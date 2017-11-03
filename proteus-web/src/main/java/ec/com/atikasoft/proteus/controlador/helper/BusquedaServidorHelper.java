/*
 *  BusquedaPuestoHelper.java
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

import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.Valor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "busquedaServidorHelper")
@SessionScoped
public class BusquedaServidorHelper implements Serializable {

    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaServidorVO busquedaServidorVO = new BusquedaServidorVO();
    /**
     * Lista de puesto
     */
    private List<DistributivoDetalle> listaServidores = new ArrayList<DistributivoDetalle>();
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
    private List<SelectItem> listaEstadoPersonal = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPais = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaProvincia = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCanton = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaParroquia = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaTipoEstadoCivil = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaCatalogoGenero = new ArrayList<SelectItem>();
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
    private List<SelectItem> genero = new ArrayList<SelectItem>();
    /**
     * Lista de opciones.
     */
    private List<SelectItem> estadoCivil = new ArrayList<SelectItem>();
    private List<Servidor> listaServidor = new ArrayList<Servidor>();

    /**
     * nombre archivo.
     */
    private String nombreArchivo = "";

    private Long contadorRegistrosPaginacion = 0L;

    private int porcentaje;
    private int totalRegistro;

    /**
     * Indica si puede selecciona la unidad organizacional en en buscador del
     * puestos.
     */
    private Boolean seleccionarUnidadOrganizacional;

    /**
     *
     */
    private List<Valor> unidadesOrganizacionalPermitidas;

    /**
     * Constructor por defecto.
     */
    public BusquedaServidorHelper() {
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
     * @return the listaPais
     */
    public List<SelectItem> getListaPais() {
        return listaPais;
    }

    /**
     * @param listaPais the listaPais to set
     */
    public void setListaPais(final List<SelectItem> listaPais) {
        this.listaPais = listaPais;
    }

    /**
     * @return the listaProvincia
     */
    public List<SelectItem> getListaProvincia() {
        return listaProvincia;
    }

    /**
     * @param listaProvincia the listaProvincia to set
     */
    public void setListaProvincia(final List<SelectItem> listaProvincia) {
        this.listaProvincia = listaProvincia;
    }

    /**
     * @return the listaCanton
     */
    public List<SelectItem> getListaCanton() {
        return listaCanton;
    }

    /**
     * @param listaCanton the listaCanton to set
     */
    public void setListaCanton(final List<SelectItem> listaCanton) {
        this.listaCanton = listaCanton;
    }

    /**
     * @return the listaParroquia
     */
    public List<SelectItem> getListaParroquia() {
        return listaParroquia;
    }

    /**
     * @param listaParroquia the listaParroquia to set
     */
    public void setListaParroquia(final List<SelectItem> listaParroquia) {
        this.listaParroquia = listaParroquia;
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
     * @return the busquedaServidorVO
     */
    public BusquedaServidorVO getBusquedaServidorVO() {
        return busquedaServidorVO;
    }

    /**
     * @param busquedaServidorVO the busquedaServidorVO to set
     */
    public void setBusquedaServidorVO(final BusquedaServidorVO busquedaServidorVO) {
        this.busquedaServidorVO = busquedaServidorVO;
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
     * @return the listaServidores
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
     * @return the listaEstadoPersonal
     */
    public List<SelectItem> getListaEstadoPersonal() {
        return listaEstadoPersonal;
    }

    /**
     * @param listaEstadoPersonal the listaEstadoPersonal to set
     */
    public void setListaEstadoPersonal(List<SelectItem> listaEstadoPersonal) {
        this.listaEstadoPersonal = listaEstadoPersonal;
    }

    /**
     * @return the genero
     */
    public List<SelectItem> getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(List<SelectItem> genero) {
        this.genero = genero;
    }

    /**
     * @return the estadoCivil
     */
    public List<SelectItem> getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(List<SelectItem> estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @return the listaTipoEstadoCivil
     */
    public List<SelectItem> getListaTipoEstadoCivil() {
        return listaTipoEstadoCivil;
    }

    /**
     * @param listaTipoEstadoCivil the listaTipoEstadoCivil to set
     */
    public void setListaTipoEstadoCivil(List<SelectItem> listaTipoEstadoCivil) {
        this.listaTipoEstadoCivil = listaTipoEstadoCivil;
    }

    /**
     * @return the listaCatalogoGenero
     */
    public List<SelectItem> getListaCatalogoGenero() {
        return listaCatalogoGenero;
    }

    /**
     * @param listaCatalogoGenero the listaCatalogoGenero to set
     */
    public void setListaCatalogoGenero(List<SelectItem> listaCatalogoGenero) {
        this.listaCatalogoGenero = listaCatalogoGenero;
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

    /**
     * @return the contadorRegistrosPaginacion
     */
    public Long getContadorRegistrosPaginacion() {
        return contadorRegistrosPaginacion;
    }

    /**
     * @param contadorRegistrosPaginacion the contadorRegistrosPaginacion to set
     */
    public void setContadorRegistrosPaginacion(Long contadorRegistrosPaginacion) {
        this.contadorRegistrosPaginacion = contadorRegistrosPaginacion;
    }

    /**
     * @return the porcentaje
     */
    public int getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * @return the totalRegistro
     */
    public int getTotalRegistro() {
        return totalRegistro;
    }

    /**
     * @param totalRegistro the totalRegistro to set
     */
    public void setTotalRegistro(int totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

    /**
     * @return the seleccionarUnidadOrganizacional
     */
    public Boolean getSeleccionarUnidadOrganizacional() {
        return seleccionarUnidadOrganizacional;
    }

    /**
     * @param seleccionarUnidadOrganizacional the seleccionarUnidadOrganizacional to set
     */
    public void setSeleccionarUnidadOrganizacional(Boolean seleccionarUnidadOrganizacional) {
        this.seleccionarUnidadOrganizacional = seleccionarUnidadOrganizacional;
    }

    /**
     * @return the unidadesOrganizacionalPermitidas
     */
    public List<Valor> getUnidadesOrganizacionalPermitidas() {
        return unidadesOrganizacionalPermitidas;
    }

    /**
     * @param unidadesOrganizacionalPermitidas the unidadesOrganizacionalPermitidas to set
     */
    public void setUnidadesOrganizacionalPermitidas(List<Valor> unidadesOrganizacionalPermitidas) {
        this.unidadesOrganizacionalPermitidas = unidadesOrganizacionalPermitidas;
    }
}
