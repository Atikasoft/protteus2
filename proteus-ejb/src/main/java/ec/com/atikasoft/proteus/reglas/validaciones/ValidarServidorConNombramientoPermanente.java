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
import ec.com.atikasoft.proteus.util.UtilCadena;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Validar que un servidor publico tengo nombramiento permanente.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarServidorConNombramientoPermanente extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG =
            Logger.getLogger(ValidarServidorConNombramientoPermanente.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALSERCONNOMPER";




    /**
     * Constructor sin argumentos.
     */
    public ValidarServidorConNombramientoPermanente() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.fine("**** Validacion servidor con nombramiento permanente.");
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
//                if (p.getMotivoIngresoId() == null) {
//                    registrarMensaje(UtilCadena.concatenar(
//                            "Servidor no tiene asignado el motivo de ingreso."), Boolean.TRUE, mensajes, regla, m);
//                }
//                CatalogoDetalle motivoIngreso = catalogoServicio.buscarMotivoIngreso(p.getMotivoIngresoId());
//                if (motivoIngreso == null) {
//                    registrarMensaje(UtilCadena.concatenar(
//                            "Motivo de ingreso con id ", p.getMotivoIngresoId(), " no existe en el core"), Boolean.TRUE,
//                            mensajes, regla, m);
//                } else {
//                    ParametroGlobal pMotivoIngresoNombramientoPermanente =
//                            buscarParametroGlobal(ParametroGlobalEnum.NEMONICO_MOTIVO_INGRESO_NOMBRAMIENTO_PERMANENTE.
//                            getCodigo(), parametros);
//                    if (!motivoIngreso.getNemonico().equals(pMotivoIngresoNombramientoPermanente.getValorTexto().trim())) {
//                        StringBuilder msg = new StringBuilder();
//                        msg.append("Servidor con identificación ").append(m.getNumeroIdentificacion());
//                        msg.append(" y nombres ").append(m.getApellidosNombres());
//                        msg.append(", no tiene NOMBRAMIENTO PERMANENTE");
//                        registrarMensaje(msg.toString(), obligatorio, mensajes, regla, m);
//                    }
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
