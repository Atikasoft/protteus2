/*
 *  GastoPersonalDao.java
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
 *  14/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.GastoPersonal;
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
public class GastoPersonalDao extends GenericDAO<GastoPersonal, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(GastoPersonalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public GastoPersonalDao() {
        super(GastoPersonal.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por id de servidor.
     *
     * @param idServidor Long
     * @return List Lista de registros de GastoPersonal
     * @throws DaoException DaoException
     */
    public List<GastoPersonal> buscarTodosPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(GastoPersonal.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(GastoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por id de servidor y id de institucion ejericio fiscal.
     *
     * @param idServidor Long
     * @param idEjercicio Long id del institucion ejercicio fiscal
     * @return List Lista de registros de GastoPersonal
     * @throws DaoException DaoException
     */
    public List<GastoPersonal> buscarTodosPorServidorYEjercicioFiscal(final Long idServidor, final Long idEjercicio)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(GastoPersonal.BUSCAR_POR_SERVIDOR_Y_EJERCICIO_FISCAL, idServidor,
                    idEjercicio);
        } catch (DaoException ex) {
            Logger.getLogger(GastoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por id de servidor.
     *
     * @param idEjercicioFiscal Long
     * @return List Lista de registros de GastoPersonal
     * @throws DaoException DaoException
     */
    public List<GastoPersonal> buscarTodosPorEjercicioFiscalInstitucion(final Long idEjercicioFiscal) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(GastoPersonal.BUSCAR_POR_SERVIDOR, idEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(GastoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de GastoPersonal que esten vigentes true.
     *
     * @return Listado GastoPersonal
     * @throws DaoException En caso de error
     */
    public List<GastoPersonal> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(GastoPersonal.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(GastoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}