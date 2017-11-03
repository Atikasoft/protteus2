/*
 *  PlanificacionVacacionValidacionControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PlanificacionVacacionValidacionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
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
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * PlanificacionVacacionValidacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionVacacionValidacionBean")
@ViewScoped
public class PlanificacionVacacionValidacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionVacacionValidacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/planificacion_vacacion_validacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD_VALIDACION = "/pages/procesos/vacacion/lista_planificacion_vacacion_validacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * Servicio de administracion.
     *
     * /**
     * Helper de clase.
     */
    @ManagedProperty("#{planificacionVacacionValidacionHelper}")
    private PlanificacionVacacionValidacionHelper planificacionVacacionValidacionHelper;

    /**
     * Constructor por defecto.
     */
    public PlanificacionVacacionValidacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(planificacionVacacionValidacionHelper);
        setPlanificacionVacacionValidacionHelper(planificacionVacacionValidacionHelper);
        iniciarControles();
    }

    private void iniciarControles() {
        iniciarComboInstitucionEjercicioFiscal();
        iniciarComboEstadoPlanifVacacion();
        llenarUnidadOrganizacional();
        planificacionVacacionValidacionHelper.setEjercicioFiscal(null);
        planificacionVacacionValidacionHelper.setEstado(null);
        planificacionVacacionValidacionHelper.getListaVacacionVO().clear();
        if (planificacionVacacionValidacionHelper.getUnidadOrganizacional() == null) {
            planificacionVacacionValidacionHelper.setUnidadOrganizacional(new UnidadOrganizacional());
        }
        planificacionVacacionValidacionHelper.setEsRRHH(esUnidadRRHH());
        planificacionVacacionValidacionHelper.getUnidadOrganizacional().setId(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
        planificacionVacacionValidacionHelper.getUnidadOrganizacional().setNombre(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
    }

    @Override
    public String guardar() {
        try {
            if (planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidorJefeId() == null) {
                planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setServidorJefeId(obtenerUsuarioConectado().getServidor().getId());
            }
            vacacionServicio.actualizarPlanificacionVacacion(getPlanificacionVacacionValidacionHelper().getPlanificacionVacacionVO().getPlanificacionVacacion());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            getPlanificacionVacacionValidacionHelper().setGuardado(Boolean.TRUE);

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     *
     * @param aprobar
     * @return
     */
    public String validarCamposRequeridos(boolean aprobar) {
        if (planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getEstado() == null) {
            mostrarMensajeEnPantalla("El campo estado es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionValidacion() == null
                || planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionValidacion().isEmpty()) {
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

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(getPlanificacionVacacionValidacionHelper().getPlanificacionVacacionEditDelete());
            PlanificacionVacacion d = (PlanificacionVacacion) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getPlanificacionVacacionValidacionHelper().setGuardado(Boolean.FALSE);
            getPlanificacionVacacionValidacionHelper().getPlanificacionVacacionVO().setPlanificacionVacacion(d);
            getPlanificacionVacacionValidacionHelper().setEsNuevo(Boolean.FALSE);
            buscarDetalles();
            iniciarDatosServidor();
        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
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
    public String borrar() {

        return null;
    }

    @Override
    public String buscar() {
        try {
            getPlanificacionVacacionValidacionHelper().getListaPlanificacionVacaciones().clear();
            getPlanificacionVacacionValidacionHelper().getListaVacacionVO().clear();
            System.out.println("unidad organizacional:" + (planificacionVacacionValidacionHelper.getUnidadOrganizacional() != null
                    ? planificacionVacacionValidacionHelper.getUnidadOrganizacional().getId() : null));
            getPlanificacionVacacionValidacionHelper().setListaVacacionVO(
                    vacacionServicio.listarTodosPlanificacionVacacionPorValidarAprobar(
                            obtenerUsuarioConectado().getServidor().getId(),
                            planificacionVacacionValidacionHelper.getEstado(),
                            obtenerUsuarioConectado().getInstitucion().getId(),
                            planificacionVacacionValidacionHelper.getEjercicioFiscal(),
                            planificacionVacacionValidacionHelper.getUnidadOrganizacional() != null
                                    ? planificacionVacacionValidacionHelper.getUnidadOrganizacional().getId() : null));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Obtiene los datos del servidor conectado en la sesión.
     */
    public void iniciarDatosServidor() {
        try {
            UsuarioVO vo = obtenerUsuarioConectado();
            planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().setFechaIngreso(
                    vo.getServidor().getFechaIngresoInstitucion());
            planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().setDistributivoDetalle(vo.getDistributivoDetalle());

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos servidor", ex);
        }
    }

    public String validar() {
        planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(
                EstadoPlanVacacionEnum.VALIDADO.getCodigo());
        return guardar();
    }

    public String noValidar() {
        if (!planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getObservacionValidacion().isEmpty()) {
            planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(
                    EstadoPlanVacacionEnum.BORRADOR.getCodigo());
            return guardar();
        } else {
            mostrarMensajeEnPantalla("La observación es un campo requerido", FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * Permite regresar al listado.
     *
     * @return
     */
    public String irListaValidacion() {
        getPlanificacionVacacionValidacionHelper().getListaPlanificacionVacaciones().clear();
        getPlanificacionVacacionValidacionHelper().getListaVacacionVO().clear();
        planificacionVacacionValidacionHelper.setEjercicioFiscal(null);
        planificacionVacacionValidacionHelper.setEstado(null);
        return LISTA_ENTIDAD_VALIDACION;
    }

    /**
     * Permite Regresar.
     *
     * @return
     */
    public String salir() {
        getPlanificacionVacacionValidacionHelper().getListaPlanificacionVacaciones().clear();
        planificacionVacacionValidacionHelper.setEjercicioFiscal(null);
        planificacionVacacionValidacionHelper.setEstado(null);
        return PAGINA_PRINCIPAL;
    }

    /**
     * Setea los detalles vigentes de la instancia de planificacion del usuario
     * conectado.
     */
    public void buscarDetalles() {
        if (planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles() == null) {
            planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().setListaPlanificacionVacacionDetalles(
                    new ArrayList<PlanificacionVacacionDetalle>());
        }
        planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().clear();
        planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().setTotalDias(0);
        for (PlanificacionVacacionDetalle det
                : planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getPlanificacionVacacionDetalles()) {
            if (det.getVigente()) {
                planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().add(det);
                planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().setTotalDias((int) (planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getTotalDias()
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
        Servidor servidor = planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidor();
        if (servidor != null) {
            List<Vacacion> listaVacacion = planificacionVacacionValidacionHelper.getListaSaldoVacaciones();
            planificacionVacacionValidacionHelper.setCadenaSaldo("0 Días");
            for (Vacacion v : listaVacacion) {
                totalSaldo += v.getSaldo();
            }
            Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(totalSaldo,servidor.getJornada());
            planificacionVacacionValidacionHelper.setCadenaSaldo(
                    saldo[0] + " Días "
                    + saldo[1] + " Horas "
                    + saldo[2] + " Minutos ");
        }
    }

    /**
     * buscar listado de saldo de vacaciones por periodos.
     *
     */
    public void getListaSaldosVacaciones() {
        planificacionVacacionValidacionHelper.getListaSaldoVacaciones().clear();
        Servidor servidor = planificacionVacacionValidacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidor();
        try {
            Vacacion v = vacacionServicio.buscarVacacion(obtenerUsuarioConectado().getInstitucion().getId(), servidor.getId());
            if(v!=null){
                planificacionVacacionValidacionHelper.getListaSaldoVacaciones().add(v);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
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
     * Este metodo llena las opciones para el combo de Estados de la
     * planificación de vacaciones.
     */
    private void iniciarComboEstadoPlanifVacacion() {
        planificacionVacacionValidacionHelper.getListaOpcionesEstadoPlanifVacacion().clear();
        iniciarCombosTodos(planificacionVacacionValidacionHelper.getListaOpcionesEstadoPlanifVacacion());
        for (EstadoPlanVacacionEnum t : EstadoPlanVacacionEnum.values()) {
            if (!t.getCodigo().equals(EstadoPlanVacacionEnum.BORRADOR.getCodigo())) {
                planificacionVacacionValidacionHelper.getListaOpcionesEstadoPlanifVacacion().add(
                        new SelectItem(t.getCodigo(), t.getDescripcion()));
            }
        }
    }

    /**
     * Permite obtener Ejercicios fiscales vigentes y no vigentes.
     */
    private void iniciarComboInstitucionEjercicioFiscal() {
        List<InstitucionEjercicioFiscal> lista;
        try {
            lista = admServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            planificacionVacacionValidacionHelper.getListaOpcionesEjercicioFiscal().clear();
            iniciarCombos(planificacionVacacionValidacionHelper.getListaOpcionesEjercicioFiscal());
            for (InstitucionEjercicioFiscal c : lista) {
                planificacionVacacionValidacionHelper.getListaOpcionesEjercicioFiscal().add(
                        new SelectItem(c.getId(), c.getEjercicioFiscal().getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
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
            nodoPrincipal = new DefaultTreeNode(planificacionVacacionValidacionHelper.getListaUnidadesOrganizacionales().get(0), null);
            planificacionVacacionValidacionHelper.setRootUnidadOrganizacional(nodoPrincipal);
            /*
             * cargar los primeros nodos
             */
            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : planificacionVacacionValidacionHelper.getListaUnidadesOrganizacionales()) {
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

    private void llenarListaInicialUnidadesOrganizacionales() {
        try {
            if (planificacionVacacionValidacionHelper.getListaUnidadesOrganizacionales() == null) {
                planificacionVacacionValidacionHelper.setListaUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
            } else {
                planificacionVacacionValidacionHelper.getListaUnidadesOrganizacionales().clear();
            }
            List<UnidadOrganizacional> listarInstitucionesHijas
                    = admServicio.listarTodosUnidadOrganizacional();

            planificacionVacacionValidacionHelper.getListaUnidadesOrganizacionales().addAll(listarInstitucionesHijas);
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar las unidades organizacionales.", e);
        }
    }

    /**
     * seleccion de nodo unidad organizacional.
     */
    public void asignarUnidadOrganizacionalSeleccionada() {
        UnidadOrganizacional un = (UnidadOrganizacional) planificacionVacacionValidacionHelper.getUnidadSeleccionada().getData();
        planificacionVacacionValidacionHelper.setUnidadOrganizacional(un);
        ejecutarComandoPrimefaces("dlgAgregar.hide()");
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
     * @return the planificacionVacacionValidacionHelper
     */
    public PlanificacionVacacionValidacionHelper getPlanificacionVacacionValidacionHelper() {
        return planificacionVacacionValidacionHelper;
    }

    /**
     * @param planificacionVacacionValidacionHelper the
     * planificacionVacacionValidacionHelper to set
     */
    public void setPlanificacionVacacionValidacionHelper(PlanificacionVacacionValidacionHelper planificacionVacacionValidacionHelper) {
        this.planificacionVacacionValidacionHelper = planificacionVacacionValidacionHelper;
    }
}
