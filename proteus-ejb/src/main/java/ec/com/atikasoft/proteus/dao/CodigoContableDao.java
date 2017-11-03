/*
 *  CodigoContableDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CodigoContable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class CodigoContableDao extends GenericDAO<CodigoContable, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CodigoContableDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public CodigoContableDao() {
        super(CodigoContable.class);
    }


    /**
     * Metodo que se encarga de buscar un listado de CodigoContable que esten vigentes true.
     *
     * @return Listado CodigoContable
     * @throws DaoException En caso de error
     */
    public List<CodigoContable> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CodigoContable.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(CodigoContableDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}