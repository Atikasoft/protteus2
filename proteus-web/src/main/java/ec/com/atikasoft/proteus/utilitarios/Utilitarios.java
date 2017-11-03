package ec.com.atikasoft.proteus.utilitarios;

import ec.com.atikasoft.proteus.util.UtilFechas;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.text.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fernando
 */
public class Utilitarios implements Serializable {

    private ResourceBundle resource = ResourceBundle.getBundle("messages");
//       ResourceBundle bundle = ResourceBundle.getBundle(
//                "messages", locale,
//                getCurrentClassLoader(params));

    protected static final String REGISTRO_GUARDADO = "message.guardar";
    
    protected static final String REGISTRO_GUARDADO_VARIOS = "message.guardarVarios";
    
    protected static final String REGISTRO_ACTUALIZADO = "message.actualizar";

    protected static final String REGISTRO_ELIMINADO = "message.eliminar";

    protected static final String ERROR_REGISTRO_GUARDADO = "message.errorGuardar";
    
    protected static final String ERROR_DIST_DETALLE_SERVIDOR = "message.errorDistributivoDetalleServidor";

    protected static final String REGISTRO_ENVIADO = "message.enviar";

    protected static final String ERROR_ENVIAR_SOLICITUD = "message.errorEnviarSolicitud";

    protected static final String SOLICITUD_ENVIADA = "message.solicitudEnviada";
    
    protected static final String SOLICITUD_PROCESADA = "message.solicitudProcesada";
    
    protected static final String SOLICITUD_APROBADA = "message.solicitudAprobada";
    
    protected static final String SOLICITUD_NEGADA = "message.solicitudNegada";
    
    protected static final String ERROR_REGISTRO_ACTUALIZADO = "message.errorActualizar";

    protected static final String ERROR_REGISTRO_ELIMINADO = "message.errorEliminar";

    protected static final String ERROR_REGISTRO_ELIMINADO_CONSTRAINT = "message.errorEliminarConstraint";

    protected static final String REGISTRO_EXISTENTE = "message.registroExistente";

    protected static final String ESCOJER_SISTEMA = "message.errorEscojerSistema";

    protected static final String LONGITUD_PASSWORD = "message.longitudPassword";

    protected static final String CONFIRMACION_PASSWORD = "message.confirmacion";

    protected static final String LOGIN_INCORRECTO = "message.loginIncorrecto";

    protected static final String USUARIO_SESION = "message.usuarioSesion";

    protected static final int MAX_ROWS = 10;

    protected static final String PATRON_FECHA = "yyyy-MM-dd";

    protected static final String PATRON_FECHA_HORA = "yyyy-MM-dd HH:mm:ss";

    protected static final String PATRON_FECHA_OTRA = "dd/MM/yyyy";

    protected static final String COINCIDENCIAS_ENCONTRADAS = "message.coincidenciasEncontradas";

    protected static final String COINCIDENCIAS_NO_ENCONTRADAS = "message.coincidenciasNoEncontradas";

    protected static final String NADA = "message.nada";

    protected static final String ERROR_VALIDACION = "message.errorValidacion";

    protected static final String VALIDACION_CORRECTA = "message.validacionCorrecta";

    protected static final String PATH = "path";

    protected static final String PROGRAMADO = "PROGRAMACION";

    protected static final String SUSPENDIDO = "SUSPENCION";

    protected static final String REPROGRAMADO = "REPROGRAMACION";

    protected static final Long ESTADO_PERIODO_INACTIVO = 2L;

    protected static final Long ESTADO_PERIODO_ACTIVO = 1L;

    protected static final Long ESTADO_SALDO_INACTIVO = 2L;

    protected static final Long ESTADO_SALDO_ACTIVO = 1L;

    protected static final String ACCION_PERSONAL = "ACCPER";

    protected static final String SIN_SERVIDOR = "message.sinServidor";

    protected static final String COMPARAR_FECHA = "message.compararFecha";

    protected static final String COMPARAR_FECHA_DOCUMENTO = "message.compararFechaDocumento";

    protected static final String COMPARAR_FECHA_DEVENGAMIENTO = "message.compararFechaDevengamiento";

    protected static final String COMPARAR_CAMPOS_BUSQUEDA = "message.compararCamposBusqueda";

    protected static final String COMPARAR_FECHA_ACTUAL = "message.compararFechaActual";

    protected static final String TAREA_REASIGNADA = "message.tareaReasignada";

    protected static final String UNIDAD_APROBADORA_COMO_DEPENDIENTE = "message.aprobadorEsDependiente";

    protected static final String REGIMEN_COMPARA_RMU = "message.regimenLaboral.valoresRMU";

    protected static final String FERIADO_FECHA_EN_EJERCICIO_FISCAL =
            "ec.gob.mrl.smp.administracion.feriado.message.fecha";

    protected static final String FERIADO_FECHA_EXISTENTE =
            "ec.gob.mrl.smp.administracion.feriado.message.fechaExistente";

    protected static final String SOLICITUD_VACACION_ENVIADA = "message.solicitudVacacionEnviada";
    
    protected static final String SOLICITUD_VACACION_EXISTENTE = "message.solicitudVacacionExistente";

    protected static final String SIN_DERECHO_VACACION = "message.vacacionSinDerecho";

    protected static final String SIN_CONFIGURACION_DISTRIBUTIVO = "message.distributivo.SinConfiguracionRegimen";

    protected static final String COMPARAR_FECHA_NO_ANTERIOR = "message.compararFechaNoAnterior";

    protected static final String SOLICITUD_VACACION_APROBADA = "message.solicitudVacacionAprobada";
    protected static final String SOLICITUD_VACACION_VALIDADA = "message.solicitudVacacionValidada";

    protected static final String SOLICITUD_VACACION_NEGADA = "message.solicitudVacacionNegada";

    protected static final String SOLICITUD_VACACION_ERROR_REGISTRO_SALDO = "vacacionErrorRegistro";

    protected static final String PROYECCION_GASTOS_PERSONALES_RELIQUIDADA = "message.proyeccionReliquidada";

    protected static final String EL_SERVIDOR = "message.elServidor";

    protected static final String SIN_CONFIGURACION_PUESTOS = "message.sinConfiguracionPuestos";

    protected static final String SIN_FECHA_INGRESO_SERVIDOR = "message.sinFechaIngreso";

    protected static final String SIN_FRACCION_BASICA = "message.sinFraccionBasica";

    protected static final String SIN_CONFIGURACION_COTIZACION_IESS = "message.sinCotizacionIESS";
 
    protected static final String BUSQUEDA_SIN_RESULTADOS = "message.listaVacia";
    
    protected static final String CARACTERES_PARA_BUSQUEDA = "message.minimo.caracteres.busqueda";
    
    protected static final String CARACTERES_PARA_BUSQUEDA_X_NOMBRE = "message.minimo.caracteres.busqueda.porNombre";
    
    protected static final String CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION = "message.minimo.caracteres.porIdentificacion";
    
      protected static final String PARAMETROS_PARA_BUSQUEDA = "message.minimo.parametros.busqueda";
      
          protected static final String SOLICITUD_ANTICIPO_APROBADA = "message.solicitudAnticipoAprobada";

    protected static final String SOLICITUD_ANTICIPO_NEGADA = "message.solicitudAnticipoRechazada";
    
     protected static final String SELECCIONAR_ESCALA_OCUPACIONAL = "ec.gob.mrl.smp.administracion.regimenLaboral.escalaOcupacional.seleccion";
    
    private Random random = new Random();

    protected Integer getNextValue() {
        return random.nextInt(64);
    }

    protected void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public static final String getContextName() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    }

    protected void descargaArchivoUpload(String nombre) {
        try {
            getResponse().setContentType("text/xml");
            getResponse().setHeader("Content-Disposition", "attachment; filename=" + "Archivo." + devuelveExtension(
                    nombre));
            File file = new File(getPathReportes(obtenerProperties().getString(PATH)) + "/" + nombre);
            FileInputStream fichero = new FileInputStream(file);
            byte[] contenido = new byte[fichero.available()];
            fichero.read(contenido);
            getResponse().setContentLength(contenido.length);
            ServletOutputStream out = getResponse().getOutputStream();
            out.write(contenido);
            out.flush();
            out.close();
            getContext().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
            ponerMensajeError(NADA, "Ocurrió un error al tratar de descargar el archivo");
        }

    }

//    protected Usuario obtenerUsuarioLogeado() {
//        InicioControlador control = (InicioControlador) getRequest().getSession().getAttribute(
//                "navegacionControlador");
//        return control.getUsuarioRol().getUsuario();
//    }
//    protected Rol obtenerRolLogeado() {
//        InicioControlador control = (InicioControlador) getRequest().getSession().getAttribute(
//                "navegacionControlador");
//        return control.getUsuarioRol().getRol();
//    }
//    protected void setearInstitucion(Institucion institucion) {
//        institucion.setCodigoCatastro(UtilCadena.concatenar(institucion.getCodigoCatastro(), ".", institucion.getId()));
//        InicioControlador control = (InicioControlador) getRequest().getSession().getAttribute(
//                "navegacionControlador");
//        control.setInstitucion(institucion);
//    }
//    protected Institucion obtenerInstitucion() {
//        InicioControlador control = (InicioControlador) getRequest().getSession().getAttribute("inicioControlador");
//        return control.getInstitucion();
//    }

    protected String devuelveFechaEnLetras() {

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        String armaFecha =
                devuelveDiaSemana(fecha.get(Calendar.DAY_OF_WEEK)) + fecha.get(Calendar.DAY_OF_MONTH) + " de " + devuelveMes(fecha.
                get(Calendar.MONTH)) + " " + fecha.get(Calendar.YEAR) + " " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + devuelveMinuto(fecha.
                get(Calendar.MINUTE));
        return armaFecha;
    }

    protected String devuelveFechaHora(Date fecha) {
        DateFormat patron = new SimpleDateFormat(PATRON_FECHA_HORA);
        return patron.format(fecha);
    }

    protected String devuelveFechaEnLetras(Date fechaParametro) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaParametro);
        String armaFecha =
                devuelveDiaSemana(fecha.get(Calendar.DAY_OF_WEEK)) + fecha.get(Calendar.DAY_OF_MONTH) + " de " + devuelveMes(fecha.
                get(Calendar.MONTH)) + " " + fecha.get(Calendar.YEAR) + " " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + devuelveMinuto(fecha.
                get(Calendar.MINUTE));
        return armaFecha;
    }

    protected String devuelveFechaEnLetrasSinHora(Date fechaParametro) {
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaParametro);
        String armaFecha =
                devuelveDiaSemana(fecha.get(Calendar.DAY_OF_WEEK)) + fecha.get(Calendar.DAY_OF_MONTH) + " de " + devuelveMes(fecha.
                get(Calendar.MONTH)) + " " + fecha.get(Calendar.YEAR);
        return armaFecha;
    }

    private String devuelveMinuto(int minuto) {
        if (minuto < 10) {
            return String.valueOf("0" + minuto);
        } else {
            return String.valueOf(minuto);
        }
    }

    public ResourceBundle obtenerProperties() {
        ResourceBundle resource = (ResourceBundle) getRequest().getServletContext().getAttribute("ficheroPropiedades");
        if (resource == null) {
            resource = ResourceBundle.getBundle("messages");
            getRequest().getServletContext().setAttribute("ficheroPropiedades", resource);
        }
        return resource;
    }

    public void recargarProperties() {
        ResourceBundle resource = ResourceBundle.getBundle("messages");
        getRequest().getServletContext().setAttribute("ficheroPropiedades", resource);

    }

    private String devuelveExtension(String archivo) {
        StringTokenizer st = new StringTokenizer(archivo, ".");
        String extension = "";
        while (st.hasMoreTokens()) {
            extension = st.nextToken();
        }
        return extension;
    }

    protected Date obtenerFechaDesde(Date fechaDesde) {
        Calendar fechaDesdeAux = Calendar.getInstance();
        fechaDesdeAux.setTime(fechaDesde);
        fechaDesdeAux.set(Calendar.HOUR_OF_DAY, 0);
        fechaDesdeAux.set(Calendar.MINUTE, 0);
        fechaDesdeAux.set(Calendar.SECOND, 0);
        return fechaDesdeAux.getTime();
    }

    protected Date obtenerFechaHasta(Date fechaHasta) {
        Calendar fechaHastaAux = Calendar.getInstance();
        fechaHastaAux.setTime(fechaHasta);
        fechaHastaAux.set(Calendar.HOUR_OF_DAY, 23);
        fechaHastaAux.set(Calendar.MINUTE, 59);
        fechaHastaAux.set(Calendar.SECOND, 59);
        return fechaHastaAux.getTime();
    }

    protected String getPathReportes(String pathReporte) {
        return getRequest().getSession().getServletContext().getRealPath(pathReporte);
    }

    protected String devuelveCantidadEnMoneda(Double valor) {
        DecimalFormatSymbols simbols = new DecimalFormatSymbols();
        simbols.setDecimalSeparator('.');
        simbols.setPatternSeparator(',');
        NumberFormat formato = new DecimalFormat("############0.00", simbols);
        return formato.format(valor);
    }

    private String devuelveDiaSemana(int dia) {
        switch (dia) {
            case 1:
                return "Domingo ";
            case 2:
                return "Lunes ";
            case 3:
                return "Martes ";
            case 4:
                return "Miércoles ";
            case 5:
                return "Jueves ";
            case 6:
                return "Viernes ";
            case 7:
                return "Sabado ";
            default:
                return "";

        }
    }

    private static String devuelveMes(int mes) {
        switch (mes) {
            case 0:
                return "Enero";
            case 1:
                return "Febrero";
            case 2:
                return "Marzo";
            case 3:
                return "Abril";
            case 4:
                return "Mayo";
            case 5:
                return "Junio";
            case 6:
                return "Julio";
            case 7:
                return "Agosto";
            case 8:
                return "Septiembre";
            case 9:
                return "Octubre";
            case 10:
                return "Noviembre";
            case 11:
                return "Diciembre";
            default:
                return "";
        }
    }

    protected void ponerMensajeInfo(String key, String mensajeAdicional) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, this.resource.getString(key).concat(
                " ").concat(mensajeAdicional != null ? mensajeAdicional : ""), resource.getString(key).concat(" ").
                concat(
                (mensajeAdicional == null) ? "" : (mensajeAdicional == null) ? "" : mensajeAdicional)));//new FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)  ));
    }

    protected void ponerMensajeError(String key, String mensajeAdicional) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resource.getString(key).concat(" ").
                concat(mensajeAdicional != null ? mensajeAdicional : ""), resource.getString(key).concat(" ").concat(
                mensajeAdicional == null ? "" : mensajeAdicional)));//new FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)  ));
    }

    protected void ponerMensajeAlerta(String key, String mensajeAdicional) {
        getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, obtenerProperties().getString(key).
                concat(" ").concat(mensajeAdicional != null ? mensajeAdicional : ""), obtenerProperties().getString(key).
                concat(" ").concat(
                mensajeAdicional == null ? "" : mensajeAdicional)));//new FacesMessage(FacesMessage.SEVERITY_INFO,resource.getString(key).concat(" ").concat(mensajeAdicional)  ));
    }

    protected void ponerMensajePorComponente(FacesMessage.Severity severity, String summary, String detail,
            String componente) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(severity);
        message.setSummary(summary);
        message.setDetail(detail);
        getContext().addMessage(componente, message);
    }

    public static void render() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    protected Object getManagedBean(String beanName) {
        ExpressionFactory ef = getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(getElContext(), getJsfEl(beanName), Object.class);
        return ve.getValue(getElContext());
    }

    /**
     * Remove the managed bean based on the bean name.
     *
     * @param beanName the bean name of the managed bean to be removed
     */
    protected static void resetManagedBean(String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ef.createValueExpression(elc, getJsfEl(beanName),
                Object.class).setValue(elc, null);
    }

    private static String getJsfEl(String value) {
        return "#{" + value + "}";
    }

    protected Date devuelveFechaPorCadena(String cadena) {
        try {
            DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaParseada = fecha.parse(cadena);
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaParseada);
            return cal.getTime();
        } catch (Exception e) {
            error(getClass().getName(), "no se puede formatear", e);
            return null;
        }
    }

    protected boolean validaRangoFecha(Date fechaDesde, Date fechaHasta) throws Exception {
        Calendar fechaSistema = Calendar.getInstance();
        Calendar fechaIngresoUsuario = Calendar.getInstance();
        fechaSistema.setTime(fechaDesde);
        fechaIngresoUsuario.setTime(fechaHasta);
        String fechaSistemaString =
                fechaSistema.get(Calendar.YEAR) + "-" + fechaSistema.get(Calendar.MONTH) + "-" + fechaSistema.get(
                Calendar.DATE);
        String fechaIngresoString = fechaIngresoUsuario.get(Calendar.YEAR) + "-" + fechaIngresoUsuario.get(
                Calendar.MONTH) + "-" + fechaIngresoUsuario.get(Calendar.DATE);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaSistemaAux = formatoFecha.parse(fechaSistemaString);
        Date fechaIngresoAux = formatoFecha.parse(fechaIngresoString);
        if (fechaSistemaAux.before(fechaIngresoAux) || fechaSistemaAux.equals(fechaIngresoAux)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean validaRangoFechaTurnos(Date fechaDesde, Date fechaHasta) throws Exception {
        Calendar fechaSistema = Calendar.getInstance();
        Calendar fechaIngresoUsuario = Calendar.getInstance();
        fechaSistema.setTime(fechaDesde);
        fechaIngresoUsuario.setTime(fechaHasta);
        String fechaSistemaString =
                fechaSistema.get(Calendar.YEAR) + "-" + fechaSistema.get(Calendar.MONTH) + "-" + fechaSistema.get(
                Calendar.DATE);
        String fechaIngresoString = fechaIngresoUsuario.get(Calendar.YEAR) + "-" + fechaIngresoUsuario.get(
                Calendar.MONTH) + "-" + fechaIngresoUsuario.get(Calendar.DATE);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaSistemaAux = formatoFecha.parse(fechaSistemaString);
        Date fechaIngresoAux = formatoFecha.parse(fechaIngresoString);
        if (fechaSistemaAux.before(fechaIngresoAux) || fechaSistemaAux.equals(fechaIngresoAux)) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean validarDocumento(String numero) {
        int suma = 0;
        int residuo = 0;
        boolean privada = false;
        boolean publica = false;
        boolean natural = false;
        int numeroProvincias = 24;
        int digitoVerificador = 0;
        int modulo = 11;

        int d1, d2, d3, d4, d5, d6, d7, d8, d9, d10;
        int p1, p2, p3, p4, p5, p6, p7, p8, p9;

        d1 = d2 = d3 = d4 = d5 = d6 = d7 = d8 = d9 = d10 = 0;
        p1 = p2 = p3 = p4 = p5 = p6 = p7 = p8 = p9 = 0;

        if (numero.length() < 10) {
            return false;
        }

        // Los primeros dos digitos corresponden al codigo de la provincia
        int provincia = Integer.parseInt(numero.substring(0, 2));

        if (provincia <= 0 || provincia > numeroProvincias) {
            return false;
        }

        // Almacena los digitos de la cedula en variables.
        d1 = Integer.parseInt(numero.substring(0, 1));
        d2 = Integer.parseInt(numero.substring(1, 2));
        d3 = Integer.parseInt(numero.substring(2, 3));
        d4 = Integer.parseInt(numero.substring(3, 4));
        d5 = Integer.parseInt(numero.substring(4, 5));
        d6 = Integer.parseInt(numero.substring(5, 6));
        d7 = Integer.parseInt(numero.substring(6, 7));
        d8 = Integer.parseInt(numero.substring(7, 8));
        d9 = Integer.parseInt(numero.substring(8, 9));
        d10 = Integer.parseInt(numero.substring(9, 10));

        // El tercer digito es:
        // 9 para sociedades privadas y extranjeros
        // 6 para sociedades publicas
        // menor que 6 (0,1,2,3,4,5) para personas naturales
        if (d3 == 7 || d3 == 8) {
            return false;
        }

        // Solo para personas naturales (modulo 10)
        if (d3 < 6) {
            natural = true;
            modulo = 10;
            p1 = d1 * 2;
            if (p1 >= 10) {
                p1 -= 9;
            }
            p2 = d2 * 1;
            if (p2 >= 10) {
                p2 -= 9;
            }
            p3 = d3 * 2;
            if (p3 >= 10) {
                p3 -= 9;
            }
            p4 = d4 * 1;
            if (p4 >= 10) {
                p4 -= 9;
            }
            p5 = d5 * 2;
            if (p5 >= 10) {
                p5 -= 9;
            }
            p6 = d6 * 1;
            if (p6 >= 10) {
                p6 -= 9;
            }
            p7 = d7 * 2;
            if (p7 >= 10) {
                p7 -= 9;
            }
            p8 = d8 * 1;
            if (p8 >= 10) {
                p8 -= 9;
            }
            p9 = d9 * 2;
            if (p9 >= 10) {
                p9 -= 9;
            }
        }

        // Solo para sociedades publicas (modulo 11)
        // Aqui el digito verficador esta en la posicion 9, en las otras 2
        // en la pos. 10
        if (d3 == 6) {
            publica = true;
            p1 = d1 * 3;
            p2 = d2 * 2;
            p3 = d3 * 7;
            p4 = d4 * 6;
            p5 = d5 * 5;
            p6 = d6 * 4;
            p7 = d7 * 3;
            p8 = d8 * 2;
            p9 = 0;
        }

        /*
         * Solo para entidades privadas (modulo 11)
         */
        if (d3 == 9) {
            privada = true;
            p1 = d1 * 4;
            p2 = d2 * 3;
            p3 = d3 * 2;
            p4 = d4 * 7;
            p5 = d5 * 6;
            p6 = d6 * 5;
            p7 = d7 * 4;
            p8 = d8 * 3;
            p9 = d9 * 2;
        }

        suma = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9;
        residuo = suma % modulo;

        // Si residuo=0, dig.ver.=0, caso contrario 10 - residuo
        digitoVerificador = residuo == 0 ? 0 : modulo - residuo;
        int longitud = numero.length(); // Longitud del string

        // ahora comparamos el elemento de la posicion 10 con el dig. ver.
        if (publica == true) {
            if (digitoVerificador != d9) {
                return false;
            }
            /*
             * El ruc de las empresas del sector publico terminan con 0001
             */
            if (!numero.substring(9, longitud).equals("0001")) {
                return false;
            }
        }

        if (privada == true) {
            if (digitoVerificador != d10) {
                return false;
            }
            if (!numero.substring(10, longitud).equals("001")) {
                return false;
            }
        }

        if (natural == true) {
            if (digitoVerificador != d10) {
                return false;
            }
            if (numero.length() > 10 && !numero.substring(10, longitud).equals(
                    "001")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the context
     */
    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    protected ExternalContext getExternalContext() {
        return getContext().getExternalContext();
    }

    protected ELContext getElContext() {
        return getContext().getELContext();
    }

    protected Application getApplication() {
        return getContext().getApplication();
    }

    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    protected HttpServletResponse getResponse() {
        return (HttpServletResponse) getContext().getExternalContext().getResponse();

    }

    protected Map<String, String> getRequestParameterMap() {
        return getExternalContext().getRequestParameterMap();
    }

    protected void info(String clase, String mensaje) {
        Logger.getLogger(clase).log(Level.INFO, mensaje.toUpperCase());
    }

    protected void error(String clase, String mensaje, Exception e) {
        Logger.getLogger(clase).log(Level.SEVERE, mensaje.toUpperCase(), e);
    }

    protected Map<String, String> obtenerParametrosFaces() {
        return getExternalContext().getRequestParameterMap();
    }

    protected String mayusculas(String mayusculas) {
        return mayusculas.toUpperCase().trim();
    }

    protected MethodExpression crearActionFormularios(String action) {
        return getApplication().getExpressionFactory().createMethodExpression(getElContext(), action, null,
                new Class<?>[0]);
    }

    public void cerrarSession() {
        try {
            Map<String, Object> map = getContext().getExternalContext().getSessionMap();
            map.remove("user");
            getRequest().getSession().invalidate();
            getExternalContext().redirect(getRequest().getContextPath() + "/login.jsf");
        } catch (Exception ex) {
            error(getClass().getName(), "no se puede cerrar la sesión", ex);
        }
    }

    protected static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    /**
     * Recupera el valor de una atributo en la session
     * @param nombreAtributo
     * @return 
     */
    public static Object obtenerAtributoSession(String nombreAtributo){
        HttpSession s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return s.getAttribute(nombreAtributo);
    }
    
    /**
     * Registra un atributo en la session
     * @param nombreAtributo
     * @param valor 
     */
    public static void ponerAtributoSession(String nombreAtributo, Object valor){
        HttpSession s = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        s.setAttribute(nombreAtributo, valor);
    }
    
    protected void closeSession() {
        try {
            getExternalContext().redirect(getRequest().getContextPath() + "/login.jsf");
            HttpSession session = getSession();
            session.invalidate();
        } catch (Exception e) {
            error(getClass().getName(), "no se puede cerrar la sesión", e);
        }

    }

    protected String calculaAniosMeses(int anioDesde, int mesDesde, int anioHasta, int mesHasta) {
        int resultadoAnios = anioHasta - anioDesde;
        int resultadoMeses = mesHasta - mesDesde;
        String devuelveEdad = "";
        if (resultadoAnios == 0) {
            if (resultadoMeses == 1) {
                devuelveEdad = resultadoMeses + " mes";
            } else {
                if (resultadoMeses == 0) {
                    devuelveEdad = "1 mes";
                } else {
                    if (resultadoMeses < 0) {
                        devuelveEdad = "Edad no válida";
                    } else {
                        devuelveEdad = resultadoMeses + " meses";
                    }
                }
            }
        } else {
            if (resultadoAnios == 1 && mesDesde == mesHasta) {
                devuelveEdad = "1 año";
            } else {
                if (resultadoAnios == 1 && mesDesde > mesHasta) {
                    int mesesFinales = (12 - mesDesde) + mesHasta;
                    devuelveEdad = mesesFinales + " meses";
                } else {
                    if (resultadoAnios == 1 && mesDesde < mesHasta) {
                        if (resultadoMeses == 1) {
                            devuelveEdad = "1 año " + resultadoMeses + " mes";
                        } else {
                            devuelveEdad = "1 año " + resultadoMeses + " meses";
                        }
                    }
                }
            }
            if (resultadoAnios > 1 && mesDesde == mesHasta) {
                devuelveEdad = resultadoAnios + " años";
            } else {
                if (resultadoAnios > 1 && mesDesde > mesHasta) {
                    int mesesFinales1 = (12 - mesDesde) + (mesHasta);
                    resultadoAnios = resultadoAnios - 1;
                    if (resultadoAnios == 1) {
                        devuelveEdad = resultadoAnios + " año " + mesesFinales1 + " meses";
                    } else {
                        devuelveEdad = resultadoAnios + " años " + mesesFinales1 + " meses";
                    }
                } else {
                    if (resultadoAnios > 1 && mesDesde < mesHasta) {
                        if (resultadoMeses == 1) {
                            devuelveEdad = resultadoAnios + " años " + resultadoMeses + " mes";
                        } else {
                            devuelveEdad = resultadoAnios + " años " + resultadoMeses + " meses";
                        }
                    }
                }
            }
            if (resultadoAnios < 0) {
                devuelveEdad = "Rango no válido";
            }
        }
        return devuelveEdad;
    }

    public static String renombraArchivo(String pathOrigen, String pathDestino, String newname) throws Exception {
        FileInputStream fis = new FileInputStream(pathOrigen);
        FileOutputStream fos = new FileOutputStream(pathDestino + "/" + newname);
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        fis.close();
        fos.close();
        inChannel.close();
        outChannel.close();
        return pathDestino + "/" + newname;
    }

    public static void descargarArchivo(File file, String pathDestino) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(pathDestino);
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        fis.close();
        fos.close();
        inChannel.close();
        outChannel.close();
    }

    protected synchronized String passwordEncriptado(String password) {
        return Crypt.encrypt(password);
    }

    protected String passwordDesencriptado(String password) {
        return Crypt.decrypt(password);
    }

    /*
     * Metodo que valida que el rango de las fechas, no sea mayor al número de dias, que se han asignado a las etapas,
     * sin tomar en cuenta los fines de semana
     */
    protected boolean validarFechasMayorNumeroDias(Date fechaDesde, Date fechaHasta, int numDias, String etapa) throws
            Exception {
        if (fechaDesde.getTime() <= fechaHasta.getTime()) {
            long dif = fechaHasta.getTime() - fechaDesde.getTime();
            dif = dif / 1000l / 60l / 60l / 24l;

            Calendar fechaMaxima = Calendar.getInstance();
            fechaMaxima.setTime(fechaDesde);
            int suma = 0;
            for (int i = 0; i < dif; i++) {
                suma += nextValidDay(fechaMaxima.get(Calendar.DAY_OF_WEEK) + i);
            }

            fechaMaxima.add(Calendar.DAY_OF_YEAR, suma + numDias);
            if (fechaHasta.getTime() > fechaMaxima.getTime().getTime()) {
                ponerMensajeError(NADA,
                        "En la etapa " + etapa + ", La diferencia entre las dos fechas no puede ser mayor a " + numDias + " dias.");
                return true;
            }
        } else {
            ponerMensajeError(NADA, "En la etapa " + etapa + ", La fecha Fin no puede ser menor a la Fecha Inicio");
            return true;
        }
        return false;
    }

    protected boolean validarFechasMenorNumeroDias(Date fechaDesde, Date fechaHasta, int numDias, String etapa) throws
            Exception {
        if (fechaDesde.getTime() <= fechaHasta.getTime()) {
            long dif = fechaHasta.getTime() - fechaDesde.getTime();
            dif = dif / 1000l / 60l / 60l / 24l;

            Calendar fechaMaxima = Calendar.getInstance();
            fechaMaxima.setTime(fechaDesde);
            int suma = 0;
            for (int i = 0; i < dif; i++) {
                suma += nextValidDay(fechaMaxima.get(Calendar.DAY_OF_WEEK) + i);
            }

            if (dif < numDias + suma) {
                ponerMensajeError(NADA,
                        "En la etapa " + etapa + ", La diferencia entre las dos fechas no puede ser menor a " + numDias + " dias.");
                return true;
            }
        } else {
            ponerMensajeError(NADA, "En la etapa " + etapa + ", La fecha Fin no puede ser menor a la Fecha Inicio");
            return true;
        }
        return false;
    }

    /*
     * TODO: Metodo que valida que el rango de las fechas, no sea menor el número de dias, que se han asignado a las
     * etapas, tomando en cuenta los fines de semana
     */
    protected boolean validarFechasMenorNumeroTodosDias(Date fechaDesde, Date fechaHasta, int numDias, String etapa)
            throws Exception {
        if (fechaDesde.getTime() <= fechaHasta.getTime()) {
            long dif = fechaHasta.getTime() - fechaDesde.getTime();
            dif = dif / 1000l / 60l / 60l / 24l;

            Calendar fechaMaxima = Calendar.getInstance();
            fechaMaxima.setTime(fechaDesde);
            System.out.println("DIF===>>> " + dif);
            if (dif < numDias) {
                ponerMensajeError(NADA,
                        "En la etapa " + etapa + ", La diferencia entre las dos fechas no puede ser menor a " + numDias + " dias.");
                return true;
            }
        } else {
            ponerMensajeError(NADA, "En la etapa " + etapa + ", La fecha Fin no puede ser menor a la Fecha Inicio");
            return true;
        }
        return false;
    }

    private int nextValidDay(int dayOfWeek) {
        return dayOfWeek == 7 ? 2 : dayOfWeek == 1 ? 1 : 0;
    }

    public static String limpiarEspacios(String valor) {
        if (valor != null) {
            valor = valor.trim();
        }
        return valor;
    }

    public static String toMayus(String valor) {
        if (valor != null) {
            valor = valor.toUpperCase();
        }
        return valor;
    }

    public double redondear(Double valor) {
        StringTokenizer token = new StringTokenizer(String.valueOf(valor), ".");
        String decimal = "";
        double retorno = 0;
        while (token.hasMoreTokens()) {
            decimal = token.nextToken();
        }
        if (decimal.length() >= 2) {
            String componer = valor.intValue() + "." + decimal.substring(0, 2);
            retorno = Double.parseDouble(componer);
        } else {
            retorno = valor;
        }
        return retorno;
    }

    public static void ordenarLista(List lista, final String propiedad) {
        Collections.sort(lista, new Comparator() {

            public int compare(Object obj1, Object obj2) {

                Class clase = obj1.getClass();
                String getter = "get" + Character.toUpperCase(propiedad.charAt(0)) + propiedad.substring(1);
                try {

                    Method getPropiedad = clase.getMethod(getter);

                    Object propiedad1 = getPropiedad.invoke(obj1);
                    Object propiedad2 = getPropiedad.invoke(obj2);

                    if (propiedad1 instanceof Comparable && propiedad2 instanceof Comparable) {
                        Comparable prop1 = (Comparable) propiedad1;
                        Comparable prop2 = (Comparable) propiedad2;
                        return prop1.compareTo(prop2);
                    }//CASO DE QUE NO SEA COMPARABLE  
                    else {
                        if (propiedad1.equals(propiedad2)) {
                            return 0;
                        } else {
                            return 1;
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }

    public static int numeroMeses(Date fechaInicio, Date fechaFin) {
        long fechaInicialMs = fechaInicio.getTime();
        long fechaFinalMs = fechaFin.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double diasDiferncia = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        int valorDias = ((int) diasDiferncia) / 30;
        return valorDias;
//        Calendar calFechaInicio = Calendar.getInstance();
//        Calendar calFechaFin = Calendar.getInstance();
//        calFechaInicio.setTime(fechaInicio);
//        calFechaFin.setTime(fechaFin);
//        int numMeses = calFechaFin.get(Calendar.MONTH) - calFechaInicio.get(Calendar.MONTH);
//        return numMeses;
    }

    public static int numeroDias(Date fechaInicio, Date fechaFin) {
        long fechaInicialMs = fechaInicio.getTime();
        long fechaFinalMs = fechaFin.getTime();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double diasDiferncia = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        int valorDias = (int) diasDiferncia;
        return valorDias;

    }
    
    /**
     * Permite extraer la hora en formato 00:00, desde la cadena
     * 00:00:00.0000000 que retorna la BD representando una hora
     * @param horaDesdeBD
     * @return 
     */
    public Date convertStringTimeToDate(final String horaDesdeBD){
        return UtilFechas.convertirHoraTimbreStringADate(horaDesdeBD);
    }
}
