/*
 *  ClaseDao.java
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
import ec.com.atikasoft.proteus.modelo.Clase;
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
public class ClaseDao extends GenericDAO<Clase, Long> {

    /**
     * Constructor sin argumentos.
     */
    public ClaseDao() {
        super(Clase.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Clase> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Clase.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(ClaseDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar las clases por el id del grupo.
     *
     * @param grupoId Id de grupo
     * @return Lista<Clase> lista de las clases encontradas
     * @throws DaoException En caso de error
     */
    public List<Clase> buscarClasePorGrupoId(final Long grupoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Clase.BUSCAR_POR_GRUPO_ID, grupoId);
        } catch (DaoException ex) {
            Logger.getLogger(ClaseDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
     /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Clase> buscarPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Clase.BUSCAR_POR_NEMONICO, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(ClaseDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}