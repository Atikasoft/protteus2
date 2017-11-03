/*
 *  GastoPersonalHistoricoDao.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.GastoPersonalHistorico;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class GastoPersonalHistoricoDao extends GenericDAO<GastoPersonalHistorico, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(GastoPersonalHistoricoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public GastoPersonalHistoricoDao() {
        super(GastoPersonalHistorico.class);
    }
}