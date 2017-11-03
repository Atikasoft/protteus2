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

import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.Movimiento;
import ec.com.atikasoft.proteus.modelo.ParametroGlobal;
import ec.com.atikasoft.proteus.modelo.Regla;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Regla que valide que el puesto que forma parte del movimiento corresponda a un puesto de jerarquico superior.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPuestoJerarquicoSuperior extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarPuestoJerarquicoSuperior.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPUJESU";

    /**
     * Constructor sin argumentos.
     */
    public ValidarPuestoJerarquicoSuperior() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion de puesto pertenece al jerarquico superior.");
        Movimiento movimiento = null;
        try {
//            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
//            ParametroGlobal pNemonicos = buscarParametroGlobal(
//                    ParametroGlobalEnum.NEMONICO_REGIMEN_LABORAL_JERARQUICO_SUPERIOR.getCodigo(), parametros);
//            SrhvOrganicoPosicionalIndividual puesto =
//                    (SrhvOrganicoPosicionalIndividual) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());
//            String[] nemonicos = pNemonicos.getValorTexto().split(",");
//            boolean existe = false;
//            for (String nemonico : nemonicos) {
//                if (nemonico.trim().equals(puesto.getRegimenLaboralNemonico())) {
//                    existe = true;
//                    break;
//                }
//            }
//            if (!existe) {
//                registrarMensaje("El puesto no corresponde a JERARQUICO SUPERIOR", Boolean.TRUE, mensajes, regla,
//                        movimiento);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
