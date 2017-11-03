/*
 *  CatalogoControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  17/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.base;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public abstract class CatalogoControlador extends BaseControlador {

    /**
     * Clave para el mensaje de error de nemonico duplicado.
     */
    public static final String NEMONICO_DUPLICADO = "ec.gob.mrl.smp.genericos.mensaje.guardar.nemonico.duplicado";

    /**
     * Helper de clase.
     */
    private CatalogoHelper catalogoHelper = new CatalogoHelper();

    /**
     * Constructor sin parametros.
     */
    public CatalogoControlador() {
        super();
        catalogoHelper.setCampoBusqueda("");
    }

    /**
     * Este metodo sera implementado para procesar el guardado de un registro
     * de un caralogo.
     *
     * @return String
     */
    public abstract String guardar();

    /**
     * Este metodo inicia el proceso de edicion de un registro.
     *
     * @return String
     */
    public abstract String iniciarEdicion();

    /**
     * Este metodo inicia el proceso de creacion de un nuevo registro.
     *
     * @return String
     */
    public abstract String iniciarNuevo();

    /**
     * Este metodo es usado para procesar el borrado logico o fisico de un registro.
     *
     * @return String
     */
    public abstract String borrar();

    /**
     * Este metodo procesara la busqueda por parametros.
     *
     * @return String
     */
    public abstract String buscar();

    /**
     * @return the catalogoHelper
     */
    public CatalogoHelper getCatalogoHelper() {
        return catalogoHelper;
    }

    /**
     * @param catalogoHelper the catalogoHelper to set
     */
    public void setCatalogoHelper(final CatalogoHelper catalogoHelper) {
        this.catalogoHelper = catalogoHelper;
    }
}
