/*
 *  DatoAdicionalControlador.java
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

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.DatoAdicionalHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoAdicionalEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoEnum;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.List;
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
 * DatoAdicional
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "datoAdicionalBean")
@ViewScoped
public class DatoAdicionalControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(DatoAdicionalControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/dato_adicional/dato_adicional.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/dato_adicional/lista_dato_adicional.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{datoAdicionalHelper}")
    private DatoAdicionalHelper datoAdicionalHelper;

    /**
     * Constructor por defecto.
     */
    public DatoAdicionalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(datoAdicionalHelper);
        setDatoAdicionalHelper(datoAdicionalHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComboTipoDato();
        iniciarComboTipo();
    }

    @Override
    public String guardar() {
        try {
            String codigoTemp = datoAdicionalHelper.getDatoAdicional().
                            getCodigo();
            
            if (datoAdicionalHelper.getEsNuevo()) {
                datoAdicionalHelper.getDatoAdicional().setCodigo("D_" + datoAdicionalHelper.getDatoAdicional().
                            getCodigo());
                if (validarCodigo()) {
                     datoAdicionalHelper.getDatoAdicional().setCodigo(codigoTemp);
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    
                    admServicio.guardarDatoAdicional(datoAdicionalHelper.getDatoAdicional());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                admServicio.actualizarDatoAdicional(datoAdicionalHelper.getDatoAdicional());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<DatoAdicional> datosAdicionales = admServicio.listarTodasDatoAdicionalPorNombre(null);
            getSession().getServletContext().setAttribute(CacheEnum.DATOS_ADICIONALES.getCodigo(), datosAdicionales);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            datoAdicionalHelper.getListaDatoAdicionalCodigo().clear();
            datoAdicionalHelper.setListaDatoAdicionalCodigo(admServicio.listarTodosDatoAdicionalPorCodigo(
                    datoAdicionalHelper.getDatoAdicional().getCodigo()));
            if (datoAdicionalHelper.getListaDatoAdicionalCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del código", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(datoAdicionalHelper.getDatoAdicionalEditDelete());
            DatoAdicional d = (DatoAdicional) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            datoAdicionalHelper.setDatoAdicional(d);
            datoAdicionalHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        datoAdicionalHelper.setDatoAdicional(new DatoAdicional());
        datoAdicionalHelper.iniciador();
        iniciarDatosEntidad(datoAdicionalHelper.getDatoAdicional(), Boolean.TRUE);
        datoAdicionalHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboTipoDato();
        iniciarComboTipo();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarDatoAdicional(datoAdicionalHelper.getDatoAdicionalEditDelete());
            datoAdicionalHelper.getListaDatosAdicionales().
                    remove(datoAdicionalHelper.getDatoAdicionalEditDelete());
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
            datoAdicionalHelper.getListaDatosAdicionales().clear();
            datoAdicionalHelper.setListaDatoAdicionales(
                    admServicio.listarTodasDatoAdicionalPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
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
        datoAdicionalHelper.getTipo().clear();
        for (TipoDatoAdicionalEnum tp : TipoDatoAdicionalEnum.values()) {
            datoAdicionalHelper.getTipo().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo de dato.
     */
    private void iniciarComboTipoDato() {
        datoAdicionalHelper.getTipoDato().clear();
        for (TipoDatoEnum tp : TipoDatoEnum.values()) {
            datoAdicionalHelper.getTipoDato().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipo(final String codigo) {
        return TipoDatoAdicionalEnum.obtenerDescripcion(codigo);
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

    /**
     * @return the datoAdicionalHelper
     */
    public DatoAdicionalHelper getDatoAdicionalHelper() {
        return datoAdicionalHelper;
    }

    /**
     * @param datoAdicionalHelper the datoAdicionalHelper to set
     */
    public void setDatoAdicionalHelper(DatoAdicionalHelper datoAdicionalHelper) {
        this.datoAdicionalHelper = datoAdicionalHelper;
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
