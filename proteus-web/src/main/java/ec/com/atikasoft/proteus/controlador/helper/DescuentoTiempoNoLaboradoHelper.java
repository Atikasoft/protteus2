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

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
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
@ManagedBean(name = "descuentoTiempoNoLaboradoHelper")
@SessionScoped
public class DescuentoTiempoNoLaboradoHelper {
    /**
     *
     */
    private List<SelectItem> opcionesTipoIdentificacion;
    
    /*
     * Variables de interaccion con la pantalla.
     */
    private boolean botonDescontar;
    
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
     * OPCION SELECCIONADA DE LA LISTA DE OPCIONES PARA LA BUSQUEDA DE UNS SERVIDOR
     */
    private String buscarServidorPor;
    
    /**
     * DISTRIBUTIVO DEL SERVIDOR ENCONTRADO Y SELECCIONADO COMO RESULTADO DE LA BUSQUEDA
     */
    private DistributivoDetalle distributivoDetalle;
    
    /**
     * LISTA DE DETALLES DE LOS PUESTOS OCUPADOS POR LOS SERVIDORES ENCOTNRADOS AL REALIZAR LA BUSQUEDA
     */
    private List<DistributivoDetalle> listaDistributivosDetalles;
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
     * 
     */
    private Long minutosADescontar;
    /**
     * 
     */
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Método para iniciar las variables del PlanificacionVacacionHelper.
     */
    @PostConstruct
    public final void iniciador() {
        setOpcionesTipoIdentificacion(new ArrayList<SelectItem>());
        setBotonDescontar(false);
        setBuscarServidorPor(null);
        opcionesBuscarServidorPor = new ArrayList<SelectItem>();
        opcionesBuscarServidorPor.add(new SelectItem(null, "Seleccione..."));
        opcionesBuscarServidorPor.add(new SelectItem("id", "Identificación"));
        opcionesBuscarServidorPor.add(new SelectItem("an", "Apellidos Nombres"));
        listaDistributivosDetalles = new ArrayList<DistributivoDetalle>();
        saldoVacacionTexto = "0 Días";
        saldoVacacionProporcionalTexto = "0 Días";
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
     * @return the botonDescontar
     */
    public boolean isBotonDescontar() {
        return botonDescontar;
    }

    /**
     * @param botonDescontar the botonDescontar to set
     */
    public void setBotonDescontar(boolean botonDescontar) {
        this.botonDescontar = botonDescontar;
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

    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
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

    /**
     * 
     * @return 
     */
    public Long getSaldoVacacion() {
        return saldoVacacion;
    }

    /**
     * 
     * @param saldoVacacion 
     */
    public void setSaldoVacacion(Long saldoVacacion) {
        this.saldoVacacion = saldoVacacion;
    }

    /**
     * 
     * @return 
     */
    public String getSaldoVacacionTexto() {
        return saldoVacacionTexto;
    }

    /**
     * 
     * @param saldoVacacionTexto 
     */
    public void setSaldoVacacionTexto(String saldoVacacionTexto) {
        this.saldoVacacionTexto = saldoVacacionTexto;
    }

    /**
     * 
     * @return 
     */
    public Long getSaldoVacacionProporcional() {
        return saldoVacacionProporcional;
    }

    /**
     * 
     * @param saldoVacacionProporcional 
     */
    public void setSaldoVacacionProporcional(Long saldoVacacionProporcional) {
        this.saldoVacacionProporcional = saldoVacacionProporcional;
    }

    /**
     * 
     * @return 
     */
    public String getSaldoVacacionProporcionalTexto() {
        return saldoVacacionProporcionalTexto;
    }

    /**
     * 
     * @param saldoVacacionProporcionalTexto 
     */
    public void setSaldoVacacionProporcionalTexto(String saldoVacacionProporcionalTexto) {
        this.saldoVacacionProporcionalTexto = saldoVacacionProporcionalTexto;
    }

    /**
     * 
     * @return 
     */
    public Long getMinutosADescontar() {
        return minutosADescontar;
    }

    /**
     * 
     * @param minutosADescontar 
     */
    public void setMinutosADescontar(Long minutosADescontar) {
        this.minutosADescontar = minutosADescontar;
    }

    /**
     * 
     * @return 
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * 
     * @param institucionEjercicioFiscal 
     */
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }
    
}
