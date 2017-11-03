/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Maikel Pérez Martínez <maikel.perez@markasoft.ec>
 */
public enum EstadoDatoAdiconalServidorEnum {

    REGISTRADO("REG", "REGISTRADO"),
    APROBADO("APR", "APROBADO"),
    RECHAZADO("REC", "RECHAZADO");
    /**
     * Codigo.
     */
    private String codigo;

    /**
     * Descripcion.
     */
    private String nombre;

    /**
     * Constructor.
     *
     * @param codigo String
     * @param descripcion String
     */
    private EstadoDatoAdiconalServidorEnum(final String codigo, final String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Este metodo busca la descripcion de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final String codigo) {
        for (EstadoDatoAdiconalServidorEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                return cc.getNombre();
            }
        }
        return null;
    }
    
    public static EstadoDatoAdiconalServidorEnum obtener(final String codigo) {
        for (EstadoDatoAdiconalServidorEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                return cc;
            }
        }
        return null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   
}
