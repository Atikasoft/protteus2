/*
 *  EventosServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Jan 24, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ContadorDao;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.TramiteBitacora;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.Devengamiento;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.dao.DevengamientoDao;
import ec.com.atikasoft.proteus.dao.DistributivoComisionServicioDao;
import ec.com.atikasoft.proteus.dao.DistributivoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.DocumentoHabilitanteDao;
import ec.com.atikasoft.proteus.dao.EstadoPersonalDao;
import ec.com.atikasoft.proteus.dao.EstadoPuestoDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoReclutamientoEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.enums.TipoRotacionEnum;
import ec.com.atikasoft.proteus.enums.TipoServidorEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javax.ejb.*;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Contiene las operaciones que se ejecutan por los cambios de estados de tramites.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class EventosServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(EventosServicio.class.getCanonicalName());

    /**
     * Dao de Movimiento.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Dao de devengamientos.
     */
    @EJB
    private DevengamientoDao devengamientoDao;

    /**
     * Dao de distributivo.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Dao de servidor.
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Dao de estado de personal.
     */
    @EJB
    private EstadoPersonalDao estadoPersonalDao;

    /**
     * Dao de estado de puesto.
     */
    @EJB
    private EstadoPuestoDao estadoPuestoDao;

    /**
     *
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     *
     */
    @EJB
    private ReclutamientoServicio reclutamientoServicio;

    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private DistributivoDao distributivoDao;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     *
     */
    @EJB
    private DocumentoHabilitanteDao documentoHabilitanteDao;

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;

    /**
     *
     */
    @EJB
    private DistributivoComisionServicioDao distributivoComisionServicioDao;

    /**
     *
     */
    @EJB
    private ContadorDao contadorDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private NotificacionesServicio notificacionesServicio;

    /**
     * Constructor sin argumentos.
     */
    public EventosServicio() {
        super();

    }

    /**
     *
     * @param tramite
     * @throws DaoException
     */
    public void liberarReclutamiento(final Tramite tramite) throws DaoException, ServicioException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente() && m.getDistributivoDetalleId() != null) {
                Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
                if (r != null) {
                    r.setFechaActualizacion(new Date());
                    r.setUsuarioActualizacion(tramite.getUsuarioCreacion());
                    r.setEmMovimientoPersonal(false);
                    reclutamientoServicio.actualizarReclutamiento(r);
                }
            }
        }
    }

    /**
     *
     * @param tramite
     * @throws DaoException
     */
    public void registrarDatosAnulacion(final Tramite tramite, final UsuarioVO usuarioVO, final String comentario)
            throws DaoException, ServicioException {
        tramite.setUsuarioAnulacionRechazo(usuarioVO.getServidor().getNumeroIdentificacion());
        tramite.setFechaAnulacionRechazo(new Date());
        tramite.setComentarioAnulacionRechazo(comentario);
    }

    /**
     * Envia notificaciones a los responsables de los desconcentrados avisandoles que debe asignar un horario al
     * servidor
     *
     * @param tramite
     * @param usuarioVO
     * @param comentario
     * @throws DaoException
     * @throws ServicioException
     */
    public void enviarNotificacionDeAsignacionHorario(final Tramite tramite, final UsuarioVO usuarioVO,
            final String comentario) throws DaoException, ServicioException {
        if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())
                || tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                        GrupoEnum.ROTACIONES.getCodigo())) {
            for (Movimiento m : tramite.getListaMovimientos()) {
                if (m.getVigente()) {
                    DistributivoDetalle puesto = null;
                    if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                            GrupoEnum.INGRESOS.getCodigo())) {
                        puesto = m.getDistributivoDetalle();
                    } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                            GrupoEnum.ROTACIONES.getCodigo())) {
                        puesto = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
                    }
                    if (puesto != null) {
                        // recuperar desconcentrado.
                        List<Servidor> responsables = desconcentradoServicio.
                                buscarResponsablesDesconcentradoPorUnidadOrganizacional(
                                        puesto.getDistributivo().getUnidadOrganizacional(),
                                        FuncionesDesconcentradosEnum.ASISTENCIA_DE_PERSONAL.getCodigo());
                        List<DestinatarioNotificacion> destinatarios = new ArrayList<>();
                        for (Servidor responsable : responsables) {
                            destinatarios.add(new DestinatarioNotificacion(responsable));
                        }
                        if (!destinatarios.isEmpty()) {
                            // envia notificaciones.
                            Notificacion notificacion = new Notificacion();
                            notificacion.setAsunto("ASIGNAR HORARIO A SERVIDOR");
                            notificacion.setMensaje(UtilCadena.concatenar("Se requiere asignar un horario al servidor:\n\n",
                                    "Tipo de identificación : ", TipoIdentificacionEnum.obtenerDescripcion(
                                            m.getTipoIdentificacion()), "\n",
                                    "Número de identificación: ", m.getNumeroIdentificacion(), "\n",
                                    "Apellidos y Nombres: ", m.getApellidosNombres(), "\n",
                                    "Unidad Organizacional: ", puesto.getDistributivo().
                                    getUnidadOrganizacional().getRuta()));
                            notificacion.setEnviadaATodos(Boolean.FALSE);
                            notificacion.setInstitucionEjercicioFiscal(usuarioVO.getInstitucionEjercicioFiscal());
                            notificacion.setVigente(Boolean.TRUE);
                            notificacion.setUsuarioCreacion(usuarioVO.getUsuario());
                            notificacion.setFechaCreacion(new Date());
                            notificacionesServicio.guardarYEnviarNotificacion(notificacion, destinatarios, usuarioVO);
                        }
                    }
                }
            }
        }
    }

    /**
     * Genera los numeros de contrato de un tramite que tenga documento habilitanto con generacion automatica..
     *
     * @param tramite Datos del tramite.
     * @throws DaoException Error de ejecucion.
     */
    public void generarNumeroContrato(final Tramite tramite) throws DaoException {
        if (tramite.getTipoMovimiento().getDocumentoHabilitante().getNumeroAutomatico()) {
            DocumentoHabilitante documentoHabilitante = documentoHabilitanteDao.buscarPorId(tramite.getTipoMovimiento().
                    getDocumentoHabilitante().getId());
            documentoHabilitanteDao.lock(documentoHabilitante);
            for (Movimiento m : tramite.getListaMovimientos()) {
                if (m.getVigente() && (m.getNumeroDocumentoHabilitante() == null
                        || m.getNumeroDocumentoHabilitante().trim().isEmpty())) {
                    documentoHabilitante.setContador(documentoHabilitante.getContador() + 1);
                    m.setNumeroDocumentoHabilitante(UtilMatematicas.rellenarConCerosIzq(
                            documentoHabilitante.getContador(), 10));
                    movimientoDao.actualizar(m);
                }
            }
            documentoHabilitanteDao.actualizar(documentoHabilitante);
        }
    }

    /**
     * Registra las devengacion que debe cumplir un servidor por licencia que genere devengamiento.
     *
     * @param tramite
     * @param usuario
     * @throws DaoException
     */
    public void registrarDevengaciones(final Tramite tramite, final UsuarioVO usuario) throws DaoException {
        if (tramite.getTipoMovimiento().getAreaTiempoPorDevengar() != null
                && tramite.getTipoMovimiento().getAreaTiempoPorDevengar()) {
            for (Movimiento m : tramite.getListaMovimientos()) {
                if (m.getVigente() && !m.getListaLicencias().isEmpty()) {
                    Licencia l = m.getListaLicencias().get(0);
                    if (l.getFechaInicioADevengar() != null && l.getFechaFinalADevengar() != null) {
                        Devengamiento d = new Devengamiento();
                        d.setFechaCreacion(new Date());
                        d.setFechaInicio(l.getFechaInicioADevengar());
                        d.setFechaFinal(l.getFechaFinalADevengar());
                        d.setInstitucion(tramite.getInstitucionEjercicioFiscal().getInstitucion());
                        d.setLicencia(l);
                        d.setNombresCompletos(m.getApellidosNombres());
                        d.setNumero("");
                        d.setNumeroIdentificacion(m.getNumeroIdentificacion());
                        d.setObservacionRegistro(l.getDevengarObservacion());
                        d.setPagoAnticipado(Boolean.FALSE);
                        d.setTipoIdentificacion(m.getTipoIdentificacion().substring(0, 1));
                        d.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
                        d.setVigente(Boolean.TRUE);
                        devengamientoDao.crear(d);
                    }
                }
            }
        }
    }

    /**
     * Se encarga de aplicar el movimiento de personal al core.
     *
     * @param tramite
     * @param usuario
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void aplicarMovimientoPersonal(final Tramite tramite, final UsuarioVO usuario) throws DaoException,
            ServicioException {
        if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
            aplicarMovimientoPersonalIngreso(tramite, usuario);
        } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.SALIDAS.getCodigo())) {
            validarPuestoOcupado(tramite, usuario);
            aplicarMovimientoPersonalCesacion(tramite, usuario);
        } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.ROTACIONES.getCodigo())) {
            validarPuestoOcupado(tramite, usuario);
            aplicarMovimientoPersonalRotacion(tramite, usuario);
        } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.ABSENTISMO.getCodigo())) {
            validarPuestoOcupado(tramite, usuario);
            if (tramite.getTipoMovimiento().getAreaComisionServicioInstitucionRequiriente()) {
                aplicarMovimientoPersonalComisionServicio(tramite, usuario);
            } else {
                aplicarMovimientoPersonalLicencia(tramite, usuario);
            }
        } else if (tramite.getTipoMovimiento().getClase().getGrupo().getNemonico().equals(
                GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo())) {
            validarPuestoOcupado(tramite, usuario);
        }

    }

    /**
     *
     * @param tramite
     * @param usuario
     * @throws ServicioException
     * @throws DaoException
     */
    private void validarPuestoOcupado(final Tramite tramite, final UsuarioVO usuario)
            throws ServicioException, DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
                DistributivoDetalle dd = distributivoDetalleDao.buscarPorId(m.getDistributivoDetalleId());
                if (!dd.getEstadoPuesto().getOcupado()) {
                    throw new ServicioException("Puesto con código " + dd.getCodigoPuesto() + " no se encuentra en estado ocupado.");
                }
            }
        }
    }

    /**
     * Se encarga de aplicar cesaciones al core.
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
    private void aplicarMovimientoPersonalIngreso(final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        TramiteBitacora tramiteBitacora = tramite.getListaBitacoras().get(0);
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
                crearIngreso(m, tramiteBitacora, tramite, usuario);
            }
        }
    }

    /**
     * Se encarga de aplicar cesaciones al core.
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
    private void aplicarMovimientoPersonalCesacion(final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
                crearCesacion(m, tramite, usuario);
            }
        }
    }

    /**
     * Se encarga de aplicar rotaciones al distributivo.
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
    private void aplicarMovimientoPersonalRotacion(final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
//                System.out.println("Movimiento aplicarMovimientoPersonalRotacion:" + m.getApellidosNombres());
                crearRotacion(m, tramite, usuario);
            }
        }
    }

    /**
     * Se encarga de aplicar regimenes disciplinarios al core. De todos los tipos de movimiento de este grupo solo los
     * de Suspensión se deben asentar en el SIITH, para lo cual se debe considerar el cambio de: a. Estado de puesto, de
     * estado de persona, registro de: fechas desde y hasta. b. Cambiar en el sistema de vacaciones la fecha para el
     * cálculo de vacaciones (el análisis de esta actividad será realizado en otra acta de análisis técnico con
     * lineamientos de la Ing. María del Carmen Rosero). ??????
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
//    private void aplicarMovimientoPersonalRegimenDisciplinario(final Tramite tramite, final UsuarioVO usuario) throws
//            ec.gob.mrl.siith.adm.excepciones.ServicioException, DaoException {
//        ParametroGlobal pNemonicoSuspension =
//                parametroGlobalDao.buscarPorNemonico(
//                ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_SUSPENSION_TEMPORAL_SIN_GOCE_REMUNERACION.getCodigo());
//        if (pNemonicoSuspension.getValorTexto().indexOf(tramite.getTipoMovimiento().getNemonico()) != -1) {
//            for (Movimiento m : tramite.getListaMovimientos()) {
//                if (m.getVigente()) {
//                    crearRegimenDisciplinario(m, tramite, usuario);
//                }
//            }
//        }
//    }
    /**
     * Se encarga de registrar los datos en el core por efectos de licencias sin remuneracion.
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
    private void aplicarMovimientoPersonalLicencia(final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
                crearLicencia(m, tramite, usuario);
            }
        }
    }

    /**
     * Se encarga de registrar los datos en el core por efectos de comision de servicio.
     *
     * @param tramite
     * @param usuario
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     */
    private void aplicarMovimientoPersonalComisionServicio(final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente()) {
                crearComisionServicio(m, tramite, usuario);
            }
        }
    }

    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @throws ServicioException
     */
    private void crearComisionServicio(final Movimiento m, final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        if (tramite.getTipoMovimiento().getRenovacionComisionServicio()) {
            if (!m.getListaComisionServicio().isEmpty()) {
                ComisionServicio cs = m.getListaComisionServicio().get(0);

                DistributivoDetalle dd = m.getDistributivoDetalle();
                dd.setTipoComisionServicio(cs.getTipoComisionServicio());
                dd.setFechaInicioComisionServicio(m.getRigeApartirDe());
                dd.setFechaFinComisionServicio(m.getFechaHasta());
                distributivoDetalleDao.actualizar(dd);

                DistributivoComisionServicio dcs = new DistributivoComisionServicio();
                dcs.setDistributivoDetalle(dd);
                dcs.setEntidad(cs.getInstitucion());
                dcs.setFechaCreacion(new Date());
                dcs.setFechaFin(m.getFechaHasta());
                dcs.setFechaInicio(m.getRigeApartirDe());
                dcs.setServidor(dd.getServidorComisionServicio());
                dcs.setTipoComisionServicio(cs.getTipoComisionServicio());
                dcs.setUsuarioCreacion(usuario.getUsuario());
                dcs.setVigente(Boolean.TRUE);
                dcs.setFinalizado(Boolean.FALSE);
                distributivoComisionServicioDao.crear(dcs);
            } else {
                throw new ServicioException("No se pudo registrar la comision de servicio");
            }
        } else {
            if (!m.getListaComisionServicio().isEmpty()) {
                ComisionServicio cs = m.getListaComisionServicio().get(0);

                DistributivoDetalle dd = m.getDistributivoDetalle();
                distributivoPuestoServicio.crearDistributivoDetalleHistorico(dd, usuario);
                actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), dd);
                if (dd.getServidor() != null) {
                    actualizarEstadoServidor(tramite.getTipoMovimiento(), dd.getServidor());
                }
                dd.setServidorComisionServicio(dd.getServidor());
                dd.setIdServidorComisionServicio(dd.getIdServidor());
                dd.setTipoComisionServicio(cs.getTipoComisionServicio());
                dd.setFechaInicioComisionServicio(m.getRigeApartirDe());
                dd.setFechaFinComisionServicio(m.getFechaHasta());
                if (!dd.getEstadoPuesto().getOcupado()) {
                    dd.setServidor(null);
                    dd.setIdServidor(null);
                }
                distributivoDetalleDao.actualizar(dd);

                DistributivoComisionServicio dcs = new DistributivoComisionServicio();
                dcs.setDistributivoDetalle(dd);
                dcs.setEntidad(cs.getInstitucion());
                dcs.setFechaCreacion(new Date());
                dcs.setFechaFin(m.getFechaHasta());
                dcs.setFechaInicio(m.getRigeApartirDe());
                dcs.setServidor(dd.getServidorComisionServicio());
                dcs.setTipoComisionServicio(cs.getTipoComisionServicio());
                dcs.setUsuarioCreacion(usuario.getUsuario());
                dcs.setVigente(Boolean.TRUE);
                dcs.setFinalizado(Boolean.FALSE);
                distributivoComisionServicioDao.crear(dcs);
            } else {
                throw new ServicioException("No se pudo registrar la comision de servicio");
            }
        }
    }

    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @throws ServicioException
     */
    private void crearLicencia(final Movimiento m, final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        DistributivoDetalle dd = m.getDistributivoDetalle();
        distributivoPuestoServicio.crearDistributivoDetalleHistorico(dd, usuario);
        actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), dd);
        if (dd.getServidor() != null) {
            actualizarEstadoServidor(tramite.getTipoMovimiento(), dd.getServidor());
        }
        if (!dd.getEstadoPuesto().getOcupado()) {
            dd.setServidor(null);
        }
        distributivoDetalleDao.actualizar(dd);
    }

    /**
     *
     * @param m
     * @param tramiteBitacora
     * @param tramite
     * @param usuario
     * @throws ServicioException
     */
    private void crearIngreso(final Movimiento m, final TramiteBitacora tramiteBitacora, final Tramite tramite,
            final UsuarioVO usuario) throws DaoException, ServicioException {
        Ingreso ingreso = m.getListaIngresos().get(0);
        Servidor s = servidorDao.buscar(m.getTipoIdentificacion(), m.getNumeroIdentificacion());
        if (s == null) {
            s = new Servidor();
            s.setTomaTransporte(Boolean.FALSE);
            s.setTipoIdentificacion(m.getTipoIdentificacion());
            s.setNumeroIdentificacion(m.getNumeroIdentificacion());
            s.setApellidosNombres(m.getApellidosNombres());
            s.setFechaCreacion(new Date());
            s.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            s.setVigente(Boolean.TRUE);
            if (ingreso.getFechaIngresoInstitucion() != null) {
                s.setFechaIngresoInstitucion(ingreso.getFechaIngresoInstitucion());
            }
            if (ingreso.getFechaIngresoSectorPublico() != null) {
                s.setFechaIngresoSectorPublico(ingreso.getFechaIngresoSectorPublico());
            }
            s.setRecibeFondoReserva(Boolean.TRUE);
            s.setClave(DigestUtils.md5Hex("12345"));
            s.setFechaCaducidad(null);
            s.setMensualizaDecimoCuarto(Boolean.TRUE);
            s.setMensualizaDecimoTercero(Boolean.TRUE);
            s.setCodigoEmpleado(contadorDao.generarCodigoServidor());
            s.setTipo(TipoServidorEnum.INTERNO.getCodigo());
            actualizarEstadoServidor(tramite.getTipoMovimiento(), s);
            s = servidorDao.crear(s);
            servidorDao.flush();
            servidorDao.refresh(s);

            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(tramite.
                    getInstitucionEjercicioFiscal().getInstitucion().getId(), s.getId());
            if (si == null) {
                si = new ServidorInstitucion();
                si.setIdServidor(s.getId());
                si.setIdInstitucion(tramite.getInstitucionEjercicioFiscal().getInstitucion().getId());
                si.setFechaIngreso(ingreso.getFechaIngresoInstitucion());
                si.setFechaCreacion(new Date());
                si.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
                si.setVigente(Boolean.TRUE);
                servidorInstitucionDao.crear(si);
            } else {
                si.setFechaIngreso(ingreso.getFechaIngresoInstitucion());
                si.setFechaActualizacion(new Date());
                si.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
                si.setVigente(Boolean.TRUE);
                servidorInstitucionDao.actualizar(si);
            }
            Vacacion v = new Vacacion();
            v.setFechaCreacion(new Date());
            v.setSaldo(0l);
            v.setSaldoProporcional(0l);
            v.setServidorInstitucion(si);
            v.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            v.setVigente(Boolean.TRUE);
            v.setSaldoMesActual(0l);
            v.setMesActual(0);
            v.setSaldoPerdida(0l);
            vacacionDao.crear(v);
        } else {
            if (s.getTomaTransporte() == null) {
                s.setTomaTransporte(Boolean.FALSE);
            }
            s.setApellidosNombres(m.getApellidosNombres());
            s.setFechaActualizacion(new Date());
            s.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
            if (ingreso.getFechaIngresoInstitucion() != null) {
                s.setFechaIngresoInstitucion(ingreso.getFechaIngresoInstitucion());
            }
            if (ingreso.getFechaIngresoSectorPublico() != null) {
                s.setFechaIngresoSectorPublico(ingreso.getFechaIngresoSectorPublico());
            }
            s.setClave(DigestUtils.md5Hex("12345"));
            s.setHorario(null);
            actualizarEstadoServidor(tramite.getTipoMovimiento(), s);
            servidorDao.actualizar(s);
            servidorDao.flush();
            servidorDao.refresh(s);
        }
        if (tramite.getTipoMovimiento().getAreaPuestoDestino()) {
            // puesto actual.
            DistributivoDetalle dd = distributivoDetalleDao.buscarPorId(m.getDistributivoDetalleId());
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(dd, usuario);
            actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), dd);
            dd.setIdServidor(null);
            dd.setFechaIngreso(null);
            dd.setFechaFin(null);
            dd.setFechaActualizacion(new Date());
            dd.setUsuarioActualizacion(usuario.getUsuario());
            if (!dd.getEstadoPuesto().getPuedeOcuparse()) {
                throw new ServicioException("(1) Se debe asignar un estado de puesto tipo VACANTE para el puesto No "
                        + dd.getCodigoPuesto());
            }
            distributivoDetalleDao.actualizar(dd);
            distributivoDetalleDao.flush();

            // puesto propuesto.
            if (m.getMovimientoSituacionPropuesta().getDistributivoDetalle() != null) {
                DistributivoDetalle ddPropuesta = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
                distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddPropuesta, usuario);
                ddPropuesta.setServidor(s);
                ddPropuesta.setIdServidor(s.getId());
                ddPropuesta.setMovimientoId(m.getId());
                ddPropuesta.setFechaIngreso(m.getRigeApartirDe());
                ddPropuesta.setFechaFin(m.getFechaHasta());
                if (tramite.getTipoMovimiento().getAreaComisionServicioInstitucionRequiriente()) {
                    ddPropuesta.setFechaInicioComisionServicio(m.getRigeApartirDe());
                    ddPropuesta.setFechaFinComisionServicio(m.getFechaHasta());
                }
                actualizarEstadoDistributivoPropuesto(tramite.getTipoMovimiento(), ddPropuesta);
                if (!ddPropuesta.getEstadoPuesto().getOcupado()) {
                    throw new ServicioException(
                            "(2) Se debe asignar un estado de puesto tipo OCUPADO para el puesto No "
                            + dd.getCodigoPuesto());
                }
                distributivoDetalleDao.actualizar(ddPropuesta);
            }
        } else {
            DistributivoDetalle dd = distributivoDetalleDao.buscarPorId(m.getDistributivoDetalleId());
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(dd, usuario);
            dd.setServidor(s);
            dd.setIdServidor(s.getId());
            dd.setMovimientoId(m.getId());
            dd.setFechaIngreso(m.getRigeApartirDe());
            dd.setFechaFin(m.getFechaHasta());
            if (tramite.getTipoMovimiento().getAreaComisionServicioInstitucionRequiriente()) {
                dd.setFechaInicioComisionServicio(m.getRigeApartirDe());
                dd.setFechaFinComisionServicio(m.getFechaHasta());
            }
            actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), dd);
            if (!dd.getEstadoPuesto().getOcupado()) {
                throw new ServicioException("(3) Se debe asignar un estado de puesto tipo OCUPADO para el puesto No "
                        + dd.getCodigoPuesto());
            }
            distributivoDetalleDao.actualizar(dd);
        }

        // verificar si hay un puesto propuesto.
        // eliminar roles anterior e ingresar rol normal.
        rolServidorDao.quitarVigencia(s.getId());
        RolServidor rs = new RolServidor();
        rs.setFechaCreacion(new Date());
        rs.setServidor(s);
        rs.setRol(new Rol(1l));
        rs.setUsuarioCreacion(usuario.getUsuario());
        rs.setVigente(Boolean.TRUE);
        rolServidorDao.crear(rs);
        // registro en reclutamiento.
        if (m.getDistributivoDetalleId() != null) {
            Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
            if (r != null) {
                r.setFechaActualizacion(new Date());
                r.setUsuarioActualizacion(tramite.getUsuarioCreacion());
                r.setEmMovimientoPersonal(false);
                r.setEstado(EstadoReclutamientoEnum.DISTRIBUTIVO.getCodigo());
                reclutamientoServicio.actualizarReclutamiento(r);
                //detalles del servidor
                administracionServicio.copiarDetallesReclutamientoAlServidor(s, r);
            }
        }
    }

    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @param pDestitucion
     * @throws ServicioException
     */
    private void crearCesacion(final Movimiento m, final Tramite tramite, final UsuarioVO usuario) throws
            ServicioException, DaoException {
        DistributivoDetalle dd = distributivoDetalleDao.buscarPorId(m.getDistributivoDetalleId());
        if (m.getTramite().getTipoMovimiento().getConLiquidacion()) {
//            liquidacionServicio.registrarLiquidacion(dd, usuario.getServidor().getNumeroIdentificacion(), m.
//                    getRigeApartirDe(), usuario.getInstitucionEjercicioFiscal());
        }

        distributivoPuestoServicio.crearDistributivoDetalleHistorico(dd, usuario);
        Servidor s = dd.getServidor();
        if (s == null) {
            throw new ServicioException("No existe servidor asociado al puesto");
        }
        s.setFechaActualizacion(new Date());
        s.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
        s.setFechaSalida(m.getRigeApartirDe());
        s.setHorario(null);
        actualizarEstadoServidor(tramite.getTipoMovimiento(), s);
        servidorDao.actualizar(s);
        // eliminar roles anterior e ingresar rol normal.
        rolServidorDao.quitarVigencia(s.getId());
        dd.setIdServidor(null);
        dd.setFechaActualizacion(new Date());
        //dd.setFechaIngreso(null);
        dd.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
        dd.setRmuSobrevalorado(BigDecimal.ZERO);
        dd.setRmu(dd.getEscalaOcupacional().getRmu());
        dd.setRmuEscala(dd.getEscalaOcupacional().getRmu());
        actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), dd);
        dd.setMovimientoId(m.getId());
        dd.setDistributivoDetalleEncargo(null);
        dd.setDistributivoDetalleSubrogacion(null);
        dd.setFechaInicioEncargo(null);
        dd.setFechaInicioSubrogacion(null);
        dd.setFechaFinEncargo(null);
        dd.setFechaFinSubrogacion(null);
        if (!dd.getEstadoPuesto().getPuedeOcuparse()) {
            throw new ServicioException("(4) Se debe asignar un estado de puesto tipo VACANTE para el puesto No "
                    + dd.getCodigoPuesto());
        }

        distributivoDetalleDao.actualizar(dd);
        // registra salida
        List<ServidorInstitucion> servidores
                = servidorInstitucionDao.buscarPorInstitucionServidor(
                        tramite.getInstitucionEjercicioFiscal().getInstitucion().getId(),
                        s.getTipoIdentificacion(),
                        s.getNumeroIdentificacion());
        for (ServidorInstitucion si : servidores) {
            si.setFechaSalida(m.getRigeApartirDe());
            si.setFechaActualizacion(new Date());
            si.setUsuarioActualizacion(usuario.getUsuario());
//                si.setVigente(Boolean.FALSE);
            servidorInstitucionDao.actualizar(si);
        }
    }

    /**
     *
     * @param m
     * @param tramiteBitacora
     * @param tramite
     * @param usuario
     * @throws ServicioException
     */
    private void crearRotacion(final Movimiento m, final Tramite tramite, final UsuarioVO usuario) throws DaoException,
            ServicioException {
        Ingreso i = m.getListaIngresos().get(0);
        if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.CAMBIO_ADMINISTRATIVO.getCodigo())) {
            // CAMBIO ADMINISTRATIVO.
            CambioAdministrativo ca = m.getListaCambioAdministrativo().get(0);
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            ddActual.setMovimientoId(m.getId());
            ddActual.setUnidadOrganizacionalCambioAdministrativoId(ca.getUnidadOrganizacional().getId());
            ddActual.setFechaMaximoCambioAdministrativo(m.getFechaHasta());
            distributivoDetalleDao.actualizar(ddActual);

        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASLADO_ADMINISTRATIVO.
                getCodigo())) {
            // TRASLADO ADMINISTRATIVO.
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            DistributivoDetalle ddPropuesto = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddPropuesto, usuario);
            Servidor servidor = m.getDistributivoDetalle().getServidor();

            // Cambio servidor.
            servidor.setFechaActualizacion(new Date());
            servidor.setUsuarioActualizacion(usuario.getUsuario());
            servidor.setHorario(null);
            actualizarEstadoServidor(tramite.getTipoMovimiento(), servidor);
            servidorDao.actualizar(servidor);
            servidorDao.flush();

            // Cambio Puesto propuesto.
            ddPropuesto.setServidor(servidor);
            ddPropuesto.setIdServidor(servidor.getId());
            //ddPropuesto.setFechaIngreso(new Date());
            ddPropuesto.setFechaActualizacion(new Date());
            ddPropuesto.setUsuarioActualizacion(usuario.getUsuario());
            ddPropuesto.setMovimientoId(m.getId());
            actualizarEstadoDistributivoPropuesto(tramite.getTipoMovimiento(), ddPropuesto);
            if (!ddPropuesto.getEstadoPuesto().getOcupado()) {
                throw new ServicioException("(5) Se debe asignar un estado de puesto tipo OCUPADO para el puesto No "
                        + ddPropuesto.getCodigoPuesto());
            }

            distributivoDetalleDao.actualizar(ddPropuesto);
            distributivoDetalleDao.flush();

            // Cambio Puesto actual.
            ddActual.setIdServidor(null);
            ddActual.setFechaActualizacion(new Date());
            ddActual.setUsuarioActualizacion(usuario.getUsuario());
            //ddActual.setFechaFin(new Date());
            ddActual.setMovimientoId(m.getId());
            actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), ddActual);
            if (!ddActual.getEstadoPuesto().getPuedeOcuparse()) {
                throw new ServicioException("(6) Se debe asignar un estado de puesto tipo VACANTE para el puesto No "
                        + ddActual.getCodigoPuesto());
            }
            distributivoDetalleDao.actualizar(ddActual);
            distributivoDetalleDao.flush();

        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.
                getCodigo())) {
            // TRASPASO ADMINISTRATIVO.
            Traspaso t = m.getListaTraspaso().get(0);
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            Distributivo d = distributivoDao.buscar(t.getUnidadOrganizacional().getCodigo(), ddActual.getDistributivo().
                    getModalidadLaboral().getCodigo(), tramite.getInstitucionEjercicioFiscal().getId());
            if (d == null) {
                // nuevo
                d = new Distributivo();
                d.setContadorPartida(0l);
                d.setFechaCreacion(new Date());
                d.setIdUnidadOrganizacional(t.getUnidadOrganizacional().getId());
                d.setIdInstitucionEjercicioFiscal(tramite.getInstitucionEjercicioFiscal().getId());
                d.setIdModalidadLaboral(ddActual.getDistributivo().getModalidadLaboral().getId());
                d.setNombre("");
                d.setTotalDetalles(1l);
                d.setUsuarioCreacion(usuario.getUsuario());
                d.setVigente(Boolean.TRUE);
                distributivoDao.crear(d);
                distributivoDao.flush();
                ddActual.setDistributivo(d);
                ddActual.setIdDistributivo(d.getId());
                ddActual.setUnidadPresupuestaria(t.getUnidadPresupuestaria());
                ddActual.setFechaActualizacion(new Date());
                ddActual.setUsuarioActualizacion(usuario.getUsuario());
            } else {
                // existente
                ddActual.setDistributivo(d);
                ddActual.setIdDistributivo(d.getId());
                ddActual.setUnidadPresupuestaria(t.getUnidadPresupuestaria());
                ddActual.setFechaActualizacion(new Date());
                ddActual.setUsuarioActualizacion(usuario.getUsuario());
            }
            distributivoDetalleDao.actualizar(ddActual);
        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.SUBROGACIONES.getCodigo())) {
            // INICIAR SUBROGACIONES
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            DistributivoDetalle ddPropuesto = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
            ddActual.setDistributivoDetalleSubrogacion(ddPropuesto);
            ddActual.setFechaInicioSubrogacion(m.getRigeApartirDe());
            ddActual.setFechaFinSubrogacion(m.getFechaHasta());
            ddActual.setMovimientoId(m.getId());
            ddActual.setMovimientoId(m.getId());
            distributivoDetalleDao.actualizar(ddActual);
        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.ENCARGO.getCodigo())) {
            // INICIAR ENCARGO
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            DistributivoDetalle ddPropuesto = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddPropuesto, usuario);
            ddActual.setDistributivoDetalleEncargo(ddPropuesto);
            ddActual.setFechaInicioEncargo(m.getRigeApartirDe());
            ddActual.setFechaFinEncargo(m.getFechaHasta());
            ddActual.setMovimientoId(m.getId());
            distributivoDetalleDao.actualizar(ddActual);
        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.FIN_SUBROGACIONES.getCodigo())) {
            // FINALIZAR SUBROGACIONES
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            ddActual.setFechaFinSubrogacion(m.getRigeApartirDe());
            ddActual.setMovimientoId(m.getId());
            distributivoDetalleDao.actualizar(ddActual);
        } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.FIN_ENCARGO.getCodigo())) {
            // FINALIZAR ENCARGO
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            ddActual.setFechaFinEncargo(m.getRigeApartirDe());
            ddActual.setMovimientoId(m.getId());
            distributivoDetalleDao.actualizar(ddActual);
        } else {
            // MODIFICACIONES
            DistributivoDetalle ddActual = m.getDistributivoDetalle();
            Servidor servidor = ddActual.getServidor();
            distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddActual, usuario);
            actualizarEstadoDistributivoActual(tramite.getTipoMovimiento(), ddActual);
            if (ddActual.getServidor() != null) {
                actualizarEstadoServidor(tramite.getTipoMovimiento(), ddActual.getServidor());
            }
            if (ddActual.getEstadoPuesto().getPuedeOcuparse()) {
                ddActual.setServidor(null);
                ddActual.setIdServidor(null);
                ddActual.setFechaIngreso(null);
                ddActual.setFechaInicio(null);
            } else {
                //ddActual.setFechaIngreso(m.getRigeApartirDe());
            }
            ddActual.setEscalaOcupacional(m.getEscalaOcupacional());
            ddActual.setIdEscalaOcupacional(m.getEscalaOcupacional().getId());
            ddActual.setRmuEscala(m.getEscalaOcupacional().getRmu());
            ddActual.setRmuSobrevalorado(m.getRmuSobrevalorado());
            ddActual.setFechaIngreso(i.getFechaIngresoInstitucion());
            if (ddActual.getRmuSobrevalorado() != null
                    && ddActual.getRmuSobrevalorado().compareTo(ddActual.getRmuEscala()) > 0) {
                ddActual.setRmu(ddActual.getRmuSobrevalorado());
            } else {
                ddActual.setRmu(ddActual.getRmuEscala());
            }
            distributivoDetalleDao.actualizar(ddActual);
            if (tramite.getTipoMovimiento().getAreaPuestoDestino()) {
                DistributivoDetalle ddPropuesto = m.getMovimientoSituacionPropuesta().getDistributivoDetalle();
                distributivoPuestoServicio.crearDistributivoDetalleHistorico(ddPropuesto, usuario);
                actualizarEstadoDistributivoPropuesto(tramite.getTipoMovimiento(), ddPropuesto);
                if (ddPropuesto.getEstadoPuesto().getOcupado()) {
                    ddPropuesto.setServidor(servidor);
                    ddPropuesto.setIdServidor(servidor.getId());
                    //ddPropuesto.setFechaIngreso(m.getRigeApartirDe());
                }
            }
        }
    }

    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @throws ServicioException
     */
//    private void crearRegimenDisciplinario(final Movimiento m, final Tramite tramite, final UsuarioVO usuario) throws
//            ServicioException {
//        RegimenDisciplinarioDTO dto = new RegimenDisciplinarioDTO();
//        dto.setDenominacionPuestoNombre(m.getPuestoInstitucionCoreNombre());
//        dto.setEstadoPuestoId(m.getEstadoPuestoFinTramite());
//        dto.setEstadoServidorId(m.getEstadoPersonalFin());
//        dto.setFechaRigePartirDe(m.getRigeApartirDe());
//        dto.setInstitucionId(tramite.getInstitucionEjercicioFiscal().getId());
//        dto.setInstitucionNombre(tramite.getInstitucionEjercicioFiscal().getInstitucion().getNombre());
//        dto.setOrganigramaPosicionalId(m.getOrganigramaPosicionalId());
//        dto.setOrganigramaPosicionalIndividualId(m.getOrganigramaPosicionalIndividualId());
//        dto.setServidorId(m.getServidorId());
//        dto.setServidorPuestoId(m.getServidorPuestoId());
//        dto.setUnidadAdministrativaNombre(m.getUnidadAdministrativaCoreNombre());
//        dto.setUsuario(usuario.getCedulaUsuario());
//        dto.setMotivoSalidaId(tramite.getTipoMovimiento().getMotivoSalidaId());
//        movimientoPersonalServicio.efectuarRegimenDisciplinarioPorSuspensionTemporalSinGoceDeRemuneracion(dto, tramite.
//                getInstitucionEjercicioFiscal().getId());
//
//    }
    /**
     *
     * @param m
     * @param usuario
     * @param s
     * @throws ServicioException
     */
//    private void crearSubrogacion(final Movimiento m, final UsuarioVO usuario, final Subrogacion s) throws
//            ServicioException {
//        SubrogacionDTO dto = new SubrogacionDTO();
//        dto.setOrganigramaPosicionalId(m.getOrganigramaPosicionalId());
//        dto.setOrganigramaPosicionalIndividualId(m.getOrganigramaPosicionalIndividualId());
//        dto.setServidorId(m.getServidorId());
//        dto.setServidorPuestoId(m.getServidorPuestoId());
//        dto.setUsuario(usuario.getCedulaUsuario());
//        dto.setServidorPuestoASubrogarId(s.getServidorPuestoId());
//        movimientoPersonalServicio.efectuarSubrogacion(dto, m.getTramite().getInstitucionEjercicioFiscal().getId());
//    }
    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @param ta
     * @throws ServicioException
     */
//    private void crearTrasladoAdministrativo(final Movimiento m, final Tramite tramite, final UsuarioVO usuario,
//            final TrasladoAdministrativo ta) throws ServicioException {
//        TrasladoAdministrativoDTO dto = new TrasladoAdministrativoDTO();
//        dto.setOrganigramaPosicionalId(m.getOrganigramaPosicionalId());
//        dto.setOrganigramaPosicionalIndividualId(m.getOrganigramaPosicionalIndividualId());
//        dto.setOrganigramaPosicionalIndividualDestinoId(m.getMovimientoSituacionPropuesta().
//                getOrganigramaPosicionalIndividualCoreId());
//        dto.setServidorId(m.getServidorId());
//        dto.setServidorPuestoId(m.getServidorPuestoId());
//        dto.setEstadoServidorId(m.getEstadoPersonalFin());
//        dto.setEstadoPuestoSituacionActualId(tramite.getTipoMovimiento().getEstadoPuestoFinalCore());
//        dto.setEstadoPuestoSituacionPropuestaId(tramite.getTipoMovimiento().getEstadoPuestoFinalPropuestaCore());
//        dto.setUsuario(usuario.getCedulaUsuario());
//        dto.setInstitucionId(tramite.getInstitucionEjercicioFiscal().getId());
//        dto.setInstitucionNombre(tramite.getInstitucionEjercicioFiscal().getInstitucion().getNombre());
//        dto.setUnidadAdministrativaNombre(ta.getUnidadAdministrativaCoreNombre());
//        dto.setDenominacionPuestoNombre(m.getPuestoInstitucionCoreNombre());
//        dto.setFechaRigePartirDe(m.getRigeApartirDe());
//        dto.setMotivoSalidaId(tramite.getTipoMovimiento().getMotivoSalidaId());
//        movimientoPersonalServicio.efectuarTrasladoAdministrativo(dto, tramite.getInstitucionEjercicioFiscal().getId());
//    }
    /**
     *
     * @param m
     * @param tramite
     * @param usuario
     * @param t
     * @throws ServicioException
     */
//    private void crearTraspaso(final Movimiento m, final Tramite tramite, final UsuarioVO usuario, final Traspaso t)
//            throws ServicioException {
//        TraspasoDTO dto = new TraspasoDTO();
//        dto.setOrganigramaPosicionalIndividualId(m.getOrganigramaPosicionalIndividualId());
//        dto.setOrganigramaPosicionalId(m.getOrganigramaPosicionalId());
//        dto.setEstadoPuestoSituacionActualId(tramite.getTipoMovimiento().getEstadoPuestoFinalCore());
//        dto.setEstadoPuestoSituacionPropuestaId(tramite.getTipoMovimiento().getEstadoPuestoFinalPropuestaCore());
//        dto.setUsuario(usuario.getCedulaUsuario());
//        dto.setDenominacionPuestoId(t.getDenominacionPuestoCoreId());
//        dto.setDenominacionPuestoNombre(m.getPuestoInstitucionCoreNombre());
//        dto.setEstructuraOrganizacionalDestinoId(t.getUnidadAdminitrativaCoreId());
//        dto.setEstructuraOrganizacionalDestinoNombre(t.getUnidadAdministrativaCoreNombre());
//        dto.setInstitucionId(tramite.getInstitucionEjercicioFiscal().getId());
//        dto.setInstitucionNombre(tramite.getInstitucionEjercicioFiscal().getInstitucion().getNombre());
//        dto.setFechaRigePartirDe(m.getRigeApartirDe());
//        dto.setMotivoSalidaId(tramite.getTipoMovimiento().getMotivoSalidaId());
//        dto.setServidorId(m.getServidorId());
//        dto.setServidorPuestoId(m.getServidorPuestoId());
//        dto.setEstadoServidorId(m.getEstadoPersonalFin());
//        movimientoPersonalServicio.efectuarTraspasoMismaInstitucion(dto, tramite.getInstitucionEjercicioFiscal().getId());
//    }
    /**
     *
     * @param tipoMovimiento
     * @param distributivoDetalle
     */
    private void actualizarEstadoDistributivoActual(final TipoMovimiento tipoMovimiento,
            final DistributivoDetalle distributivoDetalle) throws DaoException {
        EstadoPuesto ep = null;
        if (tipoMovimiento.getEstadoPuestoFinalCore() == null) {
            if (distributivoDetalle.getIdEstadoPuesto() == null) {
                ep = estadoPuestoDao.buscarPredeterminado();
            }
        } else {
            ep = estadoPuestoDao.buscarPorId(tipoMovimiento.getEstadoPuestoFinalCore());
        }
        if (ep != null) {
            distributivoDetalle.setIdEstadoPuesto(ep.getId());
            distributivoDetalle.setEstadoPuesto(ep);
        }
    }

    /**
     *
     * @param tipoMovimiento
     * @param distributivoDetalle
     */
    private void actualizarEstadoDistributivoPropuesto(final TipoMovimiento tipoMovimiento,
            final DistributivoDetalle distributivoDetalle) throws DaoException {
        if (tipoMovimiento.getEstadoPuestoFinalPropuestaCore() == null) {
            if (distributivoDetalle.getIdEstadoPuesto() == null) {
                EstadoPuesto ep = estadoPuestoDao.buscarPredeterminado();
                if (ep != null) {
                    distributivoDetalle.setIdEstadoPuesto(ep.getId());
                    distributivoDetalle.setEstadoPuesto(ep);
                }
            }
        } else {
            EstadoPuesto ep = estadoPuestoDao.buscarPorId(tipoMovimiento.getEstadoPuestoFinalPropuestaCore());
            distributivoDetalle.setIdEstadoPuesto(ep.getId());
            distributivoDetalle.setEstadoPuesto(ep);
        }
    }

    /**
     *
     * @param tipoMovimiento
     * @param servidor
     */
    private void actualizarEstadoServidor(final TipoMovimiento tipoMovimiento,
            final Servidor servidor) throws DaoException {
        if (tipoMovimiento.getEstadoPersonalFinalCore() == null) {
            if (servidor.getEstadoPersonalId() == null) {
                EstadoPersonal e = estadoPersonalDao.buscarPredeterminado();
                if (e != null) {
                    servidor.setEstadoPersonalId(e.getId());
                    servidor.setEstadoPersonal(e);
                }
            }
        } else {
            EstadoPersonal e = estadoPersonalDao.buscarPorId(tipoMovimiento.getEstadoPersonalFinalCore());
            servidor.setEstadoPersonalId(e.getId());
            servidor.setEstadoPersonal(e);
        }
    }

    /**
     *
     * @param tramite
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void guardarTrayectoriaLaboral(Tramite tramite) throws DaoException {
        for (Movimiento m : tramite.getListaMovimientos()) {
            if (m.getVigente() && m.getDistributivoDetalleId() != null) {
                TrayectoriaLaboral tl = new TrayectoriaLaboral();
                tl.setApellidos(m.getApellidos());
                if (m.getAccionPersonalExplicacion() != null) {
                    tl.setExplicacion(m.getAccionPersonalExplicacion());
                } else {
                    if (!m.getListaIngresos().isEmpty()) {
                        tl.setExplicacion(m.getListaIngresos().get(0).getAntecedentesContrato());
                    }
                }
                tl.setFechaCreacion(new Date());
                tl.setFechaDesde(UtilFechas.formatearLargo(m.getRigeApartirDe()));
                if (m.getFechaHasta() != null) {
                    tl.setFechaHasta(UtilFechas.formatearLargo(m.getFechaHasta()));
                }

                tl.setGrado(m.getDistributivoDetalle().getEscalaOcupacional().getGrado());
                tl.setGrupo(m.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico());
                tl.setClase(m.getTramite().getTipoMovimiento().getClase().getNombre());
                tl.setNumeroDocumentoHabilitante(m.getNumeroDocumentoHabilitante());
                tl.setUnidadPresupuestaria(m.getDistributivoDetalle().getUnidadPresupuestaria().getNombre());
                tl.setUnidadOrganizacional(m.getDistributivoDetalle().getDistributivo().
                        getUnidadOrganizacional().getRuta());
                for (CertificacionPresupuestaria cp : m.getDistributivoDetalle().
                        getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                    if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                            m.getDistributivoDetalle().getDistributivo().getModalidadLaboral().getId())) {
                        tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                        break;
                    }
                }
                tl.setNombres(m.getNombres());
                tl.setNumeroIdentificacion(m.getNumeroIdentificacion());
                tl.setNumeroMovimiento(m.getTramite().getNumericoTramite());
                tl.setRegimenLaboral(m.getDistributivoDetalle().getEscalaOcupacional().getNivelOcupacional().
                        getRegimenLaboral().getNombre());
                tl.setRmu(m.getDistributivoDetalle().getRmu());
                tl.setRmuSobrevalorado(m.getDistributivoDetalle().getRmuSobrevalorado());
                tl.setTipoIdentificacion(m.getTipoIdentificacion());
                tl.setTipoMovimiento(m.getTramite().getTipoMovimiento().getNombre());
                tl.setUsuarioCreacion(m.getUsuarioCreacion());
                tl.setVigente(Boolean.TRUE);
                tl.setElaborador(m.getTramite().getUsuarioAsignadoNombreElaborador());
                tl.setFechaElaborador(m.getTramite().getTramiteBitacora().getFechaElaboracion());
                tl.setDenominacionPuesto(m.getDistributivoDetalle().getEscalaOcupacional().getNombre());
                tl.setLegalizador(m.getTramite().getUsuarioAsignadoNombre());
                tl.setFechaLegalizador(new Date());
                trayectoriaLaboralDao.crear(tl);
            }
        }
    }

}
