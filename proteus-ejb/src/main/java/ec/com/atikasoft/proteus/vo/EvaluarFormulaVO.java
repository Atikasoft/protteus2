/*
 *  EvaluarFormulaVO.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Jul 25, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.vo;

import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.ImpuestoRenta;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.Nomina;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.PeriodoNomina;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Variable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javolution.util.FastMap;

/**
 * Contiene los datos para proceder a evaluar una formula.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
public class EvaluarFormulaVO {

    /**
     *
     */
    private Nomina nomina;

    /**
     *
     */
    private Formula formula;

    /**
     *
     */
    private Variable variable;

    /**
     *
     */
    private DistributivoDetalle dd;

    /**
     *
     */
    private PeriodoNomina pn;

    /**
     *
     */
    private ServletContext sc;

    /**
     *
     */
    private Map<Long, BigDecimal> ingresosProyeccionIR;

    /**
     *
     */
    private Servidor servidor;

    /**
     *
     */
    private Liquidacion liquidacion;

    /**
     *
     */
    private List<NovedadDetalle> novedadDetalles;

    /**
     *
     */
    private ImpuestoRenta impuestoRentaExoneracion;

    /**
     *
     */
    private List<ImpuestoRenta> tablaImpuestoRenta;

    /**
     * Constructor sin argumentos.
     */
    public EvaluarFormulaVO() {
        super();
    }

    /**
     * @return the formula
     */
    public Formula getFormula() {
        return formula;
    }

    /**
     * @return the dd
     */
    public DistributivoDetalle getDd() {
        return dd;
    }

    /**
     * @return the pn
     */
    public PeriodoNomina getPn() {
        return pn;
    }

    /**
     * @return the sc
     */
    public ServletContext getSc() {
        return sc;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(final Formula formula) {
        this.formula = formula;
    }

    /**
     * @param dd the dd to set
     */
    public void setDd(final DistributivoDetalle dd) {
        this.dd = dd;
    }

    /**
     * @param pn the pn to set
     */
    public void setPn(final PeriodoNomina pn) {
        this.pn = pn;
    }

    /**
     * @param sc the sc to set
     */
    public void setSc(final ServletContext sc) {
        this.sc = sc;
    }

    /**
     * @return the variable
     */
    public Variable getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(final Variable variable) {
        this.variable = variable;
    }

    /**
     * @return the nomina
     */
    public Nomina getNomina() {
        return nomina;
    }

    /**
     * @param nomina the nomina to set
     */
    public void setNomina(final Nomina nomina) {
        this.nomina = nomina;
    }

    /**
     * @return the servidor
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * @param servidor the servidor to set
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    /**
     * @return the liquidacion
     */
    public Liquidacion getLiquidacion() {
        return liquidacion;
    }

    /**
     * @param liquidacion the liquidacion to set
     */
    public void setLiquidacion(Liquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    /**
     * @return the novedadDetalles
     */
    public List<NovedadDetalle> getNovedadDetalles() {
        if (novedadDetalles == null) {
            novedadDetalles = new ArrayList<NovedadDetalle>();
        }
        return novedadDetalles;
    }

    /**
     * @param novedadDetalles the novedadDetalles to set
     */
    public void setNovedadDetalles(List<NovedadDetalle> novedadDetalles) {
        this.novedadDetalles = novedadDetalles;
    }

    /**
     * @param ingresosProyeccionIR the ingresosProyeccionIR to set
     */
    public void setIngresosProyeccionIR(Map<Long, BigDecimal> ingresosProyeccionIR) {
        this.ingresosProyeccionIR = ingresosProyeccionIR;
    }

    /**
     * @return the ingresosProyeccionIR
     */
    public Map<Long, BigDecimal> getIngresosProyeccionIR() {
        if (ingresosProyeccionIR == null) {
            ingresosProyeccionIR = new FastMap<Long, BigDecimal>();
        }
        return ingresosProyeccionIR;
    }

    /**
     * @return the impuestoRentaExoneracion
     */
    public ImpuestoRenta getImpuestoRentaExoneracion() {
        return impuestoRentaExoneracion;
    }

    /**
     * @param impuestoRentaExoneracion the impuestoRentaExoneracion to set
     */
    public void setImpuestoRentaExoneracion(ImpuestoRenta impuestoRentaExoneracion) {
        this.impuestoRentaExoneracion = impuestoRentaExoneracion;
    }

    /**
     * @return the tablaImpuestoRenta
     */
    public List<ImpuestoRenta> getTablaImpuestoRenta() {
        return tablaImpuestoRenta;
    }

    /**
     * @param tablaImpuestoRenta the tablaImpuestoRenta to set
     */
    public void setTablaImpuestoRenta(List<ImpuestoRenta> tablaImpuestoRenta) {
        this.tablaImpuestoRenta = tablaImpuestoRenta;
    }
}
