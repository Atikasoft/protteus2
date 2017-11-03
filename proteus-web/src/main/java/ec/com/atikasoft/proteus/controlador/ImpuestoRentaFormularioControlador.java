/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ImpuestoRentaFormularioHelper;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "impuestoRentaFormularioBean")
@ViewScoped
public class ImpuestoRentaFormularioControlador extends BaseControlador{

    @ManagedProperty("#{impuestoRentaFormularioHelper}")
    private ImpuestoRentaFormularioHelper impuestoRentaFormularioHelper;
    
    /**
     * DAO de Ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;
    
    @Override
    @PostConstruct
    public void init() {
        iniciarComboEjercicio();
    }
    
    /**
     * Inicia los valores de combo de del combo de los ejercicios fiscales
     */
    private void iniciarComboEjercicio() {
        try {
            impuestoRentaFormularioHelper.getInstitucionesEjerciciosFiscales().clear();
            
            impuestoRentaFormularioHelper.getInstitucionesEjerciciosFiscales().addAll(institucionEjercicioFiscalDao.buscarPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId()));

            impuestoRentaFormularioHelper.getListaPeriodoFiscal().clear();

            for (InstitucionEjercicioFiscal ef : impuestoRentaFormularioHelper.getInstitucionesEjerciciosFiscales()) {
                if (ef.getEjercicioFiscal().getVigente()) {
                    impuestoRentaFormularioHelper.getListaPeriodoFiscal().add(new SelectItem(ef.getId(),
                            ef.getEjercicioFiscal().getNombre()));
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(InformacionSRIControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Descarga el reporte de formulario 107
     */
    public void descargarFormulario(){
        String anio = "";
        String ruc = "";
        String ef = "";
        for(InstitucionEjercicioFiscal ejercicioFiscal: impuestoRentaFormularioHelper.getInstitucionesEjerciciosFiscales()){
            if(ejercicioFiscal.getId().equals(impuestoRentaFormularioHelper.getPeriodoFiscalId())){
                anio = ejercicioFiscal.getEjercicioFiscal().getNombre();
                ruc = ejercicioFiscal.getInstitucion().getRuc();
                ef = ejercicioFiscal.getEjercicioFiscal().getId() + "";
                break;
            }
        }
        
        setReporte(ReportesEnum.PROTEUS_REPORTE_FORMULARIO_107.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "FORMULARIOS 107");
        parametrosReporte.put("idServidor", obtenerUsuarioConectado().getServidor().getId().toString());
        parametrosReporte.put("ruc", ruc);
        parametrosReporte.put("anio", anio);
        parametrosReporte.put("ef", ef);
        generarUrlDeReporte();
    }

    public ImpuestoRentaFormularioHelper getImpuestoRentaFormularioHelper() {
        return impuestoRentaFormularioHelper;
    }

    public void setImpuestoRentaFormularioHelper(ImpuestoRentaFormularioHelper impuestoRentaFormularioHelper) {
        this.impuestoRentaFormularioHelper = impuestoRentaFormularioHelper;
    }
    
    
}
