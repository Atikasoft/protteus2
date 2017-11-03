/*
 *  LicenciaHorarioControlador.java
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
 *  22/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.LicenciaHorarioHelper;
import ec.com.atikasoft.proteus.controlador.helper.RegistroSolicitudHelper;
import ec.com.atikasoft.proteus.controlador.helper.TramiteHelper;
import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.enums.PeriodoControlEnum;
import ec.com.atikasoft.proteus.enums.TipoHorarioEnum;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.HorarioVO;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador de Licencia Horario.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ViewScoped
@ManagedBean(name = "licenciaHorarioBean")
public class LicenciaHorarioControlador extends BaseControlador {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{licenciaHorarioHelper}")
    private LicenciaHorarioHelper licenciaHorarioHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteHelper}")
    private TramiteHelper tramiteHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{registroSolicitudHelper}")
    private RegistroSolicitudHelper registroSolicitudHelper;

    /**
     * Servicio de Administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor por defecto.
     */
    public LicenciaHorarioControlador() {
        super();
    }

    @PostConstruct
    @Override
    public void init() {
        calcularTotales();
    }

    /**
     * Este metodo toma las horas en minutos y los pasas a formato hora:minutos.
     *
     * @param minuros Long
     * @return String
     */
    public String obtenerHorasMinutos(final Long minuros) {
        String total = "00:00";
        try {
            total = (completar(minuros / 60) + " : " + completar(minuros - ((minuros / 60) * 60L)));
        } catch (Exception e) {
            error(getClass().getName(), "Error al generar hora.", e);
        }
        return total;
    }

    /**
     * Este método es usado para completar el mes o año con un cero a la
     * izquierda.
     *
     * @param o Object
     * @return String
     */
    private String completar(final Object o) {
        String resultado = o.toString();
        if (resultado.length() == 1) {
            resultado = "0".concat(resultado);
        }
        return resultado;
    }

    /**
     * Este metodo inicia la configuracion de horario para movimiento.
     *
     * @return String
     */
    public String controlHorarioTramite() {
        try {
            licenciaHorarioHelper.iniciador();
            List<Licencia> licencias = licenciaHorarioHelper.getMovimiento().getListaLicencias();
            if (licencias != null && !licencias.isEmpty()) {
                Licencia licencia = licencias.get(0);
                licenciaHorarioHelper.setLicencia(licencia);
                List<LicenciaHorario> licenciasHorario =
                        administracionServicio.listarLicenciaHorarioPorLicencia(licencia.getId());
                for (LicenciaHorario l : licenciasHorario) {
                    licenciaHorarioHelper.getHorarios().get(l.getDia()).getHorario().add(l);
                }
                calcularTotales();
            }
            ejecutarComandoPrimefaces("dlgHorarios.show();");
        } catch (Exception e) {
            error(this.getClass().getName(), "Error al iniciar la edicion del horario.", e);
        }
        return null;
    }

    /**
     * Este metodo muetra el popUp de horarios.
     *
     * @return String
     */
    public String controlHorarioSolicitud() {
        try {
            licenciaHorarioHelper.iniciador();
            if (!registroSolicitudHelper.getListaLicenciaHorario().isEmpty()) {
                for (LicenciaHorario l : registroSolicitudHelper.getListaLicenciaHorario()) {
                    licenciaHorarioHelper.getHorarios().get(l.getDia()).getHorario().add(l);
                }
                calcularTotales();
            }
            ejecutarComandoPrimefaces("dlgHorarios.show();");
        } catch (Exception e) {
            error(this.getClass().getName(), "Error al consultar el tipo de movimento", e);
        }
        return null;
    }

    /**
     * Este metodo inicializa una hora para cualquier dia.
     *
     * @return String
     */
    public String iniciarNuevoHorario() {
        licenciaHorarioHelper.setLicenciaHorario(new LicenciaHorario());
        iniciarDatosEntidad(licenciaHorarioHelper.getLicenciaHorario(), Boolean.TRUE);
        licenciaHorarioHelper.getLicenciaHorario().setDia(licenciaHorarioHelper.getDia());
        return null;
    }

    /**
     * Este metodo iniciliza la eliminacion del horario.
     *
     * @return String
     */
    public String iniciarEliminacion() {
        LicenciaHorario horario = licenciaHorarioHelper.getLicenciaHorario();
        licenciaHorarioHelper.setMensajeEliminacion("Esta seguro de eliminar el horario de " + completar(
                horario.getHoraInicio().getHours()) + ":" + completar(
                horario.getHoraInicio().getMinutes()) + " a " + completar(
                horario.getHoraFin().getHours()) + ":" + completar(
                horario.getHoraFin().getMinutes()) + " del dia " + DiasEnum.obtenerNombre(
                horario.getDia()));
        ejecutarComandoPrimefaces("dlgEliminacionHora.show();");
        return null;
    }

    /**
     * Este metodo elimina un horario de un dia especifico.
     *
     * @return String
     */
    public String eliminar() {
        LicenciaHorario l = licenciaHorarioHelper.getLicenciaHorario();
        Integer dia = l.getDia();
        if (dia != null) {
            licenciaHorarioHelper.getHorarios().get(dia).getHorario().remove(l);
            ejecutarComandoPrimefaces("dlgEliminacionHora.hide();");
            calcularTotales();
        }
        return null;
    }

    /**
     * Este metodo agrega la lista de horarios a la del licencia.
     *
     * @return String
     */
    public String agregarHorariosSolicitud() {
        if (evaluarIngresoHorario()) {
            mostrarMensajeEnPantalla("Debe registrar por lo menos un horario para este trámite.", FacesMessage.SEVERITY_WARN);
        } else {
            registroSolicitudHelper.getListaLicenciaHorario().clear();
            for (HorarioVO h : licenciaHorarioHelper.getHorarios().values()) {
                registroSolicitudHelper.getListaLicenciaHorario().addAll(h.getHorario());
            }
            ejecutarComandoPrimefaces("dlgHorarios.hide();");
            mostrarMensajeEnPantalla("Horarios agregados.", FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    /**
     * Este metodo agrega los horarios al tramite.
     *
     * @return String
     */
    public String agregarHorariosTramite() {
        try {
            if (evaluarIngresoHorario()) {
                mostrarMensajeEnPantalla("Debe registrar por lo menos un horario para este trámite.", FacesMessage.SEVERITY_WARN);
            } else {
                List<LicenciaHorario> listaLicenciaHorario = licenciaHorarioHelper.getListaLicenciaHorario();
                listaLicenciaHorario.clear();
                for (HorarioVO h : licenciaHorarioHelper.getHorarios().values()) {
                    listaLicenciaHorario.addAll(h.getHorario());
                }
                administracionServicio.crearLicenciaHorario(listaLicenciaHorario, licenciaHorarioHelper.getLicencia());
                mostrarMensajeEnPantalla("Horarios guardados con exito.", FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("dlgHorarios.hide();");
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al crear los horario.", e);
        }
        return null;
    }

    /**
     * Este método evalua que al menos se ingrese un horario.
     *
     * @return Boolean
     */
    private Boolean evaluarIngresoHorario() {
        int n = 0;
        for (HorarioVO h : licenciaHorarioHelper.getHorarios().values()) {
            System.out.print("Numero de horas: " + h.getHorario().size() + "-->" + n);
            if (!h.getHorario().isEmpty()) {
                n++;
            }
        }
        return n == 0;
    }

    /**
     * Este metodo agrega el nuevo horario al dia correspondiente.
     *
     * @return String
     */
    public String agregar() {
        LicenciaHorario l = licenciaHorarioHelper.getLicenciaHorario();
        if (l.getHoraInicio().after(l.getHoraFin())) {
            mostrarMensajeEnPantalla("La hora de inicio debe ser menor a la hora fin.", FacesMessage.SEVERITY_WARN);
        } else {
            HorarioVO dia = licenciaHorarioHelper.getHorarios().get(licenciaHorarioHelper.getDia());
            controlHoras(dia.getHorario(), l);
            calcularTotales();
        }
        return null;
    }

    /**
     * Este metodo suma el total de horas por dia y luego la suma por semana.
     */
    private void calcularTotales() {
        Long totalSemana = 0L;
        Long totalSemanaRecuperacion = 0L;
        for (HorarioVO h : licenciaHorarioHelper.getHorarios().values()) {
            calcularTotalHoras(h);
            totalSemana += h.getTotalHoras();
            totalSemanaRecuperacion += h.getTotalHorasRecuperacion();
        }
        licenciaHorarioHelper.setTotalHorasSemana(totalSemana);
        licenciaHorarioHelper.setTotalHorasSemanaRecuperacion(totalSemanaRecuperacion);
    }

    /**
     * Este metodo calcula el horario de dia.
     *
     * @param horarioVO HorarioVO
     */
    private void calcularTotalHoras(final HorarioVO horarioVO) {
        Long horasDia = 0L;
        Long horasRecuoeracion = 0L;
        for (LicenciaHorario l : horarioVO.getHorario()) {
            Long horas = UtilFechas.calcularDiferenciaMinutosEntreFechas(
                    l.getHoraInicio(), l.getHoraFin());
            if (esRecuperacion(l)) {
                horasRecuoeracion += horas;
            } else {
                horasDia += horas;
            }
        }
        horarioVO.setTotalHoras(horasDia);
        horarioVO.setTotalHorasRecuperacion(horasRecuoeracion);
    }

    /**
     * Este metodo controla las horas semanas y dias.
     *
     * @param lista List
     * @param lh LicenciaHorario
     */
    private void controlHoras(final List<LicenciaHorario> lista, final LicenciaHorario lh) {
        Object[] control = controlCruceHorarios(lista, lh);
        Boolean cruce = (Boolean) control[0];
        if (!cruce) {
            Long horasDia = (Long) control[1];
            Long horasDiaRecuperacion = (Long) control[2];
            Long horaIngresada = UtilFechas.calcularDiferenciaMinutosEntreFechas(lh.getHoraInicio(), lh.getHoraFin());
            horasDia += horaIngresada;
            horasDiaRecuperacion += horaIngresada;
            Boolean controlDia = Boolean.TRUE;
            Boolean controlDiaRecuperacion = Boolean.TRUE;
            Boolean controlSemana;
            Boolean controlSemanaRecuperacion;
            Boolean esRecuperacion = esRecuperacion(lh);
            if (licenciaHorarioHelper.getTipoMovimiento().getPeriodoControl().equals(
                    PeriodoControlEnum.DIARIO.getCodigo())) {
                int totalPC = licenciaHorarioHelper.getTipoMovimiento().getValorPeriodoControl() * 60;
                controlDia = horasDia <= totalPC;
                controlDiaRecuperacion = horasDiaRecuperacion <= totalPC;
                if (!controlDia && !esRecuperacion) {
                    horasDia -= horaIngresada;
                    mostrarMensajeEnPantalla("El límite de horas por día está cubierto.",
                            FacesMessage.SEVERITY_WARN);
                }
                if (esRecuperacion && !controlDiaRecuperacion) {
                    horasDiaRecuperacion -= horaIngresada;
                    mostrarMensajeEnPantalla("El límite de horas de recuperación por día está cubierto.",
                            FacesMessage.SEVERITY_WARN);
                }
                if (esRecuperacion && horasDiaRecuperacion > (horasDia - horaIngresada)) {
                    controlDia = Boolean.FALSE;
                    controlDiaRecuperacion = Boolean.FALSE;
                    mostrarMensajeEnPantalla(UtilCadena.concatenar("El límite de horas de recuperación",
                            " no puede ser mayo a numero de horas Licencia / Permisos."),
                            FacesMessage.SEVERITY_WARN);
                }
            }
            calcularTotales();
            Long totalSemana = licenciaHorarioHelper.getTotalHorasSemana() + horasDia;
            Long totalSemanaRecuperacion = licenciaHorarioHelper.getTotalHorasSemanaRecuperacion() + horasDia;
            int totalSemanaControl = licenciaHorarioHelper.getTipoMovimiento().getTotalHorasSemana() * 60;
            controlSemana = totalSemana > totalSemanaControl;
            controlSemanaRecuperacion = totalSemanaRecuperacion > totalSemanaControl;
            if (controlSemana) {
                mostrarMensajeEnPantalla("El límite de horas por semana esta cubierto.",
                        FacesMessage.SEVERITY_WARN);
            }
            if (controlSemanaRecuperacion && esRecuperacion) {
                mostrarMensajeEnPantalla("El límite en recuperación de horas por semana esta cubierto.",
                        FacesMessage.SEVERITY_WARN);
            }
            if (esRecuperacion && controlDiaRecuperacion && !controlSemanaRecuperacion) {
                creacionHorario(lh, horaIngresada, lista);
            } else if (!esRecuperacion && controlDia && !controlSemana) {
                creacionHorario(lh, horaIngresada, lista);
            }
        }
    }

    /**
     * Este metodo crea el horario sin importar el tipo.
     *
     * @param lh LicenciaHorario
     * @param horaIngresada Long
     * @param lista List
     */
    private void creacionHorario(final LicenciaHorario lh,
            final Long horaIngresada, final List<LicenciaHorario> lista) {
        lh.setTotalMinutos(horaIngresada);
        lista.add(lh);
        ejecutarComandoPrimefaces("dlgNuevaHoraDia.hide();");
        actualizarComponente("frmHorario");
    }

    /**
     * Este metodo retorna la lista de horarios segun el dia.
     *
     * @param dia int
     * @return List
     */
    public List<LicenciaHorario> extraerListaHorario(final int dia) {
        return licenciaHorarioHelper.getHorarios().get(dia).getHorario();
    }

    /**
     * Este metodo retorna la lista de horarios segun el dia.
     *
     * @param dia int
     * @return List
     */
    public HorarioVO extraerHorario(final int dia) {
        return licenciaHorarioHelper.getHorarios().get(dia);
    }

    /**
     * Este metodo controla el cruce de una nueva hora.
     *
     * @param lista List
     * @param lh LicenciaHorario
     * @return Object
     */
    private Object[] controlCruceHorarios(final List<LicenciaHorario> lista,
            final LicenciaHorario lh) {
        Boolean cruce = Boolean.FALSE;
        Long horasDia = 0L;
        Long horasRecuperacion = 0L;
        for (LicenciaHorario l : lista) {
            if ((compare(l.getHoraInicio(), lh.getHoraInicio()) > 0 && compare(
                    l.getHoraInicio(), lh.getHoraFin()) < 0) || (compare(
                    l.getHoraFin(), lh.getHoraInicio()) > 0 && compare(
                    l.getHoraFin(), lh.getHoraFin()) < 0)) {
                cruce = Boolean.TRUE;
                mostrarMensajeEnPantalla("La fecha se cruza con el horario de " + completar(
                        l.getHoraInicio().getHours()) + ":" + completar(
                        l.getHoraInicio().getMinutes()) + " a " + completar(
                        l.getHoraFin().getHours()) + ":" + completar(
                        l.getHoraFin().getMinutes()) + " del dia " + DiasEnum.obtenerNombre(
                        l.getDia()),
                        FacesMessage.SEVERITY_WARN);
                break;
            } else {
                Long horas = UtilFechas.calcularDiferenciaMinutosEntreFechas(
                        l.getHoraInicio(), l.getHoraFin());
                if (esRecuperacion(l)) {
                    horasRecuperacion += horas;
                } else {
                    horasDia += horas;
                }

            }
        }
        Object[] ob = {cruce, horasDia, horasRecuperacion};
        return ob;
    }

    /**
     * Este metodo evalua el tipo de horario.
     *
     * @param l LicenciaHorario
     * @return boolean
     */
    private boolean esRecuperacion(final LicenciaHorario l) {
        return l.getTipo() != null && l.getTipo().equals(
                TipoHorarioEnum.RECUPERACION.getCodigo());
    }

    /**
     * Compara fechas.
     *
     * @param f1 Date
     * @param f2 Date
     * @return int
     */
    private int compare(final Date f1, final Date f2) {
        return f1.compareTo(f2);
    }

    /**
     * @return the licenciaHorarioHelper
     */
    public LicenciaHorarioHelper getLicenciaHorarioHelper() {
        return licenciaHorarioHelper;
    }

    /**
     * @param licenciaHorarioHelper the licenciaHorarioHelper to set
     */
    public void setLicenciaHorarioHelper(final LicenciaHorarioHelper licenciaHorarioHelper) {
        this.licenciaHorarioHelper = licenciaHorarioHelper;
    }

    /**
     * @return the registroSolicitudHelper
     */
    public RegistroSolicitudHelper getRegistroSolicitudHelper() {
        return registroSolicitudHelper;
    }

    /**
     * @param registroSolicitudHelper the registroSolicitudHelper to set
     */
    public void setRegistroSolicitudHelper(final RegistroSolicitudHelper registroSolicitudHelper) {
        this.registroSolicitudHelper = registroSolicitudHelper;
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
}
