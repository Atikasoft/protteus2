/*
 *  RolServidor.java
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
 *  21/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "roles_servidor", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = RolServidor.BUSCAR_VIGENTES,
            query = "SELECT a FROM RolServidor a where a.vigente=true order by a.rol.nombre "),
    @NamedQuery(name = RolServidor.BUSCAR_POR_ROL,
            query = "SELECT a FROM RolServidor a where a.vigente=true and a.rol.id = ?1 order by a.rol.nombre "),
    @NamedQuery(name = RolServidor.BUSCAR_POR_CODIGO_ROL,
            query = "SELECT a FROM RolServidor a where a.vigente=true and a.rol.codigo = ?1 "),
    @NamedQuery(name = RolServidor.BUSCAR_POR_CODIGO_ROL_UNIDAD,
            query = "SELECT a FROM RolServidor a where a.vigente=true and a.rol.codigo = ?1 and a.servidor.id in ("
            + " SELECT dd.idServidor FROM DistributivoDetalle dd WHERE dd.vigente=true AND dd.idServidor IS NOT NULL AND "
            + " (dd.distributivo.unidadOrganizacional.codigo=?2)"
            + ")"),
    @NamedQuery(name = RolServidor.BUSCAR_POR_SERVIDOR,
            query = "SELECT a FROM RolServidor a where a.vigente=true and a.servidor.id = ?1 order by  a.rol.nombre "),
    @NamedQuery(name = RolServidor.BUSCAR_POR_SERVIDOR_ROL,
            query = "SELECT a FROM RolServidor a where a.vigente=true and a.servidor.numeroIdentificacion = ?1 and "
            + " a.rol.codigo=?2"),
    @NamedQuery(name = RolServidor.BUSCAR_POR_SERVIDOR_ROL_UNIDAD,
            query
            = "SELECT a FROM RolServidor a where a.vigente=true and a.servidor.numeroIdentificacion = ?1 and a.rol.codigo=?2 "
            + " and a.servidor.id in ("
            + " SELECT dd.idServidor FROM DistributivoDetalle dd WHERE dd.vigente=true AND dd.idServidor IS NOT NULL"
            + ")"),
    @NamedQuery(name = RolServidor.QUITAR_VIGENCIA, query
            = "UPDATE RolServidor o SET o.vigente=false WHERE o.servidor.id=?1")
})
public class RolServidor extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "RolServidor.buscarVigente";

    /**
     * Nombre para busqueda de vigentes por id del rol.
     */
    public static final String BUSCAR_POR_ROL = "RolServidor.buscarPorRol";

    /**
     * Nombre para busqueda de vigentes por id del rol.
     */
    public static final String BUSCAR_POR_CODIGO_ROL = "RolServidor.buscarCodigoPorRol";

    /**
     * Nombre para busqueda por rol y unidad.
     */
    public static final String BUSCAR_POR_CODIGO_ROL_UNIDAD = "RolServidor.buscarPorCodigoRolUnidad";

    /**
     * Nombre para busqueda de vigentes por id de servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR = "RolServidor.buscarPorServidor";

    /**
     * Nombre para busqueda de vigentes por id de servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR_ROL = "RolServidor.buscarPorServidorRol";

    /**
     * Nombre para busqueda de vigentes por id de servidor.
     */
    public static final String BUSCAR_POR_SERVIDOR_ROL_UNIDAD = "RolServidor.buscarPorServidorRolUnidad";

    /**
     *
     */
    public static final String QUITAR_VIGENCIA = "RolServidor.quitarVigencia";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo de referencia a la entidad rol.
     */
    @JoinColumn(name = "rol_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Rol rol;

    /**
     * Campo de referencia a la entidad menu.
     */
    @JoinColumn(name = "servidor_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * Constructor.
     */
    public RolServidor() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public RolServidor(final Long id) {
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
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
}
