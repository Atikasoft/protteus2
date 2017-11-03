/*
 *  JustificacionControlador.java
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
 *  07/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.JustificacionHelper;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Justificacion;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
@ManagedBean(name = "justificacionBean")
@ViewScoped
public class JustificacionControlador extends BaseControlador {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(JustificacionControlador.class.getCanonicalName());

    /**
     * Regla de navegacion.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/tramite/justificacion.jsf";

    /**
     * Regla de navegacion.
     */
    public static final String LISTA_MENSAJE = "/pages/procesos/tramite/lista_mensaje.jsf";

    /**
     * Key del Mensaje cuando el archivo a subir es requerido.
     */
    public static final String MENSAJE_ARCHIVO_REQUERIDO =
            "ec.gob.mrl.smp.justificacionReglas.archivoCargadoObligatorio";

    /**
     * BO de administracion de servicio.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{justificacionHelper}")
    private JustificacionHelper justificacionHelper;

    @Override
    @PostConstruct
    public void init() {
    }

    /**
     * Metodo que setea la variable del popup a true para q se pueda ver.
     *
     * @return String regla de navegacion.
     */
    public String iniciarJustificacion() {
        try {
            justificacionHelper.setArchivoCargado(null);
            Justificacion justificacion = administracionServicio.buscarJustificacionPorMovimeintoYTipoMovimientoRegla(
                    justificacionHelper.getJustificacionVO().getReglaMensaje().getMovimiento().getId(),
                    justificacionHelper.getJustificacionVO().getReglaMensaje().getTipoMovimientoRegla().getId());
            LOG.info(UtilCadena.concatenar("ID MOVIMIENTO ",
                    justificacionHelper.getJustificacionVO().getReglaMensaje().getMovimiento().getId()));
            LOG.info(UtilCadena.concatenar("ID TIPO MOVIMIENTO REGLA ",
                    justificacionHelper.getJustificacionVO().getReglaMensaje().getTipoMovimientoRegla().getId()));
            LOG.info(UtilCadena.concatenar("JUSTIFICACION CONSULTADA ", justificacion));
            if (justificacion == null) {
                System.out.println("Entra al if");
                justificacionHelper.setEsNuevo(Boolean.TRUE);
                justificacionHelper.setArchivoRequerido(Boolean.TRUE);
                LOG.info("El archivo es requerido");
                justificacionHelper.getJustificacionVO().setJustificacion(new Justificacion());
            } else {
                System.out.println("Entra al else");
                justificacionHelper.setEsNuevo(Boolean.FALSE);
                LOG.info("El archivo NO es requerido");
                justificacionHelper.setArchivoRequerido(Boolean.FALSE);
                justificacionHelper.getJustificacionVO().setJustificacion(justificacion);
            }
            reglaNavegacionDirecta(FORMULARIO_ENTIDAD);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Metodo que se encarga de guardar la justificacion.
     *
     * @return Regla de navegacion
     */
    public String guardarJustificacion() {
        try {
            if (justificacionHelper.getEsNuevo()) {
                LOG.info("ENTRO POR Q ES NUEVO");
                Justificacion justificacion = justificacionHelper.getJustificacionVO().getJustificacion();
                justificacion.setTipoMovimientoRegla(justificacionHelper.getJustificacionVO().getReglaMensaje().
                        getTipoMovimientoRegla());
                justificacion.setMovimiento(
                        justificacionHelper.getJustificacionVO().getReglaMensaje().getMovimiento());
                if (justificacionHelper.getArchivoCargado() != null) {
                    LOG.info(UtilCadena.concatenar("Archivo a guardar ", justificacionHelper.getArchivoCargado()));
                    administracionServicio.guardarJustificacion(
                            justificacionHelper.getArchivoCargado().getContents(),
                            justificacionHelper.getArchivoCargado().getFileName(),
                            justificacionHelper.getArchivoCargado().getContentType(),
                            justificacionHelper.getArchivoCargado().getSize(),
                            obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(), justificacion);
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    justificacionHelper.setArchivoCargado(null);
                    justificacionHelper.setEsNuevo(Boolean.FALSE);
                } else {
                    mostrarMensajeEnPantalla(MENSAJE_ARCHIVO_REQUERIDO, FacesMessage.SEVERITY_ERROR);
                }
            } else {
                LOG.info("ENTRO POR Q ES EDICION");
                Justificacion justificacion = justificacionHelper.getJustificacionVO().getJustificacion();
                justificacion.setTipoMovimientoRegla(justificacionHelper.getJustificacionVO().getReglaMensaje().
                        getTipoMovimientoRegla());
                justificacion.setMovimiento(
                        justificacionHelper.getJustificacionVO().getReglaMensaje().getMovimiento());

                LOG.info("Archivo de justificacion a editar " + justificacion.getArchivo());
                if (justificacionHelper.getArchivoCargado() != null) {
                    administracionServicio.actualizarJustificacion(
                            justificacionHelper.getArchivoCargado().getContents(),
                            justificacionHelper.getArchivoCargado().getFileName(),
                            justificacionHelper.getArchivoCargado().getContentType(),
                            justificacionHelper.getArchivoCargado().getSize(),
                            obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(), justificacion);
                } else {
                    administracionServicio.actualizarJustificacion(
                            null, "", "", 0L, obtenerUsuarioConectado().getServidor().getNumeroIdentificacion(),
                            justificacion);
                }
                justificacionHelper.setArchivoCargado(null);
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            //mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            LOG.log(Level.INFO, "Error al guardar la Justificacion {0}", e);
        }
        return null;
    }

    /**
     * Metodo que se encarga de regresar a la pantalla de la lista de mensajes.
     *
     * @return String regla de navegacion.
     */
    public String regresar() {
        reglaNavegacionDirecta(LISTA_MENSAJE);
        return null;
    }

    /**
     * Metodo que realiza la carga de un archivo pdf.
     *
     * @throws ServicioException En caso de error
     */
    public void upload() throws ServicioException {
        if (justificacionHelper.getArchivoCargado() != null) {
            if (justificacionHelper.getArchivoCargado().getContentType().equals("application/pdf")) {
                ParametroGlobal buscarParametroGlobalPorNemonico =
                        administracionServicio.buscarParametroGlobalPorNemonico(
                        ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (justificacionHelper.getArchivoCargado().getSize()
                        > buscarParametroGlobalPorNemonico.getValorNumerico().longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo supera los "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 1024) + " MB",
                            FacesMessage.SEVERITY_WARN);
                    justificacionHelper.setArchivoRequerido(Boolean.TRUE);
                    justificacionHelper.setArchivoCargado(null);
                } else {
                    mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoCorrectamente",
                            FacesMessage.SEVERITY_INFO);
                    justificacionHelper.setArchivoRequerido(Boolean.FALSE);
                    System.out.println("Archivo Cargado " + justificacionHelper.getArchivoCargado().getFileName());
                }
            } else {
                mostrarMensajeEnPantalla("ec.gob.mrl.smp.justificacionReglas.archivoCargadoNoesPDF",
                        FacesMessage.SEVERITY_ERROR);
                justificacionHelper.setArchivoRequerido(Boolean.TRUE);
                justificacionHelper.setArchivoCargado(null);
            }

        }
    }

    /**
     * Metodo que se encarga de buscar la justificacion en la base de datos si esta retorna que si para saber si ya esta
     * justificado.
     *
     * @param movimientoId id de movimiento
     * @param tipoMovimientoReglaId id de tipo movimiento regla
     * @return String
     */
    public String verificarJustificado(final Long movimientoId, final Long tipoMovimientoReglaId) {
        String resultado = "no justificado";
        try {
            Justificacion justificacion = administracionServicio.buscarJustificacionPorMovimeintoYTipoMovimientoRegla(
                    movimientoId, tipoMovimientoReglaId);
            if (justificacion != null) {
                resultado = "SI JUSTIFICADO";
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            LOG.log(Level.INFO, "Error al buscar y verificar si la regla esta justificada", ex);
        }
        return resultado;
    }

    /**
     * @return the justificacionHelper
     */
    public JustificacionHelper getJustificacionHelper() {
        return justificacionHelper;
    }

    /**
     * @param justificacionHelper the justificacionHelper to set
     */
    public void setJustificacionHelper(final JustificacionHelper justificacionHelper) {
        this.justificacionHelper = justificacionHelper;
    }
}
