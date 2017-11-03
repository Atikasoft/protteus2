/*
 *  ConsultaSaldoVacacionControlador.java
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
import ec.com.atikasoft.proteus.controlador.helper.ConsultaSaldoVacacionHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionDetalleDao;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionEnum;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * VacacionSolicitud
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
@ManagedBean(name = "consultaSaldoVacacionBean")
@ViewScoped
public class ConsultaSaldoVacacionControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(ConsultaSaldoVacacionControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String FORMULARIO_ENTIDAD = "/pages/procesos/vacacion/consulta_saldo_vacacion.jsf";

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_INDEX = "/pages/portal_rrhh.jsf";

    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de vacacion.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     *
     */
    @EJB
    private ServidorInstitucionDao servidorInstitucionDao;
    /**
     *
     */
    @EJB
    private VacacionDao vacacionDao;

    /**
     *
     */
    @EJB
    private VacacionDetalleDao vacacionDetalleDao;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{consultaSaldoVacacionHelper}")
    private ConsultaSaldoVacacionHelper consultaSaldoVacacionHelper;

    @Override
    @PostConstruct
    public void init() {
        iniciarDatosServidor();
    }

    /**
     * recupera las vacaciones del servidor.
     *
     * @return
     */
    public String buscarVacacionesSaldos() {
        try {

            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                    obtenerUsuarioConectado().getInstitucion().getId(), obtenerUsuarioConectado().getServidor().getId());
            vacacionDao.totalizarSaldosVacaciones(si.getId());
            Vacacion v = vacacionDao.buscarPorServidor(si.getId());
            Long totalMinutos = v.getSaldo();
            Long totalMinutosProporcional = v.getSaldoProporcional();
            Long totalMinutosDiasPerdidos = v.getSaldoPerdida();
            consultaSaldoVacacionHelper.setVacacion(v);
            consultaSaldoVacacionHelper.setCadenaSaldo("0 Días");
            consultaSaldoVacacionHelper.setCadenaSaldoProporcional("0 Días");
            consultaSaldoVacacionHelper.setSaldo(UtilFechas.convertirMinutosA_ddHHmm(Math.abs(totalMinutos), si.getServidor().getJornada()));
            consultaSaldoVacacionHelper.setSaldoProporcional(UtilFechas.convertirMinutosA_ddHHmm(Math.abs(totalMinutosProporcional), si.getServidor().getJornada()));
            consultaSaldoVacacionHelper.setSaldoDiasPerdidos(UtilFechas.convertirMinutosA_ddHHmm(Math.abs(totalMinutosDiasPerdidos), si.getServidor().getJornada()));
            if (totalMinutos < 0) {
                consultaSaldoVacacionHelper.setCadenaSaldo(
                        consultaSaldoVacacionHelper.getSaldo()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldo()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldo()[2] + " Minutos  (TIEMPO ADEUDADO)");
            } else {
                consultaSaldoVacacionHelper.setCadenaSaldo(
                        consultaSaldoVacacionHelper.getSaldo()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldo()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldo()[2] + " Minutos ");
            }
            if (totalMinutosProporcional < 0) {
                consultaSaldoVacacionHelper.setCadenaSaldoProporcional(
                        consultaSaldoVacacionHelper.getSaldoProporcional()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldoProporcional()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldoProporcional()[2] + " Minutos  (TIEMPO ADEUDADO)");
            } else {
                consultaSaldoVacacionHelper.setCadenaSaldoProporcional(
                        consultaSaldoVacacionHelper.getSaldoProporcional()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldoProporcional()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldoProporcional()[2] + " Minutos ");
            }
            if (totalMinutosDiasPerdidos < 0) {
                consultaSaldoVacacionHelper.setCadenaSaldoDiasPerdidos(
                        consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[2] + " Minutos  (TIEMPO ADEUDADO)");
            } else {
                consultaSaldoVacacionHelper.setCadenaSaldoDiasPerdidos(
                        consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[0] + " Días "
                        + consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[1] + " Horas "
                        + consultaSaldoVacacionHelper.getSaldoDiasPerdidos()[2] + " Minutos ");
            }

        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda vacacion", ex);
        }
        return null;
    }

    /**
     * Obtiene los datos del servidor conectado en la sesión.
     *
     * @return true si hay problemas
     */
    public boolean iniciarDatosServidor() {

        boolean problemas = true;
        try {
            consultaSaldoVacacionHelper.setUsuario(obtenerUsuarioConectado());
            List<ServidorInstitucion> lista
                    = admServicio.buscarServidorInstitucionVigentePorDocumentoServidor(consultaSaldoVacacionHelper.getUsuario().
                            getServidor().getNumeroIdentificacion());
            if (!lista.isEmpty()) {
                consultaSaldoVacacionHelper.getVacacionSolicitud().setServidorInstitucion(lista.get(0));
                consultaSaldoVacacionHelper.getVacacionSolicitud().setIdServidorInstitucion(lista.get(0).getId());
                if (consultaSaldoVacacionHelper.getVacacionSolicitud().getServidorInstitucion() == null) {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                    LOG.log(Level.INFO, "{0} El servidor no se encuentra registrado en una instituci\u00f3n", getClass().
                            getName());
                    return problemas;
                }
                if (!obtenerParametrizacionVacacion()) {
                    mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                    LOG.log(Level.INFO, "{0} El servidor no tiene parametrizacion de vacaciones", getClass().getName());
                } else {
                    buscarVacacionesSaldos();
                    problemas = false;
                }
            } else {
                mostrarMensajeEnPantalla(SIN_CONFIGURACION_DISTRIBUTIVO, FacesMessage.SEVERITY_ERROR);
                LOG.log(Level.INFO, "{0} El servidor no se encuentra registrado en una instituci\u00f3n", getClass().
                        getName());

            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda datos servidor", ex);
        }
        return problemas;
    }

    /**
     * Permite obtener los parametros para la gestion de las vacaciones de
     * acuerdo al regimen laboral del servidor<br><p>
     *
     * @return true si existe la configuracion 1. Regimen Laboral se obtiene
     * desde el detalle del distributivo a aprtir del numero de documento del
     * usuario<br/> 2. Con el regimen laboral Obtener parametrizacion de
     * vacacion del servidor </p>
     */
    public boolean obtenerParametrizacionVacacion() {
        boolean hayRegimenLab = false;
        try {
            RegimenLaboral regLaboral;
            if (consultaSaldoVacacionHelper.getVacacionSolicitud().getServidorInstitucion() != null) {
                DistributivoDetalle dd = obtenerUsuarioConectado().getDistributivoDetalle();
                if (dd != null) {
                    regLaboral = dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral();

                    List<VacacionParametro> listaParametros = vacacionServicio.
                            listarTodosVacacionParametroPorRegimenLaboral(regLaboral.getId());
                    if (!listaParametros.isEmpty()) {
                        for (VacacionParametro p : listaParametros) {
                            consultaSaldoVacacionHelper.getVacacionSolicitud().setVacacionParametro(p);
                            consultaSaldoVacacionHelper.getVacacionSolicitud().setIdVacacionParametro(p.getId());
                            hayRegimenLab = true;
                            break;
                        }
                    } else {
                        mostrarMensajeEnPantalla("No se puede encontrar la configuración para vacaciones anuales.",
                                FacesMessage.SEVERITY_ERROR);
                    }
                }
            }
        } catch (ServicioException ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error al procesar la busqueda parametrizacion", ex);
        }
        return hayRegimenLab;
    }

    /**
     * busca detalles de las vacaciones.
     *
     * @param idVacacion
     */
    public void buscarDetallesVacaciones(final Long idVacacion) {
        try {
            consultaSaldoVacacionHelper.setListaVacacionDetalle(
                    vacacionDetalleDao.buscarPorVacacionPorTipoVacacion(getConsultaSaldoVacacionHelper().getVacacion().getId()));
            consultaSaldoVacacionHelper.setCadenaSaldoDetalle(consultaSaldoVacacionHelper.getCadenaSaldo());
            ejecutarComandoPrimefaces("detDialog.show();");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo vacacion ", ex);
        }
    }

    /**
     * busca detalles de las vacaciones.
     *
     * @param idVacacion
     */
    public void buscarDetallesProporcionales(final Long idVacacion) {
        try {
            consultaSaldoVacacionHelper.setCadenaSaldoDetalle(consultaSaldoVacacionHelper.getCadenaSaldoProporcional());
            consultaSaldoVacacionHelper.setListaVacacionDetalle(
                    vacacionDetalleDao.buscarPorVacacionPorTipoProporcional(getConsultaSaldoVacacionHelper().getVacacion().getId()));
            ejecutarComandoPrimefaces("detDialog.show();");
        } catch (Exception ex) {
            mostrarMensajeEnPantalla(ERROR_CONSULTA, FacesMessage.SEVERITY_ERROR);
            error(getClass().getName(), "Error la procesar la busqueda saldo proporcional ", ex);
        }
    }

    /**
     * Este metodo transacciona la busqueda del nombre del tipo de vacaciones
     * parametro.
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionTipoVacacion(final String codigo) {
        return TipoVacacionEnum.obtenerNombre(codigo);
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
     * Devuelve en palabras el tiempo
     *
     * @param minutos
     * @return
     */
    public String obtenerTiempo(Long minutos) {
        Servidor s = getConsultaSaldoVacacionHelper().getVacacion().getServidorInstitucion().getServidor();
        String cadena = UtilFechas.convertirMinutosA_ddHHmmPalabras(minutos, s.getJornada());
        if (cadena.isEmpty()) {
            cadena = "0 Días";
        }
        return cadena;
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
     * @return the consultaSaldoVacacionHelper
     */
    public ConsultaSaldoVacacionHelper getConsultaSaldoVacacionHelper() {
        return consultaSaldoVacacionHelper;
    }

    /**
     * @param consultaSaldoVacacionHelper the consultaSaldoVacacionHelper to set
     */
    public void setConsultaSaldoVacacionHelper(ConsultaSaldoVacacionHelper consultaSaldoVacacionHelper) {
        this.consultaSaldoVacacionHelper = consultaSaldoVacacionHelper;
    }
}
