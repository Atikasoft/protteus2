/*
 *  BusquedaPuestoVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * VO para la Busqueda de Puesto.
 *
 *
 */
@Setter
@Getter
public class BusquedaVacacionVO implements Serializable {

    /**
     *
     */
    private Long intitucionEjercicioFiscalId;
    /**
     *
     */
    private Long unidadAdministrativaId;
    /**
     * Campo para el filtro.
     */
    private String unidadAdministrativaNombre;
    /**
     *
     */
    private Long distributivoDetalleId;
    /**
     * Campo para el filtro.
     */
    private String regimenLaboralNombre;
    /**
     *
     */
    private Long regimenLaboralId;
    /**
     * Campo para el filtro.
     */
    private String estadoPlanificacionId;
    /**
     * Campo para el filtro.
     */
    private Long estadoServidorId;
    /**
     * Campo para el filtro.
     */
    private String nombreServidor;
    /**
     * Campo para el filtro.
     */
    private String tipoDocumentoServidor;
    /**
     * Campo para el filtro.
     */
    private String numeroDocumentoServidor;
    /**
     * fecha inicio de la planificacion.
     */
    private Date fechaInicioPlanificacio;
    /**
     * fecha fin de la planificacion.
     */
    private Date fechaFinPlanificacio;

    /**
     * Campo para el filtro.
     */
    private String estadoVacacion;
    /**
     * Campo para el filtro.
     */
    private String tipoVacacion;

    /**
     *
     */
    private UsuarioVO usuarioVO;

    /**
     * Constructor por defecto.
     */
    public BusquedaVacacionVO() {
        super();
    }

}
