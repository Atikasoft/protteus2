/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Reclutamiento
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "reclutamientos_trayectoria_laboral", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ReclutamientoTrayectoriaLaboral.BUSCAR_VIGENTES, query
            = "SELECT c FROM ReclutamientoTrayectoriaLaboral c where c.vigente=true"),
    @NamedQuery(name = ReclutamientoTrayectoriaLaboral.BUSCAR_POR_RECLUTAMIENTO_ID, query
            = "SELECT c FROM ReclutamientoTrayectoriaLaboral c where  c.reclutamientoId=?1  and c.vigente=true")
})
public class ReclutamientoTrayectoriaLaboral extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ReclutamientoTrayectoriaLaboral.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_RECLUTAMIENTO_ID = "ReclutamientoTrayectoriaLaboral.buscarPorReclutamientoId";

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
     * Campo fecha.
     */
    @Column(name = "fecha_inicio")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaInicio;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha_fin")
    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fechaFin;

    /**
     * Campo que indica la cantidad en mese y años.
     */
    @Column(name = "mes_trayectoria")
    private Long mesTrayectoria;

    /**
     * Campo que indica la cantidad en mese y años.
     */
    @Column(name = "anios")
    private Long anios;
    /**
     * organizacionEmpresa.
     */
    @Column(name = "organizacion_empresa")
    private String organizacionEmpresa;
    /**
     * denominacionPuesto.
     */
    @Column(name = "denominacion_puesto")
    private String denominacionPuesto;
    /**
     * responsabilidades.
     */
    @Column(name = "Responsabilidades")
    private String responsabilidades;
    /**
     * titulo_obtenido.
     */
    @Column(name = "razon_salida")
    private String razonSalida;

    public ReclutamientoTrayectoriaLaboral() {
    }

    public ReclutamientoTrayectoriaLaboral(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the mesTrayectoria
     */
    public Long getMesTrayectoria() {
        return mesTrayectoria;
    }

    /**
     * @param mesTrayectoria the mesTrayectoria to set
     */
    public void setMesTrayectoria(Long mesTrayectoria) {
        this.mesTrayectoria = mesTrayectoria;
    }

    /**
     * @return the organizacionEmpresa
     */
    public String getOrganizacionEmpresa() {
        return organizacionEmpresa;
    }

    /**
     * @param organizacionEmpresa the organizacionEmpresa to set
     */
    public void setOrganizacionEmpresa(String organizacionEmpresa) {
        this.organizacionEmpresa = organizacionEmpresa;
    }

    /**
     * @return the denominacionPuesto
     */
    public String getDenominacionPuesto() {
        return denominacionPuesto;
    }

    /**
     * @param denominacionPuesto the denominacionPuesto to set
     */
    public void setDenominacionPuesto(String denominacionPuesto) {
        this.denominacionPuesto = denominacionPuesto;
    }

    /**
     * @return the responsabilidades
     */
    public String getResponsabilidades() {
        return responsabilidades;
    }

    /**
     * @param responsabilidades the responsabilidades to set
     */
    public void setResponsabilidades(String responsabilidades) {
        this.responsabilidades = responsabilidades;
    }

    /**
     * @return the razonSalida
     */
    public String getRazonSalida() {
        return razonSalida;
    }

    /**
     * @param razonSalida the razonSalida to set
     */
    public void setRazonSalida(String razonSalida) {
        this.razonSalida = razonSalida;
    }

    /**
     * @return the anios
     */
    public Long getAnios() {
        return anios;
    }

    /**
     * @param anios the anios to set
     */
    public void setAnios(Long anios) {
        this.anios = anios;
    }

}
