/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AnticipoPagoDao;
import ec.com.atikasoft.proteus.dao.AnticipoPlanPagoDao;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.ImpuestoRentaDao;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleNovedadDao;
import ec.com.atikasoft.proteus.dao.NominaHistoricoDao;
import ec.com.atikasoft.proteus.dao.NominaHistoricoRolDao;
import ec.com.atikasoft.proteus.dao.NominaProblemaDao;
import ec.com.atikasoft.proteus.dao.NovedadDao;
import ec.com.atikasoft.proteus.dao.NovedadDetalleDao;
import ec.com.atikasoft.proteus.dao.RubroTipoNominaDao;
import ec.com.atikasoft.proteus.dao.TerceroNominaDetalleDao;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.modelo.nomina.NominaProblema;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import ec.com.atikasoft.proteus.vo.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.servlet.ServletContext;
import javolution.util.FastTable;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
@Stateless
@LocalBean
public class NominaServicio extends BaseServicio {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(NominaServicio.class.getName());

    /**
     * Indica el total de servidores que se procesara la nomina en paginacion.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 500;

    /**
     * Formato de fecha.
     */
    public static final String MONEDA_DOLARES = "USD";

    /**
     * Dao de nomina.
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Dao de detalle de nomina.
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     * Dao del historico de la nomina.
     */
    @EJB
    private NominaHistoricoDao nominaHistoricoDao;

    /**
     * Dao de problemas en nominas.
     */
    @EJB
    private NominaProblemaDao nominaProblemaDao;

    /**
     * Dao de nomina x novedades.
     */
    @EJB
    private NominaDetalleNovedadDao nominaDetalleNovedadDao;

    /**
     *
     */
    @EJB
    private NovedadDetalleDao novedadDetalleDao;

    /**
     *
     */
    @EJB
    private TerceroNominaDetalleDao terceroNominaDetalleDao;

    /**
     * Dao del distributivo.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Dao de institucion x ejercicio fiscal.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     *
     */
    @EJB
    private RubroTipoNominaDao rubroTipoNominaDao;

    /**
     *
     */
    @EJB
    private NominaHistoricoRolDao nominaHistoricoRolDao;

    /**
     *
     */
    @EJB
    private NominaCalculoServicio nominaCalculoServicio;

    /**
     *
     */
    @EJB
    private ProblemaServicio problemaServicio;

    /**
     *
     */
    @EJB
    private NovedadDao novedadDao;

    /**
     *
     */
    @EJB
    private AnticipoPagoDao anticipoPagoDao;

    /**
     *
     */
    @EJB
    private AnticipoPlanPagoDao anticipoPlanPagoDao;

    /**
     *
     */
    @EJB
    private AnticipoServicio anticipoServicio;

    /**
     *
     */
    @EJB
    private ImpuestoRentaDao impuestoRentaDao;

    /**
     * Permite crear una nomina nueva.
     *
     * @param nomina datos de nomina
     * @param usuario datos del usuario
     * @return Nomina
     * @throws ServicioException error
     */
    public Nomina crear(final Nomina nomina, final UsuarioVO usuario) throws ServicioException {
        try {
            InstitucionEjercicioFiscal ief = institucionEjercicioFiscalDao.buscarPorId(usuario.
                    getInstitucionEjercicioFiscal().getId());
            nomina.setUsuarioCreacion(usuario.getServidor().getNumeroIdentificacion());
            nomina.setFechaCreacion(new Date());
            nomina.setVigente(Boolean.TRUE);
            nomina.setNumero(generarNumeroNomina(ief));
            nomina.setCalculando(Boolean.FALSE);
            nomina.setBloqueado(Boolean.FALSE);
            Nomina nom = nominaDao.crear(nomina);
            guardarNominaHistorico(nom);
            return nom;
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite actualizar una nomina actualizada.
     *
     * @param nomina Datos de nomina
     * @throws ServicioException Error en actualizar nomina
     */
    public void actualizarNomina(final Nomina nomina) throws ServicioException {
        try {
            nominaDao.actualizar(nomina);
            guardarNominaHistorico(nomina);
            if (nomina.getEstado().equals(EstadoNominaEnum.PAGADO.getCodigo())) {
                guardarHistoricoRoles(nomina);
            }

        } catch (DaoException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
    }

    /**
     * Genera el numero del tramite.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return Numero generado.
     * @throws DaoException Error de ejecucion.
     */
    private String generarNumeroNomina(final InstitucionEjercicioFiscal institucion) throws DaoException {
        institucionEjercicioFiscalDao.lock(institucion);
        institucion.setContadorTramite(institucion.getContadorTramite() + 1);
        institucionEjercicioFiscalDao.actualizar(institucion);
        return UtilMatematicas.rellenarConCerosIzq(institucion.getContadorTramite(), 10);
    }

    /**
     * Se encarga de ejecutar una nomina.
     *
     * @param vo
     * @param nominaId
     * @param usuario
     * @param sc
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void ejecutar(final EjecucionNominaVO vo, final Long nominaId, final UsuarioVO usuario,
            final ServletContext sc) throws ServicioException {
        try {
            Nomina nomina = nominaDao.buscarPorId(nominaId);
            List<NominaProblema> problemas = new FastTable<>();
            List<PersonaNominaVO> beneficiariosNominaVO = new FastTable<>();
            List<BeneficiarioInstitucionalNominaVO> beneficiarios = new FastTable<>();
            ObjetoNominaVO objeto = new ObjetoNominaVO();
            objeto.setNomina(nomina);
            objeto.setProblemas(problemas);
            objeto.setUsuario(usuario);
            objeto.setEjecucionNominaVO(vo);
            objeto.setBeneficiariosInstitucionales(beneficiarios);
            objeto.setBeneficiariosNominaVO(beneficiariosNominaVO);
            objeto.setServletContext(sc);
            objeto.setInstitucionEjercicioFiscal(nomina.getInstitucionEjercicioFiscal());
            // carga ir de exoneracion
            ImpuestoRenta irExoneracion = impuestoRentaDao.
                    buscarFraccionBasicaPorExoneracion(nomina.getPeriodoNomina().getEjercicioFiscalId());
            objeto.setImpuestoRentaExoneracion(irExoneracion);
            List<Rubro> rubros = new ArrayList<>();
            List<RubroTipoNomina> rubrosTiposNominas = rubroTipoNominaDao.buscarVigentesPorTipoNomina(nomina.
                    getTipoNominaId());
            for (RubroTipoNomina rtn : rubrosTiposNominas) {
                rubros.add(rtn.getRubro());
            }
            validarExistenciaRubroIngreso(objeto, rubros);
            validarExistenciaEstadoPuesto(objeto);
            validarExistenciaEstadoServidores(objeto);
            objeto.setRubros(rubros);
            if (nomina.getTipoNomina().getCobertura().equals(CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())) {
                calculoServidores(objeto);
            } else if (nomina.getTipoNomina().getCobertura().equals(CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
                calculoAnticipos(objeto);
            } else if (nomina.getTipoNomina().getCobertura().equals(CoberturaNominaEnum.LIQUIDACIONES.getCodigo())) {
                calculoLiquidaciones(objeto);
            } else if (nomina.getTipoNomina().getCobertura().equals(CoberturaNominaEnum.TERCEROS.getCodigo())) {
                //calculoAnticipos(objeto);
            }

            problemaServicio.registrarEjecucion("VALIDANDO MINIMO A PAGAR....", objeto.getUsuario().getUsuario(),
                    objeto.getNomina().getId());
            // validar minimos.
            nominaCalculoServicio.validarMinimoPagar(objeto);

            problemaServicio.registrarEjecucion("VALIDANDO MAXIMO INGRESO....", objeto.getUsuario().getUsuario(),
                    objeto.getNomina().getId());
            // validar maximo ingreso
            nominaCalculoServicio.validarMaximoIngreso(objeto);

            // validar ocurrencia
            problemaServicio.registrarEjecucion("VALIDANDO OCURRENCIA....", objeto.getUsuario().getUsuario(),
                    objeto.getNomina().getId());
            nominaCalculoServicio.validarOcurrencia(objeto);

            // validar liquida a pagar negativo.
            problemaServicio.registrarEjecucion("VALIDANDO LIQUIDOS A PAGAR NEGATIVOS....", objeto.getUsuario().
                    getUsuario(), objeto.getNomina().getId());
            nominaCalculoServicio.validarLiquidoAPagarNegativo(objeto);

            // calcular pagos.
            problemaServicio.registrarEjecucion("REGISTRANDO PAGOS....", objeto.getUsuario().getUsuario(),
                    objeto.getNomina().getId());
            //nominaCalculoServicio.registrarPagos(objeto);

            nomina.setFechaActualizacion(new Date());
            nomina.setUsuarioActualizacion(usuario.getServidor().getNumeroIdentificacion());
            nomina.setFechaGeneracion(new Date());
            nomina.setBloqueado(Boolean.TRUE);
            nominaDao.actualizar(nomina);
            nominaProblemaDao.registrarProblemas(objeto);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Calcula la nomina de anticipos.
     *
     * @param objeto
     */
    private void calculoAnticipos(final ObjetoNominaVO objeto) throws DaoException, IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchMethodException, ServicioException {
        if (objeto.getProblemas().isEmpty()) {
            if (objeto.getEjecucionNominaVO().getTodos()) {
                nominaCalculoServicio.eliminarDetallesNomina(objeto);
            }
            nominaCalculoServicio.calcularAnticiposSegmento(objeto.getEjecucionNominaVO().getAnticipos(), objeto, 0);
        }
    }

    /**
     * Calcula la nomina de anticipos.
     *
     * @param objeto
     */
    private void calculoLiquidaciones(final ObjetoNominaVO objeto) throws DaoException, IllegalAccessException,
            InstantiationException, InvocationTargetException, NoSuchMethodException, ServicioException {
        if (objeto.getProblemas().isEmpty()) {
            if (objeto.getEjecucionNominaVO().getTodos()) {
                nominaCalculoServicio.eliminarDetallesNomina(objeto);
            }
            nominaCalculoServicio.calcularLiquidacionesSegmento(
                    objeto.getEjecucionNominaVO().getLiquidaciones(), objeto, 0);

        }

    }

    /**
     * Calcula la nomina de servidores.
     *
     * @param objeto
     */
    private void calculoServidores(final ObjetoNominaVO objeto) throws DaoException, ServidorException,
            ServicioException, IllegalAccessException, InstantiationException, InvocationTargetException,
            NoSuchMethodException {
        if (objeto.getProblemas().isEmpty()) {
            if (objeto.getEjecucionNominaVO().getTodos()) {
                problemaServicio.registrarEjecucion("ELIMINANDO DATOS ANTERIORES....", objeto.getUsuario().
                        getUsuario(), objeto.getNomina().getId());
                nominaCalculoServicio.eliminarDetallesNomina(objeto);
            }
            // Recuperar servidores por lotes.
            int lote = 1;
            int fila = 0;
            Long total = distributivoDetalleDao.contar(objeto.getEjecucionNominaVO());
            problemaServicio.registrarEjecucion("INICIANDO CALCULO....", objeto.getUsuario().getUsuario(),
                    objeto.getNomina().getId());

            List<DistributivoDetalle> puestos = distributivoDetalleDao.buscar(objeto.getEjecucionNominaVO(),
                    fila, TOTAL_LOTE_SERVIDORES);
            if (puestos.isEmpty()) {
                throw new ServidorException(
                        "No se selecciono ningún servidor o servidor(es) seleccionado(s) no cumple(n) las"
                        + " especificaciones del Tipo Nómina.");
            } else {
                try {
                    while (!puestos.isEmpty()) {
                        final int l = lote;
                        System.out.println("INICIANDO LOTE :" + lote + " de " + puestos.size());
                        nominaCalculoServicio.calcularServidoresSegmentoFase1(puestos, objeto, l);
                        nominaCalculoServicio.calcularServidoresSegmentoFase2(puestos, objeto, l);
                        lote++;
                        fila += TOTAL_LOTE_SERVIDORES;
                        puestos = distributivoDetalleDao.buscar(objeto.getEjecucionNominaVO(), fila,
                                TOTAL_LOTE_SERVIDORES);
                    }
                } finally {
                }
            }
            problemaServicio.registrarEjecucion("CALCULO FINALIZADO.............. ", objeto.getUsuario().
                    getUsuario(), objeto.getNomina().getId());
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
//    private NominaDetalle registrarDetalleBeneficiarios(final ObjetoNominaVO objeto, final PersonaNominaVO p,
//            final RubroNominaVO rubro) throws DaoException {
//        NominaDetalle nd = null;
//        if (objeto.getRubro().getTipo().equals(TipoRubroEnum.DESCUENTOS.getCodigo())) {
//            nd = registrarDetalleDescuentos(objeto, p, rubro, "BEN");
//        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.RECUPERACION_ANTICIPOS.getCodigo())) {
//            nd = registrarDetalleDescuentos(objeto, p, rubro, "BEN");
//        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo())) {
//            nd = registrarDetalleIngresos(objeto, p, rubro, "BEN");
//        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.APORTE_INSTITUCIONAL.getCodigo())) {
//            nd = registrarDetalleAportes(objeto, p, rubro, "BEN");
//        } else if (objeto.getRubro().getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
////            registrarDetalleAnticipos(objeto, p, rubro, tipo);
//        }
//        return nd;
//    }
    /**
     * REgistra beneficiarios.
     *
     * @param objeto Datos de la ejecucion.
     * @param valor Valor del rubro.
     * @throws DaoException
     */
//    private void registrarBeneficiariosq(final ObjetoNominaVO objeto, final BigDecimal valor) throws DaoException {
//        if (objeto.getRubro().getBeneficiarioUnico() != null && objeto.getRubro().getBeneficiarioUnico()) {
//            PersonaNominaVO per = new PersonaNominaVO();
//            if (objeto.getRubro().getIdentificacionBeneficiario().trim().length() == TipoDocumentoEnum.CEDULA.
//                    getLonguitud()) {
//                per.setTipoDocumento(TipoDocumentoEnum.CEDULA.getNemonico());
//            } else if (objeto.getRubro().getIdentificacionBeneficiario().trim().length() == TipoDocumentoEnum.RUC.
//                    getLonguitud()) {
//                per.setTipoDocumento(TipoDocumentoEnum.RUC.getNemonico());
//            } else {
//                per.setTipoDocumento(TipoDocumentoEnum.PASAPORTE.getNemonico());
//            }
//            per.setNumeroDocumento(objeto.getRubro().getIdentificacionBeneficiario());
//            per.setNombres(objeto.getRubro().getNombreBeneficiario());
//            per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
//            per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
//            per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());
//            per.setDistributivoDetalle(objeto.getDistributivoDetalle());
//            per.setTipoDocumentoOrigen(objeto.getPersonaNominaVO().getTipoDocumentoOrigen());
//            per.setNumeroDocumentoOrigen(objeto.getPersonaNominaVO().getNumeroDocumentoOrigen());
//
//            RubroNominaVO rub = new RubroNominaVO();
//            per.getListaRubrosIngresos().add(rub);
//            rub.setRubro(objeto.getRubro());
//            rub.setValorRubro(valor);
//            if (objeto.getBeneficiariosNominaVO() != null) {
//                objeto.getBeneficiariosNominaVO().add(per);
//            }
//        } else {
//            // por beneficiarios normales.
//            if (objeto.getRubro().getTipoBeneficiario() != null && objeto.getRubro().getTipoBeneficiario().
//                    equals(TipoBeneficiarioEnum.NORMAL.getCodigo())) {
//                BeneficiarioInstitucional bi = null;
//                for (BeneficiarioInstitucionalNominaVO bin : objeto.getBeneficiariosInstitucionales()) {
//                    if (bin.getRubro().getId().compareTo(objeto.getRubro().getId()) == 0) {
//                        bi = bin.getBeneficiarioInstitucional();
//                        break;
//                    }
//                }
//                if (bi == null) {
//                    problemaServicio.registrarProblema(objeto, TipoProblemaEnum.ESPECIFICACION_BENEFICIARIO_RUBRO.getId());
//                } else {
//                    PersonaNominaVO per = new PersonaNominaVO();
//                    per.setListaRubrosAportes(new FastTable<RubroNominaVO>());
//                    per.setListaRubrosDescuentos(new FastTable<RubroNominaVO>());
//                    per.setListaRubrosIngresos(new FastTable<RubroNominaVO>());
//                    per.setTipoDocumento(bi.getTipoIdentificacion());
//                    per.setNumeroDocumento(bi.getNumeroIdentificacion());
//                    per.setNombres(bi.getNombreBeneficiario());
//                    per.setDistributivoDetalle(objeto.getDistributivoDetalle());
//                    per.setTipoDocumentoOrigen(objeto.getPersonaNominaVO().getTipoDocumentoOrigen());
//                    per.setNumeroDocumentoOrigen(objeto.getPersonaNominaVO().getNumeroDocumentoOrigen());
//
//                    RubroNominaVO rub = new RubroNominaVO();
//                    per.getListaRubrosIngresos().add(rub);
//                    rub.setRubro(objeto.getRubro());
//                    rub.setValorRubro(valor);
//                    objeto.getBeneficiariosNominaVO().add(per);
//                }
//            }
//        }
//    }
    /**
     * Valida que por lo menos exista un rubrode ingreso.
     *
     * @param objeto
     * @param rubros
     */
    private void validarExistenciaRubroIngreso(final ObjetoNominaVO objeto, final List<Rubro> rubros) throws
            DaoException {
        boolean deIngreso = false;
        for (Rubro r : rubros) {
            if (r.getTipo().equals(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo())
                    || r.getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
                deIngreso = true;
            }
        }
        if (!deIngreso) {
            problemaServicio.registrarProblema(objeto, TipoProblemaEnum.NO_EXISTE_RUBRO_INGRESOS.getId());
        }
    }

    /**
     * Valida que por lo menos exista un rubro de ingreso.
     *
     * @param objeto
     * @param rubros
     */
    private void validarExistenciaEstadoServidores(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getListaTipoNominaEstadoPersonales().isEmpty()) {
            problemaServicio.registrarProblema(objeto, TipoProblemaEnum.NO_EXISTE_ESTADOS_SERVIDOR.getId());
        } else {
            List<Long> estados = new ArrayList<>();
            for (TipoNominaEstadoPersonal ep : objeto.getNomina().getTipoNomina().
                    getListaTipoNominaEstadoPersonales()) {
                if (ep.getVigente()) {
                    estados.add(ep.getEstadoPersonalId());
                }
            }
            objeto.getEjecucionNominaVO().setEstadosServidorId(estados);
        }
    }

    /**
     * Valida que por lo menos exista un rubrode ingreso.
     *
     * @param objeto
     * @param rubros
     */
    private void validarExistenciaEstadoPuesto(final ObjetoNominaVO objeto) throws DaoException {
        if (objeto.getNomina().getTipoNomina().getCobertura().equals(
                CoberturaNominaEnum.SERVIDORES_MUNICIPALES.getCodigo())
                || objeto.getNomina().getTipoNomina().getCobertura().equals(
                        CoberturaNominaEnum.ANTICIPOS.getCodigo())) {
            if (objeto.getNomina().getTipoNomina().getListaTipoNominaEstadoPuestos().isEmpty()) {
                problemaServicio.registrarProblema(objeto, TipoProblemaEnum.NO_EXISTE_ESTADOS_PUESTOS.getId());
            } else {
                List<Long> estados = new ArrayList<>();
                for (TipoNominaEstadoPuesto ep : objeto.getNomina().getTipoNomina().getListaTipoNominaEstadoPuestos()) {
                    if (ep.getVigente()) {
                        estados.add(ep.getEstadoPuestoId());
                    }
                }
                objeto.getEjecucionNominaVO().setEstadosPuestoId(estados);
            }
        }
    }

    /**
     *
     * @param vo ResultadoNominaVO
     * @return
     * @throws ServicioException
     */
    public List<ResultadoNominaVO> listarResultadoNomina(final ResultadoNominaVO vo)
            throws ServicioException {
        try {
            return nominaDetalleDao.buscarResultadoNomina(vo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param vo ResultadoNominaVO
     * @return
     * @throws ServicioException
     */
    public BigDecimal calcularCapacidadPago(final BusquedaNominaVO vo)
            throws ServicioException {
        try {
            return nominaDetalleDao.calcularCapacidadPago(vo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param vo ResultadoNominaVO
     * @param firstRow
     * @param numberOfRows
     * @return
     * @throws ServicioException
     */
    public List<ResultadoNominaVO> listarResultadoNomina(final ResultadoNominaVO vo, int firstRow,
            int numberOfRows)
            throws ServicioException {
        try {
            vo.setInicioConsulta(firstRow);
            vo.setFinConsulta(numberOfRows);
            return nominaDetalleDao.buscarResultadoNomina(vo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Busca totales de resultados de la nomina.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public int contarResultadoNomina(final ResultadoNominaVO vo) throws ServicioException {
        return nominaDetalleDao.contarResultadoNomina1(vo);
    }

    /**
     * BUsca totales de los resultados de la nomina.
     *
     * @param vo
     * @return
     * @throws ServicioException
     */
    public ResultadoNominaVO buscarTotalesResultadoNomina(final ResultadoNominaVO vo) throws ServicioException {
        try {
            return nominaDetalleDao.buscarTotalResultadoNomina(vo);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param idNomina
     * @return
     * @throws ServicioException
     */
    public Boolean existeNomina(final Long idNomina) throws ServicioException {
        try {
            return nominaDetalleDao.existeNomina(idNomina);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Método que devuelve el query String para la consulta de trámites.
     *
     * @param o ConsultaTramiteVO
     * @return String
     * @throws DaoException
     */
    public String buscarNominaString(final BusquedaNominaVO o) throws DaoException {
        return nominaDao.buscarPorFiltrosNominaString(o);
    }

    /**
     *
     * @param idNomina
     * @param numeroIdentificacion
     * @return
     * @throws ServicioException
     */
    public List<NominaDetalle> listarPorNominaYServidor(final Long idNomina, final String numeroIdentificacion)
            throws ServicioException {
        try {
            return nominaDetalleDao.buscarTodosPorNominaYServidor(idNomina, numeroIdentificacion);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param nomina
     * @throws DaoException
     */
    private void guardarHistoricoRoles(Nomina nomina) throws DaoException {
        int lote = 1000;
        int inicio = 0;
        List<NominaDetalle> detalles = nominaDetalleDao.buscarTodosPorNomina(nomina.getId(), inicio, lote);
        while (!detalles.isEmpty()) {
            for (NominaDetalle d : detalles) {
                NominaHistoricoRol nhr = new NominaHistoricoRol();
                nhr.setAnio(Integer.valueOf(nomina.getInstitucionEjercicioFiscal().getEjercicioFiscal().getNombre()));
                nhr.setCodigoDependencia(d.getDistributivoDetalle().getDistributivo().
                        getUnidadOrganizacional().getCodigo());
                nhr.setNombreDependencia(d.getDistributivoDetalle().getDistributivo().
                        getUnidadOrganizacional().getRuta());
                nhr.setFechaCreacion(new Date());
                nhr.setFechaGeneracion(nomina.getFechaGeneracion());
                nhr.setGravable(d.getGravable());
                nhr.setMes(nomina.getPeriodoNomina().getNumero().intValue());
                nhr.setNombreRubro(nhr.getNombreRubro());
                nhr.setNombres(d.getNombres());
                nhr.setNombresBeneficiario(d.getNombresBeneficiario());
                nhr.setNumeroIdentificacion(d.getNumeroIdentificacion());
                nhr.setNumeroIdentificacionBeneficiario(d.getNumeroIdentificacionBeneficiario());
                nhr.setNumeroNomina(nomina.getNumero());
                nhr.setPartidaIndividual(d.getPartidaIndividual());
                nhr.setSueldo(d.getRmu());
                nhr.setTipoIdentificacion(d.getTipoIdentificacion());
                nhr.setTipoIdentificacionBeneficiario(d.getTipoIdentificacionBeneficiario());
                nhr.setTipoNomina(nomina.getTipoNomina().getTipo());
                nhr.setUsuarioCreacion(d.getUsuarioCreacion());
                if (d.getValorDescontadoRubroAportes() != null) {
                    nhr.setTipoRubro(TipoRubroEnum.APORTE_INSTITUCIONAL.getCodigo());
                    nhr.setValor(d.getValorDescontadoRubroAportes());
                } else if (d.getValorDescontadoRubroDescuentos() != null) {
                    nhr.setTipoRubro(TipoRubroEnum.DESCUENTOS.getCodigo());
                    nhr.setValor(d.getValorDescontadoRubroDescuentos());
                } else if (d.getValorDescontadoRubroIngreso() != null) {
                    nhr.setTipoRubro(TipoRubroEnum.INGRESO_SERVIDORES.getCodigo());
                    nhr.setValor(d.getValorDescontadoRubroIngreso());
                }
                nhr.setVigente(Boolean.TRUE);
                try {
                    System.out.println(BeanUtils.describe(nhr));
                } catch (Exception e) {
                }

                nominaHistoricoRolDao.crear(nhr);
            }
            nominaDetalleDao.flush();
            nominaDetalleDao.clear();
            inicio += lote;
            detalles.clear();
            detalles.addAll(nominaDetalleDao.buscarTodosPorNomina(nomina.getId(), inicio, lote));
        }

    }

    /**
     * Este metodo procesa la creacion de un historico de la nomina.
     *
     * @param n Nomina
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void guardarNominaHistorico(final Nomina n) throws ServicioException {
        try {
            NominaHistorico nh = new NominaHistorico();
            nh.setEstado(EstadoNominaEnum.obtenerDescripcion(n.getEstado()));
            nh.setNomina(n);
            nh.setFechaCreacion(new Date());
            nh.setVigente(Boolean.TRUE);
            nh.setObservacion(n.getObservacion());
            if (n.getUsuarioActualizacion() == null) {
                nh.setUsuarioCreacion(n.getUsuarioCreacion());
            } else {
                nh.setUsuarioCreacion(n.getUsuarioActualizacion());
            }
            nominaHistoricoDao.crear(nh);
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Usado para liberar liberar recursos asociados a la nomina cuando esta es descartada.
     *
     * @param nominaId
     * @param usuario
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void descartarNomina(Long nominaId, String usuario) throws ServicioException {
        try {
            // eliminar anticipos.
            Set<Long> ids = new HashSet<>();
            List<AnticipoPago> pagos = anticipoPagoDao.buscarPorNomina(nominaId);
            for (AnticipoPago pago : pagos) {
                AnticipoPlanPago app = pago.getAnticipoPlanPago();
                if (!ids.contains(app.getId()) && app.getEstadoPago().equals(EstadoPlanPagoEnum.PAGADO.getCodigo())) {
                    if (anticipoServicio.calcularSaldoDeCuota(app.getId()).compareTo(BigDecimal.ZERO) != 0) {
                        app.setEstadoPago(EstadoPlanPagoEnum.PENDIENTE.getCodigo());
                        anticipoPlanPagoDao.actualizar(app);
                    }
                }
                ids.add(app.getId());
            }
            anticipoPagoDao.eliminar(nominaId);
            // eliminar novedades
            nominaDetalleNovedadDao.eliminar(nominaId);
            // encerar los valores descontados de la novedad
            List<NovedadDetalle> detalles = novedadDetalleDao.buscarNovedadesDetallesPorNominaId(nominaId);
            for (NovedadDetalle nd : detalles) {
                nd.setValorDescontado(BigDecimal.ZERO);
                nd.setUsuarioActualizacion(usuario);
                nd.setFechaActualizacion(new Date());
                nd.setValorCalculado(BigDecimal.ZERO);
                nd.setValorDescontado(BigDecimal.ZERO);
                novedadDetalleDao.actualizar(nd);
            }
            // eliminar terceros
            terceroNominaDetalleDao.eliminarPorNomina(nominaId);
            // eliminar detalles de la nomina
            nominaDetalleDao.eliminar(nominaId);
            // quitar vigencia a las novedades asociadas a la nomina
            List<Novedad> novedades = novedadDao.buscarNovedadesNominaId(nominaId);
            for (Novedad novedad : novedades) {
                novedad.setVigente(Boolean.FALSE);
                novedad.setUsuarioActualizacion(usuario);
                novedad.setFechaActualizacion(new Date());
                novedadDao.actualizar(novedad);
            }

        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param servidorId
     * @param insitucionEF
     * @param primerRegistro
     * @param numeroRegistros
     * @return
     * @throws ServicioException
     */
    public List<ResultadoNominaVO> listarNominaPorServidor(final Long servidorId, final Long insitucionEF, 
            final Integer primerRegistro, final Integer numeroRegistros) throws ServicioException {
        try {
            List<ResultadoNominaVO> l = nominaDao.buscarNominaPorServidor(servidorId, insitucionEF, 
                    primerRegistro, numeroRegistros);
            for (ResultadoNominaVO r : l) {
                Long nId = r.getNomina().getId();
                r.setNomina(nominaDao.buscarPorId(nId));
            }
            return l;
        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param servidorId
     * @param insitucionEF
     * @return
     * @throws ServicioException
     */
    public Integer contarNominaPorServidor(final Long servidorId, final Long insitucionEF) throws ServicioException {
        try {
            Integer conteo = nominaDao.contarNominaPorServidor(servidorId, insitucionEF);
            return conteo;
        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param nomina
     * @return
     */
    public ByteArrayInputStream generarNominaSPI(Nomina nomina) {
        try {

            List<RegistrosSpiVO> data = nominaDetalleDao.obtenerDatosParaArchivoSPI(nomina.getId());

            TipoNomina tipo = nomina.getTipoNomina();

            String titulo = tipo.getRegimenLaboral().getNombre();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet hoja = workbook.createSheet(titulo);

            CellStyle estiloCelda = workbook.createCellStyle();
            HSSFFont fuenteCabecera = workbook.createFont();
            fuenteCabecera.setBold(true);
            estiloCelda.setFont(fuenteCabecera);
            estiloCelda.setAlignment(HorizontalAlignment.LEFT);

            // crear cabecera
            int fila = 0;
            UtilArchivos.crearFilaExcel(workbook, hoja, 0, estiloCelda,
                    "Número de Identificacion",
                    "Número",
                    "Apellidos y Nombres",
                    "Banco",
                    "Número de Cuenta",
                    "Tipo de Cuenta",
                    "Valor",
                    "Código",
                    "Descripción");

            // cargar los datos          
            for (RegistrosSpiVO reg : data) {
                fila++;
                UtilArchivos.crearFilaExcel(
                        workbook, hoja, fila, null,
                        reg.getNumeroIdentificacion(),
                        reg.getNumero(),
                        reg.getApellidosNombres(),
                        reg.getBanco(),
                        reg.getCuenta(),
                        reg.getTipoCuenta(),
                        reg.getValor(),
                        reg.getCodigo(),
                        reg.getDescripcion());
            }

            //Ajustando el tamaño de cada columna al tamaño del texto que contiene
            for (int i = 0; i == 2; i++) {
                hoja.autoSizeColumn(i);
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            workbook.write(stream);
            ByteArrayInputStream inStream = new ByteArrayInputStream(stream.toByteArray());
            return inStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param nominaId
     * @param periodo
     * @param nroNomina
     * @param tipoNominaYFecha
     * @param gruposPresupuestarios
     * @return
     * @throws ServicioException
     */
    public String[] generarDataParaArchivosSIPARI(Long nominaId, String periodo, String nroNomina,
            String tipoNominaYFecha, String... gruposPresupuestarios) throws ServicioException {
        String[] dataResultado = new String[gruposPresupuestarios.length];
        try {
            for (int i = 0; i < gruposPresupuestarios.length; i++) {
                List<Object[]> data = nominaDetalleDao.recuperarDataSIPARI(nominaId, gruposPresupuestarios[i]);

                StringBuilder dataParaArchivo = new StringBuilder("");
                Iterator<Object[]> it = data.iterator();
                String sociedad = "**";
                while (it.hasNext()) {
                    StringBuilder sb = new StringBuilder("");
                    Object[] fila = it.next();
                    if (!fila[0].toString().equals(sociedad)) {
                        dataParaArchivo.append(armarEncabezado(
                                fila[0].toString(), periodo, periodo,
                                "04", "USD", nroNomina, tipoNominaYFecha)).append("#");
                        sociedad = fila[0].toString();
                    }

                    int j = 0;
                    for (Object columna : fila) {
                        if (j > 0) {
                            sb.append(columna != null ? columna : "");
                            if (j < fila.length - 1) {
                                sb.append(";");
                            } else {
                                sb.append("#");
                            }
                        }
                        j++;
                    }
                    dataParaArchivo.append(sb).append("#");
                }
                dataResultado[i] = dataParaArchivo.toString();
            }
            return dataResultado;

        } catch (DaoException ex) {
            Logger.getLogger(NominaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param sociedad
     * @param param2
     * @param param3
     * @param param4
     * @param tipoMoneda
     * @param param6
     * @param tipoNominaYFecha
     * @return
     */
    private String armarEncabezado(
            String sociedad, String param2,
            String param3, String param4,
            String tipoMoneda, String param6,
            String tipoNominaYFecha) {

        StringBuilder sb = new StringBuilder("");
        sb.append(sociedad).append(";");
        sb.append(param2).append(";");
        sb.append(param3).append(";");
        sb.append(param4).append(";");
        sb.append(tipoMoneda).append(";");
        sb.append(param6).append(";");
        sb.append(tipoNominaYFecha).append(";");

        return sb.toString();
    }

    /**
     * Busca por el id del puesto los datos de nomina necesarios
     *
     * @param distributivoDetalleId
     * @return
     * @throws ServicioException
     */
    public List<NominaDetalleVO> listarNominaDetallesPorPuesto(final Long distributivoDetalleId)
            throws ServicioException {
        try {
            return nominaDetalleDao.buscarNominaDetallesPorPuesto(distributivoDetalleId);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

}
