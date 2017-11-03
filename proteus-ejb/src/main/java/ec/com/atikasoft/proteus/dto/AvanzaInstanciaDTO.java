/*
 *  IniciaInstanciaDTO.java
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
 *  Mar 1, 2013
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.dto;

import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Contiene los datos necesario para avanzar una instancia de estado.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Setter
@Getter
public class AvanzaInstanciaDTO {

    /**
     * Codigo de la institucion.
     */
    private String codigoInstitucion;

    /**
     * Nombre de la institucion.
     */
    private String nombreInstitucion;

    /**
     * Ejercicio fiscal actual.
     */
    private Integer ejercicioFiscal;

    /**
     * Codigo del proceso.
     */
    private String codigoProceso;

    /**
     * Fase inicial.
     */
    private String codigoFaseInicial;

    /**
     * Fase final.
     */
    private String codigoFaseFinal;

    /**
     * Comentario de la operacion.
     */
    private String comentario;

    /**
     * Identificacion del usuario.
     */
    private String usuario;

    /**
     * Nombre del usuario.
     */
    private String usuarioNombre;

    /**
     * Identificador de la institucion.
     */
    private Long institucionId;

    /**
     * Identificador de la tarea.
     */
    private Long tareaId;

    /**
     * Identificador de la instancia.
     */
    private Long instanciaId;

    /**
     * Identificador del usuario que se debe asignar si la asignacion de usuario es manual.
     */
    private String usuarioAsignar;
    
    /**
     * 
     */
    private List<UnidadOrganizacional> unidadesAcceso;


}
