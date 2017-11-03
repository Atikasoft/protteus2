/*
 *  EstadoPuestoHelper.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "estadoPuestoHelper")
@SessionScoped
public final class EstadoPuestoHelper extends CatalogoHelper {

    /**
     * estado puesto.
     */
    private EstadoPuesto estadoPuesto;

    /**
     * estado puesto.
     */
    private EstadoPuesto estadoPuestoEditDelete;

    /**
     * lista de estado puesto.
     */
    private List<EstadoPuesto> listaEstadoPuestos;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<EstadoPuesto> listaAlertaNemonico;

    /**
     * Constructor por defecto.
     */
    public EstadoPuestoHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setEstadoPuesto(new EstadoPuesto());
        setListaEstadoPuestos(new ArrayList<EstadoPuesto>());
        estadoPuestoEditDelete = new EstadoPuesto();
        listaAlertaNemonico = new ArrayList<EstadoPuesto>();
    }

    /**
     * @return the estadoPuesto
     */
    public EstadoPuesto getEstadoPuesto() {
        return estadoPuesto;
    }

    /**
     * @param estadoPuesto the estadoPuesto to set
     */
    public void setEstadoPuesto(final EstadoPuesto estadoPuesto) {
        this.estadoPuesto = estadoPuesto;
    }

    /**
     * @return the listaEstadoPuestos
     */
    public List<EstadoPuesto> getListaEstadoPuestos() {
        return listaEstadoPuestos;
    }

    /**
     * @param listaEstadoPuestos the listaEstadoPuestos to set
     */
    public void setListaEstadoPuestos(final List<EstadoPuesto> listaEstadoPuestos) {
        this.listaEstadoPuestos = listaEstadoPuestos;
    }

    /**
     * @return the estadoPuestoEditDelete
     */
    public EstadoPuesto getEstadoPuestoEditDelete() {
        return estadoPuestoEditDelete;
    }

    /**
     * @param estadoPuestoEditDelete the estadoPuestoEditDelete to set
     */
    public void setEstadoPuestoEditDelete(final EstadoPuesto estadoPuestoEditDelete) {
        this.estadoPuestoEditDelete = estadoPuestoEditDelete;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<EstadoPuesto> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<EstadoPuesto> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
