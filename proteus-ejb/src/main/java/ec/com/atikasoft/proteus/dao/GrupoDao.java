/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Grupo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@LocalBean
@Stateless
public class GrupoDao extends GenericDAO<Grupo, Long> {

    /**
     * Constructor sin Argumentos.
     */
    public GrupoDao() {
        super(Grupo.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre
     * @return List
     * @throws DaoException
     */
    public List<Grupo> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Grupo.BUSCAR_POR_NOMBRE, "%" + nombre + "%");
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los grupos vigentes.
     *
     * @return Lista de grupos
     * @throws DaoException En caso de error
     */
    public List<Grupo> buscarTodosVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Grupo.BUSCAR_TODOS_VIGENTE);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Metodo que se encarga de buscar los grupos por Nemónico.
     * @param nemonico String
     * @return
     * @throws DaoException 
     */
      public List<Grupo> buscarTodosPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Grupo.BUSCAR_POR_NEMONICO, nemonico);
        } catch (Exception ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
//    public List<Grupo> buscarActivos(){
//        
//    }
}
