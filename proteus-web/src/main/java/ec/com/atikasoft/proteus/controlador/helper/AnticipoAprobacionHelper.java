/*
 *  AnticipoAprobacionHelper.java
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
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.vo.AnticipoVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * anticipoAprobacionHelper
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "anticipoAprobacionHelper")
@SessionScoped
public class AnticipoAprobacionHelper extends CatalogoHelper {

    /**
     * Variable de referencia Anticipo.
     */
    private AnticipoVO anticipoVO;
    /**
     * Variable para almacenar las busquedadas.
     */
    private List<AnticipoVO> listaAnticipos;

    /**
     * Variable para almacenar las busquedadas.
     */
    private List<AnticipoPlanPago> listaAnticipoPlanPago;

    /**
     * Variable para cargar filtros de búsqueda.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales;

    /**
     * Variable para creacion del arbol de unidades organizacionales.
     */
    private TreeNode rootUnidadOrganizacional;
    private TreeNode unidadSeleccionada;

    private List<SelectItem> listaOpcionesEjercicioFiscal;
    private List<SelectItem> listaOpcionesEstado;
    private List<SelectItem> listaOpcionesEstadoList;

    private Long ejercicioFiscal;
    private UnidadOrganizacional unidadOrganizacional;
    private String estado;

    private Date fechaIngreso;
    private String observacion;
    private Boolean guardado = Boolean.FALSE;
    private Boolean esRRHH = Boolean.FALSE;

    /**
     * Constructor por defecto.
     */
    public AnticipoAprobacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    public final void iniciador() {
        anticipoVO = new AnticipoVO();
        setListaUnidadesOrganizacionales(new ArrayList<UnidadOrganizacional>());
        setRootUnidadOrganizacional(new DefaultTreeNode());
        setUnidadSeleccionada(new DefaultTreeNode());
        setListaOpcionesEjercicioFiscal(new ArrayList<SelectItem>());
        setListaOpcionesEstado(new ArrayList<SelectItem>());
        setUnidadOrganizacional(new UnidadOrganizacional());
        listaAnticipos = new ArrayList<AnticipoVO>();
        listaAnticipoPlanPago = new ArrayList<AnticipoPlanPago>();
        listaOpcionesEstadoList = new ArrayList<SelectItem>();
    }

    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to
     * set
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the rootUnidadOrganizacional
     */
    public TreeNode getRootUnidadOrganizacional() {
        return rootUnidadOrganizacional;
    }

    /**
     * @param rootUnidadOrganizacional the rootUnidadOrganizacional to set
     */
    public void setRootUnidadOrganizacional(TreeNode rootUnidadOrganizacional) {
        this.rootUnidadOrganizacional = rootUnidadOrganizacional;
    }

    /**
     * @return the unidadSeleccionada
     */
    public TreeNode getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    /**
     * @param unidadSeleccionada the unidadSeleccionada to set
     */
    public void setUnidadSeleccionada(TreeNode unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    /**
     * @return the listaOpcionesEjercicioFiscal
     */
    public List<SelectItem> getListaOpcionesEjercicioFiscal() {
        return listaOpcionesEjercicioFiscal;
    }

    /**
     * @param listaOpcionesEjercicioFiscal the listaOpcionesEjercicioFiscal to
     * set
     */
    public void setListaOpcionesEjercicioFiscal(List<SelectItem> listaOpcionesEjercicioFiscal) {
        this.listaOpcionesEjercicioFiscal = listaOpcionesEjercicioFiscal;
    }

    /**
     * @return the listaOpcionesEstado
     */
    public List<SelectItem> getListaOpcionesEstado() {
        return listaOpcionesEstado;
    }

    /**
     * @param listaOpcionesEstado the listaOpcionesEstado to set
     */
    public void setListaOpcionesEstado(List<SelectItem> listaOpcionesEstado) {
        this.listaOpcionesEstado = listaOpcionesEstado;
    }

    /**
     * @return the ejercicioFiscal
     */
    public Long getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(Long ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
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
     * @return the listaAnticipos
     */
    public List<AnticipoVO> getListaAnticipos() {
        return listaAnticipos;
    }

    /**
     * @param listaAnticipos the listaAnticipos to set
     */
    public void setListaAnticipos(List<AnticipoVO> listaAnticipos) {
        this.listaAnticipos = listaAnticipos;
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
     * @return the listaOpcionesEstadoList
     */
    public List<SelectItem> getListaOpcionesEstadoList() {
        return listaOpcionesEstadoList;
    }

    /**
     * @param listaOpcionesEstadoList the listaOpcionesEstadoList to set
     */
    public void setListaOpcionesEstadoList(List<SelectItem> listaOpcionesEstadoList) {
        this.listaOpcionesEstadoList = listaOpcionesEstadoList;
    }

    /**
     * @return the guardado
     */
    public Boolean getGuardado() {
        return guardado;
    }

    /**
     * @param guardado the guardado to set
     */
    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    /**
     * @return the esRRHH
     */
    public Boolean getEsRRHH() {
        return esRRHH;
    }

    /**
     * @param esRRHH the esRRHH to set
     */
    public void setEsRRHH(Boolean esRRHH) {
        this.esRRHH = esRRHH;
    }

}
