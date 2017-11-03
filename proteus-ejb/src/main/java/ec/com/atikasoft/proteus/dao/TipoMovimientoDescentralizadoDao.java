/*
 *  TipoMovimientoDao.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  22/10/2012
 *  Actualización: 13/11/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDescentralizado;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class TipoMovimientoDescentralizadoDao extends GenericDAO<TipoMovimientoDescentralizado, Long> {

    /**
     * Constructor sin argumentos.
     */
    public TipoMovimientoDescentralizadoDao() {
        super(TipoMovimientoDescentralizado.class);
    }

    /**
     * Este metodo busca todos los TipoMovimientoDescentralizado activos.
     *
     * @param tipoMovimientoId Long
     * @param institucionId Long institucion logueada
     * @return List
     * @throws DaoException captura de errores.
     */
    public List<TipoMovimientoDescentralizado> buscarActivos(final Long tipoMovimientoId, final Long institucionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    TipoMovimientoDescentralizado.BUSCAR_TODOS_ACTIVOS_TIPO_MOV, tipoMovimientoId, institucionId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    /**
     * Este metodo busca todos los TipoMovimientoDescentralizado activos segun la Unidad Organizacional.
     * @param idUnidad 
     * @return List
     * @throws DaoException 
     */
    public List<TipoMovimientoDescentralizado> buscarPorUnidadOrganizacional( final Long idUnidad) throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    TipoMovimientoDescentralizado.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, idUnidad);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo guarda una lista de Unidades Organizacionales para descentralizarse.
     *
     * @param instituciones final
     * @throws DaoException captura de errores.
     */
    public void guardarTiposMovimientosDescentralizados(
            final List<TipoMovimientoDescentralizado> instituciones) throws DaoException {
        try {
            for (TipoMovimientoDescentralizado ins : instituciones) {
                crear(ins);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Verifica si el tipo de movimiento es descentralizado para la institucion.
     *
     * @param tipoMovimientoId
     * @param idUnidad Unidad Organizacional
     * @return
     * @throws DaoException
     */
    public Boolean esDescentralizado(final Long tipoMovimientoId, final Long idUnidad) throws DaoException {
        try {
            Boolean esDescentralizado = Boolean.FALSE;
            List<TipoMovimientoDescentralizado> lista = buscarPorConsultaNombrada(
                    TipoMovimientoDescentralizado.BUSCAR_POR_TIPO_MOVIMIENTO_Y_UNIDAD_ORGANIZACIONAL, tipoMovimientoId,
                    idUnidad);
            if (!lista.isEmpty()) {
                esDescentralizado = Boolean.TRUE;
            }
            return esDescentralizado;
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
}