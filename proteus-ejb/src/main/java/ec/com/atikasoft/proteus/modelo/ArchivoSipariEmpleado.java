/*
 *  ArchivoSipariEmpleado.java
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
@Table(name = "archivos_sipari_empleados", catalog = "sch_proteus")

@NamedQueries({
    @NamedQuery(name = ArchivoSipariEmpleado.BUSCAR_POR_PADRE,
            query = "SELECT a FROM ArchivoSipariEmpleado a where a.archivoSipari.id= ?1 and a.vigente=true"),
     @NamedQuery(name = ArchivoSipariEmpleado.BUSCAR_ENCABEZADOS_POR_PADRE,
            query = "SELECT a FROM ArchivoSipariEmpleado a where a.archivoSipari.id= ?1 "
                    + " and a.encabezado is not null and a.vigente=true")
})

public class ArchivoSipariEmpleado extends EntidadBasica {

    /**
     * Variable para busqeda por padre.
     */
    public static final String BUSCAR_POR_PADRE = "ArchivoSipariEmpleado.buscarporPadre";
      /**
     * Variable para busqeda por padre.
     */
    public static final String BUSCAR_ENCABEZADOS_POR_PADRE = "ArchivoSipariEmpleado.buscarporEncabezadosPorPadre";
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
     * Campo estado del Empleado: Estado del empleado (Activo, Comisión de
     * Servicio con sueldo, Comisión de servicio sin sueldo, Licencia por
     * maternidad, Licencia sin sueldo, Suspensión sin sueldo…); Se encuentra
     * codificadoEstado del empleado ( Activo, Comisión de Servicio con sueldo,
     * Comisión de servicio sin sueldo, Licencia por maternidad, Licencia sin
     * sueldo, Suspensión sin sueldo…); Se encuentra codificado.
     */
    //@NotNull
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;

    /**
     * Código del empleado: Será identificado con el número de cédula de
     * ciudadanía.
     */
    //@NotNull
    @Size(max = 20)
    @Column(name = "empleado")
    private String empleado;

    /**
     * Sociedad: Corresponde a la administración Central y los Entes Contables.
     */
    //@NotNull
    @Size(max = 4)
    @Column(name = "sociedad")
    private String sociedad;

    /**
     * Nombres: Se divide en 2 campos de 35 caracteres cada uno. Donde se
     * especificara los dos nombres y los dos apellidos.
     */
    //@NotNull
    @Size(max = 35)
    @Column(name = "nombre1")
    private String nombre1;

    //@NotNull
    @Size(max = 35)
    @Column(name = "apellido1")
    private String apellido1;

    /**
     * Búsqueda: Campo para facilitar las búsquedas. Puede contener las
     * iniciales de la persona, las siglas de la empresa o cualquier otra
     * información que se considere de interés.
     */
    @Size(max = 10)
    @Column(name = "campo_busqueda")
    private String campoBusqueda;

    /**
     * Dirección del Empleado: Se introduce el nombre de la calle o avenida y
     * resto de datos.
     */
    //@NotNull
    @Size(max = 60)
    @Column(name = "direccion")
    private String direccion;
    /**
     * País; Codificado:El código asignado será el valor fijo EC.
     */
    //@NotNull
    @Size(max = 3)
    @Column(name = "pais")
    private String pais;

    /**
     * Región: Será a través de los dos primeros dígitos de su cédula que
     * identifica que provincia pertenece.
     */
    //@NotNull
    @Size(max = 2)
    @Column(name = "region")
    private String region;

    /**
     * Teléfono del empleado: Puedes ser teléfono fijo o celular. En caso de
     * disponer de ambos se da preferencia al fijo.
     */
    @Size(max = 16)
    @Column(name = "telefono")
    private String telefono;
    /**
     * Correo electrónico: Dirección de correo electrónico del empleado.
     */
    @Size(max = 30)
    @Column(name = "correo_electronico")
    private String correoElectronico;

    /**
     * Identificación fiscal: Se especificara el número de cedula del empleado.
     */
    //@NotNull
    @Size(max = 10)
    @Column(name = "identificacion_fiscal")
    private String identificacionFiscal;

    /**
     * Tipo de identificación: El tratamiento será como persona natural.
     */
    //@NotNull
    @Size(max = 3)
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    /**
     * Tipo de identificación: El tratamiento será como persona natural.
     */
    //@NotNull
    @Size(max = 2)
    @Column(name = "pais_banco")
    private String paisBanco;

    /**
     * Datos de banco. Se indica los datos de la cuenta del banco del empleado:
     * País Clave Banco Cuenta bancaria Tipo de cuenta de banco: Ahorros o
     * corriente
     */
    //@NotNull
    @Size(max = 4)
    @Column(name = "clave_banco")
    private String claveBanco;

    //@NotNull
    @Size(max = 18)
    @Column(name = "cuenta_banco")
    private String cuentaBanco;

    //@NotNull
    @Size(max = 1)
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;
    
     /**
     * Campo codigo.
     */
    //@NotNull
    @Column(name = "encabezado")
    private String encabezado;

     /**
     * Referencia a ArchivoSipariEmpleado padre.
     */
    @JoinColumn(name = "encabezado_id")
    @ManyToOne(fetch = FetchType.LAZY)
     private ArchivoSipariEmpleado encabezadoPadre;
    
    /**
     * Constructor.
     */
    public ArchivoSipariEmpleado() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public ArchivoSipariEmpleado(final Long id) {
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
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
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
     * @return the empleado
     */
    public String getEmpleado() {
        return empleado;
    }

    /**
     * @param empleado the empleado to set
     */
    public void setEmpleado(String empleado) {
        this.empleado = empleado;
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
     * @return the nombre1
     */
    public String getNombre1() {
        return nombre1;
    }

    /**
     * @param nombre1 the nombre1 to set
     */
    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    /**
     * @return the apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * @param apellido1 the apellido1 to set
     */
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    /**
     * @return the campoBusqueda
     */
    public String getCampoBusqueda() {
        return campoBusqueda;
    }

    /**
     * @param campoBusqueda the campoBusqueda to set
     */
    public void setCampoBusqueda(String campoBusqueda) {
        this.campoBusqueda = campoBusqueda;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correoElectronico
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * @param correoElectronico the correoElectronico to set
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * @return the identificacionFiscal
     */
    public String getIdentificacionFiscal() {
        return identificacionFiscal;
    }

    /**
     * @param identificacionFiscal the identificacionFiscal to set
     */
    public void setIdentificacionFiscal(String identificacionFiscal) {
        this.identificacionFiscal = identificacionFiscal;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
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

    /**
     * @return the encabezadoPadre
     */
    public ArchivoSipariEmpleado getEncabezadoPadre() {
        return encabezadoPadre;
    }

    /**
     * @param encabezadoPadre the encabezadoPadre to set
     */
    public void setEncabezadoPadre(ArchivoSipariEmpleado encabezadoPadre) {
        this.encabezadoPadre = encabezadoPadre;
    }
}
