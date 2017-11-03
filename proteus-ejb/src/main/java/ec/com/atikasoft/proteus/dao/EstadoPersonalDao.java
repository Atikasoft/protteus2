/*
 *  EstadoPuestoDao.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@LocalBean
@Stateless
public class EstadoPersonalDao extends GenericDAO<EstadoPersonal, Long> {

    /**
     * Constructor por defecto.
     */
    public EstadoPersonalDao() {
        super(EstadoPersonal.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EstadoPersonal> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPersonal.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(EstadoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Estados puesto que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<EstadoPersonal> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPersonal.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<EstadoPersonal> buscarPorNemonico(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPersonal.BUSCAR_POR_NEMONICO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @return @throws DaoException
     */
    public EstadoPersonal buscarPredeterminado() throws DaoException {
        try {
            EstadoPersonal entidad = null;
            List<EstadoPersonal> lista = buscarPorConsultaNombrada(EstadoPersonal.BUSCAR_PREDETERMINADO);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
