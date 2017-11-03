/*
 *  BandejaTareaControlador.java
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
 *  31/10/2012
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.ConsultaTramitesServidoresInactivosHelper;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicio;
import ec.com.atikasoft.proteus.servicio.TareaServicioSMP;
import ec.com.atikasoft.proteus.servicio.TramiteServicio;
import ec.com.atikasoft.proteus.vo.TareaVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Leydis Garzón
 */
@ManagedBean(name = "consultaTramitesServidoresInactivosBean")
@ViewScoped
@Getter
@Setter
public class ConsultaTramitesServidoresInactivosControlador extends BaseControlador {

    /**
     * Servicio de tarea.
     */
    @EJB
    private TareaServicio tareaServicio;

    /**
     * Servicio de tramite.
     */
    @EJB
    private TareaServicioSMP tareaServicioSMP;

    /**
     * Url de la pagina.
     */
    public static final String LISTA_ENTIDAD = "/pages/bandeja_tarea/bandeja_tarea.jsf";

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getLogger(ConsultaTramitesServidoresInactivosControlador.class.getCanonicalName());

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaTramitesServidoresInactivosHelper}")
    private ConsultaTramitesServidoresInactivosHelper consultaTramitesServidoresInactivosHelper;

    /**
     * Servicio de tramite.
     */
    @EJB
    private TramiteServicio tramiteServicio;

    /**
     * Servicio administración
     */
    @EJB
    private AdministracionServicio administracionServicio;

    /**
     * Dao de Parametro Institucional
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    @Override
    @PostConstruct
    public void init() {
        buscar();
    }

    /**
     * Este método busca las tareas segun el ejercicio fiscal actual.
     *
     * @return String
     */
    public String buscar() {
        try {
            consultaTramitesServidoresInactivosHelper.getListaTareas().clear();
            List<TareaVO> tareas = tareaServicioSMP.buscarPorServidoresInactivos();
            consultaTramitesServidoresInactivosHelper.setListaTareas(tareas);
            consultaTramitesServidoresInactivosHelper.getListaTareasFiltradas().clear();
            consultaTramitesServidoresInactivosHelper.getListaTareasFiltradas().addAll(tareas);
            mostrarMensajeEnPantalla("Trámite eliminado correctamente", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            LOG.log(Level.INFO, "Error al buscar las tareas {0}", e);
            error(getClass().getName(), "Error al buscar las tareas.", e);
        }
        return LISTA_ENTIDAD;
    }

    public void anularTarea() {
        try {
            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            tramiteServicio.gestionarTramite(consultaTramitesServidoresInactivosHelper.getTareaVO().getTramite().getId(),
                    obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().
                    getCodigo(), Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().getNombre()),
                    /*"ANULADO POR ESTAR ASIGNADO A UN SERVIDOR INACTIVO"*/
                    consultaTramitesServidoresInactivosHelper.getJustificacion().toUpperCase(), obtenerUsuarioConectado(),
                    esRRHH(pi.getValorTexto()), consultaTramitesServidoresInactivosHelper.getTareaVO().getTramite().
                    getCodigoFase(), EstadosTramiteEnum.ANULADO.getCodigo());
            consultaTramitesServidoresInactivosHelper.getListaTareas().remove(
                    consultaTramitesServidoresInactivosHelper.getTareaVO());
            consultaTramitesServidoresInactivosHelper.getListaTareasFiltradas().remove(
                    consultaTramitesServidoresInactivosHelper.getTareaVO());
            consultaTramitesServidoresInactivosHelper.setJustificacion("");
            mostrarMensajeEnPantalla("Trámite anulado correctamente", FacesMessage.SEVERITY_INFO);
            ejecutarComandoPrimefaces("justificacionAnularWV.hide();confirmacionAnularWV.hide()");
        } catch (Exception ex) {
            Logger.getLogger(ConsultaTramitesServidoresInactivosControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarTarea() {
        try {
            ParametroInstitucional pi = parametroInstitucionalDao.buscarPorInstitucionYNemonico(obtenerUsuarioConectado().
                    getInstitucion().getId(), ParametroInstitucionCatalogoEnum.CODIGO_RRHH.getCodigo());
            tramiteServicio.gestionarTramite(consultaTramitesServidoresInactivosHelper.getTareaVO().getTramite().getId(), obtenerUsuarioConectado().
                    getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo(),
                    Integer.parseInt(obtenerUsuarioConectado().getEjercicioFiscal().getNombre()),
                    "ELIMINADO POR ESTAR ASIGNADO A UN SERVIDOR INACTIVO", obtenerUsuarioConectado(), esRRHH(pi.getValorTexto()),
                    consultaTramitesServidoresInactivosHelper.getTareaVO().getTramite().getCodigoFase(),
                    EstadosTramiteEnum.ELIMINADO.getCodigo());
            consultaTramitesServidoresInactivosHelper.getListaTareas().remove(
                    consultaTramitesServidoresInactivosHelper.getTareaVO());
        } catch (Exception ex) {
            Logger.getLogger(ConsultaTramitesServidoresInactivosControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public Boolean puedeEliminar(TareaVO tareaVO) {
//        return tareaVO.getTramite().getCodigoFase().equals(EstadosTramiteEnum.ELABORACION.getCodigo());
//    }
    public Boolean puedeAnular(TareaVO tareaVO) {
        String estado = tareaVO.getTramite().getCodigoFase();
        return estado.equals(EstadosTramiteEnum.APROBACION.getCodigo()) || estado.equals(
                EstadosTramiteEnum.LEGALIZACION.getCodigo()) || estado.equals(
                        EstadosTramiteEnum.LEGALIZACION_NOMINA.getCodigo());
    }

}
