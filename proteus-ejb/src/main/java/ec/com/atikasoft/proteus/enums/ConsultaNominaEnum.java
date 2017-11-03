/*
 *  ArchivoSipariEnum.java
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
 *  28/01/2014
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Alvaro Tituania <alvaro.tituania@markasoft.ec>
 */
public enum ConsultaNominaEnum {

    /**
     * <N>ómina
     */
    NOMINA("PROTEUS_NOMINA", "RESUMEN POR EMPLEADO"),
    /**
     * <E>mpleados
     */
    EMPLEADOS("PROTEUS_TOTAL_NOMINAS", "DETALLES ROL DE PAGOS"),
    /**
     * <N>ómina
     */
    PROTEUS_RESUMEN_ROL_DE_PAGOS("PROTEUS_RESUMEN_ROL_DE_PAGOS", "RESUMEN ROL DE PAGOS"),
    /**
     * Resumen ingresos descuentos.
     */
    //PROTEUS_RESUMEN_ROL_DE_PAGOS_POR_UNIDAD_ORG("PROTEUS_RESUMEN_ROL_DE_PAGOS_POR_UNIDAD_ORG", "RESUMEN ROL DE PAGOS POR UNIDAD ADMINISTRATIVA"),
    /**
     * <E>mpleados
     */
    PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES("PROTEUS_RESUMEN_DE_PAGOS_POR_DIRECCIONES", "RESUMEN DE PAGOS POR DIRECCIONES"),
    /**
     * <N>ómina
     */
    PROTEUS_RESUMEN_DE_RETENCIONES("PROTEUS_RESUMEN_DE_RETENCIONES", "RESUMEN DE RETENCIONES"),
    /**
     * <E>mpleados
     */
    PROTEUS_RESUMEN_DE_DESCUENTOS("PROTEUS_RESUMEN_DE_DESCUENTOS", "RESUMEN DE DESCUENTOS"),
    /**
     * <E>mpleados
     */
    PROTEUS_RESUMEN_POR_RUBROS("PROTEUS_RESUMEN_POR_RUBROS", "RESUMEN POR RUBROS"),
    /**
     * <N>ómina
     */
    PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS("PROTEUS_RESUMEN_POR_INGRESOS_DESCUENTOS", "RESUMEN POR INGRESOS DESCUENTOS");

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
     * @param codigo
     * @param descripcion
     */
    private ConsultaNominaEnum(String codigo, String descripcion) {
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
        for (ConsultaNominaEnum cc : values()) {
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
