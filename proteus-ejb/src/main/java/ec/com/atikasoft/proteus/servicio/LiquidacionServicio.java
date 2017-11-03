/*
 *  LiquidacionServicio.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *adminis
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.servicio;

import ec.com.atikasoft.proteus.dao.LiquidacionDao;
import ec.com.atikasoft.proteus.dao.VacacionDao;
import ec.com.atikasoft.proteus.dao.VacacionDetalleDao;
import ec.com.atikasoft.proteus.enums.EstadoLiquidacionEnum;
import ec.com.atikasoft.proteus.enums.TipoVacacionDetalleEnum;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.excepciones.ServicioException;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.InstitucionEjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Liquidacion;
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.modelo.ServidorInstitucion;
import ec.com.atikasoft.proteus.modelo.Vacacion;
import ec.com.atikasoft.proteus.modelo.VacacionDetalle;
import ec.com.atikasoft.proteus.servicio.base.BaseServicio;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author LRodriguez <liliana.rodriguez@marKasoft.ec>
 */
@Stateless
@LocalBean
public class LiquidacionServicio extends BaseServicio {

    /**
     * Logger de clase.
     */
    private Logger LOG = Logger.getLogger(LiquidacionServicio.class.getCanonicalName());
    /**
     * DAO de Liquidacion.
     */
    @EJB
    private LiquidacionDao liquidacionDao;
    /**
     * Servicio de administracion.
     */
    @EJB
    private AdministracionServicio admServicio;

    /**
     * Servicio de vacaciones.
     */
    @EJB
    private VacacionServicio vacacionServicio;

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
     * Permite el registro de un liquidacion
     *
     * @param liquidacion registro a crear en el sistema
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void guardarLiquidacion(final Liquidacion liquidacion) throws ServicioException {
        try {
            liquidacionDao.crear(liquidacion);

        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método permite actualizar un liquidacion
     *
     * @param liquidacion registro a actualizar
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void actualizarLiquidacion(final Liquidacion liquidacion) throws ServicioException {
        try {
            liquidacionDao.actualizar(liquidacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Este método procesa la eliminación de un liquidacion.
     *
     * @param liquidacion registro eliminar: Cambiar la vigencia
     * @throws ServicioException excepcion a nivel de servicio
     */
    public void eliminarLiquidacion(final Liquidacion liquidacion) throws ServicioException {
        try {
            liquidacion.setVigente(false);
            liquidacionDao.actualizar(liquidacion);
        } catch (DaoException ex) {
            throw new ServicioException(ex);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los liquidacions vigentes de un
     * servidor
     *
     * @param idServidor Long id del servidor
     * @return List<Liquidacion> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Liquidacion> listarTodosLiquidacionesPorServidor(final Long idServidor) throws ServicioException {
        try {
            return liquidacionDao.buscarPorServidor(idServidor);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los liquidacions vigentes de un
     * estado
     *
     * @param estado Estado
     * @return List<Liquidacion> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Liquidacion> listarTodosLiquidacionesPorEstado(final String estado) throws ServicioException {
        try {
            return liquidacionDao.buscarPorEstado(estado);
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Metodo que se encarga de buscar todas los liquidacions vigentes de un
     * servidor
     *
     * @return List<Liquidacion> listado obtenido
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public List<Liquidacion> listarTodosLiquidacionesVigentes() throws ServicioException {
        try {
            return liquidacionDao.buscarVigente();
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }

    /**
     * Permite preparar la información para la nómina de liquidacion.
     *
     * @param dd distributivoDetalle del servidor a liquidar
     * @param usuario nombre de usuario conectado
     * @param fechaLegalizacion fecha de cesación
     * @param institucionEjercicioFiscal
     * @return
     * @throws ec.com.atikasoft.proteus.excepciones.ServicioException
     */
    public boolean registrarLiquidacion(final DistributivoDetalle dd, final String usuario, final Date fechaLegalizacion, final InstitucionEjercicioFiscal institucionEjercicioFiscal)
            throws ServicioException {
        boolean problemas = false;

        try {
            Servidor s = dd.getServidor();
            Date fechaIngreso = dd.getFechaIngreso();
            //meses y dias trabajados desde enero 
            double numMesesPeriodo = 0;
            long numDiasPeriodo = 0;
            if (UtilFechas.obtenerAnio(institucionEjercicioFiscal.getEjercicioFiscal().getFechaInicio()) == UtilFechas.obtenerAnio(fechaIngreso).intValue()) {
                numMesesPeriodo = UtilFechas.calculaNumMesesEntreDosFechas(fechaIngreso, fechaLegalizacion);
                numDiasPeriodo = UtilFechas.calcularDiferenciaDiasEntreFechas(fechaIngreso, fechaLegalizacion);
            } else {
                numMesesPeriodo = UtilFechas.calculaNumMesesEntreDosFechas(institucionEjercicioFiscal.getEjercicioFiscal().getFechaInicio(), fechaLegalizacion);
                numDiasPeriodo = UtilFechas.calcularDiferenciaDiasEntreFechas(institucionEjercicioFiscal.getEjercicioFiscal().getFechaInicio(), fechaLegalizacion);
            }
            // meses y dias trabajados el ultimo anio de labores.
            double numMesesLaboral = 0;
            long numDiasLaboral = 0;
            Date fechaInicioUltimoPeriodoLaboral = fechaIngreso;
            while (fechaInicioUltimoPeriodoLaboral.getTime() <= fechaLegalizacion.getTime()) {
                fechaInicioUltimoPeriodoLaboral = UtilFechas.sumarAnios(fechaInicioUltimoPeriodoLaboral, 1);
            }
            numMesesLaboral = UtilFechas.calculaNumMesesEntreDosFechas(fechaInicioUltimoPeriodoLaboral, fechaLegalizacion);
            numDiasLaboral = UtilFechas.calcularDiferenciaDiasEntreFechas(fechaInicioUltimoPeriodoLaboral, fechaLegalizacion);
            // numero dias trabajados ultimo mes.
            String inicioTiempo = UtilCadena.concatenar("01/", UtilFechas.obtenerMes(fechaLegalizacion) + 1, "/" + UtilFechas.obtenerAnio(fechaLegalizacion));
            Date inicioMes = UtilFechas.convertirFechaStringEnDate(inicioTiempo, UtilFechas.FORMATO_FECHA);
            long numDiasMesSalida = UtilFechas.calcularDiferenciaDiasEntreFechas(inicioMes, fechaLegalizacion);

            // numero de meses de servicios a la institucion
            double mesesServicio = UtilFechas.calculaNumMesesEntreDosFechas(fechaIngreso, fechaLegalizacion);

            //dias de saldo de vacaciones
            Long minSaldoVacacion = 0l;

            Vacacion vacacion = vacacionDao.buscarPorServidor(s.getId());
            if (vacacion != null) {
                minSaldoVacacion = vacacion.getSaldo();
            }

            //calcular tiempo proporcional de vacacion desde acreditacion del timmer
            Long saldoPropDias = 0l;
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, UtilFechas.obtenerAnio(new Date()));
            c.set(Calendar.MONTH, UtilFechas.obtenerMes(new Date()) + 1);
            c.set(Calendar.DAY_OF_MONTH, UtilFechas.obtenerDiaDelMes(new Date()));
            Date fechaAcreditacion = UtilFechas.truncarFecha(c.getTime());

            if (fechaAcreditacion.compareTo(UtilFechas.truncarFecha(new Date())) < 0) {
                saldoPropDias = UtilFechas.calcularDiferenciaDiasEntreFechas(fechaAcreditacion, UtilFechas.truncarFecha(new Date()));
            }
            double diasSaldoVacacion = UtilFechas.convertirMinutosEnHoras(minSaldoVacacion) * (s.getJornada() == null ? 0 : s.getJornada()) + saldoPropDias;
            Liquidacion liquidacion = new Liquidacion();
            liquidacion.setFechaCreacion(new Date());
            liquidacion.setUsuarioCreacion(usuario);
            liquidacion.setVigente(Boolean.TRUE);
            liquidacion.setServidorId(dd.getServidor().getId());
            liquidacion.setSueldo(dd.getRmu());
            liquidacion.setNumeroDiasTrabajadosAnio((int) numDiasPeriodo);
            liquidacion.setNumeroMesesTrabajadosAnio((int) numMesesPeriodo);
            liquidacion.setNumeroDiasTrabajadosLaboral((int) numDiasLaboral);
            liquidacion.setNumeroMesesTrabajadosLaboral((int) numMesesLaboral);
            liquidacion.setNumeroMesesServicio((int) mesesServicio);
            liquidacion.setNumeroDiasTrabajadosUltimoMes((int) numDiasMesSalida);
            liquidacion.setNumeroDiasSaldoVacaciones(new BigDecimal(diasSaldoVacacion));
            liquidacion.setPagaMesCompleto(Boolean.TRUE);
            liquidacion.setEstado(EstadoLiquidacionEnum.REGISTRADO.getCodigo());
            liquidacion.setDistributivoDetalle(dd);
            liquidacion.setInstitucionEjercicioFiscal(institucionEjercicioFiscal);
            liquidacion.setFechaIngreso(fechaIngreso);
            liquidacion.setFechaLegalizacion(fechaLegalizacion);
            liquidacion.setNominaId(null);
            eliminarLiquidacionPorServidor(liquidacion.getServidorId(), usuario);
            liquidacionDao.crear(liquidacion);
            if (vacacion != null) {
                VacacionDetalle vd = new VacacionDetalle();
                vd.setCredito(0l);
                vd.setDebito(vacacion.getSaldo());
                vd.setFechaCreacion(new Date());
                vd.setFechaInicio(new Date());
                vd.setFechaFin(new Date());
                vd.setObservacion("LIQUIDACIÓN DE VACACIONES POR CESACIÓN-" + UtilFechas.formatear(fechaLegalizacion));
                vd.setUsuarioCreacion(usuario);
                vd.setVacacion(vacacion);
                vd.setVigente(Boolean.TRUE);
                vd.setTipo(TipoVacacionDetalleEnum.VACACION.getCodigo());
                vd.setEsAjusteManual(false);
                vacacionDetalleDao.crear(vd);
            }
        } catch (DaoException e) {
            problemas = true;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        } catch (ServicioException e) {
            problemas = true;
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            throw new ServicioException(e);
        }
        return problemas;
    }

    /**
     * Elimina los registros vigentes de un servidor.
     *
     * @param idServidor
     * @param usuario
     * @throws ServicioException
     */
    public void eliminarLiquidacionPorServidor(final Long idServidor, final String usuario) throws ServicioException {
        try {
            List<Liquidacion> listaLiquidacionesServidor = listarTodosLiquidacionesPorServidor(idServidor);
            for (Liquidacion l : listaLiquidacionesServidor) {
                if (l.getVigente() && l.getEstado().equals(EstadoLiquidacionEnum.REGISTRADO.getCodigo())) {
                    l.setFechaActualizacion(new Date());
                    l.setUsuarioActualizacion(usuario);
                    l.setVigente(Boolean.FALSE);
                    liquidacionDao.actualizar(l);
                }
            }
        } catch (DaoException e) {
            throw new ServicioException(e);
        }
    }
}
