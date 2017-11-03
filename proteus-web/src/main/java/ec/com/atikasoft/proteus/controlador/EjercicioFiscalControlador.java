/*
 *  EjercicioFiscalControlador.java
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
 *  01/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.EjercicioFiscalHelper;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "ejercicioFiscalBean")
@ViewScoped
public class EjercicioFiscalControlador extends CatalogoControlador {

    /**
     * Logger de reglas.
     */
    private Logger LOG = Logger.getLogger(EjercicioFiscalControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/ejercicio_fiscal/ejercicio_fiscal.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/ejercicio_fiscal/lista_ejercicio_fiscal.jsf";

    /**
     * Servicio de administración.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @ManagedProperty("#{ejercicioFiscalHelper}")
    private EjercicioFiscalHelper ejercicioFiscalHelper;

    public EjercicioFiscalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(ejercicioFiscalHelper);
        buscar();
    }

    /**
     * @return the ejercicioFiscalHelper
     */
    public EjercicioFiscalHelper getEjercicioFiscalHelper() {
        return ejercicioFiscalHelper;
    }

    /**
     * @param ejercicioFiscalHelper the ejercicioFiscalHelper to set
     */
    public void setEjercicioFiscalHelper(final EjercicioFiscalHelper ejercicioFiscalHelper) {
        this.ejercicioFiscalHelper = ejercicioFiscalHelper;
    }

    /**
     * 
     */
    @Override
    public void generarReporte() {
        setReporte(ReportesEnum.EJERCICIOS_FISCALES.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "REPORTE DE EJERCICIOS FISCALES");
        generarUrlDeReporte();
    }

    /**
     * 
     * @return 
     */
    @Override
    public String guardar() {
        Boolean error = false;
        try {
            if (ejercicioFiscalHelper.getEjercicioFiscal().getFirmaContador() == null
                    || ejercicioFiscalHelper.getEjercicioFiscal().getFirmaAgenteRetencion() == null) {
                error = true;
                mostrarMensajeEnPantalla("Las firmas son campos obligatorios, "
                        + "por favor cargar las imágenes correspondientes.", FacesMessage.SEVERITY_INFO);
            }

            if (ejercicioFiscalHelper.getEjercicioFiscal().getRucContador() == null
                    || ejercicioFiscalHelper.getEjercicioFiscal().getRucContador().trim().isEmpty()) {
                error = true;
                mostrarMensajeEnPantalla("El campo RUC Contador es requerido", FacesMessage.SEVERITY_INFO);
            }

            if (ejercicioFiscalHelper.getFechaEntrega() == null
                    || ejercicioFiscalHelper.getFechaEntrega().trim().isEmpty()) {
                error = true;
                mostrarMensajeEnPantalla("El campo Fecha Entrega Formulario 107 es requerido", FacesMessage.SEVERITY_INFO);
            }

            if (ejercicioFiscalHelper.getEjercicioFiscal().getFraccionBasica() == null) {
                error = true;
                mostrarMensajeEnPantalla("El campo fracción básica es requerido", FacesMessage.SEVERITY_INFO);
            }

            if (!error) {
                ejercicioFiscalHelper.getEjercicioFiscal().setFechaEntregaFormulario107(
                        UtilFechas.formatear(ejercicioFiscalHelper.getFechaEntrega())
                );

                if (ejercicioFiscalHelper.getEsNuevo()) {
                    administracionServicio.guardarEjercicioFiscal(ejercicioFiscalHelper.getEjercicioFiscal());
                    iniciarNuevo();
                } else {
                    administracionServicio.actualizarEjercicioFiscal(ejercicioFiscalHelper.getEjercicioFiscal());
                }
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                ejercicioFiscalHelper.setNombreArchivoFirmaContador("");
                ejercicioFiscalHelper.setNombreArchivoFirmaRetencion("");
            }
            cargarImagenesFirmas();
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar el ejercicio fiscal", e);
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(ejercicioFiscalHelper.getEjercicioFiscalEditDelete());

            EjercicioFiscal ej = (EjercicioFiscal) cloneBean;
            iniciarDatosEntidad(ej, Boolean.FALSE);
            ejercicioFiscalHelper.setEjercicioFiscal(ej);
            ejercicioFiscalHelper.setEsNuevo(Boolean.FALSE);
            ejercicioFiscalHelper.setFechaEntrega(UtilFechas.formatear(ej.getFechaEntregaFormulario107()));
            cargarImagenesFirmas();
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String iniciarNuevo() {
        ejercicioFiscalHelper.setEjercicioFiscal(new EjercicioFiscal());
        iniciarDatosEntidad(ejercicioFiscalHelper.getEjercicioFiscal(), Boolean.TRUE);
        ejercicioFiscalHelper.setEsNuevo(Boolean.TRUE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String borrar() {
        try {
            administracionServicio.eliminarEjercicioFiscal(ejercicioFiscalHelper.getEjercicioFiscalEditDelete());
            ejercicioFiscalHelper.getListaEjercicioFiscal().
                    remove(ejercicioFiscalHelper.getEjercicioFiscalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String buscar() {
        try {
            ejercicioFiscalHelper.getListaEjercicioFiscal().clear();
            ejercicioFiscalHelper.setListaEjercicioFiscal(
                    administracionServicio.listarTodosEjercicioFiscalPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
            ejercicioFiscalHelper.setNombreArchivoFirmaContador("");
            ejercicioFiscalHelper.setNombreArchivoFirmaRetencion("");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Guarda en el ejercicio fiscal la imagen de la firma del contador
     */
    public void cargarArchivoFirmaContador(final FileUploadEvent event) {
        try {
            UploadedFile archivo = event.getFile();
            ejercicioFiscalHelper.setNombreArchivoFirmaContador(archivo.getFileName());
            ejercicioFiscalHelper.getEjercicioFiscal().setFirmaContador(archivo.getContents());
            mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            Logger.getLogger(EjercicioFiscalControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Guarda en el ejercicio fiscal la imagen de la firma del agente de retencion
     */
    public void cargarArchivoFirmaRetencion(final FileUploadEvent event) {
        try {
            UploadedFile archivo = event.getFile();
            ejercicioFiscalHelper.setNombreArchivoFirmaRetencion(archivo.getFileName());
            ejercicioFiscalHelper.getEjercicioFiscal().setFirmaAgenteRetencion(archivo.getContents());
            mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            Logger.getLogger(EjercicioFiscalControlador.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Muestra en la vista las imagenes de las firmas guardadas en la BD
     */
    private void cargarImagenesFirmas() {
        try {
            if (ejercicioFiscalHelper.getEjercicioFiscal().getFirmaContador() != null) {
                File fileFirmaContador = File.createTempFile("temp", ".jpg");
                UtilArchivos.copy(ejercicioFiscalHelper.getEjercicioFiscal().getFirmaContador(), fileFirmaContador);
                ejercicioFiscalHelper.setFirmaContadorImage(new DefaultStreamedContent(
                        new FileInputStream(fileFirmaContador), "image/jpg"));
            }

            if (ejercicioFiscalHelper.getEjercicioFiscal().getFirmaAgenteRetencion() != null) {
                File fileFirmaAgenteRetencion = new File("firmaAgenteRetencion");
                UtilArchivos.copy(ejercicioFiscalHelper.getEjercicioFiscal().getFirmaAgenteRetencion(),
                        fileFirmaAgenteRetencion);
                ejercicioFiscalHelper.setFirmaRetencionImage(new DefaultStreamedContent(
                        new FileInputStream(fileFirmaAgenteRetencion), "image/jpg"));
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Error cargando imágenes de firmas", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error cargando imágenes de firmas", e);
        }
    }
}
