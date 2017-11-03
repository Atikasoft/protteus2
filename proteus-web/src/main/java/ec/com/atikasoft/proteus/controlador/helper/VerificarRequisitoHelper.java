/*
 *  VerificarRequisitoHelper.java
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
 *  19/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.integracion.TituloIntegracion;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Requisito;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Validacion;
import ec.com.atikasoft.proteus.vo.ValidacionTipoMovimientoRequisitoVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "verificarRequisitoHelper")
@SessionScoped
public class VerificarRequisitoHelper implements Serializable {

    /**
     * Wraper de Validacion y Tipo de movimiento requisito.
     */
    private ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoVO;

    /**
     * Wraper de Validacion y Tipo de movimiento requisito.
     */
    private ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoEditVO;

    /**
     * Lista de Wraper de Validacion y Tipo de movimiento requisito.
     */
    private List<ValidacionTipoMovimientoRequisitoVO> listaValidacionTipoMovimientoRequisitoVO;

    /**
     * Bandera para saber si es nuevo o edito.
     */
    private Boolean esNuevo;

    /**
     * Entidad de movimiento.
     */
    private Movimiento movimiento;

    /**
     * Bandera para abrir el pop up de sustento legal.
     */
    private Boolean abrirPopUpSustentoLegal;

    /**
     * variable sustento legal.
     */
    private String sustentoLegal;

    /**
     * Bandera para abrir el pop up de cargar archivo.
     */
    private Boolean abrirPopUpCargarArchivos;

    /**
     * Bandera para saber si tiene o no calificacion.
     */
    private Boolean tieneCalificacion;

    /**
     * Archivo cargado.
     */
    private UploadedFile archivoCargado;

    /**
     * Bandera para verificar segun el estado del tramite que campos voy a habilitar o no.
     */
    private Boolean camposDesabilitados;

    /**
     * Bandera para presentar el pop up de verificación de información académica.
     */
    private Boolean verPopUpInformacionAcademica;

    /**
     * Lista de Senescyt para los titulos.
     */
    private List<TituloIntegracion> listaTituloIntegracion;

    /**
     * Lista de formacion academica degun el servidor.
     */
//    private List<ServidorFormacionAcademica> listaServidorFormacionAcademica;
    /**
     * Habilitar el boto de Información académica.
     */
    private Boolean botonInformacionAcademica;

    /**
     * objeto requisito.
     */
    private Requisito requisito;
    
    /**
     * Determina si el registro de requisitos se realizará de forma masiva
     */
    private Boolean registroMasivoRequisitos;

    /**
     * Constructor por defecto.
     */
    public VerificarRequisitoHelper() {
        super();
        init();
    }

    /**
     * Metodo iniciador de variables.
     */
    public final void init() {
        validacionTipoMovimientoRequisitoVO = new ValidacionTipoMovimientoRequisitoVO();
        validacionTipoMovimientoRequisitoVO.setTipoMovimientoRequisito(new TipoMovimientoRequisito());
        validacionTipoMovimientoRequisitoVO.setValidacion(new Validacion());
        validacionTipoMovimientoRequisitoVO.setArchivo(new Archivo());
        validacionTipoMovimientoRequisitoEditVO = new ValidacionTipoMovimientoRequisitoVO();
        listaValidacionTipoMovimientoRequisitoVO = new ArrayList<ValidacionTipoMovimientoRequisitoVO>();
        esNuevo = Boolean.TRUE;
        movimiento = new Movimiento();
        abrirPopUpSustentoLegal = Boolean.FALSE;
        abrirPopUpCargarArchivos = Boolean.FALSE;
        setTieneCalificacion(Boolean.FALSE);
        setVerPopUpInformacionAcademica(Boolean.FALSE);
        setListaTituloIntegracion(new ArrayList<TituloIntegracion>());
       // setListaServidorFormacionAcademica(new ArrayList<ServidorFormacionAcademica>());
        requisito = new Requisito();
    }

    /**
     * @return the validacionTipoMovimientoRequisitoVO
     */
    public ValidacionTipoMovimientoRequisitoVO getValidacionTipoMovimientoRequisitoVO() {
        return validacionTipoMovimientoRequisitoVO;
    }

    /**
     * @param validacionTipoMovimientoRequisitoVO the validacionTipoMovimientoRequisitoVO to set
     */
    public void setValidacionTipoMovimientoRequisitoVO(
            final ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoVO) {
        this.validacionTipoMovimientoRequisitoVO = validacionTipoMovimientoRequisitoVO;
    }

    /**
     * @return the listaValidacionTipoMovimientoRequisitoVO
     */
    public List<ValidacionTipoMovimientoRequisitoVO> getListaValidacionTipoMovimientoRequisitoVO() {
        return listaValidacionTipoMovimientoRequisitoVO;
    }

    /**
     * @param listaValidacionTipoMovimientoRequisitoVO the listaValidacionTipoMovimientoRequisitoVO to set
     */
    public void setListaValidacionTipoMovimientoRequisitoVO(
            final List<ValidacionTipoMovimientoRequisitoVO> listaValidacionTipoMovimientoRequisitoVO) {
        this.listaValidacionTipoMovimientoRequisitoVO = listaValidacionTipoMovimientoRequisitoVO;
    }

    /**
     * @return the validacionTipoMovimientoRequisitoEditVO
     */
    public ValidacionTipoMovimientoRequisitoVO getValidacionTipoMovimientoRequisitoEditVO() {
        return validacionTipoMovimientoRequisitoEditVO;
    }

    /**
     * @param validacionTipoMovimientoRequisitoEditVO the validacionTipoMovimientoRequisitoEditVO to set
     */
    public void setValidacionTipoMovimientoRequisitoEditVO(
            final ValidacionTipoMovimientoRequisitoVO validacionTipoMovimientoRequisitoEditVO) {
        this.validacionTipoMovimientoRequisitoEditVO = validacionTipoMovimientoRequisitoEditVO;
    }

    /**
     * @return the esNuevo
     */
    public Boolean getEsNuevo() {
        return esNuevo;
    }

    /**
     * @param esNuevo the esNuevo to set
     */
    public void setEsNuevo(final Boolean esNuevo) {
        this.esNuevo = esNuevo;
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
     * @return the abrirPopUpSustentoLegal
     */
    public Boolean getAbrirPopUpSustentoLegal() {
        return abrirPopUpSustentoLegal;
    }

    /**
     * @param abrirPopUpSustentoLegal the abrirPopUpSustentoLegal to set
     */
    public void setAbrirPopUpSustentoLegal(final Boolean abrirPopUpSustentoLegal) {
        this.abrirPopUpSustentoLegal = abrirPopUpSustentoLegal;
    }

    /**
     * @return the tieneCalificacion
     */
    public Boolean getTieneCalificacion() {
        return tieneCalificacion;
    }

    /**
     * @param tieneCalificacion the tieneCalificacion to set
     */
    public void setTieneCalificacion(final Boolean tieneCalificacion) {
        this.tieneCalificacion = tieneCalificacion;
    }

    /**
     * @return the archivoCargado
     */
    public UploadedFile getArchivoCargado() {
        return archivoCargado;
    }

    /**
     * @param archivoCargado the archivoCargado to set
     */
    public void setArchivoCargado(final UploadedFile archivoCargado) {
        this.archivoCargado = archivoCargado;
    }

    /**
     * @return the camposDesabilitados
     */
    public Boolean getCamposDesabilitados() {
        return camposDesabilitados;
    }

    /**
     * @param camposDesabilitados the camposDesabilitados to set
     */
    public void setCamposDesabilitados(final Boolean camposDesabilitados) {
        this.camposDesabilitados = camposDesabilitados;
    }

    /**
     * @return the verPopUpInformacionAcademica
     */
    public Boolean getVerPopUpInformacionAcademica() {
        return verPopUpInformacionAcademica;
    }

    /**
     * @param verPopUpInformacionAcademica the verPopUpInformacionAcademica to set
     */
    public void setVerPopUpInformacionAcademica(final Boolean verPopUpInformacionAcademica) {
        this.verPopUpInformacionAcademica = verPopUpInformacionAcademica;
    }

    /**
     * @return the listaTituloIntegracion
     */
    public List<TituloIntegracion> getListaTituloIntegracion() {
        return listaTituloIntegracion;
    }

    /**
     * @param listaTituloIntegracion the listaTituloIntegracion to set
     */
    public void setListaTituloIntegracion(final List<TituloIntegracion> listaTituloIntegracion) {
        this.listaTituloIntegracion = listaTituloIntegracion;
    }

    /**
     * @return the listaServidorFormacionAcademica
     */
//    public List<ServidorFormacionAcademica> getListaServidorFormacionAcademica() {
//        return listaServidorFormacionAcademica;
//    }
    /**
     * @param listaServidorFormacionAcademica the listaServidorFormacionAcademica to set
     */
//    public void setListaServidorFormacionAcademica(final List<ServidorFormacionAcademica> listaServidorFormacionAcademica) {
//        this.listaServidorFormacionAcademica = listaServidorFormacionAcademica;
//    }
    /**
     * @return the botonInformacionAcademica
     */
    public Boolean getBotonInformacionAcademica() {
        return botonInformacionAcademica;
    }

    /**
     * @param botonInformacionAcademica the botonInformacionAcademica to set
     */
    public void setBotonInformacionAcademica(final Boolean botonInformacionAcademica) {
        this.botonInformacionAcademica = botonInformacionAcademica;
    }

    /**
     * @return the requisito
     */
    public Requisito getRequisito() {
        return requisito;
    }

    /**
     * @param requisito the requisito to set
     */
    public void setRequisito(final Requisito requisito) {
        this.requisito = requisito;
    }

    /**
     * @return the abrirPopUpCargarArchivos
     */
    public Boolean getAbrirPopUpCargarArchivos() {
        return abrirPopUpCargarArchivos;
    }

    /**
     * @param abrirPopUpCargarArchivos the abrirPopUpCargarArchivos to set
     */
    public void setAbrirPopUpCargarArchivos(final Boolean abrirPopUpCargarArchivos) {
        this.abrirPopUpCargarArchivos = abrirPopUpCargarArchivos;
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

    public Boolean getRegistroMasivoRequisitos() {
        return registroMasivoRequisitos;
    }

    public void setRegistroMasivoRequisitos(Boolean registroMasivoRequisitos) {
        this.registroMasivoRequisitos = registroMasivoRequisitos;
    }
    
    
}
