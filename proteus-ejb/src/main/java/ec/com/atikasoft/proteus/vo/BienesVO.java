/*
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
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * VO para gestión de los bienes asignados
 */
public class BienesVO implements Serializable {

    /**
     * Item.
     */
    private String item;
    
    /**
     * Descripción del bien.
     */
    private String descripcion;
    
    /**
     * Características del bien.
     */
    private String caracteristicas;
    
    /**
     * Número de serie.
     */   
    private String serie;
   
    /**
     * Valor del bien.
     */   
    private BigDecimal valor;
    

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BienesVO(String item, String descripcion, String caracteristicas, String serie, BigDecimal valor) {
        this.item = item;
        this.descripcion = descripcion;
        this.caracteristicas = caracteristicas;
        this.serie = serie;
        this.valor = valor;
    }
    
    
    
}
