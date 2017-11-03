/*
 *  TramiteValidacionControlador.java
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
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.AtencionControlador;
import ec.com.atikasoft.proteus.controlador.helper.TramiteRevisionHelper;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Controlador de Tramite Validacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteRevisionBean")
@ViewScoped
public class TramiteRevisionControlador extends AtencionControlador {

    /**
     * Regla de navegacion al formulario.
     */
    public static final String PAGINA = "/pages/procesos/tramite/tramite_revision.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteRevisionHelper}")
    private TramiteRevisionHelper tramiteRevisionHelper;

    /**
     * Constructor por defecto.
     */
    public TramiteRevisionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        establecerHelper(getTramiteRevisionHelper());
    }

    /**
     * @return the tramiteRevisionHelper
     */
    public TramiteRevisionHelper getTramiteRevisionHelper() {
        return tramiteRevisionHelper;
    }

    /**
     * @param tramiteRevisionHelper the tramiteRevisionHelper to set
     */
    public void setTramiteRevisionHelper(final TramiteRevisionHelper tramiteRevisionHelper) {
        this.tramiteRevisionHelper = tramiteRevisionHelper;
    }
}
