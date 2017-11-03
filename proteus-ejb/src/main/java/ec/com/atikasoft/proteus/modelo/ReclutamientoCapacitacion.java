/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Reclutamiento
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@Entity
@Table(name = "reclutamientos_capacitacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ReclutamientoCapacitacion.BUSCAR_VIGENTES, query
            = "SELECT c FROM ReclutamientoCapacitacion c where c.vigente=true "),
    @NamedQuery(name = ReclutamientoCapacitacion.BUSCAR_POR_RECLUTAMIENTO_ID, query
            = "SELECT c FROM ReclutamientoCapacitacion c where  c.reclutamientoId=?1  and c.vigente=true")

})
public class ReclutamientoCapacitacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ReclutamientoCapacitacion.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_RECLUTAMIENTO_ID = "ReclutamientoCapacitacion.buscarPorReclutamientoId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Reclutamiento
     */
    @JoinColumn(name = "reclutamiento_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Reclutamiento reclutamiento;
    /**
     * Reclutamiento id.
     */
    @Column(name = "reclutamiento_id")
    private Long reclutamientoId;

    /**
     * nombre_evento.
     */
    @Column(name = "nombre_evento")
    private String nombreEvento;
    /**
     * nombre_institucion.
     */
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;
    /**
     * tipo_diploma.
     */
    @Column(name = "tipo_diploma")
    private String tipoDiploma;

    /**
     * duracion_horas.
     */
    @Column(name = "duracion_horas")
    private Long duracionHoras;

    public ReclutamientoCapacitacion() {
        super();
    }

    public ReclutamientoCapacitacion(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the reclutamientoId
     */
    public Long getReclutamientoId() {
        return reclutamientoId;
    }

    /**
     * @param reclutamientoId the reclutamientoId to set
     */
    public void setReclutamientoId(Long reclutamientoId) {
        this.reclutamientoId = reclutamientoId;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * @return the reclutamiento
     */
    public Reclutamiento getReclutamiento() {
        return reclutamiento;
    }

    /**
     * @param reclutamiento the reclutamiento to set
     */
    public void setReclutamiento(Reclutamiento reclutamiento) {
        this.reclutamiento = reclutamiento;
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
     * @return the nombreEvento
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * @param nombreEvento the nombreEvento to set
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    /**
     * @return the tipoDiploma
     */
    public String getTipoDiploma() {
        return tipoDiploma;
    }

    /**
     * @param tipoDiploma the tipoDiploma to set
     */
    public void setTipoDiploma(String tipoDiploma) {
        this.tipoDiploma = tipoDiploma;
    }

    /**
     * @return the duracionHoras
     */
    public Long getDuracionHoras() {
        return duracionHoras;
    }

    /**
     * @param duracionHoras the duracionHoras to set
     */
    public void setDuracionHoras(Long duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

}
