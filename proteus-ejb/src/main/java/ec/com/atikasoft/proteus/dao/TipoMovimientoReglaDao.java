/*
 *  TipoMovimientoDao.java
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
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoMovimientoReglaDao extends GenericDAO<TipoMovimientoRegla, Long> {

    /**
     * Logger de clase.
     */
    private final static Logger LOG = Logger.getLogger(TipoMovimientoReglaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimientoReglaDao() {
        super(TipoMovimientoRegla.class);
    }

    /**
     * Recupera las reglas de un tipo de movimiento en particular.
     *
     * @param tipoMovimientoId Identificador unico del tipo de institucion.
     * @return Lista de reglas.
     * @throws DaoException Error en capa de persistencia.
     */
    public List<TipoMovimientoRegla> buscarPorTipoMovimiento(final Long tipoMovimientoId) throws DaoException {
        LOG.fine(UtilCadena.concatenarLog("TipoMovimientoReglaDao.buscarPorTipoMovimiento", "tipoMovimientoId=",
                tipoMovimientoId));
        try {
            return buscarPorConsultaNombrada(TipoMovimientoRegla.BUSCAR_POR_TIPO_MOVIMIENTOS, tipoMovimientoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}