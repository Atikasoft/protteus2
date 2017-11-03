/*
 *  TramiteValidacionHelper.java
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
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.AtencionHelper;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *     Helper de Tramite Validacion.
 *
 *     @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteValidacionHelper")
@SessionScoped
public class TramiteValidacionHelper extends AtencionHelper {

    /**
     *     Constructor por defecto.
     */
    public TramiteValidacionHelper() {
        super();
    }
}
