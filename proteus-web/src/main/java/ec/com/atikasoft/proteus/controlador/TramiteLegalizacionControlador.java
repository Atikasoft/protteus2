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

import ec.com.atikasoft.proteus.modelo.Archivo;
import ec.com.atikasoft.proteus.modelo.MovimientoBitacora;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.DocumentoHabilitante;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;
import ec.com.atikasoft.proteus.controlador.base.AtencionControlador;
import ec.com.atikasoft.proteus.controlador.helper.TramiteLegalizacionHelper;
import ec.com.atikasoft.proteus.dao.TransicionDao;
import ec.com.atikasoft.proteus.enums.DocumentoHabilitanteEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Transicion;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarFechaMaximaMovimiento;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Controlador de Tramite Legalizacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@ManagedBean(name = "tramiteLegalizacionBean")
@ViewScoped
public class TramiteLegalizacionControlador extends AtencionControlador {

    /**
     * Regla de navegacion al formulario.
     */
    public static final String PAGINA = "/pages/procesos/tramite/tramite_legalizacion.jsf?est=vre";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{tramiteLegalizacionHelper}")
    private TramiteLegalizacionHelper tramiteLegalizacionHelper;

    /**
     * Servidio del tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de transicion.
     */
    @EJB
    private TransicionDao transicionDao;

    /**
     * Constructor por defecto.
     */
    public TramiteLegalizacionControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        establecerHelper(getTramiteLegalizacionHelper());
        establecerTramite();
        tramiteLegalizacionHelper.setEsAccionPersonal(
                tramiteLegalizacionHelper.getTramiteFormularioVO().
                getTramite().getTipoMovimiento().getDocumentoHabilitante().
                getNemonico().equals(DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo()));
        tramiteLegalizacionHelper.setEsMemorando(
                tramiteLegalizacionHelper.getTramiteFormularioVO().
                getTramite().getTipoMovimiento().getDocumentoHabilitante().
                getNemonico().equals(DocumentoHabilitanteEnum.MEMORANDO.getCodigo()));
        tramiteLegalizacionHelper.getListaNovedades().clear();
    }

    @Override
    public void avanzarTramite() {
        try {
            if (tramiteLegalizacionHelper.getComentario() != null && !tramiteLegalizacionHelper.getComentario().trim().
                    isEmpty()) {
                Transicion transicion = transicionDao.buscarPorId(tramiteLegalizacionHelper.getTransicionId());
                if (transicion.getFaseFinal().getCodigo().equals(EstadosTramiteEnum.REGISTRADO.getCodigo())) {
                    Boolean validaNumeroDocumentoHabilitante = Boolean.FALSE;
                    Boolean validaNumeroMeno = Boolean.FALSE;
                    Boolean validaNumeroRegistro = Boolean.FALSE;
                    Boolean validaArchivoDocumentoHabilitante = Boolean.FALSE;
                    Boolean validaRige = Boolean.TRUE;
                    TipoMovimiento tipoMovimiento = administracionServicio.buscarTipoMovimientoPorId(getTramiteHelper().
                            getTramite().getTipoMovimiento().getId());
                    DocumentoHabilitante documentoHabilitante = tipoMovimiento.getDocumentoHabilitante();
                    if (documentoHabilitante != null) {
                        if (documentoHabilitante.getNemonico().equals(
                                DocumentoHabilitanteEnum.ACCION_PERSONAL.getCodigo())) {
                            validaNumeroDocumentoHabilitante = Boolean.TRUE;
                            validaNumeroRegistro = Boolean.TRUE;
                            validaArchivoDocumentoHabilitante = tipoMovimiento.getCargarDocumentoHabilitanteObligatorio();
                        } else if (documentoHabilitante.getNemonico().equals(
                                DocumentoHabilitanteEnum.CONTRATOS_SERVICIOS_OCASIONALES.getCodigo())) {
                            validaNumeroDocumentoHabilitante = Boolean.TRUE;
                            validaNumeroRegistro = Boolean.TRUE;
                            validaArchivoDocumentoHabilitante = tipoMovimiento.getCargarDocumentoHabilitanteObligatorio();;
                        } else if (documentoHabilitante.getNemonico().equals(
                                DocumentoHabilitanteEnum.MEMORANDO.getCodigo())) {
                            validaNumeroDocumentoHabilitante = Boolean.FALSE;
                            validaNumeroMeno = Boolean.TRUE;
                            validaNumeroRegistro = Boolean.TRUE;
                            validaArchivoDocumentoHabilitante = tipoMovimiento.getCargarDocumentoHabilitanteObligatorio();;
                        }
                    }
                    List<Object> errores = new ArrayList<>();
                    int index = 1;
                    for (Movimiento m : getTramiteHelper().getListaMovimientos()) {
                        Integer[] mensaje = new Integer[5];
                        mensaje[0] = index;
                        int con = 0;

                        mensaje[1] = 0;
                        if ((validaNumeroDocumentoHabilitante && m.getNumeroDocumentoHabilitante() == null)
                                || (validaNumeroMeno && m.getNumeroMemo() == null)) {
                            con++;
                            mensaje[1] = 1;
                        }

                        mensaje[2] = 0;
                        if (m.getNumeroRegistro() == null && validaNumeroRegistro) {
                            con++;
                            mensaje[2] = 1;
                        }

                        mensaje[3] = 0;
                        if (m.getArchivo() == null && validaArchivoDocumentoHabilitante) {
                            con++;
                            mensaje[3] = 1;
                        }
                        mensaje[4] = 0;
                        if (m.getRigeApartirDe() == null && validaRige) {
                            con++;
                            mensaje[4] = 1;
                        }

                        if (con != 0) {
                            errores.add(mensaje);
                        }
                        index++;
                    }
                    if (errores.isEmpty()) {
                        super.avanzarTramite();
                    } else {
                        mostrarMensajeEnPantalla("ec.gob.mrl.smp.tramite.validacion.mensaje.error.validacion.tramite",
                                FacesMessage.SEVERITY_WARN);
                        tramiteLegalizacionHelper.getListaNovedades().clear();
                        tramiteLegalizacionHelper.getListaNovedades().addAll(errores);
                    }
                } else {
                    super.avanzarTramite();
                }
            } else {
                mostrarMensajeEnPantalla("message.comentario.obligatorio", FacesMessage.SEVERITY_ERROR);
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al avanzar con el tramite", ex);
        }
    }

    /**
     * Este metodo guarda el archivo.
     */
    public void subirArchivo() {
        try {
            if (tramiteLegalizacionHelper.getArchivo() != null) {
                ParametroGlobal buscarParametroGlobalPorNemonico
                        = administracionServicio.buscarParametroGlobalPorNemonico(
                                ParametroGlobalEnum.TAMANIO_MAXIMO_PDF.getCodigo());
                if (tramiteLegalizacionHelper.getArchivo().getSize()
                        > buscarParametroGlobalPorNemonico.getValorNumerico().longValue()) {
                    mostrarMensajeEnPantalla("El tamaño del archivo supera los "
                            + (buscarParametroGlobalPorNemonico.getValorNumerico().longValue() / 2048) + " MB",
                            FacesMessage.SEVERITY_WARN);
                } else {
                    if ("application/force-download".equals(
                            tramiteLegalizacionHelper.getArchivo().getContentType()) || "application/pdf".equals(
                                    tramiteLegalizacionHelper.getArchivo().getContentType()) || "application/download".equals(
                                    tramiteLegalizacionHelper.getArchivo().getContentType()) || "binary/octet-stream".equals(
                                    tramiteLegalizacionHelper.getArchivo().getContentType())) {
                        Movimiento movimiento = tramiteLegalizacionHelper.getMovimiento();
                        Archivo archivo = new Archivo();
                        iniciarDatosEntidad(archivo, Boolean.TRUE);
                        archivo.setArchivo(tramiteLegalizacionHelper.getArchivo().getContents());
                        archivo.setNombre(tramiteLegalizacionHelper.getArchivo().getFileName());
                        tramiteServicio.guardarArchivoMovimiento(movimiento, archivo);
                        consultarDatos();
                        FacesMessage msg = new FacesMessage("Archivo "
                                + tramiteLegalizacionHelper.getArchivo().getFileName() + " subió correctamente");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        mostrarMensajeEnPantalla("ec.gob.mrl.smp.genericos.mensaje.archivoCargadoNoesPDF",
                                FacesMessage.SEVERITY_WARN);
                        tramiteLegalizacionHelper.setArchivo(null);
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
     * Este metodo genera el numero de registro y actualiza el movimiento.
     *
     * @return String
     */
    public String generarNumeroRegistro() {
        try {
            tramiteServicio.generarNumeroRegistro(tramiteLegalizacionHelper.getMovimiento().getId(), obtenerUsuarioConectado());
            consultarDatos();
            actualizarComponente("frmTramiteLegalizacion:j_idt55:busquedaPuestoHelper_listaPuestos");
        } catch (Exception ex) {
            error(getClass().getName(), "Error al generar Número Registro", ex);
        }
        return null;
    }

    /**
     * Este metodo busca los movientos del tramite.
     */
    private void consultarDatos() {
        try {
            getTramiteHelper().getListaMovimientos().clear();
            getTramiteHelper().setListaMovimientos(tramiteServicio.buscarMovimientosPorTramite(
                    getTramiteHelper().getTramite().getId()));
        } catch (Exception ex) {
            error(getClass().getName(), null, ex);
        }
    }

    /**
     * Este metodo inicia la edicion del documento habilitante.
     *
     * @return String
     */
    public String iniciarEdicionMovimiento() {
        try {
            Movimiento movimiento = (Movimiento) BeanUtils.cloneBean(tramiteLegalizacionHelper.getMovimiento());
            tramiteLegalizacionHelper.setMovimiento(movimiento);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion del movimiento.", ex);
        }
        return null;
    }

    /**
     * Este método actualiza el movimiento.
     *
     * @return String
     */
    public void guardarEdicionMovimiento() {
        try {
            Boolean validado = Boolean.TRUE;
            Movimiento movimiento = (Movimiento) BeanUtils.cloneBean(tramiteLegalizacionHelper.getMovimiento());
            if (movimiento.getRigeApartirDe() != null && movimiento.getFechaHasta() != null && movimiento.
                    getRigeApartirDe().after(movimiento.getFechaHasta())) {
                validado = Boolean.FALSE;
                mostrarMensajeEnPantalla("La fecha 'Rige a Partir De' debe ser menor que la fecha 'Hasta'",
                        FacesMessage.SEVERITY_WARN);
            } else {
                List<TipoMovimientoRegla> reglas
                        = administracionServicio.listarTipoMovimientoReglaPorTipoMovimientoId(tramiteLegalizacionHelper.
                                getMovimiento().getTramite().getTipoMovimiento().getId());
                for (TipoMovimientoRegla regla : reglas) {
                    if (regla.getRegla().getNemonico().equals(ValidarFechaMaximaMovimiento.CODIGO_REGLA)) {
                        if (!validarFechaMaximaMovimiento(movimiento, regla.getRegla())) {
                            validado = Boolean.FALSE;
                        }
                        break;
                    }
                }
            }
            if (validado) {
                tramiteServicio.actualizarMovimiento(movimiento);
                mostrarMensajeEnPantalla("Movimiento actualizado.", FacesMessage.SEVERITY_INFO);
            } else {
                tramiteLegalizacionHelper.getMovimiento().setRigeApartirDe(tramiteLegalizacionHelper.getMovimiento().
                        getRigeApartirDeCopia());

            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al actualizar la fecha rige a partir de", ex);
        }
    }

    /**
     * Permite validar la fecha maxima del movimiento.
     *
     * @param m
     * @param regla
     */
    private Boolean validarFechaMaximaMovimiento(final Movimiento m, final Regla regla) {
        Boolean validado = Boolean.TRUE;
        try {
            if (regla.getTipoPeriodo() == null || regla.getValorPeriodo() == null) {
                validado = Boolean.FALSE;
                mostrarMensajeEnPantalla(
                        "No existe configurado el periodo en la regla de validación fecha máxima del movimiento",
                        FacesMessage.SEVERITY_WARN);
            } else {
                Date fechaMaxima = UtilFechas.truncarFecha(new Date());
                fechaMaxima = UtilFechas.sumarPeriodos(fechaMaxima, regla.getTipoPeriodo(), regla.getValorPeriodo());
                if (m.getRigeApartirDe().compareTo(fechaMaxima) == 1) {
                    validado = Boolean.FALSE;
                    mostrarMensajeEnPantalla(UtilCadena.concatenar(
                            "La fecha 'Rige a Partir de' (" + UtilFechas.formatear(m.getRigeApartirDe()),
                            ") no puede ser mayor a la fecha permitida (", UtilFechas.formatear(fechaMaxima), ")"),
                            FacesMessage.SEVERITY_WARN);
                }
            }
        } catch (Exception e) {
            validado = Boolean.FALSE;
            e.printStackTrace();
            mostrarMensajeEnPantalla("Se presento un error no contralado al validar la fecha máxima del movimiento",
                    FacesMessage.SEVERITY_ERROR);

        }
        return validado;
    }

    /**
     * Este método actualiza el movimiento.
     *
     * @return String
     */
    public String guardarNumeroMemorandum() {
        try {
            Movimiento movimiento = (Movimiento) BeanUtils.cloneBean(tramiteLegalizacionHelper.getMovimiento());
            guardarMovimientoBitacora(movimiento, movimiento.getNumeroMemo().toUpperCase());
            ejecutarComandoPrimefaces("popUpNumeroMemorandum.hide();");
        } catch (Exception e) {
            error(getClass().getName(), "Error al guaradar el numero de Memorándum", e);
        }
        return null;
    }

    /**
     * Este metodo guarada el registro en bitacora.
     *
     * @param movimiento Movimiento
     * @param numeroDocumentoHabilitante String
     * @throws ServicioException Control errores.
     */
    private void guardarMovimientoBitacora(final Movimiento movimiento,
            final String numeroDocumentoHabilitante) throws ServicioException {
        MovimientoBitacora mb = new MovimientoBitacora();
        iniciarDatosEntidad(mb, Boolean.TRUE);
        mb.setCedula(obtenerUsuario());
        if (obtenerUsuarioConectado().getDistributivoDetalle().getDenominacionPuesto() != null) {
            mb.setCargo(obtenerUsuarioConectado().getDistributivoDetalle().getDenominacionPuesto().getNombre());
        } else {
            mb.setCargo("********");
        }
        mb.setNombre(obtenerNombreUsuario());
        mb.setFecha(new Date());
        mb.setNumeroDocumentoHabilitante(numeroDocumentoHabilitante);
        mb.setMovimiento(tramiteLegalizacionHelper.getMovimiento());
        tramiteServicio.actualizarDocumentoHabilitanteMovimiento(movimiento, mb);
        consultarDatos();
    }

    /**
     * Este metodo guarada el documento habilitante en el movimiento.
     *
     * @return String
     */
    public String guardarDocumentoHabilitante() {
        try {
            Movimiento movimiento = (Movimiento) BeanUtils.cloneBean(
                    tramiteLegalizacionHelper.getMovimiento());
            tramiteLegalizacionHelper.setMovimiento(movimiento);
//            if (movimiento != null){
            if (tramiteServicio.buscarMovimientoPorDocumentoHabilitante(
                    movimiento.getNumeroDocumentoHabilitante()) == null) {
                procesarGuardarMovimientoBitacora();
            } else {
                tramiteLegalizacionHelper.setMensajes(
                        UtilCadena.concatenar("El número de la Acción de Personal que usted a ingresado No.",
                                movimiento.getNumeroDocumentoHabilitante(),
                                ", <br/>se encuentra ocupado por otra Institución,",
                                " por favor verificar si el número es correcto."));
                ejecutarComandoPrimefaces("dlgCofirmacionNumeroDocumentoDuplicado.show();");
                actualizarComponente("frmCofirmacionNumeroDocumentoDuplicado");

            }

//            } else {
//                mostrarMensajeEnPantalla("ec.gob.mrl.smp.tramite.validacion.ducumentoHabilitante.mensaje.error",
//                        FacesMessage.SEVERITY_WARN);
//            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al guardar documento habilitante", ex);
        }
        return null;
    }

    /**
     * Este metodo procesa el guardado de el movimiento bitacora.
     *
     * @return String
     */
    public String procesarGuardarMovimientoBitacora() {
        try {
            Movimiento movimiento = tramiteLegalizacionHelper.getMovimiento();
            guardarMovimientoBitacora(movimiento, movimiento.getNumeroDocumentoHabilitante());
            ejecutarComandoPrimefaces("popUpNumeroDocHab.hide();");
            ejecutarComandoPrimefaces("dlgCofirmacionNumeroDocumentoDuplicado.hide();");
        } catch (Exception ex) {
            error(getClass().getName(), "Error al guardar documento habilitante", ex);
        }
        return null;
    }

    /**
     * @return the tramiteLegalizacionHelper
     */
    public TramiteLegalizacionHelper getTramiteLegalizacionHelper() {
        return tramiteLegalizacionHelper;
    }

    /**
     * @param tramiteLegalizacionHelper the tramiteLegalizacionHelper to set
     */
    public void setTramiteLegalizacionHelper(final TramiteLegalizacionHelper tramiteLegalizacionHelper) {
        this.tramiteLegalizacionHelper = tramiteLegalizacionHelper;
    }
}
