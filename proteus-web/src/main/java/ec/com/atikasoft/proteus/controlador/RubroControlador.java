/*
 *  RubroControlador.java
 *
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disc   lose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  15/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.RubroHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.TipoBeneficiarioEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoRubroEnum;
import ec.com.atikasoft.proteus.enums.UsoRubroEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.lang.reflect.InvocationTargetException;
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
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "rubroBean")
@ViewScoped
public class RubroControlador extends CatalogoControlador {

    /**
     * Logger de rubros.
     */
    private Logger LOG = Logger.getLogger(RubroControlador.class.getCanonicalName());

    /**
     * Rubro de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/rubro/rubro.jsf";

    /**
     * Rubro de navegación para configuracion.
     */
    public static final String FORMULARIO_ENTIDAD_CONFIG = "/pages/administracion/rubro/config_rubro.jsf";

    /**
     * Rubro de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/rubro/lista_rubro.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de persona.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{rubroHelper}")
    private RubroHelper rubroHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(rubroHelper);
        setRubroHelper(rubroHelper);
        iniciarComboTipoRubro();
        iniciarComboTipoBeneficiario();
        iniciarComboPrioridad();
        iniciarComboClasificadorGastos();
        iniciarComboTipoDocumento();
        iniciarComboPartidas();
        iniciarComboCodigosContables();
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    public RubroControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            if (rubroHelper.getRubro().getBeneficiarioUnico() && !rubroHelper.getExisteBeneficiario()) {
                mostrarMensajeEnPantalla("El nombre del beneficiario es un campo requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (rubroHelper.getEsNuevo()) {
                if (validarCodigo()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {
                    admServicio.guardarRubro(rubroHelper.getRubro());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                if (rubroHelper.isEditaRubroPeriodoNomina()) {
                    guardarRubroPeriodoNomina();
                    rubroHelper.setEditaRubroPeriodoNomina(false);
                }
                if (rubroHelper.isEditaRubroTipoNomina()) {
                    guardarRubroTipoNomina();
                    rubroHelper.setEditaRubroTipoNomina(false);
                }
                admServicio.actualizarRubro(rubroHelper.getRubro());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<Rubro> rubros = admServicio.listarTodosRubrosVigentes();
            getSession().getServletContext().setAttribute(CacheEnum.RUBROS.getCodigo(), rubros);

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(rubroHelper.getRubroEditDelete());
            Rubro re = (Rubro) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            rubroHelper.setRubro(re);
            rubroHelper.setEsNuevo(Boolean.FALSE);
            rubroHelper.setExisteBeneficiario(Boolean.FALSE);
            if (re.getBeneficiarioUnico() != null && re.getBeneficiarioUnico() && re.getNombreBeneficiario() != null && !re.getNombreBeneficiario().isEmpty()) {
                rubroHelper.setExisteBeneficiario(Boolean.TRUE);
            }

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

    public String iniciarConfiguracion() {
        try {
            Object cloneBean = BeanUtils.cloneBean(rubroHelper.getRubroEditDelete());
            Rubro re = (Rubro) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            rubroHelper.setRubro(re);
            rubroHelper.setEsNuevo(Boolean.FALSE);
            iniciarComboDatosAdicionales();
            iniciarComboFormulas();
            iniciarComboUsoRubro();
            llenarPeriodoNomina();
            llenarTipoNomina();
            if (re.getBeneficiarioUnico() != null && re.getBeneficiarioUnico() && re.getNombreBeneficiario() != null && !re.getNombreBeneficiario().isEmpty()) {
                rubroHelper.setExisteBeneficiario(Boolean.TRUE);
            }
            rubroHelper.setEditaRubroTipoNomina(false);
            rubroHelper.setEditaRubroPeriodoNomina(false);
        } catch (IllegalAccessException ex) {
            error(getClass().getName(), "Error al iniciar la configuracion", ex);
        } catch (InstantiationException ex) {
            error(getClass().getName(), "Error al iniciar la configuracion", ex);
        } catch (NoSuchMethodException ex) {
            error(getClass().getName(), "Error al iniciar la configuracion", ex);
        } catch (InvocationTargetException ex) {
            error(getClass().getName(), "Error al iniciar la configuracion", ex);
        }
        return FORMULARIO_ENTIDAD_CONFIG;
    }

    @Override
    public String iniciarNuevo() {
        rubroHelper.setRubro(new Rubro());
        iniciarDatosEntidad(rubroHelper.getRubro(), Boolean.TRUE);
        rubroHelper.setEsNuevo(Boolean.TRUE);
        iniciarComboTipoRubro();
        iniciarComboTipoBeneficiario();
        iniciarComboPrioridad();
        iniciarComboClasificadorGastos();
        rubroHelper.setExisteBeneficiario(Boolean.FALSE);
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarRubro(rubroHelper.getRubroEditDelete());
            rubroHelper.getListaRubros().
                    remove(rubroHelper.getRubroEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la eliminacion", ex);
        }
        return null;
    }

    @Override
    public String buscar() {
        try {
            rubroHelper.getListaRubros().clear();
            rubroHelper.setListaRubros(
                    admServicio.listarTodosRubrosPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * metodo para validar si ya existe el codigo.
     *
     * @return haycodigo Boolean.
     */
    public Boolean validarCodigo() {
        Boolean haycodigo = true;
        try {
            rubroHelper.getListaRubroCodigo().clear();
            rubroHelper.setListaRubroCodigo(
                    admServicio.listarTodosRubroPorCodigo(
                            rubroHelper.getRubro().getCodigo()));
            if (rubroHelper.getListaRubroCodigo().isEmpty()) {
                haycodigo = false;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del cddigo", ex);
        }
        return haycodigo;
    }

    /**
     * Este metodo llena las opciones para el combo de prioridades
     */
    private void iniciarComboPrioridad() {
        rubroHelper.getOpcionPrioridad().clear();
        iniciarCombos(rubroHelper.getOpcionPrioridad());
        for (int i = 1; i <= 10; i++) {
            rubroHelper.getOpcionPrioridad().add(new SelectItem(i, String.valueOf(i)));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo rubro.
     */
    private void iniciarComboTipoRubro() {
        rubroHelper.getTipo().clear();
        iniciarCombos(rubroHelper.getTipo());
        for (TipoRubroEnum t : TipoRubroEnum.values()) {
            rubroHelper.getTipo().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de tipo beneficiario.
     */
    private void iniciarComboTipoBeneficiario() {
        rubroHelper.getTipoBeneficiario().clear();
        iniciarCombos(rubroHelper.getTipoBeneficiario());
        for (TipoBeneficiarioEnum t : TipoBeneficiarioEnum.values()) {
            rubroHelper.getTipoBeneficiario().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de usos del rubro.
     */
    private void iniciarComboUsoRubro() {
        rubroHelper.getUsoRubro().clear();
        iniciarCombos(rubroHelper.getUsoRubro());
        for (UsoRubroEnum t : UsoRubroEnum.values()) {
            rubroHelper.getUsoRubro().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Datos
     * Adicionales
     */
    private void iniciarComboDatosAdicionales() {

        try {
            rubroHelper.getListaDatosAdicionales().clear();
            rubroHelper.setListaDatosAdicionales(
                    admServicio.listarTodosDatosAdicionalesVigentes());
            iniciarCombos(rubroHelper.getOpcionDatoAdicional());

            for (DatoAdicional c : rubroHelper.getListaDatosAdicionales()) {
                rubroHelper.getOpcionDatoAdicional().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos adicionales", ex);
        }

    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Formulas
     */
    private void iniciarComboFormulas() {
        List<Formula> lista;
        try {
            rubroHelper.getListaFormulas().clear();
            lista = admServicio.listarTodosFormulaVigentes();
            rubroHelper.setListaFormulas(lista);
            iniciarCombos(rubroHelper.getOpcionFormula());
            for (Formula c : rubroHelper.getListaFormulas()) {
                rubroHelper.getOpcionFormula().add(new SelectItem(c.getId(), c.getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda formulas", ex);
        }

    }

    /**
     *
     */
    private void iniciarComboPartidas() {
        try {
            rubroHelper.getOpcionPartida().clear();
            List<Partida> partidas = admServicio.buscarPartidasVigentes();
            iniciarCombos(rubroHelper.getOpcionPartida());
            for (Partida p : partidas) {
                rubroHelper.getOpcionPartida().add(new SelectItem(p.getId(), UtilCadena.concatenar(p.getNombre(), " / ",
                        p.getPartida())));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda partidas", ex);
        }

    }
    
    /**
     *
     */
    private void iniciarComboCodigosContables() {
        try {
            rubroHelper.getListaCodigoContable().clear();
            rubroHelper.setListaCodigoContable(admServicio.buscarCodigosContablesVigentes());
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda codigos contables", ex);
        }

    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo rubro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoRubro(final String codigo) {
        return TipoRubroEnum.obtenerDescripcion(codigo);
    }

    /**
     * Acciones segun el tipo de rubro seleccionado: == Ingreso de servidores
     * <I>- Mostrar lista de clasificador de gasto == Ingreso de anticipos <A>-
     * Nada == Descuentos<D> ó Recuperacion de anticipos <R> - Mostrar lista de
     * codigos contables
     *
     * - Benificiario unico (true /false) - Si beneficiario unico = true -
     * mostrar identificacion y nombre beneficiario - Si beneficiario unico =
     * false- mostrar lista de tipo de beneficiario
     *
     * == Aporte institucional
     * <P>
     * - Mostrar lista de codigos contables y lista de clasificador de gasto
     * Mostrar identificacion y nombre beneficiario (() automaticamente true en
     * beneficiario_unico)
     */
    public void OnTipoRubroSeleccionado() {
        char opcion = 'z';
        if (rubroHelper.getRubro().getTipo() != null) {
            opcion = rubroHelper.getRubro().getTipo().charAt(0);
        }
        rubroHelper.getRubro().setCodigoContable(null);
        rubroHelper.getRubro().setIdCodigoContable(null);
        switch (opcion) {
            case 'I':

                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.FALSE);
                rubroHelper.getRubro().setTipoBeneficiario(null);
                rubroHelper.getRubro().setIdentificacionBeneficiario(null);
                rubroHelper.getRubro().setNombreBeneficiario(null);
                break;
            case 'A':
                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.FALSE);
                rubroHelper.getRubro().setTipoBeneficiario(null);
                rubroHelper.getRubro().setIdentificacionBeneficiario(null);
                rubroHelper.getRubro().setNombreBeneficiario(null);

                break;
            case 'D':
                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.FALSE);

                break;
            case 'R':
                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.FALSE);

                break;
            case 'P':
                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.TRUE);
                rubroHelper.getRubro().setTipoBeneficiario(null);
                rubroHelper.getRubro().setIdentificacionBeneficiario(null);
                rubroHelper.getRubro().setNombreBeneficiario(null);

                break;
            default:
                rubroHelper.getRubro().setBeneficiarioUnico(Boolean.FALSE);
                break;
        }
    }

    /**
     * LLena los tipos de identificacion de los beneficiarios.
     */
    public void iniciarComboTipoDocumento() {
        rubroHelper.getTipoDocumento().clear();
        iniciarCombos(rubroHelper.getTipoDocumento());
        for (TipoDocumentoEnum i : TipoDocumentoEnum.values()) {
            rubroHelper.getTipoDocumento().add(new SelectItem(i.getNemonico(),
                    i.getNombre()));
        }

    }

    /**
     * Permite buscar al beneficiario único.
     */
    public void buscarBeneficiarioUnico() {
        rubroHelper.setExisteBeneficiario(Boolean.FALSE);
        if (rubroHelper.getRubro().getTipoIdentificacionBeneficiario() != null && rubroHelper.getRubro().
                getIdentificacionBeneficiario() != null
                && !rubroHelper.getRubro().getTipoIdentificacionBeneficiario().isEmpty() && !rubroHelper.getRubro().
                getIdentificacionBeneficiario().isEmpty()) {
            try {
                rubroHelper.setPersonaVO(
                        servidorServicio.buscarPersona(rubroHelper.getRubro().getTipoIdentificacionBeneficiario(),
                                rubroHelper.getRubro().getIdentificacionBeneficiario(), obtenerUsuarioConectado()));
                if (rubroHelper.getPersonaVO() != null && rubroHelper.getPersonaVO().getNumeroIdentificacion() != null) {
                    rubroHelper.getRubro().setNombreBeneficiario(rubroHelper.getPersonaVO().getNombres());
                    rubroHelper.setExisteBeneficiario(Boolean.TRUE);
                } else {
                    mostrarMensajeEnPantalla(BUSQUEDA_SIN_RESULTADOS, FacesMessage.SEVERITY_ERROR);
                    rubroHelper.getRubro().setNombreBeneficiario("");
                    rubroHelper.setExisteBeneficiario(Boolean.FALSE);
                }
            } catch (ServicioException ex) {
                mostrarMensajeEnPantalla("Problemas al buscar beneficiario único", FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Problemas al buscar beneficiario único ", ex);
            }
        } else {
            mostrarMensajeEnPantalla("Los campos Tipo y Número de Identificación del Beneficiario son requeridos ",
                    FacesMessage.SEVERITY_ERROR);
        }
    }

    /**
     * Acciones segun el uso de rubro seleccionado: Dato Adicional D: Mostrar
     * listado de datos adicionales Formula F: Mostrar listado de Fórmulas
     */
    public void OnUsoRubroSeleccionado() {
        char opcion = 'N';
        if (rubroHelper.getRubro().getUso() != null) {
            opcion = rubroHelper.getRubro().getUso().charAt(0);
        }
        switch (opcion) {
            case 'D':
                rubroHelper.getRubro().setIdFormula(null);
                rubroHelper.getRubro().setFormula(null);
                break;
            case 'F':
                rubroHelper.getRubro().setIdDatoAdicional(null);
                rubroHelper.getRubro().setDatoAdicional(null);
                break;
            default:
                rubroHelper.getRubro().setIdDatoAdicional(null);
                rubroHelper.getRubro().setIdFormula(null);
                rubroHelper.getRubro().setFormula(null);
                rubroHelper.getRubro().setDatoAdicional(null);
                break;
        }
    }

    /**
     * Acciones si el check de Beneficiario unico es seleccionado o no
     */
    public void OnBeneficiarioUnicoSeleccionado() {

        if (!rubroHelper.getRubro().getBeneficiarioUnico()) {
            rubroHelper.getRubro().setIdentificacionBeneficiario(null);
            rubroHelper.getRubro().setNombreBeneficiario(null);
        } else {
            rubroHelper.getRubro().setTipoBeneficiario(null);
        }

    }

    /**
     * Se encarga de poblar la lista de PeriodoNomina vigentes
     */
    public void llenarPeriodoNomina() {
        try {
            if (rubroHelper.getRubro() != null && rubroHelper.getRubro().getId() != null) {
                rubroHelper.getListaPeriodoNomina().clear();
                rubroHelper.setListaPeriodoNomina(
                        admServicio.listarPeriodoNominaDeRubro(rubroHelper.getRubro().getId()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda periodo nomina", ex);
        }
    }

    /**
     * Se encarga de poblar la lista de TipoNomina vigentes
     */
    public void llenarTipoNomina() {
        try {
            if (rubroHelper.getRubro() != null && rubroHelper.getRubro().getId() != null) {
                rubroHelper.getListaTipoNomina().clear();
                rubroHelper.setListaTipoNomina(
                        admServicio.listarTipoNominaDeRubro(rubroHelper.getRubro().getId()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda tipo nomina", ex);
        }
    }

    /**
     * Se encarga de poblar la lista de ClasificadorGastos vigentes
     */
    public void iniciarComboClasificadorGastos() {
        try {
            rubroHelper.getListaClasificadorGasto().clear();
            rubroHelper.setListaClasificadorGasto(
                    admServicio.listarTodosClasificadorGastoVigentes());

            iniciarCombos(rubroHelper.getOpcionClasificadorGasto());
            for (ClasificadorGasto c : rubroHelper.getListaClasificadorGasto()) {
                rubroHelper.getOpcionClasificadorGasto().add(new SelectItem(c.getId(), c.getNombre()));
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda clasificador de gastos", ex);
        }
    }

    /**
     * Permite asignar al rubro los tipos de nomina 1.- Eliminar lógicamente los
     * registros rubroTipoNomina 2.- Insertar los nuevos registros seleccionados
     */
    private void guardarRubroTipoNomina() {
        try {
            System.out.println("RUBRO**************:" + rubroHelper.getRubro().getNombre());
            System.out.println("tipo nomina tamnaño**************:" + rubroHelper.getRubro().getListaRubrosTipoNomina().
                    size());
            if (rubroHelper.getRubro() != null && !rubroHelper.getRubro().getListaRubrosTipoNomina().isEmpty()) {
                System.out.println("tipo nomina tamnaño.............:" + rubroHelper.getRubro().getListaRubrosTipoNomina().
                        size());
                for (RubroTipoNomina reg : rubroHelper.getRubro().getListaRubrosTipoNomina()) {
                    System.out.println("Sys eliminado RubroTipoNomina...." + reg.getTipoNomina().getNombre());
                    iniciarDatosEntidad(reg, Boolean.FALSE);
                    if (reg.getVigente()) {
                        admServicio.eliminarRubroTipoNomina(reg);
                    }
                }
            }
            rubroHelper.getRubro().getListaRubrosTipoNomina().clear();
            for (TipoNomina n : rubroHelper.getListaTipoNomina()) {
                if (n.getSeleccionado()) {
                    RubroTipoNomina rubroTipoNomina = new RubroTipoNomina();
                    rubroTipoNomina.setRubro(
                            rubroHelper.getRubro());
                    rubroTipoNomina.setIdRubro(
                            rubroHelper.getRubro().getId());
                    rubroTipoNomina.setTipoNomina(n);
                    rubroTipoNomina.setIdTipoNomina(
                            n.getId());
                    iniciarDatosEntidad(rubroTipoNomina, Boolean.TRUE);
                    admServicio.guardarRubroTipoNomina(rubroTipoNomina);
                    rubroHelper.getRubro().getListaRubrosTipoNomina().add(rubroTipoNomina);
                }
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar rubro tipo nomina ", e);
        }

    }

    /**
     * Permite asignar al rubro los periodos de nomina 1.- Eliminar lógicamente
     * los registros rubroPeriodoNomina 2.- Insertar los nuevos registros
     * seleccionados
     */
    private void guardarRubroPeriodoNomina() {
        try {
            RubroPeriodoNomina rubroPeriodoNomina;
            System.out.println("tipo nomina tamnaño:" + rubroHelper.getRubro().getListaRubrosPeriodoNomina().size());
            if (rubroHelper.getRubro() != null && !rubroHelper.getRubro().getListaRubrosPeriodoNomina().isEmpty()) {
                for (RubroPeriodoNomina reg : rubroHelper.getRubro().getListaRubrosPeriodoNomina()) {
                    System.out.println("Sys eliminado periodo...." + reg.getPeriodoNomina().getNombre());
                    iniciarDatosEntidad(reg, Boolean.FALSE);
                    if (reg.getVigente()) {
                        admServicio.eliminarRubroPeriodoNomina(reg);
                    }
                }
            }
            rubroHelper.getRubro().getListaRubrosPeriodoNomina().clear();
            for (PeriodoNomina n : rubroHelper.getListaPeriodoNomina()) {
                if (n.getSeleccionado()) {
                    rubroPeriodoNomina = new RubroPeriodoNomina();
                    rubroPeriodoNomina.setRubro(
                            rubroHelper.getRubro());
                    rubroPeriodoNomina.setIdRubro(
                            rubroHelper.getRubro().getId());
                    rubroPeriodoNomina.setPeriodoNomina(n);
                    rubroPeriodoNomina.setIdPeriodoNomina(
                            n.getId());
                    iniciarDatosEntidad(rubroPeriodoNomina, Boolean.TRUE);
                    admServicio.guardarRubroPeriodoNomina(rubroPeriodoNomina);
                    rubroHelper.getRubro().getListaRubrosPeriodoNomina().add(rubroPeriodoNomina);
                }
            }
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar los periodos de nomina", e);
        }

    }

    /**
     * Acciones cuando la lista de Tipos de Nóminas es accionada
     *
     * @param tn
     */
    public void onRubroTipoNominaSeleccionado() {
        rubroHelper.setEditaRubroTipoNomina(true);
    }

    /**
     * Acciones cuando la lista de Periodos de Nóminas es accionada
     *
     * @param tn
     */
    public void onRubroPeriodoNominaSeleccionado() {
        rubroHelper.setEditaRubroPeriodoNomina(true);
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

    /**
     * @return the rubroHelper
     */
    public RubroHelper getRubroHelper() {
        return rubroHelper;
    }

    /**
     * @param rubroHelper the rubroHelper to set
     */
    public void setRubroHelper(RubroHelper rubroHelper) {
        this.rubroHelper = rubroHelper;
    }
}
