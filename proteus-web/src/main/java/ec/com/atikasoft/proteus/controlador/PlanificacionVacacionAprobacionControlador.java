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

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PlanificacionVacacionAprobacionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * PlanificacionVacacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionVacacionAprobacionBean")
@ViewScoped
public class PlanificacionVacacionAprobacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionVacacionAprobacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/planificacion_vacacion_aprobacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD_APROBACION = "/pages/procesos/vacacion/lista_planificacion_vacacion_aprobacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{planificacionVacacionAprobacionHelper}")
    private PlanificacionVacacionAprobacionHelper planificacionVacacionAprobacionHelper;

    /**
     * Constructor por defecto.
     */
    public PlanificacionVacacionAprobacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(planificacionVacacionAprobacionHelper);
        setPlanificacionVacacionAprobacionHelper(planificacionVacacionAprobacionHelper);
        getCatalogoHelper().setCampoBusqueda("");
        iniciarControles();
    }

    private void iniciarControles() {
        iniciarComboEstadoPlanifVacacion();
        iniciarComboInstitucionEjercicioFiscal();
        llenarUnidadOrganizacional();
        if (planificacionVacacionAprobacionHelper.getUnidadOrganizacional() == null) {
            planificacionVacacionAprobacionHelper.setUnidadOrganizacional(new UnidadOrganizacional());
        }
        planificacionVacacionAprobacionHelper.setEjercicioFiscal(null);
        planificacionVacacionAprobacionHelper.setEstado(null);
        planificacionVacacionAprobacionHelper.getListaVacacionVO().clear();
        planificacionVacacionAprobacionHelper.setEsRRHH(esUnidadRRHH());
        planificacionVacacionAprobacionHelper.getUnidadOrganizacional().setId(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
        planificacionVacacionAprobacionHelper.getUnidadOrganizacional().setNombre(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
    }

    @Override
    public String iniciarEdicion() {
        try {
            planificacionVacacionAprobacionHelper.setEsNuevo(Boolean.FALSE);
            planificacionVacacionAprobacionHelper.setGuardado(Boolean.FALSE);
            iniciarDatosEntidad(planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.FALSE);
            buscarDetalles();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {

        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String guardar() {
        try {
            if (planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidorRRHHId() == null) {
                planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setServidorRRHHId(obtenerUsuarioConectado().getServidor().getId());
            }
            vacacionServicio.actualizarPlanificacionVacacion(
                    planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            planificacionVacacionAprobacionHelper.setGuardado(Boolean.TRUE);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Aprueba masivamente todos los registros de la lista.
     *
     * @param aprobar si se aprueba masivamente, caso contrario se devuelve al
     * remitente.
     * @return
     */
    public String aprobarMasivamente(Boolean aprobar) {
        int count = 0;
        try {
            for (PlanificacionVacacionVO vo : planificacionVacacionAprobacionHelper.getListaVacacionVO()) {
                vo.getPlanificacionVacacion().setObservacionAprobacion(
                        planificacionVacacionAprobacionHelper.getObservacionAprobacion());
                if (vo.getPlanificacionVacacion().getServidorRRHHId() == null) {
                    vo.getPlanificacionVacacion().setServidorRRHHId(obtenerUsuarioConectado().getServidor().getId());
                }
                if (vo.getPlanificacionVacacion().getEstado().equals(EstadoPlanVacacionEnum.VALIDADO.getCodigo())) {
                    if (aprobar) {
                        vo.getPlanificacionVacacion().setEstado(
                                EstadoPlanVacacionEnum.APROBADO.getCodigo());
                    } else {
                        vo.getPlanificacionVacacion().setEstado(
                                EstadoPlanVacacionEnum.ENVIADO.getCodigo());
                    }
                    count++;
                }
                iniciarDatosEntidad(vo.getPlanificacionVacacion(), Boolean.FALSE);
                vacacionServicio.actualizarPlanificacionVacacion(
                        vo.getPlanificacionVacacion());
            }
            mostrarMensajeEnPantalla("Registro Guardado. TOTAL REGISTROS :" + count, FacesMessage.SEVERITY_INFO);
            if (!aprobar) {
                ejecutarComandoPrimefaces("confirmationDesaprobar.hide()");
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar masivamente", e);
        }
        return LISTA_ENTIDAD_APROBACION;
    }

    /**
     * Aprueba de manera individual el registro.
     *
     * @return
     */
    public String aprobar() {
        planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(
                EstadoPlanVacacionEnum.APROBADO.getCodigo());
        return guardar();
    }

    /**
     *
     * @param aprobar
     * @return
     */
    public String validarCamposRequeridos(boolean aprobar) {
        if (planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getEstado() == null) {
            mostrarMensajeEnPantalla("El campo estado es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionAprobacion() == null
                || planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionAprobacion().isEmpty()) {
            mostrarMensajeEnPantalla("El campo observacion es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (aprobar) {
            ejecutarComandoPrimefaces("confirmAprobar.show();");
        } else {
            ejecutarComandoPrimefaces("confirmNegar.show();");
        }
        return null;
    }

    public String noAprobar() {

        if (!planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionAprobacion().isEmpty()) {
            planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(
                    EstadoPlanVacacionEnum.ENVIADO.getCodigo());
            return guardar();
        } else {
            mostrarMensajeEnPantalla("La observación es un campo requerido", FacesMessage.SEVERITY_ERROR);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Obtiene los datos del servidor conectado en la sesión.
     */
    public void iniciarDatosServidor() {
        try {
            UsuarioVO vo = obtenerUsuarioConectado();
            planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().setFechaIngreso(
                    vo.getServidor().getFechaIngresoInstitucion());
            planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().setDistributivoDetalle(vo.getDistributivoDetalle());

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda datos servidor", ex);
        }
    }

    @Override
    public String borrar() {
        return null;
    }

    @Override
    /**
     * Busca todas las Unidades Organizacionales que tienen planificacion de
     * vacaciones en estado VALIDADO.
     */
    public String buscar() {
        try {
            List<PlanificacionVacacionVO> lista = vacacionServicio.listarTodosPlanificacionVacacionPorValidarAprobar(
                    obtenerUsuarioConectado().getServidor().getId(),
                    planificacionVacacionAprobacionHelper.getEstado(),
                    obtenerUsuarioConectado().getInstitucion().getId(),
                    planificacionVacacionAprobacionHelper.getEjercicioFiscal(),
                    planificacionVacacionAprobacionHelper.getUnidadOrganizacional() != null
                            ? planificacionVacacionAprobacionHelper.getUnidadOrganizacional().getId() : null);
            planificacionVacacionAprobacionHelper.setListaVacacionVO(lista);
        } catch (ServicioException ex) {
//            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Consulta lista de aprobacion", ex);
        }
        return null;
    }
    /*
     * Setea los detalles vigentes de la instancia de planificacion del usuario conectado.
     */

    public void buscarDetalles() {
        if (planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles() == null) {
            planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().setListaPlanificacionVacacionDetalles(
                    new ArrayList<PlanificacionVacacionDetalle>());
        }
        planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().clear();
        planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().setTotalDias(0);
        for (PlanificacionVacacionDetalle det
                : planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getPlanificacionVacacionDetalles()) {
            if (det.getVigente()) {
                planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().add(det);
                planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().setTotalDias((int) (planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getTotalDias()
                        + det.getNumeroDias()));
            }
        }

        getListaSaldosVacaciones();
        obtenerRegistroSaldoVacacionServidor();
    }

    /**
     * Se obtiene del ultimo registro de vacaciones(acumulacion) de acuerdo a la
     * fecha de creacion Alli se registra el saldo final de la transacción. Si
     * no existen registros el saldo es 0 minutos
     */
    public void obtenerRegistroSaldoVacacionServidor() {
        Long totalSaldo = 0L;
        Servidor servidor = planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidor();
        if (servidor != null) {
            List<Vacacion> listaVacacion = planificacionVacacionAprobacionHelper.getListaSaldoVacaciones();
            planificacionVacacionAprobacionHelper.setCadenaSaldo("0 Días");
            for (Vacacion v : listaVacacion) {
                totalSaldo += v.getSaldo();
            }
            Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(totalSaldo, servidor.getJornada());
            planificacionVacacionAprobacionHelper.setCadenaSaldo(
                    saldo[0] + " Días "
                    + saldo[1] + " Horas "
                    + saldo[2] + " Minutos ");
            Double saldoHoras = UtilFechas.convertirMinutosEnHoras(totalSaldo);
            Double saldoDias = UtilFechas.convertirHoraEnDiasLaboral(saldoHoras.longValue());
            planificacionVacacionAprobacionHelper.setTotalSaldoVacacion(saldoDias);
        }
    }

    /**
     * buscar listado de saldo de vacaciones por periodos.
     *
     */
    public void getListaSaldosVacaciones() {
        planificacionVacacionAprobacionHelper.getListaSaldoVacaciones().clear();
        PlanificacionVacacion pv = planificacionVacacionAprobacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion();
        try {
            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(pv.getInstitucionEjercicioFiscal().getInstitucion().getId(), pv.getServidorId());
            Vacacion v = vacacionDao.buscarPorServidor(si.getId());
            planificacionVacacionAprobacionHelper.getListaSaldoVacaciones().add(v);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }

    }

    private void llenarListaInicialUnidadesOrganizacionales() {
        try {
            if (planificacionVacacionAprobacionHelper.getListaUnidadesOrganizacionales() == null) {
                planificacionVacacionAprobacionHelper.setListaUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
            } else {
                planificacionVacacionAprobacionHelper.getListaUnidadesOrganizacionales().clear();
            }
            List<UnidadOrganizacional> listarInstitucionesHijas
                    = admServicio.listarTodosUnidadOrganizacional();

            planificacionVacacionAprobacionHelper.getListaUnidadesOrganizacionales().addAll(listarInstitucionesHijas);
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales.", e);
        }
    }

    /**
     * Permite armar el arbol para presentar en la página.
     *
     * @return
     */
    public String llenarUnidadOrganizacional() {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre, nodoHijo;
            /*
             * carga el primer registro (nodo principal)
             */
            llenarListaInicialUnidadesOrganizacionales();
            nodoPrincipal = new DefaultTreeNode(planificacionVacacionAprobacionHelper.getListaUnidadesOrganizacionales().get(0), null);
            planificacionVacacionAprobacionHelper.setRootUnidadOrganizacional(nodoPrincipal);
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : planificacionVacacionAprobacionHelper.getListaUnidadesOrganizacionales()) {
                if (un.getVigente()) {
                    nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (un.getId() != null) {
                        obtenerHijos(un, nodoPadre);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
        return null;
    }

    public void obtenerHijos(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        try {
            for (UnidadOrganizacional unidad : registroPadre.getListaUnidadesOrganizacionales()) {
                if (unidad.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(unidad, nodoPadre);
                    if (!unidad.getListaUnidadesOrganizacionales().isEmpty()) {
                        obtenerHijos(unidad, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * seleccion de nodo unidad organizacional.
     */
    public void asignarUnidadOrganizacionalSeleccionada() {
        UnidadOrganizacional un = (UnidadOrganizacional) planificacionVacacionAprobacionHelper.getUnidadSeleccionada().getData();
        planificacionVacacionAprobacionHelper.setUnidadOrganizacional(un);
        ejecutarComandoPrimefaces("dlgAgregar.hide()");
    }

    private boolean esUnidadRRHH() {
        boolean esUnidadRRHH = false;
        try {
            String parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
            esUnidadRRHH = esRRHH(parametro);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esUnidadRRHH;
    }

    /**
     * Permite obtener Ejercicios fiscales vigentes y no vigentes.
     */
    private void iniciarComboInstitucionEjercicioFiscal() {
        List<InstitucionEjercicioFiscal> lista;
        try {
            lista = admServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            planificacionVacacionAprobacionHelper.getListaOpcionesEjercicioFiscal().clear();
            iniciarCombos(planificacionVacacionAprobacionHelper.getListaOpcionesEjercicioFiscal());
            for (InstitucionEjercicioFiscal c : lista) {
                planificacionVacacionAprobacionHelper.getListaOpcionesEjercicioFiscal().add(
                        new SelectItem(c.getId(), c.getEjercicioFiscal().getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
        }

    }

    /**
     * Este metodo llena las opciones para el combo de Estados de la
     * planificación de vacaciones.
     */
    private void iniciarComboEstadoPlanifVacacion() {
        iniciarCombosTodos(planificacionVacacionAprobacionHelper.getListaOpcionesEstadoPlanifVacacion());
        for (EstadoPlanVacacionEnum t : EstadoPlanVacacionEnum.values()) {
            if (!t.getCodigo().equals(EstadoPlanVacacionEnum.BORRADOR.getCodigo())
                    && !t.getCodigo().equals(EstadoPlanVacacionEnum.VALIDADO.getCodigo())) {
                planificacionVacacionAprobacionHelper.getListaOpcionesEstadoPlanifVacacion().add(
                        new SelectItem(t.getCodigo(), t.getDescripcion()));
            }
        }
    }

    /**
     * Permite regresar al listado.
     *
     * @return
     */
    public String irListaAprobacion() {
        planificacionVacacionAprobacionHelper.getListaPlanificacionVacaciones().clear();
        planificacionVacacionAprobacionHelper.setEjercicioFiscal(null);
        planificacionVacacionAprobacionHelper.setUnidadOrganizacional(null);
        planificacionVacacionAprobacionHelper.setEstado(null);
        return LISTA_ENTIDAD_APROBACION;
    }

    /**
     * Permite Regresar.
     *
     * @return
     */
    public String salir() {
        planificacionVacacionAprobacionHelper.getListaPlanificacionVacaciones().clear();
        planificacionVacacionAprobacionHelper.setEjercicioFiscal(null);
        planificacionVacacionAprobacionHelper.setUnidadOrganizacional(null);
        planificacionVacacionAprobacionHelper.setEstado(null);
        return PAGINA_PORTAL;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * @return the vacacionServicio
     */
    public VacacionServicio getAdmServicio() {
        return vacacionServicio;
    }

    /**
     * @param vacacionServicio the vacacionServicio to set
     */
    public void setAdmServicio(VacacionServicio vacacionServicio) {
        this.vacacionServicio = vacacionServicio;
    }

    /**
     * @return the planificacionVacacionAprobacionHelper
     */
    public PlanificacionVacacionAprobacionHelper getPlanificacionVacacionAprobacionHelper() {
        return planificacionVacacionAprobacionHelper;
    }

    /**
     * @param planificacionVacacionAprobacionHelper the
     * planificacionVacacionAprobacionHelper to set
     */
    public void setPlanificacionVacacionAprobacionHelper(PlanificacionVacacionAprobacionHelper planificacionVacacionAprobacionHelper) {
        this.planificacionVacacionAprobacionHelper = planificacionVacacionAprobacionHelper;
    }
}
