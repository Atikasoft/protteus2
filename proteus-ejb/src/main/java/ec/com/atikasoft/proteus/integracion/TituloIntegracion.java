/*
 *  CiudadanoIntegracion.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Oct 10, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.integracion;

import java.io.Serializable;

/**
 * Contiene los datos de los titulos de un ciudadano..
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class TituloIntegracion implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nivel del titulo.
     */
    private String nivelTitulo;

    /**
     * Nombre del titulo.
     */
    private String titulo;

    /**
     * Institución de Educacion Superior.
     */
    private String institucion;

    /**
     * Tipo.
     */
    private String tipo;

    /**
     * Numero de registro.
     */
    private String numeroRegistro;

    /**
     * Fecha de registro.
     */
    private String fechaRegistro;

    /**
     * Constructor sin argumentos.
     */
    public TituloIntegracion() {
        super();
    }

    /**
     * @return the nivelTitulo
     */
    public String getNivelTitulo() {
        return nivelTitulo;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return the institucion
     */
    public String getInstitucion() {
        return institucion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the numeroRegistro
     */
    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * @return the fechaRegistro
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param nivelTitulo the nivelTitulo to set
     */
    public void setNivelTitulo(final String nivelTitulo) {
        this.nivelTitulo = nivelTitulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final String institucion) {
        this.institucion = institucion;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param numeroRegistro the numeroRegistro to set
     */
    public void setNumeroRegistro(final String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(final String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
