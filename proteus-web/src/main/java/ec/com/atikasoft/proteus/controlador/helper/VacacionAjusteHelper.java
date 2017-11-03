/**
 * VacacionAjusteHelper.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of Proteus ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * PROTEUS.
 *
 * PROTEUS Quito - Ecuador
 *
 * 29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.vo.BusquedaVacacionVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * VacacionAjusteHelper
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionAjusteHelper")
@SessionScoped
public class VacacionAjusteHelper extends CatalogoHelper {

     /**
     * Variables para servidores.
     */
    private List<Servidor> listaServidores;

    /**
     * filtro nombreServidor.
     */
    private String numeroIdentificacion;

    /**
     * filtro nombreServidor.
     */
    private String nombreServidor;
    /**
     * Variable de servidor.
     */
    private Servidor servidor;
    /**
     * Variable para los años que trabaja el servidor.
     */
    private List<SelectItem> listaAniosServidos;
    private Integer anioVacacion;
    
    /**
     * Distributivo de vacaciones.
     */
    private DistributivoDetalle distributivoDetalle = new DistributivoDetalle();
  
    
    /**
     * Vacaciones.
     */
    private List<Vacacion> listaVacacion;
    private List<VacacionDetalle> listaVacacionDetalle;
    private Vacacion vacacion;
    private BigDecimal diasEfectivos;
    private Integer mesesProporcionales;
    private BigDecimal diasProporcionales;
    private Long saldoMinutos;
    /**
     * Variables para la actualizacion de saldos.
     */
    private Double nuevoSaldoDias;
    private String observacionAjuste;
    private String mensaje;

    /**
     * Variable para asignar valores de busqueda.
     */
    private BusquedaVacacionVO busquedaVacacionVO = new BusquedaVacacionVO();

    /**
     * Constructor por defecto.
     */
    public VacacionAjusteHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     */
    public final void iniciador() {
        setListaVacacion(new ArrayList<Vacacion>());
        setServidor(new Servidor());
        setListaServidores(new ArrayList<Servidor>());
        listaAniosServidos =new ArrayList<SelectItem>();
        vacacion = new Vacacion();
        listaVacacionDetalle = new ArrayList<VacacionDetalle>();
    }

    /**
     * @return the listaVacacion
     */
    public List<Vacacion> getListaVacacion() {
        return listaVacacion;
    }

    /**
     * @param listaVacacion the listaVacacion to set
     */
    public void setListaVacacion(List<Vacacion> listaVacacion) {
        this.listaVacacion = listaVacacion;
    }

    /**
     * @return the busquedaVacacionVO
     */
    public BusquedaVacacionVO getBusquedaVacacionVO() {
        return busquedaVacacionVO;
    }

    /**
     * e
     *
     * @param busquedaVacacionVO the busquedaVacacionVO to set
     */
    public void setBusquedaVacacionVO(BusquedaVacacionVO busquedaVacacionVO) {
        this.busquedaVacacionVO = busquedaVacacionVO;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
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
     * @return the listaAniosServidos
     */
    public List<SelectItem> getListaAniosServidos() {
        return listaAniosServidos;
    }

    /**
     * @param listaAniosServidos the listaAniosServidos to set
     */
    public void setListaAniosServidos(List<SelectItem> listaAniosServidos) {
        this.listaAniosServidos = listaAniosServidos;
    }

    /**
     * @return the anioVacacion
     */
    public Integer getAnioVacacion() {
        return anioVacacion;
    }

    /**
     * @param anioVacacion the anioVacacion to set
     */
    public void setAnioVacacion(Integer anioVacacion) {
        this.anioVacacion = anioVacacion;
    }

    /**
     * @return the vacacion
     */
    public Vacacion getVacacion() {
        return vacacion;
    }

    /**
     * @param vacacion the vacacion to set
     */
    public void setVacacion(Vacacion vacacion) {
        this.vacacion = vacacion;
    }

    /**
     * @return the listaVacacionDetalle
     */
    public List<VacacionDetalle> getListaVacacionDetalle() {
        return listaVacacionDetalle;
    }

    /**
     * @param listaVacacionDetalle the listaVacacionDetalle to set
     */
    public void setListaVacacionDetalle(List<VacacionDetalle> listaVacacionDetalle) {
        this.listaVacacionDetalle = listaVacacionDetalle;
    }

    /**
     * @return the diasEfectivos
     */
    public BigDecimal getDiasEfectivos() {
        return diasEfectivos;
    }

    /**
     * @param diasEfectivos the diasEfectivos to set
     */
    public void setDiasEfectivos(BigDecimal diasEfectivos) {
        this.diasEfectivos = diasEfectivos;
    }

    /**
     * @return the mesesProporcionales
     */
    public Integer getMesesProporcionales() {
        return mesesProporcionales;
    }

    /**
     * @param mesesProporcionales the mesesProporcionales to set
     */
    public void setMesesProporcionales(Integer mesesProporcionales) {
        this.mesesProporcionales = mesesProporcionales;
    }

    /**
     * @return the diasProporcionales
     */
    public BigDecimal getDiasProporcionales() {
        return diasProporcionales;
    }

    /**
     * @param diasProporcionales the diasProporcionales to set
     */
    public void setDiasProporcionales(BigDecimal diasProporcionales) {
        this.diasProporcionales = diasProporcionales;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the saldoMinutos
     */
    public Long getSaldoMinutos() {
        return saldoMinutos;
    }

    /**
     * @param saldoMinutos the saldoMinutos to set
     */
    public void setSaldoMinutos(Long saldoMinutos) {
        this.saldoMinutos = saldoMinutos;
    }

    /**
     * @return the nuevoSaldoDias
     */
    public Double getNuevoSaldoDias() {
        return nuevoSaldoDias;
    }

    /**
     * @param nuevoSaldoDias the nuevoSaldoDias to set
     */
    public void setNuevoSaldoDias(Double nuevoSaldoDias) {
        this.nuevoSaldoDias = nuevoSaldoDias;
    }

    /**
     * @return the observacionAjuste
     */
    public String getObservacionAjuste() {
        return observacionAjuste;
    }

    /**
     * @param observacionAjuste the observacionAjuste to set
     */
    public void setObservacionAjuste(String observacionAjuste) {
        this.observacionAjuste = observacionAjuste;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
