/*
 *
 *  Quito - Ecuador
 *
 *  19/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ModalidadLaboralHelper;
import ec.com.atikasoft.proteus.enums.TipoModalidadEnum;
import ec.com.atikasoft.proteus.modelo.ModalidadLaboral;
import ec.com.atikasoft.proteus.modelo.ModalidadNivelOcupacional;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.ArrayList;
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
 * Controlador para la Administracion de ModalidadLaborals.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@ManagedBean(name = "modalidadLaboralBean")
@ViewScoped
public class ModalidadLaboralControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ModalidadLaboralControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion"
            + "/modalidad_laboral/modalidad_laboral.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion"
            + "/modalidad_laboral/lista_modalidad_laboral.jsf";
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{modalidadLaboralHelper}")
    private ModalidadLaboralHelper modalidadLaboralHelper;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(modalidadLaboralHelper);
        buscar();
        iniciarComboTipoModalidad();
        buscarNivelesOcupacionales();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public String guardar() {
        try {
            if (getModalidadLaboralHelper().getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    getadministracionServicio().guardarModalidadLaboral(getModalidadLaboralHelper().getModalidadLaboral());
                    asignarNivelesOcupacionales();
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

                }
            } else {
                getadministracionServicio().actualizarModalidadLaboral(getModalidadLaboralHelper().getModalidadLaboral());
                asignarNivelesOcupacionales();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Permite asignar a la modalidad laboral los niveles ocupacionales 1.-
     * Eliminar lógicamente los registros modalidad laboral - niveles
     * ocupacionales de la Modalidad 2.- Insertar los nuevos registros
     * seleccionados
     */
    public void asignarNivelesOcupacionales() {

        try {
            if (modalidadLaboralHelper.getModalidadLaboral() != null 
                    && modalidadLaboralHelper.getModalidadLaboral().getListaModalidadNivelOcupacional()!=null) {
                for (ModalidadNivelOcupacional reg : modalidadLaboralHelper.getModalidadLaboral().getListaModalidadNivelOcupacional()) {
                    iniciarDatosEntidad(reg, Boolean.FALSE);
                    if (reg.getVigente()) {
                        administracionServicio.eliminarModalidadNivelOcupacional(reg);
                    }
                }
            }
            for (NivelOcupacional nivel : modalidadLaboralHelper.getNivelesOcupacionales()) {
                if (nivel.getSeleccionado()) {
                    modalidadLaboralHelper.getModalidadNivelOcupacional().setModalidadLaboral(
                            modalidadLaboralHelper.getModalidadLaboral());
                    modalidadLaboralHelper.getModalidadNivelOcupacional().setNivelOcupacional(nivel);
                    iniciarDatosEntidad(modalidadLaboralHelper.getModalidadNivelOcupacional(), Boolean.TRUE);
                    administracionServicio.guardarModalidadNivelOcupacional(modalidadLaboralHelper.getModalidadNivelOcupacional());
                }
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al obtener los niveles ocupacionales", e);
        }
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object modalidadLaboral =
                    BeanUtils.cloneBean(getModalidadLaboralHelper().getModalidadLaboralEditDelete());
            ModalidadLaboral a = (ModalidadLaboral) modalidadLaboral;

            iniciarDatosEntidad(a, Boolean.FALSE);

            getModalidadLaboralHelper().setModalidadLaboral(a);
            getModalidadLaboralHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getModalidadLaboralHelper().setModalidadLaboral(new ModalidadLaboral());
        iniciarDatosEntidad(getModalidadLaboralHelper().getModalidadLaboral(), Boolean.TRUE);
        iniciarDatosEntidad(getModalidadLaboralHelper().getModalidadNivelOcupacional(), Boolean.TRUE);
        getModalidadLaboralHelper().setEsNuevo(Boolean.TRUE);
        modalidadLaboralHelper.setNivelesOcupacionales(new ArrayList<NivelOcupacional>());
        buscarNivelesOcupacionales();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            administracionServicio.eliminarModalidadLaboral(getModalidadLaboralHelper().getModalidadLaboralEditDelete());
            getModalidadLaboralHelper().getListaModalidadLaboral().
                    remove(getModalidadLaboralHelper().getModalidadLaboralEditDelete());
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
            modalidadLaboralHelper.getListaModalidadLaboral().clear();
            modalidadLaboralHelper.setListaModalidadLaboral(
                    administracionServicio.listarTodosModalidadLaboralPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo modalidad.
     */
    private void iniciarComboTipoModalidad() {
        modalidadLaboralHelper.getTipoModalidad().clear();
        for (TipoModalidadEnum tp : TipoModalidadEnum.values()) {
            modalidadLaboralHelper.getTipoModalidad().add(new SelectItem(tp.getCodigo(), tp.getDescripcion()));
        }
    }

    /**
     * metodo para validar si ya existe el codigo/nemonico.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean hayCodigo = true;
        try {
            modalidadLaboralHelper.getListaModalidadLaboralCodigo().clear();
            modalidadLaboralHelper.setListaModalidadLaboralCodigo(
                    administracionServicio.listarTodosModalidadLaboralPorCodigo(
                    modalidadLaboralHelper.getModalidadLaboral().getCodigo()));

            if (modalidadLaboralHelper.getListaModalidadLaboralCodigo().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del codigo", ex);
        }
        return hayCodigo;
    }

    /**
     * Permite obtener el listado completo de Niveles Ocupacionales
     *
     * @return formulario de la entidad
     */
    public String buscarNivelesOcupacionales() {
        try {

            modalidadLaboralHelper.getNivelesOcupacionales().clear();
            modalidadLaboralHelper.getNivelesOcupacionales().addAll(administracionServicio.listarNivelOcupacionalDeModalidad(
                    modalidadLaboralHelper.getModalidadLaboral().getId()));



        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda de niveles ocupacionales", ex);
        }
        return null;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * modalidad parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoModalidad(final String codigo) {
        return TipoModalidadEnum.obtenerDescripcion(codigo);
    }

    /**
     * @return the modalidadLaboralHelper
     */
    public ModalidadLaboralHelper getModalidadLaboralHelper() {
        return modalidadLaboralHelper;
    }

    /**
     * @param modalidadLaboralHelper the modalidadLaboralHelper to set
     */
    public void setModalidadLaboralHelper(final ModalidadLaboralHelper modalidadLaboralHelper) {
        this.modalidadLaboralHelper = modalidadLaboralHelper;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getadministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setadministracionServicio(final AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
