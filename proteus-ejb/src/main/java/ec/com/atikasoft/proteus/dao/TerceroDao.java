/*
 *  TerceroDao.java
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
 *  25/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Tercero;
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
public class TerceroDao extends GenericDAO<Tercero, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(TerceroDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TerceroDao() {
        super(Tercero.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Tercero que esten vigentes
     * true por identificacion de tercero.
     *
     * @param tipoDocumento
     * @param numeroDocumento
     * @return Listado Tercero
     * @throws DaoException En caso de error
     */
    public List<Tercero> buscarVigentePorIdentificacion(final String tipoDocumento, final String numeroDocumento) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Tercero.BUSCAR_POR_IDENTIFICACION, tipoDocumento, numeroDocumento);
        } catch (DaoException ex) {
            Logger.getLogger(TerceroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
 

    /**
     * Metodo que se encarga de buscar un listado de Tercero que esten vigentes
     * true.
     *
     * @return Listado Tercero
     * @throws DaoException En caso de error
     */
    public List<Tercero> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Tercero.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(TerceroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param ejercicioFiscalId
     * @param estado
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<Tercero> buscarPorEstado(final Long ejercicioFiscalId, final String estado) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Tercero.BUSCAR_POR_ESTADO_INST_EJERCICIO, ejercicioFiscalId, estado);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
