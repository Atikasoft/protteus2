
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import ec.com.atikasoft.proteus.modelo.Validacion;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Requisito;
import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.vo.ErroresVO;
import ec.com.atikasoft.proteus.vo.ValidacionTipoMovimientoRequisitoVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "registroSolicitudHelper")
@SessionScoped
public class RegistroSolicitudHelper extends CatalogoHelper {
    /**
     * variable para horarios.
     */
    private Boolean horario;
    /**
     * variable para validar las fechas.
     */
    private Boolean validacionFechas;
    /**
     * variable para lectura.
     */
    private Boolean lectura;
    /**
     * variable para obligatorio.
     */
    private Boolean obligatorio;   
    /**
     * Lista de tipos de solicitud.
     */
    private List<SelectItem> tipoSolicitud;
    /**
     * Lista de nivel de estudio.
     */
    private List<SelectItem> nivelEstudio;
    /**
     * Lista de tipos de nacimiento.
     */
    private List<SelectItem> listaTipoNacimiento;
    /**
     * Opciondes de tipo movimiento.
     */
    private List<SelectItem> listaTipoMovimiento;
    /**
     * tipo de solicitud seleccionado.
     */
    private String tipoSolicitudSeleccionado;
     /**
     * Wraper de Validacion y Tipo de movimiento requisito.
     */
    private ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoVO;

    /**
     * Wraper de Validacion y Tipo de movimiento requisito.
     */
    private ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoVOArchivo;

    /**
     * Lista de Wraper de Validacion y Tipo de movimiento requisito.
     */
    private List<ValidacionTipoMovimientoRequisitoVO> listaValidacionTipoMovimientoRequisitoVO;
    /**
     * Entidad Tipo Movimiento.
     */
    private TipoMovimiento tipoMovimiento;
    /**
     * Entidad de licencia.
     */
    private Licencia licencia;
    /**
     * Id del movimiento seleccionado.
     */
    private Long tipoMovimientoId;
    /**
     * Nombre del movimiento seleccionado.
     */
    private String tipoMovimientoNombre;
    /**
     * objeto requisito.
     */
    private Requisito requisito;
    /**
     * variable detalle.
     */
    private String detalle;
    /**
     * variable fecha desde.
     */
    private Date fecha_desde;
    /**
     * variable fecha hasta.
     */
    private Date fecha_hasta;
    /**
     * variable fecha hecho.
     */
    private Date fecha_hecho;
    /**
     * variable tipo nacimiento.
     */
    private String tipoNacimiento;
    /**
     * variable para areaDevengar.
     */
    private Boolean areaDevengar;
    /**
     * variable para comboDevengar.
     */
    private Boolean Devengar;
    /**
     * Lista areaDevengar Si/ No.
     */
    private List<SelectItem> listaDevengar;
    /**
     * Lista periodo areaDevengar.
     */
    private List<SelectItem> listaPeridoDevengar;
    /**
     * Bandera para abrir el pop up de sustento legal.
     */
    private Boolean abrirPopUpSustentoLegal;
    /**
     * Bandera para abrir el pop up de cargar archivo.
     */
    private Boolean abrirPopUpCargarArchivos;
    /**
     * variable sustento legal.
     */
    private String sustentoLegal;
    /**
     * entidad tramite.
     */
    private Tramite tramite;
    /**
     * entidad tramite.
     */
    private Tramite tramiteCreado;
    /**
     * entidad movimiento.
     */
    private Movimiento movimiento;
    /**
     * entidad lista de licencias horarios.
     */
    private List<LicenciaHorario> listaLicenciaHorario;
    /**
     * lista de errores VO.
     */
    private List<ErroresVO> listaErroresVO;
    /**
     * lista de validaciones.
     */
    private List<Validacion> listaValidaciones;
    /**
     * VO de errores.
     */
    private ErroresVO errorVO;
    /**
     * variable para mostrar tabla de errores.
     */
    private Boolean mostrarTablaErrores;
    /**
     * variable para el número de horas de permiso matriculas hijos.
     */
    private Long numerohorasMatriculasHijo;    
    /**
     * Archivo para cada requisito.
     */
    private UploadedFile archivo;
    /**
     * fecha actual.
     */
    private Date fecha_actual = new Date();
    /**
     * Constructor de la clase.
     *
     */
    public RegistroSolicitudHelper() {
        super();
        iniciar();
    }
    /**
     * metodo para iniciar mis variables y objetos.
     */
    public final void iniciar() {
        setTipoSolicitud(new ArrayList<SelectItem>());
        setNivelEstudio(new ArrayList<SelectItem>());
        listaTipoMovimiento = new ArrayList<SelectItem>();
        setListaPeridoDevengar(new ArrayList<SelectItem>());
        setListaDevengar(new ArrayList<SelectItem>());
        setListaTipoNacimiento(new ArrayList<SelectItem>());
        setValidacionTipoMovimientoRequisitoVO(new ValidacionTipoMovimientoRequisitoVO());
        getValidacionTipoMovimientoRequisitoVO().setTipoMovimientoRequisito(new TipoMovimientoRequisito());
        getValidacionTipoMovimientoRequisitoVO().getTipoMovimientoRequisito().setRequisito(new Requisito());
        getValidacionTipoMovimientoRequisitoVO().setValidacion(new Validacion());
        getValidacionTipoMovimientoRequisitoVO().getValidacion().setArchivo(new Archivo());
        getValidacionTipoMovimientoRequisitoVO().setArchivo(new Archivo());
        //**********vo para archivo
        setValidacionTipoMovimientoRequisitoVOArchivo(new ValidacionTipoMovimientoRequisitoVO());        
        getValidacionTipoMovimientoRequisitoVOArchivo().setTipoMovimientoRequisito(new TipoMovimientoRequisito());
        getValidacionTipoMovimientoRequisitoVOArchivo().getTipoMovimientoRequisito().setRequisito(new Requisito());
        getValidacionTipoMovimientoRequisitoVOArchivo().setValidacion(new Validacion());
        getValidacionTipoMovimientoRequisitoVOArchivo().getValidacion().setArchivo(new Archivo());
        getValidacionTipoMovimientoRequisitoVOArchivo().setArchivo(new Archivo());        
        //*************************
        setListaValidacionTipoMovimientoRequisitoVO(new ArrayList<ValidacionTipoMovimientoRequisitoVO>());
        setEsNuevo(Boolean.TRUE);
        setValidacionFechas(Boolean.FALSE);
        setLicencia(new Licencia());
        getLicencia().setMovimiento(new Movimiento());
        requisito = new Requisito();
        tipoMovimiento = new TipoMovimiento();
        setHorario(Boolean.FALSE);
        setAreaDevengar(Boolean.FALSE);
        setDevengar(Boolean.FALSE);
        setDetalle("");
        setObligatorio(Boolean.FALSE);
        setLectura(Boolean.TRUE);
        abrirPopUpSustentoLegal = Boolean.FALSE;
        setAbrirPopUpCargarArchivos(Boolean.FALSE);
        setTramite(new Tramite());
        setTramiteCreado(new Tramite());
        setMovimiento(new Movimiento());
        setListaLicenciaHorario(new ArrayList<LicenciaHorario>());
        setListaErroresVO(new ArrayList<ErroresVO>());
        setListaValidaciones(new ArrayList<Validacion>());
        setErrorVO(new ErroresVO());
        setMostrarTablaErrores(Boolean.FALSE);
        setNumerohorasMatriculasHijo(0L);
    }

    /**
     * @return the tipoSolicitud
     */
    public List<SelectItem> getTipoSolicitud() {
        return tipoSolicitud;
    }
    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(final List<SelectItem> tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
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
     * @return the tipoSolicitudSeleccionado
     */
    public String getTipoSolicitudSeleccionado() {
        return tipoSolicitudSeleccionado;
    }

    /**
     * @param tipoSolicitudSeleccionado the tipoSolicitudSeleccionado to set
     */
    public void setTipoSolicitudSeleccionado(final String tipoSolicitudSeleccionado) {
        this.tipoSolicitudSeleccionado = tipoSolicitudSeleccionado;
    }

    /**
     * @return the validacionTipoMovimientoRequisitoVO
     */
    public ValidacionTipoMovimientoRequisitoVO getValidacionTipoMovimientoRequisitoVO() {
        return validacionTipoMovimientoRequisitoVO;
    }

    /**
     * @param validacionTipoMovimientoRequisitoVO the validacionTipoMovimientoRequisitoVO to set
     */
    public void setValidacionTipoMovimientoRequisitoVO(final ValidacionTipoMovimientoRequisitoVO
            validacionTipoMovimientoRequisitoVO) {
        this.validacionTipoMovimientoRequisitoVO = validacionTipoMovimientoRequisitoVO;
    }

    /**
     * @return the validacionTipoMovimientoRequisitoEditVO
     */
    public ValidacionTipoMovimientoRequisitoVO getValidacionTipoMovimientoRequisitoVOArchivo() {
        return validacionTipoMovimientoRequisitoVOArchivo;
    }

    /**
     * @param validacionTipoMovimientoRequisitoEditVO the validacionTipoMovimientoRequisitoEditVO to set
     */
    public void setValidacionTipoMovimientoRequisitoVOArchivo(final ValidacionTipoMovimientoRequisitoVO
            validacionTipoMovimientoRequisitoVOArchivo) {
        this.validacionTipoMovimientoRequisitoVOArchivo = validacionTipoMovimientoRequisitoVOArchivo;
    }

    /**
     * @return the listaValidacionTipoMovimientoRequisitoVO
     */
    public List<ValidacionTipoMovimientoRequisitoVO> getListaValidacionTipoMovimientoRequisitoVO() {
        return listaValidacionTipoMovimientoRequisitoVO;
    }

    /**
     * @param listaValidacionTipoMovimientoRequisitoVO the listaValidacionTipoMovimientoRequisitoVO to set
     */
    public void setListaValidacionTipoMovimientoRequisitoVO(final List<ValidacionTipoMovimientoRequisitoVO>
            listaValidacionTipoMovimientoRequisitoVO) {
        this.listaValidacionTipoMovimientoRequisitoVO = listaValidacionTipoMovimientoRequisitoVO;
    }
    /**
     * @return the requisito
     */
    public Requisito getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(final Requisito requisito) {
        this.requisito = requisito;
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
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the horario
     */
    public Boolean getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(final Boolean horario) {
        this.horario = horario;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(final String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the fecha_desde
     */
    public Date getFecha_desde() {
        return fecha_desde;
    }

    /**
     * @param fecha_desde the fecha_desde to set
     */
    public void setFecha_desde(final Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    /**
     * @return the fecha_hasta
     */
    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    /**
     * @param fecha_hasta the fecha_hasta to set
     */
    public void setFecha_hasta(final Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    /**
     * @return the fecha_hecho
     */
    public Date getFecha_hecho() {
        return fecha_hecho;
    }

    /**
     * @param fecha_hecho the fecha_hecho to set
     */
    public void setFecha_hecho(final Date fecha_hecho) {
        this.fecha_hecho = fecha_hecho;
    }

    /**
     * @return the licencia
     */
    public Licencia getLicencia() {
        return licencia;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(final Licencia licencia) {
        this.licencia = licencia;
    }

    /**
     * @return the tipoNacimiento
     */
    public String getTipoNacimiento() {
        return tipoNacimiento;
    }

    /**
     * @param tipoNacimiento the tipoNacimiento to set
     */
    public void setTipoNacimiento(final String tipoNacimiento) {
        this.tipoNacimiento = tipoNacimiento;
    }

    /**
     * @return the listaTipoNacimiento
     */
    public List<SelectItem> getListaTipoNacimiento() {
        return listaTipoNacimiento;
    }

    /**
     * @param listaTipoNacimiento the listaTipoNacimiento to set
     */
    public void setListaTipoNacimiento(final List<SelectItem> listaTipoNacimiento) {
        this.listaTipoNacimiento = listaTipoNacimiento;
    }

    /**
     * @return the areaDevengar
     */
    public Boolean getAreaDevengar() {
        return areaDevengar;
    }

    /**
     * @param areaDevengar the areaDevengar to set
     */
    public void setAreaDevengar(final Boolean areaDevengar) {
        this.areaDevengar = areaDevengar;
    }

    /**
     * @return the listaDevengar
     */
    public List<SelectItem> getListaDevengar() {
        return listaDevengar;
    }

    /**
     * @param listaDevengar the listaDevengar to set
     */
    public void setListaDevengar(final List<SelectItem> listaDevengar) {
        this.listaDevengar = listaDevengar;
    }

    /**
     * @return the listaPeridoDevengar
     */
    public List<SelectItem> getListaPeridoDevengar() {
        return listaPeridoDevengar;
    }

    /**
     * @param listaPeridoDevengar the listaPeridoDevengar to set
     */
    public void setListaPeridoDevengar(final List<SelectItem> listaPeridoDevengar) {
        this.listaPeridoDevengar = listaPeridoDevengar;
    }

    /**
     * @return the Devengar
     */
    public Boolean getDevengar() {
        return Devengar;
    }

    /**
     * @param Devengar the Devengar to set
     */
    public void setDevengar(final Boolean Devengar) {
        this.Devengar = Devengar;
    }

    /**
     * @return the lectura
     */
    public Boolean getLectura() {
        return lectura;
    }

    /**
     * @param lectura the lectura to set
     */
    public void setLectura(final Boolean lectura) {
        this.lectura = lectura;
    }

    /**
     * @return the obligatorio
     */
    public Boolean getObligatorio() {
        return obligatorio;
    }

    /**
     * @param obligatorio the obligatorio to set
     */
    public void setObligatorio(final Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    /**
     * @return the abrirPopUpSustentoLegal
     */
    public Boolean getAbrirPopUpSustentoLegal() {
        return abrirPopUpSustentoLegal;
    }

    /**
     * @param abrirPopUpSustentoLegal the abrirPopUpSustentoLegal to set
     */
    public void setAbrirPopUpSustentoLegal(final Boolean abrirPopUpSustentoLegal) {
        this.abrirPopUpSustentoLegal = abrirPopUpSustentoLegal;
    }

    /**
     * @return the abrirPopUpCargarArchivos
     */
    public Boolean getAbrirPopUpCargarArchivos() {
        return abrirPopUpCargarArchivos;
    }

    /**
     * @param abrirPopUpCargarArchivos the abrirPopUpCargarArchivos to set
     */
    public void setAbrirPopUpCargarArchivos(final Boolean abrirPopUpCargarArchivos) {
        this.abrirPopUpCargarArchivos = abrirPopUpCargarArchivos;
    }

    /**
     * @return the sustentoLegal
     */
    public String getSustentoLegal() {
        return sustentoLegal;
    }

    /**
     * @param sustentoLegal the sustentoLegal to set
     */
    public void setSustentoLegal(final String sustentoLegal) {
        this.sustentoLegal = sustentoLegal;
    }

    /**
     * @return the validacionFechas
     */
    public Boolean getValidacionFechas() {
        return validacionFechas;
    }

    /**
     * @param validacionFechas the validacionFechas to set
     */
    public void setValidacionFechas(final Boolean validacionFechas) {
        this.validacionFechas = validacionFechas;
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
     * @return the tipoMovimientoNombre
     */
    public String getTipoMovimientoNombre() {
        return tipoMovimientoNombre;
    }

    /**
     * @param tipoMovimientoNombre the tipoMovimientoNombre to set
     */
    public void setTipoMovimientoNombre(final String tipoMovimientoNombre) {
        this.tipoMovimientoNombre = tipoMovimientoNombre;
    }

    /**
     * @return the listaLicenciaHorario
     */
    public List<LicenciaHorario> getListaLicenciaHorario() {
        return listaLicenciaHorario;
    }

    /**
     * @param listaLicenciaHorario the listaLicenciaHorario to set
     */
    public void setListaLicenciaHorario(final List<LicenciaHorario> listaLicenciaHorario) {
        this.listaLicenciaHorario = listaLicenciaHorario;
    }

    /**
     * @return the errorVO
     */
    public ErroresVO getErrorVO() {
        return errorVO;
    }

    /**
     * @param errorVO the errorVO to set
     */
    public void setErrorVO(final ErroresVO errorVO) {
        this.errorVO = errorVO;
    }

    /**
     * @return the mostrarTablaErrores
     */
    public Boolean getMostrarTablaErrores() {
        return mostrarTablaErrores;
    }

    /**
     * @param mostrarTablaErrores the mostrarTablaErrores to set
     */
    public void setMostrarTablaErrores(final Boolean mostrarTablaErrores) {
        this.mostrarTablaErrores = mostrarTablaErrores;
    }

    /**
     * @return the listaErroresVO
     */
    public List<ErroresVO> getListaErroresVO() {
        return listaErroresVO;
    }

    /**
     * @param listaErroresVO the listaErroresVO to set
     */
    public void setListaErroresVO(final List<ErroresVO> listaErroresVO) {
        this.listaErroresVO = listaErroresVO;
    }

    /**
     * @return the tramiteCreado
     */
    public Tramite getTramiteCreado() {
        return tramiteCreado;
    }

    /**
     * @param tramiteCreado the tramiteCreado to set
     */
    public void setTramiteCreado(final Tramite tramiteCreado) {
        this.tramiteCreado = tramiteCreado;
    }

    /**
     * @return the listaValidaciones
     */
    public List<Validacion> getListaValidaciones() {
        return listaValidaciones;
    }

    /**
     * @param listaValidacion the listaValidacion to set
     */
    public void setListaValidaciones(final List<Validacion> listaValidacion) {
        this.listaValidaciones = listaValidacion;
    }

    /**
     * @return the nivelEstudio
     */
    public List<SelectItem> getNivelEstudio() {
        return nivelEstudio;
    }

    /**
     * @param nivelEstudio the nivelEstudio to set
     */
    public void setNivelEstudio(final List<SelectItem> nivelEstudio) {
        this.nivelEstudio = nivelEstudio;
    }

    /**
     * @return the numerohorasMatriculasHijo
     */
    public Long getNumerohorasMatriculasHijo() {
        return numerohorasMatriculasHijo;
    }

    /**
     * @param numerohorasMatriculasHijo the numerohorasMatriculasHijo to set
     */
    public void setNumerohorasMatriculasHijo(final Long numerohorasMatriculasHijo) {
        this.numerohorasMatriculasHijo = numerohorasMatriculasHijo;
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
     * @return the fecha_actual
     */
    public Date getFecha_actual() {
        return fecha_actual;
    }

    /**
     * @param fecha_actual the fecha_actual to set
     */
    public void setFecha_actual(final Date fecha_actual) {
        this.fecha_actual = fecha_actual;
    }
}
