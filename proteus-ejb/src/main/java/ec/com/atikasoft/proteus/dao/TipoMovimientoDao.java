/*
 *  TipoMovimientoDao.java
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
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoMovimientoDao extends GenericDAO<TipoMovimiento, Long> {

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimientoDao() {
        super(TipoMovimiento.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<TipoMovimiento> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase.
     *
     * @param id Long
     * @return List
     * @throws DaoException DaoException
     */
    public List<TipoMovimiento> buscarActivosPorClase(final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_ACTIVOS_POR_CLASE, id);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este método busca los Tipos Movimientos activos y segun el id de clase y sin el tipo de movimiento padre.
     *
     * @param idClase Id de la Clase a buscar
     * @param idTipoMovimiento Id del tipo de movimiento
     * @return List
     * @throws DaoException DaoException
     */
    public List<TipoMovimiento> buscarActivosPorClaseSinPadre(final Long idClase, final Long idTipoMovimiento)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_ACTIVOS_POR_CLASE_SIN_PADRE, idClase,
                    idTipoMovimiento);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar el tipo de movimiento por nemonico.
     *
     * @param nemonico Nemonico
     * @return Lista de tipo de movimiento
     * @throws DaoException En caso de error
     */
    public List<TipoMovimiento> buscarTodosPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_NEMONICO, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todos los tipos de movimientos activos.
     *
     * @return Lista de tipo de movimiento activos.
     * @throws DaoException En caso de error
     */
    public List<TipoMovimiento> buscarTodosActivos(final Boolean conRequisitos, final Boolean conAlertas,
            final Boolean conReglas) throws DaoException {
        try {
            List<TipoMovimiento> tipos = buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_ACTIVOS);
            for (TipoMovimiento tm : tipos) {
                if (conAlertas) {
                    tm.getListaAlerta().size();
                }
                if (conReglas) {
                    tm.getListaReglas().size();
                }
                if (conRequisitos) {
                    tm.getListaRequisitos();
                }
            }
            return tipos;
        } catch (Exception ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo busca los tipos movimientos y los ordena por nombre.
     *
     * @return List
     * @throws DaoException Captura de errores
     */
    public List<TipoMovimiento> buscarTodosActivos() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_ACTIVOS_ORDEN_NOMBRE);
        } catch (Exception ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera tipos de movimientos desconcentrados.
     *
     * @return
     * @throws DaoException
     */
    public List<TipoMovimiento> buscarPorTipoGestionDesconcentrado(final Long claseId) throws DaoException {
        return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_TIPO_GESTION_DESCONCENTRADO, claseId);
    }

    /**
     * Recupera tipos de movimientos desconcentrados.
     *
     * @return
     * @throws DaoException
     */
    public List<TipoMovimiento> buscarPorTipoGestionCentralizado(final Long claseId) throws DaoException {
        return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_TIPO_GESTION_CENTRALIZADO, claseId);
    }

    /**
     * Metodo que se encarga de buscar los grupos vigentes.
     *
     * @return Lista de grupos
     * @throws DaoException En caso de error
     */
    public List<TipoMovimiento> buscarTodosVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_TODOS_VIGENTE);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos vigentes ordenados.
     *
     * @return Lista de grupos
     * @throws DaoException En caso de error
     */
    public List<TipoMovimiento> buscarTodosVigenteOrdenados() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_TODOS_VIGENTE_ORDENADO);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera tipos de movimientos con solicitud.
     *
     * @param nemonico
     * @return
     * @throws DaoException
     */
    public List<TipoMovimiento> buscarConSolicitud(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_GRUPO_CON_SOLICITUD, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera tipos de movimientos por modalidad laboral.
     *
     * @param nemonico
     * @return
     * @throws DaoException
     */
    public List<TipoMovimiento> buscarPorModalidadLaboralConSolicitud(final String nemonico,
            final Long modalidadLaboralId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_GRUPO_CON_SOLICITUD_MODALIDAD_LABORAL, nemonico,
                    modalidadLaboralId);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recuperar los tipos e movimients activos dado un grupo.
     * @param nemonicoGrupo
     * @return
     * @throws DaoException 
     */
    public List<TipoMovimiento> buscarPorGrupo(final String nemonicoGrupo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_GRUPO, nemonicoGrupo);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Listar los tipos de movimientos que pertencen a un grupo, dado el id del grupo
     * @param idGrupo
     * @return
     * @throws DaoException 
     */
    public List<TipoMovimiento> buscarPorIdGrupo(final Long idGrupo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoMovimiento.BUSCAR_POR_ID_GRUPO, idGrupo);
        } catch (DaoException ex) {
            Logger.getLogger(TipoMovimientoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}