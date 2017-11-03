/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.asistencia.ProcesoRegistroMigracionMarcacion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * DAO de ProcesoRegistroMigracionMarcacion
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@LocalBean
@Stateless
public class ProcesoRegistroMigracionMarcacionDao extends GenericDAO<ProcesoRegistroMigracionMarcacion, Long> {

    /**
     * Constructor por defecto.
     */
    public ProcesoRegistroMigracionMarcacionDao() {
        super(ProcesoRegistroMigracionMarcacion.class);
    }

    /**
     *
     * @return @throws DaoException
     */
    public ProcesoRegistroMigracionMarcacion ultimaEjecucion() throws DaoException {
        List<ProcesoRegistroMigracionMarcacion> l
                = buscarPorConsultaNombrada(ProcesoRegistroMigracionMarcacion.MIGRACIONES_ORDENADAS);
        if (l != null && !l.isEmpty()) {
            return l.get(0);
        }
        return null;
    }

    /**
     *
     * @return @throws DaoException
     */
    public boolean ejecucionEnCurso() throws DaoException {
        return false;
    }

    /**
     *
     * @return @throws DaoException
     */
    public List<ProcesoRegistroMigracionMarcacion> ejecucionesPendientes() throws DaoException {
        return null;
    }
}
