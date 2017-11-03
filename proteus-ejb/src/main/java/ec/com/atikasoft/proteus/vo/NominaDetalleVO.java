/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;

public class NominaDetalleVO implements Serializable {

    /**
     * Año.
     */
    private String year;

    /**
     * Nombre de Mes.
     */
    private String mes;

    /**
     * Numero de Mes.
     */
    private String numeroMes;

    /**
     * Numero nomina.
     */
    private String numero;

    /**
     * Tipo de nomina.
     */
    private String tipo;

    /**
     * Apellidos Nombres de servidor.
     */
    private String nombreServidor;

    /**
     * 
     * @return 
     */
    public String getYear() {
        return year;
    }

    /**
     * 
     * @param year 
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 
     * @return 
     */
    public String getMes() {
        return mes;
    }

    /**
     * 
     * @param mes 
     */
    public void setMes(String mes) {
        this.mes = mes;
    }

    /**
     * 
     * @return 
     */
    public String getNumeroMes() {
        return numeroMes;
    }

    /**
     * 
     * @param numeroMes 
     */
    public void setNumeroMes(String numeroMes) {
        this.numeroMes = numeroMes;
    }

    /**
     * 
     * @return 
     */
    public String getNumero() {
        return numero;
    }

    /**
     * 
     * @param numero 
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * 
     * @return 
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * 
     * @param tipo 
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * 
     * @return 
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * 
     * @param nombreServidor 
     */
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }
    
    
}
