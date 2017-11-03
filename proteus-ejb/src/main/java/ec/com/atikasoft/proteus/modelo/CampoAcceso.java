/*
 *  CampoAcceso.java
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
 *  03/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
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
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Consiste en selecciona tablas del sistema que puedan ser accedidas desde parametrización de tipo de movimientos y
 * nómina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "campos_acceso", catalog = "sch_proteus")
@NamedQueries({@NamedQuery(name = CampoAcceso.BUSCAR_POR_TABLA,
    query =
    "select o from CampoAcceso o where o.metadataColumna.metadataTabla.id=?1 and o.vigente=TRUE ORDER BY o.nombre"),
    @NamedQuery(name = CampoAcceso.BUSCAR_POR_COLUMNA,
    query = "select o from CampoAcceso o where o.vigente=TRUE and o.metadataColumnaId=?1 ORDER BY o.nombre"),
    @NamedQuery(name = CampoAcceso.BUSCAR_POR_NOMBRE,
    query = "select o from CampoAcceso o where o.vigente=TRUE and o.nombre like ?1 ORDER BY o.nombre"),
    @NamedQuery(name = CampoAcceso.BUSCAR_VIGENTES,
    query = "select o from CampoAcceso o where o.vigente=TRUE ORDER BY o.nombre"),
    @NamedQuery(name = CampoAcceso.BUSCAR_POR_ID,
    query = "select o from CampoAcceso o where o.vigente=TRUE and o.id=?1 ORDER BY o.nombre"),
    @NamedQuery(name = CampoAcceso.BUSCAR_POR_TIPO,
    query = "select o from CampoAcceso o where o.tipo=?1 and o.vigente=true ORDER BY o.nombre")
})
public class CampoAcceso extends EntidadBasica {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_TABLA = "CampoAcceso.buscarPorTabla";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_COLUMNA = "CampoAcceso.buscarPorColumna";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_NOMBRE = "CampoAcceso.buscarPorNombre";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_TIPO = "CampoAcceso.buscarPorTipo";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_VIGENTES = "CampoAcceso.buscarVigentes";

    /**
     * Nombre de consulta.
     */
    public static final String BUSCAR_POR_ID = "CampoAcceso.buscarPorId";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * Es la designación o denominación verbal que se le da a un campo de acceso para distinguirlo de otros.
     */
    @Column(name = "NOMBRE")
    private String nombre;

    /**
     * Es descripcion.
     */
    @Column(name = "DESCRIPCION")
    private String descripcion;

    /**
     * Indica el tipo del campo de acceso , los cuales son: - Tipo de Movimiento (T). - Nomina (N)
     */
    @Column(name = "TIPO")
    private String tipo;

    /**
     * Columna - tabla.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metadata_columnas_id", insertable = false, updatable = false)
    private MetadataColumna metadataColumna;

    /**
     * Columna - tabla.
     */
    @Column(name = "metadata_columnas_id")
    private Long metadataColumnaId;

    /**
     * Constructor sin argumentos.
     */
    public CampoAcceso() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the metadataColumna
     */
    public MetadataColumna getMetadataColumna() {
        return metadataColumna;
    }

    /**
     * @return the metadataColumnaId
     */
    public Long getMetadataColumnaId() {
        return metadataColumnaId;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param metadataColumna the metadataColumna to set
     */
    public void setMetadataColumna(final MetadataColumna metadataColumna) {
        this.metadataColumna = metadataColumna;
    }

    /**
     * @param metadataColumnaId the metadataColumnaId to set
     */
    public void setMetadataColumnaId(final Long metadataColumnaId) {
        this.metadataColumnaId = metadataColumnaId;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
}