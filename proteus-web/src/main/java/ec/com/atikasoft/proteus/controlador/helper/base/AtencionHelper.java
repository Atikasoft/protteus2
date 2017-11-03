/*
 *  AtencionHelper.java
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
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper.base;

import ec.com.atikasoft.proteus.modelo.Detalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.modelo.TramiteBitacora;
import ec.com.atikasoft.proteus.vo.TramiteFormularioVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 * Helper para la Atencion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class AtencionHelper implements Serializable {

    /**
     * Comentario de la actividad.
     */
    private String comentario = null;

    /**
     * Mensajes extras.
     */
    private String mensajes;

    /**
     * Movimiento de transaccion.
     */
    private Movimiento movimiento = new Movimiento();

    /**
     * VO de transaccion.
     */
    private TramiteFormularioVO tramiteFormularioVO = new TramiteFormularioVO();

    /**
     * Lista de opciones para la actividad.
     */
    private List<SelectItem> opciones = new ArrayList<SelectItem>();

    /**
     * Lista de historico del tramite.
     */
    private List<Detalle> listaDetalles = new ArrayList<Detalle>();

    /**
     * Titulo del proceso.
     */
    private String titulo;

    /**
     * Objeto de tarea.
     */
    private Tarea tarea;

    /**
     * private TramiteBitacora tramiteBitacora.
     */
    private TramiteBitacora tramiteBitacora = new TramiteBitacora();

    /**
     * Bandera para el control de opciones.
     */
    private Boolean automatico = Boolean.FALSE;

    /**
     * Id de la transicion.
     */
    private Long transicionId;

    /**
     * Id de la tarea.
     */
    private Long tareaId;

    /**
     * Constructor por defecto.
     */
    public AtencionHelper() {
        super();
    }

    /**
     * @return the opciones
     */
    public List<SelectItem> getOpciones() {
        return opciones;
    }

    /**
     * @param opciones the opciones to set
     */
    public void setOpciones(final List<SelectItem> opciones) {
        this.opciones = opciones;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the automatico
     */
    public Boolean getAutomatico() {
        return automatico;
    }

    /**
     * @param automatico the automatico to set
     */
    public void setAutomatico(final Boolean automatico) {
        this.automatico = automatico;
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
     * @return the transicionId
     */
    public Long getTransicionId() {
        return transicionId;
    }

    /**
     * @param transicionId the transicionId to set
     */
    public void setTransicionId(final Long transicionId) {
        this.transicionId = transicionId;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(final String comentario) {
        this.comentario = comentario;
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
     * @return the tareaId
     */
    public Long getTareaId() {
        return tareaId;
    }

    /**
     * @param tareaId the tareaId to set
     */
    public void setTareaId(final Long tareaId) {
        this.tareaId = tareaId;
    }

    /**
     * @return the listaDetalles
     */
    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    /**
     * @param listaDetalles the listaDetalles to set
     */
    public void setListaDetalles(final List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    /**
     * @return the tramiteBitacora
     */
    public TramiteBitacora getTramiteBitacora() {
        return tramiteBitacora;
    }

    /**
     * @param tramiteBitacora the tramiteBitacora to set
     */
    public void setTramiteBitacora(final TramiteBitacora tramiteBitacora) {
        this.tramiteBitacora = tramiteBitacora;
    }

    /**
     * @return the mensajes
     */
    public String getMensajes() {
        return mensajes;
    }

    /**
     * @param mensajes the mensajes to set
     */
    public void setMensajes(final String mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }
}
