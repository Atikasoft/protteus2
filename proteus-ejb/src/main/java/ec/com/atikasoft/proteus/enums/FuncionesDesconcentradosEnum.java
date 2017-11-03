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
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public enum FuncionesDesconcentradosEnum {

    /**
     *
     */
    DISTRIBUTIVO("CON_DIS", "DISTRIBUTIVO"),
    /**
     *
     */
    VACACIONES("APR_VAC", "VACACIONES"),
    /**
     *
     */
    REASIGNACION_TRAMITE("REA_TRA", "REASIGNACIÓN DE TRÁMITE"),
    /**
     *
     */
    NOVEDADES("CRE_NOV", "NOVEDADES"),
    /**
     * 
     */
    ASISTENCIA_DE_PERSONAL("ASI_PER", "ASISTENCIA DE PERSONAL");
    
    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;
    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor de mi enum.
     *
     * @param codigo indentificador de la funcion
     * @param descripcion descripcion de la funcion
     */
    private FuncionesDesconcentradosEnum(String codigo, String descripcion) {
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
        String des = null;
        for (FuncionesDesconcentradosEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
