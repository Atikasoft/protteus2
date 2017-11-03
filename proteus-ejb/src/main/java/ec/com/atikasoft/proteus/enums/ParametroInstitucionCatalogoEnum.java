/*
 *  TipoParametroInstitucionCatalogoEnum.java
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
 *  07/12/2012
 *
 */
package ec.com.atikasoft.proteus.enums;

/**
 * Define el catalogo de parametris institucionales.
 *
 * @author Henry Molina <hmolina@atikasoft.com.ec>
 */
public enum ParametroInstitucionCatalogoEnum {

    /**
     * DELEGADO REPRESENTANTE LEGAL.
     */
    DELEGADO_REPRESENTANTE_LEGAL("DEL.REG.LEG", "DELEGADO REPRESENTANTE LEGAL"),
    /**
     * ARTICULO DE LA INSTITUCIÓN.
     */
    ARTICULO_DE_INSTITUCION("ART.INT", "ARTICULO DE LA INSTITUCIÓN"),
    /**
     * NOMBRE DE LA INSTITUCIÓN.
     */
    NOMBRE_DE_INSTITUCION("NOM.INS", "NOMBRE DE LA INSTITUCIÓN"),
    /**
     * REPRESENTANTE LEGAL.
     */
    REPRESENTANTE_LEGAL("REP.LEG", "REPRESENTANTE LEGAL"),
    /**
     * NUMERO DE ACUERDO MINISTERIAL.
     */
    NUMERO_ACUERDO_MINISTERIAL("NUM.ACU.MIN", "NUMERO DE ACUERDO MINISTERIAL"),
    /**
     * FECHA DEL ACUERDO MINISTERIAL.
     */
    FECHA_ACUERDO_MINISTERIAL("FEC.ACU.MIN", "FECHA DEL ACUERDO MINISTERIAL"),
    /**
     * DENOMINACIÓN DE LA ENTIDAD.
     */
    DENOMINACION_DE_ENTIDAD("DEN.ENT", "DENOMINACIÓN DE LA ENTIDAD"),
    /**
     * ARTICULO REPRESENTANTE LEGAL.
     */
    ARTICULO_REPRESENTANTE_LEGAL("ART.REP.LEG", "ARTICULO REPRESENTANTE LEGAL"),
    /**
     * LOGO DE LA INSTITUCION.
     */
    LOGO_DE_INSTITUCION("LOG.INS", "LOGO DE LA INSTITUCION"),
    /**
     * DIRECCION DE LA INSTITUCION.
     */
    DIRECCION_DE_INSTITUCION("DIR.INS", "DIRECCION DE LA INSTITUCION"),
    /**
     * MAXIMA AUTORIDAD INSTITUCIONAL.
     */
    MAXIMA_AUTORIDAD_INSTITUCIONAL("MAX.AUT.INS", "MAXIMA AUTORIDAD INSTITUCIONAL"),
    /**
     * PUESTO REPRESENTANTE LEGAL.
     */
    PUESTO_REPRESENTANTE_LEGAL("PUE.REP.LEG", "PUESTO REPRESENTANTE LEGAL"),
    /**
     * PERMITE REGISTRO DE PLANIFICACION ANUAL DE VACACIONES.
     */
    PLANIFICACION_ANUAL_VACACIONES("PEREPLANVA", "PERMITE REGISTRO DE PLANIFICACION ANUAL DE VACACIONES"),
    /**
     * PERMITE REGISTRO DE PLANIFICACION DE GASTOS PERSONALES.
     */
    ADMINISTRACION_GASTOS_PERSONALES("PEREGASPER", "PERMITE ADMINISTRACIÓN DE GASTOS PERSONALES"),
    /**
     * PERMITE INDICAR LA HORA DE INICIO DEL ALMUERZO.FORMATO: HH:MM.
     */
    HORA_FIN_ALMUERZO("FIN.ALMUERZO", "HORA DE FINALIZACIÓN DE HORARIO DE ALMUERZO"),
    /**
     * PERMITE INDICAR LA HORA DE FINALIZACION DEL ALMUERZO.FORMATO: HH:MM.
     */
    HORA_INICIO_ALMUERZO("INICIO.ALMUERZO", "HORA DE INICIO DE ALMUERZO"),
    /**
     * Endpoint ws de seguridades.
     */
    ENDPOINT_SEGURIDADES("ENDPOINT.SEGURIDAD", "ENDPOINT WS DE SEGURIDADES"),
    /**
     * Endpoint ws de personas.
     */
    ENDPOINT_PERSONAS("ENDPOINT.PERSONAS", "ENDPOINT WS DE PERSONAS"),
    /**
     * Codigo de RRHH.
     */
    CODIGO_RRHH("COD.RRHH", "CODIGO DE RRHH"),
    /**
     * Codigo de RRHH.
     */
    CODIGO_DIRECCION_RRHH("CO.DI.RE.HU", "CODIGO DIRECCION DE RRHH"),
    /**
     * Valor de numero maximo de horas de atraso, si sobrepasa se considera falta.
     */
    MAX_HORAS_ATRASO_DIA("H.MAX.ATRASO.DIA", "MAX NUMERO HORAS ATRASO POR DIA"),
    /**
     * Minimo de minutos para acumular horas extras.
     */
    MIN_MINUTOS_PARA_HORA_EXTRAS("MIN.MINUTO.HORA.EXTR", "MINIMO DE MINUTOS PARA CALCULAR HORAS EXTRAS"),
    /**
     * Mes pago decimo cuarto.
     */
    MES_PAGO_DECIMO_CUARTO("ME.PA.DE.CU", "MES PAGO DECIMO CUARTO"),
    /**
     * Mes pago decimo tercero.
     */
    MES_PAGO_DECIMO_TERCERO("ME.PA.DE.TE", "MES PAGO DECIMO TERCERO"),
    /**
     * Mes de anio que se puede realizar cambios respecto al pago de decimos.
     */
    MES_MODIFICACION_DECIMOS("ME.MO.DE", "MES MODIFICACION DECIMOS "),
    /**
     * Numeros de meses al partir del ingreso del servidor que puede decidir el pago de decimos.
     */
    MES_MODIFICACION_DECIMOS_SERVIDORES_NUEVOS("ME.MD.DE.SE.NU", "MES MODIFICACION DECIMOS SERVIDORES NUEVOS"),
    /**
     *
     */
    DIA_MAXIMO_REGISTRO_NOVEDADES_DESCONCENTRADOS("DIMARENODE", "DIA MAXIMO REGISTRO DE NOVEDADES POR DESCONCENTRADOS"),
    /**
     * USUARIOS AUTORIZADOS CALCULO DE NOMINA
     */
    USUARIOS_AUTORIZADOS_CALCULO_NOMINA("USAUCALNO", "USUARIOS AUTORIZADOS CALCULO DE NOMINA"),
    /**
     * DIA INICIO CALCULO NOMINA
     */
    DIA_INICIO_CALCULO_NOMINA("DIINCANO", "DIA INICIO CALCULO NOMINA"),
    /**
     * DIA FINAL CALCULO NOMINA
     */
    DIA_FINAL_CALCULO_NOMINA("DIFICANO", "DIA FINAL CALCULO NOMINA"),
    /**
     * CLAVE EJECUCION COMPLETA DE NOMINA
     */
    CLAVE_EJECUCION_COMPLETA_NOMINA("CLEJCONO", "CLAVE EJECUCION COMPLETA DE NOMINA"),
    /**
     * CLAVE DESBLOQUEO DE NOMINA
     */
    CLAVE_DESBLOQUEO_NOMINA("CLDENO", "CLAVE DESBLOQUEO DE NOMINA"),
    /**
     * DESCRIPCIÓN DE RESOLUCIÓN PARA ACCION DE REGISTRO PERSONAL DE OTRAS INSTITUCIONES
     */
    DESCRIPCION_DE_RESOLUCION_PARA_ACCION_DE_PERSONAL_REGISTRO_PERSONAL_OTRAS_INTITUCIONES(
            "DESC.RESOL.ACCION.PERSONAL.OTRA.INST.REGISTRO",
            "DESCRIPCIÓN DE RESOLUCIÓN PARA ACCION DE PERSONAL REGISTRO DE PERSONAL OTRA INSTITUCION"),
    /**
     * DESCRIPCIÓN DE RESOLUCIÓN PARA ACCION DE TERMINACIÓN PERSONAL DE OTRAS INSTITUCIONES
     */
    DESCRIPCION_DE_RESOLUCION_PARA_ACCION_DE_PERSONAL_TERMINACION_PERSONAL_OTRAS_INTITUCIONES(
            "DESC.RESOL.ACCION.PERSONAL.OTRA.INST.TERMINAC",
            "DESCRIPCIÓN DE RESOLUCIÓN PARA ACCION DE PERSONAL TERMINACIÓN DE PERSONAL OTRA INSTITUCION"),
    /**
     * USUARIOS AUTORIZADOS ENVIO NOTIFICACIONES.
     */
    USUARIOS_AUTORIZADOS_ENVIO_NOTIFICACIONES("USAUENNO", "USUARIOS AUTORIZADOS ENVIO NOTIFICACIONES");

    /**
     * Código del tipo parametro.
     */
    private String codigo;

    /**
     * Descripcion del tipo parametro.
     */
    private String descripcion;

    /**
     * Constructor.
     *
     * @param codigo
     * @param descripcion
     */
    private ParametroInstitucionCatalogoEnum(String codigo, String descripcion) {
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
        for (ParametroInstitucionCatalogoEnum tpg : values()) {
            if (tpg.getCodigo().equals(codigo)) {
                des = tpg.getDescripcion();
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
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
