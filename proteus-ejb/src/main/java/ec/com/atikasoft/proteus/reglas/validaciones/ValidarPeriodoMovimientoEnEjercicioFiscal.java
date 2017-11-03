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

import ec.com.atikasoft.proteus.dao.EjercicioFiscalDao;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.EjercicioFiscal;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Validar que fecha inicio y fin del movimiento se encuentre dentro del ejercicio fiscal.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPeriodoMovimientoEnEjercicioFiscal extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(
            ValidarPeriodoMovimientoEnEjercicioFiscal.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPERMOVEJEFIS";

    /**
     * Dao de ejercicio fiscal.
     */
    @EJB
    private EjercicioFiscalDao ejercicioFiscalDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarPeriodoMovimientoEnEjercicioFiscal() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de periodo de movimiento dentro del ejercicio fiscal.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            EjercicioFiscal ef = ejercicioFiscalDao.buscarActivo();
            if (m.getRigeApartirDe() != null && (m.getRigeApartirDe().compareTo(ef.getFechaInicio()) == -1 || m.
                    getRigeApartirDe().compareTo(ef.getFechaFin()) == 1)) {
                StringBuilder msg = new StringBuilder();
                msg.append("La fecha 'Rige A Partir De'(");
                msg.append(UtilFechas.formatear(m.getRigeApartirDe()));
                msg.append(") debe corresponde ejercicio fiscal actual: ");
                msg.append(ef.getNombre());
                registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);

            }
            if (m.getFechaHasta() != null && (m.getFechaHasta().compareTo(ef.getFechaInicio()) == -1 || m.getFechaHasta().
                    compareTo(ef.getFechaFin()) == 1)) {
                StringBuilder msg = new StringBuilder();
                msg.append("La fecha 'Fecha Hasta'(");
                msg.append(UtilFechas.formatear(m.getFechaHasta()));
                msg.append(") debe corresponde ejercicio fiscal actual: ");
                msg.append(ef.getNombre());
                registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);

            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
