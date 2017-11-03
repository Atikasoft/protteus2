/*
 *  Servidor.java
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
 *  Quito - Ecuadormr
 *  
 *
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.*;

/**
 * Historicos de jornada laboral del servidor
 *
 * @author Nelson Jumbo <nelson.jumbo@atikasoft.com.ec>
 */
@Entity
@XmlRootElement(name = "historico_jornada")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "servidor_historicos_jornada", catalog = "sch_proteus")
public class ServidorHistoricosJornada extends EntidadBasica {

    public ServidorHistoricosJornada(final Long id) {
        super();
        this.id = id;

    }

    /**
     * Constructor por defecto.
     */
    public ServidorHistoricosJornada() {
        super();
    }

    @XmlElement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servidor_id")
    private Servidor servidor;
    
    @Column(name = "jornada")
    private Integer jornada;
    
    @Column(name = "hora_entrada", columnDefinition = "datetime2")
    @Temporal(TemporalType.TIME)
    private Date horaEntrada;
    
    @Column(name="marcacion_obligatoria")
    private Boolean marcacionObligatoria;
    
    @Column(name="recibe_horas_extra")
    private Boolean recibeHorasExtra;
    
    @Column(name="motivo_cambio_jornada")
    private String motivoCambioJornada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public Integer getJornada() {
        return jornada;
    }

    public void setJornada(Integer jornada) {
        this.jornada = jornada;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Boolean isMarcacionObligatoria() {
        return marcacionObligatoria;
    }

    public void setMarcacionObligatoria(Boolean marcacionObligatoria) {
        this.marcacionObligatoria = marcacionObligatoria;
    }

    public Boolean isRecibeHorasExtra() {
        return recibeHorasExtra;
    }

    public void setRecibeHorasExtra(Boolean recibeHorasExtra) {
        this.recibeHorasExtra = recibeHorasExtra;
    }

    public String getMotivoCambioJornada() {
        return motivoCambioJornada;
    }

    public void setMotivoCambioJornada(String motivoCambioJornada) {
        this.motivoCambioJornada = motivoCambioJornada;
    }
    
    

}
