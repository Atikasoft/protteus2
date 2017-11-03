/*
 *  MetadataTabla.java
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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "metadata_tablas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = MetadataTabla.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM MetadataTabla a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = MetadataTabla.BUSCAR_VIGENTES,
            query = "SELECT a FROM MetadataTabla a where a.vigente=true order by a.nombre")
})
public class MetadataTabla extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "MetadataTabla.buscarporNombre ";
    
    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "MetadataTabla.buscarVigente";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Campo código.
     */
    @Column(name = "tabla")
    private String tabla;
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
     * Lista de metadata de columnas
     */
    @OneToMany(mappedBy = "metadataTabla")
    private List<MetadataColumna> listaMetadataColumna;
    
   

    /**
     * Constructor.
     */
    public MetadataTabla() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MetadataTabla(Long id) {
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
     * @return the tabla
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * @param tabla the tabla to set
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * @return the listaMetadataColumna
     */
    public List<MetadataColumna> getListaMetadataColumna() {
        return listaMetadataColumna;
    }

    /**
     * @param listaMetadataColumna the listaMetadataColumna to set
     */
    public void setListaMetadataColumna(List<MetadataColumna> listaMetadataColumna) {
        this.listaMetadataColumna = listaMetadataColumna;
    }


}
