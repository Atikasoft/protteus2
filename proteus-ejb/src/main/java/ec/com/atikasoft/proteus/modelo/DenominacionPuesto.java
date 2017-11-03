/*
 *  DenominacionPuesto.java
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
 *  19/09/2013
 *
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
 * DenominacionPuesto
 *
 * @author Alvaro Tituaña <alvaro.tituania@markasoft.ec>
 */
@Entity
@Table(name = "denominaciones_puestos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DenominacionPuesto.BUSCAR_POR_NOMBRE, query =
    "SELECT c FROM DenominacionPuesto c where c.nombre like ?1 and c.vigente=true"),
    @NamedQuery(name = DenominacionPuesto.BUSCAR_VIGENTES,
    query = "SELECT a FROM DenominacionPuesto a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = DenominacionPuesto.BUSCAR_POR_NEMONICO, query =
    "SELECT a FROM DenominacionPuesto a where a.codigo=?1")
})
public class DenominacionPuesto extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una denominacion puesto.
     */
    public static final String BUSCAR_POR_NOMBRE = "DenominacionPuesto.buscarPorNombre";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "DenominacionPuesto.buscarporNemonico ";

    /**
     * Nombre de consulta para buscar por vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DenominacionPuesto.buscarporvigentes ";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Constructor por defecto.
     */
    public DenominacionPuesto() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public DenominacionPuesto(final Long id) {
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
}
