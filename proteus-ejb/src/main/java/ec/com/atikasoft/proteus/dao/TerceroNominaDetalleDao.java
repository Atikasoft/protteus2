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
import ec.com.atikasoft.proteus.modelo.TerceroNominaDetalle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class TerceroNominaDetalleDao extends GenericDAO<TerceroNominaDetalle, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(TerceroNominaDetalleDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public TerceroNominaDetalleDao() {
        super(TerceroNominaDetalle.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de Tercero que esten vigentes true por identificacion de tercero.
     *
     * @param tipoDocumento
     * @param numeroDocumento
     * @return Listado Tercero
     * @throws DaoException En caso de error
     */
    public List<TerceroNominaDetalle> buscarVigentePorIdentificacion(final String tipoDocumento,
            final String numeroDocumento) throws DaoException {
        try {
            return buscarPorConsultaNombrada(TerceroNominaDetalle.BUSCAR_POR_IDENTIFICACION, tipoDocumento,
                    numeroDocumento);
        } catch (DaoException ex) {
            Logger.getLogger(TerceroNominaDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Tercero que esten vigentes true.
     *
     * @return Listado Tercero
     * @throws DaoException En caso de error
     */
    public List<TerceroNominaDetalle> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TerceroNominaDetalle.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(TerceroNominaDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param idTercero
     * @param idNominaDetalle
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<TerceroNominaDetalle> buscarPorTerceroYNominaDet(final Long idTercero, final Long idNominaDetalle)
            throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(TerceroNominaDetalle.BUSCAR_POR_TERCERO_Y_NOMINA_DET, idTercero,
                    idNominaDetalle);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param idTercero
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public List<TerceroNominaDetalle> buscarPorTercero(final Long idTercero) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(TerceroNominaDetalle.BUSCAR_POR_TERCERO, idTercero);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarPorNomina(final Long nominaId) throws DaoException {
        try {
            ejecutarPorConsultaNombrada(TerceroNominaDetalle.ELIMINAR_POR_NOMINA, nominaId);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
    }
}
