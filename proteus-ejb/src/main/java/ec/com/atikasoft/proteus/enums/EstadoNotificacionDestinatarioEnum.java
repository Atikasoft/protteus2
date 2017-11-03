/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 * 
 * @author Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 */
public enum EstadoNotificacionDestinatarioEnum {

    NO_LEIDO("NLEI", "NO LEÍDO", "font-weight: bold; color: #0f0f0f"),
    LEIDO("LEI", "LEÍDO", "");

    /**
     * Codigo.
     */
    private final String codigo;

    /**
     * Descripcion.
     */
    private final String descripcion;
    
    /**
     * Color.
     */
    private final String estilo;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private EstadoNotificacionDestinatarioEnum(final String codigo, final String descripcion, final String color) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estilo = color;
    }
  /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        String des = null;
        for (EstadoNotificacionDestinatarioEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getDescripcion();
                break;
            }
        }
        return des;
    }
    
    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerColor(final String codigo) {
        String des = null;
        for (EstadoNotificacionDestinatarioEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getEstilo();
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

    /**
     * 
     * @return 
     */
    public String getEstilo() {
        return estilo;
    }
}
