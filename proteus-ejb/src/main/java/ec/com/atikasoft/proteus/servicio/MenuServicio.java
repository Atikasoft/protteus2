/*
 *  MenuServicio.java
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
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.MenuRolDao;
import ec.com.atikasoft.proteus.dao.RolDao;
import ec.com.atikasoft.proteus.dao.MenuDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.modelo.MenuRol;
import ec.com.atikasoft.proteus.modelo.RolServidor;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.MenuVO;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class MenuServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private final Logger LOG = Logger.getLogger(MenuServicio.class.getCanonicalName());
    /**
     * DAO de Menu.
     */
    @EJB
    private MenuDao menuDao;

    /**
     * DAO de Rol.
     */
    @EJB
    private RolDao rolDao;

    /**
     * DAO de MenuRol.
     */
    @EJB
    private MenuRolDao menuRolDao;

    /**
     * DAO de MenuRol.
     */
    @EJB
    private RolServidorDao rolServidorDao;

    /**
     * metodo para guardar un menu
     *
     * @param menu registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarMenu(final Menu menu) throws ServicioException {
        try {
            menu.setCodigo(menu.getCodigo().toUpperCase());
            menu.setNombre(menu.getNombre().toUpperCase());
            menu.setVigente(Boolean.TRUE);
            menuDao.crear(menu);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un menu
     *
     * @param menu Instancia de menu
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarMenu(final Menu menu) throws ServicioException {
        try {
            menu.setNombre(menu.getNombre().toUpperCase());
            menuDao.actualizar(menu);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un menu
     *
     * @param menu registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarMenu(final Menu menu) throws ServicioException {
        try {
            menu.setVigente(Boolean.FALSE);
            menuDao.actualizar(menu);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los menus vigentes
     *
     * @return List<Menu> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Menu> listarTodosMenusVigentesPrincipales() throws ServicioException {
        try {
            List<Menu> lista;
            lista = menuDao.buscarVigentesPrincipales();
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Menu> listarTodasMenusPorNombre(final String nombre) throws ServicioException {
        try {
            List<Menu> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = menuDao.buscarVigente();
            } else {
                lista = menuDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Busca lista de menus por id del menu padre.
     *
     * @param idMenuPadre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Menu> listarTodasMenusPorMenuPadre(final Long idMenuPadre) throws ServicioException {
        try {
            List<Menu> lista = menuDao.buscarTodosPorMenuPadre(idMenuPadre);
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de menus por codigo.
     *
     * @param codigo String del menu del menu para la busqueda
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Menu> listarTodosMenuPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Menu> listaMenu;
            listaMenu = menuDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaMenu;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de un rol
     *
     * @param rol registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarRol(final Rol rol) throws ServicioException {
        try {
            rol.setCodigo(rol.getCodigo().toUpperCase());
            rol.setNombre(rol.getNombre().toUpperCase());
            rolDao.crear(rol);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un anticipoPago
     *
     * @param rol registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarRol(final Rol rol) throws ServicioException {
        try {
            rol.setNombre(rol.getNombre().toUpperCase());
            rolDao.actualizar(rol);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un rol
     *
     * @param rol registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarRol(final Rol rol) throws ServicioException {
        try {
            rol.setVigente(Boolean.FALSE);
            rolDao.actualizar(rol);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los menus vigentes
     *
     * @return List<Rol> listado obtenido
     */
    public List<Rol> listarTodosRolesVigentes() throws ServicioException {
        try {
            return rolDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<Rol> listarTodosRolesPorNombre(final String nombre) throws ServicioException {
        try {
            List<Rol> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = rolDao.buscarVigente();
            } else {
                lista = rolDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de roles por codigo.
     *
     * @param codigo String del menu del menu para la busqueda
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Rol> listarTodosRolPorCodigo(final String codigo) throws ServicioException {
        try {
            List<Rol> listaRol;
            listaRol = rolDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaRol;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite listar los registros de menu por id del rol.
     *
     * @param idRol String del menu del menu para la busqueda
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<MenuRol> listarTodosMenuPorRol(final Long idRol) throws ServicioException {
        try {
            List<MenuRol> listaRol = menuRolDao.buscarTodosPorRol(idRol);
            return listaRol;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }
  
    /**
     * Este método permite buscar menus
     *
     * @param vo String del menu del menu para la busqueda
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Menu> listarTodosMenus(final MenuVO vo) throws ServicioException {
        try {
            List<Menu> lista = menuDao.buscarMenu(vo);
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }
    
      /**
     * Este método permite buscar menus roles por id del menu
     *
     * @param idMenu Long
     * @param idRol
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<MenuRol> listarTodosMenusRolesPorMenuYRol(final Long idMenu, final Long idRol) throws ServicioException {
        try {
            List<MenuRol> lista = menuRolDao.buscarTodosPorMenuYRol(idMenu,idRol);
            return lista;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /* Este metodo guarda una lista de instituciones para descentralizarse.
     *
     * @param lista final
     * @throws DaoException captura de errores.
     */
    public void guardarMenusRoles(
            final List<MenuRol> lista) throws ServicioException {
        try {
            menuRolDao.guardarAsignacionMenuRoles(lista);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite actualizar un MenuRol
     *
     * @param menuRol registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarMenuRol(final MenuRol menuRol) throws ServicioException {
        try {
            menuRolDao.actualizar(menuRol);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los registros de usuario por id del servidor.
     *
     * @param idServidor Long del servidor a buscar
     * @return lista demenus recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<RolServidor> listarTodosRolServidorPorServidor(final Long idServidor) throws ServicioException {
        try {
            List<RolServidor> listaRol = rolServidorDao.buscarTodosPorServidor(idServidor);
            return listaRol;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /* Este metodo guarda una lista de RolServidor.
     *
     * @param lista final
     * @throws DaoException captura de errores.
     */
    public void guardarRolServidor(
            final List<RolServidor> lista) throws ServicioException {
        try {
            rolServidorDao.guardarAsignacionRolServidores(lista);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite actualizar un RolServidor
     *
     * @param reg registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarRolServidor(final RolServidor reg) throws ServicioException {
        try {
            rolServidorDao.actualizar(reg);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }
}
