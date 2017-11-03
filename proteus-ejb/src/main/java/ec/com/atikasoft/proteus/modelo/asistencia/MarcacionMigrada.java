/*
 *  Anticipo.java
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo.asistencia;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Marcacion Migrada
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Entity
@Table(name = "marcaciones_migradas", catalog = "sch_proteus")
@Setter
@Getter
public class MarcacionMigrada extends EntidadBasica {

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    /**
     * Codigo de empleado
     */
    @Column(name = "codigo_empleado")
    private String codigoEmpleado;
    
    /**
     * Cedula de empleado
     */
    @Column(name = "cedula")
    private String cedula;
    
    /**
     * Nombre de empleado
     */
    @Column(name = "nombre")
    private String nombre;
    
    /**
     * Nombre del dia
     */
    @Column(name = "dia")
    private String dia;

    /**
     * Fecha de la marcada
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha")
    private Date fecha;
    
    
    /**
     * Dispositivo marcada 1
     */
    @Column(name = "e1")
    private String e1;
    
    /**
     * Hora marcada 1
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m1")
    private Date m1;
    
    
    /**
     * Dispositivo marcada 2
     */
    @Column(name = "e2")
    private String e2;
    
    /**
     * Hora marcada 2
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m2")
    private Date m2;
    
    
    /**
     * Dispositivo marcada 3
     */
    @Column(name = "e3")
    private String e3;
    
    /**
     * Hora marcada 3
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m3")
    private Date m3;
    
    
    /**
     * Dispositivo marcada 4
     */
    @Column(name = "e4")
    private String e4;
    
    /**
     * Hora marcada 4
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m4")
    private Date m4;
    
    
    /**
     * Dispositivo marcada 5
     */
    @Column(name = "e5")
    private String e5;
    
    /**
     * Hora marcada 5
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m5")
    private Date m5;
    
    /**
     * Dispositivo marcada 6
     */
    @Column(name = "e6")
    private String e6;
    
    /**
     * Hora marcada 6
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m6")
    private Date m6;
    
    /**
     * Dispositivo marcada 7
     */
    @Column(name = "e7")
    private String e7;
    
    /**
     * Hora marcada 7
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m7")
    private Date m7;
    
    
    /**
     * Dispositivo marcada 8
     */
    @Column(name = "e8")
    private String e8;
    
    /**
     * Hora marcada 8
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m8")
    private Date m8;
    
    /**
     * Dispositivo marcada 9
     */
    @Column(name = "e9")
    private String e9;
    
    /**
     * Hora marcada 9
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m9")
    private Date m9;
    
    /**
     * Dispositivo marcada 10
     */
    @Column(name = "e10")
    private String e10;
    
    /**
     * Hora marcada 10
     */
    @Temporal(TemporalType.TIME)
    @Column(name = "m10")
    private Date m10;
    
    
    @ManyToOne()
    @JoinColumn(name = "proceso_registro_migracion_marcaciones_id")
    private ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion;
    
    /**
     * Constructor.
     */
    public MarcacionMigrada() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public MarcacionMigrada(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    
}
