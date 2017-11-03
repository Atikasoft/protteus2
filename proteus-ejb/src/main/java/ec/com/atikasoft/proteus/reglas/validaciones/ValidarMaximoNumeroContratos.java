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
 * 
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 * @version $Revision 1.0 $
 */
@Stateless
@LocalBean
public class ValidarMaximoNumeroContratos extends ReglaAbstracta {

    /**
     * Logger de la clase.
     */
    private static final Logger LOG = Logger.getLogger(ValidarMaximoNumeroContratos.class.getCanonicalName());

    /**
     * Identificador de la regla.
     */
    public static final String CODIGO_REGLA = "VALMAXNUMCON";

    /**
     * Nemonico de la modalidad de nombramiento.
     */
    private static final String NEMONICO_MODALIDAD_NOMBRAMIENTO = "N";

    /**
     * Nemonico de la modalidad de contrato.
     */
    private static final String NEMONICO_MODALIDAD_CONTRATO = "C";



    /**
     * Constructor sin argumentos.
     */
    public ValidarMaximoNumeroContratos() {
        super();
    }

    @Override
    public void validar(final Map<String, Object> parametros, final List<ReglaMensaje> mensajes,
            final Boolean obligatorio, final Regla regla) {
        LOG.info("**** Validacion maximo numero de contratos.");
        Movimiento m = null;
        try {
            ParametroGlobal pPorcentaje =
                    buscarParametroGlobal(ParametroGlobalEnum.PORCENTAJE_MAXIMO_CONTRATOS_RELACION_TOTAL_EMPLEADOS.
                    getCodigo(), parametros);
            m = (Movimiento) parametros.get(ReglasParametrosEnum.MOVIMIENTO.getCodigo());
//            Long totalNombramientos = contarNombramientos(m.getTramite().getInstitucionEjercicioFiscal().getInstitucion().getId(),
//                    catalogoServicio.buscarModalidadLaboral(NEMONICO_MODALIDAD_NOMBRAMIENTO).getId());
//            Long totalContratos = contarContratos(m.getTramite().getInstitucionEjercicioFiscal().getId(),
//                    catalogoServicio.buscarModalidadLaboral(NEMONICO_MODALIDAD_CONTRATO).getId());
//            if (totalContratos + 1 > totalNombramientos * pPorcentaje.getValorNumerico().longValue() / 100) {
//                registrarMensaje(UtilCadena.concatenar("El valor total de contratos(", totalContratos, ") supera al ",
//                        pPorcentaje.getValorNumerico().intValue(), "% del total de nombramientos(", totalNombramientos,
//                        ")."), obligatorio, mensajes, regla, m);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            registrarMensaje(MENSAJE_ERROR_GENERAL, Boolean.TRUE, mensajes, regla, m);
        }
    }

    /**
     * Cuenta el total de nombramientos que tiene la institucion.
     *
     * @param institucionId Identificador unico de la institucion.
     * @return Total de nombramientos.
     * @throws Exception
     */
//    private Long contarNombramientos(final Long institucionId, final Long modalidadId) throws Exception {
//        SrhvOrganicoPosicionalIndividual filtro = new SrhvOrganicoPosicionalIndividual();
//        filtro.setModalidadLaboralId(modalidadId);
//        filtro.setProyectoInversion(0);
//        filtro.setInstitucionId(institucionId);
//        return puestoServicio.contarPuestoporFiltros(filtro);
//    }

    /**
     * Cuenta el total de contratos que tiene la institucion.
     *
     * @param institucionId contrato unico de la institucion.
     * @return Total de nombramientos.
     * @throws Exception
     */
//    private Long contarContratos(final Long institucionId, final Long modalidadId) throws Exception {
//        SrhvOrganicoPosicionalIndividual filtro = new SrhvOrganicoPosicionalIndividual();
//        filtro.setModalidadLaboralId(modalidadId);
//        filtro.setProyectoInversion(0);
//        filtro.setInstitucionId(institucionId);
//        return puestoServicio.contarPuestoporFiltros(filtro);
//    }
}
