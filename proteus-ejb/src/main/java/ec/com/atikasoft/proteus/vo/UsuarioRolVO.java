/*
 *  UsuarioRolVO.java
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
 *  04/01/2013
 *
 */

package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.Servidor;
import java.io.Serializable;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */

public class UsuarioRolVO implements Serializable {
    /**
     * numero de asignaciones.
     */
    private Long numeroAsignaciones;
    /**
     * SERVIDOR.
     */
    private Servidor servidor;
    
    /**
     * Rol
     */
    private Rol rol;
    
    /**
     * Nombre del Rol
     */
    private String nombreRol;
    
    /**
     * Nombres del servidor
     */
    private String nombresServidor;
    
    /**
     * Numero de cedula
     */
    private String numeroIdentificacion;
    
    /**
     * CODIGO ROL.
     */
    private String codigoRol;
    /**
     * variable para delegacion de aprobador.
     */
    private Boolean aprobado = Boolean.FALSE;
    /**
     * variable para maxima autoridad.
     */
    private String maximaAutoridad = SiNoEnum.NO.getDescripcion();
    /**
     * variable id para delegación de aprobacion
     */
    private Long idDelegacionAprobacion = 0L;
    /**
     * Constructor.
     */
    public UsuarioRolVO() {
        super();
    }

    /**
     * @return the numeroAsignaciones
     */
    public Long getNumeroAsignaciones() {
        return numeroAsignaciones;
    }

    /**
     * @param numeroAsignaciones the numeroAsignaciones to set
     */
    public void setNumeroAsignaciones(final Long numeroAsignaciones) {
        this.numeroAsignaciones = numeroAsignaciones;
    }

    /**
     * @return the aprobado
     */
    public Boolean getAprobado() {
        return aprobado;
    }

    /**
     * @param aprobado the aprobado to set
     */
    public void setAprobado(final Boolean aprobado) {
        this.aprobado = aprobado;
    }

    /**
     * @return the maximaAutoridad
     */
    public String getMaximaAutoridad() {
        return maximaAutoridad;
    }

    /**
     * @param maximaAutoridad the maximaAutoridad to set
     */
    public void setMaximaAutoridad(final String maximaAutoridad) {
        this.maximaAutoridad = maximaAutoridad;
    }

    /**
     * @return the idDelegacionAprobacion
     */
    public Long getIdDelegacionAprobacion() {
        return idDelegacionAprobacion;
    }

    /**
     * @param idDelegacionAprobacion the idDelegacionAprobacion to set
     */
    public void setIdDelegacionAprobacion(Long idDelegacionAprobacion) {
        this.idDelegacionAprobacion = idDelegacionAprobacion;
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
     * @return the codigoRol
     */
    public String getCodigoRol() {
        return codigoRol;
    }

    /**
     * @param codigoRol the codigoRol to set
     */
    public void setCodigoRol(final String codigoRol) {
        this.codigoRol = codigoRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getNombresServidor() {
        return nombresServidor;
    }

    public void setNombresServidor(String nombresServidor) {
        this.nombresServidor = nombresServidor;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }
}
