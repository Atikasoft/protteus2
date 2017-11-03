/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Setter
@Getter
public class RegistroReporteSaldosVacacionesVO {

    /**
     * Tipo de identificacion.
     */
    private String tipoIdentificacion;

    /**
     * Numero de identificacion.
     */
    private String numeroIdentificacion;

    /**
     * Nombre y apellidos.
     */
    private String apellidosNombres;

    /**
     * Saldo.
     */
    private Long saldo;

    /**
     * Saldo proporcional.
     */
    private Long saldoProporcional;

    /**
     * Nombre de Unidad Organizacional.
     */
    private String nombreUnidadOrganizacional;

    /**
     * Codigo de Unidad Organizacional.
     */
    private String codigoUnidadOrganizacional;

    /**
     * Duración Jornada laboral del servidor
     */
    private int jornada;

}
