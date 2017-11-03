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

import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Datos adicionales del servidor
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "servidor_datos_adicionales", catalog = "sch_proteus")
public class ServidorDatoAdicional extends EntidadBasica {

    /**
     * Constructor por defecto.
     */
    public ServidorDatoAdicional() {
        super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor_texto")
    private String valorTexto;

    @Temporal(TemporalType.DATE)
    @Column(name = "valor_fecha")
    private Date valorFecha;

    @Column(name = "valor_numerico")
    private BigDecimal valorNumerico;

    @Column(name = "valor_logico")
    private Boolean valorLogico;
    /**
     * Referencia con Servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    @Column(name = "servidor_id")
    private Long servidorId;

    @JoinColumn(name = "dato_adicional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DatoAdicional datoAdicional;

    @Column(name = "dato_adicional_id")
    private Long datoAdicionalId;

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
     * @return the valorTexto
     */
    public String getValorTexto() {
        return valorTexto;
    }

    /**
     * @param valorTexto the valorTexto to set
     */
    public void setValorTexto(String valorTexto) {
        this.valorTexto = valorTexto;
    }

    /**
     * @return the valorFecha
     */
    public Date getValorFecha() {
        return valorFecha;
    }

    /**
     * @param valorFecha the valorFecha to set
     */
    public void setValorFecha(Date valorFecha) {
        this.valorFecha = valorFecha;
    }

    /**
     * @return the valorNumerico
     */
    public BigDecimal getValorNumerico() {
        return valorNumerico;
    }

    /**
     * @param valorNumerico the valorNumerico to set
     */
    public void setValorNumerico(BigDecimal valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    /**
     * @return the valorLogico
     */
    public Boolean getValorLogico() {
        return valorLogico;
    }

    /**
     * @param valorLogico the valorLogico to set
     */
    public void setValorLogico(Boolean valorLogico) {
        this.valorLogico = valorLogico;
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
     * @return the datoAdicional
     */
    public DatoAdicional getDatoAdicional() {
        return datoAdicional;
    }

    /**
     * @param datoAdicional the datoAdicional to set
     */
    public void setDatoAdicional(DatoAdicional datoAdicional) {
        this.datoAdicional = datoAdicional;
    }

    /**
     * @return the datoAdicionalId
     */
    public Long getDatoAdicionalId() {
        return datoAdicionalId;
    }

    /**
     * @param datoAdicionalId the datoAdicionalId to set
     */
    public void setDatoAdicionalId(Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
    }

}
