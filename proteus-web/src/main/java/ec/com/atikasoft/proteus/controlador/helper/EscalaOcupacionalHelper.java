/*
 *  EscalaOcupacionalHelper.java
 *  Quito - Ecuador
 *  20/09/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author LRordriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean (name = "escalaOcupacionalHelper")
@SessionScoped
public class EscalaOcupacionalHelper extends CatalogoHelper {
     /**
     * Variable para modificar/eliminar una escalaOcupacional.
     */
    private EscalaOcupacional escalaOcupacionalEditDelete;
    /**
     * Variable para listar las escalaOcupacionals.
     */
    private List<EscalaOcupacional> listaEscalaOcupacional;
    /**
     * Variable para listar las escalaOcupacionals por nemonicos.
     */
    private List<EscalaOcupacional> listaEscalaOcupacionalCodigo = new ArrayList<EscalaOcupacional>();
  
    /**
     * Constructor.
     */
    public EscalaOcupacionalHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la EscalaOcupacionalHelper.
     */
    public final void iniciador() {
       listaEscalaOcupacionalCodigo = new ArrayList<EscalaOcupacional>();
    }


    /**
     * @return the escalaOcupacionalEditDelete
     */
    public EscalaOcupacional getEscalaOcupacionalEditDelete() {
        return escalaOcupacionalEditDelete;
    }

    /**
     * @param escalaOcupacionalEditDelete the escalaOcupacionalEditDelete to set
     */
    public void setEscalaOcupacionalEditDelete(final EscalaOcupacional escalaOcupacionalEditDelete) {
        this.escalaOcupacionalEditDelete = escalaOcupacionalEditDelete;
    }

    /**
     * @return the listaEscalaOcupacional
     */
    public List<EscalaOcupacional> getListaEscalaOcupacional() {
        return listaEscalaOcupacional;
    }

    /**
     * @param listaEscalaOcupacional the listaEscalaOcupacional to set
     */
    public void setListaEscalaOcupacional(final List<EscalaOcupacional> listaEscalaOcupacional) {
        this.listaEscalaOcupacional = listaEscalaOcupacional;
    }

     /**
     * @return the listaEscalaOcupacionalCodigo
     */
    public List<EscalaOcupacional> getListaEscalaOcupacionalCodigo() {
        return listaEscalaOcupacionalCodigo;
    }

    /**
     * @param listaEscalaOcupacionalCodigo the listaEscalaOcupacionalCodigo to set
     */
    public void setListaEscalaOcupacionalCodigo(List<EscalaOcupacional> listaEscalaOcupacionalCodigo) {
        this.listaEscalaOcupacionalCodigo = listaEscalaOcupacionalCodigo;
    }
    
    
    
}
