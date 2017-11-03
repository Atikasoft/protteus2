/*
 *  NivelOcupacional.java
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
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
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "niveles_ocupacionales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NivelOcupacional.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM NivelOcupacional a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = NivelOcupacional.BUSCAR_VIGENTES,
            query = "SELECT a FROM NivelOcupacional a where a.vigente=true order by a.nombre"),
    @NamedQuery(name = NivelOcupacional.BUSCAR_POR_ID_REGIMEN,
            query = "SELECT a FROM NivelOcupacional a where a.idRegimenLaboral=?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = NivelOcupacional.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM NivelOcupacional a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
@Setter
@Getter
public class NivelOcupacional extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "NivelOcupacional.buscarporNombre ";
    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_CODIGO = "NivelOcupacional.buscarporCodigo ";
    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "NivelOcupacional.buscarVigente";
    public static final String BUSCAR_POR_ID_REGIMEN = "NivelOcupacional.buscarPorIdRegimen";
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    /**
     * Campo nombre.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    /**
     * Campo descripción.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "descripcion")
    private String descripcion;

    /**
     *
     */
    @Column(name = "es_libre_remocion")
    private Boolean esLibreRemocion;
    /**
     * Referencia a regimenLaboral.
     */
    @JoinColumn(name = "regimenes_laborales_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private RegimenLaboral regimenLaboral;
    /**
     * Lista de escala ocupacional
     */
    @OneToMany(mappedBy = "nivelOcupacional")
    private List<EscalaOcupacional> listaEscalaOcupacional;
    /**
     * Lista de modalidadLaboral_NivelOcupacional
     */
    @OneToMany(mappedBy = "nivelOcupacional")
    private List<ModalidadNivelOcupacional> listaModalidadNivelOcupacional;
    /**
     * regimenes laborales id.
     */
    @Column(name = "regimenes_laborales_id")
    private Long idRegimenLaboral;

    /**
     *
     */
    @Transient
    private Boolean seleccionado;
    /**
     *
     */
    @Transient
    private BigDecimal rmu;
    /**
     *
     */
    @Transient
    private String grado;
    /**
     *
     */
    @Transient
    private BigDecimal rmuMaximo;

    /**
     * Constructor.
     *
     */
    public NivelOcupacional() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public NivelOcupacional(Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
