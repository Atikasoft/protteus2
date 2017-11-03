/*
 *  PlanificacionHorarioHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Planificacion Jornada Laboral
 *
 * @author Nelson Jumbo <nelson.jumbo@markasoft.ec>
 */
@ManagedBean(name = "planificacionJornadaLaboralHelper")
@SessionScoped
public class PlanificacionJornadaLaboralHelper extends CatalogoHelper {

    /**
     * Variables para asignación del garante del planificacionHorario.
     */
    private List<Servidor> listaServidores;

    /**
     * Servidor a ser editado
     */
    private Servidor servidor;
    
    /**
     * Servidor con la informacion antes de ser editada
     * misma que se mantendra intacta para efectos de generacion
     * de historicos
     */
    private Servidor servidorConDatosAntesDeEdicion;

    /**
     * nombreServidor.
     */
    private String nombreServidor;

    /**
     * nombreServidor.
     */
    private String numeroIdentificacion;

    /**
     * Constructor por defecto.
     */
    public PlanificacionJornadaLaboralHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del PlanificacionHorarioHelper.
     */
    public final void iniciador() {
        listaServidores = new ArrayList<Servidor>();
        servidor = new Servidor();
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
    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
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

    public Servidor getServidorConDatosAntesDeEdicion() {
        return servidorConDatosAntesDeEdicion;
    }

    public void setServidorConDatosAntesDeEdicion(Servidor servidorConDatosAntesDeEdicion) {
        this.servidorConDatosAntesDeEdicion = servidorConDatosAntesDeEdicion;
    }
}
