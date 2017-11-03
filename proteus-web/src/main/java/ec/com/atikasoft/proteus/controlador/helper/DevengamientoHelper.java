/*
 *  DevengamientoHelper.java
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
 *  24/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Devengamiento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "devengamientoHelper")
@SessionScoped
public class DevengamientoHelper implements Serializable {

    /**
     * Parametros para la busqueda de puestos.
     */
//    private SrhvOrganicoPosicionalIndividual parametrosBusqueda = new SrhvOrganicoPosicionalIndividual();

    /**
     * Lista de puesto.
     */
//    private List<SrhvOrganicoPosicionalIndividual> listaDevengamientos = new ArrayList<SrhvOrganicoPosicionalIndividual>();

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion;

    /**
     * tipo de identificacion.
     */
    private String tipoIdentificacion;

    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento = new ArrayList<SelectItem>();

    /**
     * tipo licencia Booleana.
     */
    private Boolean tipoCedula;

    /**
     * obj para consultar los devengamientos pendientes.
     */
    private Devengamiento devengamiento;

    /**
     * obj para consultar los devengamientos pendientes edit.
     */
    private Devengamiento devengamientoEdit;

    /**
     * fecha actual.
     */
    private Date fechaCorte = new Date();

    /**
     * lista de devengamientos.
     */
    private List<Devengamiento> devengamientoLista = new ArrayList<Devengamiento>();

    /**
     * controlar el devengamiento.
     */
    private Boolean activar;

    /**
     * controlador de la clase.
     */
    public DevengamientoHelper() {
        super();
        iniciadorEntidades();
    }

    /**
     * Este método inicia las variables del helper.
     */
    public final void iniciadorEntidades() {
//        tipoCedula = Boolean.FALSE;
//        tipoDocumento = new ArrayList<SelectItem>();
//        parametrosBusqueda = new SrhvOrganicoPosicionalIndividual();
//        listaDevengamientos = new ArrayList<SrhvOrganicoPosicionalIndividual>();
//        devengamiento = new Devengamiento();
//        devengamientoEdit = new Devengamiento();
//        setDevengamientoLista(new ArrayList<Devengamiento>());
//        setActivar(Boolean.FALSE);
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the tipoDocumento
     */
    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the tipoCedula
     */
    public Boolean getTipoCedula() {
        return tipoCedula;
    }

    /**
     * @param tipoCedula the tipoCedula to set
     */
    public void setTipoCedula(final Boolean tipoCedula) {
        this.tipoCedula = tipoCedula;
    }

    /**
     * @return the listaDevengamientos
     */
//    public List<SrhvOrganicoPosicionalIndividual> getListaDevengamientos() {
//        return listaDevengamientos;
//    }

    /**
     * @param listaDevengamientos the listaDevengamientos to set
     */
//    public void setListaDevengamientos(final List<SrhvOrganicoPosicionalIndividual> listaDevengamientos) {
//        this.listaDevengamientos = listaDevengamientos;
//    }

    /**
     * @return the parametrosBusqueda
     */
//    public SrhvOrganicoPosicionalIndividual getParametrosBusqueda() {
//        return parametrosBusqueda;
//    }

    /**
     * @param parametrosBusqueda the parametrosBusqueda to set
     */
//    public void setParametrosBusqueda(final SrhvOrganicoPosicionalIndividual parametrosBusqueda) {
//        this.parametrosBusqueda = parametrosBusqueda;
//    }

    /**
     * @return the devengamiento
     */
    public Devengamiento getDevengamiento() {
        return devengamiento;
    }

    /**
     * @param devengamiento the devengamiento to set
     */
    public void setDevengamiento(Devengamiento devengamiento) {
        this.devengamiento = devengamiento;
    }

    /**
     * @return the devengamientoEdit
     */
    public Devengamiento getDevengamientoEdit() {
        return devengamientoEdit;
    }

    /**
     * @param devengamientoEdit the devengamientoEdit to set
     */
    public void setDevengamientoEdit(Devengamiento devengamientoEdit) {
        this.devengamientoEdit = devengamientoEdit;
    }

    /**
     * @return the fechaCorte
     */
    public Date getFechaCorte() {
        return fechaCorte;
    }

    /**
     * @param fechaCorte the fechaCorte to set
     */
    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    /**
     * @return the devengamientoLista
     */
    public List<Devengamiento> getDevengamientoLista() {
        return devengamientoLista;
    }

    /**
     * @param devengamientoLista the devengamientoLista to set
     */
    public void setDevengamientoLista(List<Devengamiento> devengamientoLista) {
        this.devengamientoLista = devengamientoLista;
    }

    /**
     * @return the activar
     */
    public Boolean getActivar() {
        return activar;
    }

    /**
     * @param activar the activar to set
     */
    public void setActivar(Boolean activar) {
        this.activar = activar;
    }
}
