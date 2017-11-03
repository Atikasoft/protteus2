/*
 *  TipoNominaHelper.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import ec.com.atikasoft.proteus.modelo.TipoNominaEstadoPersonal;
import ec.com.atikasoft.proteus.modelo.TipoNominaEstadoPuesto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "tipoNominaHelper")
@SessionScoped
public final class TipoNominaHelper extends CatalogoHelper {

    /**
     * tipo nomina.
     */
    private TipoNomina tipoNomina;

    /**
     * lsta nomina.
     */
    private List<TipoNomina> listaNominas;

    /**
     * lsta nomina.
     */
    private List<EstadoPuesto> listaEstadoPuestos;

    /**
     * tipoNominaEstadoPuesto
     */
    private TipoNominaEstadoPuesto tipoNominaEstadoPuesto;

    /**
     * lsta nomina.
     */
    private List<EstadoPersonal> listaEstadoPersonal;

    /**
     * tipoNominaEstadoPersonal.
     */
    private TipoNominaEstadoPersonal tipoNominaEstadoPersonal;

    /**
     * tipo nomina.
     */
    private TipoNomina tipoNominaEditDelete;

    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<TipoNomina> listaAlertaNemonico;

    /**
     * lista tipo.
     */
    private List<SelectItem> listaTipo;

    /**
     * lista cobertura.
     */
    private List<SelectItem> listaCobertura;

    /**
     * lista periodicidad.
     */
    private List<SelectItem> listaPeriodicidad;
    /**
     * lista periodicidad.
     */
    private List<SelectItem> listaRegimenLaboral;

    /**
     * Constructor por defecto.
     */
    public TipoNominaHelper() {
        super();
        iniciador();
    }
    
    public void iniciador() {
        setTipoNomina(new TipoNomina());
        setListaNominas(new ArrayList<TipoNomina>());
        tipoNominaEditDelete = new TipoNomina();
        listaAlertaNemonico = new ArrayList<TipoNomina>();
        listaTipo = new ArrayList<SelectItem>();
        setListaCobertura(new ArrayList<SelectItem>());
        setListaPeriodicidad(new ArrayList<SelectItem>());
        listaEstadoPersonal = new ArrayList<EstadoPersonal>();
        listaEstadoPuestos = new ArrayList<EstadoPuesto>();
        tipoNominaEstadoPuesto = new TipoNominaEstadoPuesto();
        tipoNominaEstadoPersonal = new TipoNominaEstadoPersonal();
        setListaRegimenLaboral(new ArrayList<SelectItem>());
        getTipoNomina().setRegimenLaboral(new RegimenLaboral());
    }

    /**
     * @return the tipoNomina
     */
    public TipoNomina getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(TipoNomina tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the listaNominas
     */
    public List<TipoNomina> getListaNominas() {
        return listaNominas;
    }

    /**
     * @param listaNominas the listaNominas to set
     */
    public void setListaNominas(List<TipoNomina> listaNominas) {
        this.listaNominas = listaNominas;
    }

    /**
     * @return the tipoNominaEditDelete
     */
    public TipoNomina getTipoNominaEditDelete() {
        return tipoNominaEditDelete;
    }

    /**
     * @param tipoNominaEditDelete the tipoNominaEditDelete to set
     */
    public void setTipoNominaEditDelete(TipoNomina tipoNominaEditDelete) {
        this.tipoNominaEditDelete = tipoNominaEditDelete;
    }

    /**
     * @return the listaAlertaNemonico
     */
    public List<TipoNomina> getListaAlertaNemonico() {
        return listaAlertaNemonico;
    }

    /**
     * @param listaAlertaNemonico the listaAlertaNemonico to set
     */
    public void setListaAlertaNemonico(List<TipoNomina> listaAlertaNemonico) {
        this.listaAlertaNemonico = listaAlertaNemonico;
    }

    /**
     * @return the listaTipo
     */
    public List<SelectItem> getListaTipo() {
        return listaTipo;
    }

    /**
     * @param listaTipo the listaTipo to set
     */
    public void setListaTipo(List<SelectItem> listaTipo) {
        this.listaTipo = listaTipo;
    }

    /**
     * @return the listaCobertura
     */
    public List<SelectItem> getListaCobertura() {
        return listaCobertura;
    }

    /**
     * @param listaCobertura the listaCobertura to set
     */
    public void setListaCobertura(List<SelectItem> listaCobertura) {
        this.listaCobertura = listaCobertura;
    }

    /**
     * @return the listaPeriodicidad
     */
    public List<SelectItem> getListaPeriodicidad() {
        return listaPeriodicidad;
    }

    /**
     * @param listaPeriodicidad the listaPeriodicidad to set
     */
    public void setListaPeriodicidad(List<SelectItem> listaPeriodicidad) {
        this.listaPeriodicidad = listaPeriodicidad;
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
    public void setListaEstadoPuestos(List<EstadoPuesto> listaEstadoPuestos) {
        this.listaEstadoPuestos = listaEstadoPuestos;
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
     * @return the tipoNominaEstadoPuesto
     */
    public TipoNominaEstadoPuesto getTipoNominaEstadoPuesto() {
        return tipoNominaEstadoPuesto;
    }

    /**
     * @param tipoNominaEstadoPuesto the tipoNominaEstadoPuesto to set
     */
    public void setTipoNominaEstadoPuesto(TipoNominaEstadoPuesto tipoNominaEstadoPuesto) {
        this.tipoNominaEstadoPuesto = tipoNominaEstadoPuesto;
    }

    /**
     * @return the tipoNominaEstadoPersonal
     */
    public TipoNominaEstadoPersonal getTipoNominaEstadoPersonal() {
        return tipoNominaEstadoPersonal;
    }

    /**
     * @param tipoNominaEstadoPersonal the tipoNominaEstadoPersonal to set
     */
    public void setTipoNominaEstadoPersonal(TipoNominaEstadoPersonal tipoNominaEstadoPersonal) {
        this.tipoNominaEstadoPersonal = tipoNominaEstadoPersonal;
    }

    /**
     * @return the listaRegimenLaboral
     */
    public List<SelectItem> getListaRegimenLaboral() {
        return listaRegimenLaboral;
    }

    /**
     * @param listaRegimenLaboral the listaRegimenLaboral to set
     */
    public void setListaRegimenLaboral(List<SelectItem> listaRegimenLaboral) {
        this.listaRegimenLaboral = listaRegimenLaboral;
    }
}
