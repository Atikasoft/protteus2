/*
 *  Detalle.java
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
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Detalle de la instancia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.detalles")
@NamedQueries({@NamedQuery(name = Detalle.BUSCAR_POR_INSTANCIA_EXTERNA, query =
    "SELECT o FROM Detalle o WHERE o.vigente=true AND o.instancia.identificadorExterno=?1 "
    + " ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = Detalle.BUSCAR_POR_INSTANCIA_EXTERNA_Y_ESTADO, query =
    "SELECT o FROM Detalle o WHERE o.vigente=true AND o.instancia.identificadorExterno=?1 "
    + "AND o.estado.id=?2 ORDER BY o.fechaCreacion DESC")})
public class Detalle extends EntidadBasica {

    /**
     * Nombre de la consulta que recupera el historico una instancia.
     */
    public static final String BUSCAR_POR_INSTANCIA_EXTERNA = "Detalle.buscarPorInstanciaExterna";

    /**
     * Nombre de la consulta que recupera el historico de una instancia en un estado especifico.
     */
    public static final String BUSCAR_POR_INSTANCIA_EXTERNA_Y_ESTADO = "Detalle.buscarPorInstancia";

    /**
     * intificador unico del sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificacion del usuario.
     */
    @Column(name = "usuario")
    private String usuario;

    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    /**
     * Comentario realizado en la transicion.
     */
    @Column(name = "comentario")
    private String comentario;

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
     * Referencia de la instancia.
     */
    @JoinColumn(name = "instancias_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Instancia instancia;

    /**
     * Referencia del estado.
     */
    @JoinColumn(name = "estados_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fase estado;

    /**
     * Constructor.
     */
    public Detalle() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @return the instancia
     */
    public Instancia getInstancia() {
        return instancia;
    }

    /**
     * @return the estado
     */
    public Fase getEstado() {
        return estado;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(final String comentario) {
        this.comentario = comentario;
    }

    /**
     * @param instancia the instancia to set
     */
    public void setInstancia(final Instancia instancia) {
        this.instancia = instancia;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final Fase estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
