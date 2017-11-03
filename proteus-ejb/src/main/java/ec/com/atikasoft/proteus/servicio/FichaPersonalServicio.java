/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.EscalaOcupacionalDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.NivelOcupacionalDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoDao;
import ec.com.atikasoft.proteus.dao.ReclutamientoTrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.dao.ServidorCapacitacionDao;
import ec.com.atikasoft.proteus.dao.ServidorCargaFamiliarDao;
import ec.com.atikasoft.proteus.dao.ServidorExperienciaDao;
import ec.com.atikasoft.proteus.dao.ServidorInformacionMedicaDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.ServidorInstruccionDao;
import ec.com.atikasoft.proteus.dao.ServidorParienteInstitucionDao;
import ec.com.atikasoft.proteus.dao.servidorEvaluacionDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.Reclutamiento;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.ServidorCargaFamiliar;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorEvaluacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInformacionMedica;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInstruccion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorParienteInstitucion;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class FichaPersonalServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(FichaPersonalServicio.class.getCanonicalName());

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
     * Dao de servidor por institucion.
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private ReclutamientoTrayectoriaLaboralDao reclutamientoTrayectoriaLaboralDao;

    /**
     * **
     * BORRAR TODOS LOS DAOS ANTERIORES DESPUES DE CORREGUIR TODO
     */
    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private ServidorCapacitacionDao servidorCapacitacionDao;
    /**
     * **
     * servidor cargas familiares
     */
    @EJB
    private ServidorCargaFamiliarDao servidorCargaFamiliarDao;
    /**
     * dao reclutamientoCapacitacionDao.
     */
    @EJB
    private servidorEvaluacionDao servidorEvaluacionDao;
    /**
     * dao servidor experiencia.
     */
    @EJB
    private ServidorExperienciaDao servidorExperienciaDao;
    /**
     * dao servidor experiencia.
     */
    @EJB
    private ServidorInstruccionDao servidorInstruccionDao;
    /**
     * dao servidor ServidorParienteInstitucionDao.
     */
    @EJB
    private ServidorParienteInstitucionDao servidorParienteInstitucionDao;
    /**
     * dao servidor servidorInformacionMedicaDao.
     */
    @EJB
    private ServidorInformacionMedicaDao servidorInformacionMedicaDao;
//
//    /**
//     * Este metodo busca los movimientos por Id.
//     *
//     * @param nombre
//     * @param numeroIdentificacion
//     * @param id Long
//     * @return Movimiento
//     * @throws ServicioException ServicioException
//     */
//    public List<Reclutamiento> buscarServidorPorNombreYIdentificacion(final String nombre, final String numeroIdentificacion)
//            throws ServicioException {
//        try {
//            List<Reclutamiento> reclutamiento = new ArrayList<Reclutamiento>();
//            reclutamiento = reclutamientoDao.buscarPorNombreYIdentificacion(nombre.toUpperCase(), numeroIdentificacion);
//            return reclutamiento;
//        } catch (DaoException e) {
//            throw new ServicioException(e);
//        }
//    }
//
//    /**
//     * Este metodo busca los movimientos por Id.
//     *
//     * @param id Long
//     * @return Movimiento
//     * @throws ServicioException ServicioException
//     */
//    public Reclutamiento buscarServidorPorTipoDocumento(final String tipoDocumento) throws ServicioException {
//        try {
//            Reclutamiento reclutamiento = new Reclutamiento();
//            if (tipoDocumento != null) {
//                reclutamiento = reclutamientoDao.buscarPorTipoIdentificacion(tipoDocumento);
//            }
//            return reclutamiento;
//        } catch (Exception e) {
//            throw new ServicioException(e);
//        }
//    }

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
    public List<RegimenLaboral> listarTodosRegimenesLaborales() throws ServicioException {
        try {
            List<RegimenLaboral> listaRegimenLaboral = regimenLaboralDao.buscarVigente();
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
     * Metodo que se encarga de buscar el nivel ocupacional por el id de regimen
     * laboral.
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
            List<EscalaOcupacional> listaEscalaOcupacional = escalaOcupacionalDao.
                    buscarTodosPorNivelOcupacionalId(nivelocupacionalId);
            return listaEscalaOcupacional;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * *********************SERVIDOR CARGAS FAMILIARES*************
     */
    /**
     * Metodo que se encarga de buscar los cargas familiares.
     *
     * @param servidorId
     * @return List<ServidorCargaFamiliar>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorCargaFamiliar> listarTodosCargaFamiliar(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorCargaFamiliar> listaCargaFamiliares = servidorCargaFamiliarDao.
                    buscarPorServidorId(servidorId);
            return listaCargaFamiliares;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea una cargas familiares.
     *
     * @param a
     * @throws ServicioException ServicioException
     */
    public void guardarServidorCargaFamiliar(final ServidorCargaFamiliar a) throws ServicioException {
        try {
            a.setNombres(a.getNombres().toUpperCase());
            servidorCargaFamiliarDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de una cargas familiares.
     *
     * @param a ServidorCargaFamiliar
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorCargaFamiliar(final ServidorCargaFamiliar a) throws ServicioException {
        try {
            a.setNombres(a.getNombres().toUpperCase());
            servidorCargaFamiliarDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una cargas familiares
     * capacitacion.
     *
     * @param a ServidorCargaFamiliar
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorCargaFamiliar(final ServidorCargaFamiliar a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorCargaFamiliarDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR CAPACITACION*************
     */
    /**
     * Metodo que se encarga de buscar los ReclutamientoInstruccion.
     *
     * @param servidorId
     * @return List<ReclutamientoInstruccion>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorCapacitacion> listarTodosReclutamientoCapacitacionPorIdReclutado(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorCapacitacion> listaReclutamientoCapacitaciones = servidorCapacitacionDao.
                    buscarPorServidorId(servidorId);
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
    public void guardarServidorCapacitacion(final ServidorCapacitacion a) throws ServicioException {
        try {
            servidorCapacitacionDao.crear(a);
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
    public void actualizarServidorCapacitacion(final ServidorCapacitacion a) throws ServicioException {
        try {
            servidorCapacitacionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de una reclutamiento
     * capacitacion.
     *
     * @param a ReclutamientoCapacitacion
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorCapacitacion(final ServidorCapacitacion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorCapacitacionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR EVALUACION*************
     */
    /**
     * Metodo que se encarga de buscar los ReclutamientoInstruccion.
     *
     * @param servidorId
     * @return List<ReclutamientoTrayectoriaLaboral>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorEvaluacion> listarTodosServidorEvaluacionPorIdServidor(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorEvaluacion> listaServidorEvaluaciones = servidorEvaluacionDao.
                    buscarPorServidorId(servidorId);
            return listaServidorEvaluaciones;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void guardarServidorEvaluacion(final ServidorEvaluacion a) throws ServicioException {
        try {
            servidorEvaluacionDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorEvaluacion(final ServidorEvaluacion a) throws ServicioException {
        try {
            servidorEvaluacionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un ServidorEvaluacion
     * capacitacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorEvaluacion(final ServidorEvaluacion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorEvaluacionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR EXPERIENCIA*************
     */
    /**
     * Metodo que se encarga de buscar los ServidorExperiencia.
     *
     * @param servidorId
     * @return List<ServidorExperiencia>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorExperiencia> listarTodosServidorExperienciaPorIdServidor(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorExperiencia> listaServidorExperiencia = servidorExperienciaDao.
                    buscarPorServidorId(servidorId);
            return listaServidorExperiencia;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void guardarServidorExperiencia(final ServidorExperiencia a) throws ServicioException {
        try {
            servidorExperienciaDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorExperiencia(final ServidorExperiencia a) throws ServicioException {
        try {
            servidorExperienciaDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un ServidorEvaluacion
     * capacitacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorExperiencia(final ServidorExperiencia a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorExperienciaDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR INSTRUCCION*************
     */
    /**
     * Metodo que se encarga de buscar los ServidorExperiencia.
     *
     * @param servidorId
     * @return List<ServidorExperiencia>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorInstruccion> listarTodosServidorInstruccionPorIdServidor(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorInstruccion> listaServidorInstruccionsInstrucciones = servidorInstruccionDao.
                    buscarPorServidorId(servidorId);
            return listaServidorInstruccionsInstrucciones;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void guardarServidorInstruccion(final ServidorInstruccion a) throws ServicioException {
        try {
            servidorInstruccionDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorInstruccion(final ServidorInstruccion a) throws ServicioException {
        try {
            servidorInstruccionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un ServidorEvaluacion
     * capacitacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorInstruccion(final ServidorInstruccion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorInstruccionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR PARIENTE INSTITUCIÓN*************
     */
    /**
     * Metodo que se encarga de buscar los ServidorExperiencia.
     *
     * @param servidorId
     * @return List<ServidorExperiencia>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorParienteInstitucion> listarTodosServidorParientesPorIdServidor(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorParienteInstitucion> listaServidorParienteInstituciones = servidorParienteInstitucionDao.
                    buscarPorServidorId(servidorId);
            return listaServidorParienteInstituciones;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void guardarServidorParientes(final ServidorParienteInstitucion a) throws ServicioException {
        try {
            servidorParienteInstitucionDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de un ServidorEvaluacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorParientes(final ServidorParienteInstitucion a) throws ServicioException {
        try {
            servidorParienteInstitucionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un ServidorEvaluacion
     * capacitacion.
     *
     * @param a ReclutamientoTrayectoriaLaboral
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorParientes(final ServidorParienteInstitucion a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorParienteInstitucionDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * *********************SERVIDOR INFORMACION MEDICA*************
     */
    /**
     * Metodo que se encarga de buscar los ServidorExperiencia.
     *
     * @param servidorId
     * @return List<ServidorInformacionMedica>
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<ServidorInformacionMedica> listarTodosServidorInformacionMedicaPorIdServidor(
            final Long servidorId) throws ServicioException {
        try {
            List<ServidorInformacionMedica> listaServidorInformacionMedicas = servidorInformacionMedicaDao.
                    buscarPorServidorId(servidorId);
            return listaServidorInformacionMedicas;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método crea un ServidorEvaluacion.
     *
     * @param a ServidorInformacionMedica
     * @throws ServicioException ServicioException
     */
    public void guardarServidorInformacionMedica(final ServidorInformacionMedica a) throws ServicioException {
        try {
            servidorInformacionMedicaDao.crear(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza un registro de un ServidorEvaluacion.
     *
     * @param a ServidorInformacionMedica
     * @throws ServicioException ServicioException
     */
    public void actualizarServidorInformacionMedica(final ServidorInformacionMedica a) throws ServicioException {
        try {
            servidorInformacionMedicaDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Esté método transacciona la eliminación de un ServidorEvaluacion
     * capacitacion.
     *
     * @param a ServidorInformacionMedica
     * @throws ServicioException ServicioException
     */
    public void eliminarServidorInformacionMedica(final ServidorInformacionMedica a) throws ServicioException {
        try {
            a.setVigente(Boolean.FALSE);
            servidorInformacionMedicaDao.actualizar(a);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
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
    public void setServidorInstitucionDao(final ServidorInstitucionDao servidorInstitucionDao) {
        this.servidorInstitucionDao = servidorInstitucionDao;
    }

    /**
     * @return the servidorCargaFamiliarDao
     */
    public ServidorCargaFamiliarDao getServidorCargaFamiliarDao() {
        return servidorCargaFamiliarDao;
    }

    /**
     * @param servidorCargaFamiliarDao the servidorCargaFamiliarDao to set
     */
    public void setServidorCargaFamiliarDao(ServidorCargaFamiliarDao servidorCargaFamiliarDao) {
        this.servidorCargaFamiliarDao = servidorCargaFamiliarDao;
    }
}
