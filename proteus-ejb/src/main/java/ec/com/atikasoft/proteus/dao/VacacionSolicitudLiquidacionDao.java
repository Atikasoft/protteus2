/**
 * VacacionSolicitudDao.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * AtikaSoft ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with AtikaSoft.
 *
 *
 * AtikaSoft Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 29/10/2013
 *
 * Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudLiquidacion;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@LocalBean
@Stateless
public class VacacionSolicitudLiquidacionDao extends GenericDAO<VacacionSolicitudLiquidacion, Long> {

    /**
     * Constructor sin argumentos.
     */
    public VacacionSolicitudLiquidacionDao() {
        super(VacacionSolicitudLiquidacion.class);
    }

    /**
     *
     * @return @throws DaoException
     */
    public List<VacacionSolicitudLiquidacion> buscarTodosVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitudLiquidacion.BUSCAR_TODOS_VIGENTES);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * BUSCAR LAS LIQUIDACIONES ASOCIADAS A UN SERVIDOR INSTITUCION
     *
     * @param servidorInstitucionId
     * @return
     * @throws DaoException
     */
    public List<VacacionSolicitudLiquidacion> buscarPorServidorInstitucion(
            Long servidorInstitucionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_INSTITUCION, servidorInstitucionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * BUSCAR LAS LIQUIDACIONES ASOCIADAS A UN SERVIDOR INSTITUCION Y ESTADO DE LA SOLICITUD
     *
     * @param servidorInstitucionId
     * @param estado 
     * @return
     * @throws DaoException
     */
    public List<VacacionSolicitudLiquidacion> buscarPorServidorInstitucionYEstado(
            Long servidorInstitucionId, String estado) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_INSTITUCION_Y_ESTADO, servidorInstitucionId, estado);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * BUSCAR LAS LIQUIDACIONES ASOCIADAS A UN SERVIDOR
     *
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<VacacionSolicitudLiquidacion> buscarPorServidorId(Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_ID, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
