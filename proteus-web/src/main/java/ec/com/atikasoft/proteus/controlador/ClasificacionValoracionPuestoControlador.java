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
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_GENERICO;
import ec.com.atikasoft.proteus.controlador.helper.ClasificacionValoracionPuestoHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.dao.EstadoAdministracionPuestoRegimenLaboralDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.util.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "clasificacionValoracionPuestoBean")
@ViewScoped
public class ClasificacionValoracionPuestoControlador extends BaseControlador implements Serializable {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/clasificacion_valoracion_puestos/clasificacion_valoracion_puesto.xhtml";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/clasificacion_valoracion_puestos/lista_clasificacion_valoracion_puestos.jsf";

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
     * Servicio de nómina.
     */
    @EJB
    private NominaServicio nominaServicio;

    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private EstadoAdministracionPuestoRegimenLaboralDao estadoAdminPuestoRegimenLaboralDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{clasificacionValoracionPuestoHelper}")
    private ClasificacionValoracionPuestoHelper clasificacionValoracionPuestoHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Constructor por defecto.
     */
    public ClasificacionValoracionPuestoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setClasificacionValoracionPuestoHelper(clasificacionValoracionPuestoHelper);
        iniciardatosBusqueda();
        clasificacionValoracionPuestoHelper.setNombreArchivo("Reporte_Distributivo_" + UtilFechas.formatear2(new Date()));
        clasificacionValoracionPuestoHelper.setNombreArchivoHistorico("Reporte_Historico_" + UtilFechas.formatear2(new Date()));
        clasificacionValoracionPuestoHelper.setNombreArchivoNomina("Reporte_Nomina_" + UtilFechas.formatear2(new Date()));
        clasificacionValoracionPuestoHelper.setTotalRegistros(0L);

    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {
            iniciarComboRegimenesLaborales();
            buscarNivelOcupacional();
            buscarEscalaOcupacional();
            iniciarComboModalidadLaboral();
            iniciarComboUnidadesPresupuestarias();
            iniciarComboDenominacionesPuesto();
            iniciarComboTiposDocumento();
            iniciarComboEstadoAdministracionPuestoRegimenLaboral();

            clasificacionValoracionPuestoHelper.setGeneroArchivo(Boolean.FALSE);
            clasificacionValoracionPuestoHelper.getListaPuestos().clear();

        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * poblar los tipos de documento de identificacion
     */
    private void iniciarComboTiposDocumento() {
        try {
            iniciarCombosTodos(clasificacionValoracionPuestoHelper.getListaTipoDocumento());
            clasificacionValoracionPuestoHelper.getListaTipoDocumento().add(
                    new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(), TipoDocumentoEnum.CEDULA.getNombre()));
            clasificacionValoracionPuestoHelper.getListaTipoDocumento().add(
                    new SelectItem(TipoDocumentoEnum.PASAPORTE.getNemonico(), TipoDocumentoEnum.PASAPORTE.getNombre()));

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda Tipo Documento Identificacion", ex);
        }
    }

    /**
     * poblar los regimenes laboral.
     */
    private void iniciarComboRegimenesLaborales() {
        try {
            List<RegimenLaboral> rls = regimenLaboralServicio.listarTodosRegimenVigentes();
            clasificacionValoracionPuestoHelper.getListaRegimenLaboral().clear();
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaRegimenLaboral());
            for (RegimenLaboral rl : rls) {
                clasificacionValoracionPuestoHelper.getListaRegimenLaboral().add(
                        new SelectItem(rl.getId(), rl.getNombre()));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda Regimen Laboral", ex);
        }
    }

    /**
     * poblar los estados puesto.
     */
    private void iniciarComboEstadosPuesto() {
        try {
            List<EstadoPuesto> estadosPuesto = administracionServicio.listarTodosEstadoPuestoPorNombre(null);
            clasificacionValoracionPuestoHelper.getListaEstadoPuesto().clear();
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaEstadoPuesto());
            for (EstadoPuesto estado : estadosPuesto) {
                clasificacionValoracionPuestoHelper.getListaEstadoPuesto().add(new SelectItem(estado.getId(),
                        estado.getNombre()));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda Estados Puesto", ex);
        }
    }

    /**
     * Poblar opciones denominaciones de puesto.
     */
    private void iniciarComboDenominacionesPuesto() {
        try {
            List<DenominacionPuesto> denominaciones
                    = administracionServicio.listarTodosDenominacionPuestoPorNombre(null);
            clasificacionValoracionPuestoHelper.getListaOpcionesDenominacionPuesto().clear();
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaOpcionesDenominacionPuesto());
            for (DenominacionPuesto denominacion : denominaciones) {
                clasificacionValoracionPuestoHelper.getListaOpcionesDenominacionPuesto().add(
                        new SelectItem(denominacion.getId(), denominacion.getNombre()));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda Denominacion Puesto", ex);
        }
    }

    /**
     *
     */
    public void seleccionarDenominacionPuesto() {
        try {
            for (DenominacionPuesto dp : clasificacionValoracionPuestoHelper.getListaDenominacionPuesto()) {
                if (clasificacionValoracionPuestoHelper
                        .getDistributivoDetalle().getUnidadPresupuestaria().getId().equals(dp.getId())) {
//                    clasificacionValoracionPuestoHelper.getDistributivoDetalle().setIdDenominacionPuesto(dp.getId());
                    clasificacionValoracionPuestoHelper.getDistributivoDetalle().setDenominacionPuesto(dp);
//                    actualizarComponente(":frmPrincipal:estadoPuesto");
                    break;
                }
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al seleccionar denominacion puesto", e);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Modalidades Laborales vigentes
     */
    private void iniciarComboModalidadLaboral() {
        try {
            List<ModalidadLaboral> mls = administracionServicio.listarTodosModalidadLaboralPorNombre(null);
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaModalidadLaboral());
            clasificacionValoracionPuestoHelper.getListaModalidadLaboral().remove(0);
            for (ModalidadLaboral ml : mls) {
                clasificacionValoracionPuestoHelper.getListaModalidadLaboral().add(
                        new SelectItem(ml.getId(), ml.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda Modalidad Laboral ", ex);
        }
    }

    /**
     * Este metodo llena las opciones para estado administracion puesto
     */
    private void iniciarComboEstadoAdministracionPuestoRegimenLaboral() {
        try {
            List<EstadoAdministracionPuestoRegimenLaboral> l = estadoAdminPuestoRegimenLaboralDao
                    .buscarPorRegimenLaboralId(
                            clasificacionValoracionPuestoHelper.getDistributivoDetalle().
                            getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId());

            iniciarCombos(clasificacionValoracionPuestoHelper.getListaOpcionesEstadoAdminPuestoRegimenLaboral());

            for (EstadoAdministracionPuestoRegimenLaboral ea : l) {
                clasificacionValoracionPuestoHelper.getListaOpcionesEstadoAdminPuestoRegimenLaboral().add(
                        new SelectItem(ea.getId(), ea.getEstadoAdministracionPuesto().getNombre()));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la búsqueda Administración Puesto Régimen Laboral", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de UnidadesPresupuestarias vigentes
     */
    private void iniciarComboUnidadesPresupuestarias() {
        try {
            clasificacionValoracionPuestoHelper.setListaUnidadesPresupuestarias(
                    administracionServicio.listarTodosUnidadesPresupuestarias());

            iniciarCombos(clasificacionValoracionPuestoHelper.getListaOpcionesUnidadesPresupuestarias());
            clasificacionValoracionPuestoHelper.getListaOpcionesUnidadesPresupuestarias().remove(0);

            for (UnidadPresupuestaria up : clasificacionValoracionPuestoHelper.getListaUnidadesPresupuestarias()) {
                clasificacionValoracionPuestoHelper.getListaOpcionesUnidadesPresupuestarias().add(
                        new SelectItem(up.getId(), UtilCadena.concatenar(up.getSector().getNombre(), " / ", up.getNombre())));
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la búsqueda Unidad Presupuestaria", ex);
        }
    }

    /**
     *
     */
    public void seleccionarUnidadPresupuestaria() {
        try {
            for (UnidadPresupuestaria up : clasificacionValoracionPuestoHelper.getListaUnidadesPresupuestarias()) {
                if (clasificacionValoracionPuestoHelper
                        .getDistributivoDetalle().getUnidadPresupuestaria().getId().equals(up.getId())) {
                    clasificacionValoracionPuestoHelper.getDistributivoDetalle().setUnidadPresupuestaria(up);
                    break;
                }
            }
//            actualizarComponente(":frmPrincipal");

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al seleccionar la Unidad Presupuestaria", e);
        }
    }

    /**
     * llena ubiacion geografica.
     *
     */
    public void llenarUnidadesOrganizacionales() {
        try {
            TreeNode nodoPrincipal;
            TreeNode nodoPadre;
            /*
             * cargar lista de registros padre iniciales
             */
            clasificacionValoracionPuestoHelper.setListaUnidadesOrganizacionales(
                    administracionServicio.listarTodosUnidadOrganizacional());
            /*
             * carga el primer registro (nodo principal)
             */

            nodoPrincipal = new DefaultTreeNode(
                    clasificacionValoracionPuestoHelper.getListaUnidadesOrganizacionales().get(0), null);
            clasificacionValoracionPuestoHelper.setRoot(nodoPrincipal);
            //unidadOrganizacionalHelper.getListaUnidadOrganizacional().remove(0);
            /*
             * cargar los primeros nodos
             */
//            nodoPadre = nodoPrincipal;

            for (UnidadOrganizacional un : clasificacionValoracionPuestoHelper.getListaUnidadesOrganizacionales()) {
                if (un.getVigente()) {
                    nodoPadre = new DefaultTreeNode(un, nodoPrincipal);
                    /*
                     * cargar los hijos
                     */
                    if (un.getId() != null) {
                        obtenerHijosUOrg(un, nodoPadre);
                    }
                }
            }
        } catch (Exception e) {
            // notificarError(e);
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     * Obtiene los hijos de cada undiad organizacional recursivamente
     *
     * @param registroPadre
     * @param nodoPadre
     */
    private void obtenerHijosUOrg(UnidadOrganizacional registroPadre, TreeNode nodoPadre) {
        try {
            for (UnidadOrganizacional unidad : registroPadre.getListaUnidadesOrganizacionales()) {
                if (unidad.getVigente()) {
                    TreeNode nodoHijo = new DefaultTreeNode(unidad, nodoPadre);
                    if (!unidad.getListaUnidadesOrganizacionales().isEmpty()) {
                        obtenerHijosUOrg(unidad, nodoHijo);
                    }
                }
            }
        } catch (Exception e) {
            error(getClass().getName(), ERROR_GENERICO, e);
        }
    }

    /**
     *
     * @return
     */
    public String seleccionarUnidadOrg() {
//        clasificacionValoracionPuestoHelper.getDistributivoDetalle().getDistributivo().setIdUnidadOrganizacional(
//                clasificacionValoracionPuestoHelper.getUo().getId());
//        clasificacionValoracionPuestoHelper.getDistributivoDetalle().getDistributivo().setUnidadOrganizacional(
//                clasificacionValoracionPuestoHelper.getUo());
        mostrarMensajeEnPantalla("Unidad Organizacional Seleccionada satisfacoriamente", FacesMessage.SEVERITY_INFO);
        return null;
    }

    /**
     * resetea el formulario de busqueda de puesto
     */
    public void iniciardatosBusqueda() {
        clasificacionValoracionPuestoHelper.getListaPuestos().clear();
        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setNumeroDocumentoServidor(null);
        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setNombreServidor(null);
        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setCodigoPuesto(null);
        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setEstadoPuestoId(null);
        iniciarComboEstadosPuesto();
    }

    /**
     *
     * @return
     */
    public String iniciarEdicion() {
        iniciarOpciones();
        clasificacionValoracionPuestoHelper.setEsNuevo(Boolean.FALSE);
        clasificacionValoracionPuestoHelper.setIdModalidadLaboralSeleccionada(
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().getDistributivo().getModalidadLaboral().getId());
        clasificacionValoracionPuestoHelper.setUo(
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional());
        clasificacionValoracionPuestoHelper.setIdEstadoAdminPuestoRegimenLaboralSeleccionado(
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().getEstadosAdmPuestosRegimenLaboralId());
        clasificacionValoracionPuestoHelper.getDistributivoDetalle().setObservacionUltimaModificacion("");
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String regresar() {
        iniciardatosBusqueda();
        return LISTA_ENTIDAD;
    }

    /**
     * METODO Q VALIDA PARA GENERAR EL ARCHIVO MASIVO.
     *
     * @return
     */
    /*public String generarArchivo() {
     try {
     clasificacionValoracionPuestoHelper.setListaSelecto(new ArrayList<DistributivoDetalle>());
     clasificacionValoracionPuestoHelper.setSeleccionado(new HashMap<Integer, DistributivoDetalle>());
     int posicion = 1;
     for (DistributivoDetalle dd : clasificacionValoracionPuestoHelper.getListaPuestos()) {
     if (dd.getSelecto() != null && dd.getSelecto()) {
     clasificacionValoracionPuestoHelper.getListaSelecto().add(dd);
     clasificacionValoracionPuestoHelper.getSeleccionado().put(posicion, dd);
     }
     posicion++;
     }
     if (clasificacionValoracionPuestoHelper.getListaSelecto().isEmpty()) {
     mostrarMensajeEnPantalla("ec.gob.mrl.smp.pantalla5.edicionTramite.mensaje.archivoMasivo.sinSeleccionar",
     FacesMessage.SEVERITY_INFO);
     } else {
     clasificacionValoracionPuestoHelper.setGeneroArchivo(Boolean.TRUE);
     //                escribirExcel(tramiteHelper.getTramite(), clasificacionValoracionPuestoHelper.getListaSelecto());
     mostrarMensajeEnPantalla("Archivo Generado con exito", FacesMessage.SEVERITY_INFO);
     }
     } catch (Exception e) {
     error(getClass().getName(), "Error al generar archivo", e);
     }
     return null;
     }*/
    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param nemonico String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String nemonico) {
        return TipoDocumentoEnum.obtenerNombre(nemonico);
    }

    /**
     * GUARDA LOS DATOS DEL PUESTO.
     *
     * @return
     */
    public String guardar() {
        try {
            String nroPartidaIndividual = clasificacionValoracionPuestoHelper
                    .getDistributivoDetalle().getPartidaIndividual();
            if (nroPartidaIndividual != null && !nroPartidaIndividual.trim().isEmpty()) {
                DistributivoDetalle ddOtro = distributivoPuestoServicio
                        .buscarPorPartidaIndividual(nroPartidaIndividual);
                if (ddOtro != null
                        && !ddOtro.getId().equals(
                                clasificacionValoracionPuestoHelper.getDistributivoDetalle().getId())) {
                    mostrarMensajeEnPantalla(
                            "El número de partida individual ya existe para otro puesto.",
                            FacesMessage.SEVERITY_ERROR);
                    return null;
                }

            }
            iniciarDatosEntidad(clasificacionValoracionPuestoHelper.getDistributivoDetalle(), Boolean.FALSE);
            Distributivo distributivo = distributivoPuestoServicio.buscarDistributivo(
                    clasificacionValoracionPuestoHelper.getUo().getId(),
                    clasificacionValoracionPuestoHelper.getIdModalidadLaboralSeleccionada(),
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

            if (distributivo == null) {
                distributivo = new Distributivo();
                iniciarDatosEntidad(distributivo, Boolean.TRUE);
                distributivo.setContadorPartida(0L);

                distributivo.setIdModalidadLaboral(
                        clasificacionValoracionPuestoHelper.getIdModalidadLaboralSeleccionada());
                ModalidadLaboral ml = administracionServicio.buscarModalidadLaboral(
                        distributivo.getIdModalidadLaboral());
                distributivo.setModalidadLaboral(ml);

                distributivo.setIdUnidadOrganizacional(clasificacionValoracionPuestoHelper.getUo().getId());
                UnidadOrganizacional uo = administracionServicio.buscarUnidadOrganizacional(
                        distributivo.getIdUnidadOrganizacional());
                distributivo.setUnidadOrganizacional(uo);

                distributivo.setIdInstitucionEjercicioFiscal(
                        obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

            } else {
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().setIdDistributivo(distributivo.getId());
            }

            clasificacionValoracionPuestoHelper.getDistributivoDetalle().setEstadosAdmPuestosRegimenLaboralId(
                    clasificacionValoracionPuestoHelper.getIdEstadoAdminPuestoRegimenLaboralSeleccionado());

            clasificacionValoracionPuestoHelper.getDistributivoDetalle().setDistributivo(distributivo);

            distributivoPuestoServicio.guardarClasificacionValoracionPuesto(
                    clasificacionValoracionPuestoHelper.getDistributivoDetalle(), obtenerUsuarioConectado());

            ejecutarComandoPrimefaces("dlgConfirmarConObservacion.hide();");
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception e) {
            e.printStackTrace();
            error(getClass().getName(), "Error al guardar los datos del puestos", e);
        }
        return null;
    }

    /**
     * metodo que genera el archivo excel de la consulta del distributivo.
     *
     * @return
     */
    /*public String generarExcel() {
     try {
     if (clasificacionValoracionPuestoHelper.getListaPuestos().size() <= 0) {
     mostrarMensajeEnPantalla(
     "No hay registros, tiene que realizar primero una busqueda del distributivo.",
     FacesMessage.SEVERITY_WARN);
     } else {
     //se crea el archivo segun el nombre.
     File file = new File(clasificacionValoracionPuestoHelper.getNombreArchivo() + ".xls");
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
     datosColumnasConsultaExcel(sheet, clasificacionValoracionPuestoHelper.getListaPuestos());

     //Escribimos los resultados al fichero Excel
     workbook.write();
     workbook.close();

     InputStream is = new ByteArrayInputStream(UtilArchivos.getBytesFromFile(file));

     clasificacionValoracionPuestoHelper.setArchivoDescarga(new DefaultStreamedContent(is, "application/xls",
     clasificacionValoracionPuestoHelper.getNombreArchivo() + ".xls"));
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
     sheet.addCell(new jxl.write.Number(6, x, dd.getEscalaOcupacional().getRmu().longValue(), enteroFormat));
     }
     //8 Sueldo Máximo
     if (dd.getEscalaOcupacional().getRmuMaximo() != null) {
     sheet.addCell(new jxl.write.Number(7, x, dd.getEscalaOcupacional().getRmuMaximo().longValue(), enteroFormat));
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
     sheet.addCell(new jxl.write.Label(13, x, dd.getDistributivo().getUnidadOrganizacional().getRuta(), textFormat));
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
     sheet.addCell(new jxl.write.Label(18, x, obtenerDescripcionTipoDocumento(
     dd.getServidor().getTipoIdentificacion()), textFormat));
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
     sheet.addCell(new jxl.write.Label(21, x, dd.getUnidadOrganizacionalCambioAdministrativo().getRuta(), textFormat));
     }
     //23 Fecha Fin del Cambio Administrativo
     if (dd.getUnidadOrganizacionalCambioAdministrativo() != null) {
     sheet.addCell(new jxl.write.DateTime(22, x, dd.getFechaMaximoCambioAdministrativo(), dateFormat));
     }
     //24 Código Puesto Encargo
     if (dd.getDistributivoDetalleEncargo() != null) {
     sheet.addCell(new jxl.write.Number(23, x, dd.getDistributivoDetalleEncargo().getCodigoPuesto(), numberFormat));
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
     sheet.addCell(new jxl.write.Number(
     26, x, dd.getDistributivoDetalleSubrogacion().getCodigoPuesto(), numberFormat));
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
     getNumericoTramite() + "-"
     + dd.getMovimiento().getTramite().getInstitucionEjercicioFiscal()
     .getEjercicioFiscal().getNombre(), textFormat));
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
     }*/
    /**
     * Método que escribe los encabezados del archivo excel de la consulta.
     *
     * @param sheet
     * @return
     */
    /* public WritableSheet encabezadosColumnasConsultaExcel(final WritableSheet sheet) {
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
     }*/
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
            clasificacionValoracionPuestoHelper.getListaPuestos().clear();
            clasificacionValoracionPuestoHelper.getListaPuestos().addAll(
                    distributivoPuestoServicio.buscar(clasificacionValoracionPuestoHelper.getBusquedaPuestoVO(),
                            true,obtenerUsuarioConectado(),esRRHH(pi.getValorTexto())));
            clasificacionValoracionPuestoHelper.setActivo("1");
            actualizarComponente("accordionPanel");
            clasificacionValoracionPuestoHelper.setTotalRegistros(
                    new Long(clasificacionValoracionPuestoHelper.getListaPuestos().size()));
            actualizarComponente("totalPanel");
            if (clasificacionValoracionPuestoHelper.getListaPuestos().isEmpty()) {
                mostrarMensajeEnPantalla(
                        "No se encontraron registros", FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(
                        clasificacionValoracionPuestoHelper.getListaPuestos().size() > 1
                                ? UtilCadena.concatenar(
                                        "Se encontraron ",
                                        clasificacionValoracionPuestoHelper.getListaPuestos().size(), " registros.")
                                : UtilCadena.concatenar("Un registro encontrado."), FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los puestos.", e);
        }
        return null;
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
     * Este metodo es usado para iniciar el proceso de seleccion de una ubucacion geográfica.
     */
    public void iniciarUbicacionGeografica() {
        try {
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaPais());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaProvincia());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaCanton());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaParroquia());

            List<UbicacionGeografica> paises = administracionServicio.listarTodosubicacionGeografica();
            for (UbicacionGeografica ug : paises) {
                clasificacionValoracionPuestoHelper.getListaPais().add(new SelectItem(ug.getId(), ug.getNombre()));
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
            if (clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getParroquiaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(
                        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().
                        getParroquiaId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(
                        UtilCadena.concatenar(
                                ug.getUbicacionGeografica().getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                                ug.getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ", ug.
                                getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                clasificacionValoracionPuestoHelper
                        .getBusquedaPuestoVO().setUbicacionGeograficaTipo(TipoUbicacionGeograficaEnum.PARROQUIA.
                                getCodigo());
            } else if (clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getCantonId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(
                        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().
                        getCantonId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(
                        UtilCadena.concatenar(ug.
                                getUbicacionGeografica().getUbicacionGeografica().getNombre(), " / ",
                                ug.getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.CANTON.getCodigo());
            } else if (clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getProvinciaId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(
                        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getProvinciaId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(
                        UtilCadena.concatenar(ug.
                                getUbicacionGeografica().getNombre(), " / ", ug.getNombre()));
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.PROVINCIA.getCodigo());
            } else if (clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getPaisId() != null) {
                ug = administracionServicio.buscarUbicacionGeograficaId(
                        clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getPaisId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaNombre(ug.getNombre());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaId(ug.getId());
                clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().setUbicacionGeograficaTipo(
                        TipoUbicacionGeograficaEnum.PAIS.getCodigo());
            }
            actualizarComponente("frmPrincipal:".concat("parametrosBusqueda_parroquia"));
            ejecutarComandoPrimefaces("ubcgo.hide();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al seleccionar ubicación geográfico.", e);
        }
    }

    /**
     * Buscar niveles ocupacionales.
     */
    public void buscarNivelOcupacional() {
        try {
            List<NivelOcupacional> lista
                    = regimenLaboralServicio.listarTodosNivelOcupacionalPorRegimen(clasificacionValoracionPuestoHelper.
                            getBusquedaPuestoVO().getRegimenLaboralId());
            iniciarCombosTodos(clasificacionValoracionPuestoHelper.getListaNivelOcupacional());
            for (NivelOcupacional no : lista) {
                clasificacionValoracionPuestoHelper.getListaNivelOcupacional().add(new SelectItem(no.getId(), no.getNombre()));
            }
            buscarEscalaOcupacional();
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar niveles ocupacionales.", e);
        }
    }

    /**
     * Buscar escalas ocupacionales.
     */
    public void buscarEscalaOcupacional() {
        try {
            clasificacionValoracionPuestoHelper.setListaEscalasOcupacionales(
                    regimenLaboralServicio.listarTodosEscalaOcupacionalPorNivelOcup(
                            clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getNivelOcupacionalId()));

            iniciarCombos(clasificacionValoracionPuestoHelper.getListaOpcionesEscalaOcupacional());
            clasificacionValoracionPuestoHelper.getListaOpcionesEscalaOcupacional().remove(0);

            for (EscalaOcupacional eo : clasificacionValoracionPuestoHelper.getListaEscalasOcupacionales()) {
                clasificacionValoracionPuestoHelper.getListaOpcionesEscalaOcupacional().add(
                        new SelectItem(eo.getId(), UtilCadena.concatenar(
                                        eo.getNombre(), " / ", eo.getGrado(), " / ",
                                        UtilNumeros.getInstancia().formatearMoneda(eo.getRmu()))));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar escalas ocupacionales.", e);
        }
    }

    public void seleccionarEscalaOcupacional() {
        for (EscalaOcupacional eo : clasificacionValoracionPuestoHelper.getListaEscalasOcupacionales()) {
            if (clasificacionValoracionPuestoHelper
                    .getDistributivoDetalle().getIdEscalaOcupacional().equals(eo.getId())) {
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().setRmu(eo.getRmu());
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().setRmuEscala(eo.getRmu());
                clasificacionValoracionPuestoHelper.getDistributivoDetalle().setEscalaOcupacional(eo);
                break;
            }
        }
    }

    /**
     * Este metodo busca las provincias segun la region seleccionada.
     */
    public void buscarProvincias() {
        try {
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaProvincia());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaCanton());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> provincias = administracionServicio.listarTodosIdUbicacionGeografica(
                    clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getPaisId());
            for (UbicacionGeografica provincia : provincias) {
                clasificacionValoracionPuestoHelper.getListaProvincia().add(
                        new SelectItem(provincia.getId(), provincia.getNombre()));
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
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaCanton());
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> cantones = administracionServicio.listarTodosIdUbicacionGeografica(
                    clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getProvinciaId());
            for (UbicacionGeografica canton : cantones) {
                clasificacionValoracionPuestoHelper.getListaCanton().add(
                        new SelectItem(canton.getId(), canton.getNombre()));
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
            iniciarCombos(clasificacionValoracionPuestoHelper.getListaParroquia());
            List<UbicacionGeografica> parroquias = administracionServicio.listarTodosIdUbicacionGeografica(
                    clasificacionValoracionPuestoHelper.getBusquedaPuestoVO().getCantonId());
            for (UbicacionGeografica parroquia : parroquias) {
                clasificacionValoracionPuestoHelper.getListaParroquia().add(
                        new SelectItem(parroquia.getId(), parroquia.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar parroquias.", e);
        }
    }

    /**
     *
     * @param document
     */
    public void postProcessXLS(Object document) {

        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setWrapText(true);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
//            sheet.autoSizeColumn(i);
        }
    }

    /**
     *
     * @return
     */
    public ClasificacionValoracionPuestoHelper getClasificacionValoracionPuestoHelper() {
        return clasificacionValoracionPuestoHelper;
    }

    /**
     *
     * @param clasificacionValoracionPuestoHelper
     */
    public void setClasificacionValoracionPuestoHelper(
            ClasificacionValoracionPuestoHelper clasificacionValoracionPuestoHelper) {
        this.clasificacionValoracionPuestoHelper = clasificacionValoracionPuestoHelper;
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
     * Busca histórico del puesto seleccionado por el usuario
     */
    public void buscarHistorico() {
        try {
            clasificacionValoracionPuestoHelper.getListaHistoricos().clear();
            clasificacionValoracionPuestoHelper.setListaHistoricos(distributivoPuestoServicio.buscarHistoricoPorPuesto(
                    clasificacionValoracionPuestoHelper.getDistributivoDetalle().getCodigoPuesto()));
            clasificacionValoracionPuestoHelper.setHistorico(true);
            ejecutarComandoPrimefaces("historicoPuesto.show()");
        } catch (ServicioException ex) {
            Logger.getLogger(ClasificacionValoracionPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca nominas detalles del puesto seleccionado por el usuario
     */
    public void buscarNominasDetalle() {
        try {
            clasificacionValoracionPuestoHelper.getListaNominas().clear();
            clasificacionValoracionPuestoHelper.setListaNominas(
                    nominaServicio.listarNominaDetallesPorPuesto(
                            clasificacionValoracionPuestoHelper.getDistributivoDetalle().getId()));
            clasificacionValoracionPuestoHelper.setHistorico(false);
            ejecutarComandoPrimefaces("nominasPuesto.show()");
        } catch (ServicioException ex) {
            Logger.getLogger(ClasificacionValoracionPuestoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea encabezado de archivo Nomina/Historico segun corresponda
     *
     * @param document
     */
    public void xlsEncabezado(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFFont fuenteCabecera = wb.createFont();
        fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle.setFont(fuenteCabecera);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);
            if (clasificacionValoracionPuestoHelper.isHistorico()) {
                cell.setCellValue(clasificacionValoracionPuestoHelper.getCabeceraArchivoHistorico()[i]);
            } else {
                cell.setCellValue(clasificacionValoracionPuestoHelper.getCabeceraArchivoNomina()[i]);
            }
            cell.setCellStyle(cellStyle);
        }
    }

    public String buscarNumeroDeCertificacion(){
        return distributivoPuestoServicio.obtenerNumeroCertificacionPresupuestaria(clasificacionValoracionPuestoHelper.
                getDistributivoDetalle().getUnidadPresupuestaria().getId(),
                clasificacionValoracionPuestoHelper.getIdModalidadLaboralSeleccionada());
    }

}
