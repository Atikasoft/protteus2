/**
 * VacacionAprobacionControlador.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of Proteus ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with PROTEUS.
 *
 * PROTEUS Quito - Ecuador
 *
 * 04/11/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.VacacionValidacionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
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

/**
 * VacacionAprobacion
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionValidacionBean")
@ViewScoped
public class VacacionValidacionControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionValidacionControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/validacion_solicitud_vacacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_solicitud_vacacion_validacion.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Constante para observacion de actualizacion de saldo de vacaciones.
     */
    public static final String OBSERVACION_SALDO_VACACION = "DESCUENTO POR SOLICITUD DE ";
    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;
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
     * Helper de clase.
     */
    @ManagedProperty("#{vacacionValidacionHelper}")
    private VacacionValidacionHelper vacacionValidacionHelper;

    /**
     * Constructor por defecto.
     */
    public VacacionValidacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getVacacionValidacionHelper());
        setVacacionValidacionHelper(getVacacionValidacionHelper());
        iniciarComponentes();

    }

    private void iniciarComponentes() {
        getCatalogoHelper().setCampoBusqueda("");
        vacacionValidacionHelper.setEsRRHH(esUnidadRRHH());

        try {
            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = admServicio.listarUnidadOrganizacionalVigente();
            vacacionValidacionHelper.setListaUnidadesOrganizacionales(unidades);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar unidades organizacionales", e);
        }
        vacacionValidacionHelper.getUnidadOrganizacional().setId(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
        vacacionValidacionHelper.getUnidadOrganizacional().setNombre(
                obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
        if (!vacacionValidacionHelper.getEsRRHH()) {
            buscar();
        }
    }

    public String validarCamposRequeridos() {
        if (vacacionValidacionHelper.getVacacionSolicitud().getEstado() == null) {
            mostrarMensajeEnPantalla("El campo estado es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (vacacionValidacionHelper.getVacacionSolicitud().getMotivo() == null
                || vacacionValidacionHelper.getVacacionSolicitud().getMotivo().isEmpty()) {
            mostrarMensajeEnPantalla("El campo observacion es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        ejecutarComandoPrimefaces("confirmAprobar.show();");
        return null;
    }

    @Override
    public String guardar() {
        try {

//            if (vacacionValidacionHelper.getVacacionSolicitud().getServidorInstitucion().getServidor().getId().equals(
//                    obtenerUsuarioConectado().getServidor().getId())) {
//                if (vacacionValidacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
//                    mostrarMensajeEnPantalla("No es posible Validar sus propias Solicitudes", FacesMessage.SEVERITY_ERROR);
//                } else {
//                    mostrarMensajeEnPantalla("No es posible Aprobar sus propias Solicitudes", FacesMessage.SEVERITY_ERROR);
//                }
//                return null;
//            }
            Servidor s =vacacionValidacionHelper.getVacacionSolicitud().getServidorInstitucion().getServidor();
            if (vacacionValidacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
                Long saldo = obtenerRegistroSaldoVacacionServidor();
                if (saldo <= 0) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones", FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (vacacionValidacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())
                        && UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', saldo,s.getJornada()) < vacacionValidacionHelper.getVacacionSolicitud().getCantidadSolicitada()) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente", FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (vacacionValidacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.MINUTOS.getCodigo())
                        && saldo < vacacionValidacionHelper.getVacacionSolicitud().getCantidadSolicitada()) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente", FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                if (vacacionValidacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.HORAS.getCodigo())
                        && UtilFechas.convertirMinutosEnHoras(saldo) < 
                        vacacionValidacionHelper.getVacacionSolicitud().getCantidadSolicitada()) {
                    mostrarMensajeEnPantalla("Servidor sin Saldo de Vacaciones Suficiente", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
            }
            vacacionValidacionHelper.getVacacionSolicitud().setValidador(
                    buscarAprobador(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion()));
            if (vacacionValidacionHelper.getVacacionSolicitud().getValidador() == null) {
                mostrarMensajeEnPantalla("El Validador no está configurado dentro de la institución.", FacesMessage.SEVERITY_INFO);
                return null;
            }
            
            vacacionServicio.actualizarVacacionSolicitud(
                    getVacacionValidacionHelper().getVacacionSolicitud(),null, obtenerUsuarioConectado());
            if (vacacionValidacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.VALIDADO.getCodigo())) {
                mostrarMensajeEnPantalla(SOLICITUD_PROCESADA, FacesMessage.SEVERITY_INFO);
            } else if (vacacionValidacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
                vacacionValidacionHelper.getVacacionSolicitud().setAprobador(
                        buscarAprobador(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion()));
                mostrarMensajeEnPantalla(SOLICITUD_PROCESADA, FacesMessage.SEVERITY_INFO);
                try {
                    vacacionServicio.enviarMailAprobacionSolicitudVacacion(vacacionValidacionHelper.getVacacionSolicitud());
                } catch (Exception ex) {
                    mostrarMensajeEnPantalla("Problemas al enviar email de notificación.", FacesMessage.SEVERITY_INFO);
                }
            } else if (vacacionValidacionHelper.getVacacionSolicitud().getEstado().equals(EstadoVacacionEnum.NEGADO.getCodigo())) {
                mostrarMensajeEnPantalla(SOLICITUD_PROCESADA, FacesMessage.SEVERITY_INFO);
            }
            getVacacionValidacionHelper().setGuardado(Boolean.TRUE);
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Problemas al guardar", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(getVacacionValidacionHelper().getVacacionSolicitudEditDelete());
            VacacionSolicitud d = (VacacionSolicitud) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getVacacionValidacionHelper().setVacacionSolicitud(d);
            getVacacionValidacionHelper().setEsNuevo(Boolean.FALSE);
            getVacacionValidacionHelper().setGuardado(Boolean.FALSE);
            obtenerRegistroSaldoVacacionServidor();
            obtenerTiempoServidorEnInstitucion(d.getFecha());
            getVacacionValidacionHelper().getVacacionSolicitud().setMotivo("");
            iniciarComboEstadoVacacion();
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

    private boolean esUnidadRRHH() {
        boolean esUnidadRRHH = false;
        try {
            String parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
            esUnidadRRHH = esRRHH(parametro);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esUnidadRRHH;
    }

    /**
     * Este metodo llena las opciones para el combo de estado vacacion.
     */
    private void iniciarComboEstadoVacacion() {
        iniciarCombos(vacacionValidacionHelper.getListaOpcionesEstadoVacacion());
        vacacionValidacionHelper.getListaOpcionesEstadoVacacion().
                add(new SelectItem(EstadoVacacionEnum.NEGADO.getCodigo(), EstadoVacacionEnum.NEGADO.getDescripcion()));
        if (vacacionValidacionHelper.getVacacionSolicitud().getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
            vacacionValidacionHelper.getListaOpcionesEstadoVacacion().
                    add(new SelectItem(EstadoVacacionEnum.VALIDADO.getCodigo(), EstadoVacacionEnum.VALIDADO.getDescripcion()));
        } else {
            vacacionValidacionHelper.getListaOpcionesEstadoVacacion().
                    add(new SelectItem(EstadoVacacionEnum.APROBADO.getCodigo(), EstadoVacacionEnum.APROBADO.getDescripcion()));
        }
    }

    /**
     * Busca todos los registros en estado registrado deben mostrarse unicamente
     * para el rol Aprobador de vacaciones
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            vacacionValidacionHelper.getListaVacacionSolicitudes().clear();
            BusquedaVacacionVO vo = new BusquedaVacacionVO();
            vo.setEstadoVacacion(EstadoVacacionEnum.REGISTRADO.getCodigo());
            vo.setUnidadAdministrativaId(vacacionValidacionHelper.getUnidadOrganizacional() != null ? vacacionValidacionHelper.getUnidadOrganizacional().getId() : null);
            vacacionValidacionHelper.setListaVacacionSolicitudes(vacacionServicio.buscar(vo));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
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
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoVacacion(final String codigo) {
        return TipoVacacionEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de periodo de
     * vacaciones parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionPeriodoVacacion(final String codigo) {
        return PeriodoVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoVacacion(final String codigo) {
        return EstadoVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
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
     * Se obtiene del ultimo registro de vacaciones(acumulacion) de acuerdo a la
     * fecha de creacion Alli se registra el saldo final de la transacción. Si
     * no existen registros el saldo es 0 minutos
     *
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public Long obtenerRegistroSaldoVacacionServidor() throws DaoException {
        Long totalSaldo = 0L;
        ServidorInstitucion si = vacacionValidacionHelper.getVacacionSolicitud().getServidorInstitucion();
        if (si != null) {
            vacacionValidacionHelper.setCadenaSaldo("0 Días");
            vacacionValidacionHelper.getVacacion().setSaldo(0L);
            Vacacion v = vacacionDao.buscarPorServidor(
                    vacacionValidacionHelper.getVacacionSolicitud().getServidorInstitucion().getId());
            if (v != null) {
                vacacionValidacionHelper.setVacacion(v);
                totalSaldo = v.getSaldo();
            }
            Integer saldo[] = UtilFechas.convertirMinutosA_ddHHmm(totalSaldo,si.getServidor().getJornada());
            vacacionValidacionHelper.setCadenaSaldo(saldo[0] + " Días " + saldo[1] + " Horas " + saldo[2] + " Minutos ");
        }
        return totalSaldo;
    }

    /**
     * Calcula el tiempo del Servidor en la Empresa, a partir de su fecha de
     * ingreso hasta la fecha de Inicio de la Vacacion.<br/>
     *
     * Obtiene el tiempo en años, meses, dias, para presentar en vista.<br>
     *
     * @param fechaInicioVacacion fecha de inicio de la vacacion
     */
    private void obtenerTiempoServidorEnInstitucion(Date fechaInicioVacacion) {
        if (vacacionValidacionHelper.getVacacionSolicitud().getServidorInstitucion() != null) {
            Integer tiempo[] = UtilFechas.calcularAniosMesesDiasEntreFechas(vacacionValidacionHelper.getVacacionSolicitud().
                    getServidorInstitucion().getFechaIngreso(), fechaInicioVacacion);

            vacacionValidacionHelper.setCadenaTiempoEmpresa(
                    tiempo[0] + " Años "
                    + tiempo[1] + " Meses "
                    + tiempo[2] + " Días ");
        }
    }

    /**
     * @return the vacacionValidacionHelper
     */
    public VacacionValidacionHelper getVacacionValidacionHelper() {
        return vacacionValidacionHelper;
    }

    /**
     * @param vacacionValidacionHelper the vacacionValidacionHelper to set
     */
    public void setVacacionValidacionHelper(VacacionValidacionHelper vacacionValidacionHelper) {
        this.vacacionValidacionHelper = vacacionValidacionHelper;
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }
}
