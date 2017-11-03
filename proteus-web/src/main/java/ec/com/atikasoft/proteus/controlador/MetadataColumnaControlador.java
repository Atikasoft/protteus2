/*
 *  MetadataColumnaControlador.java
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

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.MetadataColumnaHelper;
import ec.com.atikasoft.proteus.controlador.helper.MetadataTablaHelper;
import ec.com.atikasoft.proteus.enums.TipoDatoEnum;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * MetadataColumna
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "metadataColumnaBean")
@ViewScoped
public class MetadataColumnaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(MetadataColumnaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/metadata_tabla_columna/metadata_columna.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/metadata_tabla_columna/lista_metadata_columna.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD_PADRE =
            "/pages/administracion/metadata_tabla_columna/lista_metadata_tabla.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{metadataColumnaHelper}")
    private MetadataColumnaHelper metadataColumnaHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{metadataTablaHelper}")
    private MetadataTablaHelper metadataTablaHelper;

    /**
     * Constructor por defecto.
     */
    public MetadataColumnaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getMetadataColumnaHelper());
        setMetadataColumnaHelper(getMetadataColumnaHelper());
        buscarPorMetadataTabla();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String buscar() {
        try {
            getMetadataColumnaHelper().getListaMetadataColumna().clear();
            getMetadataColumnaHelper().setListaMetadataColumna(
                    admServicio.listarMetadataColumnasPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    public String buscarPorMetadataTabla() {
        try {
            getMetadataColumnaHelper().setListaMetadataColumna(
                    admServicio.listarMetadataColumnasPorIdMetadataTabla(
                    metadataTablaHelper.getMetadataTabla().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Permite navegar desde la lista hacia el detalle
     *
     * @return
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Permite navegar desde la lista hacia el detalle
     *
     * @return
     */
    public String irFormulario() {
        return FORMULARIO_ENTIDAD;
    }

    public String irListaMetadataTablas() {
        return LISTA_ENTIDAD_PADRE;
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

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDato(final String codigo) {
        return TipoDatoEnum.obtenerDescripcion(codigo);
    }

    public String borrar() {
        return null;
    }

    /**
     * @return the metadataColumnaHelper
     */
    public MetadataColumnaHelper getMetadataColumnaHelper() {
        return metadataColumnaHelper;
    }

    /**
     * @param metadataColumnaHelper the metadataColumnaHelper to set
     */
    public void setMetadataColumnaHelper(MetadataColumnaHelper metadataColumnaHelper) {
        this.metadataColumnaHelper = metadataColumnaHelper;
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
}
