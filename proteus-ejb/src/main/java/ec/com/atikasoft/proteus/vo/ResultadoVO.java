/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import java.util.List;

/**
 *
 * @author henry
 */
public class ResultadoVO {

    /**
     *
     */
    private Boolean ejecutadoExitosamente;

    /**
     *
     */
    private String mensajeError;

    /**
     *
     */
    private Boolean existenResultado;

    /**
     *
     */
    private EmpleadoAuditoriaVO empleadoAuditoria;

    /**
     * @return the ejecutadoExitosamente
     */
    public Boolean getEjecutadoExitosamente() {
        return ejecutadoExitosamente;
    }

    /**
     * @param ejecutadoExitosamente the ejecutadoExitosamente to set
     */
    public void setEjecutadoExitosamente(Boolean ejecutadoExitosamente) {
        this.ejecutadoExitosamente = ejecutadoExitosamente;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    /**
     * @return the existenResultado
     */
    public Boolean getExistenResultado() {
        return existenResultado;
    }

    /**
     * @param existenResultado the existenResultado to set
     */
    public void setExistenResultado(Boolean existenResultado) {
        this.existenResultado = existenResultado;
    }

    /**
     * @return the empleadoAuditoria
     */
    public EmpleadoAuditoriaVO getEmpleadoAuditoria() {
        return empleadoAuditoria;
    }

    /**
     * @param empleadoAuditoria the empleadoAuditoria to set
     */
    public void setEmpleadoAuditoria(EmpleadoAuditoriaVO empleadoAuditoria) {
        this.empleadoAuditoria = empleadoAuditoria;
    }

}
