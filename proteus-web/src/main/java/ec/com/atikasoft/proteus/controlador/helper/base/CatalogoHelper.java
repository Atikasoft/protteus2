/*
 *  CatalogoHelper.java
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
 *  23/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper.base;

import java.io.Serializable;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class CatalogoHelper implements Serializable {

    private String campoBusqueda;

    private Boolean esNuevo;

    /**
     * @return the campoBusqueda
     */
    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    /**
     * @param campoBusqueda the campoBusqueda to set
     */
    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    /**
     * @return the esNuevo
     */
    public Boolean getEsNuevo() {
        return esNuevo;
    }

    /**
     * @param esNuevo the esNuevo to set
     */
    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
}
