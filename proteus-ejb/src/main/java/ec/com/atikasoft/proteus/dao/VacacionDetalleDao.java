/*
 *  VacacionDao.java
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
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import java.util.Date;
import java.util.List;
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
public class VacacionDetalleDao extends GenericDAO<VacacionDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionDetalleDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VacacionDetalleDao() {
        super(VacacionDetalle.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param estado String: <R>egistrado, <N>egado, <A>probado
     * @return List
     * @throws DaoException DaoException
     */
    /**
     * Este metodo procesa la busqueda de todo por Número de identificación del servidor.
     *
     * @param tipoDocumentoServidor tipo de documento der servidor
     * @param documentoServidor String: Número de identificación del servidor
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionDetalle> buscarTodosPorIdentificacionServidor(final String tipoDocumentoServidor, 
            final String documentoServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionDetalle.BUSCAR_POR_DOCUMENTO_SERVIDOR, tipoDocumentoServidor, 
                    documentoServidor);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de VacacionDetalle que esten vigentes true.
     *
     * @return Listado VacacionDetalle
     * @throws DaoException En caso de error
     */
    public List<VacacionDetalle> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionDetalle.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros por el id de vacaciones
     *
     * @param idVacacion identificador de la vacacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionDetalle> buscarPorVacacion(final Long idVacacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionDetalle.BUSCAR_POR_VACACION, idVacacion);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros por el id de vacaciones
     *
     * @param idVacacion identificador de la vacacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionDetalle> buscarPorVacacionPorTipoVacacion(final Long idVacacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionDetalle.BUSCAR_POR_VACACION_POR_TIPO_VACACION, idVacacion);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros por el id de vacaciones
     *
     * @param idVacacion identificador de la vacacion
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionDetalle> buscarPorVacacionPorTipoProporcional(final Long idVacacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionDetalle.BUSCAR_POR_VACACION_POR_TIPO_PROPORCIONAL, idVacacion);
        } catch (DaoException ex) {
            Logger.getLogger(DistributivoDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param fecha fecha del feriado
     * @return lista de vacaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<VacacionDetalle> buscarPorFechaYServidor(final Date fecha)
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM Feriado a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND a.fecha = :fecha");
        sql.append(" ORDER BY a.fecha desc ");
        Query createQuery = getEntityManager().createQuery(sql.toString());
        createQuery.setParameter("fecha", fecha, TemporalType.TIMESTAMP);
        List resultList = createQuery.getResultList();
        return resultList;
    }

    /**
     *
     * @param vacacionId identificador de la vacacion
     * @throws DaoException error en el acceso a datos
     */
    public void quitarVigencia(Long vacacionId) throws DaoException {
        ejecutarPorConsultaNombrada(VacacionDetalle.QUITAR_VIGENCIA, vacacionId);
    }

    /**
     *
     * @param tipoNuevo tipo de vacacion nuevo
     * @param tipoAnterior tipo de vacacion anterior
     * @param vacacionId identificador del
     * @throws DaoException error en el acceso a datos
     */
    public void actualizarTipo(String tipoNuevo, String tipoAnterior, Long vacacionId) throws DaoException {
        ejecutarPorConsultaNombrada(VacacionDetalle.ACTUALIZAR_TIPO, tipoNuevo, tipoAnterior, vacacionId);
    }
}
