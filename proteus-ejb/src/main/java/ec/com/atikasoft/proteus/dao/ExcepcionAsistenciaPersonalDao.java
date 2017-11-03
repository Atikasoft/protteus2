/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ExcepcionAsistenciaPersonal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class ExcepcionAsistenciaPersonalDao extends GenericDAO<ExcepcionAsistenciaPersonal, Long> {

    public ExcepcionAsistenciaPersonalDao() {
        super(ExcepcionAsistenciaPersonal.class);
    }

    /**
     * Recupera todos los registros vigentes
     * @return
     * @throws DaoException 
     */
    public List<ExcepcionAsistenciaPersonal> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ExcepcionAsistenciaPersonal.BUSCAR_TODOS_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ExcepcionAsistenciaPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Recupera todos los registros vigentes dado el ide del servidor
     * @param servidorId 
     * @return List<ExcepcionAsistenciaPersonal>
     * @throws DaoException 
     */
    public List<ExcepcionAsistenciaPersonal> buscarVigentesPorServidorId(final Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ExcepcionAsistenciaPersonal.BUSCAR_VIGENTES_POR_SERVIDOR_ID, servidorId);
        } catch (DaoException ex) {
            Logger.getLogger(ExcepcionAsistenciaPersonalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}