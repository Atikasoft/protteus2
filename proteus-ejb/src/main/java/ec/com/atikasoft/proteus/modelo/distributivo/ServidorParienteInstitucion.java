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
 * Pariente en la institucion del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_parientes_institucion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorParienteInstitucion.BUSCAR_VIGENTES, query
            = "SELECT c FROM ServidorParienteInstitucion c where c.vigente=true"),
    @NamedQuery(name = ServidorParienteInstitucion.BUSCAR_POR_SERVIDOR_ID, query
            = "SELECT c FROM ServidorParienteInstitucion c where  c.servidor.id=?1  and c.vigente=true")
})
public class ServidorParienteInstitucion extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ServidorParienteInstitucion.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "ServidorParienteInstitucion.buscarServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorParienteInstitucion() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombres ")
    private String nombres;
    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne
    private Servidor servidor;
    @Column(name = "servidor_id")
    private Long servidorid;
    /**
     * Referencia con catalogo de parentezco.
     */
    @JoinColumn(name = "catalago_parentezco_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalagoParentezco;
    /**
     * Referencia con catalogo de dependencia.
     */
    @JoinColumn(name = "catalago_dependencia_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalagoDependencia;
    @Column(name = "catalago_parentezco_id")
    private Long catalagoParentezcoid;
    @Column(name = "catalago_dependencia_id")
    private Long catalagoDependenciaid;
    
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
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @return the catalagoParentezco
     */
    public Catalogo getCatalagoParentezco() {
        return catalagoParentezco;
    }

    /**
     * @return the catalagoDependencia
     */
    public Catalogo getCatalagoDependencia() {
        return catalagoDependencia;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @param catalagoParentezco the catalagoParentezco to set
     */
    public void setCatalagoParentezco(final Catalogo catalagoParentezco) {
        this.catalagoParentezco = catalagoParentezco;
    }

    /**
     * @param catalagoDependencia the catalagoDependencia to set
     */
    public void setCatalagoDependencia(final Catalogo catalagoDependencia) {
        this.catalagoDependencia = catalagoDependencia;
    }

    /**
     * @return the catalagoParentezcoid
     */
    public Long getCatalagoParentezcoid() {
        return catalagoParentezcoid;
    }

    /**
     * @param catalagoParentezcoid the catalagoParentezcoid to set
     */
    public void setCatalagoParentezcoid(final Long catalagoParentezcoid) {
        this.catalagoParentezcoid = catalagoParentezcoid;
    }

    /**
     * @return the catalagoDependenciaid
     */
    public Long getCatalagoDependenciaid() {
        return catalagoDependenciaid;
    }

    /**
     * @param catalagoDependenciaid the catalagoDependenciaid to set
     */
    public void setCatalagoDependenciaid(final Long catalagoDependenciaid) {
        this.catalagoDependenciaid = catalagoDependenciaid;
    }

    /**
     * @return the servidorid
     */
    public Long getServidorid() {
        return servidorid;
    }

    /**
     * @param servidorid the servidorid to set
     */
    public void setServidorid(Long servidorid) {
        this.servidorid = servidorid;
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
