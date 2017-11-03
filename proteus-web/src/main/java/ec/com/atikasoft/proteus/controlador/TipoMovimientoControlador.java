/*
 *  TipoMovimientoControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoMovimientoHelper;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.HorarioConfiguracionTipoMovimientoEnum;
import ec.com.atikasoft.proteus.enums.IngresaHoraMinutoEnum;
import ec.com.atikasoft.proteus.enums.ObligatorioEnum;
import ec.com.atikasoft.proteus.enums.PeriodoControlEnum;
import ec.com.atikasoft.proteus.enums.PeriodoTiempoMaximoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.TipoAreaAccionPersonalEnum;
import ec.com.atikasoft.proteus.enums.TipoFlujoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

/**
 * Controlador para la administración de Tipos de Movimientos.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoBean")
@ViewScoped
public class TipoMovimientoControlador extends CatalogoControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TipoMovimientoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/tipo_movimiento/tipo_movimiento.jsf";

    /**
     * Servicio de administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de regimen laboral.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tipoMovimientoHelp}")
    private TipoMovimientoHelper tipoMovimientoHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(tipoMovimientoHelper);
        llenarComboModalidadLaboral();
        llenarComboNivelOcupacional();
        tipoMovimientoHelper.setGrupoSeleccionadoFiltro(null);
        tipoMovimientoHelper.setClasesSeleccionadaFiltro(null);
        tipoMovimientoHelper.setClasesFiltrosSeleccionados(null);
        llenarComboGrupo();
        llenarComboClaseFiltros();
        llenarComboDocumentoHabilitante();
        llenarComboCamposConfiguracion();
        llenarComboCamposConfiguracionOriginal();
        llenarComboCamposAccionPersonal();
        llenarComboListaObligatorio();
        llenarComboListaEstadoPuesto();
        llenarComboListaEstadoPersonal();
        llenarComboListaFlujo();
        llenarComboListaPeriodoTiempoMaximo();
        llenarComboListaHorario();
        llenarComboPeriodoControl();
        llenarComboCamposConfiguracionObligatorio();
        llenarComboListaSiNo();
        llenarComboIngresaHoraMinuto();
        llenarComboTiposRotaciones();
//        llenarComboTipoMovimientoFinalizacion();
        tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.FALSE);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.TIPOS_MOVIMIENTOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE TIPOS DE MOVIMIENTOS");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            // verifica si el tipo de movimiento es con solicitud o no.
            tipoMovimientoHelper.getTipoMovimiento().setConSolicitud(false);
            for (TipoFlujoEnum en : TipoFlujoEnum.values()) {
                if (tipoMovimientoHelper.getTipoMovimiento().getTipoFlujo().equals(en.getCodigo())) {
                    tipoMovimientoHelper.getTipoMovimiento().setConSolicitud(en.getConSolicitud());
                    break;
                }
            }
            // guarda el tipo de movimiento.
            if (tipoMovimientoHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    buscarNombresCombosPorId();
                    administracionServicio.guardarTipoMovimiento(tipoMovimientoHelper.getTipoMovimiento());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                buscarNombresCombosPorId();
                administracionServicio.actualizarTipoMovimiento(tipoMovimientoHelper.getTipoMovimiento());
                // actualizar cache
                List<TipoMovimiento> tipos = administracionServicio.listarTiposMovimientos(Boolean.TRUE, Boolean.FALSE,
                        Boolean.TRUE);
                getSession().getServletContext().setAttribute(CacheEnum.TIPO_MOVIMIENTO.getCodigo(), tipos);
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento", e);
        }
        return null;
    }

    /**
     * Metodo que busca el nombre de la modalidad laboral seleccionada.
     */
    public void buscarNombreModalidadLaboral() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getModalidadLaboralCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setModalidadLaboralCoreNombre(null);
            } else {
                ModalidadLaboral modalidad = administracionServicio.buscarModalidadLaboral(tipoMovimientoHelper.
                        getTipoMovimiento().getModalidadLaboralCore());
                tipoMovimientoHelper.getTipoMovimiento().setModalidadLaboralCoreNombre(modalidad.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre de la modalidad laboral.", e);
        }
    }

    /**
     * Metodo que busca el nombre del nivel ocupacional seleccionada.
     */
    public void buscarNombreNivelOcupacional() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getNivelOcupacionalCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setNivelOcupacionalCoreNombre(null);
            } else {
                NivelOcupacional nivel = regimenLaboralServicio.buscarNivelOcupacional(tipoMovimientoHelper.
                        getTipoMovimiento().getNivelOcupacionalCore());
                tipoMovimientoHelper.getTipoMovimiento().setNivelOcupacionalCoreNombre(nivel.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del nivel ocupacional.", e);
        }
    }

    /**
     * Metodo que busca el nombre del estado puesto inicial seleccionada.
     */
    public void buscarNombreEstadoPuestoInicial() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPuestoInicialCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialCoreNombre(null);
            } else {
                EstadoPuesto estadoPuesto = administracionServicio.buscarEstadoPuesto(tipoMovimientoHelper.
                        getTipoMovimiento().getEstadoPuestoInicialCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialCoreNombre(estadoPuesto.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del puesto.", e);
        }

    }

    /**
     * Metodo que busca el nombre del estado puesto final seleccionada.
     */
    public void buscarNombreEstadoPuestoFinal() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPuestoFinalCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalCoreNombre(null);
            } else {
                EstadoPuesto estadoPuesto = administracionServicio.buscarEstadoPuesto(tipoMovimientoHelper.
                        getTipoMovimiento().getEstadoPuestoFinalCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalCoreNombre(estadoPuesto.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del puesto.", e);
        }
    }

    /**
     * Metodo que busca el nombre del estado puesto inicial seleccionada.
     */
    public void buscarNombreEstadoPuestoPropuestoInicial() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPuestoInicialPropuestaCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialPropuestaCoreNombre(null);
            } else {
                EstadoPuesto estadoPuesto = administracionServicio.buscarEstadoPuesto(tipoMovimientoHelper.
                        getTipoMovimiento().
                        getEstadoPuestoInicialPropuestaCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialPropuestaCoreNombre(
                        estadoPuesto.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del puesto.", e);
        }
    }

    /**
     * Metodo que busca el nombre del estado puesto final seleccionada.
     */
    public void buscarNombreEstadoPuestoPropuestoFinal() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPuestoFinalPropuestaCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalPropuestaCoreNombre(null);
            } else {
                EstadoPuesto estadoPuesto = administracionServicio.buscarEstadoPuesto(
                        tipoMovimientoHelper.getTipoMovimiento().getEstadoPuestoFinalPropuestaCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalPropuestaCoreNombre(
                        estadoPuesto.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del puesto.", e);
        }
    }

    /**
     * Metodo que busca el nombre del estado personal inicial seleccionada.
     */
    public void buscarNombreEstadoPersonalInicial() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPersonalInicialCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalInicialCoreNombre(null);
            } else {
                EstadoPersonal estadoPersonal = administracionServicio.buscarEstadoPersonal(tipoMovimientoHelper.
                        getTipoMovimiento().
                        getEstadoPersonalInicialCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalInicialCoreNombre(estadoPersonal.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del personal.", e);
        }
    }

    /**
     * Metodo que busca el nombre del estado personal final seleccionada.
     */
    public void buscarNombreEstadoPersonalFinal() {
        try {
            if (tipoMovimientoHelper.getTipoMovimiento().getEstadoPersonalFinalCore() == null) {
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalFinalCoreNombre(null);
            } else {
                EstadoPersonal estadoPersonal = administracionServicio.buscarEstadoPersonal(
                        tipoMovimientoHelper.getTipoMovimiento().getEstadoPersonalFinalCore());
                tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalFinalCoreNombre(estadoPersonal.getNombre());
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar el nombre del estado del personal.", e);
        }

    }

    /**
     * Llama a los metodos de busqueda de nombres.
     */
    public void buscarNombresCombosPorId() {
        buscarNombreModalidadLaboral();
        buscarNombreNivelOcupacional();
        buscarNombreEstadoPuestoInicial();
        buscarNombreEstadoPuestoFinal();
        buscarNombreEstadoPersonalInicial();
        buscarNombreEstadoPersonalFinal();
        buscarNombreEstadoPuestoPropuestoFinal();
        buscarNombreEstadoPuestoPropuestoInicial();
    }

    /**
     * Metodo que se encarga de validar el nemonico.
     *
     * @return Boolean si hay nemonico o no
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            tipoMovimientoHelper.getListaTipoMovimientoNemonico().clear();
            List<TipoMovimiento> lista = administracionServicio.listarTodosTipoMovimientoPorNemonico(
                    tipoMovimientoHelper.getTipoMovimiento().getNemonico());
            tipoMovimientoHelper.setListaTipoMovimientoNemonico(lista);
            if (tipoMovimientoHelper.getListaTipoMovimientoNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(tipoMovimientoHelper.getTipoMovimientoEditDelete());
            TipoMovimiento tm = (TipoMovimiento) cloneBean;
            iniciarDatosEntidad(tm, Boolean.FALSE);
            tipoMovimientoHelper.setTipoMovimiento(tm);
            tipoMovimientoHelper.setGrupo(administracionServicio.buscarGrupo(tm.getClase().getGrupoId()));
            iniciarValoresAreasEdicion(tm);
            tipoMovimientoHelper.setEsNuevo(Boolean.FALSE);
            llenarComboClase();
            verificarValorTiempoMaximo();
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de verificar el valor que tiene el campo tiempo
     * maximo para en base a eso ocultar o mostrar un campo.
     */
    public void verificarValorTiempoMaximo() {
        if (tipoMovimientoHelper.getTipoMovimiento().getTiempoMaximo() != null) {
            LOG.info("Entra al if");
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.FALSE);
        } else {
            LOG.info("Entra al else");
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.TRUE);
        }
    }

    /**
     * Metodo que se encarga de iniciar los valores para las areas cuando se las
     * va a editar.
     *
     * @param tm Tipo de movimiento
     */
    public void iniciarValoresAreasEdicion(final TipoMovimiento tm) {
        validarAreaPuesto(tm);
        validarAreaRigeAPartirDe(tm);
        validarAreaPeriodoFijo(tm);
        validarAreaInformacionSalida(tm);
        validarAreaServidor(tm);
        validarAreaFallecimiento(tm);
        validarAreaContratoCT(tm);
        validarAreaContratoLosep(tm);
        validarAreaDiscapacidad(tm);
        validarAreaFormacionAcademica(tm);
        validarAreaEstadoPuesto(tm);
        validarAreaEstadoPuestoPropuesto(tm);
        validarAreaEstadoPersonal(tm);
        validarAreaAccionPersonal(tm);
        validarAreaMemorando(tm);
        validarAreaRegimenDisciplinario(tm);
        validarAreaLicenciasMaternidadPaternidad(tm);
        validarAreaConfiguracionLicenciasPermisos(tm);
        validarAreaLicencias(tm);
        validarAreaTiempoPorDevengar(tm);
        validarAreaRepresentacionAsociacionLaboral(tm);
        validarAreaPermisoMatriculaHijos(tm);
        validarAreaComisionServiciosInstitucionRequiriente(tm);
        validarAreaPuestoDestino(tm);
        validarAreaInformacionMovimientoReintegro(tm);

    }

    @Override
    public String iniciarNuevo() {
        tipoMovimientoHelper.setTipoMovimiento(new TipoMovimiento());
        tipoMovimientoHelper.setGrupo(new Grupo());
        tipoMovimientoHelper.getTipoMovimiento().setClase(new Clase());
        tipoMovimientoHelper.getTipoMovimiento().getClase().setGrupo(new Grupo());
        iniciarValoresAreasNuevo();
        iniciarValoresRequeridosNuevo();
        iniciarDatosEntidad(tipoMovimientoHelper.getTipoMovimiento(), Boolean.TRUE);
        iniciarCombos(tipoMovimientoHelper.getListaClase());
        tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.TRUE);
        tipoMovimientoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de iniciar los valores para las areas cuando se las
     * va a crear.
     */
    public void iniciarValoresAreasNuevo() {
        tipoMovimientoHelper.getTipoMovimiento().setAreaRigeAPartirDe(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaPuesto(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaPeriodoFijo(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionSalida(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaServidor(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaFallecimiento(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaContratoCT(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaContratoLosep(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaDiscapacidad(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaAccionPersonal(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaMemorando(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaRegimenDisciplinario(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaLicenciasMaternidadPaternidad(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaConfiguracionLicenciasPermisos(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaLicencias(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaTiempoPorDevengar(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaRepresentacionAsociacionLaboral(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaPermisoMatriculaHijos(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaComisionServicioInstitucionRequiriente(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaFormacionAcademica(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuesto(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPersonal(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuestoPropuesto(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaPuestoDestino(Boolean.FALSE);
        tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionMovimientoReintegro(Boolean.FALSE);
    }

    /**
     * Metodo que se encarga de iniciar los valores para los campos requeridos
     * cuando se las va a crear.
     */
    public void iniciarValoresRequeridosNuevo() {
        tipoMovimientoHelper.setRequeridoAreaPuesto(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaRigeAPartirDe(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaPeriodoFijo(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaInformacionSalida(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaServidor(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaFallecimiento(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaContratoCT(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaContratoLOSEP(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaDiscapacidad(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaFormacionAcademica(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaEstadoPuesto(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaEstadoPersonal(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaEstadoPuestoPropuesto(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaAccionPersonal(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaMemorando(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinario(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinarioSuspension(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaLicenciasMaternidadPaternidad(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaConfiguracionLicenciasPermisos(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaLicencias(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaTiempoPorDevengar(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoCampoTotalHorasSemana(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoCampoValorPeriodoHorasControl(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaRepresentacionAsociacionLaboral(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaPermisoMatriculaHijos(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaComisionServicioInstitucionRequiriente(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaPuestoDestino(Boolean.FALSE);
        tipoMovimientoHelper.setRequeridoAreaInformacionMovimientoReintegro(Boolean.FALSE);
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((administracionServicio.tieneRestricciones("tipoMovimiento.id",
                    "TipoMovimiento", "TipoMovimientoAlerta",
                    tipoMovimientoHelper.getTipoMovimientoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Alertas,");
            }
            if ((administracionServicio.tieneRestricciones("tipoMovimiento.id",
                    "TipoMovimiento", "TipoMovimientoRegla",
                    tipoMovimientoHelper.getTipoMovimientoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Reglas,");
            }
            if ((administracionServicio.tieneRestricciones("tipoMovimiento.id",
                    "TipoMovimiento", "TipoMovimientoRequisito",
                    tipoMovimientoHelper.getTipoMovimientoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Requisitos,");
            }
            if ((administracionServicio.tieneRestricciones("tipoMovimiento.id",
                    "TipoMovimiento", "Tramite",
                    tipoMovimientoHelper.getTipoMovimientoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Tramites.");
            }
            if (cont == 0) {
                administracionServicio.eliminarTipoMovimiento(tipoMovimientoHelper.getTipoMovimientoEditDelete());
                tipoMovimientoHelper.getListaTipoMovimiento().
                        remove(tipoMovimientoHelper.getTipoMovimientoEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, mensaje);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            tipoMovimientoHelper.getListaTipoMovimiento().clear();
            tipoMovimientoHelper.setListaTipoMovimiento(
                    administracionServicio.listarTodosTipoMovimientoPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de modalidad desde
     * un catalogo del siit core.
     */
    public void llenarComboModalidadLaboral() {
        try {
            iniciarCombos(tipoMovimientoHelper.getListaModalidadLaboral());
            List<ModalidadLaboral> modalidades = administracionServicio.listarTodosModalidadLaboralPorNombre(null);
            for (ModalidadLaboral modalidad : modalidades) {
                tipoMovimientoHelper.getListaModalidadLaboral().add(new SelectItem(modalidad.getId(), modalidad.
                        getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la modalidad laboral.", e);

        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de nivel
     * ocupacional desde un catalogo del siit core.
     */
    public void llenarComboNivelOcupacional() {
        iniciarCombos(tipoMovimientoHelper.getListaNivelOcupacional());
//        List<CatalogoDetalle> lista = catalogoServicio.buscarNivelOcupacionalLaboral();
//        for (CatalogoDetalle cd : lista) {
//            tipoMovimientoHelper.getListaNivelOcupacional().add(new SelectItem(cd.getId(), cd.getNombre()));
//            tipoMovimientoHelper.getTipoMovimiento().setNivelOcupacionalCoreNombre(cd.getNombre());
//        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboGrupo() {
        try {
            //tipoMovimientoHelper.getListaGrupo().clear();
            iniciarCombos(tipoMovimientoHelper.getListaGrupo());
            List<Grupo> listaGrupo = administracionServicio.listarTodosVigente();
            for (Grupo g : listaGrupo) {
                tipoMovimientoHelper.getListaGrupo().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de grupo" + ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de clase desde la
     * tabla clases.
     */
    public void llenarComboClase() {
        try {
            tipoMovimientoHelper.setGrupo(administracionServicio.buscarGrupo(tipoMovimientoHelper.getTipoMovimiento().
                    getClase().getGrupoId()));
            tipoMovimientoHelper.getListaClase().clear();
            iniciarCombos(tipoMovimientoHelper.getListaClase());
            List<Clase> listaClase = administracionServicio.listarClasePorGrupoId(
                    tipoMovimientoHelper.getTipoMovimiento().getClase().getGrupoId());
            for (Clase c : listaClase) {
                tipoMovimientoHelper.getListaClase().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }

    /**
     * Llenar el combo de clases segun un grupo si hay uno seleccionado en filtros
     * Generar la representacion en JSON de estas clases
     */
    public void llenarComboClaseFiltros() {
        try {
            iniciarCombos(tipoMovimientoHelper.getListaClasesFiltros());
            List<Clase> listaClase = null;
            Long idGrupo = tipoMovimientoHelper.getGrupoSeleccionadoFiltro();
            Long idClase = tipoMovimientoHelper.getClasesSeleccionadaFiltro();
            if (idGrupo == null) {
                listaClase = administracionServicio.listarTodosClase();
            } else {
                listaClase = administracionServicio.listarClasePorGrupoId(idGrupo);
            }
            JSONObject data = new JSONObject();
            data.put("grupo", idGrupo);
            data.put("clase", idClase);
            data.put("seleccion",obtenerProperties().
                    getString("ec.gob.mrl.smp.genericos.combo.seleccione"));
            JSONArray array = new JSONArray();
            for (Clase c : listaClase) {
                SelectItem it = new SelectItem(c.getId(), c.getNombre());
                tipoMovimientoHelper.getListaClasesFiltros().add(it);
                JSONObject object = new JSONObject();
                object.put("id", c.getId());
                object.put("name", c.getNombre());
                array.add(object);
            }
            data.put("clases", array);
            tipoMovimientoHelper.setClasesFiltrosSeleccionados(data.toString());
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de clase" + ex);
        }
    }

    /**
     * Maneja el evento de seleccion de filtros de la tabla desde el cliente 
     * Carga el grupo o clase seleccionado para operar con el
     */
    public void llenarComboClaseFiltrosEvent() {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        DataTable component = (DataTable) view.findComponent(":tiposMovimientosForm:tablaTiposMovimientos");
        Map<String, String> filters = component.getFilters();
        String idGrupoStr = null;
        String idClaseStr = null;
        for (Map.Entry<String, String> e : filters.entrySet()) {
            if (e.getKey().equals("clase.grupo.id")) {
                idGrupoStr = e.getValue();
            }
            if (e.getKey().equals("clase.id")) {
                idClaseStr = e.getValue();
            }
        }
        if (idGrupoStr != null) {
            tipoMovimientoHelper.setGrupoSeleccionadoFiltro(Long.parseLong(idGrupoStr));
        }
        if (idClaseStr != null) {
            tipoMovimientoHelper.setClasesSeleccionadaFiltro(Long.parseLong(idClaseStr));
        }
        llenarComboClaseFiltros();
    }

    /**
     * Metodo que se encarga de llenar el combo de documento habilitante.
     */
    public void llenarComboDocumentoHabilitante() {
        try {
            tipoMovimientoHelper.getListaDocumentoHabilitante().clear();
            iniciarCombos(tipoMovimientoHelper.getListaDocumentoHabilitante());
            List<DocumentoHabilitante> lista = administracionServicio.listarDocumentoHabilitanteVigentes();
            for (DocumentoHabilitante dh : lista) {
                tipoMovimientoHelper.getListaDocumentoHabilitante().add(new SelectItem(dh.getId(), dh.getNombre()));
            }
        } catch (ServicioException ex) {
            info(getClass().getName(), "Error al llenar el combo de documento habilitante" + ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para los combos de configuracion
     * desde una enumeracion.
     */
    public void llenarComboCamposConfiguracion() {
        iniciarCombos(tipoMovimientoHelper.getListaCamposConfiguracion());
        for (CamposConfiguracionEnum cc : CamposConfiguracionEnum.values()) {
            tipoMovimientoHelper.getListaCamposConfiguracion().add(new SelectItem(cc.getCodigo(), cc.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar una lista desde una enumeracion pero no
     * con todos los elementos de la enumeracion solo con obligatorio y solo
     * lectura.
     */
    public void llenarComboCamposConfiguracionOriginal() {
        iniciarCombos(tipoMovimientoHelper.getListaCamposConfiguracionObligatorio());
        tipoMovimientoHelper.getListaCamposConfiguracionObligatorio().add(
                new SelectItem(CamposConfiguracionEnum.OBLIGATORIO.getCodigo(),
                        CamposConfiguracionEnum.OBLIGATORIO.getDescripcion()));
        tipoMovimientoHelper.getListaCamposConfiguracionObligatorio().add(
                new SelectItem(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo(),
                        CamposConfiguracionEnum.SOLO_LECTURA.getDescripcion()));
    }

    /**
     * Metodo que se encarga de llenar la lista para los combos de configuracion
     * desde una enumeracion para el area de accion de personal.
     */
    public void llenarComboCamposAccionPersonal() {
        iniciarCombos(tipoMovimientoHelper.getListaCamposConfiguracionAccionPersonal());
        for (TipoAreaAccionPersonalEnum ta : TipoAreaAccionPersonalEnum.values()) {
            tipoMovimientoHelper.getListaCamposConfiguracionAccionPersonal().add(
                    new SelectItem(ta.getCodigo(), ta.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de obligatorio.
     */
    public void llenarComboListaObligatorio() {
        tipoMovimientoHelper.getListaObligatorio().clear();
        for (ObligatorioEnum o : ObligatorioEnum.values()) {
            tipoMovimientoHelper.getListaObligatorio().add(new SelectItem(o.getCodigo(), o.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de estado puesto.
     */
    public void llenarComboListaEstadoPuesto() {
        try {
            tipoMovimientoHelper.getListaEstadoPuesto().clear();
            iniciarCombos(tipoMovimientoHelper.getListaEstadoPuesto());
            List<EstadoPuesto> estados = administracionServicio.listarTodosEstadoPuestoPorNombre(null);
            for (EstadoPuesto cd : estados) {
                tipoMovimientoHelper.getListaEstadoPuesto().add(new SelectItem(cd.getId(), cd.getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al recuperar estados de puestos.", e);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de estado personal.
     */
    public void llenarComboListaEstadoPersonal() {
        try {
            tipoMovimientoHelper.getListaEstadoPersonal().clear();
            iniciarCombos(tipoMovimientoHelper.getListaEstadoPersonal());
            List<EstadoPersonal> estados = administracionServicio.listarTodosEstadoPersonalPorNombre(null);
            for (EstadoPersonal cd : estados) {
                tipoMovimientoHelper.getListaEstadoPersonal().add(new SelectItem(cd.getId(), cd.getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_GENERICO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al recuperar estados de personal.", e);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de flujo.
     */
    public void llenarComboListaFlujo() {
        tipoMovimientoHelper.getListaFlujo().clear();
        iniciarCombos(tipoMovimientoHelper.getListaFlujo());
        for (TipoFlujoEnum tf : TipoFlujoEnum.values()) {
            tipoMovimientoHelper.getListaFlujo().add(new SelectItem(tf.getCodigo(), tf.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de periodo tiempo maximo.
     */
    public void llenarComboListaPeriodoTiempoMaximo() {
        tipoMovimientoHelper.getListaPeriodoTiempoMaximo().clear();
        iniciarCombos(tipoMovimientoHelper.getListaPeriodoTiempoMaximo());
        for (PeriodoTiempoMaximoEnum ptm : PeriodoTiempoMaximoEnum.values()) {
            tipoMovimientoHelper.getListaPeriodoTiempoMaximo().add(
                    new SelectItem(ptm.getCodigo(), ptm.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de horario para el area de
     * configuracion de licencias persmisos.
     */
    public void llenarComboListaHorario() {
        tipoMovimientoHelper.getListaHorario().clear();
        iniciarCombos(tipoMovimientoHelper.getListaHorario());
        for (HorarioConfiguracionTipoMovimientoEnum hc : HorarioConfiguracionTipoMovimientoEnum.values()) {
            tipoMovimientoHelper.getListaHorario().add(new SelectItem(hc.getCodigo(), hc.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar la lista de periodo para control del area
     * de configuracion de licencias permisos.
     */
    public void llenarComboPeriodoControl() {
        tipoMovimientoHelper.getListaPeriodoControl().clear();
        iniciarCombos(tipoMovimientoHelper.getListaPeriodoControl());
        for (PeriodoControlEnum pc : PeriodoControlEnum.values()) {
            tipoMovimientoHelper.getListaPeriodoControl().add(new SelectItem(pc.getCodigo(), pc.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar una lista desde una enumeracion pero no
     * con todos los elementos de la enumeracion solo con obligatorio y no
     * obligatorio.
     */
    public void llenarComboCamposConfiguracionObligatorio() {
        iniciarCombos(tipoMovimientoHelper.getListaCamposConfiguracionNoObligatorio());
        tipoMovimientoHelper.getListaCamposConfiguracionNoObligatorio().add(
                new SelectItem(CamposConfiguracionEnum.OBLIGATORIO.getCodigo(),
                        CamposConfiguracionEnum.OBLIGATORIO.getDescripcion()));
        tipoMovimientoHelper.getListaCamposConfiguracionNoObligatorio().add(
                new SelectItem(CamposConfiguracionEnum.NO_OBLIGATORIO.getCodigo(),
                        CamposConfiguracionEnum.NO_OBLIGATORIO.getDescripcion()));
    }

    /**
     * Metodo que se encarga de llenar la lista desde una enumeracion de si y
     * no.
     */
    public void llenarComboListaSiNo() {
        iniciarCombos(tipoMovimientoHelper.getListaSiNo());
        for (SiNoEnum sn : SiNoEnum.values()) {
            tipoMovimientoHelper.getListaSiNo().add(new SelectItem(sn.getCodigo(), sn.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar el combo del campo ingresa hora minuto.
     */
    public void llenarComboIngresaHoraMinuto() {
        iniciarCombos(tipoMovimientoHelper.getListaIngresaHoraMinuto());
        for (IngresaHoraMinutoEnum hm : IngresaHoraMinutoEnum.values()) {
            tipoMovimientoHelper.getListaIngresaHoraMinuto().add(new SelectItem(hm.getCodigo(), hm.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar el combo del tipos de periodos
     */
    public void llenarComboTiposRotaciones() {
        iniciarCombos(tipoMovimientoHelper.getListaTipoRotaciones());
        for (TipoRotacionEnum c : TipoRotacionEnum.values()) {
            tipoMovimientoHelper.getListaTipoRotaciones().add(new SelectItem(c.getCodigo(), c.getDescripcion()));
        }
    }

    /**
     * Metodo que se encarga de llenar el combo del campo tipos de movimiento de
     * finalizacion.
     */
//    public void llenarComboTipoMovimientoFinalizacion() {
//        try {
//            iniciarCombos(tipoMovimientoHelper.getListaTiposMovimientoFinalizacion());
//            List<TipoMovimiento> tipos = administracionServicio.buscarTiposMovimientosPorGrupo(GrupoEnum.FINALIZACION.
//                    getCodigo());
//            for (TipoMovimiento tm : tipos) {
//                tipoMovimientoHelper.getListaTiposMovimientoFinalizacion().add(
//                        new SelectItem(tm.getId(), tm.getNombre()));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * Metodo que se encarga de habilitar o desabilitar el campo de tiempo
     * maximo dependiendo del combo de periodo tiempo maximo seleccionado.
     *
     * @return Boolean
     * @param periodoTiempoMaximo String
     */
    public Boolean habilitarCampoTiempoMaximo(final String periodoTiempoMaximo) {
        if (periodoTiempoMaximo.equalsIgnoreCase("A") || periodoTiempoMaximo.equalsIgnoreCase("M")
                || periodoTiempoMaximo.equalsIgnoreCase("S") || periodoTiempoMaximo.equalsIgnoreCase("D")) {
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setTiempoMaximo(null);
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.FALSE);
        }
        return tipoMovimientoHelper.getDeshabilitarCampoTiempoMaximo();
    }

    /**
     * Metodo que se encarga de minitorear el cambio de valor de el combo de
     * tiempo maximo.
     *
     * @param event ValueChangeEvent
     */
    public void tiempoMaximoListener(final ValueChangeEvent event) {
        String evento = (String) event.getNewValue();
        if (evento != null) {
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setTiempoMaximo(null);
            tipoMovimientoHelper.setDeshabilitarCampoTiempoMaximo(Boolean.FALSE);
        }
    }

    /**
     * Listener de area puesto.
     *
     * @param event Evento
     */
    public void areaPuestoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.setRequeridoAreaPuesto(Boolean.FALSE);
            tipoMovimientoHelper.getTipoMovimiento().setAreaPuesto(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaPuesto(Boolean.TRUE);
            tipoMovimientoHelper.getTipoMovimiento().setAreaPuesto(Boolean.TRUE);
        }
        setearCamposAreaPuesto();
    }

    /**
     * Valida el area de puesto y setea los valores para los campos requeridos
     * de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaPuesto(final TipoMovimiento tm) {
        if (tm.getAreaPuesto()) {
            tipoMovimientoHelper.setRequeridoAreaPuesto(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaPuesto(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area pusto
     * cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaPuesto() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaInicio(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaFin(null);
        tipoMovimientoHelper.getTipoMovimiento().setRegimenLaboral(null);
        tipoMovimientoHelper.getTipoMovimiento().setPartidaIndividual(null);
        tipoMovimientoHelper.getTipoMovimiento().setEscalaOcupacional(null);
        tipoMovimientoHelper.getTipoMovimiento().setRmu(null);
        tipoMovimientoHelper.getTipoMovimiento().setPuestoInstitucional(null);
        tipoMovimientoHelper.getTipoMovimiento().setRmuSobrevalorado(null);
        tipoMovimientoHelper.getTipoMovimiento().setPuestoAdicional(null);
        tipoMovimientoHelper.getTipoMovimiento().setUnidadOrganizacional(null);
        tipoMovimientoHelper.getTipoMovimiento().setModalidadLaboral(null);
        tipoMovimientoHelper.getTipoMovimiento().setUbicacionGeografica(null);
        tipoMovimientoHelper.getTipoMovimiento().setSueldoBasico(null);
    }

    /**
     * Listener de area rige a partir de.
     *
     * @param event Evento
     */
    public void areaRigeAPartirDeListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRigeAPartirDe(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaRigeAPartirDe(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRigeAPartirDe(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaRigeAPartirDe(Boolean.TRUE);
        }
        setearCamposAreaRigeAPartirDe();
    }

    /**
     * Valida el area de rige a partir de y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaRigeAPartirDe(final TipoMovimiento tm) {
        if (tm.getAreaRigeAPartirDe()) {
            tipoMovimientoHelper.setRequeridoAreaRigeAPartirDe(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaRigeAPartirDe(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area rige a
     * partir de cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaRigeAPartirDe() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaRigeAPartirDe(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaHasta(null);
        tipoMovimientoHelper.getTipoMovimiento().setIngresaHoraMinuto(null);
    }

    /**
     * Listener de area periodo fijo.
     *
     * @param event Evento
     */
    public void areaPeriodoFijoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPeriodoFijo(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaPeriodoFijo(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPeriodoFijo(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaPeriodoFijo(Boolean.TRUE);
        }
        setearCamposAreaPeriodoFijo();
    }

    /**
     * Valida el area de periodo fijo y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaPeriodoFijo(final TipoMovimiento tm) {
        if (tm.getAreaPeriodoFijo()) {
            tipoMovimientoHelper.setRequeridoAreaPeriodoFijo(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaPeriodoFijo(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area periodo
     * fijo cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaPeriodoFijo() {
        tipoMovimientoHelper.getTipoMovimiento().setTipoDesignacion(null);
    }

    /**
     * Listener de area informacion salida.
     *
     * @param event Evento
     */
    public void areaInformaionSalidaListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionSalida(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaInformacionSalida(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionSalida(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaInformacionSalida(Boolean.TRUE);
        }
        setearCamposAreaInformacionSalida();
    }

    /**
     * Valida el area de informacion de salida y setea los valores para los
     * campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaInformacionSalida(final TipoMovimiento tm) {
        if (tm.getAreaInformacionSalida()) {
            tipoMovimientoHelper.setRequeridoAreaInformacionSalida(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaInformacionSalida(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * informacion salida cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaInformacionSalida() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaEfectivaSalida(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaPresentaRenunciaVoluntaria(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaPresentaRenuncia(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaAceptacionRenuncia(null);
        tipoMovimientoHelper.getTipoMovimiento().setConLiquidacion(Boolean.FALSE);
    }

    //---------------------------------------------
    /**
     * Listener de area terminacion contrato.
     *
     * @param event Evento
     */
    public void areaTerminacionContratoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaTerminacionContrato(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaTerminacionContrato(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaTerminacionContrato(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaTerminacionContrato(Boolean.TRUE);
        }
        setearCamposAreaTerminacionContrato();
    }

    /**
     * Valida el area de terminacion de contrato y setea los valores para los
     * campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaTerminacionContrato(final TipoMovimiento tm) {
        if (tm.getAreaTerminacionContrato()) {
            tipoMovimientoHelper.setRequeridoAreaTerminacionContrato(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaTerminacionContrato(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * informacion salida cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaTerminacionContrato() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaInicioContratoAnterior(null);
        tipoMovimientoHelper.getTipoMovimiento().setNumeroContratoAnterior(null);
    }

    //---------------------------------------------
    /**
     * Listener de area servidor.
     *
     * @param event Evento
     */
    public void areaServidorListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaServidor(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaServidor(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaServidor(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaServidor(Boolean.TRUE);
        }
        setearCamposAreaServidor();
    }

    /**
     * Valida el area de servidor y setea los valores para los campos requeridos
     * de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaServidor(final TipoMovimiento tm) {
        if (tm.getAreaServidor()) {
            tipoMovimientoHelper.setRequeridoAreaServidor(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaServidor(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * informacion salida cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaServidor() {
        tipoMovimientoHelper.getTipoMovimiento().setTipoDocumento(null);
        tipoMovimientoHelper.getTipoMovimiento().setNumeroDocumento(null);
        tipoMovimientoHelper.getTipoMovimiento().setApellidoNombre(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaIngresoInstitucion(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaIngresoSectorPublico(null);
    }

    /**
     * Listener de area fallecimiento.
     *
     * @param event Evento
     */
    public void areaFallecimientoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFallecimiento(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaFallecimiento(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFallecimiento(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaFallecimiento(Boolean.TRUE);
        }
        setearCamposAreaFallecimiento();
    }

    /**
     * Valida el area de fallecimiento y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaFallecimiento(final TipoMovimiento tm) {
        if (tm.getAreaFallecimiento()) {
            tipoMovimientoHelper.setRequeridoAreaFallecimiento(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaFallecimiento(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * fallecimiento cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaFallecimiento() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaFallecimiento(null);
        tipoMovimientoHelper.getTipoMovimiento().setCasoFallecimiento(null);
    }

    /**
     * Listener de area contratos ct.
     *
     * @param event Evento
     */
    public void areaContratoCTListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaContratoCT(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaContratoCT(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaContratoCT(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaContratoCT(Boolean.TRUE);
        }
        setearCamposAreaContratoCP();
    }

    /**
     * Valida el area de contratos y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaContratoCT(final TipoMovimiento tm) {
        if (tm.getAreaContratoCT()) {
            tipoMovimientoHelper.setRequeridoAreaContratoCT(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaContratoCT(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area contratos
     * cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaContratoCP() {
        tipoMovimientoHelper.getTipoMovimiento().setTiempoJornadaDiaria(null);
        tipoMovimientoHelper.getTipoMovimiento().setTipoTemporada(null);
    }

    /**
     * Listener de area contratos.
     *
     * @param event Evento
     */
    public void areaContratoLosepListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaContratoLosep(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaContratoLOSEP(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaContratoLosep(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaContratoLOSEP(Boolean.TRUE);
        }
        setearCamposAreaContratoLosep();
    }

    /**
     * Valida el area de contratos y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaContratoLosep(final TipoMovimiento tm) {
        if (tm.getAreaContratoLosep()) {
            tipoMovimientoHelper.setRequeridoAreaContratoLOSEP(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaContratoLOSEP(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area contratos
     * cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaContratoLosep() {
        tipoMovimientoHelper.getTipoMovimiento().setAntecedentesContrato(null);
        tipoMovimientoHelper.getTipoMovimiento().setActividadesContrato(null);
        tipoMovimientoHelper.getTipoMovimiento().setSiglasTituloAcademico(null);
        tipoMovimientoHelper.getTipoMovimiento().setRenovacion(null);
    }

    /**
     * Listener de area contratos.
     *
     * @param event Evento
     */
    public void areaDiscapacidadListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaDiscapacidad(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaDiscapacidad(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaDiscapacidad(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaDiscapacidad(Boolean.TRUE);
        }
        setearCamposAreaDiscapacidad();
    }

    /**
     * Valida el area de discapacidad y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaDiscapacidad(final TipoMovimiento tm) {
        if (tm.getAreaDiscapacidad()) {
            tipoMovimientoHelper.setRequeridoAreaDiscapacidad(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaDiscapacidad(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * discapacidad cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaDiscapacidad() {
        tipoMovimientoHelper.getTipoMovimiento().setDiscapacidad(null);
        tipoMovimientoHelper.getTipoMovimiento().setClaseDiscapacidad(null);
        tipoMovimientoHelper.getTipoMovimiento().setTipoDiscapacidad(null);
        tipoMovimientoHelper.getTipoMovimiento().setPorcentajeDiscapacidad(null);
        tipoMovimientoHelper.getTipoMovimiento().setNumeroConadis(null);
    }

    /**
     * Listener de area contratos.
     *
     * @param event Evento
     */
    public void areaFormacionAcademicaListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFormacionAcademica(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaFormacionAcademica(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFormacionAcademica(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaFormacionAcademica(Boolean.TRUE);
        }
        setearCamposAreaFormacionAcademica();
    }

    /**
     * Valida el area de formacion academica y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaFormacionAcademica(final TipoMovimiento tm) {
        if (tm.getAreaFormacionAcademica()) {
            tipoMovimientoHelper.setRequeridoAreaFormacionAcademica(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaFormacionAcademica(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area formacion
     * academica cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaFormacionAcademica() {
        tipoMovimientoHelper.getTipoMovimiento().setNivelInstruccion(null);
        tipoMovimientoHelper.getTipoMovimiento().setTipoPeriodo(null);
        tipoMovimientoHelper.getTipoMovimiento().setPaisFormacionAcademica(null);
        tipoMovimientoHelper.getTipoMovimiento().setAniosEstudio(null);
        tipoMovimientoHelper.getTipoMovimiento().setNumeroRegistroSenescyt(null);
    }

    /**
     * Listener de area estado puesto.
     *
     * @param event Evento
     */
    public void areaEstadoPuestoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuesto(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPuesto(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuesto(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPuesto(Boolean.TRUE);
        }
        setearCamposAreaEstadoPuesto();
    }

    /**
     * Valida el area de estado puesto y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaEstadoPuesto(final TipoMovimiento tm) {
        if (tm.getAreaEstadoPuesto()) {
            tipoMovimientoHelper.setRequeridoAreaEstadoPuesto(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaEstadoPuesto(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area estado
     * puesto cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaEstadoPuesto() {
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialCore(null);
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalCore(null);
    }

    /**
     * Listener de area estado personal.
     *
     * @param event Evento
     */
    public void areaEstadoPersonalListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPersonal(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPersonal(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPersonal(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPersonal(Boolean.TRUE);
        }
        setearCamposAreaEstadoPersonal();
    }

    /**
     * Valida el area de estado personal y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaEstadoPersonal(final TipoMovimiento tm) {
        if (tm.getAreaEstadoPersonal()) {
            tipoMovimientoHelper.setRequeridoAreaEstadoPersonal(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaEstadoPersonal(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area estado
     * puesto cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaEstadoPersonal() {
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalInicialCore(null);
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPersonalFinalCore(null);
    }

    ////
    /**
     * Listener de area estado puesto propuesto.
     *
     * @param event Evento
     */
    public void areaEstadoPuestoPropuestoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuestoPropuesto(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPuestoPropuesto(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaEstadoPuestoPropuesto(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaEstadoPuestoPropuesto(Boolean.TRUE);
        }
        setearCamposAreaEstadoPuestoPropuesta();
    }

    /**
     * Valida el area de estado puesto propuesto y setea los valores para los
     * campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaEstadoPuestoPropuesto(final TipoMovimiento tm) {
        if (tm.getAreaEstadoPersonal()) {
            tipoMovimientoHelper.setRequeridoAreaEstadoPuestoPropuesto(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaEstadoPuestoPropuesto(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area estado
     * puesto/ personal cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaEstadoPuestoPropuesta() {
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoFinalPropuestaCore(null);
        tipoMovimientoHelper.getTipoMovimiento().setEstadoPuestoInicialPropuestaCore(null);
    }

    /**
     * Listener de area accion de personal.
     *
     * @param event Evento
     */
    public void areaAccionPersonalListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaAccionPersonal(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaAccionPersonal(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaAccionPersonal(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaAccionPersonal(Boolean.TRUE);
        }
        setearCamposAreaAccionPersonal();
    }

    /**
     * Valida el area de accion personal y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaAccionPersonal(final TipoMovimiento tm) {
        if (tm.getAreaAccionPersonal()) {
            tipoMovimientoHelper.setRequeridoAreaAccionPersonal(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaAccionPersonal(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area accion
     * personal cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaAccionPersonal() {
        tipoMovimientoHelper.getTipoMovimiento().setSituacionActual(null);
        tipoMovimientoHelper.getTipoMovimiento().setSituacionPropuesta(null);
        tipoMovimientoHelper.getTipoMovimiento().setReemplazo(null);
        tipoMovimientoHelper.getTipoMovimiento().setPosesionCargo(null);
    }

    /**
     * Listener de area accion de memorando.
     *
     * @param event Evento
     */
    public void areaMemorandoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaMemorando(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaMemorando(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaMemorando(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaMemorando(Boolean.TRUE);
        }
        setearCamposAreaMemorando();
    }

    /**
     * Listener de area accion de adendum.
     *
     * @param event Evento
     */
    public void areaAdendumListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaAdendum(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaAdendum(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaAdendum(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaAdendum(Boolean.TRUE);
        }
        setearCamposAreaAdendum();
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area adendum
     * cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaAdendum() {
        tipoMovimientoHelper.getTipoMovimiento().setAntecedentes(null);
    }

    /**
     * Valida el area de memorando y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaMemorando(final TipoMovimiento tm) {
        if (tm.getAreaMemorando()) {
            tipoMovimientoHelper.setRequeridoAreaMemorando(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaMemorando(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area memorando
     * cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaMemorando() {
        tipoMovimientoHelper.getTipoMovimiento().setNumeroMemorando(null);
        tipoMovimientoHelper.getTipoMovimiento().setAsuntoMemorando(null);
        tipoMovimientoHelper.getTipoMovimiento().setContenidoMemorando(null);
    }

    /**
     * Listener de area regimen disciplinario.
     *
     * @param event Evento
     */
    public void areaRegimenDisciplinarioListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRegimenDisciplinario(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinario(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRegimenDisciplinario(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinario(Boolean.TRUE);
        }
        setearCamposAreaRegimenDisciplinario();
    }

    /**
     * Valida el area de regimen disciplinario y setea los valores para los
     * campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaRegimenDisciplinario(final TipoMovimiento tm) {
        if (tm.getAreaRegimenDisciplinario()) {
            tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinario(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaRegimenDisciplinario(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area regimen
     * disciplinario cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaRegimenDisciplinario() {
        tipoMovimientoHelper.getTipoMovimiento().setRdfalta(null);
        tipoMovimientoHelper.getTipoMovimiento().setRdDescripcion(null);
    }

    /**
     * Listener de area de licencias de maternidad / paternidad.
     *
     * @param event Evento
     */
    public void areaLicenciasMaternidadPaternidadListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaLicenciasMaternidadPaternidad(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaLicenciasMaternidadPaternidad(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaLicenciasMaternidadPaternidad(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaLicenciasMaternidadPaternidad(Boolean.TRUE);
        }
        setearCamposAreaLicenciasMaternidadPaternidad();
    }

    /**
     * Valida el area de licencias maternidad / paternidad y setea los valores
     * para los campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaLicenciasMaternidadPaternidad(final TipoMovimiento tm) {
        if (tm.getAreaLicenciasMaternidadPaternidad()) {
            tipoMovimientoHelper.setRequeridoAreaLicenciasMaternidadPaternidad(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaLicenciasMaternidadPaternidad(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de
     * licencias maternidad / paternidad cuando exista un cambio de valor del
     * listener.
     */
    public void setearCamposAreaLicenciasMaternidadPaternidad() {
        tipoMovimientoHelper.getTipoMovimiento().setTipoNacimiento(null);
        tipoMovimientoHelper.getTipoMovimiento().setDiasAdicionalesMadreNacimientoMultiple(null);
        tipoMovimientoHelper.getTipoMovimiento().setDiasAdicionalesPadreNacimientoMultiple(null);
    }

    /**
     * Listener de area de configuracion licencias permisos.
     *
     * @param event Evento
     */
    public void areaConfiguracionLicenciasPermisosListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaConfiguracionLicenciasPermisos(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaConfiguracionLicenciasPermisos(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaConfiguracionLicenciasPermisos(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaConfiguracionLicenciasPermisos(Boolean.TRUE);
        }
        setearCamposAreaConfiguracionLicenciasPermisos();
    }

    /**
     * Valida el area de configuracion licencias permisos y setea los valores
     * para los campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaConfiguracionLicenciasPermisos(final TipoMovimiento tm) {
        if (tm.getAreaConfiguracionLicenciasPermisos()) {
            tipoMovimientoHelper.setRequeridoAreaConfiguracionLicenciasPermisos(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaConfiguracionLicenciasPermisos(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de
     * configuracion licencias permisos cuando exista un cambio de valor del
     * listener.
     */
    public void setearCamposAreaConfiguracionLicenciasPermisos() {
        tipoMovimientoHelper.getTipoMovimiento().setHorario(null);
        tipoMovimientoHelper.getTipoMovimiento().setTotalHorasSemana(null);
        tipoMovimientoHelper.getTipoMovimiento().setPeriodoControl(null);
        tipoMovimientoHelper.getTipoMovimiento().setValorPeriodoControl(null);
        tipoMovimientoHelper.getTipoMovimiento().setHorarioDevengar(null);
    }

    /**
     * Listener de area de licencias.
     *
     * @param event Evento
     */
    public void areaLicenciasListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaLicencias(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaLicencias(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaLicencias(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaLicencias(Boolean.TRUE);
        }
        setearCamposAreaLicencias();
    }

    /**
     * Valida el area de licencias y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaLicencias(final TipoMovimiento tm) {
        if (tm.getAreaLicencias()) {
            tipoMovimientoHelper.setRequeridoAreaLicencias(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaLicencias(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de
     * licencias cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaLicencias() {
        tipoMovimientoHelper.getTipoMovimiento().setFechaOcurrioHecho(null);
        tipoMovimientoHelper.getTipoMovimiento().setFechaPresentaDocumento(null);
        tipoMovimientoHelper.getTipoMovimiento().setValorPeriodoControl(null);
    }

    /**
     * Listener de area de tiempo por devengar.
     *
     * @param event Evento
     */
    public void areaTiempoPorDevengarListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaTiempoPorDevengar(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaTiempoPorDevengar(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaTiempoPorDevengar(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaTiempoPorDevengar(Boolean.TRUE);
        }
        setearCamposAreaTiempoPorDevengar();
    }

    /**
     * Valida el area de tiempo por devengar y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaTiempoPorDevengar(final TipoMovimiento tm) {
        if (tm.getAreaTiempoPorDevengar()) {
            tipoMovimientoHelper.setRequeridoAreaTiempoPorDevengar(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaTiempoPorDevengar(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de tiempo
     * por devengar cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaTiempoPorDevengar() {
        tipoMovimientoHelper.getTipoMovimiento().setDevengar(null);
        tipoMovimientoHelper.getTipoMovimiento().setPeriodoDevengar(null);
        tipoMovimientoHelper.getTipoMovimiento().setValorDevengar(null);
        tipoMovimientoHelper.getTipoMovimiento().setConsidereCalculo(null);
        tipoMovimientoHelper.getTipoMovimiento().setObservacionDevengar(null);
    }

    /**
     * Listener de area representacion asociacion laboral.
     *
     * @param event Evento
     */
    public void areaRepresentacionAsociacionLaboralListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRepresentacionAsociacionLaboral(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaRepresentacionAsociacionLaboral(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaRepresentacionAsociacionLaboral(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaRepresentacionAsociacionLaboral(Boolean.TRUE);
        }
        setearCamposAreaRepresentacionAsociacionLaboral();
    }

    /**
     * Valida el area de representacion asociacion laboral y setea los valores
     * para los campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaRepresentacionAsociacionLaboral(final TipoMovimiento tm) {
        if (tm.getAreaRepresentacionAsociacionLaboral()) {
            tipoMovimientoHelper.setRequeridoAreaRepresentacionAsociacionLaboral(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaRepresentacionAsociacionLaboral(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de
     * representacion asociacion laboral cuando exista un cambio de valor del
     * listener.
     */
    public void setearCamposAreaRepresentacionAsociacionLaboral() {
        tipoMovimientoHelper.getTipoMovimiento().setNumeroHorasMensuales(null);
        tipoMovimientoHelper.getTipoMovimiento().setMaximoNumeroHorasMensuales(null);
    }

    /**
     * Listener de area permiso matricula hijos.
     *
     * @param event Evento
     */
    public void areaPermisoMatriculaHijosListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPermisoMatriculaHijos(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaPermisoMatriculaHijos(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPermisoMatriculaHijos(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaPermisoMatriculaHijos(Boolean.TRUE);
        }
        setearCamposAreaPermisoMatriculaHijos();
    }

    /**
     * Valida el area de permisos matricula hijos y setea los valores para los
     * campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaPermisoMatriculaHijos(final TipoMovimiento tm) {
        if (tm.getAreaPermisoMatriculaHijos()) {
            tipoMovimientoHelper.setRequeridoAreaPermisoMatriculaHijos(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaPermisoMatriculaHijos(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area de permiso
     * matricula hijos cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaPermisoMatriculaHijos() {
        tipoMovimientoHelper.getTipoMovimiento().setNombreHijo(null);
        tipoMovimientoHelper.getTipoMovimiento().setNivelEstudio(null);
    }

    /**
     * Listener de area comision de servicio institucion requiriente.
     *
     * @param event Evento
     */
    public void areaComisionServicioInstitucionRequirienteListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaComisionServicioInstitucionRequiriente(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaComisionServicioInstitucionRequiriente(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaComisionServicioInstitucionRequiriente(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaComisionServicioInstitucionRequiriente(Boolean.TRUE);
        }
        setearCamposAreaComisionServicioInstitucionRequiriente();
    }

    /**
     * Valida el area comision de servicios institucion requiriente y setea los
     * valores para los campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaComisionServiciosInstitucionRequiriente(final TipoMovimiento tm) {
        if (tm.getAreaComisionServicioInstitucionRequiriente()) {
            tipoMovimientoHelper.setRequeridoAreaComisionServicioInstitucionRequiriente(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaComisionServicioInstitucionRequiriente(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area comision
     * de servicios institucion requiriente cuando exista un cambio de valor del
     * listener.
     */
    public void setearCamposAreaComisionServicioInstitucionRequiriente() {
        tipoMovimientoHelper.getTipoMovimiento().setInstitucion(null);
        tipoMovimientoHelper.getTipoMovimiento().setUnidadAdministrativaInstitucion(null);
        tipoMovimientoHelper.getTipoMovimiento().setUnidadAdministrativaDireccionInstitucion(null);
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area cambio
     * administrativo. cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaCambioAdministrativo() {
        tipoMovimientoHelper.getTipoMovimiento().setUnidadAdministrativaInstitucionCambio(null);
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area cambio
     * administrativo. cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaTrasladoAdministrativo() {
        tipoMovimientoHelper.getTipoMovimiento().setTaPartidaIndividual(null);
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area traspaso
     * misma institucion cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaTraspasoMismaInstitucion() {
        tipoMovimientoHelper.getTipoMovimiento().settUnidadAdministrativa(null);
        tipoMovimientoHelper.getTipoMovimiento().settUnidadAdministrativaDireccion(null);
        tipoMovimientoHelper.getTipoMovimiento().settDenominacionPuesto(null);
    }

    //////
    /**
     * Listener de area puesto destino.
     *
     * @param event Evento
     */
    public void areaPuestoDestinoListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPuestoDestino(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaPuestoDestino(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaPuestoDestino(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaPuestoDestino(Boolean.TRUE);
        }
        setearCamposAreaPuestoDestino();
    }

    /**
     * Valida el area puesto destino y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaPuestoDestino(final TipoMovimiento tm) {
        if (tm.getAreaPuestoDestino()) {
            tipoMovimientoHelper.setRequeridoAreaPuestoDestino(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaPuestoDestino(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area puesto
     * destino. cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaPuestoDestino() {
        tipoMovimientoHelper.getTipoMovimiento().setPartidaIndividualPuestDest(null);
    }

    //////
    /**
     * Listener de area informacion movimiento para reintegro.
     *
     * @param event Evento
     */
    public void areaInformacionMovimientoReintegroListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionMovimientoReintegro(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaInformacionMovimientoReintegro(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaInformacionMovimientoReintegro(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaInformacionMovimientoReintegro(Boolean.TRUE);
        }
        setearCamposAreaInformacionMovimientoReintegro();
    }

    /**
     * Valida el area informacion movimiento reintegro y setea los valores para
     * los campos requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaInformacionMovimientoReintegro(final TipoMovimiento tm) {
        if (tm.getAreaInformacionMovimientoReintegro()) {
            tipoMovimientoHelper.setRequeridoAreaInformacionMovimientoReintegro(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaInformacionMovimientoReintegro(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * informacion movimiento reintegro cuando exista un cambio de valor del
     * listener.
     */
    public void setearCamposAreaInformacionMovimientoReintegro() {
    }

    ////////
    /**
     * Listener de area finalizacio.
     *
     * @param event Evento
     */
    public void areaFinalizacionListener(final ToggleEvent event) {
        if (event.getVisibility().compareTo(Visibility.HIDDEN) == 0) {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFinalizacion(Boolean.FALSE);
            tipoMovimientoHelper.setRequeridoAreaFinalizacion(Boolean.FALSE);
        } else {
            tipoMovimientoHelper.getTipoMovimiento().setAreaFinalizacion(Boolean.TRUE);
            tipoMovimientoHelper.setRequeridoAreaFinalizacion(Boolean.TRUE);
        }
        setearCamposAreaPuestoDestino();
    }

    /**
     * Valida el area finalizacion y setea los valores para los campos
     * requeridos de esa area.
     *
     * @param tm Tipo de movimiento
     */
    public void validarAreaFinalizacion(final TipoMovimiento tm) {
        if (tm.getAreaPuestoDestino()) {
            tipoMovimientoHelper.setRequeridoAreaFinalizacion(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoAreaFinalizacion(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de setear los campos necesarios del area
     * finalizacion. cuando exista un cambio de valor del listener.
     */
    public void setearCamposAreaFinalizacion() {
        tipoMovimientoHelper.getTipoMovimiento().setTipoMovimientoFinalizacion(null);
    }

    /////////////////////////////////////
    /**
     * @return the tipoMovimientoHelper
     */
    public TipoMovimientoHelper getTipoMovimientoHelper() {
        return tipoMovimientoHelper;
    }

    /**
     * @param tipoMovimientoHelper the tipoMovimientoHelper to set
     */
    public void setTipoMovimientoHelper(final TipoMovimientoHelper tipoMovimientoHelper) {
        this.tipoMovimientoHelper = tipoMovimientoHelper;
    }

    /**
     * Indica si el tramite es de ingresos.
     *
     * @return Boolean resultado
     */
    public Boolean esGrupoDeIngresos() {
        return GrupoEnum.INGRESOS.getCodigo().equals(tipoMovimientoHelper.getGrupo().getNemonico());
    }

    /**
     * Indica si el tramite es de absentismo..
     *
     * @return Boolean resultado
     */
    public Boolean esGrupoDeAbsentismo() {
        return GrupoEnum.ABSENTISMO.getCodigo().equals(tipoMovimientoHelper.getGrupo().getNemonico());
    }

    /**
     * Indica si el tramite es de rotaciones.
     *
     * @return Boolean resultado
     */
    public Boolean esGrupoDeRotacion() {
        return GrupoEnum.ROTACIONES.getCodigo().equals(tipoMovimientoHelper.getGrupo().getNemonico());
    }

    /**
     * Indica si el tramite es de salidas.
     *
     * @return Boolean resultado
     */
    public Boolean esGrupoDeSalidas() {
        return GrupoEnum.SALIDAS.getCodigo().equals(tipoMovimientoHelper.getGrupo().getNemonico());
    }

    /**
     * Indica si el tramite es de cesaciones.
     *
     * @return Boolean resultado
     */
    public Boolean esGrupoDeRegimenDisciplinario() {
        return GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo().equals(tipoMovimientoHelper.getGrupo().getNemonico());

    }

    /**
     * Metodo que se encarga de validar si selecciona horario poner el campo
     * total horas semana como requerido.
     *
     * @param horario String
     */
    public void validarCampoTotalHorasSemana(final String horario) {
        if (horario.equalsIgnoreCase("S")) {
            tipoMovimientoHelper.setRequeridoCampoTotalHorasSemana(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoCampoTotalHorasSemana(Boolean.FALSE);
        }
    }

    /**
     * Metodo que se encarga de validar si se selecciona un periodo de control y
     * levanta la bandera para requerido.
     *
     * @param periodoControl String
     */
    public void validarCampoValorPeriodoHorasControl(final String periodoControl) {
        if (periodoControl.equalsIgnoreCase("D") || periodoControl.equalsIgnoreCase("M")
                || periodoControl.equalsIgnoreCase("S")) {
            tipoMovimientoHelper.setRequeridoCampoValorPeriodoHorasControl(Boolean.TRUE);
        } else {
            tipoMovimientoHelper.setRequeridoCampoValorPeriodoHorasControl(Boolean.FALSE);
        }
    }
}
