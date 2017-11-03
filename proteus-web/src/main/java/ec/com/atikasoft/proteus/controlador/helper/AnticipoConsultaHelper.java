/*
 *  AnticipoConsultaHelper.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.vistas.VistaAnticipo;
import ec.com.atikasoft.proteus.vo.ConsultaAnticipoVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "anticipoConsultaHelper")
@SessionScoped
public class AnticipoConsultaHelper implements Serializable {

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    /**
     * Variable para asignar valores de busqueda.
     */
    private ConsultaAnticipoVO consultaAnticipoVO = new ConsultaAnticipoVO();
    /**
     * Lista de anticipos
     */
    private List<VistaAnticipo> listaAnticipos = new ArrayList<VistaAnticipo>();
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
    private List<SelectItem> listaOpcionModalidadLaboral = new ArrayList<SelectItem>();
  
 /**
     * Lista de plan de pago de un anticipo especifico.
     */
    private List<AnticipoPlanPago> listaPlanPago = new ArrayList<AnticipoPlanPago>();
     /**
     * Lista de plan de pago de un anticipo especifico.
     */
    private List<AnticipoPago> listaPagosAnticipo = new ArrayList<AnticipoPago>();
    
    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;

    /**
     * Indica si el panel de parametros esta activo.
     */
    private String activo = "0";
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionEstadoAnticipo = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaOpcionEstadoNomina = new ArrayList<SelectItem>();
    
      /**
     * Lista de opciones Tipo de Anticipo.
     */
    private List<SelectItem> listaOpcionTipoAnticipo = new ArrayList<SelectItem>();
    
       /**
     * Lista de opciones garantes.
     */
    private List<DistributivoDetalle> listaOpcionGarante = new ArrayList<DistributivoDetalle>();
    
          /**
     * Lista de opciones Denominación del Puesto .
     */
    private List<SelectItem> listaOpcionDenominacionPuesto = new ArrayList<SelectItem>();
    /**
     * Variables para búsqueda por Escala Ocupacional.
     */
       private TreeNode rootRegimen = new DefaultTreeNode();
    private TreeNode escalaSeleccionada = new DefaultTreeNode();

    private Long idAnticipo;
    private VistaAnticipo vistaAnticipo = new VistaAnticipo();
    private BigDecimal totalPlanPago = BigDecimal.ZERO;
    private String nombresServidor="";
    
    /**
     * nombre archivo.
     */
    private String nombreArchivo = "";
    /**
     * Constructor por defecto.
     */
    public AnticipoConsultaHelper() {
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
     * @return the consultaAnticipoVO
     */
    public ConsultaAnticipoVO getConsultaAnticipoVO() {
        return consultaAnticipoVO;
    }

    /**
     * @param consultaAnticipoVO the consultaAnticipoVO to set
     */
    public void setConsultaAnticipoVO(ConsultaAnticipoVO consultaAnticipoVO) {
        this.consultaAnticipoVO = consultaAnticipoVO;
    }

    /**
     * @return the listaOpcionEstadoAnticipo
     */
    public List<SelectItem> getListaOpcionEstadoAnticipo() {
        return listaOpcionEstadoAnticipo;
    }

    /**
     * @param listaOpcionEstadoAnticipo the listaOpcionEstadoAnticipo to set
     */
    public void setListaOpcionEstadoAnticipo(List<SelectItem> listaOpcionEstadoAnticipo) {
        this.listaOpcionEstadoAnticipo = listaOpcionEstadoAnticipo;
    }

    /**
     * @return the listaOpcionEstadoNomina
     */
    public List<SelectItem> getListaOpcionEstadoNomina() {
        return listaOpcionEstadoNomina;
    }

    /**
     * @param listaOpcionEstadoNomina the listaOpcionEstadoNomina to set
     */
    public void setListaOpcionEstadoNomina(List<SelectItem> listaOpcionEstadoNomina) {
        this.listaOpcionEstadoNomina = listaOpcionEstadoNomina;
    }

    /**
     * @return the listaOpcionTipoAnticipo
     */
    public List<SelectItem> getListaOpcionTipoAnticipo() {
        return listaOpcionTipoAnticipo;
    }

    /**
     * @param listaOpcionTipoAnticipo the listaOpcionTipoAnticipo to set
     */
    public void setListaOpcionTipoAnticipo(List<SelectItem> listaOpcionTipoAnticipo) {
        this.listaOpcionTipoAnticipo = listaOpcionTipoAnticipo;
    }

    /**
     * @return the listaOpcionGarante
     */
    public List<DistributivoDetalle> getListaOpcionGarante() {
        return listaOpcionGarante;
    }

    /**
     * @param listaOpcionGarante the listaOpcionGarante to set
     */
    public void setListaOpcionGarante(List<DistributivoDetalle> listaOpcionGarante) {
        this.listaOpcionGarante = listaOpcionGarante;
    }

    /**
     * @return the rootRegimen
     */
    public TreeNode getRootRegimen() {
        return rootRegimen;
    }

    /**
     * @param rootRegimen the rootRegimen to set
     */
    public void setRootRegimen(TreeNode rootRegimen) {
        this.rootRegimen = rootRegimen;
    }

    /**
     * @return the escalaSeleccionada
     */
    public TreeNode getEscalaSeleccionada() {
        return escalaSeleccionada;
    }

    /**
     * @param escalaSeleccionada the escalaSeleccionada to set
     */
    public void setEscalaSeleccionada(TreeNode escalaSeleccionada) {
        this.escalaSeleccionada = escalaSeleccionada;
    }

    /**
     * @return the listaOpcionDenominacionPuesto
     */
    public List<SelectItem> getListaOpcionDenominacionPuesto() {
        return listaOpcionDenominacionPuesto;
    }

    /**
     * @param listaOpcionDenominacionPuesto the listaOpcionDenominacionPuesto to set
     */
    public void setListaOpcionDenominacionPuesto(List<SelectItem> listaOpcionDenominacionPuesto) {
        this.listaOpcionDenominacionPuesto = listaOpcionDenominacionPuesto;
    }

    /**
     * @return the listaOpcionModalidadLaboral
     */
    public List<SelectItem> getListaOpcionModalidadLaboral() {
        return listaOpcionModalidadLaboral;
    }

    /**
     * @param listaOpcionModalidadLaboral the listaOpcionModalidadLaboral to set
     */
    public void setListaOpcionModalidadLaboral(List<SelectItem> listaOpcionModalidadLaboral) {
        this.listaOpcionModalidadLaboral = listaOpcionModalidadLaboral;
    }

    /**
     * @return the listaPlanPago
     */
    public List<AnticipoPlanPago> getListaPlanPago() {
        return listaPlanPago;
    }

    /**
     * @param listaPlanPago the listaPlanPago to set
     */
    public void setListaPlanPago(List<AnticipoPlanPago> listaPlanPago) {
        this.listaPlanPago = listaPlanPago;
    }

    /**
     * @return the listaPagosAnticipo
     */
    public List<AnticipoPago> getListaPagosAnticipo() {
        return listaPagosAnticipo;
    }

    /**
     * @param listaPagosAnticipo the listaPagosAnticipo to set
     */
    public void setListaPagosAnticipo(List<AnticipoPago> listaPagosAnticipo) {
        this.listaPagosAnticipo = listaPagosAnticipo;
    }

    /**
     * @return the idAnticipo
     */
    public Long getIdAnticipo() {
        return idAnticipo;
    }

    /**
     * @param idAnticipo the idAnticipo to set
     */
    public void setIdAnticipo(Long idAnticipo) {
        this.idAnticipo = idAnticipo;
    }

    /**
     * @return the totalPlanPago
     */
    public BigDecimal getTotalPlanPago() {
        return totalPlanPago;
    }

    /**
     * @param totalPlanPago the totalPlanPago to set
     */
    public void setTotalPlanPago(BigDecimal totalPlanPago) {
        this.totalPlanPago = totalPlanPago;
    }

    /**
     * @return the listaAnticipos
     */
    public List<VistaAnticipo> getListaAnticipos() {
        return listaAnticipos;
    }

    /**
     * @param listaAnticipos the listaAnticipos to set
     */
    public void setListaAnticipos(List<VistaAnticipo> listaAnticipos) {
        this.listaAnticipos = listaAnticipos;
    }

    /**
     * @return the nombresServidor
     */
    public String getNombresServidor() {
        return nombresServidor;
    }

    /**
     * @param nombresServidor the nombresServidor to set
     */
    public void setNombresServidor(String nombresServidor) {
        this.nombresServidor = nombresServidor;
    }
    /**
     * @return the vistaAnticipo
     */
    public VistaAnticipo getVistaAnticipo() {
        return vistaAnticipo;
    }

    /**
     * @param vistaAnticipo the vistaAnticipo to set
     */
    public void setVistaAnticipo(VistaAnticipo vistaAnticipo) {
        this.vistaAnticipo = vistaAnticipo;
    }

    /**
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    
    
}