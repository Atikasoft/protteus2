/*
 *  TipoAnticipoHelper.java
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
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author LRodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "tipoAnticipoHelper")
@SessionScoped
public class TipoAnticipoHelper extends CatalogoHelper {

    /**
     * Variable para nueva tipoAnticipo.
     */
    private TipoAnticipo tipoAnticipo;
    /**
     * Variable para modificar/eliminar una tipoAnticipo.
     */
    private TipoAnticipo tipoAnticipoEditDelete;
    /**
     * Variable para listar las tipoAnticipos.
     */
    private List<TipoAnticipo> listaTipoAnticipo;
    /**
     * Variable para listar las tipoAnticipos por codigo.
     */
    private List<TipoAnticipo> listaTipoAnticipoCodigo;
    /**
     * Lista de tipos de anticipos.
     */
    private List<SelectItem> listaOpcionesTipoAnticipo;
    /**
     * Lista de tipos de cuotas.
     */
    private List<SelectItem> listaOpcionesTipoCuota;
    /**
     * Lista de tipos de garantias.
     */
    private List<SelectItem> listaOpcionesTipoGarantia;
    /**
     * Lista de rubros de ingresos.
     */
    private List<SelectItem> listaOpcionesRubro;
    /**
     * Lista de regimenes laborales.
     */
    private List<SelectItem> listaOpcionesRegimen;
    /**
     * Lista de dias desde-hasta.
     */
    private List<SelectItem> listaOpcionesDias;
    /**
     * Lista de meses desde-hasta.
     */
    private List<SelectItem> listaOpcionesMeses;

    /**
     * Constructor.
     */
    public TipoAnticipoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la TipoAnticipoHelper.
     */
    public final void iniciador() {
        setTipoAnticipo(new TipoAnticipo());
        setTipoAnticipoEditDelete(new TipoAnticipo());
        setListaTipoAnticipo(new ArrayList<TipoAnticipo>());
        setListaTipoAnticipoCodigo(new ArrayList<TipoAnticipo>());
        setListaOpcionesTipoAnticipo(new ArrayList<SelectItem>());
        setListaOpcionesTipoCuota(new ArrayList<SelectItem>());
        setListaOpcionesTipoGarantia(new ArrayList<SelectItem>());
        setListaOpcionesRubro(new ArrayList<SelectItem>());
        setListaOpcionesRegimen(new ArrayList<SelectItem>());
        setListaOpcionesDias(new ArrayList<SelectItem>());
        setListaOpcionesMeses(new ArrayList<SelectItem>());
    }

    /**
     * @return the tipoAnticipo
     */
    public TipoAnticipo getTipoAnticipo() {
        return tipoAnticipo;
    }

    /**
     * @param tipoAnticipo the tipoAnticipo to set
     */
    public void setTipoAnticipo(final TipoAnticipo tipoAnticipo) {
        this.tipoAnticipo = tipoAnticipo;
    }

    /**
     * @return the tipoAnticipoEditDelete
     */
    public TipoAnticipo getTipoAnticipoEditDelete() {
        return tipoAnticipoEditDelete;
    }

    /**
     * @param tipoAnticipoEditDelete the tipoAnticipoEditDelete to set
     */
    public void setTipoAnticipoEditDelete(final TipoAnticipo tipoAnticipoEditDelete) {
        this.tipoAnticipoEditDelete = tipoAnticipoEditDelete;
    }

    /**
     * @return the listaTipoAnticipo
     */
    public List<TipoAnticipo> getListaTipoAnticipo() {
        return listaTipoAnticipo;
    }

    /**
     * @param listaTipoAnticipo the listaTipoAnticipo to set
     */
    public void setListaTipoAnticipo(final List<TipoAnticipo> listaTipoAnticipo) {
        this.listaTipoAnticipo = listaTipoAnticipo;
    }

    /**
     * @return the listaTipoAnticipoNemonico
     */
    public List<TipoAnticipo> getListaTipoAnticipoCodigo() {
        return listaTipoAnticipoCodigo;
    }

    /**
     * @param listaTipoAnticipoCodigo the listaTipoAnticipoCodigo to set
     */
    public void setListaTipoAnticipoCodigo(List<TipoAnticipo> listaTipoAnticipoCodigo) {
        this.listaTipoAnticipoCodigo = listaTipoAnticipoCodigo;
    }

    /**
     * @return the listaOpcionesTipoAnticipo
     */
    public List<SelectItem> getListaOpcionesTipoAnticipo() {
        return listaOpcionesTipoAnticipo;
    }

    /**
     * @param listaOpcionesTipoAnticipo the listaOpcionesTipoAnticipo to set
     */
    public void setListaOpcionesTipoAnticipo(List<SelectItem> listaOpcionesTipoAnticipo) {
        this.listaOpcionesTipoAnticipo = listaOpcionesTipoAnticipo;
    }

    /**
     * @return the listaOpcionesTipoCuota
     */
    public List<SelectItem> getListaOpcionesTipoCuota() {
        return listaOpcionesTipoCuota;
    }

    /**
     * @param listaOpcionesTipoCuota the listaOpcionesTipoCuota to set
     */
    public void setListaOpcionesTipoCuota(List<SelectItem> listaOpcionesTipoCuota) {
        this.listaOpcionesTipoCuota = listaOpcionesTipoCuota;
    }

    /**
     * @return the listaOpcionesTipoGarantia
     */
    public List<SelectItem> getListaOpcionesTipoGarantia() {
        return listaOpcionesTipoGarantia;
    }

    /**
     * @param listaOpcionesTipoGarantia the listaOpcionesTipoGarantia to set
     */
    public void setListaOpcionesTipoGarantia(List<SelectItem> listaOpcionesTipoGarantia) {
        this.listaOpcionesTipoGarantia = listaOpcionesTipoGarantia;
    }

    /**
     * @return the listaOpcionesRubro
     */
    public List<SelectItem> getListaOpcionesRubro() {
        return listaOpcionesRubro;
    }

    /**
     * @param listaOpcionesRubro the listaOpcionesRubro to set
     */
    public void setListaOpcionesRubro(List<SelectItem> listaOpcionesRubro) {
        this.listaOpcionesRubro = listaOpcionesRubro;
    }

    /**
     * @return the listaOpcionesRegimen
     */
    public List<SelectItem> getListaOpcionesRegimen() {
        return listaOpcionesRegimen;
    }

    /**
     * @param listaOpcionesRegimen the listaOpcionesRegimen to set
     */
    public void setListaOpcionesRegimen(List<SelectItem> listaOpcionesRegimen) {
        this.listaOpcionesRegimen = listaOpcionesRegimen;
    }

    /**
     * @return the listaOpcionesDias
     */
    public List<SelectItem> getListaOpcionesDias() {
        return listaOpcionesDias;
    }

    /**
     * @param listaOpcionesDias the listaOpcionesDias to set
     */
    public void setListaOpcionesDias(List<SelectItem> listaOpcionesDias) {
        this.listaOpcionesDias = listaOpcionesDias;
    }

    /**
     * @return the listaOpcionesMeses
     */
    public List<SelectItem> getListaOpcionesMeses() {
        return listaOpcionesMeses;
    }

    /**
     * @param listaOpcionesMeses the listaOpcionesMeses to set
     */
    public void setListaOpcionesMeses(List<SelectItem> listaOpcionesMeses) {
        this.listaOpcionesMeses = listaOpcionesMeses;
    }
}
