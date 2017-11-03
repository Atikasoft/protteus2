/*
 *  AsignacionDao.java
 *  ESIPREN V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 21, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.Detalle;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Acceso a datos de la entidad Detalle de Instancia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class DetalleDao extends GenericDAO<Detalle, Long> {

    /**
     * Constructor sin argumentos.
     */
    public DetalleDao() {
        super(Detalle.class);
    }

    /**
     * Recupera los detalles de una instancia a partir de su identificador
     * externo. ..
     *
     * @param identificadorExterno Identificador externo.
     * @return Lista de detalles.
     * @throws DaoException Error de ejecucion.
     */
    public List<Detalle> buscarPorIdentificadorExterno(final Long identificadorExterno) throws DaoException {
        try {
            return buscarPorConsultaNombrada(Detalle.BUSCAR_POR_INSTANCIA_EXTERNA, identificadorExterno);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    /**
     * Recupera los detalles de una instancia a partir de su identificador
     * externo y estado.
     *
     * @param identificadorExterno Identificador externo.
     * @return Lista de detalles.
     * @throws DaoException Error de ejecucion.
     */
    public List<Detalle> buscarPorIdentificadorExternoYEstado(final Long identificadorExterno, final Long estadoId)
            throws DaoException {
        try {
            return buscarPorConsultaNombrada(Detalle.BUSCAR_POR_INSTANCIA_EXTERNA_Y_ESTADO, identificadorExterno,
                    estadoId);
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }
}
