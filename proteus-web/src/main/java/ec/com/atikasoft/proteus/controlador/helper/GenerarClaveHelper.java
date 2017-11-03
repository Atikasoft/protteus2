/**
 *  GenerarClaveHelper.java
 *  proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  26/09/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * GenerarClaveHelper
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "generarClaveHelper")
@SessionScoped
public class GenerarClaveHelper extends CatalogoHelper {

      /**
     * Variables para servidores.
     */
    private List<DistributivoDetalle> listaServidores;

    /**
     * filtro nombreServidor.
     */
    private String numeroIdentificacion;

    /**
     * filtro nombreServidor.
     */
    private String nombreServidor;
    /**
     * Variable de servidor.
     */
    private Servidor servidor;
    private String unidadOrganizacional;
    private String modalidadLaboral;
    private Boolean servidoresSinClave;
    
      /**
     * Servidores sin mail.
     */
    private List<Servidor> listaServidorError;

    /**
     * Constructor por defecto.
     */
    public GenerarClaveHelper() {
        super();
        iniciador();
    }
        
    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     */
    public final void iniciador() {
        setServidor(new Servidor());
        setListaServidores(new ArrayList<DistributivoDetalle>());
        listaServidorError= new ArrayList<Servidor>();
        servidoresSinClave = Boolean.FALSE;
    }

    /**
     * @return the listaServidores
     */
    public List<DistributivoDetalle> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<DistributivoDetalle> listaServidores) {
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
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
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
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
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
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
    /**
     * @return the unidadOrganizacional
     */
    public String getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(String unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the modalidadLaboral
     */
    public String getModalidadLaboral() {
        return modalidadLaboral;
    }

    /**
     * @param modalidadLaboral the modalidadLaboral to set
     */
    public void setModalidadLaboral(String modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }

    /**
     * @return the listaServidorError
     */
    public List<Servidor> getListaServidorError() {
        return listaServidorError;
    }

    /**
     * @param listaServidorError the listaServidorError to set
     */
    public void setListaServidorError(List<Servidor> listaServidorError) {
        this.listaServidorError = listaServidorError;
    }

    /**
     * @return the servidoresSinClave
     */
    public Boolean getServidoresSinClave() {
        return servidoresSinClave;
    }

    /**
     * @param servidoresSinClave the servidoresSinClave to set
     */
    public void setServidoresSinClave(Boolean servidoresSinClave) {
        this.servidoresSinClave = servidoresSinClave;
    }
}
