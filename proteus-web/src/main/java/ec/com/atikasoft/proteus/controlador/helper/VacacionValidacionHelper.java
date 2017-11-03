/*
 *  VacacionAprobacionHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  04/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Banco
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionValidacionHelper")
@SessionScoped
public class VacacionValidacionHelper extends CatalogoHelper {

    /**
     * clase vacacionSolicitud.
     */
    private VacacionSolicitud vacacionSolicitud;

    /**
     * clase vacacionSolicitud  para editar.
     */
    private VacacionSolicitud vacacionSolicitudEditDelete;
       /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    private UnidadOrganizacional unidadOrganizacional= new UnidadOrganizacional();

    /**
     * lista de vacacionSolicitud.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudes;

    /**
     * Variables para opciones tipo vacacion y estado vacacion
     */
   
    private List<SelectItem> listaOpcionesEstadoVacacion;
  
    /*
     * Variable para mostrar saldo de vacaciones del servidor
     */
    private String cadenaSaldo;
    private String cadenaTiempoEmpresa;
     private Boolean guardado = Boolean.FALSE;
     private Boolean esRRHH = Boolean.FALSE;

    /**
     * Variable para actualizar el saldo de las vacaciones
     */
    private Vacacion vacacion;
    /**
     * Constructor por defecto.
     */
    public VacacionValidacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {
        vacacionSolicitud=new VacacionSolicitud();
        vacacionSolicitudEditDelete=new VacacionSolicitud();
        listaVacacionSolicitudes=new ArrayList<VacacionSolicitud>();
        listaOpcionesEstadoVacacion=new ArrayList<SelectItem>();
        setCadenaSaldo("");
        setCadenaTiempoEmpresa("");
        setVacacion(new Vacacion());
    }

    /**
     * @return the vacacionSolicitud
     */
    public VacacionSolicitud getVacacionSolicitud() {
        return vacacionSolicitud;
    }

    /**
     * @param vacacionSolicitud the vacacionSolicitud to set
     */
    public void setVacacionSolicitud(VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
    }

    /**
     * @return the vacacionSolicitudEditDelete
     */
    public VacacionSolicitud getVacacionSolicitudEditDelete() {
        return vacacionSolicitudEditDelete;
    }

    /**
     * @param vacacionSolicitudEditDelete the vacacionSolicitudEditDelete to set
     */
    public void setVacacionSolicitudEditDelete(VacacionSolicitud vacacionSolicitudEditDelete) {
        this.vacacionSolicitudEditDelete = vacacionSolicitudEditDelete;
    }

    /**
     * @return the listaVacacionSolicitudes
     */
    public List<VacacionSolicitud> getListaVacacionSolicitudes() {
        return listaVacacionSolicitudes;
    }

    /**
     * @param listaVacacionSolicitudes the listaVacacionSolicitudes to set
     */
    public void setListaVacacionSolicitudes(List<VacacionSolicitud> listaVacacionSolicitudes) {
        this.listaVacacionSolicitudes = listaVacacionSolicitudes;
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
     * @return the cadenaSaldo
     */
    public String getCadenaSaldo() {
        return cadenaSaldo;
    }

    /**
     * @param cadenaSaldo the cadenaSaldo to set
     */
    public void setCadenaSaldo(String cadenaSaldo) {
        this.cadenaSaldo = cadenaSaldo;
    }

    /**
     * @return the cadenaTiempoEmpresa
     */
    public String getCadenaTiempoEmpresa() {
        return cadenaTiempoEmpresa;
    }

    /**
     * @param cadenaTiempoEmpresa the cadenaTiempoEmpresa to set
     */
    public void setCadenaTiempoEmpresa(String cadenaTiempoEmpresa) {
        this.cadenaTiempoEmpresa = cadenaTiempoEmpresa;
    }
    /**
     * @return the vacacion
     */
    public Vacacion getVacacion() {
        return vacacion;
    }

    /**
     * @param vacacion the vacacion to set
     */
    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }

    /**
     * @return the guardado
     */
    public Boolean getGuardado() {
        return guardado;
    }

    /**
     * @param guardado the guardado to set
     */
    public void setGuardado(Boolean guardado) {
        this.guardado = guardado;
    }

    /**
     * @return the listaUnidadesOrganizacionales
     */
    public List<UnidadOrganizacional> getListaUnidadesOrganizacionales() {
        return listaUnidadesOrganizacionales;
    }

    /**
     * @param listaUnidadesOrganizacionales the listaUnidadesOrganizacionales to set
     */
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the esRRHH
     */
    public Boolean getEsRRHH() {
        return esRRHH;
    }

    /**
     * @param esRRHH the esRRHH to set
     */
    public void setEsRRHH(Boolean esRRHH) {
        this.esRRHH = esRRHH;
    }
  
}
