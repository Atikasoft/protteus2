/*
 *  VariableControlador.java
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
 *  02/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.VariableCondicionHelper;
import ec.com.atikasoft.proteus.controlador.helper.VariableHelper;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.FormulaServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.Collections;
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
@ManagedBean(name = "variableBean")
@ViewScoped
public class VariableControlador extends CatalogoControlador {

    /**
     * Logger de variables.
     */
    private Logger LOG = Logger.getLogger(VariableControlador.class.getCanonicalName());

    /**
     * Variable de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/variable/variable.jsf";

    /**
     * Variable de navegación.
     */
    public static final String LISTA_ENTIDAD = "/pages/administracion/variable/lista_variable.jsf";

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
     * Helper de Variable.
     */
    @ManagedProperty("#{variableHelper}")
    private VariableHelper variableHelper;

    /**
     * Helper de VariableCondicion.
     */
    @ManagedProperty("#{variableCondicionHelper}")
    private VariableCondicionHelper variableCondicionHelper;

    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(variableHelper);
        setVariableHelper(variableHelper);
        iniciarOpcionesDatosAdicionales();
        iniciarOpcionesCamposAcceso();
        iniciarOpcionesOperaciones();
        iniciarOpcionesOrigen();
        iniciarOpcionesPreconstruidos();
        getCatalogoHelper().setCampoBusqueda("");
        buscar();
        variableHelper.getListaVariableCondiciones().clear();
        llenarListaVariableCondicion(false);
        cargarUsosFormulas();

    }

    public VariableControlador() {
        super();
    }

    @Override
    public String guardar() {
        try {
            String codigoTemp = variableHelper.getVariableP().
                    getCodigo();
            if (variableHelper.getEsNuevo()) {
                variableHelper.getVariableP().setCodigo(variableHelper.getPrefijo() + codigoTemp);
                if (validarCodigo()) {
                    variableHelper.getVariableP().setCodigo(codigoTemp);
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE,
                            FacesMessage.SEVERITY_WARN);

                } else {

                    admServicio.guardarVariableP(variableHelper.getVariableP());
                    guardarCondiciones();
                    if (variableHelper.getVariableP().getCampoAccesoId() != null) {
                        guardarCondiciones();
                    }
                    iniciarNuevo();
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                }
            } else {
                admServicio.actualizarVariableP(variableHelper.getVariableP());
                guardarCondiciones();
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
            List<Variable> variables = admServicio.listarTodasVariablePPorNombre(null);
            getSession().getServletContext().setAttribute(CacheEnum.VARIABLES.getCodigo(), variables);
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
            Object cloneBean =
                    BeanUtils.cloneBean(variableHelper.getVariablePEditDelete());
            Variable re = (Variable) cloneBean;
            iniciarDatosEntidad(re, Boolean.FALSE);
            variableHelper.setVariableP(re);
            variableHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String iniciarNuevo() {
        variableHelper.setVariableP(new Variable());
        iniciarDatosEntidad(variableHelper.getVariableP(), Boolean.TRUE);
        variableHelper.setEsNuevo(Boolean.TRUE);
        iniciarOpcionesDatosAdicionales();
        iniciarOpcionesCamposAcceso();
        iniciarOpcionesOperaciones();
        iniciarOpcionesOrigen();
        iniciarOpcionesPreconstruidos();
        return FORMULARIO_ENTIDAD;
    }

    @Override
    public String borrar() {
        try {
            admServicio.eliminarVariableP(variableHelper.getVariablePEditDelete());
            variableHelper.getListaVariableP().
                    remove(variableHelper.getVariablePEditDelete());
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
            variableHelper.getListaVariableP().clear();
            variableHelper.setListaVariableP(
                    admServicio.listarTodasVariablePPorNombre(
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
     * @return haycodigo Boolean, true si el registro existe.
     */
    public Boolean validarCodigo() {
        Boolean haycodigo = true;
        try {
            variableHelper.getListaVariablePCodigo().clear();
            variableHelper.setListaVariablePCodigo(
                    admServicio.listarTodosVariablePPorCodigo(
                    variableHelper.getVariableP().getCodigo()));
            if (variableHelper.getListaVariablePCodigo().isEmpty()) {
                haycodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la validacion del codigo", ex);
        }
        return haycodigo;
    }

    /**
     * Permite definir la lista a cargarse y mostrarse de acuerdo al origen seleccionado
     */
    public void OnOrigenDatosSeleccionado() {
        char opcion = 'A';
        if (variableHelper.getVariableP().getOrigen() != null) {
            opcion = variableHelper.getVariableP().getOrigen().charAt(0);
        }
        switch (opcion) {
            case 'D':
                variableHelper.getVariableP().setCampoAccesoId(null);
                variableHelper.getVariableP().setCampoAcceso(null);
                variableHelper.getVariableP().setCodigoPreconstruido(null);
                break;
            case 'C':
                variableHelper.getVariableP().setDatoAdicional(null);
                variableHelper.getVariableP().setDatoAdicionalId(null);
                variableHelper.getVariableP().setCodigoPreconstruido(null);
                break;
            case 'P':
                variableHelper.getVariableP().setCampoAccesoId(null);
                variableHelper.getVariableP().setCampoAcceso(null);
                variableHelper.getVariableP().setDatoAdicional(null);
                variableHelper.getVariableP().setDatoAdicionalId(null);
                break;
            default:
                variableHelper.getVariableP().setCampoAccesoId(null);
                variableHelper.getVariableP().setCampoAcceso(null);
                variableHelper.getVariableP().setDatoAdicional(null);
                variableHelper.getVariableP().setDatoAdicionalId(null);
                variableHelper.getVariableP().setCodigoPreconstruido(null);
                break;
        }
    }

    /**
     * Este metodo llena las opciones para el combo de Operaciones Matemáticas
     */
    private void iniciarOpcionesOperaciones() {
        iniciarCombos(variableHelper.getOperacionMatematica());
        for (OperacionEnum t : OperacionEnum.values()) {
            variableHelper.getOperacionMatematica().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Permite llenar las opciones para los operadores lógicos
     */
    private void iniciarOpcionesOperadoresLogicos() {
        variableHelper.getOpcionesOperadoresLogicos().clear();
        for (OperacionLogicaEnum t : OperacionLogicaEnum.values()) {
            variableHelper.getOpcionesOperadoresLogicos().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Permite llenar las opciones para el radio buton del origen de la informacion
     */
    private void iniciarOpcionesOrigen() {
        variableHelper.getOrigenDatos().clear();
        for (OrigenVariableEnum t : OrigenVariableEnum.values()) {
            variableHelper.getOrigenDatos().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Permite llenar las opciones para el radio buton del origen de la informacion
     */
    private void iniciarOpcionesPreconstruidos() {
        variableHelper.getPreconstruidos().clear();
        iniciarCombos(variableHelper.getPreconstruidos());
        for (VariablesPreconstruidasEnum t : VariablesPreconstruidasEnum.values()) {
            variableHelper.getPreconstruidos().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Permite llenar las opciones de los operadores de comparacion
     */
    private void iniciarOpcionesOperadoresComparacion() {

        variableCondicionHelper.getOperadorComparacion().clear();
        iniciarCombos(variableCondicionHelper.getOperadorComparacion());
        for (OperacionComparacionEnum t : OperacionComparacionEnum.values()) {
            variableCondicionHelper.getOperadorComparacion().add(new SelectItem(t.getCodigo(), t.getDescripcion()));
        }
    }

    /**
     * Permite llenar la lista de datos adicionales vigentes
     */
    public void llenarListaDatosAdicionales() {
        try {
            variableHelper.getListaDatosAdicionales().clear();
            variableHelper.setListaDatosAdicionales(
                    admServicio.listarTodosDatosAdicionalesVigentes());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos adicionales", ex);
        }
    }

    /**
     * Permite llenar la lista de campos de acceso vigentes
     */
    public void llenarListaCampoAcceso() {
        try {
            variableHelper.getListaCamposAcceso().clear();
            variableHelper.setListaCamposAcceso(
                    admServicio.listarCampoAccesoVigentes());
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda campo accceso", ex);
        }
    }

    /**
     * Este metodo llena las opciones para el combo de datos adicionales .
     */
    public void iniciarOpcionesDatosAdicionales() {
        variableHelper.getOpcionesDatosAdicionales().clear();
        llenarListaDatosAdicionales();
        iniciarCombos(variableHelper.getOpcionesDatosAdicionales());
        for (DatoAdicional tp : variableHelper.getListaDatosAdicionales()) {
            variableHelper.getOpcionesDatosAdicionales().add(new SelectItem(tp.getId(), tp.getNombre()));
        }
    }

    /**
     * Este metodo llena las opciones para el combo de campos acceso
     */
    public void iniciarOpcionesCamposAcceso() {
        variableHelper.getOpcionesCamposAcceso().clear();
        llenarListaCampoAcceso();
        iniciarCombos(variableHelper.getOpcionesCamposAcceso());
        for (CampoAcceso tp : variableHelper.getListaCamposAcceso()) {

            variableHelper.getOpcionesCamposAcceso().add(new SelectItem(tp.getId(),
                    TipoCampoAccesoEnum.obtenerDescripcion(tp.getMetadataColumna().getMetadataTabla().getNombre()) + " - " + tp.getNombre()));

        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del Origen de la variable
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionOrigenVarible(final String codigo) {
        return OrigenVariableEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion de la operación de la variable
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionOperacion(final String codigo) {
        return OperacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion de los datos preconstruidos
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionPreconstruidos(final String codigo) {
        return VariablesPreconstruidasEnum.getDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion de la operación logica
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionOperacionLogica(final String codigo) {
        return OperacionLogicaEnum.obtenerDescripcion(codigo);
    }

    /**
     * Permite obtener el objeto campo de acceso al agregar una lista
     *
     * @param esActualizacion indica si el registro esta siendo actualizado si es true vuelve a ejecutar la busqueda
     */
    public void obtenerObjetoCampoAcceso(boolean esActualizacion) {
        try {
            if (variableCondicionHelper.getVariableCondicion().getCampoAccesoId() != null
                    && (variableCondicionHelper.getVariableCondicion().getCampoAcceso() == null || esActualizacion)) {
                variableCondicionHelper.getVariableCondicion().setCampoAcceso(
                        obtenerObjetoCampoAcceso(variableCondicionHelper.getVariableCondicion().getCampoAccesoId()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda campo accceso", ex);
        }
    }

    /**
     * Permite obtener el objeto campo de acceso al agregar una lista
     *
     * @param idCampoAcceso id del campo de acceso a buscar
     */
    public CampoAcceso obtenerObjetoCampoAcceso(Long idCampoAcceso) {
        List<CampoAcceso> lista = null;

        try {
            if (idCampoAcceso != null) {
                lista = admServicio.listarCampoAccesoPorId(idCampoAcceso);
            }
            if (lista != null && lista.size() > 0) {
                return lista.get(0);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda campo accceso", ex);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de campos acceso de un tipo especifico
     */
    public void iniciarOpcionesCamposAccesoPorTipo() {
        try {
            if (variableHelper.getVariableP().getCampoAcceso().getTipo() != null) {
                variableHelper.getListaCamposAccesoPorTipo().clear();
                variableHelper.setListaCamposAccesoPorTipo(
                        admServicio.listarCampoAccesoVigentesPorTipo(variableHelper.getVariableP().getCampoAcceso().
                        getTipo()));
                iniciarCombos(variableHelper.getOpcionesCamposAccesoPorTipo());
                for (CampoAcceso tp : variableHelper.getListaCamposAccesoPorTipo()) {
                    if (!tp.getId().equals(variableHelper.getVariableP().getCampoAccesoId())) {
                        variableHelper.getOpcionesCamposAccesoPorTipo().add(new SelectItem(tp.getId(),
                                tp.getNombre() + " - " + TipoDatoEnum.obtenerDescripcion(
                                tp.getMetadataColumna().getTipo())));
                    }
                }
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda campo accceso", ex);
        }

    }

    /**
     * Recrea el numero de parentesis, administrados como variables transient
     *
     * @param vc registro a recreados los parentesis
     * @return registro con parentesis recreados
     */
    private VariableCondicion iniciarParentesis(VariableCondicion vc) {
        vc.setParentesisDer("");
        vc.setParentesisIzq("");
        for (int i = 0; i < vc.getCantidadParentesisIzq(); i++) {
            vc.setParentesisIzq(vc.getParentesisIzq() + "(");
        }
        for (int i = 0; i < vc.getCantidadParentesisDer(); i++) {
            vc.setParentesisDer(vc.getParentesisDer() + ")");
        }
        return vc;
    }

    /**
     * Permite iniciar los controles para agregar una nueva condicion
     */
    public String iniciarNuevaCondicion() {
        iniciarOpcionesOperadoresComparacion();
        iniciarOpcionesCamposAccesoPorTipo();
        iniciarOpcionesOperadoresLogicos();
        variableCondicionHelper.setEsNuevaCondicion(Boolean.TRUE);
        variableCondicionHelper.setVariableCondicion(new VariableCondicion());
        variableCondicionHelper.getVariableCondicion().setCantidadParentesisIzq(0);
        variableCondicionHelper.getVariableCondicion().setCantidadParentesisDer(0);
        return null;
    }
    /*
     * Evento cuando se selecciona un campo de acceso de la lista de opciones Primero obtiene el objeto campo de acceso
     * seleccionado para la variable Luego ejecuta la carga del combo por tipo.
     */

    public void onChangeCampoAcceso() {
        variableHelper.getVariableP().setCampoAcceso(
                obtenerObjetoCampoAcceso(variableHelper.getVariableP().getCampoAccesoId()));
        iniciarOpcionesCamposAccesoPorTipo();
    }

    /**
     * Permite agregar una condición a la variable cuyo origen sea un Campo de Acceso
     */
    public String agregarCondicion() {
        if (variableCondicionHelper.getVariableCondicion().getOrdinal() != null) {
            if (!validaOrdinal()) {
                iniciarDatosEntidad(variableCondicionHelper.getVariableCondicion(), Boolean.TRUE);
                variableCondicionHelper.getVariableCondicion().setVariableP(variableHelper.getVariableP());
                variableCondicionHelper.getVariableCondicion().setVariablePId(variableHelper.getVariableP().getId());
                variableCondicionHelper.setVariableCondicion(iniciarParentesis(variableCondicionHelper.
                        getVariableCondicion()));
                obtenerObjetoCampoAcceso(false);
                variableCondicionHelper.getVariableCondicion().setCadenaCondicion(
                        cadenaCondicionRegistro(variableCondicionHelper.getVariableCondicion()));
                variableHelper.getListaVariableCondiciones().add(variableCondicionHelper.getVariableCondicion());
                ejecutarComandoPrimefaces("addDialog.hide()");
                iniciarNuevaCondicion();

            } else {
                mostrarMensajeEnPantalla("Registro existente en la lista de Condiciones.",
                        FacesMessage.SEVERITY_WARN);
            }
        }
        return null;
    }

    /**
     * Permite ordenar la lista de condiciones para poder validar
     */
    public void cadenaCondicionPorValidar() {

        String txt = "";

        Collections.sort(variableHelper.getListaVariableCondiciones());

        for (VariableCondicion vc : variableHelper.getListaVariableCondiciones()) {
            txt = txt + cadenaCondicionRegistro(vc);
        }
        variableCondicionHelper.setCadenaCondicion(txt);
        ejecutarComandoPrimefaces("validDialog.show();");
    }

    /**
     * Concatena campos de un registro de variableCOndicion
     *
     * @param vc registro
     * @return cadena
     */
    public String cadenaCondicionRegistro(VariableCondicion vc) {
        String txt = "";
        String esp = " ";
        if (vc != null & vc.getOrdinal() != null) {
            txt = UtilCadena.concatenar(txt, esp,
                    vc.getParentesisIzq(), esp,
                    vc.getCampoAcceso().getNombre(), esp,
                    vc.getTipoOperacionComparacion(), esp,
                    vc.getLiteral(), esp,
                    vc.getParentesisDer(), esp,
                    (vc.getOperadorLogico() == null ? "" : obtenerDescripcionOperacionLogica(vc.getOperadorLogico())),
                    esp);
        }
        return txt;
    }

    /**
     * Elimina un registro de la lista de condiciones
     */
    public void eliminarCondicion() {
        variableHelper.getListaVariableCondiciones().remove(variableCondicionHelper.getVariableCondicion());
    }

    /**
     * Permite asignar los valores de la variable condicion que se va a actualizar y cargar los valores de los
     * controladores
     *
     * @param vc variable condicion
     */
    public void iniciarEdicionCondicion(VariableCondicion vc) {

        iniciarOpcionesOperadoresComparacion();
        iniciarOpcionesCamposAccesoPorTipo();
        iniciarOpcionesOperadoresLogicos();
        variableCondicionHelper.setEsNuevaCondicion(Boolean.FALSE);
        variableCondicionHelper.setVariableCondicion(vc);
        variableCondicionHelper.setVariableCondicionEditDelete(vc);
    }

    /**
     * Actualiza en la lista de variablesCondicion
     *
     * @return null
     */
    public String actualizarCondicion() {
        boolean actualizable = true;
        if (variableCondicionHelper.getVariableCondicionEditDelete() != null
                && !variableCondicionHelper.getVariableCondicionEditDelete().getOrdinal().
                equals(variableCondicionHelper.getVariableCondicion().getOrdinal())) {
            actualizable = !validaOrdinal();
        }
        if (actualizable) {
            variableCondicionHelper.getListaVariableCondiciones().remove(variableCondicionHelper.
                    getVariableCondicionEditDelete());

            variableCondicionHelper.setVariableCondicion(iniciarParentesis(
                    variableCondicionHelper.getVariableCondicion()));
            obtenerObjetoCampoAcceso(true);
            variableCondicionHelper.getVariableCondicion().setCadenaCondicion(
                    cadenaCondicionRegistro(variableCondicionHelper.getVariableCondicion()));
            variableCondicionHelper.getListaVariableCondiciones().add(variableCondicionHelper.getVariableCondicion());
            ejecutarComandoPrimefaces("addDialog.hide()");
        } else {
            mostrarMensajeEnPantalla("Ordinal ya existe en la lista de Condiciones.",
                    FacesMessage.SEVERITY_WARN);
        }
        return null;
    }

    /**
     * Permite verificar si un ordinal ya se encuentra en la lista agregada
     *
     * @return true si el Ordinal ya esta en la lista
     */
    public boolean validaOrdinal() {
        boolean hayOrdinal = false;
        for (VariableCondicion vc : variableHelper.getListaVariableCondiciones()) {
            if (vc.getOrdinal().equals(variableCondicionHelper.getVariableCondicion().getOrdinal())) {
                hayOrdinal = true;
                break;
            }
        }
        return hayOrdinal;
    }

    /**
     * Permite guardar el listado de condiciones Primero inactiva todos los registros asociados y luego inserta aquellos
     * que están en la lista
     */
    private void guardarCondiciones() {
        try {
            if (!variableHelper.getListaVariableCondiciones().isEmpty()) {
                for (VariableCondicion vc : variableHelper.getVariableP().getVariablesCondiciones()) {
                    if (vc.getVigente()) {
                        admServicio.eliminarVariableCondicion(vc);
                    }
                }

                for (VariableCondicion vc : variableHelper.getListaVariableCondiciones()) {
                    admServicio.guardarVariableCondicion(vc);

                }
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar condiciones", e);
        }
    }

    /**
     * Permite llenar la lista de variables condicion de la variable
     *
     * @param esAgregando true si se esta en la operacion de agregado
     */
    private void llenarListaVariableCondicion(final boolean esAgregando) {
        if (variableHelper.getVariableP().getVariablesCondiciones() != null && (variableHelper.
                getListaVariableCondiciones().isEmpty() || !esAgregando)) {
            for (VariableCondicion v : variableHelper.getVariableP().getVariablesCondiciones()) {
                if (v.getVigente()) {
                    v = iniciarParentesis(v);
                    obtenerObjetoCampoAcceso(false);
                    v.setCadenaCondicion(cadenaCondicionRegistro(variableCondicionHelper.getVariableCondicion()));
                    variableHelper.getListaVariableCondiciones().add(v);
                }
            }
        }
    }

    /**
     * Pemrite cargar la lista de usos de la variable en las formulas
     */
    public void cargarUsosFormulas() {
        try {
            if (variableHelper.getVariableP() != null && variableHelper.getVariableP().getCodigo() != null) {
                variableHelper.getListaUsosFormula().clear();
                variableHelper.setListaUsosFormula(
                        admServicio.obtenerUsosEnFormula(variableHelper.getVariableP().getCodigo()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda usos formulas ", ex);
        }

    }

    /**
     * Permite iniciar el proceso de validacion de la sintaxis de la condicion
     */
    public String validarCondicion() {
        try {
            variableHelper.getVariableP().setValidado(Boolean.FALSE);
            if (variableCondicionHelper.getCadenaCondicion() == null || variableCondicionHelper.getCadenaCondicion().
                    isEmpty()) {
                mostrarMensajeEnPantalla("No hay condiciones para validar", FacesMessage.SEVERITY_INFO);
                return null;
            }
            StringBuilder formula = new StringBuilder(variableCondicionHelper.getCadenaCondicion());
            System.out.println("validar condiciones de la formula:" + formula);
            formulaServicio.validarFormula(formula, getSession().getServletContext());
            variableHelper.getVariableP().setValidado(Boolean.TRUE);
            mostrarMensajeEnPantalla("Las condiciones para la variable son válidas", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla("Las condiciones para la variable son inválidas", FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * Permite validar la sintaxis de la condición
     *
     * @return true si la condicion es valida
     */
    public boolean validarSintaxisCondicion() {
        boolean esValido = false;
        return esValido;
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
     * @return the variableHelper
     */
    public VariableHelper getVariableHelper() {
        return variableHelper;
    }

    /**
     * @param variableHelper the variableHelper to set
     */
    public void setVariableHelper(VariableHelper variableHelper) {
        this.variableHelper = variableHelper;
    }

    /**
     * @return the variableCondicionHelper
     */
    public VariableCondicionHelper getVariableCondicionHelper() {

        return variableCondicionHelper;
    }

    /**
     * @param variableCondicionHelper the variableCondicionHelper to set
     */
    public void setVariableCondicionHelper(VariableCondicionHelper variableCondicionHelper) {
        this.variableCondicionHelper = variableCondicionHelper;
    }
}
