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
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Puesto se encuentre en litigio.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPuestoLitigio extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarPuestoLitigio.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPUELIT";

    /**
     * Constructor sin argumentos.
     */
    public ValidarPuestoLitigio() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion de puesto en litigio");
        Movimiento m = null;
        try {
//            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
//            SrhvOrganicoPosicionalIndividual p =
//                    (SrhvOrganicoPosicionalIndividual) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
//            if (m.getNumeroIdentificacion() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
//            } else if (m.getTipoIdentificacion() == null) {
//                registrarMensaje(UtilCadena.concatenar(
//                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
//            } else {
//                if (p.getLitigio().intValue() == 0) {
//                    StringBuilder msg = new StringBuilder();
//                    msg.append("Puesto no se encuentra en LITIGIO ");
//                    registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
