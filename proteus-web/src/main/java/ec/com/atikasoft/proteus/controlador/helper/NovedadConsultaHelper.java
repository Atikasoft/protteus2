/*
 *  NovedadConsultaHelper.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.vistas.VistaNovedad;
import ec.com.atikasoft.proteus.vo.ConsultaNovedadVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "novedadConsultaHelper")
@SessionScoped
public class NovedadConsultaHelper implements Serializable {

    /**
     * Variable para asignar valores de busqueda.
     */
    private ConsultaNovedadVO consultaNovedadVO = new ConsultaNovedadVO();
    /**
     * Lista de servidores.
     */
    private List<Servidor> listaServidores = new ArrayList<Servidor>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoDocumento = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoPuesto = new ArrayList<SelectItem>();
   
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionDatoAdicional = new ArrayList<SelectItem>();
    private List<SelectItem> listaOpcionEjercicioFiscal = new ArrayList<SelectItem>();
    private List<SelectItem> listaOpcionTipoNomina = new ArrayList<SelectItem>();
    private List<SelectItem> listaOpcionPeriodoNomina = new ArrayList<SelectItem>();
    private List<SelectItem> listaOpcionNomina = new ArrayList<SelectItem>();
    
 
    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;
    /**
     * Indica si el panel de parametros esta activo.
     */
    private String activo = "0";
       /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionEstadoNomina = new ArrayList<SelectItem>();

    /**
     * Constructor por defecto.
     */
    public NovedadConsultaHelper() {
        super();

    }

    /**
     * @return the listaTipoDocumento
     */
    public List<SelectItem> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    /**
     * @param listaTipoDocumento the listaTipoDocumento to set
     */
    public void setListaTipoDocumento(final List<SelectItem> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    /**
     * @return the listaEstadoPuesto
     */
    public List<SelectItem> getListaEstadoPuesto() {
        return listaEstadoPuesto;
    }

    /**
     * @param listaEstadoPuesto the listaEstadoPuesto to set
     */
    public void setListaEstadoPuesto(final List<SelectItem> listaEstadoPuesto) {
        this.listaEstadoPuesto = listaEstadoPuesto;
    }

    /**
     * @return the mensajeValidaciones
     */
    public String getMensajeValidaciones() {
        return mensajeValidaciones;
    }

    /**
     * @param mensajeValidaciones the mensajeValidaciones to set
     */
    public void setMensajeValidaciones(final String mensajeValidaciones) {
        this.mensajeValidaciones = mensajeValidaciones;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(String activo) {
        this.activo = activo;
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
     * @return the consultaNovedadVO
     */
    public ConsultaNovedadVO getConsultaNovedadVO() {
        return consultaNovedadVO;
    }

    /**
     * @param consultaNovedadVO the consultaNovedadVO to set
     */
    public void setConsultaNovedadVO(ConsultaNovedadVO consultaNovedadVO) {
        this.consultaNovedadVO = consultaNovedadVO;
    }
        /**
     * @return the listaOpcionEstadoNomina
     */
    public List<SelectItem> getListaOpcionEstadoNomina() {
        return listaOpcionEstadoNomina;
    }

    /**
     * @param listaOpcionEstadoNomina the listaOpcionEstadoNomina to set
     */
    public void setListaOpcionEstadoNomina(List<SelectItem> listaOpcionEstadoNomina) {
        this.listaOpcionEstadoNomina = listaOpcionEstadoNomina;
    }

    /**
     * @return the listaOpcionDatoAdicional
     */
    public List<SelectItem> getListaOpcionDatoAdicional() {
        return listaOpcionDatoAdicional;
    }

    /**
     * @param listaOpcionDatoAdicional the listaOpcionDatoAdicional to set
     */
    public void setListaOpcionDatoAdicional(List<SelectItem> listaOpcionDatoAdicional) {
        this.listaOpcionDatoAdicional = listaOpcionDatoAdicional;
    }

    /**
     * @return the listaOpcionEjercicioFiscal
     */
    public List<SelectItem> getListaOpcionEjercicioFiscal() {
        return listaOpcionEjercicioFiscal;
    }

    /**
     * @param listaOpcionEjercicioFiscal the listaOpcionEjercicioFiscal to set
     */
    public void setListaOpcionEjercicioFiscal(List<SelectItem> listaOpcionEjercicioFiscal) {
        this.listaOpcionEjercicioFiscal = listaOpcionEjercicioFiscal;
    }

    /**
     * @return the listaOpcionTipoNomina
     */
    public List<SelectItem> getListaOpcionTipoNomina() {
        return listaOpcionTipoNomina;
    }

    /**
     * @param listaOpcionTipoNomina the listaOpcionTipoNomina to set
     */
    public void setListaOpcionTipoNomina(List<SelectItem> listaOpcionTipoNomina) {
        this.listaOpcionTipoNomina = listaOpcionTipoNomina;
    }

    /**
     * @return the listaOpcionPeriodoNomina
     */
    public List<SelectItem> getListaOpcionPeriodoNomina() {
        return listaOpcionPeriodoNomina;
    }

    /**
     * @param listaOpcionPeriodoNomina the listaOpcionPeriodoNomina to set
     */
    public void setListaOpcionPeriodoNomina(List<SelectItem> listaOpcionPeriodoNomina) {
        this.listaOpcionPeriodoNomina = listaOpcionPeriodoNomina;
    }

    /**
     * @return the listaOpcionNomina
     */
    public List<SelectItem> getListaOpcionNomina() {
        return listaOpcionNomina;
    }

    /**
     * @param listaOpcionNomina the listaOpcionNomina to set
     */
    public void setListaOpcionNomina(List<SelectItem> listaOpcionNomina) {
        this.listaOpcionNomina = listaOpcionNomina;
    }
}