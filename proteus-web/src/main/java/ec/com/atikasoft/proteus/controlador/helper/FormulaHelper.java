/*
 *  FormulaHelper.java
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
 *  14/10/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador.helper;

import ec.com.atikasoft.proteus.controlador.helper.base.CatalogoHelper;
import ec.com.atikasoft.proteus.modelo.Constante;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.Variable;
import ec.com.atikasoft.proteus.vo.DatosVariablesVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author LRodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "formulaHelper")
@SessionScoped
public class FormulaHelper extends CatalogoHelper {

    /**
     * Variable para nueva formula.
     */
    private Formula formula;
    /**
     * Variable para modificar/eliminar una formula.
     */
    private Formula formulaEditDelete;
    /**
     * Variable para listar las formulas.
     */
    private List<Formula> listaFormula;
    /**
     * Variable para listar las formulas por codigo.
     */
    private List<Formula> listaFormulaCodigo;
    /**
     * Lista de constantes
     */
    private List<SelectItem> opcionesConstantes;
    private List<Constante> listaConstantes;
    private String nombreConstante;
    /**
     * Lista de variables
     */
    private List<SelectItem> opcionesVariables;
    private List<Variable> listaVariables;
    private String nombreVariableP;
    /**
     * Lista de formulas
     */
    private List<SelectItem> opcionesFomulas;
    private List<Formula> listaOpcionesFormulas;
    private String nombreFormula;
    /**
     * Lista de formulas
     */
    private List<SelectItem> opcionesDatosAdicionales;
    private List<DatoAdicional> listaDatosAdicionales;
    private String nombreDatoAdicional;
    private String contenidoFormula;
    private String prefijoCodigo;
    private String cadenaFormulaConValores;
    
    private List<DatosVariablesVO> listaDatosVariables;
      /**
     * Varible para acumular el resultado de la simulacion.
     */
    private BigDecimal resultadoSimulacion;
    /**
     * Variable para listar los usos de los codigos de las formulas
     * en el campo formula.
     */
    private List<Formula> listaUsosFormula;

    /**
     * Constructor.
     */
    public FormulaHelper() {
        super();
        iniciador();
    }

    /**
     * Método para iniciar las variables de la FormulaHelper.
     */
    public final void iniciador() {
        setFormula(new Formula());
        setFormulaEditDelete(new Formula());
        setListaFormula(new ArrayList<Formula>());
        setListaFormulaCodigo(new ArrayList<Formula>());
        setOpcionesConstantes(new ArrayList<SelectItem>());
        setOpcionesFomulas(new ArrayList<SelectItem>());
        setOpcionesDatosAdicionales(new ArrayList<SelectItem>());
        setOpcionesVariables(new ArrayList<SelectItem>());
        setListaConstantes(new ArrayList<Constante>());
        setListaVariables(new ArrayList<Variable>());
        setListaDatosAdicionales(new ArrayList<DatoAdicional>());
        setListaOpcionesFormulas(new ArrayList<Formula>());
        setListaUsosFormula(new ArrayList<Formula>());
        listaDatosVariables=new ArrayList<DatosVariablesVO>();
        prefijoCodigo = "F_";
        contenidoFormula = "";
    }

    /**
     * @return the formula
     */
    public Formula getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(final Formula formula) {
        this.formula = formula;
    }

    /**
     * @return the formulaEditDelete
     */
    public Formula getFormulaEditDelete() {
        return formulaEditDelete;
    }

    /**
     * @param formulaEditDelete the formulaEditDelete to set
     */
    public void setFormulaEditDelete(final Formula formulaEditDelete) {
        this.formulaEditDelete = formulaEditDelete;
    }

    /**
     * @return the listaFormula
     */
    public List<Formula> getListaFormula() {
        return listaFormula;
    }

    /**
     * @param listaFormula the listaFormula to set
     */
    public void setListaFormula(final List<Formula> listaFormula) {
        this.listaFormula = listaFormula;
    }

    /**
     * @return the listaFormulaNemonico
     */
    public List<Formula> getListaFormulaCodigo() {
        return listaFormulaCodigo;
    }

    /**
     * @param listaFormulaCodigo the listaFormulaCodigo to set
     */
    public void setListaFormulaCodigo(List<Formula> listaFormulaCodigo) {
        this.listaFormulaCodigo = listaFormulaCodigo;
    }

    /**
     * @return the prefijoCodigo
     */
    public String getPrefijoCodigo() {
        return prefijoCodigo;
    }

    /**
     * @param prefijoCodigo the prefijoCodigo to set
     */
    public void setPrefijoCodigo(String prefijoCodigo) {
        this.prefijoCodigo = prefijoCodigo;
    }

    /**
     * @return the opcionesConstantes
     */
    public List<SelectItem> getOpcionesConstantes() {
        return opcionesConstantes;
    }

    /**
     * @param opcionesConstantes the opcionesConstantes to set
     */
    public void setOpcionesConstantes(List<SelectItem> opcionesConstantes) {
        this.opcionesConstantes = opcionesConstantes;
    }

    /**
     * @return the listaConstantes
     */
    public List<Constante> getListaConstantes() {
        return listaConstantes;
    }

    /**
     * @param listaConstantes the listaConstantes to set
     */
    public void setListaConstantes(List<Constante> listaConstantes) {
        this.listaConstantes = listaConstantes;
    }

    /**
     * @return the opcionesVariables
     */
    public List<SelectItem> getOpcionesVariables() {
        return opcionesVariables;
    }

    /**
     * @param opcionesVariables the opcionesVariables to set
     */
    public void setOpcionesVariables(List<SelectItem> opcionesVariables) {
        this.opcionesVariables = opcionesVariables;
    }

    /**
     * @return the listaVariables
     */
    public List<Variable> getListaVariables() {
        return listaVariables;
    }

    /**
     * @param listaVariables the listaVariables to set
     */
    public void setListaVariables(List<Variable> listaVariables) {
        this.listaVariables = listaVariables;
    }

    /**
     * @return the opcionesFomulas
     */
    public List<SelectItem> getOpcionesFomulas() {
        return opcionesFomulas;
    }

    /**
     * @param opcionesFomulas the opcionesFomulas to set
     */
    public void setOpcionesFomulas(List<SelectItem> opcionesFomulas) {
        this.opcionesFomulas = opcionesFomulas;
    }

    /**
     * @return the listaOpcionesFormulas
     */
    public List<Formula> getListaOpcionesFormulas() {
        return listaOpcionesFormulas;
    }

    /**
     * @param listaOpcionesFormulas the listaOpcionesFormulas to set
     */
    public void setListaOpcionesFormulas(List<Formula> listaOpcionesFormulas) {
        this.listaOpcionesFormulas = listaOpcionesFormulas;
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
     * @return the nombreConstante
     */
    public String getNombreConstante() {
        return nombreConstante;
    }

    /**
     * @param nombreConstante the nombreConstante to set
     */
    public void setNombreConstante(String nombreConstante) {
        this.nombreConstante = nombreConstante;
    }

    /**
     * @return the nombreVariableP
     */
    public String getNombreVariableP() {
        return nombreVariableP;
    }

    /**
     * @param nombreVariableP the nombreVariableP to set
     */
    public void setNombreVariableP(String nombreVariableP) {
        this.nombreVariableP = nombreVariableP;
    }

    /**
     * @return the nombreFormula
     */
    public String getNombreFormula() {
        return nombreFormula;
    }

    /**
     * @param nombreFormula the nombreFormula to set
     */
    public void setNombreFormula(String nombreFormula) {
        this.nombreFormula = nombreFormula;
    }

    /**
     * @return the nombreDatoAdicional
     */
    public String getNombreDatoAdicional() {
        return nombreDatoAdicional;
    }

    /**
     * @param nombreDatoAdicional the nombreDatoAdicional to set
     */
    public void setNombreDatoAdicional(String nombreDatoAdicional) {
        this.nombreDatoAdicional = nombreDatoAdicional;
    }

    /**
     * @return the contenidoFormula
     */
    public String getContenidoFormula() {
        return contenidoFormula;
    }

    /**
     * @param contenidoFormula the contenidoFormula to set
     */
    public void setContenidoFormula(String contenidoFormula) {
        this.contenidoFormula = contenidoFormula;
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

    /**
     * @return the listaDatosVariables
     */
    public List<DatosVariablesVO> getListaDatosVariables() {
        return listaDatosVariables;
    }

    /**
     * @param listaDatosVariables the listaDatosVariables to set
     */
    public void setListaDatosVariables(List<DatosVariablesVO> listaDatosVariables) {
        this.listaDatosVariables = listaDatosVariables;
    }

    /**
     * @return the resultadoSimulacion
     */
    public BigDecimal getResultadoSimulacion() {
        return resultadoSimulacion;
    }

    /**
     * @param resultadoSimulacion the resultadoSimulacion to set
     */
    public void setResultadoSimulacion(BigDecimal resultadoSimulacion) {
        this.resultadoSimulacion = resultadoSimulacion;
    }

    /**
     * @return the cadenaFormulaConValores
     */
    public String getCadenaFormulaConValores() {
        return cadenaFormulaConValores;
    }

    /**
     * @param cadenaFormulaConValores the cadenaFormulaConValores to set
     */
    public void setCadenaFormulaConValores(String cadenaFormulaConValores) {
        this.cadenaFormulaConValores = cadenaFormulaConValores;
    }
}
