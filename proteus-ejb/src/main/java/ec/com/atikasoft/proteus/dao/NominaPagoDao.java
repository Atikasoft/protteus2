/*
 *  ClaseDao.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.NominaPago;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de pagos de nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaPagoDao extends GenericDAO<NominaPago, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaPagoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NominaPagoDao() {
        super(NominaPago.class);
    }

    /**
     *
     * @param nominaId
     * @throws DaoException
     */
    public void eliminarPorNomina(Long nominaId) throws DaoException {
        ejecutarPorConsultaNombrada(NominaPago.ELIMINAR_POR_NOMINA, nominaId);
    }

    /**
     *
     * @param nominaId
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @throws DaoException
     */
    public void eliminarPorNomina(Long nominaId, String tipoIdentificacion, String numeroIdentificacion) throws DaoException {
        ejecutarPorConsultaNombrada(NominaPago.ELIMINAR_POR_NOMINA_SERVIDOR, nominaId, tipoIdentificacion, numeroIdentificacion);
    }
}
