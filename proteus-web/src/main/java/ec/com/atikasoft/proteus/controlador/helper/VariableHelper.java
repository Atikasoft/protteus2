/*
 *  VariableHelper.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *x|
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  07/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.CampoAcceso;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.VariableCondicion;
import ec.com.atikasoft.proteus.modelo.Variable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "variableHelper")
@SessionScoped
public class VariableHelper extends CatalogoHelper {

    /**
     * Variable para nueva variableP.
     */
    private Variable variableP;
    /**
     * Variable para modificar/eliminar una variableP.
     */
    private Variable variablePEditDelete;
    /**
     * Variable para listar las variablePs.
     */
    private List<Variable> listaVariableP;
    /**
     * Variable para listar las variablePs por codigo.
     */
    private List<Variable> listaVariablePCodigo;
    /**
     * Lista de Operaciones Matemáticas.
     */
    private List<SelectItem> operacionMatematica;

    /**
     * Lista de tipos de orgigen de los datos.
     */
    private List<SelectItem> origenDatos;
 
    /**
     * Lista Precontruida, de datos preconstruidos.
     */
    private List<SelectItem> preconstruidos;
    
        /**
     * Lista de operadores Logicos
     */
    private List<SelectItem> opcionesOperadoresLogicos;
    
     /**
     * Variable para listar las variablePs por codigo.
     */
    private List<DatoAdicional> listaDatosAdicionales;
     private List<SelectItem> opcionesDatosAdicionales;
    
     /**
     * Variable para listar las variablePs por codigo.
     */
    private List<CampoAcceso> listaCamposAcceso;
     private List<SelectItem> opcionesCamposAcceso;
     
       /**
     * Variable para listar las variablePs por codigo.
     */
    private List<CampoAcceso> listaCamposAccesoPorTipo;
    private List<SelectItem> opcionesCamposAccesoPorTipo;
     
    /**
     * Variable para el prefijo de la variable
     */
     private String prefijo;
     private final static String PREFIJO_VARIABLE="V_";
     
     
     /**
      * Variable para listar las condiciones de la variable
      */
     private List<VariableCondicion> listaVariableCondiciones;
     
     /**
      * Variable para los usos de esa variable en las formulas
      */
     
        /**
     * Variable para listar los usos de los codigos de variables en las formulas.
     */
    private List<Formula> listaUsosFormula;
    
    /**
     * Constructor.
     */
    public VariableHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la VariablePHelper.
     */
    public final void iniciador() {
        setVariableP(new Variable());
        setVariablePEditDelete(new Variable());
        setListaVariableP(new ArrayList<Variable>());
        setListaVariablePCodigo(new ArrayList<Variable>());
        setPrefijo(PREFIJO_VARIABLE);
        
        setOperacionMatematica(new ArrayList<SelectItem>());
        setOrigenDatos(new ArrayList<SelectItem>());
        setPreconstruidos(new ArrayList<SelectItem>());
        setOpcionesCamposAcceso(new ArrayList<SelectItem>());
        setOpcionesDatosAdicionales(new ArrayList<SelectItem>());
        setListaDatosAdicionales(new ArrayList<DatoAdicional>());
        setListaCamposAcceso(new ArrayList<CampoAcceso>());
        setListaVariableCondiciones(new ArrayList<VariableCondicion>());
        setOpcionesOperadoresLogicos(new ArrayList<SelectItem>());
        setOpcionesCamposAccesoPorTipo(new ArrayList<SelectItem>());
        setListaCamposAccesoPorTipo(new ArrayList<CampoAcceso>());
         setListaUsosFormula(new ArrayList<Formula>());
    }

    /**
     * @return the variableP
     */
    public Variable getVariableP() {
        return variableP;
    }

    /**
     * @param variableP the variableP to set
     */
    public void setVariableP(final Variable variableP) {
        this.variableP = variableP;
    }

    /**
     * @return the variablePEditDelete
     */
    public Variable getVariablePEditDelete() {
        return variablePEditDelete;
    }

    /**
     * @param variablePEditDelete the variablePEditDelete to set
     */
    public void setVariablePEditDelete(final Variable variablePEditDelete) {
        this.variablePEditDelete = variablePEditDelete;
    }

    /**
     * @return the listaVariableP
     */
    public List<Variable> getListaVariableP() {
        return listaVariableP;
    }

    /**
     * @param listaVariableP the listaVariableP to set
     */
    public void setListaVariableP(final List<Variable> listaVariableP) {
        this.listaVariableP = listaVariableP;
    }

    /**
     * @return the listaVariablePNemonico
     */
    public List<Variable> getListaVariablePCodigo() {
        return listaVariablePCodigo;
    }

    /**
     * @param listaVariablePCodigo the listaVariablePCodigo to set
     */
    public void setListaVariablePCodigo(List<Variable> listaVariablePCodigo) {
        this.listaVariablePCodigo = listaVariablePCodigo;
    }

    /**
     * @return the operacionMatematica
     */
    public List<SelectItem> getOperacionMatematica() {
        return operacionMatematica;
    }

    /**
     * @param operacionMatematica the operacionMatematica to set
     */
    public void setOperacionMatematica(List<SelectItem> operacionMatematica) {
        this.operacionMatematica = operacionMatematica;
    }

    /**
     * @return the origenDatos
     */
    public List<SelectItem> getOrigenDatos() {
        return origenDatos;
    }

    /**
     * @param origenDatos the origenDatos to set
     */
    public void setOrigenDatos(List<SelectItem> origenDatos) {
        this.origenDatos = origenDatos;
    }

    /**
     * @return the preconstruidos
     */
    public List<SelectItem> getPreconstruidos() {
        return preconstruidos;
    }

    /**
     * @param preconstruidos the preconstruidos to set
     */
    public void setPreconstruidos(List<SelectItem> preconstruidos) {
        this.preconstruidos = preconstruidos;
    }

    /**
     * @return the listaDatosAdicionales
     */
    public List<DatoAdicional> getListaDatosAdicionales() {
        return listaDatosAdicionales;
    }

    /**
     * @param listaDatosAdicionales the listaDatosAdicionales to set
     */
    public void setListaDatosAdicionales(List<DatoAdicional> listaDatosAdicionales) {
        this.listaDatosAdicionales = listaDatosAdicionales;
    }

    /**
     * @return the listaCamposAcceso
     */
    public List<CampoAcceso> getListaCamposAcceso() {
        return listaCamposAcceso;
    }

    /**
     * @param listaCamposAcceso the listaCamposAcceso to set
     */
    public void setListaCamposAcceso(List<CampoAcceso> listaCamposAcceso) {
        this.listaCamposAcceso = listaCamposAcceso;
    }

    /**
     * @return the opcionesDatosAdicionales
     */
    public List<SelectItem> getOpcionesDatosAdicionales() {
        return opcionesDatosAdicionales;
    }

    /**
     * @param opcionesDatosAdicionales the opcionesDatosAdicionales to set
     */
    public void setOpcionesDatosAdicionales(List<SelectItem> opcionesDatosAdicionales) {
        this.opcionesDatosAdicionales = opcionesDatosAdicionales;
    }

    /**
     * @return the opcionesCamposAcceso
     */
    public List<SelectItem> getOpcionesCamposAcceso() {
        return opcionesCamposAcceso;
    }

    /**
     * @param opcionesCamposAcceso the opcionesCamposAcceso to set
     */
    public void setOpcionesCamposAcceso(List<SelectItem> opcionesCamposAcceso) {
        this.opcionesCamposAcceso = opcionesCamposAcceso;
    }

    /**
     * @return the listaVariableCondiciones
     */
    public List<VariableCondicion> getListaVariableCondiciones() {
        return listaVariableCondiciones;
    }

    /**
     * @param listaVariableCondiciones the listaVariableCondiciones to set
     */
    public void setListaVariableCondiciones(List<VariableCondicion> listaVariableCondiciones) {
        this.listaVariableCondiciones = listaVariableCondiciones;
    }

    /**
     * @return the listaCamposAccesoPorTipo
     */
    public List<CampoAcceso> getListaCamposAccesoPorTipo() {
        return listaCamposAccesoPorTipo;
    }

    /**
     * @param listaCamposAccesoPorTipo the listaCamposAccesoPorTipo to set
     */
    public void setListaCamposAccesoPorTipo(List<CampoAcceso> listaCamposAccesoPorTipo) {
        this.listaCamposAccesoPorTipo = listaCamposAccesoPorTipo;
    }

    /**
     * @return the opcionesCamposAccesoPorTipo
     */
    public List<SelectItem> getOpcionesCamposAccesoPorTipo() {
        return opcionesCamposAccesoPorTipo;
    }

    /**
     * @param opcionesCamposAccesoPorTipo the opcionesCamposAccesoPorTipo to set
     */
    public void setOpcionesCamposAccesoPorTipo(List<SelectItem> opcionesCamposAccesoPorTipo) {
        this.opcionesCamposAccesoPorTipo = opcionesCamposAccesoPorTipo;
    }

    /**
     * @return the opcionesOperadoresLogicos
     */
    public List<SelectItem> getOpcionesOperadoresLogicos() {
        return opcionesOperadoresLogicos;
    }

    /**
     * @param opcionesOperadoresLogicos the opcionesOperadoresLogicos to set
     */
    public void setOpcionesOperadoresLogicos(List<SelectItem> opcionesOperadoresLogicos) {
        this.opcionesOperadoresLogicos = opcionesOperadoresLogicos;
    }

    /**
     * @return the prefijo
     */
    public String getPrefijo() {
        return prefijo;
    }

    /**
     * @param prefijo the prefijo to set
     */
    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    /**
     * @return the listaUsosFormula
     */
    public List<Formula> getListaUsosFormula() {
        return listaUsosFormula;
    }

    /**
     * @param listaUsosFormula the listaUsosFormula to set
     */
    public void setListaUsosFormula(List<Formula> listaUsosFormula) {
        this.listaUsosFormula = listaUsosFormula;
    }
}
