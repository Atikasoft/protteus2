/**
 * AsistenciaServicio.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * AtikaSoft ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with AtikaSoft.
 *
 *
 * AtikaSoft Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 17/12/2013
 *
 * Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import com.Ostermiller.util.CSVParser;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionDetalleDao;
import ec.com.atikasoft.proteus.dao.VacacionProcesoDao;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class MigracionServicio extends BaseServicio {

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;
    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    /**
     *
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Dao de Procesos de vacaciones.
     */
    @EJB
    private VacacionProcesoDao vacacionProcesoDao;

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     *
     */
    @EJB
    private VacacionDetalleDao vacacionDetalleDao;

    /**
     *
     * @param tl
     * @param r
     * @param s
     */
    public void migrarSolicitudVacacionTrayectoriaIndividual(TrayectoriaLaboral tl, String[] r, Servidor s)
            throws DaoException {
        tl.setTipoIdentificacion("C");
        tl.setNumeroIdentificacion(r[1]);
        tl.setGrupo("V");
        tl.setTipoMovimiento(r[3] + ": MIGRACIÓN");
        tl.setNumeroMovimiento("0000000000");
        tl.setFechaHasta(r[8]);
        tl.setFechaDesde(r[7]);
        tl.setNombres(s.getNombres());
        tl.setApellidos(s.getApellidos());
        tl.setExplicacion(r[3] + ", " + r[4] + " No " + r[5] + ", DÍAS:" + r[9]);
        tl.setFechaCreacion(new Date());
        tl.setUsuarioCreacion("migracion");
        tl.setVigente(true);
        List<DistributivoDetalle> dds = distributivoDetalleDao.buscarPorServidor(s.getId());
        if (!dds.isEmpty()) {
            DistributivoDetalle dd = dds.get(0);
            tl.setRegimenLaboral(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().
                    getNombre());
            tl.setGrado(dd.getEscalaOcupacional().getGrado());
            tl.setRmu(dd.getRmu());
            tl.setRmuSobrevalorado(dd.getRmuSobrevalorado());
        }
        trayectoriaLaboralDao.crear(tl);
        trayectoriaLaboralDao.flush();
    }

    /**
     *
     * @param fila
     * @param cedula
     * @param r
     * @param s
     * @param log
     * @throws DaoException
     * @throws IOException
     * @throws ParseException
     */
    public void migrarVacacionesIndividual(int fila, String cedula, String[] r, Servidor s, OutputStream log)
            throws DaoException, IOException, ParseException {
        List<ServidorInstitucion> sis
                = servidorInstitucionDao.buscarPorInstitucionServidor(1l, s.getTipoIdentificacion(),
                        s.getNumeroIdentificacion());
        if (sis.isEmpty()) {
            // verifica si esta en un puesto activo
            List<DistributivoDetalle> puestos = distributivoDetalleDao.buscarPorServidor(s.getId());
            if (puestos.isEmpty()) {
                logger(cedula, log, "fila:", Integer.toString(fila), ",col:1 ;",
                        "Identificacion no existe asignado a un puesto:", r[0], ";", r[4]);
            } else {
                ServidorInstitucion si = new ServidorInstitucion();
                si.setFechaCreacion(new Date());
                si.setFechaIngreso(s.getFechaIngresoInstitucion());
                si.setIdInstitucion(1l);
                si.setIdServidor(s.getId());
                si.setUsuarioCreacion("migracion");
                si.setVigente(Boolean.TRUE);
                servidorInstitucionDao.crear(si);
                servidorInstitucionDao.flush();
                sis.add(si);
            }
        } else {
            Vacacion v = vacacionDao.buscarPorServidor(sis.get(0).getId());
            if (v == null) {
                v = new Vacacion();
                v.setFechaCreacion(new Date());
                v.setSaldo(0l);
                v.setSaldoProporcional(0l);
                v.setServidorInstitucion(sis.get(0));
                v.setSaldoPerdida(0l);
                v.setUsuarioCreacion("atk");
                v.setVigente(Boolean.TRUE);
                v.setSaldoMesActual(0l);
                v.setMesActual(0);
                vacacionDao.crear(v);
            }

            VacacionDetalle vd = new VacacionDetalle();
            if (r[2].trim().isEmpty()) {
                vd.setCredito(0l);
            } else {
                if (Double.valueOf(r[2]) <= 0) {
                    vd.setCredito(0l);
                } else if (Double.valueOf(r[2]) > 60) {
                    vd.setCredito(60l * 8 * 60);
                } else {
                    vd.setCredito((long) (Double.valueOf(r[2]) * 8 * 60));
                }
            }
            vd.setDebito(0l);
            vd.setFechaCreacion(new Date());
            vd.setFechaInicio(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1980"));
            vd.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").parse("31/05/2017"));
            vd.setObservacion("VACACIONES MIGRADAS");
            vd.setUsuarioCreacion("atk");
            vd.setVacacion(v);
            vd.setVigente(Boolean.TRUE);
            vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            vd.setEsAjusteManual(Boolean.FALSE);
            if (vd.getCredito().compareTo(0l) > 0) {
                vacacionDetalleDao.crear(vd);
            }
            VacacionDetalle vd1 = new VacacionDetalle();
            if (r[3].trim().isEmpty()) {
                vd1.setCredito(0l);
            } else {
                if (Double.valueOf(r[3]) <= 0) {
                    vd1.setCredito(0l);
                } else if (Double.valueOf(r[3]) > 27.5) {
                    vd1.setCredito((long) (27.5 * 8 * 60));
                } else {
                    vd1.setCredito((long) (Double.valueOf(r[3]) * 8 * 60));
                }
            }
            vd1.setDebito(0l);
            vd1.setFechaCreacion(new Date());
            vd1.setFechaInicio(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/1980"));
            vd1.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").parse("31/05/2017"));
            vd1.setObservacion("PROPORCIONALES MIGRADAS");
            vd1.setUsuarioCreacion("atk");
            vd1.setVacacion(v);
            vd1.setVigente(Boolean.TRUE);
            vd1.setEsAjusteManual(Boolean.FALSE);
            vd1.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
            if (vd1.getCredito().compareTo(0l) > 0) {
                vacacionDetalleDao.crear(vd1);
            }
        }
    }

    /**
     *
     * @param cedula
     * @param log
     * @param textos
     * @throws IOException
     */
    public void logger(String cedula, OutputStream log, String... textos) throws IOException {
        String cadena = "";
        for (String texto : textos) {
            cadena += texto;
        }
        cadena = cedula + ";" + cadena + "\n";
        log.write(cadena.getBytes());
    }

}
