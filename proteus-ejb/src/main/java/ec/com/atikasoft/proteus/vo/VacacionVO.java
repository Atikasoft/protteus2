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

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * VO para gestión de timmer de vacaciones
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class VacacionVO implements Serializable {

    /**
     * Registro de la vacacion.
     */
    private Vacacion vacacion ;
    private List<Vacacion> listaVacaciones;
    private long saldoMinutos;
    private VacacionDetalle vacacionDetalle ;
    private List<VacacionDetalle> listaVacacionesVacacionDetalles;  
    private DistributivoDetalle distributivoDetalle;
    /**
     * Constructor por defecto.
     */
    public VacacionVO() {
        super();
        vacacion = new Vacacion();
        listaVacaciones = new ArrayList<Vacacion>();
        listaVacaciones = new ArrayList<Vacacion>();
        vacacionDetalle = new VacacionDetalle();
        distributivoDetalle = new DistributivoDetalle();
    }

    /**
     * @return the vacacion
     */
    public Vacacion getVacacion() {
        return vacacion;
    }

    /**
     * @param vacacion the vacacion to set
     */
    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }

    /**
     * @return the listaVacaciones
     */
    public List<Vacacion> getListaVacaciones() {
        return listaVacaciones;
    }

    /**
     * @param listaVacaciones the listaVacaciones to set
     */
    public void setListaVacaciones(List<Vacacion> listaVacaciones) {
        this.listaVacaciones = listaVacaciones;
    }

    /**
     * @return the saldoMinutos
     */
    public long getSaldoMinutos() {
        return saldoMinutos;
    }

    /**
     * @param saldoMinutos the saldoMinutos to set
     */
    public void setSaldoMinutos(long saldoMinutos) {
        this.saldoMinutos = saldoMinutos;
    }

    /**
     * @return the vacacionDetalle
     */
    public VacacionDetalle getVacacionDetalle() {
        return vacacionDetalle;
    }

    /**
     * @param vacacionDetalle the vacacionDetalle to set
     */
    public void setVacacionDetalle(VacacionDetalle vacacionDetalle) {
        this.vacacionDetalle = vacacionDetalle;
    }

    /**
     * @return the listaVacacionesVacacionDetalles
     */
    public List<VacacionDetalle> getListaVacacionesVacacionDetalles() {
        return listaVacacionesVacacionDetalles;
    }

    /**
     * @param listaVacacionesVacacionDetalles the listaVacacionesVacacionDetalles to set
     */
    public void setListaVacacionesVacacionDetalles(List<VacacionDetalle> listaVacacionesVacacionDetalles) {
        this.listaVacacionesVacacionDetalles = listaVacacionesVacacionDetalles;
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

}
