/*
 *  DatoAdicional.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "datos_adicionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DatoAdicional.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM DatoAdicional a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = DatoAdicional.BUSCAR_VIGENTES,
            query = "SELECT a FROM DatoAdicional a where a.vigente=true order by a.nombre "),
    @NamedQuery(name = DatoAdicional.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM DatoAdicional a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class DatoAdicional extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "DatoAdicional.buscarporNombre ";

    /**
     * Variable parabusqeda por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "DatoAdicional.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DatoAdicional.buscarVigente";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo código.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Basic(optional = false)
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo tipo.
     */
    private String tipo;

    /**
     * Campo tipoDato.
     */
    @Column(name = "tipo_dato")
    private String tipoDato;

    /**
     *
     */
    @Column(name = "unidad")
    private String unidad;

    /**
     *
     */
    @Column(name = "explicacion")
    private String explicacion;
    /**
     *
     */
    @Column(name = "valor_minimo_numerico")
    private BigDecimal valorMinimoNumerico;

    /**
     *
     */
    @Column(name = "valor_maximo_numerico")
    private BigDecimal valorMaximoNumerico;

    /**
     *
     */
    @Column(name = "valor_minimo_texto")
    private String valorMinimoTexto;

    /**
     *
     */
    @Column(name = "valor_maximo_texto")
    private String valorMaximoTexto;
    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "valor_minimo_fecha")
    private Date valorMinimoFecha;

    /**
     *
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "valor_maximo_fecha")
    private Date valorMaximoFecha;
    /**
     *
     */
    @Column(name = "acepta_duplicado")
    private Boolean aceptaDuplicado;

    /**
     *
     */
    @Column(name = "descentralizado")
    private Boolean descentralizado;

    /**
     * Constructor.
     */
    public DatoAdicional() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DatoAdicional(Long id) {
        super();
        this.id = id;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the tipoDato
     */
    public String getTipoDato() {
        return tipoDato;
    }

    /**
     * @param tipoDato the tipoDato to set
     */
    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the unidad
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * @param unidad the unidad to set
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * @return the aceptaDuplicado
     */
    public Boolean getAceptaDuplicado() {
        return aceptaDuplicado;
    }

    /**
     * @param aceptaDuplicado the aceptaDuplicado to set
     */
    public void setAceptaDuplicado(Boolean aceptaDuplicado) {
        this.aceptaDuplicado = aceptaDuplicado;
    }

    /**
     * @return the descentralizado
     */
    public Boolean getDescentralizado() {
        return descentralizado;
    }

    /**
     * @param descentralizado the descentralizado to set
     */
    public void setDescentralizado(Boolean descentralizado) {
        this.descentralizado = descentralizado;
    }

    /**
     * @return the explicacion
     */
    public String getExplicacion() {
        return explicacion;
    }

    /**
     * @param explicacion the explicacion to set
     */
    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    /**
     * @return the valorMinimoNumerico
     */
    public BigDecimal getValorMinimoNumerico() {
        return valorMinimoNumerico;
    }

    /**
     * @param valorMinimoNumerico the valorMinimoNumerico to set
     */
    public void setValorMinimoNumerico(BigDecimal valorMinimoNumerico) {
        this.valorMinimoNumerico = valorMinimoNumerico;
    }

    /**
     * @return the valorMaximoNumerico
     */
    public BigDecimal getValorMaximoNumerico() {
        return valorMaximoNumerico;
    }

    /**
     * @param valorMaximoNumerico the valorMaximoNumerico to set
     */
    public void setValorMaximoNumerico(BigDecimal valorMaximoNumerico) {
        this.valorMaximoNumerico = valorMaximoNumerico;
    }

    /**
     * @return the valorMinimoTexto
     */
    public String getValorMinimoTexto() {
        return valorMinimoTexto;
    }

    /**
     * @param valorMinimoTexto the valorMinimoTexto to set
     */
    public void setValorMinimoTexto(String valorMinimoTexto) {
        this.valorMinimoTexto = valorMinimoTexto;
    }

    /**
     * @return the valorMaximoTexto
     */
    public String getValorMaximoTexto() {
        return valorMaximoTexto;
    }

    /**
     * @param valorMaximoTexto the valorMaximoTexto to set
     */
    public void setValorMaximoTexto(String valorMaximoTexto) {
        this.valorMaximoTexto = valorMaximoTexto;
    }

    /**
     * @return the valorMinimoFecha
     */
    public Date getValorMinimoFecha() {
        return valorMinimoFecha;
    }

    /**
     * @param valorMinimoFecha the valorMinimoFecha to set
     */
    public void setValorMinimoFecha(Date valorMinimoFecha) {
        this.valorMinimoFecha = valorMinimoFecha;
    }

    /**
     * @return the valorMaximoFecha
     */
    public Date getValorMaximoFecha() {
        return valorMaximoFecha;
    }

    /**
     * @param valorMaximoFecha the valorMaximoFecha to set
     */
    public void setValorMaximoFecha(Date valorMaximoFecha) {
        this.valorMaximoFecha = valorMaximoFecha;
    }

}
