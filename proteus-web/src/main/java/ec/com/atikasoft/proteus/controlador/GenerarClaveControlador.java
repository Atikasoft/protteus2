/**
 * GenerarClaveControlador.java PROTEUS V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the
 * confidential and proprietary information of Proteus ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * PROTEUS.
 *
 * PROTEUS Quito - Ecuador
 *
 * 29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.GenerarClaveHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * GenerarClaveControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "generarClaveBean")
@ViewScoped
public class GenerarClaveControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(GenerarClaveControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/clave/genera_clave.jsf";

    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{generarClaveHelper}")
    private GenerarClaveHelper generarClaveHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        getGenerarClaveHelper().setNombreServidor(null);
        getGenerarClaveHelper().setNumeroIdentificacion(null);
        generarClaveHelper.setModalidadLaboral(null);
        generarClaveHelper.setUnidadOrganizacional(null);
        getGenerarClaveHelper().getListaServidores().clear();
        generarClaveHelper.setServidoresSinClave(Boolean.FALSE);
        generarClaveHelper.getListaServidorError().clear();
    }

    /**
     * metodo que busca el servidor por nombre y/o número de identificacion.
     *
     * @return
     */
    public String buscarServidor() {
        try {
             generarClaveHelper.getListaServidores().clear();
            if (generarClaveHelper.getServidoresSinClave() == null || !generarClaveHelper.getServidoresSinClave()) {
                if (generarClaveHelper.getNombreServidor().length() < 3 && !generarClaveHelper.getNombreServidor().isEmpty()) {
                    generarClaveHelper.getListaServidores().clear();
                    mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                    return null;
                }

                if (generarClaveHelper.getNumeroIdentificacion().length() < 3 && !generarClaveHelper.getNumeroIdentificacion().isEmpty()) {
                    generarClaveHelper.getListaServidores().clear();
                    mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                    return null;
                }

                if (generarClaveHelper.getNombreServidor().isEmpty() && generarClaveHelper.getNumeroIdentificacion().isEmpty()) {
                    mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                    return null;
                }
            }
            if (!generarClaveHelper.getNombreServidor().trim().isEmpty()) {
                generarClaveHelper.setNombreServidor(generarClaveHelper.getNombreServidor().toUpperCase());
            }
           
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(generarClaveHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(generarClaveHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIntitucionEjercicioFiscalId(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());
            ser.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            if (generarClaveHelper.getServidoresSinClave() != null && generarClaveHelper.getServidoresSinClave()) {
                ser.setConClave(Boolean.FALSE);
            } else {
                ser.setConClave(null);
            }
            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            generarClaveHelper.setListaServidores(lista);
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores SIN CLAVE:{0}", generarClaveHelper.getListaServidores().size());

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de servidores", ex);
        }
        return null;
    }

    /**
     *
     * @param s
     * @return
     */
    public String iniciarEdicion(DistributivoDetalle s) {

        if (s != null && s.getServidor() != null) {
            generarClaveHelper.setServidor(s.getServidor());
            generarClaveHelper.setEsNuevo(Boolean.TRUE);
            generarClaveHelper.setModalidadLaboral(s.getDistributivo().getModalidadLaboral().getNombre());
            generarClaveHelper.setUnidadOrganizacional(s.getDistributivo().getUnidadOrganizacional().getRuta());
            iniciarDatosEntidad(generarClaveHelper.getServidor(), Boolean.FALSE);
            ejecutarComandoPrimefaces("editDialog.show();");
        } else {
            LOG.info(" Ningun servidor seleccionado...");

        }
        return null;
    }

    /**
     * Permite generar y registrar la nueva clave para el servidor,
     * adicionalmente envia mail de notificacion de cambio de clave.
     *
     * @return
     */
    public String guardar() {
        if (generarClaveHelper.getServidor() == null) {
            mostrarMensajeEnPantalla("Ningún servidor seleccionado", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (!correoValido(generarClaveHelper.getServidor().getMail())) {
            mostrarMensajeEnPantalla("Sin cuenta de correo electrónico válida", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        try {
            servidorServicio.generarClaves(generarClaveHelper.getServidor(), obtenerUsuarioConectado().getUsuario(), Boolean.FALSE);
            mostrarMensajeEnPantalla("Clave generada con éxito", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Error al generar la clave", FacesMessage.SEVERITY_ERROR);
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private boolean correoValido(final String correo){
        if(correo == null || correo.isEmpty())
            return false;
        String pattern = "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(correo);
        return m.matches();
    }

    /**
     * Generación masiva de claves. Se genera de todo el distributivo detalles.
     *
     * @return
     */
    public String guardarMasivo() {
        try {
//            generarClaveHelper.getListaServidorError().clear();
//            generarClaveHelper.setListaServidorError(
                    servidorServicio.generarClavesMasivamente(obtenerUsuarioConectado());
            mostrarMensajeEnPantalla("Claves generadas con éxito para los servidores del MDMQ", FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla("Error al generar las claves de los servidores del MDMQ", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al generar la clave ", ex);
        }
        return null;
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
     * @return the generarClaveHelper
     */
    public GenerarClaveHelper getGenerarClaveHelper() {
        return generarClaveHelper;
    }

    /**
     * @param generarClaveHelper the generarClaveHelper to set
     */
    public void setGenerarClaveHelper(GenerarClaveHelper generarClaveHelper) {
        this.generarClaveHelper = generarClaveHelper;
    }

}
