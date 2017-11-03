/*
 *  DocumentoHabilitanteDao.java
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
 *  01/11/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DocumentoHabilitante;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class DocumentoHabilitanteDao extends GenericDAO<DocumentoHabilitante, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DocumentoHabilitanteDao.class.getCanonicalName());

    /**
     * Constructor.
     */
    public DocumentoHabilitanteDao() {
        super(DocumentoHabilitante.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<DocumentoHabilitante> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DocumentoHabilitante.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(DocumentoHabilitanteDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param nemonico String
     * @return List
     * @throws DaoException DaoException
     */
    public List<DocumentoHabilitante> buscarTodosPorNemonico(final String nemonico) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DocumentoHabilitante.BUSCAR_POR_NEMONICO, nemonico);
        } catch (DaoException ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de listar los documentos habilitantes vigentes.
     *
     * @return Lista de documento habilitante
     * @throws DaoException en caso de error
     */
    public List<DocumentoHabilitante> buscarActivos() throws DaoException {
        try {
            return buscarPorConsultaNombrada(DocumentoHabilitante.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AlertaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
