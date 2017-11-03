/*
 *  PlanificacionHorarioDao.java
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
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.modelo.PlanificacionHorario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class PlanificacionHorarioDao extends GenericDAO<PlanificacionHorario, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionHorarioDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public PlanificacionHorarioDao() {
        super(PlanificacionHorario.class);
    }
    /**
     * Metodo que se encarga de buscar un listado de PlanificacionHorario que esten vigentes true.
     *
     * @return Listado PlanificacionHorario
     * @throws DaoException En caso de error
     */
    public List<PlanificacionHorario> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionHorario.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionHorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Metodo que se encarga de buscar un listado de PlanificacionHorario que esten vigentes true
     * y la busqueda se hace por servidor, ejercicio fiscal y mes.
     *
     * @param idServidor
     * @param idInstitucionEjercicioFiscal
     * @param mes
     * @return Listado PlanificacionHorario
     * @throws DaoException En caso de error
     */
    public List<PlanificacionHorario> buscarPorServidorEjercicioFiscalYMes(final long idServidor,final long idInstitucionEjercicioFiscal, final int mes) 
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionHorario.BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL_Y_MES, idServidor, idInstitucionEjercicioFiscal, mes);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionHorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Metodo que se encarga de buscar un listado de PlanificacionHorario que esten vigentes true
     * y la busqueda se hace por servidor, y ejercicio fiscal .
     *
     * @return Listado PlanificacionHorario
     * @throws DaoException En caso de error
     */
    public List<PlanificacionHorario> buscarPorServidorEjercicioFiscal(final long idServidor,final long idInstitucionEjercicioFiscal) 
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionHorario.BUSCAR_POR_SERVIDOR_EJERCICIOFISCAL, idServidor, idInstitucionEjercicioFiscal);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionHorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
       /**
     * Metodo que se encarga de buscar un listado de PlanificacionHorario que esten vigentes true
     * y la busqueda se hace por servidor,
     *
     * @param idServidor
     * @param mes
     * @return Listado PlanificacionHorario
     * @throws DaoException En caso de error
     */
    public List<PlanificacionHorario> buscarPorServidorYMes(final long idServidor,final int mes) 
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(PlanificacionHorario.BUSCAR_POR_SERVIDOR_Y_MES, idServidor, mes);
        } catch (DaoException ex) {
            Logger.getLogger(PlanificacionHorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}