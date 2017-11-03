/*
 *  PoblarFormularioIR.java
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

import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
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

/**
 * Proceso automatico que se encarga de realizar:
 * <p>
 * Se encarga de poblar una tabla con los resumunes de ingresos para efectos del
 * calculo del IR. </p>
 * <p>
 * Cron debe ejecutarse diariamente.</p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class PoblarFormularioIR {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(PoblarFormularioIR.class.getCanonicalName());

    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "POBLAR TABLAS PARA FORMULARIOS IR";

    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    /**
     * Hora de ejecución: 23 horas.
     */
    private static final int HORA = 01;

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
     * Dao de detalle de nominas.
     */
    @EJB
    private NominaDetalleDao formularioIRDao;

    /**
     * Constructor sin argumentos.
     */
    public PoblarFormularioIR() {
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
        LOG.info(UtilCadena.concatenar("Iniciando Timer ", NOMBRE_TEMPORIZADOR, " ", 
                UtilFechas.formatearLargo(initialExpiration.getTime()), ", con ", intervalDuration / 1000 / 60, 
                "\" intervalo en minutos."));
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
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":", 
                UtilFechas.formatearLargo(new Date())));
        formularioIRDao.crearVistaMaterializadaNominasImpuestoRentaIR();
        formularioIRDao.crearVistaMaterializadaFormularioIR();
    }
}
