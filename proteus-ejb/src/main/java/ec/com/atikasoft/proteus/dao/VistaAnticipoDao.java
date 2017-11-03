/*
 *  VistaAnticipoDao.java
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
import ec.com.atikasoft.proteus.modelo.vistas.VistaAnticipo;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.ConsultaAnticipoVO;
import java.util.Date;
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
public class VistaAnticipoDao {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VistaAnticipoDao.class.getCanonicalName());

     @PersistenceContext(unitName = "proteusPU")
    private EntityManager em;
    /**
     * Constructor sin argumentos.
     */
    public VistaAnticipoDao() {
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
    public List<VistaAnticipo> buscar(final ConsultaAnticipoVO vo) throws DaoException {
        try {
            Map<String, Object> parametros = new HashMap<String, Object>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT o FROM VistaAnticipo o WHERE o.institucionEjercicioFiscalId=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getInstitucionEjercicioFiscalId());
            if (vo.getEjercicioFiscalId() != null) {
                sql.append(" AND o.ejercicioId=:ejercicioId ");
                parametros.put("ejercicioId", vo.getEjercicioFiscalId());
            }
            if (vo.getTipoNomimaId() != null) {
                sql.append(" AND o.tipoNominaId=:tipoNominaId ");
                parametros.put("tipoNominaId", vo.getTipoNomimaId());
            }
            if (vo.getTipoAnticipoId()!= null) {
                sql.append(" AND o.tipoAnticipoId=:tipoAnticipoId ");
                parametros.put("tipoAnticipoId", vo.getTipoAnticipoId());
            }
            if (vo.getGaranteId()!= null) {
                sql.append(" AND o.servidorGaranteId=:servidorGaranteId ");
                parametros.put("servidorGaranteId", vo.getGaranteId());
            }
            if (vo.getEscalaOcupacionalId() != null) {
                sql.append(" AND o.escalaOcupacionalId=:escalaOcupacionalId ");
                parametros.put("escalaOcupacionalId", vo.getEscalaOcupacionalId());
            }
             if (vo.getEstadoPuestoId()!= null) {
                sql.append(" AND o.estadoPuestoId=:estadoPuestoId ");
                parametros.put("estadoPuestoId", vo.getEstadoPuestoId());
            }
            if (vo.getModalidadLaboralId()!= null) {
                sql.append(" AND o.modalidadLaboralId=:modalidadLaboralId ");
                parametros.put("modalidadLaboralId", vo.getModalidadLaboralId());
            }
            if (vo.getUbicacionGeograficaId()!= null) {
                sql.append(" AND o.ubicacionGeograficaId=:ubicacionGeograficaId ");
                parametros.put("ubicacionGeograficaId", vo.getUbicacionGeograficaId());
            }
            if (vo.getUnidadOrganizacionalId()!= null) {
                sql.append(" AND o.unidadOrganizacionalId=:unidadOrganizacionalId ");
                parametros.put("unidadOrganizacionalId", vo.getUnidadOrganizacionalId());
            }
             if (vo.getDenominacionPuestoId()!= null) {
                sql.append(" AND o.denominacionPuestoId=:denominacionPuestoId ");
                parametros.put("denominacionPuestoId", vo.getDenominacionPuestoId());
            }
             
              if (vo.getEstadoAnticipo()!= null && !vo.getEstadoAnticipo().isEmpty()) {
                sql.append(" AND o.estado = :estado ");
                parametros.put("estado",vo.getEstadoAnticipo());
            }
               if (vo.getEstadoNomina()!= null && !vo.getEstadoNomina().isEmpty()) {
                sql.append(" AND o.nominaEstado = :nominaEstado ");
                parametros.put("nominaEstado",vo.getEstadoNomina());
            }
                 if (vo.getFechaSolicitudDesde()!= null && vo.getFechaSolicitudHasta()!=null) {
                     Date fechafin = UtilFechas.sumarDias(vo.getFechaSolicitudHasta(), 2);
                sql.append(" AND o.fechaSolicitudAnticipo between :fechaSolicitudAnticipoDesde and :fechaSolicitudAnticipoHasta ");
                parametros.put("fechaSolicitudAnticipoDesde",UtilFechas.truncarFecha(vo.getFechaSolicitudDesde()));
                parametros.put("fechaSolicitudAnticipoHasta",fechafin);
            }
              
            
            if (vo.getNumeroDocumentoServidor()!= null && !vo.getNumeroDocumentoServidor().isEmpty()) {
                sql.append(" AND o.servidorNumeroIdentificacion LIKE :servidorNumeroIdentificacion ");
                parametros.put("servidorNumeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor().toUpperCase()));
            }
            if (vo.getApellidosNombresServidor()!= null && !vo.getApellidosNombresServidor().isEmpty()) {
                sql.append(" AND o.servidorApellidosNombres LIKE :servidorApellidosNombres ");
                parametros.put("servidorApellidosNombres", UtilCadena.concatenar("%", vo.getApellidosNombresServidor().toUpperCase(),"%"));
            }
            
            if (vo.getTipoDocumentoServidor() != null && !vo.getTipoDocumentoServidor().isEmpty()) {
                sql.append(" AND o.servidorTipoIdentificacion LIKE :servidorTipoIdentificacion ");
                parametros.put("servidorTipoIdentificacion", UtilCadena.concatenar("%", vo.getTipoDocumentoServidor(), "%"));
            }  
            
              if (vo.getValorDesde()!= null && vo.getValorHasta() !=null) {
                sql.append(" AND ( o.valorAnticipo >= :valorDesde AND o.valorAnticipo <= :valorHasta ) ");
                parametros.put("valorDesde", vo.getValorDesde());
                parametros.put("valorHasta", vo.getValorHasta());
            }
                 if (vo.getNumeroAnticipo()!= null && !vo.getNumeroAnticipo().isEmpty()) {
                sql.append(" AND o.numero LIKE :numero ");
                parametros.put("numero", UtilCadena.concatenar( vo.getNumeroAnticipo()));
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