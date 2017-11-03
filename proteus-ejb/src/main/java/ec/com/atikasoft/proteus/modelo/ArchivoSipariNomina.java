/*
 *  ArchivoSipariNomina.java
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
 *  28/01/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "archivos_sipari_nominas", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = ArchivoSipariNomina.BUSCAR_POR_PADRE,
    query = "SELECT a FROM ArchivoSipariNomina a where a.archivoSipari.id= ?1 and a.vigente=true"),
    @NamedQuery(name = ArchivoSipariNomina.BUSCAR_ENCABEZADOS_POR_PADRE,
    query = "SELECT a FROM ArchivoSipariNomina a where a.archivoSipari.id= ?1 "
    + " and a.encabezado is not null and a.vigente=true"),
    @NamedQuery(name = ArchivoSipariNomina.BUSCAR_INGRESOS_POR_PARTIDA,
    query = "SELECT a FROM ArchivoSipariNomina a where a.archivoSipari.id= ?1 and a.codigoEmpleado=?2  "
    + "  and a.posicionPresupuestal=?3 and a.debeHaber='D'")
})
public class ArchivoSipariNomina extends EntidadBasica {

    /**
     * Variable para busqeda por padre.
     */
    public static final String BUSCAR_POR_PADRE = "ArchivoSipariNomina.buscarporPadre";

    /**
     * Variable para busqeda por padre.
     */
    public static final String BUSCAR_ENCABEZADOS_POR_PADRE = "ArchivoSipariNomina.buscarPorEncabezadosPorPadre";

    /**
     * Busca ingresos por partidas.
     */
    public static final String BUSCAR_INGRESOS_POR_PARTIDA = "ArchivoSipariNomina.buscarIngresosPorPartida";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Referencia a ArchivoSipari.
     */
    @JoinColumn(name = "archivo_sipari_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArchivoSipari archivoSipari;

    /**
     * Campo codigo del Empleado: identificado con el número de cédula de ciudadanía.
     */
    //@NotNull
    @Size(max = 10)
    @Column(name = "codigo_empleado")
    private String codigoEmpleado;

    /**
     * Campo Concepto Nómina: Debe estar codificado en Recursos Humanos, es el codigo del rubro.
     */
    //@NotNull
    @Column(name = "concepto_nomina")
    private String conceptoNomina;

    /**
     * Campo Cuenta Contable: Cuenta contable, debe estar relacionada con el concepto nómina.
     */
    //@NotNull
    @Size(max = 4)
    @Column(name = "cuenta_contable")
    private String cuentaContable;

    /**
     * Campo Debe - Haber: D si es concepto de Gasto , H si es concepto de retención.
     */
    //@NotNull
    @Size(max = 1)
    @Column(name = "debe_haber")
    private String debeHaber;

    /**
     * Campo acreedor: si el concepto es de acreedor de retención RUC del acreedor de retención si procede.
     */
    @Column(name = "acreedor")
    private String acreedor;

    /**
     * Campo beneficiario: si el concepto de Nomina es Retención Judicial.
     */
    @Size(max = 1)
    @Column(name = "beneficiario")
    private String beneficiario;

    /**
     * Campo anticipo: si el concepto es de compensación Anticipo Empleado se indica con A.
     */
    @Size(max = 1)
    @Column(name = "anticipo")
    private String anticipo;

    /**
     * Campo centro_costo: Clasificación del gasto del empleado en cada sociedad.
     */
    //@NotNull
    @Size(max = 4)
    @Column(name = "centro_costo")
    private String centroCosto;

    /**
     * Campo centroGestor, Información organizativa de la partida..
     */
    //@NotNull
    @Size(max = 8)
    @Column(name = "centro_gestor")
    private String centroGestor;

    /**
     * Campo posicion_presupuestal: Información económica y programática de la partida.
     */
    //@NotNull
    @Size(max = 7)
    @Column(name = "posicion_presupuestal")
    private String posicionPresupuestal;

    /**
     * Campo fondo: Información del origen de financiación de la partida.
     */
    //@NotNull
    @Size(max = 2)
    @Column(name = "fondo")
    private String fondo;

    /**
     * Campo proyecto: Se indica hasta el nivel de actividad. Hay un código de proyecto para cada dependencia / centro
     * gestor.
     */
    //@NotNull
    @Size(max = 15)
    @Column(name = "proyecto")
    private String proyecto;

    /**
     * Sociedad.
     */
    @Column(name = "sociedad")
    private String sociedad;

    /**
     * Campo Importe Moneda Local:Valor de la contabilización de nómina del empleado, en USD.
     */
    //@NotNull
    //@Size(max=13,2)
    @Column(name = "importe")
    private BigDecimal importe;

    /**
     * Campo Texto posición.
     */
    @Column(name = "texto")
    private String texto;

    /**
     * Campo codigo.
     */
    //@NotNull
    @Column(name = "encabezado")
    private String encabezado;

    /**
     * programa.
     */
    @Column(name = "programa")
    private String programa;

    /**
     *
     */
    @Column(name = "certificacion_presupuestaria")
    private String certificacionPresupuestaria;

    /**
     * Referencia a ArchivoSipariNomina padre.
     */
    @JoinColumn(name = "encabezado_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ArchivoSipariNomina encabezadoPadre;

    /**
     * Constructor.
     */
    public ArchivoSipariNomina() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ArchivoSipariNomina(final Long id) {
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
     * @return the archivoSipari
     */
    public ArchivoSipari getArchivoSipari() {
        return archivoSipari;
    }

    /**
     * @param archivoSipari the archivoSipari to set
     */
    public void setArchivoSipari(ArchivoSipari archivoSipari) {
        this.archivoSipari = archivoSipari;
    }

    /**
     * @return the codigoEmpleado
     */
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    /**
     * @param codigoEmpleado the codigoEmpleado to set
     */
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    /**
     * @return the conceptoNomina
     */
    public String getConceptoNomina() {
        return conceptoNomina;
    }

    /**
     * @param conceptoNomina the conceptoNomina to set
     */
    public void setConceptoNomina(String conceptoNomina) {
        this.conceptoNomina = conceptoNomina;
    }

    /**
     * @return the cuentaContable
     */
    public String getCuentaContable() {
        return cuentaContable;
    }

    /**
     * @param cuentaContable the cuentaContable to set
     */
    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    /**
     * @return the debeHaber
     */
    public String getDebeHaber() {
        return debeHaber;
    }

    /**
     * @param debeHaber the debeHaber to set
     */
    public void setDebeHaber(String debeHaber) {
        this.debeHaber = debeHaber;
    }

    /**
     * @return the acreedor
     */
    public String getAcreedor() {
        return acreedor;
    }

    /**
     * @param acreedor the acreedor to set
     */
    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    /**
     * @return the beneficiario
     */
    public String getBeneficiario() {
        return beneficiario;
    }

    /**
     * @param beneficiario the beneficiario to set
     */
    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    /**
     * @return the anticipo
     */
    public String getAnticipo() {
        return anticipo;
    }

    /**
     * @param anticipo the anticipo to set
     */
    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    /**
     * @return the centroCosto
     */
    public String getCentroCosto() {
        return centroCosto;
    }

    /**
     * @param centroCosto the centroCosto to set
     */
    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    /**
     * @return the posicionPresupuestal
     */
    public String getPosicionPresupuestal() {
        return posicionPresupuestal;
    }

    /**
     * @param posicionPresupuestal the posicionPresupuestal to set
     */
    public void setPosicionPresupuestal(String posicionPresupuestal) {
        this.posicionPresupuestal = posicionPresupuestal;
    }

    /**
     * @return the fondo
     */
    public String getFondo() {
        return fondo;
    }

    /**
     * @param fondo the fondo to set
     */
    public void setFondo(String fondo) {
        this.fondo = fondo;
    }

    /**
     * @return the proyecto
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * @return the importe
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * @param importe the importe to set
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the centroGestor
     */
    public String getCentroGestor() {
        return centroGestor;
    }

    /**
     * @param centroGestor the centroGestor to set
     */
    public void setCentroGestor(String centroGestor) {
        this.centroGestor = centroGestor;
    }

    /**
     * @return the encabezado
     */
    public String getEncabezado() {
        return encabezado;
    }

    /**
     * @param encabezado the encabezado to set
     */
    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    /**
     * @return the encabezadoPadre
     */
    public ArchivoSipariNomina getEncabezadoPadre() {
        return encabezadoPadre;
    }

    /**
     * @param encabezadoPadre the encabezadoPadre to set
     */
    public void setEncabezadoPadre(ArchivoSipariNomina encabezadoPadre) {
        this.encabezadoPadre = encabezadoPadre;
    }

    /**
     * @return the certificacionPresupuestaria
     */
    public String getCertificacionPresupuestaria() {
        return certificacionPresupuestaria;
    }

    /**
     * @param certificacionPresupuestaria the certificacionPresupuestaria to set
     */
    public void setCertificacionPresupuestaria(final String certificacionPresupuestaria) {
        this.certificacionPresupuestaria = certificacionPresupuestaria;
    }

    /**
     * @return the programa
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * @param programa the programa to set
     */
    public void setPrograma(final String programa) {
        this.programa = programa;
    }

    /**
     * @return the sociedad
     */
    public String getSociedad() {
        return sociedad;
    }

    /**
     * @param sociedad the sociedad to set
     */
    public void setSociedad(final String sociedad) {
        this.sociedad = sociedad;
    }
}
