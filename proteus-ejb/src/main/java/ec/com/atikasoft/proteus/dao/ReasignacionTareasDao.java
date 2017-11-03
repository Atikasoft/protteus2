/*
 *  ReasignacionTareasDao.java
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
 *  07/01/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ReasignacionTarea;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class ReasignacionTareasDao extends GenericDAO<ReasignacionTarea, Long> {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(ReasignacionTareasDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ReasignacionTareasDao() {
        super(ReasignacionTarea.class);
    }

}
