/*
 *  ConsultaPuestoHelper.java
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
 *  09/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.paginador.ConsultaPuestoPaginador;
import ec.com.atikasoft.proteus.modelo.Detalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.vo.ConsultaTramiteVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.StreamedContent;

/**
 * Helper de Consulta Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@SessionScoped
@ManagedBean(name = "consultaPuestoHelper")
public class ConsultaPuestoHelper implements Serializable {

    /**
     * VO de parametros.
     */
    private ConsultaTramiteVO consultaTramiteVO = new ConsultaTramiteVO();

    private List<SelectItem> tiposMovimientos = new ArrayList<SelectItem>();

    private List<SelectItem> estadosTramite = new ArrayList<SelectItem>();

    private List<SelectItem> partidaPresupuestariaGeneral = new ArrayList<SelectItem>();

    private List<SelectItem> tipoIdentificacion = new ArrayList<SelectItem>();
    private Long numeroTramites = 0L;
    /**
     * variable movimiento.
     */
    private Movimiento movimiento;

    /**
     * Paginador.
     */
    private ConsultaPuestoPaginador consultaPuestoPaginador = null;

    /**
     * Lista de movimientos.
     */
//    private LazyDataModel<Movimiento> listaMovimientos;
    private List<Movimiento> listaMovimientos;

    private List<Movimiento> listaMovimientosSinConteo = new ArrayList<Movimiento>();
    /**
     * Variable del archivo
     */
    private StreamedContent archivo;
    /**
     * Indica si puede selecciona la unidad organizacional al buscar los
     * movimientos de ese unidad organizacional.
     */
    private Boolean seleccionarUnidadOrganizacional;
    /**
     * Lista de unidades organizacionales.
     */
    private List<UnidadOrganizacional> listaUnidadesOrganizacionales = new ArrayList<UnidadOrganizacional>();
    
    private List<SelectItem> grupos;
    
    private List<SelectItem> clases;
    
    /**
     * Lista de historico del tramite.
     */
    private List<Detalle> listaDetalles;
    
    /**
     * Tramite seleccionado en la lista de resultados
     */
    private Tramite tramite;
    
    /**
     * nombre archivo.
     */
    private String nombreArchivo = "";
    
    /**
     * Constructor por defecto.
     */
    public ConsultaPuestoHelper() {
        super();
        iniciar();
    }

    /**
     * metodo para iniciar mis variables y objetos.
     */
    public final void iniciar() {
        setMovimiento(new Movimiento());
        grupos = new ArrayList<SelectItem>();
        clases = new ArrayList<SelectItem>();
        tiposMovimientos = new ArrayList<SelectItem>();
        listaDetalles = new ArrayList<Detalle>();
    }

    /**
     * @return the consultaPuestoPaginador
     */
    public ConsultaPuestoPaginador getConsultaPuestoPaginador() {
        return consultaPuestoPaginador;
    }

    /**
     * @param consultaPuestoPaginador the consultaPuestoPaginador to set
     */
    public void setConsultaPuestoPaginador(ConsultaPuestoPaginador consultaPuestoPaginador) {
        this.consultaPuestoPaginador = consultaPuestoPaginador;
    }

    /**
     * @return the listaMovimientos
     */
    public List<Movimiento> getListaMovimientos() {
//        if (listaMovimientos == null) {
//            listaMovimientos = new ConsultaPuestoPaginador(getConsultaTramiteVO());
//        }
        return listaMovimientos;
    }

    /**
     * @param listaMovimientos the listaMovimientos to set
     */
    public void setListaMovimientos(final List<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    /**
     * @return the consultaTramiteVO
     */
    public ConsultaTramiteVO getConsultaTramiteVO() {
        return consultaTramiteVO;
    }

    /**
     * @param consultaTramiteVO the consultaTramiteVO to set
     */
    public void setConsultaTramiteVO(final ConsultaTramiteVO consultaTramiteVO) {
        this.consultaTramiteVO = consultaTramiteVO;
    }

    /**
     * @return the tiposMovimientos
     */
    public List<SelectItem> getTiposMovimientos() {
        return tiposMovimientos;
    }

    /**
     * @param tiposMovimientos the tiposMovimientos to set
     */
    public void setTiposMovimientos(final List<SelectItem> tiposMovimientos) {
        this.tiposMovimientos = tiposMovimientos;
    }

    /**
     * @return the estadosTramite
     */
    public List<SelectItem> getEstadosTramite() {
        return estadosTramite;
    }

    /**
     * @param estadosTramite the estadosTramite to set
     */
    public void setEstadosTramite(final List<SelectItem> estadosTramite) {
        this.estadosTramite = estadosTramite;
    }

    /**
     * @return the partidaPresupuestariaGeneral
     */
    public List<SelectItem> getPartidaPresupuestariaGeneral() {
        return partidaPresupuestariaGeneral;
    }

    /**
     * @param partidaPresupuestariaGeneral the partidaPresupuestariaGeneral to
     * set
     */
    public void setPartidaPresupuestariaGeneral(final List<SelectItem> partidaPresupuestariaGeneral) {
        this.partidaPresupuestariaGeneral = partidaPresupuestariaGeneral;
    }

    /**
     * @return the tipoIdentificacion
     */
    public List<SelectItem> getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final List<SelectItem> tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the archivo
     */
    public StreamedContent getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final StreamedContent archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the listaMovimientosSinConteo
     */
    public List<Movimiento> getListaMovimientosSinConteo() {
        return listaMovimientosSinConteo;
    }

    /**
     * @param listaMovimientosSinConteo the listaMovimientosSinConteo to set
     */
    public void setListaMovimientosSinConteo(List<Movimiento> listaMovimientosSinConteo) {
        this.listaMovimientosSinConteo = listaMovimientosSinConteo;
    }

    /**
     * @return the numeroTramites
     */
    public Long getNumeroTramites() {
        return numeroTramites;
    }

    /**
     * @param numeroTramites the numeroTramites to set
     */
    public void setNumeroTramites(Long numeroTramites) {
        this.numeroTramites = numeroTramites;
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
     * @return the seleccionarUnidadOrganizacional
     */
    public Boolean getSeleccionarUnidadOrganizacional() {
        return seleccionarUnidadOrganizacional;
    }

    /**
     * @param seleccionarUnidadOrganizacional the
     * seleccionarUnidadOrganizacional to set
     */
    public void setSeleccionarUnidadOrganizacional(final Boolean seleccionarUnidadOrganizacional) {
        this.seleccionarUnidadOrganizacional = seleccionarUnidadOrganizacional;
    }

    public List<SelectItem> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<SelectItem> grupos) {
        this.grupos = grupos;
    }

    public List<SelectItem> getClases() {
        return clases;
    }

    public void setClases(List<SelectItem> clases) {
        this.clases = clases;
    }

    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    public void setListaDetalles(List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
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
