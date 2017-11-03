/*
 *  TramiteDao.java
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
 *  30/10/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class LicenciaHorarioDao extends GenericDAO<LicenciaHorario, Long> {

    /**
     * Constructor.
     */
    public LicenciaHorarioDao() {
        super(LicenciaHorario.class);
    }

    /**
     * Este metodo busca objetos segun el id de Licencia.
     *
     * @param licenciaId Long
     * @return List
     * @throws DaoException Captura de errores
     */
    public List<LicenciaHorario> buscarPorLicencia(final Long licenciaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(LicenciaHorario.BUSCAR_POR_LICENCIA, licenciaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo actualiza el valor de la licencia a false segun el id de la licencia.
     *
     * @param licenciaId Long
     * @throws DaoException Captura de errores
     */
    public void quitarVigenciaSegunLicencia(final Long licenciaId, final String usuario) throws DaoException {
        try {
            Query consulta = getEntityManager().createNamedQuery(LicenciaHorario.ELIMINAR_POR_LICENCIA);
            Date fecha = new Date();
            consulta.setParameter(1, fecha);
            consulta.setParameter(2, usuario);
            consulta.setParameter(3, licenciaId);
            System.out.println("\n\n\n\ntotal actualizados: " + consulta.executeUpdate());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo crea horarios nuevos quitandole la vigencia a los anteriores.
     *
     * @param licencias List
     * @param licencia Licencia
     * @throws DaoException Captura de errores
     */
    public void crear(final List<LicenciaHorario> licencias, final Licencia licencia) throws DaoException {
        try {
            if (licencias != null && !licencias.isEmpty()) {
                quitarVigenciaSegunLicencia(licencia.getId(), licencias.get(0).getUsuarioCreacion());
                for (LicenciaHorario l : licencias) {
                    l.setLicencia(licencia);
                    crear(l);
                }
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
