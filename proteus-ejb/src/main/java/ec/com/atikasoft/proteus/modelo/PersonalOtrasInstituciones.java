/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Leydis Garzón
 */
@Entity
@Table(name = "personal_otras_instituciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_VIGENTES,
            query = "SELECT p FROM PersonalOtrasInstituciones p WHERE p.vigente=true order by p.apellidosNombres"),
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_POR_ESTADO,
            query = "SELECT p FROM PersonalOtrasInstituciones p WHERE p.vigente=?1 order by p.apellidosNombres"),
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_POR_IDENTIFICACION,
            query = "SELECT p FROM PersonalOtrasInstituciones p WHERE p.tipoIdentificacion=?1 "
                    + "AND p.numeroIdentificacion like ?2"),
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_VIGENTES_POR_IDENTIFICACION,
            query = "SELECT p FROM PersonalOtrasInstituciones p WHERE p.tipoIdentificacion=?1 "
                    + "AND p.numeroIdentificacion like ?2"),
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_VIGENTES_POR_APELLIDOS_NOMBRES,
            query = "SELECT p FROM PersonalOtrasInstituciones p where p.apellidosNombres like ?1 AND p.vigente=true"),
    @NamedQuery(name = PersonalOtrasInstituciones.BUSCAR_POR_APELLIDOS_NOMBRES,
            query = "SELECT p FROM PersonalOtrasInstituciones p where p.apellidosNombres like ?1")
})
@Getter
@Setter
public class PersonalOtrasInstituciones extends EntidadBasica {

    /**
     * Nombre de la consulta para buscar todas las PersonalOtrasInstituciones vigentes
     */
    public static final String BUSCAR_VIGENTES = "PersonalOtrasInstituciones.buscarVigentes";

    /**
     * Nombre de la consulta para buscar todas las PersonalOtrasInstituciones segun estado (activo/inactivos)
     */
    public static final String BUSCAR_POR_ESTADO = "PersonalOtrasInstituciones.buscarPorEstado";
    
    /**
     * Nombre de la consulta para buscar PersonalOtrasInstituciones por identificacion
     */
    public static final String BUSCAR_POR_IDENTIFICACION = "PersonalOtrasInstituciones.buscarPorIdentificacion";
    /**
     * Nombre de la consulta para buscar PersonalOtrasInstituciones vigentes por identificacion
     */
    public static final String BUSCAR_VIGENTES_POR_IDENTIFICACION = "PersonalOtrasInstituciones.buscarVigentesPorIdentificacion";

    /**
     * Nombre de la consulta para buscar PersonalOtrasInstituciones por nombre
     */
    public static final String BUSCAR_POR_APELLIDOS_NOMBRES = "PersonalOtrasInstituciones.buscarPorApellidosNombres";
    
    /**
     * Nombre de la consulta para buscar PersonalOtrasInstituciones vigentes por nombre
     */
    public static final String BUSCAR_VIGENTES_POR_APELLIDOS_NOMBRES = "PersonalOtrasInstituciones.buscarVigentesPorApellidosNombres";
    
    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Apellidos y nombres
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion")
    private String numeroIdentificacion;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "fecha_salida_institucion_origen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSalidaInstitucionOrigen;

    @Column(name = "justificacion_ingreso")
    private String justificacionIngreso;

    @Column(name = "justificacion_salida")
    private String justificacionSalida;

    @Column(name = "institucion_origen")
    private String institucionOrigen;

    @Column(name = "puesto_institucion_origen")
    private String puestoInstitucionOrigen;

    @Column(name = "rmu_institucion_origen")
    private BigDecimal rmuInstitucionOrigen;

    @JoinColumn(name = "unidad_organizacional_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;

    @JoinColumn(name = "archivo_accion_ingreso_personal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivoAccionIngreso;

    @JoinColumn(name = "archivo_accion_salida_personal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Archivo archivoAccionSalida;
    
    @Column(name="numero_archivo_accion_ingreso_personal")
    private String numeroArchivoAccionIngreso;

    @Column(name="numero_archivo_accion_salida_personal")
    private String numeroArchivoAccionSalida;

}
