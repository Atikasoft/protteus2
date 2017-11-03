/*
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
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


@LocalBean
@Stateless
public class CertificacionPresupuestariaDao extends GenericDAO<CertificacionPresupuestaria, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CertificacionPresupuestariaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public CertificacionPresupuestariaDao() {
        super(CertificacionPresupuestaria.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de CertificacionPresupuestaria que esten vigentes
     * true.
     *
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CertificacionPresupuestaria.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo busca el listado de CertificacionPresupuestaria que esten vigentes
     * de la Unidad Presupuestaria especificada.
     *
     * @param idUnidadPresupuestaria Long id de la Unidad Presupuestaria
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarPorUnidadPresupuestaria(final Long idUnidadPresupuestaria) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CertificacionPresupuestaria.BUSCAR_POR_UNIDAD_PRESUPUESTARIA, idUnidadPresupuestaria);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo busca las CertificacionPresupuestaria vigentes
     * de la Unidad Presupuestaria y Modalidad Laboral especificadas.
     *
     * @param idUnidadPresupuestaria Long id de la Unidad Presupuestaria
     * @param idModalidadLaboral Long id de la Modalidad Laboral
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarPorUnidadPresupuestariaYModalidad(final Long idUnidadPresupuestaria,
            final Long idModalidadLaboral) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CertificacionPresupuestaria.BUSCAR_POR_UNIDAD_PRESUPUESTARIA_Y_MODALIDAD,
                    idUnidadPresupuestaria,idModalidadLaboral);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo busca las CertificacionPresupuestaria vigentes
     * de la Modalidad Laboral especificada.
     *
     * @param idModalidadLaboral Long id de la Modalidad Laboral
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarPorModalidad(final Long idModalidadLaboral) throws DaoException {
        try {
            return buscarPorConsultaNombrada(CertificacionPresupuestaria.BUSCAR_POR_MODALIDAD_LABORAL,idModalidadLaboral);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Busca las certificaciones presupuestarias vigentes
     * con el valor de certificacion especificado,
     * En caso de que el campo certificacionId no sea nulo se excluye de la búsqueda la certificación con ese id.
     *
     * @param certificacion valor de certificación que se desea buscar
     * @param certificacionId id de la certificación presupuestaria (puede ser nulo)
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarPorCertificacion(final String certificacion, final Long certificacionId) throws DaoException {
        try {
            if(certificacionId != null)
                return buscarPorConsultaNombrada(
                        CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID,
                        certificacion.toUpperCase(),certificacionId);
            else
                return buscarPorConsultaNombrada(CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA,
                        certificacion);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Busca las certificaciones presupuestarias vigentes
     * con el valor de certificacion especificado para la unidad presupuestaria indicada,
     * En caso de que el campo certificacionId no sea nulo se excluye de la búsqueda la certificación con ese id.
     *
     * @param certificacion valor de certificación que se desea buscar
     * @param certificacionId id de la certificación presupuestaria (puede ser nulo)
     * @param unidadPresupuestariaId id de la unidad presupuestaria
     * @return Listado CertificacionPresupuestaria
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestaria> buscarPorCertificacionYUnidadPresupuestaria(final String certificacion, 
            final Long certificacionId, final Long unidadPresupuestariaId) throws DaoException {
        try {
            if(certificacionId != null)
                return buscarPorConsultaNombrada(
                        CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID_Y_UNIDAD_PRESUPUESTARIA,
                        certificacion.toUpperCase(),certificacionId, unidadPresupuestariaId);
            else
                return buscarPorConsultaNombrada(
                        CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_Y_UNIDAD_PRESUPUESTARIA,
                        certificacion, unidadPresupuestariaId);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    
}
