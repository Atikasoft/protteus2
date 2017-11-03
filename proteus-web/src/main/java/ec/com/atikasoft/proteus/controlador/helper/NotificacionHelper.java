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
import ec.com.atikasoft.proteus.enums.OpcionDestinatarioNotificacionEnum;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DestinatarioNotificacion;
import ec.com.atikasoft.proteus.modelo.Notificacion;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "notificacionHelper")
@SessionScoped
public class NotificacionHelper extends CatalogoHelper {

    /**
     * Indica si se muestran las notificaciones recibidas o las enviadas
     */
    private Boolean mostrarRecibidas;
    /**
     * Indica si se muestran los remitentes
     */
    private Boolean mostrarDestinatarios;
    /**
     *
     */
    private String opcionEnviadasRecibidas;
    /**
     *
     */
    private List<SelectItem> opcionesEnviadasRecibidas;
    /**
     *
     */
    public static final String OPCION_ENVIADAS = "ENV";
    /**
     *
     */
    public static final String OPCION_RECIBIDAS = "REC";
    /**
     *
     */
    private String opcionTipoRemitenteSeleccionada;
    /**
     *
     */
    private List<SelectItem> listaOpcionesTipoRemitente;
    /**
     *
     */
    private Long opcionUnidadDesconcentradaSeleccionada;
    /**
     *
     */
    private List<Desconcentrado> unidadesDesconcetradasSeleccionadas;
    /**
     *
     */
    private List<SelectItem> opcionesUnidadesDesconcentradas;
    /**
     *
     */
    private List<Desconcentrado> listaUnidadesDesconcentradas;
    /**
     *
     */
    private Long opcionUnidadRRHHSeleccionada;
    /**
     *
     */
    private List<SelectItem> opcionesUnidadesRRHH;
    /**
     *
     */
    private List<UnidadOrganizacional> listaUnidadesRHH;
    /**
     * Entidad notificacion
     */
    private Notificacion notificacion;
    /**
     * Entidad destinatario notificacion
     */
    private DestinatarioNotificacion destinatarioNotificacion;
    /**
     *
     */
    private List<Notificacion> notificacionesEnviadas;
    /**
     *
     */
    private List<DestinatarioNotificacion> notificacionesRecibidas;

    /**
     *
     */
    private List<Servidor> listaDestinatarios;

    /**
     *
     */
    private Map<Long, Long> mapDestinatariosIds;

    /**
     * Indicador que indicar si el usuario puede enviar notificaciones.
     */
    private Boolean enviarNotificaciones;

    /**
     * Constructor por defecto.
     */
    public NotificacionHelper() {
        super();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    @PostConstruct
    public final void iniciador() {
        listaOpcionesTipoRemitente = new ArrayList<>();
        unidadesDesconcetradasSeleccionadas = new ArrayList<>();
        opcionesUnidadesDesconcentradas = new ArrayList<>();
        listaUnidadesDesconcentradas = new ArrayList<>();
        opcionesUnidadesRRHH = new ArrayList<>();
        listaUnidadesRHH = new ArrayList<>();
        notificacionesEnviadas = new ArrayList<>();
        notificacionesRecibidas = new ArrayList<>();

        opcionesEnviadasRecibidas = new ArrayList<>();
        opcionesEnviadasRecibidas.add(new SelectItem(OPCION_RECIBIDAS, "RECIBIDAS"));
        opcionesEnviadasRecibidas.add(new SelectItem(OPCION_ENVIADAS, "ENVIADAS"));

        listaDestinatarios = new ArrayList<>();
        mapDestinatariosIds = new HashMap<>();
        enviarNotificaciones = Boolean.FALSE;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarRecibidas() {
        return mostrarRecibidas;
    }

    /**
     *
     * @param mostrarRecibidas
     */
    public void setMostrarRecibidas(Boolean mostrarRecibidas) {
        this.mostrarRecibidas = mostrarRecibidas;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarDestinatarios() {
        return mostrarDestinatarios;
    }

    /**
     *
     * @param mostrarDestinatarios
     */
    public void setMostrarDestinatarios(Boolean mostrarDestinatarios) {
        this.mostrarDestinatarios = mostrarDestinatarios;
    }

    /**
     *
     * @return
     */
    public Notificacion getNotificacion() {
        return notificacion;
    }

    /**
     *
     * @param notificacion
     */
    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    /**
     *
     * @return
     */
    public DestinatarioNotificacion getDestinatarioNotificacion() {
        return destinatarioNotificacion;
    }

    /**
     *
     * @param destinatarioNotificacion
     */
    public void setDestinatarioNotificacion(DestinatarioNotificacion destinatarioNotificacion) {
        this.destinatarioNotificacion = destinatarioNotificacion;
    }

    /**
     *
     * @return
     */
    public String getOpcionTipoRemitenteSeleccionada() {
        return opcionTipoRemitenteSeleccionada;
    }

    /**
     *
     * @param opcionTipoRemitenteSeleccionada
     */
    public void setOpcionTipoRemitenteSeleccionada(String opcionTipoRemitenteSeleccionada) {
        this.opcionTipoRemitenteSeleccionada = opcionTipoRemitenteSeleccionada;
    }

    /**
     *
     * @return
     */
    public List<Desconcentrado> getUnidadesDesconcetradasSeleccionadas() {
        return unidadesDesconcetradasSeleccionadas;
    }

    /**
     *
     * @param unidadesDesconcetradasSeleccionadas
     */
    public void setUnidadesDesconcetradasSeleccionadas(List<Desconcentrado> unidadesDesconcetradasSeleccionadas) {
        this.unidadesDesconcetradasSeleccionadas = unidadesDesconcetradasSeleccionadas;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getListaOpcionesTipoRemitente() {
        return listaOpcionesTipoRemitente;
    }

    /**
     *
     * @param listaOpcionesTipoRemitente
     */
    public void setListaOpcionesTipoRemitente(List<SelectItem> listaOpcionesTipoRemitente) {
        this.listaOpcionesTipoRemitente = listaOpcionesTipoRemitente;
    }

    /**
     *
     * @return
     */
    public Long getOpcionUnidadDesconcentradaSeleccionada() {
        return opcionUnidadDesconcentradaSeleccionada;
    }

    /**
     *
     * @param opcionUnidadDesconcentradaSeleccionada
     */
    public void setOpcionUnidadDesconcentradaSeleccionada(Long opcionUnidadDesconcentradaSeleccionada) {
        this.opcionUnidadDesconcentradaSeleccionada = opcionUnidadDesconcentradaSeleccionada;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getOpcionesUnidadesDesconcentradas() {
        return opcionesUnidadesDesconcentradas;
    }

    /**
     *
     * @param opcionesUnidadesDesconcentradas
     */
    public void setOpcionesUnidadesDesconcentradas(List<SelectItem> opcionesUnidadesDesconcentradas) {
        this.opcionesUnidadesDesconcentradas = opcionesUnidadesDesconcentradas;
    }

    /**
     *
     * @return
     */
    public List<Desconcentrado> getListaUnidadesDesconcentradas() {
        return listaUnidadesDesconcentradas;
    }

    /**
     *
     * @param listaUnidadesDesconcentradas
     */
    public void setListaUnidadesDesconcentradas(List<Desconcentrado> listaUnidadesDesconcentradas) {
        this.listaUnidadesDesconcentradas = listaUnidadesDesconcentradas;
    }

    /**
     *
     * @return
     */
    public Long getOpcionUnidadRRHHSeleccionada() {
        return opcionUnidadRRHHSeleccionada;
    }

    /**
     *
     * @param opcionUnidadRRHHSeleccionada
     */
    public void setOpcionUnidadRRHHSeleccionada(Long opcionUnidadRRHHSeleccionada) {
        this.opcionUnidadRRHHSeleccionada = opcionUnidadRRHHSeleccionada;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getOpcionesUnidadesRRHH() {
        return opcionesUnidadesRRHH;
    }

    /**
     *
     * @param opcionesUnidadesRRHH
     */
    public void setOpcionesUnidadesRRHH(List<SelectItem> opcionesUnidadesRRHH) {
        this.opcionesUnidadesRRHH = opcionesUnidadesRRHH;
    }

    /**
     *
     * @return
     */
    public List<UnidadOrganizacional> getListaUnidadesRHH() {
        return listaUnidadesRHH;
    }

    /**
     *
     * @param listaUnidadesRHH
     */
    public void setListaUnidadesRHH(List<UnidadOrganizacional> listaUnidadesRHH) {
        this.listaUnidadesRHH = listaUnidadesRHH;
    }

    /**
     *
     * @return
     */
    public List<Notificacion> getNotificacionesEnviadas() {
        return notificacionesEnviadas;
    }

    /**
     *
     * @param notificacionesEnviadas
     */
    public void setNotificacionesEnviadas(List<Notificacion> notificacionesEnviadas) {
        this.notificacionesEnviadas = notificacionesEnviadas;
    }

    /**
     *
     * @return
     */
    public List<DestinatarioNotificacion> getNotificacionesRecibidas() {
        return notificacionesRecibidas;
    }

    /**
     *
     * @param notificacionesRecibidas
     */
    public void setNotificacionesRecibidas(List<DestinatarioNotificacion> notificacionesRecibidas) {
        this.notificacionesRecibidas = notificacionesRecibidas;
    }

    /**
     *
     * @return
     */
    public String getOpcionEnviadasRecibidas() {
        return opcionEnviadasRecibidas;
    }

    /**
     *
     * @param opcionEnviadasRecibidas
     */
    public void setOpcionEnviadasRecibidas(String opcionEnviadasRecibidas) {
        this.opcionEnviadasRecibidas = opcionEnviadasRecibidas;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getOpcionesEnviadasRecibidas() {
        return opcionesEnviadasRecibidas;
    }

    /**
     *
     * @param opcionesEnviadasRecibidas
     */
    public void setOpcionesEnviadasRecibidas(List<SelectItem> opcionesEnviadasRecibidas) {
        this.opcionesEnviadasRecibidas = opcionesEnviadasRecibidas;
    }

    /**
     *
     * @return
     */
    public List<Servidor> getListaDestinatarios() {
        return listaDestinatarios;
    }

    /**
     *
     * @param listaDestinatarios
     */
    public void setListaDestinatarios(List<Servidor> listaDestinatarios) {
        this.listaDestinatarios = listaDestinatarios;
    }

    /**
     *
     * @return
     */
    public Map<Long, Long> getMapDestinatariosIds() {
        return mapDestinatariosIds;
    }

    /**
     *
     * @param mapDestinatariosIds
     */
    public void setMapDestinatariosIds(Map<Long, Long> mapDestinatariosIds) {
        this.mapDestinatariosIds = mapDestinatariosIds;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarOpcionTodos() {
        if (this.opcionTipoRemitenteSeleccionada != null) {
            return this.opcionTipoRemitenteSeleccionada.equals(OpcionDestinatarioNotificacionEnum.TODOS.getCodigo());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarOpcionesUnidadesDesconcentradas() {
        if (this.opcionTipoRemitenteSeleccionada != null) {
            return this.opcionTipoRemitenteSeleccionada.equals(OpcionDestinatarioNotificacionEnum.UNIDAD_DESCONCENTRADA.getCodigo());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarOpcionesServidoresEspecificos() {
        if (this.opcionTipoRemitenteSeleccionada != null) {
            return this.opcionTipoRemitenteSeleccionada.equals(OpcionDestinatarioNotificacionEnum.SERVIDORES_ESPECIFICOS.getCodigo());
        }
        return null;
    }

    /**
     *
     * @return
     */
    public Boolean getMostrarOpcionesUnidadesRRHH() {
        if (this.opcionTipoRemitenteSeleccionada != null) {
            return this.opcionTipoRemitenteSeleccionada.equals(OpcionDestinatarioNotificacionEnum.UNIDAD_RRHH.getCodigo());
        }
        return null;
    }

    /**
     * @return the enviarNotificaciones
     */
    public Boolean getEnviarNotificaciones() {
        return enviarNotificaciones;
    }

    /**
     * @param enviarNotificaciones the enviarNotificaciones to set
     */
    public void setEnviarNotificaciones(Boolean enviarNotificaciones) {
        this.enviarNotificaciones = enviarNotificaciones;
    }

}
