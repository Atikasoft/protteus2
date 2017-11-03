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
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Experiencia del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_experiencia", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorExperiencia.BUSCAR_VIGENTES, query
            = "SELECT c FROM ServidorExperiencia c where c.vigente=true"),
    @NamedQuery(name = ServidorExperiencia.BUSCAR_POR_SERVIDOR_ID, query
            = "SELECT c FROM ServidorExperiencia c where  c.servidor.id=?1  and c.vigente=true")
})
public class ServidorExperiencia extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ServidorExperiencia.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "ServidorExperiencia.buscarServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorExperiencia() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_desde")
    private Date fechaDesde;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_hasta")
    private Date fechaHasta;
    @Column(name = "empresa")
    private String empresa;
    @Column(name = "denominacion_puesto")
    private String denominacionPuesto;
    @Column(name = "actividades")
    private String actividades;
    @Column(name = "razon_salida")
    private String razonSalida;
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
     * @return the fechaDesde
     */
    public Date getFechaDesde() {
        return fechaDesde;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @return the denominacionPuesto
     */
    public String getDenominacionPuesto() {
        return denominacionPuesto;
    }

    /**
     * @return the actividades
     */
    public String getActividades() {
        return actividades;
    }

    /**
     * @return the razonSalida
     */
    public String getRazonSalida() {
        return razonSalida;
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
     * @param fechaDesde the fechaDesde to set
     */
    public void setFechaDesde(final Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(final Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(final String empresa) {
        this.empresa = empresa;
    }

    /**
     * @param denominacionPuesto the denominacionPuesto to set
     */
    public void setDenominacionPuesto(final String denominacionPuesto) {
        this.denominacionPuesto = denominacionPuesto;
    }

    /**
     * @param actividades the actividades to set
     */
    public void setActividades(final String actividades) {
        this.actividades = actividades;
    }

    /**
     * @param razonSalida the razonSalida to set
     */
    public void setRazonSalida(final String razonSalida) {
        this.razonSalida = razonSalida;
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
