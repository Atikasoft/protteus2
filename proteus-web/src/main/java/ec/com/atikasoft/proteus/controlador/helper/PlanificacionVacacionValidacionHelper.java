/*
 *  PlanificacionVacacionValidacionHelper.java
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
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * PlanificacionVacacionValidacionHelper
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionVacacionValidacionHelper")
@SessionScoped
public class PlanificacionVacacionValidacionHelper extends CatalogoHelper {

    /**
     * Variable de referencia planificacionVacacionVO.
     */
    private PlanificacionVacacionVO planificacionVacacionVO;
    /**
     * Variable para almacenar las busquedadas.
     */
    private List<PlanificacionVacacionVO> listaVacacionVO;
    private String observacionAprobacion;

    /**
     * clase vacacionSolicitud para editar.
     */
    private PlanificacionVacacion vacacionSolicitudEditDelete;

    /**
     * lista de vacacionSolicitud.
     */
    private List<PlanificacionVacacion> listaPlanificacionVacaciones;

    /**
     * Variable para creacion del arbol de unidades organizacionales.
     */
    private TreeNode rootUnidadOrganizacional;
    private TreeNode unidadSeleccionada;
    /**
     * Variable para cargar filtros de búsqueda.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales;

    private List<SelectItem> listaOpcionesEjercicioFiscal;
    private List<SelectItem> listaOpcionesEstadoPlanifVacacion;

    private Long ejercicioFiscal;
    private String estado;
    private UnidadOrganizacional unidadOrganizacional;

    private Boolean guardado = Boolean.FALSE;
    private Boolean esRRHH = Boolean.FALSE;

    private String cadenaSaldo;

    private List<Vacacion> listaSaldoVacaciones;

    /**
     * Constructor por defecto.
     */
    public PlanificacionVacacionValidacionHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {
        setPlanificacionVacacionEditDelete(new PlanificacionVacacion());
        setListaPlanificacionVacaciones(new ArrayList<PlanificacionVacacion>());
        planificacionVacacionVO = new PlanificacionVacacionVO();
        listaVacacionVO = new ArrayList<PlanificacionVacacionVO>();
        unidadOrganizacional = new UnidadOrganizacional();
        listaOpcionesEjercicioFiscal = new ArrayList<SelectItem>();
        listaOpcionesEstadoPlanifVacacion = new ArrayList<SelectItem>();
        listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
        rootUnidadOrganizacional = new DefaultTreeNode();
        unidadSeleccionada = new DefaultTreeNode();
        listaSaldoVacaciones = new ArrayList<Vacacion>();
    }

    /**
     * @return the vacacionSolicitudEditDelete
     */
    public PlanificacionVacacion getPlanificacionVacacionEditDelete() {
        return getVacacionSolicitudEditDelete();
    }

    /**
     * @param vacacionSolicitudEditDelete the vacacionSolicitudEditDelete to set
     */
    public void setPlanificacionVacacionEditDelete(PlanificacionVacacion vacacionSolicitudEditDelete) {
        this.setVacacionSolicitudEditDelete(vacacionSolicitudEditDelete);
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
    public void setListaPlanificacionVacaciones(List<PlanificacionVacacion> listaPlanificacionVacaciones) {
        this.listaPlanificacionVacaciones = listaPlanificacionVacaciones;
    }

    /**
     * @return the vacacionSolicitudEditDelete
     */
    public PlanificacionVacacion getVacacionSolicitudEditDelete() {
        return vacacionSolicitudEditDelete;
    }

    /**
     * @param vacacionSolicitudEditDelete the vacacionSolicitudEditDelete to set
     */
    public void setVacacionSolicitudEditDelete(PlanificacionVacacion vacacionSolicitudEditDelete) {
        this.vacacionSolicitudEditDelete = vacacionSolicitudEditDelete;
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
     * @return the listaVacacionVO
     */
    public List<PlanificacionVacacionVO> getListaVacacionVO() {
        return listaVacacionVO;
    }

    /**
     * @param listaVacacionVO the listaVacacionVO to set
     */
    public void setListaVacacionVO(List<PlanificacionVacacionVO> listaVacacionVO) {
        this.listaVacacionVO = listaVacacionVO;
    }

    /**
     * @return the listaOpcionesEjercicioFiscal
     */
    public List<SelectItem> getListaOpcionesEjercicioFiscal() {
        return listaOpcionesEjercicioFiscal;
    }

    /**
     * @param listaOpcionesEjercicioFiscal the listaOpcionesEjercicioFiscal to
     * set
     */
    public void setListaOpcionesEjercicioFiscal(List<SelectItem> listaOpcionesEjercicioFiscal) {
        this.listaOpcionesEjercicioFiscal = listaOpcionesEjercicioFiscal;
    }

    /**
     * @return the listaOpcionesEstadoPlanifVacacion
     */
    public List<SelectItem> getListaOpcionesEstadoPlanifVacacion() {
        return listaOpcionesEstadoPlanifVacacion;
    }

    /**
     * @param listaOpcionesEstadoPlanifVacacion the
     * listaOpcionesEstadoPlanifVacacion to set
     */
    public void setListaOpcionesEstadoPlanifVacacion(List<SelectItem> listaOpcionesEstadoPlanifVacacion) {
        this.listaOpcionesEstadoPlanifVacacion = listaOpcionesEstadoPlanifVacacion;
    }

    /**
     * @return the observacionAprobacion
     */
    public String getObservacionAprobacion() {
        return observacionAprobacion;
    }

    /**
     * @param observacionAprobacion the observacionAprobacion to set
     */
    public void setObservacionAprobacion(String observacionAprobacion) {
        this.observacionAprobacion = observacionAprobacion;
    }

    /**
     * @return the ejercicioFiscal
     */
    public Long getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(Long ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the rootUnidadOrganizacional
     */
    public TreeNode getRootUnidadOrganizacional() {
        return rootUnidadOrganizacional;
    }

    /**
     * @param rootUnidadOrganizacional the rootUnidadOrganizacional to set
     */
    public void setRootUnidadOrganizacional(TreeNode rootUnidadOrganizacional) {
        this.rootUnidadOrganizacional = rootUnidadOrganizacional;
    }

    /**
     * @return the unidadSeleccionada
     */
    public TreeNode getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    /**
     * @param unidadSeleccionada the unidadSeleccionada to set
     */
    public void setUnidadSeleccionada(TreeNode unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
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
    public void setListaUnidadesOrganizacionales(List<UnidadOrganizacional> listaUnidadesOrganizacionales) {
        this.listaUnidadesOrganizacionales = listaUnidadesOrganizacionales;
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

    public String getCadenaSaldo() {
        return cadenaSaldo;
    }

    public void setCadenaSaldo(String cadenaSaldo) {
        this.cadenaSaldo = cadenaSaldo;
    }

    public List<Vacacion> getListaSaldoVacaciones() {
        return listaSaldoVacaciones;
    }

    public void setListaSaldoVacaciones(List<Vacacion> listaSaldoVacaciones) {
        this.listaSaldoVacaciones = listaSaldoVacaciones;
    }

}
