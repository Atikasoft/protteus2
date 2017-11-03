/*
 *  RubroTipoNominaDao.java
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
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.RubroTipoNomina;
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
public class RubroTipoNominaDao extends GenericDAO<RubroTipoNomina, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RubroTipoNominaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public RubroTipoNominaDao() {
        super(RubroTipoNomina.class);
    }


    /**
     * Metodo que se encarga de buscar un listado de RubroTipoNomina que esten vigentes true.
     *
     * @return Listado RubroTipoNomina
     * @throws DaoException En caso de error
     */
    public List<RubroTipoNomina> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(RubroTipoNomina.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RubroTipoNominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     /**
     * Metodo que se encarga de buscar un listado de RubroTipoNomina que esten vigentes true 
     * por Rubro
     *@param rubro id del rubro
     * @return Listado RubroTipoNomina
     * @throws DaoException En caso de error
     */
    public List<RubroTipoNomina> buscarVigentesPorRubro(final Long rubro) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RubroTipoNomina.BUSCAR_POR_RUBRO, rubro);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar un listado de RubroTipoNomina que esten vigentes true 
     * por Rubro
     * @param tipoNomina id del tipo de NOmina
     * @return Listado RubroTipoNomina
     * @throws DaoException En caso de error
     */
    public List<RubroTipoNomina> buscarVigentesPorTipoNomina(final Long tipoNomina) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RubroTipoNomina.BUSCAR_POR_TIPO_NOMINA, tipoNomina);
        } catch (DaoException ex) {
            Logger.getLogger(CampoAccesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}