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
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.PostLoad;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@XmlRootElement(name = "distributivoDetalle")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "distributivo_detalles", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = DistributivoDetalle.BUSCAR_VIGENTES, query
            = "SELECT a FROM DistributivoDetalle a where a.vigente=true order by a.fechaInicio"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_DISTRIBUTIVO, query
            = "SELECT a FROM DistributivoDetalle a where a.vigente=true and a.idDistributivo=?1 "
            + " order by a.fechaInicio"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_UO_Y_ML, query
            = "SELECT a FROM DistributivoDetalle a where a.vigente=true AND  "
            + " a.distributivo.idInstitucionEjercicioFiscal=?1"
            + " AND a.distributivo.idUnidadOrganizacional=?2 AND a.distributivo.idModalidadLaboral=?3 "
            + " order by a.fechaInicio"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_SERVIDOR, hints = {
        @QueryHint(name = QueryHints.FETCH, value = "a.distributivo.unidadOrganizacional")},
            query = "SELECT a FROM DistributivoDetalle a WHERE a.vigente=true and a.servidor.id =?1"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_NOMBRES, query = "SELECT o FROM DistributivoDetalle o "
            + "WHERE o.vigente=true AND o.idServidor IS NOT NULL AND o.servidor.apellidosNombres LIKE ?1 AND "
            + "o.distributivo.idInstitucionEjercicioFiscal=?2"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_CODIGO, query = "SELECT o FROM DistributivoDetalle o "
            + "WHERE o.vigente=true AND o.codigoPuesto=?1 AND o.distributivo.idInstitucionEjercicioFiscal=?2"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_VACANTE_POR_CODIGO, query = "SELECT o FROM DistributivoDetalle o "
            + "WHERE o.vigente=true AND o.codigoPuesto=?1 AND o.idServidor IS NULL"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_SERVIDOR, query = "SELECT o FROM DistributivoDetalle o WHERE"
            + " o.servidor.tipoIdentificacion=?1 AND o.servidor.numeroIdentificacion=?2 AND "
            + "o.distributivo.idInstitucionEjercicioFiscal=?3 AND o.vigente=?4"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_SERVIDOR_POR_CODIGO, query = "SELECT o FROM DistributivoDetalle o "
            + "WHERE o.servidor.codigoEmpleado=?1 "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_UNIDAD_ORGANIZACIONAL, query = "SELECT o FROM "
            + "DistributivoDetalle o WHERE o.distributivo.unidadOrganizacional.codigo=?1 "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_UNIDAD_ORGANIZACIONAL_SERVIDOR, query = "SELECT o FROM "
            + "DistributivoDetalle o WHERE o.distributivo.unidadOrganizacional.codigo=?1 AND o.servidor.id=?2 "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_SERVIDOR_COMISION_SERVICIO, query = "SELECT o FROM "
            + "DistributivoDetalle o WHERE o.idServidorComisionServicio=?1 AND o.vigente=true "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_PARTIDA_INDIVIDUAL,
            query = "SELECT o FROM DistributivoDetalle o WHERE o.partidaIndividual=?1"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_CODIGOS_INTERNOS, query = "SELECT o FROM DistributivoDetalle o "
            + " WHERE o.vigente=true AND o.codigoPuesto in (?1) "
            + " ORDER BY o.distributivo.unidadOrganizacional.nombre, "
            + " o.distributivo.modalidadLaboral.nombre, o.unidadPresupuestaria.nombre, o.denominacionPuesto.nombre  "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_FINALIZACIONES_MES,
            query = "SELECT o FROM DistributivoDetalle o WHERE o.vigente=true AND o.servidor IS NOT NULL AND "
            + " o.fechaFin IS NOT NULL AND o.fechaFin BETWEEN ?1 AND ?2"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_IDENTIFICACION, query = "SELECT o FROM DistributivoDetalle o"
            + "  WHERE o.vigente=true AND o.servidor IS NOT NULL AND o.servidor.numeroIdentificacion=?1"),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_PARA_VACACIONES, query = "SELECT o FROM DistributivoDetalle o "
            + "WHERE o.vigente=true AND o.servidor.id IS NOT NULL AND o.escalaOcupacional.nivelOcupacional."
            + "idRegimenLaboral=?1 AND o.distributivo.institucionEjercicioFiscal.id=?2  "),
    @NamedQuery(name = DistributivoDetalle.BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL, 
            query = " SELECT o FROM DistributivoDetalle o "
            + " WHERE o.vigente=true AND o.servidor.id IS NOT NULL "
            + " AND o.distributivo.institucionEjercicioFiscal.id=?1  ")
})
@Getter
@Setter
public class DistributivoDetalle extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "DistributivoDetalle.buscarVigente";

    /**
     * Nombre para busqueda por servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR = "DistributivoDetalle.buscarPorServidor";

    /**
     * Nombre para busqueda por servidor.
     */
    public static final String BUSCAR_POR_DISTRIBUTIVO = "DistributivoDetalle.buscarPorDistributivo";

    /**
     *
     */
    public static final String BUSCAR_POR_NOMBRES = "DistributivoDetalle.buscarPorNombres";

    /**
     *
     */
    public static final String BUSCAR_POR_CODIGO = "DistributivoDetalle.buscarPorCodigo";

    /**
     *
     */
    public static final String BUSCAR_VACANTE_POR_CODIGO = "DistributivoDetalle.buscarVacantePorCodigo";

    /**
     *
     */
    public static final String BUSCAR_SERVIDOR = "DistributivoDetalle.buscarServidor";

    /**
     *
     */
    public static final String BUSCAR_SERVIDOR_POR_CODIGO = "DistributivoDetalle.buscarServidorPorCodigo";

    /**
     *
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL = "DistributivoDetalle.buscarServidorPorUnidadOrganizacional";

    /**
     *
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL_SERVIDOR = "DistributivoDetalle.buscarPorUnidadOrganizacionalServidor";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_COMISION_SERVICIO = "DistributivoDetalle.buscarPorServidorComisionServicio";

    /**
     *
     */
    public static final String BUSCAR_POR_UO_Y_ML = "DistributivoDetalle.buscarPorUnidadOrganicionalYModalidadLaboral";

    /**
     *
     */
    public static final String BUSCAR_PARA_VACACIONES = "DistributivoDetalle.buscarParaVacaciones";
    
    /**
     *
     */
    public static final String BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL = "DistributivoDetalle.buscarPorInstitucionEjercicioFiscal";

    /**
     *
     */
    public static final String BUSCAR_POR_PARTIDA_INDIVIDUAL = "DistributivoDetalle.buscarPorPartidaIndividual";

    /**
     *
     */
    public static final String BUSCAR_POR_CODIGOS_INTERNOS = "DistributivoDetalle.buscarPorCodigosInternos";

    /**
     *
     */
    public static final String BUSCAR_FINALIZACIONES_MES = "DistributivoDetalle.buscarFinalizacionesMes";
    /**
     *
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "DistributivoDetalle.buscarPorIdentificacion";

    /**
     * Id de Clase.
     */
    @Id
    @XmlElement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo partidaIndividual.
     */
    @XmlElement
    @Column(name = "partida_individual")
    private String partidaIndividual;

    /**
     * Campo rmu.
     */
    @XmlElement
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * Campo rmuOriginal.
     */
    @XmlElement
    @Column(name = "rmu_original")
    private BigDecimal rmuOriginal;

    /**
     * Campo sueldo básico.
     */
    @XmlElement
    @Column(name = "sueldo_basico")
    private BigDecimal sueldoBasico;

    /**
     * Campo rmuEscala.
     */
    @XmlElement
    @Column(name = "rmu_escala")
    private BigDecimal rmuEscala;

    /**
     * Campo rmuSobrevalorado.
     */
    @XmlElement
    @Column(name = "rmu_sobrevalorado")
    private BigDecimal rmuSobrevalorado;

    /**
     * Campo rmuSobrevalorado.
     */
    @XmlElement
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    /**
     * Campo fechaFin.
     */
    @XmlElement
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    /**
     * Campo fechaIngreso.
     */
    @XmlElement
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    /**
     * Campo tipoComisionServicio.
     */
    @XmlElement
    @Column(name = "tipo_comision_servicio")
    private String tipoComisionServicio;

    /**
     * Campo fechaInicioComisionServicio.
     */
    @XmlElement
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_comision_servicio")
    private Date fechaInicioComisionServicio;

    /**
     * Referencia unidades presupuestarias.
     */
    @JoinColumn(name = "unidad_presupuestaria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * Campo fechaFinComisionServicio.
     */
    @XmlElement
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin_comision_servicio")
    private Date fechaFinComisionServicio;

    /**
     *
     */
    @XmlElement
    @Transient
    private String certificacionPresupuestaria;

    /**
     *
     */
    @XmlElement
    @Column(name = "codigo_puesto")
    private Long codigoPuesto;

    /**
     *
     */
    @XmlElement
    @Column(name = "grupo_presupuestario")
    private String grupoPresupuestario;

    /**
     * Fecha inicio del encargo.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "fecha_inicio_encargo")
    private Date fechaInicioEncargo;

    /**
     * Fecha fin del encargo.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "fecha_fin_encargo")
    private Date fechaFinEncargo;

    /**
     * Fecha inicio de la subrogacion.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "fecha_inicio_subrogacion")
    private Date fechaInicioSubrogacion;

    /**
     * Fecha fin de la subrogacion.
     */
    @Temporal(value = TemporalType.DATE)
    @Column(name = "fecha_fin_subrogacion")
    private Date fechaFinSubrogacion;

    /**
     * Diistributivo del encargo.
     */
    @JoinColumn(name = "distributivo_detalle_encargo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalleEncargo;

    /**
     * Distributuvo de la subrogacion.
     */
    @JoinColumn(name = "distributivo_detalle_subrogacion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle distributivoDetalleSubrogacion;

    /**
     * Referencia ServidorComisionServicio.
     */
    @JoinColumn(name = "servidor_comision_servicio_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidorComisionServicio;

    /**
     * Referencia servidorComisionServicio.
     */
    @XmlElement
    @Column(name = "servidor_comision_servicio_id")
    private Long idServidorComisionServicio;

    /**
     * Referencia Escalas Ocupacionales.
     */
    @JoinColumn(name = "escala_ocupacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EscalaOcupacional escalaOcupacional;

    /**
     * Referencia Escalas Ocupacionales.
     */
    @XmlElement
    @Column(name = "escala_ocupacional_id")
    private Long idEscalaOcupacional;

    /**
     * Referencia denominacionPuesto.
     */
    @JoinColumn(name = "denominacion_puesto_id", insertable = false, updatable = false, nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private DenominacionPuesto denominacionPuesto;

    /**
     * Referencia denominacionPuesto.
     */
    @Column(name = "denominacion_puesto_id")
    private Long idDenominacionPuesto;

    /**
     * Referencia estadoPuesto.
     */
    @JoinColumn(name = "estado_puesto_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoPuesto estadoPuesto;

    /**
     * Referencia estadoPuesto.
     */
    @XmlElement
    @Column(name = "estado_puesto_id")
    private Long idEstadoPuesto;

    /**
     * Referencia ubicacionGeografica.
     */
    @JoinColumn(name = "ubicacion_geografica_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UbicacionGeografica ubicacionGeografica;

    /**
     * Referencia ubicacionGeografica.
     */
    @XmlElement
    @Column(name = "ubicacion_geografica_id")
    private Long idUbicacionGeografica;

    /**
     * Referencia servidor.
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Referencia servidor.
     */
    @XmlElement
    @Column(name = "servidor_id")
    private Long idServidor;

    /**
     * Referencia distributivo.
     */
    @XmlElement
    @JoinColumn(name = "distributivo_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Distributivo distributivo;

    /**
     * Referencia Distributivo.
     */
    @XmlElement
    @Column(name = "distributivo_id")
    private Long idDistributivo;

    /**
     * Unidad administrativa por cambio administrativo.
     */
    @JoinColumn(name = "unidad_organizacional_cambio_administrativo_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacionalCambioAdministrativo;

    /**
     * Unidad administrativa por cambio administrativo.
     */
    @Column(name = "unidad_organizacional_cambio_administrativo_id")
    private Long unidadOrganizacionalCambioAdministrativoId;

    /**
     * Estado adminiistracion puesto regimen laboral.
     */
    @JoinColumn(name = "estados_administracion_puestos_regimen_laboral_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoAdministracionPuestoRegimenLaboral estadosAdmPuestosRegimenLaboral;

    /**
     * Estado adminiistracion puesto regimen laboral.
     */
    @Column(name = "estados_administracion_puestos_regimen_laboral_id")
    private Long estadosAdmPuestosRegimenLaboralId;

    /**
     * Fecha maxima del cambio administrativo.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_maxima_cambio_administrativo")
    private Date fechaMaximoCambioAdministrativo;

    /**
     * Movimiento.
     */
    @JoinColumn(name = "movimiento_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Movimiento movimiento;

    /**
     * Movimiento.
     */
    @Column(name = "movimiento_id")
    private Long movimientoId;

    /**
     * Indica si el puesto esta seleccionado.
     */
    @Transient
    private Boolean selecto;

    /**
     * *
     * CAMPO PARA ALAMACENAR LA OBSERVACION CUANDO SE MODIFIQUE EL DISTRIBUTIVO DETALLE
     */
    @Column(name = "observacion_ultima_modificacion")
    private String observacionUltimaModificacion;

    /**
     * Constructor.
     */
    public DistributivoDetalle() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public DistributivoDetalle(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        //return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return ReflectionToStringBuilder.toString(UtilCadena.concatenar(id, "-servidor -", servidor != null ? servidor.
                getApellidosNombres() : null, "-escala-", escalaOcupacional != null ? escalaOcupacional.getCodigo()
                        : null));
    }

    /**
     *
     * @return
     */
    public String getProceso() {
        return UtilCadena.concatenar(escalaOcupacional.getNivelOcupacional().getRegimenLaboral().getNombre(),
                " / ", distributivo.getModalidadLaboral().getNombre());
    }

    /**
     *
     * @return
     */
    public String getSubProceso() {
        return distributivo.getUnidadOrganizacional().getRuta();
    }

    public static DistributivoDetalle getDistributivoDetalleVacio() {
        DistributivoDetalle distributivoDetalle = new DistributivoDetalle();
        //distributivoDetalle.setDistributivo(new Distributivo());
        distributivoDetalle.setDistributivo(Distributivo.getDistributivoVacio());
        distributivoDetalle.setEscalaOcupacional(new EscalaOcupacional());
        distributivoDetalle.setDenominacionPuesto(new DenominacionPuesto());
        distributivoDetalle.setMovimiento(new Movimiento());
        distributivoDetalle.setServidor(new Servidor());
        distributivoDetalle.setUnidadPresupuestaria(new UnidadPresupuestaria());
        //distributivoDetalle.getDistributivo().setModalidadLaboral(new ModalidadLaboral());
        //distributivoDetalle.getDistributivo().setUnidadOrganizacional(new UnidadOrganizacional());
        return distributivoDetalle;
    }

    public void setObservacionUltimaModificacion(String observacionUltimaModificacion) {
        this.observacionUltimaModificacion = observacionUltimaModificacion.toUpperCase();
    }

    @PostLoad
    public void postLoad() {
        for (CertificacionPresupuestaria cp : this.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
            if (this.distributivo.getModalidadLaboral().getCodigo().equals(cp.getModalidadLaboral().getCodigo())) {
                this.certificacionPresupuestaria = cp.getCertificacionPresupuestaria();
                break;
            }

        }
    }

}
