/*
 *  Constante.java
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "constantes", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Constante.BUSCAR_POR_NOMBRE,
    query = "SELECT a FROM Constante a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Constante.BUSCAR_VIGENTES,
    query = "SELECT a FROM Constante a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Constante.BUSCAR_POR_CODIGO,
    query = "SELECT a FROM Constante a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class Constante extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Constante.buscarporNombre ";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "Constante.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Constante.buscarVigente";

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
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo tipo.
     */
    @Column(name = "tipo")
    private String tipo;

    /**
     * Campo valorTexto.
     */
    @Column(name = "valor_texto")
    private String valorTexto;

    /**
     * Campo valorNumerico.
     */
    @Column(name = "valor_numerico")
    private BigDecimal valorNumerico;

    /**
     * Campo tipo.
     */
    @Column(name = "valor_fecha")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date valorFecha;

    /**
     * Constructor.
     */
    public Constante() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Constante(final Long id) {
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
     * @return the valorTexto
     */
    public String getValorTexto() {
        return valorTexto;
    }

    /**
     * @param valorTexto the valorTexto to set
     */
    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    /**
     * @return the valorNumerico
     */
    public BigDecimal getValorNumerico() {
        return valorNumerico;
    }

    /**
     * @param valorNumerico the valorNumerico to set
     */
    public void setValorNumerico(BigDecimal valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    /**
     * @return the valorFecha
     */
    public Date getValorFecha() {
        return valorFecha;
    }

    /**
     * @param valorFecha the valorFecha to set
     */
    public void setValorFecha(Date valorFecha) {
        this.valorFecha = valorFecha;
    }
}
