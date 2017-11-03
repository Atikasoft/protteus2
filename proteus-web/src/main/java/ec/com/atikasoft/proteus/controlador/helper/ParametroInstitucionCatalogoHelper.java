/*
 *  ParametroInstitucionCatalogoHelper.java
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
 *  05/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucionalCatalogo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "parametroInstitucionCatalogoHelper")
@SessionScoped
public class ParametroInstitucionCatalogoHelper extends CatalogoHelper {

    /**
     * Variable para nuevo parametroInstitucionCatalogo.
     */
    private ParametroInstitucional parametroInstitucional;

    /**
     * Variable para nuevo parametroInstitucionCatalogo.
     */
    private ParametroInstitucionalCatalogo parametroInstitucionalCatalogo;

    /**
     * Variable para modificar/eliminar parametroInstitucionCatalogo.
     */
    private ParametroInstitucionalCatalogo parametroInstitucionalCatalogoEditDelete;

    /**
     * Variable para listar ParametroInstitucionalCatalogos.
     */
    private List<ParametroInstitucionalCatalogo> listaParametroInstitucionalCatalogo;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<ParametroInstitucionalCatalogo> listaAlertaNemonico;

    /**
     * Lista de tipos de parametros.
     */
    private List<SelectItem> tipoParametro;

    /**
     * Constructor de la clase.
     */
    public ParametroInstitucionCatalogoHelper() {
        super();
        iniciador();
    }

    /**
     * metodo para iniciar mis variables y objetos.
     */
    public final void iniciador() {
        parametroInstitucionalCatalogo = new ParametroInstitucionalCatalogo();
        parametroInstitucionalCatalogoEditDelete = new ParametroInstitucionalCatalogo();
        listaParametroInstitucionalCatalogo = new ArrayList<ParametroInstitucionalCatalogo>();
        listaAlertaNemonico = new ArrayList<ParametroInstitucionalCatalogo>();
        setTipoParametro(new ArrayList<SelectItem>());
        parametroInstitucional = new ParametroInstitucional();
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
     * @return the parametroInstitucionalCatalogoEditDelete
     */
    public ParametroInstitucionalCatalogo getParametroInstitucionalCatalogoEditDelete() {
        return parametroInstitucionalCatalogoEditDelete;
    }

    /**
     * @param parametroInstitucionalCatalogoEditDelete the parametroInstitucionalCatalogoEditDelete to set
     */
    public void setParametroInstitucionalCatalogoEditDelete(ParametroInstitucionalCatalogo parametroInstitucionalCatalogoEditDelete) {
        this.parametroInstitucionalCatalogoEditDelete = parametroInstitucionalCatalogoEditDelete;
    }

    /**
     * @return the listaParametroInstitucionalCatalogo
     */
    public List<ParametroInstitucionalCatalogo> getListaParametroInstitucionalCatalogo() {
        return listaParametroInstitucionalCatalogo;
    }

    /**
     * @param listaParametroInstitucionalCatalogo the listaParametroInstitucionalCatalogo to set
     */
    public void setListaParametroInstitucionalCatalogo(List<ParametroInstitucionalCatalogo> listaParametroInstitucionalCatalogo) {
        this.listaParametroInstitucionalCatalogo = listaParametroInstitucionalCatalogo;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<ParametroInstitucionalCatalogo> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<ParametroInstitucionalCatalogo> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }

    /**
     * @return the tipoParametro
     */
    public List<SelectItem> getTipoParametro() {
        return tipoParametro;
    }

    /**
     * @param tipoParametro the tipoParametro to set
     */
    public void setTipoParametro(final List<SelectItem> tipoParametro) {
        this.tipoParametro = tipoParametro;
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
}
