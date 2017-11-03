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
@Table(name = "certificaciones_presupuestarias_historico", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = CertificacionPresupuestariaHistorico.BUSCAR_VIGENTES, 
            query = "SELECT c FROM CertificacionPresupuestariaHistorico c where c.vigente=true")    
})
@Getter
@Setter
public class CertificacionPresupuestariaHistorico extends EntidadBasica {
    
    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "CertificacionPresupuestariaHistorico.buscarVigente";
    
    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 
    
    
    /**
     * Número de certificación presupuestaria.
     */
    @Column(name = "certificacion_presupuestaria")
    private String numeroCertificacionPresupuestaria;
   
    /**
     * Certificación presupuestaria.
     */
    @JoinColumn(name = "certificacion_presupuestaria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private CertificacionPresupuestaria certificacionPresupuestaria;

    /**
     * Nombre de modalidad laboral.
     */
    @Column(name = "nombre_modalidad_laboral")
    private String nombreModalidadLaboral;

    /**
     * Codigo de modalidad laboral.
     */
    @Column(name = "codigo_modalidad_laboral")
    private String codigoModalidadLaboral;

    /**
     * Nombre de la unidad presupuestaria.
     */
    @Column(name = "tipo_modalidad")
    private String tipoModalidad;
    
    /**
     * Nombre de la unidad presupuestaria.
     */
    @Column(name = "nombre_unidad_presupuestaria")
    private String nombreUnidadPresupuestaria;
    
    /**
     * Codigo de la unidad presupuestaria.
     */
    @Column(name = "codigo_unidad_presupuestaria")
    private String codigoUnidadPresupuestaria;
    
}
