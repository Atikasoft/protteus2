/*
 *  ReasignacionTramiteHelper.java
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
import ec.com.atikasoft.proteus.controlador.helper.DelegacionAprobacionHelper;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDelegado;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoDescentralizado;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.vo.TipoMovimientoDelegadoVO;
import ec.com.atikasoft.proteus.vo.UsuarioRolVO;
import java.util.ArrayList;
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
@ManagedBean(name = "delegacionAprobacionBean")
@ViewScoped
public class DelegacionAprobacionControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DocumentoHabilitanteControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    private static final String FORMULARIO_ENTIDAD =
            "/pages/administracion/delegacion_aprobacion/delegacion_aprobacion.jsf";

    /**
     * Regla de navegación.
     */
    private static final String LISTA_ENTIDAD =
            "/pages/administracion/delegacion_aprobacion/lista_delegacion_aprobacion.jsf";

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{delegacionAprobacionHelper}")
    private DelegacionAprobacionHelper delegacionAprobacionHelper;

    /**
     * Servicio de tarea.
     */
    @EJB
    private AdministracionServicio administracionServicio;

    @Override
    @PostConstruct
    public void init() {
        delegacionAprobacionHelper.getListaTipoMovimientoDelegadoVO().clear();
        buscarTipoMovimientos();
    }

    /**
     * método para buscar los tipos de movimientos.
     */
    private void buscarTipoMovimientos() {
        try {
//            Long codigoInstitucion = obtenerUsuarioLogeado().getInstitucion().getId();
//            Institucion i = institucionServicio.obtenerIdMatriz(codigoInstitucion);
//            if (codigoInstitucion.equals(i.getId())) {
//                List<TipoMovimiento> lista = administracionServicio.listarTiposMovimientosOrdenados();
//                for (TipoMovimiento tm : lista) {
//                    TipoMovimientoDelegadoVO tmdVO = new TipoMovimientoDelegadoVO();
//                    tmdVO.setTipoMovimiento(tm);
//                    tmdVO.getListaTipoMovimientoDelegado().clear();
//                    tmdVO.setListaTipoMovimientoDelegado(
//                            administracionServicio.listarTiposMovimientosDelegados(tm, obtenerInstitucion().getId()));
//                    delegacionAprobacionHelper.getListaTipoMovimientoDelegadoVO().add(tmdVO);
//                }
//            } else {
//                List<TipoMovimientoDescentralizado> listaDes = administracionServicio.
//                        buscarTipoMovimientoDescentralizadoPorInstitucion(codigoInstitucion);
//                for (TipoMovimientoDescentralizado tmd : listaDes) {
//                    TipoMovimientoDelegadoVO tmdVOD = new TipoMovimientoDelegadoVO();
//                    tmdVOD.setTipoMovimiento(tmd.getTipoMovimiento());
//                    tmdVOD.getListaTipoMovimientoDelegado().clear();
//                    tmdVOD.setListaTipoMovimientoDelegado(
//                            administracionServicio.listarTiposMovimientosDelegados(tmd.getTipoMovimiento(),
//                            obtenerInstitucion().getId()));
//                    delegacionAprobacionHelper.getListaTipoMovimientoDelegadoVO().add(tmdVOD);
//                }
//            }
        } catch (Exception ex) {
            Logger.getLogger(ClaseControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * método de regresar en delegación.
     *
     * @return String
     */
    public String regresarDelegacion() {
        return LISTA_ENTIDAD;
    }

    /**
     * Método para comenzar la delegación.
     *
     * @return String
     */
    public String comenzarDelegacion() {
        try {
            delegacionAprobacionHelper.setSelectedUsuarioRolVO(null);
            delegacionAprobacionHelper.getListaUsuariosRolVO().clear();
            Object tipoMov =
                    BeanUtils.cloneBean(delegacionAprobacionHelper.getTipoMovimientoDelegadoVO().getTipoMovimiento());
            TipoMovimiento tm = (TipoMovimiento) tipoMov;
            delegacionAprobacionHelper.setTipoMovimiento(tm);
//            List<UsuarioRol> listaUsuarioRol = usuarioServicio.listaPorRolTodos(
//                    "APRB_TRM_MOVIMIENTOS", obtenerInstitucion().getId());
//            for (UsuarioRol ur : listaUsuarioRol) {
//                UsuarioRolVO urVO = new UsuarioRolVO();
////                urVO.setUsuarioRol(ur);
//                if (ur.getUsuario().getServidor().getEstadoServidor().
//                        getNombre().equals("ACTIVO")) {
//                    if (servidorServicio.servidorEsMaximaAutoridad(ur.getUsuario().getServidorId())) {
//                        urVO.setMaximaAutoridad(SiNoEnum.SI.getDescripcion());
//                    }
//                }
//                delegacionAprobacionHelper.setTipoMovimientoDelegado(new TipoMovimientoDelegado());
//                List<TipoMovimientoDelegado> lista = delegacionAprobacionHelper.getTipoMovimientoDelegadoVO().
//                        getListaTipoMovimientoDelegado();
//                if (lista.size() > 0) {
//                    for (TipoMovimientoDelegado tmd : lista) {
//                        if (ur.getUsuario().getCedula().equals(tmd.getDelegadoCedula())) {
//                            urVO.setIdDelegacionAprobacion(tmd.getId());
//                            urVO.setAprobado(Boolean.TRUE);
//                        }
//                    }
//                }
//                delegacionAprobacionHelper.getListaUsuariosRolVO().add(urVO);
//            }
            reglaNavegacionDirecta(FORMULARIO_ENTIDAD);
        } catch (Exception ex) {
            error(getClass().getName(), "Error al iniciar la delegación de los aprobadores.", ex);
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    /**
     * Método para guardar las delegaciones de los aprobadores.
     */
    public void guardar() {
        try {
            List<TipoMovimientoDelegado> tmds = new ArrayList<TipoMovimientoDelegado>();
            List<UsuarioRolVO> lurVO = delegacionAprobacionHelper.getListaUsuariosRolVO();
            for (UsuarioRolVO urVO : lurVO) {
//                if (urVO.getIdDelegacionAprobacion() > 0) {
//                    if (urVO.getAprobado().equals(Boolean.FALSE)) {
//                        TipoMovimientoDelegado tmd = administracionServicio.buscarTipoMovimientoDelegadoPorId(
//                                urVO.getIdDelegacionAprobacion());
//                        administracionServicio.eliminarTipoMovimientoDelegado(tmd);
//                    }
//                } else {
                if (urVO.getAprobado()) {
                    TipoMovimientoDelegado tmd = new TipoMovimientoDelegado();
                    tmd.setTipoMovimiento(delegacionAprobacionHelper.getTipoMovimiento());
                    tmd.setCodigoFase(EstadosTramiteEnum.APROBACION.getCodigo());
//                    tmd.setDelegadoCedula(urVO.getUsuarioRol().getUsuario().getCedula());
//                    tmd.setDelegadoNombre(urVO.getUsuarioRol().getUsuario().getNombre());
//                    tmd.setRolCodigo(urVO.getUsuarioRol().getRol().getNombre());
//                    tmd.setRolNombre(urVO.getUsuarioRol().getRol().getDescripcion());
                    tmd.setInstitucionCoreId(obtenerUsuarioConectado().getInstitucion().getId());
                    iniciarDatosEntidad(tmd, Boolean.TRUE);
                    tmds.add(tmd);

                    //delegacionAprobacionHelper.setTipoMovimientoDelegado(new TipoMovimientoDelegado());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setTipoMovimiento(delegacionAprobacionHelper.getTipoMovimiento());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setCodigoFase(EstadosTramiteEnum.APROBACION.getCodigo());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setDelegadoCedula(urVO.getUsuarioRol().getUsuario().getCedula());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setDelegadoNombre(urVO.getUsuarioRol().getUsuario().getNombre());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setRolCodigo(urVO.getUsuarioRol().getRol().getNombre());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setRolNombre(urVO.getUsuarioRol().getRol().getDescripcion());
//                    delegacionAprobacionHelper.getTipoMovimientoDelegado().
//                            setInstitucionCoreId(obtenerInstitucion().getId());
//                    iniciarDatosEntidad(delegacionAprobacionHelper.getTipoMovimientoDelegado(), Boolean.TRUE);
//                    administracionServicio.guardarTipoMovimientoDelegado(
//                            delegacionAprobacionHelper.getTipoMovimientoDelegado());
                }
//                }
            }
            if (tmds.isEmpty()) {
                mostrarMensajeEnPantalla("No existe aprobadores seleccionados.", FacesMessage.SEVERITY_ERROR);
            } else {
                String mensaje = administracionServicio.guardarTipoMovimientoDelegado(delegacionAprobacionHelper.
                        getTipoMovimiento().getId(), obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().
                        getUnidadOrganizacional().getId(), tmds, obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                if (mensaje.trim().isEmpty()) {
                    mostrarMensajeEnPantalla(
                            "Datos Guardados Correctamente." + mensaje,
                            FacesMessage.SEVERITY_INFO);
                } else {
                    mostrarMensajeEnPantalla(
                            "Datos Guardados Correctamente, pero existen las siguientes observaciones:" + mensaje,
                            FacesMessage.SEVERITY_INFO);
                }
            }

        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
    }

    /**
     * @return the delegacionAprobacionHelper
     */
    public DelegacionAprobacionHelper getDelegacionAprobacionHelper() {
        return delegacionAprobacionHelper;
    }

    /**
     * @param delegacionAprobacionHelper the delegacionAprobacionHelper to set
     */
    public void setDelegacionAprobacionHelper(final DelegacionAprobacionHelper delegacionAprobacionHelper) {
        this.delegacionAprobacionHelper = delegacionAprobacionHelper;
    }
}
