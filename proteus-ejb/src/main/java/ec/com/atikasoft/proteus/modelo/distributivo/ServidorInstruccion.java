/*
 *  TipoNomina.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo.distributivo;

import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Instruccion del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_instrucion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorInstruccion.BUSCAR_VIGENTES, query
            = "SELECT c FROM ServidorInstruccion c where c.vigente=true"),
    @NamedQuery(name = ServidorInstruccion.BUSCAR_POR_SERVIDOR_ID, query
            = "SELECT c FROM ServidorInstruccion c where  c.servidor.id=?1  and c.vigente=true")
})
public class ServidorInstruccion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ServidorInstruccion.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "ServidorInstruccion.buscarServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorInstruccion() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "unidad_educativa")
    private String unidadEducativa;
    @Column(name = "especializacion")
    private String especializacion;
    @Column(name = "titulo")
    private String titulo;
    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne
    private Servidor servidor;
    @Column(name = "servidor_id")
    private Long servidorId;
    /**
     * Referencia con catalogo de niveles de instruccion.
     */
    @JoinColumn(name = "catalogo_nivel_instruccion_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoNivelInstruccion;
    @Column(name = "catalogo_nivel_instruccion_id")
    private Long catalogoNivelInstruccionId;
    
    @Column(name = "estado")
    private String estado;
    
    @Transient
    private String estadoNombre;
    
    @Transient
    private Boolean bloqueado;

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
     * @return the unidadEducativa
     */
    public String getUnidadEducativa() {
        return unidadEducativa;
    }

    /**
     * @return the especializacion
     */
    public String getEspecializacion() {
        return especializacion;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @return the catalogoNivelInstruccion
     */
    public Catalogo getCatalogoNivelInstruccion() {
        return catalogoNivelInstruccion;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param unidadEducativa the unidadEducativa to set
     */
    public void setUnidadEducativa(final String unidadEducativa) {
        this.unidadEducativa = unidadEducativa;
    }

    /**
     * @param especializacion the especializacion to set
     */
    public void setEspecializacion(final String especializacion) {
        this.especializacion = especializacion;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @param catalogoNivelInstruccion the catalogoNivelInstruccion to set
     */
    public void setCatalogoNivelInstruccion(final Catalogo catalogoNivelInstruccion) {
        this.catalogoNivelInstruccion = catalogoNivelInstruccion;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the catalogoNivelInstruccionId
     */
    public Long getCatalogoNivelInstruccionId() {
        return catalogoNivelInstruccionId;
    }

    /**
     * @param catalogoNivelInstruccionId the catalogoNivelInstruccionId to set
     */
    public void setCatalogoNivelInstruccionId(final Long catalogoNivelInstruccionId) {
        this.catalogoNivelInstruccionId = catalogoNivelInstruccionId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
