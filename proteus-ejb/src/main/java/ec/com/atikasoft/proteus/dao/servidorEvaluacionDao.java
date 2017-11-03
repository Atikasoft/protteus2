/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorEvaluacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * servidorEvaluacionDao
 *
 *
 */
@LocalBean
@Stateless
public class servidorEvaluacionDao extends GenericDAO<ServidorEvaluacion, Long> {

    /**
     * Constructor por defecto.
     */
    public servidorEvaluacionDao() {
        super(ServidorEvaluacion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorEvaluacion
     * que esten vigentes true.
     *
     * @return Listado ReclutamientoTrayectoriaLaboral
     * @throws DaoException En caso de error
     */
    public List<ServidorEvaluacion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorEvaluacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(servidorEvaluacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * metodo que busca por el id del servidor que es el padre.
     *
     * @param reclutamientoId
     * @return
     * @throws DaoException
     */
    public List<ServidorEvaluacion> buscarPorServidorId(final Long servidorId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorEvaluacion.BUSCAR_POR_SERVIDOR_ID,
                    servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(servidorEvaluacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
