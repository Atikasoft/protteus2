/*
 *  TramiteFormularioVO.java
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
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Formulario;
import ec.com.atikasoft.proteus.modelo.Tramite;
import java.io.Serializable;

/**
 * VO de Tramite Formulario.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class TramiteFormularioVO implements Serializable {

    /**
     * Formulario del tramite.
     */
    private Formulario formulario;

    /**
     * Tramite.
     */
    private Tramite tramite;

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(final Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }
}
