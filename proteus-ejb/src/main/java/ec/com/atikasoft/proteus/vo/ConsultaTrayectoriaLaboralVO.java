/*
 *  ConsultaAnticipoVO.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VO para la Busqueda de Anticipos.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>c>
 */
public class ConsultaTrayectoriaLaboralVO implements Serializable {

    /**
     * Variable para el Tipo de Documento del servidor.
     */
    private String tipoDocumentoServidor;

    /**
     * Variable para el Número de Documento.
     */
    private String numeroDocumentoServidor;
    /**
     * Variable para el Nombre del Servidor.
     */
    private String apellidosNombresServidor;

    /**
     * Variables para ejecutar busqueda por fecha .
     */
    private Date fechaDesde;
    private Date fechaHasta;

    /**
     * Constructor por defecto.
     */
    public ConsultaTrayectoriaLaboralVO() {
        super();
    }

    /**
     * @return the tipoDocumentoServidor
     */
    public String getTipoDocumentoServidor() {
        return tipoDocumentoServidor;
    }

    /**
     * @param tipoDocumentoServidor the tipoDocumentoServidor to set
     */
    public void setTipoDocumentoServidor(String tipoDocumentoServidor) {
        this.tipoDocumentoServidor = tipoDocumentoServidor;
    }

    /**
     * @return the numeroDocumentoServidor
     */
    public String getNumeroDocumentoServidor() {
        return numeroDocumentoServidor;
    }

    /**
     * @param numeroDocumentoServidor the numeroDocumentoServidor to set
     */
    public void setNumeroDocumentoServidor(String numeroDocumentoServidor) {
        this.numeroDocumentoServidor = numeroDocumentoServidor;
    }

    /**
     * @return the apellidosNombresServidor
     */
    public String getApellidosNombresServidor() {
        return apellidosNombresServidor;
    }

    /**
     * @param apellidosNombresServidor the apellidosNombresServidor to set
     */
    public void setApellidosNombresServidor(String apellidosNombresServidor) {
        this.apellidosNombresServidor = apellidosNombresServidor;
    }

    /**
     * @return the fechaDesde
     */
    public Date getFechaDesde() {
        return fechaDesde;
    }

    /**
     * @param fechaDesde the fechaDesde to set
     */
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

}
