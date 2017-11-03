/*
 *  VacacionSolicitudHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.Atraso;
import ec.com.atikasoft.proteus.modelo.JustificacionAsistencia;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionSolicitud;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * VacacionSolicitud
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "justificacionAsistenciaHelper")
@SessionScoped
public class JustificacionAsistenciaHelper extends CatalogoHelper {

    /**
     * clase Asistencia puesto para editar.
     */
    private Asistencia asistencia;

    /**
     * clase Atraso  .
     */
    private Atraso Atraso;

        /**
     * clase Justificacion atraso .
     */
    private JustificacionAsistencia justificacionAsistencia;

    /**
     * lista de Atraso.
     */
    private List<Atraso> listaAtraso;

    /**
     * Variable de servidor.
     */
    private Servidor servidor;
    /**
     * Variable para restringir el inicio del calendario para la fecha de inicio.
     */
    private Date ayer;
    
    /**
     * Enlista opciones de tipos de justificacion.
     */
    private List<SelectItem> listaOpcionesTipoJustificacion;
    /**
     * Rango de fechas para busquedas.
     */
    private Date fechaDesde;
    private Date fechaHasta;
    /**
     * Determina si tiene o no acceso a esta opcion.
     */
    private boolean accesoAJustificacion ;
        /**
     * Determina si tiene o no acceso a esta opcion.
     */
    private boolean accesoAEdicion ;
    /**
     * Indica el tipo de justificacion a realizar.
     */
    private String tipoJustificacion;
    /**
     * Almacena el saldo de las vacaciones.
     * Si el tipo de justificacion es por vacacion, verifica el saldo.
     */
    private Long saldoVacacion;
    private double saldoDiaVacacion;
        
      /**
     * clase VacacionSolciitud utilizada para la justificacion del ausentismo.
     */
    private VacacionSolicitud vacacionSolicitud;
    /**
     * Constructor por defecto.
     */
    public JustificacionAsistenciaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     */
    public final void iniciador() {
        setAyer(UtilFechas.sumarDias(new Date(), -2));
        setAtraso(new Atraso());
        setListaAtraso(new ArrayList<Atraso>());
        setAsistencia(new Asistencia());
        setListaAtraso(new ArrayList<Atraso>());
        setServidor(new Servidor());
        setFechaDesde(new Date());
        setFechaHasta(new Date());
        setAccesoAJustificacion((boolean) Boolean.FALSE);
        accesoAEdicion = false;
        justificacionAsistencia= new JustificacionAsistencia();
        listaOpcionesTipoJustificacion=new ArrayList<SelectItem>();
        saldoDiaVacacion =0.0;
        vacacionSolicitud = new VacacionSolicitud();
    }

    /**
     * @return the asistencia
     */
    public Asistencia getAsistencia() {
        return asistencia;
    }

    /**
     * @param asistencia the asistencia to set
     */
    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
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
     * @return the ayer
     */
    public Date getAyer() {
        return ayer;
    }

    /**
     * @param ayer the ayer to set
     */
    public void setAyer(Date ayer) {
        this.ayer = ayer;
    }

    /**
     * @return the fechaDesde
     */
    public Date getFechaDesde() {
        return fechaDesde;
    }

    /**
     * @param fechaDesde the fechaDesde to set
     */
    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    /**
     * @return the fechaHasta
     */
    public Date getFechaHasta() {
        return fechaHasta;
    }

    /**
     * @param fechaHasta the fechaHasta to set
     */
    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    /**
     * @return the accesoAJustificacion
     */
    public boolean isAccesoAJustificacion() {
        return accesoAJustificacion;
    }

    /**
     * @param accesoAJustificacion the accesoAJustificacion to set
     */
    public void setAccesoAJustificacion(boolean accesoAJustificacion) {
        this.accesoAJustificacion = accesoAJustificacion;
    }

    /**
     * @return the Atraso
     */
    public Atraso getAtraso() {
        return Atraso;
    }

    /**
     * @param Atraso the Atraso to set
     */
    public void setAtraso(Atraso Atraso) {
        this.Atraso = Atraso;
    }

    /**
     * @return the listaAtraso
     */
    public List<Atraso> getListaAtraso() {
        return listaAtraso;
    }

    /**
     * @param listaAtraso the listaAtraso to set
     */
    public void setListaAtraso(List<Atraso> listaAtraso) {
        this.listaAtraso = listaAtraso;
    }

    /**
     * @return the justificacionAsistencia
     */
    public JustificacionAsistencia getJustificacionAsistencia() {
        return justificacionAsistencia;
    }

    /**
     * @param justificacionAsistencia the justificacionAsistencia to set
     */
    public void setJustificacionAsistencia(JustificacionAsistencia justificacionAsistencia) {
        this.justificacionAsistencia = justificacionAsistencia;
    }

    /**
     * @return the listaOpcionesTipoJustificacion
     */
    public List<SelectItem> getListaOpcionesTipoJustificacion() {
        return listaOpcionesTipoJustificacion;
    }

    /**
     * @param listaOpcionesTipoJustificacion the listaOpcionesTipoJustificacion to set
     */
    public void setListaOpcionesTipoJustificacion(List<SelectItem> listaOpcionesTipoJustificacion) {
        this.listaOpcionesTipoJustificacion = listaOpcionesTipoJustificacion;
    }

    /**
     * @return the tipoJustificacion
     */
    public String getTipoJustificacion() {
        return tipoJustificacion;
    }

    /**
     * @param tipoJustificacion the tipoJustificacion to set
     */
    public void setTipoJustificacion(String tipoJustificacion) {
        this.tipoJustificacion = tipoJustificacion;
    }

    /**
     * @return the saldoVacacion
     */
    public Long getSaldoVacacion() {
        return saldoVacacion;
    }

    /**
     * @param saldoVacacion the saldoVacacion to set
     */
    public void setSaldoVacacion(Long saldoVacacion) {
        this.saldoVacacion = saldoVacacion;
    }

    /**
     * @return the saldoDiaVacacion
     */
    public double getSaldoDiaVacacion() {
        return saldoDiaVacacion;
    }

    /**
     * @param saldoDiaVacacion the saldoDiaVacacion to set
     */
    public void setSaldoDiaVacacion(double saldoDiaVacacion) {
        this.saldoDiaVacacion = saldoDiaVacacion;
    }

    /**
     * @return the vacacionSolicitud
     */
    public VacacionSolicitud getVacacionSolicitud() {
        return vacacionSolicitud;
    }

    /**
     * @param vacacionSolicitud the vacacionSolicitud to set
     */
    public void setVacacionSolicitud(VacacionSolicitud vacacionSolicitud) {
        this.vacacionSolicitud = vacacionSolicitud;
    }

    /**
     * @return the accesoAEdicion
     */
    public boolean isAccesoAEdicion() {
        return accesoAEdicion;
    }

    /**
     * @param accesoAEdicion the accesoAEdicion to set
     */
    public void setAccesoAEdicion(boolean accesoAEdicion) {
        this.accesoAEdicion = accesoAEdicion;
    }

}
