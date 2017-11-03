/*
 *  ClaseDao.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoMovimientoRequisitoDao extends GenericDAO<TipoMovimientoRequisito, Long> {

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimientoRequisitoDao() {
        super(TipoMovimientoRequisito.class);
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimiento requisitos por el id del tipo de movimiento.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @return Lista de TipoMovimientoRequisito
     * @throws DaoException En caso de error
     */
    public List<TipoMovimientoRequisito> buscarPorTipoMovimientoId(final Long tipoMovimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID, tipoMovimientoId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimiento requisitos por el id del
     * tipo de movimiento que se meustran als ervidor publico.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @return Lista de TipoMovimientoRequisito
     * @throws DaoException En caso de error
     */
    public List<TipoMovimientoRequisito> buscarPorTipoMovimientoIdServidorPublico(final Long tipoMovimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID_SERVIDOR_PUBLICO, tipoMovimientoId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera los requisitos de un tipo de movimiento no validados en un tramite.
     *
     * @param tipoMovimientoId Identificador del tipo de movimiento.
     * @param movimientoId Identificacion del movimiento.
     * @return
     * @throws DaoException
     */
    public List<TipoMovimientoRequisito> buscarNoValidadosPorMovimiento(final Long tipoMovimientoId,
            final Long movimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoRequisito.BUSCAR_NO__VALIDADOS_POR_TIPO_MOVIMIENTO,
                    tipoMovimientoId, movimientoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las validaciones por tipo de movimiento requisito id y por movimiento id.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @param requisitoId Id del requisito
     * @return List<ValiTipoMovimientoRequisitodacion> lista de TipoMovimientoRequisito
     * @throws DaoException en caso de error
     */
    public TipoMovimientoRequisito buscarPorTipoMovimientoIdYRequisitoId(
            final Long tipoMovimientoId, final Long requisitoId) throws DaoException {
        TipoMovimientoRequisito tipoMovimientoRequisito = null;
        List<TipoMovimientoRequisito> tipoMovimientoRequisitos = buscarPorConsultaNombrada(
                TipoMovimientoRequisito.BUSCAR_POR_TIPO_MOVIMIENTO_ID_Y_REQUISITO_ID,
                tipoMovimientoId, requisitoId);
        if (tipoMovimientoRequisitos != null && !tipoMovimientoRequisitos.isEmpty()) {
            tipoMovimientoRequisito = tipoMovimientoRequisitos.get(0);
        }
        return tipoMovimientoRequisito;
    }
}