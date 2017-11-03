/*
 *  TramiteBorradorHelper.java
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
 *  15/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.enums.TipoOrdenamientoEnum;
import ec.com.atikasoft.proteus.modelo.Tramite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteBorradorHelper")
@SessionScoped
public class TramiteBorradorHelper implements Serializable {

    /**
     * Tramite seleccionado de la lista.
     */
    private Tramite tramite = new Tramite();

    /**
     * Comentario de la eliminacion.
     */
    private String comentario = "";

    /**
     * Lista de ordenar por.
     */
    private List<SelectItem> listaOrdenarPor;

    /**
     * Lista tipo de ordenamiento.
     */
    private List<SelectItem> listaTipoOrdenamiento;

    /**
     * Lista de tramites en borrador.
     */
    private List<Tramite> listaTramitesBorrador;

    /**
     * Campo para el ordenar por.
     */
    private String ordenarPor;

    /**
     * Campo para el tipo de ordenamiento.
     */
    private String tipoOrdenamiento;

    /**
     * Campo para la busqueda puede ser por descripcion o por movimiento.
     */
    private String token;
    
    private String clasesFiltrosSeleccionados;

    /**
     * Constructor por defecto.
     */
    public TramiteBorradorHelper() {
        super();
        init();
    }

    /**
     * Iniciador de variables.
     */
    public final void init() {
        listaOrdenarPor = new ArrayList<SelectItem>();
        listaTipoOrdenamiento = new ArrayList<SelectItem>();
        listaTramitesBorrador = new ArrayList<Tramite>();
        //ordenarPor = TramiteBorradorBuscarPorEnum.getColumna();
        tipoOrdenamiento = TipoOrdenamientoEnum.ASCENDENTE.getCodigo();
        token = "";
    }

    /**
     * @return the listaOrdenarPor
     */
    public List<SelectItem> getListaOrdenarPor() {
        return listaOrdenarPor;
    }

    /**
     * @param listaOrdenarPor the listaOrdenarPor to set
     */
    public void setListaOrdenarPor(final List<SelectItem> listaOrdenarPor) {
        this.listaOrdenarPor = listaOrdenarPor;
    }

    /**
     * @return the listaTipoOrdenamiento
     */
    public List<SelectItem> getListaTipoOrdenamiento() {
        return listaTipoOrdenamiento;
    }

    /**
     * @param listaTipoOrdenamiento the listaTipoOrdenamiento to set
     */
    public void setListaTipoOrdenamiento(final List<SelectItem> listaTipoOrdenamiento) {
        this.listaTipoOrdenamiento = listaTipoOrdenamiento;
    }

    /**
     * @return the listaTramitesBorrador
     */
    public List<Tramite> getListaTramitesBorrador() {
        return listaTramitesBorrador;
    }

    /**
     * @param listaTramitesBorrador the listaTramitesBorrador to set
     */
    public void setListaTramitesBorrador(final List<Tramite> listaTramitesBorrador) {
        this.listaTramitesBorrador = listaTramitesBorrador;
    }

    /**
     * @return the ordenarPor
     */
    public String getOrdenarPor() {
        return ordenarPor;
    }

    /**
     * @param ordenarPor the ordenarPor to set
     */
    public void setOrdenarPor(final String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    /**
     * @return the tipoOrdenamiento
     */
    public String getTipoOrdenamiento() {
        return tipoOrdenamiento;
    }

    /**
     * @param tipoOrdenamiento the tipoOrdenamiento to set
     */
    public void setTipoOrdenamiento(final String tipoOrdenamiento) {
        this.tipoOrdenamiento = tipoOrdenamiento;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(final String token) {
        this.token = token;
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

    public String getClasesFiltrosSeleccionados() {
        return clasesFiltrosSeleccionados;
    }

    public void setClasesFiltrosSeleccionados(String clasesFiltrosSeleccionados) {
        this.clasesFiltrosSeleccionados = clasesFiltrosSeleccionados;
    }
}
