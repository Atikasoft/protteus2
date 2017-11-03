/*
 *  ServidorInstitucionDao.java
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
 *  20/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
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
public class ServidorInstitucionDao extends GenericDAO<ServidorInstitucion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ServidorInstitucionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ServidorInstitucionDao() {
        super(ServidorInstitucion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorInstitucion que esten vigentes true.
     *
     * @return Listado ServidorInstitucion
     * @throws DaoException En caso de error
     */
    public List<ServidorInstitucion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInstitucion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param idInstitucion
     * @param idRegimen
     * @param inicio
     * @param longitud
     * @return
     * @throws DaoException
     */
    public List<ServidorInstitucion> buscarVigentes(final Long idInstitucion, final Long idRegimen, final Integer inicio, final Integer longitud)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM ServidorInstitucion o");
        sql.append(" WHERE o.vigente=true ");
        sql.append(" AND o.institucion.id = :institucion ");
        sql.append(" AND EXISTS ( Select d from DistributivoDetalle d where d.vigente = true and d.servidor.id=o.servidor.id"
                + " AND d.distributivo.institucionEjercicioFiscal.institucion.id= :institucion "
                + " AND d.escalaOcupacional.nivelOcupacional.regimenLaboral.id = :regimen )");
        sql.append(" ORDER BY o.id");

        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("institucion", idInstitucion);
        query.setParameter("regimen", idRegimen);
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);

        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        List<ServidorInstitucion> lista = query.getResultList();
        return lista;
    }

    /**
     * *
     *
     * @param idInstitucion
     * @param idRegimen
     * @param dia
     * @param inicio
     * @param longitud
     * @return
     * @throws DaoException
     */
    public List<Object[]> buscarVigente(final Long idInstitucion, final Long idRegimen, final Integer inicio,
            final Integer longitud) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT si.id,si.servidor_id, s.fecha_ingreso_institucion ");
            sql.append(" FROM sch_proteus.servidor_institucion si , sch_proteus.servidor s ");
            sql.append(" WHERE ");
            sql.append(" si.servidor_id=s.id and ");
            sql.append(" si.vigente = 1 AND ");
            sql.append(" si.institucion_id = ").append(idInstitucion);
            sql.append(" AND EXISTS (SELECT 1 FROM sch_proteus.distributivo_detalles dd , "
                    + "sch_proteus.escalas_ocupacionales e,");
            sql.append("  sch_proteus.niveles_ocupacionales n,sch_proteus.regimenes_laborales r");
            sql.append("  WHERE dd.servidor_id  = si.servidor_id AND dd.vigente=1 ");
            sql.append("  AND dd.escala_ocupacional_id = e.id and e.niveles_ocupacionales_id = n.id and "
                    + "n.regimenes_laborales_id=r.id");
            sql.append("  AND r.id = ").append(idRegimen).append(") order by s.id");
            //System.out.println("SQL:"+sql.toString());
            Query query = getEntityManager().createNativeQuery(sql.toString());
            query.setFirstResult(inicio);
            query.setMaxResults(longitud);
            List<Object[]> servidores = query.getResultList();
            return servidores;
        } catch (NumberFormatException e) {
            throw new DaoException(e);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorInstitucion que esten vigentes true por su Institucion.
     *
     * @param idInstitucion id de la institución por la cual se debe filtrar
     * @return Listado ServidorInstitucion
     * @throws DaoException En caso de error
     */
    public List<ServidorInstitucion> buscarVigentePorInstitucion(final Long idInstitucion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInstitucion.BUSCAR_POR_INSTITUCION, idInstitucion);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorInstitucion que esten vigentes true por su Servidor.
     *
     * @param numDoc identificacion del servidor por la cual se debe filtrar
     * @return Listado ServidorInstitucion
     * @throws DaoException En caso de error
     */
    public List<ServidorInstitucion> buscarVigentePorPorDocumentoServidor(final String numDoc) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorInstitucion.BUSCAR_POR_NUMERO_DOCUMENTO_SERVIDOR, numDoc);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de ServidorInstitucion que esten vigentes true por su Servidor.
     *
     * @param tipoDoc Tipo de documento
     * @param numDoc Nro de documento
     * @return
     * @throws DaoException
     */
    public List<ServidorInstitucion> buscarVigentePorPorDocumentoServidor(
            final String tipoDoc, final String numDoc) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    ServidorInstitucion.BUSCAR_POR_TIPO_Y_NUMERO_DOCUMENTO_SERVIDOR, tipoDoc, numDoc);

        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param institucionId
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws DaoException
     */
    public List<ServidorInstitucion> buscarPorInstitucionServidor(final Long institucionId,
            final String tipoIdentificacion, final String numeroIdentificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    ServidorInstitucion.BUSCAR_POR_INSTITUCION_SERVIDOR, institucionId, tipoIdentificacion, numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorInstitucionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param institucionId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public ServidorInstitucion buscarPorInstitucionServidor(
            final Long institucionId, final Long servidorId) throws DaoException {
        try {
            ServidorInstitucion entidad = null;
            List<ServidorInstitucion> lista = buscarPorConsultaNombrada(
                    ServidorInstitucion.BUSCAR_POR_INSTITUCION_SERVIDOR_ID, institucionId, servidorId);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
