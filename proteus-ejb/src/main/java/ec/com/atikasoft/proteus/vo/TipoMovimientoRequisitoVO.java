/*
 *  TipoMovimientoRequisitoVO.java
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
 *  13/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Requisito;
import java.io.Serializable;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public class TipoMovimientoRequisitoVO implements Serializable {

    /**
     * Tipo de movimeinto requisito.
     */
    private Requisito requisito;

    /**
     * Seleccionar.
     */
    private Boolean seleccionar;

    /**
     * Obligatorio.
     */
    private Boolean obligatorio;

    /**
     * Presenta Servidor Publico.
     */
    private Boolean presentaServidorPublico;

    /**
     * Subir Archivo Obligatorio.
     */
    private Boolean subirArchivoObligatorio;

    /**
     * Identificador del tipo de movimiento x requisito.
     */
    private Long tipoMovimientoRequisitoId;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoRequisitoVO() {
        super();
    }

    /**
     * @return the requisito
     */
    public Requisito getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(final Requisito requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the seleccionar
     */
    public Boolean getSeleccionar() {
        return seleccionar;
    }

    /**
     * @param seleccionar the seleccionar to set
     */
    public void setSeleccionar(final Boolean seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     * @return the obligatorio
     */
    public Boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(final Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the presentaServidorPublico
     */
    public Boolean getPresentaServidorPublico() {
        return presentaServidorPublico;
    }

    /**
     * @param presentaServidorPublico the presentaServidorPublico to set
     */
    public void setPresentaServidorPublico(final Boolean presentaServidorPublico) {
        this.presentaServidorPublico = presentaServidorPublico;
    }

    /**
     * @return the subirArchivoObligatorio
     */
    public Boolean getSubirArchivoObligatorio() {
        return subirArchivoObligatorio;
    }

    /**
     * @param subirArchivoObligatorio the subirArchivoObligatorio to set
     */
    public void setSubirArchivoObligatorio(final Boolean subirArchivoObligatorio) {
        this.subirArchivoObligatorio = subirArchivoObligatorio;
    }

    /**
     * @return the tipoMovimientoRequisitoId
     */
    public Long getTipoMovimientoRequisitoId() {
        return tipoMovimientoRequisitoId;
    }

    /**
     * @param tipoMovimientoRequisitoId the tipoMovimientoRequisitoId to set
     */
    public void setTipoMovimientoRequisitoId(final Long tipoMovimientoRequisitoId) {
        this.tipoMovimientoRequisitoId = tipoMovimientoRequisitoId;
    }
}