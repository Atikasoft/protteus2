/*
 *  ComprobanteRetencioHelper.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ComprobanteRetencionImpuestoRenta;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "comprobanteRetencionHelper")
@SessionScoped
public class ComprobanteRetencioHelper extends CatalogoHelper {

    /**
     * Entidad de edicion y creacion para el formulario
     */
    private ComprobanteRetencionImpuestoRenta  comprobanteRetencionImpuestoRenta;
    
    /**
     * Referencia para edicion eliminacion desde la lista
     */
    private ComprobanteRetencionImpuestoRenta  comprobanteRetencionImpuestoRentaTransaccional;

    /**
     * Lista de resultados
     */
    private List<ComprobanteRetencionImpuestoRenta> listaComprobanteRetencionImpuestoRenta;
    
    /**
     * Ejercicios fiscales para el filtro del buscador
     */
    private List<SelectItem> listaEjercicioFiscal;
    
    /**
     * Id de ejercicio fiscal seleccionado en el buscador
     */
    private Long ejercicioFiscal;
    
    /**
     * 
     */
    private EjercicioFiscal ejercicioFiscalActual;
    
    /**
     * Nombres y Apellidos del servidor entrado en el buscador
     */
    private String nombreServidor;
    
    /**
     * Numero de identificacion entrado en el buscador
     */
    private String numeroIdentificacionServidor;
    
    /**
     * Busqueda de servidor en el formulario para el caso de formulario nuevo
     */
    private Servidor servidorSeleccionado;
    
    /**
     * Busqueda de servidor en el formulario para el caso de formulario nuevo
     */
    private List<Servidor> listaServidores;
    
    /**
     * Carga de archivo
     */
    private UploadedFile archivoCargado;
    
    /**
     * Nombre Archivo Cargado
     */
    private String nombreArchivo;
    
    /**
     * Para descargar
     */
    private File archivoFile;
    
    /**
     * Distinguir si el servidor seleccionado para el formulario tiene formulario antiguo en este periodo
     */
    private boolean tieneFormulario;
    
    /**
     * Para bloquear formularios viejos
     */
    private boolean soloVer;
    
    /**
     * Menor fecha del periodo
     */
    private Date minFecha;
    
    /**
     * Mayor fecha del periodo
     */
    private Date maxFecha;
    
    /**
     * Constructor por defecto.
     */
    public ComprobanteRetencioHelper() {
        super();
        iniciador();
    }

    /**
     * Este metodo inicializa todas las variables.
     */
    public final void iniciador() {
        listaComprobanteRetencionImpuestoRenta = new ArrayList<ComprobanteRetencionImpuestoRenta>();
        listaEjercicioFiscal = new ArrayList<SelectItem>();
        listaServidores = new ArrayList<Servidor>();
    }

    public ComprobanteRetencionImpuestoRenta getComprobanteRetencionImpuestoRenta() {
        return comprobanteRetencionImpuestoRenta;
    }

    public void setComprobanteRetencionImpuestoRenta(ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRenta) {
        this.comprobanteRetencionImpuestoRenta = comprobanteRetencionImpuestoRenta;
    }

    public ComprobanteRetencionImpuestoRenta getComprobanteRetencionImpuestoRentaTransaccional() {
        return comprobanteRetencionImpuestoRentaTransaccional;
    }

    public void setComprobanteRetencionImpuestoRentaTransaccional(ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRentaTransaccional) {
        this.comprobanteRetencionImpuestoRentaTransaccional = comprobanteRetencionImpuestoRentaTransaccional;
    }

    public List<ComprobanteRetencionImpuestoRenta> getListaComprobanteRetencionImpuestoRenta() {
        return listaComprobanteRetencionImpuestoRenta;
    }

    public void setListaComprobanteRetencionImpuestoRenta(List<ComprobanteRetencionImpuestoRenta> listaComprobanteRetencionImpuestoRenta) {
        this.listaComprobanteRetencionImpuestoRenta = listaComprobanteRetencionImpuestoRenta;
    }

    public List<SelectItem> getListaEjercicioFiscal() {
        return listaEjercicioFiscal;
    }

    public void setListaEjercicioFiscal(List<SelectItem> listaEjercicioFiscal) {
        this.listaEjercicioFiscal = listaEjercicioFiscal;
    }

    public Long getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    public void setEjercicioFiscal(Long ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    public String getNombreServidor() {
        return nombreServidor;
    }

    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    public String getNumeroIdentificacionServidor() {
        return numeroIdentificacionServidor;
    }

    public void setNumeroIdentificacionServidor(String numeroIdentificacionServidor) {
        this.numeroIdentificacionServidor = numeroIdentificacionServidor;
    }

    public EjercicioFiscal getEjercicioFiscalActual() {
        return ejercicioFiscalActual;
    }

    public void setEjercicioFiscalActual(EjercicioFiscal ejercicioFiscalActual) {
        this.ejercicioFiscalActual = ejercicioFiscalActual;
    }

    public Servidor getServidorSeleccionado() {
        return servidorSeleccionado;
    }

    public void setServidorSeleccionado(Servidor servidorSeleccionado) {
        this.servidorSeleccionado = servidorSeleccionado;
    }

    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    public void setArchivoCargado(UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public File getArchivoFile() {
        return archivoFile;
    }

    public void setArchivoFile(File archivoFile) {
        this.archivoFile = archivoFile;
    }

    public boolean getTieneFormulario() {
        return tieneFormulario;
    }

    public void setTieneFormulario(boolean tieneFormulario) {
        this.tieneFormulario = tieneFormulario;
    }

    public boolean getSoloVer() {
        return soloVer;
    }

    public void setSoloVer(boolean soloVer) {
        this.soloVer = soloVer;
    }

    public Date getMinFecha() {
        return minFecha;
    }

    public void setMinFecha(Date minFecha) {
        this.minFecha = minFecha;
    }

    public Date getMaxFecha() {
        return maxFecha;
    }

    public void setMaxFecha(Date maxFecha) {
        this.maxFecha = maxFecha;
    }
}
