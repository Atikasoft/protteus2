/*
 *  JustificacionVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Justificacion;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import java.io.Serializable;

/**
 * VO para la Busqueda de Puesto.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public class JustificacionVO implements Serializable {

    /**
     * Entidad de Regla mensaje.
     */
    private ReglaMensaje reglaMensaje;

    /**
     * Entodad de Justificacion.
     */
    private Justificacion justificacion;

    /**
     * Constructor por defecto.
     */
    public JustificacionVO() {
        super();
        reglaMensaje = new ReglaMensaje();
        justificacion = new Justificacion();
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
     * @return the justificacion
     */
    public Justificacion getJustificacion() {
        return justificacion;
    }

    /**
     * @param justificacion the justificacion to set
     */
    public void setJustificacion(final Justificacion justificacion) {
        this.justificacion = justificacion;
    }
}
