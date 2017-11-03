/*
 *  VacacionSolicitudHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitudHistorico;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.UploadedFile;

/**
 * VacacionSolicitud
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionSolicitudHelper")
@SessionScoped
@Setter
@Getter
public class VacacionSolicitudHelper extends CatalogoHelper {

    /**
     * clase vacacionSolicitud.
     */
    private VacacionSolicitud vacacionSolicitud;
    /**
     * clase vacacionSolicitud puesto para editar.
     */
    private VacacionSolicitud vacacionSolicitudEditDelete;
    /**
     * lista de vacacionSolicitudes.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudes;
    /**
     * Variable para listar las alertas por nemonicos.
     */
    private List<VacacionSolicitud> listaVacacionSolicitudCodigo;
    /**
     * Variable para historico de solicitud de vacaciones
     */
    private VacacionSolicitudHistorico vacacionSolicitudHistorico;
    /**
     * lista de detalles de la planificacion de vacaciones.
     */
    private List<PlanificacionVacacionDetalle> listaVacacionDetalles;
    /**
     * *
     *
     */
    private boolean tienePlanificaciones;
    /**
     * detalle de la planificacion de vacaciones q actualiza su estado.
     */
    private PlanificacionVacacionDetalle vacacionDetalle;
    /**
     * Variable para administracion de acumulacion de vacaciones
     */
    private Vacacion vacacion;
    /**
     *
     */
    private List<Vacacion> listaVacacion;
    /**
     *
     */
    private List<Vacacion> listaVacacionAcreditacion;
    /**
     * Obtener usuario conectado.
     */
    private UsuarioVO usuario;
    /**
     * Variables temporales para mostrar datos calculados
     */
    private String cadenaSaldo;
    /**
     *
     */
    private Integer saldo[];
    /**
     *
     */
    private String cadenaSaldoProporcional;
    /**
     *
     */
    private Integer saldoProporcional[];

    /**
     * Tiempo del Empleado en la empresa calculado en años/meses/días
     */
    private Integer tiempoEnEmpresa[];

    /**
     * Variables para opciones tipo vacacion y estado vacacion
     */
    private List<SelectItem> listaOpcionesTipoVacacion;
    /**
     *
     */
    private List<SelectItem> listaOpcionesEstadoVacacion;
    /**
     *
     */
    private List<SelectItem> listaOpcionesPeriodoVacacion;

    /**
     *
     */
    private String cadenaTiempEnEmpresa;

    /**
     * Variables para indicar si se muestran o no los controles
     */
    private Boolean enviarSolicitud;
    /**
     *
     */
    private Boolean msgFeriados;
    /**
     *
     */
    private BigDecimal totalDiasSolicitud;
    /**
     * Variable para restringir el inicio del calendario para la fecha de inicio
     */
    private Date hoy;

    /**
     * archivoFile.
     */
    private File archivoFile;
    /**
     * foto del servidor.
     */
    private String nombreArchivo;
    /**
     * Archivo para justificacion.
     */
    private UploadedFile archivoCargado;
    /**
     * Total planificado.
     */
    private Long totalPlanificado;
    /**
     * Total saldo.
     */
    private Double totalSaldo;
    /**
     * Planificaciones no aprobadas.
     */
    private int planificacionesAprobadas;

    /**
     *
     */
    private String diasPlanificados;
    /**
     *
     */
    private boolean tieneSaldoEfectivo;

    /**
     *
     */
    private boolean tieneSaldoProporcional;
    /**
     *
     */
    private Date fechaMaxima;

    /**
     *
     */
    private Date fechaMinima;

    /**
     *
     */
    private Integer horaMaxima;

    /**
     *
     */
    private Integer horaMinima;

    /**
     *
     */
    private Integer minutoMaximo;

    /**
     *
     */
    private Integer minutoMinimo;

    /**
     *
     */
    private String duracionMinutos;

    /**
     *
     */
    private Long diasSolicitados;

    /**
     * OBSERVACION CUANDO SE REVIERTE UNA SOLICITUD
     */
    private String observacionReversion;
    
    /**
     * 
     */
    private Long minutosVacacionesSolicitadas;

    /**
     * Constructor por defecto.
     */
    public VacacionSolicitudHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     *
     */
    public final void iniciador() {
        try {
            hoy = new SimpleDateFormat("ddMMyyyy").parse("01012016");
        } catch (Exception e) {
        }

        setVacacionSolicitud(new VacacionSolicitud());
        getVacacionSolicitud().setMinutosImputados(BigDecimal.ZERO);
        setVacacionSolicitudEditDelete(new VacacionSolicitud());
        setListaVacacionSolicitudes(new ArrayList<VacacionSolicitud>());
        setListaVacacionSolicitudCodigo(new ArrayList<VacacionSolicitud>());
        setVacacionSolicitudHistorico(new VacacionSolicitudHistorico());
        setVacacion(new Vacacion());
        setListaVacacion(new ArrayList<Vacacion>());
        setListaOpcionesTipoVacacion(new ArrayList<SelectItem>());
        setListaOpcionesEstadoVacacion(new ArrayList<SelectItem>());
        setListaOpcionesPeriodoVacacion(new ArrayList<SelectItem>());
        cadenaSaldo = "";
        cadenaTiempEnEmpresa = "";
        setEnviarSolicitud(Boolean.FALSE);
        setListaVacacionAcreditacion(new ArrayList<Vacacion>());
        tiempoEnEmpresa = new Integer[3];
        setSaldo(new Integer[3]);
        setTotalDiasSolicitud(BigDecimal.ZERO);
        msgFeriados = Boolean.FALSE;
        listaVacacionDetalles = new ArrayList<>();
        vacacionDetalle = new PlanificacionVacacionDetalle();
    }

}
