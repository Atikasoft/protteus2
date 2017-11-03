/*
 *  EstadoPersonal.java
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
 *  30/09/2013
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "estados_personal", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EstadoPersonal.BUSCAR_POR_NOMBRE, query =
    "SELECT c FROM EstadoPersonal c where c.nombre like ?1 and c.vigente=true"),
    @NamedQuery(name = EstadoPersonal.BUSCAR_VIGENTES,
    query = "SELECT a FROM EstadoPersonal a where a.vigente=true"),
    @NamedQuery(name = EstadoPersonal.BUSCAR_POR_NEMONICO, query =
    "SELECT a FROM EstadoPersonal a where a.codigo=?1 AND a.vigente=true"),
    @NamedQuery(name = EstadoPersonal.BUSCAR_PREDETERMINADO, query =
    "SELECT a FROM EstadoPersonal a where a.vigente=true AND a.predeterminado=true")
})
public class EstadoPersonal extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NOMBRE = "EstadoPersonal.buscarPorNombre";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "EstadoPersonal.buscarVigente";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "EstadoPersonal.buscarporNemonico ";

    /**
     *
     */
    public static final String BUSCAR_PREDETERMINADO = "EstadoPersonal.buscarPredeterminado";

    /**
     * Constructor por defecto.
     */
    public EstadoPersonal() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public EstadoPersonal(final Long id) {
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

    @Column(name = "usa_nomina")
    private boolean usaNomina;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "predeterminado")
    private boolean predeterminado;

    @Transient
    private Boolean seleccionado;

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
     * @return the usaNomina
     */
    public boolean isUsaNomina() {
        return usaNomina;
    }

    /**
     * @param usaNomina the usaNomina to set
     */
    public void setUsaNomina(boolean usaNomina) {
        this.usaNomina = usaNomina;
    }

    /**
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
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
    public void setSeleccionado(final Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * @return the predeterminado
     */
    public boolean isPredeterminado() {
        return predeterminado;
    }

    /**
     * @param predeterminado the predeterminado to set
     */
    public void setPredeterminado(final boolean predeterminado) {
        this.predeterminado = predeterminado;
    }
}
