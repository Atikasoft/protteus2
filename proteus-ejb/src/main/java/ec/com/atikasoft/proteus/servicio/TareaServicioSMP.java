/**
 * TareaServicioSMP.java Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * Proteus ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Proteus.
 *
 * Proteus Quito - Ecuador
 *
 * 09/11/2012
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.TareasDao;
import ec.com.atikasoft.proteus.dao.TramiteDao;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.ReasignacionTarea;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.TareaVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Servicio para las tareas.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class TareaServicioSMP extends BaseServicio {

    /**
     * Servicio de tarea.
     */
    @EJB
    private TareaServicio tareaServicio;

    /**
     * DAO de Tramite.
     */
    @EJB
    private TramiteDao tramiteDao;

    /**
     * Servicio de desconcentrado.
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private TareasDao tareasDao;

    /**
     * Este metodo transanciona la busqueda de tareas.
     *
     * @param usuario String
     * @param codigoInstitucion String
     * @param asignado Boolean
     * @param token String
     * @param ordenar String
     * @param tipo String
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TareaVO> buscar(final String usuario, final String codigoInstitucion, final Boolean asignado,
            final String token, final String ordenar, final String tipo) throws ServicioException {
        try {
            List<TareaVO> resultados = new ArrayList<>();
            List<Tarea> buscar = tareaServicio.buscar(usuario, codigoInstitucion, asignado, token, ordenar, tipo);
            for (Tarea t : buscar) {
                Tramite tramite = tramiteDao.buscarPorId(t.getIdentificadorExterno());
                if (tramite != null) {
                    TareaVO tvo = new TareaVO();
                    tvo.setTarea(t);
                    tvo.setTipoMovimiento(tramite.getTipoMovimiento());
                    tvo.setTramite(tramite);
                    resultados.add(tvo);
                }
            }
            return resultados;
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo transanciona la busqueda de tareas de todos los usuarios.
     *
     * @param codigoInstitucion String
     * @param ejercicioFiscal Integer
     * @param asignado Boolean
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TareaVO> buscarTareasTodosUsuarios(final Boolean esRRHH, UsuarioVO usuarioVO,
            final Integer ejercicioFiscal, final Boolean asignado) throws ServicioException {
        try {
            List<TareaVO> resultados = new ArrayList<>();
            List<Tarea> tareas;
            if (esRRHH) {
                tareas = tareaServicio.buscarTareasTodosUsuarios(null, ejercicioFiscal, asignado);
            } else {
                tareas = tareasDao.buscarTareasDesconcentrados(usuarioVO.getServidor().getId());
            }
            for (Tarea t : tareas) {
                Tramite tramite = tramiteDao.buscarPorId(t.getIdentificadorExterno());
                if (tramite != null) {
                    TareaVO tvo = new TareaVO();
                    tvo.setTarea(t);
                    tvo.setTipoMovimiento(tramite.getTipoMovimiento());
                    resultados.add(tvo);
                }
            }
            return resultados;
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }
    
    /**
     * Este método realiza la búsqueda de tareas asignadas a servidores inactivos.
     *
     * @param ejercicioFiscal
     * @return List
     * @throws ServicioException ServicioException
     */
    public List<TareaVO> buscarPorServidoresInactivos() throws ServicioException {
        try {
            List<TareaVO> resultados = new ArrayList<>();
            List<Tarea> buscar = tareaServicio.buscarTareasServidoresInactivos();
            for (Tarea t : buscar) {
                Tramite tramite = tramiteDao.buscarPorId(t.getIdentificadorExterno());
                if (tramite != null) {
                    TareaVO tvo = new TareaVO();
                    tvo.setTarea(t);
                    tvo.setTipoMovimiento(tramite.getTipoMovimiento());
                    tvo.setTramite(tramite);
                    resultados.add(tvo);
                }
            }
            return resultados;
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método actualiza el la reasignación de la tarea.
     *
     * @param t Tramite
     */
    public void actualizarReasignacionTramite(final Tarea t) throws ServicioException {
        try {
            tareaServicio.actualizarReasignacionTramite(t);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método guarda la reasignación de la tarea.
     *
     * @param rt ReasignacionTarea
     */
    public void guardarReasignacionTarea(final ReasignacionTarea rt) throws ServicioException {
        try {
            tareaServicio.guardarReasignacionTarea(rt);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }
}
