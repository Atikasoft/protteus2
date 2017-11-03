/*
 *  RegimenLaboral.java
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author LRodriguez liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "regimenes_laborales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = RegimenLaboral.BUSCAR_POR_NOMBRE,
            query = "SELECT a FROM RegimenLaboral a where a.nombre like ?1 and a.vigente=true order by a.nombre"),
    @NamedQuery(name = RegimenLaboral.BUSCAR_VIGENTES,
            query = "SELECT a FROM RegimenLaboral a where a.vigente=true order by a.codigo"),
    @NamedQuery(name = RegimenLaboral.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM RegimenLaboral a where a.codigo=?1 and a.vigente=true order by a.nombre")
})
@Setter
@Getter
public class RegimenLaboral extends EntidadBasica {

    /**
     * Variable parabusqeda por nombre.
     */
    public static final String BUSCAR_POR_NOMBRE = "RegimenLaboral.buscarporNombre ";
    /**
     * Variable parabusqeda por nemonico.
     */
    public static final String BUSCAR_POR_CODIGO = "RegimenLaboral.buscarporCodigo ";
    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "RegimenLaboral.buscarVigente";
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
     * Campo explicación de vacaciones para anexo accioón personal de vacaciones.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "explicacion_vacacion")
    private String explicacionVacacion;

    /**
     * Campo explicación de vacaciones para anexo accioón personal de liquidacion de vacaciones.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "explicacion_liquidacion_vacacion")
    private String explicacionLiquidacionVacacion;

    /**
     * Lista de nivel Ocupacional.
     */
    @OneToMany(mappedBy = "regimenLaboral")
    private List<NivelOcupacional> listaNivelOcupacional;
    /**
     * 
     */
    @Transient
    private Boolean esRoot;
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
     */
    public RegimenLaboral() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public RegimenLaboral(Long id) {
        super();
        this.id = id;
    }

}
