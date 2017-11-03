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
import ec.com.atikasoft.proteus.modelo.Servidor;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * El sistema validara que no este fallecido en la BDD del SIITH en caso que el servidor este en dicha base, caso
 * contrario no se puede validar con la información que provee el Registro Civil (no se dispone), Tipo de Control: Este
 * requisito es de control obligatorio para personas que tienen cedula.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarEstarVivo extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarEstarVivo.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALVIVO";

    /**
     * Constructor sin argumentos.
     */
    public ValidarEstarVivo() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion estar vivo");
        Movimiento movimiento = null;
        try {
            movimiento = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            if (movimiento.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla,
                        movimiento);
            } else {
//                Servidor servidor = servidorServicio.buscarPorNumeroDocumento(movimiento.getNumeroIdentificacion());
//                // determinar si cuenta con el estado de PASIVO DEFINITIVO (fallecido)
//                if (servidor != null && servidor.getEstadoServidor() != null && servidor.getEstadoServidor().getNemonico().
//                        compareTo(ESTADO_PASIVO_DEFINITIVO_ADMSIITH_ID) == 0) {
//                    registrarMensaje(
//                            UtilCadena.concatenar("Ciudadano con cédula No ", movimiento.getNumeroIdentificacion(),
//                            " y nombres " + servidor.getApellido() + " " + servidor.getNombre()
//                            + " se encuentra en estado PASIVO DEFINITIVO"), obligatorio, mensajes, regla, movimiento);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, movimiento);
        }
        LOG.info("FIN validacion estar vivo");
    }
}
