/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.RequisitoHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Grupo;
import ec.com.atikasoft.proteus.modelo.Requisito;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
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
 * @author elsa.angamarca@atikasoft.com.ec
 */
@ManagedBean(name = "requisitoBean")
@ViewScoped
public class RequisitoControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/requisito/lista_requisito.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/requisito/requisito.jsf";

    /**
     * Seleccione.
     */
    public static final String SELECCIONE = "Seleccione...";

    /**
     * Servicio de administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{requisitoHelper}")
    private RequisitoHelper requisitoHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getRequisitoHelper());
        llenarComboGrupo();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.REQUISITOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE REQUISITOS");
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            if (requisitoHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    administracionServicio.guardarRequisito(requisitoHelper.getRequisito());
                    iniciarNuevo();
                    llenarComboGrupo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                administracionServicio.actualizarRequisito(requisitoHelper.getRequisito());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar Requisito", e);
        }

        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(requisitoHelper.getRequisitoEditDelete());
            Requisito requisito = (Requisito) cloneBean;
            iniciarDatosEntidad(requisito, Boolean.FALSE);
            requisitoHelper.setRequisito(requisito);
            requisitoHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        requisitoHelper.setRequisito(new Requisito());
        iniciarDatosEntidad(requisitoHelper.getRequisito(), Boolean.TRUE);
        requisitoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            int cont = 0;
            String mensaje;
            mensaje = " en ";
            if ((administracionServicio.tieneRestricciones("requisito.id",
                    "Requisito", "TipoMovimientoRequisito",
                    requisitoHelper.getRequisitoEditDelete().getId()))) {
                cont += 1;
                mensaje = UtilCadena.concatenar(mensaje, " Tipo Movimiento Requisito,");
            }
            if (cont == 0) {
                getAdministracionServicio().eliminarRequisito(requisitoHelper.getRequisitoEditDelete());
                requisitoHelper.getListaRequisito().remove(requisitoHelper.getRequisitoEditDelete());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            }else{
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
            requisitoHelper.getListaRequisito().clear();
            requisitoHelper.setListaRequisito(getAdministracionServicio().listarTodosRequisitoPorNombre(getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    public void llenarComboGrupo() {
        try {
            //requisitoHelper.getListaGrupo().clear();
            //requisitoHelper.getListaGrupo().add(new SelectItem(null, SELECCIONE));
            iniciarCombos(requisitoHelper.getListaGrupo());
            List<Grupo> listaGrupo = getAdministracionServicio().listarTodosGrupo();
            for (Grupo g : listaGrupo) {
                requisitoHelper.getListaGrupo().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(RequisitoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Valida que la existencia de un nemónico.
     *
     * @return
     */
    public Boolean validarNemonico() {
        Boolean hayNemonico = true;
        try {
            requisitoHelper.getListaRequisitoNemonico().clear();
            requisitoHelper.setListaRequisitoNemonico(getAdministracionServicio().listarTodosRequisitoPorNemonico(requisitoHelper.getRequisito().getNemonico()));
            if (requisitoHelper.getListaRequisitoNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    /**
     * @return the requisitoHelper
     */
    public RequisitoHelper getRequisitoHelper() {
        return requisitoHelper;
    }

    /**
     * @param requisitoHelper the requisitoHelper to set
     */
    public void setRequisitoHelper(RequisitoHelper requisitoHelper) {
        this.requisitoHelper = requisitoHelper;
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
