/*
 *  ServidorHelper.java
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
 *  10/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.vo.ConsultaTrayectoriaLaboralVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * ServidorHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "consultaTrayectoriaLaboralHelper")
@SessionScoped
public final class ConsultaTrayectoriaLaboralHelper implements Serializable {

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion;

    /**
     * Filtro Nombre para busqueda.
     */
    private String filtroNombre;

    /**
     * tipo de identificacion.
     */
    private String tipoIdentificacion;
    /**
     * Lista de tipo documento.
     */
    private List<SelectItem> tipoDocumento;

    /**
     * tipo licencia Booleana.
     */
    private Boolean tipoCedula;
    /**
     * objeto de consulta con los parametros.
     */
    private ConsultaTrayectoriaLaboralVO consultaTrayectoriaLaboralVO;
    /**
     * movimientos.
     */
    private TrayectoriaLaboral movimiento;
    /**
     * Lista de Movimientos.
     */
    private List<TrayectoriaLaboral> listaMovimientos = new ArrayList<TrayectoriaLaboral>();

    /**
     * Bandera para deshabilitar el boton de impresion
     */
    private boolean generarTeporte;

    /**
     * Lista de servidores que coinciden con el filtro nombre especificado en la
     * búsqueda
     */
    List<Servidor> servidoresEncontrados;

    /**
     * Bandera para determinar tipo de filtro de busqueda utilizado
     */
    private boolean filtrarPorNombre;
    
    /**
     * Bandera para determinar tipo de filtro de busqueda utilizado
     */
    private boolean filtrarPorIdentificacion;
    
    /**
     * Servidor seleccionado de la lista de resultados en busqueda por nombre
     */
    private Servidor servidorSeleccionado;

    /**
     * Constructor por defecto.
     */
    public ConsultaTrayectoriaLaboralHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setTipoDocumento(new ArrayList<SelectItem>());
        setConsultaTrayectoriaLaboralVO(new ConsultaTrayectoriaLaboralVO());
        setMovimiento(new TrayectoriaLaboral());
        setListaMovimientos(new ArrayList<TrayectoriaLaboral>());
        setServidoresEncontrados(new ArrayList<Servidor>());
        setTipoCedula(Boolean.TRUE);
        setFiltrarPorIdentificacion(true);
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
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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
    public void setTipoIdentificacion(final String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the tipoDocumento
     */
    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @return the tipoCedula
     */
    public Boolean getTipoCedula() {
        return tipoCedula;
    }

    /**
     * @param tipoCedula the tipoCedula to set
     */
    public void setTipoCedula(final Boolean tipoCedula) {
        this.tipoCedula = tipoCedula;
    }

    /**
     * @return the consultaTrayectoriaLaboralVO
     */
    public ConsultaTrayectoriaLaboralVO getConsultaTrayectoriaLaboralVO() {
        return consultaTrayectoriaLaboralVO;
    }

    /**
     * @param consultaTrayectoriaLaboralVO the consultaTrayectoriaLaboralVO to
     * set
     */
    public void setConsultaTrayectoriaLaboralVO(final ConsultaTrayectoriaLaboralVO consultaTrayectoriaLaboralVO) {
        this.consultaTrayectoriaLaboralVO = consultaTrayectoriaLaboralVO;
    }

    /**
     * @return the movimiento
     */
    public TrayectoriaLaboral getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final TrayectoriaLaboral movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the listaMovimientos
     */
    public List<TrayectoriaLaboral> getListaMovimientos() {
        return listaMovimientos;
    }

    /**
     * @param listaMovimientos the listaMovimientos to set
     */
    public void setListaMovimientos(final List<TrayectoriaLaboral> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    public boolean getGenerarTeporte() {
        return generarTeporte;
    }

    public void setGenerarTeporte(boolean generarTeporte) {
        this.generarTeporte = generarTeporte;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public List<Servidor> getServidoresEncontrados() {
        return servidoresEncontrados;
    }

    public void setServidoresEncontrados(List<Servidor> servidoresEncontrados) {
        this.servidoresEncontrados = servidoresEncontrados;
    }

    public boolean getFiltrarPorNombre() {
        return filtrarPorNombre;
    }

    public void setFiltrarPorNombre(boolean filtrarPorNombre) {
        this.filtrarPorNombre = filtrarPorNombre;
    }

    public boolean getFiltrarPorIdentificacion() {
        return filtrarPorIdentificacion;
    }

    public void setFiltrarPorIdentificacion(boolean filtrarPorIdentificacion) {
        this.filtrarPorIdentificacion = filtrarPorIdentificacion;
    }

    public Servidor getServidorSeleccionado() {
        return servidorSeleccionado;
    }

    public void setServidorSeleccionado(Servidor servidorSeleccionado) {
        this.servidorSeleccionado = servidorSeleccionado;
    }

    
}
