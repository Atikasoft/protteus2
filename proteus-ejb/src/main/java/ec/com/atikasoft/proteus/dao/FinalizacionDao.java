/*
 *  TramiteDao.java
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
 *  30/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Finalizacion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class FinalizacionDao extends GenericDAO<Finalizacion, Long> {

    /**
     * Constructor.
     */
    public FinalizacionDao() {
        super(Finalizacion.class);
    }

    /**
     * Este método busca una cesacion segun el id de movimiento.
     *
     * @param idMovimiento Long
     * @return Finalizacion
     * @throws DaoException DaoException
     */
    public Finalizacion buscarPorMovimiento(final Long idMovimiento) throws DaoException {
        try {
            Finalizacion finalizacion = null;
            List<Finalizacion> buscarPorConsultaNombrada = buscarPorConsultaNombrada(
                    Finalizacion.BUSCAR_POR_MOVIMIENTO, idMovimiento);
            if (buscarPorConsultaNombrada != null && buscarPorConsultaNombrada.size() == 1) {
                finalizacion = buscarPorConsultaNombrada.get(0);
            }
            return finalizacion;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
