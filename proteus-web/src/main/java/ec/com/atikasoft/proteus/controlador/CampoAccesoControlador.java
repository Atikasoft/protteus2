/*
 *  CampoAccesoControlador.java
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
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CampoAccesoHelper;
import ec.com.atikasoft.proteus.enums.TipoCampoAccesoEnum;
import ec.com.atikasoft.proteus.modelo.CampoAcceso;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * CampoAcceso
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "campoAccesoBean")
@ViewScoped
public class CampoAccesoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CampoAccesoControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/campo_acceso/campo_acceso.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/campo_acceso/lista_campo_acceso.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{campoAccesoHelper}")
    private CampoAccesoHelper campoAccesoHelper;

    /**
     * Constructor por defecto.
     */
    public CampoAccesoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(campoAccesoHelper);
        setCampoAccesoHelper(campoAccesoHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComboTipo();
        iniciarComboMetadataTabla();
    }

    @Override
    public String guardar() {
        try {

            if (campoAccesoHelper.getEsNuevo()) {
                if (validarNombre()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarCampoAcceso(campoAccesoHelper.getCampoAcceso());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                admServicio.actualizarCampoAcceso(campoAccesoHelper.getCampoAcceso());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayNombre Boolean.
     */
    public Boolean validarNombre() {
        Boolean hayNombre = true;
        try {
            campoAccesoHelper.getListaCampoAccesoNombre().clear();
            campoAccesoHelper.setListaCampoAccesoNombre(admServicio.listarCampoAccesoPorNombre(
                    campoAccesoHelper.getCampoAcceso().getNombre(), true));
            if (campoAccesoHelper.getListaCampoAccesoNombre().isEmpty()) {
                hayNombre = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nombre", ex);
        }
        return hayNombre;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(campoAccesoHelper.getCampoAccesoEditDelete());
            CampoAcceso d = (CampoAcceso) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            campoAccesoHelper.setCampoAcceso(d);
            campoAccesoHelper.setEsNuevo(Boolean.FALSE);
            campoAccesoHelper.setMetadataTablaAux(campoAccesoHelper.getCampoAcceso().getMetadataColumna().getMetadataTabla());
            iniciarComboMetadataColumna();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        campoAccesoHelper.setCampoAcceso(new CampoAcceso());
        campoAccesoHelper.iniciador();
        iniciarDatosEntidad(campoAccesoHelper.getCampoAcceso(), Boolean.TRUE);
        campoAccesoHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboTipo();
        iniciarComboMetadataTabla();
        campoAccesoHelper.setMetadataTablaAux(new MetadataTabla());
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarCampoAcceso(campoAccesoHelper.getCampoAccesoEditDelete());
            campoAccesoHelper.getListaCamposAcceso().
                    remove(campoAccesoHelper.getCampoAccesoEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            campoAccesoHelper.getListaCamposAcceso().clear();
            campoAccesoHelper.setListaCampoAccesoes(
                    admServicio.listarCampoAccesoPorNombre(
                    getCatalogoHelper().getCampoBusqueda(), false));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo .
     */
    private void iniciarComboTipo() {
        campoAccesoHelper.getTipo().clear();
        for (TipoCampoAccesoEnum tp : TipoCampoAccesoEnum.values()) {
            campoAccesoHelper.getTipo().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de metadataTabla .
     */
    private void iniciarComboMetadataTabla() {
        try {
            campoAccesoHelper.getListaMetadataTabla().clear();
            campoAccesoHelper.setListaMetadataTabla(
                    admServicio.listarMetadataTablasPorNombre(null));
            campoAccesoHelper.getMetadataTabla().clear();
            for (MetadataTabla tp : campoAccesoHelper.getListaMetadataTabla()) {
                campoAccesoHelper.getMetadataTabla().add(new SelectItem(tp.getId(), tp.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda metadataTabla", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de metadataColumna .
     */
    public void iniciarComboMetadataColumna() {
        try {

            if (campoAccesoHelper.getMetadataTablaAux().getId() != null) {
                campoAccesoHelper.getListaMetadataColumna().clear();

                campoAccesoHelper.setListaMetadataColumna(
                        admServicio.listarMetadataColumnasPorIdMetadataTabla(
                        campoAccesoHelper.getMetadataTablaAux().getId()));
                campoAccesoHelper.getMetadataColumna().clear();
                for (MetadataColumna tp : campoAccesoHelper.getListaMetadataColumna()) {
                    campoAccesoHelper.getMetadataColumna().add(new SelectItem(tp.getId(), tp.getNombre()));
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda metadataColumna", ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipo(final String codigo) {
        return TipoCampoAccesoEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the campoAccesoHelper
     */
    public CampoAccesoHelper getCampoAccesoHelper() {
        return campoAccesoHelper;
    }

    /**
     * @param campoAccesoHelper the campoAccesoHelper to set
     */
    public void setCampoAccesoHelper(CampoAccesoHelper campoAccesoHelper) {
        this.campoAccesoHelper = campoAccesoHelper;
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
