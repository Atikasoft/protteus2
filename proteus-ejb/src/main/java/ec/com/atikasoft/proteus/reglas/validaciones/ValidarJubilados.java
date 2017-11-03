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
 *
 * Los jubilados solo pueden reingresar al sector publico, para ocupar puestos de libre nombramiento y remocion,
 * docencia universitaria e investigacion, El sistema controlará de manera automática que solo en estos tipos de puestos
 * se permita el reingreso al sector publico, Para saber que es jubilado se debe obtener el tipo de impedimento de la BD
 * de impedidos
 *
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarJubilados extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarJubilados.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALJUBI";

    /**
     * Identificador del tipo de impedimento para JUBILADOS - RETIRADOS - PENSIONISTAS no existe nemonico.
     */
    private static final Long TIPO_IMPEDIMIENTO_JUBILADOS = 11l;

    /**
     * Identificador del regimen laboral de jerarquico superior por contratos.
     */
    private static final String NEMONICO_REGIMEN_LABORAL_JERARQUICO_SUPERIOR_CONTRATOS = "LJS_LCONTRA";

    /**
     * Identificador del regimen laboral de jerarquico superior por nombramiento.
     */
    private static final String NEMONICO_REGIMEN_LABORAL_JERARQUICO_SUPERIOR_NOMBRAMIENTOS = "LJS_LOSEP";

    /**
     * Constructor sin argumentos.
     */
    public ValidarJubilados() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion jubilados.");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            // verificar que sea jubilado.
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), obligatorio, mensajes, regla,
                        movimiento);
            } else {
                // PENDIENTE.
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
