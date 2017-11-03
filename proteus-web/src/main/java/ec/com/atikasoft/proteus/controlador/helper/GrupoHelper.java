/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Grupo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "grupoHelper")
@SessionScoped
public class GrupoHelper extends CatalogoHelper {

    /**
     * Entidad Principal.
     */
    private Grupo grupo;
    /**
     * Entidad de Transacción.
     */
    private Grupo grupoEditDelete;

    /**
     * Lista de Grupos.
     */
    private List<Grupo> listaGrupo;
    
    /**
     * Lista de Grupos por nemónico.
     */
     private List<Grupo> listaGrupoNemonico;

    /**
     * Constructor por defecto.
     */
    public GrupoHelper() {
        super();
        iniciador();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    public final void iniciador() {
        grupo=new Grupo();
        grupoEditDelete=new Grupo();
        listaGrupo=new ArrayList<Grupo>();    
        listaGrupoNemonico=new ArrayList<Grupo>();

    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the grupoEditDelete
     */
    public Grupo getGrupoEditDelete() {
        return grupoEditDelete;
    }

    /**
     * @param grupoEditDelete the grupoEditDelete to set
     */
    public void setGrupoEditDelete(Grupo grupoEditDelete) {
        this.grupoEditDelete = grupoEditDelete;
    }

    /**
     * @return the listaGrupo
     */
    public List<Grupo> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * @param listaGrupo the listaGrupo to set
     */
    public void setListaGrupo(List<Grupo> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * @return the listaGrupoNemonico
     */
    public List<Grupo> getListaGrupoNemonico() {
        return listaGrupoNemonico;
    }

    /**
     * @param listaGrupoNemonico the listaGrupoNemonico to set
     */
    public void setListaGrupoNemonico(List<Grupo> listaGrupoNemonico) {
        this.listaGrupoNemonico = listaGrupoNemonico;
    }
}
