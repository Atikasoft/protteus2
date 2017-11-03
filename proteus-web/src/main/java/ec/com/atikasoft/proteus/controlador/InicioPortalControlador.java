/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.PortalRrhhHelper;
import ec.com.atikasoft.proteus.dao.ServidorDao;
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
 * InicioPortalControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "inicioPortalBean")
@ViewScoped
public class InicioPortalControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(InicioPortalControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/portal_rrhh.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{portalRrhhHelper}")
    private PortalRrhhHelper portalRrhhHelper;

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    /**
     * METODO QUE BUSCA LAS OPCIONES DEL PORTAL.
     *
     * @return
     */
    public String buscar() {
        try {
            portalRrhhHelper.getListaPortalRhhs().clear();
            portalRrhhHelper.setListaPortalRhhs(
                    admServicio.listarTodasPortalRhhVigente());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     *
     * @param url
     * @return
     */
    public String ponerUrl(String url) {
        try {
            portalRrhhHelper.setServidorEditable(Boolean.TRUE);
            portalRrhhHelper.setPresentar(Boolean.FALSE);
            Servidor s = servidorDao.buscarPorId(obtenerUsuarioConectado().getServidor().getId());
            portalRrhhHelper.setServidor(s);
        } catch (Exception e) {
        }
        return url;
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
