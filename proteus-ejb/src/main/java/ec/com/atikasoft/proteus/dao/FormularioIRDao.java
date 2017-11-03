/*
 *  FormularioIRDao.java
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
 *  09/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.vistas.FormularioIR;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class FormularioIRDao {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(FormularioIRDao.class.getCanonicalName());

    /**
     *
     */
    @PersistenceContext(unitName = "proteusPU")
    private EntityManager em;

    /**
     * Constructor sin argumentos.
     */
    public FormularioIRDao() {
        super();
    }

    public FormularioIR buscarServidorEjercicioFiscal(final Long servidorId, final Long ejercicioFiscalId) throws DaoException {
        try {
            FormularioIR FIR = new FormularioIR();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT a FROM FormularioIR a where  a.ejercicioFiscalId=:ejercicioFiscalId and a.servidorId=:servidorId ");
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("servidorId", servidorId);
            parametros.put("ejercicioFiscalId", ejercicioFiscalId);
            Query query = em.createQuery(sql.toString(), FormularioIR.class);
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                query.setParameter(key, parametros.get(key));
            }
            List<FormularioIR> listaFIR = query.getResultList();
            if (listaFIR.size() > 0) {
                FIR = listaFIR.get(0);
            }
            return FIR;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DaoException(e);
        }
    }

    /**
     * Método para buscar servidores para el Impuesto a la Renta según
     * Parámetros
     *
     * @param vo
     * @param firstRow
     * @param numberOfRows
     * @param orderField
     * @param orderDirection
     * @param filters
     * @return
     * @throws DaoException
     */
    public List<FormularioIR> buscarServidoresP(final BusquedaServidorVO vo, Integer firstRow,
            Integer numberOfRows, String orderField, String orderDirection,
            Map<String, String> filters, Long efId, String codigoUA) throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM FormularioIR a where 1=1");
        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND a.ejercicioFiscalId = :ejercicioFiscalId ");
            parametros.put("ejercicioFiscalId", efId);
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND a.unidadOrganizacionalCodigo= :codigoUA");
            parametros.put("codigoUA", codigoUA);
        }
        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND a.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND a.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.
                    getNumeroDocumentoServidor(), "%"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND a.nombres LIKE :apellidosNombres");
            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.
                    getNombreServidor(), "%"));
        }
        sql.append(" ORDER BY TRIM(BOTH FROM a.nombres) ");
        Query query = em.createQuery(sql.toString(), FormularioIR.class);
        if (firstRow != null) {
            query.setFirstResult(firstRow);
        }
        if (numberOfRows != null) {
            query.setMaxResults(numberOfRows);
        }
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     * consulta para contar servidores
     *
     * @param vo
     * @param filters
     * @param efId
     * @param codigoUA
     * @return
     */
    public int contarFormularios(BusquedaServidorVO vo,
            Map<String, String> filters, Long efId, String codigoUA) {

        Query q = em.createNativeQuery(
                buildConsultaConteoFormularios(vo).toString());

        q.setParameter(1, efId);
        q.setParameter(2, codigoUA);
        q.setParameter(3, vo.getTipoDocumentoServidor());
        q.setParameter(4, UtilCadena.concatenar("'%", vo.getNumeroDocumentoServidor(), "%'"));
        q.setParameter(5, UtilCadena.concatenar("'%", vo.getNombreServidor(), "%'").toUpperCase());
        return Integer.valueOf(q.getSingleResult().toString()).intValue();
    }

    /**
     * genera string sql para el conteo de formularios.
     *
     * @param vo
     * @return
     */
    private StringBuilder buildConsultaConteoFormularios(final BusquedaServidorVO vo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(DISTINCT a.servidor_id)  FROM sch_proteus.formulario_ir a where 1=1");
        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND a.ejercicio_fiscal_id = ?1");
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND a.codigo_unidad_organizacional = ?2");
        }
        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND a.tipo_identificacion = ?3");
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND a.numero_identificacion LIKE ?4");
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND a.nombres LIKE ?5");
        }
        return sql;
    }

    private Object getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
