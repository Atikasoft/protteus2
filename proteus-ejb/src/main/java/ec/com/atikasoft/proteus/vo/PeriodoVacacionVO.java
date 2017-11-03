/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import java.util.Date;

/**
 *
 * @author henry
 */
public class PeriodoVacacionVO {

    /**
     *
     */
    private ServidorInstitucion servidorInstitucion;
    /**
     *
     */
    private Date fechaInicio;

    /**
     *
     */
    private Date fechaFinal;

    /**
     *
     */
    private Long saldo;

    /**
     * @return the servidorInstitucion
     */
    public ServidorInstitucion getServidorInstitucion() {
        return servidorInstitucion;
    }

    /**
     * @param servidorInstitucion the servidorInstitucion to set
     */
    public void setServidorInstitucion(ServidorInstitucion servidorInstitucion) {
        this.servidorInstitucion = servidorInstitucion;
    }

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    /**
     * @return the saldo
     */
    public Long getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

}
