/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.GrupoHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador para la administración de Grupo.
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "grupoBean")
@ViewScoped
public class GrupoControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/grupo/grupo.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/grupo/lista_grupo.jsf";

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{grupoHelper}")
    private GrupoHelper grupoHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getGrupoHelper());
        buscar();
        getCatalogoHelper().setCampoBusqueda("");

    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.GRUPOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE GRUPOS");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (getGrupoHelper().getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdministracionServicio().guardarGrupo(getGrupoHelper().getGrupo());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }
            } else {
                getAdministracionServicio().actualizarGrupo(getGrupoHelper().getGrupo());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el grupo", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getGrupoHelper().getGrupoEditDelete());
            Grupo grupo = (Grupo) cloneBean;
            iniciarDatosEntidad(grupo, Boolean.FALSE);
            getGrupoHelper().setGrupo(grupo);
            getGrupoHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getGrupoHelper().setGrupo(new Grupo());
        iniciarDatosEntidad(getGrupoHelper().getGrupo(), Boolean.TRUE);
        getGrupoHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((administracionServicio.tieneRestricciones("grupo.id",
                    "Grupo", "Clase",
                    grupoHelper.getGrupoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Clase,");
            }
            if ((administracionServicio.tieneRestricciones("grupo.id",
                    "Grupo", "Requisito",
                    grupoHelper.getGrupoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Requisito");
            }
            if (cont == 0) {
                getAdministracionServicio().eliminarGrupo(getGrupoHelper().getGrupoEditDelete());
                getGrupoHelper().getListaGrupo().remove(getGrupoHelper().getGrupoEditDelete());
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
            grupoHelper.getListaGrupo().clear();
            grupoHelper.setListaGrupo(getAdministracionServicio().listarTodosGrupoPorNombre(getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            grupoHelper.getListaGrupoNemonico().clear();
            grupoHelper.setListaGrupoNemonico(getAdministracionServicio().listarTodosGrupoPorNemonico(grupoHelper.getGrupo().getNemonico()));

            if (grupoHelper.getListaGrupoNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the grupoHelper
     */
    public GrupoHelper getGrupoHelper() {
        return grupoHelper;
    }

    /**
     * @param grupoHelper the grupoHelper to set
     */
    public void setGrupoHelper(GrupoHelper grupoHelper) {
        this.grupoHelper = grupoHelper;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getAdministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setAdministracionServicio(AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
