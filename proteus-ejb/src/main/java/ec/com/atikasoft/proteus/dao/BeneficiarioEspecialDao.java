/*
 *  BeneficiarioEspecialDao.java
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
 *  18/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
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
public class BeneficiarioEspecialDao extends GenericDAO<BeneficiarioEspecial, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(BeneficiarioEspecialDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public BeneficiarioEspecialDao() {
        super(BeneficiarioEspecial.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de BeneficiarioEspecial que esten vigentes true.
     *
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioEspecial> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioEspecial.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de BeneficiarioEspecial vigentes por Id de beneficiarioInstitucion
     *
     * @param idBeneficiarioInstitucion
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioEspecial> buscarPorIdBeneficiarioInstitucion(final Long idBeneficiarioInstitucion) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_INSTITUCION,
                    idBeneficiarioInstitucion);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
     /**
     * Metodo que se encarga de buscar un listado de BeneficiarioEspecial vigentes por Id de beneficiarioInstitucion
     *
     * @param numeroIdentificacion
     * @param idRubro
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioEspecial> buscarPorNumeroIdentificacionYRubro(final String numeroIdentificacion , final Long idRubro) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioEspecial.BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO,
                    numeroIdentificacion,idRubro);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
      /**
     * Metodo que se encarga de buscar un listado de BeneficiarioEspecial vigentes por su número de identificacion
     *
     * @param numeroIdentificacion
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioEspecial> buscarPorNumeroIdentificacion(final String numeroIdentificacion) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_POR_NUMERO_IDENTIFICACION,
                    numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param rubroId
     * @param institucionId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<BeneficiarioEspecial> buscar(final Long rubroId, final Long institucionId, final Long servidorId) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_PARA_NOMINA,
                    rubroId, institucionId, servidorId);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
}