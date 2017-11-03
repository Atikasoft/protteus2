/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.FiltroNominaVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author atikasoft
 */
@Stateless
@LocalBean
public class NominaDao extends GenericDAO<Nomina, Long> {

    public NominaDao() {
        super(Nomina.class);
    }

    public List<Nomina> buscarPorFiltros(final FiltroNominaVO filtroNominaVO) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            String query
                    = "SELECT a FROM Nomina a WHERE a.institucionEjercicioFiscalId=:institucionEjercicioFiscalId"
                    + " and a.estado IN ('A', 'V', 'R') ";
            parametros.put("institucionEjercicioFiscalId", filtroNominaVO.getPeriodoFiscal());
            if (filtroNominaVO.getPeriodoNomina() != null) {
                query = query.concat("and a.periodoNominaId=:periodoNominaId ");
                parametros.put("periodoNominaId", filtroNominaVO.getPeriodoNomina());
            }
            if (filtroNominaVO.getTipoNomina() != null) {
                query = query.concat("and a.tipoNominaId=:tipoNominaId ");
                parametros.put("tipoNominaId", filtroNominaVO.getTipoNomina());
            }
            if (filtroNominaVO.getCoberturaNomina() == null) {
                query = query.concat("and a.tipoNomina.cobertura IN ('A', 'L', 'T') ");
            } else {
                query = query.concat("and a.tipoNomina.cobertura=:cobertura ");
                parametros.put("cobertura", filtroNominaVO.getCoberturaNomina());
            }
            Query busqueda = getEntityManager().createQuery(query);
            for (String parametro : parametros.keySet()) {
                busqueda.setParameter(parametro, parametros.get(parametro));
                System.out.println(parametro + ":" + parametros.get(parametro));
            }
            busqueda.setHint(QueryHints.FETCH, "a.tipoNomina.regimenLaboral");
            busqueda.setHint(QueryHints.FETCH, "a.periodoNomina");
            return busqueda.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }

    }

    /**
     * Metodo que se encarga de buscar un listado de Nominas que esten vigentes
     * true.
     *
     * @param periodoNominaId
     * @param institucionEjercicioFiscalId
     * @param estado
     * @return Listado nominas
     * @throws DaoException En caso de error
     */
    public List<Nomina> buscarVigentePorPeriodoNominaIdEstado(final long periodoNominaId,
            final long institucionEjercicioFiscalId, String estado) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Nomina.BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID_ESTADO,
                    periodoNominaId, institucionEjercicioFiscalId, estado);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Nominas que esten vigentes
     * true.
     *
     * @param periodoNominaId
     * @param institucionEjercicioFiscalId
     * @param estado
     * @return Listado nominas
     * @throws DaoException En caso de error
     */
    public List<Nomina> buscarVigentePorPeriodoNominaId(final long periodoNominaId,
            final long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Nomina.BUSCAR_VIGENTES_POR_PERIODO_NOMINA_ID,
                    periodoNominaId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * buscar las nominas por parametros
     *
     * @param filtroNominaVO
     * @return
     * @throws DaoException
     */
    public List<Nomina> buscarPorFiltrosNomina(final BusquedaNominaVO filtroNominaVO) throws DaoException {
        try {
            String query = "SELECT a FROM Nomina a where a.institucionEjercicioFiscalId=?1 ";
            int index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                query = query.concat("and a.periodoNominaId=?" + index + " ");
                index++;
            }
            if (filtroNominaVO.getTipoNomina() != null) {
                query = query.concat("and a.tipoNominaId=?" + index + " ");
                index++;
            }

            if (filtroNominaVO.getNumeroNomina() != null && !filtroNominaVO.getNumeroNomina().trim().isEmpty()) {
                query = query.concat("and a.numero like ?" + index + " ");
                index++;
            }
            if (!filtroNominaVO.getDescripcionNomina().isEmpty()) {
                query = query.concat("and UPPER(a.descripcion) like ?" + index + " ");
                index++;
            }
            if (filtroNominaVO.getEstado() != null && !filtroNominaVO.getEstado().trim().isEmpty()) {
                query = query.concat("and a.estado=?" + index + " ");
            }
            Query busqueda = getEntityManager().createQuery(query);
            busqueda.setParameter(1, filtroNominaVO.getPeriodoFiscal());
            index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                busqueda.setParameter(index, filtroNominaVO.getPeriodoNomina());
                index++;
            }
            if (filtroNominaVO.getTipoNomina() != null) {
                busqueda.setParameter(index, filtroNominaVO.getTipoNomina());
                index++;
            }
            if (filtroNominaVO.getNumeroNomina() != null && !filtroNominaVO.getNumeroNomina().trim().isEmpty()) {
                busqueda.setParameter(index, "%" + filtroNominaVO.getNumeroNomina() + "%");
                index++;
            }
            if (!filtroNominaVO.getDescripcionNomina().trim().isEmpty()) {
                busqueda.setParameter(index, "%" + filtroNominaVO.getDescripcionNomina().toUpperCase() + "%");
                index++;
            }
            if (filtroNominaVO.getEstado() != null) {
                busqueda.setParameter(index, filtroNominaVO.getEstado());
            }
            System.out.println("consulta de nominas**********************" + query);
            return busqueda.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }

    }

    /**
     * buscar las nominas por parametros
     *
     * @param filtroNominaVO
     * @return
     * @throws DaoException
     */
    public String buscarPorFiltrosNominaString(final BusquedaNominaVO filtroNominaVO) throws DaoException {
        try {
            // Map<String, Object> parametros = new HashMap<String, Object>();
            String query = "SELECT a FROM Nomina a where a.institucionEjercicioFiscalId=?1 ";
            int index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                query = query.concat("and a.periodoNominaId=?" + index + " ");
                index++;
            }
            if (filtroNominaVO.getTipoNomina() != null) {
                query = query.concat("and a.tipoNominaId=?" + index + " ");
                index++;
            }

            if (filtroNominaVO.getNumeroNomina() != null && !filtroNominaVO.getNumeroNomina().trim().isEmpty()) {
                query = query.concat("and a.numero like ?" + index + " ");
                index++;
            }
            if (!filtroNominaVO.getDescripcionNomina().isEmpty()) {
                query = query.concat("and UPPER(a.descripcion) like ?" + index + " ");
                index++;
            }
            if (filtroNominaVO.getEstado() != null && !filtroNominaVO.getEstado().trim().isEmpty()) {
                query = query.concat("and a.estado=?" + index + " ");
            }
            Query busqueda = getEntityManager().createQuery(query);
            busqueda.setParameter(1, filtroNominaVO.getPeriodoFiscal());
            index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                busqueda.setParameter(index, filtroNominaVO.getPeriodoNomina());
                index++;
            }
            if (filtroNominaVO.getTipoNomina() != null) {
                busqueda.setParameter(index, filtroNominaVO.getTipoNomina());
                index++;
            }
            if (filtroNominaVO.getNumeroNomina() != null && !filtroNominaVO.getNumeroNomina().trim().isEmpty()) {
                busqueda.setParameter(index, "%" + filtroNominaVO.getNumeroNomina() + "%");
                index++;
            }
            if (!filtroNominaVO.getDescripcionNomina().trim().isEmpty()) {
                busqueda.setParameter(index, "%" + filtroNominaVO.getDescripcionNomina().toUpperCase() + "%");
                index++;
            }
            if (filtroNominaVO.getEstado() != null) {
                busqueda.setParameter(index, filtroNominaVO.getEstado());
            }
//             parametros.put("1", 1);
            System.out.println("consulta de nominas**********************" + query);
//            Query sql = getEntityManager().createQuery(query.toString());
//            return obtenerSql(sql, parametros);
            return query;

        } catch (Exception ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }

    }

    /**
     *
     * @param servidorId
     * @param institucionEjeFiscal
     * @param desde
     * @param max
     * @return
     * @throws DaoException
     */
    public List<ResultadoNominaVO> buscarNominaPorServidor(final Long servidorId, final Long institucionEjeFiscal, final Integer desde, final Integer max) throws DaoException {
        StringBuilder query = new StringBuilder("SELECT n.id, ef.nombre, pn.numero");
        Map<Integer, Object> parametros = new HashMap<Integer, Object>();
        sqlNominaPorServidor(query, parametros, servidorId, institucionEjeFiscal);
        query.append(" group by n.id, ef.nombre, pn.numero");
        query.append(" order by ef.nombre desc, pn.numero desc");
        Query q = createQueryNominaPorServidor(query, parametros);
        q.setFirstResult(desde);
        q.setMaxResults(max);
        List<Object[]> l = q.getResultList();
        List<ResultadoNominaVO> resultados = new ArrayList<ResultadoNominaVO>();
        if (l != null && !l.isEmpty()) {
            for (Object[] obj : l) {
                ResultadoNominaVO nominaVO = new ResultadoNominaVO();
                nominaVO.setNomina(new Nomina(Long.parseLong(obj[0].toString())));
                resultados.add(nominaVO);
            }
        }
        return resultados;
    }

    /**
     * Contar las Nominas por Servidor
     *
     * @param servidorId
     * @param institucionEjeFiscal
     * @return
     * @throws DaoException
     */
    public Integer contarNominaPorServidor(final Long servidorId, final Long institucionEjeFiscal) throws DaoException {
        StringBuilder query = new StringBuilder("SELECT count(DISTINCT n.id) ");
        Map<Integer, Object> parametros = new HashMap<Integer, Object>();
        sqlNominaPorServidor(query, parametros, servidorId, institucionEjeFiscal);
        Query q = createQueryNominaPorServidor(query, parametros);
        Integer regs = Integer.valueOf(q.getSingleResult().toString());
        return regs;
    }

    /**
     * Armar el sql necesario para Nomina por Servidor
     *
     * @param query
     * @param parametros
     * @param servidorId
     * @param institucionEjeFiscal
     * @param desde
     * @param max
     * @throws DaoException
     */
    private void sqlNominaPorServidor(final StringBuilder query, final Map<Integer, Object> parametros, final Long servidorId, final Long institucionEjeFiscal) throws DaoException {
        query.append(" FROM sch_proteus.nominas n ");
        query.append(" INNER JOIN sch_proteus.nominas_detalle nd on nd.nomina_id = n.id ");
        query.append(" INNER JOIN sch_proteus.instituciones_ejercicios_fiscales ief on n.institucion_ejercicio_fiscal_id = ief.id ");
        query.append(" INNER JOIN sch_proteus.ejercicios_fiscales ef on ief.ejercicio_fiscal_id = ef.id ");
        query.append(" INNER JOIN sch_proteus.periodos_nominas pn on n.periodo_nomina_id = pn.id ");
        query.append("  where 1=1 ");
        int pos = 1;
        if (institucionEjeFiscal != null) {
            query.append(" AND ninstitucion_ejercicio_fiscal_id= ?");
            query.append(pos);
            parametros.put(pos++, institucionEjeFiscal);
        }
        query.append(" AND nd.servidor_id =?");
        query.append(pos);
        parametros.put(pos++, servidorId);
    }

    /**
     * Crear Query de Nomina por Servidor
     *
     * @param query
     * @param parametros
     * @return
     * @throws DaoException
     */
    private Query createQueryNominaPorServidor(final StringBuilder query, final Map<Integer, Object> parametros) throws DaoException {
        Query q = getEntityManager().createNativeQuery(query.toString());
        for (Map.Entry<Integer, Object> e : parametros.entrySet()) {
            q.setParameter(e.getKey(), e.getValue());
        }
        return q;
    }

    /**
     *
     * @throws DaoException
     */
    public void quitarCalculando() throws DaoException {
        ejecutarPorConsultaNombrada(Nomina.QUITAR_CALCULANDO);
    }
}
