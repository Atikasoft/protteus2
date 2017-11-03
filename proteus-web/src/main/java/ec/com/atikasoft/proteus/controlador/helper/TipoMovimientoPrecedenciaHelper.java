/*
 *  TipoMovimientoPrecedenciaHelper.java
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
 *  29/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.enums.TipoAccionEnum;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoPrecedencia;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoPrecedenciaHelper")
@SessionScoped
public class TipoMovimientoPrecedenciaHelper {

    /**
     * Tipo de Movimiento cabcera.
     */
    private TipoMovimiento tipoMovimiento;

    /**
     * Tipo de Movimiento para la edicion.
     */
    private TipoMovimiento tipoMovimientoEdit;

    /**
     * Tipo de Movimiento para la eliminacion.
     */
    private TipoMovimientoPrecedencia tipoMovimientoPrecedenciaDelete;

    /**
     * Lista para llenar el combo de grupo desde el catalogo del Proteus.
     */
    private List<SelectItem> listaGrupo;

    /**
     * Lista para llenar el combo de clase desde el catalogo del Proteus.
     */
    private List<SelectItem> listaClase;

    /**
     * Lista de tipos de movimientos.
     */
    private List<SelectItem> listaTipoMovimiento;

    /**
     * Lista de Tipo de Movimiento Precedencia.
     */
    private List<TipoMovimientoPrecedencia> listaTipoMovimientoPrecedencia;

    /**
     * Constructor por defecto.
     */
    public TipoMovimientoPrecedenciaHelper() {
        super();
        init();
    }

    /**
     * Metodo de inicializacion de variables.
     */
    public final void init() {
        tipoMovimiento = new TipoMovimiento();
        tipoMovimientoEdit = new TipoMovimiento();
        tipoMovimientoPrecedenciaDelete = new TipoMovimientoPrecedencia();
        listaGrupo = new ArrayList<SelectItem>();
        listaClase = new ArrayList<SelectItem>();
        listaTipoMovimiento = new ArrayList<SelectItem>();
        setListaTipoMovimientoPrecedencia(new ArrayList<TipoMovimientoPrecedencia>());
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
     * @return the listaGrupo
     */
    public List<SelectItem> getListaGrupo() {
        return listaGrupo;
    }

    /**
     * @param listaGrupo the listaGrupo to set
     */
    public void setListaGrupo(final List<SelectItem> listaGrupo) {
        this.listaGrupo = listaGrupo;
    }

    /**
     * @return the listaClase
     */
    public List<SelectItem> getListaClase() {
        return listaClase;
    }

    /**
     * @param listaClase the listaClase to set
     */
    public void setListaClase(final List<SelectItem> listaClase) {
        this.listaClase = listaClase;
    }

    /**
     * @return the listaTipoMovimiento
     */
    public List<SelectItem> getListaTipoMovimiento() {
        return listaTipoMovimiento;
    }

    /**
     * @param listaTipoMovimiento the listaTipoMovimiento to set
     */
    public void setListaTipoMovimiento(final List<SelectItem> listaTipoMovimiento) {
        this.listaTipoMovimiento = listaTipoMovimiento;
    }

    /**
     * @return the tipoMovimientoEdit
     */
    public TipoMovimiento getTipoMovimientoEdit() {
        return tipoMovimientoEdit;
    }

    /**
     * @param tipoMovimientoEdit the tipoMovimientoEdit to set
     */
    public void setTipoMovimientoEdit(final TipoMovimiento tipoMovimientoEdit) {
        this.tipoMovimientoEdit = tipoMovimientoEdit;
    }

    /**
     * @return the listaTipoMovimientoPrecedencia
     */
    public List<TipoMovimientoPrecedencia> getListaTipoMovimientoPrecedencia() {
        return listaTipoMovimientoPrecedencia;
    }

    /**
     * @param listaTipoMovimientoPrecedencia the listaTipoMovimientoPrecedencia to set
     */
    public void setListaTipoMovimientoPrecedencia(
            final List<TipoMovimientoPrecedencia> listaTipoMovimientoPrecedencia) {
        this.listaTipoMovimientoPrecedencia = listaTipoMovimientoPrecedencia;
    }

    /**
     * @return the tipoMovimientoPrecedenciaDelete
     */
    public TipoMovimientoPrecedencia getTipoMovimientoPrecedenciaDelete() {
        return tipoMovimientoPrecedenciaDelete;
    }

    /**
     * @param tipoMovimientoPrecedenciaDelete the tipoMovimientoPrecedenciaDelete to set
     */
    public void setTipoMovimientoPrecedenciaDelete(
            final TipoMovimientoPrecedencia tipoMovimientoPrecedenciaDelete) {
        this.tipoMovimientoPrecedenciaDelete = tipoMovimientoPrecedenciaDelete;
    }
}
