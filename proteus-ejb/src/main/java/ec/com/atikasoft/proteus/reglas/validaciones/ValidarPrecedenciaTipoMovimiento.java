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
import ec.com.atikasoft.proteus.dao.TipoMovimientoPrecedenciaDao;
import ec.com.atikasoft.proteus.enums.EstadosTramiteEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.modelo.TipoMovimientoPrecedencia;
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
 * Valida que se haya ejecutado los tipos de movimientos predecesores.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPrecedenciaTipoMovimiento extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarPrecedenciaTipoMovimiento.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPRETIPMOV";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Dao de las precedencias de los tipos de movimientos.
     */
    @EJB
    private TipoMovimientoPrecedenciaDao tipoMovimientoPrecedenciaDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarPrecedenciaTipoMovimiento() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de precedencia del tipo de movimiento.");
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
//            } else if (m.getRigeApartirDe() == null) {
//                registrarMensaje(UtilCadena.concatenar("Debe ingrear el campo  'Rige A Partir De'", m.getTramite().
//                        getTipoMovimiento().getNombre()), Boolean.TRUE, mensajes, regla, m);
//            } else {
//                Date fechaDesde = p.getFechaIngresoInstitucion();
//                Date fechaHasta = UtilFechas.truncarFecha(new Date());
//                List<TipoMovimientoPrecedencia> precedencias = tipoMovimientoPrecedenciaDao.buscarPorTipoMovimiento(m.
//                        getTramite().getTipoMovimiento().getId());
//                Boolean cumpleTipoMovimiento = true;
//                for (TipoMovimientoPrecedencia precedencia : precedencias) {
//                    if (precedencia.getVigente()) {
//                        List<Movimiento> movimientos = movimientoDao.buscarPorPeriodoPorTipoMovimiento(m.
//                                getTipoIdentificacion(), m.getNumeroIdentificacion(), precedencia.
//                                getTipoMovimientoDependiente().getId(), EstadosTramiteEnum.REGISTRADO.getCodigo(),
//                                fechaDesde, fechaHasta);
//                        // la “fecha rige a partir de” del movimiento es sucesor no sea menor que la fecha hasta del movimiento predecesor.
//                        //Ejemplo:
//                        //(Predecesor) Movimiento Provisional Periodo de Prueba Rige a partir de 01/01/2013 hasta 01/04/2013.
//                        //(Sucesor) Movimiento “Nombramiento Definitivo” Rige a partir de 02/04/2013.
//                        cumpleTipoMovimiento = true;
//                        if (movimientos.isEmpty()) {
//                            cumpleTipoMovimiento = false;
//                        } else {
//                            cumpleTipoMovimiento = true;
//                            for (Movimiento mAnterior : movimientos) {
//                                if (mAnterior.getFechaHasta() != null && !(m.getRigeApartirDe().compareTo(mAnterior.
//                                        getFechaHasta()) == 1)) {
//                                    StringBuilder msg = new StringBuilder();
//                                    msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
//                                    msg.append(" y nombres ").append(m.getApellidosNombres());
//                                    msg.append(", tiene registrado previamente el movimiento '").append(precedencia.
//                                            getTipoMovimientoDependiente().getNombre());
//                                    msg.append("' con fecha 'Fecha Hasta' (");
//                                    msg.append(UtilFechas.formatear(mAnterior.getFechaHasta()));
//                                    msg.append(") mayor o igual a la fecha 'Rige A Partir De' (");
//                                    msg.append(UtilFechas.formatear(m.getRigeApartirDe()));
//                                    msg.append(") del actual movimiento.");
//                                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                                }
//                            }
//                        }
//                    }
//                    if (cumpleTipoMovimiento) {
//                        break;
//                    }
//                }
//                if (!cumpleTipoMovimiento) {
//                    StringBuilder msg = new StringBuilder();
//                    msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
//                    msg.append(" y nombres ").append(m.getApellidosNombres());
//                    msg.append(", debe tener registrado previamente al menos unos de estos movimientos: ");
//                    for (TipoMovimientoPrecedencia precedencia : precedencias) {
//                        if (precedencia.getVigente()) {
//                            msg.append("'").append(precedencia.getTipoMovimientoDependiente().getNombre()).append("',");
//                        }
//                    }
//                    msg.delete(msg.length() - 1, msg.length());
//                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                }
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
