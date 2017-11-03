/**
 * TipoMovimientoHelper.java Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of Proteus ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Proteus.
 *
 * Proteus Quito - Ecuador
 *
 * 22/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoHelp")
@SessionScoped
public class TipoMovimientoHelper extends CatalogoHelper {


    /**
     * Entidad de tipo de movimiento.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Grupo seleccionado.
     */
    private Grupo grupo;

    /**
     * Entidad de tipo de movimiento usada para la edicion y eliminacion.
     */
    private TipoMovimiento tipoMovimientoEditDelete;

    /**
     * Lista para todos los tipos de movimientos recuperados.
     */
    private List<TipoMovimiento> listaTipoMovimiento;

    /**
     * Lista para llenar el combo de tipo de contrato.
     */
    private List<SelectItem> listaTipoContrato;

    /**
     * Lista para llenar el combo de tipo de jornada.
     */
    private List<SelectItem> listaJornada;

    /**
     * Lista para llenar el combo de modalidad laboral desde el catalogo del
     * siit core.
     */
    private List<SelectItem> listaModalidadLaboral;

    /**
     * Lista para llenar el combo de nivel ocupacional desde el catalogo del
     * siit core.
     */
    private List<SelectItem> listaNivelOcupacional;

    /**
     * Lista para llenar el combo de motivos de ingreso desde el catalogo del
     * siit core.
     */
    private List<SelectItem> listaMotivosIngresos;

    /**
     * Lista para llenar el combo de motivos de salidas desde el catalogo del
     * siit core.
     */
    private List<SelectItem> listaMotivosSalidas;

    /**
     * Lista para llenar el combo de grupo desde el catalogo del Proteus.
     */
    private List<SelectItem> listaGrupo;

    /**
     * Lista para llenar el combo de clase desde el catalogo del Proteus.
     */
    private List<SelectItem> listaClase;

    /**
     * Lista para llenar el combo de documento habilitante.
     */
    private List<SelectItem> listaDocumentoHabilitante;

    /**
     * Lista para llenar los campos de configuracion. Obligatorio No Obligatorio
     * Deshabilitado
     */
    private List<SelectItem> listaCamposConfiguracion;

    /**
     * Lista para llenar los campos de configuracion. Obligatorio
     */
    private List<SelectItem> listaCamposConfiguracionObligatorio;

    /**
     * Lista de configuracion de area de accion de personal.
     */
    private List<SelectItem> listaCamposConfiguracionAccionPersonal;

    /**
     * Lista para llenar los campos de estado puesto.
     */
    private List<SelectItem> listaEstadoPuesto;

    /**
     * Lista para llenar los campos de estado personal.
     */
    private List<SelectItem> listaEstadoPersonal;

    /**
     * Lista para saber si es obligatorio o no.
     */
    private List<SelectItem> listaObligatorio;

    /**
     * Bandera para los campos requeridos del area puesto.
     */
    private Boolean requeridoAreaPuesto = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area rige a partir de.
     */
    private Boolean requeridoAreaRigeAPartirDe = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area periodo fijo.
     */
    private Boolean requeridoAreaPeriodoFijo = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area informacion de salida.
     */
    private Boolean requeridoAreaInformacionSalida = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area terminacion de contrato.
     */
    private Boolean requeridoAreaTerminacionContrato = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area servidor.
     */
    private Boolean requeridoAreaServidor = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area fallecimiento.
     */
    private Boolean requeridoAreaFallecimiento = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area contratos del codigo de
     * trabajo.
     */
    private Boolean requeridoAreaContratoCT = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area contratos de la LOSEP.
     */
    private Boolean requeridoAreaContratoLOSEP = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area discapacidad.
     */
    private Boolean requeridoAreaDiscapacidad = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area formacion academica.
     */
    private Boolean requeridoAreaFormacionAcademica = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area estado puesto.
     */
    private Boolean requeridoAreaEstadoPuesto = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area estado personal.
     */
    private Boolean requeridoAreaEstadoPersonal = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area estado puesto /personal
     * propuesto.
     */
    private Boolean requeridoAreaEstadoPuestoPropuesto = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area accion personal.
     */
    private Boolean requeridoAreaAccionPersonal = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area memorando.
     */
    private Boolean requeridoAreaMemorando = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area adendum.
     */
    private Boolean requeridoAreaAdendum = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area regimen disciplinario.
     */
    private Boolean requeridoAreaRegimenDisciplinario = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area regimen disciplinario
     * suspension.
     */
    private Boolean requeridoAreaRegimenDisciplinarioSuspension = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area licencias maternidad /
     * paternidad.
     */
    private Boolean requeridoAreaLicenciasMaternidadPaternidad = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area configuracion licencias
     * permisos.
     */
    private Boolean requeridoAreaConfiguracionLicenciasPermisos = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area licencias.
     */
    private Boolean requeridoAreaLicencias = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area tiempo por devengar.
     */
    private Boolean requeridoAreaTiempoPorDevengar = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area representacion de una
     * asociacion laboral.
     */
    private Boolean requeridoAreaRepresentacionAsociacionLaboral = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area permiso matricula hijos.
     */
    private Boolean requeridoAreaPermisoMatriculaHijos = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area comision de servicio -
     * Institucion requiriente.
     */
    private Boolean requeridoAreaComisionServicioInstitucionRequiriente = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area cambio administrativo.
     */
    private Boolean requeridoAreaCambioAdministrativo = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area traslado administrativo.
     */
    private Boolean requeridoAreaTrasladoAdministrativo = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area traspaso misma institucion.
     */
    private Boolean requeridoAreaTraspasoMismaInstitucion = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area puesto destino.
     */
    private Boolean requeridoAreaPuestoDestino = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area informacion movimiento
     * reintegro.
     */
    private Boolean requeridoAreaInformacionMovimientoReintegro = Boolean.FALSE;

    /**
     * Bandera para los campos requeridos del area finalizacion.
     */
    private Boolean requeridoAreaFinalizacion = Boolean.FALSE;

    /**
     * Lista de tipo de movimiento por nemonico.
     */
    private List<TipoMovimiento> listaTipoMovimientoNemonico;

    /**
     * Lista usada para llenar el combo de flujo.
     */
    private List<SelectItem> listaFlujo;

    /**
     * Lista usada para llenar el combo de periodo de tiempo maximo.
     */
    private List<SelectItem> listaPeriodoTiempoMaximo;

    /**
     * Bandera para activar o desactivar el campo de tiempo maximo en base al
     * periodo seleccionado.
     */
    private Boolean deshabilitarCampoTiempoMaximo;

    /**
     * Lista usada para llenar el combo de horario con si o no para el area de
     * configuracion de licencias permisos.
     */
    private List<SelectItem> listaHorario;

    /**
     * Lista usada para llenar el combo de periodo para control del area de
     * configuracion de licencias permisos.
     */
    private List<SelectItem> listaPeriodoControl;

    /**
     * Lista usada para llenar el combo de configuracion.
     */
    private List<SelectItem> listaCamposConfiguracionNoObligatorio;

    /**
     * Campo usado para validar el numero que es ingresado en tiempo maximo.
     */
    @Min(value = 0)
    @Max(value = 999)
    private String tiempoMaximo;

    /**
     * Lista para poblar el combo de control fecha presenta documento.
     */
    private List<SelectItem> listaControlFechaPresentaDocumento;

    /**
     * Bandera para saber si el campo de total horas semana es requerido o no.
     */
    private Boolean requeridoCampoTotalHorasSemana = Boolean.FALSE;

    /**
     * Bandera usada para saber si el campo de valor periodo en horas para
     * control es requerido.
     */
    private Boolean requeridoCampoValorPeriodoHorasControl = Boolean.FALSE;

    /**
     * Lista de tipo select item para si y no.
     */
    private List<SelectItem> listaSiNo;

    /**
     * Lista de tipo select item para llenar el combo del campo Ingresa Hora
     * Minuto.
     */
    private List<SelectItem> listaIngresaHoraMinuto;

    /**
     * Lista de tipos de movimientos de finalizacion.
     */
    private List<SelectItem> listaTiposMovimientoFinalizacion;

    /**
     * Lista de tipos de rotaciones.
     */
    private List<SelectItem> listaTipoRotaciones = new ArrayList<SelectItem>();

    /**
     * Id del grupo seleccionado en el filtro listaGruposFiltros
     */
    private Long grupoSeleccionadoFiltro;

    /**
     * Lista de clases base para el filtro de la tabla de tipos de movimientos
     */
    private List<SelectItem> listaClasesFiltros;

    /**
     * Id de la clase seleccionada en el filtro listaClasesFiltros
     */
    private Long clasesSeleccionadaFiltro;

    /**
     * Representacion JSON de la lista listaClasesFiltros se usa para actualizar
     * este filtro en la tabla
     */
    private String clasesFiltrosSeleccionados;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoHelper() {
        super();
        init();
    }

    /**
     * Este metodo inicializa las variables del helper.
     */
    public final void init() {
        tipoMovimiento = new TipoMovimiento();
        grupo = new Grupo();
        tipoMovimientoEditDelete = new TipoMovimiento();
        listaTipoMovimiento = new ArrayList<TipoMovimiento>();
        listaTipoContrato = new ArrayList<SelectItem>();
        listaJornada = new ArrayList<SelectItem>();
        listaModalidadLaboral = new ArrayList<SelectItem>();
        listaGrupo = new ArrayList<SelectItem>();
        listaClase = new ArrayList<SelectItem>();
        listaCamposConfiguracion = new ArrayList<SelectItem>();
        listaCamposConfiguracionObligatorio = new ArrayList<SelectItem>();
        listaEstadoPuesto = new ArrayList<SelectItem>();
        listaEstadoPersonal = new ArrayList<SelectItem>();
        listaNivelOcupacional = new ArrayList<SelectItem>();
        listaMotivosIngresos = new ArrayList<SelectItem>();
        listaMotivosSalidas = new ArrayList<SelectItem>();
        listaObligatorio = new ArrayList<SelectItem>();
        tipoMovimiento.setAreaPuesto(Boolean.TRUE);
        tipoMovimiento.setAreaRigeAPartirDe(Boolean.TRUE);
        tipoMovimiento.setAreaPeriodoFijo(Boolean.TRUE);
        tipoMovimiento.setAreaInformacionSalida(Boolean.TRUE);
        tipoMovimiento.setAreaServidor(Boolean.TRUE);
        tipoMovimiento.setAreaFallecimiento(Boolean.TRUE);
        tipoMovimiento.setAreaContratoCT(Boolean.TRUE);
        tipoMovimiento.setAreaContratoLosep(Boolean.TRUE);
        tipoMovimiento.setAreaDiscapacidad(Boolean.TRUE);
        tipoMovimiento.setAreaFormacionAcademica(Boolean.TRUE);
        tipoMovimiento.setAreaEstadoPuesto(Boolean.TRUE);
        tipoMovimiento.setAreaEstadoPersonal(Boolean.TRUE);
        tipoMovimiento.setAreaMemorando(Boolean.TRUE);
        tipoMovimiento.setAreaLicenciasMaternidadPaternidad(Boolean.TRUE);
        tipoMovimiento.setAreaConfiguracionLicenciasPermisos(Boolean.TRUE);
        tipoMovimiento.setAreaLicencias(Boolean.TRUE);
        tipoMovimiento.setAreaTiempoPorDevengar(Boolean.TRUE);
        tipoMovimiento.setAreaRepresentacionAsociacionLaboral(Boolean.TRUE);
        tipoMovimiento.setAreaPermisoMatriculaHijos(Boolean.TRUE);
        tipoMovimiento.setAreaComisionServicioInstitucionRequiriente(Boolean.TRUE);
        tipoMovimiento.setTipoMovimientoFinalizacion(new TipoMovimiento());
        setListaTipoMovimientoNemonico(new ArrayList<TipoMovimiento>());
        listaDocumentoHabilitante = new ArrayList<SelectItem>();
        listaCamposConfiguracionAccionPersonal = new ArrayList<SelectItem>();
        listaFlujo = new ArrayList<SelectItem>();
        listaPeriodoTiempoMaximo = new ArrayList<SelectItem>();
        tiempoMaximo = "";
        listaHorario = new ArrayList<SelectItem>();
        listaPeriodoControl = new ArrayList<SelectItem>();
        listaCamposConfiguracionNoObligatorio = new ArrayList<SelectItem>();
        listaControlFechaPresentaDocumento = new ArrayList<SelectItem>();
        listaSiNo = new ArrayList<SelectItem>();
        listaIngresaHoraMinuto = new ArrayList<SelectItem>();
        listaTiposMovimientoFinalizacion = new ArrayList<SelectItem>();
        listaClasesFiltros = new ArrayList<SelectItem>();
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
     * @return the tipoMovimientoEditDelete
     */
    public TipoMovimiento getTipoMovimientoEditDelete() {
        return tipoMovimientoEditDelete;
    }

    /**
     * @param tipoMovimientoEditDelete the tipoMovimientoEditDelete to set
     */
    public void setTipoMovimientoEditDelete(final TipoMovimiento tipoMovimientoEditDelete) {
        this.tipoMovimientoEditDelete = tipoMovimientoEditDelete;
    }

    /**
     * @return the listaTipoMovimiento
     */
    public List<TipoMovimiento> getListaTipoMovimiento() {
        return listaTipoMovimiento;
    }

    /**
     * @param listaTipoMovimiento the listaTipoMovimiento to set
     */
    public void setListaTipoMovimiento(final List<TipoMovimiento> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    /**
     * @return the listaModalidadLaboral
     */
    public List<SelectItem> getListaModalidadLaboral() {
        return listaModalidadLaboral;
    }

    /**
     * @param listaModalidadLaboral the listaModalidadLaboral to set
     */
    public void setListaModalidadLaboral(final List<SelectItem> listaModalidadLaboral) {
        this.listaModalidadLaboral = listaModalidadLaboral;
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
     * @return the listaTipoContrato
     */
    public List<SelectItem> getListaTipoContrato() {
        return listaTipoContrato;
    }

    /**
     * @param listaTipoContrato the listaTipoContrato to set
     */
    public void setListaTipoContrato(final List<SelectItem> listaTipoContrato) {
        this.listaTipoContrato = listaTipoContrato;
    }

    /**
     * @return the listaJornada
     */
    public List<SelectItem> getListaJornada() {
        return listaJornada;
    }

    /**
     * @param listaJornada the listaJornada to set
     */
    public void setListaJornada(final List<SelectItem> listaJornada) {
        this.listaJornada = listaJornada;
    }

    /**
     * @return the listaCamposConfiguracion
     */
    public List<SelectItem> getListaCamposConfiguracion() {
        return listaCamposConfiguracion;
    }

    /**
     * @param listaCamposConfiguracion the listaCamposConfiguracion to set
     */
    public void setListaCamposConfiguracion(final List<SelectItem> listaCamposConfiguracion) {
        this.listaCamposConfiguracion = listaCamposConfiguracion;
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
     * @return the listaEstadoPersonal
     */
    public List<SelectItem> getListaEstadoPersonal() {
        return listaEstadoPersonal;
    }

    /**
     * @param listaEstadoPersonal the listaEstadoPersonal to set
     */
    public void setListaEstadoPersonal(final List<SelectItem> listaEstadoPersonal) {
        this.listaEstadoPersonal = listaEstadoPersonal;
    }

    /**
     * @return the listaNivelOcupacional
     */
    public List<SelectItem> getListaNivelOcupacional() {
        return listaNivelOcupacional;
    }

    /**
     * @param listaNivelOcupacional the listaNivelOcupacional to set
     */
    public void setListaNivelOcupacional(final List<SelectItem> listaNivelOcupacional) {
        this.listaNivelOcupacional = listaNivelOcupacional;
    }

    /**
     * @return the listaObligatorio
     */
    public List<SelectItem> getListaObligatorio() {
        return listaObligatorio;
    }

    /**
     * @param listaObligatorio the listaObligatorio to set
     */
    public void setListaObligatorio(final List<SelectItem> listaObligatorio) {
        this.listaObligatorio = listaObligatorio;
    }

    /**
     * @return the requeridoAreaPuesto
     */
    public Boolean getRequeridoAreaPuesto() {
        return requeridoAreaPuesto;
    }

    /**
     * @param requeridoAreaPuesto the requeridoAreaPuesto to set
     */
    public void setRequeridoAreaPuesto(final Boolean requeridoAreaPuesto) {
        this.requeridoAreaPuesto = requeridoAreaPuesto;
    }

    /**
     * @return the requeridoAreaRigeAPartirDe
     */
    public Boolean getRequeridoAreaRigeAPartirDe() {
        return requeridoAreaRigeAPartirDe;
    }

    /**
     * @param requeridoAreaRigeAPartirDe the requeridoAreaRigeAPartirDe to set
     */
    public void setRequeridoAreaRigeAPartirDe(final Boolean requeridoAreaRigeAPartirDe) {
        this.requeridoAreaRigeAPartirDe = requeridoAreaRigeAPartirDe;
    }

    /**
     * @return the requeridoAreaPeriodoFijo
     */
    public Boolean getRequeridoAreaPeriodoFijo() {
        return requeridoAreaPeriodoFijo;
    }

    /**
     * @param requeridoAreaPeriodoFijo the requeridoAreaPeriodoFijo to set
     */
    public void setRequeridoAreaPeriodoFijo(final Boolean requeridoAreaPeriodoFijo) {
        this.requeridoAreaPeriodoFijo = requeridoAreaPeriodoFijo;
    }

    /**
     * @return the requeridoAreaInformacionSalida
     */
    public Boolean getRequeridoAreaInformacionSalida() {
        return requeridoAreaInformacionSalida;
    }

    /**
     * @param requeridoAreaInformacionSalida the requeridoAreaInformacionSalida
     * to set
     */
    public void setRequeridoAreaInformacionSalida(final Boolean requeridoAreaInformacionSalida) {
        this.requeridoAreaInformacionSalida = requeridoAreaInformacionSalida;
    }

    /**
     * @return the requeridoAreaServidor
     */
    public Boolean getRequeridoAreaServidor() {
        return requeridoAreaServidor;
    }

    /**
     * @param requeridoAreaServidor the requeridoAreaServidor to set
     */
    public void setRequeridoAreaServidor(final Boolean requeridoAreaServidor) {
        this.requeridoAreaServidor = requeridoAreaServidor;
    }

    /**
     * @return the requeridoAreaFallecimiento
     */
    public Boolean getRequeridoAreaFallecimiento() {
        return requeridoAreaFallecimiento;
    }

    /**
     * @param requeridoAreaFallecimiento the requeridoAreaFallecimiento to set
     */
    public void setRequeridoAreaFallecimiento(final Boolean requeridoAreaFallecimiento) {
        this.requeridoAreaFallecimiento = requeridoAreaFallecimiento;
    }

    /**
     * @return the requeridoAreaDiscapacidad
     */
    public Boolean getRequeridoAreaDiscapacidad() {
        return requeridoAreaDiscapacidad;
    }

    /**
     * @param requeridoAreaDiscapacidad the requeridoAreaDiscapacidad to set
     */
    public void setRequeridoAreaDiscapacidad(final Boolean requeridoAreaDiscapacidad) {
        this.requeridoAreaDiscapacidad = requeridoAreaDiscapacidad;
    }

    /**
     * @return the requeridoAreaFormacionAcademica
     */
    public Boolean getRequeridoAreaFormacionAcademica() {
        return requeridoAreaFormacionAcademica;
    }

    /**
     * @param requeridoAreaFormacionAcademica the
     * requeridoAreaFormacionAcademica to set
     */
    public void setRequeridoAreaFormacionAcademica(final Boolean requeridoAreaFormacionAcademica) {
        this.requeridoAreaFormacionAcademica = requeridoAreaFormacionAcademica;
    }

    /**
     * @return the requeridoAreaEstadoPuesto
     */
    public Boolean getRequeridoAreaEstadoPuesto() {
        return requeridoAreaEstadoPuesto;
    }

    /**
     * @param requeridoAreaEstadoPuesto the requeridoAreaEstadoPuesto to set
     */
    public void setRequeridoAreaEstadoPuesto(final Boolean requeridoAreaEstadoPuesto) {
        this.requeridoAreaEstadoPuesto = requeridoAreaEstadoPuesto;
    }

    /**
     * @return the requeridoAreaEstadoPersonal
     */
    public Boolean getRequeridoAreaEstadoPersonal() {
        return requeridoAreaEstadoPersonal;
    }

    /**
     * @param requeridoAreaEstadoPersonal the requeridoAreaEstadoPersonal to set
     */
    public void setRequeridoAreaEstadoPersonal(final Boolean requeridoAreaEstadoPersonal) {
        this.requeridoAreaEstadoPersonal = requeridoAreaEstadoPersonal;
    }

    /**
     * @return the listaTipoMovimientoNemonico
     */
    public List<TipoMovimiento> getListaTipoMovimientoNemonico() {
        return listaTipoMovimientoNemonico;
    }

    /**
     * @param listaTipoMovimientoNemonico the listaTipoMovimientoNemonico to set
     */
    public void setListaTipoMovimientoNemonico(final List<TipoMovimiento> listaTipoMovimientoNemonico) {
        this.listaTipoMovimientoNemonico = listaTipoMovimientoNemonico;
    }

    /**
     * @return the listaDocumentoHabilitante
     */
    public List<SelectItem> getListaDocumentoHabilitante() {
        return listaDocumentoHabilitante;
    }

    /**
     * @param listaDocumentoHabilitante the listaDocumentoHabilitante to set
     */
    public void setListaDocumentoHabilitante(final List<SelectItem> listaDocumentoHabilitante) {
        this.listaDocumentoHabilitante = listaDocumentoHabilitante;
    }

    /**
     * @return the requeridoAreaAccionPersonal
     */
    public Boolean getRequeridoAreaAccionPersonal() {
        return requeridoAreaAccionPersonal;
    }

    /**
     * @param requeridoAreaAccionPersonal the requeridoAreaAccionPersonal to set
     */
    public void setRequeridoAreaAccionPersonal(final Boolean requeridoAreaAccionPersonal) {
        this.requeridoAreaAccionPersonal = requeridoAreaAccionPersonal;
    }

    /**
     * @return the listaCamposConfiguracionAccionPersonal
     */
    public List<SelectItem> getListaCamposConfiguracionAccionPersonal() {
        return listaCamposConfiguracionAccionPersonal;
    }

    /**
     * @param listaCamposConfiguracionAccionPersonal the
     * listaCamposConfiguracionAccionPersonal to set
     */
    public void setListaCamposConfiguracionAccionPersonal(
            final List<SelectItem> listaCamposConfiguracionAccionPersonal) {
        this.listaCamposConfiguracionAccionPersonal = listaCamposConfiguracionAccionPersonal;
    }

    /**
     * @return the requeridoAreaContratoCT
     */
    public Boolean getRequeridoAreaContratoCT() {
        return requeridoAreaContratoCT;
    }

    /**
     * @param requeridoAreaContratoCT the requeridoAreaContratoCT to set
     */
    public void setRequeridoAreaContratoCT(final Boolean requeridoAreaContratoCT) {
        this.requeridoAreaContratoCT = requeridoAreaContratoCT;
    }

    /**
     * @return the requeridoAreaContratoLOSEP
     */
    public Boolean getRequeridoAreaContratoLOSEP() {
        return requeridoAreaContratoLOSEP;
    }

    /**
     * @param requeridoAreaContratoLOSEP the requeridoAreaContratoLOSEP to set
     */
    public void setRequeridoAreaContratoLOSEP(final Boolean requeridoAreaContratoLOSEP) {
        this.requeridoAreaContratoLOSEP = requeridoAreaContratoLOSEP;
    }

    /**
     * @return the listaCamposConfiguracionObligatorio
     */
    public List<SelectItem> getListaCamposConfiguracionObligatorio() {
        return listaCamposConfiguracionObligatorio;
    }

    /**
     * @param listaCamposConfiguracionObligatorio the
     * listaCamposConfiguracionObligatorio to set
     */
    public void setListaCamposConfiguracionObligatorio(final List<SelectItem> listaCamposConfiguracionObligatorio) {
        this.listaCamposConfiguracionObligatorio = listaCamposConfiguracionObligatorio;
    }

    /**
     * @return the requeridoAreaMemorando
     */
    public Boolean getRequeridoAreaMemorando() {
        return requeridoAreaMemorando;
    }

    /**
     * @param requeridoAreaMemorando the requeridoAreaMemorando to set
     */
    public void setRequeridoAreaMemorando(final Boolean requeridoAreaMemorando) {
        this.requeridoAreaMemorando = requeridoAreaMemorando;
    }

    /**
     * @return the requeridoAreaRegimenDisciplinario
     */
    public Boolean getRequeridoAreaRegimenDisciplinario() {
        return requeridoAreaRegimenDisciplinario;
    }

    /**
     * @param requeridoAreaRegimenDisciplinario the
     * requeridoAreaRegimenDisciplinario to set
     */
    public void setRequeridoAreaRegimenDisciplinario(final Boolean requeridoAreaRegimenDisciplinario) {
        this.requeridoAreaRegimenDisciplinario = requeridoAreaRegimenDisciplinario;
    }

    /**
     * @return the requeridoAreaRegimenDisciplinarioSuspension
     */
    public Boolean getRequeridoAreaRegimenDisciplinarioSuspension() {
        return requeridoAreaRegimenDisciplinarioSuspension;
    }

    /**
     * @param requeridoAreaRegimenDisciplinarioSuspension the
     * requeridoAreaRegimenDisciplinarioSuspension to set
     */
    public void setRequeridoAreaRegimenDisciplinarioSuspension(final Boolean requeridoAreaRegimenDisciplinarioSuspension) {
        this.requeridoAreaRegimenDisciplinarioSuspension = requeridoAreaRegimenDisciplinarioSuspension;
    }

    /**
     * @return the listaMotivosIngresos
     */
    public List<SelectItem> getListaMotivosIngresos() {
        return listaMotivosIngresos;
    }

    /**
     * @return the listaMotivosSalidas
     */
    public List<SelectItem> getListaMotivosSalidas() {
        return listaMotivosSalidas;
    }

    /**
     * @param listaMotivosIngresos the listaMotivosIngresos to set
     */
    public void setListaMotivosIngresos(final List<SelectItem> listaMotivosIngresos) {
        this.listaMotivosIngresos = listaMotivosIngresos;
    }

    /**
     * @param listaMotivosSalidas the listaMotivosSalidas to set
     */
    public void setListaMotivosSalidas(final List<SelectItem> listaMotivosSalidas) {
        this.listaMotivosSalidas = listaMotivosSalidas;
    }

    /**
     * @return the requeridoAreaLicenciasMaternidadPaternidad
     */
    public Boolean getRequeridoAreaLicenciasMaternidadPaternidad() {
        return requeridoAreaLicenciasMaternidadPaternidad;
    }

    /**
     * @param requeridoAreaLicenciasMaternidadPaternidad the
     * requeridoAreaLicenciasMaternidadPaternidad to set
     */
    public void setRequeridoAreaLicenciasMaternidadPaternidad(
            final Boolean requeridoAreaLicenciasMaternidadPaternidad) {
        this.requeridoAreaLicenciasMaternidadPaternidad = requeridoAreaLicenciasMaternidadPaternidad;
    }

    /**
     * @return the listaFlujo
     */
    public List<SelectItem> getListaFlujo() {
        return listaFlujo;
    }

    /**
     * @param listaFlujo the listaFlujo to set
     */
    public void setListaFlujo(final List<SelectItem> listaFlujo) {
        this.listaFlujo = listaFlujo;
    }

    /**
     * @return the listaPeriodoTiempoMaximo
     */
    public List<SelectItem> getListaPeriodoTiempoMaximo() {
        return listaPeriodoTiempoMaximo;
    }

    /**
     * @param listaPeriodoTiempoMaximo the listaPeriodoTiempoMaximo to set
     */
    public void setListaPeriodoTiempoMaximo(final List<SelectItem> listaPeriodoTiempoMaximo) {
        this.listaPeriodoTiempoMaximo = listaPeriodoTiempoMaximo;
    }

    /**
     * @return the deshabilitarCampoTiempoMaximo
     */
    public Boolean getDeshabilitarCampoTiempoMaximo() {
        return deshabilitarCampoTiempoMaximo;
    }

    /**
     * @param deshabilitarCampoTiempoMaximo the deshabilitarCampoTiempoMaximo to
     * set
     */
    public void setDeshabilitarCampoTiempoMaximo(final Boolean deshabilitarCampoTiempoMaximo) {
        this.deshabilitarCampoTiempoMaximo = deshabilitarCampoTiempoMaximo;
    }

    /**
     * @return the tiempoMaximo
     */
    public String getTiempoMaximo() {
        return tiempoMaximo;
    }

    /**
     * @param tiempoMaximo the tiempoMaximo to set
     */
    public void setTiempoMaximo(final String tiempoMaximo) {
        this.tiempoMaximo = tiempoMaximo;
    }

    /**
     * @return the requeridoAreaConfiguracionLicenciasPermisos
     */
    public Boolean getRequeridoAreaConfiguracionLicenciasPermisos() {
        return requeridoAreaConfiguracionLicenciasPermisos;
    }

    /**
     * @param requeridoAreaConfiguracionLicenciasPermisos the
     * requeridoAreaConfiguracionLicenciasPermisos to set
     */
    public void setRequeridoAreaConfiguracionLicenciasPermisos(
            final Boolean requeridoAreaConfiguracionLicenciasPermisos) {
        this.requeridoAreaConfiguracionLicenciasPermisos = requeridoAreaConfiguracionLicenciasPermisos;
    }

    /**
     * @return the listaHorario
     */
    public List<SelectItem> getListaHorario() {
        return listaHorario;
    }

    /**
     * @param listaHorario the listaHorario to set
     */
    public void setListaHorario(final List<SelectItem> listaHorario) {
        this.listaHorario = listaHorario;
    }

    /**
     * @return the listaPeriodoControl
     */
    public List<SelectItem> getListaPeriodoControl() {
        return listaPeriodoControl;
    }

    /**
     * @param listaPeriodoControl the listaPeriodoControl to set
     */
    public void setListaPeriodoControl(final List<SelectItem> listaPeriodoControl) {
        this.listaPeriodoControl = listaPeriodoControl;
    }

    /**
     * @return the requeridoAreaLicencias
     */
    public Boolean getRequeridoAreaLicencias() {
        return requeridoAreaLicencias;
    }

    /**
     * @param requeridoAreaLicencias the requeridoAreaLicencias to set
     */
    public void setRequeridoAreaLicencias(final Boolean requeridoAreaLicencias) {
        this.requeridoAreaLicencias = requeridoAreaLicencias;
    }

    /**
     * @return the requeridoAreaTiempoPorDevengar
     */
    public Boolean getRequeridoAreaTiempoPorDevengar() {
        return requeridoAreaTiempoPorDevengar;
    }

    /**
     * @param requeridoAreaTiempoPorDevengar the requeridoAreaTiempoPorDevengar
     * to set
     */
    public void setRequeridoAreaTiempoPorDevengar(final Boolean requeridoAreaTiempoPorDevengar) {
        this.requeridoAreaTiempoPorDevengar = requeridoAreaTiempoPorDevengar;
    }

    /**
     * @return the listaCamposConfiguracionNoObligatorio
     */
    public List<SelectItem> getListaCamposConfiguracionNoObligatorio() {
        return listaCamposConfiguracionNoObligatorio;
    }

    /**
     * @param listaCamposConfiguracionNoObligatorio the
     * listaCamposConfiguracionNoObligatorio to set
     */
    public void setListaCamposConfiguracionNoObligatorio(
            final List<SelectItem> listaCamposConfiguracionNoObligatorio) {
        this.listaCamposConfiguracionNoObligatorio = listaCamposConfiguracionNoObligatorio;
    }

    /**
     * @return the listaControlFechaPresentaDocumento
     */
    public List<SelectItem> getListaControlFechaPresentaDocumento() {
        return listaControlFechaPresentaDocumento;
    }

    /**
     * @param listaControlFechaPresentaDocumento the
     * listaControlFechaPresentaDocumento to set
     */
    public void setListaControlFechaPresentaDocumento(final List<SelectItem> listaControlFechaPresentaDocumento) {
        this.listaControlFechaPresentaDocumento = listaControlFechaPresentaDocumento;
    }

    /**
     * @return the requeridoCampoTotalHorasSemana
     */
    public Boolean getRequeridoCampoTotalHorasSemana() {
        return requeridoCampoTotalHorasSemana;
    }

    /**
     * @param requeridoCampoTotalHorasSemana the requeridoCampoTotalHorasSemana
     * to set
     */
    public void setRequeridoCampoTotalHorasSemana(final Boolean requeridoCampoTotalHorasSemana) {
        this.requeridoCampoTotalHorasSemana = requeridoCampoTotalHorasSemana;
    }

    /**
     * @return the requeridoCampoValorPeriodoHorasControl
     */
    public Boolean getRequeridoCampoValorPeriodoHorasControl() {
        return requeridoCampoValorPeriodoHorasControl;
    }

    /**
     * @param requeridoCampoValorPeriodoHorasControl the
     * requeridoCampoValorPeriodoHorasControl to set
     */
    public void setRequeridoCampoValorPeriodoHorasControl(final Boolean requeridoCampoValorPeriodoHorasControl) {
        this.requeridoCampoValorPeriodoHorasControl = requeridoCampoValorPeriodoHorasControl;
    }

    /**
     * @return the listaSiNo
     */
    public List<SelectItem> getListaSiNo() {
        return listaSiNo;
    }

    /**
     * @param listaSiNo the listaSiNo to set
     */
    public void setListaSiNo(final List<SelectItem> listaSiNo) {
        this.listaSiNo = listaSiNo;
    }

    /**
     * @return the listaIngresaHoraMinuto
     */
    public List<SelectItem> getListaIngresaHoraMinuto() {
        return listaIngresaHoraMinuto;
    }

    /**
     * @param listaIngresaHoraMinuto the listaIngresaHoraMinuto to set
     */
    public void setListaIngresaHoraMinuto(final List<SelectItem> listaIngresaHoraMinuto) {
        this.listaIngresaHoraMinuto = listaIngresaHoraMinuto;
    }

    /**
     * @return the requeridoAreaRepresentacionAsociacionLaboral
     */
    public Boolean getRequeridoAreaRepresentacionAsociacionLaboral() {
        return requeridoAreaRepresentacionAsociacionLaboral;
    }

    /**
     * @param requeridoAreaRepresentacionAsociacionLaboral the
     * requeridoAreaRepresentacionAsociacionLaboral to set
     */
    public void setRequeridoAreaRepresentacionAsociacionLaboral(
            final Boolean requeridoAreaRepresentacionAsociacionLaboral) {
        this.requeridoAreaRepresentacionAsociacionLaboral = requeridoAreaRepresentacionAsociacionLaboral;
    }

    /**
     * @return the requeridoAreaPermisoMatriculaHijos
     */
    public Boolean getRequeridoAreaPermisoMatriculaHijos() {
        return requeridoAreaPermisoMatriculaHijos;
    }

    /**
     * @param requeridoAreaPermisoMatriculaHijos the
     * requeridoAreaPermisoMatriculaHijos to set
     */
    public void setRequeridoAreaPermisoMatriculaHijos(final Boolean requeridoAreaPermisoMatriculaHijos) {
        this.requeridoAreaPermisoMatriculaHijos = requeridoAreaPermisoMatriculaHijos;
    }

    /**
     * @return the requeridoAreaComisionServicioInstitucionRequiriente
     */
    public Boolean getRequeridoAreaComisionServicioInstitucionRequiriente() {
        return requeridoAreaComisionServicioInstitucionRequiriente;
    }

    /**
     * @param requeridoAreaComisionServicioInstitucionRequiriente the
     * requeridoAreaComisionServicioInstitucionRequiriente to set
     */
    public void setRequeridoAreaComisionServicioInstitucionRequiriente(
            final Boolean requeridoAreaComisionServicioInstitucionRequiriente) {
        this.requeridoAreaComisionServicioInstitucionRequiriente = requeridoAreaComisionServicioInstitucionRequiriente;
    }

    /**
     * @return the requeridoAreaCambioAdministrativo
     */
    public Boolean getRequeridoAreaCambioAdministrativo() {
        return requeridoAreaCambioAdministrativo;
    }

    /**
     * @param requeridoAreaCambioAdministrativo the
     * requeridoAreaCambioAdministrativo to set
     */
    public void setRequeridoAreaCambioAdministrativo(final Boolean requeridoAreaCambioAdministrativo) {
        this.requeridoAreaCambioAdministrativo = requeridoAreaCambioAdministrativo;
    }

    /**
     * @return the requeridoAreaTrasladoAdministrativo
     */
    public Boolean getRequeridoAreaTrasladoAdministrativo() {
        return requeridoAreaTrasladoAdministrativo;
    }

    /**
     * @param requeridoAreaTrasladoAdministrativo the
     * requeridoAreaTrasladoAdministrativo to set
     */
    public void setRequeridoAreaTrasladoAdministrativo(final Boolean requeridoAreaTrasladoAdministrativo) {
        this.requeridoAreaTrasladoAdministrativo = requeridoAreaTrasladoAdministrativo;
    }

    /**
     * @return the requeridoAreaTraspasoMismaInstitucion
     */
    public Boolean getRequeridoAreaTraspasoMismaInstitucion() {
        return requeridoAreaTraspasoMismaInstitucion;
    }

    /**
     * @param requeridoAreaTraspasoMismaInstitucion the
     * requeridoAreaTraspasoMismaInstitucion to set
     */
    public void setRequeridoAreaTraspasoMismaInstitucion(final Boolean requeridoAreaTraspasoMismaInstitucion) {
        this.requeridoAreaTraspasoMismaInstitucion = requeridoAreaTraspasoMismaInstitucion;
    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(final Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the requeridoAreaPuestoDestino
     */
    public Boolean getRequeridoAreaPuestoDestino() {
        return requeridoAreaPuestoDestino;
    }

    /**
     * @param requeridoAreaPuestoDestino the requeridoAreaPuestoDestino to set
     */
    public void setRequeridoAreaPuestoDestino(final Boolean requeridoAreaPuestoDestino) {
        this.requeridoAreaPuestoDestino = requeridoAreaPuestoDestino;
    }

    /**
     * @return the requeridoAreaEstadoPuestoPropuesto
     */
    public Boolean getRequeridoAreaEstadoPuestoPropuesto() {
        return requeridoAreaEstadoPuestoPropuesto;
    }

    /**
     * @param requeridoAreaEstadoPuestoPropuesto the
     * requeridoAreaEstadoPuestoPropuesto to set
     */
    public void setRequeridoAreaEstadoPuestoPropuesto(final Boolean requeridoAreaEstadoPuestoPropuesto) {
        this.requeridoAreaEstadoPuestoPropuesto = requeridoAreaEstadoPuestoPropuesto;
    }

    /**
     * @return the requeridoAreaInformacionMovimientoReintegro
     */
    public Boolean getRequeridoAreaInformacionMovimientoReintegro() {
        return requeridoAreaInformacionMovimientoReintegro;
    }

    /**
     * @param requeridoAreaInformacionMovimientoReintegro the
     * requeridoAreaInformacionMovimientoReintegro to set
     */
    public void setRequeridoAreaInformacionMovimientoReintegro(final Boolean requeridoAreaInformacionMovimientoReintegro) {
        this.requeridoAreaInformacionMovimientoReintegro = requeridoAreaInformacionMovimientoReintegro;
    }

    /**
     * @return the listaTiposMovimientoFinalizacion
     */
    public List<SelectItem> getListaTiposMovimientoFinalizacion() {
        return listaTiposMovimientoFinalizacion;
    }

    /**
     * @param listaTiposMovimientoFinalizacion the
     * listaTiposMovimientoFinalizacion to set
     */
    public void setListaTiposMovimientoFinalizacion(final List<SelectItem> listaTiposMovimientoFinalizacion) {
        this.listaTiposMovimientoFinalizacion = listaTiposMovimientoFinalizacion;
    }

    /**
     * @return the requeridoAreaFinalizacion
     */
    public Boolean getRequeridoAreaFinalizacion() {
        return requeridoAreaFinalizacion;
    }

    /**
     * @param requeridoAreaFinalizacion the requeridoAreaFinalizacion to set
     */
    public void setRequeridoAreaFinalizacion(final Boolean requeridoAreaFinalizacion) {
        this.requeridoAreaFinalizacion = requeridoAreaFinalizacion;
    }

    /**
     * @return the listaTipoRotaciones
     */
    public List<SelectItem> getListaTipoRotaciones() {
        return listaTipoRotaciones;
    }

    /**
     * @param listaTipoRotaciones the listaTipoRotaciones to set
     */
    public void setListaTipoRotaciones(final List<SelectItem> listaTipoRotaciones) {
        this.listaTipoRotaciones = listaTipoRotaciones;
    }

    /**
     * @return the requeridoAreaAdendum
     */
    public Boolean getRequeridoAreaAdendum() {
        return requeridoAreaAdendum;
    }

    /**
     * @param requeridoAreaAdendum the requeridoAreaAdendum to set
     */
    public void setRequeridoAreaAdendum(final Boolean requeridoAreaAdendum) {
        this.requeridoAreaAdendum = requeridoAreaAdendum;
    }

    /**
     * @return the requeridoAreaTerminacionContrato
     */
    public Boolean getRequeridoAreaTerminacionContrato() {
        return requeridoAreaTerminacionContrato;
    }

    /**
     * @param requeridoAreaTerminacionContrato the
     * requeridoAreaTerminacionContrato to set
     */
    public void setRequeridoAreaTerminacionContrato(final Boolean requeridoAreaTerminacionContrato) {
        this.requeridoAreaTerminacionContrato = requeridoAreaTerminacionContrato;
    }

    public Long getGrupoSeleccionadoFiltro() {
        return grupoSeleccionadoFiltro;
    }

    public String getClasesFiltrosSeleccionados() {
        return clasesFiltrosSeleccionados;
    }

    public void setClasesFiltrosSeleccionados(String clasesFiltrosSeleccionados) {
        this.clasesFiltrosSeleccionados = clasesFiltrosSeleccionados;
    }

    public void setGrupoSeleccionadoFiltro(Long grupoSeleccionadoFiltro) {
        this.grupoSeleccionadoFiltro = grupoSeleccionadoFiltro;
    }

    public List<SelectItem> getListaClasesFiltros() {
        return listaClasesFiltros;
    }

    public void setListaClasesFiltros(List<SelectItem> listaClasesFiltros) {
        this.listaClasesFiltros = listaClasesFiltros;
    }

    public Long getClasesSeleccionadaFiltro() {
        return clasesSeleccionadaFiltro;
    }

    public void setClasesSeleccionadaFiltro(Long clasesSeleccionadaFiltro) {
        this.clasesSeleccionadaFiltro = clasesSeleccionadaFiltro;
    }

    
}
