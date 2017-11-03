/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.HorarioHelper;
import ec.com.atikasoft.proteus.converter.HorarioConverter;
import ec.com.atikasoft.proteus.dao.HorarioDao;
import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.modelo.Horario;
import ec.com.atikasoft.proteus.modelo.HorarioDesconcentrado;
import ec.com.atikasoft.proteus.modelo.HorarioDetalle;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador para la administración de Horario.
 *
 * @author Leydis Garzón
 */
@ManagedBean(name = "horarioBean")
@ViewScoped
public class HorarioControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/asistencia_de_personal/horario/horario.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/asistencia_de_personal/horario/lista_horario.jsf";
    /**
     * Dao de horario
     */
    @EJB
    private HorarioDao horarioDao;

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
     * Helper de Clase.
     */
    @ManagedProperty("#{horarioHelper}")
    private HorarioHelper horarioHelper;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(horarioHelper);
        getCatalogoHelper().setCampoBusqueda("");

    }

    /**
     * Recupera todos los registros vigentes
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            horarioHelper.getListaHorario().clear();
            horarioHelper.setListaHorario(asistenciaPersonalServicio.listarHorariosVigentes());
            ponerAtributoSession(HorarioConverter.CLAVE_SESSION, horarioHelper.getListaHorario());
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la búsqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Recupera la lista de Undiades Desconcentradas
     */
    public void recuperarUnidadesDesconcentradas() {
        try {
            horarioHelper.setListaDesconcentrados(desconcentradoServicio.listarDesconcentradosTodosVigentes());
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
        horarioHelper.setHorario(new Horario());
        iniciarDatosEntidad(horarioHelper.getHorario(), Boolean.TRUE);
        horarioHelper.setEsNuevo(Boolean.TRUE);
        iniciarDetalles();
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Iniciar variables para edicion de horario
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            iniciarDatosEntidad(horarioHelper.getHorario(), Boolean.FALSE);
            horarioHelper.setEsNuevo(Boolean.FALSE);
            iniciarDetalles();
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Guardar
     *
     * @return
     */
    @Override
    public String guardar() {
        try {
            if (horarioHelper.getEsNuevo() && !validarNombre()) {
                mostrarMensajeEnPantalla("Ya existe un registro con el mismo nombre", FacesMessage.SEVERITY_WARN);
                return null;
            }
            if (!hayErrorEnIntervalosHorarios()) {
                asistenciaPersonalServicio.crearActualizarHorario(
                        horarioHelper.getHorario(), horarioHelper.getListaHorarioDetalles());
                horarioHelper.setEsNuevo(Boolean.FALSE);
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el horario", e);
        }
        return null;
    }

    /**
     * Elimina el registro
     *
     * @return
     */
    @Override
    public String borrar() {
        try {
            List<HorarioDesconcentrado> horariosDesconcentrados = asistenciaPersonalServicio
                    .listarHorariosDesconcentradosVigentesDadoHorarioId(horarioHelper.getHorario().getId());
            Boolean horarioYaAsignado = asistenciaPersonalServicio.horarioAsignadoAServidores(
                    horarioHelper.getHorario().getId(),
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            if (!horarioYaAsignado && horariosDesconcentrados.isEmpty()) {
                iniciarDatosEntidad(horarioHelper.getHorario(), Boolean.FALSE);
                horarioHelper.getHorario().setVigente(Boolean.FALSE);
                asistenciaPersonalServicio.crearActualizarHorario(horarioHelper.getHorario(), null);
                horarioHelper.setHorario(null);
                buscar();
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al intentar eliminar el registro", ex);
        }
        return null;
    }

    /**
     * Verifica que no exista el nombre de horario
     *
     * @return
     */
    public Boolean validarNombre() {
        for (Horario h : horarioHelper.getListaHorario()) {
            if (h.getNombre().trim().equalsIgnoreCase(horarioHelper.getHorario().getNombre().trim())) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Inicia variables de detalles de horario
     */
    private void iniciarDetalles() {
        horarioHelper.getListaHorarioDetalles().clear();
        if (horarioHelper.getEsNuevo()) {
            for (DiasEnum de : DiasEnum.values()) {
                HorarioDetalle hd = new HorarioDetalle(de.getNumero().toString(), de.getNombre());
                hd.setHorario(new Horario());
                iniciarDatosEntidad(hd, Boolean.TRUE);
                horarioHelper.getListaHorarioDetalles().add(hd);
            }

        } else {
            try {
                horarioHelper.getListaHorarioDetalles().addAll(
                        asistenciaPersonalServicio.listarHorarioDetallesDadoHorarioId(horarioHelper.getHorario().getId()));
            } catch (Exception ex) {
                Logger.getLogger(HorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Verificar que los intervalos de horarios sean correctos
     */
    private boolean hayErrorEnIntervalosHorarios() {
        boolean error = false;
        for (HorarioDetalle hd : horarioHelper.getListaHorarioDetalles()) {
            if (hd.getLaborable()) {
                if (hd.getHoraEntrada().after(hd.getHoraSalida())) {
                    mostrarMensajeEnPantalla(
                            hd.getDiaNombre() + ": " + "La Hora de Salida no puede ser menor que la Hora de Entrada.",
                            FacesMessage.SEVERITY_ERROR);
                    error = true;
                }
                if (hd.getHoraSalidaAlmuerzo() != null && hd.getHoraEntrada().after(hd.getHoraSalidaAlmuerzo())) {
                    mostrarMensajeEnPantalla(
                            hd.getDiaNombre() + ": "
                            + "La Hora Inicio Almuerzo no puede ser menor que la Hora de Entrada.",
                            FacesMessage.SEVERITY_ERROR);
                    error = true;
                }
                if (hd.getHoraSalidaAlmuerzo() != null
                        && hd.getHoraSalidaAlmuerzo().after(hd.getHoraEntradaAlmuerzo())) {
                    mostrarMensajeEnPantalla(
                            hd.getDiaNombre() + ": "
                            + "La Hora Fín Almuerzo no puede ser menor que la Hora Inicio Almuerzo.",
                            FacesMessage.SEVERITY_ERROR);
                    error = true;
                }
                if (hd.getHoraEntradaAlmuerzo() != null && hd.getHoraEntradaAlmuerzo().after(hd.getHoraSalida())) {
                    mostrarMensajeEnPantalla(
                            hd.getDiaNombre() + ": "
                            + "La Hora de Salida no puede ser menor que la Hora Fín Almuerzo.",
                            FacesMessage.SEVERITY_ERROR);
                    error = true;

                }
                Calendar c1 = Calendar.getInstance();
                c1.setTime(hd.getHoraEntrada());
                int h1 = c1.get(Calendar.HOUR_OF_DAY);
                int m1 = c1.get(Calendar.MINUTE);

                Calendar c2 = Calendar.getInstance();
                int h2 = 0;
                int m2 = 0;
                if (hd.getHoraSalidaAlmuerzo() != null) {
                    c2.setTime(hd.getHoraSalidaAlmuerzo());
                    h2 = c2.get(Calendar.HOUR_OF_DAY);
                    m2 = c2.get(Calendar.MINUTE);
                }
                float intervalo1 = ((h2 - h1) * 60 + m2 - m1) / (float) 60;

                Calendar c3 = Calendar.getInstance();
                int h3 = 0;
                int m3 = 0;
                if (hd.getHoraEntradaAlmuerzo() != null) {
                    c3.setTime(hd.getHoraEntradaAlmuerzo());
                    h3 = c3.get(Calendar.HOUR_OF_DAY);
                    m3 = c3.get(Calendar.MINUTE);
                }

                Calendar c4 = Calendar.getInstance();
                c4.setTime(hd.getHoraSalida());
                int h4 = c4.get(Calendar.HOUR_OF_DAY);
                int m4 = c4.get(Calendar.MINUTE);
                float intervalo2 = ((h4 - h3) * 60 + m4 - m3) / (float) 60;

                float diffTiempoAlmuerzo = 0f;
                boolean conAlmuerzo = false;
                if (hd.getHoraSalidaAlmuerzo() != null && hd.getHoraEntradaAlmuerzo() != null) {
                    conAlmuerzo = true;
                    diffTiempoAlmuerzo = (UtilFechas
                            .calcularDiferenciaMinutosEntreFechas(
                                    hd.getHoraSalidaAlmuerzo(), hd.getHoraEntradaAlmuerzo())
                            - hd.getTiempoAlmuerzoPermitido()) / (float) 60;
                }

                if (conAlmuerzo && (Math.abs(intervalo1) + diffTiempoAlmuerzo
                        + Math.abs(intervalo2) != hd.getTotalHorasDiaria())
                        || (!conAlmuerzo && (Math.abs(intervalo1 + intervalo2) != hd.getTotalHorasDiaria()))) {
                    error = true;
                    mostrarMensajeEnPantalla(
                            hd.getDiaNombre() + ": "
                            + "El total de tiempo asociado a los intervalos definidos"
                            + " no coincide con el número total de horas diarias", FacesMessage.SEVERITY_ERROR);

                }
            }
        }
        return error;
    }

    /**
     * Propaga la configuracion de un dia de horario a los demas dias del horario
     *
     * @param posicionDetalle
     */
    public void propagarConfiguracionHorario(int posicionDetalle) {
        HorarioDetalle horarioDetalleFuente = horarioHelper.getListaHorarioDetalles().get(posicionDetalle);
        int pos = 0;
        for (HorarioDetalle hd : horarioHelper.getListaHorarioDetalles()) {
            if (pos != posicionDetalle) {
                hd.setLaborable(horarioDetalleFuente.getLaborable());
                hd.setHoraEntrada(horarioDetalleFuente.getHoraEntrada());
                hd.setHoraSalidaAlmuerzo(horarioDetalleFuente.getHoraSalidaAlmuerzo());
                hd.setHoraEntradaAlmuerzo(horarioDetalleFuente.getHoraEntradaAlmuerzo());
                hd.setTiempoAlmuerzoPermitido(horarioDetalleFuente.getTiempoAlmuerzoPermitido());
                hd.setHoraSalida(horarioDetalleFuente.getHoraSalida());
                hd.setTotalHorasDiaria(horarioDetalleFuente.getTotalHorasDiaria());
            }
            pos++;
        }
        mostrarMensajeEnPantalla(
                "Configuración copiada a los demás días satisfactoriamente", FacesMessage.SEVERITY_INFO);
    }

    /**
     * Limpia los datos del horario detalle si no es dia laborable
     *
     * @param horarioDetalle
     */
    public void actualizarFormularioHorarioDetalle(HorarioDetalle horarioDetalle) {
        horarioDetalle.setHoraEntrada(null);
        horarioDetalle.setHoraEntradaAlmuerzo(null);
        horarioDetalle.setHoraSalidaAlmuerzo(null);
        horarioDetalle.setHoraSalida(null);
        horarioDetalle.setTotalHorasDiaria(null);
        horarioDetalle.setTiempoAlmuerzoPermitido(null);
    }

    /**
     * Prepara variables para realizar asignacion de horarios a unidad desconcentrada
     */
    public void inciarAsignacionDeHorariosADesconcentrado() {
        horarioHelper.getHorariosAsignables().clear();
        horarioHelper.getHorariosAsignados().clear();
        horarioHelper.getListaHorario().clear();
        buscar();
        try {
            horarioHelper.setHorariosYaAsigados(asistenciaPersonalServicio
                    .listarTodosHorariosDesconcentradosDadoDesconcentradoId(horarioHelper.getDesconcentrado().getId()));
            boolean encontrado = false;
            for (Horario h : horarioHelper.getListaHorario()) {
                for (HorarioDesconcentrado hd : horarioHelper.getHorariosYaAsigados()) {
                    if (hd.getVigente() && hd.getHorario().getId().equals(h.getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado) {
                    horarioHelper.getHorariosAsignados().add(h);
                    encontrado = false;
                } else {
                    horarioHelper.getHorariosAsignables().add(h);
                }
            }
            horarioHelper.getSeleccionHorarios().setSource(horarioHelper.getHorariosAsignables());
            horarioHelper.getSeleccionHorarios().setTarget(horarioHelper.getHorariosAsignados());
        } catch (Exception ex) {
            Logger.getLogger(HorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        ejecutarComandoPrimefaces("seleccionHorariosDlg.show()");
    }

    /**
     * Guarda la asignacion de horarios para la unidad desconcentrada
     */
    public void guardarAsignacionHorarios() {
        try {
            List<HorarioDesconcentrado> horariosDesconcentrados = new ArrayList<>();
            boolean encontrado = false;
            for (Horario h : horarioHelper.getSeleccionHorarios().getTarget()) {
                for (HorarioDesconcentrado hd : horarioHelper.getHorariosYaAsigados()) {
                    if (hd.getHorario().getId().equals(h.getId())) {
                        encontrado = true;
                        if (!hd.getVigente()) {
                            hd.setVigente(Boolean.TRUE);
                        }
                        horariosDesconcentrados.add(hd);
                        break;
                    }
                }
                if (!encontrado) {
                    HorarioDesconcentrado hdesc = new HorarioDesconcentrado(horarioHelper.getDesconcentrado(), h);
                    iniciarDatosEntidad(hdesc, Boolean.TRUE);
                    horariosDesconcentrados.add(hdesc);
                } else {
                    encontrado = false;
                }
            }

            asistenciaPersonalServicio.guardarHorariosAsignadosADesconcentrado(
                    obtenerUsuarioConectado(), horarioHelper.getDesconcentrado().getId(), horariosDesconcentrados);

            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            ejecutarComandoPrimefaces("seleccionHorariosDlg.hide()");
            recuperarUnidadesDesconcentradas();

        } catch (Exception ex) {
            Logger.getLogger(HorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
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
            return asistenciaPersonalServicio.listarHorariosDesconcentradosVigentesDadoDesconcentradoId(desconcentradoId);
        } catch (Exception ex) {
            Logger.getLogger(HorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
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
        return horarioHelper.getListaHorarioDetalles().get(posicion);
    }

    /**
     * Muestra el diálogo con los detalles del horario seleccionado
     *
     * @return
     */
    public String mostrarDlgHorarioDetalles() {
        horarioHelper.getListaHorarioDetalles().clear();
        try {
            horarioHelper.getListaHorarioDetalles().addAll(
                    asistenciaPersonalServicio.listarHorarioDetallesDadoHorarioId(horarioHelper.getHorario().getId()));
        } catch (Exception ex) {
            Logger.getLogger(HorarioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        ejecutarComandoPrimefaces("dlgLecturaHorarioDetalleWV.show();");
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
        return horarioHelper.getListaHorarioDetalles().isEmpty()
                ? new HorarioDetalle() : horarioHelper.getListaHorarioDetalles().get(pos - 1);
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
    public HorarioHelper getHorarioHelper() {
        return horarioHelper;
    }

    /**
     *
     * @param horarioHelper
     */
    public void setHorarioHelper(HorarioHelper horarioHelper) {
        this.horarioHelper = horarioHelper;
    }
}
