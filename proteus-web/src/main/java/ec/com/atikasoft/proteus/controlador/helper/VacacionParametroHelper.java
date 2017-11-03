/*
 *  VacacionParametroHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  28/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * VacacionParametro
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionParametroHelper")
@SessionScoped
public class VacacionParametroHelper extends CatalogoHelper {

    /**
     * clase vacacionParametro.
     */
    private VacacionParametro vacacionParametro;
    
    /**
     * clase vacacionParametro puesto para editar.
     */
    private VacacionParametro vacacionParametroEditDelete;
    /**
     * lista de vacacionParametros.
     */
    private List<VacacionParametro> listaVacacionParametros;
    /**
     * Variable para listar campos de acceso por nombre
     */
    private List<VacacionParametro> listaVacacionParametroNombre;
    /**
     * Lista de tipos de acumulacion.
     */
    private List<SelectItem> opcionTipoAcumulacion;
     /**
     * Lista de regimenes laborales.
     */
    private List<SelectItem> opcionRegimenLaboral;
    
    /**
     * Constructor por defecto.
     */
    public VacacionParametroHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionParametroHelper.
     */
    public final void iniciador() {
        setVacacionParametro(new VacacionParametro());
        setVacacionParametroEditDelete(new VacacionParametro());
         setListaVacacionParametroNombre(new ArrayList<VacacionParametro>());
        setOpcionRegimenLaboral(new ArrayList<SelectItem>());
        setOpcionTipoAcumulacion(new ArrayList<SelectItem>());
        setListaVacacionParametros(new ArrayList<VacacionParametro>());
    }

    /**
     * @return the vacacionParametro
     */
    public VacacionParametro getVacacionParametro() {
        return vacacionParametro;
    }

    /**
     * @param vacacionParametro the vacacionParametro to set
     */
    public void setVacacionParametro(final VacacionParametro vacacionParametro) {
        this.vacacionParametro = vacacionParametro;
    }

    /**
     * @return the vacacionParametroEditDelete
     */
    public VacacionParametro getVacacionParametroEditDelete() {
        return vacacionParametroEditDelete;
    }

    /**
     * @param vacacionParametroEditDelete the vacacionParametroEditDelete to set
     */
    public void setVacacionParametroEditDelete(final VacacionParametro vacacionParametroEditDelete) {
        this.vacacionParametroEditDelete = vacacionParametroEditDelete;
    }

    /**
     * @return the listaVacacionParametroNombre
     */
    public List<VacacionParametro> getListaVacacionParametroNombre() {
        return listaVacacionParametroNombre;
    }

    /**
     * @param listaVacacionParametroNombre the listaVacacionParametroNombre to set
     */
    public void setListaVacacionParametroNombre(final List<VacacionParametro> listaVacacionParametroNombre) {
        this.listaVacacionParametroNombre = listaVacacionParametroNombre;
    }

    /**
     * @return the listaVacacionParametros
     */
    public List<VacacionParametro> getListaVacacionParametros() {
        return listaVacacionParametros;
    }

    /**
     * @param listaVacacionParametros the listaVacacionParametros to set
     */
    public void setListaVacacionParametros(List<VacacionParametro> listaVacacionParametros) {
        this.listaVacacionParametros = listaVacacionParametros;
    }

    /**
     * @return the opcionTipoAcumulacion
     */
    public List<SelectItem> getOpcionTipoAcumulacion() {
        return opcionTipoAcumulacion;
    }

    /**
     * @param opcionTipoAcumulacion the opcionTipoAcumulacion to set
     */
    public void setOpcionTipoAcumulacion(List<SelectItem> opcionTipoAcumulacion) {
        this.opcionTipoAcumulacion = opcionTipoAcumulacion;
    }

    /**
     * @return the opcionRegimenLaboral
     */
    public List<SelectItem> getOpcionRegimenLaboral() {
        return opcionRegimenLaboral;
    }

    /**
     * @param opcionRegimenLaboral the opcionRegimenLaboral to set
     */
    public void setOpcionRegimenLaboral(List<SelectItem> opcionRegimenLaboral) {
        this.opcionRegimenLaboral = opcionRegimenLaboral;
    }
}
