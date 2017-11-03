/*
 *  VerificarRequisitoControlador.java
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
 *  19/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.VerificarRequisitoHelper;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ObligatorioEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.Validacion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.SenescytServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.vo.ValidacionTipoMovimientoRequisitoVO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "verificarRequisitoBean")
@ViewScoped
public class VerificarRequisitoControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(VerificarRequisitoControlador.class.getName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/tramite/lista_verificar_requisito.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/verificar_requisito.jsf";

    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_TRAMITE = "/pages/procesos/tramite/tramite.jsf";

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{verificarRequisitoHelper}")
    private VerificarRequisitoHelper verificarRequisitoHelper;

    /**
     * Administracion de servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * tramite servicio.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * SenescytServicio servicio.
     */
    @EJB
    private SenescytServicio senescytServicio;

    /**
     * Variable usada para asignar el archivo que se va a descargar.
     */
    private StreamedContent file;

    /**
     * Constructor por defecto.
     */
    public VerificarRequisitoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        LOG.info("init.................:");
        verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        verificarRequisitoHelper.setAbrirPopUpCargarArchivos(Boolean.FALSE);
        verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.FALSE);
    }

    /**
     * Metodo que inicia la pantalla de tipo lista de requisitos por tipo de movimiento.
     *
     * @return pantalla de tipo lista de la entidad
     */
    public String iniciarValidacionRequisitos() {
        iniciarDatosNecesarios(verificarRequisitoHelper.getMovimiento(), null, false);
        reglaNavegacionDirecta(LISTA_ENTIDAD);
        return null;
    }

    private void iniciarDatosNecesarios(Movimiento movimiento, List<ValidacionTipoMovimientoRequisitoVO> lista,
            boolean usarListaMasificada) {
        cargarListaRequisitosPorTipoMovimiento(movimiento.getId());
        verificarEstadoTramite(movimiento.getTramite());
        asignarCampoCumple(usarListaMasificada ? lista
                : verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO());
    }

    /**
     * Metodo que se encarga de asignar en el campo cumple cambio de valor lo que esta en cumple antes de cambiar de
     * valor.
     *
     * @param lista
     */
    public void asignarCampoCumple(List<ValidacionTipoMovimientoRequisitoVO> lista) {
        for (ValidacionTipoMovimientoRequisitoVO v : lista) {
            v.setCumpleCambioValor(v.getValidacion().getCumple());
        }
    }

    /**
     * cargar en pantalla las dos listas la del senescyt y la del servidor.
     */
    public void cargarListas() {
        cargarListaSenescyt();
        cargarListaServidorServicio();
    }

    /**
     * Carga de la lista de informacion academica del Senescyt.
     *
     * @return LISTA_ENTIDAD
     */
    public String cargarListaSenescyt() {
        try {
            verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.TRUE);
            verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
            verificarRequisitoHelper.getListaTituloIntegracion().clear();
            verificarRequisitoHelper.setListaTituloIntegracion(
                    senescytServicio.buscarTitulos(
                            verificarRequisitoHelper.getMovimiento().getNumeroIdentificacion()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Metodo que abre el panel popup de sustento legal.
     */
    public void verSustentoLegalv() {
        verificarRequisitoHelper.setSustentoLegal(verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().
                getTipoMovimientoRequisito().getRequisito().getSustentoLegal());
        verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.TRUE);
        verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.FALSE);
    }

    /**
     * Carga de lista Informacion academica del siith core.
     *
     * @return LISTA_ENTIDAD
     */
    public String cargarListaServidorServicio() {
//        try {
//            verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.TRUE);
//            verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
//            verificarRequisitoHelper.getListaServidorFormacionAcademica().clear();
//            verificarRequisitoHelper.setListaServidorFormacionAcademica(
//                    servidorServicio.buscarFormacionAcademica(
//                    verificarRequisitoHelper.getMovimiento().getServidorId()));
//            System.out.println("id del servidor----" + verificarRequisitoHelper.getMovimiento().getServidorId());
//        } catch (Exception ex) {
//            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
//            error(getClass().getName(), "Error la procesar la busqueda", ex);
//        }
        return LISTA_ENTIDAD;
    }

    /**
     * control para presentar el icono de la configuracion de la regla cuando es de Informacion Academica.
     *
     * @param nemo Nemonico
     * @return retorno
     */
    public Boolean validarParametroGlobalBoton(final String nemo) {
        Boolean retorno = Boolean.FALSE;
        try {
            ParametroGlobal pNemonicoInformacionAcademica
                    = administracionServicio.buscarParametroGlobalPorNemonico(
                            ParametroGlobalEnum.VERIFICAR_FORMACION_ACADEMICA.getCodigo());
            if (pNemonicoInformacionAcademica.getValorTexto().contains(nemo)) {
                retorno = Boolean.TRUE;
            }
        } catch (ServicioException ex) {
            Logger.getLogger(VerificarRequisitoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /**
     * Metodo que se encarga de buscar los requisitos por tipo de movimientos mediante el id de tipo de movimiento.
     *
     * @param movimientoId Id del movimeinto
     */
    public void cargarListaRequisitosPorTipoMovimiento(final Long movimientoId) {
        try {
            List<TipoMovimientoRequisito> listaTipoMovimientoRequisito
                    = administracionServicio.listarTipoMovimientoRequisitoPorTipoMovimientoId(
                            verificarRequisitoHelper.getMovimiento().getTramite().getTipoMovimiento().getId());
            List<Validacion> listaValidacion = administracionServicio.listarValidacionPorMovimientoId(movimientoId);
            verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO().clear();
            for (TipoMovimientoRequisito tmr : listaTipoMovimientoRequisito) {
                ValidacionTipoMovimientoRequisitoVO vtmrvo = new ValidacionTipoMovimientoRequisitoVO();
                vtmrvo.setValidacion(new Validacion());
                vtmrvo.setTipoMovimientoRequisito(tmr);
                verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO().add(vtmrvo);
                for (Validacion val : listaValidacion) {
                    if (tmr.getId().equals(val.getTipoMovimientoRequisito().getId())
                            && val.getMovimiento().getId().equals(movimientoId)) {
                        vtmrvo.setValidacion(val);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "Error al cargar a tabla de requisitos por tipo de movimiento " + ex);
        }
    }

    /**
     * Metodo que se encarga de validar el boolean recibido para retornar un Si o No y mostrar en la lista.
     *
     * @param obligatorio parametro a evaluar
     * @return String resultado
     */
    public String verificarObligatorio(final Boolean obligatorio) {
        String resultado;
        if (obligatorio) {
            resultado = ObligatorioEnum.SI.getDescripcion();
        } else {
            resultado = ObligatorioEnum.NO.getDescripcion();
        }
        return resultado;
    }

    /**
     * Metodo que se encarga de validar el boolean recibido como parametro para retornar el String si o no.
     *
     * @param presentaServidorPublico Boolean a evaluar
     * @return String
     */
    public String verificarPresentaServidorPublico(final Boolean presentaServidorPublico) {
        String resultado;
        if (presentaServidorPublico) {
            resultado = ObligatorioEnum.SI.getDescripcion();
        } else {
            resultado = ObligatorioEnum.NO.getDescripcion();
        }
        return resultado;
    }

    /**
     * Metodo que se encarga del iniciar la entidad para el ingreso de la informacion necesaria.
     *
     * @return Strinf formulario
     */
    public String iniciarIngresoInformacion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(
                    verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoEditVO());
            ValidacionTipoMovimientoRequisitoVO vtmrvo = (ValidacionTipoMovimientoRequisitoVO) cloneBean;
            validarIngresoInformacionNuevo(vtmrvo);
            validarRequisitoTieneCalificacion(vtmrvo);
            iniciarDatosEntidad(vtmrvo.getValidacion(), Boolean.FALSE);
            verificarRequisitoHelper.setValidacionTipoMovimientoRequisitoVO(vtmrvo);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de validar si al ingreso de informacion es nuevo o no, y setea una bandera para poder crear
     * un nuevo o solo editar.
     *
     * @param vtmrvo ValidacionTipoMovimientoRequisitoVO
     */
    public void validarIngresoInformacionNuevo(final ValidacionTipoMovimientoRequisitoVO vtmrvo) {
        if (vtmrvo.getValidacion().getId() == null) {
            verificarRequisitoHelper.setEsNuevo(Boolean.TRUE);
        } else {
            verificarRequisitoHelper.setEsNuevo(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de validar si el requisito tiene calificacion para activar el campo de calificacion.
     *
     * @param vtmrvo ValidacionTipoMovimientoRequisitoVO
     * @return Boolean para saber si activa o no el campo
     */
    public Boolean validarRequisitoTieneCalificacion(final ValidacionTipoMovimientoRequisitoVO vtmrvo) {
        Boolean resultado;
        if (vtmrvo.getTipoMovimientoRequisito().getRequisito().getTieneCalificacion()) {
            resultado = Boolean.TRUE;
        } else {
            resultado = Boolean.FALSE;
        }
        return resultado;
    }

    /**
     * Metodo que se encarga de guardar las validaciones de requisitos.
     *
     * @return String
     */
    public String guardar() {
        try {
            List<ValidacionTipoMovimientoRequisitoVO> listaTipoMovimientoRequisitoMasificada = new ArrayList<>();
            listaTipoMovimientoRequisitoMasificada.addAll(verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO());
            List<Movimiento> listaMovimientos = new ArrayList<>();
            if (!verificarRequisitoHelper.getRegistroMasivoRequisitos()) {
                listaMovimientos.add(verificarRequisitoHelper.getMovimiento());
            } else {
                listaMovimientos = tramiteServicio.buscarMovimientosPorTramite(
                        verificarRequisitoHelper.getMovimiento().getTramite().getId());
            }

            verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.FALSE);

            for (Movimiento m : listaMovimientos) {
                iniciarDatosNecesarios(m, listaTipoMovimientoRequisitoMasificada, true);

                List<ValidacionTipoMovimientoRequisitoVO> lista
                        = verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO();

                tomarCumplimientosDeMasificada(lista, listaTipoMovimientoRequisitoMasificada);

                List<ValidacionTipoMovimientoRequisitoVO> listaGuardar = new ArrayList<>();

                if (!lista.isEmpty()) {
                    for (ValidacionTipoMovimientoRequisitoVO vtmvo : lista) {
                        if (vtmvo.getTipoMovimientoRequisito().getSubirArchivoObligatorio()
                                && vtmvo.getArchivo() == null && vtmvo.getValidacion().getArchivo() == null) {
                            mostrarMensajeEnPantalla(
                                    getBundle("ec.gob.mrl.smp.verificarRequisito.boton.cargarArchivoObligatorio",
                                            new Object[]{vtmvo.getTipoMovimientoRequisito().getRequisito().getNombre()}),
                                    FacesMessage.SEVERITY_ERROR);
                            listaGuardar = new ArrayList<>();
                            break;
                        } else {
                            listaGuardar.add(vtmvo);
                        }
                    }
                }
                if (!listaGuardar.isEmpty()) {
                    administracionServicio.guardarValidacion(listaGuardar,
                            verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().getTipoMovimientoRequisito(),
                            m, obtenerUsuario());
                    // listaGuardar = new ArrayList<>();
                }
            }
            verificarRequisitoHelper.setRegistroMasivoRequisitos(Boolean.FALSE);
            verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(null);
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento", ex);
        }
        return null;
    }

    /**
     * Verifica la calificacion a guardar o editar.
     *
     * @return String resultado
     */
    public String verificarTieneCalificacion() {
        String resultado;
        if (verificarRequisitoHelper.getTieneCalificacion()) {
            resultado = verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().
                    getValidacion().getCalificacion();
        } else {
            resultado = null;
        }
        return resultado;
    }

    /**
     * Metodo que se encarga de regresar a la pantalla de la lista despues de ingresar la informacion en el formulario.
     *
     * @return String Pantalla de la lista de verificar requisitos
     */
    public String cancelarVerificarRequisitos() {
        cargarListaRequisitosPorTipoMovimiento(verificarRequisitoHelper.getMovimiento().getId());
        return LISTA_ENTIDAD;
    }

    /**
     * Metodo que abre el panel popup de sustento legal.
     */
    public void verSustentoLegal() {
        verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.TRUE);
    }

    /**
     * Metodo que abre el panel popup de cargar archivo.
     */
    public void verCargarArchivo() {
        verificarRequisitoHelper.setAbrirPopUpCargarArchivos(Boolean.TRUE);
    }

    /**
     * Metodo que abre el panel popup de sustento legal.
     */
    public void verInformacionAcademica() {
        verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.TRUE);
    }

    /**
     * Metodo que se encarga de regresar a la pantalla de tramite.
     *
     * @throws IOException En caso de error
     */
    public void regresarPantallaTramite() throws IOException {
        String codigoFaseTramite = verificarRequisitoHelper.getMovimiento().getTramite().getCodigoFase();
        if (codigoFaseTramite.equals(EstadosTramiteEnum.ELABORACION.getCodigo())) {
            reglaNavegacionDirecta(PANTALLA_TRAMITE + "?est=edt");
        }
        if (codigoFaseTramite.equals(EstadosTramiteEnum.REVISION.getCodigo())
                || codigoFaseTramite.equals(EstadosTramiteEnum.APROBACION.getCodigo())
                || codigoFaseTramite.equals(EstadosTramiteEnum.VALIDACION.getCodigo())) {
            reglaNavegacionDirecta(PANTALLA_TRAMITE + "?est=vr");
        }
    }

    /**
     * Metodo que realiza la carga de un archivo pdf.
     *
     * @param event FileUploadEvent
     * @throws ServicioException en caso de error
     */
    public void upload() throws ServicioException {
        verificarRequisitoHelper.setVerPopUpInformacionAcademica(Boolean.FALSE);
        verificarRequisitoHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        if (verificarRequisitoHelper.getArchivoCargado() != null) {
            if (verificarRequisitoHelper.getArchivoCargado().getContentType().equals("application/pdf")) {
                ParametroGlobal buscarParametroGlobalPorNemonico
                        = administracionServicio.buscarParametroGlobalPorNemonico(
                                ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (verificarRequisitoHelper.getArchivoCargado().getSize() > buscarParametroGlobalPorNemonico.
                        getValorNumerico().longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo supera los "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 1024) + " MB",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoCorrectamente",
                            FacesMessage.SEVERITY_INFO);
                    System.out.println("Archivo Cargado " + verificarRequisitoHelper.getArchivoCargado().getFileName());
                    verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(new Archivo());
                    verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().getArchivo().setArchivo(
                            verificarRequisitoHelper.getArchivoCargado().getContents());
                    verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().getArchivo().setNombre(
                            verificarRequisitoHelper.getArchivoCargado().getFileName());

                    ValidacionTipoMovimientoRequisitoVO va = verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO();
                    System.out.println(" archivo*** ==> " + va.getArchivo() != null ? va.getArchivo().getNombre() : null);
                    System.out.println(" id ***    ===>" + va.getTipoMovimientoRequisito().getId());
                    System.out.println(" valor***    ===>" + va.getCumpleCambioValor());
                    System.out.println(" id validacion*** ===>" + va.getValidacion() != null ? va.getValidacion().getId() : null);

                    for (ValidacionTipoMovimientoRequisitoVO v : verificarRequisitoHelper.getListaValidacionTipoMovimientoRequisitoVO()) {
                        System.out.println(" archivo ==> " + v.getArchivo() != null ? v.getArchivo().getNombre() : null);
                        System.out.println(" id     ===>" + v.getTipoMovimientoRequisito().getId());
                        System.out.println(" valor     ===>" + v.getCumpleCambioValor());
                        System.out.println(" id validacion ===>" + v.getValidacion() != null ? v.getValidacion().getId() : null);
                        if (va.getTipoMovimientoRequisito().getId().equals(v.getTipoMovimientoRequisito().getId())) {
                            System.out.println(" en el if ===>" + v.getTipoMovimientoRequisito().getId());
                            v.setArchivo(va.getArchivo());
                            break;
                        }
                    }
                }
            } else {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoNoesPDF",
                        FacesMessage.SEVERITY_ERROR);
                verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(new Archivo());
            }
        } else {
            mostrarMensajeEnPantalla("El archivo esta vacio.", FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * Metodo que se encarga de realizar la descarga del archivo.
     *
     * @param archivo Archivo a descargar
     */
    public void descargarArchivo(final Archivo archivo) {
        if (archivo != null) {
            InputStream stream = new ByteArrayInputStream(archivo.getArchivo());
            file = new DefaultStreamedContent(stream, "application/pdf", archivo.getNombre());
        }
    }

    /**
     * Metodo que se encarga de poner el archivo null a una validacion encontrada.
     */
    public void eliminarArchivo() {
        try {
            if (verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().getArchivo() == null) {
                administracionServicio.eliminarArchivoValidacion(
                        verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO(),
                        verificarRequisitoHelper.getMovimiento(), obtenerUsuario());
                cargarListaRequisitosPorTipoMovimiento(verificarRequisitoHelper.getMovimiento().getId());
            } else {
                verificarRequisitoHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(null);
            }
            mostrarMensajeEnPantalla("Archivo eliminado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla("Error al eliminar el Archivo", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al eliminar el archivo ", ex);
        }
    }

    /**
     * Metodo que se encarga de verificar el estado del tramite y setear una bandera para no dejar editar los
     * requisitos.
     *
     * @param tramite Tramite a buscar el estado.
     */
    public void verificarEstadoTramite(final Tramite tramite) {
        if (tramite.getCodigoFase().equals(EstadosTramiteEnum.ELABORACION.getCodigo())) {
            verificarRequisitoHelper.setCamposDesabilitados(Boolean.FALSE);
        } else {
            verificarRequisitoHelper.setCamposDesabilitados(Boolean.TRUE);
        }
    }

    /**
     * @return the verificarRequisitoHelper
     */
    public VerificarRequisitoHelper getVerificarRequisitoHelper() {
        return verificarRequisitoHelper;
    }

    /**
     * @param verificarRequisitoHelper the verificarRequisitoHelper to set
     */
    public void setVerificarRequisitoHelper(final VerificarRequisitoHelper verificarRequisitoHelper) {
        this.verificarRequisitoHelper = verificarRequisitoHelper;
    }

    /**
     * @return the senescytServicio
     */
    public SenescytServicio getSenescytServicio() {
        return senescytServicio;
    }

    /**
     * @param senescytServicio the senescytServicio to set
     */
    public void setSenescytServicio(final SenescytServicio senescytServicio) {
        this.senescytServicio = senescytServicio;
    }

    /**
     * @return the servidorServicio
     */
//    public ServidorServicio getServidorServicio() {
//        return servidorServicio;
//    }
    /**
     * @param servidorServicio the servidorServicio to set
     */
//    public void setServidorServicio(final ServidorServicio servidorServicio) {
//        this.servidorServicio = servidorServicio;
//    }
    /**
     * @return the File
     */
    public StreamedContent getFile() {
        return file;
    }

    private void tomarCumplimientosDeMasificada(List<ValidacionTipoMovimientoRequisitoVO> lista, List<ValidacionTipoMovimientoRequisitoVO> listaTipoMovimientoRequisitoMasificada) {
        for (ValidacionTipoMovimientoRequisitoVO vtmrvo : lista) {
            for (ValidacionTipoMovimientoRequisitoVO vtmrvoMasiva : listaTipoMovimientoRequisitoMasificada) {
                if (vtmrvo.getTipoMovimientoRequisito().getId().equals(
                        vtmrvoMasiva.getTipoMovimientoRequisito().getId())) {
                    vtmrvo.setCumpleCambioValor(vtmrvoMasiva.getCumpleCambioValor());
                    vtmrvo.getValidacion().setCumple(vtmrvoMasiva.getValidacion().getCumple());
                    break;
                }
            }
        }
    }
}
