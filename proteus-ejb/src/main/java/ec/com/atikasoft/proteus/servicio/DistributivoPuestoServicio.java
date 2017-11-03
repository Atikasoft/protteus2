/*
 *  PuestosServicio.java
 
 *  Quito - Ecuador
 *
 *  11/11/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.CargaMasivaPuestoDao;
import ec.com.atikasoft.proteus.dao.CertificacionPresupuestariaDao;
import ec.com.atikasoft.proteus.dao.DependenciaAsistenciaDao;
import ec.com.atikasoft.proteus.dao.DistributivoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleHistoricoDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import ec.com.atikasoft.proteus.modelo.DependenciaAsistencia;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.distributivo.CargaMasivaPuesto;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.modelo.distributivo.DistributivoDetalleHistorico;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.InfoSriVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class DistributivoPuestoServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(DistributivoPuestoServicio.class.getCanonicalName());

    /**
     * DAO de Distributivo
     */
    @EJB
    private DistributivoDao distributivoDao;

    /**
     * DAO de DistributivoDetalle
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * DAO de DistributivoDetalle
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao del historico del distributivo.
     */
    @EJB
    private DistributivoDetalleHistoricoDao distributivoDetalleHistoricoDao;

    /**
     * Dao de dependencia.
     */
    @EJB
    private DependenciaAsistenciaDao dependenciaAsistenciaDao;

    /**
     * Dao de Unidad Organizacional
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     * Dao de Servidor
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Dao de Trayectoria laboral
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     * Dao de Certificación Presupuestaria
     */
    @EJB
    private CertificacionPresupuestariaDao certificacionPresupuestariaDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private CargaMasivaPuestoDao cargaMasivaPuestoDao;

    /**
     * Permite el registro de los distributivos
     *
     * @param distibutivo registro a crear en el sistema
     * @param usuario
     * @param codigosPuestosGenerados
     * @return
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Distributivo guardarDistributivo(final Distributivo distibutivo, final UsuarioVO usuario, final List<Long> codigosPuestosGenerados) throws ServicioException {
        try {
            distibutivo.setContadorPartida(0l);
            distibutivo.setId(null);
            distibutivo.setNombre(distibutivo.getNombre() != null ? distibutivo.getNombre().toUpperCase() : null);
            Distributivo d = distributivoDao.crear(distibutivo);
            distributivoDao.flush();
            if (distibutivo.getDistributivoDetalles() != null && !distibutivo.getDistributivoDetalles().isEmpty()) {
                for (DistributivoDetalle det : distibutivo.getDistributivoDetalles()) {
                    det.setIdDistributivo(distibutivo.getId());
                    det.setDistributivo(distibutivo);
                    List<CertificacionPresupuestaria> cp = certificacionPresupuestariaDao
                            .buscarPorUnidadPresupuestariaYModalidad(det.getUnidadPresupuestaria().getId(), distibutivo.getModalidadLaboral().getId());
                    det.setCertificacionPresupuestaria(cp != null && !cp.isEmpty() ? cp.get(0).getCertificacionPresupuestaria() : null);
                    det.setGrupoPresupuestario(det.getUnidadPresupuestaria().getGrupoPresupuestario());
                    guardarDistributivoDetalle(det, usuario, codigosPuestosGenerados);
                }
            }
            return d;
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un distibutivo
     *
     * @param distributivo registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarDistributivo(final Distributivo distributivo) throws ServicioException {
        try {
//            Long secuenciaPartida = distributivo.getContadorPartida();
            //buscarContadorPartidaDistributivo(distributivo.getId());
            for (DistributivoDetalle det : distributivo.getDistributivoDetalles()) {
                if (det.getId() == null) {
                    det.setIdDistributivo(distributivo.getId());
//                    secuenciaPartida += SECUENCIA_PARTIDA_INDIVIDUAL;
                    det.setPartidaIndividual(administracionServicio.generarNumeroPartidaIndividual(distributivo.getInstitucionEjercicioFiscal().getInstitucion().getId()));
                    det.setIdDistributivo(distributivo.getId());
                    distributivoDetalleDao.crear(det);
                }
            }
//            distributivo.setContadorPartida(secuenciaPartida);
//            distributivoDao.actualizar(distributivo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un distibutivo
     *
     * @param distributivoDetalle
     * @param distributivo registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarDistributivoDetalle(final DistributivoDetalle distributivoDetalle, final Distributivo distributivo) throws ServicioException {
        try {
            if (!distributivo.getIdModalidadLaboral().equals(distributivoDetalle.getDistributivo().getIdModalidadLaboral())
                    || !distributivo.getIdUnidadOrganizacional().equals(distributivoDetalle.getDistributivo().getIdUnidadOrganizacional())) {
                Distributivo d = distributivoDao.buscar(distributivo.getUnidadOrganizacional().getCodigo(), distributivo.getModalidadLaboral().getCodigo(), distributivo.getIdInstitucionEjercicioFiscal());
                if (d == null) {
                    d = new Distributivo();
                    d.setContadorPartida(0l);
                    d.setFechaCreacion(new Date());
                    d.setIdInstitucionEjercicioFiscal(distributivo.getIdInstitucionEjercicioFiscal());
                    d.setIdModalidadLaboral(distributivo.getIdModalidadLaboral());
                    d.setIdUnidadOrganizacional(distributivo.getIdUnidadOrganizacional());
                    d.setNombre("");
                    d.setTotalDetalles(1l);
                    d.setUsuarioCreacion(distributivo.getUsuarioCreacion());
                    d.setVigente(Boolean.TRUE);
                    distributivoDao.crear(d);
                    distributivoDao.flush();
                }
                distributivoDetalle.setIdDistributivo(d.getId());
            }
            distributivoDetalleDao.actualizar(distributivoDetalle);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un distibutivo
     *
     * @param distributivoDetalle
     * @param distributivoId
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarDistributivoEnDistributivoDetalle(final DistributivoDetalle distributivoDetalle, final Long distributivoId) throws ServicioException {
        try {
            distributivoDetalle.setIdDistributivo(distributivoId);
            distributivoDetalleDao.actualizar(distributivoDetalle);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de los distributivos detalles
     *
     * @param det registro a crear en el sistema
     * @param usuario
     * @param codigosPuestoGenerados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarDistributivoDetalle(final DistributivoDetalle det, final UsuarioVO usuario, final List<Long> codigosPuestoGenerados) throws ServicioException {
        try {
            if (det.getDistributivo() != null) {
                det.setCodigoPuesto(Long.valueOf(administracionServicio.generarNumeroPuesto(det.getDistributivo().
                        getInstitucionEjercicioFiscal().getInstitucion().getId())));
                if (codigosPuestoGenerados != null) {
                    codigosPuestoGenerados.add(det.getCodigoPuesto());
                }
                det.setPartidaIndividual(administracionServicio.generarNumeroPartidaIndividual(det.getDistributivo().
                        getInstitucionEjercicioFiscal().getInstitucion().getId()));
                distributivoDetalleDao.crear(det);
                crearDistributivoDetalleHistorico(det, usuario);
            } else {
                LOG.log(Level.INFO, "No se puede obtener el distributivo para guardar!!!!!!{0}", det.
                        getEscalaOcupacional().getNombre());
            }

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un detalle de distributivo
     *
     * @param det registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarDistributivoDetalle(final DistributivoDetalle det) throws ServicioException {
        try {
            det.setVigente(false);
            distributivoDetalleDao.actualizar(det);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite obtener todos los registros vigentes
     *
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Distributivo> listarTodosDistributivoVigentes() throws ServicioException {
        try {
            return distributivoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener todos los registros vigentes
     *
     * @param idDistributivo
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<DistributivoDetalle> listarTodosDistributivoDetallePorDistributivo(final Long idDistributivo)
            throws ServicioException {
        try {
            return distributivoDetalleDao.buscarPorDistributivo(idDistributivo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los Distributivo por nombre
     *
     * @param nombre String descripcion del Distributivo
     * @param like
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Distributivo> listarTodosDistributivoPorNombre(final String nombre, final boolean like)
            throws ServicioException {
        try {
            List<Distributivo> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = distributivoDao.buscarVigente();
            } else {
                lista = distributivoDao.buscarTodosPorNombre(nombre.toUpperCase(), like);
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener el contador de la partida individual del dsitributivo
     *
     * @param id
     * @return
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Long buscarContadorPartidaDistributivo(final Long id) throws ServicioException {
        try {
            return distributivoDao.buscarContadorPartida(id);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener registros del distributivo por modalidad y unidad organizacional
     *
     * @param unidadOrganizacional
     * @param modalidadLaboral
     * @param institucionEsjercicioFiscalId
     * @return
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Distributivo buscarDistributivo(final String unidadOrganizacional, final String modalidadLaboral,
            final Long institucionEsjercicioFiscalId)
            throws ServicioException {
        try {
            return distributivoDao.buscar(unidadOrganizacional, modalidadLaboral, institucionEsjercicioFiscalId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener registros del distributivo por ids modalidad y unidad organizacional
     *
     * @param unidadOrganizacionalId
     * @param modalidadLaboralId
     * @param institucionEsjercicioFiscalId
     * @return
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Distributivo buscarDistributivo(final Long unidadOrganizacionalId, final Long modalidadLaboralId,
            final Long institucionEsjercicioFiscalId)
            throws ServicioException {
        try {
            return distributivoDao.buscar(unidadOrganizacionalId, modalidadLaboralId, institucionEsjercicioFiscalId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener el numero de detalles por distributivo
     *
     * @param distributivo
     * @return
     * @throws ServicioException excepcion a nivel de servicio
     */
    public Long contarDetallesPorDistributivo(final Distributivo distributivo) throws ServicioException {
        try {
            UnidadOrganizacional uo = distributivo.getUnidadOrganizacional();
            ModalidadLaboral ml = distributivo.getModalidadLaboral();
            return contarDetallesPorDistributivo(uo, ml);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    private Long contarDetallesPorDistributivo(UnidadOrganizacional uo, ModalidadLaboral ml) throws DaoException {
        Long contador = distributivoDetalleDao.contarDetallesporDistributivo(uo.getId(), ml.getId());
        for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
            contador += contarDetallesPorDistributivo(hijo, ml);
        }
        return contador;
    }

    /**
     * REcupera puesto basado en un conjunto de parametros.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscar(final BusquedaPuestoVO vo, boolean especial, UsuarioVO usuarioVO,
            boolean todos) throws ServicioException {
        try {
            List<DistributivoDetalle> puestos = new ArrayList<>();
            if (todos) {
                vo.setUnidadAdministrativaId(null);
                puestos.addAll(distributivoDetalleDao.buscar(vo, especial));
            } else {
                List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                        usuarioVO.getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());
                for (UnidadOrganizacional uo : unidadesAcceso) {
                    vo.setUnidadAdministrativaId(uo.getId());
                    puestos.addAll(distributivoDetalleDao.buscar(vo, especial));
                }
            }
//            puestos.addAll(distributivoDetalleDao.buscarRecusivamente(vo, especial));
            return puestos;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Busca un distributivoDetalle por numero de partida individual
     *
     * @param nroPartidaIndividual
     * @exception ServicioException
     * @return
     */
    public DistributivoDetalle buscarPorPartidaIndividual(String nroPartidaIndividual) throws ServicioException {
        try {
            List<DistributivoDetalle> ldd = distributivoDetalleDao.buscarPorPartidaIndividual(nroPartidaIndividual);
            if (!ldd.isEmpty()) {
                return ldd.get(0);
            }
            return null;

        } catch (DaoException e) {
            throw new ServicioException(e);
        }

    }

    /**
     * REcupera puestos vacantes dado un codigo
     *
     * @param codigoPuesto
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscarVacantesPorCodigo(final Long codigoPuesto) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarVacantePorCodigo(codigoPuesto);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * REcupera puesto basado en un conjunto de parametros.
     *
     * @param codigoPuesto
     * @param institucionEjercicioFiscalId
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscarPorCodigoPuesto(final Long codigoPuesto,
            final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarPorCodigo(codigoPuesto, institucionEjercicioFiscalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param institucionEjercicioFiscalId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public DistributivoDetalle buscarDistributivoPorServidor(final String tipoIdentificacion,
            final String numeroIdentificacion, final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarPorServidor(tipoIdentificacion, numeroIdentificacion,
                    institucionEjercicioFiscalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * RECUPERA EL ÚLTIMO REGISTRO DE DISTRIBUTIVO DETALLE DE ACUERDO A LA IDENTIFICACION DEL SERVIDOR
     *
     * @param tipoIdentifiacion
     * @param nroIdentificacion
     * @return
     * @throws ServicioException
     */
    public DistributivoDetalle recuperarUltimoDistributivoDetalleOcupadoPorServidor(
            String tipoIdentifiacion, String nroIdentificacion) throws ServicioException {
        try {
            List<DistributivoDetalleHistorico> lddh = distributivoDetalleHistoricoDao.buscarPorIdentificacionYEstado(
                    tipoIdentifiacion, nroIdentificacion, EstadoPuestoEnum.OCUPADO.getCodigo());
            if (!lddh.isEmpty()) {
                DistributivoDetalleHistorico ddh = lddh.get(0);
                List<DistributivoDetalle> ldd = distributivoDetalleDao.buscarVacantePorCodigo(ddh.getCodigoPuesto());
                if (!ldd.isEmpty()) {
                    return ldd.get(0);
                }
            }
            return null;

        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServicioException(
                    "Error tratando de recuperar el último Distributivo Detalle Historicos por Servidor", e);
        }
    }

    /**
     *
     * @param dd
     * @param usuarioVO
     * @throws ServicioException
     */
    public void crearDistributivoDetalleHistorico(final DistributivoDetalle dd, final UsuarioVO usuarioVO) throws
            ServicioException {
        try {
            DistributivoDetalleHistorico his = new DistributivoDetalleHistorico();
            his.setCertificacionPresupuestaria(dd.getCertificacionPresupuestaria());
            his.setCodigoPuesto(dd.getCodigoPuesto());
            his.setEscalaOcupacionalCodigo(dd.getEscalaOcupacional().getCodigo());
            his.setEscalaOcupacionalGrado(dd.getEscalaOcupacional().getGrado());
            his.setEscalaOcupacionalNombre(dd.getEscalaOcupacional().getNombre());
            his.setEscalaOcupacionalRmu(dd.getEscalaOcupacional().getRmu());
            his.setEscalaOcupacionalRmuMaximo(dd.getEscalaOcupacional().getRmuMaximo());
            his.setEstadoPuestoCodigo(dd.getEstadoPuesto().getCodigo());
            his.setEstadoPuestoNombre(dd.getEstadoPuesto().getNombre());
            his.setFechaFin(dd.getFechaFin());
            his.setFechaFinComisionServicio(dd.getFechaFinComisionServicio());
            his.setFechaIngreso(dd.getFechaIngreso());
            his.setFechaInicio(dd.getFechaInicio());
            his.setFechaInicioComisionServicio(dd.getFechaInicioComisionServicio());
            his.setFechaMaximoCambioAdministrativo(dd.getFechaMaximoCambioAdministrativo());
            his.setGrupoPresupuestario(dd.getGrupoPresupuestario());
            his.setModalidadLaboralCodigo(dd.getDistributivo().getModalidadLaboral().getCodigo());
            his.setModalidadLaboralNombre(dd.getDistributivo().getModalidadLaboral().getNombre());
            his.setNivelOcupacionalCodigo(dd.getEscalaOcupacional().getNivelOcupacional().getCodigo());
            his.setNivelOcupacionalNombre(dd.getEscalaOcupacional().getNivelOcupacional().getNombre());
            his.setPartidaIndividual(dd.getPartidaIndividual());
            his.setRegimenLaboralCodigo(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getCodigo());
            his.setRegimenLaboralNombre(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre());
            his.setRmu(dd.getRmu());
            his.setRmuSobrevalorado(dd.getRmuSobrevalorado());
            his.setSueldoBasico(dd.getSueldoBasico());
            his.setTipoComisionServicio(dd.getTipoComisionServicio());
            his.setUbicacionGeograficaCodigo(dd.getUbicacionGeografica().getCodigo());
            his.setUbicacionGeograficaNombre(dd.getUbicacionGeografica().getNombre());
            his.setUnidadOrganizacionalCodigo(dd.getDistributivo().getUnidadOrganizacional().getCodigo());
            his.setUnidadOrganizacionalNombre(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            his.setObservacion(dd.getObservacionUltimaModificacion());
            if (dd.getMovimiento() != null) {
                his.setMovimientoId(dd.getMovimiento().getId());
            }
            if (dd.getDenominacionPuesto() != null) {
                his.setDenominacionPuestoCodigo(dd.getDenominacionPuesto().getCodigo());
                his.setDenominacionPuestoNombre(dd.getDenominacionPuesto().getNombre());
            }

            if (dd.getServidorComisionServicio() != null) {
                his.setServidorComisionServicioNombres(dd.getServidorComisionServicio().getApellidosNombres());
                his.setServidorComisionServicioNumeroIdentificacion(
                        dd.getServidorComisionServicio().getNumeroIdentificacion());
                his.setServidorComisionServicioTipoIdentificacion(
                        dd.getServidorComisionServicio().getTipoIdentificacion());
            }
            if (dd.getServidor() != null) {
                his.setServidorNombres(dd.getServidor().getApellidosNombres());
                his.setServidorNumeroIdentificacion(dd.getServidor().getNumeroIdentificacion());
                his.setServidorTipoIdentificacion(dd.getServidor().getTipoIdentificacion());
                his.setSueldoBasico(dd.getServidor().getSueldoBasico());
            }
            if (dd.getUnidadOrganizacionalCambioAdministrativo() != null) {
                his.setUnidadOrganizacionalCambioAdministrativoCodigo(dd.getUnidadOrganizacionalCambioAdministrativo().
                        getCodigo());
                his.setUnidadOrganizacionalCambioAdministrativoNombre(dd.getUnidadOrganizacionalCambioAdministrativo().
                        getRuta());
            }
            if (dd.getUnidadPresupuestaria() != null) {
                his.setUnidadPresupuestariaCentroCosto(dd.getUnidadPresupuestaria().getCentroCosto());
                his.setUnidadPresupuestariaCentroGestor(dd.getUnidadPresupuestaria().getCentroGestor());
                his.setUnidadPresupuestariaCodigo(dd.getUnidadPresupuestaria().getCodigo());
                his.setUnidadPresupuestariaFondo(dd.getUnidadPresupuestaria().getFondo());
                his.setUnidadPresupuestariaNombre(dd.getUnidadPresupuestaria().getNombre());
                his.setUnidadPresupuestariaPrograma(dd.getUnidadPresupuestaria().getPrograma());
                his.setUnidadPresupuestariaProyecto(dd.getUnidadPresupuestaria().getProyecto());
                his.setUnidadPresupuestariaSector(dd.getUnidadPresupuestaria().getSector().getNombre());
                his.setUnidadPresupuestariaSociedad(dd.getUnidadPresupuestaria().getSociedad());
            }
            if (dd.getEstadosAdmPuestosRegimenLaboral() != null) {
                his.setEstadoAdministracionPuesto(dd.getEstadosAdmPuestosRegimenLaboral().getEstadoAdministracionPuesto()
                        .getNombre());
            }
            his.setFechaCreacion(new Date());
            his.setUsuarioCreacion(usuarioVO.getUsuario());
            his.setVigente(Boolean.TRUE);
//            System.out.println(BeanUtils.describe(his));
            distributivoDetalleHistoricoDao.crear(his);
            //distributivoDetalleHistoricoDao.flush();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<DistributivoDetalleHistorico> buscarHistoricoPorPuesto(Long codigoPuesto)
            throws ServicioException {
        try {
            return distributivoDetalleHistoricoDao.buscarPorPuesto(codigoPuesto);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param numeroIdentificacion
     * @param nombres
     * @param codigoDependencia
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscar(final String numeroIdentificacion, final String nombres,
            final String codigoDependencia) throws ServicioException {
        try {
            List<DistributivoDetalle> puestos = new ArrayList<DistributivoDetalle>();
            // buscar codigo de dependencia.
            if (codigoDependencia == null || codigoDependencia.trim().isEmpty()) {
                puestos.addAll(distributivoDetalleDao.buscar(numeroIdentificacion, UtilCadena.concatenar(nombres, "%"), null));
            } else {
                List<DependenciaAsistencia> das = dependenciaAsistenciaDao.buscarPorCodigo(codigoDependencia);
                if (das.isEmpty()) {
                    puestos.addAll(distributivoDetalleDao.buscar(numeroIdentificacion, UtilCadena.concatenar(nombres, "%"), null));
                } else {
                    for (DependenciaAsistencia da : das) {
                        puestos.addAll(distributivoDetalleDao.buscar(numeroIdentificacion, UtilCadena.concatenar(nombres, "%"), da.getCodigoUnidadOrganizacional()));
                    }
                }
            }
            return puestos;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param codigoDependencia
     * @return
     * @throws ServicioException
     */
    public List<DistributivoDetalle> buscar(final String codigoDependencia) throws ServicioException {
        try {
            List<DistributivoDetalle> puestos = new ArrayList<DistributivoDetalle>();
            if (codigoDependencia != null) {
                List<DependenciaAsistencia> das = dependenciaAsistenciaDao.buscarPorCodigo(codigoDependencia);
                for (DependenciaAsistencia da : das) {
                    puestos.addAll(distributivoDetalleDao.buscarPorUnidadOrganizacional(da.getCodigoUnidadOrganizacional()));
                }
            }
            return puestos;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param codigo
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public DistributivoDetalle buscarServidorPorCodigo(final Long codigo) throws ServicioException {
        try {
            return distributivoDetalleDao.buscarServidorPorCodigo(codigo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca los puesto de manera recursiva.
     *
     * @param institucionEjercicioFiscalId
     * @param unidadOrganizacionalId
     * @param modalidadLaboralId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<DistributivoDetalle> buscarPuestos(final Long institucionEjercicioFiscalId,
            final Long unidadOrganizacionalId, final Long modalidadLaboralId) throws ServicioException {
        try {
            UnidadOrganizacional uo = unidadOrganizacionalDao.buscarPorId(unidadOrganizacionalId);
            List<DistributivoDetalle> puestos = distributivoDetalleDao.buscarPorUnidadOrganizacionalYModalidadLaboral(
                    institucionEjercicioFiscalId, uo.getId(), modalidadLaboralId);
            for (UnidadOrganizacional hijo : uo.getListaUnidadesOrganizacionales()) {
                puestos.addAll(buscarPuestos(institucionEjercicioFiscalId, hijo.getId(), modalidadLaboralId));
            }
            return puestos;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * GUARDA DATOS DEL DISTRIBUTIVO DESDE LA PANTALLA CLASIFICACION VALORACION PUESTO
     *
     * @param dd
     * @param usuarioConectado
     * @throws ServicioException
     */
    public void guardarClasificacionValoracionPuesto(
            DistributivoDetalle dd, UsuarioVO usuarioConectado) throws ServicioException {
        try {
            if (dd.getDistributivo().getId() == null) {
                distributivoDao.crear(dd.getDistributivo());
                distributivoDao.flush();

                Distributivo nuevoDistributivo = distributivoDao.buscar(
                        dd.getDistributivo().getIdUnidadOrganizacional(),
                        dd.getDistributivo().getIdModalidadLaboral(),
                        usuarioConectado.getInstitucionEjercicioFiscal().getId());

                dd.setIdDistributivo(nuevoDistributivo.getId());
                dd.setDistributivo(nuevoDistributivo);

            }

            if (dd.getServidor() != null) {
                servidorDao.actualizar(dd.getServidor());
                servidorDao.flush();
            }

            distributivoDetalleDao.actualizar(dd);
            distributivoDetalleDao.flush();

            //actualizar trayectoria laboral
            crearDistributivoDetalleHistorico(dd, usuarioConectado);
            afectarTrayectoriaLaboral(dd, "MODIFICACIÓN DE PUESTO", GrupoEnum.ROTACIONES.getCodigo(),
                    "MODIFICACIÓN DE PUESTO", "0000000000", usuarioConectado.getServidor().getNumeroIdentificacion(),
                    usuarioConectado.getServidor().getApellidosNombres());

        } catch (DaoException ex) {
            Logger.getLogger(VacacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Registra los ajustes realizados en la tabla de trayectoria laboral
     *
     * @param dd
     * @param explicacion
     * @param grupo
     * @param tipoMovimiento
     * @param numeroMovimiento
     * @param usuario
     */
    public void afectarTrayectoriaLaboral(DistributivoDetalle dd, String explicacion, String grupo, String tipoMovimiento,
            String numeroMovimiento, String usuario, String nombreUsuario) {
        try {
            TrayectoriaLaboral tl = new TrayectoriaLaboral();
            tl.setExplicacion(explicacion);
            tl.setGrupo(grupo);
            tl.setClase(grupo);
            tl.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
            tl.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            for (CertificacionPresupuestaria cp : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                        dd.getDistributivo().getModalidadLaboral().getId())) {
                    tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                    break;
                }
            }

            tl.setTipoMovimiento(tipoMovimiento);
            tl.setNumeroMovimiento(numeroMovimiento);

            if (dd.getServidor() != null) {
                tl.setNombres(dd.getServidor().getNombres());
                tl.setApellidos(dd.getServidor().getApellidos());
                tl.setNumeroIdentificacion(dd.getServidor().getNumeroIdentificacion());
                tl.setTipoIdentificacion(dd.getServidor().getTipoIdentificacion());
            }
            tl.setRmu(dd.getRmu());
            tl.setRmuSobrevalorado(dd.getRmuSobrevalorado());
            tl.setRegimenLaboral(dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre());
            tl.setGrado(dd.getEscalaOcupacional().getGrado());
            tl.setFechaDesde(UtilFechas.formatear(new Date()));
            tl.setFechaHasta(UtilFechas.formatear(new Date()));
            tl.setVigente(true);
            tl.setUsuarioCreacion(usuario);
            tl.setFechaCreacion(new Date());
            tl.setElaborador(nombreUsuario);
            tl.setFechaElaborador(new Date());
            tl.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
            tl.setLegalizador(nombreUsuario);
            tl.setFechaLegalizador(new Date());
            //crear trayectoria en BD
            trayectoriaLaboralDao.crear(tl);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoPuestoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * GUARDA DATOS DISTRIBUTIVO DETALLE DESDE LA PANTALLA CLASIFICACION VALORACION PUESTO CARGA MASIVA
     *
     * @param puestos
     * @param usuarioConectado
     * @return cantidad de registros procesados
     */
    public int guardarClasificacionValoracionPuestoMasiva(List<DistributivoDetalle> puestos,
            UsuarioVO usuarioConectado) {
        int i = 1;
        boolean errorGuardar = false;
        for (DistributivoDetalle d : puestos) {
            try {
                guardarClasificacionValoracionPuesto(d, usuarioConectado);
            } catch (ServicioException ex) {
                errorGuardar = true;
                Logger.getLogger(DistributivoPuestoServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            i++;
        }
        if (errorGuardar) {
            return i - 1;
        }
        return 0;
    }

    /**
     * Busca la certificación de la unidad presupuestaria y la modalidad laboral especificadas
     *
     * @param unidadPresupuestariaId Long id de la unidad presupuestaria
     * @param modalidadLaboralId Long id de la modalidad laboral
     * @return
     */
    public String obtenerNumeroCertificacionPresupuestaria(final Long unidadPresupuestariaId, final Long modalidadLaboralId) {
        try {
            List<CertificacionPresupuestaria> lista
                    = certificacionPresupuestariaDao.buscarPorUnidadPresupuestariaYModalidad(
                            unidadPresupuestariaId, modalidadLaboralId);
            if (!lista.isEmpty()) {
                return lista.get(0).getCertificacionPresupuestaria();
            }
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoPuestoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //------------- Generacion Masiva de Puestos
    /**
     * Generar puestos cargados a partir del csv, devuelve los codigos unicos generados a los puestos
     *
     * @param listaDistributivo
     * @param usuario
     * @throws ServicioException
     */
    public void generarPuestosMasivo(final List<Distributivo> listaDistributivo, final UsuarioVO usuario) throws ServicioException {
        List<Long> codigosPuestosGenerados = new ArrayList<>();
        try {
            for (Distributivo distributivo : listaDistributivo) {

                Distributivo d = distributivoDao.buscar(distributivo.getUnidadOrganizacional().getCodigo(),
                        distributivo.getModalidadLaboral().getCodigo(), distributivo.getInstitucionEjercicioFiscal().getId());

                if (d == null) {
                    guardarDistributivo(distributivo, usuario, codigosPuestosGenerados);
                } else {
                    for (DistributivoDetalle det : distributivo.getDistributivoDetalles()) {
                        det.setIdDistributivo(d.getId());
                        det.setDistributivo(d);
                        List<CertificacionPresupuestaria> cp = certificacionPresupuestariaDao
                                .buscarPorUnidadPresupuestariaYModalidad(det.getUnidadPresupuestaria().getId(),
                                        distributivo.getModalidadLaboral().getId());
                        det.setCertificacionPresupuestaria(cp != null && !cp.isEmpty() ? cp.get(0).getCertificacionPresupuestaria() : null);
                        det.setGrupoPresupuestario(det.getUnidadPresupuestaria().getGrupoPresupuestario());
                        guardarDistributivoDetalle(det, usuario, codigosPuestosGenerados);
                    }
                }
            }

            CargaMasivaPuesto cargaMasivaPuesto = new CargaMasivaPuesto();
            cargaMasivaPuesto.setFechaCreacion(new Date());
            cargaMasivaPuesto.setVigente(Boolean.TRUE);
            cargaMasivaPuesto.setUsuarioCreacion(usuario.getUsuario());
            cargaMasivaPuesto.setIdInstitucionEjercicioFiscal(usuario.getInstitucionEjercicioFiscal().getId());
            cargaMasivaPuesto.setInstitucionEjercicioFiscal(usuario.getInstitucionEjercicioFiscal());
            cargaMasivaPuesto.setPuestosComoString(codigosPuestosGenerados);
            cargaMasivaPuesto.setTotalPuestosGenerados(codigosPuestosGenerados.size());
            cargaMasivaPuestoDao.crear(cargaMasivaPuesto);

        } catch (Exception e) {
            Logger.getLogger(DistributivoPuestoServicio.class.getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * *
     * Genera el excel de los puestos cargados duarante una carga masiva
     *
     * @param idCargaMasiva
     * @return
     */
    public ByteArrayInputStream generarExcelPuestosGenerados(final Long idCargaMasiva) {
        try {

            CargaMasivaPuesto cargaMasivaPuesto = cargaMasivaPuestoDao.buscarPorId(idCargaMasiva);
            List<DistributivoDetalle> listaPuestos = distributivoDetalleDao.buscarPorCodigos(cargaMasivaPuesto.getPuestos());

            String titulo = "CARGA MASIVA";

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet hoja = workbook.createSheet(titulo);

            CellStyle estiloCelda = workbook.createCellStyle();
            HSSFFont fuenteCabecera = workbook.createFont();
            fuenteCabecera.setBold(true);
            estiloCelda.setFont(fuenteCabecera);
            estiloCelda.setAlignment(HorizontalAlignment.LEFT);

            // crear cabecera
            int fila = 0;
            UtilArchivos.crearFilaExcel(workbook, hoja, 0, estiloCelda,
                    "CODIGO", "PARTIDA INDIVIDUAL", "UNIDAD ORGANIZACIONAL", "MODALIDAD LABORAL", "UNIDAD PRESUPUESTARIA", "DENOMINACION PUESTO", "FECHA CREACION",
                    "GRUPO OCUPACIONAL", "UBICACION GEOGRAFICA");

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // cargar los datos          
            for (DistributivoDetalle dd : listaPuestos) {
                fila++;
                UtilArchivos.crearFilaExcel(
                        workbook, hoja, fila, null,
                        dd.getCodigoPuesto(),
                        dd.getPartidaIndividual(),
                        dd.getDistributivo().getUnidadOrganizacional().getNombre(),
                        dd.getDistributivo().getModalidadLaboral().getNombre(),
                        dd.getUnidadPresupuestaria().getNombreCompleto(),
                        dd.getEscalaOcupacional().getNombreCompleto(),
                        sdf.format(dd.getFechaInicio()),
                        dd.getDenominacionPuesto().getNombre(),
                        dd.getUbicacionGeografica().getNombreCompleto()
                );
            }

            //Ajustando el tamaño de cada columna al tamaño del texto que contiene
            for (int i = 0; i <= 9; i++) {
                hoja.autoSizeColumn(i);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);
            ByteArrayInputStream inStream = new ByteArrayInputStream(stream.toByteArray());
            return inStream;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error generando el EXCEL de carga masiva.");
        }
        return null;
    }
}
