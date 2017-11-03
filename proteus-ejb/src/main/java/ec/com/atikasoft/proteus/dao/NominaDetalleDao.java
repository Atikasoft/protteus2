/*
 *  ClaseDao.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.NominaDetalleVO;
import ec.com.atikasoft.proteus.vo.NominaPagoVO;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import ec.com.atikasoft.proteus.vo.RegistrosSpiVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import ec.com.atikasoft.proteus.vo.ServidorNominaVO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class NominaDetalleDao extends GenericDAO<NominaDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaDetalleDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public NominaDetalleDao() {
        super(NominaDetalle.class);
    }

    /**
     * construir el conteo para paginacion de resultados.
     *
     * @param vo
     * @return
     */
    public int contarResultadoNomina1(ResultadoNominaVO vo) {
        StringBuilder sql = new StringBuilder();

        sql.append(" select count(DISTINCT n.numero_identificacion) "
                + " from sch_proteus.nominas_detalle n "
                + " where n.vigente = 1 and tipo='SER' ");
        if (vo.getNomina().getId() != null) {
            sql.append(" and n.nomina_id = ");
            sql.append(vo.getNomina().getId());
        }
        if (vo.getFiltroNumeroIdentificacion() != null && !vo.getFiltroNumeroIdentificacion().trim().isEmpty()) {
            sql.append(" and n.numero_identificacion like '%");
            sql.append(vo.getFiltroNumeroIdentificacion());
            sql.append("%'");
        }
        if (vo.getFiltroNombreServidor() != null && !vo.getFiltroNombreServidor().trim().isEmpty()) {
            sql.append(" and n.nombres like '%");
            sql.append(vo.getFiltroNombreServidor());
            sql.append("%'");
        }
        Query q = getEntityManager().createNativeQuery(sql.toString());
        Integer regs = Integer.valueOf(q.getSingleResult().toString()).intValue();
        return regs;

    }

    /**
     * Asigna los valores recuperados con la sentencia construida, y asigna los valores recuperados, Obtiene los
     * resultados de la Nómina.
     *
     * @param vo ResultadoNominaVO
     * @return
     * @throws DaoException
     */
    public List<ResultadoNominaVO> buscarResultadoNomina(final ResultadoNominaVO vo) throws DaoException {
        List<Object[]> lista;
        List<ResultadoNominaVO> listaResultados = new ArrayList<ResultadoNominaVO>();
        try {
            StringBuilder sql = construirQueryResultadoNomina(vo.getNomina().getId(), vo.getFiltroNumeroIdentificacion(),
                    vo.getFiltroNombreServidor());
            Query query = getEntityManager().createNativeQuery(sql.toString());
            if (vo.getInicioConsulta() != null) {
                query.setFirstResult(vo.getInicioConsulta());
            }
            if (vo.getFinConsulta() != null) {
                query.setMaxResults(vo.getFinConsulta());
            }
            lista = query.getResultList();
            vo.inicializarTotales();
            for (Object[] o : lista) {
                ResultadoNominaVO v = new ResultadoNominaVO();
                v.setTipoIdentificacion(o[0] == null ? null : o[0].toString());
                v.setNumeroIdentificacion(o[1] == null ? null : o[1].toString());
                v.setNombreServidor(o[2] == null ? null : o[2].toString());
                v.setNombreModalidadLaboral(o[3] == null ? null : o[3].toString());
                v.setIngresos(new BigDecimal(o[4] == null ? BigDecimal.ZERO.toString() : o[4].toString()));
                v.setDescuentos(new BigDecimal(o[5] == null ? BigDecimal.ZERO.toString() : o[5].toString()));
                v.setAportePatronal(new BigDecimal(o[6] == null ? BigDecimal.ZERO.toString() : o[6].toString()));
                v.setLiquidoAPagar(new BigDecimal(o[7] == null ? BigDecimal.ZERO.toString() : o[7].toString()));
                v.setServidorId(o[8] == null ? null : ((BigDecimal) o[8]).longValue());

                vo.setTotalIngresos(vo.getTotalIngresos().add(v.getIngresos()));
                vo.setTotalDescuentos(vo.getTotalDescuentos().add(v.getDescuentos()));
                vo.setTotalAportePatronal(vo.getTotalAportePatronal().add(v.getAportePatronal()));
                vo.setTotalLiquidoAPagar(vo.getTotalLiquidoAPagar().add(v.getLiquidoAPagar()));
                vo.setTotalServidores(vo.getTotalServidores().add(BigDecimal.ONE));
                listaResultados.add(v);
            }
            return listaResultados;
        } catch (DaoException e) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error obtener resultados de la nómina " + e);
        }
    }

    /**
     * Obtiene los totales de los resultados de la nómina.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public ResultadoNominaVO buscarTotalResultadoNomina(final ResultadoNominaVO vo) throws DaoException {
        try {
            StringBuilder sql = construirQueryTotalesResultadoNomina(vo.getNomina().getId(), vo.
                    getFiltroNumeroIdentificacion(),
                    vo.getFiltroNombreServidor());
            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            vo.inicializarTotales();
            for (Object[] o : lista) {
                vo.setTotalIngresos(new BigDecimal(o[0] == null ? "0" : o[0].toString()));
                vo.setTotalDescuentos(new BigDecimal(o[1] == null ? BigDecimal.ZERO.toString() : o[1].toString()));
                vo.setTotalAportePatronal(new BigDecimal(o[2] == null ? BigDecimal.ZERO.toString() : o[2].toString()));
                vo.setTotalLiquidoAPagar(new BigDecimal(o[3] == null ? BigDecimal.ZERO.toString() : o[3].toString()));
                vo.setTotalServidores(new BigDecimal(o[4] == null ? BigDecimal.ZERO.toString() : o[4].toString()));
                break;
            }
            return vo;
        } catch (DaoException e) {
            Logger.getLogger(PlanificacionVacacionDao.class.getName()).log(Level.SEVERE, null, e);
            throw new DaoException("Error obtener resultados de la nómina " + e);
        }
    }

    /**
     * Construcción de sentencia sql para obtener los resultados de la nómina
     *
     * @param idNomina
     * @param numeroIdentificacion
     * @param nombreServidor
     * @return
     * @throws DaoException
     */
    public StringBuilder construirQueryResultadoNomina(final Long idNomina, final String numeroIdentificacion,
            final String nombreServidor) throws DaoException {

        StringBuilder sql = new StringBuilder();

        sql.append("select tipo_identificacion, numero_identificacion, nombres, nombre_modalidad_laboral, "
                + " ISNULL(sum(valor_descontado_rubro_ingreso),0) ingresos, "
                + " ISNULL(sum(valor_descontado_rubro_descuentos),0) descuentos, "
                + " ISNULL(sum(valor_descontado_rubro_aportes),0) aportes,"
                + " ISNULL(sum(valor_descontado_rubro_ingreso),0) - ISNULL(sum(valor_descontado_rubro_descuentos),0) liquido, "
                + " servidor_id "
                + " from sch_proteus.nominas_detalle "
                + " where vigente = 1 and tipo='SER' ");

        if (idNomina != null) {
            sql.append(" and nomina_id = ");
            sql.append(idNomina);
        }
        if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
            sql.append(" and numero_identificacion like ");
            sql.append(UtilCadena.concatenar("'%", numeroIdentificacion, "%'"));
        }
        if (nombreServidor != null && !nombreServidor.trim().isEmpty()) {
            sql.append(" and nombres LIKE ");
            sql.append(UtilCadena.concatenar("'%", nombreServidor, "%'"));
        }
        sql.append(" group by tipo_identificacion, numero_identificacion, nombres, nombre_modalidad_laboral,servidor_id");
        sql.append(" ORDER BY nombres ");
//        LOG.log(Level.INFO, "SQL RESULTADOS NOMINA==============================={0}", sql.toString());
        return sql;
    }

    public StringBuilder construirQueryTotalesResultadoNomina(final Long idNomina, final String numeroIdentificacion,
            final String nombreServidor) throws DaoException {

        StringBuilder sql = new StringBuilder();

        sql.append("select "
                + " ISNULL(sum(valor_descontado_rubro_ingreso),0) ingresos, "
                + " ISNULL(sum(valor_descontado_rubro_descuentos),0) descuentos, "
                + " ISNULL(sum(valor_descontado_rubro_aportes),0) aportes,"
                + " ISNULL(sum(valor_descontado_rubro_ingreso),0) - ISNULL(sum(valor_descontado_rubro_descuentos),0) liquido,"
                + " COUNT (distinct numero_identificacion) total"
                + " from sch_proteus.nominas_detalle "
                + " where vigente = 1 and tipo='SER' ");

        if (idNomina != null) {
            sql.append(" and nomina_id = ");
            sql.append(idNomina);
        }
        if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
            sql.append(" and numero_identificacion like ");
            sql.append(UtilCadena.concatenar("'%", numeroIdentificacion, "%'"));
        }
        if (nombreServidor != null && !nombreServidor.trim().isEmpty()) {
            sql.append(" and nombres LIKE ");
            sql.append(UtilCadena.concatenar("'%", nombreServidor, "%'"));
        }
        return sql;
    }

    /**
     *
     * @param nominaId
     * @param valor
     * @return
     * @throws DaoException
     */
    public List<Object[]> buscarLiquidoAPagarNegativos(final Long nominaId) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("   r.tipo_identificacion,");
            sql.append("   r.numero_identificacion,");
            sql.append("   r.nombres ");
            sql.append("FROM ");
            sql.append("  (");
            sql.append("       SELECT ");
            sql.append("           nd.tipo_identificacion,");
            sql.append("           nd.numero_identificacion,");
            sql.append("           nd.nombres,");
            sql.append("           round(sum(nd.valor_descontado_rubro_ingreso),2) ingreso,");
            sql.append("           round(sum(nd.valor_descontado_rubro_descuentos),2) descuento");
            sql.append("        FROM sch_proteus.nominas_detalle nd");
            sql.append("        WHERE nd.vigente=1 AND");
            sql.append("              nd.tipo='SER' AND");
            sql.append("              nomina_id=").append(nominaId);
            sql.append("        GROUP BY nd.tipo_identificacion,nd.numero_identificacion,nd.nombres");
            sql.append("  ) r ");
            sql.append("WHERE r.ingreso<r.descuento");
            System.out.println("sql:" + sql);
            Query query = getEntityManager().createNativeQuery(sql.toString());
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param valor
     * @return
     * @throws DaoException
     */
    public List<Object[]> buscarMaximoIngreso(final Long nominaId, final BigDecimal valor) throws
            DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("   r.tipo_identificacion,");
            sql.append("   r.numero_identificacion,");
            sql.append("   r.nombres,");
            sql.append("   r.ingreso ");
            sql.append("FROM ");
            sql.append("  (");
            sql.append("       SELECT ");
            sql.append("           nd.tipo_identificacion,");
            sql.append("           nd.numero_identificacion,");
            sql.append("           nd.nombres,");
            sql.append("           round(sum(nd.valor_descontado_rubro_ingreso),2) ingreso");
            sql.append("        FROM sch_proteus.nominas_detalle nd");
            sql.append("        WHERE nd.vigente=1 AND");
            sql.append("              nd.tipo='SER' AND");
            sql.append("              nomina_id=").append(nominaId);
            sql.append("        GROUP BY nd.tipo_identificacion,nd.numero_identificacion,nd.nombres");
            sql.append("  ) r ");
            sql.append("WHERE r.ingreso>").append(valor.toString());
            System.out.println("sql:" + sql);
            Query query = getEntityManager().createNativeQuery(sql.toString());
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param tipo
     * @param valor
     * @return
     * @throws DaoException
     */
    public List<Object[]> buscarMinimoPagar(final Long nominaId, final String tipo, final BigDecimal valor) throws
            DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT resultado.tipo_identificacion,");
            sql.append("       resultado.numero_identificacion,");
            if ("F".equals(tipo)) {
                sql.append(valor).append("-resultado.liquido recuperacion");
            } else {
                sql.append("round(resultado.ingreso*").append(valor).append("/100,2)-resultado.liquido recuperacion");
            }
            sql.append(" FROM  (");
            sql.append("        SELECT ");
            sql.append("           nd.tipo_identificacion,");
            sql.append("           nd.numero_identificacion,");
            sql.append("           round(sum(nd.valor_descontado_rubro_ingreso),2) ingreso,");
            sql.append("           round(sum(nd.valor_descontado_rubro_descuentos),2) descuento,");
            sql.append("           round(sum(nd.valor_descontado_rubro_aportes),2) aporte,");
            sql.append(
                    "           round(sum(nd.valor_descontado_rubro_ingreso),2)-round(sum(nd.valor_descontado_rubro_descuentos),2) liquido");
            sql.append("        FROM sch_proteus.nominas_detalle nd");
            sql.append("        WHERE vigente=1 AND nomina_id=").append(nominaId);
            sql.append("        GROUP BY nd.tipo_identificacion,nd.numero_identificacion");
            sql.append("      ) resultado");
            if ("F".equals(tipo)) {
                sql.append(" WHERE liquido<").append(valor);
            } else {
                sql.append(" WHERE resultado.liquido<round(resultado.ingreso*").append(valor).append("/100,2)");
            }
            Query query = getEntityManager().createNativeQuery(sql.toString());
            System.out.println("sql:" + sql);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param valor
     * @return
     * @throws DaoException
     */
    public List<RegistrosSpiVO> obtenerDatosParaArchivoSPI(final Long nominaId) throws
            DaoException {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT *");
            sql.append("FROM ");
            sql.append("  (");
            sql.append("       SELECT ");
            sql.append("           nd.numero_identificacion,");
            sql.append("           n.numero,");
            sql.append("           REPLACE (nd.nombres , CHAR(13) + CHAR(10) , ' ' ) as apellidos_nombres,");
            sql.append("           CASE WHEN b.codigo IS NULL then  '' ELSE  b.codigo END as banco,");
            sql.append("           CASE WHEN cb.numero_cuenta IS NULL then  '' ELSE  cb.numero_cuenta END as numero_cuenta,");
            sql.append("           CASE WHEN cb.tipo_cuenta='C' then  '1' ELSE ");
            sql.append("                CASE WHEN cb.tipo_cuenta='H' then  '2' ELSE ");
            sql.append("                    CASE WHEN cb.tipo_cuenta='K' then  '' ELSE '2'");
            sql.append("                    END");
            sql.append("                END");
            sql.append("           END  as tipo_cuenta,");
            sql.append("       SUM(nd.valor_descontado_rubro_ingreso)-");
            sql.append("            CASE WHEN SUM(nd.valor_descontado_rubro_descuentos) is null THEN 0 ELSE ");
            sql.append("                 SUM(nd.valor_descontado_rubro_descuentos)");
            sql.append("            END  as valor,");
            sql.append("       '40101' as codigo,");
            sql.append("       'PAGO SUELDO '+ n.descripcion as descripcion");

            sql.append("        FROM sch_proteus.nominas_detalle nd");

            sql.append("        JOIN sch_proteus.nominas n on nd.nomina_id=n.id ");
            sql.append("        JOIN sch_proteus.servidor s on nd.servidor_id = s.id ");
            sql.append("        LEFT JOIN sch_proteus.cuentas_bancarias cb on cb.servidor_id= s.id and cb.paga_nomina=1");
            sql.append("        LEFT JOIN sch_proteus.bancos b on cb.banco_id = b.id");

            sql.append("        WHERE           ");
            sql.append("              nomina_id=").append(nominaId);
            sql.append("        GROUP BY nd.numero_identificacion,n.numero,nd.nombres,b.codigo,cb.numero_cuenta,cb.tipo_cuenta,n.descripcion");
            sql.append("  ) a ");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<RegistrosSpiVO> registros = new ArrayList<>();

            for (Object[] o : lista) {
                RegistrosSpiVO v = new RegistrosSpiVO();
                v.setNumeroIdentificacion(o[0] == null ? null : o[0].toString());
                v.setNumero(o[1] == null ? null : o[1].toString());
                v.setApellidosNombres(o[2] == null ? null : o[2].toString());
                v.setBanco(o[3] == null ? null : o[3].toString());
                v.setCuenta(o[4] == null ? null : o[4].toString());
                v.setTipoCuenta(o[5] == null ? null : o[5].toString());
                v.setValor(new BigDecimal(o[6] == null ? BigDecimal.ZERO.toString() : o[6].toString()));
                v.setCodigo(o[7] == null ? BigDecimal.ZERO.toString() : o[7].toString());
                v.setDescripcion(o[8] == null ? null : o[8].toString());

                registros.add(v);
            }
            return registros;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @return
     * @throws DaoException
     */
    public List<NominaDetalle> buscarServidoresConMinimoAPagar(final Long nominaId, final String tipoIdentificacion,
            final String numeroIdentificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NominaDetalle.BUSCAR_SERVIDOR_MINIMO_PAGAR, nominaId, tipoIdentificacion,
                    numeroIdentificacion);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nomina.
     *
     * @param idNomina String
     * @return List
     * @throws DaoException DaoException
     */
    public List<NominaDetalle> buscarTodosPorNomina(final Long idNomina) throws DaoException {
        try {
            return buscarPorConsultaNombrada(NominaDetalle.BUSCAR_POR_NOMINA, idNomina);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nomina.
     *
     * @param idNomina String
     * @param inicio
     * @param longitud
     * @return List
     * @throws DaoException DaoException
     */
    public List<NominaDetalle> buscarTodosPorNomina(final Long idNomina, Integer inicio, Integer longitud) throws DaoException {
        try {
            return buscarPorConsultaNombradaPaginacion(NominaDetalle.BUSCAR_POR_NOMINA, inicio, longitud, idNomina);
        } catch (DaoException ex) {
            Logger.getLogger(NominaDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Verifica si existe registros en la nomina.
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public Boolean existeNomina(final Long nominaId) throws DaoException {
        try {
            Boolean resultado = Boolean.FALSE;
            List<NominaDetalle> lista = buscarPorConsultaNombradaPaginacion(NominaDetalle.BUSCAR_POR_NOMINA, 0, 1,
                    nominaId);
            if (!lista.isEmpty()) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nomina.
     *
     * @param idNomina
     * @param numeroIdentificacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<NominaDetalle> buscarTodosPorNominaYServidor(final Long idNomina, final String numeroIdentificacion)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(NominaDetalle.BUSCAR_POR_NOMINA_Y_SERVIDOR, idNomina, numeroIdentificacion);

        } catch (DaoException ex) {
            Logger.getLogger(NominaDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nomina.
     *
     * @param idNomina
     * @param idInstitucionEjericioFiscal
     * @param sociedad
     * @param retencion
     * @param cobertura
     * @param inicio
     * @param longitud
     * @return List
     * @throws DaoException DaoException
     */
    public List<NominaDetalle> buscarDetallesParaArchivoSipari(final Long idNomina,
            final Long idInstitucionEjericioFiscal, final String sociedad, Boolean retencion,
            String cobertura, final Integer inicio, Integer longitud) throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();

        String tipo = "SER";
        StringBuilder sql;
        if (cobertura.equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            sql
                    = new StringBuilder(
                            "SELECT DISTINCT o FROM NominaDetalle o, Liquidacion l "
                            + " WHERE o.vigente=true AND o.nomina.id = :idNomina "
                            + " AND o.nomina.institucionEjercicioFiscalId = :ejercicioFiscal  "
                            + " AND o.nomina.id = l.nomina.id  AND l.vigente=true "
                            + " AND l.servidor.numeroIdentificacion = o.numeroIdentificacion"
                            + " AND l.servidor.tipoIdentificacion = o.tipoIdentificacion"
                            + " AND l.distributivoDetalle.distributivo.idInstitucionEjercicioFiscal = o.nomina.institucionEjercicioFiscalId ");
            if (sociedad != null) {
                sql.append(" AND a.distributivoDetalle.unidadPresupuestaria.sociedad = :sociedad ");
                parametros.put("sociedad", sociedad);
            }
            sql.append(" AND o.tipo = :tipo "
                    + " ORDER BY o.prioridad DESC ");
        } else {
            sql = new StringBuilder(
                    "SELECT o FROM NominaDetalle o "
                    + " WHERE o.vigente = true AND o.nomina.id = :idNomina "
                    + " AND o.nomina.institucionEjercicioFiscalId = :ejercicioFiscal  "
                    + " AND EXISTS ( SELECT a FROM DistributivoDetalle a "
                    //                    + " WHERE a.vigente =true AND a.idServidor IS NOT NULL "
                    + " WHERE a.idServidor IS NOT NULL "
                    + " AND a.idServidor = o.servidorId "
                    //                    + " AND a.distributivo.vigente = true "
                    + " AND a.distributivo.idInstitucionEjercicioFiscal = o.nomina.institucionEjercicioFiscalId ");

            if (sociedad != null) {
                sql.append(" AND a.unidadPresupuestaria.sociedad = :sociedad ");
                parametros.put("sociedad", sociedad);
            }
            sql.append(") AND o.tipo = :tipo ");
            if (retencion) {
                sql.append(" AND o.retencionJudicial=true AND o.codigoRubroIngreso IS NULL ");
            }
            sql.append(" ORDER BY o.id DESC ");
        }
        parametros.put("idNomina", idNomina);
        parametros.put("ejercicioFiscal", idInstitucionEjericioFiscal);
        parametros.put("tipo", tipo);
        //parametros.put("retencion", retencion);
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);
        return query.getResultList();
    }

    /**
     *
     */
    public void crearVistaMaterializadaNominas() {
        // elimina indice.
        String validaIndice = "SELECT * FROM sys.indexes WHERE name='idx_nominas_detalles_ir'";
        if (!getEntityManager().createNativeQuery(validaIndice).getResultList().isEmpty()) {
            String eliminaIndice = "DROP INDEX sch_proteus.nominas_detalles_ir.IDX_nominas_detalles_ir";
            System.out.println(eliminaIndice);
            getEntityManager().createNativeQuery(eliminaIndice).executeUpdate();
        }
        // elimina tabla.
        String validaTabla = "SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'sch_proteus.nominas_detalles_ir')";
        if (!getEntityManager().createNativeQuery(validaTabla).getResultList().isEmpty()) {
            String eliminaTabla = "DROP TABLE sch_proteus.nominas_detalles_ir";
            System.out.println(eliminaTabla);
            getEntityManager().createNativeQuery(eliminaTabla).executeUpdate();
        }

        // crea tabla.
        String creaTabla = "SELECT * INTO sch_proteus.nominas_detalles_ir FROM sch_proteus.vista_nominas_ir";
        System.out.println(creaTabla);
        getEntityManager().createNativeQuery(creaTabla).executeUpdate();
        // crear indices.
        String creaIndice = "CREATE INDEX IDX_nominas_detalles_ir ON"
                + " sch_proteus.nominas_detalles_ir(periodo, ejercicio_fiscal_id, servidor_id) ";
        System.out.println(creaIndice);
        getEntityManager().createNativeQuery(creaIndice).executeUpdate();
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public BigDecimal buscarRMU(final Long nominaId, final Long servidorId) throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT nd.rmu  ");
        sql.append("FROM NominaDetalle nd WHERE ");
        sql.append("nd.vigente=true AND ");
        sql.append("nd.nomina.id=:nominaId AND ");
        sql.append("nd.servidorId=:servidorId ");
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("nominaId", nominaId);
        parametros.put("servidorId", servidorId);
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        BigDecimal valor = (BigDecimal) query.getSingleResult();
        if (valor == null) {
            valor = BigDecimal.ZERO;
        }
        return UtilNumeros.redondear(valor, 2, true);
    }

    /**
     * Calcula el total de ingresos gravables de un servidor en periodo especifico.
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularTotalIngresosGravables(final Long nominaId, final Long servidorId) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT SUM(nd.valorDescontadoRubroIngreso)-SUM(nd.valorDescontadoRubroDescuentos) ");
            sql.append("FROM NominaDetalle nd WHERE ");
            sql.append("nd.vigente=true AND ");
            sql.append("nd.nomina.id=:nominaId AND ");
            sql.append("nd.servidorId=:servidorId AND ");
            sql.append("nd.tipo='SER' AND ");
            sql.append("nd.gravable=true ");
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nominaId", nominaId);
            parametros.put("servidorId", servidorId);
            Query query = getEntityManager().createQuery(sql.toString());
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                query.setParameter(key, parametros.get(key));
            }
            query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
            BigDecimal valor = (BigDecimal) query.getSingleResult();
            if (valor == null) {
                valor = BigDecimal.ZERO;
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Calcula el total de ingresos gravables de un servidor en periodo especifico.
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularTotalIngresoPorProyeccionIR(final Long nominaId, final Long servidorId) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT SUM(nd.valorDescontadoRubroIngreso)-SUM(nd.valorDescontadoRubroDescuentos) ");
            sql.append("FROM NominaDetalle nd WHERE ");
            sql.append("nd.vigente=true AND ");
            sql.append("nd.nomina.id=:nominaId AND ");
            sql.append("nd.servidorId=:servidorId AND ");
            sql.append("nd.tipo='SER' AND ");
            sql.append("nd.esProyeccionImpuestoRenta=true ");
            sql.append("nd.gravable=true ");
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("nominaId", nominaId);
            parametros.put("servidorId", servidorId);
            Query query = getEntityManager().createQuery(sql.toString());
            Set<String> keys = parametros.keySet();
            for (String key : keys) {
                query.setParameter(key, parametros.get(key));
            }
            BigDecimal valor = (BigDecimal) query.getSingleResult();
            if (valor == null) {
                valor = BigDecimal.ZERO;
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     * Permite obtener los datos para poblar tablas que generan archivo sipari de empleados
     *
     * @param idNomina
     * @param idInstitucionEjericioFiscal
     * @param sociedad
     * @param retencion
     * @param cobertura
     * @param inicio
     * @param longitud
     * @return
     * @throws DaoException
     */
    public List<Servidor> buscarDetallesParaArchivoSipariEmpleados(
            final Long idNomina, final Long idInstitucionEjericioFiscal, final String sociedad, Boolean retencion,
            String cobertura,
            final Integer inicio, Integer longitud)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();

        String tipo = "SER";

        StringBuilder sql;
        if (cobertura.equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            sql = new StringBuilder(
                    " SELECT a.servidor FROM Liquidacion a "
                    + " WHERE a.vigente = true AND a.nomina.id = : idNomina"
                    + " AND a.institucionEjercicioFiscal.id = :ejercicioFiscal");
            if (sociedad != null) {
                sql.append(" AND a.distributivoDetalle.distributivo.unidadPresupuestaria.sociedad = :sociedad ");
                parametros.put("sociedad", sociedad);
            }
            sql.append(" AND EXISTS ( SELECT o.numeroIdentificacion FROM NominaDetalle o "
                    + " WHERE o.vigente=true AND o.nomina.id = :idNomina "
                    + " AND a.servidor.numeroIdentificacion = o.numeroIdentificacion "
                    + " AND a.servidor.tipoIdentificacion = o.tipoIdentificacion "
                    + " AND o.tipo = :tipo "
                    + " AND o.retencionJudicial = :retencion "
                    + " AND o.nomina.institucionEjercicioFiscal.id = :ejercicioFiscal)"
                    + " ORDER BY a.servidor.apellidos, a.servidor.numeroIdentificacion ");
        } else {
            sql = new StringBuilder(
                    " SELECT DISTINCT a.servidor FROM DistributivoDetalle a "
                    //+ " WHERE a.vigente = true AND a.idServidor IS NOT NULL "
                    + " WHERE a.idServidor IS NOT NULL "
                    + " AND a.distributivo.vigente = true "
                    + " AND a.distributivo.institucionEjercicioFiscal.id = :ejercicioFiscal");
            if (sociedad != null) {
                sql.append(" AND a.unidadPresupuestaria.sociedad = :sociedad ");
                parametros.put("sociedad", sociedad);
            }
            sql.append("AND EXISTS ("
                    + "  SELECT o.numeroIdentificacion FROM NominaDetalle o "
                    + " WHERE o.vigente=true AND o.nomina.id = :idNomina "
                    + " AND a.servidor.numeroIdentificacion = o.numeroIdentificacion "
                    + " AND o.tipoIdentificacion = a.servidor.tipoIdentificacion "
                    + " AND a.distributivo.institucionEjercicioFiscal.id = o.nomina.institucionEjercicioFiscal.id "
                    + " AND o.tipo = :tipo )"
                    //                    + " AND o.retencionJudicial = :retencion )"
                    + " ORDER BY a.servidor.apellidos, a.servidor.numeroIdentificacion ");
        }
        parametros.put("idNomina", idNomina);
        parametros.put("ejercicioFiscal", idInstitucionEjericioFiscal);
        parametros.put("tipo", tipo);
//        parametros.put("retencion", retencion);
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        // System.out.println("SQL:" + obtenerSql(query, parametros));
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);
        return query.getResultList();
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int eliminar(final Long nominaId) throws DaoException {
        try {
            return ejecutarPorConsultaNombrada(NominaDetalle.ELIMINAR, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param distributivoDetalleId
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarPorServidor(final Long nominaId, final Long distributivoDetalleId) throws
            DaoException {
        try {
            ejecutarPorConsultaNombrada(NominaDetalle.ELIMINAR_POR_SERVIDOR, nominaId, distributivoDetalleId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Contar ocurrencia mensual.
     *
     * @param institucionEjercicioFiscalId
     * @param tipoNominaId
     * @param periodoNominaId
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public Long contarMensualmente(final Long institucionEjercicioFiscalId, final Long tipoNominaId,
            final Long periodoNominaId, final Long nominaId, final Long servidorId) throws DaoException {
        try {
            return (Long) buscarUnicoPorConsultaNombrada(NominaDetalle.CONTAR_MENSUALMENTE, institucionEjercicioFiscalId,
                    tipoNominaId, periodoNominaId, nominaId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Contar ocurrencia mensual.
     *
     * @param institucionEjercicioFiscalId
     * @param tipoNominaId
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public Long contarAnualmente(final Long institucionEjercicioFiscalId, final Long tipoNominaId, final Long nominaId,
            final Long servidorId) throws DaoException {
        try {
            return (Long) buscarUnicoPorConsultaNombrada(NominaDetalle.CONTAR_ANUALMENTE, institucionEjercicioFiscalId,
                    tipoNominaId, nominaId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public Long contarServidoresEjecutados(final Long nominaId) throws DaoException {
        return (Long) buscarUnicoPorConsultaNombrada(NominaDetalle.CONTAR_SERVIDORES_EJECUTADOS, nominaId);
    }

    /**
     * buscar las nominas por parametros
     *
     * @param filtroNominaVO
     * @return
     * @throws DaoException
     */
    public List<Nomina> buscarPorFiltrosNominaDetalle(final BusquedaNominaVO filtroNominaVO) throws DaoException {
        try {
            String query = "SELECT DISTINCT a.nomina FROM NominaDetalle a "
                    + " where a.nomina.institucionEjercicioFiscalId=?1 ";
            // + " and a.nomina.estado='P' ";
            int index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                query = query.concat("and a.nomina.periodoNominaId=?" + index + " ");
                index++;
            }
            if (filtroNominaVO.getServidorLogueado() != null) {
                query = query.concat("and a.servidorId=?" + index + " ");
                index++;
            }
            Query busqueda = getEntityManager().createQuery(query);
            busqueda.setParameter(1, filtroNominaVO.getPeriodoFiscal());
            index = 2;
            if (filtroNominaVO.getPeriodoNomina() != null) {
                busqueda.setParameter(index, filtroNominaVO.getPeriodoNomina());
                index++;
            }
            if (filtroNominaVO.getServidorLogueado() != null) {
                busqueda.setParameter(index, filtroNominaVO.getServidorLogueado());
                index++;
            }
            return busqueda.getResultList();
        } catch (Exception ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }

    }

    /**
     * Calcula la capacidad de pago de acuerdo a los filtros.
     *
     * @param filtroNominaVO
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularCapacidadPago(final BusquedaNominaVO filtroNominaVO)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        BigDecimal totalCapacidadPago = BigDecimal.ZERO;
        Map<String, Object> parametros = new HashMap<String, Object>();
        sql.append("SELECT a FROM NominaDetalle a ");
        sql.append(" WHERE a.vigente=true AND a.tipo='SER' ");
        if (filtroNominaVO.getIdNomina() != null) {
            sql.append(" AND a.nomina.id = :nominaId");
            parametros.put("nominaId", filtroNominaVO.getIdNomina());
        }
        if (filtroNominaVO.getServidorLogueado() != null) {
            sql.append(" AND a.servidorId = :servidorId");
            parametros.put("servidorId", filtroNominaVO.getServidorLogueado());
        }
        if (filtroNominaVO.getTipoNomina() != null) {
            sql.append(" AND a.nomina.tipoNomina.id = :tipoNominaId");
            parametros.put("tipoNominaId", filtroNominaVO.getTipoNomina());
        }
        if (filtroNominaVO.getPeriodoNomina() != null) {
            sql.append(" AND a.nomina.periodoNomina.id = :periodoNominaId");
            parametros.put("periodoNominaId", filtroNominaVO.getPeriodoNomina());
        }
        if (filtroNominaVO.getPeriodoFiscal() != null) {
            sql.append(" AND a.nomina.institucionEjercicioFiscalId = :ie");
            parametros.put("ie", filtroNominaVO.getPeriodoFiscal());
        }
        if (filtroNominaVO.getEsCapacidadPago() != null && filtroNominaVO.getEsCapacidadPago()) {
            sql.append(
                    " AND  ("
                    + " a.codigoRubroAportes IN ( SELECT b.codigo FROM Rubro b WHERE  b.vigente = true AND b.calculoCapacidadPago = true)"
                    + " OR a.codigoRubroDescuentos IN ( SELECT c.codigo FROM Rubro c WHERE  c.vigente = true AND c.calculoCapacidadPago = true)"
                    + " OR a.codigoRubroIngreso IN ( SELECT d.codigo FROM Rubro d WHERE  d.vigente = true AND d.calculoCapacidadPago = true))");
        }
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        List<NominaDetalle> resultList = query.getResultList();
        for (NominaDetalle nd : resultList) {
            totalCapacidadPago = totalCapacidadPago.subtract(nd.getValorDescontadoRubroAportes() != null ? nd.
                    getValorDescontadoRubroAportes() : BigDecimal.ZERO);
            totalCapacidadPago = totalCapacidadPago.subtract(nd.getValorDescontadoRubroDescuentos() != null ? nd.
                    getValorDescontadoRubroDescuentos() : BigDecimal.ZERO);
            totalCapacidadPago = totalCapacidadPago.add(nd.getValorDescontadoRubroIngreso() != null ? nd.
                    getValorDescontadoRubroIngreso() : BigDecimal.ZERO);
        }
        if (totalCapacidadPago.compareTo(BigDecimal.ZERO) < 0) {
            totalCapacidadPago = BigDecimal.ZERO;
        }
        return totalCapacidadPago;
    }

    /**
     *
     * @param objeto
     * @param nominaId
     * @param inicio
     * @param pagina
     * @return
     * @throws DaoException
     */
    public List<NominaPagoVO> buscarPagos(ObjetoNominaVO objeto, int inicio, int pagina) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("nd.servidor_id, ");
            sql.append("nd.tipo_identificacion, ");
            sql.append("nd.numero_identificacion, ");
            sql.append("nd.nombres, ");
            sql.append("sum(isnull(nd.valor_descontado_rubro_ingreso,0)) as valor_ingresos, ");
            sql.append("sum(isnull(nd.valor_descontado_rubro_descuentos,0)) as valor_descuentos, ");
            sql.append("sum(isnull(nd.valor_descontado_rubro_aportes,0)) as valor_aportes, ");
            sql.append("sum(isnull(nd.valor_descontado_rubro_ingreso,0))-sum(isnull(nd.valor_descontado_rubro_descuentos,0)) as liquido_a_pagar, ");
            sql.append("d.unidad_organizacional_id, ");
            sql.append("dd.unidad_presupuestaria_id ");
            sql.append("from sch_proteus.nominas_detalle nd  ");
            sql.append("LEFT JOIN sch_proteus.distributivo_detalles dd on nd.distributivo_detalle_id=dd.id  ");
            sql.append("LEFT JOIN sch_proteus.distributivo d on dd.distributivo_id=d.id  ");
            sql.append("where nd.nomina_id=").append(objeto.getNomina().getId()).append(" ");
            if (!objeto.getEjecucionNominaVO().getTodos()) {
                sql.append(" AND ( ");
                boolean primeraVez = true;
                for (Servidor s : objeto.getEjecucionNominaVO().getServidores()) {
                    if (!primeraVez) {
                        sql.append(" OR ");
                    }
                    sql.append(" nd.servidor_id = ").append(s.getId()).append(" ");
                    primeraVez = false;
                }
                sql.append(" ) ");
            }
            sql.append("group by nd.servidor_id,nd.tipo_identificacion,nd.numero_identificacion, nd.nombres,d.unidad_organizacional_id,dd.unidad_presupuestaria_id ");
            //System.out.println(sql.toString());
            Query query = getEntityManager().createNativeQuery(sql.toString());
            query.setParameter("nominaId", objeto.getNomina().getId());
            query.setMaxResults(pagina);
            query.setFirstResult(inicio);
            List<Object[]> lista = query.getResultList();
            List<NominaPagoVO> pagos = new ArrayList<NominaPagoVO>();
            for (Object[] obj : lista) {
                pagos.add(objetoToNominaPago(obj));
            }
            return pagos;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param institucionEjercicioFiscalId
     * @param tipoNominaId
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public List<ServidorNominaVO> buscarOcurrenciaAnual(final Long institucionEjercicioFiscalId, final Long tipoNominaId,
            final Long nominaId) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select nd.servidor_id,nd.tipo_identificacion,nd.numero_identificacion,nd.nombres ");
            sql.append("from sch_proteus.nominas_detalle nd ");
            sql.append("JOIN sch_proteus.nominas n on nd.nomina_id=n.id ");
            sql.append("JOIN sch_proteus.instituciones_ejercicios_fiscales ief on n.institucion_ejercicio_fiscal_id=ief.id ");
            sql.append("JOIN sch_proteus.tipos_nominas tn on n.tipo_nomina_id=tn.id ");
            sql.append("where  ");
            sql.append("ief.id=").append(institucionEjercicioFiscalId).append(" and  ");
            sql.append("tn.id=").append(tipoNominaId).append(" and  ");
            sql.append("n.id<>").append(nominaId).append(" ");
            sql.append("group by nd.servidor_id,nd.tipo_identificacion,nd.numero_identificacion,nd.nombres ");
            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<ServidorNominaVO> servidores = new ArrayList<ServidorNominaVO>();
            for (Object[] obj : lista) {
                servidores.add(objetoToServidorNomina(obj));
            }
            return servidores;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     *
     * @param institucionEjercicioFiscalId
     * @param periodoNominaId
     * @param tipoNominaId
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public List<ServidorNominaVO> buscarOcurrenciaMensual(final Long institucionEjercicioFiscalId,
            final Long periodoNominaId, final Long tipoNominaId, final Long nominaId) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select nd.servidor_id,nd.tipo_identificacion,nd.numero_identificacion,nd.nombres ");
            sql.append("from sch_proteus.nominas_detalle nd ");
            sql.append("JOIN sch_proteus.nominas n on nd.nomina_id=n.id ");
            sql.append("JOIN sch_proteus.instituciones_ejercicios_fiscales ief on "
                    + " n.institucion_ejercicio_fiscal_id=ief.id ");
            sql.append("JOIN sch_proteus.tipos_nominas tn on n.tipo_nomina_id=tn.id ");
            sql.append("JOIN sch_proteus.periodos_nominas pn on n.periodo_nomina_id=pn.id ");
            sql.append("where ");
            sql.append("ief.id=").append(institucionEjercicioFiscalId).append(" and  ");
            sql.append("tn.id=").append(tipoNominaId).append(" and  ");
            sql.append("pn.id=").append(periodoNominaId).append(" and  ");
            sql.append("n.id<>").append(nominaId).append(" ");
            sql.append("group by nd.servidor_id,nd.tipo_identificacion,nd.numero_identificacion,nd.nombres ");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            List<Object[]> lista = query.getResultList();
            List<ServidorNominaVO> servidores = new ArrayList<>();
            for (Object[] obj : lista) {
                servidores.add(objetoToServidorNomina(obj));
            }
            return servidores;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     *
     * @param obj
     * @return
     */
    private NominaPagoVO objetoToNominaPago(Object[] obj) {
        NominaPagoVO np = new NominaPagoVO();
        np.setServidorId(((BigDecimal) obj[0]).longValue());
        np.setTipoIdentificacion((String) obj[1]);
        np.setNumeroIdentificacion((String) obj[2]);
        np.setApellidosNombres((String) obj[3]);
        np.setValorIngresos((BigDecimal) obj[4]);
        np.setValorDescuentos((BigDecimal) obj[5]);
        np.setValorAportes((BigDecimal) obj[6]);
        np.setValorLiquidoAPagar((BigDecimal) obj[7]);
        np.setUnidadOrganizacionalId(((BigDecimal) obj[8]).longValue());
        np.setUnidadPresupuestarioId(((BigDecimal) obj[9]).longValue());
        return np;
    }

    /**
     *
     * @param obj
     * @return
     */
    private ServidorNominaVO objetoToServidorNomina(Object[] obj) {
        ServidorNominaVO np = new ServidorNominaVO();
        np.setServidorId(((BigDecimal) obj[0]).longValue());
        np.setTipoIdentificacion((String) obj[1]);
        np.setNumeroIdentificacion((String) obj[2]);
        np.setApellidosNombres((String) obj[3]);
        return np;
    }

    /**
     * Llena una tabla para formularios 107.
     */
    public void crearVistaMaterializadaFormularioIR() {
        // elimina indice.
        String validaIndice = "SELECT * FROM sys.indexes WHERE name='IDX_formulario_ir'";
        if (!getEntityManager().createNativeQuery(validaIndice).getResultList().isEmpty()) {
            String eliminaIndice = "DROP INDEX sch_proteus.formulario_ir.IDX_formulario_ir";
            LOG.info(eliminaIndice);
            getEntityManager().createNativeQuery(eliminaIndice).executeUpdate();
        }
        // elimina tabla.
        String validaTabla = "SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'sch_proteus.formulario_ir')";
        if (!getEntityManager().createNativeQuery(validaTabla).getResultList().isEmpty()) {
            String eliminaTabla = "DROP TABLE sch_proteus.formulario_ir";
            LOG.info(eliminaTabla);
            getEntityManager().createNativeQuery(eliminaTabla).executeUpdate();
        }
        // crea tabla.
        String creaTabla = "SELECT * INTO sch_proteus.formulario_ir FROM sch_proteus.vista_formularioIR";
        LOG.info(creaTabla);
        getEntityManager().createNativeQuery(creaTabla).executeUpdate();
        // crear indices.
        String creaIndice = "CREATE INDEX IDX_formulario_ir ON"
                + " sch_proteus.formulario_ir(ejercicio_fiscal_id, servidor_id) ";
        LOG.info(creaIndice);
        getEntityManager().createNativeQuery(creaIndice).executeUpdate();
    }

    /**
     * Llena una tabla para confidencial de pago.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearVistaMaterializadaConfidencialPago() {
        // elimina indice.
        String validaIndice = "SELECT * FROM sys.indexes WHERE name='IDX_formulario_ir'";
        if (!getEntityManager().createNativeQuery(validaIndice).getResultList().isEmpty()) {
            String eliminaIndice = "DROP INDEX sch_proteus.formulario_ir.IDX_formulario_ir";
            LOG.info(eliminaIndice);
            getEntityManager().createNativeQuery(eliminaIndice).executeUpdate();
        }
        // elimina tabla.
        String validaTabla = "SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'sch_proteus.formulario_ir')";
        if (!getEntityManager().createNativeQuery(validaTabla).getResultList().isEmpty()) {
            String eliminaTabla = "DROP TABLE sch_proteus.formulario_ir";
            LOG.info(eliminaTabla);
            getEntityManager().createNativeQuery(eliminaTabla).executeUpdate();
        }
        // crea tabla.
        String creaTabla = "SELECT * INTO sch_proteus.formulario_ir FROM sch_proteus.vista_formularioIR";
        LOG.info(creaTabla);
        getEntityManager().createNativeQuery(creaTabla).executeUpdate();
        // crear indices.
        String creaIndice = "CREATE INDEX IDX_formulario_ir ON"
                + " sch_proteus.formulario_ir(ejercicio_fiscal_id, servidor_id) ";
        LOG.info(creaIndice);
        getEntityManager().createNativeQuery(creaIndice).executeUpdate();
        getEntityManager().flush();
    }

    /**
     * Llena una tabla para impuesto a la renta.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearVistaMaterializadaNominasImpuestoRentaIR() {
        // elimina indice.
        String validaIndice = "SELECT * FROM sys.indexes WHERE name='IDX_impuesto_renta_ir'";
        if (!getEntityManager().createNativeQuery(validaIndice).getResultList().isEmpty()) {
            String eliminaIndice = "DROP INDEX sch_proteus.nominas_impuestos_renta.IDX_impuesto_renta_ir";
            getEntityManager().createNativeQuery(eliminaIndice).executeUpdate();
        }
        // elimina tabla.
        String validaTabla
                = "SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'sch_proteus.nominas_impuestos_renta')";
        if (!getEntityManager().createNativeQuery(validaTabla).getResultList().isEmpty()) {
            String eliminaTabla = "DROP TABLE sch_proteus.nominas_impuestos_renta";
            LOG.info(eliminaTabla);
            getEntityManager().createNativeQuery(eliminaTabla).executeUpdate();
        }
        // crea tabla.
        String creaTabla = "SELECT * INTO sch_proteus.nominas_impuestos_renta FROM sch_proteus.vista_impuesto_renta";
        LOG.info(creaTabla);
        getEntityManager().createNativeQuery(creaTabla).executeUpdate();
        // crear indices.
        String creaIndice = "CREATE INDEX IDX_impuesto_renta_ir ON"
                + " sch_proteus.nominas_impuestos_renta(ejercicio_fiscal_id, periodo_id, servidor_id) ";
        LOG.info(creaIndice);
        getEntityManager().createNativeQuery(creaIndice).executeUpdate();
        getEntityManager().flush();
    }

    /**
     *
     * @param periodoNominaId
     * @param servidorId
     * @param codigoRubro
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularValorAnticipoQuincena(final Long periodoNominaId, final Long servidorId,
            final String codigoRubro) throws DaoException {
        BigDecimal valor = BigDecimal.ZERO;
        Query query = getEntityManager().createNamedQuery(NominaDetalle.SUMAR_POR_RUBRO_PERIODO_SERVIDOR);
        query.setParameter(1, periodoNominaId);
        query.setParameter(2, servidorId);
        query.setParameter(3, codigoRubro);
        List<Object> resultado = query.getResultList();
        if (!resultado.isEmpty()) {
            if (resultado.get(0) != null) {
                valor = (BigDecimal) resultado.get(0);
            }
        }
        return valor;
    }

    /**
     *
     * @param pn
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularValorAnticipoDecimoCuarto(final PeriodoNomina pn, final Long servidorId)
            throws DaoException {
        BigDecimal valor = BigDecimal.ZERO;
        StringBuilder sql = new StringBuilder();
        sql.append("select sum(nd.valor_calculado_rubro_ingreso) from sch_proteus.nominas_detalle nd  ");
        sql.append("JOIN sch_proteus.nominas n on nd.nomina_id=n.id ");
        sql.append("JOIN sch_proteus.periodos_nominas pn on n.periodo_nomina_id=pn.id ");
        sql.append("JOIN sch_proteus.ejercicios_fiscales ef on pn.ejercicio_fiscal_id = ef.id ");
        sql.append("JOIN sch_proteus.rubros r on nd.rubro_ingreso_id = r.id and r.es_decimo_cuarto=1 ");
        sql.append("where ");
        sql.append("ef.nombre+'/'+cast(pn.numero as varchar) in (");
        long anio = Long.parseLong(pn.getEjercicioFiscal().getNombre());
        long mes = pn.getNumero();
        for (int i = 0; i < 12; i++) {
            mes = mes - 1;
            if (mes == 0) {
                anio = anio - 1;
                mes = 12;
            }
            sql.append("'").append(anio).append("/").append(mes).append("',");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") ");
        sql.append("and nd.servidor_id = ").append(servidorId);
//        System.out.println(sql.toString());
        Query query = getEntityManager().createNativeQuery(sql.toString());
        List<Object> resultado = query.getResultList();
        if (!resultado.isEmpty()) {
            if (resultado.get(0) != null) {
                valor = (BigDecimal) resultado.get(0);
            }
        }
        return valor;
    }

    /**
     *
     * @param pn
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularValorAnticipoDecimoTercero(final PeriodoNomina pn, final Long servidorId)
            throws DaoException {
        BigDecimal valor = BigDecimal.ZERO;
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("sum(case when r1.es_decimo_tercero=1 then  nd.valor_calculado_rubro_ingreso else 0 end)- ");
        sql.append("sum(case when r2.es_decimo_tercero=1 then nd.valor_calculado_rubro_descuentos else 0 end) ");
        sql.append("from sch_proteus.nominas_detalle nd  ");
        sql.append("JOIN sch_proteus.nominas n on nd.nomina_id=n.id ");
        sql.append("JOIN sch_proteus.periodos_nominas pn on n.periodo_nomina_id=pn.id ");
        sql.append("JOIN sch_proteus.ejercicios_fiscales ef on pn.ejercicio_fiscal_id = ef.id ");
        sql.append("LEFT JOIN sch_proteus.rubros r1 on nd.rubro_ingreso_id = r1.id and r1.es_decimo_tercero=1 ");
        sql.append("LEFT JOIN sch_proteus.rubros r2 on nd.rubro_descuentos_id = r2.id and r2.es_decimo_tercero=1 ");
        sql.append("where ");
        sql.append("ef.nombre+'/'+cast(pn.numero as varchar) in (");
        long anio = Long.parseLong(pn.getEjercicioFiscal().getNombre());
        long mes = pn.getNumero();
        for (int i = 0; i < 12; i++) {
            mes = mes - 1;
            if (mes == 0) {
                anio = anio - 1;
                mes = 12;
            }
            sql.append("'").append(anio).append("/").append(mes).append("',");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") ");
        sql.append("and nd.servidor_id = ").append(servidorId);
        // System.out.println(sql.toString());
        Query query = getEntityManager().createNativeQuery(sql.toString());
        List<Object> resultado = query.getResultList();
        if (!resultado.isEmpty()) {
            if (resultado.get(0) != null) {
                valor = (BigDecimal) resultado.get(0);
            }
        }
        return valor;
    }

    /**
     * Calcula el total de valores retenidos por IR.
     *
     * @param ejercicioFiscal
     * @param servidor
     * @param periodoMaximo
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularIRRetenido(final EjercicioFiscal ejercicioFiscal, final Servidor servidor,
            final Long periodoMaximo) throws DaoException {
        try {
            BigDecimal valor = BigDecimal.ZERO;
            if (periodoMaximo > 0) {
                // sumatorio ingresos datos sistema.
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT SUM(nd.valorDescontadoRubroDescuentos) ");
                sql.append("FROM NominaDetalle nd WHERE ");
                sql.append("nd.nomina.institucionEjercicioFiscal.ejercicioFiscal.id=:ejercicioFiscalId AND ");
                sql.append("nd.servidorId=:servidorId AND ");
                sql.append("nd.rubroDescuentosId IN (SELECT r.id FROM Rubro r WHERE r.esImpuestoRenta=true ) AND ");
                sql.append("nd.nomina.periodoNomina.numero IN ( ");
                for (long i = 1; i <= periodoMaximo; i++) {
                    sql.append(i).append(",");
                }
                sql.delete(sql.length() - 1, sql.length());
                sql.append(") ");
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("ejercicioFiscalId", ejercicioFiscal.getId());
                parametros.put("servidorId", servidor.getId());
                Query query = getEntityManager().createQuery(sql.toString());
                Set<String> keys = parametros.keySet();
                for (String key : keys) {
                    query.setParameter(key, parametros.get(key));
                }
                valor = (BigDecimal) query.getSingleResult();
                if (valor == null) {
                    valor = BigDecimal.ZERO;
                }
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     *
     * @param nominaId
     * @param grupoPresupuestario
     * @return
     * @throws DaoException
     */
    public List<Object[]> recuperarDataSIPARI(Long nominaId, String grupoPresupuestario) throws DaoException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select \n"
                    + "	a.sociedad,\n"
                    + "	a.ruc,\n"
                    + "	a.codigo_rubro,\n"
                    + "	a.cuenta_contable,\n"
                    + "	a.debe_haber,\n"
                    + "	a.acredor,\n"
                    + "	a.beneficiario,\n"
                    + "	a.anticipos,\n"
                    + "	a.centro_costo,     \n"
                    + "	a.centro_gestor,\n"
                    + "	a.posicion_presupuestaria,\n"
                    + "	a.programa,\n"
                    + "	a.fondo,\n"
                    + "	a.proyecto,\n"
                    + "	a.certificacion_presupuestaria,\n"
                    + "	replace(str(sum(a.valor),13,2),'.',',') as valor,\n"
                    + "	a.texto                                                    \n"
                    + "	from\n"
                    + "	(select\n"
                    + "	up.sociedad, \n"
                    + "	up.ruc, \n"
                    + "	case when nd.codigo_rubro_descuentos is null then  isnull(p.codigo,p2.codigo) else  "
                    + "           cc.nombre end as codigo_rubro,\n"
                    + "	'' as cuenta_contable,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then 'D' else 'H' end as debe_haber,\n"
                    + "	case when nd.codigo_rubro_descuentos is not null then\n"
                    + "	    case when nd.retencion_judicial=1 then \n"
                    + "	          case when r2.codigo='D1' then '1791709209001' else	    \n"
                    + "	             case when r2.codigo='D88' then '1791298020001' else '1111111111111'\n"
                    + "	             end	    \n"
                    + "	           end\n"
                    + "	    else r2.identificacion_beneficiario end \n"
                    + "	else '' end acredor,\n"
                    + "	case when nd.retencion_judicial=1 then \n"
                    + "	     case when r2.codigo in ('D1','D88') then '' else 'S' end	    \n"
                    + "	 else '' end beneficiario,\n"
                    + "	'' anticipos,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then up.centro_costo else '' end  centro_costo,\n"
                    + "	up.centro_gestor centro_gestor,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then  "
                    + "         isnull(up.grupo_presupuestario+p.partida,up.grupo_presupuestario+p2.partida) else  '' "
                    + " end posicion_presupuestaria,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then up.programa else '' end  programa,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then up.fondo else '' end  fondo,\n"
                    + "	case when nd.codigo_rubro_descuentos is null then up.proyecto else '' end  proyecto,\n"
                    + " case when nd.codigo_rubro_descuentos is null then cp.certificacion_presupuestaria else '' end "
                    + "certificacion_presupuestaria, \n"
                    + "	case when nd.codigo_rubro_descuentos is null then \n"
                    + "	isnull(nd.valor_descontado_rubro_ingreso,nd.valor_descontado_rubro_aportes) else \n"
                    + "	nd.valor_descontado_rubro_descuentos end as valor,\n"
                    + "	case when nd.rubro_descuentos_id is null then isnull(REPLACE (p.nombre , CHAR(13) + "
                    + " CHAR(10) , ' ' ),"
                    + " REPLACE (r3.nombre , CHAR(13) + CHAR(10) , ' ' ) ) else REPLACE (r2.nombre , CHAR(13) + "
                    + " CHAR(10) , ' ' ) end as texto,\n"
                    + "	dd.grupo_presupuestario\n"
                    + "	from sch_proteus.nominas_detalle nd\n"
                    + "	join sch_proteus.distributivo_detalles dd on nd.distributivo_detalle_id = dd.id\n"
                    + "	join sch_proteus.unidades_presupuestarias up on up.id = dd.unidad_presupuestaria_id\n"
                    + "	join sch_proteus.escalas_ocupacionales eo on eo.id = dd.escala_ocupacional_id"
                    + "	join sch_proteus.niveles_ocupacionales ni on ni.id = eo.niveles_ocupacionales_id"
                    + " join sch_proteus.distributivo d on d.id = dd.distributivo_id"
                    + " join sch_proteus.modalidades_laborales ml on d.modalidad_laboral_id = ml.id"
                    + " left join sch_proteus.certificaciones_presupuestarias cp on ml.id = cp.modalidad_laboral_id "
                    + " and up.id = cp.unidad_presupuestaria_id and cp.vigente=1"
                    + "	left join sch_proteus.rubros r on nd.rubro_ingreso_id=r.id\n"
                    + "	left join sch_proteus.rubros r2 on nd.rubro_descuentos_id=r2.id\n"
                    + "	left join sch_proteus.rubros r3 on nd.rubro_aportes_id=r3.id\n"
                    + "	left join sch_proteus.partidas p on r.partida_id = p.id\n"
                    + "	left join sch_proteus.partidas p2 on r3.partida_id = p2.id\n"
                    + "	left join sch_proteus.codigos_contables cc on r2.codigo_contable_id = cc.id\n"
                    + "	where nd.nomina_id=" + nominaId + " and up.grupo_presupuestario=" + grupoPresupuestario
                    + "	UNION ALL\n"
                    + "	select\n"
                    + "	up.sociedad, \n"
                    + "	up.ruc, \n"
                    + "	r.codigo codigo_rubro,\n"
                    + "	''  cuenta_contable,\n"
                    + "	'H' debe_haber,\n"
                    + "	r.identificacion_beneficiario acredor,\n"
                    + "	'' beneficiario,\n"
                    + "	'' anticipos,\n"
                    + "	'' centro_costo,\n"
                    + "	up.centro_gestor centro_gestor,\n"
                    + "	'' posicion_presupuestaria,\n"
                    + "	'' programa,\n"
                    + "	'' fondo,\n"
                    + "	'' proyecto,\n"
                    + "	'' certificacion_presupuestaria,\n"
                    + "	nd.valor_descontado_rubro_aportes valor,\n"
                    + "	REPLACE (r.nombre , CHAR(13) + CHAR(10) , ' ' ) as texto,\n"
                    + "	dd.grupo_presupuestario\n"
                    + "	from sch_proteus.nominas_detalle nd\n"
                    + "	join sch_proteus.distributivo_detalles dd on nd.distributivo_detalle_id = dd.id\n"
                    + "	join sch_proteus.unidades_presupuestarias up on up.id = dd.unidad_presupuestaria_id\n"
                    + "	join sch_proteus.rubros r on nd.rubro_aportes_id=r.id\n"
                    + "	join sch_proteus.escalas_ocupacionales eo on eo.id = dd.escala_ocupacional_id"
                    + "	join sch_proteus.niveles_ocupacionales ni on ni.id = eo.niveles_ocupacionales_id"
                    + "	where nd.nomina_id=" + nominaId + " AND nd.rubro_aportes_id is not null and "
                    + " up.grupo_presupuestario=" + grupoPresupuestario
                    + "	) a \n"
                    + "	where a.valor>0 \n"
                    + "	group by\n"
                    + "	a.sociedad,\n"
                    + "	a.ruc,\n"
                    + "	a.codigo_rubro,\n"
                    + "	a.cuenta_contable,\n"
                    + "	a.debe_haber,\n"
                    + "	a.acredor,\n"
                    + "	a.beneficiario,\n"
                    + "	a.anticipos,\n"
                    + "	a.centro_costo,     \n"
                    + "	a.centro_gestor,\n"
                    + "	a.posicion_presupuestaria,\n"
                    + "	a.programa,\n"
                    + "	a.fondo,\n"
                    + "	a.proyecto,\n"
                    + "	a.certificacion_presupuestaria,\n"
                    + "	a.texto,\n"
                    + "	a.grupo_presupuestario                                                    \n"
                    + "	order by a.sociedad, a.centro_gestor,a.grupo_presupuestario, a.debe_haber,a.texto");

            Query query = getEntityManager().createNativeQuery(sql.toString());
            return query.getResultList();

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Busca por el id del puesto los datos de nomina necesarios para el NominaDetalleVO
     *
     * @param puestoId
     * @return
     * @throws DaoException
     */
    public List<NominaDetalleVO> buscarNominaDetallesPorPuesto(final Long puestoId) throws DaoException {
        try {
            Query q = getEntityManager().createNamedQuery(NominaDetalle.BUSCAR_POR_PUESTO);
            q.setParameter(1, puestoId);
            List<Object[]> lista = q.getResultList();
            List<NominaDetalleVO> nominaDetallesVO = new ArrayList<>();
            for (Object[] o : lista) {
                NominaDetalleVO vo = new NominaDetalleVO();
                vo.setYear(o[0] == null ? null : o[0].toString());
                vo.setMes(o[1] == null ? null : o[1].toString());
                vo.setNumeroMes(o[2] == null ? null : o[2].toString());
                vo.setNumero(o[3] == null ? null : o[3].toString());
                vo.setTipo(o[4] == null ? null : o[4].toString());
                vo.setNombreServidor(o[5] == null ? null : o[5].toString());
                nominaDetallesVO.add(vo);
            }
            return nominaDetallesVO;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
