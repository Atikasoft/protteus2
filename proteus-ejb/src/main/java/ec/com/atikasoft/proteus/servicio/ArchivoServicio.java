/*
 *  ArchivoServicio.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  05/02/2014
 *
 *  Copyright (C) 2014 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.ArchivoSipariDao;
import ec.com.atikasoft.proteus.dao.ArchivoSipariEmpleadoDao;
import ec.com.atikasoft.proteus.dao.ArchivoSipariNominaDao;
import ec.com.atikasoft.proteus.dao.ArchivoSipariRetencionDao;
import ec.com.atikasoft.proteus.dao.CuentaBancariaDao;
import ec.com.atikasoft.proteus.dao.NominaDetalleDao;
import ec.com.atikasoft.proteus.dao.RubroDao;
import ec.com.atikasoft.proteus.dao.UnidadPresupuestariaDao;
import ec.com.atikasoft.proteus.enums.*;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.util.UtilMatematicas;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javolution.context.ConcurrentContext;
import javolution.util.FastTable;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ArchivoServicio extends BaseServicio {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ArchivoServicio.class.getName());

    /**
     * Indica el total de servidores que se procesara la nomina en paginacion.
     */
    private static final Integer TOTAL_LOTE_SERVIDORES = 1500;

    /**
     * Indica el nombre de origen de la información para los archivos SIPARI.
     */
    private static final String SIGLAS_SISTEMA_NOMINA = "NOMI";

    /**
     * Campo para cabecera de archivos SIPARI.
     */
    private static final String CLASE_DOCUMENTO = "04";

    /**
     * Formato de fecha.
     */
    public static final String MONEDA_DOLARES = "USD";

    /**
     * Indica el separador que se utilizará para los archivos SIPARI.
     */
    private static final String SEPARADOR = ";";

    /**
     * Dao de detalle de nomina.
     */
    @EJB
    private NominaDetalleDao nominaDetalleDao;

    /**
     * Servicio de administracion de unidadPresupuestaria .
     */
    @EJB
    private UnidadPresupuestariaDao unidadPresupuestariaDao;

    /**
     * Dao del Archivo Sipari.
     */
    @EJB
    private ArchivoSipariDao archivoSipariDao;

    /**
     * Dao del Archivo Sipari de Nomina.
     */
    @EJB
    private ArchivoSipariNominaDao archivoSipariNominaDao;

    /**
     * Dao del Rubro.
     */
    @EJB
    private RubroDao rubroDao;

    /**
     * Dao del Distributivo.
     */
    @EJB
    private CuentaBancariaDao cuentaBancariaDao;

    /**
     * Dao del Archivo Sipari de Empleados.
     */
    @EJB
    private ArchivoSipariEmpleadoDao archivoSipariEmpleadoDao;

    /**
     * Dao del Archivo Sipari de Retenciones Judiciales.
     */
    @EJB
    private ArchivoSipariRetencionDao archivoSipariRetencionDao;

    private List<Rubro> rubros = null;

    /**
     * Inicializacion de datos.
     */
    @PostConstruct
    public void postConstructor() {
        try {
            rubros = rubroDao.buscarTodos();
        } catch (Exception ex) {
            ex.printStackTrace();;
        }
    }

    /**
     *
     * @param idNomina
     * @param tipo
     * @return
     * @throws ServicioException
     */
    public List<ArchivoSipari> listarArchivoSipariPorNominaYTipo(final Long idNomina, final String tipo) throws
            ServicioException {
        try {
            return archivoSipariDao.buscarTodosPorNominaYTipo(idNomina, tipo);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     *
     * @param a
     * @throws ServicioException
     */
    public void actualizarArchivoSipari(final ArchivoSipari a) throws ServicioException {
        try {
            archivoSipariDao.actualizar(a);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Este metodo procesa la creacion de la cabecera de un archivo sipari.
     *
     * @param archivos
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<String> guardarArchivoSipari(final List<ArchivoSipari> archivos) throws ServicioException {
        List<String> mensajes = new ArrayList<String>();
        System.out.println("guardarArchivoSipari.1");
        try {
            ArchivoSipari archivoCab;
            int fila = 0;
            long contador = 0;
            List<NominaDetalle> detalles = new FastTable<NominaDetalle>();
//            List<ArchivoSipariNomina> registrosArchivoNomina;
            List<ArchivoSipariEmpleado> registrosArchivoEmpleados;
            List<ArchivoSipariRetencion> registrosArchivoRetencion;
            if (!archivos.isEmpty()) {
                for (ArchivoSipari p : archivos) {
                    archivoCab = p;
                    if (p.getId() == null && p.getSeleccionado() != null && p.getSeleccionado()) {
                        char tipo = p.getTipo().charAt(0);
                        boolean hayDatos = !nominaDetalleDao.buscarDetallesParaArchivoSipari(
                                p.getNomina().getId(), p.getNomina().getInstitucionEjercicioFiscalId(), null, tipo == 'R'
                                ? true : false, p.getNomina().getTipoNomina().getCobertura(), fila, 1).isEmpty();
                        if (!hayDatos) {
                            LOG.log(Level.INFO,
                                    "Nomina sin detalles para este tipo de archivo. No se puede crear el archivo !!!{0} de la nómina {1} ",
                                    new Object[]{ArchivoSipariEnum.obtenerDescripcion(p.getTipo()),
                                        archivoCab.getNomina().getDescripcion()});
                            mensajes.add(UtilCadena.concatenar(
                                    ArchivoSipariEnum.obtenerDescripcion(archivoCab.getTipo()), "=",
                                    "Nomina sin detalles para este tipo de archivo. No se puede crear el archivo !!! de la nómina= ",
                                    archivoCab.getNomina().getDescripcion()));
                            return mensajes;
                        }
                        System.out.println("guardarArchivoSipari.2");
                        List<String> sociedades = unidadPresupuestariaDao.buscarSociedades(p.getNomina().
                                getInstitucionEjercicioFiscalId());
                        if (sociedades.isEmpty()) {
                            mensajes.add(UtilCadena.concatenar(" ", "No existen sociedades vigentes !!!"));
                            return mensajes;
                        } else {
                            System.out.println("guardarArchivoSipari.3:" + sociedades.size());
                            archivoCab.setNombre(generarNombreArchivo(archivoCab.getTipo(), archivoCab.getNomina()));
                            for (String sociedad : sociedades) {
                                System.out.println("SOCIEDAD:" + sociedad);
                                fila = 0;
                                detalles.clear();
                                switch (tipo) {
                                    case 'N':
                                        detalles.addAll(nominaDetalleDao.buscarDetallesParaArchivoSipari(
                                                p.getNomina().getId(), p.getNomina().getInstitucionEjercicioFiscalId(),
                                                sociedad, false, p.getNomina().getTipoNomina().getCobertura(),
                                                fila, TOTAL_LOTE_SERVIDORES));
                                        if (!detalles.isEmpty()) {
                                            if (archivoCab.getId() == null) {
                                                archivoCab = archivoSipariDao.crearArchivoSipari(archivoCab);
                                            }
                                            guardarDetallesSIPARINomina(detalles, sociedad, archivoCab, fila);
                                        }
                                        break;
                                    case 'E':
                                        List<Servidor> detServidores = nominaDetalleDao.
                                                buscarDetallesParaArchivoSipariEmpleados(
                                                p.getNomina().getId(), p.getNomina().getInstitucionEjercicioFiscalId(),
                                                sociedad, false, archivoCab.getNomina().getTipoNomina().getCobertura(),
                                                fila, TOTAL_LOTE_SERVIDORES);
                                        if (!detServidores.isEmpty()) {
                                            archivoCab = archivoSipariDao.crear(archivoCab);
                                            registrosArchivoEmpleados = guardarDetallesSIPARIEmpleados(detServidores,
                                                    sociedad, archivoCab, fila);
                                            if (!registrosArchivoEmpleados.isEmpty()) {
                                                contador = contador + detalles.size();
                                            } else {
                                                mensajes.add(
                                                        UtilCadena.concatenar(ArchivoSipariEnum.obtenerDescripcion(archivoCab.
                                                        getTipo()), "=",
                                                        "No existen detalles para el archivo =", archivoCab.getNombre()));
                                            }
                                        }
                                        break;
                                    case 'R':
                                        detalles.addAll(nominaDetalleDao.buscarDetallesParaArchivoSipari(
                                                p.getNomina().getId(), p.getNomina().getInstitucionEjercicioFiscalId(),
                                                sociedad, true, p.getNomina().getTipoNomina().getCobertura(),
                                                fila, TOTAL_LOTE_SERVIDORES));
                                        if (!detalles.isEmpty()) {
                                            System.out.println(" antes de guardar registros recuperados=" + detalles.
                                                    size());
                                            archivoCab = archivoSipariDao.crear(archivoCab);
                                            registrosArchivoRetencion = guardarDetallesSIPARIRetencion(detalles,
                                                    sociedad, archivoCab, fila);
                                            if (!registrosArchivoRetencion.isEmpty()) {
                                                contador = contador + detalles.size();
                                            } else {
                                                mensajes.add(
                                                        UtilCadena.concatenar(ArchivoSipariEnum.obtenerDescripcion(archivoCab.
                                                        getTipo()), "=",
                                                        "No existen detalles para el arcvhivo =", archivoCab.getNombre()));
                                            }
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        } catch (ServicioException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
        return mensajes;
    }

    /**
     * Inserta los registros en la base de datos
     *
     * @param detalles
     * @param uni
     * @param archivoCab
     * @param fila
     * @return
     * @throws DaoException
     * @throws ServicioException
     */
    private void guardarDetallesSIPARINomina(List<NominaDetalle> detalles,
            final String sociedad, final ArchivoSipari archivoCab, int fila) throws DaoException, ServicioException {
        final ArchivoSipariNomina archivoEncabezado = new ArchivoSipariNomina();
        archivoEncabezado.setArchivoSipari(archivoCab);
        archivoEncabezado.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
        archivoEncabezado.setFechaCreacion(new Date());
        archivoEncabezado.setVigente(Boolean.TRUE);
        archivoEncabezado.setEncabezado(generarCabeceraArchivo(archivoCab.getTipo(), archivoCab.getNomina(), sociedad));
        archivoSipariNominaDao.crearEncabezado(archivoEncabezado);

        ConcurrentContext ctx = ConcurrentContext.enter();
        try {
            while (!detalles.isEmpty()) {
                final Set<NominaDetalle> segmento =
                        Collections.synchronizedSet(new HashSet<NominaDetalle>(detalles));
                ctx.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            guardarDetallesNominaAsincrono(segmento, archivoEncabezado, archivoCab, sociedad);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                fila += TOTAL_LOTE_SERVIDORES;
                if (detalles.size() == TOTAL_LOTE_SERVIDORES) {
                    detalles.clear();
                    detalles = nominaDetalleDao.buscarDetallesParaArchivoSipari(archivoCab.getNomina().getId(),
                            archivoCab.getNomina().getInstitucionEjercicioFiscalId(), sociedad, false, archivoCab.
                            getNomina().getTipoNomina().getCobertura(), fila, TOTAL_LOTE_SERVIDORES);
                } else {
                    detalles.clear();
                }
            }
        } finally {
            System.out.print("LOTE ENVIADO A PROCESAR....");
            ctx.exit();
            System.out.print("PROCESO FINALIZADO....");
        }
    }

    /**
     *
     * @param detalles
     * @param lista
     * @param archivoEncabezado
     * @param archivoCab
     * @param sociedad
     * @throws DaoException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void guardarDetallesNominaAsincrono(Set<NominaDetalle> detalles, ArchivoSipariNomina archivoEncabezado,
            ArchivoSipari archivoCab, final String sociedad) throws DaoException {
        int i = 1;
        for (NominaDetalle dd : detalles) {
            if (dd.getCodigoRubroIngreso() != null) {
                registrarArchivoNominaIngresos(archivoEncabezado, archivoCab, dd, sociedad, false);
            } else if (dd.getCodigoRubroDescuentos() != null) {
                registrarArchivoNominaDescuentos(archivoEncabezado, archivoCab, dd, false);
            } else if (dd.getCodigoRubroAportes() != null) {
                registrarArchivoNominaDescuentos(archivoEncabezado, archivoCab, dd, true);
                registrarArchivoNominaIngresos(archivoEncabezado, archivoCab, dd, sociedad, true);
            }
            if (i % 100 == 0) {
                System.out.println("nomina: " + i + " de " + detalles.size());
                archivoSipariNominaDao.flush();
                archivoSipariNominaDao.clear();
            }
            i++;
        }
    }

    /**
     * Buscar un rubro desde memoria.
     *
     * @param codigoRubro
     * @return
     */
    private Rubro buscarRubro(String codigoRubro) {
        Rubro rubro = null;
        for (Rubro r : rubros) {
            if (r.getCodigo().equals(codigoRubro)) {
                rubro = r;
                break;
            }
        }
        return rubro;
    }

    /**
     *
     * @param archivoEncabezado
     * @param archivoCab
     * @param dd
     * @param sociedad
     * @return
     * @throws DaoException
     */
    private void registrarArchivoNominaIngresos(final ArchivoSipariNomina archivoEncabezado,
            final ArchivoSipari archivoCab, final NominaDetalle dd, final String sociedad, final Boolean aportePatronal)
            throws DaoException {
        Rubro rubro;
        if (aportePatronal) {
            rubro = buscarRubro(dd.getCodigoRubroAportes());
        } else {
            rubro = buscarRubro(dd.getCodigoRubroIngreso());
        }
        ArchivoSipariNomina archivo = archivoSipariNominaDao.buscarIngresosPorPartida(archivoCab.getId(), dd.
                getNumeroIdentificacion(), UtilCadena.concatenar(dd.getDistributivoDetalle().getGrupoPresupuestario(),
                rubro.getPartida().getPartida()));
        if (archivo == null) {
            archivo = new ArchivoSipariNomina();
            archivo.setEncabezadoPadre(archivoEncabezado);
            archivo.setArchivoSipari(archivoCab);
            archivo.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
            archivo.setFechaCreacion(new Date());
            archivo.setVigente(Boolean.TRUE);
            archivo.setCodigoEmpleado(dd.getNumeroIdentificacion());
            archivo.setCuentaContable(null);
            archivo.setFondo("01");
            archivo.setCertificacionPresupuestaria(dd.getCertificacionPresupuestaria());
            UnidadPresupuestaria uni = dd.getDistributivoDetalle().getUnidadPresupuestaria();
            archivo.setCentroCosto(uni.getCentroCosto());
            archivo.setProyecto(uni.getProyecto());
            archivo.setCentroGestor(uni.getCentroGestor());
            archivo.setFondo(uni.getFondo());
            archivo.setPosicionPresupuestal(dd.getDistributivoDetalle().getGrupoPresupuestario());
            archivo.setPrograma(uni.getPrograma());
            archivo.setSociedad(sociedad);

            //List<Rubro> rubros;
            if (aportePatronal) {
                archivo.setImporte(dd.getValorDescontadoRubroAportes());
                archivo.setConceptoNomina(rubro.getPartida().getCodigo());
                archivo.setTexto(rubro.getPartida().getNombre());
                archivo.setPosicionPresupuestal(UtilCadena.concatenar(archivo.getPosicionPresupuestal(), rubro.
                        getPartida().
                        getPartida()));
            } else {
                archivo.setImporte(dd.getValorDescontadoRubroIngreso());
                archivo.setConceptoNomina(rubro.getPartida().getCodigo());
                archivo.setTexto(rubro.getPartida().getNombre());
                archivo.setPosicionPresupuestal(UtilCadena.concatenar(archivo.getPosicionPresupuestal(), rubro.
                        getPartida().
                        getPartida()));
            }
            archivo.setDebeHaber("D");
            //Anticipo: si el concepto es de compensación Anticipo Empleado se indica con  A
            if (rubro != null && rubro.getTipo().equals(TipoRubroEnum.INGRESO_ANTICIPOS.getCodigo())) {
                archivo.setAnticipo("A");
            }
            archivoSipariNominaDao.crear(archivo);
        } else {
            if (aportePatronal) {
                archivo.setImporte(archivo.getImporte().add(dd.getValorDescontadoRubroAportes()));
            } else {
                archivo.setImporte(archivo.getImporte().add(dd.getValorDescontadoRubroIngreso()));
            }
            archivoSipariNominaDao.actualizar(archivo);
        }
        //archivoSipariNominaDao.flush();
    }

    /**
     *
     * @param archivoEncabezado
     * @param archivoCab
     * @param dd
     * @param sociedad
     * @return
     * @throws DaoException
     */
    private ArchivoSipariNomina registrarArchivoNominaDescuentos(final ArchivoSipariNomina archivoEncabezado,
            final ArchivoSipari archivoCab, final NominaDetalle dd, final Boolean aportePatronal) throws DaoException {
        ArchivoSipariNomina archivo = new ArchivoSipariNomina();
        archivo.setEncabezadoPadre(archivoEncabezado);
        archivo.setArchivoSipari(archivoCab);
        archivo.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
        archivo.setFechaCreacion(new Date());
        archivo.setVigente(Boolean.TRUE);
        archivo.setCodigoEmpleado(dd.getNumeroIdentificacion());
        archivo.setCuentaContable(null);
        archivo.setFondo("01");
        archivo.setDebeHaber("H");
        archivo.setAcreedor(dd.getNumeroIdentificacionBeneficiario());
        // Beneficiario: si el concepto de Nomina es Retención Judicial. 
        if (dd.getRetencionJudicial()) {
            archivo.setBeneficiario("S");
            if (aportePatronal) {
                archivo.setConceptoNomina(dd.getCodigoRubroAportes());
                archivo.setImporte(dd.getValorDescontadoRubroAportes());
                archivo.setTexto(UtilCadena.concatenar(dd.getNombreRubroAportes(), " - ", dd.getNumeroIdentificacion()));
            } else {
                archivo.setConceptoNomina(dd.getCodigoRubroDescuentos());
                archivo.setImporte(dd.getValorDescontadoRubroDescuentos());
                archivo.setTexto(UtilCadena.concatenar(dd.getNombreRubroDescuentos(), " - ",
                        dd.getNumeroIdentificacion()));
            }
        } else {
            if (aportePatronal) {
                archivo.setConceptoNomina(dd.getCodigoRubroAportes());
                archivo.setImporte(dd.getValorDescontadoRubroAportes());
                archivo.setTexto(dd.getNombreRubroAportes());
            } else {
                archivo.setConceptoNomina(dd.getCodigoRubroDescuentos());
                archivo.setImporte(dd.getValorDescontadoRubroDescuentos());
                archivo.setTexto(dd.getNombreRubroDescuentos());
            }
        }
        return archivoSipariNominaDao.crear(archivo);
    }

    /**
     * Inserta los registros en la base de datos en la tabla para reporte sipari de empleados
     *
     * @param detalles
     * @param uni
     * @param archivoCab
     * @param fila
     * @return
     * @throws DaoException
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private List<ArchivoSipariEmpleado> guardarDetallesSIPARIEmpleados(final List<Servidor> detalles,
            final String sociedad, ArchivoSipari archivoCab, int fila) throws DaoException, ServicioException {
        List<ArchivoSipariEmpleado> lista = new FastTable<ArchivoSipariEmpleado>();
        ArchivoSipariEmpleado archivoEncabezado = new ArchivoSipariEmpleado();
        archivoEncabezado.setArchivoSipari(archivoCab);
        archivoEncabezado.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
        archivoEncabezado.setFechaCreacion(new Date());
        archivoEncabezado.setVigente(Boolean.TRUE);
        archivoEncabezado.setEncabezado(generarCabeceraArchivo(archivoCab.getTipo(), archivoCab.getNomina(), sociedad));
        lista.add(archivoEncabezado);
        archivoSipariEmpleadoDao.crear(archivoEncabezado);
        while (!detalles.isEmpty()) {
            for (Servidor dd : detalles) {
                ArchivoSipariEmpleado archivo = new ArchivoSipariEmpleado();
                archivo.setArchivoSipari(archivoCab);
                archivo.setEncabezadoPadre(archivoEncabezado);
                archivo.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
                archivo.setFechaCreacion(new Date());
                archivo.setVigente(Boolean.TRUE);
                archivo.setEmpleado(dd.getNumeroIdentificacion());

                archivo.setApellido1(dd.getApellidos().length() > 35
                        ? dd.getApellidos().substring(0, 35) : dd.getApellidos());
                archivo.setNombre1(dd.getNombres().length() > 35
                        ? dd.getNombres().substring(0, 35) : dd.getNombres());
                if (dd.getCalleDomicilio() != null) {
                    archivo.setDireccion(dd.getCalleDomicilio().length() > 60 ? dd.getCalleDomicilio().substring(0, 60)
                            : dd.getCalleDomicilio());
                }
                if (dd.getTelefono() != null) {
                    archivo.setTelefono(dd.getTelefono().length() > 16 ? dd.getTelefono().substring(0, 16) : dd.
                            getTelefono());
                }
                if (dd.getMail() != null) {
                    archivo.setCorreoElectronico(dd.getMail().length() > 30 ? dd.getMail().substring(0, 30)
                            : dd.getMail());
                }
                if (dd.getEstadoPersonal() != null) {
                    archivo.setEstado(dd.getEstadoPersonal().getNombre());
                }
                List<CuentaBancaria> cuentas = cuentaBancariaDao.buscarVigenteParaNominaPorServidor(dd.getId());
                if (!cuentas.isEmpty()) {
                    archivo.setPaisBanco(cuentas.get(0).getBanco().getPais());
                    archivo.setClaveBanco(cuentas.get(0).getBanco().getCodigo());
                    archivo.setTipoCuenta(cuentas.get(0).getTipoCuenta().equals(TipoCuentaEnum.AHORRO.getCodigo())
                            ? "1" : "2");
                    archivo.setCuentaBanco(cuentas.get(0).getNumerCuenta());
                }
                archivo.setSociedad(sociedad);
                archivo.setPais("EC");
                if(TipoIdentificacionEnum.CEDULA.getCodigo().equals(dd.getTipoIdentificacion())){
                    archivo.setRegion(dd.getNumeroIdentificacion().substring(0, 2));
                } else {
                    archivo.setRegion("17");
                }
                archivo.setIdentificacionFiscal(dd.getNumeroIdentificacion());
                if (dd.getTipoIdentificacion().equals(TipoDocumentoEnum.CEDULA.getNemonico())) {
                    archivo.setTipoIdentificacion("02");
                } else if (dd.getTipoIdentificacion().equals(TipoDocumentoEnum.PASAPORTE.getNemonico())) {
                    archivo.setTipoIdentificacion("03");
                } else {
                    archivo.setTipoIdentificacion("01");
                }
                lista.add(archivo);
                archivoSipariEmpleadoDao.crear(archivo);
            }
            fila += TOTAL_LOTE_SERVIDORES;

            detalles.clear();
            detalles.addAll(nominaDetalleDao.buscarDetallesParaArchivoSipariEmpleados(
                    archivoCab.getNomina().getId(), archivoCab.getNomina().getInstitucionEjercicioFiscalId(), sociedad,
                    archivoCab.getTipo().equals(ArchivoSipariEnum.RETENCIONES.getCodigo()), archivoCab.getNomina().
                    getTipoNomina().getCobertura(),
                    fila, TOTAL_LOTE_SERVIDORES));
        }
        return lista;
    }

    /**
     * Inserta los registros en la base de datos en la tabla para reporte sipari para retenciones judiciales.
     *
     * @param detalles
     * @param uni
     * @param archivoCab
     * @param fila
     * @return
     * @throws DaoException
     * @throws ServicioException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private List<ArchivoSipariRetencion> guardarDetallesSIPARIRetencion(final List<NominaDetalle> detalles,
            final String sociedad, final ArchivoSipari archivoCab, int fila) throws DaoException,
            ServicioException {
        List<ArchivoSipariRetencion> lista = new FastTable<ArchivoSipariRetencion>();
        while (!detalles.isEmpty()) {
            for (NominaDetalle dd : detalles) {
                ArchivoSipariRetencion archivo = new ArchivoSipariRetencion();
                archivo.setArchivoSipari(archivoCab);
                archivo.setUsuarioCreacion(archivoCab.getUsuarioCreacion());
                archivo.setFechaCreacion(new Date());
                archivo.setVigente(Boolean.TRUE);
                archivo.setCodigoEmpleado(dd.getNumeroIdentificacion());
                archivo.setSociedad(sociedad);
                archivo.setCodigoBeneficiario(dd.getNumeroIdentificacionBeneficiario());
                if (dd.getNombresBeneficiario() != null) {
                    archivo.setNombre(dd.getNombresBeneficiario().trim().length() > 70 ? dd.getNombresBeneficiario().
                            trim().
                            substring(0, 72) : dd.getNombresBeneficiario().trim());
                }
                archivo.setApellido("");
                archivo.setConceptoNomina(dd.getCodigoRubroDescuentos() == null ? dd.getCodigoRubroAportes() : dd.
                        getCodigoRubroDescuentos());
                archivo.setImporte(dd.getCodigoRubroDescuentos() == null ? dd.getValorDescontadoRubroAportes() : dd.
                        getValorDescontadoRubroDescuentos());
                if (archivo.getCodigoBeneficiario() != null) {
//                    List<CuentaBancaria> cuentas = cuentaBancariaDao.buscarVigenteParaNominaPorBeneficiarioEspecial(
//                            archivo.getCodigoBeneficiario());
//                    if (cuentas.isEmpty()) {
//                        cuentas = cuentaBancariaDao.buscarVigenteParaNominaPorBeneficiarioInstitucional(
//                                archivo.getCodigoBeneficiario());
//                    }
//                    if (!cuentas.isEmpty()) {
//                        archivo.setPaisBanco(cuentas.get(0).getBanco().getPais());
//                        archivo.setClaveBanco(cuentas.get(0).getBanco().getCodigo());
//                        archivo.setTipoCuenta(cuentas.get(0).getTipoCuenta().equals(TipoCuentaEnum.AHORRO.getCodigo())
//                                ? "1" : "2");
//                        archivo.setCuentaBanco(cuentas.get(0).getNumerCuenta());
//                    }
                }
                archivoSipariRetencionDao.crear(archivo);
                lista.add(archivo);
            }
            fila += TOTAL_LOTE_SERVIDORES;
            detalles.clear();
            detalles.addAll(nominaDetalleDao.buscarDetallesParaArchivoSipari(
                    archivoCab.getNomina().getId(), archivoCab.getNomina().getInstitucionEjercicioFiscalId(), sociedad,
                    archivoCab.getTipo().equals(ArchivoSipariEnum.RETENCIONES.getCodigo()), archivoCab.getNomina().
                    getTipoNomina().getCobertura(),
                    fila, TOTAL_LOTE_SERVIDORES));
        }
        return lista;
    }

    /**
     *
     * @param idArchivoPadre
     * @param tipoArchivo
     * @return
     * @throws DaoException
     */
    public List<List<String>> armarArchivoSipari(final Long idArchivoPadre, final String tipoArchivo) throws
            DaoException {
        char tipo = tipoArchivo.charAt(0);
        List<List<String>> lista = null;
        switch (tipo) {
            case 'N':
                lista = armarArchivoSipariNomina(idArchivoPadre);
                break;
            case 'E':
                lista = armarArchivoSipariEmpleados(idArchivoPadre);
                break;
            case 'R':
                lista = armarArchivoSipariRetenciones(idArchivoPadre);
                break;
        }
        return lista;
    }

    /**
     *
     * @param archivoPadre
     * @return
     * @throws DaoException
     */
    public List<List<String>> armarArchivoSipariNomina(final Long archivoPadre) throws DaoException {
        int fila = 0;
        List<List<String>> lineas = new FastTable<List<String>>();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("######0.00", otherSymbols);
        List<ArchivoSipariNomina> listaEncabezados = archivoSipariNominaDao.buscarEncabezadosPorPadre(archivoPadre);
        if (!listaEncabezados.isEmpty()) {
            for (ArchivoSipariNomina encabez : listaEncabezados) {
                fila = 0;
                List<String> lineaEncabezado = Arrays.asList(encabez.getEncabezado().split(";"));
                lineas.add(lineaEncabezado);
                List<ArchivoSipariNomina> lista = archivoSipariNominaDao.buscarDetallesParaArchivoSipariNomina(
                        archivoPadre, encabez.getId(), fila, TOTAL_LOTE_SERVIDORES);

                while (!lista.isEmpty()) {
                    for (ArchivoSipariNomina a : lista) {
                        List<String> linea = new FastTable<String>();
                        linea.add(a.getCodigoEmpleado().length() > 13 ? a.getCodigoEmpleado().substring(0, 13)
                                : a.getCodigoEmpleado());
                        linea.add(a.getConceptoNomina());
                        if (a.getCuentaContable() != null) {
                            linea.add(a.getCuentaContable().length() > 10 ? a.getCuentaContable().substring(0,
                                    10) : a.getCuentaContable());
                        } else {
                            linea.add("");
                        }
                        linea.add(a.getDebeHaber());
                        linea.add(a.getAcreedor() != null ? a.getAcreedor() : "");
                        if (a.getBeneficiario() != null) {
                            linea.add(a.getBeneficiario().length() > 1 ? a.getBeneficiario().substring(0, 1)
                                    : a.getBeneficiario());
                        } else {
                            linea.add("");
                        }
                        if (a.getAnticipo() != null) {
                            linea.add(a.getAnticipo().length() > 1 ? a.getAnticipo().substring(0, 1)
                                    : a.getAnticipo());
                        } else {
                            linea.add("");
                        }
                        if (a.getDebeHaber().equals("D")) {
                            linea.add(a.getCentroCosto().length() > 4 ? a.getCentroCosto().substring(0, 4) : a.
                                    getCentroCosto());
                            linea.add(a.getCentroGestor().length() > 8 ? a.getCentroGestor().substring(0, 8) : a.
                                    getCentroGestor());
                            linea.add(a.getPosicionPresupuestal().length() > 7 ? a.getPosicionPresupuestal().
                                    substring(0, 7) : a.getPosicionPresupuestal());
                            linea.add(a.getPrograma() != null ? a.getPrograma() : "");
                            linea.add(a.getFondo().length() > 2 ? a.getFondo().substring(0, 2) : a.getFondo());
                            linea.add(a.getProyecto().length() > 15 ? a.getProyecto().substring(0, 15) : a.getProyecto());
                            linea.add(a.getCertificacionPresupuestaria());
                        } else {
                            linea.add("");
                            linea.add("");
                            linea.add("");
                            linea.add("");
                            linea.add("");
                            linea.add("");
                            linea.add("");
                        }
                        linea.add(a.getImporte() == null ? df.format(BigDecimal.ZERO) : df.format(a.getImporte()));
                        linea.add(a.getTexto().length() > 50 ? a.getTexto().substring(0, 50) : a.getTexto());
                        lineas.add(linea);
                    }
                    fila = fila + TOTAL_LOTE_SERVIDORES;
                    lista = archivoSipariNominaDao.buscarDetallesParaArchivoSipariNomina(archivoPadre,
                            encabez.getId(), fila, TOTAL_LOTE_SERVIDORES);
                }
            }
            return lineas;
        } else {
            throw new DaoException("No hay registros para el archivo =" + archivoPadre);
        }
    }

    /**
     *
     * @param archivoPadre
     * @throws DaoException
     */
    private List<List<String>> armarArchivoSipariEmpleados(final Long archivoPadre) throws DaoException {
        int fila = 0;
        List<List<String>> lineas = new FastTable<List<String>>();
        StringBuilder cadena;
        List<ArchivoSipariEmpleado> listaEncabezados = archivoSipariEmpleadoDao.buscarEncabezadosPorPadre(archivoPadre);
        if (!listaEncabezados.isEmpty()) {
            for (ArchivoSipariEmpleado encabez : listaEncabezados) {
//                lineas.add(encabez.getEncabezado());
                fila = 0;
                List<ArchivoSipariEmpleado> lista = archivoSipariEmpleadoDao.buscarDetallesParaArchivoSipariEmpleados(
                        archivoPadre, encabez.getId(), fila, TOTAL_LOTE_SERVIDORES);
                while (!lista.isEmpty()) {
                    for (ArchivoSipariEmpleado a : lista) {
                        List<String> linea = new FastTable<String>();
                        if (a.getEncabezado() != null) {
                            linea.add(a.getEncabezado());
                        } else {
                            linea.add(a.getEmpleado() == null ? "" : a.getEmpleado());
                            linea.add(a.getSociedad() == null ? "" : a.getSociedad());
                            linea.add(a.getApellido1() == null ? "" : a.getApellido1());
                            linea.add(a.getNombre1() == null ? "" : a.getNombre1());
                            linea.add(a.getCampoBusqueda() == null ? "" : a.getCampoBusqueda());
                            linea.add(a.getDireccion() == null ? "" : a.getDireccion());
                            linea.add(a.getPais() == null ? "EC" : a.getPais());
                            linea.add(a.getRegion() == null ? "" : a.getRegion());
                            linea.add(a.getTelefono() == null ? "" : a.getTelefono());
                            linea.add(a.getCorreoElectronico() == null ? "" : a.getCorreoElectronico());
                            linea.add(a.getTipoIdentificacion() == null ? "" : a.getTipoIdentificacion());
                            linea.add(a.getIdentificacionFiscal() == null ? "" : a.getIdentificacionFiscal());
                            linea.add(a.getPaisBanco() == null ? "" : a.getPaisBanco());
                            linea.add(a.getClaveBanco() == null ? "" : a.getClaveBanco());
                            linea.add(a.getCuentaBanco() == null ? "" : a.getCuentaBanco());
                            linea.add(a.getTipoCuenta() == null ? "" : a.getTipoCuenta());
                            linea.add(a.getEstado() == null ? "" : a.getEstado());
                        }
                        lineas.add(linea);
                    }
                    fila = fila + TOTAL_LOTE_SERVIDORES;
                    lista = archivoSipariEmpleadoDao.buscarDetallesParaArchivoSipariEmpleados(archivoPadre,
                            encabez.getId(), fila, TOTAL_LOTE_SERVIDORES);
                }
            }
        } else {
            throw new DaoException("No hay registros para el archivo=" + archivoPadre);
        }
        return lineas;
    }

    /**
     *
     * @param archivo
     * @throws DaoException
     */
    private List<List<String>> armarArchivoSipariRetenciones(final Long archivoPadre) throws DaoException {
        int fila = 0;
        StringBuilder cadena;
        List<List<String>> lineas = new FastTable<List<String>>();
        List<ArchivoSipariRetencion> lista = archivoSipariRetencionDao.buscarDetallesParaArchivoSipariRetencions(
                archivoPadre, fila, TOTAL_LOTE_SERVIDORES);
        if (!lista.isEmpty()) {
            while (!lista.isEmpty()) {
                for (ArchivoSipariRetencion a : lista) {
                    List<String> linea = new FastTable<String>();
                    if (a.getEncabezado() != null) {
                        linea.add(a.getEncabezado());
                    } else {
                        linea.add(a.getSociedad() == null ? "" : a.getSociedad());
                        linea.add(a.getCodigoEmpleado() == null ? "" : a.getCodigoEmpleado());
                        linea.add(a.getCodigoBeneficiario() == null ? "" : a.getCodigoBeneficiario());
                        linea.add(a.getApellido() == null ? "" : a.getApellido());
                        linea.add(a.getNombre() == null ? "" : a.getNombre());
                        linea.add("");
                        linea.add("");
                        linea.add(a.getPaisBanco() == null ? "EC" : a.getPaisBanco());
                        linea.add(a.getClaveBanco() == null ? "" : a.getClaveBanco());
                        linea.add(a.getCuentaBanco() == null ? "" : a.getCuentaBanco());
                        linea.add(a.getTipoCuenta() == null ? "" : a.getTipoCuenta());
                    }
                    lineas.add(linea);
                }
                fila = fila + TOTAL_LOTE_SERVIDORES;
                lista = archivoSipariRetencionDao.buscarDetallesParaArchivoSipariRetencions(archivoPadre,
                        fila, TOTAL_LOTE_SERVIDORES);
            }

        } else {
            throw new DaoException("No hay registros para el archivo id=" + archivoPadre);
        }
        return lineas;
    }

    /**
     *
     * @param tipo
     * @param nomina
     * @return arreglo de dos elementos, posicion 0 ==> nombre y posicion 1 ==> encabezado
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarNombreArchivo(final String tipo, final Nomina nomina)
            throws ServicioException {
        char t = tipo.charAt(0);
        StringBuilder nombre = new StringBuilder("");
        String numeroPeriodo = nomina.getPeriodoNomina().getNumero().toString();
        numeroPeriodo = numeroPeriodo.length() > 1 ? numeroPeriodo : UtilCadena.concatenar("0", numeroPeriodo);
        try {
            nombre.append(nomina.getInstitucionEjercicioFiscal().getEjercicioFiscal().getNombre()).append("-");
            nombre.append(UtilCadena.reemplazarEspacios(numeroPeriodo, "")).append("-");

            switch (t) {
                case 'N':
                    nombre.append(ArchivoSipariEnum.NOMINA.getDescripcion()).append("-");
                    break;
                case 'E':
                    nombre.append(ArchivoSipariEnum.EMPLEADOS.getDescripcion()).append("-");
                    break;
                case 'R':
                    nombre.append(ArchivoSipariEnum.RETENCIONES.getDescripcion()).append("-");
                    break;
            }
            nombre.append(UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre().trim(), "")).append("-");
            nombre.append(UtilFechas.formatearCompleto1(new Date()));
            nombre.append(".csv");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, " Problemas en definir nombre de archivo", e);
            throw new ServicioException(e);
        }
        return nombre.toString();
    }

    /**
     *
     * @param tipo
     * @param nomina
     * @param sociedad
     * @return arreglo de dos elementos, posicion 0 ==> nombre y posicion 1 ==> encabezado
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public String generarCabeceraArchivo(final String tipo, final Nomina nomina, final String sociedad)
            throws ServicioException {
        char t = tipo.charAt(0);
        StringBuilder cabecera = new StringBuilder();
        try {
            switch (t) {
                case 'N':
                    if (sociedad.trim().length() > 4) {
                        cabecera.append(sociedad.trim().substring(0, 4)).append(SEPARADOR);
                    } else {
                        cabecera.append(sociedad.trim()).append(SEPARADOR);
                    }
                    cabecera.append(UtilFechas.formatear2(nomina.getFechaAprovacion())).append(SEPARADOR);
                    cabecera.append(UtilFechas.formatear2(nomina.getFechaAprovacion())).append(SEPARADOR);
                    cabecera.append(CLASE_DOCUMENTO).append(SEPARADOR);
                    cabecera.append(MONEDA_DOLARES).append(SEPARADOR);
                    cabecera.append(UtilMatematicas.rellenarConCerosIzq(nomina.getId(), 16)).append(SEPARADOR);
                    if (UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), "").length() > 50) {
                        cabecera.append(UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), "")).
                                subSequence(0, 50);
                    } else {
                        cabecera.append(UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), ""));
                    }
                    break;
                case 'E':
                    cabecera.append(UtilFechas.formatear2(new Date())).append(SEPARADOR);
                    cabecera.append(SIGLAS_SISTEMA_NOMINA).append(SEPARADOR);
                    cabecera.append(UtilFechas.formatear2(nomina.getFechaAprovacion())).append(SEPARADOR);
                    if (UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), "").length() > 50) {
                        cabecera.append(UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), "").
                                subSequence(0, 50));
                    } else {
                        cabecera.append(UtilCadena.reemplazarEspacios(nomina.getTipoNomina().getNombre(), ""));
                    }
                    break;
                case 'R':
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, " Problemas en definir cabecera de archivo", e);
            throw new ServicioException(e);
        }
        return cabecera.toString();
    }

    /**
     * Este metodo procesa elimina logicamente los archivos creados. (Solo se eliminaran las cabeceras de los archivos)
     *
     * @param archivos lista e archivos a eliminarIndependientes
     * @param usuarioActualizacion
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void eliminarArchivoSipari(final List<ArchivoSipari> archivos, final String usuarioActualizacion)
            throws ServicioException {
        try {
            for (ArchivoSipari a : archivos) {
                if (a.getId() != null) {
                    a.setVigente(Boolean.FALSE);
                    a.setFechaActualizacion(new Date());
                    a.setUsuarioActualizacion(usuarioActualizacion);
                    archivoSipariDao.actualizar(a);
                }
            }
        } catch (DaoException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }
// </editor-fold>
}
