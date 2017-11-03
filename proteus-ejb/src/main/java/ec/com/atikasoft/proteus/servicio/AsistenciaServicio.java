/*
 *  AsistenciaServicio.java
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
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.AsistenciaDao;
import ec.com.atikasoft.proteus.dao.AsistenciaProcesoDao;
import ec.com.atikasoft.proteus.dao.AtrasoDao;
import ec.com.atikasoft.proteus.dao.JustificacionAsistenciaDao;
import ec.com.atikasoft.proteus.dao.ParametroInstitucionalDao;
import ec.com.atikasoft.proteus.dao.PlanificacionHorarioDao;
import ec.com.atikasoft.proteus.enums.ParametroInstitucionCatalogoEnum;
import ec.com.atikasoft.proteus.enums.TipoAusentismoEnum;
import ec.com.atikasoft.proteus.enums.TipoJustificacionEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.excepciones.ServidorException;
import ec.com.atikasoft.proteus.modelo.Asistencia;
import ec.com.atikasoft.proteus.modelo.AsistenciaProceso;
import ec.com.atikasoft.proteus.modelo.Atraso;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.EscalaOcupacional;
import ec.com.atikasoft.proteus.modelo.JustificacionAsistencia;
import ec.com.atikasoft.proteus.modelo.ParametroInstitucional;
import ec.com.atikasoft.proteus.modelo.PlanificacionHorario;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class AsistenciaServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private static final Logger LOG = Logger.getLogger(AsistenciaServicio.class.getCanonicalName());
    /**
     * Indica que equivale a una falta, para cuando existe una sola timbrada, o
     * no hay timbradas.
     */
    private static final Long MINUTOS_FALTA = -1000L;
    /**
     * DAO de PlanificacionHorario.
     */
    @EJB
    private PlanificacionHorarioDao planificacionHorarioDao;

    /**
     * DAO de Asistencia.
     */
    @EJB
    private AsistenciaDao asistenciaDao;

    /**
     * DAO de JustificacionAsistenciaDao.
     */
    @EJB
    private JustificacionAsistenciaDao justificacionAsistenciaDao;
    /**
     * DAO de Atrasos.
     */
    @EJB
    private AtrasoDao atrasoDao;
    /**
     * Distributivo.
     */
    @EJB
    private DistributivoDetalleServicio distributivoDetalleServicio;
    /**
     * DAO de AsistenciaProceso.
     */
    @EJB
    private AsistenciaProcesoDao asistenciaProcesoDao;
    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;
    /**
     * Servicio de adminitracion.
     */
    @EJB
    private AdministracionServicio administracionServicio;
    /**
     * Dao de parametros institucionales.
     */
    @EJB
    private ParametroInstitucionalDao parametroInstitucionalDao;

    /**
     * Este método procesa la eliminación de un planificacionHorario.
     *
     * @param planificacionHorario registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarPlanificacionHorario(final PlanificacionHorario planificacionHorario) throws ServicioException {
        try {
            planificacionHorario.setVigente(false);
            planificacionHorarioDao.actualizar(planificacionHorario);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un planificacionHorario
     *
     * @param planificacionHorario
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarPlanificacionHorario(final PlanificacionHorario planificacionHorario) throws ServicioException {
        try {
            planificacionHorarioDao.crear(planificacionHorario);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un planificacionHorario
     *
     * @param planificacionHorario registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarPlanificacionHorario(final PlanificacionHorario planificacionHorario) throws ServicioException {
        try {
            planificacionHorarioDao.actualizar(planificacionHorario);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar por servidor, institucion ejercicio fiscal y
     * mes
     *
     * @param idServidor id del servidor
     * @param idInstitucionEjercicioFiscal ejericio fiscal de la institucion
     * @param mes
     * @mes mes a buscar
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionHorario> listarPlanificacionHorarioPorServidorEjercicioFiscalYMes(final long idServidor, final long idInstitucionEjercicioFiscal, final int mes)
            throws ServicioException {
        try {
            return planificacionHorarioDao.buscarPorServidorEjercicioFiscalYMes(idServidor, idInstitucionEjercicioFiscal, mes);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar por servidor,y institucion ejercicio fiscal
     *
     * @param idServidor id del servidor
     * @param idInstitucionEjercicioFiscal ejericio fiscal de la institucion
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<PlanificacionHorario> listarPlanificacionHorarioPorServidorEjercicioFiscal(final long idServidor, final long idInstitucionEjercicioFiscal)
            throws ServicioException {
        try {
            return planificacionHorarioDao.buscarPorServidorEjercicioFiscal(idServidor, idInstitucionEjercicioFiscal);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias en una fecha específica.
     *
     * @param fecha
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Asistencia> listarAsistenciaPorFecha(final Date fecha)
            throws ServicioException {
        try {
            return asistenciaDao.buscarPorFecha(fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias de una planificacion
     * específica.
     *
     * @param idPlanificacion
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Atraso> listarAtrasoPorPlanificacionHorario(final Long idPlanificacion)
            throws ServicioException {
        try {
            return atrasoDao.buscarPorPlanificacionHorario(idPlanificacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias en una fecha específica.
     *
     * @param idServidor
     * @param fechaInicio
     * @param fechaFin
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Asistencia> listarAsistenciaPorServidorYRangoFecha(final Long idServidor, final Date fechaInicio, final Date fechaFin)
            throws ServicioException {
        try {
            return asistenciaDao.buscarPorServidorPorRangoFechas(idServidor, fechaInicio, fechaFin);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias en una fecha específica.
     *
     * @param idServidor
     * @param fechaInicio
     * @param fechaFin
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Atraso> listarAtrasoPorServidorYRangoFecha(final Long idServidor, final Date fechaInicio, final Date fechaFin)
            throws ServicioException {
        try {
            return atrasoDao.buscarPorServidorPorRangoFechas(idServidor, fechaInicio, fechaFin);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias en una fecha específica y
     * codigo de empleado.
     *
     * @param codigoEmpleado
     * @param fecha
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Asistencia> listarAsistenciaPorFechaYEmpleado(final Long codigoEmpleado, final Date fecha)
            throws ServicioException {
        try {
            return asistenciaDao.buscarPorFechaYEmpleado(codigoEmpleado, fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las asistencias en una fecha específica y
     * codigo de empleado.
     *
     * @param idServidor
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Asistencia> listarAsistenciaPorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return asistenciaDao.buscarPorServidor(idServidor);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar el ultimo registro procesado de asistencias.
     *
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<AsistenciaProceso> listarAsistenciaProcesoUltimaFecha()
            throws ServicioException {
        try {
            return asistenciaProcesoDao.buscarUltimaFechaProcesada();
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las inasistencias y/o atrasos de un servidor.
     *
     * @param idServidor id del servidor
     * @param fecha
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Atraso> listarAtrasosPorServidorYFecha(final Long idServidor, final Date fecha)
            throws ServicioException {
        try {
            return atrasoDao.buscarPorServidorYFecha(idServidor, fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar las inasistencias y/o atrasos de un servidor.
     *
     * @param idServidor id del servidor
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<Atraso> listarAtrasosPorServidor(final Long idServidor)
            throws ServicioException {
        try {
            return atrasoDao.buscarPorServidor(idServidor);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar fechas de procesos de asistencia.
     *
     * @param fecha id del servidor
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public AsistenciaProceso listarAsistenciaProcesosPorFecha(final Date fecha)
            throws ServicioException {
        try {
            return asistenciaProcesoDao.buscarPorFecha(fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de una AsistenciaProceso (fecha del proceso)
     *
     * @param asistenciaProceso registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAsistenciaProceso(final AsistenciaProceso asistenciaProceso) throws ServicioException {
        try {
            asistenciaProcesoDao.crear(asistenciaProceso);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un atraso
     *
     * @param atraso registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarAtraso(final Atraso atraso) throws ServicioException {
        try {
            atrasoDao.crear(atraso);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Permite el registro de un Asistencia
     *
     * @param asistencia registro a crear en el sistema
     * @param idInstitucion
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarAsistencia(final Asistencia asistencia, final Long idInstitucion) throws ServicioException {
        try {
            AsistenciaProceso ap = null;
            List<Atraso> latrasoNoVigente = atrasoDao.buscarPorServidorYFecha(asistencia.getServidor().getId(), asistencia.getFecha());
            ////System.out.println(" lsita de atrasos que deben quedar como no vigentes:" + latrasoNoVigente.size()  );
            if (!latrasoNoVigente.isEmpty()) {
                for (Atraso atrasoNoVigente : latrasoNoVigente) {
                    atrasoNoVigente.setFechaActualizacion(new Date());
                    atrasoNoVigente.setUsuarioActualizacion(asistencia.getUsuarioActualizacion());
                    atrasoNoVigente.setVigente(Boolean.FALSE);
                    ap = atrasoNoVigente.getAsistenciaProceso();
                    atrasoDao.actualizar(atrasoNoVigente);
                    ////System.out.println(" anulacion ==>" + atrasoNoVigente.getId());
                }
            } else {
                ap = asistenciaProcesoDao.buscarPorFecha(asistencia.getFecha());
            }
            if (ap == null) {
                throw new ServicioException("No se puede encontrar el proceso de asistencia asociado! " + asistencia.getServidor().getApellidosNombres());
            }

            ////System.out.println("proceso asistencia :" + ap.getFecha());
            DistributivoDetalle dd = distributivoDetalleServicio.buscar(asistencia.getServidor().getId());

            calcularGuardarAusentismo(asistencia, idInstitucion, ap, vacacionServicio.esFechaLaborable(asistencia.getFecha(),
                    dd.getEscalaOcupacional().getNivelOcupacional().getRegimenLaboral().getId()), dd.getEscalaOcupacional());

            asistenciaDao.actualizar(asistencia);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     *
     * @param asistencia
     * @param idInstitucion
     * @param ap
     * @param esFeriado
     * @param escala
     * @return registros procesados
     * @throws ServicioException
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public int calcularGuardarAusentismo(final Asistencia asistencia, final Long idInstitucion, final AsistenciaProceso ap, boolean esFeriado,
            final EscalaOcupacional escala)
            throws ServicioException, DaoException {
        Map<String, Object> parametros = obtenerParametrosAsistencia(idInstitucion, asistencia.getServidor());
        Long tiempoAlmuerzo = (Long) parametros.get("TIEMPO_ALMUERZO"); //en minutos
        double horasAlmuerzo = UtilFechas.convertirMinutosEnHoras(tiempoAlmuerzo);
        Long maxHoraAtrasoXdia = (Long) parametros.get(ParametroInstitucionCatalogoEnum.MAX_HORAS_ATRASO_DIA.getCodigo());
        maxHoraAtrasoXdia = UtilFechas.convertirEnMinutosPorTipoUnidadTiempo('H', maxHoraAtrasoXdia, asistencia.getServidor().getJornada());
        Long minMinutosHE = (Long) parametros.get(ParametroInstitucionCatalogoEnum.MIN_MINUTOS_PARA_HORA_EXTRAS.getCodigo());
        Atraso atraso;
        int contadorRegistros = 0;
        Long minutosAtraso = 0l, minutosAdicionales = 0l;
        Boolean esFalta = Boolean.FALSE;
        if (tiempoAlmuerzo <= 0) {
            throw new ServicioException(UtilCadena.concatenar("El tiempo para el almuerzo es cero, para la institución: ", asistencia.getServidor().getApellidosNombres()));
        }
        if (asistencia.getServidor().getHoraEntrada() == null) {
            throw new ServicioException("Sin registro de hora de inicio de jornada laboral para " + asistencia.getServidor().getApellidosNombres());
        }
        if (asistencia.getServidor().getJornada() == null) {
            throw new ServicioException("Sin registro de número de horas de jornada laboral para " + asistencia.getServidor().getApellidosNombres());
        }
        long diasVacacion = vacacionServicio.esServidorEnVacaciones(asistencia.getServidor(), asistencia.getFecha());
        long minutosVacacion = diasVacacion * asistencia.getServidor().getJornada() * (long) UtilFechas.MIN_EN_HORA;
        if (diasVacacion >= 1) {
            //servidor en vacaciones: si es uno indica dia sin trabajar, si es >0 y <1 permiso por horas.
            throw new ServicioException("Servidor en periodo de vacaciones " + asistencia.getServidor().getApellidosNombres());
        }
        //determinar si es dia laboral
        esFeriado = UtilFechas.esFinSemana(asistencia.getFecha());

        ////System.out.println(" marcacion obligatoria:" + escala.getMarcacionObligatoria());
        ////System.out.println(" horas extras:" + escala.getRecibeHorasExtra());
        if (asistencia.getTimbre1() == null && !esFeriado && escala.getMarcacionObligatoria()) {
            esFalta = Boolean.TRUE;
            //System.out.println("no timbro:" + asistencia.getServidor().getNumeroIdentificacion() + ":" + asistencia.getTimbre1());
        } else if (asistencia.getTimbre1() != null && esFeriado && escala.getRecibeHorasExtra()) {
            minutosAdicionales = determinarHorasExtras(asistencia, tiempoAlmuerzo, minMinutosHE, esFeriado);
        } else if (asistencia.getTimbre1() != null && !esFeriado) {
            if (escala.getMarcacionObligatoria()) {
                minutosAtraso = determinarAtraso(asistencia, horasAlmuerzo);
            }
            if (escala.getRecibeHorasExtra()) {
                minutosAdicionales = determinarHorasExtras(asistencia, horasAlmuerzo, minMinutosHE, esFeriado);
            }
        } else {
            LOG.log(Level.INFO, " no hace nada !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{0}", asistencia.getServidor().getNumeroIdentificacion());
        }

        //System.out.println("servidor:" + asistencia.getServidor().getNumeroIdentificacion() + ":minutosAtraso:" + minutosAtraso + " minutosAdicionales:" + minutosAdicionales + " maxHoraAtrasoXdia:" + maxHoraAtrasoXdia + " es falta:" + esFalta);
        if (minutosAtraso - minutosVacacion >= maxHoraAtrasoXdia) { //se considera falta si sobrepasa las 4h de atraso.
            esFalta = Boolean.TRUE;
        } else if (minutosAtraso - minutosVacacion > 0) {
            atraso = new Atraso();
            atraso.setFecha(asistencia.getFecha());
            atraso.setAsistenciaProceso(ap);
            atraso.setServidor(asistencia.getServidor());
            atraso.setTipo(TipoAusentismoEnum.ATRASO.getCodigo());
            atraso.setMinutosAtraso(minutosAtraso - minutosVacacion);
            atraso.setMinutosXJustificar(minutosAtraso - minutosVacacion);
            atraso.setEsJustificado(Boolean.FALSE);
            guardarAusentismo(atraso, asistencia);
            contadorRegistros++;
        } else if (minutosAtraso.equals(MINUTOS_FALTA)) { //no hay timbradas o solo hay una
            esFalta = Boolean.TRUE;
        }
        if (esFalta) {
            atraso = new Atraso();
            atraso.setAsistenciaProceso(ap);
            atraso.setFecha(ap.getFecha());
            atraso.setServidor(asistencia.getServidor());
            atraso.setMinutosAtraso(1l);
            atraso.setMinutosXJustificar(1l);
            atraso.setTipo(TipoAusentismoEnum.FALTA.getCodigo());
            atraso.setEsJustificado(Boolean.FALSE);
            guardarAusentismo(atraso, asistencia);
            contadorRegistros++;
        }
        //horas extras
        if (minutosAdicionales > 0) {
            atraso = new Atraso();
            atraso.setAsistenciaProceso(ap);
            atraso.setFecha(ap.getFecha());
            atraso.setServidor(asistencia.getServidor());
            atraso.setMinutosAtraso(minutosAdicionales);
            atraso.setMinutosXJustificar(minutosAdicionales);
            atraso.setEsJustificado(Boolean.FALSE);
            atraso.setTipo(esFeriado ? TipoAusentismoEnum.HORA_COMPLEMENTARIA.getCodigo() : TipoAusentismoEnum.HORA_SUPLEMENTARIA.getCodigo());
            if (!esFeriado) {
                List<PlanificacionHorario> lista = planificacionHorarioDao.buscarPorServidorYMes(
                        asistencia.getServidor().getId(), UtilFechas.obtenerMes(asistencia.getFecha()) + 1);
                if (!lista.isEmpty()) {
                    atraso.setPlanificacionHorario(lista.get(0));
                }
            }
            guardarAusentismo(atraso, asistencia);
            contadorRegistros++;
        }

        return contadorRegistros;
    }

    /**
     *
     * @param idInstitucion
     * @return
     * @throws ServicioException
     */
    public Map<String, Object> obtenerParametrosAsistencia(final Long idInstitucion, final Servidor s) throws ServicioException {
        Map<String, Object> p = new HashMap<String, Object>();
        Time hInicioAlmuerzoEstablecida = null;
        Time hFinAlmuerzoEstablecida = null;
        ParametroInstitucional parametro;
        long tiempoAlmuerzo;
        String h1;
        Boolean continuar = true;
        Long maxHoraAtrasoXdia = (long) (s.getJornada().intValue()), minutosParaHE;
        try {
            parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    idInstitucion, ParametroInstitucionCatalogoEnum.MAX_HORAS_ATRASO_DIA.getCodigo());
            if (parametro.getValorNumerico() != null) {
                maxHoraAtrasoXdia = new Long(parametro.getValorNumerico().toString());
                p.put(ParametroInstitucionCatalogoEnum.MAX_HORAS_ATRASO_DIA.getCodigo(), maxHoraAtrasoXdia);
            }
            ////System.out.println("parametro.getValorNumerico()" + maxHoraAtrasoXdia);

            parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                    idInstitucion, ParametroInstitucionCatalogoEnum.HORA_INICIO_ALMUERZO.getCodigo());

            h1 = parametro == null ? null : parametro.getValorTexto();
            if (h1 != null && !h1.isEmpty()) {

                hInicioAlmuerzoEstablecida = UtilFechas.convertirHoraTimbreStringATime(h1 + ":00");
                p.put(ParametroInstitucionCatalogoEnum.HORA_INICIO_ALMUERZO.getCodigo(), hInicioAlmuerzoEstablecida);
            } else {
                LOG.info(UtilCadena.concatenar("No se ha configurado la hora inicicio de almuerzo para la institucion ", idInstitucion));
                throw new ServicioException("No se ha configurado la hora inicicio de almuerzo para la institucion");
            }
            if (continuar) {
                parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(idInstitucion, ParametroInstitucionCatalogoEnum.HORA_FIN_ALMUERZO.getCodigo());
                h1 = parametro == null ? null : parametro.getValorTexto();
                if (h1 != null && !h1.isEmpty()) {
                    hFinAlmuerzoEstablecida = UtilFechas.convertirHoraTimbreStringATime(h1 + ":00");
                    p.put(ParametroInstitucionCatalogoEnum.HORA_FIN_ALMUERZO.getCodigo(), hFinAlmuerzoEstablecida);
                } else {
                    LOG.info(UtilCadena.concatenar("No se ha configurado la hora fin de almuerzo para la institucion ", idInstitucion));
                    throw new ServicioException("No se ha configurado la hora fin de almuerzo para la institucion");
                }
                tiempoAlmuerzo = UtilFechas.calcularMinutosEntreHoras(hInicioAlmuerzoEstablecida, hFinAlmuerzoEstablecida);
                p.put("TIEMPO_ALMUERZO", tiempoAlmuerzo);

                parametro = parametroInstitucionalDao.buscarPorInstitucionYNemonico(
                        idInstitucion, ParametroInstitucionCatalogoEnum.MIN_MINUTOS_PARA_HORA_EXTRAS.getCodigo());
                if (parametro != null && parametro.getValorNumerico() != null) {
                    minutosParaHE = new Long(parametro.getValorNumerico().toString());
                    p.put(ParametroInstitucionCatalogoEnum.MIN_MINUTOS_PARA_HORA_EXTRAS.getCodigo(), minutosParaHE);
                }
            }
            LOG.info(UtilCadena.concatenar("hInicioAlmuerzoEstablecida: ", hInicioAlmuerzoEstablecida,
                    " hFinAlmuerzoEstablecida: " + hFinAlmuerzoEstablecida));

        } catch (DaoException ex) {
            LOG.log(Level.INFO, "No se ha configurado el maximo de horas de atraso en dia: {0}", idInstitucion);
            throw new ServicioException(":No se ha configurado el maximo de horas de atraso en dia para la institucion");
        }

        return p;
    }

    /**
     * Permite el registro de un Asistencia
     *
     * @param j registro a crear en el sistema
     * @param tipo
     * @param atraso
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarJustificacionAsistencia(final JustificacionAsistencia j, final String tipo, final Atraso atraso) throws ServicioException {
        try {

            if (tipo.equals(TipoJustificacionEnum.VACACION.getCodigo())) {
                j.setVacacionSolicitud(vacacionServicio.crearSolicitudVacacion(j));
            }

            justificacionAsistenciaDao.crear(j);
            atrasoDao.actualizar(atraso);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        } catch (ServidorException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * * Este método permite buscar justificaciones por fechas.
     *
     * @param idServidor
     * @param fecha id del servidor
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<JustificacionAsistencia> listarJustificacionAsistenciaPorServidorYfecha(final Long idServidor, final Date fecha)
            throws ServicioException {
        try {
            return justificacionAsistenciaDao.buscarPorServidorYFecha(idServidor, fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar justificaciones por atraso.
     *
     * @param idAtraso
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<JustificacionAsistencia> listarJustificacionAsistenciaPorAtraso(final Long idAtraso)
            throws ServicioException {
        try {
            return justificacionAsistenciaDao.buscarPorAtraso(idAtraso);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite buscar fechas de procesos de asistencia.
     *
     * @param idServidor
     * @param fecha
     * @return lista de registros coincidentes
     * @throws ServicioException excepcion a nivel de servicio
     */
    public List<JustificacionAsistencia> listarJustificacionAsistenciaPorServidorFecha(final Long idServidor, final Date fecha)
            throws ServicioException {
        try {
            return justificacionAsistenciaDao.buscarPorServidorYFecha(idServidor, fecha);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Calcula los minutos de retraso.
     *
     * @param a
     * @param horasAlmuerzo
     * @return
     */
    public Long determinarAtraso(final Asistencia a, final double horasAlmuerzo) {
        Long minutosAtraso = 0L;
        Time hEntradaEstablecida, hSalidaEstablecida;
        hEntradaEstablecida = new Time(a.getHoraTimbres(
                String.valueOf(UtilFechas.formatearEnTiempo(a.getServidor().getHoraEntrada()))).getTime());
        hSalidaEstablecida = new Time(UtilFechas.sumarHoras(a.getServidor().getHoraEntrada(), (int) (a.getServidor().getJornada() + horasAlmuerzo)).getTime());
        Time hTimbreInicial, hTimbreFinal;
        int timbreFinal = 0;
        //Primer timbre
        if (a.getTimbre1() == null) {
            //Registro sin timbradas 
            return MINUTOS_FALTA;
        } else {
            hTimbreInicial = a.getHoraTimbres(a.getTimbre1());
            ////System.out.println(" atraso recuperado ===> "+ a);
            //solo hay un timbre, entonces es falta
            timbreFinal = obtenerUltimoTimbre(a);
            ////System.out.println("ultimo timbre ===> "+ timbreFinal);
            if (timbreFinal < 2) {
                return MINUTOS_FALTA;
            }
            //atraso en la entrada
            if (hEntradaEstablecida.getTime() < hTimbreInicial.getTime()) {
                minutosAtraso += UtilFechas.calcularMinutosEntreHoras(hEntradaEstablecida, hTimbreInicial);
                //System.out.println(" minutos de atraso en la entrada==>" + a.getServidor().getNumeroIdentificacion() + ":" + minutosAtraso);
            }
            //atraso por salir antes de la hora establecida
            hTimbreFinal = obtenerHoraUltimoTimbre(a);
            //System.out.println(" hora fin ==>"+a.getServidor().getId()+":" + a.getServidor().getNumeroIdentificacion() + "/" + hTimbreFinal+"/"+hSalidaEstablecida);
            if (hTimbreFinal.getTime() < hSalidaEstablecida.getTime()) {
                minutosAtraso += UtilFechas.calcularMinutosEntreHoras(hSalidaEstablecida, hTimbreFinal);
                //System.out.println(" minutos de atraso en la salida==>" + a.getServidor().getNumeroIdentificacion() + ":" + minutosAtraso);
            }
            //si tiene 4 timbres, verificar tiempo tomado en almuerzo
            if (timbreFinal == 4) {
                long minAlmuerzoTomados = UtilFechas.calcularMinutosEntreHoras(a.getHoraTimbres(a.getTimbre2()), a.getHoraTimbres(a.getTimbre2()));
                long dif = (long) (horasAlmuerzo * UtilFechas.MIN_EN_HORA) - minAlmuerzoTomados;
                if (dif < 0) {
                    minutosAtraso += dif;
                }
                //System.out.println(" minutos exceso en almuerzo==>" + a.getServidor().getNumeroIdentificacion() + ":" + minutosAtraso);
            }
        }
        return minutosAtraso;
    }

    /**
     * Determina si el servidor tiene o no horas extras. Las horas extras son
     * generadas solo fuera del horario laboral
     *
     * @param a Asistencia con todas sus marcaciones
     * @param tiempoAlmuerzo
     * @param minutosParaHE
     * @param esFeriado
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.DaoException
     */
    public Long determinarHorasExtras(final Asistencia a, final double tiempoAlmuerzo, final Long minutosParaHE, final Boolean esFeriado) throws DaoException {
        Long minutosAdicionales = 0L;
        int timbreFinal = obtenerUltimoTimbre(a);
        if (timbreFinal < 2) {
            return minutosAdicionales;
        }
        Time hSalidaEstablecida = new Time(UtilFechas.sumarHoras(a.getServidor().getHoraEntrada(), (int) (a.getServidor().getJornada() + tiempoAlmuerzo)).getTime());
        Time hTimbreFinal, hTimbreInicial;
        hTimbreInicial = a.getHoraTimbres(a.getTimbre1());
        hTimbreFinal = obtenerHoraUltimoTimbre(a);
        //System.out.println("calculando tiempo adicional" + a.getServidor().getNumeroIdentificacion() + " :hSalidaEstablecida:" + hSalidaEstablecida + ":hTimbreFinal:" + hTimbreFinal + ":hTimbreInicial:" + hTimbreInicial);
        if (esFeriado) {
            minutosAdicionales = UtilFechas.calcularMinutosEntreHoras(hTimbreInicial, hTimbreFinal);
            //System.out.println("minutosAdicionales en feriado:  " + a.getServidor().getNumeroIdentificacion() + ":" + minutosAdicionales);
        } else {
            List<PlanificacionHorario> lista = planificacionHorarioDao.buscarPorServidorYMes(a.getServidor().getId(), UtilFechas.obtenerMes(a.getFecha()) + 1);
            if (!lista.isEmpty()) {
                hSalidaEstablecida = new Time(lista.get(0).getHoraFin().getTime());
            }

            if (hTimbreFinal.getTime() > hSalidaEstablecida.getTime()) {
                minutosAdicionales += UtilFechas.calcularMinutosEntreHoras(hSalidaEstablecida, hTimbreFinal);
                //System.out.println(" minutos adicionales en dia laborable==>" + a.getServidor().getNumeroIdentificacion() + ":" + minutosAdicionales);
            }
        }
        if (minutosAdicionales < minutosParaHE) {
            minutosAdicionales = 0l;
        }
        return minutosAdicionales;
    }

    /**
     * Obtiene la hora del ultimo timbre del servidor.
     *
     * @param a
     * @return
     */
    public Time obtenerHoraUltimoTimbre(final Asistencia a) {
        Time h = null;
        Time[] t = a.getTimbres(a);
        for (Time t1 : t) {
            if (t1 == null) {
                break;
            } else {
                h = t1;
            }
        }
        return h;
    }

    /**
     * Obtiene el ultimo timbre del servidor.
     *
     * @param a
     * @return
     */
    public int obtenerUltimoTimbre(final Asistencia a) {
        int timbre = 0;
        Time[] t = a.getTimbres(a);
        for (int i = 0; i < t.length; i++) {
            if (t[i] == null) {
                timbre = i;
                break;
            }
        }
        return timbre;
    }

    /**
     * Registra el tiempo de atrasos y faltas de un servidor.
     *
     * @param atraso
     * @param a
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public void guardarAusentismo(Atraso atraso, Asistencia a) throws ServicioException {
        try {
            if (a.getUsuarioActualizacion() != null) {
                atraso.setUsuarioCreacion(a.getUsuarioActualizacion());
            } else {
                atraso.setUsuarioCreacion("Automático");
            }
            atraso.setFechaCreacion(new Date());
            atraso.setVigente(Boolean.TRUE);
            atraso.setTimbre1(a.getTimbre1());
            atraso.setTimbre2(a.getTimbre2());
            atraso.setTimbre3(a.getTimbre3());
            atraso.setTimbre4(a.getTimbre4());
            atraso.setTimbre5(a.getTimbre5());
            atraso.setTimbre6(a.getTimbre6());
            atraso.setTimbre7(a.getTimbre7());
            atraso.setTimbre8(a.getTimbre8());
            atraso.setTimbre9(a.getTimbre9());
            atraso.setTimbre10(a.getTimbre10());
            guardarAtraso(atraso);
            //System.out.println("servidor guardado:" + a.getServidor().getNumeroIdentificacion() + ":reg:" + atraso.getId() + " fecha:" + UtilFechas.formatear(atraso.getFecha()));
        } catch (ConstraintViolationException ex) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al guardar la fecha del proceso: Problemas x clave primaria duplicada", ex));
            throw new ServicioException("Error al guardar la fecha del proceso: Problemas x clave primaria duplicada.");
        } catch (ServicioException e) {
            LOG.info(UtilCadena.concatenar(getClass().getName(), "Error al guardar la fecha del proceso", e));
            throw new ServicioException("Error al guardar la fecha del proceso.");
        }
    }
}
