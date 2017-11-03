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
import ec.com.atikasoft.proteus.modelo.DestinatarioNotificacion;
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
public class DestinatarioNotificacionDao extends GenericDAO<DestinatarioNotificacion, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DestinatarioNotificacionDao() {
        super(DestinatarioNotificacion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de DestinatarioNotificacion que esten vigentes true.
     *
     * @return Listado alertas
     * @throws DaoException En caso de error
     */
    public List<DestinatarioNotificacion> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DestinatarioNotificacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera DestinatarioNotificacion por id de notificacion
     *
     * @param notificacionId id de la notificacion
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorNotificacionId(final Long notificacionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DestinatarioNotificacion.BUSCAR_POR_NOTIFICACION_ID, notificacionId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera DestinatarioNotificacion por id de notificacion e id de destinatario
     *
     * @param notificacionId id de la notificacion
     * @param destinatarioId id del destinatario
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorNotificacionYDestinatarioIds(
            final Long notificacionId, Long destinatarioId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DestinatarioNotificacion.BUSCAR_POR_NOTIFICACION_Y_DESTINATARIO_IDS, notificacionId, destinatarioId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera las destinatarioNotificaciones por id del remitente y la institucionEjecicioFiscal
     *
     * @param remitenteId id del remitente
     * @param institucionEjercicioFiscalId id de institucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorRemitenteIdInstitucionEjercicioFiscalId(
            final Long remitenteId, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DestinatarioNotificacion.BUSCAR_POR_REMITENTE_ID_INSTITUCION_EJERCICIO_FISCAL,
                    remitenteId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera las destinatarioNotificaciones por numero de identificacion del remitente y la institucionEjecicioFiscal
     *
     * @param nroIdentificacion nro identificacion del remitente
     * @param institucionEjercicioFiscalId id de institucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorRemitenteNroIdentificacionInstitucionEjercicioFiscalId(
            final String nroIdentificacion, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DestinatarioNotificacion.BUSCAR_POR_REMITENTE_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL,
                    nroIdentificacion, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera las destinatarioNotificaciones por id del destinatario y la institucionEjecicioFiscal
     *
     * @param destinatarioId id del destinatario
     * @param institucionEjercicioFiscalId id de institucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorDestinatarioIdInstitucionEjercicioFiscalId(
            final Long destinatarioId, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DestinatarioNotificacion.BUSCAR_POR_DESTINATARIO_ID_INSTITUCION_EJERCICIO_FISCAL,
                    destinatarioId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera las destinatarioNotificaciones por numero de identificacion del destinatario y la
     * institucionEjecicioFiscal
     *
     * @param nroIdentificacion nro identificacion del destinatario
     * @param institucionEjercicioFiscalId id de institucionEjercicioFiscal
     * @return
     * @throws DaoException
     */
    public List<DestinatarioNotificacion> buscarPorDestinatarioNroIdentificacionInstitucionEjercicioFiscalId(
            final String nroIdentificacion, final Long institucionEjercicioFiscalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    DestinatarioNotificacion.BUSCAR_POR_DESTINATARIO_IDENTIFICACION_INSTITUCION_EJERCICIO_FISCAL,
                    nroIdentificacion, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Cuenta la cantidad de notificaciones de un destinatario de acuerdo al estado de lectura de las misma (Leído/No
     * Leído)
     *
     * @param destinatarioId id del destinatario
     * @param estadoLectura estado de la notificacion
     * @return
     * @throws DaoException
     */
    public long contarNotificacionesPorDestinatarioIdYEstadoLectura(
            final Long destinatarioId, final String estadoLectura) throws DaoException {
        try {
            return contarPorConsultaNombrada(
                    DestinatarioNotificacion.CONTAR_NOTIFICACIONES_POR_DESTINATARIO_ID_Y_ESTADO_LECTURA,
                    destinatarioId, estadoLectura);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
