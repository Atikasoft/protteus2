/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * @author Leydis Garzón
 */
@LocalBean
@Stateless
public class HorarioDesconcentradoDao extends GenericDAO<HorarioDesconcentrado, Long> {

    public HorarioDesconcentradoDao() {
        super(HorarioDesconcentrado.class);
    }

    /**
     * Recuepera todos los registros vigentes
     * @return
     * @throws DaoException 
     */
    public List<HorarioDesconcentrado> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDesconcentrado.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDesconcentradoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera los registros vigentes de acuerdo al id de la unidad desconcentrada
     * @param desconcentradoId
     * @return
     * @throws DaoException 
     */
    public List<HorarioDesconcentrado> buscarVigentesPorDesconcentradoId(final Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDesconcentrado.BUSCAR_VIGENTES_POR_DESCONCENTRADO_ID, desconcentradoId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDesconcentradoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera todos los registros, vigentes y no vigentes, de acuerdo al id de la unidad desconcentrada
     * @param desconcentradoId
     * @return
     * @throws DaoException 
     */
    public List<HorarioDesconcentrado> buscarTodosPorDesconcentradoId(final Long desconcentradoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDesconcentrado.BUSCAR_TODOS_POR_DESCONCENTRADO_ID, desconcentradoId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDesconcentradoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera los registros de acuerdo al id del horario asigando
     * @param horarioId
     * @return
     * @throws DaoException 
     */
    public List<HorarioDesconcentrado> buscarVigentesPorHorarioId(final Long horarioId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDesconcentrado.BUSCAR_VIGENTES_POR_HORARIO_ID, horarioId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDesconcentradoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
