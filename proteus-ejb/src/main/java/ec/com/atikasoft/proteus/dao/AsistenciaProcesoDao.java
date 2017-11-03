/*
 *  AsistenciaProcesoDao.java
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
import ec.com.atikasoft.proteus.modelo.AsistenciaProceso;
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
public class AsistenciaProcesoDao extends GenericDAO<AsistenciaProceso, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AsistenciaProcesoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AsistenciaProcesoDao() {
        super(AsistenciaProceso.class);
    }
    /**
     * Metodo que se encarga de buscar un listado de AsistenciaProceso que esten vigentes true.
     *
     * @return Listado AsistenciaProceso
     * @throws DaoException En caso de error
     */
    public List<AsistenciaProceso> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(AsistenciaProceso.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AsistenciaProcesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar el ultimo registro procesado
     * @return Listado AsistenciaProceso
     * @throws DaoException En caso de error
     */
    public List<AsistenciaProceso> buscarUltimaFechaProcesada() throws DaoException {
        try {
            return buscarPorConsultaNombrada(AsistenciaProceso.BUSCAR_ULTIMA_FECHA_PROCESADA);           
        } catch (DaoException ex) {
            Logger.getLogger(AsistenciaProcesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
      /**
     *
     * @param fecha
     * @return
     * @throws DaoException
     */
    public AsistenciaProceso buscarPorFecha(final Date fecha)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM AsistenciaProceso a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        List<AsistenciaProceso> resultList = createQuery.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }
}