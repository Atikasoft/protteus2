/*
 *  ConstanteControlador.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConstanteHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoEnum;
import ec.com.atikasoft.proteus.modelo.Constante;
import ec.com.atikasoft.proteus.modelo.Rubro;
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
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "constanteBean")
@ViewScoped
public class ConstanteControlador extends CatalogoControlador {

    /**
     * Logger de constantes.
     */
    private Logger LOG = Logger.getLogger(ConstanteControlador.class.getCanonicalName());

    /**
     * Constante de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/constante/constante.jsf";

    /**
     * Constante de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/constante/lista_constante.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{constanteHelper}")
    private ConstanteHelper constanteHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(constanteHelper);
        setConstanteHelper(constanteHelper);
        iniciarComboTipoDato();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    /**
     * Este metodo llena las opciones para el combo de tipo dato.
     */
    private void iniciarComboTipoDato() {
        iniciarCombos(constanteHelper.getTipoDato());
        for (TipoDatoEnum t : TipoDatoEnum.values()) {
            constanteHelper.getTipoDato().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo periodo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDato(final String codigo) {
        return TipoDatoEnum.obtenerDescripcion(codigo);
    }

    public ConstanteControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
             String codigoTemp = constanteHelper.getConstante().
                            getCodigo();
            if (constanteHelper.getEsNuevo()) {
                  constanteHelper.getConstante().setCodigo(constanteHelper.getPrefijoCodigo() + constanteHelper.
                            getConstante().getCodigo());
                if (validarCodigo()) {
                     constanteHelper.getConstante().setCodigo(codigoTemp);
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                  
                    admServicio.guardarConstante(constanteHelper.getConstante());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarConstante(constanteHelper.getConstante());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<Constante> constantes = admServicio.listarTodasConstantePorNombre(null);
            getSession().getServletContext().setAttribute(CacheEnum.CONSTANTES.getCodigo(), constantes);
            List<Rubro> rubros = admServicio.listarTodosRubrosVigentes();
            getSession().getServletContext().setAttribute(CacheEnum.RUBROS.getCodigo(), rubros);
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
                    BeanUtils.cloneBean(constanteHelper.getConstanteEditDelete());
            Constante re = (Constante) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            constanteHelper.setConstante(re);
            constanteHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        constanteHelper.setConstante(new Constante());
        iniciarDatosEntidad(constanteHelper.getConstante(), Boolean.TRUE);
        constanteHelper.setEsNuevo(Boolean.TRUE);
        constanteHelper.setPrefijoCodigo("C_");
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarConstante(constanteHelper.getConstanteEditDelete());
            constanteHelper.getListaConstante().
                    remove(constanteHelper.getConstanteEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            constanteHelper.getListaConstante().clear();
            constanteHelper.setListaConstante(
                    admServicio.listarTodasConstantePorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para validar si ya existe el codigo.
     *
     * @return haycodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean haycodigo = true;
        try {
            constanteHelper.getListaConstanteCodigo().clear();
            constanteHelper.setListaConstanteCodigo(
                    admServicio.listarTodosConstantePorCodigo(
                    constanteHelper.getConstante().getCodigo()));
            if (constanteHelper.getListaConstanteCodigo().isEmpty()) {
                haycodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del cddigo", ex);
        }
        return haycodigo;
    }

    public void OnTipoDatoSeleccionado() {
        char opcion = 'A';
        if (constanteHelper.getConstante().getTipo() != null) {
            opcion = constanteHelper.getConstante().getTipo().charAt(0);
        }
        switch (opcion) {
            case 'T':
                constanteHelper.getConstante().setValorFecha(null);
                constanteHelper.getConstante().setValorNumerico(null);
                break;
            case 'N':
                constanteHelper.getConstante().setValorFecha(null);
                constanteHelper.getConstante().setValorTexto(null);
                break;
            case 'F':
                constanteHelper.getConstante().setValorTexto(null);
                constanteHelper.getConstante().setValorNumerico(null);
                break;
            default:
                constanteHelper.getConstante().setValorFecha(null);
                constanteHelper.getConstante().setValorNumerico(null);
                constanteHelper.getConstante().setValorTexto(null);
                break;
        }
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
     * @return the constanteHelper
     */
    public ConstanteHelper getConstanteHelper() {
        return constanteHelper;
    }

    /**
     * @param constanteHelper the constanteHelper to set
     */
    public void setConstanteHelper(ConstanteHelper constanteHelper) {
        this.constanteHelper = constanteHelper;
    }
}
