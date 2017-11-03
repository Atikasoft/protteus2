/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.CodigoContable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "codigoContableHelper")
@SessionScoped
public class CodigoContableHelper extends CatalogoHelper {

    /**
     * Entidad Principal.
     */
    private CodigoContable codigoContable;
    /**
     * Entidad de Transacción.
     */
    private CodigoContable codigoContableEditDelete;

    /**
     * Lista de Codigos Contables.
     */
    private List<CodigoContable> listaCodigoContable;
    
    /**
     * Lista de CodigoContables por nemónico.
     */
     //private List<CodigoContable> listaCodigoContableCodigo;

    /**
     * Constructor por defecto.
     */
    public CodigoContableHelper() {
        super();
        iniciador();
    }

    /**
     * Este método Inicializa las variables del Helper.
     */
    public final void iniciador() {
        codigoContable=new CodigoContable();
        codigoContableEditDelete=new CodigoContable();
        listaCodigoContable=new ArrayList<CodigoContable>();    
        //listaCodigoContableCodigo=new ArrayList<CodigoContable>();
        setEsNuevo(Boolean.TRUE);
    }

    /**
     * @return the codigoContable
     */
    public CodigoContable getCodigoContable() {
        return codigoContable;
    }

    /**
     * @param codigoContable the codigoContable to set
     */
    public void setCodigoContable(CodigoContable codigoContable) {
        this.codigoContable = codigoContable;
    }

    /**
     * @return the codigoContableEditDelete
     */
    public CodigoContable getCodigoContableEditDelete() {
        return codigoContableEditDelete;
    }

    /**
     * @param codigoContableEditDelete the codigoContableEditDelete to set
     */
    public void setCodigoContableEditDelete(CodigoContable codigoContableEditDelete) {
        this.codigoContableEditDelete = codigoContableEditDelete;
    }

    /**
     * @return the listaCodigoContable
     */
    public List<CodigoContable> getListaCodigoContable() {
        return listaCodigoContable;
    }

    /**
     * @param listaCodigoContable the listaCodigoContable to set
     */
    public void setListaCodigoContable(List<CodigoContable> listaCodigoContable) {
        this.listaCodigoContable = listaCodigoContable;
    }

    /**
     * @return the listaCodigoContableCodigo
     */
//    public List<CodigoContable> getListaCodigoContableCodigo() {
//        return listaCodigoContableCodigo;
//    }

    /**
     * @param listaCodigoContableCodigo the listaCodigoContableCodigo to set
     */
//    public void setListaCodigoContableCodigo(List<CodigoContable> listaCodigoContableCodigo) {
//        this.listaCodigoContableCodigo = listaCodigoContableCodigo;
//    }
}
