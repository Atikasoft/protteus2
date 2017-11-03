/*
 *  AsistenciaDao.java
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
 *  26/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.distributivo.CargaMasivaPuesto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@LocalBean
@Stateless
public class CargaMasivaPuestoDao extends GenericDAO<CargaMasivaPuesto, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CargaMasivaPuestoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public CargaMasivaPuestoDao() {
        super(CargaMasivaPuesto.class);
    }

    /**
     * Busca las cargas masivas de puestos realizadas para una institucion ejecicio fiscal
     * @param idInstitucionEjericioFiscal
     * @return
     * @throws DaoException 
     */
    public List<CargaMasivaPuesto> buscarPorInstitucionEjericioFiscal(final Long idInstitucionEjericioFiscal) throws DaoException {
        try {
            List<CargaMasivaPuesto> lista = buscarPorConsultaNombrada(CargaMasivaPuesto.BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL,
                    idInstitucionEjericioFiscal);
            return lista;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error cargando la lista de cargas masivas ejecutadas", e);
            throw new DaoException(e);
        }
    }
}
