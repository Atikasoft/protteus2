/*
 *  MetadataTablaControlador.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.MetadataTablaHelper;
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * MetadataTabla
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "metadataTablaBean")
@ViewScoped
public class MetadataTablaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(MetadataTablaControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/metadata_tabla_columna/metadata_tabla.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/metadata_tabla_columna/lista_metadata_tabla.jsf";
    
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD_HIJA = "/pages/administracion/metadata_tabla_columna/lista_metadata_columna.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{metadataTablaHelper}")
    private MetadataTablaHelper metadataTablaHelper;

    /**
     * Constructor por defecto.
     */
    public MetadataTablaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(metadataTablaHelper);
        setMetadataTablaHelper(metadataTablaHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String buscar() {
        try {
            metadataTablaHelper.setListaMetadataTabla(new ArrayList<MetadataTabla>());
           metadataTablaHelper.setListaMetadataTabla(
                    admServicio.listarMetadataTablasPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

     /**
     * Permite navegar desde la lista hacia el detalle
     * @return 
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }
    
    public String irListaMetadataColumnas() {
        return LISTA_ENTIDAD_HIJA;
    }
    
       /**
     * Permite navegar desde la lista hacia el detalle
     * @return 
     */
    public String irFormulario() {
       return  FORMULARIO_ENTIDAD;
    }
    
    public String guardar() {

        return null;
    }

    public String iniciarEdicion() {

        return FORMULARIO_ENTIDAD;
    }

    public String iniciarNuevo() {

        return FORMULARIO_ENTIDAD;
    }

    public String borrar() {
        return null;
    }

    /**
     * @return the metadataTablaHelper
     */
    public MetadataTablaHelper getMetadataTablaHelper() {
        return metadataTablaHelper;
    }

    /**
     * @param metadataTablaHelper the metadataTablaHelper to set
     */
    public void setMetadataTablaHelper(MetadataTablaHelper metadataTablaHelper) {
        this.metadataTablaHelper = metadataTablaHelper;
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }
}
