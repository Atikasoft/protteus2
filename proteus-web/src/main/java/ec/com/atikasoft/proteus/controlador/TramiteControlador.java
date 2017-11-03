/*
 *  TramiteControlador.java
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
 *
 *  30/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import com.itextpdf.text.Document;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.TramiteAuxiliar;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.enums.DocumentoHabilitanteEnum;
import ec.com.atikasoft.proteus.enums.TipoAccionEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.TipoGestionEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.AgregarServidorHelper;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaPuestoHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.dao.CatalogoDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ReporteDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoHabilitanteEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoMovimientoEnum;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.modelo.DocumentoHabilitante;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Reporte;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.GestionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * Controlador de tramite.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteBean")
@ViewScoped
public class TramiteControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TramiteControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/tramite.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_MENSAJE = "/pages/procesos/tramite/lista_mensaje.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String TRAMITE_BORRADOR = "/pages/procesos/tramite/tramite_borrador.jsf";

    /**
     * varible que muestra el mensaje.
     */
    public static final String MENSAJE = "ec.gob.mrl.smp.tramite.mensaje.validarRequisitos";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Helper de agregar servidro.
     */
    @ManagedProperty("#{agregarServidorHelper}")
    private AgregarServidorHelper agregarServidorHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaPuestoHelper}")
    private BusquedaPuestoHelper busquedaPuestoHelper;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de Tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de gestion.
     */
    @EJB
    private GestionServicio gestionServicio;
    /**
     * Dao de catalogo.
     */
    @EJB
    private CatalogoDao catalogoDao;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private ReporteDao reporteDao;

    /**
     * Costructor.
     */
    public TramiteControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        tramiteHelper.setEditable(Boolean.FALSE);
        tramiteHelper.setEsAccionPersonal(Boolean.FALSE);
        String estado = getRequest().getParameter("est");
        tramiteHelper.setControlNavegacion("nx");
        tramiteHelper.getErroresCargaMasiva().clear();
        iniciarOpciones();

        if (estado == null) {
            iniciarCreacion();
            tramiteHelper.setEditable(Boolean.TRUE);
            tramiteHelper.setCreado(Boolean.FALSE);
        } else if (estado.equals("edt") || estado.equals("vr") || estado.equals("vre")) {
            tramiteHelper.setControlNavegacion(estado);
            edicionTramite();
        } else {
            salirPantallaPrincipal();
        }
        iniciarCatalogos();
    }

    private void iniciarCatalogos() {
        if (tramiteHelper.getTramite().getCatalogoAutoridadNominadora() == null) {
            tramiteHelper.getTramite().setCatalogoAutoridadNominadora(new Catalogo());
        }
        if (tramiteHelper.getTramite().getCatalogoRepresentanteRRHH() == null) {
            tramiteHelper.getTramite().setCatalogoRepresentanteRRHH(new Catalogo());
        }
    }

    /**
     * Este metodo procesa los campos para la configuracion de documento habilitante.
     *
     */
    private void procesarCamposDocumentoHabilitante() {
        try {
            List<String> campos = new ArrayList<String>();
            campos.add("fechaRigeAPartirDe");
            campos.add("fechaHasta");
            if (tramiteHelper.getEsAccionPersonal()) {
                campos.add("apDocumentoPrevio");
                campos.add("apNumeroDocumento");
                campos.add("apFechaDocumento");
                campos.add("apExplicacion");
            } else if (tramiteHelper.getEsContrato()) {
                campos.add("antecedentesContrato");
                campos.add("actividadesContrato");
            } else if (tramiteHelper.getEsMemorando()) {
                campos.add("asuntoMemorando");
                campos.add("contenidoMemorando");
            }
            List<TipoMovimiento> tiposMovimentos = (List<TipoMovimiento>) getSession().getServletContext().
                    getAttribute(CacheEnum.TIPO_MOVIMIENTO.getCodigo());
            TipoMovimiento tm = null;
            if (tiposMovimentos == null) {
                tiposMovimentos = admServicio.listarTiposMovimientos(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
            }
            for (TipoMovimiento t : tiposMovimentos) {
                if (t.getId().equals(tramiteHelper.getTramite().getTipoMovimiento().getId())) {
                    tm = t;
                    break;
                }
            }
            FacesContext fc = FacesContext.getCurrentInstance();
            UIViewRoot view = fc.getViewRoot();

            for (String campo : campos) {
                UIComponent componente = view.findComponent("frmPanelDocumentoHabilitante:".concat(campo));
                if (componente instanceof UIInput) {

                    Class forName = tm.getClass();
                    Field field = forName.getDeclaredField(campo);
                    if (field != null) {
//                        System.out.println("Si hay Campo: " + campo);
                        field.setAccessible(true);
                        Object get = field.get(tm);
                        if (get instanceof String) {
                            Boolean disabled = get.toString().equals(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());

                            boolean obligatorio = get.toString().equals(
                                    CamposConfiguracionEnum.OBLIGATORIO.getCodigo());
                            UIInput com = (UIInput) componente;
                            com.setRequired(obligatorio);
                            com.getAttributes().put("disabled", disabled);

                            UIComponent componenteLbl = view.findComponent(
                                    "frmPanelDocumentoHabilitante:".concat(campo + "_lbl"));
                            if (componenteLbl != null && obligatorio) {
                                HtmlOutputLabel label = (HtmlOutputLabel) componenteLbl;
                                label.setValue("* " + label.getValue());
                            }
                        }
                    }

                }

            }
        } catch (Exception e) {
            error(getClass().getName(), "Error en la configuracion del formulario de documento habilitante.", e);
        }
//        return null;
    }

    /**
     * Este método llama a la regla de navegación hacia agregar servidor.
     *
     * @return String
     */
    public String iniciarAgregacion() {
        if (tramiteHelper.getMovimiento().getDistributivoDetalle().getDenominacionPuesto() == null) {
            tramiteHelper.getMovimiento().getDistributivoDetalle().setDenominacionPuesto(new DenominacionPuesto());
        }
        agregarServidorHelper.setMovimiento(tramiteHelper.getMovimiento());
        if (tramiteHelper.getControlNavegacion().equals("edt")
                || tramiteHelper.getControlNavegacion().equals("vr")
                || tramiteHelper.getControlNavegacion().equals("vre")) {
            reglaNavegacionDirecta(AgregarServidorControlador.FORMULARIO_ENTIDAD.concat(
                    "?est=").concat(tramiteHelper.getControlNavegacion()));
        } else {
            salirPantallaPrincipal();

        }
        return null;
    }

    /**
     * Este metodo procesa la carga de datos para la edicion.
     */
    private void edicionTramite() {
        //iniciarOpciones();
        iniciarDatosEntidad(tramiteHelper.getTramite(), Boolean.FALSE);
        tramiteHelper.setCreado(Boolean.TRUE);
        tramiteHelper.setEditable(Boolean.FALSE);
        tramiteHelper.setGrupoId(tramiteHelper.getTramite().getTipoMovimiento().getClase().getGrupo().getId());
        controlSeleccionGrupo();
        tramiteHelper.setClaseId(tramiteHelper.getTramite().getTipoMovimiento().getClase().getId());
        controlSeleccionClase();
        buscarMovimientos();
        areaDocumentoHabilitante();
        tramiteHelper.setEnElaboracion(Boolean.FALSE);

    }

    /**
     * Este metodo busca el detalle del tramite.
     */
    public void verDetalleHistorico() {
        try {
            tramiteHelper.getListaDetalles().clear();
            tramiteHelper.setListaDetalles(gestionServicio.listarDetalles(
                    tramiteHelper.getTramite().getId()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al mostrar el historico.", e);
        }
    }

    /**
     * Este metodo busca la bitacora el tramite.
     */
    public void verBitacora() {
        try {
            tramiteHelper.setTramiteBitacora(
                    tramiteServicio.buscarBitacoraDeTramite(
                            tramiteHelper.getTramite().getId()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al mostrar la bitacora", e);
        }
    }

    /**
     * Este metodo realiza una regla de navegacion.
     *
     * @return String
     */
    public String regredarTramiteBorrador() {
        return TRAMITE_BORRADOR;
    }

    /**
     * Este método controla la selección del tipo de gestion.
     */
    public void controlSeleccionTipoGestion() {
        tramiteHelper.setGrupoId(null);
        tramiteHelper.setClaseId(null);
        iniciarCombos(tramiteHelper.getListaClase());
        iniciarCombos(tramiteHelper.getListaTipoMovimiento());
        controlTipoMovimiento();
        //tramiteHelper.setDocumentoAccionPersonal(Boolean.FALSE);
    }

    /**
     * Este método es usado para avazar el proceso.
     *
     * @return String
     */
    public String enviarTramite() {
        try {
            List<ParametroGlobal> parametros = (List<ParametroGlobal>) getRequest().getServletContext().
                    getAttribute(CacheEnum.PARAMETROS_GLOBALES.getCodigo());
            if (tramiteServicio.validarTramite(
                    tramiteHelper.getTramite().getId(), obtenerNombreUsuario(), true, parametros)) {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
                tramiteServicio.enviarTramite(tramiteHelper.getTramite().getId(), obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(),
                        Integer.valueOf(obtenerUsuarioConectado().getEjercicioFiscal().
                                getNombre()), obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));
                reglaNavegacionDirecta(TramiteBorradorControlador.PAGINA_PRINCIPAL);
            } else {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.tramite.validacion.mensaje.error", FacesMessage.SEVERITY_WARN);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al validar el tramite.", e);
        }
        return null;
    }

    /**
     * Este metodo muetra los puestos seleccionados.
     */
    private void buscarMovimientos() {
        try {
            tramiteHelper.getListaMovimientos().clear();
            tramiteHelper.setListaMovimientos(tramiteServicio.buscarMovimientosPorTramite(
                    tramiteHelper.getTramite().getId()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar la busqueda de puesto", e);

        }
    }

    /**
     * Este método llama a una regla de navegación.
     *
     * @return String
     */
    public String busquedaPuesto() {
        try {
//            busquedaPuestoHelper.getListaPuestos().clear();
            busquedaPuestoHelper.iniciador();
            reglaNavegacionDirecta(BusquedaPuestoControlador.FORMULARIO_ENTIDAD);
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar la busqueda de puesto", e);
        }
        return null;
    }

    /**
     * Busca el auxiliar de un tramite.
     */
    public void buscarAuxiliar() {
        try {
            tramiteHelper.getTramite().setTramiteAuxiliar(
                    admServicio.buscarTramiteAuxiliarPorTramite(tramiteHelper.getTramite().getId()));
            tramiteHelper.getTramite().getTramiteAuxiliar().setExplicacion(tramiteHelper.getTramite().
                    getTipoMovimiento().getExplicacion());
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar buscar auxiliar", e);
        }
    }

    /**
     * Este metodo inicializa la creacion de una tramite.
     */
    private void iniciarCreacion() {
        try {
            tramiteHelper.setTramite(new Tramite());
            tramiteHelper.getTramite().setTipoMovimiento(new TipoMovimiento());
            iniciarDatosEntidad(tramiteHelper.getTramite(), Boolean.TRUE);
            tramiteHelper.getTramite().setCodigoInstitucion(obtenerUsuarioConectado().getDistributivoDetalle().
                    getDistributivo().getUnidadOrganizacional().getCodigo());
            tramiteHelper.getTramite().setCodigoEjercicioFiscal(Integer.valueOf(obtenerUsuarioConectado().
                    getEjercicioFiscal().getNombre()));
            tramiteHelper.getTramite().setIdentificadorProceso(1L);
            tramiteHelper.getTramite().setInstitucionEjercicioFiscal(obtenerUsuarioConectado().
                    getInstitucionEjercicioFiscal());
            tramiteHelper.setEnElaboracion(Boolean.TRUE);
            tramiteHelper.getListaMovimientos().clear();
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar la creacion del tramite", e);
        }
    }

    /**
     * Este metodo inicializa los combos.
     */
    private void iniciarOpciones() {
        try {
            iniciarComboTipoAccion();
            iniciarCombos(tramiteHelper.getListaClase());
            iniciarCombos(tramiteHelper.getListaGrupo());
            iniciarCombos(tramiteHelper.getListaTipoGestion());
            iniciarCombos(tramiteHelper.getListaTipoMovimiento());
            iniciarCombos(tramiteHelper.getListaDocumentoPrevio());
            iniciarCombos(tramiteHelper.getListaTipoPeriodos());
            for (DocumentoPrevioEnum d : DocumentoPrevioEnum.values()) {
                tramiteHelper.getListaDocumentoPrevio().add(new SelectItem(d.getCodigo(), d.getDescripcion()));
            }
            for (TipoGestionEnum tg : TipoGestionEnum.values()) {
                tramiteHelper.getListaTipoGestion().add(new SelectItem(tg.getCodigo(), tg.getDescripcion()));
            }

            List<Grupo> listarTodosGrupo = admServicio.listarTodosGrupo();
            for (Grupo g : listarTodosGrupo) {
                tramiteHelper.getListaGrupo().add(new SelectItem(g.getId(), g.getNombre(), g.getDescripcion()));
            }
            tramiteHelper.setTipoGestion(null);
            tramiteHelper.setGrupoId(null);
            tramiteHelper.setClaseId(null);
            tramiteHelper.setTipoMovimientoId(null);
            // lista de representantes de rrhh
            iniciarCombos(tramiteHelper.getListaRepresentanteRRHH());
            List<Catalogo> listaRepresentantes = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.REPRESENTANTE_RRHH.getCodigo());
            for (Catalogo c : listaRepresentantes) {
                tramiteHelper.getListaRepresentanteRRHH().add(new SelectItem(c.getId(), c.getNombre()));
            }
            // lista de autoridades nominadoras
            iniciarCombos(tramiteHelper.getListaAutoridadNominadora());
            List<Catalogo> listaAutoridades = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.AUTORIDAD_NOMINADORA.getCodigo());
            for (Catalogo c : listaAutoridades) {
                tramiteHelper.getListaAutoridadNominadora().add(new SelectItem(c.getId(), c.getNombre()));
            }
            for (TipoPeriodoMovimientoEnum c : TipoPeriodoMovimientoEnum.values()) {
                tramiteHelper.getListaTipoPeriodos().add(new SelectItem(c.getCodigo(), c.getDescripcion()));
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar la creacion del tramite", e);
        }
    }

    /**
     * Este método guarda el tramite.
     *
     * @return String
     */
    public String guardar() {
        try {
            Boolean estado = Boolean.TRUE;
            if (DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo().equals(tramiteHelper.getTramite().
                    getTipoMovimiento().getDocumentoHabilitante().getNemonico())) {
                if (tramiteHelper.getTramite().getFechaDocumento() != null && (tramiteHelper.getTramite().getFechaDocumento().getTime()
                        > tramiteHelper.getFechaActual().getTime())) {
                    estado = Boolean.FALSE;
                }
            }
            if (estado) {
                if (tramiteHelper.getTramite().getCatalogoAutoridadNominadora() != null
                        && tramiteHelper.getTramite().getCatalogoAutoridadNominadora().getId() == null) {
                    tramiteHelper.getTramite().setCatalogoAutoridadNominadora(null);
                }
                if (tramiteHelper.getTramite().getCatalogoRepresentanteRRHH() != null
                        && tramiteHelper.getTramite().getCatalogoRepresentanteRRHH().getId() == null) {
                    tramiteHelper.getTramite().setCatalogoRepresentanteRRHH(null);
                }
                if (tramiteHelper.getCreado()) {
                    tramiteServicio.actualizarTramite(tramiteHelper.getTramite());
                } else {
                    tramiteHelper.getTramite().setInstitucionEjercicioFiscal(
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
                    Tramite crearTramite = tramiteServicio.crearTramite(
                            tramiteHelper.getTramite(), obtenerUsuarioConectado());
                    tramiteHelper.setTramite(crearTramite);
                    tramiteHelper.setCreado(Boolean.TRUE);
                    tramiteHelper.setControlNavegacion("edt");
                    edicionTramite();
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(COMPARAR_FECHA_ACTUAL, FacesMessage.SEVERITY_WARN);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Este método eliminar un movimiento de un tramite.
     */
    public void eliminarMovimiento() {
        try {
            if (!tramiteHelper.getControlNavegacion().equals("edt")
                    && tramiteHelper.getListaMovimientos().size() == 1) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.tramite.legalizacion.control.movimientos.minimos",
                        FacesMessage.SEVERITY_WARN);
            } else {
                tramiteServicio.eliminarMovimiento(tramiteHelper.getMovimiento().getId(), obtenerUsuario());
//                iniciarDatosEntidad(tramiteHelper.getMovimiento(), Boolean.FALSE);
//                tramiteHelper.getMovimiento().setVigente(Boolean.FALSE);
//                tramiteServicio.actualizarMovimiento(tramiteHelper.getMovimiento());
                buscarMovimientos();
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            }
            ejecutarComandoPrimefaces("confElimMov.hide()");
            if (tramiteHelper.getControlNavegacion().equals("vre")) {
                actualizarComponente("frmTramiteLegalizacion:j_idt55:busquedaPuestoHelper_listaPuestos");
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al eliminar el movimiento", e);
        }
    }

    /**
     * Este método eliminar un movimiento de un tramite.
     */
    public void eliminarMovimientos() {
        try {
            tramiteServicio.eliminarMovimientos(tramiteHelper.getTramite().getId(), obtenerUsuario());
            buscarMovimientos();
        } catch (Exception e) {
            error(getClass().getName(), "Error al eliminar los movimientos", e);
        }
    }

    /**
     * Este método procesa la selección del grupo.
     */
    public void controlSeleccionGrupo() {
        try {
            tramiteHelper.setClaseId(null);
            iniciarCombos(tramiteHelper.getListaClase());
            iniciarCombos(tramiteHelper.getListaTipoMovimiento());
            controlTipoMovimiento();
            if (tramiteHelper.getGrupoId() != null) {
                List<Clase> listaClases = admServicio.listarClasePorGrupoId(tramiteHelper.getGrupoId());
                for (Clase c : listaClases) {
                    tramiteHelper.getListaClase().add(new SelectItem(c.getId(), c.getNombre(), c.getDescripcion()));
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar las clases por grupo", e);
        }
    }

    /**
     * Este metodo controla la eliminacion de los campos del tipo movimiento.
     */
    private void controlTipoMovimiento() {
        if (tramiteHelper.getControlNavegacion() == null
                || tramiteHelper.getControlNavegacion().isEmpty()
                || tramiteHelper.getControlNavegacion().equals("nx")) {
            tramiteHelper.getTramite().getTipoMovimiento().setId(null);
            tramiteHelper.getTramite().getTipoMovimiento().setSustentoLegal(null);
        }
    }

    /**
     * Este método procesa la selección del clase.
     */
    public void controlSeleccionClase() {
        try {
            iniciarCombos(tramiteHelper.getListaTipoMovimiento());
            controlTipoMovimiento();

            if (tramiteHelper.getClaseId() != null) {
                List<TipoMovimiento> listaTipoMovimiento = admServicio.listarTipoMovimientoActivosPorClase(
                        tramiteHelper.getClaseId());
                for (TipoMovimiento c : listaTipoMovimiento) {
                    if (c.getPublicado() != null && c.getPublicado()) {
                        tramiteHelper.getListaTipoMovimiento().add(
                                new SelectItem(c.getId(), c.getNombre(), c.getDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los tipos de movimientos por clase", e);
        }
    }

    /**
     * Este método procesa la selección del Tipo de Movimiento.
     */
    public void controlSeleccionTipoMovimiento() {
        try {
            TipoMovimiento tipoMovimiento = admServicio.buscarTipoMovimientoPorId(
                    tramiteHelper.getTramite().getTipoMovimiento().getId());
            if (tipoMovimiento.getId() != null) {
                tramiteHelper.getTramite().setTipoMovimiento(tipoMovimiento);
            } else {
                if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante() != null) {
                    tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().setNemonico("");
                }
            }
            areaDocumentoHabilitante();
        } catch (Exception e) {
            error(getClass().getName(), "Error en control de tipo movimiento", e);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo accion para el tramite.
     */
    public void iniciarComboTipoAccion() {
        iniciarCombos(tramiteHelper.getListaTipoAccion());
        for (TipoAccionEnum tp : TipoAccionEnum.values()) {
            tramiteHelper.getListaTipoAccion().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Método para ver el reporte de detalles de horarios.
     */
    public void generarReporteDetalleHorarios() {
        setReporte(ReportesEnum.DETALLE_HORARIOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "DETALLE DE HORARIOS");
        parametrosReporte.put("p_tramite_id", tramiteHelper.getTramite().getId().toString());
        parametrosReporte.put("p_horario_l", tramiteHelper.getTramite().
                getTipoMovimiento().getHorario());
        parametrosReporte.put("p_horario_r", tramiteHelper.getTramite().
                getTipoMovimiento().getHorarioDevengar());
        generarUrlDeReporte();
    }

    @Override
    public void generarReporte() {
        try {
            String estado = tramiteHelper.getTramite().getCodigoFase();
            String nemo = tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico();
            if (EstadosTramiteEnum.ELABORACION.getCodigo().equals(estado)) {
                impresionPdf(tramiteHelper.getMovimiento().getId().toString());
            } else if (EstadosTramiteEnum.REVISION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.VALIDACION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.APROBACION.getCodigo().equals(estado)) {
                impresionPdf(tramiteHelper.getMovimiento().getId().toString());
            } else if (EstadosTramiteEnum.LEGALIZACION.getCodigo().equals(estado)) {
                impresionPdf(tramiteHelper.getMovimiento().getId().toString());
            } else if (EstadosTramiteEnum.LEGALIZACION_NOMINA.getCodigo().equals(estado)) {
                impresionPdf(tramiteHelper.getMovimiento().getId().toString());
            }
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "No tiene accion de personal" + e.getMessage(), e);
        }
    }

    /**
     * metodo para la vista previa del reporte.
     */
    private void vistaPrevia() {
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
            UsuarioVO user = obtenerUsuarioConectado();
            setReporte(ReportesEnum.ACCIONES_PERSONAL.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_mov_id", tramiteHelper.getMovimiento().getId().toString());
            parametrosReporte.put("p_user", user.getUsuario());
//            parametrosReporte.put("p_lugar_conectado",
//                    tramiteHelper.getMovimiento().getCantonCoreNombre());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_SERVICIOS_OCASIONALES.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_SERVICIOS_OCASIONALES_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
            setReporte(ReportesEnum.MEMORANDO_IMPRESION.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_CODIGO_TRABAJO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_EVENTUAL.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_EVENTUAL_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_TEMPORADA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_TEMPORADA_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_INDEFINIDO_PRUEBA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_INDEFINIDO_PRUEBA_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_TIEMPO_INDEFINIDO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_TIEMPO_INDEFINIDO_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_PLAZO_FIJO_JORNADA_PARCIAL.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_JORNADA_PARCIAL_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_PLAZO_FIJO_PERIODO_DE_PRUEBA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_PERIODO_DE_PRUEBA_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACTA_TERMINACION_CONTRATO_SERVICIOS_OCACIONALES.getCodigo())) {
            setReporte(ReportesEnum.PROTEUS_ACTA_TERMINACION_CONTRATO_SERVICIOS_OCACIONALES.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ADEMDUM_MODIFICATORIO_CAMBIO_DEPENDENCIA.getCodigo())) {
            setReporte(ReportesEnum.PROTEUS_ADEMDUM_MODIFICATORIO_CAMBIO_DEPENDENCIA.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    tramiteHelper.getMovimiento().getId().toString());
        }
    }

    /**
     * metodo para la impresion a pdf directa del reporte.
     */
    private void impresionPdf(String movimientoId) {
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
            UsuarioVO user = obtenerUsuarioConectado();
            setReporte(ReportesEnum.ACCIONES_PERSONAL.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_mov_id", movimientoId);
            parametrosReporte.put("p_legalizador", obtenerNombreUsuario());
            parametrosReporte.put("p_user", user.getUsuario());
//            parametrosReporte.put("p_lugar_conectado",
//                    tramiteHelper.getMovimiento().getCantonCoreNombre());
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_SERVICIOS_OCASIONALES.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_SERVICIOS_OCASIONALES_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_legalizador", obtenerNombreUsuario());

            if (obtenerUsuarioConectado().getDistributivoDetalle().getDenominacionPuesto() != null) {
                parametrosReporte.put("p_cargo_legalizador", obtenerUsuarioConectado().getDistributivoDetalle().
                        getDenominacionPuesto().getNombre());
            } else {
                parametrosReporte.put("p_cargo_legalizador", "************");
            }
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
            setReporte(ReportesEnum.MEMORANDO_IMPRESION.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_CODIGO_TRABAJO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_EVENTUAL.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_EVENTUAL_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_TEMPORADA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_TEMPORADA_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_TRABAJO_INDEFINIDO_PRUEBA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_INDEFINIDO_PRUEBA_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_TIEMPO_INDEFINIDO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_TIEMPO_INDEFINIDO_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_PLAZO_FIJO_JORNADA_PARCIAL.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_JORNADA_PARCIAL_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATO_TRABAJO_PLAZO_FIJO_PERIODO_DE_PRUEBA.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_TRABAJO_PLAZO_FIJO_PERIODO_DE_PRUEBA_IMPR.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACTA_TERMINACION_CONTRATO_SERVICIOS_OCACIONALES.getCodigo())) {
            setReporte(ReportesEnum.PROTEUS_ACTA_TERMINACION_CONTRATO_SERVICIOS_OCACIONALES.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ADEMDUM_MODIFICATORIO_CAMBIO_DEPENDENCIA.getCodigo())) {
            setReporte(ReportesEnum.PROTEUS_ADEMDUM_MODIFICATORIO_CAMBIO_DEPENDENCIA.getReporte());
            parametrosReporte = new HashMap<>();
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id", movimientoId);
        }
    }

    /**
     * Controlar el area del documento habilitante.
     */
    public void areaDocumentoHabilitante() {
        if (tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante() != null) {
            DocumentoHabilitante dh = tramiteHelper.getTramite().getTipoMovimiento().getDocumentoHabilitante();
            if (dh.getCatalogoTipoDocumentoHabilitante() != null) {
                tramiteHelper.setEsAccionPersonal(dh.getCatalogoTipoDocumentoHabilitante().getCodigo().
                        equals(TipoDocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo()));
                tramiteHelper.setEsContrato(dh.getCatalogoTipoDocumentoHabilitante().getCodigo().equals(
                        TipoDocumentoHabilitanteEnum.CONTRATO.getCodigo()));
                tramiteHelper.setEsMemorando(dh.getCatalogoTipoDocumentoHabilitante().getCodigo().equals(
                        TipoDocumentoHabilitanteEnum.MEMORANDO.getCodigo()));
            }
        }
        procesarCamposDocumentoHabilitante();
    }

    /**
     * Este metodo guarda datos de documento habilita de forma masiva.
     *
     * @return String.
     */
    public String guardarDatosDocumentoHabilitanteMasivo() {
        try {
            if (tramiteHelper.getTramite().getTramiteAuxiliar().getTipoPeriodo() != null
                    && tramiteHelper.getTramite().getTramiteAuxiliar().getTipoPeriodo().equals(
                            TipoPeriodoMovimientoEnum.FECHA.getCodigo())
                    && validarFechaRige(tramiteHelper.getTramite().getTramiteAuxiliar())) {
                mostrarMensajeEnPantalla("La fecha 'Rige a Partir De' debe ser menor que la fecha 'Hasta'",
                        FacesMessage.SEVERITY_WARN);
            } else {
                /*Manejo de strings: explicacion,antecedentes, actividades, asuntomemo, contenidomemo */
                String explicacion = retirarEspaciosCadenaTexto(tramiteHelper.getTramite().getTramiteAuxiliar().
                        getExplicacion());
                tramiteHelper.getTramite().getTramiteAuxiliar().setExplicacion(explicacion);
                String antecedentes = retirarEspaciosCadenaTexto(tramiteHelper.getTramite().getTramiteAuxiliar().
                        getAntecedentesContrato());
                tramiteHelper.getTramite().getTramiteAuxiliar().setAntecedentesContrato(antecedentes);
                String actividades = retirarEspaciosCadenaTexto(tramiteHelper.getTramite().getTramiteAuxiliar().
                        getActividadesContrato());
                tramiteHelper.getTramite().getTramiteAuxiliar().setActividadesContrato(actividades);
                String asuntomemo = retirarEspaciosCadenaTexto(tramiteHelper.getTramite().getTramiteAuxiliar().
                        getAsuntoMeno());
                tramiteHelper.getTramite().getTramiteAuxiliar().setAsuntoMeno(asuntomemo);
                String contenidomemo = retirarEspaciosCadenaTexto(tramiteHelper.getTramite().getTramiteAuxiliar().
                        getContenidoMemo());
                tramiteHelper.getTramite().getTramiteAuxiliar().setContenidoMemo(contenidomemo);

                tramiteServicio.cambioValoresDocumentoHabilitanteTramite(tramiteHelper.getTramite());
                ejecutarComandoPrimefaces("panelDocumentoHabilitante.hide();");
                buscarMovimientos();
                actualizarComponente("fmrTramite:busquedaPuestoHelper_listaPuestos");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar los datos masivamente.", e);
        }
        return null;
    }

    /**
     * Este metodo guarda datos de firmas de forma masiva.
     *
     * @return String.
     */
    public String guardarDatosFirmasMasivo() {
        try {
            tramiteServicio.cambioValoresFirmasTramite(tramiteHelper.getTramite());
            ejecutarComandoPrimefaces("panelFirmas.hide();");
            buscarMovimientos();
            actualizarComponente("fmrTramite:busquedaPuestoHelper_listaPuestos");
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar los datos masivamente.", e);
        }
        return null;
    }

    /**
     * Este metodo evalua la fecha apartir de y la fecha hasta.
     *
     * @param movimiento Movimiento
     * @return Boolean
     */
    private Boolean validarFechaRige(final TramiteAuxiliar movimiento) {
        return movimiento.getRigeApartirDe() != null && movimiento.getFechaHasta() != null
                && movimiento.getRigeApartirDe().after(movimiento.getFechaHasta());
    }

    /**
     * Este metodo guarda el archivo.
     */
    public void subirArchivo() {
        try {
            tramiteHelper.getTramite().setCatalogoAutoridadNominadora(new Catalogo());
            tramiteHelper.getTramite().setCatalogoRepresentanteRRHH(new Catalogo());
            tramiteHelper.getErroresCargaMasiva().clear();
            if (tramiteHelper.getArchivo() == null) {
                FacesMessage msg = new FacesMessage("El archivo esta vacio.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                ParametroGlobal buscarParametroGlobalPorNemonico = admServicio.buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (tramiteHelper.getArchivo().getSize() > buscarParametroGlobalPorNemonico.getValorNumerico().
                        longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo supera los "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 2048) + " MB",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    // validar que sea un archivo csv
                    if (tramiteHelper.getArchivo().getContentType().contains("csv")
                            || tramiteHelper.getArchivo().getContentType().contains("excel")) {
                        List<String> errores = tramiteServicio.enviarCargaMasiva(tramiteHelper.getTramite().getId(),
                                tramiteHelper.getArchivo().
                                getFileName(), tramiteHelper.getArchivo().getInputstream(), obtenerUsuarioConectado());
                        if (!errores.isEmpty()) {
                            mostrarMensajeEnPantalla(
                                    "ec.gob.mrl.smp.pantalla5.edicionTramite.mensaje.archivoMasivo.errores",
                                    FacesMessage.SEVERITY_ERROR);
                            tramiteHelper.setErroresCargaMasiva(errores);
                        } else {
                            mostrarMensajeEnPantalla("ec.gob.mrl.smp.pantalla5.edicionTramite.mensaje.archivoMasivo",
                                    FacesMessage.SEVERITY_INFO);
                        }
                        for (String err : errores) {
                            System.out.println(err);
                        }
                        buscarMovimientos();
                    } else {
                        mostrarMensajeEnPantalla("Archivo cargado no corresponde a un CSV",
                                FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al subir el archivo.", ex);
        }
    }

    /**
     *
     */
    public void generarNumeroRegistroMasivo() {
        try {
            for (Movimiento m : tramiteHelper.getListaMovimientos()) {
                if (m.getVigente()) {
                    tramiteServicio.generarNumeroRegistro(m.getId(), obtenerUsuarioConectado());
                }
            }
            buscarMovimientos();
            actualizarComponente("busquedaPuestoHelper_listaPuestos");
        } catch (Exception ex) {
            error(getClass().getName(), "Error al generar Número Registro", ex);
        }
    }

    /**
     * @return the tramiteHelper
     */
    public TramiteHelper getTramiteHelper() {
        return tramiteHelper;
    }

    /**
     * @param tramiteHelper the tramiteHelper to set
     */
    public void setTramiteHelper(final TramiteHelper tramiteHelper) {
        this.tramiteHelper = tramiteHelper;
    }

    /**
     * @return the busquedaPuestoHelper
     */
    public BusquedaPuestoHelper getBusquedaPuestoHelper() {
        return busquedaPuestoHelper;
    }

    /**
     * @param busquedaPuestoHelper the busquedaPuestoHelper to set
     */
    public void setBusquedaPuestoHelper(final BusquedaPuestoHelper busquedaPuestoHelper) {
        this.busquedaPuestoHelper = busquedaPuestoHelper;
    }

    /**
     * @return the agregarServidorHelper
     */
    public AgregarServidorHelper getAgregarServidorHelper() {
        return agregarServidorHelper;
    }

    /**
     * @param agregarServidorHelper the agregarServidorHelper to set
     */
    public void setAgregarServidorHelper(final AgregarServidorHelper agregarServidorHelper) {
        this.agregarServidorHelper = agregarServidorHelper;
    }

    /**
     *
     * @return
     */
    public StreamedContent getFormatoMovimientoPersonal() {
        InputStream streamOperativo = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().
                getContext()).getResourceAsStream("/pdf/Formato_archivos_movimiento_personal_20160206.pdf");
        return new DefaultStreamedContent(streamOperativo,
                "application/pdf", "Formato_archivos_movimiento_personal_20160206.pdf");
    }

    /**
     *
     */
    public StreamedContent descargarDocumentosHabilitantes() {
        StreamedContent sc = null;
        Reporte r = null;
        try {
            if (tramiteHelper.getListaMovimientos().isEmpty()) {
                mostrarMensajeEnPantalla("No existen movimientos", FacesMessage.SEVERITY_ERROR);

            } else {
                List<File> pdfs = new ArrayList<>();
                for (Movimiento movimiento : tramiteHelper.getListaMovimientos()) {
                    impresionPdf(movimiento.getId().toString());
                    if (r == null) {
                        r = reporteDao.buscarPorReporte(getReporte());
                    }
                    String ruta = generarURLdeReporte(r);
                    URL url = new URL(ruta);
                    File temporal = File.createTempFile("prefix-", "-suffix");
                    FileUtils.copyURLToFile(url, temporal, 20000, 20000);
                    pdfs.add(temporal);
                }
                File pdf = File.createTempFile("prefix-", "-suffix");
                UtilArchivos.concatenarPdfs(pdfs, pdf);
                sc = new DefaultStreamedContent(new FileInputStream(pdf),
                        "application/pdf", "tramite_" + tramiteHelper.getTramite().getNumericoTramite() + ".pdf");
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al descargar los documentos habilitantes", e);
        }
        return sc;

    }

}
