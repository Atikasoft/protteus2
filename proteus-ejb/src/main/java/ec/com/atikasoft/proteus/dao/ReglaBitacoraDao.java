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
import ec.com.atikasoft.proteus.modelo.ReglaBitacora;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Dao de regla bitacora.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ReglaBitacoraDao extends GenericDAO<ReglaBitacora, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ReglaBitacoraDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ReglaBitacoraDao() {
        super(ReglaBitacora.class);
    }

    /**
     * Quita la vigencia en la bitacora de reglas de acuerdo al movimiento.
     *
     * @param movimientoId Identificador unico de movimiento.
     * @throws DaoException Error de ejecucion.
     */
    public void quitarVigenciaPorMovimiento(final Long movimientoId) throws DaoException {
        LOG.fine(UtilCadena.concatenarLog("quitarVigenciaPorMovimiento", "movimientoId=", movimientoId));
        try {
            Query query = getEntityManager().createNamedQuery(ReglaBitacora.QUITAR_VIGENCIA_MOVIMIENTO);
            query.setParameter(1, movimientoId);
            query.executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}