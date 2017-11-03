/*
 *  DatoAdicionalHelper.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * DatoAdicional
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "datoAdicionalHelper")
@SessionScoped
public class DatoAdicionalHelper extends CatalogoHelper {

    /**
     * clase datoAdicional.
     */
    private DatoAdicional datoAdicional;
    /**
     * clase datoAdicional puesto para editar.
     */
    private DatoAdicional datoAdicionalEditDelete;
    /**
     * lista de datoAdicionals.
     */
    private List<DatoAdicional> listaDatosAdicionales;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<DatoAdicional> listaDatoAdicionalCodigo;
    /**
     * Lista de tipos de dato adicional.
     */
    private List<SelectItem> tipo;
    
     /**
     * Lista de tipos de dato.
     */
    private List<SelectItem> tipoDato;

    /**
     * Constructor por defecto.
     */
    public DatoAdicionalHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DatoAdicionalHelper.
     */
    public final void iniciador() {
        setDatoAdicional(new DatoAdicional());
        setDatoAdicionalEditDelete(new DatoAdicional());
        setListaDatoAdicionales(new ArrayList<DatoAdicional>());
        setListaDatoAdicionalCodigo(new ArrayList<DatoAdicional>());
        setTipo(new ArrayList<SelectItem>());
        setTipoDato(new ArrayList<SelectItem>());
    }

    /**
     * @return the datoAdicional
     */
    public DatoAdicional getDatoAdicional() {
        return datoAdicional;
    }

    /**
     * @param datoAdicional the datoAdicional to set
     */
    public void setDatoAdicional(final DatoAdicional datoAdicional) {
        this.datoAdicional = datoAdicional;
    }

    /**
     * @return the datoAdicionalEditDelete
     */
    public DatoAdicional getDatoAdicionalEditDelete() {
        return datoAdicionalEditDelete;
    }

    /**
     * @param datoAdicionalEditDelete the datoAdicionalEditDelete to set
     */
    public void setDatoAdicionalEditDelete(final DatoAdicional datoAdicionalEditDelete) {
        this.datoAdicionalEditDelete = datoAdicionalEditDelete;
    }

    /**
     * @return the listaDatoAdicionals
     */
    public List<DatoAdicional> getListaDatosAdicionales() {
        return listaDatosAdicionales;
    }

    /**
     * @param listaDatoAdicionals the listaDatoAdicionals to set
     */
    public void setListaDatoAdicionales(final List<DatoAdicional> listaDatosAdicionaless) {
        this.listaDatosAdicionales = listaDatosAdicionaless;
    }

    /**
     * @return the listaDatoAdicionalCodigo
     */
    public List<DatoAdicional> getListaDatoAdicionalCodigo() {
        return listaDatoAdicionalCodigo;
    }

    /**
     * @param listaDatoAdicionalCodigo the listaDatoAdicionalCodigo to set
     */
    public void setListaDatoAdicionalCodigo(final List<DatoAdicional> listaDatoAdicionalCodigo) {
        this.listaDatoAdicionalCodigo = listaDatoAdicionalCodigo;
    }

    /**
     * @return the tipo
     */
    public List<SelectItem> getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(List<SelectItem> tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tipoDato
     */
    public List<SelectItem> getTipoDato() {
        return tipoDato;
    }

    /**
     * @param tipoDato the tipoDato to set
     */
    public void setTipoDato(List<SelectItem> tipoDato) {
        this.tipoDato = tipoDato;
    }
}
