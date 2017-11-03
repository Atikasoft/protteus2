/*
 *  DocumentoHabilitanteControlador.java
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
 *  05/11/2012
 *
 */

package ec.com.atikasoft.proteus.controlador.base;

import ec.com.atikasoft.proteus.utilitarios.Utilitarios;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */

public abstract class DocumentoHabilitanteControlador extends Utilitarios {
    /**
     * Metodo iniciador, recomendado para el uso con @PostConstruct.
     */
    public abstract void init();

}
