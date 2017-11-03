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
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "estadoAdminPuestoHelper")
@SessionScoped
public final class EstadoAdministracionPuestoHelper extends CatalogoHelper {

    /**
     * estado puesto.
     */
    private EstadoAdministracionPuesto estadoAdministracionPuesto;

    /**
     * estado puesto.
     */
    private EstadoAdministracionPuesto estadoAdministracionPuestoEditDelete;

    /**
     * lista de estado puesto.
     */
    private List<EstadoAdministracionPuesto> listaEstadoAdministracionPuesto;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<EstadoAdministracionPuesto> listaEstadosEncontrados;

    /**
     * Constructor por defecto.
     */
    public EstadoAdministracionPuestoHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setEstadoAdministracionPuesto(new EstadoAdministracionPuesto());
        setEstadoAdministracionPuestoEditDelete(new EstadoAdministracionPuesto());
        setListaEstadoAdministracionPuesto(new ArrayList<EstadoAdministracionPuesto>());
        setListaEstadosEncontrados(new ArrayList<EstadoAdministracionPuesto>());
    }

    /**
     * @return the estadoAdministracionPuesto
     */
    public EstadoAdministracionPuesto getEstadoAdministracionPuesto() {
        return estadoAdministracionPuesto;
    }

    /**
     * @param estadoAdministracionPuesto the estadoAdministracionPuesto to set
     */
    public void setEstadoAdministracionPuesto(EstadoAdministracionPuesto estadoAdministracionPuesto) {
        this.estadoAdministracionPuesto = estadoAdministracionPuesto;
    }

    /**
     * @return the estadoAdministracionPuestoEditDelete
     */
    public EstadoAdministracionPuesto getEstadoAdministracionPuestoEditDelete() {
        return estadoAdministracionPuestoEditDelete;
    }

    /**
     * @param estadoAdministracionPuestoEditDelete the estadoAdministracionPuestoEditDelete to set
     */
    public void setEstadoAdministracionPuestoEditDelete(EstadoAdministracionPuesto estadoAdministracionPuestoEditDelete) {
        this.estadoAdministracionPuestoEditDelete = estadoAdministracionPuestoEditDelete;
    }

    /**
     * @return the listaEstadoAdministracionPuesto
     */
    public List<EstadoAdministracionPuesto> getListaEstadoAdministracionPuesto() {
        return listaEstadoAdministracionPuesto;
    }

    /**
     * @param listaEstadoAdministracionPuesto the listaEstadoAdministracionPuesto to set
     */
    public void setListaEstadoAdministracionPuesto(List<EstadoAdministracionPuesto> listaEstadoAdministracionPuesto) {
        this.listaEstadoAdministracionPuesto = listaEstadoAdministracionPuesto;
    }

    /**
     * @return the listaEstadosEncontrados
     */
    public List<EstadoAdministracionPuesto> getListaEstadosEncontrados() {
        return listaEstadosEncontrados;
    }

    /**
     * @param listaEstadosEncontrados the listaEstadosEncontrados to set
     */
    public void setListaEstadosEncontrados(List<EstadoAdministracionPuesto> listaEstadosEncontrados) {
        this.listaEstadosEncontrados = listaEstadosEncontrados;
    }
}
