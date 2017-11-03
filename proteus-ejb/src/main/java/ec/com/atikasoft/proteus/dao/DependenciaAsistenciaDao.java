/*
 *  ClaseDao.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DependenciaAsistencia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class DependenciaAsistenciaDao extends GenericDAO<DependenciaAsistencia, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DependenciaAsistenciaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public DependenciaAsistenciaDao() {
        super(DependenciaAsistencia.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nemonico.
     *
     * @param codigo
     * @return List
     * @throws DaoException DaoException
     */
    public List<DependenciaAsistencia> buscarPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DependenciaAsistencia.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(DependenciaAsistenciaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
