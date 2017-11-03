/*
 *  ServidorControlador.java
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
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ServidorHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * ServidorControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "servidorBean")
@ViewScoped
public class ServidorControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(ServidorControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/administracion/servidor/registro_firma.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{servidorHelper}")
    private ServidorHelper servidorHelper;

    /**
     * Constructor por defecto.
     */
    public ServidorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarComboTipo();
        servidorHelper.setTipoIdentificacion("");
        servidorHelper.setNumeroIdentificacion("");
        servidorHelper.getServidor().setApellidosNombres("");
        servidorHelper.setNombreArchivo("");
        servidorHelper.setImagenFirma(null);
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        servidorHelper.getTipoDocumento().clear();
        iniciarCombos(servidorHelper.getTipoDocumento());
        for (TipoDocumentoEnum t : TipoDocumentoEnum.values()) {
            servidorHelper.getTipoDocumento().add(new SelectItem(t.getNemonico(), t.getNombre()));
        }
    }

    /**
     * metodo para buscar un servidor segun su Id.
     */
    public String buscarPorCedula() {
        try {
            servidorHelper.setServidor(new Servidor());
            if (servidorHelper.getNumeroIdentificacion() != null) {
                if (servidorHelper.getTipoIdentificacion().equals(
                        TipoDocumentoEnum.CEDULA.getNemonico())
                        || servidorHelper.getTipoIdentificacion().
                        equals(TipoDocumentoEnum.PASAPORTE.getNemonico())) {
                    servidorHelper.setServidor(
                            admServicio.buscarServidorPorTipoDocumento(servidorHelper.getNumeroIdentificacion()));
                    if (servidorHelper.getServidor() != null) {
                        if (servidorHelper.getServidor().getArchivo() != null) {
                            servidorHelper.setNombreArchivo(servidorHelper.getServidor().getArchivo().getNombre());
                        } else {
                            servidorHelper.setNombreArchivo("");
                        }
                    }
                    if (servidorHelper.getServidor() == null) {
                        servidorHelper.setNombreArchivo("");
                         servidorHelper.setImagenFirma(null);
                        servidorHelper.setServidor(new Servidor());
                        ponerMensajeError(NADA, "Servidor no registrado");
                    } else {
                        cargarImagen();
                    }
                }
            } else {
                ponerMensajeError(NADA, "No a ingresado el numero de identificación");
            }

        } catch (Exception ex) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void cargarImagen() {
        if (servidorHelper.getServidor() != null
                && servidorHelper.getServidor().getArchivo() != null
                && servidorHelper.getServidor().getArchivo().getArchivo() != null) {
            String logoId = servidorHelper.getServidor().getArchivoId().toString();
            servidorHelper.setImagenFirma(UtilCadena.concatenar("/imageServlet/", logoId));
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
            servidorHelper.setArchivoCargar(file);
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
                servidorHelper.getServidor().setArchivo(new Archivo());
                iniciarDatosEntidad(servidorHelper.getServidor().getArchivo(), Boolean.TRUE);
                servidorHelper.getServidor().getArchivo().setArchivo(UtilArchivos.getBytesFromFile(f));
                servidorHelper.getServidor().getArchivo().setNombre(nombreArchivo);
                servidorHelper.setNombreArchivo(nombreArchivo);
                servidorHelper.setArchivoFile(UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt, extencionSinPunto));
                admServicio.guardarArchivoFirmas(servidorHelper.getServidor(), servidorHelper.getArchivoFile());
                cargarImagen();
                FacesMessage msg = new FacesMessage("El archivo se subió correctamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public String guardar() {
        try {
            if (servidorHelper.getServidor().getId() != null) {
                if (servidorHelper.getServidor().getArchivoId() != null) {
                    servidorHelper.getServidor().getArchivo().setId(servidorHelper.getServidor().getArchivoId());
                }
                servidorHelper.getServidor().getArchivo().setNombre(servidorHelper.getNombreArchivo());
                admServicio.guardarServidorArchivo(servidorHelper.getServidor(), obtenerUsuarioConectado().getServidor().
                        getNumeroIdentificacion());
                limpiarFittros();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla("No se a consultado ningun servidor todavía", FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    public void limpiarFittros() {
        servidorHelper.setServidor(new Servidor());
        servidorHelper.getServidor().setArchivo(new Archivo());
        servidorHelper.setTipoIdentificacion("");
        servidorHelper.setImagenFirma(null);
        servidorHelper.setNumeroIdentificacion("");
        servidorHelper.getServidor().getArchivo().setNombre("");
    }

    /**
     * @return the servidorHelper
     */
    public ServidorHelper getServidorHelper() {
        return servidorHelper;
    }

    /**
     * @param servidorHelper the servidorHelper to set
     */
    public void setServidorHelper(final ServidorHelper servidorHelper) {
        this.servidorHelper = servidorHelper;
    }
}
