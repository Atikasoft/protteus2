/*
 *  BeneficiarioInstitucionalDao.java
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
 *  17/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
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
public class BeneficiarioInstitucionalDao extends GenericDAO<BeneficiarioInstitucional, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(BeneficiarioInstitucionalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public BeneficiarioInstitucionalDao() {
        super(BeneficiarioInstitucional.class);
    }

   
    
    /**
     * Este metodo procesa la busqueda de todo por tipo de beneficiario
     *
     * @param tipoBeneficiario String Normal <N> o Especial <E>
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<BeneficiarioInstitucional> buscarTodosPorTipoBeneficiario(final String tipoBeneficiario) throws DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioInstitucional.BUSCAR_POR_TIPO_BENEFICIARIO, tipoBeneficiario);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioInstitucionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
      /**
     * Este metodo procesa la busqueda de todo por tipo de beneficiario
     *
     * @param numeroDocumento String Número de identificacion
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<BeneficiarioInstitucional> buscarTodosPorNumeroIdentificacion(final String numeroDocumento) throws DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioInstitucional.BUSCAR_POR_NUMERO_IDENTIFICACION, numeroDocumento);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioInstitucionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de BeneficiarioInstitucional que esten vigentes true.
     *
     * @return Listado BeneficiarioInstitucional
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioInstitucional> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioInstitucional.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioInstitucionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
      /**
     * Metodo que se encarga de buscar un listado de BeneficiarioInstitucional vigentes por Id de beneficiarioInstitucion
     *
     * @param numeroIdentificacion
     * @param idRubro
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioInstitucional> buscarPorNumeroIdentificacionYRubro(final String numeroIdentificacion , final Long idRubro) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioInstitucional.BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO,
                    numeroIdentificacion,idRubro);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
         /**
     * Metodo que se encarga de buscar un listado de BeneficiarioInstitucional vigentes por Id de beneficiarioInstitucion
     *
     * @param idServidor
     * @param idRubro
     * @return Listado BeneficiarioEspecial
     * @throws DaoException En caso de error
     */
    public List<BeneficiarioInstitucional> buscarPorServidorYRubro(final Long idServidor , final Long idRubro) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(BeneficiarioInstitucional.BUSCAR_POR_SERVIDOR_Y_RUBRO,
                    idServidor,idRubro);
        } catch (DaoException ex) {
            Logger.getLogger(BeneficiarioEspecialDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}