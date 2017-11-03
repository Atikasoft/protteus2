/*
 *  Vacacion.java
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
 *  29/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
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
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "vacaciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Vacacion.BUSCAR_POR_SERVIDORINSTITUCIONID, query = "SELECT a FROM Vacacion a WHERE a.vigente=true"
            + " AND a.servidorInstitucion.id=?1 "),
    @NamedQuery(name = Vacacion.BUSCAR_POR_SERVIDOR_SOBRELIMITE, query = "SELECT a FROM Vacacion a WHERE a.vigente=true"
            + " AND a.saldo>?1 AND a.servidorInstitucion.servidor.id IN (SELECT dd.idServidor FROM DistributivoDetalle "
            + "dd WHERE dd.vigente=true AND dd.escalaOcupacional.nivelOcupacional.idRegimenLaboral=?2)"),
    @NamedQuery(name = Vacacion.BUSCAR_POR_SERVIDOR_ID, query = "SELECT a FROM Vacacion a WHERE a.vigente=true "
            + "AND a.servidorInstitucion.servidor.id IN (SELECT s.id FROM Servidor s WHERE s.vigente=true AND s.id=?1)"),
    @NamedQuery(name = Vacacion.BUSCAR_POR_SERVIDOR_INSTITUCION, query = "SELECT a FROM Vacacion a WHERE "
            + "a.vigente=true AND a.servidorInstitucion.idServidor =?1 AND a.servidorInstitucion.idInstitucion=?2")

})
@Setter
@Getter
public class Vacacion extends EntidadBasica {

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDORINSTITUCIONID = "Vacacion.buscarPorServidorInstitucionId";
    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_SOBRELIMITE = "Vacacion.buscarServidorSorelimite";
    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_ID = "Vacacion.buscarServidorPorId";

    /**
     *
     */
    public static final String BUSCAR_POR_SERVIDOR_INSTITUCION = "Vacacion.buscarServidorInstitucion";
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     *
     * /**
     * Referencia servidorInstitucion.
     */
    @JoinColumn(name = "servidor_institucion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ServidorInstitucion servidorInstitucion;

    /**
     * Saldo de vacaciones en minutos.
     */
    @Column(name = "saldo")
    private Long saldo;

    /**
     * Saldo de proporcional en minutos.
     */
    @Column(name = "saldo_proporcional")
    private Long saldoProporcional;

    /**
     * Saldo de perdida en minutos.
     */
    @Column(name = "saldo_perdida")
    private Long saldoPerdida;

    /**
     * Saldo del mes actual.
     */
    @Column(name = "saldo_mes_actual")
    private Long saldoMesActual;

    /**
     * Mes actual del calculo de vacaciones
     */
    @Column(name = "mes_actual")
    private Integer mesActual;

    /**
     * Constructor.
     */
    public Vacacion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Vacacion(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
