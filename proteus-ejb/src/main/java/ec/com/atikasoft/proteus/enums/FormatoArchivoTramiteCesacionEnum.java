/*
 *  CamposConfiguracionEnum.java
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
 *  22/10/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Patricio Recalde <patricio.recalde@atikasoft.com.ec>
 */
public enum FormatoArchivoTramiteCesacionEnum {

    OPI_ID(1, "OPI ID", "T", "S", null, null),
    PG(2, "PARTIDA GENERAL", "T", "N", null, null),
    PI(3, "PARTIDA INDIVIDUAL", "T", "N", null, null),
    RG(4, "REGIMEN LABORAL", "T", "N", null, null),
    RMU(5, "RMU", "N", "N", null, null),
    UA(6, "UNIDAD ADMINISTRATIVA", "T", "N", null, null),
    DP(7, "DENOMINACIÓN PUESTO", "T", "N", null, null),
    UG(8, "UBICACIÓN GEOGRÁFICA", "T", "N", null, null),
    ML(9, "MODALIDAD LABORAL", "T", "N", null, null),
    GO(10, "GRUPO OCUPACIONAL", "T", "N", null, null),
    PN(11, "PROYECTO DE INVERSIÓN", "T", "N", "SI,NO", null),
    RP(12, "ROL DEL PUESTO", "T", "N", null, null),
    FI(13, "RIGE A PARTIR DE", "F", "C", null, null),
    FF(14, "FECHA FINAL", "F", "C", null, null),
    TI(15, "TIPO DE IDENTIFICACIÓN", "T", "C", "C,P", null),
    NI(16, "NÚMERO DE IDENTIFICACIÓN ", "T", "C", null, null),
    AS(17, "APELLIDOS DEL SERVIDOR", "T", "C", null, null),
    NS(18, "NOMBRES DEL SERVIDOR", "T", "C", null, null),
    GE(19, "GÉNERO", "T", "C", null, null),
    NA(20, "NACIONALIDAD", "T", "C", null, null),
    EC(21, "ESTADO CIVIL", "T", "C", null, null),
    FN(22, "FECHA DE NACIMIENTO", "F", "C", null, null),
    FII(23, "FECHA DE INGRESO A LA INSTITUCIÓN", "F", "C", null, null),
    FISP(24, "FECHA DE INGRESO AL SECTOR PÚBLICO", "F", "C", null, null),
    ISA(25, "IMPRIME LA SITUACIÓN ACTUAL", "T", "C", "SI,NO", null),
    ISP(26, "IMPRIME LA SITUACIÓN PROPUESTA", "T", "C", "SI,NO", null),
    DH(27, "DOCUMENTO HABILITANTE", "T", "C", null, null),
    EAP(28, "EXPLICACIÓN DE ACCIÓN DE PERSONAL", "T", "C", null, null),
    DPAP(29, "DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "T", "C", "DCR,ACR,RSL", null),
    NDPAP(30, "NÚMERO DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "T", "C", null, null),
    FDPAP(31, "FECHA DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "F", "C", null, null),
    AM(32, "ASUNTO DEL MEMORANDO", "T", "C", null, null),
    NM(33, "NÚMERO DEL MEMORANDO", "T", "C", null, null),
    CM(34, "CONTENIDO DEL MEMORANDO", "T", "C", null, null),
    FAR(35, "FECHA ACEPTACIÓN DE RENUNCIA", "F", "C", null, null),
    FPR(36, "FECHA PRESENTACIÓN DE RENUNCIA", "F", "C", null, null),
    FFA(37, "FECHA DE FALLECIMIENTO", "F", "C", null, null),
    CFA(38, "CASO DE FALLECIMIENTO", "T", "C", "ACT,ENP,ENF", null);

    /**
     * Numero de columna.
     */
    private int columna;

    /**
     * Descripcion del campo de configuracion.
     */
    private String descripcion;

    private String tipo;

    private String obligatoriedad;

    private String dominio;

    private String catalogo;

    public static final String OBLIGATORIEDAD_SI = "SI";

    public static final String OBLIGATORIEDAD_NO = "NO";

    public static final String OBLIGATORIEDAD_CONFIGURACION = "C";

    public static final String TIPO_TEXTO = "T";

    public static final String TIPO_NUMERICO = "N";

    public static final String TIPO_FECHA = "F";

    private FormatoArchivoTramiteCesacionEnum(final int columna, final String descripcion,
            final String tipo, final String obligatoriedad, final String dominio, final String catalogo) {
        this.columna = columna;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.obligatoriedad = obligatoriedad;
        this.catalogo = catalogo;
        this.dominio = dominio;
        
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the obligatoriedad
     */
    public String getObligatoriedad() {
        return obligatoriedad;
    }

    /**
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @return the catalogo
     */
    public String getCatalogo() {
        return catalogo;
    }

    /**
     * @param obligatoriedad the obligatoriedad to set
     */
    public void setObligatoriedad(final String obligatoriedad) {
        this.obligatoriedad = obligatoriedad;
    }
}
