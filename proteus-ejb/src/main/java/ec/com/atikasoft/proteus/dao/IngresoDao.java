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
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Tramite;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class IngresoDao extends GenericDAO<Ingreso, Long> {

    /**
     * Constructor.
     */
    public IngresoDao() {
        super(Ingreso.class);
    }

    /**
     * Este método busca un ingreso segun el id de movimiento.
     *
     * @param idMovimiento Long
     * @return Ingreso
     * @throws DaoException DaoException
     */
    public Ingreso buscarPorMovimiento(final Long idMovimiento) throws DaoException {
        try {
            Ingreso ingreso = null;
            List<Ingreso> buscarPorConsultaNombrada = buscarPorConsultaNombrada(
                    Ingreso.BUSCAR_POR_MOVIMIENTO, idMovimiento);
            if (buscarPorConsultaNombrada != null && buscarPorConsultaNombrada.size() == 1) {
                ingreso = buscarPorConsultaNombrada.get(0);
            }
            return ingreso;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo actualiza el ingreso segun el tramite.
     *
     * @param tramite Tramite
     * @throws DaoException Captura de errores.
     */
    public void actualizarDatosDocumentoHabilitenteMasivos(final Tramite tramite) throws DaoException {
        tramite.getTipoMovimiento().getDocumentoHabilitante().getNemonico();
        Query query = getEntityManager().createNamedQuery(Ingreso.ACTUALIZACION_MASIVA_TRAMITE);
        query.setParameter(1, tramite.getTramiteAuxiliar().getAntecedentesContrato());
        query.setParameter(2, tramite.getTramiteAuxiliar().getActividadesContrato());
        query.setParameter(3, tramite.getId());
        query.executeUpdate();
    }

    /**
     *
     * @param tramiteId
     * @return
     * @throws DaoException
     */
    public List<Ingreso> buscarPorTramite(final Long tramiteId) throws DaoException {
        return buscarPorConsultaNombrada(Ingreso.BUSCAR_POR_TRAMITE, tramiteId);
    }
}
