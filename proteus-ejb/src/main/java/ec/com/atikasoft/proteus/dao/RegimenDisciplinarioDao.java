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
import ec.com.atikasoft.proteus.modelo.RegimenDisciplinario;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class RegimenDisciplinarioDao extends GenericDAO<RegimenDisciplinario, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RegimenDisciplinarioDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public RegimenDisciplinarioDao() {
        super(RegimenDisciplinario.class);
    }

    /**
     * Recupera el regimen disciplinario de un movimiento.
     *
     * @param movimientoId Identificador unico del movimiento.
     * @return
     * @throws DaoException
     */
    public RegimenDisciplinario buscarPorMovimiento(final Long movimientoId) throws DaoException {
        try {
            RegimenDisciplinario entidad = null;
            List<RegimenDisciplinario> lista = buscarPorConsultaNombrada(RegimenDisciplinario.BUSCAR_POR_MOVIMIENTO,
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