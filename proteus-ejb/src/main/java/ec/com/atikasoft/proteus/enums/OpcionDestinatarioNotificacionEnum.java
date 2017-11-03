/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public enum OpcionDestinatarioNotificacionEnum {

    TODOS("T", "Todos los servidores"),
    SERVIDORES_ESPECIFICOS("SE", "Servidor(es) Específico(s)"),
    UNIDAD_DESCONCENTRADA("UD", "Unidad(es) Desconcentrada(s)"),
    UNIDAD_RRHH("URH", "Unidades de Recursos Humanos");

    /**
     * Codigo.
     */
    private final String codigo;

    /**
     * Descripcion.
     */
    private final String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private OpcionDestinatarioNotificacionEnum(final String codigo, final String descripcion) {
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
        for (OpcionDestinatarioNotificacionEnum cc : values()) {
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
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
}
