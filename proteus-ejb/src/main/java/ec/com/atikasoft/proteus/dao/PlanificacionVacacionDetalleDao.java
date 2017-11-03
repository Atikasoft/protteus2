/*
 *  PlanificacionVacacionDetalleDao.java
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
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.util.ArrayList;
import java.util.Date;
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
public class PlanificacionVacacionDetalleDao extends GenericDAO<PlanificacionVacacionDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionVacacionDetalleDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public PlanificacionVacacionDetalleDao() {
        super(PlanificacionVacacionDetalle.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de
     * PlanificacionVacacionDetalle que esten vigentes true.
     *
     * @return Listado PlanificacionVacacionDetalle
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacionDetalle> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionVacacionDetalle.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de
     * PlanificacionVacacionDetalle vigentes por Id de PlanificacionVacacion
     *
     * @param idPlanificacionVacacion identificador de planificacion de vacacion
     * @return Listado PlanificacionVacacionDetalle
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacionDetalle> buscarPorIdPlanificacionVacacion(
            Long idPlanificacionVacacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    PlanificacionVacacionDetalle.BUSCAR_POR_PLANIFICACION_VACACION, 
                    idPlanificacionVacacion);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de
     * PlanificacionVacacionDetalle vigentes por Id de servidor y ejercicio
     * fiscal
     *
     * @param idServidor identificador del servidor
     * @param idEjercicioFiscal identificador del ejercicio fiscal
     * @return Listado PlanificacionVacacionDetalle
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacionDetalle> buscarPorServidorYEjericicioFiscal(
            final Long idServidor, final Long idEjercicioFiscal) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    PlanificacionVacacionDetalle.BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL, 
                    idServidor, idEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de
     * PlanificacionVacacionDetalle vigentes por Id de servidor y estado.
     *
     * @param idServidor identificador del sevidor
     * @param estado estado de la planificacion
     * @return Listado PlanificacionVacacionDetalle
     * @throws DaoException En caso de error
     */
    public List<PlanificacionVacacionDetalle> buscarPorServidorYEstado(final Long idServidor, final String estado)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionVacacionDetalle.BUSCAR_POR_SERVIDOR_Y_ESTADO, idServidor, estado);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionVacacionDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * buscar vacaciones por parametros.
     *
     * @param vo value objeto de la busqueda de planificacion de vacaciones
     * @return lista de planificaciones de vacacion
     * @throws DaoException error en el acceso a datos
     */
    public List<PlanificacionVacacionDetalle> buscarVaciones(final BusquedaVacacionVO vo) throws DaoException {

        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM PlanificacionVacacionDetalle p, DistributivoDetalle o ");
        sql.append(" WHERE p.vigente=true AND o.servidor.id = p.planificacionVacacion.servidor.id "
                + " and p.planificacionVacacion.vigente=true ");
        if (vo.getIntitucionEjercicioFiscalId() != null) {
            sql.append(" AND p.planificacionVacacion.institucionEjercicioFiscal.id=:institucionEjercicioFiscalId ");
            parametros.put("institucionEjercicioFiscalId", vo.getIntitucionEjercicioFiscalId());
        }
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND o.distributivo.unidadOrganizacional.id ="
                    + " :unidadOrganizacional");
            parametros.put("unidadOrganizacional", vo.getUnidadAdministrativaId());
        }
        if (vo.getFechaInicioPlanificacio() != null && vo.getFechaFinPlanificacio() == null) {
            sql.append(" AND p.fechaInicio>=:fechaInicio");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicioPlanificacio()));
        } else if (vo.getFechaInicioPlanificacio() == null && vo.getFechaFinPlanificacio() != null) {
            sql.append(" AND p.fechaInicio<=:fechaFin");
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFinPlanificacio(), 1)));
        } else if (vo.getFechaInicioPlanificacio() != null && vo.getFechaFinPlanificacio() != null) {
            sql.append(" AND p.fechaInicio BETWEEN :fechaInicio AND :fechaFin");
            parametros.put("fechaInicio", UtilFechas.truncarFecha(vo.getFechaInicioPlanificacio()));
            parametros.put("fechaFin", UtilFechas.truncarFecha(UtilFechas.sumarDias(vo.getFechaFinPlanificacio(), 1)));
        }

        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND o.servidor.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND o.servidor.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND o.servidor.apellidosNombres LIKE :apellidosNombres");
            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getNombreServidor(), "%"));
        }
        if (vo.getEstadoServidorId() != null) {
            sql.append(" AND o.servidor.estadoPersonalId=:estadoPersonalId");
            parametros.put("estadoPersonalId", vo.getEstadoServidorId());
        }
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }
    
    /**
     * Lista todas las planificaciones aprobadas por unidad organizacional para un mes
     * para no tener que filtrar por ejercicio fiscal, para ubicar al mes se debe pasar la fecha completa del
     * primer y ultimo dia
     * @param idUnidadOrganizacional identificador de la unidad organizacionl
     * @param primerDiaMes numero del primer dia del mes
     * @param ultimoDiaMes numero del ultimo dia del mes
     * @return lista de planificaciones de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<PlanificacionVacacionDetalle> planificacionesAprobadasParaMes(final Long idUnidadOrganizacional, 
            final Date primerDiaMes, final Date ultimoDiaMes) throws DaoException {
        List<PlanificacionVacacionDetalle> result = null;
        Query query = this.getEntityManager().createNamedQuery(PlanificacionVacacionDetalle.BUSCAR_APROBADAS_MES);
        query.setParameter("idUnidadOrganizacional", idUnidadOrganizacional);
        query.setParameter("fechaDesde", primerDiaMes);
        query.setParameter("fechaHasta", ultimoDiaMes);
        result = query.getResultList();        
        return result == null ? new ArrayList<PlanificacionVacacionDetalle>() : result;
    }
}