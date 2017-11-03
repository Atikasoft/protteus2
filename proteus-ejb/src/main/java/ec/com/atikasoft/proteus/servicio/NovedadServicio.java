/*
 *  NovedadServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Dec 8, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.NovedadDao;
import ec.com.atikasoft.proteus.dao.NovedadDetalleDao;
import ec.com.atikasoft.proteus.dao.VistaNovedadDao;
import ec.com.atikasoft.proteus.enums.ErrorCargaServidorNovedadEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.Novedad;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.vistas.VistaNovedad;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.BusquedaNovedadVO;
import ec.com.atikasoft.proteus.vo.ConsultaNovedadVO;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class NovedadServicio extends BaseServicio {

    /**
     * Dao de detalles de la novedades.
     */
    @EJB
    private NovedadDetalleDao notaDetalleDao;

    /**
     * Dao de detalles de la vista novedades.
     */
    @EJB
    private VistaNovedadDao vistaNovedadDao;

    /**
     * Dao de detalles de la vista novedades.
     */
    @EJB
    private NovedadDao novedadDao;

    /**
     *
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    /**
     *
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Constructor sin argumentos.
     */
    public NovedadServicio() {
        super();
    }

    /**
     *
     * @param institucionEjercicioFiscalId
     * @param nominaId
     * @param datoAdicionalId
     * @param servidorId
     * @param novedadDetalles
     * @return
     * @throws ServicioException
     */
    public BigDecimal calcular(final Long institucionEjercicioFiscalId, final Long nominaId, final Long datoAdicionalId,
            final Long servidorId, final List<NovedadDetalle> novedadDetalles) throws ServicioException {
        try {
            BigDecimal valor = BigDecimal.ZERO;
            List<NovedadDetalle> lista = notaDetalleDao.buscarPorServidorNominaDatoAdicional(
                    institucionEjercicioFiscalId, nominaId, datoAdicionalId, servidorId);
            for (NovedadDetalle d : lista) {
                valor = valor.add(d.getValor());
                novedadDetalles.add(d);
            }
            return valor;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de ejecutar la busqueda de una novedad
     *
     * @param novedadConsultaVO objeto que contiene los parametros de busqueda
     * @return List<VistaAnticipo> listado obtenido
     */
    public List<VistaNovedad> buscar(
            final ConsultaNovedadVO novedadConsultaVO) throws ServicioException {
        try {
            return vistaNovedadDao.buscar(novedadConsultaVO);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca totales de novedades.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public int contarTotalNovedades(final ConsultaNovedadVO vo) throws ServicioException {
        return vistaNovedadDao.contarVistaNovedad(vo);
    }

    /**
     * Metodo que se encarga de ejecutar la busqueda de una novedad
     *
     * @param novedadConsultaVO
     * @param conDetalles
     * @return List<Novedad> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Novedad> buscar(final BusquedaNovedadVO novedadConsultaVO, Boolean conDetalles)
            throws ServicioException {
        try {
            List<Novedad> novedades = novedadDao.buscar(novedadConsultaVO);
            if (conDetalles) {
                for (Novedad novedad : novedades) {
                    List<NovedadDetalle> dt = novedadDetalleDao.buscarNovedadesDetallesPorNovedadId(novedad.getId());
                    novedad.setListaDetalles(dt);
                    novedad.setTotalRegistros(dt.size());
                    novedad.setTotalValor(BigDecimal.ZERO);
                    for (NovedadDetalle nd : novedad.getListaDetalles()) {
                        novedad.setTotalValor(novedad.getTotalValor().add(nd.getValor()));
                    }
                }
            }
            return novedades;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de ejecutar la busqueda de una novedad
     *
     * @param nominaId
     * @param periodoId
     * @param datoAdicionalId
     * @return List<Novedad> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Novedad> buscarNovedadNominaIdYPeriodoId(
            final Long nominaId, final Long periodoId, final Long datoAdicionalId) throws ServicioException {
        try {
            return novedadDao.buscarNovedadesPorNominaYPeriodoId(nominaId, periodoId, datoAdicionalId);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Valida los datos de un servidor cargados desde un archivo CSV en caso de presentarse errores los coloca en
     * novedad.erroresAlCargarServidor
     *
     * @param nominaId
     * @param servidores
     * @param idInstitucionEjercicioFiscal
     * @param nd
     * @param esRRHH
     * @throws ServicioException
     */
    public void validarServidor(final Nomina nomina, final Long idInstitucionEjercicioFiscal,
            final NovedadDetalle nd, Boolean esRRHH, List<UnidadOrganizacional> unidadesAcceso)
            throws ServicioException, DaoException {
        RegimenLaboral rl = nomina.getTipoNomina().getRegimenLaboral();
        DistributivoDetalle dd = distributivoDetalleServicio.buscarPorServidor(nd.getServidor().getTipoIdentificacion(),
                nd.getServidor().getNumeroIdentificacion(), idInstitucionEjercicioFiscal);
        if (dd == null) {
            nd.getErroresAlCargarServidor().add(
                    ErrorCargaServidorNovedadEnum.NO_EXISTE_EN_DISTRIBUTIVO.getDescripcion());
        } else {
            // validar regimen laboral
            if (rl.getCodigo().equals(dd.getEscalaOcupacional().getNivelOcupacional().
                    getRegimenLaboral().getCodigo())) {
                nd.setServidor(dd.getServidor());
                nd.setServidorId(dd.getServidor().getId());
            } else {
                nd.getErroresAlCargarServidor().add(UtilCadena.concatenar(
                        ErrorCargaServidorNovedadEnum.NO_PERTENECE_REGIMEN_LABORAL.
                        getDescripcion(), "(", rl.getNombre(), ")"));
            }
            // validar valor numerico
            if (!NumberUtils.isNumber(nd.getValorNumericoEnCSV())) {
                nd.getErroresAlCargarServidor().add(ErrorCargaServidorNovedadEnum.VALOR_NUMERICO_INVALIDO.getDescripcion());
            }
            // validar acceso a unidad organizacional
            if (!esRRHH) {
                boolean existe = false;
                for (UnidadOrganizacional uo : unidadesAcceso) {
                    if (uo.getCodigo().equals(dd.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) {
                    nd.getErroresAlCargarServidor().add(
                            ErrorCargaServidorNovedadEnum.NO_PERTENECE_UNIDAD_ORGANIZACIONAL.getDescripcion());
                }
            }
        }
    }

    /**
     *
     * @param novedad
     * @param detalles
     * @param usuario
     * @throws ServicioException
     */
    public void crearNovedad(final Novedad novedad, final List<NovedadDetalle> detalles, final String usuario) throws
            ServicioException {
        try {
            Novedad nueva = novedadDao.crear(novedad);
            novedadDao.flush();
            novedadDao.refresh(nueva);
//            System.out.println(nueva.getId());
            for (NovedadDetalle nd : detalles) {
                nd.setNovedadId(nueva.getId());
                novedadDetalleDao.crear(nd);
            }
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    public List<NovedadDetalle> buscarNovedadPorNominaDatoAdicionalYServidor(final Long institucionEjercicioFiscal,
            final Long idNomina, final Long idDatoAdiconal, long idServidor) throws ServicioException {
        try {
            return novedadDetalleDao.buscarPorServidorNominaDatoAdicional(
                    institucionEjercicioFiscal, idNomina, idDatoAdiconal, idServidor);
        } catch (DaoException ex) {
            Logger.getLogger(NovedadServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
}
