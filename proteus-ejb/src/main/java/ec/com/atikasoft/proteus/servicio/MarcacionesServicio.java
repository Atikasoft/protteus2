/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.servicio;

import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.dao.EjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.InstitucionEjercicioFiscalDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.ProcesoRegistroMigracionMarcacionDao;
import ec.com.atikasoft.proteus.enums.DiasMigracionMarcacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoEjecucionProcesoMigracionMarcacionesEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.asistencia.ProcesoRegistroMigracionMarcacion;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

/**
 * Servicio para procesar marcaciones de servidores
 *
 * @author Maikel Pérez Martínez <maikel.perez@atikasoft.ec>
 */
@Stateless
@LocalBean
public class MarcacionesServicio extends BaseServicio {

    private static final Logger LOG = Logger.getLogger(MarcacionesServicio.class.getName());

    /**
     *
     */
    @EJB
    private ProcesoRegistroMigracionMarcacionDao procesoRegistroMigracionMarcacionDao;

    /**
     * 
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;
    
    /**
     * 
     */
    @EJB
    private InstitucionEjercicioFiscalDao institucionEjercicioFiscalDao;
    
    /**
     * 
     */
    @EJB
    private DistributivoDetalleDao  distributivoDetalleDao;
    
    /**
     * 
     */
    @EJB
    private MarcacionCalculoServicio marcacionCalculoServicio;
    
    /**
     * 
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Datasource.
     */
    @Resource(name = "jdbc/relojasistencia")
    private DataSource dataSource;

    /**
     * Configuracion de orden de dias cuando el primer dia de la semana se
     * configura DOM
     */
    private static final String[] DIAS_ORDEN_DOM = new String[]{
        DiasMigracionMarcacionEnum.DOMINGO.getNombre(),
        DiasMigracionMarcacionEnum.LUNES.getNombre(),
        DiasMigracionMarcacionEnum.MARTES.getNombre(),
        DiasMigracionMarcacionEnum.MIERCOLES.getNombre(),
        DiasMigracionMarcacionEnum.JUEVES.getNombre(),
        DiasMigracionMarcacionEnum.VIERNES.getNombre(),
        DiasMigracionMarcacionEnum.SABADO.getNombre()};

    /**
     * Configuracion de orden de dias cuando el primer dia de la semana se
     * configura LUN
     */
    private static final String[] DIAS_ORDEN_LUN = new String[]{
        DiasMigracionMarcacionEnum.LUNES.getNombre(),
        DiasMigracionMarcacionEnum.MARTES.getNombre(),
        DiasMigracionMarcacionEnum.MIERCOLES.getNombre(),
        DiasMigracionMarcacionEnum.JUEVES.getNombre(),
        DiasMigracionMarcacionEnum.VIERNES.getNombre(),
        DiasMigracionMarcacionEnum.SABADO.getNombre(),
        DiasMigracionMarcacionEnum.DOMINGO.getNombre()};

    /**
     * Que valor de configuracion se cargo para el dia de corte son adminitros
     * cualquiera de los 7 dias de la semana en formato LUN
     */
    private String diaSemanaEjecucion;

    /**
     * Que valor de configuracion se cargo para el primer dia de la semana solo
     * son admitidos LUN o DOM
     */
    private String primerDiaSemana;

    /**
     * Cuantos millisegundos tiene un dia
     */
    private static final long milliPorDia = 1000 * 60 * 60 * 24;

    /**
     * Hora de ejecucion del timer
     */
    private Date horaEjecucion;

    /**
     * Dia de HOY, dia en que se ejecuta el timer
     */
    private Calendar hoy;
    
    /**
     * 
     */
    private static final int LOTE_SERVIDORES = 400;

    private void iniciarParametros() throws ServicioException {
        try {
            hoy = Calendar.getInstance();
            hoy.setTime(UtilFechas.truncarFecha(hoy.getTime()));

            horaEjecucion = new Date();
            ParametroGlobal pIprimerDiaSemana = parametroGlobalDao
                    .buscarPorNemonico(ParametroGlobalEnum.PRIMER_DIA_SEMANA.getCodigo());
            if (pIprimerDiaSemana == null) {
                throw new ServicioException("El parametro  PRIMER_DIA_SEMANA no esta definido "
                );
            }
            primerDiaSemana = pIprimerDiaSemana.getValorTexto();

            if (primerDiaSemana == null || (!primerDiaSemana.equals(DiasMigracionMarcacionEnum.LUNES.getNombre())
                    && !primerDiaSemana.equals(DiasMigracionMarcacionEnum.DOMINGO.getNombre()))) {
                throw new ServicioException("Los unicos valores permitidos "
                        + "para el primer dia de la semana son LUN y DOM ");

            }

            ParametroGlobal pIdiaCorte = parametroGlobalDao
                    .buscarPorNemonico(ParametroGlobalEnum.DIA_CORTE_MARCACIONES.getCodigo());
            if (pIdiaCorte == null) {
                throw new ServicioException("El parametro DIA_CORTE_MARCACIONES no esta definido "
                );
            }
            diaSemanaEjecucion = pIdiaCorte.getValorTexto();
            if (diaSemanaEjecucion == null) {
                throw new ServicioException("Los unicos valores permitidos "
                        + "para el dia de corte son LUN, MAR, MIE, JUE, VIE, SAB y DOM ");
            }
        } catch (DaoException ex) {
            Logger.getLogger(MarcacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @throws ServicioException
     */
    public void ejecutarMigracionMarcacionesSemanales()
            throws ServicioException {
        try {
            //cargar ultima ejecucion de proceso
            boolean ejecucionEnCurso = procesoRegistroMigracionMarcacionDao
                    .ejecucionEnCurso();

            //Verificar que no exista una ejecucion en curso
            if (ejecucionEnCurso) {
                throw new ServicioException("Existe un proceso de Migracion "
                        + "de Marcaciones abierto");
            }

            iniciarParametros();

            definirNuevosBloquesDeEjecucion();

            List<InstitucionEjercicioFiscal> instituciones 
                    = administracionServicio.listarInstitucionesVigentes();
            List<DistributivoDetalle> listaServidores = null;
            int desde = 0;
            int c = 0, y = 0;
            for (InstitucionEjercicioFiscal ief : instituciones) {
                desde = 0; y = 0;
                LOG.log(Level.INFO, " INST >> "+ief.getInstitucion().getNombre());
                LOG.log(Level.INFO, " desde >> "+desde);
                listaServidores = distributivoDetalleDao
                        .buscarPorInstitucionEjercicioFiscal(ief.getId(), desde, LOTE_SERVIDORES);
                while(!listaServidores.isEmpty()){
                    LOG.log(Level.INFO, " result >> "+listaServidores.size());
                    
                    
                    
                    
                    y+=listaServidores.size();
                    desde+= LOTE_SERVIDORES;
                    listaServidores = distributivoDetalleDao
                        .buscarPorInstitucionEjercicioFiscal(ief.getId(), desde, LOTE_SERVIDORES);
                    LOG.log(Level.INFO, " desde >> "+desde);
                    LOG.log(Level.INFO, " result >> "+listaServidores.size());
                }
                LOG.log(Level.INFO, " sub >> "+y);
                c+=y;
            }
            
            LOG.log(Level.INFO, " total >> "+c);

        } catch (DaoException ex) {
            Logger.getLogger(MarcacionesServicio.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Determina para el dia de hoy que semana o bloques de procesamientos hay
     * que generar
     *
     * @throws ServicioException
     */
    private void definirNuevosBloquesDeEjecucion() throws ServicioException {
        try {
            //si a se alcanzo el dia de generacion de la semana
            if (rangoGeneracion()) {
                //semana que toca analizas a partir del dia actual definido por hoy
                Calendar[] semanaAnterior = encontrarSemanaAnterior();
                //Ultima fecha analizada en base de datos
                Calendar ultimoDiaAnalizado = fechaFinUltimaGeneracion();
                //Verificar que la semana que toca analizar este o no en la base de datos
                boolean semanaAnteriorAnalizada = false;
                if (ultimoDiaAnalizado != null) {
                    semanaAnteriorAnalizada = semanaGenerada(semanaAnterior[0], semanaAnterior[1], ultimoDiaAnalizado);
                }

                //si la semana que toca generar ya fue generada no se genera 
                //ningun bloque nuevo
                if (!semanaAnteriorAnalizada) {
                    //verifico si existe continuidad entre bloques o si hay dias
                    //de por medio
                    if (ultimoDiaAnalizado != null) {
                        Calendar primerDiaSemanaAnalisis = semanaAnterior[0];
                        long diffDias = (primerDiaSemanaAnalisis.getTimeInMillis() - ultimoDiaAnalizado.getTimeInMillis()) / milliPorDia;
                        if (diffDias > 1) {
                            //si hay dias de por medio creo un bloque de compenzacion
                            Calendar[] bloqueCompenzacion = bloqueCompenzacion(ultimoDiaAnalizado, primerDiaSemanaAnalisis);
                            ProcesoRegistroMigracionMarcacion proceso
                                    = new ProcesoRegistroMigracionMarcacion(new Date(), bloqueCompenzacion[0].getTime(), bloqueCompenzacion[1].getTime());
                            proceso.setEstado(EstadoEjecucionProcesoMigracionMarcacionesEnum.REGISTRADO.getCodigo());
                            proceso.setComentario("Compenzar");
                            procesoRegistroMigracionMarcacionDao.crear(proceso);
                        }
                    }
                    //creo el bloque o proceso para la semana que toca analizar
                    ProcesoRegistroMigracionMarcacion proceso
                            = new ProcesoRegistroMigracionMarcacion(new Date(), semanaAnterior[0].getTime(), semanaAnterior[1].getTime());
                    proceso.setEstado(EstadoEjecucionProcesoMigracionMarcacionesEnum.REGISTRADO.getCodigo());
                    proceso.setComentario("Normal");
                    procesoRegistroMigracionMarcacionDao.crear(proceso);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MarcacionesServicio.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    private void ejecutarProcesosPendientes() throws ServicioException {
        try {
            List<ProcesoRegistroMigracionMarcacion> listaProcesosPendientes
                    = procesoRegistroMigracionMarcacionDao
                    .ejecucionesPendientes();

            if (listaProcesosPendientes != null) {
                for (ProcesoRegistroMigracionMarcacion prmm
                        : listaProcesosPendientes) {
                    procesarEjecucion(prmm);
                }
            }

        } catch (DaoException ex) {
            Logger.getLogger(MarcacionesServicio.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    private void procesarEjecucion(
            final ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion)
            throws ServicioException {
        if (!procesoRegistroMigracionMarcacion.getProcesoRegistro()) {
            procesarMigracion(procesoRegistroMigracionMarcacion);
        }
        if (!procesoRegistroMigracionMarcacion.getProcesoSeleccionMarcadas()) {
            procesarSeleccionMarcadas(procesoRegistroMigracionMarcacion);
        }
        if (!procesoRegistroMigracionMarcacion.getProcesoDeterminacionAtrasos()) {
            procesarAtrasos(procesoRegistroMigracionMarcacion);
        }
        if (!procesoRegistroMigracionMarcacion.getProcesoDeterminacionHorasExt()) {
            procesarHorasExtras(procesoRegistroMigracionMarcacion);
        }
    }

    private void procesarMigracion(
            final ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion) {

    }

    private void procesarSeleccionMarcadas(
            final ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion) {

    }

    private void procesarAtrasos(
            final ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion) {

    }

    private void procesarHorasExtras(
            final ProcesoRegistroMigracionMarcacion procesoRegistroMigracionMarcacion) {

    }

    /**
     * Para el dia de corte especificado encuentra el rango de fechas que
     * delimita a la semana anterior
     *
     * @param diaCorte
     * @return
     */
    private Calendar[] encontrarSemanaAnterior() {
        Calendar diaCorteEnSemanaAnteriror = Calendar.getInstance();
        diaCorteEnSemanaAnteriror.setTime(hoy.getTime());
        diaCorteEnSemanaAnteriror.add(Calendar.DATE, -7);

        String diaNombre = DiasMigracionMarcacionEnum.obtenerDiaPorNumero(diaCorteEnSemanaAnteriror
                .get(Calendar.DAY_OF_WEEK)).getNombre();

        String[] ordenDias = primerDiaSemana.equals(DiasMigracionMarcacionEnum.LUNES.getNombre()) ? DIAS_ORDEN_LUN
                : DIAS_ORDEN_DOM;

        int posRelativaEnOrdenSemana = posCadenaEnArreglo(diaNombre, ordenDias);
        int margenDerechoDias = 7 - (posRelativaEnOrdenSemana + 1);

        Calendar diaInicioSemana = Calendar.getInstance();
        diaInicioSemana.setTime(diaCorteEnSemanaAnteriror.getTime());
        Calendar diaFinSemana = Calendar.getInstance();
        diaFinSemana.setTime(diaCorteEnSemanaAnteriror.getTime());

        if (posRelativaEnOrdenSemana > 0) {
            diaInicioSemana.add(Calendar.DATE, -posRelativaEnOrdenSemana);
        }
        if (margenDerechoDias > 0) {
            diaFinSemana.add(Calendar.DATE, margenDerechoDias);
        }
        return new Calendar[]{diaInicioSemana, diaFinSemana};
    }

    /**
     * Obtiene para el dia especificado en formato LUN el ordinal que le
     * corresponde a nivel de calendario, siendo DOM = 1
     *
     * @param nombreDia
     * @return
     */
    private int obtenerConstanteDiaCalendario(final String nombreDia) {
        return DiasMigracionMarcacionEnum.obtenerDiaPorNombre(nombreDia)
                .getNumero();
    }

    /**
     * Obtiene la posicion que ocupa una cadena dentro de un arreglo de cadenas
     *
     * @param str
     * @param args
     * @return
     */
    private int posCadenaEnArreglo(String str, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (str.equals(args[i])) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Localiza el ultimo bloque o semana generada y obtine un Calendar con la
     * fecha de fin de esa semana
     *
     * @return
     * @throws ServicioException
     */
    private Calendar fechaFinUltimaGeneracion() throws ServicioException {
        try {
            ProcesoRegistroMigracionMarcacion proceso
                    = procesoRegistroMigracionMarcacionDao
                    .ultimaEjecucion();
            if (proceso != null) {
                Calendar fecha = Calendar.getInstance();
                fecha.setTime(proceso.getFechaHasta());
                return fecha;
            }
            return null;
        } catch (DaoException ex) {
            Logger.getLogger(MarcacionesServicio.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new ServicioException(ex);
        }
    }

    /**
     * Determina el rango de fechas que son necesarias compenzar cuando la
     * semana que se va a generar no es continua a la ultima generada
     *
     * @param fechaFinUltimaGeneracion
     * @param inicioSemanaAGenerar
     * @return
     */
    private Calendar[] bloqueCompenzacion(final Calendar fechaFinUltimaGeneracion,
            final Calendar inicioSemanaAGenerar) {
        Calendar inicioBloque = Calendar.getInstance();
        inicioBloque.setTime(fechaFinUltimaGeneracion.getTime());
        inicioBloque.add(Calendar.DATE, 1);

        Calendar finBloque = Calendar.getInstance();
        finBloque.setTime(inicioSemanaAGenerar.getTime());
        finBloque.add(Calendar.DATE, -1);

        return new Calendar[]{inicioBloque, finBloque};
    }

    /**
     * Verifica que la semana definida por semanaI y semanaF contemple el dia
     * semanaAF en base a la configuracion actual para prevenir duplicados
     *
     * @param semanaI
     * @param semanaF
     * @param semanaAF
     * @return
     */
    private boolean semanaGenerada(Calendar semanaI, Calendar semanaF, Calendar semanaAF) {
        Calendar diaAjuste = Calendar.getInstance();
        diaAjuste.setTime(semanaAF.getTime());
        diaAjuste.add(Calendar.DATE, -2);
        Calendar[] semana = semanaDelDia(diaAjuste);
        return semanaI.compareTo(semana[0]) == 0 && semanaF.compareTo(semana[1]) == 0;
    }

    /**
     * Determina el inicio y fin de una semana dado un dia de la misma,
     * utilizando la configuracion actual
     *
     * @param dia
     * @return
     */
    private Calendar[] semanaDelDia(final Calendar dia) {
        String diaNombre = DiasMigracionMarcacionEnum.obtenerDiaPorNumero(dia
                .get(Calendar.DAY_OF_WEEK)).getNombre();
        String[] ordenDias = primerDiaSemana.equals(
                DiasMigracionMarcacionEnum.LUNES.getNombre()) ? DIAS_ORDEN_LUN
                        : DIAS_ORDEN_DOM;
        int posRelativaEnOrdenSemana = posCadenaEnArreglo(diaNombre, ordenDias);
        int OffsetRight = 7 - (posRelativaEnOrdenSemana + 1);

        Calendar diaInicioSemana = Calendar.getInstance();
        diaInicioSemana.setTime(dia.getTime());
        Calendar diaFinSemana = Calendar.getInstance();
        diaFinSemana.setTime(dia.getTime());

        if (posRelativaEnOrdenSemana > 0) {
            diaInicioSemana.add(Calendar.DATE, -posRelativaEnOrdenSemana);
        }
        if (OffsetRight > 0) {
            diaFinSemana.add(Calendar.DATE, OffsetRight);
        }
        return new Calendar[]{diaInicioSemana, diaFinSemana};
    }

    /**
     * Verifica si para la semana en curso el dia de hoy esta en rango de
     * generacion, o lo que es lo mismo si ya se alcanzo o se supero el dia de
     * corte
     *
     * @param hoy
     * @return
     */
    private boolean rangoGeneracion() {
        if (hoy.get(Calendar.DAY_OF_WEEK) == obtenerConstanteDiaCalendario(diaSemanaEjecucion)) {
            return true;
        }
        String diaNombreHoy = DiasMigracionMarcacionEnum.obtenerDiaPorNumero(hoy
                .get(Calendar.DAY_OF_WEEK)).getNombre();
        String[] ordenDias = primerDiaSemana.equals(
                DiasMigracionMarcacionEnum.LUNES.getNombre()) ? DIAS_ORDEN_LUN
                        : DIAS_ORDEN_DOM;
        int posHoy = posCadenaEnArreglo(diaNombreHoy, ordenDias);
        int posCorte = posCadenaEnArreglo(diaSemanaEjecucion, ordenDias);
        return posHoy > posCorte;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long cargarAsistenciasMDMQ(final Date fechaInicio, final Date fechaFin) throws ServicioException {
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cst = null;
        Long contador = 0l;
        /*LOG.log(Level.INFO, "********* cargando asistencia desde {0} hasta:{1}", new Object[]{
            UtilFechas.formatearLargo(fechaInicio), UtilFechas.formatearLargo(fechaFin)});*/
        try {
            List<String> codigos = new ArrayList<>();
            codigos.add("34052");
            codigos.add("35719");
            codigos.add("34062");
            codigos.add("34046");
            //(34052), (35719), (34062), (34046)
            conn = dataSource.getConnection();
            
           /*String q2 = " USE [RelojAsistencia_desa]; "
                    + " declare @Empleados [RelojAsistencia].[EMPLEADO_TVP]; "
                    + " insert into @Empleados ( "
                    + "       CODIGO "
                    + " ) "
                    + " values (34052), (35719), (34062), (34046);  "*/

            String q2 = "  "
                //    + " declare @Empleados [RelojAsistencia].[EMPLEADO_TVP]; "
                    + " EXEC [RelojAsistencia].[ZKS_NOVEDADES_GENERAL_V3_STP] "
                    + "       @I_Empleados = ?, "
                    + "       @I_FechaDesde= '2017/07/01', "
                    + "       @I_FechaHasta='2017/07/31' ";
            //SQLServerDataTable
            //cst = conn.prepareCall(query);
            //PreparedStatement ps1 = conn.prepareStatement(q1);
            //PreparedStatement ps2 = conn.prepareStatement(q2);
            //cst.setObject(1, codigos);
            //cst.setDate(2, new java.sql.Date(new GregorianCalendar(2017, 6, 1).getTime().getTime()));
            //cst.setDate(3, new java.sql.Date(new GregorianCalendar(2017, 6, 31).getTime().getTime()));
            //cst.setString(4, null);
            //cst.setString(5, null);
            //cst.setString(6, null);
            //cst.setString(7, null);
            //cst.setString(8, "ZKSRptMarcaciones");
            //cst.execute();
            //rs = cst.getResultSet();
            //ps1.executeUpdate();
            int i = 0;
            LOG.log(Level.INFO, "------------------------------------------------------------------");
            LOG.log(Level.INFO, "---------------> ");
            while (rs.next()) {
                LOG.log(Level.INFO, ">"+i);
                LOG.log(Level.INFO, "M1 > " + rs.getString("M1"));
                /*Asistencia a = new Asistencia();
                a.setCodigoEmpleado(Long.valueOf(rs.getString("COD_EMP")));
                a.setFecha(UtilFechas.convertirFechaStringEnDate(rs.getString("FECHA"), UtilFechas.FORMATO_FECHA_1));
                a.setTimbre1(rs.getString("M1"));
                a.setTimbre2(rs.getString("M2"));
                a.setTimbre3(rs.getString("M3"));
                a.setTimbre4(rs.getString("M4"));
                a.setTimbre5(rs.getString("M5"));
                a.setTimbre6(rs.getString("M6"));
                a.setTimbre7(rs.getString("M7"));
                a.setTimbre8(rs.getString("M8"));
                a.setTimbre9(rs.getString("M9"));
                a.setTimbre10(rs.getString("M10"));

                guardarAsistencia(a);*/
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServicioException("Error al poblar asistencia desde stp mdmq", ex);
        } finally {
            if (cst != null) {
                try {
                    cst.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    LOG.log(Level.INFO, "{0}{1}", new Object[]{ex.getMessage(), ex});
                }
            }
        }
        return contador;
    }
}
