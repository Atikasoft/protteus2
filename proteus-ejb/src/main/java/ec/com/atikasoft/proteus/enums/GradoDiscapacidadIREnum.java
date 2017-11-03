/*
 *  ActivoInactivoEnum.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *  
 *  03/12/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Liliana Rodríguez <liliana.rodriguez@markasoft.ec>
 */
public enum GradoDiscapacidadIREnum {

    /**
     *
     */
    GRADO_DISCAPACIDAD_40_49(40, 49, 60),
    GRADO_DISCAPACIDAD_50_74(50, 74, 70),
    GRADO_DISCAPACIDAD_75_84(75, 84, 80),
    GRADO_DISCAPACIDAD_85_100(85, 100, 100);
    /**
     * Grado Minimo.
     */
    private final Integer gradoMinimo;
    /**
     * Grado Maximo.
     */
    private final Integer gradoMaximo;

    /**
     *
     */
    private final Integer porcentajeDescontar;

    /**
     *
     * @param gradoMinimo
     * @param gradoMaximo
     * @param porcentajeDescontar
     */
    private GradoDiscapacidadIREnum(Integer gradoMinimo, Integer gradoMaximo, Integer porcentajeDescontar) {
        this.gradoMinimo = gradoMinimo;
        this.gradoMaximo = gradoMaximo;
        this.porcentajeDescontar = porcentajeDescontar;
    }

    /**
     * @return the gradoMinimo
     */
    public Integer getGradoMinimo() {
        return gradoMinimo;
    }

    /**
     * @return the gradoMaximo
     */
    public Integer getGradoMaximo() {
        return gradoMaximo;
    }

    /**
     * @return the porcentajeDescontar
     */
    public Integer getPorcentajeDescontar() {
        return porcentajeDescontar;
    }

}
