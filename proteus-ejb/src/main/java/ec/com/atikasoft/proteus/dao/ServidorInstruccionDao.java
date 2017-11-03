/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInstruccion;
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
public class ServidorInstruccionDao extends GenericDAO<ServidorInstruccion, Long> {

    /**
     * Constructor por defecto.
     */
    public ServidorInstruccionDao() {
        super(ServidorInstruccion.class);
    }
    
       /**
     * Metodo que se encarga de buscar un listado de ServidorInstruccion que
     * esten vigentes true.
     *
     * @return Listado ServidorInstruccion
     * @throws DaoException En caso de error
     */
    public List<ServidorInstruccion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInstruccion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstruccionDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<ServidorInstruccion> buscarPorServidorId(final Long servidorId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInstruccion.BUSCAR_POR_SERVIDOR_ID,
                    servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstruccionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
