/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.OrigenVariableEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoEnum;
import ec.com.atikasoft.proteus.excepciones.ErrorDivisionCeroException;
import ec.com.atikasoft.proteus.excepciones.ErrorSintaxisException;
import ec.com.atikasoft.proteus.excepciones.FormulaException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Constante;
import ec.com.atikasoft.proteus.modelo.DatoAdicional;
import ec.com.atikasoft.proteus.modelo.Formula;
import ec.com.atikasoft.proteus.modelo.Variable;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.EvaluarFormulaVO;
import ec.com.atikasoft.proteus.vo.ResultadoFormulaVO;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javolution.util.FastMap;
import ec.com.atikasoft.proteus.expr.Expr;
import ec.com.atikasoft.proteus.expr.ExprDouble;
import ec.com.atikasoft.proteus.expr.ExprEvaluatable;
import ec.com.atikasoft.proteus.expr.ExprString;
import ec.com.atikasoft.proteus.expr.parser.ExprParser;
import ec.com.atikasoft.proteus.expr.util.SimpleEvaluationContext;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class FormulaServicio extends BaseServicio {

    /**
     * Servicio de variables.
     */
    @EJB
    private VariableServicio variableServicio;

    /**
     * Servicio de novedades.
     */
    @EJB
    private NovedadServicio novedadServicio;

    /**
     * Recupera los codigos que componenete una formula.
     *
     * @param formula
     * @param sc
     * @return
     * @throws ServicioException
     */
    public Map<String, Object> buscarCodigosFormula(final StringBuilder formula, final ServletContext sc) throws
            ServicioException {
        try {
            Map<String, Object> valores = new HashMap<String, Object>();
            recolectarConstantes(formula, valores, sc);
            recolectarVariables(formula, valores, sc);
            recolectarDatosAdicionales(formula, valores, sc);
            Integer count = 1;
            recolectarFormulas(null, formula, valores, count, sc);
            return valores;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Valida si una formula es valida
     *
     * @param formula
     * @param sc
     * @throws ServicioException
     */
    public void validarFormula(final StringBuilder formula, final ServletContext sc) throws FormulaException,
            ServicioException {
        try {
            SimpleEvaluationContext context = new SimpleEvaluationContext();
            Map<String, Object> valores = new HashMap<String, Object>();
            recolectarConstantes(formula, valores, sc);
            recolectarVariables(formula, valores, sc);
            recolectarDatosAdicionales(formula, valores, sc);
            Integer count = 1;
            recolectarFormulas(null, formula, valores, count, sc);
            inyectarValores(context, formula, valores);
            calcularFormulaExcel(context, formula);
        } catch (FormulaException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Simula la ejecucion de la formula.
     *
     * @param formula
     * @param valores
     * @param sc
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.FormulaException
     * @throws ServicioException
     */
    public BigDecimal simularFormular(final StringBuilder formula, final Map<String, Object> valores,
            final ServletContext sc) throws FormulaException, ServicioException {
        try {
            SimpleEvaluationContext context = new SimpleEvaluationContext();
            Map<String, Object> valoresTempo = new HashMap<String, Object>();
            recolectarConstantes(formula, valoresTempo, sc);
            recolectarVariables(formula, valoresTempo, sc);
            recolectarDatosAdicionales(formula, valoresTempo, sc);
            Integer count = 1;
            recolectarFormulas(null, formula, valoresTempo, count, sc);
            inyectarValores(context, formula, valores);
            return calcularFormulaExcel(context, formula);
        } catch (FormulaException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Evalua una formula
     *
     * @param vo
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.FormulaException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public synchronized ResultadoFormulaVO evaluarFormula(final EvaluarFormulaVO vo) throws FormulaException,
            ServicioException {
        try {
            SimpleEvaluationContext context = new SimpleEvaluationContext();
            Formula formula = vo.getFormula();
            StringBuilder f = new StringBuilder(formula.getFormula());
            Map<String, Object> valores = new FastMap<String, Object>();
            // recolectar varibles y constantes.
            recolectarConstantes(f, valores, vo.getSc());
            recolectarVariables(f, valores, vo.getSc());
            recolectarDatosAdicionales(f, valores, vo.getSc());
            Integer count = 1;
            recolectarFormulas(formula.getCodigo(), f, valores, count, vo.getSc());
            // calcular las variables.
            calcularValorVariable(vo, valores);
            // calcular datos adicionales.
            calcularValorDatoAdicional(vo, valores);
            // inyectar valores.
            inyectarValores(context, f, valores);
            // calcular.
            ResultadoFormulaVO r = new ResultadoFormulaVO();

//            System.out.println("evaluarFormula.1:" + valores);
//            System.out.println("evaluarFormula..2:" + f);
            r.setValor(calcularFormulaExcel(context, f));
//            System.out.println("evaluarFormula.9:");
            r.setValores(valores);
            r.setFormula(f.toString());
//            System.out.println("evaluarFormula.10:");
            return r;
        } catch (FormulaException e) {
            throw e;
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Recupera los valores contantes.
     *
     * @param formula
     * @param valores
     * @param sc
     */
    private void recolectarConstantes(final StringBuilder formula, final Map<String, Object> valores,
            final ServletContext sc) {
        List<Constante> lista = (List<Constante>) sc.getAttribute(CacheEnum.CONSTANTES.getCodigo());
        for (Constante c : lista) {
            if (UtilCadena.buscarPalabraCompleta(c.getCodigo(), formula) != -1
                    && !valores.containsKey(c.getCodigo())) {
                if (c.getTipo().equals(TipoDatoEnum.NUMERICO.getCodigo())) {
                    valores.put(c.getCodigo(), c.getValorNumerico());
                } else if (c.getTipo().equals(TipoDatoEnum.TEXTO.getCodigo())) {
                    valores.put(c.getCodigo(), c.getValorTexto());
                } else if (c.getTipo().equals(TipoDatoEnum.FECHA.getCodigo())) {
                    valores.put(c.getCodigo(), c.getValorFecha());
                }
            }
        }
    }

    /**
     * Recupera las variables que forman parte de la formula.
     */
    private void recolectarVariables(final StringBuilder formula, final Map<String, Object> valores,
            final ServletContext sc) {
        List<Variable> lista = (List<Variable>) sc.getAttribute(CacheEnum.VARIABLES.getCodigo());
        for (Variable v : lista) {
            if (UtilCadena.buscarPalabraCompleta(v.getCodigo(), formula) != -1
                    && !valores.containsKey(v.getCodigo())) {
                if (v.getOrigen().equals(OrigenVariableEnum.CAMPOS_ACCESO.getCodigo())) {
                    if (v.getCampoAcceso().getMetadataColumna().getTipo().equals(TipoDatoEnum.TEXTO.getCodigo())) {
                        valores.put(v.getCodigo(), "0");
                    } else if (v.getCampoAcceso().getMetadataColumna().getTipo().equals(
                            TipoDatoEnum.NUMERICO.getCodigo())) {
                        valores.put(v.getCodigo(), BigDecimal.ZERO);
                    } else if (v.getCampoAcceso().getMetadataColumna().getTipo().equals(TipoDatoEnum.FECHA.getCodigo())) {
                        valores.put(v.getCodigo(), new Date());
                    } else {
                        valores.put(v.getCodigo(), BigDecimal.ZERO);
                    }
                } else if (v.getOrigen().equals(OrigenVariableEnum.DATO_ADICIONAL.getCodigo())) {
                    if (v.getDatoAdicional().getTipo().equals(TipoDatoEnum.TEXTO.getCodigo())) {
                        valores.put(v.getCodigo(), "0");
                    } else if (v.getDatoAdicional().getTipo().equals(TipoDatoEnum.NUMERICO.getCodigo())) {
                        valores.put(v.getCodigo(), BigDecimal.ZERO);
                    } else if (v.getDatoAdicional().getTipo().equals(TipoDatoEnum.FECHA.getCodigo())) {
                        valores.put(v.getCodigo(), new Date());
                    } else {
                        valores.put(v.getCodigo(), BigDecimal.ZERO);
                    }
                } else {
                    valores.put(v.getCodigo(), BigDecimal.ZERO);
                }
            }
        }
    }

    /**
     * Recupera los datos adicionales que forman parte de la formula.
     *
     * @param formula
     * @param valores
     * @param sc
     */
    private void recolectarDatosAdicionales(final StringBuilder formula, final Map<String, Object> valores,
            final ServletContext sc) {
        List<DatoAdicional> lista = (List<DatoAdicional>) sc.getAttribute(CacheEnum.DATOS_ADICIONALES.getCodigo());
        for (DatoAdicional da : lista) {
            if (UtilCadena.buscarPalabraCompleta(da.getCodigo(), formula) != -1
                    && !valores.containsKey(da.getCodigo())) {
                valores.put(da.getCodigo(), BigDecimal.ZERO);
            }
        }
    }

    /**
     * Recupera los valores de la formula.
     *
     * @param codigoFormulaOriginal
     * @param formula
     * @param valores
     * @param count
     * @param sc
     * @throws EntidadBOExcepcion
     */
    private void recolectarFormulas(final String codigoFormulaOriginal, final StringBuilder formula,
            final Map<String, Object> valores, Integer count, final ServletContext sc) throws
            ServicioException {
        List<Formula> lista = (List<Formula>) sc.getAttribute(CacheEnum.FORMULAS.getCodigo());
        int i = -1;
        for (Formula f : lista) {
            if (!f.getCodigo().equals(codigoFormulaOriginal)) {
                i = UtilCadena.buscarPalabraCompleta(f.getCodigo(), formula);
                if (i != -1 && !valores.containsKey(f.getCodigo())) {
                    if (codigoFormulaOriginal != null && codigoFormulaOriginal.equals(f.getCodigo())) {
                        throw new ServicioException(UtilCadena.concatenar(
                                "Existe referencia circular para la formula ", codigoFormulaOriginal, " en la formula ",
                                f.getCodigo()));
                    }
                    while (i != -1) {
                        formula.replace(i, i + f.getCodigo().length(), new StringBuilder().append(
                                "(").append(f.getFormula()).append(")").toString());
                        i = UtilCadena.buscarPalabraCompleta(f.getCodigo(), formula);
                    }
                    recolectarConstantes(new StringBuilder(f.getFormula()), valores, sc);
                    recolectarVariables(new StringBuilder(f.getFormula()), valores, sc);
                    recolectarDatosAdicionales(new StringBuilder(f.getFormula()), valores, sc);
                    count++;
                    if (count <= 59) {
                        recolectarFormulas(codigoFormulaOriginal, formula, valores, count, sc);
                    } else {
                        throw new ServicioException("Formula entro en un círculo infinito. ");
                    }
                }
            }
        }
    }

    /**
     * Realiza el calculo de variables.
     *
     * @param vo
     * @param valores
     * @throws ServicioException
     */
    private void calcularValorVariable(final EvaluarFormulaVO vo, final Map<String, Object> valores) throws
            ServicioException {
        Set<String> keys = valores.keySet();
        for (String key : keys) {
            if (key.indexOf("V_") != -1) {
                for (Variable v : (List<Variable>) vo.getSc().getAttribute(CacheEnum.VARIABLES.getCodigo())) {
                    if (v.getCodigo().equals(key)) {
                        vo.setVariable(v);
                        valores.put(key, variableServicio.calcular(vo));
                        break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param vo
     * @param valores
     * @throws ServicioException
     */
    private void calcularValorDatoAdicional(final EvaluarFormulaVO vo, final Map<String, Object> valores) throws
            ServicioException {
        Set<String> keys = valores.keySet();
        for (String key : keys) {
            if (key.indexOf("D_") != -1) {
                for (DatoAdicional da : (List<DatoAdicional>) vo.getSc().getAttribute(
                        CacheEnum.DATOS_ADICIONALES.getCodigo())) {
                    if (da.getCodigo().equals(key) && vo.getNomina() != null) {
                        valores.put(key, novedadServicio.calcular(vo.getNomina().getInstitucionEjercicioFiscalId(), vo.
                                getNomina().getId(), da.getId(), vo.getDd().getServidor().getId(), vo.getNovedadDetalles()));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Inyecta los valores ccalculados en la formula.
     *
     * @param formula
     * @param valores
     */
    private void inyectarValores(final SimpleEvaluationContext context, final StringBuilder formula,
            final Map<String, Object> valores) {
        for (String key : valores.keySet()) {
            //System.out.println("inyectarValores:" + key + ":" + determinarValor(valores.get(key)));
            context.setVariable(key, determinarValor(valores.get(key)));
        }
    }

    /**
     * Determina el valor.
     *
     * @param objeto
     * @return
     */
    private Expr determinarValor(final Object objeto) {
        Expr v;
        if (objeto instanceof BigDecimal || objeto instanceof Integer || objeto instanceof Long
                || objeto instanceof Float || objeto instanceof Double) {
            v = new ExprDouble(Double.valueOf(objeto.toString()));
        } else if (objeto instanceof String) {
            v = new ExprString(objeto.toString().replaceAll("-", ""));
        } else if (objeto instanceof Date) {
            v = new ExprString(new SimpleDateFormat("dd-MM-yyyy").format(objeto));
        } else {
            v = new ExprString(objeto.toString());
        }
        return v;

    }

    /**
     *
     * @param formula
     * @return
     * @throws ServicioException
     * @throws ErrorSintaxisException
     * @throws ErrorDivisionCeroException
     */
    private BigDecimal calcularFormulaExcel(final SimpleEvaluationContext context, final StringBuilder formula) throws
            FormulaException {
        try {
            
            Expr e = ExprParser.parse(formula.toString().replaceAll(" +", ""));
            //System.out.println("calcularFormulaExcel:" + e.toString());
            e = ((ExprEvaluatable) e).evaluate(context);
            BigDecimal valor = new BigDecimal(e.toString());
            //System.out.println(":" + formula+"="+valor);
            return valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FormulaException(e);
        }
    }
}
