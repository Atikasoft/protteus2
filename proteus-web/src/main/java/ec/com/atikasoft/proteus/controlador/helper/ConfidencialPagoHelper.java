/*
 *  AlertaHelper.java
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
 *  08/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.ResultadoNominaVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "confidencialPagoHelper")
@SessionScoped
public class ConfidencialPagoHelper extends CatalogoHelper {

    /**
     * BusquedaNominaVO.
     */
    private BusquedaNominaVO busquedaNominaVO;
    /**
     * lista nominas detalles.
     */
    private List<NominaDetalle> listaNominasDetalles;
    /**
     * lista nominas detalles.
     */
    private List<Nomina> listaNominas;
    private Nomina nomina;
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoFiscal = new ArrayList<SelectItem>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoNomina = new ArrayList<SelectItem>();

    /**
     * Lista de resultados de la búsqueda.
     */
    private List<ResultadoNominaVO> listaResultadoNominaVO;
    private ResultadoNominaVO resultadoNominaVO;
    /**
     * LIstas para mostrar el detalle de la nomina por servidor.
     */
    private List<NominaDetalle> listaDetallesIngresos;

    private List<NominaDetalle> listaDetallesDescuentos;

    private List<NominaDetalle> listaDetallesAportes;
    private List<ResultadoNominaVO> listaResultadoConteo;
    private NominaDetalle nominaDetalle;

    /**
     * Constructor.
     */
    public ConfidencialPagoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la AlertaHelper.
     */
    public final void iniciador() {
        setBusquedaNominaVO(new BusquedaNominaVO());
        setListaNominasDetalles(new ArrayList<NominaDetalle>());
        setListaPeriodoFiscal(new ArrayList<SelectItem>());
        setListaPeriodoNomina(new ArrayList<SelectItem>());
        setListaNominas(new ArrayList<Nomina>());
        setResultadoNominaVO(new ResultadoNominaVO());
        setNomina(new Nomina());
        setListaResultadoNominaVO(new ArrayList<ResultadoNominaVO>());
        setListaDetallesIngresos(new ArrayList<NominaDetalle>());
        setListaDetallesDescuentos(new ArrayList<NominaDetalle>());
        setListaDetallesAportes(new ArrayList<NominaDetalle>());
        setListaResultadoConteo(new ArrayList<ResultadoNominaVO>());
        setNominaDetalle(new NominaDetalle());
    }

    /**
     * @return the busquedaNominaVO
     */
    public BusquedaNominaVO getBusquedaNominaVO() {
        return busquedaNominaVO;
    }

    /**
     * @param busquedaNominaVO the busquedaNominaVO to set
     */
    public void setBusquedaNominaVO(BusquedaNominaVO busquedaNominaVO) {
        this.busquedaNominaVO = busquedaNominaVO;
    }

    /**
     * @return the listaNominasDetalles
     */
    public List<NominaDetalle> getListaNominasDetalles() {
        return listaNominasDetalles;
    }

    /**
     * @param listaNominasDetalles the listaNominasDetalles to set
     */
    public void setListaNominasDetalles(List<NominaDetalle> listaNominasDetalles) {
        this.listaNominasDetalles = listaNominasDetalles;
    }

    /**
     * @return the listaPeriodoFiscal
     */
    public List<SelectItem> getListaPeriodoFiscal() {
        return listaPeriodoFiscal;
    }

    /**
     * @param listaPeriodoFiscal the listaPeriodoFiscal to set
     */
    public void setListaPeriodoFiscal(List<SelectItem> listaPeriodoFiscal) {
        this.listaPeriodoFiscal = listaPeriodoFiscal;
    }

    /**
     * @return the listaPeriodoNomina
     */
    public List<SelectItem> getListaPeriodoNomina() {
        return listaPeriodoNomina;
    }

    /**
     * @param listaPeriodoNomina the listaPeriodoNomina to set
     */
    public void setListaPeriodoNomina(List<SelectItem> listaPeriodoNomina) {
        this.listaPeriodoNomina = listaPeriodoNomina;
    }

    /**
     * @return the listaNominas
     */
    public List<Nomina> getListaNominas() {
        return listaNominas;
    }

    /**
     * @param listaNominas the listaNominas to set
     */
    public void setListaNominas(List<Nomina> listaNominas) {
        this.listaNominas = listaNominas;
    }

    /**
     * @return the resultadoNominaVO
     */
    public ResultadoNominaVO getResultadoNominaVO() {
        return resultadoNominaVO;
    }

    /**
     * @param resultadoNominaVO the resultadoNominaVO to set
     */
    public void setResultadoNominaVO(ResultadoNominaVO resultadoNominaVO) {
        this.resultadoNominaVO = resultadoNominaVO;
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
     * @return the listaResultadoNominaVO
     */
    public List<ResultadoNominaVO> getListaResultadoNominaVO() {
        return listaResultadoNominaVO;
    }

    /**
     * @param listaResultadoNominaVO the listaResultadoNominaVO to set
     */
    public void setListaResultadoNominaVO(List<ResultadoNominaVO> listaResultadoNominaVO) {
        this.listaResultadoNominaVO = listaResultadoNominaVO;
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
    public void setListaDetallesIngresos(List<NominaDetalle> listaDetallesIngresos) {
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
    public void setListaDetallesDescuentos(List<NominaDetalle> listaDetallesDescuentos) {
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
    public void setListaDetallesAportes(List<NominaDetalle> listaDetallesAportes) {
        this.listaDetallesAportes = listaDetallesAportes;
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

}
