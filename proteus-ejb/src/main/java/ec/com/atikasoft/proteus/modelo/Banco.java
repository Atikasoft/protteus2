/*
 *  Banco.java
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "bancos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Banco.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM Banco a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Banco.BUSCAR_VIGENTES,
            query = "SELECT a FROM Banco a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = Banco.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM Banco a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class Banco extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Banco.buscarporNombre";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "Banco.buscarporCodigo";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Banco.buscarVigente";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo codigo.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10, message = "{org.hibernate.validator.constraints.Length.message.codigo}")
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100, message = "{org.hibernate.validator.constraints.Length.message.nombre}")
    @Column(name = "nombre")
    private String nombre;

    /**
     *
     */
    @Column(name = "clave")
    private String clave;

    /**
     * Identificacion del pais.
     */
    @Column(name = "pais")
    private String pais;

    /**
     * Constructor.
     */
    public Banco() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Banco(final Long id) {
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
    public void setId(final Long id) {
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
    public void setCodigo(final String codigo) {
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
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(final String pais) {
        this.pais = pais;
    }
    
    
    /**
     * @return the pais
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave
     */
    public void setClave(final String clave) {
        this.clave = clave;
    }
}
