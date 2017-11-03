/*
 *  ProcesoNominaProblema.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Mar 3, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.nomina;

import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;

/**
 * Contiene la relacion de novedades y nominas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "nominas_detalles_novedades", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaDetalleNovedad.ELIMINAR,
    query = "DELETE FROM NominaDetalleNovedad o  WHERE o.nominaDetalle.nomina.id=?1 "),
    @NamedQuery(name = NominaDetalleNovedad.ELIMINAR_POR_SERVIDOR,
    query = "DELETE FROM NominaDetalleNovedad o WHERE o.nominaDetalle.nomina.id=?1 "
    + " AND o.nominaDetalle.distributivoDetalle.id=?2")
})
public class NominaDetalleNovedad extends EntidadBasica {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR = "NominaDetalleNovedad.eliminar";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String ELIMINAR_POR_SERVIDOR = "NominaDetalleNovedad.eliminarPorServidor";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nominas.
     */
    @ManyToOne
    @JoinColumn(name = "nomina_detalle_id")
    private NominaDetalle nominaDetalle;

    /**
     * Novedades.
     */
    @ManyToOne
    @JoinColumn(name = "novedad_detalle_id")
    private NovedadDetalle novedadDetalle;

    /**
     * Constructor sin argumentos.
     */
    public NominaDetalleNovedad() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the nominaDetalle
     */
    public NominaDetalle getNominaDetalle() {
        return nominaDetalle;
    }

    /**
     * @return the novedadDetalle
     */
    public NovedadDetalle getNovedadDetalle() {
        return novedadDetalle;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param nominaDetalle the nominaDetalle to set
     */
    public void setNominaDetalle(final NominaDetalle nominaDetalle) {
        this.nominaDetalle = nominaDetalle;
    }

    /**
     * @param novedadDetalle the novedadDetalle to set
     */
    public void setNovedadDetalle(final NovedadDetalle novedadDetalle) {
        this.novedadDetalle = novedadDetalle;
    }
}
