/*
 *  ValidarMensajeHelper.java
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
 *  03/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.vo.JustificacionVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "validarMensajeHelper")
@SessionScoped
public class ValidarMensajeHelper extends CatalogoHelper {

    /**
     * Objeto Grupo.
     */
    private Grupo grupo;

    /**
     * lista de mensajes.
     */
    private List<ReglaMensaje> listaMensajes = new ArrayList<ReglaMensaje>();

    /**
     * Entidad de regla mensaje.
     */
    private ReglaMensaje reglaMensaje;

//    /**
//     * Bandera usada para abrir o cerrar el popup de justificacion.
//     */
//    private Boolean verPopUpJustificacion;

//    /**
//     * Variable usada para el numero de documento de Justificacion.
//     */
//    private String numeroDocumento;
//
//    /**
//     * Variable usada para la fecha de documento de Justificacion.
//     */
//    private Date fechaDocumento;
//
//    /**
//     * Variable usada para el documento de Justificacion.
//     */
//    private Archivo documento;
//
//    /**
//     * Variable usada para la observacion de Justificacion.
//     */
//    private String observacion;

  
    /**
     * constructos del Helper.
     */
    public ValidarMensajeHelper() {
        super();
    }

    /**
     * Init.
     */
    public final void init() {
        setListaMensajes(new ArrayList<ReglaMensaje>());
        reglaMensaje = new ReglaMensaje();
        //archivoRequerido = Boolean.TRUE;
//        verPopUpJustificacion = Boolean.FALSE;
       // justificacionVO = new JustificacionVO();
    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(final Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the listaMensajes
     */
    public List<ReglaMensaje> getListaMensajes() {
        return listaMensajes;
    }

    /**
     * @param listaMensajes the listaMensajes to set
     */
    public void setListaMensajes(final List<ReglaMensaje> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    /**
     * @return the reglaMensaje
     */
    public ReglaMensaje getReglaMensaje() {
        return reglaMensaje;
    }

    /**
     * @param reglaMensaje the reglaMensaje to set
     */
    public void setReglaMensaje(final ReglaMensaje reglaMensaje) {
        this.reglaMensaje = reglaMensaje;
    }

    /**
//     * @return the verPopUpJustificacion
//     */
//    public Boolean getVerPopUpJustificacion() {
//        return verPopUpJustificacion;
//    }
//
//    /**
//     * @param verPopUpJustificacion the verPopUpJustificacion to set
//     */
//    public void setVerPopUpJustificacion(final Boolean verPopUpJustificacion) {
//        this.verPopUpJustificacion = verPopUpJustificacion;
//    }

//    /**
//     * @return the numeroDocumento
//     */
//    public String getNumeroDocumento() {
//        return numeroDocumento;
//    }
//
//    /**
//     * @param numeroDocumento the numeroDocumento to set
//     */
//    public void setNumeroDocumento(final String numeroDocumento) {
//        this.numeroDocumento = numeroDocumento;
//    }
//
//    /**
//     * @return the fechaDocumento
//     */
//    public Date getFechaDocumento() {
//        return fechaDocumento;
//    }
//
//    /**
//     * @param fechaDocumento the fechaDocumento to set
//     */
//    public void setFechaDocumento(final Date fechaDocumento) {
//        this.fechaDocumento = fechaDocumento;
//    }
//
//    /**
//     * @return the observacion
//     */
//    public String getObservacion() {
//        return observacion;
//    }
//
//    /**
//     * @param observacion the observacion to set
//     */
//    public void setObservacion(final String observacion) {
//        this.observacion = observacion;
//    }
//
//    /**
//     * @return the documento
//     */
//    public Archivo getDocumento() {
//        return documento;
//    }
//
//    /**
//     * @param documento the documento to set
//     */
//    public void setDocumento(final Archivo documento) {
//        this.documento = documento;
//    }

//    /**
//     * @return the archivoCargado
//     */
//    public UploadedFile getArchivoCargado() {
//        return archivoCargado;
//    }
//
//    /**
//     * @param archivoCargado the archivoCargado to set
//     */
//    public void setArchivoCargado(final UploadedFile archivoCargado) {
//        this.archivoCargado = archivoCargado;
//    }
//
//    /**
//     * @return the esNuevo
//     */
//    public Boolean getEsNuevo() {
//        return esNuevo;
//    }
//
//    /**
//     * @param esNuevo the esNuevo to set
//     */
//    public void setEsNuevo(final Boolean esNuevo) {
//        this.esNuevo = esNuevo;
//    }
//
//    /**
//     * @return the justificacionVO
//     */
//    public JustificacionVO getJustificacionVO() {
//        return justificacionVO;
//    }
//
//    /**
//     * @param justificacionVO the justificacionVO to set
//     */
//    public void setJustificacionVO(final JustificacionVO justificacionVO) {
//        this.justificacionVO = justificacionVO;
//    }
//
//    /**
//     * @return the archivoRequerido
//     */
//    public Boolean getArchivoRequerido() {
//        return archivoRequerido;
//    }
//
//    /**
//     * @param archivoRequerido the archivoRequerido to set
//     */
//    public void setArchivoRequerido(final Boolean archivoRequerido) {
//        this.archivoRequerido = archivoRequerido;
//    }
}
