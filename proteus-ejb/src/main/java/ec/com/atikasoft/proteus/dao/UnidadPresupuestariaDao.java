/*
 *  UnidadPresupuestaria.java
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
 *  20/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class UnidadPresupuestariaDao extends GenericDAO<UnidadPresupuestaria, Long> {

    /**
     * Constructor por defecto.
     */
    public UnidadPresupuestariaDao() {
        super(UnidadPresupuestaria.class);
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo.
     *
     * @param codigo String
     * @return List
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarPorCodigo(final String codigo) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadPresupuestaria.BUSCAR_POR_CODIGO, codigo);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por codigo y sector.
     *
     * @param codigo String
     * @param sectorId
     * @return List
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarPorCodigoYSector(final String codigo, final Long sectorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadPresupuestaria.BUSCAR_POR_CODIGO_Y_SECTOR, codigo, sectorId);
        } catch (DaoException ex) {
            Logger.getLogger(UnidadPresupuestariaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todo por nombre.
     *
     * @param nombre String
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarTodosPorNombre(final String nombre) throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadPresupuestaria.BUSCAR_POR_NOMBRE, UtilCadena.concatenar("%", nombre,
                    "%"));
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todos los registros vigentes
     *
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarVigentes() throws DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadPresupuestaria.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todos los registros vigentes por
     * sociedad
     *
     * @param sociedad
     * @param numeroIdentificacion
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarPorSociedad(final String sociedad, final String numeroIdentificacion) throws
            DaoException {
        try {
            return buscarPorConsultaNombrada(UnidadPresupuestaria.BUSCAR_POR_SOCIEDAD, sociedad, numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todos los registros vigentes por
     * sociedad
     *
     * @param sociedad
     * @param numeroIdentificacion
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<UnidadPresupuestaria> buscarPorSociedadEnLiquidaciones(final String sociedad,
            final String numeroIdentificacion) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Liquidacion.BUSCAR_UNIDAD_PRESUPUESTARIA_SOCIEDAD, sociedad,
                    numeroIdentificacion);
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de todos los registros vigentes que estén
     * asociados en el distributivo
     *
     * @param idInstitucionEjercicioFiscal
     * @return List de Variables
     * @throws DaoException DaoException
     */
    public List<String> buscarSociedades(final Long idInstitucionEjercicioFiscal) throws DaoException {
        String sql
                = " Select distinct u.sociedad FROM UnidadPresupuestaria u where u.vigente=true "
                + " AND EXISTS ( SELECT a FROM DistributivoDetalle a WHERE a.vigente = true AND a.idServidor IS NOT NULL"
                + " AND a.distributivo.idInstitucionEjercicioFiscal = :ejercicioFiscal ) order by u.sociedad";
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("ejercicioFiscal", idInstitucionEjercicioFiscal);
        List<String> lista = query.getResultList();
        return lista;
    }

    /**
     *
     */
    public UnidadPresupuestaria buscar(String sector, String codigo) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM UnidadPresupuestaria o ");
        sql.append(" WHERE o.sector.codigo = '").append(sector).append("' AND ");
        sql.append(" o.codigo ='").append(codigo).append("'");
        Query query = getEntityManager().createQuery(sql.toString());
        return (UnidadPresupuestaria) query.getSingleResult();
    }

    /**
     * Verifica si el codigo interno esta asignado, en el caso de edicion se
     * puede pasar el id del registo que se esta editando
     *
     * @param codigoInterno
     * @param ignorarId
     * @return
     * @throws DaoException
     */
    public boolean existeCodigoInterno(final String codigoInterno, final Long ignorarId) throws DaoException {
        try {
            return ((ignorarId == null)
                    ? contarPorConsultaNombrada(UnidadPresupuestaria.CONTAR_POR_CODIGO_INTERNO, codigoInterno)
                    : contarPorConsultaNombrada(UnidadPresupuestaria.CONTAR_POR_CODIGO_INTERNO_IGNORAR_ID, codigoInterno, ignorarId)) > 0;
        } catch (DaoException ex) {
            Logger.getLogger(RubroDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
}
