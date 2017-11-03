/*
 *  ResultadoAutentificarVO.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Dec 10, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.consumer.mdmq.vo;

import java.io.Serializable;

/**
 * Contiene el resultado de autentificar.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ResultadoAutentificarVO implements Serializable {

    /**
     * Identificador unico de la sesion.
     */
    private String identificadorSesion;

    /**
     * Mensaje que retorna.
     */
    private String mensaje;

    /**
     * Codigo del resultado.
     */
    private Integer resultado;

    /**
     *
     */
    private String numeroIdentificacion;

    /**
     *
     */
    private Long numeroPersona;

    /**
     * Constructor sin argumentos.
     */
    public ResultadoAutentificarVO() {
        super();

    }

    /**
     * @return the identificadorSesion
     */
    public String getIdentificadorSesion() {
        return identificadorSesion;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return the resultado
     */
    public Integer getResultado() {
        return resultado;
    }

    /**
     * @param identificadorSesion the identificadorSesion to set
     */
    public void setIdentificadorSesion(final String identificadorSesion) {
        this.identificadorSesion = identificadorSesion;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(final Integer resultado) {
        this.resultado = resultado;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @return the numeroPersona
     */
    public Long getNumeroPersona() {
        return numeroPersona;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @param numeroPersona the numeroPersona to set
     */
    public void setNumeroPersona(final Long numeroPersona) {
        this.numeroPersona = numeroPersona;
    }
}
