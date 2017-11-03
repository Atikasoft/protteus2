/*
 *  GastoPersonalHelper.java
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
 *  14/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.GastoPersonal;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.TipoGastoPersonal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author LRodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "gastoPersonalHelper")
@SessionScoped
public class GastoPersonalHelper extends CatalogoHelper {

    /**
     * Variable para nueva gastoPersonal.
     */
    private GastoPersonal gastoPersonal;
    /**
     * Variable para modificar/eliminar una gastoPersonal.
     */
    private GastoPersonal gastoPersonalEditDelete;
    /**
     * Variable para listar las gastoPersonals.
     */
    private List<GastoPersonal> listaGastosPersonales;
    /**
     * Variable para listar las gastoPersonals por codigo.
     */
    private List<GastoPersonal> listaGastoPersonalCodigo;
    /**
     * Lista para cargar los ejercicios fiscales
     */
    private List<InstitucionEjercicioFiscal> listaEjercicioFiscal;
    /**
     * para cargar el detalle de distribucion del servidor
     */
    private DistributivoDetalle distributivoDetalle;
    /**
     * Lista de ejercicios fiscales.
     */
    private List<SelectItem> opcionEjercicioFiscal;
    /**
     * Lista de mensajes de validación.
     */
    private List<String> msgValidacion = new ArrayList<String>();
    private BigDecimal totalSinIESS;
    /**
     * Variables de iteraccion de la vista.
     */
    private String sinConfiguracion = "";
    private Date fechIngreso;
    private Boolean botonGuardar;
    private Boolean edicionGastos;
    private Boolean esTerceraEdad;
    private Boolean esDiscapacitado;
    /**
     * Gastos Vivienda Vestimenta Educacion Vestimenta Alimentacion.
     */
    private BigDecimal maxGastosVVEA;
    private BigDecimal maxSalud;
    private BigDecimal maxExoneraciones;
    private BigDecimal maxDeducible;
    private BigDecimal maxDeducibleSobreFraccion;
    
    /**
     * 
     */
    private List<TipoGastoPersonal> listaTipoGastoPersonal;
    /**
     * Constructor.
     */
    public GastoPersonalHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la GastoPersonalHelper.
     */
    public final void iniciador() {
        setGastoPersonal(new GastoPersonal());
        setGastoPersonalEditDelete(new GastoPersonal());
        listaGastosPersonales = new ArrayList<GastoPersonal>();
        setListaGastoPersonalCodigo(new ArrayList<GastoPersonal>());
        opcionEjercicioFiscal = new ArrayList<SelectItem>();
        setBotonGuardar(Boolean.FALSE);
        setListaEjercicioFiscal(new ArrayList<InstitucionEjercicioFiscal>());
        distributivoDetalle = new DistributivoDetalle();
        edicionGastos = Boolean.FALSE;
        esTerceraEdad = Boolean.FALSE;
        esDiscapacitado = Boolean.FALSE;
        totalSinIESS = BigDecimal.ZERO;
        maxGastosVVEA = BigDecimal.ZERO;
        maxSalud = BigDecimal.ZERO;
        maxExoneraciones = BigDecimal.ZERO;
        maxDeducible = BigDecimal.ZERO;
        maxDeducibleSobreFraccion= BigDecimal.ZERO;
    }

    /**
     * @return the gastoPersonal
     */
    public GastoPersonal getGastoPersonal() {
        return gastoPersonal;
    }

    /**
     * @param gastoPersonal the gastoPersonal to set
     */
    public void setGastoPersonal(final GastoPersonal gastoPersonal) {
        this.gastoPersonal = gastoPersonal;
    }

    /**
     * @return the gastoPersonalEditDelete
     */
    public GastoPersonal getGastoPersonalEditDelete() {
        return gastoPersonalEditDelete;
    }

    /**
     * @param gastoPersonalEditDelete the gastoPersonalEditDelete to set
     */
    public void setGastoPersonalEditDelete(final GastoPersonal gastoPersonalEditDelete) {
        this.gastoPersonalEditDelete = gastoPersonalEditDelete;
    }

    /**
     * @return the listaGastoPersonalNemonico
     */
    public List<GastoPersonal> getListaGastoPersonalCodigo() {
        return listaGastoPersonalCodigo;
    }

    /**
     * @param listaGastoPersonalCodigo the listaGastoPersonalCodigo to set
     */
    public void setListaGastoPersonalCodigo(List<GastoPersonal> listaGastoPersonalCodigo) {
        this.listaGastoPersonalCodigo = listaGastoPersonalCodigo;
    }

    /**
     * @return the opcionEjercicioFiscal
     */
    public List<SelectItem> getOpcionEjercicioFiscal() {
        return opcionEjercicioFiscal;
    }

    /**
     * @param opcionEjercicioFiscal the opcionEjercicioFiscal to set
     */
    public void setOpcionEjercicioFiscal(List<SelectItem> opcionEjercicioFiscal) {
        this.opcionEjercicioFiscal = opcionEjercicioFiscal;
    }

    /**
     * @return the listaGastosPersonales
     */
    public List<GastoPersonal> getListaGastosPersonales() {
        return listaGastosPersonales;
    }

    /**
     * @param listaGastosPersonales the listaGastosPersonales to set
     */
    public void setListaGastosPersonales(List<GastoPersonal> listaGastosPersonales) {
        this.listaGastosPersonales = listaGastosPersonales;
    }

    /**
     * @return the botonGuardar
     */
    public Boolean getBotonGuardar() {
        return botonGuardar;
    }

    /**
     * @param botonGuardar the botonGuardar to set
     */
    public void setBotonGuardar(Boolean botonGuardar) {
        this.botonGuardar = botonGuardar;
    }

    /**
     * @return the listaEjercicioFiscal
     */
    public List<InstitucionEjercicioFiscal> getListaEjercicioFiscal() {
        return listaEjercicioFiscal;
    }

    /**
     * @param listaEjercicioFiscal the listaEjercicioFiscal to set
     */
    public void setListaEjercicioFiscal(List<InstitucionEjercicioFiscal> listaEjercicioFiscal) {
        this.listaEjercicioFiscal = listaEjercicioFiscal;
    }

    /**
     * @return the sinConfiguracion
     */
    public String getSinConfiguracion() {
        return sinConfiguracion;
    }

    /**
     * @param sinConfiguracion the sinConfiguracion to set
     */
    public void setSinConfiguracion(String sinConfiguracion) {
        this.sinConfiguracion = sinConfiguracion;
    }

    /**
     * @param msgValidacion the msgValidacion to set
     */
    public void setMsgValidacion(List<String> msgValidacion) {
        this.msgValidacion = msgValidacion;
    }

    /**
     * @return the msgValidacion
     */
    public List<String> getMsgValidacion() {
        return msgValidacion;
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
     * @return the edicionGastos
     */
    public Boolean getEdicionGastos() {
        return edicionGastos;
    }

    /**
     * @param edicionGastos the edicionGastos to set
     */
    public void setEdicionGastos(Boolean edicionGastos) {
        this.edicionGastos = edicionGastos;
    }

    /**
     * @return the fechIngreso
     */
    public Date getFechIngreso() {
        return fechIngreso;
    }

    /**
     * @param fechIngreso the fechIngreso to set
     */
    public void setFechIngreso(Date fechIngreso) {
        this.fechIngreso = fechIngreso;
    }

    /**
     * @return the esTerceraEdad
     */
    public Boolean getEsTerceraEdad() {
        return esTerceraEdad;
    }

    /**
     * @param esTerceraEdad the esTerceraEdad to set
     */
    public void setEsTerceraEdad(Boolean esTerceraEdad) {
        this.esTerceraEdad = esTerceraEdad;
    }

    /**
     * @return the esDiscapacitado
     */
    public Boolean getEsDiscapacitado() {
        return esDiscapacitado;
    }

    /**
     * @param esDiscapacitado the esDiscapacitado to set
     */
    public void setEsDiscapacitado(Boolean esDiscapacitado) {
        this.esDiscapacitado = esDiscapacitado;
    }

    /**
     * @return the totalSinIESS
     */
    public BigDecimal getTotalSinIESS() {
        return totalSinIESS;
    }

    /**
     * @param totalSinIESS the totalSinIESS to set
     */
    public void setTotalSinIESS(BigDecimal totalSinIESS) {
        this.totalSinIESS = totalSinIESS;
    }

    /**
     * @return the maxGastosVVEA
     */
    public BigDecimal getMaxGastosVVEA() {
        return maxGastosVVEA;
    }

    /**
     * @param maxGastosVVEA the maxGastosVVEA to set
     */
    public void setMaxGastosVVEA(BigDecimal maxGastosVVEA) {
        this.maxGastosVVEA = maxGastosVVEA;
    }

    /**
     * @return the maxSalud
     */
    public BigDecimal getMaxSalud() {
        return maxSalud;
    }

    /**
     * @param maxSalud the maxSalud to set
     */
    public void setMaxSalud(BigDecimal maxSalud) {
        this.maxSalud = maxSalud;
    }

    /**
     * @return the maxExoneraciones
     */
    public BigDecimal getMaxExoneraciones() {
        return maxExoneraciones;
    }

    /**
     * @param maxExoneraciones the maxExoneraciones to set
     */
    public void setMaxExoneraciones(BigDecimal maxExoneraciones) {
        this.maxExoneraciones = maxExoneraciones;
    }

    /**
     * @return the maxDeducible
     */
    public BigDecimal getMaxDeducible() {
        return maxDeducible;
    }

    /**
     * @param maxDeducible the maxDeducible to set
     */
    public void setMaxDeducible(BigDecimal maxDeducible) {
        this.maxDeducible = maxDeducible;
    }

    /**
     * @return the maxDeducibleSobreFraccion
     */
    public BigDecimal getMaxDeducibleSobreFraccion() {
        return maxDeducibleSobreFraccion;
    }

    /**
     * @param maxDeducibleSobreFraccion the maxDeducibleSobreFraccion to set
     */
    public void setMaxDeducibleSobreFraccion(BigDecimal maxDeducibleSobreFraccion) {
        this.maxDeducibleSobreFraccion = maxDeducibleSobreFraccion;
    }

    /**
     * @return the listaTipoGastoPersonal
     */
    public List<TipoGastoPersonal> getListaTipoGastoPersonal() {
        return listaTipoGastoPersonal;
    }

    /**
     * @param listaTipoGastoPersonal the listaTipoGastoPersonal to set
     */
    public void setListaTipoGastoPersonal(List<TipoGastoPersonal> listaTipoGastoPersonal) {
        this.listaTipoGastoPersonal = listaTipoGastoPersonal;
    }
}
