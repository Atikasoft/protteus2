/*
 *  UbicacionGeograficaDao.java
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
 *  23/09/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.UbicacionGeografica;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * UbicacionGeograficaDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class UbicacionGeograficaDao extends GenericDAO<UbicacionGeografica, Long> {

    /**
     * Constructor por defecto.
     */
    public UbicacionGeograficaDao() {
        super(UbicacionGeografica.class);
    }

    /**
     *
     * @return @throws DaoException
     */
    public List<UbicacionGeografica> buscarActivosOrdenados() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UbicacionGeografica.BUSCAR_ORDENADA);
        } catch (Exception ex) {
            Logger.getLogger(UbicacionGeograficaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param idUbicacionGeografica
     * @return
     * @throws DaoException
     */
    public List<UbicacionGeografica> buscarPorPadre(final Long idUbicacionGeografica) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UbicacionGeografica.BUSCAR_POR_PADRE, idUbicacionGeografica);
        } catch (Exception ex) {
            Logger.getLogger(UbicacionGeograficaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @return @throws DaoException
     */
    public List<UbicacionGeografica> buscarPaises() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UbicacionGeografica.BUSCAR_PAISES);
        } catch (Exception ex) {
            Logger.getLogger(UbicacionGeograficaDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<UbicacionGeografica> buscarPorNemonico(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UbicacionGeografica.BUSCAR_POR_NEMONICO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(UbicacionGeograficaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
