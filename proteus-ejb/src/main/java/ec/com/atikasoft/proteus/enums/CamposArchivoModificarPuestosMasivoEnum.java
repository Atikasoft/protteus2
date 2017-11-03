/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.atikasoft.proteus.enums;

/**
 *
 * @author Leydis Garzon
 */
public enum CamposArchivoModificarPuestosMasivoEnum {
    
    CODIGO_PUESTO(1,"CPT","Código del Puesto","NUMÉRICO"),
    
    ESCALA_OCUPACIONAL(2,"CEO","Escala Ocupacional","ALFANUMÉRICO"),
    
    ESTADO_ADMINISTRACION_PUESTO(3,"CEAP","Código Estado Administración Puesto","ALFANUMÉRICO"),
    
    MODALIDAD_LABORAL(4,"CML","Código de Modalidad Laboral","ALFANUMÉRICO"),
    
    DENOMINACION_PUESTO(5,"CDP","Código de Denominación del Puesto","ALFANUMÉRICO"),
    
    UNIDAD_ORGANIZACIONAL(6,"CUO","Código de Unidad Organizacional","ALFANUMÉRICO"),
    
    PARTIDA_INDIVIDUAL(7,"CPI","Partida Individual","NUMÉRICO"),
    
    UNIDAD_PRESUPUESTARIA(8,"CUP","Código de Unidad Presupuestaria","ALFANUMÉRICO"),
    
    GRUPO_PRESUPUESTARIO(9,"CGP","Grupo Presupuestario","ALFANUMÉRICO"),
    
   /* CERTIFICACION_PRESUPUESTARIA(10,"VCP","Certificación Presupuestaria","ALFANUMÉRICO"),*/
    
    OBSERVACION(10,"OBS","Observación","ALFANUMÉRICO");
    
    
    /**
     * Columna del campo en el archivo.
     */
    private final int columna;
    /**
     * Codigo del campo.
     */
    private final String codigo;
    /**
     * Descripcion del campo.
     */
    private final String descripcion;
    
    /**
     * Tipo de dato del campo.
     */
    private final String tipo;
    
    
    /**
     * @param columna
     * @param descripcion 
     * @param tipo 
     */
    private CamposArchivoModificarPuestosMasivoEnum(int columna, String codigo, String descripcion, String tipo) {
        this.columna = columna;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.codigo = codigo;
    }
    
    public int getColumna() {
        return columna;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCodigo() {
        return codigo;
    }
    
}
