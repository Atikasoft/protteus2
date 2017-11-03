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

import ec.com.atikasoft.proteus.dao.BancoDao;
import ec.com.atikasoft.proteus.dao.CatalogoDao;
import ec.com.atikasoft.proteus.dao.CuentaBancariaDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class MigracionServidorServicio extends BaseServicio {

    @EJB
    private ServidorDao servidorDao;

    @EJB
    private CatalogoDao catalogoDao;

    @EJB
    private BancoDao bancoDao;

    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    public void migrar() throws ServicioException {
        try {

            SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
            String dir = "/home/henry/Dropbox/Proyectos/municipio/proteus/archivos/nomina/";
            List<String> errores = new ArrayList<String>();
            File file = new File(dir + "servidores-ultimo.csv");
            byte[] archivo = UtilArchivos.getBytesFromFile(file);
            Map<Long, List<String>> datos = UtilArchivos.parsearArchivoCSV(archivo, ';', 50);
            System.out.print("TOTAL REGISTROS:" + datos.size());
            for (Long fila : datos.keySet()) {
                List<String> error = new ArrayList<String>();
                System.out.print("FILA :" + fila);
                List<String> dato = datos.get(fila);

                //------------------------------------------------------------------------//
                // Genero 4
                Catalogo genero = catalogoDao.buscar(dato.get(4), TipoCatalogoEnum.GENERO.getCodigo());
                if (genero == null) {
                    error.add("fila:" + fila + ",genero no existe :" + dato.get(4));
                }
                // tipo sangre 5
                Catalogo sangre = catalogoDao.buscar(dato.get(5), TipoCatalogoEnum.TIPO_SANGRE.getCodigo());
                if (sangre == null && dato.get(5).trim().length() > 0) {
                    error.add("fila:" + fila + ",sangre no existe :" + dato.get(5));
                }
                // estado civil 6
                Catalogo estadoCivil = catalogoDao.buscar(dato.get(6), TipoCatalogoEnum.ESTADO_CIVIL.getCodigo());
                if (estadoCivil == null) {
                    error.add("fila:" + fila + ",estado civil no existe :" + dato.get(6));
                }
                // etnia 7
                Catalogo etnia = catalogoDao.buscar(dato.get(7), TipoCatalogoEnum.ETNIA.getCodigo());
                if (etnia == null && dato.get(7).trim().length() > 0) {
                    error.add("fila:" + fila + ",etnia no existe :" + dato.get(7));
                }
                // talla uniforme 9
                Catalogo tallaUniforme = catalogoDao.buscar(dato.get(9), TipoCatalogoEnum.TALLA_UNIFORME.getCodigo());
                if (tallaUniforme == null && dato.get(9).trim().length() > 0) {
                    error.add("fila:" + fila + ",tallaUniforme no existe :" + dato.get(9));
                }
                // talla calzado 10
                Catalogo tallaCalzado = catalogoDao.buscar(dato.get(10), TipoCatalogoEnum.NUMERO_CALZADO.getCodigo());
                if (tallaCalzado == null && dato.get(10).trim().length() > 0) {
                    error.add("fila:" + fila + ",tallaCalzado no existe :" + dato.get(10));
                }
                // capaidad especial 12
                Catalogo capacidadEspecial = catalogoDao.buscar(dato.get(12),
                        TipoCatalogoEnum.DISCAPACIDADES.getCodigo());
                if (capacidadEspecial == null && dato.get(12).trim().length() > 0) {
                    error.add("fila:" + fila + ",capacidadEspecial no existe :" + dato.get(12));
                }
                // nacionalidad 14
                Catalogo nacionalidad = catalogoDao.buscar(dato.get(14), TipoCatalogoEnum.NACIONALIDAD.getCodigo());
                if (nacionalidad == null) {
                    error.add("fila:" + fila + ",nacionalidad no existe :" + dato.get(14));
                }
                // banco 17
                Banco banco = null;
                if (dato.get(17).trim().length() > 0) {
                    List<Banco> bancos = bancoDao.buscarTodosPorCodigo(dato.get(17));
                    if (bancos.isEmpty()) {
                        error.add("fila:" + fila + ",banco no existe :" + dato.get(17));
                    } else {
                        banco = bancos.get(0);
                    }
                }
                //------------------------------------------------------------------------//
                if (error.isEmpty()) {
                    Servidor servidor = servidorDao.buscar(dato.get(0), dato.get(1));
                    if (servidor == null) {
                        // crear
                        servidor = new Servidor();
                        servidor.setApellidos(dato.get(2));
                        servidor.setApellidosNombres(dato.get(2) + " " + dato.get(3));
                        if (capacidadEspecial != null) {
                            servidor.setCatalogoCapacidadesId(capacidadEspecial.getId());
                        }
                        if (estadoCivil != null) {
                            servidor.setCatalogoEstadoCivilId(estadoCivil.getId());
                        }
                        if (etnia != null) {
                            servidor.setCatalogoEtniaId(etnia.getId());
                        }
                        if (genero != null) {
                            servidor.setCatalogoGeneroId(genero.getId());
                        }
                        if (nacionalidad != null) {
                            servidor.setCatalogoNacionalidadId(nacionalidad.getId());
                        }
                        if (tallaCalzado != null) {
                            servidor.setCatalogoNumeroCalzadoId(tallaCalzado.getId());
                        }
                        if (tallaUniforme != null) {
                            servidor.setCatalogoTallaUniformeId(tallaUniforme.getId());
                        }
                        if (sangre != null) {
                            servidor.setCatalogoTipoSangreId(sangre.getId());
                        }
                        servidor.setCodigoEmpleado(Long.valueOf(dato.get(20)));
                        servidor.setEstadoPersonalId(7l);
                        servidor.setFechaCreacion(new Date());
                        servidor.setFechaNacimiento(formato.parse(dato.get(16)));
                        servidor.setJornada(8);
                        servidor.setMail(dato.get(8));
                        servidor.setNombres(dato.get(3));
                        servidor.setNumeroCarnetConadis(dato.get(11));
                        servidor.setNumeroIdentificacion(dato.get(1));
                        if (dato.get(13).trim().length() > 0) {
                            servidor.setPorcentajeDiscapacidad(new BigDecimal(dato.get(13)));
                        }
                        servidor.setRecibeFondoReserva(Boolean.TRUE);
                        servidor.setTipoIdentificacion(dato.get(0));
                        servidor.setUsuarioCreacion("migra");
                        servidor.setVigente(Boolean.TRUE);
                        //servidorDao.crear(servidor);
                    } else {
                        // actualizar.
                        servidor.setApellidos(dato.get(2));
                        servidor.setApellidosNombres(dato.get(2) + " " + dato.get(3));
                        if (capacidadEspecial != null) {
                            servidor.setCatalogoCapacidadesId(capacidadEspecial.getId());
                        }
                        if (estadoCivil != null) {
                            servidor.setCatalogoEstadoCivilId(estadoCivil.getId());
                        }
                        if (etnia != null) {
                            servidor.setCatalogoEtniaId(etnia.getId());
                        }
                        if (genero != null) {
                            servidor.setCatalogoGeneroId(genero.getId());
                        }
                        if (nacionalidad != null) {
                            servidor.setCatalogoNacionalidadId(nacionalidad.getId());
                        }
                        if (tallaCalzado != null) {
                            servidor.setCatalogoNumeroCalzadoId(tallaCalzado.getId());
                        }
                        if (tallaUniforme != null) {
                            servidor.setCatalogoTallaUniformeId(tallaUniforme.getId());
                        }
                        if (sangre != null) {
                            servidor.setCatalogoTipoSangreId(sangre.getId());
                        }
                        servidor.setCodigoEmpleado(Long.valueOf(dato.get(20)));
                        servidor.setEstadoPersonalId(7l);
                        servidor.setFechaCreacion(new Date());
                        servidor.setFechaNacimiento(formato.parse(dato.get(16)));
                        servidor.setJornada(8);
                        servidor.setMail(dato.get(8));
                        servidor.setNombres(dato.get(3));
                        servidor.setNumeroCarnetConadis(dato.get(11));
                        servidor.setNumeroIdentificacion(dato.get(1));
                        if (dato.get(13).trim().length() > 0) {
                            servidor.setPorcentajeDiscapacidad(new BigDecimal(dato.get(13)));
                        }
                        servidor.setRecibeFondoReserva(Boolean.TRUE);
                        servidor.setTipoIdentificacion(dato.get(0));
                        servidor.setUsuarioCreacion("migra");
                        servidor.setVigente(Boolean.TRUE);
                        //servidorDao.actualizar(servidor);

                        if (banco != null) {
                            CuentaBancaria c = new CuentaBancaria();
                            c.setBancoId(banco.getId());
                            c.setFechaCreacion(new Date());
                            c.setNumerCuenta(dato.get(19));
                            c.setPagoNomina(Boolean.TRUE);
                            c.setServidorId(servidor.getId());
                            c.setTipoCuenta(dato.get(18));
                            c.setUsuarioCreacion("migra");
                            c.setVigente(Boolean.TRUE);
                            cuentaBancariaDao.crear(c);
                        }
                    }
                } else {
                    errores.addAll(error);
                }
            }
            PrintWriter writer = new PrintWriter(dir + "errores-servidores.txt", "UTF-8");
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
