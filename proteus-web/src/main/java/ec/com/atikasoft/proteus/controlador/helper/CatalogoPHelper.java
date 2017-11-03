/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.TipoCatalogo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Atikasoft
 */
@ManagedBean(name = "catalogoPHelper")
@SessionScoped
public class CatalogoPHelper extends CatalogoHelper {

    /**
     * ENTIDAD DE CATALOGO.
     */
    private Catalogo catalogo;

    /**
     * ENTIDAD PARA ELIMINAR.
     */
    private Catalogo catalogoEliminar;

    /**
     * LISTA DE CATALOGOS.
     */
    private List<Catalogo> listaCatalogos;

    /**
     * LISTA DE CATALOGOS.
     */
    private List<Catalogo> listaAlertaNemonico;

    /**
     * LISTA PARA LLENAR EL COMBO DE TIPO DE CATALOGO.
     */
    private List<SelectItem> listaTipoCatalogo;

    /**
     * CONSTRUCTOR.
     */
    public CatalogoPHelper() {
        super();
        catalogo = new Catalogo();
        catalogo.setTipoCatalogo(new TipoCatalogo());
        catalogoEliminar = new Catalogo();
        listaCatalogos = new ArrayList<Catalogo>();
        listaTipoCatalogo = new ArrayList<SelectItem>();
        listaAlertaNemonico = new ArrayList<Catalogo>();
    }

    /**
     * @return the catalogo
     */
    public Catalogo getCatalogo() {
        return catalogo;
    }

    /**
     * @param catalogo the catalogo to set
     */
    public void setCatalogo(final Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * @return the listaCatalogos
     */
    public List<Catalogo> getListaCatalogos() {
        return listaCatalogos;
    }

    /**
     * @param listaCatalogos the listaCatalogos to set
     */
    public void setListaCatalogos(final List<Catalogo> listaCatalogos) {
        this.listaCatalogos = listaCatalogos;
    }

    /**
     * @return the catalogoEliminar
     */
    public Catalogo getCatalogoEliminar() {
        return catalogoEliminar;
    }

    /**
     * @param catalogoEliminar the catalogoEliminar to set
     */
    public void setCatalogoEliminar(final Catalogo catalogoEliminar) {
        this.catalogoEliminar = catalogoEliminar;
    }

    /**
     * @return the listaTipoCatalogo
     */
    public List<SelectItem> getListaTipoCatalogo() {
        return listaTipoCatalogo;
    }

    /**
     * @param listaTipoCatalogo the listaTipoCatalogo to set
     */
    public void setListaTipoCatalogo(final List<SelectItem> listaTipoCatalogo) {
        this.listaTipoCatalogo = listaTipoCatalogo;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<Catalogo> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<Catalogo> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
