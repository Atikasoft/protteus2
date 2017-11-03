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
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.DocumentoHabilitanteEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Clase;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.GestionServicio;
import ec.com.atikasoft.proteus.servicio.MovimientoServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.ConsultaTramiteVO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.datatable.DataTable;

/**
 * Controlador de Consulta Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ViewScoped
@ManagedBean(name = "consultaPuestoBean")
public class ConsultaPuestoControlador extends BaseControlador {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaPuestoHelper}")
    private ConsultaPuestoHelper consultaPuestoHelper;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de Movimiento.
     */
    @EJB
    private MovimientoServicio movimientoServicio;

    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private GestionServicio gestionServicio;

    /**
     * Constructor por defecto.
     */
    public ConsultaPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        consultaPuestoHelper.getConsultaTramiteVO().setUsuarioVO(obtenerUsuarioConectado());
        valoresParametrosConsultaTramite();
        if (consultaPuestoHelper.getSeleccionarUnidadOrganizacional()) {
            consultaPuestoHelper.setConsultaTramiteVO(new ConsultaTramiteVO());
        } else {
            limpiarDatosConsulta();
        }
        consultaPuestoHelper.setListaMovimientos(null);
//        consultaPuestoHelper.getConsultaTramiteVO().setCodigoInstitucion(obtenerUsuarioConectado().getIdInstitucion());
        try {
            iniciarCombosConsulta(consultaPuestoHelper.getEstadosTramite());
            for (EstadosTramiteEnum et : EstadosTramiteEnum.values()) {
                consultaPuestoHelper.getEstadosTramite().add(
                        new SelectItem(et.getCodigo(), et.getDescripcion()));
            }
            iniciarCombos(consultaPuestoHelper.getPartidaPresupuestariaGeneral());
//            List<RegimenPersonalDatos> partias = catalogoServicio.buscarRegimenLaboralDatosPorInstituion(
//                    obtenerUsuarioLogeado().getInstitucionId());
//
//            for (RegimenPersonalDatos p : partias) {
//                consultaPuestoHelper.getPartidaPresupuestariaGeneral().add(
//                        new SelectItem(p.getPartidaPresupuestaria(), p.getPartidaPresupuestaria()));
//            }

            iniciarCombos(consultaPuestoHelper.getTipoIdentificacion());
            for (TipoDocumentoEnum et : TipoDocumentoEnum.values()) {
                consultaPuestoHelper.getTipoIdentificacion().add(
                        new SelectItem(et.getNemonico(), et.getNombre()));
            }

            iniciarCombosConsulta(consultaPuestoHelper.getTiposMovimientos());
            List<TipoMovimiento> listaTipoMovimiento = admServicio.listarTiposMovimientosActivos();
            for (TipoMovimiento c : listaTipoMovimiento) {
                consultaPuestoHelper.getTiposMovimientos().add(
                        new SelectItem(c.getId(), UtilCadena.concatenar(
                                        c.getClase().getGrupo().getNombre() + '/' + c.getClase().getNombre()
                                        + '/' + c.getNombre()), c.getDescripcion()));
            }
            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = admServicio.listarUnidadOrganizacionalVigente();
            consultaPuestoHelper.setListaUnidadesOrganizacionales(unidades);

            /**
             * Filtros para tipo de movimiento
             */
            consultaPuestoHelper.getConsultaTramiteVO().setGrupo(null);
            consultaPuestoHelper.getConsultaTramiteVO().setClase(null);
            cargarGrupos();
            consultaPuestoHelper.getConsultaTramiteVO().setInstitucionEjercicioFiscalId(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

            // verifica si el usuario conectado es de RRHH
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            consultaPuestoHelper.getConsultaTramiteVO().setEsRRHH(esRRHH(pi.getValorTexto()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar la consulta.", e);
        }
    }

    /**
     * limpiar los parametros de busy¡ueda para cuando no es servidor de recursos humanos
     */
    public void limpiarDatosConsulta() {
        consultaPuestoHelper.getConsultaTramiteVO().setTipoMovimiento(null);
        consultaPuestoHelper.getConsultaTramiteVO().setCodigoPuesto(null);
//        consultaPuestoHelper.getConsultaTramiteVO().setUnidadAdministrativaNombre(null);
//        consultaPuestoHelper.getConsultaTramiteVO().setUnidadAdministrativaId(0L);
        consultaPuestoHelper.getConsultaTramiteVO().setPartidaPresupuestariaIndividual("");
        consultaPuestoHelper.getConsultaTramiteVO().setNumeroTramite("");
        consultaPuestoHelper.getConsultaTramiteVO().setNumeroDocumentoHabilitante("");
        consultaPuestoHelper.getConsultaTramiteVO().setEstado("");
        consultaPuestoHelper.getConsultaTramiteVO().setFechaElaboracionDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaElaboracionHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaRevisionDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaRevisionHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaValidacionDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaValidacionHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaAprobacionDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaAprobacionHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaLegalizacionDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaLegalizacionHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaVigenciaDesde(null);
        consultaPuestoHelper.getConsultaTramiteVO().setFechaVigenciaHasta(null);
        consultaPuestoHelper.getConsultaTramiteVO().setTipoIdentificacion(null);
        consultaPuestoHelper.getConsultaTramiteVO().setNumeroIdentificacion(null);
        consultaPuestoHelper.getConsultaTramiteVO().setNombres(null);

    }

    private void cargarGrupos() {
        try {
            consultaPuestoHelper.getConsultaTramiteVO().setGrupo(null);
            consultaPuestoHelper.getConsultaTramiteVO().setClase(null);
            iniciarCombosConsulta(consultaPuestoHelper.getGrupos());
            List<Grupo> grupos = admServicio.listarTodosVigente();
            for (Grupo g : grupos) {
                consultaPuestoHelper.getGrupos().add(new SelectItem(g.getId(), g.getNombre()));
            }

            cargarClases();
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaPuestoAnalistaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void cargarClases() {
        try {
            consultaPuestoHelper.getConsultaTramiteVO().setClase(null);
            iniciarCombosConsulta(consultaPuestoHelper.getClases());
            Long idGrupo = consultaPuestoHelper.getConsultaTramiteVO().getGrupo();
            List<Clase> clases = idGrupo == null ? admServicio.listarTodosClase()
                    : admServicio.listarClasePorGrupoId(idGrupo);
            for (Clase c : clases) {
                consultaPuestoHelper.getClases().add(new SelectItem(c.getId(), c.getNombre()));
            }
            cargarTiposMovimientos();
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaPuestoAnalistaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void cargarTiposMovimientos() {
        try {
            iniciarCombosConsulta(consultaPuestoHelper.getTiposMovimientos());
            Long idClase = consultaPuestoHelper.getConsultaTramiteVO().getClase();
            Long idGrupo = consultaPuestoHelper.getConsultaTramiteVO().getGrupo();

            List<TipoMovimiento> listaTipoMovimiento = idClase == null
                    ? idGrupo == null
                            ? admServicio.listarTiposMovimientosActivos()
                            : admServicio.listarTipoMovimientoActivosPorGrupo(idGrupo)
                    : admServicio.listarTipoMovimientoActivosPorClase(idClase);
            for (TipoMovimiento tm : listaTipoMovimiento) {
                consultaPuestoHelper.getTiposMovimientos().add(new SelectItem(tm.getId(), tm.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ConsultaPuestoAnalistaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * enviar el parametro de la unidad organizacional para la busqueda de tramires.
     *
     */
    private void valoresParametrosConsultaTramite() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().
                            getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            if (esRRHH(pi.getValorTexto())) {
                consultaPuestoHelper.setSeleccionarUnidadOrganizacional(Boolean.TRUE);
            } else {
                consultaPuestoHelper.setSeleccionarUnidadOrganizacional(Boolean.FALSE);
                consultaPuestoHelper.getConsultaTramiteVO().setUnidadAdministrativaId(obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
                consultaPuestoHelper.getConsultaTramiteVO().setUnidadAdministrativaNombre(UtilCadena.concatenar(
                        obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                        getUnidadOrganizacional().getRuta(), " / ", obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta()));
            }
            actualizarComponente(":frmUnidadAdministrativa:parametrosBusqueda_unidadAdministrativa");
        } catch (Exception e) {
            error(getClass().getName(), "Error al recuperar paramtro institucion del código del RRHH.", e);
        }

    }

    /**
     * Este método busca los movimientos segun los filtros.
     *
     * @return String
     */
    public String buscar() {
        try {
//            consultaPuestoHelper.setListaMovimientos((List<Movimiento>) new ConsultaPuestoPaginador(
//                    consultaPuestoHelper.getConsultaTramiteVO(), movimientoServicio));
//            consultaPuestoHelper.setListaMovimientosSinConteo(movimientoServicio.buscarTramites(consultaPuestoHelper.getConsultaTramiteVO(), 0, 100));
//            System.out.println("lista de la consulta tramites******************"+ consultaPuestoHelper.getListaMovimientosSinConteo().size());
//            consultaPuestoHelper.getConsultaTramiteVO().setTotalRegistros(
//                    movimientoServicio.contarTramites(consultaPuestoHelper.getConsultaTramiteVO()));           
            final DataTable d = (DataTable) FacesContext.getCurrentInstance().
                    getViewRoot().findComponent("frmConsultaMovimientoInsti:tblConsultaMovimientoInsti");

            //d.setFirst(0);
        } catch (Exception e) {
            e.printStackTrace();
            error(this.getClass().getName(), "Error al buscar.", e);
        }
        return null;
    }

    @Override
    public void generarReporte() {
        try {
            String estado = consultaPuestoHelper.getMovimiento().getTramite().getCodigoFase();
            String nemo = consultaPuestoHelper.getMovimiento().getTramite().
                    getTipoMovimiento().getDocumentoHabilitante().getNemonico();
            if (EstadosTramiteEnum.ELABORACION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.REVISION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.VALIDACION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.APROBACION.getCodigo().equals(estado)) {
                vistaPrevia();
            } else if (EstadosTramiteEnum.LEGALIZACION.getCodigo().equals(estado)) {
                //impresionPdf();
                vistaPrevia();
            }
            vistaPrevia();
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "No tiene accion de personal" + e.getMessage(), e);
        }
    }

    /**
     * Este metodo procesa la descarga del archivo.
     */
    public void descargarArchivo() {
        try {
            if (consultaPuestoHelper.getMovimiento() != null && consultaPuestoHelper.getMovimiento().getId() != null) {
                Archivo archivo = consultaPuestoHelper.getMovimiento().getArchivo();
                if (archivo != null) {
                    byte[] buf = archivo.getArchivo();
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                            getExternalContext().getResponse();
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + archivo.getNombre() + "");
                    response.getOutputStream().write(buf);
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    FacesContext.getCurrentInstance().responseComplete();
                }
            }
        } catch (IOException ex) {
            error(getClass().getName(), "Error al obtener el archivo", ex);
            System.out.println("Error : " + ex);
        }
    }

    /**
     * metodo para la vista previa del reporte.
     */
    private void vistaPrevia() {
        if (consultaPuestoHelper.getMovimiento().getTramite().
                getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
            setReporte(ReportesEnum.ACCIONES_PERSONAL.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_mov_id", consultaPuestoHelper.getMovimiento().getId().toString());
//            parametrosReporte.put("p_lugar_conectado",
//                    consultaPuestoHelper.getMovimiento().getCantonCoreNombre());
        }
        if (consultaPuestoHelper.getMovimiento().getTramite().getTipoMovimiento().
                getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_SERVICIOS_OCASIONALES.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_SERVICIOS_OCASIONALES_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    consultaPuestoHelper.getMovimiento().getId().toString());
        }
        if (consultaPuestoHelper.getMovimiento().getTramite().getTipoMovimiento().
                getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
            setReporte(ReportesEnum.MEMORANDO_VISTA_PREVIA.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("__toolbar", "false");
            parametrosReporte.put("p_movimiento_id",
                    consultaPuestoHelper.getMovimiento().getId().toString());
        }
    }

    /**
     * metodo para la impresion a pdf directa del reporte.
     */
    private void impresionPdf() {
        if (consultaPuestoHelper.getMovimiento().getTramite().getTipoMovimiento().
                getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
            setReporte(ReportesEnum.ACCIONES_PERSONAL_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_legalizador", obtenerNombreUsuario());
            parametrosReporte.put("p_mov_id", consultaPuestoHelper.getMovimiento().getId().toString());
//            parametrosReporte.put("p_lugar_conectado",
//                    consultaPuestoHelper.getMovimiento().getCantonCoreNombre());
        }
        if (consultaPuestoHelper.getMovimiento().getTramite().getTipoMovimiento().
                getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.CONTRATOS_SERVICIOS_OCASIONALES.getCodigo())) {
            setReporte(ReportesEnum.CONTRATO_SERVICIOS_OCASIONALES_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_legalizador", obtenerNombreUsuario());

            if (obtenerUsuarioConectado().getDistributivoDetalle().
                    getDenominacionPuesto() != null) {
                parametrosReporte.put("p_cargo_legalizador", obtenerUsuarioConectado().getDistributivoDetalle().
                        getDenominacionPuesto().getNombre());
            } else {
                parametrosReporte.put("p_cargo_legalizador", "****************");

            }
            parametrosReporte.put("p_movimiento_id",
                    consultaPuestoHelper.getMovimiento().getId().toString());
        }
        if (consultaPuestoHelper.getMovimiento().getTramite().getTipoMovimiento().
                getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
            setReporte(ReportesEnum.MEMORANDO_IMPRESION.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id",
                    consultaPuestoHelper.getMovimiento().getId().toString());
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
        actualizarComponente("contenedorResultados");
        ConsultaTramiteVO consultaTramiteVO = consultaPuestoHelper.getConsultaTramiteVO();
        consultaTramiteVO.setCedulaServidor(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
        consultaTramiteVO.setConsultaServidor(Boolean.TRUE);
        String sql = movimientoServicio.buscarTramitesString(consultaTramiteVO);
        generarReporteConsultaMovimientoServidor(sql);
        return null;
    }

    /**
     * Método para generar reporte para la consulta de movimientos.
     */
    public void generarReporteConsultaMovimientoServidor(String sql) {
        try {

            setReporte(ReportesEnum.CONSULTA_MOVIMIENTOS_ANALISTA.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("p_titulo", "CONSULTA TRÁMITES INSTITUCIÓN");
            parametrosReporte.put("p_num_doc_usu_conec",
                    obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
            parametrosReporte.put("p_nombres_usu_conec", obtenerNombreUsuario());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                    getExternalContext().getResponse();

            Cookie cookie = new Cookie("SQL_REPORT_ANALISTA", sql);
            // Expire time. -1 = by end of current session, 0 = immediately
            // expire it, otherwise just the lifetime in seconds.
            cookie.setMaxAge(60);
            cookie.setPath("/proteus-rpt");
            response.addCookie(cookie);
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de trámites "
                    + "UATH" + e.getMessage(), e);
        }
    }

    /**
     * Este metodo limpia los filtros y la tabla.
     *
     * @return null
     */
    public String limpiar() {
        consultaPuestoHelper.setListaMovimientos(null);
        consultaPuestoHelper.setConsultaTramiteVO(new ConsultaTramiteVO());
        return null;
    }

    /**
     * Ver el Historico del Tramite
     */
    public void verDetalleHistorico() {
        try {
            consultaPuestoHelper.getListaDetalles().clear();
            consultaPuestoHelper.setListaDetalles(gestionServicio.listarDetalles(consultaPuestoHelper.getTramite().getId()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al mostrar el historico.", e);
        }
    }

    public void postProcessXLS(Object document) {

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
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
}
