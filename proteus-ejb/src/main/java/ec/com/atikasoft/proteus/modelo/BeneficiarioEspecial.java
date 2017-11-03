/*
 *  BeneficiarioEspecial.java
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
 *  18/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "beneficiarios_especiales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = BeneficiarioEspecial.BUSCAR_VIGENTES,
            query = "SELECT a FROM BeneficiarioEspecial a where a.vigente=true order by a.nombreBeneficiario"),
    @NamedQuery(name = BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_POR_NUMERO_IDENTIFICACION,
            query
            = "SELECT a FROM BeneficiarioEspecial a where a.vigente=true and a.numeroIdentificacion= ?1 order by a.nombreBeneficiario"),
    @NamedQuery(name = BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_INSTITUCION,
            query
            = "SELECT a FROM BeneficiarioEspecial a where a.vigente=true and a.idBeneficiarioInstitucion=?1 "
            + "order by a.nombreBeneficiario"),
    @NamedQuery(name = BeneficiarioEspecial.BUSCAR_POR_BENEFICIARIO_PARA_NOMINA, query
            = "SELECT o FROM BeneficiarioEspecial o WHERE o.vigente=true AND o.beneficiarioInstitucional.vigente=true "
            + " AND o.beneficiarioInstitucional.idRubro=?1 "
            + " AND o.beneficiarioInstitucional.idInstitucion=?2"
            + " AND o.beneficiarioInstitucional.idServidor=?3 "),
    @NamedQuery(name = BeneficiarioEspecial.BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO,
            query = "SELECT a FROM BeneficiarioEspecial a where a.vigente=true and a.numeroIdentificacion= ?1 "
            + " and a.beneficiarioInstitucional.rubro.id=?2 order by a.nombreBeneficiario")
})
public class BeneficiarioEspecial extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "BeneficiarioEspecial.buscarVigente";

    /**
     * Nombre para busqueda de registros por el beneficiario institucional.
     */
    public static final String BUSCAR_POR_BENEFICIARIO_INSTITUCION
            = "BeneficiarioEspecial.buscarPorBeneficiarioInstitucional";

    /**
     * Nombre para busqueda de registros por el beneficiario especiales para
     * nomina.
     */
    public static final String BUSCAR_POR_BENEFICIARIO_PARA_NOMINA
            = "BeneficiarioEspecial.buscarPorBeneficiarioParaNomina";

    /**
     * Nombre para busqueda de registros por el beneficiario especiales para
     * nomina.
     */
    public static final String BUSCAR_POR_BENEFICIARIO_POR_NUMERO_IDENTIFICACION
            = "BeneficiarioEspecial.buscarPorBeneficiarioPorNumeroIdentificacion";

    /**
     * Nombre para busqueda de registros por el beneficiario especialpor
     * identificacion y rubro.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO
            = "BeneficiarioEspecial.buscarPorBeneficiarioPorNumeroIdentificacionYRubro";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo tipo_identificacion para el beneficiario Normal.
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     * Campo numero_identificacion para el beneficiario Normal.
     */
    @Size(max = 20)
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     * Campo nombre para el beneficiario Normal.
     */
    @Size(max = 100)
    @Column(name = "nombre_beneficiario")
    private String nombreBeneficiario;

    /**
     * Referencia a beneficiarioInstitucion
     */
    @JoinColumn(name = "beneficiario_institucion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private BeneficiarioInstitucional beneficiarioInstitucional;

    /**
     * beneficiarioInstitucion id.
     */
    @Column(name = "beneficiario_institucion_id")
    private Long idBeneficiarioInstitucion;

    /**
     * Especifica la fecha de inicio del descuento
     */
    @Column(name = "fecha_inicio")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaInicio;

    /**
     * Especifica la fecha de fin del descuento
     */
    @Column(name = "fecha_fin")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date fechaFin;

    /**
     * Registra el valor que se debe descontar
     */
    @Column(name = "valor")
    private BigDecimal valor;

    /**
     * Constructor.
     */
    public BeneficiarioEspecial() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public BeneficiarioEspecial(final Long id) {
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
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the numeroidentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroidentificacion the numeroidentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroidentificacion) {
        this.numeroIdentificacion = numeroidentificacion;
    }

    /**
     * @return the nombreBeneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * @param nombreBeneficiario the nombreBeneficiario to set
     */
    public void setNombreBeneficiario(final String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the beneficiarioInstitucion
     */
    public BeneficiarioInstitucional getBeneficiarioInstitucional() {
        return beneficiarioInstitucional;
    }

    /**
     * @param beneficiarioInstitucion the beneficiarioInstitucion to set
     */
    public void setBeneficiarioInstitucional(final BeneficiarioInstitucional beneficiarioInstitucional) {
        this.beneficiarioInstitucional = beneficiarioInstitucional;
    }

    /**
     * @return the idBeneficiarioInstitucion
     */
    public Long getIdBeneficiarioInstitucion() {
        return idBeneficiarioInstitucion;
    }

    /**
     * @param idBeneficiarioInstitucion the idBeneficiarioInstitucion to set
     */
    public void setIdBeneficiarioInstitucion(final Long idBeneficiarioInstitucion) {
        this.idBeneficiarioInstitucion = idBeneficiarioInstitucion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        
        BeneficiarioEspecial other = ((BeneficiarioEspecial) obj);
        
        if (id != null && other.getId() != null && id.equals(other.getId())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
