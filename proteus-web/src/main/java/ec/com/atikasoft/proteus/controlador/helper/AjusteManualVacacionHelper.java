/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "ajusteManualVacacionHelper")
@SessionScoped
public class AjusteManualVacacionHelper implements Serializable {

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
     * 
     */
    private Boolean tipoCedulaSeleccionado;
    /**
     * 
     */
    private Boolean visualizarInputCedula;
    /**
     * 
     */
    private Boolean visualizarInputPasaporte;
    /**
     *
     */
    private int tabViewIndex;
    /**
     * Servidor seleccionado de la lista de resultados en busqueda por nombre
     */
    private Servidor servidorSeleccionado;
    
    /**
     * 
     */
    private boolean tieneVacacion;
    
    /**
     * 
     */
    private Vacacion servidorVacacion;
    
    /**
     * Saldo Efectivo En dias, horas y minutos
     */
    private String saldoEfectivoEnPalabras;
    
    /**
     * Saldo Proporcional En dias, horas y minutos
     */
    private String saldoProporcionalEnPalabras;
    
    /**
     * Saldo Días Perdidos En dias, horas y minutos
     */
    private String saldoPerdidoEnPalabras;
    
    /**
     * Justificación de cambio Saldo Efectivo
     */
    private String saldoEfectivoJustificacion;
    
    /**
     * Justificación de cambio Saldo Proporcional
     */
    private String saldoProporcionalJustificacion;
    
    /**
     * Justificación de cambio Saldo Días Perdidos
     */
    private String saldoPerdidoJustificacion;
    
    /**
     * Saldo Efectivo En dias
     */
    private double saldoEfectivoEnDias;
    
    /**
     * Saldo Proporcional En dias
     */
    private double saldoProporcionalEnDias;
    
    /**
     * Saldo Días Perdidos En dias
     */
    private double saldoPerdidoEnDias;
    
    /**
     * Maximo de días de vacaciones según el regimen laboral
     */
    private double maxVacaciones;
    
    /**
     * Maximo de días de vacaciones proporcionales según el regimen laboral
     */
    private double maxVacacionesProporcional;
            
    /**
     * Lista de servidores que coinciden con el filtro nombre especificado en la
     * búsqueda
     */
    List<Servidor> servidoresEncontrados;

    public AjusteManualVacacionHelper() {
        super();
        iniciador();
    }

    private void iniciador() {
        setTipoDocumento(new ArrayList<SelectItem>());
        setServidoresEncontrados(new ArrayList<Servidor>());
        setTipoCedulaSeleccionado(Boolean.FALSE);
        setVisualizarInputCedula(Boolean.FALSE);
        setVisualizarInputPasaporte(Boolean.FALSE);
    }

    public int getTabViewIndex() {
        return tabViewIndex;
    }

    public void setTabViewIndex(int tabViewIndex) {
        this.tabViewIndex = tabViewIndex;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getFiltroNombre() {
        return filtroNombre;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public List<SelectItem> getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(List<SelectItem> tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Servidor getServidorSeleccionado() {
        return servidorSeleccionado;
    }

    public void setServidorSeleccionado(Servidor servidorSeleccionado) {
        this.servidorSeleccionado = servidorSeleccionado;
    }

    public List<Servidor> getServidoresEncontrados() {
        return servidoresEncontrados;
    }

    public void setServidoresEncontrados(List<Servidor> servidoresEncontrados) {
        this.servidoresEncontrados = servidoresEncontrados;
    }

    public Boolean getTipoCedulaSeleccionado() {
        return tipoCedulaSeleccionado;
    }

    public void setTipoCedulaSeleccionado(Boolean tipoCedulaSeleccionado) {
        this.tipoCedulaSeleccionado = tipoCedulaSeleccionado;
    }

    public Vacacion getServidorVacacion() {
        return servidorVacacion;
    }

    public void setServidorVacacion(Vacacion servidorVacacion) {
        this.servidorVacacion = servidorVacacion;
    }

    public String getSaldoEfectivoEnPalabras() {
        return saldoEfectivoEnPalabras;
    }

    public void setSaldoEfectivoEnPalabras(String saldoEfectivoEnPalabras) {
        this.saldoEfectivoEnPalabras = saldoEfectivoEnPalabras;
    }

    public String getSaldoProporcionalEnPalabras() {
        return saldoProporcionalEnPalabras;
    }

    public void setSaldoProporcionalEnPalabras(String saldoProporcionalEnPalabras) {
        this.saldoProporcionalEnPalabras = saldoProporcionalEnPalabras;
    }

    public String getSaldoPerdidoEnPalabras() {
        return saldoPerdidoEnPalabras;
    }

    public void setSaldoPerdidoEnPalabras(String saldoPerdidoEnPalabras) {
        this.saldoPerdidoEnPalabras = saldoPerdidoEnPalabras;
    }

    public boolean getTieneVacacion() {
        return tieneVacacion;
    }

    public void setTieneVacacion(boolean tieneVacacion) {
        this.tieneVacacion = tieneVacacion;
    }

    public double getSaldoEfectivoEnDias() {
        return saldoEfectivoEnDias;
    }

    public void setSaldoEfectivoEnDias(double saldoEfectivoEnDias) {
        this.saldoEfectivoEnDias = saldoEfectivoEnDias;
    }

    public double getSaldoProporcionalEnDias() {
        return saldoProporcionalEnDias;
    }

    public void setSaldoProporcionalEnDias(double saldoProporcionalEnDias) {
        this.saldoProporcionalEnDias = saldoProporcionalEnDias;
    }

    public double getSaldoPerdidoEnDias() {
        return saldoPerdidoEnDias;
    }

    public void setSaldoPerdidoEnDias(double saldoPerdidoEnDias) {
        this.saldoPerdidoEnDias = saldoPerdidoEnDias;
    }

    public String getSaldoEfectivoJustificacion() {
        return saldoEfectivoJustificacion;
    }

    public void setSaldoEfectivoJustificacion(String saldoEfectivoJustificacion) {
        this.saldoEfectivoJustificacion = saldoEfectivoJustificacion;
    }

    public String getSaldoProporcionalJustificacion() {
        return saldoProporcionalJustificacion;
    }

    public void setSaldoProporcionalJustificacion(String saldoProporcionalJustificacion) {
        this.saldoProporcionalJustificacion = saldoProporcionalJustificacion;
    }

    public String getSaldoPerdidoJustificacion() {
        return saldoPerdidoJustificacion;
    }

    public void setSaldoPerdidoJustificacion(String saldoPerdidoJustificacion) {
        this.saldoPerdidoJustificacion = saldoPerdidoJustificacion;
    }

    public double getMaxVacaciones() {
        return maxVacaciones;
    }

    public void setMaxVacaciones(double maxVacaciones) {
        this.maxVacaciones = maxVacaciones;
    }

    public Boolean getVisualizarInputCedula() {
        return visualizarInputCedula;
    }

    public void setVisualizarInputCedula(Boolean visualizarInputCedula) {
        this.visualizarInputCedula = visualizarInputCedula;
    }

    public Boolean getVisualizarInputPasaporte() {
        return visualizarInputPasaporte;
    }

    public void setVisualizarInputPasaporte(Boolean visualizarInputPasaporte) {
        this.visualizarInputPasaporte = visualizarInputPasaporte;
    }

    /**
     * @return the maxVacacionesProporcional
     */
    public double getMaxVacacionesProporcional() {
        return maxVacacionesProporcional;
    }

    /**
     * @param maxVacacionesProporcional the maxVacacionesProporcional to set
     */
    public void setMaxVacacionesProporcional(double maxVacacionesProporcional) {
        this.maxVacacionesProporcional = maxVacacionesProporcional;
    }

    
    
}
