/*
 *  DatoAdicionalDao.java
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
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Feriado;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class DatoAdicionalDao extends GenericDAO<DatoAdicional, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DatoAdicionalDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public DatoAdicionalDao() {
        super(DatoAdicional.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List
     * @throws DaoException DaoException
     */
    public List<DatoAdicional> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DatoAdicional.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(DatoAdicionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List Lista de registros de Dato Adicional
     * @throws DaoException DaoException
     */
    public List<DatoAdicional> buscarTodosPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(DatoAdicional.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(DatoAdicionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de DatoAdicional que esten vigentes true.
     *
     * @return Listado DatoAdicional
     * @throws DaoException En caso de error
     */
    public List<DatoAdicional> buscarVigente() throws DaoException {    
        try {
            return buscarPorConsultaNombrada(DatoAdicional.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(DatoAdicionalDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
      /**
     * Busca los datos adicionales de acuerdo al tipo de la nómina.
     * @param idTipoNomina
     * @return
     * @throws DaoException
     */
    public List<DatoAdicional> buscarPorTipoNomina(final Long idTipoNomina) 
            throws DaoException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a FROM DatoAdicional a ");
        sql.append(" WHERE a.vigente=true ");
        sql.append(" AND  EXISTS (SELECT r.id FROM Rubro r WHERE r.vigente = true AND  EXISTS ( ");
        sql.append(" SELECT rt.id FROM RubroTipoNomina rt where rt.vigente = true AND  rt.idTipoNomina = :idTipoNomina)) ");
        sql.append(" ORDER BY a.nombre");
        
        Query createQuery = getEntityManager().createQuery(sql.toString());
            createQuery.setParameter("idTipoNomina", idTipoNomina);
        List resultList = createQuery.getResultList();
        return resultList;
    }
}