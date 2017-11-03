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
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "estadoPersonalHelper")
@SessionScoped
public final class EstadoPersonalHelper extends CatalogoHelper {

    /**
     * estado puesto.
     */
    private EstadoPersonal estadoPersonal;

    /**
     * estado puesto.
     */
    private EstadoPersonal estadoPersonalEditDelete;

    /**
     * lista de estado puesto.
     */
    private List<EstadoPersonal> listaEstadoPersonal;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<EstadoPersonal> listaAlertaNemonico;

    /**
     * Constructor por defecto.
     */
    public EstadoPersonalHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setEstadoPersonal(new EstadoPersonal());
        setEstadoPersonalEditDelete(new EstadoPersonal());
        setListaEstadoPersonal(new ArrayList<EstadoPersonal>());
        setListaAlertaNemonico(new ArrayList<EstadoPersonal>());
    }

    /**
     * @return the estadoPersonal
     */
    public EstadoPersonal getEstadoPersonal() {
        return estadoPersonal;
    }

    /**
     * @param estadoPersonal the estadoPersonal to set
     */
    public void setEstadoPersonal(EstadoPersonal estadoPersonal) {
        this.estadoPersonal = estadoPersonal;
    }

    /**
     * @return the estadoPersonalEditDelete
     */
    public EstadoPersonal getEstadoPersonalEditDelete() {
        return estadoPersonalEditDelete;
    }

    /**
     * @param estadoPersonalEditDelete the estadoPersonalEditDelete to set
     */
    public void setEstadoPersonalEditDelete(EstadoPersonal estadoPersonalEditDelete) {
        this.estadoPersonalEditDelete = estadoPersonalEditDelete;
    }

    /**
     * @return the listaEstadoPersonal
     */
    public List<EstadoPersonal> getListaEstadoPersonal() {
        return listaEstadoPersonal;
    }

    /**
     * @param listaEstadoPersonal the listaEstadoPersonal to set
     */
    public void setListaEstadoPersonal(List<EstadoPersonal> listaEstadoPersonal) {
        this.listaEstadoPersonal = listaEstadoPersonal;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<EstadoPersonal> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<EstadoPersonal> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
