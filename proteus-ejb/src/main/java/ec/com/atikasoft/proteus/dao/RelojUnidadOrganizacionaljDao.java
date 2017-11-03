/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.RelojUnidadOrganizacional;
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
public class RelojUnidadOrganizacionaljDao extends GenericDAO<RelojUnidadOrganizacional, Long> {

    public RelojUnidadOrganizacionaljDao() {
        super(RelojUnidadOrganizacional.class);
    }

    /**
     * Recupera todos los registros vigentes
     * @return
     * @throws DaoException 
     */
    public List<RelojUnidadOrganizacional> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(RelojUnidadOrganizacional.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RelojUnidadOrganizacionaljDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda por id del reloj.
     *
     * @param relojId 
     * @return List
     * @throws DaoException
     */
    public List<RelojUnidadOrganizacional> buscarVigentesPorRelojId(final Long relojId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RelojUnidadOrganizacional.BUSCAR_POR_RELOJ_ID, relojId);
        } catch (DaoException ex) {
            Logger.getLogger(RelojUnidadOrganizacionaljDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda por id de la unidad organizacional.
     *
     * @param unidadOrganizacionalId 
     * @return List
     * @throws DaoException
     */
    public List<RelojUnidadOrganizacional> buscarVigentesPorUnidadOrganizacionalId(
            final Long unidadOrganizacionalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(RelojUnidadOrganizacional.BUSCAR_POR_RELOJ_ID, unidadOrganizacionalId);
        } catch (DaoException ex) {
            Logger.getLogger(RelojUnidadOrganizacionaljDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}