/*
 *  DevengamientoControlador.java
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
 *  24/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.DevengamientoHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Devengamiento;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import java.lang.Override;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "devengamientoBean")
@ViewScoped
public class DevengamientoControlador extends BaseControlador {

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD =
            "/pages/procesos/devengamiento/registro_devengamientos.jsf";

    /**
     * Servicio de puesto.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{devengamientoHelper}")
    private DevengamientoHelper devengamientoHelper;

    /**
     * constructor de la clase.
     */
    public DevengamientoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
//        devengamientoHelper.setDevengamiento(new Devengamiento());
//        devengamientoHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
//        devengamientoHelper.getListaDevengamientos().clear();
//        devengamientoHelper.setTipoDocumento(new ArrayList<SelectItem>());
//        devengamientoHelper.setTipoIdentificacion(null);
//        devengamientoHelper.setNumeroIdentificacion(null);
//        consultarCatalogos();
//        controlSeleccionTipoDocumento();
//        devengamientoHelper.setActivar(Boolean.FALSE);
    }

    /**
     * metodo para buscar un servidor segun su Id.
     */
    public void buscarPorCedula() {
////        devengamientoHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
//        devengamientoHelper.getParametrosBusqueda().setTipoDocumento(devengamientoHelper.getTipoIdentificacion());
//        devengamientoHelper.getParametrosBusqueda().setNumeroDocumento(devengamientoHelper.getNumeroIdentificacion());
//        devengamientoHelper.getParametrosBusqueda().setInstitucionId(obtenerInstitucion().getId());
//        try {
//            if (devengamientoHelper.getNumeroIdentificacion() != null
//                    || devengamientoHelper.getParametrosBusqueda().getNumeroDocumento().trim().length() > 0) {
//                if (devengamientoHelper.getTipoIdentificacion().equals(
//                        TipoDocumentoEnum.CEDULA.getNombre())
//                        || devengamientoHelper.getTipoIdentificacion().
//                        equals(TipoDocumentoEnum.PASAPORTE.getNombre())) {
//                    devengamientoHelper.getListaDevengamientos().clear();
////                    devengamientoHelper.setListaDevengamientos(
////                            puestoServicio.buscarPuestoporFiltros(devengamientoHelper.getParametrosBusqueda()));
//                    if (devengamientoHelper.getListaDevengamientos().isEmpty()) {
//                        devengamientoHelper.setDevengamiento(new Devengamiento());
//                        devengamientoHelper.setActivar(Boolean.FALSE);
//                        ponerMensajeError(NADA, "Servidor no registrado");
//                    } else {
////                        SrhvOrganicoPosicionalIndividual devengamiento =
////                                devengamientoHelper.getListaDevengamientos().get(0);
////                        devengamientoHelper.setParametrosBusqueda(devengamiento);
//                        buscarDevengacion();
//                        if (devengamientoHelper.getDevengamientoLista().isEmpty()) {
//                            devengamientoHelper.setDevengamiento(new Devengamiento());
//                            devengamientoHelper.setActivar(Boolean.TRUE);
//                            ponerMensajeInfo(NADA, "No tiene informacion de denvengacion, debe ingresar los datos.");
//                        } else {
//                            Devengamiento devengamientos =
//                                    devengamientoHelper.getDevengamientoLista().get(0);
//                            devengamientoHelper.setDevengamiento(devengamientos);
//                            devengamientoHelper.setActivar(Boolean.FALSE);
//                        }
//                    }
//
//                }
//            } else {
//                ponerMensajeError(NADA, "No a ingresado el numero de identificación");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DevengamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * busca los devengamientos activos.
     */
    private void buscarDevengacion() {
        try {
            devengamientoHelper.getDevengamientoLista().clear();
            devengamientoHelper.setDevengamientoLista(tramiteServicio.buscarDevengamientosPorServidor(
                    devengamientoHelper.getTipoIdentificacion(), devengamientoHelper.getNumeroIdentificacion(),
                    obtenerUsuarioConectado().getInstitucion().getId(), devengamientoHelper.getFechaCorte()));
        } catch (ServicioException ex) {
            Logger.getLogger(DevengamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo que guarde el periodo del devengamineto.
     *
     * @return
     */
    public String guardar() {
//        if (devengamientoHelper.getActivar()) {
//            try {
//                actualizarComponente("fechaFinDevengar,fechaInicioDevengar");
//                Boolean estado = Boolean.TRUE;
//                String bundlekey = "";
//                if (devengamientoHelper.getDevengamiento().getFechaInicio() != null
//                        && devengamientoHelper.getDevengamiento().getFechaFinal() != null) {
//                    long f1 = devengamientoHelper.getDevengamiento().getFechaInicio().getTime();
//                    long f2 = devengamientoHelper.getDevengamiento().getFechaFinal().getTime();
//                    if (f1 > f2) {
//                        estado = Boolean.FALSE;
//                        bundlekey = COMPARAR_FECHA_DEVENGAMIENTO;
//                    }
//                }
//                if (estado) {
//                    InstitucionEjercicioFiscal i = new InstitucionEjercicioFiscal();
//                    i.setId(obtenerUsuarioConectado().getIdInstitucion());
//                    devengamientoHelper.getDevengamiento().setNombresCompletos(
//                            devengamientoHelper.getParametrosBusqueda().getNombreCompleto());
//                    devengamientoHelper.getDevengamiento().setTipoIdentificacion(
//                            TipoDocumentoEnum.CEDULA.getNemonico().substring(0, 1));
//                    devengamientoHelper.getDevengamiento().setInstitucion(i);
//                    devengamientoHelper.getDevengamiento().setNumeroIdentificacion(
//                            devengamientoHelper.getNumeroIdentificacion());
//                    tramiteServicio.guardarDevengacion(devengamientoHelper.getDevengamiento(),
//                            obtenerUsuarioConectado(), obtenerUsuarioConectado().getIdInstitucion());
//                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
//                    devengamientoHelper.setNumeroIdentificacion("");
//                    devengamientoHelper.setTipoIdentificacion("");
////                    devengamientoHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
//                    devengamientoHelper.setDevengamiento(new Devengamiento());
//                    devengamientoHelper.setActivar(Boolean.FALSE);
//                } else {
//                    mostrarMensajeEnPantalla(bundlekey, FacesMessage.SEVERITY_WARN);
//                }
//            } catch (Exception ex) {
//                mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
//                //error(getClass().getName(), "Error al guardar el devengamiento", ex);
//            }
//        }
        return null;
    }

    /**
     * Este metodo busca los catalogos y los llena en las opciones.
     */
    private void consultarCatalogos() {
        try {
//            llenarOpcionesCatalogoNombre(devengamientoHelper.getTipoDocumento(),
//                    catalogoServicio.buscarTipoDocumento());
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar catalogo.", e);
        }
    }

    /**
     * Este método pasa una lista de catalogo a una lista de seleccion.
     *
     * @param lista List
     * @param opciones List
     */
//    public final void llenarOpcionesCatalogoNombre(final List<SelectItem> lista, final List<CatalogoDetalle> opciones) {
//        iniciarCombos(lista);
//        if (opciones != null) {
//            for (CatalogoDetalle c : opciones) {
//                lista.add(new SelectItem(c.getNombre(), c.getNombre(), c.getDescripcion()));
//            }
//        }
//    }

    /**
     * Este método control la selección del tipo de decumento.
     */
    public void controlSeleccionTipoDocumento() {
        if (TipoDocumentoEnum.CEDULA.getNombre().equals(
                devengamientoHelper.getTipoIdentificacion())) {
            devengamientoHelper.setTipoCedula(Boolean.TRUE);
        } else {
            devengamientoHelper.setTipoCedula(Boolean.FALSE);
        }
    }

    /**
     * @return the puestoServicio
     */
//    public SrhvOrganicoPosicionalIndividualServicio getPuestoServicio() {
//        return puestoServicio;
//    }

    /**
     * @param puestoServicio the puestoServicio to set
     */
//    public void setPuestoServicio(final SrhvOrganicoPosicionalIndividualServicio puestoServicio) {
//        this.puestoServicio = puestoServicio;
//    }

    /**
     * @return the devengamientoHelper
     */
    public DevengamientoHelper getDevengamientoHelper() {
        return devengamientoHelper;
    }

    /**
     * @param devengamientoHelper the devengamientoHelper to set
     */
    public void setDevengamientoHelper(final DevengamientoHelper devengamientoHelper) {
        this.devengamientoHelper = devengamientoHelper;
    }

    /**
     * @return the catalogoServicio
     */
//    public CatalogoServicio getCatalogoServicio() {
//        return catalogoServicio;
//    }

    /**
     * @param catalogoServicio the catalogoServicio to set
     */
//    public void setCatalogoServicio(final CatalogoServicio catalogoServicio) {
//        this.catalogoServicio = catalogoServicio;
//    }

    /**
     * @return the tramiteServicio
     */
    public TramiteServicio getTramiteServicio() {
        return tramiteServicio;
    }

    /**
     * @param tramiteServicio the tramiteServicio to set
     */
    public void setTramiteServicio(final TramiteServicio tramiteServicio) {
        this.tramiteServicio = tramiteServicio;
    }
}
