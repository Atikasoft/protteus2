/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;


public class EstadoAdministracionPuestoRegimenLaboralVO {
    
    private EstadoAdministracionPuesto estadoAdministracionPuesto;
    
    private Long estadoAdministracionPuestoRegimenLaboralId;
    
    private boolean seleccionado;

    public EstadoAdministracionPuesto getEstadoAdministracionPuesto() {
        return estadoAdministracionPuesto;
    }

    public void setEstadoAdministracionPuesto(EstadoAdministracionPuesto estadoAdministracionPuesto) {
        this.estadoAdministracionPuesto = estadoAdministracionPuesto;
    }

    public Long getEstadoAdministracionPuestoRegimenLaboralId() {
        return estadoAdministracionPuestoRegimenLaboralId;
    }

    public void setEstadoAdministracionPuestoRegimenLaboralId(Long estadoAdministracionPuestoRegimenLaboralId) {
        this.estadoAdministracionPuestoRegimenLaboralId = estadoAdministracionPuestoRegimenLaboralId;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
    
    
}
