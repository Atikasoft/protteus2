/*
 *  BusquedaPuestoControlador.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaPuestoHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.DefaultStreamedContent;

/**
 * Controlador de Busqueda Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "busquedaPuestoBean")
@ViewScoped
public class BusquedaPuestoControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(BusquedaPuestoControlador.class.getName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/busqueda_puesto.jsf";

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de regimen laboral.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     *
     */
    @EJB
    private TipoMovimientoDao tipoMovimientoDao;

    /**
     *
     */
    @EJB
    UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaPuestoHelper}")
    private BusquedaPuestoHelper busquedaPuestoHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Constructor por defecto.
     */
    public BusquedaPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();
        valoresTipoMovimiento();
        iniciardatosBusqueda();
        busquedaPuestoHelper.setNombreArchivo("Reporte_Distributivo_" + UtilFechas.formatear2(new Date()));
        busquedaPuestoHelper.setTotalRegistros(0L);

    }

    public void iniciardatosBusqueda() {
        //getBusquedaPuestoHelper().getBusquedaPuestoVO().setRegimenLaboralId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setPaisId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setParroquiaId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setPartidaIndividual(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setParroquiaId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setCodigoPuesto(0L);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setUnidadAdministrativaId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setUnidadAdministrativaNombre("");
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setUbicacionGeograficaNombre("");
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setFechaInicio(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setFechaFin(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setRmu(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setRmuSobrevalorado(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setTipoDocumentoServidor("");
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setNumeroDocumentoServidor("");
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setNombreServidor("");
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setSueldoBasico(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setCodigoPuesto(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setCertificacionPresupuestaria("");
        //getBusquedaPuestoHelper().getBusquedaPuestoVO().setEstadoServidorId(null); 
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setDenominacionPuestoId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setNivelOcupacionalId(null);
        getBusquedaPuestoHelper().getBusquedaPuestoVO().setEscalaOcupacionalId(null);
    }

    /**
     * Este método verifica los valores del tipo movimiento para la busqueda de puesto.
     */
    private void valoresTipoMovimiento() {
        try {
            if (tramiteHelper.getTramite().getTipoMovimiento().getId() != null) {
                TipoMovimiento tipoMovimiento = tipoMovimientoDao.buscarPorId(tramiteHelper.getTramite().
                        getTipoMovimiento().getId());
//        busquedaPuestoHelper.getParametrosBusqueda().setNivelOcupacionalId(tipoMovimiento.getNivelOcupacionalCore());
                busquedaPuestoHelper.getBusquedaPuestoVO().setModalidadLaboralId(tipoMovimiento.
                        getModalidadLaboralCore());
                busquedaPuestoHelper.getBusquedaPuestoVO().setEstadoPuestoId(tipoMovimiento.
                        getEstadoPuestoInicialCore());
                busquedaPuestoHelper.getBusquedaPuestoVO().setEstadoServidorId(tipoMovimiento.
                        getEstadoPersonalInicialCore());
                if (tipoMovimiento.getClase().getRegimenLaboral() != null) {
                    busquedaPuestoHelper.getBusquedaPuestoVO().setRegimenLaboralId(tipoMovimiento.getClase().
                            getRegimenLaboral().getId());
                    busquedaPuestoHelper.getBusquedaPuestoVO().setRegimenLaboralNombre(tipoMovimiento.getClase().
                            getRegimenLaboral().getNombre());
                }

                // verificar si el tipo de movimiento es de renovacion de comision de servicio.
                busquedaPuestoHelper.getBusquedaPuestoVO().setRenovacionComisionServicio(tipoMovimiento.
                        getRenovacionComisionServicio() == null ? false : tipoMovimiento.
                                getRenovacionComisionServicio());
            }
//            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
//                    obtenerUsuarioConectado().getInstitucion().getId(), 
//                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
//            if (esRRHH(pi.getValorTexto())) {
//                busquedaPuestoHelper.setSeleccionarUnidadOrganizacional(Boolean.TRUE);
//            } else {
//                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarAgrupador(obtenerUsuarioConectado().
//                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
//                if (uo == null) {
//                    mostrarMensajeEnPantalla("NO EXISTE CONFIGURADO EL AGRUPADOR PARA SU UNIDAD ORGANIZACIONAL",
//                            FacesMessage.SEVERITY_ERROR);
//                } else if (!uo.getControl()) {
//                    busquedaPuestoHelper.setSeleccionarUnidadOrganizacional(Boolean.FALSE);
//                    busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaId(uo.getId());
//                    busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(uo.getRuta() == null
//                            ? uo.getNombre() : uo.getRuta());
//                }
//
//            }
            actualizarComponente(":frmPrincipal:parametrosBusqueda_unidadAdministrativa");
        } catch (Exception e) {
            error(getClass().getName(), "Error al recuperar paramtro institucion del código del RRHH.", e);
        }

    }

    /**
     * METODO Q VALIDA PARA GENERAR EL ARCHIVO MASIVO.
     *
     * @return
     */
    public String generarArchivo() {
        try {
            busquedaPuestoHelper.setListaSelecto(new ArrayList<DistributivoDetalle>());
            busquedaPuestoHelper.setSeleccionado(new HashMap<Integer, DistributivoDetalle>());
            int posicion = 1;
            for (DistributivoDetalle dd : busquedaPuestoHelper.getListaPuestos()) {
                if (dd.getSelecto() != null && dd.getSelecto()) {
                    busquedaPuestoHelper.getListaSelecto().add(dd);
                    busquedaPuestoHelper.getSeleccionado().put(posicion, dd);
                }
                posicion++;
            }
            if (busquedaPuestoHelper.getListaSelecto().isEmpty()) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.pantalla5.edicionTramite.mensaje.archivoMasivo.sinSeleccionar",
                        FacesMessage.SEVERITY_INFO);
            } else {
                busquedaPuestoHelper.setGeneroArchivo(Boolean.TRUE);
//                escribirExcel(tramiteHelper.getTramite(), busquedaPuestoHelper.getListaSelecto());
                mostrarMensajeEnPantalla("Archivo Generado con exito", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al generar archivo", e);
        }
        return null;
    }

    /**
     * genera archivo de excel.
     *
     * @param tramite Tramite
     * @param listaPuestos List
     * @param usuario String
     */
//    public void escribirExcel(final Tramite tramite, final List<DistributivoDetalle> listaPuestos) {
//        try {
//            String grupo = tramite.getTipoMovimiento().getClase().getGrupo().getNemonico();
//            if (grupo.equals(GrupoEnum.INGRESOS.getCodigo())) {
//                busquedaPuestoHelper.setNombreArchivo("CARGA-MASIVA-MOVIMIENTOS-INGRESOS.xls");
//            } else if (grupo.equals(GrupoEnum.SALIDAS.getCodigo())) {
//                busquedaPuestoHelper.setNombreArchivo("CARGA-MASIVA-MOVIMIENTOS-SALIDAS.xls");
//            }
//
//            //se crea el archivo segun el nombre.
//            File file = new File(busquedaPuestoHelper.getNombreArchivo());
//
//            //Se crea el libro Excel
//            WritableWorkbook workbook = Workbook.createWorkbook(file);
//
//            //Se crea una nueva hoja dentro del libro
//            WritableSheet sheet = workbook.createSheet(tramite.getTipoMovimiento().getNombre(), 0);
//
//            sheet = encabezadosColumnas(sheet, tramite.getTipoMovimiento());
//
//            escribirDatosExcel(sheet, listaPuestos, tramite.getTipoMovimiento());
//
//            //Escribimos los resultados al fichero Excel
//
//            workbook.write();
//            workbook.close();
//
//            InputStream is = new ByteArrayInputStream(UtilArchivos.getBytesFromFile(file));
//
//            busquedaPuestoHelper.setArchivoDescarga(new DefaultStreamedContent(is, "application/xls",
//                    busquedaPuestoHelper.getNombreArchivo()));
//            ejecutarComandoPrimefaces("dlgArchivoGenerado.show()");
//        } catch (IOException ex) {
//            error(getClass().getName(), "Error al crear el fichero.", ex);
//            //System.out.println("Error al crear el fichero.");
//        } catch (WriteException ex) {
//            error(getClass().getName(), "Error al escribir el fichero.", ex);
//            //System.out.println("Error al escribir el fichero.");
//        }
//    }
    /**
     *
     * @param sheet
     * @param listaPuestos
     * @param tipoMovimiento
     * @return
     */
//    public void escribirDatosExcel(final WritableSheet sheet, final List<DistributivoDetalle> listaPuestos,
//            final TipoMovimiento tipoMovimiento) {
//        try {
//            //definimos tipo de font para textos
//            WritableFont textFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
//            //definimos un formato con el font para texto
//            WritableCellFormat textFormat = new WritableCellFormat(textFont);
//            //definimos un formato de tipo numerico y le asignamos el font de texto
//            WritableCellFormat numberFormat = new WritableCellFormat(new NumberFormat("##0.00"));
//            numberFormat.setFont(textFont);
//            //definimos un formato de tipo numerico y le asignamos el font de texto
//            WritableCellFormat enteroFormat = new WritableCellFormat(new NumberFormat("##0"));
//            enteroFormat.setFont(textFont);
//            //definimos un formato de fecha y le asignamos el font de texto
//            WritableCellFormat dateFormat = new WritableCellFormat(new DateFormat("dd/MM/yyyy"));
//            dateFormat.setFont(textFont);
//
//            int x = 1;
//            for (DistributivoDetalle dd : listaPuestos) {
//                if (tipoMovimiento.getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
//                    //1 Codigo de puesto,                        
//                    sheet.addCell(new jxl.write.Number(0, x, dd.getCodigoPuesto(), enteroFormat));
//                    //3 Partida Presupuestaria Individual
//                    sheet.addCell(new jxl.write.Label(2, x, dd.getPartidaIndividual() == null ? "" : dd.
//                            getPartidaIndividual(), textFormat));
//                    //4 Nombre Régimen Laboral
//                    sheet.addCell(new jxl.write.Label(3, x, dd.getRegimenLaboral() == null ? "" : dd.
//                            getRegimenLaboral(), textFormat));
//                    //5 Nombre Unidad Administrativa
//                    sheet.addCell(new jxl.write.Label(4, x, dd.getUnidadAdministrativa() == null ? "" : dd.
//                            getUnidadAdministrativa(), textFormat));
//                    //6 Nombre Denominación del Puesto
//                    sheet.addCell(new jxl.write.Label(5, x, dd.getPuestoInstitucion() == null ? "" : dd.
//                            getPuestoInstitucion(), textFormat));
//                    //7 Ubicacion Geografica
////                    sheet.addCell(new jxl.write.Label(6, x, dd.getCantonPuestoId() == null ? ""
////                            : tramiteServicio.buscarNombreCanonicoCanton((dd.getCantonPuestoId())), textFormat));
//                    //8 Nombre Modalidad Laboral 
//                    sheet.addCell(new jxl.write.Label(7, x, dd.getModalidadLaboral() == null ? "" : dd.
//                            getModalidadLaboral(), textFormat));
//                    //9 Nombre Grupo Ocupacional
//                    sheet.addCell(new jxl.write.Label(8, x, dd.getGrupoOcupacional() == null ? "" : dd.
//                            getGrupoOcupacional(), textFormat));
//                    //10 Proyecto de Inversión 
//                    sheet.addCell(new jxl.write.Label(9, x, dd.getProyectoInversion() == null ? "NO"
//                            : dd.getProyectoInversion().equals(1) ? "SI" : "NO", textFormat));
//                    //11 Nombre Rol Puesto 
//                    sheet.addCell(new jxl.write.Label(10, x, dd.getRolPuestoId() == null ? ""
//                            : catalogoServicio.buscarRolPuesto(dd.getRolPuestoId()).getNombre(), textFormat));
//                    //12 RMU 
//                    sheet.addCell(new jxl.write.Number(11, x, dd.getSueldo() == null ? null : dd.getSueldo().
//                            longValue(), numberFormat));
//                    //49 Documento Habilitante 
//                    sheet.addCell(new jxl.write.Label(48, x, tipoMovimiento.getDocumentoHabilitanteId() == null ? ""
//                            : tipoMovimiento.getDocumentoHabilitante().getNombre(), textFormat));
//                    //50 Imprime Situación Actual 
//                    sheet.addCell(new jxl.write.Label(49, x, tipoMovimiento.getSituacionActual() == null ? "NO"
//                            : tipoMovimiento.getSituacionActual().equals(true) ? "SI" : "NO", textFormat));
//                    //51 Imprime Situación Propuesta                     
//                    sheet.addCell(new jxl.write.Label(50, x, tipoMovimiento.getSituacionPropuesta() == null ? "NO"
//                            : tipoMovimiento.getSituacionPropuesta().equals(true) ? "SI" : "NO", textFormat));
//                    x++;
//
//                } else if (tipoMovimiento.getClase().getGrupo().getNemonico().equals(GrupoEnum.SALIDAS.getCodigo())) {
//                    if (dd.getNumeroDocumento() != null) {
//                        //1 Id_OPI                        
//                        sheet.addCell(new jxl.write.Number(0, x, dd.getId() == null ? null : dd.getId(),
//                                enteroFormat));
//                        //2 Partida Presupuestaria General
//                        sheet.addCell(new jxl.write.Label(1, x, dd.getPartidaGeneral() == null ? "" : dd.
//                                getPartidaGeneral(), textFormat));
//                        //3 Partida Presupuestaria Individual
//                        sheet.addCell(new jxl.write.Label(2, x, dd.getPartidaIndividual() == null ? "" : dd.
//                                getPartidaIndividual(), textFormat));
//                        //4 Nombre Régimen Laboral
//                        sheet.addCell(new jxl.write.Label(3, x, dd.getRegimenLaboral() == null ? "" : dd.
//                                getRegimenLaboral(), textFormat));
//                        //5 RMU 
//                        sheet.addCell(new jxl.write.Number(4, x, dd.getSueldo() == null ? null : dd.getSueldo().
//                                longValue(), numberFormat));
//                        //6 Nombre Unidad Administrativa
//                        sheet.addCell(new jxl.write.Label(5, x, dd.getUnidadAdministrativa() == null ? "" : dd.
//                                getUnidadAdministrativa(), textFormat));
//                        //7 Nombre Denominación del Puesto
//                        sheet.addCell(new jxl.write.Label(6, x, dd.getPuestoInstitucion() == null ? "" : dd.
//                                getPuestoInstitucion(), textFormat));
//                        //8 Ubicacion Geografica
////                        sheet.addCell(new jxl.write.Label(7, x, dd.getCantonPuestoId() == null ? ""
////                                : tramiteServicio.buscarNombreCanonicoCanton((dd.getCantonPuestoId())), textFormat));
//                        //9 Nombre Modalidad Laboral 
//                        sheet.addCell(new jxl.write.Label(8, x, dd.getModalidadLaboral() == null ? "" : dd.
//                                getModalidadLaboral(), textFormat));
//                        //10 Nombre Grupo Ocupacional
//                        sheet.addCell(new jxl.write.Label(9, x, dd.getGrupoOcupacional() == null ? "" : dd.
//                                getGrupoOcupacional(), textFormat));
//                        //11 Proyecto de Inversión 
//                        sheet.addCell(new jxl.write.Label(10, x, dd.getProyectoInversion() == null ? "NO"
//                                : dd.getProyectoInversion().equals(1) ? "SI" : "NO", textFormat));
//                        //12 Nombre Rol Puesto 
//                        sheet.addCell(new jxl.write.Label(11, x, dd.getRolPuestoId() == null ? ""
//                                : catalogoServicio.buscarRolPuesto(dd.getRolPuestoId()).getNombre(), textFormat));
//                        //15 Tipo Identificación                     
//                        sheet.addCell(new jxl.write.Label(14, x, dd.getTipoDocumento() == null ? "" : dd.
//                                getTipoDocumento().substring(0, 1), textFormat));
//                        //16 Número Identificación                     
//                        sheet.addCell(new jxl.write.Label(15, x, dd.getNumeroDocumento() == null ? "" : dd.
//                                getNumeroDocumento(), textFormat));
//                        //17 Apellidos Servidor
//                        sheet.addCell(new jxl.write.Label(16, x, dd.getApellido() == null ? "" : dd.getApellido(),
//                                textFormat));
//                        //18 Nombres Servidor
//                        sheet.addCell(new jxl.write.Label(17, x, dd.getNombre() == null ? "" : dd.getNombre(),
//                                textFormat));
//                        //19 Nombre Género
//                        sheet.addCell(new jxl.write.Label(18, x, dd.getGeneroId() == null ? ""
//                                : dd.getGeneroNombre(), textFormat));
//                        //20 Nombre Nacionalidad
//                        sheet.addCell(new jxl.write.Label(19, x, dd.getServidorNacionalidadId() == null ? ""
//                                : catalogoServicio.buscarNacionalidad(dd.getServidorNacionalidadId()).getNombre(),
//                                textFormat));
//                        //21 Nombre Estado Civil
//                        sheet.addCell(new jxl.write.Label(20, x, dd.getServidorEstadoCivilId() == null ? ""
//                                : catalogoServicio.buscarEstadoCivil(dd.getServidorEstadoCivilId()).getNombre(),
//                                textFormat));
//                        //22 Fecha de Nacimiento del Servidor                
//                        sheet.addCell(new jxl.write.DateTime(21, x, dd.getServidorFechaNacimiento() == null ? null
//                                : dd.getServidorFechaNacimiento(), dateFormat));
//                        //23 Fecha de Ingreso a la Institución                 
//                        sheet.addCell(new jxl.write.DateTime(22, x, dd.getFechaIngresoInstitucion() == null ? null
//                                : dd.getFechaIngresoInstitucion(), dateFormat));
//                        //24 Fecha de Ingreso al Sector Público                
//                        sheet.addCell(new jxl.write.DateTime(23, x, dd.getFechaIngresoSectorPublico() == null ? null
//                                : dd.getFechaIngresoSectorPublico(), dateFormat));
//                        //25 Imprime Situación Actual                     
//                        sheet.addCell(new jxl.write.Label(24, x, tipoMovimiento.getSituacionActual() == null ? "NO"
//                                : tipoMovimiento.getSituacionActual().equals(true) ? "SI" : "NO", textFormat));
//                        //26 Imprime Situación Propuesta 
//                        sheet.addCell(new jxl.write.Label(25, x, tipoMovimiento.getSituacionPropuesta() == null ? "NO"
//                                : tipoMovimiento.getSituacionPropuesta().equals(true) ? "SI" : "NO", textFormat));
//                        //27 Documento Habilitante 
//                        sheet.addCell(new jxl.write.Label(26, x, tipoMovimiento.getDocumentoHabilitanteId() == null ? ""
//                                : tipoMovimiento.getDocumentoHabilitante().getNombre(), textFormat));
//                        x++;
//                    }
//                }
//
//            }
//
//        } catch (Exception ex) {
//            error(getClass().getName(), "Error al ingresar datos en el fichero.", ex);
//            //System.out.println("Error al ingresar datos en el fichero.");
//        }
//
//        return sheet;
//    }
    /**
     * genera los nombres de columnas.
     *
     * @param sheet
     * @return
     */
//    public WritableSheet encabezadosColumnas(final WritableSheet sheet, final TipoMovimiento tipoMovimiento) {
//        try {
//            String titulo = "";
//            //definimos tipo de font para nombres de columnas
//            WritableFont headersFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//            headersFont.setColour(Colour.WHITE);
//            //definimos tipo de font para campo obligatorio
//            WritableFont obligatorioFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
//            //definimos un formato con el font de cabeceras, mas el fondo de color azul
//            WritableCellFormat headerFormat = new WritableCellFormat(headersFont);
//            headerFormat.setBackground(Colour.BLUE2);
//            //definimos foramto de celda apra obligatorio centrado
//            WritableCellFormat obligatorioFormat = new WritableCellFormat(obligatorioFont);
//            obligatorioFormat.setAlignment(Alignment.CENTRE);
//
//            if (tipoMovimiento.getClase().getGrupo().getNemonico().equals(GrupoEnum.INGRESOS.getCodigo())) {
//
//                //1 Id_OPI                    
//                sheet.addCell(new jxl.write.Label(0, 0, "(*) Id_OPI", headerFormat));
//                //2 Partida Presupuestaria General
//                sheet.addCell(new jxl.write.Label(1, 0, "Partida Presupuestaria General", headerFormat));
//                //3 Partida Presupuestaria Individual
//                sheet.addCell(new jxl.write.Label(2, 0, "Partida Presupuestaria Individual", headerFormat));
//                //4 Nombre Régimen Laboral
//                sheet.addCell(new jxl.write.Label(3, 0, "Nombre Régimen Laboral", headerFormat));
//                //5 Nombre Unidad Administrativa
//                sheet.addCell(new jxl.write.Label(4, 0, "Nombre Unidad Administrativa", headerFormat));
//                //6 Nombre Denominación del Puesto                
//                sheet.addCell(new jxl.write.Label(5, 0, "Nombre Denominación del Puesto", headerFormat));
//                //7 Ubicación Geográfica 
//                sheet.addCell(new jxl.write.Label(6, 0, "Ubicación Geográfica", headerFormat));
//                //8 Nombre Modalidad Laboral 
//                sheet.addCell(new jxl.write.Label(7, 0, "Nombre Modalidad Laboral", headerFormat));
//                //9 Nombre Grupo Ocupacional
//                sheet.addCell(new jxl.write.Label(8, 0, "Nombre Grupo Ocupacional", headerFormat));
//                //10 Proyecto de Inversión 
//                sheet.addCell(new jxl.write.Label(9, 0, "Proyecto de Inversión", headerFormat));
//                //11 Nombre Rol Puesto 
//                sheet.addCell(new jxl.write.Label(10, 0, "Nombre Rol Puesto", headerFormat));
//                //12 RMU 
//                sheet.addCell(new jxl.write.Label(11, 0, "Sueldo", headerFormat));
//                //13 Tipo Identificación 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getTipoDocumento()
//                        == null ? "" : tipoMovimiento.getTipoDocumento()), "Tipo Identificación");
//                sheet.addCell(new jxl.write.Label(12, 0, titulo, headerFormat));
//                //14 Número Identificación 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getNumeroDocumento()
//                        == null ? "" : tipoMovimiento.getNumeroDocumento()), "Número Identificación");
//                sheet.addCell(new jxl.write.Label(13, 0, titulo, headerFormat));
//                //15 Apellidos Servidor
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApellidoNombre()
//                        == null ? "" : tipoMovimiento.getApellidoNombre()), "Apellidos Servidor");
//                sheet.addCell(new jxl.write.Label(14, 0, titulo, headerFormat));
//                //16 Nombres Servidor
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApellidoNombre()
//                        == null ? "" : tipoMovimiento.getApellidoNombre()), "Nombres Servidor");
//                sheet.addCell(new jxl.write.Label(15, 0, titulo, headerFormat));
//                sheet.addCell(new jxl.write.Label(16, 0, titulo, headerFormat));
//                sheet.addCell(new jxl.write.Label(17, 0, titulo, headerFormat));
//                sheet.addCell(new jxl.write.Label(22, 0, titulo, headerFormat));
//                //32 Fecha de Ingreso a la Institución 
//                titulo =
//                        UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaIngresoInstitucion()
//                        == null ? "" : tipoMovimiento.getFechaIngresoInstitucion()), "Fecha de Ingreso a la Institución");
//                sheet.addCell(new jxl.write.Label(31, 0, titulo, headerFormat));
//                //33 Fecha de Ingreso al Sector Público
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaIngresoSectorPublico()
//                        == null ? "" : tipoMovimiento.getFechaIngresoSectorPublico()),
//                        "Fecha de Ingreso al Sector Público");
//                sheet.addCell(new jxl.write.Label(32, 0, titulo, headerFormat));
//                //37 Discapacidad
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getDiscapacidad()
//                        == null ? "" : tipoMovimiento.getDiscapacidad()), "Discapacidad");
//                sheet.addCell(new jxl.write.Label(36, 0, titulo, headerFormat));
//                //38 Número Conadis                
//                sheet.addCell(new jxl.write.Label(37, 0, "(2*) Número Conadis", headerFormat));
//                //39 Especifique Discapacidad                
//                sheet.addCell(new jxl.write.Label(38, 0, "(2*) Especifique Discapacidad", headerFormat));
//                //40 Código Tipo Discapacidad                
//                sheet.addCell(new jxl.write.Label(39, 0, "(2*) Código Tipo Discapacidad", headerFormat));
//                //41 Porcentaje Discapacidad 
//                sheet.addCell(new jxl.write.Label(40, 0, "(2*) Porcentaje Discapacidad", headerFormat));
//                //42 Corresponde a Discapacidad 
//                sheet.addCell(new jxl.write.Label(41, 0, "(2*) Corresponde a Discapacidad", headerFormat));
//                //43 Justificación de  Discapacidad 
//                sheet.addCell(new jxl.write.Label(42, 0, "(2*) Justificación de  Discapacidad", headerFormat));
//                //44 Código Tipo Designación 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getTipoDesignacion()
//                        == null ? "" : tipoMovimiento.getTipoDesignacion()), "Código Tipo Designación");
//                sheet.addCell(new jxl.write.Label(43, 0, titulo, headerFormat));
//                //45 Tiempo Jornada Diaria 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getTiempoJornadaDiaria()
//                        == null ? "" : tipoMovimiento.getTiempoJornadaDiaria()), "Tiempo Jornada Diaria");
//                sheet.addCell(new jxl.write.Label(44, 0, titulo, headerFormat));
//                //46 Tipo Temporada 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getTipoTemporada()
//                        == null ? "" : tipoMovimiento.getTipoTemporada()), "Tipo Temporada");
//                sheet.addCell(new jxl.write.Label(45, 0, titulo, headerFormat));
//                //47 Rige a Partir de 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaRigeAPartirDe()
//                        == null ? "" : tipoMovimiento.getFechaRigeAPartirDe()), "Rige a Partir de");
//                sheet.addCell(new jxl.write.Label(46, 0, titulo, headerFormat));
//                //48 Fecha Hasta 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaHasta()
//                        == null ? "" : tipoMovimiento.getFechaHasta()), "Fecha Hasta");
//                sheet.addCell(new jxl.write.Label(47, 0, titulo, headerFormat));
//                //49 Documento Habilitante                 
//                sheet.addCell(new jxl.write.Label(48, 0, "(*) Documento Habilitante", headerFormat));
//                //50 Imprime Situación Actual                 
//                sheet.addCell(new jxl.write.Label(49, 0, "(*) Imprime Situación Actual", headerFormat));
//                //51 Imprime Situación Propuesta                 
//                sheet.addCell(new jxl.write.Label(50, 0, "(*) Imprime Situación Propuesta", headerFormat));
//                //52 Explicación de la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApExplicacion()
//                        == null ? "" : tipoMovimiento.getApExplicacion()), "Explicación de la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(51, 0, titulo, headerFormat));
//                //53 Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApDocumentoPrevio()
//                        == null ? "" : tipoMovimiento.getApDocumentoPrevio()),
//                        "Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(52, 0, titulo, headerFormat));
//                //54 Número de Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApNumeroDocumento()
//                        == null ? "" : tipoMovimiento.getApNumeroDocumento()),
//                        "Número de Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(53, 0, titulo, headerFormat));
//                //55 Fecha de Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApFechaDocumento()
//                        == null ? "" : tipoMovimiento.getApFechaDocumento()),
//                        "Fecha de Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(54, 0, titulo, headerFormat));
//                //56 Asunto del Memorando 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getAsuntoMemorando()
//                        == null ? "" : tipoMovimiento.getAsuntoMemorando()), "Asunto del Memorando");
//                sheet.addCell(new jxl.write.Label(55, 0, titulo, headerFormat));
//                //57 Número Memorando 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getNumeroMemorando()
//                        == null ? "" : tipoMovimiento.getNumeroMemorando()), "Número Memorando");
//                sheet.addCell(new jxl.write.Label(56, 0, titulo, headerFormat));
//                //58 Contenido Memorando 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getContenidoMemorando()
//                        == null ? "" : tipoMovimiento.getContenidoMemorando()), "Contenido Memorando");
//                sheet.addCell(new jxl.write.Label(57, 0, titulo, headerFormat));
//                //59 Antecedentes contrato 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getAntecedentesContrato()
//                        == null ? "" : tipoMovimiento.getAntecedentesContrato()), "Antecedentes contrato");
//                sheet.addCell(new jxl.write.Label(58, 0, titulo, headerFormat));
//                //60 Actividades Contrato 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getActividadesContrato()
//                        == null ? "" : tipoMovimiento.getActividadesContrato()), "Actividades Contrato");
//                sheet.addCell(new jxl.write.Label(59, 0, titulo, headerFormat));
//                //61 Siglas de Título Académico 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getSiglasTituloAcademico()
//                        == null ? "" : tipoMovimiento.getSiglasTituloAcademico()), "Siglas de Título Académico");
//                sheet.addCell(new jxl.write.Label(60, 0, titulo, headerFormat));
//                //62 Renovación Contrato
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getRenovacion()
//                        == null ? "" : tipoMovimiento.getRenovacion()), "Renovación Contrato");
//                sheet.addCell(new jxl.write.Label(61, 0, titulo, headerFormat));
//
//            } else if (tipoMovimiento.getClase().getGrupo().getNemonico().equals(GrupoEnum.SALIDAS.getCodigo())) {
//
//                //1 Id_OPI                    
//                sheet.addCell(new jxl.write.Label(0, 0, "(*) Id_OPI", headerFormat));
//                //2 Partida Presupuestaria General
//                sheet.addCell(new jxl.write.Label(1, 0, "Partida General", headerFormat));
//                //3 Partida Presupuestaria Individual
//                sheet.addCell(new jxl.write.Label(2, 0, "Partida Individual", headerFormat));
//                //4 Nombre Régimen Laboral
//                sheet.addCell(new jxl.write.Label(3, 0, "Nombre Régimen Laboral", headerFormat));
//                //5 RMU 
//                sheet.addCell(new jxl.write.Label(4, 0, "RMU", headerFormat));
//                //6 Nombre Unidad Administrativa
//                sheet.addCell(new jxl.write.Label(5, 0, "Nombre Unidad Administrativa", headerFormat));
//                //7 Nombre Denominación del Puesto
//                sheet.addCell(new jxl.write.Label(6, 0, "Nombre Denominación del Puesto", headerFormat));
//                //8 Ubicación Geográfica
//                sheet.addCell(new jxl.write.Label(7, 0, "Ubicación Geográfica", headerFormat));
//                //9 Nombre Modalidad Laboral
//                sheet.addCell(new jxl.write.Label(8, 0, "Nombre Modalidad Laboral", headerFormat));
//                //10 Nombre Grupo Ocupacional
//                sheet.addCell(new jxl.write.Label(9, 0, "Nombre Grupo Ocupacional", headerFormat));
//                //11 Proyecto de Inversion                
//                sheet.addCell(new jxl.write.Label(10, 0, "Proyecto de Inversion", headerFormat));
//                //12 Nombre Rol Puesto
//                sheet.addCell(new jxl.write.Label(11, 0, "Nombre Rol Puesto", headerFormat));
//                //13 Rige a Partir de 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaRigeAPartirDe()
//                        == null ? "" : tipoMovimiento.getFechaRigeAPartirDe()), "Rige a Partir de");
//                sheet.addCell(new jxl.write.Label(12, 0, titulo, headerFormat));
//                //14 Fecha Hasta 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaHasta()
//                        == null ? "" : tipoMovimiento.getFechaHasta()), "Fecha Hasta");
//                sheet.addCell(new jxl.write.Label(13, 0, titulo, headerFormat));
//                //15 Tipo Identificación 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getTipoDocumento()
//                        == null ? "" : tipoMovimiento.getTipoDocumento()), "Tipo Identificación");
//                sheet.addCell(new jxl.write.Label(14, 0, titulo, headerFormat));
//                //16 Número Identificación 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getNumeroDocumento()
//                        == null ? "" : tipoMovimiento.getNumeroDocumento()), "Número Identificación");
//                sheet.addCell(new jxl.write.Label(15, 0, titulo, headerFormat));
//                //17 Apellidos Servidor
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApellidoNombre()
//                        == null ? "" : tipoMovimiento.getApellidoNombre()), "Apellidos Servidor");
//                sheet.addCell(new jxl.write.Label(16, 0, titulo, headerFormat));
//                //18 Nombres Servidor
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApellidoNombre()
//                        == null ? "" : tipoMovimiento.getApellidoNombre()), "Nombres Servidor");
//                sheet.addCell(new jxl.write.Label(17, 0, titulo, headerFormat));
//                //23 Fecha de Ingreso a la Institución
//                titulo =
//                        UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaIngresoInstitucion()
//                        == null ? "" : tipoMovimiento.getFechaIngresoInstitucion()), "Fecha de Ingreso a la Institución");
//                sheet.addCell(new jxl.write.Label(22, 0, titulo, headerFormat));
//                //24 Fecha de Ingreso al Sector Público
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaIngresoSectorPublico()
//                        == null ? "" : tipoMovimiento.getFechaIngresoSectorPublico()),
//                        "Fecha de Ingreso al Sector Público");
//                sheet.addCell(new jxl.write.Label(23, 0, titulo, headerFormat));
//                //25 Imprimir Situación Actual                 
//                sheet.addCell(new jxl.write.Label(24, 0, "(*) Imprimir Situación Actual", headerFormat));
//                //26 Imprimir Situación Propuesta                 
//                sheet.addCell(new jxl.write.Label(25, 0, "(*) Imprimir Situación Propuesta", headerFormat));
//                //27 Documento Habilitante                 
//                sheet.addCell(new jxl.write.Label(26, 0, "(*) Documento Habilitante", headerFormat));
//                //28 Explicación de la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaIngresoSectorPublico()
//                        == null ? "" : tipoMovimiento.getFechaIngresoSectorPublico()),
//                        "Explicación de la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(27, 0, titulo, headerFormat));
//                //29 Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApDocumentoPrevio()
//                        == null ? "" : tipoMovimiento.getApDocumentoPrevio()),
//                        "Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(28, 0, titulo, headerFormat));
//                //30 Número de Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApNumeroDocumento()
//                        == null ? "" : tipoMovimiento.getApNumeroDocumento()),
//                        "Número de Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(29, 0, titulo, headerFormat));
//                //31 Fecha de Documento Previo para la Acción de Personal
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getApFechaDocumento()
//                        == null ? "" : tipoMovimiento.getApFechaDocumento()),
//                        "Fecha de Documento Previo para la Acción de Personal");
//                sheet.addCell(new jxl.write.Label(30, 0, titulo, headerFormat));
//                //32 Asunto del Memo
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getAsuntoMemorando()
//                        == null ? "" : tipoMovimiento.getAsuntoMemorando()), "Asunto del Memo");
//                sheet.addCell(new jxl.write.Label(31, 0, titulo, headerFormat));
//                //33 Número Memorando
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getNumeroMemorando()
//                        == null ? "" : tipoMovimiento.getNumeroMemorando()), "Número Memorando");
//                sheet.addCell(new jxl.write.Label(32, 0, titulo, headerFormat));
//                //34 Contenido Memorando 
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getContenidoMemorando()
//                        == null ? "" : tipoMovimiento.getContenidoMemorando()), "Contenido Memorando");
//                sheet.addCell(new jxl.write.Label(33, 0, titulo, headerFormat));
//                //35 Fecha de Aceptación de Renuncia
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaAceptacionRenuncia()
//                        == null ? "" : tipoMovimiento.getFechaAceptacionRenuncia()), "Fecha de Aceptación de Renuncia");
//                sheet.addCell(new jxl.write.Label(34, 0, titulo, headerFormat));
//                //36 Fecha Presenta Renuncia
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaPresentaRenuncia()
//                        == null ? "" : tipoMovimiento.getFechaPresentaRenuncia()), "Fecha Presenta Renuncia");
//                sheet.addCell(new jxl.write.Label(35, 0, titulo, headerFormat));
//                //37 Fecha de Fallecimiento
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getFechaFallecimiento()
//                        == null ? "" : tipoMovimiento.getFechaFallecimiento()), "Fecha de Fallecimiento");
//                sheet.addCell(new jxl.write.Label(36, 0, titulo, headerFormat));
//                //38 Caso de Fallecimiento
//                titulo = UtilCadena.concatenar(esObligatorio(tipoMovimiento.getCasoFallecimiento()
//                        == null ? "" : tipoMovimiento.getCasoFallecimiento()), "Caso de Fallecimiento");
//                sheet.addCell(new jxl.write.Label(37, 0, titulo, headerFormat));
//            }
//
//
//
//        } catch (WriteException ex) {
//            error(getClass().getName(), "Error al escribir cabeceras en el fichero.", ex);
//            //System.out.println("Error al escribir cabeceras en el fichero.");
//        }
//        return sheet;
//    }
    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String nemonico) {
        return TipoDocumentoEnum.obtenerNombre(nemonico);
    }

    private String esObligatorio(String campo) {
        String retorno = "";
        if (campo.equals(CamposConfiguracionEnum.OBLIGATORIO.getCodigo())) {
            retorno = "(*) ";
        }
        return retorno;
    }

    /**
     * Este método regresa a la pantalla de tramite.
     *
     * @return String
     */
    public String guardarPuestos() {
        try {
            busquedaPuestoHelper.setListaSelecto(new ArrayList<DistributivoDetalle>());
            busquedaPuestoHelper.setSeleccionado(new HashMap<Integer, DistributivoDetalle>());
            int posicion = 1;
            for (DistributivoDetalle dd : busquedaPuestoHelper.getListaPuestos()) {
                if (dd.getSelecto() != null && dd.getSelecto()) {
                    busquedaPuestoHelper.getListaSelecto().add(dd);
                    busquedaPuestoHelper.getSeleccionado().put(posicion, dd);
                }
                posicion++;
            }
            if (busquedaPuestoHelper.getListaSelecto().isEmpty()) {
                mostrarMensajeEnPantalla("No ha seleccionado ningún puesto.", FacesMessage.SEVERITY_INFO);
            } else if (busquedaPuestoHelper.getGeneroArchivo()) {
                busquedaPuestoHelper.setContinuar(Boolean.FALSE);
                ejecutarComandoPrimefaces("confirmation.show();");
            } else {
                busquedaPuestoHelper.setContinuar(Boolean.TRUE);
                grabar();
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar los puestos", e);
        }
        return null;
    }

    public void grabar() {
        try {
            if (busquedaPuestoHelper.getContinuar()) {
                tramiteServicio.guardarMovimientos(tramiteHelper.getTramite(),
                        busquedaPuestoHelper.getListaSelecto(), obtenerUsuarioConectado().getServidor().
                        getNumeroIdentificacion(), false);
                ejecutarComandoPrimefaces("pnlMensajeExito.show();");
                actualizarComponente("pnlMensajeExito");
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al grabar los puestos", e);
        }
    }

    /**
     * continua grabando.
     */
    public void siContinuar() {
        busquedaPuestoHelper.setContinuar(Boolean.TRUE);
        grabar();
    }

    /**
     * ya no graba.
     */
    public void noContinuar() {
        busquedaPuestoHelper.setContinuar(Boolean.FALSE);
    }

    /**
     * metodo que genera el archivo excel de la consulta del distributivo.
     *
     * @return
     */
    public String generarExcel() {
        try {
            if (busquedaPuestoHelper.getListaPuestos().size() <= 0) {
                mostrarMensajeEnPantalla("No hay registros, tiene que realizar primero una busqueda del distributivo.",
                        FacesMessage.SEVERITY_WARN);
            } else {
                //se crea el archivo segun el nombre.
                File file = new File(busquedaPuestoHelper.getNombreArchivo() + ".xls");
                //verifica si ya existe el archivo y lo borra y crea el nuevo
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();

                //Se crea el libro Excel
                WritableWorkbook workbook
                        = Workbook.createWorkbook(file);

                //Se crea una nueva hoja dentro del libro
                WritableSheet sheet
                        = workbook.createSheet("Consulta", 0);

                encabezadosColumnasConsultaExcel(sheet);
                datosColumnasConsultaExcel(sheet, busquedaPuestoHelper.getListaPuestos());

                //Escribimos los resultados al fichero Excel
                workbook.write();
                workbook.close();

                InputStream is = new ByteArrayInputStream(UtilArchivos.getBytesFromFile(file));

                busquedaPuestoHelper.setArchivoDescarga(new DefaultStreamedContent(is, "application/xls",
                        busquedaPuestoHelper.getNombreArchivo() + ".xls"));
                actualizarComponente(":frmGuardarArchivoGenerado");
                ejecutarComandoPrimefaces("dlgArchivoGenerado.show()");

                //mostrarMensajeEnPantalla("Archivo excel, generado con éxito.", FacesMessage.SEVERITY_INFO);
            }
            actualizarComponente("accordionPanel");
        } catch (IOException ex) {
            ex.printStackTrace();
            error(getClass().getName(), "Error al crear el fichero.", ex);
        } catch (Exception e) {
            e.printStackTrace();
            error(getClass().getName(), "Error al generar excel de consultar los puestos.", e);
        }
        return null;
    }

    public WritableSheet datosColumnasConsultaExcel(final WritableSheet sheet, List<DistributivoDetalle> listaPuestos) {
        try {
            //definimos tipo de font para textos
            WritableFont textFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
            //definimos un formato con el font para texto
            WritableCellFormat textFormat = new WritableCellFormat(textFont);
            //definimos un formato de tipo numerico y le asignamos el font de texto
            WritableCellFormat numberFormat = new WritableCellFormat(new NumberFormat("##0.00"));
            numberFormat.setFont(textFont);
            //definimos un formato de tipo numerico y le asignamos el font de texto
            WritableCellFormat enteroFormat = new WritableCellFormat(new NumberFormat("##0"));
            enteroFormat.setFont(textFont);
            //definimos un formato de fecha y le asignamos el font de texto
            WritableCellFormat dateFormat = new WritableCellFormat(new DateFormat("dd/MM/yyyy"));
            dateFormat.setFont(textFont);

            int x = 1;
            for (DistributivoDetalle dd : listaPuestos) {
                //1 numero,                        
                sheet.addCell(new jxl.write.Number(0, x, x, enteroFormat));
                //2 codigo puesto
                if (dd.getCodigoPuesto() != null) {
                    sheet.addCell(new jxl.write.Number(1, x, dd.getCodigoPuesto().longValue(), enteroFormat));
                }
                //3 Régimen laboral
                if (dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral() != null) {
                    sheet.addCell(new jxl.write.Label(2, x, dd.getEscalaOcupacional().getNivelOcupacional().
                            getRegimenLaboral().getNombre(), textFormat));
                }
                //4 Escala Remunerativa
                if (dd.getEscalaOcupacional().getNivelOcupacional() != null) {
                    sheet.addCell(new jxl.write.Label(3, x, dd.getEscalaOcupacional().getNivelOcupacional().
                            getNombre(), textFormat));
                }
                //5 Modalidad Laboral
                if (dd.getDistributivo().getModalidadLaboral() != null) {
                    sheet.addCell(new jxl.write.Label(4, x, dd.getDistributivo().getModalidadLaboral().
                            getNombre(), textFormat));
                }
                //6 Denominación de Puesto
                if (dd.getEscalaOcupacional() != null) {
                    sheet.addCell(new jxl.write.Label(5, x, dd.getEscalaOcupacional().getNombre(), textFormat));
                }
                //7 Sueldo Escala
                if (dd.getEscalaOcupacional().getRmu() != null) {
                    sheet.addCell(new jxl.write.Number(6, x, dd.getEscalaOcupacional().getRmu().longValue(),
                            enteroFormat));
                }
                //8 Sueldo Máximo
                if (dd.getEscalaOcupacional().getRmuMaximo() != null) {
                    sheet.addCell(new jxl.write.Number(7, x, dd.getEscalaOcupacional().getRmuMaximo().longValue(),
                            enteroFormat));
                }
                //9 Sueldo
                if (dd.getRmu() != null) {
                    sheet.addCell(new jxl.write.Number(8, x, dd.getRmu().longValue(), enteroFormat));
                }
                //10 Sueldo Básico
                if (dd.getSueldoBasico() != null) {
                    sheet.addCell(new jxl.write.Number(9, x, dd.getSueldoBasico().longValue(), enteroFormat));
                }
                //11 Fecha Creación
                if (dd.getFechaInicio() != null) {
                    sheet.addCell(new jxl.write.DateTime(10, x, dd.getFechaInicio(), dateFormat));
                }
                //12 Fecha Fin
                if (dd.getFechaFin() != null) {
                    sheet.addCell(new jxl.write.DateTime(11, x, dd.getFechaFin(), dateFormat));
                }
                //13 Partida Individual
                if (dd.getPartidaIndividual() != null) {
                    sheet.addCell(new jxl.write.Label(12, x, dd.getPartidaIndividual(), textFormat));
                }
                //14 Unidad Organizacional
                if (dd.getDistributivo().getUnidadOrganizacional().
                        getUnidadOrganizacional() != null) {
                    sheet.addCell(new jxl.write.Label(13, x, dd.getDistributivo().getUnidadOrganizacional().getRuta(),
                            textFormat));
                }
                //15 Ubicación Geográfica
                if (dd.getUbicacionGeografica().getUbicacionGeografica() != null) {
                    sheet.addCell(new jxl.write.Label(14, x, dd.getUbicacionGeografica().getUbicacionGeografica().
                            getNombre() + " / " + dd.getUbicacionGeografica().getNombre(), textFormat));
                }
                //16 Grupo Ocupacional
                if (dd.getDenominacionPuesto() != null) {
                    sheet.addCell(new jxl.write.Label(15, x, dd.getDenominacionPuesto().getNombre(), textFormat));
                }
                //17 Certificación Presupuestaria
                if (dd.getCertificacionPresupuestaria() != null) {
                    sheet.addCell(new jxl.write.Label(16, x, dd.getCertificacionPresupuestaria(), textFormat));
                }
                //18 Estado Puesto
                if (dd.getEstadoPuesto() != null) {
                    sheet.addCell(new jxl.write.Label(17, x, dd.getEstadoPuesto().getNombre(), textFormat));
                }
                //19 Tipo Identificación
                if (dd.getServidor() != null) {
                    sheet.addCell(new jxl.write.Label(18, x, obtenerDescripcionTipoDocumento(dd.getServidor().
                            getTipoIdentificacion()), textFormat));
                }
                //20 Número Identificación
                if (dd.getServidor() != null) {
                    sheet.addCell(new jxl.write.Label(19, x, dd.getServidor().getNumeroIdentificacion(), textFormat));
                }
                //21 Apellidos Nombres 
                if (dd.getServidor() != null) {
                    sheet.addCell(new jxl.write.Label(20, x, dd.getServidor().getApellidosNombres(), textFormat));
                }
                //22 Unidad Organizacional Por Cambio Administrativo
                if (dd.getUnidadOrganizacionalCambioAdministrativo() != null) {
                    sheet.addCell(new jxl.write.Label(21, x, dd.getUnidadOrganizacionalCambioAdministrativo().getRuta(),
                            textFormat));
                }
                //23 Fecha Fin del Cambio Administrativo
                if (dd.getUnidadOrganizacionalCambioAdministrativo() != null) {
                    sheet.addCell(new jxl.write.DateTime(22, x, dd.getFechaMaximoCambioAdministrativo(), dateFormat));
                }
                //24 Código Puesto Encargo
                if (dd.getDistributivoDetalleEncargo() != null) {
                    sheet.addCell(new jxl.write.Number(23, x, dd.getDistributivoDetalleEncargo().getCodigoPuesto(),
                            numberFormat));
                }
                //25 Fecha Inicio Encargo
                if (dd.getFechaInicioEncargo() != null) {
                    sheet.addCell(new jxl.write.DateTime(24, x, dd.getFechaInicioEncargo(), dateFormat));
                }
                //26 Fecha Fin Encargo
                if (dd.getFechaFinEncargo() != null) {
                    sheet.addCell(new jxl.write.DateTime(25, x, dd.getFechaFinEncargo(), dateFormat));
                }
                //27 Código Puesto Subrogado
                if (dd.getDistributivoDetalleSubrogacion() != null) {
                    sheet.addCell(new jxl.write.Number(26, x, dd.getDistributivoDetalleSubrogacion().getCodigoPuesto(),
                            numberFormat));
                }
                //28 Fecha Inicio Subrogación
                if (dd.getFechaInicioSubrogacion() != null) {
                    sheet.addCell(new jxl.write.DateTime(27, x, dd.getFechaInicioSubrogacion(), dateFormat));
                }
                //29 Fecha Fin Subrogación
                if (dd.getFechaFinSubrogacion() != null) {
                    sheet.addCell(new jxl.write.DateTime(28, x, dd.getFechaFinSubrogacion(), dateFormat));
                }
                //30 Trámite No
                if (dd.getMovimiento() != null) {
                    if (dd.getMovimiento().getTramite() != null) {
                        sheet.addCell(new jxl.write.Label(29, x, dd.getMovimiento().getTramite().
                                getNumericoTramite() + "-" + dd.getMovimiento().getTramite().
                                getInstitucionEjercicioFiscal().
                                getEjercicioFiscal().getNombre(), textFormat));
                    } else {
                        sheet.addCell(new jxl.write.Label(29, x, "-", textFormat));
                    }
                } else {
                    sheet.addCell(new jxl.write.Label(29, x, "-", textFormat));
                }
                x++;
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al ingresar datos en el fichero.", ex);
        }
        return sheet;
    }

    /**
     * Método que escribe los encabezados del archivo excel de la consulta.
     *
     * @param sheet
     * @return
     */
    public WritableSheet encabezadosColumnasConsultaExcel(final WritableSheet sheet) {
        try {
            String titulo = "";
            //definimos tipo de font para nombres de columnas
            WritableFont headersFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            headersFont.setColour(Colour.WHITE);
            //definimos un formato con el font de cabeceras, mas el fondo de color azul
            WritableCellFormat headerFormat = new WritableCellFormat(headersFont);
            headerFormat.setBackground(Colour.BLUE2);
            headerFormat.setAlignment(Alignment.CENTRE);

            //1 No.                    
            sheet.addCell(new jxl.write.Label(0, 0, "No.", headerFormat));
            //2 codigo puesto
            sheet.addCell(new jxl.write.Label(1, 0, "CÓDIGO PUESTO", headerFormat));
            //3 Régimen laboral
            sheet.addCell(new jxl.write.Label(2, 0, "RÉGIMEN LABORAL", headerFormat));
            //4 Escala Remunerativa
            sheet.addCell(new jxl.write.Label(3, 0, "ESCALA REMUNERATIVA", headerFormat));
            //5 Modalidad Laboral
            sheet.addCell(new jxl.write.Label(4, 0, "MODALIDAD LABORAL", headerFormat));
            //6 Denominación de Puesto
            sheet.addCell(new jxl.write.Label(5, 0, "DENOMINACIÓN DE PUESTO", headerFormat));
            //7 Sueldo Escala
            sheet.addCell(new jxl.write.Label(6, 0, "SUELDO ESCALA", headerFormat));
            //8 Sueldo Máximo
            sheet.addCell(new jxl.write.Label(7, 0, "SUELDO MÁXIMO", headerFormat));
            //9 Sueldo
            sheet.addCell(new jxl.write.Label(8, 0, "SUELDO", headerFormat));
            //10 Sueldo Básico
            sheet.addCell(new jxl.write.Label(9, 0, "SUELDO BÁSICO", headerFormat));
            //11 Fecha Creación
            sheet.addCell(new jxl.write.Label(10, 0, "FECHA CREACIÓN", headerFormat));
            //12 Fecha Fin
            sheet.addCell(new jxl.write.Label(11, 0, "FECHA FIN", headerFormat));
            //13 Partida Individual
            sheet.addCell(new jxl.write.Label(12, 0, "PARTIDA INDIVIDUAL", headerFormat));
            //14 Unidad Organizacional
            sheet.addCell(new jxl.write.Label(13, 0, "UNIDAD ORGANIZACIONAL", headerFormat));
            //15 Ubicación Geográfica
            sheet.addCell(new jxl.write.Label(14, 0, "UBICACIÓN GEOGRÁFICA", headerFormat));
            //16 Grupo Ocupacional
            sheet.addCell(new jxl.write.Label(15, 0, "GRUPO OCUPACIONAL", headerFormat));
            //17 Certificación Presupuestaria
            sheet.addCell(new jxl.write.Label(16, 0, "CERTIFICACIÓN PRESUPUESTARIA", headerFormat));
            //18 Estado Puesto
            sheet.addCell(new jxl.write.Label(17, 0, "ESTADO PUESTO", headerFormat));
            //19 Tipo Identificación
            sheet.addCell(new jxl.write.Label(18, 0, "TIPO DE IDENTIFICACIÓN", headerFormat));
            //20 Número Identificación
            sheet.addCell(new jxl.write.Label(19, 0, "NÚMERO IDENTIFICACIÓN", headerFormat));
            //21 Apellidos Nombres 
            sheet.addCell(new jxl.write.Label(20, 0, "APELLIDOS NOMBRES", headerFormat));
            //22 Unidad Organizacional Por Cambio Administrativo
            sheet.addCell(new jxl.write.Label(21, 0, "UNIDAD ORGANIZACIONAL POR CAMBIO ADMINISTRATIVO", headerFormat));
            //23 Fecha Fin del Cambio Administrativo
            sheet.addCell(new jxl.write.Label(22, 0, "FECHA CAMBIO ADMINISTRATIVO", headerFormat));
            //24 Código Puesto Encargo
            sheet.addCell(new jxl.write.Label(23, 0, "CÓDIGO PUESTO ENCARGO", headerFormat));
            //25 Fecha Inicio Encargo
            sheet.addCell(new jxl.write.Label(24, 0, "FECHA INICIO ENCARGO", headerFormat));
            //26 Fecha Fin Encargo
            sheet.addCell(new jxl.write.Label(25, 0, "FECHA FIN ENCARGO", headerFormat));
            //27 Código Puesto Subrogado
            sheet.addCell(new jxl.write.Label(26, 0, "CÓDIGO PUESTO SUBROGADO", headerFormat));
            //28 Fecha Inicio Subrogación
            sheet.addCell(new jxl.write.Label(27, 0, "FECHA INICIO SUBROGACIÓN", headerFormat));
            //29 Fecha Fin Subrogación
            sheet.addCell(new jxl.write.Label(28, 0, "FECHA FIN SUBROGACIÓN", headerFormat));
            //30 Trámite No
            sheet.addCell(new jxl.write.Label(29, 0, "TRÁMITE No.", headerFormat));

        } catch (WriteException ex) {
            ex.printStackTrace();
            error(getClass().getName(), "Error al escribir cabeceras en el fichero.", ex);
        }
        return sheet;
    }

    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @return String
     */
    public String buscarPuestos() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            busquedaPuestoHelper.getBusquedaPuestoVO().setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().
                    getInstitucionEjercicioFiscal().getId());

            busquedaPuestoHelper.getListaPuestos().clear();
            //busquedaPuestoHelper.getBusquedaPuestoVO().setInicioConsulta(0);
            //busquedaPuestoHelper.getBusquedaPuestoVO().setFinConsulta(200);
            busquedaPuestoHelper.getListaPuestos().addAll(distributivoPuestoServicio.buscar(busquedaPuestoHelper.
                    getBusquedaPuestoVO(), false, obtenerUsuarioConectado(), esRRHH(pi.getValorTexto())));

            busquedaPuestoHelper.setActivo("1");
            actualizarComponente("accordionPanel");
            busquedaPuestoHelper.setTotalRegistros(new Long(busquedaPuestoHelper.getListaPuestos().size()));
            actualizarComponente("totalPanel");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consular los puestos.", e);
        }
        return null;
    }

    /**
     * Recupera las Unidades Organizacionales que están configuradas para la Undiad Desconcentrada a la que pertenece el
     * usuario logueado
     */
    public void recuperarUnidadesOrganizacionalesConfiguradasEnUnidadDesconcentrada() {
        try {
            iniciarCombos(busquedaPuestoHelper.getListaOpcionesUnidadesOrganizacionales());
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            List<UnidadOrganizacional> listaUnidadesOrganizacionales;
            if (esRRHH(pi.getValorTexto())) {
                listaUnidadesOrganizacionales = unidadOrganizacionalDao.buscarPorCodigoLike("1");
            } else {
                listaUnidadesOrganizacionales = desconcentradoServicio.buscarUnidadesDeAcceso(
                        obtenerUsuarioConectado().getServidor().getId(),
                        FuncionesDesconcentradosEnum.DISTRIBUTIVO.getCodigo());
            }
            for (UnidadOrganizacional uo : listaUnidadesOrganizacionales) {
                busquedaPuestoHelper.getListaOpcionesUnidadesOrganizacionales().add(
                        new SelectItem(uo.getId(), uo.getRuta()));
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error genérico", e);
        }
    }

    /**
     *
     */
    public void seleccionarUnidadOrganizacional() {
        try {
            if (busquedaPuestoHelper.getBusquedaPuestoVO().getUnidadAdministrativaId() != null) {
                UnidadOrganizacional uo = unidadOrganizacionalDao.buscarPorId(
                        busquedaPuestoHelper.getBusquedaPuestoVO().getUnidadAdministrativaId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(uo.getRuta());
            } else {
                busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(null);
            }
//
//            boolean encontrada = false;
//            for (SelectItem item : busquedaPuestoHelper.getListaOpcionesUnidadesOrganizacionales()) {
//                if (item.getValue().equals(busquedaPuestoHelper.getBusquedaPuestoVO().getUnidadAdministrativaId())) {
//                    busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(item.getLabel());
//                    encontrada = true;
//                    break;
//                }
//            }
//            if (!encontrada) {
//                busquedaPuestoHelper.getBusquedaPuestoVO().setUnidadAdministrativaNombre(null);
//            }
            ejecutarComandoPrimefaces("unidadAdministrativa.hide();");

        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar la unidad organizacional", e);
        }
    }

    /**
     * Método para la navegación.
     *
     * @return String
     */
    public String cancelarBusqueda() {
        reglaNavegacionDirecta(TramiteControlador.FORMULARIO_ENTIDAD.concat("?est=edt"));
        return null;
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {
            /**
             * poblar los regimenes personales de la institucion.
             */
            List<RegimenLaboral> rls = regimenLaboralServicio.listarTodosRegimenVigentes();
            getBusquedaPuestoHelper().getListaRegimenLaboral().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaRegimenLaboral());
            for (RegimenLaboral rl : rls) {
                getBusquedaPuestoHelper().getListaRegimenLaboral().add(new SelectItem(rl.getId(), rl.getNombre()));
            }

            buscarNivelOcupacionalYEstadosAdmPuestoRegLaboral();
            buscarEscalaOcupacional();

            /**
             * poblar modalidades laborales.
             */
            List<ModalidadLaboral> mls = administracionServicio.listarTodosModalidadLaboralPorNombre(null);
            getBusquedaPuestoHelper().getListaModalidadLaboral().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaModalidadLaboral());
            for (ModalidadLaboral ml : mls) {
                getBusquedaPuestoHelper().getListaModalidadLaboral().add(new SelectItem(ml.getId(), ml.getNombre()));
            }

            /**
             * Poblar unidades organizacionales permitidas.
             */
//            busquedaPuestoHelper.setUnidadesOrganizacionalPermitidas(buscarUnidadesOrganizacionalPermitidas());
//            busquedaPuestoHelper.setUnidadesOrganizacionalPermitidas(busquedaPuestoHelper.
//                    getUnidadesOrganizacionalPermitidas());
            /**
             * Poblar denominaciones de puesto.
             */
            List<DenominacionPuesto> denominaciones
                    = administracionServicio.listarTodosDenominacionPuestoPorNombre(null);
            getBusquedaPuestoHelper().getListaDenominacionPuesto().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaDenominacionPuesto());
            for (DenominacionPuesto denominacion : denominaciones) {
                getBusquedaPuestoHelper().getListaDenominacionPuesto().add(new SelectItem(denominacion.getId(),
                        denominacion.getNombre()));
            }

            /**
             * Poblae estado de puestos.
             */
            List<EstadoPuesto> estadosPuesto = administracionServicio.listarTodosEstadoPuestoPorNombre(null);
            getBusquedaPuestoHelper().getListaEstadoPuesto().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaEstadoPuesto());
            for (EstadoPuesto estado : estadosPuesto) {
                getBusquedaPuestoHelper().getListaEstadoPuesto().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }

            /**
             * Poblando lista de estados administracion puestos regimen laboral guardando en la descripcion de cada
             * elemento el id del regimen laboral al que pertenece para luego filtrar de acuerdo al regimen laboral
             */
            List<EstadoAdministracionPuestoRegimenLaboral> estadosAdmPuestoRegLab = administracionServicio
                    .buscarEstadoAdministracionPuestoRegimenLaboralVigentes();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaEstadoAdminPuestoRegimenLaboral());
            for (EstadoAdministracionPuestoRegimenLaboral estado : estadosAdmPuestoRegLab) {
                busquedaPuestoHelper.getListaEstadoAdminPuestoRegimenLaboral().add(
                        new SelectItem(estado.getId(), estado.getEstadoAdministracionPuesto().getNombre(),
                                String.valueOf(estado.getRegimenLaboral().getId())));
            }

            filtrarEstadosAdmPuestosRegLaboral();

            /**
             * Poblar estado de servidor
             */
            List<EstadoPersonal> estadosPersonal = administracionServicio.listarTodosEstadoPersonalPorNombre(null);
            getBusquedaPuestoHelper().getListaEstadoPersonal().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaEstadoPersonal());
            for (EstadoPersonal estado : estadosPersonal) {
                getBusquedaPuestoHelper().getListaEstadoPersonal().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }
            getBusquedaPuestoHelper().getListaTipoDocumento().clear();
            iniciarCombosTodos(getBusquedaPuestoHelper().getListaTipoDocumento());
            getBusquedaPuestoHelper().getListaTipoDocumento().add(new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            getBusquedaPuestoHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));

            busquedaPuestoHelper.setGeneroArchivo(Boolean.FALSE);
            busquedaPuestoHelper.getListaPuestos().clear();

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * Este metodo es usado para iniciar el proceso de seleccion de una ubucacion geográfica.
     */
    public void iniciarUbicacionGeografica() {
        try {
            iniciarCombos(busquedaPuestoHelper.getListaPais());
            iniciarCombos(busquedaPuestoHelper.getListaProvincia());
            iniciarCombos(busquedaPuestoHelper.getListaCanton());
            iniciarCombos(busquedaPuestoHelper.getListaParroquia());

            List<UbicacionGeografica> paises = administracionServicio.listarTodosPaisesUbicacionGeografica();
            for (UbicacionGeografica pais : paises) {
                busquedaPuestoHelper.getListaPais().add(new SelectItem(pais.getId(), pais.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * Este metodo controla la seleccion de ubicacion geografica.
     */
    public void procesarUbicacionGeografica() {
        try {
            UbicacionGeografica ug;
            if (busquedaPuestoHelper.getBusquedaPuestoVO().getParroquiaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaPuestoHelper.getBusquedaPuestoVO().
                        getParroquiaId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                        ug.getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ", ug.
                        getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.PARROQUIA.getCodigo());
            } else if (busquedaPuestoHelper.getBusquedaPuestoVO().getCantonId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaPuestoHelper.getBusquedaPuestoVO().
                        getCantonId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                        ug.getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.CANTON.
                        getCodigo());
            } else if (busquedaPuestoHelper.getBusquedaPuestoVO().getProvinciaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaPuestoHelper.getBusquedaPuestoVO().
                        getProvinciaId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.PROVINCIA.
                        getCodigo());
            } else if (busquedaPuestoHelper.getBusquedaPuestoVO().getPaisId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaPuestoHelper.getBusquedaPuestoVO().
                        getPaisId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(ug.getNombre());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                busquedaPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.PAIS.
                        getCodigo());
            }
            actualizarComponente("frmPrincipal:".concat("parametrosBusqueda_parroquia"));
            ejecutarComandoPrimefaces("ubcgo.hide();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar ubicación geográfico.", e);
        }
    }

    /**
     * Buscar niveles ocupacionales y filtra los estados administracon de puestos para el regimen laboral.
     */
    public void buscarNivelOcupacionalYEstadosAdmPuestoRegLaboral() {
        try {
            busquedaPuestoHelper.getNivelesOcupacionales().addAll(
                    regimenLaboralServicio.listarTodosNivelOcupacionalPorRegimen(busquedaPuestoHelper.
                            getBusquedaPuestoVO().getRegimenLaboralId()));
            iniciarCombosTodos(busquedaPuestoHelper.getListaNivelOcupacional());
            for (NivelOcupacional no : busquedaPuestoHelper.getNivelesOcupacionales()) {
                busquedaPuestoHelper.getListaNivelOcupacional().add(new SelectItem(no.getId(), no.getNombre()));
            }
            buscarEscalaOcupacional();
            filtrarEstadosAdmPuestosRegLaboral();
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar niveles ocupacionales.", e);
        }
    }

    /**
     * FILTRA LA LISTA DE ESTADOS ADMINISTRACION PUESTO REGIMEN LABORAL DE ACUERDO AL REGIMEN SELECCIONADO
     */
    private void filtrarEstadosAdmPuestosRegLaboral() {
        busquedaPuestoHelper.getBusquedaPuestoVO().setEstadoAdmPuestoRegLabId(null);
        iniciarCombosTodos(busquedaPuestoHelper.getListaFiltradaEstadoAdminPuestoRegimenLaboral());
        if (busquedaPuestoHelper.getBusquedaPuestoVO().getRegimenLaboralId() == null) {
            busquedaPuestoHelper.getListaFiltradaEstadoAdminPuestoRegimenLaboral().addAll(
                    busquedaPuestoHelper.getListaEstadoAdminPuestoRegimenLaboral());
        } else {
            Long idRegLab;
            ListIterator<SelectItem> li = busquedaPuestoHelper.getListaEstadoAdminPuestoRegimenLaboral().listIterator(1);
            while (li.hasNext()) {
                SelectItem item = li.next();
                idRegLab = new Long(item.getDescription());
                if (busquedaPuestoHelper.getBusquedaPuestoVO().getRegimenLaboralId().equals(idRegLab)) {
                    busquedaPuestoHelper.getListaFiltradaEstadoAdminPuestoRegimenLaboral().add(item);
                }
            }
        }

    }

    /**
     * Buscar escalas ocupacionales.
     */
    public void buscarEscalaOcupacional() {
        try {
            List<EscalaOcupacional> lista
                    = regimenLaboralServicio.listarTodosEscalaOcupacionalPorNivelOcup(busquedaPuestoHelper.
                            getBusquedaPuestoVO().getNivelOcupacionalId());
            iniciarCombosTodos(busquedaPuestoHelper.getListaEscalaOcupacional());
            for (EscalaOcupacional eo : lista) {
                busquedaPuestoHelper.getListaEscalaOcupacional().add(new SelectItem(eo.getId(), UtilCadena.concatenar(
                        eo.getNombre(), " - ", eo.getGrado(), " - ", UtilNumeros.getInstancia().formatearMoneda(eo.
                                getRmu()))));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar escalas ocupacionales.", e);
        }
    }

    /**
     * Este metodo busca las provincias segun la region seleccionada.
     */
    public void buscarProvincias() {
        try {
            iniciarCombos(busquedaPuestoHelper.getListaProvincia());
            iniciarCombos(busquedaPuestoHelper.getListaCanton());
            iniciarCombos(busquedaPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> provincias = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaPuestoHelper.getBusquedaPuestoVO().getPaisId());
            for (UbicacionGeografica provincia : provincias) {
                busquedaPuestoHelper.getListaProvincia().add(new SelectItem(provincia.getId(), provincia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar provincia.", e);
        }
    }

    /**
     * Este metodo busca los cantones segun la provincias seleccionada.
     */
    public void buscarCantones() {
        try {
            iniciarCombos(busquedaPuestoHelper.getListaCanton());
            iniciarCombos(busquedaPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> cantones = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaPuestoHelper.getBusquedaPuestoVO().getProvinciaId());
            for (UbicacionGeografica canton : cantones) {
                busquedaPuestoHelper.getListaCanton().add(new SelectItem(canton.getId(), canton.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar cantones.", e);
        }
    }

    /**
     * Este metodo busca las parroquias segun el canton seleccionada.
     */
    public void buscarParroquias() {
        try {
            iniciarCombos(busquedaPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> parroquias = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaPuestoHelper.getBusquedaPuestoVO().getCantonId());
            for (UbicacionGeografica parroquia : parroquias) {
                busquedaPuestoHelper.getListaParroquia().add(new SelectItem(parroquia.getId(), parroquia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar parroquias.", e);
        }
    }

    /**
     * @return the busquedaPuestoHelper
     */
    public BusquedaPuestoHelper getBusquedaPuestoHelper() {
        return busquedaPuestoHelper;
    }

    /**
     * @param busquedaPuestoHelper the busquedaPuestoHelper to set
     */
    public void setBusquedaPuestoHelper(final BusquedaPuestoHelper busquedaPuestoHelper) {
        this.busquedaPuestoHelper = busquedaPuestoHelper;
    }

    /**
     * @return the tramiteHelper
     */
    public TramiteHelper getTramiteHelper() {
        return tramiteHelper;
    }

    /**
     * @param tramiteHelper the tramiteHelper to set
     */
    public void setTramiteHelper(final TramiteHelper tramiteHelper) {
        this.tramiteHelper = tramiteHelper;
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

    private NivelOcupacional buscarNivelOcupacional() {
        for (NivelOcupacional up : busquedaPuestoHelper.getNivelesOcupacionales()) {
            if (busquedaPuestoHelper.getBusquedaPuestoVO().getNivelOcupacionalId() != null
                    && up.getId().equals(busquedaPuestoHelper.getBusquedaPuestoVO().getNivelOcupacionalId())) {
                return up;
            }
        }
        return null;
    }

}
