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
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class TramiteDao extends GenericDAO<Tramite, Long> {

    /**
     * Logger de clase.
     */
    private static final Logger LOG = Logger.getLogger(TramiteDao.class.getCanonicalName());

    /**
     * Constructor.
     */
    public TramiteDao() {
        super(Tramite.class);
    }

    /**
     * Metodo que se encarga de buscar una lista de tramites por estado y usuario.
     *
     * @param estado estado del tramite
     * @param usuario usuario logeado
     * @param codigoUnidadOrganizacional
     * @return Lista de tramites
     * @throws DaoException en caso de error
     */
    public List<Tramite> buscarPorEstadoYUsuario(final String estado, final String usuario) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Tramite.BUSCAR_POR_ESTADO_Y_USUARIO, estado, usuario);
        } catch (DaoException ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de contar tramites por estado y usuario.
     *
     * @param estado estado del tramite
     * @param usuario usuario logeado
     * @param codigoInstitucion
     * @return Lista de tramites
     * @throws DaoException en caso de error
     */
    public Long contarPorEstadoYUsuario(final String estado, final String usuario, final String codigoInstitucion)
            throws DaoException {
        try {
            Query query = getEntityManager().createNamedQuery(Tramite.CONTAR_POR_ESTADO_Y_USUARIO);
            query.setParameter(1, estado);
            query.setParameter(2, usuario);
            query.setParameter(3, codigoInstitucion);
            return (Long) query.getSingleResult();
        } catch (Exception ex) {
            Logger.getLogger(GrupoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de listar los tramites segun los parametros enviados como filtros.
     *
     * @param codigoFase codigoFase a buscar
     * @param usuario usuario
     * @param codigoInstitucion codigo de la institucion logeado
     * @param ejercicioFiscal ejercicio fiscal de la institucion logeado
     * @param token campo abierto de busqueda
     * @param ordenar campo para ordenar
     * @param tipo tipo de ordenamiento
     * @return List<Tramite> lista de tramites
     * @throws DaoException en caso de error
     */
    public List<Tramite> buscar(final String codigoFase, final String usuario, final String codigoInstitucion,
            final Integer ejercicioFiscal, final String token, final String ordenar, final String tipo)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(
                "SELECT o FROM Tramite o  WHERE 1=1  ");
        generarFiltro(sql, parametros, "codigoFase", codigoFase);
        generarFiltro(sql, parametros, "usuarioAsignadoCedulaElaborador", usuario);
        generarFiltro(sql, parametros, "codigoInstitucion", codigoInstitucion);
        generarFiltro(sql, parametros, "codigoEjercicioFiscal", ejercicioFiscal);
        if (token != null && !token.isEmpty()) {
            sql.append(" AND concat(o.descripcion,' ',o.tipoMovimiento.nombre) LIKE :token ");
            parametros.put("token", UtilCadena.concatenar("%", token, "%"));
        }
        ordenar(ordenar, tipo, sql);
//        LOG.info("SQL:" + sql);
        Query query = getEntityManager().createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
//            LOG.info("\n\n" + key + " : " + parametros.get(key));
        }
//        LOG.info("SQL::::" + obtenerSql(query, parametros));
        return query.getResultList();
    }

    /**
     * Genera filtro por parametro de consulta.
     *
     * @param sql Sql.
     * @param parametros Parametros.
     * @param nombre Nombre.
     * @param valor Valor.
     */
    @Override
    protected void generarFiltro(final StringBuilder sql, final Map<String, Object> parametros, final String nombre,
            final Object valor) {
        if (valor != null) {
            sql.append(" AND o.");
            sql.append(nombre);
            sql.append("=:");
            sql.append(nombre);
            parametros.put(nombre, valor);
        }
    }

    /**
     * Ordena la consulta.
     *
     * @param ordenar
     * @param tipo
     * @param sql
     */
    protected void ordenar(final String ordenar, final String tipo, final StringBuilder sql) {
        if (ordenar != null && !ordenar.trim().isEmpty()) {
            sql.append(" ORDER BY o.");
            sql.append(ordenar);
            if (tipo != null && !tipo.trim().isEmpty()) {
                sql.append(" ");
                sql.append(tipo);
            }
        }
    }

    /**
     * Metodo que se encarga de listar los tramites segun los estados especificados.
     *
     * @param estados
     * @return lista de tramites
     * @throws DaoException en caso de error
     */
    public List<Tramite> buscarPorEstados(final String[] estados)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<>();
        StringBuilder sql = new StringBuilder(
                "SELECT o FROM Tramite o WHERE o.vigente='true' ");
        boolean primero = true;
        for (String e : estados) {
            sql.append(primero ? " AND " : " OR ");
            sql.append(" o.codigoFase=:");
            sql.append(e);
            parametros.put(e, e);
            primero = false;
        }
        sql.append(" ORDER BY o.estado ");
        //LOG.log(Level.INFO, "SQL:{0}", sql);
        Query query = getEntityManager().createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            query.setParameter(key, parametros.get(key));
         //   LOG.log(Level.INFO, "\n\n{0} : {1}", new Object[]{key, parametros.get(key)});
        }
       // LOG.log(Level.INFO, "SQL:::: {0}", obtenerSql(query, parametros));
        return query.getResultList();
    }
}
