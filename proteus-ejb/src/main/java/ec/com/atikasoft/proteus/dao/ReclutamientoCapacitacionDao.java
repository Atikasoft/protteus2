
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
import ec.com.atikasoft.proteus.modelo.ReclutamientoCapacitacion;
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
public class ReclutamientoCapacitacionDao extends GenericDAO<ReclutamientoCapacitacion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ReclutamientoCapacitacionDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public ReclutamientoCapacitacionDao() {
        super(ReclutamientoCapacitacion.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de ReclutamientoCapacitacion
     * que esten vigentes true.
     *
     * @return Listado ReclutamientoCapacitacion
     * @throws DaoException En caso de error
     */
    public List<ReclutamientoCapacitacion> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ReclutamientoCapacitacion.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ReclutamientoCapacitacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * metodo que busca por el id del reclutamiento que es el padre.
     *
     * @param reclutamientoId
     * @return
     * @throws DaoException
     */
    public List<ReclutamientoCapacitacion> buscarPorReclutamientoId(final Long reclutamientoId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ReclutamientoCapacitacion.BUSCAR_POR_RECLUTAMIENTO_ID,
                    reclutamientoId);
        } catch (DaoException ex) {
            Logger.getLogger(ReclutamientoCapacitacionDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
