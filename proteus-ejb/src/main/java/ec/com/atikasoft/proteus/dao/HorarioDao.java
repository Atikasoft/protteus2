/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Horario;
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
public class HorarioDao extends GenericDAO<Horario, Long> {

    public HorarioDao() {
        super(Horario.class);
    }

    public List<Horario> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Horario.BUSCAR_TODOS_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda por nombre.
     *
     * @param nombre
     * @return List
     * @throws DaoException
     */
    public List<Horario> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Horario.BUSCAR_POR_NOMBRE, "%" + nombre + "%");
        } catch (DaoException ex) {
            Logger.getLogger(HorarioDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
