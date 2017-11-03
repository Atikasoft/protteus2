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
import ec.com.atikasoft.proteus.controlador.helper.PlanificacionJornadaLaboralHelper;
import ec.com.atikasoft.proteus.dao.ServidorDao;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.excepciones.ServidorSinDistributivoException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
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
import org.apache.commons.beanutils.BeanUtils;

/**
 * PlanificacionHorario
 *
 * @author Nelson Jumbo <nelson.jumbo@atikasoft.com.ec>
 */
@ManagedBean(name = "planificacionJornadaLaboralBean")
@ViewScoped
public class PlanificacionJornadaLaboralControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(PlanificacionJornadaLaboralControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/administracion/jornada_laboral/planificacion_jornada.jsf";

    /**
     * Regla de navegación.
     */
    public static final String PANTALLA_INICIAL = "/pages/index.jsf";

    /**
     * Maxima cantidad de horas para jornada laboral.
     */
    private static final Integer MAX_JORNADA_LABORAL = 10;

    /**
     * Minima cantidad de horas para jornada laboral
     */
    private static final Integer MIN_JORNADA_LABORAL = 1;

    /**
     * Servicio de servidor.
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     *
     */
    @EJB
    private ServidorDao servidorDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{planificacionJornadaLaboralHelper}")
    private PlanificacionJornadaLaboralHelper planificacionJornadaLaboralHelper;

    /**
     * Constructor por defecto.
     */
    public PlanificacionJornadaLaboralControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        planificacionJornadaLaboralHelper.setNombreServidor("");
        planificacionJornadaLaboralHelper.setNumeroIdentificacion("");
        planificacionJornadaLaboralHelper.getListaServidores().clear();
    }

    public String guardar() {
        try {
            if (planificacionJornadaLaboralHelper.getServidor().getId().equals(
                    obtenerUsuarioConectado().getServidor().getId())) {
                mostrarMensajeEnPantalla("No es posible Actualizar sus propios registros", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (planificacionJornadaLaboralHelper.getServidor().getHoraEntrada() == null) {
                mostrarMensajeEnPantalla("El campo hora de inicio es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }

            if (planificacionJornadaLaboralHelper.getServidor().getJornada() > MAX_JORNADA_LABORAL
                    || planificacionJornadaLaboralHelper.getServidor().getJornada() < MIN_JORNADA_LABORAL) {
                mostrarMensajeEnPantalla("La jornada laboral debe ser de mínimo 1 y máximo 10 horas", FacesMessage.SEVERITY_ERROR);
                return null;
            }

            if (planificacionJornadaLaboralHelper.getServidor().getJornada() == null) {
                mostrarMensajeEnPantalla("El campo hora de jornada laboral es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }

            servidorServicio.actualizarJornadaServidorConGeneracionHistorico(
                    planificacionJornadaLaboralHelper.getServidor(),
                    planificacionJornadaLaboralHelper.getServidorConDatosAntesDeEdicion(),
                    obtenerUsuarioConectado(), obtenerUsuarioConectado().getInstitucionEjercicioFiscal().getId());

            igualarInformacionServidorAntesDepuesEdicion();
            servidorDao.actualizar(planificacionJornadaLaboralHelper.getServidor());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
        } catch (ServidorSinDistributivoException ssde) {
            mostrarMensajeEnPantalla(ERROR_DIST_DETALLE_SERVIDOR, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al generar historicos", ssde);
        } catch (Exception e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     * Luego de guardar permite que el servidor antes de edicion actualice su
     * informacion con la que se acaba de guardar para poder volver a comparar
     * los cambios realizados
     *
     * @throws Exception
     */
    private void igualarInformacionServidorAntesDepuesEdicion() throws Exception {
        planificacionJornadaLaboralHelper.setServidorConDatosAntesDeEdicion(
                (Servidor) BeanUtils.cloneBean(planificacionJornadaLaboralHelper.getServidor()));
    }

    public String iniciarEdicion() {
        try {
            iniciarDatosEntidad(planificacionJornadaLaboralHelper.getServidor(), Boolean.FALSE);
            planificacionJornadaLaboralHelper.setEsNuevo(Boolean.FALSE);

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al iniciar la edicion", ex);
        }
        return FORMULARIO_ENTIDAD;
    }

    public String editarServidor() {
        if (planificacionJornadaLaboralHelper.getServidor() != null & planificacionJornadaLaboralHelper.getServidor().getId() != null) {
            try {
                planificacionJornadaLaboralHelper.setEsNuevo(Boolean.FALSE);
//                planificacionJornadaLaboralHelper.getServidor().setHoraEntrada(UtilFechas.convertirHoraTimbreStringADate(planificacionJornadaLaboralHelper.getServidor().getHoraEntradaParaMostrar()));
                planificacionJornadaLaboralHelper.setServidorConDatosAntesDeEdicion((Servidor) BeanUtils.cloneBean(planificacionJornadaLaboralHelper.getServidor()));
            } catch (Exception ex) {
                mostrarMensajeEnPantalla(ERROR_REGISTRO_ACTUALIZADO, FacesMessage.SEVERITY_ERROR);
                error(getClass().getName(), "Error al iniciar la edicion", ex);
            }

        }
        return null;
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

            if (planificacionJornadaLaboralHelper.getNombreServidor().length() < 3 && !planificacionJornadaLaboralHelper.getNombreServidor().isEmpty()) {
                planificacionJornadaLaboralHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_NOMBRE, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (planificacionJornadaLaboralHelper.getNumeroIdentificacion().length() < 3 && !planificacionJornadaLaboralHelper.getNumeroIdentificacion().isEmpty()) {
                planificacionJornadaLaboralHelper.getListaServidores().clear();
                mostrarMensajeEnPantalla(CARACTERES_PARA_BUSQUEDA_X_IDENTIFICACION, FacesMessage.SEVERITY_INFO);
                return null;
            }

            if (planificacionJornadaLaboralHelper.getNombreServidor().isEmpty() && planificacionJornadaLaboralHelper.getNumeroIdentificacion().isEmpty()) {
                mostrarMensajeEnPantalla(PARAMETROS_PARA_BUSQUEDA, FacesMessage.SEVERITY_INFO);
                return null;
            }
            if (!planificacionJornadaLaboralHelper.getNombreServidor().trim().isEmpty()) {
                planificacionJornadaLaboralHelper.setNombreServidor(planificacionJornadaLaboralHelper.getNombreServidor().toUpperCase());
            }
            planificacionJornadaLaboralHelper.getListaServidores().clear();
            BusquedaServidorVO ser = new BusquedaServidorVO();
            ser.setNombreServidor(planificacionJornadaLaboralHelper.getNombreServidor());
            ser.setNumeroDocumentoServidor(planificacionJornadaLaboralHelper.getNumeroIdentificacion());
            ser.setPuestoVacante(Boolean.FALSE);
            ser.setIdInstitucion(obtenerUsuarioConectado().getInstitucion().getId());
            ser.setCodUnidadAdministrativa(obtenerUsuarioConectado().getDistributivoDetalle().getDistributivo().getUnidadOrganizacional().getCodigo());

            List<DistributivoDetalle> lista = servidorServicio.buscar(ser);
            for (DistributivoDetalle s : lista) {
                if (s.getVigente()) {
                    planificacionJornadaLaboralHelper.getListaServidores().add(s.getServidor());
                }
            }
            LOG.log(Level.INFO, "Registros recuperados en la busqueda de servidores para planificar horarios:{0}", planificacionJornadaLaboralHelper.getListaServidores().size());

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de servidores", ex);
        }
        return null;
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
     * @return the planificacionJornadaHelper
     */
    public PlanificacionJornadaLaboralHelper getPlanificacionJornadaLaboralHelper() {
        return planificacionJornadaLaboralHelper;
    }

    /**
     * @param planificacionJornadaLaboralHelper the planificacionJornadaHelper
     * to set
     */
    public void setPlanificacionJornadaLaboralHelper(PlanificacionJornadaLaboralHelper planificacionJornadaLaboralHelper) {
        this.planificacionJornadaLaboralHelper = planificacionJornadaLaboralHelper;
    }
}
