/*
 *  VacacionProcesoDao.java
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
 *  27/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.VacacionProceso;
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
public class VacacionProcesoDao extends GenericDAO<VacacionProceso, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionProcesoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public VacacionProcesoDao() {
        super(VacacionProceso.class);
    }
    /**
     * Metodo que se encarga de buscar un listado de VacacionProceso que esten vigentes true.
     *
     * @return Listado VacacionProceso
     * @throws DaoException En caso de error
     */
    public List<VacacionProceso> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionProceso.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionProcesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar el ultimo registro procesado
     * @return Listado VacacionProceso
     * @throws DaoException En caso de error
     */
    public List<VacacionProceso> buscarUltimaFechaProcesada() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionProceso.BUSCAR_ULTIMA_FECHA_PROCESADA);           
        } catch (DaoException ex) {
            Logger.getLogger(VacacionProcesoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}