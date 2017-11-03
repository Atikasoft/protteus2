/*
 *  AnticipoDao.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.ServidorCargaFamiliar;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class ServidorCargaFamiliarDao extends GenericDAO<ServidorCargaFamiliar, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ServidorCargaFamiliarDao.class.getName());

    /**
     * Constructor sin argumentos.
     */
    public ServidorCargaFamiliarDao() {
        super(ServidorCargaFamiliar.class);
    }

    /**
     * Calcula el numero de cargas familiares para la nomina.
     */
    public Long contarPorServidor(final Long servidorId) throws DaoException {
        try {
            Date fechaReferencia = UtilFechas.sumarAnios(new Date(), -18);
            return (Long) buscarUnicoPorConsultaNombrada(ServidorCargaFamiliar.BUSCAR_PAGO_NOMINA, servidorId,
                    fechaReferencia);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     * metodo que busca por el id del reclutamiento que es el padre.
     *
     * @param cargaFamiliarId
     * @return
     * @throws DaoException
     */
    public List<ServidorCargaFamiliar> buscarPorServidorId(final Long cargaFamiliarId
    ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(ServidorCargaFamiliar.BUSCAR_POR_CARGA_FAMILIAR_ID,
                    cargaFamiliarId);
        } catch (DaoException ex) {
            Logger.getLogger(ServidorCargaFamiliarDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

}
