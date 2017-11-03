/*
 *  CampoAccesoHelper.java
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
 *  04/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.CampoAcceso;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * CampoAcceso
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "campoAccesoHelper")
@SessionScoped
public class CampoAccesoHelper extends CatalogoHelper {

    /**
     * clase campoAcceso.
     */
    private CampoAcceso campoAcceso;
    
    /**
     * campo para identificar la metada tabla
     */
    private MetadataTabla metadataTablaAux;
    /**
     * clase campoAcceso puesto para editar.
     */
    private CampoAcceso campoAccesoEditDelete;
    /**
     * lista de campoAccesos.
     */
    private List<CampoAcceso> listaCamposAcceso;
    /**
     * Variable para listar campos de acceso por nombre
     */
    private List<CampoAcceso> listaCampoAccesoNombre;
    /**
     * Lista de tipos de periodos.
     */
    private List<SelectItem> tipo;
    
     /**
     * Lista de metadataTabla para los combos
     */
    private List<SelectItem> metadataTabla;
    
     /**
     * Lista de metadataColumnas para los combos
     */
    private List<SelectItem> metadataColumna;
    
       /**
     * Variable para listar la metadataTabla 
     */
    private List<MetadataTabla> listaMetadataTabla;
         /**
     * Variable para listar la metadataColumna 
     */
    private List<MetadataColumna> listaMetadataColumna;
    /**
     * Constructor por defecto.
     */
    public CampoAccesoHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del CampoAccesoHelper.
     */
    public final void iniciador() {
        setCampoAcceso(new CampoAcceso());
        setCampoAccesoEditDelete(new CampoAcceso());
        setListaCampoAccesoes(new ArrayList<CampoAcceso>());
        setListaCampoAccesoNombre(new ArrayList<CampoAcceso>());
        setTipo(new ArrayList<SelectItem>());
        setMetadataTabla(new ArrayList<SelectItem>());
        setMetadataColumna(new ArrayList<SelectItem>());
        setListaMetadataTabla(new ArrayList<MetadataTabla>());
        setListaMetadataColumna(new ArrayList<MetadataColumna>());
        setMetadataTablaAux(new MetadataTabla());
    }

    /**
     * @return the campoAcceso
     */
    public CampoAcceso getCampoAcceso() {
        return campoAcceso;
    }

    /**
     * @param campoAcceso the campoAcceso to set
     */
    public void setCampoAcceso(final CampoAcceso campoAcceso) {
        this.campoAcceso = campoAcceso;
    }

    /**
     * @return the campoAccesoEditDelete
     */
    public CampoAcceso getCampoAccesoEditDelete() {
        return campoAccesoEditDelete;
    }

    /**
     * @param campoAccesoEditDelete the campoAccesoEditDelete to set
     */
    public void setCampoAccesoEditDelete(final CampoAcceso campoAccesoEditDelete) {
        this.campoAccesoEditDelete = campoAccesoEditDelete;
    }

    /**
     * @return the listaCampoAccesos
     */
    public List<CampoAcceso> getListaCamposAcceso() {
        return listaCamposAcceso;
    }

    /**
     * @param listaCampoAccesos the listaCampoAccesos to set
     */
    public void setListaCampoAccesoes(final List<CampoAcceso> listaCamposAccesos) {
        this.listaCamposAcceso = listaCamposAccesos;
    }

    /**
     * @return the listaCampoAccesoNombre
     */
    public List<CampoAcceso> getListaCampoAccesoNombre() {
        return listaCampoAccesoNombre;
    }

    /**
     * @param listaCampoAccesoNombre the listaCampoAccesoNombre to set
     */
    public void setListaCampoAccesoNombre(final List<CampoAcceso> listaCampoAccesoNombre) {
        this.listaCampoAccesoNombre = listaCampoAccesoNombre;
    }

    /**
     * @return the tipo
     */
    public List<SelectItem> getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(List<SelectItem> tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the metadataTabla
     */
    public List<SelectItem> getMetadataTabla() {
        return metadataTabla;
    }

    /**
     * @param metadataTabla the metadataTabla to set
     */
    public void setMetadataTabla(List<SelectItem> metadataTabla) {
        this.metadataTabla = metadataTabla;
    }

    /**
     * @return the metadataColumna
     */
    public List<SelectItem> getMetadataColumna() {
        return metadataColumna;
    }

    /**
     * @param metadataColumna the metadataColumna to set
     */
    public void setMetadataColumna(List<SelectItem> metadataColumna) {
        this.metadataColumna = metadataColumna;
    }

    /**
     * @return the listaMetadataTabla
     */
    public List<MetadataTabla> getListaMetadataTabla() {
        return listaMetadataTabla;
    }

    /**
     * @param listaMetadataTabla the listaMetadataTabla to set
     */
    public void setListaMetadataTabla(List<MetadataTabla> listaMetadataTabla) {
        this.listaMetadataTabla = listaMetadataTabla;
    }

    /**
     * @return the listaMetadataColumna
     */
    public List<MetadataColumna> getListaMetadataColumna() {
        return listaMetadataColumna;
    }

    /**
     * @param listaMetadataColumna the listaMetadataColumna to set
     */
    public void setListaMetadataColumna(List<MetadataColumna> listaMetadataColumna) {
        this.listaMetadataColumna = listaMetadataColumna;
    }

    /**
     * @return the metadataTablaAux
     */
    public MetadataTabla getMetadataTablaAux() {
        return metadataTablaAux;
    }

    /**
     * @param metadataTablaAux the metadataTablaAux to set
     */
    public void setMetadataTablaAux(MetadataTabla metadataTablaAux) {
        this.metadataTablaAux = metadataTablaAux;
    }

}
