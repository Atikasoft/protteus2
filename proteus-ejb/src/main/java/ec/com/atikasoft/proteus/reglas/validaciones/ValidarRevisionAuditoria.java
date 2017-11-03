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
 * Si el puesto tiene identificado 'En revisión de auditoria' no se permita el registro del tramite
 *
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarRevisionAuditoria extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarRevisionAuditoria.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALREAU";

    /**
     * Tipo de unidad administrativa que corresponde a auditoria interna.
     */
    private static final Long TIPO_UNIDAD_AUDITORIA_INTERNA = 2340071l;

    /**
     * Constructor sin argumentos.
     */
    public ValidarRevisionAuditoria() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion en revision de autoria.");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            // verificar que sea jubilado.
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), obligatorio, mensajes, regla,
                        movimiento);
            } else {
//                // si es jubilado, verificar el tipo de puesto.
//
//                SrhvOrganicoPosicionalIndividual p =
//                        (SrhvOrganicoPosicionalIndividual) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
//                if (p.getTipoUnidadAdministrativaId().compareTo(TIPO_UNIDAD_AUDITORIA_INTERNA) == 0) {
//                    registrarMensaje(UtilCadena.concatenar("Puesto con partida individual ", movimiento.
//                            getPartidaIndividual(), " y partida general ",
//                            movimiento.getPartidaGeneral(),
//                            " corresponde a AUDITORÍA INTERNA, no se permite su registro"), obligatorio,
//                            mensajes, regla, movimiento);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
