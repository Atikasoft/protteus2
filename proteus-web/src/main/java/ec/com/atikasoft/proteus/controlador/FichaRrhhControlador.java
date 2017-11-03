/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.FichaRrhhHelper;
import ec.com.atikasoft.proteus.controlador.helper.PortalRrhhHelper;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * FichaRrhhControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "fichaRrhhBean")
@ViewScoped
public class FichaRrhhControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(PortalRrhhControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/ficha_personal_rrhh/ficha_personal_rrhh.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/portal/portal_rrhh.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_INICIAL = "/pages/index.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{fichaRrhhHelper}")
    private FichaRrhhHelper fichaRrhhHelper;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{portalRrhhHelper}")
    private PortalRrhhHelper portalRrhhHelper;

    @Override
    @PostConstruct
    public void init() {
        fichaRrhhHelper.setNombreServidor("");
        fichaRrhhHelper.setNumeroIdentificacion("");        
        fichaRrhhHelper.getListaServidores().clear();
    }

    /**
     * metodo que busca el servidor por ej nombre
     */
    public String buscarServidorPorNombre() {
        try {
            if (fichaRrhhHelper.getNombreServidor().isEmpty() && fichaRrhhHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla("INGRESE ALMENOS 1 PARAMETRO DE BUSQUEDA...", FacesMessage.SEVERITY_INFO);
                fichaRrhhHelper.getListaServidores().clear();
            } else {
                if (!fichaRrhhHelper.getNombreServidor().isEmpty() && !fichaRrhhHelper.getNumeroIdentificacion().isEmpty()) {
                    fichaRrhhHelper.getListaServidores().clear();
                    fichaRrhhHelper.setListaServidores(admServicio.buscarServidorPorNombreYIdentificacion(
                            fichaRrhhHelper.getNombreServidor().toUpperCase(), fichaRrhhHelper.getNumeroIdentificacion()));
                }

                if (!fichaRrhhHelper.getNombreServidor().isEmpty() && fichaRrhhHelper.getNumeroIdentificacion().isEmpty()) {
                    if (fichaRrhhHelper.getNombreServidor().length() < 3) {
                        fichaRrhhHelper.getListaServidores().clear();
                        mostrarMensajeEnPantalla("INGRESE ALMENOS 3 CARACTERES PARA LA BUSQUEDA... ", FacesMessage.SEVERITY_INFO);
                    } else {
                        fichaRrhhHelper.getListaServidores().clear();
                        fichaRrhhHelper.setListaServidores(admServicio.buscarServidorPorNombre(
                                fichaRrhhHelper.getNombreServidor().toUpperCase()));
                    }
                }
                if (!fichaRrhhHelper.getNumeroIdentificacion().isEmpty() && fichaRrhhHelper.getNombreServidor().isEmpty()) {
                    fichaRrhhHelper.getListaServidores().clear();
                    Servidor r = admServicio.buscarServidorPorTipoDocumento(
                            fichaRrhhHelper.getNumeroIdentificacion());
                      if (r != null) {
                        fichaRrhhHelper.getListaServidores().add(r);
                    } else {
                        mostrarMensajeEnPantalla("Servidor no registrado", FacesMessage.SEVERITY_INFO);
                        fichaRrhhHelper.getListaServidores().clear();
                    }                    
                }
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipo(final String codigo) {
        return TipoIdentificacionEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String iniciarEdicion() {
        portalRrhhHelper.setPresentar(Boolean.TRUE);
        return LISTA_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        fichaRrhhHelper.setServidor(new Servidor());
        fichaRrhhHelper.iniciador();
        iniciarDatosEntidad(fichaRrhhHelper.getServidor(), Boolean.TRUE);
        fichaRrhhHelper.setEsNuevo(Boolean.TRUE);

        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        return PANTALLA_INICIAL;
    }

    /**
     * MERODO PARA REGRESAR A AL FORMULARIO.
     *
     * @return
     */
    public String regresarFormulario() {
        fichaRrhhHelper.getListaServidores().clear();
        fichaRrhhHelper.setNombreServidor("");
        fichaRrhhHelper.setNumeroIdentificacion("");
        return FORMULARIO_ENTIDAD;
    }

    /**
     * @return the fichaRrhhHelper
     */
    public FichaRrhhHelper getFichaRrhhHelper() {
        return fichaRrhhHelper;
    }

    /**
     * @param fichaRrhhHelper the fichaRrhhHelper to set
     */
    public void setFichaRrhhHelper(final FichaRrhhHelper fichaRrhhHelper) {
        this.fichaRrhhHelper = fichaRrhhHelper;
    }

    /**
     * @return the portalRrhhHelper
     */
    public PortalRrhhHelper getPortalRrhhHelper() {
        return portalRrhhHelper;
    }

    /**
     * @param portalRrhhHelper the portalRrhhHelper to set
     */
    public void setPortalRrhhHelper(final PortalRrhhHelper portalRrhhHelper) {
        this.portalRrhhHelper = portalRrhhHelper;
    }
}
