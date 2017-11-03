/*
 *  MenuRolDao.java
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
 *  16/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.MenuRol;
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
public class MenuRolDao extends GenericDAO<MenuRol, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(MenuRolDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public MenuRolDao() {
        super(MenuRol.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param idMenu Long
     * @param idRol
     * @return List
     * @throws DaoException DaoException
     */
    public List<MenuRol> buscarTodosPorMenuYRol(final Long idMenu, final Long idRol) throws DaoException {
        try {
            return buscarPorConsultaNombrada(MenuRol.BUSCAR_POR_MENU_Y_ROL, idMenu,idRol);
        } catch (DaoException ex) {
            Logger.getLogger(MenuRolDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<MenuRol> buscarTodosPorRol(final Long idRol) throws DaoException {
        try {
            return buscarPorConsultaNombrada(MenuRol.BUSCAR_POR_ROL, idRol);
        } catch (DaoException ex) {
            Logger.getLogger(MenuRolDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de MenuRol que esten vigentes
     * true.
     *
     * @return Listado MenuRol
     * @throws DaoException En caso de error
     */
    public List<MenuRol> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(MenuRol.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(MenuRolDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo guarda una lista de Menus asignados a un rol.
     *
     * @param lista
     * @throws DaoException captura de errores.
     */
    public void guardarAsignacionMenuRoles(
            final List<MenuRol> lista) throws DaoException {
        try {
            for (MenuRol m : lista) {
                crear(m);
            }
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

}
