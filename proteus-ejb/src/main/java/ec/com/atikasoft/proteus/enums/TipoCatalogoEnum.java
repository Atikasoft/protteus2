/*
 *  TipoModalidadEnum.java
 *  Proteus V 2.0 $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of Proteus
 *  ("Confidential Information").  You shall not disclose such Confidential
 *  Information and shall use it only in accordance with the terms of the
 *  license agreement you entered into
 *  with Proteus.
 *
 *  Proteus
 *  Quito - Ecuador
 *  
 *
 *  29/01/2013
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Enum de tipo catalogos.
 *
 * @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public enum TipoCatalogoEnum {

    /**
     * Genero.
     */
    GENERO("GEN", "GENERO"),
    /**
     * Esstado civil.
     */
    ESTADO_CIVIL("ECI", "ESTADO CIVIL"),
    /**
     * Etnia.
     */
    ETNIA("ETN", "ETNIA"),
    /**
     * Talla uniforme.
     */
    TALLA_UNIFORME("TUN", "TALLA DE UNIFORME"),
    /**
     * Numero de calzado.
     */
    NUMERO_CALZADO("NCA", "NUMERO DE CALZADO"),
    /**
     * Discapacidades.
     */
    DISCAPACIDADES("DIS", "DISCAPACIDADES"),
    /**
     * Parentezco.
     */
    PARENTEZCO("PAR", "PARENTEZCO"),
    /**
     * Dependencia.
     */
    DEPENDENCIA("DEP", "DEPENDENCIA"),
    /**
     * Enfermedades.
     */
    ENFERMEDADES("ENF", "ENFERMEDADES"),
    /**
     * Enfermedades catastroficas.
     */
    ENFERMEDADES_CATASTROFICAS("ECA", "ENFERMEDADES CATASTROFICAS"),
    /**
     * Nivel de instruccion.
     */
    NIVEL_INSTRUCCION("NIN", "NIVEL DE INSTRUCCION"),
    /**
     * Tipo de instruccion.
     */
    TIPO_INSTRUCCION("TIN", "TIPO DE INSTRUCCION"),
    /**
     * Tipo de sangre.
     */
    NACIONALIDAD("NAC", "NACIONALIDAD"),
    /**
     * Tipo de sangre.
     */
    TIPO_SANGRE("TSA", "TIPO DE SANGRE"),
    /**
     * Titulos de educacion.
     *
     */
    TITULOS("TIT", "TITULOS"),
    /**
     *
     */
    REPRESENTANTE_RRHH("RRH", "REPRESENTANTE RRHH"),
    /**
     *
     */
    AUTORIDAD_NOMINADORA("AUN", "AUTORIDAD NOMINADORA"),
    /**
     * TIPO DOCUMENTO HABILITANTE
     */
    TIPO_DOCUMENTO_HABILITANTE("TDH", "TIPO DOCUMENTO HABILITANTE"),
    /**
     * 
     */
    MODELO_RELOJ("MODREL", "MODELO RELOJ");
    
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
    private TipoCatalogoEnum(final String codigo, final String descripcion) {
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
        for (TipoCatalogoEnum cc : values()) {
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
