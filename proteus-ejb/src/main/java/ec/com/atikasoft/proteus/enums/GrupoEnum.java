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

/**
 * Definicio de los grupos que soporta el sistema
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
public enum GrupoEnum {

    /**
     * Grupo de ingresos.
     */
    INGRESOS("GRU.ING", "INGRESOS"),
    /**
     * Grupo de ABSENTISMO.
     */
    ABSENTISMO("GRU.ABS", "ABSENTISMO"),
    /**
     * Grupo de ROTACION.
     */
    ROTACIONES("GRU.ROT", "ROTACIONES / MODIFICACIONES"),
    /**
     * Grupo de REGIMEN_DISCIPLINARIO.
     */
    REGIMEN_DISCIPLINARIO("GRU.REG.DIS", "RÉGIMEN DISCIPLINARIO"),
    /**
     * Grupo de SALIDAS.
     */
    SALIDAS("GRU.SAL", "SALIDAS"),
    /**
     *
     */
    ABSENTISMO_MIGRACION("A", "ABSENTISMO MIG."),
    /**
     *
     */
    INGRESOS_MIGRACION("I", "INGRESOS MIG."),
    /**
     *
     */
    SALIDAS_MIGRACION("S", "SALIDAS MIG."),
    /**
     *
     */
    ROTACIONES_MIGRACION("R", "ROTACIONES MIG."),
    /**
     *
     */
    REGIMEN_MIGRACION("E", "REGIMEN DISCIPLINARIO MIG."),
    /**
     * 
     */
    VACACIONES("V", "VACACIONES");
    
    

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
    private GrupoEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        String des = "****";
        for (GrupoEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getDescripcion();
                break;
            }
        }
        return des;
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
