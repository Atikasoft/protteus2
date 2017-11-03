/**
 * VacacionSolicitudDao.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * AtikaSoft ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with AtikaSoft.
 *
 *
 * AtikaSoft Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 29/10/2013
 *
 * Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.util.Arrays;
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
import javax.persistence.TemporalType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class VacacionSolicitudDao extends GenericDAO<VacacionSolicitud, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionSolicitudDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VacacionSolicitudDao() {
        super(VacacionSolicitud.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por estado.
     *
     * @param estado String: <R>egistrado, <N>egado, <A>probado
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionSolicitud> buscarTodosPorEstado(final String estado) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitud.BUSCAR_POR_ESTADO, estado);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por id del servidor.
     *
     * @param idServidor id del servidor
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionSolicitud> buscarTodosPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitud.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por id del servidor y estado de la solicitud.
     *
     * @param idServidor id del servidor
     * @param estado esta de la solicitud de vacacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionSolicitud> buscarTodosPorServidorYEstado(final Long idServidor, final String estado)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    VacacionSolicitud.BUSCAR_POR_SERVIDOR_Y_ESTADO, idServidor, estado);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de solicitudes en estado registrado y validado por servidor.
     *
     * @param idServidor id del servidor
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionSolicitud> buscarTodosPorServidorEnTramite(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitud.BUSCAR_POR_SERVIDOR_EN_TRAMITE, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de solicitudes de vacaciones y anticipos de vacaciones no negadas por fecha.
     *
     * @param idServidor id del servidor
     * @param fechaInicio fecha de inicio
     * @param fechafin fecha final
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionSolicitud> buscarVacacionesPorServidorEnTramitePorFecha(final Long idServidor,
            final Date fechaInicio, final Date fechafin) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitud.BUSCAR_VACACIONES_POR_SERVIDOR_Y_FECHA, fechaInicio,
                    fechafin, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de VacacionSolicitud que esten vigentes true.
     *
     * @return Listado VacacionSolicitud
     * @throws DaoException En caso de error
     */
    public List<VacacionSolicitud> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitud.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionSolicitudDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Busca vacaciones de un servidor por id de servidor, tipo, fecha de inicio y fecha fin
     *
     * @param idServidor identificador del servidor
     * @param tipo tipo de vacacion
     * @param fechaInicio fecha de inicio
     * @param estado estado de la solicitud.
     * @param fechaFin fecha final
     * @return lista de solicitudes
     * @throws DaoException error en el acceso a datos
     */
    public List<VacacionSolicitud> buscarPorServidorTipoFechaInicioFin(
            final Long idServidor, final String tipo, final Date fechaInicio, final Date fechaFin, final String estado)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM VacacionSolicitud a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.servidorInstitucion.servidor.id = :idServidor");
        if (tipo != null && tipo.isEmpty()) {
            sql.append("  AND a.tipo = :tipo");
        }
        if (estado != null && estado.isEmpty()) {
            sql.append("  AND a.estado = :estado");
        }
        if (fechaInicio != null && fechaFin != null) {
            sql.append("  AND ( ( a.fechaInicio BETWEEN :fechaInicio AND :fechaFin )");
            sql.append("  OR ( a.fechaFin BETWEEN :fechaInicio AND :fechaFin ))");
            sql.append("  AND a.estado = :estado");
        }
        if (fechaInicio != null && fechaFin == null) {
            sql.append("  AND ( :fechaInicio BETWEEN a.fechaInicio  AND a.fechaFin )");
            sql.append("  AND a.estado = :estado");
        }
        sql.append(" ORDER BY a.fechaCreacion desc ");

        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("idServidor", idServidor);
        if (tipo != null && tipo.isEmpty()) {
            createQuery.setParameter("tipo", tipo);
        }
        if (estado != null && estado.isEmpty()) {
            List<String> estados = Arrays.asList(estado.split(","));
            createQuery.setParameter("estado", estados);
        }
        if (fechaInicio != null && fechaFin != null) {
            createQuery.setParameter("fechaInicio", fechaInicio, TemporalType.TIMESTAMP);
            createQuery.setParameter("fechaFin", fechaFin, TemporalType.TIMESTAMP);
            createQuery.setParameter("estado", EstadoVacacionEnum.APROBADO.getCodigo());
        }
        if (fechaInicio != null && fechaFin == null) {
            createQuery.setParameter("fechaInicio", fechaInicio, TemporalType.TIMESTAMP);
            createQuery.setParameter("estado", EstadoVacacionEnum.APROBADO.getCodigo());
        }
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     * buscar vacaciones por parametros.
     *
     * @param vo value objeto con datos de la busqueda de solicitudes de vacaciones
     * @return lista de solicitudes de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<VacacionSolicitud> buscarVaciones(final BusquedaVacacionVO vo) throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p FROM VacacionSolicitud p ");
        sql.append(" WHERE p.vigente=true  ");
        if (vo.getUnidadAdministrativaId() != null) {
            sql.append(" AND EXISTS( SELECT o.id FROM DistributivoDetalle o WHERE o.vigente = true ");
            sql.append("     AND  o.servidor.id = p.servidorInstitucion.servidor.id");
            sql.append("     AND o.distributivo.unidadOrganizacional.id ="
                    + "      :unidadOrganizacional )");
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
        if (vo.getTipoVacacion() != null && !vo.getTipoVacacion().trim().isEmpty()) {
            sql.append(" AND p.tipo = :tipoVacacion");
            parametros.put("tipoVacacion", vo.getTipoVacacion());
        }
        if (vo.getTipoDocumentoServidor() != null) {
            sql.append(" AND p.servidorInstitucion.servidor.tipoIdentificacion=:tipoIdentificacion");
            parametros.put("tipoIdentificacion", vo.getTipoDocumentoServidor());
        }
        if (vo.getNumeroDocumentoServidor() != null && !vo.getNumeroDocumentoServidor().trim().isEmpty()) {
            sql.append(" AND p.servidorInstitucion.servidor.numeroIdentificacion LIKE :numeroIdentificacion");
            parametros.put("numeroIdentificacion", UtilCadena.concatenar("%", vo.getNumeroDocumentoServidor(), "%"));
        }
        if (vo.getNombreServidor() != null && !vo.getNombreServidor().trim().isEmpty()) {
            sql.append(" AND p.servidorInstitucion.servidor.apellidosNombres LIKE :apellidosNombres");
            parametros.put("apellidosNombres", UtilCadena.concatenar("%", vo.getNombreServidor(), "%"));
        }
        if (vo.getEstadoServidorId() != null) {
            sql.append(" AND p.servidorInstitucion.servidor.estadoPersonalId=:estadoPersonalId");
            parametros.put("estadoPersonalId", vo.getEstadoServidorId());
        }
        if (vo.getEstadoVacacion() != null) {
            sql.append(" AND p.estado=:estado");
            parametros.put("estado", vo.getEstadoVacacion());
        }
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     *
     * @param idServidor identificador del servidor
     * @param tipo tipo de vacacion
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha final
     * @return lista de solicitudes de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<VacacionSolicitud> buscarPorServidorTipoFechaCreacion(final Long idServidor, final String tipo,
            final Date fechaInicio, final Date fechaFin) throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM VacacionSolicitud a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.servidorInstitucion.servidor.id = :idServidor");
        sql.append(" AND a.tipo = :tipo ");
        sql.append(" AND (a.fechaCreacion BETWEEN :fechaInicio");
        sql.append(" AND :fechaFin ) ORDER BY a.fechaCreacion desc ");

        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("idServidor", idServidor);
        createQuery.setParameter("tipo", tipo);
        createQuery.setParameter("fechaInicio", fechaInicio, TemporalType.TIMESTAMP);
        createQuery.setParameter("fechaFin", fechaFin, TemporalType.TIMESTAMP);
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     *
     * @param servidorInstitucionId
     * @return
     * @throws DaoException
     */
    public Long contarMinutosVacacionesSolicitadasYAprobadas(Long servidorInstitucionId) throws DaoException {
        return contarPorConsultaNombrada(VacacionSolicitud.CONTAR_MINUTOS_APROBADAS, servidorInstitucionId);
    }
}
