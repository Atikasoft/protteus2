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
 * Capacitacion del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_capacitacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorCapacitacion.BUSCAR_POR_RECLUTAMIENTO_ID, query
            = "SELECT c FROM ServidorCapacitacion c where  c.servidorId=?1  and c.vigente=true")

})
public class ServidorCapacitacion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_RECLUTAMIENTO_ID = "ServidorCapacitacion.buscarPorServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorCapacitacion() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "evento")
    private String evento;
    @Column(name = "capacitador")
    private String capacitador;
    /**
     * <A>sistencia, A<p>
     * robacion.
     */
    @Column(name = "tipo_diploma")
    private String tipoDiploma;
    @Column(name = "duracion_horas")
    private Integer duracionHoras;
    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne

    private Servidor servidor;
    /**
     * Referencia con Servidor.
     */
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
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @return the capacitador
     */
    public String getCapacitador() {
        return capacitador;
    }

    /**
     * @return the tipoDiploma
     */
    public String getTipoDiploma() {
        return tipoDiploma;
    }

    /**
     * @return the duracionHoras
     */
    public Integer getDuracionHoras() {
        return duracionHoras;
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
     * @param evento the evento to set
     */
    public void setEvento(final String evento) {
        this.evento = evento;
    }

    /**
     * @param capacitador the capacitador to set
     */
    public void setCapacitador(final String capacitador) {
        this.capacitador = capacitador;
    }

    /**
     * @param tipoDiploma the tipoDiploma to set
     */
    public void setTipoDiploma(final String tipoDiploma) {
        this.tipoDiploma = tipoDiploma;
    }

    /**
     * @param duracionHoras the duracionHoras to set
     */
    public void setDuracionHoras(final Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
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
