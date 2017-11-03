/*
 *  ValidarRegistroCivil.java
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
package ec.com.atikasoft.proteus.reglas.validaciones;

import ec.com.atikasoft.proteus.modelo.TipoMovimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Licencia;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.enums.GrupoEnum;
import ec.com.atikasoft.proteus.enums.TipoNacimientoEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.excepciones.DaoException;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Valida que el servidor pueda solicitar hasta el tiempo maximo permitido por tipo de movimiento acumulado en el ultimo
 * año laboral.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarTiempoMaximoAcumuladoTipoMovimiento extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG =
            Logger.getLogger(ValidarTiempoMaximoAcumuladoTipoMovimiento.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALTIEMAXTIPMOV";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarTiempoMaximoAcumuladoTipoMovimiento() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de tiempo maximo del tipo de movimiento.");
        Movimiento m = null;
        try {
//            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
//            SrhvOrganicoPosicionalIndividual p =
//                    (SrhvOrganicoPosicionalIndividual) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
//
//            if (m.getNumeroIdentificacion() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
//            } else if (m.getTipoIdentificacion() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
//            } else if (m.getTramite().getTipoMovimiento().getTiempoMaximo() == null || m.getTramite().getTipoMovimiento().
//                    getPeriodoTiempoMaximo() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe configurado el tiempo máximo en el tipo de movimiento", m.getTramite().
//                        getTipoMovimiento().getNombre()), Boolean.TRUE, mensajes, regla, m);
//            } else if (m.getRigeApartirDe() == null) {
//                registrarMensaje(UtilCadena.concatenar("Debe ingrear el campo  'Rige A Partir De'", m.getTramite().
//                        getTipoMovimiento().getNombre()), Boolean.TRUE, mensajes, regla, m);
//            } else if (m.getFechaHasta() == null) {
//                registrarMensaje(UtilCadena.concatenar("Debe ingrear el campo  'Fecha Hasta'", m.getTramite().
//                        getTipoMovimiento().getNombre()), Boolean.TRUE, mensajes, regla, m);
//            } else {
//                long totalDias = UtilFechas.calcularDiferenciaDiasEntreFechas(m.getRigeApartirDe(), m.getFechaHasta());
//                Date fechaDesde;
//                Date fechaHasta;
//                if (p == null || p.getFechaIngresoInstitucion() == null) {
//                    fechaDesde = m.getRigeApartirDe();
//                    fechaHasta = m.getFechaHasta();
//                } else {
//                    fechaDesde = UtilFechas.calcularFechaInicioUltimoAnioDeLabores(p.getFechaIngresoInstitucion());
//                    fechaHasta = UtilFechas.sumarPeriodos(fechaDesde, regla.getTipoPeriodo(),
//                            regla.getValorPeriodo());
//                    List<Movimiento> movimientos = movimientoDao.buscarPorPeriodoPorTipoMovimiento(m.
//                            getTipoIdentificacion(), m.getNumeroIdentificacion(), m.getTramite().getTipoMovimiento().
//                            getId(), EstadosTramiteEnum.REGISTRADO.getCodigo(), fechaDesde, fechaHasta);
//                    for (Movimiento mAnterior : movimientos) {
//                        totalDias += UtilFechas.calcularDiferenciaDiasEntreFechas(mAnterior.getRigeApartirDe(),
//                                mAnterior.getFechaHasta());
//                    }
//                }
//
//                long totalDiasPermitidos = UtilFechas.calcularNumeroDiasDePeriodo(m.getRigeApartirDe(), m.getTramite().
//                        getTipoMovimiento().getPeriodoTiempoMaximo(),
//                        m.getTramite().getTipoMovimiento().getTiempoMaximo());
//                totalDiasPermitidos += totalDiasPorLicenciaMaternidadPaternidad(m, parametros);
//                if (totalDias > totalDiasPermitidos) {
//                    StringBuilder msg = new StringBuilder();
//                    msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
//                    msg.append(" y nombres ").append(m.getApellidosNombres());
//                    msg.append(",  no puede registrar el movimiento '");
//                    msg.append(m.getTramite().getTipoMovimiento().getNombre());
//                    msg.append("'  durante el periodo ").append(UtilFechas.formatear(fechaDesde));
//                    msg.append(" a ").append(UtilFechas.formatear(fechaHasta));
//                    msg.append(" por que ya supero (").append(totalDias).append(" días) ");
//                    msg.append(",el tiempo máximo permitido (").append(totalDiasPermitidos).append(" días)");
//                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }

    /**
     * Calcula los dias adicionales por caso especial de maternidad y paternidad.
     *
     * @param tm
     * @param tipoNacimiento
     * @param parametros
     * @return
     * @throws DaoException
     */
    private Integer totalDiasPorLicenciaMaternidadPaternidad(final Movimiento m, final Map<String, Object> parametros)
            throws DaoException {
        int total = 0;
//        String grupo = m.getTramite().getTipoMovimiento().getClase().getGrupo().getNemonico();
//        if (grupo.equals(GrupoEnum.LICENCIAS.getCodigo())) {
//            TipoMovimiento tm = m.getTramite().getTipoMovimiento();
//            Licencia l = m.getListaLicencias().get(0);
//            if (l.getTipoNacimiento() != null && l.getTipoNacimiento().equals(TipoNacimientoEnum.MULTIPLE.getCodigo())) {
//                ParametroGlobal licenciaMaternidad = buscarParametroGlobal(
//                        ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_MATERNIDAD.getCodigo(), parametros);
//                ParametroGlobal licenciaPaternidad = buscarParametroGlobal(
//                        ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_PATERNIDAD.getCodigo(), parametros);
//                if (licenciaMaternidad.getValorTexto().indexOf(tm.getNemonico()) != -1) {
//                    total = tm.getDiasAdicionalesMadreNacimientoMultiple();
//                } else if (licenciaPaternidad.getValorTexto().indexOf(tm.getNemonico()) != -1) {
//                    total = tm.getDiasAdicionalesPadreNacimientoMultiple();
//                }
//            } else if (l.getTipoNacimiento() != null && l.getTipoNacimiento().equals(TipoNacimientoEnum.CESAREA.
//                    getCodigo())) {
//                ParametroGlobal licenciaPaternidad = buscarParametroGlobal(
//                        ParametroGlobalEnum.NEMONICO_TIPO_MOVIMIENTO_LICENCIA_PATERNIDAD.getCodigo(), parametros);
//                if (licenciaPaternidad.getValorTexto().indexOf(tm.getNemonico()) != -1) {
//                    total = tm.getDiasAdicionalesPadreNacimientoMultiple();
//                }
//            }
//        }
        return total;
    }
}
