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
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * PlanificacionVacacion
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionVacacionHelper")
@SessionScoped
public class PlanificacionVacacionHelper extends CatalogoHelper {


    /**
     * clase planificacionVacacion puesto para editar.
     */
    private PlanificacionVacacion planificacionVacacionEditDelete;
    /**
     * lista de planificacionVacacions.
     */
    private List<PlanificacionVacacion> listaPlanificacionVacaciones;
    /**
     * Variable para listar las planificaciones de vacaciones para verificacion de duplicados.
     */
    private List<PlanificacionVacacion> listaPlanificacionVacacionCodigo;
       /**
     * Variable para listar los ejercicios fiscales.
     */
    private List<InstitucionEjercicioFiscal> listaInstitucionEjercicioFiscal;
    
    /**
     * Variable de referencia planificacionVacacionVO.
     */
    private PlanificacionVacacionVO planificacionVacacionVO;
    
    /**
     * Variable para combo de opciones de periodos fiscales.
     */
    private List<SelectItem> opcionesEjercicioFiscal;
    
     private List<PlanificacionVacacionDetalle> listaPlanificacionVacacionDet;
     private PlanificacionVacacionDetalle planificacionVacacionDetReprogramar;
     
     private Integer diasDisponiblesVacaciones;
    /*
     * Variables de interaccion con la pantalla.
     */
    private boolean botonGuardar;
    private boolean editarNuevo;
    private String mensajes;
    private boolean hayParametros;
    
    private String observacionReprogramacion="";

    /**
     * Variable para busqueda
     */
    private Long periodofiscal;
    /**
     * Constructor por defecto.
     */
    public PlanificacionVacacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del PlanificacionVacacionHelper.
     */
    public final void iniciador() {
        setPlanificacionVacacionEditDelete(new PlanificacionVacacion());
        setListaPlanificacionVacaciones(new ArrayList<PlanificacionVacacion>());
        setListaPlanificacionVacacionCodigo(new ArrayList<PlanificacionVacacion>());
        setPlanificacionVacacionVO(new PlanificacionVacacionVO());
        setOpcionesEjercicioFiscal(new ArrayList<SelectItem>());
        setListaInstitucionEjercicioFiscal(new ArrayList<InstitucionEjercicioFiscal>());
        setBotonGuardar(false);
        setEditarNuevo(false);
        mensajes="";
       listaPlanificacionVacacionDet=new ArrayList<PlanificacionVacacionDetalle>();
       planificacionVacacionDetReprogramar=new PlanificacionVacacionDetalle();
     
       diasDisponiblesVacaciones=0;
    }

    /**
     * @return the planificacionVacacionEditDelete
     */
    public PlanificacionVacacion getPlanificacionVacacionEditDelete() {
        return planificacionVacacionEditDelete;
    }

    /**
     * @param planificacionVacacionEditDelete the
     * planificacionVacacionEditDelete to set
     */
    public void setPlanificacionVacacionEditDelete(final PlanificacionVacacion planificacionVacacionEditDelete) {
        this.planificacionVacacionEditDelete = planificacionVacacionEditDelete;
    }

    /**
     * @return the listaPlanificacionVacaciones
     */
    public List<PlanificacionVacacion> getListaPlanificacionVacaciones() {
        return listaPlanificacionVacaciones;
    }

    /**
     * @param listaPlanificacionVacaciones the listaPlanificacionVacaciones to
     * set
     */
    public void setListaPlanificacionVacaciones(final List<PlanificacionVacacion> listaPlanificacionVacaciones) {
        this.listaPlanificacionVacaciones = listaPlanificacionVacaciones;
    }

    /**
     * @return the listaPlanificacionVacacionCodigo
     */
    public List<PlanificacionVacacion> getListaPlanificacionVacacionCodigo() {
        return listaPlanificacionVacacionCodigo;
    }

    /**
     * @param listaPlanificacionVacacionCodigo the
     * listaPlanificacionVacacionCodigo to set
     */
    public void setListaPlanificacionVacacionCodigo(final List<PlanificacionVacacion> listaPlanificacionVacacionCodigo) {
        this.listaPlanificacionVacacionCodigo = listaPlanificacionVacacionCodigo;
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
     * @return the listaInstitucionEjercicioFiscal
     */
    public List<InstitucionEjercicioFiscal> getListaInstitucionEjercicioFiscal() {
        return listaInstitucionEjercicioFiscal;
    }

    /**
     * @param listaInstitucionEjercicioFiscal the listaInstitucionEjercicioFiscal to set
     */
    public void setListaInstitucionEjercicioFiscal(List<InstitucionEjercicioFiscal> listaInstitucionEjercicioFiscal) {
        this.listaInstitucionEjercicioFiscal = listaInstitucionEjercicioFiscal;
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
     * @return the editarNuevo
     */
    public boolean isEditarNuevo() {
        return editarNuevo;
    }

    /**
     * @param editarNuevo the editarNuevo to set
     */
    public void setEditarNuevo(boolean editarNuevo) {
        this.editarNuevo = editarNuevo;
    }

    /**
     * @return the mensajes
     */
    public String getMensajes() {
        return mensajes;
    }

    /**
     * @param mensajes the mensajes to set
     */
    public void setMensajes(String mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * @return the periodofiscal
     */
    public Long getPeriodofiscal() {
        return periodofiscal;
    }

    /**
     * @param periodofiscal the periodofiscal to set
     */
    public void setPeriodofiscal(Long periodofiscal) {
        this.periodofiscal = periodofiscal;
    }

    /**
     * @return the hayParametros
     */
    public boolean isHayParametros() {
        return hayParametros;
    }

    /**
     * @param hayParametros the hayParametros to set
     */
    public void setHayParametros(boolean hayParametros) {
        this.hayParametros = hayParametros;
    }

    /**
     * @return the listaPlanificacionVacacionDet
     */
    public List<PlanificacionVacacionDetalle> getListaPlanificacionVacacionDet() {
        return listaPlanificacionVacacionDet;
    }

    /**
     * @param listaPlanificacionVacacionDet the listaPlanificacionVacacionDet to set
     */
    public void setListaPlanificacionVacacionDet(List<PlanificacionVacacionDetalle> listaPlanificacionVacacionDet) {
        this.listaPlanificacionVacacionDet = listaPlanificacionVacacionDet;
    }

    /**
     * @return the planificacionVacacionDetReprogramar
     */
    public PlanificacionVacacionDetalle getPlanificacionVacacionDetReprogramar() {
        return planificacionVacacionDetReprogramar;
    }

    /**
     * @param planificacionVacacionDetReprogramar the planificacionVacacionDetReprogramar to set
     */
    public void setPlanificacionVacacionDetReprogramar(PlanificacionVacacionDetalle planificacionVacacionDetReprogramar) {
        this.planificacionVacacionDetReprogramar = planificacionVacacionDetReprogramar;
    }

    /**
     * @return the diasDisponiblesVacaciones
     */
    public Integer getDiasDisponiblesVacaciones() {
        return diasDisponiblesVacaciones;
    }

    /**
     * @param diasDisponiblesVacaciones the diasDisponiblesVacaciones to set
     */
    public void setDiasDisponiblesVacaciones(Integer diasDisponiblesVacaciones) {
        this.diasDisponiblesVacaciones = diasDisponiblesVacaciones;
    }

    /**
     * @return the observacionReprogramacion
     */
    public String getObservacionReprogramacion() {
        return observacionReprogramacion;
    }

    /**
     * @param observacionReprogramacion the observacionReprogramacion to set
     */
    public void setObservacionReprogramacion(String observacionReprogramacion) {
        this.observacionReprogramacion = observacionReprogramacion;
    }
}
