/*
 *  AlertaControlador.java
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
 *  08/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConfidencialPagoHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 * Controlador para la Administracion de Alertas.
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "confidencialPagoBean")
@ViewScoped
public class ConfidencialPagoControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ConfidencialPagoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/consultas/consulta_confidencial_pago.jsf";
    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_RESULTADO_NOMINA = "/pages/consultas/resultado_nomina_pago.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Dao de nomina.
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;
    /**
     * Dao de nomina.
     */
    @EJB
    private NominaServicio nominaServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{confidencialPagoHelper}")
    private ConfidencialPagoHelper confidencialPagoHelper;
    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;
    /**
     * DAO de Periodo Nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;

    @Override
    @PostConstruct
    public void init() {
        iniciarCatalogos();
        //confidencialPagoHelper.getListaNominas().clear();
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String buscarNominas() {
        try {

//            confidencialPagoHelper.getBusquedaNominaVO().setPeriodoFiscal(
//                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            //USUARIO DE PRUEBA 33161L
            //obtenerUsuarioConectado().getServidor().getId()
            confidencialPagoHelper.getBusquedaNominaVO().setServidorLogueado(obtenerUsuarioConectado().getServidor().getId());
            confidencialPagoHelper.setListaNominas(nominaDetalleDao.
                    buscarPorFiltrosNominaDetalle(confidencialPagoHelper.getBusquedaNominaVO()));
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al buscar nominas por filtro", e);
        }
        return null;
    }

    /**
     * Permite ir a la página para ver los resultados de la Nómina
     *
     * @return
     */
    public String irFormularioResultadoNomina() {
        confidencialPagoHelper.setResultadoNominaVO(new ResultadoNominaVO());
        confidencialPagoHelper.getResultadoNominaVO().setNomina(new Nomina());
        confidencialPagoHelper.getResultadoNominaVO().setNomina(confidencialPagoHelper.getNomina());
        confidencialPagoHelper.getResultadoNominaVO().setNominaDetalle(new NominaDetalle());
        confidencialPagoHelper.getResultadoNominaVO().getNominaDetalle().setNomina(confidencialPagoHelper.getNomina());
        buscar();
        return FORMULARIO_RESULTADO_NOMINA;
    }

    /**
     * Este método procesa la busqueda. Obtiene totales por grupos.
     *
     * @return String
     */
    public String buscar() {
        try {
            confidencialPagoHelper.getListaResultadoNominaVO().clear();
            //cedula de prueba 1715052971    
            confidencialPagoHelper.getResultadoNominaVO().setFiltroNumeroIdentificacion(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
            confidencialPagoHelper.setListaResultadoNominaVO(nominaServicio.listarResultadoNomina(
                    confidencialPagoHelper.getResultadoNominaVO()));
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar los resultados de la nomina.", e);
        }
        return null;
    }

    /**
     * regresar a la nómina.
     *
     * @return
     */
    public String regresarNomina() {
        return LISTA_ENTIDAD;
    }

    /**
     * regresar al portal.
     *
     * @return
     */
    public String irPortal() {
        return LISTA_PORTAL;
    }

    /**
     * Obtiene los detalles de una nomina.
     *
     * @return
     */
    public String buscarDetalles() {
        try {
            confidencialPagoHelper.getListaDetallesIngresos().clear();
            confidencialPagoHelper.getListaDetallesDescuentos().clear();
            confidencialPagoHelper.getListaDetallesAportes().clear();
            confidencialPagoHelper.getResultadoNominaVO().inicializarTotalesServidor();
            List<NominaDetalle> listaDetNomina = nominaServicio.listarPorNominaYServidor(
                    confidencialPagoHelper.getResultadoNominaVO().getNomina().getId(),
                    confidencialPagoHelper.getResultadoNominaVO().getNumeroIdentificacion());
            for (NominaDetalle det : listaDetNomina) {
                if (det.getValorDescontadoRubroIngreso() != null && det.getTipo().equals("SER")) {
                    confidencialPagoHelper.getListaDetallesIngresos().add(det);
                    confidencialPagoHelper.getResultadoNominaVO().setTotalIngresosServidor(
                            confidencialPagoHelper.getResultadoNominaVO().getTotalIngresosServidor().add(det.
                                    getValorDescontadoRubroIngreso()));
                } else if (det.getValorDescontadoRubroDescuentos() != null && det.getTipo().equals("SER")) {
                    confidencialPagoHelper.getListaDetallesDescuentos().add(det);
                    confidencialPagoHelper.getResultadoNominaVO().setTotalDescuentosServidor(
                            confidencialPagoHelper.getResultadoNominaVO().getTotalDescuentosServidor().add(det.
                                    getValorDescontadoRubroDescuentos()));
                } else if (det.getValorDescontadoRubroAportes() != null && det.getTipo().equals("SER")) {
                    confidencialPagoHelper.getListaDetallesAportes().add(det);
                    confidencialPagoHelper.getResultadoNominaVO().setTotalAportePatronalServidor(
                            confidencialPagoHelper.getResultadoNominaVO().getTotalAportePatronalServidor().add(det.
                                    getValorDescontadoRubroAportes()));
                }
            }
            ejecutarComandoPrimefaces("dlgDetalleNomina.show()");
            actualizarComponente(":popDetalles");

        } catch (ServicioException e) {
            error(getClass().getName(), "Error al consultar los detalles de los resultados de la nómina.", e);
        }
        return null;
    }

    /**
     * Vista previa de confidencial de pago.
     */
    public void generarConfidecialPago() {
        setReporte(ReportesEnum.PROTEUS_CONFIDENCIAL_PAGO.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "CONFIDENCIAL DE PAGO");
        parametrosReporte.put("nominaId", confidencialPagoHelper.getNomina().getId().toString());
        parametrosReporte.put("servidorId", obtenerUsuarioConectado().getServidor().getId().toString());
        generarUrlDeReporte();
    }

    /**
     * Este metodo inicia lo catalogos para la nomina.
     */
    private void iniciarCatalogos() {
        try {
            //Periodo fiscal            
            List<InstitucionEjercicioFiscal> ejercicios = institucionEjercicioFiscalDao.buscarTodosPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            confidencialPagoHelper.getListaPeriodoFiscal().clear();
            iniciarCombos(confidencialPagoHelper.getListaPeriodoFiscal());
            for (InstitucionEjercicioFiscal ef : ejercicios) {
                confidencialPagoHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(),
                        ef.getEjercicioFiscal().getNombre()));
            }
            //Periodo nomina
            iniciarCombos(confidencialPagoHelper.getListaPeriodoNomina());

//            List<PeriodoNomina> periodos = periodoNominaDao.buscarVigente();
//            iniciarCombosTodos(confidencialPagoHelper.getListaPeriodoNomina());
//            for (PeriodoNomina periodo : periodos) {
//                confidencialPagoHelper.getListaPeriodoNomina().add(
//                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
//
//            }
        } catch (DaoException e) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos: {0}", e);
        }
    }

    public void buscarPeriodosNominas() {
        try {
            iniciarCombos(confidencialPagoHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos = periodoNominaDao.buscarPorEjercicio(confidencialPagoHelper.getBusquedaNominaVO().getPeriodoFiscal());
            iniciarCombosTodos(confidencialPagoHelper.getListaPeriodoNomina());
            for (PeriodoNomina periodo : periodos) {
                confidencialPagoHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }
        } catch (Exception e) {
            LOG.log(Level.INFO, "Problemas al recuperar periodos de nominas", e);
        }
    }

    /**
     * Imprime reporte de rol de pagos.
     */
    public void imprimir() {
        setReporte(ReportesEnum.PROTEUS_CONFIDENCIAL_PAGO.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "ROL DE PAGOS DEL SERVIDOR");
        parametrosReporte.put("p_ef", obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getEjercicioFiscal().getId().toString());
        parametrosReporte.put("p_ins", obtenerUsuarioConectado().getInstitucion().getId().toString());
        parametrosReporte.put("p_usu", obtenerUsuarioConectado().getServidor().getId().toString());
        parametrosReporte.put("nominaId", confidencialPagoHelper.getNomina().getId().toString());
        generarUrlDeReporte();
    }

    /**
     * MERODO PARA REGRESAR A ALA LISTA.
     *
     * @return
     */
    public String regresarLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoPeriodo(final String codigo) {
        return TipoPeriodoAlertaEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getadministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setadministracionServicio(final AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }

    /**
     * @return the confidencialPagoHelper
     */
    public ConfidencialPagoHelper getConfidencialPagoHelper() {
        return confidencialPagoHelper;
    }

    /**
     * @param confidencialPagoHelper the confidencialPagoHelper to set
     */
    public void setConfidencialPagoHelper(ConfidencialPagoHelper confidencialPagoHelper) {
        this.confidencialPagoHelper = confidencialPagoHelper;
    }
}
