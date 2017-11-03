/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.enums.EstadoNotificacionDestinatarioEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoIdentificacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.SeguridadClaveAnteriroIncorrectaException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Institucion;
import ec.com.atikasoft.proteus.modelo.Menu;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import ec.com.atikasoft.proteus.servicio.NotificacionesServicio;
import ec.com.atikasoft.proteus.servicio.SeguridadServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.MenuVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

/**
 * Controlador de inicio de sistema.
 *
 * @author henry molina
 */
@ManagedBean
@SessionScoped
public class InicioControlador extends BaseControlador {

    private static final Logger LOG = Logger.getLogger(InicioControlador.class.getName());

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Servicio de seguridad.
     */
    @EJB
    private SeguridadServicio seguridadServicio;

    /**
     *
     */
    private String usuario;
    /**
     *
     */
    private String usuarioOlvido;
    /**
     *
     */
    private String clave;
    /**
     *
     */
    private String claveOlvido;
    /**
     *
     */
    private String claveOlvidoR;

    /**
     *
     */
    private String identificadorSesion;
    /**
     *
     */
    private Integer oficina = 12;
    /**
     *
     */
    private List<String> roles;
    /**
     *
     */
    private HttpSession sessionIniciada;
    /**
     *
     */
    private Institucion institucion;
    /**
     *
     */
    private boolean apareceInstiucion;
    /**
     *
     */
    private DistributivoDetalle distributivoDetalle;

    /**
     * Cambio de clave usuario portal
     */
    private String contrasennaAnterior;
    /**
     *
     */
    private String contrasennaNueva;
    /**
     *
     */
    private String contrasennaNuevaConfirmacion;

    /**
     * Servicio de administracion de menu y roles.
     */
    @EJB
    private MenuServicio menuServicio;

    /**
     *
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;

    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;
    /**
     *
     */
    @EJB
    private NotificacionesServicio notificacionesServicio;

    /**
     *
     */
    private MenuModel model;
    /**
     *
     */
    private Boolean cambioClave = Boolean.FALSE;
    /**
     *
     */
    private Boolean esUsuarioWS = Boolean.FALSE;

    /**
     *
     */
    @PostConstruct
    private void cargarSession() {
        this.sessionIniciada = getRequest().getSession();
    }

    /**
     * Verifica que el usuario no este logueado.
     *
     * @return
     */
    private boolean validarUso() {
        Boolean resultado = false;
        List<String> usuarios = (List<String>) getSession().getServletContext().getAttribute("USUARIOS");
        if (usuarios.contains(getUsuario())) {
            resultado = true;
        } else {
            usuarios.add(getUsuario());
        }
        return false;
    }

    /**
     * Valida la ejecucion de nomina.
     *
     * @return
     * @throws DaoException
     */
    private Boolean validarEjecucionNomina() throws DaoException {
        int ini = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                1l, ParametroInstitucionCatalogoEnum.DIA_INICIO_CALCULO_NOMINA.getCodigo()).getValorNumerico().intValue();
        int fin = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                1l, ParametroInstitucionCatalogoEnum.DIA_FINAL_CALCULO_NOMINA.getCodigo()).getValorNumerico().intValue();
        if (UtilFechas.obtenerDiaDelMes(new Date()) >= ini && UtilFechas.obtenerDiaDelMes(new Date()) <= fin) {
            String usuariosAutorizados = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    1l, ParametroInstitucionCatalogoEnum.USUARIOS_AUTORIZADOS_CALCULO_NOMINA.getCodigo()).getValorTexto();
            return !usuariosAutorizados.contains(getUsuario());
        } else {
            return false;
        }
    }

    /**
     * Realiza la autentifica de usuarios.
     *
     */
    public void autentificar() {
        try {
            roles = new ArrayList<String>();
            if (validarEjecucionNomina()) {
                ponerMensajeAlerta(NADA, "NÓMINA EN EJECUCIÓN , NO PUEDE USAR EL SISTEMA POR EL MOMENTO........");
            } else {
                //UsuarioVO vo = seguridadServicio.autentificar(getUsuario(), getClave(), getOficina(), getRequest().getRemoteAddr());
                UsuarioVO vo = seguridadServicio.autentificar(getUsuario(), getClave(), 12, getRequest().getRemoteAddr());
//                LOG.log(Level.INFO, " con accceso ====>{0}", vo.getConAcceso());
                if (vo.getConAcceso()) {
                    EjercicioFiscal buscarEjercicioFiscalActivo = administracionServicio.buscarEjercicioFiscalActivo();
                    if (buscarEjercicioFiscalActivo == null) {
                        ponerMensajeAlerta(NADA, "No existe un ejercicio fiscal activo");
                    } else {
                        vo.setEjercicioFiscal(buscarEjercicioFiscalActivo);
                        // recuperar las unidades de acceso
                        getSession().setAttribute(BaseControlador.DTO_USER_NAME, vo);
                        ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                                vo.getInstitucion().getId(), vo.getServidor().getId());
                        if (si == null) {
                            si = new ServidorInstitucion();
                            si.setFechaCreacion(new Date());
                            si.setFechaIngreso(vo.getServidor().getFechaIngresoInstitucion());
                            si.setIdInstitucion(vo.getInstitucion().getId());
                            si.setIdServidor(vo.getServidor().getId());
                            si.setUsuarioCreacion(obtenerUsuarioConectado().getUsuario());
                            si.setVigente(true);
                            servidorInstitucionDao.crear(si);
                        } else if (!si.getVigente()) {
                            si.setFechaIngreso(vo.getServidor().getFechaIngresoInstitucion());
                            si.setFechaActualizacion(new Date());
                            si.setUsuarioActualizacion(obtenerUsuarioConectado().getUsuario());
                            si.setVigente(true);
                            servidorInstitucionDao.actualizar(si);
                        }
                        contarNotificacionesNoLeidas();
                        cargarMenuDinamico(vo);
                        if (vo.getEsUsuarioWS()) {
                            redirect(getContextName() + "/pages/index.jsf");
                        } else {
                            redirect(getContextName() + "/pages/portal_rrhh.jsf");
                        }
                    }
                } else if (vo.getCambiarClave()) {
                    setUsuarioOlvido(getUsuario());
                    setEsUsuarioWS(vo.getEsUsuarioWS());
                    if (vo.getEsUsuarioWS()) {
                        setIdentificadorSesion(vo.getResultadoAutentificarVO().getIdentificadorSesion());
                    } else {
                        setIdentificadorSesion(getSession().getId());
                        vo.getResultadoAutentificarVO().setIdentificadorSesion(getIdentificadorSesion());
                    }
                    mostrarPopupOlvidoClave();
//                    ponerMensajeError(NADA, "Clave caducada, espere hasta que exista la opcion de cambio de clave.");
                } else {
                    ponerMensajeError(NADA, "Usuario no puede ingresar: " + vo.getResultadoAutentificarVO().getMensaje());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            ponerMensajeError(NADA, "Error a realizar la autentificación del usuario.");
            error(getClass().getName(), "Error a realizar la autentificación del usuario", e);
        }

    }

    /**
     * Realiza la autentifica de usuarios.
     *
     * @return
     */
    public String cambioClave() {
        try {
            if (getClave().equals(getClaveOlvido())) {
                mostrarMensajeEnPantalla("La clave nueva debe ser diferente a la clave anterior.", FacesMessage.SEVERITY_ERROR);
                return null;
            }
//            System.out.println("usuario:" + getUsuario() + " usuario olvido:" + getUsuarioOlvido());
//            System.out.println("clave antigua:" + getClave() + "clave nueva:" + getClaveOlvido() + " repeticion:" + getClaveOlvidoR());
            if (getEsUsuarioWS()) {
                setCambioClave(seguridadServicio.cambioClaveWS(getIdentificadorSesion(),
                        getUsuarioOlvido(), getClave(), getClaveOlvido()));
            } else {
                setCambioClave(seguridadServicio.cambioClaveServidor(getUsuarioOlvido(), getClave(), getClaveOlvido()));
            }
            if (getCambioClave() && getClaveOlvido().equals(getClaveOlvidoR())) {
                mostrarMensajeEnPantalla("Cambio de clave se realizó correctamente.", FacesMessage.SEVERITY_INFO);
                Limpiar();
                setUsuarioOlvido(null);
            } else {
                mostrarMensajeEnPantalla("Ocurrió algún problema en el cambio de clave.", FacesMessage.SEVERITY_INFO);
                Limpiar();
            }
            ejecutarComandoPrimefaces("dlgOlvidoClave.hide();");
        } catch (SeguridadClaveAnteriroIncorrectaException ex) {
            Logger.getLogger(InicioControlador.class.getName()).log(Level.SEVERE, null, ex);
            ponerMensajeError(NADA, "La clave anterior es incorrecta.");
        } catch (ServicioException e) {
            e.printStackTrace();
            ponerMensajeError(NADA, "Error a realizar cambio de clave del usuario.");
        }
        return null;
    }

    public void Limpiar() {
        setIdentificadorSesion(null);
//        setUsuarioOlvido(null);
        setClaveOlvido(null);
        setClaveOlvidoR(null);
        setUsuario(null);
        setClave(null);
        setOficina(12);
    }

    /**
     * muestra la ventana.
     */
    public void mostrarPopupOlvidoClave() {
        actualizarComponente("dlgOlvidoClavedlg");
        ejecutarComandoPrimefaces("dlgOlvidoClave.show();");
    }

    /**
     *
     * @param s
     * @param nombre
     * @param url
     */
    private void registrarMenu(final Submenu s, final String nombre, final String url) {
        MenuItem m = new MenuItem();
        m.setValue(nombre);
        m.setUrl(url);
        m.setAjax(false);
        s.getChildren().add(m);

    }

    /**
     * Este metodo transacciona la busqueda del nombre del estado del anticipo parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoIdentificacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Registra los submenus de un menu principal
     *
     * @param listaMenu
     * @param subMenu
     */
    private void cargarItems(final Submenu subMenu, List<Menu> listaMenu) {
        for (Menu m : listaMenu) {

            MenuVO mvo = new MenuVO();
            mvo.setServidorId(obtenerUsuarioConectado().getServidor().getId());
            mvo.setMenuPrincipalId(m.getId());
            mvo.setEsPrincipal(Boolean.FALSE);
            mvo.setTipo("E");
            try {
                listaMenu = menuServicio.listarTodosMenus(mvo);
                if (!listaMenu.isEmpty()) {
                    Submenu submenuHijo = new Submenu();
                    submenuHijo.setLabel(m.getEtiqueta());
                    subMenu.getChildren().add(submenuHijo);
                    cargarItems(submenuHijo, listaMenu);
                } else {
                    registrarMenu(subMenu, m.getEtiqueta(), m.getUrl());
                }
            } catch (ServicioException ex) {
                Logger.getLogger(InicioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Permite generar el menu dinámicamente de acuerdo a los permisos del usuario.
     *
     * @param vo
     */
    private void cargarMenuDinamico(UsuarioVO vo) {
        try {
            if (vo.getServidor() != null) {
                MenuVO mvo = new MenuVO();
                mvo.setServidorId(vo.getServidor().getId());
                mvo.setEsPrincipal(Boolean.TRUE);
                mvo.setTipo("E");
                List<Menu> listaMenuRolPrincipales = menuServicio.listarTodosMenus(mvo);

                this.model = new DefaultMenuModel();
                for (Menu m : listaMenuRolPrincipales) {

                    Submenu subMenu = new Submenu();
                    subMenu.setLabel(m.getEtiqueta());

                    mvo = new MenuVO();
                    mvo.setServidorId(vo.getServidor().getId());
                    mvo.setMenuPrincipalId(m.getId());
                    mvo.setEsPrincipal(Boolean.FALSE);
                    mvo.setTipo("E");
                    List<Menu> listaMenu = menuServicio.listarTodosMenus(mvo);

                    if (!listaMenu.isEmpty()) {
                        cargarItems(subMenu, listaMenu);
                    }
                    this.model.addSubmenu(subMenu);
                }

//                Submenu s0 = new Submenu();
//                s0.setLabel("Portal de RRHH");
//                this.model.addSubmenu(s0);
//                registrarMenu(s0, "Servicios", "/pages/portal_rrhh.jsf");
//                registrarMenu(s0, "Aprobación Vacaciones",
//                        "/pages/procesos/vacacion/lista_solicitud_vacacion_aprobacion.jsf");
//                registrarMenu(s0, "Validación Planificación Vacaciones",
//                        "/pages/procesos/vacacion/lista_planificacion_vacacion_validacion.jsf");
//                registrarMenu(s0, "Aprobación Planificación Vacaciones",
//                        "/pages/procesos/vacacion/lista_planificacion_vacacion_aprobacion.jsf");
//                registrarMenu(s0, "Actualización Ficha de Personal",
//                        "/pages/administracion/ficha_personal_rrhh/ficha_personal_rrhh.jsf");
//
//                Submenu s1 = new Submenu();
//                s1.setLabel("Adm. Movimientos de Personal");
//                this.model.addSubmenu(s1);
//                registrarMenu(s1, "Grupos", "/pages/administracion/grupo/lista_grupo.jsf");
//                registrarMenu(s1, "Clases", "/pages/administracion/clase/lista_clase.jsf");
//                registrarMenu(s1, "Tipo de Movimientos", "/pages/administracion/tipo_movimiento/lista_tipo_movimiento.jsf");
//                registrarMenu(s1, "Documentos Habilitantes",
//                        "/pages/administracion/documento_habilitante/lista_documento_habilitante.jsf");
//                registrarMenu(s1, "Reglas", "/pages/administracion/regla/lista_regla.jsf");
//                registrarMenu(s1, "Requisitos", "/pages/administracion/requisito/lista_requisito.jsf");
//
//                Submenu s2 = new Submenu();
//                s2.setLabel("Adm. Nóminas");
//                this.model.addSubmenu(s2);
//                registrarMenu(s2, "Regimenes Laborales", "/pages/administracion/regimen_laboral/lista_regimen_laboral.jsf");
//                registrarMenu(s2, "Modalidades Laborales",
//                        "/pages/administracion/modalidad_laboral/lista_modalidad_laboral.jsf");
//                registrarMenu(s2, "Grupo Ocupacional",
//                        "/pages/administracion/denominacion_puesto/lista_denominacion_puesto.jsf");
//                registrarMenu(s2, "Estados de Puestos", "/pages/administracion/estado_puesto/lista_estado_puesto.jsf");
//                registrarMenu(s2, "Estados del Servidor", "/pages/administracion/estado_personal/lista_estado_personal.jsf");
//                registrarMenu(s2, "Datos Adicionales", "/pages/administracion/dato_adicional/lista_dato_adicional.jsf");
//                registrarMenu(s2, "Constantes", "/pages/administracion/constante/lista_constante.jsf");
//                registrarMenu(s2, "Variables", "/pages/administracion/variable/lista_variable.jsf");
//                registrarMenu(s2, "Formulas", "/pages/administracion/formula/lista_formula.jsf");
//                registrarMenu(s2, "Rubros", "/pages/administracion/rubro/lista_rubro.jsf");
//                registrarMenu(s2, "Metadata", "/pages/administracion/metadata_tabla_columna/lista_metadata_tabla.jsf");
//                registrarMenu(s2, "Campos de Accesos", "/pages/administracion/campo_acceso/lista_campo_acceso.jsf");
//                registrarMenu(s2, "Periodos de Nóminas", "/pages/administracion/nomina/lista_nomina.jsf");
//                registrarMenu(s2, "Tipos de Nóminas", "/pages/administracion/nomina/lista_tipo_nomina.jsf");
//                registrarMenu(s2, "Cotizaciones IESS", "/pages/administracion/cotizacion_iess/lista_cotizacion_iess.jsf");
//                registrarMenu(s2, "Beneficiarios",
//                        "/pages/administracion/beneficiario_institucional/lista_beneficiario_institucional.jsf");
//
//                Submenu s3 = new Submenu();
//                s3.setLabel("Adm. General");
//                this.model.addSubmenu(s3);
//                registrarMenu(s3, "Ejercicio Fiscal", "/pages/administracion/ejercicio_fiscal/lista_ejercicio_fiscal.jsf");
//                registrarMenu(s3, "Catalogo de Parámetros Institucionales",
//                        "/pages/administracion/parametro_institucion_catalogo/lista_parametro_institucion_catalogo.jsf");
//                registrarMenu(s3, "Parámetros Globales", "/pages/administracion/parametro_global/lista_parametro_global.jsf");
//                registrarMenu(s3, "Parámetros Institucionales",
//                        "/pages/administracion/parametro_institucional/lista_parametro_institucional.jsf");
//                registrarMenu(s3, "Delegación de Aprobación",
//                        "/pages/administracion/delegacion_aprobacion/lista_delegacion_aprobacion.jsf");
//                registrarMenu(s3, "Descentralización de Gestión de Movimientos",
//                        "/pages/procesos/descentralizacion/lista_tipo_movimiento.jsf");
//                registrarMenu(s3, "Habilitacion de Instituciones", "/pages/administracion/institucion/lista_institucion.jsf");
//                registrarMenu(s3, "Bancos", "/pages/administracion/banco/lista_banco.jsf");
//                registrarMenu(s3, "Firmas", "/pages/administracion/servidor/registro_firma.jsf");
//                registrarMenu(s3, "Cuentas Bancarias", "/pages/administracion/cuenta_bancaria/lista_cuenta_bancaria.jsf");
//                registrarMenu(s3, "Unidades Organizacionales",
//                        "/pages/administracion/unidad_organizacional/unidad_organizacional.jsf");
//                registrarMenu(s3, "Unidades Presupuestarias",
//                        "/pages/administracion/unidad_organizacional/unidad_presupuestaria.jsf");
//
//                registrarMenu(s3, "Unidades Organizacionales de Aprobación",
//                        "/pages/administracion/unidad_organizacional/unidad_aprobacion.jsf");
//                registrarMenu(s3, "Feriados", "/pages/administracion/feriado/lista_feriado.jsf");
//                registrarMenu(s3, "Vacaciones", "/pages/administracion/vacacion/lista_vacacion_parametro.jsf");
//                registrarMenu(s3, "Ubicaciones Geografica",
//                        "/pages/administracion/ubicacion_geografica/ubicacion_geografica.jsf");
//                registrarMenu(s3, "Catalogos", "/pages/administracion/catalogo/lista_catalogo.jsf");
//                registrarMenu(s3, "Puestos", "/pages/administracion/puesto/lista_puesto.jsf");
//                registrarMenu(s3, "Tipo Anticipo", "/pages/administracion/tipo_anticipo/lista_tipo_anticipo.jsf");
//                registrarMenu(s3, "Registro Novedades", "/pages/procesos/novedad/lista_novedades.jsf");
//                registrarMenu(s3, "Planificación Horario Laboral",
//                        "/pages/administracion/asistencia/planificacion_horario.jsf");
//
//                Submenu s4 = new Submenu();
//                s4.setLabel("Movimientos de Personal");
//                this.model.addSubmenu(s4);
//
//                registrarMenu(s4, "Bandeja de Trámites", "/pages/bandeja_tarea/bandeja_tarea.jsf");
//                registrarMenu(s4, "Nuevo Trámite", "/pages/procesos/tramite/tramite.jsf");
//                registrarMenu(s4, "Trámites en Borrador", "/pages/procesos/tramite/tramite_borrador.jsf");
//                registrarMenu(s4, "Reasignacion Trámites", "/pages/administracion/institucion/lista_institucion.jsf");
////
////
////
//                Submenu s5 = new Submenu();
//                s5.setLabel("Nómina");
//                this.model.addSubmenu(s5);
//                registrarMenu(s5, "Nóminas", "/pages/procesos/nomina/lista_nominas.jsf");
////
////
//                Submenu s6 = new Submenu();
//                s6.setLabel("Consultas");
//                this.model.addSubmenu(s6);
//                registrarMenu(s6, "Historial Movimientos Servidor",
//                        "/pages/consultas/consulta_historial _movimientos_servidor.jsf");
//                registrarMenu(s6, "Consulta Trámites", "/pages/consultas/consulta_movimientos_institucion.jsf");
//                registrarMenu(s6, "Consulta Movimientos", "/pages/consultas/consulta_movimientos_analista.jsf");
//                registrarMenu(s6, "Consulta Distributivo", "/pages/procesos/consultar_distributivo/busqueda_puesto.jsf");
//                registrarMenu(s6, "Consulta Servidores", "/pages/consultas/busqueda_servidor.jsf");
//                registrarMenu(s6, "Consulta Vacaciones", "/pages/consultas/busqueda_vacaciones.jsf");
//                registrarMenu(s6, "Consulta Anticipos", "/pages/consultas/consulta_anticipo.jsf");
//                registrarMenu(s6, "Consulta Novedades", "/pages/consultas/consulta_novedad.jsf");
//                Submenu s7 = new Submenu();
//                s7.setLabel("Sistema");
//                this.model.addSubmenu(s7);
//                registrarMenu(s7, "Roles", "/pages/administracion/rol/lista_rol.jsf");
//                registrarMenu(s7, "Opciones de Menú", "/pages/administracion/menu/menu.jsf");
//                registrarMenu(s7, "Permisos a Usuarios", "/pages/administracion/rol/lista_rol_por_servidor.jsf");
//            List<EntityMenuDTO> listaTodos = accesoServicio.listarPorUsuarioRol(this.listaUsuarioRol);
//            this.model = new DefaultMenuModel();
//            Acceso acceso = accesoServicio.obtenerPorNombre("MOVIMIENTOS PERSONAL");
//
//            info(getClass().getName(), acceso.getId().toString());
//            for (EntityMenuDTO menu : listaTodos) {
//                if (menu.getIdMenuPadre() != null) {
//                    if (menu.getIdMenuPadre().longValue() == acceso.getId().longValue() && menu.getActionMenu() == null) {
//                        Submenu subMenu = new Submenu();
//                        subMenu.setLabel(menu.getEtiquetaMenu());
//                        cargarItems(menu, listaTodos, subMenu, null);
//                        this.model.addSubmenu(subMenu);
//                    }
//
//                }
//            }
            }
        } catch (ServicioException e) {
            ponerMensajeError(NADA, "No se puede generar menú dinámico favor revise el log.");
            error(getClass().getName(), "no se puede generar menu dinamico", e);
        }
    }

//    private MenuItem cargarItems(EntityMenuDTO men, List<EntityMenuDTO> listaTodo, Submenu menuPadre, MenuItem menuItem) {
//        try {
//            for (EntityMenuDTO menu : listaTodo) {
//                if (menu.getIdMenuPadre() != null) {
//                    if (men.getIdMenu().longValue() == menu.getIdMenuPadre().longValue()) {
//                        if (menu.getActionMenu() != null) {
//                            MenuItem menuItem1 = new MenuItem();
//                            menuItem1.setValue(menu.getEtiquetaMenu());
////                            menuItem1.setActionExpression(crearActionFormularios(menu.getActionMenu()));
//                            menuItem1.setUrl(menu.getActionMenu());
//                            menuItem1.setAjax(false);
//                            menuPadre.getChildren().add(menuItem1);
//                        } else {
//                            Submenu submenuHijo = new Submenu();
//                            submenuHijo.setLabel(menu.getEtiquetaMenu());
//                            menuPadre.getChildren().add(submenuHijo);
//                            MenuItem menues = cargarItems(menu, listaTodo, submenuHijo, menuItem);
//                            if (menues != null) {
//                                submenuHijo.getChildren().add(menues);
//                            }
//                        }
//                    }
//                }
//            }
//            return menuItem;
//        } catch (Exception e) {
//            error(getClass().getName(), "", e);
//            return null;
//        }
//    }
    public void cerrarSesion() {
        try {
            List<String> usuarios = (List<String>) getSession().getServletContext().getAttribute("USUARIOS");
            usuarios.remove(obtenerUsuario());
            closeSession();
        } catch (Exception e) {
            error(getClass().getName(), "no se puede cerrar sesion", e);
        }
    }

    /**
     * Cambio de contrase;a usuario portal -----------------------------------
     */
    /**
     * Resetear los campos referentes a las contrase;a del usuario portal
     */
    public void iniciarCambioContrasenna() {
        contrasennaAnterior = null;
        contrasennaNueva = null;
        contrasennaNuevaConfirmacion = null;
    }

    public void cambiarContrasenna() {
        boolean error = false;
        UsuarioVO usuario = obtenerUsuarioConectado();
        if (contrasennaNueva == null) {
            mostrarMensajeEnPantalla("Clave nueva es requerida", FacesMessage.SEVERITY_ERROR);
            error = true;
        }
        if (contrasennaAnterior == null) {
            mostrarMensajeEnPantalla("Clave anterior es requerida", FacesMessage.SEVERITY_ERROR);
            error = true;
        }
        if (!error) {
            try {
                boolean ok = seguridadServicio.cambioClaveServidor(usuario.getServidor().getNumeroIdentificacion(), contrasennaAnterior, contrasennaNueva);
                if (ok) {
                    mostrarMensajeEnPantalla("Clave se actualizo correctamente", FacesMessage.SEVERITY_INFO);
                    ejecutarComandoPrimefaces("dlgCambioPass.hide();");
                } else {
                    mostrarMensajeEnPantalla("Se presento un error al actualizar su clave", FacesMessage.SEVERITY_ERROR);
                }
            } catch (Exception ex) {
                Logger.getLogger(InicioControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensajeEnPantalla(getRootCause(ex).getMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    /**
     * Crea en base de datos los registros de remitentes asociados a las notificaciones enviadas para todos los
     * servidores
     */
    private void crearDestinatariosNotificacionesParaNotificacionesMasivas() {
        try {
            UsuarioVO uc = obtenerUsuarioConectado();
            notificacionesServicio.crearDestinatarioNotificacionesParaNotificacionesMasivas(uc);
        } catch (Exception ex) {
            Logger.getLogger(InicioControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensajeEnPantalla(getRootCause(ex).getMessage(), FacesMessage.SEVERITY_ERROR);
        }

    }

    /**
     * Cuenta las notificaciones no leías para un servidor y guarda los datos en session
     */
    public void contarNotificacionesNoLeidas() {
        try {
            if (obtenerUsuarioConectado() != null) {
                crearDestinatariosNotificacionesParaNotificacionesMasivas();
                Map<Long, String> infoNotificacionesNoLeidas = new HashMap<>();
                long cantidadNoLeidas = notificacionesServicio.
                        contarNotificacionesPorDestinatarioIdYEstadoLectura(
                                obtenerUsuarioConectado().getServidor().getId(),
                                EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo());
                if (cantidadNoLeidas == 1) {
                    infoNotificacionesNoLeidas.put(cantidadNoLeidas, "Tiene 1 notificación sin leer");

                } else if (cantidadNoLeidas > 1) {
                    infoNotificacionesNoLeidas.put(
                            cantidadNoLeidas, "Tiene " + cantidadNoLeidas + " notificaciones sin leer");

                } else {
                    infoNotificacionesNoLeidas.put(0L, "");
                }
                ponerAtributoSession(BaseControlador.INFO_NOTIFICACIONES_NO_LEIDAS, infoNotificacionesNoLeidas);
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar contar notificaciones no leídas por el servidor", e);
        }
    }

    /**
     * envía a la vista los datos de las notificaciones sin leer
     *
     * @return
     */
    public Map<String, Object> getDatosNotificaciones() {
        Map<Long, String> info = (Map) obtenerAtributoSession(BaseControlador.INFO_NOTIFICACIONES_NO_LEIDAS);
        Map<String, Object> datosNotificaciones = new HashMap<>();
        if (info != null && !info.isEmpty()) {
            datosNotificaciones.put("cantidad", info.keySet().toArray()[0]);
            datosNotificaciones.put("mensaje", info.values().toArray()[0]);
        } else {
            datosNotificaciones.put("cantidad", 0);
            datosNotificaciones.put("mensaje", 0);

        }
        return datosNotificaciones;
    }

    /**
     *
     * @return
     */
    public String irANotificaciones() {
        return "/pages/notificaciones/bandeja_notificaciones.jsf";
    }

    /**
     * Transforma
     *
     * @param cantidadNotificaciones numero de notificaciones no leidas
     * @return
     */
    public String totalNotificacionesTexto(Object cantidadNotificaciones) {
        return cantidadNotificaciones.toString();
    }

    /**
     * @return the sessionIniciada
     */
    public HttpSession getSessionIniciada() {
        return sessionIniciada;
    }

    /**
     * @param sessionIniciada the sessionIniciada to set
     */
    public void setSessionIniciada(final HttpSession sessionIniciada) {
        this.sessionIniciada = sessionIniciada;
    }

    /**
     * @return the model
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(final MenuModel model) {
        this.model = model;
    }

    /**
     * @return the institucion
     */
    public Institucion getInstitucion() {
        return institucion;
    }

    /**
     * @param institucion the institucion to set
     */
    public void setInstitucion(final Institucion institucion) {
        this.institucion = institucion;
    }

    /**
     * @return the apareceInstiucion
     */
    public boolean isApareceInstiucion() {
        return apareceInstiucion;
    }

    /**
     * @param apareceInstiucion the apareceInstiucion to set
     */
    public void setApareceInstiucion(final boolean apareceInstiucion) {
        this.apareceInstiucion = apareceInstiucion;
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final String usuario) {
        this.usuario = usuario;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(final String clave) {
        this.clave = clave;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the oficina
     */
    public Integer getOficina() {
        return oficina;
    }

    /**
     * @param oficina the oficina to set
     */
    public void setOficina(final Integer oficina) {
        this.oficina = oficina;
    }

    /**
     * @return the distributivoDetalle
     */
    public DistributivoDetalle getDistributivoDetalle() {
        return distributivoDetalle;
    }

    /**
     * @param distributivoDetalle the distributivoDetalle to set
     */
    public void setDistributivoDetalle(DistributivoDetalle distributivoDetalle) {
        this.distributivoDetalle = distributivoDetalle;
    }

    /**
     * @return the usuarioOlvido
     */
    public String getUsuarioOlvido() {
        return usuarioOlvido;
    }

    /**
     * @param usuarioOlvido the usuarioOlvido to set
     */
    public void setUsuarioOlvido(String usuarioOlvido) {
        this.usuarioOlvido = usuarioOlvido;
    }

    /**
     * @return the claveOlvido
     */
    public String getClaveOlvido() {
        return claveOlvido;
    }

    /**
     * @param claveOlvido the claveOlvido to set
     */
    public void setClaveOlvido(String claveOlvido) {
        this.claveOlvido = claveOlvido;
    }

    /**
     * @return the claveOlvidoR
     */
    public String getClaveOlvidoR() {
        return claveOlvidoR;
    }

    /**
     * @param claveOlvidoR the claveOlvidoR to set
     */
    public void setClaveOlvidoR(String claveOlvidoR) {
        this.claveOlvidoR = claveOlvidoR;
    }

    /**
     * @return the identificadorSesion
     */
    public String getIdentificadorSesion() {
        return identificadorSesion;
    }

    /**
     * @param identificadorSesion the identificadorSesion to set
     */
    public void setIdentificadorSesion(String identificadorSesion) {
        this.identificadorSesion = identificadorSesion;
    }

    /**
     * @return the cambioClave
     */
    public Boolean getCambioClave() {
        return cambioClave;
    }

    /**
     * @param cambioClave the cambioClave to set
     */
    public void setCambioClave(Boolean cambioClave) {
        this.cambioClave = cambioClave;
    }

    /**
     * @return the esUsuarioWS
     */
    public Boolean getEsUsuarioWS() {
        return esUsuarioWS;
    }

    /**
     * @param esUsuarioWS the esUsuarioWS to set
     */
    public void setEsUsuarioWS(Boolean esUsuarioWS) {
        this.esUsuarioWS = esUsuarioWS;
    }

    public String getContrasennaNueva() {
        return contrasennaNueva;
    }

    public void setContrasennaNueva(String contrasennaNueva) {
        this.contrasennaNueva = contrasennaNueva;
    }

    public String getContrasennaAnterior() {
        return contrasennaAnterior;
    }

    public void setContrasennaAnterior(String contrasennaAnterior) {
        this.contrasennaAnterior = contrasennaAnterior;
    }

    public String getContrasennaNuevaConfirmacion() {
        return contrasennaNuevaConfirmacion;
    }

    public void setContrasennaNuevaConfirmacion(String contrasennaNuevaConfirmacion) {
        this.contrasennaNuevaConfirmacion = contrasennaNuevaConfirmacion;
    }

}
