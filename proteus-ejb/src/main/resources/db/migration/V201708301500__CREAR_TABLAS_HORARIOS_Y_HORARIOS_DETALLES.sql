/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Leydis Garzón
 * Created: Ago 10, 2017
 */

CREATE TABLE sch_proteus.horarios
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    nombre VARCHAR(400) COLLATE Modern_Spanish_CI_AS NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT horarios_pk PRIMARY KEY (id)
);

CREATE TABLE sch_proteus.horarios_detalles
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    horario_id NUMERIC(19,0) NOT NULL,
    dia_semana VARCHAR(1) NOT NULL,
    laborable BIT DEFAULT 0,
    total_horas_diaria INTEGER,
    hora_entrada DATETIME,
    hora_inicio_almuerzo DATETIME,
    hora_fin_almuerzo DATETIME,
    tiempo_almuerzo_permitido INTEGER,
    hora_salida DATETIME,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT horarios_detalles_pk PRIMARY KEY (id),
    CONSTRAINT horarios_id_fk 
               FOREIGN KEY (horario_id) 
               REFERENCES sch_proteus.horarios ("id")
);