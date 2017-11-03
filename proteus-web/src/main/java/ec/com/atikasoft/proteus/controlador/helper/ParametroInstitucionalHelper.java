/*
 *  ParametroInstitucionalHelper.java
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
 *  11/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucionalCatalogo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "parametroInstitucionalHelper")
@SessionScoped
public class ParametroInstitucionalHelper extends CatalogoHelper {

    /**
     * variable para la clase
     */
    private InstitucionEjercicioFiscal institucion;

    /**
     * variable para la clase
     */
    private ParametroInstitucionalCatalogo parametroInstitucionalCatalogo;

    /**
     * variable para clase.
     */
    private ParametroInstitucional parametroInstitucional;

    /**
     * lista de parametros institucionales.
     */
    private List<ParametroInstitucional> listaParametroInstitucional;

    /**
     * Variable para modificar/eliminar una regla.
     */
    private ParametroInstitucional parametroInstitucionalEditDelete;

    /**
     * Lista para llenar el combo de parametros institucionales catalogo.
     */
    private List<SelectItem> listaParametroInstitucionCatalogo;

    /**
     * Lista de tipos de parametros.
     */
    private List<SelectItem> ListaTipoParametro;

    /**
     * Archivo cargado.
     */
    private UploadedFile archivoCargado;

    public ParametroInstitucionalHelper() {
        super();
        iniciador();
    }

    public final void iniciador() {
        setInstitucion(new InstitucionEjercicioFiscal());
        setParametroInstitucional(new ParametroInstitucional());
        setListaParametroInstitucional(new ArrayList<ParametroInstitucional>());
        listaParametroInstitucionCatalogo = new ArrayList<SelectItem>();
        parametroInstitucionalCatalogo = new ParametroInstitucionalCatalogo();
        setListaTipoParametro(new ArrayList<SelectItem>());
        parametroInstitucionalEditDelete = new ParametroInstitucional();

    }

    /**
     * @return the institucion
     */
    public InstitucionEjercicioFiscal getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(InstitucionEjercicioFiscal institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the parametroInstitucional
     */
    public ParametroInstitucional getParametroInstitucional() {
        return parametroInstitucional;
    }

    /**
     * @param parametroInstitucional the parametroInstitucional to set
     */
    public void setParametroInstitucional(ParametroInstitucional parametroInstitucional) {
        this.parametroInstitucional = parametroInstitucional;
    }

    /**
     * @return the listaParametroInstitucional
     */
    public List<ParametroInstitucional> getListaParametroInstitucional() {
        return listaParametroInstitucional;
    }

    /**
     * @param listaParametroInstitucional the listaParametroInstitucional to set
     */
    public void setListaParametroInstitucional(List<ParametroInstitucional> listaParametroInstitucional) {
        this.listaParametroInstitucional = listaParametroInstitucional;
    }

    /**
     * @return the parametroInstitucionalEditDelete
     */
    public ParametroInstitucional getParametroInstitucionalEditDelete() {
        return parametroInstitucionalEditDelete;
    }

    /**
     * @param parametroInstitucionalEditDelete the parametroInstitucionalEditDelete to set
     */
    public void setParametroInstitucionalEditDelete(ParametroInstitucional parametroInstitucionalEditDelete) {
        this.parametroInstitucionalEditDelete = parametroInstitucionalEditDelete;
    }

    /**
     * @return the listaParametroInstitucionCatalogo
     */
    public List<SelectItem> getListaParametroInstitucionCatalogo() {
        return listaParametroInstitucionCatalogo;
    }

    /**
     * @param listaParametroInstitucionCatalogo the listaParametroInstitucionCatalogo to set
     */
    public void setListaParametroInstitucionCatalogo(List<SelectItem> listaParametroInstitucionCatalogo) {
        this.listaParametroInstitucionCatalogo = listaParametroInstitucionCatalogo;
    }

    /**
     * @return the parametroInstitucionalCatalogo
     */
    public ParametroInstitucionalCatalogo getParametroInstitucionalCatalogo() {
        return parametroInstitucionalCatalogo;
    }

    /**
     * @param parametroInstitucionalCatalogo the parametroInstitucionalCatalogo to set
     */
    public void setParametroInstitucionalCatalogo(ParametroInstitucionalCatalogo parametroInstitucionalCatalogo) {
        this.parametroInstitucionalCatalogo = parametroInstitucionalCatalogo;
    }

    /**
     * @return the archivoCargado
     */
    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    /**
     * @param archivoCargado the archivoCargado to set
     */
    public void setArchivoCargado(UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    /**
     * @return the ListaTipoParametro
     */
    public List<SelectItem> getListaTipoParametro() {
        return ListaTipoParametro;
    }

    /**
     * @param ListaTipoParametro the ListaTipoParametro to set
     */
    public void setListaTipoParametro(List<SelectItem> ListaTipoParametro) {
        this.ListaTipoParametro = ListaTipoParametro;
    }
}
