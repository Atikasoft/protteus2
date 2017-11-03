/*
 *  VacacionSolicitudControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.VacacionSolicitudHelper;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionSolicitudDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.pdf.MarcaAgua;
import ec.com.atikasoft.proteus.pdfs.GenerarSolicitudVacacion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * VacacionSolicitud
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionSolicitudBean")
@ViewScoped
public class VacacionSolicitudControlador extends BaseControlador {

    /**
     *
     */
    private StreamedContent pdf;

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionSolicitudControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/solicitud_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_solicitud_vacacion.jsf";

    /**
     * Observación de planificación de vacacion anual.
     */
    public static final String OBSERVACION_PLANIFICACION_ANUAL
            = "Confirmación de inicio de ejecución de vacación según planificación anual.";

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_INDEX = "/pages/portal_rrhh.jsf";

    /**
     * Constante para observacion de actualizacion de saldo de vacaciones.
     */
    public static final String OBSERVACION_SALDO_VACACION = "DESCUENTO POR SOLICITUD DE ";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;

    /**
     *
     */
    @EJB
    private VacacionSolicitudDao vacacionSolicitudDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{vacacionSolicitudHelper}")
    private VacacionSolicitudHelper vacacionSolicitudHelper;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Constante de variable TIMER para la observacion.
     */
    public static final String TIMER_OBSERVACION_ACUMULACION_VACACION_PERIODO
            = "ACREDITACIÓN PERIODICA DE MINUTOS DE VACACIONES.";

    @Override
    @PostConstruct
    public void init() {
        buscar();
        iniciarComboTipoVacacion();
        iniciarComboEstadoVacacion();
    }

    /**
     *
     * @return
     */
    public String guardar() {
        try {
            if (vacacionSolicitudHelper.getEsNuevo()) {
                Long saldo = vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesEfectiva();
                Long saldoProporsional = vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesProporcial();
                Servidor s = vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
                Long saldoSolicitado = 0L;
                if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals("D")) {
                    determinarCantidadSolicitada();
                    saldoSolicitado = UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(
                            vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().charAt(0),
                            vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada(), s.getJornada());
                    vacacionSolicitudHelper.getVacacionSolicitud().setFecha(null);
                    vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(null);
                    vacacionSolicitudHelper.getVacacionSolicitud().setHoraFin(null);
                } else {
                    saldoSolicitado = UtilFechas.calcularDiferenciaMinutosEntreFechas(
                            vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio(),
                            vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin());
                    long saldoMinutos = (vacacionSolicitudHelper.getMinutosVacacionesSolicitadas() % (480 * 5));
                    if ((saldoMinutos + saldoSolicitado) >= (480 * 5)) {
                        saldoSolicitado = saldoSolicitado + 480 * 2;
                    }
                    vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(null);
                }

                if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(
                        TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())) {
                    if (saldoProporsional <= 0) {
                        mostrarMensajeEnPantalla("Servidor sin Saldo Proporcional de Vacaciones",
                                FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                    if (saldoProporsional < saldoSolicitado) {
                        mostrarMensajeEnPantalla("Servidor sin Saldo Proporcional de Vacaciones Suficiente",
                                FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                } else {
                    if (saldo <= 0) {
                        mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones", FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                    if (saldo < saldoSolicitado) {
                        mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente", FacesMessage.SEVERITY_ERROR);
                        return null;
                    }

                    Long saldoComprometido = validarVacacionesEnTramite();

                    if ((saldoComprometido + saldoSolicitado) > saldo) {
                        mostrarMensajeEnPantalla("Revise sus solicitudes en trámite, su saldo comprometido es "
                                + UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', saldoComprometido, s.getJornada())
                                + " días", FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                }

                if (validarVacacionesSolapadas()) {
                    mostrarMensajeEnPantalla(SOLICITUD_VACACION_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    return null;
                }
                if (existeSolicitudEnCurso()) {
                    mostrarMensajeEnPantalla("Existen solicitudes en curso.", FacesMessage.SEVERITY_WARN);
                    return null;
                }

                vacacionSolicitudHelper.getVacacionSolicitud().setId(null);
                vacacionSolicitudHelper.getVacacionSolicitud().setEstado(EstadoVacacionEnum.REGISTRADO.getCodigo());

                vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitadaMinutos(saldoSolicitado);
                vacacionSolicitudHelper.getVacacionSolicitud().setNombreUsuarioCreacion(obtenerNombreUsuario());
                vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitadaTexto(
                        UtilFechas.convertirMinutosA_ddHHmmPalabras(vacacionSolicitudHelper.getVacacionSolicitud()
                                .getCantidadSolicitadaMinutos(), s.getJornada()));

                PlanificacionVacacionDetalle planificacionVacacionDetalle = null;
                if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals("V")) {
                    if (!vacacionSolicitudHelper.getListaVacacionDetalles().isEmpty()) {
                        planificacionVacacionDetalle = vacacionSolicitudHelper.getListaVacacionDetalles().get(0);
                    }
                }
                vacacionServicio.guardarVacacionSolicitud(vacacionSolicitudHelper.getVacacionSolicitud(),
                        planificacionVacacionDetalle, obtenerUsuarioConectado());
                if (vacacionSolicitudHelper.getVacacionSolicitud().getArchivo() != null) {
                    vacacionServicio.guardarArchivoVacacionSolicitud(vacacionSolicitudHelper.getVacacionSolicitud(),
                            vacacionSolicitudHelper.getArchivoFile());
                }
                mostrarMensajeEnPantalla(SOLICITUD_VACACION_ENVIADA, FacesMessage.SEVERITY_INFO);
                vacacionSolicitudHelper.setVacacionSolicitudEditDelete(vacacionSolicitudHelper.getVacacionSolicitud());
                vacacionSolicitudHelper.setEsNuevo(Boolean.FALSE);

                if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo())) {
                    ejecutarComandoPrimefaces("establecerDiasVacacionPlanificada();");
                } else if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
                    ejecutarComandoPrimefaces("establecerDiasVacacionNoPlanificadaDiaDias();");
                }
                ejecutarComandoPrimefaces("confirmation.hide(); ");
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_ENVIAR_SOLICITUD, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar solicitud...", e);
        }
        return null;
    }

    /**
     *
     * @return
     */
    private boolean determinarCantidadSolicitada() {
        boolean isOk = true;
        String fechas = vacacionSolicitudHelper.getVacacionSolicitud().getDiasPlanificados();
        vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitada(0L);
        if (fechas == null || fechas.trim().isEmpty()) {
            isOk = false;
            mostrarMensajeEnPantalla("No se seleccionaron fechas", FacesMessage.SEVERITY_ERROR);
        } else {
            String[] fechasArr = fechas.split(",");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (String f : fechasArr) {
                try {
                    sdf.parse(f);
                } catch (Exception e) {
                    LOG.log(Level.INFO, "{0} No se pudo obtener la fecha {1}", new String[]{getClass().
                        getName(), f});
                    mostrarMensajeEnPantalla("No se pudo obtener la fecha a partir del formato dado", FacesMessage.SEVERITY_ERROR);
                    isOk = false;
                }
            }
            if (isOk) {
                long saldo = (vacacionSolicitudHelper.getMinutosVacacionesSolicitadas() % (480 * 5)) / 8 / 60;
                long dias = (long) fechasArr.length;
                // verificar por cada 5 dias sumas 2 dias mas.
                long diasAdicionales = ((dias + saldo) / 5);
                vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitada(dias + diasAdicionales * 2);

            }

        }
        return isOk;
    }

    public String iniciarEdicion() {
        try {
            VacacionSolicitud d = vacacionSolicitudDao.buscarPorId(vacacionSolicitudHelper.getVacacionSolicitudEditDelete().getId());
            iniciarDatosEntidad(d, Boolean.FALSE);
            vacacionSolicitudHelper.setVacacionSolicitud(d);
            vacacionSolicitudHelper.setEsNuevo(Boolean.FALSE);
            vacacionSolicitudHelper.setNombreArchivo(d.getArchivo() != null ? d.getArchivo().getNombre() : null);
            iniciarComboPeriodoVacacion();
            determinarSaldoDisponible();
            System.out.println(" >>> " + vacacionSolicitudHelper.getVacacionSolicitud().getDiasPlanificados());
            //permisoHora();
            //registrarVacacionesNoPlanificadas();
            //esTipoVacacionSeleccionada();

            /*if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(TipoVacacionEnum.VACACION_PANIFICADA.getCodigo())) {
             ejecutarComandoPrimefaces("establecerDiasVacacionPlanificada();");
             } else if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
             esVacacionNoPlanificada();
             ejecutarComandoPrimefaces("establecerDiasVacacionNoPlanificadaDiaDias();");
             }*/
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String iniciarNuevo() {

        vacacionSolicitudHelper.iniciador();
        iniciarDatosEntidad(vacacionSolicitudHelper.getVacacionSolicitud(), Boolean.TRUE);
        vacacionSolicitudHelper.setEsNuevo(Boolean.TRUE);
        vacacionSolicitudHelper.setArchivoCargado(new DefaultUploadedFile());
        vacacionSolicitudHelper.setNombreArchivo(null);
        vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados("");
        vacacionSolicitudHelper.setDiasSolicitados(0l);
        if (!iniciarDatosServidor()) {
            iniciarComboEstadoVacacion();
            iniciarComboPeriodoVacacion();
            if (!vacacionSolicitudHelper.getEsNuevo()
                    && vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(
                            TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo())) {
                buscarDetalles();
            }
        } else {
            return LISTA_ENTIDAD;
        }
        return FORMULARIO_ENTIDAD;
    }

    public String borrar() {
        try {
            iniciarDatosEntidad(vacacionSolicitudHelper.getVacacionSolicitudEditDelete(), Boolean.FALSE);
            vacacionSolicitudHelper.getVacacionSolicitudEditDelete().setVigente(Boolean.FALSE);
            vacacionServicio.actualizarVacacionSolicitud(
                    vacacionSolicitudHelper.getVacacionSolicitudEditDelete(), null, obtenerUsuarioConectado());
            vacacionSolicitudHelper.getListaVacacionSolicitudes().
                    remove(vacacionSolicitudHelper.getVacacionSolicitudEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * REVIERTE LA SOLICITUD DE VACACIONES QUE YA ESTÁ APROBADA
     *
     * @param desdeListaSolicitudes verifica si el llamado a este metodo se hizo desde la lista de solicitudes
     * @return
     */
    public String revertir(Boolean desdeListaSolicitudes) {
        VacacionSolicitud vs = vacacionSolicitudHelper.getVacacionSolicitud();
        UsuarioVO usuarioConectado = obtenerUsuarioConectado();
        if (vs.getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
            try {
                vs = vacacionServicio.revertirVacacionAprobada(vs, usuarioConectado);
                if (desdeListaSolicitudes) {
                    buscar();
                    vacacionSolicitudHelper.setVacacionSolicitud(null);
                } else {
                    vacacionSolicitudHelper.setVacacionSolicitud(vs);
                }

                mostrarMensajeEnPantalla("Solicitud de vacaciones revertida satisfactoriamente.", FacesMessage.SEVERITY_ERROR);

            } catch (Exception e) {
                e.printStackTrace();
                mostrarMensajeEnPantalla(
                        "Error al intentar revertir la solicitud de vacaciones",
                        FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error al intentar revertir la solicitud de vacaciones", e);
            }

        } else {
            mostrarMensajeEnPantalla(
                    "No puede revertir una solicitud que no ha sido aprobada",
                    FacesMessage.SEVERITY_ERROR);
        }
        ejecutarComandoPrimefaces("confirmRevertir.hide()");
        return null;
    }

    /**
     * Busca todos los registros en estado registrado deben mostrarse unicamente para el rol Aprobador de vacaciones
     *
     * @return
     */
    public String buscar() {
        try {
            vacacionSolicitudHelper.getListaVacacionSolicitudes().clear();
            vacacionSolicitudHelper.setListaVacacionSolicitudes(
                    vacacionServicio.listarTodosVacacionSolicitudPorServidor(obtenerUsuarioConectado().getServidor().getId()));

            vacacionSolicitudHelper.setMinutosVacacionesSolicitadas(
                    vacacionSolicitudDao.contarMinutosVacacionesSolicitadasYAprobadas(
                            vacacionSolicitudHelper.getVacacion().getServidorInstitucion().getId()));
            if (vacacionSolicitudHelper.getMinutosVacacionesSolicitadas() == null) {
                vacacionSolicitudHelper.setMinutosVacacionesSolicitadas(0l);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /*
     * Setea los detalles vigentes de la instancia de planificacion vacacion detalle del usuario conectado.
     */
    public void buscarDetalles() {
        try {
            vacacionSolicitudHelper.setPlanificacionesAprobadas(0);
            vacacionSolicitudHelper.getListaVacacionDetalles().clear();

            List<PlanificacionVacacionDetalle> lista
                    = vacacionServicio.listarTodosPlanificacionVacacionDetallePorServidorYEstado(
                            obtenerUsuarioConectado().getServidor().getId(), EstadoVacacionDetalleEnum.DISPONIBLE.
                            getCodigo());
            boolean planicacionActual = false;
            if (!lista.isEmpty()) {
                PlanificacionVacacionDetalle planificacion = lista.get(0);
                String[] fechas = planificacion.getDiasPlanificados().split(";");
                if (UtilFechas.obtenerAnio(new SimpleDateFormat("dd/MM/yyyy").parse(fechas[0])).
                        compareTo(UtilFechas.obtenerAnio(obtenerUsuarioConectado().getEjercicioFiscal().
                                        getFechaInicio())) == 0) {
                    planicacionActual = true;
                }
            }
            if (planicacionActual) {
                vacacionSolicitudHelper.setListaVacacionDetalles(lista);
            } else {
                vacacionSolicitudHelper.setListaVacacionDetalles(new ArrayList<PlanificacionVacacionDetalle>());
            }
            vacacionSolicitudHelper.setTotalPlanificado(0L);
            StringBuilder sb = new StringBuilder("");
            for (PlanificacionVacacionDetalle pvd : vacacionSolicitudHelper.getListaVacacionDetalles()) {
                vacacionSolicitudHelper.setTotalPlanificado(vacacionSolicitudHelper.getTotalPlanificado()
                        + pvd.getNumeroDias());
                if (pvd.getDiasPlanificados() != null) {
                    if (!sb.toString().isEmpty()) {
                        sb.append(",");
                    }
                    sb.append(pvd.getDiasPlanificados());
                }
                if (!pvd.getPlanificacionVacacion().getEstado().equals(EstadoPlanVacacionEnum.APROBADO.getCodigo())) {
                    vacacionSolicitudHelper.setPlanificacionesAprobadas(vacacionSolicitudHelper.getPlanificacionesAprobadas() + 1);
                }
            }
            vacacionSolicitudHelper.setTienePlanificaciones(!vacacionSolicitudHelper.getListaVacacionDetalles().isEmpty());
            vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(sb.toString());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda detalles", ex);
        }
    }

    /**
     * Permite dejar en estado no disponible el detalle de planificacion de vacaciones.
     *
     * @return
     */
    public String guardarDetallePlanificacion() {
        try {
            if (vacacionSolicitudHelper.getVacacionDetalle() != null) {
                vacacionSolicitudHelper.getVacacionSolicitud().setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
                vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitada(vacacionSolicitudHelper.
                        getVacacionDetalle().getNumeroDias());
                vacacionSolicitudHelper.getVacacionSolicitud().setId(null);
                /*vacacionSolicitudHelper.getVacacionSolicitud().setFechaInicio(vacacionSolicitudHelper.getVacacionDetalle().
                 getFechaInicio());
                 vacacionSolicitudHelper.getVacacionSolicitud().setFechaFin(vacacionSolicitudHelper.getVacacionDetalle().
                 getFechaFin());*/

                vacacionSolicitudHelper.getVacacionSolicitud().setTipo(TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo());

                vacacionSolicitudHelper.getVacacionSolicitud().setEstado(EstadoVacacionEnum.REGISTRADO.getCodigo());
                vacacionSolicitudHelper.getVacacionSolicitud().setObservacion(OBSERVACION_PLANIFICACION_ANUAL);
                vacacionSolicitudHelper.getVacacionSolicitud().setMinutosImputados(BigDecimal.ZERO);
                vacacionSolicitudHelper.getVacacionSolicitud().setSaldoVacacionesEfectiva(buscarListaSaldosVacaciones());
                Long saldo = vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesEfectiva();
                Servidor s = vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
                if (saldo <= 0) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vacacionSolicitudHelper.
                        getVacacionSolicitud().getTipoPeriodo().charAt(0), saldo, s.getJornada())
                        < vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada()) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente", FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                Long saldoComprometido = validarVacacionesEnTramite();
                Long minsolicitados = UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(
                        vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().charAt(0),
                        vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada(), s.getJornada());
                if (saldoComprometido + minsolicitados > saldo) {
                    mostrarMensajeEnPantalla("Revise sus solicitudes en trámite. Su saldo comprometido es "
                            + UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', saldoComprometido, s.getJornada()),
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (validarVacacionesSolapadas()) {
                    mostrarMensajeEnPantalla(SOLICITUD_VACACION_EXISTENTE, FacesMessage.SEVERITY_WARN);
                    return null;
                }
                vacacionSolicitudHelper.getVacacionDetalle().setEstado(
                        EstadoVacacionDetalleEnum.NO_DISPONIBLE.getCodigo());
                iniciarDatosEntidad(vacacionSolicitudHelper.getVacacionDetalle(), Boolean.FALSE);

                vacacionServicio.guardarVacacionSolicitud(vacacionSolicitudHelper.getVacacionSolicitud(),
                        vacacionSolicitudHelper.getVacacionDetalle(), obtenerUsuarioConectado());
                vacacionSolicitudHelper.setEsNuevo(Boolean.FALSE);

                if (vacacionSolicitudHelper.getVacacionSolicitud().getArchivo() != null) {
                    vacacionServicio.guardarArchivoVacacionSolicitud(vacacionSolicitudHelper.
                            getVacacionSolicitud(), vacacionSolicitudHelper.getArchivoFile());
                }

                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("confirmation.hide()");
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al actualizar detalles de planificacion de vacaciones", e);
        } catch (ServidorException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al actualizar detalles de planificacion de vacaciones", e);
        }
        return null;
    }

    /*
     * @param sinSaldo cuando hay que registrar un nuevo registro de vacacion para saldos.
     * @param det
     * @throws Exception 
     */
    public void guardarDetalleVacacion(final VacacionDetalle det, final boolean sinSaldo) throws Exception {
        try {
            det.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            if (sinSaldo) {
                iniciarDatosEntidad(det.getVacacion(), Boolean.TRUE);
                vacacionServicio.guardarVacacion(det.getVacacion());
            } else {
                iniciarDatosEntidad(det.getVacacion(), Boolean.FALSE);
                vacacionServicio.actualizarVacacion(det.getVacacion());
            }
            vacacionServicio.guardarVacacionDetalle(det);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(SOLICITUD_VACACION_ERROR_REGISTRO_SALDO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar saldo de vacaciones", e);
            throw new Exception(e);
        }
    }

    /**
     * buscar listado de saldo de vacaciones por periodos.
     *
     * @return
     */
    private Long buscarListaSaldosVacaciones() {
        Long saldo = 0L;
        try {
            Vacacion v = vacacionDao.buscarPorServidor(vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getId());
            if (v != null) {
                vacacionSolicitudHelper.setVacacion(v);
                saldo += v.getSaldo();
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
        return saldo;
    }

    /**
     * buscar listado de vacaciones acumuladas por periodos. debe estar ordenada en forma ascendete por fecha de inicio.
     *
     * @return
     */
    private List<Vacacion> buscarTodasVacaciones() {
        try {
            List<Vacacion> vacaciones = new ArrayList<Vacacion>();
            Vacacion v = vacacionDao.buscarPorServidor(vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getId());
            if (v != null) {
                vacaciones.add(v);
            }
            return vacaciones;
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
        return null;
    }

    /**
     * Valida que no existan solicitudes en curso.
     *
     * @return
     */
    private Boolean existeSolicitudEnCurso() {
        Boolean hay = false;
        try {
            List<VacacionSolicitud> solicitudes = vacacionSolicitudDao.buscarTodosPorServidorEnTramite(vacacionSolicitudHelper.
                    getVacacionSolicitud().getServidorInstitucion().getServidor().getId());
            if (!solicitudes.isEmpty()) {
                hay = true;
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion de la solicitud de vacaciones", e);

        }
        return hay;

    }

    /**
     * metodo para validar si ya existe una solicitud para el servidor dentro del mismo periodo que se encuentre
     * vigente.
     *
     * @return hay Boolean.
     */
    public Boolean validarExistenciaSolicitudServidor() {
        Boolean hay = true;
        try {
            vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().clear();
            vacacionSolicitudHelper.setListaVacacionSolicitudCodigo(
                    vacacionServicio.listarTodosVacacionSolicitudPorServidorTipoFechaInicio(vacacionSolicitudHelper.
                            getVacacionSolicitud().getServidorInstitucion().getServidor().getId(),
                            vacacionSolicitudHelper.getVacacionSolicitud().getTipo(),
                            null, null, null));
            if (vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().isEmpty()) {
                hay = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion de la solicitud de vacaciones", ex);
        }
        return hay;
    }

    /**
     * metodo para validar si ya existe una solicitud para el servidor dentro del mismo periodo que se encuentre
     * vigente.
     *
     * @return hay Boolean.
     */
    public Boolean validarVacacionesSolapadas() {
        Boolean hay = true;
        try {
            vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().clear();
            vacacionSolicitudHelper.setListaVacacionSolicitudCodigo(
                    vacacionServicio.listarVacacionSolicitudPorServidorEnTramitePorFecha(vacacionSolicitudHelper.
                            getVacacionSolicitud().getServidorInstitucion().getServidor().getId(),
                            null, null));
            if (vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().isEmpty()) {
                hay = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion de la solicitud de vacaciones", ex);
        }
        return hay;
    }

    public Boolean validarTiempoPermiso() {
        Boolean error = false;
        try {
            Servidor s = vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
            vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().clear();
            vacacionSolicitudHelper.setListaVacacionSolicitudCodigo(
                    vacacionServicio.listarVacacionSolicitudEnTramitePorServidor(s.getId()));
            Long totalMin = 0l;
            for (VacacionSolicitud vs : vacacionSolicitudHelper.getListaVacacionSolicitudCodigo()) {
                if (vs.getTipo().equals(TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())) {
                    totalMin += UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vs.getTipoPeriodo().charAt(0), vs.getCantidadSolicitada(), s.getJornada());
                }
            }
            totalMin += UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().charAt(0),
                    vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada(), s.getJornada());

            if (totalMin > UtilFechas.convertirEnMinutosPorTipoUnidadTiempo('D', vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getMaximosDiasTomarPermisos().longValue(), s.getJornada())) {
                error = true;
            }
        } catch (ServicioException ex) {
            error(getClass().getName(), "Error al procesar la validacion de la solicitud de vacaciones", ex);
            error = true;
        }
        return !error;
    }

    /**
     * metodo para validar si ya existe una solicitud para el servidor dentro del mismo periodo que se encuentre
     * vigente.
     *
     * @return hay Boolean.
     */
    public Long validarVacacionesEnTramite() {
        Long saldoComprometido = 0l;
        try {
            vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().clear();
            vacacionSolicitudHelper.setListaVacacionSolicitudCodigo(
                    vacacionServicio.listarVacacionSolicitudEnTramitePorServidor(
                            vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor().getId()));
            if (!vacacionSolicitudHelper.getListaVacacionSolicitudCodigo().isEmpty()) {
                for (VacacionSolicitud vs : vacacionSolicitudHelper.getListaVacacionSolicitudCodigo()) {
                    saldoComprometido = saldoComprometido + UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(
                            vs.getTipoPeriodo().charAt(0), vs.getCantidadSolicitada(), vs.getServidorInstitucion().getServidor().getJornada());
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de saldo comprometido ", ex);
        }
        return saldoComprometido;
    }

    /**
     * Obtiene los datos del servidor conectado en la sesión.
     *
     * @return true si hay problemas
     */
    public boolean iniciarDatosServidor() {
        boolean problemas = true;
        try {
            vacacionSolicitudHelper.setUsuario(obtenerUsuarioConectado());
            List<ServidorInstitucion> lista
                    = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(vacacionSolicitudHelper
                            .getUsuario().getServidor().getNumeroIdentificacion());
            if (!lista.isEmpty()) {
                vacacionSolicitudHelper.getVacacionSolicitud().setServidorInstitucion(lista.get(0));
                vacacionSolicitudHelper.getVacacionSolicitud().setIdServidorInstitucion(lista.get(0).getId());
                if (vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion() == null) {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                    LOG.log(Level.INFO, "{0} El servidor no se encuentra registrado en la instituci\u00f3n",
                            getClass().getName());
                    vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
                    return problemas;
                } else {
                    vacacionDao.totalizarSaldosVacaciones(lista.get(0).getId());
                }
                if (!obtenerParametrizacionVacacion()) {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                    LOG.log(Level.INFO, "{0} El servidor no tiene parametrizacion de vacaciones", getClass().getName());
                    vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
                } else {
                    determinarSaldoDisponible();
                    problemas = false;
                }
                if (existeSolicitudEnCurso()) {
                    mostrarMensajeEnPantalla("message.vacaciones.solicitud.unica", FacesMessage.SEVERITY_ERROR);
                    return true;
                }
            } else {
                vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                LOG.log(Level.INFO, "{0} El servidor no se encuentra registrado en una instituci\u00f3n", getClass().
                        getName());

            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos servidor", ex);
        }
        return problemas;
    }

    /**
     * Permite obtener los parametros para la gestion de las vacaciones de acuerdo al regimen laboral del
     * servidor<br><p>
     *
     * @return true si existe la configuracion 1. Regimen Laboral se obtiene desde el detalle del distributivo a aprtir
     * del numero de documento del usuario<br/> 2. Con el regimen laboral Obtener parametrizacion de vacacion del
     * servidor </p>
     */
    public boolean obtenerParametrizacionVacacion() {
        boolean hayRegimenLab = false;
        try {
            RegimenLaboral regLaboral;
            if (vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion() != null) {
                DistributivoDetalle dd = obtenerUsuarioConectado().getDistributivoDetalle();
                if (dd != null) {
                    regLaboral = dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral();

                    List<VacacionParametro> listaParametros = vacacionServicio.
                            listarTodosVacacionParametroPorRegimenLaboral(regLaboral.getId());
                    if (!listaParametros.isEmpty()) {
                        for (VacacionParametro p : listaParametros) {
                            vacacionSolicitudHelper.getVacacionSolicitud().setVacacionParametro(p);
                            vacacionSolicitudHelper.getVacacionSolicitud().setIdVacacionParametro(p.getId());
                            hayRegimenLab = true;
                            break;
                        }
                    } else {
                        mostrarMensajeEnPantalla("No se puede encontrar la configuración para vacaciones anuales.",
                                FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda parametrizacion", ex);
        }
        return hayRegimenLab;
    }

    /**
     * Obtiene saldo de tabla de vacaciones. Setea el ultimo registro de vacaciones a una variable.
     */
    private void determinarSaldoDisponible() throws DaoException {
        obtenerRegistroSaldoVacacionServidor();
        if (vacacionSolicitudHelper.getVacacion().getId() == null) {
            //lista ordenada x fecha inicio asc
            List<Vacacion> listaVacaciones = buscarTodasVacaciones();
            if (!listaVacaciones.isEmpty()) {
                vacacionSolicitudHelper.setVacacion(listaVacaciones.get(listaVacaciones.size() - 1));
            }
        }
    }

    /**
     * Se obtiene saldo de vacaciones en minutos.
     *
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void obtenerRegistroSaldoVacacionServidor() throws DaoException {
        ServidorInstitucion si = vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion();
        if (si != null) {
            Vacacion v = vacacionDao.buscarPorServidor(vacacionSolicitudHelper.getVacacionSolicitud().
                    getServidorInstitucion().getId());
            vacacionSolicitudHelper.getVacacionSolicitud().setSaldoVacacionesEfectiva(0l);
            vacacionSolicitudHelper.setCadenaSaldo("0 Días");
            vacacionSolicitudHelper.setCadenaSaldoProporcional("0 Días");
            Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(v == null ? 0 : v.getSaldo(),
                    si.getServidor().getJornada());
            Integer saldoProporcional[] = UtilFechas.convertirMinutosA_ddHHmm(v == null ? 0 : v.getSaldoProporcional(),
                    si.getServidor().getJornada());
            vacacionSolicitudHelper.getVacacionSolicitud().setSaldoVacacionesEfectiva(v == null ? 0 : v.getSaldo());
            vacacionSolicitudHelper.getVacacionSolicitud().setSaldoVacacionesProporcial(v == null ? 0
                    : v.getSaldoProporcional());
            vacacionSolicitudHelper.setCadenaSaldo(saldo[0] + " Días " + saldo[1] + " Horas " + saldo[2] + " Minutos ");
            vacacionSolicitudHelper.setCadenaSaldoProporcional(saldoProporcional[0] + " Días " + saldoProporcional[1]
                    + " Horas " + saldoProporcional[2] + " Minutos ");
            vacacionSolicitudHelper.setTieneSaldoEfectivo(vacacionSolicitudHelper.getVacacionSolicitud().
                    getSaldoVacacionesEfectiva() > 0);
            vacacionSolicitudHelper.setTieneSaldoProporcional(vacacionSolicitudHelper.getVacacionSolicitud().
                    getSaldoVacacionesProporcial() > 0);

        }
    }

    /**
     * Método que valida las condiciones de Vacaciones.
     * <p>
     * - Tiene un mínimo de días por solicitar.<br/> - No sobrepasar el saldo actual.<br/> - Se imputan feriados y fines
     * de semana<br/> - Existe un minimo de meses para tener derecho a esta prestación.<br/> - Solo puede contar con el
     * saldo disponible no con el proporcional <br/>
     * </p>
     *
     * @param saldoEnUnidadTiempo
     * @return
     */
    public boolean validarCondicionesVacacion(BigDecimal saldoEnUnidadTiempo) {
        boolean continuar = false;
        long cantidad = vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada();
        VacacionParametro vp = vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro();

        if (vacacionSolicitudHelper.getTiempoEnEmpresa()[0] < 1
                && vacacionSolicitudHelper.getTiempoEnEmpresa()[1] < vp.getMinimoMesesDerechoVacacion()) {
            mostrarMensajeEnPantalla(SIN_DERECHO_VACACION, FacesMessage.SEVERITY_ERROR);
            return continuar;
        }
        if (cantidad < vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getMinimoDiasTomarVacacion()) {
            mostrarMensajeEnPantalla("La cantidad mínima de días no es correcta", FacesMessage.SEVERITY_WARN);

            return continuar;
        }
        if (cantidad > saldoEnUnidadTiempo.longValue()) {
            mostrarMensajeEnPantalla("La cantidad solicitada excede de días de vacación disponibles ",
                    FacesMessage.SEVERITY_WARN);
            return continuar;
        }
        continuar = true;
        return continuar;
    }

    /**
     * Método que valida las condiciones de Permisos.
     * <p>
     * - Existe un techo para la cantidad de permisos<br/> - No sobrepasar el (saldo actual + proporcional).<br/> - No
     * se imputan feriados ni fines de semana<br/> - No Existe un minimo de meses para tener derecho a esta
     * prestación.<br/> </p>
     *
     * @param tiempoSolicitado
     * @param saldoEnUnidadTiempo
     * @return
     */
    public boolean validarCondicionesPermiso(final BigDecimal tiempoSolicitado, final BigDecimal saldoEnUnidadTiempo) {
        boolean continuar = false;
        VacacionParametro vp = vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro();

        if (saldoEnUnidadTiempo.compareTo(tiempoSolicitado) < 0) {
            mostrarMensajeEnPantalla("La cantidad solicitada excede el tiempo disponible.", FacesMessage.SEVERITY_WARN);
            return continuar;
        }
        if (tiempoSolicitado.compareTo(new BigDecimal(vp.getMaximosDiasTomarPermisos())) > 0) {
            mostrarMensajeEnPantalla("La cantidad solicitada excede el tiempo reglamentario para Permisos que es de "
                    + vp.getMaximosDiasTomarPermisos() + " días.",
                    FacesMessage.SEVERITY_WARN);
            return continuar;
        }
        continuar = true;
        return continuar;
    }

    /**
     * Método que valida las condiciones de Adelanto de Vacaciones.
     * <p>
     * - No sobrepasar el (saldo actual + proporcional).<br/> - Se imputan feriados y fines de semana<br/> - No Existe
     * un minimo de meses para tener derecho a esta prestación.<br/> </p>
     *
     * @param tiempoSolicitado
     * @param saldoEnUnidadTiempo
     * @return
     */
    public boolean validarCondicionesAdelantoVacacion(final BigDecimal tiempoSolicitado,
            final BigDecimal saldoEnUnidadTiempo) {
        boolean continuar = false;
        VacacionParametro vp = vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro();
        if ((saldoEnUnidadTiempo).compareTo(tiempoSolicitado) < 0) {
            mostrarMensajeEnPantalla("La cantidad solicitada excede el tiempo disponible.", FacesMessage.SEVERITY_WARN);
            return continuar;
        }
        System.out.println("tiempoSolicitado.intValue():" + tiempoSolicitado.intValue());
        System.out.println("vp.getMinimoDiasTomarVacacion():" + vp.getMinimoDiasTomarVacacion());
        System.out.println("saldoEnUnidadTiempo.intValue():" + saldoEnUnidadTiempo.intValue());
        if (saldoEnUnidadTiempo.intValue() < vp.getMinimoDiasTomarVacacion()
                && tiempoSolicitado.intValue() < saldoEnUnidadTiempo.intValue()) {
            mostrarMensajeEnPantalla("Debe solicitar mínimo " + saldoEnUnidadTiempo.intValue() + " días.", FacesMessage.SEVERITY_ERROR);
            return continuar;
        } else if (saldoEnUnidadTiempo.intValue() > vp.getMinimoDiasTomarVacacion()
                && tiempoSolicitado.intValue() < vp.getMinimoDiasTomarVacacion()) {
            mostrarMensajeEnPantalla("Debe solicitar mínimo " + vp.getMinimoDiasTomarVacacion() + " días.", FacesMessage.SEVERITY_ERROR);
            return continuar;
        }
        continuar = true;
        return continuar;
    }

    /**
     * Determina los dias que se deben agregar por fines de semana Setea la fecha fin y cantidad solicitada.
     *
     */
    public void imputarFinesSemana() {
        BigDecimal minutos = BigDecimal.ZERO;
        if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(
                TipoVacacionEnum.VACACION_NO_PLANIFICADAS.getCodigo())
                && vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getImputarFinSemanaVacacion()) {
            minutos = new BigDecimal(UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vacacionSolicitudHelper.
                    getVacacionSolicitud().getTipoPeriodo().charAt(0),
                    vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada(),
                    vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor().getJornada()));
            minutos = UtilFechas.calcularPromedioDiarioEnMinutosCargoVacacion(minutos.longValue(),
                    vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor().getJornada());
            if (minutos.compareTo(new BigDecimal(99999)) > 0) {
                minutos = new BigDecimal(99999);
            }
        }
        vacacionSolicitudHelper.getVacacionSolicitud().setMinutosImputados(minutos);
    }

    /**
     * Calcula fecha fin en funcion del tipo de vacacion y tipo de periodo.
     */
    public void calcularFechaFinPorTipoPeriodo() {
        BigDecimal tiempoSolicitadoEnDias = BigDecimal.ZERO; //en dias
        Calendar c = Calendar.getInstance();
        boolean continuar = false;
        BigDecimal saldoEnUnidadTiempo = BigDecimal.ZERO;
        Long minutosSolicitados = 0l;
        vacacionSolicitudHelper.setMsgFeriados(Boolean.FALSE);
        vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
        c.setTime(vacacionSolicitudHelper.getVacacionSolicitud().getFecha());
        if (vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada().equals(0L)) {
            mostrarMensajeEnPantalla("La cantidad debe ser mayor a cero(0)", FacesMessage.SEVERITY_ERROR);
            return;
        }

        Servidor s = vacacionSolicitudHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
        if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo() != null
                && vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo() != null) {

            if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(
                    TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo())) {
                vacacionSolicitudHelper.getVacacionSolicitud().setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
            }
            char tipo = vacacionSolicitudHelper.getVacacionSolicitud().getTipo().charAt(0);
            char a = vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().charAt(0);
            vacacionSolicitudHelper.getVacacionSolicitud().setMinutosSolicitados(
                    UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(
                            a, vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada(), s.getJornada()));
            if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(
                    TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())
                    && !vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals(
                            PeriodoVacacionEnum.DIAS.getCodigo())) {
                if (!UtilFechas.validarFechaInicioFin(vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio(),
                        vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin())) {
                    mostrarMensajeEnPantalla(COMPARAR_FECHA, FacesMessage.SEVERITY_ERROR);
                    return;
                }
                Time ti = new Time(vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio().getTime());
                Time tf = new Time(vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin().getTime());
                minutosSolicitados = UtilFechas.calcularMinutosEntreHoras(ti, tf);
                if (!minutosSolicitados.equals(vacacionSolicitudHelper.getVacacionSolicitud().getMinutosSolicitados())) {
                    mostrarMensajeEnPantalla("El tiempo entre hora Inicio y hora Fin debe corresponder con la "
                            + "cantidad solicitada", FacesMessage.SEVERITY_ERROR);
                    return;
                }
            }
            switch (a) {
                case 'D':
                    tiempoSolicitadoEnDias = new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().
                            getCantidadSolicitada());
                    saldoEnUnidadTiempo = new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesEfectiva()
                            / (UtilFechas.MIN_EN_HORA * s.getJornada()));
                    break;
                case 'H':
                    tiempoSolicitadoEnDias = (new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().
                            getCantidadSolicitada()).divide(new BigDecimal(s.getJornada()), 2,
                                    RoundingMode.HALF_DOWN)).setScale(2, BigDecimal.ROUND_FLOOR);
                    if (tiempoSolicitadoEnDias.compareTo(BigDecimal.ONE) >= 0) {
                        mostrarMensajeEnPantalla("Haga su solicitud en DÍAS", FacesMessage.SEVERITY_ERROR);
                        return;
                    }
                    saldoEnUnidadTiempo = new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesEfectiva()
                            / s.getJornada());
                    break;
                case 'M':
                    tiempoSolicitadoEnDias = (new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().
                            getCantidadSolicitada()).divide(new BigDecimal(
                                            s.getJornada() * UtilFechas.MIN_EN_HORA), 2, RoundingMode.HALF_DOWN)).setScale(
                                    2, BigDecimal.ROUND_FLOOR);
                    if (tiempoSolicitadoEnDias.compareTo(BigDecimal.ONE) >= 0) {
                        mostrarMensajeEnPantalla("Haga su solicitud en DÍAS", FacesMessage.SEVERITY_ERROR);
                        return;
                    }
                    saldoEnUnidadTiempo = new BigDecimal(vacacionSolicitudHelper.getVacacionSolicitud().getSaldoVacacionesEfectiva());
                    break;
            }
            c.add(Calendar.DATE, tiempoSolicitadoEnDias.intValue());
            //vacacionSolicitudHelper.getVacacionSolicitud().setFechaFin(c.getTime());
            switch (tipo) {
                case 'V':
                    continuar = validarCondicionesVacacion(saldoEnUnidadTiempo);
                    break;
                case 'A':
                    continuar = validarCondicionesAdelantoVacacion(tiempoSolicitadoEnDias, saldoEnUnidadTiempo);
                    break;
                case 'P':
                    continuar = validarCondicionesPermiso(tiempoSolicitadoEnDias, saldoEnUnidadTiempo);
                    break;
            }
            if (continuar) {
            }
        }

    }

    /**
     * Obtiene fecha fin contando el numero de dias feriados y fines de semana. Valida en función de la cantidad
     * solicitada calculada en días
     *
     * @param cantidadEnDias cantidad solicitada en días
     * @return fecha fin
     */
    public Date obtenerFechaFinPermiso(BigDecimal cantidadEnDias) {
        Date ff = null /*UtilFechas.truncarFecha(vacacionSolicitudHelper.getVacacionSolicitud().getFechaInicio())*/;
        Calendar c = Calendar.getInstance();
        int cantidadSolDias = cantidadEnDias.intValue();

        if (cantidadEnDias.compareTo(new BigDecimal(cantidadEnDias.intValue())) > 0) {
            cantidadSolDias++;
        }
        vacacionSolicitudHelper.setMsgFeriados(Boolean.FALSE);
        c.setTime(ff);
        while (cantidadSolDias > 0) {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    | c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                vacacionSolicitudHelper.setMsgFeriados(Boolean.TRUE);
            } else if (esFeriado(c.getTime())) {
                vacacionSolicitudHelper.setMsgFeriados(Boolean.TRUE);
            } else {
                cantidadSolDias--;
            }
            c.add(Calendar.DATE, 1);
        }
        c.add(Calendar.DATE, -1);
        while (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                | c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                | esFeriado(c.getTime())) {
            c.add(Calendar.DATE, 1);
        }
        return c.getTime();
    }

    /**
     * Verifica si la fecha corresponde a un feriado
     *
     * @param ff fecha a verificar
     * @return true si la fecha es un feriado
     */
    public boolean esFeriado(Date ff) {
        boolean hayFeriado = false;
        try {
            if (vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro() != null
                    && vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getIdRegimenLaboral() != null) {
                List<Feriado> listaFeriado = vacacionServicio.listarFeriadoPorFechaEjercicioFiscalYRegimen(ff,
                        obtenerUsuarioConectado().getEjercicioFiscal().getId(),
                        vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getIdRegimenLaboral());
                if (!listaFeriado.isEmpty()) {
                    hayFeriado = true;
                }
            } else {
                LOG.info("No existe parametrización de regimen laboral!!!!!");
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda feriados", ex);
        }
        return hayFeriado;
    }

    /**
     * Permite imprimir la solicitud de vacaciones para que el servidor firme y entregue a quien corresponda.
     *
     */
    public void imprimirSolicitudVacacion() {
        try {
            List<DistributivoDetalle> dds = distributivoDetalleDao.buscarPorServidor(
                    vacacionSolicitudHelper.getVacacionSolicitudEditDelete().getServidorInstitucion().getIdServidor());
            vacacionSolicitudHelper.getVacacionSolicitudEditDelete().setDistributivoDetalle(dds.get(0));
            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorNemonico(
                    ParametroInstitucionCatalogoEnum.LOGO_DE_INSTITUCION.getCodigo());

            String tipo = vacacionSolicitudHelper.getVacacionSolicitud().getTipo();
//            if (TipoVacacionEnum.VACACION_NO_PLANIFICADAS.getCodigo().equals(tipo)
//                    || TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo().equals(tipo)) {
            // Vacaciones planificadas
            GenerarSolicitudVacacion generar = new GenerarSolicitudVacacion(
                    vacacionSolicitudHelper.getVacacionSolicitudEditDelete(), pi.getArchivo().getArchivo(),
                    vacacionServicio);
            File archivo = generar.generar();
            ParametroGlobal repos = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.REPOS.getCodigo());
            File marcaAgua = new File(repos.getValorTexto() + File.separator + "reportes" + File.separator + "img"
                    + File.separator + "marca_agua.png");
            File pdfMarcaAgua = MarcaAgua.procesar(archivo, marcaAgua);

            pdf = new DefaultStreamedContent(new FileInputStream(pdfMarcaAgua), "application/pdf", "SOLICITUD_VACACION_"
                    + dds.get(0).getServidor().getNumeroIdentificacion() + ".pdf");

//            } else {
//                // Vacaciones no planificadas y anticipos.
//                GenerarSolicitudAnticipoVacacion generar = new GenerarSolicitudAnticipoVacacion(vacacionSolicitudHelper.
//                        getVacacionSolicitudEditDelete(), pi.getArchivo().getArchivo(), vacacionServicio);
//                File archivo = generar.generar();
//                pdf = new DefaultStreamedContent(new FileInputStream(archivo), "application/pdf", "SOLICITUD_PERMISOS_"
//                        + dds.get(0).getServidor().getNumeroIdentificacion() + ".pdf");
//            }
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de registro de solicitud de vacaciones "
                    + "UATH" + e.getMessage(), e);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo vacacion.
     */
    private void iniciarComboTipoVacacion() {
        iniciarCombos(vacacionSolicitudHelper.getListaOpcionesTipoVacacion());
        for (TipoVacacionEnum t : TipoVacacionEnum.values()) {
            vacacionSolicitudHelper.getListaOpcionesTipoVacacion().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de estado vacacion.
     */
    private void iniciarComboEstadoVacacion() {
        iniciarCombos(vacacionSolicitudHelper.getListaOpcionesEstadoVacacion());
        for (EstadoVacacionEnum t : EstadoVacacionEnum.values()) {
            vacacionSolicitudHelper.getListaOpcionesEstadoVacacion().add(new SelectItem(t.getCodigo(),
                    t.getDescripcion()));
        }
    }

    /**
     *
     */
    public void permisoHora() {
        VacacionSolicitud v = vacacionSolicitudHelper.getVacacionSolicitud();
        calculoHorasMinutos();
    }

    /**
     *
     */
    private void calculoHorasMinutos() {
        VacacionSolicitud v = vacacionSolicitudHelper.getVacacionSolicitud();
        if (v.getHoraInicio() != null) {
            vacacionSolicitudHelper.setHoraMinima(UtilFechas.obtenerHoraDelDia(v.getHoraInicio()));
            vacacionSolicitudHelper.setHoraMaxima(23);
            vacacionSolicitudHelper.setMinutoMinimo(UtilFechas.obtenerMinutoDeHora(v.getHoraInicio()));
            vacacionSolicitudHelper.setMinutoMaximo(59);
        }
        if (v.getHoraInicio() != null && v.getHoraFin() != null) {

            Long horas = UtilFechas.calcularDiferenciaHorasEntreFechas(v.getHoraInicio(), v.getHoraFin());
            Long minutos = UtilFechas.calcularDiferenciaMinutosEntreFechas(v.getHoraInicio(), v.getHoraFin());
            vacacionSolicitudHelper.setDuracionMinutos(new DecimalFormat("00").format(horas) + ":" + new DecimalFormat("00").format(minutos - (horas * 60)));
            v.setCantidadSolicitada(minutos);
            vacacionSolicitudHelper.setEnviarSolicitud(Boolean.TRUE);
        } else {
            vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);

        }
    }

    /**
     *
     */
    public void permisoDia() {
        if (vacacionSolicitudHelper.getVacacionSolicitud().getFecha() != null) {
            vacacionSolicitudHelper.setFechaMinima(vacacionSolicitudHelper.getVacacionSolicitud().getFecha());
            vacacionSolicitudHelper.setFechaMaxima(UtilFechas.sumarDias(vacacionSolicitudHelper.getVacacionSolicitud().getFecha(), 15));
        }

    }

    public void permisos() {
        vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
        vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitada(null);
    }

    /**
     * Encera variables cuando se cambia de seleccion de tipo de vacaciones.
     */
    public void esTipoVacacionSeleccionada() {
//        vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo()

        if (vacacionSolicitudHelper.getEsNuevo()) {

            vacacionSolicitudHelper.getVacacionSolicitud().setFecha(null);
            vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(null);
            vacacionSolicitudHelper.getVacacionSolicitud().setHoraFin(null);
            vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(null);

            vacacionSolicitudHelper.setMsgFeriados(Boolean.FALSE);
            if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(TipoVacacionEnum.VACACION_PLANIFICADAS.
                    getCodigo())) {
                if (vacacionSolicitudHelper.getVacacion().getSaldo().compareTo(0l) > 0) {
                    vacacionSolicitudHelper.setEnviarSolicitud(Boolean.TRUE);
                    buscarDetalles();
                    if (vacacionSolicitudHelper.getPlanificacionesAprobadas() > 0) {
                        mostrarMensajeEnPantalla("No puede realizar una solicitud de tipo VACACIÓN PLANIFICADA, "
                                + "porque la planificación anual de sus vacaciones no ha sido aprobada.",
                                FacesMessage.SEVERITY_WARN);
                        vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
                        vacacionSolicitudHelper.getVacacionSolicitud().setTipo(null);
                    }
                    vacacionSolicitudHelper.getVacacionSolicitud().setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
                    if (vacacionSolicitudHelper.isTienePlanificaciones()) {
                        ejecutarComandoPrimefaces("establecerDiasVacacionPlanificada();");
                    }
                } else {
                    mostrarMensajeEnPantalla("EL SERVIDOR NO TIENE SALDO DE VACACIONES, DEBE SOLICITAR ANTICIPO "
                            + "DE VACACIONES.", FacesMessage.SEVERITY_WARN);
                    vacacionSolicitudHelper.getVacacionSolicitud().setTipo(null);
                }
            } else if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(TipoVacacionEnum.VACACION_NO_PLANIFICADAS.getCodigo())) {
                if (vacacionSolicitudHelper.getVacacion().getSaldo().compareTo(0l) > 0) {
                    vacacionSolicitudHelper.getVacacionSolicitud().setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
                } else {
                    mostrarMensajeEnPantalla("EL SERVIDOR NO TIENE SALDO DE VACACIONES, DEBE SOLICITAR ANTICIPO "
                            + "DE VACACIONES.", FacesMessage.SEVERITY_WARN);
                    vacacionSolicitudHelper.getVacacionSolicitud().setTipo(null);
                }
            } else if (vacacionSolicitudHelper.getVacacionSolicitud().getTipo().equals(TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())) {
                if (vacacionSolicitudHelper.getVacacion().getSaldo().compareTo(0l) > 0) {
                    mostrarMensajeEnPantalla("Actualmente existe saldo de vacaciones efectivas, por lo tanto no "
                            + "puede realizar una solicitud de ANTICIPOS DE VACACIONES", FacesMessage.SEVERITY_WARN);
                    vacacionSolicitudHelper.getVacacionSolicitud().setTipo(null);
                } else {
                    if (vacacionSolicitudHelper.getVacacion().getSaldoProporcional().compareTo(0l) > 0) {
                        iniciarComboPeriodoVacacion();
                        vacacionSolicitudHelper.getVacacionSolicitud().setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
                        vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(null);
                    } else {
                        mostrarMensajeEnPantalla("No existe saldo de vacaciones propercionales.", FacesMessage.SEVERITY_WARN);
                        vacacionSolicitudHelper.getVacacionSolicitud().setTipo(null);
                    }
                }
            }

        }

        actualizarComponente(PATRON_FECHA);
    }

    public void reiniciarSolicitud() {
        if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals("H")) {
            vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(null);
        } else {
            vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(null);
        }
        esVacacionNoPlanificada();
    }

    /**
     *
     */
    public void esVacacionNoPlanificada() {
        if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals("H")) {
            if (vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio() == null) {
                Calendar hoy = new GregorianCalendar();
                vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(hoy.getTime());
                hoy.add(Calendar.HOUR_OF_DAY, 2);
                vacacionSolicitudHelper.getVacacionSolicitud().setHoraFin(hoy.getTime());
                vacacionSolicitudHelper.getVacacionSolicitud().setFecha(hoy.getTime());
            }
            permisoHora();
        } else {
            chequearDias(vacacionSolicitudHelper.getVacacionSolicitud().getDiasPlanificados());
            ejecutarComandoPrimefaces("establecerDiasVacacionNoPlanificadaDiaDias();");
        }
    }

    /**
     *
     */
    public void esAnticipoVacacion() {
        if (vacacionSolicitudHelper.getVacacionSolicitud().getTipoPeriodo().equals("H")) {
            if (vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio() == null) {
                Calendar hoy = new GregorianCalendar();
                vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(hoy.getTime());
                hoy.add(Calendar.HOUR_OF_DAY, 2);
                vacacionSolicitudHelper.getVacacionSolicitud().setHoraFin(hoy.getTime());
                vacacionSolicitudHelper.getVacacionSolicitud().setFecha(hoy.getTime());
            }
            permisoHora();
        } else {
            chequearDias(vacacionSolicitudHelper.getVacacionSolicitud().getDiasPlanificados());
            ejecutarComandoPrimefaces("establecerDiasAnticipoVacacionesDiaDias();");
        }
    }

    public void asignarNoPlanificadaDiaCalenarSeleccion() {
        String fechas = obtenerParametrosFaces().get("seleccionDias");
        chequearDias(fechas);
    }

    /**
     *
     * @param fechas
     */
    private void chequearDias(String fechas) {
        boolean isOk = false;
        vacacionSolicitudHelper.setEnviarSolicitud(false);
        vacacionSolicitudHelper.setDiasSolicitados(0l);
        if (fechas != null) {
            String[] f = fechas.split(",");
            List<String> fechasCadenas = Arrays.asList(f);
            if (!fechasCadenas.isEmpty()) {
                try {
                    List<Date> dates = UtilFechas.parsearLista(fechasCadenas, "dd/MM/yyyy");
                    long saldo = (vacacionSolicitudHelper.getMinutosVacacionesSolicitadas() % (480 * 5)) / 8 / 60;
                    long c = dates.size();
                    long offset = 0;
                    if (vacacionSolicitudHelper.getVacacionSolicitud().getVacacionParametro().getImputarFinSemanaVacacion()) {
                        offset = (c + saldo) / 5;
                    }
                    vacacionSolicitudHelper.setDiasSolicitados(c + (offset * 2));
                    vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitadaMinutos(
                            vacacionSolicitudHelper.getDiasSolicitados() + saldo);

                    vacacionSolicitudHelper.getVacacionSolicitud().setDiasPlanificados(UtilFechas.convertirCSV(
                            dates, "dd/MM/yyyy"));
                    isOk = true;
                } catch (ParseException ex) {
                    Logger.getLogger(VacacionSolicitudControlador.class.getName()).log(Level.SEVERE, null, ex);
                    isOk = false;
                }
            }
        }
        vacacionSolicitudHelper.setEnviarSolicitud(isOk);
    }

    public boolean esVacacionNoPlanificadaHoraValida() {
        boolean esValida = true;
        vacacionSolicitudHelper.setDuracionMinutos("");
        if (vacacionSolicitudHelper.getVacacionSolicitud().getFecha() == null) {
            esValida = false;
        } else {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(vacacionSolicitudHelper.getVacacionSolicitud().getFecha());

            Calendar c2 = Calendar.getInstance();
            c2.setTime(vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio());

            Calendar c3 = Calendar.getInstance();
            c3.setTime(vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin());

            c2.set(Calendar.YEAR, c1.get(Calendar.YEAR));
            c2.set(Calendar.MONTH, c1.get(Calendar.MONTH));
            c2.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));

            c3.set(Calendar.YEAR, c1.get(Calendar.YEAR));
            c3.set(Calendar.MONTH, c1.get(Calendar.MONTH));
            c3.set(Calendar.DAY_OF_MONTH, c1.get(Calendar.DAY_OF_MONTH));

            vacacionSolicitudHelper.getVacacionSolicitud().setHoraInicio(c2.getTime());
            vacacionSolicitudHelper.getVacacionSolicitud().setHoraFin(c3.getTime());

            if (vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio() == null
                    || vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin() == null) {
                esValida = false;
            } else if (!vacacionSolicitudHelper.getVacacionSolicitud().getHoraInicio().before(vacacionSolicitudHelper.getVacacionSolicitud().getHoraFin())) {
                esValida = false;
                mostrarMensajeEnPantalla("La hora de salida debe ser anterior a la hora de entrada", FacesMessage.SEVERITY_ERROR);
            }

        }

        if (esValida) {
            permisoHora();
        }

        vacacionSolicitudHelper.setEnviarSolicitud(esValida);
        return esValida;
    }

    /**
     * Este metodo llena las opciones para el combo de periodo vacacion.
     */
    private void iniciarComboPeriodoVacacion() {
        vacacionSolicitudHelper.getListaOpcionesPeriodoVacacion().clear();
        iniciarCombos(vacacionSolicitudHelper.getListaOpcionesPeriodoVacacion());
        vacacionSolicitudHelper.getListaOpcionesPeriodoVacacion().add(new SelectItem(PeriodoVacacionEnum.DIAS.getCodigo(), PeriodoVacacionEnum.DIAS.getDescripcion()));
        vacacionSolicitudHelper.getListaOpcionesPeriodoVacacion().add(new SelectItem(PeriodoVacacionEnum.HORAS.getCodigo(), PeriodoVacacionEnum.HORAS.getDescripcion()));
    }

    /**
     * Invoca el servlet para descargar los archivos
     *
     * @param id
     * @return
     */
    public String invocarServlet(Long id) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().
                getContext();
        String url = servletContext.getContextPath();
        String cadena = UtilCadena.concatenar(url, "/imageServlet/", id);
        return cadena;
    }

    /**
     * Archivo.
     *
     * @param event FileUploadEvent
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            vacacionSolicitudHelper.setArchivoCargado(file);
            InputStream in = file.getInputstream();
            String nombreArchivo = file.getFileName();
            int indice = nombreArchivo.lastIndexOf(".");
            String nombreArchivoSinExt = nombreArchivo.substring(0, indice);
            String extencionSinPunto = nombreArchivo.substring(indice + 1);
            InputStream stream = new ByteArrayInputStream(file.getContents());
            String extencion = nombreArchivo.substring(indice);
            File tempFile = UtilArchivos.getFileFromBytes(in, nombreArchivo, extencion);
            if (tempFile != null) {
                File f = tempFile;
                vacacionSolicitudHelper.getVacacionSolicitud().setArchivo(new Archivo());
                iniciarDatosEntidad(vacacionSolicitudHelper.getVacacionSolicitud().getArchivo(), Boolean.TRUE);
                vacacionSolicitudHelper.getVacacionSolicitud().getArchivo().setArchivo(UtilArchivos.getBytesFromFile(f));
                vacacionSolicitudHelper.getVacacionSolicitud().getArchivo().setNombre(nombreArchivo);
                vacacionSolicitudHelper.setArchivoFile(UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt,
                        extencionSinPunto));
                System.out.println(" archivo cargado: " + vacacionSolicitudHelper.getVacacionSolicitud().getArchivo().getNombre());
                if (vacacionSolicitudHelper.getVacacionSolicitud().getArchivo() != null) {
                    vacacionSolicitudHelper.setNombreArchivo(vacacionSolicitudHelper.getVacacionSolicitud().getArchivo().getNombre());
                }
                FacesMessage msg = new FacesMessage("El archivo se subió correctamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class
                    .getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoVacacion(final String codigo) {
        return TipoVacacionEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoPeriodo(final String codigo) {
        return PeriodoVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoVacacion(final String codigo) {
        return EstadoVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Verifica que las fechas ingresadas no sean anteriores a la fecha actual
     *
     * @param fecha que es objeto de comparacion
     * @return
     */
    public boolean verificaFecha(Date fecha) {
        return UtilFechas.calcularDiferenciaDiasEntreFechas(fecha, new Date()) >= 0;
    }

    /**
     * Permite regresar al listado
     *
     * @return
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Permite regresar al listado
     *
     * @return
     */
    public String irNuevo() {
        return iniciarNuevo();
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_INDEX;
    }

    /**
     * @return the vacacionSolicitudHelper
     */
    public VacacionSolicitudHelper getVacacionSolicitudHelper() {
        return vacacionSolicitudHelper;
    }

    /**
     * @param vacacionSolicitudHelper the vacacionSolicitudHelper to set
     */
    public void setVacacionSolicitudHelper(VacacionSolicitudHelper vacacionSolicitudHelper) {
        this.vacacionSolicitudHelper = vacacionSolicitudHelper;
    }

    /**
     *
     */
    public void registrarVacacionesNoPlanificadas() {
        /* if (vacacionSolicitudHelper.getVacacionSolicitud().getFecha() != null
         && vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada() != null) {
         //            vacacionSolicitudHelper.getVacacionSolicitud().setCantidadSolicitada(15l);
         vacacionSolicitudHelper.getVacacionSolicitud().setFechaFin(
         UtilFechas.sumarDias(vacacionSolicitudHelper.getVacacionSolicitud().getFechaInicio(),
         vacacionSolicitudHelper.getVacacionSolicitud().getCantidadSolicitada().intValue()));
         vacacionSolicitudHelper.setEnviarSolicitud(Boolean.TRUE);

         } else {
         vacacionSolicitudHelper.setEnviarSolicitud(Boolean.FALSE);
         }*/

    }

    /**
     * @return the pdf
     */
    public StreamedContent getPdf() {
        return pdf;
    }
}
