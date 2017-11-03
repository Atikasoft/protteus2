/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInformacionMedica;
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
public class ServidorInformacionMedicaDao extends GenericDAO<ServidorInformacionMedica, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ServidorInformacionMedica.class.getCanonicalName());

    /**
     * Constructor por defecto.
     */
    public ServidorInformacionMedicaDao() {
        super(ServidorInformacionMedica.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorParienteInstitucion
     * que esten vigentes true.
     *
     * @return Listado ServidorParienteInstitucion
     * @throws DaoException En caso de error
     */
    public List<ServidorInformacionMedica> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInformacionMedica.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInformacionMedicaDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<ServidorInformacionMedica> buscarPorServidorId(final Long servidorId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInformacionMedica.BUSCAR_POR_SERVIDOR_ID,
                    servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInformacionMedicaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
