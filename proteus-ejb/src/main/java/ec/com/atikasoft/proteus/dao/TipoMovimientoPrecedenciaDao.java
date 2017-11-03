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
import ec.com.atikasoft.proteus.modelo.TipoMovimientoPrecedencia;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class TipoMovimientoPrecedenciaDao extends GenericDAO<TipoMovimientoPrecedencia, Long> {

    /**
     * Constructor.
     */
    public TipoMovimientoPrecedenciaDao() {
        super(TipoMovimientoPrecedencia.class);
    }

    /**
     * Recupera las precencias del tipo de moviento por tipo de movimiento.
     *
     * @param tipoMovimientoId
     * @return
     * @throws DaoException
     */
    public List<TipoMovimientoPrecedencia> buscarPorTipoMovimiento(final Long tipoMovimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoPrecedencia.BUSCAR_POR_TIPO_MOVIMIENTO, tipoMovimientoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera las precencias del tipo de moviento por tipo de movimiento id y por dependiente id.
     *
     * @param tipoMovimientoId Long
     * @param tipoMovimientoDependienteId Long
     * @return TipoMovimientoPrecedencia
     * @throws DaoException En caso de error
     */
    public TipoMovimientoPrecedencia buscarPorTipoMovimientoYDependiente(final Long tipoMovimientoId,
            final Long tipoMovimientoDependienteId) throws DaoException {
        try {
            TipoMovimientoPrecedencia tmp = null;
            List<TipoMovimientoPrecedencia> lista = buscarPorConsultaNombrada(
                    TipoMovimientoPrecedencia.BUSCAR_POR_TIPO_MOVIMIENTO_Y_DEPENDIENTE,
                    tipoMovimientoId, tipoMovimientoDependienteId);
            if (lista != null && !lista.isEmpty()) {
                tmp = lista.get(0);
            }
            return tmp;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
