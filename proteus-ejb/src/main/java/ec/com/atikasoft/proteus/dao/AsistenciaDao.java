/*
 *  AsistenciaDao.java
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
import ec.com.atikasoft.proteus.modelo.Asistencia;
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
public class AsistenciaDao extends GenericDAO<Asistencia, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AsistenciaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AsistenciaDao() {
        super(Asistencia.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia que esten
     * vigentes true.
     *
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Asistencia.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AsistenciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia que esten
     * vigentes true de un servidor específico.
     *
     * @param codigoServidor Long codigo del servidor para verificación de
     * asistencias
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarPorServidor(final Long codigoServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Asistencia.BUSCAR_POR_SERVIDOR, codigoServidor);
        } catch (DaoException ex) {
            Logger.getLogger(AsistenciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia de una fecha
     * especifica.
     *
     * @param fecha Long fecha de evento
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarPorFecha(final Date fecha) throws DaoException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Asistencia a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia de una fecha
     * especifica.
     *
     * @param idServidor identificacion del servidor
     * @param fechaInicio fecha inicio
     * @param fechaFin fecha final
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarPorServidorPorRangoFechas(final Long idServidor, final Date fechaInicio, final Date fechaFin) throws DaoException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Asistencia a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.servidor.id= :idServidor ");
        sql.append(" AND  a.fecha BETWEEN :fechaInicio AND  :fechaFin ");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fechaInicio", fechaInicio, TemporalType.TIMESTAMP);
        createQuery.setParameter("fechaFin", fechaFin, TemporalType.TIMESTAMP);
        createQuery.setParameter("idServidor", idServidor);
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     * Metodo que se encarga de buscar un listado de Asistencia de una fecha
     * especificay codigo de empleado.
     *
     * @param codigoEmpleado codigo del empleado
     * @param fecha Long fecha de evento
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarPorFechaYEmpleado(final Long codigoEmpleado, final Date fecha) throws DaoException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Asistencia a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" AND a.codigoEmpleado = :codigoEmpleado");
        sql.append(" ORDER BY a.fecha  ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        createQuery.setParameter("codigoEmpleado", codigoEmpleado);
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     * Metodo que se encarga de buscar el ultimo registro procesado
     *
     * @return Listado Asistencia
     * @throws DaoException En caso de error
     */
    public List<Asistencia> buscarUltimaFechaProcesada() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Asistencia.BUSCAR_ULTIMA_FECHA_PROCESADA);
        } catch (DaoException ex) {
            Logger.getLogger(AsistenciaProcesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
