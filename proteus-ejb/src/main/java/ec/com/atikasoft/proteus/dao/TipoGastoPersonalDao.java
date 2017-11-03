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
import ec.com.atikasoft.proteus.modelo.TipoGastoPersonal;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Dao de TipoGastoPersonal.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoGastoPersonalDao extends GenericDAO<TipoGastoPersonal, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(TipoGastoPersonalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TipoGastoPersonalDao() {
        super(TipoGastoPersonal.class);
    }

    public List<TipoGastoPersonal> buscarPorEjercicioFiscal(final Long ejercicioFiscalId) throws DaoException {
        return buscarPorConsultaNombrada(TipoGastoPersonal.BUSCAR_POR_EJERCICIO_FISCAL, ejercicioFiscalId);
    }
}
