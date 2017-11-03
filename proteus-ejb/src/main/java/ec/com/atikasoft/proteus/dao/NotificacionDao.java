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

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Notificacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class NotificacionDao extends GenericDAO<Notificacion, Long> {

    /**
     * Constructor sin argumentos.
     */
    public NotificacionDao() {
        super(Notificacion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de notificaciones que esten vigentes true.
     *
     * @return Listado alertas
     * @throws DaoException En caso de error
     */
    public List<Notificacion> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Notificacion.BUSCAR_TODAS_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera la notificacion por su id
     * @param id id de la notificacion
     * @return
     * @throws DaoException 
     */
    public List<Notificacion> buscarNotificacionPorId(final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Notificacion.BUSCAR_POR_ID, id);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recuepera las notificaciones por id del remitente  
     * @param remitenteId id del remitente
     * @return
     * @throws DaoException 
     */
    public List<Notificacion> buscarPorRemitenteId (final Long remitenteId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Notificacion.BUSCAR_TODAS_VIGENTES_POR_REMITENTE_ID, remitenteId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recuepera las notificaciones por numero de identificacion del remitente
     * @param nroIdentificacion nro identificacion del remitente
     * @return 
     * @throws DaoException
     */
    public List<Notificacion> buscarPorRemitenteNroIdentificacion(final String nroIdentificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    Notificacion.BUSCAR_TODAS_VIGENTES_POR_REMITENTE_IDENTIFICACION, nroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera las notificaciones por id del remitente e id institucionEjercicioFiscal
     * @param remitenteId id del remitente
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal 
     * @return
     * @throws DaoException 
     */
    public List<Notificacion> buscarPorRemitenteIdInstitucionEjercicioFiscalId(
            final Long remitenteId, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    Notificacion.BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL, 
                    remitenteId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera las notificaciones por id institucionEjercicioFiscal y el alcance de lectura 
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal 
     * @param enviadaATodos indica si la notifiacion fue enviada a todos los servidores
     * @return
     * @throws DaoException 
     */
    public List<Notificacion> buscarPorInstitucionEjercicioFiscalAlcanceLectura(
            final Long institucionEjercicioFiscalId, final Boolean enviadaATodos) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Notificacion.BUSCAR_POR_INSTITUCION_EJERCICIO_FISCAL_ALCANCE_LECTURA, 
                    institucionEjercicioFiscalId, enviadaATodos);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera las notificaciones por identificacion del remitente e id institucionEjercicioFiscal
     * @param remitenteNroIdentificacion nro identificacion del remitente
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal 
     * @return
     * @throws DaoException 
     */
    public List<Notificacion> buscarPorRemitenteIdentificacionInstitucionEjercicioFiscalId(
            final String remitenteNroIdentificacion, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    Notificacion.BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL, 
                    remitenteNroIdentificacion, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
