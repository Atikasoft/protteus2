/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AnticipoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPagoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPlanPagoDao;
import ec.com.atikasoft.proteus.dao.LiquidacionDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleNovedadDao;
import ec.com.atikasoft.proteus.dao.NominaIRDao;
import ec.com.atikasoft.proteus.dao.NovedadDetalleDao;
import ec.com.atikasoft.proteus.dao.TerceroNominaDetalleDao;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadoPlanPagoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.AnticipoPago;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.*;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class NominaEliminacionServicio extends BaseServicio {

    /**
     *
     */
    @EJB
    private TerceroNominaDetalleDao terceroNominaDetalleDao;

    /**
     *
     */
    @EJB
    private AnticipoDao anticipoDao;

    /**
     *
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     *
     */
    @EJB
    private NominaDetalleNovedadDao nominaDetalleNovedadDao;

    /**
     *
     */
    @EJB
    private AnticipoPagoDao anticipoPagoDao;

    /**
     *
     */
    @EJB
    private AnticipoPlanPagoDao anticipoPlanPagoDao;

    /**
     *
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     *
     */
    @EJB
    private NominaIRDao nominaIRDao;

    /**
     *
     */
    @EJB
    private LiquidacionDao liquidacionDao;

    /**
     *
     */
    @EJB
    private AnticipoServicio anticipoServicio;

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresTerceros(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.getCodigo())) {
            terceroNominaDetalleDao.eliminarPorNomina(objeto.getNomina().getId());
        }
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresAnticipos(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            anticipoDao.quitarNomina(objeto.getNomina().getId());
        }
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void encerarDatosAnterioresNovedades(final ObjetoNominaVO objeto) throws DaoException {
        novedadDetalleDao.encerarNovedadesDetallesPorNomina(objeto.getNomina().getId());
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresNovedades(final ObjetoNominaVO objeto) throws DaoException {
        nominaDetalleNovedadDao.eliminar(objeto.getNomina().getId());
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPagosAnticipos(final ObjetoNominaVO objeto) throws DaoException, ServicioException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
            Set<Long> ids = new HashSet<Long>();
            List<AnticipoPago> pagos = anticipoPagoDao.buscarPorNomina(objeto.getNomina().getId());
            for (AnticipoPago pago : pagos) {
                AnticipoPlanPago app = pago.getAnticipoPlanPago();
                if (!ids.contains(app.getId()) && app.getEstadoPago().equals(EstadoPlanPagoEnum.PAGADO.getCodigo())) {
                    if (anticipoServicio.calcularSaldoDeCuota(app.getId()).compareTo(BigDecimal.ZERO) != 0) {
                        app.setEstadoPago(EstadoPlanPagoEnum.PENDIENTE.getCodigo());
                        anticipoPlanPagoDao.actualizar(app);
                    }
                }
                ids.add(app.getId());
            }
            anticipoPagoDao.eliminar(objeto.getNomina().getId());
        }
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresNominas(final ObjetoNominaVO objeto) throws DaoException {
        nominaDetalleDao.eliminar(objeto.getNomina().getId());
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresNominasIR(final ObjetoNominaVO objeto) throws DaoException {
        nominaIRDao.eliminar(objeto.getNomina().getId());
    }

    /**
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresLiquidaciones(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            liquidacionDao.quitarNomina(objeto.getNomina().getId());
        }
    }

    /**
     *
     * @param objeto
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorLiquidaciones(final ObjetoNominaVO objeto,
            final DistributivoDetalle dd) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            liquidacionDao.quitarNominaServidor(objeto.getNomina().getId(), dd.getServidor().getId());
        }
    }

    /**
     *
     * @param objeto
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorAnticipos(final ObjetoNominaVO objeto,
            final DistributivoDetalle dd) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            anticipoDao.quitarNominaServidor(objeto.getNomina().getId(), dd.getServidor().getId());
        }
    }

    /**
     *
     * @param objeto
     * @param dd
     * @throws DaoException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorPagosAnticipos(final ObjetoNominaVO objeto, final DistributivoDetalle dd)
            throws DaoException, ServicioException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
            Set<Long> ids = new HashSet<Long>();
            List<AnticipoPago> pagos = anticipoPagoDao.buscarPorNominaServidor(objeto.getNomina().getId(), dd.getServidor().getId());
            for (AnticipoPago pago : pagos) {
                AnticipoPlanPago app = pago.getAnticipoPlanPago();
                if (!ids.contains(app.getId()) && app.getEstadoPago().equals(EstadoPlanPagoEnum.PAGADO.getCodigo())) {
                    if (anticipoServicio.calcularSaldoDeCuota(app.getId()).compareTo(BigDecimal.ZERO) != 0) {
                        app.setEstadoPago(EstadoPlanPagoEnum.PENDIENTE.getCodigo());
                        anticipoPlanPagoDao.actualizar(app);
                    }
                }
                ids.add(app.getId());
            }
            anticipoPagoDao.eliminarPorServidor(objeto.getNomina().getId(), dd.getId());
        }
    }

    /**
     *
     * @param obj
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void encerarDatosAnterioresPorServidorNovedades(final ObjetoNominaVO obj, final DistributivoDetalle dd)
            throws DaoException {
        novedadDetalleDao.encerarNovedadesDetallesPorNominas(obj.getNomina().getId(), dd.getServidor().getId());
    }

    /**
     *
     * @param obj
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorNovedades(final ObjetoNominaVO obj, final DistributivoDetalle dd)
            throws DaoException {
        nominaDetalleNovedadDao.eliminarPorServidor(obj.getNomina().getId(), dd.getId());
    }

    /**
     *
     * @param obj
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorNominas(final ObjetoNominaVO obj,
            final DistributivoDetalle dd) throws DaoException {
        nominaDetalleDao.eliminarPorServidor(obj.getNomina().getId(), dd.getId());
    }

    /**
     *
     * @param obj
     * @param dd
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void eliminarDatosAnterioresPorServidorNominasIR(final ObjetoNominaVO obj,
            final DistributivoDetalle dd) throws DaoException {
        nominaIRDao.eliminarPorServidor(obj.getNomina().getId(), dd.getServidor().getId());
    }

}
