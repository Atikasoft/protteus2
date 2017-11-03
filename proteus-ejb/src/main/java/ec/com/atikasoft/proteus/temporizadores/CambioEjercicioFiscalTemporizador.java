/*
 *  CambioEjercicioFiscalTemporizador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  14/01/2013
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.EjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.*;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.text.SimpleDateFormat;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class CambioEjercicioFiscalTemporizador {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(CambioEjercicioFiscalTemporizador.class.getCanonicalName());

    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    /**
     * Mes de ejecución: Enero.
     */
    //private static final int START_MONTH = 2;
    private static final int START_MONTH = 12;

    /**
     * Día de ejecución: 1 .
     */
    //private static final int START_DAY = 25;
    private static final int START_DAY = 31;

    /**
     * Hora de ejecución: 23 horas.
     */
    //private static final int START_HOUR = 11;
    private static final int START_HOUR = 23;

    /**
     * Minutos de ejecución: 0 minutos.
     */
    //private static final int START_MINUTES = 15;
    private static final int START_MINUTES = 59;

    /**
     * Segundos de ejecución: 00.
     */
    private static final int START_SECONDS = 59;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVAL_IN_MINUTES = 1440;

    /**
     * Dao del ejercicio fiscal.
     */
    @EJB
    private EjercicioFiscalDao ejercicioFiscalDao;

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de la Institución.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Constructor sin argumentos.
     */
    public CambioEjercicioFiscalTemporizador() {
        super();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        LOG.info("Iniciando Timer- Cambio Ejercicio Fiscal se encuentra iniciando.......");
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.MONTH, START_MONTH);
        initialExpiration.set(Calendar.DAY_OF_MONTH, START_DAY);
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long intervalDuration = Integer.valueOf(INTERVAL_IN_MINUTES) * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer - Se creo un nuevo timer de Cambio Ejercicio Fiscal para \"",
                initialExpiration.getTime(), "\", con \"", intervalDuration, "\" intervalo en milss."));
        timerService.createTimer(initialExpiration.getTime(), intervalDuration, null);
    }

    /**
     * Para el servicio.
     */
    public void deteniendoTimer() {
        Collection<Timer> timers = timerService.getTimers();
        LOG.info(UtilCadena.concatenar("Deteniendo Timer - existen timers ? ", timers));
        if (timers != null) {
            for (Iterator iterator = timers.iterator(); iterator.hasNext();) {
                Timer t = (Timer) iterator.next();
                t.cancel();
                LOG.info(UtilCadena.concatenar("Deteniendo timers - timer \"", t, "\" cancelado."));
            }
        }
    }

    /**
     * método callback que se invocará al terminar el intervalo definido.
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        LOG.info(UtilCadena.concatenar("Ejecutando Cambio Ejercicio Fiscal - ", timer.getInfo(), ":", new Date()));
        try {
            EjercicioFiscal ef = ejercicioFiscalDao.buscarActivo();
            ejecutarCambio(ef);
            EjercicioFiscal efnv = ejercicioFiscalDao.buscarActivo();

            List<InstitucionEjercicioFiscal> instituciones = institucionEjercicioFiscalDao.buscarVigentes();

            iniciarInstitucionesNuevoEjericioFiscal(efnv, instituciones);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para ejecutar el cambio de ejercicio fiscal.
     *
     * @param efactivo EjercicioFiscal
     * @throws ServicioException ServicioException
     */
    private void ejecutarCambio(final EjercicioFiscal efactivo) throws ServicioException {
        LOG.info(UtilCadena.concatenar("ejecutando Cambio de Ejercicio Fiscal"));
        efactivo.setVigente(Boolean.FALSE);
        Integer x;
        x = Integer.parseInt(efactivo.getNombre());
        x += 1;
        administracionServicio.actualizarEjercicioFiscal(efactivo);
        EjercicioFiscal efnuevo = new EjercicioFiscal();
        efnuevo.setNombre(x.toString());
        Calendar cal = Calendar.getInstance();
        cal.setTime(efactivo.getFechaFin());
        cal.add(Calendar.DAY_OF_YEAR, 1);//Adding 1 day to current date
        efnuevo.setFechaInicio(cal.getTime());
        cal.add(Calendar.YEAR, 1); //Adding 1 year to current date
        cal.add(Calendar.DAY_OF_YEAR, -1); //minus 1 day to current date
        efnuevo.setFechaFin(cal.getTime());
        efnuevo.setFechaCreacion(new Date());
        efnuevo.setUsuarioCreacion("MRL");
        efnuevo.setVigente(Boolean.TRUE);
        administracionServicio.guardarEjercicioFiscal(efnuevo);
    }

    /**
     * Metodo para actualizar las insituciones.
     *
     * @param ef EjercicioFiscal.
     * @param instituciones List.
     * @throws ServicioException ServicioException
     */
    private void iniciarInstitucionesNuevoEjericioFiscal(final EjercicioFiscal ef,
            final List<InstitucionEjercicioFiscal> instituciones)
            throws ServicioException {
        LOG.info(UtilCadena.concatenar("ejecutando Cambio de Ejercicio Fiscal "
                + "para la actualizacion de las Instituciones"));
//        for (InstitucionEjercicioFiscal i : instituciones) {
//            i.setVigente(Boolean.FALSE);
//            administracionServicio.actualizarInstitucion(i);
//            InstitucionEjercicioFiscal inuevo = new InstitucionEjercicioFiscal();
//            inuevo.setContadorActoAdministrativo(0L);
//            inuevo.setContadorRegistro(0L);
//            inuevo.setContadorTramite(0L);
//            inuevo.setEjercicioFiscal(ef);
//            inuevo.setCodigo(i.getCodigo());
//            inuevo.setInstitucionCoreId(i.getInstitucionCoreId());
//            inuevo.setNombre(i.getNombre());
//            inuevo.setRuc(i.getRuc());
//            inuevo.setVigente(Boolean.TRUE);
//            inuevo.setFechaCreacion(new Date());
//            inuevo.setUsuarioCreacion("MRL");
//            administracionServicio.guardarInstituciones(inuevo);
//            InstitucionEjercicioFiscal instNueva = administracionServicio.buscarInstitucionPorCodigoYEjercicioFiscal(i.getCodigo(), ef.
//                    getId());
//            List<ParametroInstitucional> listaPI =
//                    administracionServicio.listarTodosParametroInstitucional(i.getId());
//            iniciarParametrosInstitucionalesNuevoEjercicioFiscal(instNueva, listaPI);
//        }
    }

    /**
     * Método apra actualizar los parametros insitucionales.
     *
     * @param i InstitucionEjercicioFiscal
     * @param listaPI List
     */
    private void iniciarParametrosInstitucionalesNuevoEjercicioFiscal(final InstitucionEjercicioFiscal i,
            final List<ParametroInstitucional> listaPI) throws ServicioException {
        LOG.info(UtilCadena.concatenar("ejecutando Cambio de Ejercicio Fiscal "
                + "para la actualizacion de parametros insitucionales"));
        for (ParametroInstitucional pI : listaPI) {
            pI.setVigente(Boolean.FALSE);
            administracionServicio.actualizarParametroInstitucional(pI);
            ParametroInstitucional pINuevo = new ParametroInstitucional();
            pINuevo = pI;
            pINuevo.setVigente(Boolean.TRUE);
            pINuevo.setInstitucion(i.getInstitucion());
            pINuevo.setFechaCreacion(new Date());
            pINuevo.setUsuarioCreacion("MRL");
            pINuevo.setFechaActualizacion(null);
            pINuevo.setUsuarioActualizacion(null);
            pINuevo.setId(null);
            administracionServicio.guardarParametroInstitucional(pI, pI.getArchivo());
        }
    }
}
