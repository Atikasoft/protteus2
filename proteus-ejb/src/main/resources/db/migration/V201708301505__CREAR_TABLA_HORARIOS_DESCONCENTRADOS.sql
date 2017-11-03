/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Osmel Cobas Guilarte <osmel.cobas@atikasoft.com>
 * Created: Aug 18, 2017
 */

CREATE TABLE sch_proteus.horarios_desconcentrados
(
    id NUMERIC(19,0) NOT NULL IDENTITY,
    horario_id NUMERIC(19,0) NOT NULL,
    desconcentrado_id NUMERIC(19,0) NOT NULL,

    fecha_creacion DATETIME NOT NULL,
    usuario_creacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS NOT NULL,
    fecha_actualizacion DATETIME,
    usuario_actualizacion VARCHAR(40) COLLATE Modern_Spanish_CI_AS,
    vigente BIT DEFAULT 0,

    CONSTRAINT horarios_desconcentrados_pk PRIMARY KEY (id),
    CONSTRAINT horario_id_fk 
               FOREIGN KEY (horario_id) 
               REFERENCES sch_proteus.horarios ("id"),
    CONSTRAINT desconcentrado_id_fk 
               FOREIGN KEY (desconcentrado_id) 
               REFERENCES sch_proteus.desconcentrados ("id"),  
    CONSTRAINT horarios_desconcentrados_uk 
               UNIQUE (horario_id, desconcentrado_id)           
);