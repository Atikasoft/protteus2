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

import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Evaluacion del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_evaluacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorEvaluacion.BUSCAR_VIGENTES, query
            = "SELECT c FROM ServidorEvaluacion c where c.vigente=true"),
    @NamedQuery(name = ServidorEvaluacion.BUSCAR_POR_SERVIDOR_ID, query
            = "SELECT c FROM ServidorEvaluacion c where  c.servidor.id=?1  and c.vigente=true")
})
public class ServidorEvaluacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ServidorEvaluacion.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "ServidorEvaluacion.buscarServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorEvaluacion() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "periodo")
    private String periodo;
    @Column(name = "institucion_evaluadora")
    private String institucionEvaluadora;
    @Column(name = "evaluacion_obtenida")
    private String evaluacionObtenida;
    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne
    private Servidor servidor;
    @Column(name = "servidor_id")
    private Long servidorId;
    
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
     * @return the periodo
     */
    public String getPeriodo() {
        return periodo;
    }

    /**
     * @return the institucionEvaluadora
     */
    public String getInstitucionEvaluadora() {
        return institucionEvaluadora;
    }

    /**
     * @return the evaluacionObtenida
     */
    public String getEvaluacionObtenida() {
        return evaluacionObtenida;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(final String periodo) {
        this.periodo = periodo;
    }

    /**
     * @param institucionEvaluadora the institucionEvaluadora to set
     */
    public void setInstitucionEvaluadora(final String institucionEvaluadora) {
        this.institucionEvaluadora = institucionEvaluadora;
    }

    /**
     * @param evaluacionObtenida the evaluacionObtenida to set
     */
    public void setEvaluacionObtenida(final String evaluacionObtenida) {
        this.evaluacionObtenida = evaluacionObtenida;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
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
