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
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionParametroDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.modelo.VacacionProceso;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javolution.util.FastTable;

/**
 * Proceso automatico que se encarga de realizar: - Acreditación de días de vacaciones (en minutos) de todos los
 * servidores que se encuentren activos, de acuerdo a la fecha de ingreso y la parametrizacion respectiva
 * <p>
 * -
 * Acreditación de días adicionales de vacaciones por tiempo de servicio. Eliminación de días de vacaciones por exceso
 * de acumulación. </p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class VacacionTemporizador extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(VacacionTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador
     */
    private static final String NOMBRE_TEMPORIZADOR = "REGISTRO DE ACUMULACION DE VACACIONES";

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
    private static final int INTERVALO_EN_MINUTOS = 1440;

    /**
     * Indica el total de servidores que se procesara.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 1000;

    /**
     * Variable para observacion de cada proceso.
     */
    public static final String TIMER_OBSERVACION_ACUMULACION_VACACION_PERIODO
            = "ACREDITACIÓN PERIODICA DE SALDO DE VACACIONES.";

    /**
     *
     */
    public static final String TIMER_OBSERVACION_ACUMULACION_VACACION_ADICIONAL
            = "ACREDITACIÓN PERIODICA DE SALDO ADICIONAL DE VACACIONES POR TIEMPO SERVICIO.";

    /**
     *
     */
    public static final String TIMER_OBSERVACION_CONTROL_EXCESO_ACUMULACION_VACACION
            = "DÉBITO POR EXCESO DE ACUMULACIÓN DE SALDO DE VACACIONES.";

    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de VacacionParametro.
     */
    @EJB
    private VacacionParametroDao vacacionParametroDao;

    /**
     * Servicio de Vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Constructor sin argumentos.
     */
    public VacacionTemporizador() {
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
        LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":",
                UtilFechas.formatearLargo(new Date())));
        ParametroGlobal pNemonicoCorreoAdm = null;
        Long contadorRegistros;
        final List<String> errores = new FastTable<String>().shared();
        try {
            pNemonicoCorreoAdm = parametroGlobalDao.buscarPorNemonico(
                    ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.getCodigo());
            List<InstitucionEjercicioFiscal> instituciones = administracionServicio.listarInstitucionesVigentes();
            List<VacacionParametro> parametros = vacacionParametroDao.buscarVigente();
            List<DistributivoDetalle> servidores;
            for (InstitucionEjercicioFiscal ief : instituciones) {
                Date ultimaFecha = determinarUltimaFechaEjecutada();
                if (ultimaFecha == null) {
                    ultimaFecha = new SimpleDateFormat("dd/MM/yyyy").parse("30/04/2017");
                }
                Date fechaProceso = UtilFechas.truncarFecha(UtilFechas.sumarDias(ultimaFecha, 2)); //ultima fecha + 1 dia
                Date fechaTope = UtilFechas.truncarFecha(new Date());
                while (fechaProceso.compareTo(fechaTope) <= 0) {
                    System.out.println("FECHA PROCESO:::" + UtilFechas.formatear(fechaProceso));
                    contadorRegistros = 0L;
                    VacacionProceso vproceso = new VacacionProceso();
                    vproceso.setFecha(fechaProceso);
                    vproceso.setFechaCreacion(new Date());
                    vproceso.setUsuarioCreacion("auto");
                    vproceso.setVigente(Boolean.TRUE);
                    vproceso.setNumeroRegistros(0L);
                    vproceso.setInstitucionEjercicioFiscal(ief);
                    vacacionServicio.guardarFechaProceso(vproceso, true);
                    for (final VacacionParametro vp : parametros) {
                        int fila = 0;
                        servidores = distributivoDetalleDao.buscarParaVacaciones(vp.getRegimenLaboral().getId(),
                                ief.getId(), fila, TOTAL_LOTE_SERVIDORES);
                        int mesActual = Integer.valueOf(new SimpleDateFormat("MM").format(fechaProceso));
                        contadorRegistros += servidores.size();
                        while (!servidores.isEmpty()) {
                            System.out.println(UtilFechas.formatear(fechaProceso) + ":"
                                    + vp.getRegimenLaboral().getNombre() + ":" + servidores.size());

                            System.out.println("....verificando");
                            vacacionServicio.verificarVacaciones(servidores);
                            System.out.println("....mensual");

                            errores.addAll(vacacionServicio.ejecutarAcreditacionDiaria(servidores, vp, fechaProceso,
                                    mesActual));
                            System.out.println("....anual");
                            errores.addAll(vacacionServicio.ejecutarAcreditacionPorAniversario(servidores, vp,
                                    fechaProceso, ief));

                            fila = fila + TOTAL_LOTE_SERVIDORES;
                            servidores = distributivoDetalleDao.buscarParaVacaciones(vp.getRegimenLaboral().getId(),
                                    ief.getId(), fila, TOTAL_LOTE_SERVIDORES);
                            contadorRegistros += servidores.size();
                        }
                        //System.out.println("FINALIZANDO BLOQUEO: " + vp.getRegimenLaboral().getNombre());
                        if (contadorRegistros > 0) {
                            vproceso.setNumeroRegistros(contadorRegistros);
                            vacacionServicio.guardarFechaProceso(vproceso, false);
                        }
                    }
                    fechaProceso = UtilFechas.sumarDias(fechaProceso, 2);
                }
            }
            vacacionServicio.finalizarCalculoVacacion();
        } catch (Exception e) {
            LOG.log(Level.INFO, "Problemas al ejecutar el Timmer Vacaciones==>{0}", e);
        } finally {
            try {
                if (pNemonicoCorreoAdm != null) {
                    notificarCulminacionProceso(errores, pNemonicoCorreoAdm);
                }
            } catch (DaoException ex) {
                Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception a) {
                LOG.log(Level.INFO, "PROBLEMAS EN LA NOTIFICACION DE MENSAJES/{0}", a);
                try {
                    throw new Exception(a);
                } catch (Exception ex) {
                    Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Obtiene la ultima fecha que se procesó la asistencia.
     *
     * @return fecha de la ultima ejecucion
     */
    private Date determinarUltimaFechaEjecutada() {
        Date ultimaFecha = null;
        try {
            List<VacacionProceso> lista = vacacionServicio.listarVacacionProcesoUltimaFecha();
            if (!lista.isEmpty()) {
                ultimaFecha = lista.get(0).getFecha();
            }
        } catch (ServicioException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE,
                    " No se puede obtener ultima fecha procesada", ex);
        }

        return ultimaFecha;
    }

    /**
     *
     * @param errores lista de errores
     * @param pNemonicoCorreoAdm codigo del parametro del correo del administrador del contrato
     * @throws Exception error general
     */
    private void notificarCulminacionProceso(final List<String> errores, final ParametroGlobal pNemonicoCorreoAdm)
            throws Exception {

        if (pNemonicoCorreoAdm != null) {
            String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
            if (to == null || to.length == 0) {
                to[0] = pNemonicoCorreoAdm.getValorTexto();
            }
            String asunto = UtilCadena.concatenar(NOMBRE_TEMPORIZADOR, ":",
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

            StringBuilder mensaje = new StringBuilder();
            if (errores.size() > 0) {
                mensaje.append("La ejecución del proceso " + NOMBRE_TEMPORIZADOR
                        + " presentó los siguientes errores:\n");
                mensaje.append("Servidores con Error:\n");
                for (String s : errores) {
                    mensaje.append(s).append("\n");
                }
            } else {
                mensaje.append("La ejecución del proceso automático de " + NOMBRE_TEMPORIZADOR
                        + " se ejecuto con éxito.\n");
                mensaje.append("Fecha:").append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
            }
            mensajeriaServicio.enviarMail(asunto, null, to, null, mensaje.toString(), null, null);
        }
    }

}
