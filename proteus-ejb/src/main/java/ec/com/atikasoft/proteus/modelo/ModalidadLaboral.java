/*
 *  Persistencia de la tabla movimientos_laborales
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@marcasoft.ec>
 */
@Entity
@Table(name = "modalidades_laborales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ModalidadLaboral.BUSCAR_POR_NOMBRE,
    query = "SELECT a FROM ModalidadLaboral a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = ModalidadLaboral.BUSCAR_MODALIDADES_SIN_CERTIFICACION_PRESUPUESTARIA_POR_UNIDAD_PRESUPUESTARIA, 
            query = "SELECT a FROM ModalidadLaboral a where a.vigente=true and a.id NOT IN (SELECT c.modalidadLaboral.id FROM CertificacionPresupuestaria c WHERE c.unidadPresupuestaria.id=?1) order by a.nombre"),
    @NamedQuery(name = ModalidadLaboral.BUSCAR_VIGENTES,
    query = "SELECT a FROM ModalidadLaboral a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = ModalidadLaboral.BUSCAR_POR_CODIGO,
    query = "SELECT a FROM ModalidadLaboral a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
public class ModalidadLaboral extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "ModalidadLaboral.buscarporNombre ";

    /**
     * Variable parabusqeda por código.
     */
    public static final String BUSCAR_POR_CODIGO = "ModalidadLaboral.buscarporCodigo ";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "ModalidadLaboral.buscarVigente";
    
    /**
     * Nombre para busqueda de modalidades sin certificacion presupuestaria en la unidad presupuestaria especificada.
     */
    public static final String BUSCAR_MODALIDADES_SIN_CERTIFICACION_PRESUPUESTARIA_POR_UNIDAD_PRESUPUESTARIA = 
            "ModalidadLaboral.buscarModalidadSinCertificacionPresupuestariaPorUnidadPresupuestaria";

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
    @Column(name = "codigo")
    private String codigo;

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
     * Campo item_presupuestario
     */
    @Column(name = "item_presupuestario")
    private String itemPresupuestario;

    /**
     * Campo modalidad
     */
    @Column(name = "modalidad")
    private String modalidad;

    /**
     * Campo estabilidad_laboral
     */
    @Column(name = "estabilidad_laboral")
    private boolean estabilidadLaboral;

    /**
     * Campo aplica_aporte_patronal
     */
    @Column(name = "aplica_aporte_patronal")
    private boolean aplicaAportePatronal;

    /**
     * Campo aplica_aporte_individual
     */
    @Column(name = "aplica_aporte_individual")
    private boolean aplicaAporteIndividual;

    /**
     * Lista de modalidadLaboral_NivelOcupacional
     */
    @OneToMany(mappedBy = "modalidadLaboral")
    private List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional;

    /**
     * Constructor.
     */
    public ModalidadLaboral() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ModalidadLaboral(final Long id) {
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
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
     * @return the itemPresupuestario
     */
    public String getItemPresupuestario() {
        return itemPresupuestario;
    }

    /**
     * @param itemPresupuestario the itemPresupuestario to set
     */
    public void setItemPresupuestario(String itemPresupuestario) {
        this.itemPresupuestario = itemPresupuestario;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the estabilidadLaboral
     */
    public boolean isEstabilidadLaboral() {
        return estabilidadLaboral;
    }

    /**
     * @param estabilidadLaboral the estabilidadLaboral to set
     */
    public void setEstabilidadLaboral(boolean estabilidadLaboral) {
        this.estabilidadLaboral = estabilidadLaboral;
    }

    /**
     * @return the aplicaAportePatronal
     */
    public boolean isAplicaAportePatronal() {
        return aplicaAportePatronal;
    }

    /**
     * @param aplicaAportePatronal the aplicaAportePatronal to set
     */
    public void setAplicaAportePatronal(boolean aplicaAportePatronal) {
        this.aplicaAportePatronal = aplicaAportePatronal;
    }

    /**
     * @return the aplicaAporteIndividual
     */
    public boolean isAplicaAporteIndividual() {
        return aplicaAporteIndividual;
    }

    /**
     * @param aplicaAporteIndividual the aplicaAporteIndividual to set
     */
    public void setAplicaAporteIndividual(boolean aplicaAporteIndividual) {
        this.aplicaAporteIndividual = aplicaAporteIndividual;
    }

    /**
     * @return the listaModalidadNivelOcupacional
     */
    public List<ModalidadNivelOcupacional> getListaModalidadNivelOcupacional() {
        return listaModalidadNivelOcupacional;
    }

    /**
     * @param listaModalidadNivelOcupacional the listaModalidadNivelOcupacional to set
     */
    public void setListaModalidadNivelOcupacional(List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional) {
        this.listaModalidadNivelOcupacional = listaModalidadNivelOcupacional;
    }
}
