/*
 *  NovedadNominaVO.java
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

import ec.com.atikasoft.proteus.modelo.Novedad;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Soporta los datos que se muestran en los resultados de la nómina
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class NovedadNominaVO implements Serializable {

    /**
     * Lista de novedades de la nomina.
     */
    private List<NovedadDetalle> listaNovedadesDetalles;

    /**
     * Lista de novedades de la nomina.
     */
    private List<Novedad> listaNovedades;

    /**
     * Lista de novedades de la nomina.
     */
    private NovedadDetalle novedadDetalle;

    /**
     * Lista de novedades de la nomina.
     */
    private Set<NovedadDetalle> listaNovedadDetalleEditados;

    /**
     * Lista de novedades de la nomina.
     */
    private Novedad novedad;
    /**
     * Lista de novedades de la nomina.
     */
    private BigDecimal totalValor;

    /**
     *
     */
    private BigDecimal totalValorDescontado;

    /**
     *
     */
    private BigDecimal totalValorCalculado;
    /**
     * Lista de novedades de la nomina.
     */
    private Integer totalRegistros;

    public NovedadNominaVO() {
        inicializarVariables();
        inicializarTotales();
    }

    private void inicializarVariables() {
        listaNovedadesDetalles = new ArrayList<NovedadDetalle>();
        setListaNovedadDetalleEditados(new HashSet<NovedadDetalle>());
        novedadDetalle = new NovedadDetalle();
        listaNovedades = new ArrayList<Novedad>();
    }

    private void inicializarTotales() {
        totalValor = BigDecimal.ZERO;
        totalRegistros = 0;
        totalValorDescontado = BigDecimal.ZERO;
    }

    /**
     * @return the listaNovedadesDetalles
     */
    public List<NovedadDetalle> getListaNovedadesDetalles() {
        return listaNovedadesDetalles;
    }

    /**
     * @param listaNovedadesDetalles the listaNovedadesDetalles to set
     */
    public void setListaNovedadesDetalles(List<NovedadDetalle> listaNovedadesDetalles) {
        this.listaNovedadesDetalles = listaNovedadesDetalles;
    }

    /**
     * @return the novedadDetalle
     */
    public NovedadDetalle getNovedadDetalle() {
        return novedadDetalle;
    }

    /**
     * @param novedadDetalle the novedadDetalle to set
     */
    public void setNovedadDetalle(NovedadDetalle novedadDetalle) {
        this.novedadDetalle = novedadDetalle;
    }

    /**
     * @return the totalValor
     */
    public BigDecimal getTotalValor() {
        return totalValor;
    }

    /**
     * @param totalValor the totalValor to set
     */
    public void setTotalValor(BigDecimal totalValor) {
        this.totalValor = totalValor;
    }

    /**
     * @return the totalRegistros
     */
    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    /**
     * @param totalRegistros the totalRegistros to set
     */
    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    /**
     * @return the listaNovedades
     */
    public List<Novedad> getListaNovedades() {
        return listaNovedades;
    }

    /**
     * @param listaNovedades the listaNovedades to set
     */
    public void setListaNovedades(List<Novedad> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    /**
     * @return the novedad
     */
    public Novedad getNovedad() {
        return novedad;
    }

    /**
     * @param novedad the novedad to set
     */
    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }

    /**
     * @return the listaNovedadDetalleEditados
     */
    public Set<NovedadDetalle> getListaNovedadDetalleEditados() {
        return listaNovedadDetalleEditados;
    }

    /**
     * @param listaNovedadDetalleEditados the listaNovedadDetalleEditados to set
     */
    public void setListaNovedadDetalleEditados(Set<NovedadDetalle> listaNovedadDetalleEditados) {
        this.listaNovedadDetalleEditados = listaNovedadDetalleEditados;
    }

    /**
     * @return the totalValorDescontado
     */
    public BigDecimal getTotalValorDescontado() {
        return totalValorDescontado;
    }

    /**
     * @param totalValorDescontado the totalValorDescontado to set
     */
    public void setTotalValorDescontado(BigDecimal totalValorDescontado) {
        this.totalValorDescontado = totalValorDescontado;
    }

    /**
     * @return the totalValorCalculado
     */
    public BigDecimal getTotalValorCalculado() {
        return totalValorCalculado;
    }

    /**
     * @param totalValorCalculado the totalValorCalculado to set
     */
    public void setTotalValorCalculado(BigDecimal totalValorCalculado) {
        this.totalValorCalculado = totalValorCalculado;
    }
}
