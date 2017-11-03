/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.servicio.MarcacionesServicio;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Stateless
@LocalBean
public class ProcesoRegistroMarcacionesTemporizador extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(VacacionTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador
     */
    private static final String NOMBRE_TEMPORIZADOR = "PROCESO DE MARCACIONES SEMANALES";

    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    /**
     * Hora de ejecución: 23 horas.
     */
    private static final int HORA = 11;

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
    private static final int INTERVALO_EN_MINUTOS = /*1440*/1;

    /**
     * Indica el total de servidores que se procesara.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 1000;

    /**
     *
     */
    @EJB
    private MarcacionesServicio marcacionesServicio;

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        //initialExpiration.set(Calendar.HOUR_OF_DAY, HORA);
        //initialExpiration.set(Calendar.MINUTE, MINUTOS);
        //initialExpiration.set(Calendar.SECOND, SEGUNDOS);
        initialExpiration.add(Calendar.SECOND, 20);
        if (initialExpiration.getTime().compareTo(new Date()) < 0) {
            initialExpiration.set(Calendar.DAY_OF_YEAR, initialExpiration.get(Calendar.DAY_OF_YEAR) + 1);
        }
        long intervalDuration = INTERVALO_EN_MINUTOS * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer ", NOMBRE_TEMPORIZADOR, " ",
                UtilFechas.formatearLargo(initialExpiration.getTime()), ", con ", intervalDuration / 1000 / 60,
                " intervalo en minutos."));
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
     * @param timer timer de ejecucion
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        try {
            marcacionesServicio.ejecutarMigracionMarcacionesSemanales();
        } catch (ServicioException ex) {
            Logger.getLogger(ProcesoRegistroMarcacionesTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
