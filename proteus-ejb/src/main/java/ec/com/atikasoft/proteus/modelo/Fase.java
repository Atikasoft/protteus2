/*
 *  Estado.java
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
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Denominación de los diferentes estados que soporta el sistema
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
@Entity
@Table(name = "sch_proteus.estados")
@NamedQueries({@NamedQuery(name = Fase.BUSCAR_ESTADO_INICIAL_POR_PROCESO,
    query =
    "SELECT o.faseFinal FROM Transicion o WHERE o.vigente = true AND o.faseInicial IS NULL "
    + "AND o.proceso.codigo=?1"),
    @NamedQuery(name = Fase.BUSCAR_ESTADOS_SIGUIENTES_POR_PROCESO,
    query =
    "SELECT o.faseFinal FROM Transicion o WHERE o.vigente = true AND o.faseInicial.codigo= ?1 "
    + " AND o.proceso.codigo= ?2 ORDER BY o.ordinal"),
    @NamedQuery(name = Fase.BUSCAR_TODOS, query = "SELECT o FROM Fase o WHERE o.vigente=true ORDER BY o.nombre")
})
public class Fase extends EntidadBasica {

    /**
     * Permite recuperar el estado inicial de un proceso especifico.
     */
    public static final String BUSCAR_ESTADO_INICIAL_POR_PROCESO = "Fase.buscarFaseInicialPorProceso";

    /**
     * Permite buscar los estados siguientes dado un estado actual para un proceso.
     */
    public static final String BUSCAR_ESTADOS_SIGUIENTES_POR_PROCESO =
            "Fase.buscarFasesSiguientesPorProceso";

    /**
     * Nombre de la consulta que recupera todas las fases ordenanadas.
     */
    public static final String BUSCAR_TODOS = "Fase.buscarTodos";

    /**
     * Constructor.
     */
    public Fase() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Fase(final Long id) {
        super();
        this.id = id;
    }
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
    @Column(name = "codigo")
    private String codigo;

    /**
     * Denominación del estado.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Indica como se atendera este estado: A: Automativo, si existe una sola transicion, se ira por alli, si existe mas
     * de una transacción se procedera a evaluar el camino correspondiente M : Manual, indique se de debe presentar en
     * el formulario las diferente acción a realizar (transiciones).
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "tipo_atencion")
    private String tipoAtencion;

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
     * @return the tipoAtencion
     */
    public String getTipoAtencion() {
        return tipoAtencion;
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
     * @param tipoAtencion the tipoAtencion to set
     */
    public void setTipoAtencion(final String tipoAtencion) {
        this.tipoAtencion = tipoAtencion;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
