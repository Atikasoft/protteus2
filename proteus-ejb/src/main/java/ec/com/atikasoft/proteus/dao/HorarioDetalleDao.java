/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
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
public class HorarioDetalleDao extends GenericDAO<HorarioDetalle, Long> {

    public HorarioDetalleDao() {
        super(HorarioDetalle.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre del horario.
     *
     * @param nombre
     * @return List
     * @throws DaoException
     */
    public List<HorarioDetalle> buscarTodosPorNombreHorario(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDetalle.BUSCAR_POR_NOMBRE_HORARIO, "%" + nombre + "%");
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo por id del horario.
     *
     * @param horarioId 
     * @return List
     * @throws DaoException
     */
    public List<HorarioDetalle> buscarTodosPorHorarioId(final Long horarioId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(HorarioDetalle.BUSCAR_POR_HORARIO_ID, horarioId);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDetalleDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
