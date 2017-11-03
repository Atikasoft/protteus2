/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 */
public enum TiposCertificacionesPresupuestariasEnum {

    CONTRATOS("CON", "Certificación Presupuestaria Contratos"),
    JUBILADOS("JUB", "Certificación Presupuestaria Jubilados"),
    PASANTES("PAS", "Certificación Presupuestaria Pasantes"),
    SERVICIOS_PROFESIONALES("SER", "Certificación Presupuestaria Servicios Profesionales"),
    CONCEJALES_ALTERNOS("ALT", "Certificación Presupuestaria Concejales Alternos");

    /**
     *
     */
    private String codigo;

    /**
     *
     */
    private String descripcion;

    /**
     *
     * @param codigo codigo del tipo de certificacion
     * @param descripcion descripcion del tipo de certificacion
     */
    private TiposCertificacionesPresupuestariasEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     *
     * @return codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     *
     * @param codigo codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     *
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Este metodo retorna descripción del enum segun el codigo.
     *
     * @param codigo codigo
     * @return String descripcion
     */
    public static String obtenerDescripcion(final String codigo) {
        for (TiposCertificacionesPresupuestariasEnum d : values()) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return d.getDescripcion();
            }
        }
        return "";
    }

    /**
     * Este metodo retorna el enum segun el codigo.
     *
     * @param codigo codigo del tipo de certificacion
     * @return String enumeracion
     */
    public static TiposCertificacionesPresupuestariasEnum obtenerEnumerador(final String codigo) {
        for (TiposCertificacionesPresupuestariasEnum d : values()) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return d;
            }
        }
        return null;
    }
}
