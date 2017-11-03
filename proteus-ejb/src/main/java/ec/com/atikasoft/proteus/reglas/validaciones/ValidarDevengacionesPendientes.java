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

import ec.com.atikasoft.proteus.dao.DevengamientoDao;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Devengamiento;
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
 * Si la persona tiene tiempo pendiente por ser devengado no se permite el registro de la cesación, Este tiempo por
 * devengar es producto de una licencia con remuneración que anteriormente mantuvo la persona, es decir el módulo de
 * licencias deberá registrar el tiempo por devengar (programar cuando este el grupo de licencias).
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarDevengacionesPendientes extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarDevengacionesPendientes.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALDEVPENCE";

    /**
     * Dao de devengaciones
     */
    @EJB
    private DevengamientoDao devengamientoDao;

    /**
     * Constructor sin argumentos.
     */
    public ValidarDevengacionesPendientes() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de devegaciones pendientes.");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (m.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        m);
            } else if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        m);
            } else {
                Date fechaCorte = UtilFechas.truncarFecha(new Date());
                List<Devengamiento> devengaciones = devengamientoDao.buscarPorServidor(m.getTipoIdentificacion(), m.
                        getNumeroIdentificacion(), m.getTramite().getInstitucionEjercicioFiscal().getId(), fechaCorte);

                if (!devengaciones.isEmpty()) {
                    StringBuilder msg = new StringBuilder();
                    msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
                    msg.append(" y nombres ").append(m.getApellidosNombres());
                    msg.append(", tiene pendiente por devengar días de trabajo.");
                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
