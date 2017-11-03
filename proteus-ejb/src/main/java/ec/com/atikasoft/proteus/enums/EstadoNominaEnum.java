/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum EstadoNominaEnum {

    ABIERTO("A", "ABIERTO", "ABRIR"),
    VALIDADO("V", "VALIDADO", "VALIDAR"),
    APROBADO("R", "APROBADO", "APROBAR"),
    PAGADO("P", "PAGADO", "PAGAR"),
    ANULAR("N", "ANULADO", "ANULAR"),
    RECHAZAR("Z", "RECHAZADO", "RECHAZAR");
    /**
     * Codigo.
     */
    private String codigo;

    /**
     * Descripcion.
     */
    private String descripcion;

    /**
     * Accion.
     */
    private String accion;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private EstadoNominaEnum(final String codigo, final String descripcion, final String accion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.accion = accion;
    }
  /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        String des = null;
        for (EstadoNominaEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getDescripcion();
                break;
            }
        }
        return des;
    }
    /**
     * Este metodo busca la accion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerAccion(final String codigo) {
        String des = null;
        for (EstadoNominaEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getAccion();
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
     * @return the accion
     */
    public String getAccion() {
        return accion;
    }

    /**
     * @param accion the accion to set
     */
    public void setAccion(final String accion) {
        this.accion = accion;
    }
}
