/*
 *  ResultadoNominaHelper.java
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
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.nomina.NominaIR;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * BusquedaPuestoHelper.
 *
 *
 */
@ManagedBean(name = "resultadoNominaHelper")
@SessionScoped
public class ResultadoNominaHelper implements Serializable {

    /**
     * Lista de resultados de la búsqueda.
     */
    private List<ResultadoNominaVO> listaResultadoNomina;

    /**
     * Variable de busqueda de resultados.
     */
    private ResultadoNominaVO resultadoNomina;

    private Nomina nomina;
    private NominaDetalle nominaDetalle;

    /**
     * LIstas para mostrar el detalle de la nomina por servidor.
     */
    private List<NominaDetalle> listaDetallesIngresos;

    private List<NominaDetalle> listaDetallesDescuentos;

    private List<NominaDetalle> listaDetallesAportes;
    private List<ResultadoNominaVO> listaResultadoConteo;

    private List<SelectItem> tiposDocumentos;

    private Servidor servidor;

    private String numeroIdentificacion;

    private String tipoIdentificacion;

    private Long institucionEjercicioFiscal;

    /**
     * Lista de servidores para el autocomplete
     */
    private List<Servidor> servidores;

    /**
     * Variable que permite navegar entre pantallas. 1.- Nomina - resultados 2.-
     * nomina diferenciada - resultados 3.- consulta - resultados
     */
    private int retorno;

    /**
     *
     */
    private NominaIR nominaIR;

    /**
     * Constructor por defecto.
     */
    public ResultadoNominaHelper() {
        super();
        retorno = 1;
        nomina = new Nomina();
        nominaDetalle = new NominaDetalle();
        listaResultadoNomina = new ArrayList<ResultadoNominaVO>();
        resultadoNomina = new ResultadoNominaVO();
        listaDetallesAportes = new ArrayList<NominaDetalle>();
        listaDetallesDescuentos = new ArrayList<NominaDetalle>();
        listaDetallesIngresos = new ArrayList<NominaDetalle>();
        resultadoNomina = new ResultadoNominaVO();
        resultadoNomina.setNomina(new Nomina());
        listaResultadoConteo = new ArrayList<ResultadoNominaVO>();
        tiposDocumentos = new ArrayList<SelectItem>();
        servidores = new ArrayList<Servidor>();

    }

    /**
     * @return the listaResultadoNomina
     */
    public List<ResultadoNominaVO> getListaResultadoNomina() {
        return listaResultadoNomina;
    }

    /**
     * @param listaResultadoNomina the listaResultadoNomina to set
     */
    public void setListaResultadoNomina(final List<ResultadoNominaVO> listaResultadoNomina) {
        this.listaResultadoNomina = listaResultadoNomina;
    }

    /**
     * @return the resultadoNomina
     */
    public ResultadoNominaVO getResultadoNomina() {
        return resultadoNomina;
    }

    /**
     * @param resultadoNomina the resultadoNomina to set
     */
    public void setResultadoNomina(final ResultadoNominaVO resultadoNomina) {
        this.resultadoNomina = resultadoNomina;
    }

    /**
     * @return the listaDetallesIngresos
     */
    public List<NominaDetalle> getListaDetallesIngresos() {
        return listaDetallesIngresos;
    }

    /**
     * @param listaDetallesIngresos the listaDetallesIngresos to set
     */
    public void setListaDetallesIngresos(final List<NominaDetalle> listaDetallesIngresos) {
        this.listaDetallesIngresos = listaDetallesIngresos;
    }

    /**
     * @return the listaDetallesDescuentos
     */
    public List<NominaDetalle> getListaDetallesDescuentos() {
        return listaDetallesDescuentos;
    }

    /**
     * @param listaDetallesDescuentos the listaDetallesDescuentos to set
     */
    public void setListaDetallesDescuentos(final List<NominaDetalle> listaDetallesDescuentos) {
        this.listaDetallesDescuentos = listaDetallesDescuentos;
    }

    /**
     * @return the listaDetallesAportes
     */
    public List<NominaDetalle> getListaDetallesAportes() {
        return listaDetallesAportes;
    }

    /**
     * @param listaDetallesAportes the listaDetallesAportes to set
     */
    public void setListaDetallesAportes(final List<NominaDetalle> listaDetallesAportes) {
        this.listaDetallesAportes = listaDetallesAportes;
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
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the listaResultadoConteo
     */
    public List<ResultadoNominaVO> getListaResultadoConteo() {
        return listaResultadoConteo;
    }

    /**
     * @param listaResultadoConteo the listaResultadoConteo to set
     */
    public void setListaResultadoConteo(List<ResultadoNominaVO> listaResultadoConteo) {
        this.listaResultadoConteo = listaResultadoConteo;
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
     * @return the retorno
     */
    public int getRetorno() {
        return retorno;
    }

    /**
     * @param retorno the retorno to set
     */
    public void setRetorno(int retorno) {
        this.retorno = retorno;
    }

    public List<SelectItem> getTiposDocumentos() {
        return tiposDocumentos;
    }

    public void setTiposDocumentos(List<SelectItem> tiposDocumentos) {
        this.tiposDocumentos = tiposDocumentos;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    public void setServidores(List<Servidor> servidores) {
        this.servidores = servidores;
    }

    public Long getInstitucionEjercicioFiscal() {
        return institucionEjercicioFiscal;
    }

    public void setInstitucionEjercicioFiscal(Long institucionEjercicioFiscal) {
        this.institucionEjercicioFiscal = institucionEjercicioFiscal;
    }

    /**
     * @return the nominaIR
     */
    public NominaIR getNominaIR() {
        return nominaIR;
    }

    /**
     * @param nominaIR the nominaIR to set
     */
    public void setNominaIR(NominaIR nominaIR) {
        this.nominaIR = nominaIR;
    }
}
