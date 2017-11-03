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
import ec.com.atikasoft.proteus.controlador.helper.LiquidacionVacacionesHelper;
import ec.com.atikasoft.proteus.dao.CatalogoDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.VacacionSolicitudLiquidacionDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadosPersonalEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudLiquidacion;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "liquidacionVacacionesBean")
@ViewScoped
public class LiquidacionVacacionesControlador extends BaseControlador {

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/liquidacion_vacaciones/lista_liquidacion_vacaciones.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORM_NUEVA_LIQUIDACION = "/pages/procesos/vacacion/liquidacion_vacaciones/nueva_liquidacion_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FOR_DATOS_LIQUIDACION = "/pages/procesos/vacacion/liquidacion_vacaciones/ver_liquidacion_vacacion.jsf";

    /**
     *
     */
    private StreamedContent archivoPdf;
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     *
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;
    /**
     *
     */
    @EJB
    private VacacionSolicitudLiquidacionDao vacacionSolicitudLiquidacionDao;
    /**
     *
     */
    @EJB
    UnidadOrganizacionalDao unidadOrgDao;
    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private CatalogoDao catalogoDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{liquidacionVacacionesHelper}")
    private LiquidacionVacacionesHelper liquidacionVacacionesHelper;

    /**
     * Constructor por defecto.
     */
    public LiquidacionVacacionesControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        liquidacionVacacionesHelper.setBuscarServidorPor(null);
        iniciarFormularioBusqueda();
    }

    /**
     * INCIACIALIZA LAS VARIABLES ASOCIADAS A LA PLANIFICACION DE VACACIONES
     *
     */
    public void iniciarFormularioBusqueda() {
        liquidacionVacacionesHelper.iniciador();
        limpiarDatosPuesto();
        iniciarDatosAccionPersonal();
        iniciarComboTipoIdentificacion();
        iniciarComboAutoridadNominadora();
        iniciarComboRepresentanteRRHH();
        if (liquidacionVacacionesHelper.getBuscarServidorPor() == null) {
            liquidacionVacacionesHelper.setApellidosNombres(null);
            liquidacionVacacionesHelper.setTipoIdentificacion(null);
            liquidacionVacacionesHelper.setNroIdentificacion(null);
        } else if (liquidacionVacacionesHelper.getBuscarServidorPor().equals("id")) {
            liquidacionVacacionesHelper.setApellidosNombres(null);
        } else {
            liquidacionVacacionesHelper.setTipoIdentificacion(null);
            liquidacionVacacionesHelper.setNroIdentificacion(null);
        }
        liquidacionVacacionesHelper.getListaLiquidaciones().clear();
    }

    /**
     *
     * @return
     */
    public String iniciarEdicion() {
        String urlDestino;
        if (liquidacionVacacionesHelper.getEsNuevo()) {
            liquidacionVacacionesHelper.setLiquidacionSeleccionada(new VacacionSolicitudLiquidacion());
            urlDestino = FORM_NUEVA_LIQUIDACION;
        } else {
            urlDestino = FOR_DATOS_LIQUIDACION;
        }
        iniciarDatosEntidad(
                liquidacionVacacionesHelper.getLiquidacionSeleccionada(), liquidacionVacacionesHelper.getEsNuevo());

        return urlDestino;
    }

    /**
     * LIMPIA EL CAMPO NRO IDENTIFICACION
     */
    public void limpiarNroIdentificacion() {
        liquidacionVacacionesHelper.setNroIdentificacion(null);
        limpiarDatosPuesto();
    }

    /**
     *
     */
    public void limpiarDatosPuesto() {
        liquidacionVacacionesHelper.setVacacion(null);
        liquidacionVacacionesHelper.setDistributivoDetalleSeleccionado(null);
        liquidacionVacacionesHelper.setServidor(null);
    }

    /**
     *
     */
    private void iniciarDatosAccionPersonal() {
        liquidacionVacacionesHelper.setCodigoTipoDocPrevio(null);
        liquidacionVacacionesHelper.setNumeroDocumentoPrevio(null);
        liquidacionVacacionesHelper.setFechaDocPrevio(null);
        String explicacionLiquidacion = obtenerUsuarioConectado().getDistributivoDetalle()
                .getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getExplicacionLiquidacionVacacion();
        liquidacionVacacionesHelper.setExplicacionLiquidacionVacacion(explicacionLiquidacion);
    }

    /**
     * LLENA LA LISTA DE SELECCION DE TIPO DE IDENTIFICACION EN EL FORMULARIO DE BUSQUEDA
     */
    private void iniciarComboTipoIdentificacion() {
        iniciarCombos(liquidacionVacacionesHelper.getOpcionesTipoIdentificacion());
        for (TipoDocumentoEnum tDocId : TipoDocumentoEnum.values()) {
            if (!(tDocId.getNemonico().equals(TipoDocumentoEnum.RUC.getNemonico()))) {
                liquidacionVacacionesHelper.getOpcionesTipoIdentificacion().add(
                        new SelectItem(tDocId.getNemonico(), tDocId.getNombre()));
            }
        }
    }

    /**
     *
     */
    private void iniciarComboAutoridadNominadora() {
        try {
            // lista de autoridades nominadoras
            iniciarCombos(liquidacionVacacionesHelper.getListaAutoridadNominadora());
            List<Catalogo> listaAutoridades = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.AUTORIDAD_NOMINADORA.getCodigo());
            for (Catalogo c : listaAutoridades) {
                liquidacionVacacionesHelper.getListaAutoridadNominadora().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Error al iniciar combo de autoridad nominadora", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     *
     */
    private void iniciarComboRepresentanteRRHH() {
        try {
            // lista de representantes de rrhh
            iniciarCombos(liquidacionVacacionesHelper.getListaRepresentanteRRHH());
            List<Catalogo> listaRepresentantes = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.REPRESENTANTE_RRHH.getCodigo());
            for (Catalogo c : listaRepresentantes) {
                liquidacionVacacionesHelper.getListaRepresentanteRRHH().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Error al iniciar combo de autoridad nominadora", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * BUSCAR SERVIDOR
     *
     * @return String
     */
    public String buscarServidor() {
        try {
            iniciarFormularioBusqueda();
            liquidacionVacacionesHelper.getListaDistributivosDetalles().clear();
            liquidacionVacacionesHelper.getListaServidores().clear();

            if (liquidacionVacacionesHelper.getTipoIdentificacion() != null
                    && liquidacionVacacionesHelper.getNroIdentificacion() != null) {

                liquidacionVacacionesHelper.getListaServidores().add(servidorServicio.buscarServidor(
                        liquidacionVacacionesHelper.getTipoIdentificacion(),
                        liquidacionVacacionesHelper.getNroIdentificacion()));

            } else if (liquidacionVacacionesHelper.getApellidosNombres() != null) {
                liquidacionVacacionesHelper.getListaServidores().addAll(
                        servidorServicio.buscarServidorPorNombre(liquidacionVacacionesHelper.getApellidosNombres()));
            }

            ejecutarComandoPrimefaces("dlgResultadosBusqueda.show();");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla("Error al procesar la búsqueda", FacesMessage.SEVERITY_ERROR);
        }

        return null;
    }

    /**
     * SELECCIONAR SERVIDOR DE LA LISTA DE RESULTADOS DE LA BUSQUEDA POR NOMBRES Y APELLDIOS Y MANDA A BUSCAR LOS DATOS
     * DEL ULTIMO PUESTO DONDE ESTUVO
     *
     * @return
     */
    public String seleccionarServidorBuscarSuUltimoPuesto() {
        if (recuperarDatosUltimoPuestoServidor()) {
            mostrarMensajeEnPantalla(
                    "Registro cargado satisfactoriamente", FacesMessage.SEVERITY_INFO);
        }
        ejecutarComandoPrimefaces("dlgResultadosBusqueda.hide();");
        return null;
    }

    /**
     * RECUPERA LOS DATOS DEL ULTIMO PUESTO OCUPADO POR EL SERVIDOR
     *
     * @param servidor
     * @return Boolean
     */
    private Boolean recuperarDatosUltimoPuestoServidor() {
        try {
            liquidacionVacacionesHelper.setBotonProcesar(Boolean.FALSE);
            Servidor s = liquidacionVacacionesHelper.getServidor();
            boolean servidorVacante = false;
            if (s != null) {
                DistributivoDetalle dd;
                if (s.getEstadoPersonal().getCodigo().equals(EstadosPersonalEnum.INACTIVO.getCodigo())) {
                    dd = distributivoPuestoServicio.recuperarUltimoDistributivoDetalleOcupadoPorServidor(
                            s.getTipoIdentificacion(), s.getNumeroIdentificacion());
                    servidorVacante = true;
                } else {
                    dd = distributivoPuestoServicio.buscarDistributivoPorServidor(
                            s.getTipoIdentificacion(), s.getNumeroIdentificacion(),
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
                    mostrarMensajeEnPantalla(
                            "El servidor aún está activo. No es posible realizar la liquidación de vacaciones",
                            FacesMessage.SEVERITY_ERROR);
                }

                if (dd != null) {
                    if (verificadoServidorPerteneceAMismaUnidadOrg(dd)) {
                        liquidacionVacacionesHelper.setDistributivoDetalleSeleccionado(dd);
                        Vacacion v = vacacionServicio.buscarVacacionPorServidorId(s.getId());
                        if (v != null) {
                            liquidacionVacacionesHelper.setVacacion(v);

                            actualizarSaldosAMostrar();

                            if (v.getSaldo() + v.getSaldoProporcional() > 0) {
                                String explicacionLiquidacion = liquidacionVacacionesHelper.getExplicacionLiquidacionVacacion();
                                Long saldoTotalInicial = liquidacionVacacionesHelper.getSaldoVacacion()
                                        + liquidacionVacacionesHelper.getSaldoVacacionProporcional();
                                SimpleDateFormat sdf = new SimpleDateFormat(UtilFechas.FORMATO_FECHA);
                                explicacionLiquidacion = explicacionLiquidacion.replaceAll(
                                        "#fecha_inicio", "" + sdf.format(s.getFechaIngresoInstitucion()));

                                Date fechaSalida = s.getFechaSalida();
                                if (fechaSalida != null) {
                                    explicacionLiquidacion = explicacionLiquidacion.replaceAll(
                                            "#fecha_final", "" + sdf.format(fechaSalida));
                                } else {
                                    explicacionLiquidacion = explicacionLiquidacion.replaceAll(" AL #fecha_final", "");
                                }

                                explicacionLiquidacion = explicacionLiquidacion.replaceAll("#saldo_vacacion", String.valueOf(
                                        UtilFechas.convertirMinutosA_ddHHmmPalabras(saldoTotalInicial, s.getJornada())));

                                liquidacionVacacionesHelper.setExplicacionLiquidacionVacacion(explicacionLiquidacion);
                                if (servidorVacante) {
                                    liquidacionVacacionesHelper.setBotonProcesar(Boolean.TRUE);
                                }
                            }

                        } else {
                            mostrarMensajeEnPantalla(
                                    "No se encontraron datos de vacaciones asociadas al servidor.",
                                    FacesMessage.SEVERITY_ERROR);
                        }

                    } else {
                        liquidacionVacacionesHelper.setDistributivoDetalleSeleccionado(null);
                        liquidacionVacacionesHelper.setServidor(null);
                        mostrarMensajeEnPantalla(
                                "El servidor seleccionado no pertenece a su Unidad Organizacional",
                                FacesMessage.SEVERITY_ERROR);
                    }
                } else {
                    liquidacionVacacionesHelper.setDistributivoDetalleSeleccionado(null);
                    liquidacionVacacionesHelper.setServidor(null);
                    mostrarMensajeEnPantalla(
                            "El servidor no tiene un historial de servicio en la Institución. "
                            + "No es posible realizar la liquidación de vacaciones",
                            FacesMessage.SEVERITY_ERROR);
                }
            }

        } catch (Exception e) {
            error(getClass().getName(),
                    "Error al intentar recuperar los datos del último puesto ocupado por el servidor", e);
        }
        return Boolean.FALSE;
    }

    /**
     * PREPARA LOS DATOS DE SALDO DE VACACIONES A MOSTRAR EN LA VISTA
     */
    private void actualizarSaldosAMostrar() {
        Vacacion v = liquidacionVacacionesHelper.getVacacion();
        Servidor s = liquidacionVacacionesHelper.getServidor();
        Integer[] saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(v.getSaldo()), s.getJornada());

        liquidacionVacacionesHelper.setSaldoVacacionTexto(
                UtilCadena.concatenar(saldo[0], " Días, ", saldo[1], " Horas, " + saldo[2], " Minutos"));
        liquidacionVacacionesHelper.setSaldoVacacion(v.getSaldo());

        Integer[] saldoProporcional = UtilFechas.convertirMinutosA_ddHHmm(
                Math.abs(v.getSaldoProporcional()), s.getJornada());

        liquidacionVacacionesHelper.setSaldoVacacionProporcionalTexto(UtilCadena.concatenar(
                saldoProporcional[0], " Días, ", saldoProporcional[1],
                " Horas, " + saldoProporcional[2], " Minutos"));
        liquidacionVacacionesHelper.setSaldoVacacionProporcional(v.getSaldoProporcional());
    }

    /**
     * Verifica si un s pertenece a la misma unidad organizacional que el usuario conectado
     *
     * @param distributiboDetalleServidor datos del puesto del s que contiene la unidad organizacional
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
     * SELECCIONAR SERVIDOR DE LA LISTA DE RESULTADOS DE LA BUSQUEDA POR NOMBRES Y APELLDIOS Y MANDA A BUSCAR LOS DATOS
     * DE LAS LIQUIDACIONES QUE SE LE HAN REALIZADO
     *
     * @return
     */
    public String seleccionarServidorBuscarSusLiquidaciones() {
        try {
            Servidor s = liquidacionVacacionesHelper.getServidor();
            UsuarioVO usuarioConectado = obtenerUsuarioConectado();
            DistributivoDetalle dd;
            if (s.getEstadoPersonal().getCodigo().equals(EstadosPersonalEnum.INACTIVO.getCodigo())) {
                dd = distributivoPuestoServicio.recuperarUltimoDistributivoDetalleOcupadoPorServidor(
                        s.getTipoIdentificacion(), s.getNumeroIdentificacion());
            } else {
                dd = distributivoPuestoServicio.buscarDistributivoPorServidor(
                        s.getTipoIdentificacion(), s.getNumeroIdentificacion(),
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            }

            liquidacionVacacionesHelper.setDistributivoDetalleSeleccionado(dd);
            Vacacion vacacion = vacacionServicio.buscarVacacionPorServidorId(s.getId());
            if (vacacion != null) {
                liquidacionVacacionesHelper.setVacacion(vacacion);
                actualizarSaldosAMostrar();

                ServidorInstitucion siServ = servidorServicio.buscarServidorInstitucion(
                        usuarioConectado.getDistributivoDetalle().getDistributivo()
                        .getInstitucionEjercicioFiscal().getInstitucion().getId(), s.getId());

                liquidacionVacacionesHelper.getListaLiquidaciones().clear();
                liquidacionVacacionesHelper.setListaLiquidaciones(
                        vacacionSolicitudLiquidacionDao.buscarPorServidorInstitucion(siServ.getId()));

            } else {
                mostrarMensajeEnPantalla(
                        "No se encontraron datos de vacaciones asociadas al servidor.",
                        FacesMessage.SEVERITY_ERROR);
            }

            ejecutarComandoPrimefaces("dlgResultadosBusqueda.hide();");

        } catch (Exception e) {
            error(getClass().getName(),
                    "Error al intentar recuperar las liquidaciones de vacaciones asociadas al servidor seleccionado", e);
        }

        return null;
    }

    /**
     * EJECUTAR LA LIQUIDACIÓN DE VACACIONES DEL SERVIDOR
     *
     * @return
     */
    public String ejecutarLiquidacion() {
        try {
            if (!servidorTieneSolicitudesVacacionesPendiente()) {
                DistributivoDetalle dd = liquidacionVacacionesHelper.getDistributivoDetalleSeleccionado();
                Servidor s = liquidacionVacacionesHelper.getServidor();
                UsuarioVO usuarioContectado = obtenerUsuarioConectado();

                ServidorInstitucion siServidor = servidorServicio.buscarServidorInstitucion(
                        dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId(), s.getId());

                ServidorInstitucion siServElaborador = servidorServicio.buscarServidorInstitucion(
                        usuarioContectado.getInstitucion().getId(), usuarioContectado.getServidor().getId());

                Catalogo representanteRRHH = catalogoDao.buscarPorId(liquidacionVacacionesHelper.
                        getRepresentanteRRHHId());
                Catalogo autoridadNominadora = catalogoDao.buscarPorId(liquidacionVacacionesHelper.
                        getAutoridadNominadoraId());

                vacacionServicio.generarPdfAccionPersonalLiquidacionVacacion(
                        s, dd, liquidacionVacacionesHelper.getVacacion(), siServidor.getId(), siServElaborador.getId(),
                        liquidacionVacacionesHelper.getExplicacionLiquidacionVacacion(),
                        liquidacionVacacionesHelper.getCodigoTipoDocPrevio(),
                        liquidacionVacacionesHelper.getNumeroDocumentoPrevio(),
                        liquidacionVacacionesHelper.getFechaDocPrevio(),
                        representanteRRHH.getNombre(),
                        liquidacionVacacionesHelper.getNombreRepresentanteRRHH(),
                        autoridadNominadora.getNombre(),
                        liquidacionVacacionesHelper.getNombreAutoridadNominadora(),
                        usuarioContectado);

                VacacionSolicitudLiquidacion vsl = vacacionSolicitudLiquidacionDao.buscarPorServidorId(s.getId()).get(0);

                this.archivoPdf = new DefaultStreamedContent(
                        new ByteArrayInputStream(vsl.getArchivoAccioPersonal().getArchivo()),
                        "application/pdf", vsl.getArchivoAccioPersonal().getNombre());

                ejecutarComandoPrimefaces("dlgDatosAccionPersonal.hide();");
                iniciarDatosAccionPersonal();
                ejecutarComandoPrimefaces("dlgLiquidacionExitosa.show();");
                recuperarDatosUltimoPuestoServidor();
            } else {
                mostrarMensajeEnPantalla(
                        "El servidor tiene solicitudes de vacaciones pendientes", FacesMessage.SEVERITY_ERROR);
            }

        } catch (ServicioException e) {
            error(getClass().getName(), "Error al ejecutar la liquidación de vacaciones", e);
        } catch (DaoException e) {
            error(getClass().getName(), "Error al intentar recuperar los datos de la liquidación realizada", e);
        }
        return null;
    }

    /**
     * VERIFICA SI EL SERVIDOR SELECCIONADO TIENE SOLICITUDES DE VACACIONES PENDIENTES
     *
     * @return
     */
    private Boolean servidorTieneSolicitudesVacacionesPendiente() {
        try {
            List<VacacionSolicitud> lvs = vacacionServicio.listarTodosVacacionSolicitudPorServidor(
                    liquidacionVacacionesHelper.getServidor().getId());
            for (VacacionSolicitud vs : lvs) {
                if (vs.getEstado().equals(EstadoVacacionEnum.REGISTRADO.getCodigo())
                        || vs.getEstado().equals(EstadoVacacionEnum.VALIDADO.getCodigo())) {
                    return Boolean.TRUE;
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error verificando si el servidor tiene solicitudes de vacaciones pendientes.", e);
        }
        return Boolean.FALSE;
    }

    /**
     *
     * @param archivo
     * @return
     */
    public StreamedContent descargarArchivo(Archivo archivo) {
        try {
            return new DefaultStreamedContent(
                    new ByteArrayInputStream(archivo.getArchivo()), "application/pdf", archivo.getNombre());
        } catch (Exception e) {
            error(getClass().getName(), "Error al descargar archivo", e);
        }
        return null;
    }

    /**
     * REVIERTE LA LIQUIDCIÓN DE VACACIONES
     *
     * @return
     */
    public String revertirLiquidacionVacacion() {
        try {
            if (verificadoServidorPerteneceAMismaUnidadOrg(
                    liquidacionVacacionesHelper.getDistributivoDetalleSeleccionado())) {
                UsuarioVO usuario = obtenerUsuarioConectado();
                VacacionSolicitudLiquidacion vsl = liquidacionVacacionesHelper.getLiquidacionSeleccionada();
                Vacacion vacacion = liquidacionVacacionesHelper.getVacacion();
                DistributivoDetalle dd = liquidacionVacacionesHelper.getDistributivoDetalleSeleccionado();
                Servidor s = liquidacionVacacionesHelper.getServidor();

                vacacionServicio.revertirLiquidacionVacaciones(vacacion, dd, s, vsl, usuario);
                seleccionarServidorBuscarSusLiquidaciones();

                mostrarMensajeEnPantalla(
                        "Reversión de liquidación de vacaciones realizada satisfactoriamente",
                        FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(
                        "No es posible realizar la liquidación de vacaciones. "
                        + "El servidor no pertenece a su Unidad Organizacional.",
                        FacesMessage.SEVERITY_ERROR);
            }

            ejecutarComandoPrimefaces("dlgReversionLiquidacion.hide();");

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar revertir la liquidación de vacaciones", e);
        }
        return null;
    }

    /**
     * AGREGA EL SIMBOLO $ AL RMU
     *
     * @param rmu
     * @return
     */
    public String rmuTexto(Long rmu) {
        if (rmu == null) {
            return "$0.00";
        }
        return UtilCadena.concatenar("$", String.valueOf(rmu));
    }

    /**
     * Permite Ir a la pagina principal
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String regresar() {
        return LISTA_ENTIDAD;
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
    public StreamedContent getArchivoPdf() {
        return archivoPdf;
    }

    /**
     *
     * @param archivoPdf
     */
    public void setArchivoPdf(StreamedContent archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    /**
     *
     * @return
     */
    public LiquidacionVacacionesHelper getLiquidacionVacacionesHelper() {
        return liquidacionVacacionesHelper;
    }

    /**
     *
     * @param liquidacionVacacionesHelper
     */
    public void setLiquidacionVacacionesHelper(LiquidacionVacacionesHelper liquidacionVacacionesHelper) {
        this.liquidacionVacacionesHelper = liquidacionVacacionesHelper;
    }

}
