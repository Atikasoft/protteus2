/*
 *  TipoAcumulacionEnum.java
 *  PROTEUS V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with PROTEUS.
 *
 *  PROTEUS
 *  Quito - Ecuador
 *
 *  28/10/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enumeración de Tipo Documento.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public enum TipoVacacionEnum {

    /**
     * Vacacion
     */
    VACACION_PLANIFICADAS("VACACIONES PLANIFICADAS", "V", false, false),
    /**
     * Adelanto Vacaciones.
     */
    VACACION_NO_PLANIFICADAS("VACACIONES", "A", true, true),
    /**
     * Permiso con cargo a vacación
     */
    ANTICIPO_VACACIONES("ANTICIPO DE VACACIONES", "P", false, false);
    /**
     * Nombre del tipo acumulacion.
     */
    private final String nombre;
    /**
     * Nemonico del tipo acumulacion.
     */
    private final String codigo;
    /**
     * Variable que indica si son imputables los fines de semana
     */
    private final boolean cuentaFinSemana;
    /**
     * Variable que indica si son imputables los feriados
     */
    private final boolean cuentaFeriado;

    /**
     * Constructor para enumeración.
     *
     * @param nombre String
     * @param codigo String
     * @param cuentaFeriado boolean
     * @param cuentaFinSemana boolean
     */
    private TipoVacacionEnum(final String nombre, final String codigo, final boolean cuentaFeriado, final boolean cuentaFinSemana) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cuentaFinSemana = cuentaFinSemana;
        this.cuentaFeriado = cuentaFeriado;
    }

    /**
     * Este metodo busca el nombre de un tipo segun el codigo.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerNombre(final String codigo) {
        String des = null;
        for (TipoVacacionEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.getNombre();
                break;
            }
        }
        return des;
    }

    /**
     * Este metodo busca el campo que indica si se imputan feriados de un tipo segun el codigo.
     *
     * @param codigo boolean
     * @return String
     */
    public static boolean obtenerCuentaFeriado(final String codigo) {
        boolean des = false;
        for (TipoVacacionEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.isCuentaFeriado();
                break;
            }
        }
        return des;
    }

    /**
     * Este metodo busca el campo que indica si se imputan fines de semana de un tipo segun el codigo.
     *
     * @param codigo boolean
     * @return String
     */
    public static boolean obtenerCuentaFinSemana(final String codigo) {
        boolean des = false;
        for (TipoVacacionEnum cc : values()) {
            if (cc.getCodigo().equals(codigo)) {
                des = cc.isCuentaFinSemana();
                break;
            }
        }
        return des;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the cuentaFinSemana
     */
    public boolean isCuentaFinSemana() {
        return cuentaFinSemana;
    }

    /**
     * @return the cuentaFeriado
     */
    public boolean isCuentaFeriado() {
        return cuentaFeriado;
    }
}
