/*
 *  GastoPersonalControlador.java
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
 *  14/11/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.GastoPersonalHelper;
import ec.com.atikasoft.proteus.dao.CotizacionIessDao;
import ec.com.atikasoft.proteus.dao.GastoPersonalDao;
import ec.com.atikasoft.proteus.dao.TipoGastoPersonalDao;
import ec.com.atikasoft.proteus.enums.CatalogoEnum;
import ec.com.atikasoft.proteus.enums.GastoPersonalEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.ReportesEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoProyeccionGastoPersonalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.CotizacionIess;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.GastoPersonal;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.TipoGastoPersonal;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ImpuestoRentaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
 * GastoPersonal
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "gastoPersonalBean")
@ViewScoped
public class GastoPersonalControlador extends CatalogoControlador {

    /**
     * Logger de clases.
     */
    private Logger LOG = Logger.getLogger(GastoPersonalControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/impuesto_renta/gasto_personal/gasto_personal.jsf";
    /**
     * Regla de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/procesos/impuesto_renta/gasto_personal/lista_gasto_personal.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Sumatoria del total de gp no pueder ser mayor a 1.3 (130%) veces la
     * fracción básica.
     */
    public static BigDecimal MAXIMO_DEDUCIBLE_SOBRE_FRACCION_BASICA = BigDecimal.ZERO;
    /**
     * Sumatoria del total de gp no pueder se mayor al 50% del total de los
     * ingresos gravables.
     */
    public static BigDecimal PORCENTAJE_MAXIMO_DEDUCIBLE_SOBRE_INGRESOS = BigDecimal.ZERO;
    /**
     * el monto de la exoneración por discapacidad será el equivalente al doble
     * de la fracción básica exenta de Impuesto a la Renta.
     */
    public static BigDecimal EXONERACION_DISCAPACIDAD = BigDecimal.ZERO;
    /**
     * el beneficio de la exoneración por tercera edad se configura a partir del
     * ejercicio en el cual el beneficiario cumpla los 65 años de edad, El monto
     * de la exoneración será el equivalente al doble de la fracción básica
     * exenta de Impuesto a la Renta.
     */
    public static BigDecimal EXONERACION_TERCERA_EDAD = BigDecimal.ZERO;
    /**
     * Inicio de la tercera edad
     */
    public static Integer NUMERO_ANIOS_TERCERA_EDAD = 0;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Servicio de Impuesto a la renta.
     */
    @EJB
    private ImpuestoRentaServicio impuestoRentaServicio;

    /**
     *
     */
    @EJB
    private TipoGastoPersonalDao tipoGastoPersonalDao;

    /**
     *
     */
    @EJB
    private CotizacionIessDao cotizacionIessDao;

    /**
     *
     */
    @EJB
    private GastoPersonalDao gastoPersonalDao;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{gastoPersonalHelper}")
    private GastoPersonalHelper gastoPersonalHelper;

    /**
     * Constructor por defecto.
     */
    public GastoPersonalControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(gastoPersonalHelper);
        setGastoPersonalHelper(gastoPersonalHelper);
        UsuarioVO usuario = obtenerUsuarioConectado();
        gastoPersonalHelper.setDistributivoDetalle(usuario.getDistributivoDetalle());
        gastoPersonalHelper.getGastoPersonal().setServidor(usuario.getServidor());
        gastoPersonalHelper.getGastoPersonal().setIdServidor(usuario.getServidor().getId());
        gastoPersonalHelper.getGastoPersonal().setIdEjercicioFiscal(usuario.getInstitucionEjercicioFiscal().getId());
        gastoPersonalHelper.getGastoPersonal().setEjercicioFiscal(usuario.getInstitucionEjercicioFiscal());
        gastoPersonalHelper.setFechIngreso(usuario.getServidor().getFechaIngresoInstitucion());
        gastoPersonalHelper.getMsgValidacion().clear();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
        verificarParametroInstitucional();
        iniciarComboEjercicioFiscal();
        cargarTiposGastosPersonales();
        cargarValoresEjercicioFiscal();
        calcularTotales();

    }

    private void cargarValoresEjercicioFiscal() {
        EjercicioFiscal ef = obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getEjercicioFiscal();
        MAXIMO_DEDUCIBLE_SOBRE_FRACCION_BASICA = ef.getMaximoDeducibleSobreFracionBasica();
        PORCENTAJE_MAXIMO_DEDUCIBLE_SOBRE_INGRESOS = ef.getPorcentajeMaximoDeducibleSobreIngresos();
        EXONERACION_DISCAPACIDAD = ef.getExoneracionSobreDiscapacidad();
        EXONERACION_TERCERA_EDAD = ef.getNumeroAniosTerceraEdad();
        NUMERO_ANIOS_TERCERA_EDAD = ef.getNumeroAniosTerceraEdad().intValue();
    }

    /**
     *
     * @throws DaoException
     */
    private void cargarTiposGastosPersonales() {
        try {
            List<TipoGastoPersonal> tipos = tipoGastoPersonalDao.buscarPorEjercicioFiscal(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getEjercicioFiscal().getId());
            gastoPersonalHelper.setListaTipoGastoPersonal(tipos);
        } catch (DaoException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al cargar tipos de gastos personales", e);

        }
    }

    @Override
    public String guardar() {
        try {
            calcularTotales();
            if (!gastoPersonalHelper.getMsgValidacion().isEmpty()) {
                mostrarMensajeEnPantalla("Resuelva las observaciones antes de guardar.", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (gastoPersonalHelper.getGastoPersonal().getTotalIngresos().equals(BigDecimal.ZERO)) {
                mostrarMensajeEnPantalla("Calcule antes de guardar.", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (gastoPersonalHelper.getEsNuevo()) {
                if (validarExisteProyeccionEnEjercicioFiscal()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    gastoPersonalHelper.getGastoPersonal().setTipo(TipoProyeccionGastoPersonalEnum.ORIGINAL.getCodigo());
                    impuestoRentaServicio.guardarGastoPersonal(gastoPersonalHelper.getGastoPersonal(), 
                            gastoPersonalHelper.getDistributivoDetalle().getRmu());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    gastoPersonalHelper.setBotonGuardar(Boolean.FALSE);
                }

            } else {
                int mesActual = UtilFechas.obtenerMes(new Date());
                if (mesActual > 2) {
                    gastoPersonalHelper.getGastoPersonal().setTipo(TipoProyeccionGastoPersonalEnum.
                            RELIQUIDACION.getCodigo());
                } else {
                    gastoPersonalHelper.getGastoPersonal().setTipo(TipoProyeccionGastoPersonalEnum.
                            ORIGINAL.getCodigo());
                }
                impuestoRentaServicio.actualizarGastoPersonal(gastoPersonalHelper.getGastoPersonal(),
                        gastoPersonalHelper.getDistributivoDetalle().getRmu());

                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe una proyección de gastos para el
     * servidor y ejericio fiscal.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarExisteProyeccionEnEjercicioFiscal() {
        Boolean hayCodigo = false;
        try {
            gastoPersonalHelper.getListaGastoPersonalCodigo().clear();
            gastoPersonalHelper.setListaGastoPersonalCodigo(
                    impuestoRentaServicio.listarGastoPersonalPorServidorYEjercicioFiscal(
                            gastoPersonalHelper.getGastoPersonal().getServidor().getId(),
                            gastoPersonalHelper.getGastoPersonal().getIdEjercicioFiscal()));

            for (GastoPersonal g : gastoPersonalHelper.getListaGastoPersonalCodigo()) {
                if (g.getTipo().equals(TipoProyeccionGastoPersonalEnum.ORIGINAL.getCodigo())) {
                    hayCodigo = true;
                    break;
                }
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la existencia de proyeccion de gastos", ex);
        }
        return hayCodigo;
    }

    @Override
    public String iniciarEdicion() {
        try {
            UsuarioVO usuario = obtenerUsuarioConectado();
            Object cloneBean = BeanUtils.cloneBean(gastoPersonalHelper.getGastoPersonalEditDelete());
            GastoPersonal d = (GastoPersonal) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            gastoPersonalHelper.setGastoPersonal(d);
            gastoPersonalHelper.setEsNuevo(Boolean.FALSE);
            gastoPersonalHelper.setFechIngreso(usuario.getServidor().getFechaIngresoInstitucion());
            gastoPersonalHelper.getMsgValidacion().clear();
            calcularEdadServidor();
            determinarDiscapacidad();
            calcularIngresosEnEjercicioFiscal();
            calcularTotales();
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

    @Override
    public String iniciarNuevo() {
        try {

            UsuarioVO usuario = obtenerUsuarioConectado();

            List<GastoPersonal> gastos = gastoPersonalDao.buscarTodosPorServidorYEjercicioFiscal(
                    usuario.getServidor().getId(), usuario.getInstitucionEjercicioFiscal().getEjercicioFiscal().getId());
            if (gastos.isEmpty()) {
                gastoPersonalHelper.iniciador();
                gastoPersonalHelper.setDistributivoDetalle(usuario.getDistributivoDetalle());
                gastoPersonalHelper.getGastoPersonal().setServidor(usuario.getServidor());
                gastoPersonalHelper.getGastoPersonal().setIdServidor(usuario.getServidor().getId());
                gastoPersonalHelper.getGastoPersonal().setIdEjercicioFiscal(usuario.getInstitucionEjercicioFiscal().getId());
                gastoPersonalHelper.getGastoPersonal().setEjercicioFiscal(usuario.getInstitucionEjercicioFiscal());
                gastoPersonalHelper.setFechIngreso(usuario.getServidor().getFechaIngresoInstitucion());
                iniciarDatosEntidad(gastoPersonalHelper.getGastoPersonal(), Boolean.TRUE);
                gastoPersonalHelper.setEsNuevo(Boolean.TRUE);
                gastoPersonalHelper.getGastoPersonal().setTipo(TipoProyeccionGastoPersonalEnum.ORIGINAL.getCodigo());
                if (usuario.getEjercicioFiscal().getFraccionBasica() == null) {
                    mostrarMensajeEnPantalla("No se ha configurado el valor de la fracción básica para el Ejercicio Fiscal "
                            + usuario.getEjercicioFiscal().getNombre(), FacesMessage.SEVERITY_ERROR);
                    return null;
                }

                gastoPersonalHelper.getMsgValidacion().clear();
                calcularEdadServidor();
                determinarDiscapacidad();
                calcularIngresosEnEjercicioFiscal();
                return FORMULARIO_ENTIDAD;
            } else {
                mostrarMensajeEnPantalla("No puede crear un nuevo registro de gasto personales porque ya existe",
                        FacesMessage.SEVERITY_ERROR);
                return null;
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar", e);
        }
        return null;

    }

    @Override
    public String borrar() {
        try {
            impuestoRentaServicio.eliminarGastoPersonal(gastoPersonalHelper.getGastoPersonalEditDelete());
            gastoPersonalHelper.getListaGastosPersonales().
                    remove(gastoPersonalHelper.getGastoPersonalEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            if (gastoPersonalHelper.getListaGastosPersonales() == null) {
                gastoPersonalHelper.setListaGastosPersonales(new ArrayList<GastoPersonal>());
            }
            gastoPersonalHelper.getListaGastosPersonales().clear();
            gastoPersonalHelper.setListaGastosPersonales(
                    impuestoRentaServicio.listarGastoPersonalPorServidor(
                            obtenerUsuarioConectado().getServidor().getId()));
            for (GastoPersonal g : gastoPersonalHelper.getListaGastosPersonales()) {
                if (g.getIdEjercicioFiscal().equals(obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId())) {
                    g.setEditable(true);
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda inicial", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de INSTITUCION ejercicios
     * fiscales.
     */
    private void iniciarComboEjercicioFiscal() {
        buscarListaEjercicioFiscal();
        gastoPersonalHelper.getOpcionEjercicioFiscal().clear();
        iniciarCombos(gastoPersonalHelper.getOpcionEjercicioFiscal());
        for (InstitucionEjercicioFiscal t : gastoPersonalHelper.getListaEjercicioFiscal()) {
            gastoPersonalHelper.getOpcionEjercicioFiscal().add(new SelectItem(t.getId(),
                    t.getEjercicioFiscal().getNombre()));
        }
    }

    /**
     * Obtiene lista de INSTITUCION ejercicios fiscales.
     *
     */
    public void buscarListaEjercicioFiscal() {
        try {
            gastoPersonalHelper.getListaEjercicioFiscal().clear();
            gastoPersonalHelper.setListaEjercicioFiscal(admServicio.
                    listarTodosInstitucionesEjercicioFiscalPorInstitucion(
                    obtenerUsuarioConectado().getInstitucion().getId()));
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicio fiscal ", ex);
        }
    }

    /**
     * Permite verificar si es posible o no la actualización/creación de gastos
     * personales.
     */
    public void verificarParametroInstitucional() {
        boolean activarBotonNuevo = false;
        try {
            ParametroInstitucional articulo = admServicio.buscarParametroIntitucional(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.
                            ADMINISTRACION_GASTOS_PERSONALES.getCodigo());
            if (articulo != null && !articulo.getValorTexto().isEmpty()) {
                if (articulo.getValorTexto().toUpperCase().equals(SiNoEnum.SI.getDescripcion().toUpperCase())) {
                    activarBotonNuevo = true;
                } else if (articulo.getValorTexto().toUpperCase().equals(SiNoEnum.SI.getCodigo().toUpperCase())) {
                    activarBotonNuevo = true;
                } else if (articulo.getValorTexto().substring(0, 1).toUpperCase().equals(
                        SiNoEnum.SI.getCodigo().toUpperCase())) {
                    activarBotonNuevo = true;
                } else {
                    gastoPersonalHelper.getMsgValidacion().add("La Configuración de Parámetros "
                            + "Institucionales indica que la Planificación de Gastos Personales se "
                            + "encuentra inhabilitada.");
                }
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de parametro institucional", ex);
        }
        gastoPersonalHelper.setEdicionGastos(activarBotonNuevo);
        LOG.info("Parametro Institucional para planificacion  de gastos personales: " + activarBotonNuevo);
    }

    /**
     * Permite obtener la distribucion de puestos para el servidor<p>
     * Con esta información se carga el Ejercicio Fiscal y se obtiene el
     * sueldo(ingresoMensual) base sobre el cual se calculan los ingresos del
     * servidor.
     *
     * @return true si existe la configuracion de puestos de trabajo </p>
     */
    public boolean verificarParametrizacionDistributivo() {
        boolean hayConfiguracion = false;
        if (gastoPersonalHelper.getDistributivoDetalle() != null
                && gastoPersonalHelper.getDistributivoDetalle().getId() != null) {

            hayConfiguracion = true;
        } else {
            hayConfiguracion = false;
        }
        return hayConfiguracion;
    }

    /**
     * Calcula los ingresos totales en el Ejercicio Fiscal en la institucion.
     */
    public void calcularIngresosEnEjercicioFiscal() {
        try {
            BigDecimal ingresoMensual = BigDecimal.ZERO, totalIngresos = BigDecimal.ZERO;
            String msg;
            Integer[] tiempoEnEmpresa;
            gastoPersonalHelper.setBotonGuardar(Boolean.FALSE);
            if (gastoPersonalHelper.getFechIngreso() == null) {
                msg = UtilCadena.concatenar(EL_SERVIDOR, " ", gastoPersonalHelper.getGastoPersonal().
                        getServidor().getNumeroIdentificacion(), SIN_CONFIGURACION_PUESTOS);
                mostrarMensajeEnPantalla(msg, FacesMessage.SEVERITY_ERROR);
                gastoPersonalHelper.setSinConfiguracion(msg);
                LOG.info(msg);
                return;
            }
            if (!verificarParametrizacionDistributivo()) {
                msg = UtilCadena.concatenar(SIN_FECHA_INGRESO_SERVIDOR, gastoPersonalHelper.getGastoPersonal().
                        getServidor().getNumeroIdentificacion(), " a la institución.");
                mostrarMensajeEnPantalla(msg, FacesMessage.SEVERITY_ERROR);
                LOG.info(msg);
                return;
            }

            if (gastoPersonalHelper.getDistributivoDetalle() != null && gastoPersonalHelper.
                    getDistributivoDetalle().getId() != null) {
                ingresoMensual = gastoPersonalHelper.getDistributivoDetalle().getRmu();

            }
            // Obtener mes actual del sistema.
            Date fechaActual = UtilFechas.truncarFecha(new Date());
            long mesActual = UtilFechas.obtenerMes(fechaActual);
            long mesesCalculados = mesActual;
            long mesesProyectados = 12 - mesesCalculados;

            // calcular valor proyectados
            Calendar fechaInicioMes = Calendar.getInstance();
            fechaInicioMes.setTime(fechaActual);
            fechaInicioMes.set(Calendar.DAY_OF_MONTH, fechaInicioMes.getActualMinimum(Calendar.DAY_OF_MONTH));
            if (UtilFechas.truncarFecha(gastoPersonalHelper.getFechIngreso()).compareTo(fechaInicioMes.getTime()) <= 0) {
                totalIngresos = totalIngresos.add(ingresoMensual.multiply(new BigDecimal(mesesProyectados)));
            } else {
                totalIngresos = totalIngresos.add(ingresoMensual.multiply(new BigDecimal(mesesProyectados - 1)));
                totalIngresos = totalIngresos.add(ingresoMensual.multiply(new BigDecimal(
                        UtilFechas.obtenerDiaDelMes(fechaActual) / 30)));
            }

            // restar aporte iess
            BigDecimal porcentajeAporte = buscarAportePersonalServidor();
            porcentajeAporte = porcentajeAporte.divide(new BigDecimal(100)).setScale(4, RoundingMode.HALF_UP);
            totalIngresos = totalIngresos.subtract(totalIngresos.multiply(porcentajeAporte));

            // calcular valor calculado
            BigDecimal totalNominas = impuestoRentaServicio.calcularTotalIngresosGravables(
                    gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().getEjercicioFiscal(),
                    gastoPersonalHelper.getGastoPersonal().getServidor(), mesesCalculados);
            totalIngresos = totalIngresos.add(totalNominas);
            gastoPersonalHelper.getGastoPersonal().setIngresos(totalIngresos);
            gastoPersonalHelper.setBotonGuardar(Boolean.TRUE);

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar ingresos en nominas ", e);

        }

    }

    /**
     * Valida los montos maximos.
     *
     * @return
     */
    private boolean validarMontoMaximoGasto() {
        String msg = "";
        boolean valido = true;
        BigDecimal montoMaximo;

        gastoPersonalHelper.getMsgValidacion().clear();
        if (gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().getEjercicioFiscal().
                getFraccionBasica() == null) {
            msg = UtilCadena.concatenar(msg, "La fracción básica no ha sido configurada.");
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.ALIMENTACION.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        if (gastoPersonalHelper.getGastoPersonal().getAlimentacion().compareTo(montoMaximo) > 0) {
            msg = UtilCadena.concatenar("Gasto por Alimentación excede el monto máximo permitido que es", 
                    " ", montoMaximo);
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.EDUCACION.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        if (gastoPersonalHelper.getGastoPersonal().getEducacion().compareTo(montoMaximo) > 0) {
            msg = UtilCadena.concatenar("Gasto por Educación excede el monto máximo permitido que es", " ", 
                    montoMaximo);
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.SALUD.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        if (gastoPersonalHelper.getGastoPersonal().getSalud().compareTo(montoMaximo) > 0) {
            msg = UtilCadena.concatenar("Gasto por Salud excede el monto máximo permitido que es", " ", montoMaximo);
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.VIVIENDA.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        if (gastoPersonalHelper.getGastoPersonal().getVivienda().compareTo(montoMaximo) > 0) {
            msg = UtilCadena.concatenar("Gasto por Vivienda excede el monto máximo permitido que es", " ", montoMaximo);
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.VESTIMENTA.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);

        if (gastoPersonalHelper.getGastoPersonal().getVestimenta().compareTo(montoMaximo) > 0) {
            msg = UtilCadena.concatenar("Gasto por Vestimenta excede el monto máximo permitido que es", " ", 
                    montoMaximo);
            gastoPersonalHelper.getMsgValidacion().add(msg);
            valido = false;
        }
        return valido;
    }

    /**
     *
     * @return
     */
    private void determinarMontoMaximoGasto() {
        String msg = "";
        BigDecimal montoMaximo;

   //     gastoPersonalHelper.getMsgValidacion().clear();
        if (gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().getEjercicioFiscal().getFraccionBasica()
                == null) {
            msg = UtilCadena.concatenar(msg, "La fracción básica no ha sido configurada.");
            gastoPersonalHelper.getMsgValidacion().add(msg);
        }
        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.ALIMENTACION.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        gastoPersonalHelper.setMaxGastosVVEA(montoMaximo);

        montoMaximo = obtenerPorcentajeMaximoPorGasto(GastoPersonalEnum.SALUD.getCodigo()).multiply(
                gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().
                getEjercicioFiscal().getFraccionBasica()).setScale(2, RoundingMode.HALF_UP);
        gastoPersonalHelper.setMaxSalud(montoMaximo);

        montoMaximo = gastoPersonalHelper.getGastoPersonal().getTotalIngresos().
                multiply(PORCENTAJE_MAXIMO_DEDUCIBLE_SOBRE_INGRESOS).setScale(2, RoundingMode.HALF_UP);
        gastoPersonalHelper.setMaxDeducible(montoMaximo);

        montoMaximo = gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().getEjercicioFiscal().
                getFraccionBasica().
                multiply(EXONERACION_DISCAPACIDAD).setScale(2, RoundingMode.HALF_UP);

        gastoPersonalHelper.setMaxExoneraciones(montoMaximo);

        montoMaximo = gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal().getEjercicioFiscal().
                getFraccionBasica().
                multiply(MAXIMO_DEDUCIBLE_SOBRE_FRACCION_BASICA).setScale(2, RoundingMode.HALF_UP);
        gastoPersonalHelper.setMaxDeducibleSobreFraccion(montoMaximo);

    }

    /**
     * calcula todos los totales de todos los ingresos en el mismo empleador y
     * con otros empleadores.
     *
     * @param porcentajeAporte porcentaje de aporte personal de iess. Se calcula
     * solo para el empleador.
     * @return
     */
    public BigDecimal calcularTotalTodosIngresos(BigDecimal porcentajeAporte) {
        BigDecimal totalIngresos;
        totalIngresos = gastoPersonalHelper.getGastoPersonal().getIngresos().add(
                gastoPersonalHelper.getGastoPersonal().getIngresosOtroEmpleador()).setScale(2, RoundingMode.HALF_UP);

        gastoPersonalHelper.getGastoPersonal().setIessPersonal(
                totalIngresos.multiply(porcentajeAporte).setScale(2, RoundingMode.HALF_UP));

//        totalIngresos = totalIngresos.add(
//                gastoPersonalHelper.getGastoPersonal().getIessPersonalOtroEmpleador()).setScale(2, RoundingMode.HALF_UP);
        return totalIngresos;
    }

    /**
     * calcula el total disminuyendo los aportes personales al IESS.
     *
     * @return
     */
    public BigDecimal calcularTotalSinIESS() {
        return gastoPersonalHelper.getGastoPersonal().getTotalIngresos().subtract(
                gastoPersonalHelper.getGastoPersonal().getIessPersonal().add(
                        gastoPersonalHelper.getGastoPersonal().getIessPersonalOtroEmpleador())).setScale(2, 
                                RoundingMode.HALF_UP);
    }

    /**
     * Calcula el total de gastos deducibles.
     *
     * @return
     */
    public BigDecimal calcularTotalTodosGastos() {
        return gastoPersonalHelper.getGastoPersonal().getAlimentacion().add(
                gastoPersonalHelper.getGastoPersonal().getEducacion()).add(
                        gastoPersonalHelper.getGastoPersonal().getSalud()).add(
                        gastoPersonalHelper.getGastoPersonal().getVestimenta()).add(
                        gastoPersonalHelper.getGastoPersonal().getVivienda()).add(
                        gastoPersonalHelper.getGastoPersonal().getExoneracionDiscapacidad()).add(
                        gastoPersonalHelper.getGastoPersonal().getExoneracionTerceraEdad()).setScale(2, 
                                RoundingMode.HALF_UP);
    }

    /**
     * Totaliza los ingresos durante el ejercicio fiscal y también los valores a
     * deducir.
     *
     * @return
     */
    public String calcularTotales() {
        BigDecimal totalDeducible, valorCalculado, totalIngresos, porcentajeAporte;
        String msg = "";

        gastoPersonalHelper.getMsgValidacion().clear();
        gastoPersonalHelper.getGastoPersonal().setTotalDeducible(BigDecimal.ZERO);
        InstitucionEjercicioFiscal eje = gastoPersonalHelper.getGastoPersonal().getEjercicioFiscal();

        porcentajeAporte = buscarAportePersonalServidor();
        if (porcentajeAporte.compareTo(BigDecimal.ZERO) == 0) {
            gastoPersonalHelper.setBotonGuardar(Boolean.FALSE);
            msg = UtilCadena.concatenar("No se encuentra la configuración de Cotizaciones IESS para el Nivel "
                    + "Ocupacional del Servidor.");
            gastoPersonalHelper.getMsgValidacion().add(msg);
            mostrarMensajeEnPantalla(msg, FacesMessage.SEVERITY_ERROR);
            return null;
        } else {
            porcentajeAporte = porcentajeAporte.divide(new BigDecimal(100)).setScale(4, RoundingMode.HALF_UP);
        }

        if ((gastoPersonalHelper.getGastoPersonal().getIngresosOtroEmpleador().compareTo(BigDecimal.ZERO) > 0)
                && gastoPersonalHelper.getGastoPersonal().getIessPersonalOtroEmpleador().compareTo(BigDecimal.ZERO) >
                0) {
            BigDecimal aporteMinimo = buscarPorcentajeAportePersonalMinimo().multiply(new BigDecimal(0.01));
            BigDecimal aporteTemp = aporteMinimo.multiply(gastoPersonalHelper.getGastoPersonal().
                    getIngresosOtroEmpleador());
            if (gastoPersonalHelper.getGastoPersonal().getIessPersonalOtroEmpleador().compareTo(aporteTemp) < 0) {
                msg = "El aporte con otro Empleador debe calcularse al menos con el 9.35%. "
                        + "EL valor correspondería al menos a:" + aporteTemp.setScale(2, RoundingMode.HALF_UP);
                mostrarMensajeEnPantalla(msg, FacesMessage.SEVERITY_ERROR);
                return null;
            }

        }
        if (validarMontoMaximoGasto()) {
            totalIngresos = calcularTotalTodosIngresos(porcentajeAporte);
            totalDeducible = calcularTotalTodosGastos();

            gastoPersonalHelper.getGastoPersonal().setTotalDeducible(totalDeducible);
            gastoPersonalHelper.getGastoPersonal().setTotalIngresos(totalIngresos);
            gastoPersonalHelper.setTotalSinIESS(calcularTotalSinIESS());

            if (gastoPersonalHelper.getTotalSinIESS() == null) {
                gastoPersonalHelper.setTotalSinIESS(BigDecimal.ZERO);
            }

            valorCalculado = gastoPersonalHelper.getGastoPersonal().getTotalIngresos().
                    multiply(PORCENTAJE_MAXIMO_DEDUCIBLE_SOBRE_INGRESOS).setScale(2, RoundingMode.HALF_UP);
            if (totalDeducible.compareTo(valorCalculado) > 0) {
                msg = UtilCadena.concatenar("El total a deducir sobrepasa el maximo deducible sobre el Total de"
                        + " Ingresos que es", " ",
                        valorCalculado);
                gastoPersonalHelper.getMsgValidacion().add(msg);
            }

            valorCalculado = eje.getEjercicioFiscal().getFraccionBasica().
                    multiply(MAXIMO_DEDUCIBLE_SOBRE_FRACCION_BASICA).setScale(2, RoundingMode.HALF_UP);
            if (totalDeducible.compareTo(valorCalculado) > 0) {
                msg = UtilCadena.concatenar("El total a deducir sobrepasa el maximo deducible sobre la fracción"
                        + " básica que es", " ", valorCalculado);
                gastoPersonalHelper.getMsgValidacion().add(msg);
            }
            valorCalculado = eje.getEjercicioFiscal().getFraccionBasica().
                    multiply(EXONERACION_DISCAPACIDAD).setScale(2, RoundingMode.HALF_UP);
            if (totalDeducible.compareTo(valorCalculado) > 0) {
                msg = UtilCadena.concatenar("El valor por exoneración por Discapacidad sobrepasa el maximo deducible "
                        + "sobre la fracción básica que es", " ", valorCalculado);
                gastoPersonalHelper.getMsgValidacion().add(msg);
            }

            valorCalculado = eje.getEjercicioFiscal().getFraccionBasica().
                    multiply(EXONERACION_TERCERA_EDAD).setScale(2, RoundingMode.HALF_UP);
            if (totalDeducible.compareTo(valorCalculado) > 0) {
                msg = UtilCadena.concatenar("El valor por exoneración por Tercera Edad sobrepasa el maximo"
                        + " deducible sobre la fracción básica que es", " ", valorCalculado);
                gastoPersonalHelper.getMsgValidacion().add(msg);

            }
        }

        if (gastoPersonalHelper.getMsgValidacion().size() > 0) {
            gastoPersonalHelper.setBotonGuardar(Boolean.FALSE);
        } else {
            gastoPersonalHelper.setBotonGuardar(Boolean.TRUE);
        }
        determinarMontoMaximoGasto();
        determinarDiscapacidad();
        return null;
    }

    /**
     * Determina si un servidor es de la tercera edad.
     *
     */
    public void calcularEdadServidor() {
        Integer[] edad;
        if (gastoPersonalHelper.getGastoPersonal().getServidor().getFechaNacimiento() != null) {
            edad = UtilFechas.calcularAniosMesesDiasEntreFechas(
                    gastoPersonalHelper.getGastoPersonal().getServidor().getFechaNacimiento(), new Date());
            if (edad[0] >= NUMERO_ANIOS_TERCERA_EDAD) {
                gastoPersonalHelper.setEsTerceraEdad(true);
            } else {
                gastoPersonalHelper.setEsTerceraEdad(false);
            }
        } else {
            gastoPersonalHelper.setEsTerceraEdad(false);
            mostrarMensajeEnPantalla("No se ha configurado la fecha de nacimiento del servidor",
                    FacesMessage.SEVERITY_WARN);
        }
    }

    /**
     * determina si un servidor es discapacitado.
     *
     */
    public void determinarDiscapacidad() {
        if (gastoPersonalHelper.getGastoPersonal().getServidor().getCatalogoCapacidades() != null
                && !gastoPersonalHelper.getGastoPersonal().getServidor().getCatalogoCapacidades().getCodigo().
                        equals(CatalogoEnum.NINGUNO.getCodigo())) {
            gastoPersonalHelper.setEsDiscapacitado(true);
        } else {
            gastoPersonalHelper.setEsDiscapacitado(false);
        }
    }

    /**
     * Este metodo transacciona la busqueda de del porcentaje maximo del gasto
     *
     * @param codigo String
     * @return String
     */
    private BigDecimal obtenerPorcentajeMaximoPorGasto(String codigo) {
        BigDecimal valor = BigDecimal.ZERO;
        for (TipoGastoPersonal tgp : gastoPersonalHelper.getListaTipoGastoPersonal()) {
            if (tgp.getCodigo().equals(codigo)) {
                valor = tgp.getPorcentaje();
            }
        }
        return valor;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo periodo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoProyeccion(final String codigo) {
        return TipoProyeccionGastoPersonalEnum.obtenerDescripcion(codigo);
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
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
    }
    /*
     * Regresa al listado general
     */

    public String irLista() {
        return LISTA_ENTIDAD;
    }

    /**
     *
     * @return
     */
    public BigDecimal buscarAportePersonalServidor() {
        BigDecimal porcentaje = BigDecimal.ZERO;
        try {
            if (gastoPersonalHelper.getDistributivoDetalle().getId() != null) {
                List<CotizacionIess> lista = admServicio.listarCotizacionPorIdNivelOcupacional(
                        gastoPersonalHelper.getDistributivoDetalle().getEscalaOcupacional().getNivelOcupacional().
                                getId());
                for (CotizacionIess c : lista) {
                    porcentaje = c.getPorcentajeAporteIndividual().add(c.getPorcentajeAdicionalAporteIndividual());
                    break;
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return porcentaje;
    }

    /**
     * 
     * @return 
     */
    private BigDecimal buscarPorcentajeAportePersonalMinimo() {
        BigDecimal porcentajeMinimo = BigDecimal.ZERO;
        try {
            List<CotizacionIess> lista = admServicio.listarTodosCotizacionIessPorNombre(null);
            for (CotizacionIess c : lista) {
                if (porcentajeMinimo.compareTo(c.getPorcentajeAporteIndividual()) == 0) {
                    porcentajeMinimo = c.getPorcentajeAporteIndividual();
                }
                if (c.getPorcentajeAporteIndividual().compareTo(porcentajeMinimo) <= 0) {
                    porcentajeMinimo = c.getPorcentajeAporteIndividual().add(c.getPorcentajeAdicionalAporteIndividual());
                }
            }
            System.out.println(" porcentaje minimo : " + porcentajeMinimo);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda", ex);
        }
        return porcentajeMinimo;
    }

    /**
     * Método imprimir.
     *
     * @return String
     * @throws DaoException
     */
    public void imprimirFormulario() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(gastoPersonalHelper.getGastoPersonalEditDelete());
            GastoPersonal d = (GastoPersonal) cloneBean;

            setReporte(ReportesEnum.PROTEUS_GASTOS_PERSONALES.getReporte());
            parametrosReporte = new HashMap<String, String>();
            parametrosReporte.put("p_titulo", "GASTOS ṔERSONALES");
            parametrosReporte.put("__format", "pdf");
            String pEjercicioFiscal = d.getEjercicioFiscal().getEjercicioFiscal().getNombre();
            Long pGpId = d.getId();
            if (pEjercicioFiscal != null) {
                parametrosReporte.put("p_ejerFiscal", pEjercicioFiscal);
            }
            if (pGpId != null) {
                parametrosReporte.put("p_idGp", pGpId.toString());
            }
            generarReporte();
        } catch (Exception e) {
            error(getClass().getName(), "error al generar reporte de Gastos Personales "
                    + "UATH" + e.getMessage(), e);
        }

        //return null;
    }

    /**
     * @return the gastoPersonalHelper
     */
    public GastoPersonalHelper getGastoPersonalHelper() {
        return gastoPersonalHelper;
    }

    /**
     * @param gastoPersonalHelper the gastoPersonalHelper to set
     */
    public void setGastoPersonalHelper(GastoPersonalHelper gastoPersonalHelper) {
        this.gastoPersonalHelper = gastoPersonalHelper;
    }

    @Override
    public void generarReporte() {
        try {
            generarUrlDeReporte();
        } catch (Exception e) {
            error(getClass().getName(), "No tiene accion de personal" + e.getMessage(), e);
        }
    }
}
