/*
 *  CotizacionIessControlador.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.CotizacionIessHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.modelo.CotizacionIess;
import ec.com.atikasoft.proteus.modelo.NivelOcupacional;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
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
 * CotizacionIessControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "cotizacionIessBean")
@ViewScoped
public class CotizacionIessControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CotizacionIessControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/cotizacion_iess/cotizacion_iess.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/cotizacion_iess/lista_cotizacion_iess.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private RegimenLaboralServicio admregLaboralServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{cotizacionIessHelper}")
    private CotizacionIessHelper cotizacionIessHelper;

    /**
     * Constructor por defecto.
     */
    public CotizacionIessControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            if (cotizacionIessHelper.getEsNuevo()) {
                if (validarNemonico()) {
                    mostrarMensajeEnPantalla("LA ESCALA REMUNERATIVA YA SE ENCUENTRA REGISTRADA",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarCotizacionIess(cotizacionIessHelper.getCotizacionIess());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarCotizacionIess(cotizacionIessHelper.getCotizacionIess());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<CotizacionIess> cotizaciones = admServicio.listarTodosCotizacionIessPorNombre(null);
            getSession().getServletContext().setAttribute(CacheEnum.COTIZACIONES_IESS.getCodigo(), cotizaciones);
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
            cotizacionIessHelper.getListaCotizacionIesse().clear();
            cotizacionIessHelper.setListaCotizacionIesse(admServicio.listarCotizacionPorIdNivelOcupacional(
                    cotizacionIessHelper.getCotizacionIess().getNivelOcupacionalId()));
            if (cotizacionIessHelper.getListaCotizacionIesse().isEmpty()) {
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
            Object cloneBean =
                    BeanUtils.cloneBean(cotizacionIessHelper.getCotizacionIessEdirDelete());
            CotizacionIess d = (CotizacionIess) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            cotizacionIessHelper.setCotizacionIess(d);
            cotizacionIessHelper.setIdRegimenLaboral(d.getNivelOcupacional().getIdRegimenLaboral());
            llenarComboNivelOcupacional();
            cotizacionIessHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        cotizacionIessHelper.setCotizacionIess(new CotizacionIess());
        cotizacionIessHelper.iniciador();
        cotizacionIessHelper.setIdRegimenLaboral(null);
        iniciarDatosEntidad(cotizacionIessHelper.getCotizacionIess(), Boolean.TRUE);
        cotizacionIessHelper.setEsNuevo(Boolean.TRUE);
        llenarComboRegimen();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarCotizacionIess(cotizacionIessHelper.getCotizacionIessEdirDelete());
            cotizacionIessHelper.getListaCotizacionIesse().
                    remove(cotizacionIessHelper.getCotizacionIessEdirDelete());
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
            cotizacionIessHelper.getListaCotizacionIesse().clear();
            cotizacionIessHelper.setListaCotizacionIesse(
                    admServicio.listarTodosCotizacionIessPorNombre(
                    getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * METODO PARA LLENAR LA LISTA DE llenarComboRegimen.
     */
    public void llenarComboRegimen() {
        try {
            cotizacionIessHelper.getListaRegimenLaborals().clear();
            iniciarCombos(cotizacionIessHelper.getListaRegimenLaborals());
            List<RegimenLaboral> buscarTodosActivos = admregLaboralServicio.listarTodosRegimenVigentes();
            for (RegimenLaboral re : buscarTodosActivos) {
                cotizacionIessHelper.getListaRegimenLaborals().add(new SelectItem(re.getId(), re.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "error al llenar el combo regimen",
                    e);
        }
    }

    /**
     * METODO PARA LLENAR LA LISTA DE llenarComboNivelOcupacional.
     */
    public void llenarComboNivelOcupacional() {
        try {
            cotizacionIessHelper.getListaNivelesOcupacionales().clear();
            iniciarCombos(cotizacionIessHelper.getListaNivelesOcupacionales());
            List<NivelOcupacional> buscarTodosActivos =
                    admregLaboralServicio.listarTodosPorRegimenLaboralId(cotizacionIessHelper.getIdRegimenLaboral());
            for (NivelOcupacional ni : buscarTodosActivos) {
                cotizacionIessHelper.getListaNivelesOcupacionales().add(new SelectItem(ni.getId(), ni.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(),
                    "error al llenar el combo de nivel ocupacional", e);
        }
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(cotizacionIessHelper);
        setCotizacionIessHelper(cotizacionIessHelper);
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        llenarComboRegimen();
    }

    /**
     * @return the cotizacionIessHelper
     */
    public CotizacionIessHelper getCotizacionIessHelper() {
        return cotizacionIessHelper;
    }

    /**
     * @param cotizacionIessHelper the cotizacionIessHelper to set
     */
    public void setCotizacionIessHelper(final CotizacionIessHelper cotizacionIessHelper) {
        this.cotizacionIessHelper = cotizacionIessHelper;
    }
}
