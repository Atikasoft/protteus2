/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author atikasoft
 */
public enum TipoDiplomaEnum {

    /**
     * Numérico.
     */
    ASISTENCIA("A", "ASISTENCIA"),
    /**
     * Texto.
     */
    APROBACION("AP", "APROBACIÓN");

    /**
     * Código del campo de configuracion.
     */
    private final String codigo;

    /**
     * Descripcion del campo de configuracion.
     */
    private final String descripcion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoDiplomaEnum(final String codigo, final String descripcion) {
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
        for (TipoDiplomaEnum cc : values()) {
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
