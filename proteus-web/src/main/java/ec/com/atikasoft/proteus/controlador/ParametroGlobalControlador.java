/*
 *  ParametroGlobalControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ParametroGlobalHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.TipoParametroGlobalEnum;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador para la administración de Parametro Global.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "parametroGlobalBean")
@ViewScoped
public class ParametroGlobalControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/parametro_global/parametro_global.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/parametro_global/lista_parametro_global.jsf";

    /**
     * Servicio de administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{parametroGlobalHelp}")
    private ParametroGlobalHelper parametroGlobalHelper;

    /**
     * Constructor sin parametros.
     */
    public ParametroGlobalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(parametroGlobalHelper);
        iniciarComboTipo();
        buscar();
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        parametroGlobalHelper.getTipoParametro().clear();
        for (TipoParametroGlobalEnum t : TipoParametroGlobalEnum.values()) {
            parametroGlobalHelper.getTipoParametro().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoParametro(final String codigo) {
        return TipoParametroGlobalEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String guardar() {
        try {

            if (parametroGlobalHelper.getEsNuevo()) {
                if (administracionServicio.buscarParametroGlobalPorNemonico(
                        parametroGlobalHelper.getParametroGlobal().getNemonico()) == null) {
                    administracionServicio.guardarParametroGlobal(parametroGlobalHelper.getParametroGlobal());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else {
                    mostrarMensajeEnPantalla(NEMONICO_DUPLICADO, FacesMessage.SEVERITY_WARN);
                }
            } else {
                administracionServicio.actulizarParametroGlobal(parametroGlobalHelper.getParametroGlobal());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            // actualizar cache
            List<ParametroGlobal> parametros = administracionServicio.buscarParametrosGlobales();
            getSession().getServletContext().setAttribute(CacheEnum.PARAMETROS_GLOBALES.getCodigo(), parametros);
        } catch (Exception e) {
            error(getClass().getName(), "Erro al guardar el parametro", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(parametroGlobalHelper.getParametroGlobalEditDelete());
            ParametroGlobal pg = (ParametroGlobal) cloneBean;
            iniciarDatosEntidad(pg, Boolean.FALSE);
            parametroGlobalHelper.setParametroGlobal(pg);
            parametroGlobalHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        parametroGlobalHelper.setParametroGlobal(new ParametroGlobal());
        iniciarDatosEntidad(parametroGlobalHelper.getParametroGlobal(), Boolean.TRUE);
        parametroGlobalHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            administracionServicio.eliminarParametroGlobal(parametroGlobalHelper.getParametroGlobalEditDelete());
            parametroGlobalHelper.getListaParametroGlobal().
                    remove(parametroGlobalHelper.getParametroGlobalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * @return the parametroGlobalHelper
     */
    public ParametroGlobalHelper getParametroGlobalHelper() {
        return parametroGlobalHelper;
    }

    /**
     * @param parametroGlobalHelper the parametroGlobalHelper to set
     */
    public void setParametroGlobalHelper(final ParametroGlobalHelper parametroGlobalHelper) {
        this.parametroGlobalHelper = parametroGlobalHelper;
    }

    @Override
    public String buscar() {
        try {
            parametroGlobalHelper.getListaParametroGlobal().clear();
            parametroGlobalHelper.setListaParametroGlobal(
                    administracionServicio.listarTodosParametroGlobalPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }
}
