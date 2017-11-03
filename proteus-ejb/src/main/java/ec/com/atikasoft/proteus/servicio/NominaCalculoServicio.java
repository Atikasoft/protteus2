/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AnticipoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPagoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPlanPagoDao;
import ec.com.atikasoft.proteus.dao.BeneficiarioEspecialDao;
import ec.com.atikasoft.proteus.dao.CuentaBancariaDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.LiquidacionDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleNovedadDao;
import ec.com.atikasoft.proteus.dao.NominaIRDao;
import ec.com.atikasoft.proteus.dao.NominaPagoDao;
import ec.com.atikasoft.proteus.dao.NovedadDetalleDao;
import ec.com.atikasoft.proteus.dao.TerceroNominaDetalleDao;
import ec.com.atikasoft.proteus.enums.CoberturaNominaEnum;
import ec.com.atikasoft.proteus.enums.EstadoPlanPagoEnum;
import ec.com.atikasoft.proteus.enums.PeriodicidadOcurrenciaNominaEnum;
import ec.com.atikasoft.proteus.enums.TipoBeneficiarioEnum;
import ec.com.atikasoft.proteus.enums.TipoProblemaEnum;
import ec.com.atikasoft.proteus.enums.TipoRubroEnum;
import ec.com.atikasoft.proteus.enums.UsoRubroEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.FormulaException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Anticipo;
import ec.com.atikasoft.proteus.modelo.AnticipoPago;
import ec.com.atikasoft.proteus.modelo.AnticipoPlanPago;
import ec.com.atikasoft.proteus.modelo.BeneficiarioEspecial;
import ec.com.atikasoft.proteus.modelo.BeneficiarioInstitucional;
import ec.com.atikasoft.proteus.modelo.CuentaBancaria;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.NominaDetalle;
import ec.com.atikasoft.proteus.modelo.NominaPago;
import ec.com.atikasoft.proteus.modelo.NovedadDetalle;
import ec.com.atikasoft.proteus.modelo.Rubro;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.UnidadPresupuestaria;
import ec.com.atikasoft.proteus.modelo.nomina.NominaDetalleNovedad;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilNumeros;
import ec.com.atikasoft.proteus.vo.BeneficiarioInstitucionalNominaVO;
import ec.com.atikasoft.proteus.vo.EvaluarFormulaVO;
import ec.com.atikasoft.proteus.vo.NominaPagoVO;
import ec.com.atikasoft.proteus.vo.ObjetoNominaVO;
import ec.com.atikasoft.proteus.vo.PersonaNominaVO;
import ec.com.atikasoft.proteus.vo.ResultadoFormulaVO;
import ec.com.atikasoft.proteus.vo.ResultadoRecuperacionAnticipoVO;
import ec.com.atikasoft.proteus.vo.RubroNominaVO;
import ec.com.atikasoft.proteus.vo.ServidorNominaVO;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javolution.util.FastMap;
import javolution.util.FastTable;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author henry
 */
@Stateless
@LocalBean
public class NominaCalculoServicio extends BaseServicio {

    /**
     *
     */
    @EJB
    private NominaDetalleNovedadDao nominaDetalleNovedadDao;

    /**
     *
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     *
     */
    @EJB
    private FormulaServicio formulaServicio;

    /**
     *
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     *
     */
    @EJB
    private BeneficiarioEspecialDao beneficiarioEspecialDao;

    /**
     *
     */
    @EJB
    private TerceroNominaDetalleDao terceroNominaDetalleDao;

    /**
     *
     */
    @EJB
    private ProblemaServicio problemaServicio;

    /**
     *
     */
    @EJB
    private LiquidacionDao liquidacionDao;

    /**
     *
     */
    @EJB
    private AnticipoPlanPagoDao anticipoPlanPagoDao;

    /**
     *
     */
    @EJB
    private AnticipoPagoDao anticipoPagoDao;

    /**
     *
     */
    @EJB
    private AnticipoServicio anticipoServicio;

    /**
     *
     */
    @EJB
    private AnticipoDao anticipoDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     *
     */
    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    /**
     *
     */
    @EJB
    private NominaPagoDao nominaPagoDao;

    /**
     *
     */
    @EJB
    private NominaIRDao nominaIRDao;

    /**
     *
     */
    @EJB
    private NominaEliminacionServicio nominaEliminacionServicio;

    /**
     *
     * @param puestos
     * @param objeto
     * @param lote
     * @throws DaoException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calcularServidoresSegmentoFase1(final List<DistributivoDetalle> puestos, final ObjetoNominaVO objeto,
            final int lote) throws DaoException, IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {
        ObjetoNominaVO obj = (ObjetoNominaVO) BeanUtils.cloneBean(objeto);
        int i = 1;
        Map<String, NominaDetalle> detalles = new FastMap<>();
        for (DistributivoDetalle dd : puestos) {
            try {
                // validar que el servidor se encuentre activo en el distributivo.
//                System.out.println("Calculando:"+dd.getServidor().getApellidosNombres());
//                BigDecimal ingresosProyeccionIR = BigDecimal.ZERO;
//                obj.setIngresosProyeccionIR(ingresosProyeccionIR);
                // TODO validar fondos disponibles.
                // TODO validar si tiene la informacion para el SIPARI de acuerdo a su unidad organizacional.
                obj.setDistributivoDetalle(dd);
                obj.setBeneficiariosNominaVO(new FastTable<PersonaNominaVO>());
                obj.setRubro(null);
                obj.setListaNovedadDetalle(new FastTable<NovedadDetalle>());
                PersonaNominaVO per = new PersonaNominaVO();
                obj.setPersonaNominaVO(per);
                per.setDistributivoDetalle(dd);
                per.setTipoDocumento(dd.getServidor().getTipoIdentificacion());
                per.setNumeroDocumento(dd.getServidor().getNumeroIdentificacion());
                per.setTipoDocumentoOrigen(dd.getServidor().getTipoIdentificacion());
                per.setNumeroDocumentoOrigen(dd.getServidor().getNumeroIdentificacion());
                per.setNombres(dd.getServidor().getApellidosNombres());
                per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
                per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
                per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());
                if (estanActivoEnPeriodo(obj, dd)) {
                    if (!obj.getEjecucionNominaVO().getTodos()) {
                        eliminarDetallesNomina(objeto, dd);
                    }
                    // Fase 1
                    for (Rubro r : obj.getRubros()) {
                        if (r.getVigente() && r.getFase() == 1) {
                            obj.setRubro(r);
                            calcularRubro(obj);
                        }
                    }
                    registrarDetalles(obj, detalles);

                } else {
                    problemaServicio.registrarProblema(obj,
                            TipoProblemaEnum.SERVIDOR_NO_ENCUENTRA_PERIODO_CONTRATACION.getId(),
                            UtilFechas.formatear(dd.getServidor().getFechaIngresoInstitucion()),
                            dd.getMovimiento() == null ? UtilFechas.formatear(dd.getFechaFin())
                                    : UtilFechas.formatear(dd.getMovimiento().getFechaHasta()));
                }

            } catch (FormulaException e) {
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_SINTAXIS_FORMULA.getId());
            } catch (Exception e) {
                e.printStackTrace();
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_DESCONOCIDO.getId(), e.getMessage());
//                break;
            }
            // bulk insert.
            if (i % 100 == 0) {
                System.out.println("LOTE :" + lote + ":" + i);
                nominaDetalleDao.clear();
                nominaDetalleDao.flush();
            }
            i++;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calcularServidoresSegmentoFase2(final List<DistributivoDetalle> puestos, final ObjetoNominaVO objeto,
            final int lote) throws DaoException, IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {
        ObjetoNominaVO obj = (ObjetoNominaVO) BeanUtils.cloneBean(objeto);
        int i = 1;
        Map<String, NominaDetalle> detalles = new FastMap<>();
        for (DistributivoDetalle dd : puestos) {
            try {
                // validar que el servidor se encuentre activo en el distributivo.
//                System.out.println("Calculando:"+dd.getServidor().getApellidosNombres());
//                BigDecimal ingresosProyeccionIR = BigDecimal.ZERO;
//                obj.setIngresosProyeccionIR(ingresosProyeccionIR);
                // TODO validar fondos disponibles.
                // TODO validar si tiene la informacion para el SIPARI de acuerdo a su unidad organizacional.
                obj.setDistributivoDetalle(dd);
                obj.setBeneficiariosNominaVO(new FastTable<PersonaNominaVO>());
                obj.setRubro(null);
                obj.setListaNovedadDetalle(new FastTable<NovedadDetalle>());
                PersonaNominaVO per = new PersonaNominaVO();
                obj.setPersonaNominaVO(per);
                per.setDistributivoDetalle(dd);
                per.setTipoDocumento(dd.getServidor().getTipoIdentificacion());
                per.setNumeroDocumento(dd.getServidor().getNumeroIdentificacion());
                per.setTipoDocumentoOrigen(dd.getServidor().getTipoIdentificacion());
                per.setNumeroDocumentoOrigen(dd.getServidor().getNumeroIdentificacion());
                per.setNombres(dd.getServidor().getApellidosNombres());
                per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
                per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
                per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());

                // Fase 2
                per.getListaRubrosAportes().clear();
                per.getListaRubrosDescuentos().clear();
                per.getListaRubrosIngresos().clear();
                obj.getBeneficiariosNominaVO().clear();
                obj.getBeneficiariosInstitucionales().clear();
                obj.getListaNovedadDetalle().clear();
                if (estanActivoEnPeriodo(obj, dd)) {
                    for (Rubro r : obj.getRubros()) {
                        if (r.getVigente() && r.getFase() == 2) {
                            obj.setRubro(r);
                            calcularRubro(obj);
                        }
                    }
                    registrarDetalles(obj, detalles);
                }

            } catch (FormulaException e) {
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_SINTAXIS_FORMULA.getId());
            } catch (Exception e) {
                e.printStackTrace();
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_DESCONOCIDO.getId(), e.getMessage());
//                break;
            }
            // bulk insert.
            if (i % 100 == 0) {
                System.out.println("LOTE :" + lote + ":" + i);
                nominaDetalleDao.clear();
                nominaDetalleDao.flush();
            }
            i++;
        }
    }

    /**
     *
     * @param liquidaciones
     * @param objeto
     * @param lote
     * @throws DaoException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calcularLiquidacionesSegmento(final List<Liquidacion> liquidaciones, final ObjetoNominaVO objeto,
            final int lote) throws DaoException, IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {
        ObjetoNominaVO obj = (ObjetoNominaVO) BeanUtils.cloneBean(objeto);
        int i = 1;
        Map<String, NominaDetalle> detalles = new FastMap<String, NominaDetalle>();
        for (Liquidacion liquidacion : liquidaciones) {
            try {
//                System.out.println("Calculando:"+dd.getServidor().getApellidosNombres());
                obj.setBeneficiariosNominaVO(new FastTable<PersonaNominaVO>());
                obj.setRubro(null);
                obj.setDistributivoDetalle(liquidacion.getDistributivoDetalle());
                obj.setListaNovedadDetalle(new FastTable<NovedadDetalle>());
                obj.setLiquidacion(liquidacion);
                PersonaNominaVO per = new PersonaNominaVO();
                obj.setPersonaNominaVO(per);
                per.setDistributivoDetalle(liquidacion.getDistributivoDetalle());
                per.setTipoDocumento(liquidacion.getServidor().getTipoIdentificacion());
                per.setNumeroDocumento(liquidacion.getServidor().getNumeroIdentificacion());
                per.setTipoDocumentoOrigen(liquidacion.getServidor().getTipoIdentificacion());
                per.setNumeroDocumentoOrigen(liquidacion.getServidor().getNumeroIdentificacion());
                per.setNombres(liquidacion.getServidor().getApellidosNombres());
                per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
                per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
                per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());
                if (!obj.getEjecucionNominaVO().getTodos()) {
                    eliminarDetallesNomina(objeto, liquidacion.getDistributivoDetalle());
                }
                // Fase 1
                for (Rubro r : obj.getRubros()) {
                    if (r.getVigente() && r.getFase() == 1) {
                        obj.setRubro(r);
                        calcularRubro(obj);
                    }
                }
                registrarDetalles(obj, detalles);

                // Fase 2
                per.getListaRubrosAportes().clear();
                per.getListaRubrosDescuentos().clear();
                per.getListaRubrosIngresos().clear();
                obj.getBeneficiariosNominaVO().clear();
                obj.getBeneficiariosInstitucionales().clear();
                obj.getListaNovedadDetalle().clear();
                for (Rubro r : obj.getRubros()) {
                    if (r.getVigente() && r.getFase() == 2) {
                        obj.setRubro(r);
                        calcularRubro(obj);
                    }
                }
                registrarDetalles(obj, detalles);

            } catch (FormulaException e) {
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_SINTAXIS_FORMULA.getId());
            } catch (Exception e) {
                e.printStackTrace();
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_DESCONOCIDO.getId(), e.getMessage());
            }
            liquidacion.setNominaId(objeto.getNomina().getId());
            liquidacion.setUsuarioActualizacion(objeto.getUsuario().getUsuario());
            liquidacion.setFechaActualizacion(new Date());
            liquidacionDao.actualizar(liquidacion);
            // bulk insert.
            if (i % 100 == 0) {
                System.out.println("LOTE :" + lote + ":" + i);
                nominaDetalleDao.clear();
                nominaDetalleDao.flush();
            }
            i++;
        }
    }

    /**
     *
     * @param anticipos
     * @param objeto
     * @param lote
     * @throws DaoException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.InstantiationException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.NoSuchMethodException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void calcularAnticiposSegmento(final List<Anticipo> anticipos, final ObjetoNominaVO objeto,
            final int lote) throws DaoException, IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {
        ObjetoNominaVO obj = (ObjetoNominaVO) BeanUtils.cloneBean(objeto);
        int i = 1;
        Map<String, NominaDetalle> detalles = new FastMap<String, NominaDetalle>();
        for (Anticipo anticipo : anticipos) {
            try {
                DistributivoDetalle dd = distributivoDetalleDao.buscarPorServidor(anticipo.getServidorId()).get(0);
//                BigDecimal ingresosProyeccionIR = BigDecimal.ZERO;
//                obj.setIngresosProyeccionIR(ingresosProyeccionIR);
                obj.setBeneficiariosNominaVO(new FastTable<PersonaNominaVO>());
                obj.setRubro(null);
                obj.setDistributivoDetalle(dd);
                obj.setListaNovedadDetalle(new FastTable<NovedadDetalle>());
                obj.setAnticipo(anticipo);
                PersonaNominaVO per = new PersonaNominaVO();
                obj.setPersonaNominaVO(per);
                per.setDistributivoDetalle(dd);
                per.setTipoDocumento(anticipo.getServidor().getTipoIdentificacion());
                per.setNumeroDocumento(anticipo.getServidor().getNumeroIdentificacion());
                per.setTipoDocumentoOrigen(anticipo.getServidor().getTipoIdentificacion());
                per.setNumeroDocumentoOrigen(anticipo.getServidor().getNumeroIdentificacion());
                per.setNombres(anticipo.getServidor().getApellidosNombres());
                per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
                per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
                per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());
                if (estanActivoEnPeriodo(obj, dd)) {
                    if (!obj.getEjecucionNominaVO().getTodos()) {
                        eliminarDetallesNomina(objeto, dd);
                    }
                    // Fase 1
                    for (Rubro r : obj.getRubros()) {
                        if (r.getVigente() && r.getFase() == 1) {
                            obj.setRubro(r);
                            calcularRubro(obj);
                        }
                    }
                    registrarDetalles(obj, detalles);

                    // Fase 2
                    per.getListaRubrosAportes().clear();
                    per.getListaRubrosDescuentos().clear();
                    per.getListaRubrosIngresos().clear();
                    obj.getBeneficiariosNominaVO().clear();
                    obj.getBeneficiariosInstitucionales().clear();
                    obj.getListaNovedadDetalle().clear();
                    for (Rubro r : obj.getRubros()) {
                        if (r.getVigente() && r.getFase() == 2) {
                            obj.setRubro(r);
                            calcularRubro(obj);
                        }
                    }
                    registrarDetalles(obj, detalles);
                } else {
                    problemaServicio.registrarProblema(objeto, TipoProblemaEnum.SERVIDOR_NO_ENCUENTRA_PERIODO_CONTRATACION.getId(),
                            UtilFechas.formatear(dd.getServidor().getFechaIngresoInstitucion()), UtilFechas.formatear(dd.getMovimiento().getFechaHasta()));
                }

            } catch (FormulaException e) {
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_SINTAXIS_FORMULA.getId());
            } catch (Exception e) {
                e.printStackTrace();
                problemaServicio.registrarProblema(obj, TipoProblemaEnum.ERROR_DESCONOCIDO.getId(), e.getMessage());
            }
            anticipo.setNominaId(objeto.getNomina().getId());
            anticipo.setUsuarioActualizacion(objeto.getUsuario().getUsuario());
            anticipo.setFechaActualizacion(new Date());
            anticipoDao.actualizar(anticipo);
            // bulk insert.
            if (i % 100 == 0) {
                System.out.println("LOTE :" + lote + ":" + i);
                nominaDetalleDao.clear();
                nominaDetalleDao.flush();
            }
            i++;
        }
    }

    /**
     * Calcula el valor de un rubro.
     *
     * @param rubro
     * @param personas
     */
    private void calcularRubro(final ObjetoNominaVO objeto) throws FormulaException, ServicioException, DaoException {
        if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo())) {
            calcularRubroIngreso(objeto);
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.DESCUENTOS.getCodigo())) {
            calcularRubroDescuento(objeto);
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.APORTE_INSTITUCIONAL.getCodigo())) {
            calcularRubroAportes(objeto);
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
            calcularRubroIngresoAnticipo(objeto);
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.RECUPERACION_ANTICIPOS.getCodigo())) {
            calcularRubroDescuentoAnticipo(objeto);
        }
    }

    /**
     * Calcula los rubros de ingresos.
     *
     * @param rubro
     * @param persona
     */
    private void calcularRubroIngreso(final ObjetoNominaVO objeto) throws FormulaException, ServicioException,
            DaoException {
        RubroNominaVO rub = new RubroNominaVO();
        BigDecimal valor = BigDecimal.ZERO;
        if (UsoRubroEnum.FORMULA.getCodigo().equals(objeto.getRubro().getUso())) {
            EvaluarFormulaVO vo = new EvaluarFormulaVO();
            vo.setLiquidacion(objeto.getLiquidacion());
            vo.setFormula(objeto.getRubro().getFormula());
            vo.setPn(objeto.getNomina().getPeriodoNomina());
            vo.setNomina(objeto.getNomina());
            vo.setSc(objeto.getServletContext());
            vo.setDd(objeto.getDistributivoDetalle());
            vo.setIngresosProyeccionIR(objeto.getIngresosProyeccionIR());
            vo.setImpuestoRentaExoneracion(objeto.getImpuestoRentaExoneracion());
            vo.setTablaImpuestoRenta(objeto.getTablaImpuestoRenta());
            ResultadoFormulaVO r = formulaServicio.evaluarFormula(vo);
            valor = r.getValor();
            rub.setValores(r.getValores());
            objeto.getListaNovedadDetalle().addAll(vo.getNovedadDetalles());
        } else if (UsoRubroEnum.DATO_ADICIONAL.getCodigo().equals(objeto.getRubro().getUso())) {
            List<NovedadDetalle> novedades = buscarDatosAdicionales(objeto);
            for (NovedadDetalle novedad : novedades) {
                RubroNominaVO rubNovedad = new RubroNominaVO();
                calcularRubroIngresoRegistro(null, novedad, rubNovedad, objeto);
            }
            return;
        }
        calcularRubroIngresoRegistro(valor, null, rub, objeto);
    }

    /**
     *
     * @param valor
     * @param rub
     * @param objeto
     */
    private void calcularRubroIngresoRegistro(BigDecimal valor, NovedadDetalle novedad, final RubroNominaVO rub, final ObjetoNominaVO objeto) {
        BigDecimal valorLocal = BigDecimal.ZERO;
        if (valor != null) {
            valorLocal = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (novedad != null) {
            valorLocal = novedad.getValor().setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (valorLocal.compareTo(BigDecimal.ZERO) == 1) {
            rub.setNovedadDetalle(novedad);
            rub.setRubro(objeto.getRubro());
            rub.setValorRubro(valorLocal);
            rub.setBeneficiarioEspecial(Boolean.FALSE);
            rub.setListaNovedadDetalle(new ArrayList<NovedadDetalle>());
            if (objeto.getListaNovedadDetalle() != null) {
                rub.getListaNovedadDetalle().addAll(objeto.getListaNovedadDetalle());
                objeto.getListaNovedadDetalle().clear();
            }
            if (rub.getRubro().getEsProyeccionImpuestoRenta()) {
                BigDecimal valorProyeccionIR = BigDecimal.ZERO;
                if (objeto.getIngresosProyeccionIR().containsKey(objeto.getDistributivoDetalle().getServidor().getId())) {
                    valorProyeccionIR = objeto.getIngresosProyeccionIR().get(objeto.getDistributivoDetalle().getServidor().getId());
                }
                objeto.getIngresosProyeccionIR().put(objeto.getDistributivoDetalle().getServidor().getId(),
                        valorProyeccionIR.add(valorLocal));

            }
            objeto.getPersonaNominaVO().getListaRubrosIngresos().add(rub);
        }

    }

    /**
     * Calcula los rubros de descuentos.
     *
     * @param rubro
     * @param persona
     */
    private void calcularRubroDescuento(final ObjetoNominaVO objeto) throws FormulaException, DaoException,
            ServicioException {
        RubroNominaVO rub = new RubroNominaVO();
        BigDecimal valor = BigDecimal.ZERO;
        if (!objeto.getRubro().getBeneficiarioUnico() && objeto.getRubro().getTipoBeneficiario() != null && objeto.getRubro().getTipoBeneficiario().equals(TipoBeneficiarioEnum.ESPECIAL.
                getCodigo())) {
            // calcula descuento por beneficiarios especiales.
            if (objeto.getDistributivoDetalle() != null) {
                valor = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
                rub.setRubro(objeto.getRubro());
                rub.setValorRubro(valor);
                rub.setBeneficiarioEspecial(Boolean.TRUE);
                objeto.getPersonaNominaVO().getListaRubrosDescuentos().add(rub);
            }
        } else {
            if (UsoRubroEnum.FORMULA.getCodigo().equals(objeto.getRubro().getUso())) {
                EvaluarFormulaVO vo = new EvaluarFormulaVO();
                vo.setLiquidacion(objeto.getLiquidacion());
                vo.setFormula(objeto.getRubro().getFormula());
                vo.setPn(objeto.getNomina().getPeriodoNomina());
                vo.setNomina(objeto.getNomina());
                vo.setSc(objeto.getServletContext());
                vo.setDd(objeto.getDistributivoDetalle());
                vo.setIngresosProyeccionIR(objeto.getIngresosProyeccionIR());
                vo.setImpuestoRentaExoneracion(objeto.getImpuestoRentaExoneracion());
                vo.setTablaImpuestoRenta(objeto.getTablaImpuestoRenta());

                vo.getNovedadDetalles();
                ResultadoFormulaVO r = formulaServicio.evaluarFormula(vo);
                valor = r.getValor();
                objeto.getListaNovedadDetalle().addAll(vo.getNovedadDetalles());
                rub.setValores(r.getValores());
            } else if (UsoRubroEnum.DATO_ADICIONAL.getCodigo().equals(objeto.getRubro().getUso())) {
                List<NovedadDetalle> novedades = buscarDatosAdicionales(objeto);
                for (NovedadDetalle novedad : novedades) {
                    RubroNominaVO rubNovedad = new RubroNominaVO();
                    calculoRubroDescuentoRegistro(null, novedad, rubNovedad, objeto);
                }
                return;
            }
            calculoRubroDescuentoRegistro(valor, null, rub, objeto);
        }
    }

    /**
     *
     * @param valor
     * @param rub
     * @param objeto
     */
    private void calculoRubroDescuentoRegistro(BigDecimal valor, NovedadDetalle novedad, final RubroNominaVO rub, final ObjetoNominaVO objeto) {
        BigDecimal valorLocal = BigDecimal.ZERO;
        if (valor != null) {
            valorLocal = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (novedad != null) {
            valorLocal = novedad.getValor().setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (valorLocal.compareTo(BigDecimal.ZERO) == 1) {
            rub.setNovedadDetalle(novedad);
            rub.setRubro(objeto.getRubro());
            rub.setValorRubro(valorLocal);
            rub.setBeneficiarioEspecial(Boolean.FALSE);
            rub.setListaNovedadDetalle(new ArrayList<NovedadDetalle>());
            if (objeto.getListaNovedadDetalle() != null) {
                rub.getListaNovedadDetalle().addAll(objeto.getListaNovedadDetalle());
                objeto.getListaNovedadDetalle().clear();
            }
            objeto.getPersonaNominaVO().getListaRubrosDescuentos().add(rub);
            if (rub.getRubro().getEsProyeccionImpuestoRenta()) {
                BigDecimal valorProyeccionIR = BigDecimal.ZERO;
                if (objeto.getIngresosProyeccionIR().containsKey(objeto.getDistributivoDetalle().getServidor().getId())) {
                    valorProyeccionIR = objeto.getIngresosProyeccionIR().get(objeto.getDistributivoDetalle().getServidor().getId());
                }
                objeto.getIngresosProyeccionIR().put(objeto.getDistributivoDetalle().getServidor().getId(), valorProyeccionIR.subtract(valorLocal));
            }
        }

    }

    /**
     * Calcula los rubros de aportes.
     *
     * @param rubro
     * @param persona
     */
    private void calcularRubroAportes(final ObjetoNominaVO objeto) throws FormulaException, DaoException,
            ServicioException {
        RubroNominaVO rub = new RubroNominaVO();
        BigDecimal valor = BigDecimal.ZERO;
        if (UsoRubroEnum.FORMULA.getCodigo().equals(objeto.getRubro().getUso())) {
            EvaluarFormulaVO vo = new EvaluarFormulaVO();
            vo.setLiquidacion(objeto.getLiquidacion());
            vo.setFormula(objeto.getRubro().getFormula());
            vo.setPn(objeto.getNomina().getPeriodoNomina());
            vo.setNomina(objeto.getNomina());
            vo.setSc(objeto.getServletContext());
            vo.setDd(objeto.getDistributivoDetalle());
            vo.setIngresosProyeccionIR(objeto.getIngresosProyeccionIR());
            vo.setImpuestoRentaExoneracion(objeto.getImpuestoRentaExoneracion());
            vo.setTablaImpuestoRenta(objeto.getTablaImpuestoRenta());

            ResultadoFormulaVO r = formulaServicio.evaluarFormula(vo);
            valor = r.getValor();
            rub.setValores(r.getValores());
            objeto.getListaNovedadDetalle().addAll(vo.getNovedadDetalles());
        } else if (UsoRubroEnum.DATO_ADICIONAL.getCodigo().equals(objeto.getRubro().getUso())) {
            List<NovedadDetalle> novedades = buscarDatosAdicionales(objeto);
            for (NovedadDetalle novedad : novedades) {
                RubroNominaVO rubNovedad = new RubroNominaVO();
                calcularRubroAportesRegistro(null, novedad, rubNovedad, objeto);
            }
            return;
        }
        calcularRubroAportesRegistro(valor, null, rub, objeto);
    }

    /**
     *
     * @param valor
     * @param rub
     * @param objeto
     */
    private void calcularRubroAportesRegistro(BigDecimal valor, NovedadDetalle novedad, RubroNominaVO rub, final ObjetoNominaVO objeto) {
        BigDecimal valorLocal = BigDecimal.ZERO;
        if (valor != null) {
            valorLocal = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (novedad != null) {
            valorLocal = novedad.getValor().setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (valorLocal.compareTo(BigDecimal.ZERO) == 1) {
            rub.setNovedadDetalle(novedad);
            rub.setRubro(objeto.getRubro());
            rub.setValorRubro(valorLocal);
            rub.setBeneficiarioEspecial(Boolean.FALSE);
            rub.setListaNovedadDetalle(new ArrayList<NovedadDetalle>());
            if (objeto.getListaNovedadDetalle() != null) {
                rub.getListaNovedadDetalle().addAll(objeto.getListaNovedadDetalle());
                objeto.getListaNovedadDetalle().clear();
            }
            objeto.getPersonaNominaVO().getListaRubrosAportes().add(rub);
        }

    }

    /**
     * Realiza descuento de cuotas por anticipos otorgados.
     *
     * @param objeto
     */
    private void calcularRubroDescuentoAnticipo(final ObjetoNominaVO objeto) throws FormulaException, ServicioException, DaoException {
        RubroNominaVO rub = new RubroNominaVO();
        BigDecimal valor = BigDecimal.ZERO;
        if (UsoRubroEnum.NINGUNO.getCodigo().equals(objeto.getRubro().getUso())) {
            ResultadoRecuperacionAnticipoVO r = buscarPagosAnticiposPendientes(objeto);
            valor = r.getValor();
            rub.setValores(r.getValores());
        }
        valor = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (valor.compareTo(BigDecimal.ZERO) == 1) {
            rub.setRubro(objeto.getRubro());
            rub.setValorRubro(valor);
            rub.setBeneficiarioEspecial(Boolean.FALSE);
            rub.setListaAnticiposPlanPagos(new ArrayList<AnticipoPlanPago>());
            if (objeto.getListaAnticiposPlanPagos() != null) {
                rub.getListaAnticiposPlanPagos().addAll(objeto.getListaAnticiposPlanPagos());
                objeto.getListaAnticiposPlanPagos().clear();
            }
            objeto.getPersonaNominaVO().getListaRubrosDescuentos().add(rub);
        }
    }

    /**
     * Realiza descuento de cuotas por anticipos otorgados.
     *
     * @param objeto
     */
    private void calcularRubroIngresoAnticipo(final ObjetoNominaVO objeto) throws FormulaException, ServicioException, DaoException {
        RubroNominaVO rub = new RubroNominaVO();
        BigDecimal valor = BigDecimal.ZERO;
        if (UsoRubroEnum.NINGUNO.getCodigo().equals(objeto.getRubro().getUso())) {
            valor = objeto.getAnticipo().getValor();
            rub.getValores().put("ANTICIPO #", objeto.getAnticipo().getNumero());
        }
        valor = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (valor.compareTo(BigDecimal.ZERO) == 1) {
            rub.setRubro(objeto.getRubro());
            rub.setValorRubro(valor);
            rub.setBeneficiarioEspecial(Boolean.FALSE);
            rub.getListaAnticipos().add(objeto.getAnticipo());
            objeto.getPersonaNominaVO().getListaRubrosIngresos().add(rub);
        }
    }

    /**
     *
     * @param objeto
     * @param detalles
     * @param ingresosProyeccionIR
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    private void registrarDetalles(final ObjetoNominaVO objeto, final Map<String, NominaDetalle> detalles) throws DaoException, ServicioException {
        List<RubroNominaVO> rubros = new FastTable<RubroNominaVO>();
        rubros.addAll(objeto.getPersonaNominaVO().getListaRubrosIngresos());
        rubros.addAll(objeto.getPersonaNominaVO().getListaRubrosAportes());
        rubros.addAll(objeto.getPersonaNominaVO().getListaRubrosDescuentos());
        detalles.clear();
        for (RubroNominaVO r : rubros) {
            objeto.setRubro(r.getRubro());
            List<NominaDetalle> nds = registrarDetalleServidores(objeto, objeto.getPersonaNominaVO(), r);
            for (NominaDetalle nd : nds) {
                detalles.put(r.getRubro().getCodigo(), nd);
            }
        }
    }

    /**
     * Registra el detalle de la nomina.
     *
     * @param objeto
     * @param rubro
     * @param tipo
     * @throws NumberFormatException
     * @throws BridgeExcepcion
     */
    private List<NominaDetalle> registrarDetalleServidores(final ObjetoNominaVO objeto, final PersonaNominaVO p,
            final RubroNominaVO rubro) throws DaoException, ServicioException {
        List<NominaDetalle> nd = new FastTable<NominaDetalle>();
        if (objeto.getRubro().getTipo().equals(TipoRubroEnum.DESCUENTOS.getCodigo())) {
            nd.addAll(registrarDetalleDescuentos(objeto, p, rubro, "SER"));
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.RECUPERACION_ANTICIPOS.getCodigo())) {
            nd.addAll(registrarDetalleDescuentos(objeto, p, rubro, "SER"));
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo())) {
            nd.add(registrarDetalleIngresos(objeto, p, rubro, "SER"));
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.APORTE_INSTITUCIONAL.getCodigo())) {
            nd.add(registrarDetalleAportes(objeto, p, rubro, "SER"));
        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
            nd.add(registrarDetalleIngresos(objeto, p, rubro, "SER"));
        }
        return nd;
    }

    /**
     * Registra los ingresos.
     *
     * @param objeto Datos de la nomina.
     * @param p Datos de la persona.
     * @param rubro Datos del rubro.
     * @param tipo Tipo (servidor / pasivos / beneficiario).
     * @throws BridgeExcepcion Error en ejecucion.
     */
    private NominaDetalle registrarDetalleIngresos(final ObjetoNominaVO objeto, final PersonaNominaVO p,
            final RubroNominaVO rubro, final String tipo) throws DaoException {
        NominaDetalle d = new NominaDetalle();
        d.setRubroIngresoId(objeto.getRubro().getId());
        d.setCodigoRubroIngreso(objeto.getRubro().getCodigo());
        d.setNombreRubroIngreso(objeto.getRubro().getNombre());
        d.setNomina(objeto.getNomina());
        if (objeto.getDistributivoDetalle() == null) {
            d.setCodigoModalidadLaboral("");
            d.setNombreModalidadLaboral("");
            d.setCodigoUnidadOrganizacional("");
            d.setNombreUnidadOrganizacional("");
            d.setPartidaIndividual("");
        } else {
            d.setCodigoModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getCodigo());
            d.setNombreModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getNombre());
            d.setCodigoUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getCodigo());
            d.setNombreUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getRuta());
            d.setPartidaIndividual(objeto.getDistributivoDetalle().getPartidaIndividual());
        }
        d.setTipoIdentificacion(p.getTipoDocumento());
        d.setNumeroIdentificacion(p.getNumeroDocumento());
        d.setNombres(p.getNombres());
        d.setTipo(tipo);
        d.setValorCalculadoRubroIngreso(rubro.getValorRubro());
        d.setValorDescontadoRubroIngreso(rubro.getValorRubro());
        d.setRmu(objeto.getDistributivoDetalle().getRmu());
        d.setValores(formatearValores(rubro.getValores()));
        d.setFechaCreacion(new Date());
        d.setUsuarioCreacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
        d.setVigente(Boolean.TRUE);
        d.setRetencionJudicial(rubro.getBeneficiarioEspecial() == null ? Boolean.FALSE : rubro.getBeneficiarioEspecial());
        d.setDistributivoDetalle(p.getDistributivoDetalle());
        d.setCertificacionPresupuestaria(p.getDistributivoDetalle().getCertificacionPresupuestaria());
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            d.setServidorId(objeto.getLiquidacion().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.getCodigo())) {
        }

        d.setGravable(rubro.getRubro().getGravable());
        d = nominaDetalleDao.crear(d);
        nominaDetalleDao.flush();
        registrarNominaNovedades(rubro, d, objeto);
        return d;
    }

    /**
     * Registra descuentos para activos.
     *
     * @param objeto
     * @param p
     * @param rubro
     * @param tipo
     * @throws BridgeExcepcion
     */
    private List<NominaDetalle> registrarDetalleDescuentos(final ObjetoNominaVO objeto, final PersonaNominaVO p,
            final RubroNominaVO rubro, final String tipo) throws DaoException, ServicioException {
        List<NominaDetalle> detalles = new FastTable<NominaDetalle>();
        if (!objeto.getRubro().getBeneficiarioUnico() && objeto.getRubro().getTipoBeneficiario() != null && objeto.getRubro().getTipoBeneficiario().
                equals(TipoBeneficiarioEnum.ESPECIAL.getCodigo())) {
            List<BeneficiarioEspecial> beneficiarios = beneficiarioEspecialDao.buscar(objeto.getRubro().getId(),
                    objeto.getNomina().getInstitucionEjercicioFiscal().getInstitucion().getId(), objeto.
                    getDistributivoDetalle().getServidor().getId());
            for (BeneficiarioEspecial be : beneficiarios) {
                if (UtilFechas.between(objeto.getNomina().getPeriodoNomina().getFechaInicio(), be.getFechaInicio(), be.getFechaFin() == null ? objeto.getNomina().getPeriodoNomina().getFechaInicio() : be.getFechaFin())) {
                    NominaDetalle d = registrarNominaDetalleDescuentos(objeto, p, tipo, rubro, be);
                    d.setValorCalculadoRubroDescuentos(be.getValor());
                    d.setValorDescontadoRubroDescuentos(be.getValor());
                    registrarBeneficiariosEspecial(objeto, d, be);
                    d = nominaDetalleDao.crear(d);
                    detalles.add(d);
                    nominaDetalleDao.flush();
                }
            }
        } else {
            NominaDetalle d = registrarNominaDetalleDescuentos(objeto, p, tipo, rubro, null);
            // regitrar beneficiarios
            registrarBeneficiariosNormales(objeto, d);
            d = nominaDetalleDao.crear(d);
            detalles.add(d);
            nominaDetalleDao.flush();
            registrarNominaNovedades(rubro, d, objeto);
            registrarNominaDescuentosAnticipos(rubro, d, objeto);
        }
        return detalles;
    }

    /**
     * Registra los aportes patronales para activos.
     *
     * @param objeto
     * @param p
     * @param rubro
     * @param tipo
     * @throws BridgeExcepcion
     */
    private NominaDetalle registrarDetalleAportes(final ObjetoNominaVO objeto, final PersonaNominaVO p,
            final RubroNominaVO rubro, final String tipo) throws DaoException {
        NominaDetalle d = new NominaDetalle();
        d.setRubroAportesId(objeto.getRubro().getId());
        d.setCodigoRubroAportes(objeto.getRubro().getCodigo());
        d.setNombreRubroAportes(objeto.getRubro().getNombre());
        d.setNomina(objeto.getNomina());

        if (objeto.getDistributivoDetalle() == null) {
            d.setCodigoModalidadLaboral("");
            d.setNombreModalidadLaboral("");
            d.setCodigoUnidadOrganizacional("");
            d.setNombreUnidadOrganizacional("");
            d.setPartidaIndividual("");
        } else {
            d.setCodigoModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getCodigo());
            d.setNombreModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getNombre());
            d.setCodigoUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getCodigo());
            d.setNombreUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getRuta());
            d.setPartidaIndividual(objeto.getDistributivoDetalle().getPartidaIndividual());
        }
        d.setTipoIdentificacion(p.getTipoDocumento());
        d.setNumeroIdentificacion(p.getNumeroDocumento());
        d.setNombres(p.getNombres());
        d.setTipo(tipo);
        d.setValorCalculadoRubroAportes(rubro.getValorRubro());
        d.setValorDescontadoRubroAportes(rubro.getValorRubro());
        d.setRmu(objeto.getDistributivoDetalle().getRmu());
        d.setValores(formatearValores(rubro.getValores()));
        d.setFechaCreacion(new Date());
        d.setUsuarioCreacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
        d.setVigente(Boolean.TRUE);
        d.setRetencionJudicial(rubro.getBeneficiarioEspecial() == null ? Boolean.FALSE : rubro.getBeneficiarioEspecial());
        d.setDistributivoDetalle(p.getDistributivoDetalle());
        d.setCertificacionPresupuestaria(p.getDistributivoDetalle().getCertificacionPresupuestaria());
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            d.setServidorId(objeto.getLiquidacion().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.getCodigo())) {
        }
        d.setGravable(rubro.getRubro().getGravable());
        // regitrar beneficiarios
        registrarBeneficiariosNormales(objeto, d);
        d = nominaDetalleDao.crear(d);
        nominaDetalleDao.flush();
        registrarNominaNovedades(rubro, d, objeto);
        return d;
    }

    /**
     *
     * @param objeto
     * @param nominaDetalle
     * @param be
     * @throws DaoException
     */
    private void registrarBeneficiariosEspecial(final ObjetoNominaVO objeto, final NominaDetalle nominaDetalle,
            final BeneficiarioEspecial be) throws DaoException {
        nominaDetalle.setTipoIdentificacionBeneficiario(be.getTipoIdentificacion());
        nominaDetalle.setNumeroIdentificacionBeneficiario(be.getNumeroIdentificacion());
        nominaDetalle.setNombresBeneficiario(be.getNombreBeneficiario());
    }

    /**
     *
     * @param objeto
     * @param p
     * @param tipo
     * @param rubro
     * @return
     */
    private NominaDetalle registrarNominaDetalleDescuentos(final ObjetoNominaVO objeto, final PersonaNominaVO p,
            final String tipo, final RubroNominaVO rubro, final BeneficiarioEspecial be) {
        NominaDetalle d = new NominaDetalle();
        d.setRubroDescuentosId(objeto.getRubro().getId());
        d.setCodigoRubroDescuentos(objeto.getRubro().getCodigo());
        if (be == null) {
            d.setNombreRubroDescuentos(objeto.getRubro().getNombre());
        } else {
            d.setNombreRubroDescuentos(UtilCadena.concatenar(objeto.getRubro().getNombre(), "(", be.getNombreBeneficiario(), ")"));
        }
        d.setNomina(objeto.getNomina());
        if (objeto.getDistributivoDetalle() == null) {
            d.setCodigoModalidadLaboral("");
            d.setNombreModalidadLaboral("");
            d.setCodigoUnidadOrganizacional("");
            d.setNombreUnidadOrganizacional("");
            d.setPartidaIndividual("");
        } else {
            d.setCodigoModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getCodigo());
            d.setNombreModalidadLaboral(objeto.getDistributivoDetalle().getDistributivo().getModalidadLaboral().
                    getNombre());
            d.setCodigoUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional().getCodigo());
            d.setNombreUnidadOrganizacional(objeto.getDistributivoDetalle().getDistributivo().
                    getUnidadOrganizacional().getRuta());
            d.setPartidaIndividual(objeto.getDistributivoDetalle().getPartidaIndividual());
        }
        d.setTipoIdentificacion(p.getTipoDocumento());
        d.setNumeroIdentificacion(p.getNumeroDocumento());
        d.setNombres(p.getNombres());
        d.setTipo(tipo);
        d.setValorCalculadoRubroDescuentos(rubro.getValorRubro());
        d.setValorDescontadoRubroDescuentos(rubro.getValorRubro());
        d.setRmu(objeto.getDistributivoDetalle().getRmu());
        d.setValores(formatearValores(rubro.getValores()));
        d.setFechaCreacion(new Date());
        d.setUsuarioCreacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
        d.setVigente(Boolean.TRUE);
        d.setRetencionJudicial(rubro.getBeneficiarioEspecial() == null ? Boolean.FALSE : rubro.getBeneficiarioEspecial());
        d.setDistributivoDetalle(p.getDistributivoDetalle());
        d.setCertificacionPresupuestaria(p.getDistributivoDetalle().getCertificacionPresupuestaria());
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
            d.setServidorId(objeto.getLiquidacion().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
            d.setServidorId(objeto.getDistributivoDetalle().getServidor().getId());
        } else if (objeto.getNomina().getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.getCodigo())) {
        }
        d.setGravable(rubro.getRubro().getGravable());
        if (rubro.getRubro().getPrioridadDescuento() != null) {
            d.setPrioridad(rubro.getRubro().getPrioridadDescuento());
            d.setPrioridadConDescuento(rubro.getRubro().getConDescuentoParcial());
        }
        return d;
    }

    /**
     * Formateo los valores de un formula.
     *
     * @param valores
     * @return Texto de valores.
     */
    private String formatearValores(final Map<String, Object> valores) {
        StringBuilder s = new StringBuilder();
        if (valores != null) {
            Set<String> keys = valores.keySet();
            for (String key : keys) {
                s.append(key);
                s.append("=");
                s.append(UtilNumeros.getInstancia().formatearMoneda(valores.get(key)));
                s.append("\n");
            }
        }
        return s.toString();

    }

    /**
     *
     * @param objeto
     * @param nominaDetalle
     * @throws DaoException
     */
    private void registrarBeneficiariosNormales(final ObjetoNominaVO objeto, final NominaDetalle nominaDetalle) throws
            DaoException {
        if (objeto.getRubro().getBeneficiarioUnico() != null && objeto.getRubro().getBeneficiarioUnico()) {
            // beneficiario unico.    
            nominaDetalle.setTipoIdentificacionBeneficiario(objeto.getRubro().getTipoIdentificacionBeneficiario());
            nominaDetalle.setNumeroIdentificacionBeneficiario(objeto.getRubro().getIdentificacionBeneficiario());
            nominaDetalle.setNombresBeneficiario(objeto.getRubro().getNombreBeneficiario());
        } else if (objeto.getRubro().getTipoBeneficiario() != null && objeto.getRubro().getTipoBeneficiario().
                equals(TipoBeneficiarioEnum.NORMAL.getCodigo())) {
            BeneficiarioInstitucional bi = null;
            for (BeneficiarioInstitucionalNominaVO bin : objeto.getBeneficiariosInstitucionales()) {
                if (bin.getRubro().getId().compareTo(objeto.getRubro().getId()) == 0) {
                    bi = bin.getBeneficiarioInstitucional();
                    break;
                }
            }
            if (bi == null) {
                problemaServicio.registrarProblema(objeto, TipoProblemaEnum.ESPECIFICACION_BENEFICIARIO_RUBRO.getId());
            } else {
                nominaDetalle.setTipoIdentificacionBeneficiario(bi.getTipoIdentificacion());
                nominaDetalle.setNumeroIdentificacionBeneficiario(bi.getNumeroIdentificacion());
                nominaDetalle.setNombresBeneficiario(bi.getNombreBeneficiario());
            }
        }
    }

    /**
     *
     * @param rubro
     * @param d
     * @param objeto
     * @throws BridgeExcepcion
     */
    private void registrarNominaNovedades(final RubroNominaVO rubro, final NominaDetalle d,
            final ObjetoNominaVO objeto) throws DaoException {
        if (rubro.getNovedadDetalle() != null) {
            NovedadDetalle pnd = rubro.getNovedadDetalle();
            NominaDetalleNovedad pndn = new NominaDetalleNovedad();
            pndn.setNominaDetalle(d);
            pndn.setNovedadDetalle(pnd);
            pndn.setFechaCreacion(new Date());
            pndn.setUsuarioCreacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
            pndn.setVigente(Boolean.TRUE);
            nominaDetalleNovedadDao.crear(pndn);
            if (rubro.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
                pnd.setValorDescontado(d.getValorDescontadoRubroIngreso());
                pnd.setValorCalculado(d.getValorCalculadoRubroIngreso());
            } else if (rubro.getRubro().getTipo().equals(TipoRubroEnum.APORTE_INSTITUCIONAL.getCodigo())) {
                pnd.setValorDescontado(d.getValorDescontadoRubroAportes());
                pnd.setValorCalculado(d.getValorCalculadoRubroAportes());
            } else if (rubro.getRubro().getTipo().equals(TipoRubroEnum.DESCUENTOS.getCodigo())) {
                pnd.setValorDescontado(d.getValorDescontadoRubroDescuentos());
                pnd.setValorCalculado(d.getValorCalculadoRubroDescuentos());
            } else if (rubro.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo())) {
                pnd.setValorDescontado(d.getValorDescontadoRubroIngreso());
                pnd.setValorCalculado(d.getValorCalculadoRubroIngreso());
            } else if (rubro.getRubro().getTipo().equals(TipoRubroEnum.RECUPERACION_ANTICIPOS.getCodigo())) {
                pnd.setValorDescontado(d.getValorDescontadoRubroDescuentos());
                pnd.setValorCalculado(d.getValorCalculadoRubroDescuentos());
            } else {
                pnd.setValorDescontado(BigDecimal.ZERO);
                pnd.setValorCalculado(BigDecimal.ZERO);
            }

            pnd.setFechaActualizacion(new Date());
            pnd.setUsuarioActualizacion(objeto.getUsuario().getServidor().getNumeroIdentificacion());
            novedadDetalleDao.actualizar(pnd);
            rubro.getListaNovedadDetalle().clear();
        }
    }

    /**
     *
     * @param rubro
     * @param d
     * @param objeto
     * @throws BridgeExcepcion
     */
    private void registrarNominaDescuentosAnticipos(final RubroNominaVO rubro, final NominaDetalle d,
            final ObjetoNominaVO objeto) throws DaoException, ServicioException {
        if (rubro.getListaAnticiposPlanPagos() != null) {
            for (AnticipoPlanPago app : rubro.getListaAnticiposPlanPagos()) {
                AnticipoPago ap = new AnticipoPago();
                ap.setAnticipoPlanPago(app);
                ap.setAnticipoPlanPagoId(app.getId());
                ap.setFechaCreacion(new Date());
                ap.setMontoPagado(app.getValorAPagar());
                ap.setNominaDetalle(d);
                ap.setNominaDetalleId(d.getId());
                ap.setUsuarioCreacion(objeto.getUsuario().getUsuario());
                ap.setVigente(Boolean.TRUE);
                anticipoPagoDao.crear(ap);
                anticipoPagoDao.flush();

                // verificar si cuota esta cancelada.
                if (anticipoServicio.calcularSaldoDeCuota(app.getId()).compareTo(BigDecimal.ZERO) == 0) {
                    app.setEstadoPago(EstadoPlanPagoEnum.PAGADO.getCodigo());
                    anticipoPlanPagoDao.actualizar(app);
                }

                anticipoServicio.actualizarAnticipo(app.getAnticipo());
            }
            rubro.getListaAnticiposPlanPagos().clear();
        }
    }

    /**
     * Recupera valores por datos dicionales.
     *
     * @param objeto
     * @return
     * @throws EntidadBOExcepcion
     */
    private List<NovedadDetalle> buscarDatosAdicionales(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getDistributivoDetalle() != null) {
            return novedadDetalleDao.buscarPorServidorNominaDatoAdicional(objeto.getNomina().
                    getInstitucionEjercicioFiscalId(), objeto.getNomina().getId(),
                    objeto.getRubro().getIdDatoAdicional(), objeto.getDistributivoDetalle().getIdServidor());
        }
        return null;
    }

    /**
     *
     * @param objeto
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public ResultadoRecuperacionAnticipoVO buscarPagosAnticiposPendientes(final ObjetoNominaVO objeto) throws DaoException {
        ResultadoRecuperacionAnticipoVO vo = new ResultadoRecuperacionAnticipoVO();
        BigDecimal valor = BigDecimal.ZERO;
        BigDecimal valorCuota;
        List<Object[]> pagos = anticipoPlanPagoDao.buscarPagosPendientes(
                objeto.getDistributivoDetalle().getServidor().getId(), objeto.getNomina().getPeriodoNomina().getFechaFinal());
        if (!pagos.isEmpty()) {
            for (Object[] pago : pagos) {
                AnticipoPlanPago app = anticipoPlanPagoDao.buscarPorId(((BigDecimal) pago[0]).longValue());
                valorCuota = (BigDecimal) pago[3];
                if (pago[4] != null) {
                    valorCuota = valorCuota.subtract((BigDecimal) pago[4]);
                }
                app.setValorAPagar(valorCuota);
                vo.getValores().put(UtilCadena.concatenar("#", (String) pago[2], "/", (Integer) pago[1]), valorCuota);
                objeto.getListaAnticiposPlanPagos().add(app);
                valor = valor.add(valorCuota);
            }
        }
        vo.setValor(valor);
        return vo;
    }

    /**
     * Valida la ocurrencias de pago.
     *
     * @param objeto
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public void validarOcurrencia(final ObjetoNominaVO objeto) throws DaoException {
        List<ServidorNominaVO> servidores = null;
        if (PeriodicidadOcurrenciaNominaEnum.MENSUAL.getCodigo().equals(objeto.getNomina().
                getTipoNomina().getPeriodicidadOcurrencia())) {
            servidores = nominaDetalleDao.buscarOcurrenciaMensual(objeto.getInstitucionEjercicioFiscal().getId(), objeto.getNomina().getPeriodoNominaId(), objeto.
                    getNomina().getTipoNominaId(), objeto.getNomina().getId());
        } else if (PeriodicidadOcurrenciaNominaEnum.ANUAL.getCodigo().equals(objeto.getNomina().
                getTipoNomina().getPeriodicidadOcurrencia())) {
            servidores = nominaDetalleDao.buscarOcurrenciaAnual(objeto.getInstitucionEjercicioFiscal().getId(), objeto.getNomina().getTipoNominaId(), objeto.getNomina().getId());
        }
        if (servidores != null) {
            for (ServidorNominaVO s : servidores) {
                objeto.getPersonaNominaVO().setTipoDocumento(s.getTipoIdentificacion());
                objeto.getPersonaNominaVO().setNumeroDocumento(s.getNumeroIdentificacion());
                objeto.getPersonaNominaVO().setNombres(s.getApellidosNombres());
                problemaServicio.registrarProblema(objeto, TipoProblemaEnum.NUMERO_MAXIMO_PAGOS.getId());
            }
        }
    }

    /**
     *
     * @param objeto
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void eliminarDetallesNomina(final ObjetoNominaVO objeto) throws DaoException, ServicioException {
        nominaEliminacionServicio.eliminarDatosAnterioresTerceros(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresAnticipos(objeto);
        nominaEliminacionServicio.encerarDatosAnterioresNovedades(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresNovedades(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresPagosAnticipos(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresNominasIR(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresNominas(objeto);
        nominaEliminacionServicio.eliminarDatosAnterioresLiquidaciones(objeto);
    }

    /**
     *
     * @param objeto
     * @param dd
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void eliminarDetallesNomina(final ObjetoNominaVO objeto, final DistributivoDetalle dd) throws DaoException, ServicioException {
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorLiquidaciones(objeto, dd);
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorAnticipos(objeto, dd);
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorPagosAnticipos(objeto, dd);
        nominaEliminacionServicio.encerarDatosAnterioresPorServidorNovedades(objeto, dd);
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorNovedades(objeto, dd);
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorNominas(objeto, dd);
        nominaEliminacionServicio.eliminarDatosAnterioresPorServidorNominasIR(objeto, dd);
    }

    /**
     * Verifica si el servidor se encuentra activo dentro del periodo de nomina.
     *
     * @param objeto
     * @param dd
     * @return
     */
    private Boolean estanActivoEnPeriodo(final ObjetoNominaVO objeto, final DistributivoDetalle dd) {
        if (!objeto.getNomina().getTipoNomina().getValidaActivoPeriodo()) {
            return true;
        }
        if (dd.getFechaFin() != null) {
            if (UtilFechas.between(objeto.getNomina().getPeriodoNomina().getFechaInicio(),
                    dd.getServidor().getFechaIngresoInstitucion(), dd.getFechaFin())
                    || UtilFechas.between(objeto.getNomina().getPeriodoNomina().getFechaFinal(),
                            dd.getServidor().getFechaIngresoInstitucion(), dd.getFechaFin())) {
                return true;
            }
        } else {
            if (dd.getServidor().getFechaIngresoInstitucion().compareTo(objeto.getNomina().getPeriodoNomina().
                    getFechaFinal()) <= 0) {
                return true;
            }
        }

        return false;

    }

    /**
     * Registra pagos realizar a una nomina.
     *
     * @param objeto
     * @throws ServicioException
     */
    public void registrarPagos(final ObjetoNominaVO objeto) throws ServicioException {
        try {

            System.out.println("INICIANDO CALCULO DE PAGOS..");
            if (objeto.getEjecucionNominaVO().getTodos()) {
                nominaPagoDao.eliminarPorNomina(objeto.getNomina().getId());
            } else {
                for (Servidor s : objeto.getEjecucionNominaVO().getServidores()) {
                    nominaPagoDao.eliminarPorNomina(objeto.getNomina().getId(), s.getTipoIdentificacion(), s.getNumeroIdentificacion());
                }
            }
            objeto.setPersonaNominaVO(new PersonaNominaVO());
            int inicio = 0;
            int pagina = 1000;
            System.out.println("CREANDO CALCULO DE PAGOS..");

            List<NominaPagoVO> pagos = nominaDetalleDao.buscarPagos(objeto, inicio, pagina);
            while (!pagos.isEmpty()) {
                for (NominaPagoVO vo : pagos) {
                    NominaPago np = new NominaPago();
                    np.setApellidosNombres(vo.getApellidosNombres());
                    np.setTipoIdentificacion(vo.getTipoIdentificacion());
                    np.setNumeroIdentificacion(vo.getNumeroIdentificacion());
                    np.setValorIngresos(vo.getValorIngresos());
                    np.setValorDescuentos(vo.getValorDescuentos());
                    np.setValorAportes(vo.getValorAportes());
                    np.setValorLiquidoAPagar(vo.getValorLiquidoAPagar());
                    np.setNomina(objeto.getNomina());
                    np.setUnidadOrganizacional(new UnidadOrganizacional(vo.getUnidadOrganizacionalId()));
                    np.setUnidadPresupuestaria(new UnidadPresupuestaria(vo.getUnidadPresupuestarioId()));
                    np.setUsuarioCreacion(objeto.getUsuario().getUsuario());
                    np.setFechaCreacion(new Date());
                    np.setVigente(Boolean.TRUE);
                    List<CuentaBancaria> cuenta = cuentaBancariaDao.buscarVigenteParaNominaPorServidor(vo.getServidorId());
                    if (cuenta.isEmpty()) {
//                        objeto.getPersonaNominaVO().setNombres(vo.getApellidosNombres());
//                        objeto.getPersonaNominaVO().setTipoDocumento(vo.getTipoIdentificacion());
//                        objeto.getPersonaNominaVO().setNumeroDocumento(vo.getNumeroIdentificacion());
//                        problemaServicio.registrarProblema(objeto, TipoProblemaEnum.CUENTA_BANCARIA_NO_REGISTRADA.getId());
                        np.setBanco(null);
                        np.setTipoCuenta("");
                        np.setNumeroCuenta("");

                    } else {
                        CuentaBancaria cb = cuenta.get(0);
                        np.setBanco(cb.getBanco());
                        np.setTipoCuenta(cb.getTipoCuenta());
                        np.setNumeroCuenta(cb.getNumerCuenta());
                    }
                    nominaPagoDao.crear(np);
                    //nominaPagoDao.flush();
                }
                //nominaPagoDao.clear();
                nominaPagoDao.flush();
                inicio += pagina;
                pagos = nominaDetalleDao.buscarPagos(objeto, inicio, pagina);
            }

            System.out.println("FIN CALCULO DE PAGOS..");

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Se encarga de verificar los servidores a los cuales su liquido a pagar es menor al minimo a pagar y realiza los
     * ajuste en descuentos.
     *
     * @param objeto Datos de la ejecucion de la nomina
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void validarMinimoPagar(final ObjetoNominaVO objeto) throws DaoException {
        List<Object[]> servidores = nominaDetalleDao.buscarMinimoPagar(objeto.getNomina().getId(),
                objeto.getNomina().getMinimoPagarTipo(), objeto.getNomina().getMinimoPagarValor());
        for (Object[] servidor : servidores) {
            ajustarMinimoPagar(objeto.getNomina().getId(), ((String) servidor[0]),
                    (String) servidor[1], (BigDecimal) servidor[2]);
        }
    }

    /**
     * Realiza el ajusto de los rubros de descuento de acuerdo al minimo pagar.
     *
     * @param idProcesoNomina
     * @param tipoDocumento
     * @param numeroDocumento
     * @param descuento
     * @throws EntidadBOExcepcion
     * @throws BridgeExcepcion
     */
    private void ajustarMinimoPagar(final Long nominaId, final String tipoDocumento,
            final String numeroDocumento, BigDecimal descuento) throws DaoException {
        // recuperar los descuentos del servidor.
        List<NominaDetalle> descuentos = nominaDetalleDao.buscarServidoresConMinimoAPagar(
                nominaId, tipoDocumento, numeroDocumento);
        if (!descuentos.isEmpty()) {
            // agrupar por prioridades
            Map<Integer, List<NominaDetalle>> mapa = new FastMap<Integer, List<NominaDetalle>>();
            for (NominaDetalle d : descuentos) {
                List<NominaDetalle> rubros;
                if (mapa.containsKey(d.getPrioridad())) {
                    rubros = mapa.get(d.getPrioridad());
                } else {
                    rubros = new FastTable<NominaDetalle>();
                }
                rubros.add(d);
                mapa.put(d.getPrioridad(), rubros);
            }
            // Realizar recortes en los descuentos.
            Set<Integer> prioridades = mapa.keySet();
            for (Integer prioridad : prioridades) {
                List<NominaDetalle> rubros = mapa.get(prioridad);
                if (descuento.compareTo(BigDecimal.ZERO) == 1) {
                    descuento = realizarDescuentoMinimoPagar(rubros, descuento);
                }
            }
        }
    }

    /**
     * Realiza el descuentos de los rubros de descuento.
     *
     * @param rubros
     * @param descuento
     * @throws BridgeExcepcion
     */
    private BigDecimal realizarDescuentoMinimoPagar(final List<NominaDetalle> rubros, BigDecimal descuento) throws
            DaoException {
        if (!rubros.isEmpty()) {
            int total = rubros.size();
            // calcular valor equitativo a descontar
            //BigDecimal promedio = descuento.divide(new BigDecimal(total), 2, RoundingMode.HALF_UP);

            int i = 0;
            for (NominaDetalle r : rubros) {
                if (descuento.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
                if (r.getValorDescontadoRubroDescuentos().compareTo(descuento) == -1) {
                    descuento = descuento.subtract(r.getValorDescontadoRubroDescuentos());
                    r.setValorDescontadoRubroDescuentos(BigDecimal.ZERO);
                    r.setVigente(false);
                } else {
                    r.setValorDescontadoRubroDescuentos(r.getValorDescontadoRubroDescuentos().subtract(descuento));
                    descuento = BigDecimal.ZERO;
                }
                nominaDetalleDao.actualizar(r);
                // descontar en novedades
                if (!r.getListaNovedades().isEmpty()) {
                    BigDecimal cuota = r.getValorDescontadoRubroDescuentos().divide(new BigDecimal(r.getListaNovedades().
                            size()), 2,
                            RoundingMode.HALF_UP);
                    BigDecimal saldo = r.getValorDescontadoRubroDescuentos();
                    for (NominaDetalleNovedad pndn : r.getListaNovedades()) {
                        pndn.getNovedadDetalle().setValorDescontado(cuota);
                        nominaDetalleNovedadDao.actualizar(pndn);
                        saldo = saldo.subtract(cuota);
                    }
                    if (!(saldo.compareTo(BigDecimal.ZERO) == 0)) {
                        NominaDetalleNovedad pndn = r.getListaNovedades().get(0);
                        pndn.getNovedadDetalle().setValorDescontado(pndn.getNovedadDetalle().
                                getValorDescontado().add(saldo));
                        nominaDetalleNovedadDao.actualizar(pndn);
                    }
                }
                i++;
            }
        }
        return descuento;
    }

    /**
     * Se encarga de verificar que el monto del ingreso no sea mayor al permitido en el tipo del movimiento.
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void validarMaximoIngreso(final ObjetoNominaVO objeto) throws DaoException {
        List<Object[]> servidores = nominaDetalleDao.buscarMaximoIngreso(objeto.getNomina().getId(), objeto.getNomina().
                getTipoNomina().getTotalIngresoMaximo());
        PersonaNominaVO vo = new PersonaNominaVO();
        objeto.setPersonaNominaVO(vo);
        for (Object[] servidor : servidores) {
            vo.setTipoDocumento((String) servidor[0]);
            vo.setNumeroDocumento((String) servidor[1]);
            vo.setNombres((String) servidor[2]);
            problemaServicio.registrarProblema(objeto, TipoProblemaEnum.MAXIMO_TOTAL_INGRESO_PERMITIDO.getId());
        }
    }

    /**
     * Se encarga de verificar que el monto del ingreso no sea mayor al permitido en el tipo del movimiento.
     *
     * @param objeto
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void validarLiquidoAPagarNegativo(final ObjetoNominaVO objeto) throws DaoException {
        List<Object[]> servidores = nominaDetalleDao.buscarLiquidoAPagarNegativos(objeto.getNomina().getId());
        PersonaNominaVO vo = new PersonaNominaVO();
        objeto.setPersonaNominaVO(vo);
        for (Object[] servidor : servidores) {
            vo.setTipoDocumento((String) servidor[0]);
            vo.setNumeroDocumento((String) servidor[1]);
            vo.setNombres((String) servidor[2]);
            problemaServicio.registrarProblema(objeto, TipoProblemaEnum.LIQUIDO_PAGAR_NEGATIVO.getId());
        }
    }

}
