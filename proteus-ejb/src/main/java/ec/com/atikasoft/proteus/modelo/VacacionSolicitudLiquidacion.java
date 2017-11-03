/*
 *  VacacionSolicitud.java
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

import ec.com.atikasoft.proteus.enums.EstadoLiquidacionVacionEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@Entity
@Table(name = "vacaciones_solicitud_liquidacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = VacacionSolicitudLiquidacion.BUSCAR_TODOS_VIGENTES,
            query = "SELECT a FROM VacacionSolicitudLiquidacion a where a.vigente=true order by a.fechaCreacion desc "),
    @NamedQuery(name = VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_INSTITUCION,
            query = "SELECT a FROM VacacionSolicitudLiquidacion a WHERE a.vigente=true "
            + "AND a.servidorInstitucion.id=?1 order by a.fechaCreacion desc"),
    @NamedQuery(name = VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_INSTITUCION_Y_ESTADO,
            query = "SELECT a FROM VacacionSolicitudLiquidacion a WHERE a.vigente=true "
            + "AND a.servidorInstitucion.id=?1 AND a.estado=?2 order by a.fechaCreacion desc"),
    @NamedQuery(name = VacacionSolicitudLiquidacion.BUSCAR_POR_SERVIDOR_ID,
            query = "SELECT a FROM VacacionSolicitudLiquidacion a WHERE a.vigente=true AND "
            + "a.servidorInstitucion.servidor.id=?1 order by a.fechaCreacion desc")
})
@Setter
@Getter
public class VacacionSolicitudLiquidacion extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_TODOS_VIGENTES = "VacacionSolicitudLiquidacion.buscarTodosVigentes";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_INSTITUCION
            = "VacacionSolicitudLiquidacion.buscarPorServidorInstitucion";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_INSTITUCION_Y_ESTADO
            = "VacacionSolicitudLiquidacion.buscarPorServidorInstitucionEstado";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "VacacionSolicitudLiquidacion.buscarPorServidorId";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia servidorInstitucion.
     */
    @JoinColumn(name = "servidor_institucion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion servidorInstitucion;

    /**
     * servidorInstitucion id.
     */
    @Column(name = "servidor_institucion_id")
    private Long idServidorInstitucion;

    /**
     * Referencia servidorInstitucion que ejecuto la liquidacion.
     */
    @JoinColumn(name = "servidor_inst_elaborador_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion servidorInstitucionElaborador;

    /**
     * servidorInstitucionElaborador id.
     */
    @Column(name = "servidor_inst_elaborador_id")
    private Long idServidorInstitucionElaborador;

    /**
     * Referencia al regimen laboral.
     */
    @JoinColumn(name = "regimen_laboral_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;

    /**
     * servidorInstitucionElaborador id.
     */
    @Column(name = "regimen_laboral_id")
    private Long idRegimenLaboral;

    /**
     * Referencia a la Unidad Organizacional.
     */
    @JoinColumn(name = "unidad_organizacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    /**
     * unidadOrganizacional id.
     */
    @Column(name = "unidad_organizacional_id")
    private Long idUnidadOrganizacional;

    /**
     * Referencia al ultimo puesto ocupado por el servidor.
     */
    @JoinColumn(name = "ultimo_puesto_ocupado_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private DistributivoDetalle ultimoPuestoOcupado;

    /**
     * ultimo puesto ocupado por el servidor id.
     */
    @Column(name = "ultimo_puesto_ocupado_id")
    private Long idUltimoPuestoOcupado;

    /**
     *
     */
    @Column(name = "rmu")
    private BigDecimal rmu;

    /**
     * Campo saldoActual efectiva.
     */
    @Column(name = "saldo_vacaciones_efectivas")
    private Long saldoVacacionesEfectivas;

    /**
     * Campo saldoActual proporcionales.
     */
    @Column(name = "saldo_vacaciones_proporcionales")
    private Long saldoVacacionesProporcionales;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha_inicio")
    @Temporal(value = TemporalType.DATE)
    private Date fechaInicio;

    /**
     * Campo fecha.
     */
    @Column(name = "fecha_fin")
    @Temporal(value = TemporalType.DATE)
    private Date fechaFin;

    /**
     * SUSTENTO LEGAL PARA LA REALIZACION DE LA LIQUIDACION
     */
    @Column(name = "explicacion_liquidacion_vacacion")
    private String explicacionLiquidacionVacacion;

    /**
     * TIPO DE DOCUMENTO LEGAL PARA LA REALIZACION DE LA LIQUIDACION
     */
    @Column(name = "codigo_documento_previo")
    private String codigoDocumentoPrevio;

    /**
     * NUMERO DEL DOCUMENTO LEGAL PARA LA REALIZACION DE LA LIQUIDACION
     */
    @Column(name = "numero_documento_previo")
    private String numeroDocumentoPrevio;

    /**
     *
     */
    @Column(name = "fecha_documento_previo")
    @Temporal(value = TemporalType.DATE)
    private Date fechaDocumentoPrevio;

    /**
     * NUMERO DE ACCION PERSONAL QUE SE GENERA
     */
    @Column(name = "numero_accion_personal")
    private String numeroAccionPersonal;

    /**
     * Referencia al archivo pdf asociado a la accion de personal.
     */
    @JoinColumn(name = "archivo_accion_personal_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivoAccioPersonal;

    /**
     * Referencia al archivo pdf asociado a la accion de personal.
     */
    @Column(name = "archivo_accion_personal_id")
    private Long idArchivoAccionPersonal;

    /**
     *
     */
    @Column(name = "autoridad_nominadora")
    private String autoridadNominadora;

    /**
     *
     */
    @Column(name = "nombre_autoridad_nominadora")
    private String nombreAutoridadNominadora;

    /**
     *
     */
    @Column(name = "representante_rrhh")
    private String representanteRRHH;

    /**
     *
     */
    @Column(name = "nombre_representante_rrh")
    private String nombreRepresentanteRRHH;

    /**
     * ESTADO DE LA SOLICITUD DE LIQUIDACION
     */
    @Column(name = "estado")
    private String estado;

    /**
     * NOMBRE ESTADO DE LA SOLICITUD DE LIQUIDACION
     */
    @Transient
    private String estadoNombre;

    /**
     * INDICA SI SE DEBE O NO MOSTRAR LAS OPCIONES EN LA TABLA QUE LISTA LOS REGISTROS DE ESTE TIPO DE ENTIDAD
     */
    @Transient
    private Boolean deshabilitarOpciones;

    /**
     * Constructor.
     */
    public VacacionSolicitudLiquidacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public VacacionSolicitudLiquidacion(final Long id) {
        super();
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getEstadoNombre() {
        return EstadoLiquidacionVacionEnum.obtenerDescripcion(this.estado);
    }

    /**
     *
     * @param estadoNombre
     */
    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    /**
     *
     * @return
     */
    public Boolean getDeshabilitarOpciones() {
        return this.estado.equals(EstadoLiquidacionVacionEnum.REVERTIDO.getCodigo());
    }

    /**
     *
     * @param deshabilitarOpciones
     */
    public void setDeshabilitarOpciones(Boolean deshabilitarOpciones) {
        this.deshabilitarOpciones = deshabilitarOpciones;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
