package ec.com.atikasoft.proteus.controlador.nomina;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.NominaDiferenciadaHelper;
import ec.com.atikasoft.proteus.controlador.helper.NominaNovedadHelper;
import ec.com.atikasoft.proteus.controlador.helper.ResultadoNominaHelper;
import static ec.com.atikasoft.proteus.controlador.nomina.NominaControlador.CONSULTA_NOMINA;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.NominaProblemaDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.dao.TipoNominaDao;
import ec.com.atikasoft.proteus.enums.ActivoInactivoEnum;
import ec.com.atikasoft.proteus.enums.ArchivoSipariEnum;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.ConsultaNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.EstadoLiquidacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AnticipoServicio;
import ec.com.atikasoft.proteus.servicio.ArchivoServicio;
import ec.com.atikasoft.proteus.servicio.LiquidacionServicio;
import ec.com.atikasoft.proteus.servicio.NominaCalculoServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.EjecucionNominaVO;
import ec.com.atikasoft.proteus.vo.NovedadNominaVO;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 * @author Liliana Rodriguez <liliana.rodriguez@atikasoft.com.ec>
 */
@ManagedBean(name = "nominaDiferenciadaBean")
@ViewScoped
public class NominaDiferenciadaControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(NominaDiferenciadaControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_NOMINA = "/pages/procesos/nomina/nomina_diferenciada.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_NOMINA = "/pages/procesos/nomina/lista_nominas_diferenciada.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String BUSQUEDA_NOMINA = "pages/consultas/consulta_nominas.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_RESULTADO_NOMINA = "/pages/procesos/nomina/resultado_nomina.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_NOVEDADES_NOMINA = "/pages/procesos/nomina/novedad_nomina.jsf";

    /**
     * url servlet descargas.
     */
    public static final String SERVET_DESCARGA = "/descargaServlet/";

    /**
     * Helper Nomina.
     */
    @ManagedProperty("#{nominaDiferenciadaHelper}")
    private NominaDiferenciadaHelper nominaDiferenciadaHelper;

    /**
     * Helper de resultado de la nomina.
     */
    @ManagedProperty("#{resultadoNominaHelper}")
    private ResultadoNominaHelper resultadoNominaHelper;

    /**
     * Helper de clase de novedades.
     */
    @ManagedProperty("#{nominaNovedadHelper}")
    private NominaNovedadHelper nominaNovedadHelper;

    /**
     * Servicio de archivos.
     */
    @EJB
    private ArchivoServicio archivoServicio;

    /**
     * DAO de Periodo Nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;

    /**
     * Servicio de servidor.
     */
    @EJB
    private LiquidacionServicio liquidacionServicio;

    /**
     * Servicio de servidor.
     */
    @EJB
    private AnticipoServicio anticipoServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de nomina.
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Dao de problemas de nomina.
     */
    @EJB
    private NominaProblemaDao nominaProblemaDao;

    /**
     * DAO de Tipo nomina.
     */
    @EJB
    private TipoNominaDao tipoNominaDao;

    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Servicio de nomina.
     */
    @EJB
    private NominaServicio nominaServicio;

    /**
     *
     */
    @EJB
    private NominaCalculoServicio nominaCalculoServicio;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        iniciarCatalogos();
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String listarNominas() {
        try {
            nominaDiferenciadaHelper.setListaNominas(nominaDao.buscarPorFiltros(nominaDiferenciadaHelper.getFiltro()));
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al buscar nominas por filtro", e);
        }

        return LISTA_NOMINA;
    }

    /**
     * Este metodo guarda la nomina.
     *
     * @return String
     */
    public String guardarNomina() {
        try {
            nominaDiferenciadaHelper.setNominaAprobada(Boolean.FALSE);
            if (nominaDiferenciadaHelper.getEsNuevo()) {
                nominaServicio.crear(nominaDiferenciadaHelper.getNomina(), obtenerUsuarioConectado());
                nominaServicio.guardarNominaHistorico(nominaDiferenciadaHelper.getNomina());
                nominaDiferenciadaHelper.setEsNuevo(Boolean.FALSE);
                mostrarMensajeEnPantalla("NOMINA GUARDADA", FacesMessage.SEVERITY_INFO);
            } else {
                iniciarDatosEntidad(nominaDiferenciadaHelper.getNomina(), Boolean.FALSE);

                if (nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())
                        && !verificarDetallesNomina()) {
                    mostrarMensajeEnPantalla("NO SE PUEDE VALIDAR LA NÓMINA PORQUE NO HA SIDO PROCESADA !!!",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())
                        && !nominaDiferenciadaHelper.getListaProblemas().isEmpty()) {
                    mostrarMensajeEnPantalla("EXISTEN PROBLEMAS EN LA NÓMINA PROCESADA !!!",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
                    nominaDiferenciadaHelper.getNomina().setFechaAprovacion(new Date());
                    nominaDiferenciadaHelper.setNominaAprobada(Boolean.TRUE);

                } else if (nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())
                        && !nominaDiferenciadaHelper.getListaTipoArchivoSIPARI().isEmpty()) {
                    archivoServicio.eliminarArchivoSipari(nominaDiferenciadaHelper.getListaTipoArchivoSIPARI(),
                            obtenerUsuarioConectado().getUsuario());
                }
                if (nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.ANULAR.getCodigo())
                        || nominaDiferenciadaHelper.getAccion().equals(EstadoNominaEnum.RECHAZAR.getCodigo())) {
                    anularNovedadesNomina();
                }
                nominaDiferenciadaHelper.getNomina().setEstado(nominaDiferenciadaHelper.getAccion());
                nominaServicio.actualizarNomina(nominaDiferenciadaHelper.getNomina());
                ejecutarComandoPrimefaces("confirmAccion.hide();");
                mostrarMensajeEnPantalla("La nómina ha sido enviada", FacesMessage.SEVERITY_INFO);
                if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
                    nominaDiferenciadaHelper.setCalcular(Boolean.TRUE);
                } else {
                    nominaDiferenciadaHelper.setCalcular(Boolean.FALSE);
                }
            }
            determinarAccion();

        } catch (ServicioException e) {
            error(this.getClass().getName(), "Error al crear la nómina.", e);
        }
        return iniciarCreacion();
    }

    /**
     *
     * @return
     */
    public boolean verificarDetallesNomina() {
        boolean hayDetalles = false;
        try {
            hayDetalles = nominaServicio.existeNomina(nominaDiferenciadaHelper.getNomina().getId());
        } catch (ServicioException ex) {
            error(this.getClass().getName(), "Error al buscar detalles de la nomina", ex);
        }
        return hayDetalles;
    }

    /**
     * Este metodo inicia la edicion de la nomin.
     *
     * @return String
     */
    public String iniciarCreacion() {
        String resultado = null;
        try {
            PeriodoNomina pn = periodoNominaDao.buscarPorFecha(new Date());
            if (pn == null) {
                error(this.getClass().getName(), "No existe un periodo de nómina activo.", null);
            } else {
                if (nominaDiferenciadaHelper.getListaPeriodoNomina().size() > 0) {
                    nominaDiferenciadaHelper.getListaPeriodoNomina().set(0, new SelectItem(null, "Seleccione.."));
                }
                nominaDiferenciadaHelper.setNomina(new Nomina());
                nominaDiferenciadaHelper.getNomina().setEstado(EstadoNominaEnum.ABIERTO.getCodigo());
                nominaDiferenciadaHelper.getNomina().setInstitucionEjercicioFiscalId(obtenerUsuarioConectado().
                        getInstitucionEjercicioFiscal().getId());
                nominaDiferenciadaHelper.getNomina().setPeriodoNomina(pn);
                nominaDiferenciadaHelper.getNomina().setPeriodoNominaId(pn.getId());
                nominaDiferenciadaHelper.getNomina().setTipoNomina(new TipoNomina());
                nominaDiferenciadaHelper.getNomina().setTipoNominaId(null);
                iniciarDatosEntidad(nominaDiferenciadaHelper.getNomina(), Boolean.TRUE);
                nominaDiferenciadaHelper.setEsNuevo(Boolean.TRUE);

                resultado = FORMULARIO_NOMINA;
            }
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al iniciar nuevo.", e);
        }
        return resultado;
    }

    /**
     *
     * @return
     */
    public String iniciarEdicion() {
        nominaDiferenciadaHelper.setEsNuevo(Boolean.FALSE);
        nominaDiferenciadaHelper.getNomina().setObservacion(null);
        determinarAccion();
        return FORMULARIO_NOMINA;
    }

    /**
     *
     * @param codigo
     * @return
     */
    public String obtenerDescripcionEstado(String codigo) {
        return EstadoNominaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Obtiene la descripcion de la cobertura del tipo de nomina.
     *
     * @param codigo
     * @return
     */
    public String obtenerDescripcionCobertura(String codigo) {
        return CoberturaNominaEnum.obtenerDescripcion(codigo);
    }

    /**
     *
     * @param codigo
     * @return
     */
    public String obtenerDescripcionTipoArchivo(String codigo) {
        return ArchivoSipariEnum.obtenerDescripcion(codigo);
    }

    /**
     *
     * @param codigo
     * @return
     */
    public String obtenerAccionEstado(String codigo) {
        String accion = EstadoNominaEnum.obtenerAccion(codigo);
        if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())
                && (codigo.equals(EstadoNominaEnum.VALIDADO.getCodigo()))) {
            return "DEVOLVER";
        }
        if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                && (codigo.equals(EstadoNominaEnum.ABIERTO.getCodigo()))) {
            return "DEVOLVER";
        }
        return accion;
    }

    /**
     * Carga los combos de seleccion de acuerdo al estado.
     */
    private void determinarAccion() {
        nominaDiferenciadaHelper.setCalcular(Boolean.FALSE);
        nominaDiferenciadaHelper.setNominaAprobada(Boolean.FALSE);
        nominaDiferenciadaHelper.setNominaAnulada(Boolean.FALSE);
        iniciarCombos(nominaDiferenciadaHelper.getListaAcciones());
        if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.VALIDADO.getCodigo(),
                    EstadoNominaEnum.VALIDADO.getAccion()));
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.ANULAR.getCodigo(),
                    EstadoNominaEnum.ANULAR.getAccion()));
            nominaDiferenciadaHelper.setCalcular(Boolean.TRUE);

        } else if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.VALIDADO.getCodigo())) {
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.APROBADO.getCodigo(),
                    EstadoNominaEnum.APROBADO.getAccion()));
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.ABIERTO.getCodigo(),
                    "DEVOLVER"));
        } else if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.PAGADO.getCodigo(),
                    EstadoNominaEnum.PAGADO.getAccion()));
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.VALIDADO.getCodigo(),
                    "DEVOLVER"));
            nominaDiferenciadaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.RECHAZAR.getCodigo(),
                    EstadoNominaEnum.RECHAZAR.getAccion()));
            nominaDiferenciadaHelper.setNominaAprobada(Boolean.TRUE);
            iniciarListaTipoArchivoSIPARI(false);
        } else if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ANULAR.getCodigo())) {
            nominaDiferenciadaHelper.setNominaAnulada(Boolean.TRUE);
        } else if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.RECHAZAR.getCodigo())) {
            nominaDiferenciadaHelper.setNominaAnulada(Boolean.TRUE);
        }
        nominaDiferenciadaHelper.getNomina().setObservacion(null);
        nominaDiferenciadaHelper.setAccion(null);
    }

    /**
     *
     * @return
     */
    public String iniciarCalculo() {
        cargarOpcionesCalculo();
        iniciarServidores();
        //iniciarTerceros();
        ejecutarComandoPrimefaces("dlgCalculoNomina.show();");
        return null;
    }

    /**
     *
     * @return
     */
    public String verObservaciones() {
        try {
            nominaDiferenciadaHelper.setListaProblemas(nominaProblemaDao.buscar(nominaDiferenciadaHelper.getNomina().
                    getId()));
            ejecutarComandoPrimefaces("dlgObservacionesNomina.show();");
        } catch (DaoException e) {
            error(getClass().getName(), NADA, e);
        }
        return null;
    }

    /**
     * Este metodo carga las opciones de calculo.
     */
    private void cargarOpcionesCalculo() {
        nominaDiferenciadaHelper.getDatosNomina().setTipoCalculo("P");
        nominaDiferenciadaHelper.getDatosNomina().setModalidadLaboralId(null);
        nominaDiferenciadaHelper.getDatosNomina().setNivelOcupacionalId(null);
        nominaDiferenciadaHelper.getDatosNomina().setRegimenLaboralId(null);
        nominaDiferenciadaHelper.getDatosNomina().getServidores().clear();
        nominaDiferenciadaHelper.getDatosNomina().getTerceros().clear();

    }

    /**
     * Este metodo inicia lo catalogos para la nomina.
     */
    private void iniciarCatalogos() {
        try {
            //Periodo fiscal            
            List<InstitucionEjercicioFiscal> ejercicios = institucionEjercicioFiscalDao.buscarPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            nominaDiferenciadaHelper.getListaPeriodoFiscal().clear();
            for (InstitucionEjercicioFiscal ef : ejercicios) {
                nominaDiferenciadaHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(),
                        ef.getEjercicioFiscal().getNombre()));
            }
            //Periodo nomina
            iniciarCombos(nominaDiferenciadaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos;
            if (nominaDiferenciadaHelper.getFiltro().getPeriodoFiscal() == null) {
                periodos = periodoNominaDao.buscarPorEjercicio(ejercicios.get(0).getId());
            } else {
                periodos = periodoNominaDao.buscarPorEjercicio(nominaDiferenciadaHelper.
                        getFiltro().getPeriodoFiscal());
            }
            for (PeriodoNomina periodo : periodos) {
                nominaDiferenciadaHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }

            nominaDiferenciadaHelper.getListaMensajes().clear();

            //Cobertura de Nomina
            iniciarCombos(nominaDiferenciadaHelper.getListaCoberturaNomina());
            nominaDiferenciadaHelper.getListaCoberturaNomina().add(
                    new SelectItem(CoberturaNominaEnum.ANTICIPOS.getCodigo(), CoberturaNominaEnum.ANTICIPOS.
                            getDescripcion().toUpperCase()));
            nominaDiferenciadaHelper.getListaCoberturaNomina().add(
                    new SelectItem(CoberturaNominaEnum.LIQUIDACIONES.getCodigo(), CoberturaNominaEnum.LIQUIDACIONES.
                            getDescripcion().toUpperCase()));
            nominaDiferenciadaHelper.getListaCoberturaNomina().add(
                    new SelectItem(CoberturaNominaEnum.TERCEROS.getCodigo(),
                            CoberturaNominaEnum.TERCEROS.getDescripcion().toUpperCase()));

            // Tipo de nomina
            iniciarCombos(nominaDiferenciadaHelper.getListaTipoNomina());
        } catch (DaoException e) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos: {0}", e);
        }
    }
    
     public void buscarPeriodos() {
        try {
            iniciarCombos(nominaDiferenciadaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos = periodoNominaDao.buscarPorEjercicio(
                    nominaDiferenciadaHelper.getFiltro().getPeriodoFiscal());
            iniciarCombosTodos(nominaDiferenciadaHelper.getListaPeriodoNomina());
            for (PeriodoNomina periodo : periodos) {
                nominaDiferenciadaHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de periodos", e);
        }
    }

    /**
     * Permite ir a la página para ver los resultados de la Nómina
     *
     * @return
     */
    public String irFormularioResultadoNomina() {
        getResultadoNominaHelper().setResultadoNomina(new ResultadoNominaVO());
        getResultadoNominaHelper().getResultadoNomina().setNomina(new Nomina());
        getResultadoNominaHelper().getResultadoNomina().setNomina(nominaDiferenciadaHelper.getNomina());
        getResultadoNominaHelper().getResultadoNomina().setNominaDetalle(new NominaDetalle());
        getResultadoNominaHelper().getResultadoNomina().getNominaDetalle().setNomina(
                nominaDiferenciadaHelper.getNomina());
        buscar();
        return FORMULARIO_RESULTADO_NOMINA;
    }

    /**
     * Permite ir a la página para ver los resultados de la Nómina
     *
     * @return
     */
    public String irFormularioNovedadesNomina() {
        getNominaNovedadHelper().setNovedadNominaVO(new NovedadNominaVO());
        getNominaNovedadHelper().setNomina(new Nomina());
        getNominaNovedadHelper().setNomina(nominaDiferenciadaHelper.getNomina());
        getNominaNovedadHelper().getNovedadNominaVO().setNovedad(new Novedad());
        buscarNovedades();
        return FORMULARIO_NOVEDADES_NOMINA;
    }

    /**
     * Permite buscar las novedades de una nomina.
     *
     * @return
     */
    public String buscarNovedades() {
        try {
            if (getNominaNovedadHelper().getNomina() != null) {
                getNominaNovedadHelper().getNovedadNominaVO().setListaNovedades(
                        administracionServicio.listarTodosNovedadesPorNominaId(getNominaNovedadHelper().getNomina().
                                getId()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de Novedades", ex);
        }
        return null;
    }

    /**
     * Enlista los tipos de nomina de acuerdo a la cobertura.
     *
     * @param codigoCobertura
     */
    public void iniciarTiposNomina(String codigoCobertura) {
        List<TipoNomina> tiposNomina;
        try {
            tiposNomina = tipoNominaDao.buscarVigente();
            iniciarCombos(nominaDiferenciadaHelper.getListaTipoNomina());
            for (TipoNomina tn : tiposNomina) {
                if (tn.getCobertura().equals(codigoCobertura)) {
                    nominaDiferenciadaHelper.getListaTipoNomina().add(new SelectItem(tn.getId(), tn.getNombre()));
                }
            }
        } catch (DaoException ex) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos de tipo de nomina: {0}", ex);
        }
    }

    /**
     * Enlista los servidores de acuerdo a la cobertura.
     */
    public void iniciarServidores() {
        try {
            nominaDiferenciadaHelper.getListaServidores().clear();
            nominaDiferenciadaHelper.getListaTerceros().clear();
            nominaDiferenciadaHelper.getListaLiquidaciones().clear();
            nominaDiferenciadaHelper.getListaAnticipos().clear();
            if (esAnticipo()) {
                List<Anticipo> listaAnticipo = anticipoServicio.listarTodosAnticiposPorEstado(EstadoAnticipoEnum.APROBADO.getCodigo(),
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                for (Anticipo a : listaAnticipo) {
                    nominaDiferenciadaHelper.getListaServidores().add(a.getServidor());
                    nominaDiferenciadaHelper.getListaAnticipos().add(a);
                }
            }
            if (esLiquidacion()) {
                List<Liquidacion> listaLiquidacion = liquidacionServicio.listarTodosLiquidacionesPorEstado(EstadoLiquidacionEnum.REGISTRADO.getCodigo());
                for (Liquidacion l : listaLiquidacion) {
                    nominaDiferenciadaHelper.getListaServidores().add(l.getServidor());
                    nominaDiferenciadaHelper.getListaLiquidaciones().add(l);
                }
            }

        } catch (ServicioException ex) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos DE SERVIDORES: {0}", ex);
        }
    }

    /**
     * Enlista los terceros a pagar.
     */
    public void iniciarTerceros() {
        try {
            nominaDiferenciadaHelper.getListaServidores().clear();
            nominaDiferenciadaHelper.getListaTerceros().clear();
            nominaDiferenciadaHelper.getListaTercerosSeleccionados().clear();
            if (nominaDiferenciadaHelper.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.
                    getCodigo())) {
                List<Tercero> listaTerceros = administracionServicio.listarTodosTerceroPorEstado(
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId(), ActivoInactivoEnum.ACTIVO.
                        getCodigo());
                for (Tercero a : listaTerceros) {
                    nominaDiferenciadaHelper.getListaTerceros().add(a);
                }
            }
        } catch (ServicioException ex) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos DE terceros {0}", ex);
        }
    }

    /**
     * Este método procesa la busqueda. Obtiene totales por grupos.
     *
     * @return String
     */
    public String buscar() {
        try {
            resultadoNominaHelper.getListaResultadoNomina().clear();
            resultadoNominaHelper.setListaResultadoNomina(nominaServicio.listarResultadoNomina(
                    resultadoNominaHelper.getResultadoNomina()));
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar los resultados de la nomina.", e);
        }
        return null;
    }

    /**
     * Obtiene los detalles de una nomina.
     *
     * @return
     */
    public String buscarDetalles() {
        try {
            resultadoNominaHelper.getListaDetallesIngresos().clear();
            resultadoNominaHelper.getListaDetallesDescuentos().clear();
            resultadoNominaHelper.getListaDetallesAportes().clear();
            resultadoNominaHelper.getResultadoNomina().inicializarTotalesServidor();
            List<NominaDetalle> listaDetNomina = nominaServicio.listarPorNominaYServidor(
                    resultadoNominaHelper.getResultadoNomina().getNomina().getId(),
                    resultadoNominaHelper.getResultadoNomina().getNumeroIdentificacion());
            for (NominaDetalle det : listaDetNomina) {
                if (det.getValorDescontadoRubroIngreso() != null && det.getTipo().equals("SER")) {
                    resultadoNominaHelper.getListaDetallesIngresos().add(det);
                    resultadoNominaHelper.getResultadoNomina().setTotalIngresosServidor(
                            resultadoNominaHelper.getResultadoNomina().getTotalIngresosServidor().add(det.
                                    getValorDescontadoRubroIngreso()));
                } else if (det.getValorDescontadoRubroDescuentos() != null && det.getTipo().equals("SER")) {
                    resultadoNominaHelper.getListaDetallesDescuentos().add(det);
                    resultadoNominaHelper.getResultadoNomina().setTotalDescuentosServidor(
                            resultadoNominaHelper.getResultadoNomina().getTotalDescuentosServidor().add(det.
                                    getValorDescontadoRubroDescuentos()));
                } else if (det.getValorDescontadoRubroAportes() != null && det.getTipo().equals("SER")) {
                    resultadoNominaHelper.getListaDetallesAportes().add(det);
                    resultadoNominaHelper.getResultadoNomina().setTotalAportePatronalServidor(
                            resultadoNominaHelper.getResultadoNomina().getTotalAportePatronalServidor().add(det.
                                    getValorDescontadoRubroAportes()));
                }
            }
            ejecutarComandoPrimefaces("dlgDetalleNomina.show()");
            actualizarComponente(":popDetalles");

        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar los detalles de los resultados de la nómina.", e);
        }
        return null;
    }

    /**
     * Navega hacia la página de Nómina.
     *
     * @return
     */
    public String irNomina() {
        return FORMULARIO_NOMINA;
    }

    /**
     * Navega hacia la página de Nómina.
     *
     * @return
     */
    public String irConsultaNomina() {
        return CONSULTA_NOMINA;
    }

    public Boolean esLiquidacion() {
        return nominaDiferenciadaHelper.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo());
    }

    public Boolean esAnticipo() {
        return nominaDiferenciadaHelper.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo());
    }

    /**
     *
     * @return
     */
    public String procesarNomina() {
        try {
            EjecucionNominaVO vo = new EjecucionNominaVO();
            List<Liquidacion> liquidacionesProcesar = new ArrayList<Liquidacion>();
            List<Anticipo> anticiposProcesar = new ArrayList<Anticipo>();
            if (esLiquidacion()) {
                for (Liquidacion l : nominaDiferenciadaHelper.getListaLiquidaciones()) {
                    if (l.getSelecto()) {
                        liquidacionesProcesar.add(l);
                    }
                }
            }
            if (esAnticipo()) {
                for (Anticipo a : nominaDiferenciadaHelper.getListaAnticipos()) {
                    if (a.getSelecto()) {
                        anticiposProcesar.add(a);
                    }
                }
            }
            if (liquidacionesProcesar.isEmpty() && anticiposProcesar.isEmpty()) {
                mostrarMensajeEnPantalla("DEBE SELECCIONAR POR LO MENOS UN REGISTRO", FacesMessage.SEVERITY_ERROR);
            } else {
                vo.setTerceros(nominaDiferenciadaHelper.getListaTercerosSeleccionados());
                vo.setTodos(nominaDiferenciadaHelper.getDatosNomina().getTipoCalculo().equals("C"));
                vo.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                vo.setLiquidaciones(liquidacionesProcesar);
                vo.setAnticipos(anticiposProcesar);
                nominaServicio.ejecutar(vo, nominaDiferenciadaHelper.getNomina().getId(), obtenerUsuarioConectado(),
                        getSession().getServletContext());
                List<NominaProblema> problemas = nominaProblemaDao.buscar(nominaDiferenciadaHelper.getNomina().getId());
                if (problemas.isEmpty()) {
                    mostrarMensajeEnPantalla("NÓMINA CALCULADA CORRECTAMENTE...", FacesMessage.SEVERITY_INFO);
                } else {
                    mostrarMensajeEnPantalla("EXISTE OBSERVACIONES EN LA NÓMINA...", FacesMessage.SEVERITY_ERROR);
                }
                ejecutarComandoPrimefaces("dlgCalculoNomina.hide();");

            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Nomina no pudo ser procesada {0}  ", e);
            mostrarMensajeEnPantalla("NÓMINA NO PUDO SER PROCESADA-" + limpiarMensajeDeExcepcion(e.getMessage()), FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String eliminarDetalles() {
        try {
            ObjetoNominaVO obj = new ObjetoNominaVO();
            obj.setNomina(nominaDiferenciadaHelper.getNomina());
            obj.setUsuario(obtenerUsuarioConectado());
            nominaCalculoServicio.eliminarDetallesNomina(obj);
            mostrarMensajeEnPantalla("Registros Eliminados", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Problemas al Eliminar detalles", FacesMessage.SEVERITY_ERROR);
            LOG.log(Level.INFO, "Problemas al eliminar detalles de la nomina {0}", ex);
        }
        return null;
    }

    /**
     * Carga las opciones para crear los diferentes archivos.
     *
     * @param popup
     * @return
     */
    public String iniciarListaTipoArchivoSIPARI(boolean popup) {
        nominaDiferenciadaHelper.getListaTipoArchivoSIPARI().clear();
        nominaDiferenciadaHelper.getArchivosSeleccionados().clear();
        if (nominaDiferenciadaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
            for (ArchivoSipari a : nominaDiferenciadaHelper.getNomina().getListaArchivoSIPARI()) {
                if (a.getVigente()) {
                    a.setSeleccionado(Boolean.TRUE);
                    nominaDiferenciadaHelper.getListaTipoArchivoSIPARI().add(a);
                    iniciarDatosEntidad(a, Boolean.FALSE);
                }
            }
            for (ArchivoSipariEnum tipo : ArchivoSipariEnum.values()) {

                if (!buscarArchivoPorNominaYTipo(tipo.getCodigo())) {
                    ArchivoSipari a = new ArchivoSipari();
                    a.setTipo(tipo.getCodigo());
                    iniciarDatosEntidad(a, Boolean.TRUE);
                    a.setNomina(nominaDiferenciadaHelper.getNomina());
                    nominaDiferenciadaHelper.getListaTipoArchivoSIPARI().add(a);
                }
            }

            if (popup) {
                actualizarComponente("frmMenu:tablaArchivos");
                ejecutarComandoPrimefaces("confirmArchivo.show();");
            }
        }
        return null;
    }

    /**
     * Verifica si el archivo ya fue creado.
     *
     * @param tipo
     * @return
     */
    private boolean buscarArchivoPorNominaYTipo(String tipo) {
        boolean agregado = false;
        if (!nominaDiferenciadaHelper.getListaTipoArchivoSIPARI().isEmpty()) {
            for (ArchivoSipari a : nominaDiferenciadaHelper.getListaTipoArchivoSIPARI()) {
                if (a.getId() != null && a.getTipo().equals(tipo)) {
                    return true;
                }
            }
        }
        return agregado;
    }

    /**
     * *
     *
     * @param a
     */
    public void esSeleccionado(ArchivoSipari a) {
        if (a.getSeleccionado() != null && a.getSeleccionado()) {
            nominaDiferenciadaHelper.getArchivosSeleccionados().add(a);
        } else {
            nominaDiferenciadaHelper.getArchivosSeleccionados().remove(a);
        }
    }

    /**
     * Permite mostrar el modal panel que confirma y jecuta el cambio de estado.
     *
     * @return
     */
    public String activarPanelActualizarEstado() {
        if (nominaDiferenciadaHelper.getNomina().getObservacion() == null | nominaDiferenciadaHelper.getNomina().
                getObservacion().isEmpty()) {
            mostrarMensajeEnPantalla("El campo Comentario es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (nominaDiferenciadaHelper.getAccion() == null) {
            mostrarMensajeEnPantalla("El campo Acción es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ejecutarComandoPrimefaces("confirmAccion.show();");
        return null;
    }

    /**
     * Envia a registrar en la tabla de archivos.
     *
     * @param a
     * @return
     */
    public String generarArchivoSipari(ArchivoSipari a) {
        try {
            nominaDiferenciadaHelper.getListaMensajes().clear();
            a.setSeleccionado(Boolean.TRUE);
            nominaDiferenciadaHelper.getArchivosSeleccionados().add(a);
            nominaDiferenciadaHelper.setListaMensajes(archivoServicio.guardarArchivoSipari(nominaDiferenciadaHelper.
                    getArchivosSeleccionados()));
            if (nominaDiferenciadaHelper.getListaMensajes().isEmpty() && a.getId() != null) {
                nominaDiferenciadaHelper.getNomina().getListaArchivoSIPARI().add(a);
                iniciarListaTipoArchivoSIPARI(false);
                mostrarMensajeEnPantalla("Los registros fueron guardados exitosamente", FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla("Problemas al guardar archivo", FacesMessage.SEVERITY_ERROR);
            }

            LOG.log(Level.INFO, "Fin generacion de archivo:{0}", a.getTipo());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla("Error al crear archivos sipari", FacesMessage.SEVERITY_ERROR);
            error(this.getClass().getName(), "Error al crear archivos sipari.", ex);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        nominaDiferenciadaHelper.getListaTipoNominaEnum().clear();
        for (ConsultaNominaEnum t : ConsultaNominaEnum.values()) {
            nominaDiferenciadaHelper.getListaTipoNominaEnum().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * recoorrer la lista de nominas para ver que rporte quiero presentar.
     */
    public void asignarNominaCodigo() {
        for (Nomina n : nominaDiferenciadaHelper.getListaNominas()) {
            nominaDiferenciadaHelper.setNominaEnumCodigo(nominaDiferenciadaHelper.getNominaEnumCodigo());
        }
    }

    /**
     * Invoca el servlet para descargar los archivos
     *
     * @param id
     * @param nombre
     * @param tipo
     * @return
     */
    public String invocarServlet(Long id, String nombre, String tipo) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().
                getContext();
        String url = servletContext.getContextPath();
        String cadena = UtilCadena.concatenar(url, SERVET_DESCARGA, id, "+", nombre, "+", tipo);
        return cadena;
    }

    @Override
    public void generarReporte() {
        if (nominaDiferenciadaHelper.getNominaEnumCodigo() != null) {
            if (nominaDiferenciadaHelper.getNominaEnumCodigo().equals(ReportesEnum.NOMINA.getReporte())) {
                setReporte(ReportesEnum.NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE DE NOMINA");
                parametrosReporte.put("nominaId", nominaDiferenciadaHelper.getNomina().getId().toString());
            } else if (nominaDiferenciadaHelper.getNominaEnumCodigo().equals(ReportesEnum.TOTAL_NOMINA.getReporte())) {
                setReporte(ReportesEnum.TOTAL_NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE TOTAL NOMINA");
                parametrosReporte.put("nominaId", nominaDiferenciadaHelper.getNomina().getId().toString());
            }
            generarUrlDeReporte();
        } else {
            mostrarMensajeEnPantalla("No se a seleccionado un reporte", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * Acciones cuando una opcion es seleccionada
     *
     * @param ter
     */
    public void onRubroPeriodoNominaSeleccionado(Tercero ter) {
        if (ter.getSeleccionado()) {
            nominaDiferenciadaHelper.getListaTercerosSeleccionados().add(ter);
        } else {
            nominaDiferenciadaHelper.getListaTercerosSeleccionados().remove(ter);
        }
    }

    /**
     * Si la nómina es anulada o rechazada, se inactivan las novedades
     * asociadas.
     */
    private void anularNovedadesNomina() {
        try {
            buscarNovedades();
            for (Novedad nov : getNominaNovedadHelper().getNovedadNominaVO().getListaNovedades()) {
                iniciarDatosEntidad(nov, Boolean.FALSE);
                administracionServicio.eliminarNovedad(nov);
            }
        } catch (ServicioException ex) {
            error(this.getClass().getName(), "Error al eliminar novedades de la nómina.", ex);
        }
    }

    /**
     *
     * @return the nominaDiferenciadaHelper
     */
    public NominaDiferenciadaHelper getNominaDiferenciadaHelper() {
        return nominaDiferenciadaHelper;
    }

    /**
     * @param nominaDiferenciadaHelper the nominaDiferenciadaHelper to set
     */
    public void setNominaDiferenciadaHelper(final NominaDiferenciadaHelper nominaDiferenciadaHelper) {
        this.nominaDiferenciadaHelper = nominaDiferenciadaHelper;
    }

    /**
     * @return the resultadoNominaHelper
     */
    public ResultadoNominaHelper getResultadoNominaHelper() {
        return resultadoNominaHelper;
    }

    /**
     * @param resultadoNominaHelper the resultadoNominaHelper to set
     */
    public void setResultadoNominaHelper(ResultadoNominaHelper resultadoNominaHelper) {
        this.resultadoNominaHelper = resultadoNominaHelper;
    }

    /**
     * @return the nominaNovedadHelper
     */
    public NominaNovedadHelper getNominaNovedadHelper() {
        return nominaNovedadHelper;
    }

    /**
     * @param nominaNovedadHelper the nominaNovedadHelper to set
     */
    public void setNominaNovedadHelper(NominaNovedadHelper nominaNovedadHelper) {
        this.nominaNovedadHelper = nominaNovedadHelper;
    }
}
