/*
 *  TipoNominaEstadoPersonalDao.java
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
 *  07/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TipoNominaEstadoPersonal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * TipoNominaEstadoPersonalDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoNominaEstadoPersonalDao extends GenericDAO<TipoNominaEstadoPersonal, Long> {

    /**
     * Constructor por defecto.
     */
    public TipoNominaEstadoPersonalDao() {
        super(TipoNominaEstadoPersonal.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de TipoNominaEstadoPersonal que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<TipoNominaEstadoPersonal> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(TipoNominaEstadoPersonal.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(TipoNominaEstadoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
        /**
     * Metodo que se encarga de buscar un listado de registros que esten vigentes true
     * y que pertenezcan a una Nomina.
     *
     * @return Listado registros vigentes
     * @throws DaoException En caso de error
     */
    public List<TipoNominaEstadoPersonal> buscarVigentePorEtadoPersonal(final Long id) throws DaoException {
        try {
            System.out.println("entra al metodo para nuscar personal****************");
            return buscarPorConsultaNombrada(TipoNominaEstadoPersonal.BUSCAR_POR_ESTADO_PERSONAL, id);
        } catch (DaoException ex) {
            Logger.getLogger(TipoNominaEstadoPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
