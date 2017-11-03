/*
 *  BeneficiarioInstitucional.java
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
 *  21/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "servidor_institucion", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_NOMBRE_E_INSTITUCION, query = "SELECT o FROM ServidorInstitucion o where o.servidor.apellidosNombres like ?1 and o.idInstitucion=?2"),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_INSTITUCION, query = "SELECT a FROM ServidorInstitucion a where a.idInstitucion = ?1 order by a.servidor.apellidosNombres"),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_NUMERO_DOCUMENTO_SERVIDOR, query = "SELECT a FROM ServidorInstitucion a where a.servidor.numeroIdentificacion = ?1 order by a.servidor.apellidosNombres, a.fechaCreacion desc"),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_TIPO_Y_NUMERO_DOCUMENTO_SERVIDOR, query = "SELECT a FROM ServidorInstitucion a where a.servidor.tipoIdentificacion = ?1 AND a.servidor.numeroIdentificacion = ?2  order by a.servidor.apellidosNombres, a.fechaCreacion desc"),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_VIGENTES, query = "SELECT a FROM ServidorInstitucion a order by a.servidor.apellidosNombres "),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_INSTITUCION_SERVIDOR, query = "SELECT a FROM ServidorInstitucion a where a.idInstitucion=?1 AND a.servidor.tipoIdentificacion=?2 AND a.servidor.numeroIdentificacion=?3 ORDER BY a.fechaIngreso DESC "),
    @NamedQuery(name = ServidorInstitucion.BUSCAR_POR_INSTITUCION_SERVIDOR_ID, query = "SELECT a FROM ServidorInstitucion a where a.idInstitucion=?1 AND a.servidor.id=?2 ")

})
public class ServidorInstitucion extends EntidadBasica {

    /**
     * Variable para busqueda por Institucion
     */
    public static final String BUSCAR_POR_INSTITUCION = "ServidorInstitucion.buscarporInstitucion ";

    /**
     * Variable para busqueda por Servidor
     */
    public static final String BUSCAR_POR_NUMERO_DOCUMENTO_SERVIDOR = "ServidorInstitucion.buscarporNumDocumentoServidor";
    /**
     * Variable para busqueda por Servidor
     */
    public static final String BUSCAR_POR_TIPO_Y_NUMERO_DOCUMENTO_SERVIDOR = "ServidorInstitucion.buscarporTipoYNumDocumentoServidor";

    /**
     * Nombre para busqeda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "ServidorInstitucion.buscarVigente";

    /**
     * Nombre de conualta.
     */
    public static final String BUSCAR_POR_NOMBRE_E_INSTITUCION = "ServidorInstitucion.buscarPorNombreEInstitucion";

    /**
     *
     */
    public static final String BUSCAR_POR_INSTITUCION_SERVIDOR = "ServidorInstitucion.buscarPorInstitucionServidor";

    /**
     *
     */
    public static final String BUSCAR_POR_INSTITUCION_SERVIDOR_ID = "ServidorInstitucion.buscarPorInstitucionServidorId";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a servidor
     */
    @JoinColumn(name = "servidor_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    /**
     * servidor id.
     */
    @Column(name = "servidor_id")
    private Long idServidor;

    /**
     * Referencia a institucion
     */
    @JoinColumn(name = "institucion_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Institucion institucion;

    /**
     * institucion id.
     */
    @Column(name = "institucion_id")
    private Long idInstitucion;

    /**
     * Especifica la fecha de ingreso
     */
    @Column(name = "fecha_ingreso")
    @Temporal(value = TemporalType.DATE)
    private Date fechaIngreso;
    /**
     * Especifica la fecha de salida
     */
    @Column(name = "fecha_salida")
    @Temporal(value = TemporalType.DATE)
    private Date fechaSalida;

    /**
     * Constructor.
     */
    public ServidorInstitucion() {
        super();
    }

    /**
     * Constructor por defecto.
     *
     * @param id Long
     */
    public ServidorInstitucion(final Long id) {
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
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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

    /**
     * @return the idServidor
     */
    public Long getIdServidor() {
        return idServidor;
    }

    /**
     * @param idServidor the idServidor to set
     */
    public void setIdServidor(Long idServidor) {
        this.idServidor = idServidor;
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set.
     */
    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the idInstitucion
     */
    public Long getIdInstitucion() {
        return idInstitucion;
    }

    /**
     * @param idInstitucion the idInstitucion to set
     */
    public void setIdInstitucion(Long idInstitucion) {
        this.idInstitucion = idInstitucion;
    }

    /**
     * @return the fechaIngreso
     */
    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    /**
     * @param fechaIngreso the fechaIngreso to set
     */
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    /**
     * @return the fechaSalida
     */
    public Date getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
}
