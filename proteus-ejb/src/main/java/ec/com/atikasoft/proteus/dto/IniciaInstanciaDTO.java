/*
 *  IniciaInstanciaDTO.java
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
 *  Mar 1, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dto;

/**
 * Contiene los datos necesario para crear una nueva instancia.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class IniciaInstanciaDTO {

    /**
     * Codigo de la institucion.
     */
    private String codigoInstitucion;

    /**
     * Nombre de la institucion.
     */
    private String nombreInstitucion;

    /**
     * Ejercicio fiscal de la institucion.
     */
    private Integer ejercicioFiscal;

    /**
     * Codigo del proceso.
     */
    private String codigoProceso;

    /**
     * Identificado externo.
     */
    private Long identificadorExterno;

    /**
     * Identificador de la institucion.
     */
    private Long institucionId;

    /**
     * Indica quien origino la instancia.
     */
    private String origen;

    /**
     * Comentario realizados por el usuario.
     */
    private String comentario;

    /**
     * Identificacion del usuario.
     */
    private String usuario;

    /**
     * Nombre del usuario.
     */
    private String nombreUsuario;

    /**
     * Numero externo de la nueva instancia.
     */
    private String numeroExterno;

    /**
     * Constructor sin argumentos.
     */
    public IniciaInstanciaDTO() {
        super();

    }

    /**
     * @return the codigoInstitucion
     */
    public String getCodigoInstitucion() {
        return codigoInstitucion;
    }

    /**
     * @return the ejercicioFiscal
     */
    public Integer getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    /**
     * @return the codigoProceso
     */
    public String getCodigoProceso() {
        return codigoProceso;
    }

    /**
     * @return the identificadorExterno
     */
    public Long getIdentificadorExterno() {
        return identificadorExterno;
    }

    /**
     * @return the institucionId
     */
    public Long getInstitucionId() {
        return institucionId;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @return the numeroExterno
     */
    public String getNumeroExterno() {
        return numeroExterno;
    }

    /**
     * @param codigoInstitucion the codigoInstitucion to set
     */
    public void setCodigoInstitucion(final String codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    /**
     * @param ejercicioFiscal the ejercicioFiscal to set
     */
    public void setEjercicioFiscal(final Integer ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    /**
     * @param codigoProceso the codigoProceso to set
     */
    public void setCodigoProceso(final String codigoProceso) {
        this.codigoProceso = codigoProceso;
    }

    /**
     * @param identificadorExterno the identificadorExterno to set
     */
    public void setIdentificadorExterno(final Long identificadorExterno) {
        this.identificadorExterno = identificadorExterno;
    }

    /**
     * @param institucionId the institucionId to set
     */
    public void setInstitucionId(final Long institucionId) {
        this.institucionId = institucionId;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(final String origen) {
        this.origen = origen;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(final String comentario) {
        this.comentario = comentario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(final String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @param numeroExterno the numeroExterno to set
     */
    public void setNumeroExterno(final String numeroExterno) {
        this.numeroExterno = numeroExterno;
    }

    /**
     * @return the nombreInstitucion
     */
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    /**
     * @param nombreInstitucion the nombreInstitucion to set
     */
    public void setNombreInstitucion(final String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }
}
