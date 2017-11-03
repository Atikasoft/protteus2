/*
 *  TipoNomina.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo.distributivo;

import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Historico de cargas masivas de puestos
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Entity
@XmlRootElement(name = "cargas_masivas_puestos")
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "cargas_masivas_puestos", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CargaMasivaPuesto.BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL,
            query = "Select o from CargaMasivaPuesto o where o.idInstitucionEjercicioFiscal = ?1 order by o.fechaCreacion desc ")
})
@Getter
@Setter
public class CargaMasivaPuesto extends EntidadBasica {

    public static final String BUSCAR_POR_INSTITUCION_EJECICIO_FISCAL = "CargaMasivaPuesto.buscarPorInstitucionEjecicioFiscal";

    @Id
    @XmlElement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Comma separado
     */
    @XmlElement
    @Basic(optional = false)
    @Column(name = "puestos")
    private String puestos;

    /**
     * Referencia con institucion x ejercicio fiscal.
     */
    @JoinColumn(name = "institucion_ejercicio_fiscal_id", insertable = false, updatable = false)
    @ManyToOne
    private InstitucionEjercicioFiscal institucionEjercicioFiscal;

    /**
     * institucion x ejercicio fiscal.
     */
    @XmlElement
    @Column(name = "institucion_ejercicio_fiscal_id")
    private Long idInstitucionEjercicioFiscal;
    
    /**
     * 
     */
    @XmlElement
    @Column(name = "total_puestos_generados")
    private Integer totalPuestosGenerados;

    /**
     * Constructor por defecto.
     */
    public CargaMasivaPuesto() {
        super();
    }

    @Override
    public String toString() {
        return UtilCadena.concatenar(id, ":ejercicioFiscal:", getInstitucionEjercicioFiscal().getEjercicioFiscal().getNombre());
    }

    /**
     * Recibe un coma separado de los codigos de los puestos
     *
     * @param codigosPuestos
     */
    public void setPuestosComoString(final List<Long> codigosPuestos) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (codigosPuestos != null) {
            for (Long codigo : codigosPuestos) {
                if (i++ > 0) {
                    sb.append(",");
                }
                sb.append(codigo);
            }
        }
        puestos = sb.toString();
    }

    public List<Long> obtenerCodigosPuestos() {
        List<Long> codigos = new ArrayList<>();
        if (puestos != null && !puestos.isEmpty()) {
            List<String> codigosStr = Arrays.asList(puestos.split(","));
            for (String str : codigosStr) {
                codigos.add(Long.parseLong(str));
            }
        }
        return codigos;
    }
}
