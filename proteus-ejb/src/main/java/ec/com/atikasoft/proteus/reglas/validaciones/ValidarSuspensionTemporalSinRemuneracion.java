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
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Control Suspensión temporal sin goce de remuneración (REGLA4):Si el servidor tiene una sanción de este tipo en un año
 * consecutivo no dejar hacer otra, Cálculo de año consecutivo: la fecha del año inicia desde la fecha de vigencia de la
 * última sanción por “Suspensión” que registre el servidor.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarSuspensionTemporalSinRemuneracion extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG =
            Logger.getLogger(ValidarSuspensionTemporalSinRemuneracion.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALSUTESIRE";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarSuspensionTemporalSinRemuneracion() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de suspensión temporal.");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else if (movimiento.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else {
                Calendar fechaCorte = Calendar.getInstance();
                fechaCorte.setTime(new Date());
                fechaCorte.add(Calendar.YEAR, -1);
                List<Movimiento> movimientos = movimientoDao.buscar(movimiento.getTramite().
                        getInstitucionEjercicioFiscal().getId(),
                        movimiento.getTipoIdentificacion(), movimiento.getNumeroIdentificacion(),
                        movimiento.getTramite().getTipoMovimiento().getNemonico(), EstadosTramiteEnum.REGISTRADO.
                        getCodigo(), fechaCorte.getTime());
                if (movimientos.isEmpty()) {
                    // controla el tiempo masivo de la suspension temporal.
                    if (movimiento.getRigeApartirDe() == null) {
                        registrarMensaje(UtilCadena.concatenar(
                                "No se ingreso la fecha rige a partir de."), Boolean.TRUE, mensajes, regla, movimiento);
                    } else if (movimiento.getFechaHasta() == null) {
                        registrarMensaje(UtilCadena.concatenar(
                                "No se ingreso la fecha hasta."), Boolean.TRUE, mensajes, regla, movimiento);
                    } else {
                        ParametroGlobal pNemonico =
                                buscarParametroGlobal(ParametroGlobalEnum.TIEMPO_MAXIMO_SUSPENSION_TEMPORAL_SIN_GOCE_REMUNERACION.
                                getCodigo(), parametros);
                        Long dias = UtilFechas.calcularDiferenciaDiasEntreFechas(movimiento.getRigeApartirDe(),
                                movimiento.getFechaHasta()) - 1;
                        if (dias.intValue() > pNemonico.getValorNumerico().intValue()) {
                            StringBuilder msg = new StringBuilder();
                            msg.append("Servidor con identificación ").append(movimiento.getNumeroIdentificacion());
                            msg.append(" y nombres ").append(movimiento.getApellidosNombres());
                            msg.append(
                                    ", la suspensión temporal sin goce de remuneración supera los días máximos permitidos,"
                                    + " se ha ingresado ");
                            msg.append(dias).append(" días, máximo permitido es ").append(pNemonico.getValorNumerico().
                                    longValue()).append(" días");
                            registrarMensaje(msg.toString(), obligatorio, mensajes, regla, movimiento);
                        }
                    }
                } else {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Servidor con identificación ").append(movimiento.getNumeroIdentificacion());
                    msg.append(" y nombres ").append(movimiento.getApellidosNombres());
                    msg.append(", ya cuenta con una suspensión temporal sin goce de remuneración"
                            + " en el ultimo año de labores.");
                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, movimiento);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
