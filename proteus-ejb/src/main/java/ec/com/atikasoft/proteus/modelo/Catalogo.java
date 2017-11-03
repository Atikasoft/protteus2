/*
 *  Catalogo.java
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
 *  01/10/2013
 *
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 * Catalogo
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Entity
@Table(name = "catalogo", catalog = "sch_proteus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Catalogo.BUSCAR_POR_TIPO_CATALOGO_ID, query = "Select o FROM Catalogo "
            + "o where o.vigente=true and o.tipoCatalogoId = ?1  order by o.nombre"),
    @NamedQuery(name = Catalogo.BUSCAR_TIPO_CATALOGO_CODIGO, query = "Select o FROM Catalogo "
            + "o where o.vigente=true and o.tipoCatalogo.codigo = ?1  order by o.nombre"),
    @NamedQuery(name = Catalogo.BUSCAR_POR_NEMONICO, query
            = "SELECT a FROM Catalogo a where a.vigente=true and  a.codigo=?1"),
    @NamedQuery(name = Catalogo.BUSCAR_TIPO_CATALOGO, query
            = "SELECT a FROM Catalogo a where a.vigente=true AND  a.codigo=?1 AND a.tipoCatalogo.codigo=?2 ")
})
@Setter
@Getter
public class Catalogo extends EntidadBasica {

    /**
     * Nombre de cosulta.
     */
    public static final String BUSCAR_POR_TIPO_CATALOGO_ID = "Catalogo.buscarPorTipoCatalogoId";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_POR_NEMONICO = "Catalogo.buscarporNemonico ";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_TIPO_CATALOGO_CODIGO = "Catalogo.buscarporTipoCatalogoCodigo";

    /**
     * Nombre de consulta para buscar por nemonico.
     */
    public static final String BUSCAR_TIPO_CATALOGO = "Catalogo.buscarporTipoCatalogo";

    /**
     * Id de la clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * campo codigo.
     */
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;

    /**
     * campo nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    /**
     * Tipo del catalogo.
     */
    @JoinColumn(name = "id_tipocatalogo", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoCatalogo tipoCatalogo;

    /**
     * Id de tipo de catalogo.
     */
    @Column(name = "id_tipocatalogo")
    private Long tipoCatalogoId;

    /**
     * contructor.
     */
    public Catalogo() {
        super();
    }

    /**
     * contructor con paremetro id.
     *
     * @param id Catalogo
     */
    public Catalogo(final Long id) {
        this.id = id;
    }

}
