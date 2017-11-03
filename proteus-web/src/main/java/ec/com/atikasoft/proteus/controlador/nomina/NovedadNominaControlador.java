/*
 *  NovedadNominaControlador.java
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
 *  14/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.nomina;

import ec.com.atikasoft.proteus.controlador.*;
import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.NominaNovedadHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * NovedadNominaControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "novedadNominaBean")
@ViewScoped
public class NovedadNominaControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(GastoPersonalControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_NOVEDADES_NOMINA = "/pages/procesos/nomina/novedad_nomina.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_NOMINA = "/pages/procesos/nomina/lista_nominas.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_NOMINA_DIFERENCIADA = "/pages/procesos/nomina/lista_nominas_diferenciada.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{nominaNovedadHelper}")
    private NominaNovedadHelper nominaNovedadHelper;

    /**
     * Constructor por defecto.
     */
    public NovedadNominaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
    }

    /**
     * Actualiza los valores de las novedades.
     *
     * @return
     */
    public String guardar() {
        try {
            nominaNovedadHelper.setAceptaCambios(false);
            if (!nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados().isEmpty()) {
                for (NovedadDetalle det : nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados()) {
                    iniciarDatosEntidad(det, Boolean.FALSE);
                    administracionServicio.actualizarNovedadDetalle(det);
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO_VARIOS, FacesMessage.SEVERITY_INFO);
                ejecutarComandoPrimefaces("dlgDetalleNovedad.hide();");
            } else {
                mostrarMensajeEnPantalla("Novedad sin registros editados", FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Permite buscar los detalles de las novedades de una nomina.
     *
     * @param alCargarPopUp
     * @return
     */
    public String buscarNovedadesDetalles(boolean alCargarPopUp) {
        if (alCargarPopUp) {
            nominaNovedadHelper.setAceptaCambios(false);
            nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados().clear();
        }

        nominaNovedadHelper.getNovedadNominaVO().setTotalValor(BigDecimal.ZERO);
        nominaNovedadHelper.getNovedadNominaVO().setTotalValorDescontado(BigDecimal.ZERO);
        nominaNovedadHelper.getNovedadNominaVO().setTotalValorCalculado(BigDecimal.ZERO);
        if (!nominaNovedadHelper.getNovedadNominaVO().getListaNovedades().isEmpty()
                && nominaNovedadHelper.getNovedadNominaVO().getNovedad() != null) {
            try {
                nominaNovedadHelper.getNovedadNominaVO().setListaNovedadesDetalles(
                        administracionServicio.listarTodosNovedadDetalleNovedadId(nominaNovedadHelper.getNovedadNominaVO().
                                getNovedad().getId()));
                for (NovedadDetalle det : nominaNovedadHelper.getNovedadNominaVO().getListaNovedadesDetalles()) {
                    if (!nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados().isEmpty()) {
                        det.setValor(obtenerValorEditado(det.getId()));
                        if (det.getValor() == null) {
                            det.setValor(det.getValor());
                        }
                    }
                    nominaNovedadHelper.getNovedadNominaVO().setTotalValor(nominaNovedadHelper.getNovedadNominaVO().getTotalValor().add(det.getValor()));
                    nominaNovedadHelper.getNovedadNominaVO().setTotalValorDescontado(nominaNovedadHelper.getNovedadNominaVO().getTotalValorDescontado().add(det.getValorDescontado() == null ? BigDecimal.ZERO : det.getValorDescontado()));
                    nominaNovedadHelper.getNovedadNominaVO().setTotalValorCalculado(nominaNovedadHelper.getNovedadNominaVO().getTotalValorCalculado().add(det.getValorCalculado() == null ? BigDecimal.ZERO : det.getValorCalculado()));
                }
                nominaNovedadHelper.getNovedadNominaVO().setTotalRegistros(nominaNovedadHelper.getNovedadNominaVO().getListaNovedadesDetalles().size());
                ejecutarComandoPrimefaces("dlgDetalleNovedad.show();");
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error al procesar la busqueda DE Novedades Detalles", ex);
            }
        }
        return null;
    }

    /**
     * Permite obtener el nuevo valor de un registro editado.
     *
     * @param idDetalle
     * @return
     */
    private BigDecimal obtenerValorEditado(Long idDetalle) {
        System.out.println("id del detalle...." + idDetalle);
        for (NovedadDetalle det : nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados()) {
            if (det.getId().equals(idDetalle)) {
                System.out.println("id del detalle=>" + idDetalle + " valor:" + det.getValor());
                return det.getValor();
            }
        }
        return null;
    }

    /**
     * Agrega a la lista de valores editados para su registro respectivo.
     *
     * @param det
     */
    public void esValorDetalleEditado(NovedadDetalle det) {
        try {
            System.out.println(" valor .....:" + det.getValor()
                    + " lista:" + nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados().size());
            if (det.getValor() != null) {
                nominaNovedadHelper.getNovedadNominaVO().getListaNovedadDetalleEditados().add(det);
            } else {
                mostrarMensajeEnPantalla("El valor es un campo numérico requerido", FacesMessage.SEVERITY_ERROR);
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Tipo de Dato no corresponde!", FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona el formateo de una fecha especifica.
     *
     * @param fecha Date
     * @return String
     */
    public final String obtenerFechaFormateada(final Date fecha) {
        if (fecha != null) {
            return UtilFechas.formatearLargo(fecha);
        } else {
            return "";
        }
    }

    /**
     * Permite eliminar una Novedade de la Nomina.
     *
     * @return
     */
    public String borrarNovedad() {

        try {
            if (nominaNovedadHelper.getNovedadNominaVO().getNovedad() != null) {
                nominaNovedadHelper.getNovedadNominaVO().getListaNovedades().remove(nominaNovedadHelper.
                        getNovedadNominaVO().getNovedad());
                iniciarDatosEntidad(nominaNovedadHelper.getNovedadNominaVO().getNovedad(), Boolean.FALSE);
                administracionServicio.eliminarNovedad(nominaNovedadHelper.getNovedadNominaVO().getNovedad());
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion de la novedad", ex);
        }

        return FORMULARIO_NOVEDADES_NOMINA;
    }

    /**
     * Permite eliminar un detalle de la Novedade de la Nomina.
     *
     * @return
     */
    public String borrarNovedadDetalle() {
        try {
            if (nominaNovedadHelper.getNovedadNominaVO().getNovedadDetalle() != null) {
                System.out.println(" eliminado... " + nominaNovedadHelper.getNovedadNominaVO().getNovedadDetalle().
                        getValor()
                        + " ... " + nominaNovedadHelper.getNovedadNominaVO().getNovedadDetalle().getNovedad().
                        getDatoAdicional().getNombre());
                nominaNovedadHelper.getNovedadNominaVO().getListaNovedadesDetalles().remove(nominaNovedadHelper.
                        getNovedadNominaVO().getNovedadDetalle());
                iniciarDatosEntidad(nominaNovedadHelper.getNovedadNominaVO().getNovedadDetalle(), Boolean.FALSE);
                administracionServicio.eliminarNovedadDetalle(
                        nominaNovedadHelper.getNovedadNominaVO().getNovedadDetalle());
                buscarNovedadesDetalles(false);
                mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion de detalles de la novedad", ex);
        }

        return FORMULARIO_NOVEDADES_NOMINA;
    }

    /**
     * Regresa al listado general.
     */
    public String irListaNomina() {
        return LISTA_NOMINA;
    }

    /**
     * Regresa al listado general.
     */
    public String irListaNominaEspecial() {
        return LISTA_NOMINA_DIFERENCIADA;
    }

    /**
     * @return the nominaNovedadHelper
     */
    public NominaNovedadHelper getNominaNovedadHelper() {
        return nominaNovedadHelper;
    }

    /**
     * @param nominaNovedadHelper the nominaNovedadHelper to set
     */
    public void setNominaNovedadHelper(NominaNovedadHelper nominaNovedadHelper) {
        this.nominaNovedadHelper = nominaNovedadHelper;
    }
}
