/*
 *  EstadosTramiteEnum.java
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
 *  05/11/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Definicion los nemonicos de documento habilitantes.
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum DocumentoHabilitanteEnum {

    /**
     * Accion de personal.
     */
    ACCION_PERSONAL("ACC.PER", "ACCION DE PERSONAL"),
    /**
     * Contratos de servicions ocasionales.
     */
    CONTRATOS_SERVICIOS_OCASIONALES("CON.SER.OCA", "CONTRATOS SERVICIOS OCASIONALES"),
    /**
     * Contratos de código del trabajo.
     */
    CONTRATOS_CODIGO_TRABAJO("CONT.CT", "CONTRATO TRABAJO A PLAZO FIJO"),
    /**
     * Contratos de trabajo temporada.
     */
    CONTRATOS_TRABAJO_TEMPORADA("CON.TEM", "CONTRATO TRABAJO TEMPORADA"),
    /**
     * Contratos de trabajo eventual.
     */
    CONTRATOS_TRABAJO_EVENTUAL("CON.EVE", "CONTRATO TRABAJO EVENTUAL"),
    /**
     * Contratos de trabajo por tiempo indefinido con periodo de prueba.
     */
    CONTRATOS_TRABAJO_INDEFINIDO_PRUEBA("CON.IND.PER.PRU", "CONTRATO TRABAJO POR TIEMPO INDEFINIDO CON PERIODO DE PRUEBA"),
    /**
     * Contratos de trabajo por tiempo indefinido.
     */
    CONTRATO_TRABAJO_TIEMPO_INDEFINIDO("CON.IND", "CONTRATO DE TRABAJO POR TIEMPO INDEFINIDO"),
    /**
     * Contratos de trabajo.
     */
    CONTRATO_TRABAJO("CON.TRA", "CONTRATO DE TRABAJO"),
    /**
     * Contratos de trabajo plazo fijo jornada parcial.
     */
    CONTRATO_TRABAJO_PLAZO_FIJO_JORNADA_PARCIAL("CON.TRA.PLA.FIJ.JOR.PAR", "CONTRATO DE TRABAJO PLAZO FIJO JORNADA PARCIAL"),
    /**
     * Contratos de trabajo plazo fijo jornada parcial.
     */
    CONTRATO_TRABAJO_PLAZO_FIJO_PERIODO_DE_PRUEBA("CON.TRA.PLA.FIJ", "CONTRATO DE TRABAJO PLAZO FIJO JORNADA PARCIAL"),
    /**
     * Acta de terminacion de contrato por sevicios ocacionales.
     */
    ACTA_TERMINACION_CONTRATO_SERVICIOS_OCACIONALES("ACT.TER.CON.SER.OCA", "ACTA TERMINACIÓN DE CONTRATOS DE SERVICIOS OCACIONALES"),
    /**
     * ademdum modificatorio - cambio dependencia   .
     */
    ADEMDUM_MODIFICATORIO_CAMBIO_DEPENDENCIA("ADM.MOD.CAM.DEP", "ADEMDUM MODIFICATORIO - CAMBIO DEPENDENCIA"),
    /**
     * Contratos de memorando.
     */
    MEMORANDO("MEM", "MEMORANDO");

    /**
     * Columna del campo ordenamiento.
     */
    private String codigo;

    /**
     * Descripcion del campo ordenamiento.
     */
    private String descripcion;

    /**
     * Constructor requerido.
     *
     * @param codigo String
     * @param descripcion String
     */
    private DocumentoHabilitanteEnum(final String codigo, final String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public static DocumentoHabilitanteEnum obtenerPorCodigo(final String codigo) {
        for (DocumentoHabilitanteEnum d : values()) {
            if (codigo.equals(d.getCodigo())) {
                return d;
            }
        }
        return null;
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
