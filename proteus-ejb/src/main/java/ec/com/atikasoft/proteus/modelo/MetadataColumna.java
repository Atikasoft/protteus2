/*
 *  MetadataColumna.java
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
 *  07/10/2013
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

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "metadata_columnas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = MetadataColumna.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM MetadataColumna a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = MetadataColumna.BUSCAR_VIGENTES,
            query = "SELECT a FROM MetadataColumna a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = MetadataColumna.BUSCAR_POR_METADATA_TABLA,
            query = "SELECT a FROM MetadataColumna a where a.metadataTabla.id=?1 and a.vigente=true order by a.nombre")
})
public class MetadataColumna extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "MetadataColumna.buscarporNombre ";
    /**
     * Variable para busqueda de registros vigentes por id de Metadata de la tabla.
     */
    public static final String BUSCAR_POR_METADATA_TABLA = "MetadataColumna.buscarporMetadataTabla ";
    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "MetadataColumna.buscarVigente";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Campo columna.
     */
    @Column(name = "columna")
    private String columna;
    /**
     * Campo nombre.
     */
    @Column(name = "nombre")
    private String nombre;
    /**
     * Campo descripción.
     */
    @Column(name = "descripcion")
    private String descripcion;
    
    /**
     * Campo descripción.
     */
    @Column(name = "tipo")
    private String tipo;
    
    
     /**
     * Referencia a regimenLaboral.
     */
    @JoinColumn(name = "metadata_tablas_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MetadataTabla metadataTabla;
    
     /**
     * regimenes laborales id.
     */
    @Column(name = "metadata_tablas_id")
    private Long idMetadataTabla;

    

    /**
     * Constructor.
     */
    public MetadataColumna() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MetadataColumna(Long id) {
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
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the columna
     */
    public String getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(String columna) {
        this.columna = columna;
    }

    /**
     * @return the metadataTabla
     */
    public MetadataTabla getMetadataTabla() {
        return metadataTabla;
    }

    /**
     * @param metadataTabla the metadataTabla to set
     */
    public void setMetadataTabla(MetadataTabla metadataTabla) {
        this.metadataTabla = metadataTabla;
    }

    /**
     * @return the idMetadataTabla
     */
    public Long getIdMetadataTabla() {
        return idMetadataTabla;
    }

    /**
     * @param idMetadataTabla the idMetadataTabla to set
     */
    public void setIdMetadataTabla(Long idMetadataTabla) {
        this.idMetadataTabla = idMetadataTabla;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
