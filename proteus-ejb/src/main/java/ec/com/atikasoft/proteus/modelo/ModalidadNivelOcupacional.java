/*
 *  ModalidadNivelOcupacional.java
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
 *  07/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import javax.persistence.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "modalidades_laborales_nivelocu", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ModalidadNivelOcupacional.BUSCAR_VIGENTES,
    query = "SELECT a FROM ModalidadNivelOcupacional a where a.vigente=true order by a.modalidadLaboral.nombre"),
    
    @NamedQuery(name = ModalidadNivelOcupacional.BUSCAR_POR_MODALIDAD_LABORAL,
    query = "SELECT a FROM ModalidadNivelOcupacional a where a.vigente=true and a.modalidadLaboral.id =?1 order by a.modalidadLaboral.nombre")
})
public class ModalidadNivelOcupacional extends EntidadBasica {
 
  
    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "ModalidadNivelOcupacional.buscarVigente";
    
     
    /**
     * Nombre para busqueda de vigentes de una determinada Modalidad Laboral.
     */
    public static final String BUSCAR_POR_MODALIDAD_LABORAL = "ModalidadNivelOcupacional.buscarPorModalidad";
    

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

   
    /**
     * referencia del movimiento.
     */
    @JoinColumn(name = "modalidad_laboral_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ModalidadLaboral modalidadLaboral;

    @JoinColumn(name = "nivel_ocupacional_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private NivelOcupacional nivelOcupacional;
      
     /**
     * modalidad laboral id.
     */
    @Column(name = "modalidad_laboral_id")
    private Long idModalidadLaboral;
    
     /**
     * nivel ocupacional id id.
     */
    @Column(name = "nivel_ocupacional_id")
    private Long idNivelOcupacional;
  
    /**
     * Constructor.
     */
    public ModalidadNivelOcupacional() {
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
    public void setId(final Long id) {
        this.id = id;
    }

     @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * @return the modalidadLaboral
     */
    public ModalidadLaboral getModalidadLaboral() {
        return modalidadLaboral;
    }
/**
     * @param modalidadLaboral the nivelOcupacional to set
     */
    public void setModalidadLaboral(ModalidadLaboral modalidadLaboral) {
        this.modalidadLaboral = modalidadLaboral;
    }
    /**
     * @return the nivelOcupacional
     */
    public NivelOcupacional getNivelOcupacional() {
        return nivelOcupacional;
    }

    /**
     * @param nivelOcupacional the nivelOcupacional to set
     */
    public void setNivelOcupacional(NivelOcupacional nivelOcupacional) {
        this.nivelOcupacional = nivelOcupacional;
    }

    /**
     * @return the idModalidadLaboral
     */
    public Long getIdModalidadLaboral() {
        return idModalidadLaboral;
    }

    /**
     * @return the idNivelOcupacional
     */
    public Long getIdNivelOcupacional() {
        return idNivelOcupacional;
    }

    /**
     * @param idModalidadLaboral the idModalidadLaboral to set
     */
    public void setIdModalidadLaboral(Long idModalidadLaboral) {
        this.idModalidadLaboral = idModalidadLaboral;
    }

    /**
     * @param idNivelOcupacional the idNivelOcupacional to set
     */
    public void setIdNivelOcupacional(Long idNivelOcupacional) {
        this.idNivelOcupacional = idNivelOcupacional;
    }

     
}
