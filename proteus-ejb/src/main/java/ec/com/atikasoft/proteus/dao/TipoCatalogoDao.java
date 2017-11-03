/*
 *  TipoCatalogoDao.java
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
 *  01/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TipoCatalogo;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * TipoCatalogoDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoCatalogoDao extends GenericDAO<TipoCatalogo, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(TipoCatalogo.class.getCanonicalName());

    /**
     * Constructor por defecto.
     */
    public TipoCatalogoDao() {
        super(TipoCatalogo.class);
    }

    /**
     * Recupera todos los tipos catalogos vigentes y ordenados.
     *
     * @return
     * @throws DaoException
     */
    public List<TipoCatalogo> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoCatalogo.BUSCAR_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
