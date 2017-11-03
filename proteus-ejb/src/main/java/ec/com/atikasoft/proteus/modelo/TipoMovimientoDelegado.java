/*
 *  TipoMovimientoDelegado.java
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
 *  21/02/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@Entity
@Table(name = "tipos_movimientos_delegados", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoDelegado.BUSCAR_VIGENTES,
    query = "SELECT a FROM TipoMovimientoDelegado a where a.vigente=true"),
    @NamedQuery(name = TipoMovimientoDelegado.BUSCAR_TIPO_MOVIMIENTO_DELEGADO_POR_ID,
    query = "SELECT a FROM TipoMovimientoDelegado a where a.id=?1"),
    @NamedQuery(name = TipoMovimientoDelegado.BUSCAR_POR_TIPO_MOVIMIENTO_DELEGADO,
    query =
    "SELECT a FROM TipoMovimientoDelegado a where a.vigente=true and a.tipoMovimiento=?1 and a.delegadoCedula=?2 "),
    @NamedQuery(name = TipoMovimientoDelegado.BUSCAR_POR_TIPO_MOVIMIENTO_E_INSTITUCION,
    query =
    "SELECT a FROM TipoMovimientoDelegado a where a.vigente=true and a.tipoMovimiento.id=?1 and a.institucionCoreId=?2 ")
})
public class TipoMovimientoDelegado extends EntidadBasica {

    /**
     * Variable para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "TipoMovimientoDelegado.buscarVigente";

    /**
     * Variable para busqeda por tipo de movimiento y delegado.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_DELEGADO =
            "TipoMovimientoDelegado.buscarPorTipoMovimientoDelegado";

    /**
     * Variable para busqeda de tipo de movimiento y delegado por id.
     */
    public static final String BUSCAR_TIPO_MOVIMIENTO_DELEGADO_POR_ID =
            "TipoMovimientoDelegado.buscarTipoMovimientoDelegadoPorId";

    /**
     * Nombre de consulta para recuperar los delegados de una institucion x tipo de movimiento.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_E_INSTITUCION =
            "TipoMovimientoDelegado.buscarPorTipoMovimientoEInstitucion";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * variable tipo de movimiento.
     */
    @JoinColumn(name = "tipos_movimientos_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;

    /**
     * cédula del delegado del movimiento.
     */
    @Column(name = "delegado_cedula")
    private String delegadoCedula;

    /**
     * nombre del delegado del movimiento.
     */
    @Column(name = "delegado_nombre")
    private String delegadoNombre;

    /**
     * nemónico del rol del delegado del movimiento.
     */
    @Column(name = "rol_codigo")
    private String rolCodigo;

    /**
     * nombre del rol del delegado del movimiento.
     */
    @Column(name = "rol_nombre")
    private String rolNombre;

    /**
     * Codigo correspondiente a la fase.
     */
    @Column(name = "codigo_fase")
    private String codigoFase;

    /**
     * Identificador unico de la institucion en adm-siith.
     */
    @Column(name = "institucion_core_id")
    private Long institucionCoreId;

    /**
     * Constructor.
     */
    public TipoMovimientoDelegado() {
        super();
    }

    /**
     * constructor con aprametro id.
     *
     * @param id Long
     */
    public TipoMovimientoDelegado(final Long id) {
        super();
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the delegadoCedula
     */
    public String getDelegadoCedula() {
        return delegadoCedula;
    }

    /**
     * @param delegadoCedula the delegadoCedula to set
     */
    public void setDelegadoCedula(final String delegadoCedula) {
        this.delegadoCedula = delegadoCedula;
    }

    /**
     * @return the delegadoNombre
     */
    public String getDelegadoNombre() {
        return delegadoNombre;
    }

    /**
     * @param delegadoNombre the delegadoNombre to set
     */
    public void setDelegadoNombre(final String delegadoNombre) {
        this.delegadoNombre = delegadoNombre;
    }

    /**
     * @return the rolCodigo
     */
    public String getRolCodigo() {
        return rolCodigo;
    }

    /**
     * @param rolCodigo the rolCodigo to set
     */
    public void setRolCodigo(final String rolCodigo) {
        this.rolCodigo = rolCodigo;
    }

    /**
     * @return the rolNombre
     */
    public String getRolNombre() {
        return rolNombre;
    }

    /**
     * @param rolNombre the rolNombre to set
     */
    public void setRolNombre(final String rolNombre) {
        this.rolNombre = rolNombre;
    }

    /**
     * @return the codigoFase
     */
    public String getCodigoFase() {
        return codigoFase;
    }

    /**
     * @param codigoFase the codigoFase to set
     */
    public void setCodigoFase(final String codigoFase) {
        this.codigoFase = codigoFase;
    }

    /**
     * @return the institucionCoreId
     */
    public Long getInstitucionCoreId() {
        return institucionCoreId;
    }

    /**
     * @param institucionCoreId the institucionCoreId to set
     */
    public void setInstitucionCoreId(final Long institucionCoreId) {
        this.institucionCoreId = institucionCoreId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
