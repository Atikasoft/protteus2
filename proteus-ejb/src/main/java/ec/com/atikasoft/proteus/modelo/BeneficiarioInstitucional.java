/*
 *  BeneficiarioInstitucional.java
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
 *  17/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
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
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "beneficiarios_instituciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = BeneficiarioInstitucional.BUSCAR_POR_TIPO_BENEFICIARIO,
    query = "SELECT a FROM BeneficiarioInstitucional a where a.rubro.tipoBeneficiario = ?1 and a.vigente=true "
    + "order by a.idRubro"),
       @NamedQuery(name = BeneficiarioInstitucional.BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO,
    query = "SELECT a FROM BeneficiarioInstitucional a where a.numeroIdentificacion = ?1 and a.vigente=true  "
    + " and a.rubro.id = ?2 order by a.numeroIdentificacion"),
       @NamedQuery(name = BeneficiarioInstitucional.BUSCAR_POR_SERVIDOR_Y_RUBRO,
    query = "SELECT a FROM BeneficiarioInstitucional a where a.servidor.id = ?1 and a.vigente=true  "
    + " and a.rubro.id = ?2 order by a.servidor.apellidos"),
       @NamedQuery(name = BeneficiarioInstitucional.BUSCAR_POR_NUMERO_IDENTIFICACION,
    query = "SELECT a FROM BeneficiarioInstitucional a where a.numeroIdentificacion = ?1 and a.vigente=true "
    + "order by a.numeroIdentificacion"),
    @NamedQuery(name = BeneficiarioInstitucional.BUSCAR_VIGENTES,
    query = "SELECT a FROM BeneficiarioInstitucional a where a.vigente=true order by a.idRubro")
})
public class BeneficiarioInstitucional extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_TIPO_BENEFICIARIO = "BeneficiarioInstitucional.buscarporNombre ";
      /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION = "BeneficiarioInstitucional.buscarPorNUmeroIdentificacion";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "BeneficiarioInstitucional.buscarVigente";
        /**
     * Nombre para busqueda de registros por el beneficiario por identificacion y rubro.
     */
    public static final String BUSCAR_POR_NUMERO_IDENTIFICACION_Y_RUBRO=
            "BeneficiarioInstitucional.buscarPorBeneficiarioPorNumeroIdentificacionYRubro";
            /**
     * Nombre para busqueda de registros por el servidor y rubro.
     */
    public static final String BUSCAR_POR_SERVIDOR_Y_RUBRO=
            "BeneficiarioInstitucional.buscarPorServidorYRubro";

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
     * Referencia a rubro
     */
    @JoinColumn(name = "rubro_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Rubro rubro;

    /**
     * rubro id.
     */
    @Column(name = "rubro_id")
    private Long idRubro;

    /**
     * Referencia a servidor
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * servidor id.
     */
    @Column(name = "servidor_id")
    private Long idServidor;

    /**
     * Referencia a institucion
     */
    @JoinColumn(name = "institucion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Institucion institucion;

    /**
     * institucion id.
     */
    @Column(name = "institucion_id")
    private Long idInstitucion;

    /**
     * Constructor.
     */
    public BeneficiarioInstitucional() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public BeneficiarioInstitucional(final Long id) {
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
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
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
    public void setNumeroIdentificacion(String numeroidentificacion) {
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
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * @return the rubro
     */
    public Rubro getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the idServidor
     */
    public Long getIdServidor() {
        return idServidor;
    }

    /**
     * @param idServidor the idServidor to set
     */
    public void setIdServidor(Long idServidor) {
        this.idServidor = idServidor;
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the idInstitucion
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the idRubro
     */
    public Long getIdRubro() {
        return idRubro;
    }

    /**
     * @param idRubro the idRubro to set
     */
    public void setIdRubro(Long idRubro) {
        this.idRubro = idRubro;
    }
}
