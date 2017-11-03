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
 *  21/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "servidor_cargas_familiares", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorCargaFamiliar.BUSCAR_PAGO_NOMINA,
            query = "SELECT COUNT(o) FROM ServidorCargaFamiliar o WHERE o.vigente=true AND "
            + " o.servidor.id = ?1 AND (o.discapacitado=true OR o.fechaNacimiento > ?2 ) "),
    @NamedQuery(name = ServidorCargaFamiliar.BUSCAR_POR_CARGA_FAMILIAR_ID, 
            query = "SELECT c FROM ServidorCargaFamiliar c where  c.servidorId=?1  and c.vigente=true ")
})
public class ServidorCargaFamiliar extends EntidadBasica {

    /**
     * Nombre de conualta.
     */
    public static final String BUSCAR_PAGO_NOMINA = "ServidorCargaFamiliar.buscarPagoNomina";
    /**
     * Nombre de la consulta para buscar todos los servidores vigentes
     */
    public static final String BUSCAR_POR_CARGA_FAMILIAR_ID = "ServidorCargaFamiliar.buscarPorCargaFamiliarId";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a servidor
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;
    /**
     * Referencia con Servidor.
     */
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     * Especifica la fecha de nacimiento
     */
    @Column(name = "fecha_nacimiento")
    @Temporal(value = TemporalType.DATE)
    @NotNull
    private Date fechaNacimiento;

    /**
     *
     */
    @Column(name = "discapacitado")
    private Boolean discapacitado;

    @Column(name = "nombres")
    private String nombres;
    
    @Column(name = "estado")
    private String estado;
    
    @Transient
    private String estadoNombre;
    
    @Transient
    private Boolean bloqueado;

    /**
     * Constructor.
     */
    public ServidorCargaFamiliar() {
        super();
    }

    /**
     * Constructor por defecto.
     *
     * @param id Long
     */
    public ServidorCargaFamiliar(final Long id) {
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
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @return the fechaNacimiento
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @return the discapacitado
     */
    public Boolean getDiscapacitado() {
        return discapacitado;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(final Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @param discapacitado the discapacitado to set
     */
    public void setDiscapacitado(final Boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(final String nombres) {
        this.nombres = nombres;
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
    public void setServidorId(final Long servidorId) {
        this.servidorId = servidorId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}
