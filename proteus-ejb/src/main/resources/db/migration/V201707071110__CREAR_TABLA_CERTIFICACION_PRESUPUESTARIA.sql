/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzon
 * Created: Jul 07, 2017
 */

CREATE TABLE sch_proteus.certificaciones_presupuestarias
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    modalidad_laboral_id NUMERIC(19,0) NOT NULL,
    unidad_presupuestaria_id NUMERIC(19,0) NOT NULL,
    certificacion_presupuestaria VARCHAR(200) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    CONSTRAINT certificaciones_presupuestarias_pk PRIMARY KEY (id),
    CONSTRAINT cp_modalidad_laboral_id_fk 
               FOREIGN KEY (modalidad_laboral_id) 
               REFERENCES sch_proteus.modalidades_laborales ("id"),
    CONSTRAINT cp_unidad_presupuestaria_id_fk 
               FOREIGN KEY (unidad_presupuestaria_id) 
               REFERENCES sch_proteus.unidades_presupuestarias ("id")
);

CREATE TABLE sch_proteus.certificaciones_presupuestarias_historico
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    modalidad_laboral_id NUMERIC(19,0) NOT NULL,
    unidad_presupuestaria_id NUMERIC(19,0) NOT NULL,
    nombre_modalidad_laboral VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    tipo_modalidad VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    nombre_unidad_presupuestaria VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    grupo_presupuestario VARCHAR(255) COLLATE Modern_Spanish_CI_AS,
    ruc VARCHAR(13) COLLATE Modern_Spanish_CI_AS,
    certificacion_presupuestaria VARCHAR(200) COLLATE Modern_Spanish_CI_AS,
    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,
    observacion VARCHAR(500) COLLATE Modern_Spanish_CI_AS,
    CONSTRAINT certificaciones_presupuestarias_historico_pk PRIMARY KEY (id),
    CONSTRAINT cph_modalidad_laboral_id_fk 
               FOREIGN KEY (modalidad_laboral_id) 
               REFERENCES sch_proteus.modalidades_laborales ("id"),
    CONSTRAINT cph_unidad_presupuestaria_id_fk 
               FOREIGN KEY (unidad_presupuestaria_id) 
               REFERENCES sch_proteus.unidades_presupuestarias ("id")
);