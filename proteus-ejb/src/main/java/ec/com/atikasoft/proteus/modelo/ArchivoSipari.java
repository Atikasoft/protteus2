/*
 *  ArchivoSipari.java
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
 *  28/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.ArchivoSipariEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 * http://wiki.eclipse.org/EclipseLink/Examples/JPA/MappingSelectionCriteria
 */
@Entity
@Table(name = "archivos_sipari", catalog = "sch_proteus")

@NamedQueries({
    @NamedQuery(name = ArchivoSipari.BUSCAR_POR_NOMINA_Y_TIPO,
            query = "SELECT a FROM ArchivoSipari a where a.nomina.id = ?1 and a.vigente=true  and a.tipo=?2")
})
public class ArchivoSipari extends EntidadBasica {

    /**
     * Variable parabusqeda por NOmina y tipo.
     */
    public static final String BUSCAR_POR_NOMINA_Y_TIPO = "ArchivoSipari.buscarporNominaYTipo";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * Campo nombre.
     */
    @NotNull
    @Column(name = "nombre")
    private String nombre;

   
    /**
     * Campo tipo: N-Nomina, E-Empleados, R-Retenciones Judiciales.
     */
    @NotNull
    @Column(name = "tipo")
    private String tipo;
    
        /**
     * Campo tipo: N-Nomina, E-Empleados, R-Retenciones Judiciales.
     */
    @Transient
    private String tipoNombre;

    /**
     * Referencia a nomina.
     */
    @JoinColumn(name = "nomina_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Nomina nomina;

    @OneToMany(mappedBy = "archivoSipari")
    private List<ArchivoSipariNomina> listaArchivosNominas;

    @OneToMany(mappedBy = "archivoSipari")
    private List<ArchivoSipariEmpleado> listaArchivosEmpleados;

    @Transient
    private Boolean seleccionado;
    /**
     * Constructor.
     */
    public ArchivoSipari() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ArchivoSipari(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
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
    /**
     * @return the listaArchivosNominas
     */
    public List<ArchivoSipariNomina> getListaArchivosNominas() {
        return listaArchivosNominas;
    }

    /**
     * @param listaArchivosNominas the listaArchivosNominas to set
     */
    public void setListaArchivosNominas(List<ArchivoSipariNomina> listaArchivosNominas) {
        this.listaArchivosNominas = listaArchivosNominas;
    }

    /**
     * @return the listaArchivosEmpleados
     */
    public List<ArchivoSipariEmpleado> getListaArchivosEmpleados() {
        return listaArchivosEmpleados;
    }

    /**
     * @param listaArchivosEmpleados the listaArchivosEmpleados to set
     */
    public void setListaArchivosEmpleados(List<ArchivoSipariEmpleado> listaArchivosEmpleados) {
        this.listaArchivosEmpleados = listaArchivosEmpleados;
    }

    /**
     * @return the tipoNombre
     */
    public String getTipoNombre() {
         tipoNombre = ArchivoSipariEnum.obtenerDescripcion(this.tipo);
        return tipoNombre;
    }

    /**
     * @param tipoNombre the tipoNombre to set
     */
    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }

    /**
     * @return the seleccionado
     */
    public Boolean getSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

}
