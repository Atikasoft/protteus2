/*
 *  EstadoPuesto.java
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
 *  26/09/2013
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
@Table(name = "estados_puestos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = EstadoPuesto.BUSCAR_POR_NOMBRE, query =
    "SELECT c FROM EstadoPuesto c where c.nombre like ?1"),
    @NamedQuery(name = EstadoPuesto.BUSCAR_VIGENTES,
    query = "SELECT a FROM EstadoPuesto a where a.vigente=true"),
    @NamedQuery(name = EstadoPuesto.BUSCAR_POR_NEMONICO, query =
    "SELECT a FROM EstadoPuesto a where a.codigo=?1"),
    @NamedQuery(name = EstadoPuesto.BUSCAR_POR_CODIGO, query =
    "SELECT a FROM EstadoPuesto a where a.codigo = ?1 and a.vigente = true "),
    @NamedQuery(name = EstadoPuesto.BUSCAR_PREDETERMINADO, query =
    "SELECT a FROM EstadoPuesto a where a.vigente=true AND a.predeterminado=true"),
    @NamedQuery(name = EstadoPuesto.REMOVER_PREDETERMINADO, query =
    "UPDATE EstadoPuesto o set o.predeterminado = false where o.predeterminado = true and o.vigente = true")
})
public class EstadoPuesto extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre un estado.
     */
    public static final String BUSCAR_POR_NOMBRE = "EstadoPuesto.buscarPorNombre";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "EstadoPuesto.buscarVigente";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "EstadoPuesto.buscarporNemonico ";
    
    /**
     * BUSCAR UN EstadoPuesto segun su codigo Unico
     */
    public static final String BUSCAR_POR_CODIGO = "EstadoPuesto.buscarPorCodigo";

    /**
     *
     */
    public static final String BUSCAR_PREDETERMINADO = "EstadoPuesto.buscarPredeterminado";
    
    /**
     * 
     */
    public static final String REMOVER_PREDETERMINADO = "EstadoPuesto.removerPredeterminado";
    
    @Transient
    private Boolean seleccionado;

    /**
     * Constructor por defecto.
     */
    public EstadoPuesto() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public EstadoPuesto(final Long id) {
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

    @Column(name = "puede_ocuparse")
    private boolean puedeOcuparse;

    @Column(name = "ocupado")
    private boolean ocupado;

    @Column(name = "predeterminado")
    private boolean predeterminado;

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
     * @return the usaNomina
     */
    public Boolean getUsaNomina() {
        return usaNomina;
    }

    /**
     * @param usaNomina the usaNomina to set
     */
    public void setUsaNomina(final Boolean usaNomina) {
        this.usaNomina = usaNomina;
    }

    /**
     * @return the puedeOcuparse
     */
    public Boolean getPuedeOcuparse() {
        return puedeOcuparse;
    }

    /**
     * @param puedeOcuparse the puedeOcuparse to set
     */
    public void setPuedeOcuparse(final Boolean puedeOcuparse) {
        this.puedeOcuparse = puedeOcuparse;
    }

    /**
     * @return the ocupado
     */
    public Boolean getOcupado() {
        return ocupado;
    }

    /**
     * @param ocupado the ocupado to set
     */
    public void setOcupado(final Boolean ocupado) {
        this.ocupado = ocupado;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
