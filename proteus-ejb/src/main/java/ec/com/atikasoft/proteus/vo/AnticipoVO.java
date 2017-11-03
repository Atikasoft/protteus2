/*
 *  AnticipoVO.java
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
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.AnticipoPago;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * VO para gestión de los anticipos
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class AnticipoVO implements Serializable {

    /**
     * Registro de la solicitud del anticipo.
     */
    private Anticipo anticipo;
    /**
     * Registro de anticipoPlanPago.
     */
    private AnticipoPlanPago anticipoPlanPago;
    /**
     * Lista del plan de pago del anticipo (cuotas - tabla de amortizacion).
     */
    private List<AnticipoPlanPago> listaAnticipoPlanPago;
    /**
     * Pago del anticipo.
     */   
    private AnticipoPago anticipoPago;
    private List<AnticipoPago> listaAnticipoPago;
    /**
     * Distribución de puestos del servidor.
     */
    private DistributivoDetalle distributivoDetalle;
     /**
     * Periodo Inicio.
     */
    private Integer mesInicio;
    private Integer anioInicio;
    
    /**
     * Variable para confirmar la aceptación del anticipo.
     */
    private Boolean acepta;
    /**
     * Constructor por defecto.
     */
    public AnticipoVO() {
        super();
        anticipo = new Anticipo();
        listaAnticipoPlanPago= new ArrayList<AnticipoPlanPago>();
        listaAnticipoPago=new ArrayList<AnticipoPago>();
        Calendar c = Calendar.getInstance();
        anioInicio = c.get(Calendar.YEAR);
        mesInicio = c.get(Calendar.MONTH);
        acepta = Boolean.FALSE;
    }

    /**
     * @return the anticipo
     */
    public Anticipo getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(Anticipo anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the anticipoPlanPago
     */
    public AnticipoPlanPago getAnticipoPlanPago() {
        return anticipoPlanPago;
    }

    /**
     * @param anticipoPlanPago the anticipoPlanPago to set
     */
    public void setAnticipoPlanPago(AnticipoPlanPago anticipoPlanPago) {
        this.anticipoPlanPago = anticipoPlanPago;
    }

    /**
     * @return the listaAnticipoPlanPago
     */
    public List<AnticipoPlanPago> getListaAnticipoPlanPago() {
        return listaAnticipoPlanPago;
    }

    /**
     * @param listaAnticipoPlanPago the listaAnticipoPlanPago to set
     */
    public void setListaAnticipoPlanPago(List<AnticipoPlanPago> listaAnticipoPlanPago) {
        this.listaAnticipoPlanPago = listaAnticipoPlanPago;
    }

    /**
     * @return the anticipoPago
     */
    public AnticipoPago getAnticipoPago() {
        return anticipoPago;
    }

    /**
     * @param anticipoPago the anticipoPago to set
     */
    public void setAnticipoPago(AnticipoPago anticipoPago) {
        this.anticipoPago = anticipoPago;
    }

    /**
     * @return the listaAnticipoPago
     */
    public List<AnticipoPago> getListaAnticipoPago() {
        return listaAnticipoPago;
    }

    /**
     * @param listaAnticipoPago the listaAnticipoPago to set
     */
    public void setListaAnticipoPago(List<AnticipoPago> listaAnticipoPago) {
        this.listaAnticipoPago = listaAnticipoPago;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }
    /**
     * @return the mesInicio
     */
    public Integer getMesInicio() {
        return mesInicio;
    }

    /**
     * @param mesInicio the mesInicio to set
     */
    public void setMesInicio(Integer mesInicio) {
        this.mesInicio = mesInicio;
    }

    /**
     * @return the anioInicio
     */
    public Integer getAnioInicio() {
        return anioInicio;
    }

    /**
     * @param anioInicio the anioInicio to set
     */
    public void setAnioInicio(Integer anioInicio) {
        this.anioInicio = anioInicio;
    }

    /**
     * @return the acepta
     */
    public Boolean getAcepta() {
        return acepta;
    }

    /**
     * @param acepta the acepta to set
     */
    public void setAcepta(Boolean acepta) {
        this.acepta = acepta;
    }
}
