/*
 *  Variable.java
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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Variable es una palabra que representa a aquello que varía o que está sujeto a algún tipo de cambio, se trata de algo
 * que se caracteriza por ser inestable, inconstante y mudable, en otras palabras, una variable es un símbolo que
 * permite identificar a un elemento no especificado dentro de un determinado grupo, este conjunto suele ser definido
 * como el conjunto universal de la variable (universo de la variable, en otras ocasiones), y cada pieza incluida en él
 * constituye un valor de la variable.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.instancias_variables")
public class InstanciaVariable extends EntidadBasica {

    /**
     * Identificación unica de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador único de negocio.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;

    /**
     * Denominación de la variable.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Identifica el tipo de dato de la variable T: texto N : Valor Númerico F : Fecha.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo")
    private String tipo;

    /**
     * Valor para el tipo texto.
     */
    @Size(max = 100)
    @Column(name = "valor_texto")
    private String valorTexto;

    /**
     * Valor para el tipo númerico.
     */
    @Column(name = "valor_numerico")
    private BigDecimal valorNumerico;

    /**
     * Valor para el tipo fecha.
     */
    @Column(name = "valor_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valorFecha;

    /**
     * Referencia de la instancia.
     */
    @JoinColumn(name = "instancias_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instancia instancia;

    /**
     * Constructor.
     */
    public InstanciaVariable() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the valorTexto
     */
    public String getValorTexto() {
        return valorTexto;
    }

    /**
     * @return the valorNumerico
     */
    public BigDecimal getValorNumerico() {
        return valorNumerico;
    }

    /**
     * @return the valorFecha
     */
    public Date getValorFecha() {
        return valorFecha;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param valorTexto the valorTexto to set
     */
    public void setValorTexto(final String valorTexto) {
        this.valorTexto = valorTexto;
    }

    /**
     * @param valorNumerico the valorNumerico to set
     */
    public void setValorNumerico(final BigDecimal valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    /**
     * @param valorFecha the valorFecha to set
     */
    public void setValorFecha(final Date valorFecha) {
        this.valorFecha = valorFecha;
    }

    /**
     * @return the instancia
     */
    public Instancia getInstancia() {
        return instancia;
    }

    /**
     * @param instancia the instancia to set
     */
    public void setInstancia(final Instancia instancia) {
        this.instancia = instancia;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
