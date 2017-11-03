/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author atikasoft
 */
@LocalBean
@Stateless
public class ServidorExperienciaDao extends GenericDAO<ServidorExperiencia, Long> {

    /**
     * Constructor por defecto.
     */
    public ServidorExperienciaDao() {
        super(ServidorExperiencia.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorExperiencia que
     * esten vigentes true.
     *
     * @return Listado ServidorExperiencia
     * @throws DaoException En caso de error
     */
    public List<ServidorExperiencia> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorExperiencia.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorExperienciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * metodo que busca por el id del servidor que es el padre.
     *
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<ServidorExperiencia> buscarPorServidorId(final Long servidorId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorExperiencia.BUSCAR_POR_SERVIDOR_ID,
                    servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorExperienciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
