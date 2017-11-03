/*
 *  MenuVO.java
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
 * VO para gestión de los menus
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public class MenuVO implements Serializable {

    private Long rolId;

    private Long servidorId;

    private Long menuPrincipalId;

    private Boolean esPrincipal;
    
    private String tipo;

    /**
     * Constructor por defecto.
     */
    public MenuVO() {
        super();

    }

    /**
     * @return the rolId
     */
    public Long getRolId() {
        return rolId;
    }

    /**
     * @param rolId the rolId to set
     */
    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    /**
     * @return the esPrincipal
     */
    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    /**
     * @param esPrincipal the esPrincipal to set
     */
    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    /**
     * @return the servidorId
     */
    public Long getServidorId() {
        return servidorId;
    }

    /**
     * @param servidorId the servidorId to set
     */
    public void setServidorId(Long servidorId) {
        this.servidorId = servidorId;
    }

    /**
     * @return the menuPrincipalId
     */
    public Long getMenuPrincipalId() {
        return menuPrincipalId;
    }

    /**
     * @param menuPrincipalId the menuPrincipalId to set
     */
    public void setMenuPrincipalId(Long menuPrincipalId) {
        this.menuPrincipalId = menuPrincipalId;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
