/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "impuestoRentaFormularioHelper")
@SessionScoped
public class ImpuestoRentaFormularioHelper {
    
    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoFiscal = new ArrayList<>();
    
    /**
     * Instituciones Ejercicio Fiscal encontrados.
     */
    private List<InstitucionEjercicioFiscal> institucionesEjerciciosFiscales = new ArrayList<>();
    
    /**
     * Id Ejercicio Fiscal seleccionado.
     */
    private Long periodoFiscalId;
    

    public List<SelectItem> getListaPeriodoFiscal() {
        return listaPeriodoFiscal;
    }

    public void setListaPeriodoFiscal(List<SelectItem> listaPeriodoFiscal) {
        this.listaPeriodoFiscal = listaPeriodoFiscal;
    }

    public List<InstitucionEjercicioFiscal> getInstitucionesEjerciciosFiscales() {
        return institucionesEjerciciosFiscales;
    }

    public void setInstitucionesEjerciciosFiscales(List<InstitucionEjercicioFiscal> institucionesEjerciciosFiscales) {
        this.institucionesEjerciciosFiscales = institucionesEjerciciosFiscales;
    }

    public Long getPeriodoFiscalId() {
        return periodoFiscalId;
    }

    public void setPeriodoFiscalId(Long periodoFiscalId) {
        this.periodoFiscalId = periodoFiscalId;
    }
    
    
    
}
