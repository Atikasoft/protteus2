/*
 *  TipoMovimientoDelegadoDao.java
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
 *  21/02/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDelegado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class TipoMovimientoDelegadoDao extends GenericDAO<TipoMovimientoDelegado, Long> {

    /**
     * constructor.
     */
    public TipoMovimientoDelegadoDao() {
        super(TipoMovimientoDelegado.class);
    }

    /**
     * Devuelve si existe ese delegado usado en el tipo de movimiento.
     *
     * @param tm TipoMovimiento
     * @param cedulaDelegado String
     * @return TipoMovimientoDelegado
     */
    public List<TipoMovimientoDelegado> buscarTipoMovimientoDelegado(final TipoMovimiento tm, String cedulaDelegado)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoDelegado.BUSCAR_POR_TIPO_MOVIMIENTO_DELEGADO, tm,
                    cedulaDelegado);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Devuelve si existe ese delegado usado con el Id.
     *
     * @param id Long
     * @return List
     * @throws DaoException
     */
    public List<TipoMovimientoDelegado> buscarTipoMovimientoDelegadoporId(final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoDelegado.BUSCAR_TIPO_MOVIMIENTO_DELEGADO_POR_ID, id);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera los delegados de una institucion x tipo de movimiento.
     *
     * @param tipoMovimiento
     * @param institucion
     * @return
     * @throws DaoException
     */
    public List<TipoMovimientoDelegado> buscarPorTipoMovimientoEInstitucion(final Long tipoMovimientoId,
            final Long institucionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoDelegado.BUSCAR_POR_TIPO_MOVIMIENTO_E_INSTITUCION,
                    tipoMovimientoId, institucionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
