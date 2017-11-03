/*
 *  BusquedaImpuestoRentaVO.java
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

import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.vistas.FormularioIR;

/**
 *VO para la Busqueda de Impuesto a la Renta.
 * @author atikasoft
 */
public class BusquedaImpuestoRentaVO {
    /**
     *campo para guardar datos del servidor.
     */
    private Servidor servidor;
    /**
     * campo para guardar los datos del Formulario del impuesto a la renta.
     */
    private FormularioIR formularioIR;
    /**
     * consturctor.
     */
    public BusquedaImpuestoRentaVO() {
        super();
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
     * @return the formularioIR
     */
    public FormularioIR getFormularioIR() {
        return formularioIR;
    }

    /**
     * @param formularioIR the formularioIR to set
     */
    public void setFormularioIR(FormularioIR formularioIR) {
        this.formularioIR = formularioIR;
    }
    
    
    
}
