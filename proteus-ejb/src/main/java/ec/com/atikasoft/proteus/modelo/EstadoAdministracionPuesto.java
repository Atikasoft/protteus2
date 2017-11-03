/*
 *  EstadoAdministracionPuesto.java
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
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author hmolina
 */
@Entity
@Table(name = "estados_administracion_puestos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EstadoAdministracionPuesto.BUSCAR_POR_NOMBRE, query
            = "SELECT c FROM EstadoAdministracionPuesto c where c.nombre like ?1 and c.vigente=true"),
    @NamedQuery(name = EstadoAdministracionPuesto.BUSCAR_VIGENTES,
            query = "SELECT a FROM EstadoAdministracionPuesto a where a.vigente=true"),
    @NamedQuery(name = EstadoAdministracionPuesto.BUSCAR_POR_CODIGO, query
            = "SELECT a FROM EstadoAdministracionPuesto a where a.codigo=?1 AND a.vigente=true")
})
@Setter
@Getter
public class EstadoAdministracionPuesto extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NOMBRE = "EstadoAdministracionPuesto.buscarPorNombre";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "EstadoAdministracionPuesto.buscarVigente";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_CODIGO = "EstadoAdministracionPuesto.buscarporCodigo ";

    /**
     * Constructor por defecto.
     */
    public EstadoAdministracionPuesto() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public EstadoAdministracionPuesto(final Long id) {
        super();
        this.id = id;
    }
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

    /**
     *
     */
    @Column(name = "activo")
    private Boolean activo;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
