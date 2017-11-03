/*
 *  AnticipoAprobacionHelper.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  05/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.ImpuestoRenta;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;


/**
 * ImpuestoRenta
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "impuestoRentaHelper")
@SessionScoped
public class ImpuestoRentaHelper extends CatalogoHelper {

    /**
     * Ejercicio fiscales para el filtro de la tabla
     */
    private List<SelectItem> ejerciciosFiscales;
    
    /**
     * Ejercicio fiscal seleccionado en el filtro
     */
    private Long ejercicioFiscalSeleccionado;
    
    /**
     * Lista de impuestos a la renta
     */
    private List<ImpuestoRenta> impuestosRenta;
    
    /**
     * Impuesto a la renta transaccional para edicion eliminacion en la tabla
     */
    private ImpuestoRenta impuestoRentaEditDelete;
    
    /**
     * Referencia Impuesto a la renta seleccionado
     */
    private ImpuestoRenta impuestoRenta;
    
    /**
     * Bandera que define si esta o no seleccionado un ejercicio fiscal en el
     * filtro de la pagina de la tabla
     */
    private boolean existeEjercicioFiscalSeleccionado;
    
    /**
     * Constructor por defecto.
     */
    public ImpuestoRentaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables del AnticipoHelper.
     */
    public final void iniciador() {
        ejerciciosFiscales = new ArrayList<SelectItem>();
        existeEjercicioFiscalSeleccionado = false;
        impuestosRenta = new ArrayList<ImpuestoRenta>();
    }

    public List<SelectItem> getEjerciciosFiscales() {
        return ejerciciosFiscales;
    }

    public void setEjerciciosFiscales(List<SelectItem> ejerciciosFiscales) {
        this.ejerciciosFiscales = ejerciciosFiscales;
    }

    public Long getEjercicioFiscalSeleccionado() {
        return ejercicioFiscalSeleccionado;
    }

    public void setEjercicioFiscalSeleccionado(Long ejercicioFiscalSeleccionado) {
        this.ejercicioFiscalSeleccionado = ejercicioFiscalSeleccionado;
    }

    public List<ImpuestoRenta> getImpuestosRenta() {
        return impuestosRenta;
    }

    public void setImpuestosRenta(List<ImpuestoRenta> impuestosRenta) {
        this.impuestosRenta = impuestosRenta;
    }

    public ImpuestoRenta getImpuestoRentaEditDelete() {
        return impuestoRentaEditDelete;
    }

    public void setImpuestoRentaEditDelete(ImpuestoRenta impuestoRentaEditDelete) {
        this.impuestoRentaEditDelete = impuestoRentaEditDelete;
    }

    public ImpuestoRenta getImpuestoRenta() {
        return impuestoRenta;
    }

    public void setImpuestoRenta(ImpuestoRenta impuestoRenta) {
        this.impuestoRenta = impuestoRenta;
    }

    public boolean isExisteEjercicioFiscalSeleccionado() {
        return existeEjercicioFiscalSeleccionado;
    }

    public void setExisteEjercicioFiscalSeleccionado(boolean existeEjercicioFiscalSeleccionado) {
        this.existeEjercicioFiscalSeleccionado = existeEjercicioFiscalSeleccionado;
    }
}
