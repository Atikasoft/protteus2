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
import ec.com.atikasoft.proteus.controlador.helper.TramiteValidacionHelper;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * TramiteValidacionControlador
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteValidacionBean")
@ViewScoped
public class TramiteValidacionControlador extends AtencionControlador {

    /**
     * Regla de navegacion al formulario.
     */
    public static final String PAGINA = "/pages/procesos/tramite/tramite_validacion.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteValidacionHelper}")
    private TramiteValidacionHelper tramiteValidacionHelper;

    /**
     * Constructor por defecto.
     */
    public TramiteValidacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        establecerHelper(getTramiteValidacionHelper());
    }

    /**
     * @return the tramiteValidacionHelper
     */
    public TramiteValidacionHelper getTramiteValidacionHelper() {
        return tramiteValidacionHelper;
    }

    /**
     * @param tramiteValidacionHelper the tramiteValidacionHelper to set
     */
    public void setTramiteValidacionHelper(final TramiteValidacionHelper tramiteValidacionHelper) {
        this.tramiteValidacionHelper = tramiteValidacionHelper;
    }
}
