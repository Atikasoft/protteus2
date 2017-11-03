/*
 *  RolServidorHelper.java
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
 *  21/01/2014O
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DualListModel;

/**
 * RolServidor
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rolServidorHelper")
@SessionScoped
public class RolServidorHelper extends CatalogoHelper {
  /**
     * clase rolServidor puesto para editar.
     */
    private RolServidor rolServidorEditDelete;
    
      /**
     * clase rolServidor puesto para editar.
     */
    private RolServidor rolServidor;

    /**
     * lista de rolServidors para la asignacion.
     */
    private List<RolServidor> listaRolPorServidor;
    /**
     * Variables para el pick List.
     */
    private List<Rol> listaOrigen;
    private List<Rol> listaDestino;
    private DualListModel<Rol> listaRolesServidores; 

    /**
     * Variable para listar los registros por duplicados.
     */
    private List<RolServidor> listaRolServidorDuplicado;
    
       /**
     * Variable para listar los roles vigentes.
     */
    private List<Rol> listaRolesVigentes;
   
    /**
     * Variables para asignación del garante del rolServidor.
     */
    private List<DistributivoDetalle> listaServidores;
    /**
     * Variable para combos.
     */
      private List<SelectItem> listaOpcionMeses;
      
      private List<SelectItem> listaOpcionEjercicioFiscal;
      
      private Servidor servidor;
      private DistributivoDetalle distributivoDetalle;
   
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
    public RolServidorHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del RolServidorHelper.
     */
    public final void iniciador() {
        setRolServidorEditDelete(new RolServidor());
        listaRolPorServidor=new ArrayList<RolServidor>();
       listaServidores=new ArrayList<DistributivoDetalle>();
       rolServidor = new RolServidor();
       listaRolServidorDuplicado = new ArrayList<RolServidor>();
       listaOpcionMeses=new ArrayList<SelectItem>();
       listaOpcionEjercicioFiscal=new ArrayList<SelectItem>();
       rolServidor.setServidor(new Servidor());
       rolServidor.setRol(new Rol());
       servidor=new Servidor();
       listaOrigen = new ArrayList<Rol>();
       listaDestino = new ArrayList<Rol>();
       distributivoDetalle = new DistributivoDetalle();
       listaRolesVigentes = new ArrayList<Rol>();
       listaRolesServidores = new DualListModel<Rol>();
    }
    /**
     * @return the rolServidorEditDelete
     */
    public RolServidor getRolServidorEditDelete() {
        return rolServidorEditDelete;
    }

    /**
     * @param rolServidorEditDelete the rolServidorEditDelete to set
     */
    public void setRolServidorEditDelete(final RolServidor rolServidorEditDelete) {
        this.rolServidorEditDelete = rolServidorEditDelete;
    }
       /**
     * @return the listaServidores..
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
     * @return the rolServidor
     */
    public RolServidor getRolServidor() {
        return rolServidor;
    }

    /**
     * @param rolServidor the rolServidor to set
     */
    public void setRolServidor(RolServidor rolServidor) {
        this.rolServidor = rolServidor;
    }

    /**
     * @return the listaRolServidorDuplicado
     */
    public List<RolServidor> getListaRolServidorDuplicado() {
        return listaRolServidorDuplicado;
    }

    /**
     * @param listaRolServidorDuplicado the listaRolServidorDuplicado to set
     */
    public void setListaRolServidorDuplicado(List<RolServidor> listaRolServidorDuplicado) {
        this.listaRolServidorDuplicado = listaRolServidorDuplicado;
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

    /**
     * @return the listaRolPorServidor
     */
    public List<RolServidor> getListaRolPorServidor() {
        return listaRolPorServidor;
    }

    /**
     * @param listaRolPorServidor the listaRolPorServidor to set
     */
    public void setListaRolPorServidor(List<RolServidor> listaRolPorServidor) {
        this.listaRolPorServidor = listaRolPorServidor;
    }

    /**
     * @return the listaOrigen
     */
    public List<Rol> getListaOrigen() {
        return listaOrigen;
    }

    /**
     * @param listaOrigen the listaOrigen to set
     */
    public void setListaOrigen(List<Rol> listaOrigen) {
        this.listaOrigen = listaOrigen;
    }

    /**
     * @return the listaDestino
     */
    public List<Rol> getListaDestino() {
        return listaDestino;
    }

    /**
     * @param listaDestino the listaDestino to set
     */
    public void setListaDestino(List<Rol> listaDestino) {
        this.listaDestino = listaDestino;
    }

    /**
     * @return the listaRolesServidores
     */
    public DualListModel<Rol> getListaRolesServidores() {
        return listaRolesServidores;
    }

    /**
     * @param listaRolesServidores the listaRolesServidores to set
     */
    public void setListaRolesServidores(DualListModel<Rol> listaRolesServidores) {
        this.listaRolesServidores = listaRolesServidores;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the listaRolesVigentes
     */
    public List<Rol> getListaRolesVigentes() {
        return listaRolesVigentes;
    }

    /**
     * @param listaRolesVigentes the listaRolesVigentes to set
     */
    public void setListaRolesVigentes(List<Rol> listaRolesVigentes) {
        this.listaRolesVigentes = listaRolesVigentes;
    }
}