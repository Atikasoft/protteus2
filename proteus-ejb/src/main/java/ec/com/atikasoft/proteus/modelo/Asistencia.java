/*
 *  Asistencia.java
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
 *  26/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.modelo;

import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.sql.Time;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@Entity
@Table(name = "asistencias", catalog = "sch_proteus")
@NamedQueries({
    @NamedQuery(name = Asistencia.BUSCAR_VIGENTES,
            query = "SELECT a FROM Asistencia a where a.vigente=true order by a.fecha desc"),
    @NamedQuery(name = Asistencia.BUSCAR_POR_SERVIDOR,
            query = "SELECT a FROM Asistencia a where a.vigente=true and a.codigoEmpleado = ?1 order by a.fecha desc"),
    @NamedQuery(name = Asistencia.BUSCAR_POR_FECHA,
            query
            = "SELECT a FROM Asistencia a where a.vigente=true and a.fecha = :fecha order by a.servidor.apellidosNombres desc"),
    @NamedQuery(name = Asistencia.BUSCAR_ULTIMA_FECHA_PROCESADA,
            query = "SELECT a FROM Asistencia a where a.vigente=true and a.fecha in ("
            + " select max(b.fecha) from Asistencia b where b.vigente=true)")
})
public class Asistencia extends EntidadBasica {

    /**
     * Nombre para busqueda de vigentes.
     */
    public static final String BUSCAR_VIGENTES = "Asistencia.buscarVigente";

    /**
     * Nombre para busqueda de vigentes de un servidor específico.
     */
    public static final String BUSCAR_POR_SERVIDOR = "Asistencia.buscarPorServidor";

    /**
     * Nombre para busqueda de vigentes de una fecha especifica.
     */
    public static final String BUSCAR_POR_FECHA = "Asistencia.buscarPorFecha";
    /**
     * Nombre para busqueda la ultima fecha procesada.
     */
    public static final String BUSCAR_ULTIMA_FECHA_PROCESADA = "Asistencia.buscarUltimaFechaProcesada";

    /**
     * Id de Clase.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Campo identificador del codigo del servidor.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_empleado")
    private Long codigoEmpleado;

    /**
     * Especifica la fecha.
     */
    @Column(name = "fecha")
    @Temporal(value = TemporalType.DATE)
    @NotNull(message = "La fecha es campo requerido")
    private Date fecha;

    /**
     * Referencia a servidor.
     */
    @JoinColumn(name = "servidor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Servidor servidor;

    @Size(max = 20)
    @Column(name = "timbre1")
    private String timbre1;

    @Size(max = 20)
    @Column(name = "timbre2")
    private String timbre2;

    @Size(max = 20)
    @Column(name = "timbre3")
    private String timbre3;

    @Size(max = 20)
    @Column(name = "timbre4")
    private String timbre4;

    @Size(max = 20)
    @Column(name = "timbre5")
    private String timbre5;

    @Size(max = 20)
    @Column(name = "timbre6")
    private String timbre6;

    @Size(max = 20)
    @Column(name = "timbre7")
    private String timbre7;

    @Size(max = 20)
    @Column(name = "timbre8")
    private String timbre8;

    @Size(max = 20)
    @Column(name = "timbre9")
    private String timbre9;

    @Size(max = 20)
    @Column(name = "timbre10")
    private String timbre10;

    /**
     * Permite registrar una observacion cuando modifica el registro.
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * Constructor.
     */
    public Asistencia() {
        super();
    }

    /**
     * Constructor.
     *
     * @param id Long
     */
    public Asistencia(final Long id) {
        super();
        this.id = id;
    }

    @Override
    public String toString() {
//        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return this.getFecha()+"/"+this.getServidor().getNumeroIdentificacion()+" id:"+this.servidor.getId()+"/"+this.timbre1+"/"+this.getTimbre2()+
                "/"+this.timbre3+"/"+this.timbre4;
    }

    /**
     * Permite obtener el campo en forma de hora
     *
     * @param timbre
     * @return
     */
    public Date getHoraTimbre(String timbre) {
        if (timbre != null && !timbre.isEmpty()) {
            Date t;
            StringBuilder timbreAux = new StringBuilder(timbre);
            int posicion = timbreAux.lastIndexOf(":");
            timbreAux = new StringBuilder(timbreAux.substring(0, posicion + 3).trim());
            t = UtilFechas.convertirFechaStringEnDate(timbreAux.toString(), UtilFechas.FORMATO_HORA);
            return t;
        } else {
            return null;
        }
    }

    public Time getHoraTimbres(String timbre) {
        Time t;
        if (timbre != null && !timbre.isEmpty()) {
            t = new Time(getHoraTimbre(timbre).getTime());
        } else {
            return null;
        }
        return t;
    }

    /**
     * Almacena los timbres en un vector.
     *
     * @param a
     * @return
     */
    public Time[] getTimbres(Asistencia a) {
        Time[] tiempo = new Time[10];
        tiempo[0] = a.getHoraTimbres(a.getTimbre1());
        tiempo[1] = a.getHoraTimbres(a.getTimbre2());
        tiempo[2] = a.getHoraTimbres(a.getTimbre3());
        tiempo[3] = a.getHoraTimbres(a.getTimbre4());
        tiempo[4] = a.getHoraTimbres(a.getTimbre5());
        tiempo[5] = a.getHoraTimbres(a.getTimbre6());
        tiempo[6] = a.getHoraTimbres(a.getTimbre7());
        tiempo[7] = a.getHoraTimbres(a.getTimbre8());
        tiempo[8] = a.getHoraTimbres(a.getTimbre9());
        tiempo[9] = a.getHoraTimbres(a.getTimbre10());
        return tiempo;
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
     * @return the codigoEmpleado
     */
    public Long getCodigoEmpleado() {
        return codigoEmpleado;
    }

    /**
     * @param codigoEmpleado the codigoEmpleado to set
     */
    public void setCodigoEmpleado(Long codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the timbre1
     */
    public String getTimbre1() {
        return timbre1;
    }

    /**
     * @param timbre1 the timbre1 to set
     */
    public void setTimbre1(String timbre1) {
        this.timbre1 = timbre1;
    }

    /**
     * @return the timbre2
     */
    public String getTimbre2() {
        return timbre2;
    }

    /**
     * @param timbre2 the timbre2 to set
     */
    public void setTimbre2(String timbre2) {
        this.timbre2 = timbre2;
    }

    /**
     * @return the timbre3
     */
    public String getTimbre3() {
        return timbre3;
    }

    /**
     * @param timbre3 the timbre3 to set
     */
    public void setTimbre3(String timbre3) {
        this.timbre3 = timbre3;
    }

    /**
     * @return the timbre4
     */
    public String getTimbre4() {
        return timbre4;
    }

    /**
     * @param timbre4 the timbre4 to set
     */
    public void setTimbre4(String timbre4) {
        this.timbre4 = timbre4;
    }

    /**
     * @return the timbre5
     */
    public String getTimbre5() {
        return timbre5;
    }

    /**
     * @param timbre5 the timbre5 to set
     */
    public void setTimbre5(String timbre5) {
        this.timbre5 = timbre5;
    }

    /**
     * @return the timbre6
     */
    public String getTimbre6() {
        return timbre6;
    }

    /**
     * @param timbre6 the timbre6 to set
     */
    public void setTimbre6(String timbre6) {
        this.timbre6 = timbre6;
    }

    /**
     * @return the timbre7
     */
    public String getTimbre7() {
        return timbre7;
    }

    /**
     * @param timbre7 the timbre7 to set
     */
    public void setTimbre7(String timbre7) {
        this.timbre7 = timbre7;
    }

    /**
     * @return the timbre8
     */
    public String getTimbre8() {
        return timbre8;
    }

    /**
     * @param timbre8 the timbre8 to set
     */
    public void setTimbre8(String timbre8) {
        this.timbre8 = timbre8;
    }

    /**
     * @return the timbre9
     */
    public String getTimbre9() {
        return timbre9;
    }

    /**
     * @param timbre9 the timbre9 to set
     */
    public void setTimbre9(String timbre9) {
        this.timbre9 = timbre9;
    }

    /**
     * @return the timbre10
     */
    public String getTimbre10() {
        return timbre10;
    }

    /**
     * @param timbre10 the timbre10 to set
     */
    public void setTimbre10(String timbre10) {
        this.timbre10 = timbre10;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
