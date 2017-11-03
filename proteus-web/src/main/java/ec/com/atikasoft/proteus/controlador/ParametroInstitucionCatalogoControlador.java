/*
 *  ParametroInstitucionCatalogoControlador.java
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
 *  05/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ParametroInstitucionCatalogoHelper;
import ec.com.atikasoft.proteus.enums.TipoParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucionalCatalogo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
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
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "parametroInstitucionCatalogoBean")
@ViewScoped
public class ParametroInstitucionCatalogoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ParametroInstitucionCatalogoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/parametro_institucion_catalogo"
            + "/parametro_institucion_catalogo.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/parametro_institucion_catalogo"
            + "/lista_parametro_institucion_catalogo.jsf";

    /**
     * variable del servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{parametroInstitucionCatalogoHelper}")
    private ParametroInstitucionCatalogoHelper parametroInstitucionCatalogoHelper;

    public ParametroInstitucionCatalogoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(parametroInstitucionCatalogoHelper);
        buscar();
        iniciarComboTipo();
        getCatalogoHelper().setCampoBusqueda("");
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    public void iniciarComboTipo() {
        parametroInstitucionCatalogoHelper.getTipoParametro().clear();
        for (TipoParametroInstitucionCatalogoEnum t : TipoParametroInstitucionCatalogoEnum.values()) {
            parametroInstitucionCatalogoHelper.getTipoParametro().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoParametro(final String codigo) {
        return TipoParametroInstitucionCatalogoEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String guardar() {
        try {
            if (getParametroInstitucionCatalogoHelper().getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdmServicio().guardarParametroInstitucionalCatalogo(
                            getParametroInstitucionCatalogoHelper().getParametroInstitucionalCatalogo());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                getAdmServicio().actualizarParametroInstitucionalCatalogo(
                        getParametroInstitucionCatalogoHelper().getParametroInstitucionalCatalogo());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(parametroInstitucionCatalogoHelper.getParametroInstitucionalCatalogoEditDelete());
            ParametroInstitucionalCatalogo d = (ParametroInstitucionalCatalogo) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            parametroInstitucionCatalogoHelper.setParametroInstitucionalCatalogo(d);
            parametroInstitucionCatalogoHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        parametroInstitucionCatalogoHelper.setParametroInstitucionalCatalogo(new ParametroInstitucionalCatalogo());
        iniciarDatosEntidad(parametroInstitucionCatalogoHelper.getParametroInstitucionalCatalogo(), Boolean.TRUE);
        parametroInstitucionCatalogoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((admServicio.tieneRestricciones("parametroInstitucionalCatalogo.id",
                    "ParametroInstitucionalCatalogo", "ParametroInstitucional",
                    parametroInstitucionCatalogoHelper.getParametroInstitucionalCatalogoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Parametro Institucional,");
            }
            if (cont == 0) {
                admServicio.eliminarParametroInstitucionalCatalogo(getParametroInstitucionCatalogoHelper().
                        getParametroInstitucionalCatalogoEditDelete());
                getParametroInstitucionCatalogoHelper().getListaParametroInstitucionalCatalogo().
                        remove(getParametroInstitucionCatalogoHelper().getParametroInstitucionalCatalogoEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR, mensaje);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            parametroInstitucionCatalogoHelper.getListaParametroInstitucionalCatalogo().clear();
            parametroInstitucionCatalogoHelper.setListaParametroInstitucionalCatalogo(
                    admServicio.listarTodosParametroInstitucionalCatalogoPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
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
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            getParametroInstitucionCatalogoHelper().getListaAlertaNemonico().clear();
            getParametroInstitucionCatalogoHelper().setListaAlertaNemonico(
                    admServicio.listarTodosParametroInstitucionalCatalogoPorNemonico(
                    getParametroInstitucionCatalogoHelper().getParametroInstitucionalCatalogo().getNemonico()));
            if (getParametroInstitucionCatalogoHelper().getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the parametroInstitucionCatalogoHelper
     */
    public ParametroInstitucionCatalogoHelper getParametroInstitucionCatalogoHelper() {
        return parametroInstitucionCatalogoHelper;
    }

    /**
     * @param parametroInstitucionCatalogoHelper the parametroInstitucionCatalogoHelper to set
     */
    public void setParametroInstitucionCatalogoHelper(ParametroInstitucionCatalogoHelper parametroInstitucionCatalogoHelper) {
        this.parametroInstitucionCatalogoHelper = parametroInstitucionCatalogoHelper;
    }
}
