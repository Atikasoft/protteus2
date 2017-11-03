/*
 *  ValidacionBitacora.java
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
 *  04/02/2013
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@Entity
@Table(name = "validaciones_bitacora", catalog = "sch_proteus")
public class ValidacionBitacora extends EntidadBasica {

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Bandera cumple.
     */
    @Column(name = "cumple")
    private boolean cumple;

    /**
     * Numero de documento.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Fecha de documento.
     */
    @Column(name = "fecha_documento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;

    /**
     * Observacion.
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * Calificacion.
     */
    @Column(name = "calificacion")
    private String calificacion;

    /**
     * Sustento Legal.
     */
    @Column(name = "sustento_legal")
    private String sustentoLegal;

    /**
     * Tipo de Movimiento Requisito.
     */
    @JoinColumn(name = "tipos_movimientos_requisitos_id")
    @OneToOne(optional = false)
    private TipoMovimientoRequisito tipoMovimientoRequisito;

    /**
     * Movimiento.
     */
    @JoinColumn(name = "movimientos_id")
    @OneToOne(optional = false)
    private Movimiento movimiento;

    /**
     * Referencia a archivos.
     */
    @JoinColumn(name = "archivos_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Archivo archivo;

    /**
     * Constructor por defecto.
     */
    public ValidacionBitacora() {
        super();
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
     * @return the cumple
     */
    public boolean isCumple() {
        return cumple;
    }

    /**
     * @param cumple the cumple to set
     */
    public void setCumple(final boolean cumple) {
        this.cumple = cumple;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the fechaDocumento
     */
    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    /**
     * @param fechaDocumento the fechaDocumento to set
     */
    public void setFechaDocumento(final Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the calificacion
     */
    public String getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(final String calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * @return the sustentoLegal
     */
    public String getSustentoLegal() {
        return sustentoLegal;
    }

    /**
     * @param sustentoLegal the sustentoLegal to set
     */
    public void setSustentoLegal(final String sustentoLegal) {
        this.sustentoLegal = sustentoLegal;
    }

    /**
     * @return the tipoMovimientoRequisito
     */
    public TipoMovimientoRequisito getTipoMovimientoRequisito() {
        return tipoMovimientoRequisito;
    }

    /**
     * @param tipoMovimientoRequisito the tipoMovimientoRequisito to set
     */
    public void setTipoMovimientoRequisito(final TipoMovimientoRequisito tipoMovimientoRequisito) {
        this.tipoMovimientoRequisito = tipoMovimientoRequisito;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(final Archivo archivo) {
        this.archivo = archivo;
    }
}