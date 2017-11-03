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
import ec.com.atikasoft.proteus.controlador.helper.JustificacionAsistenciaHelper;
import ec.com.atikasoft.proteus.enums.PeriodoVacacionEnum;
import ec.com.atikasoft.proteus.enums.TipoAusentismoEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoJustificacionEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AsistenciaServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 * AsistenciaControlador
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "justificacionAsistenciaBean")
@ViewScoped
public class JustificacionAsistenciaControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(JustificacionAsistenciaControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/asistencia/justificacion_asistencia.jsf";
    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_BUSCAR = "/pages/procesos/asistencia/buscar_servidor_asistencia.jsf";
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
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{justificacionAsistenciaHelper}")
    private JustificacionAsistenciaHelper justificacionAsistenciaHelper;

    @Override
    @PostConstruct
    public void init() {
        justificacionAsistenciaHelper.setFechaDesde(null);
        justificacionAsistenciaHelper.setFechaHasta(null);
        justificacionAsistenciaHelper.getListaAtraso().clear();
    }

    /**
     *
     * @return
     */
    public String guardar() {
        try {
               if (justificacionAsistenciaHelper.getJustificacionAsistencia().getServidor().getId().equals(
                    obtenerUsuarioConectado().getServidor().getId())) {
                mostrarMensajeEnPantalla("No es posible Justificar sus propios registros", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (justificacionAsistenciaHelper.getJustificacionAsistencia().getObservacion() == null || justificacionAsistenciaHelper.getJustificacionAsistencia().getObservacion().isEmpty()) {
                mostrarMensajeEnPantalla("El campo motivo es requerido", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if ( justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo() > justificacionAsistenciaHelper.getAtraso().getMinutosAtraso()) {
                mostrarMensajeEnPantalla("El tiempo a justificar debe ser menor o igual al tiempo del Ausentismo", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            if (!justificacionAsistenciaHelper.getAtraso().getTipo().equals(TipoAusentismoEnum.FALTA.getCodigo()) &&
                    justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo() > justificacionAsistenciaHelper.getAtraso().getMinutosXJustificar()) {
                mostrarMensajeEnPantalla("El tiempo a justificar debe ser menor o igual al tiempo no justificado del Ausentismo", FacesMessage.SEVERITY_ERROR);
                return null;
            }
            Servidor s =justificacionAsistenciaHelper.getJustificacionAsistencia().getServidor();
            long cantSolMinutos = 0l;
            if (justificacionAsistenciaHelper.getAtraso().getTipo().equals(TipoAusentismoEnum.FALTA.getCodigo())) {
                cantSolMinutos = UtilFechas.convertirEnMinutosPorTipoUnidadTiempo('D', justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo(),s.getJornada());
            } else {
                cantSolMinutos = justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo();
            }
            if (justificacionAsistenciaHelper.getTipoJustificacion().equals(TipoJustificacionEnum.VACACION.getCodigo())
                    && justificacionAsistenciaHelper.getSaldoVacacion() < cantSolMinutos) {
                mostrarMensajeEnPantalla("El saldo disponible no es suficiente para Justificar el Ausentismo", FacesMessage.SEVERITY_ERROR);
                return null;
            }
               if (!justificacionAsistenciaHelper.getAtraso().getTipo().equals(TipoAusentismoEnum.FALTA.getCodigo())) {
                long minJustificados = 0l;
                justificacionAsistenciaHelper.getAtraso().setEsJustificado(Boolean.FALSE);
                for (JustificacionAsistencia jj : getListaJustificaciones(justificacionAsistenciaHelper.getAtraso().getId())) {
                    if (jj.getVigente()) {
                        minJustificados += jj.getCantidadTiempo();
                        System.out.println(" contando lo jsutificado:"+jj.getCantidadTiempo()+" total:"+minJustificados);
                    }
                }
                if ((minJustificados+justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo()) == 
                        justificacionAsistenciaHelper.getAtraso().getMinutosAtraso()) {
                    justificacionAsistenciaHelper.getAtraso().setEsJustificado(Boolean.TRUE);
                    justificacionAsistenciaHelper.getAtraso().setMinutosXJustificar(0l);
                }else{
                    long dif = justificacionAsistenciaHelper.getAtraso().getMinutosAtraso()-minJustificados-justificacionAsistenciaHelper.getJustificacionAsistencia().getCantidadTiempo();
                    justificacionAsistenciaHelper.getAtraso().setMinutosXJustificar(dif < 0?0:dif);
                }
            } else {
                justificacionAsistenciaHelper.getAtraso().setEsJustificado(Boolean.TRUE);
                justificacionAsistenciaHelper.getAtraso().setMinutosXJustificar(0l);
            }
            asistenciaServicio.guardarJustificacionAsistencia(justificacionAsistenciaHelper.getJustificacionAsistencia(), justificacionAsistenciaHelper.getTipoJustificacion(), justificacionAsistenciaHelper.getAtraso());
            mostrarMensajeEnPantalla(REGISTRO_GUARDADO, FacesMessage.SEVERITY_INFO);
            iniciarNuevaJustificacion(justificacionAsistenciaHelper.getAtraso());
            buscar();
        } catch (ServicioException e) {
            mostrarMensajeEnPantalla(ERROR_REGISTRO_GUARDADO, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al guardar", e);
        }
        return null;
    }

    /**
     *
     * @param atraso
     * @return
     */
    public String iniciarNuevaJustificacion(Atraso atraso) {
        justificacionAsistenciaHelper.setAtraso(atraso);
        iniciarComboTipoJustificacion(atraso.getTipo());
        justificacionAsistenciaHelper.setTipoJustificacion(TipoJustificacionEnum.NORMAL.getCodigo());
        justificacionAsistenciaHelper.setTipoJustificacion(null);
        justificacionAsistenciaHelper.setSaldoVacacion(0l);
        if (justificacionAsistenciaHelper.getAtraso() != null) {
            justificacionAsistenciaHelper.setJustificacionAsistencia(new JustificacionAsistencia());
            justificacionAsistenciaHelper.getJustificacionAsistencia().setInstitucionEjercicioFiscal(obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
            justificacionAsistenciaHelper.getJustificacionAsistencia().setServidor(justificacionAsistenciaHelper.getAtraso().getServidor());
            justificacionAsistenciaHelper.getJustificacionAsistencia().setServidorAprobador(obtenerUsuarioConectado().getServidor());
            justificacionAsistenciaHelper.getJustificacionAsistencia().setFecha(justificacionAsistenciaHelper.getAtraso().getFecha());
            justificacionAsistenciaHelper.getJustificacionAsistencia().setAtraso(atraso);

            if (justificacionAsistenciaHelper.getAtraso().getTipo().equals(TipoAusentismoEnum.FALTA.getCodigo())) {
                justificacionAsistenciaHelper.getJustificacionAsistencia().setUnidadTiempo(PeriodoVacacionEnum.DIAS.getCodigo());
                justificacionAsistenciaHelper.getJustificacionAsistencia().setCantidadTiempo(1l);
            } else {
                justificacionAsistenciaHelper.getJustificacionAsistencia().setUnidadTiempo(PeriodoVacacionEnum.MINUTOS.getCodigo());
                justificacionAsistenciaHelper.getJustificacionAsistencia().setCantidadTiempo(
                        justificacionAsistenciaHelper.getAtraso().getMinutosXJustificar());
            }

            justificacionAsistenciaHelper.getJustificacionAsistencia().setObservacion(null);
            justificacionAsistenciaHelper.getJustificacionAsistencia().setVacacionSolicitud(null);
            iniciarDatosEntidad(justificacionAsistenciaHelper.getJustificacionAsistencia(), Boolean.TRUE);
            iniciarDatosEntidad(justificacionAsistenciaHelper.getAtraso(), Boolean.FALSE);

            ejecutarComandoPrimefaces("justificarDialog.show();");
          
        }

        return FORMULARIO_ENTIDAD;
    }

    /**
     * da formato a la fecha
     *
     * @param fecha
     * @return
     */
    public String formatearFecha(Date fecha) {
        if (fecha != null) {
            return UtilFechas.formatear(fecha);
        } else {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public long obtenerSaldo() {
        long saldo = 0l;
        try {
            if (justificacionAsistenciaHelper.getTipoJustificacion().equals(TipoJustificacionEnum.VACACION.getCodigo())) {
                saldo = vacacionServicio.buscarSaldoVacacionesPorServidor(obtenerUsuarioConectado().getInstitucion().getId(), justificacionAsistenciaHelper.getServidor().getId());
                justificacionAsistenciaHelper.setSaldoDiaVacacion(UtilFechas.convertirEnDiasPorTipoUnidadTiempo('M', saldo,
                        justificacionAsistenciaHelper.getServidor().getJornada()));
                justificacionAsistenciaHelper.setSaldoVacacion(saldo);
            }
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al buscar saldo de vacaciones", ex);
        }
        return saldo;
    }

    /**
     * Busca todos los registros
     *
     * @return
     */
    public String buscar() {
        try {
            justificacionAsistenciaHelper.setListaAtraso(
                    asistenciaServicio.listarAtrasoPorServidorYRangoFecha(justificacionAsistenciaHelper.getServidor().getId(),
                            justificacionAsistenciaHelper.getFechaDesde(), justificacionAsistenciaHelper.getFechaHasta()));
            if (!justificacionAsistenciaHelper.getListaAtraso().isEmpty()) {

            } else {
                mostrarMensajeEnPantalla(BUSQUEDA_SIN_RESULTADOS, FacesMessage.SEVERITY_INFO);
            }

        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda", ex);
        }
        return null;
    }

    /**
     *
     * @param idAtraso
     * @return
     */
    public List<JustificacionAsistencia> getListaJustificaciones(final Long idAtraso) {
        List<JustificacionAsistencia> a = null;
        try {
            a = asistenciaServicio.listarJustificacionAsistenciaPorAtraso(idAtraso);
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda de justificacion de asistencias", ex);
        }
        return a;
    }

    /**
     * Este metodo llena las opciones para el combo de Opciones de Rubros de
     * descuentos.
     */
    private void iniciarComboTipoJustificacion(String tipo) {
        justificacionAsistenciaHelper.getListaOpcionesTipoJustificacion().clear();
        justificacionAsistenciaHelper.getListaOpcionesTipoJustificacion().add(new SelectItem(TipoJustificacionEnum.NORMAL.getCodigo(),
                TipoJustificacionEnum.NORMAL.getDescripcion()));
        if ((tipo.equals(TipoAusentismoEnum.ATRASO.getCodigo())
                || tipo.equals(TipoAusentismoEnum.FALTA.getCodigo()))) {
            justificacionAsistenciaHelper.getListaOpcionesTipoJustificacion().add(new SelectItem(TipoJustificacionEnum.VACACION.getCodigo(),
                    TipoJustificacionEnum.VACACION.getDescripcion()));
        }
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
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * ausentismo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoAusentismo(final String codigo) {
        return TipoAusentismoEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del tipo de
     * ausentismo.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionPeriodo(final String codigo) {
        return PeriodoVacacionEnum.obtenerDescripcion(codigo);
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
    public String irJustificacionAsistencia() {
        return FORMULARIO_ENTIDAD;
    }

    /**
     * @return the justificacionAsistenciaHelper
     */
    public JustificacionAsistenciaHelper getJustificacionAsistenciaHelper() {
        return justificacionAsistenciaHelper;
    }

    /**
     * @param justificacionAsistenciaHelper the justificacionAsistenciaHelper to
     * set
     */
    public void setJustificacionAsistenciaHelper(JustificacionAsistenciaHelper justificacionAsistenciaHelper) {
        this.justificacionAsistenciaHelper = justificacionAsistenciaHelper;
    }
}
