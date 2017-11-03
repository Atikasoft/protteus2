/*
 *  VacacionSolicitudHistorico.java
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
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones_solicitudes_historicos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionSolicitudHistorico.BUSCAR_VIGENTES,
            query = "SELECT a FROM VacacionSolicitudHistorico a where a.vigente=true order by a.fechaCreacion desc ")
})
@Setter
@Getter
public class VacacionSolicitudHistorico extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VacacionSolicitudHistorico.buscarVigente";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacionServidor;

    /**
     *
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacionServidor;

    /**
     *
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombresServidor;

    /**
     * Datos del Funcionario
     */
    @Column(name = "tipo_identificacion_aprobador")
    private String tipoIdentificacionAprobador;

    /**
     *
     */
    @Column(name = "numero_identificacion_aprobador")
    private String numeroIdentificacionAprobador;

    /**
     *
     */
    @Column(name = "apellidos_nombres_aprobador")
    private String apellidosNombresAprobador;

    /**
     * Referencia vacacionParametro.
     */
    @JoinColumn(name = "vacacion_parametro_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VacacionParametro vacacionParametro;

    /**
     * vacacionParametro id.
     */
    @Column(name = "vacacion_parametro_id")
    private Long idVacacionParametro;

    /**
     * Referencia VacacionSolicitud.
     */
    @JoinColumn(name = "vacacion_solicitud_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private VacacionSolicitud vacacionSolicitud;
    /**
     * servidorInstitucion id.
     */
    @Column(name = "vacacion_solicitud_id")
    private Long idVacacionSolicitud;

    /**
     * Campo tipo: V-Vacación, A-Adelanto Vacacion, P-Permiso con cargo a
     * vacación
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * Campo fechaFin.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fecha;

    /**
     * Campo saldoActual efectiva.
     */
    @Column(name = "saldo_vacaciones_efectiva")
    private Long saldoVacacionesEfectiva;

    /**
     * Campo saldoActual efectiva.
     */
    @Column(name = "saldo_vacaciones_proporcional")
    private Long saldoVacacionesProporcial;
    
    /**
     * Coma separado de las fechas seleccionadas en formato dd/MM/yyyy
     */
    @Column(name = "dias_planificados")
    private String diasPlanificados;

    /**
     * Campo estado:  <R>egistrado, <N>egado, <A>probado'
     */
    @Column(name = "estado")
    private String estado;

    /**
     * Tipo de periodo: Dia - Hora - Minuto
     */
    @Column(name = "tipo_periodo")
    private String tipoPeriodo;

    /**
     * Campo observacion.
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * Número de Días No Laborables que se incluyen en la solicitud, que el
     * Sistema considera en la solicitud, debido para el conteo de días
     * laborables necesarios e imputación de dias.
     */
    @Column(name = "minutos_imputados")
    private BigDecimal minutosImputados;


    /**
     * Campo numero_dias: Número de días total de vacaciones. Cod Trab:15 -
     * LOSEP:30
     */
    @Column(name = "numero_dias")
    private Integer numeroDias;

    /**
     * Campo maximo_acumulacion: Máximo número de días o periodos a acumular
     * para las vacaciones
     */
    @Column(name = "maximo_acumulacion")
    private Integer maximoAcumulacion;

    /**
     * Campo tipo_acumulacion: Tipo de acumulacion <D>ias,
     * <P>
     * eriodos
     */
    @Column(name = "tipo_acumulacion")
    private String tipoAcumulacion;

    /**
     * Campo tipo_acumulacion: Número mínimo de dias que se pueden tomar por
     * vacaciones
     */
    @Column(name = "minimo_dias_tomar_vacacion")
    private Integer minimoDiasTomarVacacion;

    /**
     * Campo maximos_dias_tomar_permisos: Número máximo de dias que se pueden
     * tomar por permisos
     */
    @Column(name = "maximos_dias_tomar_permisos")
    private Integer maximosDiasTomarPermisos;
    /**
     * Campo tiempoProporcionalMensual. Determina el tiempo proporcional de
     * vacacion de derecho mensual.
     */
    @Column(name = "tiempo_proporcional_mensual")
    private BigDecimal tiempoProporcionalMensual;

    /**
     * Campo periodo_inicio: Orden del periodos desde el cual aplica el
     * parametro. Generalmente refiere a los años (Ejercicio fiscal).
     */
    @Column(name = "periodo_inicio")
    private Integer periodoInicio;

    /**
     * Campo periodo_fin: Orden del periodo hasta el cual aplica el parametro.
     * Generalmente refiere a los años (ejercicio fiscal).
     */
    @Column(name = "periodo_fin")
    private Integer periodoFin;
    /**
     * Campo diasFinSemanaImputables Número de días imputables que corresponden
     * a los fines de semana que se deben agregar una vez completados los días
     * laborables.
     */
    @Column(name = "dias_imputables")
    private BigDecimal diasImputables;

    /**
     * Campo diasLabParaImputacion, corresponde al número de días laborables que
     * se deben ir considerando para poder agregar los días Imputables.
     */
    @Column(name = "dias_lab_para_imputacion")
    private BigDecimal diasLabParaImputacion;
    /**
     * Campo valor_incremento: Número de días que se eincrementan cada periodo
     */
    @Column(name = "valor_incremento")
    private Integer valorIncremento;

    /**
     * Campo justificacion: Indica si existe justificación para la acumulación
     * extraordinaria de vacaciones
     */
    @Column(name = "justificacion")
    private Boolean justificacion;

    /**
     * Campo minimo_meses_derecho_vacacion: Número mínimo de meses que se deben
     * tener para tener derecho a las vacaciones
     */
    @Column(name = "minimo_meses_derecho_vacacion")
    private Integer minimoMesesDerechoVacacion;

    /**
     * Referencia a ejercicioFiscal
     */
    @JoinColumn(name = "regimen_laboral_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;
    /**
     * ejercicioFiscal id.
     */
    @Column(name = "regimen_laboral_id")
    private Long idRegimenLaboral;

    /**
     * Constructor.
     */
    public VacacionSolicitudHistorico() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionSolicitudHistorico(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
