
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Mauro Vincent <mauro.vincent@atikasoft.com.ec>
 */

public enum PeriodoDevengarEnum {
/**
     * Años.
     */
    ANIO("A", "AÑO"),
    /**
     * Mes.
     */
    MES("M", "MES"),
    /**
     * Dias.
     */
    DIA("D", "DÍAS");
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
    private PeriodoDevengarEnum(final String codigo, final String descripcion) {
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
        for (PeriodoDevengarEnum pd : values()) {
            if (pd.getCodigo().equals(codigo)) {
                des = pd.getDescripcion();
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
