/*
 *  TipoMovimientoReglaHelper.java
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
 *  07/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;
import ec.com.atikasoft.proteus.vo.TipoMovimientoReglaVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoReglaHelper")
@SessionScoped
public class TipoMovimientoReglaHelper extends CatalogoHelper {

    /**
     * Clase de tipo de movimiento.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Clase de reglas por tipo de movimiento.
     */
    private TipoMovimientoRegla tipoMovimientoRegla;

    /**
     * Wraper de reglas por Tipo de movimiento.
     */
    private TipoMovimientoReglaVO tipoMovimientoReglaVO;

    /**
     * Lista del Wraper de reglas por Tipo de movimiento.
     */
    private List<TipoMovimientoReglaVO> listaTipoMovimientoReglaVO;

    /**
     * Lista de obligatorio.
     */
    private List<SelectItem> listaObligatorio;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoReglaHelper() {
        super();
        init();
    }

    /**
     * Iniciador de variables.
     */
    public final void init() {
        tipoMovimiento = new TipoMovimiento();
        tipoMovimientoRegla = new TipoMovimientoRegla();
        tipoMovimientoReglaVO = new TipoMovimientoReglaVO();
        listaTipoMovimientoReglaVO = new ArrayList<TipoMovimientoReglaVO>();
        listaObligatorio = new ArrayList<SelectItem>();
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
     * @return the tipoMovimientoRegla
     */
    public TipoMovimientoRegla getTipoMovimientoRegla() {
        return tipoMovimientoRegla;
    }

    /**
     * @param tipoMovimientoRegla the tipoMovimientoRegla to set
     */
    public void setTipoMovimientoRegla(final TipoMovimientoRegla tipoMovimientoRegla) {
        this.tipoMovimientoRegla = tipoMovimientoRegla;
    }

    /**
     * @return the tipoMovimientoReglaVO
     */
    public TipoMovimientoReglaVO getTipoMovimientoReglaVO() {
        return tipoMovimientoReglaVO;
    }

    /**
     * @param tipoMovimientoReglaVO the tipoMovimientoReglaVO to set
     */
    public void setTipoMovimientoReglaVO(final TipoMovimientoReglaVO tipoMovimientoReglaVO) {
        this.tipoMovimientoReglaVO = tipoMovimientoReglaVO;
    }

    /**
     * @return the listaTipoMovimientoReglaVO
     */
    public List<TipoMovimientoReglaVO> getListaTipoMovimientoReglaVO() {
        return listaTipoMovimientoReglaVO;
    }

    /**
     * @param listaTipoMovimientoReglaVO the listaTipoMovimientoReglaVO to set
     */
    public void setListaTipoMovimientoReglaVO(final List<TipoMovimientoReglaVO> listaTipoMovimientoReglaVO) {
        this.listaTipoMovimientoReglaVO = listaTipoMovimientoReglaVO;
    }

    /**
     * @return the listaObligatorio
     */
    public List<SelectItem> getListaObligatorio() {
        return listaObligatorio;
    }

    /**
     * @param listaObligatorio the listaObligatorio to set
     */
    public void setListaObligatorio(final List<SelectItem> listaObligatorio) {
        this.listaObligatorio = listaObligatorio;
    }
}
