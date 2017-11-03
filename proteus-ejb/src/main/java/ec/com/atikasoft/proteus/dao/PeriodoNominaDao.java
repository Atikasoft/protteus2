/*
 *  PeriodoNominaDao.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.dao;

import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.generico.GenericDAO;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * PeriodoNominaDao
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@LocalBean
@Stateless
public class PeriodoNominaDao extends GenericDAO<PeriodoNomina, Long> {

    /**
     * Constructor por defecto.
     */
    public PeriodoNominaDao() {
        super(PeriodoNomina.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de periodo que esten vigentes true.
     *
     * @return Listado Banco
     * @throws DaoException En caso de error
     */
    public List<PeriodoNomina> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(PeriodoNomina.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(PeriodoNominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param fecha
     * @return
     * @throws DaoException
     */
    public PeriodoNomina buscarPorFecha(final Date fecha) throws DaoException {
        try {
            PeriodoNomina pn = null;
            List<PeriodoNomina> lista = buscarPorConsultaNombrada(PeriodoNomina.BUSCAR_POR_FECHA, UtilFechas.
                    truncarFecha(fecha));
            if (!lista.isEmpty()) {
                pn = lista.get(0);
            }
            return pn;
        } catch (DaoException ex) {
            Logger.getLogger(PeriodoNominaDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }
    
    public List<PeriodoNomina> buscarPorEjercicio(Long ejercicioFiscalId ) throws DaoException {
        try {
            return buscarPorConsultaNombrada(PeriodoNomina.BUSCAR_POR_EJERCICIO, ejercicioFiscalId);
        } catch (Exception e) {
             throw new DaoException(e);
        }
    }
}
