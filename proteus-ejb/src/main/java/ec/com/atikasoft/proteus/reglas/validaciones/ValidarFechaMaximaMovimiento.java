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
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Valida la fecha maxima que puede tener un movimiento con respecto a la fecha actual (aplica fecha rige a partir de)
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarFechaMaximaMovimiento extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarFechaMaximaMovimiento.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALFECMAXMOV";

    /**
     * Constructor sin argumentos.
     */
    public ValidarFechaMaximaMovimiento() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion de fecha maxima del movimiento.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (regla.getTipoPeriodo() == null || regla.getValorPeriodo() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe configurado el periodo en la regla."), Boolean.TRUE, mensajes, regla, m);
            } else {
                Date fechaMaxima = UtilFechas.truncarFecha(new Date());
                fechaMaxima = UtilFechas.sumarPeriodos(fechaMaxima, regla.getTipoPeriodo(), regla.getValorPeriodo());
                if (m.getRigeApartirDe().compareTo(fechaMaxima) == 1) {
                    registrarMensaje(
                            UtilCadena.concatenar(
                            "La fecha 'Rige a Partir de' (" + UtilFechas.formatear(m.getRigeApartirDe()),
                            ") no puede ser mayor a la fecha permitida (", UtilFechas.formatear(fechaMaxima), ")"),
                            Boolean.TRUE, mensajes, regla, m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
