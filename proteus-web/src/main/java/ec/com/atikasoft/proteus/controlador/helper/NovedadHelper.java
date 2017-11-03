/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Novedad;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.vo.BusquedaNovedadVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "novedadHelper")
@SessionScoped
public final class NovedadHelper extends CatalogoHelper {

    /**
     * lista de datos adicionales.
     */
    private List<SelectItem> listaDatoAdicionales;
    /**
     * lista Tipo nomina.
     */
    private List<SelectItem> listaNomina;
    /**
     * lista Periodo nomina.
     */
    private List<SelectItem> listaPeriodoNomina;
    /**
     * periodo nomina id.
     */
    private Long PeriodoNominaId;
    /**
     * nomina id.
     */
    private Long nominaId;
    /**
     * dato adicional id.
     */
    private Long datoAdicionalId;

    /**
     * periodo nomina id.
     */
    private Long periodoNominaFormularioId;
    /**
     * nomina id.
     */
    private Long nominaFormularioId;
    /**
     * dato adicional id.
     */
    private Long datoAdicionalFormularioId;

    /**
     * nombreArchivo.
     */
    private String nombreArchivo;
    /**
     * lista servidor.
     */
    private List<Servidor> listaServidores;
    /**
     * servidor selecto para pasar de una lista a la otra.
     */
    private Servidor servidorSelecto;
    /**
     * nombre servidor.
     */
    private String nombreServidor;
    /**
     * tab inicializado para validar requeridos.
     */
    private String tab1 = "tab1";
    /**
     * periodo nomina id.
     */
    private Long PeriodoNominaCopiaId;
    /**
     * listaNovedadDetalles
     */
    private List<NovedadDetalle> listaNovedadDetalles;
    /**
     * lista de detalles eliminados
     */
    private List<NovedadDetalle> listaNovedadDetalleEliminados;
    /**
     * novedad.
     */
    private Novedad novedad;
    /**
     * novedad detalle.
     */
    private NovedadDetalle novedadDetalle;
    /**
     * listaNovedadDetalles
     */
    private List<NovedadDetalle> listaNovedadDetallesServidorExiste;
    /**
     * listaNovedadDetalles
     */
    private List<NovedadDetalle> listaNovedadDetallesPorPeriodo;
    /**
     * listaNovedadDetalles
     */
    private List<NovedadDetalle> listaNovedadDetallesEditables;
    /**
     * listaNovedadDetalles
     */
    private List<NovedadDetalle> listaNovedadDetallesNoExistenServidor;
    /**
     * novedadDetalleEliminar.
     */
    private NovedadDetalle novedadDetalleEliminar;
    /**
     * listaNovedad para los nuevos casos
     */
    private List<Novedad> listaNovedades;

    /**
     * novedad para la carga de las novedades detalles por el id de la novedad.
     */
    private Novedad novedadEditar;
    /**
     * busqueda de las novedades.
     */
    private BusquedaNovedadVO busquedaNovedadVO;
    /**
     * Variable de la nomina.
     */
    private boolean aceptaCambios;
    /**
     * Lista de novedades de la nomina.
     */
    private BigDecimal totalValor;
    private BigDecimal totalValorDescontado;
    /**
     * Lista de novedades de la nomina.
     */
    private Integer totalRegistros;
    private Boolean valoresEditar;
    private Integer totalRegistrosCargados;

    /**
     * listaNovedad para los nuevos casos
     */
    private List<Novedad> listaNovedadesNemonico;

    /**
     *
     */
    private UnidadOrganizacional unidadOrganizacional;

    /**
     *
     */
    private DatoAdicional datoAdicionalSeleccionado;

    /**
     * Descripcion de la novedad.
     */
    private String descripcion;

    /**
     *
     */
    private UsuarioVO usuarioVO;

    /**
     *
     */
    private String nombreUnidadOrganizacional;

    /**
     * Indica si el desconcentrado tiene permiso para crear o modificar
     * novedades.
     */
    private Boolean tienePermiso;

    /**
     * Indica si la unidad organizaciones es de recursos humanos.
     */
    private Boolean esRRHH;

    /**
     * Indica si la unidad organizaciones es desconcentrado.
     */
    private Boolean esDesconcentrado;

    /**
     * Indica si la nomina esta abierta.
     */
    private Boolean esNominaAbierta;

    /**
     *
     */
    private Boolean conAcceso;
    
    private String tipoIdentificacion;
    
    private List<SelectItem> listaTipoIdentificacion;
    
    private String numeroIdentificacion;
    
    private String nombres;

    public NovedadHelper() {
        super();
        iniciador();
    }

    /**
     * metodo para inicializar los datos.
     */
    public void iniciador() {
        datoAdicionalSeleccionado = null;
        listaDatoAdicionales = new ArrayList<SelectItem>();
        setListaNomina(new ArrayList<SelectItem>());
        setListaPeriodoNomina(new ArrayList<SelectItem>());
        setListaServidores(new ArrayList<Servidor>());
        servidorSelecto = new Servidor();
        listaNovedadDetalles = new ArrayList<NovedadDetalle>();
        setNovedad(new Novedad());
        novedadDetalle = new NovedadDetalle();
        listaNovedadDetallesServidorExiste = new ArrayList<NovedadDetalle>();
        listaNovedadDetallesPorPeriodo = new ArrayList<NovedadDetalle>();
        listaNovedadDetallesNoExistenServidor = new ArrayList<NovedadDetalle>();
        novedadDetalleEliminar = new NovedadDetalle();
        setNovedadEditar(new Novedad());
        setListaNovedades(new ArrayList<Novedad>());
        setBusquedaNovedadVO(new BusquedaNovedadVO());
        setListaNovedadDetallesEditables(new ArrayList<NovedadDetalle>());
        setAceptaCambios(Boolean.FALSE);
        setTotalValor(BigDecimal.ZERO);
        totalRegistros = 0;
        totalValorDescontado = BigDecimal.ZERO;
        setValoresEditar(Boolean.FALSE);
        setListaNovedadesNemonico(new ArrayList<Novedad>());
        setEsNuevo(Boolean.FALSE);
        setTotalRegistrosCargados(0);
        listaNovedadDetalleEliminados = new ArrayList<NovedadDetalle>();
        listaTipoIdentificacion = new ArrayList<SelectItem>();
    }

    /**
     * @return the listaPeriodoNomina
     */
    public List<SelectItem> getListaPeriodoNomina() {
        return listaPeriodoNomina;
    }

    /**
     * @param listaPeriodoNomina the listaPeriodoNomina to set
     */
    public void setListaPeriodoNomina(final List<SelectItem> listaPeriodoNomina) {
        this.listaPeriodoNomina = listaPeriodoNomina;
    }

    /**
     * @return the listaNomina
     */
    public List<SelectItem> getListaNomina() {
        return listaNomina;
    }

    /**
     * @param listaNomina the listaNomina to set
     */
    public void setListaNomina(final List<SelectItem> listaNomina) {
        this.listaNomina = listaNomina;
    }

    /**
     * @return the PeriodoNominaId
     */
    public Long getPeriodoNominaId() {
        return PeriodoNominaId;
    }

    /**
     * @param PeriodoNominaId the PeriodoNominaId to set
     */
    public void setPeriodoNominaId(final Long PeriodoNominaId) {
        this.PeriodoNominaId = PeriodoNominaId;
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(final Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @return the datoAdicionalId
     */
    public Long getDatoAdicionalId() {
        return datoAdicionalId;
    }

    /**
     * @param datoAdicionalId the datoAdicionalId to set
     */
    public void setDatoAdicionalId(final Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
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
    public void setListaServidores(final List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
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
    public void setNombreServidor(final String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * @return the servidorSelecto
     */
    public Servidor getServidorSelecto() {
        return servidorSelecto;
    }

    /**
     * @param servidorSelecto the servidorSelecto to set
     */
    public void setServidorSelecto(final Servidor servidorSelecto) {
        this.servidorSelecto = servidorSelecto;
    }

    /**
     * @return the listaNovedadDetalles
     */
    public List<NovedadDetalle> getListaNovedadDetalles() {
        return listaNovedadDetalles;
    }

    /**
     * @param listaNovedadDetalles the listaNovedadDetalles to set
     */
    public void setListaNovedadDetalles(final List<NovedadDetalle> listaNovedadDetalles) {
        this.listaNovedadDetalles = listaNovedadDetalles;
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
    public void setNombreArchivo(final String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * @return the novedad
     */
    public Novedad getNovedad() {
        return novedad;
    }

    /**
     * @param novedad the novedad to set
     */
    public void setNovedad(final Novedad novedad) {
        this.novedad = novedad;
    }

    /**
     * @return the novedadDetalle
     */
    public NovedadDetalle getNovedadDetalle() {
        return novedadDetalle;
    }

    /**
     * @param novedadDetalle the novedadDetalle to set
     */
    public void setNovedadDetalle(final NovedadDetalle novedadDetalle) {
        this.novedadDetalle = novedadDetalle;
    }

    /**
     * @return the listaNovedadDetallesServidorExiste
     */
    public List<NovedadDetalle> getListaNovedadDetallesServidorExiste() {
        return listaNovedadDetallesServidorExiste;
    }

    /**
     * @param listaNovedadDetallesServidorExiste the
     * listaNovedadDetallesServidorExiste to set
     */
    public void setListaNovedadDetallesServidorExiste(final List<NovedadDetalle> listaNovedadDetallesServidorExiste) {
        this.listaNovedadDetallesServidorExiste = listaNovedadDetallesServidorExiste;
    }

    /**
     * @return the tab1
     */
    public String getTab1() {
        return tab1;
    }

    /**
     * @param tab1 the tab1 to set
     */
    public void setTab1(final String tab1) {
        this.tab1 = tab1;
    }

    /**
     * @return the PeriodoNominaCopiaId
     */
    public Long getPeriodoNominaCopiaId() {
        return PeriodoNominaCopiaId;
    }

    /**
     * @param PeriodoNominaCopiaId the PeriodoNominaCopiaId to set
     */
    public void setPeriodoNominaCopiaId(final Long PeriodoNominaCopiaId) {
        this.PeriodoNominaCopiaId = PeriodoNominaCopiaId;
    }

    /**
     * @return the listaNovedadDetallesPorPeriodo
     */
    public List<NovedadDetalle> getListaNovedadDetallesPorPeriodo() {
        return listaNovedadDetallesPorPeriodo;
    }

    /**
     * @param listaNovedadDetallesPorPeriodo the listaNovedadDetallesPorPeriodo
     * to set
     */
    public void setListaNovedadDetallesPorPeriodo(final List<NovedadDetalle> listaNovedadDetallesPorPeriodo) {
        this.listaNovedadDetallesPorPeriodo = listaNovedadDetallesPorPeriodo;
    }

    /**
     * @return the listaNovedadDetallesNoExistenServidor
     */
    public List<NovedadDetalle> getListaNovedadDetallesNoExistenServidor() {
        return listaNovedadDetallesNoExistenServidor;
    }

    /**
     * @param listaNovedadDetallesNoExistenServidor the
     * listaNovedadDetallesNoExistenServidor to set
     */
    public void setListaNovedadDetallesNoExistenServidor(final List<NovedadDetalle> listaNovedadDetallesNoExistenServidor) {
        this.listaNovedadDetallesNoExistenServidor = listaNovedadDetallesNoExistenServidor;
    }

    /**
     * @return the novedadDetalleEliminar
     */
    public NovedadDetalle getNovedadDetalleEliminar() {
        return novedadDetalleEliminar;
    }

    /**
     * @param novedadDetalleEliminar the novedadDetalleEliminar to set
     */
    public void setNovedadDetalleEliminar(final NovedadDetalle novedadDetalleEliminar) {
        this.novedadDetalleEliminar = novedadDetalleEliminar;
    }

    /**
     * @return the listaNovedades
     */
    public List<Novedad> getListaNovedades() {
        return listaNovedades;
    }

    /**
     * @param listaNovedades the listaNovedades to set
     */
    public void setListaNovedades(final List<Novedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    /**
     * @return the busquedaNovedadVO
     */
    public BusquedaNovedadVO getBusquedaNovedadVO() {
        return busquedaNovedadVO;
    }

    /**
     * @param busquedaNovedadVO the busquedaNovedadVO to set
     */
    public void setBusquedaNovedadVO(BusquedaNovedadVO busquedaNovedadVO) {
        this.busquedaNovedadVO = busquedaNovedadVO;
    }

    /**
     * @return the novedadEditar
     */
    public Novedad getNovedadEditar() {
        return novedadEditar;
    }

    /**
     * @param novedadEditar the novedadEditar to set
     */
    public void setNovedadEditar(Novedad novedadEditar) {
        this.novedadEditar = novedadEditar;
    }

    /**
     * @return the aceptaCambios
     */
    public boolean isAceptaCambios() {
        return aceptaCambios;
    }

    /**
     * @param aceptaCambios the aceptaCambios to set
     */
    public void setAceptaCambios(boolean aceptaCambios) {
        this.aceptaCambios = aceptaCambios;
    }

    /**
     * @return the totalValor
     */
    public BigDecimal getTotalValor() {
        return totalValor;
    }

    /**
     * @param totalValor the totalValor to set
     */
    public void setTotalValor(BigDecimal totalValor) {
        this.totalValor = totalValor;
    }

    /**
     * @return the totalValorDescontado
     */
    public BigDecimal getTotalValorDescontado() {
        return totalValorDescontado;
    }

    /**
     * @param totalValorDescontado the totalValorDescontado to set
     */
    public void setTotalValorDescontado(BigDecimal totalValorDescontado) {
        this.totalValorDescontado = totalValorDescontado;
    }

    /**
     * @return the listaNovedadDetallesEditables
     */
    public List<NovedadDetalle> getListaNovedadDetallesEditables() {
        return listaNovedadDetallesEditables;
    }

    /**
     * @param listaNovedadDetallesEditables the listaNovedadDetallesEditables to
     * set
     */
    public void setListaNovedadDetallesEditables(List<NovedadDetalle> listaNovedadDetallesEditables) {
        this.listaNovedadDetallesEditables = listaNovedadDetallesEditables;
    }

    /**
     * @return the totalRegistros
     */
    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the valoresEditar
     */
    public Boolean getValoresEditar() {
        return valoresEditar;
    }

    /**
     * @param valoresEditar the valoresEditar to set
     */
    public void setValoresEditar(Boolean valoresEditar) {
        this.valoresEditar = valoresEditar;
    }

    /**
     * @return the listaNovedadesNemonico
     */
    public List<Novedad> getListaNovedadesNemonico() {
        return listaNovedadesNemonico;
    }

    /**
     * @param listaNovedadesNemonico the listaNovedadesNemonico to set
     */
    public void setListaNovedadesNemonico(List<Novedad> listaNovedadesNemonico) {
        this.listaNovedadesNemonico = listaNovedadesNemonico;
    }

    /**
     * @return the totalRegistrosCargados
     */
    public Integer getTotalRegistrosCargados() {
        return getListaNovedadDetalles().size();
    }

    /**
     * @param totalRegistrosCargados the totalRegistrosCargados to set
     */
    public void setTotalRegistrosCargados(Integer totalRegistrosCargados) {
        this.totalRegistrosCargados = totalRegistrosCargados;
    }

    /**
     * @return the datoAdicionalSeleccionado
     */
    public DatoAdicional getDatoAdicionalSeleccionado() {
        return datoAdicionalSeleccionado;
    }

    /**
     * @param datoAdicionalSeleccionado the datoAdicionalSeleccionado to set
     */
    public void setDatoAdicionalSeleccionado(DatoAdicional datoAdicionalSeleccionado) {
        this.datoAdicionalSeleccionado = datoAdicionalSeleccionado;
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

    /**
     * @return the nombreUnidadOrganizacional
     */
    public String getNombreUnidadOrganizacional() {
        return nombreUnidadOrganizacional;
    }

    /**
     * @param nombreUnidadOrganizacional the nombreUnidadOrganizacional to set
     */
    public void setNombreUnidadOrganizacional(String nombreUnidadOrganizacional) {
        this.nombreUnidadOrganizacional = nombreUnidadOrganizacional;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the periodoNominaFormularioId
     */
    public Long getPeriodoNominaFormularioId() {
        return periodoNominaFormularioId;
    }

    /**
     * @param periodoNominaFormularioId the periodoNominaFormularioId to set
     */
    public void setPeriodoNominaFormularioId(Long periodoNominaFormularioId) {
        this.periodoNominaFormularioId = periodoNominaFormularioId;
    }

    /**
     * @return the nominaFormularioId
     */
    public Long getNominaFormularioId() {
        return nominaFormularioId;
    }

    /**
     * @param nominaFormularioId the nominaFormularioId to set
     */
    public void setNominaFormularioId(Long nominaFormularioId) {
        this.nominaFormularioId = nominaFormularioId;
    }

    /**
     * @return the datoAdicionalFormularioId
     */
    public Long getDatoAdicionalFormularioId() {
        return datoAdicionalFormularioId;
    }

    /**
     * @param datoAdicionalFormularioId the datoAdicionalFormularioId to set
     */
    public void setDatoAdicionalFormularioId(Long datoAdicionalFormularioId) {
        this.datoAdicionalFormularioId = datoAdicionalFormularioId;
    }

    /**
     * @return the usuarioVO
     */
    public UsuarioVO getUsuarioVO() {
        return usuarioVO;
    }

    /**
     * @param usuarioVO the usuarioVO to set
     */
    public void setUsuarioVO(UsuarioVO usuarioVO) {
        this.usuarioVO = usuarioVO;
    }

    /**
     * @return the listaDatoAdicionales
     */
    public List<SelectItem> getListaDatoAdicionales() {
        return listaDatoAdicionales;
    }

    /**
     * @param listaDatoAdicionales the listaDatoAdicionales to set
     */
    public void setListaDatoAdicionales(List<SelectItem> listaDatoAdicionales) {
        this.listaDatoAdicionales = listaDatoAdicionales;
    }

    public List<NovedadDetalle> getListaNovedadDetalleEliminados() {
        return listaNovedadDetalleEliminados;
    }

    public void setListaNovedadDetalleEliminados(List<NovedadDetalle> listaNovedadDetalleEliminados) {
        this.listaNovedadDetalleEliminados = listaNovedadDetalleEliminados;
    }

    /**
     * @return the tienePermiso
     */
    public Boolean getTienePermiso() {
        return tienePermiso;
    }

    /**
     * @param tienePermiso the tienePermiso to set
     */
    public void setTienePermiso(Boolean tienePermiso) {
        this.tienePermiso = tienePermiso;
    }

    /**
     * @return the esDesconcentrado
     */
    public Boolean getEsDesconcentrado() {
        return esDesconcentrado;
    }

    /**
     * @param esDesconcentrado the esDesconcentrado to set
     */
    public void setEsDesconcentrado(Boolean esDesconcentrado) {
        this.esDesconcentrado = esDesconcentrado;
    }

    /**
     * @return the esNominaAbierta
     */
    public Boolean getEsNominaAbierta() {
        return esNominaAbierta;
    }

    /**
     * @param esNominaAbierta the esNominaAbierta to set
     */
    public void setEsNominaAbierta(Boolean esNominaAbierta) {
        this.esNominaAbierta = esNominaAbierta;
    }

    /**
     * @return the conAcceso
     */
    public Boolean getConAcceso() {
        return conAcceso;
    }

    /**
     * @param conAcceso the conAcceso to set
     */
    public void setConAcceso(Boolean conAcceso) {
        this.conAcceso = conAcceso;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<SelectItem> getListaTipoIdentificacion() {
        return listaTipoIdentificacion;
    }

    public void setListaTipoIdentificacion(List<SelectItem> listaTipoIdentificacion) {
        this.listaTipoIdentificacion = listaTipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
