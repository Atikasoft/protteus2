/*
 *  TipoNominaEstadoPuesto.java
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
 *  07/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.ArrayList;
import java.util.List;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "tipos_nominas_estados_puestos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoNominaEstadoPuesto.BUSCAR_VIGENTES,
    query = "SELECT a FROM TipoNominaEstadoPuesto a where a.vigente=true"),
    @NamedQuery(name = TipoNominaEstadoPuesto.BUSCAR_POR_ESTADO_PUESTO,
    query = "SELECT a FROM TipoNominaEstadoPuesto a where a.vigente=true and a.tipoNominaId=?1")
})
public class TipoNominaEstadoPuesto extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "TipoNominaEstadoPuesto.buscarVigentes";

    /**
     * buscar por estado puesto id.
     */
    public static final String BUSCAR_POR_ESTADO_PUESTO = "TipoNominaEstadoPuesto.buscarEstadoPuesto";

    /**
     * Constructor por defecto.
     */
    public TipoNominaEstadoPuesto() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "tipo_nomina_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoNomina tipoNomina;

    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;

    @JoinColumn(name = "estado_puesto_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPuesto estadoPuesto;

    @Column(name = "estado_puesto_id")
    private Long estadoPuestoId;

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
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the tipoNomina
     */
    public TipoNomina getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(final TipoNomina tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the tipoNominaId
     */
    public Long getTipoNominaId() {
        return tipoNominaId;
    }

    /**
     * @param tipoNominaId the tipoNominaId to set
     */
    public void setTipoNominaId(final Long tipoNominaId) {
        this.tipoNominaId = tipoNominaId;
    }

    /**
     * @return the estadoPuesto
     */
    public EstadoPuesto getEstadoPuesto() {
        return estadoPuesto;
    }

    /**
     * @param estadoPuesto the estadoPuesto to set
     */
    public void setEstadoPuesto(final EstadoPuesto estadoPuesto) {
        this.estadoPuesto = estadoPuesto;
    }

    /**
     * @return the estadoPuestoId
     */
    public Long getEstadoPuestoId() {
        return estadoPuestoId;
    }

    /**
     * @param estadoPuestoId the estadoPuestoId to set
     */
    public void setEstadoPuestoId(final Long estadoPuestoId) {
        this.estadoPuestoId = estadoPuestoId;
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
}
