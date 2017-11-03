/*
 *  DenominacionPuestoDao.java
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
 *  19/09/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DenominacionPuesto;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * DenominacionPuestoDao
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@LocalBean
@Stateless
public class DenominacionPuestoDao extends GenericDAO<ec.com.atikasoft.proteus.modelo.DenominacionPuesto, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ec.com.atikasoft.proteus.modelo.DenominacionPuesto.class.getCanonicalName());

    /**
     * Constructor por defecto.
     */
    public DenominacionPuestoDao() {
        super(ec.com.atikasoft.proteus.modelo.DenominacionPuesto.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<DenominacionPuesto> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DenominacionPuesto.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(DenominacionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<DenominacionPuesto> buscarPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DenominacionPuesto.BUSCAR_POR_NEMONICO, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(DenominacionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de denominacion puestos que esten vigentes true.
     *
     * @return Listado ModalidadLaboral
     * @throws DaoException En caso de error
     */
    public List<DenominacionPuesto> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DenominacionPuesto.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(DenominacionPuestoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
