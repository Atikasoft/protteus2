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
import ec.com.atikasoft.proteus.modelo.nomina.NominaIR;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Dao de nomina ir.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaIRDao extends GenericDAO<NominaIR, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaIRDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NominaIRDao() {
        super(NominaIR.class);
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
            return ejecutarPorConsultaNombrada(NominaIR.ELIMINAR, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarPorServidor(final Long nominaId, final Long servidorId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(NominaIR.ELIMINAR_POR_SERVIDOR, nominaId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public NominaIR buscarPorNominaServidor(Long nominaId, Long servidorId) throws DaoException {
        NominaIR entidad = null;
        List<NominaIR> lista = buscarPorConsultaNombrada(NominaIR.BUSCAR_POR_SERVIDOR_NOMINA, nominaId, servidorId);
        if (!lista.isEmpty()) {
            entidad = lista.get(0);
        }
        return entidad;

    }

}
