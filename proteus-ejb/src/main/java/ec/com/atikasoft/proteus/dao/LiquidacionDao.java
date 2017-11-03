/*
 *  LiquidacionDao.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
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
public class LiquidacionDao extends GenericDAO<Liquidacion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(LiquidacionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public LiquidacionDao() {
        super(Liquidacion.class);
    }
    /**
     * Metodo que se encarga de buscar un listado de Liquidacion que esten vigentes true.
     *
     * @return Listado Liquidacion
     * @throws DaoException En caso de error
     */
    public List<Liquidacion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Liquidacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(LiquidacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
        /**
     * Metodo que se encarga de buscar un listado de Liquidacion que esten vigentes true de un servidor específico.
     *@param  idServidor Long id del servidor
     * @return Listado Liquidacion
     * @throws DaoException En caso de error
     */
    public List<Liquidacion> buscarPorServidor(final Long idServidor) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Liquidacion.BUSCAR_POR_SERVIDOR, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(LiquidacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
       /**
     * Metodo que se encarga de buscar un listado de Liquidacion que esten vigentes true de un estado específico.
     *@param  estado Long id del servidor
     * @return Listado Liquidacion
     * @throws DaoException En caso de error
     */
    public List<Liquidacion> buscarPorEstado(final String estado) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Liquidacion.BUSCAR_POR_ESTADO, estado);
        } catch (DaoException ex) {
            Logger.getLogger(LiquidacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
        /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public Integer quitarNomina(final Long nominaId) throws DaoException {
        return ejecutarPorConsultaNombrada(Liquidacion.QUITAR_NOMINA, nominaId);
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public Integer quitarNominaServidor(final Long nominaId, final Long servidorId) throws DaoException {
        return ejecutarPorConsultaNombrada(Liquidacion.QUITAR_NOMINA_SERVIDOR, nominaId, servidorId);
    }

     
}