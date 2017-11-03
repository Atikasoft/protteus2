/*
 *  Tercero.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "pagos_terceros", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Tercero.BUSCAR_VIGENTES,
            query = "SELECT a FROM Tercero a where a.vigente=true order by a.fechaCreacion desc, a.nombres"),
    @NamedQuery(name = Tercero.BUSCAR_POR_ESTADO_INST_EJERCICIO,
            query = "SELECT a FROM Tercero a where a.vigente=true and a.institucionEjercicioFiscal.id=?1 and a.estado = ?2  order by a.fechaCreacion desc, a.nombres"),
    @NamedQuery(name = Tercero.BUSCAR_POR_IDENTIFICACION,
            query = "SELECT a FROM Tercero a where a.vigente=true and a.tipoIdentificacion = ?1 and a.numeroIdentificacion=?2 order by a.fechaCreacion desc, a.nombres")
})
public class Tercero extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Tercero.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "Tercero.buscarPorIdentificacion";
    /**
     * Nombre para busqueda de vigentes por estado.
     */
    public static final String BUSCAR_POR_ESTADO_INST_EJERCICIO = "Tercero.buscarPorEstadoInstitucionEjercicioFiscal";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo del tipo de identificación.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     * Especifica numero identificacion de terceros.
     */
    @Column(name = "numero_identificacion")
    @NotNull
    private String numeroIdentificacion;

    /**
     * Referencia a institucion_ejercicio_fiscal_id .
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Referencia a archivo de respaldo.
     */
    @JoinColumn(name = "archivo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     * Nombres del beneficiario.
     */
    @NotNull
    @Column(name = "nombres")
    private String nombres;

    /**
     * descripcion del beneficiario.
     */
//    @NotNull
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo estado: <A>Activo, <I>Inactivo
     * Pagado.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private String estado;
    /**
     * Registra el valor del tercero.
     */
    @Column(name = "valor")
//    @NotNull
    private BigDecimal valor;
    /**
     * Variable para seleccionar un pago a tercero
     */
    @Transient
    private Boolean seleccionado;

    /**
     * Constructor.
     */
    public Tercero() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Tercero(final Long id) {
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
     * @return the institucionEjercicioFiscal
     */
    public InstitucionEjercicioFiscal getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    /**
     * @param institucionEjercicioFiscal the institucionEjercicioFiscal to set
     */
    public void setInstitucionEjercicioFiscal(InstitucionEjercicioFiscal institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }
    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the seleccionado
     */
    public Boolean getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
