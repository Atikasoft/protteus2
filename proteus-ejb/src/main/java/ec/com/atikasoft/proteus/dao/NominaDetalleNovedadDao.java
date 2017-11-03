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
import ec.com.atikasoft.proteus.modelo.nomina.NominaDetalleNovedad;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaDetalleNovedadDao extends GenericDAO<NominaDetalleNovedad, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaDetalleNovedadDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public NominaDetalleNovedadDao() {
        super(NominaDetalleNovedad.class);
    }

    /**
     *
     * @param nominaId
     * @return 
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int eliminar(final Long nominaId) throws DaoException {
        try {
            return ejecutarPorConsultaNombrada(NominaDetalleNovedad.ELIMINAR, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param distributivoDetalleId
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarPorServidor(final Long nominaId, final Long distributivoDetalleId)
            throws DaoException {
        try {
            ejecutarPorConsultaNombrada(NominaDetalleNovedad.ELIMINAR_POR_SERVIDOR, nominaId, distributivoDetalleId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}