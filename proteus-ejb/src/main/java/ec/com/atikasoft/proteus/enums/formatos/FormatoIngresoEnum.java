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

import ec.com.atikasoft.proteus.enums.DocumentoPrevioEnum;
import ec.com.atikasoft.proteus.enums.TipoDesignacionEnum;
import ec.com.atikasoft.proteus.enums.TipoPeriodoMovimientoEnum;

/**
 * Formato de movimientos de ingresos.
 *
 * @author Victor Quimbiamba <victor.quimbiamba@atikasoft.com.ec>
 */
public enum FormatoIngresoEnum {

    CODIGO_PUESTO(0, "CÓDIGO DEL PUESTO", "N", null),
    TIPO_PERIODO(1, "TIPO DE PERIODO", "T",
            TipoPeriodoMovimientoEnum.FECHA.getCodigo() + ","
            + TipoPeriodoMovimientoEnum.SU_NOTIFICACION.getCodigo() + ","
            + TipoPeriodoMovimientoEnum.SU_POSESION.getCodigo()),
    /**
     * Periodos de vigencia.
     */
    FECHA_DESDE(2, "FECHA RIGE A PARTIR DE", "F", null),
    FECHA_HASTA(3, "FECHA HASTA", "F", null),
    /**
     * Servidor.
     */
    TIPO_IDENTIFICACION(4, "TIPO DE IDENTIFICACIÓN", "T", "C,P"),
    NUMERO_IDENTIFICACION(5, "NÚMERO DE IDENTIFICACIÓN", "T", null),
    FECHA_INGRESO_INSTITUCION(6, "FECHA DE INGRESO A LA INSTITUCIÓN", "F", null),
    FECHA_INGRESO_PUBLICO(7, "FECHA DE INGRESO AL SECTOR PÚBLICO", "F", null),
    /**
     * Periodo fijo.
     */
    TIPO_DESIGNACION(8, "TIPO DE DESIGNACIÓN", "T",
            TipoDesignacionEnum.CONCURSO.getCodigo() + ","
            + TipoDesignacionEnum.POPULAR.getCodigo()),
    /**
     * Accion de personal.
     */
    DOCUMENTO_PREVIO(9, "DOCUMENTO PREVIO", "T",
            DocumentoPrevioEnum.ACUERDO.getCodigo() + ","
            + DocumentoPrevioEnum.DECRETO.getCodigo() + ","
            + DocumentoPrevioEnum.RESOLUCION.getCodigo()),
    NUMERO_DOCUMENTO(10, "NÚMERO DE DOCUMENTO", "T", null),
    FECHA_DOCUMENTO(11, "FECHA DE DOCUMENTO", "F", null),
    EXPLICACION(12, "EXPLICACIÓN", "T", null),
    /**
     * Contrato.
     */
    ANTECEDENTES(13, "ANTECEDENTES", "T", null),
    ACTIVIDADES(14, "ACTIVIDADES", "T", null),
    RENOVACION(15, "RENOVACION", "T", "S,N"),
    /**
     * Certificacion Presupuestario.
     */
    CERTIFICACION_PRESUPUESTARIA(16, "CERTIFICACION PRESUPUESTARIA", "T", null),
    /**
     * Puesto propuesto.
     */
    CODIGO_PUESTO_PROPUESTO(17, "CÓDIGO DEL PUESTO PROPUESTO", "N", null);

    /**
     * Indice.
     */
    private final Integer indice;

    /**
     * Nombre.
     */
    private final String nombre;

    /**
     * Tipo de dato.
     */
    private final String tipoDato;

    /**
     * Dominio.
     */
    private final String dominio;

    /**
     * Constructor.
     *
     * @param indice Integer
     */
    private FormatoIngresoEnum(final Integer indice, final String nombre, final String tipoDato, final String dominio) {
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
