/*
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
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "sriHelper")
@SessionScoped
public class SRIHelper extends CatalogoHelper {

    /**
     * Lista de opciones.
     */
    private List<SelectItem> listaPeriodoFiscal = new ArrayList<>();

    /**
     * Lista de opciones.
     */
    private List<SelectItem> opcionesMeses = new ArrayList<>();

    /**
     * Id Ejercicio Fiscal seleccionado para Bases Imponibles.
     */
    private Long periodoFiscalBaseId;

    /**
     * Id Ejercicio Fiscal seleccionado.
     */
    private Long periodoFiscalRDEPId;

    /**
     * Ejercicio Fiscal seleccionado.
     */
    private InstitucionEjercicioFiscal periodoFiscal;

    /**
     * Instituciones Ejercicio Fiscal encontrados.
     */
    private List<InstitucionEjercicioFiscal> institucionesEsjerciciosFiscales = new ArrayList<>();

    /**
     * Mes seleccionado para Bases Imponibles.
     */
    private Integer mesBaseSeleccionado;

    /**
     * Mes seleccionado para RDEP.
     */
    private Integer mesRDEPSeleccionado;

    /**
     * Constructor por defecto.
     */
    public SRIHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del BancoHelper.
     */
    public final void iniciador() {

    }

    public List<SelectItem> getListaPeriodoFiscal() {
        return listaPeriodoFiscal;
    }

    public void setListaPeriodoFiscal(List<SelectItem> listaPeriodoFiscal) {
        this.listaPeriodoFiscal = listaPeriodoFiscal;
    }

    public List<SelectItem> getOpcionesMeses() {
        return opcionesMeses;
    }

    public void setOpcionesMeses(List<SelectItem> opcionesMeses) {
        this.opcionesMeses = opcionesMeses;
    }

    public InstitucionEjercicioFiscal getPeriodoFiscal() {
        return periodoFiscal;
    }

    public void setPeriodoFiscal(InstitucionEjercicioFiscal periodoFiscal) {
        this.periodoFiscal = periodoFiscal;
    }

    public Integer getMesBaseSeleccionado() {
        return mesBaseSeleccionado;
    }

    public void setMesBaseSeleccionado(Integer mesBaseSeleccionado) {
        this.mesBaseSeleccionado = mesBaseSeleccionado;
    }

    public Long getPeriodoFiscalBaseId() {
        return periodoFiscalBaseId;
    }

    public void setPeriodoFiscalBaseId(Long periodoFiscalBaseId) {
        this.periodoFiscalBaseId = periodoFiscalBaseId;
    }

    public List<InstitucionEjercicioFiscal> getInstitucionesEsjerciciosFiscales() {
        return institucionesEsjerciciosFiscales;
    }

    public void setInstitucionesEsjerciciosFiscales(List<InstitucionEjercicioFiscal> institucionesEsjerciciosFiscales) {
        this.institucionesEsjerciciosFiscales = institucionesEsjerciciosFiscales;
    }

    public Long getPeriodoFiscalRDEPId() {
        return periodoFiscalRDEPId;
    }

    public void setPeriodoFiscalRDEPId(Long periodoFiscalRDEPId) {
        this.periodoFiscalRDEPId = periodoFiscalRDEPId;
    }

    public Integer getMesRDEPSeleccionado() {
        return mesRDEPSeleccionado;
    }

    public void setMesRDEPSeleccionado(Integer mesRDEPSeleccionado) {
        this.mesRDEPSeleccionado = mesRDEPSeleccionado;
    }

    
}
