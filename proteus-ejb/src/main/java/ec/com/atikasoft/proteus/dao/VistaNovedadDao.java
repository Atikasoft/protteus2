/*
 *  VistaNovedadDao.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.vistas.VistaNovedad;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ConsultaNovedadVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class VistaNovedadDao {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VistaNovedadDao.class.getCanonicalName());

    @PersistenceContext(unitName = "proteusPU")
    private EntityManager em;

    /**
     * Constructor sin argumentos.
     */
    public VistaNovedadDao() {
        super();
    }

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Consulta de nominas.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<VistaNovedad> buscar(final ConsultaNovedadVO vo) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append(
                    "SELECT o FROM VistaNovedad o WHERE o.institucionEjercicioFiscalId=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getInstitucionEjercicioFiscalId());

            if (vo.getNominaId() != null) {
                sql.append(" AND o.nominaId=:nominaId ");
                parametros.put("nominaId", vo.getNominaId());
            }
            if (vo.getDatoAdicionalId() != null) {
                sql.append(" AND o.datoAdicionalId=:datoAdicionalId ");
                parametros.put("datoAdicionalId", vo.getDatoAdicionalId());
            }
            if (vo.getEjercicioFiscalId() != null) {
                sql.append(" AND o.ejercicioFiscalId=:ejercicioFiscalId ");
                parametros.put("ejercicioFiscalId", vo.getEjercicioFiscalId());
            }

            if (vo.getNominaEstado() != null && !vo.getNominaEstado().isEmpty()) {
                sql.append(" AND o.nominaEstado LIKE :nominaEstado ");
                parametros.put("nominaEstado", UtilCadena.concatenar(vo.getNominaEstado()));
            }
            if (vo.getTipoNominaId() != null) {
                sql.append(" AND o.tipoNominaId = :tipoNominaId ");
                parametros.put("tipoNominaId", vo.getTipoNominaId());
            }
            if (vo.getPeriodoNominaId() != null) {
                sql.append(" AND o.periodoNominaId = :periodoNominaId ");
                parametros.put("periodoNominaId", vo.getPeriodoNominaId());
            }

            if (vo.getInstitucionId() != null) {
                sql.append(" AND o.institucionId=:institucionId ");
                parametros.put("institucionId", vo.getInstitucionId());
            }
            if (vo.getServidorId() != null) {
                sql.append(" AND o.servidorId=:servidorId ");
                parametros.put("servidorId", vo.getServidorId());
            }
            if (vo.getNominaNumero() != null && !vo.getNominaNumero().isEmpty()) {
                sql.append(" AND o.nominaNumero LIKE :nominaNumero ");
                parametros.put("nominaNumero", UtilCadena.concatenar("%",vo.getNominaNumero(), "%"));
            }

            if (vo.getNovedadNumero() != null && !vo.getNovedadNumero().isEmpty()) {
                sql.append(" AND o.novedadNumero LIKE :novedadNumero ");
                parametros.put("novedadNumero", UtilCadena.concatenar("%",vo.getNovedadNumero(), "%"));
            }
            if (vo.getNovedadFechaCreacionDesde() != null && vo.getNovedadFechaCreacionHasta() != null) {
                sql.append(
                        " AND o.novedadFechaCreacion between :novedadFechaCreacionDesde and :novedadFechaCreacionHasta ");
                parametros.put("novedadFechaCreacionDesde", UtilFechas.truncarFecha(vo.getNovedadFechaCreacionDesde()));
                parametros.put("novedadFechaCreacionHasta", UtilFechas.sumarDias(vo.getNovedadFechaCreacionHasta(), 2));
            }
            if (vo.getNovedadDetalleValorDesde() != null && vo.getNovedadDetalleValorHasta() != null) {
                sql.append(" AND o.novedadDetalleValor between :valorDesde1 and  :valorHasta2 ");
                parametros.put("valorDesde1", vo.getNovedadDetalleValorDesde());
                parametros.put("valorHasta2", vo.getNovedadDetalleValorHasta());
            }

            Query query = getEntityManager().createQuery(sql.toString());
            for (String key : parametros.keySet()) {
                query.setParameter(key, parametros.get(key));
            }
            if (vo.getInicioConsulta() != null && vo.getFinConsulta() != null) {
                query.setFirstResult(vo.getInicioConsulta());
                query.setMaxResults(vo.getFinConsulta());
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * construir el conteo para paginacion de resultados.
     *
     * @param vo
     * @return
     */
    public int contarVistaNovedad(final ConsultaNovedadVO vo) {
        StringBuilder sql = new StringBuilder();

        sql.append(" select count(1) "
                + " FROM sch_proteus.vista_novedades o WHERE o.institucion_ejercicio_fiscal_id = ");
        sql.append(vo.getInstitucionEjercicioFiscalId());

        if (vo.getNominaId() != null) {
            sql.append(" AND o.nomina_id = ");
            sql.append(vo.getNominaId());
        }
        if (vo.getDatoAdicionalId() != null) {
            sql.append(" AND o.dato_adicional_id=");
            sql.append(vo.getDatoAdicionalId());
        }
        if (vo.getEjercicioFiscalId() != null) {
            sql.append(" AND o.ejercicio_fiscal_id= ");
            sql.append(vo.getEjercicioFiscalId());
        }

        if (vo.getNominaEstado() != null && !vo.getNominaEstado().isEmpty()) {
            sql.append(" AND o.nomina_estado LIKE '");
            sql.append(UtilCadena.concatenar(vo.getNominaEstado(), "'"));
        }

        if (vo.getTipoNominaId() != null) {
            sql.append(" AND o.tipo_nomina_id = ");
            sql.append(vo.getTipoNominaId());
        }
        if (vo.getPeriodoNominaId() != null) {
            sql.append(" AND o.periodo_nomina_id = ");
            sql.append(vo.getPeriodoNominaId());
        }
        if (vo.getInstitucionId() != null) {
            sql.append(" AND o.institucion_id= ");
            sql.append(vo.getInstitucionId());
        }
        if (vo.getServidorId() != null) {
            sql.append(" AND o.servidor_id= ");
            sql.append(vo.getServidorId());
        }
        if (vo.getNominaNumero() != null && !vo.getNominaNumero().isEmpty()) {
            sql.append(" AND o.nomina_numero LIKE '");
            sql.append(UtilCadena.concatenar("%",vo.getNominaNumero(), "%'"));
        }

        if (vo.getNovedadNumero() != null && !vo.getNovedadNumero().isEmpty()) {
            sql.append(" AND o.novedad_numero LIKE '");
            sql.append(UtilCadena.concatenar("%",vo.getNovedadNumero(), "%'"));
        }
        if (vo.getNovedadFechaCreacionDesde() != null && vo.getNovedadFechaCreacionHasta() != null) {
            sql.append(
                    " AND o.novedad_fecha_creacion between '");
            sql.append(UtilFechas.formatear(vo.getNovedadFechaCreacionDesde()));
            sql.append("' and '");
            sql.append(UtilFechas.formatear(UtilFechas.sumarDias(vo.getNovedadFechaCreacionHasta(), 2)));
            sql.append("'");
        }

        if (vo.getNovedadDetalleValorDesde() != null && vo.getNovedadDetalleValorHasta() != null) {
            sql.append(" AND o.novedad_detalle_valor between  ");
            sql.append(vo.getNovedadDetalleValorDesde());
            sql.append(" AND ");
            sql.append(vo.getNovedadDetalleValorHasta());
        }

        Query q = getEntityManager().createNativeQuery(sql.toString());
        Integer regs = Integer.valueOf(q.getSingleResult().toString()).intValue();
//        System.out.println(" numero de registros: " + regs);
        return regs;

    }
}
