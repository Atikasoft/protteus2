/*
 *  UnidadAprobacionDao.java
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
 *  17/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Rubro;
import ec.com.atikasoft.proteus.modelo.UnidadAprobacion;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class UnidadAprobacionDao extends GenericDAO<UnidadAprobacion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(UnidadAprobacionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public UnidadAprobacionDao() {
        super(UnidadAprobacion.class);
    }

    /**
     * Este metodo procesa la busqueda de todos los vigentes por el Aprobador
     *
     * @param idAprobador Id del aprobador
     * @return List de registros de UnidadAprobacion
     * @throws DaoException DaoException
     */
    public List<UnidadAprobacion> buscarTodosPorAprobador(final Long idAprobador) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadAprobacion.BUSCAR_POR_APROBADOR, idAprobador);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadAprobacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     /**
     * Este metodo procesa la busqueda de todos los vigentes por el Nombre de la Unidad organizacional
     *
     * @param idUnidad id de UnidadOrganizacional a buscar
     * @return List de registros de UnidadAprobacion
     * @throws DaoException DaoException
     */
    public List<UnidadAprobacion> buscarTodosPorUnidadOrganizacional(final Long idUnidad) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadAprobacion.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, idUnidad);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadAprobacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
 /**
     * Este metodo procesa la busqueda de todo por id de la Unidad Organizacional y id de su aprobador.
     *
      * @param idAprobador identificador de la Unidad Aprobadora
     * @param idUnidad identificador de la Unidad Organizacional
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadAprobacion> buscarTodosPorSiExiste(final Long idAprobador, final Long idUnidad) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadAprobacion.BUSCAR_DUPLICADOS, idAprobador,idUnidad);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Metodo que se encarga de buscar un listado de UnidadAprobacion que esten vigentes true.
     *
     * @return Listado UnidadAprobacion
     * @throws DaoException En caso de error
     */
    public List<UnidadAprobacion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadAprobacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadAprobacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}