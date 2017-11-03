package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Validacion;
import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import ec.com.atikasoft.proteus.enums.HorarioConfiguracionTipoMovimientoEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.TipoHorarioEnum;
import ec.com.atikasoft.proteus.enums.NivelEstudioHijoEnLicenciaEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.PeriodoDevengarEnum;
import ec.com.atikasoft.proteus.enums.TipoNacimientoEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.TipoSolicitudEnum;
import ec.com.atikasoft.proteus.enums.ObligatorioEnum;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.RegistroSolicitudHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.servicio.ValidacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ErroresVO;
import ec.com.atikasoft.proteus.vo.ValidacionTipoMovimientoRequisitoVO;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "registroSolicitudBean")
@ViewScoped
public class RegistroSolicitudControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(
            ParametroInstitucionCatalogoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/solicitudes/nueva_solicitud.jsf";

    /**
     * Regla de navegación.
     */
    public static final String SOLICITUD_ENVIADA = "/pages/solicitudes/solicitud_enviada.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{registroSolicitudHelper}")
    private RegistroSolicitudHelper registroSolicitudHelper;

    /**
     * clase Administracion servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * clase Tramite servicio.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * clase Validacion servicio.
     */
    @EJB
    private ValidacionServicio validacionServicio;

    /**
     * Constructor.
     */
    public RegistroSolicitudControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarComboTipo();
        iniciarComboTipoNacimiento();
        iniciarComboDevengar();
        iniciarComboPeriodoDevengar();
        registroSolicitudHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        iniciarCombos(registroSolicitudHelper.getListaTipoMovimiento());
        registroSolicitudHelper.setTipoSolicitudSeleccionado("");
        registroSolicitudHelper.getListaValidacionTipoMovimientoRequisitoVO().clear();
        registroSolicitudHelper.getListaErroresVO().clear();
        registroSolicitudHelper.setMostrarTablaErrores(Boolean.FALSE);
        registroSolicitudHelper.setDetalle(UtilCadena.concatenar("Yo, ",
                obtenerNombreUsuario(), " con cédula de Identidad ",
                obtenerUsuario(), " solicito ..."));
    }

    /**
     * Este metodo llena las opciones para la seleccion de tipo de solicitud.
     */
    public void iniciarComboTipo() {
        registroSolicitudHelper.getTipoSolicitud().clear();
        registroSolicitudHelper.getNivelEstudio().clear();
        registroSolicitudHelper.setTipoMovimientoId(0L);
        registroSolicitudHelper.setTipoMovimiento(new TipoMovimiento());
        registroSolicitudHelper.setHorario(Boolean.FALSE);
        registroSolicitudHelper.setAreaDevengar(Boolean.FALSE);
        registroSolicitudHelper.setDevengar(Boolean.FALSE);
        registroSolicitudHelper.setObligatorio(Boolean.FALSE);
        registroSolicitudHelper.setLectura(Boolean.TRUE);
        registroSolicitudHelper.setValidacionFechas(Boolean.FALSE);
        registroSolicitudHelper.setLicencia(new Licencia());
        registroSolicitudHelper.getLicencia().setMovimiento(new Movimiento());
        registroSolicitudHelper.setMovimiento(new Movimiento());
        registroSolicitudHelper.getListaLicenciaHorario().clear();
        for (TipoSolicitudEnum t : TipoSolicitudEnum.values()) {
            getRegistroSolicitudHelper().getTipoSolicitud().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones de nivles de estudio en el área de permiso de matriculas de hijos.
     */
    public void iniciarComboNivelEstudio() {
        for (NivelEstudioHijoEnLicenciaEnum t : NivelEstudioHijoEnLicenciaEnum.values()) {
            getRegistroSolicitudHelper().getNivelEstudio().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
    }

    /**
     * Metodo que se encarga de validar si el requisito tiene calificacion para activar el campo de calificacion.
     *
     * @param vtmrvo ValidacionTipoMovimientoRequisitoVO
     * @return Boolean para saber si activa o no el campo
     */
    public Boolean validarRequisitoTieneCalificacion(final ValidacionTipoMovimientoRequisitoVO vtmrvo) {
        return vtmrvo.getTipoMovimientoRequisito().getRequisito().getTieneCalificacion();
    }

    /**
     * Este metodo llena las opciones para la seleccion de tipo de nacimiento.
     */
    public void iniciarComboTipoNacimiento() {
        getRegistroSolicitudHelper().getListaTipoNacimiento().clear();
        iniciarCombos(registroSolicitudHelper.getListaTipoNacimiento());
        for (TipoNacimientoEnum tn : TipoNacimientoEnum.values()) {
            getRegistroSolicitudHelper().getListaTipoNacimiento().
                    add(new SelectItem(tn.getCodigo(), tn.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para la seleccion devengar.
     */
    public void iniciarComboDevengar() {
        getRegistroSolicitudHelper().getListaDevengar().clear();
        iniciarCombos(registroSolicitudHelper.getListaDevengar());
        for (ObligatorioEnum o : ObligatorioEnum.values()) {
            getRegistroSolicitudHelper().getListaDevengar().
                    add(new SelectItem(o.getCodigo(), o.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para la seleccion periodo devengar.
     */
    public void iniciarComboPeriodoDevengar() {
        getRegistroSolicitudHelper().getListaPeridoDevengar().clear();
        iniciarCombos(registroSolicitudHelper.getListaPeridoDevengar());
        for (PeriodoDevengarEnum pd : PeriodoDevengarEnum.values()) {
            getRegistroSolicitudHelper().getListaPeridoDevengar().
                    add(new SelectItem(pd.getCodigo(), pd.getDescripcion()));
        }
    }

    /**
     * Método para habilitar la opcion de observacion en el area de tiempo a devengar.
     */
    public void habilitarDevengar() {
        if (registroSolicitudHelper.getDevengar().equals(ObligatorioEnum.SI.getCodigo())) {
            if (registroSolicitudHelper.getTipoMovimiento().getObservacionDevengar().
                    equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
                registroSolicitudHelper.setObligatorio(Boolean.TRUE);
                registroSolicitudHelper.setLectura(Boolean.FALSE);
            } else if (registroSolicitudHelper.getTipoMovimiento().getObservacionDevengar().
                    equals(CamposConfiguracionEnum.NO_OBLIGATORIO.getCodigo())) {
                registroSolicitudHelper.setObligatorio(Boolean.FALSE);
                registroSolicitudHelper.setLectura(Boolean.FALSE);
            } else {
                registroSolicitudHelper.setObligatorio(Boolean.FALSE);
                registroSolicitudHelper.setLectura(Boolean.TRUE);
            }
        } else {
            registroSolicitudHelper.setObligatorio(Boolean.FALSE);
            registroSolicitudHelper.setLectura(Boolean.TRUE);
        }
    }

    /**
     * método para setear el disabled de los campos.
     *
     * @param valor String
     * @return retorno Boolean
     */
    public Boolean setearLectura(final String valor) {
        Boolean retorno = Boolean.FALSE;
        if (valor.equals(CamposConfiguracionEnum.SOLO_LECTURA.getCodigo())) {
            retorno = Boolean.TRUE;
        }
        return retorno;
    }

    /**
     * método para setear el requerido de los campos.
     *
     *
     * @param valor String
     * @return retorno Boolean
     */
    public Boolean setearObligatorio(final String valor) {
        Boolean retorno = Boolean.FALSE;
        if (valor.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            retorno = Boolean.TRUE;
        } else if (valor.equals(CamposConfiguracionEnum.NO_OBLIGATORIO.getCodigo())) {
            retorno = Boolean.FALSE;
        }
        return retorno;
    }

    /**
     * Metodo que se encarga de validar el boolean recibido como parametro para retornar el String si o no.
     *
     * @param presentaServidorPublico Boolean a evaluar
     * @return String
     */
    public String verificarPresentaServidorPublico(final Boolean presentaServidorPublico) {
        String resultado;
        if (presentaServidorPublico) {
            resultado = ObligatorioEnum.SI.getDescripcion();
        } else {
            resultado = ObligatorioEnum.NO.getDescripcion();
        }
        return resultado;
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo de movimientos.
     */
    public void llenarComboTipoMovimiento() {
        try {
            registroSolicitudHelper.getListaTipoMovimiento().clear();
            iniciarCombos(registroSolicitudHelper.getListaTipoMovimiento());
            registroSolicitudHelper.setTipoMovimientoId(0L);
            registroSolicitudHelper.getListaValidacionTipoMovimientoRequisitoVO().clear();
            registroSolicitudHelper.setMostrarTablaErrores(Boolean.FALSE);
            registroSolicitudHelper.setNumerohorasMatriculasHijo(0L);
            registroSolicitudHelper.getNivelEstudio().clear();
            registroSolicitudHelper.setHorario(Boolean.FALSE);
            registroSolicitudHelper.setAreaDevengar(Boolean.FALSE);
            registroSolicitudHelper.setDevengar(Boolean.FALSE);
            registroSolicitudHelper.setTipoMovimiento(new TipoMovimiento());
            registroSolicitudHelper.getListaLicenciaHorario().clear();
            List<TipoMovimiento> listaTipoMovimiento;
          /*  if (registroSolicitudHelper.getTipoSolicitudSeleccionado().
                    equals(TipoSolicitudEnum.LICENCIAS.getCodigo())) {
                listaTipoMovimiento = administracionServicio.listarTiposDeMovimientosPorModalidadLaboralConsolicitud(
                        GrupoEnum.LICENCIAS.getCodigo(), obtenerUsuarioConectado().getDistributivoDetalle().
                        getDistributivo().getModalidadNivelOcupacional().getModalidadLaboral().getId());
            } else {
                listaTipoMovimiento = administracionServicio.listarTiposDeMovimientosPorModalidadLaboralConsolicitud(
                        GrupoEnum.PERMISOS.getCodigo(), obtenerUsuarioConectado().getDistributivoDetalle().
                        getDistributivo().getModalidadNivelOcupacional().getModalidadLaboral().getId());
            }
            for (TipoMovimiento g : listaTipoMovimiento) {
                registroSolicitudHelper.getListaTipoMovimiento().add(new SelectItem(g.getId(), g.getNombre()));
            }*/  //comentado x MDMQDES-40 nov-2013
            actualizarComponente(
                    "areaDevengar, areaMaternidad,areaRepresentacionAsociacion,"
                    + "horasMensuales, areaPermisoMatriculaHijos, horarios");
        } catch (Exception ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los requisitos por tipo de movimientos mediante el id de tipo de movimiento.
     */
    public void cargarListaRequisitosPorTipoMovimiento() {
        Long tipoMovimientoId = registroSolicitudHelper.getTipoMovimientoId();
        try {
            registroSolicitudHelper.setNumerohorasMatriculasHijo(0L);
            registroSolicitudHelper.getListaLicenciaHorario().clear();
            registroSolicitudHelper.setHorario(Boolean.FALSE);
            registroSolicitudHelper.setAreaDevengar(Boolean.FALSE);
            registroSolicitudHelper.setDevengar(Boolean.FALSE);
            registroSolicitudHelper.setValidacionFechas(Boolean.FALSE);
            registroSolicitudHelper.setTipoMovimiento(
                    administracionServicio.buscarTipoMovimientoPorId(tipoMovimientoId));
            registroSolicitudHelper.setMostrarTablaErrores(Boolean.FALSE);
            registroSolicitudHelper.getNivelEstudio().clear();
            List<TipoMovimientoRequisito> listaTipoMovimientoRequisito =
                    administracionServicio.listarTipoMovimientoRequisitoPorTipoMovimientoIdServidorPublico(
                    tipoMovimientoId);
            registroSolicitudHelper.getListaValidacionTipoMovimientoRequisitoVO().clear();
            for (TipoMovimientoRequisito tmr : listaTipoMovimientoRequisito) {
                ValidacionTipoMovimientoRequisitoVO vtmrvo = new ValidacionTipoMovimientoRequisitoVO();
                vtmrvo.setValidacion(new Validacion());
                //vtmrvo.setArchivo(new Archivo());
                vtmrvo.setTipoMovimientoRequisito(tmr);
                registroSolicitudHelper.getListaValidacionTipoMovimientoRequisitoVO().add(vtmrvo);
            }
            registroSolicitudHelper.setTipoMovimiento(
                    administracionServicio.buscarTipoMovimientoPorId(tipoMovimientoId));
            registroSolicitudHelper.setAreaDevengar(registroSolicitudHelper.getTipoMovimiento().
                    getAreaTiempoPorDevengar());
            if (registroSolicitudHelper.getTipoMovimiento().getHorario() != null) {
                registroSolicitudHelper.setHorario(registroSolicitudHelper.getTipoMovimiento().
                        getAreaConfiguracionLicenciasPermisos());
            }
            iniciarComboNivelEstudio();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "Error al cargar a tabla de requisitos por tipo de movimiento " + ex);
        }
    }

    /**
     * Metodo que se encarga de validar el boolean recibido para retornar un Si o No y mostrar en la lista.
     *
     * @param obligatorio parametro a evaluar
     * @return String resultado
     */
    public String verificarObligatorio(final Boolean obligatorio) {
        String resultado;
        if (obligatorio) {
            resultado = ObligatorioEnum.SI.getDescripcion();
        } else {
            resultado = ObligatorioEnum.NO.getDescripcion();
        }
        return resultado;
    }

    /**
     * Metodo para validar el tiempo solicitado de licencia.
     */
    public void validarTiempo(final List<ErroresVO> err) {
        registroSolicitudHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        StringBuilder error = new StringBuilder();
        registroSolicitudHelper.setFecha_desde(registroSolicitudHelper.getLicencia().
                getMovimiento().getRigeApartirDe());
        registroSolicitudHelper.setFecha_hasta(registroSolicitudHelper.getLicencia().getMovimiento().getFechaHasta());
        if (registroSolicitudHelper.getFecha_desde() == null
                || registroSolicitudHelper.getFecha_hasta() == null) {
            err.add(tramiteServicio.registroError("Tiempo Solicitado", "fecha de inicio y fecha de finalización",
                    "j_idt54:fecha_inicio",
                    "Debe Ingresar las Fechas de inicio y Hasta para poder realizar la validación."));
        } else if (registroSolicitudHelper.getFecha_desde().getTime()
                > registroSolicitudHelper.getFecha_hasta().getTime()) {
            err.add(tramiteServicio.registroError("Tiempo Solicitado", "fecha de inicio y fecha de finalización",
                    "j_idt54:fecha_inicio",
                    "La fecha de inicio no puede ser mayor a la fecha de finalización."));
        } else {
            if (validacionServicio.cumpleTiempoMaximoPermitido(registroSolicitudHelper.getTipoMovimiento().getId(),
                    registroSolicitudHelper.getFecha_desde(),
                    registroSolicitudHelper.getFecha_hasta(),
                    registroSolicitudHelper.getLicencia().getTipoNacimiento(),
                    error,
                    getSession().getServletContext())) {
                registroSolicitudHelper.setValidacionFechas(Boolean.TRUE);
            } else {
                err.add(tramiteServicio.registroError("Tiempo Solicitado",
                        "fecha de inicio y fecha de finalización", "j_idt54:fecha_inicio", error.toString()));
            }
        }
        if (registroSolicitudHelper.getTipoMovimiento().getControlFechaPresentaDocumento().
                equals(SiNoEnum.SI.getCodigo())) {
            Date d = new Date();
            if (d.getTime() < registroSolicitudHelper.getLicencia().getFechaOcurrioHecho().getTime()) {
                err.add(tramiteServicio.registroError("Tiempo Solicitado", "Fecha del Hecho", "j_idt54:fecha_inicio",
                        "La fecha en la que ocurrió el hecho no puede ser mayor a la fecha en que está presentando la solicitud."));
            }
        }
    }

    /**
     * Metodo que abre el panel popup de sustento legal.
     */
    public void verSustentoLegal() {
        registroSolicitudHelper.setSustentoLegal(registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().
                getTipoMovimientoRequisito().
                getRequisito().getSustentoLegal());
        registroSolicitudHelper.setAbrirPopUpSustentoLegal(Boolean.TRUE);
    }

    /**
     * Metodo que realiza la carga de un archivo pdf.
     */
    public void subirArchivo() {
        registroSolicitudHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        try {
            if (registroSolicitudHelper.getArchivo() != null) {
                ParametroGlobal buscarParametroGlobalPorNemonico =
                        administracionServicio.buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (registroSolicitudHelper.getArchivo().getSize()
                        > buscarParametroGlobalPorNemonico.getValorNumerico().longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo super las "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 2048) + " MB",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    if ("application/force-download".equals(
                            registroSolicitudHelper.getArchivo().getContentType()) || "application/pdf".equals(
                            registroSolicitudHelper.getArchivo().getContentType())) {
                        registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(new Archivo());
                        registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().
                                getArchivo().setArchivo(registroSolicitudHelper.getArchivo().getContents());
                        registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().
                                getArchivo().setNombre(registroSolicitudHelper.getArchivo().getFileName());
                        FacesMessage msg = new FacesMessage("Archivo "
                                + registroSolicitudHelper.getArchivo().getFileName() + " subió correctamente");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        mostrarMensajeEnPantalla("ec.gob.mrl.smp.genericos.mensaje.archivoCargadoNoesPDF",
                                FacesMessage.SEVERITY_WARN);
                        registroSolicitudHelper.setArchivo(null);
                    }
                }
            } else {
                FacesMessage msg = new FacesMessage("El archivo esta vacio.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al subir el archivo.", ex);
        }
    }

    /**
     * Método para elmiminar el archivo de la lista.
     */
    public void eliminarArchivo() {
        registroSolicitudHelper.setAbrirPopUpSustentoLegal(Boolean.FALSE);
        registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().setArchivo(new Archivo());
        registroSolicitudHelper.getValidacionTipoMovimientoRequisitoVO().getArchivo().setNombre("");
    }

    /**
     * metodo que calcula las horas de permiso para matriculas de hijos.
     */
    public void calcularHorasPermisoMatricula() {
        registroSolicitudHelper.setNumerohorasMatriculasHijo(
                UtilFechas.calcularDiferenciaHorasEntreFechas(registroSolicitudHelper.getFecha_desde(),
                registroSolicitudHelper.getFecha_hasta()));
        actualizarComponente("numeroHoras");
    }

    /**
     * Metodo que se encarga de guardar las validaciones de requisitos.
     *
     * @return String
     */
    public String guardar() {
        registroSolicitudHelper.getListaErroresVO().clear();
        registroSolicitudHelper.setAbrirPopUpCargarArchivos(Boolean.FALSE);
        StringBuilder errorBuilder = new StringBuilder();
        Boolean req = Boolean.FALSE;
        try {
            validarTiempo(registroSolicitudHelper.getListaErroresVO());
            calcularHorasPermisoMatricula();
            validarMaximoHoras(registroSolicitudHelper.getListaErroresVO());
            validarRequisitos(registroSolicitudHelper.getListaErroresVO(),
                    registroSolicitudHelper.getListaValidaciones());
            validarHorarios(registroSolicitudHelper.getListaErroresVO());
//            req = tramiteServicio.existenLicenciaEnCursoSolicitud(obtenerUsuarioConectado().getServidor().
//                    getTipoDocumento().getDescripcion(), obtenerCedulaUsuario(), obtenerNombreUsuario(),
//                    registroSolicitudHelper.getTipoMovimientoId(),
//                    registroSolicitudHelper.getTipoMovimiento().getNombre(), errorBuilder);
//            if (!req && registroSolicitudHelper.getListaErroresVO().size() <= 0) {
//                if (registroSolicitudHelper.getListaErroresVO().size() <= 0) {
//                    // validar que no existe otro movimiento con las mismas caracteriisticas.
//                    Date f = new Date();
//                    registroSolicitudHelper.getMovimiento().setJustificacion(registroSolicitudHelper.getDetalle());
//                    registroSolicitudHelper.getMovimiento().setRigeApartirDe(registroSolicitudHelper.getLicencia().
//                            getMovimiento().getRigeApartirDe());
//                    registroSolicitudHelper.getMovimiento().setFechaHasta(registroSolicitudHelper.getLicencia().
//                            getMovimiento().getFechaHasta());
//                    registroSolicitudHelper.getTramite().setDescripcion(UtilCadena.concatenar("Solicitud de ",
//                            registroSolicitudHelper.getTipoMovimiento().getNombre(), ", ",
//                            obtenerNombreUsuario(), ", ", UtilFechas.formatearLargo(f)));
//                    registroSolicitudHelper.getLicencia().setFechaPresentaDocumento(f);
//                    registroSolicitudHelper.getLicencia().setDevengar(registroSolicitudHelper.getDevengar());
//                    //**********************CREAR TRAMITE**********************
//                    registroSolicitudHelper.setTramiteCreado(tramiteServicio.crearTramiteEnSolicitudLicencias(
//                            registroSolicitudHelper.getTipoMovimientoId(),
//                            obtenerUsuarioConectado().getIdInstitucion(), registroSolicitudHelper.getTramite(),
//                            registroSolicitudHelper.getMovimiento(), registroSolicitudHelper.getLicencia(),
//                            registroSolicitudHelper.getListaValidaciones(), registroSolicitudHelper.
//                            getListaLicenciaHorario(), obtenerUsuarioConectado()));
//                    reglaNavegacionDirecta(SOLICITUD_ENVIADA);
//                }
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            registroSolicitudHelper.getListaErroresVO().add(tramiteServicio.registroError("",
                    "Error", NADA, UtilCadena.concatenar("Al enviar la solicitud: ", ex.getMessage().replaceAll(
                    "ec.gob.mrl.smp.excepciones.ServicioException:", ""))));
        }
        if (registroSolicitudHelper.getListaErroresVO().size() > 0) {
            registroSolicitudHelper.setMostrarTablaErrores(Boolean.TRUE);
        } else {
            registroSolicitudHelper.setMostrarTablaErrores(Boolean.FALSE);
        }
        return null;
    }

    /**
     * Método para validar si ingreso los horarios.
     *
     * @param err
     */
    public void validarHorarios(final List<ErroresVO> err) {
        if (registroSolicitudHelper.getHorario()) {
            if (registroSolicitudHelper.getTipoMovimiento().getHorario().
                    equals(HorarioConfiguracionTipoMovimientoEnum.SI.getCodigo())) {
                if (registroSolicitudHelper.getListaLicenciaHorario().size() <= 0) {
                    err.add(tramiteServicio.registroError("Horarios", "Registro de Horarios", "j_idt54:horarios",
                            "No se ha Registrado ningun horario para la licencia/permiso"));
                } else {
                    int cont = 0;
                    for (LicenciaHorario lh : registroSolicitudHelper.getListaLicenciaHorario()) {
                        if (lh.getTipo() != null) {
                            if (lh.getTipo().equals(TipoHorarioEnum.LICENCIA_PERMISO.getCodigo())) {
                                cont += 1;
                            }
                        } else {
                            cont += 1;
                        }
                    }
                    if (cont < 1) {
                        err.add(tramiteServicio.registroError("Horarios", "Registro de Horarios", "j_idt54:horarios",
                                "No se ha Registrado ningun horario para la licencia/permiso"));
                    }
                }
            }
            if (registroSolicitudHelper.getTipoMovimiento().getHorarioDevengar().
                    equals(HorarioConfiguracionTipoMovimientoEnum.SI.getCodigo())) {
                if (registroSolicitudHelper.getListaLicenciaHorario().size() <= 0) {
                    err.add(tramiteServicio.registroError("Horarios", "Registro de Horarios", "j_idt54:horarios",
                            "No se ha Registrado ningun horario x devengar"));
                } else {
                    int cont = 0;
                    for (LicenciaHorario lh : registroSolicitudHelper.getListaLicenciaHorario()) {
                        if (lh.getTipo() != null) {
                            if (lh.getTipo().equals(TipoHorarioEnum.RECUPERACION.getCodigo())) {
                                cont += 1;
                            }
                        }
                    }
                    if (cont < 1) {
                        err.add(tramiteServicio.registroError("Horarios", "Registro de Horarios", "j_idt54:horarios",
                                "No se ha Registrado ningun horario x devengar"));
                    }
                }
            }
        }
    }

    /**
     * Metodo que valida el maximo de horas que puede solicitar para representar a la asociación y tambien el maximo de
     * horas de permiso para matricular hijos.
     *
     * @param err List
     */
    public void validarMaximoHoras(final List<ErroresVO> err) {
        //********************representacion de asociacion
        if (registroSolicitudHelper.getTipoMovimiento().getAreaRepresentacionAsociacionLaboral()) {
            int valor = registroSolicitudHelper.getTipoMovimiento().getMaximoNumeroHorasMensuales();
            if (registroSolicitudHelper.getLicencia().getHorasMensualesRepresentacionLaboral() > valor) {
                err.add(tramiteServicio.registroError("Área Representación de una Asociación Laboral",
                        "Número de Horas Mensuales", "j_idt54:etqHorasMensuales",
                        UtilCadena.concatenar("El valor exede de ", valor, " que es el máximo permitido")));
            }
        }
    }

    /**
     * metodo para validar la tabla de registros.
     *
     * @param err List<ErroresVO>
     * @param val List<Validacion>
     * @return
     */
    public void validarRequisitos(List<ErroresVO> err, List<Validacion> val) {
        Boolean oblig, archiv;
        List<ValidacionTipoMovimientoRequisitoVO> lista = new ArrayList<ValidacionTipoMovimientoRequisitoVO>();
        lista = registroSolicitudHelper.getListaValidacionTipoMovimientoRequisitoVO();
        for (ValidacionTipoMovimientoRequisitoVO vtmrvo : lista) {
            String t = "Tabla Requisitos";
            String c = vtmrvo.getTipoMovimientoRequisito().getRequisito().getNombre();
            String r = "j_idt54:tablaVerificarRequisitos_data";
            if (vtmrvo != null) {
                oblig = vtmrvo.getTipoMovimientoRequisito().getObligatorio();
                archiv = vtmrvo.getTipoMovimientoRequisito().getSubirArchivoObligatorio();
                if (oblig) {
                    if (vtmrvo.getValidacion().getFechaDocumento() == null
                            || vtmrvo.getValidacion().getFechaDocumento().toString() == "") {
                        err.add(tramiteServicio.registroError(t, c, r,
                                "Falta fecha de documento"));
                    }
                    if (vtmrvo.getValidacion().getNumeroDocumento().isEmpty()) {
                        err.add(tramiteServicio.registroError(t, c, r, "Falta numero de documento"));
                    }
                    if (vtmrvo.getTipoMovimientoRequisito().getRequisito().
                            getTieneCalificacion() == Boolean.TRUE) {
                        if (vtmrvo.getValidacion().getCalificacion().isEmpty()) {
                            err.add(tramiteServicio.registroError(t, c, r, "Falta la calificación"));
                        }
                    }
                }
                if (archiv) {
                    if (vtmrvo.getArchivo() == null) {
                        err.add(tramiteServicio.registroError(t, c, r, "Falta cargar el archivo"));
                    } else {
                        if (vtmrvo.getArchivo().getNombre().isEmpty()) {
                            err.add(tramiteServicio.registroError(t, c, r, "Falta cargar el archivo"));
                        } else {
                            vtmrvo.getValidacion().setArchivo(new Archivo());
                            vtmrvo.getValidacion().setArchivo(vtmrvo.getArchivo());
                        }
                    }
                } else {
                    if (vtmrvo.getArchivo() != null) {
                        vtmrvo.getValidacion().setArchivo(new Archivo());
                        vtmrvo.getValidacion().setArchivo(vtmrvo.getArchivo());
                    }
                }
                vtmrvo.getValidacion().setTipoMovimientoRequisito(vtmrvo.getTipoMovimientoRequisito());
                val.add(vtmrvo.getValidacion());
            }
        }
    }

    /**
     * Método para imprimir la Solicitud.
     */
    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.SOLICITUD_LP.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("__format", "pdf");
        parametrosReporte.put("p_titulo", "SOLICITUD DEL TRÁMITE");
        parametrosReporte.put("p_tramite_id", registroSolicitudHelper.getTramiteCreado().getId().toString());
        parametrosReporte.put("p_horario_l", registroSolicitudHelper.getTramiteCreado().
                getTipoMovimiento().getHorario());
        parametrosReporte.put("p_horario_r", registroSolicitudHelper.getTramiteCreado().
                getTipoMovimiento().getHorarioDevengar());
        generarUrlDeReporte();
    }

    /**
     * @return the registroSolicitudHelper
     */
    public RegistroSolicitudHelper getRegistroSolicitudHelper() {
        return registroSolicitudHelper;
    }

    /**
     * @param registroSolicitudHelper the registroSolicitudHelper to set
     */
    public void setRegistroSolicitudHelper(final RegistroSolicitudHelper registroSolicitudHelper) {
        this.registroSolicitudHelper = registroSolicitudHelper;
    }
}
