/*
 *  TipoMovimientoAlertaVO.java
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
 *  15/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Alerta;
import java.io.Serializable;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public class TipoMovimientoAlertaVO implements Serializable {

    /**
     * Entidad de tipo alerta.
     */
    private Alerta alerta;

    /**
     * Variable booleana para seleccionar.
     */
    private Boolean seleccionar;

    /**
     * Mensaje a guardar.
     */
    private String mensaje;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoAlertaVO() {
        super();
    }

    /**
     * @return the alerta
     */
    public Alerta getAlerta() {
        return alerta;
    }

    /**
     * @param alerta the alerta to set
     */
    public void setAlerta(final Alerta alerta) {
        this.alerta = alerta;
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
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
}
