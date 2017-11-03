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
 *  06/26/2017
 *
 *  Copyright (C) 2017 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.vo.DesconcentradoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "desconcentradoHelper")
@SessionScoped
public class DesconcentradoHelper extends CatalogoHelper {

    /**
     * ENTIDAD DESCOCENTRADO
     */
    private Desconcentrado desconcentrado;

    /**
     *
     */
    private DesconcentradoVO desconcentradoVO;

    /**
     *
     */
    private List<Servidor> listaServidoresApoyoSeleccionados;
    
    /**
     * LISTA PARA GUARDAR LOS SERVIDORES DE APOYO SELECCIONADOS CUANDO SE VA A EDITAR UN DESCOCNETRADO
     */
    private List<Servidor> listaServApoyoSeleccionadosTemp;
    
    /**
     * VARIABLE PARA GUARDAR EL SERVIDORES RESPONSABLE CUANDO SE VA A EDITAR UN DESCOCNETRADO
     */
    private Long servidorResponsableTempId;

    /**
     *
     */
    private List<Desconcentrado> listaDesconcentrados;

    /**
     *
     */
    private List<DesconcentradoApoyo> listaDesconcentradosApoyos;

    /**
     *
     */
    private List<DesconcentradoUnidadOrganizacional> listaDesconcentradosUnidadesOrganizacionales;

    /**
     *
     */
    private String codigoFuncionSeleccionada;
    /**
     *
     */
    private String codigoFuncionOrigenCopia;

    /**
     *
     */
    private List<SelectItem> listaFuncionesDesconcentrado;
    
    /**
     *
     */
    private List<SelectItem> listaFuncionesDesconcentrado2;
    
    /**
     * 
     */
    private TreeNode root;

    /**
     * 
     */
    private TreeNode selectedNode;

    /**
     * variables para ubicacion geográfica.
     */
    private List<UnidadOrganizacional> listaUnidadOrganizacional;
    
    /**
     * 
     */
    private Map<Long, Map<String, String>> mapUnidOrgFunciones;
    
    /**
     * 
     */
    private List<Long> listaIdUnidadesOrganizacionalesSeleccionadas;
    
    /**
     * 
     */
    private Boolean guardarSoloDatosGeneralesYApoyos;
    

    /**
     * Constructor por defecto.
     */
    public DesconcentradoHelper() {
        super();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    @PostConstruct
    public final void iniciador() {
        desconcentradoVO = new DesconcentradoVO();
        listaDesconcentrados = new ArrayList<>();
        listaServidoresApoyoSeleccionados = new ArrayList<>();
        listaServApoyoSeleccionadosTemp = new ArrayList<>();
        listaDesconcentradosApoyos = new ArrayList<>();
        listaDesconcentradosUnidadesOrganizacionales = new ArrayList<>();
        listaFuncionesDesconcentrado = new ArrayList<>();
        listaFuncionesDesconcentrado2 = new ArrayList<>();
        listaIdUnidadesOrganizacionalesSeleccionadas = new ArrayList<>();
        
        listaUnidadOrganizacional = new ArrayList<>();
        mapUnidOrgFunciones = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public Desconcentrado getDesconcentrado() {
        return desconcentrado;
    }

    /**
     *
     * @param desconcentrado
     */
    public void setDesconcentrado(Desconcentrado desconcentrado) {
        this.desconcentrado = desconcentrado;
    }

    /**
     *
     * @return
     */
    public DesconcentradoVO getDesconcentradoVO() {
        return desconcentradoVO;
    }

    /**
     *
     * @param desconcentradoVO
     */
    public void setDesconcentradoVO(DesconcentradoVO desconcentradoVO) {
        this.desconcentradoVO = desconcentradoVO;
    }

    /**
     *
     * @return
     */
    public List<Desconcentrado> getListaDesconcentrados() {
        return listaDesconcentrados;
    }

    /**
     *
     * @param listaDesconcentrados
     */
    public void setListaDesconcentrados(List<Desconcentrado> listaDesconcentrados) {
        this.listaDesconcentrados = listaDesconcentrados;
    }

    /**
     *
     * @return
     */
    public List<Servidor> getListaServidoresApoyoSeleccionados() {
        return listaServidoresApoyoSeleccionados;
    }

    /**
     *
     * @param listaServidoresApoyoSeleccionados
     */
    public void setListaServidoresApoyoSeleccionados(List<Servidor> listaServidoresApoyoSeleccionados) {
        this.listaServidoresApoyoSeleccionados = listaServidoresApoyoSeleccionados;
    }

    /**
     * 
     * @return 
     */
    public List<Servidor> getListaServApoyoSeleccionadosTemp() {
        return listaServApoyoSeleccionadosTemp;
    }

    /**
     * 
     * @param listaServApoyoSeleccionadosTemp 
     */
    public void setListaServApoyoSeleccionadosTemp(List<Servidor> listaServApoyoSeleccionadosTemp) {
        this.listaServApoyoSeleccionadosTemp = listaServApoyoSeleccionadosTemp;
    }

    /**
     * 
     * @return 
     */
    public Long getServidorResponsableTempId() {
        return servidorResponsableTempId;
    }

    /**
     * 
     * @param servidorResponsableTempId 
     */
    public void setServidorResponsableTempId(Long servidorResponsableTempId) {
        this.servidorResponsableTempId = servidorResponsableTempId;
    }

    /**
     *
     * @return
     */
    public List<DesconcentradoApoyo> getListaDesconcentradosApoyos() {
        return listaDesconcentradosApoyos;
    }

    /**
     *
     * @param listaDesconcentradosApoyos
     */
    public void setListaDesconcentradosApoyos(List<DesconcentradoApoyo> listaDesconcentradosApoyos) {
        this.listaDesconcentradosApoyos = listaDesconcentradosApoyos;
    }

    /**
     *
     * @return
     */
    public List<DesconcentradoUnidadOrganizacional> getListaDesconcentradosUnidadesOrganizacionales() {
        return listaDesconcentradosUnidadesOrganizacionales;
    }

    /**
     *
     * @param listaDesconcentradosUnidadesOrganizacionales
     */
    public void setListaDesconcentradosUnidadesOrganizacionales(
            List<DesconcentradoUnidadOrganizacional> listaDesconcentradosUnidadesOrganizacionales) {
        this.listaDesconcentradosUnidadesOrganizacionales = listaDesconcentradosUnidadesOrganizacionales;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListaFuncionesDesconcentrado() {
        return listaFuncionesDesconcentrado;
    }

    /**
     *
     * @param listaFuncionesDesconcentrado
     */
    public void setListaFuncionesDesconcentrado(List<SelectItem> listaFuncionesDesconcentrado) {
        this.listaFuncionesDesconcentrado = listaFuncionesDesconcentrado;
    }

    /**
     *
     * @return
     */
    public String getCodigoFuncionSeleccionada() {
        return codigoFuncionSeleccionada;
    }

    /**
     *
     * @param codigoFuncionSeleccionada
     */
    public void setCodigoFuncionSeleccionada(String codigoFuncionSeleccionada) {
        this.codigoFuncionSeleccionada = codigoFuncionSeleccionada;
    }

    /**
     * 
     * @return 
     */
    public String getCodigoFuncionOrigenCopia() {
        return codigoFuncionOrigenCopia;
    }

    /**
     * 
     * @param codigoFuncionOrigenCopia 
     */
    public void setCodigoFuncionOrigenCopia(String codigoFuncionOrigenCopia) {
        this.codigoFuncionOrigenCopia = codigoFuncionOrigenCopia;
    }

    /**
     * 
     * @return 
     */
    public List<SelectItem> getListaFuncionesDesconcentrado2() {
        return listaFuncionesDesconcentrado2;
    }

    /**
     * 
     * @param listaFuncionesDesconcentrado2 
     */
    public void setListaFuncionesDesconcentrado2(List<SelectItem> listaFuncionesDesconcentrado2) {
        this.listaFuncionesDesconcentrado2 = listaFuncionesDesconcentrado2;
    }

    /**
     * 
     * @return 
     */
    public List<Long> getListaIdUnidadesOrganizacionalesSeleccionadas() {
        return listaIdUnidadesOrganizacionalesSeleccionadas;
    }

    /**
     * 
     * @param listaIdUnidadesOrganizacionalesSeleccionadas 
     */
    public void setListaIdUnidadesOrganizacionalesSeleccionadas(List<Long> listaIdUnidadesOrganizacionalesSeleccionadas) {
        this.listaIdUnidadesOrganizacionalesSeleccionadas = listaIdUnidadesOrganizacionalesSeleccionadas;
    }

    /**
     * 
     * @return 
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * 
     * @param root 
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * 
     * @return 
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * 
     * @param selectedNode 
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    /**
     * 
     * @return 
     */
    public List<UnidadOrganizacional> getListaUnidadOrganizacional() {
        return listaUnidadOrganizacional;
    }

    /**
     * 
     * @param listaUnidadOrganizacional 
     */
    public void setListaUnidadOrganizacional(List<UnidadOrganizacional> listaUnidadOrganizacional) {
        this.listaUnidadOrganizacional = listaUnidadOrganizacional;
    }

    /**
     * 
     * @return 
     */
    public Map<Long, Map<String, String>> getMapUnidOrgFunciones() {
        return mapUnidOrgFunciones;
    }

    /**
     * 
     * @param mapUnidOrgFunciones 
     */
    public void setMapUnidOrgFunciones(Map<Long, Map<String, String>> mapUnidOrgFunciones) {
        this.mapUnidOrgFunciones = mapUnidOrgFunciones;
    }

    /**
     * 
     * @return 
     */
    public Boolean getGuardarSoloDatosGeneralesYApoyos() {
        return guardarSoloDatosGeneralesYApoyos;
    }

    /**
     * 
     * @param guardarSoloDatosGeneralesYApoyos 
     */
    public void setGuardarSoloDatosGeneralesYApoyos(Boolean guardarSoloDatosGeneralesYApoyos) {
        this.guardarSoloDatosGeneralesYApoyos = guardarSoloDatosGeneralesYApoyos;
    }
    
}
