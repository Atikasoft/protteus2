/*
 *  AnticipoControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  05/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.AnticipoHelper;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PeriodoNominaDao;
import ec.com.atikasoft.proteus.enums.EstadoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.EstadoPlanPagoEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.TipoAnticipoEnum;
import ec.com.atikasoft.proteus.enums.TipoCuotaEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoGarantiaEnum;
import ec.com.atikasoft.proteus.enums.TipoModalidadEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.TipoAnticipo;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AnticipoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.NominaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.AnticipoVO;
import ec.com.atikasoft.proteus.vo.BusquedaNominaVO;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
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
 * AnticipoControlador, gestiona las solicitudes de Anticipos
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "anticipoBean")
@ViewScoped
public class AnticipoControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(AnticipoControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/anticipo/solicitud_anticipo.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/anticipo/lista_anticipo.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Servicio de anticipos.
     */
    @EJB
    private AnticipoServicio anticipoServicio;
    /**
     * Servicio de servidor.
     */
    @EJB
    private DistributivoPuestoServicio distributivoServicio;
    /**
     * Servicio de Nomina.
     */
    @EJB
    private NominaServicio nominaServicio;
    /**
     * Dao de Periodo de nomina.
     */
    @EJB
    private PeriodoNominaDao periodoNominaDao;
    /**
     * Dao de Periodo de nomina.
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Dao de parametros institucional.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{anticipoHelper}")
    private AnticipoHelper anticipoHelper;

    /**
     * Constructor por defecto.
     */
    public AnticipoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(anticipoHelper);
        setAnticipoHelper(anticipoHelper);
        buscar();
        anticipoHelper.getAnticipoVO().setDistributivoDetalle(
                obtenerUsuarioConectado().getDistributivoDetalle());
        anticipoHelper.setFechaIngreso(obtenerUsuarioConectado().getServidor().getFechaIngresoInstitucion());
        iniciarComboTipoAnticipo();
    }

    @Override
    public String guardar() {
        try {
            if (anticipoHelper.getEsNuevo()) {
                if (anticipoHelper.getAnticipoVO().getAcepta() == null | !anticipoHelper.getAnticipoVO().getAcepta()) {
                    mostrarMensajeEnPantalla("Ud. debe aceptar los términos y condiciones estipuladas para la "
                            + "otorgación de Anticipos de Sueldo.", FacesMessage.SEVERITY_ERROR);
                    return FORMULARIO_ENTIDAD;
                }
                if (anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().isEmpty()) {
                    mostrarMensajeEnPantalla("Debe tener un plan de pago de al menos un periodo.",
                            FacesMessage.SEVERITY_INFO);
                } else {
                    anticipoServicio.guardarAnticipo(anticipoHelper.getAnticipoVO().getAnticipo(),
                            anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    ejecutarComandoPrimefaces("confirmationSolicitud.hide();");
                    anticipoHelper.setEsNuevo(Boolean.FALSE);
                }

            } else {
                anticipoServicio.actualizarAnticipo(anticipoHelper.getAnticipoVO().getAnticipo());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(anticipoHelper.getAnticipoEditDelete());
            Anticipo d = (Anticipo) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            anticipoHelper.getAnticipoVO().setAnticipo(d);
            anticipoHelper.setEsNuevo(Boolean.FALSE);
            anticipoHelper.setFechaIngreso(obtenerUsuarioConectado().getServidor().getFechaIngresoInstitucion());
            anticipoHelper.getAnticipoVO().setAcepta(Boolean.TRUE);
            buscarDetalles();
        } catch (IllegalAccessException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InstantiationException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (InvocationTargetException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        } catch (NoSuchMethodException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        UsuarioVO usu = obtenerUsuarioConectado();
        anticipoHelper.setAnticipoVO(new AnticipoVO());
        anticipoHelper.iniciador();
        iniciarDatosEntidad(anticipoHelper.getAnticipoVO().getAnticipo(), Boolean.TRUE);
        anticipoHelper.setEsNuevo(Boolean.TRUE);
        anticipoHelper.getAnticipoVO().getAnticipo().setFechaSolicitud(new Date());
        anticipoHelper.getAnticipoVO().getAnticipo().setServidor(usu.getServidor());
        anticipoHelper.getAnticipoVO().getAnticipo().setServidorId(usu.getServidor().getId());
        anticipoHelper.getAnticipoVO().getAnticipo().setEstado(EstadoAnticipoEnum.REGISTRADO.getCodigo());
        anticipoHelper.getAnticipoVO().getAnticipo().setValorCapacidadPago(BigDecimal.ZERO);
        anticipoHelper.getAnticipoVO().getAnticipo().setInstitucionEjercicioFiscal(buscarEjercicioFiscal(
                usu.getInstitucion().getCodigo(), usu.getEjercicioFiscal().getId()));
        anticipoHelper.getAnticipoVO().getAnticipo().setInstitucionEjercicioFiscalId(
                anticipoHelper.getAnticipoVO().getAnticipo().getInstitucionEjercicioFiscal().getId());
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            anticipoServicio.eliminarAnticipo(anticipoHelper.getAnticipoEditDelete());
            anticipoHelper.getListaAnticipos().
                    remove(anticipoHelper.getAnticipoEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            anticipoHelper.getListaAnticipos().clear();
            anticipoHelper.setListaAnticipos(anticipoServicio.listarTodosAnticiposPorServidor(
                    obtenerUsuarioConectado().getServidor().getId()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda ", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Obtiene los detalles del anticipo de sueldo.
     */
    public void buscarDetalles() {
        anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
        anticipoHelper.setTotal(BigDecimal.ZERO);
        for (AnticipoPlanPago plan : anticipoHelper.getAnticipoVO().getAnticipo().getListaAnticipoPlaPlago()) {
            if (plan.getVigente()) {
                plan.setMesPalabras(plan.getMes() + " ( " + UtilFechas.obtenerNombreMes(plan.getMes()) + " )");
                anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().add(plan);
                anticipoHelper.setTotal(anticipoHelper.getTotal().add(plan.getMonto().setScale(2, RoundingMode.HALF_UP)));
            }
        }
    }

    public InstitucionEjercicioFiscal buscarEjercicioFiscal(final String codInstitucion, final Long idEjercicio) {
        try {
            return admServicio.buscarInstitucionPorCodigoYEjercicioFiscal(
                    codInstitucion, idEjercicio);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda de institucion ejercicio fiscal ", ex);
        }
        return null;
    }

    /**
     * Obtiene lista de los servidores de nombramiento con estabilidad Laboral. La lista es obtenida desde el
     * distributivo con los cargos que se encuentren no vacantes
     *
     * @return
     */
    public String buscarServidoresInstitucion() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

            BusquedaPuestoVO ser = new BusquedaPuestoVO();
            ser.setTipoModalidad(TipoModalidadEnum.NOMBRAMIENTO.getCodigo());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIntitucionEjercicioFiscalId(anticipoHelper.getAnticipoVO().getAnticipo().
                    getInstitucionEjercicioFiscalId());
            List<DistributivoDetalle> lista = distributivoServicio.buscar(ser, false, obtenerUsuarioConectado(),
                    esRRHH(pi.getValorTexto()));
            anticipoHelper.getListaServidores().clear();
            for (DistributivoDetalle det : lista) {
                if (det.getDistributivo().getModalidadLaboral().isEstabilidadLaboral()) {
                    anticipoHelper.getListaServidores().add(det.getServidor());
                }
            }
            ejecutarComandoPrimefaces("dlgGarante.show()");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda ", ex);
        }
        return null;
    }

    /**
     * Carga las opciones para poder seleccionar un tipo de anticipo.
     * <p>
     * El tipo de anticipo está dado por el régimen laboral al cual pertenece el servidor en la distribución de puestos
     * vigente.</p>
     */
    public void iniciarComboTipoAnticipo() {
        try {
            anticipoHelper.getListaOpcionesMeses().clear();
            iniciarCombos(anticipoHelper.getListaOpcionesMeses());
            if (anticipoHelper.getAnticipoVO().getDistributivoDetalle() != null) {
                List<TipoAnticipo> lista = anticipoServicio.listarTodosTipoAnticiposPorRegimenLaboral(
                        anticipoHelper.getAnticipoVO().getDistributivoDetalle().getEscalaOcupacional().
                        getNivelOcupacional().getIdRegimenLaboral());
                if (!lista.isEmpty()) {
                    anticipoHelper.getListaOpcionesTipoAnticipos().clear();
                    iniciarCombos(anticipoHelper.getListaOpcionesTipoAnticipos());
                    for (TipoAnticipo ta : lista) {
                        anticipoHelper.getListaOpcionesTipoAnticipos().add(new SelectItem(ta.getId(), ta.getNombre()));
                    }
                    anticipoHelper.setListaTipoAnticipos(lista);
                } else {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                }
            } else {
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda de tipo anticipo ", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de plazo en meses.
     * <p>
     * El final de la lista de opciones está restringido por el tipo de anticipo seleccionado.
     * </p>
     */
    private void iniciarComboMeses() {
        anticipoHelper.getListaOpcionesMeses().clear();
        iniciarCombos(anticipoHelper.getListaOpcionesMeses());
        for (int i = 1; i <= anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getPlazoMaximoMeses(); i++) {
            anticipoHelper.getListaOpcionesMeses().add(new SelectItem(i, String.valueOf(i)));
        }
    }

    /**
     * Encera la listal plan de pago del anticipo al seleccionar un número para el plazo en meses.
     */
    public void seleccionarMes() {
        LOG.info(UtilCadena.concatenar(" Plazo en Meses seleccionado : ",
                anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses(),
                " valor : ", anticipoHelper.getAnticipoVO().getAnticipo().getValor()));
        anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
    }

    /**
     * Inicializa el perido Inicial del préstamo, corresponde al mes actual + 1. Es una combinación : anio/mes
     */
    public void determinarPeriodoInicial() {
        String periodo, mes;
        Date fecha = new Date();

        fecha = UtilFechas.sumarMeses(fecha, 1);
        anticipoHelper.getAnticipoVO().setAnioInicio(UtilFechas.obtenerAnio(fecha));
        anticipoHelper.getAnticipoVO().setMesInicio(UtilFechas.obtenerMes(fecha) + 1);

        mes = anticipoHelper.getAnticipoVO().getMesInicio().toString();
        periodo = UtilCadena.concatenar(anticipoHelper.getAnticipoVO().getAnioInicio(), "/", mes.length() == 1 ? "0" + mes : mes);
        anticipoHelper.getAnticipoVO().getAnticipo().setPeriodoInicio(periodo);
        LOG.info(UtilCadena.concatenar(" periodo inicial ", periodo, ":", anticipoHelper.getAnticipoVO().getAnioInicio(),
                "-", anticipoHelper.getAnticipoVO().getMesInicio()));
    }

    /**
     * Inicializa el perido Final del anticipo de sueldo, se obtiene a partir del periodo inicial + plazo meses. Es una
     * combinación : anio/mes
     */
    public void determinarPeriodoFinal() {
        String periodo, mes;
        Date fechafin;
        Integer anio, mm;
        anticipoHelper.setContinuarAnticipo(false);
        if (anticipoHelper.getAnticipoVO().getAnticipo().getPeriodoInicio() != null) {
            periodo = anticipoHelper.getAnticipoVO().getAnticipo().getPeriodoInicio();
            fechafin = UtilFechas.sumarMeses(UtilFechas.convertirFechaStringEnDate(periodo + "/01", UtilFechas.FORMATO_FECHA_1),
                    anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses() - 1);

            mm = UtilFechas.obtenerMes(fechafin) + 1;
            mes = mm.toString();
            anio = UtilFechas.obtenerAnio(fechafin);

            periodo = UtilCadena.concatenar(anio, "/", mes.length() == 1 ? "0" + mes : mes);
            anticipoHelper.getAnticipoVO().getAnticipo().setPeriodoFin(periodo);
            anticipoHelper.setContinuarAnticipo(true);
        }
    }

    /**
     * metodo para validar si existe otro anticipo de sueldo vigente para el servidor.
     *
     * @return hayCodigo Boolean true, si exite otro anticipo que impide que se otorgue uno nuevo.
     */
    public Boolean validarAnticipoExistente() {
        Boolean hayCodigo = false;
        try {
            anticipoHelper.getListaAnticipoCodigo().clear();
            anticipoHelper.setListaAnticipoCodigo(anticipoServicio.listarTodosAnticiposPorServidor(
                    obtenerUsuarioConectado().getServidor().getId()));

            if (!anticipoHelper.getListaAnticipoCodigo().isEmpty()) {
                for (Anticipo anticipo : anticipoHelper.getListaAnticipoCodigo()) {
                    String periodo = anticipo.getPeriodoFin();
                    Date fecha = UtilFechas.convertirFechaStringEnDate(periodo + "/01", UtilFechas.FORMATO_FECHA_1);
                    if (!anticipo.getEstado().equals(EstadoAnticipoEnum.RECHAZADO.getCodigo())
                            && fecha != null && UtilFechas.truncarFecha(fecha).compareTo(UtilFechas.truncarFecha(new Date())) > 0) {
                        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getPorcentajeMaximoSaldoRenovacion().
                                compareTo(BigDecimal.ZERO) > 0) {
                            if (!verificarPorcentajeOtroAnticipo(anticipo)) {
                                hayCodigo = true;
                                break;
                            }
                        }

                    }
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion del anticipo existente", ex);
        }
        return hayCodigo;
    }

    /**
     * Verifica que se haya cubierto el mínimo porcentaje para poder acceder a otro anticipo de sueldo.
     *
     * @param anticipo
     * @return true si es posible acceder a otro anticipo de sueldo.
     */
    public boolean verificarPorcentajeOtroAnticipo(Anticipo anticipo) {
        boolean valido;
        BigDecimal totalPagado = BigDecimal.ZERO, minParaOtroAnticipo = BigDecimal.ZERO;

        minParaOtroAnticipo = anticipo.getValor().multiply(
                anticipo.getTipoAnticipo().getPorcentajeMaximoSaldoRenovacion()).
                divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        for (AnticipoPlanPago plan : anticipo.getListaAnticipoPlaPlago()) {
            if (plan.getVigente() && plan.getEstadoPago().equals(EstadoPlanPagoEnum.PAGADO.getCodigo())) {
                totalPagado = (totalPagado.add(plan.getMonto())).setScale(2, RoundingMode.HALF_UP);
            }
        }
        if (totalPagado.compareTo(anticipo.getValor()) < 0) {
            LOG.info(UtilCadena.concatenar("Aun no cancela la totalidad del valor del anticipo:",
                    anticipo.getServidor().getNumeroIdentificacion()));
            valido = minParaOtroAnticipo.compareTo(totalPagado) <= 0;
        } else {
            valido = true;
        }
        anticipoHelper.setTotalPagado(totalPagado);
        anticipoHelper.setPorcentajeOtroAnticipo(minParaOtroAnticipo);
        return valido;
    }

    /**
     * Este metodo verifica de acuerdo al tipo de anticipo seleccionado los días y meses permitidos para otorgar
     * anticipos de sueldos.
     */
    public void verificarRestriccionFechas() {
        anticipoHelper.setContinuarAnticipo(false);
        anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
        anticipoHelper.setTotal(BigDecimal.ZERO);
        anticipoHelper.setPorcentajeOtroAnticipo(BigDecimal.ZERO);
        anticipoHelper.setMontoMaximo(BigDecimal.ZERO);
        anticipoHelper.getAnticipoVO().setAcepta(null);
        for (TipoAnticipo t : anticipoHelper.getListaTipoAnticipos()) {
            if (t.getId().equals(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipoId())) {
                anticipoHelper.getAnticipoVO().getAnticipo().setTipoAnticipo(t);
                break;
            }
        }
        anticipoHelper.getListaOpcionesMeses().clear();
        if (UtilFechas.obtenerMes(anticipoHelper.getAnticipoVO().getAnticipo().getFechaSolicitud()) + 1
                >= anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getMesDesde()
                && UtilFechas.obtenerMes(anticipoHelper.getAnticipoVO().getAnticipo().getFechaSolicitud()) + 1
                <= anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getMesHasta()) {
            if (UtilFechas.obtenerDiaDelMes(anticipoHelper.getAnticipoVO().getAnticipo().getFechaSolicitud())
                    >= anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getDiaDesde()
                    && UtilFechas.obtenerDiaDelMes(anticipoHelper.getAnticipoVO().getAnticipo().getFechaSolicitud())
                    <= anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getDiaHasta()) {
                iniciarComboMeses();
                anticipoHelper.setContinuarAnticipo(true);
                //calcular monto máximo de anticipo que se puede solicitar

                if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoAnticipo().equals(
                        TipoAnticipoEnum.FIJO.getCodigo())) {
                    anticipoHelper.setMontoMaximo(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorTechoAnticipo());
                } else {
                    anticipoHelper.setMontoMaximo(anticipoHelper.getAnticipoVO().getDistributivoDetalle().getRmu().
                            multiply(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorTechoAnticipo()).
                            divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
                }
                if ((!anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo().equals(BigDecimal.ZERO))
                        && anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo().compareTo(
                                anticipoHelper.getMontoMaximo()) < 0) {
                    anticipoHelper.setMontoMaximo(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo());
                }

            } else {
                mostrarMensajeEnPantalla("Ud. no puede solicitar un anticipo por restricción de fechas. Las solicitudes de anticipos se permiten entre los días  "
                        + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getDiaDesde() + " y "
                        + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getDiaHasta() + " de cada mes.", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            mostrarMensajeEnPantalla("Ud. no puede solicitar un anticipo en este mes. Las solicitudes de anticipos de sueldos se permiten entre  los meses "
                    + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getMesDesde() + " y  "
                    + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getMesHasta(), FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Genera las cuentas que tiene que devengar el valor solicitado. Si el anticipo incluye diciembre, se determina en
     * base a la parametrización del tipo de anticipo el porcentaje mínimo sobre la RMU. Si éste valor es superior al
     * valor solicitado, sería éste el valor para diciembre y se solicita al usuario ingresar nuevo plazo. Determina el
     * valor de la cuota: Si se incluye diciembre se prorratea el saldo para las cuotas restantes. Si no hay diciembre
     * se prorratea la totalidad para el número de meses seleccionado por el usuario. Si es un funcionario de contrato,
     * se verifica la fecha fin del mismo. Se verifica si un anticipo puede trascender de un periodo fiscal a otro. Se
     * verifica que si un valor es menor a un RMU debe ser pagado en maximo 2 meses.
     *
     * @return
     */
    public String generarPlanCuentas() {
        int cuotas;
        Date fechaInicio, fechaAux;
        AnticipoPlanPago plan;
        BigDecimal valorCuota, valorMaxCuota = BigDecimal.ZERO, valorCapacidadPag = BigDecimal.ZERO;
        int regimen;
        System.out.println(" tipo techo cuota:" + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoCuota());
        anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
        if (anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses() == null) {
            mostrarMensajeEnPantalla("El campo plazo en meses es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        determinarPeriodoInicial();
        fechaInicio = UtilFechas.convertirFechaStringEnDate(anticipoHelper.getAnticipoVO().getAnticipo().getPeriodoInicio() + "/01",
                UtilFechas.FORMATO_FECHA_1);
        if (fechaInicio == null) {
            mostrarMensajeEnPantalla("El periodo inicial no es válido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnticipo().getValor() == null) {
            mostrarMensajeEnPantalla("El campo valor es requerido", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (!anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo().equals(BigDecimal.ZERO)
                && anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo().compareTo(
                        anticipoHelper.getMontoMaximo()) < 0) {
            mostrarMensajeEnPantalla("El valor máximo para solicitar un anticipo de sueldo no puede exceder de "
                    + anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorMaximo(),
                    FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getMontoMaximo().compareTo(anticipoHelper.getAnticipoVO().getAnticipo().getValor()) < 0) {
            mostrarMensajeEnPantalla("El monto máximo para solicitar un anticipo de sueldo es "
                    + anticipoHelper.getMontoMaximo(), FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnioInicio() > UtilFechas.obtenerAnio(new Date())
                && !anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTransciendeEjercicioFiscal()) {
            mostrarMensajeEnPantalla("El plazo de pago no puede transcender del ejercicio fiscal corriente",
                    FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoGarantia().equals(TipoGarantiaEnum.SIEMPRE_GARANTE.getCodigo())
                && anticipoHelper.getAnticipoVO().getAnticipo().getServidorGarante() == null) {
            mostrarMensajeEnPantalla("El garante es requerido",
                    FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoGarantia().equals(TipoGarantiaEnum.ESTABILIDAD_LABORAL.getCodigo())
                && (!anticipoHelper.getAnticipoVO().getDistributivoDetalle().getDistributivo().getModalidadLaboral().getModalidad().equals(TipoModalidadEnum.NOMBRAMIENTO.getCodigo())
                || !anticipoHelper.getAnticipoVO().getDistributivoDetalle().getDistributivo().getModalidadLaboral().isEstabilidadLaboral())) {
            mostrarMensajeEnPantalla("Este tipo de Anticipo está disponible solo para Servidores con Estabilidad Laboral.",
                    FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoCuota().equals(TipoCuotaEnum.CAPACIDAD_PAGO.getCodigo())) {
            valorCapacidadPag = determinarCapacidadPago();
            anticipoHelper.getAnticipoVO().getAnticipo().setValorCapacidadPago(valorCapacidadPag);
//             System.out.println(" capacidad de pago inicial:"+valorCapacidadPag+" valor de cuota:"+
//                     anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorTechoCuota());
            valorCapacidadPag = valorCapacidadPag.multiply(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorTechoCuota())
                    .divide(new BigDecimal(UtilNumeros.NUMERO_CIEN)).setScale(2, RoundingMode.UP);
//              System.out.println(" capacidad de pago porcentaje:"+valorCapacidadPag);
        }

        BigDecimal valorMinimoDiciembre = BigDecimal.ZERO;
        fechaAux = fechaInicio;
        cuotas = 1;
        while (cuotas <= anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses()) {
            if (UtilFechas.obtenerMes(fechaAux) == 11) {
                valorMinimoDiciembre = anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getPorcentajeDescuentoDiciembre().multiply(
                        anticipoHelper.getAnticipoVO().getDistributivoDetalle().getRmu()).divide(new BigDecimal(UtilNumeros.NUMERO_CIEN), 2, RoundingMode.HALF_UP);

                if (valorMinimoDiciembre.compareTo(anticipoHelper.getAnticipoVO().getAnticipo().getValor()) > 0) {
                    valorMinimoDiciembre = anticipoHelper.getAnticipoVO().getAnticipo().getValor();
                }
                LOG.info(UtilCadena.concatenar(" Cuota para diciembre:", valorMinimoDiciembre));
                break;
            }
            fechaAux = UtilFechas.sumarMeses(fechaInicio, cuotas);
            cuotas++;
        }

        if (valorMinimoDiciembre.compareTo(BigDecimal.ZERO) > 0) {
            if ((anticipoHelper.getAnticipoVO().getAnticipo().getValor().subtract(
                    valorMinimoDiciembre)).compareTo(BigDecimal.ZERO) <= 0
                    && anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses() > 1) {
                mostrarMensajeEnPantalla("Para el mes de diciembre se genera una cuota por al menos $" + valorMinimoDiciembre + ", por tanto debe seleccionar un plazo en meses inferior.", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            valorCuota = anticipoHelper.getAnticipoVO().getAnticipo().getValor().subtract(
                    valorMinimoDiciembre).divide(
                            new BigDecimal(anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses() - 1), 2, RoundingMode.HALF_UP);
        } else {
            valorCuota = anticipoHelper.getAnticipoVO().getAnticipo().getValor().divide(
                    new BigDecimal(anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses()), 2, RoundingMode.HALF_UP);
        }
        //calcular monto máximo de cuota que se puede solicitar
        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoCuota().equals(
                TipoCuotaEnum.PORCENTAJE_RMU.getCodigo())) {
            valorMaxCuota = anticipoHelper.getAnticipoVO().getDistributivoDetalle().getRmu().
                    multiply(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getValorTechoCuota()).
                    divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        } else {
            valorMaxCuota = valorCapacidadPag;
        }
        if (valorCuota.compareTo(valorMaxCuota) > 0
                && anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoCuota().equals(TipoCuotaEnum.PORCENTAJE_RMU.getCodigo())) {
            mostrarMensajeEnPantalla("El anticipo solicitado genera una cuota de $" + valorCuota + ", pero su monto máximo de cuota es $"
                    + valorMaxCuota + ". Revise el plazo en meses.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        if (anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTipoTechoCuota().equals(TipoCuotaEnum.CAPACIDAD_PAGO.getCodigo())
                && valorCuota.compareTo(valorMaxCuota) > 0) {
            mostrarMensajeEnPantalla("El anticipo solicitado genera una cuota de $" + valorCuota + ", pero su capacidad de pago es de $"
                    + valorMaxCuota + ". Revise el plazo en meses de ser necesario.", FacesMessage.SEVERITY_ERROR);
            return null;
        }
        regimen = Integer.valueOf(anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getRegimen().getCodigo());
        if (validarAnticipoExistente()) {
            mostrarMensajeEnPantalla("Ud. ya tiene un anticipo de sueldo vigente!!. "
                    + "Y no ha cubierto aún el mínimo para acceder a otro Anticipo de Sueldo. Al menos debe cubrir $"
                    + anticipoHelper.getPorcentajeOtroAnticipo() + ". Ústed ha cancelado $" + anticipoHelper.getTotalPagado(), FacesMessage.SEVERITY_WARN);
            return null;
        }

        fechaAux = fechaInicio;
        anticipoHelper.setTotal(BigDecimal.ZERO);
        cuotas = 0;
        while (cuotas < anticipoHelper.getAnticipoVO().getAnticipo().getPlazoMeses()) {
            int mes = UtilFechas.obtenerMes(fechaAux);
            plan = new AnticipoPlanPago();
            plan.setAnio(UtilFechas.obtenerAnio(fechaAux));
            mes = mes + 1;
            plan.setMes(mes);
            plan.setMesPalabras(mes + " ( " + UtilFechas.obtenerNombreMes(mes) + " )");
            plan.setAnticipo(anticipoHelper.getAnticipoVO().getAnticipo());
            plan.setDividendo(cuotas + 1);
            plan.setEstadoPago(EstadoPlanPagoEnum.PENDIENTE.getCodigo());
            plan.setMonto(valorCuota);
            plan.setMontoNomina(BigDecimal.ZERO);
            plan.setFechaMaximaPago(UtilFechas.obtenerUltimaFechaDelMes(fechaAux));

            iniciarDatosEntidad(plan, Boolean.TRUE);
            //validar porcentaje en diciembre
            if (plan.getMes() == 12 && valorMinimoDiciembre.compareTo(BigDecimal.ZERO) > 0) {
                plan.setMonto(valorMinimoDiciembre);
            }
            //validar fecha contrato
            if (anticipoHelper.getAnticipoVO().getDistributivoDetalle().getDistributivo().getModalidadLaboral().getModalidad().equals(
                    TipoModalidadEnum.CONTRATO.getCodigo())) {

                Date fechaFinContrato = anticipoHelper.getAnticipoVO().getDistributivoDetalle().getFechaFin();
                if (UtilFechas.truncarFecha(UtilFechas.obtenerUltimaFechaDelMes(fechaAux)).compareTo(
                        UtilFechas.truncarFecha(fechaFinContrato)) > 0) {
                    mostrarMensajeEnPantalla("El plan de pago no debe exceder a la fecha fin de su contrato vigente que es "
                            + UtilFechas.formatear(fechaFinContrato),
                            FacesMessage.SEVERITY_ERROR);
                    anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
                    return null;
                }
            }
            //validar si trasciende de ejercicio fiscal
            if (plan.getAnio() > anticipoHelper.getAnticipoVO().getAnioInicio()
                    && !anticipoHelper.getAnticipoVO().getAnticipo().getTipoAnticipo().getTransciendeEjercicioFiscal()) {
                mostrarMensajeEnPantalla("El plazo de pago no puede transcender del ejercicio fiscal corriente",
                        FacesMessage.SEVERITY_ERROR);
                anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().clear();
                anticipoHelper.setTotal(BigDecimal.ZERO);
                anticipoHelper.setPorcentajeOtroAnticipo(BigDecimal.ZERO);
                return null;
            }
            cuotas++;
            anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().add(plan);
            fechaAux = UtilFechas.sumarMeses(fechaInicio, cuotas);
            anticipoHelper.setTotal(anticipoHelper.getTotal().add(plan.getMonto()).setScale(2, RoundingMode.HALF_UP));
        }
        cuadrarTotalPlanPago(anticipoHelper.getTotal());
        determinarPeriodoFinal();
        return null;
    }

    /**
     * Determina la capacidad de pago del servidor, tomando como referencia el mes inmediatamente anterior.
     *
     * @return
     */
    private BigDecimal determinarCapacidadPago() {
        BigDecimal valorCapacidad = BigDecimal.ZERO;
        try {
            PeriodoNomina pn = periodoNominaDao.buscarPorFecha(UtilFechas.sumarMeses(new Date(), -1));
            if (pn == null) {
                error(this.getClass().getName(), "No existe un periodo de nómina activo.", null);
            } else {
                BusquedaNominaVO r = new BusquedaNominaVO();
                r.setServidorLogueado(anticipoHelper.getAnticipoVO().getAnticipo().getServidor().getId());
                List<Nomina> listaNominasMesAnterior
                        = nominaDao.buscarVigentePorPeriodoNominaId(pn.getId(), anticipoHelper.getAnticipoVO().getAnticipo().getInstitucionEjercicioFiscalId());
                System.out.println(" listaNominasMesAnterior:" + listaNominasMesAnterior.size());
                if (!listaNominasMesAnterior.isEmpty()) {
                    for (Nomina n : listaNominasMesAnterior) {
                        r.setIdNomina(n.getId());
                        r.setTipoNomina(n.getTipoNominaId());
                        r.setPeriodoNomina(n.getPeriodoNominaId());
                        r.setPeriodoFiscal(n.getInstitucionEjercicioFiscalId());
                        r.setEsCapacidadPago(Boolean.TRUE);
                        valorCapacidad = valorCapacidad.add(nominaServicio.calcularCapacidadPago(r));
                        System.out.println(" valorCapacidad:" + valorCapacidad);
                    }
                }
            }
        } catch (DaoException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar capacidad pago", ex);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar capacidad pago", ex);
        }
        return valorCapacidad;
    }

    /**
     * Determinar periodo final y prorrateo de valor faltante en primera cuota.
     *
     * @param total Valor total de cuotas.
     */
    private void cuadrarTotalPlanPago(BigDecimal total) {
        BigDecimal valorDif;
        Boolean sumar = null;
        Integer contCentavos;
        if (!anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago().isEmpty()) {
            valorDif = anticipoHelper.getAnticipoVO().getAnticipo().getValor().subtract(total).setScale(2, RoundingMode.HALF_UP);
            if (valorDif.compareTo(BigDecimal.ZERO) > 0) {
                sumar = Boolean.TRUE;
            } else if (valorDif.compareTo(BigDecimal.ZERO) < 0) {

                sumar = Boolean.FALSE;
            }
            if (sumar != null) {

                contCentavos = Math.abs(valorDif.multiply(new BigDecimal(100)).intValue());

                while (contCentavos > 0) {
                    anticipoHelper.setTotal(BigDecimal.ZERO);
                    for (AnticipoPlanPago plan : anticipoHelper.getAnticipoVO().getListaAnticipoPlanPago()) {
                        if (Math.abs(contCentavos) > 0) {
                            if (sumar) {
                                plan.setMonto(plan.getMonto().add(new BigDecimal(0.01)).setScale(2, RoundingMode.HALF_UP));
                            } else {
                                plan.setMonto(plan.getMonto().subtract(new BigDecimal(0.01)).setScale(2, RoundingMode.HALF_UP));
                            }
                            contCentavos--;
                        }
                        anticipoHelper.setTotal(anticipoHelper.getTotal().add(plan.getMonto()).setScale(2, RoundingMode.HALF_UP));
                    }
                }
            }
        }
    }

    /**
     * Redirecciona a la lista de anticipos.
     *
     * @return
     */
    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     * Redirecciona a la página que crea un nuevo registro.
     *
     * @return
     */
    public String irNuevo() {
        iniciarNuevo();
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Redirecciona la pagina del portal.
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
    }

    /**
     * Imprime reporte de solicitud de anticipos.
     */
    public void imprimir() {

        setReporte(ReportesEnum.SOLICITUD_ANTICIPO.getReporte());
        parametrosReporte = new HashMap<String, String>();
        parametrosReporte.put("p_titulo", "FORMULARIOS PARA ANTICIPOS DE SUELDO");
        parametrosReporte.put("servidorId", obtenerUsuarioConectado().getServidor().getId().toString());
        parametrosReporte.put("solicitudAnticipoId", anticipoHelper.getAnticipoVO().getAnticipo().getId().toString());
        generarUrlDeReporte();
    }

    /**
     * Este metodo transacciona la busqueda del nombre del estado del anticipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoAnticipo(final String codigo) {
        return EstadoAnticipoEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda del nombre del estado de la cuota parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanPago(final String codigo) {
        return EstadoPlanPagoEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripción del tipo de documento de identificacion parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * @return the anticipoHelper
     */
    public AnticipoHelper getAnticipoHelper() {
        return anticipoHelper;
    }

    /**
     * @param anticipoHelper the anticipoHelper to set
     */
    public void setAnticipoHelper(AnticipoHelper anticipoHelper) {
        this.anticipoHelper = anticipoHelper;
    }

    /**
     * @return the admServicio
     */
    public AdministracionServicio getAdmServicio() {
        return admServicio;
    }

    /**
     * @param admServicio the admServicio to set
     */
    public void setAdmServicio(AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }
}
