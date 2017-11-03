/**
 * FinalizacionAutomaticaTemporizador.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of PROTEUS ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with PROTEUS.
 *
 * PROTEUS Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 14/01/2013
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import java.util.*;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.logging.Level;
import javax.ejb.*;
import javax.ejb.Timer;

/**
 * Proceso automatico que se encargue de realizar el reintegro de un servidor.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class FinalizacionAutomaticaTemporizador {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(FinalizacionAutomaticaTemporizador.class.getCanonicalName());

    /**
     * Nemonico del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "FINALIZACIÓN DE MOVIMIENTOS DE PERSONAL";
    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;
    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 1440;
    /**
     * Horas del dia de la ejecucion.
     */
    private static final int HORA = 23;
    /**
     * Minutos de ejecución: 0 minutos.
     */
    private static final int MINUTOS = 0;
    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 0;

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Constructor sin argumentos.
     */
    public FinalizacionAutomaticaTemporizador() {
        super();
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
     *
     * @param timer
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":", UtilFechas.formatearLargo(new Date())));
        ParametroGlobal pNemonicoCorreoAdm = null;
        ParametroInstitucional correoRRHH = null;
        try {
            pNemonicoCorreoAdm = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.
                    getCodigo());
            correoRRHH = parametroInstitucionalDao.buscarPorNemonico(ParametroGlobalEnum.CORREO_RRHH.getCodigo());
            if (correoRRHH == null) {
                enviarNotificacionAdministradorEjecucionFallida(pNemonicoCorreoAdm, new Exception("No existe el correo de Recursos Humanos"));
                return;
            }
            String correo = correoRRHH.getValorTexto().replace(";", ",");
            String[] cuentas = correo.split(",");
            List<Movimiento> movimientos = movimientoDao.buscarParaNotificacionFinalizacion();
            LOG.info(UtilCadena.concatenar("ejecutando FinalizacionTemporizador, num movimientos:", movimientos.size()));
            if (!movimientos.isEmpty()) {
                enviarNotificacionFinalizacion(movimientos, cuentas);
            }
            LOG.info(UtilCadena.concatenar("ejecutando FinalizacionTemporizador, num destinatarios:", cuentas.length));
        } catch (Exception e) {
            Logger.getLogger(FinalizacionAutomaticaTemporizador.class.getName()).log(Level.SEVERE, null, e);
            try {
                // enviar notificacion al administrador indicando que la ejecucion tiene errores.
                enviarNotificacionAdministradorEjecucionFallida(pNemonicoCorreoAdm, e);
            } catch (Exception ex) {
                Logger.getLogger(FinalizacionAutomaticaTemporizador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Envia mensaje a los analista de la institucion.
     *
     * @param institucionCoreId
     * @param mensaje
     * @param servidores
     * @param to
     * @throws ServicioException
     */
    private void enviarNotificacionFinalizacion(final List<Movimiento> movimientos, final String[] to)
            throws ServicioException {
        String asunto = "SIGEN - " + NOMBRE_TEMPORIZADOR;
        StringBuilder contenido = new StringBuilder();
        contenido.append("Estimados(as):\n\n");
        contenido.append("El Sistema de Movimiento de Personal y Nomina SIGEN informa que ");
        contenido.append("los movimientos que están vigentes y que se enlistan a continuacion están por finalizar,"
                + " por favor considerar para realizar las acciones pertinentes:\n\n");
        int cont = 0;
        for (Movimiento m : movimientos) {
            cont++;
            contenido.append(UtilCadena.concatenar(cont + ".-El Movimiento " + m.getTramite().getTipoMovimiento().getNombre() + " finaliza el "
                    + UtilFechas.formatear(m.getFechaHasta())
                    + " para el servidor con Número Identificación: ", m.getNumeroIdentificacion(), " Nombres: ", m.getApellidosNombres(),
                    " Trámite:", m.getTramite().getNumericoTramite() + "\n"));
        }
        contenido.append("\n");
        mensajeriaServicio.enviarMail(asunto, null, to, null, contenido.toString(), null, null);
    }

    /**
     * envia notificaciones de errores.
     *
     * @param pNemonicoCorreoAdm
     * @param e
     * @throws Exception
     */
    private void enviarNotificacionAdministradorEjecucionFallida(final ParametroGlobal pNemonicoCorreoAdm,
            final Exception e) throws Exception {
        if (pNemonicoCorreoAdm != null) {
            String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
            if (to == null || to.length == 0) {
                to = new String[1];
                to[0] = pNemonicoCorreoAdm.getValorTexto();
            }
            String asunto = UtilCadena.concatenar("!! " + NOMBRE_TEMPORIZADOR + "!!");
            StringBuilder mensaje = new StringBuilder();
            mensaje.append("La ejecución del proceso  de " + NOMBRE_TEMPORIZADOR + " no se ejecuto, presento errores:\n");
            mensaje.append(e.getMessage()).append("\n");
            mensaje.append("Pila de Error:\n");
            for (StackTraceElement ele : e.getStackTrace()) {
                mensaje.append(ele.getFileName()).append(".").append(ele.getClassName()).append(".").append(ele.
                        getMethodName()).append(":").append(ele.getLineNumber()).append("\n");
            }
            mensajeriaServicio.enviarMail(asunto, null, to, null, mensaje.toString(), null, null);
        }
    }
}
