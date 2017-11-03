/*
 *  TipoMovimientoAlertaHelper.java
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
import ec.com.atikasoft.proteus.modelo.TipoMovimientoAlerta;
import ec.com.atikasoft.proteus.vo.TipoMovimientoAlertaVO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoAlertaHelper")
@SessionScoped
public class TipoMovimientoAlertaHelper extends CatalogoHelper {

    /**
     * Clase de Tipo de Movimiento.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Clase de Alertas por tipo de movimiento.
     */
    private TipoMovimientoAlerta tipoMovimientoAlerta;

    /**
     * Wraper para alertas por tipo de movimientos.
     */
    private TipoMovimientoAlertaVO tipoMovimientoAlertaVO;

    /**
     * Lista del Wraper para alertas por tipo de movimientos.
     */
    private List<TipoMovimientoAlertaVO> listaTipoMovimientoAlertaVO;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoAlertaHelper() {
        super();
        init();
    }

    /**
     * Metodo de inicializacion de variables.
     */
    public final void init() {
        tipoMovimiento = new TipoMovimiento();
        tipoMovimientoAlerta = new TipoMovimientoAlerta();
        tipoMovimientoAlertaVO = new TipoMovimientoAlertaVO();
        listaTipoMovimientoAlertaVO = new ArrayList<TipoMovimientoAlertaVO>();
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
     * @return the tipoMovimientoAlerta
     */
    public TipoMovimientoAlerta getTipoMovimientoAlerta() {
        return tipoMovimientoAlerta;
    }

    /**
     * @param tipoMovimientoAlerta the tipoMovimientoAlerta to set
     */
    public void setTipoMovimientoAlerta(final TipoMovimientoAlerta tipoMovimientoAlerta) {
        this.tipoMovimientoAlerta = tipoMovimientoAlerta;
    }

    /**
     * @return the tipoMovimientoAlertaVO
     */
    public TipoMovimientoAlertaVO getTipoMovimientoAlertaVO() {
        return tipoMovimientoAlertaVO;
    }

    /**
     * @param tipoMovimientoAlertaVO the tipoMovimientoAlertaVO to set
     */
    public void setTipoMovimientoAlertaVO(final TipoMovimientoAlertaVO tipoMovimientoAlertaVO) {
        this.tipoMovimientoAlertaVO = tipoMovimientoAlertaVO;
    }

    /**
     * @return the listaTipoMovimientoAlertaVO
     */
    public List<TipoMovimientoAlertaVO> getListaTipoMovimientoAlertaVO() {
        return listaTipoMovimientoAlertaVO;
    }

    /**
     * @param listaTipoMovimientoAlertaVO the listaTipoMovimientoAlertaVO to set
     */
    public void setListaTipoMovimientoAlertaVO(final List<TipoMovimientoAlertaVO> listaTipoMovimientoAlertaVO) {
        this.listaTipoMovimientoAlertaVO = listaTipoMovimientoAlertaVO;
    }
}