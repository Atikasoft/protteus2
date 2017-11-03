/*
 *  DistributivoDetalle.java
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
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.distributivo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "distributivo_detalles_historico", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DistributivoDetalleHistorico.BUSCAR_POR_IDENTIFICACION,
            query = "SELECT o FROM DistributivoDetalleHistorico o WHERE o.vigente=true"
            + " AND o.servidorTipoIdentificacion=?1 AND o.servidorNumeroIdentificacion=?2"
            + " ORDER BY o.fechaCreacion DESC "),
    @NamedQuery(name = DistributivoDetalleHistorico.BUSCAR_POR_IDENTIFICACION_Y_ESTADO,
            query = "SELECT o FROM DistributivoDetalleHistorico o WHERE o.vigente=true"
            + " AND o.servidorTipoIdentificacion=?1 AND o.servidorNumeroIdentificacion=?2 AND o.estadoPuestoCodigo=?3"
            + " ORDER BY o.fechaCreacion DESC "),
    @NamedQuery(name = DistributivoDetalleHistorico.BUSCAR_POR_PUESTO,
            query = "SELECT o FROM DistributivoDetalleHistorico o WHERE o.vigente=true AND o.codigoPuesto=?1 "
            + "ORDER BY o.fechaCreacion DESC ")
})
@Setter
@Getter
public class DistributivoDetalleHistorico extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "DistributivoDetalleHistorico.buscarPorIdentificacion";
    /**
     * 
     */
    public static final String BUSCAR_POR_IDENTIFICACION_Y_ESTADO = "DistributivoDetalleHistorico.buscarPorIdentificacionYEstado";

    /**
     *
     */
    public static final String BUSCAR_POR_PUESTO = "DistributivoDetalleHistorico.buscarPorPuesto";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo partidaIndividual.
     */
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * Campo rmu.
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * Campo sueldo básico.
     */
    @Column(name = "sueldo_basico")
    private BigDecimal sueldoBasico;

    /**
     * Campo rmuSobrevalorado.
     */
    @Column(name = "rmu_sobrevalorado")
    private BigDecimal rmuSobrevalorado;

    /**
     * Campo rmuSobrevalorado.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    /**
     * Campo fechaFin.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    /**
     * Campo fechaIngreso.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    /**
     * Campo tipoComisionServicio.
     */
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;

    /**
     * Campo fechaInicioComisionServicio.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_comision_servicio")
    private Date fechaInicioComisionServicio;

    /**
     * Campo fechaFinComisionServicio.
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin_comision_servicio")
    private Date fechaFinComisionServicio;

    /**
     *
     */
    @Column(name = "unidad_presupuestaria_codigo")
    private String unidadPresupuestariaCodigo;

    /**
     * Unidad Presupuestaria Nombre.
     */
    @Column(name = "unidad_presupuestaria_nombre")
    private String unidadPresupuestariaNombre;

    /**
     * Unidad Presupuestaria Centro Gestor.
     */
    @Column(name = "unidad_presupuestaria_centro_gestor")
    private String unidadPresupuestariaCentroGestor;

    /**
     * Unidad Presupuestaria Centro Costo.
     */
    @Column(name = "unidad_presupuestaria_centro_costo")
    private String unidadPresupuestariaCentroCosto;

    /**
     * Unidad Presupuestaria Proyecto.
     */
    @Column(name = "unidad_presupuestaria_proyecto")
    private String unidadPresupuestariaProyecto;

    /**
     * Unidad Presupuestaria Programa.
     */
    @Column(name = "unidad_presupuestaria_programa")
    private String unidadPresupuestariaPrograma;

    /**
     * Unidad Presupuestaria Fondo.
     */
    @Column(name = "unidad_presupuestaria_fondo")
    private String unidadPresupuestariaFondo;

    /**
     * Unidad Presupuestaria Sociedad.
     */
    @Column(name = "unidad_presupuestaria_sociedad")
    private String unidadPresupuestariaSociedad;

    /**
     * Unidad Presupuestaria Sector.
     */
    @Column(name = "unidad_presupuestaria_sector")
    private String unidadPresupuestariaSector;

    /**
     *
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     *
     */
    @Column(name = "codigo_puesto")
    private Long codigoPuesto;

    /**
     *
     */
    @Column(name = "grupo_presupuestario")
    private String grupoPresupuestario;

    /**
     * Servidor Comision Servicio Tipo Identificacion.
     */
    @Column(name = "servidor_comision_servicio_tipo_identificacion")
    private String servidorComisionServicioTipoIdentificacion;

    /**
     * Servidor Comision Servicio Numero Identificacion.
     */
    @Column(name = "servidor_comision_servicio_numero_identificacion")
    private String servidorComisionServicioNumeroIdentificacion;

    /**
     * Servidor Comision Servicio Nombres.
     */
    @Column(name = "servidor_comision_servicio_nombres")
    private String servidorComisionServicioNombres;

    /**
     * Regimen Laboral Codigo.
     */
    @Column(name = "regimen_laboral_codigo")
    private String regimenLaboralCodigo;

    /**
     * Regimen Laboral Nombre.
     */
    @Column(name = "regimen_laboral_nombre")
    private String regimenLaboralNombre;

    /**
     * Nivel Ocupacional Codigo.
     */
    @Column(name = "nivel_ocupacional_codigo")
    private String nivelOcupacionalCodigo;

    /**
     * Nivel Ocupacional Nombre.
     */
    @Column(name = "nivel_ocupacional_nombre")
    private String nivelOcupacionalNombre;

    /**
     * Escala Ocupacional Nombre.
     */
    @Column(name = "escala_ocupacional_codigo")
    private String escalaOcupacionalCodigo;

    /**
     *
     */
    @Column(name = "escala_ocupacional_nombre")
    private String escalaOcupacionalNombre;

    /**
     * Escala Ocupacional Grado.
     */
    @Column(name = "escala_ocupacional_grado")
    private String escalaOcupacionalGrado;

    /**
     * Escala Ocupacional Rmu.
     */
    @Column(name = "escala_ocupacional_rmu")
    private BigDecimal escalaOcupacionalRmu;

    /**
     * Escala Ocupacional Rmu Maximo.
     */
    @Column(name = "escala_ocupacional_rmu_maximo")
    private BigDecimal escalaOcupacionalRmuMaximo;

    /**
     * Denominacion Puesto Codigo.
     */
    @Column(name = "denominacion_puesto_codigo")
    private String denominacionPuestoCodigo;

    /**
     * Denominacion Puesto Nombre.
     */
    @Column(name = "denominacion_puesto_nombre")
    private String denominacionPuestoNombre;

    /**
     * Estado Puesto Codigo.
     */
    @Column(name = "estado_puesto_codigo")
    private String estadoPuestoCodigo;

    /**
     * Estado Puesto Nombre.
     */
    @Column(name = "estado_puesto_nombre")
    private String estadoPuestoNombre;

    /**
     * Ubicacion Geografica Codigo.
     */
    @Column(name = "ubicacion_geografica_codigo")
    private String ubicacionGeograficaCodigo;

    /**
     * Ubicacion Geografica Nombre.
     */
    @Column(name = "ubicacion_geografica_nombre")
    private String ubicacionGeograficaNombre;

    /**
     * Servidor Tipo Identificacion.
     */
    @Column(name = "servidor_tipo_identificacion")
    private String servidorTipoIdentificacion;

    /**
     * Servidor Numero Identificacion.
     */
    @Column(name = "servidor_numero_identificacion")
    private String servidorNumeroIdentificacion;

    /**
     * Servidor Nombres.
     */
    @Column(name = "servidor_nombres")
    private String servidorNombres;

    /**
     * Unidad Organizacional Codigo.
     */
    @Column(name = "unidad_organizacional_codigo")
    private String unidadOrganizacionalCodigo;

    /**
     * Unidad Organizacional Nombre.
     */
    @Column(name = "unidad_organizacional_nombre")
    private String unidadOrganizacionalNombre;

    /**
     * Modalidad Laboral Codigo.
     */
    @Column(name = "modalidad_laboral_codigo")
    private String modalidadLaboralCodigo;

    /**
     * Modalidad Laboral Nombre.
     */
    @Column(name = "modalidad_laboral_nombre")
    private String modalidadLaboralNombre;

    /**
     * Unidad Organizacional Cambio Administrativo Codigo.
     */
    @Column(name = "unidad_organizacional_cambio_administrativo_codigo")
    private String unidadOrganizacionalCambioAdministrativoCodigo;

    /**
     * Unidad Organizacional Cambio Administrativo Nombre.
     */
    @Column(name = "unidad_organizacional_cambio_administrativo_nombre")
    private String unidadOrganizacionalCambioAdministrativoNombre;

    /**
     * Fecha maxima del cambio administrativo.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_maxima_cambio_administrativo")
    private Date fechaMaximoCambioAdministrativo;

    /**
     * Movimiento id.
     */
    @Column(name = "movimiento_id")
    private Long movimientoId;

    /**
     *
     */
    @Column(name = "estado_administracion_puesto")
    private String estadoAdministracionPuesto;

    /**
     *
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * Constructor.
     */
    public DistributivoDetalleHistorico() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DistributivoDetalleHistorico(final Long id) {
        super();
        this.id = id;
    }
}
