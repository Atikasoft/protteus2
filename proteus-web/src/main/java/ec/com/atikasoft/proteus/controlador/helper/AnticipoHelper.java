/*
 *  AnticipoHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  05/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import ec.com.atikasoft.proteus.vo.AnticipoVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Anticipo
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "anticipoHelper")
@SessionScoped
public class AnticipoHelper extends CatalogoHelper {

    /**
     * clase anticipo.
     */
    private AnticipoVO anticipoVO;

    /**
     * clase anticipo puesto para editar.
     */
    private Anticipo anticipoEditDelete;

    /**
     * lista de anticipos.
     */
    private List<Anticipo> listaAnticipos;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Anticipo> listaAnticipoCodigo;
    /**
     * Variable para almacenar la fecha de ingreso del servidor a la institución.
     */
    private Date fechaIngreso;
    /**
     * Variable para crear opciones de selección.
     */
    private List<SelectItem> listaOpcionesMeses;
    private List<SelectItem> listaOpcionesTipoAnticipos;
    private List<TipoAnticipo> listaTipoAnticipos;
    
    /**
     * Variables para asignación del garante del anticipo.
     */
    private List<Servidor> listaServidores;
    
    /**
     * Variable para indicar que no existen configuraciones de regimen laboral.
     */
    private boolean hayConfiguracion;
    private boolean continuarAnticipo;
    private BigDecimal montoMaximo;
    private BigDecimal total;
    private BigDecimal porcentajeOtroAnticipo;
    private BigDecimal totalPagado;
    
    /**
     * Constructor por defecto.
     */
    public AnticipoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    public final void iniciador() {
        setAnticipoVO(new AnticipoVO());
        setAnticipoEditDelete(new Anticipo());
        setListaAnticipos(new ArrayList<Anticipo>());
       setListaAnticipoCodigo(new ArrayList<Anticipo>());
       listaOpcionesMeses=new ArrayList<SelectItem>();
        setListaOpcionesTipoAnticipos(new ArrayList<SelectItem>());
        listaServidores=new ArrayList<Servidor>();
        listaTipoAnticipos=new ArrayList<TipoAnticipo>();
        continuarAnticipo=false;
        montoMaximo=BigDecimal.ZERO;
        total=BigDecimal.ZERO;
        porcentajeOtroAnticipo=BigDecimal.ZERO;
        totalPagado =BigDecimal.ZERO;
    }
    /**
     * @return the anticipoEditDelete
     */
    public Anticipo getAnticipoEditDelete() {
        return anticipoEditDelete;
    }

    /**
     * @param anticipoEditDelete the anticipoEditDelete to set
     */
    public void setAnticipoEditDelete(final Anticipo anticipoEditDelete) {
        this.anticipoEditDelete = anticipoEditDelete;
    }

    /**
     * @return the listaAnticipos
     */
    public List<Anticipo> getListaAnticipos() {
        return listaAnticipos;
    }

    /**
     * @param listaAnticipos the listaAnticipos to set
     */
    public void setListaAnticipos(final List<Anticipo> listaAnticipos) {
        this.listaAnticipos = listaAnticipos;
    }

    /**
     * @return the listaAnticipoCodigo
     */
    public List<Anticipo> getListaAnticipoCodigo() {
        return listaAnticipoCodigo;
    }

    /**
     * @param listaAnticipoCodigo the listaAnticipoCodigo to set
     */
    public void setListaAnticipoCodigo(final List<Anticipo> listaAnticipoCodigo) {
        this.listaAnticipoCodigo = listaAnticipoCodigo;
    }

    /**
     * @return the anticipoVO
     */
    public AnticipoVO getAnticipoVO() {
        return anticipoVO;
    }

    /**
     * @param anticipoVO the anticipoVO to set
     */
    public void setAnticipoVO(AnticipoVO anticipoVO) {
        this.anticipoVO = anticipoVO;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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

    /**
     * @return the listaOpcionesTipoAnticipos
     */
    public List<SelectItem> getListaOpcionesTipoAnticipos() {
        return listaOpcionesTipoAnticipos;
    }

    /**
     * @param listaOpcionesTipoAnticipos the listaOpcionesTipoAnticipos to set
     */
    public void setListaOpcionesTipoAnticipos(List<SelectItem> listaOpcionesTipoAnticipos) {
        this.listaOpcionesTipoAnticipos = listaOpcionesTipoAnticipos;
    }

    /**
     * @return the hayConfiguracion
     */
    public boolean isHayConfiguracion() {
        return hayConfiguracion;
    }

    /**
     * @param hayConfiguracion the hayConfiguracion to set
     */
    public void setHayConfiguracion(boolean hayConfiguracion) {
        this.hayConfiguracion = hayConfiguracion;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the listaTipoAnticipos
     */
    public List<TipoAnticipo> getListaTipoAnticipos() {
        return listaTipoAnticipos;
    }

    /**
     * @param listaTipoAnticipos the listaTipoAnticipos to set
     */
    public void setListaTipoAnticipos(List<TipoAnticipo> listaTipoAnticipos) {
        this.listaTipoAnticipos = listaTipoAnticipos;
    }

    /**
     * @return the continuarAnticipo
     */
    public boolean isContinuarAnticipo() {
        return continuarAnticipo;
    }

    /**
     * @param continuarAnticipo the continuarAnticipo to set
     */
    public void setContinuarAnticipo(boolean continuarAnticipo) {
        this.continuarAnticipo = continuarAnticipo;
    }

    /**
     * @return the montoMaximo
     */
    public BigDecimal getMontoMaximo() {
        return montoMaximo;
    }

    /**
     * @param montoMaximo the montoMaximo to set
     */
    public void setMontoMaximo(BigDecimal montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return the porcentajeOtroAnticipo
     */
    public BigDecimal getPorcentajeOtroAnticipo() {
        return porcentajeOtroAnticipo;
    }

    /**
     * @param porcentajeOtroAnticipo the porcentajeOtroAnticipo to set
     */
    public void setPorcentajeOtroAnticipo(BigDecimal porcentajeOtroAnticipo) {
        this.porcentajeOtroAnticipo = porcentajeOtroAnticipo;
    }

    /**
     * @return the totalPagado
     */
    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    /**
     * @param totalPagado the totalPagado to set
     */
    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }
}