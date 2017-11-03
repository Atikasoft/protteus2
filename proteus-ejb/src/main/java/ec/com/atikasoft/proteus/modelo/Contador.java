package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@Entity
@Table(name = "contadores", catalog = "sch_proteus")
public class Contador extends EntidadBasica {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     */
    @Column(name = "contador_codigo_puestos")
    private Long contadorCodigoPuestos;

    /**
     * Constructor.
     */
    public Contador() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id
     */
    public Contador(final Long id) {
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
     * @return the contadorCodigoPuestos
     */
    public Long getContadorCodigoPuestos() {
        return contadorCodigoPuestos;
    }

    /**
     * @param contadorCodigoPuestos the contadorCodigoPuestos to set
     */
    public void setContadorCodigoPuestos(Long contadorCodigoPuestos) {
        this.contadorCodigoPuestos = contadorCodigoPuestos;
    }

}
