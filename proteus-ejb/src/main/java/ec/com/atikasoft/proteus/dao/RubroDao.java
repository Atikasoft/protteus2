/*
 *  RubroDao.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Rubro;
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
public class RubroDao extends GenericDAO<Rubro, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(RubroDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public RubroDao() {
        super(Rubro.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<Rubro> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Rubro.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List  de registros de Variables
     * @throws DaoException DaoException
     */
    public List<Rubro> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Rubro.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de Banco que esten vigentes true.
     *
     * @return Listado Rubro
     * @throws DaoException En caso de error
     */
    public List<Rubro> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(Rubro.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Metodo que se encarga de buscar un listado de Banco que esten vigentes true.
     * @param tipo :<I>ngreso de Servidores,Ingresos de <A>nticipos, <D>escuentos, 
     * <P>Aporte institucional, <R>ecuperacion Anticipos
     * @return Listado Rubro
     * @throws DaoException En caso de error
     */
    public List<Rubro> buscarPorTipo(final String tipo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Rubro.BUSCAR_POR_TIPO,tipo);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     /**
     * Metodo que se encarga de buscar un listado de Rubro vigentes po el tipo de beneficiario 
     * @param tipoBeneficiario <N>Normal o <E> Especial.
     * @return Listado Rubro
     * @throws DaoException En caso de error
     */
    public List<Rubro> buscarPorTipoBeneficiario(final String tipoBeneficiario) throws DaoException {
         try {
            return buscarPorConsultaNombrada(Rubro.BUSCAR_POR_TIPO_BENEFICIARIO,tipoBeneficiario);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    
}