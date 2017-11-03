package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the Partidas database table.
 */
@Entity
@Table(name = "partidas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Partida.BUSCAR_POR_CODIGO_Y_NOMBRE,
            query = "SELECT o FROM Partida o where o.codigo like ?1 or o.nombre like ?2 and o.vigente=true order by o.nombre"),
    @NamedQuery(name = Partida.BUSCAR_POR_CODIGO, query = "SELECT o FROM Partida o WHERE o.codigo=?1 and o.vigente=true"),
    @NamedQuery(name = Partida.BUSCAR_POR_NOMBRE, query = "SELECT o FROM Partida o WHERE o.nombre=?1 and o.id<>?2 and o.vigente=true"),
    @NamedQuery(name = Partida.BUSCAR_VIGENTES, query = "SELECT o FROM Partida o WHERE o.vigente=true ORDER BY o.nombre")
})
public class Partida extends EntidadBasica implements Serializable {

    /**
     * Variable para busqueda por codigo y nombre.
     */
    public static final String BUSCAR_POR_CODIGO_Y_NOMBRE = "Partida.buscarPorCodigoNombre ";
    
    public static final String BUSCAR_POR_CODIGO = "Partida.BuscarPorCodigo";
    
    /**
     * Variable para busqueda por nombre, excepto la partida especificada por el id.
     */
    public static final String BUSCAR_POR_NOMBRE = "Partida.BuscarPorNombre";
        
    public static final String BUSCAR_VIGENTES = "Partida.vigentes";

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clave primaria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador funcional.
     */
    @Column(name = "codigo")
    private String codigo;

    /**
     * Descripción clara de lo que representa el registro.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Nombre.
     */
    @Column(name = "nombre")
    private String nombre;

    /**
     * Partida.
     */
    @Column(name = "partida")
    private String partida;
    
    /**
     * Con certificacion presupuestaria puesto.
     */
    @Column(name = "con_certificacion_presupuestaria_puesto")
    private Boolean certificacionPresupuestariaPuesto;
    

    /**
     * Constructor sin argumentos.
     */
    public Partida() {
        super();
    }

    /**
     *
     * @param id
     */
    public Partida(final Long id) {
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
    public void setId(final Long id) {
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
     * @return the partida
     */
    public String getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(final String partida) {
        this.partida = partida;
    }

    public Boolean getCertificacionPresupuestariaPuesto() {
        return certificacionPresupuestariaPuesto;
    }

    public void setCertificacionPresupuestariaPuesto(Boolean certificacionPresupuestariaPuesto) {
        this.certificacionPresupuestariaPuesto = certificacionPresupuestariaPuesto;
    }
    
    
}