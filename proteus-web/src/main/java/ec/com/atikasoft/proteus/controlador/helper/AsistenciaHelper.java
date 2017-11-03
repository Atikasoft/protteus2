/*
 *  AsistenciaHelper.java
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
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * VacacionSolicitud
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "asistenciaHelper")
@SessionScoped
public class AsistenciaHelper extends CatalogoHelper {

    /**
     * clase Asistencia puesto para editar.
     */
    private Asistencia asistencia;

    /**
     * clase Asistencia puesto para editar.
     */
    private Asistencia AsistenciaEditar;

    /**
     * Asistencia lista de planificacionHorarios.
     */
    private List<Asistencia> listaAsistencia;

    /**
     * Variables para servidores.
     */
    private List<Servidor> listaServidores;

    /**
     * filtro nombreServidor.
     */
    private String numeroIdentificacion;

    /**
     * filtro nombreServidor.
     */
    private String nombreServidor;
    /**
     * Variable de servidor.
     */
    private Servidor servidor;
    /**
     * Variable para restringir el inicio del calendario para la fecha de
     * inicio.
     */
    private Date ayer;
    /**
     * Variables para los horarios de asistencia.
     */
    private Date hEntrada;
    private Date hInicioAlmuerzo;
    private Date hFinAlmuerzo;
    private Date hSalida;

    /**
     * Variable que indica si la fecha procesada ya fue enviada a nomina
     */
    private Boolean estaEnNomina;
    private Boolean estaAtrasoJustificado;
    /**
     * Rango de fechas para busquedas.
     */
    private Date fechaDesde;
    private Date fechaHasta;

    /**
     * Constructor por defecto.
     */
    public AsistenciaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del VacacionSolicitudHelper.
     */
    public final void iniciador() {
        setAyer(UtilFechas.sumarDias(new Date(), -2));
        setAsistenciaEditar(new Asistencia());
        setListaAsistencia(new ArrayList<Asistencia>());
        setListaServidores(new ArrayList<Servidor>());
        asistencia = new Asistencia();
        listaAsistencia = new ArrayList<Asistencia>();
        setServidor(new Servidor());
        estaEnNomina = Boolean.FALSE;
        fechaDesde = new Date();
        fechaHasta = new Date();
        estaAtrasoJustificado = Boolean.FALSE;
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
     * @return the AsistenciaEditar
     */
    public Asistencia getAsistenciaEditar() {
        return AsistenciaEditar;
    }

    /**
     * @param AsistenciaEditar the AsistenciaEditar to set
     */
    public void setAsistenciaEditar(Asistencia AsistenciaEditar) {
        this.AsistenciaEditar = AsistenciaEditar;
    }

    /**
     * @return the listaAsistencia
     */
    public List<Asistencia> getListaAsistencia() {
        return listaAsistencia;
    }

    /**
     * @param listaAsistencia the listaAsistencia to set
     */
    public void setListaAsistencia(List<Asistencia> listaAsistencia) {
        this.listaAsistencia = listaAsistencia;
    }

    /**
     * @return the listaServidores
     */
    public List<Servidor> getListaServidores() {
        return listaServidores;
    }

    /**
     * @param listaServidores the listaServidores to set
     */
    public void setListaServidores(List<Servidor> listaServidores) {
        this.listaServidores = listaServidores;
    }

    /**
     * @return the numeroIdentificacion
     */
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * @param numeroIdentificacion the numeroIdentificacion to set
     */
    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    /**
     * @return the nombreServidor
     */
    public String getNombreServidor() {
        return nombreServidor;
    }

    /**
     * @param nombreServidor the nombreServidor to set
     */
    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
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
     * @return the estaEnNomina
     */
    public Boolean getEstaEnNomina() {
        return estaEnNomina;
    }

    /**
     * @param estaEnNomina the estaEnNomina to set
     */
    public void setEstaEnNomina(Boolean estaEnNomina) {
        this.estaEnNomina = estaEnNomina;
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
     * @return the hEntrada
     */
    public Date gethEntrada() {
        return hEntrada;
    }

    /**
     * @param hEntrada the hEntrada to set
     */
    public void sethEntrada(Date hEntrada) {
        this.hEntrada = hEntrada;
    }

    /**
     * @return the hInicioAlmuerzo
     */
    public Date gethInicioAlmuerzo() {
        return hInicioAlmuerzo;
    }

    /**
     * @param hInicioAlmuerzo the hInicioAlmuerzo to set
     */
    public void sethInicioAlmuerzo(Date hInicioAlmuerzo) {
        this.hInicioAlmuerzo = hInicioAlmuerzo;
    }

    /**
     * @return the hFinAlmuerzo
     */
    public Date gethFinAlmuerzo() {
        return hFinAlmuerzo;
    }

    /**
     * @param hFinAlmuerzo the hFinAlmuerzo to set
     */
    public void sethFinAlmuerzo(Date hFinAlmuerzo) {
        this.hFinAlmuerzo = hFinAlmuerzo;
    }

    /**
     * @return the hSalida
     */
    public Date gethSalida() {
        return hSalida;
    }

    /**
     * @param hSalida the hSalida to set
     */
    public void sethSalida(Date hSalida) {
        this.hSalida = hSalida;
    }

    /**
     * @return the estaAtrasoJustificado
     */
    public Boolean getEstaAtrasoJustificado() {
        return estaAtrasoJustificado;
    }

    /**
     * @param estaAtrasoJustificado the estaAtrasoJustificado to set
     */
    public void setEstaAtrasoJustificado(Boolean estaAtrasoJustificado) {
        this.estaAtrasoJustificado = estaAtrasoJustificado;
    }
}
