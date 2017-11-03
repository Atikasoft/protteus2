/*
 *  ReglaHelper.java
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
 *  08/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Regla;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "reglaHelper")
@SessionScoped
public class ReglaHelper extends CatalogoHelper {

    /**
     * Variable para nueva regla.
     */
    private Regla regla;

    /**
     * Variable para modificar/eliminar una regla.
     */
    private Regla reglaEditDelete;

    /**
     * Variable para listar los documentos habilitantes.
     */
    private List<Regla> listaRegla;

    /**
     * Lista de tipos de periodo.
     */
    private List<SelectItem> tipoPeriodo;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Regla> listaAlertaNemonico;

    /**
     * campo para activar el tipo periodo.
     */
    private Boolean campoTipoPeriodo;

    public ReglaHelper() {
        super();
        iniciador();
    }

    public final void iniciador() {
        setRegla(new Regla());
        setReglaEditDelete(new Regla());
        setListaRegla(new ArrayList<Regla>());
        tipoPeriodo = new ArrayList<SelectItem>();
        listaAlertaNemonico = new ArrayList<Regla>();
        campoTipoPeriodo = Boolean.FALSE;
    }

    /**
     * @return the regla
     */
    public Regla getRegla() {
        return regla;
    }

    /**
     * @param regla the regla to set
     */
    public void setRegla(Regla regla) {
        this.regla = regla;
    }

    /**
     * @return the reglaEditDelete
     */
    public Regla getReglaEditDelete() {
        return reglaEditDelete;
    }

    /**
     * @param reglaEditDelete the reglaEditDelete to set
     */
    public void setReglaEditDelete(Regla reglaEditDelete) {
        this.reglaEditDelete = reglaEditDelete;
    }

    /**
     * @return the listaRegla
     */
    public List<Regla> getListaRegla() {
        return listaRegla;
    }

    /**
     * @param listaRegla the listaRegla to set
     */
    public void setListaRegla(List<Regla> listaRegla) {
        this.listaRegla = listaRegla;
    }

    /**
     * @return the tipoPeriodo
     */
    public List<SelectItem> getTipoPeriodo() {
        return tipoPeriodo;
    }

    /**
     * @param tipoPeriodo the tipoPeriodo to set
     */
    public void setTipoPeriodo(List<SelectItem> tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<Regla> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<Regla> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }

    /**
     * @return the campoTipoPeriodo
     */
    public Boolean getCampoTipoPeriodo() {
        return campoTipoPeriodo;
    }

    /**
     * @param campoTipoPeriodo the campoTipoPeriodo to set
     */
    public void setCampoTipoPeriodo(Boolean campoTipoPeriodo) {
        this.campoTipoPeriodo = campoTipoPeriodo;
    }
}
