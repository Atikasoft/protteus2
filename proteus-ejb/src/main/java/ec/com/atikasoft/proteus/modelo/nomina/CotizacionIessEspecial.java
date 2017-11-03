/*
 *  CotizacionIess.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo.nomina;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * CotizacionIessEspecial
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Entity
@Table(name = "cotizaciones_iess_especiales", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CotizacionIessEspecial.BUSCAR_VIGENTES, query = "Select o From CotizacionIessEspecial o Where o.vigente = true order by o.nombre "),
    @NamedQuery(name = CotizacionIessEspecial.CONTAR_POR_NOMBRE, query = "Select COUNT(o) From CotizacionIessEspecial o Where o.nombre = ?1 and o.vigente = true "),
    @NamedQuery(name = CotizacionIessEspecial.CONTAR_POR_NOMBRE_IGNORAR_ID, query = "Select COUNT(o) From CotizacionIessEspecial o Where o.nombre = ?1 and o.id <> ?2 and o.vigente = true ")
})
@Setter
@Getter
public class CotizacionIessEspecial extends EntidadBasica {

    /**
     * Buscar registros vigente = true.
     */
    public static final String BUSCAR_VIGENTES = "CotizacionIessEspecial.buscarVigentes";
    /**
     * Contar por nombre.
     */
    public static final String CONTAR_POR_NOMBRE = "CotizacionIessEspecial.contarPorNombre";
    /**
     * Contar por nombre ignorando el id de edicion.
     */
    public static final String CONTAR_POR_NOMBRE_IGNORAR_ID = "CotizacionIessEspecial.contarPorNombreIgnorarId";
    
    /**
     * Constructor por defecto.
     */
    public CotizacionIessEspecial() {
        super();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * 
     */
    @Column(name = "nombre")
    protected String nombre;

    /**
     * PORCENTAJE APORTE INDIVIDUAL de cotizacion IESS.
     */
    @Column(name = "porcentaje_aporte_individual")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAporteIndividual;

    /**
     * PORCENTAJE APORTE PATRONAL de cotizacion IESS.
     */
    @Column(name = "porcentaje_aporte_patronal")
    @NotNull
    @Max(100)
    @Min(0)
    private BigDecimal porcentajeAportePatronal;

    

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

   
}
