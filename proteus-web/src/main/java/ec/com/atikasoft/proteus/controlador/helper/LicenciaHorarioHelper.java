/*
 *  LicenciaHorarioHelper.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  22/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.enums.DiasEnum;
import ec.com.atikasoft.proteus.enums.TipoLicenciaHorarioEnum;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.LicenciaHorario;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.vo.HorarioVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 * Helper de Licencia Horario Controlador.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "licenciaHorarioHelper")
@SessionScoped
public class LicenciaHorarioHelper {

    /**
     * Valor del dia.
     */
    private Integer dia;

    /**
     * Lista de tipo horario.
     */
    private List<SelectItem> tipoHorario = new ArrayList<SelectItem>();

    /**
     * Mensaje de eliminacion.
     */
    private String mensajeEliminacion = "";

    /**
     * Tipo de movimiento seleccionado.
     */
    private TipoMovimiento tipoMovimiento = new TipoMovimiento();

    /**
     * Movimiento del horario.
     */
    private Movimiento movimiento;

    /**
     * Objeto padre.
     */
    private Licencia licencia = new Licencia();

    /**
     * Key de tipo pantalla.
     */
    private String tipoPantalla;

    /**
     * Objeto hijo granularidad.
     */
    private LicenciaHorario licenciaHorario = new LicenciaHorario();

    /**
     * Este objeto contiene los horario de la semana.
     */
    private HashMap<Integer, HorarioVO> horarios = new HashMap<Integer, HorarioVO>(7);

    /**
     * Lista por dia.
     */
    private List<LicenciaHorario> listaLicenciaHorario = new ArrayList<LicenciaHorario>();

    /**
     * Total horas dia.
     */
    private Long totalHorasSemana = 0L;

    /**
     * Total horas dia.
     */
    private Long totalHorasSemanaRecuperacion = 0L;

    /**
     * Constructor por defecto.
     */
    public LicenciaHorarioHelper() {
        super();
        iniciador();
        tipoHorario.clear();
        for (TipoLicenciaHorarioEnum l : TipoLicenciaHorarioEnum.values()) {
            tipoHorario.add(new SelectItem(l.getCodigo(), l.getNombre()));
        }
    }

    /**
     * Este metodo limpia las listas de dicas y totales.
     */
    public final void iniciador() {
        horarios.clear();
        for (DiasEnum d : DiasEnum.values()) {
            horarios.put(d.getNumero(), new HorarioVO());
        }
        listaLicenciaHorario.clear();
        totalHorasSemana = 0L;
        totalHorasSemanaRecuperacion = 0L;
    }

    /**
     * @return the licencia
     */
    public Licencia getLicencia() {
        return licencia;
    }

    /**
     * @param licencia the licencia to set
     */
    public void setLicencia(final Licencia licencia) {
        this.licencia = licencia;
    }

    /**
     * @return the licenciaHorario
     */
    public LicenciaHorario getLicenciaHorario() {
        return licenciaHorario;
    }

    /**
     * @param licenciaHorario the licenciaHorario to set
     */
    public void setLicenciaHorario(final LicenciaHorario licenciaHorario) {
        this.licenciaHorario = licenciaHorario;
    }

    /**
     * @return the totalHorasSemana
     */
    public Long getTotalHorasSemana() {
        return totalHorasSemana;
    }

    /**
     * @param totalHorasSemana the totalHorasSemana to set
     */
    public void setTotalHorasSemana(final Long totalHorasSemana) {
        this.totalHorasSemana = totalHorasSemana;
    }

    /**
     * @return the dia
     */
    public Integer getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(final Integer dia) {
        this.dia = dia;
    }

    /**
     * @return the tipoMovimiento
     */
    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(final TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the movimiento
     */
    public Movimiento getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(final Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    /**
     * @return the mensajeEliminacion
     */
    public String getMensajeEliminacion() {
        return mensajeEliminacion;
    }

    /**
     * @param mensajeEliminacion the mensajeEliminacion to set
     */
    public void setMensajeEliminacion(final String mensajeEliminacion) {
        this.mensajeEliminacion = mensajeEliminacion;
    }

    /**
     * @return the tipoPantalla
     */
    public String getTipoPantalla() {
        return tipoPantalla;
    }

    /**
     * @param tipoPantalla the tipoPantalla to set
     */
    public void setTipoPantalla(final String tipoPantalla) {
        this.tipoPantalla = tipoPantalla;
    }

    /**
     * @return the listaLicenciaHorario
     */
    public List<LicenciaHorario> getListaLicenciaHorario() {
        return listaLicenciaHorario;
    }

    /**
     * @param listaLicenciaHorario the listaLicenciaHorario to set
     */
    public void setListaLicenciaHorario(final List<LicenciaHorario> listaLicenciaHorario) {
        this.listaLicenciaHorario = listaLicenciaHorario;
    }

    /**
     * @return the tipoHorario
     */
    public List<SelectItem> getTipoHorario() {
        return tipoHorario;
    }

    /**
     * @param tipoHorario the tipoHorario to set
     */
    public void setTipoHorario(final List<SelectItem> tipoHorario) {
        this.tipoHorario = tipoHorario;
    }

    /**
     * @return the horarios
     */
    public HashMap<Integer, HorarioVO> getHorarios() {
        return horarios;
    }

    /**
     * @param horarios the horarios to set
     */
    public void setHorarios(final HashMap<Integer, HorarioVO> horarios) {
        this.horarios = horarios;
    }

    /**
     * @return the totalHorasSemanaRecuperacion
     */
    public Long getTotalHorasSemanaRecuperacion() {
        return totalHorasSemanaRecuperacion;
    }

    /**
     * @param totalHorasSemanaRecuperacion the totalHorasSemanaRecuperacion to set
     */
    public void setTotalHorasSemanaRecuperacion(final Long totalHorasSemanaRecuperacion) {
        this.totalHorasSemanaRecuperacion = totalHorasSemanaRecuperacion;
    }
}
