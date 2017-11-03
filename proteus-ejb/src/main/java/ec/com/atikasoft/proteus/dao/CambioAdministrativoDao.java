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
import ec.com.atikasoft.proteus.modelo.CambioAdministrativo;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de cambios administrativos.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class CambioAdministrativoDao extends GenericDAO<CambioAdministrativo, Long> {

    /**
     * Constructor.
     */
    public CambioAdministrativoDao() {
        super(CambioAdministrativo.class);
    }

    /**
     * Recupera la licencia de un movimiento.
     *
     * @param movimientoId Identificador unico del movimiento.
     * @return
     * @throws DaoException
     */
    public CambioAdministrativo buscarPorMovimiento(final Long movimientoId) throws DaoException {
        try {
            CambioAdministrativo entidad = null;
            List<CambioAdministrativo> lista = buscarPorConsultaNombrada(CambioAdministrativo.BUSCAR_POR_MOVIMIENTO,
                    movimientoId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
