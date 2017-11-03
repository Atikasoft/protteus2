/*
 *  ModalidadLaboralDao.java
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
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@LocalBean
@Stateless
public class ModalidadLaboralDao extends GenericDAO<ModalidadLaboral, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ModalidadLaboralDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ModalidadLaboralDao() {
        super(ModalidadLaboral.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<ModalidadLaboral> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ModalidadLaboral.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadLaboralDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<ModalidadLaboral> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ModalidadLaboral.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadLaboralDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de ModalidadLaboral que esten vigentes true.
     *
     * @return Listado ModalidadLaboral
     * @throws DaoException En caso de error
     */
    public List<ModalidadLaboral> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(ModalidadLaboral.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadLaboralDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    /**
     * Busca las modalidades sin certificación presupuestaria en la unidad presupuestaria especificada.
     *
     * @param idUnidadPresupuestaria id de la unidad presupuestaria
     * @return Listado ModalidadLaboral
     * @throws DaoException En caso de error
     */
    public List<ModalidadLaboral> buscarModalidadesSinCertificacionPorUnidadPresupuestaria(Long idUnidadPresupuestaria)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(
                    ModalidadLaboral.BUSCAR_MODALIDADES_SIN_CERTIFICACION_PRESUPUESTARIA_POR_UNIDAD_PRESUPUESTARIA,
                    idUnidadPresupuestaria);
        } catch (DaoException ex) {
            Logger.getLogger(ModalidadLaboralDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}