/*
 *  ParametroGlobalEnum.java
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
 *  Sep 6, 2011
 *
 *
 *  Copyright (C) 2008 AtikaSoft.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Juan Carlos Vaca <jvaca@atikasoft.com.ec>
 */
public enum ParametroGlobalEnum {

    /**
     * Indica la edad cuando se considera a una persona mayor de edad.
     */
    MAYOR_EDAD("MAYEDAD"),
    /**
     * Indica la edad cuando se considera a una persona de tercera edad.
     */
    TERCERA_EDAD("TEREDAD"),
    /**
     * NEMONICO DE MODALIDAD LABORAL DE NOMBRAMIENTO.
     */
    NEMONICO_MODALIDAD_LABORAL_NOMBRAMIENTO("NEMOLANO"),
    /**
     * NEMONICO DE MODALIDAD LABORAL DE CONTRATOS.
     */
    NEMONICO_MODALIDAD_LABORAL_CONTRATOS("NEMOLACON"),
    /**
     * NÉMONICO DEL TIPO DE MOVIMIENTO DE SUSPENSIÓN TEMPORAL SIN GOCE DE REMUNERACIÓN.
     */
    NEMONICO_TIPO_MOVIMIENTO_SUSPENSION_TEMPORAL_SIN_GOCE_REMUNERACION("SUSTEM"),
    /**
     * NEMÓNICO DEL TIPO DE MOVIMIENTO COMISIÓN SERVICIO SIN REMUNERACIÓN.
     */
    NEMONICO_TIPO_MOVIMIENTO_COMISION_SERVICIO_SIN_REMUNERACION("NETIMOCOSESIRE"),
    /**
     * NEMONICO DEL TIPO DE MOVIMIENTO CESACIÓN POR DESTITUCIÓN.
     */
    NEMONICO_TIPO_MOVIMIENTO_CESACION_POR_DESTITUCION("NETIMOCEPODE"),
    /**
     * NÉMONICO DEL TIPO DE MOVIMIENTO PERIODO DE PRUEBA POR ASCENSO
     */
    NEMONICO_TIPO_MOVIMIENTO_PERIODO_PRUEBA_ASCENSO("NETIMOPEPRPOAS"),
    /**
     * TIEMPO MAXIMO DE SUSPENSIÓN TEMPORAL SIN GOCE DE REMUNERACIÓ (DÍAS).
     */
    TIEMPO_MAXIMO_SUSPENSION_TEMPORAL_SIN_GOCE_REMUNERACION("TIMASUTE"),
    /**
     * NEMONICO DE MOTIVO DE INGRESO NOMBRAMIENTO PERMANENTE.
     */
    NEMONICO_MOTIVO_INGRESO_NOMBRAMIENTO_PERMANENTE("NEMOINNOPE"),
    /**
     * CORREO ELECTRONICO DEL ADMINISTRADOR DEL SISTEMA.
     */
    CORREO_ELECTRONICO_ADMINISTRADOR_SISTEMA("CORELEADM"),
    /**
     * EDAD JUBILACION OBLIGATORIA MASCULINA.
     */
    EDAD_JUBILACION_OBLIGATORIA_MASCULINA("EDJUOBMA"),
    /**
     * EDAD JUBILACION OBLIGATORIA FEMENINA.
     */
    EDAD_JUBILACION_OBLIGATORIA_FEMENINA("EDJUOBFE"),
    /**
     * EDAD JUBILACION MINIMA MASCULINA.
     */
    EDAD_JUBILACION_MINIMA_MASCULINA("EDJUMIMA"),
    /**
     * EDAD JUBILACION MINIMA FEMENINA.
     */
    EDAD_JUBILACION_MINIMA_FEMENINA("EDJUMIFE"),
    /**
     * EDAD JUBILACION MAXIMA MASCULINA.
     */
    EDAD_JUBILACION_MAXIMA_MASCULINA("EDJUMAMA"),
    /**
     * EDAD JUBILACION MAXIMA FEMENINA.
     */
    EDAD_JUBILACION_MAXIMA_FEMENINA("EDJUMAFE"),
    /**
     * PORCENTAJE MÁXIMO DE CONTRATOS EN RELACION AL TOTAL DE EMPLEADOS.
     */
    PORCENTAJE_MAXIMO_CONTRATOS_RELACION_TOTAL_EMPLEADOS("POMACONRETO"),
    /**
     * Url de reportes.
     */
    URL_DE_REPORTES("URLREP"),
    /**
     * NÉMONICO DE REQUISITOS PARA LA CONSULTA DE LA INFORMACIÓN ACADÉMICA.
     */
    VERIFICAR_FORMACION_ACADEMICA("VERFORACA"),
    /**
     * NEMÓNICO DEL TIPO DE MOVIMIENTO LICENCIA POR MATERNIDAD.
     */
    NEMONICO_TIPO_MOVIMIENTO_LICENCIA_MATERNIDAD("NETIMOLIMA"),
    /**
     * NEMÓNICO DEL TIPO DE MOVIMIENTO LICENCIA POR PATERNIDAD.
     */
    NEMONICO_TIPO_MOVIMIENTO_LICENCIA_PATERNIDAD("NETIMOLIPA"),
    /**
     * NEMÓNICO DEL TIPO DE MOVIMIENTO LICENCIA SIN REMUNERACION.
     */
    NEMONICO_TIPO_MOVIMIENTO_LICENCIA_SIN_REMUNERACION("NETIMOLISIRE"),
    /**
     * NEMONICO DE REGIMENES DE PERSONAL LOSEP.
     */
    NEMONICO_REGIMENES_PERSONAL_LOSEP("NEREPELO"),
    /**
     * Tamaño maximo de archivos pdf.
     */
    TAMANIO_MAXIMO_PDF("PDFTAM"),
    /**
     * Dirección de correo electrónico de rrhh para notificaciones.
     */
    CORREO_RRHH("MAIL.RRHH"),
    /**
     * FECHA EN QUE INICIA EL PERIODO PARA REALIZAR LA PLANIFICACION DE LAS VACACIONES
     */
    FECHA_INICIO_PLANIFICACION_VACACIONES("FIPV"),
    /**
     * FECHA EN QUE TERMINA EL PERIODO PARA REALIZAR LA PLANIFICACION DE LAS VACACIONES
     */
    FECHA_FIN_PLANIFICACION_VACACIONES("FFPV"),
    /**
     * REPOSITORIO DE ARCHIVOS.
     */
    REPOS("REPOS"),
    /**
     * USUARIO SISTEMA DE PERSONAS.
     */
    USUARIO_SISTEMA_PERSONAS("USSIPE"),
    /**
     * CLAVE SISTEMA DE PERSONAS.
     */
    CLAVE_SISTEMA_PERSONAS("CLSIPE"),
    /**
     * DEFINE DEL PRIMER DIA DE LA SEMANA.
     */
    PRIMER_DIA_SEMANA("ASIS.MARC.PRIMER.DIA.SEMANA"),
    /**
     * DEFINE EL DIA DE CORTE PARA MIGRAR LAS MARCACIONES.
     */
    DIA_CORTE_MARCACIONES("ASIS.MARC.DIA.CORTE");

    /**
     * Codigo del parametro en la base de datos.
     */
    private final String codigo;

    /**
     * Constructor que inicializa el codigo.
     *
     * @param codigo Codigo del parametro en la base de datos
     */
    private ParametroGlobalEnum(final String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
}
