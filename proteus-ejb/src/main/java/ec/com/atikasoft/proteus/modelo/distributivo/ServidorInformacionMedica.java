/*
 *  TipoNomina.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo.distributivo;

import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Informacion medica del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_informacion_medica", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorInformacionMedica.BUSCAR_VIGENTES, query
            = "SELECT c FROM ServidorInformacionMedica c where c.vigente=true"),
    @NamedQuery(name = ServidorInformacionMedica.BUSCAR_POR_SERVIDOR_ID, query
            = "SELECT c FROM ServidorInformacionMedica c where  c.servidor.id=?1  and c.vigente=true")
})
public class ServidorInformacionMedica extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_VIGENTES = "ServidorInformacionMedica.buscarVigentes";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "ServidorInformacionMedica.buscarServidorId";

    /**
     * Constructor por defecto.
     */
    public ServidorInformacionMedica() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sufre_enfermedad_catastrofica")
    private Boolean sufreEnfermedadCatastrofica;

    @Column(name = "cuenta_certificado_medico_iess")
    private Boolean cuentaCertificadoMedicoIess;

    @Column(name = "cuida_persona_con_discapacidad_severa")
    private Boolean cuidaPersonaConDiscapacidadSevera;

    @Column(name = "cuida_persona_con_enfermedad_catastrofica")
    private Boolean cuidaPersonaConEnfermedadCatastrofica;

    @Column(name = "cuenta_certificado_medico_con_enfermedad_catastrofica")
    private Boolean cuentaCertificadoMedicoConEnfermedadCatastrofica;

    @Column(name = "nombres_persona_con_discapacidad")
    private String nombresPersonaConDiscapacidad;

    @Column(name = "cedula_persona_con_discapacidad")
    private String cedulaPersonaConDiscapacidad;

    @Column(name = "numero_carnet_conadis")
    private String numeroCarnetConadis;

    @Column(name = "porcentaje_discapacidad")
    private BigDecimal porcentaje_discapacidad;

    @Column(name = "nombres_persona_con_enfermedad_catastrofica")
    private String nombresPersonaConEnfermedadCatastrofica;

    @Column(name = "cedula_persona_con_enfermedad_catastrofica")
    private String cedulaPersonaConEnfermedadCatastrofica;

    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @OneToOne
    private Servidor servidor;
    /**
     * Id de tipo de servidorId.
     */
    @Column(name = "servidor_id")
    private Long servidorId;
    /**
     * Referencia con catalogo de enfermedades.
     */
    @JoinColumn(name = "catalogo_enfermedades_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoEnfermedades;
    /**
     * Id de tipo de catalogoEnfermedadesId.
     */
    @Column(name = "catalogo_enfermedades_id")
    private Long catalogoEnfermedadesId;
    /**
     * Referencia con catalogo de parentezco.
     */
    @JoinColumn(name = "catalogo_parentezco_con_discapacidad_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoParentezcoConDiscapacidad;
    /**
     * Id de tipo de catalogoEnfermedadesId.
     */
    @Column(name = "catalogo_parentezco_con_discapacidad_id")
    private Long catalogoParentezcoConDiscapacidadId;
    /**
     * Referencia con catalogo de discapacidades.
     */
    @JoinColumn(name = "catalogo_discapacidades_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoDiscapacidades;
    /**
     * Id de tipo de catalogoEnfermedadesId.
     */
    @Column(name = "catalogo_discapacidades_id")
    private Long catalogoDiscapacidadesId;
    /**
     * Referencia con catalogo de enfermedades catastroficas..
     */
    @JoinColumn(name = "catalogo_enfermedades_catastroficas_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoEnfermedadesCatastroficas;
    /**
     * Id de tipo de catalogoEnfermedadesId.
     */
    @Column(name = "catalogo_enfermedades_catastroficas_id")
    private Long catalogoEnfermedadesCatastroficasId;
    /**
     * Referencia con catalogo de parentezco por enfermedades catastroficas..
     */
    @JoinColumn(name = "catalogo_parentezco_enfermedad_catastrofica_id", insertable = false, updatable = false)
    @ManyToOne
    private Catalogo catalogoParentezcoEnfermedadCatastrofica;
    /**
     * Id de tipo de catalogoEnfermedadesId.
     */
    @Column(name = "catalogo_parentezco_enfermedad_catastrofica_id")
    private Long catalogoParentezcoEnfermedadCatastroficaId;

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
     * @return the sufreEnfermedadCatastrofica
     */
    public Boolean getSufreEnfermedadCatastrofica() {
        return sufreEnfermedadCatastrofica;
    }

    /**
     * @return the cuentaCertificadoMedicoIess
     */
    public Boolean getCuentaCertificadoMedicoIess() {
        return cuentaCertificadoMedicoIess;
    }

    /**
     * @return the cuidaPersonaConDiscapacidadSevera
     */
    public Boolean getCuidaPersonaConDiscapacidadSevera() {
        return cuidaPersonaConDiscapacidadSevera;
    }

    /**
     * @return the cuidaPersonaConEnfermedadCatastrofica
     */
    public Boolean getCuidaPersonaConEnfermedadCatastrofica() {
        return cuidaPersonaConEnfermedadCatastrofica;
    }

    /**
     * @return the cuentaCertificadoMedicoConEnfermedadCatastrofica
     */
    public Boolean getCuentaCertificadoMedicoConEnfermedadCatastrofica() {
        return cuentaCertificadoMedicoConEnfermedadCatastrofica;
    }

    /**
     * @return the nombresPersonaConDiscapacidad
     */
    public String getNombresPersonaConDiscapacidad() {
        return nombresPersonaConDiscapacidad;
    }

    /**
     * @return the cedulaPersonaConDiscapacidad
     */
    public String getCedulaPersonaConDiscapacidad() {
        return cedulaPersonaConDiscapacidad;
    }

    /**
     * @return the numeroCarnetConadis
     */
    public String getNumeroCarnetConadis() {
        return numeroCarnetConadis;
    }

    /**
     * @return the porcentaje_discapacidad
     */
    public BigDecimal getPorcentaje_discapacidad() {
        return porcentaje_discapacidad;
    }

    /**
     * @return the nombresPersonaConEnfermedadCatastrofica
     */
    public String getNombresPersonaConEnfermedadCatastrofica() {
        return nombresPersonaConEnfermedadCatastrofica;
    }

    /**
     * @return the cedulaPersonaConEnfermedadCatastrofica
     */
    public String getCedulaPersonaConEnfermedadCatastrofica() {
        return cedulaPersonaConEnfermedadCatastrofica;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @return the catalogoEnfermedades
     */
    public Catalogo getCatalogoEnfermedades() {
        return catalogoEnfermedades;
    }

    /**
     * @return the catalogoParentezcoConDiscapacidad
     */
    public Catalogo getCatalogoParentezcoConDiscapacidad() {
        return catalogoParentezcoConDiscapacidad;
    }

    /**
     * @return the catalogoDiscapacidades
     */
    public Catalogo getCatalogoDiscapacidades() {
        return catalogoDiscapacidades;
    }

    /**
     * @return the catalogoEnfermedadesCatastroficas
     */
    public Catalogo getCatalogoEnfermedadesCatastroficas() {
        return catalogoEnfermedadesCatastroficas;
    }

    /**
     * @return the catalogoParentezcoEnfermedadCatastrofica
     */
    public Catalogo getCatalogoParentezcoEnfermedadCatastrofica() {
        return catalogoParentezcoEnfermedadCatastrofica;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param sufreEnfermedadCatastrofica the sufreEnfermedadCatastrofica to set
     */
    public void setSufreEnfermedadCatastrofica(final Boolean sufreEnfermedadCatastrofica) {
        this.sufreEnfermedadCatastrofica = sufreEnfermedadCatastrofica;
    }

    /**
     * @param cuentaCertificadoMedicoIess the cuentaCertificadoMedicoIess to set
     */
    public void setCuentaCertificadoMedicoIess(final Boolean cuentaCertificadoMedicoIess) {
        this.cuentaCertificadoMedicoIess = cuentaCertificadoMedicoIess;
    }

    /**
     * @param cuidaPersonaConDiscapacidadSevera the
     * cuidaPersonaConDiscapacidadSevera to set
     */
    public void setCuidaPersonaConDiscapacidadSevera(final Boolean cuidaPersonaConDiscapacidadSevera) {
        this.cuidaPersonaConDiscapacidadSevera = cuidaPersonaConDiscapacidadSevera;
    }

    /**
     * @param cuidaPersonaConEnfermedadCatastrofica the
     * cuidaPersonaConEnfermedadCatastrofica to set
     */
    public void setCuidaPersonaConEnfermedadCatastrofica(final Boolean cuidaPersonaConEnfermedadCatastrofica) {
        this.cuidaPersonaConEnfermedadCatastrofica = cuidaPersonaConEnfermedadCatastrofica;
    }

    /**
     * @param cuentaCertificadoMedicoConEnfermedadCatastrofica the
     * cuentaCertificadoMedicoConEnfermedadCatastrofica to set
     */
    public void setCuentaCertificadoMedicoConEnfermedadCatastrofica(
            final Boolean cuentaCertificadoMedicoConEnfermedadCatastrofica) {
        this.cuentaCertificadoMedicoConEnfermedadCatastrofica = cuentaCertificadoMedicoConEnfermedadCatastrofica;
    }

    /**
     * @param nombresPersonaConDiscapacidad the nombresPersonaConDiscapacidad to
     * set
     */
    public void setNombresPersonaConDiscapacidad(final String nombresPersonaConDiscapacidad) {
        this.nombresPersonaConDiscapacidad = nombresPersonaConDiscapacidad;
    }

    /**
     * @param cedulaPersonaConDiscapacidad the cedulaPersonaConDiscapacidad to
     * set
     */
    public void setCedulaPersonaConDiscapacidad(final String cedulaPersonaConDiscapacidad) {
        this.cedulaPersonaConDiscapacidad = cedulaPersonaConDiscapacidad;
    }

    /**
     * @param numeroCarnetConadis the numeroCarnetConadis to set
     */
    public void setNumeroCarnetConadis(final String numeroCarnetConadis) {
        this.numeroCarnetConadis = numeroCarnetConadis;
    }

    /**
     * @param porcentaje_discapacidad the porcentaje_discapacidad to set
     */
    public void setPorcentaje_discapacidad(final BigDecimal porcentaje_discapacidad) {
        this.porcentaje_discapacidad = porcentaje_discapacidad;
    }

    /**
     * @param nombresPersonaConEnfermedadCatastrofica the
     * nombresPersonaConEnfermedadCatastrofica to set
     */
    public void setNombresPersonaConEnfermedadCatastrofica(final String nombresPersonaConEnfermedadCatastrofica) {
        this.nombresPersonaConEnfermedadCatastrofica = nombresPersonaConEnfermedadCatastrofica;
    }

    /**
     * @param cedulaPersonaConEnfermedadCatastrofica the
     * cedulaPersonaConEnfermedadCatastrofica to set
     */
    public void setCedulaPersonaConEnfermedadCatastrofica(final String cedulaPersonaConEnfermedadCatastrofica) {
        this.cedulaPersonaConEnfermedadCatastrofica = cedulaPersonaConEnfermedadCatastrofica;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @param catalogoEnfermedades the catalogoEnfermedades to set
     */
    public void setCatalogoEnfermedades(final Catalogo catalogoEnfermedades) {
        this.catalogoEnfermedades = catalogoEnfermedades;
    }

    /**
     * @param catalogoParentezcoConDiscapacidad the
     * catalogoParentezcoConDiscapacidad to set
     */
    public void setCatalogoParentezcoConDiscapacidad(final Catalogo catalogoParentezcoConDiscapacidad) {
        this.catalogoParentezcoConDiscapacidad = catalogoParentezcoConDiscapacidad;
    }

    /**
     * @param catalogoDiscapacidades the catalogoDiscapacidades to set
     */
    public void setCatalogoDiscapacidades(final Catalogo catalogoDiscapacidades) {
        this.catalogoDiscapacidades = catalogoDiscapacidades;
    }

    /**
     * @param catalogoEnfermedadesCatastroficas the
     * catalogoEnfermedadesCatastroficas to set
     */
    public void setCatalogoEnfermedadesCatastroficas(final Catalogo catalogoEnfermedadesCatastroficas) {
        this.catalogoEnfermedadesCatastroficas = catalogoEnfermedadesCatastroficas;
    }

    /**
     * @param catalogoParentezcoEnfermedadCatastrofica the
     * catalogoParentezcoEnfermedadCatastrofica to set
     */
    public void setCatalogoParentezcoEnfermedadCatastrofica(final Catalogo catalogoParentezcoEnfermedadCatastrofica) {
        this.catalogoParentezcoEnfermedadCatastrofica = catalogoParentezcoEnfermedadCatastrofica;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the catalogoEnfermedadesId
     */
    public Long getCatalogoEnfermedadesId() {
        return catalogoEnfermedadesId;
    }

    /**
     * @param catalogoEnfermedadesId the catalogoEnfermedadesId to set
     */
    public void setCatalogoEnfermedadesId(Long catalogoEnfermedadesId) {
        this.catalogoEnfermedadesId = catalogoEnfermedadesId;
    }

    /**
     * @return the catalogoParentezcoConDiscapacidadId
     */
    public Long getCatalogoParentezcoConDiscapacidadId() {
        return catalogoParentezcoConDiscapacidadId;
    }

    /**
     * @param catalogoParentezcoConDiscapacidadId the
     * catalogoParentezcoConDiscapacidadId to set
     */
    public void setCatalogoParentezcoConDiscapacidadId(Long catalogoParentezcoConDiscapacidadId) {
        this.catalogoParentezcoConDiscapacidadId = catalogoParentezcoConDiscapacidadId;
    }

    /**
     * @return the catalogoDiscapacidadesId
     */
    public Long getCatalogoDiscapacidadesId() {
        return catalogoDiscapacidadesId;
    }

    /**
     * @param catalogoDiscapacidadesId the catalogoDiscapacidadesId to set
     */
    public void setCatalogoDiscapacidadesId(Long catalogoDiscapacidadesId) {
        this.catalogoDiscapacidadesId = catalogoDiscapacidadesId;
    }

    /**
     * @return the catalogoEnfermedadesCatastroficasId
     */
    public Long getCatalogoEnfermedadesCatastroficasId() {
        return catalogoEnfermedadesCatastroficasId;
    }

    /**
     * @param catalogoEnfermedadesCatastroficasId the
     * catalogoEnfermedadesCatastroficasId to set
     */
    public void setCatalogoEnfermedadesCatastroficasId(Long catalogoEnfermedadesCatastroficasId) {
        this.catalogoEnfermedadesCatastroficasId = catalogoEnfermedadesCatastroficasId;
    }

    /**
     * @return the catalogoParentezcoEnfermedadCatastroficaId
     */
    public Long getCatalogoParentezcoEnfermedadCatastroficaId() {
        return catalogoParentezcoEnfermedadCatastroficaId;
    }

    /**
     * @param catalogoParentezcoEnfermedadCatastroficaId the
     * catalogoParentezcoEnfermedadCatastroficaId to set
     */
    public void setCatalogoParentezcoEnfermedadCatastroficaId(Long catalogoParentezcoEnfermedadCatastroficaId) {
        this.catalogoParentezcoEnfermedadCatastroficaId = catalogoParentezcoEnfermedadCatastroficaId;
    }
}
