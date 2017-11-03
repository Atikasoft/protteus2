/*
 *  AnticipoDao.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import java.math.BigDecimal;
import java.util.HashMap;
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
public class NominaDetalleIRDao {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaDetalleIRDao.class.getName());

    /**
     *
     */
    @PersistenceContext(unitName = "proteusPU")
    private EntityManager em;

    /**
     * Constructor sin argumentos.
     */
    public NominaDetalleIRDao() {
        super();
    }

    /**
     * Calcula el total de ingresos gravables de un servidor en unos periodos
     * especificos.
     *
     * @param ejercicioFiscal
     * @param servidor
     * @param periodoMaximo
     * @return
     * @throws DaoException
     */
    public BigDecimal calcularTotalIngresosGravables(final EjercicioFiscal ejercicioFiscal, final Servidor servidor,
            final Long periodoMaximo) throws DaoException {
        try {
            BigDecimal valor = BigDecimal.ZERO;
            if (periodoMaximo > 0) {
                // sumatorio ingresos datos sistema.
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT SUM(nd.valorIngresos-nd.valorDescuentos) ");
                sql.append("FROM NominaDetalleIR nd WHERE ");
                sql.append("nd.ejercicioFiscalId=:ejercicioFiscaoId AND ");
                sql.append("nd.servidorId=:servidorId AND ");
                sql.append("nd.periodo IN ( ");
                for (long i = 1; i <= periodoMaximo; i++) {
                    sql.append(i).append(",");
                }
                sql.delete(sql.length() - 1, sql.length());
                sql.append(") ");
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("ejercicioFiscaoId", ejercicioFiscal.getId());
                parametros.put("servidorId", servidor.getId());
                System.out.println(":" + sql.toString());
                Query query = em.createQuery(sql.toString());
                Set<String> keys = parametros.keySet();
                for (String key : keys) {
                    query.setParameter(key, parametros.get(key));
                }
                valor = (BigDecimal) query.getSingleResult();
                if (valor == null) {
                    valor = BigDecimal.ZERO;
                }
                // sumatoria de ingresos desde migracion
                StringBuilder sql2 = new StringBuilder();
                sql2.append("SELECT SUM(nd.ingresos-nd.descuentos) ");
                sql2.append("FROM sch_proteus.nominas_historicos_roles nd WHERE gravable=1 AND ");
                sql2.append("nd.anio=? AND ");
                sql2.append("nd.numero_identificacion=? AND ");
                sql2.append("nd.mes IN ( ");
                for (long i = 1; i <= periodoMaximo; i++) {
                    sql2.append(i).append(",");
                }
                sql2.delete(sql2.length() - 1, sql2.length());
                sql2.append(") ");
                System.out.println(":" + sql2.toString());
                Query query2 = em.createNativeQuery(sql2.toString());
                Map<Integer, Object> parametros2 = new HashMap();
                parametros2.put(1, Integer.valueOf(ejercicioFiscal.getNombre()));
                parametros2.put(2, servidor.getNumeroIdentificacion());
                Set<Integer> keys2 = parametros2.keySet();
                for (Integer key2 : keys2) {
                    query2.setParameter(key2, parametros2.get(key2));
                }
                BigDecimal valor2 = (BigDecimal) query2.getSingleResult();
                if (valor2 == null) {
                    valor2 = BigDecimal.ZERO;
                }
                valor = valor.add(valor2);
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

}
