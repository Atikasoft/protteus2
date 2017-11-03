/*
 *  AsistenciaDePersonalServicio.java
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
 *  30/08/2017
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.*;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class AsistenciaDePersonalServicio extends BaseServicio {

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
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Dao de reloj
     */
    @EJB
    private RelojDao relojDao;

    /**
     * Dao de relojUnidadOrganizacional
     */
    @EJB
    private RelojUnidadOrganizacionaljDao relojUnidadOrgDao;

    /**
     * Dao de ExcepcionAsistenciaPersonal
     */
    @EJB
    private ExcepcionAsistenciaPersonalDao excepcionAsistenciaPersonalDao;

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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param horario
     * @param horarioDestalles
     * @throws ServicioException
     */
    public void crearActualizarHorario(
            final Horario horario, final List<HorarioDetalle> horarioDestalles) throws ServicioException {

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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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

            if (horarioDetalles != null) {
                for (HorarioDetalle hd : horarioDetalles) {
                    if (hd.getId() == null) {
                        hd.setHorario(h);
                        horarioDetalleDao.crear(hd);
                    } else {
                        horarioDetalleDao.actualizar(hd);
                    }
                    horarioDetalleDao.flush();
                }
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AsistenciaDePersonalServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Recupera todos los relojes vigentes o no
     *
     * @return
     * @throws ServicioException
     */
    public List<Reloj> listarRelojesVigentesONo() throws ServicioException {
        try {
            return relojDao.buscarTodos();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera todos los relojes vigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<Reloj> listarRelojesVigentes() throws ServicioException {
        try {
            return relojDao.buscarTodosVigentes();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera un reloj dado su codigo
     *
     * @param codigoReloj codigo del reloj
     * @return
     * @throws ServicioException
     */
    public Reloj buscarRelojPorCodigo(final String codigoReloj) throws ServicioException {
        try {
            return relojDao.buscarVigentePorCodigo(codigoReloj);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera un reloj dado su id
     *
     * @param idReloj id del reloj
     * @return
     * @throws ServicioException
     */
    public Reloj buscarRelojPorId(final Long idReloj) throws ServicioException {
        try {
            return relojDao.buscarPorId(idReloj);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Crea o actualiza un registro de reloj
     *
     * @param reloj
     * @throws ServicioException
     */
    public void crearActualizarReloj(final Reloj reloj) throws ServicioException {
        try {
            if (reloj.getId() == null) {
                relojDao.crear(reloj);
            } else {
                relojDao.actualizar(reloj);
            }
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera los registros de relojUnidadOrganizacional dado el id de la Unidad Organizacional
     *
     * @param unidadOrganizacionalId id de la Unidad Organizacional
     * @return
     * @throws ServicioException
     */
    public List<RelojUnidadOrganizacional> listarRelojesUnidadOrganizalesPorUnidadOrganizacional(
            final Long unidadOrganizacionalId) throws ServicioException {
        try {
            return relojUnidadOrgDao.buscarVigentesPorUnidadOrganizacionalId(unidadOrganizacionalId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Crea o actualiza un registro de ExcepcionAsistenciaPersonal
     *
     * @param excepcionAsistenciaPersonal
     * @throws ServicioException
     */
    public void crearActualizarExcepcionAsistenciaPersonal(
            final ExcepcionAsistenciaPersonal excepcionAsistenciaPersonal) throws ServicioException {
        try {
            if (excepcionAsistenciaPersonal.getId() == null) {
                excepcionAsistenciaPersonalDao.crear(excepcionAsistenciaPersonal);
            } else {
                excepcionAsistenciaPersonalDao.actualizar(excepcionAsistenciaPersonal);
            }
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera los registros de ExcepcionAsistenciaPersonalvigentes
     *
     * @return
     * @throws ServicioException
     */
    public List<ExcepcionAsistenciaPersonal> listarExcepcionesAsistenciaPersonalVigentes() throws ServicioException {
        try {
            return excepcionAsistenciaPersonalDao.buscarTodosVigentes();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera los registros de ExcepcionAsistenciaPersonalvigentes dado el id de un servidor
     *
     * @param servidorId id del servidor
     * @return
     * @throws ServicioException
     */
    public List<ExcepcionAsistenciaPersonal> listarExcepcionesAsistenciaPersonalVigentesPorServidorId(
            final Long servidorId) throws ServicioException {
        try {
            return excepcionAsistenciaPersonalDao.buscarVigentesPorServidorId(servidorId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Retorna la lista de excepciones que pertenencen a las unidades desconcetradas del usuario logueado
     *
     * @param usuario usuario logueado
     * @return
     * @throws ServicioException
     */
    public List<ExcepcionAsistenciaPersonal> listarExcepcionesAsistenciaPersonalPorUsuarioLogueado(
            final UsuarioVO usuario) throws ServicioException {
        try {
            List<UnidadOrganizacional> uoAcceso = desconcentradoServicio
                    .buscarUnidadesDeAcceso(usuario.getServidor().getId(),
                            FuncionesDesconcentradosEnum.ASISTENCIA_DE_PERSONAL.getCodigo());

            Map<Long, String> mapaUnidOrg = new HashMap<>();
            for (UnidadOrganizacional uo : uoAcceso) {
                mapaUnidOrg.put(uo.getId(), uo.getCodigo());

            }
            List<ExcepcionAsistenciaPersonal> excepcionesVigentes = excepcionAsistenciaPersonalDao.buscarTodosVigentes();
            List<ExcepcionAsistenciaPersonal> resultado = new ArrayList<>();
            for (ExcepcionAsistenciaPersonal eap : excepcionesVigentes) {
                if (mapaUnidOrg.containsKey(eap.getServidor().getDistributivoDetalle().getDistributivo()
                        .getUnidadOrganizacional().getId())) {
                    resultado.add(eap);
                }
            }
            return resultado;

        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }
}
