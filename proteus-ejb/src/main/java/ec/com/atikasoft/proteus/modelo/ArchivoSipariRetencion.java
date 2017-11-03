/*
 *  ArchivoSipariRetencion.java
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
 *  03/02/2014
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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "archivos_sipari_retenciones", catalog = "sch_proteus")

@NamedQueries({
    @NamedQuery(name = ArchivoSipariRetencion.BUSCAR_POR_PADRE,
            query = "SELECT a FROM ArchivoSipariRetencion a where a.archivoSipari.id= ?1 and a.vigente=true")
    
})

public class ArchivoSipariRetencion extends EntidadBasica {

    /**
     * Variable para busqeda por padre.
     */
    public static final String BUSCAR_POR_PADRE = "ArchivoSipariRetencion.buscarporPadre";
   
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
     * Código del empleado: Será identificado con el número de cédula de
     * ciudadanía.
     */
    //@NotNull
    //@Size(max = 20)
    @Column(name = "codigo_empleado")
    private String codigoEmpleado;
    
    /**
     * Número de identificacion del beneficiario.
     */
      //@Size(max = 20)
    @Column(name = "codigo_beneficiario")
    private String codigoBeneficiario;

    /**
     * Sociedad: Corresponde a la administración Central y los Entes Contables.
     */
    //@NotNull
    //@Size(max = 4)
    @Column(name = "sociedad")
    private String sociedad;

    /**
     * Nombres: Se divide en 2 campos de 35 caracteres cada uno. Donde se
     * especificara los dos nombres y los dos apellidos.
     */
    //@NotNull
    //@Size(max = 70)
    @Column(name = "nombre")
    private String nombre;

    //@NotNull
    //@Size(max = 70)
    @Column(name = "apellido")
    private String apellido;
     /**
     * Campo Concepto Nómina: Debe estar codificado en Recursos Humanos, es el
     * codigo del rubro.
     */
    //@NotNull
    @Column(name = "concepto_nomina")
    private String conceptoNomina;
    
      /**
     * Campo Importe Moneda Local:Valor de la contabilización de nómina del
     * empleado, en USD.
     */
    //@NotNull
    //@Size(max=13,2)
    @Column(name = "importe")
    private BigDecimal importe;
    /**
     * Tipo de identificación: El tratamiento será como persona natural.
     */
    //@NotNull
    //@Size(max = 2)
    @Column(name = "pais_banco")
    private String paisBanco;

    /**
     * Datos de banco. Se indica los datos de la cuenta del banco del empleado:
     * País Clave Banco Cuenta bancaria Tipo de cuenta de banco: Ahorros o
     * corriente
     */
    //@NotNull
    //@Size(max = 4)
    @Column(name = "clave_banco")
    private String claveBanco;

    //@NotNull
    //@Size(max = 18)
    @Column(name = "cuenta_banco")
    private String cuentaBanco;

    //@NotNull
    //@Size(max = 1)
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;
    
     /**
     * Campo codigo.
     */
    //@NotNull
    @Column(name = "encabezado")
    private String encabezado;

   
    
    /**
     * Constructor.
     */
    public ArchivoSipariRetencion() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ArchivoSipariRetencion(final Long id) {
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
     * @return the codigoBeneficiario
     */
    public String getCodigoBeneficiario() {
        return codigoBeneficiario;
    }

    /**
     * @param codigoBeneficiario the codigoBeneficiario to set
     */
    public void setCodigoBeneficiario(String codigoBeneficiario) {
        this.codigoBeneficiario = codigoBeneficiario;
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
    public void setSociedad(String sociedad) {
        this.sociedad = sociedad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
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
     * @return the paisBanco
     */
    public String getPaisBanco() {
        return paisBanco;
    }

    /**
     * @param paisBanco the paisBanco to set
     */
    public void setPaisBanco(String paisBanco) {
        this.paisBanco = paisBanco;
    }

    /**
     * @return the claveBanco
     */
    public String getClaveBanco() {
        return claveBanco;
    }

    /**
     * @param claveBanco the claveBanco to set
     */
    public void setClaveBanco(String claveBanco) {
        this.claveBanco = claveBanco;
    }

    /**
     * @return the cuentaBanco
     */
    public String getCuentaBanco() {
        return cuentaBanco;
    }

    /**
     * @param cuentaBanco the cuentaBanco to set
     */
    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    /**
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }

    /**
     * @param tipoCuenta the tipoCuenta to set
     */
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
}
