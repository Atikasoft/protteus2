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
 *  Apr 11, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.integracion;

import java.io.Serializable;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class CiudadanoIntegracion implements Serializable {

    /**
     * Serial de la clase.
     */
    private static final long serialVersionUID = 836082858749270784L;

    private String cedula;

    private String digitoVerificador;

    private String nombreCedulado;

    private String fechaNacimiento;

    private String resideEnGuayaquil;

    private String condicionDeCedula;

    private String estadoCivil;

    private String nombreConyuge;

    private String poseeFichaIndice;

    private String poseeFichaDactilodcopica;

    /**
     * Constructor sin argumentos.
     */
    public CiudadanoIntegracion() {
        super();
    }

    /**
     * @return the cedula
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * @return the digitoVerificador
     */
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    /**
     * @return the nombreCedulado
     */
    public String getNombreCedulado() {
        return nombreCedulado;
    }

    /**
     * @return the fechaNacimiento
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * @return the resideEnGuayaquil
     */
    public String getResideEnGuayaquil() {
        return resideEnGuayaquil;
    }

    /**
     * @return the condicionDeCedula
     */
    public String getCondicionDeCedula() {
        return condicionDeCedula;
    }

    /**
     * @return the estadoCivil
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * @return the nombreConyuge
     */
    public String getNombreConyuge() {
        return nombreConyuge;
    }

    /**
     * @return the poseeFichaIndice
     */
    public String getPoseeFichaIndice() {
        return poseeFichaIndice;
    }

    /**
     * @return the poseeFichaDactilodcopica
     */
    public String getPoseeFichaDactilodcopica() {
        return poseeFichaDactilodcopica;
    }

    /**
     * @param cedula the cedula to set
     */
    public void setCedula(final String cedula) {
        this.cedula = cedula;
    }

    /**
     * @param digitoVerificador the digitoVerificador to set
     */
    public void setDigitoVerificador(final String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }

    /**
     * @param nombreCedulado the nombreCedulado to set
     */
    public void setNombreCedulado(final String nombreCedulado) {
        this.nombreCedulado = nombreCedulado;
    }

    /**
     * @param fechaNacimiento the fechaNacimiento to set
     */
    public void setFechaNacimiento(final String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * @param resideEnGuayaquil the resideEnGuayaquil to set
     */
    public void setResideEnGuayaquil(final String resideEnGuayaquil) {
        this.resideEnGuayaquil = resideEnGuayaquil;
    }

    /**
     * @param condicionDeCedula the condicionDeCedula to set
     */
    public void setCondicionDeCedula(final String condicionDeCedula) {
        this.condicionDeCedula = condicionDeCedula;
    }

    /**
     * @param estadoCivil the estadoCivil to set
     */
    public void setEstadoCivil(final String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    /**
     * @param nombreConyuge the nombreConyuge to set
     */
    public void setNombreConyuge(final String nombreConyuge) {
        this.nombreConyuge = nombreConyuge;
    }

    /**
     * @param poseeFichaIndice the poseeFichaIndice to set
     */
    public void setPoseeFichaIndice(final String poseeFichaIndice) {
        this.poseeFichaIndice = poseeFichaIndice;
    }

    /**
     * @param poseeFichaDactilodcopica the poseeFichaDactilodcopica to set
     */
    public void setPoseeFichaDactilodcopica(final String poseeFichaDactilodcopica) {
        this.poseeFichaDactilodcopica = poseeFichaDactilodcopica;
    }
}
