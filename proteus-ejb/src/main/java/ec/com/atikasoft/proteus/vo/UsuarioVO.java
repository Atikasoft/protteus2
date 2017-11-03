/*
 *  UsuarioVO.java
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
 *  14/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.consumer.mdmq.vo.MenuVO;
import ec.com.atikasoft.proteus.consumer.mdmq.vo.ResultadoAutentificarVO;
import ec.com.atikasoft.proteus.modelo.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto de contiene los datos del usuario iniciado.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Setter
@Getter
public class UsuarioVO implements Serializable {

    /**
     * Indica si el usuario tiene acceso al sistema.
     */
    private Boolean conAcceso;

    /**
     * Indica si el usuario necesita cambiar de clave.
     */
    private Boolean cambiarClave;

    /**
     * REsultado de la autentificacion.
     */
    private ResultadoAutentificarVO resultadoAutentificarVO;

    /**
     * Opciones de menu que el usuario tiene acceso.
     */
    private List<MenuVO> menus;

    /**
     * Datos del servidor.
     */
    private Servidor servidor;

    /**
     * Dato de la institucion.
     */
    private Institucion institucion;

    /**
     * Datos del ejercicio fiscal.
     */
    private EjercicioFiscal ejercicioFiscal;

    /**
     * Datos de la instiotucion x ejercicio fiscal.
     */
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * Dato del distributivo.
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     * Usuario.
     */
    private String usuario;

    /**
     * Fecha de ingreso.
     */
    private Date fechaIngreso;
    /**
     * Indica si el usuario viene del MDMQ.
     */
    private Boolean esUsuarioWS;

    /**
     * Constructor por defecto.
     */
    public UsuarioVO() {
        super();
    }

}
