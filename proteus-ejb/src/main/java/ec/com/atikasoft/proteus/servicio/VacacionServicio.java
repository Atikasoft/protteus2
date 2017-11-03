/**
 * VacacionServicio.java
 *
 * Quito - Ecuador
 *
 * 25/10/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ArchivoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleHistoricoDao;
import ec.com.atikasoft.proteus.dao.FeriadoDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PlanificacionVacacionDao;
import ec.com.atikasoft.proteus.dao.PlanificacionVacacionDetalleDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionDetalleDao;
import ec.com.atikasoft.proteus.dao.VacacionParametroDao;
import ec.com.atikasoft.proteus.dao.VacacionProcesoDao;
import ec.com.atikasoft.proteus.dao.VacacionSolicitudDao;
import ec.com.atikasoft.proteus.dao.VacacionSolicitudHistoricoDao;
import ec.com.atikasoft.proteus.dao.VacacionSolicitudLiquidacionDao;
import ec.com.atikasoft.proteus.enums.EstadoLiquidacionVacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoAcumulacionEnum;
import ec.com.atikasoft.proteus.enums.TipoAusentismoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.modelo.Institucion;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.JustificacionAsistencia;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.modelo.VacacionProceso;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudHistorico;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudLiquidacion;
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.pdf.GenerarAccionPersonalLiquidacionVacacion;
import ec.com.atikasoft.proteus.pdf.GenerarAccionPersonalSolicitudVacacion;
import ec.com.atikasoft.proteus.pdf.MarcaAgua;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.temporizadores.AusentismoTemporizador;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import ec.com.atikasoft.proteus.vo.PlanificacionVacacionVO;
import ec.com.atikasoft.proteus.vo.RegistroReporteSaldosVacacionesVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class VacacionServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private static final Logger LOG = Logger.getLogger(VacacionServicio.class.getCanonicalName());
    /**
     * DAO de Feriado
     */
    @EJB
    private FeriadoDao feriadoDao;
    /**
     * DAO de PlanificacionVacacion.
     */
    @EJB
    private PlanificacionVacacionDao planificacionVacacionDao;
    /**
     * DAO de PlanificacionVacacionDetalle.
     */
    @EJB
    private PlanificacionVacacionDetalleDao planificacionVacacionDetalleDao;
    /**
     * DAO de VacacionParametro
     */
    @EJB
    private VacacionParametroDao vacacionParametroDao;
    /**
     * DAO de VacacionSolicitudHistorico
     */
    @EJB
    private VacacionSolicitudHistoricoDao vacacionSolicitudHistoricoDao;
    /**
     * DAO de VacacionSolicitud
     */
    @EJB
    private VacacionSolicitudDao vacacionSolicitudDao;
    /**
     * DAO de VacacionSolicitudLiquidacion
     */
    @EJB
    private VacacionSolicitudLiquidacionDao vacacionSolicitudLiquidacionDao;
    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleHistoricoDao distributivoDetalleHistoricoDao;

    /**
     * DAO de archivos
     */
    @EJB
    private ArchivoDao archivoDao;

    /**
     * DAO de Vacacion
     */
    @EJB
    private VacacionDao vacacionDao;
    /**
     * DAO de Vacacion
     */
    @EJB
    private VacacionDetalleDao vacacionDetalleDao;
    /**
     * Servicio de mensajeria.
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Distributivo.
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;
    /**
     * DAO de Vacacion Proceso.
     */
    @EJB
    private VacacionProcesoDao vacacionProcesoDao;

    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Permite el registro de un feriado
     *
     * @param feriado registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarFeriado(final Feriado feriado) throws ServicioException {
        try {
            feriado.setDescripcion(feriado.getDescripcion().toUpperCase());
            feriadoDao.crear(feriado);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un feriado
     *
     * @param feriado registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarFeriado(final Feriado feriado) throws ServicioException {
        try {
            feriado.setDescripcion(feriado.getDescripcion().toUpperCase());
            feriadoDao.actualizar(feriado);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un feriado
     *
     * @param feriado registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarFeriado(final Feriado feriado) throws ServicioException {
        try {
            feriado.setVigente(false);
            feriadoDao.actualizar(feriado);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite obtener los registros por fecha
     *
     * @param fecha fecha
     * @param idEjercicioFiscal identificador del ejercico fiscal
     * @param idRegimenLaboral identificador del regimen laboral
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Feriado> listarFeriadoPorFechaEjercicioFiscalYRegimen(final Date fecha, final Long idEjercicioFiscal,
            final Long idRegimenLaboral) throws ServicioException {
        try {
            return feriadoDao.buscarPorFecha(fecha, idEjercicioFiscal, idRegimenLaboral);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los feriados por nombre
     *
     * @param nombre String descripcion del feriado
     * @param idRegimenLaboral identificador del regimen laboral
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Feriado> listarTodosFeriadoPorNombreYPorRegimenLaboral(final String nombre, final Long idRegimenLaboral)
            throws ServicioException {
        try {
            List<Feriado> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = feriadoDao.buscarVigentePorRegimenLaboral(idRegimenLaboral);
            } else {
                lista = feriadoDao.buscarTodosPorNombre(nombre.toUpperCase(), idRegimenLaboral);
            }
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de un VacacionParametro
     *
     * @param vacacionParametro registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarVacacionParametro(final VacacionParametro vacacionParametro)
            throws ServicioException {
        try {
            vacacionParametro.setNombre(vacacionParametro.getNombre().toUpperCase());
            vacacionParametroDao.crear(vacacionParametro);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un VacacionParametro
     *
     * @param vacacionParametro registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarVacacionParametro(VacacionParametro vacacionParametro)
            throws ServicioException {
        try {
            vacacionParametro.setNombre(vacacionParametro.getNombre().toUpperCase());
            vacacionParametroDao.actualizar(vacacionParametro);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un VacacionParametro
     *
     * @param vacacionParametro registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarVacacionParametro(final VacacionParametro vacacionParametro)
            throws ServicioException {
        try {
            vacacionParametro.setVigente(false);
            vacacionParametroDao.actualizar(vacacionParametro);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los VacacionParametro por su nombre para verificacion de duplicacidad
     *
     * @param nombre nombre del regimen laboral
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionParametro> listarTodosVacacionParametroPorNombreDuplicado(final String nombre)
            throws ServicioException {
        try {
            List<VacacionParametro> lista;
            lista = vacacionParametroDao.buscarPorNombreDuplicados(nombre);
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los feriados por nombre
     *
     * @param nombre String descripcion del feriado
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionParametro> listarTodosVacacionParametroPorNombre(final String nombre)
            throws ServicioException {
        try {
            List<VacacionParametro> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = vacacionParametroDao.buscarVigente();
            } else {
                lista = vacacionParametroDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los VacacionParametro por su nombre para verificacion de duplicacidad
     *
     * @param idRegimen id del regimen laboral a buscar
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionParametro> listarTodosVacacionParametroPorRegimenLaboral(final Long idRegimen)
            throws ServicioException {
        try {
            return vacacionParametroDao.buscarPorRegimenLaboral(idRegimen);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * metodo para guardar un VacacionSolicitudHistorico.
     *
     * @param vs solicitud de vacacion
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarVacacionSolicitudHistorico(final VacacionSolicitud vs)
            throws ServicioException {
        try {
            VacacionSolicitudHistorico h = new VacacionSolicitudHistorico();
            /*
             * usuario y servidor
             */
            h.setVigente(Boolean.TRUE);
            if (vs.getUsuarioActualizacion() != null) {
                h.setUsuarioCreacion(vs.getUsuarioActualizacion());
            } else {
                h.setUsuarioCreacion(vs.getUsuarioCreacion());
            }
            h.setFechaCreacion(new Date());
            h.setApellidosNombresServidor(vs.getServidorInstitucion().getServidor().getApellidosNombres());
            h.setTipoIdentificacionServidor(vs.getServidorInstitucion().getServidor().getTipoIdentificacion());
            h.setNumeroIdentificacionServidor(vs.getServidorInstitucion().getServidor().getNumeroIdentificacion());
            h.setObservacion(vs.getObservacion());
            /**
             * validador.
             */
            if (vs.getEstado().equals(EstadoVacacionEnum.VALIDADO.getCodigo())) {
                h.setNumeroIdentificacionAprobador(vs.getValidador() != null ? vs.getValidador().getServidor().
                        getNumeroIdentificacion() : null);
                h.setApellidosNombresAprobador(vs.getValidador() != null ? vs.getValidador().getServidor().
                        getApellidosNombres() : null);
                h.setObservacion(vs.getMotivo() != null ? vs.getMotivo() : vs.getObservacion());
            }
            /**
             * aprobador.
             */
            if (vs.getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
                h.setNumeroIdentificacionAprobador(vs.getAprobador() != null ? vs.getAprobador().getServidor().
                        getNumeroIdentificacion() : null);
                h.setApellidosNombresAprobador(vs.getAprobador() != null ? vs.getAprobador().getServidor().
                        getApellidosNombres() : null);
                h.setObservacion(vs.getMotivo() != null ? vs.getMotivo() : vs.getObservacion());
            }
            /**
             * RECTIFICADOR.
             */
            if (vs.getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo()) && vs.getRecuperador() != null) {
                h.setNumeroIdentificacionAprobador(vs.getRecuperador() != null ? vs.getRecuperador().
                        getNumeroIdentificacion() : null);
                h.setApellidosNombresAprobador(vs.getRecuperador() != null ? vs.getRecuperador().getApellidosNombres()
                        : null);
                h.setObservacion(vs.getMotivo());
            }
            /**
             * solicitud de vacacion.
             */
            h.setTipo(vs.getTipo());
            h.setIdVacacionSolicitud(vs.getId());
            h.setSaldoVacacionesEfectiva(vs.getSaldoVacacionesEfectiva());
            h.setSaldoVacacionesProporcial(vs.getSaldoVacacionesProporcial());
            h.setTipoPeriodo(vs.getTipoPeriodo());
            h.setEstado(vs.getEstado());
            h.setFecha(vs.getFecha());
            h.setIdVacacionParametro(vs.getIdVacacionParametro());
            h.setTipoPeriodo(vs.getTipoPeriodo());
            h.setMinutosImputados(vs.getMinutosImputados());
            h.setDiasPlanificados(vs.getDiasPlanificados());
            /*
             * parametros
             */
            h.setIdRegimenLaboral(vs.getVacacionParametro().getIdRegimenLaboral());
            h.setJustificacion(vs.getVacacionParametro().getJustificacion());
            h.setMaximoAcumulacion(vs.getVacacionParametro().getMaximoAcumulacion());
            h.setMaximosDiasTomarPermisos(vs.getVacacionParametro().getMaximosDiasTomarPermisos());
            h.setMinimoDiasTomarVacacion(vs.getVacacionParametro().getMinimoDiasTomarVacacion());
            h.setMinimoMesesDerechoVacacion(vs.getVacacionParametro().getMinimoMesesDerechoVacacion());
            h.setTipoAcumulacion(vs.getVacacionParametro().getTipoAcumulacion());
            h.setValorIncremento(vs.getVacacionParametro().getValorIncremento());
            h.setNumeroDias(vs.getVacacionParametro().getNumeroDias());
            h.setPeriodoInicio(vs.getVacacionParametro().getPeriodoInicio());
            h.setPeriodoFin(vs.getVacacionParametro().getPeriodoFin());
            h.setValorIncremento(vs.getVacacionParametro().getValorIncremento());
            h.setTiempoProporcionalMensual(new BigDecimal(vs.getVacacionParametro().getNumeroDias()
                    / UtilFechas.MESES_EN_ANIO));

            h.setDiasImputables(BigDecimal.ZERO);
            if (vs.getVacacionParametro().getImputarFinSemanaVacacion()) {
                h.setDiasLabParaImputacion(BigDecimal.ONE);
            } else {
                h.setDiasLabParaImputacion(BigDecimal.ZERO);
            }

            vacacionSolicitudHistoricoDao.crear(h);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo para guardar el archivo de la foto
     *
     * @param vacacionSolicitud solicitud de vacaciones
     * @param archivoFoto archivo de foto
     * @throws ServicioException error al acceder al servicio
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardarArchivoVacacionSolicitud(VacacionSolicitud vacacionSolicitud, final File archivoFoto)
            throws ServicioException {
        try {

            //guarda archivo
            if (archivoFoto != null) {
                Archivo archivo = new Archivo();
                archivo.setArchivo(UtilArchivos.getBytesFromFile(archivoFoto));
                archivo.setDescripcion(UtilCadena.concatenar("SOLICITUD DE ", TipoVacacionEnum.obtenerNombre(
                        vacacionSolicitud.getTipo())) + " -" + UtilFechas.formatear(vacacionSolicitud.getFechaCreacion()));
                archivo.setNombre(vacacionSolicitud.getArchivo().getNombre());
                archivo.setVigente(Boolean.TRUE);
                archivo.setFechaCreacion(new Date());
                if (vacacionSolicitud.getUsuarioActualizacion() != null) {
                    archivo.setUsuarioCreacion(vacacionSolicitud.getUsuarioActualizacion());
                } else {
                    archivo.setUsuarioCreacion(vacacionSolicitud.getUsuarioCreacion());
                }
                Archivo crear = archivoDao.crear(archivo);
                archivoDao.flush();
                vacacionSolicitud.setArchivo(crear);
                vacacionSolicitudDao.actualizar(vacacionSolicitud);
            } else {
                vacacionSolicitud.setArchivo(null);
            }

        } catch (IOException e) {
            throw new ServicioException(e);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método procesa la eliminación logica de un vacacionSolicitud
     *
     * @param vacacionSolicitud registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarVacacionSolicitud(final VacacionSolicitud vacacionSolicitud)
            throws ServicioException {
        try {
            vacacionSolicitud.setVigente(false);
            vacacionSolicitudDao.actualizar(vacacionSolicitud);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un vacacionSolicitud
     *
     * @param vacacionSolicitud registro eliminar: Cambiar la vigencia
     * @param usuarioVO value objeto de los datos del usuario conectado
     * @return solicitud de vacacion
     * @throws ServicioException excepcion a nivel de servicio
     */
    public VacacionSolicitud revertirVacacionAprobada(
            final VacacionSolicitud vacacionSolicitud,
            final UsuarioVO usuarioVO) throws ServicioException {

        try {
            vacacionSolicitud.setUsuarioActualizacion(usuarioVO.getServidor().getNumeroIdentificacion());
            vacacionSolicitud.setFechaActualizacion(new Date());
            vacacionSolicitud.setEstado(EstadoVacacionEnum.REVERTIDO.getCodigo());
            vacacionSolicitudDao.actualizar(vacacionSolicitud);
            vacacionSolicitudDao.flush();

            guardarVacacionSolicitudHistorico(vacacionSolicitud);

            DistributivoDetalle dd = distributivoDetalleServicio.buscar(
                    vacacionSolicitud.getServidorInstitucion().getServidor().getId());
            Vacacion vacacion = vacacionDao.buscarPorServidor(vacacionSolicitud.getIdServidorInstitucion());
            VacacionDetalle vd = new VacacionDetalle();

            if (vacacionSolicitud.getTipo().equals(TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo())) {
                vd.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
            } else {
                vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            }

            vd.setVacacion(vacacion);
            vd.setVacacionSolicitud(vacacionSolicitud);
            vd.setObservacion("CRÉDITO POR REVERSIÓN DE SOLICITUD DE VACACIONES");
            vd.setVigente(Boolean.TRUE);
            vd.setCredito(vacacionSolicitud.getCantidadSolicitadaMinutos());
            vd.setDebito(0L);
            vd.setFechaInicio(new Date());
            vd.setFechaFin(new Date());
            vd.setEsAjusteManual(Boolean.FALSE);
            vd.setFechaCreacion(vacacionSolicitud.getFechaActualizacion());
            vd.setUsuarioCreacion(vacacionSolicitud.getUsuarioActualizacion());
            vacacionDetalleDao.crear(vd);
            vacacionDetalleDao.flush();
            vacacionDao.totalizarSaldosVacaciones(
                    vacacionSolicitud.getIdServidorInstitucion());

            TrayectoriaLaboral tl = new TrayectoriaLaboral();
            tl.setExplicacion("REVERSIÓN DE SOLICITUD DE VACACIONES");
            tl.setGrupo(GrupoEnum.VACACIONES.getCodigo());
            tl.setClase(GrupoEnum.VACACIONES.getCodigo());
            tl.setTipoMovimiento("REVERSIÓN DE SOLICITUD DE VACACIONES ID: " + vacacionSolicitud.getId());
            tl.setNumeroMovimiento("0000000000");
            tl.setNumeroDocumentoHabilitante("0000000000");

            Servidor s = vacacionSolicitud.getServidorInstitucion().getServidor();

            tl.setNombres(s.getNombres());
            tl.setApellidos(s.getApellidos());
            tl.setTipoIdentificacion(s.getTipoIdentificacion());
            tl.setNumeroIdentificacion(s.getNumeroIdentificacion());
            tl.setRmu(dd.getRmu());
            tl.setRmuSobrevalorado(dd.getRmuSobrevalorado());
            tl.setRegimenLaboral(dd
                    .getEscalaOcupacional().getNivelOcupacional()
                    .getRegimenLaboral().getNombre());
            tl.setGrado(dd.getEscalaOcupacional().getGrado());
            tl.setFechaDesde(UtilFechas.formatear(new Date()));
            tl.setFechaHasta(UtilFechas.formatear(new Date()));
            tl.setVigente(true);
            tl.setUsuarioCreacion(usuarioVO.getServidor().getNumeroIdentificacion());
            tl.setFechaCreacion(new Date());
            tl.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
            tl.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            for (CertificacionPresupuestaria cp : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                        dd.getDistributivo().getModalidadLaboral().getId())) {
                    tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                }
            }
            tl.setElaborador(vacacionSolicitud.getServidorInstitucion().getServidor().getApellidosNombres());
            tl.setFechaElaborador(vacacionSolicitud.getFecha());
            tl.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
            tl.setLegalizador(usuarioVO.getServidor().getApellidosNombres());
            tl.setFechaLegalizador(new Date());

            trayectoriaLaboralDao.crear(tl);
            trayectoriaLaboralDao.flush();

            return vacacionSolicitud;

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los VacacionSolicitud por estado
     *
     * @param estado estado de la solicitud de vacaciones
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarTodosVacacionSolicitudPorEstado(final String estado)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarTodosPorEstado(estado);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los VacacionSolicitud Por Servidor Tipo FechaInicio
     *
     * @param idServidor identificador del servidor
     * @param tipo tipo de la solicitud de vacaciones
     * @param fechaInicio fecha de iinicio
     * @param fechaFin fecha final
     * @param estado estado de la solicitud de vacaciones
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarTodosVacacionSolicitudPorServidorTipoFechaInicio(
            final Long idServidor, final String tipo, final Date fechaInicio, final Date fechaFin, final String estado)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarPorServidorTipoFechaInicioFin(idServidor, tipo, fechaInicio, fechaFin, estado);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los VacacionSolicitud Por Servidor Tipo FechaCreacion
     *
     * @param idServidor identificador del servidor
     * @param tipo tipo de solicitud de vacacion
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha final
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarTodosVacacionSolicitudPorServidorTipoFechaCreacion(
            final Long idServidor, final String tipo, final Date fechaInicio, final Date fechaFin)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarPorServidorTipoFechaCreacion(idServidor, tipo, fechaInicio, fechaFin);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los VacacionSolicitud por servidor
     *
     * @param idServidor identificador del servidor
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarTodosVacacionSolicitudPorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarTodosPorServidor(idServidor);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los VacacionSolicitud por servidor y estado de la solicitud
     *
     * @param idServidor identificador del servidor
     * @param estado estado de la solicitud de vacacion
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarTodosVacacionSolicitudPorServidorYEstado(
            final Long idServidor, String estado)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarTodosPorServidorYEstado(idServidor, estado);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los detalles de vacaciones periodicas por id de vacacion.
     *
     * @param idVacacion identificador de la vacacion
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionDetalle> listarVacacionPorVacacion(final Long idVacacion) throws ServicioException {
        try {
            return vacacionDetalleDao.buscarPorVacacion(idVacacion);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite guardaar el registro de saldos de Vacaciones
     *
     * @param vacacion registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarVacacion(final Vacacion vacacion) throws ServicioException {
        try {
            if (vacacion.getSaldoMesActual() == null) {
                vacacion.setSaldoMesActual(0l);
            }
            if (vacacion.getMesActual() == null) {
                vacacion.setMesActual(0);
            }
            if (vacacion.getSaldoPerdida() == null) {
                vacacion.setSaldoPerdida(0l);
            }
            vacacionDao.crear(vacacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite guardaar el registro Vacaciones con sus detalles.
     *
     * @param vacacion registro a crear en el sistema
     * @param detalles detalles de la vacaciones
     * @param esNuevo indica si el registro de vacacion es nuevo
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarVacacionYDetalles(
            final Vacacion vacacion,
            final List<VacacionDetalle> detalles,
            final boolean esNuevo) throws ServicioException {
        try {
            if (esNuevo) {
                vacacion.setSaldoMesActual(0l);
                vacacion.setMesActual(0);
                vacacion.setSaldoPerdida(0l);
                vacacionDao.crear(vacacion);
            } else {
                vacacionDao.actualizar(vacacion);
            }
            LOG.log(Level.INFO, "detalles de ajustes: {0}", detalles != null ? detalles.size() : null);
            if (detalles != null && !detalles.isEmpty()) {
                for (VacacionDetalle det : detalles) {
                    det.setVacacion(vacacion);
                    det.setTipo(TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo());
                    det.setEsAjusteManual(false);
                    vacacionDetalleDao.crear(det);
                    vacacionDetalleDao.flush();
                }
            }
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite guardar los registro de vacaciones detalles correspondientes a ajustes manuales de saldos Actualiza
     * saldos en Vacación.
     *
     * @param vacacion registro a crear en el sistema
     * @param detalles detalles de la vacacion
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAjusteSaldosVacacionYDetalles(final Vacacion vacacion, final List<VacacionDetalle> detalles)
            throws ServicioException {
        try {

            if (detalles != null && !detalles.isEmpty()) {
                for (VacacionDetalle det : detalles) {
                    det.setVacacion(vacacion);
                    vacacionDetalleDao.crear(det);
                    vacacionDetalleDao.flush();
                }
            }
            vacacionDao.totalizarSaldosVacaciones(vacacion.getServidorInstitucion().getId());
            //vacacionDao.actualizar(vacacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite actualizar el registro de saldos de Vacaciones
     *
     * @param vacacion registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarVacacion(final Vacacion vacacion) throws ServicioException {
        try {
            vacacionDao.actualizar(vacacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite guardar el registro de detalles de Vacaciones
     *
     * @param vacacion registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarVacacionDetalle(final VacacionDetalle vacacion) throws ServicioException {
        try {
            vacacionDetalleDao.crear(vacacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Envio mail notificando el resultado del proceso.
     *
     * @param vacacionSolicitud solicitud de vacacion
     * @throws java.lang.Exception error general
     */
    public void enviarMailAprobacionSolicitudVacacion(final VacacionSolicitud vacacionSolicitud)
            throws Exception {
        StringBuilder mensajeFinal = new StringBuilder();
        String destinatario = vacacionSolicitud.getServidorInstitucion().getServidor().getMail();
        if (destinatario != null && !destinatario.isEmpty()) {
            mensajeFinal.append("Estimada(o):\n\n");
            mensajeFinal.append("Se le informa que la Solicitud de Vacaciones ").append(
                    "que usted registró en el Sistema el ").append(new SimpleDateFormat(
                                    UtilFechas.FORMATO_FECHA_LARGO).format(vacacionSolicitud.getFechaCreacion())).
                    append(" se encuentra en estado ").
                    append(EstadoVacacionEnum.obtenerDescripcion(vacacionSolicitud.getEstado())).append("\n\n");
            mensajeFinal.append("Usted puede continuar con la revisión y verificación de la misma.\n\n\n\n");
            mensajeFinal.append("Saludos Cordiales.\n");
            mensajeriaServicio.enviarMail("ESTADO DE SOLICITUD DE "
                    + TipoVacacionEnum.obtenerNombre(vacacionSolicitud.getTipo()), null,
                    new String[]{destinatario}, null,
                    mensajeFinal.toString(), null, null);
        }
    }

    /**
     * Permite el registro de un registro de planificacionVacaccion Al trabajar en una proyeccion se trata de un
     * ejericion fiscal futuro por tanto, es necesario crear dicho registro en no vigente, y a guardar actualzarlo
     * temporalmente para poder obtener el numero de trámite.
     *
     * @param planificacionVacaccion registro a crear en el sistema
     * @param ins institucion ejercicio fiscal
     * @param detalles detalles de la planificacion de vacaciones
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarPlanificacionVacacion(final PlanificacionVacacion planificacionVacaccion,
            final InstitucionEjercicioFiscal ins, final List<PlanificacionVacacionDetalle> detalles) throws ServicioException {
        try {

            planificacionVacaccion.setNumero(
                    admServicio.generarNumeroTramite(ins, ins.getId()));

            planificacionVacacionDao.crear(planificacionVacaccion);
            planificacionVacacionDao.flush();
            for (PlanificacionVacacionDetalle det : detalles) {
                det.setUsuarioActualizacion(planificacionVacaccion.getUsuarioActualizacion());
                det.setFechaActualizacion(new Date());
                det.setVigente(Boolean.TRUE);
                det.setPlanificacionVacacionId(planificacionVacaccion.getId());
                planificacionVacacionDetalleDao.crear(det);
            }
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        } catch (ServicioException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Guarda la planificacion de vacaciones
     *
     * @param planificacionVacacion planifica de vacacion
     * @param planificacionVacacionDetalle detalle de la planificacion de vacacion
     * @throws ServicioException error en el servicio de vacacion
     */
    public void guardarPlanificacionVacacion(
            final PlanificacionVacacion planificacionVacacion,
            final PlanificacionVacacionDetalle planificacionVacacionDetalle) throws ServicioException {

        try {
            if (planificacionVacacion.getId() != null) {
                planificacionVacacionDetalle.setVigente(Boolean.TRUE);
                planificacionVacacionDao.actualizar(planificacionVacacion);
            } else {
                planificacionVacacionDao.crear(planificacionVacacion);
                planificacionVacacionDao.flush();
                planificacionVacacionDetalle.setPlanificacionVacacionId(planificacionVacacion.getId());
            }

            if (planificacionVacacionDetalle.getId() != null) {
                planificacionVacacionDetalle.setUsuarioActualizacion(planificacionVacacion.getUsuarioActualizacion());
                planificacionVacacionDetalle.setFechaActualizacion(new Date());
                planificacionVacacionDetalleDao.actualizar(planificacionVacacionDetalle);

            } else {
                planificacionVacacionDetalle.setVigente(Boolean.TRUE);
                planificacionVacacionDetalle.setPlanificacionVacacionId(planificacionVacacion.getId());
                planificacionVacacionDetalle.setUsuarioCreacion(planificacionVacacion.getUsuarioCreacion());
                planificacionVacacionDetalle.setFechaCreacion(planificacionVacacion.getFechaCreacion());
                planificacionVacacionDetalleDao.crear(planificacionVacacionDetalle);
            }

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar una planificacionVacaccion.
     *
     * @param planificacionVacaccion registro a actualizar
     * @param listaAgregados lista de planificaciones de vacaciones a agregar
     * @param listaEliminados lista de planificaciones de vacaciones a eliminar
     * @param obsReprog observaciones a la actualizacion
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarPlanificacionVacacion(final PlanificacionVacacion planificacionVacaccion,
            final List<PlanificacionVacacionDetalle> listaAgregados,
            List<PlanificacionVacacionDetalle> listaEliminados, final String obsReprog) throws ServicioException {
        try {
            for (PlanificacionVacacionDetalle det : listaAgregados) {
                if (det.getId() == null) {
                    det.setUsuarioCreacion(planificacionVacaccion.getUsuarioActualizacion());
                    det.setFechaCreacion(new Date());
                    det.setVigente(Boolean.TRUE);
                    det.setPlanificacionVacacionId(planificacionVacaccion.getId());
                    det.setObservacion(obsReprog);
                    planificacionVacacionDetalleDao.crear(det);
//                    planificacionVacacionDetalleDao.flush();
                }
            }
            for (PlanificacionVacacionDetalle det : listaEliminados) {
                det.setUsuarioActualizacion(planificacionVacaccion.getUsuarioActualizacion());
                det.setFechaActualizacion(new Date());
                det.setVigente(Boolean.FALSE);
                det.setObservacion(obsReprog);
                eliminarPlanificacionVacacionDetalle(det);
            }
            planificacionVacacionDao.actualizar(planificacionVacaccion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *
     *
     * @param planificacionVacaccion planificacion de la vacacion
     * @throws ServicioException error en el servicio
     */
    public void actualizarPlanificacionVacacion(final PlanificacionVacacion planificacionVacaccion) throws
            ServicioException {
        try {
            planificacionVacacionDao.actualizar(planificacionVacaccion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un registro planificacionVacaccion
     *
     * @param planificacionVacaccion registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarPlanificacionVacacion(final PlanificacionVacacion planificacionVacaccion)
            throws ServicioException {
        try {
            planificacionVacaccion.setVigente(false);
            planificacionVacacionDao.actualizar(planificacionVacaccion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un registro de planificacionVacaccionDetalle
     *
     * @param planificacionVacaccionDetalle registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarPlanificacionVacacionDetalle(final PlanificacionVacacionDetalle planificacionVacaccionDetalle)
            throws ServicioException {
        try {
            planificacionVacacionDetalleDao.crear(planificacionVacaccionDetalle);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite actualizacion de un registro de planificacionVacaccionDetalle
     *
     * @param planificacionVacaccionDetalle registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarPlanificacionVacacionDetalle(final PlanificacionVacacionDetalle planificacionVacaccionDetalle)
            throws ServicioException {
        try {
            planificacionVacacionDetalleDao.actualizar(planificacionVacaccionDetalle);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un registro planificacionVacaccionDetalle
     *
     * @param planificacionVacaccionDetalle registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarPlanificacionVacacionDetalle(final PlanificacionVacacionDetalle planificacionVacaccionDetalle)
            throws ServicioException {
        try {
            planificacionVacaccionDetalle.setVigente(false);
            planificacionVacacionDetalleDao.actualizar(planificacionVacaccionDetalle);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los detalles de la planificacion detalle por ejercicio fiscal y servidor
     *
     * @param idEjercioFiscal periodo fiscal
     * @param idServidor servidor
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacionDetalle> listarTodosPlanificacionVacacionDetallePorServidorYEjercicioFiscal(
            final Long idServidor, final Long idEjercioFiscal) throws ServicioException {
        try {
            return planificacionVacacionDetalleDao.buscarPorServidorYEjericicioFiscal(idServidor, idEjercioFiscal);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los detalles de la planificacion detalle por servidor y estado
     *
     * @param idServidor servidor
     * @param estado estado
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacionDetalle> listarTodosPlanificacionVacacionDetallePorServidorYEstado(
            final Long idServidor, final String estado) throws ServicioException {
        try {
            return planificacionVacacionDetalleDao.buscarPorServidorYEstado(idServidor, estado);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los detalles de la planificacion detalle por el id de la planificacion padre
     *
     * @param idPlanificacionVacacion identificador de la planificacion de vacacion
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacionDetalle> listarTodosPlanificacionVacacionDetallePorIdPlanificacionVacacion(
            final Long idPlanificacionVacacion) throws ServicioException {
        try {
            return planificacionVacacionDetalleDao.buscarPorIdPlanificacionVacacion(idPlanificacionVacacion);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar las planificaciones de vacaciones por ejercicio fiscal y servidor
     *
     * @param idInstitucionEjercioFiscal institucion ejercicio fiscal
     * @param idServidor servidor
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacion> listarTodosPlanificacionVacacionPorServidorYEjercicioFiscal(
            final Long idServidor, final Long idInstitucionEjercioFiscal) throws ServicioException {
        try {
            return planificacionVacacionDao
                    .buscarPorIdServidorInstitucionEjercicioFiscal(idServidor, idInstitucionEjercioFiscal);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar las planificaciones de vacaciones por ejercicio fiscal y estado
     *
     * @param estado estado de la planificacion de vacacion
     * @param idInstitucionEjercioFiscal institucion ejercicio fiscal
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacion> listarTodosPlanificacionVacacionPorEjercicioFiscalYEstado(
            final String estado, final Long idInstitucionEjercioFiscal) throws ServicioException {
        try {
            return planificacionVacacionDao
                    .buscarPorInstitucionEjercicioFiscalYEstado(estado, idInstitucionEjercioFiscal);

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de PlanificacionVacacion vigentes por Id de servidor y id de
     * institucion y estado
     *
     * @param idServidorJefe identificador del servidor jefe
     * @param idInstitucion identificador de la institucion
     * @param estado eatado de la planificacion de vacacion
     * @param idEjercicioFiscal identificador del ejercicio fiscal
     * @param idUnidadOrganizacional identificador de la unidad organizacional
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionVacacionVO> listarTodosPlanificacionVacacionPorValidarAprobar(final Long idServidorJefe,
            final String estado, final Long idInstitucion, final Long idEjercicioFiscal, final Long idUnidadOrganizacional)
            throws ServicioException {
        try {
            List<PlanificacionVacacion> lista1;
            List<PlanificacionVacacionVO> listaVacacion = new ArrayList<>();

            lista1 = planificacionVacacionDao.buscarListaValidacionAprobacion(idServidorJefe,
                    estado, idInstitucion, idEjercicioFiscal, idUnidadOrganizacional);

            for (PlanificacionVacacion p : lista1) {
//                if (!p.getServidorId().equals(idServidorUsuaio)) {

                PlanificacionVacacionVO vo = new PlanificacionVacacionVO();
                vo.setPlanificacionVacacion(p);
                List<DistributivoDetalle> dist = admServicio.listarTodosDistributivoDetallePorServidor(
                        p.getServidorId());
                for (DistributivoDetalle dd : dist) {
                    vo.setDistributivoDetalle(dd);
                    break;
                }
                List<ServidorInstitucion> lsi = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(
                        p.getServidor().getNumeroIdentificacion());
                for (ServidorInstitucion si : lsi) {
                    vo.setFechaIngreso(si.getFechaIngreso());
                    break;
                }
                listaVacacion.add(vo);
            }
//            }
            return listaVacacion;

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite buscar el ultimo registro procesado de vacaciones.
     *
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionProceso> listarVacacionProcesoUltimaFecha()
            throws ServicioException {
        try {
            return vacacionProcesoDao.buscarUltimaFechaProcesada();
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de solicitudes en estado registrado y validado por servidor.
     *
     * @param idServidor identificador del servidor
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarVacacionSolicitudEnTramitePorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarTodosPorServidorEnTramite(idServidor);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de solicitudes de vacaciones y anticipos de vacaciones no negadas por fecha.
     *
     * @param idServidor identificador del servidor
     * @param fechaInicio fecha de inicio
     * @param fechafin fecha final
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<VacacionSolicitud> listarVacacionSolicitudPorServidorEnTramitePorFecha(
            final Long idServidor,
            final Date fechaInicio,
            final Date fechafin) throws ServicioException {
        try {
            return vacacionSolicitudDao.buscarVacacionesPorServidorEnTramitePorFecha(
                    idServidor, fechaInicio, fechafin);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de una VacacionProceso (fecha del proceso)
     *
     * @param vProceso registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarVacacionProceso(final VacacionProceso vProceso) throws ServicioException {
        try {
            vacacionProcesoDao.crear(vProceso);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Verifica si es una fecha laborable.
     *
     * @param fecha fecha
     * @param idRegimen identificador delregimen laboral
     * @return indica =si la fecha es laborable.
     */
    public Boolean esFechaLaborable(final Date fecha, final Long idRegimen) {
        Boolean laborable = Boolean.FALSE;
        Calendar c = Calendar.getInstance();
        try {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    | c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                return laborable;
            }

            if (feriadoDao.buscarPorFecha(fecha, idRegimen).isEmpty()) {
                return laborable;
            }
            laborable = Boolean.TRUE;
        } catch (DaoException e) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al determinar dias laborables", e));
        }
        return laborable;
    }

    /**
     * Crea una solicitud de vacacion de permiso para justificar un atraso o falta.
     *
     * @param j justificacion de la asistencia
     * @return solicitud de vacacion
     * @throws ServicioException error en el servicio
     * @throws ServidorException error en el servidor
     */
    public VacacionSolicitud crearSolicitudVacacion(final JustificacionAsistencia j) throws ServicioException,
            ServidorException {
        VacacionSolicitud v = new VacacionSolicitud();
        v.setVigente(Boolean.TRUE);
        v.setUsuarioCreacion(j.getUsuarioCreacion());
        v.setFechaCreacion(new Date());
        v.setEstado(EstadoVacacionEnum.APROBADO.getCodigo());
        v.setTipo(TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo());
        v.setMinutosImputados(BigDecimal.ZERO);
        v.setObservacion("JUSTIF. " + TipoAusentismoEnum.obtenerDescripcion(j.getAtraso().getTipo()) + ":"
                + j.getObservacion());
//        v.setFechaInicio(j.getFecha());
//        v.setFechaFin(j.getFecha());
//        v.setCantidadSolicitada(j.getCantidadTiempo());

        if (j.getAtraso().getTipo().equals(TipoAusentismoEnum.ATRASO.getCodigo())) {
            v.setTipoPeriodo(PeriodoVacacionEnum.MINUTOS.getCodigo());
        } else {
            v.setTipoPeriodo(PeriodoVacacionEnum.DIAS.getCodigo());
        }
        long saldo = 0;
        if (saldo > 0 && saldo >= j.getCantidadTiempo()) {
            v.setSaldoVacacionesEfectiva(saldo);
            List<ServidorInstitucion> lista
                    = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(
                            j.getAtraso().getServidor().getNumeroIdentificacion());
            if (!lista.isEmpty()) {
                v.setServidorInstitucion(lista.get(0));
                v.setIdServidorInstitucion(lista.get(0).getId());

                List<ServidorInstitucion> listaJustificador
                        = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(
                                j.getServidorAprobador().getNumeroIdentificacion());
                if (!listaJustificador.isEmpty()) {
                    v.setAprobador(listaJustificador.get(0));
                    v.setIdAprobador(listaJustificador.get(0).getId());
                    DistributivoDetalle dd = distributivoDetalleServicio.buscar(
                            j.getAtraso().getServidor().getId());

                    List<VacacionParametro> listaParametros = listarTodosVacacionParametroPorRegimenLaboral(
                            dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId());
                    if (!listaParametros.isEmpty()) {
                        v.setIdVacacionParametro(listaParametros.get(0).getId());
                        v.setVacacionParametro(listaParametros.get(0));
                        //guardarVacacionSolicitud(v, null);
                        //System.out.println(" solicitud creada: " + v.getId());
                    } else {
                        throw new ServicioException("El servidor no tiene configuracion de Régimen Laboral o"
                                + " no existe la parametrización de vacaciones para este Régimen Laboral");
                    }

                } else {
                    throw new ServicioException("El servidor aprobador no se encuentra configurado dentro de"
                            + " la institucion");
                }

            } else {
                throw new ServicioException("El servidor no se encuentra configurado dentro de la institucion");
            }
        } else {
            throw new ServicioException("El saldo de vacaciones no es suficiente para realizar esta operación.");
        }
        return v;
    }

    /**
     * Permite verificar si el servidor esta o no de vacaciones.
     *
     * @param s datos del servidor
     * @param fechaProceso fecha del proceso
     * @return resultado
     */
    public long esServidorEnVacaciones(final Servidor s, final Date fechaProceso) {
        long dias = 0l;
        try {
            List<VacacionSolicitud> lista = vacacionSolicitudDao.buscarPorServidorTipoFechaInicioFin(s.getId(), null,
                    fechaProceso, null, null);
//            if (!lista.isEmpty()) {
//                for (VacacionSolicitud r : lista) {
//                    if (r.getTipo().equals(TipoVacacionEnum.PERMISO.getCodigo())) {
//                        switch (r.getTipoPeriodo().charAt(0)) {
//                            case 'D':
//                                dias = dias + r.getCantidadSolicitada();
//                                break;
//                            case 'H':
//                                dias = dias + (r.getCantidadSolicitada() / (long) s.getJornada());
//                                break;
//                            case 'M':
//                                dias = dias + (r.getCantidadSolicitada() / (long) (s.getJornada() * UtilFechas.MIN_EN_HORA));
//                                break;
//                        }
//
//                    } else {
//                        dias = dias + r.getCantidadSolicitada();
//
//                    }
//                }
//            }
        } catch (DaoException ex) {
            Logger.getLogger(AusentismoTemporizador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return dias;
    }

    /**
     *
     * @param lista lista de servidores en la institucion
     * @param vp parametros de la vacacion
     * @param fechaProceso fecha del proceso
     * @param ief institucion ejercicio fiscal
     * @return lista
     * @throws ServicioException error en el servicio de vacacion
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<String> ejecutarAcreditacionPorAniversario(final List<DistributivoDetalle> lista,
            final VacacionParametro vp, final Date fechaProceso, final InstitucionEjercicioFiscal ief)
            throws ServicioException {
        List<String> errores = new ArrayList<>();
        try {

            for (DistributivoDetalle dd : lista) {
                Date fechaIngreso = dd.getServidor().getFechaIngresoInstitucion();
                if (UtilFechas.obtenerMes(fechaIngreso).compareTo(UtilFechas.obtenerMes(fechaProceso)) == 0
                        && UtilFechas.obtenerDiaDelMes(fechaProceso).compareTo(UtilFechas.obtenerDiaDelMes(
                                        fechaIngreso)) == 0) {
                    acreditarVacacionAniversarioAnual(dd, fechaProceso);
                    descontarSobrelimitado(vp, dd);
                }
            }
            return errores;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param vp
     * @param dd
     */
    private void descontarSobrelimitado(final VacacionParametro vp, final DistributivoDetalle dd) throws DaoException {
        int maximo = 0;
        if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.DIAS.getCodigo())) {
            maximo = vp.getMaximoAcumulacion() * 8 * 60;
        } else if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.PERIODOS.getCodigo())) {
            maximo = vp.getMaximoAcumulacion() * (vp.getDiasEnPeriodo() == null ? 0 : vp.getDiasEnPeriodo()) * 8 * 60;
        }
        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(dd.getDistributivo().
                getInstitucionEjercicioFiscal().getInstitucion().getId(), dd.getServidor().getId());
        vacacionDao.totalizarSaldosVacaciones(si.getId());
        Vacacion vacacion = vacacionDao.buscarPorServidorInstitucion(dd.getServidor().getId(),
                dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
        vacacionDao.refresh(vacacion);
        if (vacacion.getSaldo() > maximo) {
            VacacionDetalle vd = new VacacionDetalle();
            vd.setCredito(0l);
            vd.setDebito(vacacion.getSaldo() - maximo);
            vd.setFechaCreacion(new Date());
            vd.setFechaInicio(new Date());
            vd.setFechaFin(new Date());
            vd.setObservacion("DESCUENTO DE VACACIONES POR SOBREPASAR EL LIMITE MAXIMO PERMITIDO");
            vd.setUsuarioCreacion("auto");
            vd.setVacacion(vacacion);
            vd.setVigente(Boolean.TRUE);
            vd.setEsAjusteManual(Boolean.FALSE);
            vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            if (vd.getDebito().compareTo(0l) > 0) {
                vacacionDetalleDao.crear(vd);
                VacacionDetalle vdPerdida = new VacacionDetalle();
                vdPerdida.setDebito(0l);
                vdPerdida.setCredito(vd.getDebito());
                vdPerdida.setFechaCreacion(new Date());
                vdPerdida.setFechaInicio(new Date());
                vdPerdida.setFechaFin(new Date());
                vdPerdida.setObservacion("PERDIDA DE VACACIONES POR SOBREPASAR EL LIMITE MAXIMO PERMITIDO");
                vdPerdida.setUsuarioCreacion("auto");
                vdPerdida.setVacacion(vacacion);
                vdPerdida.setVigente(Boolean.TRUE);
                vdPerdida.setEsAjusteManual(Boolean.FALSE);
                vdPerdida.setTipo(TipoVacacionDetalleEnum.PERDIDA.getCodigo());
                vacacionDetalleDao.crear(vd);
                vacacionDao.totalizarSaldosVacaciones(si.getId());
            }
        }
    }

    /**
     *
     * @param lista lista de servidores de la institucion
     * @throws ServicioException error en el servicio de vacacion
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void verificarVacaciones(final List<DistributivoDetalle> lista) throws ServicioException {
        try {
            for (DistributivoDetalle dd : lista) {
                Vacacion vacacion = vacacionDao.buscarPorServidorInstitucion(dd.getServidor().getId(),
                        dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
                if (vacacion == null) {
                    ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                            dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId(),
                            dd.getServidor().getId());
                    if (si == null) {
                        si = new ServidorInstitucion();
                        si.setFechaIngreso(dd.getServidor().getFechaIngresoInstitucion());
                        si.setIdInstitucion(dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
                        si.setIdServidor(dd.getServidor().getId());
                        si.setFechaCreacion(new Date());
                        si.setUsuarioCreacion("auto");
                        si.setVigente(Boolean.TRUE);
                        servidorInstitucionDao.crear(si);
                        servidorInstitucionDao.flush();
                    }
                    vacacion = new Vacacion();
                    vacacion.setFechaCreacion(new Date());
                    vacacion.setSaldo(0l);
                    vacacion.setSaldoProporcional(0l);
                    vacacion.setSaldoPerdida(0l);
                    vacacion.setServidorInstitucion(si);
                    vacacion.setUsuarioCreacion("auto");
                    vacacion.setVigente(Boolean.TRUE);
                    vacacion.setSaldoMesActual(0l);
                    vacacion.setMesActual(0);
                    vacacionDao.crear(vacacion);
                    vacacionDao.flush();
                }
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param puestos lista de servidores de la institucion
     * @param vp parametro de vacacion
     * @param fechaProceso fecha del proceso
     * @param ief institucion ejercicio fiscal
     * @return lista de resultados
     * @throws ServicioException error en el servicio de vacacion
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<String> ejecutarAcreditacionDiaria(final List<DistributivoDetalle> puestos, final VacacionParametro vp,
            final Date fechaProceso, int mesActual) throws ServicioException {
        List<String> errores = new ArrayList<>();
        try {
            for (DistributivoDetalle dd : puestos) {
//                System.out.println("ejecutarAcreditacionMensual:" + si.getId());
                // Recupera registro de vacacion del servidor.
                Vacacion vacacion = vacacionDao.buscarPorServidorInstitucion(dd.getServidor().getId(),
                        dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
                // Calcular el aniversario del servidor.
                if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.DIAS.getCodigo())) {
                    // Acredita valor de vacacion diario.
                    acreditarVacacionDiaria(vacacion, vp, fechaProceso, mesActual);
                } else if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.PERIODOS.getCodigo())) {
                    // Acredito valor de vacacion periodo.
                    acreditarVacacionPeriodo(vacacion, vp, fechaProceso, mesActual);
                }
            }
            //System.out.println("FIN BLOQUE 2:" + bloque + ":" + lista.size());
            return errores;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param dd servicio en la institucion
     * @param fechaProceso fecha de proceso
     * @throws DaoException error al acceder a datos
     */
    private void acreditarVacacionAniversarioAnual(final DistributivoDetalle dd, Date fechaProceso) throws DaoException {
        Vacacion v = vacacionDao.buscarPorServidorInstitucion(dd.getServidor().getId(),
                dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
        // Todas los proporcionales se pasan a vacaciones.
        vacacionDetalleDao.actualizarTipo(TipoVacacionDetalleEnum.VACACION.getCodigo(),
                TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo(), v.getId());
    }

    /**
     * Acredita valor de vacacion mensual.
     *
     * @param vacacion vacacion de un servidor
     * @param vp parametro de la vacaciones
     * @param fechaProceso fecha del proceso
     * @throws DaoException error al acceder a datos
     */
    private void acreditarVacacionDiaria(final Vacacion vacacion, VacacionParametro vp, Date fechaProceso,
            int mesActual) throws DaoException {
        if (vacacion.getMesActual().compareTo(mesActual) != 0) {
            // encerar el mes actual
            vacacion.setMesActual(mesActual);
            vacacion.setSaldoMesActual(0l);
        }
        VacacionDetalle vd = new VacacionDetalle();
        int jornada = vacacion.getServidorInstitucion().getServidor().getJornada() == null ? 8
                : vacacion.getServidorInstitucion().getServidor().getJornada();
        long minutos = vp.getNumeroDias() * jornada * 60 / 360;
        if ((vacacion.getSaldoMesActual() + minutos) <= (vp.getNumeroDias() * 8 * 60 / 12)) {
            Calendar f = Calendar.getInstance();
            f.setTime(fechaProceso);
            if (f.getActualMaximum(Calendar.DAY_OF_MONTH) == f.get(Calendar.DAY_OF_MONTH)) {
                // es fin de mes, ajuste el valor mensual a 2.5
                minutos = minutos + (vp.getNumeroDias() * 8 * 60 / 12) - (vacacion.getSaldoMesActual() + minutos);
            }
            vd.setCredito(minutos);
            vd.setDebito(0l);
            vd.setFechaCreacion(new Date());
            vd.setFechaInicio(fechaProceso);
            vd.setFechaFin(fechaProceso);
            vd.setObservacion(UtilCadena.concatenar("CRÉDITO DIARIO DE VACACIONES CORRESPONDIENTE A ",
                    UtilFechas.formatear(fechaProceso), " - ", vp.getRegimenLaboral().getNombre()));
            vd.setUsuarioCreacion("AUTO");
            vd.setVacacion(vacacion);
            vd.setVigente(Boolean.TRUE);
            vd.setEsAjusteManual(Boolean.FALSE);
            vd.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
            vacacionDetalleDao.crear(vd);
            vacacion.setMesActual(mesActual);
            vacacion.setSaldoMesActual(vacacion.getSaldoMesActual() + minutos);
            vacacionDao.actualizar(vacacion);
            vacacionDetalleDao.flush();
        }
    }

    /**
     * Acredita valor de vacacion mensual.
     *
     * @param vacacion vacacion del servidor
     * @param vp parametro de la vacacion
     * @param fechaProceso fecha del proceso
     * @throws DaoException error al acceder a datos
     */
    private void acreditarVacacionPeriodo(final Vacacion vacacion, VacacionParametro vp, Date fechaProceso,
            int mesActual) throws DaoException {
        if (vacacion.getMesActual().compareTo(mesActual) != 0) {
            // encerar el mes actual
            vacacion.setMesActual(mesActual);
            vacacion.setSaldoMesActual(0l);
        }
        int jornada = vacacion.getServidorInstitucion().getServidor().getJornada() == null ? 8
                : vacacion.getServidorInstitucion().getServidor().getJornada();

        // calcular los dias adicionales de acuerdo a los anios laborados.
        Integer[] duracion = UtilFechas.calcularAniosMesesDiasEntreFechas(vacacion.getServidorInstitucion().
                getServidor().getFechaIngresoInstitucion(), fechaProceso);
        int adicional = 0;
        if (duracion[0] >= vp.getPeriodoInicio() && duracion[0] <= vp.getPeriodoFin()) {
            adicional = (duracion[0] - vp.getPeriodoInicio() + vp.getValorIncremento()) * jornada * 60 / 360;
        } else if (duracion[0] > vp.getPeriodoFin()) {
            adicional = (vp.getPeriodoFin() - vp.getPeriodoInicio() + vp.getValorIncremento()) * jornada * 60 / 360;
        }

        VacacionDetalle vd = new VacacionDetalle();
        long minutos = (vp.getNumeroDias()) * jornada * 60 / 360;
        if ((vacacion.getSaldoMesActual() + minutos + adicional) <= ((vp.getNumeroDias() + adicional) * 8 * 60 / 12)) {
            Calendar f = Calendar.getInstance();
            f.setTime(fechaProceso);
            if (f.getActualMaximum(Calendar.DAY_OF_MONTH) == f.get(Calendar.DAY_OF_MONTH)) {
                // es fin de mes, ajuste el valor mensual a 2.5
                minutos = minutos + adicional + ((vp.getNumeroDias() + adicional) * 8 * 60 / 12)
                        - (vacacion.getSaldoMesActual() + minutos + adicional);
            } else {
                minutos = minutos + adicional;
            }
            vd.setCredito(minutos);
            vd.setDebito(0l);
            vd.setFechaCreacion(new Date());
            vd.setFechaInicio(fechaProceso);
            vd.setFechaFin(fechaProceso);
            vd.setObservacion(UtilCadena.concatenar("CRÉDITO DIARIO DE VACACIONES CORRESPONDIENTE A ",
                    UtilFechas.formatear(fechaProceso), " - ", vp.getRegimenLaboral().getNombre()));
            vd.setUsuarioCreacion("AUTO");
            vd.setVacacion(vacacion);
            vd.setVigente(Boolean.TRUE);
            vd.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
            vd.setEsAjusteManual(Boolean.FALSE);
            vacacionDetalleDao.crear(vd);
            vacacion.setMesActual(mesActual);
            vacacion.setSaldoMesActual(vacacion.getSaldoMesActual() + minutos);
            vacacionDao.actualizar(vacacion);

            vacacionDetalleDao.flush();

        }
    }

    /**
     *
     * @param vp parametro de la vacacion
     * @throws ServicioException error en el servicio de vacacion
     */
    public void descontarSobrelimitado(VacacionParametro vp) throws ServicioException {
        try {
            int jornada;
            int maximo = 0;
            if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.DIAS.getCodigo())) {
                maximo = vp.getMaximoAcumulacion() * 8 * 60;
            } else if (vp.getTipoAcumulacion().equals(TipoAcumulacionEnum.PERIODOS.getCodigo())) {
                maximo = vp.getMaximoAcumulacion() * (vp.getDiasEnPeriodo() == null ? 0 : vp.getDiasEnPeriodo())
                        * 8 * 60;
            }
            List<Vacacion> vacaciones
                    = vacacionDao.buscarPorServidorSobrelimitado(vp.getIdRegimenLaboral(), maximo);
            for (Vacacion v : vacaciones) {
                jornada = v.getServidorInstitucion().getServidor().getJornada() == null ? 8 : v.getServidorInstitucion()
                        .getServidor().getJornada();
                VacacionDetalle vd = new VacacionDetalle();
                vd.setCredito(0l);
                vd.setDebito(v.getSaldo() - (maximo * jornada * 60));
                vd.setFechaCreacion(new Date());
                vd.setFechaInicio(new Date());
                vd.setFechaFin(new Date());
                vd.setObservacion("DESCUENTO DE VACACIONES POR SOBREPASAR EL LIMITE MAXIMO PERMITIDO");
                vd.setUsuarioCreacion("AUTO");
                vd.setVacacion(v);
                vd.setVigente(Boolean.TRUE);
                vd.setEsAjusteManual(Boolean.FALSE);
                vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
                if (vd.getDebito().compareTo(0l) > 0) {
                    vacacionDetalleDao.crear(vd);
                }

                VacacionDetalle vdPerdida = new VacacionDetalle();
                vdPerdida.setDebito(0l);
                vdPerdida.setCredito(v.getSaldo() - (vp.getMaximoAcumulacion() * jornada * 60));
                vdPerdida.setFechaCreacion(new Date());
                vdPerdida.setFechaInicio(new Date());
                vdPerdida.setFechaFin(new Date());
                vdPerdida.setObservacion("PERIDA DE VACACIONES POR SOBREPASAR EL LIMITE MAXIMO PERMITIDO");
                vdPerdida.setUsuarioCreacion("AUTO");
                vdPerdida.setVacacion(v);
                vdPerdida.setVigente(Boolean.TRUE);
                vdPerdida.setEsAjusteManual(Boolean.FALSE);
                vdPerdida.setTipo(TipoVacacionDetalleEnum.PERDIDA.getCodigo());
                if (vd.getDebito().compareTo(0l) > 0) {
                    vacacionDetalleDao.crear(vd);
                }

            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Registra en la base de datos la fecha del proceso que si presentó registros de vacaciones.
     *
     * @param vacacionProceso proceso de vacacion
     * @param esNuevo indicador si es nuevo
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void guardarFechaProceso(VacacionProceso vacacionProceso, boolean esNuevo) {
        try {
            if (esNuevo) {
                vacacionProceso.setUsuarioCreacion("Automático");
                vacacionProceso.setFechaCreacion(new Date());
                vacacionProceso.setVigente(Boolean.TRUE);
                vacacionProcesoDao.crear(vacacionProceso);
            } else {
                vacacionProcesoDao.actualizar(vacacionProceso);
            }
        } catch (DaoException e) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al guardar la fecha del proceso", e));
        }
    }

    /**
     * Este método permite actualizar un vacacionSolicitud
     *
     * @param vacacionSolicitud registro a actualizar
     * @param numeroAccionPersonal
     * @param usuarioConectado datoa del usuario conectado
     * @throws ServicioException excepcion a nivel de servicio
     * @throws ec.com.atikasoft.proteus.excepciones.ServidorException error en el servidor
     */
    public void actualizarVacacionSolicitud(final VacacionSolicitud vacacionSolicitud,
            final String numeroAccionPersonal, final UsuarioVO usuarioConectado)
            throws ServicioException, ServidorException {
        try {
            vacacionSolicitudDao.actualizar(vacacionSolicitud);
            vacacionSolicitudDao.flush();

            guardarVacacionSolicitudHistorico(vacacionSolicitud);

            if (vacacionSolicitud.getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
                guardarSaldoVacacion(vacacionSolicitud, numeroAccionPersonal, usuarioConectado);

            } else if (vacacionSolicitud.getTipo().equals(TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo())) {
                BusquedaVacacionVO vo = new BusquedaVacacionVO();
                vo.setNumeroDocumentoServidor(vacacionSolicitud.getServidorInstitucion().getServidor().getNumeroIdentificacion());
                vo.setTipoDocumentoServidor(vacacionSolicitud.getServidorInstitucion().getServidor().getTipoIdentificacion());
                vo.setEstadoPlanificacionId(EstadoVacacionDetalleEnum.NO_DISPONIBLE.getCodigo());
                vo.setRegimenLaboralId(vacacionSolicitud.getVacacionParametro().getIdRegimenLaboral());
                List<PlanificacionVacacionDetalle> lista = planificacionVacacionDetalleDao.buscarVaciones(vo);
                if (!lista.isEmpty()) {
                    PlanificacionVacacionDetalle pd = lista.get(0);
                    pd.setUsuarioActualizacion(vacacionSolicitud.getUsuarioActualizacion());
                    pd.setFechaActualizacion(new Date());
                    pd.setEstado(EstadoVacacionDetalleEnum.DISPONIBLE.getCodigo());
                    planificacionVacacionDetalleDao.actualizar(pd);
                }
            }

        } catch (DaoException | ServicioException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Genera pdf de accion de personal
     *
     * @param vacacionSolicitud Solicitud de vacaciones
     * @param usuario datos del usuario conectado
     * @return
     * @throws ServicioException
     */
    public String generarPdfAccionPersonalSolicitudVacacion(VacacionSolicitud vacacionSolicitud,
            final String representanteRRHH, final String nombreRepresentanteRRHH, final String autoridadNominadora,
            final String nombreAutoridadNominadora, UsuarioVO usuario) throws ServicioException {

        try {
            String numeroAccion = administracionServicio.generarNumeroAccionPersonalVacaciones(
                    vacacionSolicitud.getServidorInstitucion().getIdInstitucion());
            Archivo archivo = new Archivo();
            archivo.setUsuarioCreacion(vacacionSolicitud.getUsuarioActualizacion());
            archivo.setFechaCreacion(vacacionSolicitud.getFechaActualizacion());
            archivo.setVigente(Boolean.TRUE);

            ParametroInstitucional pi = parametroInstitucionalDao
                    .buscarPorNemonico(ParametroInstitucionCatalogoEnum.LOGO_DE_INSTITUCION.getCodigo());

            GenerarAccionPersonalSolicitudVacacion gapv = new GenerarAccionPersonalSolicitudVacacion(
                    vacacionSolicitud, usuario.getServidor().getApellidosNombres(),
                    numeroAccion, this, representanteRRHH, nombreRepresentanteRRHH, autoridadNominadora,
                    nombreAutoridadNominadora, pi.getArchivo().getArchivo());

            File pdf = gapv.generar();
            ParametroGlobal repos = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.REPOS.getCodigo());
            File marcaAgua = new File(repos.getValorTexto() + File.separator + "reportes" + File.separator + "img"
                    + File.separator + "marca_agua.png");
            File pdfMarcaAgua = MarcaAgua.procesar(pdf, marcaAgua);

            archivo.setArchivo(IOUtils.toByteArray(new FileInputStream(pdfMarcaAgua)));
            archivo.setDescripcion("ARCHIVO DE ACCIÓN DE PERSONAL POR SOLICITUD DE VACACIÓN");
            archivo.setNombre(pdfMarcaAgua.getName());
            archivo.setPalabrasClave("accion personal");
            archivoDao.crear(archivo);
            archivoDao.flush();
            vacacionSolicitud.setArchivo(archivo);
            vacacionSolicitud.setIdArchivoAccionPersonal(archivo.getId());
            vacacionSolicitud.setArchivoAccionPersonal(archivo);
            vacacionSolicitudDao.actualizar(vacacionSolicitud);
            vacacionSolicitudDao.flush();
            return numeroAccion;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException("Error generando archivo acción de personal por solicitud de vacación.", e);
        }

    }

    /**
     *
     * @param s
     * @param dd
     * @param v
     * @param siServidor
     * @param siServElaborador
     * @param explicacion
     * @param codigoDocPrevio
     * @param valorDocPrevio
     * @param fechaDocPrevio
     * @param usuario
     * @throws ServicioException
     */
    public void generarPdfAccionPersonalLiquidacionVacacion(
            final Servidor s,
            final DistributivoDetalle dd,
            final Vacacion v,
            final Long siServidor,
            final Long siServElaborador,
            final String explicacion,
            final String codigoDocPrevio,
            final String valorDocPrevio,
            final Date fechaDocPrevio,
            final String representanteRRHH,
            final String nombreRepresentanteRRHH,
            final String autoridadNominadora,
            final String nombreAutoridadNominadora,
            UsuarioVO usuario) throws ServicioException {

        try {
            List<DistributivoDetalleHistorico> lddh = distributivoDetalleHistoricoDao.buscarPorIdentificacionYEstado(
                    s.getTipoIdentificacion(), s.getNumeroIdentificacion(), EstadoPuestoEnum.OCUPADO.getCodigo());
            if (!lddh.isEmpty()) {
                DistributivoDetalleHistorico ddh = lddh.get(0);
                dd.setServidor(s);
                String numeroAccion = administracionServicio.generarNumeroAccionPersonalLiquidacionVacaciones(
                        v.getServidorInstitucion().getIdInstitucion());

                ParametroInstitucional pi = parametroInstitucionalDao
                        .buscarPorNemonico(ParametroInstitucionCatalogoEnum.LOGO_DE_INSTITUCION.getCodigo());

                GenerarAccionPersonalLiquidacionVacacion gapv = new GenerarAccionPersonalLiquidacionVacacion(
                        v, dd, usuario.getServidor().getApellidosNombres(), numeroAccion, explicacion,
                        codigoDocPrevio, valorDocPrevio, fechaDocPrevio, representanteRRHH, nombreRepresentanteRRHH,
                        autoridadNominadora, nombreAutoridadNominadora, pi.getArchivo().getArchivo());

                File pdf = gapv.generar();
                ParametroGlobal repos = parametroGlobalDao.buscarPorNemonico(ParametroGlobalEnum.REPOS.getCodigo());
                File marcaAgua = new File(repos.getValorTexto() + File.separator + "reportes" + File.separator + "img"
                        + File.separator + "marca_agua.png");
                File pdfMarcaAgua = MarcaAgua.procesar(pdf, marcaAgua);

                Archivo a = new Archivo();
                a.setUsuarioCreacion(usuario.getUsuario());
                a.setFechaCreacion(new Date());
                a.setVigente(Boolean.TRUE);
                a.setDescripcion("ARCHIVO DE ACCIÓN DE PERSONAL POR LIQUIDACION DE VACACIONES");
                a.setNombre(pdfMarcaAgua.getName());
                a.setPalabrasClave("liquidación");
                a.setArchivo(UtilArchivos.getBytesFromFile(pdfMarcaAgua));
                archivoDao.crear(a);
                archivoDao.flush();

                VacacionSolicitudLiquidacion vsl = new VacacionSolicitudLiquidacion();
                vsl.setEstado(EstadoLiquidacionVacionEnum.REGISTRADO.getCodigo());
                vsl.setUsuarioCreacion(usuario.getUsuario());
                vsl.setFechaCreacion(new Date());
                vsl.setVigente(Boolean.TRUE);

                vsl.setIdServidorInstitucion(siServidor);
                vsl.setIdServidorInstitucionElaborador(siServElaborador);

                vsl.setExplicacionLiquidacionVacacion(explicacion);
                vsl.setCodigoDocumentoPrevio(codigoDocPrevio);
                vsl.setNumeroDocumentoPrevio(valorDocPrevio);
                vsl.setNumeroAccionPersonal(numeroAccion);
                vsl.setFechaDocumentoPrevio(fechaDocPrevio);
                vsl.setFechaInicio(s.getFechaIngresoInstitucion());
                vsl.setFechaFin(s.getFechaSalida());
                vsl.setIdRegimenLaboral(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId());
                vsl.setRmu(ddh.getRmu());
                vsl.setSaldoVacacionesEfectivas(v.getSaldo());
                vsl.setSaldoVacacionesProporcionales(v.getSaldoProporcional());
                vsl.setIdArchivoAccionPersonal(a.getId());
                vsl.setIdUnidadOrganizacional(dd.getDistributivo().getIdUnidadOrganizacional());
                vsl.setIdUltimoPuestoOcupado(dd.getId());
                vsl.setRepresentanteRRHH(representanteRRHH);
                vsl.setNombreRepresentanteRRHH(nombreRepresentanteRRHH);
                vsl.setAutoridadNominadora(autoridadNominadora);
                vsl.setNombreAutoridadNominadora(nombreAutoridadNominadora);
                vacacionSolicitudLiquidacionDao.crear(vsl);
                vacacionSolicitudLiquidacionDao.flush();

                VacacionDetalle vd1 = new VacacionDetalle();
                vd1.setUsuarioCreacion(usuario.getUsuario());
                vd1.setFechaCreacion(new Date());
                vd1.setVigente(Boolean.TRUE);
                vd1.setCredito(0L);
                vd1.setDebito(v.getSaldo());
                vd1.setEsAjusteManual(Boolean.FALSE);
                vd1.setFechaInicio(new Date());
                vd1.setFechaFin(new Date());
                vd1.setObservacion("DÉBITO POR LIQUIDACIÓN DE VACACIONES");
                vd1.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
                vd1.setVacacion(v);
                vacacionDetalleDao.crear(vd1);

                VacacionDetalle vd2 = new VacacionDetalle();
                vd2.setUsuarioCreacion(usuario.getUsuario());
                vd2.setFechaCreacion(new Date());
                vd2.setVigente(Boolean.TRUE);
                vd2.setCredito(0L);
                vd2.setDebito(v.getSaldoProporcional());
                vd2.setEsAjusteManual(Boolean.FALSE);
                vd2.setFechaInicio(new Date());
                vd2.setFechaFin(new Date());
                vd2.setObservacion("DÉBITO POR LIQUIDACIÓN DE VACACIONES");
                vd2.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
                vd2.setVacacion(v);
                vacacionDetalleDao.crear(vd2);

                vacacionDetalleDao.flush();

                //ACTUALIZAR SALDOS DE VACACIONES
                vacacionDao.totalizarSaldosVacaciones(v.getServidorInstitucion().getId());
                vacacionDao.flush();

                //AFECTAR TRAYECTORIA LABORAL-------------------------------------
                SimpleDateFormat sdf = new SimpleDateFormat(UtilFechas.FORMATO_FECHA);
                TrayectoriaLaboral trayectoriaLaboral = new TrayectoriaLaboral();
                trayectoriaLaboral.setApellidos(s.getApellidos());
                trayectoriaLaboral.setExplicacion("LIQUIDACIÓN DE VACACIONES");
                trayectoriaLaboral.setFechaDesde(sdf.format(new Date()));
                trayectoriaLaboral.setFechaHasta(sdf.format(new Date()));
                trayectoriaLaboral.setGrado(dd.getEscalaOcupacional().getGrado());
                trayectoriaLaboral.setGrupo(GrupoEnum.VACACIONES.getCodigo());
                trayectoriaLaboral.setClase(GrupoEnum.VACACIONES.getCodigo());
                trayectoriaLaboral.setNombres(s.getNombres());
                trayectoriaLaboral.setNumeroIdentificacion(s.getNumeroIdentificacion());
                trayectoriaLaboral.setNumeroMovimiento(numeroAccion == null ? "0000000000"
                        : UtilMatematicas.rellenarConCerosIzq(numeroAccion, 10));
                trayectoriaLaboral.setNumeroDocumentoHabilitante(numeroAccion == null ? "0000000000"
                        : UtilMatematicas.rellenarConCerosIzq(numeroAccion, 10));
                trayectoriaLaboral.setRegimenLaboral(
                        dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre());
                trayectoriaLaboral.setRmu(dd.getRmu());
                trayectoriaLaboral.setRmuSobrevalorado(dd.getRmuSobrevalorado());
                trayectoriaLaboral.setTipoIdentificacion(s.getTipoIdentificacion());
                trayectoriaLaboral.setTipoMovimiento("LIQUIDACIÓN DE VACACIONES");
                trayectoriaLaboral.setUsuarioCreacion(usuario.getUsuario());
                trayectoriaLaboral.setVigente(true);
                trayectoriaLaboral.setFechaCreacion(new Date());
                trayectoriaLaboral.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
                trayectoriaLaboral.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
                for (CertificacionPresupuestaria cp
                        : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                    if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                            dd.getDistributivo().getModalidadLaboral().getId())) {
                        trayectoriaLaboral.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                    }
                }
                trayectoriaLaboral.setElaborador(usuario.getServidor().getApellidosNombres());
                trayectoriaLaboral.setFechaElaborador(new Date());
                trayectoriaLaboral.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
                trayectoriaLaboral.setLegalizador(usuario.getServidor().getApellidosNombres());
                trayectoriaLaboral.setFechaLegalizador(new Date());

                trayectoriaLaboralDao.crear(trayectoriaLaboral);
                trayectoriaLaboralDao.flush();

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServicioException("Error generando archivo acción de personal por liquidación de vacación.", e);
        }

    }

    /**
     * REVIERTE UNA LIQUIDACIÓN DE VACACION
     *
     * @param vacacion
     * @param dd
     * @param s
     * @param vsl
     * @param usuario
     * @throws ServicioException
     */
    public void revertirLiquidacionVacaciones(
            final Vacacion vacacion,
            final DistributivoDetalle dd,
            final Servidor s,
            final VacacionSolicitudLiquidacion vsl,
            final UsuarioVO usuario) throws ServicioException {

        try {
            //---------------------------PONER LIQUIDACION ES ESTADO REVERTIDO---------
            vsl.setEstado(EstadoLiquidacionVacionEnum.REVERTIDO.getCodigo());
            vacacionSolicitudLiquidacionDao.actualizar(vsl);
            vacacionSolicitudLiquidacionDao.flush();

            //---------------------------DEVOLUCIÓN DEL SALDO EFECTIVO---------
            VacacionDetalle vd = new VacacionDetalle();
            vd.setVacacion(vacacion);
            vd.setObservacion("CRÉDITO POR REVERSIÓN DE LIQUIDACIÓN DE VACACIONES");
            vd.setVigente(Boolean.TRUE);
            vd.setCredito(vsl.getSaldoVacacionesEfectivas());
            vd.setDebito(0L);
            vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            vd.setFechaInicio(new Date());
            vd.setFechaFin(new Date());
            vd.setEsAjusteManual(Boolean.FALSE);
            vd.setFechaCreacion(new Date());
            vd.setUsuarioCreacion(usuario.getUsuario());
            vacacionDetalleDao.crear(vd);
            vacacionDetalleDao.flush();

            //---------------------------DEVOLUCIÓN DEL SALDO PROPORCIONAL-------------------       
            VacacionDetalle vd1 = new VacacionDetalle();
            vd1.setVacacion(vacacion);
            vd1.setObservacion("CRÉDITO POR REVERSIÓN DE LIQUIDACIÓN DE VACACIONES");
            vd1.setVigente(Boolean.TRUE);
            vd1.setCredito(vsl.getSaldoVacacionesProporcionales());
            vd1.setDebito(0L);
            vd1.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
            vd1.setFechaInicio(new Date());
            vd1.setFechaFin(new Date());
            vd1.setEsAjusteManual(Boolean.FALSE);
            vd1.setFechaCreacion(new Date());
            vd1.setUsuarioCreacion(usuario.getUsuario());
            vacacionDetalleDao.crear(vd1);
            vacacionDetalleDao.flush();

            vacacionDao.totalizarSaldosVacaciones(vacacion.getServidorInstitucion().getId());

            TrayectoriaLaboral tl = new TrayectoriaLaboral();
            tl.setExplicacion("REVERSIÓN DE LIQUIDACIÓN DE VACACIONES");
            tl.setGrupo(GrupoEnum.VACACIONES.getCodigo());
            tl.setClase(GrupoEnum.VACACIONES.getCodigo());
            tl.setTipoMovimiento("REVERSIÓN DE LIQUIDACIÓN DE VACACIONES ID: " + vsl.getId());
            tl.setNumeroMovimiento("0000000000");
            tl.setNumeroDocumentoHabilitante("0000000000");
            tl.setNombres(s.getNombres());
            tl.setApellidos(s.getApellidos());
            tl.setTipoIdentificacion(s.getTipoIdentificacion());
            tl.setNumeroIdentificacion(s.getNumeroIdentificacion());
            tl.setRmu(dd.getRmu());
            tl.setRmuSobrevalorado(dd.getRmuSobrevalorado());
            tl.setRegimenLaboral(dd
                    .getEscalaOcupacional().getNivelOcupacional()
                    .getRegimenLaboral().getNombre());
            tl.setGrado(dd.getEscalaOcupacional().getGrado());
            tl.setFechaDesde(UtilFechas.formatear(new Date()));
            tl.setFechaHasta(UtilFechas.formatear(new Date()));
            tl.setVigente(true);
            tl.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            tl.setFechaCreacion(new Date());
            tl.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
            tl.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            for (CertificacionPresupuestaria cp
                    : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                        dd.getDistributivo().getModalidadLaboral().getId())) {
                    tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                }
            }
            tl.setElaborador(usuario.getServidor().getApellidosNombres());
            tl.setFechaElaborador(new Date());
            tl.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
            tl.setLegalizador(usuario.getServidor().getApellidosNombres());
            tl.setFechaLegalizador(new Date());
            trayectoriaLaboralDao.crear(tl);
            trayectoriaLaboralDao.flush();

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Guarda la solicitud de vacaciones
     *
     * @param vacacionSolicitud Solicitud de vacacion
     * @param numeroAccionPersonal
     * @throws ec.com.atikasoft.proteus.excepciones.ServidorException Error en el servidor
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException error en el acceso a datos
     */
    public void guardarSaldoVacacion(final VacacionSolicitud vacacionSolicitud, String numeroAccionPersonal,
            UsuarioVO usuarioVO) throws ServidorException, DaoException {
        VacacionDetalle det = new VacacionDetalle();
        det.setUsuarioCreacion(vacacionSolicitud.getUsuarioCreacion());
        det.setFechaCreacion(new Date());
        det.setVigente(Boolean.TRUE);
        if (vacacionSolicitud.getMinutosImputados() == null) {
            vacacionSolicitud.setMinutosImputados(BigDecimal.ZERO);
        }
        try {
            det.setCredito(0L);
            det.setVacacionSolicitud(vacacionSolicitud);
            if (vacacionSolicitud.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.DIA.getCodigo())) {
                det.setObservacion(UtilCadena.concatenar("DESCUENTO POR SOLICITUD DE ", TipoVacacionEnum.obtenerNombre(
                        vacacionSolicitud.getTipo()), ",CANTIDAD SOLICITADA:", vacacionSolicitud.
                        getCantidadSolicitadaTexto(), ",DÍAS:", vacacionSolicitud.getDiasPlanificados()));
                String[] diasVacPlanificados = vacacionSolicitud.getDiasPlanificados().split(",");
                det.setFechaInicio(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(diasVacPlanificados[0]));
                det.setFechaFin(new SimpleDateFormat(UtilFechas.FORMATO_FECHA).parse(
                        diasVacPlanificados[diasVacPlanificados.length - 1]));
            } else {
                det.setObservacion(UtilCadena.concatenar("DESCUENTO POR SOLICITUD DE ", TipoVacacionEnum.obtenerNombre(
                        vacacionSolicitud.getTipo()), ", CANTIDAD SOLICITADA:", vacacionSolicitud.
                        getCantidadSolicitadaTexto(), ", DÍA:", UtilFechas.formatear(vacacionSolicitud.getFecha()),
                        ", DESDE ", new SimpleDateFormat("HH:mm").format(vacacionSolicitud.getHoraInicio()), " HASTA ",
                        new SimpleDateFormat("HH:mm").format(vacacionSolicitud.getHoraFin())));
                det.setFechaInicio(vacacionSolicitud.getHoraInicio());
                det.setFechaFin(vacacionSolicitud.getHoraFin());
            }
            boolean esProporcional = vacacionSolicitud.getTipo().equals(TipoVacacionEnum.ANTICIPO_VACACIONES.
                    getCodigo());
            if (esProporcional) {
                det.setObservacion(det.getObservacion() + ", CON CARGO A VACACIONES PROPORCIONALES");
            } else {
                det.setObservacion(det.getObservacion() + ", CON CARGO A VACACIONES EFECTIVAS");
            }
            Long saldoActual = esProporcional ? vacacionSolicitud.getSaldoVacacionesProporcial()
                    : vacacionSolicitud.getSaldoVacacionesEfectiva();
            if (saldoActual > 0) {
                descontarSaldoDisponible(vacacionSolicitud, det, esProporcional, numeroAccionPersonal, usuarioVO);
            } else {
                throw new ServidorException("El Servidor No tiene saldo " + (esProporcional ? "proporcional" : "")
                        + " de vacaciones disponible.");
            }
        } catch (Exception ex) {
            Logger.getLogger(VacacionServicio.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param vs Solicitud de vacaciones
     * @param d detalle de la vacacion
     * @param esProporsional indicador si el saldo a usar es el proporcional
     * @param numeroAccionPersonal
     * @throws Exception error en la ejecucion del metodo
     */
    public void descontarSaldoDisponible(final VacacionSolicitud vs, final VacacionDetalle d,
            final boolean esProporsional, final String numeroAccionPersonal, UsuarioVO usuarioVO) throws Exception {

        Long totalSolicitado = vs.getCantidadSolicitadaMinutos();
        Vacacion vacacion = vacacionDao.buscarPorServidor(vs.getIdServidorInstitucion());
        if (vacacion != null) {
            Long saldoActual = esProporsional ? vacacion.getSaldoProporcional() : vacacion.getSaldo();
            totalSolicitado = totalSolicitado > saldoActual ? saldoActual : totalSolicitado;
            d.setVacacion(vacacion);
            d.setDebito(totalSolicitado);
            d.setEsAjusteManual(false);
            d.setTipo(
                    esProporsional ? TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo()
                            : TipoVacacionDetalleEnum.VACACION.getCodigo());

            vacacionDetalleDao.crear(d);
            vacacionDetalleDao.flush();

            vacacionDao.totalizarSaldosVacaciones(vacacion.getServidorInstitucion().getId());
            vacacionDao.flush();

            Servidor s = d.getVacacion().getServidorInstitucion().getServidor();
            SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");

            DistributivoDetalle dd = distributivoDetalleServicio.buscar(s.getId());
            TrayectoriaLaboral trayectoriaLaboral = new TrayectoriaLaboral();
            trayectoriaLaboral.setApellidos(s.getApellidos());
            trayectoriaLaboral.setExplicacion(d.getObservacion());
            trayectoriaLaboral.setFechaDesde(sfd.format(d.getFechaInicio()));
            trayectoriaLaboral.setFechaHasta(sfd.format(d.getFechaFin()));
            trayectoriaLaboral.setGrado(dd.getEscalaOcupacional().getGrado());
            trayectoriaLaboral.setGrupo(GrupoEnum.VACACIONES.getCodigo());
            trayectoriaLaboral.setClase(GrupoEnum.VACACIONES.getCodigo());
            trayectoriaLaboral.setNombres(s.getNombres());
            trayectoriaLaboral.setNumeroIdentificacion(s.getNumeroIdentificacion());
            trayectoriaLaboral.setNumeroMovimiento(numeroAccionPersonal == null ? "0000000000"
                    : UtilMatematicas.rellenarConCerosIzq(numeroAccionPersonal, 10));
            trayectoriaLaboral.setNumeroDocumentoHabilitante(numeroAccionPersonal == null ? "0000000000"
                    : UtilMatematicas.rellenarConCerosIzq(numeroAccionPersonal, 10));
            trayectoriaLaboral.setRegimenLaboral(vs.getVacacionParametro().getRegimenLaboral().getNombre());
            trayectoriaLaboral.setRmu(dd.getRmu());
            trayectoriaLaboral.setRmuSobrevalorado(dd.getRmuSobrevalorado());
            trayectoriaLaboral.setTipoIdentificacion(s.getTipoIdentificacion());
            trayectoriaLaboral.setTipoMovimiento("APROBACIÓN DE SOLICITUD DE VACACIONES");
            trayectoriaLaboral.setUsuarioCreacion(d.getUsuarioCreacion());
            trayectoriaLaboral.setVigente(true);
            trayectoriaLaboral.setFechaCreacion(d.getFechaCreacion());
            trayectoriaLaboral.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
            trayectoriaLaboral.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            for (CertificacionPresupuestaria cp
                    : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                        dd.getDistributivo().getModalidadLaboral().getId())) {
                    trayectoriaLaboral.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                }
            }
            trayectoriaLaboral.setElaborador(usuarioVO.getServidor().getApellidosNombres());
            trayectoriaLaboral.setFechaElaborador(new Date());
            trayectoriaLaboral.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
            trayectoriaLaboral.setLegalizador(usuarioVO.getServidor().getApellidosNombres());
            trayectoriaLaboral.setFechaLegalizador(new Date());

            trayectoriaLaboralDao.crear(trayectoriaLaboral);
        }
    }

    /**
     *
     * @param vo Value objeto de los datos de busqueda de la solicitud de vacacion
     * @return lista de solicitudes de vacacion
     * @throws ServicioException error en el servicio
     */
    public List<VacacionSolicitud> buscarSolicitudesVacacion(final BusquedaVacacionVO vo) throws ServicioException {
        try {
            List<VacacionSolicitud> solicitudes = new ArrayList<>();
            List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                    vo.getUsuarioVO().getServidor().getId(),
                    FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            for (UnidadOrganizacional unidad : unidades) {
                vo.setUnidadAdministrativaId(unidad.getId());
                solicitudes.addAll(buscar(vo));
            }
            return solicitudes;

        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Realiza la busqueda de solicitudes de vacaciones
     *
     * @param vo value objeto de los datos de busqueda
     * @param uo datos de la unidasd organizacional
     * @return lista de solicitudes de vacacion
     * @throws ServicioException error en el servicio
     */
//    public List<VacacionSolicitud> buscar(final BusquedaVacacionVO vo, UnidadOrganizacional uo) throws ServicioException {
//        vo.setUnidadAdministrativaId(uo.getId());
//        List<VacacionSolicitud> lista = buscar(vo);
//        for (UnidadOrganizacional uoHijo : uo.getListaUnidadesOrganizacionales()) {
//            lista.addAll(buscar(vo, uoHijo));
//        }
//        return lista;
//    }
    /**
     * REcupera servidor basado en un conjunto de parametros.
     *
     * @param vo value objeto de los datos de busqueda
     * @return lista de solicitudes de vacaciones
     * @throws ServicioException error en el servicio
     */
    public List<VacacionSolicitud> buscar(final BusquedaVacacionVO vo) throws ServicioException {
        try {
            List<VacacionSolicitud> lista = vacacionSolicitudDao.buscarVaciones(vo);
            for (VacacionSolicitud p : lista) {
                long minSaldoTotal = buscarSaldoVacacionesPorServidor(p.getServidorInstitucion().getId());
                Integer[] saldo;
                saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(minSaldoTotal), p.getServidorInstitucion().
                        getServidor().getJornada());
                if (minSaldoTotal < 0 && p.getCadenaSaldo().isEmpty()) {
                    p.setCadenaSaldo(
                            saldo[0] + " Días "
                            + saldo[1] + " Horas "
                            + saldo[2] + " Minutos  (TIEMPO ADEUDADO)");
                } else {
                    p.setCadenaSaldo(
                            saldo[0] + " Días "
                            + saldo[1] + " Horas "
                            + saldo[2] + " Minutos");
                }
                List<DistributivoDetalle> dist = admServicio.listarTodosDistributivoDetallePorServidor(
                        p.getServidorInstitucion().getServidor().getId());
                for (DistributivoDetalle dd : dist) {
                    p.setDistributivoDetalle(dd);
                    break;
                }
            }
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        } catch (ServicioException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param institucionId identificador de la institucion
     * @param servidorId identificador del servidor
     * @return Datos de la vacacion
     * @throws DaoException error en el acceso a datos
     */
    public Vacacion buscarVacacion(final Long institucionId, final Long servidorId) throws DaoException {
        try {
            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(institucionId, servidorId);
            return buscarVacacion(si.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca registro vacacion por el id de servidor_institucion
     *
     * @param servidorInstitucionId identificador del servidor en la institucion
     * @return datos de la vacacion
     * @throws DaoException error en el acceso a datos
     */
    public Vacacion buscarVacacion(final Long servidorInstitucionId) throws DaoException {
        try {
            return vacacionDao.buscarPorServidor(servidorInstitucionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca registro vacacion por el id del servidor
     *
     * @param servidorId identificador delservidor
     * @return datos de la vacacion
     * @throws DaoException error en el acceso de datos
     */
    public Vacacion buscarVacacionPorServidorId(final Long servidorId) throws DaoException {
        try {
            return vacacionDao.buscarPorServidorId(servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Obtiene el total de minutos disponibles de vacaciones de un servidor.
     *
     * @param servidorInstitucionId identificador del servidor en la institucion
     * @return saldo en minutos
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException error en el acceso a datos
     */
    public Long buscarSaldoVacacionesPorServidor(final Long servidorInstitucionId) throws DaoException {
        Long saldo = 0L;
        Vacacion vacacion = vacacionDao.buscarPorServidor(servidorInstitucionId);
        if (vacacion != null) {
            saldo = vacacion.getSaldo();
        }
        return saldo;
    }

    /**
     * Obtiene el total de minutos disponibles de vacaciones de un servidor.
     *
     * @param institucionId identificador de la institucion
     * @param servidorId identificador del servidor
     * @return minutos
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException error en el acceso a datos
     */
    public Long buscarSaldoVacacionesPorServidor(
            final Long institucionId,
            final Long servidorId) throws DaoException {
        Long saldo = 0L;
        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(institucionId, servidorId);
        Vacacion vacacion = vacacionDao.buscarPorServidor(si.getId());
        if (vacacion != null) {
            saldo = vacacion.getSaldo();
        }
        return saldo;
    }

    /**
     * Permite la recuperación de saldo de un registro de vacacionSolicitud
     *
     * @param vacacionSolicitud registro a crear en el sistema
     * @param fechaFinAnterior fecha final anterior
     * @throws ServicioException excepcion a nivel de servicio
     * @throws ec.com.atikasoft.proteus.excepciones.ServidorException error en el servidor
     */
    public void recuperarSaldoVacacionSolicitud(
            final VacacionSolicitud vacacionSolicitud,
            final Date fechaFinAnterior) throws ServicioException, ServidorException {
        try {
            System.out.println("actualizacion de solicitud");
            vacacionSolicitudDao.actualizar(vacacionSolicitud);
            guardarVacacionSolicitudHistorico(vacacionSolicitud);
            System.out.println("despues del historico de solicitud: " + vacacionSolicitud.getId());
            recuperarSaldoVacacion(vacacionSolicitud, fechaFinAnterior);
            System.out.println("despues de recuperar");
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param vs solicitud de vacacion
     * @param fechaFinAnterior fecha final anterior
     * @throws ServicioException error en el servicio
     */
    public void recuperarSaldoVacacion(final VacacionSolicitud vs, final Date fechaFinAnterior)
            throws ServicioException {
        try {
            ServidorInstitucion si = servidorInstitucionDao.buscarPorId(vs.getIdServidorInstitucion());
            VacacionDetalle det = new VacacionDetalle();
            det.setUsuarioCreacion(vs.getUsuarioCreacion());
            det.setFechaCreacion(new Date());
            det.setVigente(Boolean.TRUE);
            vs.setMinutosSolicitados(UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(vs.getTipoPeriodo().charAt(0),
                    vs.getCantidadRecuperada(), si.getServidor().getJornada()));
            if (vs.getMinutosImputados() == null) {
                vs.setMinutosImputados(BigDecimal.ZERO);
            }

            det.setDebito(0L);
            det.setFechaFin(fechaFinAnterior);
            det.setVacacionSolicitud(vs);
            incrementarSaldoDisponible(vs, det);

        } catch (Exception ex) {
            Logger.getLogger(VacacionServicio.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param vs Solicitud de vacaciones
     * @param det detalle de la vacacion
     * @throws Exception error
     */
    public void incrementarSaldoDisponible(final VacacionSolicitud vs, VacacionDetalle det)
            throws Exception {
        long tiempoxDevolver = vs.getMinutosSolicitados();
        Vacacion v = vacacionDao.buscarPorServidor(vs.getServidorInstitucion().getId());
        if (v != null) {
            det.setId(null);
            det.setVacacion(v);
            det.setCredito(tiempoxDevolver);
            v.setSaldo(v.getSaldo() + tiempoxDevolver);
            v.setFechaActualizacion(new Date());
            v.setUsuarioActualizacion(vs.getUsuarioActualizacion());
            det.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
            vacacionDetalleDao.crear(det);
            vacacionDetalleDao.flush();
            vacacionDao.actualizar(v);
        }
    }

    /**
     * Permite el registro de un vacacionSolicitud
     *
     * @param vacacionSolicitud registro a crear en el sistema
     * @param pdet detalle de la planificacion de la vacacion
     * @throws ServicioException excepcion a nivel de servicio
     * @throws ec.com.atikasoft.proteus.excepciones.ServidorException error en el servicio
     */
    public void guardarVacacionSolicitud(final VacacionSolicitud vacacionSolicitud,
            final PlanificacionVacacionDetalle pdet, UsuarioVO usuarioVO) throws ServicioException, ServidorException {
        try {
            if (vacacionSolicitud.getId() == null) {
                vacacionSolicitud.setCantidadRecuperada(0L);
                vacacionSolicitud.setEsVacacionEfectiva(Boolean.TRUE);
                if (vacacionSolicitud.getTipoPeriodo().equals(PeriodoVacacionEnum.DIAS.getCodigo())) {
                    // dias
                    vacacionSolicitud.setCantidadTomadaMinutos((long) vacacionSolicitud.getDiasPlanificados().
                            split(",").length * 8 * 60);
                } else {
                    // minutos
                    vacacionSolicitud.setCantidadTomadaMinutos(UtilFechas.
                            calcularDiferenciaMinutosEntreFechas(vacacionSolicitud.getHoraInicio(),
                                    vacacionSolicitud.getHoraFin()));
                }
                vacacionSolicitudDao.crear(vacacionSolicitud);
            } else {
                vacacionSolicitudDao.actualizar(vacacionSolicitud);
            }
            vacacionSolicitudDao.flush();
            guardarVacacionSolicitudHistorico(vacacionSolicitud);
            if (vacacionSolicitud.getTipo().equals(
                    TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo()) && pdet != null) {
                planificacionVacacionDetalleDao.actualizar(pdet);
            }
            if (vacacionSolicitud.getEstado().equals(EstadoVacacionEnum.APROBADO.getCodigo())) {
                guardarSaldoVacacion(vacacionSolicitud, null, usuarioVO);
            }

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param institucionId identificador de la institucion
     * @param unidadOrganizacionalUsuarioConectado datos de la unidad organizacional del usuario conectado
     * @return Archivo
     */
    public ByteArrayInputStream generarSaldosVacaciones(Long institucionId, UsuarioVO usuarioVO, final Boolean esRRHH) {
        try {
            String titulo = "MUNICIPIO DEL DISTRITO METROPOLITANO DE QUITO";

            List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                    usuarioVO.getServidor().getId(), FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            List<RegistroReporteSaldosVacacionesVO> data
                    = vacacionDao.obtenerDatosParaReporteSaldos(institucionId, unidadesAcceso, esRRHH);

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet hoja = workbook.createSheet(titulo);

            CellStyle estiloCelda = workbook.createCellStyle();
            HSSFFont fuenteCabecera = workbook.createFont();
            fuenteCabecera.setBold(true);
            estiloCelda.setFont(fuenteCabecera);
            estiloCelda.setAlignment(HorizontalAlignment.LEFT);

            //TITULO DEL REPORTE
            //UNIENDO CELDAS EN UNA SOLA
            hoja.addMergedRegion(new CellRangeAddress(
                    0, //Primera fila
                    0, //Última fila
                    0, //Primera columna
                    9 //Última columna
            ));
            UtilArchivos.crearFilaExcel(workbook, hoja, 0, estiloCelda, "SALDOS VACACIONES EFECTIVAS");
            //NOMBRE UNIDAD AGRUPADORA
            //UNIENDO CELDAS EN UNA SOLA
            hoja.addMergedRegion(new CellRangeAddress(
                    1, //Primera fila
                    1, //Última fila
                    0, //Primera columna
                    9 //Última columna
            ));
            UtilArchivos.crearFilaExcel(workbook, hoja, 1, estiloCelda, "UNIDAD ORGANIZACIONAL: " + titulo);

            //UNIENDO CELDAS EN UNA SOLA
            hoja.addMergedRegion(new CellRangeAddress(
                    2, //Primera fila
                    2, //Última fila
                    4, //Primera columna
                    6 //Última columna
            ));
            //UNIENDO CELDAS EN UNA SOLA
            hoja.addMergedRegion(new CellRangeAddress(
                    2, //Primera fila
                    2, //Última fila
                    7, //Primera columna
                    9 //Última columna
            ));
            UtilArchivos.crearFilaExcel(workbook, hoja, 2, estiloCelda, "", "", "", "", "SALDO VACACIONES PROPORCIONAL",
                    "", "", "SALDO EFECTIVO");

            // crear cabecera
            int fila = 3;
            UtilArchivos.crearFilaExcel(workbook, hoja, 3, estiloCelda,
                    "TIPO DE IDENTIFICACIÓN",
                    "NÚMERO DE IDENTIFICACIÓN",
                    "APELLIDOS Y NOMBRES",
                    "DEPENDENCIA",
                    "DÍAS",
                    "HORAS",
                    "MINUTOS",
                    "DÍAS",
                    "HORAS",
                    "MINUTOS");

            // cargar los datos          
            for (RegistroReporteSaldosVacacionesVO reg : data) {
                fila++;
                Integer[] minutosSaldoConvertidos = UtilFechas.convertirMinutosA_ddHHmm(reg.getSaldo(), 8);
                Integer[] minutosSaldoProporcionalConvertidos = UtilFechas.convertirMinutosA_ddHHmm(
                        reg.getSaldoProporcional(), 8);
                if (reg.getNumeroIdentificacion().equals("1725887622")) {
                    System.out.println("ok");
                }
                UtilArchivos.crearFilaExcel(
                        workbook, hoja, fila, null,
                        TipoIdentificacionEnum.obtenerDescripcion(reg.getTipoIdentificacion()),
                        reg.getNumeroIdentificacion(),
                        reg.getApellidosNombres(),
                        reg.getNombreUnidadOrganizacional(),
                        minutosSaldoProporcionalConvertidos[0],
                        minutosSaldoProporcionalConvertidos[1],
                        minutosSaldoProporcionalConvertidos[2],//reg.getSaldoProporcional(),
                        minutosSaldoConvertidos[0],
                        minutosSaldoConvertidos[1],
                        minutosSaldoConvertidos[2]/*reg.getSaldo()*/);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);
            ByteArrayInputStream inStream = new ByteArrayInputStream(stream.toByteArray());
            return inStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retorna la fecha de inicio de la solicitud de vacaciones
     *
     * @param vacacionSolicitud solicitud de vacacion
     * @return fecha de inicio
     * @throws ServicioException error en el servicio
     */
    public Date obtenerFechaInicioVacaciones(VacacionSolicitud vacacionSolicitud) throws ServicioException {
        Date date = null;
        if (vacacionSolicitud.getFecha() != null) {
            date = vacacionSolicitud.getFecha();
        } else {
            try {
                List<Date> fechas = UtilFechas.parsearLista(Arrays.asList(vacacionSolicitud.getDiasPlanificados().
                        split(",")), "dd/MM/yyyy");
                date = fechas.get(0);
            } catch (ParseException ex) {
                Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServicioException(ex);
            }
        }
        return date;
    }

    /**
     * DESCUENTA DEL SALDO DE VACIONES EFECTIVAS Y DEL SALDO DE VACACIONES PROPORCIONALES, SI ES NECESARIO, UNA CANTIDAD
     * DE TIEMPO ESPECIFICADA
     *
     * @param vacacionId identificador de la vacacion
     * @param descuentoARealizar minutos de vacaciones a descontar
     * @param usuarioVO datos del usuario conectado
     * @return saldo resultado
     * @throws ServicioException error en el servicio
     */
    public String[] realizarDescuentoTiempoNoLaborado(Long vacacionId, Long descuentoARealizar, UsuarioVO usuarioVO)
            throws ServicioException {

        try {
            Vacacion v = vacacionDao.buscarPorId(vacacionId);
            Long saldoEfectivo = v.getSaldo();
            Long saldoProporcional = v.getSaldoProporcional();

            String[] mensaje = new String[2];

            if (descuentoARealizar > (saldoEfectivo + saldoProporcional)) {
                mensaje[0] = "ERROR";
                mensaje[1] = "No es posible realizar el descuento de tiempo especificado. "
                        + "La cantidad a descontar supera el saldo de vacaciones disponible.";
            } else {
                String observacion = "DESCUENTO DE TIEMPO NO LABORADO";
                Long descuentoEfectivo = 0L;
                if (saldoEfectivo > 0) {
                    descuentoEfectivo = saldoEfectivo >= descuentoARealizar
                            ? descuentoARealizar : saldoEfectivo;

                    VacacionDetalle vd = new VacacionDetalle();
                    vd.setDebito(descuentoEfectivo);
                    vd.setCredito(0L);
                    vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
                    vd.setVacacion(v);
                    vd.setObservacion(observacion);
                    vd.setVigente(Boolean.TRUE);
                    vd.setFechaInicio(new Date());
                    vd.setFechaFin(new Date());
                    vd.setEsAjusteManual(Boolean.FALSE);
                    vd.setFechaCreacion(new Date());
                    vd.setUsuarioCreacion(usuarioVO.getServidor().getNumeroIdentificacion());
                    vacacionDetalleDao.crear(vd);
                    vacacionDetalleDao.flush();
                }

                Long descuentoProporcional = descuentoARealizar - descuentoEfectivo;

                if (descuentoProporcional > 0) {
                    VacacionDetalle vd1 = new VacacionDetalle();
                    vd1.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
                    vd1.setDebito(descuentoProporcional);
                    vd1.setCredito(0L);
                    vd1.setVacacion(v);
                    vd1.setObservacion(observacion);
                    vd1.setVigente(Boolean.TRUE);
                    vd1.setFechaInicio(new Date());
                    vd1.setFechaFin(new Date());
                    vd1.setEsAjusteManual(Boolean.FALSE);
                    vd1.setFechaCreacion(new Date());
                    vd1.setUsuarioCreacion(usuarioVO.getServidor().getNumeroIdentificacion());
                    vacacionDetalleDao.crear(vd1);
                    vacacionDetalleDao.flush();
                }
                vacacionDao.totalizarSaldosVacaciones(v.getServidorInstitucion().getId());

                TrayectoriaLaboral tl = new TrayectoriaLaboral();
                tl.setExplicacion("DESCUENTO TIEMPO NO LABORADO");
                tl.setGrupo(GrupoEnum.VACACIONES.getCodigo());
                tl.setClase(GrupoEnum.VACACIONES.getCodigo());
                tl.setTipoMovimiento("DESCUENTO TIEMPO NO LABORADO");
                tl.setNumeroMovimiento("0000000000");
                tl.setNumeroDocumentoHabilitante("0000000000");

                DistributivoDetalle dd = distributivoDetalleServicio.buscar(
                        v.getServidorInstitucion().getServidor().getId());
                Servidor s = dd.getServidor();

                tl.setNombres(s.getNombres());
                tl.setApellidos(s.getApellidos());
                tl.setTipoIdentificacion(s.getTipoIdentificacion());
                tl.setNumeroIdentificacion(s.getNumeroIdentificacion());
                tl.setRmu(dd.getRmu());
                tl.setRmuSobrevalorado(dd.getRmuSobrevalorado());
                tl.setRegimenLaboral(dd
                        .getEscalaOcupacional().getNivelOcupacional()
                        .getRegimenLaboral().getNombre());
                tl.setGrado(dd.getEscalaOcupacional().getGrado());
                tl.setFechaDesde(UtilFechas.formatear(new Date()));
                tl.setFechaHasta(UtilFechas.formatear(new Date()));
                tl.setVigente(true);
                tl.setUsuarioCreacion(usuarioVO.getServidor().getNumeroIdentificacion());
                tl.setFechaCreacion(new Date());
                tl.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
                tl.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
                for (CertificacionPresupuestaria cp
                        : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                    if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                            dd.getDistributivo().getModalidadLaboral().getId())) {
                        tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                    }
                }
                tl.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
                tl.setElaborador(usuarioVO.getServidor().getApellidosNombres());
                tl.setFechaElaborador(new Date());
                tl.setLegalizador(usuarioVO.getServidor().getApellidosNombres());
                tl.setFechaLegalizador(new Date());
                trayectoriaLaboralDao.crear(tl);
                trayectoriaLaboralDao.flush();

                mensaje[0] = "INFO";
                mensaje[1] = "Descuento realizado satisfactoriamente";
            }

            return mensaje;

        } catch (DaoException ex) {
            Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void finalizarCalculoVacacion() throws DaoException {
        // Totalizar saldos de vacaciones.
        System.out.println("TOTALIZAR ");
//        vacacionDao.ajustarVacacionesProporcionalesExcedidas();
        vacacionDao.totalizarSaldosVacaciones();

        // Descontar los saldos superior a los permitidos.
//            for (VacacionParametro vp : parametros) {
//                vacacionServicio.descontarSobrelimitado(vp);
//            }
//        vacacionDao.totalizarSaldosVacaciones();
    }

    /**
     *
     * @param servidorInstitucionId
     * @return
     * @throws ServicioException
     */
    public Long contarMinutosAprobadosYSolicitadosUltimos(final Long servidorInstitucionId) throws ServicioException {
        try {
            Long total = vacacionSolicitudDao.contarMinutosVacacionesSolicitadasYAprobadas(servidorInstitucionId);
            return total;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * RECUPERA TODAS LAS LIQUIDACIONES DE VACACIONES VIGENTES
     *
     * @return
     * @throws ServicioException
     */
    public List<VacacionSolicitudLiquidacion> listarLiquidacionesVacacionesTodasVigentes() throws ServicioException {
        try {
            return vacacionSolicitudLiquidacionDao.buscarTodosVigentes();
        } catch (Exception e) {
            throw new ServicioException("Error al recuperar todas las liquidaciones de vacaciones vigentes", e);
        }
    }

    /**
     * RECUPERA TODAS LAS LIQUIDACIONES DE VACACIONES VIGENTES ASOCIADAS A UN SERVIDOR_INSTITUCION
     *
     * @param servidorInstitucionId
     * @return
     * @throws ServicioException
     */
    public List<VacacionSolicitudLiquidacion> listarLiquidacionesVacacionesPorServidorInstitucionId(
            Long servidorInstitucionId) throws ServicioException {
        try {
            return vacacionSolicitudLiquidacionDao.buscarPorServidorInstitucion(servidorInstitucionId);
        } catch (Exception e) {
            throw new ServicioException("Error al recuperar todas las liquidaciones de vacaciones vigentes", e);
        }
    }
}
