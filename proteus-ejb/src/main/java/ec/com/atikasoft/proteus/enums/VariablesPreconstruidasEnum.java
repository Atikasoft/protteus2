/*
 *  TipoUsoRubroEnum.java
 *  MEF $Revision 1.0 $
 *
 *  Todos los derechos reservados. All rights reserved.
 *  This software is the confidential and proprietary information of AtikaSoft
 *  ("Confidential Information").  You shall not disclose such Confidential Information
 *  and shall use it only in accordance with the terms of the license agreement you entered into
 *  with AtikaSoft.
 *
 *
 *  AtikaSoft
 *  Quito - Ecuador
 *  http://www.atikasoft.com.ec/
 *
 *  05/01/2012
 *
 *  
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Define las varibles pre construidas en el sistema.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
public enum VariablesPreconstruidasEnum {

    /**
     * NUMERO DE DIAS LABORADOS.
     */
    NUMERO_DIAS_LABORADOS_PERIODO("NDL", "NÚMERO DE DÍAS LABORADOS EN UN PERIODO"),
    /**
     * Porcenta de aporte patronal.
     */
    PORCENTAJE_APORTE_PATRONAL("PAP", "PORCENTAJE DE APORTE PATRONAL"),
    /**
     * Porcentaje de aporte individual.
     */
    PORCENTAJE_APORTE_INDIVIDUAL("PAI", "PORCENTAJE DE APORTE INDIVIDUAL"),
    /**
     * Numero de cargas familiars.
     */
    NUMERO_CARGAS_FAMILIARES("NCF", "NÚMERO DE CARGAS FAMILIARES"),
    /**
     * Numero de dias trabajados cuando ingresan en el periodo actual.
     */
    NUMERO_DIAS_LABORADOS_PRIMER_PERIODO("NDLPV", "NÚMERO DE DÍAS LABORADOS EN UN PERIODO POR PRIMERA VEZ"),
    /**
     * Numero de meses laborados institucion.
     */
    NUMERO_MESES_LABORADOS_INSTITUCION("NML", "NÚMERO DE MESES LABORADOS INSTITUCION"),
    /**
     * Numero de meses laborados sector publico.
     */
    NUMERO_MESES_LABORADOS_SECTOR_PUBLICO("NMLSP", "NÚMERO DE MESES LABORADOS SECTOR PUBLICO"),
    /**
     * Impuesto renta
     */
    IMPUESTO_RENTA("IR", "IMPUESTO RENTA"),
    /**
     * Encargo
     *
     */
    ENCARGO("ENC", "ENCARGO"),
    /**
     * Subrogacion.
     */
    SUBROGACION("SUB", "SUBROGACION"),
    /**
     *
     */
    RECUPERA_ANTICIPO_QUINCENA("RAQ", "RECUPERACION ANTICIPO QUINCENA"),
    /**
     *
     */
    RECUPERA_ANTICIPO_DECIMO_CUARTO("RDC", "RECUPERACION DE DECIMO CUARTO"),
    /**
     *
     */
    RECUPERA_ANTICIPO_DECIMO_TERCERO("RDT", "RECUPERACION DE DECIMO TERCERO");

    /**
     * Codigo del tipo uso rubro.
     */
    private final String codigo;

    /**
     * Descripcion del tipo uso rubro.
     */
    private final String descripcion;

    /**
     * Constructor con parametros.
     *
     * @param codigo String
     * @param descripcion String
     */
    private VariablesPreconstruidasEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
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

    public static String getDescripcion(final String codigo) {
        String descripcion = null;
        for (VariablesPreconstruidasEnum e : VariablesPreconstruidasEnum.values()) {
            if (e.getCodigo().equals(codigo)) {
                descripcion = e.getDescripcion();
                break;
            }
        }
        return descripcion;
    }
}
