/*
 *  AgregarServidorControlador.java
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
 *  27/11/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.PagoTiempoDevengarHelper;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.Devengamiento;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "pagoTiempoDevengarBean")
@ViewScoped
public class PagoTiempoDevengarControlador extends BaseControlador {

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD =
            "/pages/procesos/pago_tiempo_devengar/pago_tiempo_devengar.jsf";

    /**
     * Servicio de puesto.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{pagoTiempoDevengarHelper}")
    private PagoTiempoDevengarHelper pagoTiempoDevengarHelper;

    /**
     * variable contenido de archivo.
     */
    private StreamedContent file;

    /**
     * constructor de la clase.
     */
    public PagoTiempoDevengarControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
//        pagoTiempoDevengarHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
        pagoTiempoDevengarHelper.setDevengamiento(new Devengamiento());
        pagoTiempoDevengarHelper.setTipoDocumento(new ArrayList<SelectItem>());
        pagoTiempoDevengarHelper.setTipoIdentificacion(null);
        pagoTiempoDevengarHelper.setPago(Boolean.FALSE);
        pagoTiempoDevengarHelper.setNumeroIdentificacion(null);
        consultarCatalogos();
        controlSeleccionTipoDocumento();
    }

    /**
     * metodo para buscar un servidor segun su Id.
     */
    public void buscarPorCedula() {
//        pagoTiempoDevengarHelper.getDevengamiento().setArchivo(new Archivo());
////        pagoTiempoDevengarHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
//        pagoTiempoDevengarHelper.getParametrosBusqueda().setTipoDocumento(
//                pagoTiempoDevengarHelper.getTipoIdentificacion());
//        pagoTiempoDevengarHelper.getParametrosBusqueda().setNumeroDocumento(
//                pagoTiempoDevengarHelper.getNumeroIdentificacion());
//        pagoTiempoDevengarHelper.getParametrosBusqueda().setInstitucionId(obtenerInstitucion().getId());
//        try {
//            if (pagoTiempoDevengarHelper.getNumeroIdentificacion() != null
//                    || pagoTiempoDevengarHelper.getParametrosBusqueda().getNumeroDocumento().trim().length() > 0) {
//                if (pagoTiempoDevengarHelper.getTipoIdentificacion().equals(
//                        TipoDocumentoEnum.CEDULA.getNombre())
//                        || pagoTiempoDevengarHelper.getTipoIdentificacion().
//                        equals(TipoDocumentoEnum.PASAPORTE.getNombre())) {
//                    pagoTiempoDevengarHelper.getListaDevengamientos().clear();
//                    pagoTiempoDevengarHelper.setListaDevengamientos(
//                            puestoServicio.buscarPuestoporFiltros(pagoTiempoDevengarHelper.getParametrosBusqueda()));
//                    if (pagoTiempoDevengarHelper.getListaDevengamientos().isEmpty()) {
//                        pagoTiempoDevengarHelper.setDevengamiento(new Devengamiento());
//                        pagoTiempoDevengarHelper.setPago(Boolean.FALSE);
//                        ponerMensajeError(NADA, "Servidor no registrado");
//                    } else {
//                        SrhvOrganicoPosicionalIndividual devengamiento =
//                                pagoTiempoDevengarHelper.getListaDevengamientos().get(0);
//                        pagoTiempoDevengarHelper.setParametrosBusqueda(devengamiento);
//                        buscarDevengacion();
//                        pagoTiempoDevengarHelper.setPago(Boolean.TRUE);
//                        if (pagoTiempoDevengarHelper.getDevengamientoLista().isEmpty()) {
//                            pagoTiempoDevengarHelper.setDevengamiento(new Devengamiento());
//                            pagoTiempoDevengarHelper.getDevengamiento().setArchivo(new Archivo());
//                            pagoTiempoDevengarHelper.setActivar(Boolean.TRUE);
//                            pagoTiempoDevengarHelper.setPago(Boolean.FALSE);
//                            ponerMensajeError(NADA,
//                                    "No tiene informacion de denvengacion, debe ingresar el periodo a devengar.");
//                        } else {
//                            Devengamiento devengamientos =
//                                    pagoTiempoDevengarHelper.getDevengamientoLista().get(0);
//                            pagoTiempoDevengarHelper.setDevengamiento(devengamientos);
//                            if (pagoTiempoDevengarHelper.getDevengamiento().getArchivo() == null) {
//                                pagoTiempoDevengarHelper.getDevengamiento().setArchivo(new Archivo());
//                            }
//                            pagoTiempoDevengarHelper.setActivar(Boolean.FALSE);
//                            diasDevengados();
//                        }
//                    }
//
//                }
//            } else {
//                ponerMensajeError(NADA, "No a ingresado el numero de identificación");
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(DevengamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Este metodo guarda el archivo.
     */
    public void subirArchivo() {
        try {
            if (pagoTiempoDevengarHelper.getArchivoCargar() != null) {
                ParametroGlobal buscarParametroGlobalPorNemonico =
                        getAdministracionServicio().buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (pagoTiempoDevengarHelper.getArchivoCargar().getSize()
                        > buscarParametroGlobalPorNemonico.getValorNumerico().longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo super las "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 2048) + " MB",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    if ("application/force-download".equals(
                            pagoTiempoDevengarHelper.getArchivoCargar().getContentType()) || "application/pdf".equals(
                            pagoTiempoDevengarHelper.getArchivoCargar().getContentType())) {
                        Archivo archivo = new Archivo();
                        iniciarDatosEntidad(archivo, Boolean.TRUE);
                        archivo.setArchivo(pagoTiempoDevengarHelper.getArchivoCargar().getContents());
                        archivo.setNombre(pagoTiempoDevengarHelper.getArchivoCargar().getFileName());
                        pagoTiempoDevengarHelper.getDevengamiento().setArchivo(archivo);
//                        tramiteServicio.guardarArchivoMovimiento(deven, archivo);                       
                        FacesMessage msg = new FacesMessage("Archivo "
                                + pagoTiempoDevengarHelper.getArchivoCargar().getFileName() + " subió correctamente");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        mostrarMensajeEnPantalla("ec.gob.mrl.smp.genericos.mensaje.archivoCargadoNoesPDF",
                                FacesMessage.SEVERITY_WARN);
                        pagoTiempoDevengarHelper.setArchivoCargar(null);
                    }
                }
            } else {
                FacesMessage msg = new FacesMessage("El archivo esta vacio.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al subir el archivo.", ex);
        }
    }

    /**
     * calculo de dias devengados.
     *
     */
    public void diasDevengados() {
        if (pagoTiempoDevengarHelper.getFechaCorte().getTime()
                > pagoTiempoDevengarHelper.getDevengamiento().getFechaInicio().getTime()) {
            pagoTiempoDevengarHelper.getDevengamiento().setDiasDevengados(
                    Integer.parseInt(UtilFechas.calcularDiferenciaDiasEntreFechas(
                    pagoTiempoDevengarHelper.getDevengamiento().getFechaInicio(),
                    pagoTiempoDevengarHelper.getFechaCorte()).toString()));
        }
        if (pagoTiempoDevengarHelper.getDevengamiento().getFechaFinal().getTime()
                > pagoTiempoDevengarHelper.getFechaCorte().getTime()) {
            pagoTiempoDevengarHelper.getDevengamiento().setDiasPendientes(Integer.parseInt(
                    UtilFechas.calcularDiferenciaDiasEntreFechas(
                    pagoTiempoDevengarHelper.getFechaCorte(),
                    pagoTiempoDevengarHelper.getDevengamiento().getFechaFinal()).toString()));
        }
    }

    /**
     * busca los devengamientos activos.
     */
    private void buscarDevengacion() {
        try {
            pagoTiempoDevengarHelper.setDevengamientoLista(tramiteServicio.buscarDevengamientosPorServidor(
                    pagoTiempoDevengarHelper.getTipoIdentificacion(),
                    pagoTiempoDevengarHelper.getNumeroIdentificacion(),
                    obtenerUsuarioConectado().getInstitucion().getId(), pagoTiempoDevengarHelper.getFechaCorte()));
        } catch (ServicioException ex) {
            Logger.getLogger(DevengamientoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo para guardar el pago del devengamiento.
     *
     * @return null
     */
    public String guardar() {
//        if (pagoTiempoDevengarHelper.getDevengamiento().getPagoAnticipado()) {
//            try {
//                Boolean estado = Boolean.TRUE;
//                String bundlekey = "";
//                if (pagoTiempoDevengarHelper.getDevengamiento().getFechaDocumento() != null
//                        && pagoTiempoDevengarHelper.getDevengamiento().getFechaFinal() != null) {
//                    long f1 = pagoTiempoDevengarHelper.getDevengamiento().getFechaDocumento().getTime();
//                    long f2 = pagoTiempoDevengarHelper.getDevengamiento().getFechaFinal().getTime();
//                    if (f1 > f2) {
//                        estado = Boolean.FALSE;
//                        bundlekey = COMPARAR_FECHA_DOCUMENTO;
//                    }
//                }
//                if (estado) {
//                    InstitucionEjercicioFiscal i = new InstitucionEjercicioFiscal();
//                    i.setId(obtenerUsuarioConectado().getIdInstitucion());
//                    pagoTiempoDevengarHelper.getDevengamiento().setInstitucion(i);
//                    pagoTiempoDevengarHelper.getDevengamiento().
//                            setFechaPagoAnticipado(pagoTiempoDevengarHelper.getFechaCorte());
//                    tramiteServicio.actualizarDevengamiento(pagoTiempoDevengarHelper.getDevengamiento(),
//                            pagoTiempoDevengarHelper.getDevengamiento().getArchivo());
//                    mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
//                    pagoTiempoDevengarHelper.setNumeroIdentificacion("");
//                    pagoTiempoDevengarHelper.setTipoIdentificacion("");
//                    pagoTiempoDevengarHelper.setParametrosBusqueda(new SrhvOrganicoPosicionalIndividual());
//                    pagoTiempoDevengarHelper.setDevengamiento(new Devengamiento());
//                } else {
//                    mostrarMensajeEnPantalla(bundlekey, FacesMessage.SEVERITY_WARN);
//                }
//            } catch (Exception ex) {
//                mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
//                //error(getClass().getName(), "Error al guardar el devengamiento", ex);
//            }
//        }
        return null;
    }

    /**
     * Este metodo busca los catalogos y los llena en las opciones.
     */
    private void consultarCatalogos() {
//        try {
//            llenarOpcionesCatalogoNombre(pagoTiempoDevengarHelper.getTipoDocumento(),
//                    catalogoServicio.buscarTipoDocumento());
//        } catch (Exception e) {
//            error(getClass().getName(), "Error al consultar catalogo.", e);
//        }
    }

    /**
     * Este método pasa una lista de catalogo a una lista de seleccion.
     *
     * @param lista List
     * @param opciones List
     */
//    public final void llenarOpcionesCatalogoNombre(final List<SelectItem> lista, final List<CatalogoDetalle> opciones) {
//        iniciarCombos(lista);
//        if (opciones != null) {
//            for (CatalogoDetalle c : opciones) {
//                lista.add(new SelectItem(c.getNombre(), c.getNombre(), c.getDescripcion()));
//            }
//        }
//    }

    /**
     * Este método control la selección del tipo de decumento.
     */
    public void controlSeleccionTipoDocumento() {
        if (TipoDocumentoEnum.CEDULA.getNombre().equals(
                pagoTiempoDevengarHelper.getTipoIdentificacion())) {
            pagoTiempoDevengarHelper.setTipoCedula(Boolean.TRUE);
        } else {
            pagoTiempoDevengarHelper.setTipoCedula(Boolean.FALSE);
        }
    }

    /**
     * @return the puestoServicio
     */
//    public SrhvOrganicoPosicionalIndividualServicio getPuestoServicio() {
//        return puestoServicio;
//    }

    /**
     * @param puestoServicio the puestoServicio to set
     */
//    public void setPuestoServicio(final SrhvOrganicoPosicionalIndividualServicio puestoServicio) {
//        this.puestoServicio = puestoServicio;
//    }

    /**
     * @return the catalogoServicio
     */
//    public CatalogoServicio getCatalogoServicio() {
//        return catalogoServicio;
//    }

    /**
     * @param catalogoServicio the catalogoServicio to set
     */
//    public void setCatalogoServicio(final CatalogoServicio catalogoServicio) {
//        this.catalogoServicio = catalogoServicio;
//    }

    /**
     * @return the tramiteServicio
     */
    public TramiteServicio getTramiteServicio() {
        return tramiteServicio;
    }

    /**
     * @param tramiteServicio the tramiteServicio to set
     */
    public void setTramiteServicio(final TramiteServicio tramiteServicio) {
        this.tramiteServicio = tramiteServicio;
    }

    /**
     * @return the pagoTiempoDevengarHelper
     */
    public PagoTiempoDevengarHelper getPagoTiempoDevengarHelper() {
        return pagoTiempoDevengarHelper;
    }

    /**
     * @param pagoTiempoDevengarHelper the pagoTiempoDevengarHelper to set
     */
    public void setPagoTiempoDevengarHelper(final PagoTiempoDevengarHelper pagoTiempoDevengarHelper) {
        this.pagoTiempoDevengarHelper = pagoTiempoDevengarHelper;
    }

    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(final StreamedContent file) {
        this.file = file;
    }

    /**
     * @return the administracionServicio
     */
    public AdministracionServicio getAdministracionServicio() {
        return administracionServicio;
    }

    /**
     * @param administracionServicio the administracionServicio to set
     */
    public void setAdministracionServicio(AdministracionServicio administracionServicio) {
        this.administracionServicio = administracionServicio;
    }
}
