/*
 *  PlanificacionVacacionControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
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
import ec.com.atikasoft.proteus.controlador.helper.RevertirVacacionesHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "revertirVacacionesBean")
@ViewScoped
public class RevertirVacacionesControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RevertirVacacionesControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     *
     */
    public static final String PAGINA_ENTIDAD = "/pages/procesos/vacacion/solicitud_vacacion_a_revertir.jsf";
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;
    /**
     *
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;
    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{revertirVacacionesHelper}")
    private RevertirVacacionesHelper revertirVacacionesHelper;

    /**
     * Constructor por defecto.
     */
    public RevertirVacacionesControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        revertirVacacionesHelper.iniciador();
        limpiarFormulario();
    }

    /**
     * INCIACIALIZA LAS VARIABLES ASOCIADAS A LA PLANIFICACION DE VACACIONES
     *
     */
    public void limpiarFormulario() {
        limpiarPanelDatosServidor();
        if (revertirVacacionesHelper.getBuscarServidorPor() == null) {
            revertirVacacionesHelper.setApellidosNombres(null);
            revertirVacacionesHelper.setTipoIdentificacion(null);
            revertirVacacionesHelper.setNroIdentificacion(null);
        } else if (revertirVacacionesHelper.getBuscarServidorPor().equals("id")) {
            revertirVacacionesHelper.setApellidosNombres(null);
        } else {
            revertirVacacionesHelper.setTipoIdentificacion(null);
            revertirVacacionesHelper.setNroIdentificacion(null);
        }
        iniciarComboTipoIdentificacion();
    }

    /**
     * LIMPIA EL PANEL DE LOS DATOS DEL SERVIDOR
     */
    public void limpiarPanelDatosServidor() {
        revertirVacacionesHelper.setInstitucionEjercicioFiscal(null);
        revertirVacacionesHelper.setBotonGuardar(false);
        revertirVacacionesHelper.getListaDistributivosDetalles().clear();
        revertirVacacionesHelper.setDistributivosDetalle(null);
        revertirVacacionesHelper.setServidor(null);
        revertirVacacionesHelper.setSaldoVacacion(0L);
        revertirVacacionesHelper.setSaldoVacacionTexto(null);
        revertirVacacionesHelper.setSaldoVacacionProporcional(0L);
        revertirVacacionesHelper.setSaldoVacacionProporcionalTexto(null);
        revertirVacacionesHelper
                .setListaVacacionSolicitudesAprobadas(new ArrayList<VacacionSolicitud>());
    }

    /**
     * LIMPIA EL CAMPO NRO IDENTIFICACION
     */
    public void limpiarNroIdentificacion() {
        revertirVacacionesHelper.setNroIdentificacion(null);
        limpiarPanelDatosServidor();
    }

    /**
     * LLENA LA LISTA DE SELECCION DE TIPO DE IDENTIFICACION EN EL FORMULARIO DE BUSQUEDA
     */
    private void iniciarComboTipoIdentificacion() {
        iniciarCombos(revertirVacacionesHelper.getOpcionesTipoIdentificacion());
        for (TipoDocumentoEnum tDocId : TipoDocumentoEnum.values()) {
            if (!(tDocId.getNemonico().equals(TipoDocumentoEnum.RUC.getNemonico()))) {
                revertirVacacionesHelper.getOpcionesTipoIdentificacion().add(
                        new SelectItem(tDocId.getNemonico(), tDocId.getNombre()));
            }
        }
    }

    /**
     * REVIERTE LA SOLICITUD DE VACACIONES
     *
     * @param desdeListaSolicitudes verifica si el llamado a este metodo se hizo desde la lista de solicitudes
     * @return
     */
    public String revertir(Boolean desdeListaSolicitudes) {
        VacacionSolicitud vs = revertirVacacionesHelper.getSolicitudVacionAprobada();
        UsuarioVO usuarioConectado = obtenerUsuarioConectado();
        try {
            vs = vacacionServicio.revertirVacacionAprobada(vs, usuarioConectado);
            if (desdeListaSolicitudes) {
                recuperarSolicitudesVacacionesAprobadas();
                revertirVacacionesHelper.setSolicitudVacionAprobada(null);
            } else {
                revertirVacacionesHelper.setSolicitudVacionAprobada(vs);
            }

            mostrarMensajeEnPantalla(
                    "Vacaciones revertidas satisfactoriamente.",
                    FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla(
                    "Error al intentar revertir las vacaciones",
                    FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al intentar revertir las vacaciones", e);
        }

        ejecutarComandoPrimefaces("confirmRevertir.hide()");
        return null;
    }

    /**
     * BUSCAR UNNSERVIDOR Y SU PLANIFICACION DE VACACIONES
     *
     * @return String
     */
    public String buscarServidorYPlanificacionVacaciones() {
        try {
            limpiarFormulario();
            revertirVacacionesHelper.getListaDistributivosDetalles().clear();
            BusquedaServidorVO bServidor = new BusquedaServidorVO();

            if (revertirVacacionesHelper.getTipoIdentificacion() != null
                    && revertirVacacionesHelper.getNroIdentificacion() != null) {

                bServidor.setTipoDocumentoServidor(
                        revertirVacacionesHelper.getTipoIdentificacion());
                bServidor.setNumeroDocumentoServidor(
                        revertirVacacionesHelper.getNroIdentificacion());

            } else if (revertirVacacionesHelper.getApellidosNombres() != null) {
                bServidor.setNombreServidor(
                        revertirVacacionesHelper.getApellidosNombres());
            }

            revertirVacacionesHelper
                    .setListaDistributivosDetalles(servidorServicio.buscar(bServidor));

            ejecutarComandoPrimefaces("dlgResultadosBusqueda.show();");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * SELECCIONAR SERVIDOR DE LA LISTA DE RESULTADOS DE LA BUSQUEDA POR NOMBRES Y APELLDIOS Y MANDA A BSUCAR LOS DATOS
     * DE SU PALNIFICACION DE VACACIONES
     *
     * @return
     */
    public String seleccionarServidorBuscarPlanificacion() {
        if (recuperarSolicitudesVacacionesAprobadas()) {
            mostrarMensajeEnPantalla(
                    "Registro cargado satisfactoriamente", FacesMessage.SEVERITY_INFO);
        }
        ejecutarComandoPrimefaces("dlgResultadosBusqueda.hide();");
        return null;
    }

    /**
     * RECUPERA las solicitudes aprobadas asociadas al servidor
     *
     * @param servidor
     * @return Boolean
     */
    private Boolean recuperarSolicitudesVacacionesAprobadas() {
        try {
            Servidor servidor = revertirVacacionesHelper.getServidor();
            if (servidor != null) {
                UsuarioVO usuarioConectado = obtenerUsuarioConectado();
                revertirVacacionesHelper.setInstitucionEjercicioFiscal(
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
                DistributivoDetalle dd = distributivoPuestoServicio.buscarDistributivoPorServidor(
                        servidor.getTipoIdentificacion(),
                        servidor.getNumeroIdentificacion(),
                        usuarioConectado.getEjercicioFiscal().getId());

                if (verificadoServidorPerteneceAMismaUnidadOrg(dd)) {
                    revertirVacacionesHelper.setDistributivosDetalle(dd);
                    obtenerSaldosDeVacacionesEfectivasYProporcionales();
                    llenarListaSolicitudesAprobadas();

                    return Boolean.TRUE;

                } else {
                    mostrarMensajeEnPantalla(
                            "El servidor seleccionado no pertenence a su Unidad Organizacional asignada",
                            FacesMessage.SEVERITY_ERROR);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla(
                    "Error al intentar recuperar la planificación de vacaciones asociada al servidor",
                    FacesMessage.SEVERITY_ERROR);
        }
        return Boolean.FALSE;
    }

    /**
     * Vierifica si un servidor pertenece a la misma unidad organizacional que el usuario conectado
     *
     * @param distributiboDetalleServidor datos del puesto del servidor que contiene la unidad organizacional
     * @return
     */
    private Boolean verificadoServidorPerteneceAMismaUnidadOrg(DistributivoDetalle distributiboDetalleServidor) {
        try {
            List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            for (UnidadOrganizacional unidad : unidades) {
                if (unidad.getCodigo().equals(
                        distributiboDetalleServidor.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                    return Boolean.TRUE;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * Se obtiene el saldo de vacaciones pendientes, tanto efectivas como proporcionales asociadas al servidor
     *
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void obtenerSaldosDeVacacionesEfectivasYProporcionales() throws DaoException {
        revertirVacacionesHelper.setSaldoVacacion(0l);
        Servidor s = revertirVacacionesHelper.getServidor();

        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                obtenerUsuarioConectado().getInstitucion().getId(), s.getId());

        Vacacion v = vacacionServicio.buscarVacacion(si.getId());

        if (v != null) {
            Integer[] saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(v.getSaldo()), s.getJornada());
            revertirVacacionesHelper.setSaldoVacacionTexto(
                    UtilCadena.concatenar(saldo[0], " Días, ", saldo[1], " Horas, " + saldo[2], " Minutos"));
            revertirVacacionesHelper.setSaldoVacacion(v.getSaldo());

            Integer[] saldoProporcional = UtilFechas.convertirMinutosA_ddHHmm(
                    Math.abs(v.getSaldoProporcional()), s.getJornada());
            revertirVacacionesHelper.setSaldoVacacionProporcionalTexto(UtilCadena.concatenar(
                    saldoProporcional[0], " Días, ", saldoProporcional[1],
                    " Horas, " + saldoProporcional[2], " Minutos"));
            revertirVacacionesHelper.setSaldoVacacionProporcional(v.getSaldoProporcional());

        }
    }

    /**
     * RECUPERA LA LISTA DE SOLICITUDES DE VACACIONES APROBADAS ASOCIADAS AL SERVIDOR
     */
    private void llenarListaSolicitudesAprobadas() {
        try {
            revertirVacacionesHelper.getListaVacacionSolicitudesAprobadas().clear();
            revertirVacacionesHelper.setListaVacacionSolicitudesAprobadas(
                    vacacionServicio.listarTodosVacacionSolicitudPorServidorYEstado(
                            revertirVacacionesHelper.getServidor().getId(),
                            EstadoVacacionEnum.APROBADO.getCodigo()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de solicitudes aprobadas", ex);
        }
    }

    public String mostrarSolicitud() {
        return PAGINA_ENTIDAD;
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
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
     *
     * @return
     */
    public RevertirVacacionesHelper getRevertirVacacionesHelper() {
        return revertirVacacionesHelper;
    }

    /**
     *
     * @param revertirVacacionesHelper
     */
    public void setRevertirVacacionesHelper(RevertirVacacionesHelper revertirVacacionesHelper) {
        this.revertirVacacionesHelper = revertirVacacionesHelper;
    }
}
