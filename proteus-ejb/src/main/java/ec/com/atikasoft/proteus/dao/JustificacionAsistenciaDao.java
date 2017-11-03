/*
 *  JustificacionAsistenciaDao.java
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
 *  26/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.JustificacionAsistencia;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class JustificacionAsistenciaDao extends GenericDAO<JustificacionAsistencia, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(JustificacionAsistenciaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public JustificacionAsistenciaDao() {
        super(JustificacionAsistencia.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia que esten
     * vigentes true.
     *
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<JustificacionAsistencia> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(JustificacionAsistencia.BUSCAR_VIGENTE);
        } catch (DaoException ex) {
            Logger.getLogger(JustificacionAsistenciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
       /**
     * Metodo que se encarga de buscar un listado de Asistencia que esten
     * vigentes true por id de atraso.
     *
     * @param idAtraso
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<JustificacionAsistencia> buscarPorAtraso(final Long idAtraso) throws DaoException {
        try {
            return buscarPorConsultaNombrada(JustificacionAsistencia.BUSCAR_POR_ATRASO,idAtraso);
        } catch (DaoException ex) {
            Logger.getLogger(JustificacionAsistenciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    
    /**
     * Metodo que se encarga de buscar un listado de Asistencia de una fecha
     * especificay codigo de empleado.
     *
     * @param idServidor
     * @param fecha Long fecha de evento
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<JustificacionAsistencia> buscarPorServidorYFecha(final Long idServidor, final Date fecha) throws DaoException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM JustificacionAsistencia a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" AND a.servidor.id = :idServidor");
        sql.append(" ORDER BY a.atraso.fecha  ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        createQuery.setParameter("idServidor", idServidor);
        List resultList = createQuery.getResultList();
        return resultList;
    }
}
