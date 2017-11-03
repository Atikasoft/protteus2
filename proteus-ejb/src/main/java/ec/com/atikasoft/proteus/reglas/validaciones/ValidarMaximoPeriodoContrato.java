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

import ec.com.atikasoft.proteus.dao.MovimientoDao;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.enums.TipoModalidadEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoAlertaEnum;
import ec.com.atikasoft.proteus.modelo.DistributivoDetalle;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Permite validar que un funcionar no puede estar un maximo de meses por contrato.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarMaximoPeriodoContrato extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarMaximoPeriodoContrato.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALMAXPERCON";

    /**
     * Dao de tareas
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarMaximoPeriodoContrato() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion maximo meses por contrato.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (m.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (regla.getTipoPeriodo() == null || regla.getValorPeriodo() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe configurado el periodo en la regla."), Boolean.TRUE, mensajes, regla, m);
            } else {
                DistributivoDetalle p
                        = (DistributivoDetalle) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
                if (p.getDistributivo().getModalidadLaboral().getModalidad().equals(
                        TipoModalidadEnum.CONTRATO.getCodigo())) {
                    // calcular el maximo de meses
                    double dias = 0;
                    if (regla.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.ANIO.getCodigo())) {
                        dias = regla.getValorPeriodo() * 360;
                    } else if (regla.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.MES.getCodigo())) {
                        dias = regla.getValorPeriodo() * 30;
                    } else if (regla.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.SEMANA.getCodigo())) {
                        dias = regla.getValorPeriodo() * 7;
                    } else if (regla.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.DIA.getCodigo())) {
                        dias = regla.getValorPeriodo();
                    } else if (regla.getTipoPeriodo().equals(TipoPeriodoAlertaEnum.HORA.getCodigo())) {
                        dias = regla.getValorPeriodo() / 8;
                    }
                    // validar el numero de meses que tiene de contrato.
                    double totalAnterior = 0;
                    List<Movimiento> movimientos = movimientoDao.buscarIngresosRegistrados(m.getNumeroIdentificacion());
                    for (Movimiento movimiento : movimientos) {
                        if (movimiento.getRigeApartirDe() != null && movimiento.getFechaHasta() != null
                                && !movimiento.getDistributivoDetalle().getEscalaOcupacional().getNivelOcupacional().
                                getEsLibreRemocion()) {
                            totalAnterior += UtilFechas.calcularDiferenciaDiasEntreFechas(movimiento.getRigeApartirDe(),
                                    movimiento.getFechaHasta());
                        }
                    }
                    double totalActual = 0;
                    if (m.getRigeApartirDe() != null && m.getFechaHasta() != null) {
                        totalActual += UtilFechas.calcularDiferenciaDiasEntreFechas(m.getRigeApartirDe(),
                                m.getFechaHasta());
                    }
                    if (totalAnterior >= dias) {
                        registrarMensaje(UtilCadena.concatenar(
                                "El servidor ", m.getApellidosNombres(),
                                " ha laborado más de 24 meses en el Municipio de Quito"),
                                Boolean.TRUE, mensajes, regla, m);
                    } else if ((totalAnterior + totalActual) > dias) {
                        registrarMensaje(UtilCadena.concatenar(
                                "El servidor ", m.getApellidosNombres(),
                                " con el periodo del presente movimiento supera los 24 meses de labor en el Municipio "
                                + "de Quito, actualmente cuenta con ", (int) totalAnterior / 30, " meses laborados."),
                                Boolean.TRUE, mensajes, regla, m);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }

}
