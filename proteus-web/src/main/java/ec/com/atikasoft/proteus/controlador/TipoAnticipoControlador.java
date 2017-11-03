/*
 *  TipoAnticipoControlador.java
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
 *  02/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.TipoAnticipoHelper;
import ec.com.atikasoft.proteus.enums.TipoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.TipoCuotaEnum;
import ec.com.atikasoft.proteus.enums.TipoGarantiaEnum;
import ec.com.atikasoft.proteus.enums.TipoRubroEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.modelo.Rubro;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AnticipoServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "tipoAnticipoBean")
@ViewScoped
public class TipoAnticipoControlador extends CatalogoControlador {

    /**
     * Logger de tipoAnticipos.
     */
    private Logger LOG = Logger.getLogger(TipoAnticipoControlador.class.getCanonicalName());
    /**
     * TipoAnticipo de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/tipo_anticipo/tipo_anticipo.jsf";
    /**
     * TipoAnticipo de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/tipo_anticipo/lista_tipo_anticipo.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AnticipoServicio anticipoServicio;
    /**
     * Servicio de regimen laboral.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tipoAnticipoHelper}")
    private TipoAnticipoHelper tipoAnticipoHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(tipoAnticipoHelper);
        setTipoAnticipoHelper(tipoAnticipoHelper);
        iniciarComboTipoAnticipo();
        iniciarComboTipoCuota();
        iniciarComboTipoGarantia();
        buscarRegimenLaboral();
        buscarRubroIngreso();
        iniciarComboDias();
        iniciarComboMeses();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    public TipoAnticipoControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            boolean validado = validar();
            if (!validado) {
                return null;
            }
            if (tipoAnticipoHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    anticipoServicio.guardarTipoAnticipo(tipoAnticipoHelper.getTipoAnticipo());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                anticipoServicio.actualizarTipoAnticipo(tipoAnticipoHelper.getTipoAnticipo());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * true si todas las validaciones salieron ok
     *
     * @return
     */
    private boolean validar() {
        boolean ok = true;
        if (!validarRangoDias() || !validarRangoMes() || !validarRangoPlazo() || !validarRangoTechoCuota() || !validarRangoDiciembre()) {
            ok = false;
        }
        return ok;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(tipoAnticipoHelper.getTipoAnticipoEditDelete());
            TipoAnticipo re = (TipoAnticipo) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            tipoAnticipoHelper.setTipoAnticipo(re);
            tipoAnticipoHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        tipoAnticipoHelper.setTipoAnticipo(new TipoAnticipo());
        iniciarDatosEntidad(tipoAnticipoHelper.getTipoAnticipo(), Boolean.TRUE);
        tipoAnticipoHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            anticipoServicio.eliminarTipoAnticipo(tipoAnticipoHelper.getTipoAnticipoEditDelete());
            tipoAnticipoHelper.getListaTipoAnticipo().
                    remove(tipoAnticipoHelper.getTipoAnticipoEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            tipoAnticipoHelper.getListaTipoAnticipo().clear();
            tipoAnticipoHelper.setListaTipoAnticipo(
                    anticipoServicio.listarTodasTipoAnticiposPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para validar si ya existe el codigo.
     *
     * @return haycodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean haycodigo = true;
        try {
            tipoAnticipoHelper.getListaTipoAnticipoCodigo().clear();
            tipoAnticipoHelper.setListaTipoAnticipoCodigo(
                    anticipoServicio.listarTodosTipoAnticipoPorCodigo(
                            tipoAnticipoHelper.getTipoAnticipo().getCodigo()));
            if (tipoAnticipoHelper.getListaTipoAnticipoCodigo().isEmpty()) {
                haycodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del cddigo", ex);
        }
        return haycodigo;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo anticipo.
     */
    private void iniciarComboTipoAnticipo() {
        tipoAnticipoHelper.getListaOpcionesTipoAnticipo().clear();
        iniciarCombos(tipoAnticipoHelper.getListaOpcionesTipoAnticipo());
        for (TipoAnticipoEnum t : TipoAnticipoEnum.values()) {
            tipoAnticipoHelper.getListaOpcionesTipoAnticipo().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo cuota.
     */
    private void iniciarComboTipoCuota() {
        tipoAnticipoHelper.getListaOpcionesTipoCuota().clear();
        iniciarCombos(tipoAnticipoHelper.getListaOpcionesTipoCuota());
        for (TipoCuotaEnum t : TipoCuotaEnum.values()) {
            tipoAnticipoHelper.getListaOpcionesTipoCuota().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo garantia.
     */
    private void iniciarComboTipoGarantia() {
        tipoAnticipoHelper.getListaOpcionesTipoGarantia().clear();
        iniciarCombos(tipoAnticipoHelper.getListaOpcionesTipoGarantia());
        for (TipoGarantiaEnum t : TipoGarantiaEnum.values()) {
            tipoAnticipoHelper.getListaOpcionesTipoGarantia().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo periodo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoAnticipo(final String codigo) {
        return TipoAnticipoEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo cuota.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoCuota(final String codigo) {
        return TipoCuotaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo garantia.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoGarantia(final String codigo) {
        return TipoGarantiaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo llena las opciones para el combo de regimen laboral.
     */
    public void buscarRegimenLaboral() {
        try {
            List<RegimenLaboral> lista = regimenLaboralServicio.listarTodosRegimenVigentes();
            tipoAnticipoHelper.getListaOpcionesRegimen().clear();
            iniciarCombos(tipoAnticipoHelper.getListaOpcionesRegimen());
            for (RegimenLaboral t : lista) {
                tipoAnticipoHelper.getListaOpcionesRegimen().add(new SelectItem(t.getId(), t.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de regimen laboral", ex);
        }
    }

    public void buscarRubroIngreso() {
        try {
            List<Rubro> lista = admServicio.listarTodosRubroPorTipo(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo());
            tipoAnticipoHelper.getListaOpcionesRubro().clear();
            iniciarCombos(tipoAnticipoHelper.getListaOpcionesRubro());
            for (Rubro t : lista) {
                tipoAnticipoHelper.getListaOpcionesRubro().add(new SelectItem(t.getId(), t.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de rubros de ingresos", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de dias.
     */
    private void iniciarComboDias() {
        tipoAnticipoHelper.getListaOpcionesDias().clear();
        iniciarCombos(tipoAnticipoHelper.getListaOpcionesDias());
        for (int i = 1; i <= 31; i++) {
            tipoAnticipoHelper.getListaOpcionesDias().add(new SelectItem(i, String.valueOf(i)));
        }
    }

    /**
     * valida los rangos de valores de los campos.
     */
    public boolean validarRangoDias() {
        boolean ok = true;
        if (tipoAnticipoHelper.getTipoAnticipo().getDiaDesde().compareTo(tipoAnticipoHelper.getTipoAnticipo().getDiaHasta()) >= 0) {
            mostrarMensajeEnPantalla("El campo Día Hasta debe ser mayor al campo Día Desde", FacesMessage.SEVERITY_ERROR);
            tipoAnticipoHelper.getTipoAnticipo().setDiaHasta(null);
            ok = false;
        }
        return ok;
    }

    public boolean validarRangoMes() {
        boolean ok = true;
        if (tipoAnticipoHelper.getTipoAnticipo().getMesDesde().compareTo(tipoAnticipoHelper.getTipoAnticipo().getMesHasta()) >= 0) {
            mostrarMensajeEnPantalla("El campo Mes Hasta debe ser mayor al campo Mes Desde", FacesMessage.SEVERITY_ERROR);
            tipoAnticipoHelper.getTipoAnticipo().setMesHasta(null);
            ok = false;
        }
        return ok;
    }

    public boolean validarRangoTechoCuota() {
        boolean ok = true;
        if (tipoAnticipoHelper.getTipoAnticipo().getValorTechoCuota().compareTo(BigDecimal.ZERO) <= 0
                || tipoAnticipoHelper.getTipoAnticipo().getValorTechoCuota().compareTo(new BigDecimal(100)) > 0) {
            mostrarMensajeEnPantalla("El campo Porcentaje para la Cuota debe estar entre 1 y 100 ", FacesMessage.SEVERITY_ERROR);
            tipoAnticipoHelper.getTipoAnticipo().setValorTechoCuota(null);
            ok = false;
        }
        return ok;
    }

    public boolean validarRangoDiciembre() {
        boolean ok = true;
        if (tipoAnticipoHelper.getTipoAnticipo().getPorcentajeDescuentoDiciembre().compareTo(BigDecimal.ZERO) <= 0
                || tipoAnticipoHelper.getTipoAnticipo().getPorcentajeDescuentoDiciembre().compareTo(new BigDecimal(100)) > 0) {
            mostrarMensajeEnPantalla("El campo Porcentaje Descuento de diciembre debe estar entre 1 y 100 ", FacesMessage.SEVERITY_ERROR);
            tipoAnticipoHelper.getTipoAnticipo().setPorcentajeDescuentoDiciembre(null);
            ok = false;
        }
        return ok;
    }

    public boolean validarRangoPlazo() {
        boolean ok = true;
        if (tipoAnticipoHelper.getTipoAnticipo().getPlazoMaximoMeses() <= 0
                || tipoAnticipoHelper.getTipoAnticipo().getPlazoMaximoMeses() > 12) {
            mostrarMensajeEnPantalla("El campo Plazo en meses debe estar entre 1 y 12 ", FacesMessage.SEVERITY_ERROR);
            tipoAnticipoHelper.getTipoAnticipo().setPlazoMaximoMeses(null);
            ok = false;
        }
        return ok;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo garantia.
     */
    private void iniciarComboMeses() {
        tipoAnticipoHelper.getListaOpcionesMeses().clear();
        iniciarCombos(tipoAnticipoHelper.getListaOpcionesMeses());
        for (int i = 1; i <= UtilFechas.MESES_EN_ANIO; i++) {
            tipoAnticipoHelper.getListaOpcionesMeses().add(new SelectItem(i, i + " - " + UtilFechas.obtenerNombreMes(i)));
        }
    }

    public void conciliarPorcentajeValorAnticipo() {
        tipoAnticipoHelper.getTipoAnticipo().setValorTechoAnticipo(null);
    }

    /**
     * @return the tipoAnticipoHelper
     */
    public TipoAnticipoHelper getTipoAnticipoHelper() {
        return tipoAnticipoHelper;
    }

    /**
     * @param tipoAnticipoHelper the tipoAnticipoHelper to set
     */
    public void setTipoAnticipoHelper(TipoAnticipoHelper tipoAnticipoHelper) {
        this.tipoAnticipoHelper = tipoAnticipoHelper;
    }
}
