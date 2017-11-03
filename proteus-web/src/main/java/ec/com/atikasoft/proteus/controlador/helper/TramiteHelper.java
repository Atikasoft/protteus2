/*
 *  TramiteHelper.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Detalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.TramiteBitacora;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteHelper")
@SessionScoped
public class TramiteHelper extends CatalogoHelper {

    /**
     * private TramiteBitacora tramiteBitacora.
     */
    private TramiteBitacora tramiteBitacora = new TramiteBitacora();

    /**
     * Lista de historico del tramite.
     */
    private List<Detalle> listaDetalles = new ArrayList<Detalle>();

    /**
     * Indica si el tramite se encuentra en elaboracion.
     */
    private Boolean enElaboracion = Boolean.FALSE;

    /**
     * Variable de control para la navegacion.
     */
    private String controlNavegacion = "nx";

    /**
     * Objeto tramite.
     */
    private Tramite tramite;
    /**
     * Almacena la lista de errore en la carga de archivo.
     */
    private List<String> erroresCargaMasiva;

    /**
     * Objeto tramite.
     */
    private Movimiento movimiento;

    /**
     * Opciones de tipo gestion.
     */
    private List<SelectItem> listaTipoGestion;

    /**
     * Opciones de grupo.
     */
    private List<SelectItem> listaGrupo;

    /**
     * Opciones de clase.
     */
    private List<SelectItem> listaClase;

    /**
     * Opciondes de tipo movimiento.
     */
    private List<SelectItem> listaTipoMovimiento;

    /**
     * Lista de Movimientos.
     */
    private List<Movimiento> listaMovimientos = new ArrayList<Movimiento>();

    /**
     * Bandera para el control del estado de la cabecera.
     */
    private Boolean creado = Boolean.FALSE;

    /**
     * Bandera para el caso del formulario no editable.
     */
    private Boolean editable = Boolean.TRUE;

    /**
     * Lista de tipo Accion para el tramite .
     */
    private List<SelectItem> listaTipoAccion;

    /**
     * Lista de tipo Accion para el tramite .
     */
    private List<SelectItem> listaDocumentoPrevio = new ArrayList<SelectItem>();

    /**
     * Id de Grupo.
     */
    private Long grupoId;

    /**
     * Id de Clase.
     */
    private Long claseId;

    /**
     * variable del Reporte.
     */
    private String reporte;

    /**
     * Id de Tipo de Movimiento.
     */
    private Long tipoMovimientoId;

    /**
     * Id de tipo de gestion.
     */
    private Long tipoGestion;

    /**
     * Nombre para validar la accion.
     */
    private Boolean campoAccion;

    /**
     * Nombre para validar la accion.
     */
    private Boolean esAccionPersonal;

    /**
     * Badera de documento habilitante.
     */
    private Boolean esContrato;

    /**
     * Badera de documento habilitante.
     */
    private Boolean esMemorando;

    /**
     * fecha actual.
     */
    private Date fechaActual = new Date();

    /**
     * Archivo para cada movimiento.
     */
    private UploadedFile archivo;

    /**
     * Lista de autoridad nominadora.
     */
    private List<SelectItem> listaAutoridadNominadora = new ArrayList<>();

    /**
     * Lista de representnates de rrhh.
     */
    private List<SelectItem> listaRepresentanteRRHH = new ArrayList<>();
    
    /**
     * Lista de tipos de peridos.
     */
    private List<SelectItem> listaTipoPeriodos = new ArrayList<>();
    
    private Boolean esAprobar;


    /**
     * Constructor por defecto.
     */
    public TramiteHelper() {
        super();
        iniciador();
    }

    /**
     * Este metodo inicializa todas las variables.
     */
    public final void iniciador() {
        tramite = new Tramite();
        getTramite().setTipoMovimiento(new TipoMovimiento());
        setListaClase(new ArrayList<SelectItem>());
        setListaGrupo(new ArrayList<SelectItem>());
        setListaTipoAccion(new ArrayList<SelectItem>());
        setListaTipoGestion(new ArrayList<SelectItem>());
        setListaTipoMovimiento(new ArrayList<SelectItem>());
        movimiento = new Movimiento();
        campoAccion = Boolean.FALSE;
        esAccionPersonal = Boolean.FALSE;
        erroresCargaMasiva = new ArrayList<String>();
        esAprobar = Boolean.FALSE;
    }

    /**
     * @return the tramite
     */
    public Tramite getTramite() {
        return tramite;
    }

    /**
     * @param tramite the tramite to set
     */
    public void setTramite(final Tramite tramite) {
        this.tramite = tramite;
    }

    /**
     * @return the listaTipoGestion
     */
    public List<SelectItem> getListaTipoGestion() {
        return listaTipoGestion;
    }

    /**
     * @param listaTipoGestion the listaTipoGestion to set
     */
    public void setListaTipoGestion(final List<SelectItem> listaTipoGestion) {
        this.listaTipoGestion = listaTipoGestion;
    }

    /**
     * @return the listaGrupo
     */
    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * @param listaGrupo the listaGrupo to set
     */
    public void setListaGrupo(final List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * @return the listaClase
     */
    public List<SelectItem> getListaClase() {
        return listaClase;
    }

    /**
     * @param listaClase the listaClase to set
     */
    public void setListaClase(final List<SelectItem> listaClase) {
        this.listaClase = listaClase;
    }

    /**
     * @return the listaTipoMovimiento
     */
    public List<SelectItem> getListaTipoMovimiento() {
        return listaTipoMovimiento;
    }

    /**
     * @param listaTipoMovimiento the listaTipoMovimiento to set
     */
    public void setListaTipoMovimiento(final List<SelectItem> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    /**
     * @return the creado
     */
    public Boolean getCreado() {
        return creado;
    }

    /**
     * @param creado the creado to set
     */
    public void setCreado(final Boolean creado) {
        this.creado = creado;
    }

    /**
     * @return the grupoId
     */
    public Long getGrupoId() {
        return grupoId;
    }

    /**
     * @param grupoId the grupoId to set
     */
    public void setGrupoId(final Long grupoId) {
        this.grupoId = grupoId;
    }

    /**
     * @return the claseId
     */
    public Long getClaseId() {
        return claseId;
    }

    /**
     * @param claseId the claseId to set
     */
    public void setClaseId(final Long claseId) {
        this.claseId = claseId;
    }

    /**
     * @return the tipoMovimientoId
     */
    public Long getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    /**
     * @param tipoMovimientoId the tipoMovimientoId to set
     */
    public void setTipoMovimientoId(final Long tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    /**
     * @return the tipoGestion
     */
    public Long getTipoGestion() {
        return tipoGestion;
    }

    /**
     * @param tipoGestion the tipoGestion to set
     */
    public void setTipoGestion(final Long tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    /**
     * @return the listaMovimientos
     */
    public List<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    /**
     * @param listaMovimientos the listaMovimientos to set
     */
    public void setListaMovimientos(final List<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    /**
     * @return the campoAccion
     */
    public Boolean getCampoAccion() {
        return campoAccion;
    }

    /**
     * @param campoAccion the campoAccion to set
     */
    public void setCampoAccion(final Boolean campoAccion) {
        this.campoAccion = campoAccion;
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
     * @return the listaTipoAccion
     */
    public List<SelectItem> getListaTipoAccion() {
        return listaTipoAccion;
    }

    /**
     * @param listaTipoAccion the listaTipoAccion to set
     */
    public void setListaTipoAccion(final List<SelectItem> listaTipoAccion) {
        this.listaTipoAccion = listaTipoAccion;
    }

    /**
     * @return the esAccionPersonal
     */
    public Boolean getEsAccionPersonal() {
        return esAccionPersonal;
    }

    /**
     * @param esAccionPersonal the esAccionPersonal to set
     */
    public void setEsAccionPersonal(final Boolean esAccionPersonal) {
        this.esAccionPersonal = esAccionPersonal;
    }

    /**
     * @return the reporte
     */
    public String getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(final String reporte) {
        this.reporte = reporte;
    }

    /**
     * @return the editable
     */
    public Boolean getEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(final Boolean editable) {
        this.editable = editable;
    }

    /**
     * @return the controlNavegacion
     */
    public String getControlNavegacion() {
        return controlNavegacion;
    }

    /**
     * @param controlNavegacion the controlNavegacion to set
     */
    public void setControlNavegacion(final String controlNavegacion) {
        this.controlNavegacion = controlNavegacion;
    }

    /**
     * @return the enElaboracion
     */
    public Boolean getEnElaboracion() {
        return enElaboracion;
    }

    /**
     * @param enElaboracion the enElaboracion to set
     */
    public void setEnElaboracion(final Boolean enElaboracion) {
        this.enElaboracion = enElaboracion;
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(final Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the tramiteBitacora
     */
    public TramiteBitacora getTramiteBitacora() {
        return tramiteBitacora;
    }

    /**
     * @param tramiteBitacora the tramiteBitacora to set
     */
    public void setTramiteBitacora(final TramiteBitacora tramiteBitacora) {
        this.tramiteBitacora = tramiteBitacora;
    }

    /**
     * @return the listaDetalles
     */
    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    /**
     * @param listaDetalles the listaDetalles to set
     */
    public void setListaDetalles(final List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    /**
     * @return the esContrato
     */
    public Boolean getEsContrato() {
        return esContrato;
    }

    /**
     * @param esContrato the esContrato to set
     */
    public void setEsContrato(final Boolean esContrato) {
        this.esContrato = esContrato;
    }

    /**
     * @return the esMemorando
     */
    public Boolean getEsMemorando() {
        return esMemorando;
    }

    /**
     * @param esMemorando the esMemorando to set
     */
    public void setEsMemorando(final Boolean esMemorando) {
        this.esMemorando = esMemorando;
    }

    /**
     * @return the listaDocumentoPrevio
     */
    public List<SelectItem> getListaDocumentoPrevio() {
        return listaDocumentoPrevio;
    }

    /**
     * @param listaDocumentoPrevio the listaDocumentoPrevio to set
     */
    public void setListaDocumentoPrevio(final List<SelectItem> listaDocumentoPrevio) {
        this.listaDocumentoPrevio = listaDocumentoPrevio;
    }

    /**
     * @return the archivo
     */
    public UploadedFile getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final UploadedFile archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the erroresCargaMasiva
     */
    public List<String> getErroresCargaMasiva() {
        return erroresCargaMasiva;
    }

    /**
     * @param erroresCargaMasiva the erroresCargaMasiva to set
     */
    public void setErroresCargaMasiva(List<String> erroresCargaMasiva) {
        this.erroresCargaMasiva = erroresCargaMasiva;
    }

    /**
     * @return the listaAutoridadNominadora
     */
    public List<SelectItem> getListaAutoridadNominadora() {
        return listaAutoridadNominadora;
    }

    /**
     * @param listaAutoridadNominadora the listaAutoridadNominadora to set
     */
    public void setListaAutoridadNominadora(List<SelectItem> listaAutoridadNominadora) {
        this.listaAutoridadNominadora = listaAutoridadNominadora;
    }

    /**
     * @return the listaRepresentanteRRHH
     */
    public List<SelectItem> getListaRepresentanteRRHH() {
        return listaRepresentanteRRHH;
    }

    /**
     * @param listaRepresentanteRRHH the listaRepresentanteRRHH to set
     */
    public void setListaRepresentanteRRHH(List<SelectItem> listaRepresentanteRRHH) {
        this.listaRepresentanteRRHH = listaRepresentanteRRHH;
    }

    /**
     * @return the listaTipoPeriodos
     */
    public List<SelectItem> getListaTipoPeriodos() {
        return listaTipoPeriodos;
    }

    /**
     * @param listaTipoPeriodos the listaTipoPeriodos to set
     */
    public void setListaTipoPeriodos(List<SelectItem> listaTipoPeriodos) {
        this.listaTipoPeriodos = listaTipoPeriodos;
    }

    public Boolean getEsAprobar() {
        return esAprobar;
    }

    public void setEsAprobar(Boolean esAprobar) {
        this.esAprobar = esAprobar;
    }
}
