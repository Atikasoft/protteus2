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
import ec.com.atikasoft.proteus.controlador.helper.BusquedaServidorHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoUbicacionGeograficaEnum;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.dom4j.DocumentException;
import org.primefaces.component.datatable.DataTable;

/**
 * Controlador de Busqueda Puesto.
 *
 *
 */
@ManagedBean(name = "busquedaServidorBean")
@ViewScoped
public class BusquedaServidorControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(BusquedaServidorControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/procesos/tramite/busqueda_puesto.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaServidorHelper}")
    private BusquedaServidorHelper busquedaServidorHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Constructor por defecto.
     */
    public BusquedaServidorControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            iniciarOpciones();
            busquedaServidorHelper.setBusquedaServidorVO(new BusquedaServidorVO());
            busquedaServidorHelper.setNombreArchivo("Reporte_Servidor_" + UtilFechas.formatear2(new Date()));
//            busquedaServidorHelper.setSeleccionarUnidadOrganizacional(
//                    administracionServicio.esUnidadOrganizacionDeRecursosHumanos(obtenerUsuarioConectado()));
            if (!busquedaServidorHelper.getSeleccionarUnidadOrganizacional()) {
                busquedaServidorHelper.getBusquedaServidorVO().setUnidadAdministrativaId(obtenerUsuarioConectado().
                        getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
                busquedaServidorHelper.getBusquedaServidorVO().setUnidadAdministrativaNombre(
                        buscarNombreUnidadOrganizacional(obtenerUsuarioConectado().
                                getDistributivoDetalle().getDistributivo().getUnidadOrganizacional()));
            }
        } catch (Exception e) {
        }

    }

    /**
     * actualiza la paginación.
     */
    public void refreshPagination() {
        ((DataTable) FacesContext.getCurrentInstance().
                getViewRoot().
                findComponent("frmPrincipal").
                findComponent("busquedaServidorHelper_listaPuestos")).setFirst(0);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param nemonico
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
             * Poblae estado de servidor
             */
            List<EstadoPersonal> estadosPersonal = administracionServicio.listarTodosEstadoPersonalPorNombre(null);
            iniciarCombosTodos(getBusquedaServidorHelper().getListaEstadoPersonal());
            for (EstadoPersonal estado : estadosPersonal) {
                getBusquedaServidorHelper().getListaEstadoPersonal().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }
            /**
             * llenar estado civil.
             */
            getBusquedaServidorHelper().getListaTipoEstadoCivil().clear();
            iniciarCombosTodos(getBusquedaServidorHelper().getListaTipoEstadoCivil());
            List<Catalogo> listaCatalogo = administracionServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ESTADO_CIVIL.getCodigo());
            for (Catalogo g : listaCatalogo) {
                getBusquedaServidorHelper().getListaTipoEstadoCivil().add(new SelectItem(g.getId(), g.getNombre()));
            }
            /**
             * llenar estado civil.
             */
            getBusquedaServidorHelper().getListaCatalogoGenero().clear();
            iniciarCombosTodos(getBusquedaServidorHelper().getListaCatalogoGenero());
            List<Catalogo> listaCatalogoGenero = administracionServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.GENERO.getCodigo());
            for (Catalogo g : listaCatalogoGenero) {
                getBusquedaServidorHelper().getListaCatalogoGenero().add(new SelectItem(g.getId(), g.getNombre()));
            }
            /**
             * Poblar unidades organizacionales.
             */
            //busquedaServidorHelper.setUnidadesOrganizacionalPermitidas(buscarUnidadesOrganizacionalPermitidas());

            iniciarCombosTodos(getBusquedaServidorHelper().getListaTipoDocumento());
            getBusquedaServidorHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            getBusquedaServidorHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * Este metodo es usado para iniciar el proceso de seleccion de una
     * ubucacion geográfica.
     */
    public void iniciarUbicacionGeografica() {
        try {
            iniciarCombos(busquedaServidorHelper.getListaPais());
            iniciarCombos(busquedaServidorHelper.getListaProvincia());
            iniciarCombos(busquedaServidorHelper.getListaCanton());
            iniciarCombos(busquedaServidorHelper.getListaParroquia());
            busquedaServidorHelper.setBusquedaServidorVO(new BusquedaServidorVO());
            List<UbicacionGeografica> paises = administracionServicio.listarTodosPaisesUbicacionGeografica();
            for (UbicacionGeografica pais : paises) {
                busquedaServidorHelper.getListaPais().add(new SelectItem(pais.getId(), pais.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

//    /**
//     * Este método procesa la busqueda de puestos por filtro.
//     *
//     * @return String
//     */
//    public String buscarServidores() {
//        try {
//            busquedaServidorHelper.getListaServidores().clear();
//            busquedaServidorHelper.getBusquedaServidorVO().setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().
//                    getInstitucionEjercicioFiscal().getId());
//            busquedaServidorHelper.getListaServidor().addAll(servidorServicio.buscar(busquedaServidorHelper.
//                    getBusquedaServidorVO()));
//            busquedaServidorHelper.setActivo("1");
//            actualizarComponente("accordionPanel");
//        } catch (Exception e) {
//            error(getClass().getName(), "Error al consular los servidores.", e);
//        }
//        return null;
//    }
    /**
     * Este metodo controla la seleccion de ubicacion geografica.
     */
    public void procesarUbicacionGeografica() {
        try {
            UbicacionGeografica ug;
            if (busquedaServidorHelper.getBusquedaServidorVO().getParroquiaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaServidorHelper.getBusquedaServidorVO().
                        getParroquiaId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                        ug.getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ", ug.
                        getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaId(ug.getId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.PARROQUIA.
                        getCodigo());
            } else if (busquedaServidorHelper.getBusquedaServidorVO().getCantonId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaServidorHelper.getBusquedaServidorVO().
                        getCantonId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                        ug.getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaId(ug.getId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.CANTON.
                        getCodigo());
            } else if (busquedaServidorHelper.getBusquedaServidorVO().getProvinciaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaServidorHelper.getBusquedaServidorVO().
                        getProvinciaId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaNombre(UtilCadena.concatenar(ug.
                        getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaId(ug.getId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.PROVINCIA.
                        getCodigo());
            } else if (busquedaServidorHelper.getBusquedaServidorVO().getPaisId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(busquedaServidorHelper.getBusquedaServidorVO().
                        getPaisId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaNombre(ug.getNombre());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaId(ug.getId());
                busquedaServidorHelper.getBusquedaServidorVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.PAIS.
                        getCodigo());
            }
            actualizarComponente("frmPrincipal:".concat("parametrosBusqueda_parroquia"));
            ejecutarComandoPrimefaces("ubcgo.hide();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar ubicación geográfico.", e);
        }
    }

    /**
     * Este metodo busca las provincias segun la region seleccionada.
     */
    public void buscarProvincias() {
        try {
            iniciarCombos(busquedaServidorHelper.getListaProvincia());
            iniciarCombos(busquedaServidorHelper.getListaCanton());
            iniciarCombos(busquedaServidorHelper.getListaParroquia());
            List<UbicacionGeografica> provincias = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaServidorHelper.getBusquedaServidorVO().getPaisId());
            for (UbicacionGeografica provincia : provincias) {
                busquedaServidorHelper.getListaProvincia().add(new SelectItem(provincia.getId(), provincia.getNombre()));
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
            iniciarCombos(busquedaServidorHelper.getListaCanton());
            iniciarCombos(busquedaServidorHelper.getListaParroquia());
            List<UbicacionGeografica> cantones = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaServidorHelper.getBusquedaServidorVO().getProvinciaId());
            for (UbicacionGeografica canton : cantones) {
                busquedaServidorHelper.getListaCanton().add(new SelectItem(canton.getId(), canton.getNombre()));
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
            iniciarCombos(busquedaServidorHelper.getListaParroquia());
            List<UbicacionGeografica> parroquias = administracionServicio.listarTodosIdUbicacionGeografica(
                    busquedaServidorHelper.getBusquedaServidorVO().getCantonId());
            for (UbicacionGeografica parroquia : parroquias) {
                busquedaServidorHelper.getListaParroquia().add(new SelectItem(parroquia.getId(), parroquia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar parroquias.", e);
        }
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

    /**
     * @return the busquedaServidorHelper
     */
    public BusquedaServidorHelper getBusquedaServidorHelper() {
        return busquedaServidorHelper;
    }

    /**
     * @param busquedaServidorHelper the busquedaServidorHelper to set
     */
    public void setBusquedaServidorHelper(final BusquedaServidorHelper busquedaServidorHelper) {
        this.busquedaServidorHelper = busquedaServidorHelper;
    }

    public void postProcessXLS(Object document) throws IOException, DocumentException {

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

}
