/*
 *  MovimientoControlador.java
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
 *  07/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.MovimientoHelper;
import ec.com.atikasoft.proteus.enums.DocumentoHabilitanteEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Fase;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.GestionServicio;
import ec.com.atikasoft.proteus.servicio.MovimientoServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import java.io.IOException;
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
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "movimientoBean")
@ViewScoped
public class MovimientoControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(MovimientoControlador.class.getCanonicalName());

    /**
     * clse servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Movimiento servicio.
     */
    @EJB
    private MovimientoServicio movimientoServicio;

    /**
     * Tramite servicio.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private GestionServicio gestionServicio;

    /**
     * helper de la clase.
     */
    @ManagedProperty("#{movimientoHelper}")
    private MovimientoHelper movimientoHelper;

    /**
     * Url de la pagina.
     */
    public static final String LISTA_ENTIDAD = "/pages/consultas/consulta_historial _movimientos_servidor.jsf";

    @Override
    @PostConstruct
    public void init() {
        llenarComboTipoMovimiento();
        llenarcomboEstadoTramite();
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboTipoMovimiento() {
        try {
            iniciarCombosConsulta(movimientoHelper.getListaTipoMovimiento());
            List<TipoMovimiento> listaTipoMovimiento
                    = administracionServicio.listarTiposMovimientos();
            for (TipoMovimiento g : listaTipoMovimiento) {
                movimientoHelper.getListaTipoMovimiento().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (Exception ex) {
            Logger.getLogger(MovimientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para llenar el estado del trámite.
     */
    public void llenarcomboEstadoTramite() {
        try {
            iniciarCombosConsulta(movimientoHelper.getListaFase());
            List<Fase> listaFase = gestionServicio.buscarFases();
            for (Fase fase : listaFase) {
                movimientoHelper.getListaFase().add(new SelectItem(fase.getCodigo(), fase.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(MovimientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo procesa la descarga del archivo.
     */
    public void descargarArchivo() {
        try {
            if (movimientoHelper.getMovimiento() != null && movimientoHelper.getMovimiento().getId() != null) {
                Archivo archivo = movimientoHelper.getMovimiento().getArchivo();
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
     * metodo para limpiar los campos de busqueda.
     */
    public void limpiarCamposBusqueda() {
        movimientoHelper.setFechaVigenteDesde(null);
        movimientoHelper.setFechaVigenteHasta(null);
        movimientoHelper.setNumeroTramites(0);
        movimientoHelper.setTipoMovimientoId(new Long(0));
        movimientoHelper.getListaMovimientos().clear();
        movimientoHelper.setCodigoFase(null);
        llenarcomboEstadoTramite();
    }

    /**
     * metodo de busqueda de movimientos por los filtros.
     *
     * @return String un string
     */
    public String buscar() {
        try {
            Boolean estado = Boolean.TRUE;
            String bundlekey = "";
//            if (movimientoHelper.getFechaVigenteDesde() == null
//                    && movimientoHelper.getFechaVigenteHasta() ==
//            null && movimientoHelper.getTipoMovimientoId() == null) {
//                estado = Boolean.FALSE;
//                bundlekey = COMPARAR_CAMPOS_BUSQUEDA;
//            }
            if (movimientoHelper.getFechaVigenteDesde() != null
                    && movimientoHelper.getFechaVigenteHasta() != null) {
                long f1 = movimientoHelper.getFechaVigenteDesde().getTime();
                long f2 = movimientoHelper.getFechaVigenteHasta().getTime();
                if (f1 > f2) {
                    estado = Boolean.FALSE;
                    bundlekey = COMPARAR_FECHA;
                }
            }
            if (estado) {
//                System.out.println("entra a imprimir controlador");
//                movimientoHelper.getListaMovimientos().clear();
//                movimientoHelper.setListaMovimientos(
//                        administracionServicio.listarMovimientoPorFiltros(obtenerCedulaUsuario(),
//                        obtenerInstitucion().getCodigoCatastro(),
//                        movimientoHelper.getFechaVigenteDesde(),
//                        movimientoHelper.getFechaVigenteHasta(),
//                        movimientoHelper.getTipoMovimientoId(),
//                        movimientoHelper.getCodigoFase(),
//                        movimientoHelper.getFechaEstadoDesde(),
//                        movimientoHelper.getFechaEstadoHasta()));
//                movimientoHelper.setNumeroTramites(movimientoHelper.getListaMovimientos().size());
//                System.out.println("cedula del usuario:----" + obtenerCedulaUsuario());
            } else {
                mostrarMensajeEnPantalla(bundlekey, FacesMessage.SEVERITY_WARN);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    public String imprimir() throws DaoException {
        buscar();
        actualizarComponente("listaMovimientos, panelBusqueda");
//        String sql = movimientoServicio.buscarSqlMovimientoServidor(obtenerCedulaUsuario(),
//                obtenerInstitucion().getCodigoCatastro(),
//                movimientoHelper.getFechaVigenteDesde(),
//                movimientoHelper.getFechaVigenteHasta(),
//                movimientoHelper.getTipoMovimientoId(),
//                movimientoHelper.getCodigoFase(),
//                movimientoHelper.getFechaEstadoDesde(),
//                movimientoHelper.getFechaEstadoHasta());
//        generarReporteConsultaMovimientoServidor(sql);
        return null;
    }

    @Override
    public void generarReporte() {
        try {
            String estado = movimientoHelper.getMovimiento().getTramite().getCodigoFase();
            String nemo = movimientoHelper.getMovimiento().getTramite().
                    getTipoMovimiento().getDocumentoHabilitante().getNemonico();
            LOG.info("ESTADO:" + estado + "NEMONICO:" + nemo);
            if (EstadosTramiteEnum.REGISTRADO.getCodigo().equals(estado)) {
                impresionPdf();
            }
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "No tiene accion de personal" + e.getMessage(), e);
        }
    }

    /**
     * Método para generar reporte para la consulta de movimientos.
     */
    public void generarReporteConsultaMovimientoServidor(String sql) {
        try {

            setReporte(ReportesEnum.CONSULTA_HISTORIAL_MOVIMIENTOS_SERVIDOR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("p_titulo", "HISTORIAL MOVIMIENTO SERVIDOR");
            parametrosReporte.put("p_num_doc_usu_conec",
                    obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
            parametrosReporte.put("p_nombres_usu_conec", obtenerNombreUsuario());
//            parametrosReporte.put("p_tipo_doc_usu_conec", obtenerUsuarioConectado().getServidor().getTipoDocumento().
//                    getNombre());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                    getExternalContext().getResponse();
            Cookie cookie = new Cookie("SQL_REPORT", sql);
            // Expire time. -1 = by end of current session, 0 = immediately expire it, otherwise just the lifetime in seconds.
            cookie.setMaxAge(60);
            cookie.setPath("/birt");
            response.addCookie(cookie);
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de historial "
                    + "de movimientos de servidor" + e.getMessage(), e);
        }
    }

    /**
     * metodo para la impresion a pdf directa del reporte.
     */
    private void impresionPdf() {
        if (movimientoHelper.getMovimiento().getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
            setReporte(ReportesEnum.ACCIONES_PERSONAL_IMPR.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_mov_id", movimientoHelper.getMovimiento().getId().toString());
            parametrosReporte.put("p_legalizador", obtenerNombreUsuario());
//            parametrosReporte.put("p_lugar_conectado",
//                    movimientoHelper.getMovimiento().getCantonCoreNombre());
        }
        if (movimientoHelper.getMovimiento().getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
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
                parametrosReporte.put("p_cargo_legalizador", "**************");
            }
            parametrosReporte.put("p_movimiento_id",
                    movimientoHelper.getMovimiento().getId().toString());
        }
        if (movimientoHelper.getMovimiento().getTramite().getTipoMovimiento().getDocumentoHabilitante().getNemonico().
                equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
            setReporte(ReportesEnum.MEMORANDO_IMPRESION.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("__format", "pdf");
            parametrosReporte.put("p_movimiento_id",
                    movimientoHelper.getMovimiento().getId().toString());
        }
    }

    /**
     * @return the movimientoHelper
     */
    public MovimientoHelper getMovimientoHelper() {
        return movimientoHelper;
    }

    /**
     * @param movimientoHelper the movimientoHelper to set
     */
    public void setMovimientoHelper(final MovimientoHelper movimientoHelper) {
        this.movimientoHelper = movimientoHelper;
    }

    /**
     * @return the tramiteServicio
     */
    public TramiteServicio getTramiteServicio() {
        return tramiteServicio;
    }

    /**
     * @param tramiteServicio the tramiteServicio to set
     */
    public void setTramiteServicio(final TramiteServicio tramiteServicio) {
        this.tramiteServicio = tramiteServicio;
    }

    /**
     * @return the gestionServicio
     */
    public GestionServicio getGestionServicio() {
        return gestionServicio;
    }

    /**
     * @param gestionServicio the gestionServicio to set
     */
    public void setGestionServicio(final GestionServicio gestionServicio) {
        this.gestionServicio = gestionServicio;
    }
}
