/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CodigoContableHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.modelo.CodigoContable;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador para la administración de Codigo Contable.
 *
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "codigoContableBean")
@ViewScoped
public class CodigosContablesControlador extends CatalogoControlador {

    /**
     * Regla de Navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/codigos_contables/codigos_contables.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/codigos_contables/lista_codigos_contables.jsf";

    /**
     * Servicio de Administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de Clase.
     */
    @ManagedProperty("#{codigoContableHelper}")
    private CodigoContableHelper codigoContableHelper;

    @Override
    @PostConstruct
    public void init() {
        /*setCatalogoHelper(getCodigoContableHelper());*/
        buscar();
        //getCatalogoHelper().setCampoBusqueda("");

    }

    /*@Override
    public void generarReporte() {
        setReporte(ReportesEnum.GRUPOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE GRUPOS");
        generarUrlDeReporte();
    }*/
    @Override
    public String guardar() {
        try {
            if (getCodigoContableHelper().getEsNuevo()) {
                if (validarCodigoYNombre()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    getAdministracionServicio().guardarCodigoContable(getCodigoContableHelper().getCodigoContable());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                }
            } else if (validarNombre()) {
                mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
            } else {
                getAdministracionServicio().actualizarCodigoContable(getCodigoContableHelper().getCodigoContable());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el código contable", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getCodigoContableHelper().getCodigoContableEditDelete());
            CodigoContable codigoContable = (CodigoContable) cloneBean;
            iniciarDatosEntidad(codigoContable, Boolean.FALSE);
            getCodigoContableHelper().setCodigoContable(codigoContable);
            getCodigoContableHelper().setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        getCodigoContableHelper().setCodigoContable(new CodigoContable());
        iniciarDatosEntidad(getCodigoContableHelper().getCodigoContable(), Boolean.TRUE);
        getCodigoContableHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            getAdministracionServicio().eliminarCodigoContable(getCodigoContableHelper().getCodigoContableEditDelete());
            getCodigoContableHelper().getListaCodigoContable().remove(getCodigoContableHelper().getCodigoContableEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    public String buscar() {
        try {
            codigoContableHelper.getListaCodigoContable().clear();
            codigoContableHelper.setListaCodigoContable(getAdministracionServicio().buscarCodigosContablesVigentes());
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }


    
    /**
     * Valida que el nombre no se repita entre los códigos contables vigentes
     *
     * @return
     */
    public Boolean validarNombre() {
        Boolean existe = true;
        try {
            List<CodigoContable> lista = getAdministracionServicio().listarCodigosContablesPorNombre(
                    codigoContableHelper.getCodigoContable().getNombre());
            
            if (lista.isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del nombre", ex);
        }
        return existe;
    }
    
    /**
     * Valida que el código y el nombre no se repita entre los códigos contables vigentes
     *
     * @return
     */
    public Boolean validarCodigoYNombre() {
        Boolean existe = true;
        try {
            List<CodigoContable> lista = getAdministracionServicio().listarCodigosContablesPorCodigoONombre(
                    codigoContableHelper.getCodigoContable().getCodigo(), codigoContableHelper.getCodigoContable().
                    getNombre());

            if (lista.isEmpty()) {
                existe = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validación del codigo-nombre", ex);
        }
        return existe;
    }

    /**
     * @return the codigoContableHelper
     */
    public CodigoContableHelper getCodigoContableHelper() {
        return codigoContableHelper;
    }

    /**
     * @param codigoContableHelper the codigoContableHelper to set
     */
    public void setCodigoContableHelper(CodigoContableHelper codigoContableHelper) {
        this.codigoContableHelper = codigoContableHelper;
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
