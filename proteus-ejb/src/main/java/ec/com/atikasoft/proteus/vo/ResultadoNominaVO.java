/*
 *  ResultadoNominaVO.java
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
 *  23/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Soporta los datos que se muestran en los resultados de la nómina
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class ResultadoNominaVO implements Serializable {

    /**
     * Detalle de la Nómina.
     */
    private NominaDetalle nominaDetalle;
    /**
     * Id de la nómina.
     */
    private Nomina nomina;
    /**
     * Tipo de identificacion.
     */
    private String tipoIdentificacion;
    /**
     * Nuumero de identificacion.
     */
    private String numeroIdentificacion;
    /**
     * Nombres del servidor.
     */
    private String nombreServidor;
    /**
     * Modalidad Laboral: contrato - nombramiento.
     */
    private String nombreModalidadLaboral;
    /**
     * Total de ingresos x Servidor.
     */
    private BigDecimal ingresos;
    /**
     * Total egresos x Servidor.
     */
    private BigDecimal descuentos;
    /**
     * Total aportePatronal x Servidor.
     */
    private BigDecimal aportePatronal;
    /**
     * Total líquido a pagar x Servidor.
     */
    private BigDecimal liquidoAPagar;
    /**
     * Total general por Nómina de Ingresos.
     */
    private BigDecimal totalIngresos;
    /**
     * Total general por Nómina de Descuentos.
     */
    private BigDecimal totalDescuentos;
    /**
     * Total general por Nómina de Aportes Patronales.
     */
    private BigDecimal totalAportePatronal;
    /**
     * Total general de líquido a pagar por Nómina.
     */
    private BigDecimal totalLiquidoAPagar;
    /**
     * Total general de servidores por Nómina.
     */
    private BigDecimal totalServidores;
    /**
     * Variable.
     */
    private String variable;
    /**
     * Filtro para busqueda por número de identificación del servidor.
     */
    private String filtroNumeroIdentificacion;
    /**
     * Filtro para búsqueda por nombres del servidor.
     */
    private String filtroNombreServidor;

    /**
     * Total general por Nómina y por Servidor de Ingresos.
     */
    private BigDecimal totalIngresosServidor;
    /**
     * Total general por Nómina y por Servidor de Descuentos.
     */
    private BigDecimal totalDescuentosServidor;
    /**
     * Total general por Nómina y por Servidor de Aportes Patronales.
     */
    private BigDecimal totalAportePatronalServidor;

    /**
     * Total general por Nómina y por Servidor de Liquido a pagar.
     */
    private BigDecimal totalLiquidoPagarServidor;

    /**
     *
     */
    private Long servidorId;

    private Integer inicioConsulta;
    private Integer finConsulta;

    public ResultadoNominaVO() {
    }

    public void inicializarVariables() {
        setIngresos(BigDecimal.ZERO);
        setDescuentos(BigDecimal.ZERO);
        setAportePatronal(BigDecimal.ZERO);
        setLiquidoAPagar(BigDecimal.ZERO);

    }

    public void inicializarTotales() {
        setTotalLiquidoAPagar(BigDecimal.ZERO);
        setTotalDescuentos(BigDecimal.ZERO);
        setTotalIngresos(BigDecimal.ZERO);
        setTotalAportePatronal(BigDecimal.ZERO);
        totalServidores = BigDecimal.ZERO;
    }

    public void inicializarTotalesServidor() {
        totalIngresosServidor = BigDecimal.ZERO;
        totalDescuentosServidor = BigDecimal.ZERO;
        totalAportePatronalServidor = BigDecimal.ZERO;
    }

    public void inicializarFiltros() {
        setFiltroNumeroIdentificacion("");
        setFiltroNombreServidor("");
    }

    /**
     * @return the nominaDetalle
     */
    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }

    /**
     * @param nominaDetalle the nominaDetalle to set
     */
    public void setNominaDetalle(NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * @return the nombreModalidadLaboral
     */
    public String getNombreModalidadLaboral() {
        return nombreModalidadLaboral;
    }

    /**
     * @param nombreModalidadLaboral the nombreModalidadLaboral to set
     */
    public void setNombreModalidadLaboral(String nombreModalidadLaboral) {
        this.nombreModalidadLaboral = nombreModalidadLaboral;
    }

    /**
     * @return the ingresos
     */
    public BigDecimal getIngresos() {
        return ingresos;
    }

    /**
     * @param ingresos the ingresos to set
     */
    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    /**
     * @return the descuentos
     */
    public BigDecimal getDescuentos() {
        return descuentos;
    }

    /**
     * @param descuentos the descuentos to set
     */
    public void setDescuentos(BigDecimal descuentos) {
        this.descuentos = descuentos;
    }

    /**
     * @return the aportePatronal
     */
    public BigDecimal getAportePatronal() {
        return aportePatronal;
    }

    /**
     * @param aportePatronal the aportePatronal to set
     */
    public void setAportePatronal(BigDecimal aportePatronal) {
        this.aportePatronal = aportePatronal;
    }

    /**
     * @return the liquidoAPagar
     */
    public BigDecimal getLiquidoAPagar() {
        return liquidoAPagar;
    }

    /**
     * @param liquidoAPagar the liquidoAPagar to set
     */
    public void setLiquidoAPagar(BigDecimal liquidoAPagar) {
        this.liquidoAPagar = liquidoAPagar;
    }

    /**
     * @return the totalIngresos
     */
    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    /**
     * @param totalIngresos the totalIngresos to set
     */
    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    /**
     * @return the totalDescuentos
     */
    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    /**
     * @param totalDescuentos the totalDescuentos to set
     */
    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    /**
     * @return the totalAportePatronal
     */
    public BigDecimal getTotalAportePatronal() {
        return totalAportePatronal;
    }

    /**
     * @param totalAportePatronal the totalAportePatronal to set
     */
    public void setTotalAportePatronal(BigDecimal totalAportePatronal) {
        this.totalAportePatronal = totalAportePatronal;
    }

    /**
     * @return the totalLiquidoAPagar
     */
    public BigDecimal getTotalLiquidoAPagar() {
        return totalLiquidoAPagar;
    }

    /**
     * @param totalLiquidoAPagar the totalLiquidoAPagar to set
     */
    public void setTotalLiquidoAPagar(BigDecimal totalLiquidoAPagar) {
        this.totalLiquidoAPagar = totalLiquidoAPagar;
    }

    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * @return the filtroNúmeroIdentificacion
     */
    public String getFiltroNumeroIdentificacion() {
        return filtroNumeroIdentificacion;
    }

    /**
     * @param filtroNumeroIdentificacion the filtroNumeroIdentificacion to set
     */
    public void setFiltroNumeroIdentificacion(String filtroNumeroIdentificacion) {
        this.filtroNumeroIdentificacion = filtroNumeroIdentificacion;
    }

    /**
     * @return the filtroNombreServidor
     */
    public String getFiltroNombreServidor() {
        return filtroNombreServidor;
    }

    /**
     * @param filtroNombreServidor the filtroNombreServidor to set
     */
    public void setFiltroNombreServidor(String filtroNombreServidor) {
        this.filtroNombreServidor = filtroNombreServidor;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
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
     * @return the totalServidores
     */
    public BigDecimal getTotalServidores() {
        return totalServidores;
    }

    /**
     * @param totalServidores the totalServidores to set
     */
    public void setTotalServidores(BigDecimal totalServidores) {
        this.totalServidores = totalServidores;
    }

    /**
     * @return the totalIngresosServidor
     */
    public BigDecimal getTotalIngresosServidor() {
        return totalIngresosServidor;
    }

    /**
     * @param totalIngresosServidor the totalIngresosServidor to set
     */
    public void setTotalIngresosServidor(BigDecimal totalIngresosServidor) {
        this.totalIngresosServidor = totalIngresosServidor;
    }

    /**
     * @return the totalDescuentosServidor
     */
    public BigDecimal getTotalDescuentosServidor() {
        return totalDescuentosServidor;
    }

    /**
     * @param totalDescuentosServidor the totalDescuentosServidor to set
     */
    public void setTotalDescuentosServidor(BigDecimal totalDescuentosServidor) {
        this.totalDescuentosServidor = totalDescuentosServidor;
    }

    /**
     * @return the totalAportePatronalServidor
     */
    public BigDecimal getTotalAportePatronalServidor() {
        return totalAportePatronalServidor;
    }

    /**
     * @param totalAportePatronalServidor the totalAportePatronalServidor to set
     */
    public void setTotalAportePatronalServidor(BigDecimal totalAportePatronalServidor) {
        this.totalAportePatronalServidor = totalAportePatronalServidor;
    }

    /**
     * @return the totalLiquidoPagarServidor
     */
    public BigDecimal getTotalLiquidoPagarServidor() {
        return totalLiquidoPagarServidor;
    }

    /**
     * @param totalLiquidoPagarServidor the totalLiquidoPagarServidor to set
     */
    public void setTotalLiquidoPagarServidor(BigDecimal totalLiquidoPagarServidor) {
        this.totalLiquidoPagarServidor = totalLiquidoPagarServidor;
    }

    /**
     * @return the inicioConsulta
     */
    public Integer getInicioConsulta() {
        return inicioConsulta;
    }

    /**
     * @param inicioConsulta the inicioConsulta to set
     */
    public void setInicioConsulta(Integer inicioConsulta) {
        this.inicioConsulta = inicioConsulta;
    }

    /**
     * @return the finConsulta
     */
    public Integer getFinConsulta() {
        return finConsulta;
    }

    /**
     * @param finConsulta the finConsulta to set
     */
    public void setFinConsulta(Integer finConsulta) {
        this.finConsulta = finConsulta;
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
}
