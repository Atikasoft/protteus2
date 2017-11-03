/*
 *  PeriodoNominaControlador.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.PeriodoNominaHelper;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "periodoNominaBean")
@ViewScoped
public class PeriodoNominaControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(CatalogoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/nomina/lista_nomina.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{periodoNominaHelper}")
    private PeriodoNominaHelper periodoNominaHelper;

    /**
     * Constructor por defecto.
     */
    public PeriodoNominaControlador() {
        super();
    }

    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        try {            
            periodoNominaHelper.getListaPeriodoNominas().clear();
            periodoNominaHelper.setListaPeriodoNominas(
                    admServicio.listarTodosPeriodoNominaVigente());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(periodoNominaHelper);
        setPeriodoNominaHelper(periodoNominaHelper);
        buscar();
    }

    /**
     * @return the periodoNominaHelper
     */
    public PeriodoNominaHelper getPeriodoNominaHelper() {
        return periodoNominaHelper;
    }

    /**
     * @param periodoNominaHelper the periodoNominaHelper to set
     */
    public void setPeriodoNominaHelper(PeriodoNominaHelper periodoNominaHelper) {
        this.periodoNominaHelper = periodoNominaHelper;
    }
}
