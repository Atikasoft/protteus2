/*
 *  MovimientoBitacoraDao.java
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
 *  17/12/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.MovimientoBitacora;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class MovimientoBitacoraDao extends GenericDAO<MovimientoBitacora, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(MovimientoBitacora.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public MovimientoBitacoraDao() {
        super(MovimientoBitacora.class);
    }
}
