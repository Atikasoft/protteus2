/*
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.ConsultaBienesAsignadosHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "consultaBienesAsignadosBean")
@ViewScoped
public class ConsultaBienesAsignadosControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ConsultaBienesAsignadosControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_INDEX = "/pages/portal_rrhh.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaBienesAsignadosHelper}")
    private ConsultaBienesAsignadosHelper consultaBienesAsignadosHelper;

    @Override
    @PostConstruct
    public void init() {
        consultaBienesAsignadosHelper.setUsuario(obtenerUsuarioConectado());
    }

    /**
     * recupera bienes del servidor.
     *
     * @return
     */
    public String buscarDetalleBienes() {
        try {

            // consultar el webservices

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de bienes", ex);
        }
        return null;
    }


    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_INDEX;
    }

    /**
     * @return the consultaBienesAsignadosHelper
     */
    public ConsultaBienesAsignadosHelper getConsultaBienesAsignadosHelper() {
        return consultaBienesAsignadosHelper;
    }

    /**
     * @param consultaBienesAsignadosHelper the consultaBienesAsignadosHelper to set
     */
    public void setConsultaBienesAsignadosHelper(ConsultaBienesAsignadosHelper consultaBienesAsignadosHelper) {
        this.consultaBienesAsignadosHelper = consultaBienesAsignadosHelper;
    }
}
