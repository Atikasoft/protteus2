/*
 *  BusquedaPuestoVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * VO para la Busqueda de Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class ConsultaNominaVO implements Serializable {

    /**
     * ****************************************************************************************************************************************************
     */
    /**
     * Variable para el Ejercicio Fiscal
     */
    private Long ejercicioFiscalId;

    /**
     * Variable para la Nomina
     */
    private Long nominaId;

    /**
     * Variable para el Tipo Nominas
     */
    private Long tipoNomimaId;

    /**
     * Variable para el Periodo Nominas
     */
    private Long periodoNomimaId;

    /**
     * Variable para el Tipo de Nominas
     */
    private String tipoDocumento;

    /**
     * Variable para el Número de Documento
     */
    private String numeroDocumento;

    /**
     * Variable para el nombre
     */
    private String nombre;

    /**
     * Variable para la fecha de creación
     */
    private Date fechaCreacionInicio;

    private Date fechaCreacionFin;

    /**
     * Variable para la fecha de Generación
     */
    private Date fechaGeneracionInicio;

    private Date fechaGeneracionFin;

    /**
     * Variable para la fecha de Aprobacion
     */
    private Date fechaAprobacionInicio;

    private Date fechaAprobacionFin;

    /**
     * Variable para el estado
     */
    private String estado;

    /**
     * Variable para el Rubro de Ingresos
     */
    private Long rubroIngresoId;

    /**
     * Variable para el Rubro de Descuentos
     */
    private Long rubroDescuentoId;

    /**
     * Variable para el Rubro de Aportes
     */
    private Long rubroAporteId;


    /**
     * Campo para el filtro, para garantes anticipos.
     */
    private String tipoModalidad;

    /**
     * Campo para el filtro, para ver si el puesto del distributivo esta vacante
     * o no, si es null se considera vacante.
     */
    private Boolean puestoVacante;

    /**
     * Constructor por defecto.
     */
    public ConsultaNominaVO() {
        super();
    }

       /**
     * @return the tipoModalidad
     */
    public String getTipoModalidad() {
        return tipoModalidad;
    }

    /**
     * @param tipoModalidad the tipoModalidad to set
     */
    public void setTipoModalidad(final String tipoModalidad) {
        this.tipoModalidad = tipoModalidad;
    }

    /**
     * @return the puestoVacante
     */
    public Boolean getPuestoVacante() {
        return puestoVacante;
    }

    /**
     * @param puestoVacante the puestoVacante to set
     */
    public void setPuestoVacante(final Boolean puestoVacante) {
        this.puestoVacante = puestoVacante;
    }

    /**
     * @return the nominaId
     */
    public Long getNominaId() {
        return nominaId;
    }

    /**
     * @param nominaId the nominaId to set
     */
    public void setNominaId(Long nominaId) {
        this.nominaId = nominaId;
    }

    /**
     * @return the tipoNomimaId
     */
    public Long getTipoNomimaId() {
        return tipoNomimaId;
    }

    /**
     * @param tipoNomimaId the tipoNomimaId to set
     */
    public void setTipoNomimaId(Long tipoNomimaId) {
        this.tipoNomimaId = tipoNomimaId;
    }

    /**
     * @return the periodoNomimaId
     */
    public Long getPeriodoNomimaId() {
        return periodoNomimaId;
    }

    /**
     * @param periodoNomimaId the periodoNomimaId to set
     */
    public void setPeriodoNomimaId(Long periodoNomimaId) {
        this.periodoNomimaId = periodoNomimaId;
    }

    /**
     * @return the ejercicioFiscalId
     */
    public Long getEjercicioFiscalId() {
        return ejercicioFiscalId;
    }

    /**
     * @param ejercicioFiscalId the ejercicioFiscalId to set
     */
    public void setEjercicioFiscalId(Long ejercicioFiscalId) {
        this.ejercicioFiscalId = ejercicioFiscalId;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the fechaCreacionInicio
     */
    public Date getFechaCreacionInicio() {
        return fechaCreacionInicio;
    }

    /**
     * @param fechaCreacionInicio the fechaCreacionInicio to set
     */
    public void setFechaCreacionInicio(Date fechaCreacionInicio) {
        this.fechaCreacionInicio = fechaCreacionInicio;
    }

    /**
     * @return the fechaCreacionFin
     */
    public Date getFechaCreacionFin() {
        return fechaCreacionFin;
    }

    /**
     * @param fechaCreacionFin the fechaCreacionFin to set
     */
    public void setFechaCreacionFin(Date fechaCreacionFin) {
        this.fechaCreacionFin = fechaCreacionFin;
    }

    /**
     * @return the fechaGeneracionInicio
     */
    public Date getFechaGeneracionInicio() {
        return fechaGeneracionInicio;
    }

    /**
     * @param fechaGeneracionInicio the fechaGeneracionInicio to set
     */
    public void setFechaGeneracionInicio(Date fechaGeneracionInicio) {
        this.fechaGeneracionInicio = fechaGeneracionInicio;
    }

    /**
     * @return the fechaGeneracionFin
     */
    public Date getFechaGeneracionFin() {
        return fechaGeneracionFin;
    }

    /**
     * @param fechaGeneracionFin the fechaGeneracionFin to set
     */
    public void setFechaGeneracionFin(Date fechaGeneracionFin) {
        this.fechaGeneracionFin = fechaGeneracionFin;
    }

    /**
     * @return the fechaAprobacionInicio
     */
    public Date getFechaAprobacionInicio() {
        return fechaAprobacionInicio;
    }

    /**
     * @param fechaAprobacionInicio the fechaAprobacionInicio to set
     */
    public void setFechaAprobacionInicio(Date fechaAprobacionInicio) {
        this.fechaAprobacionInicio = fechaAprobacionInicio;
    }

    /**
     * @return the fechaAprobacionFin
     */
    public Date getFechaAprobacionFin() {
        return fechaAprobacionFin;
    }

    /**
     * @param fechaAprobacionFin the fechaAprobacionFin to set
     */
    public void setFechaAprobacionFin(Date fechaAprobacionFin) {
        this.fechaAprobacionFin = fechaAprobacionFin;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the rubroIngresoId
     */
    public Long getRubroIngresoId() {
        return rubroIngresoId;
    }

    /**
     * @param rubroIngresoId the rubroIngresoId to set
     */
    public void setRubroIngresoId(Long rubroIngresoId) {
        this.rubroIngresoId = rubroIngresoId;
    }

    /**
     * @return the rubroDescuentoId
     */
    public Long getRubroDescuentoId() {
        return rubroDescuentoId;
    }

    /**
     * @param rubroDescuentoId the rubroDescuentoId to set
     */
    public void setRubroDescuentoId(Long rubroDescuentoId) {
        this.rubroDescuentoId = rubroDescuentoId;
    }

    /**
     * @return the rubroAporteId
     */
    public Long getRubroAporteId() {
        return rubroAporteId;
    }

    /**
     * @param rubroAporteId the rubroAporteId to set
     */
    public void setRubroAporteId(Long rubroAporteId) {
        this.rubroAporteId = rubroAporteId;
    }
}
