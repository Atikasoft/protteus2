/*
 *  ReasignacionTramiteControlador.java
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
 *  03/01/2013
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ReasignacionTramiteHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.RolServidorDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.ReasignacionTarea;
import ec.com.atikasoft.proteus.modelo.Rol;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.Tarea;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.MenuServicio;
import ec.com.atikasoft.proteus.servicio.SeguridadServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicioSMP;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.vo.TareaVO;
import ec.com.atikasoft.proteus.vo.UsuarioRolVO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
@ManagedBean(name = "reasignacionTramiteBean")
@ViewScoped
public class ReasignacionTramiteControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private final static Logger LOG = Logger.getLogger(DocumentoHabilitanteControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    private final static String FORMULARIO_ENTIDAD = "/pages/procesos/reasignacion_tramite/reasignacion_tramite.jsf";

    /**
     * Regla de navegación.
     */
    private final static String LISTA_ENTIDAD = "/pages/procesos/reasignacion_tramite/lista_reasignacion_tramite.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{reasignacionTramiteHelper}")
    private ReasignacionTramiteHelper reasignacionTramiteHelper;

    /**
     * Servicio de tarea.
     */
    @EJB
    private TareaServicioSMP tareaServicioSMP;

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     *
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
    private DesconcentradoServicio desconcentradoServicio;

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    /**
     * Este método reasigna el tramite.
     *
     * @return String
     */
    public String guardar() {
        try {
            if (reasignacionTramiteHelper.getSelectedUsuarioRolVO() != null) {
                Object tarea
                        = BeanUtils.cloneBean(getReasignacionTramiteHelper().getTarea());
                Tarea t = (Tarea) tarea;
                ReasignacionTarea rt = new ReasignacionTarea();
                rt.setFechaAsignacion(new Date());
                rt.setTarea(t);
                rt.setMotivo(reasignacionTramiteHelper.getMotivo());
                rt.setUsuarioAsignadoAnterior(t.getUsuarioAsignado());
                rt.setUsuarioReAsignado(reasignacionTramiteHelper.getSelectedUsuarioRolVO().getServidor().getNumeroIdentificacion());
                rt.setUsuarioCreacion(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                rt.setFechaCreacion(new Date());
                rt.setVigente(Boolean.TRUE);
                t.setUsuarioAsignado(reasignacionTramiteHelper.getSelectedUsuarioRolVO().getServidor().getNumeroIdentificacion());
                t.setNombreUsuarioAsignado(reasignacionTramiteHelper.getSelectedUsuarioRolVO().getServidor().getApellidosNombres());
                t.setNemonicoRol(reasignacionTramiteHelper.getSelectedUsuarioRolVO().getCodigoRol());
                t.setFechaAsignacion(new Date());
                tramiteServicio.reasignarTareaDelTramite(t, rt);
                ponerMensajeInfo(TAREA_REASIGNADA, " " + t.getNombreUsuarioAsignado());
                //reglaNavegacionDirecta(LISTA_ENTIDAD);
            } else {
                mostrarMensajeEnPantalla(SIN_SERVIDOR, FacesMessage.SEVERITY_WARN);
            }
        } catch (Exception e) {
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Este metodo buscara las tareas segun los parametros ingresados.
     *
     * @return String
     */
    public String buscar() {
        try {
            ParametroInstitucional pi
                    = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                            getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            getReasignacionTramiteHelper().getListaTareas().clear();
            Integer ejercicioFiscal = Integer.valueOf(obtenerUsuarioConectado().getEjercicioFiscal().getNombre());
            List<TareaVO> tareas = tareaServicioSMP.buscarTareasTodosUsuarios(esRRHH(pi.getValorTexto()),
                    obtenerUsuarioConectado(), ejercicioFiscal, Boolean.TRUE);
            getReasignacionTramiteHelper().setListaTareas(tareas);
            getReasignacionTramiteHelper().getListaTareasFiltradas().clear();
            getReasignacionTramiteHelper().getListaTareasFiltradas().addAll(tareas);

        } catch (Exception ex) {
            Logger.getLogger(ReasignacionTramiteControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return LISTA_ENTIDAD;
    }

    /**
     * Método para reasignar el tramite.
     *
     * @return String
     */
    public String reasignarTramite() {
        try {
            reasignacionTramiteHelper.setSelectedUsuarioRolVO(null);
            reasignacionTramiteHelper.getListaUsuariosRolVO().clear();
            Object tarea = BeanUtils.cloneBean(getReasignacionTramiteHelper().getTarea());
            Tarea t = (Tarea) tarea;
            getReasignacionTramiteHelper().setTarea(t);
            Tramite tramite = tramiteServicio.buscarTramite(t.getIdentificadorExterno());
            getReasignacionTramiteHelper().setTramite(tramite);

            List<Servidor> listaUsuarioRol = desconcentradoServicio.buscarServidoresResposablesYApoyosSegunRol(
                    obtenerUsuarioConectado().getServidor().getId(), t.getNemonicoRol());
            for (Servidor ur : listaUsuarioRol) {
                if (!ur.getNumeroIdentificacion().equals(t.getUsuarioAsignado())) {
                    Long x = 0l;
//                    x = tareaServicio.contar(ur.getNumeroIdentificacion(), obtenerUsuarioConectado().
//                            getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(), Boolean.TRUE);
                    UsuarioRolVO urVO = new UsuarioRolVO();
                    urVO.setNumeroAsignaciones(x);
                    urVO.setServidor(ur);
                    urVO.setNombresServidor(ur.getApellidosNombres());
                    urVO.setCodigoRol(t.getNemonicoRol());
                    urVO.setNumeroIdentificacion(ur.getNumeroIdentificacion());
                    List<Rol> roles = menuServicio.listarTodosRolPorCodigo(t.getNemonicoRol());
                    if (roles != null && !roles.isEmpty()) {
                        urVO.setRol(roles.get(0));
                        urVO.setNombreRol(urVO.getRol().getNombre());
                    }
                    reasignacionTramiteHelper.getListaUsuariosRolVO().add(urVO);
                }
            }
            reasignacionTramiteHelper.setMotivo("");
            reglaNavegacionDirecta(FORMULARIO_ENTIDAD);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la reasignación del tramite.", ex);
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * Metodo que se encarga de dar el formato para la busqueda de la columna de fecha.
     *
     * @param date Fecha
     * @return String fecha encontrada
     */
    public String localizedDateRep(final Date date) {
        String retorno = "";
        if (date != null) {
            retorno = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
        }
        return retorno;
    }

    /**
     * metodo de regresar en reasignación.
     *
     * @return String
     */
    public String regresarReasignacion() {
        return LISTA_ENTIDAD;
    }

    /**
     * @return the reasignacionTramiteHelper
     */
    public ReasignacionTramiteHelper getReasignacionTramiteHelper() {
        return reasignacionTramiteHelper;
    }

    /**
     * @param reasignacionTramiteHelper the reasignacionTramiteHelper to set
     */
    public void setReasignacionTramiteHelper(final ReasignacionTramiteHelper reasignacionTramiteHelper) {
        this.reasignacionTramiteHelper = reasignacionTramiteHelper;
    }
}
