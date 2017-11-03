/*
 *  MovimientoServicio.java
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
 *  09/01/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.vo.ConsultaTramiteVO;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Servicio de Movimiento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class MovimientoServicio extends BaseServicio {

    /**
     * DAO de movimiento.
     */
    @EJB
    private MovimientoDao movimientoDao;

    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Constructor por defecto.
     */
    public MovimientoServicio() {
        super();
    }

    /**
     * Este metodo busca los tramites (Movimientos individuales).
     *
     * @param o ConsultaTramiteVO
     * @param inicio int
     * @param maximo int
     * @return List
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Movimiento> buscarTramites(final ConsultaTramiteVO o,
            final int inicio, final int maximo) throws ServicioException {
        try {
            if (!o.getEsRRHH()) {
                o.setUnidades(desconcentradoServicio.buscarUnidadesDeAcceso(
                        o.getUsuarioVO().getServidor().getId(), FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo()));
            }
            List<Movimiento> lista = movimientoDao.consultaTramite(o, inicio, maximo);
            for (Movimiento m : lista) {
                DistributivoDetalle dd = distributivoPuestoServicio.buscarDistributivoPorServidor(
                        m.getTipoIdentificacion(), m.getNumeroIdentificacion(), o.getInstitucionEjercicioFiscalId());
                if (dd == null) {
                    m.setExisteDistributivo(Boolean.FALSE);
                } else {
                    if (dd.getEstadoPuesto().getOcupado()) {
                        m.setExisteDistributivo(Boolean.TRUE);
                    } else {
                        m.setExisteDistributivo(Boolean.FALSE);
                    }
                }

            }
            return lista;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Método que devuelve el query String para la consulta de trámites.
     *
     * @param o ConsultaTramiteVO
     * @param inicio int
     * @param maximo int
     * @return String
     * @throws DaoException
     */
    public String buscarTramitesString(final ConsultaTramiteVO o) throws DaoException {
        return movimientoDao.consultaTramiteString(o);
    }

    /**
     * Este metodo busca los tramites (Movimientos individuales).
     *
     * @param o ConsultaTramiteVO
     * @return List
     * @throws DaoException COntrol de errores
     */
    public Long contarTramites(final ConsultaTramiteVO o) throws DaoException {
        return movimientoDao.contarTramite(o);
    }

    /**
     * Método q retorna el sql para el reporte.
     *
     * @param usuario String
     * @param codigoInstitucion String
     * @param fechaVigenciaDesde Date
     * @param fechaVigenciaHasta Date
     * @param tipoMovimientoId Long
     * @param codigoFase String
     * @param fechaEstadoDesde Date
     * @param fechaEstadoHasta Date
     * @return
     * @throws DaoException
     */
    public String buscarSqlMovimientoServidor(final String usuario, final String codigoInstitucion,
            final Date fechaVigenciaDesde, final Date fechaVigenciaHasta, final Long tipoMovimientoId,
            final String codigoFase, final Date fechaEstadoDesde, final Date fechaEstadoHasta) throws DaoException {
        return movimientoDao.buscarSqlMovimientoServidor(usuario, codigoInstitucion,
                fechaVigenciaDesde, fechaVigenciaHasta, tipoMovimientoId, codigoFase,
                fechaEstadoDesde, fechaEstadoHasta);
    }

    /**
     * Recupera los movimientos para su finalizacion.
     *
     * @param tipoIdentificacion
     * @param numeroIdentificacion
     * @param tipoMovimientoFinalizacionId
     * @return
     * @throws ServicioException
     */
    public List<Movimiento> buscarMovimientosParaFinalizacion(final String tipoIdentificacion,
            final String numeroIdentificacion, final Long tipoMovimientoFinalizacionId) throws ServicioException {
        try {
            return movimientoDao.buscarParaFinalizacion(tipoIdentificacion, numeroIdentificacion,
                    tipoMovimientoFinalizacionId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo busca los movimientos por Id.
     *
     * @param tipoDocumento
     * @return Movimiento
     * @throws ServicioException ServicioException
     */
    public Movimiento buscarServidorPorTipoDocumento(final String tipoDocumento) throws ServicioException {
        try {
            Movimiento servidor = new Movimiento();
            if (tipoDocumento != null) {
                servidor = movimientoDao.buscarPorTipoIdentificacion(tipoDocumento);
            }
            return servidor;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera los movimientos consulta.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public List<Movimiento> buscarMovimientosPopParametros(final ConsultaTrayectoriaLaboralVO vo) throws ServicioException {
        try {
            return movimientoDao.buscarTrayectoria(vo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }
}
