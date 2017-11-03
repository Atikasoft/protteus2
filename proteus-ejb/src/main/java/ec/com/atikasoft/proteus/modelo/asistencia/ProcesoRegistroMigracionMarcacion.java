/*
 *  Anticipo.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.asistencia;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Proceso de migracion de marcaciones
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Entity
@Table(name = "proceso_registro_migracion_marcaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ProcesoRegistroMigracionMarcacion.PROCESO_EN_CURSO, 
            query = "Select o From ProcesoRegistroMigracionMarcacion o where "
                    + " o.vigente = true and o.horaInicio is not null and o.horaFin is null "),
    @NamedQuery(name = ProcesoRegistroMigracionMarcacion.MIGRACIONES_ORDENADAS, 
            query = "Select o From ProcesoRegistroMigracionMarcacion o where "
                    + " o.vigente = true Order by o.fechaHasta desc ")
})
@Setter
@Getter
public class ProcesoRegistroMigracionMarcacion extends EntidadBasica {
    
    /**
     * 
     */
    public static final String PROCESO_EN_CURSO = "ProcesoRegistroMigracionMarcacion.procesoEnCurso";
    
    public static final String MIGRACIONES_ORDENADAS = "ProcesoRegistroMigracionMarcacion.migracionesOrdenadas";
    
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Fecha de ejecucion del cron
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;

    /**
     * Hora de inicio de ejecicion del cron
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_inicio")
    private Date horaInicio;

    /**
     * Hora de fin de ejecicion del cron
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "hora_fin")
    private Date horaFin;

    /**
     * Fecha de inicio del rango de dias a tomar para la migracion
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_desde")
    private Date fechaDesde;

    /**
     * Fecha de fin del rango de dias a tomar para la migracion
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_hasta")
    private Date fechaHasta;

    /**
     * true cuando la migracion se complete sin errores
     */
    @Column(name = "proceso_registro")
    private Boolean procesoRegistro;

    /**
     * true cuando la seleccion de las 4 marcadas efectivas se complete sin
     * errores
     */
    @Column(name = "proceso_seleccion_marcadas")
    private Boolean procesoSeleccionMarcadas;

    /**
     * true cuando la determinacion de atrasos se complete sin errores
     */
    @Column(name = "proceso_determinacion_atrasos")
    private Boolean procesoDeterminacionAtrasos;

    /**
     * true cuando la determinacion de horas extras se complete sin errores
     */
    @Column(name = "proceso_determinacion_horas_ext")
    private Boolean procesoDeterminacionHorasExt;

    /**
     * true cuando todo el proceso se complete sin errores
     */
    @Column(name = "ejecucion_exito")
    private Boolean ejecucionExito;

    /**
     * Detalle de errores
     */
    @Column(name = "errores")
    private String errores;

    /**
     * 
     */
    @Column(name = "comentario")
    private String comentario;
    
    /**
     * 
     */
    @Column(name = "estado")
    private String estado;
    
    /**
     * Constructor.
     */
    public ProcesoRegistroMigracionMarcacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ProcesoRegistroMigracionMarcacion(final Long id) {
        super();
        this.id = id;
    }

    public ProcesoRegistroMigracionMarcacion(final Date fechaCreacion,
            final Date fechaDesde, final Date fechaHasta) {
        super();
        ejecucionExito = false;
        this.fechaCreacion = fechaCreacion;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        procesoDeterminacionAtrasos = false;
        procesoDeterminacionHorasExt = false;
        procesoRegistro = false;
        procesoSeleccionMarcadas = false;
        vigente = true;
        usuarioCreacion = "timer";
        
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
