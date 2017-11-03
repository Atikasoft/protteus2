/*
 *  ReglaServicio.java
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
 *  Nov 14, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.*;
import ec.com.atikasoft.proteus.dto.AvanzaInstanciaDTO;
import ec.com.atikasoft.proteus.dto.IniciaInstanciaDTO;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.enums.formatos.FormatoIngresoEnum;
import ec.com.atikasoft.proteus.enums.formatos.FormatoSalidaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.servicio.helpers.TramiteHelper;
import ec.com.atikasoft.proteus.util.*;
import ec.com.atikasoft.proteus.vo.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Brinda operaciones para el tratamiento de tramites.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class TramiteServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(TramiteServicio.class.getCanonicalName());

    /**
     * Servicio de gestion.
     */
    @EJB
    private GestionServicio gestionServicio;

    /**
     * DAO de Mobimientos Bitacora.
     */
    @EJB
    private MovimientoBitacoraDao movimientosBitacoraDao;

    /**
     * Dao de Ingresos.
     */
    @EJB
    private IngresoDao ingresoDao;

    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Dao de Cesacion.
     */
    @EJB
    private CesacionDao cesacionDao;

    /**
     * Dao de regimen disciplinario.
     */
    @EJB
    private RegimenDisciplinarioDao regimenDisciplinarioDao;

    /**
     * Dao de licencias.
     */
    @EJB
    private LicenciaDao licenciaDao;

    /**
     * Dao de comisiones de servicios.
     */
    @EJB
    private ComisionServicioDao comisionServicioDao;

    /**
     * Dao de tramites.
     */
    @EJB
    private TramiteDao tramiteDao;

    /**
     * Dao de Movimiento.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Servicio de reglas.
     */
    @EJB
    private ReglaServicio reglaServicio;

    /**
     * Dao de la bitacora del tramite.
     */
    @EJB
    private TramiteBitacoraDao tramiteBitacoraDao;

    /**
     * Dao de institucion.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionDao;

    /**
     * DAO de archivo.
     */
    @EJB
    private ArchivoDao archivoDao;

    /**
     * Servicio de eventos.
     */
    @EJB
    private EventosServicio eventosServicio;

    /**
     * Dao de devengamientos.
     */
    @EJB
    private DevengamientoDao devengamientoDao;

    /**
     * Dao de devengamientos.
     */
    @EJB
    private TipoMovimientoDao tipoMovimientoDao;

    /**
     * Dao del auxiliar del tramite.
     */
    @EJB
    private TramiteAuxiliarDao tramiteAuxiliarDao;

    /**
     * Dao de validaciones.
     */
    @EJB
    private ValidacionDao validacionDao;

    /**
     * Dao de situacion actual del movimiento.
     */
    @EJB
    private MovimientoSituacionActualDao movimientoSituacionActualDao;

    /**
     * Dao de situacion propuesta del movimiento.
     */
    @EJB
    private MovimientoSituacionPropuestaDao movimientoSituacionPropuestaDao;

    /**
     * Dao de cambio administrativo.
     */
    @EJB
    private CambioAdministrativoDao cambioAdministrativoDao;

    /**
     * Dao de traslados adm inistrativo.
     */
    @EJB
    private TrasladoAdministrativoDao trasladoAdministrativoDao;

    /**
     * dao de traspasos.
     */
    @EJB
    private TraspasoDao traspasoDao;

    /**
     * Dao de subrogaciones.
     */
    @EJB
    private SubrogacionDao subrogacionDao;

    /**
     * Dao de encaergo.
     */
    @EJB
    private EncargoDao encargoDao;

    /**
     * Servicios de tareas.
     */
    @EJB
    private TareaServicio tareaServicio;

    /**
     * Dao de finalizaicon.
     */
    @EJB
    private FinalizacionDao finalizacionDao;

    /**
     * Servicio de reclutamiento.
     */
    @EJB
    private ReclutamientoServicio reclutamientoServicio;

    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     *
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     *
     */
    @EJB
    private TipoMovimientoRequisitoDao tipoMovimientoRequisitoDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Constructor sin argumentos.
     */
    public TramiteServicio() {
        super();
    }

    /**
     * Determina el tipo de flujo que le correcponde al tramite.
     *
     * @param tipoFlujo
     * @return
     */
    private TipoFlujoEnum determinarTipoFlujo(final String tipoFlujo) {
        TipoFlujoEnum enumeracion = null;
        for (TipoFlujoEnum en : TipoFlujoEnum.values()) {
            if (tipoFlujo.equals(en.getCodigo())) {
                enumeracion = en;
                break;
            }
        }
        return enumeracion;
    }

    /**
     * Este método guarda el tramite.
     *
     * @param t Tramite
     * @param usuario
     * @return Tramite
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public Tramite crearTramite(Tramite t, final UsuarioVO usuario) throws ServicioException {
        try {
            TipoMovimiento tipoMovimiento = tipoMovimientoDao.buscarPorId(t.getTipoMovimiento().getId());
            TipoFlujoEnum tipoFlujo = determinarTipoFlujo(tipoMovimiento.getTipoFlujo().trim());
            InstitucionEjercicioFiscal institucion = institucionDao.buscarPorId(
                    t.getInstitucionEjercicioFiscal().getId());
            t.setCodigoInstitucion(usuario.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getCodigo());
            StringBuilder nombreUnidad = new StringBuilder();
            nombreUnidad.append(usuario.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
            t.setCodigoEjercicioFiscal(Integer.parseInt(institucion.getEjercicioFiscal().getNombre()));
            t.setNumericoTramite(generarNumeroTramite(institucion));
            t.setIdentificadorProceso(1L);
            t.setEstado("X");
            t.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            t.setFechaCreacion(new Date());
            t.setTipoGestion(tipoMovimiento.getTipoGestionDesconcentrado() ? TipoGestionEnum.DESCONCETRADA.getCodigo()
                    : TipoGestionEnum.CENTRALIZADA.getCodigo());
            t.setCodigoProceso(tipoFlujo.getCodigoProceso());
            t.setConSolicitud(tipoMovimiento.getConSolicitud());
            t.setUsuarioAsignadoCedula(usuario.getServidor().getNumeroIdentificacion());
            t.setUsuarioAsignadoNombre(usuario.getServidor().getApellidosNombres());
            t.setUsuarioAsignadoCedulaElaborador(usuario.getServidor().getNumeroIdentificacion());
            t.setUsuarioAsignadoNombreElaborador(usuario.getServidor().getApellidosNombres());
            t.setConSolicitud(false);
            t.setVigente(Boolean.TRUE);
            t.setDescripcion(t.getDescripcion().toUpperCase());
            t = tramiteDao.crear(t);
            tramiteDao.flush();

            IniciaInstanciaDTO dto = new IniciaInstanciaDTO();
            dto.setCodigoInstitucion(usuario.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getCodigo());
            dto.setNombreInstitucion(nombreUnidad.toString());
            dto.setEjercicioFiscal(t.getCodigoEjercicioFiscal());
            dto.setCodigoProceso(tipoFlujo.getCodigoProceso());
            dto.setIdentificadorExterno(t.getId());
            dto.setNumeroExterno(t.getNumericoTramite());
            dto.setComentario(t.getDescripcion());
            dto.setUsuario(usuario.getServidor().getNumeroIdentificacion());
            dto.setNombreUsuario(usuario.getServidor().getApellidosNombres());
            dto.setInstitucionId(usuario.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
            dto.setOrigen(OrigenTramiteEnum.SERVIDOR.getCodigo());
            Instancia instancia = gestionServicio.iniciarInstancia(dto);
            t.setIdentificadorProceso(instancia.getId());
            t.setEstado(instancia.getFase().getNombre());
            t.setCodigoFase(instancia.getFase().getCodigo());

            tramiteDao.actualizar(t);

            // registrar bitacora.
            TramiteBitacora tb = new TramiteBitacora();
            tb.setCedulaElaboracion(usuario.getServidor().getNumeroIdentificacion());
            tb.setNombreElaboracion(usuario.getServidor().getApellidosNombres());
            if (usuario.getDistributivoDetalle().getDenominacionPuesto() != null) {
                tb.setCargoElaboracion(usuario.getDistributivoDetalle().getDenominacionPuesto().getNombre());
            } else {
                tb.setCargoElaboracion("*****************");
            }

            tb.setFechaElaboracion(new Date());
            tb.setFechaCreacion(new Date());
            tb.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            tb.setVigente(Boolean.TRUE);
            tb.setTramite(t);
            tramiteBitacoraDao.crear(tb);

            // registrar auxiliar del tramite.
            TramiteAuxiliar ta = new TramiteAuxiliar();
            ta.setTipoFlujo(tipoMovimiento.getTipoFlujo());

            if (tipoMovimiento.getHorario() == null) {
                ta.setHorario(false);
            } else {
                ta.setHorario(tipoMovimiento.getHorario().equals(
                        HorarioConfiguracionTipoMovimientoEnum.SI.getCodigo()));
            }
            ta.setPeriodoHorario(tipoMovimiento.getPeriodoControl());
            ta.setValorHorario(tipoMovimiento.getValorPeriodoControl());
            ta.setPeriodoTiempoMaximo(tipoMovimiento.getPeriodoTiempoMaximo());
            ta.setTiempoMaximo(tipoMovimiento.getTiempoMaximo());

            ta.setFechaCreacion(new Date());
            ta.setTramite(t);
            ta.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            ta.setVigente(Boolean.TRUE);
            tramiteAuxiliarDao.crear(ta);
            t.setTramiteAuxiliar(ta);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException(e);
        }
    }

    /**
     * Este método actualiza el tramite.
     *
     * @param t Tramite
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void actualizarTramite(final Tramite t) throws ServicioException {
        try {

            t.setDescripcion(t.getDescripcion().toUpperCase());
            tramiteDao.actualizar(t);
            // actualizar la instancia del tramite.
//            gestionServicio.actualizarDescripcionInstanticia(t.getId(), t.getDescripcion());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Asignar el usuario elaboador mediante balanceao.
     *
     * @param usuarios
     * @param codigoInstitucion
     * @param ejercicioFiscal
     * @return
     * @throws DaoException
     */
//    public UsuarioRol asignarTramiteAUsuarioElaborador(final List<UsuarioRol> usuarios, final String codigoInstitucion)
//            throws DaoException {
//        UsuarioRol usuarioAsignado = null;
//        long minimo = 9999999;
//        for (UsuarioRol usuario : usuarios) {
//            long contador = tramiteDao.contarPorEstadoYUsuario(EstadosTramiteEnum.ELABORACION.getCodigo(), usuario.
//                    getUsuario().getCedula(), codigoInstitucion);
//            if (contador < minimo) {
//                usuarioAsignado = usuario;
//                minimo = contador;
//            }
//        }
//        return usuarioAsignado;
//    }
    /**
     * Este método busca los movimientos segun el tramite.
     *
     * @param idTramite Long
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Movimiento> buscarMovimientosPorTramite(final Long idTramite) throws ServicioException {
        try {
            return movimientoDao.buscarPorTramite(idTramite);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<String> validarNumeroRegistroMovimientos(final List<Movimiento> movimientos) throws ServicioException {
        List<String> repetidos = new ArrayList<String>();
        return repetidos;
    }

    /**
     * Este metodo busca un movimiento según el Id.
     *
     * @param idMovimiento Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Movimiento buscarMovimientoPorId(final Long idMovimiento) throws ServicioException {
        try {
            return movimientoDao.buscarPorId(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método guarda los Movimientos por tramite.
     *
     * @param tramite Tramite
     * @param listaPuestos List
     * @param usuario StringIn
     * @param procesaPrimerRegistro
     * @throws ServicioException
     */
    public void guardarMovimientos(final Tramite tramite, final List<DistributivoDetalle> listaPuestos,
            final String usuario, final Boolean procesaPrimerRegistro) throws ServicioException {
        try {
            if (!listaPuestos.isEmpty()) {
                Set<Long> puestos = new HashSet<Long>();
                if (procesaPrimerRegistro) {
                    crearMovimiento(usuario, listaPuestos.get(0), tramite);
                } else {
                    for (DistributivoDetalle puesto : listaPuestos) {
                        if (!puestos.contains(puesto.getId())) {
                            if (puesto.getDenominacionPuesto() == null) {
                                puesto.setDenominacionPuesto(new DenominacionPuesto());
                            }
                            crearMovimiento(usuario, puesto, tramite);
                            puestos.add(puesto.getId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método guarda los Movimientos por tramite.
     *
     * @param tramite Tramite
     * @param listaPuestos List
     * @param usuario StringIn
     * @throws ServicioException
     */
    public void guardarMovimientosMasivos(final Tramite tramite, final List<CargaMasivaMovimientoVO> listaPuestos,
            final UsuarioVO usuario) throws ServicioException {
        try {
            List<TipoMovimientoRequisito> requisitos
                    = tipoMovimientoRequisitoDao.buscarPorTipoMovimientoId(tramite.getTipoMovimiento().getId());
            int i = 1;
            for (CargaMasivaMovimientoVO puesto : listaPuestos) {
                //System.out.println(":" + i);
                crearMovimientoMasivos(usuario, puesto, tramite, requisitos);
                i++;
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param usuario
     * @param sopi
     * @param tramite
     * @throws DaoException
     */
    private void crearMovimiento(final String usuario, final DistributivoDetalle puesto, final Tramite tramite)
            throws DaoException {

        Movimiento m = new Movimiento();
        m.setUsuarioCreacion(usuario);
        m.setFechaCreacion(new Date());
        m.setVigente(Boolean.TRUE);
        if (tramite.getTipoMovimiento().getRenovacionComisionServicio() != null && tramite.getTipoMovimiento().
                getRenovacionComisionServicio()) {
            if (puesto.getServidorComisionServicio() != null) {
                m.setTipoIdentificacion(puesto.getServidorComisionServicio().getTipoIdentificacion());
                m.setNumeroIdentificacion(puesto.getServidorComisionServicio().getNumeroIdentificacion());
                m.setApellidosNombres(puesto.getServidorComisionServicio().getApellidosNombres());
                m.setApellidos(puesto.getServidorComisionServicio().getApellidos());
                m.setNombres(puesto.getServidorComisionServicio().getNombres());
            }
        } else {
            if (puesto.getServidor() != null) {
                m.setTipoIdentificacion(puesto.getServidor().getTipoIdentificacion());
                m.setNumeroIdentificacion(puesto.getServidor().getNumeroIdentificacion());
                m.setApellidosNombres(puesto.getServidor().getApellidosNombres());
                m.setApellidos(puesto.getServidor().getApellidos());
                m.setNombres(puesto.getServidor().getNombres());
            }

        }

        m.setRmu(puesto.getEscalaOcupacional().getRmu());
        m.setRmuSobrevalorado(puesto.getRmuSobrevalorado());
        m.setCertificacionPresupuestaria(puesto.getCertificacionPresupuestaria());
        m.setNumeroRegistro(null);
        m.setFechaRegistro(null);
        m.setJustificacion("");
        m.setNumeroActoAdministrativo("");
        m.setTramite(tramite);
        m.setRmuLetras(UtilNumeros.getInstancia().transformarNumerosLetras(m.getRmu()));
        m.setEscalaOcupacional(puesto.getEscalaOcupacional());
        m.setUnidadPresupuestaria(puesto.getUnidadPresupuestaria());
        m.setUnidadOrganizacional(puesto.getDistributivo().getUnidadOrganizacional());
        m.setDistributivoDetalleId(puesto.getId());
        m.setUnidadPresupuestaria(puesto.getUnidadPresupuestaria());
        m.setUnidadOrganizacional(puesto.getDistributivo().getUnidadOrganizacional());
        m.setConJustificacion(Boolean.FALSE);
        m.setSituacionActual(tramite.getTipoMovimiento().getSituacionActual());
        m.setSituacionPropuesta(tramite.getTipoMovimiento().getSituacionPropuesta());
        m.setAccionPersonalExplicacion(tramite.getTramiteAuxiliar() == null ? "" : tramite.getTramiteAuxiliar().
                getExplicacion());
        m.setFinalizado(Boolean.FALSE);
        if (tramite.getTipoMovimiento().getAreaAccionPersonal() && tramite.getTipoMovimiento().getExplicacion()
                != null) {
            m.setAccionPersonalExplicacion(tramite.getTipoMovimiento().getExplicacion());
        }
        movimientoDao.crear(m);
        movimientoDao.flush();
        // copia la situacion actual.
        crearSituacionActualYPropuesta(puesto, m, usuario);
        // crear en detalle de acuerdo al grupo.
        crearContenido(m, tramite, puesto);
    }

    /**
     *
     * @param usuario
     * @param sopi
     * @param tramite
     * @throws DaoException
     */
    private void crearMovimientoMasivos(final UsuarioVO usuario, final CargaMasivaMovimientoVO vo,
            final Tramite tramite, List<TipoMovimientoRequisito> requisitos) throws DaoException, ParseException,
            ServicioException, UnsupportedEncodingException {
        String grupo = tramite.getTipoMovimiento().getClase().getGrupo().getNemonico();
        DistributivoDetalle puesto = vo.getDistributivoDetalle();
        List<String> r = vo.getDatos();
        Movimiento m = new Movimiento();
        m.setUsuarioCreacion(usuario.getUsuario());
        m.setFechaCreacion(new Date());
        m.setVigente(Boolean.TRUE);
        if (puesto.getServidor() != null) {
            m.setTipoIdentificacion(puesto.getServidor().getTipoIdentificacion());
            m.setNumeroIdentificacion(puesto.getServidor().getNumeroIdentificacion());
            m.setApellidosNombres(puesto.getServidor().getApellidosNombres());
            m.setApellidos(puesto.getServidor().getApellidos());
            m.setNombres(puesto.getServidor().getNombres());
        }
        m.setRmu(puesto.getEscalaOcupacional().getRmu());
        m.setRmuSobrevalorado(puesto.getRmuSobrevalorado());
        m.setEscalaOcupacional(puesto.getEscalaOcupacional());
        m.setUnidadOrganizacional(puesto.getDistributivo().getUnidadOrganizacional());
        m.setUnidadPresupuestaria(puesto.getUnidadPresupuestaria());
        m.setNumeroRegistro(null);
        m.setFechaRegistro(null);
        m.setJustificacion("");
        m.setNumeroActoAdministrativo("");
        m.setTramite(tramite);
        m.setRmuLetras(UtilNumeros.getInstancia().transformarNumerosLetras(m.getRmu()));
        m.setDistributivoDetalleId(puesto.getId());

        m.setConJustificacion(Boolean.FALSE);
        m.setSituacionActual(tramite.getTipoMovimiento().getSituacionActual());
        m.setSituacionPropuesta(tramite.getTipoMovimiento().getSituacionPropuesta());
        m.setAccionPersonalExplicacion(tramite.getTramiteAuxiliar() == null ? "" : tramite.getTramiteAuxiliar().
                getExplicacion());
        m.setFinalizado(Boolean.FALSE);
        if (GrupoEnum.INGRESOS.getCodigo().equals(grupo)) {
            if (!r.get(FormatoIngresoEnum.TIPO_PERIODO.getIndice()).trim().isEmpty()) {
                m.setTipoPeriodo(vo.getDatos().get(FormatoIngresoEnum.TIPO_PERIODO.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.FECHA_DESDE.getIndice()).trim().isEmpty()) {
                m.setRigeApartirDe(UtilFechas.formatear(r.get(FormatoIngresoEnum.FECHA_DESDE.getIndice())));
            }
            if (!r.get(FormatoIngresoEnum.FECHA_HASTA.getIndice()).trim().isEmpty()) {
                m.setFechaHasta(UtilFechas.formatear(r.get(FormatoIngresoEnum.FECHA_HASTA.getIndice())));
            }
            if (!r.get(FormatoIngresoEnum.TIPO_IDENTIFICACION.getIndice()).trim().isEmpty()) {
                m.setTipoIdentificacion(vo.getDatos().get(FormatoIngresoEnum.TIPO_IDENTIFICACION.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.NUMERO_IDENTIFICACION.getIndice()).trim().isEmpty()) {
                m.setNumeroIdentificacion(vo.getDatos().get(FormatoIngresoEnum.NUMERO_IDENTIFICACION.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.DOCUMENTO_PREVIO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalDocumentoPrevio(r.get(FormatoIngresoEnum.DOCUMENTO_PREVIO.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.NUMERO_DOCUMENTO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalNumeroDocumento(r.get(FormatoIngresoEnum.NUMERO_DOCUMENTO.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.EXPLICACION.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalExplicacion(new String(r.get(FormatoIngresoEnum.EXPLICACION.getIndice()).
                        getBytes("ISO-8859-1")));
            }
            if (!r.get(FormatoIngresoEnum.CERTIFICACION_PRESUPUESTARIA.getIndice()).trim().isEmpty()) {
                m.setCertificacionPresupuestaria(new String(r.get(FormatoIngresoEnum.CERTIFICACION_PRESUPUESTARIA.
                        getIndice()).getBytes("ISO-8859-1")));
            }
            if (!r.get(FormatoIngresoEnum.FECHA_DOCUMENTO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalFechaDocumento(UtilFechas.formatear(r.get(FormatoIngresoEnum.FECHA_DOCUMENTO.getIndice())));
            }
            // registrar nombre del servidor nuevo
            m.setApellidosNombres(vo.getNombreServidor());
        } else if (GrupoEnum.SALIDAS.getCodigo().equals(grupo)) {
            if (!r.get(FormatoSalidaEnum.TIPO_PERIODO.getIndice()).trim().isEmpty()) {
                m.setTipoPeriodo(vo.getDatos().get(FormatoSalidaEnum.TIPO_PERIODO.getIndice()));
            }
            if (!r.get(FormatoSalidaEnum.DOCUMENTO_PREVIO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalDocumentoPrevio(r.get(FormatoSalidaEnum.DOCUMENTO_PREVIO.getIndice()));
            }
            if (!r.get(FormatoSalidaEnum.NUMERO_DOCUMENTO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalNumeroDocumento(r.get(FormatoSalidaEnum.NUMERO_DOCUMENTO.getIndice()));
            }
            if (!r.get(FormatoSalidaEnum.EXPLICACION.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalExplicacion(new String(r.get(FormatoIngresoEnum.EXPLICACION.getIndice()).
                        getBytes("ISO-8859-1")));
            }
            if (!r.get(FormatoSalidaEnum.FECHA_DOCUMENTO.getIndice()).trim().isEmpty()) {
                m.setAccionPersonalFechaDocumento(UtilFechas.formatear(r.get(
                        FormatoSalidaEnum.FECHA_DOCUMENTO.getIndice())));
            }
            if (!r.get(FormatoSalidaEnum.FECHA_DESDE.getIndice()).trim().isEmpty()) {
                m.setRigeApartirDe(UtilFechas.formatear(r.get(FormatoSalidaEnum.FECHA_DESDE.getIndice())));
            }
        }
        m.setConJustificacion(Boolean.TRUE);
        movimientoDao.crear(m);
        movimientoDao.flush();
        // copia la situacion actual.
        crearSituacionActualYPropuestaMasivos(vo, m, usuario.getUsuario());
        // crear en detalle de acuerdo al grupo.
        crearContenidoMasivos(m, tramite, vo);
        // Registrar requisitos como cumplidos.
        registrarRequisitosCumplidosMasivos(m, requisitos);
    }

    /**
     *
     * @param m
     * @param requisitos
     * @throws DaoException
     */
    private void registrarRequisitosCumplidosMasivos(final Movimiento m, final List<TipoMovimientoRequisito> requisitos)
            throws DaoException {
        List<ValidacionTipoMovimientoRequisitoVO> validaciones = new ArrayList<>();
        for (TipoMovimientoRequisito requisito : requisitos) {
            if (requisito.getObligatorio()) {
                Validacion v = new Validacion();
                v.setCumple(true);
                v.setFechaCreacion(new Date());
                v.setMovimiento(m);
                v.setObservacion("APROBADO POR MIGRACION MASIVA");
                v.setSustentoLegal("APROBADO POR MIGRACION MASIVA");
                v.setTipoMovimientoRequisito(requisito);
                v.setUsuarioCreacion("");
                v.setVigente(Boolean.TRUE);
                validacionDao.crear(v);
            }
        }

    }

    /**
     * Permite crear siempre la situacion actual del puesto.
     *
     * @param sopi
     * @param canton
     * @param m
     * @param usuario
     */
    private void crearSituacionActualYPropuesta(final DistributivoDetalle p,
            final Movimiento m, final String usuario) throws DaoException {
        // Copia situacion actual
        MovimientoSituacionActual sa = new MovimientoSituacionActual();
        if (p.getServidor() != null) {
            sa.setApellidos(p.getServidor().getApellidos());
            sa.setApellidosNombres(p.getServidor().getApellidosNombres());
            sa.setNombres(p.getServidor().getNombres());
            sa.setTipoIdentificacion(p.getServidor().getTipoIdentificacion());
            sa.setNumeroIdentificacion(p.getServidor().getNumeroIdentificacion());
            sa.setIdEstadoPersonal(p.getServidor().getEstadoPersonalId());
            sa.setIdServidor(p.getIdServidor());
        }
        sa.setDistributivoDetalleId(p.getId());
        sa.setFechaFin(p.getFechaFin());
        sa.setFechaFinComisionServicio(p.getFechaFinComisionServicio());
        sa.setFechaIngreso(p.getFechaIngreso());
        sa.setFechaInicio(p.getFechaInicio());
        sa.setFechaInicioComisionServicio(p.getFechaInicioComisionServicio());
        sa.setIdDenominacionPuesto(p.getIdDenominacionPuesto());
        sa.setIdEscalaOcupacional(p.getIdEscalaOcupacional());
        sa.setIdEstadoPuesto(p.getIdEstadoPuesto());
        sa.setIdServidorComisionServicio(p.getIdServidorComisionServicio());
        sa.setIdUbicacionGeografica(p.getIdUbicacionGeografica());
        sa.setPartidaIndividual(p.getPartidaIndividual());
        sa.setRmu(p.getRmu());
        sa.setRmuEscala(p.getRmuEscala());
        sa.setRmuOriginal(p.getRmuOriginal());
        sa.setRmuSobrevalorado(p.getRmuSobrevalorado());
        sa.setSueldoBasico(p.getSueldoBasico());
        sa.setTipoComisionServicio(p.getTipoComisionServicio());
        sa.setFechaCreacion(new Date());
        sa.setUsuarioCreacion(usuario);
        sa.setMovimientoId(m.getId());
        sa.setVigente(Boolean.TRUE);
        movimientoSituacionActualDao.crear(sa);
        m.setMovimientoSituacionActual(sa);

        // crea  situacion propuesta.
        MovimientoSituacionPropuesta sp = new MovimientoSituacionPropuesta();
        sp.setProceso(p.getProceso());
        sp.setSubproceso(p.getSubProceso());
        sp.setLugarTrabajo(p.getUbicacionGeografica().getNombre());
        sp.setPartidaPresupuestaria(p.getPartidaIndividual());
        sp.setRmu(p.getRmu());
        sp.setPuesto(UtilCadena.concatenar(p.getEscalaOcupacional().getNombre(), "/",
                p.getEscalaOcupacional().getGrado()));
        sp.setMovimientoId(m.getId());
        sp.setFechaCreacion(new Date());
        sp.setUsuarioCreacion(usuario);
        sp.setDistributivoDetalleId(p.getId());
        sp.setVigente(Boolean.TRUE);
        movimientoSituacionPropuestaDao.crear(sp);
        m.setMovimientoSituacionPropuesta(sp);
    }

    /**
     * Permite crear siempre la situacion actual del puesto.
     *
     * @param sopi
     * @param canton
     * @param m
     * @param usuario
     */
    private void crearSituacionActualYPropuestaMasivos(final CargaMasivaMovimientoVO vo,
            final Movimiento m, final String usuario) throws DaoException, ServicioException {
        String grupo = m.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico();
        DistributivoDetalle p = vo.getDistributivoDetalle();
        List<String> r = vo.getDatos();
        // Copia situacion actual
        MovimientoSituacionActual sa = new MovimientoSituacionActual();
        if (p.getServidor() != null) {
            sa.setApellidos(p.getServidor().getApellidos());
            sa.setApellidosNombres(p.getServidor().getApellidosNombres());
            sa.setNombres(p.getServidor().getNombres());
            sa.setTipoIdentificacion(p.getServidor().getTipoIdentificacion());
            sa.setNumeroIdentificacion(p.getServidor().getNumeroIdentificacion());
            sa.setIdEstadoPersonal(p.getServidor().getEstadoPersonalId());
            sa.setIdServidor(p.getIdServidor());
        }
        sa.setDistributivoDetalleId(p.getId());
        sa.setFechaFin(p.getFechaFin());
        sa.setFechaFinComisionServicio(p.getFechaFinComisionServicio());
        sa.setFechaIngreso(p.getFechaIngreso());
        sa.setFechaInicio(p.getFechaInicio());
        sa.setFechaInicioComisionServicio(p.getFechaInicioComisionServicio());
        sa.setIdDenominacionPuesto(p.getIdDenominacionPuesto());
        sa.setIdEscalaOcupacional(p.getIdEscalaOcupacional());
        sa.setIdEstadoPuesto(p.getIdEstadoPuesto());
        sa.setIdServidorComisionServicio(p.getIdServidorComisionServicio());
        sa.setIdUbicacionGeografica(p.getIdUbicacionGeografica());
        sa.setPartidaIndividual(p.getPartidaIndividual());
        sa.setRmu(p.getRmu());
        sa.setRmuEscala(p.getRmuEscala());
        sa.setRmuOriginal(p.getRmuOriginal());
        sa.setRmuSobrevalorado(p.getRmuSobrevalorado());
        sa.setSueldoBasico(p.getSueldoBasico());
        sa.setTipoComisionServicio(p.getTipoComisionServicio());
        sa.setFechaCreacion(new Date());
        sa.setUsuarioCreacion(usuario);
        sa.setMovimientoId(m.getId());
        sa.setVigente(Boolean.TRUE);
        movimientoSituacionActualDao.crear(sa);
        m.setMovimientoSituacionActual(sa);

        // crea  situacion propuesta.
        if (grupo.equals(GrupoEnum.INGRESOS.getCodigo()) && !r.get(
                FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice()).trim().isEmpty()) {
            Long codigoPuesto = Long.valueOf(r.get(FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice()));
            List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscarPorCodigoPuesto(codigoPuesto, m.
                    getTramite().getInstitucionEjercicioFiscal().
                    getId());
            if (!puestos.isEmpty()) {
                DistributivoDetalle pPropuesto = puestos.get(0);
                MovimientoSituacionPropuesta sp = new MovimientoSituacionPropuesta();
                sp.setProceso(pPropuesto.getProceso());
                sp.setSubproceso(pPropuesto.getSubProceso());
                sp.setLugarTrabajo(pPropuesto.getUbicacionGeografica().getNombre());
                sp.setPartidaPresupuestaria(pPropuesto.getPartidaIndividual());
                sp.setRmu(pPropuesto.getRmu());
                sp.setPuesto(UtilCadena.concatenar(pPropuesto.getEscalaOcupacional().getNombre(), "/",
                        pPropuesto.getEscalaOcupacional().getGrado()));
                sp.setMovimientoId(m.getId());
                sp.setFechaCreacion(new Date());
                sp.setUsuarioCreacion(usuario);
                sp.setVigente(Boolean.TRUE);
                movimientoSituacionPropuestaDao.crear(sp);
                m.setMovimientoSituacionPropuesta(sp);

            }
        } else {
            MovimientoSituacionPropuesta sp = new MovimientoSituacionPropuesta();
            sp.setProceso(p.getProceso());
            sp.setSubproceso(p.getSubProceso());
            sp.setLugarTrabajo(p.getUbicacionGeografica().getNombre());
            sp.setPartidaPresupuestaria(p.getPartidaIndividual());
            sp.setRmu(p.getRmu());
            sp.setPuesto(UtilCadena.concatenar(p.getEscalaOcupacional().getNombre(), "/",
                    p.getEscalaOcupacional().getGrado()));
            sp.setMovimientoId(m.getId());
            sp.setFechaCreacion(new Date());
            sp.setUsuarioCreacion(usuario);
            sp.setVigente(Boolean.TRUE);
            movimientoSituacionPropuestaDao.crear(sp);
            m.setMovimientoSituacionPropuesta(sp);
        }
    }

    /**
     * Este método crea el contenido del movimiento.
     *
     * @param m Movimiento
     * @param grupo Grupo.
     * @throws DaoException DaoException
     */
    private void crearContenido(final Movimiento m, final Tramite tramite,
            final DistributivoDetalle puesto) throws DaoException {
        String grupo = tramite.getTipoMovimiento().getClase().getGrupo().getNemonico();
        Date fechaActual = new Date();
        Ingreso ingreso = new Ingreso();
        ingreso.setUsuarioCreacion(m.getUsuarioCreacion());
        ingreso.setFechaCreacion(fechaActual);
        ingreso.setVigente(Boolean.TRUE);
        ingreso.setMovimiento(m);
        if (puesto.getServidor() != null) {
            ingreso.setFechaIngresoSectorPublico(puesto.getServidor().getFechaIngresoSectorPublico());
            ingreso.setFechaIngresoInstitucion(puesto.getServidor().getFechaIngresoInstitucion());
        }
        ingresoDao.crear(ingreso);
        if (grupo.equals(GrupoEnum.SALIDAS.getCodigo())) {
            Cesacion cesacion = new Cesacion();
            cesacion.setUsuarioCreacion(m.getUsuarioCreacion());
            cesacion.setFechaCreacion(fechaActual);
            cesacion.setVigente(Boolean.TRUE);
            cesacion.setMovimiento(m);
            cesacionDao.crear(cesacion);
        } else if (grupo.equals(GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo())) {
            RegimenDisciplinario rd = new RegimenDisciplinario();
            rd.setFechaCreacion(fechaActual);
            rd.setUsuarioCreacion(m.getUsuarioCreacion());
            rd.setVigente(Boolean.TRUE);
            rd.setMovimiento(m);
            regimenDisciplinarioDao.crear(rd);
        } else if (grupo.equals(GrupoEnum.ROTACIONES.getCodigo())) {
            if (tramite.getTipoMovimiento().getTipoRotacion() == null) {
                throw new DaoException("El tipo de movimiento de ROTACIÓN no tiene configurado el TIPO DE ROTACIÓN");
            } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.CAMBIO_ADMINISTRATIVO.getCodigo())) {
                CambioAdministrativo ca = new CambioAdministrativo();
                ca.setFechaCreacion(fechaActual);
                ca.setUsuarioCreacion(m.getUsuarioCreacion());
                ca.setVigente(Boolean.TRUE);
                ca.setMovimiento(m);
                cambioAdministrativoDao.crear(ca);
            } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASLADO_ADMINISTRATIVO.
                    getCodigo())) {
                TrasladoAdministrativo ta = new TrasladoAdministrativo();
                ta.setFechaCreacion(fechaActual);
                ta.setUsuarioCreacion(m.getUsuarioCreacion());
                ta.setVigente(Boolean.TRUE);
                ta.setMovimiento(m);
                trasladoAdministrativoDao.crear(ta);
            } else if (tramite.getTipoMovimiento().getTipoRotacion().equals(TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.
                    getCodigo())) {
                Traspaso t = new Traspaso();
                t.setFechaCreacion(fechaActual);
                t.setUsuarioCreacion(m.getUsuarioCreacion());
                t.setVigente(Boolean.TRUE);
                t.setMovimiento(m);
                traspasoDao.crear(t);
            }
        } else if (grupo.equals(GrupoEnum.ABSENTISMO.getCodigo())) {
            Licencia l = new Licencia();
            l.setFechaCreacion(fechaActual);
            l.setUsuarioCreacion(m.getUsuarioCreacion());
            l.setVigente(Boolean.TRUE);
            l.setMovimiento(m);
            licenciaDao.crear(l);
            ComisionServicio cs = new ComisionServicio();
            cs.setFechaCreacion(fechaActual);
            cs.setUsuarioCreacion(m.getUsuarioCreacion());
            cs.setVigente(Boolean.TRUE);
            cs.setMovimiento(m);
            comisionServicioDao.crear(cs);
//        } else if (grupo.equals(GrupoEnum.FINALIZACION.getCodigo())) {
//            Finalizacion f = new Finalizacion();
//            f.setFechaCreacion(fechaActual);
//            f.setUsuarioCreacion(m.getUsuarioCreacion());
//            f.setVigente(Boolean.TRUE);
//            f.setMovimiento(m);
//            finalizacionDao.crear(f);
        }
    }

    /**
     * Este método crea el contenido del movimiento.
     *
     * @param m Movimiento
     * @param grupo Grupo.
     * @throws DaoException DaoException
     */
    private void crearContenidoMasivos(final Movimiento m, final Tramite tramite,
            final CargaMasivaMovimientoVO vo) throws DaoException, ParseException, UnsupportedEncodingException {
        DistributivoDetalle puesto = vo.getDistributivoDetalle();
        List<String> r = vo.getDatos();
        String grupo = tramite.getTipoMovimiento().getClase().getGrupo().getNemonico();
        Date fechaActual = new Date();
        Ingreso ingreso = new Ingreso();
        ingreso.setUsuarioCreacion(m.getUsuarioCreacion());
        ingreso.setFechaCreacion(fechaActual);
        ingreso.setVigente(Boolean.TRUE);
        ingreso.setMovimiento(m);
//        if (puesto.getServidor() != null) {
//            ingreso.setFechaIngresoSectorPublico(puesto.getServidor().getFechaIngresoSectorPublico());
//            ingreso.setFechaIngresoInstitucion(puesto.getServidor().getFechaIngresoInstitucion());
//        }
        if (grupo.equals(GrupoEnum.INGRESOS.getCodigo())) {
            if (!r.get(FormatoIngresoEnum.FECHA_INGRESO_INSTITUCION.getIndice()).trim().isEmpty()) {
                ingreso.setFechaIngresoInstitucion(UtilFechas.formatear(r.get(
                        FormatoIngresoEnum.FECHA_INGRESO_INSTITUCION.
                        getIndice())));
            }
            if (!r.get(FormatoIngresoEnum.FECHA_INGRESO_PUBLICO.getIndice()).trim().isEmpty()) {
                ingreso.setFechaIngresoSectorPublico(UtilFechas.formatear(r.get(
                        FormatoIngresoEnum.FECHA_INGRESO_PUBLICO.
                        getIndice())));
            }
            if (!r.get(FormatoIngresoEnum.TIPO_DESIGNACION.getIndice()).trim().isEmpty()) {
                ingreso.setTipoDesignacion(vo.getDatos().get(FormatoIngresoEnum.TIPO_DESIGNACION.getIndice()));
            }
            if (!r.get(FormatoIngresoEnum.ANTECEDENTES.getIndice()).trim().isEmpty()) {
                ingreso.setAntecedentesContrato(new String(vo.getDatos().get(
                        FormatoIngresoEnum.ANTECEDENTES.getIndice()).getBytes("UTF8")));
            }
            if (!r.get(FormatoIngresoEnum.ACTIVIDADES.getIndice()).trim().isEmpty()) {
                ingreso.setActividadesContrato(new String(vo.getDatos().get(FormatoIngresoEnum.ACTIVIDADES.getIndice()).
                        getBytes("ISO-8859-1")));
            }
            if (!r.get(FormatoIngresoEnum.RENOVACION.getIndice()).trim().isEmpty()) {
                ingreso.setRenovacionContrato(vo.getDatos().get(FormatoIngresoEnum.RENOVACION.getIndice()).
                        toUpperCase().equals("S"));
            }
        }
        ingresoDao.crear(ingreso);
        if (grupo.equals(GrupoEnum.SALIDAS.getCodigo())) {
            Cesacion cesacion = new Cesacion();
            cesacion.setUsuarioCreacion(m.getUsuarioCreacion());
            cesacion.setFechaCreacion(fechaActual);
            cesacion.setVigente(Boolean.TRUE);
            if (!r.get(FormatoSalidaEnum.FECHA_PRESENTA_RENUNCIA.getIndice()).trim().isEmpty()) {
                cesacion.setFechaPresentaRenuncia(UtilFechas.formatear(r.get(
                        FormatoSalidaEnum.FECHA_PRESENTA_RENUNCIA.getIndice())));
            }
            if (!r.get(FormatoSalidaEnum.FECHA_ACEPTA_RENUNCIA.getIndice()).trim().isEmpty()) {
                cesacion.setFechaAceptacionRenuncia(UtilFechas.formatear(r.get(FormatoSalidaEnum.FECHA_ACEPTA_RENUNCIA.
                        getIndice())));
            }
            if (!r.get(FormatoSalidaEnum.NUMERO_CONTRATO_ANTERIOR.getIndice()).trim().isEmpty()) {
                cesacion.setNumeroContratoAnterior(r.get(FormatoSalidaEnum.NUMERO_CONTRATO_ANTERIOR.getIndice()));
            }
            if (!r.get(FormatoSalidaEnum.FECHA_INICIO_CONTRATO_ANTERIOR.getIndice()).trim().isEmpty()) {
                cesacion.setFechaInicioContratoAnterior(UtilFechas.formatear(r.get(
                        FormatoSalidaEnum.FECHA_INICIO_CONTRATO_ANTERIOR.getIndice())));
            }
            if (!r.get(FormatoSalidaEnum.FECHA_FALLECIMIENTO.getIndice()).trim().isEmpty()) {
                cesacion.setFechaFallecimiento(UtilFechas.formatear(r.get(FormatoSalidaEnum.FECHA_FALLECIMIENTO.
                        getIndice())));
            }
            if (!r.get(FormatoSalidaEnum.CASO_FALLECIMIENTO.getIndice()).trim().isEmpty()) {
                cesacion.setCasoFallecimiento(r.get(FormatoSalidaEnum.CASO_FALLECIMIENTO.getIndice()));
            }

            cesacion.setMovimiento(m);
            cesacionDao.crear(cesacion);
        }
    }

    /**
     * Este método guardar el movimiento y su respectivo detalle en edición.
     *
     * @param m Movimiento
     * @param tm
     * @param detalleMovimientoVO DetalleMovimientoVO
     * @throws ServicioException Error de ejecucion.
     */
    public void guardarEdicionMovimiento(final Movimiento m, final TipoMovimiento tm,
            final DetalleMovimientoVO detalleMovimientoVO) throws ServicioException {
        try {
            Grupo grupo = tm.getClase().getGrupo();
            if (m.getAccionPersonalNumeroDocumento() != null) {
                m.setAccionPersonalNumeroDocumento(m.getAccionPersonalNumeroDocumento().toUpperCase());
            }
            if (m.getAccionPersonalExplicacion() != null && m.getAccionPersonalExplicacion().length() > 1000) {
                m.setAccionPersonalExplicacion(m.getAccionPersonalExplicacion().substring(0, 1000));
            }
            movimientoDao.actualizar(m);
            actualizarSituacionPropuesta(m);
            guardarEdicionMovimientoIngreso(detalleMovimientoVO, m);
            if (grupo.getNemonico().equals(GrupoEnum.SALIDAS.getCodigo())) {
                cesacionDao.actualizar(detalleMovimientoVO.getCesacion());
            } else if (grupo.getNemonico().equals(GrupoEnum.ROTACIONES.getCodigo())) {
                if (tm.getTipoRotacion().equals(TipoRotacionEnum.CAMBIO_ADMINISTRATIVO.getCodigo())) {
                    guardarEdicionMovimientoCambioAdministrativo(detalleMovimientoVO, m);
                } else if (tm.getTipoRotacion().equals(TipoRotacionEnum.TRASLADO_ADMINISTRATIVO.getCodigo())) {
                    guardarEdicionMovimientoTrasladoAdministrativo(detalleMovimientoVO, m);
                } else if (tm.getTipoRotacion().equals(TipoRotacionEnum.TRASPASO_MISMA_INSTITUCION.getCodigo())) {
                    guardarEdicionMovimientoTraspaso(detalleMovimientoVO, m);
                }
            } else if (grupo.getNemonico().equals(GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo())) {
                regimenDisciplinarioDao.actualizar(detalleMovimientoVO.getRegimenDisciplinario());
            } else if (grupo.getNemonico().equals(GrupoEnum.ABSENTISMO.getCodigo())) {
                if (tm.getAreaComisionServicioInstitucionRequiriente()) {
                    // comision de servicios.
                    guardarEdicionMovimientoComisionServicio(detalleMovimientoVO, m);
                } else {
                    // licencias.
                    guardarEdicionMovimientoLicencia(detalleMovimientoVO, m);
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Helper Actualiza los valores de situacion propuesta para un movimiento
     *
     * @param movimiento
     * @throws DaoException
     */
    private void actualizarSituacionPropuesta(final Movimiento movimiento) throws DaoException {
        // crea  situacion propuesta.
        MovimientoSituacionPropuesta situacionPropuesta = movimiento.getMovimientoSituacionPropuesta();
        if (situacionPropuesta != null) {
            DistributivoDetalle dt = situacionPropuesta.getDistributivoDetalle();
            if (dt != null) {
                situacionPropuesta.setProceso(dt.getProceso());
                situacionPropuesta.setSubproceso(dt.getSubProceso());
                situacionPropuesta.setLugarTrabajo(dt.getUbicacionGeografica().getNombre());
                situacionPropuesta.setPartidaPresupuestaria(dt.getPartidaIndividual());
                situacionPropuesta.setRmu(dt.getRmu());
                situacionPropuesta.setPuesto(UtilCadena.concatenar(dt.getEscalaOcupacional().getNombre(), "/",
                        dt.getEscalaOcupacional().getGrado()));
            } else {
                situacionPropuesta.setRmu(movimiento.getRmu());
                situacionPropuesta.setPuesto(UtilCadena.concatenar(movimiento.getEscalaOcupacional().getNombre(), "/",
                        movimiento.getEscalaOcupacional().getGrado()));
            }
            situacionPropuesta.setUsuarioActualizacion(movimiento.getUsuarioActualizacion());
            situacionPropuesta.setFechaActualizacion(movimiento.getFechaActualizacion());
            movimientoSituacionPropuestaDao.actualizar(situacionPropuesta);
        }
    }

    /**
     * Guarda la edicion de licencias del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoLicencia(final DetalleMovimientoVO detalleMovimientoVO, final Movimiento m)
            throws DaoException {
        // registro de fecha de devengamientos.
        Licencia l = detalleMovimientoVO.getLicencia();
        if (l.getDevengar() != null && l.getDevengar() && l.getDevengarPeriodo() != null
                && l.getDevengarValor() != null && m.getFechaHasta() != null) {
            l.setFechaInicioADevengar(UtilFechas.sumarDias(m.getFechaHasta(), 1));
            l.setFechaFinalADevengar(UtilFechas.sumarPeriodos(l.getFechaInicioADevengar(),
                    l.getDevengarPeriodo(), l.getDevengarValor()));
        }
        licenciaDao.actualizar(l);
    }

    /**
     * Guarda la edicion de comision de servicios del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoComisionServicio(final DetalleMovimientoVO detalleMovimientoVO,
            final Movimiento m) throws DaoException {
        // guardar la situacion propuesta.
        ComisionServicio cs = detalleMovimientoVO.getComisionServicio();
        MovimientoSituacionPropuesta p = m.getMovimientoSituacionPropuesta();
        p.setProceso(cs.getInstitucion());
        p.setSubproceso(cs.getUnidad());
        p.setPuesto(null);
        p.setLugarTrabajo(null);
        p.setPartidaPresupuestaria(null);
        p.setRmu(null);
        movimientoSituacionPropuestaDao.actualizar(p);
        comisionServicioDao.actualizar(cs);
    }

    /**
     * Guarda la edicion de cambios administrativos del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoCambioAdministrativo(final DetalleMovimientoVO detalleMovimientoVO,
            final Movimiento m) throws DaoException {
        // guardar la situacion propuesta.
        CambioAdministrativo ca = detalleMovimientoVO.getCambioAdministrativo();
        MovimientoSituacionActual pa = m.getMovimientoSituacionActual();
        MovimientoSituacionPropuesta pp = m.getMovimientoSituacionPropuesta();
        if (ca.getUnidadOrganizacional() != null) {
            pp.setProceso(m.getDistributivoDetalle().getProceso());
            pp.setSubproceso(ca.getUnidadOrganizacional().getRuta());
            movimientoSituacionPropuestaDao.actualizar(pp);
        }
        cambioAdministrativoDao.actualizar(ca);
    }

    /**
     * Guarda la edicion de traslado administrativos del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoTrasladoAdministrativo(final DetalleMovimientoVO detalleMovimientoVO,
            final Movimiento m) throws DaoException {
        // guardar la situacion propuesta.
        TrasladoAdministrativo ta = detalleMovimientoVO.getTrasladoAdministrativo();
        trasladoAdministrativoDao.actualizar(ta);
        MovimientoSituacionPropuesta pp = m.getMovimientoSituacionPropuesta();
        pp.setProceso(pp.getDistributivoDetalle().getProceso());
        pp.setSubproceso(pp.getDistributivoDetalle().getSubProceso());
        pp.setLugarTrabajo(pp.getDistributivoDetalle().getUbicacionGeografica().getNombre());
        pp.setPuesto(UtilCadena.concatenar(pp.getDistributivoDetalle().getEscalaOcupacional().getNombre(), "/", pp.
                getDistributivoDetalle().getEscalaOcupacional().getGrado()));
        pp.setPartidaPresupuestaria(pp.getDistributivoDetalle().getPartidaIndividual());
        pp.setRmu(pp.getDistributivoDetalle().getRmu());
        movimientoSituacionPropuestaDao.actualizar(pp);
    }

    /**
     * Guarda la edicion de traspaso del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoTraspaso(final DetalleMovimientoVO detalleMovimientoVO,
            final Movimiento m) throws DaoException {
        // guardar la situacion propuesta.
        Traspaso t = detalleMovimientoVO.getTraspaso();
//        MovimientoSituacionActual pa = m.getMovimientoSituacionActual();
        MovimientoSituacionPropuesta pp = m.getMovimientoSituacionPropuesta();
        if (t.getUnidadOrganizacional() != null) {
            pp.setProceso(t.getMovimiento().getDistributivoDetalle().getProceso());
            pp.setSubproceso(t.getUnidadOrganizacional().getRuta());
            movimientoSituacionPropuestaDao.actualizar(pp);
        }
        traspasoDao.actualizar(t);
    }

    /**
     * Guarda la edicion de subrogacion del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoSubrogacion(final DetalleMovimientoVO detalleMovimientoVO, final Movimiento m)
            throws DaoException {
        Subrogacion s = detalleMovimientoVO.getSubrogacion();
        subrogacionDao.actualizar(s);
        MovimientoSituacionPropuesta pp = m.getMovimientoSituacionPropuesta();
        pp.setProceso(pp.getDistributivoDetalle().getProceso());
        pp.setSubproceso(pp.getDistributivoDetalle().getSubProceso());
        pp.setLugarTrabajo(pp.getDistributivoDetalle().getUbicacionGeografica().getNombre());
        pp.setPuesto(UtilCadena.concatenar(pp.getDistributivoDetalle().getEscalaOcupacional().getNombre(), "/", pp.
                getDistributivoDetalle().getEscalaOcupacional().
                getGrado()));
        pp.setPartidaPresupuestaria(pp.getDistributivoDetalle().getPartidaIndividual());
        pp.setRmu(pp.getDistributivoDetalle().getRmu());
        movimientoSituacionPropuestaDao.actualizar(pp);

    }

    /**
     * Guarda la edicion de subrogacion del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoEncargo(final DetalleMovimientoVO detalleMovimientoVO, final Movimiento m)
            throws DaoException {
        Encargo e = detalleMovimientoVO.getEncargo();
        encargoDao.actualizar(e);
        MovimientoSituacionPropuesta pp = m.getMovimientoSituacionPropuesta();
        pp.setProceso(pp.getDistributivoDetalle().getProceso());
        pp.setSubproceso(pp.getDistributivoDetalle().getSubProceso());
        pp.setLugarTrabajo(pp.getDistributivoDetalle().getUbicacionGeografica().getNombre());
        pp.setPuesto(UtilCadena.concatenar(pp.getDistributivoDetalle().getEscalaOcupacional().getNombre(), "/", pp.
                getDistributivoDetalle().getEscalaOcupacional().
                getGrado()));
        pp.setPartidaPresupuestaria(pp.getDistributivoDetalle().getPartidaIndividual());
        pp.setRmu(pp.getDistributivoDetalle().getRmu());
        movimientoSituacionPropuestaDao.actualizar(pp);
    }

    /**
     * Guarda la edicion de finalizacion del movimiento.
     *
     * @param detalleMovimientoVO
     * @param m
     * @throws DaoException
     */
    private void guardarEdicionMovimientoFinalizacion(final DetalleMovimientoVO detalleMovimientoVO, final Movimiento m)
            throws DaoException {
        Finalizacion f = detalleMovimientoVO.getFinalizacion();
        finalizacionDao.actualizar(f);
    }

    /**
     * Guarda la edicion de ingresos del movimiento.
     *
     * @param detalleMovimientoVO
     * @param movimiento
     * @throws DaoException
     */
    private void guardarEdicionMovimientoIngreso(final DetalleMovimientoVO detalleMovimientoVO,
            final Movimiento movimiento) throws DaoException, ServicioException {
        if (detalleMovimientoVO.getIngreso().getDiscapacidad() != null && detalleMovimientoVO.getIngreso().
                getDiscapacidad()) {
            if (detalleMovimientoVO.getIngreso().getEspecifiqueDiscapacidad().
                    equals(EspecifiqueDiscapacidadEnum.DISCAPACIDAD.getCodigo())) {
                detalleMovimientoVO.getIngreso().setJustificacionDiscapacidad(null);
            } else if (detalleMovimientoVO.getIngreso().getEspecifiqueDiscapacidad().
                    equals(EspecifiqueDiscapacidadEnum.ENFERMEDAD_CATASTROFICA.getCodigo())) {
                detalleMovimientoVO.getIngreso().setTipoDiscapacidad(null);
                detalleMovimientoVO.getIngreso().setPorcentajeDiscapacidad(null);
                detalleMovimientoVO.getIngreso().setNumeroConadis(null);
            }
        } else {
            detalleMovimientoVO.getIngreso().setEspecifiqueDiscapacidad(null);
            detalleMovimientoVO.getIngreso().setCorrespondeDiscapacidad(null);
            detalleMovimientoVO.getIngreso().setTipoDiscapacidad(null);
            detalleMovimientoVO.getIngreso().setPorcentajeDiscapacidad(null);
            detalleMovimientoVO.getIngreso().setNumeroConadis(null);
            detalleMovimientoVO.getIngreso().setJustificacionDiscapacidad(null);
        }
        ingresoDao.actualizar(detalleMovimientoVO.getIngreso());
//        ingresoDao.flush();

        // registrar en reclutamiento
        Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(movimiento.getDistributivoDetalleId());
        if (r != null) {
            r.setEmMovimientoPersonal(true);
            r.setUsuarioActualizacion(movimiento.getUsuarioCreacion());
            r.setFechaActualizacion(new Date());
            reclutamientoServicio.actualizarReclutamiento(r);
        }
    }

    /**
     * Realiza la eliminacion del tramite.
     *
     * @param tramiteId Identificador del tramite.
     * @param codigoInstitucion Codigo de la institucion.
     * @param ejercicioFiscal Ejercicio fiscal en curso.
     * @param comentario Conentario de la operacion.
     * @param usuarioDTO
     * @param esRRHH
     * @throws ServicioException Error de ejecucion.
     */
    public void eliminarTramite(final Long tramiteId, final String codigoInstitucion, final Integer ejercicioFiscal,
            final String comentario, final UsuarioVO usuarioDTO, final Boolean esRRHH) throws ServicioException {

        gestionarTramite(tramiteId, codigoInstitucion, ejercicioFiscal, comentario, usuarioDTO, esRRHH,
                EstadosTramiteEnum.ELABORACION.getCodigo(), EstadosTramiteEnum.ELIMINADO.getCodigo());
        /*try {
         Tramite t = tramiteDao.buscarPorId(tramiteId);
         Transicion transicion = gestionServicio.buscarTransicion(EstadosTramiteEnum.ELABORACION.getCodigo(),
         EstadosTramiteEnum.ELIMINADO.getCodigo(), t.getCodigoProceso());
         avanzarTramite(tramiteId, transicion.getId(), null, comentario, usuarioDTO, esRRHH);
         // registrar en reclutamiento
         for (Movimiento m : t.getListaMovimientos()) {
         if (m.getVigente() && m.getDistributivoDetalleId() != null) {
         Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
         if (r != null) {
         r.setEmMovimientoPersonal(false);
         r.setUsuarioActualizacion(m.getUsuarioCreacion());
         r.setFechaActualizacion(new Date());
         reclutamientoServicio.actualizarReclutamiento(r);
         }
         }
         }
         } catch (Exception e) {
         throw new ServicioException(e);
         }*/
    }

    /**
     * Realiza el rechazo del tramite.
     *
     * @param tramiteId Identificador del tramite.
     * @param codigoInstitucion Codigo de la institucion.
     * @param ejercicioFiscal Ejercicio fiscal en curso.
     * @param comentario Conentario de la operacion.
     * @param usuarioDTO
     * @param esRRHH
     * @throws ServicioException Error de ejecucion.
     */
    public void rechazarTramite(final Long tramiteId, final String codigoInstitucion, final Integer ejercicioFiscal,
            final String comentario, final UsuarioVO usuarioDTO, final Boolean esRRHH) throws ServicioException {
        gestionarTramite(tramiteId, codigoInstitucion, ejercicioFiscal, comentario, usuarioDTO, esRRHH,
                EstadosTramiteEnum.ELABORACION.getCodigo(), EstadosTramiteEnum.RECHAZADO.getCodigo());
        /*try {
         Tramite t = tramiteDao.buscarPorId(tramiteId);
         Transicion transicion = gestionServicio.buscarTransicion(EstadosTramiteEnum.ELABORACION.getCodigo(),
         EstadosTramiteEnum.RECHAZADO.getCodigo(), t.getCodigoProceso());
         avanzarTramite(tramiteId, transicion.getId(), null, comentario, usuarioDTO, esRRHH);
         // registrar en reclutamiento
         for (Movimiento m : t.getListaMovimientos()) {
         if (m.getVigente() && m.getDistributivoDetalleId() != null) {
         Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
         if (r != null) {
         r.setEmMovimientoPersonal(false);
         r.setUsuarioActualizacion(m.getUsuarioCreacion());
         r.setFechaActualizacion(new Date());
         reclutamientoServicio.actualizarReclutamiento(r);
         }
         }
         }
         } catch (Exception e) {
         throw new ServicioException(e);
         }*/
    }

    /**
     * Realiza la anulacion del tramite.
     *
     * @param tramiteId Identificador del tramite.
     * @param codigoInstitucion Codigo de la institucion.
     * @param ejercicioFiscal Ejercicio fiscal en curso.
     * @param comentario Comentario de la operacion.
     * @param usuarioDTO
     * @param esRRHH
     * @throws ServicioException Error de ejecucion.
     */
    public void anularTramite(final Long tramiteId, final String codigoInstitucion, final Integer ejercicioFiscal,
            final String comentario, final UsuarioVO usuarioDTO, final Boolean esRRHH) throws ServicioException {
        gestionarTramite(tramiteId, codigoInstitucion, ejercicioFiscal, comentario, usuarioDTO, esRRHH,
                EstadosTramiteEnum.ELABORACION.getCodigo(), EstadosTramiteEnum.ANULADO.getCodigo());
        /*try {
         Tramite t = tramiteDao.buscarPorId(tramiteId);
         Transicion transicion = gestionServicio.buscarTransicion(EstadosTramiteEnum.ELABORACION.getCodigo(),
         EstadosTramiteEnum.ANULADO.getCodigo(), t.getCodigoProceso());
         avanzarTramite(tramiteId, transicion.getId(), null, comentario, usuarioDTO, esRRHH);
         // registrar en reclutamiento
         for (Movimiento m : t.getListaMovimientos()) {
         if (m.getVigente() && m.getDistributivoDetalleId() != null) {
         Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
         if (r != null) {
         r.setEmMovimientoPersonal(false);
         r.setUsuarioActualizacion(m.getUsuarioCreacion());
         r.setFechaActualizacion(new Date());
         reclutamientoServicio.actualizarReclutamiento(r);
         }
         }
         }
         } catch (Exception e) {
         throw new ServicioException(e);
         }*/
    }

    /**
     * Procede a eliminar un movimiento que pertenece a un tramite (quitar vigencia).
     *
     * @param movimientoId Identificador unico del movimiento.
     * @param usuario Usuario conectado.
     * @throws ServicioException Error de ejecucion.
     */
    public void eliminarMovimiento(final Long movimientoId, final String usuario) throws ServicioException {
        try {
            Movimiento movimiento = movimientoDao.buscarPorId(movimientoId);
            movimiento.setUsuarioActualizacion(usuario);
            movimiento.setFechaActualizacion(new Date());
            movimiento.setVigente(Boolean.FALSE);
            movimientoDao.actualizar(movimiento);
            if (movimiento.getDistributivoDetalleId() != null) {
                Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(movimiento.getDistributivoDetalleId());
                if (r != null) {
                    r.setEmMovimientoPersonal(false);
                    r.setUsuarioActualizacion(movimiento.getUsuarioCreacion());
                    r.setFechaActualizacion(new Date());
                    reclutamientoServicio.actualizarReclutamiento(r);
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Procede a eliminar los movimientos que pertenece a un tramite (quitar vigencia).
     *
     * @param movimientoId Identificador unico del movimiento.
     * @param usuario Usuario conectado.
     * @throws ServicioException Error de ejecucion.
     */
    public void eliminarMovimientos(final Long tramiteId, final String usuario) throws ServicioException {
        try {
            movimientoDao.quitarVigencia(tramiteId);
            Tramite t = tramiteDao.buscarPorId(tramiteId);
            for (Movimiento m : t.getListaMovimientos()) {
                if (m.getVigente() && m.getDistributivoDetalleId() != null) {
                    Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
                    if (r != null) {
                        r.setEmMovimientoPersonal(false);
                        r.setUsuarioActualizacion(m.getUsuarioCreacion());
                        r.setFechaActualizacion(new Date());
                        reclutamientoServicio.actualizarReclutamiento(r);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método busca un ingreso segun el id del movimiento.
     *
     * @param idMovimineto Long
     * @return Ingreso
     * @throws ServicioException ServicioException
     */
    public Ingreso buscaIngresoPorMovimiento(final Long idMovimineto) throws ServicioException {
        try {
            return ingresoDao.buscarPorMovimiento(idMovimineto);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una cesacion para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Cesacion buscarCesacionPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return cesacionDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una regimen disciplinario para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public RegimenDisciplinario buscarRegimenDisciplinarioPorMovimiento(final Long idMovimiento) throws
            ServicioException {
        try {
            return regimenDisciplinarioDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una licencia para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Licencia buscarLicenciaPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return licenciaDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar un comision de servicio para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public ComisionServicio buscarComisionServicioPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return comisionServicioDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar un cambio administrativo para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public CambioAdministrativo buscarCambioAdministrativoPorMovimiento(final Long idMovimiento) throws
            ServicioException {
        try {
            return cambioAdministrativoDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar un traslado administrativo para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public TrasladoAdministrativo buscarTrasladoAdministrativoPorMovimiento(final Long idMovimiento) throws
            ServicioException {
        try {
            return trasladoAdministrativoDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar un traspaso para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Traspaso buscarTraspasoPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return traspasoDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una subrogacion para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Subrogacion buscarSubrogacionPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return subrogacionDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar un encargo para el movimiento.
     *
     * @param idMovimiento Long
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Encargo buscarEncargoPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return encargoDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una finalizacion para el movimiento.
     *
     * @param idMovimiento Long
     * @return Finalizacion
     * @throws ServicioException ServicioException
     */
    public Finalizacion buscarFinalizacionPorMovimiento(final Long idMovimiento) throws ServicioException {
        try {
            return finalizacionDao.buscarPorMovimiento(idMovimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una licencia para el movimiento.
     *
     * @param cedula String
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Movimiento buscarMovimientoPorCedulaLicenciaAcademica(final String cedula, final String tipoLicencia) throws
            ServicioException {
        try {
            return movimientoDao.buscarMovimientoPorCedulaLicenciaAcademica(cedula, tipoLicencia);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método buscar una licencia para el movimiento.
     *
     * @param cedula String
     * @return Cesacion
     * @throws ServicioException ServicioException
     */
    public Movimiento buscarMovimientoPorCedula(final String cedula) throws ServicioException {
        try {
            return movimientoDao.buscarMovimientoPorCedula(cedula);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public Boolean validarTramite(final Long tramiteId, final String usuario, final Boolean enviar,
            final List<ParametroGlobal> parametrosGlobales) throws ServicioException {
        try {
            return reglaServicio.ejecutar(tramiteId, new ArrayList<ReglaMensaje>(), usuario, enviar,
                    parametrosGlobales);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método envia el tramite.
     *
     * @param tramiteId
     * @param codigoInstitucion
     * @param ejercicioFiscal
     * @param usuarioVO
     * @throws ServicioException
     */
    public void enviarTramite(final Long tramiteId, final String codigoInstitucion,
            final Integer ejercicioFiscal, final UsuarioVO usuarioVO, final Boolean esRRHH) throws ServicioException {
        try {
            Tramite t = tramiteDao.buscarPorId(tramiteId);
            String estadofinal;
            String mensaje;
            String codigoProceso = TipoFlujoEnum.buscar(t.getTipoMovimiento().getTipoFlujo()).getCodigoProceso();
            if (codigoProceso.equals(ProcesoEnum.MOVIMIENTO_PERSONAL_REGULAR.getCodigo())) {
                estadofinal = EstadosTramiteEnum.REVISION.getCodigo();
                mensaje = "TRÁMITE ENVIADO PARA SU REVISIÓN";
            } else if (codigoProceso.equals(ProcesoEnum.MOVIMIENTO_PERSONAL_CORTO.getCodigo())) {
                estadofinal = EstadosTramiteEnum.APROBACION.getCodigo();
                mensaje = "TRÁMITE ENVIADO PARA SU APROBACIÓN";
            } else if (codigoProceso.equals(ProcesoEnum.MOVIMIENTO_PERSONAL_DIRECTO.getCodigo())) {
                estadofinal = EstadosTramiteEnum.LEGALIZACION.getCodigo();
                mensaje = "TRÁMITE ENVIADO PARA SU LEGALIZACION";
            } else {
                throw new ServicioException("No se puede determinar el estado siguiente");
            }
            t.setConSolicitud(Boolean.TRUE);
            t.setCodigoProceso(codigoProceso);
            tramiteDao.actualizar(t);
            Transicion transicion = gestionServicio.buscarTransicion(EstadosTramiteEnum.ELABORACION.getCodigo(),
                    estadofinal, codigoProceso);
            avanzarTramite(tramiteId, transicion.getId(), null, mensaje, usuarioVO, esRRHH);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite avanzar el tramite de acuerdo al proceso definido.
     *
     * @param tramiteId Identificador del tramite.
     * @param transicionId Identificador de la transicion.
     * @param comentario Conmentario.
     * @param usuarioVO Datos del usuario conectados.
     * @throws ServicioException Error de ejecucion.
     */
    public synchronized void avanzarTramite(final Long tramiteId, final Long transicionId, final Long tareaId,
            final String comentario, final UsuarioVO usuarioVO, final Boolean esRRHH) throws ServicioException {
        try {
            Tramite t = tramiteDao.buscarPorId(tramiteId);

            // registraer bitacora.
            TramiteBitacora tb = tramiteBitacoraDao.buscarPorTramite(tramiteId);
            if (tb == null) {
                throw new ServicioException("No existe registrada la bitácora del trámite.");
            } else {
                Transicion transicion = gestionServicio.buscarTransicion(transicionId);
                ejecucionLogica(t, transicion, usuarioVO, comentario);
                Instancia i = avanzarTramite(transicionId, t, usuarioVO, comentario, tareaId, esRRHH);
                registraEstadoDelTramite(t, i, usuarioVO, transicion);
                registrarTramiteBitacora(transicion, tb, i, usuarioVO);
            }
        } catch (ServicioException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param transicionId
     * @param t
     * @param usuarioVO
     * @param comentario
     * @param tareaId
     * @return
     * @throws DaoException
     * @throws NumberFormatException
     * @throws ec.gob.mrl.siith.adm.excepciones.ServicioException
     * @throws ServicioException
     * @throws ec.gob.mrl.gestor.excepciones.ServicioException
     */
    private Instancia avanzarTramite(final Long transicionId, final Tramite t, final UsuarioVO usuarioVO,
            final String comentario, final Long tareaId, final Boolean esRRHH)
            throws DaoException, NumberFormatException, ServicioException {
        Transicion transicion = gestionServicio.buscarTransicion(transicionId);
//        if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.LEGALIZACION_NOMINA.getCodigo())) {
//            // dterminar la unidad organizacional a atender.
//            if (!uo.getLegaliza()) {
//                // localizar unidad organizacion de recursos humanos.
//                ParametroInstitucional rrhh = parametroInstitucionalDao.buscarPorInstitucionYNemonico(usuarioVO.
//                        getInstitucionEjercicioFiscal().
//                        getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_DIRECCION_RRHH.
//                        getCodigo());
//                if (rrhh == null || rrhh.getValorTexto().trim().isEmpty()) {
//                    throw new ServicioException(
//                            "El parámetro institucional de código de unidad organizacional de RRHH "
//                            + "no se encuentra registrado.");
//                } else {
//                    List<UnidadOrganizacional> oficina = unidadOrganizacionalDao.buscarPorNemonico(
//                            rrhh.getValorTexto());
//                    if (oficina.isEmpty()) {
//                        throw new ServicioException("No se pudo localizar la unidad organizacional de "
//                                + "RRHH con código:" + rrhh.
//                                getValorTexto());
//                    } else {
//                        uo = oficina.get(0);
//                    }
//                }
//            }
//        }

        // registrar datos
        if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.APROBACION.getCodigo())) {
            t.setCodigoInstitucionAprobador(usuarioVO.getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional().getCodigo());
            t.setNombreInstitucionAprobador(usuarioVO.getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional().getRuta());

        }
        // unidades con acceso
        List<UnidadOrganizacional> unidadesAcceso;
        if (esRRHH) {
            unidadesAcceso = recuperarUnidadesAccesoRRHH(usuarioVO);
        } else {
            if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.LEGALIZACION_NOMINA.getCodigo())
                    && t.getTipoMovimiento().getTipoFlujo().equals(TipoFlujoEnum.FLUJO_CORTO.getCodigo())) {
                List<UnidadOrganizacional> uos = unidadOrganizacionalDao.buscarPorNemonico(t.getCodigoInstitucion());
                if (!uos.isEmpty()) {
                    UnidadOrganizacional uo = uos.get(0);
                    if (uo.getLegaliza()) {
                        unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                                usuarioVO.getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());
                    } else {
                        unidadesAcceso = recuperarUnidadesAccesoRRHH(usuarioVO);
                    }
                } else {
                    unidadesAcceso = recuperarUnidadesAccesoRRHH(usuarioVO);
                }
            } else {
                unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                        usuarioVO.getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());
            }
        }

        // avanzar el tramite.
        AvanzaInstanciaDTO dto = new AvanzaInstanciaDTO();
        dto.setUnidadesAcceso(unidadesAcceso);
//        dto.setCodigoInstitucion(uo.getCodigo());
//        dto.setNombreInstitucion(uo.getRuta());
        dto.setEjercicioFiscal(Integer.parseInt(usuarioVO.getEjercicioFiscal().getNombre()));
        dto.setCodigoProceso(t.getCodigoProceso());
        dto.setTareaId(tareaId);
        dto.setComentario(comentario);
        dto.setUsuario(usuarioVO.getServidor().getNumeroIdentificacion());
        dto.setUsuarioNombre(usuarioVO.getServidor().getApellidosNombres());
        dto.setInstitucionId(t.getInstitucionEjercicioFiscal().getId());
        dto.setInstanciaId(t.getIdentificadorProceso());
        dto.setUsuarioAsignar(balancearUsuario(usuarioVO, unidadesAcceso, transicion, dto));
        Instancia i = gestionServicio.avanzarInstancia(dto, transicionId);
        return i;
    }

    /**
     *
     * @param uoRRHH
     * @param unidadesAcceso
     */
    private void recolectarUnidadesDeRRHH(UnidadOrganizacional uoRRHH, List<UnidadOrganizacional> unidadesAcceso) {
        unidadesAcceso.add(uoRRHH);
        for (UnidadOrganizacional hijo : uoRRHH.getListaUnidadesOrganizacionales()) {
            if (hijo.getVigente()) {
                recolectarUnidadesDeRRHH(hijo, unidadesAcceso);
            }
        }
    }

    /**
     *
     * @param usuarioVO
     * @param uo
     * @param t
     * @return
     * @throws DaoException
     */
    private String balancearUsuario(UsuarioVO usuarioVO, List<UnidadOrganizacional> unidadesAcceso, Transicion t,
            AvanzaInstanciaDTO dto) throws DaoException, ServicioException {
        if (t.getDirecto()) {
            return usuarioVO.getServidor().getNumeroIdentificacion();
        } else {
            List<String> roles = new ArrayList<>();
            for (Asignacion a : t.getListaAsignaciones()) {
                roles.add(a.getNemonicoRol());
            }
            List<String> unidades = new ArrayList<>();
            for (UnidadOrganizacional uo : unidadesAcceso) {
                unidades.add(uo.getCodigo());
            }
            List<RolServidor> servidores = recolectarServidorPorRolUnidadOrganizacion(unidadesAcceso, roles);
            System.out.println("roles:" + roles);
            System.out.println("total servidores:" + servidores.size());
            for (RolServidor rs : servidores) {
                System.out.println("..." + rs.getServidor().getNumeroIdentificacion());
            }
            if (servidores.isEmpty()) {
                throw new DaoException(UtilCadena.concatenar("No existen usuarios asignados con el rol ",
                        roles + " y unidades organizacionales ", unidades));
            } else {
                Servidor servidor;
                if (servidores.size() == 1) {
                    servidor = servidores.get(0).getServidor();
                } else {
                    Random r = new Random();
                    servidor = servidores.get(r.nextInt(servidores.size())).getServidor();
                }
                DistributivoDetalle dd = distributivoDetalleServicio.buscar(servidor.getId());
                dto.setCodigoInstitucion(dd.getDistributivo().getUnidadOrganizacional().getCodigo());
                dto.setNombreInstitucion(dd.getDistributivo().getUnidadOrganizacional().getRuta());
                return servidor.getNumeroIdentificacion();

            }
        }
    }

    /**
     *
     * @param uo
     * @param roles
     * @return
     * @throws DaoException
     */
    private List<RolServidor> recolectarServidorPorRolUnidadOrganizacion(List<UnidadOrganizacional> unidadesAcceso,
            List<String> roles) throws DaoException {
        List<RolServidor> puestos = new ArrayList<>();
        for (String rol : roles) {
            for (UnidadOrganizacional uo : unidadesAcceso) {
                puestos.addAll(rolServidorDao.buscarPorRol(rol, uo.getCodigo()));
            }
        }
        return puestos;
    }

    /**
     * Balancea los aprobadores del tramite.
     *
     * @param transicion
     * @param t
     * @return
     * @throws DaoException
     * @throws ec.gob.mrl.gestor.excepciones.ServicioException
     */
    private String balancearAprobadores(final Transicion transicion, final Tramite t,
            final InstitucionEjercicioFiscal institucion)
            throws DaoException, ec.com.atikasoft.proteus.excepciones.ServicioException, ServicioException {
        String usuarioAprobador = null;
//        if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.APROBACION.getCodigo())) {
//            List<TipoMovimientoDelegado> delegados =
//                    tipoMovimientoDelegadoDao.buscarPorTipoMovimientoEInstitucion(t.getTipoMovimiento().getId(),
//                    institucion.getId());
//            if (delegados.isEmpty()) {
//                throw new ServicioException(UtilCadena.concatenar(
//                        "No se asigno los usuarios aprobadores a la unidad organizacional ",
//                        institucion.getInstitucion().
//                        getNombre()));
//            } else {
//                long minimo = 9999999;
//                for (TipoMovimientoDelegado delegado : delegados) {
//                    long contador = tareaServicio.contar(delegado.getDelegadoCedula(),
//                            t.getInstitucionEjercicioFiscal().getInstitucion().getCodigo(), Boolean.TRUE);
//                    if (contador < minimo) {
//                        usuarioAprobador = delegado.getDelegadoCedula();
//                        minimo = contador;
//                    }
//                }
//            }
//        }
        return "1234";
    }

    /**
     *
     * @param t
     * @param i
     * @param usuarioDTO
     * @throws DaoException
     */
    private void registraEstadoDelTramite(final Tramite t, final Instancia i, final UsuarioVO usuarioDTO,
            final Transicion transicion) throws
            ServicioException {
        try {
            t.setEstado(i.getFase().getNombre());
            t.setCodigoFase(i.getFase().getCodigo());
            t.setFechaActualizacion(new Date());
            t.setUsuarioActualizacion(usuarioDTO.getServidor().getNumeroIdentificacion());
            t.setUsuarioAsignadoCedula(i.getUsuarioAsignadoCedula());
            t.setUsuarioAsignadoNombre(i.getUsuarioAsignadoNombre());
            if (t.getCodigoFase().equals(EstadosTramiteEnum.ELABORACION.getCodigo())) {
                List<Detalle> detalles = gestionServicio.buscarDetalles(i.getIdentificadorExterno(),
                        transicion.getFaseFinal().getId());
                if (detalles.size() > 0) {
                    t.setUsuarioAsignadoCedula(detalles.get(0).getUsuario());
                    t.setUsuarioAsignadoNombre(detalles.get(0).getNombreUsuario());
                } else {
                    t.setUsuarioAsignadoCedula(t.getUsuarioAsignadoCedulaElaborador());
                    t.setUsuarioAsignadoNombre(t.getUsuarioAsignadoNombreElaborador());
                }
            }
            tramiteDao.actualizar(t);
        } catch (Exception ex) {
            Logger.getLogger(TramiteServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Registra en la bitacora del tramite.
     *
     * @param t
     * @param tb
     * @param usuarioDTO
     * @throws DaoException
     */
    private void registrarTramiteBitacora(final Transicion tr, final TramiteBitacora tb, final Instancia i,
            final UsuarioVO usuarioDTO) throws ServicioException, DaoException {
        DistributivoDetalle dd = distributivoPuestoServicio.buscarDistributivoPorServidor(TipoIdentificacionEnum.CEDULA.
                getCodigo(),
                i.getUsuarioAsignadoCedula(), usuarioDTO.getInstitucionEjercicioFiscal().getId());
        if (tr.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.REVISION.getCodigo())) {
            tb.setCedulaRevision(dd.getServidor().getNumeroIdentificacion());
            tb.setNombreRevision(dd.getServidor().getApellidosNombres());
            if (dd.getDenominacionPuesto() != null) {
                tb.setCargoRevision(dd.getDenominacionPuesto().getNombre());
            } else {
                tb.setCargoRevision("*****************");
            }
            tb.setFechaRevision(new Date());
        } else if (tr.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.VALIDACION.getCodigo())) {
            tb.setCedulaValidacion(dd.getServidor().getNumeroIdentificacion());
            tb.setNombreValidacion(dd.getServidor().getApellidosNombres());
            if (dd.getDenominacionPuesto() != null) {
                tb.setCargoValidacion(dd.getDenominacionPuesto().getNombre());
            } else {
                tb.setCargoValidacion("****************");
            }
            tb.setFechaValidacion(new Date());
        } else if (tr.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.APROBACION.getCodigo())) {
            tb.setCedulaAprobacion(dd.getServidor().getNumeroIdentificacion());
            tb.setNombreAprobacion(dd.getServidor().getApellidosNombres());
            if (dd.getDenominacionPuesto() != null) {
                tb.setCargoAprobacion(dd.getDenominacionPuesto().getNombre());
            } else {
                tb.setCargoAprobacion("***************");
            }
            tb.setFechaAprobacion(new Date());
            tb.setFechaAprobacionLetras(UtilFechas.transformarFechasLetras(tb.getFechaAprobacion()));
        } else if (tr.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.LEGALIZACION.getCodigo())) {
            tb.setCedulaLegalizacion(dd.getServidor().getNumeroIdentificacion());
            tb.setNombreLegalizacion(dd.getServidor().getApellidosNombres());
            if (dd.getDenominacionPuesto() != null) {
                tb.setCargoLegalizacion(dd.getDenominacionPuesto().getNombre());
            } else {
                tb.setCargoLegalizacion("*****************");
            }
            tb.setFechaLegalizacion(new Date());
        }
        tramiteBitacoraDao.actualizar(tb);
    }

    /**
     * Este metodo busca un tramite y formulario.
     *
     * @param tarea Tarea
     * @return TramiteFormularioVO
     * @throws ServicioException ServicioException
     */
    public TramiteFormularioVO obtenerTramiteFormulario(final Tarea tarea) throws ServicioException {
        try {
            TramiteFormularioVO tfvo = new TramiteFormularioVO();
            tfvo.setFormulario(
                    gestionServicio.buscarFormulario(tarea.getIdentificadorFormulario()));
            tfvo.setTramite(
                    tramiteDao.buscarPorId(tarea.getIdentificadorExterno()));
            return tfvo;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca una bitacora de un tramite segun su id.
     *
     * @param tramiteId Long
     * @return TramiteBitacora
     * @throws ServicioException ServicioException
     */
    public TramiteBitacora buscarBitacoraDeTramite(final Long tramiteId) throws ServicioException {
        try {
            return tramiteBitacoraDao.buscarPorTramite(tramiteId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la actualizacion del movimiento.
     *
     * @param movimiento Movimiento
     * @throws ServicioException ServicioException
     */
    public void actualizarMovimiento(final Movimiento movimiento) throws ServicioException {
        try {
            movimientoDao.actualizar(movimiento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public void actualizarDocumentoHabilitanteMovimiento(final Movimiento movimiento,
            final MovimientoBitacora movimientoBitacora) throws ServicioException {
        try {
            if (movimiento.getNumeroMemo() != null) {
                movimiento.setNumeroMemo(movimiento.getNumeroMemo().toUpperCase());
                movimiento.setNumeroDocumentoHabilitante(movimiento.getNumeroMemo());
            }
            movimientoDao.actualizar(movimiento);
            movimientoBitacora.setMovimiento(movimiento);
            movimientosBitacoraDao.crear(movimientoBitacora);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Ejecuta logica durante el proceso del tramite.
     *
     * @param codigoFase Codigo de la fase del tramite.
     * @param tramite Datos del tramite.
     * @throws DaoException Error de ejecucion.
     */
    private void ejecucionLogica(final Tramite tramite, final Transicion transicion, final UsuarioVO usuario,
            final String comentario) throws DaoException, ServicioException {
        if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.APROBACION.getCodigo())) {
            eventosServicio.generarNumeroContrato(tramite);
        } else if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.LEGALIZACION.getCodigo())) {
            eventosServicio.generarNumeroContrato(tramite);
        } else if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.REGISTRADO.getCodigo())) {
            eventosServicio.registrarDevengaciones(tramite, usuario);
            eventosServicio.aplicarMovimientoPersonal(tramite, usuario);
            eventosServicio.guardarTrayectoriaLaboral(tramite);
            eventosServicio.enviarNotificacionDeAsignacionHorario(tramite, usuario, comentario);
        } else if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.ELIMINADO.getCodigo())) {
            eventosServicio.liberarReclutamiento(tramite);
        } else if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.ANULADO.getCodigo())) {
            eventosServicio.liberarReclutamiento(tramite);
            eventosServicio.registrarDatosAnulacion(tramite, usuario, comentario);
        } else if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.RECHAZADO.getCodigo())) {
            eventosServicio.liberarReclutamiento(tramite);
            eventosServicio.registrarDatosAnulacion(tramite, usuario, comentario);
        }
    }

    /**
     * Este metodo procesa la busqueda segun el numero de documento.
     *
     * @param numeroDocumento Integer
     * @return Movimiento
     * @throws ServicioException
     */
    public Movimiento buscarMovimientoPorDocumentoHabilitante(final String numeroDocumento) throws ServicioException {
        try {
            return movimientoDao.buscarPorDocumentoHabilitante(numeroDocumento);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Genera el numero de registro de un movimiento de personal
     *
     * @param movimientoId Identificador del movimimiento.
     * @param usuarioDTO Datos del usuario conectado.
     * @throws ServicioException
     */
    public void generarNumeroRegistro(final Long movimientoId, final UsuarioVO usuarioDTO) throws ServicioException {
        try {
            Movimiento movimiento = movimientoDao.buscarPorId(movimientoId);
            if (movimiento.getNumeroRegistro() == null || movimiento.getNumeroRegistro().trim().isEmpty()) {
                InstitucionEjercicioFiscal institucion = movimiento.getTramite().getInstitucionEjercicioFiscal();
                institucionDao.lock(institucion);
                institucion.setContadorRegistro(institucion.getContadorRegistro() + 1);
                movimiento.setNumeroRegistro(UtilMatematicas.rellenarConCerosIzq(
                        institucion.getContadorRegistro(), 10));
                movimiento.setFechaRegistro(new Date());
                movimiento.setFechaActualizacion(new Date());
                movimiento.setUsuarioActualizacion(usuarioDTO.getServidor().getNumeroIdentificacion());
                movimientoDao.actualizar(movimiento);

                institucionDao.actualizar(institucion);
            } else {
                throw new ServicioException(UtilCadena.concatenar("Movimiento con id :", movimientoId,
                        " ya tiene registrado el numero de registro"));
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo crea el archivo y lo actualiza.
     *
     * @param movimiento Movimiento
     * @param archivo Archivo
     * @throws ServicioException Captura de errores
     */
    public void guardarArchivoMovimiento(final Movimiento movimiento, final Archivo archivo) throws ServicioException {
        try {
            Archivo crear = archivoDao.crear(archivo);
            movimiento.setArchivo(crear);
            movimientoDao.actualizar(movimiento);
        } catch (Exception ex) {
            Logger.getLogger(TramiteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método crea una registro de movimientos bitacora.
     *
     * @param mb MovimientoBitacora
     * @throws ServicioException ServicioException
     */
    public void guardarMovimientosBitacora(final MovimientoBitacora mb, String usuario) throws ServicioException {
        try {
            movimientosBitacoraDao.crear(mb);
        } catch (Exception ex) {
            Logger.getLogger(TramiteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recupera el nombre completo del canton.
     *
     * @param cantonId
     * @return
     * @throws ServicioException
     */
//    public String buscarNombreCanonicoCanton(final Long cantonId) throws ServicioException {
//        try {
//            return catalogoServicio.buscarNombreCanonicoCanton(cantonId);
//        } catch (Exception e) {
//            throw new ServicioException(e);
//        }
//    }
    /**
     * Recupera los devengiamientos de un servidor.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param institucionId
     * @param fechaCorte
     * @return
     * @throws ServicioException
     */
    public List<Devengamiento> buscarDevengamientosPorServidor(final String tipoIdentificacion,
            final String numeroIdentificacion, final Long institucionId, final Date fechaCorte) throws ServicioException {
        try {
            return devengamientoDao.buscarPorServidor(tipoIdentificacion, numeroIdentificacion, institucionId,
                    fechaCorte);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Genera el numero del tramite.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return Numero generado.
     * @throws DaoException Error de ejecucion.
     */
    private String generarNumeroTramite(final InstitucionEjercicioFiscal institucion) throws DaoException {
        institucionDao.lock(institucion);
        institucion.setContadorTramite(institucion.getContadorTramite() + 1);
        institucionDao.actualizar(institucion);
        return UtilMatematicas.rellenarConCerosIzq(institucion.getContadorTramite(), 10);
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public Tramite buscarTramite(final Long id) throws ServicioException {
        try {
            return tramiteDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para retornar un error para añadir a la tabla de errores.
     *
     * @param seccion String
     * @param campo String
     * @param mensaje String
     * @return err ErroresVO
     */
    public ErroresVO registroError(final String seccion, final String campo, final String referencia,
            final String mensaje) {
        ErroresVO err = new ErroresVO();
        err.setSeccion(seccion);
        err.setCampo(campo);
        err.setMensajeError(mensaje);
        err.setReferencia("#" + referencia);
        return err;
    }

    /**
     * Metodo para validar si existe otra licencia del mismo tipo para solicitudes.
     *
     * @param tipoDocumento String
     * @param numeroDocumento String
     * @param nombreServidor String
     * @param tipoMovimientoId String
     * @param tipoMovimientoNombre String
     * @param err List<ErroresVO>
     * @return existe Boolean
     */
    public Boolean existenLicenciaEnCursoSolicitud(final String tipoDocumento, final String numeroDocumento,
            final String nombreServidor, final Long tipoMovimientoId, final String tipoMovimientoNombre,
            StringBuilder error) {
        Boolean existe = Boolean.FALSE;
        try {
            Date fechaActual = UtilFechas.truncarFecha(new Date());
            if (tipoDocumento != null && numeroDocumento != null) {
                List<Licencia> licencias = licenciaDao.buscarEnCurso(tipoDocumento, numeroDocumento,
                        tipoMovimientoId, fechaActual);
                if (!licencias.isEmpty()) {
                    error.append("El servidor ").append(nombreServidor).append(
                            " tiene actualmente en curso una licencia de '").append(tipoMovimientoNombre).append("'\n");
                    existe = Boolean.TRUE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error.append("Error no controlado al validar licencias en curso");
        }
        return existe;
    }

    /**
     * Metodo que se encarga de guardar los devengamientos.
     *
     * @param usuario Usuario Logeado
     * @param dv Tipo de movimiento
     * @throws ServicioException En caso de error
     */
    public void guardarDevengacion(final Devengamiento dv, final UsuarioVO usuarioDTO,
            final Long institucionId) throws ServicioException {
        try {
            dv.setUsuarioCreacion(usuarioDTO.getServidor().getNumeroIdentificacion());
            dv.setFechaCreacion(new Date());
            dv.setVigente(Boolean.TRUE);
            dv.setArchivo(null);
            dv.setDiasDevengados(0);
            dv.setDiasPendientes(0);
            dv.setFechaDocumento(null);
            dv.setFechaPagoAnticipado(null);
            dv.setLicencia(null);
            dv.setNumero("xxx");
            dv.setNumeroDocumento(null);
            dv.setObservacionPagoAnticipado(null);
            dv.setPagoAnticipado(Boolean.FALSE);
            devengamientoDao.crear(dv);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }

    }

    /**
     * metodo para actualizar un parametro institucional con el archivo.
     *
     * @param dv
     * @param archivo
     * @throws ServicioException
     */
    public void actualizarDevengamiento(final Devengamiento dv, final Archivo archivo) throws
            ServicioException {
        try {
            dv.setFechaCreacion(new Date());
            dv.setVigente(Boolean.TRUE);
            if (archivo.getId() != null) {
                archivoDao.actualizar(archivo);
            } else {
                Archivo crear = archivoDao.crear(archivo);
                devengamientoDao.actualizar(dv);
                dv.setArchivo(crear);
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Realiza la reasignacion de una tarea del tramite.
     *
     * @param tarea
     * @param reasignacionTarea
     * @throws ServicioException
     */
    public void reasignarTareaDelTramite(final Tarea tarea, final ReasignacionTarea reasignacionTarea) throws
            ServicioException {
        try {
            Tramite t = tramiteDao.buscarPorId(tarea.getIdentificadorExterno());
            t.setUsuarioAsignadoCedula(tarea.getUsuarioAsignado());
            t.setUsuarioAsignadoNombre(tarea.getNombreUsuarioAsignado());
            tramiteDao.actualizar(t);
            tareaServicio.actualizarReasignacionTramite(tarea);
            tareaServicio.guardarReasignacionTarea(reasignacionTarea);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo cambia actualiza los valores genericos del tramite, segun el documento habilitante.
     *
     * @param tramite Tramite
     * @throws ServicioException Captura de errores
     */
    public void cambioValoresFirmasTramite(final Tramite tramite) throws
            ServicioException {
        try {
            if (tramite.getCatalogoAutoridadNominadora().getId() == null) {
                tramite.setCatalogoAutoridadNominadora(null);
            }
            if (tramite.getCatalogoRepresentanteRRHH().getId() == null) {
                tramite.setCatalogoRepresentanteRRHH(null);
            }
            tramiteDao.actualizar(tramite);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo cambia actualiza los valores genericos del tramite, segun el documento habilitante.
     *
     * @param tramite Tramite
     * @throws ServicioException Captura de errores
     */
    public void cambioValoresDocumentoHabilitanteTramite(final Tramite tramite) throws
            ServicioException {
        try {
            tramiteAuxiliarDao.actualizar(tramite.getTramiteAuxiliar());
            movimientoDao.actualizarDatosDocumentoHabilitenteMasivos(tramite);
            ingresoDao.actualizarDatosDocumentoHabilitenteMasivos(tramite);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Se encarga de enviar el archivo de carga masiva para su ejecucion.
     *
     * @param tramiteId
     * @param nombreArchivo
     * @param archivo
     * @param usuario
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<String> enviarCargaMasiva(final Long tramiteId, final String nombreArchivo, final InputStream archivo,
            final UsuarioVO usuario) throws ServicioException {
        try {
            Tramite tramite = tramiteDao.buscarPorId(tramiteId);
            TipoMovimiento tm = tramite.getTipoMovimiento();
            List<String> errores = new ArrayList<>();
            BufferedReader in = new BufferedReader(new InputStreamReader(archivo, StandardCharsets.ISO_8859_1));
            Map<Long, List<String>> registros = UtilArchivos.parsearArchivoCSV(IOUtils.toByteArray(in,
                    StandardCharsets.ISO_8859_1), ';', 20);
            if (tm.getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
                cargaMasivaIngresos(registros, errores, tramite, tm, usuario);
            } else if (tm.getClase().getGrupo().getNemonico().equals(GrupoEnum.SALIDAS.getCodigo())) {
                cargaMasivaSalidas(registros, errores, tramite, tm, usuario);
            }
            return errores;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Carga masiva de ingresos.
     *
     * @param registros
     * @param errores
     * @param tramite
     * @param tm
     * @throws NumberFormatException
     * @throws ServicioException
     */
    private void cargaMasivaIngresos(final Map<Long, List<String>> registros, final List<String> errores,
            final Tramite tramite, final TipoMovimiento tm, final UsuarioVO usuario) throws NumberFormatException,
            ServicioException, DaoException, MalformedURLException, DatatypeConfigurationException {
        ParametroInstitucional p = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                tramite.getInstitucionEjercicioFiscal().getInstitucion().getId(),
                ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
        List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                usuario.getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());

        List<CargaMasivaMovimientoVO> detalles = new ArrayList<>();
        int f = 1;
        System.out.println("iniciando validacion");
        if (registros.size() > 0) {
            for (List<String> r : registros.values()) {
                System.out.println("fila:" + f);
                CargaMasivaMovimientoVO vo = new CargaMasivaMovimientoVO();
                for (FormatoIngresoEnum fmt : FormatoIngresoEnum.values()) {
                    // validar tipos de datos.
                    if (fmt.getTipoDato().equals("N") && !r.get(fmt.getIndice()).trim().isEmpty()) {
                        if (!NumberUtils.isNumber(r.get(fmt.getIndice()))) {
                            TramiteHelper.registrarError(r, errores, f, fmt, "Valor debe ser numérico");
                        }
                    } else if (fmt.getTipoDato().equals("F") && !r.get(fmt.getIndice()).trim().isEmpty()) {
                        try {
                            Date fecha = DateUtils.parseDate(r.get(fmt.getIndice()), UtilFechas.FORMATO_FECHA);
                            if (UtilFechas.obtenerAnio(fecha) < 1900) {
                                TramiteHelper.registrarError(r, errores, f, fmt, "Valor de fecha es menor a 1900");
                            }
                        } catch (ParseException e) {
                            TramiteHelper.registrarError(r, errores, f, fmt, "Valor debe ser fecha");
                        }
                    }
                    // validar dominios
                    if (fmt.getDominio() != null && !fmt.getDominio().contains(r.get(fmt.getIndice()))) {
                        TramiteHelper.registrarError(r, errores, f, fmt, " No es válido, debe usar "
                                + fmt.getDominio());
                    }
                }
                // validar codigo de puesto.
                if (StringUtils.isEmpty(r.get(FormatoIngresoEnum.CODIGO_PUESTO.getIndice()))) {
                    TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO,
                            "Valor es obligatorio");
                } else if (NumberUtils.isNumber(r.get(FormatoIngresoEnum.CODIGO_PUESTO.getIndice()))) {
                    Long codigo = Long.valueOf(r.get(FormatoIngresoEnum.CODIGO_PUESTO.getIndice()));
                    List<DistributivoDetalle> puesto = distributivoPuestoServicio.buscarPorCodigoPuesto(codigo,
                            tramite.getInstitucionEjercicioFiscal().getId());
                    if (puesto.isEmpty()) {
                        TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO,
                                "No existe en el distributivo");
                    } else {
                        DistributivoDetalle dd = puesto.get(0);
                        TramiteHelper.validarRegimenLaboral(tm, dd, errores, f);
                        TramiteHelper.validarEstadoPuesto(tm, dd, errores, f);
                        TramiteHelper.validarEstadoPersonal(tm, dd, errores, f);
                        TramiteHelper.validarPuestoPerteneceAUnidad(unidadesAcceso, usuario, p, dd, errores, f);
                        vo.setDistributivoDetalle(dd);
                        vo.setDatos(r);
                        detalles.add(vo);
                    }
                } else {
                    TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO, "No es númerico");
                }
                // validar codigo de puesto propuesto.
                if (StringUtils.isEmpty(r.get(FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice()))) {
                    TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO,
                            "Es obligatorio");
                } else {
                    if (NumberUtils.isNumber(r.get(FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice()))) {
                        Long codigo = Long.valueOf(r.get(FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO.getIndice()));
                        List<DistributivoDetalle> puesto = distributivoPuestoServicio.buscarPorCodigoPuesto(codigo,
                                tramite.getInstitucionEjercicioFiscal().getId());
                        if (puesto.isEmpty()) {
                            TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO,
                                    "No existe en el distributivo");
                        } else {
                            DistributivoDetalle dd = puesto.get(0);
                            TramiteHelper.validarEstadoPuestoPropuesto(tm, dd, errores, f);
                            vo.setDistributivoDetalle(dd);
                            vo.setDatos(r);
                            // TODO verificar que el puesto propuesto se cargue en el campo de puesto propuesto 
                            //en el movimiento.
                            //detalles.add(vo);
                        }
                    } else {
                        TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.CODIGO_PUESTO_PROPUESTO,
                                "No es númerico");
                    }
                }
                // validar tipo de periodo
                if (StringUtils.isEmpty(r.get(FormatoIngresoEnum.TIPO_PERIODO.getIndice()))) {
                    TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.TIPO_PERIODO, "Es obligatorio");
                }
                // validar obligatoriedad.
                if (r.get(FormatoIngresoEnum.TIPO_PERIODO.getIndice()).equals(
                        TipoPeriodoMovimientoEnum.FECHA.getCodigo())) {
                    validarIngreso(tm.getFechaRigeAPartirDe(), f, FormatoIngresoEnum.FECHA_DESDE, r, errores);
                }
                validarIngreso(tm.getFechaHasta(), f, FormatoIngresoEnum.FECHA_HASTA, r, errores);
                validarIngresoDatosServidor(tm.getTipoDocumento(), f, FormatoIngresoEnum.TIPO_IDENTIFICACION, r,
                        errores);
                validarIngresoDatosServidor(tm.getNumeroDocumento(), f,
                        FormatoIngresoEnum.NUMERO_IDENTIFICACION,
                        r, errores);
                validarIngresoDatosServidor(tm.getFechaIngresoInstitucion(), f,
                        FormatoIngresoEnum.FECHA_INGRESO_INSTITUCION, r, errores);
                validarIngresoDatosServidor(tm.getFechaIngresoSectorPublico(), f,
                        FormatoIngresoEnum.FECHA_INGRESO_PUBLICO, r, errores);
                validarIngreso(tm.getTipoDesignacion() == null ? "" : tm.getTipoDesignacion(), f,
                        FormatoIngresoEnum.TIPO_DESIGNACION, r, errores);

                if (tm.getAreaAccionPersonal()) {
                    validarIngreso(tm.getApDocumentoPrevio(), f, FormatoIngresoEnum.DOCUMENTO_PREVIO, r, errores);
                    validarIngreso(tm.getApNumeroDocumento(), f, FormatoIngresoEnum.NUMERO_DOCUMENTO, r, errores);
                    validarIngreso(tm.getApFechaDocumento(), f, FormatoIngresoEnum.FECHA_DOCUMENTO, r, errores);
                    validarIngreso(tm.getApExplicacion(), f, FormatoIngresoEnum.EXPLICACION, r, errores);

                }
                if (tm.getAreaContratoLosep()) {
                    validarIngreso(tm.getAntecedentesContrato(), f, FormatoIngresoEnum.ANTECEDENTES, r, errores);
                    validarIngreso(tm.getActividadesContrato(), f, FormatoIngresoEnum.ACTIVIDADES, r, errores);
                    validarIngreso(tm.getRenovacion(), f, FormatoIngresoEnum.RENOVACION, r, errores);
                }

                // obtener el nombre del servidor.
                if (!r.get(FormatoIngresoEnum.TIPO_IDENTIFICACION.getIndice()).trim().isEmpty()
                        && !r.get(FormatoIngresoEnum.NUMERO_IDENTIFICACION.getIndice()).trim().isEmpty()) {
                    if (tramite.getTipoMovimiento().getIngresoPorReclutamiento()) {
                        // buscar en reclutamiento.
                        Reclutamiento reclutamiento = reclutamientoServicio.buscarPorIdentificacionYEstado(
                                r.get(FormatoIngresoEnum.TIPO_IDENTIFICACION.
                                        getIndice()), r.get(FormatoIngresoEnum.NUMERO_IDENTIFICACION.getIndice()),
                                EstadoReclutamientoEnum.REGISTRADO.getCodigo());
                        if (reclutamiento == null) {
                            TramiteHelper.registrarError(r, errores, f, FormatoIngresoEnum.NUMERO_IDENTIFICACION,
                                    "No se encuentra registrado en reclutamiento.");
                        } else {
                            vo.setNombreServidor(reclutamiento.getApellidoNombre());
                        }
                    } else {
                        // buscar en ws de personas.

                        StringBuilder identificacion = new StringBuilder(r.get(FormatoIngresoEnum.NUMERO_IDENTIFICACION.getIndice()));
                        if (r.get(FormatoIngresoEnum.TIPO_IDENTIFICACION.getIndice()).equals(
                                TipoDocumentoEnum.CEDULA.getNemonico())
                                && identificacion.length() == 9) {
                            identificacion.insert(0, "0");
                        }
                        PersonaVO personaVO = servidorServicio.buscarPersona(
                                r.get(FormatoIngresoEnum.TIPO_IDENTIFICACION.getIndice()),
                                identificacion.toString(), usuario);
                        if (personaVO == null) {
                            TramiteHelper.registrarError(r, errores, f,
                                    FormatoIngresoEnum.NUMERO_IDENTIFICACION,
                                    "No se encuentra registrado en el Sistema de Personas.");
                        } else {
                            vo.setNombreServidor(personaVO.getNombres());
                        }
                    }
                }
                f++;
            }
        } else {
            errores.add("Archivo CSV no contiene registros.");
        }

//        System.out.println("total errores:" + errores.size());
        if (errores.isEmpty()) {
            // grabar los datos.
//            System.out.println("inicindo grabacion:" + errores.size());
            guardarMovimientosMasivos(tramite, detalles, usuario);
        }
    }

    /**
     * Carga masiva de salidas.
     *
     * @param registros
     * @param erroresc
     * @param tramite
     * @param tm
     * @throws NumberFormatException
     * @throws ServicioException
     */
    private void cargaMasivaSalidas(final Map<Long, List<String>> registros, final List<String> errores,
            final Tramite tramite, final TipoMovimiento tm, final UsuarioVO usuario) throws NumberFormatException,
            ServicioException, DaoException {
        ParametroInstitucional p = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                tramite.getInstitucionEjercicioFiscal().getInstitucion().getId(),
                ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
        List<CargaMasivaMovimientoVO> detalles = new ArrayList<>();
        List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                usuario.getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());

        int f = 1;
        if (registros.size() > 0) {
            for (List<String> r : registros.values()) {
                for (FormatoSalidaEnum fmt : FormatoSalidaEnum.values()) {
                    // validar tipos de datos.
                    if (fmt.getTipoDato().equals("N") && !r.get(fmt.getIndice()).trim().isEmpty()) {
                        if (!NumberUtils.isNumber(r.get(fmt.getIndice()))) {
                            TramiteHelper.registrarError(r, errores, f, fmt, "Valor debe ser numérico");
                        }
                    } else if (fmt.getTipoDato().equals("F") && !r.get(fmt.getIndice()).trim().isEmpty()) {
                        try {
                            DateUtils.parseDate(r.get(fmt.getIndice()), UtilFechas.FORMATO_FECHA);
                        } catch (ParseException e) {
                            TramiteHelper.registrarError(r, errores, f, fmt, "Valor debe ser fecha");
                        }
                    }
                    // validar dominios
                    if (fmt.getDominio() != null && !fmt.getDominio().contains(r.get(fmt.getIndice()))) {
                        TramiteHelper.registrarError(r, errores, f, fmt, " no es válido, debe usar "
                                + fmt.getDominio());
                    }
                }
                // validar codigo de puesto.
                if (StringUtils.isEmpty(r.get(FormatoSalidaEnum.CODIGO_PUESTO.getIndice()))) {
                    TramiteHelper.registrarError(r, errores, f, FormatoSalidaEnum.CODIGO_PUESTO, "Es obligatorio");
                } else if (NumberUtils.isNumber(r.get(FormatoSalidaEnum.CODIGO_PUESTO.getIndice()))) {
                    Long codigo = Long.valueOf(r.get(FormatoSalidaEnum.CODIGO_PUESTO.getIndice()));
                    List<DistributivoDetalle> puesto = distributivoPuestoServicio.buscarPorCodigoPuesto(codigo,
                            tramite.
                            getInstitucionEjercicioFiscal().getId());
                    if (puesto.isEmpty()) {
                        TramiteHelper.registrarError(r, errores, f, FormatoSalidaEnum.CODIGO_PUESTO,
                                "No existe en el distributivo");
                    } else {
                        DistributivoDetalle dd = puesto.get(0);
                        TramiteHelper.validarEstadoPuesto(tm, dd, errores, f);
                        TramiteHelper.validarPuestoPerteneceAUnidad(unidadesAcceso, usuario, p, dd, errores, f);
                        CargaMasivaMovimientoVO vo = new CargaMasivaMovimientoVO();
                        vo.setDistributivoDetalle(dd);
                        vo.setDatos(r);
                        detalles.add(vo);
                    }
                }
                // validar tipo de periodo
                if (StringUtils.isEmpty(r.get(FormatoSalidaEnum.TIPO_PERIODO.getIndice()))) {
                    TramiteHelper.registrarError(r, errores, f, FormatoSalidaEnum.TIPO_PERIODO, "es obligatorio");
                }

                // validar obligatoriedad.
                if (r.get(FormatoSalidaEnum.TIPO_PERIODO.getIndice()).equals(
                        TipoPeriodoMovimientoEnum.FECHA.getCodigo())) {
                    validarSalida(tm.getFechaRigeAPartirDe(), f, FormatoSalidaEnum.FECHA_DESDE, r, errores);
                }

                if (tm.getAreaInformacionSalida()) {
                    validarSalida(tm.getFechaPresentaRenuncia(), f, FormatoSalidaEnum.FECHA_PRESENTA_RENUNCIA, r,
                            errores);
                    validarSalida(tm.getFechaAceptacionRenuncia(), f, FormatoSalidaEnum.FECHA_ACEPTA_RENUNCIA, r,
                            errores);
                }

                if (tm.getAreaFallecimiento()) {
                    validarSalida(tm.getFechaFallecimiento(), f, FormatoSalidaEnum.FECHA_FALLECIMIENTO, r, errores);
                    validarSalida(tm.getCasoFallecimiento(), f, FormatoSalidaEnum.CASO_FALLECIMIENTO, r, errores);
                }
                if (tm.getAreaTerminacionContrato()) {
                    validarSalida(tm.getNumeroContratoAnterior(), f, FormatoSalidaEnum.NUMERO_CONTRATO_ANTERIOR, r,
                            errores);
                    validarSalida(tm.getFechaInicioContratoAnterior(), f,
                            FormatoSalidaEnum.FECHA_INICIO_CONTRATO_ANTERIOR, r, errores);
                }
                if (tm.getAreaAccionPersonal()) {
                    validarSalida(tm.getApDocumentoPrevio(), f, FormatoSalidaEnum.DOCUMENTO_PREVIO, r, errores);
                    validarSalida(tm.getApNumeroDocumento(), f, FormatoSalidaEnum.NUMERO_DOCUMENTO, r, errores);
                    validarSalida(tm.getApFechaDocumento(), f, FormatoSalidaEnum.FECHA_DOCUMENTO, r, errores);
                    validarSalida(tm.getApExplicacion(), f, FormatoSalidaEnum.EXPLICACION, r, errores);
                }
                f++;
            }
        } else {
            errores.add("Archivo CSV no contiene registros.");
        }

        if (errores.isEmpty()) {
            // grabar los datos.
            guardarMovimientosMasivos(tramite, detalles, usuario);

        }
    }

    /**
     *
     * @param campo
     * @param fila
     * @param fmt
     * @param r
     * @param errores
     */
    private void validarIngreso(final String campo, final int fila, final FormatoIngresoEnum fmt, final List<String> r,
            final List<String> errores) {
        if (campo == null) {
            TramiteHelper.registrarError(r, errores, fila, fmt, "no se encuentra configurado en el tipo de movimiento");
        } else {
            if (CamposConfiguracionEnum.OBLIGATORIO.getCodigo().equals(campo) && StringUtils.isEmpty(
                    r.get(fmt.getIndice()))) {
                TramiteHelper.registrarError(r, errores, fila, fmt, "es obligatorio");
            } else if (CamposConfiguracionEnum.SOLO_LECTURA.getCodigo().equals(campo) && !StringUtils.isEmpty(
                    r.get(fmt.getIndice()))) {
                TramiteHelper.registrarError(r, errores, fila, fmt, "no pueder ser cargado por que el campo es "
                        + "de solo lectura");
            }
        }
    }

    /**
     *
     * @param campo
     * @param fila
     * @param fmt
     * @param r
     * @param errores
     */
    private void validarIngresoDatosServidor(final String campo, final int fila, final FormatoIngresoEnum fmt,
            final List<String> r, final List<String> errores) {
        if (campo == null) {
            TramiteHelper.registrarError(r, errores, fila, fmt, "no se encuentra configurado en el tipo de movimiento");
        } else {
            if (CamposConfiguracionEnum.OBLIGATORIO.getCodigo().equals(campo) && StringUtils.isEmpty(
                    r.get(fmt.getIndice()))) {
                TramiteHelper.registrarError(r, errores, fila, fmt, "es obligatorio");
            }
        }
    }

    /**
     *
     * @param campo
     * @param fila
     * @param fmt
     * @param r
     * @param errores
     */
    private void validarSalida(final String campo, final int fila, final FormatoSalidaEnum fmt,
            final List<String> r, final List<String> errores) {
        if (campo == null) {
            TramiteHelper.registrarError(r, errores, fila, fmt, "no se encuentra configurado en el tipo de movimiento");
        } else {
            if (CamposConfiguracionEnum.OBLIGATORIO.getCodigo().equals(campo) && StringUtils.isEmpty(r.get(
                    fmt.getIndice()))) {
                TramiteHelper.registrarError(r, errores, fila, fmt, "es obligatorio");
            } else if (CamposConfiguracionEnum.SOLO_LECTURA.getCodigo().equals(campo) && !StringUtils.isEmpty(r.get(fmt.
                    getIndice()))) {
                TramiteHelper.registrarError(r, errores, fila, fmt, "no pueder ser cargado");
            }
        }
    }

    /**
     *
     * @param usuarioVO
     * @return
     * @throws ServicioException
     */
    private List<UnidadOrganizacional> recuperarUnidadesAccesoRRHH(UsuarioVO usuarioVO) throws ServicioException,
            DaoException {
        ParametroInstitucional rrhh = parametroInstitucionalDao.buscarPorInstitucionYNemonico(usuarioVO.
                getInstitucionEjercicioFiscal().
                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_DIRECCION_RRHH.
                getCodigo());
        List<UnidadOrganizacional> unidadesAcceso;
        if (rrhh == null || rrhh.getValorTexto().trim().isEmpty()) {
            throw new ServicioException(
                    "El parámetro institucional de código de unidad organizacional de RRHH "
                    + "no se encuentra registrado.");
        } else {
            List<UnidadOrganizacional> oficina = unidadOrganizacionalDao.buscarPorNemonico(
                    rrhh.getValorTexto());
            if (oficina.isEmpty()) {
                throw new ServicioException("No se pudo localizar la unidad organizacional de "
                        + "RRHH con código:" + rrhh.
                        getValorTexto());
            } else {
                unidadesAcceso = new ArrayList<>();
                recolectarUnidadesDeRRHH(oficina.get(0), unidadesAcceso);
                return unidadesAcceso;
            }
        }
    }

    /**
     * Se utiliza para avanzar un trámite del estado inicial al estado final especificados.
     *
     * @param tramiteId Identificador del tramite.
     * @param codigoInstitucion Codigo de la institucion.
     * @param ejercicioFiscal Ejercicio fiscal en curso.
     * @param comentario Conentario de la operacion.
     * @param usuarioDTO
     * @param esRRHH
     * @param codigoEstadoFinal
     * @param codigoEstadoInicial
     * @throws ServicioException Error de ejecucion.
     */
    public void gestionarTramite(final Long tramiteId, final String codigoInstitucion, final Integer ejercicioFiscal,
            final String comentario, final UsuarioVO usuarioDTO, final Boolean esRRHH,
            final String codigoEstadoInicial, final String codigoEstadoFinal) throws ServicioException {
        try {
            Tramite t = tramiteDao.buscarPorId(tramiteId);
            Transicion transicion = gestionServicio.buscarTransicion(codigoEstadoInicial, codigoEstadoFinal,
                    t.getCodigoProceso());
            avanzarTramite(tramiteId, transicion.getId(), null, comentario, usuarioDTO, esRRHH);
            // registrar en reclutamiento
            for (Movimiento m : t.getListaMovimientos()) {
                if (m.getVigente() && m.getDistributivoDetalleId() != null) {
                    Reclutamiento r = reclutamientoServicio.buscarPorDistributivo(m.getDistributivoDetalleId());
                    if (r != null) {
                        r.setEmMovimientoPersonal(false);
                        r.setUsuarioActualizacion(m.getUsuarioCreacion());
                        r.setFechaActualizacion(new Date());
                        reclutamientoServicio.actualizarReclutamiento(r);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }
}
