/*
 *  NominaEjecucion.java
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

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Contiene los eventos de la ejecucion de una nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "nominas_ejecuciones", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaEjecucion.BUSCAR_POR_NOMINA, query = "SELECT o FROM NominaEjecucion o WHERE o.nomina.id=?1 AND o.vigente=true ORDER BY o.fechaCreacion DESC"),
    @NamedQuery(name = NominaEjecucion.QUITAR_VIGENCIA, query = "UPDATE NominaEjecucion o SET o.vigente=false WHERE o.nomina.id=?1")
})
public class NominaEjecucion extends EntidadBasica implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final String BUSCAR_POR_NOMINA = "NominaEjecucion.buscarPorNomina";

    /**
     *
     */
    public static final String QUITAR_VIGENCIA = "NominaEjecucion.quitarVigencia";

    /**
     * Identificador unico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Tipo de documento.
     */
    @Column(name = "evento")
    private String evento;

    /**
     * Nominas.
     */
    @ManyToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    /**
     * Constructor sin argumentos.
     */
    public NominaEjecucion() {
        super();
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
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(Nomina nomina) {
        this.nomina = nomina;
    }

}
