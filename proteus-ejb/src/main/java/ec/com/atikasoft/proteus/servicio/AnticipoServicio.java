/*
 *  AnticipoServicio.java
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
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AnticipoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPagoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPlanPagoDao;
import ec.com.atikasoft.proteus.dao.TipoAnticipoDao;
import ec.com.atikasoft.proteus.dao.VistaAnticipoDao;
import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.AnticipoPago;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import ec.com.atikasoft.proteus.modelo.vistas.VistaAnticipo;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.AnticipoVO;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.ConsultaAnticipoVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class AnticipoServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(AnticipoServicio.class.getCanonicalName());
    /**
     * DAO de Anticipo.
     */
    @EJB
    private AnticipoDao anticipoDao;

    /**
     * DAO de AnticipoPago.
     */
    @EJB
    private AnticipoPagoDao anticipoPagoDao;

    /**
     * DAO de AnticipoPlanPago.
     */
    @EJB
    private AnticipoPlanPagoDao anticipoPlanPagoDao;

    /**
     * Dao de TipoAnticipo.
     */
    @EJB
    private TipoAnticipoDao tipoAnticipoDao;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * DAO de Vista de anticipo.
     */
    @EJB
    private VistaAnticipoDao vistaAnticipoDao;

    /**
     * metodo para guardar un tipoAnticipo
     *
     * @param tipoAnticipo registro a guardar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void guardarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setCodigo(tipoAnticipo.getCodigo().toUpperCase());
            tipoAnticipo.setNombre(tipoAnticipo.getNombre().toUpperCase());
            tipoAnticipo.setVigente(Boolean.TRUE);
            tipoAnticipoDao.crear(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la actualización de un tipoAnticipo
     *
     * @param tipoAnticipo Instancia de tipoAnticipo
     * @throws ServicioException excepciones a nivel de negocio
     */
    public void actualizarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setCodigo(tipoAnticipo.getCodigo().toUpperCase());
            tipoAnticipo.setNombre(tipoAnticipo.getNombre().toUpperCase());
            tipoAnticipoDao.actualizar(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este metodo procesa la eliminación lógica de un tipoAnticipo
     *
     * @param tipoAnticipo registro a eliminar
     * @throws ServicioException excepcion a nivel de negocio
     */
    public void eliminarTipoAnticipo(final TipoAnticipo tipoAnticipo) throws ServicioException {
        try {
            tipoAnticipo.setVigente(Boolean.FALSE);
            tipoAnticipoDao.actualizar(tipoAnticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los tipoAnticipos vigentes
     *
     * @return List<TipoAnticipo> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<TipoAnticipo> listarTodosTipoAnticiposVigentes() throws ServicioException {
        try {
            return tipoAnticipoDao.buscarVigente();
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nombre campo a buscar
     * @return lista obtenida
     * @throws ServicioException excepcion a nivel de negocio
     */
    public List<TipoAnticipo> listarTodasTipoAnticiposPorNombre(final String nombre) throws ServicioException {
        try {
            List<TipoAnticipo> lista;
            if (nombre == null || nombre.isEmpty()) {
                lista = tipoAnticipoDao.buscarVigente();
            } else {
                lista = tipoAnticipoDao.buscarTodosPorNombre(nombre.toUpperCase());
            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }

    }

    /**
     * Este método permite listar los registros de tipoAnticipos por codigo.
     *
     * @param codigo String del tipoAnticipo del tipoAnticipo para la busqueda
     * @return lista detipoAnticipos recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<TipoAnticipo> listarTodosTipoAnticipoPorCodigo(final String codigo) throws ServicioException {
        try {
            List<TipoAnticipo> listaTipoAnticipo;
            listaTipoAnticipo = tipoAnticipoDao.buscarTodosPorCodigo(codigo.toUpperCase());
            return listaTipoAnticipo;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los tipoAnticipos vigentes de un
     * regimen laboral
     *
     * @param idRegimenLaboral Long id de regimen laboral
     * @return List<TipoAnticipo> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<TipoAnticipo> listarTodosTipoAnticiposPorRegimenLaboral(final Long idRegimenLaboral) throws ServicioException {
        try {
            return tipoAnticipoDao.buscarPorRegimenLaboral(idRegimenLaboral);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de un anticipo
     *
     * @param anticipo registro a crear en el sistema
     * @param listaCuotas
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAnticipo(Anticipo anticipo, final List<AnticipoPlanPago> listaCuotas) throws ServicioException {
        try {
            if (anticipo.getId() == null) {
                anticipo.setNumero(
                        admServicio.generarNumeroTramite(anticipo.getInstitucionEjercicioFiscal(), anticipo.getInstitucionEjercicioFiscal().getId()));
                anticipo.setSaldo(anticipo.getValor());
                anticipo = anticipoDao.crear(anticipo);
                anticipoDao.flush();
                for (AnticipoPlanPago plan : listaCuotas) {
                    plan.setAnticipoId(anticipo.getId());
                    plan.setAnticipo(anticipo);
                    guardarAnticipoPlanPago(plan);
                }
            } else {
                LOG.log(Level.INFO, "Registro Anticipo no es nuevo {0}", anticipo.getId());
            }
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un anticipo
     *
     * @param anticipo registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarAnticipo(final Anticipo anticipo) throws ServicioException {
        try {
            BigDecimal valor = anticipo.getValor();
            BigDecimal pagado = calcularSaldoAnticipo(anticipo);
            BigDecimal saldo =  valor.subtract(pagado);
            anticipo.setSaldo(UtilNumeros.redondear(saldo, 2, true));
            anticipoDao.actualizar(anticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un anticipo.
     *
     * @param anticipo registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarAnticipo(final Anticipo anticipo) throws ServicioException {
        try {
            anticipo.setVigente(false);
            anticipoDao.actualizar(anticipo);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los anticipos vigentes de un
     * servidor
     *
     * @param idServidor Long id del servidor
     * @return List<Anticipo> listado obtenido
     */
    public List<Anticipo> listarTodosAnticiposPorServidor(final Long idServidor) throws ServicioException {
        try {
            return anticipoDao.buscarPorServidor(idServidor);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los anticipos vigentes de un estado
     * especifico
     *
     * @param estado estado
     * @param institucionEjercicioFiscal
     * @return List<Anticipo> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Anticipo> listarTodosAnticiposPorEstado(final String estado, final Long institucionEjercicioFiscal) throws ServicioException {
        try {
            return anticipoDao.buscarPorEstado(estado, institucionEjercicioFiscal);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite el registro de un anticipoPago
     *
     * @param anticipoPago registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAnticipoPago(final AnticipoPago anticipoPago) throws ServicioException {
        try {
            anticipoPagoDao.crear(anticipoPago);
            actualizarAnticipo(anticipoPago.getAnticipoPlanPago().getAnticipo());
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un anticipoPago
     *
     * @param anticipoPago registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarAnticipoPago(final AnticipoPago anticipoPago) throws ServicioException {
        try {
            anticipoPagoDao.actualizar(anticipoPago);
            actualizarAnticipo(anticipoPago.getAnticipoPlanPago().getAnticipo());
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un anticipoPlanPago
     *
     * @param anticipoPlanPago registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAnticipoPlanPago(final AnticipoPlanPago anticipoPlanPago) throws ServicioException {
        try {
            anticipoPlanPagoDao.crear(anticipoPlanPago);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los AnticipoPlanPago vigentes por
     * id de anticipo
     *
     * @param idAnticipo
     * @return List<AnticipoPlanPago> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<AnticipoPlanPago> listarTodosPlanPagoAnticiposPorAnticipo(final Long idAnticipo)
            throws ServicioException {
        try {
            return anticipoPlanPagoDao.buscarPorAnticipo(idAnticipo);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los AnticipoPago vigentes por id de
     * plan pago del anticipo
     *
     * @param idPlanPagoAnticipo
     * @return List<AnticipoPago> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<AnticipoPago> listarTodosAnticipoPagoPorPlanPago(final Long idPlanPagoAnticipo)
            throws ServicioException {
        try {
            return anticipoPagoDao.buscarPorPlanPago(idPlanPagoAnticipo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los AnticipoPago para aprobar
     *
     * @param idInstitucionEjercicioFiscal
     * @param idUnidadOrganizacional
     * @param estado
     * @param idAprobador
     * @return List<AnticipoVO> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<AnticipoVO> listarListaAprobacionAnticipos(final Long idInstitucionEjercicioFiscal,
            final Long idUnidadOrganizacional, final String estado, final Long idAprobador)
            throws ServicioException {
        try {
            List<AnticipoVO> listaVO = new ArrayList<AnticipoVO>();

            List<Anticipo> lista
                    = anticipoDao.buscarListaAprobacion(idInstitucionEjercicioFiscal, idUnidadOrganizacional, estado, idAprobador);
            for (Anticipo a : lista) {
                AnticipoVO vo = new AnticipoVO();
                BusquedaServidorVO puesto = new BusquedaServidorVO();
                puesto.setIntitucionEjercicioFiscalId(idInstitucionEjercicioFiscal);
                puesto.setUnidadAdministrativaId(idUnidadOrganizacional);
                puesto.setNumeroDocumentoServidor(a.getServidor().getNumeroIdentificacion());
                puesto.setTipoDocumentoServidor(a.getServidor().getTipoIdentificacion());
                List<DistributivoDetalle> listaDD = servidorServicio.buscar(puesto);
                if (!listaDD.isEmpty()) {
                    vo.setDistributivoDetalle(listaDD.get(0));
                }
                vo.setAnticipo(a);
                if (!a.getEstado().equals(EstadoAnticipoEnum.REGISTRADO.getCodigo())
                        && a.getAprobador() != null && !a.getAprobador().getId().equals(idAprobador)) {
                } else {
                    listaVO.add(vo);
                }

            }
            return listaVO;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite actualizar un anticipoPago
     *
     * @param anticipoPlanPago registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarAnticipoPlanPago(final AnticipoPlanPago anticipoPlanPago) throws ServicioException {
        try {
            anticipoPlanPagoDao.actualizar(anticipoPlanPago);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de ejecutar la busqueda de un anticipo
     *
     * @param consultaAnticipoVO objeto que contiene los parametros de busqueda
     * @return List<VistaAnticipo> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<VistaAnticipo> buscar(
            final ConsultaAnticipoVO consultaAnticipoVO) throws ServicioException {
        try {
            return vistaAnticipoDao.buscar(consultaAnticipoVO);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Verifica si una cuota de un anticipo se encuentra cancelada totalmente
     *
     * @param anticipoPlanPagoId
     * @return
     * @throws ServicioException
     */
    public BigDecimal calcularSaldoDeCuota(Long anticipoPlanPagoId) throws ServicioException {
        BigDecimal valor = BigDecimal.ZERO;
        try {
            AnticipoPlanPago app = anticipoPlanPagoDao.buscarPorId(anticipoPlanPagoId);
            valor = app.getMonto();
            valor = valor.subtract(anticipoPagoDao.buscarTotalPagosDeCuota(anticipoPlanPagoId));
        } catch (Exception e) {
            throw new ServicioException(e);
        }
        return UtilNumeros.redondear(valor, 2, true);

    }
    
    /**
     * Calcula el total pagado para una solicitud de anticipo
     * @param anticipo
     * @return
     * @throws ServicioException 
     */
    public BigDecimal calcularSaldoAnticipo(final Anticipo anticipo) throws ServicioException{
        try {
            return anticipoPagoDao.buscarTotalPagosAnticipo(anticipo.getId());
        } catch (DaoException ex) {
            Logger.getLogger(AnticipoServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

}
