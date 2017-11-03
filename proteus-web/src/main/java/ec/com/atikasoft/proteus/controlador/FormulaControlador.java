/*
 *  FormulaControlador.java
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
 *  09/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.FormulaControlador.FORMULARIO_ENTIDAD;
import static ec.com.atikasoft.proteus.controlador.FormulaControlador.LISTA_ENTIDAD;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.FormulaHelper;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.FormulaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.DatosVariablesVO;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "formulaBean")
@ViewScoped
public class FormulaControlador extends CatalogoControlador {

    /**
     * Logger de formulas.
     */
    private Logger LOG = Logger.getLogger(FormulaControlador.class.getCanonicalName());

    /**
     * Formula de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/formula/formula.jsf";

    /**
     * Formula de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/formula/lista_formula.jsf";

    /**
     * Variable para el prefijo de la formula
     */
    private String prefijoFormula;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de formulas.
     */
    @EJB
    private FormulaServicio formulaServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{formulaHelper}")
    private FormulaHelper formulaHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(formulaHelper);
        setFormulaHelper(formulaHelper);
        iniciarComboConstantes();
        iniciarComboVariables();
        iniciarComboDatosAdicionales();
        iniciarComboFormulas();
        cargarUsosFormulas();
        setPrefijoFormula("F_");
        buscar();
        getCatalogoHelper().setCampoBusqueda("");
    }

    public FormulaControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            String codigoTemp = formulaHelper.getFormula().
                    getCodigo();
            if (formulaHelper.getEsNuevo()) {
                formulaHelper.getFormula().setCodigo(formulaHelper.getPrefijoCodigo() + formulaHelper.getFormula().
                        getCodigo());
                if (validarCodigo()) {
                    formulaHelper.getFormula().setCodigo(codigoTemp);
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);
                } else {

                    admServicio.guardarFormula(formulaHelper.getFormula());
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarFormula(formulaHelper.getFormula());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<Formula> formulas = admServicio.listarTodasFormulaPorNombre(null);
            getSession().getServletContext().setAttribute(CacheEnum.FORMULAS.getCodigo(), formulas);
            List<Rubro> rubros = admServicio.listarTodosRubrosVigentes();
            getSession().getServletContext().setAttribute(CacheEnum.RUBROS.getCodigo(), rubros);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    @Override
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(formulaHelper.getFormulaEditDelete());
            Formula re = (Formula) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            formulaHelper.setFormula(re);
            formulaHelper.setEsNuevo(Boolean.FALSE);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        formulaHelper.setFormula(new Formula());
        iniciarDatosEntidad(formulaHelper.getFormula(), Boolean.TRUE);
        formulaHelper.setEsNuevo(Boolean.TRUE);
        formulaHelper.setPrefijoCodigo("F_");
        iniciarComboConstantes();
        iniciarComboVariables();
        iniciarComboDatosAdicionales();
        iniciarComboFormulas();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarFormula(formulaHelper.getFormulaEditDelete());
            formulaHelper.getListaFormula().
                    remove(formulaHelper.getFormulaEditDelete());
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
            formulaHelper.getListaFormula().clear();
            formulaHelper.setListaFormula(
                    admServicio.listarTodasFormulaPorNombre(
                            getCatalogoHelper().getCampoBusqueda()));
        } catch (Exception ex) {
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
            formulaHelper.getListaFormulaCodigo().clear();
            formulaHelper.setListaFormulaCodigo(
                    admServicio.listarTodosFormulaPorCodigo(
                            formulaHelper.getFormula().getCodigo()));
            if (formulaHelper.getListaFormulaCodigo().isEmpty()) {
                haycodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del cddigo", ex);
        }
        return haycodigo;
    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Constantes
     */
    private void iniciarComboConstantes() {

        try {
            formulaHelper.getListaConstantes().clear();
            formulaHelper.setListaConstantes(
                    admServicio.listarTodosConstantesVigentes());
            iniciarCombos(formulaHelper.getOpcionesConstantes());

            for (Constante c : formulaHelper.getListaConstantes()) {
                formulaHelper.getOpcionesConstantes().add(new SelectItem(c.getCodigo(), c.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda constantes", ex);
        }

    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Variables
     */
    private void iniciarComboVariables() {

        try {
            formulaHelper.getListaVariables().clear();
            formulaHelper.setListaVariables(
                    admServicio.listarTodosVariablePVigentes());
            iniciarCombos(formulaHelper.getOpcionesVariables());

            for (Variable c : formulaHelper.getListaVariables()) {
                formulaHelper.getOpcionesVariables().add(new SelectItem(c.getCodigo(), c.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda variables", ex);
        }

    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Datos
     * Adicionales
     */
    private void iniciarComboDatosAdicionales() {

        try {
            formulaHelper.getListaDatosAdicionales().clear();
            formulaHelper.setListaDatosAdicionales(
                    admServicio.listarTodosDatosAdicionalesVigentes());
            iniciarCombos(formulaHelper.getOpcionesDatosAdicionales());

            for (DatoAdicional c : formulaHelper.getListaDatosAdicionales()) {
                formulaHelper.getOpcionesDatosAdicionales().add(new SelectItem(c.getCodigo(), c.getNombre()));
            }
        } catch (Exception ex) {
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
            formulaHelper.getListaOpcionesFormulas().clear();
            lista = admServicio.listarTodosFormulaVigentes();
            formulaHelper.setListaOpcionesFormulas(lista);

            iniciarCombos(formulaHelper.getOpcionesFomulas());
            for (Formula c : formulaHelper.getListaOpcionesFormulas()) {
                formulaHelper.getOpcionesFomulas().add(new SelectItem(c.getCodigo(), c.getNombre()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda formulas", ex);
        }

    }

    /**
     * Permite ir construyendo la formula con lo que el usuario seleccione
     */
    public void construirFormula(String texto) {

        if (formulaHelper.getFormula().getFormula() == null) {
            formulaHelper.getFormula().setFormula("");

        }
        formulaHelper.getFormula().setFormula(
                UtilCadena.concatenar(formulaHelper.getContenidoFormula(), " ", texto));
    }

    /**
     * Ajax al seleccionar una constante
     */
    public void seleccionConstante() {
        if (formulaHelper.getFormula().getFormula() == null) {
            formulaHelper.getFormula().setFormula("");

        }
        formulaHelper.setContenidoFormula(formulaHelper.getFormula().getFormula());
        construirFormula(formulaHelper.getNombreConstante());

        iniciarComboConstantes();
        formulaHelper.setNombreConstante("");
    }

    /**
     * Ajax al seleccionar una Variable
     */
    public void seleccionVariable() {
        if (formulaHelper.getFormula().getFormula() == null) {
            formulaHelper.getFormula().setFormula("");

        }
        formulaHelper.setNombreVariableP(formulaHelper.getNombreVariableP());
        formulaHelper.setContenidoFormula(formulaHelper.getFormula().getFormula());
        construirFormula(formulaHelper.getNombreVariableP());
        iniciarComboVariables();
        formulaHelper.setNombreVariableP("");
    }

    /**
     * Ajax al seleccionar un DatoAdicional
     */
    public void seleccionDatoAdicional() {
        if (formulaHelper.getFormula().getFormula() == null) {
            formulaHelper.getFormula().setFormula("");

        }
        formulaHelper.setNombreDatoAdicional(formulaHelper.getNombreDatoAdicional());
        formulaHelper.setContenidoFormula(formulaHelper.getFormula().getFormula());
        construirFormula(formulaHelper.getNombreDatoAdicional());
        iniciarComboDatosAdicionales();
        formulaHelper.setNombreDatoAdicional("");
    }

    /**
     * Ajax al seleccionar una Formula
     */
    public void seleccionFormula() {
        if (formulaHelper.getFormula().getFormula() == null) {
            formulaHelper.getFormula().setFormula("");

        }
        formulaHelper.setNombreFormula(formulaHelper.getNombreFormula());
        formulaHelper.setContenidoFormula(formulaHelper.getFormula().getFormula());
        construirFormula(formulaHelper.getNombreFormula());
        iniciarComboFormulas();
        formulaHelper.setNombreFormula("");
    }

    /**
     * Permite iniciar el proceso de validacion de la sintaxis de la formula.
     *
     * @return
     */
    public String validarFormula() {
        try {
            StringBuilder formula = new StringBuilder(formulaHelper.getFormula().getFormula());
            formulaServicio.validarFormula(formula, getSession().getServletContext());
            mostrarMensajeEnPantalla("La fórmula es válida", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla("La fórmula es incorrecta", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Permite simular la formula
     *
     * @return
     */
    public String simularFormula() {
        try {
            StringBuilder formula = new StringBuilder(formulaHelper.getFormula().getFormula());
            Map<String, Object> valores = formulaServicio.buscarCodigosFormula(formula, getSession().getServletContext());
            List<DatosVariablesVO> datos = formulaHelper.getListaDatosVariables();
            for (String key : valores.keySet()) {
                for (DatosVariablesVO dato : datos) {
                    if (dato.getCodigo().equals(key)) {
                        valores.put(key, dato.getValor());
                        break;
                    }
                }
            }
            formulaHelper.setResultadoSimulacion(formulaServicio.simularFormular(formula, valores, getSession().getServletContext()));
            mostrarMensajeEnPantalla("Simulación: La fórmula se calculo correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Simulación: La fórmula es incorrecta", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Permite cargar los datos variables.
     */
    public void cargarDatosVariables() {
        try {
            formulaHelper.getListaDatosVariables().clear();
            Map<String, Object> buscarCodigosFormula
                    = formulaServicio.buscarCodigosFormula(new StringBuilder(formulaHelper.getFormula().getFormula()),
                            getSession().getServletContext());
            Set<String> keys = buscarCodigosFormula.keySet();
            for (String key : keys) {

                Object valorEncontrado = buscarCodigosFormula.get(key);

                if (valorEncontrado instanceof java.lang.String) {
                    formulaHelper.getListaDatosVariables().add(
                            new DatosVariablesVO(key, new BigDecimal(valorEncontrado.toString())));
                } else {
                    formulaHelper.getListaDatosVariables().add(
                            new DatosVariablesVO(key, buscarCodigosFormula.get(key)));
                }

            }
            ejecutarComandoPrimefaces("simularDialog.show();");
        } catch (ServicioException ex) {
            Logger.getLogger(FormulaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Permite cargar el listado de usos de la formula
     */
    public void cargarUsosFormulas() {
        try {
            if (formulaHelper.getFormula() != null && formulaHelper.getFormula().getCodigo() != null) {
                formulaHelper.getListaUsosFormula().clear();
                formulaHelper.setListaUsosFormula(
                        admServicio.obtenerUsosEnFormula(formulaHelper.getFormula().getCodigo()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda usos formulas ", ex);
        }

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
    public void setAdmServicio(final AdministracionServicio admServicio) {
        this.admServicio = admServicio;
    }

    /**
     * @return the formulaHelper
     */
    public FormulaHelper getFormulaHelper() {
        return formulaHelper;
    }

    /**
     * @param formulaHelper the formulaHelper to set
     */
    public void setFormulaHelper(final FormulaHelper formulaHelper) {
        this.formulaHelper = formulaHelper;
    }

    /**
     * @return the prefijoFormula
     */
    public String getPrefijoFormula() {
        return prefijoFormula;
    }

    /**
     * @param prefijoFormula the prefijoFormula to set
     */
    public void setPrefijoFormula(final String prefijoFormula) {
        this.prefijoFormula = prefijoFormula;
    }
}
