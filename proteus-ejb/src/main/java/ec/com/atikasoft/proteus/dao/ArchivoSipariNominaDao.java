/*
 *  ArchivoSipariNominaDao.java
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
 *  28/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ArchivoSipari;
import ec.com.atikasoft.proteus.modelo.ArchivoSipariNomina;
import ec.com.atikasoft.proteus.modelo.Banco;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class ArchivoSipariNominaDao extends GenericDAO<ArchivoSipariNomina, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ArchivoSipariNominaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ArchivoSipariNominaDao() {
        super(ArchivoSipariNomina.class);
    }

    /**
     * Este metodo procesa la busqueda de todos los encabezados por padre.
     *
     * @param idArchivoSipari
     * @return List de registros de Bancos
     * @throws DaoException DaoException
     */
    public List<ArchivoSipariNomina> buscarEncabezadosPorPadre(final Long idArchivoSipari) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ArchivoSipariNomina.BUSCAR_ENCABEZADOS_POR_PADRE, idArchivoSipari);
        } catch (DaoException ex) {
            Logger.getLogger(ArchivoSipariNominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Este metodo procesa la busqueda de registros ordenados por el encabezado.
     *
     * @param idArchivoPadre
     * @param idEncabezado
     * @param inicio
     * @param longitud
     * @return List
     * @throws DaoException DaoException
     */
    public List<ArchivoSipariNomina> buscarDetallesParaArchivoSipariNomina(
            final Long idArchivoPadre, final Long idEncabezado, final Integer inicio, Integer longitud)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        String sql =
                "SELECT o FROM ArchivoSipariNomina o WHERE o.vigente=true AND o.archivoSipari.id = :idArchivoPadre "
                + " and o.encabezadoPadre.id = :idEncabezado"
                + " ORDER BY o.codigoEmpleado,o.debeHaber,o.conceptoNomina ";
        parametros.put("idArchivoPadre", idArchivoPadre);
        parametros.put("idEncabezado", idEncabezado);
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);
        return query.getResultList();
    }

    /**
     *
     * @param archivoEncabezado
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearEncabezado(final ArchivoSipariNomina archivoEncabezado) throws DaoException {
        crear(archivoEncabezado);
        flush();
    }

    /**
     *
     * @param archivoSipariId
     * @param codigoEmpleado
     * @param posicion
     * @return
     * @throws DaoException
     */
    public ArchivoSipariNomina buscarIngresosPorPartida(final Long archivoSipariId, final String codigoEmpleado,
            final String posicion) throws DaoException {
        try {
            ArchivoSipariNomina entidad = null;
            List<ArchivoSipariNomina> lista = buscarPorConsultaNombrada(ArchivoSipariNomina.BUSCAR_INGRESOS_POR_PARTIDA,
                    archivoSipariId, codigoEmpleado, posicion);
            if (!lista.isEmpty()) {
                entidad = lista.get(0);
            }
            return entidad;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
