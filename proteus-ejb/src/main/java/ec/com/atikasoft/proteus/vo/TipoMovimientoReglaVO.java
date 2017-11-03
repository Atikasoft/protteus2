/*
 *  TipoMovimientoReglaVO.java
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

import ec.com.atikasoft.proteus.modelo.Regla;
import java.io.Serializable;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public class TipoMovimientoReglaVO implements Serializable {

    /**
     * Bandera de seleccione.
     */
    private Boolean seleccionar;

    /**
     * Entidad de regla.
     */
    private Regla regla;

    /**
     * Bandera de obligatorio.
     */
    private Boolean obligatorio;

    /**
     * Bandera para saber si la regla es justificable.
     */
    private Boolean justificable;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoReglaVO() {
        super();
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
     * @return the regla
     */
    public Regla getRegla() {
        return regla;
    }

    /**
     * @param regla the regla to set
     */
    public void setRegla(final Regla regla) {
        this.regla = regla;
    }

    /**
     * @return the obligatorio
     */
    public Boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(final Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the justificable
     */
    public Boolean getJustificable() {
        return justificable;
    }

    /**
     * @param justificable the justificable to set
     */
    public void setJustificable(final Boolean justificable) {
        this.justificable = justificable;
    }
}
