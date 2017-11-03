/*
 *  DetalleMovimientoVO.java
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
 *  04/12/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.CambioAdministrativo;
import ec.com.atikasoft.proteus.modelo.Cesacion;
import ec.com.atikasoft.proteus.modelo.ComisionServicio;
import ec.com.atikasoft.proteus.modelo.Encargo;
import ec.com.atikasoft.proteus.modelo.Finalizacion;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.RegimenDisciplinario;
import ec.com.atikasoft.proteus.modelo.Subrogacion;
import ec.com.atikasoft.proteus.modelo.TrasladoAdministrativo;
import ec.com.atikasoft.proteus.modelo.Traspaso;

/**
 * Detalle del Movimiento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class DetalleMovimientoVO {

    /**
     * Tipo Ingreso.
     */
    private Ingreso ingreso;

    /**
     * Tipo Cesacion.
     */
    private Cesacion cesacion;

    /**
     * Tipo Regimen Disciplinario.
     */
    private RegimenDisciplinario regimenDisciplinario;

    /**
     * Tipo de licencia.
     */
    private Licencia licencia;

    /**
     * Tipo de comision de servicio.
     */
    private ComisionServicio comisionServicio;

    /**
     * Tipo de cambio administrativos.
     */
    private CambioAdministrativo cambioAdministrativo;

    /**
     * Tipo de traslado administrativo.
     */
    private TrasladoAdministrativo trasladoAdministrativo;

    /**
     * Tipo de traspaso
     */
    private Traspaso traspaso;

    /**
     * Tipo de subrogacion.
     */
    private Subrogacion subrogacion;

    /**
     * Tipo de encargo.
     */
    private Encargo encargo;

    /**
     * Tipo de finalizacion.
     */
    private Finalizacion finalizacion;

    /**
     * @return the ingreso
     */
    public Ingreso getIngreso() {
        return ingreso;
    }

    /**
     * @param ingreso the ingreso to set
     */
    public void setIngreso(final Ingreso ingreso) {
        this.ingreso = ingreso;
    }

    /**
     * @return the cesacion
     */
    public Cesacion getCesacion() {
        return cesacion;
    }

    /**
     * @param cesacion the cesacion to set
     */
    public void setCesacion(final Cesacion cesacion) {
        this.cesacion = cesacion;
    }

    /**
     * @return the regimenDisciplinario
     */
    public RegimenDisciplinario getRegimenDisciplinario() {
        return regimenDisciplinario;
    }

    /**
     * @param regimenDisciplinario the regimenDisciplinario to set
     */
    public void setRegimenDisciplinario(final RegimenDisciplinario regimenDisciplinario) {
        this.regimenDisciplinario = regimenDisciplinario;
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
     * @return the comisionServicio
     */
    public ComisionServicio getComisionServicio() {
        return comisionServicio;
    }

    /**
     * @param comisionServicio the comisionServicio to set
     */
    public void setComisionServicio(final ComisionServicio comisionServicio) {
        this.comisionServicio = comisionServicio;
    }

    /**
     * @return the cambioAdministrativo
     */
    public CambioAdministrativo getCambioAdministrativo() {
        return cambioAdministrativo;
    }

    /**
     * @param cambioAdministrativo the cambioAdministrativo to set
     */
    public void setCambioAdministrativo(final CambioAdministrativo cambioAdministrativo) {
        this.cambioAdministrativo = cambioAdministrativo;
    }

    /**
     * @return the trasladoAdministrativo
     */
    public TrasladoAdministrativo getTrasladoAdministrativo() {
        return trasladoAdministrativo;
    }

    /**
     * @param trasladoAdministrativo the trasladoAdministrativo to set
     */
    public void setTrasladoAdministrativo(final TrasladoAdministrativo trasladoAdministrativo) {
        this.trasladoAdministrativo = trasladoAdministrativo;
    }

    /**
     * @return the traspaso
     */
    public Traspaso getTraspaso() {
        return traspaso;
    }

    /**
     * @param traspaso the traspaso to set
     */
    public void setTraspaso(final Traspaso traspaso) {
        this.traspaso = traspaso;
    }

    /**
     * @return the subrogacion
     */
    public Subrogacion getSubrogacion() {
        return subrogacion;
    }

    /**
     * @param subrogacion the subrogacion to set
     */
    public void setSubrogacion(final Subrogacion subrogacion) {
        this.subrogacion = subrogacion;
    }

    /**
     * @return the encargo
     */
    public Encargo getEncargo() {
        return encargo;
    }

    /**
     * @param encargo the encargo to set
     */
    public void setEncargo(final Encargo encargo) {
        this.encargo = encargo;
    }

    /**
     * @return the finalizacion
     */
    public Finalizacion getFinalizacion() {
        return finalizacion;
    }

    /**
     * @param finalizacion the finalizacion to set
     */
    public void setFinalizacion(final Finalizacion finalizacion) {
        this.finalizacion = finalizacion;
    }
}
