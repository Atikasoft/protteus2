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

import ec.com.atikasoft.proteus.dao.DistributivoDetalleDao;
import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.enums.SiNoEnum;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.TipoNacimientoEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.dao.ParametroGlobalDao;
import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.dao.LicenciaDao;
import ec.com.atikasoft.proteus.dao.TipoMovimientoDao;
import ec.com.atikasoft.proteus.dao.EjercicioFiscalDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.*;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.servlet.ServletContext;

/**
 * Realiza validaciones activas en el tramite.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidacionServicio extends BaseServicio {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidacionServicio.class.getCanonicalName());

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Dao de licencias.
     */
    @EJB
    private LicenciaDao licenciaDao;

    /**
     * Dao del tipo de movimiento.
     */
    @EJB
    private TipoMovimientoDao tipoMovimientoDao;

    /**
     * Dao de ejercicio fiscal.
     */
    @EJB
    private EjercicioFiscalDao ejercicioFiscalDao;

    /**
     * Dao del detalle del distributivo.
     */
    @EJB
    private DistributivoDetalleDao distributivoDetalleDao;

    /**
     * Dao de parametros globales.
     */
    @EJB
    private ParametroGlobalDao parametroGlobalDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidacionServicio() {
        super();
    }

    /**
     * Valida que el puesto anterior se encuentre vacante.
     *
     * @param tm
     * @param mInicial
     * @param error
     * @return
     */
    public Boolean validarPuestoARegresarVacanteParaNombramiento(final TipoMovimiento tm, final Long puestoNuevoId,
            final Long puestoAnteriorId, final StringBuilder error) {
        Boolean resultado = Boolean.TRUE;
        try {
            if (tm.getAreaFinalizacion() && puestoNuevoId.longValue() != puestoAnteriorId.longValue()) {
                if (puestoAnteriorId == null) {
                    resultado = Boolean.FALSE;
                    error.append("No se localizo el identificador del puesto anterior.");
                } else {
                    DistributivoDetalle dd = distributivoDetalleDao.buscarPorId(puestoAnteriorId);
                    ParametroGlobal pNemonicoNombramiento = parametroGlobalDao.buscarPorNemonico(
                            ParametroGlobalEnum.NEMONICO_MODALIDAD_LABORAL_NOMBRAMIENTO.getCodigo());
                    String[] nemonicos = pNemonicoNombramiento.getValorTexto().split(",");
                    boolean existe = false;
                    for (String nemonico : nemonicos) {
                        if (nemonico.trim().equals(dd.getDistributivo().getModalidadLaboral().getCodigo())) {
                            existe = true;
                            break;
                        }
                    }
                    // el puesto anterior si es de nombramiento, verificar que se encuentre vacante
                    if (existe && dd.getEstadoPuesto().getId().longValue() != tm.getEstadoPuestoInicialPropuestaCore()) {
                        resultado = Boolean.FALSE;
                        error.append(UtilCadena.concatenar("puesto con partida individual '", dd.getPartidaIndividual(),
                                "' se encuentra en estado '", dd.getEstadoPuesto().getNombre(),
                                "', debe estar en estado '", tm.getEstadoPuestoInicialPropuestaCoreNombre(),
                                "', proceda a verificar para que se deje disponible."));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            error.append("Se presento un error no controlado en la validación de puesto a regresar para nombramientos.");
        }
        return resultado;

    }

    /**
     * Valida que el numero de horas mensuales por representacion en
     * asociaciones laborales no se mayor al maximo permitido en el tipo de
     * movimiento.
     *
     * @param tm
     * @param fechaOcurrioHecho
     * @param fechaPresentaDocumento
     * @param error
     * @return
     */
    public Boolean validarHorasMensualesPorRepresentacionAsociacion(final TipoMovimiento tm,
            final Integer horasMensuales, final StringBuilder error) {
        Boolean resultado = Boolean.TRUE;
        try {
            if (tm.getAreaRepresentacionAsociacionLaboral()) {
                if (horasMensuales == null) {
                    resultado = Boolean.FALSE;
                    error.append("Debe ingresar el campo 'Horas Mensual'");
                } else if (horasMensuales.compareTo(tm.getMaximoNumeroHorasMensuales()) == 1) {
                    resultado = Boolean.FALSE;
                    error.append("El número de 'horas mensuales' (");
                    error.append(horasMensuales);
                    error.append(" horas) no puede ser mayor al máximo permitido (");
                    error.append(tm.getMaximoNumeroHorasMensuales());
                    error.append(" horas).");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            error.append("Se presento un error no controlado en la validación de horas"
                    + " mensuales por representación de asociación laboral.");
        }
        return resultado;
    }

    /**
     * Valida que la fecha de ocurriodo el hecho no se mayor a la fecha de
     * presentacion de la documentacion, o viceverse segun configuracion del
     * tipo de movimiento en el campo 'Control Fecha Presenta Documento'
     *
     * @param tm
     * @param fechaOcurrioHecho
     * @param fechaPresentaDocumento
     * @param error
     * @return
     */
    public Boolean validarFechasLicencia(final TipoMovimiento tm, final Date fechaOcurrioHecho,
            final Date fechaPresentaDocumento, final StringBuilder error) {
        Boolean resultado = Boolean.TRUE;
        try {
            if (tm.getControlFechaPresentaDocumento() != null && tm.getControlFechaPresentaDocumento().equals(SiNoEnum.SI.
                    getCodigo())) {
                if (tm.getAreaLicencias() && fechaOcurrioHecho != null && fechaPresentaDocumento != null && fechaOcurrioHecho.
                        compareTo(fechaPresentaDocumento) == 1) {
                    resultado = Boolean.FALSE;
                    error.append("'Fecha Ocurrió El Hecho' (");
                    error.append(UtilFechas.formatear(fechaOcurrioHecho));
                    error.append(") no debe ser mayor a la 'Fecha Presenta Documentación'(");
                    error.append(UtilFechas.formatear(fechaPresentaDocumento));
                    error.append(").");
                }
                // SE COMENTA POR QUE HUGO DICE QUE NO SE DEBE CONTROLAR , SE ASOCIA AL PRUMOVPER-81 19FE2013 12:16
//            } else {
//                if (tm.getAreaLicencias() && fechaOcurrioHecho != null && UtilFechas.truncarFecha(fechaOcurrioHecho).
//                        compareTo(UtilFechas.truncarFecha(new Date())) == -1) {
//                    resultado = Boolean.FALSE;
//                    error.append("'Fecha Ocurrió El Hecho'(");
//                    error.append(UtilFechas.formatear(fechaOcurrioHecho));
//                    error.append(") no debe ser menor  la 'Fecha Presenta Documentación'");
//                    error.append(UtilFechas.formatear(new Date()));
//                    error.append(").");
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            error.append("Se presento un error no controlado en la validación de fechas de la licencia / permisos");
        }
        return resultado;
    }

    /**
     * Validar que el movimiento de contratos solo puede ser suscrito dentro del
     * ejercicio fiscal vigente.
     *
     * @param fechaRigeAPartirDe
     * @param error
     * @param dd
     * @param fechaHasta
     * @param ctx
     * @return
     */
    public Boolean validarDuracionContratos(final Date fechaRigeAPartirDe, final Date fechaHasta,
            final DistributivoDetalle dd,
            final StringBuilder error, final ServletContext ctx) {
        Boolean resultado = Boolean.TRUE;
        try {
            ParametroGlobal regimenLosep
                    = buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_REGIMENES_PERSONAL_LOSEP.getCodigo(), ctx);
            ParametroGlobal modalidadContrato
                    = buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_MODALIDAD_LABORAL_CONTRATOS.getCodigo(), ctx);
            if (modalidadContrato.getValorTexto().contains(dd.getDistributivo().getModalidadLaboral().getModalidad())
                    && regimenLosep.getValorTexto().contains(dd.getEscalaOcupacional().getNivelOcupacional().
                            getRegimenLaboral().getCodigo())) {
                if (fechaRigeAPartirDe == null) {
                    resultado = Boolean.FALSE;
                    error.append("El campo 'Rige A Partir De' de ser ingresado para CONTRATOS de LOSEP.");
                } else if (fechaHasta == null) {
                    resultado = Boolean.FALSE;
                    error.append("El campo 'Fecha Hasta' de ser ingresado para CONTRATOS de LOSEP");
                } else {
                    EjercicioFiscal ef = ejercicioFiscalDao.buscarActivo();
                    if (fechaRigeAPartirDe.compareTo(ef.getFechaInicio()) == -1 || fechaHasta.compareTo(
                            ef.getFechaFin()) == 1) {
                        resultado = Boolean.FALSE;
                        error.append("El periodo de la contratación debe corresponder al ejercicio fiscal actual: ").
                                append(ef.getNombre());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            error.append("Se presento un error no controlado en la validación de duración de contratos.");
        }
        return resultado;
    }

    /**
     * Valida que se cumpla el periodo permitir del tipo de movimiento.
     *
     * @param tipoMovimientoId
     * @param fechaDesde
     * @param fechaHasta
     * @param tipoNacimiento
     * @param error
     * @param ctx
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public Boolean cumpleTiempoMaximoPermitido(final Long tipoMovimientoId, final Date fechaDesde,
            final Date fechaHasta, final String tipoNacimiento, final StringBuilder error, final ServletContext ctx) {
        Boolean resultado = Boolean.TRUE;
        try {
            TipoMovimiento tm = tipoMovimientoDao.buscarPorId(tipoMovimientoId);
            if (tm.getTiempoMaximo() != null && tm.getPeriodoTiempoMaximo() != null
                    && fechaDesde != null && fechaHasta != null) {
                Calendar fecha = Calendar.getInstance();
                fecha.setTime(UtilFechas.sumarPeriodos(fechaDesde, tm.getPeriodoTiempoMaximo(), tm.getTiempoMaximo()));
                // verifica si el tipo de movimiento corresponde a licencia por maternidad y cesarea.
                controlaLicenciaMaternidadPaternidad(tm, tipoNacimiento, fecha, ctx);
                if (fechaHasta.compareTo(fecha.getTime()) == 1) {
                    resultado = Boolean.FALSE;
                    if (UtilFechas.calcularDiferenciaDiasEntreFechas(UtilFechas.truncarFecha(fechaDesde),
                            UtilFechas.truncarFecha(fechaHasta)) == 1) {
                        // diferencias en horas.
                        error.append("El número de horas solicitadas(");
                        error.append(UtilFechas.calcularDiferenciaHorasEntreFechas(fechaDesde, fechaHasta));
                        error.append(" horas) es mayor al número de horas permitidas (");
                        error.append(UtilFechas.calcularDiferenciaHorasEntreFechas(fechaDesde, fecha.getTime()));
                        error.append(" horas)");
                    } else {
                        // diferencias en dias
                        error.append("El número de días solicitados(");
                        error.append(UtilFechas.calcularDiferenciaDiasEntreFechas(fechaDesde, fechaHasta));
                        error.append(" días) es mayor al número de días permitidos (");
                        error.append(UtilFechas.calcularDiferenciaDiasEntreFechas(fechaDesde, fecha.getTime()));
                        error.append(" días)");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultado = Boolean.FALSE;
            error.append("Se presento un error no controlado en la validación del cumpliento del tiempo permitido.");
        }
        return resultado;
    }

    /**
     * Verifica si el tipo de movimiento corresponde a licencia por maternidad y
     * cesarea.
     *
     * @param tm
     * @param m
     * @param fecha
     */
    private void controlaLicenciaMaternidadPaternidad(final TipoMovimiento tm, final String tipoNacimiento,
            final Calendar fecha, final ServletContext ctx) throws DaoException {
        if (tm.getClase().getGrupo().getNemonico().equals(GrupoEnum.ABSENTISMO.getCodigo()) && tipoNacimiento != null
                && tipoNacimiento.equals(TipoNacimientoEnum.MULTIPLE.getCodigo())) {
            ParametroGlobal licenciaMaternidad = buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_MATERNIDAD.getCodigo(), ctx);
            ParametroGlobal licenciaPaternidad = buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_PATERNIDAD.getCodigo(), ctx);
            if (licenciaMaternidad.getValorTexto().contains(tm.getNemonico())) {
                fecha.add(Calendar.DAY_OF_YEAR, tm.getDiasAdicionalesMadreNacimientoMultiple());
            } else if (licenciaPaternidad.getValorTexto().contains(tm.getNemonico())) {
                fecha.add(Calendar.DAY_OF_YEAR, tm.getDiasAdicionalesPadreNacimientoMultiple());
            }
        } else if (tm.getClase().getGrupo().getNemonico().equals(GrupoEnum.ABSENTISMO.getCodigo()) && tipoNacimiento != null
                && tipoNacimiento.equals(TipoNacimientoEnum.CESAREA.getCodigo())) {
            ParametroGlobal licenciaPaternidad = buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_PATERNIDAD.getCodigo(), ctx);
            if (licenciaPaternidad.getValorTexto().contains(tm.getNemonico())) {
                fecha.add(Calendar.DAY_OF_YEAR, tm.getDiasAdicionalesPadreNacimientoMultiple());
            }
        }
    }

    /**
     * Valida si existe puestos duplicados en otros tramites.
     *
     * @param puestos
     * @param error
     * @return
     */
    public void existenPuestoDuplicados(final Map<Integer, DistributivoDetalle> puestos, final StringBuilder error) {
        try {
            List<Integer> lista = movimientoDao.validarDuplicadorCreacion(puestos);
            if (!lista.isEmpty()) {
                error.append("- Los siguientes puestos seleccionados ya estan siendo utilizados: ");
                for (Integer i : lista) {
                    error.append(i).append(",");
                }
                error.delete(error.length() - 1, error.length());
                //error.append("<br/>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            error.append("- Error no controlado al validar puestos duplicados");
        }
    }

    /**
     * Valida si existe una licencia del mismo tipo de movimiento en curso.
     *
     * @param puestos
     * @param error
     * @return
     */
    public void existenLicenciaEnCurso(final List<DistributivoDetalle> puestos, final TipoMovimiento tm,
            final StringBuilder error) {
        try {
            Date fechaActual = UtilFechas.truncarFecha(new Date());
            for (DistributivoDetalle p : puestos) {
                Servidor s = p.getServidor();
                if (s != null && s.getTipoIdentificacion() != null && s.getNumeroIdentificacion() != null) {
                    List<Licencia> licencias = licenciaDao.buscarEnCurso(s.getTipoIdentificacion(), s.
                            getNumeroIdentificacion(),
                            tm.getId(), fechaActual);
                    if (!licencias.isEmpty()) {
                        error.append("El servidor ").append(s.getApellidosNombres()).append(
                                " tiene actualmente en curso una licencia de '").append(tm.getNombre()).append("'\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            error.append("Error no controlado al validar puestos duplicados");
        }
    }
}
