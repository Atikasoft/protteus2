/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Jul 19, 2017
 */

CREATE TABLE sch_proteus.notificaciones
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    remitente_id NUMERIC(19,0) NOT NULL,
    institucion_ejercicio_fiscal_id NUMERIC(19,0) NOT NULL,
    asunto VARCHAR(100) COLLATE Modern_Spanish_CI_AS NOT NULL,
    mensaje VARCHAR(2000) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_envio DATETIME NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT notificaciones_pk PRIMARY KEY (id),
    CONSTRAINT notificaciones_remitente_id_fk 
               FOREIGN KEY (remitente_id) 
               REFERENCES sch_proteus.servidor ("id"),
    CONSTRAINT notificaciones_institucion_ejercicio_fiscal_id_fk 
               FOREIGN KEY (institucion_ejercicio_fiscal_id) 
               REFERENCES sch_proteus.instituciones_ejercicios_fiscales ("id")          
);

CREATE TABLE sch_proteus.notificaciones_destinatarios
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    notificacion_id NUMERIC(19,0) NOT NULL,
    destinatario_id NUMERIC(19,0) NOT NULL,
    estado VARCHAR(10) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_lectura DATETIME NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT notificaciones_destinatarios_pk PRIMARY KEY (id),
    CONSTRAINT notificaciones_destinatarios_notificacion_id_fk 
               FOREIGN KEY (notificacion_id) 
               REFERENCES sch_proteus.notificaciones ("id"),
    CONSTRAINT notificaciones_destinatarios_destinatario_id_fk 
               FOREIGN KEY (destinatario_id) 
               REFERENCES sch_proteus.servidor ("id"),
    CONSTRAINT notificaciones_destinatarios_uk 
               UNIQUE (notificacion_id, destinatario_id)            
);