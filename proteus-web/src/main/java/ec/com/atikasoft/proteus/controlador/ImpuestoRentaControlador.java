/*
 *  MetadataTablaControlador.java
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
 *  03/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ImpuestoRentaHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ImpuestoRenta;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ImpuestoRentaServicio;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
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
 * ImpuestoRenta
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "impuestoRentaBean")
@ViewScoped
public class ImpuestoRentaControlador extends CatalogoControlador {

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/impuestos_renta/impuestos_renta.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/impuestos_renta/lista_impuestos_renta.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    @EJB
    private ImpuestoRentaServicio impuestoRentaServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{impuestoRentaHelper}")
    private ImpuestoRentaHelper impuestoRentaHelper;

    /**
     * Constructor por defecto.
     */
    public ImpuestoRentaControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        cargarEjerciciosFiscales();
        preseleccional();
        buscar();
    }

    @Override
    public String buscar() {
        Long ejercicioSeleccionado = impuestoRentaHelper.getEjercicioFiscalSeleccionado();
        impuestoRentaHelper.getImpuestosRenta().clear();
        if (ejercicioSeleccionado != null) {
            List<ImpuestoRenta> lista = null;
            try {
                lista = admServicio.listarImpuestosRenta(ejercicioSeleccionado);
            } catch (ServicioException ex) {
                Logger.getLogger(ImpuestoRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (lista != null) {
                impuestoRentaHelper.getImpuestosRenta().addAll(lista);
            }
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Permite navegar desde la lista hacia el detalle
     *
     * @return
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Permite navegar desde la lista hacia el detalle
     *
     * @return
     */
    public String irFormulario() {
        return FORMULARIO_ENTIDAD;
    }

    public String guardar() {
        ImpuestoRenta impuestoRenta = impuestoRentaHelper.getImpuestoRenta();
        try {
            Long idEjercicioFiscal = impuestoRentaHelper.getEjercicioFiscalSeleccionado();
            if (idEjercicioFiscal != null) {
                EjercicioFiscal ef = admServicio.obtenerEjercicioFiscalPorId(idEjercicioFiscal);
                impuestoRenta.setEjercicioFiscal(ef);
            }
            if (validarDatosImpuesto(impuestoRenta)) {
                if (impuestoRentaHelper.getEsNuevo()) {
                    admServicio.guardarImpuestoRenta(impuestoRenta);
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else {
                    getAdmServicio().actualizarImpuestoRenta(impuestoRenta);
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            }

        } catch (Exception e) {
            error(getClass().getName(), ERROR_REGISTRO_GUARDADO, e);
        }
        return null;
    }

    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(impuestoRentaHelper.getImpuestoRentaEditDelete());
            ImpuestoRenta impuestoRenta = (ImpuestoRenta) cloneBean;
            iniciarDatosEntidad(impuestoRenta, Boolean.FALSE);
            impuestoRentaHelper.setImpuestoRenta(impuestoRenta);
            impuestoRentaHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    public String iniciarNuevo() {
        ImpuestoRenta impuestoRenta = new ImpuestoRenta();
        iniciarDatosEntidad(impuestoRenta, Boolean.TRUE);
        impuestoRentaHelper.setEsNuevo(Boolean.TRUE);
        impuestoRentaHelper.setImpuestoRenta(impuestoRenta);
        return FORMULARIO_ENTIDAD;
    }

    public String borrar() {
        try {
            ImpuestoRenta impuestoRenta = impuestoRentaHelper.getImpuestoRentaEditDelete();
            admServicio.eliminarImpuestoRenta(impuestoRenta);
            impuestoRentaHelper.getImpuestosRenta().remove(impuestoRenta);
        } catch (ServicioException ex) {
            Logger.getLogger(ImpuestoRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Iniciar la lista de ejericios fiscales
     */
    private void cargarEjerciciosFiscales() {
        iniciarCombos(impuestoRentaHelper.getEjerciciosFiscales());
        try {
            List<EjercicioFiscal> listaEjerciciosFiscales = admServicio.listarTodosEjercicioFiscalVigentes();
            for (EjercicioFiscal ef : listaEjerciciosFiscales) {
                impuestoRentaHelper.getEjerciciosFiscales().add(new SelectItem(ef.getId(), ef.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ImpuestoRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Marca el primer ejercicio fiscal como seleccionado si no existe una
     * seleccion ya hecha
     */
    private void preseleccional() {
        if (impuestoRentaHelper.getEjercicioFiscalSeleccionado() == null) {
            if (!impuestoRentaHelper.getEjerciciosFiscales().isEmpty() && impuestoRentaHelper.getEjerciciosFiscales().size() > 1) {
                impuestoRentaHelper.setEjercicioFiscalSeleccionado((Long) impuestoRentaHelper.getEjerciciosFiscales().get(1).getValue());
            }
        }
    }

    private boolean validarDatosImpuesto(final ImpuestoRenta impuestoRenta) {
        boolean conError = false;
        if (impuestoRenta.getEjercicioFiscal() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.ejercicioFiscal", FacesMessage.SEVERITY_ERROR);
            conError = true;
        }

        if (impuestoRenta.getFraccionBasica() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.fraccionBasica", FacesMessage.SEVERITY_ERROR);
            conError = true;
        } else {
            if (impuestoRenta.getFraccionBasica().compareTo(BigDecimal.ZERO) < 0) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.todoPositivo", FacesMessage.SEVERITY_ERROR);
                conError = true;
            }
        }

        if (impuestoRenta.getExcesoHasta() != null && impuestoRenta.getExcesoHasta().compareTo(BigDecimal.ZERO) < 0) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.todoPositivo", FacesMessage.SEVERITY_ERROR);
            conError = true;
        }

        if (impuestoRenta.getPorcentajeImpuestoFraccionExcedente() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.impuestoExcedente", FacesMessage.SEVERITY_ERROR);
            conError = true;
        } else {
            if (impuestoRenta.getPorcentajeImpuestoFraccionExcedente().compareTo(BigDecimal.ZERO) < 0) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.todoPositivo", FacesMessage.SEVERITY_ERROR);
                conError = true;
            }
        }

        if (impuestoRenta.getPorcentajeImpuestoSobreFraccionBasica() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.impuestoBasica", FacesMessage.SEVERITY_ERROR);
            conError = true;
        } else {
            if (impuestoRenta.getPorcentajeImpuestoSobreFraccionBasica().compareTo(BigDecimal.ZERO) < 0) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.todoPositivo", FacesMessage.SEVERITY_ERROR);
                conError = true;
            }
        }

        try {
            if (impuestoRentaServicio.validarRangoFraccionBasicaYExcesoHastaSolapado(impuestoRenta)) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.impuestoRenta.msg.requerido.rango", FacesMessage.SEVERITY_ERROR);
                conError = true;
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ImpuestoRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return !conError;
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }

    public ImpuestoRentaHelper getImpuestoRentaHelper() {
        return impuestoRentaHelper;
    }

    public void setImpuestoRentaHelper(ImpuestoRentaHelper impuestoRentaHelper) {
        this.impuestoRentaHelper = impuestoRentaHelper;
    }

    public ImpuestoRentaServicio getImpuestoRentaServicio() {
        return impuestoRentaServicio;
    }

    public void setImpuestoRentaServicio(ImpuestoRentaServicio impuestoRentaServicio) {
        this.impuestoRentaServicio = impuestoRentaServicio;
    }
}
