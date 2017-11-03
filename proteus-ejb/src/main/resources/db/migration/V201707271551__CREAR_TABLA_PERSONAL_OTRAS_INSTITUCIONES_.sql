/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 21, 2017
 */

CREATE TABLE sch_proteus.personal_otras_instituciones
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    apellidos_nombres VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    tipo_identificacion VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    numero_identificacion VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    fecha_salida_institucion_origen DATETIME NOT NULL,
    justificacion_ingreso VARCHAR(1000) COLLATE Modern_Spanish_CI_AS,
    justificacion_salida VARCHAR(1000) COLLATE Modern_Spanish_CI_AS,
    institucion_origen VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    puesto_institucion_origen VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    rmu_institucion_origen NUMERIC(16,2) DEFAULT 0,
    unidad_organizacional_id NUMERIC(19,0),

    archivo_accion_ingreso_personal_id NUMERIC(19,0),
    numero_archivo_accion_ingreso_personal VARCHAR(200) COLLATE Modern_Spanish_CI_AS,
    archivo_accion_salida_personal_id NUMERIC(19,0),
    numero_archivo_accion_salida_personal VARCHAR(200) COLLATE Modern_Spanish_CI_AS,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT personal_otras_instituciones_pk PRIMARY KEY (id),
    CONSTRAINT poi_archivo_accion_ingreso_fk 
               FOREIGN KEY (archivo_accion_ingreso_personal_id) 
               REFERENCES sch_proteus.archivos ("id"),
    CONSTRAINT poi_archivo_accion_salida_fk 
               FOREIGN KEY (archivo_accion_salida_personal_id) 
               REFERENCES sch_proteus.archivos ("id"),
    CONSTRAINT poi_unidad_organizacional_fk 
               FOREIGN KEY (unidad_organizacional_id) 
               REFERENCES sch_proteus.unidades_organizacionales ("id")
);