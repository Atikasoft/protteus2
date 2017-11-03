/*
 *  ComprobanteRetencionImpuestoRentaDao.java
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
import ec.com.atikasoft.proteus.modelo.Banco;
import ec.com.atikasoft.proteus.modelo.ComprobanteRetencionImpuestoRenta;
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
 * Formulario 107
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@LocalBean
@Stateless
public class ComprobanteRetencionImpuestoRentaDao extends GenericDAO<ComprobanteRetencionImpuestoRenta, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ComprobanteRetencionImpuestoRentaDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public ComprobanteRetencionImpuestoRentaDao() {
        super(ComprobanteRetencionImpuestoRenta.class);
    }

    /**
     * Obtiene un listado de formularios 107 dado un ejercicio fiscal, y opcionalmente apellidos, nombres y numero identificacion del servidor
     * @param ejecicioFiscalId
     * @param apellidosNombres
     * @param numeroIdentificacion
     * @return
     * @throws DaoException 
     */
    public List<ComprobanteRetencionImpuestoRenta> buscar(final Long ejecicioFiscalId, final String apellidosNombres, final String numeroIdentificacion) throws DaoException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        StringBuilder querySb = new StringBuilder("Select o from ComprobanteRetencionImpuestoRenta o where o.vigente = true ");
        querySb.append(" AND o.ejercicioFiscal.id = :ejercicioFiscal ");
        parametros.put("ejercicioFiscal", ejecicioFiscalId);
        if (apellidosNombres != null && !apellidosNombres.trim().isEmpty()) {
            querySb.append(" AND o.servidor.apellidosNombres like :apellidosNombres ");
            parametros.put("apellidosNombres", "%"+apellidosNombres+"%");
        }
        if (numeroIdentificacion != null && !numeroIdentificacion.trim().isEmpty()) {
            querySb.append(" AND o.servidor.numeroIdentificacion like :numeroIdentificacion ");
            parametros.put("numeroIdentificacion", "%"+numeroIdentificacion+"%");
        }
        querySb.append("order by o.fechaRegistro desc, o.servidor.apellidosNombres ");

        Query q = getEntityManager().createQuery(querySb.toString());
        for (Map.Entry<String, Object> entry : parametros.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return q.getResultList();
    }
}
