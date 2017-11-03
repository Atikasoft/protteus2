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
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuestoRegimenLaboral;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class EstadoAdministracionPuestoRegimenLaboralDao 
        extends GenericDAO<EstadoAdministracionPuestoRegimenLaboral, Long> {

    /**
     * Constructor sin argumentos.
     */
    public EstadoAdministracionPuestoRegimenLaboralDao() {
        super(EstadoAdministracionPuestoRegimenLaboral.class);
    }

    /**
     * Metodo que se encarga de buscar los Estados Administracion Puesto - Regimen Laboral vigentes
     *
     * @return Lista de EstadoAdministracionPuestoRegimenLaboral
     * @throws DaoException En caso de error
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(EstadoAdministracionPuestoRegimenLaboral.BUSCAR_TODOS_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los Estados Administracion Puesto - Regimen Laboral por el id del Estado
     * Administracion Puesto.
     *
     * @param estadoAdministracionPuestoId Id del Estado Administracion Puesto
     * @return Lista de EstadoAdministracionPuestoRegimenLaboral
     * @throws DaoException En caso de error
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarPorEstadoAdministracionPuestoId(
            final Long estadoAdministracionPuestoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_ESTADO_ADMINISTRACION_PUESTO_ID, 
                    estadoAdministracionPuestoId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar los Estados Administracion Puesto - Regimen Laboral por el id del regimen
     * laboral.
     *
     * @param regimenLaboralId Id del regimen laboral
     * @return Lista de EstadoAdministracionPuestoRegimenLaboral
     * @throws DaoException En caso de error
     */
    public List<EstadoAdministracionPuestoRegimenLaboral> buscarPorRegimenLaboralId(final Long regimenLaboralId)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_REGIMEN_LABORAL_ID, regimenLaboralId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que busca los Estado Administracion Puesto - Regimen Laboral por regimen Laboral id y Estado
     * Administracion Puesto id.
     *
     * @param estadoAdministracionPuestoId Id del Estado Administracion Puesto
     * @param regimenLaboralId Id del regimenLaboral
     * @return List<> lista de EstadoAdministracionPuestoRegimenLaboral
     * @throws DaoException en caso de error
     */
    public EstadoAdministracionPuestoRegimenLaboral buscarPorEstadoAdministracionPuestoIdYRegimenLaboralId(
            final Long estadoAdministracionPuestoId, final Long regimenLaboralId) throws DaoException {
        EstadoAdministracionPuestoRegimenLaboral estadoAdministracionPuestoRegimenLaboral = null;
        List<EstadoAdministracionPuestoRegimenLaboral> estadoAdministracionPuestoRegimenLaborales = buscarPorConsultaNombrada(
                EstadoAdministracionPuestoRegimenLaboral.BUSCAR_POR_ESTADO_ADMINISTRACION_ID_Y_REGIMEN_LABORAL_ID,
                estadoAdministracionPuestoId, regimenLaboralId);
        if (estadoAdministracionPuestoRegimenLaborales != null && !estadoAdministracionPuestoRegimenLaborales.isEmpty()) {
            estadoAdministracionPuestoRegimenLaboral = estadoAdministracionPuestoRegimenLaborales.get(0);
        }
        return estadoAdministracionPuestoRegimenLaboral;
    }
}
