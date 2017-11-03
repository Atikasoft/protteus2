                                                                                                                                                                                                                                                                                                                         /*
 *  ParametroGlobalHelper.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "parametroGlobalHelp")
@SessionScoped
public class ParametroGlobalHelper extends CatalogoHelper {

    /**
     * Entida principal.
     */
    private ParametroGlobal parametroGlobal;

    /**
     * Entidad de transaccion.
     */
    private ParametroGlobal parametroGlobalEditDelete;

    /**
     * Bandera para la creacion y edicion.
     */
    private Boolean esNuevo;

    /**
     * Lista de tipos de parametros.
     */
    private List<SelectItem> tipoParametro;

    /**
     * Lista de parametros globales.
     */
    private List<ParametroGlobal> listaParametroGlobal;

    /**
     * Constructor por defecto.
     */
    public ParametroGlobalHelper() {
        super();
        iniciador();
    }

    /**
     * Este metodo inicializa las variables del helper.
     */
    public final void iniciador() {
        parametroGlobal = new ParametroGlobal();
        parametroGlobalEditDelete = new ParametroGlobal();
        tipoParametro = new ArrayList<SelectItem>();
        listaParametroGlobal = new ArrayList<ParametroGlobal>();
    }

    /**
     * @return the parametroGlobal
     */
    public ParametroGlobal getParametroGlobal() {
        return parametroGlobal;
    }

    /**
     * @param parametroGlobal the parametroGlobal to set
     */
    public void setParametroGlobal(final ParametroGlobal parametroGlobal) {
        this.parametroGlobal = parametroGlobal;
    }

    /**
     * @return the parametroGlobalEditDelete
     */
    public ParametroGlobal getParametroGlobalEditDelete() {
        return parametroGlobalEditDelete;
    }

    /**
     * @param parametroGlobalEditDelete the parametroGlobalEditDelete to set
     */
    public void setParametroGlobalEditDelete(final ParametroGlobal parametroGlobalEditDelete) {
        this.parametroGlobalEditDelete = parametroGlobalEditDelete;
    }

    /**
     * @return the esNuevo
     */
    public Boolean getEsNuevo() {
        return esNuevo;
    }

    /**
     * @param esNuevo the esNuevo to set
     */
    public void setEsNuevo(final Boolean esNuevo) {
        this.esNuevo = esNuevo;
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
     * @return the listaParametroGlobal
     */
    public List<ParametroGlobal> getListaParametroGlobal() {
        return listaParametroGlobal;
    }

    /**
     * @param listaParametroGlobal the listaParametroGlobal to set
     */
    public void setListaParametroGlobal(final List<ParametroGlobal> listaParametroGlobal) {
        this.listaParametroGlobal = listaParametroGlobal;
    }
}
