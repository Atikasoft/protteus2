
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
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.ReclutamientoInstruccion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class ReclutamientoInstruccionDao extends GenericDAO<ReclutamientoInstruccion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ReclutamientoInstruccionDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public ReclutamientoInstruccionDao() {
        super(ReclutamientoInstruccion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ReclutamientoInstruccion que esten
     * vigentes true.
     *
     * @return Listado ReclutamientoInstruccion
     * @throws DaoException En caso de error
     */
    public List<ReclutamientoInstruccion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ReclutamientoInstruccion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ReclutamientoInstruccionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     public List<ReclutamientoInstruccion> buscarPorReclutamientoId(final Long reclutamientoId
            ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ReclutamientoInstruccion.BUSCAR_POR_RECLUTAMIENTO_ID,
                    reclutamientoId);
        } catch (DaoException ex) {
            Logger.getLogger(ReclutamientoInstruccionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
