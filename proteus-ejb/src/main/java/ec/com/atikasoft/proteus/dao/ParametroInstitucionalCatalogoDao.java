/*
 *  TramiteDao.java
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
 *  30/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucionalCatalogo;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class ParametroInstitucionalCatalogoDao extends GenericDAO<ParametroInstitucionalCatalogo, Long> {

    /**
     * Constructor.
     */
    public ParametroInstitucionalCatalogoDao() {
        super(ParametroInstitucionalCatalogo.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws DaoException DaoException
     */
    public List<ParametroInstitucionalCatalogo> buscarTodosPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ParametroInstitucionalCatalogo.BUSCAR_POR_NEMONICO, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(ParametroInstitucionalCatalogoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<ParametroInstitucionalCatalogo> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ParametroInstitucionalCatalogo.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(ParametroInstitucionalCatalogoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public List<ParametroInstitucionalCatalogo> buscarSinParametrosInstitucionales(final Long institucionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ParametroInstitucionalCatalogo.BUSCAR_SIN_PARAMETROS_INSTITUCIONALES, institucionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
