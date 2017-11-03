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
import ec.com.atikasoft.proteus.modelo.Licencia;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class LicenciaDao extends GenericDAO<Licencia, Long> {

    /**
     * Constructor.
     */
    public LicenciaDao() {
        super(Licencia.class);
    }

    /**
     * Recupera la licencia de un movimiento.
     *
     * @param movimientoId Identificador unico del movimiento.
     * @return
     * @throws DaoException
     */
    public Licencia buscarPorMovimiento(final Long movimientoId) throws DaoException {
        try {
            Licencia entidad = null;
            List<Licencia> lista = buscarPorConsultaNombrada(Licencia.BUSCAR_POR_MOVIMIENTO, movimientoId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera licencia en curso.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoId
     * @param fecha
     * @return
     * @throws DaoException
     */
    public List<Licencia> buscarEnCurso(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long tipoMovimientoId, final Date fecha) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Licencia.BUSCAR_LICENCIAS_EN_CURSO, tipoIdentificacion,
                    numeroIdentificacion, tipoMovimientoId, fecha);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
}
