/*
 *  TipoNominaEstadoPersonal.java
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
@Table(name = "tipos_nominas_estados_personal", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoNominaEstadoPersonal.BUSCAR_VIGENTES,
    query = "SELECT a FROM TipoNominaEstadoPersonal a where a.vigente=true"),
    @NamedQuery(name = TipoNominaEstadoPersonal.BUSCAR_POR_ESTADO_PERSONAL,
    query = "SELECT a FROM TipoNominaEstadoPersonal a where a.vigente=true and a.tipoNominaId=?1")
})
public class TipoNominaEstadoPersonal extends EntidadBasica {

    /**
     * Constructor por defecto.
     */
    public TipoNominaEstadoPersonal() {
        super();
    }

    /**
     * Nombre de la consulta para buscar por nombre una clase.
     */
    public static final String BUSCAR_VIGENTES = "TipoNominaEstadoPersonal.buscarVigentes";

    /**
     * buscar por estado puesto id.
     */
    public static final String BUSCAR_POR_ESTADO_PERSONAL = "TipoNominaEstadoPersonal.buscarEstadoPersonal";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "tipo_nomina_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoNomina tipoNomina;

    @Column(name = "tipo_nomina_id")
    private Long tipoNominaId;

    @JoinColumn(name = "estado_personal_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPersonal estadoPersonal;

    @Column(name = "estado_personal_id")
    private Long estadoPersonalId;

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
     * @return the estadoPersonal
     */
    public EstadoPersonal getEstadoPersonal() {
        return estadoPersonal;
    }

    /**
     * @param estadoPersonal the estadoPersonal to set
     */
    public void setEstadoPersonal(final EstadoPersonal estadoPersonal) {
        this.estadoPersonal = estadoPersonal;
    }

    /**
     * @return the estadoPersonalId
     */
    public Long getEstadoPersonalId() {
        return estadoPersonalId;
    }

    /**
     * @param estadoPersonalId the estadoPersonalId to set
     */
    public void setEstadoPersonalId(final Long estadoPersonalId) {
        this.estadoPersonalId = estadoPersonalId;
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
