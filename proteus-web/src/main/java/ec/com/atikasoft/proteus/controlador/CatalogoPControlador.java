/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CatalogoPHelper;
import ec.com.atikasoft.proteus.modelo.Catalogo;
import ec.com.atikasoft.proteus.modelo.TipoCatalogo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.ArrayList;
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
 *
 * @author Atikasoft
 */
@ManagedBean(name = "catalogoPBean")
@ViewScoped
public class CatalogoPControlador extends CatalogoControlador {

    /**
     * BB DE LA CLASE.
     */
    @ManagedProperty("#{catalogoPHelper}")
    private CatalogoPHelper catalogoPHelper;

    /**
     * REGLA DE NAVEGACION PANTALLA PRINCIPAL.
     */
    public static final String PANTALLA_PRINCIPAL = "/pages/index.jsf";

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/catalogo/catalogo.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/catalogo/lista_catalogo.jsf";

    /**
     * /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * CONSTRUCTOR.
     */
    public CatalogoPControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(getCatalogoPHelper());
        llenarComboTipoCatalogo();
    }

    /**
     * METODO QUE SE ENCARGA DE LLENAR EL COMBO DE TIPO DE CATALOGO.
     */
    public void llenarComboTipoCatalogo() {
        try {
            List<TipoCatalogo> listaTipoCatalogo = admServicio.listarTodosTipoCatalogo();
            iniciarCombos(getCatalogoPHelper().getListaTipoCatalogo());
            for (TipoCatalogo tc : listaTipoCatalogo) {
                getCatalogoPHelper().getListaTipoCatalogo().add(new SelectItem(tc.getId(), tc.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "ec.markasoft.claseabierta.administracion.catalogo.error.consulta.tipoCatalogo", e);
        }
    }

    /**
     * METODO QUE SE ENCARGA DE LLENAR LA TABLA DE CATALOGO BUSCANDO POR EL ID
     * DE TIPO DE CATALOGO.
     */
    public void cargarTablaCatalogo() {
        try {
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoId(
                    getCatalogoPHelper().getCatalogo().getTipoCatalogoId());
            getCatalogoPHelper().setListaCatalogos(listaCatalogo);
        } catch (Exception e) {
            error(getClass().getName(), "ec.markasoft.claseabierta.administracion.catalogo.error.consulta.catalogo", e);
        }
    }

    /**
     * METODO QUE REDIRIGE A LA PANTALLA PRINCIPAL.
     *
     * @return REGLA DE NAVEGACION.
     */
    public String irPantallaPrincipal() {
        return PANTALLA_PRINCIPAL;
    }

    @Override
    public String guardar() {
        try {
            if (catalogoPHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarCatalogo(catalogoPHelper.getCatalogo());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarCatalogo(catalogoPHelper.getCatalogo());

                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
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
            catalogoPHelper.getListaAlertaNemonico().clear();
            catalogoPHelper.setListaAlertaNemonico(admServicio.listarCatalogoPorNemonico(
                    catalogoPHelper.getCatalogo().getCodigo()));
            if (catalogoPHelper.getListaAlertaNemonico().isEmpty()) {
                hayNemonico = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del nemonico", ex);
        }
        return hayNemonico;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object catalogo = BeanUtils.cloneBean(getCatalogoPHelper().getCatalogoEliminar());
            Catalogo c = (Catalogo) catalogo;
            getCatalogoPHelper().setCatalogo(c);
            getCatalogoPHelper().setEsNuevo(Boolean.FALSE);
            iniciarDatosEntidad(getCatalogoPHelper().getCatalogo(), Boolean.FALSE);
        } catch (Exception e) {
            error(getClass().getName(), "ec.markasoft.claseabierta.administracion.catalogo.error.iniciarEdicion", e);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        System.out.println("enta al metodo de inicio***********************");
        getCatalogoPHelper().setEsNuevo(Boolean.TRUE);
        getCatalogoPHelper().setCatalogo(new Catalogo());
        iniciarDatosEntidad(getCatalogoPHelper().getCatalogo(), Boolean.TRUE);
        llenarComboTipoCatalogo();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarCatalogo(getCatalogoPHelper().getCatalogoEliminar());
            getCatalogoPHelper().getListaCatalogos().
                    remove(getCatalogoPHelper().getCatalogoEliminar());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        getCatalogoPHelper().setListaCatalogos(new ArrayList<Catalogo>());
        getCatalogoPHelper().getCatalogo().setTipoCatalogoId(null);
        return LISTA_ENTIDAD;
    }

    /**
     * @return the catalogoPHelper
     */
    public CatalogoPHelper getCatalogoPHelper() {
        return catalogoPHelper;
    }

    /**
     * @param catalogoPHelper the catalogoPHelper to set
     */
    public void setCatalogoPHelper(CatalogoPHelper catalogoPHelper) {
        this.catalogoPHelper = catalogoPHelper;
    }
}
