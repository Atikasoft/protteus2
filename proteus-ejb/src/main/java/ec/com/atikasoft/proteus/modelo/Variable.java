/*
 *  Variable.java
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
 *  Nov 28, 2011
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.enums.OperacionEnum;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "variables", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Variable.BUSCAR_POR_NOMBRE, query = "SELECT a FROM Variable a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = Variable.BUSCAR_VIGENTES, query = "SELECT a FROM Variable a where a.vigente=true order by a.nombre "),
    @NamedQuery(name = Variable.BUSCAR_POR_CODIGO, query = "SELECT a FROM Variable a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class Variable extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "Variable.buscarporNombre ";

    /**
     * Variable parabusqeda por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "Variable.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Variable.buscarVigente";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo código. Identificador funcional de variable.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Campo nombre. Es la designación o denominación verbal que se le da a una
     * variable para distinguirlo de otros.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Campo descripción. Es la explicación, de forma detallada y ordenada, de
     * cómo es la de variable.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Campo mensajeValidacion. Mensaje de validacion para las condiciones.
     */
    @Column(name = "mensaje_validacion")
    private String mensajeValidacion;

    /**
     * Campo Operación. Indica la operacion , los cuales son. - Sumatoria (S) -
     * Contar (C) - Promedio (P) - Valor (V) - MAXIMO (M) - MINIMO (I)
     */
    @Column(name = "operacion")
    private String operacion;

    /**
     * Campo Origen. Indica el origen de la variables, pueder ser. - Dato
     * adicional (DA) - Campos de Acceso (CA) - Preconstruidos (PC)
     */
    @Column(name = "origen")
    private String origen;

    /**
     * Campo Validado. Indica si las condiciones son válidas, ya que pueden
     * tener error de sintaxis.
     */
    @Column(name = "validado")
    private Boolean validado;

    /**
     *
     * Codigo de la varible preconstruido.
     */
    @Column(name = "codigo_preconstruido")
    private String codigoPreconstruido;

    /**
     * Campo Acceso
     */
    @ManyToOne
    @JoinColumn(name = "campos_accesos_id", insertable = false, updatable = false)
    private CampoAcceso campoAcceso;

    /**
     * Columna Campo Acceso Id
     */
    @Column(name = "campos_accesos_id")
    private Long campoAccesoId;

    /**
     * Dato Adicional
     */
    @ManyToOne
    @JoinColumn(name = "datos_adicionales_id", insertable = false, updatable = false)
    private DatoAdicional datoAdicional;

    /**
     * Columna Campo Acceso Id
     */
    @Column(name = "datos_adicionales_id")
    private Long datoAdicionalId;

    /**
     * Lista de VariableCondicion
     */
    @OneToMany(mappedBy = "variableP")
    private List<VariableCondicion> variablesCondiciones;

    /**
     * Constructor.
     */
    public Variable() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Variable(final Long id) {
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final String codigo) {
        this.codigo = codigo;
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
    public void setNombre(final String nombre) {
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
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the mensajeValidacion
     */
    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    /**
     * @param mensajeValidacion the mensajeValidacion to set
     */
    public void setMensajeValidacion(final String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    /**
     * @return the operacion
     */
    public String getOperacion() {
        return operacion;
    }

    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(final String operacion) {
        this.operacion = operacion;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(final String origen) {
        this.origen = origen;
    }

    /**
     * @return the validado
     */
    public Boolean getValidado() {
        return validado;
    }

    /**
     * @param validado the validado to set
     */
    public void setValidado(final Boolean validado) {
        this.validado = validado;
    }

    /**
     * @return the codigoPreconstruido
     */
    public String getCodigoPreconstruido() {
        return codigoPreconstruido;
    }

    /**
     * @param codigoPreconstruido the codigoPreconstruido to set
     */
    public void setCodigoPreconstruido(final String codigoPreconstruido) {
        this.codigoPreconstruido = codigoPreconstruido;
    }

    /**
     * @return the campoAcceso
     */
    public CampoAcceso getCampoAcceso() {
        return campoAcceso;
    }

    /**
     * @param campoAcceso the campoAcceso to set
     */
    public void setCampoAcceso(final CampoAcceso campoAcceso) {
        this.campoAcceso = campoAcceso;
    }

    /**
     * @return the campoAccesoId
     */
    public Long getCampoAccesoId() {
        return campoAccesoId;
    }

    /**
     * @param campoAccesoId the campoAccesoId to set
     */
    public void setCampoAccesoId(final Long campoAccesoId) {
        this.campoAccesoId = campoAccesoId;
    }

    /**
     * @return the datoAdicional
     */
    public DatoAdicional getDatoAdicional() {
        return datoAdicional;
    }

    /**
     * @param datoAdicional the datoAdicional to set
     */
    public void setDatoAdicional(final DatoAdicional datoAdicional) {
        this.datoAdicional = datoAdicional;
    }

    /**
     * @return the datoAdicionalId
     */
    public Long getDatoAdicionalId() {
        return datoAdicionalId;
    }

    /**
     * @param datoAdicionalId the datoAdicionalId to set
     */
    public void setDatoAdicionalId(final Long datoAdicionalId) {
        this.datoAdicionalId = datoAdicionalId;
    }

    /**
     * @return the variablesCondiciones
     */
    public List<VariableCondicion> getVariablesCondiciones() {
        return variablesCondiciones;
    }

    /**
     * @param variablesCondiciones the variablesCondiciones to set
     */
    public void setVariablesCondiciones(final List<VariableCondicion> variablesCondiciones) {
        this.variablesCondiciones = variablesCondiciones;
    }

    /**
     * Recuperar el select de la variable.
     *
     * @return Select.
     */
    public String getSelect() {
        StringBuilder select = new StringBuilder("SELECT ");
        if (OperacionEnum.VALOR.getCodigo().equals(operacion)) {
            select.append(getCampoAcceso().getMetadataColumna().getColumna());
        } else {
            determinaFuncion(select);
            select.append("(").append(getCampoAcceso().getMetadataColumna().getColumna()).append(")");
        }
        select.append(" FROM ");
        select.append(getCampoAcceso().getMetadataColumna().getMetadataTabla().getTabla());
        return select.toString();
    }

    /**
     * Determina la funcion a usar.
     *
     * @param select Select.
     */
    private void determinaFuncion(final StringBuilder select) {
        if (OperacionEnum.CONTAR.getCodigo().equals(operacion)) {
            select.append("COUNT");
        } else if (OperacionEnum.MAXIMO.getCodigo().equals(operacion)) {
            select.append("MAX");
        } else if (OperacionEnum.MINIMO.getCodigo().equals(operacion)) {
            select.append("MIN");
        } else if (OperacionEnum.PROMEDIO.getCodigo().equals(operacion)) {
            select.append("MAX");
        } else if (OperacionEnum.SUMATORIA.getCodigo().equals(operacion)) {
            select.append("SUM");
        }
    }
}
