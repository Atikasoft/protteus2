/**
 * VacacionAprobacionControlador.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * Proteus ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with PROTEUS.
 *
 * PROTEUS Quito - Ecuador
 *
 * 04/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.VacacionAprobacionHelper;
import ec.com.atikasoft.proteus.dao.ArchivoDao;
import ec.com.atikasoft.proteus.dao.CatalogoDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorException;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
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
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * VacacionAprobacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionAprobacionBean")
@ViewScoped
public class VacacionAprobacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionAprobacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/aprobacion_solicitud_vacacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_solicitud_vacacion_aprobacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Constante para observacion de actualizacion de saldo de vacaciones.
     */
    public static final String OBSERVACION_SALDO_VACACION = "DESCUENTO POR SOLICITUD DE ";
    /**
     * variable que guarda el archivo pdf generado
     */
    private StreamedContent pdf;
    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Servicio de distributivo.
     */
    @EJB
    private ArchivoDao archivoDao;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;
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
    @ManagedProperty("#{vacacionAprobacionHelper}")
    private VacacionAprobacionHelper vacacionAprobacionHelper;

    /**
     * Constructor por defecto.
     */
    public VacacionAprobacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getVacacionAprobacionHelper());
        setVacacionAprobacionHelper(getVacacionAprobacionHelper());
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComponentes();
        iniciarComboAutoridadNominadora();
        iniciarComboRepresentanteRRHH();

    }

    /**
     *
     */
    private void iniciarComponentes() {
        iniciarComboEstadoVacacion();
        getCatalogoHelper().setCampoBusqueda("");
        vacacionAprobacionHelper.setEsRRHH(esUnidadRRHH());
        try {
            List<UnidadOrganizacional> unidades = admServicio.listarUnidadOrganizacionalVigente();
            vacacionAprobacionHelper.setListaUnidadesOrganizacionales(unidades);
            vacacionAprobacionHelper.getUnidadOrganizacional().setId(
                    obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
            vacacionAprobacionHelper.getUnidadOrganizacional().setNombre(
                    obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
            buscar();
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar unidades organizacionales", e);
        }

    }

    /**
     *
     * @return
     */
    public String validarCamposRequeridos() {
        if (vacacionAprobacionHelper.getVacacionSolicitud().getEstado() == null) {
            mostrarMensajeEnPantalla("El campo estado es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (vacacionAprobacionHelper.getVacacionSolicitud().getMotivo() == null
                || vacacionAprobacionHelper.getVacacionSolicitud().getMotivo().isEmpty()) {
            mostrarMensajeEnPantalla("El campo observacion es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (vacacionAprobacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.
                getCodigo()) && vacacionAprobacionHelper.getVacacionSolicitud().getEstado().equals(
                        EstadoVacacionEnum.APROBADO.getCodigo())) {
            ejecutarComandoPrimefaces("dlgAprobarConDatosAccionPersona.show();");

        } else {
            ejecutarComandoPrimefaces("confirmAprobar.show();");
        }
        return null;
    }

    @Override
    public String guardar() {
        try {
            Servidor s = vacacionAprobacionHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
            if (vacacionAprobacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.APROBADO.
                    getCodigo())) {
                Long saldo = obtenerRegistroSaldoVacacionServidor(vacacionAprobacionHelper.getVacacionSolicitud());
                if (saldo <= 0) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (vacacionAprobacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.
                        getCodigo())
                        && UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', saldo, s.getJornada())
                        < UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', vacacionAprobacionHelper.
                                getVacacionSolicitud().getCantidadSolicitadaMinutos(), s.getJornada())) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (vacacionAprobacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.HORAS.getCodigo())
                        && saldo < vacacionAprobacionHelper.getVacacionSolicitud().getCantidadSolicitadaMinutos()) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                // se setea el saldo con el cual se aprueba la solicitud.
                vacacionAprobacionHelper.getVacacionSolicitud().setSaldoVacacionesEfectiva(saldo);
                //Se setea el campo observacion de la entidad vacacion.
            }
            vacacionAprobacionHelper.getVacacionSolicitud().setAprobador(
                    buscarAprobador(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion()));
            vacacionAprobacionHelper.getVacacionSolicitud().setIdAprobador(
                    vacacionAprobacionHelper.getVacacionSolicitud().getAprobador().getId());

            if (vacacionAprobacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.APROBADO.
                    getCodigo())) {
                Catalogo representanteRRHH = catalogoDao.buscarPorId(vacacionAprobacionHelper.
                        getRepresentanteRRHHId());
                Catalogo autoridadNominadora = catalogoDao.buscarPorId(vacacionAprobacionHelper.
                        getAutoridadNominadoraId());

                if (vacacionAprobacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(
                        TipoPeriodoAlertaEnum.DIA.getCodigo())) {
                    ejecutarComandoPrimefaces("dlgAprobarConDatosAccionPersona.hide();");
                    String numeroAccion = vacacionServicio.generarPdfAccionPersonalSolicitudVacacion(
                            vacacionAprobacionHelper.getVacacionSolicitud(),
                            representanteRRHH.getNombre(),
                            vacacionAprobacionHelper.getNombreRepresentanteRRHH(),
                            autoridadNominadora.getNombre(),
                            vacacionAprobacionHelper.getNombreAutoridadNominadora(),
                            obtenerUsuarioConectado());
                    vacacionServicio.actualizarVacacionSolicitud(
                            getVacacionAprobacionHelper().getVacacionSolicitud(), numeroAccion, obtenerUsuarioConectado());
                    pdf = new DefaultStreamedContent(new ByteArrayInputStream(
                            vacacionAprobacionHelper.getVacacionSolicitud().getArchivoAccionPersonal().getArchivo()),
                            "application/pdf", vacacionAprobacionHelper.getVacacionSolicitud().
                            getArchivoAccionPersonal().getNombre());
                    ejecutarComandoPrimefaces("dlgInfoAprobar.show()");
                } else {
                    vacacionServicio.actualizarVacacionSolicitud(
                            getVacacionAprobacionHelper().getVacacionSolicitud(), null, obtenerUsuarioConectado());
                }
                mostrarMensajeEnPantalla(SOLICITUD_APROBADA, FacesMessage.SEVERITY_INFO);
            } else {
                vacacionServicio.actualizarVacacionSolicitud(
                        getVacacionAprobacionHelper().getVacacionSolicitud(), null, obtenerUsuarioConectado());
                mostrarMensajeEnPantalla(SOLICITUD_NEGADA, FacesMessage.SEVERITY_INFO);
                return LISTA_ENTIDAD;
            }
            getVacacionAprobacionHelper().setGuardado(Boolean.TRUE);
            enviarMail(getVacacionAprobacionHelper().getVacacionSolicitud());

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }

        return null;
    }

    /**
     * envia mail de confirmación.
     *
     * @param vs
     */
    private void enviarMail(final VacacionSolicitud vs) {
        try {
//            vacacionServicio.enviarMailAprobacionSolicitudVacacion(vs);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Problemas al enviar correo de notificacion", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al enviar correo de notificacion", ex);
        }
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getVacacionAprobacionHelper().getVacacionSolicitudEditDelete());
            VacacionSolicitud d = (VacacionSolicitud) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getVacacionAprobacionHelper().setVacacionSolicitud(d);
            getVacacionAprobacionHelper().setEsNuevo(Boolean.FALSE);
            getVacacionAprobacionHelper().setGuardado(Boolean.FALSE);
            obtenerRegistroSaldoVacacionServidor(d);
            obtenerTiempoServidorEnInstitucion(vacacionServicio.obtenerFechaInicioVacaciones(d));
            getVacacionAprobacionHelper().getVacacionSolicitud().setMotivo("");
            String explicacion = vacacionAprobacionHelper.getVacacionSolicitud().getDistributivoDetalle().
                    getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getExplicacionVacacion().
                    replaceAll("#dias", "" + (vacacionAprobacionHelper.getVacacionSolicitud()
                            .getCantidadSolicitadaMinutos() / (8 * 60)));
            vacacionAprobacionHelper.setExplicacion(explicacion);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {

        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {

        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de estado vacacion.
     */
    private void iniciarComboEstadoVacacion() {
        iniciarCombos(vacacionAprobacionHelper.getListaOpcionesEstadoVacacion());
        vacacionAprobacionHelper.getListaOpcionesEstadoVacacion().
                add(new SelectItem(EstadoVacacionEnum.APROBADO.getCodigo(), EstadoVacacionEnum.APROBADO.getDescripcion()));
        vacacionAprobacionHelper.getListaOpcionesEstadoVacacion().
                add(new SelectItem(EstadoVacacionEnum.NEGADO.getCodigo(), EstadoVacacionEnum.NEGADO.getDescripcion()));
    }

    /**
     * Busca todos los registros en estado registrado deben mostrarse unicamente para el rol Aprobador de vacaciones
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            BusquedaVacacionVO vo = new BusquedaVacacionVO();
            vo.setUsuarioVO(obtenerUsuarioConectado());
            vo.setEstadoVacacion(EstadoVacacionEnum.REGISTRADO.getCodigo());
            vo.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            vo.setUnidadAdministrativaId(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional().getId());

            List<VacacionSolicitud> lvs = vacacionServicio.buscarSolicitudesVacacion(vo);
            vacacionAprobacionHelper.setListaVacacionSolicitudes(lvs);
            //seleccionarSolicitudesPermitidasParaUnidadDeAcceso(lvs);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * SELECCIONA LAS SOLICITUDES DE SERVIDORES QUE PERTENECEN A UNA UNIDAD ORGANIZACIONAL CONFIGURADA COMO
     *
     * @param listaSolicitudes
     */
    private void seleccionarSolicitudesPermitidasParaUnidadDeAcceso(List<VacacionSolicitud> listaSolicitudes) {
        try {
            vacacionAprobacionHelper.getListaVacacionSolicitudes().clear();
            List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            for (UnidadOrganizacional unidad : unidades) {
                for (VacacionSolicitud vs : listaSolicitudes) {
                    UnidadOrganizacional uo = vs.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional();
                    if (unidad.getCodigo().equals(uo.getCodigo())) {
                        vacacionAprobacionHelper.getListaVacacionSolicitudes().add(vs);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     *
     * @return
     */
    private boolean esUnidadRRHH() {
        boolean esUnidadRRHH = false;
        try {
            String parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
            esUnidadRRHH = esRRHH(parametro);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esUnidadRRHH;
    }

    /**
     * Encuentra ServidorInstitucion del usuario conectado
     *
     * @param numeroDocumento
     * @return ServidorInstitucion
     */
    public ServidorInstitucion buscarAprobador(String numeroDocumento) {
        try {
            List<ServidorInstitucion> lista
                    = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(numeroDocumento);
            if (lista != null | !lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoVacacion(final String codigo) {
        return TipoVacacionEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de periodo de vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionPeriodoVacacion(final String codigo) {
        return PeriodoVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoVacacion(final String codigo) {
        return EstadoVacacionEnum.obtenerDescripcion(codigo);
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
     *
     * /**
     * Permite regresar al
     *
     * @return listado
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_PRINCIPAL;
    }

    /**
     * Se obtiene del ultimo registro de vacaciones(acumulacion) de acuerdo a la fecha de creacion Alli se registra el
     * saldo final de la transacción. Si no existen registros el saldo es 0 minutos
     *
     * @param vacacionSolicitud
     * @return
     */
    public Long obtenerRegistroSaldoVacacionServidor(final VacacionSolicitud vacacionSolicitud) {
        Long totalSaldo = 0L;
        Long totalSaldoProporcional = 0L;
        ServidorInstitucion si = vacacionAprobacionHelper.getVacacionSolicitud().getServidorInstitucion();
        if (si != null) {
            List<Vacacion> listaVacacion = getListaSaldosVacaciones();
            vacacionAprobacionHelper.setCadenaSaldo("0 Días");
            vacacionAprobacionHelper.setCadenaSaldoProporcional("0 Días");
            vacacionAprobacionHelper.getVacacion().setSaldo(0L);
            vacacionAprobacionHelper.getVacacion().setSaldoProporcional(0L);
            for (Vacacion v : listaVacacion) {
                vacacionAprobacionHelper.setVacacion(v);
                totalSaldo = v.getSaldo();
                totalSaldoProporcional = v.getSaldoProporcional();
                break;

            }
            Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(totalSaldo, si.getServidor().getJornada());
            vacacionAprobacionHelper.setCadenaSaldo(
                    saldo[0] + " Días "
                    + saldo[1] + " Horas "
                    + saldo[2] + " Minutos ");

            Integer saldoProporcional[] = UtilFechas.convertirMinutosA_ddHHmm(totalSaldoProporcional, si.getServidor().getJornada());
            vacacionAprobacionHelper.setCadenaSaldoProporcional(
                    saldoProporcional[0] + " Días "
                    + saldoProporcional[1] + " Horas "
                    + saldoProporcional[2] + " Minutos ");
        }
        return vacacionSolicitud.getTipo().equals(TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())
                ? totalSaldoProporcional : totalSaldo;
    }

    /**
     * buscar listado de saldo de vacaciones por periodos.
     *
     * @return
     */
    public List<Vacacion> getListaSaldosVacaciones() {
        List<Vacacion> lista = new ArrayList<Vacacion>();
        try {
            Vacacion v = vacacionDao.buscarPorServidor(vacacionAprobacionHelper.getVacacionSolicitud().getServidorInstitucion().getId());
            lista.add(v);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
        return lista;
    }

    /**
     * Calcula el tiempo del Servidor en la Empresa, a partir de su fecha de ingreso hasta la fecha de Inicio de la
     * Vacacion.<br/>
     *
     * Obtiene el tiempo en años, meses, dias, para presentar en vista.<br>
     *
     * @param fechaInicioVacacion fecha de inicio de la vacacion
     */
    private void obtenerTiempoServidorEnInstitucion(Date fechaInicioVacacion) {
        if (vacacionAprobacionHelper.getVacacionSolicitud().getServidorInstitucion() != null) {
            Integer tiempo[] = UtilFechas.calcularAniosMesesDiasEntreFechas(vacacionAprobacionHelper.getVacacionSolicitud().
                    getServidorInstitucion().getFechaIngreso(), fechaInicioVacacion);

            vacacionAprobacionHelper.setCadenaTiempoEmpresa(
                    tiempo[0] + " Años "
                    + tiempo[1] + " Meses "
                    + tiempo[2] + " Días ");
        }
    }

    /**
     *
     */
    private void iniciarComboAutoridadNominadora() {
        try {
            // lista de autoridades nominadoras
            iniciarCombos(vacacionAprobacionHelper.getListaAutoridadNominadora());
            List<Catalogo> listaAutoridades = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.AUTORIDAD_NOMINADORA.getCodigo());
            for (Catalogo c : listaAutoridades) {
                vacacionAprobacionHelper.getListaAutoridadNominadora().add(new SelectItem(c.getId(), c.getNombre()));
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
            iniciarCombos(vacacionAprobacionHelper.getListaRepresentanteRRHH());
            List<Catalogo> listaRepresentantes = catalogoDao.buscarPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.REPRESENTANTE_RRHH.getCodigo());
            for (Catalogo c : listaRepresentantes) {
                vacacionAprobacionHelper.getListaRepresentanteRRHH().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Error al iniciar combo de autoridad nominadora", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * @return the vacacionAprobacionHelper
     */
    public VacacionAprobacionHelper getVacacionAprobacionHelper() {
        return vacacionAprobacionHelper;
    }

    /**
     * @param vacacionAprobacionHelper the vacacionAprobacionHelper to set
     */
    public void setVacacionAprobacionHelper(VacacionAprobacionHelper vacacionAprobacionHelper) {
        this.vacacionAprobacionHelper = vacacionAprobacionHelper;
    }

    /**
     *
     * @return
     */
    public StreamedContent getPdf() {
        return pdf;
    }

    /**
     *
     * @param pdf
     */
    public void setPdf(StreamedContent pdf) {
        this.pdf = pdf;
    }

}
