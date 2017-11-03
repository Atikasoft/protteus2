/*
 *  FormulaDao.java
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
 *  09/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class FormulaDao extends GenericDAO<Formula, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(FormulaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public FormulaDao() {
        super(Formula.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<Formula> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Formula.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(FormulaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<Formula> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Formula.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(FormulaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Formula que esten vigentes true.
     *
     * @return Listado Formula
     * @throws DaoException En caso de error
     */
    public List<Formula> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Formula.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(FormulaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Este metodo procesa la busqueda de todo por codigo en la formula.
     *
     * @param codigo String codigo de formula a buscar
     * @return List de formulas que contienen en campo formula el codigo buscado
     * @throws DaoException excepcion en dao
     */
    public List<Formula> buscarTodosPorCodigoEnFormula(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Formula.BUSCAR_EN_FORMULA, UtilCadena.concatenar("%", codigo,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(FormulaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}