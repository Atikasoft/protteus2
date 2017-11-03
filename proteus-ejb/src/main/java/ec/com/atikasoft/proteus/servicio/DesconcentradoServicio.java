/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.DesconcentradoApoyoDao;
import ec.com.atikasoft.proteus.dao.DesconcentradoHistoricoDao;
import ec.com.atikasoft.proteus.dao.DesconcentradosDao;
import ec.com.atikasoft.proteus.dao.DesconcentradosUnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import ec.com.atikasoft.proteus.modelo.DesconcentradoHistorico;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

/**
 * SERVICIO PARA LA ADMINISTRACIÓN DE UNIDADES DESCONCETRADAS
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Stateless
@LocalBean
@TransactionAttribute
public class DesconcentradoServicio extends BaseServicio {

    /**
     * Dao de desconcentrados.
     */
    @EJB
    DesconcentradosDao desconcentradoDao;
    /**
     * Dao de apoyos de desconcentrados.
     */
    @EJB
    DesconcentradoApoyoDao desconcentradoApoyoDao;
    /**
     *
     */
    @EJB
    private DesconcentradoHistoricoDao desconcentradoHistoricosDao;
    /**
     *
     */
    @EJB
    private DesconcentradosUnidadOrganizacionalDao desconcentradoUnidOrgDao;
    /**
     * Dao de DistributivoDetalle.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     *
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     *
     * @param servidorId identificador del servidor
     * @param funcionDesconcentrado funcion del desconcentrado.
     * @return lista de unidades organizacionales.
     * @throws ServicioException error en el servicio
     */
    public List<UnidadOrganizacional> buscarUnidadesDeAcceso(
            Long servidorId, String funcionDesconcentrado) throws ServicioException {
        try {
            List<UnidadOrganizacional> unidades = new ArrayList<>();
            List<Desconcentrado> desconcentrados = desconcentradoDao.buscarPorServidorResponsableId(servidorId);
            List<DesconcentradoApoyo> apoyos = desconcentradoApoyoDao.buscarPorServidorApoyoId(servidorId);
            for (DesconcentradoApoyo da : apoyos) {
                desconcentrados.add(da.getDesconcentrado());
            }
            for (Desconcentrado d : desconcentrados) {
                if (d.getVigente()) {
                    for (DesconcentradoUnidadOrganizacional u : d.getListaUnidadOrganizacional()) {
                        if (u.getVigente() && u.getFunciones().contains(funcionDesconcentrado)) {
                            unidades.add(u.getUnidadOrganizacional());
                        }
                    }
                }
            }
            return unidades;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOS VIGENTES
     *
     * @return lista de desconcentrados
     * @throws ServicioException error en el servicio
     */
    public List<Desconcentrado> listarDesconcentradosTodosVigentes() throws ServicioException {
        try {
            return desconcentradoDao.buscarVigentes();
        } catch (DaoException ex) {
            throw new ServicioException("Error al intentar listarDesconcentradosTodosVigentes", ex);
        }
    }

    /**
     * RECUPERA UN DESCONCENTRADO DADO SU ID
     *
     * @param desconcentradoId identificador del desconcentrado
     * @return Datos del desconcentrado
     * @throws ServicioException error en el servicio
     */
    public Desconcentrado recuperarDesconcentradoPorId(final Long desconcentradoId) throws ServicioException {
        try {
            return desconcentradoDao.buscarPorId(desconcentradoId);
        } catch (Exception ex) {
            throw new ServicioException("Error al intentar recuperarDesconcentradoPorId", ex);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOS DE ACUERDO AL NNOMBRE
     *
     * @param desconcentradoNombre nombre del desconcentrado
     * @return lista de desconcentrados
     * @throws ServicioException error en el servicio
     */
    public List<Desconcentrado> listarDesconcentradosPorNombre(
            final String desconcentradoNombre) throws ServicioException {
        try {
            return desconcentradoDao.buscarPorNombre(desconcentradoNombre);
        } catch (Exception e) {
            throw new ServicioException("Error al intentar listarDesconcentradosPorNombre", e);
        }
    }

    /**
     * RECUPERA LOS DESCONCENTRADOS DE ACUERDO AL ID DEL SERVIDOR RESPONSABLE
     *
     * @param servidorResponsableId identificador del responsable
     * @return lista de desconcentrados
     * @throws ServicioException error en el servicio
     */
    public List<Desconcentrado> listarDesconcentradosPorServidorResponsableId(
            final Long servidorResponsableId) throws ServicioException {
        try {
            return desconcentradoDao.buscarPorServidorResponsableId(servidorResponsableId);
        } catch (Exception e) {
            throw new ServicioException("Error al intentar listarDesconcentradosPorServidorResponsableId", e);
        }
    }

    /**
     * RECUEPERA LOS SERVIDORES DE APOYO DE UN DESCONCONCENTRADO DADO SU ID
     *
     * @param desconcentradoId identificador del desconcentrado
     * @return lista de apoyos
     * @throws ServicioException error en el servicio
     */
    public List<DesconcentradoApoyo> recuperarServidoresDeApoyoDadoDesconcentradoId(
            final Long desconcentradoId) throws ServicioException {
        try {
            return desconcentradoApoyoDao.buscarPorDesconcentradoId(desconcentradoId);
        } catch (DaoException e) {
            throw new ServicioException(
                    "Error al intentar recuperarServidoresDeApoyoDadoDesconcentradoId", e);
        }
    }

    /**
     * RECUEPERA LOS SERVIDORES DE APOYO DE UN DESCONCONCENTRADO DADO SU ID VIGENTES O NO
     *
     * @param desconcentradoId identificador del desconcentrado
     * @return lista de apoyos
     * @throws ServicioException error en el servicio
     */
    public List<DesconcentradoApoyo> recuperarTodosServidoresDeApoyoDadoDesconcentradoId(
            final Long desconcentradoId) throws ServicioException {
        try {
            return desconcentradoApoyoDao.buscarTodosPorDesconcentradoId(desconcentradoId);
        } catch (DaoException e) {
            throw new ServicioException(
                    "Error al intentar recuperarTodosServidoresDeApoyoDadoDesconcentradoId", e);
        }
    }

    /**
     * RECUEPERA LAS UNIDADES ORGANIZACIONALES ASOCIADAS A UN DESCONCONCENTRADO DADO SU ID
     *
     * @param desconcentradoId identificador del desconcentrado
     * @return lista de unidiades organizacionales del desconcentrado
     * @throws ServicioException error en el servicio
     */
    public List<DesconcentradoUnidadOrganizacional> recuperarUnidadesOrganizacionalesAsociadasADesconcentrado(
            final Long desconcentradoId) throws ServicioException {
        try {
            return desconcentradoUnidOrgDao.buscarPorDesconcentradoId(desconcentradoId);
        } catch (DaoException e) {
            throw new ServicioException(
                    "Error al intentar recuperarUndiadesOrganizacionalesAsociadasADesconcentrado", e);
        }
    }

    /**
     * GUARDA UN DESCONCENTRADO
     *
     * @param guardarSoloDatosGeneralesYApoyos indicador de guardad solo datos generales y apoyo
     * @param d datos del desconcentrado
     * @param servidoresDeApoyo lista de los servidores de apoyo
     * @param unidadesOrganizacionales lista de la unidades organizacionales del desconcentrado
     * @throws ServicioException error en el servicio
     */
    public void guardarDesconcentrado(
            final Boolean guardarSoloDatosGeneralesYApoyos,
            final Desconcentrado d,
            final List<DesconcentradoApoyo> servidoresDeApoyo,
            final List<DesconcentradoUnidadOrganizacional> unidadesOrganizacionales) throws ServicioException {
        try {
            Date fechaCreacionModificacion = d.getFechaActualizacion() != null
                    ? d.getFechaActualizacion() : d.getFechaCreacion();
            if (d.getId() != null) {
                desconcentradoDao.actualizar(d);
            } else {
                desconcentradoDao.crear(d);
            }
            desconcentradoDao.flush();
            guardarHistoricoDesconcentrado(d, fechaCreacionModificacion);
            if (guardarSoloDatosGeneralesYApoyos != null && guardarSoloDatosGeneralesYApoyos) {
                guardarServidoresDeApoyoDesconcentrado(d, servidoresDeApoyo, fechaCreacionModificacion);
            } else if (guardarSoloDatosGeneralesYApoyos != null && !guardarSoloDatosGeneralesYApoyos) {
                guardarUnidadesOrganizacionalesDesconcentrado(d, unidadesOrganizacionales, fechaCreacionModificacion);
            } else {
                guardarServidoresDeApoyoDesconcentrado(d, servidoresDeApoyo, fechaCreacionModificacion);
                guardarUnidadesOrganizacionalesDesconcentrado(d, unidadesOrganizacionales, fechaCreacionModificacion);
            }

        } catch (DaoException e) {
            throw new ServicioException("Error al intentar guardar el desconcentrado", e);
        }
    }

    /**
     * GUARDA LOS SERVIDORES DE APOYO DE UN DESCONCENTRADO
     *
     * @param d Datos del desconcentrado
     * @param lda lista de apoyos
     * @param fechaCereacionModificacion fecha de creacion / modificado de los apoyos
     * @throws DaoException error en el acceso a los datos
     */
    private void guardarServidoresDeApoyoDesconcentrado(
            Desconcentrado d, List<DesconcentradoApoyo> lda, Date fechaCereacionModificacion) throws DaoException {
        try {
            List<DesconcentradoApoyo> actuales = desconcentradoApoyoDao.buscarPorDesconcentradoId(d.getId());
            String usuario = d.getUsuarioActualizacion() != null && !d.getUsuarioActualizacion().trim().isEmpty()
                    ? d.getUsuarioActualizacion() : d.getUsuarioCreacion();
            for (DesconcentradoApoyo da : actuales) {
                da.setVigente(Boolean.FALSE);
                da.setFechaActualizacion(fechaCereacionModificacion);
                da.setUsuarioActualizacion(usuario);
                desconcentradoApoyoDao.actualizar(da);
                desconcentradoApoyoDao.flush();
            }

            for (DesconcentradoApoyo da : lda) {
                da.getDesconcentrado().setId(d.getId());
                if (da.getId() == null) {
                    desconcentradoApoyoDao.crear(da);
                } else {
                    desconcentradoApoyoDao.actualizar(da);
                }
                desconcentradoApoyoDao.flush();
            }

        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     * GUARDA LAS UNIDADES ORGANIZACIONALES ASOCIADAS A UN DESCONCENTRADO
     *
     * @param d datos del desconcentrado
     * @param lDUO lista de la unidades organizacionales del desconcentrado
     * @param fechaCereacionModificacion fecha de creacion / modificacion de las unidades organizacionales del
     * desconcentrado
     * @throws DaoException error en el acceso a datos
     */
    private void guardarUnidadesOrganizacionalesDesconcentrado(
            Desconcentrado d,
            List<DesconcentradoUnidadOrganizacional> lDUO,
            Date fechaCereacionModificacion) throws DaoException {
        try {
            List<DesconcentradoUnidadOrganizacional> actuales = desconcentradoUnidOrgDao
                    .buscarPorDesconcentradoId(d.getId());
            String usuario = d.getUsuarioActualizacion() != null && !d.getUsuarioActualizacion().trim().isEmpty()
                    ? d.getUsuarioActualizacion() : d.getUsuarioCreacion();
            for (DesconcentradoUnidadOrganizacional dUO : actuales) {
                dUO.setVigente(Boolean.FALSE);
                dUO.setFechaActualizacion(fechaCereacionModificacion);
                dUO.setUsuarioActualizacion(usuario);
                desconcentradoUnidOrgDao.actualizar(dUO);
                desconcentradoUnidOrgDao.flush();
            }
            for (DesconcentradoUnidadOrganizacional dUO : lDUO) {
                dUO.getDesconcentrado().setId(d.getId());
                if (dUO.getId() == null) {
                    DesconcentradoUnidadOrganizacional du
                            = desconcentradoUnidOrgDao.buscar(d.getId(), dUO.getId());
                    if (du == null) {
                        desconcentradoUnidOrgDao.crear(dUO);
                    }
                } else {
                    desconcentradoUnidOrgDao.actualizar(dUO);
                }
                desconcentradoUnidOrgDao.flush();
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * AGREGA UN REGISTRO A LA TABLA HISTORICO DE DESCONCENTRADOS
     *
     * @param d Datos del desconcentrado
     * @param fechaCreacion fecha de creacion del historico
     * @throws DaoException error en el acceso a datos
     */
    private void guardarHistoricoDesconcentrado(Desconcentrado d, Date fechaCreacion) throws DaoException {
        try {
            DesconcentradoHistorico dh = new DesconcentradoHistorico();
            dh.setFechaCreacion(fechaCreacion);
            dh.setUsuarioCreacion(d.getUsuarioCreacion());
            dh.setVigente(Boolean.TRUE);
            dh.setServidorResponsable(d.getServidorResponsable());
            dh.setServidorResponsableNombre(d.getServidorResponsable().getApellidosNombres());
            dh.setDesconcentrado(d);
            dh.setDesconcentradoNombre(d.getNombre());
            desconcentradoHistoricosDao.crear(dh);
            desconcentradoHistoricosDao.flush();

        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @return @throws ServicioException
     */
    public List<Servidor> buscarServidoresResposablesYApoyosSegunRol(Long servidorId, String codigoRol)
            throws ServicioException {
        try {
            Set<String> identificaciones = new HashSet<>();
            List<Desconcentrado> desconcentrados = desconcentradoDao.buscarPorServidorResponsableId(servidorId);
            for (Desconcentrado desconcentrado : desconcentrados) {
                for (DesconcentradoApoyo apoyo : desconcentrado.getListaApoyos()) {
                    identificaciones.add(apoyo.getServidorApoyo().getNumeroIdentificacion());
                }
            }
            List<DesconcentradoApoyo> apoyos = desconcentradoApoyoDao.buscarPorServidorApoyoId(servidorId);
            for (DesconcentradoApoyo apoyo : apoyos) {
                identificaciones.add(apoyo.getDesconcentrado().getServidorResponsable().getNumeroIdentificacion());
                for (DesconcentradoApoyo apoyo2 : apoyo.getDesconcentrado().getListaApoyos()) {
                    identificaciones.add(apoyo2.getServidorApoyo().getNumeroIdentificacion());
                }
            }

            List<Servidor> servidores = new ArrayList<>();
            for (String identificacion : identificaciones) {
                RolServidor rolServidor = rolServidorDao.buscarServidorRol(identificacion, codigoRol);
                if (rolServidor != null) {
                    servidores.add(rolServidor.getServidor());
                }
            }
            return servidores;
        } catch (Exception e) {
            throw new ServicioException(e);

        }
    }

    /**
     * @param servidorId
     * @return @throws ServicioException
     */
    public List<Servidor> buscarServidoresResposablesYApoyos(Long servidorId) throws ServicioException {
        try {
            Set<Servidor> servidores = new HashSet<>();
            List<Desconcentrado> desconcentrados = desconcentradoDao.buscarPorServidorResponsableId(servidorId);
            for (Desconcentrado desconcentrado : desconcentrados) {
                for (DesconcentradoApoyo apoyo : desconcentrado.getListaApoyos()) {
                    servidores.add(apoyo.getServidorApoyo());
                }
            }
            List<DesconcentradoApoyo> apoyos = desconcentradoApoyoDao.buscarPorServidorApoyoId(servidorId);
            for (DesconcentradoApoyo apoyo : apoyos) {
                servidores.add(apoyo.getDesconcentrado().getServidorResponsable());
                for (DesconcentradoApoyo apoyo2 : apoyo.getDesconcentrado().getListaApoyos()) {
                    servidores.add(apoyo2.getServidorApoyo());
                }
            }
            return new ArrayList<>(servidores);
        } catch (Exception e) {
            throw new ServicioException(e);

        }
    }

    /**
     * Recupera los registros asociados al id de la unidad organizacional pasado como parametro
     *
     * @param unidadOrganizacionalId
     * @return
     * @throws ServicioException
     */
    public List<DesconcentradoUnidadOrganizacional> buscarDesconcentradosAsociadosAUnidadOrganizacional(
            final Long unidadOrganizacionalId) throws ServicioException {
        try {
            return desconcentradoUnidOrgDao.buscarPorUnidadOrganizacionalId(unidadOrganizacionalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recuperar los usuarios responsable del desconcentrado que le corresponde a una unidad organizacional.
     *
     * @param uo Datos del servidor del cual se requiere conocer sus responsables.
     * @return Lista de servidores responsables.
     * @throws ServicioException
     */
    public List<Servidor> buscarResponsablesDesconcentradoPorUnidadOrganizacional(UnidadOrganizacional uo, String funcionAcceso)
            throws ServicioException {
        try {
            List<Servidor> responsables = new ArrayList<>();
            List<DesconcentradoUnidadOrganizacional> desconcentrados
                    = desconcentradoUnidOrgDao.buscarPorUnidadOrganizacionalId(uo.getId());
            for (DesconcentradoUnidadOrganizacional desconcentrado : desconcentrados) {
                if (desconcentrado.getVigente()) {
                    if (desconcentrado.getFunciones().contains(funcionAcceso)) {
                        responsables.add(desconcentrado.getDesconcentrado().getServidorResponsable());
                        for (DesconcentradoApoyo apoyo : desconcentrado.getDesconcentrado().getListaApoyos()) {
                            if (apoyo.getVigente()) {
                                responsables.add(apoyo.getServidorApoyo());
                            }
                        }
                    }
                }
            }
            return responsables;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

}
