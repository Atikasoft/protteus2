/*
 *  VacacionParametroDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  28/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class VacacionParametroDao extends GenericDAO<VacacionParametro, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionParametroDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VacacionParametroDao() {
        super(VacacionParametro.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<VacacionParametro> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionParametro.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(VacacionParametroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de VacacionParametro que esten vigentes true.
     *
     * @return Listado VacacionParametro
     * @throws DaoException En caso de error
     */
    public List<VacacionParametro> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionParametro.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionParametroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de VacacionParametro vigentes por nombre
     *
     * @param nombre nombre
     * @return Listado VacacionParametro
     * @throws DaoException En caso de error
     */
    public List<VacacionParametro> buscarPorNombreDuplicados(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionParametro.BUSCAR_POR_NOMBRE, nombre);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionParametroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de VacacionParametro vigentes por regimen laboral
     *
     * @param idRegimen identificador del regimen laboral
     * @return Listado VacacionParametro
     * @throws DaoException En caso de error
     */
    public List<VacacionParametro> buscarPorRegimenLaboral(final Long idRegimen) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionParametro.BUSCAR_POR_REGIMEN_LABORAL, idRegimen);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionParametroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
