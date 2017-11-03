/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "fichaRrhhHelper")
@SessionScoped
public final class FichaRrhhHelper extends CatalogoHelper {

    /**
     * servidor.
     */
    private Servidor servidor;
    /**
     * nombreServidor.
     */
    private String nombreServidor;
    /**
     * nombreServidor.
     */
    private String numeroIdentificacion;
    /**
     * lista servidor.
     */
    private List<Servidor> listaServidores;

    public FichaRrhhHelper() {
        super();
        iniciador();
    }

    /**
     * metodo para iniciar las bariables.
     */
    public void iniciador() {
        setServidor(new Servidor());
        setListaServidores(new ArrayList<Servidor>());
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(final Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(final String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(final List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(final String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
