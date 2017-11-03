/*
 *  PlanificacionVacacionControlador.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  01/06/2017
 *
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.DescuentoTiempoNoLaboradoHelper;
import ec.com.atikasoft.proteus.dao.ServidorInstitucionDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.enums.EstadoPlanVacacionEnum;
import ec.com.atikasoft.proteus.enums.EstadoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.servicio.AdministracionServicio;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoPuestoServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import ec.com.atikasoft.proteus.vo.BusquedaServidorVO;
import ec.com.atikasoft.proteus.vo.UsuarioVO;
import java.util.ArrayList;
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
 *
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
@ManagedBean(name = "descuentoTiempoNoLaboradoBean")
@ViewScoped
public class DescuentoTiempoNoLaboradoControlador extends BaseControlador {

    /**
     * Logger de clases.
     */
    private static final Logger LOG = Logger.getLogger(DescuentoTiempoNoLaboradoControlador.class.getCanonicalName());

    /**
     * Regla de navegación.
     */
    public static final String PAGINA_PORTAL = "/pages/portal_rrhh.jsf";
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;
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
    UnidadOrganizacionalDao unidadOrgDao;
    /**
     * 
     */
    @EJB
    DesconcentradoServicio desconcentradoServicio;

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{descuentoTiempoNoLaboradoHelper}")
    private DescuentoTiempoNoLaboradoHelper descuentoTiempoNoLaboradoHelper;

    /**
     * Constructor por defecto.
     */
    public DescuentoTiempoNoLaboradoControlador() {
        super();
    }

    @Override
    @PostConstruct
    public void init() {
        descuentoTiempoNoLaboradoHelper.iniciador();
        limpiarFormulario();
    }

    /**
     * INCIACIALIZA LAS VARIABLES ASOCIADAS A LA PLANIFICACION DE VACACIONES
     *
     */
    public void limpiarFormulario() {
        limpiarPanelDatosServidor();
        if (descuentoTiempoNoLaboradoHelper.getBuscarServidorPor() == null) {
            descuentoTiempoNoLaboradoHelper.setApellidosNombres(null);
            descuentoTiempoNoLaboradoHelper.setTipoIdentificacion(null);
            descuentoTiempoNoLaboradoHelper.setNroIdentificacion(null);
        } else if (descuentoTiempoNoLaboradoHelper.getBuscarServidorPor().equals("id")) {
            descuentoTiempoNoLaboradoHelper.setApellidosNombres(null);
        } else {
            descuentoTiempoNoLaboradoHelper.setTipoIdentificacion(null);
            descuentoTiempoNoLaboradoHelper.setNroIdentificacion(null);
        }
        iniciarComboTipoIdentificacion();
    }

    /**
     * LIMPIA EL PANEL DE LOS DATOS DEL SERVIDOR
     */
    public void limpiarPanelDatosServidor() {
        descuentoTiempoNoLaboradoHelper.setInstitucionEjercicioFiscal(null);
        descuentoTiempoNoLaboradoHelper.setBotonDescontar(false);
        descuentoTiempoNoLaboradoHelper.getListaDistributivosDetalles().clear();
        descuentoTiempoNoLaboradoHelper.setDistributivoDetalle(null);
        descuentoTiempoNoLaboradoHelper.setSaldoVacacion(0L);
        descuentoTiempoNoLaboradoHelper.setSaldoVacacionTexto(null);
        descuentoTiempoNoLaboradoHelper.setSaldoVacacionProporcional(0L);
        descuentoTiempoNoLaboradoHelper.setSaldoVacacionProporcionalTexto(null);
    }

    /**
     * LIMPIA EL CAMPO NRO IDENTIFICACION
     */
    public void limpiarNroIdentificacion() {
        descuentoTiempoNoLaboradoHelper.setNroIdentificacion(null);
        limpiarPanelDatosServidor();
    }

    /**
     * GUARDA LA PLANIFICACION DE VACACIONES PARA EL SERVIDOR SELECCIONADO
     *
     * @return
     */
    public String descontarTiempoNoLaborado() {
        try {
            Long descuento = descuentoTiempoNoLaboradoHelper.getMinutosADescontar();

            Servidor s = descuentoTiempoNoLaboradoHelper.getDistributivoDetalle().getServidor();
            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                    obtenerUsuarioConectado().getInstitucion().getId(), s.getId());

            Vacacion v = vacacionDao.buscarPorServidor(si.getId());

            String[] msj = vacacionServicio.realizarDescuentoTiempoNoLaborado(
                    v.getId(), descuento, obtenerUsuarioConectado());

            if (msj[0].equals("ERROR")) {
                mostrarMensajeEnPantalla(msj[1], FacesMessage.SEVERITY_ERROR);
            } else if (msj[0].equals("INFO")) {
                mostrarMensajeEnPantalla(msj[1], FacesMessage.SEVERITY_INFO);
                seleccionarServidorYrecuperarDatosVacaciones(false);
                ejecutarComandoPrimefaces("dlgDatosDescuento.hide();");
            }

        } catch (Exception e) {
            e.printStackTrace();
            error(getClass().getName(), "Error al intentar realizar el descuento.", e);
        }
        return null;
    }

    /**
     * LLENA LA LISTA DE SELECCION DE TIPO DE IDENTIFICACION EN EL FORMULARIO DE
     * BUSQUEDA
     */
    private void iniciarComboTipoIdentificacion() {
        iniciarCombos(descuentoTiempoNoLaboradoHelper.getOpcionesTipoIdentificacion());
        for (TipoDocumentoEnum tDocId : TipoDocumentoEnum.values()) {
            if (!(tDocId.getNemonico().equals(TipoDocumentoEnum.RUC.getNemonico()))) {
                descuentoTiempoNoLaboradoHelper.getOpcionesTipoIdentificacion().add(
                        new SelectItem(tDocId.getNemonico(), tDocId.getNombre()));
            }
        }
    }

    /**
     * BUSCAR SERVIDOR Y SUS DATOS DE SALDOS DE VACACIONES
     *
     * @return String
     */
    public String recuperarDatosServidor() {
        try {
            limpiarFormulario();
            descuentoTiempoNoLaboradoHelper.getListaDistributivosDetalles().clear();
            BusquedaServidorVO bServidor = new BusquedaServidorVO();

            if (descuentoTiempoNoLaboradoHelper.getTipoIdentificacion() != null
                    && descuentoTiempoNoLaboradoHelper.getNroIdentificacion() != null) {

                bServidor.setTipoDocumentoServidor(
                        descuentoTiempoNoLaboradoHelper.getTipoIdentificacion());
                bServidor.setNumeroDocumentoServidor(
                        descuentoTiempoNoLaboradoHelper.getNroIdentificacion());

            } else if (descuentoTiempoNoLaboradoHelper.getApellidosNombres() != null) {
                bServidor.setNombreServidor(
                        descuentoTiempoNoLaboradoHelper.getApellidosNombres());
            }

            descuentoTiempoNoLaboradoHelper
                    .setListaDistributivosDetalles(servidorServicio.buscar(bServidor));

            ejecutarComandoPrimefaces("dlgResultadosBusqueda.show();");

        } catch (Exception e) {
            e.printStackTrace();
            error(getClass().getName(), "Error recuperando los datos de saldo de vacaciones del servidor", e);
        }

        return null;
    }

    /**
     * SELECCIONAR SERVIDOR DE LA LISTA DE RESULTADOS DE LA BUSQUEDA POR NOMBRES
     * Y APELLDIOS Y MANDA A BSUCAR LOS DATOS DE SU PALNIFICACION DE VACACIONES
     * @param mostrarMsj 
     * @return
     */
    public String seleccionarServidorYrecuperarDatosVacaciones(boolean mostrarMsj) {
        if (recuperarDatosVacaciones()) {
            descuentoTiempoNoLaboradoHelper.setInstitucionEjercicioFiscal(
                    obtenerUsuarioConectado().getInstitucionEjercicioFiscal());
            if (mostrarMsj) {
                mostrarMensajeEnPantalla(
                        "Registro cargado satisfactoriamente", FacesMessage.SEVERITY_INFO);
            }
        }
        ejecutarComandoPrimefaces("dlgResultadosBusqueda.hide();");
        return null;
    }

    /**
     * RECUPERA LOS DATOS DE SALDOS VACACIONES ASOCIADA AL SERVIDOR
     *
     * @return Boolean
     */
    private Boolean recuperarDatosVacaciones() {
        try {
            DistributivoDetalle dd = descuentoTiempoNoLaboradoHelper.getDistributivoDetalle();
            if (dd != null) {
                if (verificadoServidorPerteneceAMismaUnidadOrg(dd)) {
                    descuentoTiempoNoLaboradoHelper.setBotonDescontar(Boolean.TRUE);
                    obtenerSaldosDeVacacionesEfectivasYProporcionales();
                    return Boolean.TRUE;

                } else {
                    limpiarFormulario();
                    mostrarMensajeEnPantalla(
                            "El servidor seleccionado no pertenence a su Unidad Organizacional",
                            FacesMessage.SEVERITY_ERROR);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensajeEnPantalla(
                    "Error al intentar recuperar los datos de vacaciones del servidor",
                    FacesMessage.SEVERITY_ERROR);
        }

        return Boolean.FALSE;
    }

    /**
     * Se obtiene el saldo de vacaciones pendientes, tanto efectivas como
     * proporcionales asociadas al servidor
     *
     */
    public void obtenerSaldosDeVacacionesEfectivasYProporcionales() {
        try {
            descuentoTiempoNoLaboradoHelper.setSaldoVacacion(0l);
            Servidor s = descuentoTiempoNoLaboradoHelper.getDistributivoDetalle().getServidor();
            ServidorInstitucion si = servidorInstitucionDao.buscarPorInstitucionServidor(
                    obtenerUsuarioConectado().getInstitucion().getId(), s.getId());

            Vacacion v = vacacionServicio.buscarVacacion(si.getId());

            if (v != null) {
                Integer[] saldo = UtilFechas.convertirMinutosA_ddHHmm(Math.abs(v.getSaldo()), s.getJornada());
                descuentoTiempoNoLaboradoHelper.setSaldoVacacionTexto(
                        UtilCadena.concatenar(saldo[0], " Días, ", saldo[1], " Horas, " + saldo[2], " Minutos"));
                descuentoTiempoNoLaboradoHelper.setSaldoVacacion(v.getSaldo());

                Integer[] saldoProporcional = UtilFechas.convertirMinutosA_ddHHmm(
                        Math.abs(v.getSaldoProporcional()), s.getJornada());
                descuentoTiempoNoLaboradoHelper.setSaldoVacacionProporcionalTexto(UtilCadena.concatenar(
                        saldoProporcional[0], " Días, ", saldoProporcional[1],
                        " Horas, " + saldoProporcional[2], " Minutos"));
                descuentoTiempoNoLaboradoHelper.setSaldoVacacionProporcional(v.getSaldoProporcional());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vierifica si un servidor pertenece a la misma unidad organizacional que
     * el usuario conectado
     *
     * @param distributiboDetalleServidor datos del puesto del servidor que
     * contiene la unidad organizacional
     * @return
     */
    private Boolean verificadoServidorPerteneceAMismaUnidadOrg(DistributivoDetalle distributiboDetalleServidor) {
        try {
            List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(),
                    FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            for (UnidadOrganizacional unidad : unidades) {
                if (unidad.getCodigo().equals(
                        distributiboDetalleServidor.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                    return Boolean.TRUE;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Boolean.FALSE;
    }

    public void iniciarDialogoDescuento() {
        descuentoTiempoNoLaboradoHelper.setMinutosADescontar(0l);
        ejecutarComandoPrimefaces("dlgDatosDescuento.show();");
    }

    /**
     * Permite Regresar
     *
     * @return
     */
    public String salir() {
        return PAGINA_PORTAL;
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
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacion(final String codigo) {
        return EstadoPlanVacacionEnum.obtenerDescripcion(codigo);
    }

    /**
     * Este metodo transacciona la busqueda de la descripcion del estado del
     * registro
     *
     * @param codigo String
     * @return String
     */
    public final String obtenerDescripcionEstadoPlanifVacacionDetalle(final String codigo) {
        return EstadoVacacionDetalleEnum.obtenerDescripcion(codigo);
    }

    /**
     *
     * @return
     */
    public DescuentoTiempoNoLaboradoHelper getDescuentoTiempoNoLaboradoHelper() {
        return descuentoTiempoNoLaboradoHelper;
    }

    /**
     *
     * @param descuentoTiempoNoLaboradoHelper
     */
    public void setDescuentoTiempoNoLaboradoHelper(
            DescuentoTiempoNoLaboradoHelper descuentoTiempoNoLaboradoHelper) {
        this.descuentoTiempoNoLaboradoHelper = descuentoTiempoNoLaboradoHelper;
    }
}
