/*
 *  AsignacionDao.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
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
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * Acceso a datos de la entidad Tarea.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class TareasDao extends GenericDAO<Tarea, Long> {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(TareasDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TareasDao() {
        super(Tarea.class);
    }

    /**
     * Cuenta la tareas de un usuario.
     *
     * @param usuario Identificacion del usuario.
     * @param codigoInstitucion Codigo de la intitucion.
     * @param ejercicioFiscal Ejercicio fiscal.
     * @return Numero de tareas.
     * @throws DaoException Error de ejecucion.
     */
    public Long contar(final String usuario, final String codigoInstitucion, final Boolean asignado) throws DaoException {
        return contarPorConsultaNombrada(Tarea.CONTAR_TAREAS_POR_USUARIO, usuario, codigoInstitucion, asignado);
    }

    /**
     * Cuenta la tareas de un usuario.
     *
     * @param usuario Identificacion del usuario.
     * @return Numero de tareas.
     * @throws DaoException Error de ejecucion.
     */
    public Long contar(final String usuario) throws DaoException {
        return contarPorConsultaNombrada(Tarea.CONTAR_TAREAS_PENDIENTES, usuario);
    }

    /**
     * Cuenta la tareas de un usuario dentro de una o varias unidades organizacionales y estado de la tarea(asignada o
     * no) .
     *
     * @param usuario Identificacion del usuario.
     * @param codigosUnidades Codigo de las unidades organizacionales.
     * @param asignado Tarea asignada o no.
     * @return Numero de tareas.
     * @throws DaoException Error de ejecucion.
     */
    public Long contarTareasPorUsuarioUnidadesOrganizacionalesEstado(
            final String usuario, final String codigosUnidades, final Boolean asignado) throws DaoException {
        List<String> listaCodigos = Arrays.asList(codigosUnidades.split(","));
        return contarPorConsultaNombrada(
                Tarea.CONTAR_TAREAS_POR_USUARIO_UNIDADES_ORGANIZACIONALES_Y_ESTADO, usuario, listaCodigos, asignado);
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
     * @throws DaoException Error de ejecucion.
     */
    public List<Tarea> buscar(final String usuario, final String codigoInstitucion, final Boolean asignado,
            final String token, final String ordenar, final String tipo) throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT o FROM Tarea o  WHERE 1=1  ");
        generarFiltro(sql, parametros, "asignado", asignado);
        generarFiltro(sql, parametros, "usuarioAsignado", usuario);
        //    generarFiltro(sql, parametros, "codigoInstitucion", codigoInstitucion);
        if (token != null && !token.isEmpty()) {
            sql.append(" AND concat(o.descripcion,' ',o.nombreEstadoActual,' ',o.numeroExterno) LIKE :token ");
            parametros.put("token", UtilCadena.concatenar("%", token, "%"));
        }
        ordenar(ordenar, tipo, sql);
//        LOG.info("SQL:" + sql);
        Query query = getEntityManager().createQuery(sql.toString());
//        if (longitud >= 0) {
//            query.setMaxResults(longitud);
//        }
//        if (posicion >= 0) {
//            query.setFirstResult(posicion);
//        }
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
            System.out.println("\n\n" + key + " : " + parametros.get(key));
            //LOG.info("\nSQL: " + sql);
        }
        return query.getResultList();
    }

    /**
     * Cuenta las tareas de todos los usuarios de la institución.
     *
     * @param codigoInstitucion Codigo de la intitucion.
     * @param ejercicioFiscal Ejercicio fiscal.
     * @param asignado Asignados True/False.
     * @return Numero de tareas.
     * @throws DaoException
     */
    public List<Tarea> buscarTareasTodosUsuarios(final String codigoInstitucion, final Integer ejercicioFiscal,
            final Boolean asignado) throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT o FROM Tarea o  WHERE 1=1  ");
        generarFiltro(sql, parametros, "asignado", asignado);
        if (codigoInstitucion != null) {
            generarFiltro(sql, parametros, "codigoInstitucion", codigoInstitucion);
        }
        generarFiltro(sql, parametros, "ejercicioFiscal", ejercicioFiscal);
        ordenar("numeroExterno", "ASC", sql);
        Query query = getEntityManager().createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
        }
        return query.getResultList();
    }

    /**
     *
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<Tarea> buscarTareasDesconcentrados(Long servidorId) throws DaoException {
        return buscarPorConsultaNombrada(Tarea.BUSCAR_POR_DESCONCENTRADO, servidorId, servidorId, servidorId);
    }
    
    /**
     *
     * @param ejercicioFiscal ejercicio fiscal actual (Ej: 2017)
     * @return
     * @throws DaoException
     */
    public List<Tarea> buscarTareasServidoresInactivos() throws DaoException {
        return buscarPorConsultaNombrada(Tarea.BUSCAR_TAREAS_DE_SERVIDORES_INACTIVOS);
    }
}
