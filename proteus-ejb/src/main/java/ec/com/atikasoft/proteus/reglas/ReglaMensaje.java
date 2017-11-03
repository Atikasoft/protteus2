/*
 *  ReglaMensaje.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Nov 14, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.reglas;

import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;

/**
 * Representa los mensajes emitidos por reglas inclumplidas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class ReglaMensaje {

    /**
     * Descripcion del mensaje.
     */
    private String mensaje;

    /**
     * Indica si el mensaje debe ser obligatorio o no.
     */
    private Boolean obligatorio;

    /**
     * Regla
     */
    private Regla regla;

    /**
     * Movimiento.
     */
    private Movimiento movimiento;

    /**
     * reglas de tipo de movimiento.
     */
    private TipoMovimientoRegla tipoMovimientoRegla;

    /**
     * Partida individual.
     */
    private String partidaIndividual;

    /**
     * Partida general.
     */
    private String partidaGeneral;

    /**
     * Constructor sin argumentos.
     */
    public ReglaMensaje() {
        super();
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return the obligatorio
     */
    public Boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(final Boolean obligatorio) {
        this.obligatorio = obligatorio;
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
     * @return the partidaIndividual
     */
    public String getPartidaIndividual() {
        return partidaIndividual;
    }

    /**
     * @return the partidaGeneral
     */
    public String getPartidaGeneral() {
        return partidaGeneral;
    }

    /**
     * @param partidaIndividual the partidaIndividual to set
     */
    public void setPartidaIndividual(String partidaIndividual) {
        this.partidaIndividual = partidaIndividual;
    }

    /**
     * @param partidaGeneral the partidaGeneral to set
     */
    public void setPartidaGeneral(String partidaGeneral) {
        this.partidaGeneral = partidaGeneral;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @return the tipoMovimientoRegla
     */
    public TipoMovimientoRegla getTipoMovimientoRegla() {
        return tipoMovimientoRegla;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @param tipoMovimientoRegla the tipoMovimientoRegla to set
     */
    public void setTipoMovimientoRegla(final TipoMovimientoRegla tipoMovimientoRegla) {
        this.tipoMovimientoRegla = tipoMovimientoRegla;
    }
}
