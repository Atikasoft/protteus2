/*
 *  PersonaVO.java
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
 *  Jan 9, 2014
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Servidor;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Getter
@Setter
public class PersonaVO {

    /**
     *
     */
    private String tipoIdentificacion;

    /**
     *
     */
    private String numeroIdentificacion;

    /**
     *
     */
    private String apellidosNombres;

    /**
     *
     */
    private String apellidos;
    /**
     *
     */
    private String nombres;

    /**
     *
     */
    private String estadoCivil;

    /**
     *
     */
    private Date fechaNacimiento;

    /**
     *
     */
    private String mail;

    /**
     *
     */
    private Servidor servidor;

    /**
     * Constructor sin argumentos.
     */
    public PersonaVO() {
        super();
    }

}
