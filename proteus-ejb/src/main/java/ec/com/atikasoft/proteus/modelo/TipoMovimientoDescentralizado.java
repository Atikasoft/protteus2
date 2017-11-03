/*
 *  TipoMovimientoDescentralizado.java
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

/**
 * Tipo Movimiento Descentralizado.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "tipos_movimientos_descentralizacion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = TipoMovimientoDescentralizado.BUSCAR_TODOS_ACTIVOS_TIPO_MOV,
    query =
    "select o from TipoMovimientoDescentralizado o where o.vigente=true and o.tipoMovimiento.id=?1 and o.institucion.id=?2"),
    @NamedQuery(name = TipoMovimientoDescentralizado.BUSCAR_POR_TIPO_MOVIMIENTO_Y_UNIDAD_ORGANIZACIONAL,
    query = "select o from TipoMovimientoDescentralizado o where o.vigente=true and o.tipoMovimiento.id=?1 and "
    + " o.idUnidadOrganizacional=?2"),
    @NamedQuery(name = TipoMovimientoDescentralizado.BUSCAR_POR_UNIDAD_ORGANIZACIONAL,
    query = "select o from TipoMovimientoDescentralizado o where o.vigente=true and o.idUnidadOrganizacional=?1")
})
public class TipoMovimientoDescentralizado extends EntidadBasica {

    /**
     * Nombre de variable.
     */
    public static final String BUSCAR_TODOS_ACTIVOS_TIPO_MOV = "TipoMovimientoDescentralizado.buscarTodosActivos";

    /**
     * Nombre de consulta que recupera los tipos de movimientos descentralizados.
     */
    public static final String BUSCAR_POR_TIPO_MOVIMIENTO_Y_UNIDAD_ORGANIZACIONAL =
            "TipoMovimientoDescentralizado.buscarPorTipoMovimientoEInstitucion";

     /**
     * Nombre de consulta que recupera los tipos de movimientos descentralizados por Unidad Organizacional.
     */
    public static final String BUSCAR_POR_UNIDAD_ORGANIZACIONAL =
            "TipoMovimientoDescentralizado.buscarPorInstitucion";
    /**
     * Id de entidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Tipo de movimiento.
     */
    @JoinColumn(name = "tipos_movimientos_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TipoMovimiento tipoMovimiento;
    /**
     * Id de Tipo de movimiento.
     */
    @NotNull
    @Column(name = "tipos_movimientos_id")
    private Long idTipoMovimiento;
    
     /**
     * Unidad Organizacional.
     */
    @JoinColumn(name = "unidad_organizacional_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnidadOrganizacional unidadOrganizacional;
    
    /**
     * Id de la Unidad Organizacional.
     */
    @NotNull
    @Column(name = "unidad_organizacional_id")
    private Long idUnidadOrganizacional;

    /**
     * Id de la institucion que se loguea.
     */
    @JoinColumn(name = "institucion_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InstitucionEjercicioFiscal institucion;
    
        /**
     * Id de la institucion que se loguea.
     */
    @NotNull
    @Column(name = "institucion_id")
    private Long idInstitucion;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoDescentralizado() {
        super();
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
     * @return the institucion
     */
    public InstitucionEjercicioFiscal getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final InstitucionEjercicioFiscal institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the idTipoMovimiento
     */
    public Long getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    /**
     * @param idTipoMovimiento the idTipoMovimiento to set
     */
    public void setIdTipoMovimiento(Long idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    /**
     * @return the unidadOrganizacional
     */
    public UnidadOrganizacional getUnidadOrganizacional() {
        return unidadOrganizacional;
    }

    /**
     * @param unidadOrganizacional the unidadOrganizacional to set
     */
    public void setUnidadOrganizacional(UnidadOrganizacional unidadOrganizacional) {
        this.unidadOrganizacional = unidadOrganizacional;
    }

    /**
     * @return the idUnidadOrganizacional
     */
    public Long getIdUnidadOrganizacional() {
        return idUnidadOrganizacional;
    }

    /**
     * @param idUnidadOrganizacional the idUnidadOrganizacional to set
     */
    public void setIdUnidadOrganizacional(Long idUnidadOrganizacional) {
        this.idUnidadOrganizacional = idUnidadOrganizacional;
    }

    /**
     * @return the idInstitucion
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }
   
}
