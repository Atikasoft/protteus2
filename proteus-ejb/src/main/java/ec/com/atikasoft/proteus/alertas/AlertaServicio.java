/*
 *  AlertaServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Jan 4, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.alertas;

import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoAlertaDao;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoAlerta;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.*;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 * Ejecuta las alarmas del sistema.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class AlertaServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(AlertaServicio.class.getCanonicalName());

    /**
     * Injección del TimerService
     */
    @Resource
    private TimerService timerService;

    /**
     * Dao de parametro global.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Dao de tipos de movimientos x alerta.
     */
    @EJB
    private TipoMovimientoAlertaDao tipoMovimientoAlertaDao;

    /**
     * Hora de ejecución: 23 horas
     */
    private static final int START_HOUR = 23;

    /**
     * Minutos de ejecución: 0 minutos
     */
    private static final int START_MINUTES = 0;

    /**
     * Segundos de ejecución: 00
     */
    private static final int START_SECONDS = 0;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas
     */
    private static final int INTERVAL_IN_MINUTES = 1440;

    /**
     * Constructor sin argumentos.
     */
    public AlertaServicio() {
        super();
    }

    /**
     * Levanta el servicio
     */
    public void iniciandoTimer() {
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.HOUR_OF_DAY, START_HOUR);
        initialExpiration.set(Calendar.MINUTE, START_MINUTES);
        initialExpiration.set(Calendar.SECOND, START_SECONDS);
        long intervalDuration = INTERVAL_IN_MINUTES * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer ", " ALERTAS ", " ", UtilFechas.formatearLargo(initialExpiration.getTime()), ", con ", intervalDuration / 1000 / 60, "\" intervalo en minutos."));
        timerService.createTimer(initialExpiration.getTime(), intervalDuration, null);
    }

    /**
     * Para el servicio
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
     * metodo callback que se invocará al terminar el intervalo definido
     *
     * @param timer datos del time
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", " ALERTAS ", ":",
                UtilFechas.formatearLargo(new Date())));
        try {
            List<ParametroGlobal> parametrosGlobales = parametroGlobalDao.buscarTodos();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put(ReglasParametrosEnum.PARAMETROS_GLOBALES.getCodigo(), parametrosGlobales);
            ejecutarAlertas(parametros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecuta las alerta definidas por tipo de movimiento.
     *
     * @param parametros lista de parametros
     * @throws DaoException error en el acceso a datos
     */
    private void ejecutarAlertas(final Map<String, Object> parametros) throws DaoException {
        List<TipoMovimientoAlerta> alertas = tipoMovimientoAlertaDao.buscarTodos();
        for (TipoMovimientoAlerta alerta : alertas) {
            if (alerta.getVigente() && alerta.getAlerta().getVigente()) {
//                if (alerta.getAlerta().getNemonico().equals(AlertarConclusionSuspensionTemporal.NEMONICO_ALERTA)) {
//                    alertarConclusionSuspensionTemporal.ejecutar(parametros, alerta);
//                }
            }
        }
    }
}
