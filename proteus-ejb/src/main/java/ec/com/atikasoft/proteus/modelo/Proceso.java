/*
 *  Proceso.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  Oct 20, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Conjunto de actividades mutuamente relacionadas o que interactuan, las cuales transforman elementos de entrada en
 * resultados.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.procesos")
@NamedQueries({@NamedQuery(name = Proceso.BUSCAR_POR_CODIGO, query =
    "SELECT o FROM Proceso o WHERE o.vigente=true AND o.codigo=?1")})
public class Proceso extends EntidadBasica {

    /**
     * Nombre de consulta que permite buscar un proceso por su codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "Proceso.buscarPorCodigo";

    /**
     * Identificador único de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador único de negocio.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;

    /**
     * Denominación del proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Explicación detallada del proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Imagen que describe el proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "imagen")
    private String imagen;

    /**
     * Contador de tareas.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "contador_tareas")
    private Long contadorTarea;

    /**
     * Referencia del formulario.
     */
    @JoinColumn(name = "formularios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Formulario formulario;

    /**
     * Lista de transiciones.
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "proceso")
    private List<Transicion> listaTransiciones;

    /**
     * Lista de instancias.
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "proceso")
    private List<Instancia> listaIntancias;

    /**
     * Constructor.
     */
    public Proceso() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the listaTransiciones
     */
    public List<Transicion> getListaTransiciones() {
        return listaTransiciones;
    }

    /**
     * @return the listaIntancias
     */
    public List<Instancia> getListaIntancias() {
        return listaIntancias;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(final String imagen) {
        this.imagen = imagen;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(final Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param listaTransiciones the listaTransiciones to set
     */
    public void setListaTransiciones(final List<Transicion> listaTransiciones) {
        this.listaTransiciones = listaTransiciones;
    }

    /**
     * @param listaIntancias the listaIntancias to set
     */
    public void setListaIntancias(final List<Instancia> listaIntancias) {
        this.listaIntancias = listaIntancias;
    }

    /**
     * @return the contadorTarea
     */
    public Long getContadorTarea() {
        return contadorTarea;
    }

    /**
     * @param contadorTarea the contadorTarea to set
     */
    public void setContadorTarea(final Long contadorTarea) {
        this.contadorTarea = contadorTarea;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "listaIntancias", "listaTransiciones", "listaVariables");
    }
}
