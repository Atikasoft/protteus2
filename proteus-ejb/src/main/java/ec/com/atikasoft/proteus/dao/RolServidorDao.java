/*
 *  RolDistributivoDetalleDao.java
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
 *  21/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class RolServidorDao extends GenericDAO<RolServidor, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RolServidorDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public RolServidorDao() {
        super(RolServidor.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por id del servidor.
     *
     * @param idServidor Long
     * @return List
     * @throws DaoException DaoException
     */
    public List<RolServidor> buscarTodosPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(RolServidorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por id del rol.
     *
     * @param idRol
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<RolServidor> buscarTodosPorRol(final Long idRol) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_ROL, idRol);
        } catch (DaoException ex) {
            Logger.getLogger(RolServidorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo del rol.
     *
     * @param idRol
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<RolServidor> buscarPorRol(final String codigoRol) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_CODIGO_ROL, codigoRol);
        } catch (DaoException ex) {
            Logger.getLogger(RolServidorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo del rol.
     *
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<RolServidor> buscarPorRol(final String codigoRol, final String codigoUnidadOrganizacional) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_CODIGO_ROL_UNIDAD, codigoRol,
                    codigoUnidadOrganizacional);
        } catch (DaoException ex) {
            Logger.getLogger(RolServidorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param numeroIdentificacion
     * @param codigoRol
     * @return
     * @throws DaoException
     */
    public RolServidor buscarServidorRol(final String numeroIdentificacion, final String codigoRol) throws DaoException {
        try {
            RolServidor entidad = null;
            List<RolServidor> lista = buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_SERVIDOR_ROL,
                    numeroIdentificacion, codigoRol);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param numeroIdentificacion
     * @param codigoRol
     * @param codigoUnidadOrganizacional
     * @return
     * @throws DaoException
     */
    public RolServidor buscarServidorRol(final String numeroIdentificacion, final String codigoRol,
            final String codigoUnidadOrganizacional) throws DaoException {
        try {
            RolServidor entidad = null;
            List<RolServidor> lista = buscarPorConsultaNombrada(RolServidor.BUSCAR_POR_SERVIDOR_ROL_UNIDAD,
                    numeroIdentificacion, codigoRol);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de RolDistributivoDetalle que esten vigentes true.
     *
     * @return Listado RolDistributivoDetalle
     * @throws DaoException En caso de error
     */
    public List<RolServidor> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(RolServidor.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RolServidorDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo guarda una lista de Distributivos asignados a un rol.
     *
     * @param lista
     * @throws DaoException captura de errores.
     */
    public void guardarAsignacionRolServidores(
            final List<RolServidor> lista) throws DaoException {
        try {
            for (RolServidor m : lista) {
                if (m.getId() != null) {
                    actualizar(m);
                } else {
                    crear(m);
                }
            }
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param servidorId
     * @throws DaoException
     */
    public void quitarVigencia(final Long servidorId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(RolServidor.QUITAR_VIGENCIA, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
