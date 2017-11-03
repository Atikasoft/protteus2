/*
 *  BusquedaImpuestoRentaHelper.java
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

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.vistas.NominaDetalleIR;
import ec.com.atikasoft.proteus.vo.BusquedaImpuestoRentaVO;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * BusquedaImpuestoRentaHelper.
 * @author atikasoft
 *
 */
@ManagedBean(name = "busquedaImpuestoRentaHelper")
@SessionScoped
public class BusquedaImpuestoRentaHelper implements Serializable {
     /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    private String unidadOrganizacionalCodigo="";
    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaServidorVO busquedaServidorVO = new BusquedaServidorVO();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoDocumento = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodos = new ArrayList<SelectItem>();
    /**
     * id de periodo.
     */
    private Long periodoId;
    /**
     * Mensaje de validacion.
     */
    private String mensajeValidaciones;
    /**
     * lista de peustos seleccionados.
     */
    private Map<Integer, DistributivoDetalle> seleccionado = new HashMap<Integer, DistributivoDetalle>();
    /**
     * Indica si el panel de parametros esta activo.
     */
    private String activo = "0";
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaEjercicioFiscal = new ArrayList<SelectItem>();
    private List<Servidor> listaServidor = new ArrayList<Servidor>();
    /**
     * Variable que almacena los registro del impuesto a la renta por periodos.
     */
    private List<NominaDetalle> listaNominasIR = new ArrayList<NominaDetalle>();
    private BusquedaImpuestoRentaVO vo = new BusquedaImpuestoRentaVO();
    /**
     * constructor.
     */
    public BusquedaImpuestoRentaHelper() {
        super();
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
     * @return the busquedaServidorVO
     */
    public BusquedaServidorVO getBusquedaServidorVO() {
        return busquedaServidorVO;
    }

    /**
     * @param busquedaServidorVO the busquedaServidorVO to set
     */
    public void setBusquedaServidorVO(BusquedaServidorVO busquedaServidorVO) {
        this.busquedaServidorVO = busquedaServidorVO;
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
    public void setListaTipoDocumento(List<SelectItem> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
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
    public void setMensajeValidaciones(String mensajeValidaciones) {
        this.mensajeValidaciones = mensajeValidaciones;
    }

    /**
     * @return the seleccionado
     */
    public Map<Integer, DistributivoDetalle> getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Map<Integer, DistributivoDetalle> seleccionado) {
        this.seleccionado = seleccionado;
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
     * @return the listaEjercicioFiscal
     */
    public List<SelectItem> getListaEjercicioFiscal() {
        return listaEjercicioFiscal;
    }

    /**
     * @param listaEjercicioFiscal the listaEjercicioFiscal to set
     */
    public void setListaEjercicioFiscal(List<SelectItem> listaEjercicioFiscal) {
        this.listaEjercicioFiscal = listaEjercicioFiscal;
    }

    /**
     * @return the listaServidor
     */
    public List<Servidor> getListaServidor() {
        return listaServidor;
    }

    /**
     * @param listaServidor the listaServidor to set
     */
    public void setListaServidor(List<Servidor> listaServidor) {
        this.listaServidor = listaServidor;
    }

    /**
     * @return the unidadOrganizacionalCodigo
     */
    public String getUnidadOrganizacionalCodigo() {
        return unidadOrganizacionalCodigo;
    }

    /**
     * @param unidadOrganizacionalCodigo the unidadOrganizacionalCodigo to set
     */
    public void setUnidadOrganizacionalCodigo(String unidadOrganizacionalCodigo) {
        this.unidadOrganizacionalCodigo = unidadOrganizacionalCodigo;
    }

    /**
     * @return the listaPeriodos
     */
    public List<SelectItem> getListaPeriodos() {
        return listaPeriodos;
    }

    /**
     * @param listaPeriodos the listaPeriodos to set
     */
    public void setListaPeriodos(List<SelectItem> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    /**
     * @return the periodoId
     */
    public Long getPeriodoId() {
        return periodoId;
    }

    /**
     * @param periodoId the periodoId to set
     */
    public void setPeriodoId(Long periodoId) {
        this.periodoId = periodoId;
    }

    /**
     * @return the listaNominasIR
     */
    public List<NominaDetalle> getListaNominasIR() {
        return listaNominasIR;
    }

    /**
     * @param listaNominasIR the listaNominasIR to set
     */
    public void setListaNominasIR(List<NominaDetalle> listaNominasIR) {
        this.listaNominasIR = listaNominasIR;
    }

    /**
     * @return the vo
     */
    public BusquedaImpuestoRentaVO getVo() {
        return vo;
    }

    /**
     * @param vo the vo to set
     */
    public void setVo(BusquedaImpuestoRentaVO vo) {
        this.vo = vo;
    }
    
    
    
}
