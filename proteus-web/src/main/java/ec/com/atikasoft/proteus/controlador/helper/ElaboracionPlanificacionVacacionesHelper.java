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
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
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
@ManagedBean(name = "elaboracionPlanificacionVacacionesHelper")
@SessionScoped
public class ElaboracionPlanificacionVacacionesHelper extends CatalogoHelper {

    /**
     * Variable de referencia planificacionVacacionVO.
     */
    private PlanificacionVacacionVO planificacionVacacionVO;

    /**
     * Variable para combo de opciones de periodos fiscales.
     */
    private List<SelectItem> opcionesEjercicioFiscal;

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
    private InstitucionEjercicioFiscal institucionEjercicioFiscalAPlanificarVacaciones;

    /**
     * VERIFICA SI EL PERIODO PARA REALIZAR LA PLANIFICACION DE VACACIONES ESTA
     * ACTIVO
     */
    private Boolean periodoParaPlanificarVacacionesEstaActivo;
    /**
     * FECHA INICIO DEL EJERCICIO FISCAL EN QUE SE REALIZARA LA PLANIFICACION
     */
    private String fechaInicioEjercicioFiscal;
    /**
     * FECHA FIN DEL EJERCICIO FISCAL EN QUE SE REALIZARA LA PLANIFICACION
     */
    private String fechaFinEjercicioFiscal;

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
     * NUMERO MAXIMO DE DIAS QUE SE PUEDEN SELECCIONAR PARA LA PLANIFICACION DE
     * VACACIONES DEPENDE DEL REGIMEN LABORAL
     */
    private int maximoDiasSeleccionables;
    
     /**
     * Opcion que determina si se debe filtrar por año o por mes
     */
    private boolean filtrarPorYear;    
    
    /**
     * 
     */
    private Integer mesSeleccionado;
    
    /*
     * LISTA DE MESES PARA EL FILTRO DE LA BUSQUEDA
     */
    private List<SelectItem> opcionesMeses;
    
    /**
     * Lista de vacaciones planificadas para 
     * el mes o el año actual (según filtro seleccionado)
     */
    private List<PlanificacionVacacionVO> planificacionesEncontradasVO;

    /**
     * Método para iniciar las variables del PlanificacionVacacionHelper.
     */
    @PostConstruct
    public final void iniciador() {
        setPlanificacionVacacionVO(new PlanificacionVacacionVO());
        setOpcionesEjercicioFiscal(new ArrayList<SelectItem>());
        setOpcionesTipoIdentificacion(new ArrayList<SelectItem>());
        setOpcionesMeses(new ArrayList<SelectItem>());
        setBotonGuardar(false);
        opcionesBuscarServidorPor = new ArrayList<SelectItem>();
        opcionesBuscarServidorPor.add(new SelectItem(null, "Seleccione..."));
        opcionesBuscarServidorPor.add(new SelectItem("id", "Identificación"));
        opcionesBuscarServidorPor.add(new SelectItem("an", "Apellidos Nombres"));
        listaDistributivosDetalles = new ArrayList<DistributivoDetalle>();
        setPlanificacionesEncontradasVO(new ArrayList<PlanificacionVacacionVO>());
        setFiltrarPorYear(false);
    }

    /**
     * @return the planificacionVacacionVO
     */
    public PlanificacionVacacionVO getPlanificacionVacacionVO() {
        return planificacionVacacionVO;
    }

    /**
     * @param planificacionVacacionVO the planificacionVacacionVO to set
     */
    public void setPlanificacionVacacionVO(PlanificacionVacacionVO planificacionVacacionVO) {
        this.planificacionVacacionVO = planificacionVacacionVO;
    }

    /**
     * @return the opcionesEjercicioFiscal
     */
    public List<SelectItem> getOpcionesEjercicioFiscal() {
        return opcionesEjercicioFiscal;
    }

    /**
     * @param opcionesEjercicioFiscal the opcionesEjercicioFiscal to set
     */
    public void setOpcionesEjercicioFiscal(List<SelectItem> opcionesEjercicioFiscal) {
        this.opcionesEjercicioFiscal = opcionesEjercicioFiscal;
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
     * @return the institucionEjercicioFiscalAPlanificarVacaciones
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscalAPlanificarVacaciones() {
        return institucionEjercicioFiscalAPlanificarVacaciones;
    }

    /**
     * @param ejercicioFiscal the
     * institucionEjercicioFiscalAPlanificarVacaciones to set
     */
    public void setInstitucionEjercicioFiscalAPlanificarVacaciones(
            InstitucionEjercicioFiscal ejercicioFiscal) {
        this.institucionEjercicioFiscalAPlanificarVacaciones = ejercicioFiscal;
    }

    /**
     *
     * @return
     */
    public Boolean getPeriodoParaPlanificarVacacionesEstaActivo() {
        return periodoParaPlanificarVacacionesEstaActivo;
    }

    /**
     *
     * @param periodoParaPlanificarVacacionesEstaActivo
     */
    public void setPeriodoParaPlanificarVacacionesEstaActivo(Boolean periodoParaPlanificarVacacionesEstaActivo) {
        this.periodoParaPlanificarVacacionesEstaActivo = periodoParaPlanificarVacacionesEstaActivo;
    }

    public String getFechaInicioEjercicioFiscal() {
        return fechaInicioEjercicioFiscal;
    }

    public void setFechaInicioEjercicioFiscal(String fechaInicioEjercicioFiscal) {
        this.fechaInicioEjercicioFiscal = fechaInicioEjercicioFiscal;
    }

    public String getFechaFinEjercicioFiscal() {
        return fechaFinEjercicioFiscal;
    }

    public void setFechaFinEjercicioFiscal(String fechaFinEjercicioFiscal) {
        this.fechaFinEjercicioFiscal = fechaFinEjercicioFiscal;
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

    /**
     *
     * @return
     */
    public int getMaximoDiasSeleccionables() {
        return maximoDiasSeleccionables;
    }

    /**
     *
     * @param maximoDiasSeleccionables
     */
    public void setMaximoDiasSeleccionables(int maximoDiasSeleccionables) {
        this.maximoDiasSeleccionables = maximoDiasSeleccionables;
    }
    
    public boolean getFiltrarPorYear() {
        return filtrarPorYear;
    }

    public void setFiltrarPorYear(boolean filtrarPorYear) {
        this.filtrarPorYear = filtrarPorYear;
    }

     public Integer getMesSeleccionado() {
        return mesSeleccionado;
    }

    public void setMesSeleccionado(Integer mesSeleccionado) {
        this.mesSeleccionado = mesSeleccionado;
    }

    public List<SelectItem> getOpcionesMeses() {
        return opcionesMeses;
    }

    public void setOpcionesMeses(List<SelectItem> opcionesMeses) {
        this.opcionesMeses = opcionesMeses;
    }

    public List<PlanificacionVacacionVO> getPlanificacionesEncontradasVO() {
        return planificacionesEncontradasVO;
    }

    public void setPlanificacionesEncontradasVO(List<PlanificacionVacacionVO> planificacionesEncontradasVO) {
        this.planificacionesEncontradasVO = planificacionesEncontradasVO;
    }

}
