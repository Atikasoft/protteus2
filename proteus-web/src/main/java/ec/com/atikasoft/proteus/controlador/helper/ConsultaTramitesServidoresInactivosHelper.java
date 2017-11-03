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
 * @author Leydis Garzon
 */
@ManagedBean(name = "consultaTramitesServidoresInactivosHelper")
@SessionScoped
public class ConsultaTramitesServidoresInactivosHelper implements Serializable {

    /**
     * Tarea.
     */
    private TareaVO tareaVO;

    /**
     * Lista de tareas.
     */
    private List<TareaVO> listaTareas;

    /**
     * Lista de tareas filtradas.
     */
    private List<TareaVO> listaTareasFiltradas;

    /**
     * Justificación de anulación del trámite
     */
    private String justificacion;

    /**
     * Constructor por defecto.
     */
    public ConsultaTramitesServidoresInactivosHelper() {
        iniciador();
    }

    /**
     * Este método inicializa las variables del helper.
     */
    public final void iniciador() {
        listaTareas = new ArrayList<>();
        tareaVO = new TareaVO();
        listaTareasFiltradas = new ArrayList<>();
    }

    /**
     * @return the tareaVO
     */
    public TareaVO getTareaVO() {
        return tareaVO;
    }

    /**
     * @param tareaVO the tareaVO to set
     */
    public void setTareaVO(final TareaVO tareaVO) {
        this.tareaVO = tareaVO;
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

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public List<TareaVO> getListaTareasFiltradas() {
        return listaTareasFiltradas;
    }

    public void setListaTareasFiltradas(List<TareaVO> listaTareasFiltradas) {
        this.listaTareasFiltradas = listaTareasFiltradas;
    }

    
}
