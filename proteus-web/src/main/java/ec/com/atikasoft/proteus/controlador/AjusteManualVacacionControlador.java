/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.controlador;

import ec.com.atikasoft.proteus.controlador.base.BaseControlador;
import ec.com.atikasoft.proteus.controlador.helper.AjusteManualVacacionHelper;
import ec.com.atikasoft.proteus.dao.TrayectoriaLaboralDao;
import ec.com.atikasoft.proteus.dao.UnidadOrganizacionalDao;
import ec.com.atikasoft.proteus.enums.FuncionesDesconcentradosEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.TipoAcumulacionEnum;
import ec.com.atikasoft.proteus.enums.TipoDocumentoEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.CertificacionPresupuestaria;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.TrayectoriaLaboral;
import ec.com.atikasoft.proteus.modelo.UnidadOrganizacional;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.modelo.VacacionParametro;
import ec.com.atikasoft.proteus.servicio.DesconcentradoServicio;
import ec.com.atikasoft.proteus.servicio.DistributivoDetalleServicio;
import ec.com.atikasoft.proteus.servicio.ServidorServicio;
import ec.com.atikasoft.proteus.servicio.VacacionServicio;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "ajusteManualVacacionBean")
@ViewScoped
public class AjusteManualVacacionControlador extends BaseControlador {

    /**
     * Helper de clase.
     */
    @ManagedProperty("#{ajusteManualVacacionHelper}")
    private AjusteManualVacacionHelper ajusteManualVacacionHelper;

    /**
     *
     */
    @EJB
    private ServidorServicio servidorServicio;

    /**
     *
     */
    @EJB
    private UnidadOrganizacionalDao unidadOrganizacionalDao;

    /**
     *
     */
    @EJB
    private TrayectoriaLaboralDao trayectoriaLaboralDao;

    /**
     *
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;

    /**
     *
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     *
     */
    @EJB
    private DesconcentradoServicio desconcentradoServicio;

    @Override
    @PostConstruct
    public void init() {
        iniciarComboTipo();
        ajusteManualVacacionHelper.setTipoIdentificacion("");
        ajusteManualVacacionHelper.setNumeroIdentificacion("");
    }

    /**
     * combo del tipo de documento.
     */
    private void iniciarComboTipo() {
        iniciarCombos(ajusteManualVacacionHelper.getTipoDocumento());
        ajusteManualVacacionHelper.getTipoDocumento().add(new SelectItem(TipoDocumentoEnum.CEDULA.getNemonico(),
                TipoDocumentoEnum.CEDULA.getNombre()));
        ajusteManualVacacionHelper.getTipoDocumento().add(new SelectItem(
                TipoDocumentoEnum.PASAPORTE.getNemonico(),
                TipoDocumentoEnum.PASAPORTE.getNombre()));
    }

    /**
     * Renderiza y Habilita o deshabilita el campo número de documento.
     */
    public void visualizacionYHabilitacionCampoDocumento() {
        if (ajusteManualVacacionHelper.getTipoIdentificacion() != null && !ajusteManualVacacionHelper.getTipoIdentificacion().isEmpty()) {
            ajusteManualVacacionHelper.setTipoCedulaSeleccionado(Boolean.TRUE);
            if (ajusteManualVacacionHelper.getTipoIdentificacion().equals(TipoDocumentoEnum.CEDULA.getNemonico())) {
                ajusteManualVacacionHelper.setVisualizarInputCedula(Boolean.TRUE);
                ajusteManualVacacionHelper.setVisualizarInputPasaporte(Boolean.FALSE);
            } else {

                ajusteManualVacacionHelper.setVisualizarInputCedula(Boolean.FALSE);
                ajusteManualVacacionHelper.setVisualizarInputPasaporte(Boolean.TRUE);
            }
        } else {
            ajusteManualVacacionHelper.setVisualizarInputCedula(Boolean.FALSE);
            ajusteManualVacacionHelper.setVisualizarInputPasaporte(Boolean.FALSE);
            ajusteManualVacacionHelper.setTipoCedulaSeleccionado(Boolean.FALSE);
        }
    }

    /**
     * Habilita o deshabilita el campo número de documento.
     */
    /*public void validarCampoDisable() {
     if (ajusteManualVacacionHelper.getTipoIdentificacion() != null && !ajusteManualVacacionHelper.getTipoIdentificacion().isEmpty()) {
     ajusteManualVacacionHelper.setTipoCedulaSeleccionado(Boolean.TRUE);
     } else {
     ajusteManualVacacionHelper.setTipoCedulaSeleccionado(Boolean.FALSE);
     }
     }*/
    /**
     * Determina cuando visualizar el campo número de cédula o número de pasaporte.
     *
     * @return
     */
    /*public boolean visualizarCampoCedula() {
     return ajusteManualVacacionHelper.getTipoIdentificacion() != null && !ajusteManualVacacionHelper.getTipoIdentificacion().isEmpty()
     && ajusteManualVacacionHelper.getTipoIdentificacion().equals(TipoDocumentoEnum.CEDULA.getNemonico());
     }*/
    /**
     *
     * @return
     */
    public AjusteManualVacacionHelper getAjusteManualVacacionHelper() {
        return ajusteManualVacacionHelper;
    }

    /**
     *
     * @param ajusteManualVacacionHelper
     */
    public void setAjusteManualVacacionHelper(AjusteManualVacacionHelper ajusteManualVacacionHelper) {
        this.ajusteManualVacacionHelper = ajusteManualVacacionHelper;
    }

    /**
     * Borra los campos en pantalla al cambiar de tab
     */
    public void limpiarTab() {
        ajusteManualVacacionHelper.setTipoIdentificacion(null);
        ajusteManualVacacionHelper.setTipoCedulaSeleccionado(Boolean.FALSE);
        ajusteManualVacacionHelper.setVisualizarInputCedula(Boolean.FALSE);
        ajusteManualVacacionHelper.setVisualizarInputPasaporte(Boolean.FALSE);
        ajusteManualVacacionHelper.setNumeroIdentificacion(null);
        ajusteManualVacacionHelper.setFiltroNombre(null);
        /*ajusteManualVacacionHelper.setServidorSeleccionado(null);
         ajusteManualVacacionHelper.setServidorVacacion(null);
         ajusteManualVacacionHelper.setTieneVacacion(false);*/
        limpiarSaldos();
        limpiarDatosServidor();
        //actualizarComponente("frmPrincipal:btnGuardar");
        actualizarComponente("frmPrincipal:tabViewId:pnlBusquedaNombre");
        actualizarComponente("frmPrincipal:tabViewId:pnlGridBusquedaIdentificacion");
    }

    /**
     * Borra en pantalla datos del servidor
     */
    public void limpiarDatosServidor() {
        ajusteManualVacacionHelper.setServidorSeleccionado(null);
        ajusteManualVacacionHelper.setServidorVacacion(null);
        ajusteManualVacacionHelper.setTieneVacacion(false);
        actualizarComponente("frmPrincipal:btnGuardar");
    }

    /**
     * Borra en pantalla los saldos
     */
    public void limpiarSaldos() {
        ajusteManualVacacionHelper.setSaldoEfectivoEnPalabras("");
        ajusteManualVacacionHelper.setSaldoProporcionalEnPalabras("");
        ajusteManualVacacionHelper.setSaldoPerdidoEnPalabras("");
        actualizarComponente("frmPrincipal:pnlInformacionObtenida");
        ajusteManualVacacionHelper.setSaldoEfectivoEnDias(0);
        ajusteManualVacacionHelper.setSaldoProporcionalEnDias(0);
        ajusteManualVacacionHelper.setSaldoPerdidoEnDias(0);
        limpiarAjustesSaldosJustificacion();
    }

    /**
     * Borra en pantalla los saldos de ajuste manual.
     */
    public void limpiarAjustesSaldosJustificacion() {
        ajusteManualVacacionHelper.setSaldoEfectivoJustificacion("");
        ajusteManualVacacionHelper.setSaldoProporcionalJustificacion("");
        ajusteManualVacacionHelper.setSaldoPerdidoJustificacion("");
//        ajusteManualVacacionHelper.setSaldoEfectivoEnDias(0);
//        ajusteManualVacacionHelper.setSaldoProporcionalEnDias(0);
//        ajusteManualVacacionHelper.setSaldoPerdidoEnDias(0);
        actualizarComponente("frmPrincipal:pnlAjustesSaldos");
    }

    /**
     * metodo para buscar un servidor segun su nombre (solo servidores con puesto asignado y de las unidades
     * organizacionales especificadas
     *
     *
     * @return
     */
    public String buscarPorNombre() {
        try {
            ajusteManualVacacionHelper.getServidoresEncontrados().clear();
            if (ajusteManualVacacionHelper.getFiltroNombre() != null
                    && !ajusteManualVacacionHelper.getFiltroNombre().trim().isEmpty()) {
                List<UnidadOrganizacional> unidades = desconcentradoServicio.buscarUnidadesDeAcceso(
                        obtenerUsuarioConectado().getServidor().getId(),
                        FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
                StringBuilder codigosUnidades = new StringBuilder();
                for (UnidadOrganizacional uo : unidades) {
                    codigosUnidades.append(uo.getCodigo()).append(",");
                }
                codigosUnidades.deleteCharAt(codigosUnidades.length() - 1);

                List<Servidor> servidores = servidorServicio.buscarServidorConPuestoPorNombreYUnidadOrganizacional(
                        ajusteManualVacacionHelper.getFiltroNombre(), codigosUnidades.toString());
                if (servidores == null) {
                    mostrarMensajeEnPantalla("No existen servidores con el nombre ingresado", FacesMessage.SEVERITY_INFO);
                } else {
                    ajusteManualVacacionHelper.setServidoresEncontrados(servidores);
                    ejecutarComandoPrimefaces("dlgResultadosNombre.show();");
                }
            } else {
                mostrarMensajeEnPantalla("No ha ingresado el nombre por el que desea realizar la búsqueda",
                        FacesMessage.SEVERITY_INFO);
            }

        } catch (Exception ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * metodo para buscar un servidor por identificación.
     *
     * @return
     */
    public String buscarPorCedula() {
        try {
            if (ajusteManualVacacionHelper.getNumeroIdentificacion() != null
                    && !ajusteManualVacacionHelper.getNumeroIdentificacion().trim().isEmpty()) {
                if (ajusteManualVacacionHelper.getTipoIdentificacion() == null) {
                    mostrarMensajeEnPantalla("No ha seleccionado el tipo de identificación",
                            FacesMessage.SEVERITY_WARN);
                } else if (ajusteManualVacacionHelper.getTipoIdentificacion().equals(
                        TipoDocumentoEnum.CEDULA.getNemonico())
                        && ajusteManualVacacionHelper.getNumeroIdentificacion().length() != 10) {
                    mostrarMensajeEnPantalla("La cédula debe ser un número de 10 dígitos", FacesMessage.SEVERITY_INFO);
                } else if (ajusteManualVacacionHelper.getTipoIdentificacion().equals(
                        TipoDocumentoEnum.CEDULA.getNemonico())
                        || ajusteManualVacacionHelper.getTipoIdentificacion().equals(
                                TipoDocumentoEnum.PASAPORTE.getNemonico())) {
                    Servidor servidorCandidato = servidorServicio.buscarServidor(
                            ajusteManualVacacionHelper.getTipoIdentificacion(),
                            ajusteManualVacacionHelper.getNumeroIdentificacion());

                    if (servidorCandidato == null) {
                        mostrarMensajeEnPantalla("No existe servidor con la identificación ingresada",
                                FacesMessage.SEVERITY_INFO);
                    } else {
                        verificarDistributivoDetalleServidor(servidorCandidato);
                        if (ajusteManualVacacionHelper.getServidorSeleccionado() != null) {
                            recuperarNombreTipoDocumento();
                            buscarVacacion();
                        }
                    }
                }

            } else {
                mostrarMensajeEnPantalla("No ha ingresado el número de identificación", FacesMessage.SEVERITY_WARN);
            }

        } catch (Exception ex) {
            Logger.getLogger(ConsultaTrayectoriaLaboralControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Busqueda de servidor segun filtros.
     */
    public void buscar() {
        limpiarDatosServidor();
        limpiarSaldos();
        if (ajusteManualVacacionHelper.getTabViewIndex() == 1) {
            buscarPorNombre();
        } else {
            buscarPorCedula();
            obtenerMaximosVacaciones();
        }
    }

    /**
     * Busca datos de vacacion del servidor seleccionado.
     */
    public void buscarVacacion() {
        try {
            ajusteManualVacacionHelper.setServidorVacacion(
                    vacacionServicio.buscarVacacionPorServidorId(
                            ajusteManualVacacionHelper.getServidorSeleccionado().getId()));

            if (ajusteManualVacacionHelper.getServidorVacacion() != null) {
                ajusteManualVacacionHelper.setTieneVacacion(true);

                actualizarSaldosEnPalabras();

                ajusteManualVacacionHelper.setSaldoEfectivoEnDias(UtilFechas.convertirMinutosEnDias(ajusteManualVacacionHelper.getServidorVacacion().getSaldo(),
                        ajusteManualVacacionHelper.getServidorSeleccionado().getJornada()));
                ajusteManualVacacionHelper.setSaldoProporcionalEnDias(UtilFechas.convertirMinutosEnDias(ajusteManualVacacionHelper.getServidorVacacion().getSaldoProporcional(),
                        ajusteManualVacacionHelper.getServidorSeleccionado().getJornada()));
                ajusteManualVacacionHelper.setSaldoPerdidoEnDias(UtilFechas.convertirMinutosEnDias(ajusteManualVacacionHelper.getServidorVacacion().getSaldoPerdida(),
                        ajusteManualVacacionHelper.getServidorSeleccionado().getJornada()));

            } else {
                mostrarMensajeEnPantalla("El servidor seleccionado no tiene vacaciones registradas", FacesMessage.SEVERITY_INFO);
                ajusteManualVacacionHelper.setTieneVacacion(false);
            }

        } catch (DaoException ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Completa los datos faltantes para mostrar.
     */
    public void procesarSeleccion() {
        recuperarNombreTipoDocumento();
        try {
            ajusteManualVacacionHelper.getServidorSeleccionado().setDistributivoDetalle(distributivoDetalleServicio.buscar(ajusteManualVacacionHelper.getServidorSeleccionado().getId()));
            buscarVacacion();
            obtenerMaximosVacaciones();
        } catch (ServicioException ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Busca nombre de tipo de documento del servidor.
     */
    public void recuperarNombreTipoDocumento() {
        ajusteManualVacacionHelper.getServidorSeleccionado().setNombreTipoIdentificacion(
                ajusteManualVacacionHelper.getServidorSeleccionado().getTipoIdentificacion().equalsIgnoreCase("C")
                        ? TipoDocumentoEnum.CEDULA.getNombre() : TipoDocumentoEnum.PASAPORTE.getNombre());
    }

    /**
     * Obtiene cantidad máxima de dias de vacaciones según regimen laboral del sevidor.
     */
    public void obtenerMaximosVacaciones() {
        if (ajusteManualVacacionHelper.getServidorSeleccionado() != null) {
            Long idRegimen = ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle().
                    getEscalaOcupacional().getNivelOcupacional().getIdRegimenLaboral();
            try {
                VacacionParametro vp = vacacionServicio.listarTodosVacacionParametroPorRegimenLaboral(idRegimen).get(0);
                if (vp.getTipoAcumulacion().equalsIgnoreCase(TipoAcumulacionEnum.PERIODOS.getCodigo())) {
                    ajusteManualVacacionHelper.setMaxVacaciones(vp.getDiasEnPeriodo());
                } else {
                    ajusteManualVacacionHelper.setMaxVacaciones(vp.getMaximoAcumulacion());
                }
                ajusteManualVacacionHelper.setMaxVacacionesProporcional(vp.getMaximoAcumulacionProporcionales());

            } catch (ServicioException ex) {
                Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Verifica que el servidor tenga asignado puesto y pertenezca a la unidad organizacional agrupadora del usuario
     * conectado
     *
     * @param servidorCandidato
     */
    private void verificarDistributivoDetalleServidor(Servidor servidorCandidato) {
        try {
            DistributivoDetalle dd = distributivoDetalleServicio.buscar(servidorCandidato.getId());
            if (dd == null) {
                mostrarMensajeEnPantalla("El servidor indicado no está asociado a un puesto", FacesMessage.SEVERITY_INFO);
                ajusteManualVacacionHelper.setServidorSeleccionado(null);
            } else if (verificarUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getCodigo())) {
                ajusteManualVacacionHelper.setServidorSeleccionado(servidorCandidato);
                ajusteManualVacacionHelper.getServidorSeleccionado().setDistributivoDetalle(dd);
            } else {
                mostrarMensajeEnPantalla("El servidor seleccionado no pertenece a la dependencia "
                        + "del usuario conectado", FacesMessage.SEVERITY_INFO);
            }
        } catch (ServicioException ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifica que la unidad organizacional del usuario pertenezca a la unidad agrupadora correspondiente al usuario
     * conectado
     *
     * @param codigo
     * @return
     */
    private boolean verificarUnidadOrganizacional(String codigo) {
        try {
            List<UnidadOrganizacional> unidadesAcceso = desconcentradoServicio.buscarUnidadesDeAcceso(
                    obtenerUsuarioConectado().getServidor().getId(), FuncionesDesconcentradosEnum.VACACIONES.getCodigo());
            boolean existe = false;
            for (UnidadOrganizacional uo : unidadesAcceso) {
                if (uo.getCodigo().equals(codigo)) {
                    existe = true;
                    break;
                }
            }
            return existe;
        } catch (ServicioException ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Procesa validaciones necesarias para guardar los ajustes realizados
     */
    public void iniciarGuardar() {
        long diferenciaSaldoEfectivo = UtilFechas.convertirDiasEnMinutos(ajusteManualVacacionHelper.
                getSaldoEfectivoEnDias()) - ajusteManualVacacionHelper.getServidorVacacion().getSaldo();
        long diferenciaSaldoProporcional = UtilFechas.convertirDiasEnMinutos(ajusteManualVacacionHelper.
                getSaldoProporcionalEnDias()) - ajusteManualVacacionHelper.getServidorVacacion().getSaldoProporcional();
        long diferenciaSaldoPerdido = UtilFechas.convertirDiasEnMinutos(ajusteManualVacacionHelper.
                getSaldoPerdidoEnDias()) - ajusteManualVacacionHelper.getServidorVacacion().getSaldoPerdida();
        boolean permitirGuardar = diferenciaSaldoEfectivo != 0 || diferenciaSaldoProporcional != 0
                || diferenciaSaldoPerdido != 0;
        if (permitirGuardar) {
            if (diferenciaSaldoEfectivo != 0) {
                if (!(ajusteManualVacacionHelper.getSaldoEfectivoJustificacion() != null
                        && !ajusteManualVacacionHelper.getSaldoEfectivoJustificacion().trim().isEmpty())) {
                    permitirGuardar = false;
                    mostrarMensajeEnPantalla("Debe indicar una justificación para el ajuste de Saldo Vacaciones",
                            FacesMessage.SEVERITY_WARN);
                }
                if (ajusteManualVacacionHelper.getSaldoEfectivoEnDias() < 0 || ajusteManualVacacionHelper.
                        getSaldoEfectivoEnDias() > ajusteManualVacacionHelper.getMaxVacaciones()) {
                    permitirGuardar = false;
                    mostrarMensajeEnPantalla("La cantidad de días de vacaciones según regimen laboral "
                            + ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle()
                            .getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre()
                            + " es entre 0 y " + (int) ajusteManualVacacionHelper.getMaxVacaciones(), FacesMessage.SEVERITY_WARN);
                }
            }
            if (diferenciaSaldoProporcional != 0) {
                if (!(ajusteManualVacacionHelper.getSaldoProporcionalJustificacion() != null
                        && !ajusteManualVacacionHelper.getSaldoProporcionalJustificacion().trim().isEmpty())) {
                    permitirGuardar = false;
                    mostrarMensajeEnPantalla("Debe indicar una justificación para el ajuste de Saldo Proporcional",
                            FacesMessage.SEVERITY_WARN);
                }
                if (ajusteManualVacacionHelper.getSaldoProporcionalEnDias() < 0 || ajusteManualVacacionHelper.
                        getSaldoProporcionalEnDias() > ajusteManualVacacionHelper.getMaxVacacionesProporcional()) {
                    permitirGuardar = false;
                    mostrarMensajeEnPantalla("La cantidad de días de vacaciones proporcional según regimen laboral "
                            + ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle()
                            .getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre()
                            + " es entre 0 y " + (int) ajusteManualVacacionHelper.getMaxVacacionesProporcional(),
                            FacesMessage.SEVERITY_WARN);
                }
            }
            if (diferenciaSaldoPerdido != 0) {
                if (!(ajusteManualVacacionHelper.getSaldoPerdidoJustificacion() != null
                        && !ajusteManualVacacionHelper.getSaldoPerdidoJustificacion().trim().isEmpty())) {
                    permitirGuardar = false;
                    mostrarMensajeEnPantalla("Debe indicar una justificación para el ajuste de Días Perdidos",
                            FacesMessage.SEVERITY_WARN);
                }
            }
        } else {
            mostrarMensajeEnPantalla("No ha realizado ajuste alguno en los saldos", FacesMessage.SEVERITY_INFO);
        }
        if (permitirGuardar) {
            ejecutarComandoPrimefaces("confirmDlgWV.show();");
        }
    }

    /**
     * Guarda en la base de datos los ajustes realizados
     */
    public void guardar() {
        try {
            List<VacacionDetalle> detalles = new ArrayList<>();
            long diferenciaSaldoEfectivo = UtilFechas.convertirDiasEnMinutos(
                    ajusteManualVacacionHelper.getSaldoEfectivoEnDias()) - ajusteManualVacacionHelper.getServidorVacacion().getSaldo();
            long diferenciaSaldoProporcional = UtilFechas.convertirDiasEnMinutos(
                    ajusteManualVacacionHelper.getSaldoProporcionalEnDias())
                    - ajusteManualVacacionHelper.getServidorVacacion().getSaldoProporcional();
            long diferenciaSaldoPerdido = UtilFechas.convertirDiasEnMinutos(
                    ajusteManualVacacionHelper.getSaldoPerdidoEnDias())
                    - ajusteManualVacacionHelper.getServidorVacacion().getSaldoPerdida();
            if (diferenciaSaldoEfectivo != 0) {
                VacacionDetalle detalleEfectivo = new VacacionDetalle();
                detalleEfectivo.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
                detalleEfectivo.setCredito(diferenciaSaldoEfectivo > 0 ? diferenciaSaldoEfectivo : 0);
                detalleEfectivo.setDebito(diferenciaSaldoEfectivo < 0 ? diferenciaSaldoEfectivo * (-1) : 0);
                detalleEfectivo.setUsuarioCreacion(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                detalleEfectivo.setFechaCreacion(new Date());
                detalleEfectivo.setObservacion(ajusteManualVacacionHelper.getSaldoEfectivoJustificacion());
                detalleEfectivo.setVigente(Boolean.TRUE);
                detalleEfectivo.setEsAjusteManual(Boolean.TRUE);
                detalleEfectivo.setFechaInicio(new Date());
                detalleEfectivo.setFechaFin(new Date());
                detalles.add(detalleEfectivo);

                TrayectoriaLaboral tl = new TrayectoriaLaboral();
                tl.setExplicacion(ajusteManualVacacionHelper.getSaldoEfectivoJustificacion());
                afectarTrayectoriaLaboral(tl, ajusteManualVacacionHelper.getServidorVacacion());
            }

            if (diferenciaSaldoProporcional != 0) {
                VacacionDetalle detalleProporcional = new VacacionDetalle();
                detalleProporcional.setTipo(TipoVacacionDetalleEnum.PROPORCIONAL.getCodigo());
                detalleProporcional.setCredito(diferenciaSaldoProporcional > 0 ? diferenciaSaldoProporcional : 0);
                detalleProporcional.setDebito(diferenciaSaldoProporcional < 0 ? diferenciaSaldoProporcional * (-1) : 0);
                detalleProporcional.setUsuarioCreacion(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                detalleProporcional.setFechaCreacion(new Date());
                detalleProporcional.setObservacion(ajusteManualVacacionHelper.getSaldoProporcionalJustificacion());
                detalleProporcional.setVigente(Boolean.TRUE);
                detalleProporcional.setEsAjusteManual(Boolean.TRUE);
                detalleProporcional.setFechaInicio(new Date());
                detalleProporcional.setFechaFin(new Date());
                detalles.add(detalleProporcional);

                TrayectoriaLaboral tl = new TrayectoriaLaboral();
                tl.setExplicacion(ajusteManualVacacionHelper.getSaldoProporcionalJustificacion());
                afectarTrayectoriaLaboral(tl, ajusteManualVacacionHelper.getServidorVacacion());
            }

            if (diferenciaSaldoPerdido != 0) {
                VacacionDetalle detallePerdido = new VacacionDetalle();
                detallePerdido.setTipo(TipoVacacionDetalleEnum.PERDIDA.getCodigo());
                detallePerdido.setCredito(diferenciaSaldoPerdido > 0 ? diferenciaSaldoPerdido : 0);
                detallePerdido.setDebito(diferenciaSaldoPerdido < 0 ? diferenciaSaldoPerdido * (-1) : 0);
                detallePerdido.setUsuarioCreacion(obtenerUsuarioConectado().getServidor().getNumeroIdentificacion());
                detallePerdido.setFechaCreacion(new Date());
                detallePerdido.setObservacion(ajusteManualVacacionHelper.getSaldoPerdidoJustificacion());
                detallePerdido.setVigente(Boolean.TRUE);
                detallePerdido.setEsAjusteManual(Boolean.TRUE);
                detallePerdido.setFechaInicio(new Date());
                detallePerdido.setFechaFin(new Date());
                detalles.add(detallePerdido);

                TrayectoriaLaboral tl = new TrayectoriaLaboral();
                tl.setExplicacion(ajusteManualVacacionHelper.getSaldoPerdidoJustificacion());
                afectarTrayectoriaLaboral(tl, ajusteManualVacacionHelper.getServidorVacacion());
            }
            vacacionServicio.guardarAjusteSaldosVacacionYDetalles(
                    ajusteManualVacacionHelper.getServidorVacacion(), detalles);
            mostrarMensajeEnPantalla("Ajustes guardados exitosamente", FacesMessage.SEVERITY_INFO);
            try {
                ajusteManualVacacionHelper.setServidorVacacion(
                        vacacionServicio.buscarVacacionPorServidorId(
                                ajusteManualVacacionHelper.getServidorSeleccionado().getId()));
            } catch (DaoException ex) {
                Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            actualizarSaldosEnPalabras();
            limpiarAjustesSaldosJustificacion();
            //}
            /*} else {
             mostrarMensajeEnPantalla("No ha realizado ajuste alguno en los saldos", FacesMessage.SEVERITY_INFO);
             }*/

        } catch (ServicioException ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza en pantalla los saldos por en días, horas y minutos (en palabras)
     */
    private void actualizarSaldosEnPalabras() {
        ajusteManualVacacionHelper.setSaldoEfectivoEnPalabras(
                UtilFechas.convertirMinutosA_ddHHmmPalabras(ajusteManualVacacionHelper.
                        getServidorVacacion().getSaldo(), 8));
        ajusteManualVacacionHelper.setSaldoProporcionalEnPalabras(
                UtilFechas.convertirMinutosA_ddHHmmPalabras(ajusteManualVacacionHelper.
                        getServidorVacacion().getSaldoProporcional(), 8));
        ajusteManualVacacionHelper.setSaldoPerdidoEnPalabras(
                UtilFechas.convertirMinutosA_ddHHmmPalabras(ajusteManualVacacionHelper.getServidorVacacion().getSaldoPerdida(), 8));
        actualizarComponente("frmPrincipal:pnlInformacionObtenida");
    }

    /**
     * Registra los ajustes realizados en la tabla de trayectoria laboral
     *
     * @param tl
     */
    private void afectarTrayectoriaLaboral(TrayectoriaLaboral tl, Vacacion vacacion) {
        try {
            tl.setGrupo(GrupoEnum.VACACIONES.getCodigo());
            tl.setClase(GrupoEnum.VACACIONES.getCodigo());
            tl.setTipoMovimiento("AJUSTE MANUAL DE VACACIONES");
            tl.setNumeroMovimiento("0000000000");
            tl.setNumeroDocumentoHabilitante("0000000000");
            tl.setNombres(ajusteManualVacacionHelper.getServidorSeleccionado().getNombres());
            tl.setApellidos(ajusteManualVacacionHelper.getServidorSeleccionado().getApellidos());
            tl.setNumeroIdentificacion(ajusteManualVacacionHelper.getServidorSeleccionado().getNumeroIdentificacion());
            tl.setTipoIdentificacion(ajusteManualVacacionHelper.getServidorSeleccionado().getTipoIdentificacion());
            tl.setRmu(ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle().getRmu());
            tl.setRmuSobrevalorado(ajusteManualVacacionHelper.getServidorSeleccionado().
                    getDistributivoDetalle().getRmuSobrevalorado());
            tl.setRegimenLaboral(ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle()
                    .getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getNombre());
            tl.setGrado(ajusteManualVacacionHelper.getServidorSeleccionado().getDistributivoDetalle()
                    .getEscalaOcupacional().getGrado());
            tl.setFechaDesde(UtilFechas.formatear(new Date()));
            tl.setFechaHasta(UtilFechas.formatear(new Date()));
            tl.setVigente(true);
            tl.setUsuarioCreacion(obtenerUsuarioConectado().getUsuario());
            tl.setFechaCreacion(new Date());
            DistributivoDetalle dd = distributivoDetalleServicio.buscar(
                    vacacion.getServidorInstitucion().getServidor().getId());
            tl.setUnidadPresupuestaria(dd.getUnidadPresupuestaria().getNombre());
            tl.setUnidadOrganizacional(dd.getDistributivo().getUnidadOrganizacional().getRuta());
            for (CertificacionPresupuestaria cp
                    : dd.getUnidadPresupuestaria().getListaCertificacionesPresupuestarias()) {
                if (cp.getVigente() && Objects.equals(cp.getModalidadLaboral().getId(),
                        dd.getDistributivo().getModalidadLaboral().getId())) {
                    tl.setCertificacionPresupuestaria(cp.getCertificacionPresupuestaria());
                }
            }
            tl.setDenominacionPuesto(dd.getEscalaOcupacional().getNombre());
            tl.setElaborador(obtenerUsuarioConectado().getServidor().getApellidosNombres());
            tl.setFechaElaborador(new Date());
            tl.setLegalizador(obtenerUsuarioConectado().getServidor().getApellidosNombres());
            tl.setFechaLegalizador(new Date());
            //crear trayectoria en BD
            trayectoriaLaboralDao.crear(tl);
        } catch (Exception ex) {
            Logger.getLogger(AjusteManualVacacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
