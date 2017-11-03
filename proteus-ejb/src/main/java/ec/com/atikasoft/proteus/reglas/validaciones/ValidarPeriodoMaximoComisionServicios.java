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
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
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
 * Controlar que solo se permita realizar una comisión de servisios de tipo "Comisión de servicios sin remuneración para
 * prestar servicios en otra entidad del Estado" solo hasta 6 años durante su carrera administrativa, se debe
 * cuantificar de manera acumulable en todas las instituciones que estuvo el servidor en comisión de servicio, Si el
 * servidor ya cumplió los 6 año por comisión de servicio no dejar hacer una nueva comisión de servicio del tipo
 * indicado.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPeriodoMaximoComisionServicios extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarPeriodoMaximoComisionServicios.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPERMAXCOMSER";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarPeriodoMaximoComisionServicios() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de periodo maximo de comision de servicios.");
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
//            } else if (regla.getTipoPeriodo() == null || regla.getValorPeriodo() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe configurado el periodo en la regla."), Boolean.TRUE, mensajes, regla, m);
//            } else if (p.getFechaIngresoSectorPublico() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "Servidor no tiene registrado la fecha de ingreso al sector público."), Boolean.TRUE, mensajes,
//                        regla, m);
//            } else {
//                Date fechaDesde = p.getFechaIngresoSectorPublico();
//                Date fechaHasta = UtilFechas.truncarFecha(new Date());
//                List<Movimiento> movimientos = movimientoDao.buscarPorPeriodoPorTipoMovimiento(m.getTipoIdentificacion(),
//                        m.getNumeroIdentificacion(), m.getTramite().getTipoMovimiento().getId(),
//                        EstadosTramiteEnum.REGISTRADO.getCodigo(), fechaDesde, fechaHasta);
//                int totalDias = 0;
//                for (Movimiento mAnterior : movimientos) {
//                    totalDias += UtilFechas.calcularDiferenciaDiasEntreFechas(mAnterior.getRigeApartirDe(), mAnterior.
//                            getFechaHasta());
//                }
//                Date fechaCumplida = UtilFechas.sumarDias(fechaDesde, totalDias);
//                Date fechaMaxima = UtilFechas.sumarPeriodos(fechaDesde, regla.getTipoPeriodo(), regla.getValorPeriodo());
//                if (fechaCumplida.compareTo(fechaMaxima) == 1) {
//                    StringBuilder msg = new StringBuilder();
//                    msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
//                    msg.append(" y nombres ").append(m.getApellidosNombres());
//                    msg.append(", supera el tiempo máximo de comisión de servicios permitidos (");
//                    msg.append(regla.getValorPeriodo()).append(" ").append(regla.getTipoPeriodo());
//                    msg.append(")");
//                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
