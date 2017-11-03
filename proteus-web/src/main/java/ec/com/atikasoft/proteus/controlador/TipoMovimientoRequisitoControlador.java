/*
 *  TipoMovimientoRequisitoControlador.java
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
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoMovimientoRequisitoHelper;
import ec.com.atikasoft.proteus.comparadores.ComparadorTipoMovimientoRequisitoVO;
import ec.com.atikasoft.proteus.enums.ObligatorioEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Requisito;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.TipoMovimientoRequisitoVO;
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
@ManagedBean(name = "tipoMovimientoRequisitoBean")
@ViewScoped
public class TipoMovimientoRequisitoControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD =
            "/pages/administracion/tipo_movimiento/tipo_movimiento_requisito.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf";

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{tipoMovimientoRequisitoHelp}")
    private TipoMovimientoRequisitoHelper tipoMovimientoRequisitoHelper;

    /**
     * Clase de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Constructor de la clase.
     */
    public TipoMovimientoRequisitoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setTipoMovimientoRequisitoHelper(tipoMovimientoRequisitoHelper);
        setCatalogoHelper(tipoMovimientoRequisitoHelper);
        llenarListaObligatorio();
    }

    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.TIPOS_MOVIMIENTOS_REQUISITOS.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE REQUISITOS POR TIPOS DE MOVIMIENTOS");
        parametrosReporte.put("p_tipo_movimiento_id",
                tipoMovimientoRequisitoHelper.getTipoMovimiento().getId().toString());
        generarUrlDeReporte();
    }

    /**
     * Metodo que se encarga de iniciar la paguina de requisitos por tipo de movimiento.
     *
     * @return String pantalla de configuracion de requisitos
     */
    public String iniciarConfiguracionRequisitos() {
        cargarTablaRequisitos(
                tipoMovimientoRequisitoHelper.getTipoMovimiento().getClase().getGrupo().getId(),
                tipoMovimientoRequisitoHelper.getTipoMovimiento().getId());
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Metodo que se encarga de llenar la lista de obligatorio.
     */
    public void llenarListaObligatorio() {
        tipoMovimientoRequisitoHelper.getListaObligatorio().clear();
        for (ObligatorioEnum o : ObligatorioEnum.values()) {
            tipoMovimientoRequisitoHelper.getListaObligatorio().add(new SelectItem(o.getCodigo(), o.getDescripcion()));
        }
    }

    @Override
    public String guardar() {
        try {
            administracionServicio.guardarTipoMovimientoRequisito(obtenerUsuario(),
                    tipoMovimientoRequisitoHelper.getTipoMovimiento().getId(), tipoMovimientoRequisitoHelper.
                    getListaTipoMovimientoRequisitoVO());
            cargarTablaRequisitos(
                    tipoMovimientoRequisitoHelper.getTipoMovimiento().getClase().getGrupo().getId(),
                    tipoMovimientoRequisitoHelper.getTipoMovimiento().getId());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento requisito", ex);
        }
        return null;
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
     * Metodo que se encarga de llenar la lista de requisitos para la tabla de configuracion de requisitos por tipo de
     * movimiento.
     *
     * @param grupoId id del grupo
     * @param tipoMovimientoId id del tipo de movimiento
     */
    public void cargarTablaRequisitos(final Long grupoId, final Long tipoMovimientoId) {
        try {
            List<Requisito> listaRequisitos = administracionServicio.listarRequisitoPorGrupoId(grupoId);
            List<TipoMovimientoRequisito> listaTipoMovimientoRequisito =
                    administracionServicio.listarTipoMovimientoRequisitoPorTipoMovimientoId(tipoMovimientoId);
            tipoMovimientoRequisitoHelper.setListaTipoMovimientoRequisitoVO(new ArrayList<TipoMovimientoRequisitoVO>());
            validarRequisitosSeleccionados(listaRequisitos, listaTipoMovimientoRequisito);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            info(getClass().getName(), "Error al cargar a tabla de requisitos " + ex);
        }
    }

    /**
     * Metodo que se encarga de validar los requisitos seleccionados en la pantalla.
     *
     * @param listaRequisitos Lista de requisitos
     * @param listaTipoMovimientoRequisito lista de requisitos por tipo de movimiento
     */
    public void validarRequisitosSeleccionados(final List<Requisito> listaRequisitos,
            final List<TipoMovimientoRequisito> listaTipoMovimientoRequisito) {
        for (Requisito req : listaRequisitos) {
            TipoMovimientoRequisitoVO tmrvo = new TipoMovimientoRequisitoVO();
            tmrvo.setSeleccionar(Boolean.FALSE);
            tmrvo.setPresentaServidorPublico(Boolean.FALSE);
            tmrvo.setSubirArchivoObligatorio(Boolean.FALSE);
            tmrvo.setRequisito(req);
            tmrvo.setObligatorio(Boolean.FALSE);
            tipoMovimientoRequisitoHelper.getListaTipoMovimientoRequisitoVO().add(tmrvo);
            for (TipoMovimientoRequisito tmr : listaTipoMovimientoRequisito) {
                if (req.getId().compareTo(tmr.getRequisito().getId()) == 0) {
                    tmrvo.setSeleccionar(Boolean.TRUE);
                    tmrvo.setObligatorio(tmr.getObligatorio());
                    tmrvo.setPresentaServidorPublico(tmr.getPresentaServidorPublico());
                    tmrvo.setSubirArchivoObligatorio(tmr.getSubirArchivoObligatorio());
                    tmrvo.setTipoMovimientoRequisitoId(tmr.getId());
                    break;
                }
            }
        }
        Collections.sort(tipoMovimientoRequisitoHelper.getListaTipoMovimientoRequisitoVO(),
                new ComparadorTipoMovimientoRequisitoVO());
    }

    /**
     * @return the tipoMovimientoRequisitoHelper
     */
    public TipoMovimientoRequisitoHelper getTipoMovimientoRequisitoHelper() {
        return tipoMovimientoRequisitoHelper;
    }

    /**
     * @param tipoMovimientoRequisitoHelper the tipoMovimientoRequisitoHelper to set
     */
    public void setTipoMovimientoRequisitoHelper(final TipoMovimientoRequisitoHelper tipoMovimientoRequisitoHelper) {
        this.tipoMovimientoRequisitoHelper = tipoMovimientoRequisitoHelper;
    }
}
