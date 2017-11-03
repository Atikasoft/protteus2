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
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.Date;
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
public class EjercicioFiscalDao extends GenericDAO<EjercicioFiscal, Long> {

    /**
     * Constructor sin argumentos.
     */
    public EjercicioFiscalDao() {
        super(EjercicioFiscal.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EjercicioFiscal> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EjercicioFiscal.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre, "%"));
        } catch (Exception ex) {
            Logger.getLogger(EjercicioFiscalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo busca el ejercicio fiscal activo.
     *
     * @return EjercicioFiscal
     * @throws DaoException DaoExceptiond
     */
    public EjercicioFiscal buscarActivo() throws DaoException {
        try {
            EjercicioFiscal ef = null;
            List<EjercicioFiscal> buscarPorConsultaNombrada = buscarPorConsultaNombrada(EjercicioFiscal.BUSCAR_ACTIVO);
            if (buscarPorConsultaNombrada != null && !buscarPorConsultaNombrada.isEmpty()) {
                ef = buscarPorConsultaNombrada.get(0);
            }
            return ef;
        } catch (Exception ex) {
            Logger.getLogger(EjercicioFiscalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Busca los ejercicios fiscales que son proximos a los ejercicios actuales
     *
     * @return
     * @throws DaoException
     */
    public List<EjercicioFiscal> buscarEjerciciosFiscalesSonProximosEjercicios() throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    EjercicioFiscal.BUSCAR_EJERCICIOS_SON_PROXIMOS_EJERCICIOS);
        } catch (Exception ex) {
            Logger.getLogger(EjercicioFiscalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
