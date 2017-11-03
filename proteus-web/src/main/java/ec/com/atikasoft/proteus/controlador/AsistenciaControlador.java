/*
 *  AsistenciaControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  29/10/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.AsistenciaHelper;
import ec.com.atikasoft.proteus.controlador.helper.JustificacionAsistenciaHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AsistenciaServicio;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.MenuVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * AsistenciaControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "asistenciaBean")
@ViewScoped
public class AsistenciaControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(AsistenciaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/asistencia/marcaciones.jsf";
    
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_BUSCAR = "/pages/procesos/asistencia/buscar_servidor_asistencia.jsf";
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_JUSTIFICACION = "/pages/procesos/asistencia/justificacion_asistencia.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PAGINA_INDEX = "/pages/portal_rrhh.jsf";

    /**
     * Servicio de asistencia.
     */
    @EJB
    private AsistenciaServicio asistenciaServicio;
    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     * Servicio de menus
     */
    @EJB
    private MenuServicio menuServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{asistenciaHelper}")
    private AsistenciaHelper asistenciaHelper;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{justificacionAsistenciaHelper}")
    private JustificacionAsistenciaHelper justificacaionAsistenciaHelper;

    @Override
    @PostConstruct
    public void init() {
        asistenciaHelper.setNombreServidor("");
        asistenciaHelper.setNumeroIdentificacion("");
        asistenciaHelper.getListaServidores().clear();
        asistenciaHelper.setFechaDesde(null);
        asistenciaHelper.setFechaHasta(null);
        verificarPermisoMenuDelUsuario();
    }

    /**
     *
     * @return
     */
    public String guardar() {
        try {
            if (asistenciaHelper.getAsistencia().getServidor().getId().equals(
                    obtenerUsuarioConectado().getServidor().getId())) {
                mostrarMensajeEnPantalla("No es posible Actualizar sus propios registros", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            
            if (asistenciaHelper.getAsistencia().getObservacion() == null || asistenciaHelper.getAsistencia().getObservacion().isEmpty()) {
                mostrarMensajeEnPantalla("El campo motivo es requerido", FacesMessage.SEVERITY_ERROR);
                return FORMULARIO_ENTIDAD;
            }
            if (asistenciaHelper.gethEntrada() == null || asistenciaHelper.gethSalida() == null) {
                mostrarMensajeEnPantalla("La hora de Entrada y hora de Salida son campos requeridos", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethInicioAlmuerzo() != null && asistenciaHelper.gethEntrada().compareTo(asistenciaHelper.gethInicioAlmuerzo()) >= 0) {
                mostrarMensajeEnPantalla("La hora de Entrada debe ser menor a la hora de Inicio Almuerzo", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethFinAlmuerzo() != null && asistenciaHelper.gethEntrada().compareTo(asistenciaHelper.gethFinAlmuerzo()) >= 0) {
                mostrarMensajeEnPantalla("La hora de Entrada debe ser menor a la hora Fin de Almuerzo", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethFinAlmuerzo() != null && asistenciaHelper.gethEntrada().compareTo(asistenciaHelper.gethFinAlmuerzo()) >= 0) {
                mostrarMensajeEnPantalla("La hora de Entrada debe ser menor a la hora de Salida", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethInicioAlmuerzo() != null && asistenciaHelper.gethFinAlmuerzo() != null
                    && asistenciaHelper.gethInicioAlmuerzo().compareTo(asistenciaHelper.gethFinAlmuerzo()) >= 0) {
                mostrarMensajeEnPantalla("La hora de Inicio de Almuerzo debe ser menor a la hora Fin de Almuerzo", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethInicioAlmuerzo() != null && asistenciaHelper.gethFinAlmuerzo() != null
                    && asistenciaHelper.gethInicioAlmuerzo().compareTo(asistenciaHelper.gethFinAlmuerzo()) >= 0) {
                mostrarMensajeEnPantalla("La hora de Inicio de Almuerzo debe ser menor a la hora de Salida", FacesMessage.SEVERITY_ERROR);
            } else if (asistenciaHelper.gethFinAlmuerzo() != null
                    && asistenciaHelper.gethFinAlmuerzo().compareTo(asistenciaHelper.gethSalida()) >= 0) {
                mostrarMensajeEnPantalla("La hora Fin de Almuerzo debe ser menor a la hora de Salida", FacesMessage.SEVERITY_ERROR);
            } else {
                asistenciaHelper.getAsistencia().setTimbre1(UtilFechas.formatearEnTiempo(asistenciaHelper.gethEntrada()).toString());

                if (asistenciaHelper.gethInicioAlmuerzo() != null && asistenciaHelper.gethFinAlmuerzo() != null) {
                    asistenciaHelper.getAsistencia().setTimbre2(UtilFechas.formatearEnTiempo(asistenciaHelper.gethInicioAlmuerzo()).toString());
                    asistenciaHelper.getAsistencia().setTimbre3(UtilFechas.formatearEnTiempo(asistenciaHelper.gethFinAlmuerzo()).toString());
                    asistenciaHelper.getAsistencia().setTimbre4(UtilFechas.formatearEnTiempo(asistenciaHelper.gethSalida()).toString());
                } else if (asistenciaHelper.gethInicioAlmuerzo() != null && asistenciaHelper.gethFinAlmuerzo() == null) {
                    asistenciaHelper.getAsistencia().setTimbre2(UtilFechas.formatearEnTiempo(asistenciaHelper.gethInicioAlmuerzo()).toString());
                    asistenciaHelper.getAsistencia().setTimbre3(UtilFechas.formatearEnTiempo(asistenciaHelper.gethSalida()).toString());
                    asistenciaHelper.getAsistencia().setTimbre4(null);
                } else if (asistenciaHelper.gethInicioAlmuerzo() == null && asistenciaHelper.gethFinAlmuerzo() != null) {
                    asistenciaHelper.getAsistencia().setTimbre2(UtilFechas.formatearEnTiempo(asistenciaHelper.gethFinAlmuerzo()).toString());
                    asistenciaHelper.getAsistencia().setTimbre3(UtilFechas.formatearEnTiempo(asistenciaHelper.gethSalida()).toString());
                    asistenciaHelper.getAsistencia().setTimbre4(null);
                } else if (asistenciaHelper.gethInicioAlmuerzo() == null && asistenciaHelper.gethFinAlmuerzo() == null) {
                    asistenciaHelper.getAsistencia().setTimbre2(UtilFechas.formatearEnTiempo(asistenciaHelper.gethSalida()).toString());
                    asistenciaHelper.getAsistencia().setTimbre3(null);
                    asistenciaHelper.getAsistencia().setTimbre4(null);
                }

                asistenciaHelper.getAsistencia().setTimbre5(null);
                asistenciaHelper.getAsistencia().setTimbre6(null);
                asistenciaHelper.getAsistencia().setTimbre7(null);
                asistenciaHelper.getAsistencia().setTimbre8(null);
                asistenciaHelper.getAsistencia().setTimbre9(null);
                asistenciaHelper.getAsistencia().setTimbre10(null);
                iniciarDatosEntidad(asistenciaHelper.getAsistencia(), Boolean.FALSE);
                asistenciaServicio.actualizarAsistencia(asistenciaHelper.getAsistencia(), obtenerUsuarioConectado().getInstitucion().getId());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     *
     * @param s
     * @param esEdicion
     * @return
     */
    public String iniciarNuevo(Servidor s, boolean esEdicion) {        
        asistenciaHelper.setServidor(s);
        if (asistenciaHelper.getServidor() != null && asistenciaHelper.getServidor().getId() != null) {
            
            asistenciaHelper.getListaAsistencia().clear();
            asistenciaHelper.sethEntrada(null);
            asistenciaHelper.sethInicioAlmuerzo(null);
            asistenciaHelper.sethFinAlmuerzo(null);
            asistenciaHelper.sethSalida(null);
            asistenciaHelper.setEstaAtrasoJustificado(Boolean.FALSE);
            asistenciaHelper.setEstaEnNomina(Boolean.FALSE);
            if (asistenciaHelper.getAsistencia() == null) {
                asistenciaHelper.setAsistencia(new Asistencia());
            }
             asistenciaHelper.getAsistencia().setServidor(asistenciaHelper.getServidor());
            asistenciaHelper.getAsistencia().setObservacion(null);
            asistenciaHelper.getAsistencia().setFecha(null);
            asistenciaHelper.setFechaDesde(null);
            asistenciaHelper.setFechaHasta(null);            
            if (!esEdicion) {
                ejecutarComandoPrimefaces("detDialog.show();");
                return null;
            }else{
                return FORMULARIO_ENTIDAD;
            }
        }
        return null;
    }

    /**
     *
     * @param a
     */
    private void iniciarTimbradas(Asistencia a) {
        try {

            asistenciaHelper.sethEntrada(null);
            asistenciaHelper.sethInicioAlmuerzo(null);
            asistenciaHelper.sethFinAlmuerzo(null);
            asistenciaHelper.sethSalida(null);
            if (a.getTimbre1() != null) {
                int posUltimoTimbre = asistenciaServicio.obtenerUltimoTimbre(a) - 1;
                switch (posUltimoTimbre) {
                    case 0:
                        asistenciaHelper.sethEntrada(a.getHoraTimbre(a.getTimbre1()));
                        break;
                    case 1:
                        asistenciaHelper.sethEntrada(a.getHoraTimbre(a.getTimbre1()));
                        asistenciaHelper.sethSalida(a.getHoraTimbre(a.getTimbre2()));
                        break;
                    case 2:
                        asistenciaHelper.sethEntrada(a.getHoraTimbre(a.getTimbre1()));
                        asistenciaHelper.sethInicioAlmuerzo(a.getHoraTimbre(a.getTimbre2()));
                        asistenciaHelper.sethSalida(a.getHoraTimbre(a.getTimbre3()));
                        break;
                    case 3:
                        asistenciaHelper.sethEntrada(a.getHoraTimbre(a.getTimbre1()));
                        asistenciaHelper.sethInicioAlmuerzo(a.getHoraTimbre(a.getTimbre2()));
                        asistenciaHelper.sethFinAlmuerzo(a.getHoraTimbre(a.getTimbre3()));
                        asistenciaHelper.sethSalida(a.getHoraTimbre(a.getTimbre4()));
                        break;
                    case 4:
                        asistenciaHelper.sethEntrada(a.getHoraTimbre(a.getTimbre1()));
                        asistenciaHelper.sethInicioAlmuerzo(a.getHoraTimbre(a.getTimbre2()));
                        asistenciaHelper.sethFinAlmuerzo(a.getHoraTimbre(a.getTimbre3()));
                        asistenciaHelper.sethSalida(a.getTimbres(a)[posUltimoTimbre]);
                    case 9:
                        break;

                }
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla("Problemas en la asignacion", FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error en la asignacion", e);
        }
    }
/**
 * 
 */
    private void verificarPermisoMenuDelUsuario() {
        try {
            justificacaionAsistenciaHelper.setAccesoAJustificacion(Boolean.FALSE);
             justificacaionAsistenciaHelper.setAccesoAEdicion(Boolean.FALSE);
            MenuVO vo = new MenuVO();
            vo.setTipo("I");
            vo.setServidorId(obtenerUsuarioConectado().getServidor().getId());
            List<Menu> listaMenu = menuServicio.listarTodosMenus(vo);

            for (Menu m : listaMenu) {
                if (m.getUrl().equals(FORMULARIO_JUSTIFICACION)) {
                    justificacaionAsistenciaHelper.setAccesoAJustificacion(Boolean.TRUE);
                    break;
                }
            }
            for (Menu m : listaMenu) {
                if (m.getUrl().equals(FORMULARIO_ENTIDAD)) {
                    justificacaionAsistenciaHelper.setAccesoAEdicion(Boolean.TRUE);
                    break;
                }
            }
        } catch (ServicioException ex) {
            Logger.getLogger(AsistenciaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Busca todos los registros en estado registrado deben mostrarse unicamente
     * para el rol Aprobador de vacaciones
     *
     * @param idCodigo
     * @return
     */
    public String buscar(final Long idCodigo) {
        try {

            asistenciaHelper.sethEntrada(null);
            asistenciaHelper.sethInicioAlmuerzo(null);
            asistenciaHelper.sethFinAlmuerzo(null);
            asistenciaHelper.sethSalida(null);
            asistenciaHelper.setEstaAtrasoJustificado(Boolean.FALSE);
            asistenciaHelper.setEstaEnNomina(Boolean.FALSE);

            if (idCodigo != null && asistenciaHelper.getAsistencia().getFecha() != null) {
                List<Asistencia> lista
                        = asistenciaServicio.listarAsistenciaPorFechaYEmpleado(idCodigo, asistenciaHelper.getAsistencia().getFecha());
                if (!lista.isEmpty()) {
                    asistenciaHelper.setAsistencia(lista.get(0));
                    iniciarTimbradas(asistenciaHelper.getAsistencia());
                    iniciarDatosEntidad(asistenciaHelper.getAsistencia(), Boolean.FALSE);
                    asistenciaHelper.setEstaEnNomina(estaFechaEnNomina());
                    asistenciaHelper.setEstaAtrasoJustificado(estaAtrasoJustificado());
                }else{
                     mostrarMensajeEnPantalla("La fecha seleccionada no se encuentra registrada en el biométrico", FacesMessage.SEVERITY_ERROR);
                }

            } else {
                mostrarMensajeEnPantalla("La fecha es campo requerido", FacesMessage.SEVERITY_ERROR);
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return null;
    }

    /**
     *
     * @param idServidor
     * @return
     */
    private boolean estaFechaEnNomina() {
        try {
            AsistenciaProceso a = asistenciaServicio.listarAsistenciaProcesosPorFecha(asistenciaHelper.getAsistencia().getFecha());
            if (a != null && a.getNomina() != null) {
                return true;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de fechas que estan en nomina", ex);
        }
        return false;
    }

    /**
     *
     * @param idServidor
     * @return
     */
    private boolean estaAtrasoJustificado() {
        try {
            List<JustificacionAsistencia> a = asistenciaServicio.listarJustificacionAsistenciaPorServidorFecha(asistenciaHelper.getServidor().getId(), asistenciaHelper.getAsistencia().getFecha());
            if (a != null && !a.isEmpty()) {
                return true;
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de fechas que estan justificaciones", ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String buscarDetalles() {
        try {
            asistenciaHelper.getListaAsistencia().clear();
            asistenciaHelper.setListaAsistencia(
                    asistenciaServicio.listarAsistenciaPorServidorYRangoFecha(asistenciaHelper.getAsistencia().getServidor().getId(),
                            asistenciaHelper.getFechaDesde(), asistenciaHelper.getFechaHasta()));
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de fechas que estan en nomina", ex);
        }
        return null;
    }

    /**
     * metodo que busca el servidor por nombre y/o número de identificacion.
     *
     * @return
     */
    public String buscarServidor() {
        try {

            if (asistenciaHelper.getNombreServidor().length() < 3 && !asistenciaHelper.getNombreServidor().isEmpty()) {
                asistenciaHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (asistenciaHelper.getNumeroIdentificacion().length() < 3 && !asistenciaHelper.getNumeroIdentificacion().isEmpty()) {
                asistenciaHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (asistenciaHelper.getNombreServidor().isEmpty() && asistenciaHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (!asistenciaHelper.getNombreServidor().trim().isEmpty()) {
                asistenciaHelper.setNombreServidor(asistenciaHelper.getNombreServidor().toUpperCase());
            }
            asistenciaHelper.getListaAsistencia().clear();
            asistenciaHelper.getListaAsistencia().clear();
            asistenciaHelper.getListaServidores().clear();
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(asistenciaHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(asistenciaHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            ser.setCodUnidadAdministrativa(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());

            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            for (DistributivoDetalle s : lista) {
                if (s.getVigente()) {
                    asistenciaHelper.getListaServidores().add(s.getServidor());
                }
            }
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores para asistencias:{0}", asistenciaHelper.getListaServidores().size());

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de servidores", ex);
        }
        return null;
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * documento parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }
  /**
     * Permite Regresar
     *
     * @return
     */
    public String irBuscar() {
        return FORMULARIO_BUSCAR;
    }
    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_INDEX;
    }

    /**
     * @return the asistenciaHelper
     */
    public AsistenciaHelper getAsistenciaHelper() {
        return asistenciaHelper;
    }

    /**
     * @param asistenciaHelper the asistenciaHelper to set
     */
    public void setAsistenciaHelper(AsistenciaHelper asistenciaHelper) {
        this.asistenciaHelper = asistenciaHelper;
    }

    /**
     * @return the justificacaionAsistenciaHelper
     */
    public JustificacionAsistenciaHelper getJustificacaionAsistenciaHelper() {
        return justificacaionAsistenciaHelper;
    }

    /**
     * @param justificacaionAsistenciaHelper the justificacaionAsistenciaHelper
     * to set
     */
    public void setJustificacaionAsistenciaHelper(JustificacionAsistenciaHelper justificacaionAsistenciaHelper) {
        this.justificacaionAsistenciaHelper = justificacaionAsistenciaHelper;
    }
}
