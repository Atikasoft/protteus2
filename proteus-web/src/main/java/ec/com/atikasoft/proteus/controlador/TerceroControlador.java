/*
 *  TerceroControlador.java
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

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.TerceroHelper;
import ec.com.atikasoft.proteus.enums.ActivoInactivoEnum;
import ec.com.atikasoft.proteus.enums.EstadoTerceroEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Tercero;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Tercero
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "terceroBean")
@ViewScoped
public class TerceroControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(TerceroControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/tercero/tercero.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tercero/lista_tercero.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{terceroHelper}")
    private TerceroHelper terceroHelper;

    /**
     * Constructor por defecto.
     */
    public TerceroControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        buscar();
        iniciarComboEstados();
        iniciarComboTipoIdentificacion();
    }

    public String guardar() {
        try {
            if (terceroHelper.getTercero().getNombres() == null || terceroHelper.getTercero().getNombres().isEmpty()) {
                mostrarMensajeEnPantalla("El nombre es campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (terceroHelper.getTercero().getValor() == null) {
                mostrarMensajeEnPantalla("El valor es campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (terceroHelper.getTercero().getDescripcion() == null || terceroHelper.getTercero().getDescripcion().isEmpty()) {
                mostrarMensajeEnPantalla("La descripción es campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (terceroHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarTercero(terceroHelper.getTercero());
                    if (terceroHelper.getTercero().getArchivo() != null) {
                        admServicio.guardarArchivoTerceros(terceroHelper.getTercero(), terceroHelper.getArchivoFile());
                    }
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                admServicio.actualizarTercero(terceroHelper.getTercero());
                if (terceroHelper.getTercero().getArchivo() != null) {
                    admServicio.guardarArchivoTerceros(terceroHelper.getTercero(), terceroHelper.getArchivoFile());
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            terceroHelper.getListaTerceroCodigo().clear();
            terceroHelper.setListaTerceroCodigo(admServicio.listarTodosTerceroPorIdentificacion(
                    terceroHelper.getTercero().getTipoIdentificacion(), terceroHelper.getTercero().getNumeroIdentificacion()));

            if (terceroHelper.getListaTerceroCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(terceroHelper.getTerceroEditDelete());
            Tercero d = (Tercero) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            terceroHelper.setTercero(d);
            terceroHelper.setEsNuevo(Boolean.FALSE);
            System.out.println(" archivo:" + terceroHelper.getTercero().getArchivo());
            if (terceroHelper.getTercero().getArchivo() != null) {
                terceroHelper.setNombreArchivo(terceroHelper.getTercero().getArchivo().getNombre());
                System.out.println("nombre de archivo:" + terceroHelper.getNombreArchivo());
            }
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

    public String iniciarNuevo() {
        terceroHelper.setTercero(new Tercero());
        iniciarDatosEntidad(terceroHelper.getTercero(), Boolean.TRUE);
        terceroHelper.setEsNuevo(Boolean.TRUE);
        terceroHelper.setNombreArchivo("");
        terceroHelper.getTercero().setEstado(ActivoInactivoEnum.ACTIVO.getCodigo());
        terceroHelper.getTercero().setInstitucionEjercicioFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
        return FORMULARIO_ENTIDAD;
    }

    public String borrar() {
        try {
            admServicio.eliminarTercero(terceroHelper.getTerceroEditDelete());
            terceroHelper.getListaTerceros().
                    remove(terceroHelper.getTerceroEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    public String buscarDetalles() {
        try {
            terceroHelper.setListaTercerosEnNomina(
                    admServicio.listarTodosTercerosEnNominaPorTercero(terceroHelper.getTercero().getId()));
            ejecutarComandoPrimefaces("dlgDetalles.show();");
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error consulta de detalles de terceros", ex);
        }
        return null;
    }

    public String buscar() {
        try {
            terceroHelper.getListaTerceros().clear();
            terceroHelper.setListaTerceros(
                    admServicio.listarTodosTerceros());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Permite buscar al Tercero.
     *
     * @return
     */
    public String buscarTercero() {
        if (terceroHelper.getTercero().getTipoIdentificacion() != null
                && terceroHelper.getTercero().getNumeroIdentificacion() != null
                && !terceroHelper.getTercero().getTipoIdentificacion().isEmpty()
                && !terceroHelper.getTercero().getNumeroIdentificacion().isEmpty()) {
            try {
                terceroHelper.setPersonaVO(
                        servidorServicio.buscarPersona(terceroHelper.getTercero().
                                getTipoIdentificacion(),
                                terceroHelper.getTercero().getNumeroIdentificacion(),
                                obtenerUsuarioConectado()));
                if (terceroHelper.getPersonaVO() != null && terceroHelper.
                        getPersonaVO().getNumeroIdentificacion() != null) {
                    terceroHelper.getTercero().setNombres(terceroHelper.
                            getPersonaVO().getNombres());
                    System.out.println(" tercero encontrado:" + terceroHelper.getTercero().getNombres());
                } else {
                    mostrarMensajeEnPantalla(BUSQUEDA_SIN_RESULTADOS, FacesMessage.SEVERITY_ERROR);
                    terceroHelper.getTercero().setNombres("");
                }
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Problemas al buscar terceros ", ex);
            }
        } else {
            mostrarMensajeEnPantalla(
                    "Los campos Tipo y Número de Identificación son requeridos ",
                    FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de estado.
     */
    private void iniciarComboEstados() {
        terceroHelper.getListaEstados().clear();
        iniciarCombos(terceroHelper.getListaEstados());
        for (ActivoInactivoEnum t : ActivoInactivoEnum.values()) {
            terceroHelper.getListaEstados().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de estado.
     *
     * @return
     */
    public List<SelectItem> getListaEstadosTerceroNomina() {
        List<SelectItem> lista = new ArrayList<SelectItem>();
        iniciarCombos(lista);
        for (EstadoTerceroEnum t : EstadoTerceroEnum.values()) {
            lista.add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
        return lista;
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
     * Este metodo llena las opciones para el combo de estado.
     */
    private void iniciarComboTipoIdentificacion() {
        terceroHelper.getListaTipoIdentificacion().clear();
        iniciarCombos(terceroHelper.getListaTipoIdentificacion());
        for (TipoDocumentoEnum t : TipoDocumentoEnum.values()) {
            terceroHelper.getListaTipoIdentificacion().add(new SelectItem(t.getNemonico(), t.getNombre()));
        }
    }

    /**
     * Archivo.
     *
     * @param event FileUploadEvent
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            terceroHelper.setArchivoCargado(file);
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
                terceroHelper.getTercero().setArchivo(new Archivo());
                iniciarDatosEntidad(terceroHelper.getTercero().getArchivo(), Boolean.TRUE);
                terceroHelper.getTercero().getArchivo().setArchivo(UtilArchivos.getBytesFromFile(f));
                terceroHelper.getTercero().getArchivo().setNombre(nombreArchivo);
                terceroHelper.setArchivoFile(UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt,
                        extencionSinPunto));

                if (terceroHelper.getTercero().getArchivo() != null) {
                    terceroHelper.setNombreArchivo(terceroHelper.getTercero().getArchivo().getNombre());
                }
                FacesMessage msg = new FacesMessage("El archivo se subió correctamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Permite regresar al listado principal.
     *
     * @return
     */
    public String irLista() {
        buscar();
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * tercero.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoTercero(final String codigo) {
        return EstadoTerceroEnum.obtenerDescripcion(codigo);
    }
    
    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * tercero.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionActivoInactivo(final String codigo) {
        return ActivoInactivoEnum.obtenerDescripcion(codigo);
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
     * @return the terceroHelper
     */
    public TerceroHelper getTerceroHelper() {
        return terceroHelper;
    }

    /**
     * @param terceroHelper the terceroHelper to set
     */
    public void setTerceroHelper(TerceroHelper terceroHelper) {
        this.terceroHelper = terceroHelper;
    }

}
