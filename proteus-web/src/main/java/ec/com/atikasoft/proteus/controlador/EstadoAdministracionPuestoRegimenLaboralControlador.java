/*
 *  EstadoAdministracionPuestoRegimenLaboralControlador.java
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

import ec.com.atikasoft.proteus.comparadores.ComparadorEstadoAdmiPuestoRegimenLaboralVO;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.EstadoAdministracionPuestoRegimenLaboralHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuesto;
import ec.com.atikasoft.proteus.modelo.EstadoAdministracionPuestoRegimenLaboral;
import ec.com.atikasoft.proteus.modelo.RegimenLaboral;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.RegimenLaboralServicio;
import ec.com.atikasoft.proteus.vo.EstadoAdministracionPuestoRegimenLaboralVO;
import java.util.Collections;
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

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "estadoAdministracionPuestoRegimenLaboralBean")
@ViewScoped
public class EstadoAdministracionPuestoRegimenLaboralControlador extends CatalogoControlador {

    /**
     * Helper de la clase.
     */
    @ManagedProperty("#{estadoAdministracionPuestoRegimenLaboralHelper}")
    private EstadoAdministracionPuestoRegimenLaboralHelper estadoAdministracionPuestoRegimenLaboralHelper;

    /**
     * Clase de administracion servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Clase de regimen laboral servicio.
     */
    @EJB
    private RegimenLaboralServicio regimenLaboralServicio;

    /**
     * Constructor de la clase.
     */
    public EstadoAdministracionPuestoRegimenLaboralControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setEstadoAdministracionPuestoRegimenLaboralHelper(estadoAdministracionPuestoRegimenLaboralHelper);
        setCatalogoHelper(estadoAdministracionPuestoRegimenLaboralHelper);
        estadoAdministracionPuestoRegimenLaboralHelper.setRegimenSeleccionadoId(null);
        estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO().clear();
        llenarComboRegimenes();
    }

    @Override
    public String guardar() {
        try {
            administracionServicio.guardarEstadoAdministracionPuestoRegimenLaboral(obtenerUsuarioConectado()
                    .getUsuario(),
                    estadoAdministracionPuestoRegimenLaboralHelper.getRegimenSeleccionadoId(),
                    estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el estado administracion puesto - regimen laboral", ex);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        return null;
    }

    @Override
    public String iniciarNuevo() {
        return null;
    }

    @Override
    public String borrar() {
        return null;
    }

    /**
     * Se encarga de llenar en la tabla de la vista la información correspondiente a los estados del regimen laboral
     * seleccionado
     *
     * @return
     */
    @Override
    public String buscar() {
        //estadoAdministracionPuestoRegimenLaboralHelper.getEstadosRegimenes().clear();
        estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO().clear();
        cargarEstadosRegimenes();
        return null;
    }

    /**
     * @return the estadoAdministracionPuestoRegimenLaboralHelper
     */
    public EstadoAdministracionPuestoRegimenLaboralHelper getEstadoAdministracionPuestoRegimenLaboralHelper() {
        return estadoAdministracionPuestoRegimenLaboralHelper;
    }

    /**
     * @param estadoAdministracionPuestoRegimenLaboralHelper the estadoAdministracionPuestoRegimenLaboralHelper to set
     */
    public void setEstadoAdministracionPuestoRegimenLaboralHelper(final EstadoAdministracionPuestoRegimenLaboralHelper estadoAdministracionPuestoRegimenLaboralHelper) {
        this.estadoAdministracionPuestoRegimenLaboralHelper = estadoAdministracionPuestoRegimenLaboralHelper;
    }

    /**
     * Este metodo llena el combo de regimenes laborales.
     */
    private void llenarComboRegimenes() {
        try {
            iniciarCombos(estadoAdministracionPuestoRegimenLaboralHelper.getRegimenesSelectItems());
            List<RegimenLaboral> lista = regimenLaboralServicio.listarTodosRegimenVigentes();
            for (RegimenLaboral rl : lista) {
                estadoAdministracionPuestoRegimenLaboralHelper.getRegimenesSelectItems().add(
                        new SelectItem(rl.getId(), rl.getNombre())
                );
            }
        } catch (ServicioException ex) {
            Logger.getLogger(EstadoAdministracionPuestoRegimenLaboralControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * obtiene la lista de los posibles estados administración puesto y los estados administración puestos asignados al
     * regimen seleccionado.
     */
    private void cargarEstadosRegimenes() {
        try {
            List<EstadoAdministracionPuesto> listaEstados = administracionServicio.listarTodosEstadoAdministracionPuesto();
            List<EstadoAdministracionPuestoRegimenLaboral> listaEstadosRegimenes
                    = administracionServicio.buscarEstadoAdministracionPuestoRegimenLaboralPorRegimenId(
                            estadoAdministracionPuestoRegimenLaboralHelper.getRegimenSeleccionadoId());

            estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO().clear();
            for (EstadoAdministracionPuesto eap : listaEstados) {
                EstadoAdministracionPuestoRegimenLaboralVO eaprvo = new EstadoAdministracionPuestoRegimenLaboralVO();
                eaprvo.setSeleccionado(false);
                eaprvo.setEstadoAdministracionPuesto(eap);
                estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO().add(eaprvo);
                for (EstadoAdministracionPuestoRegimenLaboral er : listaEstadosRegimenes) {
                    if (eap.getId().compareTo(er.getEstadoAdministracionPuesto().getId()) == 0) {
                        eaprvo.setSeleccionado(true);
                        eaprvo.setEstadoAdministracionPuestoRegimenLaboralId(er.getId());
                        break;
                    }
                }
            }
            Collections.sort(estadoAdministracionPuestoRegimenLaboralHelper.getListaEstadosRegimenesVO(),
                    new ComparadorEstadoAdmiPuestoRegimenLaboralVO());

        } catch (ServicioException ex) {
            Logger.getLogger(EstadoAdministracionPuestoRegimenLaboralControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
