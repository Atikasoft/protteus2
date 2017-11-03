/*
 *  PlanificacionHorarioHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
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
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PlanificacionHorario;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * PlanificacionHorario
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionHorarioHelper")
@SessionScoped
public class PlanificacionHorarioHelper extends CatalogoHelper {
  /**
     * clase planificacionHorario puesto para editar.
     */
    private PlanificacionHorario planificacionHorarioEditDelete;
    
      /**
     * clase planificacionHorario puesto para editar.
     */
    private PlanificacionHorario planificacionHorario;

    /**
     * lista de planificacionHorarios.
     */
    private List<PlanificacionHorario> listaPlanificacionHorarios;

    /**
     * Variable para listar las alertas por duplicados.
     */
    private List<PlanificacionHorario> listaPlanificacionHorarioDuplicado;
   
    /**
     * Variables para asignación del garante del planificacionHorario.
     */
    private List<Servidor> listaServidores;
    /**
     * Variable para combos.
     */
      private List<SelectItem> listaOpcionMeses;
      
      private List<SelectItem> listaOpcionEjercicioFiscal;
      
      private Servidor servidor;
   
    /**
     * nombreServidor.
     */
    private String nombreServidor;
    /**
     * nombreServidor.
     */
    private String numeroIdentificacion;
    /**
     * Constructor por defecto.
     */
    public PlanificacionHorarioHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del PlanificacionHorarioHelper.
     */
    public final void iniciador() {
        setPlanificacionHorarioEditDelete(new PlanificacionHorario());
        setListaPlanificacionHorarios(new ArrayList<PlanificacionHorario>());
       listaServidores=new ArrayList<Servidor>();
       planificacionHorario = new PlanificacionHorario();
       listaPlanificacionHorarioDuplicado = new ArrayList<PlanificacionHorario>();
       listaPlanificacionHorarios = new ArrayList<PlanificacionHorario>();
       listaOpcionMeses=new ArrayList<SelectItem>();
       listaOpcionEjercicioFiscal=new ArrayList<SelectItem>();
       planificacionHorario.setInstitucionEjercicioFiscal(new InstitucionEjercicioFiscal());
       planificacionHorario.setServidor(new Servidor());
       servidor=new Servidor();
    }
    /**
     * @return the planificacionHorarioEditDelete
     */
    public PlanificacionHorario getPlanificacionHorarioEditDelete() {
        return planificacionHorarioEditDelete;
    }

    /**
     * @param planificacionHorarioEditDelete the planificacionHorarioEditDelete to set
     */
    public void setPlanificacionHorarioEditDelete(final PlanificacionHorario planificacionHorarioEditDelete) {
        this.planificacionHorarioEditDelete = planificacionHorarioEditDelete;
    }

    /**
     * @return the listaPlanificacionHorarios
     */
    public List<PlanificacionHorario> getListaPlanificacionHorarios() {
        return listaPlanificacionHorarios;
    }

    /**
     * @param listaPlanificacionHorarios the listaPlanificacionHorarios to set
     */
    public void setListaPlanificacionHorarios(final List<PlanificacionHorario> listaPlanificacionHorarios) {
        this.listaPlanificacionHorarios = listaPlanificacionHorarios;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the planificacionHorario
     */
    public PlanificacionHorario getPlanificacionHorario() {
        return planificacionHorario;
    }

    /**
     * @param planificacionHorario the planificacionHorario to set
     */
    public void setPlanificacionHorario(PlanificacionHorario planificacionHorario) {
        this.planificacionHorario = planificacionHorario;
    }

    /**
     * @return the listaPlanificacionHorarioDuplicado
     */
    public List<PlanificacionHorario> getListaPlanificacionHorarioDuplicado() {
        return listaPlanificacionHorarioDuplicado;
    }

    /**
     * @param listaPlanificacionHorarioDuplicado the listaPlanificacionHorarioDuplicado to set
     */
    public void setListaPlanificacionHorarioDuplicado(List<PlanificacionHorario> listaPlanificacionHorarioDuplicado) {
        this.listaPlanificacionHorarioDuplicado = listaPlanificacionHorarioDuplicado;
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

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the listaOpcionMeses
     */
    public List<SelectItem> getListaOpcionMeses() {
        return listaOpcionMeses;
    }

    /**
     * @param listaOpcionMeses the listaOpcionMeses to set
     */
    public void setListaOpcionMeses(List<SelectItem> listaOpcionMeses) {
        this.listaOpcionMeses = listaOpcionMeses;
    }

    /**
     * @return the listaOpcionEjercicioFiscal
     */
    public List<SelectItem> getListaOpcionEjercicioFiscal() {
        return listaOpcionEjercicioFiscal;
    }

    /**
     * @param listaOpcionEjercicioFiscal the listaOpcionEjercicioFiscal to set
     */
    public void setListaOpcionEjercicioFiscal(List<SelectItem> listaOpcionEjercicioFiscal) {
        this.listaOpcionEjercicioFiscal = listaOpcionEjercicioFiscal;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
}