/*
 *  AnticipoServicio.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserveh.
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

import ec.com.atikasoft.proteus.dao.HorarioDao;
import ec.com.atikasoft.proteus.dao.HorarioDesconcentradoDao;
import ec.com.atikasoft.proteus.dao.HorarioDetalleDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
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
public class HorarioServicio1 extends BaseServicio {

    /**
     * DAO de Horarios.
     */
    @EJB
    private HorarioDao horarioDao;
    /**
     * DAO de HorarioDetalle.
     */
    @EJB
    private HorarioDetalleDao horarioDetalleDao;
    /**
     * DAO de HorarioDesconcentrado
     */
    @EJB
    private HorarioDesconcentradoDao horarioDesconcentradoDao;

    /**
     * DAO de Servidor
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Recupera todos los registros de horarios vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<Horario> listarHorariosVigentes() throws ServicioException {
        try {
            return horarioDao.buscarTodosVigentes();
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param horario
     * @param horarioDestalles
     * @param uc
     * @throws ServicioException
     */
    public void guardarHorario(
            final Horario horario, final List<HorarioDetalle> horarioDestalles,
            final UsuarioVO uc) throws ServicioException {

        try {
            if (horario.getId() == null) {
                horarioDao.crear(horario);
            } else {
                horarioDao.actualizar(horario);
            }
            horarioDao.flush();
            Date fechaCreacionModificacion = horario.getFechaActualizacion() != null
                    ? horario.getFechaActualizacion() : horario.getFechaCreacion();
            guardarHorarioDetalles(horario, horarioDestalles, fechaCreacionModificacion);

        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param h
     * @param detalles
     * @param fechaCreacionModificacion
     * @throws DaoException
     */
    private void guardarHorarioDetalles(
            final Horario h, final List<HorarioDetalle> horarioDetalles,
            final Date fechaCreacionModificacion) throws DaoException {
        try {
            List<HorarioDetalle> detallesActuales = horarioDetalleDao.buscarTodosPorHorarioId(h.getId());
            String usuario = h.getUsuarioActualizacion() != null && !h.getUsuarioActualizacion().trim().isEmpty()
                    ? h.getUsuarioActualizacion() : h.getUsuarioCreacion();
            for (HorarioDetalle hd : detallesActuales) {
                hd.setVigente(Boolean.FALSE);
                hd.setFechaActualizacion(fechaCreacionModificacion);
                hd.setUsuarioActualizacion(usuario);
                horarioDetalleDao.actualizar(hd);
                horarioDetalleDao.flush();
            }

            for (HorarioDetalle hd : horarioDetalles) {
                if (hd.getId() == null) {
                    hd.setHorario(h);
                    horarioDetalleDao.crear(hd);
                } else {
                    horarioDetalleDao.actualizar(hd);
                }
                horarioDetalleDao.flush();
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera todos los registros de HorariosDesconcentrados vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<HorarioDesconcentrado> listarHorariosDesconcentradosVigentes() throws ServicioException {
        try {
            return horarioDesconcentradoDao.buscarTodosVigentes();
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param horarioId
     * @param institucionEjercicioFiscalId
     * @return
     * @throws ServicioException
     */
    public Boolean horarioAsignadoAServidores(
            final Long horarioId, final Long institucionEjercicioFiscalId) throws ServicioException {
        try {
            return !servidorDao.buscarActivosPorHorarioId(horarioId, institucionEjercicioFiscalId).isEmpty();
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera todos los registros de HorariosDesconcentrados de acuerdo al id de la unidad desconcentrada
     *
     * @param horarioId
     * @return
     * @throws ServicioException
     */
    public List<HorarioDesconcentrado> listarHorariosDesconcentradosVigentesDadoHorarioId(
            final Long horarioId) throws ServicioException {
        try {
            return horarioDesconcentradoDao.buscarVigentesPorHorarioId(horarioId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera todos los registros de HorariosDesconcentrados de acuerdo al id de la unidad desconcentrada
     *
     * @param desconcentradoId
     * @return
     * @throws ServicioException
     */
    public List<HorarioDesconcentrado> listarHorariosDesconcentradosVigentesDadoDesconcentradoId(
            final Long desconcentradoId) throws ServicioException {
        try {
            return horarioDesconcentradoDao.buscarVigentesPorDesconcentradoId(desconcentradoId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera todos los registros de HorariosDesconcentrados de acuerdo al id de la unidad desconcentrada
     *
     * @param desconcentradoId
     * @return
     * @throws ServicioException
     */
    public List<HorarioDesconcentrado> listarTodosHorariosDesconcentradosDadoDesconcentradoId(
            final Long desconcentradoId) throws ServicioException {
        try {
            return horarioDesconcentradoDao.buscarTodosPorDesconcentradoId(desconcentradoId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Guarda los horarios asignados a la unidad desconcentrada
     *
     * @param usuario usuario conectado
     * @param desconcentradoId id de la unidad desconcentrada
     * @param listaHorariosDesconcentrados lista de horarios configurados
     * @throws ServicioException
     */
    public void guardarHorariosAsignadosADesconcentrado(
            final UsuarioVO usuario,
            final Long desconcentradoId,
            final List<HorarioDesconcentrado> listaHorariosDesconcentrados) throws ServicioException {
        try {
            List<HorarioDesconcentrado> actuales = horarioDesconcentradoDao.buscarVigentesPorDesconcentradoId(desconcentradoId);
            Date horaCreacionModificacion = new Date();
            for (HorarioDesconcentrado hdesc : actuales) {
                hdesc.setVigente(Boolean.FALSE);
                hdesc.setFechaActualizacion(horaCreacionModificacion);
                hdesc.setUsuarioActualizacion(usuario.getUsuario());
                horarioDesconcentradoDao.actualizar(hdesc);
                horarioDesconcentradoDao.flush();
            }
            for (HorarioDesconcentrado hdesc : listaHorariosDesconcentrados) {
                if (hdesc.getId() == null) {
                    horarioDesconcentradoDao.crear(hdesc);
                } else {
                    horarioDesconcentradoDao.actualizar(hdesc);
                }
                horarioDesconcentradoDao.flush();
            }

        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera los detalles de un horario dado su id
     *
     * @param horarioId
     * @return
     * @throws ServicioException
     */
    public List<HorarioDetalle> listarHorarioDetallesDadoHorarioId(Long horarioId) throws ServicioException {
        try {
            return horarioDetalleDao.buscarTodosPorHorarioId(horarioId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Actualiza el horario de cada uno de los servidores pasados como parametro
     *
     * @param servidores
     * @throws ServicioException
     */
    public void guardarAsignacionHorarioAServidores(final List<Servidor> servidores) throws ServicioException {
        try {
            for (Servidor s : servidores) {
                servidorDao.actualizar(s);
            }

        } catch (DaoException ex) {
            Logger.getLogger(HorarioServicio1.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

}
