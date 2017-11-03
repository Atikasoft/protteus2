/*
 *  Sector.java
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
 *  20/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Sector;
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
public class SectorDao extends GenericDAO<Sector, Long> {

    /**
     * Constructor por defecto.
     */
    public SectorDao() {
        super(Sector.class);
    }
    /**
     * Este metodo procesa la busqueda de todos los registros vigentes
     *
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<Sector> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Sector.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
