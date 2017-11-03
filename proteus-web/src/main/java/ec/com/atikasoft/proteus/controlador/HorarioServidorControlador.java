/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.HorarioServidorHelper;
import ec.com.atikasoft.proteus.dao.HorarioDetalleDao;
import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.TipoServidorEstadoHorarioEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "horarioServidorBean")
@ViewScoped
public class HorarioServidorControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = 
            "/pages/administracion/asistencia_de_personal/horario/asignacion_horarios_a_servidores.jsf";

    /**
     * Dao de horario detalle
     */
    @EJB
    private HorarioDetalleDao horarioDetalleDao;

    /**
     * Servicio de horarios
     */
    @EJB
    private AsistenciaDePersonalServicio asistenciaPersonalServicio;

    /**
     * Servicio de Undiades Desconcentradas
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Servicio de distributivo detalle
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{horarioServidorHelper}")
    private HorarioServidorHelper horarioServidorHelper;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(horarioServidorHelper);
        iniciarListaOpcionesTipoServidorHorario();
        horarioServidorHelper.setOpcionTipoServidorHorario(TipoServidorEstadoHorarioEnum.SIN_HORARIO.getCodigo());
        buscar();
        buscarHorarios();
    }

    /**
     * Llena la lista de opciones de tipo de servidor horario
     */
    private void iniciarListaOpcionesTipoServidorHorario() {
        horarioServidorHelper.getOpcionesTipoServidorHorario().clear();
        for (TipoServidorEstadoHorarioEnum tse : TipoServidorEstadoHorarioEnum.values()) {
            horarioServidorHelper.getOpcionesTipoServidorHorario().add(
                    new SelectItem(tse.getCodigo(), tse.getDescripcion()));
        }
    }

    /**
     * Recupera los distributivos detalles asociados a la Unidad desconcentrada del usuario logueado
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            List<UnidadOrganizacional> unidesDeAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.ASISTENCIA_DE_PERSONAL.getCodigo());

            String opcionTipo = horarioServidorHelper.getOpcionTipoServidorHorario();
            horarioServidorHelper.getListaPuestos().clear();
            BusquedaServidorVO bs = new BusquedaServidorVO();
            bs.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            bs.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            for (UnidadOrganizacional uo : unidesDeAcceso) {
                bs.setCodUnidadAdministrativa(uo.getCodigo());
                if (opcionTipo.equals(TipoServidorEstadoHorarioEnum.SIN_HORARIO.getCodigo())) {
                    bs.setConHorarioAsignado(Boolean.FALSE);
                } else if (opcionTipo.equals(TipoServidorEstadoHorarioEnum.CON_HORARIO.getCodigo())) {
                    bs.setConHorarioAsignado(Boolean.TRUE);
                }
                horarioServidorHelper.getListaPuestos().addAll(servidorServicio.buscar(bs));
            }

            DataTable dataTable = (DataTable) FacesContext
                    .getCurrentInstance().getViewRoot().findComponent("asigHorario:tablaServidores");
            dataTable.reset();

        } catch (Exception ex) {
            error(getClass().getName(), "Error al procesar la búsqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Recupera la lista de horarios vigentes
     */
    private void buscarHorarios() {
        try {
            iniciarCombos(horarioServidorHelper.getOpcionesHorario());
            for (Horario h : asistenciaPersonalServicio.listarHorariosVigentes()) {
                horarioServidorHelper.getOpcionesHorario().add(new SelectItem(h.getId(), h.getNombre()));
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al intentar recuperar la lista de horarios", ex);
        }

    }

    /**
     * Recupera la lista de Undiades Desconcentradas
     */
    public void recuperarUnidadesDesconcentradas() {
        try {
            horarioServidorHelper.setListaDesconcentrados(desconcentradoServicio.listarDesconcentradosTodosVigentes());
        } catch (Exception ex) {
            error(getClass().getName(), "Error al intentar recuperar las Unidades Desconcetradas", ex);
        }
    }

    /**
     * Iniciar variables para nuevo horario
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        return null;
    }

    /**
     * Iniciar variables para edicion de horario
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        return null;
    }

    /**
     * Guardar
     *
     * @return
     */
    @Override
    public String guardar() {
        guardarAsignacionDeHorario();
        return null;
    }

    /**
     * Elimina el registro
     *
     * @return
     */
    @Override
    public String borrar() {
        return null;
    }

    /**
     * Prepara variables para realizar asignacion del horario a un servidor o varios servidores
     *
     * @return
     */
    public String iniciarAsignacionDeHorario() {
        try {
            horarioServidorHelper.setListaAsignacionHorarios(new ArrayList<DistributivoDetalle>());
            if (horarioServidorHelper.getAsignacionMasivaActivada()) {
                horarioServidorHelper.setHorario(new Horario());
                ejecutarComandoPrimefaces("dlgAsignacionMasivaHorarioWV.show();");
                DataTable tabla = (DataTable) FacesContext
                        .getCurrentInstance().getViewRoot().findComponent("frmAsignacionMasivaHorario:tblServidores");
                tabla.reset();
            } else {
                if (horarioServidorHelper.getPuesto().getServidor().getHorario() != null) {
                    horarioServidorHelper.setHorario(horarioServidorHelper.getPuesto().getServidor().getHorario());
                } else {
                    horarioServidorHelper.setHorario(new Horario());
                }
                ejecutarComandoPrimefaces("seleccionHorarioDlg.show();");
            }

        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * Guarda la asignacion de horario para el o los servidores seleccionados
     *
     */
    private String guardarAsignacionDeHorario() {
        try {
            horarioServidorHelper.getPuesto().getServidor().setHorario(horarioServidorHelper.getHorario());
            if (!horarioServidorHelper.getAsignacionMasivaActivada()) {
                horarioServidorHelper.getListaAsignacionHorarios().add(horarioServidorHelper.getPuesto());
                ejecutarComandoPrimefaces("seleccionHorarioDlg.hide();");
            } else if (!horarioServidorHelper.getListaAsignacionHorarios().isEmpty()) {
                ejecutarComandoPrimefaces("dlgAsignacionMasivaHorarioWV.hide();");
            } else {
                mostrarMensajeEnPantalla("Debe seleccionar al menos un servidor", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            List<Servidor> servidores = new ArrayList<>();
            for (DistributivoDetalle dd : horarioServidorHelper.getListaAsignacionHorarios()) {
                iniciarDatosEntidad(dd.getServidor(), Boolean.FALSE);
                dd.getServidor().setHorario(horarioServidorHelper.getHorario());
                servidores.add(dd.getServidor());
            }
            asistenciaPersonalServicio.guardarAsignacionHorarioAServidores(servidores);
            horarioServidorHelper.setHorario(new Horario());
            horarioServidorHelper.setOpcionTipoServidorHorario(TipoServidorEstadoHorarioEnum.SIN_HORARIO.getCodigo());
            mostrarMensajeEnPantalla("Asignación de Horario realizada satisfactoriamente", FacesMessage.SEVERITY_INFO);
            return buscar();

        } catch (Exception ex) {
            error(getClass().getName(), ERROR_REGISTRO_GUARDADO, ex);
        }
        return null;
    }

    /**
     * Aplica formato al excel generado
     *
     * @param document
     * @throws IOException
     * @throws DocumentException
     */
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

    /**
     * Devuelve un horario detalle dada su posicion en la lista
     *
     * @param posicion
     * @return
     */
    public HorarioDetalle buscarHorarioDetallePorPosicion(int posicion) {
        return horarioServidorHelper.getListaHorarioDetalles().get(posicion);
    }

    /**
     * Muestra el diálogo con los detalles del horario seleccionado
     *
     * @return
     */
    public String mostrarDlgHorarioDetalles() {
        horarioServidorHelper.getListaHorarioDetalles().clear();
        try {
            horarioServidorHelper.getListaHorarioDetalles().addAll(
                    horarioDetalleDao.buscarTodosPorHorarioId(horarioServidorHelper.getHorario().getId()));
            ejecutarComandoPrimefaces("dlgLecturaHorarioDetalleWV.show();");
        } catch (DaoException ex) {
            error(getClass().getName(), "Error al intentar msotrar los detalles del horario seleccioando", ex);
        }
        return null;
    }

    /**
     * Devuelve un horario detalle dado el id del horario y nombre del nombreDia
     *
     * @param horarioId
     * @param nombreDia
     * @return
     */
    public HorarioDetalle buscarHorarioDetallePorNombreDia(Long horarioId, String nombreDia) {
        Integer pos = DiasEnum.obtenerNumeroDadoNombre(nombreDia);
        return horarioServidorHelper.getListaHorarioDetalles().isEmpty()
                ? new HorarioDetalle() : horarioServidorHelper.getListaHorarioDetalles().get(pos - 1);
    }

    /**
     * Retorna el listado de los nombres de los dias de la semana
     *
     * @return
     */
    public String[] getNombresDias() {
        String[] nombreDias = new String[DiasEnum.values().length];
        for (DiasEnum de : DiasEnum.values()) {
            nombreDias[de.getNumero() - 1] = de.getNombre();
        }
        return nombreDias;
    }

    /**
     *
     * @return
     */
    public HorarioServidorHelper getHorarioServidorHelper() {
        return horarioServidorHelper;
    }

    /**
     *
     * @param horarioHelper
     */
    public void setHorarioServidorHelper(HorarioServidorHelper horarioHelper) {
        this.horarioServidorHelper = horarioHelper;
    }
}
