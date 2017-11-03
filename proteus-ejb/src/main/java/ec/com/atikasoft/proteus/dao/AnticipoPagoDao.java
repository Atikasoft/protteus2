/*
 *  AnticipoPagoDao.java
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
import ec.com.atikasoft.proteus.modelo.AnticipoPago;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@LocalBean
@Stateless
public class AnticipoPagoDao extends GenericDAO<AnticipoPago, Long> {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AnticipoPagoDao.class.getCanonicalName());

    /**
     * Constructor sin argumentos.
     */
    public AnticipoPagoDao() {
        super(AnticipoPago.class);
    }

    /**
     * Metodo que se encarga de buscar un listado de AnticipoPago que esten
     * vigentes true.
     *
     * @return Listado AnticipoPago
     * @throws DaoException En caso de error
     */
    public List<AnticipoPago> buscarVigente() throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPago.BUSCAR_VIGENTES);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoPagoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar un listado de AnticipoPago que esten
     * vigentes por id del plan pago true.
     *
     * @param idPlanPago
     * @return Listado AnticipoPago
     * @throws DaoException En caso de error
     */
    public List<AnticipoPago> buscarPorPlanPago(final Long idPlanPago) throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPago.BUSCAR_POR_PLAN_PAGO, idPlanPago);
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoPagoDao.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException(ex);
        }
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int eliminar(final Long nominaId) throws DaoException {
        try {
            return ejecutarPorConsultaNombrada(AnticipoPago.ELIMINAR, nominaId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param distributivoDetalleId
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarPorServidor(final Long nominaId, final Long distributivoDetalleId)
            throws DaoException {
        try {
            ejecutarPorConsultaNombrada(AnticipoPago.ELIMINAR_POR_SERVIDOR, nominaId, distributivoDetalleId);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param anticipoPlanPagoId
     * @return
     * @throws DaoException
     */
    public BigDecimal buscarTotalPagosDeCuota(final Long anticipoPlanPagoId) throws DaoException {
        try {
            BigDecimal valor = BigDecimal.ZERO;
            Object obj = buscarUnicoPorConsultaNombrada(AnticipoPago.SUMAR_PAGOS, anticipoPlanPagoId);
            if (obj != null) {
                valor = (BigDecimal) obj;
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @return
     * @throws DaoException
     */
    public List<AnticipoPago> buscarPorNomina(Long nominaId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPago.BUSCAR_NOMINA, nominaId);

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    /**
     *
     * @param nominaId
     * @param servidorId
     * @return
     * @throws DaoException
     */
    public List<AnticipoPago> buscarPorNominaServidor(Long nominaId, Long servidorId) throws DaoException {
        try {
            return buscarPorConsultaNombrada(AnticipoPago.BUSCAR_NOMINA_SERVIDOR, nominaId, servidorId);

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
    
    /**
     * Determina el total pagado para una solicitud de anticipo
     * @param anticipoId
     * @return
     * @throws DaoException 
     */
    public BigDecimal buscarTotalPagosAnticipo(final Long anticipoId) throws DaoException {
        try {
            BigDecimal valor = BigDecimal.ZERO;
            Object obj = buscarUnicoPorConsultaNombrada(AnticipoPago.TOTAL_PAGADO_ANTICIPO, anticipoId);
            if (obj != null) {
                valor = (BigDecimal) obj;
            }
            return UtilNumeros.redondear(valor, 2, true);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}
