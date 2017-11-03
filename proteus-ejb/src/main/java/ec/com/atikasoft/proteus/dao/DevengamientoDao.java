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
import ec.com.atikasoft.proteus.modelo.Devengamiento;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a devengamiento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class DevengamientoDao extends GenericDAO<Devengamiento, Long> {

    /**
     * Constructor.
     */
    public DevengamientoDao() {
        super(Devengamiento.class);
    }

    /**
     * Recupera los devengamientos vigentes por servidor.
     *
     * @param tipoIdentificacion Tipo de identificacion.
     * @param numeroIdentificacion Numero de identificacion.
     * @param institucionId Identificacion de la institucion.
     * @param fechaCorte Fecha de corte.
     * @return
     * @throws DaoException
     */
    public List<Devengamiento> buscarPorServidor(final String tipoIdentificacion, final String numeroIdentificacion,
            final Long institucionId, final Date fechaCorte) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Devengamiento.BUSCAR_POR_SERVIDOR, tipoIdentificacion.substring(0, 1),
                    numeroIdentificacion, institucionId, fechaCorte);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * Este metodo busca un Devengamiento segun el numero de identificacion.
     *
     * @param numeroIdentificacion String
     * @return Devengamiento
     * @throws DaoException Captura de errores
     */
    public Devengamiento buscarPorDocumentoHabilitante(final String numeroIdentificacion) throws DaoException {
        Devengamiento m = null;
        List<Devengamiento> movimientos = buscarPorConsultaNombrada(
                Devengamiento.BUSCAR_POR_NUMERO_IDENTIFICACION, numeroIdentificacion);
        if (movimientos != null && !movimientos.isEmpty()) {
            m = movimientos.get(0);
        }
        return m;
    }
}
