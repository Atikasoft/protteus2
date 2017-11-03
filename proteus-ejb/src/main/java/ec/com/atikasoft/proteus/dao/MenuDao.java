/*
 *  MenuDao.java
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
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.MenuVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class MenuDao extends GenericDAO<Menu, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(MenuDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public MenuDao() {
        super(Menu.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Menu> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Menu.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<Menu> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Menu.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Menu que esten vigentes true.
     *
     * @return Listado Menu
     * @throws DaoException En caso de error
     */
    public List<Menu> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Menu.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
      /**
     * Metodo que se encarga de buscar un listado de Menu que no tengan padres.
     *
     * @return Listado Menu
     * @throws DaoException En caso de error
     */
    public List<Menu> buscarVigentesPrincipales() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Menu.BUSCAR_MENUS_PRINCIPALES);
        } catch (DaoException ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
       /**
     * Este metodo procesa la busqueda de todo por id del menu padre.
     *
     * @param idMenuPadre Long
     * @return List
     * @throws DaoException DaoException
     */
    public List<Menu> buscarTodosPorMenuPadre(final Long idMenuPadre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Menu.BUSCAR_POR_MENU_PADRE, idMenuPadre);
        } catch (DaoException ex) {
            Logger.getLogger(MenuDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
     public List<Menu> buscarMenu(final MenuVO vo)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT m.menu FROM MenuRol m, RolServidor r ");
        sql.append(" WHERE  m.vigente = 1 and r.vigente = 1 and m.menu.vigente=1 ");
        sql.append(" and m.rol.id = r.rol.id ");
        if(vo.getTipo()!=null && !vo.getTipo().isEmpty()){
            sql.append(" and m.menu.tipo = :tipo");
             parametros.put("tipo", vo.getTipo());
        }
        if (vo.getServidorId() != null) {
            sql.append(" AND r.servidor.id =:servidor");
            parametros.put("servidor", vo.getServidorId());
        }
        if (vo.getEsPrincipal() != null) {
            if (vo.getEsPrincipal()) {
                sql.append(" AND m.menu.menu.id = 1");
            } else {
                sql.append(" AND m.menu.menu.id > 1");
            }
        }
        if (vo.getMenuPrincipalId() != null) {
            sql.append(" AND m.menu.menu.id =:menu");
            parametros.put("menu", vo.getMenuPrincipalId());
        }

        if (vo.getRolId() != null) {
            sql.append(" AND m.rol.id =:rol");
            parametros.put("rol", vo.getRolId());
        }
        sql.append(" order by m.menu.menu.id, m.menu.orden");
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        
        //System.out.println(obtenerSql(query, parametros));
        return query.getResultList();

    }
    
}