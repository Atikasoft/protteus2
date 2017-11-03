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
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
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
public class ParametroInstitucionalDao extends GenericDAO<ParametroInstitucional, Long> {

    /**
     * Constructor.
     */
    public ParametroInstitucionalDao() {
        super(ParametroInstitucional.class);
    }

    /**
     * Metodo que se encarga de buscar las validaciones por el id del movimiento.
     *
     * @param movimientoId Id del de movimiento
     * @return Lista de Validaciones
     * @throws DaoException En caso de error
     */
    public List<ParametroInstitucional> buscarPorInstitucionId(final Long institucionId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ParametroInstitucional.BUSCAR_POR_INSTITUCION_ID, institucionId);
        } catch (DaoException ex) {
            Logger.getLogger(ParametroInstitucionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Recupera el parametro institucional.
     *
     * @param institucionId Identificador de la institucion.
     * @param nemonico Nemonico del catalogo.
     * @return
     * @throws DaoException
     */
    public ParametroInstitucional buscarPorInstitucionYNemonico(final Long institucionId, final String nemonico) throws
            DaoException {
        try {
            ParametroInstitucional entidad = null;
            List<ParametroInstitucional> lista = buscarPorConsultaNombrada(
                    ParametroInstitucional.BUSCAR_POR_INSTITUCION_Y_CODIGO, institucionId, nemonico);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
     /**
     * Recupera el parametro institucional.
     *
     * @param nemonico Nemonico del catalogo.
     * @return
     * @throws DaoException
     */
    public ParametroInstitucional buscarPorNemonico(final String nemonico) throws
            DaoException {
        try {
            ParametroInstitucional entidad = null;
            List<ParametroInstitucional> lista = buscarPorConsultaNombrada(
                    ParametroInstitucional.BUSCAR_POR_CODIGO,  nemonico);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
      public ParametroInstitucional buscarParametroInstitucionalPorInstitucionYparametroInstitucionalCatalogo(
             final Long institucionId, final Long parametroInstitucionalCatalogoId) throws DaoException {
        ParametroInstitucional pi = null;
        List<ParametroInstitucional> parametroInstitucional = buscarPorConsultaNombrada(
                ParametroInstitucional.BUSCAR_POR_INSTITUCION_PARAMETRO_INSTITUCINAL_CATALOGO, 
                institucionId, parametroInstitucionalCatalogoId);
//        if (parametroInstitucional != null && !parametroInstitucional.isEmpty()) {
//            pi = parametroInstitucional.get(0);
//        }
        return pi;
    }
}
