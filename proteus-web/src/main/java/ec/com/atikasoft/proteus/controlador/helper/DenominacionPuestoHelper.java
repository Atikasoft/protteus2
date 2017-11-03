/*
 *  DenominacionPuestoHelper.java
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
 *  19/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * DenominacionPuesto
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@ManagedBean(name = "denominacionPuestoHelper")
@SessionScoped
public class DenominacionPuestoHelper extends CatalogoHelper {

    /**
     * clase denominacion puesto.
     */
    private DenominacionPuesto denominacionPuesto;

    /**
     * clase denominacion puesto para editar.
     */
    private DenominacionPuesto denominacionPuestoEditDelete;

    /**
     * lista de denominacion puesto.
     */
    private List<DenominacionPuesto> listaDenominacionPuestos;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<DenominacionPuesto> listaAlertaNemonico;

    /**
     * Constructor por defecto.
     */
    public DenominacionPuestoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del DocumentoHabilitanteHelper.
     */
    public final void iniciador() {
        setDenominacionPuesto(new DenominacionPuesto());
        setDenominacionPuestoEditDelete(new DenominacionPuesto());
        setListaDenominacionPuestos(new ArrayList<DenominacionPuesto>());
        setListaAlertaNemonico(new ArrayList<DenominacionPuesto>());
        
    }

    /**
     * @return the denominacionPuesto
     */
    public DenominacionPuesto getDenominacionPuesto() {
        return denominacionPuesto;
    }

    /**
     * @param denominacionPuesto the denominacionPuesto to set
     */
    public void setDenominacionPuesto(final DenominacionPuesto denominacionPuesto) {
        this.denominacionPuesto = denominacionPuesto;
    }

    /**
     * @return the denominacionPuestoEditDelete
     */
    public DenominacionPuesto getDenominacionPuestoEditDelete() {
        return denominacionPuestoEditDelete;
    }

    /**
     * @param denominacionPuestoEditDelete the denominacionPuestoEditDelete to set
     */
    public void setDenominacionPuestoEditDelete(final DenominacionPuesto denominacionPuestoEditDelete) {
        this.denominacionPuestoEditDelete = denominacionPuestoEditDelete;
    }

    /**
     * @return the listaDenominacionPuestos
     */
    public List<DenominacionPuesto> getListaDenominacionPuestos() {
        return listaDenominacionPuestos;
    }

    /**
     * @param listaDenominacionPuestos the listaDenominacionPuestos to set
     */
    public void setListaDenominacionPuestos(final List<DenominacionPuesto> listaDenominacionPuestos) {
        this.listaDenominacionPuestos = listaDenominacionPuestos;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<DenominacionPuesto> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(final List<DenominacionPuesto> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }
}
