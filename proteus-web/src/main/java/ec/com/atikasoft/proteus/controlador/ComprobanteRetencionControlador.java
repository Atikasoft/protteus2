/*
 *  ComprobanteRetencionControlador.java
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
 *  30/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.ComprobanteRetencioHelper;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.ComprobanteRetencionImpuestoRenta;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ComprobanteRetencionIRServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
@ManagedBean(name = "comprobanteRetencionBean")
@ViewScoped
public class ComprobanteRetencionControlador extends CatalogoControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(ComprobanteRetencionControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/comprobante_retencion/comprobante.jsf?faces-redirect=true";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/comprobante_retencion/lista_comprobante.jsf?faces-redirect=true";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{comprobanteRetencionHelper}")
    private ComprobanteRetencioHelper comprobanteRetencioHelper;

    @EJB
    private AdministracionServicio administracionServicio;

    @EJB
    private ComprobanteRetencionIRServicio comprobanteRetencionIRServicio;

    /**
     * Para descarga de Archivo
     */
    private StreamedContent file;

    /**
     * Costructor.
     */
    public ComprobanteRetencionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        iniciarCatalogos();
    }

    @Override
    public String iniciarNuevo() {
        ComprobanteRetencionImpuestoRenta formulario = ComprobanteRetencionImpuestoRenta.comprobanteRetencionImpuestoRentaFabrica();
        formulario.setEjercicioFiscal(comprobanteRetencioHelper.getEjercicioFiscalActual());
        formulario.setFechaRegistro(new Date());
        iniciarDatosEntidad(formulario, Boolean.TRUE);
        comprobanteRetencioHelper.setEsNuevo(Boolean.TRUE);
        comprobanteRetencioHelper.setComprobanteRetencionImpuestoRenta(formulario);
        comprobanteRetencioHelper.setSoloVer(false);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRentaTransaccional());
            ComprobanteRetencionImpuestoRenta d = (ComprobanteRetencionImpuestoRenta) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            comprobanteRetencioHelper.setComprobanteRetencionImpuestoRenta(d);
            comprobanteRetencioHelper.setEsNuevo(Boolean.FALSE);
            comprobanteRetencioHelper.setSoloVer(!d.getEjercicioFiscal().getId().equals(comprobanteRetencioHelper.getEjercicioFiscalActual().getId()));
            return FORMULARIO_ENTIDAD;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        String nombre = comprobanteRetencioHelper.getNombreServidor();
        String numero = comprobanteRetencioHelper.getNumeroIdentificacionServidor();
        Long ef = comprobanteRetencioHelper.getEjercicioFiscal();
        if (ef == null) {
            mostrarMensajeEnPantalla("El Ejercicio Fiscal es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        try {
            List<ComprobanteRetencionImpuestoRenta> resultados = comprobanteRetencionIRServicio.busquedaComprobanteRetencion(ef, nombre, numero);
            comprobanteRetencioHelper.getListaComprobanteRetencionImpuestoRenta().clear();
            comprobanteRetencioHelper.getListaComprobanteRetencionImpuestoRenta().addAll(resultados);
        } catch (ServicioException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla("Ocurrió un error al realizar la búsqueda", FacesMessage.SEVERITY_ERROR);
        }
        return LISTA_ENTIDAD;
    }

    public void iniciarBusqueda() {
        comprobanteRetencioHelper.setServidorSeleccionado(null);
        comprobanteRetencioHelper.setNumeroIdentificacionServidor(null);
        comprobanteRetencioHelper.getListaServidores().clear();
    }

    public void buscarServidor() {
        String numeroIdentificacion = comprobanteRetencioHelper.getNumeroIdentificacionServidor();
        String nombres = comprobanteRetencioHelper.getNombreServidor();
        comprobanteRetencioHelper.getListaServidores().clear();
        try {
            List<Servidor> l = administracionServicio.buscarServidorPorNombreYNumeroIdentificacionDistributivo(nombres, numeroIdentificacion, comprobanteRetencioHelper.getEjercicioFiscalActual().getId());
            if (l != null && !l.isEmpty()) {
                comprobanteRetencioHelper.getListaServidores().addAll(l);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla("Ocurrió un error al realizar la búsqueda", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void seleccionarServidor() {
        Servidor s = comprobanteRetencioHelper.getServidorSeleccionado();
        String nombre = comprobanteRetencioHelper.getNombreServidor();
        String numero = comprobanteRetencioHelper.getNumeroIdentificacionServidor();
        Long ef = comprobanteRetencioHelper.getEjercicioFiscal();
        comprobanteRetencioHelper.setComprobanteRetencionImpuestoRentaTransaccional(null);
        try {
            List<ComprobanteRetencionImpuestoRenta> resultados = comprobanteRetencionIRServicio.busquedaComprobanteRetencion(ef, nombre, numero);
            comprobanteRetencioHelper.setTieneFormulario(false);
            for (ComprobanteRetencionImpuestoRenta ft : resultados) {
                if (ft.getServidor().getId().equals(s.getId())) {
                    comprobanteRetencioHelper.setTieneFormulario(true);
                    comprobanteRetencioHelper.setComprobanteRetencionImpuestoRentaTransaccional(ft);
                    break;
                }
            }
            if (comprobanteRetencioHelper.getTieneFormulario()) {
                ejecutarComandoPrimefaces("cargarFormularioPopUp.show();");
            } else {
                utilizarServidor();
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla("Ocurrió un error seleccionado al servidor", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void utilizarServidor() {
        if (!comprobanteRetencioHelper.getTieneFormulario()) {
            Servidor s = comprobanteRetencioHelper.getServidorSeleccionado();
            comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().setServidor(s);
        } else {
            iniciarEdicion();
            actualizarComponente(":j_idt32:panelFormulario");
        }
        ejecutarComandoPrimefaces("editDialog.hide();");
    }

    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            comprobanteRetencioHelper.setArchivoCargado(file);
            InputStream in = file.getInputstream();
            String nombreArchivo = file.getFileName();
            int indice = nombreArchivo.lastIndexOf(".");
            String nombreArchivoSinExt = nombreArchivo.substring(0, indice);
            String extencionSinPunto = nombreArchivo.substring(indice + 1);
            InputStream stream = new ByteArrayInputStream(file.getContents());
            String extencion = nombreArchivo.substring(indice);
            File tempFile = UtilArchivos.getFileFromBytes(in, nombreArchivo, extencion);
            if (tempFile != null) {
                File f = tempFile;
                comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().setArchivo(new Archivo());
                iniciarDatosEntidad(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().getArchivo(), Boolean.TRUE);
                comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().getArchivo().setArchivo(UtilArchivos.getBytesFromFile(f));
                comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().getArchivo().setNombre(nombreArchivo);
                comprobanteRetencioHelper.setNombreArchivo(nombreArchivo);
                comprobanteRetencioHelper.setArchivoFile(UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt, extencionSinPunto));
                mostrarMensajeEnPantalla("El Archvio se subió correctamente", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
            mostrarMensajeEnPantalla("Ocurrió un error subiendo el Archivo", FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Descarga del archivo del formulario
     *
     * @return
     */
    public void descargarArchivo() {
        Archivo archivo = comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().getArchivo();
        file = null;
        if (archivo != null) {
            try {
                InputStream stream = new ByteArrayInputStream(archivo.getArchivo());

                //Para el mime type
                String nombreArchivo = archivo.getNombre();
                int indice = nombreArchivo.lastIndexOf(".");
                String nombreArchivoSinExt = nombreArchivo.substring(0, indice);
                String extencionSinPunto = nombreArchivo.substring(indice + 1);
                File t = UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt, extencionSinPunto);
                Path source = Paths.get(t.getPath());
                String mimeType = Files.probeContentType(source);
                stream = new ByteArrayInputStream(archivo.getArchivo());
                file = new DefaultStreamedContent(stream, mimeType, archivo.getNombre());
            } catch (IOException ex) {
                Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String guardar() {
        if (validarFormulario()) {
            if (comprobanteRetencioHelper.getEsNuevo()) {
                try {
                    comprobanteRetencionIRServicio.crearComprobanteRetencionImpuestoRenta(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta());
                    mostrarMensajeEnPantalla("Formulario 107 guardado correctamente", FacesMessage.SEVERITY_INFO);
                    ComprobanteRetencionImpuestoRenta comprobanteRetencionImpuestoRenta = comprobanteRetencionIRServicio.buscar(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta().getId());
                    comprobanteRetencioHelper.setComprobanteRetencionImpuestoRentaTransaccional(comprobanteRetencionImpuestoRenta);
                    iniciarEdicion();
                    actualizarComponente(":j_idt32:panelFormulario");
                } catch (ServicioException ex) {
                    Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
                    mostrarMensajeEnPantalla("Ocurrió un error guardando el Formulario 107", FacesMessage.SEVERITY_ERROR);
                }
            } else {
                try {
                    comprobanteRetencionIRServicio.actualizarComprobanteRetencionImpuestoRenta(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta());
                    mostrarMensajeEnPantalla("Formulario 107 actualizado correctamente", FacesMessage.SEVERITY_INFO);
                } catch (ServicioException ex) {
                    Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
                    mostrarMensajeEnPantalla("Ocurrió un error actualizando el Formulario 107", FacesMessage.SEVERITY_ERROR);
                }
            }
        }
        return null;
    }

    private boolean validarFormulario() {
        ComprobanteRetencionImpuestoRenta formulario = comprobanteRetencioHelper.getComprobanteRetencionImpuestoRenta();
        boolean ok = true;
        if (formulario.getEjercicioFiscal() == null) {
            ok = false;
            mostrarMensajeEnPantalla("(102) El Ejercicio Fiscal es requerido", FacesMessage.SEVERITY_ERROR);
        }
        if (formulario.getFechaEntrega() == null) {
            ok = false;
            mostrarMensajeEnPantalla("(103) La Fecha de Entrega es requerida", FacesMessage.SEVERITY_ERROR);
        }
        if (cadenaVacia(formulario.getRucEmpleador())) {
            ok = false;
            mostrarMensajeEnPantalla("(105) El RUC del Empleador es requerido", FacesMessage.SEVERITY_ERROR);
        }
        if (cadenaVacia(formulario.getNombreEmpleador())) {
            ok = false;
            mostrarMensajeEnPantalla("(106) El Nombre del Empleador es requerido", FacesMessage.SEVERITY_ERROR);
        }
        if (formulario.getServidor() == null) {
            ok = false;
            mostrarMensajeEnPantalla("(201) El Servidor es requerido", FacesMessage.SEVERITY_ERROR);
        }
        if (bigDecimalVacio(formulario.getSueldosSalarios())) {
            ok = false;
            mostrarMensajeEnPantalla("(301) El valor de Saldos y Salarios es requerido", FacesMessage.SEVERITY_ERROR);
        }
        if (cadenaVacia(formulario.getRucContador())) {
            ok = false;
            mostrarMensajeEnPantalla("(199) El RUC del Contador es requerido", FacesMessage.SEVERITY_ERROR);
        }
        return ok;
    }

    /**
     * se toma vacio en caso de 0 o null
     *
     * @return
     */
    private boolean bigDecimalVacio(final BigDecimal valor) {
        if (valor == null) {
            return true;
        }
        return valor.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * se toma vacia null o vacia
     *
     * @param cadena
     * @return
     */
    private boolean cadenaVacia(final String cadena) {
        if (cadena == null) {
            return true;
        }
        return cadena.trim().isEmpty();
    }

    @Override
    public String borrar() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(comprobanteRetencioHelper.getComprobanteRetencionImpuestoRentaTransaccional());
            ComprobanteRetencionImpuestoRenta d = (ComprobanteRetencionImpuestoRenta) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            comprobanteRetencionIRServicio.eliminarComprobanteRetencionImpuestoRenta(d);
            mostrarMensajeEnPantalla("Formulario 107 eliminado correctamente", FacesMessage.SEVERITY_INFO);
            return LISTA_ENTIDAD;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        mostrarMensajeEnPantalla("Ocurrió un error eliminando el Formulario 107", FacesMessage.SEVERITY_ERROR);
        return null;
    }

    private void iniciarCatalogos() {
        try {
            comprobanteRetencioHelper.getListaServidores().clear();
            List<EjercicioFiscal> efs = administracionServicio.listarTodosEjercicioFiscalVigentes();
            EjercicioFiscal efAtual = administracionServicio.buscarEjercicioFiscalActivo();
            comprobanteRetencioHelper.setEjercicioFiscalActual(efAtual);
            comprobanteRetencioHelper.setEjercicioFiscal(efAtual.getId());
            comprobanteRetencioHelper.getListaEjercicioFiscal().clear();
            for (EjercicioFiscal ef : efs) {
                comprobanteRetencioHelper.getListaEjercicioFiscal().add(new SelectItem(ef.getId(), ef.getNombre()));
            }

            comprobanteRetencioHelper.setMinFecha(comprobanteRetencioHelper.getEjercicioFiscalActual().getFechaInicio());
            comprobanteRetencioHelper.setMaxFecha(comprobanteRetencioHelper.getEjercicioFiscalActual().getFechaFin());
            comprobanteRetencioHelper.setNombreServidor(null);
            comprobanteRetencioHelper.setNumeroIdentificacionServidor(null);
        } catch (ServicioException ex) {
            Logger.getLogger(ComprobanteRetencionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ComprobanteRetencioHelper getComprobanteRetencioHelper() {
        return comprobanteRetencioHelper;
    }

    public void setComprobanteRetencioHelper(ComprobanteRetencioHelper comprobanteRetencioHelper) {
        this.comprobanteRetencioHelper = comprobanteRetencioHelper;
    }

    public StreamedContent getFile() {
        return file;
    }
}
