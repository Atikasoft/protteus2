/*
 *  EstadosTramiteEnum.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

import ec.com.atikasoft.proteus.modelo.*;

/**
 * Definicion de los objetos que se guardan en el http context
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum CacheEnum {

    /**
     * Cache de tipos de movimientos.
     */
    TIPO_MOVIMIENTO(TipoMovimiento.class.getCanonicalName(), "TIPO DE MOVIMIENTO"),
    /**
     * Cache de parametros globales.
     */
    PARAMETROS_GLOBALES(ParametroGlobal.class.getCanonicalName(), "PARAMETROS GLOBALES"),
    /**
     * Cache de constantes.
     */
    CONSTANTES(Constante.class.getCanonicalName(), "CONSTANTES"),
    /**
     * Cache de variables.
     */
    VARIABLES(Variable.class.getCanonicalName(), "VARIABLES"),
    /**
     * Cache de formulas.
     */
    FORMULAS(Formula.class.getCanonicalName(), "FORMULAS"),
    /**
     * Cache de rubros.
     */
    RUBROS(Rubro.class.getCanonicalName(), "RUBROS"),
    /**
     * Cache de datos adicionales.
     */
    DATOS_ADICIONALES(DatoAdicional.class.getCanonicalName(), "DATOS ADICIONALES"),
    /**
     * Cache de cotizaciones iess..
     */
    COTIZACIONES_IESS(CotizacionIess.class.getCanonicalName(), "COTIZACIONES IESS"),
    /**
     * Cache de feriados.
     */
    FERIADOS(Feriado.class.getCanonicalName(), "FERIADOS"),
    /**
     * Cache de unidades organizacional.
     */
    UNIDADES_ORGANIZACIONAL(UnidadOrganizacional.class.getCanonicalName(), "UNIDADES ORGANIZACIONALES");

    /**
     * Columna del campo ordenamiento.
     */
    private final String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private final String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private CacheEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
