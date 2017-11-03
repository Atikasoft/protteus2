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
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "reclutamientos_instrucciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ReclutamientoInstruccion.BUSCAR_VIGENTES, query
            = "SELECT c FROM ReclutamientoInstruccion c where c.vigente=true "),
    @NamedQuery(name = ReclutamientoInstruccion.BUSCAR_POR_RECLUTAMIENTO_ID, query
            = "SELECT c FROM ReclutamientoInstruccion c where  c.reclutamientoId=?1  and c.vigente=true")
        
})
public class ReclutamientoInstruccion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ReclutamientoInstruccion.buscarVigentes";
        /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_RECLUTAMIENTO_ID = "ReclutamientoInstruccion.buscarPorReclutamientoId";

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
     * nivel catalogo
     */
    @JoinColumn(name = "nivel_instruccion_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Catalogo catalogoNivelInstruccion;
    /**
     * nivel instruccion.
     */
    @Column(name = "nivel_instruccion_id")
    private Long nivelInstruccionId;
    /**
     * nombre_institucion.
     */
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;
    /**
     * especializacion.
     */
    @Column(name = "especializacion")
    private String especializacion;
    /**
     * titulo_obtenido.
     */
    @Column(name = "titulo_obtenido")
    private String tituloObtenido;

    public ReclutamientoInstruccion() {
        super();
    }

    public ReclutamientoInstruccion(final Long id) {
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
    public void setReclutamientoId(final Long reclutamientoId) {
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
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * @return the especializacion
     */
    public String getEspecializacion() {
        return especializacion;
    }

    /**
     * @param especializacion the especializacion to set
     */
    public void setEspecializacion(final String especializacion) {
        this.especializacion = especializacion;
    }

    /**
     * @return the tituloObtenido
     */
    public String getTituloObtenido() {
        return tituloObtenido;
    }

    /**
     * @param tituloObtenido the tituloObtenido to set
     */
    public void setTituloObtenido(final String tituloObtenido) {
        this.tituloObtenido = tituloObtenido;
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
    public void setReclutamiento(final Reclutamiento reclutamiento) {
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
     * @return the nivelInstruccionId
     */
    public Long getNivelInstruccionId() {
        return nivelInstruccionId;
    }

    /**
     * @param nivelInstruccionId the nivelInstruccionId to set
     */
    public void setNivelInstruccionId(final Long nivelInstruccionId) {
        this.nivelInstruccionId = nivelInstruccionId;
    }

    /**
     * @return the catalogoNivelInstruccion
     */
    public Catalogo getCatalogoNivelInstruccion() {
        return catalogoNivelInstruccion;
    }

    /**
     * @param catalogoNivelInstruccion the catalogoNivelInstruccion to set
     */
    public void setCatalogoNivelInstruccion(final Catalogo catalogoNivelInstruccion) {
        this.catalogoNivelInstruccion = catalogoNivelInstruccion;
    }

}
