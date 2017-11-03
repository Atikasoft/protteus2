/*
 *  ReglaServicio.java
 *  ESIPREN V 2.0 $Revision 1.0 $
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
 *  Nov 14, 2012
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.reglas.validaciones.ValidarMaximoNumeroContratos;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarRevisionAuditoria;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoDisponiblePorServidorLicenciaSinRemuneracion;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarDevengacionesPendientes;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTramitePuestoDuplicado;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoDisponiblePorServidorDestituido;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPluriempleo;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarSancionPecuniariaAdministrativa;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPeriodoMovimientoEnEjercicioFiscal;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarRegistroCivil;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarGanadorConcurso;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTramitePersonaDuplicado;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPrecedenciaTipoMovimiento;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarServidorConNombramientoPermanenteMinimo;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarFechaMaximaMovimiento;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarJubilacionObligatoria;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarOcurrenciaTipoMovimiento;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarServidorConNombramientoPermanente;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoDisponiblePorServidorComisionServicioSinRemuneracion;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarEstarVivo;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoDisponiblePorServidorPeriodoPruebaAscenso;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarAmonestacionEscrita;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTiempoMaximoAcumuladoTipoMovimiento;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoDisponiblePorServidorSuspendido;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarComisionServicio;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoJerarquicoSuperior;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarJubilados;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarJubilacionVoluntaria;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPuestoNombramiento;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarAmonestacionVerbal;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarSuspensionTemporalSinRemuneracion;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarPeriodoMaximoComisionServicios;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTerceraEdad;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarLicenciasYComisionServicioEnCesacion;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Ingreso;
import ec.com.atikasoft.proteus.modelo.Cesacion;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRequisito;
import ec.com.atikasoft.proteus.modelo.Tramite;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoRegla;
import ec.com.atikasoft.proteus.modelo.ReglaBitacora;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Justificacion;
import ec.com.atikasoft.proteus.modelo.Validacion;
import ec.com.atikasoft.proteus.dao.CesacionDao;
import ec.com.atikasoft.proteus.dao.IngresoDao;
import ec.com.atikasoft.proteus.dao.JustificacionDao;
import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.dao.ReglaBitacoraDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoReglaDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoRequisitoDao;
import ec.com.atikasoft.proteus.dao.TramiteDao;
import ec.com.atikasoft.proteus.dao.ValidacionDao;
import ec.com.atikasoft.proteus.enums.CamposConfiguracionEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.HorarioConfiguracionTipoMovimientoEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ReglaException;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarMaximoPeriodoContrato;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTareasAsignadas;
import ec.com.atikasoft.proteus.reglas.validaciones.ValidarTramiteTipoMovimientoDuplicado;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Procede a ejecutar la bateria de reglas definidas.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ReglaServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ReglaServicio.class.getCanonicalName());

    /**
     * Dao de tipo de movimiento x regla.
     */
    @EJB
    private TipoMovimientoReglaDao tipoMovimientoReglaDao;

    /**
     * Dao de tramite.
     */
    @EJB
    private TramiteDao tramiteDao;

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Dao de la bitacora de reglas.
     */
    @EJB
    private ReglaBitacoraDao reglaBitacoraDao;

    /**
     * Dao de ingresos.
     */
    @EJB
    private IngresoDao ingresoDao;

    /**
     * Dao de cesaciones.
     */
    @EJB
    private CesacionDao cesacionDao;

    /**
     * Validar que servidor exista en el registro civil.
     */
    @EJB
    private ValidarRegistroCivil validarRegistradoRegistroCivil;

    /**
     * Valido que un ciudado se encuentre vivo.
     */
    @EJB
    private ValidarEstarVivo validarEstarVivo;

    /**
     * Valida que el servidor no se encuentre en otros tramites.
     */
    @EJB
    private ValidarTramitePersonaDuplicado validarTramitePersonasDuplicadas;

    /**
     * Valida que el puesto no se encuentre en otros tramites.
     */
    @EJB
    private ValidarTramitePuestoDuplicado validarTramitePuestoDuplicado;

    /**
     * Valida el pluriempleo.
     */
    @EJB
    private ValidarPluriempleo validarPluriempleo;

    /**
     * Validacion de jubilados.
     */
    @EJB
    private ValidarJubilados validarJubilados;

    /**
     * Validacion de en revision de auditoria.
     */
    @EJB
    private ValidarRevisionAuditoria validarRevisionAuditoria;

    /**
     * Validar ganador de concurso.
     */
    @EJB
    private ValidarGanadorConcurso validarGanadorConcurso;

    /**
     * Valida que un puesto sea jerarquico superior.
     */
    @EJB
    private ValidarPuestoJerarquicoSuperior validarPuestoJerarquicoSuperior;

    /**
     * Valida que un puesto sea de nombramiento.
     */
    @EJB
    private ValidarPuestoNombramiento validarPuestoNombramiento;

    /**
     * Validad que no sea de tercera edad.
     */
    @EJB
    private ValidarTerceraEdad validarTerceraEdad;

    /**
     * Valida la aminestacion verbal.
     */
    @EJB
    private ValidarAmonestacionVerbal validarAmonestacionVerbal;

    /**
     * Valida la amonestacion escrita.
     */
    @EJB
    private ValidarAmonestacionEscrita validarAmonestacionEscrita;

    /**
     * Valida sancion pecuniaria administrativa.
     */
    @EJB
    private ValidarSancionPecuniariaAdministrativa validarSancionPecuniariaAdministrativa;

    /**
     * Valida suspension temporal sin goce de remuneracion.
     */
    @EJB
    private ValidarSuspensionTemporalSinRemuneracion validarSuspensionTemporalSinRemuneracion;

    /**
     * Validacion de jubilacion voluntaria.
     */
    @EJB
    private ValidarJubilacionVoluntaria validarJubilacionVoluntaria;

    /**
     * Validacion de jubilacion obligatoria.
     */
    @EJB
    private ValidarJubilacionObligatoria validarJubilacionObligatoria;

    /**
     * Validacion del maximo numero de contratos que puede tener una institucion.
     */
    @EJB
    private ValidarMaximoNumeroContratos validarMaximoNumeroContratos;

    /**
     * Valida la ocurrencia de un tipo de movimiento por un servidor.
     */
    @EJB
    private ValidarOcurrenciaTipoMovimiento validarOcurrenciaTipoMovimiento;

    /**
     * Valida que otros tipos de movimientos precedentes se hayan ejecutado.
     *
     */
    @EJB
    private ValidarPrecedenciaTipoMovimiento validarPrecedenciaTipoMovimiento;

    /**
     * Valida que el servidor pueda solicitar hasta el tiempo maximo permitido por tipo de movimiento.
     */
    @EJB
    private ValidarTiempoMaximoAcumuladoTipoMovimiento validarTiempoMaximoTipoMovimiento;

    /**
     * Valida que el servidor no tenga devengaciones pendientes.
     */
    @EJB
    private ValidarDevengacionesPendientes validarDevengacionesPendientes;

    /**
     * Validar que fecha inicio y fin del movimiento se encuentre dentro del ejercicio fiscal.
     */
    @EJB
    private ValidarPeriodoMovimientoEnEjercicioFiscal validarPeriodoMovimientoEnEjercicioFiscal;

    /**
     * Valida el tiempo maximo de la comision de servicio.
     */
    @EJB
    private ValidarPeriodoMaximoComisionServicios validarPeriodoMaximoComisionServicios;

    /**
     * Validaque un servidor se encuentra en comision de servicio.
     */
    @EJB
    private ValidarComisionServicio validarComisionServicio;

    /**
     * Valida que un servidor tenga nombramiento permanente.
     */
    @EJB
    private ValidarServidorConNombramientoPermanente validarServidorConNombramientoPermanente;

    /**
     * Validar que un servidor publico tengo nombramiento permanente durante un minimo de tiempo.
     */
    @EJB
    private ValidarServidorConNombramientoPermanenteMinimo validarServidorConNombramientoPermanenteMinimo;

    /**
     * No se debe permitir el registro del trámite de cesación si la persona registra una Licencia Sin Remuneración,
     * Licencia Con Remuneración, Comisión de Servicios.
     */
    @EJB
    private ValidarLicenciasYComisionServicioEnCesacion validarLicenciasYComisionServicioEnCesacion;

    /**
     * Puesto esta disponible por que servidor que fue suspendido.
     */
    @EJB
    private ValidarPuestoDisponiblePorServidorSuspendido validarPuestoDisponiblePorServidorSuspendido;

    /**
     * Puesto esta disponible por que servidor que fue destituido.
     */
    @EJB
    private ValidarPuestoDisponiblePorServidorDestituido validarPuestoDisponiblePorServidorDestituido;

    /**
     * Puesto está disponible porque servidor tiene licencia sin remuneracion.
     */
    @EJB
    private ValidarPuestoDisponiblePorServidorLicenciaSinRemuneracion validarPuestoDisponiblePorServidorLicenciaSinRemuneracion;

    /**
     * Puesto está disponible porque servidor tiene licencia sin remuneracion.
     */
    @EJB
    private ValidarPuestoDisponiblePorServidorComisionServicioSinRemuneracion validarPuestoDisponiblePorServidorComisionServicioSinRemuneracion;

    /**
     * Puesto esta disponible porque servidor esta en periodo de prueba por ascenso.
     */
    @EJB
    private ValidarPuestoDisponiblePorServidorPeriodoPruebaAscenso validarPuestoDisponiblePorServidorPeriodoPruebaAscenso;

    /**
     * Valida fecha maxima del movimiento.
     */
    @EJB
    private ValidarFechaMaximaMovimiento validarFechaMaximaMovimiento;

    /**
     *
     */
    @EJB
    private ValidarMaximoPeriodoContrato validarMaximoPeriodoContrato;

    /**
     * Dao de validaciones de requisitos.
     */
    @EJB
    private ValidacionDao validacionDao;

    /**
     * Dao de requisitos de un tipo de movimiento.
     */
    @EJB
    private TipoMovimientoRequisitoDao tipoMovimientoRequisitoDao;

    /**
     * Dao de justificacion.
     */
    @EJB
    private JustificacionDao justificacionDao;

    /**
     *
     */
    @EJB
    private ValidarTramiteTipoMovimientoDuplicado validarTramiteTipoMovimientoDuplicado;

    /**
     *
     */
    @EJB
    private ValidarTareasAsignadas validarTareasAsignadas;

    /**
     * Constructor sin argumentos.
     */
    public ReglaServicio() {
        super();
    }

    /**
     * Ejecuta las reglas de validacion para un tramite especifico.
     *
     * @param tramiteId Identificador unico del tramite.
     * @param mensajes Lista de mensajes.
     * @param usuario
     * @param enviar
     * @param parametrosGlobales
     * @return Resultado de la validacion.
     * @throws ReglaException Error de ejecucucion.
     */
    public Boolean ejecutar(final Long tramiteId, final List<ReglaMensaje> mensajes, String usuario,
            final Boolean enviar, final List<ParametroGlobal> parametrosGlobales) throws
            ReglaException {
        LOG.info(UtilCadena.concatenarLog("ReglaEjecucion.ejecutar", "tramiteId=", tramiteId));
        mensajes.clear();
        try {
            Boolean resultado = Boolean.FALSE;
            Tramite tramite = tramiteDao.buscarPorId(tramiteId);
            if (tramite == null) {
                registrarMensaje(UtilCadena.concatenar("Trámite con id =", tramiteId, " no existe"),
                        Boolean.TRUE, mensajes, null, null);
            } else {
                if (tramite.getCatalogoAutoridadNominadora() == null) {
                    registrarMensaje(UtilCadena.concatenar("Debe registrar la autoridad nominadora"),
                            Boolean.TRUE, mensajes, null, null);
                }
                if (tramite.getCatalogoRepresentanteRRHH() == null) {
                    registrarMensaje(UtilCadena.concatenar("Debe registrar el representante de rrhh"),
                            Boolean.TRUE, mensajes, null, null);
                }

                final Map<String, Object> parametros = new HashMap<>();
                List<Movimiento> movimientos = movimientoDao.buscarPorTramite(tramiteId);
                if (movimientos.isEmpty()) {
                    registrarMensaje(UtilCadena.concatenar("Trámite No ", tramite.getNumericoTramite(),
                            " no contiene puestos"), Boolean.TRUE, mensajes, null, null);
                } else {
                    // REVISION DE PUESTOS                
                    for (Movimiento mov : movimientos) {
                        parametros.clear();
                        parametros.put(ReglasParametrosEnum.PUESTO.getCodigo(), mov.getDistributivoDetalle());
                        parametros.put(ReglasParametrosEnum.PARAMETROS_GLOBALES.getCodigo(), parametrosGlobales);
                        parametros.put(ReglasParametrosEnum.MOVIMIENTO.getCodigo(), mov);
                        validarCamposObligatorios(mov, tramite, mensajes, parametrosGlobales);
                        validarFechaRenunciaVoluntaria(mov, mensajes);
                        validarHorarios(mov, mensajes);
                        validarPuestoPropuesto(mov, mensajes);
                        // validar reglas.
                        ejecutar(mov, tramite.getNumericoTramite(), tramite.getTipoMovimiento().getId(),
                                parametros, mensajes, usuario, enviar);
                    }
                }
            }
            if (mensajes.isEmpty()) {
                resultado = Boolean.TRUE;
            }
            return resultado;
        } catch (Exception e) {
            LOG.severe(e.getMessage());
            throw new ReglaException(e);
        }
    }

    /**
     * Realiza la validacion de campos obligatorios.
     *
     * @param mov
     * @param tramite
     * @param mensajes
     */
    private void validarCamposObligatorios(final Movimiento mov, final Tramite tramite,
            final List<ReglaMensaje> mensajes, final List<ParametroGlobal> parametros) throws DaoException {
        // validar campos obligatorios
        List<String> errores = validarCamposObligatorios(mov, tramite, parametros);
        if (!errores.isEmpty()) {
            registrarMensaje(UtilCadena.concatenar("Campos deben ser obligatorios: ", errores.toString()),
                    Boolean.TRUE, mensajes, null, mov);
        }
    }

    /**
     * VALIDAR FECHA DE RENUNCIA VOLUNTARIA , en el tipo de movimiento de Renuncia Voluntaria: La Fecha del campo “Rige
     * a partir de:” no debe ser menor del campo “Fecha Presentó Renuncia Voluntaria”.
     *
     * @param mov
     * @param tramite
     * @param mensajes
     */
    private void validarFechaRenunciaVoluntaria(final Movimiento mov, final List<ReglaMensaje> mensajes) throws
            DaoException {
        if (!mov.getListaCesaciones().isEmpty()) {
            Cesacion c = cesacionDao.buscarPorMovimiento(mov.getId());
            if (c.getFechaPresentaRenuncia() != null && mov.getRigeApartirDe() != null && mov.getRigeApartirDe().
                    getTime() < c.getFechaPresentaRenuncia().getTime()) {
                registrarMensaje(UtilCadena.concatenar(
                        "Fecha de presentación de renuncia debe ser menor a la fecha de rige a partir de. "),
                        Boolean.TRUE, mensajes, null, mov);

            }
        }
    }

    /**
     * Valida que se haya registrado en horarios para los permisos y licencias.
     *
     * @param mov
     * @param mensajes
     */
    private void validarHorarios(final Movimiento mov, final List<ReglaMensaje> mensajes) {
        if (mov.getTramite().getTipoMovimiento().getAreaConfiguracionLicenciasPermisos() && mov.getTramite().
                getTipoMovimiento().getHorario().equals(HorarioConfiguracionTipoMovimientoEnum.SI.getCodigo())) {
            if (mov.getListaLicencias().isEmpty()) {
                registrarMensaje(UtilCadena.concatenar("Falta registrar el horario, no se localizo los datos de la  ",
                        "licencia/permiso a pesar de que si esta configurado en el tipo de movimiento."), Boolean.TRUE,
                        mensajes, null, mov);
            } else {
                Licencia l = mov.getListaLicencias().get(0);
                if (l.getListaLicenciasHorarios().isEmpty()) {
                    registrarMensaje(UtilCadena.concatenar("Debe registrar por lo menos un horario para este trámite. "),
                            Boolean.TRUE, mensajes, null,
                            mov);
                }

            }
        }
    }

    /**
     * Valida que se haya registrado en horarios para los permisos y licencias.
     *
     * @param mov
     * @param mensajes
     */
    private void validarPuestoPropuesto(final Movimiento mov, final List<ReglaMensaje> mensajes) {
        if (mov != null) {
            if (mov.getTramite().getTipoMovimiento().getAreaPuestoDestino() != null
                    && mov.getTramite().getTipoMovimiento().getAreaPuestoDestino()
                    && mov.getTramite().getTipoMovimiento().getPartidaIndividualPuestDest().equals(
                            CamposConfiguracionEnum.OBLIGATORIO.getCodigo())
                    && mov.getMovimientoSituacionPropuesta().getDistributivoDetalleId() == null) {
                registrarMensaje(UtilCadena.concatenar("Debes seleccionar un puesto propuesto"),
                        Boolean.TRUE, mensajes, null, mov);
            } else {
                if (mov.getTramite().getTipoMovimiento().getAreaPuestoDestino() != null
                        && mov.getTramite().getTipoMovimiento().getAreaPuestoDestino()
                        && mov.getDistributivoDetalleId() != null
                        && mov.getMovimientoSituacionPropuesta().getDistributivoDetalleId() != null
                        && mov.getDistributivoDetalleId().compareTo(mov.getMovimientoSituacionPropuesta().
                                getDistributivoDetalleId()) == 0) {
                    registrarMensaje(UtilCadena.concatenar(
                            "Puesto actual es igual al puesto propuesto, debe seleccionar otro puesto"),
                            Boolean.TRUE, mensajes, null, mov);
                }
            }
        }
    }

    /**
     * Ejecuta las reglas de validacion asociadas a un tipo de movimiento especifico.
     *
     * @param tipoMovimientoId Identificador unico del tipo de movimiento.
     * @param mensajes Lista de mensajes
     *
     * @return
     * @throws ReglaException
     */
    private void ejecutar(final Movimiento movimiento, final String numeroTramite, final Long tipoMovimientoId,
            final Map<String, Object> parametros, final List<ReglaMensaje> mensajes, final String usuario,
            final Boolean enviar) throws DaoException {
        if (movimiento.getConJustificacion()) {
            // validar cumpliento de requisitos
            LOG.info("---------VALIDAR REQUISITOS:");
            List<TipoMovimientoRequisito> requisitosNoValidados = tipoMovimientoRequisitoDao.
                    buscarNoValidadosPorMovimiento(
                            tipoMovimientoId, movimiento.getId());
            for (TipoMovimientoRequisito requisito : requisitosNoValidados) {
                registrarMensaje(UtilCadena.concatenar("Requisito ", requisito.getRequisito().getNombre(),
                        " no fue validado."), Boolean.TRUE, mensajes, null, movimiento);
            }
            List<Validacion> validaciones = validacionDao.buscarRequisitosIncumplidosPorMovimiento(movimiento.getId());
            for (Validacion v : validaciones) {
                if (!v.getCumple() && v.getTipoMovimientoRequisito().getObligatorio()) {
                    registrarMensaje(UtilCadena.concatenar("No cumple el requisito ", v.getTipoMovimientoRequisito().
                            getRequisito().getNombre(), "."), Boolean.TRUE, mensajes, null, movimiento);
                }
            }
            validaciones = validacionDao.buscarRequisitosCumplidosSinArchivoPorMovimiento(movimiento.getId());
            for (Validacion v : validaciones) {
                if (v.getCumple() && v.getTipoMovimientoRequisito().getObligatorio() && v.getTipoMovimientoRequisito().
                        getSubirArchivoObligatorio()) {
                    registrarMensaje(UtilCadena.concatenar("Cumple el requisito '", v.getTipoMovimientoRequisito().
                            getRequisito().getNombre(), "' pero tiene pendiente cargar el archivo."), Boolean.TRUE,
                            mensajes, null, movimiento);
                }
            }
            List<TipoMovimientoRegla> reglas = tipoMovimientoReglaDao.buscarPorTipoMovimiento(tipoMovimientoId);
            if (reglas.isEmpty()) {
                registrarMensaje(
                        UtilCadena.concatenar("Trámite No ", numeroTramite, " no contiene reglas de validación"),
                        Boolean.FALSE, mensajes, null, movimiento);
            } else {
                // eliminar la bitacora anterior por movimiento.
                reglaBitacoraDao.quitarVigenciaPorMovimiento(movimiento.getId());
                LOG.info("---------VALIDAR REGLAS:");
                // validacion de reglas.
                for (TipoMovimientoRegla regla : reglas) {
                    // evalua regla.
                    List<ReglaMensaje> mensajesPorRegla = determinarRegla(movimiento, regla, parametros);
                    // registra reglas en bitacora
                    for (ReglaMensaje mensaje : mensajesPorRegla) {
                        if (mensaje.getRegla() == null) {
                            registrarBitacora(movimiento, regla, usuario, mensaje);
                            mensajes.add(mensaje);
                        } else {
                            if (enviar) {
                                // validar si el incumplimiento de la regla esta justificada.
                                Justificacion j
                                        = justificacionDao.buscarPorMovimientoYTipoMovimientoRegla(
                                                movimiento.getId(), regla.getId());
                                if (j == null) {
                                    registrarBitacora(movimiento, regla, usuario, mensaje);
                                    mensajes.add(mensaje);
                                }
                            } else {
                                registrarBitacora(movimiento, regla, usuario, mensaje);
                                mensajes.add(mensaje);
                            }
                        }
                    }
                }
            }
        } else {
            registrarMensaje(UtilCadena.concatenar(
                    "Existe información que debe ser revisada y confirmada en el movimiento de personal."),
                    Boolean.TRUE, mensajes, null, movimiento);
        }
    }

    /**
     *
     * @param movimiento
     * @param regla
     * @param usuario
     * @param mensaje
     * @throws DaoException
     */
    private void registrarBitacora(final Movimiento movimiento, final TipoMovimientoRegla regla, final String usuario,
            final ReglaMensaje mensaje) throws DaoException {
        ReglaBitacora rb = new ReglaBitacora();
        rb.setFechaCreacion(new Date());
        rb.setMovimiento(movimiento);
        rb.setTipoMovimientoRegla(regla);
        rb.setUsuarioCreacion(usuario);
        rb.setMensaje(mensaje.getMensaje());
        rb.setVigente(Boolean.TRUE);
        reglaBitacoraDao.crear(rb);
    }

    /**
     * Se encarga de determinar cual regla debe ejecutar.
     *
     * @param regla Regla a ejecutar.
     * @param mensajes Lista d emensajes.
     */
    private List<ReglaMensaje> determinarRegla(final Movimiento movimiento, final TipoMovimientoRegla regla,
            final Map<String, Object> parametros) {
//        LOG.info("---------REGLA:" + regla.getRegla().getNombre());
        List<ReglaMensaje> mensajes = new ArrayList<>();
        switch (regla.getRegla().getNemonico()) {
            case ValidarRegistroCivil.CODIGO_REGLA:
                validarRegistradoRegistroCivil.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarEstarVivo.CODIGO_REGLA:
                validarEstarVivo.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarTramitePersonaDuplicado.CODIGO_REGLA:
                validarTramitePersonasDuplicadas.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarTramitePuestoDuplicado.CODIGO_REGLA:
                validarTramitePuestoDuplicado.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPluriempleo.CODIGO_REGLA:
                validarPluriempleo.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarJubilados.CODIGO_REGLA:
                validarJubilados.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarRevisionAuditoria.CODIGO_REGLA:
                validarRevisionAuditoria.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarGanadorConcurso.CODIGO_REGLA:
                validarGanadorConcurso.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPuestoJerarquicoSuperior.CODIGO_REGLA:
                validarPuestoJerarquicoSuperior.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPuestoNombramiento.CODIGO_REGLA:
                validarPuestoNombramiento.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarTerceraEdad.CODIGO_REGLA:
                validarTerceraEdad.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarAmonestacionVerbal.CODIGO_REGLA:
                validarAmonestacionVerbal.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarAmonestacionEscrita.CODIGO_REGLA:
                validarAmonestacionEscrita.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarSancionPecuniariaAdministrativa.CODIGO_REGLA:
                validarSancionPecuniariaAdministrativa.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarSuspensionTemporalSinRemuneracion.CODIGO_REGLA:
                validarSuspensionTemporalSinRemuneracion.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarJubilacionVoluntaria.CODIGO_REGLA:
                validarJubilacionVoluntaria.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarJubilacionObligatoria.CODIGO_REGLA:
                validarJubilacionObligatoria.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarMaximoNumeroContratos.CODIGO_REGLA:
                validarMaximoNumeroContratos.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarOcurrenciaTipoMovimiento.CODIGO_REGLA:
                validarOcurrenciaTipoMovimiento.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarPrecedenciaTipoMovimiento.CODIGO_REGLA:
                validarPrecedenciaTipoMovimiento.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarTiempoMaximoAcumuladoTipoMovimiento.CODIGO_REGLA:
                validarTiempoMaximoTipoMovimiento.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarDevengacionesPendientes.CODIGO_REGLA:
                validarDevengacionesPendientes.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPeriodoMovimientoEnEjercicioFiscal.CODIGO_REGLA:
                validarPeriodoMovimientoEnEjercicioFiscal.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarPeriodoMaximoComisionServicios.CODIGO_REGLA:
                validarPeriodoMaximoComisionServicios.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarComisionServicio.CODIGO_REGLA:
                validarComisionServicio.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarServidorConNombramientoPermanente.CODIGO_REGLA:
                validarServidorConNombramientoPermanente.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarServidorConNombramientoPermanenteMinimo.CODIGO_REGLA:
                validarServidorConNombramientoPermanenteMinimo.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarLicenciasYComisionServicioEnCesacion.CODIGO_REGLA:
                validarLicenciasYComisionServicioEnCesacion.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarPuestoDisponiblePorServidorSuspendido.CODIGO_REGLA:
                validarPuestoDisponiblePorServidorSuspendido.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarPuestoDisponiblePorServidorDestituido.CODIGO_REGLA:
                validarPuestoDisponiblePorServidorDestituido.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarPuestoDisponiblePorServidorLicenciaSinRemuneracion.CODIGO_REGLA:
                validarPuestoDisponiblePorServidorLicenciaSinRemuneracion.validar(parametros, mensajes,
                        regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPuestoDisponiblePorServidorComisionServicioSinRemuneracion.CODIGO_REGLA:
                validarPuestoDisponiblePorServidorComisionServicioSinRemuneracion.validar(parametros, mensajes,
                        regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarPuestoDisponiblePorServidorPeriodoPruebaAscenso.CODIGO_REGLA:
                validarPuestoDisponiblePorServidorPeriodoPruebaAscenso.validar(parametros, mensajes,
                        regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarFechaMaximaMovimiento.CODIGO_REGLA:
                validarFechaMaximaMovimiento.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarTramiteTipoMovimientoDuplicado.CODIGO_REGLA:
                validarTramiteTipoMovimientoDuplicado.validar(parametros, mensajes, regla.getObligatorio(),
                        regla.getRegla());
                break;
            case ValidarTareasAsignadas.CODIGO_REGLA:
                validarTareasAsignadas.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            case ValidarMaximoPeriodoContrato.CODIGO_REGLA:
                validarMaximoPeriodoContrato.validar(parametros, mensajes, regla.getObligatorio(), regla.getRegla());
                break;
            default:
                registrarMensaje(UtilCadena.concatenar("Regla ", regla.getRegla().getNombre(),
                        "(", regla.getRegla().getNemonico(), ")",
                        " no pudo ejecutarse ya que no exista su implementación"), Boolean.TRUE,
                        mensajes, null, movimiento);
                break;
        }
        for (ReglaMensaje mensaje : mensajes) {
            mensaje.setMovimiento(movimiento);
            mensaje.setTipoMovimientoRegla(regla);
        }
        return mensajes;
    }

    /**
     * Registra un mensaje.
     *
     * @param texto
     * @param mensajes
     */
    private void registrarMensaje(final String texto, final Boolean obligatorio, final List<ReglaMensaje> mensajes,
            final Regla regla, final Movimiento movimiento) {
        ReglaMensaje mensaje = new ReglaMensaje();
        mensaje.setMensaje(texto);
        mensaje.setObligatorio(obligatorio);
        mensaje.setRegla(regla);
        if (movimiento != null) {
            mensaje.setPartidaIndividual(movimiento.getDistributivoDetalle().getPartidaIndividual());
        }
        mensajes.add(mensaje);
    }

    /**
     * Valida los campo obligatorios del siith.
     *
     * @param movimiento
     * @return
     */
    private List<String> validarCamposObligatorios(final Movimiento m, final Tramite tramite,
            final List<ParametroGlobal> parametros) throws DaoException {
        String grupo = tramite.getTipoMovimiento().getClase().getGrupo().getNemonico();
        List<String> errores = new ArrayList<String>();
        if (GrupoEnum.INGRESOS.getCodigo().equals(grupo)) {
            Ingreso i = ingresoDao.buscarPorMovimiento(m.getId());
            validarCamposObligatorio(m.getTipoIdentificacion(), "Tipo Documento", errores);
            validarCamposObligatorio(m.getNumeroIdentificacion(), "Número Documento", errores);
            validarCamposObligatorio(m.getApellidosNombres(), "Apellidos y Nombres", errores);
//        } else if (GrupoEnum.CESACIONES.getCodigo().equals(grupo)) {
//            Cesacion c = cesacionDao.buscarPorMovimiento(m.getId());
//            validarCamposObligatorio(m.getRigeApartirDe(), "Rige a Partir de ", errores);
//        } else if (GrupoEnum.LICENCIAS.getCodigo().equals(grupo) || GrupoEnum.PERMISOS.getCodigo().equals(grupo)) {
//            validarCamposObligatorio(m.getRigeApartirDe(), "Rige a Partir de ", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
        } else if (GrupoEnum.REGIMEN_DISCIPLINARIO.getCodigo().equals(grupo)) {
//            ParametroGlobal pNemonicoSuspension
//                    = buscarParametroGlobal(
//                            ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_SUSPENSION_TEMPORAL_SIN_GOCE_REMUNERACION.getCodigo(),
//                            parametros);
//            if (!pNemonicoSuspension.getValorTexto().contains(m.getTramite().getTipoMovimiento().getNemonico().trim())) {
//                validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//                validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//            }
//        } else if (GrupoEnum.COMISION_SERVICIO.getCodigo().equals(grupo)) {
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//            ParametroGlobal pNemonicoCS =
//                    buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_COMISION_SERVICIO_SIN_REMUNERACION.
//                    getCodigo(), parametros);
//            if (pNemonicoCS.getValorTexto().indexOf(m.getTramite().getTipoMovimiento().getNemonico()) != -1) {
//                ComisionServicio cs = m.getListaComisionServicio().get(0);
//                validarCamposObligatorio(cs.getInstitucionCoreId(), "Institución Destino", errores);
//                validarCamposObligatorio(cs.getUnidadAdminitrativaCoreId(), "Unidad Administrativa", errores);
//                validarCamposObligatorio(cs.getUnidadAdministrativaDireccionCoreId(),
//                        "Dirección de Unidad administrativa", errores);
//            }
//        } else if (GrupoEnum.CAMBIO_ADMINISTRATIVO.getCodigo().equals(grupo)) {
//            CambioAdministrativo ca = m.getListaCambioAdministrativo().get(0);
//            validarCamposObligatorio(ca.getUnidadAdminitrativaCoreId(), "Unidad Administrativa", errores);
//            validarCamposObligatorio(ca.getUnidadAdministrativaDireccionCoreId(),
//                    "Dirección de Unidad administrativa",
//                    errores);
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//        } else if (GrupoEnum.TRASLADO_ADMINISTRATIVO.getCodigo().equals(grupo)) {
////            validarCamposObligatorio(m.getMovimientoSituacionPropuesta().getOrganigramaPosicionalIndividualCoreId(),
////                    "Puesto a Trasladarse", errores);
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//        } else if (GrupoEnum.TRASPASOS.getCodigo().equals(grupo)) {
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//        } else if (GrupoEnum.SUBROGACIONES.getCodigo().equals(grupo)) {
//            Subrogacion s = m.getListaSubrogacion().get(0);
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//            validarCamposObligatorio(s.getServidorPuestoId(), "Puesto a Subrogar se encuentra vacante", errores);
//        } else if (GrupoEnum.ENCARGOS.getCodigo().equals(grupo)) {
//            MovimientoSituacionPropuesta sp = m.getMovimientoSituacionPropuesta();
//            validarCamposObligatorio(m.getRigeApartirDe(), "Fecha Rige A Partir De", errores);
//            validarCamposObligatorio(m.getFechaHasta(), "Fecha Hasta ", errores);
//        } else if (GrupoEnum.FINALIZACION.getCodigo().equals(grupo)) {
//            validarCamposObligatorio(m.getRigeApartirDe(), "Rige a Partir de ", errores);
        }
        return errores;
    }

    /**
     * Valida un campo obligatorio del siith.
     */
    private void validarCamposObligatorio(final Object objecto, final String nombre, final List<String> errores) {
        System.out.println(nombre + ":" + objecto);
        if (objecto == null) {
            errores.add(nombre);
        } else if (objecto instanceof String && objecto.toString().trim().isEmpty()) {
            errores.add(nombre);
        }
    }
}
