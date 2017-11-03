/*
 *  AccesoServidorVO.java
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
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;

/**
 * VO para representar los registros de un fichero de carga masiva de atributos
 * del servidor
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
public class CargaMasivaServidorVO implements Serializable {

    /**
     * Identificador en memoria
     */
    private Long id;

    /**
     * Linea en el csv
     */
    private Integer linea;

    /**
     * Codigo de tipo de documento
     */
    private String tipoDocumento;

    /**
     * Documento
     */
    private String documento;

    /**
     * Valor, representacion de 0|1
     */
    private Boolean valor;

    /**
     * Marca el registro como rechazado
     */
    private Boolean rechazado;

    /*
     * Constructor por defecto.
     */
    public CargaMasivaServidorVO() {
        super();
        valor = false;
        rechazado = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        if (id != null && id.equals(((CargaMasivaServidorVO) obj).getId())) {
            return true;
        }

        return false;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getValor() {
        return valor;
    }

    public void setValor(Boolean valor) {
        this.valor = valor;
    }

    public Boolean getRechazado() {
        return rechazado;
    }

    public void setRechazado(Boolean rechazado) {
        this.rechazado = rechazado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
