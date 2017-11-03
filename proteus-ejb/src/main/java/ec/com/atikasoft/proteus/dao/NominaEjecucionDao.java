/*
 *  NominaEjecucionDao.java
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Quito - Ecuador
 *
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.nomina.NominaEjecucion;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaEjecucionDao extends GenericDAO<NominaEjecucion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaEjecucionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public NominaEjecucionDao() {
        super(NominaEjecucion.class);
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<NominaEjecucion> buscar(final Long nominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NominaEjecucion.BUSCAR_POR_NOMINA, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @throws DaoException
     */
    public void quitarVigencia(final Long nominaId) throws DaoException {
        Query query = getEntityManager().createNamedQuery(NominaEjecucion.QUITAR_VIGENCIA);
        query.setParameter(1, nominaId);
        query.executeUpdate();
    }
}
