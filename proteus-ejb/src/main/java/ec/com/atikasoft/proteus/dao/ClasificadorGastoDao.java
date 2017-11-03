/*
 *  ClasificadorGastoDao.java
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
import ec.com.atikasoft.proteus.modelo.ClasificadorGasto;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
public class ClasificadorGastoDao extends GenericDAO<ClasificadorGasto, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ClasificadorGastoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ClasificadorGastoDao() {
        super(ClasificadorGasto.class);
    }


    /**
     * Metodo que se encarga de buscar un listado de ClasificadorGasto que esten vigentes true.
     *
     * @return Listado ClasificadorGasto
     * @throws DaoException En caso de error
     */
    public List<ClasificadorGasto> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ClasificadorGasto.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ClasificadorGastoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}