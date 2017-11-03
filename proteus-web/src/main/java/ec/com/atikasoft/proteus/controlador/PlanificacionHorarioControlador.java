/*
 *  PlanificacionHorarioControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  17/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import static ec.com.atikasoft.proteus.controlador.base.BaseControlador.ERROR_CONSULTA;
import ec.com.atikasoft.proteus.controlador.helper.PlanificacionHorarioHelper;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.Atraso;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.PlanificacionHorario;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.AsistenciaServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.apache.commons.beanutils.BeanUtils;

/**
 * PlanificacionHorario
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "planificacionHorarioBean")
@ViewScoped
public class PlanificacionHorarioControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionHorarioControlador.class.getCanonicalName());
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/asistencia/planificacion_horario.jsf";
    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_INICIAL = "/pages/index.jsf";
    /**
     * Servicio de administracion.
     */
    @EJB
    private AsistenciaServicio asistenciaServicio;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;
    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;
    /**
     * Helper de clase.
     */
    @ManagedProperty("#{planificacionHorarioHelper}")
    private PlanificacionHorarioHelper planificacionHorarioHelper;

    /**
     * Constructor por defecto.
     */
    public PlanificacionHorarioControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        planificacionHorarioHelper.setNombreServidor("");
        planificacionHorarioHelper.setNumeroIdentificacion("");
        planificacionHorarioHelper.getListaServidores().clear();
        iniciarComboMeses();
        iniciarComboEjercicioFiscal();
    }
/**
 * 
 * @return 
 */
    public String guardar() {
        try {
            if (planificacionHorarioHelper.getPlanificacionHorario().getServidor().getId().equals(
                    obtenerUsuarioConectado().getServidor().getId())) {
                mostrarMensajeEnPantalla("No es posible Actualizar sus propios registros", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            
            if (planificacionHorarioHelper.getPlanificacionHorario().getHoraInicio() == null) {
                mostrarMensajeEnPantalla("El campo hora de inicio es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (planificacionHorarioHelper.getPlanificacionHorario().getHoraInicioAlmuerzo() == null) {
                mostrarMensajeEnPantalla("El campo hora de inicio de almuerzo es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (planificacionHorarioHelper.getPlanificacionHorario().getHoraFin() == null) {
                mostrarMensajeEnPantalla("El campo hora de salida es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (planificacionHorarioHelper.getPlanificacionHorario().getHoraFinAlmuerzo() == null) {
                mostrarMensajeEnPantalla("El campo hora fin de almuerzo es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (planificacionHorarioHelper.getEsNuevo()) {
                if (validarExiste()) {
                    mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
                } else {
                    asistenciaServicio.guardarPlanificacionHorario(planificacionHorarioHelper.getPlanificacionHorario());
                    mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
                    iniciarNuevo();
                    ejecutarComandoPrimefaces("editDialog.hide();");
                }

            } else {
                if(esProcesadaPlanificacion()){
                     mostrarMensajeEnPantalla("Esta planificación ya está siendo utilizada en el control de Ausentismo y Horas Extras", FacesMessage.SEVERITY_ERROR);
                     return null;
                }
                iniciarDatosEntidad(planificacionHorarioHelper.getPlanificacionHorario(), Boolean.FALSE);
                asistenciaServicio.actualizarPlanificacionHorario(planificacionHorarioHelper.getPlanificacionHorario());
                mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * metodo para validar si ya existe el código.
     *
     * @return hayCodigo Boolean.
     */
    public Boolean validarExiste() {
        Boolean hayCodigo = true;
        try {
            planificacionHorarioHelper.getListaPlanificacionHorarioDuplicado().clear();
            planificacionHorarioHelper.setListaPlanificacionHorarioDuplicado(asistenciaServicio.listarPlanificacionHorarioPorServidorEjercicioFiscalYMes(
                    planificacionHorarioHelper.getPlanificacionHorario().getServidor().getId(),
                    planificacionHorarioHelper.getPlanificacionHorario().getInstitucionEjercicioFiscal().getId(),
                    planificacionHorarioHelper.getPlanificacionHorario().getMes()));

            if (planificacionHorarioHelper.getListaPlanificacionHorarioDuplicado().isEmpty()) {
                hayCodigo = false;
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la validacion de duplicidad", ex);
        }
        return hayCodigo;
    }
/**
 * 
 */
    public void buscarRegistroHorario() {
        if (planificacionHorarioHelper.getPlanificacionHorario().getInstitucionEjercicioFiscal() != null
                && planificacionHorarioHelper.getPlanificacionHorario().getInstitucionEjercicioFiscal().getId() == null) {
            mostrarMensajeEnPantalla("El campo ejercicio fiscal es requerido", FacesMessage.SEVERITY_ERROR);
            planificacionHorarioHelper.getPlanificacionHorario().setMes(null);
            return;
        }
        if (planificacionHorarioHelper.getPlanificacionHorario().getInstitucionEjercicioFiscal().getId() != null
                && planificacionHorarioHelper.getPlanificacionHorario().getServidor().getId() != null
                && planificacionHorarioHelper.getPlanificacionHorario().getMes() != null) {
            if (validarExiste()) {
                planificacionHorarioHelper.setEsNuevo(Boolean.FALSE);
                if (!planificacionHorarioHelper.getListaPlanificacionHorarioDuplicado().isEmpty()) {
                    planificacionHorarioHelper.setPlanificacionHorario(planificacionHorarioHelper.
                            getListaPlanificacionHorarioDuplicado().get(0));
                }
                mostrarMensajeEnPantalla(REGISTRO_EXISTENTE, FacesMessage.SEVERITY_WARN);
            } else {
                planificacionHorarioHelper.setEsNuevo(Boolean.TRUE);
            }
        }
    }
/**
 * 
 * @return 
 */
    public String iniciarEdicion() {
        try {
            Object cloneBean
                    = BeanUtils.cloneBean(planificacionHorarioHelper.getPlanificacionHorarioEditDelete());
            PlanificacionHorario d = (PlanificacionHorario) cloneBean;
            iniciarDatosEntidad(d, Boolean.FALSE);
            planificacionHorarioHelper.setPlanificacionHorario(d);
            planificacionHorarioHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }
/**
 * Permite inicialzar un nuevo registros de planificacion de horario para horas extras.
 * @return 
 */
    public String iniciarNuevo() {
         planificacionHorarioHelper.getListaPlanificacionHorarios().clear();
        if (planificacionHorarioHelper.getServidor() != null & planificacionHorarioHelper.getServidor().getId() != null) {
            planificacionHorarioHelper.setPlanificacionHorario(new PlanificacionHorario());
            planificacionHorarioHelper.getPlanificacionHorario().setServidor(planificacionHorarioHelper.getServidor());
            planificacionHorarioHelper.getPlanificacionHorario().setInstitucionEjercicioFiscal(new InstitucionEjercicioFiscal());
            iniciarDatosEntidad(planificacionHorarioHelper.getPlanificacionHorario(), Boolean.TRUE);
            planificacionHorarioHelper.setEsNuevo(Boolean.TRUE);
           
        }
        return null;
    }

    public String borrar() {
        try {
            asistenciaServicio.eliminarPlanificacionHorario(planificacionHorarioHelper.getPlanificacionHorarioEditDelete());
            planificacionHorarioHelper.getListaPlanificacionHorarios().
                    remove(planificacionHorarioHelper.getPlanificacionHorarioEditDelete());
            mostrarMensajeEnPantalla(REGISTRO_ELIMINADO, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ELIMINADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la eliminacion", ex);
        }
        return null;
    }
/**
 * Permite buscar registros.
 * @return 
 */
    public String buscar() {
        try {
            if (planificacionHorarioHelper.getServidor() != null & planificacionHorarioHelper.getServidor().getId() != null) {
                planificacionHorarioHelper.getListaPlanificacionHorarios().clear();
                planificacionHorarioHelper.setListaPlanificacionHorarios(
                        asistenciaServicio.listarPlanificacionHorarioPorServidorEjercicioFiscal(
                                planificacionHorarioHelper.getPlanificacionHorario().getServidor().getId(),
                                planificacionHorarioHelper.getPlanificacionHorario().getInstitucionEjercicioFiscal().getId()));
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de todos los registros del servidor", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    /**
     * Este metodo llena las opciones para el combo de INSTITUCION ejercicios
     * fiscales.
     */
    private void iniciarComboEjercicioFiscal() {
        try {
            List<InstitucionEjercicioFiscal> lista = admServicio.listarTodosInstitucionPorNombre(
                    obtenerUsuarioConectado().getInstitucion().getNombre());
            planificacionHorarioHelper.getListaOpcionEjercicioFiscal().clear();
            iniciarCombos(planificacionHorarioHelper.getListaOpcionEjercicioFiscal());
            for (InstitucionEjercicioFiscal t : lista) {
                planificacionHorarioHelper.getListaOpcionEjercicioFiscal().add(new SelectItem(t.getId(), t.getEjercicioFiscal().getNombre()));
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda ejercicio fiscal ", ex);
        }
    }
   /**
    * Permite determinar si este registro ya fue usado en el control de asistencia.
    * true si ya fue usado en el procesamiento de asistencia.
    * @return 
    */ 
public boolean esProcesadaPlanificacion(){
      try {
          if(planificacionHorarioHelper.getPlanificacionHorario().getId() != null){
            List<Atraso> lista = asistenciaServicio.listarAtrasoPorPlanificacionHorario(
                   planificacionHorarioHelper.getPlanificacionHorario().getId());
            if(!lista.isEmpty()){
                return true;
            }
          }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de atrasos por planificacion ", ex);
        }
      return false;
}
    /**
     * Retorna el nombre del mes del año.
     *
     * @param mes
     * @return
     */
    public String obtenerNombreMes(int mes) {
        return UtilFechas.obtenerNombreMes(mes);
    }

    /**
     * metodo que busca el servidor por nombre y/o número de identificacion.
     *
     * @return
     */
    public String buscarServidor() {
        try {

            if (planificacionHorarioHelper.getNombreServidor().length() < 3 && !planificacionHorarioHelper.getNombreServidor().isEmpty()) {
                planificacionHorarioHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (planificacionHorarioHelper.getNumeroIdentificacion().length() < 3 && !planificacionHorarioHelper.getNumeroIdentificacion().isEmpty()) {
                planificacionHorarioHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (planificacionHorarioHelper.getNombreServidor().isEmpty() && planificacionHorarioHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (!planificacionHorarioHelper.getNombreServidor().trim().isEmpty()) {
                planificacionHorarioHelper.setNombreServidor(planificacionHorarioHelper.getNombreServidor().toUpperCase());
            }
            planificacionHorarioHelper.getListaPlanificacionHorarios().clear();
            planificacionHorarioHelper.getListaPlanificacionHorarioDuplicado().clear();
            planificacionHorarioHelper.getListaServidores().clear();
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(planificacionHorarioHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(planificacionHorarioHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            ser.setCodUnidadAdministrativa(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());

            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            for (DistributivoDetalle s : lista) {
                if (s.getVigente()) {
                    planificacionHorarioHelper.getListaServidores().add(s.getServidor());
                }
            }
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores para planificar horarios:{0}", planificacionHorarioHelper.getListaServidores().size());

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de servidores", ex);
        }
        return null;
    }

    /**
     * Este metodo llena las opciones para el combo de meses del año.
     */
    private void iniciarComboMeses() {
        planificacionHorarioHelper.getListaOpcionMeses().clear();
        iniciarCombos(planificacionHorarioHelper.getListaOpcionMeses());
        for (int i = 1; i <= UtilFechas.MESES_EN_ANIO; i++) {
            planificacionHorarioHelper.getListaOpcionMeses().add(new SelectItem(i, i + " - " + UtilFechas.obtenerNombreMes(i)));
        }
    }

    /**
     * Este metodo transacciona la busqueda de la descripción del tipo de
     * documento de identificacion parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoDocumento(final String codigo) {
        return TipoDocumentoEnum.obtenerNombre(codigo);
    }

    /**
     * @return the planificacionHorarioHelper
     */
    public PlanificacionHorarioHelper getPlanificacionHorarioHelper() {
        return planificacionHorarioHelper;
    }

    /**
     * @param planificacionHorarioHelper the planificacionHorarioHelper to set
     */
    public void setPlanificacionHorarioHelper(PlanificacionHorarioHelper planificacionHorarioHelper) {
        this.planificacionHorarioHelper = planificacionHorarioHelper;
    }
}
