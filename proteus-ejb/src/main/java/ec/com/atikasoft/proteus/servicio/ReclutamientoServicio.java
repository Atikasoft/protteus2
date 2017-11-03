/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.NivelOcupacionalDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoCapacitacionDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoInstruccionDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoTrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.enums.ArchivosReclutamientoMasivoEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Reclutamiento;
import ec.com.atikasoft.proteus.modelo.ReclutamientoCapacitacion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoInstruccion;
import ec.com.atikasoft.proteus.modelo.ReclutamientoTrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class ReclutamientoServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(ReclutamientoServicio.class.getCanonicalName());

    /**
     * DAO de escalaOcupacionalDao
     */
    @EJB
    private EscalaOcupacionalDao escalaOcupacionalDao;

    /**
     * DAO de Distributivo
     */
    @EJB
    private NivelOcupacionalDao nivelOcupacionalDao;

    /**
     * DAO de Distributivo
     */
    @EJB
    private ReclutamientoDao reclutamientoDao;

    /**
     * dao regimen laboral.
     */
    @EJB
    private RegimenLaboralDao regimenLaboralDao;

    /**
     * dao modalidadLaboralDao.
     */
    @EJB
    private ModalidadLaboralDao modalidadLaboralDao;

    /**
     * dao reclutamientoInstruccionDao.
     */
    @EJB
    private ReclutamientoInstruccionDao reclutamientoInstruccionDao;

    /**
     * Dao de servidor por institucion.
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private ReclutamientoCapacitacionDao reclutamientoCapacitacionDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private ReclutamientoTrayectoriaLaboralDao reclutamientoTrayectoriaLaboralDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private DistributivoPuestoServicio distributiboPuestoDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param nombre
     * @param numeroIdentificacion
     * @param id Long
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public List<Reclutamiento> buscarServidorPorNombreYIdentificacion(
            final String nombre,
            final String numeroIdentificacion)
            throws ServicioException {
        try {
            List<Reclutamiento> reclutamiento = new ArrayList<Reclutamiento>();
            reclutamiento = reclutamientoDao.buscarPorNombreYIdentificacion(nombre.toUpperCase(), numeroIdentificacion);
            return reclutamiento;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param documento número de documento
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Reclutamiento buscarServidorPorDocumento(final String documento) throws ServicioException {
        try {
            Reclutamiento reclutamiento = new Reclutamiento();
            if (documento != null) {
                reclutamiento = reclutamientoDao.buscarPorIdentificacion(documento);
            }
            return reclutamiento;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param numeroIdentificacion String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Reclutamiento> listarPorNumeroIdentificacion(final String numeroIdentificacion) throws ServicioException {
        try {
            List<Reclutamiento> listaReglasNemonico;
            listaReglasNemonico = reclutamientoDao.buscarPorNumeroIdentificacion(numeroIdentificacion);
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo transacciona la busqueda de parametro por nemonico.
     *
     * @param escala
     * @param modalidad
     * @param unidad
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<Reclutamiento> listarPorEscalaModalidadUnidad(final Long escala,
            final Long modalidad, final Long unidad) throws ServicioException {
        try {
            List<Reclutamiento> listaReglasNemonico;
            listaReglasNemonico = reclutamientoDao.buscarPorEscalaModalidadUnidad(escala, modalidad, unidad);
            return listaReglasNemonico;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public List<Reclutamiento> buscarServidorPorNombre(final String nombre) throws ServicioException {
        try {
            List<Reclutamiento> servidor = new ArrayList<Reclutamiento>();
            if (nombre != null) {
                servidor = reclutamientoDao.buscarPorNombre(nombre.toUpperCase());
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los regiemens laborales.
     *
     * @return List<RegimenLaboral>
     */
    public List<RegimenLaboral> listarTodosRegimenesLaborales(final Long regimenId) throws ServicioException {
        try {
            List<RegimenLaboral> listaRegimenLaboral = regimenLaboralDao.buscarVigente();
            if (!listaRegimenLaboral.isEmpty() && regimenId != null) {
                listaRegimenLaboral.add(regimenLaboralDao.buscarPorId(regimenId));
            }
            return listaRegimenLaboral;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los modalidades laborales.
     *
     * @return List<ModalidadLaboral>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ModalidadLaboral> listarTodosModalidadLaboral() throws ServicioException {
        try {
            List<ModalidadLaboral> listaModalidadLaboral = modalidadLaboralDao.buscarVigente();
            return listaModalidadLaboral;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el nivel ocupacional por el id de regimen laboral.
     *
     * @return List<NivelOcupacional>
     */
    public List<NivelOcupacional> listarTodosNivelesOcupacional(Long regimenLaboralId) throws ServicioException {
        try {
            List<NivelOcupacional> listaNivelOcupacional = nivelOcupacionalDao.buscarTodosPorIdRegiem(regimenLaboralId);
            return listaNivelOcupacional;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar el escalas ocupacionales.
     *
     * @param nivelocupacionalId
     * @return List<EscalaOcupacional>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<EscalaOcupacional> listarTodosEscalaOcupacional(Long nivelocupacionalId) throws ServicioException {
        try {
            List<EscalaOcupacional> listaEscalaOcupacional = escalaOcupacionalDao.buscarTodosPorNivelOcupacionalId(
                    nivelocupacionalId);
            return listaEscalaOcupacional;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea una reclutamientos.
     *
     * @param a
     * @throws ServicioException ServicioException
     */
    public void guardarReclutamiento(final Reclutamiento a) throws ServicioException {
        try {
            a.setApellidosPaterno(a.getApellidosPaterno().toUpperCase());
            a.setNombres(a.getNombres().toUpperCase());
            a.setApellidoNombre(UtilCadena.concatenar(a.getApellidosPaterno(), " ", a.getNombres()).toUpperCase());
            a.setApellidosNombres(UtilCadena.concatenar(a.getApellidosPaterno(), " ", a.getNombres()).toUpperCase());
            reclutamientoDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param lineas
     * @param estado
     * @param usuarioVO
     * @throws ServicioException
     */
    public void guardarReclutamientosMasivamente(final Map<String, List<String[]>> lineas, final String estado,
            final UsuarioVO usuarioVO, final boolean todos) throws ServicioException {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(usuarioVO.
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            List<String[]> lineasRDG = lineas.get(
                    ArchivosReclutamientoMasivoEnum.ARCHIVO_RDG.getCodigo());
            List<String[]> lineasRC = lineas.get(
                    ArchivosReclutamientoMasivoEnum.ARCHIVO_RC.getCodigo());
            List<String[]> lineasRI = lineas.get(
                    ArchivosReclutamientoMasivoEnum.ARCHIVO_RI.getCodigo());
            List<String[]> lineasRTL = lineas.get(
                    ArchivosReclutamientoMasivoEnum.ARCHIVO_RTL.getCodigo());

            SimpleDateFormat sdfHorasMinutos = new SimpleDateFormat("HH:mm");

            for (String[] lineaRDG : lineasRDG) {

//                System.out.println("Dato general: " + Arrays.toString(lineaRDG));
                BusquedaPuestoVO busquedaPuesto = new BusquedaPuestoVO();
                busquedaPuesto.setCodigoPuesto(Long.valueOf(lineaRDG[0]));
                DistributivoDetalle dd = distributiboPuestoDao.buscar(busquedaPuesto, false, usuarioVO, todos).get(0);
                Reclutamiento r = new Reclutamiento();
                r.setFechaCreacion(new Date());
                r.setEstado(estado);
                r.setUsuarioCreacion(usuarioVO.getUsuario());
                r.setVigente(Boolean.TRUE);
                r.setDistributivoDetalle(dd);

                r.setInstitucionId(
                        dd.getDistributivo().getInstitucionEjercicioFiscal().getInstitucion().getId());
                r.setEscalaOcupacionalId(dd.getIdEscalaOcupacional());
                r.setModalidadLaboralId(
                        dd.getDistributivo().getModalidadLaboral().getId());
                r.setUnidadOrganizacionalId(
                        dd.getDistributivo().getUnidadOrganizacional().getId());

                r.setTipoIdentificacion(lineaRDG[1]);
                r.setNumeroIdentificacion(lineaRDG[2]);
                r.setApellidosPaterno(lineaRDG[3]);
                r.setApellidosMaterno(lineaRDG[4]);
                r.setNombres(lineaRDG[5]);
                r.setApellidoNombre(UtilCadena.concatenar(
                        r.getApellidosPaterno(), " ",
                        r.getApellidosMaterno(), " ",
                        r.getNombres()));
                r.setApellidosNombres(UtilCadena.concatenar(
                        r.getApellidosPaterno(), " ",
                        r.getApellidosMaterno(), " ",
                        r.getNombres()));

                r.setCatalogoGeneroId(
                        administracionServicio.buscarCatalogoPorCodigoYTipoCatalogo(
                                lineaRDG[6], TipoCatalogoEnum.GENERO.getCodigo()).getId());

                r.setTelefono(lineaRDG[7]);
                r.setCelular(lineaRDG[8]);
                r.setMail(lineaRDG[9]);
                r.setHoraEntrada(sdfHorasMinutos.parse(lineaRDG[10]));
                r.setJornada(Integer.valueOf(lineaRDG[11]));
                r.setCallePrincipal(lineaRDG[12]);
                r.setCalleSecundaria(lineaRDG[13]);
                r.setReferenciaDomicilio(lineaRDG[14]);
                r.setNumeroDomicilio(lineaRDG[15]);
                r.setPersonaConDiscapacidad(Boolean.valueOf(lineaRDG[16]));
                r.setNumeroCarnetConadis(lineaRDG[17]);
                r.setCriterioTecnico(lineaRDG[18]);
                r.setRecomendaciones(lineaRDG[19]);
                r.setElaboradoPor(lineaRDG[20]);
                r.setCargoElaboradoPor(lineaRDG[21]);
                r.setAprobadoPor(lineaRDG[22]);
                r.setCargoAprobadoPor(lineaRDG[23]);

                reclutamientoDao.crear(r);
                reclutamientoDao.flush();

                if (lineasRC != null && !lineasRC.isEmpty()) {
                    guardarReclutamientoCapacitacionMasivamente(r, lineasRC);
                }
                if (lineasRC != null && !lineasRC.isEmpty()) {
                    guardarReclutamientoInstruccionMasivamente(r, lineasRI);
                }
                if (lineasRC != null && !lineasRC.isEmpty()) {
                    guardarReclutamientoTrayectoriaLaboralMasivamente(r, lineasRTL);
                }
            }

        } catch (DaoException | ParseException ex) {
            throw new ServicioException(ex);
        }
    }

    private void guardarReclutamientoCapacitacionMasivamente(
            Reclutamiento r, List<String[]> lineasRC) throws DaoException {

        for (String[] lRC : lineasRC) {
            if (r.getTipoIdentificacion().equals(lRC[0])
                    && r.getNumeroIdentificacion().equals(lRC[1])) {

//                System.out.println("\tReclutamientoCapacitacion: " + Arrays.toString(lRC));
                ReclutamientoCapacitacion rc = new ReclutamientoCapacitacion();
                rc.setUsuarioCreacion(r.getUsuarioCreacion());
                rc.setFechaCreacion(r.getFechaCreacion());
                rc.setVigente(Boolean.TRUE);
                rc.setReclutamiento(r);
                rc.setReclutamientoId(r.getId());

                rc.setNombreEvento(lRC[2]);
                rc.setNombreInstitucion(lRC[3]);
                rc.setTipoDiploma(lRC[4]);
                rc.setDuracionHoras(Long.valueOf(lRC[5]));

                reclutamientoCapacitacionDao.crear(rc);
            }
        }
    }

    private void guardarReclutamientoInstruccionMasivamente(
            Reclutamiento r, List<String[]> lineasRI) throws DaoException {
        for (String[] lRI : lineasRI) {
            if (r.getTipoIdentificacion().equals(lRI[0])
                    && r.getNumeroIdentificacion().equals(lRI[1])) {

//                System.out.println("\tReclutamientoInstruccion: " + Arrays.toString(lRI));
                ReclutamientoInstruccion ri = new ReclutamientoInstruccion();
                ri.setUsuarioCreacion(r.getUsuarioCreacion());
                ri.setFechaCreacion(r.getFechaCreacion());
                ri.setVigente(Boolean.TRUE);
                ri.setReclutamiento(r);
                ri.setReclutamientoId(r.getId());

                ri.setNivelInstruccionId(
                        administracionServicio.buscarCatalogoPorCodigoYTipoCatalogo(
                                lRI[2], TipoCatalogoEnum.TIPO_INSTRUCCION.getCodigo()).getId());

                ri.setNombreInstitucion(lRI[3]);
                ri.setEspecializacion(lRI[4]);
                ri.setTituloObtenido(lRI[5]);

                reclutamientoInstruccionDao.crear(ri);
            }
        }
    }

    private void guardarReclutamientoTrayectoriaLaboralMasivamente(
            Reclutamiento r, List<String[]> lineasRTL) throws DaoException, ParseException {

        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy");
        for (String[] lRTL : lineasRTL) {
            if (r.getTipoIdentificacion().equals(lRTL[0])
                    && r.getNumeroIdentificacion().equals(lRTL[1])) {

//                System.out.println("\tReclutamientoTrayectoriaLaboral: " + Arrays.toString(lRTL));
                ReclutamientoTrayectoriaLaboral rtl = new ReclutamientoTrayectoriaLaboral();
                rtl.setUsuarioCreacion(r.getUsuarioCreacion());
                rtl.setFechaCreacion(r.getFechaCreacion());
                rtl.setVigente(Boolean.TRUE);
                rtl.setReclutamiento(r);
                rtl.setReclutamientoId(r.getId());

                rtl.setFechaInicio(sdfFecha.parse(lRTL[2]));
                rtl.setFechaFin(sdfFecha.parse(lRTL[3]));
                rtl.setMesTrayectoria(Integer.valueOf(lRTL[4]).longValue());
                rtl.setOrganizacionEmpresa(lRTL[5]);
                rtl.setDenominacionPuesto(lRTL[6]);
                rtl.setResponsabilidades(lRTL[7]);
                rtl.setRazonSalida(lRTL[8]);

                reclutamientoTrayectoriaLaboralDao.crear(rtl);
            }
        }
    }

    /**
     * Este método actualiza un registro de una reclutamiento.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void actualizarReclutamiento(final Reclutamiento a) throws ServicioException {
        try {
//            a.setApellidosMaterno(a.getApellidosMaterno().toUpperCase());
            a.setApellidosPaterno(a.getApellidosPaterno().toUpperCase());
            a.setNombres(a.getNombres().toUpperCase());
            a.setApellidoNombre(
                    UtilCadena.concatenar(a.getApellidosPaterno(),
                            " ", a.getNombres()).toUpperCase());
            a.setApellidosNombres(
                    UtilCadena.concatenar(a.getApellidosPaterno(),
                            " ", a.getNombres()).toUpperCase());
            reclutamientoDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una alerta.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void eliminarReclutamiento(final Reclutamiento a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            reclutamientoDao.actualizar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método crea una reclutamientos.
     *
     * @param a
     * @throws ServicioException ServicioException
     */
    public void guardarReclutamientoInstruccion(
            final ReclutamientoInstruccion a) throws ServicioException {
        try {
//            a.setNombreInstitucion(a.getNombreInstitucion().toUpperCase());
            reclutamientoInstruccionDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de una reclutamiento.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void actualizarReclutamientoInstruccion(
            final ReclutamientoInstruccion a) throws ServicioException {
        try {
            a.setNombreInstitucion(a.getNombreInstitucion().toUpperCase());
            reclutamientoInstruccionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una reclutamientoInstruccionDao capacitacion.
     *
     * @param a reclutamientoInstruccion
     * @throws ServicioException ServicioException
     */
    public void eliminarReclutamientoInstruccion(
            final ReclutamientoInstruccion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            reclutamientoInstruccionDao.actualizar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los ReclutamientoInstruccion.
     *
     * @param reclutamientoId
     * @return List<ReclutamientoInstruccion>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ReclutamientoInstruccion> listarTodosReclutamientoInstruccionPorIdReclutado(
            final Long reclutamientoId) throws ServicioException {
        try {
            List<ReclutamientoInstruccion> listaReclutamientoInstrucciones = reclutamientoInstruccionDao.
                    buscarPorReclutamientoId(reclutamientoId);
            return listaReclutamientoInstrucciones;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar los ReclutamientoInstruccion.
     *
     * @param reclutamientoId
     * @return List<ReclutamientoInstruccion>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ReclutamientoCapacitacion> listarTodosReclutamientoCapacitacionPorIdReclutado(
            final Long reclutamientoId) throws ServicioException {
        try {
            List<ReclutamientoCapacitacion> listaReclutamientoCapacitaciones = reclutamientoCapacitacionDao.
                    buscarPorReclutamientoId(reclutamientoId);
            return listaReclutamientoCapacitaciones;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea una reclutamientos.
     *
     * @param a
     * @throws ServicioException ServicioException
     */
    public void guardarReclutamientoCapacitacion(
            final ReclutamientoCapacitacion a) throws ServicioException {
        try {
            reclutamientoCapacitacionDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de una reclutamiento.
     *
     * @param a Alerta
     * @throws ServicioException ServicioException
     */
    public void actualizarReclutamientoCapacitacion(
            final ReclutamientoCapacitacion a) throws ServicioException {
        try {
            reclutamientoCapacitacionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una reclutamiento capacitacion.
     *
     * @param a ReclutamientoCapacitacion
     * @throws ServicioException ServicioException
     */
    public void eliminarReclutamientoCapacitacion(
            final ReclutamientoCapacitacion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            reclutamientoCapacitacionDao.actualizar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los ReclutamientoInstruccion.
     *
     * @param reclutamientoId
     * @return List<ReclutamientoTrayectoriaLaboral>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ReclutamientoTrayectoriaLaboral> listarTodosReclutamientoTrayectoriaPorIdReclutado(
            final Long reclutamientoId) throws ServicioException {
        try {
            List<ReclutamientoTrayectoriaLaboral> listaReclutamientoTrayectoriaLaborals
                    = reclutamientoTrayectoriaLaboralDao.buscarPorReclutamientoId(reclutamientoId);
            return listaReclutamientoTrayectoriaLaborals;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea una reclutamientos.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void guardarReclutamientoTrayectoria(
            final ReclutamientoTrayectoriaLaboral a) throws ServicioException {
        try {
            reclutamientoTrayectoriaLaboralDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de una reclutamiento.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void actualizarReclutamientoTrayectoria(
            final ReclutamientoTrayectoriaLaboral a) throws ServicioException {
        try {
            reclutamientoTrayectoriaLaboralDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una reclutamiento capacitacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void eliminarReclutamientoTrayectoria(
            final ReclutamientoTrayectoriaLaboral a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            reclutamientoTrayectoriaLaboralDao.actualizar(a);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo para controlar los contraint de las tablas.
     *
     * @param idTablaPadreNombre
     * @param nombreTablaPadre
     * @param nombreTablaHija
     * @param id
     * @return
     * @throws ServicioException
     */
    public Boolean tieneRestricciones(
            final String idTablaPadreNombre, final String nombreTablaPadre,
            final String nombreTablaHija, final Long id) throws ServicioException {
        try {
            StringBuilder sql = new StringBuilder("SELECT th.id FROM ");
            sql.append(nombreTablaPadre);
            sql.append(" tp,");
            sql.append(nombreTablaHija);
            sql.append(" th WHERE th.vigente=true AND tp.id=th.");
            sql.append(idTablaPadreNombre);
            sql.append(" AND tp.id = ");
            sql.append(id);
            //parametroGlobalDao se lo usa solo para llamar al metodo getEntityManager.
            Query query = parametroGlobalDao.getEntityManager().createQuery(sql.toString());

            Boolean resultado = Boolean.FALSE;
            if (!(query.getResultList().isEmpty())) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo para controlar los contraint de las tablas.
     *
     * @param nombreTablaPadre
     * @param nombreTablaHija
     * @param id
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public Boolean tieneRestricciones1(final String nombreTablaPadre,
            final String nombreTablaHija, final Long id) throws ServicioException {
        try {
            StringBuilder sql = new StringBuilder("SELECT th FROM ");
            sql.append(nombreTablaHija);
            sql.append(" th WHERE th.vigente=true AND th.");
            sql.append(nombreTablaPadre);
            sql.append(".id = ");
            sql.append(id);
            //parametroGlobalDao se lo usa solo para llamar al metodo getEntityManager.
            Query query = parametroGlobalDao.getEntityManager().createQuery(sql.toString());
            Boolean resultado = Boolean.FALSE;
            if (!(query.getResultList().isEmpty())) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros a través de los atributos: tipo de doc.,numero de doc., modalidad
     * laboral,escala ocupacional, unidad organizacional, institucion,estado = R
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param idModalidad
     * @param idEscalaOcupacional
     * @param idUnidadOrgan
     * @param idInstitucion
     * @param estado
     * @return List
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Reclutamiento> listarReclutamientoPorDatosMovimientoPersonal(
            final String tipoIdentificacion, final String numeroIdentificacion,
            final Long idModalidad, final Long idEscalaOcupacional,
            final Long idUnidadOrgan, final Long idInstitucion,
            final String estado) throws
            ServicioException {
        try {
            List<Reclutamiento> lista = reclutamientoDao.buscarPorDatosMovimientoPersonal(
                    tipoIdentificacion, numeroIdentificacion, idModalidad, idEscalaOcupacional,
                    idUnidadOrgan, idInstitucion, estado);

            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite buscar por id de distributivo detalle
     *
     * @param distributivoDetalleId
     * @return
     * @throws ServicioException
     */
    public Reclutamiento buscarPorDistributivo(
            final Long distributivoDetalleId) throws ServicioException {
        try {
            return reclutamientoDao.buscar(distributivoDetalleId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite buscar registro por id de distributivo detalle y estado
     *
     * @param distributivoDetalleId
     * @param estado
     * @return
     * @throws ServicioException
     */
    public Reclutamiento buscarPorDistributivoYEstado(
            final Long distributivoDetalleId, final String estado) throws
            ServicioException {
        try {
            return reclutamientoDao.buscar(distributivoDetalleId, estado);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite buscar registro por tipo y numero de identificacion
     *
     * @param tipo
     * @param numero
     * @param estado
     * @return
     * @throws ServicioException
     */
    public Reclutamiento buscarPorIdentificacionYEstado(
            final String tipo, final String numero, final String estado)
            throws ServicioException {
        try {
            return reclutamientoDao.buscar(tipo, numero, estado);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * @return the servidorInstitucionDao
     */
    public ServidorInstitucionDao getServidorInstitucionDao() {
        return servidorInstitucionDao;
    }

    /**
     * @param servidorInstitucionDao the servidorInstitucionDao to set
     */
    public void setServidorInstitucionDao(
            final ServidorInstitucionDao servidorInstitucionDao) {
        this.servidorInstitucionDao = servidorInstitucionDao;
    }
}
