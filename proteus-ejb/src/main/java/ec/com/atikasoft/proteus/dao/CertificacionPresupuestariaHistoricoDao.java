/*
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
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestariaHistorico;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless
public class CertificacionPresupuestariaHistoricoDao extends GenericDAO<CertificacionPresupuestariaHistorico, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(CertificacionPresupuestariaHistoricoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public CertificacionPresupuestariaHistoricoDao() {
        super(CertificacionPresupuestariaHistorico.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de CertificacionPresupuestariaHistorico que esten vigentes
     * true.
     *
     * @return Listado CertificacionPresupuestariaHistorico
     * @throws DaoException En caso de error
     */
    public List<CertificacionPresupuestariaHistorico> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(CertificacionPresupuestariaHistorico.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(CertificacionPresupuestariaHistoricoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    
}
