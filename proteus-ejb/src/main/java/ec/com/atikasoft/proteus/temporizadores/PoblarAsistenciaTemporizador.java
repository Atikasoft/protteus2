/*
 *  PoblarVistaAsistenciaTemporizador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of PROTEUS
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/04/2014
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.AsistenciaDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import ec.com.atikasoft.proteus.servicio.ProcedimientosServicio;
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
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

/**
 * Proceso automatico que se encarga de realizar:
 * <p>
 * - Detecta días de inasistencia de los servidores registrados en el
 * distributivo - Determina la cantidad de minutos de atraso ( llegar después de
 * la hora de inicio de la jornada y/o salir antes de la hora de finalización de
 * la jornada, salir al almuerzo antes o entrar luego de la hora de finalización
 * de la hora designada para el almuerzo.
 * </p>
 * <p>
 * Cron debe ejecutarse diariamente.</p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class PoblarAsistenciaTemporizador {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(PoblarAsistenciaTemporizador.class.getCanonicalName());
    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "POBLAR TABLAS DE ASISTENCIA";
    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;
    /**
     * Hora de ejecución: 23 horas.
     */
    private static final int HORA = 23;
    /**
     * Minutos de ejecución: 0 minutos.
     */
    private static final int MINUTOS = 30;
    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 0;
    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 1440;
    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;
    /**
     * Dao de asistencia.
     */
    @EJB
    private AsistenciaDao asistenciaDao;
    /**
     * Servicio de procedimientos almacenados.
     */
    @EJB
    private ProcedimientosServicio procedimientosServicio;
    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;
    /**
     * Almacena las observaciones del proceso.
     */
    private List<String> listaObservados;
    /**
     * Almacena el total de registros procesados.
     */
    Long contadorRegistros = 0L;

    /**
     * Constructor sin argumentos.
     */
    public PoblarAsistenciaTemporizador() {
        super();
        listaObservados = new ArrayList<String>();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, HORA);
        initialExpiration.set(Calendar.MINUTE, MINUTOS);
        initialExpiration.set(Calendar.SECOND, SEGUNDOS);
        long intervalDuration = INTERVALO_EN_MINUTOS * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer ", NOMBRE_TEMPORIZADOR, " ", UtilFechas.formatearLargo(initialExpiration.getTime()), ", con ", intervalDuration / 1000 / 60, "\" intervalo en minutos."));
        timerService.createTimer(initialExpiration.getTime(), intervalDuration, null);
    }

    /**
     * Para el servicio.
     */
    public void deteniendoTimer() {
        Collection<Timer> timers = timerService.getTimers();
        if (timers != null) {
            for (Timer t : timers) {
                t.cancel();
                LOG.info(UtilCadena.concatenar("Deteniendo timers - timer \"", t, "\" cancelado."));
            }
        }
    }

    /**
     * método callback que se invocará al terminar el intervalo definido.
     * Ejecuta la copia de registros del día anterior.
     *
     * @param timer
     */
    @Timeout
    public void ejecutar(final Timer timer) {

        Calendar c = Calendar.getInstance();
        Date fechaTopeProceso = UtilFechas.sumarDias(c.getTime(), -2);
//        fechaTopeProceso = UtilFechas.convertirFechaStringEnDate("30/04/2012", UtilFechas.FORMATO_FECHA);
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":", UtilFechas.formatearLargo(new Date())));
        Date ultimaFecha = determinarUltimaFechaEjecutada();
        try {
            if (ultimaFecha == null) {
                LOG.info("NO SE PUEDE OBTENER LA ULTIMA FECHA DE EJECUCIÓN DEL PROCESO DE " + NOMBRE_TEMPORIZADOR);
                getListaObservados().add("NO SE PUEDE OBTENER LA ULTIMA FECHA DE EJECUCIÓN DEL PROCESO DE " + fechaTopeProceso);
                //para q se procese hasta el dia anterior, ya q carga dos fechas
                ultimaFecha = UtilFechas.sumarDias(fechaTopeProceso, -6);
            }
            Date fechaProceso = ultimaFecha;
            while (UtilFechas.truncarFecha(fechaProceso).compareTo(UtilFechas.truncarFecha(fechaTopeProceso)) <= 0) {
                try {
                    Date fechaInicio = UtilFechas.obtenerInicioDelDia(fechaProceso);
                    Date fechaFin = UtilFechas.obtenerFinDelDia(UtilFechas.sumarDias(fechaProceso, 2));
                    contadorRegistros = procedimientosServicio.cargarAsistenciasMDMQ(fechaInicio, fechaFin);
                    if (contadorRegistros == 0) {
                        LOG.log(Level.INFO, NOMBRE_TEMPORIZADOR + "/DIA SIN REGISTROS DE ASISTENCIA/{0}", fechaProceso);
                        getListaObservados().add("DIA SIN REGISTROS DE ASISTENCIA/" + fechaProceso);
                    }
                } catch (ServicioException e) {
                    LOG.info(UtilCadena.concatenar("Error en procesar SP desde mdmq " + fechaProceso, e));
                }
                //ya q se procesan dos en dos dias, se suma 3 - 1 que le quita el metodo de suma = 2
                fechaProceso = UtilFechas.sumarDias(fechaProceso, 3);
            }
        } catch (Exception e) {

            LOG.info(UtilCadena.concatenar("Error general  " + e));
            try {
                throw new Exception(e);
            } catch (Exception ex) {
                Logger.getLogger(PoblarAsistenciaTemporizador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            notificarCulminacionProceso(getListaObservados(), getListaObservados().size());
        }
    }

    /**
     * Obtiene la ultima fecha que se procesó la asistencia.
     *
     * @return
     */
    private Date determinarUltimaFechaEjecutada() {
        Date ultimaFecha = null;
        try {
            List<Asistencia> lista = asistenciaDao.buscarUltimaFechaProcesada();
            if (!lista.isEmpty()) {
                ultimaFecha = lista.get(0).getFecha();
            }
        } catch (DaoException ex) {
            Logger.getLogger(PoblarAsistenciaTemporizador.class.getName()).log(Level.SEVERE,
                    " No se puede obtener ultima fecha procesada", ex);
        }

        return ultimaFecha;
    }

    /**
     * Notifica la culminación del proceso, mediante el envío de mail.
     *
     * @param errores lista de errores del proceso
     * @param pNemonicoCorreoAdm correo del administrador del sistema
     * @param numError numero de errores presentados en el proceso.
     */
    private void notificarCulminacionProceso(final List<String> errores, int numError) {
        try {
            ParametroGlobal pNemonicoCorreoAdm = parametroGlobalDao.buscarPorNemonico(
                    ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.getCodigo());
            System.out.println("parametro obtenido:" + pNemonicoCorreoAdm);
            if (pNemonicoCorreoAdm != null) {

                String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
                String asunto = UtilCadena.concatenar(NOMBRE_TEMPORIZADOR, ":",
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

                StringBuilder mensaje = new StringBuilder();
                if (numError > 0) {
                    mensaje.append("La ejecución del proceso  " + NOMBRE_TEMPORIZADOR + " presentó las siguientes observaciones:\n\n");
                    for (String s : errores) {
                        mensaje.append(s).append("\n");
                    }
                    mensaje.append("\nRegistros Procesados:").append(contadorRegistros);
                } else {
                    mensaje.append(
                            "La ejecucion del proceso automático " + NOMBRE_TEMPORIZADOR + "  se ejecuto con éxito.\n");
                    mensaje.append("\nFecha:").append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                    mensaje.append("\nRegistros Procesados:").append(contadorRegistros);
                }
                mensajeriaServicio.enviarMail(asunto, null, to, null, mensaje.toString(), null, null);
            }
        } catch (DaoException ex) {
            LOG.log(Level.INFO, NOMBRE_TEMPORIZADOR + "/NO SE PUEDE OBTENER EL MAIL DEL ADMINISTRADOR:{0}");
        } catch (ServicioException ex) {
            LOG.log(Level.INFO, NOMBRE_TEMPORIZADOR + "/NO SE PUEDE ENVIAR MAIL:{0}", ex);
        }
    }

    /**
     * @return the listaObservados
     */
    public List<String> getListaObservados() {
        return listaObservados;
    }

    /**
     * @param listaObservados the listaObservados to set
     */
    public void setListaObservados(List<String> listaObservados) {
        this.listaObservados = listaObservados;
    }
}
