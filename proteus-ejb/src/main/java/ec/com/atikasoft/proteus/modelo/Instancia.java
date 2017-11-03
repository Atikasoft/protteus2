/*
 *  Instancia.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 20, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Tramite individual ejecutandose.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.instancias")
@NamedQueries({
    @NamedQuery(name = Instancia.BUSCAR_POR_IDENTIFICADOR_EXTERNO, query
            = "SELECT o FROM Instancia o WHERE o.identificadorExterno=?1")
})
public class Instancia extends EntidadBasica {

    /**
     * Consulta que recupera la instancia dado su identifiador externo.
     */
    public final static String BUSCAR_POR_IDENTIFICADOR_EXTERNO = "Instancia.buscarPorIdentificadorExterno";

    /**
     * Identificador unico de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identifica el origen de la instancia.
     */
    @Column(name = "origen")
    private String origen;

    /**
     * Descripcion de la instancia.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Identificador externo del proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "identificador_externo")
    private Long identificadorExterno;

    /**
     * Indica si la instancia del proceso finalizo.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "finalizado")
    private Boolean finalizado;

    /**
     * Numero externo de la instancia.
     */
    @Column(name = "numero_externo")
    private String numeroExterno;

    /**
     * Cedula del usuario asignado.
     */
    @Column(name = "usuario_asignado_cedula")
    private String usuarioAsignadoCedula;

    /**
     * Nombre del usuario asignado.
     */
    @Column(name = "usuario_asignado_nombre")
    private String usuarioAsignadoNombre;

    /**
     * Codigo de la institucion.
     */
    @Column(name = "codigo_institucion")
    private String codigoInstitucion;

    /**
     * Nombre de la institucion.
     */
    @Column(name = "nombre_institucion")
    private String nombreInstitucion;

    /**
     * Identificacion de la institucion en el core.
     */
    @Column(name = "institucion_core_id")
    private Long institucionCoreId;

    /**
     * Referencia del proceso.
     */
    @JoinColumn(name = "procesos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proceso proceso;

    /**
     * REferencia del estado.
     */
    @JoinColumn(name = "estados_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Fase fase;

    /**
     * Lista de detalles.
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "instancia")
    private List<Detalle> listaDetalles;

    /**
     * Constructor.
     */
    public Instancia() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the identificadorExterno
     */
    public long getIdentificadorExterno() {
        return identificadorExterno;
    }

    /**
     * @return the proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @return the estado
     */
    public Fase getFase() {
        return fase;
    }

    /**
     * @return the listaDetalles
     */
    public List<Detalle> getListaDetalles() {
        return listaDetalles;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param identificadorExterno the identificadorExterno to set
     */
    public void setIdentificadorExterno(final Long identificadorExterno) {
        this.identificadorExterno = identificadorExterno;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(final Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @param estado the estado to set
     */
    public void setFase(final Fase fase) {
        this.fase = fase;
    }

    /**
     * @param listaDetalles the listaDetalles to set
     */
    public void setListaDetalles(final List<Detalle> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    /**
     * @return the finalizado
     */
    public Boolean getFinalizado() {
        return finalizado;
    }

    /**
     * @param finalizado the finalizado to set
     */
    public void setFinalizado(final Boolean finalizado) {
        this.finalizado = finalizado;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the numeroExterno
     */
    public String getNumeroExterno() {
        return numeroExterno;
    }

    /**
     * @param numeroExterno the numeroExterno to set
     */
    public void setNumeroExterno(final String numeroExterno) {
        this.numeroExterno = numeroExterno;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(final String origen) {
        this.origen = origen;
    }

    /**
     * @return the usuarioAsignadoCedula
     */
    public String getUsuarioAsignadoCedula() {
        return usuarioAsignadoCedula;
    }

    /**
     * @return the usuarioAsignadoNombre
     */
    public String getUsuarioAsignadoNombre() {
        return usuarioAsignadoNombre;
    }

    /**
     * @param usuarioAsignadoCedula the usuarioAsignadoCedula to set
     */
    public void setUsuarioAsignadoCedula(final String usuarioAsignadoCedula) {
        this.usuarioAsignadoCedula = usuarioAsignadoCedula;
    }

    /**
     * @param usuarioAsignadoNombre the usuarioAsignadoNombre to set
     */
    public void setUsuarioAsignadoNombre(final String usuarioAsignadoNombre) {
        this.usuarioAsignadoNombre = usuarioAsignadoNombre;
    }

    /**
     * @return the codigoInstitucion
     */
    public String getCodigoInstitucion() {
        return codigoInstitucion;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * @param codigoInstitucion the codigoInstitucion to set
     */
    public void setCodigoInstitucion(final String codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    /**
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    /**
     * @return the institucionCoreId
     */
    public Long getInstitucionCoreId() {
        return institucionCoreId;
    }

    /**
     * @param institucionCoreId the institucionCoreId to set
     */
    public void setInstitucionCoreId(final Long institucionCoreId) {
        this.institucionCoreId = institucionCoreId;
    }
}
