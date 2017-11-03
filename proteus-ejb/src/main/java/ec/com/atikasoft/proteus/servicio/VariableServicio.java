/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.CotizacionIessDao;
import ec.com.atikasoft.proteus.dao.GastoPersonalDao;
import ec.com.atikasoft.proteus.dao.ImpuestoRentaDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.ServidorCargaFamiliarDao;
import ec.com.atikasoft.proteus.dao.VariableDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleIRDao;
import ec.com.atikasoft.proteus.dao.NominaIRDao;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.GradoDiscapacidadIREnum;
import ec.com.atikasoft.proteus.enums.MetadataTablaEnum;
import ec.com.atikasoft.proteus.enums.OrigenVariableEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.TipoDatoAdicionalEnum;
import ec.com.atikasoft.proteus.enums.VariablesPreconstruidasEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.nomina.NominaIR;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.EvaluarFormulaVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class VariableServicio extends BaseServicio {

    /**
     * Dao de variables.
     */
    @EJB
    private VariableDao variableDao;

    /**
     * Dao de cargas familiares del servidor.
     */
    @EJB
    private ServidorCargaFamiliarDao servidorCargaFamiliarDao;

    /**
     * Dao de detalle de la nomina.
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     * Dao de gastos personales.
     */
    @EJB
    private GastoPersonalDao gastoPersonalDao;

    /**
     * Dao d vista de ir de nomina.
     */
    @EJB
    private NominaDetalleIRDao vistaNominaIRDao;

    /**
     * Dao de tabla de impuesto de renta.
     */
    @EJB
    private ImpuestoRentaDao impuestoRentaDao;

    /**
     *
     */
    @EJB
    private NominaIRDao nominaIRDao;

    /**
     *
     */
    @EJB
    private CotizacionIessDao cotizacionIessDao;

    /**
     * Realiza el calculo de una variable.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public Object calcular(final EvaluarFormulaVO vo) throws ServicioException {
        try {
            Object valor = BigDecimal.ZERO;
            Variable v = vo.getVariable();
            // aqui de debe validar no importa lo que tenga el atributo 'validado'.
            if (v.getOrigen().equals(OrigenVariableEnum.CAMPOS_ACCESO.getCodigo())) {
                valor = calcularCampoAcceso(vo);
            } else if (v.getOrigen().equals(OrigenVariableEnum.DATO_ADICIONAL.getCodigo())) {
                valor = calcularDatoAdicionalFijo(vo);
            } else if (v.getOrigen().equals(OrigenVariableEnum.PRECONSTRUIDOS.getCodigo())) {
                // precontruida.
                valor = calcularPreconstruidos(vo);
            }
            return (valor == null ? BigDecimal.ZERO : valor);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Calcula el valorpara campos de accesos.
     *
     * @param vo
     * @param valor
     * @return
     */
    private Object calcularCampoAcceso(final EvaluarFormulaVO vo) {
        StringBuilder select = new StringBuilder();
        select.append(vo.getVariable().getSelect());
        // condiciones para distributivo.
        if (vo.getDd() != null) {
            generarCondicionesActivos(vo, select);
        }
        // condiciones para pasivos.
//        if (vo.getPp() != null) {
//            generarCondicionesPasivos(v, vo, select);
//        }
        Object valor = null;
//        System.out.println(select.toString());
        List resultado = variableDao.getEntityManager().createNativeQuery(select.toString()).getResultList();
        for (Object o : resultado) {
            valor = o;
        }
        return valor;
    }

    /**
     * Generar las condiciones para el calculo del valor de la variable para distributivo
     *
     * @param var
     * @param idEjercicioFiscal
     * @param codigoInstitucion
     * @param idDistributivoDetalle
     * @param sql
     */
    private void generarCondicionesActivos(final EvaluarFormulaVO vo, final StringBuilder sql) {
        sql.append(" WHERE 1=1 ");
        if (!vo.getVariable().getVariablesCondiciones().isEmpty()) {
            sql.append(" AND  ");
            for (VariableCondicion c : vo.getVariable().getVariablesCondiciones()) {
                if (c.getVigente()) {
                    sql.append(" ");
                    sql.append(UtilCadena.duplicarCadena("(", c.getCantidadParentesisIzq()));
                    sql.append(c.getCampoAcceso().getMetadataColumna().getMetadataTabla().getTabla());
                    sql.append(".");
                    sql.append(c.getCampoAcceso().getMetadataColumna().getColumna());
                    sql.append(c.getTipoOperacionComparacion());
                    sql.append(c.getLiteral());
                    sql.append(UtilCadena.duplicarCadena(")", c.getCantidadParentesisDer()));
                    sql.append(" ");
                    sql.append(c.getOperadorLogico() == null ? "" : c.getOperadorLogico());
                }
            }
        }
        if (vo.getVariable().getCampoAcceso().getMetadataColumna().getMetadataTabla().getNombre().equals(
                MetadataTablaEnum.DISTRIBUTIVOS.getCodigo())) {
            sql.append(" AND distributivo_detalle_id=").append(vo.getDd().getId());
        } else if (vo.getVariable().getCampoAcceso().getMetadataColumna().getMetadataTabla().getNombre().equals(
                MetadataTablaEnum.PERIODOS.getCodigo())) {
            sql.append(" AND ejercicio_fiscal_id=").append(vo.getNomina().getInstitucionEjercicioFiscal().
                    getEjercicioFiscal().getId());
            sql.append(" AND id=").append(vo.getNomina().getPeriodoNominaId());
        } else if (vo.getVariable().getCampoAcceso().getMetadataColumna().getMetadataTabla().getNombre().equals(
                MetadataTablaEnum.NOMINAS.getCodigo())) {
            sql.append(" AND ejercicio_fiscal_id=").append(vo.getNomina().getInstitucionEjercicioFiscal().
                    getEjercicioFiscal().getId());
            sql.append(" AND servidor_id=").append(vo.getDd().getServidor().getId());
        } else if (vo.getVariable().getCampoAcceso().getMetadataColumna().getMetadataTabla().getNombre().equals(
                MetadataTablaEnum.LIQUIDACIONES.getCodigo())) {
            sql.append(" AND servidor_id=").append(vo.getLiquidacion().getServidor().getId());
            sql.append(" AND estado='R'");
        }
    }

    /**
     *
     * @param v
     * @param dd
     * @param valor
     * @return
     * @throws EntidadBOExcepcion
     */
    private Object calcularDatoAdicionalFijo(final EvaluarFormulaVO vo) throws ServicioException {
        // por dato adicional.
        Object valor = null;
        Variable v = vo.getVariable();
        DistributivoDetalle dd = vo.getDd();
        if (v.getDatoAdicional() != null && dd != null && v.getDatoAdicional().getTipo().equals(
                TipoDatoAdicionalEnum.FIJO.
                getCodigo())) {
            // buscar en servidor x dato adicional.
//            ServidorDatoAdicional da = servidorDatoAdicionalBO.buscarPorServidorYDatoAdicional(dd.getServidorId(),
//                    v.getDatoAdicionalId());
//            if (da != null) {
//                if (da.getDatoAdicional().getTipoDato().equals(TipoConstantesEnum.NUMERICO.getCodigo())) {
//                    valor = da.getValorNumerico();
//                } else if (da.getDatoAdicional().getTipoDato().equals(
//                        TipoConstantesEnum.FECHA.getCodigo())) {
//                    valor = da.getValorFecha();
//                } else if (da.getDatoAdicional().getTipoDato().equals(
//                        TipoConstantesEnum.TEXTO.getCodigo())) {
//                    valor = da.getValorTexto();
//                }
//            }
        }
        return valor;
    }

    /**
     * Ejecuta las varibles precalculadas.
     *
     * @param v Varibles.
     * @param codigoInstitucion Codigo de la institucion.
     * @param d Distributivo.
     * @param sc Servlet Context.
     * @return Valor de la varibles.
     * @throws EntidadBOExcepcion Error de ejecucion.
     */
    private BigDecimal calcularPreconstruidos(final EvaluarFormulaVO vo) throws ServicioException, DaoException {
        List<CotizacionIess> cotizaciones = (List<CotizacionIess>) vo.getSc().getAttribute(CacheEnum.COTIZACIONES_IESS.
                getCodigo());

        BigDecimal valor = BigDecimal.ZERO;
        if (VariablesPreconstruidasEnum.PORCENTAJE_APORTE_PATRONAL.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            for (CotizacionIess c : cotizaciones) {
                if (vo.getDd().getEscalaOcupacional().getNivelOcupacional().getId().compareTo(
                        c.getNivelOcupacionalId()) == 0) {
                    valor = c.getPorcentajeAportePatronal();
                    break;
                }
            }
        } else if (VariablesPreconstruidasEnum.PORCENTAJE_APORTE_INDIVIDUAL.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            for (CotizacionIess c : cotizaciones) {
                if (vo.getDd().getEscalaOcupacional().getNivelOcupacional().getId().compareTo(
                        c.getNivelOcupacionalId()) == 0) {
                    valor = c.getPorcentajeAporteIndividual();
                    break;
                }
            }
        } else if (VariablesPreconstruidasEnum.NUMERO_DIAS_LABORADOS_PERIODO.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            // calcular los feriados.
            List<Feriado> feriados = (List<Feriado>) vo.getSc().getAttribute(CacheEnum.FERIADOS.getCodigo());
            List<Integer> diasEnAnio = new ArrayList<Integer>();
            Calendar cal = Calendar.getInstance();
            for (Feriado f : feriados) {
                if (f.getFecha().getTime() >= vo.getNomina().getPeriodoNomina().getFechaInicio().getTime()
                        && f.getFecha().getTime() <= vo.getNomina().getPeriodoNomina().getFechaFinal().getTime()) {
                    cal.setTime(f.getFecha());
                    diasEnAnio.add(cal.get(Calendar.DAY_OF_YEAR));
                }
            }
            valor = new BigDecimal(UtilFechas.calcularNumeroDiasLaborables(vo.getNomina().getPeriodoNomina().
                    getFechaInicio(), vo.getNomina().getPeriodoNomina().getFechaFinal(), diasEnAnio));
        } else if (VariablesPreconstruidasEnum.NUMERO_DIAS_LABORADOS_PRIMER_PERIODO.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            if (vo.getDd().getServidor().getFechaIngresoInstitucion() != null) {
                if (vo.getDd().getFechaIngreso() != null && new SimpleDateFormat("yyyyMM").format(vo.getDd().
                        getFechaIngreso()).equals(new SimpleDateFormat("yyyyMM").format(vo.getPn().getFechaInicio()))) {
                    if (UtilFechas.truncarFecha(vo.getDd().getFechaIngreso()).compareTo(UtilFechas.truncarFecha(
                            vo.getPn().getFechaInicio())) > 0) {
                        valor = new BigDecimal(30 - UtilFechas.obtenerDiaDelMes(vo.getDd().getFechaIngreso()) + 1);
                    }
                }
            }
        } else if (VariablesPreconstruidasEnum.NUMERO_MESES_LABORADOS_INSTITUCION.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            valor = new BigDecimal(UtilFechas.calculaNumMesesEntreDosFechas(vo.getDd().getServidor().
                    getFechaIngresoInstitucion(), vo.getPn().getFechaFinal()));
        } else if (VariablesPreconstruidasEnum.NUMERO_MESES_LABORADOS_SECTOR_PUBLICO.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            valor = new BigDecimal(UtilFechas.calculaNumMesesEntreDosFechas(vo.getDd().getServidor().
                    getFechaIngresoSectorPublico(), vo.getPn().getFechaFinal()));
        } else if (VariablesPreconstruidasEnum.NUMERO_CARGAS_FAMILIARES.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            valor = new BigDecimal(servidorCargaFamiliarDao.contarPorServidor(vo.getDd().getServidor().getId()));
        } else if (VariablesPreconstruidasEnum.IMPUESTO_RENTA.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            valor = calcularImpuestoRentaMetodoAnual(vo);
        } else if (VariablesPreconstruidasEnum.ENCARGO.getCodigo().equals(vo.getVariable().getCodigoPreconstruido())) {
            valor = calculoEncargo(vo);
        } else if (VariablesPreconstruidasEnum.SUBROGACION.getCodigo().equals(vo.getVariable().getCodigoPreconstruido())) {
            valor = calculoSubrogacion(vo);
        } else if (VariablesPreconstruidasEnum.RECUPERA_ANTICIPO_QUINCENA.getCodigo().equals(vo.getVariable().
                getCodigoPreconstruido())) {
            valor = recuperaPagoAnticipoQuincena(vo);
        } else if (VariablesPreconstruidasEnum.RECUPERA_ANTICIPO_DECIMO_CUARTO.getCodigo().equals(
                vo.getVariable().getCodigoPreconstruido())) {
            valor = recuperaAnticipoDecimoCuarto(vo);
        } else if (VariablesPreconstruidasEnum.RECUPERA_ANTICIPO_DECIMO_TERCERO.getCodigo().equals(
                vo.getVariable().getCodigoPreconstruido())) {
            valor = recuperaAnticipoDecimoTercero(vo);
        }
        return valor.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     *
     * @param vo
     * @return
     */
    private BigDecimal recuperaAnticipoDecimoCuarto(final EvaluarFormulaVO vo) {
        BigDecimal valor = BigDecimal.ZERO;
        try {
            valor = nominaDetalleDao.calcularValorAnticipoDecimoCuarto(vo.getNomina().getPeriodoNomina(),
                    vo.getDd().getIdServidor());
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return valor;
    }

    /**
     *
     * @param vo
     * @return
     */
    private BigDecimal recuperaAnticipoDecimoTercero(final EvaluarFormulaVO vo) {
        BigDecimal valor = BigDecimal.ZERO;
        try {
            valor = nominaDetalleDao.calcularValorAnticipoDecimoTercero(vo.getNomina().getPeriodoNomina(),
                    vo.getDd().getIdServidor());
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return valor;
    }

    /**
     *
     * @param vo
     * @return
     */
    private BigDecimal recuperaPagoAnticipoQuincena(final EvaluarFormulaVO vo) {
        BigDecimal valor = BigDecimal.ZERO;
        try {
            valor = nominaDetalleDao.calcularValorAnticipoQuincena(vo.getNomina().getPeriodoNominaId(),
                    vo.getDd().getIdServidor(), "I7");
        } catch (DaoException ex) {
            ex.printStackTrace();
        }
        return valor;
    }

    /**
     * Calculo el valor de subrogacion.
     *
     * @param vo
     * @return
     */
    private BigDecimal calculoSubrogacion(final EvaluarFormulaVO vo) {
        BigDecimal valor = BigDecimal.ZERO;
        Date f1 = vo.getDd().getFechaInicioSubrogacion();
        Date f2 = vo.getDd().getFechaFinSubrogacion();

        if (f2 == null) {
            f2 = vo.getPn().getFechaFinal();
        }
        if (f1 != null && f2 != null && vo.getDd().getDistributivoDetalleSubrogacion() != null) {
            // calcula el numero de dias de subrogacion en el presente periodo.
            long totalDias = 0;
            if (f1.getTime() <= vo.getPn().getFechaInicio().getTime() && f2.getTime() >= vo.getPn().
                    getFechaFinal().getTime()) {
                //  periodo completo
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(vo.getPn().getFechaInicio(),
                        vo.getPn().getFechaFinal());
            } else if (f1.getTime() > vo.getPn().getFechaInicio().getTime() && f2.getTime() < vo.getPn().
                    getFechaFinal().getTime()) {
                // encargo completo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(f1, f2);
            } else if (f1.getTime() <= vo.getPn().getFechaInicio().getTime() && UtilFechas.between(f2, vo.getPn().
                    getFechaInicio(), vo.getPn().getFechaFinal())) {
                // encargo parcial en inicio de periodo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(vo.getPn().getFechaInicio(), f2);
            } else if (UtilFechas.between(f1, vo.getPn().getFechaInicio(), vo.getPn().getFechaFinal()) && f2.getTime()
                    >= vo.getPn().getFechaFinal().getTime()) {
                // encargo parcial en fin de periodo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(f1, vo.getPn().getFechaFinal());
            }
            totalDias = totalDias < 0 ? 0 : totalDias;
            // analisis de dias 
            if (totalDias > 0 && validarSiMesCompleto(f1, f2)) {
                int ultimaDiaMes = UtilFechas.obtenerUltimoDiaDelMes(f2);
                if (ultimaDiaMes != 30) {
                    totalDias = totalDias - (ultimaDiaMes - 30);
                }
            }
            // calcula el valor de la subrogacion
            // sueldo del puesto subrogado / numero de dias del periodo x numero de dias subrogados.
            if (totalDias > 0) {
                if (totalDias >= 30) {
                    valor = vo.getDd().getDistributivoDetalleSubrogacion().getRmu().subtract(vo.getDd().getRmu());
                } else {
                    BigDecimal valorSubrogado = vo.getDd().getDistributivoDetalleSubrogacion().getRmu().
                            divide(new BigDecimal("30"), 100, RoundingMode.DOWN);
                    BigDecimal valorActual = vo.getDd().getRmu().divide(new BigDecimal("30"), 100, RoundingMode.DOWN);
                    valor = valorSubrogado.subtract(valorActual);
                    valor = valor.multiply(new BigDecimal(totalDias));
                }
            }
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            valor = BigDecimal.ZERO;
        }
        return UtilNumeros.redondear(valor, 2, true);
    }

    /**
     * Calculo el valor de subrogacion.
     *
     * @param vo
     * @return
     */
    private BigDecimal calculoEncargo(final EvaluarFormulaVO vo) {
        BigDecimal valor = BigDecimal.ZERO;
        Date f1 = vo.getDd().getFechaInicioEncargo();
        Date f2 = vo.getDd().getFechaFinEncargo();

        if (f2 == null) {
            f2 = vo.getPn().getFechaFinal();
        }
        if (f1 != null && f2 != null && vo.getDd().getDistributivoDetalleEncargo() != null) {
            // calcula el numero de dias de encargo en el presente periodo.
            long totalDias = 0;
            if (f1.getTime() <= vo.getPn().getFechaInicio().getTime() && f2.getTime() >= vo.getPn().
                    getFechaFinal().getTime()) {
                //  periodo completo
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(vo.getPn().getFechaInicio(),
                        vo.getPn().getFechaFinal());
            } else if (f1.getTime() > vo.getPn().getFechaInicio().getTime() && f2.getTime() < vo.getPn().
                    getFechaFinal().getTime()) {
                // encargo completo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(f1, f2);
            } else if (f1.getTime() <= vo.getPn().getFechaInicio().getTime() && UtilFechas.between(f2, vo.getPn().
                    getFechaInicio(), vo.getPn().getFechaFinal())) {
                // encargo parcial en inicio de periodo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(vo.getPn().getFechaInicio(), f2);
            } else if (UtilFechas.between(f1, vo.getPn().getFechaInicio(), vo.getPn().getFechaFinal()) && f2.getTime()
                    >= vo.getPn().getFechaFinal().getTime()) {
                // encargo parcial en fin de periodo.
                totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(f1, vo.getPn().getFechaFinal());
            }

            totalDias = totalDias < 0 ? 0 : totalDias;
            if (totalDias > 0 && validarSiMesCompleto(f1, f2)) {
                // analisis de dias 
                int ultimaDiaMes = UtilFechas.obtenerUltimoDiaDelMes(f2);
                if (ultimaDiaMes != 30) {
                    totalDias = totalDias - (ultimaDiaMes - 30);
                }
            }

            // calcula el valor de la encargo
            // sueldo del puesto encargo / numero de dias del periodo x numero de dias encargados.
            if (totalDias > 0) {
                if (totalDias >= 30) {
                    valor = vo.getDd().getDistributivoDetalleEncargo().getRmu().subtract(vo.getDd().getRmu());
                } else {
                    BigDecimal valorEncargado = vo.getDd().getDistributivoDetalleEncargo().getRmu().divide(
                            new BigDecimal("30"), 100, RoundingMode.HALF_DOWN);
                    BigDecimal valorActual = vo.getDd().getRmu().divide(new BigDecimal("30"), 100, RoundingMode.HALF_DOWN);
                    valor = valorEncargado.subtract(valorActual);
                    valor = valor.multiply(new BigDecimal(totalDias));
                }
            }
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            valor = BigDecimal.ZERO;
        }
        return UtilNumeros.redondear(valor, 2, true);
    }

    /**
     * Realiza el calculo del impuesto de la renta mensual.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    private BigDecimal calcularImpuestoRentaMetodoMensual(final EvaluarFormulaVO vo) throws DaoException,
            ServicioException {
        // calcular el total de ingresos - descuentos del presente periodo (solo rubros grabables)
        BigDecimal ingresos = nominaDetalleDao.calcularTotalIngresosGravables(vo.getNomina().getId(), vo.getDd().
                getServidor().getId());

        // calcular el valor de la proyeccion de gastos.
        List<GastoPersonal> gastosPersonales = gastoPersonalDao.buscarTodosPorServidorYEjercicioFiscal(vo.getDd().
                getServidor().getId(), vo.getNomina().getPeriodoNomina().getEjercicioFiscalId());
        BigDecimal proyecion = BigDecimal.ZERO;
        if (!gastosPersonales.isEmpty()) {
            proyecion = gastosPersonales.get(0).getTotalDeducible();
            proyecion = proyecion.divide(new BigDecimal(UtilFechas.MESES_EN_ANIO), 2, RoundingMode.HALF_DOWN);
        }
        // obtener el saldo de ingresos.

        BigDecimal saldo = ingresos.subtract(proyecion);
//        System.out.println("ingresos:" + ingresos + " menos: " + proyecion + "    saldo:" + saldo);
        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            saldo = BigDecimal.ZERO;
        }
        // verificar la ubicacion en la tabla de IR.
        ImpuestoRenta ir = impuestoRentaDao.buscarPorEscala(vo.getNomina().getPeriodoNomina().getEjercicioFiscalId(),
                saldo);
        // realizar el calculo del IR.
        if (ir == null) {
            throw new ServicioException("No existe escala de impuesto a la renta para:" + saldo);
        }
        BigDecimal valor = ir.getPorcentajeImpuestoSobreFraccionBasica().divide(
                new BigDecimal(UtilFechas.MESES_EN_ANIO), 100, RoundingMode.HALF_DOWN);
//        System.out.println("id:" + ir.getId() + " valor sobre fraccion basica/12:" + valor + " fbasica/12:"
//                + (ir.getFraccionBasica().divide(new BigDecimal(UtilFechas.MESES_EN_ANIO), 2, RoundingMode.HALF_DOWN)));
        valor = valor.add(saldo.subtract(ir.getFraccionBasica().divide(new BigDecimal(UtilFechas.MESES_EN_ANIO), 100,
                RoundingMode.HALF_DOWN)).multiply(ir.getPorcentajeImpuestoFraccionExcedente()).divide(new BigDecimal(
                                "100")));
//        System.out.println("+++++++++++++++++++IR++++++++++++++++++++");
//        System.out.println("++Servidor:" + vo.getDd().getServidor().getId());
//        System.out.println("++ingresos:" + ingresos);
//        System.out.println("++proyecion:" + proyecion);
//        System.out.println("++saldo:" + saldo);
//        System.out.println("++ir.getFraccionBasica:" + ir.getFraccionBasica());
//        System.out.println("++ir.getExcesoHasta:" + ir.getExcesoHasta());
//        System.out.println("++ir.getPorcentajeImpuestoSobreFraccionBasica:" + ir.
//                getPorcentajeImpuestoSobreFraccionBasica());
//        System.out.println("++ir.getPorcentajeImpuestoFraccionExcedente:" + ir.getPorcentajeImpuestoFraccionExcedente());
//        System.out.println("++valor:" + valor);
        return valor;
    }

    /**
     *
     * @param vo
     * @return
     */
    private BigDecimal exoneracionTerceraEdad(final EvaluarFormulaVO vo) {
        ParametroGlobal me = buscarParametroGlobal(ParametroGlobalEnum.TERCERA_EDAD.getCodigo(), vo.getSc());
        if (vo.getDd().getServidor().getFechaNacimiento() != null && UtilFechas.obtenerAnio(
                vo.getNomina().getPeriodoNomina().getFechaFinal())
                - UtilFechas.obtenerAnio(vo.getDd().getServidor().getFechaNacimiento())
                >= me.getValorNumerico().intValue()) {
            return vo.getImpuestoRentaExoneracion() == null ? BigDecimal.ZERO
                    : vo.getImpuestoRentaExoneracion().getFraccionBasica().multiply(
                            vo.getImpuestoRentaExoneracion().getIndiceExoneracion());
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     *
     * @param vo
     * @return
     */
    private BigDecimal exoneracionDiscapacidad(final EvaluarFormulaVO vo) {
        if (vo.getDd().getServidor().getCatalogoCapacidades() != null && !vo.getDd().getServidor().
                getCatalogoCapacidades().getCodigo().equals("NIN")) {
            for (GradoDiscapacidadIREnum en : GradoDiscapacidadIREnum.values()) {
                if (vo.getDd().getServidor().getPorcentajeDiscapacidad().intValue() >= en.getGradoMinimo()
                        && vo.getDd().getServidor().getPorcentajeDiscapacidad().intValue() <= en.getGradoMaximo()) {
                    return vo.getImpuestoRentaExoneracion() == null ? BigDecimal.ZERO
                            : vo.getImpuestoRentaExoneracion().getFraccionBasica().multiply(
                                    vo.getImpuestoRentaExoneracion().getIndiceExoneracion()).multiply(
                                    new BigDecimal(en.getPorcentajeDescontar())).divide(new BigDecimal("100"));
                }
            }
            return BigDecimal.ZERO;
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Realiza el calculo del impuesto de la renta mensual.
     *
     * @param vo
     * @return
     * @throws DaoException
     */
    private BigDecimal calcularImpuestoRentaMetodoAnual(final EvaluarFormulaVO vo) throws DaoException,
            ServicioException {
        NominaIR irDetalle = fabricaNominaIR();

        // calcular el valor de proyeccionmensual => sueldo - aporte personal.
//        BigDecimal ingresosProyeccionIR = vo.getIngresosProyeccionIR().get(vo.getDd().getServidor().getId());
        BigDecimal ingresosProyeccionIR = nominaDetalleDao.buscarRMU(vo.getNomina().getId(), vo.getDd().getServidor().
                getId());
        List<CotizacionIess> cotizaciones = cotizacionIessDao.buscarPorNemonico(vo.getDd().getEscalaOcupacional().
                getNivelOcupacional().getId());
        if (!cotizaciones.isEmpty()) {
            CotizacionIess c = cotizaciones.get(0);
            ingresosProyeccionIR = ingresosProyeccionIR.subtract(
                    ingresosProyeccionIR.multiply(c.getPorcentajeAporteIndividual()).
                    divide(new BigDecimal("100").setScale(2, RoundingMode.HALF_UP)));

        }

        irDetalle.setIngresosProyeccionMensual(ingresosProyeccionIR == null ? BigDecimal.ZERO : ingresosProyeccionIR);
        irDetalle.setServidor(vo.getDd().getServidor());
        irDetalle.setNomina(vo.getNomina());
        irDetalle.setUsuarioCreacion("");
        irDetalle.setVigente(Boolean.TRUE);
        irDetalle.setUnidadPresupuestaria(vo.getDd().getUnidadPresupuestaria());
        if (ingresosProyeccionIR != null && ingresosProyeccionIR.compareTo(BigDecimal.ZERO) > 0) {
            // calcular el total de ingresos del presente periodo (solo rubros grabables)
            BigDecimal ingresos = nominaDetalleDao.calcularTotalIngresosGravables(vo.getNomina().getId(), vo.getDd().
                    getServidor().getId());
            irDetalle.setIngresosActual(ingresos);
//            System.out.println("++ingresos-actual:" + ingresos);

            // calcula la proyecccion de impuesto a la renta.        
            // multiplicar por el numero de meses faltantes del anio.
            ingresos = ingresos.add(UtilNumeros.redondear(ingresosProyeccionIR.multiply(
                    new BigDecimal(12 - vo.getNomina().
                            getPeriodoNomina().getNumero())), 2, true));
            irDetalle.setIngresosProyeccion(UtilNumeros.redondear(ingresosProyeccionIR.multiply(
                    new BigDecimal(12 - vo.getNomina().
                            getPeriodoNomina().getNumero())), 2, true));
            irDetalle.setMesesProyeccion(new BigDecimal(12 - vo.getNomina().getPeriodoNomina().getNumero()));
//            System.out.println("++ingresos-actual-proyeccion:" + ingresos);

            // calcular el valor de la proyeccion de gastos.
            List<GastoPersonal> gastosPersonales = gastoPersonalDao.buscarTodosPorServidorYEjercicioFiscal(vo.getDd().
                    getServidor().getId(), vo.getNomina().
                    getPeriodoNomina().getEjercicioFiscalId());
            BigDecimal proyecion = BigDecimal.ZERO;
            if (!gastosPersonales.isEmpty()) {
                proyecion = proyecion.add(gastosPersonales.get(0).getTotalDeducible());
                irDetalle.setGastosPersonalAlimentacion(gastosPersonales.get(0).getAlimentacion());
                irDetalle.setGastosPersonalEducacion(gastosPersonales.get(0).getEducacion());
                irDetalle.setGastosPersonalSalud(gastosPersonales.get(0).getSalud());
                irDetalle.setGastosPersonalVestimenta(gastosPersonales.get(0).getVestimenta());
                irDetalle.setGastosPersonalVivienda(gastosPersonales.get(0).getVivienda());
                irDetalle.setGastosPersonalTotal(gastosPersonales.get(0).getTotalDeducible());
                ingresos = ingresos.add(gastosPersonales.get(0).getIngresosOtroEmpleador());
                irDetalle.setIngresosOtrosEmpleadores(gastosPersonales.get(0).getIngresosOtroEmpleador());

            }
            // calcular exoneracion tercera edad;
            BigDecimal exoneracionTerceraEdad = exoneracionTerceraEdad(vo);
            irDetalle.setExoneracionTerceraEdad(exoneracionTerceraEdad);

            // calcular exoneracion discapacitado
            BigDecimal exoneracionDiscapacidad = exoneracionDiscapacidad(vo);
            irDetalle.setExoneracionDiscapacidad(exoneracionDiscapacidad);

            // calcular el total neto de ingresos de los meses anteriores a periodo actua.
            BigDecimal ingresosAnteriores = vistaNominaIRDao.calcularTotalIngresosGravables(
                    vo.getNomina().getPeriodoNomina().
                    getEjercicioFiscal(), vo.getDd().getServidor(),
                    vo.getNomina().getPeriodoNomina().getNumero() - 1);
            irDetalle.setIngresosAnterior(ingresosAnteriores);
//            System.out.println("++ingresos-anterior:" + ingresosAnteriores);

            // obtener el saldo de ingresos.
            BigDecimal saldo = ingresos.add(ingresosAnteriores.subtract(proyecion)).
                    subtract(exoneracionDiscapacidad).subtract(exoneracionTerceraEdad);
            if (saldo.compareTo(BigDecimal.ZERO) < 0) {
                saldo = BigDecimal.ZERO;
            }
            irDetalle.setSaldo(saldo);

            // verificar la ubicacion en la tabla de IR.
            ImpuestoRenta ir = impuestoRentaDao.buscarPorEscala(
                    vo.getNomina().getPeriodoNomina().getEjercicioFiscalId(),
                    saldo.divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP));

            // realizar el calculo del IR.
            if (ir == null) {
                System.out.println("No existe escala de impuesto a la renta para:" + saldo);
                return BigDecimal.ZERO;
                //throw new ServicioException("No existe escala de impuesto a la renta para:" + saldo);

            }
            irDetalle.setPorcentajeImpuestoFraccionExcedente(ir.getPorcentajeImpuestoFraccionExcedente());
            irDetalle.setFraccionBasica(ir.getFraccionBasica());
            irDetalle.setPorcentajeImpuestoSobreFraccionBasica(ir.getPorcentajeImpuestoSobreFraccionBasica());

            BigDecimal valor = ir.getPorcentajeImpuestoSobreFraccionBasica().add(saldo.subtract(ir.getFraccionBasica()).
                    multiply(ir.getPorcentajeImpuestoFraccionExcedente()).divide(new BigDecimal("100")));
            // calcular el ir retenido anteriormente
            BigDecimal irRetenido = nominaDetalleDao.calcularIRRetenido(vo.getNomina().getPeriodoNomina().
                    getEjercicioFiscal(), vo.getDd().getServidor(), vo.getNomina().getPeriodoNomina().getNumero() - 1);
            irDetalle.setIrRetenido(irRetenido);

            // verificar el total de ir calculado.
            valor = valor.subtract(irRetenido);
            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                valor = BigDecimal.ZERO;
            }

            int meses = 12 - vo.getNomina().getPeriodoNomina().getNumero().intValue() + 1;
            irDetalle.setIrCausado(UtilNumeros.redondear(valor, 2, true));
            irDetalle.setIrCausadoMensual(UtilNumeros.redondear(valor.divide(
                    new BigDecimal(meses), RoundingMode.HALF_UP), 2, true));
            nominaIRDao.crear(irDetalle);
            return UtilNumeros.redondear(valor.divide(new BigDecimal(meses), RoundingMode.HALF_UP), 2, true);
        } else {
            nominaIRDao.crear(irDetalle);
//            System.out.println("INGRESOS CERO:" + vo.getDd().getServidor().getNumeroIdentificacion());
            return BigDecimal.ZERO;
        }

    }

    /**
     *
     * @return
     */
    private NominaIR fabricaNominaIR() {
        NominaIR ir = new NominaIR();
        ir.setExoneracionDiscapacidad(BigDecimal.ZERO);
        ir.setExoneracionTerceraEdad(BigDecimal.ZERO);
        ir.setFechaCreacion(new Date());
        ir.setGastosPersonalAlimentacion(BigDecimal.ZERO);
        ir.setGastosPersonalEducacion(BigDecimal.ZERO);
        ir.setGastosPersonalSalud(BigDecimal.ZERO);
        ir.setGastosPersonalTotal(BigDecimal.ZERO);
        ir.setGastosPersonalVestimenta(BigDecimal.ZERO);
        ir.setGastosPersonalVivienda(BigDecimal.ZERO);
        ir.setIngresosActual(BigDecimal.ZERO);
        ir.setIngresosAnterior(BigDecimal.ZERO);
        ir.setIngresosProyeccion(BigDecimal.ZERO);
        ir.setIngresosProyeccionMensual(BigDecimal.ZERO);
        ir.setIrCausado(BigDecimal.ZERO);
        ir.setIrCausadoMensual(BigDecimal.ZERO);
        ir.setIrRetenido(BigDecimal.ZERO);
        ir.setMesesProyeccion(BigDecimal.ZERO);
        ir.setRmu(BigDecimal.ZERO);
        ir.setSaldo(BigDecimal.ZERO);

        return ir;
    }

    /**
     *
     * @param fechaInicio
     * @param fechaFinal
     * @return
     */
    private boolean validarSiMesCompleto(Date fechaInicio, Date fechaFinal) {
        return UtilFechas.obtenerDiaDelMes(fechaInicio) == 1
                && Objects.equals(UtilFechas.obtenerDiaDelMes(fechaFinal), UtilFechas.obtenerUltimoDiaDelMes(fechaFinal));
    }

}
