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
public enum FormatoArchivoTramiteIngresoEnum {

    OPI_ID(1, "I", "OPI ID", "T", "S", null, null),
    PG(2, "I", "PARTIDA GENERAL", "T", "N", null, null),
    PI(3, "I", "PARTIDA INDIVIDUAL", "T", "N", null, null),
    RG(4, "I", "REGIMEN LABORAL", "T", "N", null, null),
    UA(5, "I", "UNIDAD ADMINISTRATIVA", "T", "N", null, null),
    DP(6, "I", "DENOMINACIÓN PUESTO", "T", "N", null, null),
    UG(7, "I", "UBICACIÓN GEOGRÁFICA", "T", "N", null, null),
    ML(8, "I", "MODALIDAD LABORAL", "T", "N", null, null),
    GO(9, "I", "GRUPO OCUPACIONAL", "T", "N", null, null),
    PN(10, "I", "PROYECTO DE INVERSIÓN", "T", "N", "SI,NO", null),
    RP(11, "I", "ROL DEL PUESTO", "T", "N", null, null),
    RMU(12, "I", "RMU", "N", "N", null, null),
    TI(13, "I", "TIPO DE IDENTIFICACIÓN", "T", "C", "C,P", null),
    NI(14, "I", "NÚMERO DE IDENTIFICACIÓN ", "T", "C", null, null),
    AS(15, "I", "APELLIDOS DEL SERVIDOR", "T", "C", null, null),
    NS(16, "I", "NOMBRES DEL SERVIDOR", "T", "C", null, null),
    FN(17, "I", "FECHA DE NACIMIENTO", "F", "C", null, null),
    GE(18, "I", "GÉNERO", "T", "C", null, "GEN"),
    EC(19, "I", "ESTADO CIVIL", "T", "C", null, "ESC"),
    CO(20, "I", "CONYUGUE", "T", "C", null, null),
    NA(21, "I", "NACIONALIDAD", "T", "C", null, "NAC"),
    IE(22, "I", "IDENTIFICACIÓN ÉTNICA", "T", "C", null, "TIPETNIA"),
    AR(23, "I", "AÑOS DERESIDENCIA", "N", "C", null, null),
    PD(24, "I", "PARROQUIA DEL DOMICILIO", "T", "C", null, "CNT"),
    CPD(25, "I", "CALLE PRINCIPAL DEL DOMICILIO", "T", "C", null, null),
    CSD(26, "I", "CALLE SECUNDARIA DEL DOMICILIO", "T", "C", null, null),
    NUD(27, "I", "NÚMERO DEL DOMICILIO", "T", "C", null, null),
    RED(28, "I", "REFERENCIA DEL DOMICILIO", "T", "C", null, null),
    TED(29, "I", "TELÉFONO DEL DOMICILIO", "T", "C", null, null),
    TET(30, "I", "TELÉFONO DEL TRABAJO", "T", "C", null, null),
    CE(31, "I", "CORREO ELÉCTRONICO", "T", "C", null, null),
    FII(32, "I", "FECHA DE INGRESO A LA INSTITUCIÓN", "F", "C", null, null),
    FISP(33, "I", "FECHA DE INGRESO AL SECTOR PÚBLICO", "F", "C", null, null),
    SP(34, "I", "SERVIDOR ES PASANTE", "T", "C", "SI,NO", null),
    SC(35, "I", "SERVIDOR ES DE CARRERA", "T", "C", "SI,NO", null),
    NC(36, "I", "NÚMERO DE CARRERA", "T", "C", null, null),
    DI(37, "I", "DISCAPACIDAD", "T", "C", "SI,NO", null),
    NCO(38, "I", "NÚMERO DEL CONADIS", "T", "N", null, null),
    ESD(39, "I", "ESPCIFIQUE DISCAPACIDAD", "T", "N", "D,E", null),
    TID(40, "I", "TIPO DISCAPACIDAD", "T", "N", null, "TPDSC"),
    POD(41, "I", "PORCENTAJE DICAPACIDAD", "N", "N", null, null),
    COD(42, "I", "CORRESPONDE A DISCAPACIDAD", "T", "N", "S,F", null),
    JDI(43, "I", "JUSTIFICACIÓN DE DISCAPACIDAD", "T", "N", null, null),
    TD(44, "I", "TIPO DE DESIGNACIÓN", "T", "C", "EP,CC", null),
    TJD(45, "I", "TIEMPO DE JORNADA DIARIA", "N", "C", null, null),
    TT(46, "I", "TIPO DE TEMPORADA", "T", "C", "ESC,NAV,VAC,OTR", null),
    FI(47, "I", "RIGE A PARTIR DE", "F", "C", null, null),
    FF(48, "I", "FECHA FINAL", "F", "C", null, null),
    DH(49, "I", "DOCUMENTO HABILITANTE", "T", "C", null, null),
    ISA(50, "I", "IMPRIME LA SITUACIÓN ACTUAL", "T", "C", "SI,NO", null),
    ISP(51, "I", "IMPRIME LA SITUACIÓN PROPUESTA", "T", "C", "SI,NO", null),
    EAP(52, "I", "EXPLICACIÓN DE ACCIÓN DE PERSONAL", "T", "C", null, null),
    DPAP(53, "I", "DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "T", "C", "DCR,ACR,RSL", null),
    NDPAP(54, "I", "NÚMERO DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "T", "C", null, null),
    FDPAP(55, "I", "FECHA DOCUMENTO PREVIO DE ACCIÓN DE PERSONAL", "F", "C", null, null),
    AM(56, "I", "ASUNTO DEL MEMORANDO", "T", "C", null, null),
    NM(57, "I", "NÚMERO DEL MEMORANDO", "T", "C", null, null),
    CM(58, "I", "CONTENIDO DEL MEMORANDO", "T", "C", null, null),
    AC(59, "I", "ANTECEDENTES DEL CONTRATO", "T", "C", null, null),
    CC(60, "I", "ACTIVIDADES DEL CONTRATO", "T", "C", null, null),
    STA(61, "I", "SIGLAS DEL TÍTULO ACADEMICO", "T", "C", null, null),
    RC(62, "I", "RENOVACIÓN DEL CONTRATO", "T", "C", "SI,NO", null);

    private String grupo;

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

    private FormatoArchivoTramiteIngresoEnum(final int columna, final String grupo, final String descripcion,
            final String tipo,
            final String obligatoriedad, final String dominio, final String catalogo) {
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

    public String getGrupo() {
        return grupo;
    }
}
