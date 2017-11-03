/*
 *  CambioEjercicioFiscalTemporizador.java
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
 *  06/11/2013
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.VacacionParametroDao;
import ec.com.atikasoft.proteus.enums.EstadoNotificacionDestinatarioEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.modelo.DestinatarioNotificacion;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Notificacion;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import ec.com.atikasoft.proteus.servicio.NotificacionesServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
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
import java.util.logging.Level;
import javax.ejb.ScheduleExpression;
import javax.ejb.TimerConfig;

/**
 *
 * El sistema debe generar una alerta del 12 al 20 del mes anterior, cuando todos los analistas ingresan al sistema,
 * mostrando los servidores que consten en las acciones que afectan al rol de ese mes (acciones que contienen fecha
 * inicio y fecha fin)
 *
 *
 *
 *
 * @author Henry <henry.molina@atikasoft.com>
 */
@Stateless
@LocalBean
public class NotificacionFinalizacionMovimientosTemporizador extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG
            = Logger.getLogger(NotificacionFinalizacionMovimientosTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador
     */
    private static final String NOMBRE_TEMPORIZADOR = "NOTIFICACION DE FINALIZACION DE MOVIMIENTOS";

    /**
     *
     */
    private static final String ASUNTO_NOTIFICACION = "Movimiento de Personal por finalizar";

    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    /**
     *
     */
    private static final String DIAS = "12-20";

    /**
     *
     */
    private static final String HORA = "0";

    /**
     *
     */
    private static final String MINUTOS = "15";

    /**
     * Indica el total de servidores que se procesara.
     */
    private static final Integer TOTAL_LOTE = 1000;

    /**
     *
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     *
     */
    @EJB
    private NotificacionesServicio notificacionesServicio;
    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Constructor sin argumentos.
     */
    public NotificacionFinalizacionMovimientosTemporizador() {
        super();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        deteniendoTimer();
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.dayOfMonth(DIAS).second(0).minute(MINUTOS).hour(HORA);
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("NOTIFICACION DE FINALIZACION DE MOVIMIENTOS");
        timerService.createCalendarTimer(scheduleExpression, timerConfig);
        imprimir();

    }

    /**
     *
     */
    private void imprimir() {
        Collection<Timer> timers = timerService.getTimers();
        if (timers != null) {
            for (Timer t : timers) {
                LOG.info(UtilCadena.concatenar("TIMER ", t.getInfo(), ", Fecha ejecucion: "
                        + UtilFechas.formatearLargo(t.getNextTimeout())));
            }
        }
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
     * @param timer timer de ejecucion
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":",
                UtilFechas.formatearLargo(new Date())));
        try {
            int fila = 0;
            List<DistributivoDetalle> puestos = distributivoDetalleDao.buscarFinalizacionesDelMes(
                    UtilFechas.obtenerPrimerDiaDelMes(new Date()), UtilFechas.obtenerUltimaFechaDelMes(new Date()),
                    fila, TOTAL_LOTE);
            while (!puestos.isEmpty()) {
                for (DistributivoDetalle puesto : puestos) {
                    StringBuilder mensaje = new StringBuilder();
                    mensaje.append("El siguiente movimiento de personal finaliza en el presente periodo:\n\n");
                    mensaje.append("Servidor Municipal :").append(puesto.getServidor().getApellidosNombres()).
                            append("\n");
                    mensaje.append("Tipo Identificación :").append(
                            TipoIdentificacionEnum.obtenerDescripcion(puesto.getServidor().getTipoIdentificacion())).
                            append("\n");
                    mensaje.append("Número Identificación :").append(puesto.getServidor().getNumeroIdentificacion()).
                            append("\n");
                    mensaje.append("Unidad Organizacional:").append(puesto.getDistributivo().
                            getUnidadOrganizacional().getRuta()).append("\n");
                    if (puesto.getMovimiento() == null) {
                        mensaje.append("Fecha Finalización:").append(UtilFechas.formatear(puesto.getFechaFin())).
                                append("\n");
                    } else {
                        mensaje.append("Tipo de Movimiento de Personal:").append(
                                puesto.getMovimiento().getTramite().getTipoMovimiento().getNombre()).append("\n");
                        mensaje.append("Fecha Inicio:").append(
                                UtilFechas.formatear(puesto.getMovimiento().getRigeApartirDe())).append("\n");
                        if (puesto.getMovimiento().getFechaHasta() == null) {
                            mensaje.append("Fecha Finalización:").append(
                                    UtilFechas.formatear(puesto.getFechaFin())).append("\n");
                        } else {
                            mensaje.append("Fecha Finalización:").append(
                                    UtilFechas.formatear(puesto.getMovimiento().getFechaHasta())).append("\n");
                        }

                    }
                    Notificacion notificacion = new Notificacion();
                    notificacion.setAsunto(UtilCadena.concatenar(ASUNTO_NOTIFICACION, " ",
                            puesto.getServidor().getApellidosNombres()));
                    notificacion.setEnviadaATodos(Boolean.FALSE);
                    notificacion.setFechaCreacion(new Date());
                    notificacion.setInstitucionEjercicioFiscal(puesto.getDistributivo().getInstitucionEjercicioFiscal());
                    notificacion.setMensaje(mensaje.toString());
                    notificacion.setRemitente(null);
                    notificacion.setUsuarioCreacion("auto");
                    notificacion.setVigente(Boolean.TRUE);

                    List<DestinatarioNotificacion> destinatarios = new ArrayList<>();
                    //  funcionario municipal que elaboro el movimiento de personal (si esta activo en la institucion) 
                    if (puesto.getMovimiento() != null) {
                        DistributivoDetalle dd
                                = distributivoDetalleDao.buscarPorIdentificacion(puesto.getUsuarioCreacion());
                        if (dd != null) {
                            DestinatarioNotificacion dn = new DestinatarioNotificacion();
                            dn.setDestinatario(dd.getServidor());
                            dn.setEstado(EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo());
                            dn.setFechaCreacion(new Date());
                            dn.setUsuarioCreacion("auto");
                            dn.setVigente(Boolean.TRUE);
                            destinatarios.add(dn);
                        }
                    }
                    // Personal responsable y de apoyo del desconcentrado.
                    List<Servidor> servidores = desconcentradoServicio.buscarServidoresResposablesYApoyos(
                            puesto.getServidor().getId());
                    for (Servidor servidor : servidores) {
                        DestinatarioNotificacion dn = new DestinatarioNotificacion();
                        dn.setDestinatario(servidor);
                        dn.setEstado(EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo());
                        dn.setFechaCreacion(new Date());
                        dn.setUsuarioCreacion("auto");
                        dn.setVigente(Boolean.TRUE);
                        destinatarios.add(dn);
                    }

                    // Si no existen destinatario, no se envia la notificacion
                    if (!destinatarios.isEmpty()) {
                        notificacionesServicio.guardarYEnviarNotificacion(notificacion, destinatarios, null);
                    }

                }
                fila = fila + TOTAL_LOTE;
                puestos = distributivoDetalleDao.buscarFinalizacionesDelMes(
                        UtilFechas.obtenerPrimerDiaDelMes(new Date()), UtilFechas.obtenerUltimaFechaDelMes(new Date()),
                        fila, TOTAL_LOTE);
            }

        } catch (Exception e) {
            LOG.log(Level.INFO, "Problemas al ejecutar el Timmer Vacaciones==>{0}", e);
        }
    }

}
