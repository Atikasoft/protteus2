/*
 *  Transicion.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information MRL
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with MRL.
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

/**
 * Es la acción y efecto de pasar de un estado a otro distinto, el concepto
 * implica un cambio en un modo de ser o estar, por lo general se entiende como
 * un proceso con una cierta extensión en el tiempo
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.transiciones")
@NamedQueries({
    @NamedQuery(name = Transicion.BUSCAR_TRANSICION_INICIAL_POR_PROCESO,
            query
            = "SELECT o FROM Transicion o WHERE o.vigente = true AND o.faseInicial IS NULL "
            + "AND o.proceso.codigo=?1"),
    @NamedQuery(name = Transicion.BUSCAR_TRANSICION_SIGUIENTE_POR_PROCESO,
            query
            = "SELECT o FROM Transicion o WHERE o.vigente = true AND o.faseInicial.codigo= ?1 "
            + " AND o.proceso.codigo= ?2 ORDER BY o.ordinal"),
    @NamedQuery(name = Transicion.BUSCAR_TRANSICION_POR_FASE_INICIAL_FINAL,
            query
            = "SELECT o FROM Transicion o WHERE o.vigente = true AND o.faseInicial.codigo= ?1 "
            + " AND o.faseFinal.codigo=?2  AND o.proceso.codigo= ?3 ")
})
public class Transicion extends EntidadBasica {

    /**
     * Permite recuperar la transicion inicial de un proceso especifico.
     */
    public static final String BUSCAR_TRANSICION_INICIAL_POR_PROCESO = "Transicion.buscarFaseInicialPorProceso";

    /**
     * Permite buscar la transicion siguientes dado un estado actual para un
     * proceso.
     */
    public static final String BUSCAR_TRANSICION_SIGUIENTE_POR_PROCESO
            = "Transicion.buscarFasesSiguientesPorProceso";

    /**
     * Nombre de la consulta que recupera una transicion a partir de la fase
     * inicial y final.
     */
    public static final String BUSCAR_TRANSICION_POR_FASE_INICIAL_FINAL = "Transicion.buscarPorFaseInicialFinal";

    /**
     * Identificador único de sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de la acción que se presentara en el formulario para la
     * transicion, en el caso que el tipo de atención sea manual.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "accion")
    private String accion;

    /**
     * Plantilla del asunto que se mostrara en las tareas generadas.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "asunto")
    private String asunto;

    /**
     * Título del formulario asignado a la transicion, si existe este,
     * sobreescribira el titulo propio del formulario.
     */
    @Size(max = 100)
    @Column(name = "titulo_formulario")
    private String tituloFormulario;

    /**
     * Identifica el orden de la transaccion para un proceso.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ordinal")
    private Integer ordinal;

    /**
     * Indica si la presente transicion procede a crear o no tarea.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "crear_tarea")
    private Boolean crearTarea;

    /**
     * Indica si la presente transicion es de devolucion (devolver), sirve para
     * asignarle al mismo usuario.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "devolucion")
    private Boolean devolucion;

    /**
     * Indica si el la asignacion es directo al usuario.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "directo")
    private Boolean directo;

    /**
     * script que sera evaluado si el estado tiene tipo de asignación
     * automática.
     */
    @Size(max = 400)
    @Column(name = "evaluador")
    private String evaluador;

    /**
     * Indica como se asignara los usuario en la transicion: A: Automativo, se
     * asignara los usuario mediante balanceo M : Manual, se de proveer el
     * usuario a asignar la tarea.
     */
    @Column(name = "tipo_atencion")
    private String tipoAtencion;

    /**
     * Referencia del proceso.
     */
    @JoinColumn(name = "procesos_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Proceso proceso;

    /**
     * Referencia del formulario.
     */
    @JoinColumn(name = "formularios_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Formulario formulario;

    /**
     * Referencia del estado inicial.
     */
    @JoinColumn(name = "estados_inicial_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fase faseInicial;

    /**
     * Referencia del estado final.
     */
    @JoinColumn(name = "estados_final_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Fase faseFinal;

    /**
     * Lista de asignaciones.
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "transicion")
    private List<Asignacion> listaAsignaciones;

    /**
     * Lista de eventos.
     */
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "transicion")
    private List<Evento> listaEventos;

    /**
     * Cnstructor.
     */
    public Transicion() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @return the tituloFormulario
     */
    public String getTituloFormulario() {
        return tituloFormulario;
    }

    /**
     * @return the ordinal
     */
    public Integer getOrdinal() {
        return ordinal;
    }

    /**
     * @return the crearTarea
     */
    public Boolean isCrearTarea() {
        return crearTarea;
    }

    /**
     * @return the evaluador
     */
    public String getEvaluador() {
        return evaluador;
    }

    /**
     * @return the proceso
     */
    public Proceso getProceso() {
        return proceso;
    }

    /**
     * @return the formulario
     */
    public Formulario getFormulario() {
        return formulario;
    }

    /**
     * @return the estadoInicial
     */
    public Fase getFaseInicial() {
        return faseInicial;
    }

    /**
     * @return the estadoFinal
     */
    public Fase getFaseFinal() {
        return faseFinal;
    }

    /**
     * @return the listaAsignaciones
     */
    public List<Asignacion> getListaAsignaciones() {
        return listaAsignaciones;
    }

    /**
     * @return the listaEventos
     */
    public List<Evento> getListaEventos() {
        return listaEventos;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(final String accion) {
        this.accion = accion;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(final String asunto) {
        this.asunto = asunto;
    }

    /**
     * @param tituloFormulario the tituloFormulario to set
     */
    public void setTituloFormulario(final String tituloFormulario) {
        this.tituloFormulario = tituloFormulario;
    }

    /**
     * @param ordinal the ordinal to set
     */
    public void setOrdinal(final Integer ordinal) {
        this.ordinal = ordinal;
    }

    /**
     * @param crearTarea the crearTarea to set
     */
    public void setCrearTarea(final Boolean crearTarea) {
        this.crearTarea = crearTarea;
    }

    /**
     * @param evaluador the evaluador to set
     */
    public void setEvaluador(final String evaluador) {
        this.evaluador = evaluador;
    }

    /**
     * @param proceso the proceso to set
     */
    public void setProceso(final Proceso proceso) {
        this.proceso = proceso;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(final Formulario formulario) {
        this.formulario = formulario;
    }

    /**
     * @param estadoInicial the estadoInicial to set
     */
    public void setFaseInicial(final Fase faseInicial) {
        this.faseInicial = faseInicial;
    }

    /**
     * @param estadoFinal the estadoFinal to set
     */
    public void setFaseFinal(final Fase faseFinal) {
        this.faseFinal = faseFinal;
    }

    /**
     * @param listaAsignaciones the listaAsignaciones to set
     */
    public void setListaAsignaciones(final List<Asignacion> listaAsignaciones) {
        this.listaAsignaciones = listaAsignaciones;
    }

    /**
     * @param listaEventos the listaEventos to set
     */
    public void setListaEventos(final List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }

    /**
     * @return the devolucion
     */
    public Boolean getDevolucion() {
        return devolucion;
    }

    /**
     * @param devolucion the devolucion to set
     */
    public void setDevolucion(final Boolean devolucion) {
        this.devolucion = devolucion;
    }

    /**
     * @return the tipoAtencion
     */
    public String getTipoAtencion() {
        return tipoAtencion;
    }

    /**
     * @param tipoAtencion the tipoAtencion to set
     */
    public void setTipoAtencion(final String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    /**
     * @return the directo
     */
    public Boolean getDirecto() {
        return directo;
    }

    /**
     * @param directo the directo to set
     */
    public void setDirecto(Boolean directo) {
        this.directo = directo;
    }
}
