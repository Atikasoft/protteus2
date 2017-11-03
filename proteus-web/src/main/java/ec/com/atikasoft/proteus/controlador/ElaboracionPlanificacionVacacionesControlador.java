/*
 *  PlanificacionVacacionControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.ElaboracionPlanificacionVacacionesHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.MesesEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.TipoAcumulacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "elaboracionPlanificacionVacacionesBean")
@ViewScoped
public class ElaboracionPlanificacionVacacionesControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(
            ElaboracionPlanificacionVacacionesControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;
    /**
     *
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;
    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;

    /**
     *
     */
    @EJB
    UnidadOrganizacionalDao unidadOrgDao;
    /**
     * 
     */
    @EJB
    DesconcentradoServicio desconcentradoServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{elaboracionPlanificacionVacacionesHelper}")
    private ElaboracionPlanificacionVacacionesHelper elaboracionPlanificacionVacacionesHelper;

    /**
     * Constructor por defecto.
     */
    public ElaboracionPlanificacionVacacionesControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        elaboracionPlanificacionVacacionesHelper.setInstitucionEjercicioFiscalAPlanificarVacaciones(null);
        elaboracionPlanificacionVacacionesHelper.setBuscarServidorPor(null);
        buscarInstitucionEjercicioFiscalSiguiente();
        iniciarNuevo();
        iniciarComboMeses();
    }

    /**
     * llena combo de meses.
     */
    private void iniciarComboMeses() {
        iniciarCombos(elaboracionPlanificacionVacacionesHelper.getOpcionesMeses());
        for (MesesEnum me : MesesEnum.values()) {
            elaboracionPlanificacionVacacionesHelper.getOpcionesMeses().add(new SelectItem(me.getNumero(),
                    me.getNombre()));
        }
    }

    /**
     * INCIACIALIZA LAS VARIABLES ASOCIADAS A LA PLANIFICACION DE VACACIONES
     *
     */
    public void iniciarNuevo() {
        elaboracionPlanificacionVacacionesHelper.iniciador();
        iniciarComboTipoIdentificacion();
        elaboracionPlanificacionVacacionesHelper.setPlanificacionVacacionVO(new PlanificacionVacacionVO());

        iniciarDatosEntidad(elaboracionPlanificacionVacacionesHelper
                .getPlanificacionVacacionVO().getPlanificacionVacacion(), Boolean.TRUE);

        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                .getPlanificacionVacacion().setEstado(EstadoPlanVacacionEnum.APROBADO.getCodigo());

        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                .setPlanificacionVacacionDetalle(new PlanificacionVacacionDetalle());

        if (elaboracionPlanificacionVacacionesHelper.getBuscarServidorPor() == null) {
            elaboracionPlanificacionVacacionesHelper.setApellidosNombres(null);
            elaboracionPlanificacionVacacionesHelper.setTipoIdentificacion(null);
            elaboracionPlanificacionVacacionesHelper.setNroIdentificacion(null);
        } else if (elaboracionPlanificacionVacacionesHelper.getBuscarServidorPor().equals("id")) {
            elaboracionPlanificacionVacacionesHelper.setApellidosNombres(null);
        } else {
            elaboracionPlanificacionVacacionesHelper.setTipoIdentificacion(null);
            elaboracionPlanificacionVacacionesHelper.setNroIdentificacion(null);
        }

    }

    /**
     * LIMPIA EL CAMPO NRO IDENTIFICACION
     */
    public void limpiarNroIdentificacion() {
        elaboracionPlanificacionVacacionesHelper.setPlanificacionVacacionVO(
                new PlanificacionVacacionVO());
        elaboracionPlanificacionVacacionesHelper.setNroIdentificacion(null);
    }

    /**
     * GUARDA LA PLANIFICACION DE VACACIONES PARA EL SERVIDOR SELECCIONADO
     *
     * @return
     */
    public String guardar() {
        try {
            Long nroDias = elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                    .getPlanificacionVacacionDetalle().getNumeroDias();
            if (nroDias != null && nroDias > 0) {
                if (elaboracionPlanificacionVacacionesHelper.getEsNuevo()) {
                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                            .getPlanificacionVacacion()
                            .setServidorJefeId(obtenerUsuarioConectado().getServidor().getId());
                }

                vacacionServicio.guardarPlanificacionVacacion(
                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                        .getPlanificacionVacacion(),
                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                        .getPlanificacionVacacionDetalle());

                recuperarDatosPlanificacionVacaciones();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

            } else {
                mostrarMensajeEnPantalla(
                        "Debe seleccionar al menos un día en la planificación.",
                        FacesMessage.SEVERITY_ERROR);
            }

        } catch (ServicioException e) {
            e.printStackTrace();
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Se obtiene el saldo de vacaciones pendientes, tanto efectivas como proporcionales asociadas al servidor
     *
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void obtenerSaldosDeVacacionesEfectivasYProporcionales() throws DaoException {
        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO().setSaldoVacacion(0l);
        Servidor s = elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                .getPlanificacionVacacion().getServidor();

        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                obtenerUsuarioConectado().getInstitucion().getId(), s.getId());

        Vacacion v = vacacionDao.buscarPorServidor(si.getId());

        if (v != null) {
            Integer[] saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(v.getSaldo()), s.getJornada());
            elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO().setSaldoVacacionTexto(
                    UtilCadena.concatenar(saldo[0], " Días, ", saldo[1], " Horas, " + saldo[2], " Minutos"));
            elaboracionPlanificacionVacacionesHelper
                    .getPlanificacionVacacionVO().setSaldoVacacion(v.getSaldo());

            Integer[] saldoProporcional = UtilFechas.convertirMinutosA_ddHHmm(
                    Math.abs(v.getSaldoProporcional()), s.getJornada());
            elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                    .setSaldoVacacionProporcionalTexto(UtilCadena.concatenar(
                            saldoProporcional[0], " Días, ", saldoProporcional[1],
                            " Horas, " + saldoProporcional[2], " Minutos"));
            elaboracionPlanificacionVacacionesHelper
                    .getPlanificacionVacacionVO().setSaldoVacacionProporcional(v.getSaldoProporcional());

        }
    }

    /**
     * Se obtiene la cantidad maxima de diasSeleccionados que pueden ser planificables para el servidor de acuerdo a su
     * regimen laboral.
     *
     * @param idRegimenLaboral
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void obtenerMaximoDiasSeleccionables(Long idRegimenLaboral) throws DaoException {
        elaboracionPlanificacionVacacionesHelper.setMaximoDiasSeleccionables(0);
        try {
            List<VacacionParametro> parametrosVacaciones
                    = vacacionServicio.listarTodosVacacionParametroPorRegimenLaboral(idRegimenLaboral);
            int maxDiasSeleccionables = 0;
            if (!parametrosVacaciones.isEmpty()) {
                VacacionParametro vp = parametrosVacaciones.get(0);
                if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.PERIODOS.getCodigo())) {
                    maxDiasSeleccionables = vp.getMaximoAcumulacion() * vp.getNumeroDias();
                } else {
                    maxDiasSeleccionables = vp.getMaximoAcumulacion();
                }
            }
            elaboracionPlanificacionVacacionesHelper.setMaximoDiasSeleccionables(maxDiasSeleccionables);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al intentar obtener el máximo de días seleccionables para vacaciones", e);
        }
    }

    /**
     * LLENA LA LISTA DE SELECCION DE TIPO DE IDENTIFICACION EN EL FORMULARIO DE BUSQUEDA
     */
    private void iniciarComboTipoIdentificacion() {
        iniciarCombos(elaboracionPlanificacionVacacionesHelper.getOpcionesTipoIdentificacion());
        for (TipoDocumentoEnum tDocId : TipoDocumentoEnum.values()) {
            if (!(tDocId.getNemonico().equals(TipoDocumentoEnum.RUC.getNemonico()))) {
                elaboracionPlanificacionVacacionesHelper.getOpcionesTipoIdentificacion().add(
                        new SelectItem(tDocId.getNemonico(), tDocId.getNombre()));
            }
        }
    }

    /**
     * *
     * Busca el registro InstitucionEjercicioFiscal cuyo ejercicio fiscal sea el proximo ejercicio fiscal de actual
     *
     */
    private void buscarInstitucionEjercicioFiscalSiguiente() {
        try {
            InstitucionEjercicioFiscal iefProximo
                    = admServicio.buscarInstitucionEjercFiscalConProximoEjercFiscalPorInstitucion(
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getInstitucion().getId());

            elaboracionPlanificacionVacacionesHelper.setInstitucionEjercicioFiscalAPlanificarVacaciones(iefProximo);
            elaboracionPlanificacionVacacionesHelper.setPeriodoParaPlanificarVacacionesEstaActivo(null);
            if (iefProximo != null) {
                Date fechaIniPeriodoPlanVac = admServicio.buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.FECHA_INICIO_PLANIFICACION_VACACIONES.getCodigo()).getValorFecha();

                Date fechaFinPeriodoPlanVac = admServicio.buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.FECHA_FIN_PLANIFICACION_VACACIONES.getCodigo()).getValorFecha();

                elaboracionPlanificacionVacacionesHelper.setPeriodoParaPlanificarVacacionesEstaActivo(
                        UtilFechas.between(new Date(), fechaIniPeriodoPlanVac, fechaFinPeriodoPlanVac));

                elaboracionPlanificacionVacacionesHelper.setFechaInicioEjercicioFiscal(
                        UtilFechas.formatear(iefProximo.getEjercicioFiscal().getFechaInicio()));

                elaboracionPlanificacionVacacionesHelper.setFechaFinEjercicioFiscal(
                        UtilFechas.formatear(iefProximo.getEjercicioFiscal().getFechaFin()));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar ejercicio fiscal", ex);
        }
    }

    /**
     * Permite obtener la distribucion de puestos para el servidor<p>
     * Con esta información se carga el Ejercicio Fiscal y se obtiene el sueldo(rmu) base sobre el cual se calculan los
     * ingresos del servidor.
     *
     * @return true si existe la configuracion de puestos de trabajo </p>
     */
    private void obtenerParametrizacionVacacion() {
        try {
            if (elaboracionPlanificacionVacacionesHelper
                    .getPlanificacionVacacionVO().getDistributivoDetalle() != null) {
                List<VacacionParametro> listaParametros
                        = vacacionServicio.listarTodosVacacionParametroPorRegimenLaboral(
                                elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                .getDistributivoDetalle().getEscalaOcupacional()
                                .getNivelOcupacional().getRegimenLaboral().getId());
                for (VacacionParametro p : listaParametros) {
                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO().setVacacionParametro(p);
                    break;
                }
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda parametrizacion", ex);
        }
    }

    /**
     * BUSCAR UNNSERVIDOR Y SU PLANIFICACION DE VACACIONES
     *
     * @return String
     */
    public String buscarServidorYPlanificacionVacaciones() {
        try {
            iniciarNuevo();
            elaboracionPlanificacionVacacionesHelper.getListaDistributivosDetalles().clear();
            BusquedaServidorVO bServidor = new BusquedaServidorVO();

            if (elaboracionPlanificacionVacacionesHelper.getTipoIdentificacion() != null
                    && elaboracionPlanificacionVacacionesHelper.getNroIdentificacion() != null) {

                bServidor.setTipoDocumentoServidor(
                        elaboracionPlanificacionVacacionesHelper.getTipoIdentificacion());
                bServidor.setNumeroDocumentoServidor(
                        elaboracionPlanificacionVacacionesHelper.getNroIdentificacion());

            } else if (elaboracionPlanificacionVacacionesHelper.getApellidosNombres() != null) {
                bServidor.setNombreServidor(
                        elaboracionPlanificacionVacacionesHelper.getApellidosNombres());
            }

            elaboracionPlanificacionVacacionesHelper
                    .setListaDistributivosDetalles(servidorServicio.buscar(bServidor));

            ejecutarComandoPrimefaces("dlgResultadosBusqueda.show();");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * SELECCIONAR SERVIDOR DE LA LISTA DE RESULTADOS DE LA BUSQUEDA POR NOMBRES Y APELLDIOS Y MANDA A BSUCAR LOS DATOS
     * DE SU PALNIFICACION DE VACACIONES
     *
     * @return
     */
    public String seleccionarServidorBuscarPlanificacion() {
        if (recuperarDatosPlanificacionVacaciones()) {
            mostrarMensajeEnPantalla(
                    "Registro cargado satisfactoriamente", FacesMessage.SEVERITY_INFO);
        }
        ejecutarComandoPrimefaces("dlgResultadosBusqueda.hide();");
        return null;
    }

    /**
     * RECUPERA LOS DATOS DE LA PLANIFICACION DE VACACIONES ASOCIADA AL SERVIDOR
     *
     * @param servidor
     * @return Boolean
     */
    private Boolean recuperarDatosPlanificacionVacaciones() {
        try {
            Servidor servidor = elaboracionPlanificacionVacacionesHelper.getServidor();
            if (servidor != null) {
                UsuarioVO usuarioConectado = obtenerUsuarioConectado();
                DistributivoDetalle dd = distributivoPuestoServicio.buscarDistributivoPorServidor(
                        servidor.getTipoIdentificacion(),
                        servidor.getNumeroIdentificacion(),
                        usuarioConectado.getEjercicioFiscal().getId());

                if (verificadoServidorPerteneceAMismaUnidadOrg(dd)) {
                    elaboracionPlanificacionVacacionesHelper.setBotonGuardar(Boolean.TRUE);

                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                            .getPlanificacionVacacion().setServidor(servidor);

                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                            .getPlanificacionVacacion().setServidorId(servidor.getId());

                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                            .setFechaIngreso(servidor.getFechaIngresoInstitucion());

                    elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                            .setDistributivoDetalle(dd);

                    obtenerParametrizacionVacacion();

                    List<PlanificacionVacacion> listaPlanVac = vacacionServicio
                            .listarTodosPlanificacionVacacionPorServidorYEjercicioFiscal(
                                    servidor.getId(),
                                    elaboracionPlanificacionVacacionesHelper
                                    .getInstitucionEjercicioFiscalAPlanificarVacaciones().getId());

                    if (!listaPlanVac.isEmpty()) {
                        PlanificacionVacacion pv = listaPlanVac.get(0);
                        pv.setUsuarioActualizacion(usuarioConectado.getServidor().getNumeroIdentificacion());
                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                .setPlanificacionVacacion(pv);

                        List<PlanificacionVacacionDetalle> listaPvDetalles = vacacionServicio
                                .listarTodosPlanificacionVacacionDetallePorIdPlanificacionVacacion(pv.getId());

                        if (!listaPvDetalles.isEmpty()) {
                            elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                    .setPlanificacionVacacionDetalle(listaPvDetalles.get(0));

                            actualizarNroDiasSeleccionadosEnPantalla(elaboracionPlanificacionVacacionesHelper
                                    .getPlanificacionVacacionVO()
                                    .getPlanificacionVacacionDetalle().getNumeroDias());
                        }
                        elaboracionPlanificacionVacacionesHelper.setEsNuevo(Boolean.FALSE);

                    } else {
                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                .getPlanificacionVacacion().setInstitucionEjercicioFiscalId(
                                        elaboracionPlanificacionVacacionesHelper
                                        .getInstitucionEjercicioFiscalAPlanificarVacaciones().getId());

                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                .getPlanificacionVacacion().setInstitucionEjercicioFiscal(
                                        elaboracionPlanificacionVacacionesHelper
                                        .getInstitucionEjercicioFiscalAPlanificarVacaciones());

                        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                                .getPlanificacionVacacionDetalle().setEstado(
                                        EstadoVacacionDetalleEnum.DISPONIBLE.getCodigo());
                        elaboracionPlanificacionVacacionesHelper.setEsNuevo(Boolean.TRUE);
                    }

                    obtenerSaldosDeVacacionesEfectivasYProporcionales();
                    obtenerMaximoDiasSeleccionables(
                            dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId());

                    return Boolean.TRUE;

                } else {
                    mostrarMensajeEnPantalla(
                            "El servidor seleccionado no pertenence a su Unidad Organizacional",
                            FacesMessage.SEVERITY_ERROR);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla(
                    "Error al intentar recuperar la planificación de vacaciones asociada al servidor",
                    FacesMessage.SEVERITY_ERROR);
        }
        return Boolean.FALSE;
    }

    /**
     * Vierifica si un servidor pertenece a la misma unidad organizacional que el usuario conectado
     *
     * @param distributiboDetalleServidor datos del puesto del servidor que contiene la unidad organizacional
     * @return
     */
    private Boolean verificadoServidorPerteneceAMismaUnidadOrg(DistributivoDetalle distributiboDetalleServidor) {
        try {
            List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            for (UnidadOrganizacional unidad : unidades) {
                if (unidad.getCodigo().equals(
                        distributiboDetalleServidor.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                    return Boolean.TRUE;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    /**
     * ACTUALIZA LA VARIABLE QUE ALMACENA EL NUMERO DE DIAS SELECCIONADOS Y QUE SE MUESTRA EN PANTALLA
     *
     * @param nroDias
     */
    private void actualizarNroDiasSeleccionadosEnPantalla(Long nroDias) {
        if (nroDias == 1) {
            elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                    .setNroDiasSeleccionadosTexto(
                            UtilCadena.concatenar(nroDias, " ", " Día"));
//            elaboracionPlanificacionVacacionesHelper.setEsNuevo(Boolean.FALSE);

        } else {
            StringBuilder infoDias = new StringBuilder("");
            if (elaboracionPlanificacionVacacionesHelper
                    .getPlanificacionVacacionVO().getVacacionParametro().getImputarFinSemanaVacacion()) {
                infoDias.append(nroDias + (nroDias / 5) * 2).append(" ").append(" Días");
//                elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
//                        .setNroDiasSeleccionadosTexto(
//                                UtilCadena.concatenar(
//                                        nroDias + (nroDias / 5) * 2, " ", " Días"));
            } else {
                infoDias.append(nroDias).append(" ").append(" Días");
//                elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
//                        .setNroDiasSeleccionadosTexto(
//                                UtilCadena.concatenar(nroDias, " ", " Días"));
            }
            elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                    .setNroDiasSeleccionadosTexto(infoDias.toString());

//            elaboracionPlanificacionVacacionesHelper.setEsNuevo(Boolean.FALSE);
        }
    }

    /**
     * GUARDA LA CANTIDAD DE DIAS SELECCIONADOS EN EL DETALLE DE LA PLANIFICACION
     */
    public void asignarDiasVacacionesServidor() {
        String diasVacaciones = obtenerParametrosFaces().get("seleccionDias");
        elaboracionPlanificacionVacacionesHelper
                .getPlanificacionVacacionVO().getPlanificacionVacacionDetalle()
                .setDiasPlanificados(diasVacaciones.replace(" ", ""));

        Long nroDias = 0L;
        if (!diasVacaciones.trim().isEmpty()) {
            nroDias = new Long(diasVacaciones.split(",").length);
        }

        elaboracionPlanificacionVacacionesHelper.getPlanificacionVacacionVO()
                .getPlanificacionVacacionDetalle().setNumeroDias(nroDias);

        actualizarNroDiasSeleccionadosEnPantalla(nroDias);
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacion(final String codigo) {
        return EstadoPlanVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacionDetalle(final String codigo) {
        return EstadoVacacionDetalleEnum.obtenerDescripcion(codigo);
    }

    /**
     *
     * @return
     */
    public ElaboracionPlanificacionVacacionesHelper getElaboracionPlanificacionVacacionesHelper() {
        return elaboracionPlanificacionVacacionesHelper;
    }

    /**
     *
     * @param elaboracionPlanificacionVacacionesHelper
     */
    public void setElaboracionPlanificacionVacacionesHelper(
            ElaboracionPlanificacionVacacionesHelper elaboracionPlanificacionVacacionesHelper) {
        this.elaboracionPlanificacionVacacionesHelper = elaboracionPlanificacionVacacionesHelper;
    }

    /**
     * Busca las planificaciones de vacaciones segun filtro seleccionado
     */
    public void buscarSalidasVacaciones() {
        try {
            elaboracionPlanificacionVacacionesHelper.getPlanificacionesEncontradasVO().clear();
            List<PlanificacionVacacion> pvs = vacacionServicio.listarTodosPlanificacionVacacionPorEjercicioFiscalYEstado(
                    EstadoPlanVacacionEnum.APROBADO.getCodigo(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            for (PlanificacionVacacion pv : pvs) {
                String[] diasPlanificados = pv.getPlanificacionVacacionDetalles().get(0).getDiasPlanificados().split(",");
                Date diaInicio = df.parse(diasPlanificados[0]);

                if (elaboracionPlanificacionVacacionesHelper.getFiltrarPorYear()) {
                    elaboracionPlanificacionVacacionesHelper.getPlanificacionesEncontradasVO().add(crearPlanificacionVacacionVOParaReporte(pv));
                } else if (UtilFechas.obtenerNumeroRealDelMes(diaInicio)
                        .equals(elaboracionPlanificacionVacacionesHelper.getMesSeleccionado())) {
                    elaboracionPlanificacionVacacionesHelper.getPlanificacionesEncontradasVO().add(crearPlanificacionVacacionVOParaReporte(pv));
                }
            }
            if (elaboracionPlanificacionVacacionesHelper.getPlanificacionesEncontradasVO().isEmpty()) {
                mostrarMensajeEnPantalla("No se encontraron registros", FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ElaboracionPlanificacionVacacionesControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ElaboracionPlanificacionVacacionesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * A partir de PlanificacionVacacion crea el VO de Planificacion Vacacion con los datos necesarios para mostrar
     * resultados de busqueda de servidores con vacaciones planificadas
     *
     * @param pv
     * @return
     */
    public PlanificacionVacacionVO crearPlanificacionVacacionVOParaReporte(PlanificacionVacacion pv) {
        String[] diasPlanificados = pv.getPlanificacionVacacionDetalles().get(0).getDiasPlanificados().split(",");
        PlanificacionVacacionVO pvVO = new PlanificacionVacacionVO();
        pvVO.setPlanificacionVacacion(pv);
        pvVO.setFechaInicioDeVacacion(diasPlanificados[0]);
        pvVO.setFechaFinDeVacacion(diasPlanificados[diasPlanificados.length - 1]);
        return pvVO;
    }

    /**
     * Cuando cambia de radiobutton habilita/deshabilita y limpia el combo de seleccionar mes para el filtro
     */
    public void mostrarComboMeses() {
        elaboracionPlanificacionVacacionesHelper.setMesSeleccionado(null);
        elaboracionPlanificacionVacacionesHelper.getPlanificacionesEncontradasVO().clear();
        actualizarComponente("frm_reportePlanificaciones:mesId");
        actualizarComponente("frm_reportePlanificaciones:tblPlanificaciones");
    }
}
