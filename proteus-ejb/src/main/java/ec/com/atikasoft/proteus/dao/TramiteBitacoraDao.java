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
import ec.com.atikasoft.proteus.modelo.TramiteBitacora;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class TramiteBitacoraDao extends GenericDAO<TramiteBitacora, Long> {

    /**
     * Constructor.
     */
    public TramiteBitacoraDao() {
        super(TramiteBitacora.class);
    }

    /**
     * Recupera la bitacora d eun tramite.
     *
     * @param tramiteId Identificador unico del tramite.
     * @return Bitacora del tramite.
     * @throws DaoException Error en capa de persistencia.s
     */
    public TramiteBitacora buscarPorTramite(final Long tramiteId) throws DaoException {
        try {
            TramiteBitacora entidad = null;
            List<TramiteBitacora> lista = buscarPorConsultaNombrada(TramiteBitacora.BUSCAR_POR_TRAMITE, tramiteId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
