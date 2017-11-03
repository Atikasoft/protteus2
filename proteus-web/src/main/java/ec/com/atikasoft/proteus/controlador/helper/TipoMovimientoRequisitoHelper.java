/*
 *  TipoMovimientoRequisitoHelper.java
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
 *  06/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.vo.TipoMovimientoRequisitoVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoRequisitoHelp")
@SessionScoped
public class TipoMovimientoRequisitoHelper extends CatalogoHelper {

    /**
     * Entidad de tipo de movimeinto.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Entidad de tipo de movim iento requisito.
     */
    private TipoMovimientoRequisito tipoMovimientoRequisito;

    /**
     * Lista para saber si es obligatorio o no.
     */
    private List<SelectItem> listaObligatorio;

    /**
     * Wraper de Tipos de movimientos por requisitos.
     */
    private TipoMovimientoRequisitoVO tipoMovimientoRequisitoVO;

    /**
     * Lista del Tipos de movimientos por requisitos.
     */
    private List<TipoMovimientoRequisitoVO> listaTipoMovimientoRequisitoVO;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoRequisitoHelper() {
        super();
        init();
    }

    /**
     * Metodo inicializador de la clase.
     */
    public final void init() {
        tipoMovimiento = new TipoMovimiento();
        tipoMovimientoRequisito = new TipoMovimientoRequisito();
        listaObligatorio = new ArrayList<SelectItem>();
        tipoMovimientoRequisitoVO = new TipoMovimientoRequisitoVO();
        listaTipoMovimientoRequisitoVO = new ArrayList<TipoMovimientoRequisitoVO>();
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
     * @return the tipoMovimientoRequisito
     */
    public TipoMovimientoRequisito getTipoMovimientoRequisito() {
        return tipoMovimientoRequisito;
    }

    /**
     * @param tipoMovimientoRequisito the tipoMovimientoRequisito to set
     */
    public void setTipoMovimientoRequisito(final TipoMovimientoRequisito tipoMovimientoRequisito) {
        this.tipoMovimientoRequisito = tipoMovimientoRequisito;
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

    /**
     * @return the tipoMovimientoRequisitoVO
     */
    public TipoMovimientoRequisitoVO getTipoMovimientoRequisitoVO() {
        return tipoMovimientoRequisitoVO;
    }

    /**
     * @param tipoMovimientoRequisitoVO the tipoMovimientoRequisitoVO to set
     */
    public void setTipoMovimientoRequisitoVO(final TipoMovimientoRequisitoVO tipoMovimientoRequisitoVO) {
        this.tipoMovimientoRequisitoVO = tipoMovimientoRequisitoVO;
    }

    /**
     * @return the listaTipoMovimientoRequisitoVO
     */
    public List<TipoMovimientoRequisitoVO> getListaTipoMovimientoRequisitoVO() {
        return listaTipoMovimientoRequisitoVO;
    }

    /**
     * @param listaTipoMovimientoRequisitoVO the listaTipoMovimientoRequisitoVO to set
     */
    public void setListaTipoMovimientoRequisitoVO(final List<TipoMovimientoRequisitoVO> listaTipoMovimientoRequisitoVO) {
        this.listaTipoMovimientoRequisitoVO = listaTipoMovimientoRequisitoVO;
    }
}
