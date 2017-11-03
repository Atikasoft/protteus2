/*
 *  TipoMovimientoReglaControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoMovimientoReglaHelper;
import ec.com.atikasoft.proteus.comparadores.ComparadorTipoMovimientoReglaVO;
import ec.com.atikasoft.proteus.enums.ObligatorioEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.TipoMovimientoReglaVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoReglaBean")
@ViewScoped
public class TipoMovimientoReglaControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/tipo_movimiento/tipo_movimiento_regla.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf";

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tipoMovimientoReglaHelper}")
    private TipoMovimientoReglaHelper tipoMovimientoReglaHelper;

    /**
     * Administracion de servicios.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoReglaControlador() {
        super();
    }

    /**
     * Metodo que se encarga de iniciar la pagina de reglas por tipo de movimiento.
     *
     * @return String pantalla de configuracion de reglas
     */
    public String iniciarConfiguracionReglas() {
        cargarTablaReglas(tipoMovimientoReglaHelper.getTipoMovimiento().getId());
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de llenar la lista de reglas para la tabla de configuracion de reglas por tipo de
     * movimiento.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     */
    public void cargarTablaReglas(final Long tipoMovimientoId) {
        try {
            List<Regla> listaReglas = administracionServicio.listarReglasVigentes();
            List<TipoMovimientoRegla> listaTipoMovimientoRegla =
                    administracionServicio.listarTipoMovimientoReglaPorTipoMovimientoId(tipoMovimientoId);
            tipoMovimientoReglaHelper.setListaTipoMovimientoReglaVO(new ArrayList<TipoMovimientoReglaVO>());
            validarReglasSeleccionadas(listaReglas, listaTipoMovimientoRegla);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "Error al cargar a tabla de reglas " + ex);
        }
    }

    /**
     * Metodo que se encarga de validar las reglas seleccionados en la pantalla.
     *
     * @param listaReglas lista de reglas
     * @param listaTipoMovimientoRegla lista de reglas por tipo de movimiento
     */
    public void validarReglasSeleccionadas(final List<Regla> listaReglas,
            final List<TipoMovimientoRegla> listaTipoMovimientoRegla) {
        for (Regla regla : listaReglas) {
            TipoMovimientoReglaVO tmrvo = new TipoMovimientoReglaVO();
            tmrvo.setSeleccionar(Boolean.FALSE);
            tmrvo.setRegla(regla);
            tmrvo.setObligatorio(Boolean.FALSE);
            tmrvo.setJustificable(Boolean.FALSE);
            tipoMovimientoReglaHelper.getListaTipoMovimientoReglaVO().add(tmrvo);
            for (TipoMovimientoRegla tmr : listaTipoMovimientoRegla) {
                if (regla.getId().equals(tmr.getRegla().getId())) {
                    tmrvo.setSeleccionar(Boolean.TRUE);
                    tmrvo.setObligatorio(tmr.getObligatorio());
                    tmrvo.setJustificable(tmr.getJustificable());
                    break;
                }
            }
        }
        Collections.sort(tipoMovimientoReglaHelper.getListaTipoMovimientoReglaVO(),
                new ComparadorTipoMovimientoReglaVO());
    }

    @Override
    @PostConstruct
    public void init() {
        setTipoMovimientoReglaHelper(tipoMovimientoReglaHelper);
        setCatalogoHelper(tipoMovimientoReglaHelper);
        llenarListaObligatorio();
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.TIPOS_MOVIMIENTOS_REGLAS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE REGLAS POR TIPOS DE MOVIMIENTOS");
        parametrosReporte.put("p_tipo_movimiento_id", tipoMovimientoReglaHelper.getTipoMovimiento().getId().toString());
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
            List<TipoMovimientoReglaVO> lista = verificarListaGuardar();
            administracionServicio.guardarTipoMovimientoRegla(obtenerUsuario(), tipoMovimientoReglaHelper.
                    getTipoMovimiento(), lista);
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento regla", ex);
        }
        return null;
    }

    /**
     * Metodo que se encarga de iterar la lista de seleccionados y guardar la nueva lista.
     *
     * @return Lista de TipoMovimientoAlertaVO
     */
    public List<TipoMovimientoReglaVO> verificarListaGuardar() {
        List<TipoMovimientoReglaVO> listaTMRVO = new ArrayList<TipoMovimientoReglaVO>();
        for (TipoMovimientoReglaVO tmrvo : tipoMovimientoReglaHelper.getListaTipoMovimientoReglaVO()) {
            if (tmrvo.getSeleccionar()) {
                TipoMovimientoReglaVO tmr = new TipoMovimientoReglaVO();
                tmr.setObligatorio(tmrvo.getObligatorio());
                tmr.setRegla(tmrvo.getRegla());
                tmr.setSeleccionar(tmrvo.getSeleccionar());
                tmr.setJustificable(tmrvo.getJustificable());
                listaTMRVO.add(tmr);
            }
        }
        return listaTMRVO;
    }

    @Override
    public String iniciarEdicion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String iniciarNuevo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String borrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String buscar() {
        return LISTA_ENTIDAD;
    }

    /**
     * Metodo que se encarga de llenar la lista de obligatorio.
     */
    public void llenarListaObligatorio() {
        tipoMovimientoReglaHelper.getListaObligatorio().clear();
        for (ObligatorioEnum o : ObligatorioEnum.values()) {
            tipoMovimientoReglaHelper.getListaObligatorio().add(new SelectItem(o.getCodigo(), o.getDescripcion()));
        }
    }

    /**
     * @return the tipoMovimientoReglaHelper
     */
    public TipoMovimientoReglaHelper getTipoMovimientoReglaHelper() {
        return tipoMovimientoReglaHelper;
    }

    /**
     * @param tipoMovimientoReglaHelper the tipoMovimientoReglaHelper to set
     */
    public void setTipoMovimientoReglaHelper(final TipoMovimientoReglaHelper tipoMovimientoReglaHelper) {
        this.tipoMovimientoReglaHelper = tipoMovimientoReglaHelper;
    }
}
