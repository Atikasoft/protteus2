/**
 * CambioEjercicioFiscalTemporizador.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of PROTEUS ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with PROTEUS.
 *
 * PROTEUS Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 06/05/2014
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
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
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Proceso automatico que se encarga de realizar: Copia mensual del distributivo
 * detalles, con la finalidad de mantener un histórico del mismo.
 * <p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class ReplicacionDistributivoTemporizador extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ReplicacionDistributivoTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "REPLICA MENSUAL DE DISTRIBUTIVO DETALLE";

    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    private static final int DIA = 1;
    /**
     * Hora de ejecución: 23 horas.
     */
    private static final int HORA = 0;

    /**
     * Minutos de ejecución: 0 minutos.
     */
    private static final int MINUTOS = 00;

    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 10;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 1440;

    /**
     * Dao de DistributivoDetalleDao.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Constructor sin argumentos.
     */
    public ReplicacionDistributivoTemporizador() {
        super();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        LOG.info(UtilCadena.concatenar("Iniciando Timer - ", NOMBRE_TEMPORIZADOR, "......"));
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.DAY_OF_MONTH, DIA);
        initialExpiration.set(Calendar.HOUR_OF_DAY, HORA);
        initialExpiration.set(Calendar.MINUTE, MINUTOS);
        initialExpiration.set(Calendar.SECOND, SEGUNDOS);
        long intervalDuration = Integer.valueOf(INTERVALO_EN_MINUTOS) * 60 * 1000;
        LOG.info(UtilCadena.concatenar("Iniciando Timer - Se creo un nuevo timer de " + NOMBRE_TEMPORIZADOR,
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
        LOG.info(UtilCadena.concatenar("Ejecutando................. ", NOMBRE_TEMPORIZADOR, " - ", timer.getInfo(), ":", new Date()));
        if(UtilFechas.obtenerDiaDelMes(new Date())== DIA){
             crearInsertarDatos();
        }
    }

    private void crearInsertarDatos() {
        try {
            //como se ejecutará cada primero el mes corresponde al anterior.
             int mes = UtilFechas.obtenerMes(UtilFechas.sumarDias(new Date(),0))+1;
            String nombreTabla = "distributivo_detalles_" + (mes < 10 ? "0"+mes : mes) + UtilFechas.obtenerAnio(new Date()).toString();
            distributivoDetalleDao.crearInsertarCopiaMensualDistributivo(nombreTabla);
        } catch (SQLException ex) {
            Logger.getLogger(ReplicacionDistributivoTemporizador.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(" error en copiado");
        }
        
    }
}
