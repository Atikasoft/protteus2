/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Requisito;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;



/**
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name="requisitoHelper")
@SessionScoped
public class RequisitoHelper extends CatalogoHelper{
    /**
     * Entidad de Requisito.
     */
    private Requisito requisito;
    
    /**
     * Entidad de Requisito para edicion y Eliminacion.
     */
    private Requisito requisitoEditDelete;
    
    /**
     * Lista de Requisitos.
     */
    private List<Requisito> listaRequisito;
    
    /**
     * Lista de Grupos.
     */
    private List<SelectItem> listaGrupo;
   
    /**
     * Lista de Nemónico de Requisitos.
     */
    private List<Requisito> listaRequisitoNemonico;
    
     /**
     * Constructor.
     */
    public RequisitoHelper(){
        super();
        init();
    }
    
    /**
     * Este método Inicializa las variables de Requisito.
     */
    public final void init(){
        requisito=new Requisito();
        requisitoEditDelete=new Requisito();
        listaRequisito=new ArrayList<Requisito>();
        listaGrupo=new ArrayList<SelectItem>();
        listaRequisitoNemonico=new ArrayList<Requisito>();
    }
    /**
     * @return the requisito
     */
    public Requisito getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(Requisito requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the requisitoEditDelete
     */
    public Requisito getRequisitoEditDelete() {
        return requisitoEditDelete;
    }

    /**
     * @param requisitoEditDelete the requisitoEditDelete to set
     */
    public void setRequisitoEditDelete(Requisito requisitoEditDelete) {
        this.requisitoEditDelete = requisitoEditDelete;
    }

    /**
     * @return the listaRequisito
     */
    public List<Requisito> getListaRequisito() {
        return listaRequisito;
    }

    /**
     * @param listaRequisito the listaRequisito to set
     */
    public void setListaRequisito(List<Requisito> listaRequisito) {
        this.listaRequisito = listaRequisito;
    }

    /**
     * @return the listaGrupo
     */
    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * @param listaGrupo the listaGrupo to set
     */
    public void setListaGrupo(List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * @return the listaRequisitoNemonico
     */
    public List<Requisito> getListaRequisitoNemonico() {
        return listaRequisitoNemonico;
    }

    /**
     * @param listaRequisitoNemonico the listaRequisitoNemonico to set
     */
    public void setListaRequisitoNemonico(List<Requisito> listaRequisitoNemonico) {
        this.listaRequisitoNemonico = listaRequisitoNemonico;
    }
    
}
