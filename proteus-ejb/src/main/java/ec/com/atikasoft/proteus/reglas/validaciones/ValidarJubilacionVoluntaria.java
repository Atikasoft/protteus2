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

import ec.com.atikasoft.proteus.enums.GeneroEnum;
import ec.com.atikasoft.proteus.enums.ParametroGlobalEnum;
import ec.com.atikasoft.proteus.enums.ReglasParametrosEnum;
import ec.com.atikasoft.proteus.modelo.*;
import ec.com.atikasoft.proteus.reglas.ReglaAbstracta;
import ec.com.atikasoft.proteus.reglas.ReglaMensaje;
import ec.com.atikasoft.proteus.util.UtilCadena;
import ec.com.atikasoft.proteus.util.UtilFechas;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * En cesaciones por jubilación voluntaria se debe controlar la edad por género. Si es masculino, su edad debe ser entre
 * los 60 años y 70 años. (valor de años parametrizables). Si es femenino, su edad debe ser entre los 60 años y 70 años.
 * (valor de años parametrizables).
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarJubilacionVoluntaria extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarJubilacionVoluntaria.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALJUBVOL";

    /**
     * Constructor sin argumentos.
     */
    public ValidarJubilacionVoluntaria() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion jubilacion voluntaria");
        Movimiento m = null;
        try {
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
            DistributivoDetalle p =
                    (DistributivoDetalle) parametros.get(ReglasParametrosEnum.PUESTO.getCodigo());

            if (m.getNumeroIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el número de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe el tipo de identificación de la persona."), Boolean.TRUE, mensajes, regla, m);
            } else if (m.getTipoIdentificacion() == null) {
                registrarMensaje(UtilCadena.concatenar(
                        "No existe identificación del servidor."), Boolean.TRUE, mensajes, regla, m);
            } else {
                if (p.getServidor() != null) {
                    Servidor s = p.getServidor();
                    ParametroGlobal pMin = s.getCatalogoGenero().getCodigo().equals(GeneroEnum.FEMENIMO.getCodigo())
                            ? buscarParametroGlobal(ParametroGlobalEnum.EDAD_JUBILACION_MINIMA_FEMENINA.getCodigo(),
                            parametros)
                            : buscarParametroGlobal(ParametroGlobalEnum.EDAD_JUBILACION_MINIMA_MASCULINA.getCodigo(),
                            parametros);
                    ParametroGlobal pMax = s.getCatalogoGenero().getCodigo().equals(GeneroEnum.FEMENIMO.getCodigo())
                            ? buscarParametroGlobal(ParametroGlobalEnum.EDAD_JUBILACION_MAXIMA_FEMENINA.getCodigo(),
                            parametros)
                            : buscarParametroGlobal(ParametroGlobalEnum.EDAD_JUBILACION_MAXIMA_MASCULINA.getCodigo(),
                            parametros);
                    int edad = UtilFechas.calcularEdadEnAnios(s.getFechaNacimiento());
                    if (edad < pMin.getValorNumerico().intValue()) {
                        registrarMensaje(
                                UtilCadena.concatenar("Ciudadano con cédula No ",
                                m.getNumeroIdentificacion(), " y nombres ", m.getApellidos(), " ", m.getNombres(),
                                " tiene ", edad, " años de edad, por lo tanto no cumple la edad mínima (", pMin.
                                getValorNumerico(), ") requerida para la jubilación voluntaria"), obligatorio,
                                mensajes, regla, m);
                    } else if (edad > pMax.getValorNumerico().intValue()) {
                        registrarMensaje(
                                UtilCadena.concatenar("Ciudadano con cédula No ",
                                m.getNumeroIdentificacion(), " y nombres ", m.getApellidos(), " ", m.getNombres(),
                                " tiene ", edad, " años de edad, por lo tanto no cumple la edad máxima (", pMin.
                                getValorNumerico(), ") requerida para la jubilación voluntaria"), obligatorio,
                                mensajes, regla, m);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }
}
