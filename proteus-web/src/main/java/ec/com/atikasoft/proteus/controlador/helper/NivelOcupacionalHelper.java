/*
 *  NivelOcupacionalHelper.java
 *  Quito - Ecuador
 *  20/09/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.enums.TiposCertificacionesPresupuestariasEnum;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author LRordriguez <liliana.rodriguez@marcasoft.ec>
 */
@ManagedBean (name = "nivelOcupacionalHelper")
@SessionScoped
public class NivelOcupacionalHelper extends CatalogoHelper {
     /**
     * Variable para modificar/eliminar una nivelOcupacional.
     */
    private NivelOcupacional nivelOcupacionalEditDelete;
     /**
     * Variable para listar las nivelOcupacionals por nemonicos.
     */
    private List<NivelOcupacional> listaNivelOcupacionalCodigo = new ArrayList<NivelOcupacional>();

    /**
     * Variable para listar las nivelOcupacionals por nemonicos.
     */
    private TiposCertificacionesPresupuestariasEnum[] tiposCertificaciones =  TiposCertificacionesPresupuestariasEnum.values();
  
    /**
     * Constructor.
     */
    public NivelOcupacionalHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la NivelOcupacionalHelper.
     */
    public final void iniciador() {
        setNivelOcupacionalEditDelete(new NivelOcupacional());
        listaNivelOcupacionalCodigo = new ArrayList<NivelOcupacional>();
    }

  

    /**
     * @return the nivelOcupacionalEditDelete
     */
    public NivelOcupacional getNivelOcupacionalEditDelete() {
        return nivelOcupacionalEditDelete;
    }

    /**
     * @param nivelOcupacionalEditDelete the nivelOcupacionalEditDelete to set
     */
    public void setNivelOcupacionalEditDelete(final NivelOcupacional nivelOcupacionalEditDelete) {
        this.nivelOcupacionalEditDelete = nivelOcupacionalEditDelete;
    }

     /**
     * @return the listaNivelOcupacionalCodigo
     */
    public List<NivelOcupacional> getListaNivelOcupacionalCodigo() {
        return listaNivelOcupacionalCodigo;
    }

    /**
     * @param listaNivelOcupacionalCodigo the listaNivelOcupacionalCodigo to set
     */
    public void setListaNivelOcupacionalCodigo(List<NivelOcupacional> listaNivelOcupacionalCodigo) {
        this.listaNivelOcupacionalCodigo = listaNivelOcupacionalCodigo;
    }

    public TiposCertificacionesPresupuestariasEnum[] getTiposCertificaciones() {
        return tiposCertificaciones;
    }

    public void setTiposCertificaciones(TiposCertificacionesPresupuestariasEnum[] tiposCertificaciones) {
        this.tiposCertificaciones = tiposCertificaciones;
    }    
}
