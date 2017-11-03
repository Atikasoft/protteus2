/*
 *  ReasignacionTramiteHelper.java
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
 *  03/01/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.ReasignacionTarea;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.utilitarios.ReasignacionDataModel;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.vo.BusquedaTareaVO;
import ec.com.atikasoft.proteus.vo.TareaVO;
import ec.com.atikasoft.proteus.vo.TramiteFormularioVO;
import ec.com.atikasoft.proteus.vo.UsuarioRolVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "reasignacionTramiteHelper")
@SessionScoped
public class ReasignacionTramiteHelper implements Serializable {
    /**
     * Tramite.
     */
    private Tramite tramite;
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
     * Lista de tareas filtras para el datatable.
     */
    private List<TareaVO> listaTareasFiltradas;
    /**
     * Lista de UsuarioRolVO.
     */
    private List<UsuarioRolVO> listaUsuariosRolVO;
    /**
     * servidor seleccionar.
     */
    private UsuarioRolVO selectedUsuarioRolVO;
    /**
     * Datos para la busqueda de tareas.
     */
    private BusquedaTareaVO busquedaTareaVO;
    /**
     * Reasignacion Tarea.
     */
    private ReasignacionTarea reasignacionTarea;
    /**
     * ReasignacionDataModel.
     */
    private ReasignacionDataModel mediomReasignacionDataModel;
    /**
     * Motivo.
     */
    private String motivo = "";
    /**
     * Constructor por defecto.
     */
    public ReasignacionTramiteHelper() {
        super();
        iniciador();
    }
    /**
     * Este método inicializa las variables del helper.
     */
    public final void iniciador() {
        setBusquedaTareaVO(new BusquedaTareaVO());
        setListaTareas(new ArrayList<TareaVO>());
        setListaUsuariosRolVO(new ArrayList<UsuarioRolVO>());
        selectedUsuarioRolVO = new UsuarioRolVO();
        setReasignacionTarea(new ReasignacionTarea());
        mediomReasignacionDataModel = new ReasignacionDataModel(listaUsuariosRolVO);
        listaTareasFiltradas = new ArrayList<TareaVO>();
    }

    /**
     * @return the tarea
     */
    public Tarea getTarea() {
        return tarea;
    }

    /**
     * @param tarea the tarea to set
     */
    public void setTarea(final Tarea tarea) {
        this.tarea = tarea;
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
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the listaUsuariosRolVO
     */
    public List<UsuarioRolVO> getListaUsuariosRolVO() {
        return listaUsuariosRolVO;
    }

    /**
     * @param listaUsuariosRolVO the listaUsuariosRolVO to set
     */
    public void setListaUsuariosRolVO(final List<UsuarioRolVO> listaUsuariosRolVO) {
        this.listaUsuariosRolVO = listaUsuariosRolVO;
    }

    /**
     * @return the selectedUsuarioRolVO
     */
    public UsuarioRolVO getSelectedUsuarioRolVO() {
        return selectedUsuarioRolVO;
    }

    /**
     * @param selectedUsuarioRolVO the selectedUsuarioRolVO to set
     */
    public void setSelectedUsuarioRolVO(final UsuarioRolVO selectedUsuarioRolVO) {
        this.selectedUsuarioRolVO = selectedUsuarioRolVO;
    }

    /**
     * @return the reasignacionTarea
     */
    public ReasignacionTarea getReasignacionTarea() {
        return reasignacionTarea;
    }

    /**
     * @param reasignacionTarea the reasignacionTarea to set
     */
    public void setReasignacionTarea(final ReasignacionTarea reasignacionTarea) {
        this.reasignacionTarea = reasignacionTarea;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the mediomReasignacionDataModel
     */
    public ReasignacionDataModel getMediomReasignacionDataModel() {
        return mediomReasignacionDataModel;
    }

    public List<TareaVO> getListaTareasFiltradas() {
        return listaTareasFiltradas;
    }

    public void setListaTareasFiltradas(List<TareaVO> listaTareasFiltradas) {
        this.listaTareasFiltradas = listaTareasFiltradas;
    }
}
