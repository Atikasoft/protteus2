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
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.vistas.VistaNomina;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.ConsultaNominaVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class VistaNominaDao extends GenericDAO<VistaNomina, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VistaNominaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VistaNominaDao() {
        super(VistaNomina.class);
    }

    /**
     * Consulta de nominas.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    public List<VistaNomina> buscar(final ConsultaNominaVO vo) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT o FROM VistaNomina o WHERE o.institucionId=:institucionId ");
            parametros.put("institucionId", vo.getEjercicioFiscalId());
            sql.append(" AND o.ejercicioId=:ejercicioId ");
            parametros.put("ejercicioId", vo.getEjercicioFiscalId());
            if (vo.getTipoNomimaId() != null) {
                sql.append(" AND o.tipoNominaId=:tipoNominaId ");
                parametros.put("tipoNominaId", vo.getTipoNomimaId());
            }
            if (vo.getPeriodoNomimaId() != null) {
                sql.append(" AND o.periodoNominaId=:periodoNominaId ");
                parametros.put("periodoNominaId", vo.getPeriodoNomimaId());
            }
            if (vo.getNumeroDocumento() != null && !vo.getNumeroDocumento().isEmpty()) {
                sql.append(" AND o.nominaNumero LIKE :nominaNumero ");
                parametros.put("nominaNumero", UtilCadena.concatenar("%", vo.getNumeroDocumento()));
            }
            if (vo.getNumeroDocumento() != null && !vo.getNumeroDocumento().isEmpty()) {
                sql.append(" AND o.nominaDescripcion LIKE :nominaDescripcion ");
                parametros.put("nominaDescripcion", UtilCadena.concatenar("%", vo.getNumeroDocumento(), "%"));
            }
            // fecha de creacion.
            if (vo.getFechaCreacionInicio() != null && vo.getFechaCreacionFin() != null) {
                sql.append(" AND o.nominaFechaCreacion BETWEEN :nominaFechaCreacionInicio AND :nominaFechaCreacionFin ");
                parametros.put("nominaFechaCreacionInicio", vo.getFechaCreacionInicio());
                parametros.put("nominaFechaCreacionFin", vo.getFechaCreacionFin());
            } else if (vo.getFechaCreacionInicio() != null && vo.getFechaCreacionFin() == null) {
                sql.append(" AND o.nominaFechaCreacion >= :nominaFechaCreacionInicio ");
                parametros.put("nominaFechaCreacionInicio", vo.getFechaCreacionInicio());
            } else if (vo.getFechaCreacionInicio() == null && vo.getFechaCreacionFin() != null) {
                sql.append(" AND o.nominaFechaCreacion <= :nominaFechaCreacionFin ");
                parametros.put("nominaFechaCreacionFin", vo.getFechaCreacionFin());
            }
            // fecha de generacion.
            if (vo.getFechaCreacionInicio() != null && vo.getFechaCreacionFin() != null) {
                sql.append(" AND o.nominaFechaCreacion BETWEEN :nominaFechaCreacionInicio AND :nominaFechaCreacionFin ");
                parametros.put("nominaFechaCreacionInicio", vo.getFechaCreacionInicio());
                parametros.put("nominaFechaCreacionFin", vo.getFechaCreacionFin());
            } else if (vo.getFechaCreacionInicio() != null && vo.getFechaCreacionFin() == null) {
                sql.append(" AND o.nominaFechaCreacion >= :nominaFechaCreacionInicio ");
                parametros.put("nominaFechaCreacionInicio", vo.getFechaCreacionInicio());
            } else if (vo.getFechaCreacionInicio() == null && vo.getFechaCreacionFin() != null) {
                sql.append(" AND o.nominaFechaCreacion <= :nominaFechaCreacionFin ");
                parametros.put("nominaFechaCreacionFin", vo.getFechaCreacionFin());
            }

            Query query = getEntityManager().createQuery(sql.toString());
            for (String key : parametros.keySet()) {
                query.setParameter(key, parametros.get(key));
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}