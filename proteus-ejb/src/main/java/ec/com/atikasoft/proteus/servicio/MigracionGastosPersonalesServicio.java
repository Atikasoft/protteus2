/*
 *  AsistenciaServicio.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.GastoPersonalDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.enums.TipoProyeccionGastoPersonalEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class MigracionGastosPersonalesServicio extends BaseServicio {

    @EJB
    private ServidorDao servidorDao;

    @EJB
    private GastoPersonalDao gastoPersonalDao;
    /**
     * Servicio de Impuesto a la renta.
     */
    @EJB
    private ImpuestoRentaServicio impuestoRentaServicio;

    public void migrar() throws ServicioException {
        try {
            System.out.println(" MIGRANDO GASTOS PERSONALES...................");
            String dir = "/home/likewise-open/DMQ/t-hnoboa/Documentos/";
            List<String> errores = new ArrayList<String>();
            File file = new File(dir + "gastos_personales_dmq.csv");
            byte[] archivo = UtilArchivos.getBytesFromFile(file);
            Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 50);
            System.out.print("TOTAL REGISTROS:" + datos.size());
            for (Long fila : datos.keySet()) {
                List<String> error = new ArrayList<String>();
                System.out.print("FILA :" + fila);
                List<String> dato = datos.get(fila);

                //------------------------------------------------------------------------//
                // Del archivo dado, generar en excel una tabla dinamica para consolidar los tipos de gastos por servidor.
                // IDENTIFICACION  ALIMENTACION 2014	EDUCACION 2014	INGRESOS OTROS EMP 2014	SALUD 2014	
                //TOTALINGRESOS EMP 2014	VESTIMENTA 2014	VIVIENDA 2014	Total general
                //------------------------------------------------------------------------//
                String cedula = dato.get(0);
                Servidor servidor = servidorDao.buscar(TipoIdentificacionEnum.CEDULA.getCodigo(), cedula);
                if (servidor == null) {
                    cedula = dato.get(0).length() == 9 ? "0" + dato.get(0) : dato.get(0);
                    servidor = servidorDao.buscar(TipoIdentificacionEnum.CEDULA.getCodigo(), cedula);
                }
                if (servidor != null) {
                    List<GastoPersonal> lgp = gastoPersonalDao.buscarTodosPorServidorYEjercicioFiscal(servidor.getId(), 1l);

                    if (lgp.isEmpty()) {
                        // crear
                        GastoPersonal gp = new GastoPersonal();
                        gp.setServidor(servidor);
                        gp.setIdServidor(servidor.getId());
                        gp.setIdEjercicioFiscal(1l);
                        gp.setEjercicioFiscal(new InstitucionEjercicioFiscal(1l));
                        gp.setFechaCreacion(new Date());
                        gp.setUsuarioCreacion("Migración");
                        gp.setVigente(Boolean.TRUE);

                        gp.setAlimentacion(new BigDecimal(dato.get(1)));
                        gp.setEducacion(new BigDecimal(dato.get(2)));
                        gp.setIngresosOtroEmpleador(new BigDecimal(dato.get(3)));
                        gp.setSalud(new BigDecimal(dato.get(4)));
                        gp.setIngresos(new BigDecimal(dato.get(5)));
                        gp.setVestimenta(new BigDecimal(dato.get(6)));
                        gp.setVivienda(new BigDecimal(dato.get(7)));
                        BigDecimal totalDeducible = gp.getAlimentacion().add(gp.getEducacion()).add(gp.getSalud()).add(gp.getVivienda()).add(gp.getVestimenta());
                        gp.setTotalDeducible(totalDeducible);
                        gp.setTotalIngresos(gp.getTotalIngresos().add(gp.getIngresosOtroEmpleador()));
                        gp.setExoneracionDiscapacidad(BigDecimal.ZERO);
                        gp.setExoneracionTerceraEdad(BigDecimal.ZERO);
                        gp.setIessPersonal(BigDecimal.ZERO);
                        gp.setIessPersonalOtroEmpleador(BigDecimal.ZERO);
                        gp.setIngresosAdicionales(BigDecimal.ZERO);
                        gp.setTipo(TipoProyeccionGastoPersonalEnum.ORIGINAL.getCodigo());
                        impuestoRentaServicio.guardarGastoPersonal(gp, null);
                    } else {
                        GastoPersonal gp = lgp.get(0);
                        gp.setFechaActualizacion(new Date());
                        gp.setUsuarioActualizacion("Migración");
                        gp.setAlimentacion(new BigDecimal(dato.get(1)));
                        gp.setEducacion(new BigDecimal(dato.get(2)));
                        gp.setIngresosOtroEmpleador(new BigDecimal(dato.get(3)));
                        gp.setSalud(new BigDecimal(dato.get(4)));
                        gp.setIngresos(new BigDecimal(dato.get(5)));
                        gp.setVestimenta(new BigDecimal(dato.get(6)));
                        gp.setVivienda(new BigDecimal(dato.get(7)));
                        BigDecimal totalDeducible = gp.getAlimentacion().add(gp.getEducacion()).add(gp.getSalud()).add(gp.getVivienda()).add(gp.getVestimenta());
                        gp.setTotalDeducible(totalDeducible);
                        gp.setTotalIngresos(gp.getTotalIngresos().add(gp.getIngresosOtroEmpleador()));
                        gp.setExoneracionDiscapacidad(BigDecimal.ZERO);
                        gp.setExoneracionTerceraEdad(BigDecimal.ZERO);
                        gp.setIessPersonal(BigDecimal.ZERO);
                        gp.setIessPersonalOtroEmpleador(BigDecimal.ZERO);
                        gp.setIngresosAdicionales(BigDecimal.ZERO);
                        gp.setTipo(TipoProyeccionGastoPersonalEnum.ORIGINAL.getCodigo());
                        impuestoRentaServicio.actualizarGastoPersonal(gp, null);
                    }
                } else {
                    error.add(dato.get(0));
                }
                if (error.isEmpty()) {
                } else {
                    errores.addAll(error);
                }
            }
            PrintWriter writer = new PrintWriter(dir + "errores-gastos_personales.txt", "UTF-8");
            writer.println("ERRORES:" + errores.size());
            for (String e : errores) {
                writer.println(e);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
