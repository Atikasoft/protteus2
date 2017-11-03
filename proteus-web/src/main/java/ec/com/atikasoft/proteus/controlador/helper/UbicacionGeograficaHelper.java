/*
 *  UbicacionGeograficaHelper.java
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
 *  20/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.UbicacionGeografica;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "ubicacionGeograficaHelper")
@SessionScoped
public class UbicacionGeograficaHelper extends CatalogoHelper {

    private TreeNode root;

    private TreeNode selectedNode;

    /**
     * daora los nuevos.
     */
    private UbicacionGeografica ugN;

    /**
     * ver nodo selecciones.
     */
    private int nodeSeleccionado;

    /**
     * variables para ubicacion geográfica.
     */
    private List<UbicacionGeografica> listaUbicacionGeografica;

    /**
     * obj de la clase.
     */
    private UbicacionGeografica ubicacionGeografica;

    /**
     * lista de los tipos de ubicacion geografica.
     */
    private List<SelectItem> listaTipoUbicacionGeografica;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<UbicacionGeografica> listaAlertaNemonico;

    /**
     * Constructor por defecto.
     */
    public UbicacionGeograficaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {
        listaUbicacionGeografica = new ArrayList<UbicacionGeografica>();
        ubicacionGeografica = new UbicacionGeografica();
        listaTipoUbicacionGeografica = new ArrayList<SelectItem>();
        ugN = new UbicacionGeografica();
        listaAlertaNemonico = new ArrayList<UbicacionGeografica>();
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(final TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedNode
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     * @param selectedNode the selectedNode to set
     */
    public void setSelectedNode(final TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    /**
     * @return the listaUbicacionGeografica
     */
    public List<UbicacionGeografica> getListaUbicacionGeografica() {
        return listaUbicacionGeografica;
    }

    /**
     * @param listaUbicacionGeografica the listaUbicacionGeografica to set
     */
    public void setListaUbicacionGeografica(final List<UbicacionGeografica> listaUbicacionGeografica) {
        this.listaUbicacionGeografica = listaUbicacionGeografica;
    }

    /**
     * @return the ubicacionGeografica
     */
    public UbicacionGeografica getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    /**
     * @param ubicacionGeografica the ubicacionGeografica to set
     */
    public void setUbicacionGeografica(final UbicacionGeografica ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    /**
     * @return the listaTipoUbicacionGeografica
     */
    public List<SelectItem> getListaTipoUbicacionGeografica() {
        return listaTipoUbicacionGeografica;
    }

    /**
     * @param listaTipoUbicacionGeografica the listaTipoUbicacionGeografica to set
     */
    public void setListaTipoUbicacionGeografica(List<SelectItem> listaTipoUbicacionGeografica) {
        this.listaTipoUbicacionGeografica = listaTipoUbicacionGeografica;
    }

    /**
     * @return the ugN
     */
    public UbicacionGeografica getUgN() {
        return ugN;
    }

    /**
     * @param ugN the ugN to set
     */
    public void setUgN(final UbicacionGeografica ugN) {
        this.ugN = ugN;
    }

    /**
     * @return the nodeSeleccionado
     */
    public int getNodeSeleccionado() {
        return nodeSeleccionado;
    }

    /**
     * @param nodeSeleccionado the nodeSeleccionado to set
     */
    public void setNodeSeleccionado(final int nodeSeleccionado) {
        this.nodeSeleccionado = nodeSeleccionado;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<UbicacionGeografica> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<UbicacionGeografica> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
