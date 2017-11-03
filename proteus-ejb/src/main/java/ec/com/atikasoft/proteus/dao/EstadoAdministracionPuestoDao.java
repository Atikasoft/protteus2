/*
 *  EstadoAdministracionPuestoDao.java
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
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
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
public class EstadoAdministracionPuestoDao extends GenericDAO<EstadoAdministracionPuesto, Long> {

    /**
     * Constructor por defecto.
     */
    public EstadoAdministracionPuestoDao() {
        super(EstadoAdministracionPuesto.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EstadoAdministracionPuesto> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoAdministracionPuesto.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(EstadoAdministracionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Estados puesto que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<EstadoAdministracionPuesto> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoAdministracionPuesto.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoAdministracionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por código.
     *
     * @param codigo String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EstadoAdministracionPuesto> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoAdministracionPuesto.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(EstadoAdministracionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Este metodo busca de un estado por código.
     *
     * @param codigo String
     * @return List
     * @throws DaoException DaoException
     */
    public EstadoAdministracionPuesto buscarPorCodigo(final String codigo) throws DaoException {
        try {
            List<EstadoAdministracionPuesto> lista = buscarPorConsultaNombrada(EstadoAdministracionPuesto.BUSCAR_POR_CODIGO, codigo);
            if(lista!=null && !lista.isEmpty()){
                return lista.get(0);
            }
            return null;
        } catch (DaoException ex) {
            Logger.getLogger(EstadoAdministracionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    
}
