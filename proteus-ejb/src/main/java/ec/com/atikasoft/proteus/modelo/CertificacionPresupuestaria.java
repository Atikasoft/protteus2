/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Leydis Garzon
 */
@Entity
@Table(name = "certificaciones_presupuestarias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true "
            + "and UPPER(c.certificacionPresupuestaria) = ?1"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true "
            + "and UPPER(c.certificacionPresupuestaria) = ?1 and c.id<>?2"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_Y_UNIDAD_PRESUPUESTARIA,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true "
            + "and UPPER(c.certificacionPresupuestaria) = ?1 and c.unidadPresupuestaria.id <> ?2"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID_Y_UNIDAD_PRESUPUESTARIA,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true "
            + "and UPPER(c.certificacionPresupuestaria) = ?1 and c.id<>?2 and c.unidadPresupuestaria.id <> ?3"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_UNIDAD_PRESUPUESTARIA,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true and "
            + "c.unidadPresupuestaria.id = ?1 order by c.modalidadLaboral.nombre"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_UNIDAD_PRESUPUESTARIA_Y_MODALIDAD,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true and "
            + "c.unidadPresupuestaria.id = ?1 and c.modalidadLaboral.id = ?2 order by c.modalidadLaboral.nombre"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_POR_MODALIDAD_LABORAL,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true and "
            + "c.modalidadLaboral.id = ?1 order by c.modalidadLaboral.nombre"),
    @NamedQuery(name = CertificacionPresupuestaria.BUSCAR_VIGENTES,
            query = "SELECT c FROM CertificacionPresupuestaria c where c.vigente=true")
})
@Getter
@Setter
public class CertificacionPresupuestaria extends EntidadBasica {

    /**
     * Nombre para busqueda por certificación presupuestaria.
     */
    public static final String BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA
            = "CertificacionPresupuestaria.buscarPorCertificacionPresupuestaria";

    /**
     * Nombre para busqueda por certificación presupuestaria exceptuando la del id especificado.
     */
    public static final String BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID
            = "CertificacionPresupuestaria.buscarPorCertificacionPresupuestariaConId";
    /**
     * Nombre para busqueda por certificación presupuestaria y unidad presupuestaria.
     */
    public static final String BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_Y_UNIDAD_PRESUPUESTARIA
            = "CertificacionPresupuestaria.buscarPorCertificacionPresupuestariaYUnidadPresupuestaria";

    /**
     * Nombre para busqueda por certificación presupuestaria y unidad presupuestaria exceptuando la del id especificado.
     */
    public static final String BUSCAR_POR_CERTIFICACION_PRESUPUESTARIA_CON_ID_Y_UNIDAD_PRESUPUESTARIA
            = "CertificacionPresupuestaria.buscarPorCertificacionPresupuestariaConIdYUnidadPresupuestaria";

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "CertificacionPresupuestaria.buscarVigente";

    /**
     * Nombre para busqueda por unidad presupuestaria.
     */
    public static final String BUSCAR_POR_UNIDAD_PRESUPUESTARIA
            = "CertificacionPresupuestaria.buscarPorUnidadPresupuestaria";

    /**
     * Nombre para busqueda por unidad presupuestaria y modalidad laboral.
     */
    public static final String BUSCAR_POR_UNIDAD_PRESUPUESTARIA_Y_MODALIDAD
            = "CertificacionPresupuestaria.buscarPorUnidadPresupuestariaYModalidad";

    /**
     * Nombre para busqueda por modalidad laboral.
     */
    public static final String BUSCAR_POR_MODALIDAD_LABORAL
            = "CertificacionPresupuestaria.buscarPorModalidad";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a modalidad laboral.
     */
    @JoinColumn(name = "modalidad_laboral_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ModalidadLaboral modalidadLaboral;

    /**
     * Referencia a unidad presupuestaria.
     */
    @JoinColumn(name = "unidad_presupuestaria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnidadPresupuestaria unidadPresupuestaria;

    /**
     * Número de certificación presupuestaria
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     *
     * @param modalidadLaboral datos de la modilidad laboral
     * @param unidadPresupuestaria datos de la unidad presupuestaria
     */
    public CertificacionPresupuestaria(ModalidadLaboral modalidadLaboral, UnidadPresupuestaria unidadPresupuestaria) {
        this.modalidadLaboral = modalidadLaboral;
        this.unidadPresupuestaria = unidadPresupuestaria;
    }

    /**
     *
     */
    public CertificacionPresupuestaria() {
    }
    
    public void setCertificacionPresupuestaria(String certificacionPresupuestaria){
        this.certificacionPresupuestaria = certificacionPresupuestaria.toUpperCase();
    }

}
