/*
 *  ArchivoSipariRetencionDao.java
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
import ec.com.atikasoft.proteus.modelo.ArchivoSipariRetencion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ArchivoSipariRetencionDao extends GenericDAO<ArchivoSipariRetencion, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ArchivoSipariRetencionDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ArchivoSipariRetencionDao() {
        super(ArchivoSipariRetencion.class);
    }


    /**
     * Este metodo procesa la busqueda de registros ordenados por el encabezado.
     *
     * @param idArchivoPadre
     * @param inicio
     * @param longitud
     * @return List
     * @throws DaoException DaoException
     */
    public List<ArchivoSipariRetencion> buscarDetallesParaArchivoSipariRetencions(
            final Long idArchivoPadre, final Integer inicio, Integer longitud)
            throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        String sql
                = "SELECT o FROM ArchivoSipariRetencion o WHERE o.vigente=true AND o.archivoSipari.id = :idArchivoPadre "
                + " ORDER BY o.codigoEmpleado ";
        parametros.put("idArchivoPadre", idArchivoPadre);
        Query query = getEntityManager().createQuery(sql.toString());
        Set<String> keys = parametros.keySet();
        for (String key : keys) {
            query.setParameter(key, parametros.get(key));
        }
        query.setFirstResult(inicio);
        query.setMaxResults(longitud);
        return query.getResultList();
    }
}
