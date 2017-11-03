/*
 *  BaseControlador.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  17/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador.base;

//import com.sun.java_cup.internal.runtime.Symbol;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.utilitarios.Utilitarios;
import ec.com.atikasoft.proteus.dao.ReporteDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.CacheEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Reporte;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.base.EntidadBasica;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public abstract class BaseControlador extends Utilitarios implements Serializable {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(BaseControlador.class.getCanonicalName());

    /**
     * Nombre del archivo de propiedades de la aplicacion.
     */
    public static final String BUNDLE_NAME = "messages";

    /**
     * Url a pagina principal.
     */
    public static final String PAGINA_PRINCIPAL = "/pages/index.jsf";

    /**
     * Key de error al consultar.
     */
    public static final String ERROR_CONSULTA = "ec.gob.mrl.smp.genericos.error.consulta";

    /**
     * Key de error génerico.
     */
    public static final String ERROR_GENERICO = "ec.gob.mrl.smp.genericos.mensaje.error.generico";

    /**
     * Id del formulario para el componente del mensaje.
     */
    public static final String FORM_MESSAGE_ID = "ec.gob.mrl.smp.genericos.formulario.mensajes.id";

    /**
     * Id del componente del mensaje.
     */
    public static final String MESSAGE_ID = "ec.gob.mrl.smp.genericos.mensaje.generico.id";

    /**
     * Nombre del usuario objeto en sesion.
     */
    public static final String DTO_USER_NAME = "usrSessionDTO";

    /**
     * Informacion acerca de la cantidad de notificaciones no leidas por el usuario logueado
     */
    public static final String INFO_NOTIFICACIONES_NO_LEIDAS = "infoNotifNoLeidas";

    /**
     * Variable de sesion para la navegacion.
     */
    public static final String RETURN_PAGE = "urlPageReturn";

    /**
     * Nombre del campos timpo movimiento objeto en sesion.
     */
    public static final String CAMPOS_TIP_MOV = "nombreCamposTipoMovimientos";

    /**
     * Seleccione.
     */
    public static final String SELECCIONE = "Seleccione...";

    /**
     * Dao de reportes.
     */
    @EJB
    private ReporteDao reporteDao;
    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    /**
     * Nombre de reporte.
     */
    private String reporte;

    /**
     * Parametros del reporte.
     */
    protected Map<String, String> parametrosReporte;

    /**
     * Este método será usado comunmente para iniciar objetos u operaciones luego de la construción del la clase.
     */
    public abstract void init();

    /**
     * Controlador sin parametros.
     */
    public BaseControlador() {
        super();
    }

    /**
     * Se encarga de inicializar los valores comunes de una entidad.
     *
     * @param <T> Se espera como parametro cualquier entidad que herede de EntidadBasica.
     * @param entidad La entidad que se esta crearndo o actualizando
     * @param nuevo Indica si se esta creando un nuevo registro.
     */
    public <T extends EntidadBasica> void iniciarDatosEntidad(final T entidad, final Boolean nuevo) {
        if (entidad != null) {
            if (nuevo) {
                entidad.setFechaCreacion(new Date());
                entidad.setUsuarioCreacion(obtenerUsuarioConectado() != null
                        && obtenerUsuarioConectado().getUsuario() != null
                        && !obtenerUsuarioConectado().getUsuario().trim().isEmpty()
                                ? obtenerUsuarioConectado().getUsuario()
                                : obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                entidad.setVigente(Boolean.TRUE);
            } else {
                entidad.setFechaActualizacion(new Date());
                entidad.setUsuarioActualizacion(obtenerUsuarioConectado() != null
                        && obtenerUsuarioConectado().getUsuario() != null
                        && !obtenerUsuarioConectado().getUsuario().trim().isEmpty()
                                ? obtenerUsuarioConectado().getUsuario()
                                : obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
            }
        }

    }

    /**
     * Este método limpia una lista y le asigna el valor por defecto en selección.
     *
     * @param lista List
     * @return List
     */
    public final List<SelectItem> iniciarCombos(final List<SelectItem> lista) {
        if (lista != null) {
            lista.clear();
            lista.add(new SelectItem("",
                    obtenerProperties().getString("ec.gob.mrl.smp.genericos.combo.seleccione")));
        }
        return lista;
    }

    /**
     * Este método limpia una lista y le asigna el valor por defecto en selección.
     *
     * @param lista List
     * @return List
     */
    public final List<SelectItem> iniciarCombosTodos(final List<SelectItem> lista) {
        if (lista != null) {
            lista.clear();
            lista.add(new SelectItem(null,
                    obtenerProperties().
                    getString("ec.gob.mrl.smp.genericos.combo.consulta")));
        }
        return lista;
    }

    /**
     * Este méstodo limpia una lista y le asigna el valor por defecto en selección.
     *
     * @param lista List
     * @return List
     */
    public final List<SelectItem> iniciarCombosConsulta(final List<SelectItem> lista) {
        if (lista != null) {
            lista.clear();
            lista.add(new SelectItem(null,
                    obtenerProperties().
                    getString("ec.gob.mrl.smp.genericos.combo.consulta")));
        }
        return lista;
    }

    /**
     * Este método verifica si una clave esta entre las claves de la lista selección.
     *
     * @param clave
     * @param lista List
     * @return Boolean
     */
    public final Boolean existeClaveEnLista(final String clave, final List<SelectItem> lista) {
        if (lista != null) {
            for (SelectItem item : lista) {
                if (clave != null && item.getValue() != null
                        && clave.equals(item.getValue().toString())) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Este método verifica si una clave esta entre las claves de la lista selección.
     *
     * @param nombre
     * @param lista List
     * @return Boolean
     */
    public final Boolean existeNombreEnLista(final String nombre, final List<SelectItem> lista) {
        if (lista != null) {
            for (SelectItem item : lista) {
                if (nombre != null && item.getValue() != null
                        && nombre.equals(item.getValue().toString())) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Este metodo es usado para mostrar un mensaje en la pantalla.
     *
     * @param bundleKey String
     * @param e FacesMessage
     */
    public final void mostrarMensajeEnPantalla(final String bundleKey, final FacesMessage.Severity e) {
        String mensaje = null;
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println(context);
        try {
            mensaje = obtenerProperties().getString(bundleKey);
        } catch (Exception ex) {
            mensaje = bundleKey;
        } finally {
            context.addMessage(null, new FacesMessage(e, mensaje, mensaje));
            org.primefaces.context.RequestContext prmContext
                    = org.primefaces.context.RequestContext.getCurrentInstance();
            if (prmContext != null) {
                String idComponente = obtenerProperties().getString(
                        FORM_MESSAGE_ID).concat(":").concat(obtenerProperties().getString(MESSAGE_ID));
                prmContext.update(idComponente);
            }

        }
    }

    /**
     *
     * @param bundleKey
     * @param e
     * @param parametros
     */
    public final void mostrarMensajeEnPantalla(final String bundleKey, final FacesMessage.Severity e, Object[] parametros) {
        String mensaje = null;
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        boolean parametrizar = true;
        try {
            mensaje = obtenerProperties().getString(bundleKey);
            if (mensaje == null) {
                mensaje = bundleKey;
                parametrizar = false;
            }
        } catch (Exception ex) {
            mensaje = bundleKey;
            parametrizar = false;
        } finally {

            if (parametrizar) {
                if (parametros != null && parametros.length > 0) {
                    MessageFormat mf = new MessageFormat(mensaje, locale);
                    mensaje = mf.format(parametros, new StringBuffer(), null).toString();
                }
            }

            context.addMessage(null, new FacesMessage(e, mensaje, mensaje));
            org.primefaces.context.RequestContext prmContext
                    = org.primefaces.context.RequestContext.getCurrentInstance();
            if (prmContext != null) {
                String idComponente = obtenerProperties().getString(
                        FORM_MESSAGE_ID).concat(":").concat(obtenerProperties().getString(MESSAGE_ID));
                prmContext.update(idComponente);
            }

        }
    }

    /**
     * Este metodo es usado para mostrar un mensaje en la pantalla.
     *
     * @param bundleKey String
     * @param e FacesMessage
     */
    public final void mostrarMensajeEnPantalla(String bundleKey, FacesMessage.Severity e, String adicional) {
        String mensaje = UtilCadena.concatenar(obtenerProperties().getString(bundleKey), " :", adicional);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(e, mensaje, mensaje));
        org.primefaces.context.RequestContext prmContext = org.primefaces.context.RequestContext.getCurrentInstance();
        String idComponente = obtenerProperties().getString(FORM_MESSAGE_ID).concat(":").concat(obtenerProperties().
                getString(MESSAGE_ID));
        if (prmContext != null) {
            prmContext.update(idComponente);
        }
    }

    /**
     * Generar el url para ejecutar un reporte
     *
     * @param r
     * @return
     * @throws DaoException
     */
    protected String generarURLdeReporte(Reporte r) throws DaoException {
        return reporteDao.buscarURLReporte(r, parametrosReporte, obtenerUsuarioConectado().
                getInstitucion().getNombre(), Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().
                        getNombre()), obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());

    }

    /**
     * Permite generar el url de un reporte especifico.
     */
    protected void generarUrlDeReporte() {
        try {
            Reporte r = reporteDao.buscarPorReporte(getReporte());
            if (r == null) {
                mostrarMensajeEnPantalla(UtilCadena.concatenar("Reporte ", getReporte(), " no existe definido."),
                        FacesMessage.SEVERITY_ERROR);
            } else {
                String url = reporteDao.buscarURLReporte(r, parametrosReporte, obtenerUsuarioConectado().
                        getInstitucion().getNombre(), Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().
                                getNombre()), obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                StringBuilder md5 = new StringBuilder();

                md5.append(r.getRptDesing());
                md5.append(".");
                md5.append(r.getExtension());
                md5.append(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                        getExternalContext().getResponse();
                Cookie cookie = new Cookie("OUR_REPORT", DigestUtils.md5Hex(md5.toString()));
                // Expire time. -1 = by end of current session, 0 = immediately expire it, otherwise just the lifetime in seconds.
                cookie.setMaxAge(60);
                cookie.setPath("/proteus-rpt");
                response.addCookie(cookie);
                RequestContext.getCurrentInstance().execute(UtilCadena.concatenar("mostrarPopup('", url,
                        "','REPORTE MOVIMIENTOS DE PERSONAL Y NÓMINA');"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que permite inicializar y ejercutar un reporte, aqui se debe registrar el nombre del reporte y sus
     * parametros.
     */
    public void generarReporte() {
        throw new UnsupportedOperationException(UtilCadena.concatenar(
                "Debe implementar éste método en su controlador para que funcione la llamada al reporte.",
                "  Asegúrese de inicializar los atributos \"reporte\" y \"parametrosReporte\""));
    }

    /**
     * @return the reporte
     */
    public String getReporte() {
        return reporte;
    }

    /**
     * @param reporte the reporte to set
     */
    public void setReporte(final String reporte) {
        this.reporte = reporte;
    }

    /**
     * Este metodo llama a una ulr de la pagina principal, regla de navegacion.
     *
     * @return String
     */
    public final String salirPantallaPrincipal() {
        reglaNavegacionDirecta(BaseControlador.PAGINA_PRINCIPAL);
        return null;
    }

    /**
     * Este método cambia el url total para regla de navegacion.
     *
     * @param url String
     */
    public final void reglaNavegacionDirecta(final String url) {
        try {
            if (url != null) {
                FacesContext context = FacesContext.getCurrentInstance();
                String contexto = getRequest().getContextPath();
                context.getExternalContext().redirect(contexto.concat(url));
                //getResponse().sendRedirect(getRequest().getContextPath().concat(url));
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al forzar el url.", e);
        }
    }

    /**
     * Este método ejecuta un comando de primefaces.
     *
     * @param comando String
     */
    public final void ejecutarComandoPrimefaces(final String comando) {
        RequestContext currentInstance = RequestContext.getCurrentInstance();
        if (currentInstance != null) {
            currentInstance.execute(comando);
        }
    }

    /**
     * Este método actualiza un componente.
     *
     * @param idComponente String
     */
    public final void actualizarComponente(final String idComponente) {
        try {
            org.primefaces.context.RequestContext prmContext
                    = org.primefaces.context.RequestContext.getCurrentInstance();
            if (prmContext != null) {
                prmContext.update(idComponente);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al actualizar el componente", e);
        }
    }

    /**
     * Método que toma el usuario de session.
     *
     * @return UsuarioVO
     */
    public final UsuarioVO obtenerUsuarioConectado() {
        return (UsuarioVO) getSession().getAttribute(BaseControlador.DTO_USER_NAME);
    }

    /**
     * Nombre del usuario conectado.
     *
     * @return String
     */
    public final String obtenerNombreUsuario() {
        return obtenerUsuarioConectado().getServidor().getApellidosNombres();
    }

    /**
     *
     * @return
     */
    public final String obtenerUsuario() {
        return obtenerUsuarioConectado().getServidor().getNumeroIdentificacion();
    }

    /**
     * Este método pasa una lista de catalogo a una lista de seleccion.
     *
     * @param lista List
     * @param opciones List
     */
//    public final void llenarOpcionesCatalogo(final List<SelectItem> lista, final List<CatalogoDetalle> opciones) {
//        iniciarCombos(lista);
//        if (opciones != null) {
//            for (CatalogoDetalle c : opciones) {
//                lista.add(new SelectItem(c.getId(), c.getNombre(), c.getDescripcion()));
//            }
//        }
//    }
    /**
     * Este metodo establece el valor para la pagina anterior en la navegacion.
     *
     * @param siguiente String
     */
    public final void establecerPanginaAnterior(final String siguiente) {
        try {
            List<String> paginas;
//            HashMap<String, String> reglasNavegacion;
            Object reglas = getSession().getAttribute(BaseControlador.RETURN_PAGE);
            if (reglas == null) {
                paginas = new ArrayList<String>();
//                reglasNavegacion = new HashMap<String, String>();
            } else {
//                reglasNavegacion = (HashMap<String, String>) reglas;
                paginas = (List<String>) reglas;
            }
//            System.out.println("\nSe registra :key: " + siguiente + "\nValor: " + urlParcialActual());
//            reglasNavegacion.put(siguiente, urlParcialActual());
            paginas.add(urlParcialActual());
            getSession().setAttribute(BaseControlador.RETURN_PAGE, paginas);
            reglaNavegacionDirecta(siguiente);
        } catch (Exception e) {
            error(getClass().getName(), "Error al establecer URL, regresar.", e);
        }
    }

    /**
     * Este metodo genera la url parcial de la pagina actual.
     *
     * @return String
     */
    private String urlParcialActual() {
        String urlCompleta = getRequest().getRequestURL().toString();
//        System.out.println("Completa: " + urlCompleta);
        String contexto = getRequest().getContextPath();
        int indexOf = urlCompleta.indexOf(contexto);
        FacesContext ctx = FacesContext.getCurrentInstance();
        String path = ctx.getExternalContext().getRequestContextPath();
//        System.out.println("Otro pathc: " + path);

        HttpServletRequest servletRequest = (HttpServletRequest) ctx.getExternalContext().getRequest();
        String fullURI = servletRequest.getRequestURI();
//        System.out.println("Otro full pathc: " + fullURI);
        String urlParcial = urlCompleta.substring(indexOf, urlCompleta.length());
        return urlParcial.replaceFirst(contexto, "");
    }

    /**
     * Este metodo toma el valor de la pagina anterior en casos especiales de navegacion dinamica.
     */
    public final void regresarNavegacion() {
        Object reglas = getSession().getAttribute(BaseControlador.RETURN_PAGE);
        if (reglas != null) {
            List<String> paginas = (List<String>) reglas;
            if (!paginas.isEmpty()) {
                String get = paginas.get(paginas.size() - 1);
                reglaNavegacionDirecta(get);
                paginas.remove(paginas.size() - 1);
            }
            getSession().setAttribute(BaseControlador.RETURN_PAGE, paginas);
        }
    }

    /**
     * Este método devuelve la descripcion de un Select Item.
     *
     * @param lista List
     * @param dato Object
     * @return String
     */
    public static String obtenerDescripcionSelectItem(final List<SelectItem> lista, final Object dato) {
        String descripcion = "";
        if (lista != null) {
            for (SelectItem s : lista) {
                if (s.getValue() != null && dato != null && s.getValue().toString().equals(dato.toString())) {
                    descripcion = s.getLabel();
                    break;
                }
            }
        }
        return descripcion;
    }

    @Override
    public void error(final String clase, final String mensaje, final Exception e) {
        super.error(clase, mensaje, e);
        e.printStackTrace();
        String message = mensaje;
        if (message == null) {
            message = obtenerProperties().getString(ERROR_GENERICO);
        }
        if (e.getMessage() != null) {
            String msgException = e.getMessage();
            String[] separador = msgException.split(":");
            message = message.concat(": ").concat(separador[separador.length - 1]);
        }
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
        org.primefaces.context.RequestContext prmContext = org.primefaces.context.RequestContext.getCurrentInstance();
        String idComponente = obtenerProperties().getString(FORM_MESSAGE_ID).concat(":").concat(obtenerProperties().
                getString(MESSAGE_ID));
        if (prmContext != null) {
            prmContext.update(idComponente);
        }
    }

    /**
     * Obtiene valores desde el contexto.
     */
    protected Object obtenerCache(final String cacheCodigo) {
        return getSession().getServletContext().getAttribute(cacheCodigo);
    }

    /**
     * Este metodo establece el valor a un componetne jsf.
     *
     * @param idComponente String
     * @param valor Object
     */
    public void estableceValorComponente(final String idComponente, final Object valor) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIViewRoot view = fc.getViewRoot();

        UIComponent componente = view.findComponent(idComponente);
        if (componente instanceof UIInput) {
            UIInput ele = (UIInput) componente;
            ele.setValue(valor);
            actualizarComponente(idComponente);
        }
    }

    /**
     * Recupera una parametro global desde el cache.
     *
     * @param nemonico
     * @return
     */
    protected ParametroGlobal obtenerParametroGlobal(final String nemonico) {
        List<ParametroGlobal> parametros = (List<ParametroGlobal>) obtenerCache(
                CacheEnum.PARAMETROS_GLOBALES.getCodigo());
        ParametroGlobal obj = null;
        for (ParametroGlobal pg : parametros) {
            if (pg.getNemonico().equals(nemonico)) {
                obj = pg;
                break;
            }
        }
        return obj;
    }

    /**
     * M�todo usado para recuperar mensajes desde el archivo de propiedades de la aplicacion.
     *
     * @param key Es la clave por la que se recuperara el mensaje en el archivo de propiedades
     * @param params Si el mensaje tiene parametros, este array contiene los par�metros necesarios para completar el
     * mensaje
     * @return El mensaje recuperado
     */
    public static String getBundle(final String key, final Object[] params) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().
                getLocale();
        //LOG.trace(locale);
        ResourceBundle bundle = ResourceBundle.getBundle(
                BaseControlador.BUNDLE_NAME, locale, getCurrentClassLoader(params));
        String mensaje = recuperarRecurso(bundle, key);
        if (mensaje == null) {
            mensaje = key;
        } else if (params != null && params.length > 0) {
            MessageFormat mf = new MessageFormat(mensaje, locale);
            mensaje = mf.format(
                    params, new StringBuffer(), null).toString();
        }
        return mensaje;
    }

    /**
     * Se encarga de recuperar una instancia del classloader del thread actual.
     *
     * @param defaultObject objeto usado para recuperar el class loader
     * @return Instancia del class loader
     */
    protected static ClassLoader getCurrentClassLoader(final Object defaultObject) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader;
    }

    /**
     * Se encarga de recuperar un recuros desde el repositorio de recursos pasado como parametro.
     *
     * @param bundle repositorio de recursos
     * @param key key del recurso que se quiere recuperar
     * @return el recurso recuperado, si no existe retorna null
     */
    private static String recuperarRecurso(final ResourceBundle bundle, final String key) {
        String mensaje = null;
        try {
            mensaje = bundle.getString(key);
        } catch (MissingResourceException e) {
            LOG.info(UtilCadena.concatenar("No existe el recurso ", key,
                    " en el archivo ", BaseControlador.BUNDLE_NAME, ".  Asegurese de ingresarlo"));
            mensaje = generarValorRecursoDesconocido(key);
        }
        return mensaje;
    }

    /**
     * Se encarga de generar una cadena que representa a un recurso no encontrado.
     *
     * @param keyRecursoBuscado el key del mensaje buscado en el archivo de propiedades.
     * @return una cadena con signos ??? al inicio y al final
     */
    public static String generarValorRecursoDesconocido(final String keyRecursoBuscado) {
        return UtilCadena.concatenar("???", keyRecursoBuscado, "??");
    }

    /**
     *
     * @param mensaje
     * @return
     */
    protected String limpiarMensajeDeExcepcion(final String mensaje) {
        int idx = mensaje.indexOf(':');
        if (idx > 0) {
            return mensaje.substring(idx + 1);
        } else {
            return mensaje;
        }
    }

    /**
     *
     * @param parametro
     * @return
     */
    protected boolean esRRHH(String parametro) {
        String[] unidades = parametro.split(";");
        for (String unidad : unidades) {
            if (obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo().equals(
                    unidad)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param uo
     * @return
     */
    public String buscarNombreUnidadOrganizacional(UnidadOrganizacional uo) {
        return uo != null ? uo.getRuta() : "";
    }
    
    /**
     * 
     * @param codigoTipoIdentificacion
     * @return 
     */
    public String obtenerTipoIdentificacionDadoCodigo(String codigoTipoIdentificacion) {
        return TipoIdentificacionEnum.obtenerDescripcion(codigoTipoIdentificacion);
    }
    
    /**
     * concatena el nombre del servidor con su numero de identificación
     *
     * @param s servidor
     * @return
     */
    public String concatenarNombreIdentificacion(Servidor s) {
        StringBuilder nombreIdentificacion = new StringBuilder("");
        if (s != null) {
            nombreIdentificacion.append(s.getApellidosNombres());
            if (s.getId() != null && s.getId() != 0L) {
                nombreIdentificacion.append(" (").append(s.getNumeroIdentificacion()).append(")");
            }
        }
        return nombreIdentificacion.toString();
    }

    /**
     *
     * @return
     */
//    protected List<Valor> buscarUnidadesOrganizacionalPermitidas() {
//        List<Valor> valores = new ArrayList<>();
//        try {
//            ParametroInstitucional pi
//                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
//                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
//            List<UnidadOrganizacional> unidades;
//            if (esRRHH(pi.getValorTexto())) {
//                unidades = unidadOrganizacionalDao.buscarPorCodigoLike("1");
//            } else {
//                desconcentradoServicio.buscarUnidadesDeAcceso(ESTADO_SALDO_ACTIVO, reporte);
//
//            }
//
//            Boolean esRRHH = administracionServicio.esUnidadOrganizacionDeRecursosHumanos(obtenerUsuarioConectado());
//            List<UnidadOrganizacional> unidades;
//            if (esRRHH) {
//                unidades = unidadOrganizacionalDao.buscarPorCodigoLike("1");
//            } else {
//                unidades = unidadOrganizacionalDao.buscarPorCodigoLike(
//                        obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());
//            }
//            for (UnidadOrganizacional uo : unidades) {
//                valores.add(new Valor(uo.getId(), uo.getRuta()));
//            }
//        } catch (Exception e) {
//        }
//        return valores;
//    }
    /**
     * Funcion que retira espacios en blanco y line breaks de textos cortados y pegados.
     *
     * @return
     * @param cadena
     */
    public String retirarEspaciosCadenaTexto(String cadena) {
        if (cadena == null) {
            return null;
        }
        String trim = cadena.trim();
        String quitarEspacios = trim.replaceAll("\n", "");
        return quitarEspacios;
    }

    /**
     *
     * @param throwable
     * @return
     */
    public Throwable getRootCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            return getRootCause(throwable.getCause());
        }
        return throwable;
    }

}
