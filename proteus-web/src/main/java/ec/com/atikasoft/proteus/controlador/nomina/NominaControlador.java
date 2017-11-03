package ec.com.atikasoft.proteus.controlador.nomina;

import au.com.bytecode.opencsv.CSVWriter;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.NominaHelper;
import ec.com.atikasoft.proteus.controlador.helper.NominaNovedadHelper;
import ec.com.atikasoft.proteus.controlador.helper.ResultadoNominaHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.NivelOcupacionalDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.NominaEjecucionDao;
import ec.com.atikasoft.proteus.dao.NominaIRDao;
import ec.com.atikasoft.proteus.dao.NominaProblemaDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.dao.TipoNominaDao;
import ec.com.atikasoft.proteus.enums.ArchivoSipariEnum;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.ConsultaNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.nomina.NominaIR;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ArchivoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.NominaCalculoServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.servicio.ProblemaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.EjecucionNominaVO;
import ec.com.atikasoft.proteus.vo.NovedadNominaVO;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "nominaBean")
@ViewScoped
public class NominaControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(NominaControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_NOMINA = "/pages/procesos/nomina/nomina.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String CONSULTA_NOMINA = "/pages/consultas/consulta_nominas.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_NOMINA_DIFERENCIADA = "/pages/procesos/nomina/nomina_diferenciada.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_NOMINA = "/pages/procesos/nomina/lista_nominas.jsf";

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
     * Regla de navegacion.
     */
    public static final String EJECUCION_NOMINA = "/pages/procesos/nomina/ejecucion.jsf";

    /**
     * url servlet descargas.
     */
    public static final String SERVET_DESCARGA = "/descargaServlet/";

    /**
     * Fabrica de colas.
     */
    private static final String CONNECTION_FACTORY = "jms/proteusConnectionFactory";

    /**
     * Cola de ejecucion de nomina.
     */
    private static final String CONNECTION_QUEUE = "jms/proteusGeneracionNominaQueue";

    /**
     * Helper Nomina.
     */
    @ManagedProperty("#{nominaHelper}")
    private NominaHelper nominaHelper;

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

    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

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
     * Dao de Regimen laboral.
     */
    @EJB
    private RegimenLaboralDao regimenLaboralDao;

    @EJB
    private NivelOcupacionalDao nivelOcupacionalDao;

    @EJB
    private ModalidadLaboralDao modalidadLaboralDao;

    /**
     *
     */
    @EJB
    private ProblemaServicio problemaServicio;

    /**
     *
     */
    @EJB
    private NominaEjecucionDao nominaEjecucionDao;

    /**
     *
     */
    @EJB
    private NominaIRDao nominaIRDao;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        nominaHelper.getBusquedaNominaVO().setPeriodoFiscal(null);
        nominaHelper.getBusquedaNominaVO().setPeriodoNomina(null);
        nominaHelper.getBusquedaNominaVO().setTipoNomina(null);
        nominaHelper.getBusquedaNominaVO().setNumeroNomina(null);
        nominaHelper.getBusquedaNominaVO().setDescripcionNomina(null);
        nominaHelper.setNominaEnumCodigo("");
        nominaHelper.getListaServidores().clear();
        nominaHelper.setModo("I");
        if (obtenerUsuarioConectado() != null) {
            iniciarCombos(nominaHelper.getListaTipoNominaEnum());
            iniciarCatalogos();
            iniciarComboTipo();
        }
        //nominaHelper.getListaNominas().clear();
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String listarNominas() {
        try {
            nominaHelper.getListaNominas().clear();
            nominaHelper.getFiltro().setCoberturaNomina(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo());
            List<Nomina> nominas = nominaDao.buscarPorFiltros(nominaHelper.getFiltro());
            //System.out.println(nominas.size());
            nominaHelper.setListaNominas(nominas);
            actualizarComponente("tblListaNominas");
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al buscar nominas por filtro", e);
        }

        return LISTA_NOMINA;
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String buscarNominas() {
        try {
//            nominaHelper.setPresentarRegresar(Boolean.TRUE);
            nominaHelper.getListaNominas().clear();
//            iniciarComboTipo();
            nominaHelper.getBusquedaNominaVO().setPeriodoFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().
                    getId());
            nominaHelper.setListaNominas(nominaDao.buscarPorFiltrosNomina(nominaHelper.getBusquedaNominaVO()));
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al buscar nominas por filtro", e);
        }
        return null;
    }

    /**
     * Este metodo guarda la nomina.
     *
     * @return String
     */
    public String guardarNomina() {
        try {

            nominaHelper.setNominaAprobada(Boolean.FALSE);
            if (nominaHelper.getEsNuevo()) {
                nominaServicio.crear(nominaHelper.getNomina(), obtenerUsuarioConectado());
                nominaServicio.guardarNominaHistorico(nominaHelper.getNomina());
                nominaHelper.setEsNuevo(Boolean.FALSE);
                mostrarMensajeEnPantalla("NOMINA GUARDADA", FacesMessage.SEVERITY_INFO);
            } else {
                Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
                iniciarDatosEntidad(nominaHelper.getNomina(), Boolean.FALSE);
                if (nominaHelper.getAccion() == null) {
                    mostrarMensajeEnPantalla("El campo Acción es requerido",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (nominaHelper.getNomina().getObservacion() == null) {
                    mostrarMensajeEnPantalla("El campo Comentario es requerido",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (nominaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())
                        && !nomina.getBloqueado()) {
                    mostrarMensajeEnPantalla("NO SE PUEDE VALIDAR LA NÓMINA PORQUE NO ESTA BLOQUEADA, RECALCULE LA NÓMINA.",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (nominaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())
                        && !verificarDetallesNomina()) {
                    mostrarMensajeEnPantalla("NO SE PUEDE VALIDAR LA NÓMINA PORQUE NO HA SIDO PROCESADA !!!",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (nominaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())
                        && !nominaHelper.getListaProblemas().isEmpty()) {
                    mostrarMensajeEnPantalla("NO SE PUEDE VALIDAR LA NÓMINA PORQUE REGISTRA PROBLEMAS !!!",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (nominaHelper.getAccion().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
                    nominaHelper.getNomina().setFechaAprovacion(new Date());
                    nominaHelper.setNominaAprobada(Boolean.TRUE);

                } else if (nominaHelper.getAccion().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                        && nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())
                        && !nominaHelper.getListaTipoArchivoSIPARI().isEmpty()) {
                    archivoServicio.eliminarArchivoSipari(nominaHelper.getListaTipoArchivoSIPARI(),
                            obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                }
                if (nominaHelper.getAccion().equals(EstadoNominaEnum.ANULAR.getCodigo())
                        || nominaHelper.getAccion().equals(EstadoNominaEnum.RECHAZAR.getCodigo())) {
                    anularNovedadesNomina();
                }
                nominaHelper.getNomina().setEstado(nominaHelper.getAccion());
                nominaServicio.actualizarNomina(nominaHelper.getNomina());
                ejecutarComandoPrimefaces("confirmAccion.hide();");
                mostrarMensajeEnPantalla("LA NOMINA HA SIDO EN ENVIADA", FacesMessage.SEVERITY_INFO);
                if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
                    nominaHelper.setCalcular(Boolean.TRUE);
                } else {
                    nominaHelper.setCalcular(Boolean.FALSE);
                }
            }
            determinarAccion();

        } catch (Exception e) {
            error(this.getClass().getName(), "Error al crear la nómina.", e);
        }
        return null;
    }

    /**
     * Si la nómina es anulada o rechazada, se inactivan las novedades asociadas.
     */
    private void anularNovedadesNomina() {
        try {
            nominaServicio.descartarNomina(nominaHelper.getNomina().getId(), obtenerUsuarioConectado().getUsuario());
            getNominaNovedadHelper().setNomina(null);
        } catch (ServicioException ex) {
            error(this.getClass().getName(), "Error al eliminar novedades de la nómina.", ex);
        }
    }

    /**
     *
     * @return
     */
    public boolean verificarDetallesNomina() {
        boolean hayDetalles = false;
        try {
            hayDetalles = nominaServicio.existeNomina(nominaHelper.getNomina().getId());
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
                error(this.getClass().getName(), "No existe un periodo de nómina activo.", new Exception("No existe un periodo de nómina activo."));
            } else {
//                nominaHelper.getListaPeriodoNomina().set(0, new SelectItem(null, "Seleccione.."));
//                nominaHelper.getListaTipoNomina().set(0, new SelectItem(null, "Seleccione.."));
                //System.out.println(" inicia creacion:" + nominaHelper.getListaTipoNomina().get(0).getLabel());
                nominaHelper.setNomina(new Nomina());
                nominaHelper.getNomina().setEstado(EstadoNominaEnum.ABIERTO.getCodigo());
                nominaHelper.getNomina().setInstitucionEjercicioFiscalId(obtenerUsuarioConectado().
                        getInstitucionEjercicioFiscal().getId());
                nominaHelper.getNomina().setPeriodoNomina(pn);
                nominaHelper.getNomina().setPeriodoNominaId(pn.getId());
                iniciarDatosEntidad(nominaHelper.getNomina(), Boolean.TRUE);
                nominaHelper.setEsNuevo(Boolean.TRUE);
                resultado = FORMULARIO_NOMINA;
            }
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al iniciar pantalla.", e);
        }
        return resultado;
    }

    /**
     *
     * @return
     */
    public String iniciarEdicion() {
        nominaHelper.setEsNuevo(Boolean.FALSE);
        nominaHelper.getNomina().setObservacion(null);
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
     *
     * @param codigo
     * @return
     */
    public String obtenerDescripcionTipoArchivo(final String codigo) {
        return ArchivoSipariEnum.obtenerDescripcion(codigo);
    }

    /**
     *
     * @param codigo
     * @return
     */
    public String obtenerAccionEstado(final String codigo) {
        String accion = EstadoNominaEnum.obtenerAccion(codigo);
        if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())
                && (codigo.equals(EstadoNominaEnum.VALIDADO.getCodigo()))) {
            return "DEVOLVER";
        }
        if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.VALIDADO.getCodigo())
                && (codigo.equals(EstadoNominaEnum.ABIERTO.getCodigo()))) {
            return "DEVOLVER";
        }
        return accion;
    }

    /**
     * Carga los combos de seleccion de acuerdo al estado.
     */
    private void determinarAccion() {
        nominaHelper.setCalcular(Boolean.FALSE);
        nominaHelper.setNominaAprobada(Boolean.FALSE);
        nominaHelper.setNominaAnulada(Boolean.FALSE);

        iniciarCombos(nominaHelper.getListaAcciones());
        if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ABIERTO.getCodigo())) {
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.VALIDADO.getCodigo(),
                    EstadoNominaEnum.VALIDADO.getAccion()));
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.ANULAR.getCodigo(),
                    EstadoNominaEnum.ANULAR.getAccion()));
            nominaHelper.setCalcular(Boolean.TRUE);

        } else if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.VALIDADO.getCodigo())) {
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.APROBADO.getCodigo(),
                    EstadoNominaEnum.APROBADO.getAccion()));
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.ABIERTO.getCodigo(),
                    "DEVOLVER"));
        } else if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.PAGADO.getCodigo(),
                    EstadoNominaEnum.PAGADO.getAccion()));
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.VALIDADO.getCodigo(),
                    "DEVOLVER"));
            nominaHelper.getListaAcciones().add(new SelectItem(EstadoNominaEnum.RECHAZAR.getCodigo(),
                    EstadoNominaEnum.RECHAZAR.getAccion()));
            nominaHelper.setNominaAprobada(Boolean.TRUE);
//            iniciarListaTipoArchivoSIPARI(false);
        } else if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.ANULAR.getCodigo())) {
            nominaHelper.setNominaAnulada(Boolean.TRUE);
        } else if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.RECHAZAR.getCodigo())) {
            nominaHelper.setNominaAnulada(Boolean.TRUE);
        }
        nominaHelper.getNomina().setObservacion(null);
        nominaHelper.setAccion(null);
    }

    /**
     *
     * @return
     */
    public String iniciarCalculo() {
        try {
            cargarOpcionesCalculo();
            Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
            nominaHelper.setCalculando(nomina.getCalculando());
            nominaHelper.setListaEjecuciones(nominaEjecucionDao.buscar(nominaHelper.getNomina().getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EJECUCION_NOMINA;
    }

    /**
     *
     * @return
     */
    public String verObservaciones() {
        buscarObservacionesNomina();
        ejecutarComandoPrimefaces("dlgObservacionesNomina.show();");

        return null;
    }

    /**
     *
     */
    private void buscarObservacionesNomina() {
        try {
            nominaHelper.setListaProblemas(nominaProblemaDao.buscar(nominaHelper.getNomina().getId()));
        } catch (DaoException e) {
            error(getClass().getName(), NADA, e);
        }
    }

    /**
     * Este metodo carga las opciones de calculo.
     */
    private void cargarOpcionesCalculo() {
        try {
            nominaHelper.getDatosNomina().setTipoCalculo("C");
            nominaHelper.getDatosNomina().setModalidadLaboralId(null);
            nominaHelper.getDatosNomina().setNivelOcupacionalId(null);
            nominaHelper.getDatosNomina().setRegimenLaboralId(null);
            nominaHelper.getDatosNomina().getServidores().clear();
            //opciones regimen laboral
            List<RegimenLaboral> regimenesLaborales = regimenLaboralDao.buscarVigente();
            iniciarCombosTodos(nominaHelper.getListaRegimenLaboral());
            for (RegimenLaboral rl : regimenesLaborales) {
                if (nominaHelper.getNomina().getTipoNomina().getRegimenLaboralId() != null
                        && nominaHelper.getNomina().getTipoNomina().getRegimenLaboralId().equals(rl.getId())) {
                    nominaHelper.getListaRegimenLaboral().add(new SelectItem(rl.getId(), rl.getNombre()));
                } else if (nominaHelper.getNomina().getTipoNomina().getRegimenLaboralId() == null) {
                    nominaHelper.getListaRegimenLaboral().add(new SelectItem(rl.getId(), rl.getNombre()));
                }
            }
            nominaHelper.getDatosNomina().setRegimenLaboralId(nominaHelper.getNomina().getTipoNomina().getRegimenLaboralId());

            //Nivel Ocupacional
            iniciarCombosTodos(nominaHelper.getListaNivelOcupacional());

            //Modalidad Laboral
            iniciarCombosTodos(nominaHelper.getListaModalidadLaboral());
            List<ModalidadLaboral> modalidalesLabotares = modalidadLaboralDao.buscarVigente();
            for (ModalidadLaboral ml : modalidalesLabotares) {
                nominaHelper.getListaModalidadLaboral().add(new SelectItem(ml.getId(), ml.getNombre()));
            }
            nominaHelper.setListaServidores(new ArrayList<Servidor>());
        } catch (DaoException e) {
            error(getClass().getName(), "Error la consular los filtro de calculo para la nomina", e);
        }
    }

    /**
     * Buscar servidor del distributivo por filtros.
     *
     * @param nombre
     * @return
     */
    public List<Servidor> buscarServidorPorNombre(final String nombre) {
        List<Servidor> servidores = new ArrayList<>();
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            if (nombre != null && !nombre.isEmpty()) {
                BusquedaPuestoVO b = new BusquedaPuestoVO();
                b.setNombreServidor(nombre);
                b.setModalidadLaboralId(nominaHelper.getDatosNomina().getModalidadLaboralId());
                b.setRegimenLaboralId(nominaHelper.getDatosNomina().getRegimenLaboralId());
                b.setNivelOcupacionalId(nominaHelper.getDatosNomina().getNivelOcupacionalId());
                b.setInicioConsulta(0);
                b.setFinConsulta(10);
                List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(b,
                        false, obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
                for (DistributivoDetalle puesto : puestos) {
                    servidores.add(puesto.getServidor());
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar servidores", e);
        }
        return servidores;
    }

    /**
     * Este metodo busca los niveles segun la opcion anterior.
     */
    public void buscarNivelesPorRegimen() {
        try {
            List<NivelOcupacional> nivelesOcupacionales = nivelOcupacionalDao.buscarTodosPorIdRegiem(
                    nominaHelper.getDatosNomina().getRegimenLaboralId());
            iniciarCombosTodos(nominaHelper.getListaNivelOcupacional());
            for (NivelOcupacional rl : nivelesOcupacionales) {
                nominaHelper.getListaNivelOcupacional().add(new SelectItem(rl.getId(), rl.getNombre()));
            }
        } catch (DaoException e) {
            error(getClass().getName(), NADA, e);
        }
    }

    /**
     * Este metodo inicia lo catalogos para la nomina.
     */
    private void iniciarCatalogos() {
        try {
            //Periodo fiscal            
            List<InstitucionEjercicioFiscal> ejercicios = institucionEjercicioFiscalDao.buscarPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            nominaHelper.getListaPeriodoFiscal().clear();
            for (InstitucionEjercicioFiscal ef : ejercicios) {
                nominaHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(),
                        ef.getEjercicioFiscal().getNombre()));
            }
            //Periodo nomina
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos;
            if (nominaHelper.getFiltro().getPeriodoFiscal() == null) {
                periodos = periodoNominaDao.buscarPorEjercicio(ejercicios.get(0).getId());
            } else {
                periodos = periodoNominaDao.buscarPorEjercicio(nominaHelper.
                        getFiltro().getPeriodoFiscal());
            }
            for (PeriodoNomina periodo : periodos) {
                nominaHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }
            //Tipo Nomina
            List<TipoNomina> tiposNomina = tipoNominaDao.buscarVigente();

            if (nominaHelper.getEsNuevo() != null && nominaHelper.getEsNuevo()) {
                iniciarCombos(nominaHelper.getListaTipoNomina());
            } else {
                iniciarCombosTodos(nominaHelper.getListaTipoNomina());
            }
            for (TipoNomina tn : tiposNomina) {
                if (tn.getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
                    nominaHelper.getListaTipoNomina().add(new SelectItem(tn.getId(), tn.getNombre()));
                }
            }
            nominaHelper.getListaMensajes().clear();
        } catch (DaoException e) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos: {0}", e);
        }
    }

    public void buscarPeriodos() {
        try {
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos = periodoNominaDao.buscarPorEjercicio(nominaHelper.getFiltro().getPeriodoFiscal());
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            for (PeriodoNomina periodo : periodos) {
                nominaHelper.getListaPeriodoNomina().add(
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
        getResultadoNominaHelper().getResultadoNomina().setNomina(nominaHelper.getNomina());
        getResultadoNominaHelper().getResultadoNomina().setNominaDetalle(new NominaDetalle());
        getResultadoNominaHelper().getResultadoNomina().getNominaDetalle().setNomina(nominaHelper.getNomina());
//        buscar();
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
        getNominaNovedadHelper().setNomina(nominaHelper.getNomina());
        getNominaNovedadHelper().getNovedadNominaVO().setNovedad(new Novedad());
        buscarNovedades();
        return FORMULARIO_NOVEDADES_NOMINA;
    }

//    /**
//     * Este método procesa la busqueda. Obtiene totales por grupos.
//     *
//     * @return String
//     */
//    public String buscar() {
//        try {
//            resultadoNominaHelper.getListaResultadoNomina().clear();
//            resultadoNominaHelper.setListaResultadoNomina(nominaServicio.listarResultadoNomina(
//                    resultadoNominaHelper.getResultadoNomina()));
//        } catch (ServicioException e) {
//            error(getClass().getName(), "Error al consultar los resultados de la nomina.", e);
//        }
//        return null;
//    }
    /**
     * actualiza la paginación.Este método procesa la busqueda. Obtiene totales por grupos.
     */
    public void refreshPagination() {
        ((DataTable) FacesContext.getCurrentInstance().getViewRoot().
                findComponent("frmPrincipal").
                findComponent("frmPrincipal:tblListaNominas")).setFirst(0);
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
                    resultadoNominaHelper.getResultadoNomina().setTotalIngresosServidor(resultadoNominaHelper.getResultadoNomina().getTotalIngresosServidor().add(det.getValorDescontadoRubroIngreso()));
                } else if (det.getValorDescontadoRubroDescuentos() != null && det.getTipo().equals("SER")) {
                    resultadoNominaHelper.getListaDetallesDescuentos().add(det);
                    resultadoNominaHelper.getResultadoNomina().setTotalDescuentosServidor(resultadoNominaHelper.getResultadoNomina().getTotalDescuentosServidor().add(det.getValorDescontadoRubroDescuentos()));
                } else if (det.getValorDescontadoRubroAportes() != null && det.getTipo().equals("SER")) {
                    resultadoNominaHelper.getListaDetallesAportes().add(det);
                    resultadoNominaHelper.getResultadoNomina().setTotalAportePatronalServidor(resultadoNominaHelper.getResultadoNomina().getTotalAportePatronalServidor().add(det.getValorDescontadoRubroAportes()));
                }
            }
            // recuperar datos del calculo de ir
            NominaIR ir = nominaIRDao.buscarPorNominaServidor(
                    resultadoNominaHelper.getResultadoNomina().getNomina().getId(), resultadoNominaHelper.getResultadoNomina().getServidorId());
            resultadoNominaHelper.setNominaIR(ir);

            ejecutarComandoPrimefaces("dlgDetalleNomina.show()");
            actualizarComponente(":popDetalles");
        } catch (Exception e) {
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
     * Navega hacia la página de Nómina Especial.
     *
     * @return
     */
    public String irNominaEspecial() {
        return FORMULARIO_NOMINA_DIFERENCIADA;
    }

    /**
     * Navega hacia la página de Nómina.
     *
     * @return
     */
    public String irConsultaNomina() {
        return CONSULTA_NOMINA;
    }

    public String enviarProcesarNomina() {
        // validar 

        return EJECUCION_NOMINA;
    }

    /**
     *
     * @return
     */
    public String procesarNominaClave() {
        if (nominaHelper.getDatosNomina().getTipoCalculo().equals("C")) {
            nominaHelper.setClaveCalculo(null);
            ejecutarComandoPrimefaces("dlgAutorizacionEjecucionNominaCompleta.show()");
        } else {
            procesarNomina();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String procesarNomina() {

        ConnectionFactory connectionFactory;
        Connection connection = null;
        try {
            nominaEjecucionDao.quitarVigencia(nominaHelper.getNomina().getId());
            problemaServicio.registrarEjecucion("ENVIANDO.....", obtenerUsuarioConectado().getUsuario(), nominaHelper.getNomina().getId());
            Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
            nomina.setCalculando(Boolean.TRUE);
            nominaHelper.setCalculando(Boolean.TRUE);
            nominaDao.actualizar(nomina);
            EjecucionNominaVO vo = new EjecucionNominaVO();
            vo.setTodos(nominaHelper.getDatosNomina().getTipoCalculo().equals("C"));
            vo.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            vo.setModalidadLaboralId(nominaHelper.getDatosNomina().getModalidadLaboralId());
            vo.setNivelOcupacionalId(nominaHelper.getDatosNomina().getNivelOcupacionalId());
            vo.setRegimenLaboralId(nominaHelper.getDatosNomina().getRegimenLaboralId());
            vo.setTipoNomina(nominaHelper.getNomina().getTipoNomina());
            vo.setServidores(nominaHelper.getListaServidores());
            vo.setUsuarioVO(obtenerUsuarioConectado());

            Date fechaActual = new Date();
            Context jndiContext = new InitialContext();
            connectionFactory = (ConnectionFactory) jndiContext.lookup(CONNECTION_FACTORY);
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) jndiContext.lookup(CONNECTION_QUEUE);
            MessageProducer producer = session.createProducer(queue);
            ObjectMessage message = session.createObjectMessage();
            message.setLongProperty("nomina_id", nominaHelper.getNomina().getId());
            message.setStringProperty("usuario", obtenerUsuarioConectado().getUsuario());
            message.setLongProperty("fecha", fechaActual.getTime());
            message.setObject(vo);
            producer.send(message);
            buscarEjecuciones();
            ejecutarComandoPrimefaces("dlgAutorizacionEjecucionNominaCompleta.hide()");

            mostrarMensajeEnPantalla("NÓMINA ENVIADA", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla("NÓMINA NO PUDO SER ENVIADA:" + limpiarMensajeDeExcepcion(e.getMessage()),
                    FacesMessage.SEVERITY_ERROR);

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
            }
        }

//        try {
//            Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
//            nomina.setCalculando(Boolean.TRUE);
//            nominaDao.actualizar(nomina);
//            EjecucionNominaVO vo = new EjecucionNominaVO();
//            vo.setTodos(nominaHelper.getDatosNomina().getTipoCalculo().equals("C"));
//            vo.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
//            vo.setModalidadLaboralId(nominaHelper.getDatosNomina().getModalidadLaboralId());
//            vo.setNivelOcupacionalId(nominaHelper.getDatosNomina().getNivelOcupacionalId());
//            vo.setRegimenLaboralId(nominaHelper.getDatosNomina().getRegimenLaboralId());
//            vo.setTipoNomina(nominaHelper.getNomina().getTipoNomina());
//            vo.setServidores(nominaHelper.getListaServidores());
//            vo.setSc(getSession().getServletContext());
//            nominaServicio.ejecutar(vo, nominaHelper.getNomina().getId(), obtenerUsuarioConectado(),
//                    getSession().getServletContext());
//
//            boolean nominaConDetalles = verificarDetallesNomina();
//            buscarObservacionesNomina();
//            if (nominaHelper.getListaProblemas().isEmpty() && nominaConDetalles) {
//                mostrarMensajeEnPantalla("Nómina ejecutada exitosamente.", FacesMessage.SEVERITY_INFO);
//            } else if (!nominaHelper.getListaProblemas().isEmpty()) {
//                mostrarMensajeEnPantalla("Nómina ejecutada con problemas.", FacesMessage.SEVERITY_WARN);
//            } else {
//                mostrarMensajeEnPantalla("Nómina no genero detalles..", FacesMessage.SEVERITY_WARN);
//            }
//        } catch (Exception e) {
//            mostrarMensajeEnPantalla("NÓMINA NO PUDO SER PROCESADA:" + limpiarMensajeDeExcepcion(e.getMessage()),
//                    FacesMessage.SEVERITY_ERROR);
//        } finally {
//            try {
//                Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
//                nomina.setCalculando(Boolean.FALSE);
//                nominaDao.actualizar(nomina);
//            } catch (Exception e) {
//            }
//        }
        return null;
    }

    /**
     * Desbloquea una nomina.
     *
     * @return
     */
    public String desbloquear() {
        try {
            Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
            nomina.setBloqueado(Boolean.FALSE);
            nomina.setFechaActualizacion(new Date());
            nomina.setUsuarioActualizacion(obtenerUsuarioConectado().getUsuario());
            nominaDao.actualizar(nomina);
            nominaHelper.setNomina(nomina);
            mostrarMensajeEnPantalla("Nómina desbloqueada correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Problemas al desbloquear nomina", FacesMessage.SEVERITY_ERROR);
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
            obj.setNomina(nominaHelper.getNomina());
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
     * Listar y generar archivos SPI.
     *
     * @param popup
     * @return
     */
    public StreamedContent iniciarListaTipoArchivoSPI(boolean popup) {
        nominaHelper.getListaTipoArchivoSPI().clear();
        nominaHelper.getArchivosSeleccionados().clear();
        if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
            Nomina nomina = nominaHelper.getNomina();
            TipoNomina tipo = nomina.getTipoNomina();
            String nombre = "SPI_" + /*tipo.getRegimenLaboral().getNombre()*/ tipo.getNombre()
                    + "_" + nomina.getPeriodoNomina().getEjercicioFiscal().getNombre()
                    + "_" + nomina.getPeriodoNomina().getNombre() + "_"
                    + (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) + ".xls";
            try {
                InputStream stream = nominaServicio.generarNominaSPI(nominaHelper.getNomina());
                StreamedContent sc = new DefaultStreamedContent(
                        stream, "application/vnd.ms-excel", nombre);

                return sc;
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return null;

    }

    /**
     * Carga las opciones para crear los diferentes archivos.
     *
     * @param popup
     * @return
     */
    public StreamedContent iniciarListaTipoArchivoSIPARI(boolean popup) {
        /*
         nominaHelper.getListaTipoArchivoSIPARI().clear();
         nominaHelper.getArchivosSeleccionados().clear();
         if (nominaHelper.getNomina().getEstado().equals(EstadoNominaEnum.APROBADO.getCodigo())) {
         for (ArchivoSipari a : nominaHelper.getNomina().getListaArchivoSIPARI()) {
         if (a.getVigente()) {
         a.setSeleccionado(Boolean.TRUE);
         nominaHelper.getListaTipoArchivoSIPARI().add(a);
         iniciarDatosEntidad(a, Boolean.FALSE);
         }
         }
         for (ArchivoSipariEnum tipoNomina : ArchivoSipariEnum.values()) {

         if (!buscarArchivoPorNominaYTipo(tipoNomina.getCodigo())) {
         ArchivoSipari a = new ArchivoSipari();
         a.setTipo(tipoNomina.getCodigo());
         iniciarDatosEntidad(a, Boolean.TRUE);
         a.setNomina(nominaHelper.getNomina());
         nominaHelper.getListaTipoArchivoSIPARI().add(a);
         }
         }
         if (popup) {
         actualizarComponente("frmMenu:tablaArchivos");
         ejecutarComandoPrimefaces("confirmArchivo.show();");
         }
         }
         */
        try {
            Nomina nomina = nominaHelper.getNomina();

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            String periodoString = sdf.format(nomina.getPeriodoNomina().getFechaFinal());

            String[] dataParaArchivoSIPARI = nominaServicio.generarDataParaArchivosSIPARI(nomina.getId(),
                    periodoString, nomina.getNumero(), nomina.getDescripcion(), "51", "71");

            String[][] gruposData = new String[][]{{"51", dataParaArchivoSIPARI[0]}, {"71", dataParaArchivoSIPARI[1]}};

            File zipFile = generarArchivoZipCVS(gruposData);

            if (zipFile != null) {
                InputStream input = new FileInputStream(zipFile);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                return new DefaultStreamedContent(
                        input, externalContext.getMimeType(zipFile.getName()), zipFile.getName());
            } else {
                mostrarMensajeEnPantalla(
                        "Error mientras se generaban los archivos csv.", FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param gruposData
     * @return
     */
    private File generarArchivoZipCVS(String[][] gruposData) {
        Nomina nomina = nominaHelper.getNomina();
        StringBuilder zipPath = new StringBuilder("SIPARI_");
        zipPath.append(nomina.getPeriodoNomina().getNombre())
                .append("_")
                .append(nomina.getPeriodoNomina().getEjercicioFiscal().getNombre())
                .append("_")
                .append(nomina.getTipoNomina().getCodigo())
                .append(".zip");

        try {
            File zipFile = new File(zipPath.toString());
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

            for (String[] gd : gruposData) {
                String archivoCVS
                        = "SIPARI_" + nomina.getTipoNomina().getRegimenLaboral().getNombre()
                        + "_" + gd[0]
                        + "_" + nomina.getPeriodoNomina().getEjercicioFiscal().getNombre()
                        + "_" + nomina.getPeriodoNomina().getNombre() + "_"
                        + (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())) + ".csv";

                CSVWriter writer = new CSVWriter((new FileWriter(archivoCVS)), ';', CSVWriter.NO_QUOTE_CHARACTER);
                String[] lineas = gd[1].split("#");
                for (String s : lineas) {
                    if (!s.trim().isEmpty()) {
                        writer.writeNext(s.split(";"));
                    }
                }
                writer.close();

                ZipEntry zipEntry = new ZipEntry(archivoCVS);
                zipOutputStream.putNextEntry(zipEntry);
                FileInputStream fileInputStream = new FileInputStream(new File(archivoCVS));

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, bytesRead);
                }
            }

            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileOutputStream.close();

            return zipFile;

        } catch (Exception e) {
            e.printStackTrace();
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
        if (!nominaHelper.getListaTipoArchivoSIPARI().isEmpty()) {
            for (ArchivoSipari a : nominaHelper.getListaTipoArchivoSIPARI()) {
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
            nominaHelper.getArchivosSeleccionados().add(a);
        } else {
            nominaHelper.getArchivosSeleccionados().remove(a);
        }
    }

    /**
     * Permite mostrar el modal panel que confirma y jecuta el cambio de estado.
     *
     * @return
     */
    public String activarPanelActualizarEstado() {
        if (nominaHelper.getNomina().getObservacion() == null | nominaHelper.getNomina().getObservacion().isEmpty()) {
            mostrarMensajeEnPantalla("El campo Comentario es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (nominaHelper.getAccion() == null) {
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
            nominaHelper.getListaMensajes().clear();
            a.setSeleccionado(Boolean.TRUE);
            nominaHelper.getArchivosSeleccionados().add(a);
            nominaHelper.setListaMensajes(archivoServicio.guardarArchivoSipari(nominaHelper.getArchivosSeleccionados()));
            //System.out.println(" Archivo==>" + a.getId() + " nombre:" + a.getNombre());
            if (nominaHelper.getListaMensajes().isEmpty() && a.getId() != null) {
                nominaHelper.getNomina().getListaArchivoSIPARI().add(a);
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
     * Este metodo llena las opciones para el combo de tipoNomina parametro.
     */
    private void iniciarComboTipo() {
        nominaHelper.getListaTipoNominaEnum().clear();
        iniciarCombos(nominaHelper.getListaTipoNominaEnum());
        for (ConsultaNominaEnum t : ConsultaNominaEnum.values()) {
            nominaHelper.getListaTipoNominaEnum().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
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

    /**
     * recoorrer la lista de nominas para ver que rporte quiero presentar
     */
    public void asignarNominaCodigo() {
        for (Nomina n : nominaHelper.getListaNominas()) {
            nominaHelper.setNominaEnumCodigo(nominaHelper.getNominaEnumCodigo());
        }
    }

    @Override
    public void generarReporte() {
        if (nominaHelper.getNominaEnumCodigo() != null) {
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.NOMINA.getReporte())) {
                setReporte(ReportesEnum.NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE DE NOMINA");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.TOTAL_NOMINA.getReporte())) {
                setReporte(ReportesEnum.TOTAL_NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE TOTAL NOMINA");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_DESCUENTOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_DESCUENTOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE DESCUENTOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE PAGOS POR DIRECCIONES");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_RETENCIONES.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_RETENCIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE RETENCIONES");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_POR_RUBROS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_POR_RUBROS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN POR RUBROS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN ROL DE PAGOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN POR INGRESOS/DESCUENTOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            generarUrlDeReporte();
        } else {
            mostrarMensajeEnPantalla("No se a seleccionado un reporte", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     *
     */
    public void buscarEjecuciones() {
        try {
            Nomina nomina = nominaDao.buscarPorId(nominaHelper.getNomina().getId());
            nominaHelper.setCalculando(nomina.getCalculando());
            nominaHelper.setListaEjecuciones(nominaEjecucionDao.buscar(nominaHelper.getNomina().getId()));
            if (!nomina.getCalculando()) {
                ejecutarComandoPrimefaces("poll.stop();");
                List<NominaProblema> problemas = nominaProblemaDao.buscar(nominaHelper.getNomina().getId());
                if (problemas.isEmpty()) {
                    mostrarMensajeEnPantalla("NÓMINA CALCULADA CORRECTAMENTE...", FacesMessage.SEVERITY_INFO);
                } else {
                    mostrarMensajeEnPantalla("EXISTE OBSERVACIONES EN LA NÓMINA...", FacesMessage.SEVERITY_ERROR);
                }
                init();
            }
        } catch (Exception e) {
        }
    }

    /**
     *
     * @return the nominaHelper
     */
    public NominaHelper getNominaHelper() {
        return nominaHelper;
    }

    /**
     * @param nominaHelper the nominaHelper to set
     */
    public void setNominaHelper(final NominaHelper nominaHelper) {
        this.nominaHelper = nominaHelper;
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
