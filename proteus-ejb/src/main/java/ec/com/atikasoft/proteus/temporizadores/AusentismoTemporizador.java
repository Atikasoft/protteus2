/*
 *  AusentismoTemporizador.java
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
 *  27/12/2013
 *
 */
package ec.com.atikasoft.proteus.temporizadores;

import ec.com.atikasoft.proteus.dao.AsistenciaProcesoDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.AsistenciaProceso;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AsistenciaServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
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
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javolution.context.ConcurrentContext;

/**
 * Proceso automatico que se encarga de realizar:
 * <p>
 * - Detecta días de inasistencia de los servidores registrados en el distributivo - Determina la cantidad de minutos de
 * atraso ( llegar después de la hora de inicio de la jornada y/o salir antes de la hora de finalización de la jornada,
 * salir al almuerzo antes o entrar luego de la hora de finalización de la hora designada para el almuerzo. </p>
 * <p>
 * Cron debe ejecutarse diariamente.</p>
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft..ec>
 */
@Stateless
@LocalBean
public class AusentismoTemporizador {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(AusentismoTemporizador.class.getCanonicalName());

    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "CONTROL DE AUSENTISMO";

    /**
     * Indica que equivale a una falta, para cuando existe una sola timbrada, o no hay timbradas.
     */
//    private static final Long MINUTOS_FALTA = -1000L;
    /**
     * Injección del TimerService.
     */
    @Resource
    private TimerService timerService;

    /**
     * Mes de ejecución: .
     */
    private static final int MES = 12;
    /**
     * Día de ejecución: .
     */
    private static final int DIA = 15;
    /**
     * Hora de ejecución: 23 horas.
     */
    private static final int HORA = 2;

    /**
     * Minutos de ejecución: 0 minutos.
     */
    private static final int MINUTOS = 00;

    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 0;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 1440;

    /**
     * Indica el total de servidores que se procesara la nomina en paginacion.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 1000;

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
     * Servicio de Asistencia.
     */
    @EJB
    private AsistenciaServicio asistenciaServicio;

    /**
     * Servicio de Vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;

    /**
     * DAO de Asistencia procesos.
     */
    @EJB
    private AsistenciaProcesoDao asistenciaProcesoDao;

    /**
     * Servicio de Distributivo Puesto.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * tiempoAlmuerzo Dao de parametros institucionales.
     */
    private List<String> listaObservados;

    /**
     * Constructor sin argumentos.
     */
    public AusentismoTemporizador() {
        super();
        listaObservados = new ArrayList<String>();
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
        int fila = 0;
        Date fechaProceso;
        Long contadorRegistros;
        LOG.info(UtilCadena.concatenar("Ejecutando... ", NOMBRE_TEMPORIZADOR, " - ", timer.getInfo(), ":", new Date()));
        try {
            BusquedaPuestoVO vo = new BusquedaPuestoVO();
            vo.setPuestoVacante(Boolean.FALSE);
            AsistenciaProceso asistenciaProceso;
            AsistenciaProceso ultimaFechaProceso = determinarUltimaFechaEjecutada();
            Date ultimaFecha = ultimaFechaProceso != null ? ultimaFechaProceso.getFecha() : null;
            Date fechaTope = new Date(); //UtilFechas.convertirFechaStringEnDate("2/05/2012", UtilFechas.FORMATO_FECHA); 
//              //System.out.println(" -----------------en metodo ejecutar--------------------------------- ==>"+lista.size());
            if (ultimaFecha == null) {
                LOG.info("NO SE PUEDE OBTENER LA ULTIMA FECHA DE EJECUCIÓN DEL PROCESO DE " + NOMBRE_TEMPORIZADOR);
                getListaObservados().add(UtilCadena.concatenar(
                        "NO SE PUEDE OBTENER LA ULTIMA FECHA DE EJECUCIÓN DEL PROCESO DE ", NOMBRE_TEMPORIZADOR,
                        fechaTope));
                ultimaFecha = UtilFechas.sumarDias(fechaTope, -1 - 3);
            }

            List<InstitucionEjercicioFiscal> listaEjercicios = administracionServicio.listarInstitucionesVigentes();
            if (!listaEjercicios.isEmpty()) {
                for (InstitucionEjercicioFiscal ie : listaEjercicios) {
                    vo.setIntitucionEjercicioFiscalId(ie.getId());
                    vo.setInicioConsulta(fila);
                    vo.setPuestoVacante(Boolean.FALSE);
                    vo.setObligadoTimbrar(Boolean.TRUE); //recupera servidores que deben marcar
                    vo.setFinConsulta(1);

                    List<DistributivoDetalle> listaServidor = null;// distributivoPuestoServicio.buscar(vo,false);

                    if (listaServidor.isEmpty()) {
                        LOG.info("NO HAY DISTRIBUTIVOS PARA PROCESAR!!!!!!");
                        getListaObservados().add(UtilCadena.concatenar("NO HAY DISTRIBUTIVOS PARA PROCESAR!!!!!!"));
                    } else {

                        fechaProceso = UtilFechas.sumarDias(ultimaFecha, 2);
                        while (UtilFechas.truncarFecha(fechaProceso).compareTo(UtilFechas.truncarFecha(fechaTope)) < 0) {
                            fila = 0;
                            //System.out.println(" procesando...........:::::>" + fechaProceso);
                            //System.out.println(" listaServidor:" + listaServidor.size());
                            contadorRegistros = 0L;
                            vo.setInicioConsulta(fila);
                            vo.setFinConsulta(TOTAL_LOTE_SERVIDORES);
                            listaServidor = null;//distributivoPuestoServicio.buscar(vo,false);
                            if (!listaServidor.isEmpty()) {
                                asistenciaProceso = new AsistenciaProceso();
                                asistenciaProceso.setFecha(fechaProceso);
                                asistenciaProceso.setNumeroRegistros(contadorRegistros);
                                guardarFechaProceso(asistenciaProceso, true);

                                ConcurrentContext ctx = ConcurrentContext.enter();
                                while (!listaServidor.isEmpty()) {
                                    final Set<DistributivoDetalle> segmento
                                            = Collections.synchronizedSet(new HashSet<>(listaServidor));
                                    final BusquedaPuestoVO voB = vo;
                                    final Date fechaProcess = fechaProceso;
                                    final AsistenciaProceso ap = asistenciaProceso;
                                    ctx.execute(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                //System.out.println(" antes de ejecutar ==>" + segmento.size());
                                                ejecutar(segmento, voB, fechaProcess, ap);

                                                //System.out.println(" despues de ejecutar ==>" + segmento.size());
                                            } catch (ServicioException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });
                                    contadorRegistros += (long) segmento.size();
                                    fila += TOTAL_LOTE_SERVIDORES;
                                    vo.setInicioConsulta(fila);
                                    listaServidor = null;//distributivoPuestoServicio.buscar(vo,false);
                                }

                                if (contadorRegistros > 0) {
                                    asistenciaProceso.setNumeroRegistros(contadorRegistros);
                                    guardarFechaProceso(asistenciaProceso, false);
                                } else {
                                    LOG.info(UtilCadena.concatenar(getClass().getName(),
                                            " No se encontraron registros que procesar para la fecha " + UtilFechas.
                                            formatear(fechaProceso)));
                                }
                                fechaProceso = UtilFechas.sumarDias(fechaProceso, 2);
                            } else {
                                getListaObservados().add(UtilCadena.concatenar("No hay registros para procesar la fecha:" + UtilFechas.
                                        formatear(fechaProceso)));
                            }
                        }
                    }
                }
            } else {
                LOG.info("NO HAY INSTITUCION - EJERCICIO FISCAL VIGENTE PARA PROCESAR!!!!!!..No se puede continuar...");
                getListaObservados().add(UtilCadena.concatenar(
                        "NO HAY INSTITUCION - EJERCICIO FISCAL VIGENTE PARA PROCESAR!!!!!!"));
            }
        } catch (ServicioException e) {
            LOG.info(UtilCadena.concatenar("Error obteniendo institucion ejercicio fiscal o distributivo ", e));
        } finally {
            try {
                ParametroGlobal pNemonicoCorreoAdm
                        = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA.
                                getCodigo());
                notificarCulminacionProceso(listaObservados, pNemonicoCorreoAdm, listaObservados.size());
//                for(String m: listaObservados){
//                    //System.out.println("..."+ m);
//                }
            } catch (Exception ex) {
                LOG.log(Level.INFO, "PROBLEMAS EN LA NOTIFICACION DE MENSAJES/{0}", ex);
                Logger.getLogger(AusentismoTemporizador.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    throw new Exception(ex);
                } catch (Exception e) {
                    Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

    /**
     * Método para determinaryb calcular ausentismo.-----------id de proceso de asistencia
     *
     * @param lista lista de Distributivos
     * @param vo
     * @param fechaProceso
     * @param ap
     * @throws ServicioException ServicioException
     */
    public void ejecutar(final Set<DistributivoDetalle> lista, BusquedaPuestoVO vo, final Date fechaProceso,
            final AsistenciaProceso ap) throws ServicioException {
        try {
            //System.out.println(" ejecutando......."+lista.size()+" fecha proceso:"+UtilFechas.formatear(fechaProceso));
            for (DistributivoDetalle reg : lista) {
                if (reg.getServidor() == null) {
                    continue;
                }
                if (reg.getServidor().getCodigoEmpleado() == null) {
                    getListaObservados().add(UtilCadena.concatenar("Sin codigo de Empleado para asistencia: ", reg.
                            getServidor().getNumeroIdentificacion()));
                    continue;
                }
                if (reg.getServidor().getJornada() == null) {
                    getListaObservados().add(UtilCadena.concatenar("Sin jornada laboral: ", reg.getServidor().
                            getNumeroIdentificacion()));
                    continue;
                }
                if (reg.getServidor().getHoraEntrada() == null) {
                    getListaObservados().add(UtilCadena.concatenar("Sin hora de entrada: ", reg.getServidor().
                            getNumeroIdentificacion()));
                    continue;
                }
                //System.out.println(" ===> dentro del distributivo......."+reg.getServidor().getNumeroIdentificacion()+" fecha proceso:"+UtilFechas.formatear(fechaProceso));
                //Obtener timbradas para procesar
                List<Asistencia> listaTimbradasAsistencia = recuperarTimbradasPorFechaYEmpleado(
                        reg.getServidor().getCodigoEmpleado(), fechaProceso);
                //System.out.println(" lista timbradas:" + listaTimbradasAsistencia.size());
                if (listaTimbradasAsistencia.isEmpty()) {
                    getListaObservados().add(UtilCadena.concatenar("Sin timbradas: ", reg.getServidor().
                            getNumeroIdentificacion(), " fecha:", UtilFechas.formatear(fechaProceso)));
                    continue;
                }
                boolean esDiaDescanso = vacacionServicio.esFechaLaborable(fechaProceso, reg.getEscalaOcupacional().
                        getNivelOcupacional().getRegimenLaboral().getId());
                for (Asistencia a : listaTimbradasAsistencia) {
                    //System.out.println(" enviando a calcular:"+reg.getServidor().getNumeroIdentificacion()+":esdesacanso:"+esDiaDescanso+":fecha:"+UtilFechas.formatear(fechaProceso));
                    asistenciaServicio.calcularGuardarAusentismo(a, reg.getDistributivo().getInstitucionEjercicioFiscal().
                            getInstitucion().getId(),
                            ap, esDiaDescanso, reg.getEscalaOcupacional());
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(AusentismoTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioException ex) {
            Logger.getLogger(AusentismoTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Obtiene la ultima fecha que se procesó la asistencia.
     *
     * @return
     */
    private AsistenciaProceso determinarUltimaFechaEjecutada() {
        try {
            List<AsistenciaProceso> lista = asistenciaServicio.listarAsistenciaProcesoUltimaFecha();
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(AusentismoTemporizador.class.getName()).log(Level.SEVERE,
                    " No se puede obtener ultima fecha procesada", ex);
        }

        return null;
    }

    /**
     * Registra en la base de datos la fecha del proceso que si presentó registros de asistencia.
     *
     * @param asistenciaProceso
     * @param esNuevo
     */
    public void guardarFechaProceso(AsistenciaProceso asistenciaProceso, boolean esNuevo) {
        try {
            if (esNuevo) {
                asistenciaProceso.setUsuarioCreacion("Automático");
                asistenciaProceso.setFechaCreacion(new Date());
                asistenciaProceso.setVigente(Boolean.TRUE);
                asistenciaProceso.setNomina(null);
                asistenciaProcesoDao.crear(asistenciaProceso);
            } else {
                asistenciaProcesoDao.actualizar(asistenciaProceso);
            }
        } catch (DaoException e) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al guardar la fecha del proceso", e));
        }
    }

    /**
     * @param codigoEmpleado
     * @param fecha
     * @return
     */
    private List<Asistencia> recuperarTimbradasPorFechaYEmpleado(final Long codigoEmpleado, final Date fecha) {
        try {
            return asistenciaServicio.listarAsistenciaPorFechaYEmpleado(codigoEmpleado, fecha);
        } catch (ServicioException e) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al recuperar las timbradas por fecha ", e));
        }
        return null;
    }

    /**
     * Notifica la culminación del proceso, mediante el envío de mail.
     *
     * @param errores lista de errores del proceso
     * @param pNemonicoCorreoAdm correo del administrador del sistema
     * @param numError numero de errores presentados en el proceso.
     * @throws Exception
     */
    private void notificarCulminacionProceso(final List<String> errores,
            final ParametroGlobal pNemonicoCorreoAdm, int numError) throws Exception {

        if (pNemonicoCorreoAdm != null) {
            String[] to = pNemonicoCorreoAdm.getValorTexto().split(",");
            String asunto = UtilCadena.concatenar(NOMBRE_TEMPORIZADOR, ":",
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

            StringBuilder mensaje = new StringBuilder();
            if (numError > 0) {
                mensaje.append(
                        "La ejecución del proceso  " + NOMBRE_TEMPORIZADOR + " presentó las siguientes observaciones:\n\n");
                for (String s : errores) {
                    mensaje.append(s).append("\n");
                }
            } else {
                mensaje.append(
                        "La ejecucion del proceso automático " + NOMBRE_TEMPORIZADOR + "  se ejecuto con éxito.\n");
                mensaje.append("Fecha:").append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
            }
//            mensajeriaServicio.enviarMail(asunto, null, to, null, mensaje.toString(), null, null);
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
