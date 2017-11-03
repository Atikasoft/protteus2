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

/**
 * Proceso automatico que se encarga de realizar: <p> Se encarga de poblar una tabla con los resumunes de ingresos para
 * efectos del calculo del IR. </p> <p> Cron debe ejecutarse diariamente.</p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class PoblarNominasIRTemporizador {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(PoblarNominasIRTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "POBLAR TABLAS DE NOMINAS IR";

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
    private static final int MINUTOS = 0;

    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 0;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 1;

    /**
     * Dao de detalle de nominas.
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     * Constructor sin argumentos.
     */
    public PoblarNominasIRTemporizador() {
        super();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        LOG.info(UtilCadena.concatenar("Iniciando Timer - ", NOMBRE_TEMPORIZADOR, "......"));
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, HORA);
        initialExpiration.set(Calendar.MINUTE, MINUTOS);
        initialExpiration.set(Calendar.SECOND, SEGUNDOS);
        long intervalDuration = Integer.valueOf(INTERVALO_EN_MINUTOS) * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer - Se creo un nuevo timer de " + NOMBRE_TEMPORIZADOR + " \"",
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
        nominaDetalleDao.crearVistaMaterializadaNominas();
    }
}
