/*
 *  ReasignacionTramiteHelper.java
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
 *  03/01/2013
 *
 */

package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDelegado;
import ec.com.atikasoft.proteus.vo.TipoMovimientoDelegadoVO;
import ec.com.atikasoft.proteus.vo.UsuarioRolVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "delegacionAprobacionHelper")
@SessionScoped
public class DelegacionAprobacionHelper implements Serializable {
    /**
     * variable de Vo para cada registro de la lista.
     */
    private TipoMovimientoDelegadoVO tipoMovimientoDelegadoVO;
    /**
     * variable lista de Tipo Movimiento Delegado.
     */
    private List<TipoMovimientoDelegadoVO> listaTipoMovimientoDelegadoVO;
   /**
     * Lista de UsuarioRolVO.
     */
    private List<UsuarioRolVO> listaUsuariosRolVO;
    /**
     * servidor seleccionar.
     */
    private UsuarioRolVO selectedUsuarioRolVO;
    /**
     * variable de tipo de movimiento.
     */
    private TipoMovimiento tipoMovimiento;
    /**
     * variable de tipo movimiento delegado.
     */
    private TipoMovimientoDelegado tipoMovimientoDelegado;
    /**
     * constructor.
     */
    public DelegacionAprobacionHelper() {
        super();
        iniciador();
    }
    /**
     * Este método inicializa las variables del helper.
     */
    public final void iniciador() {
        setTipoMovimientoDelegadoVO(new TipoMovimientoDelegadoVO());
        setListaTipoMovimientoDelegadoVO(new ArrayList<TipoMovimientoDelegadoVO>());
        setListaUsuariosRolVO(new ArrayList<UsuarioRolVO>());
        selectedUsuarioRolVO = new UsuarioRolVO();
        setTipoMovimiento(new TipoMovimiento());
        setTipoMovimientoDelegado(new TipoMovimientoDelegado());
    }
    /**
     * @return the tipoMovimientoDelegadoVO
     */
    public TipoMovimientoDelegadoVO getTipoMovimientoDelegadoVO() {
        return tipoMovimientoDelegadoVO;
    }

    /**
     * @param tipoMovimientoDelegadoVO the tipoMovimientoDelegadoVO to set
     */
    public void setTipoMovimientoDelegadoVO(final TipoMovimientoDelegadoVO tipoMovimientoDelegadoVO) {
        this.tipoMovimientoDelegadoVO = tipoMovimientoDelegadoVO;
    }

    /**
     * @return the listaTipoMovimientoDelegadoVO
     */
    public List<TipoMovimientoDelegadoVO> getListaTipoMovimientoDelegadoVO() {
        return listaTipoMovimientoDelegadoVO;
    }

    /**
     * @param listaTipoMovimientoDelegadoVO the listaTipoMovimientoDelegadoVO to set
     */
    public void setListaTipoMovimientoDelegadoVO(final List<TipoMovimientoDelegadoVO> listaTipoMovimientoDelegadoVO) {
        this.listaTipoMovimientoDelegadoVO = listaTipoMovimientoDelegadoVO;
    }

    /**
     * @return the listaUsuariosRolVO
     */
    public List<UsuarioRolVO> getListaUsuariosRolVO() {
        return listaUsuariosRolVO;
    }

    /**
     * @param listaUsuariosRolVO the listaUsuariosRolVO to set
     */
    public void setListaUsuariosRolVO(List<UsuarioRolVO> listaUsuariosRolVO) {
        this.listaUsuariosRolVO = listaUsuariosRolVO;
    }

    /**
     * @return the selectedUsuarioRolVO
     */
    public UsuarioRolVO getSelectedUsuarioRolVO() {
        return selectedUsuarioRolVO;
    }

    /**
     * @param selectedUsuarioRolVO the selectedUsuarioRolVO to set
     */
    public void setSelectedUsuarioRolVO(UsuarioRolVO selectedUsuarioRolVO) {
        this.selectedUsuarioRolVO = selectedUsuarioRolVO;
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
     * @return the tipoMovimientoDelegado
     */
    public TipoMovimientoDelegado getTipoMovimientoDelegado() {
        return tipoMovimientoDelegado;
    }

    /**
     * @param tipoMovimientoDelegado the tipoMovimientoDelegado to set
     */
    public void setTipoMovimientoDelegado(final TipoMovimientoDelegado tipoMovimientoDelegado) {
        this.tipoMovimientoDelegado = tipoMovimientoDelegado;
    }
}
