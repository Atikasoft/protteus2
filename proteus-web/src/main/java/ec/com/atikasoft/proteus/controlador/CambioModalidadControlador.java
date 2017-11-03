package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.BusquedaPuestoHelper;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.Distributivo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Controlador de Busqueda Puesto.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "cambioModalidadBean")
@ViewScoped
public class CambioModalidadControlador extends BaseControlador {

    /**
     * Log de clase.
     */
    private static final Logger LOG = Logger.getLogger(CambioModalidadControlador.class.getName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/puesto/cambio_modalidad.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de distributivo.
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{busquedaPuestoHelper}")
    private BusquedaPuestoHelper busquedaPuestoHelper;

    /**
     * Constructor por defecto.
     */
    public CambioModalidadControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        busquedaPuestoHelper.iniciador();
        iniciarOpciones();
        iniciardatosBusqueda();

    }

    public void iniciardatosBusqueda() {

        getBusquedaPuestoHelper().getBusquedaPuestoVO().setCodigoPuesto(null);
    }

    /**
     * Este método regresa a la pantalla de tramite.
     *
     * @return String
     */
    public String guardarPuesto() {

        if (busquedaPuestoHelper.getPuesto() != null && !busquedaPuestoHelper.getPuesto().getDistributivo().
                getIdModalidadLaboral().equals(busquedaPuestoHelper.getBusquedaPuestoVO().getModalidadLaboralId())) {
            try {
                busquedaPuestoHelper.getPuesto().getDistributivo().getModalidadLaboral().setId(
                        busquedaPuestoHelper.getBusquedaPuestoVO().getModalidadLaboralId());
                Distributivo nuevoDistributivo = distributivoPuestoServicio.buscarDistributivo(
                        busquedaPuestoHelper.getPuesto().getDistributivo().getIdUnidadOrganizacional(),
                        busquedaPuestoHelper.getBusquedaPuestoVO().getModalidadLaboralId(),
                        busquedaPuestoHelper.getPuesto().getDistributivo().getIdInstitucionEjercicioFiscal());

                if (nuevoDistributivo != null) {
                    busquedaPuestoHelper.getPuesto().setDistributivo(nuevoDistributivo);

                    distributivoPuestoServicio.actualizarDistributivoEnDistributivoDetalle(
                            busquedaPuestoHelper.getPuesto(), nuevoDistributivo.getId());
                    busquedaPuestoHelper.getPuesto().getDistributivo().setIdModalidadLaboral(
                            nuevoDistributivo.getIdModalidadLaboral());
                } else {
                    //crear nuevo distributivo
                    Distributivo d = crearNuevoDistributivo(busquedaPuestoHelper.getPuesto(),
                            busquedaPuestoHelper.getBusquedaPuestoVO().getModalidadLaboralId());

                    busquedaPuestoHelper.getPuesto().setDistributivo(d);

                    //actualizar puesto con el nuevo distributivo y modalidad 
                    distributivoPuestoServicio.actualizarDistributivoEnDistributivoDetalle(
                            busquedaPuestoHelper.getPuesto(), d.getId());

                    busquedaPuestoHelper.getPuesto().getDistributivo().setModalidadLaboral(
                            administracionServicio.buscarModalidadLaboral(
                                    busquedaPuestoHelper.getPuesto().getDistributivo().getIdModalidadLaboral()));
                    busquedaPuestoHelper.getPuesto().getDistributivo().setUnidadOrganizacional(
                            administracionServicio.buscarUnidadOrganizacional(
                                    busquedaPuestoHelper.getPuesto().getDistributivo().getIdUnidadOrganizacional()));
                }

                //actualizar trayectoria laboral
                distributivoPuestoServicio.crearDistributivoDetalleHistorico(
                        busquedaPuestoHelper.getPuesto(), obtenerUsuarioConectado());
                //afectarTrayectoriaLaboral(busquedaPuestoHelper.getPuesto());
                distributivoPuestoServicio.afectarTrayectoriaLaboral(busquedaPuestoHelper.getPuesto(),
                        "CAMBIO DE MODALIDAD LABORAL", GrupoEnum.ROTACIONES.getCodigo(), "CAMBIO DE MODALIDAD LABORAL",
                        "0000000000", obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(),
                        obtenerUsuarioConectado().getServidor().getApellidosNombres());

                mostrarMensajeEnPantalla("Registro guardado con éxito", FacesMessage.SEVERITY_INFO);
                limpiarPantalla();
            } catch (Exception ex) {
                mostrarMensajeEnPantalla("Ocurrió un error al guardar la información", FacesMessage.SEVERITY_ERROR);
                Logger.getLogger(CambioModalidadControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            mostrarMensajeEnPantalla("Debe ingresar un puesto vacante y una modalidad diferente",
                    FacesMessage.SEVERITY_INFO);
        }
        return null;
    }

    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @return String
     */
    public String buscarPuestos() {
        try {
            busquedaPuestoHelper.getListaPuestos().clear();

            busquedaPuestoHelper.getListaPuestos().addAll(distributivoPuestoServicio.
                    buscarPorCodigoPuesto(busquedaPuestoHelper.getBusquedaPuestoVO().getCodigoPuesto(),
                            obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId()));

            if (!busquedaPuestoHelper.getListaPuestos().isEmpty()) {
                if (busquedaPuestoHelper.getListaPuestos().get(0).getServidor() == null) {
                    busquedaPuestoHelper.setPuesto(busquedaPuestoHelper.getListaPuestos().get(0));
                    busquedaPuestoHelper.getBusquedaPuestoVO().setModalidadLaboralId(
                            busquedaPuestoHelper.getPuesto().getDistributivo().getModalidadLaboral().getId());
                } else {
                    mostrarMensajeEnPantalla("No existe puesto vacante con el código indicado",
                            FacesMessage.SEVERITY_WARN);
                    limpiarPantalla();
                }

            } else {
                mostrarMensajeEnPantalla("No existe puesto con el código indicado", FacesMessage.SEVERITY_WARN);
                limpiarPantalla();
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al consultar los puestos.", e);
        }
        return null;
    }

    /**
     * Este método procesa la busqueda de puestos por filtro.
     *
     * @param line
     * @param puestoCodigo
     * @return
     */
    public DistributivoDetalle puestoVacante(int line, Long puestoCodigo) {
        List<DistributivoDetalle> listaPuestos = new ArrayList<DistributivoDetalle>();
        String errorMsg;
        try {
            listaPuestos.addAll(distributivoPuestoServicio.buscarPorCodigoPuesto(puestoCodigo,
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId()));

            if (!listaPuestos.isEmpty()) {
                if (listaPuestos.get(0).getServidor() == null) {
                    //busquedaPuestoHelper.getListaPuestos().add(listaPuestos.get(0));
                    busquedaPuestoHelper.getDistributivosDetalleMasivoMap().put(line, listaPuestos.get(0));
                } else {
                    errorMsg = "Error en la línea " + line + ". No existe puesto vacante con el código " + puestoCodigo;
                    busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
                }
                return listaPuestos.get(0);
            } else {
                errorMsg = "Error en la línea " + line + ". No existe puesto con el código " + puestoCodigo;
                busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
            }
        } catch (Exception e) {
            errorMsg = "Error al consultar los puestos.";
            busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
            //error(getClass().getName(), "Error al consultar los puestos.", e);
        }
        return null;
    }

    public Long buscarModalidadId(int line, String modalidadCodigo) {
        String errorMsg;
        try {
            List<ModalidadLaboral> mls = administracionServicio.listarTodosModalidadLaboralPorCodigo(modalidadCodigo);

            if (mls.isEmpty()) {
                errorMsg = "Error en la línea " + line + ". No existe modalidad con el código " + modalidadCodigo;
                busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
            } else {
                busquedaPuestoHelper.getIdsModalidadesMap().put(line, mls.get(0).getId());
                return mls.get(0).getId();
            }
        } catch (Exception e) {
            errorMsg = "Error al consultar los puestos.";
            busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
            //error(getClass().getName(), "Error al consultar los puestos.", e);
        }
        return null;
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {

            /**
             * poblar modalidades laborales.
             */
            List<ModalidadLaboral> mls = administracionServicio.listarTodosModalidadLaboralPorNombre(null);
            getBusquedaPuestoHelper().getListaModalidadLaboral().clear();
            iniciarCombos(getBusquedaPuestoHelper().getListaModalidadLaboral());
            for (ModalidadLaboral ml : mls) {
                getBusquedaPuestoHelper().getListaModalidadLaboral().add(new SelectItem(ml.getId(), ml.getNombre()));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    /**
     * @return the busquedaPuestoHelper
     */
    public BusquedaPuestoHelper getBusquedaPuestoHelper() {
        return busquedaPuestoHelper;
    }

    /**
     * @param busquedaPuestoHelper the busquedaPuestoHelper to set
     */
    public void setBusquedaPuestoHelper(final BusquedaPuestoHelper busquedaPuestoHelper) {
        this.busquedaPuestoHelper = busquedaPuestoHelper;
    }

    public void limpiarPantalla() {
        busquedaPuestoHelper.setPuesto(DistributivoDetalle.getDistributivoDetalleVacio());
        busquedaPuestoHelper.getBusquedaPuestoVO().setModalidadLaboralId(null);
    }

    /**
     *
     * @param event
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile archivo = event.getFile();
            busquedaPuestoHelper.setNombreArchivoCSV(archivo.getFileName());
            boolean error = validarArchivoCVS(archivo);
            busquedaPuestoHelper.setArchivoSinError(!error);
            if (error) {
                ejecutarComandoPrimefaces("dlgCargaArchivoMasivoErrores.show();");

            } else {
                mostrarMensajeEnPantalla("Archivo cargado satisfactoriamente.", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public String procesarArchivo() {
        if (busquedaPuestoHelper.getArchivoSinError()) {
            int i = 1;
            boolean errorGuardar = false;
            for (String[] f : busquedaPuestoHelper.getFilas()) {

                try {
                    DistributivoDetalle puesto = busquedaPuestoHelper.getDistributivosDetalleMasivoMap().get(i);
                    Long idModalidad = busquedaPuestoHelper.getIdsModalidadesMap().get(i);
                    puesto.getDistributivo().getModalidadLaboral().setId(idModalidad);
                    Distributivo nuevoDistributivo = distributivoPuestoServicio.buscarDistributivo(
                            puesto.getDistributivo().getIdUnidadOrganizacional(),
                            idModalidad, puesto.getDistributivo().getIdInstitucionEjercicioFiscal());

                    if (nuevoDistributivo != null) {
                        distributivoPuestoServicio.actualizarDistributivoEnDistributivoDetalle(
                                puesto, nuevoDistributivo.getId());
                        puesto.getDistributivo().setIdModalidadLaboral(nuevoDistributivo.getIdModalidadLaboral());
                    } else {
                        System.out.println("--------------- No se encontró nuevo distributivo");
                        //crear nuevo distributivo
                        Distributivo d = crearNuevoDistributivo(puesto, idModalidad);

                        puesto.setDistributivo(d);

                        //puesto.getDistributivo().setIdModalidadLaboral(d.getIdModalidadLaboral());
                        distributivoPuestoServicio.actualizarDistributivoEnDistributivoDetalle(puesto, d.getId());

                        puesto.getDistributivo().setModalidadLaboral(
                                administracionServicio.buscarModalidadLaboral(puesto.getDistributivo().
                                        getIdModalidadLaboral()));
                        puesto.getDistributivo().setUnidadOrganizacional(
                                administracionServicio.buscarUnidadOrganizacional(
                                        puesto.getDistributivo().getIdUnidadOrganizacional()));
                    }

                    //actualizar trayectoria laboral
                    distributivoPuestoServicio.crearDistributivoDetalleHistorico(puesto, obtenerUsuarioConectado());
                    //afectarTrayectoriaLaboral(puesto);
                    distributivoPuestoServicio.afectarTrayectoriaLaboral(puesto, "CAMBIO DE MODALIDAD LABORAL",
                            GrupoEnum.ROTACIONES.getCodigo(), "CAMBIO DE MODALIDAD LABORAL", "0000000000",
                            obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(),
                            obtenerUsuarioConectado().getServidor().getApellidosNombres());

                } catch (Exception ex) {
                    System.out.println("Ocurrió un error al guardar la información fila " + i);
                    errorGuardar = true;
                    Logger.getLogger(CambioModalidadControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
            if (errorGuardar) {
                mostrarMensajeEnPantalla("Ocurrió un error al guardar la información", FacesMessage.SEVERITY_ERROR);
            } else {
                mostrarMensajeEnPantalla("Registros guardados con éxito", FacesMessage.SEVERITY_INFO);
            }
        } else {
            mostrarMensajeEnPantalla("No se puede ejecutar el cambio: El archivo cargado presenta errores",
                    FacesMessage.SEVERITY_ERROR);
        }
        limpiarDatosArchivoCargado();
        return null;
    }

    /**
     *
     * @param archivoSubido
     * @return
     */
    private boolean validarArchivoCVS(UploadedFile archivoSubido) {
        busquedaPuestoHelper.getDistributivosDetalleMasivoMap().clear();
        busquedaPuestoHelper.getIdsModalidadesMap().clear();

        busquedaPuestoHelper.getErroresEnArchivo().clear();
        boolean error = false;
        String errorMsg;
        OutputStream outStream = null;
        try {
            //CARGAR ARCHIVO AL SERVIDOR
            File f = new File(archivoSubido.getFileName());
            outStream = new FileOutputStream(f);
            long pesoArchivo = archivoSubido.getSize();
            byte[] buffer = new byte[(int) pesoArchivo];
            InputStream stream = archivoSubido.getInputstream();
            stream.read(buffer, 0, (int) pesoArchivo);
            stream.close();
            outStream.write(buffer);
            outStream.close();

            List<String[]> lineas = UtilArchivos.leerArchivoCSVSubido(f);

            DistributivoDetalle puesto = null;
            Long modalidadId;
            int i = 1;
            for (String[] l : lineas) {
                //comprueba que tenga las 2 columnas(evita procesar lineas mal formadas, ej. lineas vacias)
                if (l.length != 2) {
                    error = true;
                    errorMsg = "Error en la linea " + i + " .No cumple con el formato de dos campos separador por ; ";
                    busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
                } else {
                    //campo código
                    if (l[0] == null || l[0].trim().isEmpty()) {
                        errorCampoObligatorio(i, "CÓDIGO");
                        error = true;
                    } else if (!l[0].trim().matches("[0-9]+")) {
                        errorCampoTipo(i, "CÓDIGO", "NUMERICO");
                        error = true;
                    } else {
                        puesto = puestoVacante(i, new Long(l[0]));
                        if (puesto == null) {
                            error = true;
                        }
                    }

                    //campo modalidad
                    if (l[1] == null || l[1].trim().isEmpty()) {
                        errorCampoObligatorio(i, "MODALIDAD");
                        error = true;
                    } else {
                        modalidadId = buscarModalidadId(i, l[1]);
                        if (modalidadId == null) {
                            error = true;
                        } else if (puesto != null) {
                            if (puesto.getDistributivo().getIdModalidadLaboral().equals(modalidadId)) {
                                error = true;
                                errorMsg = "Error en la linea " + i
                                        + ". El puesto ya tiene asignada la modalidad especificada.";
                                busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
                            }

                        }
                    }
                }
                i++;
            }
            if (!error) {
                busquedaPuestoHelper.getFilas().clear();
                busquedaPuestoHelper.getFilas().addAll(lineas);
            }
            f.delete();
        } catch (IOException ex) {
            Logger.getLogger(CambioModalidadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return error;
    }

    /**
     *
     * @param nroLinea
     * @param campo
     */
    private void errorCampoObligatorio(int nroLinea, String campo) {
        String errorMsg = "Error en la línea " + nroLinea + ". Se omitió el campo \"" + campo + "\".";
        busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
    }

    /**
     *
     * @param nroLinea
     * @param campo
     */
    private void errorCampoTipo(int nroLinea, String campo, String tipo) {
        String errorMsg = "Error en la línea " + nroLinea + ". El campo \"" + campo + "\""
                + " debe ser de tipo \"" + tipo + "\".";
        busquedaPuestoHelper.getErroresEnArchivo().add(errorMsg);
    }

    private Distributivo crearNuevoDistributivo(DistributivoDetalle puesto, Long modalidadId) {
        try {
            Distributivo nuevoDistributivo = Distributivo.getDistributivoVacio();
            nuevoDistributivo.setIdUnidadOrganizacional(puesto.getDistributivo().getIdUnidadOrganizacional());
            nuevoDistributivo.setIdInstitucionEjercicioFiscal(puesto.getDistributivo().
                    getIdInstitucionEjercicioFiscal());

            nuevoDistributivo.setIdModalidadLaboral(modalidadId);

            return distributivoPuestoServicio.guardarDistributivo(nuevoDistributivo, obtenerUsuarioConectado(), null);
        } catch (ServicioException ex) {
            Logger.getLogger(CambioModalidadControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void limpiarDatosArchivoCargado() {
        busquedaPuestoHelper.setNombreArchivoCSV("");
        busquedaPuestoHelper.getFilas().clear();
        busquedaPuestoHelper.getDistributivosDetalleMasivoMap().clear();
        busquedaPuestoHelper.getIdsModalidadesMap().clear();
        busquedaPuestoHelper.getErroresEnArchivo().clear();
        busquedaPuestoHelper.setArchivoSinError(false);
    }

    public void cancelar() {
        limpiarDatosArchivoCargado();
    }

    /**
     *
     * @return
     */
    public StreamedContent getFormatoArchivo() {
        InputStream streamOperativo = ((ServletContext) FacesContext.getCurrentInstance().
                getExternalContext().getContext()).getResourceAsStream("/pdf/FORMATO_CAMBIO_MODALIDAD_MASIVO.pdf");
        return new DefaultStreamedContent(streamOperativo, "application/pdf", "FORMATO_CAMBIO_MODALIDAD_MASIVO.pdf");
    }

}
