/*
 *  ConsultaTramiteVO.java
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
 *  08/01/2013
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * VO Consulta Tramite.
 *
 * @author Victor Quimbiamba <victor;quimbiamba@atikasoft;com;ec>
 */
@Setter
@Getter
public class ConsultaTramiteVO {

    /**
     * Codigo Institucion.
     */
    private Long codigoInstitucion = null;

    /**
     *
     */
    private Long grupo = null;

    /**
     *
     */
    private Long clase = null;

    /**
     * Tipo Movimiento.
     */
    private Long tipoMovimiento = null;

    /**
     * Tipo Identificacion.
     */
    private String tipoIdentificacion = null;

    /**
     * Numero Tramite.
     */
    private String numeroTramite = null;

    /**
     * Numero Documento Habilitante.
     */
    private String numeroDocumentoHabilitante = null;

    /**
     * Estado.
     */
    private String estado = null;

    /**
     * Partida Presupuestaria General.
     */
    private String partidaPresupuestariaGeneral = null;

    /**
     * Codigo de puesto.
     */
    private Long codigoPuesto = null;

    /**
     * Partida Presupuestaria Individual.
     */
    private String partidaPresupuestariaIndividual = null;

    /**
     * Fecha Elaboracion Desde.
     */
    private Date fechaElaboracionDesde = null;

    /**
     * Fecha Elaboracion Hasta.
     */
    private Date fechaElaboracionHasta = null;

    /**
     * Fecha Revision Desde.
     */
    private Date fechaRevisionDesde = null;

    /**
     * Fecha Revision Hasta.
     */
    private Date fechaRevisionHasta = null;

    /**
     * Fecha Validacion Desde.
     */
    private Date fechaValidacionDesde = null;

    /**
     * Fecha Validacion Hasta.
     */
    private Date fechaValidacionHasta = null;

    /**
     * Fecha Aprobacion Desde.
     */
    private Date fechaAprobacionDesde = null;

    /**
     * Fecha Aprobacion Hasta.
     */
    private Date fechaAprobacionHasta = null;

    /**
     * Fecha Legalizacion Desde.
     */
    private Date fechaLegalizacionDesde = null;

    /**
     * Fecha Legalizacion Hasta.
     */
    private Date fechaLegalizacionHasta = null;

    /**
     * Fecha Vigencia Desde.
     */
    private Date fechaVigenciaDesde = null;

    /**
     * Fecha Vigencia Hasta.
     */
    private Date fechaVigenciaHasta = null;

    /**
     * Nombres.
     */
    private String nombres = null;

    /**
     * Numero Identificacion.
     */
    private String numeroIdentificacion = null;

    /**
     * Numero de registros contados.
     */
    private Long totalRegistros = 0L;

    /**
     * Cedula del servidor.
     */
    private String cedulaServidor = "";

    /**
     * Bandera para busqueda.
     */
    private Boolean consultaServidor = Boolean.FALSE;
    /**
     * Campo para el filtro.
     */
    private String unidadAdministrativaNombre;

    /**
     * Campo para el filtro.
     */
    private Long unidadAdministrativaId;

    /**
     *
     */
    private Long institucionEjercicioFiscalId;

    /**
     *
     */
    private Boolean esRRHH;

    /**
     *
     */
    private UsuarioVO usuarioVO;

    /**
     * 
     */
    private List<UnidadOrganizacional> unidades;

    /**
     * Constructor por defecto.
     */
    public ConsultaTramiteVO() {
        super();
    }

}
