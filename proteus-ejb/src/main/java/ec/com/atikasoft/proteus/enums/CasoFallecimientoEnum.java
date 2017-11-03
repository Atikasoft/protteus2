/*
 *  CasoFallecimientoEnum.java
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
 *  04/12/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeración de Caso Fallecimiento.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum CasoFallecimientoEnum {

    /**
     * Tipo.
     */
    ACCIDENTE_TRABAJO("ACT", "Muerte por Accidente de Trabajo"),
    /**
     * Tipo.
     */
    ENFERMEDAD_PROFESIONAL("ENP", "Muerte por Enfermedad Profesional"),
    /**
     * Tipo.
     */
    NO_RELACIONADO_FUNCIONES("ENF", "Muerte no Relacionada con Funciones");

    /**
     * Código de la enumeración.
     */
    private String codigo;

    /**
     * Nombre de la enumeración.
     */
    private String nombre;

    /**
     * Constructor de enumeración.
     *
     * @param codigo String
     * @param nombre String
     */
    private CasoFallecimientoEnum(final String codigo, final String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
}
