package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Alvaro Tituaña <alvaro.tituania@marcasoft.ec>
 */
public enum CoberturaNominaEnum {

   /**
     * Servidores.
     */
    SERVIDORES_MUNICIPALES("S", "SERVIDORES MUNICIPALES"),
    /**
     * Anticipos
     */
    ANTICIPOS("A", "ANTICIPOS"),
    /**
     * Liquidaciones.
     */
    LIQUIDACIONES("L", "LIQUIDACIONES"),
        /**
     * Terceros.
     */
    TERCEROS("T", "PAGOS A TERCEROS");

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
    private CoberturaNominaEnum(final String codigo, final String descripcion) {
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
        for (CoberturaNominaEnum tpg : values()) {
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
