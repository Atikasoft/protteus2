/*
 *  AnticipoConsultaControlador.java
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
 *  03/01/2014
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.AnticipoConsultaHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoModalidadEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AnticipoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.dom4j.DocumentException;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * Controlador de Busqueda Puesto.
 *
 *
 */
@ManagedBean(name = "anticipoConsultaBean")
@ViewScoped
public class AnticipoConsultaControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(AnticipoConsultaControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/consultas/consulta_anticipo.jsf";

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
     * Servicio de servidor.
     */
    @EJB
    private AnticipoServicio anticipoServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{anticipoConsultaHelper}")
    private AnticipoConsultaHelper anticipoConsultaHelper;
    /**
     * Servicio de servidor.
     */
    @EJB
    private DistributivoPuestoServicio distributivoServicio;

    /**
     * Constructor por defecto.
     */
    public AnticipoConsultaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String nemonico) {
        return TipoDocumentoEnum.obtenerNombre(nemonico);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion estado del anticipo
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoAnticipo(final String codigo) {
        return EstadoAnticipoEnum.obtenerDescripcion(codigo);
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
            anticipoConsultaHelper.getListaAnticipos().clear();

            anticipoConsultaHelper.getConsultaAnticipoVO().setEscalaOcupacionalNombre(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setEscalaOcupacionalId(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setUnidadOrganizacionalId(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setUnidadOrganizacionalNombre(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setTipoAnticipoId(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setEstadoAnticipo(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setTipoDocumentoServidor(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setNumeroDocumentoServidor(null);

            anticipoConsultaHelper.getConsultaAnticipoVO().setApellidosNombresServidor(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setFechaSolicitudDesde(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setFechaSolicitudHasta(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setValorDesde(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setValorHasta(null);
            anticipoConsultaHelper.getConsultaAnticipoVO().setModalidadLaboralId(null);

            if (anticipoConsultaHelper.getVistaAnticipo() != null) {
                anticipoConsultaHelper.getVistaAnticipo().setServidorApellidosNombres(null);
                anticipoConsultaHelper.getConsultaAnticipoVO().setServidorId(null);
            }

            /**
             * Poblar estado de puesto.
             */
            List<EstadoPuesto> estadosPersonal = administracionServicio.listarTodosEstadoPuestoPorNombre(null);
            anticipoConsultaHelper.getListaEstadoPuesto().clear();
            iniciarCombosTodos(anticipoConsultaHelper.getListaEstadoPuesto());
            for (EstadoPuesto estado : estadosPersonal) {
                anticipoConsultaHelper.getListaEstadoPuesto().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }
            /**
             * llenar estado anticipo.
             */
            anticipoConsultaHelper.getListaOpcionEstadoAnticipo().clear();
            iniciarCombosTodos(anticipoConsultaHelper.getListaOpcionEstadoAnticipo());
            for (EstadoAnticipoEnum t : EstadoAnticipoEnum.values()) {
                anticipoConsultaHelper.getListaOpcionEstadoAnticipo().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
            }

            /**
             * Poblar tipo de anticipo.
             */
            List<TipoAnticipo> tipoAnticipos = anticipoServicio.listarTodosTipoAnticiposVigentes();
            anticipoConsultaHelper.getListaOpcionTipoAnticipo().clear();
            iniciarCombosTodos(anticipoConsultaHelper.getListaOpcionTipoAnticipo());
            for (TipoAnticipo tipo : tipoAnticipos) {
                anticipoConsultaHelper.getListaOpcionTipoAnticipo().add(new SelectItem(tipo.getId(),
                        tipo.getNombre()));
            }

////            buscarServidoresInstitucion();
//            iniciarOpcionesDenominacionPuestos();
            cargarArbol();
            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = administracionServicio.listarUnidadOrganizacionalVigente();
            anticipoConsultaHelper.setListaUnidadesOrganizacionales(unidades);

            iniciarCombosTodos(anticipoConsultaHelper.getListaTipoDocumento());
            anticipoConsultaHelper.getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            anticipoConsultaHelper.getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));

            /**
             * LLenar las Modalidades Laborales.
             */
            anticipoConsultaHelper.getListaOpcionModalidadLaboral().clear();
            iniciarCombosTodos(anticipoConsultaHelper.getListaOpcionModalidadLaboral());
            List<ModalidadLaboral> listaModalidad = administracionServicio.listarTodosModalidadLaboralVigentes();
            if (!listaModalidad.isEmpty()) {
                for (ModalidadLaboral mod : listaModalidad) {
                    anticipoConsultaHelper.getListaOpcionModalidadLaboral().add(new SelectItem(mod.getId(), mod.getNombre()));
                }
            }

            /**
             * Nombre del archivo.
             */
            anticipoConsultaHelper.setNombreArchivo("Reporte_Anticipos_" + UtilFechas.formatear2(new Date()));
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * Obtiene lista de los servidores de nombramiento. La lista es obtenida desde el distributivo con los cargos que se
     * encuentren no vacantes
     *
     * @return
     */
    public String buscarServidoresInstitucion() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            BusquedaPuestoVO ser = new BusquedaPuestoVO();
            ser.setTipoModalidad(TipoModalidadEnum.NOMBRAMIENTO.getCodigo());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            List<DistributivoDetalle> lista = distributivoServicio.buscar(ser, false, obtenerUsuarioConectado(),
                    esRRHH(pi.getValorTexto()));
            anticipoConsultaHelper.getListaOpcionGarante().clear();
            anticipoConsultaHelper.setListaOpcionGarante(lista);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda de garantes ", ex);
        }
        return null;
    }

    /**
     * Permite cargar un Tree con los datos de Regimen Laboral, Nivel Ocupacional, Escala Ocupacional
     *
     * @return
     */
    public String cargarArbol() {
        try {

            anticipoConsultaHelper.setRootRegimen(new DefaultTreeNode("root", null));
            List<RegimenLaboral> lista = regimenLaboralServicio.listarTodosRegimenLaboral();
            TreeNode nodoPrincipal = new DefaultTreeNode(lista.get(0),
                    anticipoConsultaHelper.getRootRegimen());
            lista.remove(0);
            for (RegimenLaboral reg : lista) {

                TreeNode nodoRegimen = new DefaultTreeNode(reg, nodoPrincipal);
                if (reg.getListaNivelOcupacional().size() > 0) {
                    for (NivelOcupacional nivel : reg.getListaNivelOcupacional()) {
                        if (nivel.getVigente()) {
                            TreeNode nodoNivel = new DefaultTreeNode(nivel, nodoRegimen);
                            for (EscalaOcupacional escala : nivel.getListaEscalaOcupacional()) {
                                if (escala.getVigente()) {
                                    TreeNode nodoEscala = new DefaultTreeNode(escala, nodoNivel);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la busqueda regimen laboral ", ex);
        }
        return null;
    }

    /**
     * Este método procesa la busqueda de los anticipos.
     *
     * @return String
     */
    public String buscar() {
        try {
            Long idejericicio = obtenerUsuarioConectado().
                    getInstitucionEjercicioFiscal().getId();
            anticipoConsultaHelper.getListaAnticipos().clear();

            anticipoConsultaHelper.getConsultaAnticipoVO().setInstitucionEjercicioFiscalId(idejericicio);
            anticipoConsultaHelper.getListaAnticipos().addAll(anticipoServicio.buscar(anticipoConsultaHelper.
                    getConsultaAnticipoVO()));
            anticipoConsultaHelper.setActivo("1");
            actualizarComponente("accordionPanel");
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los anticipos.", e);
        }
        return null;
    }

    /**
     * Permite obtener los registros del plan de pago de un anticipo.
     *
     * @param idAnticipo
     * @return
     */
    public List<AnticipoPlanPago> buscarPlanPagoAnticipo(final Long idAnticipo) {
        List<AnticipoPlanPago> lista = new ArrayList<AnticipoPlanPago>();
        try {
            anticipoConsultaHelper.getListaPlanPago().clear();
            if (idAnticipo != null) {
                lista = anticipoServicio.listarTodosPlanPagoAnticiposPorAnticipo(idAnticipo);
                anticipoConsultaHelper.getListaPlanPago().addAll(lista);
            }

        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la búsqueda plan pago ", ex);
        }
        return lista;
    }

    /**
     * Permite obtener los registros del pago de un plan de pago de un anticipo.
     *
     * @param idPlanPagoAnticipo
     * @return
     */
    public List<AnticipoPago> buscarPagosAnticipo(final Long idPlanPagoAnticipo) {
        List<AnticipoPago> lista = new ArrayList<AnticipoPago>();
        try {
            anticipoConsultaHelper.getListaPagosAnticipo().clear();
            if (idPlanPagoAnticipo != null) {
                lista = anticipoServicio.listarTodosAnticipoPagoPorPlanPago(idPlanPagoAnticipo);
                anticipoConsultaHelper.getListaPagosAnticipo().addAll(lista);
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la búsqueda pagos ", ex);
        }
        return lista;
    }

    /**
     * Este metodo llena las opciones para el combo de Denominacion de puestos.
     */
    private void iniciarOpcionesDenominacionPuestos() {
        try {
            List<DenominacionPuesto> lista = administracionServicio.listarTodosDenominacionPuestoVigentes();

            anticipoConsultaHelper.getListaOpcionDenominacionPuesto().clear();
            iniciarCombos(anticipoConsultaHelper.getListaOpcionDenominacionPuesto());
            for (DenominacionPuesto r : lista) {
                anticipoConsultaHelper.getListaOpcionDenominacionPuesto().add(new SelectItem(r.getId(), r.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda denominacion de puestos", ex);
        }
    }

    /**
     * Setea el valor del nodo seleccionado.
     *
     * @param event
     */
    public void onNodeSelectRegimen(NodeSelectEvent event) {
        if (anticipoConsultaHelper.getEscalaSeleccionada().getData() instanceof EscalaOcupacional) {
            EscalaOcupacional un = (EscalaOcupacional) anticipoConsultaHelper.getEscalaSeleccionada().getData();
            anticipoConsultaHelper.getConsultaAnticipoVO().setEscalaOcupacionalId(un.getId());
            anticipoConsultaHelper.getConsultaAnticipoVO().setEscalaOcupacionalNombre(un.getNombreCompleto());
            ejecutarComandoPrimefaces("dlgRegimenLaboral.hide();");
        } else {
            mostrarMensajeEnPantalla("Debe seleccionar una Escala Ocupacional", FacesMessage.SEVERITY_INFO);
        }
    }

    /**
     * @return the anticipoConsultaHelper
     */
    public AnticipoConsultaHelper getAnticipoConsultaHelper() {
        return anticipoConsultaHelper;
    }

    /**
     * @param anticipoConsultaHelper the anticipoConsultaHelper to set
     */
    public void setAnticipoConsultaHelper(AnticipoConsultaHelper anticipoConsultaHelper) {
        this.anticipoConsultaHelper = anticipoConsultaHelper;
    }

    public void postProcessXLS(Object document) throws IOException, DocumentException {

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        int columnaValor = 0;
        int columnaSaldo = 0;
        String valor = obtenerProperties().getString("ec.gob.mrl.smp.administracion.anticipo.valor");
        String saldo = obtenerProperties().getString("ec.gob.mrl.smp.administracion.anticipo.saldo");

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
            if (cell.getStringCellValue().equals(valor)) {
                columnaValor = i;
            } else if (cell.getStringCellValue().equals(saldo)) {
                columnaSaldo = i;
            }
        }

        /*Formateo numerico de columnas valor y saldo*/
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            HSSFCell celdaValor = sheet.getRow(j).getCell(columnaValor);
            HSSFCell celdaSaldo = sheet.getRow(j).getCell(columnaSaldo);
            HSSFCellStyle decimales = wb.createCellStyle();
            HSSFDataFormat dataFormat = wb.createDataFormat();
            decimales.setDataFormat(dataFormat.getFormat("#,##0.00"));
            if (celdaValor.getStringCellValue().isEmpty()) {
                celdaValor.setCellValue("0.00");
            }
            celdaValor.setCellValue(Double.valueOf(celdaValor.getStringCellValue()));
            celdaValor.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            celdaValor.setCellStyle(decimales);

            if (celdaSaldo.getStringCellValue().isEmpty()) {
                celdaSaldo.setCellValue("0.00");
            }
            celdaSaldo.setCellValue(Double.valueOf(celdaSaldo.getStringCellValue()));
            celdaSaldo.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            celdaSaldo.setCellStyle(decimales);
        }
    }
}
