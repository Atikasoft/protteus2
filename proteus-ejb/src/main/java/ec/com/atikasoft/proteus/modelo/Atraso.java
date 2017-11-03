/*
 *  Atraso.java
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
 *  26/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "atrasos_faltas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Atraso.BUSCAR_VIGENTES,
            query = "SELECT a FROM Atraso a where a.vigente=true order by a.fecha"),
    @NamedQuery(name = Atraso.BUSCAR_POR_SERVIDOR,
            query = "SELECT a FROM Atraso a where a.vigente=true and a.servidor.id = ?1 order by a.fecha desc"),
@NamedQuery(name = Atraso.BUSCAR_POR_PLANIFICACION_HORARIO,
            query = "SELECT a FROM Atraso a where a.vigente=true and a.planificacionHorario.id = ?1 order by a.fecha desc")})
public class Atraso extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Atraso.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_SERVIDOR = "Atraso.buscarPorServidor";

   
    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_PLANIFICACION_HORARIO = "Atraso.buscarPorPlanificacionHorario";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

      /**
     * Especifica la fecha.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.DATE)
    @NotNull(message = "La fecha es campo requerido")
    private Date fecha;

    /**
     * Referencia a procesos de asistencia.
     */
    @JoinColumn(name = "asistencia_proceso_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AsistenciaProceso asistenciaProceso;

    /**
     * Referencia a planificacion_horario, solo para horas extras.
     */
    @JoinColumn(name = "planificacion_horario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PlanificacionHorario planificacionHorario;

    @Size(max = 20)
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "minutos_atraso")
    private Long minutosAtraso;
    /**
     * Almacena el saldo por justificar.
     */
   @Column(name = "minutos_x_justificar")
    private Long minutosXJustificar;

    @Size(max = 20)
    @Column(name = "timbre1")
    private String timbre1; 

    @Size(max = 20)
    @Column(name = "timbre2")
    private String timbre2;

    @Size(max = 20)
    @Column(name = "timbre3")
    private String timbre3;

    @Size(max = 20)
    @Column(name = "timbre4")
    private String timbre4;

    @Size(max = 20)
    @Column(name = "timbre5")
    private String timbre5;

    @Size(max = 20)
    @Column(name = "timbre6")
    private String timbre6;

    @Size(max = 20)
    @Column(name = "timbre7")
    private String timbre7;

    @Size(max = 20)
    @Column(name = "timbre8")
    private String timbre8;

    @Size(max = 20)
    @Column(name = "timbre9")
    private String timbre9;

    @Size(max = 20)
    @Column(name = "timbre10")
    private String timbre10;
    /**
     * Lista de justificaciones.
     */
    @OneToMany(mappedBy = "atraso")
    private List<JustificacionAsistencia> listaJustificacionesAsistencia;

    /**
     * Determina si el registro ya ha sido pagado.
     */
    @NotNull
    @Column(name = "justificado")
    private Boolean esJustificado;

    /**
     * Constructor.
     */
    public Atraso() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Atraso(final Long id) {
        super();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the timbre1
     */
    public String getTimbre1() {
        return timbre1;
    }

    /**
     * @param timbre1 the timbre1 to set
     */
    public void setTimbre1(String timbre1) {
        this.timbre1 = timbre1;
    }

    /**
     * @return the timbre2
     */
    public String getTimbre2() {
        return timbre2;
    }

    /**
     * @param timbre2 the timbre2 to set
     */
    public void setTimbre2(String timbre2) {
        this.timbre2 = timbre2;
    }

    /**
     * @return the timbre3
     */
    public String getTimbre3() {
        return timbre3;
    }

    /**
     * @param timbre3 the timbre3 to set
     */
    public void setTimbre3(String timbre3) {
        this.timbre3 = timbre3;
    }

    /**
     * @return the timbre4
     */
    public String getTimbre4() {
        return timbre4;
    }

    /**
     * @param timbre4 the timbre4 to set
     */
    public void setTimbre4(String timbre4) {
        this.timbre4 = timbre4;
    }

    /**
     * @return the timbre5
     */
    public String getTimbre5() {
        return timbre5;
    }

    /**
     * @param timbre5 the timbre5 to set
     */
    public void setTimbre5(String timbre5) {
        this.timbre5 = timbre5;
    }

    /**
     * @return the timbre6
     */
    public String getTimbre6() {
        return timbre6;
    }

    /**
     * @param timbre6 the timbre6 to set
     */
    public void setTimbre6(String timbre6) {
        this.timbre6 = timbre6;
    }

    /**
     * @return the timbre7
     */
    public String getTimbre7() {
        return timbre7;
    }

    /**
     * @param timbre7 the timbre7 to set
     */
    public void setTimbre7(String timbre7) {
        this.timbre7 = timbre7;
    }

    /**
     * @return the timbre8
     */
    public String getTimbre8() {
        return timbre8;
    }

    /**
     * @param timbre8 the timbre8 to set
     */
    public void setTimbre8(String timbre8) {
        this.timbre8 = timbre8;
    }

    /**
     * @return the timbre9
     */
    public String getTimbre9() {
        return timbre9;
    }

    /**
     * @param timbre9 the timbre9 to set
     */
    public void setTimbre9(String timbre9) {
        this.timbre9 = timbre9;
    }

    /**
     * @return the timbre10
     */
    public String getTimbre10() {
        return timbre10;
    }

    /**
     * @param timbre10 the timbre10 to set
     */
    public void setTimbre10(String timbre10) {
        this.timbre10 = timbre10;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the minutosAtraso
     */
    public Long getMinutosAtraso() {
        return minutosAtraso;
    }

    /**
     * @param minutosAtraso the minutosAtraso to set
     */
    public void setMinutosAtraso(Long minutosAtraso) {
        this.minutosAtraso = minutosAtraso;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the asistenciaProceso
     */
    public AsistenciaProceso getAsistenciaProceso() {
        return asistenciaProceso;
    }

    /**
     * @param asistenciaProceso the asistenciaProceso to set
     */
    public void setAsistenciaProceso(AsistenciaProceso asistenciaProceso) {
        this.asistenciaProceso = asistenciaProceso;
    }

    /**
     * @return the esJustificado
     */
    public Boolean getEsJustificado() {
        return esJustificado;
    }

    /**
     * @param esJustificado the esJustificado to set
     */
    public void setEsJustificado(Boolean esJustificado) {
        this.esJustificado = esJustificado;
    }

    /**
     * @return the listaJustificacionesAsistencia
     */
    public List<JustificacionAsistencia> getListaJustificacionesAsistencia() {
        return listaJustificacionesAsistencia;
    }

    /**
     * @param listaJustificacionesAsistencia the listaJustificacionesAsistencia
     * to set
     */
    public void setListaJustificacionesAsistencia(List<JustificacionAsistencia> listaJustificacionesAsistencia) {
        this.listaJustificacionesAsistencia = listaJustificacionesAsistencia;
    }
 

    /**
     * @return the planificacionHorario
     */
    public PlanificacionHorario getPlanificacionHorario() {
        return planificacionHorario;
    }

    /**
     * @param planificacionHorario the planificacionHorario to set
     */
    public void setPlanificacionHorario(PlanificacionHorario planificacionHorario) {
        this.planificacionHorario = planificacionHorario;
    }

    /**
     * @return the minutosXJustificar
     */
    public Long getMinutosXJustificar() {
        return minutosXJustificar;
    }

    /**
     * @param minutosXJustificar the minutosXJustificar to set
     */
    public void setMinutosXJustificar(Long minutosXJustificar) {
        this.minutosXJustificar = minutosXJustificar;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
