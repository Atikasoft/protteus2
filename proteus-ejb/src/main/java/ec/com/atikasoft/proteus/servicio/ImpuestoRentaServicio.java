/*
 *  ImpuestoRentaServicio.java
 
 *  Quito - Ecuador
 *
 *  14/11/2013
 *
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.FormularioIRDao;
import ec.com.atikasoft.proteus.dao.GastoPersonalDao;
import ec.com.atikasoft.proteus.dao.GastoPersonalHistoricoDao;
import ec.com.atikasoft.proteus.dao.ImpuestoRentaDao;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleIRDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.formatos.FormatoRDEPEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.GastoPersonal;
import ec.com.atikasoft.proteus.modelo.GastoPersonalHistorico;
import ec.com.atikasoft.proteus.modelo.ImpuestoRenta;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.vistas.FormularioIR;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.Test;
import ec.com.atikasoft.proteus.util.UtilArchivos;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.RDEP;
import ec.com.atikasoft.proteus.vo.InfoSriVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class ImpuestoRentaServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(ImpuestoRentaServicio.class.getCanonicalName());

    /**
     * Variables que definen los valores de tags del archivo RDEP.
     */
    private final String TAG_RDEP = "rdep";

    private final String TAG_RELACION_DEP = "retRelDep";

    private final String TAG_DATO_RELACION_DEP = "datRetRelDep";

    private final String TAG_EMPLEADO = "empleado";

    /**
     * Variable para almacenar los errores generados en el proceso.
     */
    private List<String> listaErrores = new ArrayList<String>();

    /**
     * DAO de GastoPersonal
     */
    @EJB
    private GastoPersonalDao gastoPersonalDao;

    /**
     * DAO de GastoPersonal
     */
    @EJB
    private GastoPersonalHistoricoDao gastoPersonalHistoricoDao;

    /**
     * Dao d vista de ir de nomina.
     */
    @EJB
    private NominaDetalleIRDao vistaNominaIRDao;

    /**
     * Dao de formulario ir.
     */
    @EJB
    private FormularioIRDao formularioIRDao;

    /**
     * Dao de servidor.
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * DAO de ParametroInstitucionalDao.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * DAO de Institucion.
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;

    /**
     *
     */
    @EJB
    private ImpuestoRentaDao impuestoRentaDao;

    /**
     * Permite el registro de un gastoPersonal
     *
     * @param gastoPersonal registro a crear en el sistema
     * @param rmu
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarGastoPersonal(final GastoPersonal gastoPersonal, final BigDecimal rmu) throws ServicioException {
        try {
            GastoPersonal gp = gastoPersonalDao.crear(gastoPersonal);
            gastoPersonalDao.flush();
            if (gp.getId() != null) {
                guardarGastoPersonalHistorico(gp, rmu);
            } else {
                LOG.log(Level.INFO, "No se guard\u00f3 el historico de gastos personales de {0} de {1}",
                        new Object[]{gp.getNombreEjercicioFiscal(), gp.getServidor().getNumeroIdentificacion()});
            }
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        } catch (ServicioException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un historico del gastoPersonal
     *
     * @param gastoPersonal Historico registro a crear en el sistema
     * @param rmu
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarGastoPersonalHistorico(final GastoPersonal gastoPersonal, final BigDecimal rmu) throws
            ServicioException {
        try {
            GastoPersonalHistorico h = new GastoPersonalHistorico();
            /* usuario y servidor*/
            h.setVigente(Boolean.TRUE);
            if (gastoPersonal.getUsuarioActualizacion() != null) {
                h.setUsuarioCreacion(gastoPersonal.getUsuarioActualizacion());
            } else {
                h.setUsuarioCreacion(gastoPersonal.getUsuarioCreacion());
            }
            h.setFechaCreacion(new Date());
            h.setApellidosServidor(gastoPersonal.getServidor().getApellidos());
            h.setNombresServidor(gastoPersonal.getServidor().getNombres());
            h.setApellidosNombresServidor(gastoPersonal.getServidor().getApellidosNombres());
            h.setTipoIdentificacionServidor(gastoPersonal.getServidor().getTipoIdentificacion());
            h.setNumeroIdentificacionServidor(gastoPersonal.getServidor().getNumeroIdentificacion());

            /*ejercicio fiscal*/
            h.setInstitucionId(gastoPersonal.getEjercicioFiscal().getInstitucion() != null ? gastoPersonal.
                    getEjercicioFiscal().getInstitucion().getId() : null);
            h.setFraccionBasica(gastoPersonal.getEjercicioFiscal().getEjercicioFiscal() != null ? gastoPersonal.
                    getEjercicioFiscal().getEjercicioFiscal().getFraccionBasica() : null);
            h.setNombreEjericioFiscal(gastoPersonal.getEjercicioFiscal().getEjercicioFiscal() != null ? gastoPersonal.
                    getEjercicioFiscal().getEjercicioFiscal().getNombre() : null);
            h.setFechaInicioEjericioFiscal(
                    gastoPersonal.getEjercicioFiscal().getEjercicioFiscal() != null ? gastoPersonal.getEjercicioFiscal().
                            getEjercicioFiscal().getFechaInicio() : null);
            h.setFechaFinEjericioFiscal(gastoPersonal.getEjercicioFiscal().getEjercicioFiscal() != null ? gastoPersonal.
                    getEjercicioFiscal().getEjercicioFiscal().getFechaFin() : null);
            /*gastos personales*/
            h.setTipo(gastoPersonal.getTipo());
            h.setGastoPersonalId(gastoPersonal.getId());
            h.setAlimentacion(gastoPersonal.getAlimentacion());
            h.setSalud(gastoPersonal.getSalud());
            h.setEducacion(gastoPersonal.getEducacion());
            h.setVestimenta(gastoPersonal.getVestimenta());
            h.setVivienda(gastoPersonal.getVivienda());

            h.setIessPersonal(gastoPersonal.getIessPersonal());
            h.setIessPersonalOtroEmpleador(gastoPersonal.getIessPersonalOtroEmpleador());

            h.setIngresos(gastoPersonal.getIngresos());
            h.setIngresosAdicionales(gastoPersonal.getIngresosAdicionales());
            h.setIngresosOtroEmpleador(gastoPersonal.getIngresosOtroEmpleador());

            h.setTotalDeducible(gastoPersonal.getTotalDeducible());
            h.setRmu(rmu);
            if (h.getIessPersonalOtroEmpleador().compareTo(BigDecimal.ZERO) > 0) {
                h.setPorcentajeAporteOtroEmpleador(new BigDecimal(9.35).setScale(2, RoundingMode.HALF_UP));
            } else {
                h.setPorcentajeAporteOtroEmpleador(BigDecimal.ZERO);
            }

            gastoPersonalHistoricoDao.crear(h);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un gastoPersonal
     *
     * @param gastoPersonal registro a actualizar
     * @param rmu
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarGastoPersonal(final GastoPersonal gastoPersonal, final BigDecimal rmu) throws
            ServicioException {
        try {
            gastoPersonalDao.actualizar(gastoPersonal);
            guardarGastoPersonalHistorico(gastoPersonal, rmu);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación logica de un gastoPersonal
     *
     * @param gastoPersonal registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarGastoPersonal(final GastoPersonal gastoPersonal) throws ServicioException {
        try {
            gastoPersonal.setVigente(false);
            gastoPersonalDao.actualizar(gastoPersonal);
        } catch (Exception ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite listar los gastoPersonals id de institucion ejercicio fiscal
     *
     * @param idEjercioFiscal periodo fiscal
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<GastoPersonal> listarGastoPersonalPorInstitucionEjercicioFiscal(final Long idEjercioFiscal)
            throws ServicioException {
        try {

            return gastoPersonalDao.buscarTodosPorEjercicioFiscalInstitucion(idEjercioFiscal);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener los registros por idServidor
     *
     * @param idServidor Long
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<GastoPersonal> listarGastoPersonalPorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return gastoPersonalDao.buscarTodosPorServidor(idServidor);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este método permite obtener los registros por idServidor y id de institucion ejericio fiscal.
     *
     * @param idServidor Long
     * @param idEjericio Long id del institucion ejercicio fiscal
     * @return lista de registros recuperados
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<GastoPersonal> listarGastoPersonalPorServidorYEjercicioFiscal(final Long idServidor,
            final Long idEjercicio)
            throws ServicioException {
        try {
            return gastoPersonalDao.buscarTodosPorServidorYEjercicioFiscal(idServidor, idEjercicio);
        } catch (Exception e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Obtiene el total de ingresos gravables generados en el ejercicio fiscal.
     *
     * @param ejercicioFiscal
     * @param servidor
     * @param periodoMaximo
     * @return
     * @throws ServicioException
     */
    public BigDecimal calcularTotalIngresosGravables(final EjercicioFiscal ejercicioFiscal, final Servidor servidor,
            final Long periodoMaximo) throws ServicioException {
        try {
            return vistaNominaIRDao.calcularTotalIngresosGravables(ejercicioFiscal, servidor, periodoMaximo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    private void generarXmlInicioRDEP(final InstitucionEjercicioFiscal ins, final Element dependecia,
            final Document document) throws ServicioException {

    }

    /**
     * Permite generar los archivos RDEP-SRI del impuesto a la renta.
     *
     * @param vo
     * @throws ServicioException
     */
    public List<String> generarArchivoRDEP(final BusquedaServidorVO vo) throws ServicioException {

        try {
            //creacion de xml
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;

            try {
                builder = factory.newDocumentBuilder();
                //objeto Document, lo que vendría a ser la raíz del arbol XML  y también le seteamos la versión de XML.
                DOMImplementation implementation = builder.getDOMImplementation();
                Document document = implementation.createDocument(null, TAG_RDEP, null);
                document.setXmlVersion("1.0");
                document.setXmlStandalone(Boolean.TRUE);
                //Obtenemos la raíz
                Element raiz = document.getDocumentElement();
                InstitucionEjercicioFiscal ins = null;
                try {
                    ins = institucionEjercicioFiscalDao.buscarPorId(vo.getIntitucionEjercicioFiscalId());
                    if (ins == null) {
                        getListaErrores().add("No se puede obtener institucion/ejericio fiscal: " + vo.
                                getIntitucionEjercicioFiscalId());
                        return getListaErrores();
                    }
                    //creamos un nodo para agregarle a la raíz
                    for (FormatoRDEPEnum f : FormatoRDEPEnum.values()) {
                        if (f.getSeccion().equals(1)) {
                            Element dependecia = document.createElement(f.getTag());
                            Text valorDependencia = null;
                            switch (f.getIndice()) {
                                case 1:
                                    valorDependencia = document.createTextNode(ins.getInstitucion().getRuc());

                                    break;
                                case 2:
                                    valorDependencia = document.
                                            createTextNode(ins.getEjercicioFiscal().getNombre());
                                    break;
                            }
                            if (valorDependencia == null) {
                                getListaErrores().add("No se puede obtener detalles de institucion/ejericio fiscal: "
                                        + vo.
                                        getIntitucionEjercicioFiscalId());
                                return getListaErrores();
                            }
                            dependecia.appendChild(valorDependencia);
                            raiz.appendChild(dependecia);
                        }
                    }

                } catch (DaoException ex) {
                    LOG.log(Level.INFO, "No se puede obtener institucion/ejercicio fiscal:{0}", ex);
                    getListaErrores().add("No se puede obtener institucion/ejercicio fiscal: " + vo.
                            getIntitucionEjercicioFiscalId());
                    return getListaErrores();
                }
                /**
                 * ******* x cada servidor municipal
                 */
                Element seccionEmpleados = document.createElement(TAG_RELACION_DEP);
                List<FormularioIR> formulariosIRs = new ArrayList<FormularioIR>();
                try {
                    formulariosIRs = formularioIRDao.buscarServidoresP(vo, 0, 5, null, null, null, null, null);
                } catch (DaoException ex) {
                    LOG.log(Level.INFO, "Error en generacion de archivo rdep-consulta registros{0}", ex);
                    getListaErrores().add("");
                    return getListaErrores();
                }
                //informacion general del empleado
                for (FormularioIR formulario : formulariosIRs) {
                    Element seccionDatosEmpleado = document.createElement(TAG_DATO_RELACION_DEP);
                    Element empleado = document.createElement(TAG_EMPLEADO);
                    for (FormatoRDEPEnum f : FormatoRDEPEnum.values()) {
                        if (f.getSeccion().equals(2)) {
                            Element datos = document.createElement(f.getTag());
                            Servidor servidor;
                            try {
                                servidor = servidorDao.buscarPorId(formulario.getServidorId());
                            } catch (DaoException ex) {
                                LOG.log(Level.INFO, "Error al obtener el servidor {0}", ex);
                                getListaErrores().add("No se puede obtener el servidor: " + formulario.
                                        getNumeroIdentificacion() + ":" + formulario.getNombres());
                                continue;
                            }
                            if (servidor == null) {
                                getListaErrores().add("No se puede obtener el servidor: " + formulario.
                                        getNumeroIdentificacion() + ":" + formulario.getNombres());
                                continue;
                            }

                            String valor = generarArchivoXmlDatosServidor(f.getIndice(), servidor);
                            if (valor == null) {
                                continue;
                            }
                            Text valorDato = document.createTextNode(valor);
                            datos.appendChild(valorDato);
                            empleado.appendChild(datos);

                        }
                    }
                    seccionEmpleados.appendChild(seccionDatosEmpleado);
                    seccionDatosEmpleado.appendChild(empleado);
                    //detalle del impuesto a la renta por servidor
                    for (FormatoRDEPEnum f : FormatoRDEPEnum.values()) {
                        if (f.getSeccion().equals(3)) {
                            Element ir = document.createElement(f.getTag());
                            String valor = generarArchivoXmlValoresIR(f.getIndice(), formulario);
                            if (valor == null || valor.isEmpty()) {
                                getListaErrores().add("Problemas al obtener valores del Impuesto Renta: " + formulario.
                                        getNumeroIdentificacion() + ":" + formulario.getNombres());
                                continue;
                            }
                            Text valorDato = document.createTextNode(valor);
                            ir.appendChild(valorDato);
                            seccionDatosEmpleado.appendChild(ir);
                        }
                    }
                }
                raiz.appendChild(seccionEmpleados);

                // guardar ese XML creado en un archivo
                Source source = new DOMSource(document);
                Result result = new StreamResult(new java.io.File("resultado_xml_test.xml")); //nombre del archivo
                Transformer transformer;
                transformer = TransformerFactory.newInstance().newTransformer();
                transformer.transform(source, result);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (TransformerException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getListaErrores();
    }

    /**
     * Permite obtener el dato de un tag de acuerdo al indice de la enumeracion parametro, corresponde a los datos del
     * servidor.
     *
     * @param indice
     * @param servidor
     * @return
     */
    private String generarArchivoXmlDatosServidor(final Integer indice, final Servidor servidor) {
        String valorDato = null;
        switch (indice) {
            case 1:
                valorDato = "1";
                break;
            case 2:
                if (servidor.getNumeroIdentificacion() != null
                        && servidor.getNumeroIdentificacion().length() >= 3 && servidor.
                        getNumeroIdentificacion().length() <= 13) {
                    valorDato = servidor.getNumeroIdentificacion();
                } else {
                    getListaErrores().add(
                            "Servidor con numero de identificacion demasiado largo o corto: "
                            + servidor.getNumeroIdentificacion() + ":" + servidor.getApellidosNombres());
                    return null;
                }
                break;
            case 3:
                if (servidor.getApellidos() != null && servidor.getApellidos().length() > 2) {
                    valorDato = servidor.getApellidos();
                    if (servidor.getApellidos().length() > 100) {
                        valorDato = servidor.getApellidos().
                                substring(0, 100);
                    }
                } else {
                    getListaErrores().add("Servidor con apellidos demasiado largo o corto: " + servidor.
                            getNumeroIdentificacion() + ":" + servidor.getApellidosNombres());
                    return null;
                }
                break;
            case 4:
                if (servidor.getNombres() != null && servidor.getNombres().length() > 2) {
                    valorDato = servidor.getNombres();
                    if (servidor.getNombres().length() > 100) {
                        valorDato = servidor.getNombres().substring(0, 100);
                    }
                } else {
                    getListaErrores().add("Servidor con nombres demasiado largo o corto: " + servidor.
                            getNumeroIdentificacion() + ":" + servidor.getApellidosNombres());
                    return null;
                }
                break;
            case 5:
                valorDato = "001";
                break;
            case 6:
                valorDato = "01";
                break;
            case 7:
                valorDato = "593"; //ecuador
                break;
            case 8:
                //SI - NA
                valorDato = "NA"; //Campo de uso obligatorio de acuerdo a la aplicación del convenio de doble tributación
                break;
            case 9:
                if (servidor.getCatalogoCapacidades() != null) { //Discapacidad
                    valorDato = "02";
                } else {
                    valorDato = "01";
                }
                break;
            case 10:
                if (servidor.getCatalogoCapacidades() != null) {
                    if (servidor.getPorcentajeDiscapacidad().intValue() > 100 && servidor.
                            getPorcentajeDiscapacidad().intValue() <= 0) {
                        getListaErrores().add("Servidor con porcentaje de discapacidad inválido: "
                                + servidor.getNumeroIdentificacion() + ":"
                                + servidor.getApellidosNombres() + " porcentaje:" + servidor.
                                getPorcentajeDiscapacidad().intValue());
                        return null;
                    } else {
                        valorDato = servidor.getPorcentajeDiscapacidad().
                                toString();
                    }
                } else {
                    valorDato = "0";
                }
                break;
            case 11:
                //depende de la discapacidad campo 9, si discapacidad es 1 o 2 => N, caso contrario tipo de identificacion
                valorDato = "N";
                break;
            case 12:
                //depende de la discapacidad campo 9, si discapacidad es 1 o 2 => N, caso contrario num de identificacion
                valorDato = "N";
                break;
        }
        return valorDato;
    }

    /**
     * Permite obtener el dato de un tag de acuerdo al indice de la enumeracion parametro, corresponde a los datos del
     * Impuesto a la renta.
     *
     * @param indice
     * @param formulario
     * @return
     */
    private String generarArchivoXmlValoresIR(final Integer indice, final FormularioIR formulario) {
        String cero = "00.00";
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("######0.00", otherSymbols);

        String valorDato = null;
        switch (indice) {
            case 1:
                valorDato = formulario.getSueldosSalarios() == null ? cero : df.format(formulario.getSueldosSalarios().
                        setScale(2, RoundingMode.UP));
                if (formulario.getSueldosSalarios() == null) {
                    formulario.setSueldosSalarios(BigDecimal.ZERO);
                }
                break;
            case 2:
                valorDato = formulario.getOtrosIngresos() == null ? cero : df.format(formulario.getOtrosIngresos().
                        setScale(2,
                                RoundingMode.UP));
                if (formulario.getOtrosIngresos() == null) {
                    formulario.setOtrosIngresos(BigDecimal.ZERO);
                }
                break;
            case 3:
                valorDato = cero;//participacion de utilidades
                break;
            case 4:
                valorDato = formulario.getIngresosOtroEmpleador() == null ? cero : df.format(formulario.
                        getIngresosOtroEmpleador());
                break;
            case 5:
                valorDato = cero; //impuesto asumido por este empleador
                break;
            case 6:
                valorDato = formulario.getDecimoTercero() == null ? cero : df.format(formulario.getDecimoTercero());
                break;
            case 7:
                valorDato = formulario.getDecimocuarto() == null ? cero : df.format(formulario.getDecimocuarto());
                break;
            case 8:
                valorDato = formulario.getFondoReserva() == null ? cero : df.format(formulario.getFondoReserva());
                break;
            case 9: //salario digno
                valorDato = cero;
                break;
            case 10://Otros ingresos en relación de dependencia que no constituyen renta gravada
                valorDato = cero;
                break;
            case 11://INGRESOS GRAVADOS CON ESTE EMPLEADOR (informativo 301+303+305+381
                BigDecimal ingresosGravados = formulario.getSueldosSalarios().add(formulario.
                        getOtrosIngresos()).add(BigDecimal.ZERO).add(BigDecimal.ZERO);
                valorDato = df.format(ingresosGravados);
                break;
            case 12://Sistema de salario neto: 1-sin, 2 -con
                valorDato = "N";
                break;
            case 13:
                valorDato = formulario.getAportePersonal() == null ? cero : df.format(formulario.getAportePersonal());
                break;
            case 14://aportes otro empleador
                valorDato = cero;
                break;
            case 15:
                valorDato = formulario.getVivienda() == null ? cero : df.format(formulario.getVivienda());
                break;
            case 16:
                valorDato = formulario.getSalud() == null ? cero : df.format(formulario.getSalud());
                break;
            case 17:
                valorDato = formulario.getEducacion() == null ? cero : df.format(formulario.getEducacion());
                break;
            case 18:
                valorDato = formulario.getAlimentacion() == null ? cero : df.format(formulario.getAlimentacion());
                break;
            case 19:
                valorDato = formulario.getVestimenta() == null ? cero : df.format(formulario.getVestimenta());
                break;
            case 20:
                valorDato = formulario.getExoneracionDiscapacidad() == null ? cero : df.format(formulario.
                        getExoneracionDiscapacidad());
                break;
            case 21:
                valorDato = formulario.getExoneracionTerceraEdad() == null ? cero : df.format(formulario.
                        getExoneracionTerceraEdad());
                break;
            case 22: //base imponible
                valorDato = formulario.getExoneracionTerceraEdad() == null ? cero : df.format(formulario.
                        getExoneracionTerceraEdad());
                break;
            case 23: //impuesto causado
                valorDato = formulario.getExoneracionTerceraEdad() == null ? cero : df.format(formulario.
                        getExoneracionTerceraEdad());
                break;
            case 24: //impuesto renta asumido otros empleadores
                valorDato = cero;
                break;
            case 25: //impuesto renta asumido este empleadores
                valorDato = cero;
                break;
            case 26: //valor retenido
                valorDato = formulario.getImpuestoRenta() == null ? cero : df.format(formulario.getImpuestoRenta());
                break;
        }
        return valorDato;
    }

    /**
     * Validar que para el ejercicio fiscal dado y los parametros de fraccion basica y exceso hasta, no exista una
     * configuracion que se solape No deben existir impuestos a la renta para un mismo ejercicio fiscal que solapen sus
     * rangos definidos por los parametros fraccion basica y exceso hasta
     *
     * @param impuestoRenta
     * @return
     * @throws ServicioException
     */
    public boolean validarRangoFraccionBasicaYExcesoHastaSolapado(final ImpuestoRenta impuestoRenta) throws ServicioException {
        try {
            //exceso hasta en null representa "En Adelante"
            final BigDecimal exceso = impuestoRenta.getExcesoHasta() == null ? BigDecimal.valueOf(Long.MAX_VALUE) : impuestoRenta.getExcesoHasta();
            final BigDecimal fraccionBasica = impuestoRenta.getFraccionBasica();
            final Long ejercicioFiscal = impuestoRenta.getEjercicioFiscal().getId();

            List<ImpuestoRenta> lista = impuestoRentaDao.listarPorEjercicioFiscal(ejercicioFiscal);
            for (ImpuestoRenta ir : lista) {
                BigDecimal iFraccionBasica = ir.getFraccionBasica();
                BigDecimal iExcesoHasta = ir.getExcesoHasta();
                if (iExcesoHasta == null) {
                    //exceso hasta en null representa "En Adelante"
                    iExcesoHasta = BigDecimal.valueOf(Long.MAX_VALUE);
                }
                if (!((fraccionBasica.compareTo(iExcesoHasta) < 0 && exceso.compareTo(iFraccionBasica) < 0)
                        || (exceso.compareTo(iFraccionBasica) > 0 && fraccionBasica.compareTo(iExcesoHasta) > 0))) {
                    if (!(impuestoRenta.getId() != null && impuestoRenta.getId().equals(ir.getId()))) {
                        return true;
                    }
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(ImpuestoRentaServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
        return false;
    }

    
    /**
     * Genera Excel de Bases Imponibles
     * @param ruc
     * @param ejercicioFiscalId
     * @param mes
     * @return 
     */
    public ByteArrayInputStream generarExcelBasesImponibles(final String ruc, final Long ejercicioFiscalId, 
            final Integer mes) {
        try {

            List<InfoSriVO> data = impuestoRentaDao.obtenerDatosSRI(ruc, ejercicioFiscalId, mes);

            String titulo = "BASES IMPONIBLES";

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
                    "RUC", "AÑO", "TIPO IDENTIFICACION", "NUMERO IDENTIFICACION", "APELLIDOS",
                    "NOMBRES", "ESTABLECIMIENTO", "RESIDENCIA TRABAJO", "PAIS RESIDENCIA", "APLICA CONVENIO",
                    "TIPO TRABAJO DISCAPACIDAD", "PORCENTAJE DISCAPACIDAD", "TIPO DISCAPACIDAD", 
                    "IDENTIFICADOR DISCAPACIDAD", "SUELDO SALARIOS","SOBRE SUELDOS", "PARTICIPACION UTILIDADES", 
                    "INTERESES GRABADOS GENERADOS", "IMPUESTO RENTA EMPLEADOR", "DECIMO TERCERO","DECIMO CUARTO",
                    "FONDO RESERVA", "SALARIO DIGNO", "OTROS INGRESOS GRAVADOS", "INGGRAVCONESTEEMPL",
                    "SUELDOS SALARIOS NETO","APORTE PERSONAL IESS", "APORTE PERSONAL IESS OTROS EMPLEADORES",
                    "DEDUCION VIVIENDA","DEDUCION SALUD","DEDUCION EDUCACION", "DEDUCION ALIMENTACION", 
                    "DEDUCION VESTIMENTA", "EXONERACION DISCAPACIDAD", "EXONERACION TERCERA EDAD", "BASE IMPONIBLE",
                    "IMPUESTO RENTA CAUSADO","VALOR RETENIDO OTROS EMPLEADORES","VALOR IMPUESTO ASUMIDO EMPLEADOS",
                    "VALOR RETENIDO","TIPO GENERA","NOMBRE EMPLEADOR","PERIODO","REGIMEN LABORAL",
                    "UNIDAD PRESUPUESTARIA");

            // cargar los datos          
            for (InfoSriVO reg : data) {
                fila++;
                UtilArchivos.crearFilaExcel(
                        workbook, hoja, fila, null,
                        reg.getRuc(), reg.getAnio(), reg.getEmpleado().getTipoIdentificacion(), 
                        reg.getEmpleado().getNumeroIdentificacion(), reg.getEmpleado().getApellidos(), 
                        reg.getEmpleado().getNombres(), reg.getEmpleado().getEstab(), reg.getEmpleado().getResidencia(),
                        reg.getEmpleado().getPaisResidencia(), reg.getEmpleado().getAplicaConvenio(), 
                        reg.getEmpleado().getTipoDiscapacidad(), reg.getEmpleado().getPorcientoDiscapacidad(), 
                        reg.getEmpleado().getTipoIdDiscapacidad(), reg.getEmpleado().getNumeroIdDiscapacidad(),
                        reg.getSuelsual(), reg.getSobsuelcomremu(), reg.getPartutil(), reg.getIntgrabgen(),
                        reg.getImprentempl(), reg.getDecimoTercero(), reg.getDecimoCuarto(), reg.getFondoReserva(),
                        reg.getSalarioDigno(), reg.getOtrosIngresosGravables(), reg.getInggravconesteempl(),
                        reg.getSissalnet(), reg.getApoperiess(), reg.getAporperiessconotrosempls(),
                        reg.getDeducibleVivienda(), reg.getDeducibleSalud(), reg.getDeducibleEducacion(),
                        reg.getDeducibleAlimentacion(), reg.getDeducibleVestimenta(), reg.getExoneracionDiscapacidad(),
                        reg.getExoneracionTerceraEdad(), reg.getBaseImponible(), reg.getImpuestoRentaCausado(),
                        reg.getValorRetenidoOtrosEmpleadores(),reg.getValorImpuestoAsumidoEmpleados(),
                        reg.getValorRetenido(),reg.getTipoGenera(),reg.getNombreEmpleador(),reg.getPeriodo(),
                        reg.getRegimenLaboral(),reg.getUnidadPresupuestaria());
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
     * Genera archivo XML de RDEP 
     * @param ruc
     * @param ejercicioFiscalId
     * @param mes
     * @param anio
     * @return 
     */
    public ByteArrayInputStream obtenerRegistrosRDEP(final String ruc, final Long ejercicioFiscalId, final Integer mes,
            final String anio) {
        try {

            List<InfoSriVO> data = impuestoRentaDao.obtenerDatosSRI(ruc, ejercicioFiscalId, mes);
            RDEP rdep = new RDEP();
            rdep.setRegistroLista(data);
            rdep.setRuc(ruc);
            rdep.setAnio(anio);

            /* init jaxb marshaller */
            JAXBContext jaxbContext = JAXBContext.newInstance(RDEP.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            /* set this flag to true to format the output */
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
           
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            marshaller.marshal(rdep, stream);
                       
            ByteArrayInputStream inStream = new ByteArrayInputStream(stream.toByteArray());
            return inStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return the listaErrores
     */
    public List<String> getListaErrores() {
        return listaErrores;
    }

    /**
     * @param listaErrores the listaErrores to set
     */
    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }
}
