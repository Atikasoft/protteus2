/*
 *  ProcesoServicio.java
 *  Proteus V 1.0 $Revision 1.0 $
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
 *  Oct 21, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.DetalleDao;
import ec.com.atikasoft.proteus.dao.ReasignacionTareasDao;
import ec.com.atikasoft.proteus.dao.TareasDao;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.ReasignacionTarea;
import ec.com.atikasoft.proteus.modelo.Tarea;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Servicio que permite gestionar el proceso.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class TareaServicio {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TareaServicio.class.getCanonicalName());

    /**
     * Dao de tareas.
     */
    @EJB
    private TareasDao tareaDao;

    @EJB
    private DetalleDao detalleDao;

    /**
     * Dao de reasignacion de tareas.
     */
    @EJB
    private ReasignacionTareasDao reasignacionTareaDao;

    /**
     * Constructor sin argumentos.
     */
    public TareaServicio() {
        super();
    }

    /**
     * Cuenta la tareas de un usuario.
     *
     * @param usuario Identificacion del usuario.
     * @param codigoInstitucion Codigo de la intitucion.
     * @param asignado
     * @return Numero de tareas.
     * @throws ServicioException Error de ejecucion.
     */
    public Long contar(final String usuario, final String codigoInstitucion, final Boolean asignado) throws
            ServicioException {
        try {
            return tareaDao.contar(usuario, codigoInstitucion, asignado);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Cuenta la tareas de un usuario.
     *
     * @param usuario Identificacion del usuario.
     * @param codigosUnidadesOrganizacionales Codigos de las Unidades Organizacionales
     * @param asignado valor del estado asgnada o no
     * @return Numero de tareas.
     * @throws ServicioException Error de ejecucion.
     */
    public Long contarTareasPorUsuarioUnidadesOrganizacionalesEstado(
            final String usuario,
            final String codigosUnidadesOrganizacionales,
            final Boolean asignado) throws ServicioException {
        try {
            return tareaDao.contarTareasPorUsuarioUnidadesOrganizacionalesEstado(
                    usuario, codigosUnidadesOrganizacionales, asignado);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Cuenta la tareas de un usuario.
     *
     * @param usuario Identificacion del usuario.
     * @param codigoInstitucion Codigo de la intitucion.
     * @param asignado
     * @param token
     * @param ordenar
     * @param tipo
     * @return Numero de tareas.
     * @throws ServicioException Error de ejecucion.
     */
    public List<Tarea> buscar(final String usuario, final String codigoInstitucion, final Boolean asignado,
            final String token, final String ordenar, final String tipo) throws
            ServicioException {
        try {
            return tareaDao.buscar(usuario, codigoInstitucion, asignado, token, ordenar, tipo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Cuenta las tareas de todos los usuarios.
     *
     * @param codigoInstitucion Código de la Institución.
     * @param ejercicioFiscal Ejercicio Fiscal.
     * @param asignado Asignados True/False.
     * @return Numero de tareas
     * @throws ServicioException
     */
    public List<Tarea> buscarTareasTodosUsuarios(final String codigoInstitucion, final Integer ejercicioFiscal,
            final Boolean asignado) throws ServicioException {
        try {
            return tareaDao.buscarTareasTodosUsuarios(codigoInstitucion, ejercicioFiscal, asignado);
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Busca las tareas asignadas a servidores inactivos.
     *
     * @param ejercicioFiscal Ejercicio Fiscal.
     * @return lista de tareas
     * @throws ServicioException
     */
    public List<Tarea> buscarTareasServidoresInactivos() throws ServicioException {
        try {
            return tareaDao.buscarTareasServidoresInactivos();
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método actualiza el la reasignación de la tarea.
     *
     * @param t Tramite
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void actualizarReasignacionTramite(final Tarea t) throws ServicioException {
        try {
            // actualizar la tarea.
            tareaDao.actualizar(t);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método guarda la reasignación de la tarea.
     *
     * @param rt ReasignacionTarea
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void guardarReasignacionTarea(final ReasignacionTarea rt) throws ServicioException {
        try {
            reasignacionTareaDao.crear(rt);
            rt.getTarea().getDetalle().setUsuario(rt.getTarea().getUsuarioAsignado());
            detalleDao.actualizar(rt.getTarea().getDetalle());
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }
}
