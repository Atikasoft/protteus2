/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ReclutamientoCapacitacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * UbicacionGeograficaDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ServidorCapacitacionDao extends GenericDAO<ServidorCapacitacion, Long> {

    /**
     * Constructor por defecto.
     */
    public ServidorCapacitacionDao() {
        super(ServidorCapacitacion.class);
    }
     /**
     * metodo que busca por el id del reclutamiento que es el padre.
     *
     * @param reclutamientoId
     * @return
     * @throws DaoException
     */
    public List<ServidorCapacitacion> buscarPorServidorId(final Long reclutamientoId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorCapacitacion.BUSCAR_POR_RECLUTAMIENTO_ID,
                    reclutamientoId);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorCapacitacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
