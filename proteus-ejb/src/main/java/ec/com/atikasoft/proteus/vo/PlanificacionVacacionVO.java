/*
 *  PlanificacionVacacion.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  19/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacion;
import ec.com.atikasoft.proteus.modelo.PlanificacionVacacionDetalle;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Permite administrar el registro de planificación de vacaciones para el ejercicio fiscal posterior al actual.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Setter
@Getter
public class PlanificacionVacacionVO implements Serializable {

    /**
     * Objeto tarea del gestor.
     */
    private PlanificacionVacacion planificacionVacacion;
    /**
     * detalle.
     */
    private PlanificacionVacacionDetalle planificacionVacacionDetalle;
    /**
     * Lista de detalles.
     */
    private List<PlanificacionVacacionDetalle> listaPlanificacionVacacionDetalles;
    /**
     * Lista de detalles eliminados.
     */
    private List<PlanificacionVacacionDetalle> listaDetallesEliminados;

    /**
     * Parametrizaciones.
     */
    private DistributivoDetalle distributivoDetalle;
    /**
     *
     */
    private VacacionParametro vacacionParametro;

    /**
     *
     */
    private Long saldoVacacion;
    /**
     *
     */
    private String saldoVacacionTexto;
    /**
     *
     */
    private Long saldoVacacionProporcional;
    /**
     *
     */
    private String saldoVacacionProporcionalTexto;
    /**
     *
     */
    private Long nroDiasSeleccionados;
    /**
     *
     */
    private String nroDiasSeleccionadosTexto;
    /**
     *
     */
    private List<Vacacion> listaSaldoVacacion = new ArrayList<Vacacion>();
    /**
     * Variables para calculos.
     */
    private Integer totalDias = 0;

    private Date fechaIngreso;

    /**
     * Fecha Inicio de Vacacion
     */
    private String fechaInicioDeVacacion;
    /**
     * Fecha Fin de Vacacion
     */
    private String fechaFinDeVacacion;

    /**
     *
     */
    public PlanificacionVacacionVO() {
        listaPlanificacionVacacionDetalles = new ArrayList<>();
        planificacionVacacion = new PlanificacionVacacion();
        planificacionVacacionDetalle = new PlanificacionVacacionDetalle();
        listaDetallesEliminados = new ArrayList<>();
        distributivoDetalle = new DistributivoDetalle();
        totalDias = 0;
        saldoVacacion = 0l;
        saldoVacacionTexto = "0 Días";
        saldoVacacionProporcionalTexto = "0 Días";
        nroDiasSeleccionadosTexto = "0 Días";
    }

}
