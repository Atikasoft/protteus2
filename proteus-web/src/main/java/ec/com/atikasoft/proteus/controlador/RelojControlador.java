/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.RelojHelper;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.Reloj;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AsistenciaDePersonalServicio;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 * Controlador para la administración de Reloj.
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "relojBean")
@ViewScoped
public class RelojControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/asistencia_de_personal/reloj/reloj.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/asistencia_de_personal/reloj/lista_reloj.jsf";

    /**
     * Servicio Asistencia DePersonal
     */
    @EJB
    private AsistenciaDePersonalServicio asistenciaPersonalServicio;
    
    /**
     * Servicio Administracion
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{relojHelper}")
    private RelojHelper relojHelper;

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(relojHelper);
        getCatalogoHelper().setCampoBusqueda("");
        llenarListaModelosReloj();
        buscar();
    }

    private void llenarListaModelosReloj() {
        try {
            iniciarCombos(relojHelper.getListaModelosReloj());
            List<Catalogo> modelosReloj = admServicio
                    .listarTodosPorTipoCatalogoCodigo(TipoCatalogoEnum.MODELO_RELOJ.getCodigo());
            for (Catalogo c : modelosReloj) {
                relojHelper.getListaModelosReloj().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la búsqueda de modelos de reloj", ex);
        }
    }

    /**
     * Recupera todos los registros vigentes
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            relojHelper.getListaRelojesVigentes().clear();
            relojHelper.getListaRelojesVigentes().addAll(asistenciaPersonalServicio.listarRelojesVigentes());

        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la búsqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Iniciar variables para nuevo reloj
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        relojHelper.setReloj(new Reloj());
        relojHelper.getReloj().setCatalogoModelo(new Catalogo());
        relojHelper.setModeloRelojSeleccionado(null);
        relojHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Iniciar variables para edicion de reloj
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            relojHelper.setModeloRelojSeleccionado(relojHelper.getReloj().getCatalogoModelo().getId());
            relojHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Guardar
     *
     * @return
     */
    @Override
    public String guardar() {
        try {
            if (validarCodigoNombreIp()) {
                if (relojHelper.getEsNuevo()) {
                    iniciarDatosEntidad(relojHelper.getReloj(), Boolean.TRUE);
                    relojHelper.getReloj().getCatalogoModelo().setId(relojHelper.getModeloRelojSeleccionado());
                } else {
                    iniciarDatosEntidad(relojHelper.getReloj(), Boolean.FALSE);
                }
                asistenciaPersonalServicio.crearActualizarReloj(relojHelper.getReloj());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                return buscar();
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Elimina el registro
     *
     * @return
     */
    @Override
    public String borrar() {
        try {
            if (!relojEstaSiendoUsado()) {
                iniciarDatosEntidad(relojHelper.getReloj(), Boolean.FALSE);
                relojHelper.getReloj().setVigente(Boolean.FALSE);
                asistenciaPersonalServicio.crearActualizarReloj(relojHelper.getReloj());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
                buscar();
            } else {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO_CONSTRAINT, FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al intentar eliminar el registro", ex);
        }
        return null;
    }

    /**
     * Verifica que el registro no esté siendo usado por alguna Unidad Organizacional
     *
     * @return
     */
    private Boolean relojEstaSiendoUsado() {
        try {
            return !admServicio.listarUnidadesOrganizacionalesPorRelojId(relojHelper.getReloj().getId()).isEmpty();
        } catch (Exception ex) {
            error(getClass().getName(), "Error al intentar eliminar el registro", ex);
        }
        return Boolean.FALSE;
    }

    /**
     * Verifica que no exista el codigo ni el nombre ni el ip del reloj
     *
     * @return
     */
    private Boolean validarCodigoNombreIp() {
        boolean error = false;
        for (Reloj r : relojHelper.getListaRelojesVigentes()) {
            if (relojHelper.getEsNuevo() || !r.getId().equals(relojHelper.getReloj().getId())) {
                if (r.getCodigo().toUpperCase().equals(relojHelper.getReloj().getCodigo().toUpperCase())) {
                    mostrarMensajeEnPantalla("El código del reloj ya está siendo usado", FacesMessage.SEVERITY_ERROR);
                    error = true;
                    break;
                } else if (r.getNombre().toUpperCase().trim().equals(relojHelper.getReloj().getNombre().trim().toUpperCase())) {
                    mostrarMensajeEnPantalla("El nombre del reloj ya está siendo usado", FacesMessage.SEVERITY_ERROR);
                    error = true;
                    break;
                } else if (r.getIp().equals(relojHelper.getReloj().getIp())) {
                    mostrarMensajeEnPantalla("El ip del reloj ya está siendo usado", FacesMessage.SEVERITY_ERROR);
                    error = true;
                    break;
                }
            }
        }
        return !error;
    }

    /**
     *
     * @return
     */
    public RelojHelper getRelojHelper() {
        return relojHelper;
    }

    /**
     *
     * @param relojHelper
     */
    public void setRelojHelper(RelojHelper relojHelper) {
        this.relojHelper = relojHelper;
    }
}
