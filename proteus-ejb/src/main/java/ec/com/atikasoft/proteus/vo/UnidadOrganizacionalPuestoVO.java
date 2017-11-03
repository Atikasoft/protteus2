/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;

/**
 * Modela el numero total de puestos asignados a una unidad organizacional
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
public class UnidadOrganizacionalPuestoVO {

    /**
     *
     */
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * 
     */
    private Integer total;

    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    
}
