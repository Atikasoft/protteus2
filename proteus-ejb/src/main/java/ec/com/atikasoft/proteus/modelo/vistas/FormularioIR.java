/**
 * FormularioIR.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * AtikaSoft ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with AtikaSoft.
 *
 *
 * AtikaSoft Quito - Ecuador http://www.atikasoft.com.ec/
 *
 * 03/01/2014
 *
 * Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Registra información para el formulario 107 de Impuesto a la Renta del SRI.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vista_formularioIR", catalog = "sch_proteus")
@Setter
@Getter
public class FormularioIR implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Column(name = "ejercicio_fiscal")
    private String ejercicioFiscal;

    /**
     *
     */
    @Id
    @Column(name = "ejercicio_fiscal_id")
    private Long ejercicioFiscalId;

    /**
     *
     */
    @Id
    @Column(name = "institucion_ruc")
    private String institucionRuc;

    /**
     *
     */
    @Column(name = "institucion_nombre")
    private String institucionNombre;

    /**
     *
     */
    @Column(name = "codigo_unidad_organizacional")
    private String unidadOrganizacionalCodigo;

    /**
     *
     */
    @Column(name = "unidad_organizacional")
    private String unidadOrganizacionalNombre;

    /**
     *
     */
    @Id
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long institucionEjercicioFiscalId;

    /**
     *
     */
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    /**
     *
     */
    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    /**
     *
     */
    @Column(name = "nombres")
    private String nombres;

    /**
     * Campo del id del servidor.
     */
    @Id
    @Column(name = "servidor_id")
    private Long servidorId;

    /**
     * Sueldos y salarios.
     */
    @Column(name = "sueldos_salarios")
    private BigDecimal sueldosSalarios;

    /**
     * SOBRESUELDOS, COMISIONES, BONOS Y OTROS INGRESOS GRAVADOS.
     */
    @Column(name = "otros_ingresos")
    private BigDecimal otrosIngresos;

    /**
     * SOBRESUELDOS, COMISIONES, BONOS Y OTROS INGRESOS GRAVADOS.
     */
    @Column(name = "ingresos_otro_empleador")
    private BigDecimal ingresosOtroEmpleador;

    /**
     *
     */
    @Column(name = "decimo_tercero")
    private BigDecimal decimoTercero;

    /**
     *
     */
    @Column(name = "decimo_cuarto")
    private BigDecimal decimocuarto;
    /**
     *
     */
    @Column(name = "fondo_reserva")
    private BigDecimal fondoReserva;
    /**
     *
     */
    @Column(name = "aporte_personal")
    private BigDecimal aportePersonal;
    /**
     *
     */
    @Column(name = "vivienda")
    private BigDecimal vivienda;
    /**
     *
     */
    @Column(name = "alimentacion")
    private BigDecimal alimentacion;
    /**
     *
     */
    @Column(name = "vestimenta")
    private BigDecimal vestimenta;
    /**
     *
     */
    @Column(name = "educacion")
    private BigDecimal educacion;
    /**
     *
     */
    @Column(name = "salud")
    private BigDecimal salud;
    /**
     *
     */
    @Column(name = "exoneracion_discapacidad")
    private BigDecimal exoneracionDiscapacidad;
    /**
     *
     */
    @Column(name = "exoneracion_tercera_edad")
    private BigDecimal exoneracionTerceraEdad;

    /**
     *
     */
    @Column(name = "impuesto_renta")
    private BigDecimal impuestoRenta;

    /**
     * Constructor sin argumentos.
     */
    public FormularioIR() {
        super();
    }
}
