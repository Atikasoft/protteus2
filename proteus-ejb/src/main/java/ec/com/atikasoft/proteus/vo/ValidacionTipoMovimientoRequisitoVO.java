/*
 *  ValidacionTipoMovimientoRequisitoVO.java
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
 *  19/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Validacion;
import java.io.Serializable;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public class ValidacionTipoMovimientoRequisitoVO implements Serializable {

    /**
     * Tipo Movimiento Requisito.
     */
    private TipoMovimientoRequisito tipoMovimientoRequisito;

    /**
     * Validacion.
     */
    private Validacion validacion;

    /**
     * Archivo.
     */
    private Archivo archivo;

    /**
     * Bandera para saber si el campo Cumple cambia de valor.
     */
    private Boolean cumpleCambioValor;

    /**
     * Constructor por defecto.
     */
    public ValidacionTipoMovimientoRequisitoVO() {
        super();
    }

    /**
     * @return the tipoMovimientoRequisito
     */
    public TipoMovimientoRequisito getTipoMovimientoRequisito() {
        return tipoMovimientoRequisito;
    }

    /**
     * @param tipoMovimientoRequisito the tipoMovimientoRequisito to set
     */
    public void setTipoMovimientoRequisito(final TipoMovimientoRequisito tipoMovimientoRequisito) {
        this.tipoMovimientoRequisito = tipoMovimientoRequisito;
    }

    /**
     * @return the validacion
     */
    public Validacion getValidacion() {
        return validacion;
    }

    /**
     * @param validacion the validacion to set
     */
    public void setValidacion(final Validacion validacion) {
        this.validacion = validacion;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final Archivo archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the cumpleCambioValor
     */
    public Boolean getCumpleCambioValor() {
        return cumpleCambioValor;
    }

    /**
     * @param cumpleCambioValor the cumpleCambioValor to set
     */
    public void setCumpleCambioValor(final Boolean cumpleCambioValor) {
        this.cumpleCambioValor = cumpleCambioValor;
    }
}
