/*
 *  EscalaOcupacionalDao.java
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
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
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
public class EscalaOcupacionalDao extends GenericDAO<EscalaOcupacional, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(EscalaOcupacionalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public EscalaOcupacionalDao() {
        super(EscalaOcupacional.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<EscalaOcupacional> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EscalaOcupacional.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(EscalaOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<EscalaOcupacional> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EscalaOcupacional.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(EscalaOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de EscalaOcupacional que esten
     * vigentes true.
     *
     * @return Listado EscalaOcupacional
     * @throws DaoException En caso de error
     */
    public List<EscalaOcupacional> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(EscalaOcupacional.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(EscalaOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por regimen id.
     *
     * @param regimenId Long
     * @return List Lista de registros de Modalidad Laboral
     * @throws DaoException DaoException
     */
    public List<EscalaOcupacional> buscarTodosPorNivelOcupacionalId(final Long nivelOcupacionalId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(EscalaOcupacional.BUSCAR_POR_NIVEL_OCUPACIONAL, nivelOcupacionalId);
        } catch (DaoException ex) {
            Logger.getLogger(EscalaOcupacionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    public EscalaOcupacional buscar(final String codigoEscala, final String codigoNivel, final String codigoRegimen)
            throws
            DaoException {
        try {
            EscalaOcupacional eo = null;
            List<EscalaOcupacional> lista = buscarPorConsultaNombrada(EscalaOcupacional.BUSCAR_POR_CODIGOS, codigoEscala,
                    codigoNivel, codigoRegimen);
            if (!lista.isEmpty()) {
                eo = lista.get(0);
            }
            return eo;
        } catch (DaoException ex) {
            throw new DaoException(ex);
        }
    }

}
