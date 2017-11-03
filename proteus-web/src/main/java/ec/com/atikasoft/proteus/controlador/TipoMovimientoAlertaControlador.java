/*
 *  TipoMovimientoAlertaControlador.java
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
import ec.com.atikasoft.proteus.controlador.helper.TipoMovimientoAlertaHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Alerta;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoAlerta;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.TipoMovimientoAlertaVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "tipoMovimientoAlertaBean")
@ViewScoped
public class TipoMovimientoAlertaControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/tipo_movimiento/tipo_movimiento_alerta.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf";
    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tipoMovimientoAlertaHelper}")
    private TipoMovimientoAlertaHelper tipoMovimientoAlertaHelper;
    /**
     * BO de administracion de servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoAlertaControlador() {
        super();
    }

    @PostConstruct
    @Override
    public void init() {
        setTipoMovimientoAlertaHelper(tipoMovimientoAlertaHelper);
        setCatalogoHelper(tipoMovimientoAlertaHelper);
    }

    /**
     * Metodo que se encarga de inicializar todos lo necesario para la
     * configuracion de alertas por tipos de movimiento.
     *
     * @return String nombre de la pantalla
     */
    public String iniciarConfiguracionAlertas() {
        cargarTablaAlertas(tipoMovimientoAlertaHelper.getTipoMovimiento().getId());
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de llenar la lista de alertas para la tabla de
     * configuracion de alertas por tipo de movimiento.
     *
     * @param tipoMovimientoId id del tipo de movimiento
     */
    public void cargarTablaAlertas(final Long tipoMovimientoId) {
        try {
            List<Alerta> listaAlertas = administracionServicio.listarAlertasVigentes();
            List<TipoMovimientoAlerta> listaTipoMovimientoAlerta =
                    administracionServicio.listarTipoMovimientoAlertaPorTipoMovimientoId(tipoMovimientoId);
            tipoMovimientoAlertaHelper.setListaTipoMovimientoAlertaVO(new ArrayList<TipoMovimientoAlertaVO>());
            validarAlertasSeleccionadas(listaAlertas, listaTipoMovimientoAlerta);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "Error al cargar a tabla de requisitos " + ex);
        }
    }

    /**
     * Metodo que se encarga de validar las alertas seleccionados en la
     * pantalla.
     *
     * @param listaAlertas lista de alertas
     * @param listaTipoMovimientoAlerta lista de alertas por tipo de movimiento
     */
    public void validarAlertasSeleccionadas(final List<Alerta> listaAlertas,
            final List<TipoMovimientoAlerta> listaTipoMovimientoAlerta) {
        for (Alerta alerta : listaAlertas) {
            TipoMovimientoAlertaVO tmavo = new TipoMovimientoAlertaVO();
            tmavo.setSeleccionar(Boolean.FALSE);
            tmavo.setAlerta(alerta);
            tmavo.setMensaje("");
            tipoMovimientoAlertaHelper.getListaTipoMovimientoAlertaVO().add(tmavo);
            for (TipoMovimientoAlerta tma : listaTipoMovimientoAlerta) {
                if (alerta.getId().equals(tma.getAlerta().getId())) {
                    tmavo.setSeleccionar(Boolean.TRUE);
                    tmavo.setMensaje(tma.getMensaje());
                    break;
                }
            }
        }
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.TIPOS_MOVIMIENTOS_ALERTAS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE ALERTAS POR TIPOS DE MOVIMIENTOS");
        parametrosReporte.put("p_tipo_movimiento_id", tipoMovimientoAlertaHelper.getTipoMovimiento().getId().toString());
        generarUrlDeReporte();
    }

    @Override
    public String guardar() {
        try {
//            int x;
//            List<TipoMovimientoAlertaVO> lista = verificarListaGuardar();
//            List<TipoMovimientoAlerta> listaEliminar = administracionServicio.listarTipoMovimientoAlertaPorTipoMovimientoId(tipoMovimientoAlertaHelper.getTipoMovimiento().getId());
//            x = listaEliminar.size();            
//            if (!lista.isEmpty()) {
//                Boolean resultado = administracionServicio.guardarTipoMovimientoAlerta(obtenerUsuarioLogeado().getNombre(),
//                        tipoMovimientoAlertaHelper.getTipoMovimiento(), lista);
//                if (resultado) {
//                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
//                } else {
//                    mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.tipoMovimientoAlerta.mensajeVacio",
//                            FacesMessage.SEVERITY_ERROR);
//                }
//            } else {
//                if (x > 0) {
//                    Boolean resultado = administracionServicio.guardarTipoMovimientoAlerta(obtenerUsuarioLogeado().getNombre(),
//                            tipoMovimientoAlertaHelper.getTipoMovimiento(), lista);
//                    //if (resultado) {
//                        mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);                        
//                    //}
//                }
//            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento alerta", ex);
        }
        return null;
    }

    /**
     * Metodo que se encarga de iterar la lista de seleccionados y guardar la
     * nueva lista.
     *
     * @return Lista de TipoMovimientoAlertaVO
     */
    public List<TipoMovimientoAlertaVO> verificarListaGuardar() {
        List<TipoMovimientoAlertaVO> listaTMAVO = new ArrayList<TipoMovimientoAlertaVO>();
        for (TipoMovimientoAlertaVO tmavo : tipoMovimientoAlertaHelper.getListaTipoMovimientoAlertaVO()) {
            if (tmavo.getSeleccionar()) {
                TipoMovimientoAlertaVO tma = new TipoMovimientoAlertaVO();
                tma.setMensaje(tmavo.getMensaje());
                tma.setAlerta(tmavo.getAlerta());
                tma.setSeleccionar(tmavo.getSeleccionar());
                listaTMAVO.add(tma);
            }
        }
        return listaTMAVO;
    }

    /**
     * Obtiene la descripcion del tipo de periodo por el codigo.
     *
     * @param codigo codigo
     * @return Descripcion del periodo
     */
    public String obtenerDescripcionTipoPeriodo(final String codigo) {
        return TipoPeriodoAlertaEnum.obtenerDescripcion(codigo);
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
     * @return the tipoMovimientoAlertaHelper
     */
    public TipoMovimientoAlertaHelper getTipoMovimientoAlertaHelper() {
        return tipoMovimientoAlertaHelper;
    }

    /**
     * @param tipoMovimientoAlertaHelper the tipoMovimientoAlertaHelper to set
     */
    public void setTipoMovimientoAlertaHelper(final TipoMovimientoAlertaHelper tipoMovimientoAlertaHelper) {
        this.tipoMovimientoAlertaHelper = tipoMovimientoAlertaHelper;
    }
}
