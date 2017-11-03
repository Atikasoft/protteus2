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
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
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
public class EstadoPuestoDao extends GenericDAO<EstadoPuesto, Long> {

    /**
     * Constructor por defecto.
     */
    public EstadoPuestoDao() {
        super(EstadoPuesto.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EstadoPuesto> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPuesto.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(EstadoPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Banco que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<EstadoPuesto> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPuesto.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<EstadoPuesto> buscarPorNemonico(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoPuesto.BUSCAR_POR_NEMONICO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @return @throws DaoException
     */
    public EstadoPuesto buscarPredeterminado() throws DaoException {
        try {
            EstadoPuesto entidad = null;
            List<EstadoPuesto> lista = buscarPorConsultaNombrada(EstadoPuesto.BUSCAR_PREDETERMINADO);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * Pasa a no predeterminado el registro que se encuentra como predetermiando
     * @throws DaoException 
     */
    public void removerPredeterminado() throws DaoException{
        ejecutarPorConsultaNombrada(EstadoPuesto.REMOVER_PREDETERMINADO);
    }
    
    /**
     * Busca segun codigo unico
     * @param codigo
     * @return
     * @throws DaoException 
     */
    public EstadoPuesto buscarPorCodigo(final String codigo) throws DaoException {
        try {
            return (EstadoPuesto) buscarUnicoPorConsultaNombrada(EstadoPuesto.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
