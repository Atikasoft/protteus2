/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.util.Date;

/**
 *
 * @author henry
 *
 * web service que permita entregar la información solicitada por el MDMQ como
 * dato de ingreso es el número de identificación (cédula del servidor), la
 * información que entregará el web service es:
 *
 * - CEDULA
 *
 * - APELLIDOS
 *
 * - NOMBRES
 *
 * - DENOMINACION (Cargo Ejemplo: Contador, Auditor)
 *
 * - DEPENDENCIAS
 *
 * - FECHA_INICIAL (Fecha de ingreso a la institución)
 *
 * - FECHA_FINAL (Fecha de salida a la institución)
 *
 * - NOMBRE_REGIMEN
 *
 * - MDMQ acepta la propuesta y se encargara de desarrollar la forma como
 * consumirá el * web service proporcionado por Atikasoft Cía. Ltda., para el
 * tema de Integración con * Auditorias.
 */
public class EmpleadoAuditoriaVO {

    /**
     *
     */
    private String tipoIdentificacion;
    /**
     *
     */
    private String numeroIdentificacion;
    /**
     *
     */
    private String nombres;
    /**
     *
     */
    private String apellidos;
    /**
     *
     */
    private String denominacion;
    /**
     *
     */
    private String dependencias;
    /**
     *
     */
    private String regimen;

    /**
     *
     */
    private Date fechaInicial;
    /**
     *
     */
    private Date fechaFinal;

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * @param denominacion the denominacion to set
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * @return the dependencias
     */
    public String getDependencias() {
        return dependencias;
    }

    /**
     * @param dependencias the dependencias to set
     */
    public void setDependencias(String dependencias) {
        this.dependencias = dependencias;
    }

    /**
     * @return the regimen
     */
    public String getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the regimen to set
     */
    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    /**
     * @return the fechaInicial
     */
    public Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param fechaInicial the fechaInicial to set
     */
    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * @return the fechaFinal
     */
    public Date getFechaFinal() {
        return fechaFinal;
    }

    /**
     * @param fechaFinal the fechaFinal to set
     */
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

}
