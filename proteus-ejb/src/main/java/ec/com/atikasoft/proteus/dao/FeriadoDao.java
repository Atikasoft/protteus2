/*
 *  FeriadoDao.java
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
 *  25/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
public class FeriadoDao extends GenericDAO<Feriado, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(FeriadoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public FeriadoDao() {
        super(Feriado.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @param idRegimenLaboral
     * @return List
     * @throws DaoException DaoException
     */
    public List<Feriado> buscarTodosPorNombre(final String nombre,final Long idRegimenLaboral) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Feriado.BUSCAR_POR_NOMBRE_POR_REGIMEN, UtilCadena.concatenar("%", nombre,
                    "%"),idRegimenLaboral);
        } catch (DaoException ex) {
            Logger.getLogger(FeriadoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Feriado que esten vigentes true por regimen laboral.
     *
     * @param idRegimenLaboral
     * @return Listado Feriado
     * @throws DaoException En caso de error
     */
    public List<Feriado> buscarVigentePorRegimenLaboral(final Long idRegimenLaboral) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Feriado.BUSCAR_VIGENTES_POR_REGIMEN,idRegimenLaboral);
        } catch (DaoException ex) {
            Logger.getLogger(FeriadoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
       /**
     * Metodo que se encarga de buscar un listado de Feriado que esten vigentes true.
     *
     * @return Listado Feriado
     * @throws DaoException En caso de error
     */
    public List<Feriado> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Feriado.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(FeriadoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     *
     * @param fecha
     * @param idEjercicioFiscal
     * @param idRegimenLaboral
     * @return
     * @throws DaoException
     */
    public List<Feriado> buscarPorFecha(final Date fecha, final Long idEjercicioFiscal, final Long idRegimenLaboral) 
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Feriado a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" AND a.ejercicioFiscal.id = :ejercicioFiscal");
        sql.append(" AND a.regimenLaboral.id = :regimenLaboral");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        createQuery.setParameter("ejercicioFiscal", idEjercicioFiscal);
        createQuery.setParameter("regimenLaboral", idRegimenLaboral);
        List resultList = createQuery.getResultList();
        return resultList;
    }
     /**
     *
     * @param fecha
     * @param idRegimen
     * @return
     * @throws DaoException
     */
    public List<Feriado> buscarPorFecha(final Date fecha, final Long idRegimen) 
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Feriado a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        if(idRegimen!=null){
            sql.append(" AND a.regimenLaboral.id = :idRegimen");
        }
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
           if(idRegimen!=null){
            createQuery.setParameter("idRegimen", idRegimen);
        }
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     *
     * @param ejercicioFiscalId
     * @param fechaInicio
     * @param fechaFinal
     * @return 
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException 
     */
    public List<Feriado> buscar(final Long ejercicioFiscalId, final Date fechaInicio, final Date fechaFinal) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Feriado.BUSCAR_POR_PERIODO, ejercicioFiscalId, fechaInicio, fechaFinal);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
