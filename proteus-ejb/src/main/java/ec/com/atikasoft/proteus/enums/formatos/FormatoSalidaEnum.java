/*
 *  DiasEnum.java
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
package ec.com.atikasoft.proteus.enums.formatos;

import ec.com.atikasoft.proteus.enums.CasoFallecimientoEnum;
import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoMovimientoEnum;

/**
 * Formato de movimientos de salidas.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum FormatoSalidaEnum {

    CODIGO_PUESTO(0, "CÓDIGO DEL PUESTO", "N", null),
    TIPO_PERIODO(1, "TIPO DE PERIODO", "T",
    TipoPeriodoMovimientoEnum.FECHA.getCodigo() + ","
    + TipoPeriodoMovimientoEnum.SU_NOTIFICACION.getCodigo() + ","
    + TipoPeriodoMovimientoEnum.SU_POSESION.getCodigo()),
    /**
     * Periodos de vigencia.
     */
    FECHA_DESDE(2, "FECHA RIGE A PARTIR DE", "F", null),
    /**
     * Informacion de salida.
     */
    FECHA_PRESENTA_RENUNCIA(3, "FECHA PRESENTA RENUNCIA", "F", null),
    FECHA_ACEPTA_RENUNCIA(4, "FECHA ACEPTA RENUNCIA", "F", null),
    NUMERO_CONTRATO_ANTERIOR(5, "NÚMERO DE CONTRATO ANTERIOR", "T", null),
    FECHA_INICIO_CONTRATO_ANTERIOR(6, "FECHA DE INICIO CONTRATO ANTERIOR", "F", null),
    /**
     * Informacion fallecimiento.
     */
    FECHA_FALLECIMIENTO(7, "FECHA DE FALLECIMIENTO", "F", null),
    CASO_FALLECIMIENTO(8, "CASO DE FALLECIMIENTO", "T",
    CasoFallecimientoEnum.ACCIDENTE_TRABAJO.getCodigo() + ","
    + CasoFallecimientoEnum.ENFERMEDAD_PROFESIONAL.getCodigo() + ","
    + CasoFallecimientoEnum.NO_RELACIONADO_FUNCIONES.getCodigo()),
    /**
     * Accion de personal.
     */
    DOCUMENTO_PREVIO(9, "DOCUMENTO PREVIO", "T",
    DocumentoPrevioEnum.ACUERDO.getCodigo() + ","
    + DocumentoPrevioEnum.DECRETO.getCodigo() + ","
    + DocumentoPrevioEnum.RESOLUCION.getCodigo()),
    NUMERO_DOCUMENTO(10, "NÚMERO DE DOCUMENTO", "T", null),
    FECHA_DOCUMENTO(11, "FECHA DE DOCUMENTO", "F", null),
    EXPLICACION(12, "EXPLICACIÓN", "T", null);

    /**
     * Indice.
     */
    private Integer indice;

    /**
     * Nombre.
     */
    private String nombre;

    /**
     * Tipo de dato.
     */
    private String tipoDato;

    /**
     * Dominio.
     */
    private String dominio;

    /**
     * Constructor.
     *
     * @param indice Integer
     */
    private FormatoSalidaEnum(final Integer indice, final String nombre, final String tipoDato, final String dominio) {
        this.indice = indice;
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.dominio = dominio;
    }

    /**
     * @return the numero
     */
    public Integer getIndice() {
        return indice;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the tipoDato
     */
    public String getTipoDato() {
        return tipoDato;
    }

    /**
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }
}
