
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.PersonalOtrasInstituciones;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Leydis Garzón
 */
@LocalBean
@Stateless
public class PersonalOtrasInstitucionesDao extends GenericDAO<PersonalOtrasInstituciones, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PersonalOtrasInstitucionesDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public PersonalOtrasInstitucionesDao() {
        super(PersonalOtrasInstituciones.class);
    }

    /**
     * Este metodo procesa la busqueda por identificacion.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public PersonalOtrasInstituciones buscar(final String tipoIdentificacion, final String numeroIdentificacion) 
            throws DaoException {
        try {
            PersonalOtrasInstituciones persona = 
                    (PersonalOtrasInstituciones) buscarUnicoPorConsultaNombrada(
                            PersonalOtrasInstituciones.BUSCAR_POR_IDENTIFICACION, tipoIdentificacion,
                    numeroIdentificacion);
            return persona;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda por identificación de personas de otras instituciones vigentes.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return PersonalOtrasInstituciones que coincida con la busqueda
     * @throws DaoException DaoException
     */
    public PersonalOtrasInstituciones buscarVigentesPorIdentificacion(final String tipoIdentificacion, 
            final String numeroIdentificacion) throws DaoException {
        try {
            List<PersonalOtrasInstituciones> lista = buscarPorConsultaNombrada(
                    PersonalOtrasInstituciones.BUSCAR_VIGENTES_POR_IDENTIFICACION, tipoIdentificacion,
                    numeroIdentificacion);
            if(!lista.isEmpty())
                return lista.get(0);
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return null;
    }

    

    /**
     * Este metodo procesa la busqueda de todo por appellidosNombres.
     *
     * @param appellidosNombres String
     * @return Lista de PersonalOtrasInstituciones
     * @throws DaoException DaoException
     */
    public List<PersonalOtrasInstituciones> buscarPorNombre(final String appellidosNombres) throws
            DaoException {
        try {
            List<PersonalOtrasInstituciones> lista = buscarPorConsultaNombrada(
                    PersonalOtrasInstituciones.BUSCAR_POR_APELLIDOS_NOMBRES,
                    UtilCadena.concatenar("%", appellidosNombres.toUpperCase(), "%"));
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de los vigentes por appellidos y nombres.
     *
     * @param appellidosNombres String
     * @return Lista de PersonalOtrasInstituciones
     * @throws DaoException DaoException
     */
    public List<PersonalOtrasInstituciones> buscarVigentesPorNombre(final String appellidosNombres) throws
            DaoException {
        try {
            List<PersonalOtrasInstituciones> lista = buscarPorConsultaNombrada(
                    PersonalOtrasInstituciones.BUSCAR_VIGENTES_POR_APELLIDOS_NOMBRES,
                    UtilCadena.concatenar("%", appellidosNombres.toUpperCase(), "%"));
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
 
    /**
     * Este metodo busca personal de otras instituciones con el estado especificado.
     *
     * @param vigente Boolean
     * @return Lista de PersonalOtrasInstituciones
     * @throws DaoException DaoException
     */
    public List<PersonalOtrasInstituciones> buscarPorEstado(final Boolean vigente) throws DaoException {
        try {
            List<PersonalOtrasInstituciones> lista = buscarPorConsultaNombrada(
                    PersonalOtrasInstituciones.BUSCAR_POR_ESTADO,vigente);
            return lista;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
