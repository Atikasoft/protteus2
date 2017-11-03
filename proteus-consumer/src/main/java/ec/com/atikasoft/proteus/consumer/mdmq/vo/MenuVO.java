/*
 *  MenuVO.java
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
 *  Dec 10, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.consumer.mdmq.vo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class MenuVO implements Serializable {

    /**
     * Identificador del menu.
     */
    private String codigo;

    /**
     * Descripcion de la etiqueta.
     */
    private String etiqueta;

    /**
     * Url para acceder al recurso.
     */
    private String url;

    /**
     * Indicador del orden.
     */
    private int orden;

    /**
     * Lista de menus que contiene.
     */
    private List<MenuVO> menus;

    /**
     * Constructor sin argumentos.
     */
    public MenuVO() {
        super();

    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the etiqueta
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * @return the menus
     */
    public List<MenuVO> getMenus() {
        return menus;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @param etiqueta the etiqueta to set
     */
    public void setEtiqueta(final String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(final int orden) {
        this.orden = orden;
    }

    /**
     * @param menus the menus to set
     */
    public void setMenus(final List<MenuVO> menus) {
        this.menus = menus;
    }
}
