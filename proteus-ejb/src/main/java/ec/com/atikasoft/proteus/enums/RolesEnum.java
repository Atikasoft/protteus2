/*
 *  CamposConfiguracionEnum.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Define los roles de adm siith.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum RolesEnum {

    /**
     * Elaborador de movimientos.
     */
    ELABORADOR("ELAB_TRM_MOVIMIENTOS", "ELABORADOR"),
    CONSULTAS_FINANCIERO("ROL_COF", "CONSULTAS FINANCIERO"),
    CONSULTAS_AUDITORIA("ROL_COA", "CONSULTAS AUDITORIA"),
    /**
     * Aprobador de vacaciones
     */
    APROBADOR_VACACIONES("RAPR_VAC", "APROBADOR VACACIONES");

    /**
     * Código del campo de configuracion.
     */
    private String codigo;

    /**
     * Descripcion del campo de configuracion.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private RolesEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
