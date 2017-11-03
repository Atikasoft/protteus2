/*
 *  ParametroGlobalDao.java
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
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ParametroGlobalDao extends GenericDAO<ParametroGlobal, Long> {

    /**
     * Constructor sin argumentos.
     */
    public ParametroGlobalDao() {
        super(ParametroGlobal.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<ParametroGlobal> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ParametroGlobal.BUSCAR_POR_NOMBRE, "%" + nombre.toUpperCase() + "%");
        } catch (DaoException ex) {
            Logger.getLogger(ParametroGlobalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera un parametro global dado su nemonico unico.
     *
     * @param nemonico Nemonico.
     * @return Parametro global.
     * @throws DaoException Error en capa de persistencia.
     */
    public ParametroGlobal buscarPorNemonico(final String nemonico) throws DaoException {
        try {
            ParametroGlobal entidad = null;
            List<ParametroGlobal> lista = buscarPorConsultaNombrada(ParametroGlobal.BUSCAR_POR_NENOMICO, nemonico);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
