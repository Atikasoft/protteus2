/*
 *  ParametroGlobal.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.modelo.base;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entida basica del modelo.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@MappedSuperclass
public class EntidadBasica implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final String YES_NO = "yes_no";

    /**
     * Pattern usado en la validación de códigos.
     */
    public static final String PATTERN_CODIGO = "[a-zA-Z0-9_\\-\\.]+";

    /**
     * Mensaje usado cuando hay errores en la validación de códigos.
     */
    public static final String MENSAJE_VALIDACION_CODIGO = "{validacion.generica.formato.codigo}";

    /**
     * Longitud mínima de los códigos.
     */
    public static final int LONGITUD_MINIMA_CODIGO = 1;

    /**
     * Longitud mínima de los códigos.
     */
    public static final int LONGITUD_MINIMA_CODIGO_FCVD = 1;

    /**
     * Longitud máxima de los códigos para formulas, constantes, variables y dato adicional.
     */
    public static final int LONGITUD_MAXIMA_CODIGO_FCVD = 20;

    /**
     * Usuario de ultima actualizacion del objeto.
     */
    @Column(name = "vigente")
    @NotNull(message = "vigencia no puede ser null")
    protected Boolean vigente;

    /**
     * 
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false)
    @NotNull(message = "fechaCreacion no puede ser null")
    protected Date fechaCreacion;

    /**
     * 
     */
    @Column(name = "usuario_creacion", nullable = false, length = 40)
    @NotNull(message = "usuarioCreacion no pueder ser null")
    protected String usuarioCreacion;

    /**
     * 
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", nullable = true)
    protected Date fechaActualizacion;

    /**
     * 
     */
    @Column(name = "usuario_actualizacion", length = 40, nullable = true)
    protected String usuarioActualizacion;

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(final Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(final String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the fechaActualizacion
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * @param fechaActualizacion the fechaActualizacion to set
     */
    public void setFechaActualizacion(final Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * @return the usuarioActualizacion
     */
    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    /**
     * @param usuarioActualizacion the usuarioActualizacion to set
     */
    public void setUsuarioActualizacion(final String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    /**
     * @return the vigente
     */
    public Boolean getVigente() {
        return vigente;
    }

    /**
     * @param vigente the vigente to set
     */
    public void setVigente(final Boolean vigente) {
        this.vigente = vigente;
    }
}
