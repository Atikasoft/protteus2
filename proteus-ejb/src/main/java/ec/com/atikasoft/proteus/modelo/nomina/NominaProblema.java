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

import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * Contiene los problemas que se presentaron en la generacion de la nomina.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Entity
@Table(name = "nominas_problemas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = NominaProblema.QUITAR_VIGENCIA, query =
    "UPDATE NominaProblema o SET o.vigente=false WHERE o.nomina.id=?1 "),
    @NamedQuery(name = NominaProblema.QUITAR_VIGENCIA_POR_SERVIDOR,
    query =
    "UPDATE NominaProblema o SET o.vigente=false WHERE o.nomina.id=?1 "
    + " AND o.tipoDocumento=?2 AND o.numeroDocumento=?3"),
    @NamedQuery(name = NominaProblema.QUITAR_VIGENCIA_GENERALES,
    query =
    "UPDATE NominaProblema o SET o.vigente=false WHERE o.nomina.id=?1 "
    + " AND o.tipoDocumento IS NULL AND o.numeroDocumento IS NULL"),
    @NamedQuery(name = NominaProblema.BUSCAR_POR_NOMINA, query =
    "SELECT o FROM NominaProblema o WHERE o.vigente=true AND o.nomina.id=?1")
})
public class NominaProblema extends EntidadBasica implements Serializable {

    /**
     * Version de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Nombre de consulta.
     */
    public static final String ELIMINAR_POR_NOMINA = "NominaProblema.eliminarPorNomina";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String QUITAR_VIGENCIA = "NominaProblema.quitarVigencia";

    /**
     * Actualizacion que quita la vigencia de los problemas.
     */
    public static final String QUITAR_VIGENCIA_POR_SERVIDOR = "NominaProblema.quitarVigenciaPorServidor";

    /**
     * Actualizacion que quita la vigencia de los problemas que no correspnden al servidor
     */
    public static final String QUITAR_VIGENCIA_GENERALES = "NominaProblema.quitarVigenciaGenerales";

    /**
     *
     */
    public static final String BUSCAR_POR_NOMINA = "NominaProblema.buscarPorNomina";

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
    @Column(name = "tipo_documento")
    private String tipoDocumento;

    /**
     * Numero de documento.
     */
    @Column(name = "numero_documento")
    private String numeroDocumento;

    /**
     * Nombres y apellidos completo.
     */
    @Column(name = "apellidos_nombres")
    private String apellidosNombres;

    /**
     * Codigo del rubro.
     */
    @Column(name = "codigo_rubro")
    private String codigoRubro;

    /**
     * Nombre del rubro.
     */
    @Column(name = "nombre_rubro")
    private String nombreRubro;

    /**
     * Monto del rubro.
     */
    @Column(name = "monto")
    private BigDecimal monto;

    /**
     * Descripcion del problema.
     */
    @Column(name = "problema")
    private String problema;

    /**
     * Tipo del problema.
     */
    @ManyToOne
    @JoinColumn(name = "tipo_problema_id")
    private TipoProblema tipoProblema;

    /**
     * Nominas.
     */
    @ManyToOne
    @JoinColumn(name = "nomina_id")
    private Nomina nomina;

    /**
     * Constructor sin argumentos.
     */
    public NominaProblema() {
        super();
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the tipoDocumento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @return the apellidosNombres
     */
    public String getApellidosNombres() {
        return apellidosNombres;
    }

    /**
     * @return the codigoRubro
     */
    public String getCodigoRubro() {
        return codigoRubro;
    }

    /**
     * @return the nombreRubro
     */
    public String getNombreRubro() {
        return nombreRubro;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @return the tipoProblema
     */
    public TipoProblema getTipoProblema() {
        return tipoProblema;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @param tipoDocumento the tipoDocumento to set
     */
    public void setTipoDocumento(final String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(final String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @param apellidosNombres the apellidosNombres to set
     */
    public void setApellidosNombres(final String apellidosNombres) {
        this.apellidosNombres = apellidosNombres;
    }

    /**
     * @param codigoRubro the codigoRubro to set
     */
    public void setCodigoRubro(final String codigoRubro) {
        this.codigoRubro = codigoRubro;
    }

    /**
     * @param nombreRubro the nombreRubro to set
     */
    public void setNombreRubro(final String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(final BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @param tipoProblema the tipoProblema to set
     */
    public void setTipoProblema(final TipoProblema tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    /**
     * @return the problema
     */
    public String getProblema() {
        return problema;
    }

    /**
     * @param problema the problema to set
     */
    public void setProblema(final String problema) {
        this.problema = problema;
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
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }
}
