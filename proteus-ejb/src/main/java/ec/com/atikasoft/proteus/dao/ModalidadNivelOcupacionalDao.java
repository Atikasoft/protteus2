/*
 *  ModalidadNivelOcupacionalDao.java
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
 *  27/09/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Alerta;
import ec.com.atikasoft.proteus.modelo.ModalidadNivelOcupacional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez  <liliana.rodriguez@markasof.ec>
 */
@LocalBean
@Stateless
public class ModalidadNivelOcupacionalDao extends GenericDAO<ModalidadNivelOcupacional, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ModalidadNivelOcupacionalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ModalidadNivelOcupacionalDao() {
        super(ModalidadNivelOcupacional.class);
    }

   
    /**
     * Metodo que se encarga de buscar un listado de registros que esten vigentes true.
     *
     * @return Listado registros vigentes
     * @throws DaoException En caso de error
     */
    public List<ModalidadNivelOcupacional> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ModalidadNivelOcupacional.BUSCAR_POR_MODALIDAD_LABORAL);
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadNivelOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
      /**
     * Metodo que se encarga de buscar un listado de registros que esten vigentes true
     * y que pertenezcan a una Modalidad.
     *
     * @return Listado registros vigentes
     * @throws DaoException En caso de error
     */
    public List<ModalidadNivelOcupacional> buscarVigentePorModalidad(final Long id) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ModalidadNivelOcupacional.BUSCAR_POR_MODALIDAD_LABORAL, id);
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadNivelOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}