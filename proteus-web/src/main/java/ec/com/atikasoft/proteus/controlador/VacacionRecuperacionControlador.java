/*
 *  VacacionRecuperacionControlador.java
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
import ec.com.atikasoft.proteus.controlador.helper.VacacionRecuperacionHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoVacacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

/**
 * VacacionRecuperacionControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "vacacionRecuperacionBean")
@ViewScoped
public class VacacionRecuperacionControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(VacacionRecuperacionControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/solicitud_recuperacion_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/vacacion/lista_recuperacion_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_INDEX = "/pages/portal_rrhh.jsf";

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
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    
    /**
     * 
     */
    @EJB
    private VacacionDao vacacionDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{vacacionRecuperacionHelper}")
    private VacacionRecuperacionHelper vacacionRecuperacionHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarOpciones();
    }

    /**
     * Este método carga las opciones de seleccion de la pantalla.
     */
    private void iniciarOpciones() {
        try {
            iniciarComboTipoVacacion();

            /**
             * Poblar unidades organizacionales.
             */
            List<UnidadOrganizacional> unidades = admServicio.listarUnidadOrganizacionalVigente();
            getVacacionRecuperacionHelper().setListaUnidadesOrganizacionales(unidades);
            /**
             * tipo documento.
             */
            iniciarCombosTodos(getVacacionRecuperacionHelper().getListaTipoDocumento());
            getVacacionRecuperacionHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.CEDULA.getNemonico(),
                    TipoDocumentoEnum.CEDULA.getNombre()));
            getVacacionRecuperacionHelper().getListaTipoDocumento().add(new SelectItem(
                    TipoDocumentoEnum.PASAPORTE.getNemonico(),
                    TipoDocumentoEnum.PASAPORTE.getNombre()));
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setUnidadAdministrativaId(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getId());
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setUnidadAdministrativaNombre(
                    obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getRuta());
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setTipoVacacion(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setFechaInicioPlanificacio(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setFechaFinPlanificacio(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setNombreServidor(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setNumeroDocumentoServidor(null);
            vacacionRecuperacionHelper.getBusquedaVacacionVO().setTipoDocumentoServidor(null);
            vacacionRecuperacionHelper.getListaVacacionSolicitudes().clear();

            vacacionRecuperacionHelper.setEsRRHH(esUnidadRRHH());
            if (!vacacionRecuperacionHelper.getEsRRHH()) {
                buscar();
            }
        } catch (ServicioException e) {
            error(getClass().getName(), "Error al iniciar las opciones", e);
        }
    }

    private boolean esUnidadRRHH() {
        boolean esUnidadRRHH = false;
        try {
            String parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo()).getValorTexto();
            esUnidadRRHH = esRRHH(parametro);
        } catch (DaoException ex) {
            Logger.getLogger(VacacionTemporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esUnidadRRHH;
    }

    /**
     *
     * @return
     */
    public String guardar() {
        try {
            if (vacacionRecuperacionHelper.getVacacionSolicitud().getCantidadRecuperada() <= 0) {
                mostrarMensajeEnPantalla("Los días a recuperar deben ser superiores a 0 ", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (getVacacionRecuperacionHelper().getVacacionSolicitud().getCantidadSolicitada() < vacacionRecuperacionHelper.getVacacionSolicitud().getCantidadRecuperada()) {
                mostrarMensajeEnPantalla("No puede recuperar más días de los indicados en la solicitud actual, que son: "
                        + getVacacionRecuperacionHelper().getVacacionSolicitud().getCantidadSolicitada(), FacesMessage.SEVERITY_ERROR);
                return null;
            }
            /*Date fechafinAnterior = vacacionRecuperacionHelper.getVacacionSolicitud().getFechaFin();
            Date ff = UtilFechas.sumarDias(getVacacionRecuperacionHelper().getVacacionSolicitud().getFechaFin(),
                    Integer.parseInt(vacacionRecuperacionHelper.getVacacionSolicitud().getCantidadRecuperada().toString()) - 1);
            getVacacionRecuperacionHelper().getVacacionSolicitud().setCantidadSolicitada(getVacacionRecuperacionHelper().getVacacionSolicitud().getCantidadSolicitada()
                    - (long) getVacacionRecuperacionHelper().getVacacionSolicitud().getCantidadRecuperada());
            getVacacionRecuperacionHelper().getVacacionSolicitud().setFechaFin(ff);*/
            imputarFinesSemana();
            //vacacionServicio.recuperarSaldoVacacionSolicitud(getVacacionRecuperacionHelper().getVacacionSolicitud(), fechafinAnterior);
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            getVacacionRecuperacionHelper().setEsNuevo(Boolean.FALSE);
            ejecutarComandoPrimefaces("confirmation.hide()");

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Inicializa variables para la edición.
     *
     * @return
     */
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(getVacacionRecuperacionHelper().getVacacionSolicitudEditDelete());
            VacacionSolicitud d = (VacacionSolicitud) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            getVacacionRecuperacionHelper().setVacacionSolicitud(d);
            getVacacionRecuperacionHelper().setEsNuevo(Boolean.FALSE);
            vacacionRecuperacionHelper.setEditar(Boolean.TRUE);
            vacacionRecuperacionHelper.getVacacionSolicitud().setRecuperador(obtenerUsuarioConectado().getServidor());
        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public String verSolicitud() {
        vacacionRecuperacionHelper.setEditar(Boolean.FALSE);
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     */
    public void activarGuardado() {
        ejecutarComandoPrimefaces("confirmation.show();");
    }

    /**
     * Busca todos los registros en estado aprobado.
     *
     * @return
     */
    public String buscar() {
        try {
//            System.out.println(" unidad organizacional:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getUnidadAdministrativaId());
//            System.out.println(" nummero doc:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getNumeroDocumentoServidor());
//            System.out.println(" tipo sol:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getTipoVacacion());
//            System.out.println(" tipo doc:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getTipoDocumentoServidor());
//            System.out.println(" desde:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getFechaInicioPlanificacio());
//            System.out.println(" hasta:" + vacacionRecuperacionHelper.getBusquedaVacacionVO().getFechaFinPlanificacio());
            getVacacionRecuperacionHelper().getBusquedaVacacionVO().setEstadoVacacion(EstadoVacacionEnum.APROBADO.getCodigo());
            getVacacionRecuperacionHelper().setListaVacacionSolicitudes(vacacionServicio.buscar(getVacacionRecuperacionHelper().getBusquedaVacacionVO()));

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return null;
    }

    /**
     * Determina los dias que se deben agregar por fines de semana Setea la
     * fecha fin y cantidad solicitada.
     *
     */
    public void imputarFinesSemana() {
        BigDecimal minutos = BigDecimal.ZERO;
        if (getVacacionRecuperacionHelper().getVacacionSolicitud().getTipo().equals(
                TipoVacacionEnum.VACACION_NO_PLANIFICADAS.getCodigo())
                && getVacacionRecuperacionHelper().getVacacionSolicitud().getVacacionParametro().getImputarFinSemanaVacacion()) {
            Servidor s =getVacacionRecuperacionHelper().getVacacionSolicitud().getServidorInstitucion().getServidor();
            minutos = new BigDecimal(UtilFechas.convertirEnMinutosPorTipoUnidadTiempo(getVacacionRecuperacionHelper().
                    getVacacionSolicitud().getTipoPeriodo().charAt(0),
                    getVacacionRecuperacionHelper().getVacacionSolicitud().getCantidadSolicitada(),
                    s.getJornada()));
            minutos = UtilFechas.calcularPromedioDiarioEnMinutosCargoVacacion(minutos.longValue(),s.getJornada());
            if (minutos.compareTo(new BigDecimal(99999)) > 0) {
                minutos = new BigDecimal(99999);
            }
        }
        getVacacionRecuperacionHelper().getVacacionSolicitud().setMinutosImputados(minutos);
    }

    /**
     * Permite imprimir la solicitud de vacaciones para que el servidor firme y
     * entregue a quien corresponda.
     */
    public void imprimirSolicitudVacacion() {
        try {
            if (TipoVacacionEnum.VACACION_PLANIFICADAS.getCodigo().equals(getVacacionRecuperacionHelper().getVacacionSolicitud().getTipo())) {
                setReporte(ReportesEnum.CONSULTA_SOLICITUD_VACACIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "SOLICITUD DE VACACIONES");
                parametrosReporte.put("servidorId",
                        obtenerUsuarioConectado().getServidor().getId().toString());
                parametrosReporte.put("vacacionsolicitudId", getVacacionRecuperacionHelper().
                        getVacacionSolicitud().getId().toString());
                parametrosReporte.put("servidorInstitucionId", getVacacionRecuperacionHelper().
                        getVacacionSolicitud().getIdServidorInstitucion().toString());
            }

            if (TipoVacacionEnum.VACACION_NO_PLANIFICADAS.getCodigo().
                    equals(getVacacionRecuperacionHelper().
                            getVacacionSolicitud().getTipo())
                    || TipoVacacionEnum.ANTICIPO_VACACIONES.getCodigo().
                    equals(getVacacionRecuperacionHelper().
                            getVacacionSolicitud().getTipo())) {
                setReporte(ReportesEnum.CONSULTA_SOLICITUD_PERMISO_CARGO_VACACIONES.getReporte());
                parametrosReporte = new HashMap<String, String>();
                parametrosReporte.put("p_titulo", "SOLICITUD ADELANTO DE VACACIONES");
                parametrosReporte.put("servidorId",
                        obtenerUsuarioConectado().getServidor().getId().toString());
                parametrosReporte.put("vacacionsolicitudId", getVacacionRecuperacionHelper().
                        getVacacionSolicitud().getId().toString());

            }
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de registro de solicitud de vacaciones "
                    + "UATH" + e.getMessage(), e);
        }

    }

    /**
     * buscar listado de vacaciones acumuladas por periodos. debe estar ordenada
     * en forma ascendete por fecha de inicio.
     *
     * @return
     */
    public List<Vacacion> getListaSaldosVacaciones() {
        try {
            Vacacion v = vacacionDao.buscarPorServidor(vacacionRecuperacionHelper.getVacacionSolicitud().getServidorInstitucion().getId());
            if(v!=null){
                List<Vacacion> vacaciones = new ArrayList<Vacacion>();
                vacaciones.add(v);
                return vacaciones;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de tipo vacacion.
     */
    private void iniciarComboTipoVacacion() {
        iniciarCombosTodos(getVacacionRecuperacionHelper().getListaOpcionesTipoVacacion());
        for (TipoVacacionEnum t : TipoVacacionEnum.values()) {
            getVacacionRecuperacionHelper().getListaOpcionesTipoVacacion().add(new SelectItem(t.getCodigo(), t.getNombre()));
        }
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
//        buscar();
        return LISTA_ENTIDAD;
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_INDEX;
    }

    /**
     * @return the vacacionRecuperacionHelper
     */
    public VacacionRecuperacionHelper getVacacionRecuperacionHelper() {
        return vacacionRecuperacionHelper;
    }

    /**
     * @param vacacionRecuperacionHelper the vacacionRecuperacionHelper to set
     */
    public void setVacacionRecuperacionHelper(VacacionRecuperacionHelper vacacionRecuperacionHelper) {
        this.vacacionRecuperacionHelper = vacacionRecuperacionHelper;
    }
}
