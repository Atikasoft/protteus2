/*
 *  PlanificacionVacacionHelper.java
 *
 *  PROTEUS V 2.0 $Revision 1.0 $
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  19/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "revertirVacacionesHelper")
@SessionScoped
public class RevertirVacacionesHelper extends CatalogoHelper {

    /**
     *
     */
    private List<SelectItem> opcionesTipoIdentificacion;
    /*
     * Variables de interaccion con la pantalla.
     */
    private boolean botonGuardar;

    /**
     * Ejercicio fiscal sobre el cual se realizara la planificacion
     */
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     *
     */
    private String tipoIdentificacion;
    /**
     *
     */
    private String nroIdentificacion;
    /**
     *
     */
    private String apellidosNombres;
    /**
     * ISTA DE OPCIONES POR LA QUE SE REALIZARA LA BUSQUEDA DE UNS SERVIDOR
     */
    private List<SelectItem> opcionesBuscarServidorPor;
    /**
     * OPCION SELECCIONADA DE LA LISTA DE OPCIONES PARA LA BUSQUEDA DE UNS
     * SERVIDOR
     */
    private String buscarServidorPor;
    /**
     * SERVIDOR ENCONTRADO Y SELECCIONADO COMO RESULTADO DE LA BUSQUEDA
     */
    private Servidor servidor;
    /**
     * LISTA DE DETALLES DE LOS PUESTOS OCUPADOS POR LOS SERVIDORES ENCOTNRADOS
     * AL REALIZAR LA BUSQUEDA
     */
    private List<DistributivoDetalle> listaDistributivosDetalles;
    /**
     * LISTA DE DETALLES DE LOS PUESTOS OCUPADOS POR LOS SERVIDORES ENCOTNRADOS
     * AL REALIZAR LA BUSQUEDA
     */
    private DistributivoDetalle distributivosDetalle;
    /**
     *
     */
    private Long saldoVacacion;
    /**
     *
     */
    private String saldoVacacionTexto;
    /**
     *
     */
    private Long saldoVacacionProporcional;
    /**
     *
     */
    private String saldoVacacionProporcionalTexto;
    
    /**
     * lista de vacacionSolicitudes Aprobadas para el servidor.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudesAprobadas;
    
    /**
     * 
     */
    private VacacionSolicitud solicitudVacionAprobada;

    /**
     * Método para iniciar las variables del PlanificacionVacacionHelper.
     */
    @PostConstruct
    public final void iniciador() {
        setOpcionesTipoIdentificacion(new ArrayList<SelectItem>());
        setBotonGuardar(false);
        setBuscarServidorPor(null);
        setServidor(null);
        setDistributivosDetalle(null);
        opcionesBuscarServidorPor = new ArrayList<SelectItem>();
        opcionesBuscarServidorPor.add(new SelectItem(null, "Seleccione..."));
        opcionesBuscarServidorPor.add(new SelectItem("id", "Identificación"));
        opcionesBuscarServidorPor.add(new SelectItem("an", "Apellidos Nombres"));
        listaDistributivosDetalles = new ArrayList<DistributivoDetalle>();
        listaVacacionSolicitudesAprobadas = new ArrayList<VacacionSolicitud>();
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getOpcionesTipoIdentificacion() {
        return opcionesTipoIdentificacion;
    }

    /**
     *
     * @param opcionesTipoIdentificacion
     */
    public void setOpcionesTipoIdentificacion(List<SelectItem> opcionesTipoIdentificacion) {
        this.opcionesTipoIdentificacion = opcionesTipoIdentificacion;
    }

    /**
     *
     * @return
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     *
     * @param tipoIdentificacion
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     *
     * @return
     */
    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    /**
     *
     * @param nroIdentificacion
     */
    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    /**
     *
     * @return
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     *
     * @param apellidosNombres
     */
    public void setApellidosNombres(String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @return the botonGuardar
     */
    public boolean isBotonGuardar() {
        return botonGuardar;
    }

    /**
     * @param botonGuardar the botonGuardar to set
     */
    public void setBotonGuardar(boolean botonGuardar) {
        this.botonGuardar = botonGuardar;
    }

    /**
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the
 institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(
            InstitucionEjercicioFiscal ejercicioFiscal) {
        this.institucionEjercicioFiscal = ejercicioFiscal;
    }

    /**
     *
     * @return
     */
    public List<SelectItem> getOpcionesBuscarServidorPor() {
        return opcionesBuscarServidorPor;
    }

    /**
     *
     * @param opcionesBuscarServidorPor
     */
    public void setOpcionesBuscarServidorPor(List<SelectItem> opcionesBuscarServidorPor) {
        this.opcionesBuscarServidorPor = opcionesBuscarServidorPor;
    }

    /**
     *
     * @return
     */
    public String getBuscarServidorPor() {
        return buscarServidorPor;
    }

    /**
     *
     * @param buscarServidorPor
     */
    public void setBuscarServidorPor(String buscarServidorPor) {
        this.buscarServidorPor = buscarServidorPor;
    }

    /**
     *
     * @return
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     *
     * @param servidor
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     *
     * @return
     */
    public List<DistributivoDetalle> getListaDistributivosDetalles() {
        return listaDistributivosDetalles;
    }

    /**
     *
     * @param listaServidores
     */
    public void setListaDistributivosDetalles(List<DistributivoDetalle> listaServidores) {
        this.listaDistributivosDetalles = listaServidores;
    }

    public DistributivoDetalle getDistributivosDetalle() {
        return distributivosDetalle;
    }

    public void setDistributivosDetalle(DistributivoDetalle distributivosDetalle) {
        this.distributivosDetalle = distributivosDetalle;
    }

    public Long getSaldoVacacion() {
        return saldoVacacion;
    }

    public void setSaldoVacacion(Long saldoVacacion) {
        this.saldoVacacion = saldoVacacion;
    }

    public String getSaldoVacacionTexto() {
        return saldoVacacionTexto;
    }

    public void setSaldoVacacionTexto(String saldoVacacionTexto) {
        this.saldoVacacionTexto = saldoVacacionTexto;
    }

    public Long getSaldoVacacionProporcional() {
        return saldoVacacionProporcional;
    }

    public void setSaldoVacacionProporcional(Long saldoVacacionProporcional) {
        this.saldoVacacionProporcional = saldoVacacionProporcional;
    }

    public String getSaldoVacacionProporcionalTexto() {
        return saldoVacacionProporcionalTexto;
    }

    public void setSaldoVacacionProporcionalTexto(String saldoVacacionProporcionalTexto) {
        this.saldoVacacionProporcionalTexto = saldoVacacionProporcionalTexto;
    }

    /**
     * 
     * @return 
     */
    public List<VacacionSolicitud> getListaVacacionSolicitudesAprobadas() {
        return listaVacacionSolicitudesAprobadas;
    }

    /**
     * 
     * @param listaVacacionSolicitudesAprobadas 
     */
    public void setListaVacacionSolicitudesAprobadas(List<VacacionSolicitud> listaVacacionSolicitudesAprobadas) {
        this.listaVacacionSolicitudesAprobadas = listaVacacionSolicitudesAprobadas;
    }

    /**
     * 
     * @return 
     */
    public VacacionSolicitud getSolicitudVacionAprobada() {
        return solicitudVacionAprobada;
    }

    /**
     * 
     * @param solicitudVacionAprobada 
     */
    public void setSolicitudVacionAprobada(VacacionSolicitud solicitudVacionAprobada) {
        this.solicitudVacionAprobada = solicitudVacionAprobada;
    }

}
