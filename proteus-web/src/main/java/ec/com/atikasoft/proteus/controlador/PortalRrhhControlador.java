/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.PortalRrhhHelper;
import ec.com.atikasoft.proteus.dao.CuentaBancariaDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadoDatoAdiconalServidorEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoCuentaEnum;
import ec.com.atikasoft.proteus.enums.TipoDiplomaEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorCapacitacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorEvaluacion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorExperiencia;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInformacionMedica;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorInstruccion;
import ec.com.atikasoft.proteus.modelo.distributivo.ServidorParienteInstitucion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.FichaPersonalServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
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
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

/**
 * ServidorControlador
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
@ManagedBean(name = "portalRrhhBean")
@ViewScoped
public class PortalRrhhControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(PortalRrhhControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/portal/portal_rrhh.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/portal_rrhh.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de fichaPersonalServicio.
     */
    @EJB
    private FichaPersonalServicio fichaPersonalServicio;

    /**
     * Dao de paratro institucional.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Dao de cuenta bancaria.
     */
    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{portalRrhhHelper}")
    private PortalRrhhHelper portalRrhhHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarEdicionServidor();
    }

    /**
     * MERODO PARA REGRESAR A ALA LISTA.
     *
     * @return
     */
    public String regresarLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * guardar la actualizacion personal público.
     *
     * @return
     */
    public String guardar() {
        try {
            if (portalRrhhHelper.getServidor().getArchivoFotoId() != null) {
                portalRrhhHelper.getServidor().getArchivoFoto().setId(portalRrhhHelper.getServidor().getArchivoFotoId());
            }
            iniciarDatosEntidad(portalRrhhHelper.getServidor(), Boolean.FALSE);
            admServicio.guardarActualizacionFichaPersonal(portalRrhhHelper.getServidor(),
                    portalRrhhHelper.getServidorInstitucion(),
                    obtenerUsuarioConectado().getInstitucion().getId(),
                    portalRrhhHelper.getServidor().getArchivoFoto(),
                    portalRrhhHelper.getListaServidorInstrucciones(),
                    portalRrhhHelper.getListaServidorParienteInstituciones(),
                    portalRrhhHelper.getListaservidorCargaFamiliares(),
                    portalRrhhHelper.getListaServidorExperiencias(),
                    portalRrhhHelper.getListaServidorEvaluaciones(),
                    portalRrhhHelper.getListaServidorCapacitaciones());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar las servidor", ex);
        }
        return null;
    }

    /**
     * evento cambio de tab.
     *
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {
        if ("tab1".equals(event.getTab().getId())) {
            portalRrhhHelper.setTab("tab1");
        }
        if ("tab2".equals(event.getTab().getId())) {
            portalRrhhHelper.setTab("tab2");
        }
        if ("tab3".equals(event.getTab().getId())) {
//            llenarComboPeriodoNomina();
            portalRrhhHelper.setTab("tab3");
        }
        if ("tab4".equals(event.getTab().getId())) {
            portalRrhhHelper.setTab("tab4");
        }
        if ("tab5".equals(event.getTab().getId())) {
            portalRrhhHelper.setTab("tab5");
        }
        if ("tab6".equals(event.getTab().getId())) {
            portalRrhhHelper.setTab("tab6");
        }

    }

    /**
     * Archivo.
     *
     * @param event FileUploadEvent
     */
    public void cargarArchivo(final FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            portalRrhhHelper.setArchivoFoto(file);
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
                portalRrhhHelper.getServidor().setArchivoFoto(new Archivo());
                iniciarDatosEntidad(portalRrhhHelper.getServidor().getArchivoFoto(), Boolean.TRUE);
                portalRrhhHelper.getServidor().getArchivoFoto().setArchivo(UtilArchivos.getBytesFromFile(f));
                portalRrhhHelper.getServidor().getArchivoFoto().setNombre(nombreArchivo);
                portalRrhhHelper.setArchivoFile(UtilArchivos.getFileFromBytes(stream, nombreArchivoSinExt,
                        extencionSinPunto));
                admServicio.guardarArchivoPersonal(portalRrhhHelper.getServidor(), portalRrhhHelper.getArchivoFile());
                iniciarEdicionServidor();
                FacesMessage msg = new FacesMessage("El archivo se subió correctamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            Logger.getLogger(ServidorControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * metodo para llenar los catalogos.
     */
    public void iniciarDatos() throws ServicioException {
        llenarComboTipoSangre();
        llenarComboEtnia();
        llenarCombotallaUniforme();
        llenarComboNumeroCalzado();
        llenarComboCapacidadEspecial();
        llenarComboNacionalidad();
        buscarListasCapacitacion();
        buscarListasEvaluacion();
        buscarListasExperiencia();
        buscarListasInstruccion();
        llenarComboTipoGenero();
        llenarComboEspadoCivil();
        buscarListasParientes();
        buscarListasCargaFamiliares();
//        buscarListasInformacionMedica();
        camposDiscapacidad();
        llenarComboPais();
        if (portalRrhhHelper.getServidor().getUbicacionGeograficaParroquiDomicilioId() != null) {
            setearUbicacionGeografica();
        }
        llenarComboProvincia();
        llenarComboCanton();
        llenarComboParroquia();
        llenarComboNivelInstruccion();
        llenarComboParentezco();
        llenarComboDependencia();
        llenarComboEnfermedades();
        llenarComboParentezcoDiscapacidades();
        llenarComboDiscapacidades();
        llenarComboEnfermedadCatastrofica();
        llenarComboParentezcoEnfermedadCatastrofica();
        iniciarComboTipoDiploma();
        buscarListasBancos();
        buscarListasTiposCuentas();
        modificacionDecimos();

    }

    /**
     * Verifica si se pueden modificar los pagos de decimos.
     *
     * @throws ServicioException
     */
    private void modificacionDecimos() throws ServicioException {
        portalRrhhHelper.setModificaDecimos(Boolean.FALSE);
        Date fechaIngreso = portalRrhhHelper.getServidor().getFechaIngresoInstitucion();
        if (UtilFechas.obtenerAnio(fechaIngreso).compareTo(
                UtilFechas.obtenerAnio(obtenerUsuarioConectado().getEjercicioFiscal().getFechaInicio())) < 0) {
            ParametroInstitucional meses = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.MES_MODIFICACION_DECIMOS.getCodigo());
            if (UtilFechas.obtenerMes(new Date()).compareTo(meses.getValorNumerico().intValue()) <= 0) {
                portalRrhhHelper.setModificaDecimos(Boolean.TRUE);
            }
        } else {
            ParametroInstitucional meses = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.MES_MODIFICACION_DECIMOS_SERVIDORES_NUEVOS.getCodigo());
            if (UtilFechas.obtenerMes(new Date()).compareTo(UtilFechas.obtenerMes(fechaIngreso) + meses.getValorNumerico().intValue() - 1) <= 0) {
                portalRrhhHelper.setModificaDecimos(Boolean.TRUE);
            }
        }

    }

    /**
     * Inicializa el transient de tipo de cuenta TipoCuentaEnum
     */
    private void iniciarCuentas() {
        for (CuentaBancaria cb : portalRrhhHelper.getServidor().getCuentasBancarias()) {
            cb.setTipoCuentaEnum(TipoCuentaEnum.buscarPorCodigo(cb.getTipoCuenta()));
            if (cb.getPagoNomina() == null) {
                cb.setPagoNomina(Boolean.FALSE);
            }
        }
    }

    /**
     * Metodo de busqueda del servidor en session.
     *
     * @return
     */
    public String iniciarEdicionServidor() {
        try {
            if (portalRrhhHelper.getServidor() != null) {
                iniciarDatos();
                List<CuentaBancaria> cuentasBancariasVigentes = cuentaBancariaDao.buscarVigentePorServidor(portalRrhhHelper.getServidor().getId());
                portalRrhhHelper.getServidor().setCuentasBancarias(cuentasBancariasVigentes);
                iniciarCuentas();
                portalRrhhHelper.setFotoServidor("/images/silueta.gif");
                if (portalRrhhHelper.getServidor().getArchivoFotoId() != null) {
                    String logoId = portalRrhhHelper.getServidor().getArchivoFotoId().toString();
                    portalRrhhHelper.setFotoServidor(UtilCadena.concatenar("/imageServlet/", logoId));
                }
            }
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la edicion en busca del servidor", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * ********************** SERVIDOR CAPACITACIÓN*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogCapacitacion() {
        try {
            portalRrhhHelper.setServidorCapacitacion(new ServidorCapacitacion());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("reclutamientoCapacitacion.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * Buscar lista de bancos
     */
    private void buscarListasBancos() {
        try {
            iniciarCombos(portalRrhhHelper.getBancos());
            List<Banco> bancos = admServicio.listarTodasBancosVigente();
            for (Banco b : bancos) {
                portalRrhhHelper.getBancos().add(new SelectItem(b.getId(), b.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
    }

    /**
     * Buscar listas tipo de cuentas
     */
    private void buscarListasTiposCuentas() {
        try {
            iniciarCombos(portalRrhhHelper.getTiposCuenta());
            TipoCuentaEnum[] tiposCuentaEnum = TipoCuentaEnum.values();
            if (tiposCuentaEnum != null) {
                for (int i = 0; i < tiposCuentaEnum.length; i++) {
                    portalRrhhHelper.getTiposCuenta().add(new SelectItem(tiposCuentaEnum[i].getCodigo(), tiposCuentaEnum[i].getDescripcion()));
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasCapacitacion() {
        try {
            portalRrhhHelper.getListaServidorCapacitaciones().clear();
            portalRrhhHelper.setListaServidorCapacitaciones(
                    fichaPersonalServicio.listarTodosReclutamientoCapacitacionPorIdReclutado(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorCapacitacion se : portalRrhhHelper.getListaServidorCapacitaciones()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasCapacitacion() {
        try {

            Object capacitacion = BeanUtils.cloneBean(portalRrhhHelper.getServidorCapacitacionEditDelete());
            ServidorCapacitacion a = (ServidorCapacitacion) capacitacion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorCapacitacion(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
//            buscarListasCargaFamiliares();
            buscarListasCapacitacion();
            ejecutarComandoPrimefaces("reclutamientoCapacitacion.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorCapacitacion() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorCapacitacion(), Boolean.TRUE);
                portalRrhhHelper.getServidorCapacitacion().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                portalRrhhHelper.getServidorCapacitacion().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorCapacitacion().setServidorId(portalRrhhHelper.getServidor().getId());
                fichaPersonalServicio.guardarServidorCapacitacion(portalRrhhHelper.getServidorCapacitacion());
                buscarListasCapacitacion();
                ejecutarComandoPrimefaces("reclutamientoCapacitacion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                fichaPersonalServicio.actualizarServidorCapacitacion(portalRrhhHelper.getServidorCapacitacion());
                buscarListasCapacitacion();
                ejecutarComandoPrimefaces("reclutamientoCapacitacion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar instrucción de servidor", e);
        }
        return null;
    }

    /**
     * Guarda una Cuenta Bancaria
     */
    public void guardarCuentaBancaria() {
        CuentaBancaria cuentaBancaria = portalRrhhHelper.getCuentaBancaria();
        boolean errores = false;
        if (cuentaBancaria.getBanco() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.fichaPersonal.cuentaBancaria.requerido.banco", FacesMessage.SEVERITY_ERROR);
            errores = true;
        }
        if (cuentaBancaria.getTipoCuenta() == null) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.fichaPersonal.cuentaBancaria.requerido.tipoCuenta", FacesMessage.SEVERITY_ERROR);
            errores = true;
        }
        CuentaBancaria existente = /*admServicio.buscarCuentaPorBancoTipoYNumero(cuentaBancaria.getBanco().getId(),
                 cuentaBancaria.getTipoCuenta(), cuentaBancaria.getNumerCuenta());*/ buscarCuentaEnMemoria();
        boolean iguales = (cuentaBancaria.getId() != null && existente != null) && (cuentaBancaria.getId().equals(existente.getId()));
        if (existente != null && ((cuentaBancaria.getId() == null) || (cuentaBancaria.getId() != null && !iguales))) {
            mostrarMensajeEnPantalla("ec.gob.mrl.smp.administracion.fichaPersonal.cuentaBancaria.error.unica", FacesMessage.SEVERITY_ERROR);
            errores = true;
        }
        if (!errores) {
            if (portalRrhhHelper.getEsNuevoDialog()) {
                try {
                    cuentaBancaria.setBanco(admServicio.buscarBanco(cuentaBancaria.getBanco().getId()));
                    cuentaBancaria.setBancoId(cuentaBancaria.getBanco().getId());
                } catch (ServicioException ex) {
                    Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                portalRrhhHelper.getServidor().getCuentasBancarias().add(cuentaBancaria);
            }
            iniciarCuentas();
            if (cuentaBancaria.getPagoNomina() != null && cuentaBancaria.getPagoNomina() == true) {
                cambiarCuenta(cuentaBancaria);
            }
            portalRrhhHelper.setCuentaBancaria(null);
            ejecutarComandoPrimefaces("cuentaBancariaDlg.hide()");
        }
    }

    /**
     * Para poder tener en cuenta los cambios en memoria
     *
     * @return
     */
    public CuentaBancaria buscarCuentaEnMemoria() {
        CuentaBancaria cuentaBancaria = portalRrhhHelper.getCuentaBancaria();
        for (CuentaBancaria c : portalRrhhHelper.getServidor().getCuentasBancarias()) {
            if (c.getBanco().getId().equals(cuentaBancaria.getBanco().getId())
                    && c.getTipoCuenta().equals(cuentaBancaria.getTipoCuenta())
                    && c.getNumerCuenta().equals(cuentaBancaria.getNumerCuenta())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Elimina una Cuenta Bancaria de la lista de cuentas del servidor, esta
     * info se sincroniza en bd al guardar todo el formulario
     */
    public void eliminarCuentaBancaria() {
        CuentaBancaria cuentaBancaria = portalRrhhHelper.getCuentaBancaria();
        portalRrhhHelper.getServidor().getCuentasBancarias().remove(cuentaBancaria);
        /*actualizando vigencia en bdd*/
        cuentaBancaria.setVigente(Boolean.FALSE);
        try {
            cuentaBancariaDao.actualizar(cuentaBancaria);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(e.toString(), FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarCapacitacion() {
        try {
            fichaPersonalServicio.eliminarServidorCapacitacion(portalRrhhHelper.getServidorCapacitacionEditDelete());
            portalRrhhHelper.getListaServidorCapacitaciones().
                    remove(portalRrhhHelper.getServidorCapacitacionEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de capacitacion", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR EVALUACIÓN*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogEvaluacion() {
        try {
            portalRrhhHelper.setServidorEvaluacion(new ServidorEvaluacion());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("servidorEvaluacion.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasEvaluacion() {
        try {
            portalRrhhHelper.getListaServidorEvaluaciones().clear();
            portalRrhhHelper.setListaServidorEvaluaciones(
                    fichaPersonalServicio.listarTodosServidorEvaluacionPorIdServidor(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorEvaluacion se : portalRrhhHelper.getListaServidorEvaluaciones()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasEvaluacion() {
        try {

            Object evaluacion = BeanUtils.cloneBean(portalRrhhHelper.getServidorEvaluacionEditDelete());
            ServidorEvaluacion a = (ServidorEvaluacion) evaluacion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorEvaluacion(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("servidorEvaluacion.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorEvaluacion() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorEvaluacion(), Boolean.TRUE);
                portalRrhhHelper.getServidorEvaluacion().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorEvaluacion().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                portalRrhhHelper.getServidorEvaluacion().setServidorId(portalRrhhHelper.getServidor().getId());
                fichaPersonalServicio.guardarServidorEvaluacion(portalRrhhHelper.getServidorEvaluacion());
                buscarListasEvaluacion();
                ejecutarComandoPrimefaces("servidorEvaluacion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                fichaPersonalServicio.actualizarServidorEvaluacion(portalRrhhHelper.getServidorEvaluacion());
                buscarListasEvaluacion();
                ejecutarComandoPrimefaces("servidorEvaluacion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar evaluacion", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarEvaluacion() {
        try {

            fichaPersonalServicio.eliminarServidorEvaluacion(portalRrhhHelper.getServidorEvaluacionEditDelete());
            portalRrhhHelper.getListaServidorEvaluaciones().
                    remove(portalRrhhHelper.getServidorEvaluacionEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de evaluacion", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR EXPERIENCIA*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogExperiencia() {
        try {

            portalRrhhHelper.setServidorExperiencia(new ServidorExperiencia());
            iniciarDatosEntidad(portalRrhhHelper.getServidorExperiencia(), Boolean.FALSE);
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("servidorExperiencia.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasExperiencia() {
        try {
            portalRrhhHelper.getListaServidorExperiencias().clear();
            portalRrhhHelper.setListaServidorExperiencias(
                    fichaPersonalServicio.listarTodosServidorExperienciaPorIdServidor(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorExperiencia se : portalRrhhHelper.getListaServidorExperiencias()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasExperiencia() {
        try {

            Object evaluacion = BeanUtils.cloneBean(portalRrhhHelper.getServidorExperienciaEditDelete());
            ServidorExperiencia a = (ServidorExperiencia) evaluacion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorExperiencia(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("servidorExperiencia.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorExperiencia() {
        try {
            Boolean estado = Boolean.TRUE;
            if (portalRrhhHelper.getServidorExperiencia().getFechaDesde() != null
                    && portalRrhhHelper.getServidorExperiencia().getFechaHasta() != null) {
                Long fechaDesde = portalRrhhHelper.getServidorExperiencia().getFechaDesde().getTime();
                Long fechaHasta = portalRrhhHelper.getServidorExperiencia().getFechaHasta().getTime();
                if (fechaDesde > fechaHasta) {
                    estado = Boolean.FALSE;
                    mostrarMensajeEnPantalla("LA FECHA DESDE NO PUEDE SER MAYOR A LA FECHA HASTA",
                            FacesMessage.SEVERITY_ERROR);
                }
            }
            if (estado) {
                if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                    iniciarDatosEntidad(portalRrhhHelper.getServidorExperiencia(), Boolean.TRUE);
                    portalRrhhHelper.getServidorExperiencia().setServidor(portalRrhhHelper.getServidor());
                    portalRrhhHelper.getServidorExperiencia().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                    portalRrhhHelper.getServidorExperiencia().setServidorId(portalRrhhHelper.getServidor().getId());
                    fichaPersonalServicio.guardarServidorExperiencia(portalRrhhHelper.getServidorExperiencia());
                    buscarListasExperiencia();
                    ejecutarComandoPrimefaces("servidorExperiencia.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                } else {
                    fichaPersonalServicio.actualizarServidorExperiencia(portalRrhhHelper.getServidorExperiencia());
                    buscarListasExperiencia();
                    ejecutarComandoPrimefaces("servidorExperiencia.hide()");
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar experiencia", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarExperiencia() {
        try {

            fichaPersonalServicio.eliminarServidorExperiencia(portalRrhhHelper.getServidorExperienciaEditDelete());
            portalRrhhHelper.getListaServidorExperiencias().
                    remove(portalRrhhHelper.getServidorExperienciaEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de experiencia", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR INSTRUCCION*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogInstruccion() {
        try {
            portalRrhhHelper.setServidorInstruccion(new ServidorInstruccion());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("servidorInstruccion.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasInstruccion() {
        try {
            portalRrhhHelper.getListaServidorInstrucciones().clear();
            portalRrhhHelper.setListaServidorInstrucciones(
                    fichaPersonalServicio.listarTodosServidorInstruccionPorIdServidor(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorInstruccion se : portalRrhhHelper.getListaServidorInstrucciones()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda reclutamiento capacitacion", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasInstruccion() {
        try {

            Object instruccion = BeanUtils.cloneBean(portalRrhhHelper.getServidorInstruccionEditDelete());
            ServidorInstruccion a = (ServidorInstruccion) instruccion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorInstruccion(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("servidorInstruccion.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorInstruccion() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorInstruccion(), Boolean.TRUE);
                portalRrhhHelper.getServidorInstruccion().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorInstruccion().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                portalRrhhHelper.getServidorInstruccion().setServidorId(portalRrhhHelper.getServidor().getId());
                fichaPersonalServicio.guardarServidorInstruccion(portalRrhhHelper.getServidorInstruccion());
                buscarListasInstruccion();
                ejecutarComandoPrimefaces("servidorInstruccion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                fichaPersonalServicio.actualizarServidorInstruccion(portalRrhhHelper.getServidorInstruccion());
                buscarListasInstruccion();
                ejecutarComandoPrimefaces("servidorInstruccion.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar  instrucción", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarInstruccion() {
        try {

            fichaPersonalServicio.eliminarServidorInstruccion(portalRrhhHelper.getServidorInstruccionEditDelete());
            portalRrhhHelper.getListaServidorInstrucciones().
                    remove(portalRrhhHelper.getServidorInstruccionEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de instruccion", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR PARIENTES INSTITUCIÓN*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogParientes() {
        try {
            System.out.println("el nombre del la discacpacidad es**********************" + portalRrhhHelper.getServidor().
                    getCatalogoCapacidadesId());
            portalRrhhHelper.setServidorParienteInstitucion(new ServidorParienteInstitucion());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("servidorParientes.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * ********************* Cuenta bancaria
     */
    public String presentarDialogCuentaBancaria() {
        try {
            CuentaBancaria cuenta = new CuentaBancaria();
            iniciarDatosEntidad(cuenta, Boolean.TRUE);
            cuenta.setServidor(portalRrhhHelper.getServidor());
            cuenta.setServidorId(portalRrhhHelper.getServidor().getId());
            cuenta.setBanco(new Banco());
            portalRrhhHelper.setCuentaBancaria(cuenta);
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("cuentaBancariaDlg.show()");
        } catch (Exception e) {
            error(getClass().getName(), "ec.gob.mrl.smp.administracion.fichaPersonal.cuentaBancaria.error", e);
        }
        return null;
    }

    public void iniciarEdicionCuentaBancaria() {
        ejecutarComandoPrimefaces("cuentaBancariaDlg.show()");
        portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
        iniciarDatosEntidad(portalRrhhHelper.getCuentaBancaria(), Boolean.FALSE);
    }

    public void cambiarCuentaPagaNomina(AjaxBehaviorEvent event) {
        CuentaBancaria cuentaBancaria = (CuentaBancaria) event.getComponent().getAttributes().get("cuenta");
        cambiarCuenta(cuentaBancaria);
    }

    private void cambiarCuenta(final CuentaBancaria cuentaBancaria) {
        if (cuentaBancaria.getPagoNomina()) {
            for (CuentaBancaria cb : portalRrhhHelper.getServidor().getCuentasBancarias()) {
                if (!cb.getNumerCuenta().equals(cuentaBancaria.getNumerCuenta())) {
                    cb.setPagoNomina(Boolean.FALSE);
                }
            }
        }
    }

    public String tipoCuenta(String tipo) {
        for (TipoCuentaEnum en : TipoCuentaEnum.values()) {
            if (tipo.equals(en.getCodigo())) {
                return en.getDescripcion();
            }
        }
        return "***";
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasParientes() {
        try {
            portalRrhhHelper.getListaServidorParienteInstituciones().clear();
            portalRrhhHelper.setListaServidorParienteInstituciones(
                    fichaPersonalServicio.listarTodosServidorParientesPorIdServidor(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorParienteInstitucion se : portalRrhhHelper.getListaServidorParienteInstituciones()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda ", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasParientes() {
        try {

            Object instruccion = BeanUtils.cloneBean(portalRrhhHelper.getServidorParienteInstitucionEditDelete());
            ServidorParienteInstitucion a = (ServidorParienteInstitucion) instruccion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorParienteInstitucion(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("servidorParientes.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorParientes() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorParienteInstitucion(), Boolean.TRUE);
                portalRrhhHelper.getServidorParienteInstitucion().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorParienteInstitucion().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                portalRrhhHelper.getServidorParienteInstitucion().setServidorid(portalRrhhHelper.getServidor().getId());
                fichaPersonalServicio.guardarServidorParientes(portalRrhhHelper.getServidorParienteInstitucion());
                buscarListasParientes();
                ejecutarComandoPrimefaces("servidorParientes.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                fichaPersonalServicio.actualizarServidorParientes(portalRrhhHelper.getServidorParienteInstitucion());
                buscarListasParientes();
                ejecutarComandoPrimefaces("servidorParientes.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar parientes", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarParientes() {
        try {

            fichaPersonalServicio.eliminarServidorParientes(portalRrhhHelper.getServidorParienteInstitucionEditDelete());
            portalRrhhHelper.getListaServidorParienteInstituciones().
                    remove(portalRrhhHelper.getServidorParienteInstitucionEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de parientes", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR INFORMACION MEDICA*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogInformacionMedica() {
        try {
            portalRrhhHelper.setServidorInformacionMedica(new ServidorInformacionMedica());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("servidorInformacionMedica.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasInformacionMedica() {
        try {
            portalRrhhHelper.getListaServidorInformacionMedicas().clear();
            portalRrhhHelper.setListaServidorInformacionMedicas(
                    fichaPersonalServicio.listarTodosServidorInformacionMedicaPorIdServidor(
                            portalRrhhHelper.getServidor().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda ", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasInformacionMedica() {
        try {

            Object instruccion = BeanUtils.cloneBean(portalRrhhHelper.getServidorInformacionMedicaEditDelete());
            ServidorInformacionMedica a = (ServidorInformacionMedica) instruccion;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorInformacionMedica(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            ejecutarComandoPrimefaces("servidorInformacionMedica.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorInformacionMedica() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidorInformacionMedica() != null && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorInformacionMedica(), Boolean.TRUE);
                portalRrhhHelper.getServidorInformacionMedica().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorInformacionMedica().setServidorId(portalRrhhHelper.getServidor().getId());
                fichaPersonalServicio.guardarServidorInformacionMedica(portalRrhhHelper.getServidorInformacionMedica());
                buscarListasInformacionMedica();
                ejecutarComandoPrimefaces("servidorInformacionMedica.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else if (portalRrhhHelper.getServidorInformacionMedica() != null) {
                fichaPersonalServicio.actualizarServidorInformacionMedica(
                        portalRrhhHelper.getServidorInformacionMedica());
                buscarListasInformacionMedica();
                ejecutarComandoPrimefaces("servidorInformacionMedica.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar inf medica", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarInformacionMedica() {
        try {

            fichaPersonalServicio.eliminarServidorInformacionMedica(portalRrhhHelper.
                    getServidorInformacionMedicaEditDelete());
            portalRrhhHelper.getListaServidorInformacionMedicas().
                    remove(portalRrhhHelper.getServidorInformacionMedicaEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de inf medica", ex);
        }
        return null;
    }

    /**
     * ********************** SERVIDOR CARGAS FAMILIARES*****************
     */
    /**
     * presentar dialog y validar nuevo.
     *
     * @return
     */
    public String presentarDialogCargosFamiliares() {
        try {
            portalRrhhHelper.setServidorCargaFamiliar(new ServidorCargaFamiliar());
            portalRrhhHelper.setEsNuevoDialog(Boolean.TRUE);
            ejecutarComandoPrimefaces("cargasFamiliares.show()");
        } catch (Exception e) {
            error(getClass().getName(), "Error al presentar dialogo instrucción", e);
        }
        return null;
    }

    /**
     * metodo para buscar las listas capacitación.
     *
     * @return
     */
    public String buscarListasCargaFamiliares() {
        try {
            portalRrhhHelper.getListaservidorCargaFamiliares().clear();
            portalRrhhHelper.setListaservidorCargaFamiliares(
                    fichaPersonalServicio.listarTodosCargaFamiliar(
                            portalRrhhHelper.getServidor().getId()));
            for (ServidorCargaFamiliar se : portalRrhhHelper.getListaservidorCargaFamiliares()) {
                EstadoDatoAdiconalServidorEnum enumV = EstadoDatoAdiconalServidorEnum.obtener(se.getEstado());
                se.setEstadoNombre(enumV != null ? enumV.getNombre() : null);
                se.setBloqueado((enumV != null && !EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo().equals(se.getEstado())));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda cargas familiares", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para editar las listas de los tabs.
     *
     * @return
     */
    public String iniciarEdicionListasCargaFamiliar() {
        try {

            Object cargaFamiliar = BeanUtils.cloneBean(portalRrhhHelper.getServidorCargaFamiliarEditDelete());
            ServidorCargaFamiliar a = (ServidorCargaFamiliar) cargaFamiliar;
            iniciarDatosEntidad(a, Boolean.FALSE);
            portalRrhhHelper.setServidorCargaFamiliar(a);
            portalRrhhHelper.setEsNuevoDialog(Boolean.FALSE);
            buscarListasCargaFamiliares();
            ejecutarComandoPrimefaces("cargasFamiliares.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return null;
    }

    /**
     * metodo de guardar.
     *
     * @return
     */
    public String guardarServidorCargaFamiliar() {
        try {
            if (portalRrhhHelper.getEsNuevoDialog() && portalRrhhHelper.getServidor().getId() != null) {
                iniciarDatosEntidad(portalRrhhHelper.getServidorCargaFamiliar(), Boolean.TRUE);
                portalRrhhHelper.getServidorCargaFamiliar().setServidor(portalRrhhHelper.getServidor());
                portalRrhhHelper.getServidorCargaFamiliar().setServidorId(portalRrhhHelper.getServidor().getId());
                portalRrhhHelper.getServidorCargaFamiliar().setEstado(EstadoDatoAdiconalServidorEnum.REGISTRADO.getCodigo());
                fichaPersonalServicio.guardarServidorCargaFamiliar(portalRrhhHelper.getServidorCargaFamiliar());
                buscarListasCargaFamiliares();
                ejecutarComandoPrimefaces("cargasFamiliares.hide()");
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            } else {
                fichaPersonalServicio.actualizarServidorCargaFamiliar(portalRrhhHelper.getServidorCargaFamiliar());
                buscarListasCargaFamiliares();
                ejecutarComandoPrimefaces("cargasFamiliares.hide()");
                mostrarMensajeEnPantalla(REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar cargas familiares", e);
        }
        return null;
    }

    /**
     * eliminar de las listas.
     *
     * @return
     */
    public String borrarCargaFamiliar() {
        try {

            fichaPersonalServicio.eliminarServidorCargaFamiliar(portalRrhhHelper.getServidorCargaFamiliarEditDelete());
            portalRrhhHelper.getListaservidorCargaFamiliares().
                    remove(portalRrhhHelper.getServidorCargaFamiliarEditDelete());
            //buscarListasCapacitacion();
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion de cargas familiares", ex);
        }
        return null;
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboNivelInstruccion() {
        try {
            portalRrhhHelper.getListaNivelInstruccion().clear();
            iniciarCombos(portalRrhhHelper.getListaNivelInstruccion());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.NIVEL_INSTRUCCION.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaNivelInstruccion().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo Diploma.
     */
    private void iniciarComboTipoDiploma() {
        portalRrhhHelper.getListaTipoDiploma().clear();
        iniciarCombos(portalRrhhHelper.getListaTipoDiploma());
        for (TipoDiplomaEnum t : TipoDiplomaEnum.values()) {
            portalRrhhHelper.getListaTipoDiploma().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDiplomaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboTipoSangre() {
        try {
            portalRrhhHelper.getListaTipoSangre().clear();
            iniciarCombos(portalRrhhHelper.getListaTipoSangre());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.TIPO_SANGRE.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaTipoSangre().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboTipoGenero() {
        try {
            portalRrhhHelper.getListaGenero().clear();
            iniciarCombos(portalRrhhHelper.getListaGenero());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.GENERO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaGenero().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboEspadoCivil() {
        try {
            portalRrhhHelper.getListaEstadoCivil().clear();
            iniciarCombos(portalRrhhHelper.getListaEstadoCivil());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ESTADO_CIVIL.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaEstadoCivil().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboDiscapacidades() {
        try {
            portalRrhhHelper.getListaDiscapacidades().clear();
            iniciarCombos(portalRrhhHelper.getListaDiscapacidades());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.DISCAPACIDADES.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaDiscapacidades().add(new SelectItem(g.getId(), g.getNombre()));
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo para limpiar los registros.
     *
     * @return
     */
    public void camposDiscapacidad() {
        try {
            if (portalRrhhHelper.getServidor().getCatalogoCapacidadesId() != null) {
                Catalogo catalogo = admServicio.buscarCatalogoPorId(
                        portalRrhhHelper.getServidor().getCatalogoCapacidadesId());
                if ("NINGUNO".equals(catalogo.getNombre())) {
                    portalRrhhHelper.setDiscapacidad(Boolean.TRUE);
                    portalRrhhHelper.getServidor().setNumeroCarnetConadis(null);
                    portalRrhhHelper.getServidor().setPorcentajeDiscapacidad(BigDecimal.ZERO);
                } else {
                    portalRrhhHelper.setDiscapacidad(Boolean.FALSE);
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboEnfermedadCatastrofica() {
        try {
            portalRrhhHelper.getListaEnfermedadesCatastroficas().clear();
            iniciarCombos(portalRrhhHelper.getListaEnfermedadesCatastroficas());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ENFERMEDADES_CATASTROFICAS.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaEnfermedadesCatastroficas().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de tipo sangre
     * desde la tabla catalogos.
     */
    public void llenarComboParentezcoEnfermedadCatastrofica() {
        try {
            portalRrhhHelper.getListaParentescoEnfermedadCatastrofica().clear();
            iniciarCombos(portalRrhhHelper.getListaParentescoEnfermedadCatastrofica());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.PARENTEZCO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaParentescoEnfermedadCatastrofica().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboParentezco() {
        try {
            portalRrhhHelper.getListaParentezco().clear();
            iniciarCombos(portalRrhhHelper.getListaParentezco());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.PARENTEZCO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaParentezco().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboParentezcoDiscapacidades() {
        try {
            portalRrhhHelper.getListaParentestoConDiscapacidad().clear();
            iniciarCombos(portalRrhhHelper.getListaParentestoConDiscapacidad());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.PARENTEZCO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaParentestoConDiscapacidad().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboEnfermedades() {
        try {
            portalRrhhHelper.getListaEnfermedades().clear();
            iniciarCombos(portalRrhhHelper.getListaEnfermedades());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ENFERMEDADES.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaEnfermedades().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboDependencia() {
        try {
            portalRrhhHelper.getListaDependencia().clear();
            iniciarCombos(portalRrhhHelper.getListaDependencia());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.DEPENDENCIA.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaDependencia().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboEstadoCivil() {
        try {
            portalRrhhHelper.getListaTipoEstadoCivil().clear();
            iniciarCombos(portalRrhhHelper.getListaTipoEstadoCivil());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ESTADO_CIVIL.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaTipoSangre().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarCombotallaUniforme() {
        try {
            portalRrhhHelper.getListaTallaUniforme().clear();
            iniciarCombos(portalRrhhHelper.getListaTallaUniforme());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.TALLA_UNIFORME.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaTallaUniforme().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboNumeroCalzado() {
        try {
            portalRrhhHelper.getListaNumeroCalzado().clear();
            iniciarCombos(portalRrhhHelper.getListaNumeroCalzado());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.NUMERO_CALZADO.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaNumeroCalzado().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboCapacidadEspecial() {
        try {
            portalRrhhHelper.getListaCapacidadEspecial().clear();
            iniciarCombos(portalRrhhHelper.getListaCapacidadEspecial());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.DISCAPACIDADES.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaCapacidadEspecial().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboNacionalidad() {
        try {
            portalRrhhHelper.getListaNacionalidad().clear();
            iniciarCombos(portalRrhhHelper.getListaNacionalidad());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.NACIONALIDAD.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaNacionalidad().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de etnia desde la
     * tabla catalogos.
     */
    public void llenarComboEtnia() {
        try {
            portalRrhhHelper.getListaTipoEtnia().clear();
            iniciarCombos(portalRrhhHelper.getListaTipoEtnia());
            List<Catalogo> listaCatalogo = admServicio.listarTodosPorTipoCatalogoCodigo(
                    TipoCatalogoEnum.ETNIA.getCodigo());
            for (Catalogo g : listaCatalogo) {
                portalRrhhHelper.getListaTipoEtnia().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de ubicacion
     * geografica.
     */
    public void setearUbicacionGeografica() {
        //System.out.println("ubicacion geografica: " + portalRrhhHelper.getServidor().getUbicacionGeograficaParroquiDomicilioId());
        if (portalRrhhHelper.getServidor().getUbicacionGeograficaParroquiDomicilioId() != null) {
            UbicacionGeografica ubicacion = portalRrhhHelper.getServidor().getUbicacionGeograficaParroquiDomicilio();
            portalRrhhHelper.setCanto(ubicacion.getUbicacionGeografica().getId());
            portalRrhhHelper.setProvincia(ubicacion.getUbicacionGeografica().getUbicacionGeografica().getId());
            portalRrhhHelper.setPais(ubicacion.getUbicacionGeografica().getUbicacionGeografica().getUbicacionGeografica().getId());
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboPais() {
        try {
            portalRrhhHelper.getListaPais().clear();
            iniciarCombos(portalRrhhHelper.getListaPais());
            List<UbicacionGeografica> listaUbicacion = admServicio.listarTodosPaisesUbicacionGeografica();
            for (UbicacionGeografica g : listaUbicacion) {
                portalRrhhHelper.getListaPais().add(new SelectItem(g.getId(), g.getNombre()));

            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarComboProvincia() {
        try {
            portalRrhhHelper.getListaProvincia().clear();
            iniciarCombos(portalRrhhHelper.getListaProvincia());
            if (portalRrhhHelper.getPais() != null) {
                List<UbicacionGeografica> listaUbicacion = admServicio.listarTodosIdUbicacionGeografica(portalRrhhHelper.
                        getPais());
                for (UbicacionGeografica g : listaUbicacion) {
                    portalRrhhHelper.getListaProvincia().add(new SelectItem(g.getId(), g.getNombre()));

                }
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboCanton() {
        try {
            portalRrhhHelper.getListaCanto().clear();
            iniciarCombos(portalRrhhHelper.getListaCanto());
            if (portalRrhhHelper.getProvincia() != null) {
                List<UbicacionGeografica> listaUbicacion = admServicio.listarTodosIdUbicacionGeografica(
                        portalRrhhHelper.getProvincia());
                for (UbicacionGeografica g : listaUbicacion) {
                    portalRrhhHelper.getListaCanto().add(new SelectItem(g.getId(), g.getNombre()));

                }
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que se encarga de llenar la lista para el combo de grupo desde la
     * tabla grupos.
     */
    public void llenarComboParroquia() {
        try {
            portalRrhhHelper.getListaParroquia().clear();
            iniciarCombos(portalRrhhHelper.getListaParroquia());
            if (portalRrhhHelper.getCanto() != null) {
                List<UbicacionGeografica> listaUbicacion = admServicio.listarTodosIdUbicacionGeografica(
                        portalRrhhHelper.getCanto());
                for (UbicacionGeografica g : listaUbicacion) {
                    portalRrhhHelper.getListaParroquia().add(new SelectItem(g.getId(), g.getNombre()));

                }
            }
        } catch (ServicioException ex) {
            Logger.getLogger(ClaseControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEstadoCapacitacionServidor(AjaxBehaviorEvent event) {
        /*ServidorCapacitacion servidorCapacitacion = (ServidorCapacitacion) event.getComponent().getAttributes().get("capacitacion");
         try {
         fichaPersonalServicio.actualizarServidorCapacitacion(servidorCapacitacion);
         buscarListasCapacitacion();
         mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    public void actualizarEstadoEvaluacionServidor(AjaxBehaviorEvent event) {
        /* ServidorEvaluacion servidorEvaluacion = (ServidorEvaluacion) event.getComponent().getAttributes().get("evaluacion");
         try {
         fichaPersonalServicio.actualizarServidorEvaluacion(servidorEvaluacion);
         buscarListasEvaluacion();
         mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    public void actualizarEstadoExperienciaServidor(AjaxBehaviorEvent event) {
        /*ServidorExperiencia servidorExperiencia = (ServidorExperiencia) event.getComponent().getAttributes().get("experiencia");
         try {
         fichaPersonalServicio.actualizarServidorExperiencia(servidorExperiencia);
         buscarListasExperiencia();
         mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    public void actualizarEstadoInstruccionServidor(AjaxBehaviorEvent event) {
        /* ServidorInstruccion servidorInstruccion = (ServidorInstruccion) event.getComponent().getAttributes().get("instruccion");
         try {
         //fichaPersonalServicio.actualizarServidorInstruccion(servidorInstruccion);
         //buscarListasInstruccion();
         //mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    public void actualizarEstadoParienteServidor(AjaxBehaviorEvent event) {
        /* ServidorParienteInstitucion servidorParienteInstitucion = (ServidorParienteInstitucion) event.getComponent().getAttributes().get("pariente");
         try {
         fichaPersonalServicio.actualizarServidorParientes(servidorParienteInstitucion);
         buscarListasParientes();
         mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    public void actualizarEstadoFamiliarServidor(AjaxBehaviorEvent event) {
        /*ServidorCargaFamiliar servidorCargaFamiliar = (ServidorCargaFamiliar) event.getComponent().getAttributes().get("familiar");
         try {
         fichaPersonalServicio.actualizarServidorCargaFamiliar(servidorCargaFamiliar);
         buscarListasCargaFamiliares();
         mostrarMensajeEnPantalla("message.actualizar", FacesMessage.SEVERITY_INFO);
         } catch (ServicioException ex) {
         Logger.getLogger(PortalRrhhControlador.class.getName()).log(Level.SEVERE, null, ex);
         mostrarMensajeEnPantalla("message.errorActualizar", FacesMessage.SEVERITY_ERROR);
         }*/
    }

    /**
     * @return the portalRrhhHelper
     */
    public PortalRrhhHelper getPortalRrhhHelper() {
        return portalRrhhHelper;
    }

    /**
     * @param portalRrhhHelper the portalRrhhHelper to set
     */
    public void setPortalRrhhHelper(final PortalRrhhHelper portalRrhhHelper) {
        this.portalRrhhHelper = portalRrhhHelper;
    }
    /* // codigo html de informacion medica del servidor
    
     <!-- PopUp SERVIDOR INFORMACIÓN MEDICA. -->
     <!--  
     <p:dialog 
     widgetVar="servidorInformacionMedica"
     draggable="#{false}"                          
     modal="#{true}"
     dynamic="#{true}"
     style="overflow: auto"
     header="Información Médica">
     <h:form id="frmServidorInformacionMedica">

     <h:panelGrid columns="4" id="panelservidorInformacionMedica"> 
     <p:growl id="growlVisor" showDetail="false" sticky="false" /> 
     <h:outputLabel/>                         
     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.sufreEnfermedadCatastrofica']}: "
     id="sufreEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:selectBooleanCheckbox value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.sufreEnfermedadCatastrofica}"/>                           

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cuentaCertificadoMedicoIess']}: "
     id="cuentaCertificadoMedicoIess_lbl"
     styleClass="labelFormulario"/>

     <p:selectBooleanCheckbox value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cuentaCertificadoMedicoIess}"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cuidaPersonaConDiscapacidadSevera']}: "
     id="cuidaPersonaConDiscapacidadSevera_lbl"
     styleClass="labelFormulario"/>
     <p:selectBooleanCheckbox value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cuidaPersonaConDiscapacidadSevera}" />

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.catalogoEnfermedades']}: "
     id="catalogoEnfermedades_lbl"
     styleClass="labelFormulario"/>
     <p:selectOneMenu id="catalogoEnfermedades"                                              
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.catalogoEnfermedadesId}">
     <f:selectItems value="#{portalRrhhBean.portalRrhhHelper.listaEnfermedades}"/>                                                                                                
     </p:selectOneMenu>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.catalogoParentezcoConDiscapacidad']}: "
     id="catalogoParentezcoConDiscapacidad_lbl"
     styleClass="labelFormulario"/>
     <p:selectOneMenu id="catalogoParentezcoConDiscapacidad"                                              
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.catalogoParentezcoConDiscapacidadId}">
     <f:selectItems value="#{portalRrhhBean.portalRrhhHelper.listaParentestoConDiscapacidad}"/>                                                                                                
     </p:selectOneMenu>
     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.catalogoDiscapacidades']}: "
     id="catalogoDiscapacidades_lbl"
     styleClass="labelFormulario"/>
     <p:selectOneMenu id="catalogoDiscapacidades"                                            
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.catalogoDiscapacidadesId}">
     <f:selectItems value="#{portalRrhhBean.portalRrhhHelper.listaDiscapacidades}"/>                                                                                                
     </p:selectOneMenu>
     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.nombresPersonaConDiscapacidad']}: "
     id="nombresPersonaConDiscapacidad_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="nombresPersonaConDiscapacidad"                        

     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.nombresPersonaConDiscapacidad}"                                        
     style="width: 200px"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cedulaPersonaConDiscapacidad']}: "
     id="cedulaPersonaConDiscapacidad_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="cedulaPersonaConDiscapacidad"                                                                                                          
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cedulaPersonaConDiscapacidad}"                                        
     style="width: 200px" maxlength="15"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.numeroCarnetConadis']}: "
     id="numeroCarnetConadis_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="numeroCarnetConadis"                                             
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.numeroCarnetConadis}"                                        
     style="width: 200px" />

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.porcentajeDiscapacidad']}: "
     id="porcentaje_discapacidad_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="porcentaje_discapacidad"                                          
     styleClass="campoNumerico"
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.porcentaje_discapacidad}"                                        
     style="width: 200px" maxlength="15"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cuidaPersonaConEnfermedadCatastrofica']}: "
     id="cuidaPersonaConEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:selectBooleanCheckbox value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cuidaPersonaConEnfermedadCatastrofica}" 
     rendered="true"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.catalogoEnfermedadesCatastroficas']}: "
     id="catalogoEnfermedadesCatastroficas_lbl"
     styleClass="labelFormulario"/>
     <p:selectOneMenu id="catalogoEnfermedadesCatastroficas"  
     style="width: 300px"
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.catalogoEnfermedadesCatastroficasId}">
     <f:selectItems value="#{portalRrhhBean.portalRrhhHelper.listaEnfermedadesCatastroficas}"/>                                                                                                
     </p:selectOneMenu>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.catalogoParentezcoEnfermedadCatastrofica']}: "
     id="catalogoParentezcoEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:selectOneMenu id="catalogoParentezcoEnfermedadCatastrofica"                                          
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.catalogoParentezcoEnfermedadCatastroficaId}">
     <f:selectItems value="#{portalRrhhBean.portalRrhhHelper.listaParentescoEnfermedadCatastrofica}"/>                                                                                                
     </p:selectOneMenu>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.nombresPersonaConEnfermedadCatastrofica']}: "
     id="nombresPersonaConEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="nombresPersonaConEnfermedadCatastrofica"                        

     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.nombresPersonaConEnfermedadCatastrofica}"                                        
     style="width: 200px"/>


     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cedula_persona_con_enfermedad_catastrofica']}: "
     id="cedulaPersonaConEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:inputText id="cedulaPersonaConEnfermedadCatastrofica"                                             
     value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cedulaPersonaConEnfermedadCatastrofica}"                                        
     style="width: 200px" maxlength="15"/>

     <h:outputLabel value="#{msg['ec.gob.mrl.smp.administracion.fichaPersonal.informacionMedica.cuenta_certificado_medico_con_enfermedad_catastrofica']}: "
     id="cuentaCertificadoMedicoConEnfermedadCatastrofica_lbl"
     styleClass="labelFormulario"/>
     <p:selectBooleanCheckbox value="#{portalRrhhBean.portalRrhhHelper.servidorInformacionMedica.cuentaCertificadoMedicoConEnfermedadCatastrofica}" 
     rendered="true"/>
     </h:panelGrid>  
     <h:panelGrid columns="1">
     <p:commandButton value="GUARDAR"
     action="#{portalRrhhBean.guardarServidorInformacionMedica()}"                                         
     update="panelservidorInformacionMedica,:frmPrincipal:VistaInstruccion:tabInformacionMedica,:frmPrincipal:panelInstruccion, :frmPrincipal:VistaInstruccion:ServidorInformacionMedicas">                              
     </p:commandButton>                   
     </h:panelGrid>
     </h:form>
     </p:dialog>
    
     <!-- eliminar INFORMACION MEDICA. 
     <p:confirmDialog id="popupIm" message="#{msg['ec.gob.mrl.smp.genericos.etiquetas.eliminacion.registro']}"
     header="Eliminación" 
     severity="alert" 
     widgetVar="confirmEliminacionIm">  
     <div style="text-align: center">
     <p:commandButton id="aceptarIm" value="#{msg['ec.gob.mrl.smp.genericos.etiquetas.aceptar']}" 
     style="margin: auto"
     update=":frmPrincipal:VistaInstruccion:tabInformacionMedica, :frmPrincipal:panelInstruccion ,:frmPrincipal:VistaInstruccion:ServidorInformacionMedicas"
     actionListener="#{portalRrhhBean.borrarCapacitacion()}"
     oncomplete="confirmEliminacionc.hide()"  />  
     <p:commandButton id="eliminarIm" value="#{msg['ec.gob.mrl.smp.genericos.etiquetas.cancelar']}"
     onclick="confirmEliminacionIm.hide()" 
     type="button" 
     style="margin: auto"/> 
     </div>
     </p:confirmDialog> -->
     -->
    
     */
}
