/*
 *  PlanificacionVacacionDao.java
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
 *  19/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class PlanificacionVacacionDao extends GenericDAO<PlanificacionVacacion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionVacacionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public PlanificacionVacacionDao() {
        super(PlanificacionVacacion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de PlanificacionVacacion que
     * esten vigentes true.
     *
     * @return Listado PlanificacionVacacion
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionVacacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de PlanificacionVacacion
     * vigentes por Id de servidor y id de ejercicio fiscal
     *
     * @param idServidor identificacion del servidor
     * @param idInstitucionEjercicioFiscal (institucion ejercicio fiscal)
     * @return Listado PlanificacionVacacion
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacion> buscarPorIdServidorInstitucionEjercicioFiscal(
            Long idServidor, Long idInstitucionEjercicioFiscal) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    PlanificacionVacacion.BUSCAR_POR_SERVIDOR_INSTITUCION_EJERCICIOFISCAL, 
                    idServidor, idInstitucionEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar un listado de PlanificacionVacacion
     * vigentes por id de ejercicio fiscal y estado
     *
     * @param estado esta de la planificacion
     * @param idInstitucionEjercicioFiscal (institucion ejercicio fiscal)
     * @return Listado PlanificacionVacacion
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacion> buscarPorInstitucionEjercicioFiscalYEstado(
            String estado, Long idInstitucionEjercicioFiscal) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    PlanificacionVacacion.BUSCAR_POR_INSTITUCION_EJERCICIOFISCAL_Y_ESTADO, 
                    estado, idInstitucionEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de PlanificacionVacacion
     * vigentes por id de institucion y estado
     *
     * @param idServidorJefe identificador del servidor jefe.
     * @param idInstitucion identificacion de la institucion.
     * @param estado estado de la planificacion.
     * @param idEjercicioFiscal identificador del ejercicio fiscal.
     * @param idUnidadOrganizacional identificador de la unidad organizacional.
     * @return Listado PlanificacionVacacion
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacion> buscarListaValidacionAprobacion(
            final Long idServidorJefe,
            final String estado, final Long idInstitucion, final Long idEjercicioFiscal, final Long idUnidadOrganizacional) throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM PlanificacionVacacion a "
                + " where a.vigente=true  "
                + " and a.institucionEjercicioFiscal.institucion.id = :institucion ");
        parametros.put("institucion", idInstitucion);
        if (estado != null) {
            sql.append(" AND a.estado = :estado ");
            parametros.put("estado", estado);
        }
        /*if (idServidorJefe != null) {
            sql.append(" AND a.servidor.id <> :servidorJefe ");
            parametros.put("servidorJefe", idServidorJefe);
        }*/
        if (idEjercicioFiscal != null) {
            sql.append(" AND a.institucionEjercicioFiscal.ejercicioFiscal.id = :ejercicio ");
            parametros.put("ejercicio", idEjercicioFiscal);
        }
        if(idUnidadOrganizacional!= null){
            sql.append(" AND EXISTS( Select a.id from DistributivoDetalle d  WHERE d.vigente=true"
                    + " AND d.distributivo.institucionEjercicioFiscal.id = :institucion"
                    + " AND d.servidor.id = a.servidor.id"
                    + " AND d.distributivo.unidadOrganizacional.id = :unidad)");
            parametros.put("unidad", idUnidadOrganizacional);
        }
        sql.append(" order by a.servidor.apellidosNombres ");
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     * Permite obtener la lista de planificacion de vacaciones para aprobar.
     *
     * @param idServidorJefe
     * @param idInstitucionEjercicioFiscal
     * @param idUnidadOrganizacional
     * @param estado si es null, busca todos los estados
     * @return
     * @throws DaoException
     */
//    public List<PlanificacionVacacion> buscarListaAprobacion(final Long idServidorJefe, final Long idInstitucionEjercicioFiscal,
//            final Long idUnidadOrganizacional, final String estado) throws DaoException {
//        List<PlanificacionVacacion> lista1 = new ArrayList<PlanificacionVacacion>();
//        try {
//            Map<String, Object> parametros = new HashMap<String, Object>();
//            StringBuilder sql = new StringBuilder();
//            sql.append("SELECT a FROM PlanificacionVacacion a, DistributivoDetalle d "
//                    + " where a.vigente = true and a.servidor.id = d.servidor.id");
//
//            if (idInstitucionEjercicioFiscal != null) {
//                sql.append(" AND a.institucionEjercicioFiscal.id = :ejercicio ");
//                parametros.put("ejercicio", idInstitucionEjercicioFiscal);
//            }
//            if (idServidorJefe != null) {
//                sql.append(" AND a.servidor.id <> :servidorJefe ");
//                parametros.put("servidorJefe", idServidorJefe);
//            }
//            if (estado != null) {
//                sql.append(" AND a.estado = :estado ");
//                parametros.put("estado", estado);
//            }
//
//            if (idUnidadOrganizacional != null) {
//                sql.append(" AND d.distributivo.unidadOrganizacional.id = :unidad ");
//                parametros.put("unidad", idUnidadOrganizacional);
//            }
//
//            sql.append(" order by a.institucionEjercicioFiscal.ejercicioFiscal.nombre, d.distributivo.unidadOrganizacional.nombre, a.servidor.apellidosNombres, a.fechaCreacion desc ");
//            Query query = getEntityManager().createQuery(sql.toString());
//            Set<String> keys = parametros.keySet();
//            for (String key : keys) {
//                query.setParameter(key, parametros.get(key));
//            }
//            lista1 = query.getResultList();
//
//        } catch (Exception e) {
//            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, e);
//            throw new DaoException("Error obtener lista de aprobacion de planificacion anual de vacaciones.", e);
//        }
//        return lista1;
//    }
}
