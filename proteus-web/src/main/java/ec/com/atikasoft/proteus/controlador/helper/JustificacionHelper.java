/*
 *  JustificacionHelper.java
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
 *  07/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.vo.JustificacionVO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "justificacionHelper")
@SessionScoped
public class JustificacionHelper extends CatalogoHelper {

    /**
     * Archivo cargado.
     */
    private UploadedFile archivoCargado;

    /**
     * wrapper de justificacion.
     */
    private JustificacionVO justificacionVO;

    /**
     * Bandera para saber si ya se cargo archivo o no y setear la bander como requerida o no.
     */
    private Boolean archivoRequerido;

    /**
     * Constructor por defecto.
     */
    public JustificacionHelper() {
        super();
        archivoRequerido = Boolean.TRUE;
        justificacionVO = new JustificacionVO();
    }

    /**
     * @return the archivoCargado
     */
    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    /**
     * @param archivoCargado the archivoCargado to set
     */
    public void setArchivoCargado(final UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    /**
     * @return the justificacionVO
     */
    public JustificacionVO getJustificacionVO() {
        return justificacionVO;
    }

    /**
     * @param justificacionVO the justificacionVO to set
     */
    public void setJustificacionVO(final JustificacionVO justificacionVO) {
        this.justificacionVO = justificacionVO;
    }

    /**
     * @return the archivoRequerido
     */
    public Boolean getArchivoRequerido() {
        return archivoRequerido;
    }

    /**
     * @param archivoRequerido the archivoRequerido to set
     */
    public void setArchivoRequerido(final Boolean archivoRequerido) {
        this.archivoRequerido = archivoRequerido;
    }
}
