/*
 *  EjercicioFiscalDao.java
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
 *  01/11/2012
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@LocalBean
@Stateless
public class InstitucionEjercicioFiscalDao extends GenericDAO<InstitucionEjercicioFiscal, Long> {

    /**
     * Constructor sin argumentos.
     */
    public InstitucionEjercicioFiscalDao() {
        super(InstitucionEjercicioFiscal.class);
    }

    /**
     * Este metodo busca una institucion segun el codigo.
     *
     * @param codigo String
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public InstitucionEjercicioFiscal buscarPorCodigoYEjercicioFiscal(final String codigo, final Long ejercicioFiscalId) throws
            DaoException {
        try {
            InstitucionEjercicioFiscal ins = null;
            List<InstitucionEjercicioFiscal> instituciones = buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_POR_CODIGO_Y_EJERCICIO_FISCAL,
                    codigo, ejercicioFiscalId);
            if (instituciones != null && !instituciones.isEmpty()) {
                ins = instituciones.get(0);
            }
            return ins;
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo busca una institucion segun el codigo del core + ejercicio fiscal.
     *
     * @param codigo String
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public InstitucionEjercicioFiscal buscarPorCodigoCoreYEjercicioFiscal(final Long codigoCore, final Long ejercicioFiscalId) throws
            DaoException {
        try {
            InstitucionEjercicioFiscal ins = null;
            List<InstitucionEjercicioFiscal> instituciones = buscarPorConsultaNombrada(
                    InstitucionEjercicioFiscal.BUSCAR_POR_CODIGO_CORE_Y_EJERCICIO_FISCAL,
                    codigoCore, ejercicioFiscalId);
            if (instituciones != null && !instituciones.isEmpty()) {
                ins = instituciones.get(0);
            }
            return ins;
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }

    /**
     * buscar las intituciones por nombre.
     *
     * @param nombre
     * @return
     * @throws DaoException
     */
    public List<InstitucionEjercicioFiscal> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(RequisitoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);

        }
    }

    /**
     * Metodo para buscar las reglas en estado vigente.
     *
     * @return Lista de reglas
     * @throws DaoException En caso de error
     */
    public List<InstitucionEjercicioFiscal> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_VIGENTES);
        } catch (Exception ex) {
            Logger.getLogger(InstitucionEjercicioFiscalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    
    /**
     * Este metodo busca una institucionEjercicioFiscal segun la institucion.
     * Recupera registros activos e inactivos.
     *
     * @param idInstitucion Long
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public List<InstitucionEjercicioFiscal> buscarTodosPorInstitucion(final Long idInstitucion) throws
            DaoException {
        try {
            
            return buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_TODOS_POR_INSTITUCION, idInstitucion);
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo busca una institucionEjercicioFiscal segun la institucion.
     * Recupera registros activos e inactivos.
     *
     * @param idInstitucion Long
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public List<InstitucionEjercicioFiscal> buscarPorInstitucion(final Long idInstitucion) throws
            DaoException {
        try {
            
            return buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_VIGENTES_POR_INSTITUCION, idInstitucion);
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo busca una institucionEjercicioFiscal segun el ejercicio_fiscal.
     * Recupera registros activos e inactivos.
     *
     * @param nombre String
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public InstitucionEjercicioFiscal buscarPorEjercicioFiscal(Long nombre) throws
            DaoException {
        try {
            
            return buscarPorConsultaNombrada(InstitucionEjercicioFiscal.BUSCAR_POR_EJERCICIO_FISCAL, nombre).get(0);
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo busca una institucionEjercicioFiscal segun la institucion y el ejercicio fiscal.
     * Recupera registros activos e inactivos.
     *
     * @param idInstitucion Long
     * @param idEjercicioFiscal Long
     * @return InstitucionEjercicioFiscal
     * @throws DaoException DaoException
     */
    public InstitucionEjercicioFiscal buscarPorInstitucionYEjecicioFiscal(
            final Long idInstitucion, final Long idEjercicioFiscal) throws DaoException {
        try {
            return (InstitucionEjercicioFiscal) buscarUnicoPorConsultaNombrada(
                    InstitucionEjercicioFiscal.BUSCAR_VIGENTES_POR_INSTITUCION, idInstitucion, idEjercicioFiscal);
        } catch (Exception ex) {
            throw new DaoException(ex);
        }
    }
}
