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
import ec.com.atikasoft.proteus.modelo.TipoMovimientoAlerta;
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
public class TipoMovimientoAlertaDao extends GenericDAO<TipoMovimientoAlerta, Long> {

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimientoAlertaDao() {
        super(TipoMovimientoAlerta.class);
    }

    /**
     * Metodo que se encarga de buscar los tipos de movimiento alerta por el id del tipo de movimiento.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @return Lista de TipoMovimientoAlerta
     * @throws DaoException En caso de error
     */
    public List<TipoMovimientoAlerta> buscarPorTipoMovimientoId(final Long tipoMovimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimientoAlerta.BUSCAR_POR_TIPO_MOVIMIENTO_ID, tipoMovimientoId);
        } catch (ec.com.atikasoft.proteus.excepciones.DaoException ex) {
            Logger.getLogger(TipoMovimientoAlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException();
        }

    }
}