/*
 *  TipoMovimientoDelegadoVO.java
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
 *  21/02/2013
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDelegado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
public class TipoMovimientoDelegadoVO implements Serializable {

    /**
     * Variable Tipo de Movimiento.
     */
    private TipoMovimiento tipoMovimiento;
    /**
     * Variable lista de delegados.
     */
    private List<TipoMovimientoDelegado> listaTipoMovimientoDelegado = new ArrayList<TipoMovimientoDelegado>();

    /**
     * constructor.
     */
    public TipoMovimientoDelegadoVO() {
        super();
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
     * @return the listaTipoMovimientoDelegado
     */
    public List<TipoMovimientoDelegado> getListaTipoMovimientoDelegado() {
        return listaTipoMovimientoDelegado;
    }

    /**
     * @param listaTipoMovimientoDelegado the listaTipoMovimientoDelegado to set
     */
    public void setListaTipoMovimientoDelegado(final List<TipoMovimientoDelegado> listaTipoMovimientoDelegado) {
        this.listaTipoMovimientoDelegado = listaTipoMovimientoDelegado;
    }
}
