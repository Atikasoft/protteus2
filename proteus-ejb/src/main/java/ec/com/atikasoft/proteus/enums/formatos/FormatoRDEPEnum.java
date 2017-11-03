/**
 *  FormatoRDEPEnum.java
 *  PROTEUS V 2.0 $Revision 1.0 $
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
 *  03/12/2013
 *
 *  Copyright (C) 2013 AtikaSoft.
 */
package ec.com.atikasoft.proteus.enums.formatos;

/**
 * Formato de archivo RDEP del sri.
 *
 *  @author Liliana Rodriguez <liliana.rodriguez@markasoft.ec>
 */
public enum FormatoRDEPEnum {
    RUC_ENTIDAD(1,"numRuc", 1),
    EJERCICIO_FISCAL(2, "anioRet", 1),
    
    TIPO_IDENTIFICACION(1, "tipIdRet", 2),
    NUMERO_IDENTIFICACION(2, "idRet", 2),
    APELLIDO_TRABAJADOR(3, "apellidoTrab", 2),
    NOMBRE_TRABAJADOR(4, "nombreTrab", 2),   
    COD_ESTABLECIMIENTO(5, "estab", 2),
    RESIDENCIA_TRABAJADOR(6, "residenciaTrab", 2), //01 Residente Local, 02 Residente en el Exterior.
    PAIS_RESIDENCIA(7, "paisResidencia", 2),
    APLICA_CONVENIO(8, "aplicaConvenio", 2),
    TIPO_DICAPACIDAD(9, "tipoTrabajDiscap", 2), //01-NO APLICA , 02-CON DISCAPACIDAD, 03-SUSTITUTO DE UNA PERSONA CON DISCAPACIDAD, 04-CON CARGA FAM CON DISCAPACIDAD
    PORCENTAJE_DICAPACIDAD(10, "porcentajeDiscap", 2),
    TIPO_DISCAPACIDAD(11, "tipIdDiscap", 2), //tipo de identificacion de la persona discapacitada
    CODIGO_CARNET_CONADIS(12, "idDiscap", 2), //numero de identificacion de la persona discapacitada
    
    SUELDO(1, "suelSal", 3),
    SOBRESUELDO(2, "sobSuelComRemu", 3),
    PARTICIPACION_UTILIDADES(3, "partUtil", 3),
    INGRESOS_OTROS_EMPLEADORES(4, "intGrabGen", 3),
    IMPUESTO_RENTA_ASUMIDO(5, "impRentEmpl", 3), //Impuesto a la Renta asumido por este empleador
    DECIMO_TERCERO(6, "decimTer", 3),
    DECIMO_CUARTO(7, "decimCuar", 3),
    FONDOS_RESERVA(8, "fondoReserva", 3),
    SALARIO_DIGNO(9, "salarioDigno", 3),
    OTROS_INGRESOS_RENTA_GRAVADA(10, "otrosIngRenGrav", 3),
    INGRESOS_GRAVADOS(11, "ingGravConEsteEmpl", 3),
    SISTEMA_SALARIO_NETO(12, "sisSalNet", 3),
    APORTE_PERSONAL_IESS(13, "apoPerIess", 3),
    APORTE_IESS_OTROS_EMPLEADORES(14, "aporPerIessConOtrosEmpls", 3),
    DEDUCCION_VIVIENDA(15, "deducVivienda", 3),
    DEDUCCION_SALUD(16, "deducSalud", 3),
    DEDUCCION_EDUCACION(17, "deducEduca", 3),
    DEDUCCION_ALIMENTACION(18, "deducAliement", 3),
    DEDUCCION_VESTIMENTA(19, "deducVestim", 3),
    EXONERACION_DISCAPACIDAD(20, "exoDiscap", 3),
    EXONERACION_TERCERA_EDAD(21, "exoTerEd", 3),
    BASE_IMPONIBLE(22, "basImp", 3),
    IMPUESTO_CAUSADO(23, "impRentCaus", 3),
    IMPUESTO_RENTA_RETENIDO_ASUMIDO_OTROS_EMPLEADORES(24, "valRetAsuOtrosEmpls", 3),
    VALOR_IMPUESTO_RENTA_ASUMIDO(25, "valRetAsuOtrosEmpls", 3), //Valor del impuesto asumido por este empleador
    VALOR_RETENIDO(26, "valRet", 3);
     
    /**
     * Indice de ordenamiento en una seccion.
     */
    private final Integer indice;

    /**
     * Nombre del tag.
     */
    private final String tag;

    /**
     * Seccion.
     */
    private final Integer seccion;

    
    /**
     * Constructor.
     *
     * @param indice Integer
     */
    private FormatoRDEPEnum(final Integer indice, final String tag, final Integer seccion) {
        this.indice = indice;
        this.tag = tag;
        this.seccion = seccion;
        
    }
 /**
     * Este metodo busca el tag de una posicion de una seccion.
     *
     * @param seccion Integer
     * @param indice
     * @return String
     */
    public static String obtenerTag(final Integer seccion, final Integer indice) {
        String des = null;
        for (FormatoRDEPEnum cc : values()) {
            if (cc.getSeccion().equals(seccion) && cc.getIndice().equals(indice)) {
                des = cc.getTag();
                break;
            }
        }
        return des;
    }
    /**
     * @return the numero
     */
    public Integer getIndice() {
        return indice;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return the seccion
     */
    public Integer getSeccion() {
        return seccion;
    }

}
