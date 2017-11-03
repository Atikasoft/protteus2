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

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public abstract class AdministracionControlador extends BaseControlador {

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
}
