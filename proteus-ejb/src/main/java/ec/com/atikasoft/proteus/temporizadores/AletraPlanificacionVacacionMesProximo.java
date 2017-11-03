/*
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

import ec.com.atikasoft.proteus.dao.PlanificacionVacacionDetalleDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.enums.RolesEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.MensajeriaServicio;
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
 * Genera un email de alerta a los usuarios con el ROL de "APROBADOR DE
 * VACACIONES" de cada unidad organizacional con los servidores que tienen
 * vacaciones planificadas del siguiente mes.
 *
 * @author Maikel Perez Martinez
 */
@Stateless
@LocalBean
public class AletraPlanificacionVacacionMesProximo {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(AletraPlanificacionVacacionMesProximo.class.getCanonicalName());

    /**
     * Nombre del temporizador.
     */
    private static final String NOMBRE_TEMPORIZADOR = "PLANIFICACIONES VACACIONES PROXIMO MES";

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
    private static final int HORA = 23;

    /**
     * Minutos de ejecución: 0 minutos.
     */
    private static final int MINUTOS = 59;

    /**
     * Segundos de ejecución: 00.
     */
    private static final int SEGUNDOS = 0;

    /**
     * Intervalo de la ejecución: 1440 = 24 horas.
     */
    private static final int INTERVALO_EN_MINUTOS = 43200; //30 dias

    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     *
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     *
     */
    @EJB
    private PlanificacionVacacionDetalleDao planificacionVacacionDetalleDao;

    /**
     * Constructor sin argumentos.
     */
    public AletraPlanificacionVacacionMesProximo() {
        super();
    }

    /**
     * Levanta el servicio.
     */
    public void iniciandoTimer() {
        deteniendoTimer();
        Calendar initialExpiration = Calendar.getInstance();
        initialExpiration.set(Calendar.DAY_OF_MONTH, DIA);
        initialExpiration.set(Calendar.HOUR_OF_DAY, HORA);
        initialExpiration.set(Calendar.MINUTE, MINUTOS);
        initialExpiration.set(Calendar.SECOND, SEGUNDOS);
        long intervalDuration = INTERVALO_EN_MINUTOS * 60 * 1000;
        intervalDuration = intervalDuration < 0 ? intervalDuration * -1 : intervalDuration;
        LOG.info(UtilCadena.concatenar("Iniciando Timer ", NOMBRE_TEMPORIZADOR, " ", UtilFechas.formatearLargo(initialExpiration.getTime()), ", con ", intervalDuration / 1000 / 60, "\" intervalo en minutos."));
        timerService.createTimer(initialExpiration.getTime(), intervalDuration, null);
        /*System.out.println("Corriendo a mano");
         try {
         notificacionMensualPlanificacionesAprobadas();
         System.out.println("FIN");
         } catch (ServicioException ex) {
         LOG.log(Level.SEVERE, null, ex);
         }
         System.out.println("--->");*/
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
     * @param timer Timer de ejecucion
     */
    @Timeout
    public void ejecutar(final Timer timer) {
        try {
            LOG.info(UtilCadena.concatenar("Ejecutando Temporizador ", NOMBRE_TEMPORIZADOR, ":", 
                    UtilFechas.formatearLargo(new Date())));
            notificacionMensualPlanificacionesAprobadas();
        } catch (ServicioException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera un email de alerta a los usuarios con el ROL de "APROBADOR DE
     * VACACIONES" de cada unidad organizacional con los servidores que tienen
     * vacaciones planificadas del siguiente mes.
     *
     * @throws ServicioException error en el servicio
     */
    public void notificacionMensualPlanificacionesAprobadas() throws ServicioException {
        //Fecha actual
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.set(Calendar.MONTH, 11);//MOCK
        //Mes siguiente
        calendar.add(Calendar.MONTH, 1);
        String nombreMes = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "Es"));

        //Dia inicial del mes
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        Date desde = calendar.getTime();

        //Dia final del mes
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        Date hasta = calendar.getTime();

        //Rol de aprobador
        String codigoRolAprobador = RolesEnum.APROBADOR_VACACIONES.getCodigo();
        Rol rolAprobador = null;
        if (codigoRolAprobador != null) {
            rolAprobador = admServicio.buscarRolPorCodigo(codigoRolAprobador);
        }
        if (rolAprobador == null) {
            throw new ServicioException("El Rol de aprobador de vacaciones no está configurado.");
        }
        //Unidades Organizacionales a analizar
        List<UnidadOrganizacional> unidadOrganizacionals = admServicio.listarUnidadOrganizacionalVigente();
        if (unidadOrganizacionals == null) {
            System.out.println("Sin Unidades Organizacionales");
            throw new ServicioException("Sin Unidades Organizacionales");
        }
        LOG.log(Level.FINE, "Se analizaran {0} Unidades Organizacionales", unidadOrganizacionals.size());

        //Lista para almacenar las planificaciones en cada iteracion
        List<PlanificacionVacacionDetalle> planificaciones = new ArrayList<PlanificacionVacacionDetalle>();

        //Servidor - Rol entidad
        List<RolServidor> rolServidorResult = null;

        //Temporal para utilizar en el ciclo
        Servidor servidor = null;

        //Asunto para los correos
        StringBuilder asunto = new StringBuilder("SIGEN: SERVIDORES CON VACACIONES PLANIFICADAS PARA ");
        asunto.append(nombreMes.toUpperCase());

        //Encabezado para el cuerpo de los mensajes
        StringBuilder encabezado = new StringBuilder("Estimados por favor considerar el siguiente listado de servidores que tienen registro en la planificación de vacaciones para <b>");
        encabezado.append(nombreMes);
        encabezado.append("</b>");

        //Lista con el detalle de servidores con planificaciones para el cuerpo del mensaje
        //Se resetea en cada iteracion
        //La lista va en este formato 
        //Nro. Cédula, Apellidos Nombres, Fecha Inicio, Fecha Fin
        //un servidor por linea
        StringBuilder listaPlanificacionServidor = null;

        /**
         * Para no hacer un envio inmediato de los correos y procesar todas las
         * Unidades Organizacionales sin interrupcion, se almenaran aqui los
         * correos que se generen y luego se enviaran
         */
        final Map<Long, Object> correos = new HashMap<Long, Object>();

        for (UnidadOrganizacional uo : unidadOrganizacionals) {
            //resetear la lista de planificaciones
            planificaciones.clear();
            listaPlanificacionServidor = new StringBuilder("");
            try {
                //Planificaciones aprobadas para el mes dado por desde y hasta y la unidad uo
                planificaciones.addAll(planificacionVacacionDetalleDao.planificacionesAprobadasParaMes(uo.getId(), desde, hasta));
                if (!planificaciones.isEmpty()) {
                    //Encontrar servidores con el rol aprobador de vacaciones en esta UO
                    rolServidorResult = rolServidorDao.buscarPorRol(codigoRolAprobador, uo.getCodigo());
                    if (rolServidorResult == null || rolServidorResult.isEmpty()) {
                        throw new ServicioException("No existe aprobador de vacaciones para la unidad organizacional " + uo.getRuta());
                    }

                    //Armando la lista de servidores con planificaciones para esta UO
                    System.out.println(rolServidorResult.size() + " Aprobadores encontrados ");
                    LOG.log(Level.FINE, "{0} Aprobadores encontrados ", rolServidorResult.size());
                    servidor = null;
                    listaPlanificacionServidor.append("<table width='100%' border='0'><thead><tr>");
                    listaPlanificacionServidor.append("<th style='text-align:center; width: 15px'>No</th>");
                    listaPlanificacionServidor.append("<th style='text-align:center'>Número de Cédula</th>");
                    listaPlanificacionServidor.append("<th style='text-align:center'>Apellidos Nombres</th>");
                    listaPlanificacionServidor.append("<th style='text-align:center'>Fecha Inicio</th>");
                    listaPlanificacionServidor.append("<th style='text-align:center'>Fecha Fin</th>");
                    listaPlanificacionServidor.append("<th style='text-align:center'>Duración (días)</th>");
                    listaPlanificacionServidor.append("</tr></thead><tbody>");
                    int registro = 1;
                    for (PlanificacionVacacionDetalle pvd : planificaciones) {
                        listaPlanificacionServidor.append("<tr>");
                        listaPlanificacionServidor.append("<td style='text-align:center'>");
                        listaPlanificacionServidor.append(registro++);
                        listaPlanificacionServidor.append("</td><td style='text-align:center'>");
                        servidor = pvd.getPlanificacionVacacion().getServidor();
                        listaPlanificacionServidor.append(servidor.getNumeroIdentificacion());
                        listaPlanificacionServidor.append("</td><td>");
                        listaPlanificacionServidor.append(servidor.getApellidosNombres());
                        listaPlanificacionServidor.append("</td><td style='text-align:center'>");
                        listaPlanificacionServidor.append(sdf.format(pvd.getFechaInicio()));
                        listaPlanificacionServidor.append("</td><td style='text-align:center'>");
                        listaPlanificacionServidor.append(sdf.format(pvd.getFechaFin()));
                        listaPlanificacionServidor.append("</td><td style='text-align:center'>");
                        listaPlanificacionServidor.append(pvd.getNumeroDias());
                        listaPlanificacionServidor.append("</td>");
                        listaPlanificacionServidor.append("</tr>");
                    }
                    listaPlanificacionServidor.append("</tbody></table>");

                    //Para validar los email
                    List<String> destinosLista = new ArrayList<String>();
                    for (RolServidor rs : rolServidorResult) {
                        if (rs.getServidor().getMail() != null) {
                            destinosLista.add(rs.getServidor().getMail());
                        }
                    }
                    if (destinosLista.isEmpty()) {
                        System.out.println(" No hay Aprobadores con Mail regitrado");
                        throw new ServicioException("No hay Aprobadores con Mail regitrado ");
                    }

                    if (destinosLista.size() < rolServidorResult.size()) {
                        System.out.println(" Existen Aprobadores sin Mail registrado");
                        LOG.log(Level.FINE, "Existen Aprobadores sin Mail registrado");
                    }

                    String[] destinos = new String[destinosLista.size()];
                    int i = 0;
                    for (String mail : destinosLista) {
                        destinos[i++] = mail;
                    }

                    //Solo almacenar el correo
                    correos.put(uo.getId(), new Object[]{destinos, listaPlanificacionServidor.toString()});

                } else {
                    LOG.log(Level.FINE, "Sin Planificaciones para el proximo mes");
                }

            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error procesando la Unidad Organizacional {0}", uo.getNombre());
                LOG.log(Level.SEVERE, null, ex);
            }
        }

        //Envio de los correos generados
        if (!correos.isEmpty()) {
            for (Map.Entry<Long, Object> co : correos.entrySet()) {
                Object[] data = (Object[]) co.getValue();
                String[] destinos = (String[]) data[0];
                String cuerpo = data[1].toString();

                mensajeriaServicio.enviarMail(asunto.toString(), null, destinos, null,
                        encabezado.append("<br><br>").append(cuerpo).toString(), null, null);
            }
        }
    }
}
