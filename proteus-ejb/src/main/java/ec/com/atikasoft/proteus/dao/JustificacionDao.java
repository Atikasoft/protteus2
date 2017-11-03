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
import ec.com.atikasoft.proteus.modelo.Justificacion;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class JustificacionDao extends GenericDAO<Justificacion, Long> {

    /**
     * Constructor.
     */
    public JustificacionDao() {
        super(Justificacion.class);
    }

    /**
     * Este metodo busca una justificacion por el id de movimiento y por el id de tipo de movimiento regla.
     *
     * @param movimientoId Id del movimiento
     * @param tipoMovimientoReglaId Id del tipo de movimiento regla
     * @return Justificacion
     * @throws DaoException Captura de errores
     */
    public Justificacion buscarPorMovimientoYTipoMovimientoRegla(
            final Long movimientoId, final Long tipoMovimientoReglaId) throws DaoException {
        Justificacion j = null;
        List<Justificacion> justificacion = buscarPorConsultaNombrada(
                Justificacion.BUSCAR_POR_MOVIMIENTO_TIPO_MOVIMIENTO_REGLA, movimientoId, tipoMovimientoReglaId);
        if (justificacion != null && !justificacion.isEmpty()) {
            j = justificacion.get(0);
        }
        return j;
    }
}
