/*
 *  AgregarServidorControlador.java
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
 *  Quito - Ecuador|
 *  
 *
 *  27/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.modelo.TrasladoAdministrativo;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.RegimenDisciplinario;
import ec.com.atikasoft.proteus.modelo.Cesacion;
import ec.com.atikasoft.proteus.modelo.CambioAdministrativo;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.Traspaso;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.ComisionServicio;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.enums.TipoDesignacionEnum;
import ec.com.atikasoft.proteus.enums.NivelEstudioHijoEnLicenciaEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoNacimientoEnum;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.CasoFallecimientoEnum;
import ec.com.atikasoft.proteus.enums.CorrespondeDiscapacidadEnum;
import ec.com.atikasoft.proteus.enums.FaltasRegimenDisciplinarioEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.EspecifiqueDiscapacidadEnum;
import ec.com.atikasoft.proteus.enums.TipoTemporadaEnum;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.AgregarServidorHelper;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaPuestoHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.MovimientoServicio;
import ec.com.atikasoft.proteus.servicio.ReclutamientoServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.servicio.ValidacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.SelectEvent;

/**
 * Controlador de Llenar Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "agregarServidorBean")
@ViewScoped
public class AgregarServidorControlador extends BaseControlador {

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/agregar_servidor.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{agregarServidorHelper}")
    private AgregarServidorHelper agregarServidorHelper;

    /**
     * Helper de Busqueda Puesto Bean.
     */
    @ManagedProperty("#{busquedaPuestoHelper}")
    private BusquedaPuestoHelper busquedaPuestoHelper;

    /**
     * Campos a procesar cuando el tipo de documento cambie.
     */
    private final String[] camposTipoDocumento = { "nombres", "fechaNacimiento", "estadoCivil", "edad",
        "conyuge"};

    /**
     * Propiedad del elemento ui input.
     */
    protected static final String PROPIEDAD = "disabled";

    /**
     * prependId del formulario.
     */
    private static final String ID_FORMULARIO = "frmLlenarVancante:";

    /**
     * Servicio de Tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de validaciones.
     */
    @EJB
    private ValidacionServicio validacionServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de movimiento
     */
    @EJB
    private MovimientoServicio movimientoServicio;

    /**
     * Servicio de regimenes laborales.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Dao de parametros de la institucion.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * DAO de servidor.
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Servicio de reclutamiento.
     */
    @EJB
    private ReclutamientoServicio reclutamientoServicio;

    /**
     * Servicio de distributuvo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Dao de servidores en institucion.
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Constructor por defecto.
     */
    public AgregarServidorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        agregarServidorHelper.setUltimoTrabajoServidor(null);
        agregarServidorHelper.setRegistroManualNombres(Boolean.FALSE);
        String navegacion = getRequest().getParameter("est");
        if (navegacion == null) {
            salirPantallaPrincipal();
        } else {
            agregarServidorHelper.setControlNavegacion(navegacion);
            if (navegacion.equals("edt")) {
                configurarFormulario();
            } else if (navegacion.equals("vr") || navegacion.equals("vre")) {
                //configurarFormulario();
                configurarFormularioReanOnly();
            } else {
                salirPantallaPrincipal();
            }
            consultarCatalogos();
            iniciarDatos();
            iniciarImprimirAccionPersonal();
            seleccionEspecifiqueDiscapacidad(null);
            iniciarComboCasoFallecimiento();
            iniciarComboNivelEstudioHijo();
            iniciarComboDocumentoPrevio();
            if (navegacion.equals("edt")) {
                controlSeleccionTipoDocumento();
            }
            try {
                agregarServidorHelper.setMovimiento(tramiteServicio.buscarMovimientoPorId(
                        agregarServidorHelper.getMovimiento().getId()));
            } catch (ServicioException ex) {
                Logger.getLogger(AgregarServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            calcularDiferenciaHorasFecha();
            inicializaExplicacion();
            agregarServidorHelper.setPuesto(null);
            agregarServidorHelper.getListaPuestos().clear();
            agregarServidorHelper.getMovimientosAFinalizar().clear();
        }
    }

    /**
     * Este metodo asigna los valores de la fila seleccionada en traslado.
     *
     * @param event SelectEvent
     */
    public void seleccionTraslado(final SelectEvent event) {
        TrasladoAdministrativo traslado = agregarServidorHelper.getTrasladoAdministrativo();
//        SrhvOrganicoPosicionalIndividual sopi = agregarServidorHelper.getSopi();
//        traslado.setDenominacionPuestoId(sopi.getPuestoInstitucionId());
//        traslado.setDenominacionPuestoNombre(sopi.getPuestoInstitucion());
//        traslado.setGrupoOcupacionalId(sopi.getGrupoOcupacionalId());
//        traslado.setGrupoOcupacionalNombre(sopi.getGrupoOcupacional());
//        traslado.setPartidaGeneral(sopi.getPartidaGeneral());
//        traslado.setPartidaIndividual(sopi.getPartidaIndividual());
//        traslado.setRmu(sopi.getSueldo());
//        traslado.setLugarNombre(sopi.getCantonPuestoNombre());
//        traslado.setLugarId(sopi.getCantonPuestoId());
//        traslado.setUnidadAdminitrativaCoreId(sopi.getUnidadAdministrativaId());
//        traslado.setUnidadAdministrativaCoreNombre(sopi.getUnidadAdministrativa());

        Movimiento m = agregarServidorHelper.getMovimiento();
//        m.getMovimientoSituacionPropuesta().setServidorPuestoCoreId(sopi.getServidorPuestoId());
//        m.getMovimientoSituacionPropuesta().setOrganigramaPosicionalIndividualCoreId(sopi.getId());
//        m.getMovimientoSituacionPropuesta().setLugarTrabajo(sopi.getCantonPuestoNombre());
//        m.getMovimientoSituacionPropuesta().setPartidaPresupuestariaGeneral(sopi.getPartidaGeneral());
//        m.getMovimientoSituacionPropuesta().setPartidaPresupuestariaIndividual(sopi.getPartidaIndividual());
//        m.getMovimientoSituacionPropuesta().setProceso(sopi.getUnidadAdministrativaPadre());
//        m.getMovimientoSituacionPropuesta().setPuesto(sopi.getPuestoInstitucion());
//        m.getMovimientoSituacionPropuesta().setRemuneracionMensual(sopi.getSueldo());
//        m.getMovimientoSituacionPropuesta().setSubproceso(sopi.getUnidadAdministrativa());
//        agregarServidorHelper.getSubrogacion().setServidorPuestoId(sopi.getServidorPuestoId());
        actualizarComponente(ID_FORMULARIO.concat("puestoSelectoTraslado"));
    }

    /**
     * Este metodo asigna los valores de la fila seleccionada en puesto propuesto.
     *
     * @param event SelectEvent
     */
    public void seleccionPuestoPropuesto(final SelectEvent event) {
        Movimiento m = agregarServidorHelper.getMovimiento();
        m.getMovimientoSituacionPropuesta().setDistributivoDetalle(agregarServidorHelper.getPuesto());
        m.getMovimientoSituacionPropuesta().setDistributivoDetalleId(agregarServidorHelper.getPuesto().getId());
        actualizarComponente(ID_FORMULARIO.concat("puestoSelectoPuestoPropuesto"));
    }

    /**
     * Este metodo asigna los valores de la fila seleccionada en puesto propuesto.
     *
     * @param event SelectEvent
     */
    public void seleccionMovimientoAFinalizar(final SelectEvent event) {
        Movimiento m = agregarServidorHelper.getMovimiento();
        actualizarComponente(ID_FORMULARIO.concat("puestoSelectoMovimientoAFinalizar"));
    }

    /**
     * Este metodo busca instituciones por nombre.
     *
     * @return String
     */
    public String buscarInstitucionPorNombre() {
        try {
//            agregarServidorHelper.getListaInstituciones().clear();
//            List<Long> institucionAExcluir = new ArrayList<Long>();
//            institucionAExcluir.add(obtenerUsuarioLogeado().getInstitucionId());
//            List<ec.gob.mrl.siith.adm.model.Institucion> buscarPorNombre = institucionServicio.buscarPorNombre(
//                    agregarServidorHelper.getNombreInstitucionBuscar(), institucionAExcluir, 3, MAX_ROWS);
//            agregarServidorHelper.getListaInstituciones().addAll(buscarPorNombre);
        } catch (Exception e) {
            error(getClass().getName(), "Error ", e);
        }
        return null;
    }

    /**
     * Este metodo inicia la busqueda de institucion por nombre.
     *
     * @return String
     */
    public String iniciarBuscarInstitucionPorNombre() {
//        agregarServidorHelper.getListaInstituciones().clear();
        agregarServidorHelper.setNombreInstitucionBuscar(null);
        ejecutarComandoPrimefaces("buscadorDeInstitucion.show();");
        actualizarComponente("frmBuscadorDeInstitucion");
        return null;
    }

    /**
     * Este metodo calcula la diferencia de horas de las fechas.
     */
    public void calcularDiferenciaHorasFecha() {
        java.util.Date rigeApartirDe = agregarServidorHelper.getMovimiento().getRigeApartirDe();
        java.util.Date fechaHasta = agregarServidorHelper.getMovimiento().getFechaHasta();
        agregarServidorHelper.setDiferenciaHoras("00:00");
        if (rigeApartirDe != null && fechaHasta != null) {
            agregarServidorHelper.setDiferenciaHoras(
                    obtenerHorasMinutos(UtilFechas.calcularDiferenciaMinutosEntreFechas(
                                    rigeApartirDe, fechaHasta)));
        }
    }

    /**
     * Este metodo toma las horas en minutos y los pasas a formato hora:minutos.
     *
     * @param minuros Long
     * @return String
     */
    public String obtenerHorasMinutos(final Long minuros) {
        String total = "00:00";
        try {
            total = (completar(minuros / 60) + " : " + completar(minuros - ((minuros / 60) * 60L)));
        } catch (Exception e) {
            error(getClass().getName(), "Error al generar hora.", e);
        }
        return total;
    }

    /**
     * Este metodo consulta las unidades administrativas, excepto a la que corresponde al servidor.
     *
     * @throws Exception Control de errores
     *
     */
    private void buscarUnidadesOrganizacionales() throws Exception {
        agregarServidorHelper.getListaUnidadOrganizacional().clear();
        List<UnidadOrganizacional> unidades = admServicio.listarTodosUnidadOrganizacionalPorNombre(null);
        agregarServidorHelper.getListaUnidadOrganizacional().addAll(unidades);
    }

    /**
     * Este método es usado para completar el mes o año con un cero a la izquierda.
     *
     * @param o Object
     * @return String
     */
    private String completar(final Object o) {
        String resultado = o.toString();
        if (resultado.length() == 1) {
            resultado = "0".concat(resultado);
        }
        return resultado;
    }

    /**
     * Este metodo limipa los valores segun la seleccion.
     */
    public void limpiarAreaLicencia() {
        Licencia licencia = agregarServidorHelper.getLicencia();
        licencia.setDevengarPeriodo(null);
        licencia.setDevengarValor(null);
        licencia.setDevengarObservacion(null);
    }

    /**
     * Este método forza a regresar al formularion del trámite. setPais
     *
     * @return String
     */
    public String regresar() {
        if (agregarServidorHelper.getControlNavegacion().equals("vr") || agregarServidorHelper.getControlNavegacion().
                equals("edt")) {
            reglaNavegacionDirecta(TramiteControlador.FORMULARIO_ENTIDAD.concat("?est=").
                    concat(agregarServidorHelper.getControlNavegacion()));
        } else if (agregarServidorHelper.getControlNavegacion().equals("vre")) {
            reglaNavegacionDirecta(TramiteLegalizacionControlador.PAGINA);
        }
        return null;
    }

    /**
     * Este método procesa la actualización del movimiento.
     *
     * @return String
     */
    public String guardar() {
        try {
            if (validar()) {
                establecerUbicacionIngreso();
                agregarServidorHelper.getMovimiento().setConJustificacion(Boolean.TRUE);
                tramiteServicio.guardarEdicionMovimiento(agregarServidorHelper.getMovimiento(),
                        agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento(),
                        agregarServidorHelper.getDetalleMovimientoVO());
                agregarServidorHelper.setPuesto(null);
                agregarServidorHelper.getListaPuestos().clear();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al actualizar el movimiento.", e);
        }
        return null;
    }

    /**
     * Este metodo realiza validacion extras.
     *
     * @return Boolean
     */
    private Boolean validar() throws DaoException {
        Boolean valido = Boolean.TRUE;
        StringBuilder error = new StringBuilder();
        Movimiento movimiento = agregarServidorHelper.getMovimiento();
        if (movimiento.getNumeroMemo() != null) {
            movimiento.setNumeroMemo(movimiento.getNumeroMemo().toUpperCase());
            movimiento.setNumeroDocumentoHabilitante(movimiento.getNumeroMemo().toUpperCase());
        }
        if (validarFechaRenucia(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.genericos.mensaje.validacion.fechas.renuncia", FacesMessage.SEVERITY_WARN);
        } else if (validarFechaRige(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("La fecha 'Rige a Partir De' debe ser menor que la fecha 'Hasta'", FacesMessage.SEVERITY_WARN);
        } else if (!validarFechaIngresoInstitucion(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("La fecha 'Ingreso Institución' debe ser igual a la fecha 'Rige a Partir De' para servidores nuevos Ó La fecha 'Ingreso Institución' debe ser menor o igual a la fecha 'Rige a Partir De' para servidores antiguos", FacesMessage.SEVERITY_WARN);
            // validar tiempo maximo permitido.
        } else if (!cumpleTiempoMaximoPermitido(movimiento.getTramite().getTipoMovimiento(), movimiento, agregarServidorHelper.getLicencia(), error)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla(error.toString(), FacesMessage.SEVERITY_WARN);
            // validar fechas de licencia de acuerdo al hecho.
        } else if (!validarFechasLicencia(movimiento.getTramite().getTipoMovimiento(), agregarServidorHelper.getLicencia(), error)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla(error.toString(), FacesMessage.SEVERITY_WARN);
            // validar duracion de contratos.
        } else if (!validarDuracionContratos(movimiento, error)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla(error.toString(), FacesMessage.SEVERITY_WARN);
            // valida horas mensuales por respresentacion en asociaciones laborales.
        } else if (!validarHorasMensualesPorRepresentacionAsociacion(movimiento.getTramite().getTipoMovimiento(), agregarServidorHelper.getLicencia(), error)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla(error.toString(), FacesMessage.SEVERITY_WARN);
        } else if (!validarPuestoARegresarVacanteParaNombramiento(movimiento.getTramite().getTipoMovimiento(), movimiento, agregarServidorHelper.getFinalizacion().getMovimientoInicial(), error)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla(error.toString(), FacesMessage.SEVERITY_WARN);
        } else if (!validarUnidadOrganizacionalEnCambioAdministrativo(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("Debe seleccionar la unidad organizacional.", FacesMessage.SEVERITY_WARN);
        } else if (!validarUnidadOrganizacionalEnTraspaso(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("Debe seleccionar la unidad organizacional.", FacesMessage.SEVERITY_WARN);
        } else if (!validarUnidadPresupuestariaEnTraspaso(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("Debe seleccionar la unidad presupuestaria.", FacesMessage.SEVERITY_WARN);
//        } else if (!validarFechaFinalMovimiento(movimiento)) {
//            valido = Boolean.FALSE;
//            mostrarMensajeEnPantalla("Fecha final del movimiento es superior a la fecha final del puesto.",
//                    FacesMessage.SEVERITY_WARN);
        } else if (!validarPorcentajeMulta(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("Porcentaje de la multa no puede ser superior al 10%", FacesMessage.SEVERITY_WARN);
        } else if (!validarTipoRotacion(movimiento)) {
            valido = Boolean.FALSE;
            mostrarMensajeEnPantalla("Tipo de movimiento le falta configurar el tipo de rotación.",
                    FacesMessage.SEVERITY_WARN);
        }
        return valido;
    }

    /**
     * Valida la configuracion correcta de los tipos de rotaciones.
     *
     * @param movimiento
     * @return
     */
    private Boolean validarTipoRotacion(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.ROTACIONES.
                getCodigo()) && movimiento.getTramite().getTipoMovimiento().getTipoRotacion() == null) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     * Valida el porcentaje de multa de regimen disciplinario.
     *
     * @param movimiento
     * @return
     */
    private Boolean validarPorcentajeMulta(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getTramite().getTipoMovimiento().getAreaRegimenDisciplinario()
                && agregarServidorHelper.getRegimenDisciplinario().getValorMulta() != null && agregarServidorHelper.
                getRegimenDisciplinario().getValorMulta().compareTo(new BigDecimal("10")) == 1) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     * Valida que la fecha final del movimiento no sea mayor a la fecha final del puesto.
     *
     * @param movimiento
     * @return
     */
    private Boolean validarFechaFinalMovimiento(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getDistributivoDetalle().getFechaFin() != null && movimiento.getFechaHasta() != null && movimiento.
                getFechaHasta().compareTo(movimiento.getDistributivoDetalle().getFechaFin()) == 1) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     *
     * @param movimiento
     * @return
     */
    private Boolean validarUnidadPresupuestariaEnTraspaso(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getTramite().getTipoMovimiento().getTipoRotacion() != null
                && movimiento.getTramite().getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.
                        getCodigo()) && agregarServidorHelper.getTraspaso().getUnidadPresupuestariaId() == null) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     *
     * @param movimiento
     * @return
     */
    private Boolean validarUnidadOrganizacionalEnTraspaso(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getTramite().getTipoMovimiento().getTipoRotacion() != null
                && movimiento.getTramite().getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.
                        getCodigo()) && agregarServidorHelper.getTraspaso().getUnidadOrganizacional() == null) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     *
     * @param movimiento
     * @return
     */
    private Boolean validarUnidadOrganizacionalEnCambioAdministrativo(final Movimiento movimiento) {
        Boolean r = Boolean.TRUE;
        if (movimiento.getTramite().getTipoMovimiento().getTipoRotacion() != null
                && movimiento.getTramite().getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.CAMBIO_ADMINISTRATIVO.
                        getCodigo()) && agregarServidorHelper.getCambioAdministrativo().getUnidadOrganizacional() == null) {
            r = Boolean.FALSE;
        }
        return r;
    }

    /**
     * Este metodo evalua la fecha apartir de y la fecha hasta.
     *
     * @param movimiento Movimiento
     * @return Boolean
     */
    private Boolean validarFechaRige(final Movimiento movimiento) {
        return movimiento.getRigeApartirDe() != null && movimiento.getFechaHasta() != null
                && movimiento.getRigeApartirDe().after(movimiento.getFechaHasta());
    }

    /**
     * Este metodo evalua la fecha ingreso institucion debe ser menor o igual a la fecha rige a partir de.
     *
     * @param movimiento Movimiento
     * @return Boolean
     */
    private Boolean validarFechaIngresoInstitucion(final Movimiento m) throws DaoException {
        Date fechaIngreso = agregarServidorHelper.getIngreso().getFechaIngresoInstitucion();
        if (m.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
            if (CamposConfiguracionEnum.OBLIGATORIO.getCodigo().equals(m.getTramite().getTipoMovimiento().getFechaIngresoInstitucion())) {
                List<ServidorInstitucion> servidores
                        = servidorInstitucionDao.buscarPorInstitucionServidor(m.getTramite().getInstitucionEjercicioFiscal().getInstitucion().getId(),
                                m.getTipoIdentificacion(), m.getNumeroIdentificacion());
                if (servidores.isEmpty()) {
                    return m.getRigeApartirDe() != null && fechaIngreso != null
                            && UtilFechas.truncarFecha(fechaIngreso).getTime() <= UtilFechas.truncarFecha(m.getRigeApartirDe()).getTime();
                } else {
                    return m.getRigeApartirDe() != null && fechaIngreso != null
                            && UtilFechas.truncarFecha(fechaIngreso).getTime() <= UtilFechas.truncarFecha(m.getRigeApartirDe()).getTime();
                }
            }

        }
        return true;
    }

    /**
     * Este metodo evalua la fecha de renuncia.
     *
     * @param movimiento Movimiento
     * @return Boolean
     */
    private Boolean validarFechaRenucia(final Movimiento movimiento) {
        return agregarServidorHelper.getCesacion().getFechaPresentaRenuncia() != null
                && agregarServidorHelper.getCesacion().getFechaAceptacionRenuncia() != null
                && agregarServidorHelper.getCesacion().getFechaPresentaRenuncia().after(
                        agregarServidorHelper.getCesacion().getFechaAceptacionRenuncia());
    }

    /**
     * Validar que el movimiento de contratos solo puede ser suscrito dentro del ejercicio fiscal vigente.
     *
     * @param m Movimiento
     * @param error StringBuilder
     * @return Boolean
     */
    public Boolean validarDuracionContratos(final Movimiento m, final StringBuilder error) {
        if (m.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
            return validacionServicio.validarDuracionContratos(m.getRigeApartirDe(), m.getFechaHasta(), m.
                    getDistributivoDetalle(), error, getSession().getServletContext());
        } else {
            return true;
        }
    }

    /**
     * Valida que la fecha de ocurriodo el hecho no se mayor a la fecha de presentacion de la documentacion.
     *
     * @param tm TipoMovimiento
     * @param l Licencia
     * @param error StringBuilder
     * @return Boolean
     */
    public Boolean validarFechasLicencia(final TipoMovimiento tm, final Licencia l, final StringBuilder error) {
        return validacionServicio.validarFechasLicencia(tm, l.getFechaOcurrioHecho(), l.getFechaPresentaDocumento(),
                error);
    }

    /**
     * Valida que el numero de horas mensuales por representacion en asociaciones laborales no se mayor al maximo
     * permitido en el tipo de movimiento.
     *
     * @param tm TipoMovimiento
     * @param l Licencia
     * @param error StringBuilder
     * @return Boolean
     */
    public Boolean validarHorasMensualesPorRepresentacionAsociacion(final TipoMovimiento tm, final Licencia l,
            final StringBuilder error) {
        return validacionServicio.validarHorasMensualesPorRepresentacionAsociacion(tm,
                l.getHorasMensualesRepresentacionLaboral(), error);
    }

    /**
     * Valida que el puesto anterior se encuentre vacante.
     *
     * @param tm
     * @param mInicial
     * @param error
     * @return
     */
    public Boolean validarPuestoARegresarVacanteParaNombramiento(final TipoMovimiento tm, final Movimiento m,
            final Movimiento mInicial, final StringBuilder error) {
        Boolean resultado = Boolean.TRUE;
//        if (tm.getAreaFinalizacion()) {
//            resultado = validacionServicio.validarPuestoARegresarVacanteParaNombramiento(tm, m.
//                    getOrganigramaPosicionalIndividualId(), mInicial.getOrganigramaPosicionalIndividualId(), error);
//        }
        return resultado;
    }

    /**
     * Valida que se cumpla el periodo permitir del tipo de movimiento.
     *
     * @param tm TipoMovimiento
     * @param m Movimiento
     * @param l Licencia
     * @param error StringBuilder
     * @return Boolean
     */
    private Boolean cumpleTiempoMaximoPermitido(final TipoMovimiento tm, final Movimiento m, final Licencia l,
            final StringBuilder error) {
        return validacionServicio.cumpleTiempoMaximoPermitido(tm.getId(), m.getRigeApartirDe(), m.getFechaHasta(), l.
                getTipoNacimiento(), error, getSession().getServletContext());
    }

    /**
     * Este metodo pasa los valores del selector de ubicacion geografica.
     */
    private void establecerUbicacionIngreso() throws ServicioException {
        if (agregarServidorHelper.getMovimiento().getDistributivoDetalle().getUbicacionGeografica().getId() != null) {
            UbicacionGeografica ug = admServicio.buscarUbicacionGeograficaId(agregarServidorHelper.getMovimiento().
                    getDistributivoDetalle().getUbicacionGeografica().getId());
            Ingreso ingreso = agregarServidorHelper.getDetalleMovimientoVO().getIngreso();
            ingreso.setParroquia(ug.getId());
            ingreso.setCanton(ug.getUbicacionGeografica().getId());
            ingreso.setProvincia(ug.getUbicacionGeografica().getUbicacionGeografica().getId());
        }
    }

    /**
     * Este método busca datos especificos para mostrar la descripcion o nombre.
     */
    private void iniciarDatos() {
        try {
            controlSeleccionTipoDocumento();
            // iniciar los datos para imprimir las situaciones de la accion de personal.
            if (agregarServidorHelper.getMovimiento().getSituacionActual() == null) {
                agregarServidorHelper.getMovimiento().setSituacionActual(
                        agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().getSituacionActual());
            }
            if (agregarServidorHelper.getMovimiento().getSituacionPropuesta() == null) {
                agregarServidorHelper.getMovimiento().setSituacionPropuesta(
                        agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().getSituacionPropuesta());
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar datos", e);
        }
    }

    /**
     *
     * @throws ServicioException
     */
    private void inicializaExplicacion() {
        try {
            // inicializar la justificacion
            if (!agregarServidorHelper.getMovimiento().getConJustificacion()) {
//                ParametroInstitucional articulo = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
//                        getIdInstitucion(), ParametroInstitucionCatalogoEnum.ARTICULO_REPRESENTANTE_LEGAL.getCodigo());
//                ParametroInstitucional institucion = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
//                        getIdInstitucion(), ParametroInstitucionCatalogoEnum.REPRESENTANTE_LEGAL.getCodigo());
//                String art = "";
//                if (articulo != null) {
//                    art = articulo.getValorTexto().toLowerCase();
//                    art = Character.toString(art.charAt(0)).toUpperCase() + art.substring(1);
//                }
//                String ins = "";
//                if (institucion != null) {
//                    ins = institucion.getValorTexto();
//                }
//                agregarServidorHelper.getMovimiento().setJustificacion(UtilCadena.concatenar(art, " ", ins, ", "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método control la selección del tipo de decumento.
     */
    public void controlSeleccionTipoDocumento() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot view = fc.getViewRoot();
        agregarServidorHelper.setTipoCedula(
                TipoDocumentoEnum.CEDULA.getNemonico().equals(
                        agregarServidorHelper.getMovimiento().getTipoIdentificacion()));
        for (String campo : camposTipoDocumento) {
            UIComponent componente = view.findComponent(ID_FORMULARIO.concat(campo));
            if (componente instanceof UIInput) {
                UIInput com = (UIInput) componente;
                com.getAttributes().put(PROPIEDAD, agregarServidorHelper.getTipoCedula());
            }
        }
    }

    /**
     * Este método busca un ciudadano por numero de cedula.
     *
     * @param event ActionEvent
     */
    public void buscarPorCedula(final ActionEvent event) {
        Movimiento movimiento = agregarServidorHelper.getMovimiento();
        String numeroIdentificacion = movimiento.getNumeroIdentificacion();
//        if (numeroIdentificacion != null && numeroIdentificacion.length() == TipoDocumentoEnum.CEDULA.getLonguitud()) {
//            String tipoIdentificacion = movimiento.getTipoIdentificacion();
//            limpiarIngreso(agregarServidorHelper.getIngreso(), movimiento);
//            movimiento.setNumeroIdentificacion(numeroIdentificacion);
//            movimiento.setTipoIdentificacion(tipoIdentificacion);
//            if (UtilNumeros.validadorDeCedula(numeroIdentificacion)) {
//                try {
//                    List<Impedido> impedidos = impedidoServicio.buscarImpedimentosPorEmpleado(numeroIdentificacion);
//                    if (impedidos == null || impedidos.isEmpty()) {
//                        busquedaPorCedula(numeroIdentificacion);
//                    } else {
//                        limpiarIngreso(agregarServidorHelper.getIngreso(), movimiento);
//                        mostrarMensajeEnPantalla("Existen impedimento para este servidor, revisar impedimentos.",
//                                FacesMessage.SEVERITY_WARN);
//                    }
//                } catch (DaoException ex) {
//                    Logger.getLogger(AgregarServidorControlador.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } else {
//
//                mostrarMensajeEnPantalla("El numero de identificación no cumple el dígito verificador",
//                        FacesMessage.SEVERITY_WARN);
//            }
//        } else {
////            limpiarIngreso(agregarServidorHelper.getIngreso(), movimiento);
//            mostrarMensajeEnPantalla("Longitud del numero de identificación no es valido",
//                    FacesMessage.SEVERITY_WARN);
//        }
        actualizarComponente(ID_FORMULARIO.concat("panelInformacionServido"));
    }

    /**
     * Este metodo procesa un servidor por cedula.
     *
     * @param numeroIdentificacion String
     */
    private void busquedaPorCedula(final String numeroIdentificacion) {
        Movimiento m = agregarServidorHelper.getMovimiento();
        Ingreso i = agregarServidorHelper.getIngreso();
//        RegistroCivilServicio registroCivilServicio = new RegistroCivilServicio();
//        try {
//            buscarServidorSithh(i, m, numeroIdentificacion);
//            try {
//                Persona persona = registroCivilServicio.obtenerPorCedula(numeroIdentificacion);
//                if (persona != null) {
//                    i.setFechaNacimiento(java.sql.Date.valueOf(persona.getFechaNacimiento()));
//                    i.setEdad(UtilFechas.calcularEdadEnAnios(i.getFechaNacimiento()));
//                    if (i.getEdad() < 18) {
//                        limpiarIngreso(agregarServidorHelper.getIngreso(), m);
//                        mostrarMensajeEnPantalla("La persona no es mayor de edad.", FacesMessage.SEVERITY_WARN);
//                    } else {
//                        m.setApellidosNombres(persona.getApellidos().concat(" ").concat(persona.getNombres()));
//                        m.setApellidos(persona.getApellidos());
//                        m.setNombres(persona.getNombres());
//                        controlEstadoCivil(persona.getEstadoCivil());
//                        m.setConyugue(persona.getConyugue());
//                    }
//                } else {
//                    throw new ServicioException("No existe datos en dato seguro");
//                }
//            } catch (Exception e) {
//                CiudadanoIntegracion c = crcServicio.buscarPorCedula(numeroIdentificacion);
//                if (c != null) {
//                    i.setFechaNacimiento(new SimpleDateFormat("yyyy/MM/dd").parse(c.getFechaNacimiento()));
//                    i.setEdad(UtilFechas.calcularEdadEnAnios(i.getFechaNacimiento()));
//                    if (i.getEdad() < 18) {
//                        limpiarIngreso(agregarServidorHelper.getIngreso(), m);
//                        mostrarMensajeEnPantalla("La persona no es mayor de edad.", FacesMessage.SEVERITY_WARN);
//                    } else {
//                        m.setApellidosNombres(c.getNombreCedulado());
//                        m.setApellidos(c.getNombreCedulado());
//                        m.setNombres(c.getNombreCedulado());
//                        controlEstadoCivil(ajusteEstadoCivil(c.getEstadoCivil()));
//                        m.setConyugue(c.getNombreConyuge());
//                    }
//                } else {
//                    throw new ServicioException("No existe datos en registro civil");
//                }
//            }
//        } catch (Exception ex) {
//            error(getClass().getName(),
//                    "En este momento no disponemos de información, vuelva a intentar más tarde.", ex);
//        }
    }

    private String ajusteEstadoCivil(final String estado) {
        if ("CASADO/A".equals(estado)) {
            return "CASADO";
        } else if ("SOLTERO/A".equals(estado)) {
            return "SOLTERO";
        } else if ("VIUDO/A".equals(estado)) {
            return "VIUDO";
        } else if ("DIVORCIADO/A".equals(estado)) {
            return "DIVORCIADO";
        }
        return "";
    }

    /**
     * Este metodo burra los datos del ingreso.
     *
     * @param i Ingreso
     * @param movimiento Movimiento
     */
    private void limpiarIngreso(final Ingreso i, final Movimiento movimiento) {
        movimiento.setApellidosNombres(null);
        movimiento.setApellidos(null);
        movimiento.setNombres(null);
        movimiento.setNumeroIdentificacion(null);
        i.setFechaNacimiento(null);
        i.setEdad(null);
        i.setEstadoCivil(null);
        i.setNacionalidadId(null);
        i.setAniosResidencia(null);
        i.setGenero(null);
        i.setIdentificacionEtnica(null);
        i.setProvincia(null);
        i.setCanton(null);
        i.setParroquia(null);
        i.setRegion(null);
        i.setPais(null);
        i.setServidorPasante(null);
        i.setServidorCarrera(null);
        i.setDomicilioCallePrincipal(null);
        i.setDomicilioCalleSecudaria(null);
        i.setDomicilioNumero(null);
        i.setDomicilioReferencia(null);
        i.setTelefonoDomicilio(null);
        i.setTelefonoTrabajo(null);
        i.setCorreoElectronico(null);
        i.setMotivoIngreso(null);
        i.setFechaIngresoInstitucion(null);
        i.setFechaIngresoSectorPublico(null);
        i.setNumeroCarrera(null);
        i.setNacionalidadId(null);
        iniciarCombos(busquedaPuestoHelper.getListaProvincia());
        iniciarCombos(busquedaPuestoHelper.getListaCanton());
        iniciarCombos(busquedaPuestoHelper.getListaParroquia());

    }

    /**
     * Este metodo busca el nombre del estado civil para seleccionar el id del genero.
     *
     * @param estado String
     */
    private void controlEstadoCivil(final String estado) {
        try {
            for (SelectItem ec : agregarServidorHelper.getEstadoCivil()) {
                if (estado.equals(ec.getLabel())) {
                    agregarServidorHelper.getIngreso().setEstadoCivil((Long) ec.getValue());
                    break;
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al controlar el estado civil.", e);
        }

    }

    /**
     * Este metodo controla que la activacion de la descapacidad.
     */
    public void controlDiscpacidad() {
        if (agregarServidorHelper.getIngreso().getDiscapacidad()) {
            agregarServidorHelper.setAreaDiscapacidad(Boolean.TRUE);
        } else {
            agregarServidorHelper.setAreaDiscapacidad(Boolean.FALSE);
        }
        agregarServidorHelper.getIngreso().setEspecifiqueDiscapacidad(null);
        agregarServidorHelper.getIngreso().setCorrespondeDiscapacidad(null);
        agregarServidorHelper.setAreaDetalleDiscapacidad(Boolean.FALSE);
        agregarServidorHelper.setAreaDetalleEnfermedadCatastrofica(Boolean.FALSE);
    }

    /**
     * Este método controla la visibilidad de los campo de discapacidad.
     */
    public void seleccionEspecifiqueDiscapacidad(final ValueChangeEvent evt) {
        if (agregarServidorHelper.getIngreso() != null) {
            String especifiqueDiscapacidad;
            if (evt == null) {
                especifiqueDiscapacidad = agregarServidorHelper.getIngreso().getEspecifiqueDiscapacidad();
            } else {
                especifiqueDiscapacidad = (String) evt.getNewValue();
            }
            if (agregarServidorHelper.getIngreso().getDiscapacidad() != null && agregarServidorHelper.getIngreso().
                    getDiscapacidad()) {
                agregarServidorHelper.setAreaDiscapacidad(Boolean.TRUE);
            } else {
                agregarServidorHelper.setAreaDiscapacidad(Boolean.FALSE);
            }
            agregarServidorHelper.setAreaDetalleDiscapacidad(Boolean.FALSE);
            agregarServidorHelper.setAreaDetalleEnfermedadCatastrofica(Boolean.FALSE);
            if (especifiqueDiscapacidad != null) {
                if (especifiqueDiscapacidad.equals(EspecifiqueDiscapacidadEnum.DISCAPACIDAD.getCodigo())) {
                    agregarServidorHelper.setAreaDetalleDiscapacidad(true);
                    agregarServidorHelper.setAreaDetalleEnfermedadCatastrofica(false);
                    agregarServidorHelper.getIngreso().setJustificacionDiscapacidad(null);
                } else if (especifiqueDiscapacidad.equals(
                        EspecifiqueDiscapacidadEnum.ENFERMEDAD_CATASTROFICA.getCodigo())) {
                    agregarServidorHelper.setAreaDetalleEnfermedadCatastrofica(true);
                    agregarServidorHelper.setAreaDetalleDiscapacidad(false);
                    agregarServidorHelper.getIngreso().setTipoDiscapacidad(null);
                    agregarServidorHelper.getIngreso().setPorcentajeDiscapacidad(null);
                    agregarServidorHelper.getIngreso().setNumeroConadis(null);

                }
            }
        }
    }

    /**
     * Este metodo inicia la opcion de imprimir situal de la accion persona.
     */
    private void iniciarImprimirAccionPersonal() {
        iniciarCombos(agregarServidorHelper.getImprimirAccionPersonal());
        agregarServidorHelper.getImprimirAccionPersonal().add(new SelectItem(Boolean.TRUE, "Imprimir"));
        agregarServidorHelper.getImprimirAccionPersonal().add(new SelectItem(Boolean.FALSE, "No Imprimir"));
    }

    /**
     * Este método añade un "/" a la cadena.
     *
     * @param nivel String
     * @return String
     */
    private String concantenarUbicacion(final String nivel) {
        String cadena = "";
        if (nivel != null && !nivel.isEmpty()) {
            cadena = cadena.concat(nivel).concat("/");
        }
        return cadena;
    }

    /**
     * Este metodo busca la region segun la cuidad seleccionada.
     */
    public void buscarRegiones() {
        iniciarCombos(getAgregarServidorHelper().getListaProvincia());
        iniciarCombos(getAgregarServidorHelper().getListaCanton());
        iniciarCombos(getAgregarServidorHelper().getListaParroquia());
//        llenarOpcionesCatalogo(getAgregarServidorHelper().getListaRegion(),
//                catalogoServicio.buscarRegiones(getAgregarServidorHelper().getIngreso().getPais()));
    }

    /**
     * Este método consulta las unidades administrativas.
     *
     * @return String
     */
    public String consultarUnidadOrganizacional() {
        try {
            buscarUnidadesOrganizacionales();
            ejecutarComandoPrimefaces("popUpUnidadOrganizacional.show()");
            actualizarComponente("frmUnidadAdministrativa:panelUnidadOrganizacional");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar unidades organizacionales.", e);
        }
        return null;
    }

    /**
     * Este método consulta las unidades administrativas por cambio administrativo.
     *
     * @return String
     */
    public String consultarUnidadOrganizacionalPorCambioAdminitrativo() {
        try {
            buscarUnidadesOrganizacionales();
            ejecutarComandoPrimefaces("popUpUnidadOrganizacionalCambio.show()");
            actualizarComponente("frmUnidadAdministrativa:panelUnidadOrganizacionalCambio");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar unidades organizacionales.", e);
        }
        return null;
    }

    /**
     * Este método consulta las unidades administrativas por traspaso.
     *
     * @return String
     */
    public String consultarUnidadOrganizacionalPorTraspaso() {
        try {
            buscarUnidadesOrganizacionales();
            ejecutarComandoPrimefaces("popUpUnidadOrganizacionalTraspaso.show()");
            actualizarComponente("frmUnidadAdministrativa:panelUnidadOrganizacionalTraspaso");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar unidades organizacionales.", e);
        }
        return null;
    }

    /**
     * Este método consulta las unidades administrativas.
     *
     * @return String
     */
    public String consultarUnidadAdministrativaCambio() {
        try {
            buscarUnidadesOrganizacionales();
            ejecutarComandoPrimefaces("popUpUnidadAdministrativaCambio.show()");
            actualizarComponente("frmpPopUpUnidadAdministrativaCambio");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los grupos ocupacionales.", e);
        }
        return null;
    }

    /**
     * Este método consulta las unidades administrativas.
     *
     * @return String
     */
    public String consularUnidadAdministrativaTraspaso() {
        try {
            buscarUnidadesOrganizacionales();
            ejecutarComandoPrimefaces("popUpUnidadAdministrativaTraspaso.show()");
            actualizarComponente("frmUnidadAdministrativaTraspaso");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los grupos ocupacionales.", e);
        }
        return null;
    }

    /**
     * Este método consultar los grupos ocupacionales.
     *
     * @return String
     */
    public String consultarGrupoOcupacional() {
        try {
//            getAgregarServidorHelper().getListaGrupoOcupacional().clear();
//            getAgregarServidorHelper().getListaGrupoOcupacional().addAll(
//                    grupoOcupacionalServicio.listarGrupoOcupacional(
//                    obtenerUsuarioLogeado().getInstitucion().getId()));
            ejecutarComandoPrimefaces("popUpGrupoOcupacional.show()");
            actualizarComponente("fmrGrupoOcupacional:panelGrupoOcupacional");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los grupos ocupacionales.", e);
        }
        return null;
    }

    /**
     * Este metodo llena la lista de Puesto Institución.
     *
     * @return String
     */
    public String consultarPuestoInstitucional() {
        try {
//            getAgregarServidorHelper().getListaPuestoInstitucion().clear();
//            getAgregarServidorHelper().getListaPuestoInstitucion().addAll(
//                    puestoServicio.listarPorInstitucion(obtenerUsuarioLogeado().getInstitucion()));
            ejecutarComandoPrimefaces("popUpPuestoInstitucional.show()");
            actualizarComponente("formPuestoInstitucional:panelInstitucion");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los puestos institucionales.", e);
        }
        return null;
    }

    /**
     * Este metodo busca los catalogos y los llena en las opciones.
     */
    private void consultarCatalogos() {
        try {
            /**
             * poblar modalidades laborales.
             */
            List<ModalidadLaboral> mls = admServicio.listarTodosModalidadLaboralPorNombre(null);
            iniciarCombos(agregarServidorHelper.getListaModalidadLaboral());
            for (ModalidadLaboral ml : mls) {
                agregarServidorHelper.getListaModalidadLaboral().add(new SelectItem(ml.getId(), ml.getNombre()));
            }
            /**
             * Poblar denominaciones de puesto.
             */
            List<DenominacionPuesto> denominaciones = admServicio.listarTodosDenominacionPuestoPorNombre(null);
            iniciarCombos(agregarServidorHelper.getListaDenominacionPuesto());
            for (DenominacionPuesto denominacion : denominaciones) {
                agregarServidorHelper.getListaDenominacionPuesto().add(new SelectItem(denominacion.getId(),
                        denominacion.getNombre()));
            }

            List<UnidadPresupuestaria> unidades = admServicio.listarTodosUnidadesPresupuestarias();
            iniciarCombos(agregarServidorHelper.getListaUnidadesPresupuestarios());
            for (UnidadPresupuestaria up : unidades) {
                agregarServidorHelper.getListaUnidadesPresupuestarios().add(new SelectItem(up.getId(), up.getNombre()));
            }
            catalogosEnums();
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar catalogo.", e);
        }
    }

    /**
     * Este método llena las listas de enumeraciones.
     */
    private void catalogosEnums() {
        iniciarCombos(getAgregarServidorHelper().getTipoDocumento());
        iniciarCombos(getAgregarServidorHelper().getEspecifiqueDiscapacidad());
        iniciarCombos(getAgregarServidorHelper().getCorrespondeDiscapacidad());
        iniciarCombos(getAgregarServidorHelper().getTipoTemporada());
        iniciarCombos(getAgregarServidorHelper().getTipoDesignacion());
        iniciarCombos(getAgregarServidorHelper().getCasosFallecimiento());
        iniciarCombos(getAgregarServidorHelper().getOpcionesFaltaRegimenDisciplinario());
        iniciarCombos(getAgregarServidorHelper().getOpcionesTipoNacimiento());
        iniciarCombos(getAgregarServidorHelper().getTiposDiscapacidad());
        iniciarCombos(getAgregarServidorHelper().getListaTipoPeriodos());

        getAgregarServidorHelper().getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(),
                TipoDocumentoEnum.CEDULA.getNombre()));
        getAgregarServidorHelper().getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.PASAPORTE.getNemonico(),
                TipoDocumentoEnum.PASAPORTE.getNombre()));

        for (EspecifiqueDiscapacidadEnum c : EspecifiqueDiscapacidadEnum.values()) {
            getAgregarServidorHelper().getEspecifiqueDiscapacidad().add(new SelectItem(c.getCodigo(), c.getNombre()));
        }
        for (CorrespondeDiscapacidadEnum c : CorrespondeDiscapacidadEnum.values()) {
            getAgregarServidorHelper().getCorrespondeDiscapacidad().add(new SelectItem(c.getCodigo(), c.getNombre()));
        }
        for (TipoTemporadaEnum t : TipoTemporadaEnum.values()) {
            getAgregarServidorHelper().getTipoTemporada().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
        for (TipoDesignacionEnum t : TipoDesignacionEnum.values()) {
            getAgregarServidorHelper().getTipoDesignacion().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
        for (CasoFallecimientoEnum c : CasoFallecimientoEnum.values()) {
            getAgregarServidorHelper().getCasosFallecimiento().add(new SelectItem(c.getCodigo(), c.getNombre()));
        }
        for (FaltasRegimenDisciplinarioEnum frd : FaltasRegimenDisciplinarioEnum.values()) {
            getAgregarServidorHelper().getOpcionesFaltaRegimenDisciplinario().add(
                    new SelectItem(frd.getCodigo(), frd.getDescripcion()));
        }
        for (TipoNacimientoEnum tn : TipoNacimientoEnum.values()) {
            getAgregarServidorHelper().getOpcionesTipoNacimiento().add(new SelectItem(tn.getCodigo(),
                    tn.getDescripcion()));
        }
        for (TipoDiscapacidadEnum c : TipoDiscapacidadEnum.values()) {
            getAgregarServidorHelper().getTiposDiscapacidad().add(new SelectItem(c.getCodigo(), c.getDescripcion()));
        }
        for (TipoPeriodoMovimientoEnum c : TipoPeriodoMovimientoEnum.values()) {
            getAgregarServidorHelper().getListaTipoPeriodos().add(new SelectItem(c.getCodigo(), c.getDescripcion()));
        }

    }

    /**
     * Este método toma la configuración del tipo movimiento y configura el formulario.
     */
    private void configurarFormulario() {
        try {
            List<TipoMovimiento> tiposMovimentos = (List<TipoMovimiento>) getSession().getServletContext().
                    getAttribute(CacheEnum.TIPO_MOVIMIENTO.getCodigo());
            if (tiposMovimentos == null) {
                tiposMovimentos = admServicio.listarTiposMovimientos(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
            }
            TipoMovimiento tm = new TipoMovimiento();
            for (TipoMovimiento t : tiposMovimentos) {
                if (t.getId().equals(agregarServidorHelper.getMovimiento().
                        getTramite().getTipoMovimiento().getId())) {
                    tm = t;
                    break;
                }
            }
            consultarMovimientoDetalle();
            //TODO anidar los ingresos, catadrales etc, modificando el pojo. Para setear segun el movimiento
            FacesContext fc = FacesContext.getCurrentInstance();
            UIViewRoot view = fc.getViewRoot();
            List<String> campos = (List<String>) getSession().getServletContext().
                    getAttribute(BaseControlador.CAMPOS_TIP_MOV);
            for (String campo : campos) {
                UIComponent componente = view.findComponent(ID_FORMULARIO.concat(campo));
                if (componente instanceof UIInput) {
                    UIComponent btnSearch = view.findComponent(ID_FORMULARIO.concat(campo).concat("_search"));
                    UIComponent btnTrash = view.findComponent(ID_FORMULARIO.concat(campo).concat("_trash"));
                    Class forName = tm.getClass();
                    Field field = forName.getDeclaredField(campo);
                    if (field != null) {
                        field.setAccessible(true);
                        Object get = field.get(tm);
                        if (get instanceof String) {
                            Boolean disabled = get.toString().equals(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo());

                            if ((btnSearch instanceof HtmlCommandButton)
                                    && (btnTrash instanceof HtmlCommandButton && disabled)) {
                                CommandButton coms = (CommandButton) btnSearch;
                                coms.getAttributes().put(PROPIEDAD, disabled);
                                CommandButton comt = (CommandButton) btnTrash;
                                comt.getAttributes().put(PROPIEDAD, disabled);
                            }
                            boolean obligatorio = get.toString().equals(
                                    CamposConfiguracionEnum.OBLIGATORIO.getCodigo());
                            UIInput com = (UIInput) componente;
                            com.setRequired(obligatorio);
                            com.getAttributes().put(PROPIEDAD, disabled);

                            UIComponent componenteLbl = view.findComponent(
                                    ID_FORMULARIO.concat(campo + "_lbl"));
                            if (componenteLbl != null && obligatorio) {
                                HtmlOutputLabel label = (HtmlOutputLabel) componenteLbl;
                                label.setValue("* " + label.getValue());

                            }
                        }
                    }
                } else if (componente instanceof UIPanel) {
                    Class forName = tm.getClass();
                    Field field = forName.getDeclaredField(campo);
                    if (field != null) {
                        field.setAccessible(true);
                        Object get = field.get(tm);
                        UIPanel com = (UIPanel) componente;
                        com.setRendered(get instanceof Boolean && Boolean.valueOf(get.toString()));
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error en la configuracion del formulario.", e);
        }
    }

    /**
     * Este metodo configura el formulari como read only.
     */
    private void configurarFormularioReanOnly() {
        try {
            List<TipoMovimiento> tiposMovimentos = (List<TipoMovimiento>) getSession().getServletContext().
                    getAttribute(CacheEnum.TIPO_MOVIMIENTO.getCodigo());
            TipoMovimiento tm = new TipoMovimiento();
            for (TipoMovimiento t : tiposMovimentos) {
                if (t.getId().equals(agregarServidorHelper.getMovimiento().
                        getTramite().getTipoMovimiento().getId())) {
                    tm = t;
                    break;
                }
            }
            consultarMovimientoDetalle();
            //TODO anidar los ingresos, catadrales etc, modificando el pojo. Para setear segun el movimiento
            FacesContext fc = FacesContext.getCurrentInstance();
            UIViewRoot view = fc.getViewRoot();
            List<String> campos = (List<String>) getSession().getServletContext().
                    getAttribute(BaseControlador.CAMPOS_TIP_MOV);
            for (String campo : campos) {
                UIComponent componente = view.findComponent(ID_FORMULARIO.concat(campo));
//                if (componente instanceof UIInput) {
//                    UIComponent btnSearch = view.findComponent(ID_FORMULARIO.concat(campo).concat("_search"));
//                    UIComponent btnTrash = view.findComponent(ID_FORMULARIO.concat(campo).concat("_trash"));
//                    if (btnSearch != null && btnTrash != null) {
//                        CommandButton coms = (CommandButton) btnSearch;
//                        coms.getAttributes().put(PROPIEDAD, true);
//                        CommandButton comt = (CommandButton) btnTrash;
//                        comt.getAttributes().put(PROPIEDAD, true);
//                    } else {
//                        UIInput com = (UIInput) componente;
//                        com.getAttributes().put(PROPIEDAD, true);
//                    }
//
//                } else
                if (componente instanceof UIPanel) {
                    Class forName = tm.getClass();
                    Field field = forName.getDeclaredField(campo);
                    if (field != null) {
                        field.setAccessible(true);
                        Object get = field.get(tm);
                        UIPanel com = (UIPanel) componente;
                        com.setRendered(get instanceof Boolean && Boolean.valueOf(get.toString()));
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error en la configuracion del formulario.", e);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboCasoFallecimiento() {
        iniciarCombos(agregarServidorHelper.getTipoFallecimiento());
        for (CasoFallecimientoEnum cf : CasoFallecimientoEnum.values()) {
            agregarServidorHelper.getTipoFallecimiento().add(new SelectItem(cf.getCodigo(), cf.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de documento previo de la acion de persona.
     */
    private void iniciarComboDocumentoPrevio() {
        iniciarCombos(agregarServidorHelper.getListaDocumentoPrevio());
        for (DocumentoPrevioEnum d : DocumentoPrevioEnum.values()) {
            agregarServidorHelper.getListaDocumentoPrevio().add(new SelectItem(d.getCodigo(), d.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de nivel de estudio para hijo.
     */
    private void iniciarComboNivelEstudioHijo() {
        iniciarCombos(agregarServidorHelper.getListaNivelEstudioHijo());
        for (NivelEstudioHijoEnLicenciaEnum cf : NivelEstudioHijoEnLicenciaEnum.values()) {
            agregarServidorHelper.getListaNivelEstudioHijo().add(new SelectItem(cf.getCodigo(), cf.getNombre()));
        }
    }

    /**
     * Inicializa los detalles del movimiento.
     */
    private void inicializarMovimientoDetalles() {
        agregarServidorHelper.setIngreso(new Ingreso());
        agregarServidorHelper.setCesacion(new Cesacion());
        agregarServidorHelper.setRegimenDisciplinario(new RegimenDisciplinario());
        agregarServidorHelper.setLicencia(new Licencia());
        agregarServidorHelper.setComisionServicio(new ComisionServicio());
        agregarServidorHelper.setCambioAdministrativo(new CambioAdministrativo());
        agregarServidorHelper.setTrasladoAdministrativo(new TrasladoAdministrativo());
        agregarServidorHelper.setTraspaso(new Traspaso());
    }

    /**
     * Este método consulta el tipo de movimiento y consulta su detalle.
     */
    private void consultarMovimientoDetalle() {
        try {
            inicializarMovimientoDetalles();
            TipoMovimiento tipoMovimiento = getAgregarServidorHelper().getMovimiento().getTramite().
                    getTipoMovimiento();
            iniciarDatosEntidad(tipoMovimiento, Boolean.FALSE);
            Grupo grupo = tipoMovimiento.getClase().getGrupo();
            limpiarIngreso(agregarServidorHelper.getIngreso(), agregarServidorHelper.getMovimiento());
//            System.out.println("consultarMovimientoDetalle.1:" + getAgregarServidorHelper().getMovimiento().getId());
            Ingreso ingreso = tramiteServicio.buscaIngresoPorMovimiento(
                    getAgregarServidorHelper().getMovimiento().getId());
            if (ingreso.getRenovacionContrato() == null) {
                ingreso.setRenovacionContrato(Boolean.FALSE);
            }
            iniciarDatosEntidad(ingreso, Boolean.FALSE);
            agregarServidorHelper.setIngreso(ingreso);
            if (grupo.getNemonico().equals(GrupoEnum.SALIDAS.getCodigo())) {
                Cesacion cesacion = tramiteServicio.buscarCesacionPorMovimiento(
                        getAgregarServidorHelper().getMovimiento().getId());
                iniciarDatosEntidad(cesacion, Boolean.FALSE);
                agregarServidorHelper.setCesacion(cesacion);
            } else if (grupo.getNemonico().equals(GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo())) {
                RegimenDisciplinario rd = tramiteServicio.buscarRegimenDisciplinarioPorMovimiento(
                        getAgregarServidorHelper().getMovimiento().getId());
                iniciarDatosEntidad(rd, Boolean.FALSE);
                agregarServidorHelper.setRegimenDisciplinario(rd);
            } else if (grupo.getNemonico().equals(GrupoEnum.ROTACIONES.getCodigo())) {
                if (tipoMovimiento.getTipoRotacion().equals(TipoRotacionEnum.CAMBIO_ADMINISTRATIVO.getCodigo())) {
                    CambioAdministrativo ca = tramiteServicio.buscarCambioAdministrativoPorMovimiento(
                            getAgregarServidorHelper().getMovimiento().getId());
                    iniciarDatosEntidad(ca, Boolean.FALSE);
                    agregarServidorHelper.setCambioAdministrativo(ca);
                } else if (tipoMovimiento.getTipoRotacion().equals(TipoRotacionEnum.TRASLADO_ADMINISTRATIVO.getCodigo())) {
                    TrasladoAdministrativo ta = tramiteServicio.buscarTrasladoAdministrativoPorMovimiento(
                            getAgregarServidorHelper().getMovimiento().getId());
                    iniciarDatosEntidad(ta, Boolean.FALSE);
                    agregarServidorHelper.setTrasladoAdministrativo(ta);
                } else if (tipoMovimiento.getTipoRotacion().equals(
                        TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.getCodigo())) {
                    Traspaso t = tramiteServicio.buscarTraspasoPorMovimiento(
                            getAgregarServidorHelper().getMovimiento().getId());
                    iniciarDatosEntidad(t, Boolean.FALSE);
                    agregarServidorHelper.setTraspaso(t);
                }
            } else if (grupo.getNemonico().equals(GrupoEnum.ABSENTISMO.getCodigo())) {
                Licencia l = tramiteServicio.buscarLicenciaPorMovimiento(
                        getAgregarServidorHelper().getMovimiento().getId());
                iniciarDatosEntidad(l, Boolean.FALSE);
                agregarServidorHelper.setLicencia(l);
                ComisionServicio cs = tramiteServicio.buscarComisionServicioPorMovimiento(
                        getAgregarServidorHelper().getMovimiento().getId());
                iniciarDatosEntidad(cs, Boolean.FALSE);
                agregarServidorHelper.setComisionServicio(cs);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar el detalle del movimiento", e);
        }
    }

    /**
     * Este metodo busca traslados del movimiento segun en numero de partidas individual.
     *
     * @return String
     */
    public String buscarTrasladosAdministrativos() {
        try {
//            Movimiento movimiento = agregarServidorHelper.getMovimiento();
//            SrhvOrganicoPosicionalIndividual sopi = new SrhvOrganicoPosicionalIndividual();
//            sopi.setPartidaIndividual(agregarServidorHelper.getNumeroPartidaIndividual());
//            sopi.setInstitucionId(obtenerUsuarioLogeado().getInstitucionId());
//            sopi.setEstadoPuestoId(movimiento.getTramite().getTipoMovimiento().getEstadoPuestoInicialPropuestaCore());
//            sopi.setModalidadLaboralId(movimiento.getTramite().getTipoMovimiento().getModalidadLaboralCore());
//            sopi.setSueldo(movimiento.getRmu());
//            agregarServidorHelper.getListaSopi().clear();
//            agregarServidorHelper.setListaSopi(puestoServicioSrh.buscarPuestoporFiltros(sopi));
//            if (agregarServidorHelper.getListaSopi().isEmpty()) {
//                mostrarMensajeEnPantalla("No se han encontrado resultados.", FacesMessage.SEVERITY_WARN);
//            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar traslados administrativos.", e);
        }
        return null;
    }

    /**
     * Este metodo busca puesto propuesto segun en numero de partidas individual.
     *
     * @return String
     */
    public String buscarPuestoPropuesto() {
        try {
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscarPorCodigoPuesto(agregarServidorHelper.
                    getCodigoPuesto(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            if (puestos.isEmpty()) {
                mostrarMensajeEnPantalla(UtilCadena.concatenar("No existen puestos con el código ingresado"),
                        FacesMessage.SEVERITY_ERROR);
            } else {
                DistributivoDetalle puesto = puestos.get(0);
                if (agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().getEstadoPuestoInicialPropuestaCoreNombre() != null
                        && !puesto.getEstadoPuesto().getNombre().equals(agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().getEstadoPuestoInicialPropuestaCoreNombre())) {
                    mostrarMensajeEnPantalla(UtilCadena.concatenar("El puesto seleccionado debe tener el estado '",
                            agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().
                            getEstadoPuestoInicialPropuestaCoreNombre(), "', actualmente su estado es '", puesto.
                            getEstadoPuesto().getNombre(), "'"), FacesMessage.SEVERITY_ERROR);

                } else if (agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().
                        getModalidadLaboralCoreNombre() != null && !agregarServidorHelper.getMovimiento().getTramite().
                        getTipoMovimiento().getModalidadLaboralCoreNombre().trim().isEmpty()) {
                    if (!puesto.getDistributivo().getModalidadLaboral().getNombre().equals(agregarServidorHelper.
                            getMovimiento().getTramite().getTipoMovimiento().getModalidadLaboralCoreNombre())) {
                        mostrarMensajeEnPantalla(UtilCadena.concatenar(
                                "El puesto seleccionado debe tener la modalidad laboral '",
                                agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().
                                getModalidadLaboralCoreNombre(), "', actualmente su modalidad laboral es '", puesto.
                                getDistributivo().getModalidadLaboral().getNombre(), "'"), FacesMessage.SEVERITY_ERROR);
                    } else {
                        agregarServidorHelper.setListaPuestos(puestos);
                    }
                } else {
                    agregarServidorHelper.setListaPuestos(puestos);
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar puestos propuestos.", e);
        }
        return null;
    }

    /**
     * Este metodo busca los movimientos que se deben finalizar.
     *
     * @return String
     */
    public String buscarMovimientosAFinalizar() {
        try {
            Movimiento m = agregarServidorHelper.getMovimiento();
            List<Movimiento> movimientos = movimientoServicio.buscarMovimientosParaFinalizacion(
                    m.getTipoIdentificacion(),
                    m.getNumeroIdentificacion(), m.getTramite().getTipoMovimiento().getId());
            agregarServidorHelper.setMovimientosAFinalizar(movimientos);
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar movimientos a finalizar.", e);
        }
        return null;
    }

    /**
     * @return the busquedaPuestoHelper
     */
    public BusquedaPuestoHelper getBusquedaPuestoHelper() {
        return busquedaPuestoHelper;
    }

    /**
     * @param busquedaPuestoHelper the busquedaPuestoHelper to set
     */
    public void setBusquedaPuestoHelper(final BusquedaPuestoHelper busquedaPuestoHelper) {
        this.busquedaPuestoHelper = busquedaPuestoHelper;
    }

    /**
     * @return the agregarServidorHelper
     */
    public AgregarServidorHelper getAgregarServidorHelper() {
        return agregarServidorHelper;
    }

    /**
     * @param agregarServidorHelper the agregarServidorHelper to set
     */
    public void setAgregarServidorHelper(final AgregarServidorHelper agregarServidorHelper) {
        this.agregarServidorHelper = agregarServidorHelper;
    }

    /**
     * Indica si se muestra la fechas del contrato que se encuentra en el puesto.
     *
     * @return
     */
    public Boolean mostrarFechaDeContratos() {
        Boolean resultado = Boolean.FALSE;
        if (agregarServidorHelper.getMovimiento().getFechaInicio() != null && agregarServidorHelper.getMovimiento().
                getFechaFin() != null) {
            resultado = Boolean.TRUE;
        }
        return resultado;

    }

    /**
     * ************************************************************************************************
     */
    /**
     * Este metodo consulta los paises.
     *
     * @return String
     */
    public String consultarPaises() {
        try {
            /**
             * poblar los paises.
             */
            List<UbicacionGeografica> paises = admServicio.listarTodosPaisesUbicacionGeografica();
            iniciarCombos(getAgregarServidorHelper().getListaPais());
            for (UbicacionGeografica pais : paises) {
                agregarServidorHelper.getListaPais().add(new SelectItem(pais.getId(), pais.getNombre()));
            }
            iniciarCombos(agregarServidorHelper.getListaProvincia());
            iniciarCombos(agregarServidorHelper.getListaCanton());
            iniciarCombos(agregarServidorHelper.getListaParroquia());
            agregarServidorHelper.setProvinciaId(null);
            agregarServidorHelper.setCantonId(null);
            agregarServidorHelper.setParroquiaId(null);
            ejecutarComandoPrimefaces("popUpUbicacionGeografica.show()");
            actualizarComponente("frmUbicacionGeografico:panelUbicacionGeografica");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar paises.", e);
        }
        return null;
    }

    /**
     * Este metodo busca las provincias segun la region seleccionada.
     */
    public void buscarProvincias() {
        try {
            iniciarCombos(agregarServidorHelper.getListaProvincia());
            iniciarCombos(agregarServidorHelper.getListaCanton());
            iniciarCombos(agregarServidorHelper.getListaParroquia());
            List<UbicacionGeografica> provincias = admServicio.listarTodosIdUbicacionGeografica(
                    agregarServidorHelper.getPaisId());
            for (UbicacionGeografica provincia : provincias) {
                agregarServidorHelper.getListaProvincia().add(new SelectItem(provincia.getId(), provincia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar provincia.", e);
        }
    }

    /**
     * Este metodo busca los cantones segun la provincias seleccionada.
     */
    public void buscarCantones() {
        try {
            iniciarCombos(agregarServidorHelper.getListaCanton());
            iniciarCombos(agregarServidorHelper.getListaParroquia());
            List<UbicacionGeografica> cantones = admServicio.listarTodosIdUbicacionGeografica(
                    agregarServidorHelper.getProvinciaId());
            for (UbicacionGeografica canton : cantones) {
                agregarServidorHelper.getListaCanton().add(new SelectItem(canton.getId(), canton.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar cantones.", e);
        }
    }

    /**
     * Este metodo busca las parroquias segun el canton seleccionada.
     */
    public void buscarParroquias() {
        try {
            iniciarCombos(agregarServidorHelper.getListaParroquia());
            List<UbicacionGeografica> parroquias = admServicio.listarTodosIdUbicacionGeografica(
                    agregarServidorHelper.getCantonId());
            for (UbicacionGeografica parroquia : parroquias) {
                agregarServidorHelper.getListaParroquia().add(new SelectItem(parroquia.getId(), parroquia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar parroquias.", e);
        }
    }

    /**
     * Este método consulta los regimenes laborales.
     *
     * @return String
     */
    public String consultarRegimenLaboral() {
        try {
            /**
             * poblar los regimenes personales de la institucion.
             */
            List<RegimenLaboral> rls = regimenLaboralServicio.listarTodosRegimenVigentes();
            iniciarCombos(getAgregarServidorHelper().getListaRegimenLaboral());
            for (RegimenLaboral rl : rls) {
                agregarServidorHelper.getListaRegimenLaboral().add(new SelectItem(rl.getId(), rl.getNombre()));
            }
            iniciarCombos(agregarServidorHelper.getListaNivelOcupacional());
            iniciarCombos(agregarServidorHelper.getListaEscalaOcupacional());
            agregarServidorHelper.setNivelOcupacionalId(null);
            agregarServidorHelper.setEscalaOcupacionalId(null);
            ejecutarComandoPrimefaces("popUpRegimenLaboral.show()");
            actualizarComponente("frmRegimenLaboral:panelRegimenLaboral");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar regimen laboral.", e);
        }
        return null;
    }

    /**
     * Buscar niveles ocupacionales.
     */
    public void buscarNivelOcupacional() {
        try {
            List<NivelOcupacional> lista
                    = regimenLaboralServicio.listarTodosNivelOcupacionalPorRegimen(agregarServidorHelper.
                            getRegimenLaboralId());
            iniciarCombos(agregarServidorHelper.getListaNivelOcupacional());
            for (NivelOcupacional no : lista) {
                agregarServidorHelper.getListaNivelOcupacional().add(new SelectItem(no.getId(), no.getNombre()));
            }
            iniciarCombos(agregarServidorHelper.getListaEscalaOcupacional());
            agregarServidorHelper.setEscalaOcupacionalId(null);

        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar niveles ocupacionales.", e);
        }
    }

    /**
     * Buscar escalas ocupacionales.
     */
    public void buscarEscalaOcupacional() {
        try {
            List<EscalaOcupacional> lista
                    = regimenLaboralServicio.listarTodosEscalaOcupacionalPorNivelOcup(agregarServidorHelper.
                            getNivelOcupacionalId());
            iniciarCombos(agregarServidorHelper.getListaEscalaOcupacional());
            for (EscalaOcupacional eo : lista) {
                agregarServidorHelper.getListaEscalaOcupacional().add(new SelectItem(eo.getId(), eo.getNombre() + " - " + eo.getRmu()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar escalas ocupacionales.", e);
        }
    }

    /**
     * Buscar escalas ocupacionales.
     */
    public void seleccionarEscalaOcupacional() {
        try {
            if (agregarServidorHelper.getEscalaOcupacionalId() != null) {
                EscalaOcupacional escalaOcupacional = regimenLaboralServicio.buscarEscalaOcupacional(agregarServidorHelper.getEscalaOcupacionalId());
                Movimiento m = agregarServidorHelper.getMovimiento();
                m.setEscalaOcupacional(escalaOcupacional);
                if (m.getRmuSobrevalorado() == null) {
                    m.setRmu(escalaOcupacional.getRmu());
                } else {
                    if (escalaOcupacional.getRmu().compareTo(m.getRmuSobrevalorado()) >= 0) {
                        m.setRmuSobrevalorado(BigDecimal.ZERO);
                    }
                    m.setRmu(escalaOcupacional.getRmu());
                }
                agregarServidorHelper.setRegimenLaboralId(null);
                agregarServidorHelper.setNivelOcupacionalId(null);
                agregarServidorHelper.setEscalaOcupacionalId(null);

            }
            ejecutarComandoPrimefaces("popUpRegimenLaboral.hide()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar la escala ocupacional.", e);
        }
    }

    /**
     * Este método arma el ulr de ubicacion geografica.
     *
     */
    public void seleccionarUbicacionGeografica() {
        try {
            if (agregarServidorHelper.getParroquiaId() != null) {
                UbicacionGeografica ubicacionGeografica = admServicio.buscarUbicacionGeograficaId(agregarServidorHelper.
                        getParroquiaId());
                agregarServidorHelper.getMovimiento().getDistributivoDetalle().setUbicacionGeografica(
                        ubicacionGeografica);
                agregarServidorHelper.setPaisId(null);
                agregarServidorHelper.setProvinciaId(null);
                agregarServidorHelper.setCantonId(null);
                agregarServidorHelper.setParroquiaId(null);
            }
            ejecutarComandoPrimefaces("popUpUbicacionGeografica.hide()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar la ubicación geografica.", e);
        }
    }

    /**
     * Permite buscar a una persona en la tabla de reclutamientos.
     *
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void buscarPersona() throws DaoException {
        agregarServidorHelper.setRegistroManualNombres(Boolean.FALSE);
        if (agregarServidorHelper.getMovimiento().getTipoIdentificacion() != null
                && agregarServidorHelper.getMovimiento().getNumeroIdentificacion() != null) {
            if (agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento() != null
                    && agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().
                    getIngresoPorReclutamiento() != null
                    && agregarServidorHelper.getMovimiento().getTramite().getTipoMovimiento().
                    getIngresoPorReclutamiento()) {
                buscarPersonaEnReclutamiento(agregarServidorHelper.getMovimiento().getTipoIdentificacion(),
                        agregarServidorHelper.getMovimiento().getNumeroIdentificacion());
            } else {
                buscarPersonaEnWebServicePersonas(agregarServidorHelper.getMovimiento().getTipoIdentificacion(),
                        agregarServidorHelper.getMovimiento().getNumeroIdentificacion());
            }
            // verificar si el servidor ya existe en la institucion anteriormente.
            List<ServidorInstitucion> servidores
                    = servidorInstitucionDao.buscarPorInstitucionServidor(
                            agregarServidorHelper.getMovimiento().getTramite().getInstitucionEjercicioFiscal().
                            getInstitucion().getId(),
                            agregarServidorHelper.getMovimiento().getTipoIdentificacion(), agregarServidorHelper.
                            getMovimiento().getNumeroIdentificacion());
            if (servidores.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage("estadoServidorId",
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Servidor Nuevo", "El servidor registrado es nuevo en la institución"));
            } else {
                ServidorInstitucion si = servidores.get(0);
                if (si.getFechaSalida() == null) {
                    FacesContext.getCurrentInstance().addMessage("estadoServidorId",
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Servidor Labora Actualmente", "El servidor labora actualmente en la institución, "
                                    + "el cual tiene la siguiente fecha de ingreso:"
                                    + UtilFechas.formatear(si.getFechaIngreso())));
                } else {
                    agregarServidorHelper.getIngreso().setFechaIngresoInstitucion(si.getFechaIngreso());
                    agregarServidorHelper.getIngreso().setFechaIngresoSectorPublico(si.getServidor().
                            getFechaIngresoSectorPublico());
                    FacesContext.getCurrentInstance().addMessage("estadoServidorId",
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Servidor Laboro Anteriormente", "El servidor ya laboro anteriormente en la "
                                    + " institución, el cual tiene la siguiente fecha de salida:"
                                    + UtilFechas.formatear(si.getFechaSalida())));
                }
            }
            actualizarComponente("areaServidor");
        } else {
            mostrarMensajeEnPantalla("Para buscar la persona debe ingresar el tipo y número de identificación.",
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Permite buscar una persona en el Web Service de Personas.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     */
    public void buscarPersonaEnWebServicePersonas(final String tipoIdentificacion, final String numeroIdentificacion) {
        try {
            ec.com.atikasoft.proteus.vo.PersonaVO p = servidorServicio.buscarPersona(tipoIdentificacion,
                    numeroIdentificacion, obtenerUsuarioConectado());
            if (p != null) {
                Servidor s = servidorDao.buscar(tipoIdentificacion, numeroIdentificacion);
                if (s == null) {
                    agregarServidorHelper.getMovimiento().setApellidosNombres(p.getApellidosNombres() == null ? ""
                            : p.getApellidosNombres());
                } else {
                    agregarServidorHelper.getMovimiento().setApellidos(s.getApellidos());
                    agregarServidorHelper.getMovimiento().setNombres(s.getNombres());
                    agregarServidorHelper.getMovimiento().setApellidosNombres(s.getApellidosNombres());
                    if (s.getCatalogoGenero() != null) {
                        agregarServidorHelper.getMovimiento().setGenero(s.getCatalogoGenero().getCodigo());
                    }
                }
            } else {
                if (tipoIdentificacion.equals(TipoIdentificacionEnum.CEDULA.getCodigo())) {
                    agregarServidorHelper.getMovimiento().setApellidos("");
                    agregarServidorHelper.getMovimiento().setNombres("");
                    agregarServidorHelper.getMovimiento().setGenero(null);
                    agregarServidorHelper.getMovimiento().setApellidosNombres("");
                    mostrarMensajeEnPantalla("Identificación no se encuentra registrada en el sistema de personas.",
                            FacesMessage.SEVERITY_ERROR);
                } else {
                    agregarServidorHelper.getMovimiento().setApellidos("");
                    agregarServidorHelper.getMovimiento().setNombres("");
                    agregarServidorHelper.getMovimiento().setGenero(null);
                    agregarServidorHelper.getMovimiento().setApellidosNombres("");
                    mostrarMensajeEnPantalla("El número de pasaporte ingresado no existe en el Sistema de "
                            + " Personas, proceda a ingresar el Apellido y Nombre"
                            + " asegurándose que sean correctos.", FacesMessage.SEVERITY_WARN);
                    agregarServidorHelper.setRegistroManualNombres(Boolean.TRUE);

                }
            }
        } catch (DaoException e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar en web service persona.", e);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar en web service persona.", e);
        }
    }

    /**
     * Permite buscar a la persona en la tabla de reclutamiento por el número y tipo de identificación
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     */
    public void buscarPersonaEnReclutamiento(final String tipoIdentificacion, final String numeroIdentificacion) {
        try {
            if (agregarServidorHelper.getMovimiento().getDistributivoDetalle().getDistributivo().getModalidadLaboral()
                    != null
                    && agregarServidorHelper.getMovimiento().getDistributivoDetalle().getEscalaOcupacional() != null
                    && agregarServidorHelper.getMovimiento().getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional() != null
                    && agregarServidorHelper.getMovimiento().getDistributivoDetalle().getDistributivo().
                    getInstitucionEjercicioFiscal() != null) {
                agregarServidorHelper.getMovimiento().setApellidos("");
                agregarServidorHelper.getMovimiento().setNombres("");
                agregarServidorHelper.getMovimiento().setApellidosNombres("");
                agregarServidorHelper.getMovimiento().setGenero(null);
                Reclutamiento r = reclutamientoServicio.buscarPorDistributivoYEstado(
                        agregarServidorHelper.getMovimiento().
                        getDistributivoDetalleId(), EstadoReclutamientoEnum.REGISTRADO.getCodigo());
                if (r != null) {
                    if (r.getTipoIdentificacion().trim().equals(agregarServidorHelper.getMovimiento().
                            getTipoIdentificacion().trim()) && r.
                            getNumeroIdentificacion().trim().equals(agregarServidorHelper.getMovimiento().
                                    getNumeroIdentificacion().trim())) {
                        agregarServidorHelper.getMovimiento().setApellidos(r.getApellidosPaterno());
                        agregarServidorHelper.getMovimiento().setNombres(r.getNombres());
                        agregarServidorHelper.getMovimiento().setApellidosNombres(UtilCadena.concatenar(
                                agregarServidorHelper.getMovimiento().getApellidos(), " ",
                                agregarServidorHelper.getMovimiento().getNombres()));
                        if (r.getCatalogoGenero() != null) {
                            agregarServidorHelper.getMovimiento().setGenero(r.getCatalogoGenero().getCodigo());
                        }
//                            r.setEmMovimientoPersonal(true);
//                            reclutamientoServicio.actualizarReclutamiento(r);
                    } else {
                        mostrarMensajeEnPantalla(
                                "Persona no se encuentra reclutada en el puesto seleccionado.",
                                FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    mostrarMensajeEnPantalla(
                            "Persona no se encuentra registrada en reclutamiento para el puesto seleccionado.",
                            FacesMessage.SEVERITY_ERROR);
                }
            } else {
                mostrarMensajeEnPantalla(
                        "Para buscar la persona faltan datos de Modalidad Laboral/Régimen Laboral o "
                        + " Unidad Organizacional.",
                        FacesMessage.SEVERITY_ERROR);
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al buscar una persona en reclutamiento", e);
        }
    }

    /**
     * Calcula el valor de la multa
     */
    public void calcularValorMulta() {
        BigDecimal valor = BigDecimal.ZERO;
        if (agregarServidorHelper.getRegimenDisciplinario().getValorMulta() != null) {
            valor = agregarServidorHelper.getRegimenDisciplinario().getValorMulta().multiply(
                    agregarServidorHelper.getMovimiento().getRmu().divide(new BigDecimal("100")));
        }
        agregarServidorHelper.getRegimenDisciplinario().setValorNominaMulta(UtilNumeros.redondear(
                valor, 2, true));
    }
}
