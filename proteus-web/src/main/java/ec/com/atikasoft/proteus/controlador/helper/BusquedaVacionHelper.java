/*
 *  BusquedaPuestoHelper.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "busquedaVacionHelper")
@SessionScoped
public class BusquedaVacionHelper implements Serializable {

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    /**
    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaVacacionVO busquedaVacacionVO = new BusquedaVacacionVO();
    /**
     * Lista de puesto
     */
    private List<DistributivoDetalle> listaServidores = new ArrayList<DistributivoDetalle>();
    /**
     * Lista de puesto
     */
    private List<PlanificacionVacacionDetalle> listaPlanificacionVacacionDetalles = new ArrayList<PlanificacionVacacionDetalle>();
    private List<VacacionSolicitud> listaSolicitudesVacaciones = new ArrayList<VacacionSolicitud>();
    private List<Vacacion> listaSaldoVacaciones = new ArrayList<Vacacion>();
    private List<SelectItem> listaOpcionesTipoVacacion = new ArrayList<SelectItem>();
    private List<SelectItem> listaOpcionesEstadoVacacion = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoDocumento = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoPuesto = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEjercicioFiscal = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEstadoPersonal = new ArrayList<SelectItem>();
    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;
    /**
     * Indica si el panel de parametros esta activo.
     */
    private String activo = "0";
    private String nombreServidor;

    /**
     * Constructor por defecto.
     */
    public BusquedaVacionHelper() {
        super();

    }

    /**
     * @return the listaTipoDocumento
     */
    public List<SelectItem> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    /**
     * @param listaTipoDocumento the listaTipoDocumento to set
     */
    public void setListaTipoDocumento(final List<SelectItem> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    /**
     * @return the listaEstadoPuesto
     */
    public List<SelectItem> getListaEstadoPuesto() {
        return listaEstadoPuesto;
    }

    /**
     * @param listaEstadoPuesto the listaEstadoPuesto to set
     */
    public void setListaEstadoPuesto(final List<SelectItem> listaEstadoPuesto) {
        this.listaEstadoPuesto = listaEstadoPuesto;
    }

    /**
     * @return the mensajeValidaciones
     */
    public String getMensajeValidaciones() {
        return mensajeValidaciones;
    }

    /**
     * @param mensajeValidaciones the mensajeValidaciones to set
     */
    public void setMensajeValidaciones(final String mensajeValidaciones) {
        this.mensajeValidaciones = mensajeValidaciones;
    }
    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to
     * set
     */
    public void setListaUnidadesOrganizacionales(final List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the activo
     */
    public String getActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(String activo) {
        this.activo = activo;
    }

    /**
     * @return the listaServidores
     */
    public List<DistributivoDetalle> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<DistributivoDetalle> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the listaEstadoPersonal
     */
    public List<SelectItem> getListaEstadoPersonal() {
        return listaEstadoPersonal;
    }

    /**
     * @param listaEstadoPersonal the listaEstadoPersonal to set
     */
    public void setListaEstadoPersonal(List<SelectItem> listaEstadoPersonal) {
        this.listaEstadoPersonal = listaEstadoPersonal;
    }
    /**
     * @return the busquedaVacacionVO
     */
    public BusquedaVacacionVO getBusquedaVacacionVO() {
        return busquedaVacacionVO;
    }

    /**
     * @param busquedaVacacionVO the busquedaVacacionVO to set
     */
    public void setBusquedaVacacionVO(final BusquedaVacacionVO busquedaVacacionVO) {
        this.busquedaVacacionVO = busquedaVacacionVO;
    }

    /**
     * @return the listaPlanificacionVacacionDetalles
     */
    public List<PlanificacionVacacionDetalle> getListaPlanificacionVacacionDetalles() {
        return listaPlanificacionVacacionDetalles;
    }

    /**
     * @param listaPlanificacionVacacionDetalles the
     * listaPlanificacionVacacionDetalles to set
     */
    public void setListaPlanificacionVacacionDetalles(final List<PlanificacionVacacionDetalle> listaPlanificacionVacacionDetalles) {
        this.listaPlanificacionVacacionDetalles = listaPlanificacionVacacionDetalles;
    }

    /**
     * @return the listaEjercicioFiscal
     */
    public List<SelectItem> getListaEjercicioFiscal() {
        return listaEjercicioFiscal;
    }

    /**
     * @param listaEjercicioFiscal the listaEjercicioFiscal to set
     */
    public void setListaEjercicioFiscal(final List<SelectItem> listaEjercicioFiscal) {
        this.listaEjercicioFiscal = listaEjercicioFiscal;
    }

    /**
     * @return the listaSolicitudesVacaciones
     */
    public List<VacacionSolicitud> getListaSolicitudesVacaciones() {
        return listaSolicitudesVacaciones;
    }

    /**
     * @param listaSolicitudesVacaciones the listaSolicitudesVacaciones to set
     */
    public void setListaSolicitudesVacaciones(List<VacacionSolicitud> listaSolicitudesVacaciones) {
        this.listaSolicitudesVacaciones = listaSolicitudesVacaciones;
    }

    /**
     * @return the listaSaldoVacaciones
     */
    public List<Vacacion> getListaSaldoVacaciones() {
        return listaSaldoVacaciones;
    }

    /**
     * @param listaSaldoVacaciones the listaSaldoVacaciones to set
     */
    public void setListaSaldoVacaciones(List<Vacacion> listaSaldoVacaciones) {
        this.listaSaldoVacaciones = listaSaldoVacaciones;
    }

    /**
     * @return the listaOpcionesTipoVacacion
     */
    public List<SelectItem> getListaOpcionesTipoVacacion() {
        return listaOpcionesTipoVacacion;
    }

    /**
     * @param listaOpcionesTipoVacacion the listaOpcionesTipoVacacion to set
     */
    public void setListaOpcionesTipoVacacion(List<SelectItem> listaOpcionesTipoVacacion) {
        this.listaOpcionesTipoVacacion = listaOpcionesTipoVacacion;
    }

    /**
     * @return the listaOpcionesEstadoVacacion
     */
    public List<SelectItem> getListaOpcionesEstadoVacacion() {
        return listaOpcionesEstadoVacacion;
    }

    /**
     * @param listaOpcionesEstadoVacacion the listaOpcionesEstadoVacacion to set
     */
    public void setListaOpcionesEstadoVacacion(List<SelectItem> listaOpcionesEstadoVacacion) {
        this.listaOpcionesEstadoVacacion = listaOpcionesEstadoVacacion;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    } 
}