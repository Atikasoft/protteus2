/*
 *  CotizacionIessDao.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CotizacionIess;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * CotizacionIessDao
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@LocalBean
@Stateless
public class CotizacionIessDao extends GenericDAO<CotizacionIess, Long> {

    /**
     * Constructor por defecto.
     */
    public CotizacionIessDao() {
        super(CotizacionIess.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<CotizacionIess> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CotizacionIess.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(CotizacionIessDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de CotizacionIess que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<CotizacionIess> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CotizacionIess.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(CotizacionIessDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param nivelOcupacionalId
     * @return List
     * @throws DaoException DaoException
     */
    public List<CotizacionIess> buscarPorNemonico(final Long nivelOcupacionalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CotizacionIess.BUSCAR_POR_NIVEL_OCUPACIONAL, nivelOcupacionalId);
        } catch (DaoException ex) {
            Logger.getLogger(CotizacionIessDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
