/*
 *  ParametroInstitucionalControlador.java
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
 *  11/12/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ParametroInstitucionalHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionalEnumeracionEnum;
import ec.com.atikasoft.proteus.enums.TipoParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucionalCatalogo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import java.util.Date;
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
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "parametroInstitucionalBean")
@ViewScoped
public class ParametroInstitucionalControlador extends CatalogoControlador {

    /**
     * loggwr de la clase.
     */
    private Logger LOG = Logger.getLogger(ClaseControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD
            = "/pages/administracion/parametro_institucional/parametro_institucional.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD
            = "/pages/administracion/parametro_institucional/lista_parametro_institucional.jsf";

    /**
     * Servicio de administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Seleccione.
     */
    public static final String SELECCIONE = "Seleccione...";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{parametroInstitucionalHelper}")
    private ParametroInstitucionalHelper parametroInstitucionalHelper;

//    public ParametroInstitucionalControlador() {
//        super();        
//    }
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(parametroInstitucionalHelper);
        buscar();
//        iniciarComboParametro();

    }

    /**
     * regresar a la pantalla de tramite.
     *
     * @return Regla de navegacion
     */
    public String pantallaParametroInstitucional() {
        String regresar = null;
        try {
            regresar = FORMULARIO_ENTIDAD;
        } catch (Exception e) {
            error(getClass().getName(), "Error al regresar a la pantalla tramite", e);
        }
        return regresar;
    }

    /**
     * regresar a la pantalla de tramite.
     *
     * @return Regla de navegacion
     */
    public String regresarLista() {
        reglaNavegacionDirecta(ParametroInstitucionalControlador.LISTA_ENTIDAD);
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo parametro.
     */
    private void iniciarComboParametro() {
        parametroInstitucionalHelper.getListaTipoParametro().clear();
        for (ParametroInstitucionalEnumeracionEnum pi : ParametroInstitucionalEnumeracionEnum.values()) {
            parametroInstitucionalHelper.getListaTipoParametro().add(new SelectItem(pi.getCodigo(), pi.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoParametro(final String codigo) {
        return TipoParametroInstitucionCatalogoEnum.obtenerDescripcion(codigo);
    }

    @Override
    public String guardar() {
        try {
            if (parametroInstitucionalHelper.getEsNuevo()) {
                administracionServicio.guardarParametroInstitucional(
                        parametroInstitucionalHelper.getArchivoCargado().getContents(),
                        parametroInstitucionalHelper.getArchivoCargado().getFileName(),
                        parametroInstitucionalHelper.getArchivoCargado().getContentType(),
                        parametroInstitucionalHelper.getArchivoCargado().getSize(),
                        obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(), parametroInstitucionalHelper.
                        getParametroInstitucional());
            } else {
                if (parametroInstitucionalHelper.getArchivoCargado() != null) {
                    administracionServicio.actualizarParametroInstitucional(
                            parametroInstitucionalHelper.getArchivoCargado().getContents(),
                            parametroInstitucionalHelper.getArchivoCargado().getFileName(),
                            parametroInstitucionalHelper.getArchivoCargado().getContentType(),
                            parametroInstitucionalHelper.getArchivoCargado().getSize(),
                            obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(),
                            parametroInstitucionalHelper.getParametroInstitucional());
                } else {
                    administracionServicio.actualizarParametroInstitucional(
                            null, "", "", 0L, obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(),
                            parametroInstitucionalHelper.getParametroInstitucional());
                }
            }
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el tipo de movimiento", e);
        }
        return null;

    }
    

    /**
     * Metodo que realiza la carga de un archivo pdf.
     */
    public void upload() {
        if (parametroInstitucionalHelper.getArchivoCargado() != null) {
            if (parametroInstitucionalHelper.getArchivoCargado().getContentType().equals("image/jpeg")
                    || parametroInstitucionalHelper.getArchivoCargado().getContentType().equals("image/png")
                    || parametroInstitucionalHelper.getArchivoCargado().getContentType().equals("image/gif")) {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoCorrectamente",
                        FacesMessage.SEVERITY_INFO);
            } else {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoNoesimagen",
                        FacesMessage.SEVERITY_ERROR);
                parametroInstitucionalHelper.setArchivoCargado(null);
            }

        }
    }

    @Override
    public String iniciarEdicion() {
        try {
            iniciarComboParametro();
            Object cloneBean
                    = BeanUtils.cloneBean(getParametroInstitucionalHelper().getParametroInstitucionalEditDelete());
            ParametroInstitucional d = (ParametroInstitucional) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            LOG.info("ENTRA A EDICION");
            getParametroInstitucionalHelper().setParametroInstitucional(d);
            parametroInstitucionalHelper.setParametroInstitucionalCatalogo(
                    administracionServicio.buscarParametroInstitucionalCatalogo(
                            d.getParametroInstitucionalCatalogo().getId()));
            parametroInstitucionalHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        reglaNavegacionDirecta(ParametroInstitucionalControlador.FORMULARIO_ENTIDAD);
        return null;
    }

    @Override
    public String iniciarNuevo() {
        getParametroInstitucionalHelper().setParametroInstitucional(new ParametroInstitucional());
        getParametroInstitucionalHelper().iniciador();
        iniciarDatosEntidad(getParametroInstitucionalHelper().getParametroInstitucional(), Boolean.TRUE);
        getParametroInstitucionalHelper().setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String buscar() {
        try {
            InstitucionEjercicioFiscal ief = administracionServicio.buscarInstitucionEjercicioFiscal(obtenerUsuarioConectado().
                    getInstitucionEjercicioFiscal().getId());
            getParametroInstitucionalHelper().getListaParametroInstitucional().clear();
            getParametroInstitucionalHelper().setListaParametroInstitucional(
                    administracionServicio.listarTodosParametroInstitucional(ief.getInstitucion().getId()));
            List<ParametroInstitucionalCatalogo> catalogos = administracionServicio.
                    buscarCatalogosSinParametrosInstitucionales(obtenerUsuarioConectado().getInstitucion().getId());
            for (ParametroInstitucionalCatalogo c : catalogos) {
                ParametroInstitucional pi = new ParametroInstitucional();
                pi.setFechaCreacion(new Date());
                pi.setInstitucion(ief.getInstitucion());
                pi.setParametroInstitucionalCatalogo(c);
                pi.setUsuarioCreacion(obtenerUsuario());
                pi.setVigente(Boolean.TRUE);
                getParametroInstitucionalHelper().getListaParametroInstitucional().add(pi);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * @return the parametroInstitucionalHelper
     */
    public ParametroInstitucionalHelper getParametroInstitucionalHelper() {
        return parametroInstitucionalHelper;
    }

    /**
     * @param parametroInstitucionalHelper the parametroInstitucionalHelper to set
     */
    public void setParametroInstitucionalHelper(final ParametroInstitucionalHelper parametroInstitucionalHelper) {
        this.parametroInstitucionalHelper = parametroInstitucionalHelper;
    }

    @Override
    public String borrar() {
        try {
            administracionServicio.eliminarParametroInstitucional(
                    parametroInstitucionalHelper.getParametroInstitucionalEditDelete());
            parametroInstitucionalHelper.getListaParametroInstitucional().
                    remove(parametroInstitucionalHelper.getParametroInstitucionalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }
}
