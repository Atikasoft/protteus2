/*
 *  TipoPeriodoAlertaEnum.java
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
 *  08/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */
public enum TipoPeriodoAlertaEnum {

    /**
     * Años.
     */
    ANIO("A", "AÑOS"),
    /**
     * Mes.
     */
    MES("M", "MESES"),
    /**
     * Semanas.
     */
    SEMANA("S", "SEMANA"),
    /**
     * Dias.
     */
    DIA("D", "DÍAS"),
    /**
     * Horas.
     */
    HORA("H", "HORAS");

    /**
     * Minutos.
     */
//    MINUTO("I", "Minutos"),
    /**
     * Segundos.
     */
    //  SEGUNDO("S", "Segundos");
    /**
     * Código del tipo de periodo.
     */
    private String codigo;

    /**
     * Descripcion del tipo de periodo.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoPeriodoAlertaEnum(final String codigo, final String descripcion) {
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
        for (TipoPeriodoAlertaEnum tp : values()) {
            if (tp.getCodigo().equals(codigo)) {
                des = tp.getDescripcion();
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
    public void setCodigo(final String codigo) {
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
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
}
