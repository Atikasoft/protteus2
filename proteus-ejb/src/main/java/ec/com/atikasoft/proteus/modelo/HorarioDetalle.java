/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
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
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Leydis Garzón
 */
@Entity
@Table(name = "horarios_detalles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = HorarioDetalle.BUSCAR_POR_NOMBRE_HORARIO,
                query = "SELECT o FROM HorarioDetalle o WHERE o.vigente = true AND o.horario.nombre LIKE ?1"),
    @NamedQuery(name = HorarioDetalle.BUSCAR_POR_HORARIO_ID,
                query = "SELECT o FROM HorarioDetalle o WHERE o.vigente = true AND o.horario.id=?1")
})
@Setter
@Getter
public class HorarioDetalle extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_POR_NOMBRE_HORARIO = "HorarioDetalle.buscarPorNombreHorario";
    
    /**
     *
     */
    public static final String BUSCAR_POR_HORARIO_ID = "HorarioDetalle.buscarPorHorarioId";

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *
     */
    @JoinColumn(name = "horario_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Horario horario;

    /**
     *
     */
    @Column(name = "dia_semana", nullable = false)
    private String diaDeSemana;

    /**
     *
     */
    @Column(name = "laborable")
    private Boolean laborable;

    /**
     *
     */
    @Column(name = "total_horas_diaria")
    private Integer totalHorasDiaria;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_entrada")
    private Date horaEntrada;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_inicio_almuerzo")
    private Date horaSalidaAlmuerzo;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_fin_almuerzo")
    private Date horaEntradaAlmuerzo;

    /**
     *
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "hora_salida")
    private Date horaSalida;
    
    
    /**
     * Indica la duración del período de almuerzo
     */
    @Column(name = "tiempo_almuerzo_permitido")
    private Integer tiempoAlmuerzoPermitido;

    /**
     *
     */
    @Transient
    private String diaNombre;

    /**
     *
     */
    public HorarioDetalle() {
    }

    /**
     *
     * @param diaDeSemana
     */
    public HorarioDetalle(String diaDeSemana) {
        this.diaDeSemana = diaDeSemana;
    }

    /**
     *
     * @param diaDeSemana
     * @param diaNombre
     */
    public HorarioDetalle(String diaDeSemana, String diaNombre) {
        this.diaDeSemana = diaDeSemana;
        this.diaNombre = diaNombre;
    }

    /**
     *
     */
    @PostLoad
    private void cargarDiaNombre() {
        this.diaNombre = DiasEnum.obtenerNombre(Integer.valueOf(this.diaDeSemana));
    }

}
