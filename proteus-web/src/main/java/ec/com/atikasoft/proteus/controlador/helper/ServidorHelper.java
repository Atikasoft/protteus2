/*
 *  ServidorHelper.java
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
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * ServidorHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "servidorHelper")
@SessionScoped
public final class ServidorHelper implements Serializable {

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion;
    /**
     * tipo de identificacion.
     */
    private String tipoIdentificacion;
    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento;
    /**
     * tipo licencia Booleana.
     */
    private Boolean tipoCedula;
    /**
     * servidor.
     */
    private Servidor servidor;
    /**
     * listaServidores.
     */
    private List<Servidor> listaServidores;
    /**
     * variable para el archivo.
     */
    private UploadedFile archivoCargar;
    private String nombreArchivo;
    private String ImagenFirma;
    private File archivoFile;
    private StreamedContent imagen;

    /**
     * Constructor por defecto.
     */
    public ServidorHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setTipoDocumento(new ArrayList<SelectItem>());
        setServidor(new Servidor());
        getServidor().setArchivo(new Archivo());
        setListaServidores(new ArrayList<Servidor>());       
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the tipoDocumento
     */
    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(final List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the tipoCedula
     */
    public Boolean getTipoCedula() {
        return tipoCedula;
    }

    /**
     * @param tipoCedula the tipoCedula to set
     */
    public void setTipoCedula(final Boolean tipoCedula) {
        this.tipoCedula = tipoCedula;
    }

    /**
     * @return the archivoCargar
     */
    public UploadedFile getArchivoCargar() {
        return archivoCargar;
    }

    /**
     * @param archivoCargar the archivoCargar to set
     */
    public void setArchivoCargar(final UploadedFile archivoCargar) {
        this.archivoCargar = archivoCargar;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the archivoFile
     */
    public File getArchivoFile() {
        return archivoFile;
    }

    /**
     * @param archivoFile the archivoFile to set
     */
    public void setArchivoFile(File archivoFile) {
        this.archivoFile = archivoFile;
    }

    /**
     * @return the imagen
     */
    public StreamedContent getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(StreamedContent imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the ImagenFirma
     */
    public String getImagenFirma() {
        return ImagenFirma;
    }

    /**
     * @param ImagenFirma the ImagenFirma to set
     */
    public void setImagenFirma(String ImagenFirma) {
        this.ImagenFirma = ImagenFirma;
    }
}
