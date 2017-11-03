/*
 *  RegimenLaboralServicio.java
 
 *  Quito - Ecuador
 *
 *  20/09/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.NivelOcupacionalDao;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class RegimenLaboralServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(RegimenLaboralServicio.class.getCanonicalName());

    /**
     * DAO de Régimen Laboral.
     */
    @EJB
    private RegimenLaboralDao regimenLaboralDao;

    /**
     * DAO de Régimen Laboral.
     */
    @EJB
    private NivelOcupacionalDao nivelOcupacionalDao;

    /**
     * DAO de Escala Ocupacional.
     */
    @EJB
    private EscalaOcupacionalDao escalaOcupacionalDao;

    /**
     * Permite el registro de un régimen laboral
     *
     * @param regimenLaboral registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarRegimenLaboral(final RegimenLaboral regimenLaboral, final UsuarioVO usuario) throws
            ServicioException {
        try {
            System.out.println("guardarRegimenLaboral:" + BeanUtils.describe(usuario));
            regimenLaboral.setCodigo(regimenLaboral.getCodigo().toUpperCase());
            regimenLaboral.setNombre(regimenLaboral.getNombre().toUpperCase());
            regimenLaboral.setVigente(Boolean.TRUE);
            regimenLaboral.setFechaCreacion(new Date());
            regimenLaboral.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            regimenLaboral.setId(null);
            regimenLaboralDao.crear(regimenLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un régimen laboral
     *
     * @param regimenLaboral registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarRegimenLaboral(final RegimenLaboral regimenLaboral, final UsuarioVO usuario) throws
            ServicioException {
        try {
            regimenLaboral.setCodigo(regimenLaboral.getCodigo().toUpperCase());
            regimenLaboral.setNombre(regimenLaboral.getNombre().toUpperCase());
            regimenLaboral.setFechaActualizacion(new Date());
            regimenLaboral.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
            regimenLaboralDao.actualizar(regimenLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un regimen laboral.
     *
     * @param regimenLaboral registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarRegimenLaboral(final RegimenLaboral regimenLaboral) throws ServicioException {
        try {
            regimenLaboral.setVigente(false);
            regimenLaboralDao.actualizar(regimenLaboral);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los régimenes laborales por Código.
     *
     * @param codigo String del codigo de registro para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<RegimenLaboral> listarTodosRegimenLaboralPorCodigo(final String codigo) throws ServicioException {
        try {
            List<RegimenLaboral> listaRegimenLaboralCodigo;
            listaRegimenLaboralCodigo = regimenLaboralDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaRegimenLaboralCodigo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las ubicaciones geograficas.
     *
     * @return List<RegimenLaboral>
     */
    public List<RegimenLaboral> listarTodosRegimenLaboral() throws ServicioException {
        try {
            List<RegimenLaboral> listaRegimen = new ArrayList<RegimenLaboral>();
            RegimenLaboral regimenRoot = new RegimenLaboral();
            regimenRoot.setNombre("REGIMEN LABORAL");
            regimenRoot.setDescripcion("REGIMEN LABORAL");
            regimenRoot.setCodigo("REGIMEN LABORAL");
            regimenRoot.setEsRoot(Boolean.TRUE);
            listaRegimen = regimenLaboralDao.buscarVigente();
            listaRegimen.add(0, regimenRoot);
            return listaRegimen;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los CotizacionIess vigentes
     *
     * @return List<RegimenLaboral> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<RegimenLaboral> listarTodosRegimenVigentes() throws ServicioException {
        try {
            return regimenLaboralDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar un regimen laboral por id
     *
     * @param regimenLaboralId
     * @return RegimenLaboral regimen obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public RegimenLaboral buscarRegimenLaboralPorId(Long regimenLaboralId) throws ServicioException {
        try {
            return regimenLaboralDao.buscarPorId(regimenLaboralId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de un nivel Ocupacional
     *
     * @param nivelOcupacional registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarNivelOcupacional(final NivelOcupacional nivelOcupacional) throws ServicioException {
        try {
            nivelOcupacional.setCodigo(nivelOcupacional.getCodigo().toUpperCase());
            nivelOcupacional.setNombre(nivelOcupacional.getNombre().toUpperCase());
            nivelOcupacional.setIdRegimenLaboral(nivelOcupacional.getRegimenLaboral().getId());
            nivelOcupacional.setVigente(Boolean.TRUE);
            nivelOcupacional.setEsLibreRemocion(nivelOcupacional.getEsLibreRemocion());
            nivelOcupacional.setId(null);
            nivelOcupacionalDao.crear(nivelOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar una modalidad laboral
     *
     * @param nivelOcupacional registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarNivelOcupacional(final NivelOcupacional nivelOcupacional) throws ServicioException {
        try {
            nivelOcupacional.setCodigo(nivelOcupacional.getCodigo().toUpperCase());
            nivelOcupacional.setNombre(nivelOcupacional.getNombre().toUpperCase());
            nivelOcupacional.setEsLibreRemocion(nivelOcupacional.getEsLibreRemocion());
            nivelOcupacionalDao.actualizar(nivelOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un nivel ocupacional.
     *
     * @param regimenLaboral registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarNivelOcupacional(final NivelOcupacional nivelOcupacional) throws ServicioException {
        try {
            nivelOcupacional.setVigente(false);
            nivelOcupacionalDao.actualizar(nivelOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los niveles ocupaciones por Código.
     *
     * @param codigo String del codigo de registro para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<NivelOcupacional> listarTodosNivelOcupacionalPorCodigo(final String codigo) throws ServicioException {
        try {
            List<NivelOcupacional> listaNivelOcupacionalCodigo;
            listaNivelOcupacionalCodigo = nivelOcupacionalDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaNivelOcupacionalCodigo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los niveles de un regimen laboral especifico
     *
     * @param codigoRegimenLaboral String del id de registro para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<NivelOcupacional> listarTodosNivelOcupacionalPorRegimen(final Long codigoRegimenLaboral) throws
            ServicioException {
        try {
            List<NivelOcupacional> lista;
            List<NivelOcupacional> listaRegimen = new ArrayList<NivelOcupacional>();
            lista = nivelOcupacionalDao.buscarVigente();
            for (NivelOcupacional escala : lista) {
                if (codigoRegimenLaboral == null || escala.getRegimenLaboral().getId().equals(codigoRegimenLaboral)) {
                    listaRegimen.add(escala);
                }
            }

            return listaRegimen;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<NivelOcupacional> listarTodosPorRegimenLaboralId(Long regimenId) throws ServicioException {
        try {
            return nivelOcupacionalDao.buscarTodosPorIdRegiem(regimenId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los niveles ocupacionales vigentes
     *
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<NivelOcupacional> listarTodosNivelOcupacionalVigentes() throws ServicioException {
        try {
            List<NivelOcupacional> lista;
            lista = nivelOcupacionalDao.buscarVigente();
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de una escala Ocupacional
     *
     * @param escalaOcupacional registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarEscalaOcupacional(final EscalaOcupacional escalaOcupacional) throws ServicioException {
        try {
            escalaOcupacional.setCodigo(escalaOcupacional.getCodigo().toUpperCase());
            escalaOcupacional.setNombre(escalaOcupacional.getNombre().toUpperCase());
            escalaOcupacional.setVigente(Boolean.TRUE);
            escalaOcupacional.setId(null);
            escalaOcupacional.setIdNivelOcupacional(escalaOcupacional.getNivelOcupacional().getId());
            escalaOcupacionalDao.crear(escalaOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar una escala ocupacional
     *
     * @param escalaOcupacional registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarEscalaOcupacional(final EscalaOcupacional escalaOcupacional) throws ServicioException {
        try {
            escalaOcupacional.setCodigo(escalaOcupacional.getCodigo().toUpperCase());
            escalaOcupacional.setNombre(escalaOcupacional.getNombre().toUpperCase());
            escalaOcupacionalDao.actualizar(escalaOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de una escala ocupacional
     *
     * @param regimenLaboral registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarEscalaOcupacional(final EscalaOcupacional escalaOcupacional) throws ServicioException {
        try {
            escalaOcupacional.setVigente(false);
            escalaOcupacionalDao.actualizar(escalaOcupacional);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar las escalas ocupaciones por Código.
     *
     * @param codigo String del codigo de registro para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<EscalaOcupacional> listarTodosEscalaOcupacionalPorCodigo(final String codigo) throws ServicioException {
        try {
            List<EscalaOcupacional> listaEscalaOcupacionalCodigo;
            listaEscalaOcupacionalCodigo = escalaOcupacionalDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaEscalaOcupacionalCodigo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar las escalas ocupacionales de un NIvel Ocupacional especifico
     *
     * @param codigoNivelOcupacional String del id de registro para la busqueda
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<EscalaOcupacional> listarTodosEscalaOcupacionalPorNivelOcup(final Long codigoNivelOcupacional) throws
            ServicioException {
        try {
            List<EscalaOcupacional> lista;
            List<EscalaOcupacional> listaEscalas = new ArrayList<EscalaOcupacional>();
            lista = escalaOcupacionalDao.buscarVigente();
            for (EscalaOcupacional escala : lista) {
                if (codigoNivelOcupacional == null || escala.getNivelOcupacional().getId().equals(codigoNivelOcupacional)) {
                    listaEscalas.add(escala);
                }
            }
            return listaEscalas;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar las escalas ocupacionales vigentes
     *
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<EscalaOcupacional> listarTodosEscalaOcupacionalVigentes() throws ServicioException {
        try {
            return escalaOcupacionalDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public NivelOcupacional buscarNivelOcupacional(final Long id) throws ServicioException {
        try {
            return nivelOcupacionalDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws ServicioException
     */
    public EscalaOcupacional buscarEscalaOcupacional(final Long id) throws ServicioException {
        try {
            return escalaOcupacionalDao.buscarPorId(id);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }
}
