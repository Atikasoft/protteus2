/*
 *  MetadataColumnaDao.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author liliana.rodriguez@markasoft.ec
 */
@LocalBean
@Stateless
public class MetadataColumnaDao extends GenericDAO<MetadataColumna, Long> {

    /**
     * Constructor sin Argumentos.
     */
    public MetadataColumnaDao() {
        super(MetadataColumna.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre
     * @return List
     * @throws DaoException
     */
    public List<MetadataColumna> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(MetadataColumna.BUSCAR_POR_NOMBRE, "%" + nombre + "%");
        } catch (DaoException ex) {
            Logger.getLogger(MetadataColumnaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los metadataColumnas vigentes.
     *
     * @return Lista de metadataColumnas
     * @throws DaoException En caso de error
     */
    public List<MetadataColumna> buscarTodosVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(MetadataColumna.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(MetadataColumnaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los metadataColumnas por Nemónico.
     *
     * @param nemonico String
     * @return
     * @throws DaoException
     */
    public List<MetadataColumna> buscarTodosPorMetadataTabla(final Long idPadre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(MetadataColumna.BUSCAR_POR_METADATA_TABLA, idPadre);
        } catch (Exception ex) {
            Logger.getLogger(MetadataColumnaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
