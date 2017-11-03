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
import ec.com.atikasoft.proteus.modelo.Validacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class ValidacionDao extends GenericDAO<Validacion, Long> {

    /**
     * Constructor.
     */
    public ValidacionDao() {
        super(Validacion.class);
    }

    /**
     * Metodo que se encarga de buscar las validaciones por el id del tipo de movimiento.
     *
     * @param tipoMovimientoId Id del tipo de movimiento
     * @return Lista de Validaciones
     * @throws DaoException En caso de error
     */
    public List<Validacion> buscarPorTipoMovimientoId(final Long tipoMovimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Validacion.BUSCAR_POR_TIPO_MOVIMIENTO_ID, tipoMovimientoId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar las validaciones por el id del movimiento.
     *
     * @param movimientoId Id del de movimiento
     * @return Lista de Validaciones
     * @throws DaoException En caso de error
     */
    public List<Validacion> buscarPorMovimientoId(final Long movimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Validacion.BUSCAR_POR_MOVIMIENTO_ID, movimientoId);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera las validaciones que no se ha cumplido.
     *
     * @param movimientoId Identificador unico del movimiento.
     * @return lista de validaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<Validacion> buscarRequisitosIncumplidosPorMovimiento(final Long movimientoId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Validacion.BUSCAR_REQUISITOS_INCUMPLIDOS_POR_MOVIMIENTO, movimientoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Recupera las validaciones que se han cumplido pero no se ingreso el archivo.
     *
     * @param movimientoId Identificador unico del movimiento.
     * @return lista de validaciones
     * @throws DaoException error en el acceso a datos
     */
    public List<Validacion> buscarRequisitosCumplidosSinArchivoPorMovimiento(final Long movimientoId) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(Validacion.BUSCAR_REQUISITOS_CUMPLIDOS_SIN_ARCHIVOS_POR_MOVIMIENTO,
                    movimientoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar las validaciones por tipo de movimiento requisito id y por movimiento id.
     *
     * @param tipoMovimientoRequisitoId Id del tipo de movimiento requisito
     * @param movimientoId Id del movimiento
     * @return List<Validacion> lista de validacion
     * @throws DaoException en caso de error
     */
    public Validacion buscarPorTipoMovimientoRequisitoIdYMovimientoId(
            final Long tipoMovimientoRequisitoId, final Long movimientoId) throws DaoException {
        Validacion validacion = null;
        List<Validacion> validaciones = buscarPorConsultaNombrada(
                Validacion.BUSCAR_POR_TIPO_MOVIMIENTO_REQUISITO_ID_Y_MOVIMIENTO_ID,
                tipoMovimientoRequisitoId, movimientoId);
        if (validaciones != null && !validaciones.isEmpty()) {
            validacion = validaciones.get(0);
        }
        return validacion;
    }
}
