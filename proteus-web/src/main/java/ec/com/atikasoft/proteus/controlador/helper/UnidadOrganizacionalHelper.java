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
import ec.com.atikasoft.proteus.modelo.Reloj;
import ec.com.atikasoft.proteus.modelo.RelojUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "unidadOrganizacionalHelper")
@SessionScoped
public class UnidadOrganizacionalHelper extends CatalogoHelper {

    private TreeNode root;

    private TreeNode selectedNode;

    /**
     * Unidad organizaciona.
     */
    private UnidadOrganizacional uo;

    /**
     * Unidad organizacional superior.
     */
    private UnidadOrganizacional uoSuperior;

    /**
     * ver nodo selecciones.
     */
    private int nodeSeleccionado;

    /**
     * variables para ubicacion geográfica.
     */
    private List<UnidadOrganizacional> listaUnidadOrganizacional;

    /**
     * variables para ubicacion geográfica.
     */
    private List<UnidadOrganizacional> listaUnidadOrganizacionalIdPadre;

    /**
     * obj de la clase.
     */
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<UnidadOrganizacional> listaAlertaNemonico;

    /**
     * Lista de grupos presupuestarios.
     */
    private List<SelectItem> listaGruposPresupuestarios;

    /**
     * Lista de registros de relojesUnidadesOrganizacionales asociados a la uniad organizacional seleccionada
     */
    private List<RelojUnidadOrganizacional> relojesUnidadesOrganizaccionesActuales;

    /**
     * Lista de relojes vigentes
     */
    private List<Reloj> relojesVigentes;

    /**
     * Modelo que contiene las listas de asigancion de relojes
     */
    private DualListModel<Reloj> seleccionRelojes;

    /**
     * Lista de relojes disponibles para asignar
     */
    private List<Reloj> relojesAsignables;

    /**
     * Lista de relojes ya asignados a la unidad organizacional
     */
    private List<Reloj> relojesAsignados;

    /**
     * Constructor por defecto.
     */
    public UnidadOrganizacionalHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {
        setListaUnidadOrganizacional(new ArrayList<UnidadOrganizacional>());
        setUnidadOrganizacional(new UnidadOrganizacional());
        setUo(new UnidadOrganizacional());
        setUoSuperior(new UnidadOrganizacional());
        listaUnidadOrganizacionalIdPadre = new ArrayList<>();
        listaAlertaNemonico = new ArrayList<>();
        listaGruposPresupuestarios = new ArrayList<>();
        relojesUnidadesOrganizaccionesActuales = new ArrayList<>();
        relojesVigentes = new ArrayList<>();
        seleccionRelojes = new DualListModel<>();
        relojesAsignables = new ArrayList<>();
        relojesAsignados = new ArrayList<>();
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
     * @return the uo
     */
    public UnidadOrganizacional getUo() {
        return uo;
    }

    /**
     * @param uo the uo to set
     */
    public void setUo(final UnidadOrganizacional uo) {
        this.uo = uo;
    }

    /**
     * @return the listaUnidadOrganizacional
     */
    public List<UnidadOrganizacional> getListaUnidadOrganizacional() {
        return listaUnidadOrganizacional;
    }

    /**
     * @param listaUnidadOrganizacional the listaUnidadOrganizacional to set
     */
    public void setListaUnidadOrganizacional(final List<UnidadOrganizacional> listaUnidadOrganizacional) {
        this.listaUnidadOrganizacional = listaUnidadOrganizacional;
    }

    /**
     * @return the UnidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional
     */
    public void setUnidadOrganizacional(final UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the listaUnidadOrganizacionalIdPadre
     */
    public List<UnidadOrganizacional> getListaUnidadOrganizacionalIdPadre() {
        return listaUnidadOrganizacionalIdPadre;
    }

    /**
     * @param listaUnidadOrganizacionalIdPadre the listaUnidadOrganizacionalIdPadre to set
     */
    public void setListaUnidadOrganizacionalIdPadre(List<UnidadOrganizacional> listaUnidadOrganizacionalIdPadre) {
        this.listaUnidadOrganizacionalIdPadre = listaUnidadOrganizacionalIdPadre;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<UnidadOrganizacional> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(final List<UnidadOrganizacional> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }

    /**
     * @return the uoSuperior
     */
    public UnidadOrganizacional getUoSuperior() {
        return uoSuperior;
    }

    /**
     * @param uoSuperior the uoSuperior to set
     */
    public void setUoSuperior(final UnidadOrganizacional uoSuperior) {
        this.uoSuperior = uoSuperior;
    }

    /**
     * @return the listaGruposPresupuestarios
     */
    public List<SelectItem> getListaGruposPresupuestarios() {
        return listaGruposPresupuestarios;
    }

    /**
     * @param listaGruposPresupuestarios the listaGruposPresupuestarios to set
     */
    public void setListaGruposPresupuestarios(final List<SelectItem> listaGruposPresupuestarios) {
        this.listaGruposPresupuestarios = listaGruposPresupuestarios;
    }

    /**
     *
     * @return
     */
    public List<RelojUnidadOrganizacional> getRelojesUnidadesOrganizaccionesActuales() {
        return relojesUnidadesOrganizaccionesActuales;
    }

    /**
     *
     * @param relojesUnidadesOrganizaccionesActuales
     */
    public void setRelojesUnidadesOrganizaccionesActuales(
            List<RelojUnidadOrganizacional> relojesUnidadesOrganizaccionesActuales) {
        this.relojesUnidadesOrganizaccionesActuales = relojesUnidadesOrganizaccionesActuales;
    }

    /**
     *
     * @return
     */
    public List<Reloj> getRelojesVigentes() {
        return relojesVigentes;
    }

    /**
     *
     * @param opcionesReloj
     */
    public void setRelojesVigentes(List<Reloj> opcionesReloj) {
        this.relojesVigentes = opcionesReloj;
    }

    /**
     *
     * @return
     */
    public DualListModel<Reloj> getSeleccionRelojes() {
        return seleccionRelojes;
    }

    /**
     *
     * @param seleccionRelojes
     */
    public void setSeleccionRelojes(DualListModel<Reloj> seleccionRelojes) {
        this.seleccionRelojes = seleccionRelojes;
    }

    /**
     *
     * @return
     */
    public List<Reloj> getRelojesAsignables() {
        return relojesAsignables;
    }

    /**
     *
     * @param relojesAsignables
     */
    public void setRelojesAsignables(List<Reloj> relojesAsignables) {
        this.relojesAsignables = relojesAsignables;
    }

    /**
     *
     * @return
     */
    public List<Reloj> getRelojesAsignados() {
        return relojesAsignados;
    }

    /**
     *
     * @param relojesAsignados
     */
    public void setRelojesAsignados(List<Reloj> relojesAsignados) {
        this.relojesAsignados = relojesAsignados;
    }
    
    /**
     * Indica si ya se ha asignado algún reloj a la Unidad Organizacional en la pantalla
     * @return 
     */
    public Boolean getHayRelojesAsignadosEnLaVista() {
        if (seleccionRelojes.getTarget() != null && !seleccionRelojes.getTarget().isEmpty()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
