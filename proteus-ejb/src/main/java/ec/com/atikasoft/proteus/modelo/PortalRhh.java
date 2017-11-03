/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author atikasoft
 */
@Entity
@Table(name = "potal_rhh", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = PortalRhh.BUSCAR_VIGENTES,
    query = "SELECT a FROM PortalRhh a where a.vigente=true"),
    @NamedQuery(name = PortalRhh.BUSCAR_POR_CODIGO,
            query = "SELECT a FROM PortalRhh a where a.codigo like ?1 order by a.codigo")
})
public class PortalRhh extends EntidadBasica {

    /**
     * Nombre de consulta para buscar por vigentes.
     */
    public static final String BUSCAR_VIGENTES = "PortalRhh.buscarporvigentes ";
    
    /**
     * Nombre de consulta para busqueda por codigo.
     */
    public static final String BUSCAR_POR_CODIGO = "PortalRhh.buscarPorCodigo ";
    
    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * codigo
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo")
    private String codigo;
    /**
     * nombre
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre")
    private String nombre;
    /**
     * nombreImagen
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "nombre_imagen")
    private String nombreImagen;
    
       /**
     * nombreImagen
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "url")
    private String url;

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
     * @return the nombreImagen
     */
    public String getNombreImagen() {
        return nombreImagen;
    }

    /**
     * @param nombreImagen the nombreImagen to set
     */
    public void setNombreImagen(final String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
