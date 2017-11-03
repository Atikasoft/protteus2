/*
 *  AlertaHelper.java
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
import ec.com.atikasoft.proteus.modelo.Alerta;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean (name = "alertaHelper")
@SessionScoped
public class AlertaHelper extends CatalogoHelper {
    /**
     * Variable para nueva alerta.
     */
    private Alerta alerta;
    /**
     * Variable para modificar/eliminar una alerta.
     */
    private Alerta alertaEditDelete;
    /**
     * Variable para listar las alertas.
     */
    private List<Alerta> listaAlerta;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<Alerta> listaAlertaNemonico;
    /**
     * Lista de tipos de periodos.
     */
    private List<SelectItem> tipoPeriodo;
    /**
     * Constructor.
     */
    public AlertaHelper() {
        super();
        iniciador();
    }
    /**
     * Método para iniciar las variables de la AlertaHelper.
     */
    public final void iniciador() {
        setAlerta(new Alerta());        
        setAlertaEditDelete(new Alerta());
        setListaAlerta(new ArrayList<Alerta>());
        setTipoPeriodo(new ArrayList<SelectItem>());
        setListaAlertaNemonico(new ArrayList<Alerta>());
    }

    /**
     * @return the alerta
     */
    public Alerta getAlerta() {
        return alerta;
    }

    /**
     * @param alerta the alerta to set
     */
    public void setAlerta(final Alerta alerta) {
        this.alerta = alerta;
    }

    /**
     * @return the alertaEditDelete
     */
    public Alerta getAlertaEditDelete() {
        return alertaEditDelete;
    }

    /**
     * @param alertaEditDelete the alertaEditDelete to set
     */
    public void setAlertaEditDelete(final Alerta alertaEditDelete) {
        this.alertaEditDelete = alertaEditDelete;
    }

    /**
     * @return the listaAlerta
     */
    public List<Alerta> getListaAlerta() {
        return listaAlerta;
    }

    /**
     * @param listaAlerta the listaAlerta to set
     */
    public void setListaAlerta(final List<Alerta> listaAlerta) {
        this.listaAlerta = listaAlerta;
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
    public void setTipoPeriodo(final List<SelectItem> tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<Alerta> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<Alerta> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
