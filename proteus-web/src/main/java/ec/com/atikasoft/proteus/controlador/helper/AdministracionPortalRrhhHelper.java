/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.PortalRhh;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author atikasoft
 */
/**
 * ServidorHelper
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "adminPortalRrhhHelper")
@SessionScoped
public final class AdministracionPortalRrhhHelper implements Serializable {

    /**
     * listaPortalRhhs.
     */
    private List<PortalRhh> listaPortalRhhs;

    /**
     * Portal RRHH seleccionado para edición/eliminación
     */
    private PortalRhh portalRrhhSeleccionado;

    /**
     * 
     */
    private Boolean esNuevo;

    public AdministracionPortalRrhhHelper() {
        super();
        iniciador();
    }

    public void iniciador() {
        setListaPortalRhhs(new ArrayList<PortalRhh>());

    }

    /**
     * @return the listaPortalRhhs
     */
    public List<PortalRhh> getListaPortalRhhs() {
        return listaPortalRhhs;
    }

    /**
     * @param listaPortalRhhs the listaPortalRhhs to set
     */
    public void setListaPortalRhhs(final List<PortalRhh> listaPortalRhhs) {
        this.listaPortalRhhs = listaPortalRhhs;
    }

    public PortalRhh getPortalRrhhSeleccionado() {
        return portalRrhhSeleccionado;
    }

    public void setPortalRrhhSeleccionado(PortalRhh portalRrhhSeleccionado) {
        this.portalRrhhSeleccionado = portalRrhhSeleccionado;
    }

    public Boolean getEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
    
    

}
