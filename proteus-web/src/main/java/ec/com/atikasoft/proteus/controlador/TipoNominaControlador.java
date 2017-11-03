/*
 *  TipoNominaControlador.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoNominaHelper;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.PeriodicidadOcurrenciaNominaEnum;
import ec.com.atikasoft.proteus.enums.TipoNominaEnum;
import ec.com.atikasoft.proteus.modelo.EstadoPersonal;
import ec.com.atikasoft.proteus.modelo.EstadoPuesto;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import ec.com.atikasoft.proteus.modelo.TipoNominaEstadoPersonal;
import ec.com.atikasoft.proteus.modelo.TipoNominaEstadoPuesto;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
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
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "tipoNominaBean")
@ViewScoped
public class TipoNominaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(TipoNominaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/nomina/tipo_nomina.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ESTADO_PUESTO = "/pages/administracion/nomina/lista_estado_puesto_nomina.jsf";
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ESTADO_PERSONAL = "/pages/administracion/nomina/lista_estado_personal_nomina.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/nomina/lista_tipo_nomina.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Servicio de regimenLaboralServicio.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tipoNominaHelper}")
    private TipoNominaHelper tipoNominaHelper;

    /**
     * Constructor por defecto.
     */
    public TipoNominaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(tipoNominaHelper);
        setTipoNominaHelper(tipoNominaHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        iniciarComboTipo();
        iniciarComboCobertura();
        iniciarComboPeriodicidad();
        buscarEstadoPuestos();
        buscarEstadoPersonal();
        iniciarRegimenLaboral();
    }

    @Override
    public String guardar() {
        try {
            if (tipoNominaHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarTipoNomina(tipoNominaHelper.getTipoNomina());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarTipoNomina(tipoNominaHelper.getTipoNomina());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * asignar los estados puestos a la nomina seleccionada.
     */
    public String asignarEstadoPuesto() {
        try {
            for (TipoNominaEstadoPuesto tipo : tipoNominaHelper.getTipoNomina().getListaTipoNominaEstadoPuestos()) {
                iniciarDatosEntidad(tipo, Boolean.TRUE);
                if (tipo.getVigente()) {
                    admServicio.eliminarTipoNominaEstadoPuesto(tipo);
                }
            }
            for (EstadoPuesto estado : tipoNominaHelper.getListaEstadoPuestos()) {
                if (estado.getSeleccionado()) {
                    tipoNominaHelper.getTipoNominaEstadoPuesto().setEstadoPuestoId(estado.getId());
                    tipoNominaHelper.getTipoNominaEstadoPuesto().setTipoNominaId(tipoNominaHelper.getTipoNomina().getId());
                    iniciarDatosEntidad(tipoNominaHelper.getTipoNominaEstadoPuesto(), Boolean.TRUE);
                    admServicio.guardarEstadoPuestoNomina(tipoNominaHelper.getTipoNominaEstadoPuesto());
                }
            }
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar estado puesto", e);
        }
        return null;
    }

    /**
     * asignar los estados puestos a la nomina seleccionada.
     */
    public String asignarEstadoPersonal() {
        try {
            for (TipoNominaEstadoPersonal tipo : tipoNominaHelper.getTipoNomina().getListaTipoNominaEstadoPersonales()) {
                iniciarDatosEntidad(tipo, Boolean.TRUE);
                if (tipo.getVigente()) {
                    admServicio.eliminarTipoNominaEstadoPersonal(tipo);
                }
            }
            for (EstadoPersonal estado : tipoNominaHelper.getListaEstadoPersonal()) {
                if (estado.getSeleccionado()) {
                    tipoNominaHelper.getTipoNominaEstadoPersonal().setEstadoPersonalId(estado.getId());
                    tipoNominaHelper.getTipoNominaEstadoPersonal().setTipoNominaId(tipoNominaHelper.getTipoNomina().getId());
                    iniciarDatosEntidad(tipoNominaHelper.getTipoNominaEstadoPersonal(), Boolean.TRUE);
                    admServicio.guardarEstadoPersonalNomina(tipoNominaHelper.getTipoNominaEstadoPersonal());
                }
            }
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar estado puesto", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el nemónico.
     *
     * @return haynemonico Boolean.
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            tipoNominaHelper.getListaNominas().clear();
            tipoNominaHelper.setListaNominas(admServicio.listarTipoNominaPorNemonico(
                    tipoNominaHelper.getTipoNomina().getCodigo()));
            if (tipoNominaHelper.getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoNomina(final String codigo) {
        return TipoNominaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboTipo() {
        tipoNominaHelper.getListaTipo().clear();
        for (TipoNominaEnum t : TipoNominaEnum.values()) {
            tipoNominaHelper.getListaTipo().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionCoberturaNomina(final String codigo) {
        return CoberturaNominaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboCobertura() {
        tipoNominaHelper.getListaCobertura().clear();
        for (CoberturaNominaEnum t : CoberturaNominaEnum.values()) {
            tipoNominaHelper.getListaCobertura().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionPeriodicidadNomina(final String codigo) {
        return PeriodicidadOcurrenciaNominaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboPeriodicidad() {
        tipoNominaHelper.getListaPeriodicidad().clear();
        for (PeriodicidadOcurrenciaNominaEnum t : PeriodicidadOcurrenciaNominaEnum.values()) {
            tipoNominaHelper.getListaPeriodicidad().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    public void iniciarRegimenLaboral() {
        try {
            tipoNominaHelper.getListaRegimenLaboral().clear();
            List<RegimenLaboral> listaRegimenLaboral = regimenLaboralServicio.listarTodosRegimenVigentes();
            for (RegimenLaboral t : listaRegimenLaboral) {
                tipoNominaHelper.getListaRegimenLaboral().add(new SelectItem(t.getId(), t.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(tipoNominaHelper.getTipoNominaEditDelete());
            TipoNomina d = (TipoNomina) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            tipoNominaHelper.setTipoNomina(d);
            tipoNominaHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * metodo para iniciar el estado puestos carga la lista de puestos que
     * pertenecen a una nómina.
     *
     * @return
     */
    public String iniciarEstadoPuesto() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(tipoNominaHelper.getTipoNominaEditDelete());
            TipoNomina d = (TipoNomina) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            tipoNominaHelper.setTipoNomina(d);
            tipoNominaHelper.setEsNuevo(Boolean.FALSE);
            buscarEstadoPuestos();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ESTADO_PUESTO;
    }

    /**
     * presenta la lista de los estados personal por nómina.
     *
     * @return
     */
    public String iniciarEstadoPersonal() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(tipoNominaHelper.getTipoNominaEditDelete());
            TipoNomina d = (TipoNomina) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            tipoNominaHelper.setTipoNomina(d);
            tipoNominaHelper.setEsNuevo(Boolean.FALSE);
            buscarEstadoPersonal();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ESTADO_PERSONAL;
    }

    /**
     * Permite obtener el listado completo de estados puesto.
     *
     * @return formulario de la entidad
     */
    public String buscarEstadoPuestos() {
        try {
            tipoNominaHelper.getListaEstadoPuestos().clear();
            tipoNominaHelper.getListaEstadoPuestos().addAll(admServicio.listarEstadoPuestoDeNomina(
                    tipoNominaHelper.getTipoNomina().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda de estado puestos", ex);
        }
        return null;
    }

    /**
     * Permite obtener el listado completo de Estado peesonal.
     *
     * @return formulario de la entidad
     */
    public String buscarEstadoPersonal() {
        try {
            tipoNominaHelper.getListaEstadoPersonal().clear();
            tipoNominaHelper.getListaEstadoPersonal().addAll(admServicio.listarEstadoPersonalDeNomina(
                    tipoNominaHelper.getTipoNomina().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda estados personal", ex);
        }
        return null;
    }

    @Override
    public String iniciarNuevo() {
        tipoNominaHelper.setTipoNomina(new TipoNomina());
        tipoNominaHelper.iniciador();
        iniciarDatosEntidad(tipoNominaHelper.getTipoNomina(), Boolean.TRUE);
        tipoNominaHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboTipo();
        iniciarComboCobertura();
        iniciarComboPeriodicidad();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarTipoNomina(tipoNominaHelper.getTipoNominaEditDelete());
            tipoNominaHelper.getListaNominas().
                    remove(tipoNominaHelper.getTipoNominaEditDelete());
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
            tipoNominaHelper.getListaNominas().clear();
            tipoNominaHelper.setListaNominas(
                    admServicio.listarTodosTipoNominaPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * @return the tipoNominaHelper
     */
    public TipoNominaHelper getTipoNominaHelper() {
        return tipoNominaHelper;
    }

    /**
     * @param tipoNominaHelper the tipoNominaHelper to set
     */
    public void setTipoNominaHelper(TipoNominaHelper tipoNominaHelper) {
        this.tipoNominaHelper = tipoNominaHelper;
    }
}
