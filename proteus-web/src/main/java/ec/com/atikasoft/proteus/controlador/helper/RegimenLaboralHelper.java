/*
 *  RegimenLaboralHelper.java
 *  Quito - Ecuador
 *  20/09/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.TreeNode;

/**
 *
 * @author LRordriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean (name = "regimenLaboralHelper")
@SessionScoped
public class RegimenLaboralHelper extends CatalogoHelper {
    /**
     * Variable para nuevo régimen Laboral.
     */
    private RegimenLaboral regimenLaboral;
    /**
     * Variable para modificar/eliminar registros régimen Laboral.
     */
    private RegimenLaboral regimenLaboralEditDelete;
    /**
     * Variable para listar el régimen Laboral.
     */
    private List<RegimenLaboral> listaRegimenLaboral;
    /**
     * Variable para listar las régimen Laborals por codigo.
     */
    private List<RegimenLaboral> listaRegimenLaboralCodigo =new ArrayList<RegimenLaboral>();
    
    
  /*
     * Raiz del arbol
     */
    private TreeNode root;
    
      /**
     * Item seleccionado del arbol
     */
    private TreeNode regimenSeleccionado;
    /**
     * Nivel del item seleccionado
     */
    private int nodoSeleccionado ;  
    /**
     * Variable para indicar que estamos en lista_regimen_laboral
     */
    private boolean esPaginaArbol;
   
    
    /**
     * Constructor.
     */
    public RegimenLaboralHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la RegimenLaboralHelper.
     */
    public final void iniciador() {
        setRegimenLaboral(new RegimenLaboral());        
        setRegimenLaboralEditDelete(new RegimenLaboral());
        setListaRegimenLaboral(new ArrayList<RegimenLaboral>());
        setEsPaginaArbol(true);
     }

    /**
     * @return the regimenLaboral
     */
    public RegimenLaboral getRegimenLaboral() {
        return regimenLaboral;
    }

    /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenLaboral(final RegimenLaboral regimenLaboral) {
        this.regimenLaboral = regimenLaboral;
    }

    /**
     * @return the regimenLaboralEditDelete
     */
    public RegimenLaboral getRegimenLaboralEditDelete() {
        return regimenLaboralEditDelete;
    }

    /**
     * @param regimenLaboralEditDelete the regimenLaboralEditDelete to set
     */
    public void setRegimenLaboralEditDelete(final RegimenLaboral regimenLaboralEditDelete) {
        this.regimenLaboralEditDelete = regimenLaboralEditDelete;
    }

    /**
     * @return the listaRegimenLaboral
     */
    public List<RegimenLaboral> getListaRegimenLaboral() {
        return listaRegimenLaboral;
    }

    /**
     * @param listaRegimenLaboral the listaRegimenLaboral to set
     */
    public void setListaRegimenLaboral(final List<RegimenLaboral> listaRegimenLaboral) {
        this.listaRegimenLaboral = listaRegimenLaboral;
    }

     /**
     * @return the listaRegimenLaboralCodigo
     */
    public List<RegimenLaboral> getListaRegimenLaboralCodigo() {
        return listaRegimenLaboralCodigo;
    }

    /**
     * @param listaRegimenLaboralCodigo the listaRegimenLaboralCodigo to set
     */
    public void setListaRegimenLaboralCodigo(List<RegimenLaboral> listaRegimenLaboralCodigo) {
        this.listaRegimenLaboralCodigo = listaRegimenLaboralCodigo;
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
    public void setRoot(TreeNode root) {
        this.root = root;
    }
     /**
     * @return the regimenSeleccionado
     */
     public TreeNode getRegimenSeleccionado() {
        return regimenSeleccionado;
    }
  /**
     * @param regimenLaboral the regimenLaboral to set
     */
    public void setRegimenSeleccionado(TreeNode regimenSeleccionado) {
        this.regimenSeleccionado = regimenSeleccionado;
    }
 /**
     * @return the nodoSeleccionado
     */
    public int getNodoSeleccionado() {
        return nodoSeleccionado;
    }
  /**
     * @param nodoSeleccionado the nodoSeleccionado to set
     */
    public void setNodoSeleccionado(int nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }
/**
     * @return the esPaginaArbol
     */
    public boolean isEsPaginaArbol() {
        return esPaginaArbol;
    }
  /**
     * @param esPaginaArbol the esPaginaArbol to set
     */
    public void setEsPaginaArbol(boolean esPaginaArbol) {
        this.esPaginaArbol = esPaginaArbol;
    }

}
