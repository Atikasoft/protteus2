/*
 *  BusquedaPuestoVO.java
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
 *  22/11/2012
 *
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tercero;
import ec.com.atikasoft.proteus.modelo.TipoNomina;
import java.io.Serializable;
import java.util.List;

/**
 * VO para la Busqueda de Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class EjecucionNominaVO implements Serializable {

    private Boolean todos;

    /**
     *
     */
    private Long intitucionEjercicioFiscalId;

    /**
     *
     */
    private Long regimenLaboralId;

    /**
     * Campo para el filtro.
     */
    private Long nivelOcupacionalId;

    /**
     * Campo para el filtro.
     */
    private Long escalaOcupacionalId;

    /**
     * Campo para el filtro.
     */
    private Long modalidadLaboralId;

    /**
     *
     */
    private Long denominacionPuestoId;

    /**
     * Campo para el filtro.
     */
    private Long unidadAdministrativaId;

    /**
     * Campo para el filtro.
     */
    private List<Long> estadosPuestoId;

    /**
     * Campo para el filtro.
     */
    private List<Long> estadosServidorId;

    /**
     * Campo para el filtro.
     */
    private List<Servidor> servidores;
    /**
     * Campo para el filtro.
     */
    private List<Tercero> terceros;

    /**
     * Lista de liquidaciones.
     */
    private List<Liquidacion> liquidaciones;

    /**
     * Lista de anticipos.
     */
    private List<Anticipo> anticipos;
    /**
     *
     */
    private TipoNomina tipoNomina;

    /**
     *
     */
    private UsuarioVO usuarioVO;

    /**
     * Constructor por defecto.
     */
    public EjecucionNominaVO() {
        super();
    }

    /**
     * @return the todos
     */
    public Boolean getTodos() {
        return todos;
    }

    /**
     * @return the intitucionEjercicioFiscalId
     */
    public Long getIntitucionEjercicioFiscalId() {
        return intitucionEjercicioFiscalId;
    }

    /**
     * @return the regimenLaboralId
     */
    public Long getRegimenLaboralId() {
        return regimenLaboralId;
    }

    /**
     * @return the nivelOcupacionalId
     */
    public Long getNivelOcupacionalId() {
        return nivelOcupacionalId;
    }

    /**
     * @return the escalaOcupacionalId
     */
    public Long getEscalaOcupacionalId() {
        return escalaOcupacionalId;
    }

    /**
     * @return the modalidadLaboralId
     */
    public Long getModalidadLaboralId() {
        return modalidadLaboralId;
    }

    /**
     * @return the denominacionPuestoId
     */
    public Long getDenominacionPuestoId() {
        return denominacionPuestoId;
    }

    /**
     * @return the unidadAdministrativaId
     */
    public Long getUnidadAdministrativaId() {
        return unidadAdministrativaId;
    }

    /**
     * @return the estadosPuestoId
     */
    public List<Long> getEstadosPuestoId() {
        return estadosPuestoId;
    }

    /**
     * @return the estadosServidorId
     */
    public List<Long> getEstadosServidorId() {
        return estadosServidorId;
    }

    /**
     * @param todos the todos to set
     */
    public void setTodos(final Boolean todos) {
        this.todos = todos;
    }

    /**
     * @param intitucionEjercicioFiscalId the intitucionEjercicioFiscalId to set
     */
    public void setIntitucionEjercicioFiscalId(final Long intitucionEjercicioFiscalId) {
        this.intitucionEjercicioFiscalId = intitucionEjercicioFiscalId;
    }

    /**
     * @param regimenLaboralId the regimenLaboralId to set
     */
    public void setRegimenLaboralId(final Long regimenLaboralId) {
        this.regimenLaboralId = regimenLaboralId;
    }

    /**
     * @param nivelOcupacionalId the nivelOcupacionalId to set
     */
    public void setNivelOcupacionalId(final Long nivelOcupacionalId) {
        this.nivelOcupacionalId = nivelOcupacionalId;
    }

    /**
     * @param escalaOcupacionalId the escalaOcupacionalId to set
     */
    public void setEscalaOcupacionalId(final Long escalaOcupacionalId) {
        this.escalaOcupacionalId = escalaOcupacionalId;
    }

    /**
     * @param modalidadLaboralId the modalidadLaboralId to set
     */
    public void setModalidadLaboralId(final Long modalidadLaboralId) {
        this.modalidadLaboralId = modalidadLaboralId;
    }

    /**
     * @param denominacionPuestoId the denominacionPuestoId to set
     */
    public void setDenominacionPuestoId(final Long denominacionPuestoId) {
        this.denominacionPuestoId = denominacionPuestoId;
    }

    /**
     * @param unidadAdministrativaId the unidadAdministrativaId to set
     */
    public void setUnidadAdministrativaId(final Long unidadAdministrativaId) {
        this.unidadAdministrativaId = unidadAdministrativaId;
    }

    /**
     * @param estadosPuestoId the estadosPuestoId to set
     */
    public void setEstadosPuestoId(final List<Long> estadosPuestoId) {
        this.estadosPuestoId = estadosPuestoId;
    }

    /**
     * @param estadosServidorId the estadosServidorId to set
     */
    public void setEstadosServidorId(final List<Long> estadosServidorId) {
        this.estadosServidorId = estadosServidorId;
    }

    /**
     * @return the servidores
     */
    public List<Servidor> getServidores() {
        return servidores;
    }

    /**
     * @param servidores the servidores to set
     */
    public void setServidores(final List<Servidor> servidores) {
        this.servidores = servidores;
    }

    /**
     * @return the tipoNomina
     */
    public TipoNomina getTipoNomina() {
        return tipoNomina;
    }

    /**
     * @param tipoNomina the tipoNomina to set
     */
    public void setTipoNomina(final TipoNomina tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    /**
     * @return the terceros
     */
    public List<Tercero> getTerceros() {
        return terceros;
    }

    /**
     * @param terceros the terceros to set
     */
    public void setTerceros(List<Tercero> terceros) {
        this.terceros = terceros;
    }

    /**
     * @return the usuarioVO
     */
    public UsuarioVO getUsuarioVO() {
        return usuarioVO;
    }

    /**
     * @param usuarioVO the usuarioVO to set
     */
    public void setUsuarioVO(UsuarioVO usuarioVO) {
        this.usuarioVO = usuarioVO;
    }

    /**
     * @return the liquidaciones
     */
    public List<Liquidacion> getLiquidaciones() {
        return liquidaciones;
    }

    /**
     * @param liquidaciones the liquidaciones to set
     */
    public void setLiquidaciones(List<Liquidacion> liquidaciones) {
        this.liquidaciones = liquidaciones;
    }

    /**
     * @return the anticipos
     */
    public List<Anticipo> getAnticipos() {
        return anticipos;
    }

    /**
     * @param anticipos the anticipos to set
     */
    public void setAnticipos(List<Anticipo> anticipos) {
        this.anticipos = anticipos;
    }
}
