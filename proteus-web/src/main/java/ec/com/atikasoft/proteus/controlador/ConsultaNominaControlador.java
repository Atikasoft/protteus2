/*
 *  ConsultaPuestoControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConsultaPuestoHelper;
import ec.com.atikasoft.proteus.controlador.helper.NominaHelper;
import ec.com.atikasoft.proteus.controlador.helper.NominaNovedadHelper;
import ec.com.atikasoft.proteus.controlador.helper.ResultadoNominaHelper;
import ec.com.atikasoft.proteus.controlador.nomina.NominaControlador;
import static ec.com.atikasoft.proteus.controlador.nomina.NominaControlador.FORMULARIO_RESULTADO_NOMINA;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.ModalidadLaboralDao;
import ec.com.atikasoft.proteus.dao.NivelOcupacionalDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.dao.RegimenLaboralDao;
import ec.com.atikasoft.proteus.dao.TipoNominaDao;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.ConsultaNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadoNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.ConsultaTramiteVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Controlador de Consulta Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ViewScoped
@ManagedBean(name = "consultaNominaBean")
public class ConsultaNominaControlador extends BaseControlador {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaPuestoHelper}")
    private ConsultaPuestoHelper consultaPuestoHelper;

    /**
     * Regla de navegacion.
     */
    public static final String CONSULTA_NOMINA = "/pages/consultas/consulta_nominas.jsf";
    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(NominaControlador.class.getCanonicalName());
    /**
     * Regla de navegacion.
     */
    public static final String LISTA_NOMINA = "/pages/procesos/nomina/lista_nominas.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String BUSQUEDA_NOMINA = "pages/consultas/consulta_nominas.jsf";
    /**
     * Helper Nomina.
     */
    @ManagedProperty("#{nominaHelper}")
    private NominaHelper nominaHelper;

    /**
     * Helper de resultado de la nomina.
     */
    @ManagedProperty("#{resultadoNominaHelper}")
    private ResultadoNominaHelper resultadoNominaHelper;

    /**
     * Helper de clase de novedades.
     */
    @ManagedProperty("#{nominaNovedadHelper}")
    private NominaNovedadHelper nominaNovedadHelper;

    /**
     * DAO de Periodo Nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * Dao de nomina.
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * DAO de Tipo nomina.
     */
    @EJB
    private TipoNominaDao tipoNominaDao;
    /**
     * Servidcio de Tipo nomina.
     */
    @EJB
    private NominaServicio nominaServicio;

    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     * Constructor por defecto.
     */
    public ConsultaNominaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        valoresParametrosConsultaNomina();
        getNominaHelper().getBusquedaNominaVO().setPeriodoFiscal(null);
        getNominaHelper().getBusquedaNominaVO().setPeriodoNomina(null);
        getNominaHelper().getBusquedaNominaVO().setTipoNomina(null);
        getNominaHelper().getBusquedaNominaVO().setNumeroNomina(null);
        getNominaHelper().getBusquedaNominaVO().setDescripcionNomina(null);
        getNominaHelper().setNominaEnumCodigo("");
        iniciarCombos(getNominaHelper().getListaTipoNominaEnum());
        iniciarCatalogos();
        iniciarComboTipo();
        nominaHelper.getListaNominas().clear();

    }

    /**
     * Este metodo inicia lo catalogos para la nomina.
     */
    private void iniciarCatalogos() {
        try {
            //Periodo fiscal            
            List<InstitucionEjercicioFiscal> ejercicios = institucionEjercicioFiscalDao.buscarPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId());
            nominaHelper.getListaPeriodoFiscal().clear();
            for (InstitucionEjercicioFiscal ef : ejercicios) {
                nominaHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(), ef.getEjercicioFiscal().getNombre()));
            }
            //Periodo nomina
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos;
            if (nominaHelper.getFiltro().getPeriodoFiscal() == null) {
                periodos = periodoNominaDao.buscarPorEjercicio(ejercicios.get(0).getId());
            } else {
                periodos = periodoNominaDao.buscarPorEjercicio(nominaHelper.
                        getFiltro().getPeriodoFiscal());
            }
            for (PeriodoNomina periodo : periodos) {
                nominaHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }
            //Iniciar combo estado nomina.
            iniciarCombosConsulta(nominaHelper.getListaEstadoNomina());
            for (EstadoNominaEnum et : EstadoNominaEnum.values()) {
                nominaHelper.getListaEstadoNomina().add(new SelectItem(et.getCodigo(), et.getDescripcion()));
            }
            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = admServicio.listarUnidadOrganizacionalVigente();
            nominaHelper.setListaUnidadesOrganizacionales(unidades);

            //Tipo Nomina
            List<TipoNomina> tiposNomina = tipoNominaDao.buscarVigente();

            if (nominaHelper.getEsNuevo() != null && nominaHelper.getEsNuevo()) {
                iniciarCombos(nominaHelper.getListaTipoNomina());
            } else {
                iniciarCombosTodos(nominaHelper.getListaTipoNomina());
            }
            for (TipoNomina tn : tiposNomina) {
                if (tn.getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
                    nominaHelper.getListaTipoNomina().add(new SelectItem(tn.getId(), tn.getNombre()));
                }
            }
            nominaHelper.getListaMensajes().clear();

        } catch (DaoException e) {
            LOG.log(Level.INFO, "Problemas al iniciar catalogos: {0}", e);
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaNominaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void buscarPeriodos() {
        try {
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            List<PeriodoNomina> periodos = periodoNominaDao.buscarPorEjercicio(nominaHelper.getFiltro().getPeriodoFiscal());
            iniciarCombosTodos(nominaHelper.getListaPeriodoNomina());
            for (PeriodoNomina periodo : periodos) {
                nominaHelper.getListaPeriodoNomina().add(
                        new SelectItem(periodo.getId(), UtilCadena.concatenar(periodo.getNombre())));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de periodos", e);
        }
    }

    /**
     * enviar el parametro de la unidad organizacional para la busqueda de
     * tramires.
     *
     */
    private void valoresParametrosConsultaNomina() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().getInstitucion().
                            getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            if (esRRHH(pi.getValorTexto())) {
                nominaHelper.setSeleccionarUnidadOrganizacional(Boolean.TRUE);
            } else {
                nominaHelper.setSeleccionarUnidadOrganizacional(Boolean.FALSE);
                getNominaHelper().getBusquedaNominaVO().setUnidadAdministrativaId(obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
                getNominaHelper().getBusquedaNominaVO().setUnidadAdministrativaNombre(obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
            }
            actualizarComponente(":frmUnidadAdministrativa:parametrosBusqueda_unidadAdministrativa");
        } catch (Exception e) {
            error(getClass().getName(), "Error al recuperar paramtro institucion del código del RRHH.", e);
        }

    }

    /**
     * Permite ir a la página para ver los resultados de la Nómina
     *
     * @return
     */
    public String irFormularioResultadoNomina() {
        getResultadoNominaHelper().setResultadoNomina(new ResultadoNominaVO());
        getResultadoNominaHelper().getResultadoNomina().setNomina(new Nomina());
        getResultadoNominaHelper().getResultadoNomina().setNomina(nominaHelper.getNomina());
        getResultadoNominaHelper().getResultadoNomina().setNominaDetalle(new NominaDetalle());
        getResultadoNominaHelper().getResultadoNomina().getNominaDetalle().setNomina(nominaHelper.getNomina());
//        buscar();
        return FORMULARIO_RESULTADO_NOMINA;
    }

    /**
     * Este metodo lista la nomina.
     *
     * @return String
     */
    public String buscarNominas() {
        try {
//            nominaHelper.setPresentarRegresar(Boolean.TRUE);
            getNominaHelper().getListaNominas().clear();
//            iniciarComboTipo();
            getNominaHelper().getBusquedaNominaVO().setPeriodoFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().
                    getId());
            getNominaHelper().setListaNominas(nominaDao.buscarPorFiltrosNomina(getNominaHelper().getBusquedaNominaVO()));
        } catch (DaoException e) {
            error(this.getClass().getName(), "Error al buscar nominas por filtro", e);
        }
        return null;
    }

    /**
     * recoorrer la lista de nominas para ver que rporte quiero presentar
     */
    public void asignarNominaCodigo() {
        for (Nomina n : nominaHelper.getListaNominas()) {
            nominaHelper.setNominaEnumCodigo(nominaHelper.getNominaEnumCodigo());
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        nominaHelper.getListaTipoNominaEnum().clear();
        iniciarCombos(nominaHelper.getListaTipoNominaEnum());
        for (ConsultaNominaEnum t : ConsultaNominaEnum.values()) {
            nominaHelper.getListaTipoNominaEnum().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    @Override
    public void generarReporte() {
        if (nominaHelper.getNominaEnumCodigo() != null) {
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.NOMINA.getReporte())) {
                setReporte(ReportesEnum.NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE DE NOMINA");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.TOTAL_NOMINA.getReporte())) {
                setReporte(ReportesEnum.TOTAL_NOMINA.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "DETALLE ROL DE PAGOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_DESCUENTOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_DESCUENTOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE DESCUENTOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE PAGOS POR DIRECCIONES");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_DE_RETENCIONES.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_DE_RETENCIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN DE RETENCIONES");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_POR_RUBROS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_POR_RUBROS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN POR RUBROS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN ROL DE PAGOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS.getReporte())) {
                setReporte(ReportesEnum.PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "REPORTE RESUMEN POR INGRESOS/DESCUENTOS");
                parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
            }
            /* if (nominaHelper.getNominaEnumCodigo().equals(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS_POR_UNIDAD_ORG.getReporte())) {
             setReporte(ReportesEnum.PROTEUS_RESUMEN_ROL_DE_PAGOS_POR_UNIDAD_ORG.getReporte());
             parametrosReporte = new HashMap<String, String>();
             parametrosReporte.put("p_titulo", "REPORTE RESUMEN ROL DE PAGOS POR UNIDAD ORGANIZACIONAL");
             parametrosReporte.put("nominaId", nominaHelper.getNomina().getId().toString());
             }*/
            generarUrlDeReporte();
        } else {
            mostrarMensajeEnPantalla("No se a seleccionado un reporte", FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * Método imprimir.
     *
     * @return String
     * @throws DaoException
     */
    public String imprimir() throws DaoException {
        // buscar();
        actualizarComponente("tblListaNominas");
        BusquedaNominaVO busquedaNominaVO = getNominaHelper().getBusquedaNominaVO();
//            busquedaNominaVO.setPeriodoFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().
//                    getId());
//        busquedaNominaVO.setCedulaServidor(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
//        busquedaNominaVO.setConsul(Boolean.TRUE);
        String sql = nominaServicio.buscarNominaString(busquedaNominaVO);
        System.out.println("imprimir la consulta que se envia desde el controlador********************" + sql);
        generarReporteConsultaMovimientoServidor(sql);
        return null;
    }

    /**
     * Método para generar reporte para la consulta de movimientos.
     */
    public void generarReporteConsultaMovimientoServidor(String sql) {
        try {

            setReporte(ReportesEnum.PROTEUS_CONSULTA_NOMINA.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("p_titulo", "CONSULTA NOMINAS");
//            parametrosReporte.put("p_num_doc_usu_conec",
//                    obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
//            parametrosReporte.put("p_nombres_usu_conec", obtenerNombreUsuario());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                    getExternalContext().getResponse();

            Cookie cookie = new Cookie("SQL_REPORT_NOMINA", sql);
            // Expire time. -1 = by end of current session, 0 = immediately
            // expire it, otherwise just the lifetime in seconds.
            cookie.setMaxAge(60);
            cookie.setPath("/proteus-rpt");
            response.addCookie(cookie);
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de nominas "
                    + "UATH" + e.getMessage(), e);
        }
    }

    /**
     * @return the consultaPuestoHelper
     */
    public ConsultaPuestoHelper getConsultaPuestoHelper() {
        return consultaPuestoHelper;
    }

    /**
     * @param consultaPuestoHelper the consultaPuestoHelper to set
     */
    public void setConsultaPuestoHelper(final ConsultaPuestoHelper consultaPuestoHelper) {
        this.consultaPuestoHelper = consultaPuestoHelper;
    }

    /**
     * @return the nominaHelper
     */
    public NominaHelper getNominaHelper() {
        return nominaHelper;
    }

    /**
     * @param nominaHelper the nominaHelper to set
     */
    public void setNominaHelper(NominaHelper nominaHelper) {
        this.nominaHelper = nominaHelper;
    }

    /**
     * @return the resultadoNominaHelper
     */
    public ResultadoNominaHelper getResultadoNominaHelper() {
        return resultadoNominaHelper;
    }

    /**
     * @param resultadoNominaHelper the resultadoNominaHelper to set
     */
    public void setResultadoNominaHelper(ResultadoNominaHelper resultadoNominaHelper) {
        this.resultadoNominaHelper = resultadoNominaHelper;
    }

    /**
     * @return the nominaNovedadHelper
     */
    public NominaNovedadHelper getNominaNovedadHelper() {
        return nominaNovedadHelper;
    }

    /**
     * @param nominaNovedadHelper the nominaNovedadHelper to set
     */
    public void setNominaNovedadHelper(NominaNovedadHelper nominaNovedadHelper) {
        this.nominaNovedadHelper = nominaNovedadHelper;
    }
}
