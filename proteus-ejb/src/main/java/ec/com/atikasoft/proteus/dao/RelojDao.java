/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Reloj;
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
public class RelojDao extends GenericDAO<Reloj, Long> {

    public RelojDao() {
        super(Reloj.class);
    }

    /**
     * Recupera todos los registros vigentes
     * @return
     * @throws DaoException 
     */
    public List<Reloj> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Reloj.BUSCAR_TODOS_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RelojDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda por codigo.
     *
     * @param codigo
     * @return List
     * @throws DaoException
     */
    public Reloj buscarVigentePorCodigo(final String codigo) throws DaoException {
        try {
            List<Reloj> relojes = buscarPorConsultaNombrada(Reloj.BUSCAR_VIGENTES_POR_CODIGO, codigo);
            if (!relojes.isEmpty()) {
                return relojes.get(0);
            }
        } catch (DaoException ex) {
            Logger.getLogger(RelojDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
        return null;
    }

}