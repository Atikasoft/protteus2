/*
 *  ModalidadLaboralHelper.java
 *  Quito - Ecuador
 *  19/09/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.ModalidadNivelOcupacional;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author LRordriguez <liliana.rodriguez@marcasoft.ec>
 */
@ManagedBean (name = "modalidadLaboralHelper")
@SessionScoped
public class ModalidadLaboralHelper extends CatalogoHelper {
    /**
     * Variable para nueva modalidadLaboral.
     */
    private ModalidadLaboral modalidadLaboral;
    /**
     * Variable para modificar/eliminar una modalidadLaboral.
     */
    private ModalidadLaboral modalidadLaboralEditDelete;
    /**
     * Variable para listar las modalidadLaborals.
     */
    private List<ModalidadLaboral> listaModalidadLaboral;
    /**
     * Variable para listar las modalidadLaborals por codigo.
     */
    private List<ModalidadLaboral> listaModalidadLaboralCodigo;
    
   
    /**
     * Variable para listar las modalidadLaboral - nivelOcupacional.
     */
    private List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional;
    
     /**
     * Lista de tipos de periodos.
     */
    private List<SelectItem> tipoModalidad;
    
     /**
     * Lista de Niveles Ocupacionales vigentes.
     */
     private List <NivelOcupacional> nivelesOcupacionales;
    
       /**
     * Variable para nueva modalidadLaboral - Nivel Ocupacional.
     */
    private ModalidadNivelOcupacional modalidadNivelOcupacional;
    
    /**
     * Constructor.
     */
    public ModalidadLaboralHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la ModalidadLaboralHelper.
     */
    public final void iniciador() {
        setModalidadLaboral(new ModalidadLaboral());        
        setModalidadLaboralEditDelete(new ModalidadLaboral());
        setListaModalidadLaboralCodigo(new ArrayList<ModalidadLaboral>());
        setListaModalidadLaboral(new ArrayList<ModalidadLaboral>());
        setModalidadNivelOcupacional(new ModalidadNivelOcupacional());
        setListaModalidadNivelOcupacional(new ArrayList<ModalidadNivelOcupacional>());
        setNivelesOcupacionales(new ArrayList<NivelOcupacional>());
        setTipoModalidad(new ArrayList<SelectItem>());
       }

    /**
     * @return the modalidadLaboral
     */
    public ModalidadLaboral getModalidadLaboral() {
        return modalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setModalidadLaboral(final ModalidadLaboral modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the modalidadLaboralEditDelete
     */
    public ModalidadLaboral getModalidadLaboralEditDelete() {
        return modalidadLaboralEditDelete;
    }

    /**
     * @param modalidadLaboralEditDelete the modalidadLaboralEditDelete to set
     */
    public void setModalidadLaboralEditDelete(final ModalidadLaboral modalidadLaboralEditDelete) {
        this.modalidadLaboralEditDelete = modalidadLaboralEditDelete;
    }

    /**
     * @return the listaModalidadLaboral
     */
    public List<ModalidadLaboral> getListaModalidadLaboral() {
        return listaModalidadLaboral;
    }

    /**
     * @param listaModalidadLaboral the listaModalidadLaboral to set
     */
    public void setListaModalidadLaboral(final List<ModalidadLaboral> listaModalidadLaboral) {
        this.listaModalidadLaboral = listaModalidadLaboral;
    }

     /**
     * @return the listaModalidadLaboralCodigo
     */
    public List<ModalidadLaboral> getListaModalidadLaboralCodigo() {
        return listaModalidadLaboralCodigo;
    }

    /**
     * @param listaModalidadLaboralCodigo the listaModalidadLaboralCodigo to set
     */
    public void setListaModalidadLaboralCodigo(List<ModalidadLaboral> listaModalidadLaboralCodigo) {
        this.listaModalidadLaboralCodigo = listaModalidadLaboralCodigo;
    }


    /**
     * @return the listaModalidadNivelOcupacional
     */
    public List<ModalidadNivelOcupacional> getListaModalidadNivelOcupacional() {
        return listaModalidadNivelOcupacional;
    }

    /**
     * @param listaModalidadNivelOcupacional the listaModalidadNivelOcupacional to set
     */
    public void setListaModalidadNivelOcupacional(List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional) {
        this.listaModalidadNivelOcupacional = listaModalidadNivelOcupacional;
    }

    /**
     * @return the tipoModalidad
     */
    public List<SelectItem> getTipoModalidad() {
        return tipoModalidad;
    }

    /**
     * @param tipoModalidad the tipoModalidad to set
     */
    public void setTipoModalidad(List<SelectItem> tipoModalidad) {
        this.tipoModalidad = tipoModalidad;
    }

    /**
     * @return the nivelesOcupacionales
     */
    public List <NivelOcupacional> getNivelesOcupacionales() {
        return nivelesOcupacionales;
    }

    /**
     * @param nivelesOcupacionales the nivelesOcupacionales to set
     */
    public void setNivelesOcupacionales(List <NivelOcupacional> nivelesOcupacionales) {
        this.nivelesOcupacionales = nivelesOcupacionales;
    }

    /**
     * @return the modalidadNivelOcupacional
     */
    public ModalidadNivelOcupacional getModalidadNivelOcupacional() {
        return modalidadNivelOcupacional;
    }

    /**
     * @param modalidadNivelOcupacional the modalidadNivelOcupacional to set
     */
    public void setModalidadNivelOcupacional(ModalidadNivelOcupacional modalidadNivelOcupacional) {
        this.modalidadNivelOcupacional = modalidadNivelOcupacional;
    }
}
