package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
public enum TipoDiscapacidadEnum {

    /**
     * AUDITIVA.
     */
    AUDITIVA("A", "AUDITIVA"),
    /**
     * FÍSICA.
     */
    FÍSICA("F", "FÍSICA"),
    /**
     * INTELECTUAL.
     */
    INTELECTUAL("I", "INTELECTUAL"),
    /**
     * VISUAL.
     */
    VISUAL("V", "VISUAL");

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private TipoDiscapacidadEnum(final String codigo, final String descripcion) {
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
        for (TipoDiscapacidadEnum tpg : values()) {
            if (tpg.getCodigo().equals(codigo)) {
                des = tpg.getDescripcion();
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
