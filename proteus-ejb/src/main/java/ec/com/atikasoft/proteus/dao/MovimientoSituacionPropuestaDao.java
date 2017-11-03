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

import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.MovimientoSituacionPropuesta;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos a situacion propuesta del movimiento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class MovimientoSituacionPropuestaDao extends GenericDAO<MovimientoSituacionPropuesta, Long> {

    /**
     * Constructor.
     */
    public MovimientoSituacionPropuestaDao() {
        super(MovimientoSituacionPropuesta.class);
    }
}