/*
 *  AnticipoServicio.java
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
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.DestinatarioNotificacionDao;
import ec.com.atikasoft.proteus.dao.NotificacionDao;
import ec.com.atikasoft.proteus.enums.EstadoNotificacionDestinatarioEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DestinatarioNotificacion;
import ec.com.atikasoft.proteus.modelo.Notificacion;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Stateless
@LocalBean
@TransactionAttribute
public class NotificacionesServicio extends BaseServicio {

    /**
     * DAO de Notificacion.
     */
    @EJB
    private NotificacionDao notificacionDao;
    /**
     * DAO de DestinatarioNotificacion.
     */
    @EJB
    private DestinatarioNotificacionDao destinatarioNotificacionDao;
    /**
     * Servicio mensajeria
     */
    @EJB
    private MensajeriaServicio mensajeriaServicio;

    /**
     * Crea una nueva notificación
     *
     * @param notificacion notificacion a enviar
     * @param destinatarios lista de destinatarios
     * @param usuarioVO usuario conectado
     * @throws ServicioException
     */
    public void guardarYEnviarNotificacion(
            final Notificacion notificacion,
            final List<DestinatarioNotificacion> destinatarios,
            final UsuarioVO usuarioVO) throws ServicioException {
        try {
            notificacion.setFechaEnvio(notificacion.getFechaCreacion());
            notificacionDao.crear(notificacion);
            notificacionDao.flush();
            if (!notificacion.getEnviadaATodos()) {
                for (DestinatarioNotificacion dn : destinatarios) {
                    if (destinatarioNotificacionDao
                            .buscarPorNotificacionYDestinatarioIds(notificacion.getId(), dn.getId()).isEmpty()) {
                        dn.setFechaCreacion(notificacion.getFechaEnvio());
                        dn.setNotificacion(notificacion);
                        destinatarioNotificacionDao.crear(dn);
                        destinatarioNotificacionDao.flush();
                    }
                }
                enviarCorreoADestinatarios(notificacion, destinatarios);
            } else {
                crearDestinatarioNotificacionesParaNotificacionesMasivas(usuarioVO);
            }

        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Enviar correo a los destinatarios
     *
     * @param n notificacion
     * @param destinatarios lista destinatarios
     */
    private void enviarCorreoADestinatarios(
            final Notificacion n, final List<DestinatarioNotificacion> destinatarios) throws ServicioException {
        try {
            String[] destinatariosMails = new String[destinatarios.size()];
            for (int i = 0; i < destinatariosMails.length; i++) {
                destinatarios.get(i).getDestinatario().getMail();
            }
            StringBuilder cuerpoMensaje = new StringBuilder("<br><br>");
            cuerpoMensaje.append(n.getMensaje());
            mensajeriaServicio.enviarMail(
                    n.getAsunto(), n.getRemitente() == null ? null : n.getRemitente().getMail(),
                    destinatariosMails, null, cuerpoMensaje.toString(), null, null);

        } catch (ServicioException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera las notificaciones asociadas a un remitente dado su id
     *
     * @param remitenteId id del remitente
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<Notificacion> listarNotificacionesPorRemitenteIdInstitucionEjercicioFiscalId(
            final Long remitenteId, final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return notificacionDao
                    .buscarPorRemitenteIdInstitucionEjercicioFiscalId(remitenteId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera las notificaciones por id institucionEjercicioFiscal y el alcance de lectura
     *
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal
     * @param enviadaATodos indica si la notifiacion fue enviada a todos los servidores
     * @return
     * @throws ServicioException
     */
    public List<Notificacion> buscarPorInstitucionEjercicioFiscalAlcanceLectura(
            final Long institucionEjercicioFiscalId, final Boolean enviadaATodos) throws ServicioException {
        try {
            return notificacionDao
                    .buscarPorInstitucionEjercicioFiscalAlcanceLectura(institucionEjercicioFiscalId, enviadaATodos);
        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera las notificaciones asociadas a un remitente dado su nro de identificacion e id
     * institucionEjercicioFiscal
     *
     * @param remitenteNroIdentificacion nro de identificacion del remitente
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<Notificacion> listarNotificacionesPorRemitenteNroIdentificacionInstitucionEjercicioFiscalId(
            String remitenteNroIdentificacion, final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return notificacionDao.buscarPorRemitenteIdentificacionInstitucionEjercicioFiscalId(
                    remitenteNroIdentificacion, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera una notificacion dado su id
     *
     * @param notificacionId id de la notificacion
     * @return
     * @throws ServicioException
     */
    public Notificacion recuperarNotificacionPorId(final Long notificacionId) throws ServicioException {
        try {
            List<Notificacion> lista = notificacionDao.buscarNotificacionPorId(notificacionId);
            if (lista.isEmpty()) {
                return null;
            }
            return lista.get(0);

        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera las notificaciones vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<Notificacion> listarNotificacionesVigentes() throws ServicioException {
        try {
            return notificacionDao.buscarVigentes();
        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Crea en base de datos los registros de remitentes asociados a las notificaciones enviadas para todos los
     * servidores
     *
     * @param uc
     * @throws ServicioException
     */
    public void crearDestinatarioNotificacionesParaNotificacionesMasivas(final UsuarioVO uc) throws ServicioException {
        try {
            List<Notificacion> notificacionesMasivas = buscarPorInstitucionEjercicioFiscalAlcanceLectura(
                    uc.getInstitucionEjercicioFiscal().getId(), Boolean.TRUE);
            List<DestinatarioNotificacion> destinatariosEnBaseDeDatos;
            for (Notificacion nm : notificacionesMasivas) {
                destinatariosEnBaseDeDatos = destinatarioNotificacionDao
                        .buscarPorNotificacionYDestinatarioIds(nm.getId(), uc.getServidor().getId());
                if (destinatariosEnBaseDeDatos.isEmpty()) {
                    DestinatarioNotificacion dn = new DestinatarioNotificacion();
                    dn.setDestinatario(uc.getServidor());
                    dn.setEstado(EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo());
                    dn.setNotificacion(nm);
                    dn.setVigente(Boolean.TRUE);
                    dn.setUsuarioCreacion(nm.getUsuarioCreacion());
                    dn.setFechaCreacion(nm.getFechaEnvio());
                    dn.setUsuarioCreacion(nm.getUsuarioCreacion());
                    destinatarioNotificacionDao.crear(dn);
                    destinatarioNotificacionDao.flush();
                }
            }

        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera los destinatariosnotificaciones asociados a un destinatario dado su id e institucionejerciciofiscal id
     *
     * @param destintarioId id del destinatario
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<DestinatarioNotificacion> listarDestinatarioNotificacionesPorDestinarioIdInstitucionEjercicioFiscalId(
            final Long destintarioId, final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return destinatarioNotificacionDao
                    .buscarPorDestinatarioIdInstitucionEjercicioFiscalId(destintarioId, institucionEjercicioFiscalId);
        } catch (DaoException ex) {
            Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera los destinatariosnotificaciones asociados a un destinatario dado su identifiacacion e
     * institucionejerciciofiscal id
     *
     * @param nroIdentificacion nro identificacion destintario
     * @param institucionEjercicioFiscalId id institucionEjercicioFiscal
     * @return
     * @throws ServicioException
     */
    public List<DestinatarioNotificacion>
            listarDestinatarioNotificacionesPorDestinarioNroIdentificacionInstitucionEjercicioFiscalId(
                    final String nroIdentificacion, final Long institucionEjercicioFiscalId) throws ServicioException {
                try {
                    return destinatarioNotificacionDao
                            .buscarPorDestinatarioNroIdentificacionInstitucionEjercicioFiscalId(
                                    nroIdentificacion, institucionEjercicioFiscalId);
                } catch (DaoException ex) {
                    Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServicioException(ex);
                }
            }

            /**
             * Cuenta la cantidad de notificaciones de un destinatario de acuerdo al estado de lectura de las misma
             * (Leído/No Leído)
             *
             * @param destinatarioId id del destinatario
             * @param estadoLectura estado de la notificacion
             * @return
             * @throws ServicioException
             */
            public long contarNotificacionesPorDestinatarioIdYEstadoLectura(
                    final Long destinatarioId, final String estadoLectura) throws ServicioException {
                try {
                    return destinatarioNotificacionDao
                            .contarNotificacionesPorDestinatarioIdYEstadoLectura(destinatarioId, estadoLectura);
                } catch (DaoException ex) {
                    Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServicioException(ex);
                }

            }

            /**
             * marca como leida una notificacion para un destintario especifico
             *
             * @param destinatarioNotificacion registro que relaciona al destintario con la notificacion
             * @throws ServicioException
             */
            public void marcarNotificacionComoLeida(
                    final DestinatarioNotificacion destinatarioNotificacion) throws ServicioException {
                try {
                    destinatarioNotificacion.setEstado(EstadoNotificacionDestinatarioEnum.LEIDO.getCodigo());
                    destinatarioNotificacion.setFechaLectura(new Date());
                    destinatarioNotificacionDao.actualizar(destinatarioNotificacion);
                } catch (DaoException ex) {
                    Logger.getLogger(NotificacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ServicioException(ex);
                }
            }

}
