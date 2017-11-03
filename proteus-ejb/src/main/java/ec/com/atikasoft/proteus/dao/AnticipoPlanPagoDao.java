/*
 *  AnticipoPlanPagoDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class AnticipoPlanPagoDao extends GenericDAO<AnticipoPlanPago, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AnticipoPlanPagoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AnticipoPlanPagoDao() {
        super(AnticipoPlanPago.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de AnticipoPlanPago que esten
     * vigentes true.
     *
     * @return Listado AnticipoPlanPago
     * @throws DaoException En caso de error
     */
    public List<AnticipoPlanPago> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPlanPago.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoPlanPagoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de AnticipoPlanPago que esten
     * vigentes true por el id del anticipo.
     *
     * @param idAnticipo
     * @return Listado AnticipoPlanPago
     * @throws DaoException En caso de error
     */
    public List<AnticipoPlanPago> buscarPorAnticipo(final Long idAnticipo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPlanPago.BUSCAR_POR_ANTICIPO, idAnticipo);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoPlanPagoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param servidorId
     * @param fecha
     * @return
     */
    public List<Object[]> buscarPagosPendientes(Long servidorId, Date fecha) {
        StringBuilder sql = new StringBuilder();
        sql.append("select app.id, app.dividendo,a.numero,app.monto,ap.monto_pagado ");
        sql.append("from sch_proteus.anticipos a ");
        sql.append("join sch_proteus.anticipos_planpago app on app.anticipo_id = a.id and a.estado='A' ");
        sql.append("and app.estado_pago='P' and app.fecha_maxima_pago <= ?1 ");
        sql.append("left join sch_proteus.anticipos_pagos ap on ap.anticipo_planpago_id = app.id ");
        sql.append("where a.servidor_id=?2");
        Query query = getEntityManager().createNativeQuery(sql.toString());
        query.setParameter(1, fecha);
        query.setParameter(2, servidorId);
        return query.getResultList();

    }
}
