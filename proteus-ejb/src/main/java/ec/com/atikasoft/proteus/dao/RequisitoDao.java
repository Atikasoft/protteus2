/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Requisito;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.io.Serializable;
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
public class RequisitoDao extends GenericDAO<Requisito, Long> {

    /**
     * Constructor sin Argumentos.
     */
    public RequisitoDao() {
        super(Requisito.class);
    }

    public List<Requisito> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Requisito.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(RequisitoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);

        }
    }

    /**
     * Metodo que se encarga de buscar los requisitos por el id de grupo.
     *
     * @param grupoId Id del grupo por el cual se quiere buscar
     * @return Lista de requisitos
     * @exception DaoException En caso de error
     */
    public List<Requisito> buscarPorGrupoId(final Long grupoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Requisito.BUSCAR_POR_GRUPO_ID, grupoId);
        } catch (Exception ex) {
            Logger.getLogger(RequisitoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Método que se encarga de buscar los requisitos por el nemónico.
     * @param nemonico String
     * @return
     * @throws DaoException 
     */
      public List<Requisito> buscarTodosPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Requisito.BUSCAR_POR_NEMONICO, nemonico);
        } catch (Exception ex) {
            Logger.getLogger(RequisitoDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public Requisito buscarPorNemonico(final String nemonico) throws DaoException {
        try {
            Requisito entidad = null;
            List<Requisito> lista = buscarPorConsultaNombrada(Requisito.BUSCAR_POR_NEMONICO, nemonico);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
  
}
