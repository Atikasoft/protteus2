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
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * No permitir el registro de un tramite si tiene otro tramite con el mismo tipo
 * de movimiento encurso, El sistema automaticamente controlara que no se
 * permita el registro de un tramite si la persona tiene otro tramite con el
 * mismo tipo de movimiento en curso.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarTramiteTipoMovimientoDuplicado extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarTramiteTipoMovimientoDuplicado.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPETIMOVDU";

    /**
     * Dao de movimientos.
     */
    @EJB
    private MovimientoDao movimientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarTramiteTipoMovimientoDuplicado() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de tipo de movimiento duplicados.");
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
                List<Movimiento> movimientos = movimientoDao.buscarPersonasPorTipoMovimientoDuplicadas(movimiento.getTipoIdentificacion().
                        substring(0, 1), movimiento.getNumeroIdentificacion(), movimiento.getTramite().getTipoMovimiento().getId(), movimiento.getTramite().getId());
                if (!movimientos.isEmpty()) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Ciudadano con identifcación No ").append(movimiento.getNumeroIdentificacion());
                    msg.append(" y nombres ").append(movimiento.getApellidosNombres());
                    msg.append(", se encuentra registrados en los siguientes trámites:");
                    for (Movimiento m : movimientos) {
                        msg.append(m.getTramite().getNumericoTramite()).append(", ");
                    }
                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, movimiento);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
