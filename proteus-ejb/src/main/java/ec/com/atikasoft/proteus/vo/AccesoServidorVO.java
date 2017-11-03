/*
 *  AccesoServidorVO.java
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
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Servidor;
import java.io.Serializable;

/**
 * VO para gestión de los anticipos
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class AccesoServidorVO implements Serializable {

    /**
     * Nombre del usuario logueado.
     */
    private String usuario;
    /**
     * Registro de servidor.
     */
    private Servidor servidor;
    

    /**
     * Variable para determinar si servidor tiene acceso.
     */
    private Boolean conAcceso;
    /**
     * Variable para determinar si servidor tiene acceso.
     */
    private Boolean conCambioClave;
       /**
     * Variable para determinar si servidor tiene acceso.
     */
    private String mensaje;
    /**
     * Constructor por defecto.
     */
    public AccesoServidorVO() {
        super();
        servidor = new Servidor();
        conAcceso = Boolean.FALSE;
        conCambioClave = Boolean.FALSE;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the conAcceso
     */
    public Boolean getConAcceso() {
        return conAcceso;
    }

    /**
     * @param conAcceso the conAcceso to set
     */
    public void setConAcceso(Boolean conAcceso) {
        this.conAcceso = conAcceso;
    }

    /**
     * @return the conCambioClave
     */
    public Boolean getConCambioClave() {
        return conCambioClave;
    }

    /**
     * @param conCambioClave the conCambioClave to set
     */
    public void setConCambioClave(Boolean conCambioClave) {
        this.conCambioClave = conCambioClave;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }



}
