/*
 *  VacacionParametroControlador.java
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
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.VacacionParametroHelper;
import ec.com.atikasoft.proteus.enums.TipoAcumulacionEnum;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.modelo.MetadataColumna;
import ec.com.atikasoft.proteus.modelo.MetadataTabla;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import java.math.BigDecimal;
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
 * VacacionParametro
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionParametroBean")
@ViewScoped
public class VacacionParametroControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(VacacionParametroControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/vacacion/vacacion_parametro.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/vacacion/lista_vacacion_parametro.jsf";
    /**
     * Servicio de regimen laboral.
     */
    @EJB
    private RegimenLaboralServicio regimenServicio;
    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{vacacionParametroHelper}")
    private VacacionParametroHelper vacacionParametroHelper;

    /**
     * Constructor por defecto.
     */
    public VacacionParametroControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(vacacionParametroHelper);
        setVacacionParametroHelper(vacacionParametroHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComboTipoAcumulacion();
        iniciarComboRegimenLaboral();
    }

    @Override
    public String guardar() {
        try {

            if (vacacionParametroHelper.getEsNuevo()) {
                if(validarRegimen()){
                     mostrarMensajeEnPantalla("Ya existe la configuración para ese Régimen Laboral", FacesMessage.SEVERITY_WARN);
                     return null;
                }
                if (validarNombre()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    vacacionServicio.guardarVacacionParametro(vacacionParametroHelper.getVacacionParametro());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }

            } else {
                vacacionServicio.actualizarVacacionParametro(vacacionParametroHelper.getVacacionParametro());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el nombre.
     *
     * @return hayNombre Boolean.
     */
    public Boolean validarNombre() {
        Boolean hayNombre = true;
        try {
            vacacionParametroHelper.getListaVacacionParametroNombre().clear();
            vacacionParametroHelper.setListaVacacionParametroNombre(vacacionServicio.listarTodosVacacionParametroPorNombreDuplicado(
                    vacacionParametroHelper.getVacacionParametro().getNombre()));
            if (vacacionParametroHelper.getListaVacacionParametroNombre().isEmpty()) {
                hayNombre = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nombre", ex);
        }
        return hayNombre;
    }
    /**
     * metodo para validar si ya configuracion para ese regimen.
     *
     * @return hayRegimen Boolean.
     */
    public Boolean validarRegimen() {
        Boolean hayRegimen = true;
        try {
            vacacionParametroHelper.getListaVacacionParametroNombre().clear();
            vacacionParametroHelper.setListaVacacionParametroNombre(vacacionServicio.listarTodosVacacionParametroPorRegimenLaboral(
                    vacacionParametroHelper.getVacacionParametro().getIdRegimenLaboral()));
            if (vacacionParametroHelper.getListaVacacionParametroNombre().isEmpty()) {
                hayRegimen = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nombre", ex);
        }
        return hayRegimen;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean =
                    BeanUtils.cloneBean(vacacionParametroHelper.getVacacionParametroEditDelete());
            VacacionParametro d = (VacacionParametro) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            vacacionParametroHelper.setVacacionParametro(d);
            vacacionParametroHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        vacacionParametroHelper.setVacacionParametro(new VacacionParametro());
        vacacionParametroHelper.iniciador();
        iniciarDatosEntidad(vacacionParametroHelper.getVacacionParametro(), Boolean.TRUE);
        vacacionParametroHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboTipoAcumulacion();
        iniciarComboRegimenLaboral();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            vacacionServicio.eliminarVacacionParametro(vacacionParametroHelper.getVacacionParametroEditDelete());
            vacacionParametroHelper.getListaVacacionParametros().
                    remove(vacacionParametroHelper.getVacacionParametroEditDelete());
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
            vacacionParametroHelper.getListaVacacionParametros().clear();
            vacacionParametroHelper.setListaVacacionParametros(
                    vacacionServicio.listarTodosVacacionParametroPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo acumulacion.
     */
    private void iniciarComboTipoAcumulacion() {
        vacacionParametroHelper.getOpcionTipoAcumulacion().clear();
        iniciarCombos(vacacionParametroHelper.getOpcionTipoAcumulacion());
        for (TipoAcumulacionEnum tp : TipoAcumulacionEnum.values()) {
            vacacionParametroHelper.getOpcionTipoAcumulacion().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de regimenes laborales .
     */
    public void iniciarComboRegimenLaboral() {
        try {
            List<RegimenLaboral> lista;
            lista = regimenServicio.listarTodosRegimenVigentes();
            iniciarCombos(vacacionParametroHelper.getOpcionRegimenLaboral());
            for (RegimenLaboral tp : lista) {
                vacacionParametroHelper.getOpcionRegimenLaboral().add(new SelectItem(tp.getId(), tp.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda regimen laboral", ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * acumulacion
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoAcumulacion(final String codigo) {
        return TipoAcumulacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the vacacionParametroHelper
     */
    public VacacionParametroHelper getVacacionParametroHelper() {
        return vacacionParametroHelper;
    }

    /**
     * @param vacacionParametroHelper the vacacionParametroHelper to set
     */
    public void setVacacionParametroHelper(VacacionParametroHelper vacacionParametroHelper) {
        this.vacacionParametroHelper = vacacionParametroHelper;
    }

    /**
     * @return the vacacionServicio
     */
    public VacacionServicio getVacacionServicio() {
        return vacacionServicio;
    }

    /**
     * @param vacacionServicio the vacacionServicio to set
     */
    public void setVacacionServicio(VacacionServicio vacacionServicio) {
        this.vacacionServicio = vacacionServicio;
    }
}
