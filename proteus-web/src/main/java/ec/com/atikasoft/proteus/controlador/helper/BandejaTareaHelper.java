/*
 *  BandejaTareaHelper.java
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
 *  31/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.enums.TareaOrdenarPorEnum;
import ec.com.atikasoft.proteus.enums.TipoActividadEnum;
import ec.com.atikasoft.proteus.enums.TipoOrdenamientoEnum;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.vo.BusquedaTareaVO;
import ec.com.atikasoft.proteus.vo.TareaVO;
import ec.com.atikasoft.proteus.vo.TramiteFormularioVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "bandejaTareaHelper")
@SessionScoped
public class BandejaTareaHelper implements Serializable {

    /**
     * Tarea.
     */
    private Tarea tarea;

    /**
     * VO de Tramite Formulario.
     */
    private TramiteFormularioVO tramiteFormularioVO;

    /**
     * Lista de tareas.
     */
    private List<TareaVO> listaTareas;
    
    /**
     * Lista de tareas filtradas.
     */
    private List<TareaVO> listaTareasFiltradas;

    /**
     * LIsta de campos a consultar.
     */
    private List<SelectItem> campoConsulta = new ArrayList<SelectItem>();

    /**
     * Lista de tipos de ordenamiento.
     */
    private List<SelectItem> tipoOrdenamiento = new ArrayList<SelectItem>();

    /**
     * Lista tipo de asiganacion.
     */
    private List<SelectItem> tipoActividad = new ArrayList<SelectItem>();

    /**
     * Opciones consulta.
     */
    private List<SelectItem> opcionConsulta = new ArrayList<SelectItem>();

    /**
     * Datos para la busqueda de tareas.
     */
    private BusquedaTareaVO busquedaTareaVO;

    /**
     * Bandera para ver u ocultar los campos cuando este en estado atendido.
     */
    private Boolean verCamposAtendido;

    /**
     * Bandera usada para abrir el popup de comentario.
     */
    private Boolean verComentario = Boolean.FALSE;
    
    private Long grupoId;
    
    private Long claseId;
    
    private Long tipoMovimientoId;
    
    private List<SelectItem> listaClase;
    
    private List<SelectItem> listaTipoMovimiento;
    
    private List<SelectItem> listaGrupo;
    
    private String clasesFiltrosSeleccionados;
    

    /**
     * Constructor por defecto.
     */
    public BandejaTareaHelper() {
        iniciador();
    }

    /**
     * Este método inicializa las variables del helper.
     */
    public final void iniciador() {
        busquedaTareaVO = new BusquedaTareaVO();
        listaTareas = new ArrayList<TareaVO>();
        tipoOrdenamiento.clear();
        for (TipoOrdenamientoEnum to : TipoOrdenamientoEnum.values()) {
            tipoOrdenamiento.add(new SelectItem(to.getCodigo(), to.getDescripcion()));
        }
        campoConsulta.clear();
        for (TareaOrdenarPorEnum cc : TareaOrdenarPorEnum.values()) {
            campoConsulta.add(new SelectItem(cc.getColumna(), cc.getDescripcion()));
        }
        tipoActividad.clear();
        for (TipoActividadEnum ta : TipoActividadEnum.values()) {
            tipoActividad.add(new SelectItem(ta.getCodigo(), ta.getDescripcion()));
        }
        listaClase = new ArrayList<SelectItem>();
        listaTipoMovimiento = new ArrayList<SelectItem>();
        listaGrupo = new ArrayList<SelectItem>();
        listaTareasFiltradas = new ArrayList<TareaVO>();
    }

    /**
     * @return the tarea
     */
    public Tarea getTarea() {
        return tarea;
    }
//7463737A6D6273

    /**
     * @param tarea the tarea to set
     */
    public void setTarea(final Tarea tarea) {
        this.tarea = tarea;
    }

    /**
     * @return the listaTareas
     */
    public List<TareaVO> getListaTareas() {
        return listaTareas;
    }

    /**
     * @param listaTareas the listaTareas to set
     */
    public void setListaTareas(final List<TareaVO> listaTareas) {
        this.listaTareas = listaTareas;
    }

    /**
     * @return the busquedaTareaVO
     */
    public BusquedaTareaVO getBusquedaTareaVO() {
        return busquedaTareaVO;
    }

    /**
     * @param busquedaTareaVO the busquedaTareaVO to set
     */
    public void setBusquedaTareaVO(final BusquedaTareaVO busquedaTareaVO) {
        this.busquedaTareaVO = busquedaTareaVO;
    }

    /**
     * @return the campoConsulta
     */
    public List<SelectItem> getCampoConsulta() {
        return campoConsulta;
    }

    /**
     * @param campoConsulta the campoConsulta to set
     */
    public void setCampoConsulta(final List<SelectItem> campoConsulta) {
        this.campoConsulta = campoConsulta;
    }

    /**
     * @return the tipoOrdenamiento
     */
    public List<SelectItem> getTipoOrdenamiento() {
        return tipoOrdenamiento;
    }

    /**
     * @param tipoOrdenamiento the tipoOrdenamiento to set
     */
    public void setTipoOrdenamiento(final List<SelectItem> tipoOrdenamiento) {
        this.tipoOrdenamiento = tipoOrdenamiento;
    }

    /**
     * @return the opcionConsulta
     */
    public List<SelectItem> getOpcionConsulta() {
        return opcionConsulta;
    }

    /**
     * @param opcionConsulta the opcionConsulta to set
     */
    public void setOpcionConsulta(final List<SelectItem> opcionConsulta) {
        this.opcionConsulta = opcionConsulta;
    }

    /**
     * @return the tramiteFormularioVO
     */
    public TramiteFormularioVO getTramiteFormularioVO() {
        return tramiteFormularioVO;
    }

    /**
     * @param tramiteFormularioVO the tramiteFormularioVO to set
     */
    public void setTramiteFormularioVO(final TramiteFormularioVO tramiteFormularioVO) {
        this.tramiteFormularioVO = tramiteFormularioVO;
    }

    /**
     * @return the tipoActividad
     */
    public List<SelectItem> getTipoActividad() {
        return tipoActividad;
    }

    /**
     * @param tipoActividad the tipoActividad to set
     */
    public void setTipoActividad(final List<SelectItem> tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    /**
     * @return the verCamposAtendido
     */
    public Boolean getVerCamposAtendido() {
        return verCamposAtendido;
    }

    /**
     * @param verCamposAtendido the verCamposAtendido to set
     */
    public void setVerCamposAtendido(final Boolean verCamposAtendido) {
        this.verCamposAtendido = verCamposAtendido;
    }

    /**
     * @return the verComentario
     */
    public Boolean getVerComentario() {
        return verComentario;
    }

    /**
     * @param verComentario the verComentario to set
     */
    public void setVerComentario(final Boolean verComentario) {
        this.verComentario = verComentario;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public Long getClaseId() {
        return claseId;
    }

    public void setClaseId(Long claseId) {
        this.claseId = claseId;
    }

    public Long getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Long tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public List<SelectItem> getListaClase() {
        return listaClase;
    }

    public void setListaClase(List<SelectItem> listaClase) {
        this.listaClase = listaClase;
    }

    public List<SelectItem> getListaTipoMovimiento() {
        return listaTipoMovimiento;
    }

    public void setListaTipoMovimiento(List<SelectItem> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    public void setListaGrupo(List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    public String getClasesFiltrosSeleccionados() {
        return clasesFiltrosSeleccionados;
    }

    public void setClasesFiltrosSeleccionados(String clasesFiltrosSeleccionados) {
        this.clasesFiltrosSeleccionados = clasesFiltrosSeleccionados;
    }

    public List<TareaVO> getListaTareasFiltradas() {
        return listaTareasFiltradas;
    }

    public void setListaTareasFiltradas(List<TareaVO> listaTareasFiltradas) {
        this.listaTareasFiltradas = listaTareasFiltradas;
    }
}
