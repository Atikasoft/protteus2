/*
 *  DescentralizacionHelper.java
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
 *  22/02/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDescentralizado;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;

/**
 * Helper Descentralizacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@SessionScoped
@ManagedBean(name = "descentralizacionHelper")
public class DescentralizacionHelper implements Serializable {

    /**
     * Lista de Unidades organizacionales
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();

    /**
     * Lista de tipos de movimientos.
     */
    private List<TipoMovimiento> listaTiposMovimientos = new ArrayList<TipoMovimiento>();

    /**
     * Id del tipo movimiento.
     */
    private Long tipoMovimientoId;

    /**
     * Tipo Movimiento.
     */
    private TipoMovimiento tipoMovimiento = new TipoMovimiento();

    /**
     * Variable de transicion.
     */
    private TipoMovimientoDescentralizado tipoMovimientoDescentralizado = new TipoMovimientoDescentralizado();

    /**
     * Lista de movimientos descentralizados.
     */
    private List<TipoMovimientoDescentralizado> listaTipoMovimientoDescentralizado =
            new ArrayList<TipoMovimientoDescentralizado>();

    /**
     * Lista de movimientos descentralizados.
     */
    private List<TipoMovimientoDescentralizado> tiposMovimientosDescentralizadosProceso =
            new ArrayList<TipoMovimientoDescentralizado>();

    /**
     * Variable para creacion del arbol
     */
    private TreeNode RootUnidadOrganizacional;
     private TreeNode[] unidadesSeleccionadas;
     
     private  LazyDataModel<UnidadOrganizacional> lazyModel; 
     
     
     private List<SelectItem> listaGrupos;
     
     private List<SelectItem> listaClases;
     
     private Long idGrupoSeleccionado;
     
     private Long idClaseSeleccionada;
     
     private String clasesJson;
     
    /**
     * Constructor por defecto.
     */
    public DescentralizacionHelper() {
        super();
        RootUnidadOrganizacional=new DefaultTreeNode();
        listaClases = new ArrayList<SelectItem>();
        listaGrupos = new ArrayList<SelectItem>();
    }

    /**
     * @return the listaTipoMovimientoDescentralizado
     */
    public List<TipoMovimientoDescentralizado> getListaTipoMovimientoDescentralizado() {
        return listaTipoMovimientoDescentralizado;
    }

    /**
     * @param listaTipoMovimientoDescentralizado the listaTipoMovimientoDescentralizado to set
     */
    public void setListaTipoMovimientoDescentralizado(
            final List<TipoMovimientoDescentralizado> listaTipoMovimientoDescentralizado) {
        this.listaTipoMovimientoDescentralizado = listaTipoMovimientoDescentralizado;
    }

    /**
     * @return the tiposMovimientosDescentralizadosProceso
     */
    public List<TipoMovimientoDescentralizado> getTiposMovimientosDescentralizadosProceso() {
        return tiposMovimientosDescentralizadosProceso;
    }

    /**
     * @param tiposMovimientosDescentralizadosProceso the tiposMovimientosDescentralizadosProceso to set
     */
    public void setTiposMovimientosDescentralizadosProceso(
            final List<TipoMovimientoDescentralizado> tiposMovimientosDescentralizadosProceso) {
        this.tiposMovimientosDescentralizadosProceso = tiposMovimientosDescentralizadosProceso;
    }

    /**
     * @return the tipoMovimientoDescentralizado
     */
    public TipoMovimientoDescentralizado getTipoMovimientoDescentralizado() {
        return tipoMovimientoDescentralizado;
    }

    /**
     * @param tipoMovimientoDescentralizado the tipoMovimientoDescentralizado to set
     */
    public void setTipoMovimientoDescentralizado(final TipoMovimientoDescentralizado tipoMovimientoDescentralizado) {
        this.tipoMovimientoDescentralizado = tipoMovimientoDescentralizado;
    }

    /**
     * @return the listaTiposMovimientos
     */
    public List<TipoMovimiento> getListaTiposMovimientos() {
        return listaTiposMovimientos;
    }

    /**
     * @param listaTiposMovimientos the listaTiposMovimientos to set
     */
    public void setListaTiposMovimientos(final List<TipoMovimiento> listaTiposMovimientos) {
        this.listaTiposMovimientos = listaTiposMovimientos;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
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
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to set
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the RootUnidadOrganizacional
     */
    public TreeNode getRootUnidadOrganizacional() {
        return RootUnidadOrganizacional;
    }

    /**
     * @param RootUnidadOrganizacional the RootUnidadOrganizacional to set
     */
    public void setRootUnidadOrganizacional(TreeNode RootUnidadOrganizacional) {
        this.RootUnidadOrganizacional = RootUnidadOrganizacional;
    }

    /**
     * @return the unidadesSeleccionadas
     */
    public TreeNode[] getUnidadesSeleccionadas() {
        return unidadesSeleccionadas;
    }

    /**
     * @param unidadesSeleccionadas the unidadesSeleccionadas to set
     */
    public void setUnidadesSeleccionadas(TreeNode[] unidadesSeleccionadas) {
        this.unidadesSeleccionadas = unidadesSeleccionadas;
    }

    /**
     * @return the lazyModel
     */
    public LazyDataModel<UnidadOrganizacional> getLazyModel() {
        return lazyModel;
    }

    /**
     * @param lazyModel the lazyModel to set
     */
    public void setLazyModel(LazyDataModel<UnidadOrganizacional> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public List<SelectItem> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<SelectItem> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public List<SelectItem> getListaClases() {
        return listaClases;
    }

    public void setListaClases(List<SelectItem> listaClases) {
        this.listaClases = listaClases;
    }

    public Long getIdGrupoSeleccionado() {
        return idGrupoSeleccionado;
    }

    public void setIdGrupoSeleccionado(Long idGrupoSeleccionado) {
        this.idGrupoSeleccionado = idGrupoSeleccionado;
    }

    public Long getIdClaseSeleccionada() {
        return idClaseSeleccionada;
    }

    public void setIdClaseSeleccionada(Long idClaseSeleccionada) {
        this.idClaseSeleccionada = idClaseSeleccionada;
    }

    public String getClasesJson() {
        return clasesJson;
    }

    public void setClasesJson(String clasesJson) {
        this.clasesJson = clasesJson;
    }
}
