/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Aug 28, 2017
 */

CREATE TABLE sch_proteus.excepciones_asistencia_personal
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    servidor_id NUMERIC(19,0),
    justificacion VARCHAR(400) COLLATE Modern_Spanish_CI_AS NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT excepciones_asistencia_personal_pk PRIMARY KEY (id),
    CONSTRAINT excepciones_asistencia_personal_servidor_id_fk 
               FOREIGN KEY (servidor_id) 
               REFERENCES sch_proteus.servidor ("id"),
);