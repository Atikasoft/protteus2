/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.modelo.PortalRhh;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * DenominacionPuestoDao
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@LocalBean
@Stateless
public class PortalRhhDao extends GenericDAO<PortalRhh, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PortalRhh.class.getCanonicalName());

    /**
     * Constructor por defecto.
     */
    public PortalRhhDao() {
        super(PortalRhh.class);
    }
      /**
     * Metodo que se encarga de buscar un listado de PortalRhh que esten vigentes true.
     *
     * @return Listado ModalidadLaboral
     * @throws DaoException En caso de error
     */
    public List<PortalRhh> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(PortalRhh.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(PortalRhhDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
        public List<PortalRhh> listarPortalRhhPorCodigo(final String codigo) throws ServicioException {
        try {
            return buscarPorConsultaNombrada(PortalRhh.BUSCAR_POR_CODIGO, codigo.toUpperCase());
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }
}
