/*
 *  AtrasoDao.java
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
 *  27/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.Atraso;
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
public class AtrasoDao extends GenericDAO<Atraso, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AtrasoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AtrasoDao() {
        super(Atraso.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Atraso que esten vigentes
     * true.
     *
     * @return Listado Atraso
     * @throws DaoException En caso de error
     */
    public List<Atraso> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Atraso.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AtrasoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los atrasos/faltas del servidor
     *
     * @param idServidor (servidor id)
     * @return Listado Atraso
     * @throws DaoException En caso de error
     */
    public List<Atraso> buscarPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Atraso.BUSCAR_POR_SERVIDOR);
        } catch (DaoException ex) {
            Logger.getLogger(AtrasoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Metodo que se encarga de buscar los atrasos/faltas del servidor desde su id de planificacion de horario
     *
     * @param idPlanificacion (id de planificacion horario)
     * @return Listado Atraso
     * @throws DaoException En caso de error
     */
    public List<Atraso> buscarPorPlanificacionHorario(final Long idPlanificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Atraso.BUSCAR_POR_PLANIFICACION_HORARIO,idPlanificacion);
        } catch (DaoException ex) {
            Logger.getLogger(AtrasoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     *
     * @param idServidor
     * @param fecha
     * @return
     * @throws DaoException
     */
    public List<Atraso> buscarPorServidorYFecha(final long idServidor, final Date fecha)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Atraso a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" AND a.servidor.id = :idServidor");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        createQuery.setParameter("idServidor", idServidor);
        List<Atraso> resultList = createQuery.getResultList();
            return resultList;
    }
      /**
     * Metodo que se encarga de buscar un listado de Asistencia de una fecha
     * especifica.
     *
     * @param idServidor
     * @param fechaInicio fecha inicio
     * @param fechaFin fecha final
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Atraso> buscarPorServidorPorRangoFechas(final Long idServidor, final Date fechaInicio, final Date fechaFin) throws DaoException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Atraso a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.servidor.id= :idServidor ");
        sql.append(" AND a.fecha BETWEEN :fechaInicio AND  :fechaFin ");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fechaInicio", fechaInicio, TemporalType.TIMESTAMP);
        createQuery.setParameter("fechaFin", fechaFin, TemporalType.TIMESTAMP);
        createQuery.setParameter("idServidor", idServidor);
        List resultList = createQuery.getResultList();
        return resultList;
    }
}
