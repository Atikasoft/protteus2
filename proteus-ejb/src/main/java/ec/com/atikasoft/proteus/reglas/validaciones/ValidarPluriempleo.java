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
 * No determinar pluriempleo en el Sector Publico, Sustento Legal: Art 12- Prohibicion de pluriempleo El sistema
 * automaticamente controlara que no se permita el pluriempleo para la persona, de acuerdo al control que maneja el
 * SIITH Tipo de Control: Este control es obligatorio para las personas nacionales como extranjeras.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarPluriempleo extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarPluriempleo.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALPLUR";



    /**
     * Constructor sin argumentos.
     */
    public ValidarPluriempleo() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion de pluriempleo.");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else {
//                List<ServidorPuestoDTO> puestos = servidorPuestoServicio.buscarServidorPorCedula(movimiento.
//                        getNumeroIdentificacion(), movimiento.getTramite().getInstitucionEjercicioFiscal().getId());
//                for (ServidorPuestoDTO sp : puestos) {
//                    if (sp.getExcepcionPluriempleo() == null || sp.getExcepcionPluriempleo().intValue() == 0) {
//                        registrarMensaje(
//                                UtilCadena.concatenar("Ciudadano con cédula No ",
//                                movimiento.getNumeroIdentificacion(),
//                                " y nombres " + movimiento.getApellidosNombres(),
//                                ", no puede tener otros puestos, actualmente labora en la institución ", sp.
//                                getInstitucion()), obligatorio, mensajes, regla, movimiento);
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
    }
}
