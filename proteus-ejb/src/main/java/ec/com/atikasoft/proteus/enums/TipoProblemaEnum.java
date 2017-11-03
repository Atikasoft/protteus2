/*
 *  TipoParametroGlobalEnum.java
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
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum TipoProblemaEnum {

    /**
     * No existe rubros de ingresos.
     */
    NO_EXISTE_RUBRO_INGRESOS(1L, "RUBROS DE INGRESOS"),
    /**
     * No existe definido estados de servidores.
     */
    NO_EXISTE_ESTADOS_SERVIDOR(2L, "NO EXISTE ESTADOS DE SERVIDORES"),
    /**
     * No existe definido estados de puestos.
     */
    NO_EXISTE_ESTADOS_PUESTOS(3L, "NO EXISTE ESTADOS DE PUESTOS"),
    /**
     * Indica que no existe espeficiado el beneficiario en el rubro.
     */
    ESPECIFICACION_BENEFICIARIO_RUBRO(4L, "ESPECIFICACION BENEFICIARIO RUBRO"),
    /**
     * ERROR DE SINTAXIS EN FORMULA.
     */
    ERROR_SINTAXIS_FORMULA(5L, "ERROR DE SINTAXIS EN FORMULA"),
    /**
     * ERROR DE DIVISION PARA CERO EN FORMULA.
     */
    ERROR_DIVISION_CERO_FORMULA(6L, "ERROR POR DIVISION PARA CERO EN FORMULA."),
    /**
     * NUMERO MAXIMO DE PAGOS.
     */
    NUMERO_MAXIMO_PAGOS(7L, "NUMERO MAXIMO DE PAGOS"),
    /**
     * MÁXIMO TOTAL DE INGRESO PERMITIDO.
     */
    MAXIMO_TOTAL_INGRESO_PERMITIDO(100l, "MÁXIMO TOTAL DE INGRESO PERMITIDO"),
    /**
     * LIQUIDO A PAGAR NEGATIVO.
     */
    LIQUIDO_PAGAR_NEGATIVO(102L, "LIQUIDO A PAGAR NEGATIVO"),
    /**
     * SERVIDOR FUERA DE PERIODO DE CONTRATACION
     */
    SERVIDOR_NO_ENCUENTRA_PERIODO_CONTRATACION(103L, "SERVIDOR NO SE ENCUENTRA DENTRO DEL PERIODO DE CONTRATACION"),
    /**
     * CUENTA BANCARIA NO REGISTRADA
     */
    CUENTA_BANCARIA_NO_REGISTRADA(104l, "CUENTA BANCARIA NO REGISTRADA"),
    /**
     * Error desconocido.
     */
    ERROR_DESCONOCIDO(99L, "ERROR DESCONOCIDO");

    /**
     * Código del tipo parametro.
     */
    private Long id;

    /**
     * Descripcion del tipo parametro.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoProblemaEnum(final Long id, final String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final Long id) {
        String des = null;
        for (TipoProblemaEnum tpg : values()) {
            if (tpg.getId().equals(id)) {
                des = tpg.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * @return the codigo
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
