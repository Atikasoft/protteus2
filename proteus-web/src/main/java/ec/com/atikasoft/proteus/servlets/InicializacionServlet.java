/**
 * InicializacionServlet.java Proteus V 2.0 $Revision 1.0 $
 *
 * Todos los derechos reservados. All rights reserved. This software is the confidential and proprietary information of
 * Proteus ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Proteus.
 *
 * Proteus Quito - Ecuador
 *
 * 05/11/2012
 *
 */
package ec.com.atikasoft.proteus.servlets;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.dao.ConstanteDao;
import ec.com.atikasoft.proteus.dao.CotizacionIessDao;
import ec.com.atikasoft.proteus.dao.DatoAdicionalDao;
import ec.com.atikasoft.proteus.dao.FeriadoDao;
import ec.com.atikasoft.proteus.dao.FormulaDao;
import ec.com.atikasoft.proteus.dao.NominaDao;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.RubroDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoDao;
import ec.com.atikasoft.proteus.dao.VariableDao;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.singlenton.SinglentoSistema;
import ec.com.atikasoft.proteus.temporizadores.NotificacionFinalizacionMovimientosTemporizador;
import ec.com.atikasoft.proteus.temporizadores.VacacionTemporizador;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Servlet de inicializacion.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public class InicializacionServlet extends HttpServlet {

    /**
     * Logger de clase.
     */
    private static final Logger LOG = Logger.getLogger(InicializacionServlet.class.getCanonicalName());

    /**
     * Dao de tipo de movimiento.
     */
    @EJB
    private TipoMovimientoDao tipoMovimientoDao;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Dao de constantes.
     */
    @EJB
    private ConstanteDao constanteDao;

    /**
     * Dao de variables.
     */
    @EJB
    private VariableDao variableDao;

    /**
     * Dao de formulas.
     */
    @EJB
    private FormulaDao formulaDao;

    /**
     * Dao de rubros.
     */
    @EJB
    private RubroDao rubroDao;

    /**
     * Dao de datos adicionales.
     */
    @EJB
    private DatoAdicionalDao datoAdicionalDao;

    /**
     * Dao de cotizaciones iess.
     */
    @EJB
    private CotizacionIessDao cotizacionIessDao;

    /**
     * Dao de feriados.
     */
    @EJB
    private FeriadoDao feriadoDao;

    /**
     * Timer de cambio Ejercicio Fiscal Temporizador.
     */
    @EJB
    private VacacionTemporizador vacacionTemporizador;

    /**
     * Time de notificacion por fin de movimiento de personal.
     */
    @EJB
    private NotificacionFinalizacionMovimientosTemporizador notificacionFinalizacionMovimientosTemporizador;

    /**
     *
     */
    @EJB
    private NominaDao nominaDao;

    /**
     * Inicializador del sistema.
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(final ServletConfig config) throws ServletException {
        try {
            SinglentoSistema.getInstancia().getVariablesSistema().setServletContext(config.getServletContext());
            config.getServletContext().setAttribute("USUARIOS", new ArrayList<String>());
            LOG.info("INICIANDO PROTEUS............................");
            LOG.info("*** REGISTRANDO TIPOS DE MOVIMIENTOS............................");
            registrarTiposMovimientos(config);
            LOG.info("*** REGISTRANDO PARAMETROS GLOBALES............................");
            registrarParametrosGlobales(config);
            LOG.info("*** REGISTRANDO CAMPOS PARA TIPOS DE MOVIMIENTOS............................");
            registrarCamposTipoMovimiento(config);
            LOG.info("*** REGISTRANDO DATOS DE NOMINAS............................");
            registrarDatosNominas(config);
            LOG.info("*** INICIANDO TIMERS...........................");
            nominaDao.quitarCalculando();
            iniciarTimer();
            LOG.info("INICIALIZACION OK PROTEUS........................ PowerBy AtikaSoft");

        } catch (Exception ex) {
            LOG.info("!!!!!!!! FALLO LA INICIALIZACION !!!!");
            Logger.getLogger(InicializacionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Inicia timers.
     */
    private void iniciarTimer() {
        vacacionTemporizador.iniciandoTimer();
        notificacionFinalizacionMovimientosTemporizador.iniciandoTimer();

        //alertaServicio.iniciandoTimer();
        //cambioEjercicioFiscalTemporizador.iniciandoTimer();
        //finalizacionAutomaticaTemporizador.iniciandoTimer();
        //poblarFormularioIR.iniciandoTimer();
        //poblarAsistenciaTemporizador.iniciandoTimer();
        //ausentismoTemporizador.iniciandoTimer();
        //replicacionDistributivoTemporizador.iniciandoTimer();
        //poblarNominasIRTemporizador.iniciandoTimer();
        //aletraPlanificacionVacacionMesProximo.iniciandoTimer();
    }

    private void registrarTiposMovimientos(final ServletConfig config) throws DaoException {
        List<TipoMovimiento> tipos = tipoMovimientoDao.buscarTodosActivos(true, true, true);
        guardarCache(config, CacheEnum.TIPO_MOVIMIENTO.getCodigo(), tipos);
    }

    private void registrarParametrosGlobales(final ServletConfig config) throws DaoException {
        List<ParametroGlobal> parametros = parametroGlobalDao.buscarTodos();
        guardarCache(config, CacheEnum.PARAMETROS_GLOBALES.getCodigo(), parametros);
    }

    /**
     * Registra en cache los datos usados en la ejecucion de nomina.
     *
     * @param config
     * @throws DaoException
     */
    private void registrarDatosNominas(final ServletConfig config) throws DaoException {
        List<Constante> constantes = constanteDao.buscarVigente();
        guardarCache(config, CacheEnum.CONSTANTES.getCodigo(), constantes);
        List<Variable> variables = variableDao.buscarVigente();
        guardarCache(config, CacheEnum.VARIABLES.getCodigo(), variables);
        List<Formula> formulas = formulaDao.buscarVigente();
        guardarCache(config, CacheEnum.FORMULAS.getCodigo(), formulas);
        List<Rubro> rubros = rubroDao.buscarVigente();
        guardarCache(config, CacheEnum.RUBROS.getCodigo(), rubros);
        List<DatoAdicional> datosAdicionales = datoAdicionalDao.buscarVigente();
        guardarCache(config, CacheEnum.DATOS_ADICIONALES.getCodigo(), datosAdicionales);
        List<CotizacionIess> cotizaciones = cotizacionIessDao.buscarVigente();
        guardarCache(config, CacheEnum.COTIZACIONES_IESS.getCodigo(), cotizaciones);
        List<Feriado> feriados = feriadoDao.buscarVigente();
        guardarCache(config, CacheEnum.FERIADOS.getCodigo(), feriados);

    }

    /**
     * Este método registra el nombre de los campos de todos los tipos de movimientos.
     *
     * @param config
     * @throws Exception
     */
    private void registrarCamposTipoMovimiento(final ServletConfig config) throws Exception {
        Method metodo, metodos[];
        List<String> nombresCampos = new ArrayList<String>();
        Class forName = TipoMovimiento.class;
        metodos = forName.getMethods();
        for (int i = 0; i < metodos.length; i++) {
            metodo = metodos[i];
            String nombre = metodo.getName();
            if (nombre.startsWith("get")) {
                String nNombre = nombre.substring(3, nombre.length());
                nombresCampos.add(nNombre.replaceFirst(nNombre.charAt(0) + "", (nNombre.charAt(0) + "").toLowerCase()));
            }
        }
        guardarCache(config, BaseControlador.CAMPOS_TIP_MOV, nombresCampos);

    }

    /**
     * Guarda objetos en el contexto.
     *
     * @param config Servlet config.
     * @param key Clave.
     * @param objeto Objeto a guardar.
     */
    private void guardarCache(final ServletConfig config, final String key, final Object objeto) {
        config.getServletContext().setAttribute(key, objeto);
    }

}
