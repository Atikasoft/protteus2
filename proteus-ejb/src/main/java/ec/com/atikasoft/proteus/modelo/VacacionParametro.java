/*
 *  VacacionParametro.java
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
 *  28/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones_parametros", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionParametro.BUSCAR_POR_NOMBRE, query = "SELECT a FROM VacacionParametro a where "
            + "a.vigente=true and a.nombre LIKE ?1 order by a.nombre"),
    @NamedQuery(name = VacacionParametro.BUSCAR_POR_REGIMEN_LABORAL, query = "SELECT a FROM VacacionParametro a "
            + "where a.vigente=true and a.idRegimenLaboral = ?1 order by a.nombre"),
    @NamedQuery(name = VacacionParametro.BUSCAR_VIGENTES, query = "SELECT a FROM VacacionParametro a where "
            + "a.vigente=true order by a.nombre ")
})
@Setter
@Getter
public class VacacionParametro extends EntidadBasica {

    /* Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "VacacionParametro.buscarVigente";

    /* Nombre para busqueda de fechas vigentes.
     */
    public static final String BUSCAR_POR_NOMBRE = "VacacionParametro.buscarPorNombre";

    /* Nombre para busqueda de fechas vigentes por su regimen laboral.
     */
    public static final String BUSCAR_POR_REGIMEN_LABORAL = "VacacionParametro.buscarPorRegimenLaboral";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo nombre: Denominación específica del parametro de vacaciones
     */
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @NotNull
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo numero_dias: Número de días total de vacaciones. Cod Trab:15 - LOSEP:30
     */
    @Column(name = "numero_dias")
    @NotNull
    private Integer numeroDias;

    /**
     * Campo maximo_acumulacion: Máximo número de días o periodos a acumular para las vacaciones efectivas.
     */
    @Column(name = "maximo_acumulacion")
    @NotNull
    private Integer maximoAcumulacion;

    /**
     * Campo maximo_acumulacion: Máximo número de días o periodos a acumular para las vacaciones proporcionales.
     */
    @Column(name = "maximo_acumulacion_proporcionales")
    @NotNull
    private Integer maximoAcumulacionProporcionales;

    /**
     * Campo tipo_acumulacion: Tipo de acumulacion <D>ias,
     * <P>
     * eriodos
     */
    @Column(name = "tipo_acumulacion")
    @NotNull
    private String tipoAcumulacion;

    /**
     * Campo tipo_acumulacion: Número mínimo de dias que se pueden tomar por vacaciones.
     */
    @Column(name = "minimo_dias_tomar_vacacion")
    @NotNull
    private Integer minimoDiasTomarVacacion;

    /**
     * Campo maximos_dias_tomar_permisos: Número máximo de dias que se pueden tomar por permisos.
     */
    @Column(name = "maximos_dias_tomar_permisos")
    @NotNull
    private Integer maximosDiasTomarPermisos;

    /**
     * Campo periodo_inicio: Orden del periodos desde el cual aplica el parametro. Generalmente refiere a los años
     * (Ejercicio fiscal).
     */
    @Column(name = "periodo_inicio")
    @NotNull
    private Integer periodoInicio;

    /**
     * Campo periodo_fin: Orden del periodo hasta el cual aplica el parametro. Generalmente refiere a los años
     * (ejercicio fiscal).
     */
    @Column(name = "periodo_fin")
    @NotNull
    private Integer periodoFin;

    /**
     * Campo valor_incremento: Número de días que se eincrementan cada periodo
     */
    @Column(name = "valor_incremento")
    @NotNull
    private Integer valorIncremento;

    /**
     * Campo justificacion: Indica si existe justificación para la acumulación extraordinaria de vacaciones
     */
    @Column(name = "justificacion")
    @NotNull
    private Boolean justificacion;

    /**
     * Campo minimo_meses_derecho_vacacion. Número mínimo de meses que se deben tener para tener derecho a las
     * vacaciones
     */
    @Column(name = "minimo_meses_derecho_vacacion")
    @NotNull
    private Integer minimoMesesDerechoVacacion;

    /**
     * Campo indica numero de dias que se deben contar cuando el tipo de acumulación es en periodos. Si es nulo,
     * significa que solo se debn contar los periodos, con los días a los q tenga derecho. Este campo es usado para el
     * descuento de tiempo por acumulación en exceso.
     */
    @Column(name = "dias_periodo")
    private Integer diasEnPeriodo;

    /**
     *
     */
    @Column(name = "imputar_fin_semana_vacacion")
    private Boolean imputarFinSemanaVacacion;
    
    /**
     * Referencia a ejercicioFiscal
     */
    @JoinColumn(name = "regimen_laboral_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;
    /**
     * ejercicioFiscal id.
     */
    @Column(name = "regimen_laboral_id")
    private Long idRegimenLaboral;

    /**
     * Constructor.
     */
    public VacacionParametro() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionParametro(Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
