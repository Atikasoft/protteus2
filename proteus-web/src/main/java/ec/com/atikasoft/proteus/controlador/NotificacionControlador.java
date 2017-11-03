package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.CatalogoControlador;
import ec.com.atikasoft.proteus.controlador.helper.NotificacionHelper;
import ec.com.atikasoft.proteus.converter.DesconcentradoConverter;
import ec.com.atikasoft.proteus.converter.ServidorConverter;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.EstadoNotificacionDestinatarioEnum;
import ec.com.atikasoft.proteus.enums.EstadoPuestoEnum;
import ec.com.atikasoft.proteus.enums.EstadosPersonalEnum;
import ec.com.atikasoft.proteus.enums.OpcionDestinatarioNotificacionEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Desconcentrado;
import ec.com.atikasoft.proteus.modelo.DesconcentradoApoyo;
import ec.com.atikasoft.proteus.modelo.DestinatarioNotificacion;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Notificacion;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.NotificacionesServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.vo.BusquedaPuestoVO;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "notificacionBean")
@ViewScoped
public class NotificacionControlador extends CatalogoControlador {

    /**
     * pagina listado de notificaciones
     */
    public static final String BANDEJA_NOTIFICACIONES = "/pages/notificaciones/bandeja_notificaciones.jsf";
    /**
     * pagina principal
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * servicio distributivo puesto
     */
    @EJB
    private DistributivoPuestoServicio distributivoPuestoServicio;
    /**
     * Servicio de Servidor
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     * dao de parametro institucional
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;
    /**
     * Servicio de notificaciones
     */
    @EJB
    private NotificacionesServicio notificacionServicio;
    /**
     * Servicio de Desconcetrados
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;
    /**
     * Dao de unidad organizacional
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;
    /**
     * helper de la clase
     */
    @ManagedProperty("#{notificacionHelper}")
    private NotificacionHelper notificacionHelper;

    /**
     *
     */
    public NotificacionControlador() {
        super();
    }

    /**
     *
     */
    @Override
    @PostConstruct
    public void init() {
        setCatalogoHelper(notificacionHelper);
        setNotificacionHelper(notificacionHelper);
        notificacionHelper.setOpcionEnviadasRecibidas(NotificacionHelper.OPCION_RECIBIDAS);
        notificacionHelper.setMostrarRecibidas(Boolean.TRUE);
        buscar();
        llenarListaOpcionesTipoDestintarios();
        verificaAutorizacionEnvioNotificacion();
    }

    /**
     *
     */
    private void verificaAutorizacionEnvioNotificacion() {
        try {
            ParametroInstitucional pa = administracionServicio.buscarParametroIntitucional(
                    obtenerUsuarioConectado().getInstitucion().getId(),
                    ParametroInstitucionCatalogoEnum.USUARIOS_AUTORIZADOS_ENVIO_NOTIFICACIONES.getCodigo());
            if (pa.getValorTexto().contains(obtenerUsuarioConectado().getUsuario())) {
                notificacionHelper.setEnviarNotificaciones(Boolean.TRUE);
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), NADA, e);

        }
    }

    /**
     * recupera la lista de notificaciones recibidas o enviadas
     *
     * @return
     */
    @Override
    public String buscar() {
        try {
            UsuarioVO uc = obtenerUsuarioConectado();
            notificacionHelper.getNotificacionesRecibidas().clear();
            notificacionHelper.getNotificacionesEnviadas().clear();
            if (notificacionHelper.getMostrarRecibidas()) {
                notificacionHelper.getNotificacionesRecibidas().addAll(notificacionServicio.
                        listarDestinatarioNotificacionesPorDestinarioIdInstitucionEjercicioFiscalId(
                                uc.getServidor().getId(), uc.getInstitucionEjercicioFiscal().getId()));
            } else {
                notificacionHelper.getNotificacionesEnviadas().addAll(notificacionServicio.
                        listarNotificacionesPorRemitenteIdInstitucionEjercicioFiscalId(
                                uc.getServidor().getId(), uc.getInstitucionEjercicioFiscal().getId()));
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), NADA, ex);
        }
        return BANDEJA_NOTIFICACIONES;
    }

    /**
     * llena la lista de popciones de tipo de destinatario de la notificacion
     */
    private void llenarListaOpcionesTipoDestintarios() {
        iniciarCombos(notificacionHelper.getListaOpcionesTipoRemitente());
        for (OpcionDestinatarioNotificacionEnum oen : OpcionDestinatarioNotificacionEnum.values()) {
            notificacionHelper.getListaOpcionesTipoRemitente().add(new SelectItem(oen.getCodigo(), oen.getDescripcion()));
        }
    }

    /**
     * Llena la lista de opciones de las unidades de recursos humanos
     */
    private void recuperarUnidesRRHH() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            notificacionHelper.setListaUnidadesRHH(
                    unidadOrganizacionalDao.buscarPorCodigoLikeAnidado(pi.getValorTexto()));

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), NADA, ex);
        }
    }

    /**
     * Indica si se deben mostrar las notificaciones recibidas o las enviadas
     */
    public void seleccionarOpcionEnviadasRecibidas() {
        notificacionHelper.setMostrarRecibidas(
                notificacionHelper.getOpcionEnviadasRecibidas().equals(NotificacionHelper.OPCION_RECIBIDAS));
        buscar();
    }

    /**
     * prepara las variables para crear una nueva notificación
     *
     * @return
     */
    @Override
    public String iniciarNuevo() {
        notificacionHelper.setEsNuevo(Boolean.TRUE);
        notificacionHelper.setOpcionTipoRemitenteSeleccionada(null);
        notificacionHelper.getListaDestinatarios().clear();
        notificacionHelper.getListaUnidadesRHH().clear();
        notificacionHelper.getListaUnidadesDesconcentradas().clear();
        notificacionHelper.getUnidadesDesconcetradasSeleccionadas().clear();
        notificacionHelper.setNotificacion(new Notificacion());
        iniciarDatosEntidad(notificacionHelper.getNotificacion(), Boolean.TRUE);
        notificacionHelper.getNotificacion().setInstitucionEjercicioFiscal(
                obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
        notificacionHelper.getNotificacion().setRemitente(obtenerUsuarioConectado().getServidor());
        notificacionHelper.getListaDestinatarios().clear();
        notificacionHelper.setMostrarDestinatarios(Boolean.TRUE);
        ejecutarComandoPrimefaces("dlgNotificacion.show()");
        return null;
    }

    /**
     * inicia lectura de la notificación
     *
     * @return
     */
    @Override
    public String iniciarEdicion() {
        try {
            notificacionHelper.setEsNuevo(Boolean.FALSE);
            if (notificacionHelper.getMostrarRecibidas()) {
                notificacionHelper.setMostrarDestinatarios(Boolean.TRUE);
            } else {
                notificacionHelper.setMostrarDestinatarios(Boolean.FALSE);
            }
            if (notificacionHelper.getDestinatarioNotificacion() != null
                    && notificacionHelper.getDestinatarioNotificacion().getEstado()
                    .equals(EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo())) {
                iniciarDatosEntidad(notificacionHelper.getDestinatarioNotificacion(), Boolean.FALSE);
                notificacionServicio.marcarNotificacionComoLeida(notificacionHelper.getDestinatarioNotificacion());
            } else if (!notificacionHelper.getMostrarRecibidas()) {
                if (!notificacionHelper.getNotificacion().getEnviadaATodos()) {
                    ejecutarComandoPrimefaces("tblDestinatariosWV.clearFilters()");
                } else {
                    notificacionHelper.getNotificacion().getDestinatarios().clear();
                }
            }

            ejecutarComandoPrimefaces("dlgNotificacion.show()");
            ejecutarComandoPrimefaces("actualizarBtnNotificaciones();");

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar leer la notificación", e);
        }
        return null;
    }

    /**
     * Envía la notificación
     *
     * @return
     */
    public String enviarNotificacion() {
        try {
            notificacionHelper.getNotificacion().setEnviadaATodos(Boolean.FALSE);
            List<DestinatarioNotificacion> listaDestinatarios = new ArrayList<>();
            notificacionHelper.getMapDestinatariosIds().clear();
            DestinatarioNotificacion dn;
            if (notificacionHelper.getOpcionTipoRemitenteSeleccionada().equals(
                    OpcionDestinatarioNotificacionEnum.UNIDAD_DESCONCENTRADA.getCodigo())) {
                for (Desconcentrado d : notificacionHelper.getUnidadesDesconcetradasSeleccionadas()) {
                    if (!notificacionHelper.getMapDestinatariosIds().containsKey(d.getServidorResponsable().getId())) {
                        dn = construirDestinatarioNotificacion();
                        dn.setDestinatario(d.getServidorResponsable());
                        listaDestinatarios.add(dn);
                        notificacionHelper.getMapDestinatariosIds().put(d.getServidorResponsable().getId(), null);
                    }
                    for (DesconcentradoApoyo da : d.getListaApoyos()) {
                        if (!notificacionHelper.getMapDestinatariosIds().containsKey(da.getServidorApoyo().getId())) {
                            DestinatarioNotificacion dna = construirDestinatarioNotificacion();
                            dna.setDestinatario(da.getServidorApoyo());
                            listaDestinatarios.add(dna);
                            notificacionHelper.getMapDestinatariosIds().put(da.getServidorApoyo().getId(), null);
                        }
                    }
                }

                //busca los undiadesDesconcentradas que pertenencen a unidades organizacionales de recursos humanos
            } else if (notificacionHelper.getOpcionTipoRemitenteSeleccionada().equals(
                    OpcionDestinatarioNotificacionEnum.UNIDAD_RRHH.getCodigo())) {
                recuperarUnidesRRHH();
                for (UnidadOrganizacional uo : notificacionHelper.getListaUnidadesRHH()) {
                    BusquedaServidorVO bsVo = new BusquedaServidorVO();
                    bsVo.setUnidadAdministrativaId(uo.getId());
                    List<DistributivoDetalle> distributivosRRHH = servidorServicio.buscar(bsVo);
                    for (DistributivoDetalle dd : distributivosRRHH) {
                        if (dd.getServidor() != null
                                && !notificacionHelper.getMapDestinatariosIds().containsKey(dd.getServidor().getId())) {
                            DestinatarioNotificacion dna = construirDestinatarioNotificacion();
                            dna.setDestinatario(dd.getServidor());
                            listaDestinatarios.add(dna);
                            notificacionHelper.getMapDestinatariosIds().put(dd.getServidor().getId(), null);
                        }
                    }
                }

                //agrega los undiadesDesconcentradas que fueron seleccionados 
            } else if (notificacionHelper.getOpcionTipoRemitenteSeleccionada().equals(
                    OpcionDestinatarioNotificacionEnum.SERVIDORES_ESPECIFICOS.getCodigo())) {
                for (Servidor s : notificacionHelper.getListaDestinatarios()) {
                    DestinatarioNotificacion dntf = construirDestinatarioNotificacion();
                    dntf.setDestinatario(s);
                    listaDestinatarios.add(dntf);
                }

                //Indica que se debe enviar a todos los undiadesDesconcentradas
            } else {
                notificacionHelper.getNotificacion().setEnviadaATodos(Boolean.TRUE);
            }

            notificacionServicio.guardarYEnviarNotificacion(
                    notificacionHelper.getNotificacion(), listaDestinatarios, obtenerUsuarioConectado());
            mostrarMensajeEnPantalla("Notificación enviada satisfactoriamente", FacesMessage.SEVERITY_INFO);
            ejecutarComandoPrimefaces("dlgNotificacion.hide(); actualizarBtnNotificaciones();");
            notificacionHelper.setMostrarRecibidas(Boolean.FALSE);
            buscar();

        } catch (Exception e) {
            error(getClass().getName(), "Error al intentar enviar la notificación", e);
        }
        return BANDEJA_NOTIFICACIONES;
    }

    /**
     * Construye un objete de tipo DestinatarioNotificacion
     *
     * @return
     */
    private DestinatarioNotificacion construirDestinatarioNotificacion() {
        DestinatarioNotificacion dn = new DestinatarioNotificacion();
        iniciarDatosEntidad(dn, Boolean.TRUE);
        dn.setEstado(EstadoNotificacionDestinatarioEnum.NO_LEIDO.getCodigo());
        return dn;
    }

    /**
     * recupera los undiadesDesconcentradas que estén activos y ocupando un puesto dado un nombre
     *
     * @param nombre nombre del servidor
     * @return
     */
    public List<Servidor> buscarServidoresActivosEnPuestosOcupadosPorNombre(String nombre) {
        List<Servidor> servidores = new ArrayList();
        try {
            if (nombre != null && !nombre.trim().isEmpty()) {
                ParametroInstitucional pi
                        = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                                getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());

                BusquedaPuestoVO b = new BusquedaPuestoVO();
                b.setNombreServidor(nombre);
                b.setInicioConsulta(0);
                b.setFinConsulta(10);
                b.setPuestoVacante(Boolean.FALSE);
                b.setEstadoPuestoCodigo(EstadoPuestoEnum.OCUPADO.getCodigo());
                b.setEstadoServidorCodigo(EstadosPersonalEnum.ACTIVO.getCodigo());
                List<DistributivoDetalle> puestos = distributivoPuestoServicio.buscar(
                        b, false, obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()));

                for (DistributivoDetalle puesto : puestos) {
                    servidores.add(puesto.getServidor());
                }

                if (servidores.isEmpty()) {
                    Servidor s = new Servidor(0L);
                    s.setApellidosNombres("No se encontraron resultados...");
                    servidores.add(s);
                }

                List<Servidor> servidoresEnSession = new ArrayList<>();
                servidoresEnSession.addAll(notificacionHelper.getListaDestinatarios());
                if (!servidores.get(0).getId().equals(0L)) {
                    servidoresEnSession.addAll(servidores);
                }
                ponerAtributoSession(ServidorConverter.CLAVE_SESSION, servidoresEnSession);
            }

        } catch (DaoException | ServicioException e) {
            error(getClass().getName(), "Error al buscar servidores", e);
        }

        return servidores;
    }

    /**
     * recupera las unidades desconcentradas vigentes
     *
     * @param nombreUnidad nombre de la unidad descontrada
     * @return
     */
    public List<Desconcentrado> buscarUnidadesDesconcentradasPorNombre(String nombreUnidad) {
        try {
            if (nombreUnidad != null && !nombreUnidad.trim().isEmpty()) {
                List<Desconcentrado> unidadesDesconcentradas
                        = desconcentradoServicio.listarDesconcentradosPorNombre(nombreUnidad);
                if (unidadesDesconcentradas.isEmpty()) {
                    Desconcentrado d = new Desconcentrado(0L);
                    d.setNombre("No se encontraron resultados...");
                    unidadesDesconcentradas.add(d);
                }

                List<Desconcentrado> unidadesEnSession = new ArrayList<>();
                unidadesEnSession.addAll(notificacionHelper.getUnidadesDesconcetradasSeleccionadas());
                if (!unidadesDesconcentradas.get(0).getId().equals(0L)) {
                    unidadesEnSession.addAll(unidadesDesconcentradas);
                }
                ponerAtributoSession(DesconcentradoConverter.CLAVE_SESSION, unidadesEnSession);
                return unidadesDesconcentradas;
            }

        } catch (Exception e) {
            error(getClass().getName(), "Error al buscar unidades desconcentradas", e);
        }

        return new ArrayList<>();
    }

    /**
     * concatena el nombre del servidor con su numero de identificación para mostrar en pantalla
     *
     * @param s servidor
     * @return
     */
    public String nombreIdentificacionString(Servidor s) {
        StringBuilder nombreIdentificacion = new StringBuilder("");
        if (s != null) {
            nombreIdentificacion.append(s.getApellidosNombres());
            if (s.getId() != 0L) {
                nombreIdentificacion.append(" (").append(s.getNumeroIdentificacion()).append(")");
            }
        }
        return nombreIdentificacion.toString();
    }

    /**
     * verifica si el serevidor seleccionado como destinatario no haya sido ya seleccionado
     *
     * @param e evento disparado en la vista
     */
    public void verificarDestinatarioNoEsteSeleccionado(SelectEvent e) {
        Servidor serv = (Servidor) e.getObject();
        int l = notificacionHelper.getListaDestinatarios().size();
        ListIterator<Servidor> li = notificacionHelper.getListaDestinatarios().listIterator(l - 1);
        while (li.hasPrevious()) {
            Servidor s = li.previous();
            if (serv.getId().equals(s.getId())) {
                li.next();
                li.remove();
                mostrarMensajeEnPantalla(
                        "El servidor seleccionado ya está incluído en la lista de destinatarios.",
                        FacesMessage.SEVERITY_ERROR);
                break;
            }
        }
    }

    /**
     * verifica si una unidad desconcentrada no haya sido ya seleccionada
     *
     * @param e evento disparado en la vista
     */
    public void verificarUnidadDesconcetradaNoEsteSeleccionada(SelectEvent e) {
        Desconcentrado unidad = (Desconcentrado) e.getObject();
        int l = notificacionHelper.getUnidadesDesconcetradasSeleccionadas().size();
        ListIterator<Desconcentrado> li = notificacionHelper.getUnidadesDesconcetradasSeleccionadas().listIterator(l - 1);
        while (li.hasPrevious()) {
            Desconcentrado ud = li.previous();
            if (unidad.getId().equals(ud.getId())) {
                li.next();
                li.remove();
                mostrarMensajeEnPantalla(
                        "La Unidad Desconcentrada seleccionada ya está incluída en la lista de destinatarios.",
                        FacesMessage.SEVERITY_ERROR);
                break;
            }
        }
    }

    /**
     * Devuelve la descripción del estado de la notificacion dado su código
     *
     * @param codigoEstado
     * @return
     */
    public String obtenerDescripcionEstadoNotificacion(String codigoEstado) {
        return EstadoNotificacionDestinatarioEnum.obtenerDescripcion(codigoEstado);
    }

    /**
     * Verificar si la notificacion esta leida o no
     *
     * @param codigoEstado
     * @return
     */
    public Boolean notificacionleida(String codigoEstado) {
        return codigoEstado.equals(EstadoNotificacionDestinatarioEnum.LEIDO.getCodigo());
    }

    /**
     * Recuepera el color asociado a un estado de lectura de notificacion para mostrar en pantalla
     *
     * @param estado
     * @return
     */
    public String obtenerEstiloEstado(String estado) {
        return EstadoNotificacionDestinatarioEnum.obtenerColor(estado);
    }

    /**
     * regresa a la lista de undiades desconcentradas
     *
     * @return
     */
    public String regresar() {
        return BANDEJA_NOTIFICACIONES;
    }

    /**
     *
     * @return
     */
    public NotificacionHelper getNotificacionHelper() {
        return notificacionHelper;
    }

    /**
     *
     * @param notificacionHelper
     */
    public void setNotificacionHelper(NotificacionHelper notificacionHelper) {
        this.notificacionHelper = notificacionHelper;
    }

    /**
     *
     * @return
     */
    @Override
    public String guardar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return
     */
    @Override
    public String borrar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
