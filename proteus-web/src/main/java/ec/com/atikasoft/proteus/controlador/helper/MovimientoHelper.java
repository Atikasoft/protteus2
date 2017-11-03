/*
 *  MovimientoHelper.java
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
 *  07/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "movimientoHelper")
@SessionScoped
public class MovimientoHelper extends CatalogoHelper {

    /**
     * obj Movimiento.
     */
    private Movimiento movimiento;

    /**
     * Lista para los movimientos.
     */
    private List<Movimiento> listaMovimientos;

    /**
     * Opciondes de tipo movimiento.
     */
    private List<SelectItem> listaTipoMovimiento;

    /**
     * fecha rige desde.
     */
    private Date fechaVigenteDesde;

    /**
     * fecha rige hasta.
     */
    private Date fechaVigenteHasta;

    /**
     * fecha rige desde.
     */
    private Date fechaEstadoDesde;

    /**
     * fecha rige hasta.
     */
    private Date fechaEstadoHasta;

    /**
     * variable id del tipo movimiento.
     */
    private Long tipoMovimientoId;

    /**
     * NUmero de registros que presenta la lista.
     */
    private int numeroTramites;

    /**
     * lista de tramites.
     */
    private List<SelectItem> listaFase;

    /**
     * tramite ID.
     */
    private Long idTramite;

    /**
     * codigo fase.
     */
    private String codigoFase;

    /**
     * Constructor de la clase.
     */
    public MovimientoHelper() {
        super();
        iniciador();
    }

    /**
     * iniciar las baribles de la clase.
     */
    public final void iniciador() {
        movimiento = new Movimiento();
        listaMovimientos = new ArrayList<Movimiento>();
        listaTipoMovimiento = new ArrayList<SelectItem>();
        tipoMovimientoId = new Long(0);
        listaFase = new ArrayList<SelectItem>();
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the listaMovimientos
     */
    public List<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    /**
     * @param listaMovimientos the listaMovimientos to set
     */
    public void setListaMovimientos(final List<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    /**
     * @return the fechaVigenteDesde
     */
    public Date getFechaVigenteDesde() {
        return fechaVigenteDesde;
    }

    /**
     * @param fechaVigenteDesde the fechaVigenteDesde to set
     */
    public void setFechaVigenteDesde(final Date fechaVigenteDesde) {
        this.fechaVigenteDesde = fechaVigenteDesde;
    }

    /**
     * @return the fechaVigenteHasta
     */
    public Date getFechaVigenteHasta() {
        return fechaVigenteHasta;
    }

    /**
     * @param fechaVigenteHasta the fechaVigenteHasta to set
     */
    public void setFechaVigenteHasta(final Date fechaVigenteHasta) {
        this.fechaVigenteHasta = fechaVigenteHasta;
    }

    /**
     * @return the listaTipoMovimiento
     */
    public List<SelectItem> getListaTipoMovimiento() {
        return listaTipoMovimiento;
    }

    /**
     * @param listaTipoMovimiento the listaTipoMovimiento to set
     */
    public void setListaTipoMovimiento(final List<SelectItem> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    /**
     * @return the tipoMovimientoId
     */
    public Long getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    /**
     * @param tipoMovimientoId the tipoMovimientoId to set
     */
    public void setTipoMovimientoId(final Long tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    /**
     * @return the numeroTramites
     */
    public int getNumeroTramites() {
        return numeroTramites;
    }

    /**
     * @param numeroTramites the numeroTramites to set
     */
    public void setNumeroTramites(final int numeroTramites) {
        this.numeroTramites = numeroTramites;
    }

    /**
     * @return the idTramite
     */
    public Long getIdTramite() {
        return idTramite;
    }

    /**
     * @param idTramite the idTramite to set
     */
    public void setIdTramite(final Long idTramite) {
        this.idTramite = idTramite;
    }

    /**
     * @return the listaFase
     */
    public List<SelectItem> getListaFase() {
        return listaFase;
    }

    /**
     * @param listaFase the listaFase to set
     */
    public void setListaFase(final List<SelectItem> listaFase) {
        this.listaFase = listaFase;
    }

    /**
     * @return the codigoFase
     */
    public String getCodigoFase() {
        return codigoFase;
    }

    /**
     * @param codigoFase the codigoFase to set
     */
    public void setCodigoFase(final String codigoFase) {
        this.codigoFase = codigoFase;
    }

    /**
     * @return the fechaEstadoDesde
     */
    public Date getFechaEstadoDesde() {
        return fechaEstadoDesde;
    }

    /**
     * @param fechaEstadoDesde the fechaEstadoDesde to set
     */
    public void setFechaEstadoDesde(Date fechaEstadoDesde) {
        this.fechaEstadoDesde = fechaEstadoDesde;
    }

    /**
     * @return the fechaEstadoHasta
     */
    public Date getFechaEstadoHasta() {
        return fechaEstadoHasta;
    }

    /**
     * @param fechaEstadoHasta the fechaEstadoHasta to set
     */
    public void setFechaEstadoHasta(Date fechaEstadoHasta) {
        this.fechaEstadoHasta = fechaEstadoHasta;
    }
}
