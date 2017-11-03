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
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PlanificacionVacacionHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * PlanificacionVacacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionVacacionBean")
@ViewScoped
public class PlanificacionVacacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionVacacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/planificacion_vacacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_planificacion_vacacion.jsf";
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
    @ManagedProperty("#{planificacionVacacionHelper}")
    private PlanificacionVacacionHelper planificacionVacacionHelper;

    /**
     * Constructor por defecto.
     */
    public PlanificacionVacacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(planificacionVacacionHelper);
        setPlanificacionVacacionHelper(planificacionVacacionHelper);
        obtenerParametroInstitucional();
        iniciarComboEjercicioFiscal();
        planificacionVacacionHelper.setPeriodofiscal(null);
        planificacionVacacionHelper.setMensajes("");
        planificacionVacacionHelper.getListaPlanificacionVacaciones().clear();
    }

    @Override
    public String iniciarEdicion() {
        try {
            UsuarioVO usuario = obtenerUsuarioConectado();
            Object cloneBean
                    = BeanUtils.cloneBean(planificacionVacacionHelper.getPlanificacionVacacionEditDelete());
            PlanificacionVacacion d = (PlanificacionVacacion) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            planificacionVacacionHelper.setPlanificacionVacacionVO(new PlanificacionVacacionVO());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setPlanificacionVacacion(d);
            planificacionVacacionHelper.setEsNuevo(Boolean.FALSE);
            planificacionVacacionHelper.getPlanificacionVacacionVO().setDistributivoDetalle(usuario.getDistributivoDetalle());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setFechaIngreso(usuario.getServidor().getFechaIngresoInstitucion());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setPlanificacionVacacionDetalle(new PlanificacionVacacionDetalle());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setFechaInicio(null);
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setFechaFin(null);
            buscarDetalles();
            planificacionVacacionHelper.setHayParametros(obtenerParametrizacionVacacion());
            if (!planificacionVacacionHelper.isHayParametros()) {
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                planificacionVacacionHelper.setBotonGuardar(false);
                return FORMULARIO_ENTIDAD;
            } else {
                planificacionVacacionHelper.setBotonGuardar(true);
            }
            obtenerSaldoVacacionServidor();

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        try {

            UsuarioVO usuario = obtenerUsuarioConectado();
            planificacionVacacionHelper.iniciador();
            planificacionVacacionHelper.setPlanificacionVacacionVO(new PlanificacionVacacionVO());
            iniciarDatosEntidad(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.TRUE);
            planificacionVacacionHelper.setEsNuevo(Boolean.TRUE);
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(EstadoPlanVacacionEnum.BORRADOR.getCodigo());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setServidorId(usuario.getServidor().getId());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setServidor(usuario.getServidor());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setDistributivoDetalle(usuario.getDistributivoDetalle());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setFechaIngreso(usuario.getServidor().getFechaIngresoInstitucion());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setPlanificacionVacacionDetalle(new PlanificacionVacacionDetalle());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setFechaInicio(null);
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setFechaFin(null);

            if (buscarInstitucionEjericioFiscalPosterior()) {
                planificacionVacacionHelper.setHayParametros(obtenerParametrizacionVacacion());
                if (!planificacionVacacionHelper.isHayParametros()) {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                    planificacionVacacionHelper.setBotonGuardar(false);
                    return LISTA_ENTIDAD;
                } else {
                    planificacionVacacionHelper.setBotonGuardar(true);
                    obtenerSaldoVacacionServidor();
                    return FORMULARIO_ENTIDAD;

                }
            } else {
                return LISTA_ENTIDAD;
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String guardar() {
        try {

            if (!planificacionVacacionHelper.getDiasDisponiblesVacaciones().equals(0)) {
                mostrarMensajeEnPantalla("Debe planificar todos los días de vacaciones pendientes. Le faltan planificar "
                        + planificacionVacacionHelper.getDiasDisponiblesVacaciones() + " días.", FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (planificacionVacacionHelper.getDiasDisponiblesVacaciones() < 0) {
                mostrarMensajeEnPantalla("Ud. ha planificado "
                        + Math.abs(planificacionVacacionHelper.getDiasDisponiblesVacaciones()) + " días en exceso.", FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().size() > 0) {

                if (planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias().compareTo(planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias()) > 0) {
                    mostrarMensajeEnPantalla("Sobrepasa el total de días permitidos para vacaciones, que es " + planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias(), FacesMessage.SEVERITY_INFO);
                    return null;
                }

                if (planificacionVacacionHelper.getEsNuevo()) {
                    if (validarExisteProyeccionEnEjercicioFiscal()) {
                        mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                        return null;
                    }

                    vacacionServicio.guardarPlanificacionVacacion(
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal(),
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles());
                    planificacionVacacionHelper.setEsNuevo(Boolean.FALSE);
                    iniciarDatosEntidad(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.FALSE);
                } else {
                    vacacionServicio.actualizarPlanificacionVacacion(
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(),
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles(),
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaDetallesEliminados(),
                            planificacionVacacionHelper.getObservacionReprogramacion());
                }

                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

            } else {
                mostrarMensajeEnPantalla("Al menos ingresar un registro en la Planificación de Vacaciones.", FacesMessage.SEVERITY_ERROR);
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    public String validarCamposObligatorios() {
        if (planificacionVacacionHelper.getObservacionReprogramacion() == null
                || planificacionVacacionHelper.getObservacionReprogramacion().isEmpty()) {
            mostrarMensajeEnPantalla("El campo observación es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ejecutarComandoPrimefaces("confirmReprogramar.show();");
        return null;
    }

    public String reprogramar() {
        try {
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(
                    EstadoPlanVacacionEnum.BORRADOR.getCodigo());
            iniciarDatosEntidad(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.FALSE);
            vacacionServicio.actualizarPlanificacionVacacion(
                    planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(),
                    planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles(),
                    planificacionVacacionHelper.getPlanificacionVacacionVO().getListaDetallesEliminados(),
                    planificacionVacacionHelper.getObservacionReprogramacion());
            ejecutarComandoPrimefaces("confirmReprogramar.hide();");
            actualizarComponente("frmPlanificacionVacacion");
            mostrarMensajeEnPantalla("Registro listo para replanificar", FacesMessage.SEVERITY_INFO);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return irLista();
    }

    /**
     * Cambia estado de registro a enviado.
     *
     * @return
     */
    public String enviar() {
        try {
            if (planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getEstado().equals(EstadoPlanVacacionEnum.BORRADOR.getCodigo())) {
                iniciarDatosEntidad(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.FALSE);
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setEstado(EstadoPlanVacacionEnum.ENVIADO.getCodigo());
                vacacionServicio.actualizarPlanificacionVacacion(
                        planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion());
                mostrarMensajeEnPantalla(REGISTRO_ENVIADO, FacesMessage.SEVERITY_INFO);
                planificacionVacacionHelper.setEsNuevo(Boolean.FALSE);
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al enviar ", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe una proyección de vacaciones para el
     * servidor y ejercicio fiscal.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarExisteProyeccionEnEjercicioFiscal() {
        Boolean hayCodigo = false;
        try {
            planificacionVacacionHelper.getListaPlanificacionVacacionCodigo().clear();
            planificacionVacacionHelper.setListaPlanificacionVacacionCodigo(
                    vacacionServicio.listarTodosPlanificacionVacacionPorServidorYEjercicioFiscal(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidor().getId(),
                            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getInstitucionEjercicioFiscalId()));

            if (!planificacionVacacionHelper.getListaPlanificacionVacacionCodigo().isEmpty()) {
                hayCodigo = true;
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la existencia de proyeccion de vacaciones", ex);
        }
        return hayCodigo;
    }

    /**
     *
     */
    public void obtenerParametroInstitucional() {
        boolean activarBotonNuevo = false;
        try {
            ParametroInstitucional articulo = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.PLANIFICACION_ANUAL_VACACIONES.getCodigo());
            if (articulo != null && !articulo.getValorTexto().isEmpty()) {
                if (articulo.getValorTexto().toUpperCase().equals(SiNoEnum.SI.getDescripcion().toUpperCase())) {
                    activarBotonNuevo = true;
                } else if (articulo.getValorTexto().toUpperCase().equals(SiNoEnum.SI.getCodigo().toUpperCase())) {
                    activarBotonNuevo = true;
                } else if (articulo.getValorTexto().substring(0, 1).toUpperCase().equals(SiNoEnum.SI.getCodigo().toUpperCase())) {
                    activarBotonNuevo = true;
                } else {
                    planificacionVacacionHelper.setMensajes("La configuración de parámetros institucionales indica que la planificación de vacaciones se encuentra inhabilitada.");
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de parametros institucionales", ex);
        }
        planificacionVacacionHelper.setEditarNuevo(activarBotonNuevo);
        LOG.log(Level.INFO, "Parametro Institucional para planificacion  de vacaciones: {0}", activarBotonNuevo);
    }

    /**
     * Se obtiene del ultimo registro de vacaciones(acumulacion) de acuerdo a la
     * fecha de creacion Alli se registra el saldo final de la transacción. Si
     * no existen registros el saldo es 0 minutos
     *
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void obtenerSaldoVacacionServidor() throws DaoException {
        planificacionVacacionHelper.getPlanificacionVacacionVO().setSaldoVacacion(0l);
        Servidor s = planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getServidor();
        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(obtenerUsuarioConectado().getInstitucion().getId(),
                s.getId());
        Vacacion v = vacacionDao.buscarPorServidor(si.getId());
        if (v != null) {
            Integer[] saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(v.getSaldo()), s.getJornada());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setSaldoVacacionTexto(
                    UtilCadena.concatenar(saldo[0], " Días, ", saldo[1], " Horas, " + saldo[2], " Minutos"));
            planificacionVacacionHelper.getPlanificacionVacacionVO().setSaldoVacacion(v.getSaldo());

        }
    }

    @Override
    public String borrar() {
        return null;
    }

    @Override
    public String buscar() {
        try {
            if (planificacionVacacionHelper.getListaPlanificacionVacacionDet() == null) {
                planificacionVacacionHelper.setListaPlanificacionVacacionDet(
                        new ArrayList<PlanificacionVacacionDetalle>());
            }
            planificacionVacacionHelper.getListaPlanificacionVacacionDet().clear();
            if (planificacionVacacionHelper.getPeriodofiscal() != null) {
                planificacionVacacionHelper.setListaPlanificacionVacacionDet(
                        vacacionServicio.listarTodosPlanificacionVacacionDetallePorServidorYEjercicioFiscal(
                                obtenerUsuarioConectado().getServidor().getId(), planificacionVacacionHelper.getPeriodofiscal()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public Long buscarListaSaldoVacaciones() {
        Long saldo = 0L;
        try {
            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaSaldoVacacion().clear();
            PlanificacionVacacion pv = planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion();
            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                    pv.getInstitucionEjercicioFiscal().getInstitucion().getId(), pv.getServidorId());
            Vacacion v = vacacionDao.buscarPorServidor(si.getId());
            if (v != null) {
                planificacionVacacionHelper.getPlanificacionVacacionVO().getListaSaldoVacacion().add(v);
                saldo = v.getSaldo();
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return saldo;
    }
    /*
     * Setea los detalles vigentes de la instancia de planificacion del usuario conectado
     */

    public void buscarDetalles() {
        if (planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles() == null) {
            planificacionVacacionHelper.getPlanificacionVacacionVO().setListaPlanificacionVacacionDetalles(
                    new ArrayList<PlanificacionVacacionDetalle>());
        }
        planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().clear();
        planificacionVacacionHelper.getPlanificacionVacacionVO().setTotalDias(0);
        for (PlanificacionVacacionDetalle det
                : planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getPlanificacionVacacionDetalles()) {
            if (det.getVigente()) {
                planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().add(det);
                planificacionVacacionHelper.getPlanificacionVacacionVO().setTotalDias((int) (planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias()
                        + det.getNumeroDias()));
            }
        }
        LOG.log(Level.INFO, " buscarDetalles==>{0}", planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().size());
    }

    /**
     * Permite obtener Ejercicios fiscales vigentes y no vigentes.
     */
    private void iniciarComboEjercicioFiscal() {
        planificacionVacacionHelper.setListaInstitucionEjercicioFiscal(buscarTodosEjerciciosFiscales());
        planificacionVacacionHelper.getOpcionesEjercicioFiscal().clear();
        iniciarCombos(planificacionVacacionHelper.getOpcionesEjercicioFiscal());
        for (InstitucionEjercicioFiscal c : planificacionVacacionHelper.getListaInstitucionEjercicioFiscal()) {
            planificacionVacacionHelper.getOpcionesEjercicioFiscal().add(new SelectItem(c.getId(), c.getEjercicioFiscal().getNombre()));
        }
    }

    private List<InstitucionEjercicioFiscal> buscarTodosEjerciciosFiscales() {
        List<InstitucionEjercicioFiscal> lista = new ArrayList<InstitucionEjercicioFiscal>();
        try {
            lista = admServicio.listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicios fiscales", ex);
        }
        return lista;
    }

    /**
     * *
     * Busca siguiente ejericio fiscal en institucion ejericioc fiscal.
     *
     * @return
     */
    public boolean buscarInstitucionEjericioFiscalPosterior() {
        boolean existe = true;
        try {
            UsuarioVO usuario = obtenerUsuarioConectado();
            planificacionVacacionHelper.setMensajes(null);
            InstitucionEjercicioFiscal ejeActual, ejeSiguiente = null;
            if (planificacionVacacionHelper.getListaInstitucionEjercicioFiscal().isEmpty()) {
                planificacionVacacionHelper.setListaInstitucionEjercicioFiscal(buscarTodosEjerciciosFiscales());
            }
            ejeActual = usuario.getInstitucionEjercicioFiscal();
            for (InstitucionEjercicioFiscal i : planificacionVacacionHelper.getListaInstitucionEjercicioFiscal()) {
                if (!ejeActual.getId().equals(i.getId()) && UtilFechas.validarFechaInicioFin(ejeActual.getEjercicioFiscal().getFechaFin(), i.getEjercicioFiscal().getFechaInicio())) {
                    ejeSiguiente = i;
                    break;
                }
            }
            if (ejeSiguiente != null) {
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setInstitucionEjercicioFiscal(ejeSiguiente);
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().setInstitucionEjercicioFiscalId(ejeSiguiente.getId());
            } else {
                planificacionVacacionHelper.setMensajes("No se puede obtener el siguiente ejercicio fiscal.");
                mostrarMensajeEnPantalla(planificacionVacacionHelper.getMensajes(), FacesMessage.SEVERITY_ERROR);
                planificacionVacacionHelper.setBotonGuardar(false);
                existe = false;
            }
        } catch (Exception ex) {
            existe = false;
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar ejercicio fiscal", ex);
        }
        return existe;
    }

    /**
     * Permite obtener la distribucion de puestos para el servidor<p>
     * Con esta información se carga el Ejercicio Fiscal y se obtiene el
     * sueldo(rmu) base sobre el cual se calculan los ingresos del servidor.
     *
     * @return true si existe la configuracion de puestos de trabajo </p>
     */
    public boolean obtenerParametrizacionVacacion() {
        boolean hayConfiguracion = false;
        try {
            if (planificacionVacacionHelper.getPlanificacionVacacionVO().getDistributivoDetalle() != null) {
                List<VacacionParametro> listaParametros
                        = vacacionServicio.listarTodosVacacionParametroPorRegimenLaboral(
                                planificacionVacacionHelper.getPlanificacionVacacionVO().getDistributivoDetalle().getEscalaOcupacional()
                                .getNivelOcupacional().getRegimenLaboral().getId());
                for (VacacionParametro p : listaParametros) {
                    planificacionVacacionHelper.getPlanificacionVacacionVO().setVacacionParametro(p);
                    hayConfiguracion = true;
                    break;
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda parametrizacion", ex);
        }
        return hayConfiguracion;
    }

    /**
     * Obtiene el numero de dias.
     */
    public void calcularNumeroDias() {
        Long dias = UtilFechas.calcularDiferenciaDiasEntreFechas(
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getFechaInicio(),
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getFechaFin());
        planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setNumeroDias(dias);
    }

    private boolean verificaFechaEnDetalles(Date fi, Date ff) {
        for (PlanificacionVacacionDetalle det
                : planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles()) {
            if (!det.getEstado().equals(EstadoVacacionDetalleEnum.REPROGRAMADO.getCodigo()) | !det.getVigente()) {
                if (UtilFechas.between(fi, det.getFechaInicio(), det.getFechaFin())) {
                    return true;
                }
                if (UtilFechas.between(ff, det.getFechaInicio(), det.getFechaFin())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Agrega detalles a la planificacion de vacaciones.
     */
    public void agregarDetalles() {

        Date fi, ff;

        fi = planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getFechaInicio();
        ff = planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getFechaFin();

        if (ff == null || fi == null) {
            mostrarMensajeEnPantalla("Fecha Inicio, Fecha Fin y Número de días son campos requeridos.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (!UtilFechas.validarFechaInicioFin(fi, ff)) {
            mostrarMensajeEnPantalla("La fecha fin debe ser menor a la fecha inicio.", FacesMessage.SEVERITY_ERROR);
            return;
        }
        if (planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias() <= 0) {
            mostrarMensajeEnPantalla("Verificar Fechas.", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (verificaFechaEnDetalles(fi, ff)) {
            mostrarMensajeEnPantalla("Periodo ya ingresado en esta Planificación.", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (!planificacionVacacionHelper.isHayParametros()) {
            mostrarMensajeEnPantalla("NO HAY CONFIGURACION DE PARAMETROS DE VACACIONES", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (planificacionVacacionHelper.getPlanificacionVacacionVO().getVacacionParametro() == null) {
            mostrarMensajeEnPantalla("NO HAY CONFIGURACION DE PARAMETROS DE VACACIONES", FacesMessage.SEVERITY_ERROR);
            return;
        }

        calcularNumeroDias();
        Long dias = planificacionVacacionHelper.getPlanificacionVacacionVO().getVacacionParametro().getNumeroDias()
                + planificacionVacacionHelper.getPlanificacionVacacionVO().getSaldoVacacion();
        Long tdias = planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias()
                +  planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias();
        Integer diasMinVacaciones = planificacionVacacionHelper.getPlanificacionVacacionVO().getVacacionParametro().getMinimoDiasTomarVacacion();
        Long difDias = dias - planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias();

        /*if(difDias < diasMinVacaciones && 
         planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias()< difDias){
         mostrarMensajeEnPantalla("Puede tomar mínimo "+difDias+" días continuos de Vacaciones.", FacesMessage.SEVERITY_ERROR);
         return;
         }
         if(difDias >= diasMinVacaciones && 
         planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias()< diasMinVacaciones){
         mostrarMensajeEnPantalla("Puede tomar mínimo "+diasMinVacaciones+" días continuos de Vacaciones.", FacesMessage.SEVERITY_ERROR);
         return;
         }*/
        if (planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias() < diasMinVacaciones) {
            mostrarMensajeEnPantalla("Debe solicitar como mínimo " + diasMinVacaciones + " días de Vacaciones.", FacesMessage.SEVERITY_ERROR);
            return;
        }

        if (tdias.compareTo(dias) <= 0) {

            if (!planificacionVacacionHelper.getEsNuevo()) {
                planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setPlanificacionVacacionId(
                        planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion().getId());
            }
            iniciarDatosEntidad(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle(), Boolean.TRUE);
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setPlanificacionVacacion(
                    planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacion());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setEstado(
                    EstadoVacacionDetalleEnum.DISPONIBLE.getCodigo());
            planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().setVigente(Boolean.TRUE);

            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().add(
                    planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle());
            planificacionVacacionHelper.getPlanificacionVacacionVO().setTotalDias((int) (planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias()
                    + planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle().getNumeroDias()));
            planificacionVacacionHelper.getPlanificacionVacacionVO().setPlanificacionVacacionDetalle(new PlanificacionVacacionDetalle());

//            planificacionVacacionHelper.setDiasDisponiblesVacaciones(/*planificacionVacacionHelper.getPlanificacionVacacionVO().getVacacionParametro().getNumeroDias()
//                     + */planificacionVacacionHelper.getPlanificacionVacacionVO().getSaldoDiasVacacion() - planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias());

        } else {
            mostrarMensajeEnPantalla("Sobrepasa el total de días permitidos para vacaciones, que es " + dias, FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Elimina de la lista los detalles agregados. Agrega a una nueva lista los
     * elementos eliminados que tengan Id.
     */
    public void eliminarDetalles() {
        eliminarDetalles(planificacionVacacionHelper.getPlanificacionVacacionVO().getPlanificacionVacacionDetalle());
    }

    public void eliminarDetalles(PlanificacionVacacionDetalle det) {
        if (det != null) {

            if (det.getId() != null) {
                planificacionVacacionHelper.getPlanificacionVacacionVO().getListaDetallesEliminados().add(det);
            }
            planificacionVacacionHelper.getPlanificacionVacacionVO().getListaPlanificacionVacacionDetalles().remove(det);
            planificacionVacacionHelper.getPlanificacionVacacionVO().setTotalDias((int) (planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias()
                    - det.getNumeroDias()));

//            planificacionVacacionHelper.setDiasDisponiblesVacaciones(/*planificacionVacacionHelper.getPlanificacionVacacionVO().getVacacionParametro().getNumeroDias()*/
//                    +planificacionVacacionHelper.getPlanificacionVacacionVO().getSaldoDiasVacacion() - planificacionVacacionHelper.getPlanificacionVacacionVO().getTotalDias());

        }
    }

    /**
     * Permite regresar al listado
     *
     * @return
     */
    public String irLista() {
        planificacionVacacionHelper.getListaPlanificacionVacacionDet().clear();
        planificacionVacacionHelper.setPeriodofiscal(null);
        return LISTA_ENTIDAD;
    }

    /**
     * Permite regresar al listado
     *
     * @return
     */
    public String irNuevo() {

        planificacionVacacionHelper.getListaPlanificacionVacacionDet().clear();
        planificacionVacacionHelper.setPeriodofiscal(null);
        iniciarNuevo();
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        planificacionVacacionHelper.getListaPlanificacionVacacionDet().clear();
        planificacionVacacionHelper.setPeriodofiscal(null);
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
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacion(final String codigo) {
        return EstadoPlanVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacionDetalle(final String codigo) {
        return EstadoVacacionDetalleEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the planificacionVacacionHelper
     */
    public PlanificacionVacacionHelper getPlanificacionVacacionHelper() {
        return planificacionVacacionHelper;
    }

    /**
     * @param planificacionVacacionHelper the planificacionVacacionHelper to set
     */
    public void setPlanificacionVacacionHelper(PlanificacionVacacionHelper planificacionVacacionHelper) {
        this.planificacionVacacionHelper = planificacionVacacionHelper;
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
}
