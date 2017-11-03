/*
 *  TareaVO.java
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
 *  09/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class TareaVO {

    /**
     * Objeto tarea del gestor.
     */
    private Tarea tarea;

    /**
     * Tipo de movimiento para la tarea.
     */
    private TipoMovimiento tipoMovimiento;
    
    /**
     * 
     */
    private Tramite tramite;

    /**
     * @return the tarea
     */
    public Tarea getTarea() {
        return tarea;
    }

    /**
     * @param tarea the tarea to set
     */
    public void setTarea(final Tarea tarea) {
        this.tarea = tarea;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
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
    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
}
