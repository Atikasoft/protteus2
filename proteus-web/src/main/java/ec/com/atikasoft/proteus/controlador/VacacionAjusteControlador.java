/*
 *  VacacionAjusteControlador.java
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
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.VacacionAjusteHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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

/**
 * VacacionAjusteControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionAjusteBean")
@ViewScoped
public class VacacionAjusteControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionAjusteControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/ajustes_saldo_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_ajustes_saldo_vacacion.jsf";

    /**
     * Constante para observacion de actualizacion de saldo de vacaciones.
     */
    public static final String OBSERVACION_SALDO_VACACION_CREDITO = "CRÉDITO RECUPERACIÓN DE VACACIÓN POR SOLICITUD DE ";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Dao de servidor institucion.
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;
    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{vacacionAjusteHelper}")
    private VacacionAjusteHelper vacacionAjusteHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {

        vacacionAjusteHelper.setNombreServidor(null);
        vacacionAjusteHelper.setNumeroIdentificacion(null);
        vacacionAjusteHelper.getListaServidores().clear();
    }

    /**
     * metodo que busca el servidor por nombre y/o número de identificacion.
     *
     * @return
     */
    public String buscarServidor() {
        try {

            if (vacacionAjusteHelper.getNombreServidor().length() < 3 && !vacacionAjusteHelper.getNombreServidor().isEmpty()) {
                vacacionAjusteHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (vacacionAjusteHelper.getNumeroIdentificacion().length() < 3 && !vacacionAjusteHelper.getNumeroIdentificacion().isEmpty()) {
                vacacionAjusteHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (vacacionAjusteHelper.getNombreServidor().isEmpty() && vacacionAjusteHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (!vacacionAjusteHelper.getNombreServidor().trim().isEmpty()) {
                vacacionAjusteHelper.setNombreServidor(vacacionAjusteHelper.getNombreServidor().toUpperCase());
            }
            vacacionAjusteHelper.getListaServidores().clear();
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(vacacionAjusteHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(vacacionAjusteHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            ser.setCodUnidadAdministrativa(
                    obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());

            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            for (DistributivoDetalle s : lista) {
                if (s.getVigente()) {
                    s.getServidor().setDistributivoDetalle(s);
                    vacacionAjusteHelper.getListaServidores().add(s.getServidor());
                }
            }
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores para asistencias:{0}", vacacionAjusteHelper.getListaServidores().size());

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
    public String iniciarEdicion(Servidor s) {
        vacacionAjusteHelper.setServidor(s);
        vacacionAjusteHelper.setDistributivoDetalle(vacacionAjusteHelper.getServidor().getDistributivoDetalle());
        if (vacacionAjusteHelper.getServidor() != null && vacacionAjusteHelper.getServidor().getId() != null) {
            vacacionAjusteHelper.setObservacionAjuste(null);
            vacacionAjusteHelper.setNuevoSaldoDias(null);
            vacacionAjusteHelper.setSaldoMinutos(null);
            vacacionAjusteHelper.getListaVacacion().clear();
            vacacionAjusteHelper.getListaVacacionDetalle().clear();
            vacacionAjusteHelper.setEsNuevo(Boolean.TRUE);
            buscarVacaciones();
            return FORMULARIO_ENTIDAD;
        } else {
            System.out.println(" Ningun servidor seleccionado...");
            return null;
        }

    }

    /**
     * buscar listado de saldo de vacaciones por periodos.
     *
     * @return
     */
    private void buscarVacaciones() {
        Long saldo = 0L;
        try {
            vacacionAjusteHelper.getListaVacacion().clear();
            vacacionAjusteHelper.setEsNuevo(Boolean.TRUE);
            Vacacion v = vacacionServicio.buscarVacacion(obtenerUsuarioConectado().getInstitucion().getId(), vacacionAjusteHelper.getServidor().getId());
            if (v != null) {
                vacacionAjusteHelper.getListaVacacion().add(v);
                saldo += v.getSaldo();

            }
            vacacionAjusteHelper.setSaldoMinutos(saldo);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda saldo vacacion ", ex);
        }
    }

    /**
     * Activa Actualizacion.
     *
     * @param vacacion
     */
    public void editarActualizacion(Vacacion vacacion) {
        if (vacacion != null) {
            vacacionAjusteHelper.setVacacion(vacacion);
        } else {
            vacacionAjusteHelper.setVacacion(new Vacacion());
        }
        vacacionAjusteHelper.setObservacionAjuste(null);
        vacacionAjusteHelper.setNuevoSaldoDias(null);

    }

    /**
     * busca detalles de las vacaciones.
     *
     * @param idVacacion
     */
    public void buscarDetallesVacaciones(final Long idVacacion) {
        try {
            vacacionAjusteHelper.setListaVacacionDetalle(vacacionServicio.listarVacacionPorVacacion(idVacacion));
            ejecutarComandoPrimefaces("detDialog.show();");
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
    }

    /**
     * Permite obtener los parametros para la gestion de las vacaciones de
     * acuerdo al regimen laboral del servidor<br><p>
     *
     * @return true si existe la configuracion 1. Regimen Laboral se obtiene
     * desde el detalle del distributivo a aprtir del numero de documento del
     * usuario<br/> 2. Con el regimen laboral Obtener parametrizacion de
     * vacacion del servidor </p>
     */
    public boolean obtenerParametrizacionVacacion() {
        boolean hayRegimenLab = false;
        try {
            RegimenLaboral regLaboral;
            if (vacacionAjusteHelper.getVacacion().getServidorInstitucion() != null) {
                DistributivoDetalle dd = vacacionAjusteHelper.getDistributivoDetalle();
                if (dd != null) {
                    regLaboral = dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral();

                    List<VacacionParametro> listaParametros = vacacionServicio.
                            listarTodosVacacionParametroPorRegimenLaboral(regLaboral.getId());
                    if (!listaParametros.isEmpty()) {
                        for (VacacionParametro p : listaParametros) {
                            //vacacionAjusteHelper.getVacacion().setVacacionParametro(p);
                            hayRegimenLab = true;
                            break;
                        }
                    } else {
                        mostrarMensajeEnPantalla("No se puede encontrar la configuración para vacaciones anuales.",
                                FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda parametrizacion", ex);
        }
        return hayRegimenLab;
    }

    /**
     *
     * @return
     */
    public String guardar() {
        try {
            BigDecimal saldoAnterior = obtenerDias(vacacionAjusteHelper.getSaldoMinutos());

            if (vacacionAjusteHelper.getNuevoSaldoDias() == null) {
                mostrarMensajeEnPantalla("El campo Nuevo saldo es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (vacacionAjusteHelper.getObservacionAjuste() == null || vacacionAjusteHelper.getObservacionAjuste().isEmpty()) {
                mostrarMensajeEnPantalla("El campo observación es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            Servidor s = vacacionAjusteHelper.getServidor();

            Long nuevoSaldoMinutos = (long) ((double) vacacionAjusteHelper.getNuevoSaldoDias() * (UtilFechas.MIN_EN_HORA * s.getJornada()));
            //reglas:
            // 1.- Si no hay registro de acumulacion agregar un registro de acumulacion nuevo
            // 2.- Si hay registro de acumulacion, agregar en registro de acumulacion activo, quitar en registro con saldo mas anterior
            if (vacacionAjusteHelper.getEsNuevo() && vacacionAjusteHelper.getSaldoMinutos() == 0) {
                if (saldoAnterior.compareTo(new BigDecimal(vacacionAjusteHelper.getNuevoSaldoDias())) == 0) {
                    mostrarMensajeEnPantalla("El saldo actual es igual al saldo anterior", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                crearNuevoRegistro(nuevoSaldoMinutos);
            } else {
                System.out.println(" vacaciones para guardar:" + vacacionAjusteHelper.getVacacion());
                if (vacacionAjusteHelper.getVacacion() == null || vacacionAjusteHelper.getVacacion().getId() == null) {
                    mostrarMensajeEnPantalla("Seleccione un periodo de Vacaciones para poder actualizar", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                if (obtenerDias(vacacionAjusteHelper.getVacacion().getSaldo()).compareTo(new BigDecimal(vacacionAjusteHelper.getNuevoSaldoDias())) == 0) {
                    mostrarMensajeEnPantalla("El saldo actual es igual al saldo anterior", FacesMessage.SEVERITY_ERROR);
                    return null;
                }
                actualizarRegistro(nuevoSaldoMinutos);
            }
            vacacionAjusteHelper.setObservacionAjuste(null);
            vacacionAjusteHelper.setNuevoSaldoDias(null);
            vacacionAjusteHelper.setSaldoMinutos(null);
            vacacionAjusteHelper.getListaVacacion().clear();
            vacacionAjusteHelper.getListaVacacionDetalle().clear();
            vacacionAjusteHelper.setEsNuevo(Boolean.TRUE);
            editarActualizacion(null);
            buscarVacaciones();
            ejecutarComandoPrimefaces("confirmation.hide();");
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }

        return null;
    }

    /**
     * Crea nuevo registro de vacaciones con su propio detalle.
     */
    private boolean crearNuevoRegistro(final Long nuevoSaldoMinutos) throws ServicioException {
        boolean problemas = false;
        vacacionAjusteHelper.setVacacion(new Vacacion());
        List<ServidorInstitucion> lista
                = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(vacacionAjusteHelper.
                        getServidor().getNumeroIdentificacion());
        if (!lista.isEmpty()) {
            vacacionAjusteHelper.getVacacion().setServidorInstitucion(lista.get(0));
        } else {
            try {
                LOG.log(Level.INFO, "{0} El servidor no se encuentra registrado en una instituci\u00f3n", getClass().
                        getName());
                ServidorInstitucion si = new ServidorInstitucion();
                si.setIdServidor(vacacionAjusteHelper.getServidor().getId());
                si.setIdInstitucion(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getInstitucion().getId());
                si.setFechaIngreso(vacacionAjusteHelper.getServidor().getFechaIngresoInstitucion());
                si.setFechaCreacion(new Date());
                si.setUsuarioCreacion(obtenerUsuarioConectado().getUsuario());
                si.setVigente(Boolean.TRUE);
                servidorInstitucionDao.crear(si);
                vacacionAjusteHelper.getVacacion().setServidorInstitucion(si);
            } catch (DaoException ex) {
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                Logger.getLogger(VacacionAjusteControlador.class.getName()).log(Level.SEVERE, null, ex);
                return true;
            }
        }
        if (!obtenerParametrizacionVacacion()) {
            mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
            LOG.log(Level.INFO, "{0} El servidor no tiene configuracion de parametros de vacaciones", getClass().
                    getName());
            problemas = true;
            return problemas;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(vacacionAjusteHelper.getServidor().getFechaIngresoInstitucion());
        c.set(Calendar.YEAR, UtilFechas.obtenerAnio(new Date()));
//        vacacionAjusteHelper.getVacacion().setEnAcreditacion(Boolean.TRUE);
//        vacacionAjusteHelper.getVacacion().setFechaInicioPeriodo(c.getTime());
        c.set(Calendar.YEAR, (UtilFechas.obtenerAnio(new Date()) + 1));
//        vacacionAjusteHelper.getVacacion().setFechaFinPeriodo(c.getTime());
        String obs = "AJUSTE:" + vacacionAjusteHelper.getObservacionAjuste();
//        System.out.println("observacion: "+obs);
        if (obs.length() > 255) {
            obs = obs.substring(0, 255);
        }
//        vacacionAjusteHelper.getVacacion().setObservacion(obs);
        vacacionAjusteHelper.getVacacion().setSaldo(nuevoSaldoMinutos);
        iniciarDatosEntidad(vacacionAjusteHelper.getVacacion(), Boolean.TRUE);
        List<VacacionDetalle> detalles = new ArrayList<VacacionDetalle>();
        VacacionDetalle det = new VacacionDetalle();
        iniciarDatosEntidad(det, Boolean.TRUE);
        det.setCredito(nuevoSaldoMinutos);
//        det.setFechaInicio(vacacionAjusteHelper.getVacacion().getFechaInicioPeriodo());
//        det.setFechaFin(vacacionAjusteHelper.getVacacion().getFechaFinPeriodo());
//        det.setObservacion(vacacionAjusteHelper.getVacacion().getObservacion());
        det.setDebito(0L);
        detalles.add(det);
        vacacionServicio.guardarVacacionYDetalles(vacacionAjusteHelper.getVacacion(), detalles, Boolean.TRUE);
        return problemas;
    }

    /**
     * Permite actualizar un registro de vacaciones, creando su respectivo
     * detalle
     *
     * @return
     * @throws ServicioException
     */
    private boolean actualizarRegistro(final Long nuevoSaldoMinutos) throws ServicioException {
        boolean problemas = false;
        if (vacacionAjusteHelper.getVacacion() != null) {
            if (!obtenerParametrizacionVacacion()) {
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                LOG.log(Level.INFO, "{0} El servidor no tiene configuracion de parametros de vacaciones", getClass().
                        getName());
                problemas = true;
            }
//            vacacionAjusteHelper.getVacacion().setEnAcreditacion(Boolean.TRUE);
//            vacacionAjusteHelper.getVacacion().setObservacion(vacacionAjusteHelper.getVacacion().getObservacion()
//                    + " + AJUSTE:" + vacacionAjusteHelper.getObservacionAjuste());

            iniciarDatosEntidad(vacacionAjusteHelper.getVacacion(), Boolean.FALSE);

            List<VacacionDetalle> detalles = new ArrayList<VacacionDetalle>();
            VacacionDetalle det = new VacacionDetalle();
            iniciarDatosEntidad(det, Boolean.TRUE);
            det.setDebito(0L);
            det.setCredito(0L);
            System.out.println(" saldo actual en min:" + vacacionAjusteHelper.getVacacion().getSaldo());
            System.out.println(" nuevo saldo en min:" + nuevoSaldoMinutos);
            if (vacacionAjusteHelper.getVacacion().getSaldo() < nuevoSaldoMinutos) {
                System.out.println(" ------credito-----:" + (nuevoSaldoMinutos - vacacionAjusteHelper.getVacacion().getSaldo()));
                det.setCredito(nuevoSaldoMinutos - vacacionAjusteHelper.getVacacion().getSaldo());
            } else {
                System.out.println(" ------debito-----:" + (vacacionAjusteHelper.getVacacion().getSaldo() - nuevoSaldoMinutos));
                det.setDebito(vacacionAjusteHelper.getVacacion().getSaldo() - nuevoSaldoMinutos);
            };
//            det.setFechaInicio(vacacionAjusteHelper.getVacacion().getFechaInicioPeriodo());
//            det.setFechaFin(vacacionAjusteHelper.getVacacion().getFechaFinPeriodo());
            det.setObservacion("AJUSTE:" + vacacionAjusteHelper.getObservacionAjuste());
            vacacionAjusteHelper.getVacacion().setSaldo(nuevoSaldoMinutos);
            detalles.add(det);
            vacacionServicio.guardarVacacionYDetalles(vacacionAjusteHelper.getVacacion(), detalles, Boolean.FALSE);
        } else {
            mostrarMensajeEnPantalla("Seleccione un registro de vacaciones para actualizar", FacesMessage.SEVERITY_ERROR);
        }
        return problemas;
    }

    /**
     *
     */
    public void activarGuardado() {
        ejecutarComandoPrimefaces("confirmation.show();");
    }

    /**
     * Devuelve en palabras el tiempo
     *
     * @param minutos
     * @return
     */
    public String obtenerTiempo(Long minutos) {
        String cadena = UtilFechas.convertirMinutosA_ddHHmmPalabras(minutos, vacacionAjusteHelper.getServidor().getJornada());
        if (cadena.isEmpty()) {
            cadena = "0 Días";
        }
        return cadena;
    }

    /**
     * Convierte los minutos en dias.
     *
     * @param minutos
     * @return
     */
    public BigDecimal obtenerDias(Long minutos) {
        return new BigDecimal(minutos / (UtilFechas.MIN_EN_HORA * vacacionAjusteHelper.getServidor().getJornada()));
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoVacacion(final String codigo) {
        return TipoVacacionEnum.obtenerNombre(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoVacacion(final String codigo) {
        return EstadoVacacionEnum.obtenerDescripcion(codigo);
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
     * Verifica que las fechas ingresadas no sean anteriores a la fecha actual
     *
     * @param fecha que es objeto de comparacion
     * @return
     */
    public boolean verificaFecha(Date fecha) {
        return UtilFechas.calcularDiferenciaDiasEntreFechas(fecha, new Date()) >= 0;
    }

    /**
     * Permite regresar al listado
     *
     * @return
     */
    public String irLista() {
        iniciarOpciones();
        return LISTA_ENTIDAD;
    }

    /**
     * @return the vacacionAjusteHelper
     */
    public VacacionAjusteHelper getVacacionAjusteHelper() {
        return vacacionAjusteHelper;
    }

    /**
     * @param vacacionAjusteHelper the vacacionAjusteHelper to set
     */
    public void setVacacionAjusteHelper(VacacionAjusteHelper vacacionAjusteHelper) {
        this.vacacionAjusteHelper = vacacionAjusteHelper;
    }
}
