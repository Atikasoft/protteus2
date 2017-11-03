/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.HorarioMonitoreoHelper;
import ec.com.atikasoft.proteus.dao.HorarioDetalleDao;
import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.enums.TipoServidorEstadoHorarioEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DesconcentradoUnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.HorarioMonitoreoVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
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

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "horarioMonitoreoBean")
@ViewScoped
public class HorarioMonitoreoControlador extends BaseControlador {

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
    @ManagedProperty("#{horarioMonitoreoHelper}")
    private HorarioMonitoreoHelper horarioMonitoreoHelper;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        iniciarListaOpcionesTipoServidorHorario();
        horarioMonitoreoHelper.setOpcionTipoServidorHorario("");
//        buscarHorarios();
        recuperarUnidadesDesconcentradas();
    }

    /**
     * Llena la lista de opciones de tipo de servidor horario
     */
    private void iniciarListaOpcionesTipoServidorHorario() {
        iniciarCombos(horarioMonitoreoHelper.getOpcionesTipoServidorHorario());
        for (TipoServidorEstadoHorarioEnum tse : TipoServidorEstadoHorarioEnum.values()) {
            horarioMonitoreoHelper.getOpcionesTipoServidorHorario().add(
                    new SelectItem(tse.getCodigo(), tse.getDescripcion()));
        }
    }

    /**
     * Cuenta para cada undiad desconcentra el número de sevidores que satisfacen el tipo estado horario(CON / SIN )
     */
    public void contarServidores() {
        try {
            horarioMonitoreoHelper.getListaHorarioMonitoreoVO().clear();
            Boolean conHorario = null;
            if (horarioMonitoreoHelper.getOpcionTipoServidorHorario()
                    .equals(TipoServidorEstadoHorarioEnum.CON_HORARIO.getCodigo())) {
                mostrarMensajeEnPantalla("CON HORARIO", FacesMessage.SEVERITY_INFO);
                conHorario = Boolean.TRUE;
            } else if (horarioMonitoreoHelper.getOpcionTipoServidorHorario()
                    .equals(TipoServidorEstadoHorarioEnum.SIN_HORARIO.getCodigo())) {
                mostrarMensajeEnPantalla("SIN HORARIO", FacesMessage.SEVERITY_INFO);
                conHorario = Boolean.FALSE;
            } else {
                mostrarMensajeEnPantalla("NO SELECCIÓN", FacesMessage.SEVERITY_INFO);
            }

            UsuarioVO usuario = obtenerUsuarioConectado();

            for (Desconcentrado d : horarioMonitoreoHelper.getDesconcentrados()) {
                HorarioMonitoreoVO hm = new HorarioMonitoreoVO();
                hm.setDesconcentrado(d);
                hm.setHorariosAsociados(asistenciaPersonalServicio
                        .listarHorariosDesconcentradosVigentesDadoDesconcentradoId(d.getId()));
                for (DesconcentradoUnidadOrganizacional duo : d.getListaUnidadOrganizacional()) {
                    BusquedaServidorVO bs = new BusquedaServidorVO();
                    bs.setIdInstitucion(usuario.getInstitucion().getId());
                    bs.setIntitucionEjercicioFiscalId(usuario.getInstitucionEjercicioFiscal().getId());
                    bs.setCodUnidadAdministrativa(duo.getUnidadOrganizacional().getCodigo());
                    bs.setConHorarioAsignado(conHorario);

                    hm.setPuestos(servidorServicio.buscar(bs));
                }
                horarioMonitoreoHelper.getListaHorarioMonitoreoVO().add(hm);

            }

        } catch (Exception ex) {
            error(getClass().getName(), "Error al intentar contar servidores", ex);
        }
    }

    /**
     * Recupera la lista de horarios vigentes
     */
//    private void buscarHorarios() {
//        try {
//            iniciarCombos(horarioMonitoreoHelper.getOpcionesHorario());
//            for (Horario h : asistenciaPersonalServicio.listarHorariosVigentes()) {
//                horarioMonitoreoHelper.getOpcionesHorario().add(new SelectItem(h.getId(), h.getNombre()));
//            }
//        } catch (Exception ex) {
//            error(getClass().getName(), "Error al intentar recuperar la lista de horarios", ex);
//        }
//
//    }
    /**
     * Recupera la lista de Undiades Desconcentradas
     */
    public void recuperarUnidadesDesconcentradas() {
        try {
            horarioMonitoreoHelper.setDesconcentrados(desconcentradoServicio.listarDesconcentradosTodosVigentes());
        } catch (Exception ex) {
            error(getClass().getName(), "Error al intentar recuperar las Unidades Desconcentradas", ex);
        }
    }
    
    /**
     * Recuepera la lista de HorariosDesconcentrados asociados a la undiad desconcentrada dado su id
     *
     * @param desconcentradoId
     * @return
     */
    public List<HorarioDesconcentrado> obtenerHorariosConfigurados(Long desconcentradoId) {
        try {
            return asistenciaPersonalServicio
                    .listarHorariosDesconcentradosVigentesDadoDesconcentradoId(desconcentradoId);
        } catch (Exception ex) {
             error(getClass().getName(), "Error al intentar recuperar los horarios asociados", ex);
        }
        return new ArrayList<>();
    }

    /**
     * Devuelve un horario detalle dada su posicion en la lista
     *
     * @param posicion
     * @return
     */
    public HorarioDetalle buscarHorarioDetallePorPosicion(int posicion) {
        return horarioMonitoreoHelper.getListaHorarioDetalles().get(posicion);
    }

    /**
     * Muestra el diálogo con los detalles del horario seleccionado
     *
     * @return
     */
    public String mostrarDlgHorarioDetalles() {
        horarioMonitoreoHelper.getListaHorarioDetalles().clear();
        try {
            horarioMonitoreoHelper.getListaHorarioDetalles().addAll(
                    horarioDetalleDao.buscarTodosPorHorarioId(horarioMonitoreoHelper.getHorario().getId()));
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
        return horarioMonitoreoHelper.getListaHorarioDetalles().isEmpty()
                ? new HorarioDetalle() : horarioMonitoreoHelper.getListaHorarioDetalles().get(pos - 1);
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
    public HorarioMonitoreoHelper getHorarioMonitoreoHelper() {
        return horarioMonitoreoHelper;
    }

    /**
     *
     * @param horarioMonitoreoHelper
     */
    public void setHorarioMonitoreoHelper(HorarioMonitoreoHelper horarioMonitoreoHelper) {
        this.horarioMonitoreoHelper = horarioMonitoreoHelper;
    }
}
